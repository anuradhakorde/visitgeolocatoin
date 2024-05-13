import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProxyServers } from '../proxy-servers.model';
import { ProxyServersService } from '../service/proxy-servers.service';

const proxyServersResolve = (route: ActivatedRouteSnapshot): Observable<null | IProxyServers> => {
  const id = route.params['id'];
  if (id) {
    return inject(ProxyServersService)
      .find(id)
      .pipe(
        mergeMap((proxyServers: HttpResponse<IProxyServers>) => {
          if (proxyServers.body) {
            return of(proxyServers.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default proxyServersResolve;
