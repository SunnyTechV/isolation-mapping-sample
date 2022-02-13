import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { IsolationComponent } from '../list/isolation.component';
import { IsolationDetailComponent } from '../detail/isolation-detail.component';
import { IsolationUpdateComponent } from '../update/isolation-update.component';
import { IsolationRoutingResolveService } from './isolation-routing-resolve.service';

const isolationRoute: Routes = [
  {
    path: '',
    component: IsolationComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IsolationDetailComponent,
    resolve: {
      isolation: IsolationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IsolationUpdateComponent,
    resolve: {
      isolation: IsolationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IsolationUpdateComponent,
    resolve: {
      isolation: IsolationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(isolationRoute)],
  exports: [RouterModule],
})
export class IsolationRoutingModule {}
