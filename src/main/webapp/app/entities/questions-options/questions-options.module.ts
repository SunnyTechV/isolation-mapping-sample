import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { QuestionsOptionsComponent } from './list/questions-options.component';
import { QuestionsOptionsDetailComponent } from './detail/questions-options-detail.component';
import { QuestionsOptionsUpdateComponent } from './update/questions-options-update.component';
import { QuestionsOptionsDeleteDialogComponent } from './delete/questions-options-delete-dialog.component';
import { QuestionsOptionsRoutingModule } from './route/questions-options-routing.module';

@NgModule({
  imports: [SharedModule, QuestionsOptionsRoutingModule],
  declarations: [
    QuestionsOptionsComponent,
    QuestionsOptionsDetailComponent,
    QuestionsOptionsUpdateComponent,
    QuestionsOptionsDeleteDialogComponent,
  ],
  entryComponents: [QuestionsOptionsDeleteDialogComponent],
})
export class QuestionsOptionsModule {}
