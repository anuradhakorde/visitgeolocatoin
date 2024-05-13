import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IProxyServers } from '../proxy-servers.model';

@Component({
  standalone: true,
  selector: 'jhi-proxy-servers-detail',
  templateUrl: './proxy-servers-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ProxyServersDetailComponent {
  proxyServers = input<IProxyServers | null>(null);

  previousState(): void {
    window.history.back();
  }
}
