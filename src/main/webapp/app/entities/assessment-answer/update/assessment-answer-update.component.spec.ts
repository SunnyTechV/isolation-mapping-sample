import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AssessmentAnswerService } from '../service/assessment-answer.service';
import { IAssessmentAnswer, AssessmentAnswer } from '../assessment-answer.model';
import { IAssessment } from 'app/entities/assessment/assessment.model';
import { AssessmentService } from 'app/entities/assessment/service/assessment.service';
import { IQuestion } from 'app/entities/question/question.model';
import { QuestionService } from 'app/entities/question/service/question.service';

import { AssessmentAnswerUpdateComponent } from './assessment-answer-update.component';

describe('AssessmentAnswer Management Update Component', () => {
  let comp: AssessmentAnswerUpdateComponent;
  let fixture: ComponentFixture<AssessmentAnswerUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let assessmentAnswerService: AssessmentAnswerService;
  let assessmentService: AssessmentService;
  let questionService: QuestionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AssessmentAnswerUpdateComponent],
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
      .overrideTemplate(AssessmentAnswerUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AssessmentAnswerUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    assessmentAnswerService = TestBed.inject(AssessmentAnswerService);
    assessmentService = TestBed.inject(AssessmentService);
    questionService = TestBed.inject(QuestionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Assessment query and add missing value', () => {
      const assessmentAnswer: IAssessmentAnswer = { id: 456 };
      const assessment: IAssessment = { id: 78358 };
      assessmentAnswer.assessment = assessment;

      const assessmentCollection: IAssessment[] = [{ id: 19784 }];
      jest.spyOn(assessmentService, 'query').mockReturnValue(of(new HttpResponse({ body: assessmentCollection })));
      const additionalAssessments = [assessment];
      const expectedCollection: IAssessment[] = [...additionalAssessments, ...assessmentCollection];
      jest.spyOn(assessmentService, 'addAssessmentToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ assessmentAnswer });
      comp.ngOnInit();

      expect(assessmentService.query).toHaveBeenCalled();
      expect(assessmentService.addAssessmentToCollectionIfMissing).toHaveBeenCalledWith(assessmentCollection, ...additionalAssessments);
      expect(comp.assessmentsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Question query and add missing value', () => {
      const assessmentAnswer: IAssessmentAnswer = { id: 456 };
      const question: IQuestion = { id: 74093 };
      assessmentAnswer.question = question;

      const questionCollection: IQuestion[] = [{ id: 73464 }];
      jest.spyOn(questionService, 'query').mockReturnValue(of(new HttpResponse({ body: questionCollection })));
      const additionalQuestions = [question];
      const expectedCollection: IQuestion[] = [...additionalQuestions, ...questionCollection];
      jest.spyOn(questionService, 'addQuestionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ assessmentAnswer });
      comp.ngOnInit();

      expect(questionService.query).toHaveBeenCalled();
      expect(questionService.addQuestionToCollectionIfMissing).toHaveBeenCalledWith(questionCollection, ...additionalQuestions);
      expect(comp.questionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const assessmentAnswer: IAssessmentAnswer = { id: 456 };
      const assessment: IAssessment = { id: 76996 };
      assessmentAnswer.assessment = assessment;
      const question: IQuestion = { id: 92588 };
      assessmentAnswer.question = question;

      activatedRoute.data = of({ assessmentAnswer });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(assessmentAnswer));
      expect(comp.assessmentsSharedCollection).toContain(assessment);
      expect(comp.questionsSharedCollection).toContain(question);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AssessmentAnswer>>();
      const assessmentAnswer = { id: 123 };
      jest.spyOn(assessmentAnswerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assessmentAnswer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: assessmentAnswer }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(assessmentAnswerService.update).toHaveBeenCalledWith(assessmentAnswer);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AssessmentAnswer>>();
      const assessmentAnswer = new AssessmentAnswer();
      jest.spyOn(assessmentAnswerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assessmentAnswer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: assessmentAnswer }));
      saveSubject.complete();

      // THEN
      expect(assessmentAnswerService.create).toHaveBeenCalledWith(assessmentAnswer);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AssessmentAnswer>>();
      const assessmentAnswer = { id: 123 };
      jest.spyOn(assessmentAnswerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assessmentAnswer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(assessmentAnswerService.update).toHaveBeenCalledWith(assessmentAnswer);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackAssessmentById', () => {
      it('Should return tracked Assessment primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAssessmentById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackQuestionById', () => {
      it('Should return tracked Question primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackQuestionById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
