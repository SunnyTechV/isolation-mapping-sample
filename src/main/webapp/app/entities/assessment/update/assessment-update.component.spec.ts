import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AssessmentService } from '../service/assessment.service';
import { IAssessment, Assessment } from '../assessment.model';
import { IIsolation } from 'app/entities/isolation/isolation.model';
import { IsolationService } from 'app/entities/isolation/service/isolation.service';

import { AssessmentUpdateComponent } from './assessment-update.component';

describe('Assessment Management Update Component', () => {
  let comp: AssessmentUpdateComponent;
  let fixture: ComponentFixture<AssessmentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let assessmentService: AssessmentService;
  let isolationService: IsolationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AssessmentUpdateComponent],
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
      .overrideTemplate(AssessmentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AssessmentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    assessmentService = TestBed.inject(AssessmentService);
    isolationService = TestBed.inject(IsolationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Isolation query and add missing value', () => {
      const assessment: IAssessment = { id: 456 };
      const isolation: IIsolation = { id: 86413 };
      assessment.isolation = isolation;

      const isolationCollection: IIsolation[] = [{ id: 11526 }];
      jest.spyOn(isolationService, 'query').mockReturnValue(of(new HttpResponse({ body: isolationCollection })));
      const additionalIsolations = [isolation];
      const expectedCollection: IIsolation[] = [...additionalIsolations, ...isolationCollection];
      jest.spyOn(isolationService, 'addIsolationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ assessment });
      comp.ngOnInit();

      expect(isolationService.query).toHaveBeenCalled();
      expect(isolationService.addIsolationToCollectionIfMissing).toHaveBeenCalledWith(isolationCollection, ...additionalIsolations);
      expect(comp.isolationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const assessment: IAssessment = { id: 456 };
      const isolation: IIsolation = { id: 29048 };
      assessment.isolation = isolation;

      activatedRoute.data = of({ assessment });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(assessment));
      expect(comp.isolationsSharedCollection).toContain(isolation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Assessment>>();
      const assessment = { id: 123 };
      jest.spyOn(assessmentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assessment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: assessment }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(assessmentService.update).toHaveBeenCalledWith(assessment);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Assessment>>();
      const assessment = new Assessment();
      jest.spyOn(assessmentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assessment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: assessment }));
      saveSubject.complete();

      // THEN
      expect(assessmentService.create).toHaveBeenCalledWith(assessment);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Assessment>>();
      const assessment = { id: 123 };
      jest.spyOn(assessmentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assessment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(assessmentService.update).toHaveBeenCalledWith(assessment);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackIsolationById', () => {
      it('Should return tracked Isolation primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackIsolationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
