export interface IProxyServers {
  id: number;
  ipAddress?: string | null;
  port?: number | null;
  country?: string | null;
  protocol?: string | null;
  anonymity?: string | null;
  organization?: string | null;
  speed?: number | null;
  responseTime?: number | null;
  successCount?: number | null;
  failCount?: number | null;
}

export type NewProxyServers = Omit<IProxyServers, 'id'> & { id: null };
