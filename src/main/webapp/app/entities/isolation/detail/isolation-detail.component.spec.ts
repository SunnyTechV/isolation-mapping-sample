import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IsolationDetailComponent } from './isolation-detail.component';

describe('Isolation Management Detail Component', () => {
  let comp: IsolationDetailComponent;
  let fixture: ComponentFixture<IsolationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IsolationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ isolation: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(IsolationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(IsolationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load isolation on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.isolation).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
