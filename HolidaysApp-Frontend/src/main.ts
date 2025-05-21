import { bootstrapApplication } from '@angular/platform-browser';
import { provideAnimations } from '@angular/platform-browser/animations';
import { importProvidersFrom } from '@angular/core';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory }           from 'angular-calendar/date-adapters/date-fns';
import { provideHttpClient }      from '@angular/common/http';
import { AppComponent } from './app/app.component';
import { appConfig }    from './app/app.config';

import { InMemoryWebApiModule } from 'angular-in-memory-web-api';
import { InMemoryDataService } from './app/services/in-memory-data.service';

bootstrapApplication(AppComponent, {
  providers: [
    // Para que angular-calendar pueda usar animaciones
    provideAnimations(),
    provideHttpClient(),
    

    importProvidersFrom(

      CalendarModule.forRoot({
        provide: DateAdapter,
        useFactory: adapterFactory
      }),

      /* InMemoryWebApiModule.forRoot(InMemoryDataService, {
        dataEncapsulation: false,    // devuelve el array “directo”, no { data: [...] }
        passThruUnknownUrl:  true,    // deja pasar otras URLs reales
        apiBase: "api/"
      }) */
    ),

    ...(appConfig.providers || [])
  ]
})
.catch(err => console.error(err));
