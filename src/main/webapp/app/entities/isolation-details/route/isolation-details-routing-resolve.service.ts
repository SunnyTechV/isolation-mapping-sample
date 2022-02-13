import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIsolationDetails, IsolationDetails } from '../isolation-details.model';
import { IsolationDetailsService } from '../service/isolation-details.service';

@Injectable({ providedIn: 'root' })
export class IsolationDetailsRoutingResolveService implements Resolve<IIsolationDetails> {
  constructor(protected service: IsolationDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIsolationDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((isolationDetails: HttpResponse<IsolationDetails>) => {
          if (isolationDetails.body) {
            return of(isolationDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new IsolationDetails());
  }
}
