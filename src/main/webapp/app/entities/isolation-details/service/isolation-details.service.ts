import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIsolationDetails, getIsolationDetailsIdentifier } from '../isolation-details.model';

export type EntityResponseType = HttpResponse<IIsolationDetails>;
export type EntityArrayResponseType = HttpResponse<IIsolationDetails[]>;

@Injectable({ providedIn: 'root' })
export class IsolationDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/isolation-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(isolationDetails: IIsolationDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(isolationDetails);
    return this.http
      .post<IIsolationDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(isolationDetails: IIsolationDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(isolationDetails);
    return this.http
      .put<IIsolationDetails>(`${this.resourceUrl}/${getIsolationDetailsIdentifier(isolationDetails) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(isolationDetails: IIsolationDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(isolationDetails);
    return this.http
      .patch<IIsolationDetails>(`${this.resourceUrl}/${getIsolationDetailsIdentifier(isolationDetails) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IIsolationDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIsolationDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addIsolationDetailsToCollectionIfMissing(
    isolationDetailsCollection: IIsolationDetails[],
    ...isolationDetailsToCheck: (IIsolationDetails | null | undefined)[]
  ): IIsolationDetails[] {
    const isolationDetails: IIsolationDetails[] = isolationDetailsToCheck.filter(isPresent);
    if (isolationDetails.length > 0) {
      const isolationDetailsCollectionIdentifiers = isolationDetailsCollection.map(
        isolationDetailsItem => getIsolationDetailsIdentifier(isolationDetailsItem)!
      );
      const isolationDetailsToAdd = isolationDetails.filter(isolationDetailsItem => {
        const isolationDetailsIdentifier = getIsolationDetailsIdentifier(isolationDetailsItem);
        if (isolationDetailsIdentifier == null || isolationDetailsCollectionIdentifiers.includes(isolationDetailsIdentifier)) {
          return false;
        }
        isolationDetailsCollectionIdentifiers.push(isolationDetailsIdentifier);
        return true;
      });
      return [...isolationDetailsToAdd, ...isolationDetailsCollection];
    }
    return isolationDetailsCollection;
  }

  protected convertDateFromClient(isolationDetails: IIsolationDetails): IIsolationDetails {
    return Object.assign({}, isolationDetails, {
      lastAssessment: isolationDetails.lastAssessment?.isValid() ? isolationDetails.lastAssessment.toJSON() : undefined,
      lastModified: isolationDetails.lastModified?.isValid() ? isolationDetails.lastModified.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.lastAssessment = res.body.lastAssessment ? dayjs(res.body.lastAssessment) : undefined;
      res.body.lastModified = res.body.lastModified ? dayjs(res.body.lastModified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((isolationDetails: IIsolationDetails) => {
        isolationDetails.lastAssessment = isolationDetails.lastAssessment ? dayjs(isolationDetails.lastAssessment) : undefined;
        isolationDetails.lastModified = isolationDetails.lastModified ? dayjs(isolationDetails.lastModified) : undefined;
      });
    }
    return res;
  }
}
