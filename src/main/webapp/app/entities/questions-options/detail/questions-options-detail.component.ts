import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuestionsOptions } from '../questions-options.model';

@Component({
  selector: 'jhi-questions-options-detail',
  templateUrl: './questions-options-detail.component.html',
})
export class QuestionsOptionsDetailComponent implements OnInit {
  questionsOptions: IQuestionsOptions | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ questionsOptions }) => {
      this.questionsOptions = questionsOptions;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
