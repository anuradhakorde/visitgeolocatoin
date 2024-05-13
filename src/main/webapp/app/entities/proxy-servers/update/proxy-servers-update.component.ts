import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IProxyServers } from '../proxy-servers.model';
import { ProxyServersService } from '../service/proxy-servers.service';
import { ProxyServersFormService, ProxyServersFormGroup } from './proxy-servers-form.service';

@Component({
  standalone: true,
  selector: 'jhi-proxy-servers-update',
  templateUrl: './proxy-servers-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ProxyServersUpdateComponent implements OnInit {
  isSaving = false;
  proxyServers: IProxyServers | null = null;

  protected proxyServersService = inject(ProxyServersService);
  protected proxyServersFormService = inject(ProxyServersFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ProxyServersFormGroup = this.proxyServersFormService.createProxyServersFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ proxyServers }) => {
      this.proxyServers = proxyServers;
      if (proxyServers) {
        this.updateForm(proxyServers);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const proxyServers = this.proxyServersFormService.getProxyServers(this.editForm);
    if (proxyServers.id !== null) {
      this.subscribeToSaveResponse(this.proxyServersService.update(proxyServers));
    } else {
      this.subscribeToSaveResponse(this.proxyServersService.create(proxyServers));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProxyServers>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(proxyServers: IProxyServers): void {
    this.proxyServers = proxyServers;
    this.proxyServersFormService.resetForm(this.editForm, proxyServers);
  }
}
