import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IIsolationDetails } from '../isolation-details.model';
import { IsolationDetailsService } from '../service/isolation-details.service';

@Component({
  templateUrl: './isolation-details-delete-dialog.component.html',
})
export class IsolationDetailsDeleteDialogComponent {
  isolationDetails?: IIsolationDetails;

  constructor(protected isolationDetailsService: IsolationDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.isolationDetailsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
