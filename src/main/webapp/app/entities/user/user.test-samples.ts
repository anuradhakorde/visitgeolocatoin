import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 26308,
  login: 'jUR@1\\db\\yU',
};

export const sampleWithPartialData: IUser = {
  id: 5230,
  login: 'Uj',
};

export const sampleWithFullData: IUser = {
  id: 8043,
  login: '.',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
