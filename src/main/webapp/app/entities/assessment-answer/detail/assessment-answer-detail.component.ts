import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAssessmentAnswer } from '../assessment-answer.model';

@Component({
  selector: 'jhi-assessment-answer-detail',
  templateUrl: './assessment-answer-detail.component.html',
})
export class AssessmentAnswerDetailComponent implements OnInit {
  assessmentAnswer: IAssessmentAnswer | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ assessmentAnswer }) => {
      this.assessmentAnswer = assessmentAnswer;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
