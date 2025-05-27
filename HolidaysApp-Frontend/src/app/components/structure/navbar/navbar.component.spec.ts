import { TestBed } from '@angular/core/testing';
import { NavbarComponent } from './navbar.component';
import { AuthService } from '../../../services/auth.service';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';
import { provideLocationMocks } from '@angular/common/testing';

describe('NavbarComponent', () => {
  let fixture: ReturnType<typeof TestBed.createComponent<NavbarComponent>>;
  let component: NavbarComponent;
  let compiled: HTMLElement;
  let mockAuthService: {
    isLoggedIn: boolean;
    user: { username: string };
    logout: jasmine.Spy;
  };

  // Helper para configurar el TestBed según el estado de autenticación
  const setup = async (isLoggedIn: boolean, username = '') => {
    mockAuthService = {
      isLoggedIn,
      user: { username },
      logout: jasmine.createSpy('logout'),
    };

    await TestBed.configureTestingModule({
      imports: [NavbarComponent],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        provideRouter([]),
        provideLocationMocks(),
        { provide: AuthService, useValue: mockAuthService },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(NavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    compiled = fixture.nativeElement as HTMLElement;
  };

  describe('cuando NO está autenticado', () => {
    beforeEach(async () => await setup(false));

    it('debe crearse el componente', () => {
      expect(component).toBeTruthy();
    });

    it('contiene enlace a página de inicio', () => {
      expect(compiled.querySelector('a[routerLink="/"]')).toBeTruthy();
    });

    it('muestra enlace a iniciar sesión', () => {
      expect(compiled.querySelector('a[routerLink="/login"]')).toBeTruthy();
    });

    it('NO muestra el dropdown de perfil', () => {
      expect(compiled.querySelector('#profileDropdown')).toBeNull();
    });
  });

  describe('cuando SÍ está autenticado', () => {
    const USERNAME = 'JuanPerez';

    beforeEach(async () => await setup(true, USERNAME));

    it('debe mostrar el nombre de usuario', () => {
      expect(compiled.textContent).toContain(USERNAME);
    });

    it('contiene enlace a perfil', () => {
      expect(compiled.querySelector('a[routerLink="/profile"]')).toBeTruthy();
    });

    it('contiene enlace de cerrar sesión y llama a logout al hacer click', () => {
      const logoutEl = compiled.querySelector(
        'a.dropdown-item.cursor-pointer'
      ) as HTMLElement;
      logoutEl.click();
      expect(mockAuthService.logout).toHaveBeenCalled();
    });

    it('muestra los iconos de FontAwesome', () => {
      const icons = compiled.querySelectorAll('fa-icon');
      expect(icons.length).toBeGreaterThan(0);
    });

    it('muestra el menú desplegable de "Mis vacaciones"', () => {
      expect(compiled.querySelector('#vacationDropdown')).toBeTruthy();
    });

    it('tiene los enlaces de ver, guardar, editar y borrar vacaciones en el dropdown', () => {
      const expectedLinks = [
        '/vacation/show',
        '/vacation/book',
        '/vacation/edit',
        '/vacation/delete'
      ];
      expectedLinks.forEach(path => {
        expect(
          compiled.querySelector(`a[routerLink="${path}"]`)
        ).toBeTruthy();
      });
    });
  });

});