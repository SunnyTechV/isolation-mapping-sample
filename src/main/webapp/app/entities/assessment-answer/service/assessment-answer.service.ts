import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAssessmentAnswer, getAssessmentAnswerIdentifier } from '../assessment-answer.model';

export type EntityResponseType = HttpResponse<IAssessmentAnswer>;
export type EntityArrayResponseType = HttpResponse<IAssessmentAnswer[]>;

@Injectable({ providedIn: 'root' })
export class AssessmentAnswerService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/assessment-answers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(assessmentAnswer: IAssessmentAnswer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(assessmentAnswer);
    return this.http
      .post<IAssessmentAnswer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(assessmentAnswer: IAssessmentAnswer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(assessmentAnswer);
    return this.http
      .put<IAssessmentAnswer>(`${this.resourceUrl}/${getAssessmentAnswerIdentifier(assessmentAnswer) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(assessmentAnswer: IAssessmentAnswer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(assessmentAnswer);
    return this.http
      .patch<IAssessmentAnswer>(`${this.resourceUrl}/${getAssessmentAnswerIdentifier(assessmentAnswer) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAssessmentAnswer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAssessmentAnswer[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAssessmentAnswerToCollectionIfMissing(
    assessmentAnswerCollection: IAssessmentAnswer[],
    ...assessmentAnswersToCheck: (IAssessmentAnswer | null | undefined)[]
  ): IAssessmentAnswer[] {
    const assessmentAnswers: IAssessmentAnswer[] = assessmentAnswersToCheck.filter(isPresent);
    if (assessmentAnswers.length > 0) {
      const assessmentAnswerCollectionIdentifiers = assessmentAnswerCollection.map(
        assessmentAnswerItem => getAssessmentAnswerIdentifier(assessmentAnswerItem)!
      );
      const assessmentAnswersToAdd = assessmentAnswers.filter(assessmentAnswerItem => {
        const assessmentAnswerIdentifier = getAssessmentAnswerIdentifier(assessmentAnswerItem);
        if (assessmentAnswerIdentifier == null || assessmentAnswerCollectionIdentifiers.includes(assessmentAnswerIdentifier)) {
          return false;
        }
        assessmentAnswerCollectionIdentifiers.push(assessmentAnswerIdentifier);
        return true;
      });
      return [...assessmentAnswersToAdd, ...assessmentAnswerCollection];
    }
    return assessmentAnswerCollection;
  }

  protected convertDateFromClient(assessmentAnswer: IAssessmentAnswer): IAssessmentAnswer {
    return Object.assign({}, assessmentAnswer, {
      lastModified: assessmentAnswer.lastModified?.isValid() ? assessmentAnswer.lastModified.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.lastModified = res.body.lastModified ? dayjs(res.body.lastModified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((assessmentAnswer: IAssessmentAnswer) => {
        assessmentAnswer.lastModified = assessmentAnswer.lastModified ? dayjs(assessmentAnswer.lastModified) : undefined;
      });
    }
    return res;
  }
}
