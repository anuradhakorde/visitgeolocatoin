import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ProxyServersComponent } from './list/proxy-servers.component';
import { ProxyServersDetailComponent } from './detail/proxy-servers-detail.component';
import { ProxyServersUpdateComponent } from './update/proxy-servers-update.component';
import ProxyServersResolve from './route/proxy-servers-routing-resolve.service';

const proxyServersRoute: Routes = [
  {
    path: '',
    component: ProxyServersComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProxyServersDetailComponent,
    resolve: {
      proxyServers: ProxyServersResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProxyServersUpdateComponent,
    resolve: {
      proxyServers: ProxyServersResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProxyServersUpdateComponent,
    resolve: {
      proxyServers: ProxyServersResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default proxyServersRoute;
