import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AssessmentAnswerComponent } from './list/assessment-answer.component';
import { AssessmentAnswerDetailComponent } from './detail/assessment-answer-detail.component';
import { AssessmentAnswerUpdateComponent } from './update/assessment-answer-update.component';
import { AssessmentAnswerDeleteDialogComponent } from './delete/assessment-answer-delete-dialog.component';
import { AssessmentAnswerRoutingModule } from './route/assessment-answer-routing.module';

@NgModule({
  imports: [SharedModule, AssessmentAnswerRoutingModule],
  declarations: [
    AssessmentAnswerComponent,
    AssessmentAnswerDetailComponent,
    AssessmentAnswerUpdateComponent,
    AssessmentAnswerDeleteDialogComponent,
  ],
  entryComponents: [AssessmentAnswerDeleteDialogComponent],
})
export class AssessmentAnswerModule {}
