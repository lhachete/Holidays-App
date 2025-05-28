import { bootstrapApplication } from '@angular/platform-browser';
import { provideAnimations } from '@angular/platform-browser/animations';
import { importProvidersFrom, LOCALE_ID } from '@angular/core';
import { registerLocaleData } from '@angular/common';
import localeEs from '@angular/common/locales/es';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { provideHttpClient } from '@angular/common/http';
import { AppComponent } from './app/app.component';
import { appConfig } from './app/app.config';

import { InMemoryWebApiModule } from 'angular-in-memory-web-api';
import { InMemoryDataService } from './app/services/in-memory-data.service';


registerLocaleData(localeEs, 'es-ES');

bootstrapApplication(AppComponent, {
  providers: [
    provideAnimations(),
    provideHttpClient(),

    importProvidersFrom(
      CalendarModule.forRoot({
        provide: DateAdapter,
        useFactory: adapterFactory
      }),

      InMemoryWebApiModule.forRoot(InMemoryDataService, {
        dataEncapsulation: false,    // devuelve el array “directo”, no { data: [...] }
        passThruUnknownUrl:  true,    // deja pasar otras URLs reales
        apiBase: "api/"
      })
    ),

    { provide: LOCALE_ID, useValue: 'es-ES' },
    ...(appConfig.providers || [])
  ]
})
.catch(err => console.error(err));
