import { IProxyServers, NewProxyServers } from './proxy-servers.model';

export const sampleWithRequiredData: IProxyServers = {
  id: 25580,
};

export const sampleWithPartialData: IProxyServers = {
  id: 9096,
  ipAddress: 'waterskiing sans verbally',
  port: 14396,
  country: 'Tajikistan',
  protocol: 'aboard kissingly',
  anonymity: 'whether about',
  responseTime: 13299,
  successCount: 17031,
  failCount: 291,
};

export const sampleWithFullData: IProxyServers = {
  id: 20189,
  ipAddress: 'onto merrily gut',
  port: 25877,
  country: 'Ghana',
  protocol: 'indeed',
  anonymity: 'energetically than behind',
  organization: 'gauntlet individuate adventurously',
  speed: 15123,
  responseTime: 24450,
  successCount: 25487,
  failCount: 8229,
};

export const sampleWithNewData: NewProxyServers = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
