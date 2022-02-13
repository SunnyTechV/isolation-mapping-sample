import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IQuestionsOptions, getQuestionsOptionsIdentifier } from '../questions-options.model';

export type EntityResponseType = HttpResponse<IQuestionsOptions>;
export type EntityArrayResponseType = HttpResponse<IQuestionsOptions[]>;

@Injectable({ providedIn: 'root' })
export class QuestionsOptionsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/questions-options');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(questionsOptions: IQuestionsOptions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(questionsOptions);
    return this.http
      .post<IQuestionsOptions>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(questionsOptions: IQuestionsOptions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(questionsOptions);
    return this.http
      .put<IQuestionsOptions>(`${this.resourceUrl}/${getQuestionsOptionsIdentifier(questionsOptions) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(questionsOptions: IQuestionsOptions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(questionsOptions);
    return this.http
      .patch<IQuestionsOptions>(`${this.resourceUrl}/${getQuestionsOptionsIdentifier(questionsOptions) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IQuestionsOptions>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IQuestionsOptions[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addQuestionsOptionsToCollectionIfMissing(
    questionsOptionsCollection: IQuestionsOptions[],
    ...questionsOptionsToCheck: (IQuestionsOptions | null | undefined)[]
  ): IQuestionsOptions[] {
    const questionsOptions: IQuestionsOptions[] = questionsOptionsToCheck.filter(isPresent);
    if (questionsOptions.length > 0) {
      const questionsOptionsCollectionIdentifiers = questionsOptionsCollection.map(
        questionsOptionsItem => getQuestionsOptionsIdentifier(questionsOptionsItem)!
      );
      const questionsOptionsToAdd = questionsOptions.filter(questionsOptionsItem => {
        const questionsOptionsIdentifier = getQuestionsOptionsIdentifier(questionsOptionsItem);
        if (questionsOptionsIdentifier == null || questionsOptionsCollectionIdentifiers.includes(questionsOptionsIdentifier)) {
          return false;
        }
        questionsOptionsCollectionIdentifiers.push(questionsOptionsIdentifier);
        return true;
      });
      return [...questionsOptionsToAdd, ...questionsOptionsCollection];
    }
    return questionsOptionsCollection;
  }

  protected convertDateFromClient(questionsOptions: IQuestionsOptions): IQuestionsOptions {
    return Object.assign({}, questionsOptions, {
      lastModified: questionsOptions.lastModified?.isValid() ? questionsOptions.lastModified.toJSON() : undefined,
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
      res.body.forEach((questionsOptions: IQuestionsOptions) => {
        questionsOptions.lastModified = questionsOptions.lastModified ? dayjs(questionsOptions.lastModified) : undefined;
      });
    }
    return res;
  }
}
