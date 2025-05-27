import { TestBed } from '@angular/core/testing';
import { AuthService } from './auth.service';
import { UserService } from './user.service';
import { Router } from '@angular/router';
import Swal, { SweetAlertResult } from 'sweetalert2';

describe('AuthService', () => {
  let service: AuthService;
  let mockUserService: {
    login: jasmine.Spy<(userInput: string, password: string) => Promise<any | null>>;
    addUser: jasmine.Spy<(user: any) => Promise<any>>;
  };
  let mockRouter: { navigateByUrl: jasmine.Spy };

  beforeEach(() => {
    localStorage.clear();

    mockUserService = {
      login: jasmine.createSpy('login'),
      addUser: jasmine.createSpy('addUser'),
    };
    mockRouter = {
      navigateByUrl: jasmine.createSpy('navigateByUrl'),
    };

    TestBed.configureTestingModule({
      providers: [
        AuthService,
        { provide: UserService, useValue: mockUserService },
        { provide: Router, useValue: mockRouter },
      ],
    });

    service = TestBed.inject(AuthService);
    // Para evitar que setTimeout se programe en tests
    spyOn<any>(service, 'scheduleSessionExpiration').and.stub();
  });

  it('se debería devolver USER_ERROR si userService devuelve null', async () => {
    mockUserService.login.and.returnValue(Promise.resolve(null));
    const result = await service.login('user', 'pASSWORD');
    expect(result).toBe('USER_ERROR');
    expect(service.isAuthenticated).toBeFalse();
    expect(service.user).toBeNull();
  });

  it('se debería devolver OK e iniciar la sesión correctamente al hacer login correctamente', async () => {
    // token válido con exp dentro de una hora
    const payload = { exp: Math.floor(Date.now() / 1000) + 3600 };
    const fakeToken = `header.${btoa(JSON.stringify(payload))}.sig`;
    const foundUser = {
      userId: 1,
      username: 'usuario',
      email: 'a@b.com',
      rol: { name: 'USUARIO' },
      token: fakeToken,
    };
    mockUserService.login.and.returnValue(Promise.resolve(foundUser));

    const result = await service.login('usuario', 'Passw0rd!');
    expect(result).toBe('OK');
    expect(service.isAuthenticated).toBeTrue();
    expect(service.user?.username).toBe('usuario');
    expect(localStorage.getItem('authToken')).toBe(fakeToken);
    expect(localStorage.getItem('userSession')).toBe(JSON.stringify(service.user));
  });

  it('se debería poder registrar a un usuario nuevo con éxito', async () => {
    const newUser = { userId: 2, username: 'nuevo', email: 'n@e.com', rol: { id: 2, name: 'USUARIO' } };
    mockUserService.addUser.and.returnValue(Promise.resolve(newUser));
    const result = await service.registerUser({ username: 'nuevo', password: 'Pass1234!', repeatPassword: 'Pass1234!', email: 'n@e.com' });
    expect(result).toEqual(newUser);
  });

  it('el servidor debería lanzar error con un mensaje al registrar un usuario', async () => {
    const serverError = { error: { message: 'Error de registro' } };
    mockUserService.addUser.and.returnValue(Promise.reject(serverError));
    await expectAsync(
      service.registerUser({ username: 'nuevo', password: 'Pass1234!', repeatPassword: 'Pass1234!', email: 'n@e.com' })
    ).toBeRejectedWith('Error de registro');
  });


  it('el servidor debería lanzar error con un mensaje al registrar un usuario', async () => {
    const serverError = { error: { message: 'Error de registro' } };
    mockUserService.addUser.and.returnValue(Promise.reject(serverError));
    await expectAsync(
      service.registerUser({ username: 'nuevo', password: 'Pass1234!', repeatPassword: 'Pass1234!', email: 'n@e.com' })
    ).toBeRejectedWith('Error de registro');
  });

  it('el servidor debería lanzar error si el nombre de usuario ya existe', async () => {
    const serverError = { error: { message: 'Nombre de usuario ya en uso' } };
    mockUserService.addUser.and.returnValue(Promise.reject(serverError));
    await expectAsync(
      service.registerUser({ username: 'existe', password: 'Pass1234!', repeatPassword: 'Pass1234!', email: 'n@e.com' })
    ).toBeRejectedWith('Nombre de usuario ya en uso');
  });

  it('el servidor debería lanzar error si el email ya está registrado', async () => {
    const serverError = { error: { message: 'Email ya en uso' } };
    mockUserService.addUser.and.returnValue(Promise.reject(serverError));
    await expectAsync(
      service.registerUser({ username: 'nuevo', password: 'Pass1234!', repeatPassword: 'Pass1234!', email: 'existe@e.com' })
    ).toBeRejectedWith('Email ya en uso');
  });


  it('debería devolver true solo si el rol coincide', () => {
    (service as any).user = { userId: 1, username: 'user', email: 'email', rol: { name: 'ADMIN' } };
    expect(service.hasRole('ADMIN')).toBeTrue();
    expect(service.hasRole('USUARIO')).toBeFalse();
  });

  it('validateRegistration debe detectar contraseñas no coincidentes', async () => {
    const { valid, errors } = await service.validateRegistration({
      username: 'user',
      email: 'e@e.com',
      password: 'pass1',
      repeatPassword: 'pass2',
    });

    expect(valid).toBeFalse();
    expect(errors['password']).toBe('Las contraseñas no coinciden.');
  });

  it('validateRegistration debe ser válido cuando las contraseñas coinciden', async () => {
    const { valid, errors } = await service.validateRegistration({
      username: 'user',
      email: 'e@e.com',
      password: 'Pass1234!',
      repeatPassword: 'Pass1234!',
    });
    expect(valid).toBeTrue();
    expect(errors).toEqual({});
  });

  it('debería cerrar sesión automáticamente cuando expire la sesión', async () => {
  // simulo como si se cerraramos la ventana modal
  const fakeResult: SweetAlertResult = {
    isConfirmed: true,
    isDenied: false,
    isDismissed: false
  };

  spyOn(Swal, 'fire').and.returnValue(Promise.resolve(fakeResult));
  spyOn(service, 'logout');

  // método de expiración
  (service as any).sessionExpired();

  await Promise.resolve();

  expect(Swal.fire).toHaveBeenCalledWith(jasmine.objectContaining({
    icon: 'info',
    title: 'Sesión expirada',
    text: 'Tu sesión ha caducado. Por favor, vuelve a iniciar sesión.',
    confirmButtonText: 'Aceptar'
  }));
  expect(service.logout).toHaveBeenCalled();
});


});
