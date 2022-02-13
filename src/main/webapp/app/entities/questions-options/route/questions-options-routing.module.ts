import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { QuestionsOptionsComponent } from '../list/questions-options.component';
import { QuestionsOptionsDetailComponent } from '../detail/questions-options-detail.component';
import { QuestionsOptionsUpdateComponent } from '../update/questions-options-update.component';
import { QuestionsOptionsRoutingResolveService } from './questions-options-routing-resolve.service';

const questionsOptionsRoute: Routes = [
  {
    path: '',
    component: QuestionsOptionsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QuestionsOptionsDetailComponent,
    resolve: {
      questionsOptions: QuestionsOptionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QuestionsOptionsUpdateComponent,
    resolve: {
      questionsOptions: QuestionsOptionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QuestionsOptionsUpdateComponent,
    resolve: {
      questionsOptions: QuestionsOptionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(questionsOptionsRoute)],
  exports: [RouterModule],
})
export class QuestionsOptionsRoutingModule {}
