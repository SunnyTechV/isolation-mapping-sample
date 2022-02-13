import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IsolationStatus } from 'app/entities/enumerations/isolation-status.model';
import { HealthCondition } from 'app/entities/enumerations/health-condition.model';
import { IIsolation, Isolation } from '../isolation.model';

import { IsolationService } from './isolation.service';

describe('Isolation Service', () => {
  let service: IsolationService;
  let httpMock: HttpTestingController;
  let elemDefault: IIsolation;
  let expectedResult: IIsolation | IIsolation[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(IsolationService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      icmrId: 'AAAAAAA',
      rtpcrId: 'AAAAAAA',
      ratId: 'AAAAAAA',
      firstName: 'AAAAAAA',
      lastName: 'AAAAAAA',
      latitude: 'AAAAAAA',
      longitude: 'AAAAAAA',
      email: 'AAAAAAA',
      imageUrl: 'AAAAAAA',
      activated: false,
      mobileNo: 'AAAAAAA',
      passwordHash: 'AAAAAAA',
      secondaryContactNo: 'AAAAAAA',
      aadharCardNo: 'AAAAAAA',
      status: IsolationStatus.HOMEISOLATION,
      age: 'AAAAAAA',
      gender: 'AAAAAAA',
      stateId: 0,
      districtId: 0,
      talukaId: 0,
      cityId: 0,
      address: 'AAAAAAA',
      pincode: 'AAAAAAA',
      collectionDate: currentDate,
      hospitalized: false,
      hospitalId: 0,
      addressLatitude: 'AAAAAAA',
      addressLongitude: 'AAAAAAA',
      currentLatitude: 'AAAAAAA',
      currentLongitude: 'AAAAAAA',
      hospitalizationDate: currentDate,
      healthCondition: HealthCondition.STABLE,
      remarks: 'AAAAAAA',
      symptomatic: false,
      ccmsLogin: 'AAAAAAA',
      selfRegistered: false,
      lastModified: currentDate,
      lastModifiedBy: 'AAAAAAA',
      isolationStartDate: currentDate,
      isolationEndDate: currentDate,
      tvgIsolationUserId: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          collectionDate: currentDate.format(DATE_TIME_FORMAT),
          hospitalizationDate: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          isolationStartDate: currentDate.format(DATE_TIME_FORMAT),
          isolationEndDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Isolation', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          collectionDate: currentDate.format(DATE_TIME_FORMAT),
          hospitalizationDate: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          isolationStartDate: currentDate.format(DATE_TIME_FORMAT),
          isolationEndDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          collectionDate: currentDate,
          hospitalizationDate: currentDate,
          lastModified: currentDate,
          isolationStartDate: currentDate,
          isolationEndDate: currentDate,
        },
        returnedFromService
      );

      service.create(new Isolation()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Isolation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          icmrId: 'BBBBBB',
          rtpcrId: 'BBBBBB',
          ratId: 'BBBBBB',
          firstName: 'BBBBBB',
          lastName: 'BBBBBB',
          latitude: 'BBBBBB',
          longitude: 'BBBBBB',
          email: 'BBBBBB',
          imageUrl: 'BBBBBB',
          activated: true,
          mobileNo: 'BBBBBB',
          passwordHash: 'BBBBBB',
          secondaryContactNo: 'BBBBBB',
          aadharCardNo: 'BBBBBB',
          status: 'BBBBBB',
          age: 'BBBBBB',
          gender: 'BBBBBB',
          stateId: 1,
          districtId: 1,
          talukaId: 1,
          cityId: 1,
          address: 'BBBBBB',
          pincode: 'BBBBBB',
          collectionDate: currentDate.format(DATE_TIME_FORMAT),
          hospitalized: true,
          hospitalId: 1,
          addressLatitude: 'BBBBBB',
          addressLongitude: 'BBBBBB',
          currentLatitude: 'BBBBBB',
          currentLongitude: 'BBBBBB',
          hospitalizationDate: currentDate.format(DATE_TIME_FORMAT),
          healthCondition: 'BBBBBB',
          remarks: 'BBBBBB',
          symptomatic: true,
          ccmsLogin: 'BBBBBB',
          selfRegistered: true,
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          isolationStartDate: currentDate.format(DATE_TIME_FORMAT),
          isolationEndDate: currentDate.format(DATE_TIME_FORMAT),
          tvgIsolationUserId: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          collectionDate: currentDate,
          hospitalizationDate: currentDate,
          lastModified: currentDate,
          isolationStartDate: currentDate,
          isolationEndDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Isolation', () => {
      const patchObject = Object.assign(
        {
          firstName: 'BBBBBB',
          lastName: 'BBBBBB',
          latitude: 'BBBBBB',
          longitude: 'BBBBBB',
          imageUrl: 'BBBBBB',
          passwordHash: 'BBBBBB',
          aadharCardNo: 'BBBBBB',
          status: 'BBBBBB',
          age: 'BBBBBB',
          gender: 'BBBBBB',
          address: 'BBBBBB',
          collectionDate: currentDate.format(DATE_TIME_FORMAT),
          addressLatitude: 'BBBBBB',
          healthCondition: 'BBBBBB',
          remarks: 'BBBBBB',
          isolationEndDate: currentDate.format(DATE_TIME_FORMAT),
          tvgIsolationUserId: 1,
        },
        new Isolation()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          collectionDate: currentDate,
          hospitalizationDate: currentDate,
          lastModified: currentDate,
          isolationStartDate: currentDate,
          isolationEndDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Isolation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          icmrId: 'BBBBBB',
          rtpcrId: 'BBBBBB',
          ratId: 'BBBBBB',
          firstName: 'BBBBBB',
          lastName: 'BBBBBB',
          latitude: 'BBBBBB',
          longitude: 'BBBBBB',
          email: 'BBBBBB',
          imageUrl: 'BBBBBB',
          activated: true,
          mobileNo: 'BBBBBB',
          passwordHash: 'BBBBBB',
          secondaryContactNo: 'BBBBBB',
          aadharCardNo: 'BBBBBB',
          status: 'BBBBBB',
          age: 'BBBBBB',
          gender: 'BBBBBB',
          stateId: 1,
          districtId: 1,
          talukaId: 1,
          cityId: 1,
          address: 'BBBBBB',
          pincode: 'BBBBBB',
          collectionDate: currentDate.format(DATE_TIME_FORMAT),
          hospitalized: true,
          hospitalId: 1,
          addressLatitude: 'BBBBBB',
          addressLongitude: 'BBBBBB',
          currentLatitude: 'BBBBBB',
          currentLongitude: 'BBBBBB',
          hospitalizationDate: currentDate.format(DATE_TIME_FORMAT),
          healthCondition: 'BBBBBB',
          remarks: 'BBBBBB',
          symptomatic: true,
          ccmsLogin: 'BBBBBB',
          selfRegistered: true,
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          isolationStartDate: currentDate.format(DATE_TIME_FORMAT),
          isolationEndDate: currentDate.format(DATE_TIME_FORMAT),
          tvgIsolationUserId: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          collectionDate: currentDate,
          hospitalizationDate: currentDate,
          lastModified: currentDate,
          isolationStartDate: currentDate,
          isolationEndDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Isolation', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addIsolationToCollectionIfMissing', () => {
      it('should add a Isolation to an empty array', () => {
        const isolation: IIsolation = { id: 123 };
        expectedResult = service.addIsolationToCollectionIfMissing([], isolation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(isolation);
      });

      it('should not add a Isolation to an array that contains it', () => {
        const isolation: IIsolation = { id: 123 };
        const isolationCollection: IIsolation[] = [
          {
            ...isolation,
          },
          { id: 456 },
        ];
        expectedResult = service.addIsolationToCollectionIfMissing(isolationCollection, isolation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Isolation to an array that doesn't contain it", () => {
        const isolation: IIsolation = { id: 123 };
        const isolationCollection: IIsolation[] = [{ id: 456 }];
        expectedResult = service.addIsolationToCollectionIfMissing(isolationCollection, isolation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(isolation);
      });

      it('should add only unique Isolation to an array', () => {
        const isolationArray: IIsolation[] = [{ id: 123 }, { id: 456 }, { id: 20928 }];
        const isolationCollection: IIsolation[] = [{ id: 123 }];
        expectedResult = service.addIsolationToCollectionIfMissing(isolationCollection, ...isolationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const isolation: IIsolation = { id: 123 };
        const isolation2: IIsolation = { id: 456 };
        expectedResult = service.addIsolationToCollectionIfMissing([], isolation, isolation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(isolation);
        expect(expectedResult).toContain(isolation2);
      });

      it('should accept null and undefined values', () => {
        const isolation: IIsolation = { id: 123 };
        expectedResult = service.addIsolationToCollectionIfMissing([], null, isolation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(isolation);
      });

      it('should return initial array if no Isolation is added', () => {
        const isolationCollection: IIsolation[] = [{ id: 123 }];
        expectedResult = service.addIsolationToCollectionIfMissing(isolationCollection, undefined, null);
        expect(expectedResult).toEqual(isolationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
