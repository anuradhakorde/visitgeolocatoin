import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProxyServers, NewProxyServers } from '../proxy-servers.model';

export type PartialUpdateProxyServers = Partial<IProxyServers> & Pick<IProxyServers, 'id'>;

export type EntityResponseType = HttpResponse<IProxyServers>;
export type EntityArrayResponseType = HttpResponse<IProxyServers[]>;

@Injectable({ providedIn: 'root' })
export class ProxyServersService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/proxy-servers');

  create(proxyServers: NewProxyServers): Observable<EntityResponseType> {
    return this.http.post<IProxyServers>(this.resourceUrl, proxyServers, { observe: 'response' });
  }

  update(proxyServers: IProxyServers): Observable<EntityResponseType> {
    return this.http.put<IProxyServers>(`${this.resourceUrl}/${this.getProxyServersIdentifier(proxyServers)}`, proxyServers, {
      observe: 'response',
    });
  }

  partialUpdate(proxyServers: PartialUpdateProxyServers): Observable<EntityResponseType> {
    return this.http.patch<IProxyServers>(`${this.resourceUrl}/${this.getProxyServersIdentifier(proxyServers)}`, proxyServers, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProxyServers>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProxyServers[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProxyServersIdentifier(proxyServers: Pick<IProxyServers, 'id'>): number {
    return proxyServers.id;
  }

  compareProxyServers(o1: Pick<IProxyServers, 'id'> | null, o2: Pick<IProxyServers, 'id'> | null): boolean {
    return o1 && o2 ? this.getProxyServersIdentifier(o1) === this.getProxyServersIdentifier(o2) : o1 === o2;
  }

  addProxyServersToCollectionIfMissing<Type extends Pick<IProxyServers, 'id'>>(
    proxyServersCollection: Type[],
    ...proxyServersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const proxyServers: Type[] = proxyServersToCheck.filter(isPresent);
    if (proxyServers.length > 0) {
      const proxyServersCollectionIdentifiers = proxyServersCollection.map(proxyServersItem =>
        this.getProxyServersIdentifier(proxyServersItem),
      );
      const proxyServersToAdd = proxyServers.filter(proxyServersItem => {
        const proxyServersIdentifier = this.getProxyServersIdentifier(proxyServersItem);
        if (proxyServersCollectionIdentifiers.includes(proxyServersIdentifier)) {
          return false;
        }
        proxyServersCollectionIdentifiers.push(proxyServersIdentifier);
        return true;
      });
      return [...proxyServersToAdd, ...proxyServersCollection];
    }
    return proxyServersCollection;
  }
}
