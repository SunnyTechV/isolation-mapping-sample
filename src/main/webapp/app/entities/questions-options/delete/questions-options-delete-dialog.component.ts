import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IQuestionsOptions } from '../questions-options.model';
import { QuestionsOptionsService } from '../service/questions-options.service';

@Component({
  templateUrl: './questions-options-delete-dialog.component.html',
})
export class QuestionsOptionsDeleteDialogComponent {
  questionsOptions?: IQuestionsOptions;

  constructor(protected questionsOptionsService: QuestionsOptionsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.questionsOptionsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
