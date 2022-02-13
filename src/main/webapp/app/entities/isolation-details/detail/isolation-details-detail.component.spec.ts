import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IsolationDetailsDetailComponent } from './isolation-details-detail.component';

describe('IsolationDetails Management Detail Component', () => {
  let comp: IsolationDetailsDetailComponent;
  let fixture: ComponentFixture<IsolationDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IsolationDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ isolationDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(IsolationDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(IsolationDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load isolationDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.isolationDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
