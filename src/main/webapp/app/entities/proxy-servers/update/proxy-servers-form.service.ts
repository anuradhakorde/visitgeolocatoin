import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProxyServers, NewProxyServers } from '../proxy-servers.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProxyServers for edit and NewProxyServersFormGroupInput for create.
 */
type ProxyServersFormGroupInput = IProxyServers | PartialWithRequiredKeyOf<NewProxyServers>;

type ProxyServersFormDefaults = Pick<NewProxyServers, 'id'>;

type ProxyServersFormGroupContent = {
  id: FormControl<IProxyServers['id'] | NewProxyServers['id']>;
  ipAddress: FormControl<IProxyServers['ipAddress']>;
  port: FormControl<IProxyServers['port']>;
  country: FormControl<IProxyServers['country']>;
  protocol: FormControl<IProxyServers['protocol']>;
  anonymity: FormControl<IProxyServers['anonymity']>;
  organization: FormControl<IProxyServers['organization']>;
  speed: FormControl<IProxyServers['speed']>;
  responseTime: FormControl<IProxyServers['responseTime']>;
  successCount: FormControl<IProxyServers['successCount']>;
  failCount: FormControl<IProxyServers['failCount']>;
};

export type ProxyServersFormGroup = FormGroup<ProxyServersFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProxyServersFormService {
  createProxyServersFormGroup(proxyServers: ProxyServersFormGroupInput = { id: null }): ProxyServersFormGroup {
    const proxyServersRawValue = {
      ...this.getFormDefaults(),
      ...proxyServers,
    };
    return new FormGroup<ProxyServersFormGroupContent>({
      id: new FormControl(
        { value: proxyServersRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      ipAddress: new FormControl(proxyServersRawValue.ipAddress),
      port: new FormControl(proxyServersRawValue.port),
      country: new FormControl(proxyServersRawValue.country),
      protocol: new FormControl(proxyServersRawValue.protocol),
      anonymity: new FormControl(proxyServersRawValue.anonymity),
      organization: new FormControl(proxyServersRawValue.organization),
      speed: new FormControl(proxyServersRawValue.speed),
      responseTime: new FormControl(proxyServersRawValue.responseTime),
      successCount: new FormControl(proxyServersRawValue.successCount),
      failCount: new FormControl(proxyServersRawValue.failCount),
    });
  }

  getProxyServers(form: ProxyServersFormGroup): IProxyServers | NewProxyServers {
    return form.getRawValue() as IProxyServers | NewProxyServers;
  }

  resetForm(form: ProxyServersFormGroup, proxyServers: ProxyServersFormGroupInput): void {
    const proxyServersRawValue = { ...this.getFormDefaults(), ...proxyServers };
    form.reset(
      {
        ...proxyServersRawValue,
        id: { value: proxyServersRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ProxyServersFormDefaults {
    return {
      id: null,
    };
  }
}
