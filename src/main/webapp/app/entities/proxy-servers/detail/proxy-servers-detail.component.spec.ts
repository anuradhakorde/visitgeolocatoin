import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProxyServersDetailComponent } from './proxy-servers-detail.component';

describe('ProxyServers Management Detail Component', () => {
  let comp: ProxyServersDetailComponent;
  let fixture: ComponentFixture<ProxyServersDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProxyServersDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ProxyServersDetailComponent,
              resolve: { proxyServers: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ProxyServersDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProxyServersDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load proxyServers on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ProxyServersDetailComponent);

      // THEN
      expect(instance.proxyServers()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
