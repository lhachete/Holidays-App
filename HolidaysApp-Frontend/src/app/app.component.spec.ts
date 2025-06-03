import { TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';
import { provideLocationMocks } from '@angular/common/testing';

describe('AppComponent', () => {
  let fixture: ReturnType<typeof TestBed.createComponent<AppComponent>>;
  let component: AppComponent;
  let compiled: HTMLElement;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppComponent],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        provideRouter([]),
        provideLocationMocks(),
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    compiled = fixture.nativeElement as HTMLElement;
  });

  // Que el componente se cree correctamente
  it('debe crear la aplicación', () => {
    expect(component).toBeTruthy();
  });

  // Que el navbar se renderice correctamente
  it('debe renderizar la barra de navegación', () => {
    expect(compiled.querySelector('app-navbar')).toBeTruthy();
  });

  // Que el router outlet se renderice correctamente
  it('debe contener un router outlet para la navegación', () => {
    expect(compiled.querySelector('router-outlet')).toBeTruthy();
  });

});
