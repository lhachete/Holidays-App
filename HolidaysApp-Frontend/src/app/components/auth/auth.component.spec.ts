import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthComponent } from './auth.component';

describe('AuthComponent', () => {
  let auth: AuthComponent;
  let fixture: ComponentFixture<AuthComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AuthComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuthComponent);
    auth = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('Creación del componente', () => {
    expect(auth).toBeTruthy();
  });

  it('Debe inicializar el modo de autenticación', () => {
    expect(auth.mode).toBe('login');
  });

  it('Debe cambiar al modo de registro', () => {
    auth.mode = 'register';
    expect(auth.mode).toBe('register');
  });

  it('Debe cambiar al modo de inicio de sesión', () => {
    auth.mode = 'login';
    expect(auth.mode).toBe('login');
  });

  it('Debe tener un formulario de inicio de sesión válido', () => {
    expect(auth.loginForm.valid).toBeTrue();
  });

  it('Debe tener un formulario de registro válido', () => {
    expect(auth.registerForm.valid).toBeTrue();
  });

  it('Debe tener un formulario de registro inválido si el nombre de usuario está vacío', () => {
    auth.registerForm.controls['username'].setValue('');
    expect(auth.registerForm.valid).toBeFalse();
  });

  it('Debe tener un formulario de registro inválido si el correo electrónico está vacío', () => {
    auth.registerForm.controls['email'].setValue('');
    expect(auth.registerForm.valid).toBeFalse();
  });

  it('Debe tener un formulario de registro inválido si la contraseña está vacía', () => {
    auth.registerForm.controls['password'].setValue('');
    expect(auth.registerForm.valid).toBeFalse();
  });

  it('Debe tener un formulario de registro inválido si la confirmación de contraseña está vacía', () => {
    auth.registerForm.controls['confirmPassword'].setValue('');
    expect(auth.registerForm.valid).toBeFalse();
  });

  it('Debe validar que las contraseñas coinciden', () => {
    auth.registerForm.controls['password'].setValue('Password123!');
    auth.registerForm.controls['confirmPassword'].setValue('Password123!');
    expect(auth.registerForm.valid).toBeTrue();
  });

  it('Debe mostrar un error si las contraseñas no coinciden', () => {
    auth.registerForm.controls['password'].setValue('Password123!');
    auth.registerForm.controls['confirmPassword'].setValue('DifferentPassword!');
    expect(auth.registerForm.valid).toBeFalse();
  });

  it('Debe mostrar mensajes de error para campos inválidos', () => {
    auth.loginForm.controls['userInput'].setValue('');
    auth.loginForm.controls['password'].setValue('');
    expect(auth.getErrorMessages('userInput')).toContain('El campo es obligatorio');
    expect(auth.getErrorMessages('password')).toContain('El campo es obligatorio');
  });

  it('Debe mostrar mensajes de error personalizados', () => {
    auth.registerErrors = { username: 'El nombre de usuario ya está en uso' };
    expect(auth.getErrorMessages('username')).toContain('El nombre de usuario ya está en uso');
  });

  it('Debe iniciar sesión correctamente', async () => {
    spyOn(auth.authService, 'login').and.returnValue(Promise.resolve('OK'));
    await auth.login();
    expect(auth.router.navigateByUrl).toHaveBeenCalledWith('/vacation/show');
  });

  it('Debe manejar error de usuario no encontrado al iniciar sesión', async () => {
    spyOn(auth.authService, 'login').and.returnValue(Promise.resolve('USER_ERROR'));
    await auth.login();
    expect(auth.userNotFound).toBeTrue();
  });
  
  /* it('Debe registrar un nuevo usuario correctamente', async () => {
    spyOn(auth.authService, 'registerUser').and.returnValue(Promise.resolve({ id: 10, username: 'testuser', email: 'test@email.com', role: { id: 2, name: 'USUARIO' }, password: 'Test123!' }));
    await auth.register();
    expect(auth.router.navigateByUrl).toHaveBeenCalledWith('/login');
    expect(auth.registerErrors).toEqual({});
    expect(auth.registerForm.valid).toBeTrue();
    expect(auth.registerForm.value.username).toBe('testuser'); */
    




});
