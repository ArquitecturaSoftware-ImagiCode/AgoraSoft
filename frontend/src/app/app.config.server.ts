import { mergeApplicationConfig, ApplicationConfig } from '@angular/core';
import { provideServerRendering, withRoutes } from '@angular/ssr';
import { appConfig } from './app.config';
import { serverRoutes } from './app.routes.server';

import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { serverSkipApiInterceptor } from './interceptors/server-skip-api.interceptor';

const serverConfig: ApplicationConfig = {
  providers: [
    provideServerRendering(withRoutes(serverRoutes)),
    provideHttpClient(withInterceptors([serverSkipApiInterceptor])),
  ]
};

export const config = mergeApplicationConfig(appConfig, serverConfig);
