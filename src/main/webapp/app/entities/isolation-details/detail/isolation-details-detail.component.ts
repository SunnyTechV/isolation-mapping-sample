import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIsolationDetails } from '../isolation-details.model';

@Component({
  selector: 'jhi-isolation-details-detail',
  templateUrl: './isolation-details-detail.component.html',
})
export class IsolationDetailsDetailComponent implements OnInit {
  isolationDetails: IIsolationDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ isolationDetails }) => {
      this.isolationDetails = isolationDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
