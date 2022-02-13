import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IIsolationDetails, IsolationDetails } from '../isolation-details.model';
import { IsolationDetailsService } from '../service/isolation-details.service';

import { IsolationDetailsRoutingResolveService } from './isolation-details-routing-resolve.service';

describe('IsolationDetails routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: IsolationDetailsRoutingResolveService;
  let service: IsolationDetailsService;
  let resultIsolationDetails: IIsolationDetails | undefined;

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
    routingResolveService = TestBed.inject(IsolationDetailsRoutingResolveService);
    service = TestBed.inject(IsolationDetailsService);
    resultIsolationDetails = undefined;
  });

  describe('resolve', () => {
    it('should return IIsolationDetails returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultIsolationDetails = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultIsolationDetails).toEqual({ id: 123 });
    });

    it('should return new IIsolationDetails if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultIsolationDetails = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultIsolationDetails).toEqual(new IsolationDetails());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as IsolationDetails })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultIsolationDetails = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultIsolationDetails).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
