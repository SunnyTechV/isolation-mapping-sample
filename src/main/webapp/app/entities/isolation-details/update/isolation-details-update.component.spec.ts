import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IsolationDetailsService } from '../service/isolation-details.service';
import { IIsolationDetails, IsolationDetails } from '../isolation-details.model';

import { IsolationDetailsUpdateComponent } from './isolation-details-update.component';

describe('IsolationDetails Management Update Component', () => {
  let comp: IsolationDetailsUpdateComponent;
  let fixture: ComponentFixture<IsolationDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let isolationDetailsService: IsolationDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [IsolationDetailsUpdateComponent],
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
      .overrideTemplate(IsolationDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IsolationDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    isolationDetailsService = TestBed.inject(IsolationDetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const isolationDetails: IIsolationDetails = { id: 456 };

      activatedRoute.data = of({ isolationDetails });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(isolationDetails));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IsolationDetails>>();
      const isolationDetails = { id: 123 };
      jest.spyOn(isolationDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ isolationDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: isolationDetails }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(isolationDetailsService.update).toHaveBeenCalledWith(isolationDetails);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IsolationDetails>>();
      const isolationDetails = new IsolationDetails();
      jest.spyOn(isolationDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ isolationDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: isolationDetails }));
      saveSubject.complete();

      // THEN
      expect(isolationDetailsService.create).toHaveBeenCalledWith(isolationDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IsolationDetails>>();
      const isolationDetails = { id: 123 };
      jest.spyOn(isolationDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ isolationDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(isolationDetailsService.update).toHaveBeenCalledWith(isolationDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
