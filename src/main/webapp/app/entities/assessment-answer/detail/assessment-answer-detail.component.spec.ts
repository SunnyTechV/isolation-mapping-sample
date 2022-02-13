import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AssessmentAnswerDetailComponent } from './assessment-answer-detail.component';

describe('AssessmentAnswer Management Detail Component', () => {
  let comp: AssessmentAnswerDetailComponent;
  let fixture: ComponentFixture<AssessmentAnswerDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AssessmentAnswerDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ assessmentAnswer: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AssessmentAnswerDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AssessmentAnswerDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load assessmentAnswer on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.assessmentAnswer).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
