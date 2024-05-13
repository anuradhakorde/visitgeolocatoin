import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../proxy-servers.test-samples';

import { ProxyServersFormService } from './proxy-servers-form.service';

describe('ProxyServers Form Service', () => {
  let service: ProxyServersFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProxyServersFormService);
  });

  describe('Service methods', () => {
    describe('createProxyServersFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProxyServersFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ipAddress: expect.any(Object),
            port: expect.any(Object),
            country: expect.any(Object),
            protocol: expect.any(Object),
            anonymity: expect.any(Object),
            organization: expect.any(Object),
            speed: expect.any(Object),
            responseTime: expect.any(Object),
            successCount: expect.any(Object),
            failCount: expect.any(Object),
          }),
        );
      });

      it('passing IProxyServers should create a new form with FormGroup', () => {
        const formGroup = service.createProxyServersFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ipAddress: expect.any(Object),
            port: expect.any(Object),
            country: expect.any(Object),
            protocol: expect.any(Object),
            anonymity: expect.any(Object),
            organization: expect.any(Object),
            speed: expect.any(Object),
            responseTime: expect.any(Object),
            successCount: expect.any(Object),
            failCount: expect.any(Object),
          }),
        );
      });
    });

    describe('getProxyServers', () => {
      it('should return NewProxyServers for default ProxyServers initial value', () => {
        const formGroup = service.createProxyServersFormGroup(sampleWithNewData);

        const proxyServers = service.getProxyServers(formGroup) as any;

        expect(proxyServers).toMatchObject(sampleWithNewData);
      });

      it('should return NewProxyServers for empty ProxyServers initial value', () => {
        const formGroup = service.createProxyServersFormGroup();

        const proxyServers = service.getProxyServers(formGroup) as any;

        expect(proxyServers).toMatchObject({});
      });

      it('should return IProxyServers', () => {
        const formGroup = service.createProxyServersFormGroup(sampleWithRequiredData);

        const proxyServers = service.getProxyServers(formGroup) as any;

        expect(proxyServers).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProxyServers should not enable id FormControl', () => {
        const formGroup = service.createProxyServersFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProxyServers should disable id FormControl', () => {
        const formGroup = service.createProxyServersFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
