import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAssessmentAnswer } from '../assessment-answer.model';
import { AssessmentAnswerService } from '../service/assessment-answer.service';

@Component({
  templateUrl: './assessment-answer-delete-dialog.component.html',
})
export class AssessmentAnswerDeleteDialogComponent {
  assessmentAnswer?: IAssessmentAnswer;

  constructor(protected assessmentAnswerService: AssessmentAnswerService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.assessmentAnswerService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
