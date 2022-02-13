import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IAssessmentAnswer, AssessmentAnswer } from '../assessment-answer.model';
import { AssessmentAnswerService } from '../service/assessment-answer.service';

import { AssessmentAnswerRoutingResolveService } from './assessment-answer-routing-resolve.service';

describe('AssessmentAnswer routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AssessmentAnswerRoutingResolveService;
  let service: AssessmentAnswerService;
  let resultAssessmentAnswer: IAssessmentAnswer | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(AssessmentAnswerRoutingResolveService);
    service = TestBed.inject(AssessmentAnswerService);
    resultAssessmentAnswer = undefined;
  });

  describe('resolve', () => {
    it('should return IAssessmentAnswer returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAssessmentAnswer = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAssessmentAnswer).toEqual({ id: 123 });
    });

    it('should return new IAssessmentAnswer if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAssessmentAnswer = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAssessmentAnswer).toEqual(new AssessmentAnswer());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as AssessmentAnswer })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAssessmentAnswer = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAssessmentAnswer).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
