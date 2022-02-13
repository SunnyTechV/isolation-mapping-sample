import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IIsolationDetails, IsolationDetails } from '../isolation-details.model';
import { IsolationDetailsService } from '../service/isolation-details.service';

@Component({
  selector: 'jhi-isolation-details-update',
  templateUrl: './isolation-details-update.component.html',
})
export class IsolationDetailsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    referredDrName: [],
    referredDrMobile: [],
    prescriptionUrl: [],
    reportUrl: [],
    remarks: [],
    selfRegistered: [],
    lastAssessment: [],
    lastModified: [],
    lastModifiedBy: [],
  });

  constructor(
    protected isolationDetailsService: IsolationDetailsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ isolationDetails }) => {
      if (isolationDetails.id === undefined) {
        const today = dayjs().startOf('day');
        isolationDetails.lastAssessment = today;
        isolationDetails.lastModified = today;
      }

      this.updateForm(isolationDetails);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const isolationDetails = this.createFromForm();
    if (isolationDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.isolationDetailsService.update(isolationDetails));
    } else {
      this.subscribeToSaveResponse(this.isolationDetailsService.create(isolationDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIsolationDetails>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(isolationDetails: IIsolationDetails): void {
    this.editForm.patchValue({
      id: isolationDetails.id,
      referredDrName: isolationDetails.referredDrName,
      referredDrMobile: isolationDetails.referredDrMobile,
      prescriptionUrl: isolationDetails.prescriptionUrl,
      reportUrl: isolationDetails.reportUrl,
      remarks: isolationDetails.remarks,
      selfRegistered: isolationDetails.selfRegistered,
      lastAssessment: isolationDetails.lastAssessment ? isolationDetails.lastAssessment.format(DATE_TIME_FORMAT) : null,
      lastModified: isolationDetails.lastModified ? isolationDetails.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: isolationDetails.lastModifiedBy,
    });
  }

  protected createFromForm(): IIsolationDetails {
    return {
      ...new IsolationDetails(),
      id: this.editForm.get(['id'])!.value,
      referredDrName: this.editForm.get(['referredDrName'])!.value,
      referredDrMobile: this.editForm.get(['referredDrMobile'])!.value,
      prescriptionUrl: this.editForm.get(['prescriptionUrl'])!.value,
      reportUrl: this.editForm.get(['reportUrl'])!.value,
      remarks: this.editForm.get(['remarks'])!.value,
      selfRegistered: this.editForm.get(['selfRegistered'])!.value,
      lastAssessment: this.editForm.get(['lastAssessment'])!.value
        ? dayjs(this.editForm.get(['lastAssessment'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
    };
  }
}
