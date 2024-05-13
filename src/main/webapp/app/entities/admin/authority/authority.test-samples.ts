import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: 'e6f062ca-3003-40d9-82a3-c782c371766d',
};

export const sampleWithPartialData: IAuthority = {
  name: '930aefc2-3d03-4500-871e-5bf355343689',
};

export const sampleWithFullData: IAuthority = {
  name: 'eaabf677-9080-4db7-b7cc-28d015c2e4eb',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
