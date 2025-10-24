import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';

// Angular Material moduli
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';

// Tvoje rute
import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),                      // Router provider
    provideHttpClient(),                        // HTTP klijent za servise
    importProvidersFrom(
      MatDatepickerModule,
      MatNativeDateModule
    ),                                          // Material datepicker provider
  ],
};
