import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIsolation } from '../isolation.model';

@Component({
  selector: 'jhi-isolation-detail',
  templateUrl: './isolation-detail.component.html',
})
export class IsolationDetailComponent implements OnInit {
  isolation: IIsolation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ isolation }) => {
      this.isolation = isolation;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
