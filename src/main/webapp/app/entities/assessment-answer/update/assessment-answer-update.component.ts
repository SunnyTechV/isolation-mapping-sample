import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IAssessmentAnswer, AssessmentAnswer } from '../assessment-answer.model';
import { AssessmentAnswerService } from '../service/assessment-answer.service';
import { IAssessment } from 'app/entities/assessment/assessment.model';
import { AssessmentService } from 'app/entities/assessment/service/assessment.service';
import { IQuestion } from 'app/entities/question/question.model';
import { QuestionService } from 'app/entities/question/service/question.service';

@Component({
  selector: 'jhi-assessment-answer-update',
  templateUrl: './assessment-answer-update.component.html',
})
export class AssessmentAnswerUpdateComponent implements OnInit {
  isSaving = false;

  assessmentsSharedCollection: IAssessment[] = [];
  questionsSharedCollection: IQuestion[] = [];

  editForm = this.fb.group({
    id: [],
    answer: [],
    lastModified: [],
    lastModifiedBy: [],
    assessment: [],
    question: [],
  });

  constructor(
    protected assessmentAnswerService: AssessmentAnswerService,
    protected assessmentService: AssessmentService,
    protected questionService: QuestionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ assessmentAnswer }) => {
      if (assessmentAnswer.id === undefined) {
        const today = dayjs().startOf('day');
        assessmentAnswer.lastModified = today;
      }

      this.updateForm(assessmentAnswer);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const assessmentAnswer = this.createFromForm();
    if (assessmentAnswer.id !== undefined) {
      this.subscribeToSaveResponse(this.assessmentAnswerService.update(assessmentAnswer));
    } else {
      this.subscribeToSaveResponse(this.assessmentAnswerService.create(assessmentAnswer));
    }
  }

  trackAssessmentById(index: number, item: IAssessment): number {
    return item.id!;
  }

  trackQuestionById(index: number, item: IQuestion): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAssessmentAnswer>>): void {
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

  protected updateForm(assessmentAnswer: IAssessmentAnswer): void {
    this.editForm.patchValue({
      id: assessmentAnswer.id,
      answer: assessmentAnswer.answer,
      lastModified: assessmentAnswer.lastModified ? assessmentAnswer.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: assessmentAnswer.lastModifiedBy,
      assessment: assessmentAnswer.assessment,
      question: assessmentAnswer.question,
    });

    this.assessmentsSharedCollection = this.assessmentService.addAssessmentToCollectionIfMissing(
      this.assessmentsSharedCollection,
      assessmentAnswer.assessment
    );
    this.questionsSharedCollection = this.questionService.addQuestionToCollectionIfMissing(
      this.questionsSharedCollection,
      assessmentAnswer.question
    );
  }

  protected loadRelationshipsOptions(): void {
    this.assessmentService
      .query()
      .pipe(map((res: HttpResponse<IAssessment[]>) => res.body ?? []))
      .pipe(
        map((assessments: IAssessment[]) =>
          this.assessmentService.addAssessmentToCollectionIfMissing(assessments, this.editForm.get('assessment')!.value)
        )
      )
      .subscribe((assessments: IAssessment[]) => (this.assessmentsSharedCollection = assessments));

    this.questionService
      .query()
      .pipe(map((res: HttpResponse<IQuestion[]>) => res.body ?? []))
      .pipe(
        map((questions: IQuestion[]) =>
          this.questionService.addQuestionToCollectionIfMissing(questions, this.editForm.get('question')!.value)
        )
      )
      .subscribe((questions: IQuestion[]) => (this.questionsSharedCollection = questions));
  }

  protected createFromForm(): IAssessmentAnswer {
    return {
      ...new AssessmentAnswer(),
      id: this.editForm.get(['id'])!.value,
      answer: this.editForm.get(['answer'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      assessment: this.editForm.get(['assessment'])!.value,
      question: this.editForm.get(['question'])!.value,
    };
  }
}
