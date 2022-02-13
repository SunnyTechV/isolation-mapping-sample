import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIsolation, getIsolationIdentifier } from '../isolation.model';

export type EntityResponseType = HttpResponse<IIsolation>;
export type EntityArrayResponseType = HttpResponse<IIsolation[]>;

@Injectable({ providedIn: 'root' })
export class IsolationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/isolations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(isolation: IIsolation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(isolation);
    return this.http
      .post<IIsolation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(isolation: IIsolation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(isolation);
    return this.http
      .put<IIsolation>(`${this.resourceUrl}/${getIsolationIdentifier(isolation) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(isolation: IIsolation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(isolation);
    return this.http
      .patch<IIsolation>(`${this.resourceUrl}/${getIsolationIdentifier(isolation) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IIsolation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIsolation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addIsolationToCollectionIfMissing(
    isolationCollection: IIsolation[],
    ...isolationsToCheck: (IIsolation | null | undefined)[]
  ): IIsolation[] {
    const isolations: IIsolation[] = isolationsToCheck.filter(isPresent);
    if (isolations.length > 0) {
      const isolationCollectionIdentifiers = isolationCollection.map(isolationItem => getIsolationIdentifier(isolationItem)!);
      const isolationsToAdd = isolations.filter(isolationItem => {
        const isolationIdentifier = getIsolationIdentifier(isolationItem);
        if (isolationIdentifier == null || isolationCollectionIdentifiers.includes(isolationIdentifier)) {
          return false;
        }
        isolationCollectionIdentifiers.push(isolationIdentifier);
        return true;
      });
      return [...isolationsToAdd, ...isolationCollection];
    }
    return isolationCollection;
  }

  protected convertDateFromClient(isolation: IIsolation): IIsolation {
    return Object.assign({}, isolation, {
      collectionDate: isolation.collectionDate?.isValid() ? isolation.collectionDate.toJSON() : undefined,
      hospitalizationDate: isolation.hospitalizationDate?.isValid() ? isolation.hospitalizationDate.toJSON() : undefined,
      lastModified: isolation.lastModified?.isValid() ? isolation.lastModified.toJSON() : undefined,
      isolationStartDate: isolation.isolationStartDate?.isValid() ? isolation.isolationStartDate.toJSON() : undefined,
      isolationEndDate: isolation.isolationEndDate?.isValid() ? isolation.isolationEndDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.collectionDate = res.body.collectionDate ? dayjs(res.body.collectionDate) : undefined;
      res.body.hospitalizationDate = res.body.hospitalizationDate ? dayjs(res.body.hospitalizationDate) : undefined;
      res.body.lastModified = res.body.lastModified ? dayjs(res.body.lastModified) : undefined;
      res.body.isolationStartDate = res.body.isolationStartDate ? dayjs(res.body.isolationStartDate) : undefined;
      res.body.isolationEndDate = res.body.isolationEndDate ? dayjs(res.body.isolationEndDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((isolation: IIsolation) => {
        isolation.collectionDate = isolation.collectionDate ? dayjs(isolation.collectionDate) : undefined;
        isolation.hospitalizationDate = isolation.hospitalizationDate ? dayjs(isolation.hospitalizationDate) : undefined;
        isolation.lastModified = isolation.lastModified ? dayjs(isolation.lastModified) : undefined;
        isolation.isolationStartDate = isolation.isolationStartDate ? dayjs(isolation.isolationStartDate) : undefined;
        isolation.isolationEndDate = isolation.isolationEndDate ? dayjs(isolation.isolationEndDate) : undefined;
      });
    }
    return res;
  }
}
