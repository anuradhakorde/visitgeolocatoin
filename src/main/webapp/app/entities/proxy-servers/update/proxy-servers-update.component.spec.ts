import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ProxyServersService } from '../service/proxy-servers.service';
import { IProxyServers } from '../proxy-servers.model';
import { ProxyServersFormService } from './proxy-servers-form.service';

import { ProxyServersUpdateComponent } from './proxy-servers-update.component';

describe('ProxyServers Management Update Component', () => {
  let comp: ProxyServersUpdateComponent;
  let fixture: ComponentFixture<ProxyServersUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let proxyServersFormService: ProxyServersFormService;
  let proxyServersService: ProxyServersService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ProxyServersUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ProxyServersUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProxyServersUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    proxyServersFormService = TestBed.inject(ProxyServersFormService);
    proxyServersService = TestBed.inject(ProxyServersService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const proxyServers: IProxyServers = { id: 456 };

      activatedRoute.data = of({ proxyServers });
      comp.ngOnInit();

      expect(comp.proxyServers).toEqual(proxyServers);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProxyServers>>();
      const proxyServers = { id: 123 };
      jest.spyOn(proxyServersFormService, 'getProxyServers').mockReturnValue(proxyServers);
      jest.spyOn(proxyServersService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ proxyServers });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: proxyServers }));
      saveSubject.complete();

      // THEN
      expect(proxyServersFormService.getProxyServers).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(proxyServersService.update).toHaveBeenCalledWith(expect.objectContaining(proxyServers));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProxyServers>>();
      const proxyServers = { id: 123 };
      jest.spyOn(proxyServersFormService, 'getProxyServers').mockReturnValue({ id: null });
      jest.spyOn(proxyServersService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ proxyServers: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: proxyServers }));
      saveSubject.complete();

      // THEN
      expect(proxyServersFormService.getProxyServers).toHaveBeenCalled();
      expect(proxyServersService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProxyServers>>();
      const proxyServers = { id: 123 };
      jest.spyOn(proxyServersService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ proxyServers });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(proxyServersService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
