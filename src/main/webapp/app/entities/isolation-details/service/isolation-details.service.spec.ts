import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IIsolationDetails, IsolationDetails } from '../isolation-details.model';

import { IsolationDetailsService } from './isolation-details.service';

describe('IsolationDetails Service', () => {
  let service: IsolationDetailsService;
  let httpMock: HttpTestingController;
  let elemDefault: IIsolationDetails;
  let expectedResult: IIsolationDetails | IIsolationDetails[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(IsolationDetailsService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      referredDrName: 'AAAAAAA',
      referredDrMobile: 'AAAAAAA',
      prescriptionUrl: 'AAAAAAA',
      reportUrl: 'AAAAAAA',
      remarks: 'AAAAAAA',
      selfRegistered: false,
      lastAssessment: currentDate,
      lastModified: currentDate,
      lastModifiedBy: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          lastAssessment: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a IsolationDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          lastAssessment: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          lastAssessment: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.create(new IsolationDetails()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a IsolationDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          referredDrName: 'BBBBBB',
          referredDrMobile: 'BBBBBB',
          prescriptionUrl: 'BBBBBB',
          reportUrl: 'BBBBBB',
          remarks: 'BBBBBB',
          selfRegistered: true,
          lastAssessment: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          lastAssessment: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a IsolationDetails', () => {
      const patchObject = Object.assign(
        {
          referredDrName: 'BBBBBB',
          referredDrMobile: 'BBBBBB',
          prescriptionUrl: 'BBBBBB',
          reportUrl: 'BBBBBB',
        },
        new IsolationDetails()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          lastAssessment: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of IsolationDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          referredDrName: 'BBBBBB',
          referredDrMobile: 'BBBBBB',
          prescriptionUrl: 'BBBBBB',
          reportUrl: 'BBBBBB',
          remarks: 'BBBBBB',
          selfRegistered: true,
          lastAssessment: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          lastAssessment: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a IsolationDetails', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addIsolationDetailsToCollectionIfMissing', () => {
      it('should add a IsolationDetails to an empty array', () => {
        const isolationDetails: IIsolationDetails = { id: 123 };
        expectedResult = service.addIsolationDetailsToCollectionIfMissing([], isolationDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(isolationDetails);
      });

      it('should not add a IsolationDetails to an array that contains it', () => {
        const isolationDetails: IIsolationDetails = { id: 123 };
        const isolationDetailsCollection: IIsolationDetails[] = [
          {
            ...isolationDetails,
          },
          { id: 456 },
        ];
        expectedResult = service.addIsolationDetailsToCollectionIfMissing(isolationDetailsCollection, isolationDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a IsolationDetails to an array that doesn't contain it", () => {
        const isolationDetails: IIsolationDetails = { id: 123 };
        const isolationDetailsCollection: IIsolationDetails[] = [{ id: 456 }];
        expectedResult = service.addIsolationDetailsToCollectionIfMissing(isolationDetailsCollection, isolationDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(isolationDetails);
      });

      it('should add only unique IsolationDetails to an array', () => {
        const isolationDetailsArray: IIsolationDetails[] = [{ id: 123 }, { id: 456 }, { id: 34722 }];
        const isolationDetailsCollection: IIsolationDetails[] = [{ id: 123 }];
        expectedResult = service.addIsolationDetailsToCollectionIfMissing(isolationDetailsCollection, ...isolationDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const isolationDetails: IIsolationDetails = { id: 123 };
        const isolationDetails2: IIsolationDetails = { id: 456 };
        expectedResult = service.addIsolationDetailsToCollectionIfMissing([], isolationDetails, isolationDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(isolationDetails);
        expect(expectedResult).toContain(isolationDetails2);
      });

      it('should accept null and undefined values', () => {
        const isolationDetails: IIsolationDetails = { id: 123 };
        expectedResult = service.addIsolationDetailsToCollectionIfMissing([], null, isolationDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(isolationDetails);
      });

      it('should return initial array if no IsolationDetails is added', () => {
        const isolationDetailsCollection: IIsolationDetails[] = [{ id: 123 }];
        expectedResult = service.addIsolationDetailsToCollectionIfMissing(isolationDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(isolationDetailsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
