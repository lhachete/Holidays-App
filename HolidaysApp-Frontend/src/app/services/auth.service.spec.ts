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
    spyOn<any>(service, 'scheduleSessionExpiration').and.stub();
  });

  it('debería devolver USER_ERROR si userService devuelve null', async () => {
    mockUserService.login.and.returnValue(Promise.resolve(null));
    const result = await service.login('user', 'pASSWORD');
    expect(result).toBe('USER_ERROR');
    expect(service.isAuthenticated).toBeFalse();
    expect(service.user).toBeNull();
  });

  it('debería devolver OK e iniciar sesión correctamente al hacer login', async () => {
    const payload = { exp: Math.floor(Date.now() / 1000) + 3600 };
    const fakeToken = `header.${btoa(JSON.stringify(payload))}.sig`;

    const foundUser = {
      userId: 1,
      username: 'usuario',
      email: 'a@b.com',
      name: 'Juan',
      lastName: 'Pérez',
      codeColor: '#123456',
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

  it('debería registrar un usuario nuevo con éxito', async () => {
    const newUser = {
      userId: 2,
      username: 'nuevo',
      email: 'n@e.com',
      name: 'Nombre',
      lastName: 'Apellido',
      codeColor: '#654321',
      rol: { id: 2, name: 'USUARIO' }
    };

    mockUserService.addUser.and.returnValue(Promise.resolve(newUser));

    const result = await service.registerUser({
      username: 'nuevo',
      password: 'Pass1234!',
      repeatPassword: 'Pass1234!',
      email: 'n@e.com',
      name: 'Nombre',
      lastName: 'Apellido',
      codeColor: '#654321'
    });

    expect(result).toEqual(newUser);
  });

  it('debería lanzar error si el registro falla con mensaje del servidor', async () => {
    const serverError = { error: { message: 'Error de registro' } };
    mockUserService.addUser.and.returnValue(Promise.reject(serverError));

    await expectAsync(
      service.registerUser({
        username: 'nuevo',
        password: 'Pass1234!',
        repeatPassword: 'Pass1234!',
        email: 'n@e.com',
        name: 'Nombre',
        lastName: 'Apellido',
        codeColor: '#123456'
      })
    ).toBeRejectedWith('Error de registro');
  });

  it('debería lanzar error si el nombre de usuario ya existe', async () => {
    const serverError = { error: { message: 'Nombre de usuario ya en uso' } };
    mockUserService.addUser.and.returnValue(Promise.reject(serverError));

    await expectAsync(
      service.registerUser({
        username: 'existe',
        password: 'Pass1234!',
        repeatPassword: 'Pass1234!',
        email: 'n@e.com',
        name: 'Nombre',
        lastName: 'Apellido',
        codeColor: '#123456'
      })
    ).toBeRejectedWith('Nombre de usuario ya en uso');
  });

  it('debería lanzar error si el email ya está registrado', async () => {
    const serverError = { error: { message: 'Email ya en uso' } };
    mockUserService.addUser.and.returnValue(Promise.reject(serverError));

    await expectAsync(
      service.registerUser({
        username: 'nuevo',
        password: 'Pass1234!',
        repeatPassword: 'Pass1234!',
        email: 'existe@e.com',
        name: 'Nombre',
        lastName: 'Apellido',
        codeColor: '#123456'
      })
    ).toBeRejectedWith('Email ya en uso');
  });

  it('debería devolver true solo si el rol coincide', () => {
    (service as any).user = {
      userId: 1,
      username: 'user',
      email: 'email',
      name: 'Nombre',
      lastName: 'Apellido',
      codeColor: '#000000',
      rol: { name: 'ADMIN' }
    };

    expect(service.hasRole('ADMIN')).toBeTrue();
    expect(service.hasRole('USUARIO')).toBeFalse();
  });

  it('validatePasswords debería detectar contraseñas no coincidentes', async () => {
    const { valid, errors } = await service.validatePasswords({
      password: 'pass1',
      repeatPassword: 'pass2'
    });

    expect(valid).toBeFalse();
    expect(errors['password']).toBe('Las contraseñas no coinciden.');
  });

  it('validatePasswords debería ser válido cuando las contraseñas coinciden', async () => {
    const { valid, errors } = await service.validatePasswords({
      password: 'Pass1234!',
      repeatPassword: 'Pass1234!'
    });

    expect(valid).toBeTrue();
    expect(errors).toEqual({});
  });

  it('debería cerrar sesión automáticamente cuando expire la sesión', async () => {
    const fakeResult: SweetAlertResult = {
      isConfirmed: true,
      isDenied: false,
      isDismissed: false
    };

    spyOn(Swal, 'fire').and.returnValue(Promise.resolve(fakeResult));
    spyOn(service, 'logout');

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
