import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { IsolationComponent } from './list/isolation.component';
import { IsolationDetailComponent } from './detail/isolation-detail.component';
import { IsolationUpdateComponent } from './update/isolation-update.component';
import { IsolationDeleteDialogComponent } from './delete/isolation-delete-dialog.component';
import { IsolationRoutingModule } from './route/isolation-routing.module';

@NgModule({
  imports: [SharedModule, IsolationRoutingModule],
  declarations: [IsolationComponent, IsolationDetailComponent, IsolationUpdateComponent, IsolationDeleteDialogComponent],
  entryComponents: [IsolationDeleteDialogComponent],
})
export class IsolationModule {}
