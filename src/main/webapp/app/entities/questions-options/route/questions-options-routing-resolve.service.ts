import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IQuestionsOptions, QuestionsOptions } from '../questions-options.model';
import { QuestionsOptionsService } from '../service/questions-options.service';

@Injectable({ providedIn: 'root' })
export class QuestionsOptionsRoutingResolveService implements Resolve<IQuestionsOptions> {
  constructor(protected service: QuestionsOptionsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQuestionsOptions> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((questionsOptions: HttpResponse<QuestionsOptions>) => {
          if (questionsOptions.body) {
            return of(questionsOptions.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new QuestionsOptions());
  }
}
