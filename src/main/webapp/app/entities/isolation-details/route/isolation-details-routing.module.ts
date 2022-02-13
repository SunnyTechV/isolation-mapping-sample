import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { IsolationDetailsComponent } from '../list/isolation-details.component';
import { IsolationDetailsDetailComponent } from '../detail/isolation-details-detail.component';
import { IsolationDetailsUpdateComponent } from '../update/isolation-details-update.component';
import { IsolationDetailsRoutingResolveService } from './isolation-details-routing-resolve.service';

const isolationDetailsRoute: Routes = [
  {
    path: '',
    component: IsolationDetailsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IsolationDetailsDetailComponent,
    resolve: {
      isolationDetails: IsolationDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IsolationDetailsUpdateComponent,
    resolve: {
      isolationDetails: IsolationDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IsolationDetailsUpdateComponent,
    resolve: {
      isolationDetails: IsolationDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(isolationDetailsRoute)],
  exports: [RouterModule],
})
export class IsolationDetailsRoutingModule {}
