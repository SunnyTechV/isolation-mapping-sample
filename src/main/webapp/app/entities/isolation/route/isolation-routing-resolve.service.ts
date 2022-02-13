import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIsolation, Isolation } from '../isolation.model';
import { IsolationService } from '../service/isolation.service';

@Injectable({ providedIn: 'root' })
export class IsolationRoutingResolveService implements Resolve<IIsolation> {
  constructor(protected service: IsolationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIsolation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((isolation: HttpResponse<Isolation>) => {
          if (isolation.body) {
            return of(isolation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Isolation());
  }
}
