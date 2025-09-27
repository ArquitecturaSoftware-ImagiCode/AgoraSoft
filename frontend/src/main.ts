import { bootstrapApplication } from '@angular/platform-browser';
import { UsuarioPagina } from './app/pages/usuario-pagina/usuario-pagina';
import { appConfig } from './app/app.config';

bootstrapApplication(UsuarioPagina, appConfig)
  .catch(err => console.error(err));
