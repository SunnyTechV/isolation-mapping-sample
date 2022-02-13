import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IIsolation } from '../isolation.model';
import { IsolationService } from '../service/isolation.service';

@Component({
  templateUrl: './isolation-delete-dialog.component.html',
})
export class IsolationDeleteDialogComponent {
  isolation?: IIsolation;

  constructor(protected isolationService: IsolationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.isolationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
