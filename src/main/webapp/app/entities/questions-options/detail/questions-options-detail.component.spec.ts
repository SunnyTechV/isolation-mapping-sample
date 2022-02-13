import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QuestionsOptionsDetailComponent } from './questions-options-detail.component';

describe('QuestionsOptions Management Detail Component', () => {
  let comp: QuestionsOptionsDetailComponent;
  let fixture: ComponentFixture<QuestionsOptionsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QuestionsOptionsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ questionsOptions: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(QuestionsOptionsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(QuestionsOptionsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load questionsOptions on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.questionsOptions).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
