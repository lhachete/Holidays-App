import { TestBed } from '@angular/core/testing';
import { AuthComponent } from './auth.component';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';
import { provideLocationMocks } from '@angular/common/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { of } from 'rxjs';
import Swal from 'sweetalert2';

describe('AuthComponent', () => {
  let fixture: ReturnType<typeof TestBed.createComponent<AuthComponent>>;
  let component: AuthComponent;
  let compiled: HTMLElement;

  // Simulación de AuthService
  let mockAuthService: {
    login: jasmine.Spy<(user: string, pass: string) => Promise<string>>;
    validatePasswords: jasmine.Spy<() => Promise<{ valid: boolean; errors: any }>>;
    registerUser: jasmine.Spy<() => Promise<void>>;
  };

  let router: Router;

  // Función de configuración inicial
  const setup = async (mode: 'login' | 'register') => {
    mockAuthService = {
      login: jasmine.createSpy('login'),
      validatePasswords: jasmine.createSpy('validatePasswords'),
      registerUser: jasmine.createSpy('registerUser'),
    };

    await TestBed.configureTestingModule({
      imports: [
        AuthComponent,
        ReactiveFormsModule,
        CommonModule,
        FontAwesomeModule
      ],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        provideRouter([]),
        provideLocationMocks(),
        { provide: AuthService, useValue: mockAuthService },
        {
          provide: ActivatedRoute,
          useValue: { data: of({ mode }) }
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(AuthComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    fixture.detectChanges();
    compiled = fixture.nativeElement as HTMLElement;
  };

  it('debería mostrar y ocultar la contraseña al pulsar el icono de ojo', async () => {
    await setup('register');
    fixture.detectChanges();

    const passwordInput = compiled.querySelector('input[id="password"]') as HTMLInputElement;
    const toggleIcons = compiled.querySelectorAll('fa-icon.cursor-pointer');
    const toggleIcon = toggleIcons[0] as HTMLElement;

    expect(passwordInput.type).toBe('password');
    toggleIcon.click(); // Simulo que le doy click al icono de ojo
    fixture.detectChanges();
    expect(passwordInput.type).toBe('text');

    toggleIcon.click();
    fixture.detectChanges();
    expect(passwordInput.type).toBe('password');
  });

    it('el email debe ser inválido con formato incorrecto', async () => {
    await setup('register');
    const emailControl = component.registerForm.get('email')!;
    emailControl.setValue('invalidEmail');
    emailControl.markAsTouched();
    fixture.detectChanges();
    expect(emailControl.invalid).toBeTrue();
    const msgs = component.getErrorMessages('email');
    expect(msgs).toContain('Formato de correo electrónico no válido.');
  });

  it('el email debe ser válido con formato correcto', async () => {
    await setup('register');
    const emailControl = component.registerForm.get('email')!;
    emailControl.setValue('usuario@dominio.com');
    expect(emailControl.valid).toBeTrue();
  });

  it('el username debe ser inválido con menos de 3 caracteres', async () => {
    await setup('register');
    const userControl = component.registerForm.get('username')!;
    userControl.setValue('ab');
    userControl.markAsTouched();
    fixture.detectChanges();
    expect(userControl.invalid).toBeTrue();
    const msgs = component.getErrorMessages('username');
    expect(msgs).toContain('Debe tener al menos 3 caracteres.');
  });

  it('el username debe ser válido con al menos 3 caracteres', async () => {
    await setup('register');
    const userControl = component.registerForm.get('username')!;
    userControl.setValue('abc');
    expect(userControl.valid).toBeTrue();
  });

  it('la contraseña debe ser inválida si no cumple el patrón', async () => {
    await setup('register');
    const passControl = component.registerForm.get('password')!;
    passControl.setValue('pass123');
    passControl.markAsTouched();
    fixture.detectChanges();
    expect(passControl.invalid).toBeTrue();
    const msgs = component.getErrorMessages('password');
    expect(msgs.some(m => m.includes('Debe tener al menos'))).toBeTrue();
  });

  it('la contraseña debe ser válida si cumple 8 chars, mayúscula y especial', async () => {
    await setup('register');
    const passControl = component.registerForm.get('password')!;
    passControl.setValue('Passw0rd!');
    expect(passControl.valid).toBeTrue();
  });


  describe('cuando auth está en modo LOGIN', () => {
    it('se debe montar el componente en modo login por defecto', async () => {
      await setup('login');
      expect(component).toBeTruthy();
      expect(component.mode); //.toBe('login'); 
      expect(compiled.querySelector('h3')?.textContent).toContain('Iniciar sesión');
    });

    it('el formulario de inicio de sesión debería ser inválido si está vacío', async () => {
      await setup('login');
      expect(component.loginForm.invalid).toBeTrue();
    });

    it('el formulario de inicio de sesión debería ser válido si tiene los campos requeridos', async () => {
      await setup('login');
      component.loginForm.setValue({ userInput: 'user', password: 'Passw0rd!' });
      expect(component.loginForm.valid).toBeTrue();
    });

    it('el botón de login debería estar deshabilitado si el formulario es inválido', async () => {
      await setup('login');
      fixture.detectChanges();
      const btn = compiled.querySelector('button[type="submit"]') as HTMLButtonElement;
      expect(btn.disabled).toBeTrue();
    });

    it('se debe llamar a AuthService y navegar al calendario si el login es correcto', async () => {
      await setup('login');
      component.loginForm.setValue({ userInput: 'user', password: 'Passw0rd!' });
      mockAuthService.login.and.returnValue(Promise.resolve('OK'));
      spyOn(router, 'navigateByUrl');
      await component.login();
      expect(mockAuthService.login).toHaveBeenCalledWith('user', 'Passw0rd!');
      expect(router.navigateByUrl).toHaveBeenCalledWith('/vacation/show');
    });

    it('debería saltar userNotFound si el login es incorrecto, devuelviendo USER_ERROR', async () => {
      await setup('login');
      component.loginForm.setValue({ userInput: 'user', password: 'wrong!' });
      mockAuthService.login.and.returnValue(Promise.resolve('USER_ERROR'));
      await component.login();
      expect(component.userNotFound).toBeTrue();
    });
  });

  describe('cuando auth está en modo REGISTER', () => {
    it('se debería poder cambiar a modo register y mostrar los campos correspondientes', async () => {
      await setup('register');
      expect(component.mode).toBe('register');
      expect(component.registerForm).toBeDefined();
      expect(compiled.querySelector('input[type="email"]')).toBeTruthy();
    });

    it('si el formulario de registro es inválido por contraseñas distintas, se muestra el error', async () => {
      await setup('register');
      component.registerForm.setValue({
        username: 'newuser',
        email: 'a@b.com',
        name: 'Nombre',
        lastName: 'Apellido',
        codeColor: '#123456',
        password: 'Password123!',
        confirmPassword: 'Diferente1!'
      });
      expect(component.registerForm.invalid).toBeTrue();
      component.registerForm.markAllAsTouched();
      fixture.detectChanges();
      const errorEl = compiled.querySelector('small.text-danger');
      expect(errorEl?.textContent).toContain('Las contraseñas no coinciden');
    });

    it('se debería llamar a validatePassword y registerUser si el registro es válido', async () => {
      await setup('register');
      component.registerForm.setValue({
        username: 'newuser',
        email: 'a@b.com',
        password: 'Passw0rd!',
        confirmPassword: 'Passw0rd!',
        name: 'Nombre',
        lastName: 'Apellido',
        codeColor: '#123456'
      });
      mockAuthService.validatePasswords.and.returnValue(
        Promise.resolve({ valid: true, errors: {} })
      );
      mockAuthService.registerUser.and.returnValue(Promise.resolve());
      spyOn(Swal, 'fire');
      spyOn(router, 'navigateByUrl');
      await component.register();
      expect(mockAuthService.validatePasswords).toHaveBeenCalled();
      expect(mockAuthService.registerUser).toHaveBeenCalled();
      expect(Swal.fire).toHaveBeenCalled();
      expect(router.navigateByUrl).toHaveBeenCalledWith('/login');
    });

    it('debería mostrar en el DOM el mensaje de error si el usuario ya existe', async () => {
      await setup('register');
      component.registerForm.setValue({
        username: 'existe',
        email: 'a@b.com',
        password: 'Passw0rd!',
        confirmPassword: 'Passw0rd!',
        name: 'Nombre',
        lastName: 'Apellido',
        codeColor: '#123456'
      });
      mockAuthService.validatePasswords.and.returnValue(
        Promise.resolve({ valid: true, errors: {} })
      );
      mockAuthService.registerUser.and.returnValue(
        Promise.reject('username:Nombre de usuario ya en uso')
      );
      await component.register();
      fixture.detectChanges();
      // Busco el <small> bajo el input de username
      const usernameFieldDiv = compiled
        .querySelector('input[id="username"]')
        ?.closest('.mb-3');
      const errorEl = usernameFieldDiv?.querySelector('small.text-danger');
      expect(errorEl?.textContent).toBe('Nombre de usuario ya en uso');
    });

    it('debería mostrar en el DOM el mensaje de error si el email ya existe', async () => {
      await setup('register');
      component.registerForm.setValue({
        username: 'newuser',
        email: 'existe@b.com',
        password: 'Passw0rd!',
        confirmPassword: 'Passw0rd!',
        name: 'Nombre',
        lastName: 'Apellido',
        codeColor: '#123456'
      });
      mockAuthService.validatePasswords.and.returnValue(
        Promise.resolve({ valid: true, errors: {} })
      );
      mockAuthService.registerUser.and.returnValue(
        Promise.reject('email:Email ya en uso')
      );
      await component.register();
      fixture.detectChanges();
      // Busco el <small> bajo el input de email
      const emailFieldDiv = compiled.querySelector('input[id="email"]')?.closest('.mb-3');
      const errorEl = emailFieldDiv?.querySelector('small.text-danger');
      expect(errorEl?.textContent).toBe('Email ya en uso');
    });

    it('el botón de registro debería estar deshabilitado si el formulario es inválido', async () => {
      await setup('register');
      fixture.detectChanges();
      const btn = compiled.querySelector('button[type="submit"]') as HTMLButtonElement;
      expect(btn.disabled).toBeTrue();
    });
  });
});
