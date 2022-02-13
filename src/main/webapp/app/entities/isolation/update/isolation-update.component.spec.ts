import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IsolationService } from '../service/isolation.service';
import { IIsolation, Isolation } from '../isolation.model';
import { IIsolationDetails } from 'app/entities/isolation-details/isolation-details.model';
import { IsolationDetailsService } from 'app/entities/isolation-details/service/isolation-details.service';

import { IsolationUpdateComponent } from './isolation-update.component';

describe('Isolation Management Update Component', () => {
  let comp: IsolationUpdateComponent;
  let fixture: ComponentFixture<IsolationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let isolationService: IsolationService;
  let isolationDetailsService: IsolationDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [IsolationUpdateComponent],
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
      .overrideTemplate(IsolationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IsolationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    isolationService = TestBed.inject(IsolationService);
    isolationDetailsService = TestBed.inject(IsolationDetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call isolationDetails query and add missing value', () => {
      const isolation: IIsolation = { id: 456 };
      const isolationDetails: IIsolationDetails = { id: 66600 };
      isolation.isolationDetails = isolationDetails;

      const isolationDetailsCollection: IIsolationDetails[] = [{ id: 99627 }];
      jest.spyOn(isolationDetailsService, 'query').mockReturnValue(of(new HttpResponse({ body: isolationDetailsCollection })));
      const expectedCollection: IIsolationDetails[] = [isolationDetails, ...isolationDetailsCollection];
      jest.spyOn(isolationDetailsService, 'addIsolationDetailsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ isolation });
      comp.ngOnInit();

      expect(isolationDetailsService.query).toHaveBeenCalled();
      expect(isolationDetailsService.addIsolationDetailsToCollectionIfMissing).toHaveBeenCalledWith(
        isolationDetailsCollection,
        isolationDetails
      );
      expect(comp.isolationDetailsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const isolation: IIsolation = { id: 456 };
      const isolationDetails: IIsolationDetails = { id: 49007 };
      isolation.isolationDetails = isolationDetails;

      activatedRoute.data = of({ isolation });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(isolation));
      expect(comp.isolationDetailsCollection).toContain(isolationDetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Isolation>>();
      const isolation = { id: 123 };
      jest.spyOn(isolationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ isolation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: isolation }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(isolationService.update).toHaveBeenCalledWith(isolation);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Isolation>>();
      const isolation = new Isolation();
      jest.spyOn(isolationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ isolation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: isolation }));
      saveSubject.complete();

      // THEN
      expect(isolationService.create).toHaveBeenCalledWith(isolation);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Isolation>>();
      const isolation = { id: 123 };
      jest.spyOn(isolationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ isolation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(isolationService.update).toHaveBeenCalledWith(isolation);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackIsolationDetailsById', () => {
      it('Should return tracked IsolationDetails primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackIsolationDetailsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
