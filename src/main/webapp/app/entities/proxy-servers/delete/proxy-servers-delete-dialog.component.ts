import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IProxyServers } from '../proxy-servers.model';
import { ProxyServersService } from '../service/proxy-servers.service';

@Component({
  standalone: true,
  templateUrl: './proxy-servers-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ProxyServersDeleteDialogComponent {
  proxyServers?: IProxyServers;

  protected proxyServersService = inject(ProxyServersService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.proxyServersService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
