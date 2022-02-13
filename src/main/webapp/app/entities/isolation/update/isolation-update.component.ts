import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IIsolation, Isolation } from '../isolation.model';
import { IsolationService } from '../service/isolation.service';
import { IIsolationDetails } from 'app/entities/isolation-details/isolation-details.model';
import { IsolationDetailsService } from 'app/entities/isolation-details/service/isolation-details.service';
import { IsolationStatus } from 'app/entities/enumerations/isolation-status.model';
import { HealthCondition } from 'app/entities/enumerations/health-condition.model';

@Component({
  selector: 'jhi-isolation-update',
  templateUrl: './isolation-update.component.html',
})
export class IsolationUpdateComponent implements OnInit {
  isSaving = false;
  isolationStatusValues = Object.keys(IsolationStatus);
  healthConditionValues = Object.keys(HealthCondition);

  isolationDetailsCollection: IIsolationDetails[] = [];

  editForm = this.fb.group({
    id: [],
    icmrId: [],
    rtpcrId: [],
    ratId: [],
    firstName: [],
    lastName: [],
    latitude: [],
    longitude: [],
    email: [],
    imageUrl: [],
    activated: [],
    mobileNo: [null, [Validators.required]],
    passwordHash: [null, [Validators.required]],
    secondaryContactNo: [],
    aadharCardNo: [null, [Validators.required]],
    status: [],
    age: [],
    gender: [],
    stateId: [],
    districtId: [],
    talukaId: [],
    cityId: [],
    address: [],
    pincode: [],
    collectionDate: [],
    hospitalized: [],
    hospitalId: [],
    addressLatitude: [],
    addressLongitude: [],
    currentLatitude: [],
    currentLongitude: [],
    hospitalizationDate: [],
    healthCondition: [],
    remarks: [],
    symptomatic: [],
    ccmsLogin: [],
    selfRegistered: [],
    lastModified: [],
    lastModifiedBy: [],
    isolationStartDate: [],
    isolationEndDate: [],
    tvgIsolationUserId: [],
    isolationDetails: [],
  });

