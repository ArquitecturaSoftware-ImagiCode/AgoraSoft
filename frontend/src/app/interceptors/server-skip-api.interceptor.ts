import { HttpInterceptorFn, HttpResponse } from '@angular/common/http';
import { inject, PLATFORM_ID } from '@angular/core';
import { isPlatformServer } from '@angular/common';
import { of } from 'rxjs';

/**
 * Interceptor que bloquea (en servidor) las llamadas a /api para que
 * el prerender no intente llegar al backend. En el navegador NO interviene.
 */
export const serverSkipApiInterceptor: HttpInterceptorFn = (req, next) => {
  const platformId = inject(PLATFORM_ID);

  // Solo actuar en el servidor (SSR/prerender)
  if (isPlatformServer(platformId)) {

    // Normaliza URL para cubrir casos absolutos (http://localhost:8080/api/...)
    const url = req.url.replace(/^https?:\/\/[^/]+/, '');

    if (url.startsWith('/api/')) {
      // Mock básico según método (puedes afinarlo)
      if (req.method === 'GET') {
        return of(new HttpResponse({
          status: 200,
          // Ajusta el mock si necesitas estructura concreta
          body: []
        }));
      }
      // Para POST / PUT / DELETE devolvemos 200 sin contenido
      return of(new HttpResponse({
        status: 200,
        body: null
      }));
    }
  }

  // En el navegador o si no es /api → pasa normal
  return next(req);
};
