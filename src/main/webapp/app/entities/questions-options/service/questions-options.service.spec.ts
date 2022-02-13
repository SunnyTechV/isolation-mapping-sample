import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { HealthCondition } from 'app/entities/enumerations/health-condition.model';
import { IQuestionsOptions, QuestionsOptions } from '../questions-options.model';

import { QuestionsOptionsService } from './questions-options.service';

describe('QuestionsOptions Service', () => {
  let service: QuestionsOptionsService;
  let httpMock: HttpTestingController;
  let elemDefault: IQuestionsOptions;
  let expectedResult: IQuestionsOptions | IQuestionsOptions[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(QuestionsOptionsService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      ansOption: 'AAAAAAA',
      healthCondition: HealthCondition.STABLE,
      active: false,
      lastModified: currentDate,
      lastModifiedBy: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a QuestionsOptions', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.create(new QuestionsOptions()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a QuestionsOptions', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          ansOption: 'BBBBBB',
          healthCondition: 'BBBBBB',
          active: true,
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a QuestionsOptions', () => {
      const patchObject = Object.assign({}, new QuestionsOptions());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of QuestionsOptions', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          ansOption: 'BBBBBB',
          healthCondition: 'BBBBBB',
          active: true,
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
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

    it('should delete a QuestionsOptions', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addQuestionsOptionsToCollectionIfMissing', () => {
      it('should add a QuestionsOptions to an empty array', () => {
        const questionsOptions: IQuestionsOptions = { id: 123 };
        expectedResult = service.addQuestionsOptionsToCollectionIfMissing([], questionsOptions);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(questionsOptions);
      });

      it('should not add a QuestionsOptions to an array that contains it', () => {
        const questionsOptions: IQuestionsOptions = { id: 123 };
        const questionsOptionsCollection: IQuestionsOptions[] = [
          {
            ...questionsOptions,
          },
          { id: 456 },
        ];
        expectedResult = service.addQuestionsOptionsToCollectionIfMissing(questionsOptionsCollection, questionsOptions);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a QuestionsOptions to an array that doesn't contain it", () => {
        const questionsOptions: IQuestionsOptions = { id: 123 };
        const questionsOptionsCollection: IQuestionsOptions[] = [{ id: 456 }];
        expectedResult = service.addQuestionsOptionsToCollectionIfMissing(questionsOptionsCollection, questionsOptions);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(questionsOptions);
      });

      it('should add only unique QuestionsOptions to an array', () => {
        const questionsOptionsArray: IQuestionsOptions[] = [{ id: 123 }, { id: 456 }, { id: 66152 }];
        const questionsOptionsCollection: IQuestionsOptions[] = [{ id: 123 }];
        expectedResult = service.addQuestionsOptionsToCollectionIfMissing(questionsOptionsCollection, ...questionsOptionsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const questionsOptions: IQuestionsOptions = { id: 123 };
        const questionsOptions2: IQuestionsOptions = { id: 456 };
        expectedResult = service.addQuestionsOptionsToCollectionIfMissing([], questionsOptions, questionsOptions2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(questionsOptions);
        expect(expectedResult).toContain(questionsOptions2);
      });

      it('should accept null and undefined values', () => {
        const questionsOptions: IQuestionsOptions = { id: 123 };
        expectedResult = service.addQuestionsOptionsToCollectionIfMissing([], null, questionsOptions, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(questionsOptions);
      });

      it('should return initial array if no QuestionsOptions is added', () => {
        const questionsOptionsCollection: IQuestionsOptions[] = [{ id: 123 }];
        expectedResult = service.addQuestionsOptionsToCollectionIfMissing(questionsOptionsCollection, undefined, null);
        expect(expectedResult).toEqual(questionsOptionsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