  constructor(
    protected isolationService: IsolationService,
    protected isolationDetailsService: IsolationDetailsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ isolation }) => {
      if (isolation.id === undefined) {
        const today = dayjs().startOf('day');
        isolation.collectionDate = today;
        isolation.hospitalizationDate = today;
        isolation.lastModified = today;
        isolation.isolationStartDate = today;
        isolation.isolationEndDate = today;
      }

      this.updateForm(isolation);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const isolation = this.createFromForm();
    if (isolation.id !== undefined) {
      this.subscribeToSaveResponse(this.isolationService.update(isolation));
    } else {
      this.subscribeToSaveResponse(this.isolationService.create(isolation));
    }
  }

  trackIsolationDetailsById(index: number, item: IIsolationDetails): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIsolation>>): void {
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

  protected updateForm(isolation: IIsolation): void {
    this.editForm.patchValue({
      id: isolation.id,
      icmrId: isolation.icmrId,
      rtpcrId: isolation.rtpcrId,
      ratId: isolation.ratId,
      firstName: isolation.firstName,
      lastName: isolation.lastName,
      latitude: isolation.latitude,
      longitude: isolation.longitude,
      email: isolation.email,
      imageUrl: isolation.imageUrl,
      activated: isolation.activated,
      mobileNo: isolation.mobileNo,
      passwordHash: isolation.passwordHash,
      secondaryContactNo: isolation.secondaryContactNo,
      aadharCardNo: isolation.aadharCardNo,
      status: isolation.status,
      age: isolation.age,
      gender: isolation.gender,
      stateId: isolation.stateId,
      districtId: isolation.districtId,
      talukaId: isolation.talukaId,
      cityId: isolation.cityId,
      address: isolation.address,
      pincode: isolation.pincode,
      collectionDate: isolation.collectionDate ? isolation.collectionDate.format(DATE_TIME_FORMAT) : null,
      hospitalized: isolation.hospitalized,
      hospitalId: isolation.hospitalId,
      addressLatitude: isolation.addressLatitude,
      addressLongitude: isolation.addressLongitude,
      currentLatitude: isolation.currentLatitude,
      currentLongitude: isolation.currentLongitude,
      hospitalizationDate: isolation.hospitalizationDate ? isolation.hospitalizationDate.format(DATE_TIME_FORMAT) : null,
      healthCondition: isolation.healthCondition,
      remarks: isolation.remarks,
      symptomatic: isolation.symptomatic,
      ccmsLogin: isolation.ccmsLogin,
      selfRegistered: isolation.selfRegistered,
      lastModified: isolation.lastModified ? isolation.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: isolation.lastModifiedBy,
      isolationStartDate: isolation.isolationStartDate ? isolation.isolationStartDate.format(DATE_TIME_FORMAT) : null,
      isolationEndDate: isolation.isolationEndDate ? isolation.isolationEndDate.format(DATE_TIME_FORMAT) : null,
      tvgIsolationUserId: isolation.tvgIsolationUserId,
      isolationDetails: isolation.isolationDetails,
    });

    this.isolationDetailsCollection = this.isolationDetailsService.addIsolationDetailsToCollectionIfMissing(
      this.isolationDetailsCollection,
      isolation.isolationDetails
    );
  }

  protected loadRelationshipsOptions(): void {
    this.isolationDetailsService
      .query({ 'isolationId.specified': 'false' })
      .pipe(map((res: HttpResponse<IIsolationDetails[]>) => res.body ?? []))
      .pipe(
        map((isolationDetails: IIsolationDetails[]) =>
          this.isolationDetailsService.addIsolationDetailsToCollectionIfMissing(
            isolationDetails,
            this.editForm.get('isolationDetails')!.value
          )
        )
      )
      .subscribe((isolationDetails: IIsolationDetails[]) => (this.isolationDetailsCollection = isolationDetails));
  }

  protected createFromForm(): IIsolation {
    return {
      ...new Isolation(),
      id: this.editForm.get(['id'])!.value,
      icmrId: this.editForm.get(['icmrId'])!.value,
      rtpcrId: this.editForm.get(['rtpcrId'])!.value,
      ratId: this.editForm.get(['ratId'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      latitude: this.editForm.get(['latitude'])!.value,
      longitude: this.editForm.get(['longitude'])!.value,
      email: this.editForm.get(['email'])!.value,
      imageUrl: this.editForm.get(['imageUrl'])!.value,
      activated: this.editForm.get(['activated'])!.value,
      mobileNo: this.editForm.get(['mobileNo'])!.value,
      passwordHash: this.editForm.get(['passwordHash'])!.value,
      secondaryContactNo: this.editForm.get(['secondaryContactNo'])!.value,
      aadharCardNo: this.editForm.get(['aadharCardNo'])!.value,
      status: this.editForm.get(['status'])!.value,
      age: this.editForm.get(['age'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      stateId: this.editForm.get(['stateId'])!.value,
      districtId: this.editForm.get(['districtId'])!.value,
      talukaId: this.editForm.get(['talukaId'])!.value,
      cityId: this.editForm.get(['cityId'])!.value,
      address: this.editForm.get(['address'])!.value,
      pincode: this.editForm.get(['pincode'])!.value,
      collectionDate: this.editForm.get(['collectionDate'])!.value
        ? dayjs(this.editForm.get(['collectionDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      hospitalized: this.editForm.get(['hospitalized'])!.value,
      hospitalId: this.editForm.get(['hospitalId'])!.value,
      addressLatitude: this.editForm.get(['addressLatitude'])!.value,
      addressLongitude: this.editForm.get(['addressLongitude'])!.value,
      currentLatitude: this.editForm.get(['currentLatitude'])!.value,
      currentLongitude: this.editForm.get(['currentLongitude'])!.value,
      hospitalizationDate: this.editForm.get(['hospitalizationDate'])!.value
        ? dayjs(this.editForm.get(['hospitalizationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      healthCondition: this.editForm.get(['healthCondition'])!.value,
      remarks: this.editForm.get(['remarks'])!.value,
      symptomatic: this.editForm.get(['symptomatic'])!.value,
      ccmsLogin: this.editForm.get(['ccmsLogin'])!.value,
      selfRegistered: this.editForm.get(['selfRegistered'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      isolationStartDate: this.editForm.get(['isolationStartDate'])!.value
        ? dayjs(this.editForm.get(['isolationStartDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      isolationEndDate: this.editForm.get(['isolationEndDate'])!.value
        ? dayjs(this.editForm.get(['isolationEndDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      tvgIsolationUserId: this.editForm.get(['tvgIsolationUserId'])!.value,
      isolationDetails: this.editForm.get(['isolationDetails'])!.value,
    };
  }
}
