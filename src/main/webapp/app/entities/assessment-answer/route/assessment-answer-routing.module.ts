import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AssessmentAnswerComponent } from '../list/assessment-answer.component';
import { AssessmentAnswerDetailComponent } from '../detail/assessment-answer-detail.component';
import { AssessmentAnswerUpdateComponent } from '../update/assessment-answer-update.component';
import { AssessmentAnswerRoutingResolveService } from './assessment-answer-routing-resolve.service';

const assessmentAnswerRoute: Routes = [
  {
    path: '',
    component: AssessmentAnswerComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AssessmentAnswerDetailComponent,
    resolve: {
      assessmentAnswer: AssessmentAnswerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AssessmentAnswerUpdateComponent,
    resolve: {
      assessmentAnswer: AssessmentAnswerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AssessmentAnswerUpdateComponent,
    resolve: {
      assessmentAnswer: AssessmentAnswerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(assessmentAnswerRoute)],
  exports: [RouterModule],
})
export class AssessmentAnswerRoutingModule {}
