import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAssessmentAnswer, AssessmentAnswer } from '../assessment-answer.model';

import { AssessmentAnswerService } from './assessment-answer.service';

describe('AssessmentAnswer Service', () => {
  let service: AssessmentAnswerService;
  let httpMock: HttpTestingController;
  let elemDefault: IAssessmentAnswer;
  let expectedResult: IAssessmentAnswer | IAssessmentAnswer[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AssessmentAnswerService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      answer: 'AAAAAAA',
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

    it('should create a AssessmentAnswer', () => {
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

      service.create(new AssessmentAnswer()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AssessmentAnswer', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          answer: 'BBBBBB',
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

    it('should partial update a AssessmentAnswer', () => {
      const patchObject = Object.assign(
        {
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        new AssessmentAnswer()
      );

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

    it('should return a list of AssessmentAnswer', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          answer: 'BBBBBB',
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

    it('should delete a AssessmentAnswer', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAssessmentAnswerToCollectionIfMissing', () => {
      it('should add a AssessmentAnswer to an empty array', () => {
        const assessmentAnswer: IAssessmentAnswer = { id: 123 };
        expectedResult = service.addAssessmentAnswerToCollectionIfMissing([], assessmentAnswer);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(assessmentAnswer);
      });

      it('should not add a AssessmentAnswer to an array that contains it', () => {
        const assessmentAnswer: IAssessmentAnswer = { id: 123 };
        const assessmentAnswerCollection: IAssessmentAnswer[] = [
          {
            ...assessmentAnswer,
          },
          { id: 456 },
        ];
        expectedResult = service.addAssessmentAnswerToCollectionIfMissing(assessmentAnswerCollection, assessmentAnswer);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AssessmentAnswer to an array that doesn't contain it", () => {
        const assessmentAnswer: IAssessmentAnswer = { id: 123 };
        const assessmentAnswerCollection: IAssessmentAnswer[] = [{ id: 456 }];
        expectedResult = service.addAssessmentAnswerToCollectionIfMissing(assessmentAnswerCollection, assessmentAnswer);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(assessmentAnswer);
      });

      it('should add only unique AssessmentAnswer to an array', () => {
        const assessmentAnswerArray: IAssessmentAnswer[] = [{ id: 123 }, { id: 456 }, { id: 1736 }];
        const assessmentAnswerCollection: IAssessmentAnswer[] = [{ id: 123 }];
        expectedResult = service.addAssessmentAnswerToCollectionIfMissing(assessmentAnswerCollection, ...assessmentAnswerArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const assessmentAnswer: IAssessmentAnswer = { id: 123 };
        const assessmentAnswer2: IAssessmentAnswer = { id: 456 };
        expectedResult = service.addAssessmentAnswerToCollectionIfMissing([], assessmentAnswer, assessmentAnswer2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(assessmentAnswer);
        expect(expectedResult).toContain(assessmentAnswer2);
      });

      it('should accept null and undefined values', () => {
        const assessmentAnswer: IAssessmentAnswer = { id: 123 };
        expectedResult = service.addAssessmentAnswerToCollectionIfMissing([], null, assessmentAnswer, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(assessmentAnswer);
      });

      it('should return initial array if no AssessmentAnswer is added', () => {
        const assessmentAnswerCollection: IAssessmentAnswer[] = [{ id: 123 }];
        expectedResult = service.addAssessmentAnswerToCollectionIfMissing(assessmentAnswerCollection, undefined, null);
        expect(expectedResult).toEqual(assessmentAnswerCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
