import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAssessmentAnswer, AssessmentAnswer } from '../assessment-answer.model';
import { AssessmentAnswerService } from '../service/assessment-answer.service';

@Injectable({ providedIn: 'root' })
export class AssessmentAnswerRoutingResolveService implements Resolve<IAssessmentAnswer> {
  constructor(protected service: AssessmentAnswerService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAssessmentAnswer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((assessmentAnswer: HttpResponse<AssessmentAnswer>) => {
          if (assessmentAnswer.body) {
            return of(assessmentAnswer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AssessmentAnswer());
  }
}
