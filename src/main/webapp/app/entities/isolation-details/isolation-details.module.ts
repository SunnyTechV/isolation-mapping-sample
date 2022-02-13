import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { IsolationDetailsComponent } from './list/isolation-details.component';
import { IsolationDetailsDetailComponent } from './detail/isolation-details-detail.component';
import { IsolationDetailsUpdateComponent } from './update/isolation-details-update.component';
import { IsolationDetailsDeleteDialogComponent } from './delete/isolation-details-delete-dialog.component';
import { IsolationDetailsRoutingModule } from './route/isolation-details-routing.module';

@NgModule({
  imports: [SharedModule, IsolationDetailsRoutingModule],
  declarations: [
    IsolationDetailsComponent,
    IsolationDetailsDetailComponent,
    IsolationDetailsUpdateComponent,
    IsolationDetailsDeleteDialogComponent,
  ],
  entryComponents: [IsolationDetailsDeleteDialogComponent],
})
export class IsolationDetailsModule {}
