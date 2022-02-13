import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IQuestionsOptions, QuestionsOptions } from '../questions-options.model';
import { QuestionsOptionsService } from '../service/questions-options.service';
import { IQuestion } from 'app/entities/question/question.model';
import { QuestionService } from 'app/entities/question/service/question.service';
import { HealthCondition } from 'app/entities/enumerations/health-condition.model';

@Component({
  selector: 'jhi-questions-options-update',
  templateUrl: './questions-options-update.component.html',
})
export class QuestionsOptionsUpdateComponent implements OnInit {
  isSaving = false;
  healthConditionValues = Object.keys(HealthCondition);

  questionsSharedCollection: IQuestion[] = [];

  editForm = this.fb.group({
    id: [],
    ansOption: [],
    healthCondition: [],
    active: [],
    lastModified: [],
    lastModifiedBy: [],
    question: [],
  });

  constructor(
    protected questionsOptionsService: QuestionsOptionsService,
    protected questionService: QuestionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ questionsOptions }) => {
      if (questionsOptions.id === undefined) {
        const today = dayjs().startOf('day');
        questionsOptions.lastModified = today;
      }

      this.updateForm(questionsOptions);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const questionsOptions = this.createFromForm();
    if (questionsOptions.id !== undefined) {
      this.subscribeToSaveResponse(this.questionsOptionsService.update(questionsOptions));
    } else {
      this.subscribeToSaveResponse(this.questionsOptionsService.create(questionsOptions));
    }
  }

  trackQuestionById(index: number, item: IQuestion): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionsOptions>>): void {
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

  protected updateForm(questionsOptions: IQuestionsOptions): void {
    this.editForm.patchValue({
      id: questionsOptions.id,
      ansOption: questionsOptions.ansOption,
      healthCondition: questionsOptions.healthCondition,
      active: questionsOptions.active,
      lastModified: questionsOptions.lastModified ? questionsOptions.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: questionsOptions.lastModifiedBy,
      question: questionsOptions.question,
    });

    this.questionsSharedCollection = this.questionService.addQuestionToCollectionIfMissing(
      this.questionsSharedCollection,
      questionsOptions.question
    );
  }

  protected loadRelationshipsOptions(): void {
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

  protected createFromForm(): IQuestionsOptions {
    return {
      ...new QuestionsOptions(),
      id: this.editForm.get(['id'])!.value,
      ansOption: this.editForm.get(['ansOption'])!.value,
      healthCondition: this.editForm.get(['healthCondition'])!.value,
      active: this.editForm.get(['active'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      question: this.editForm.get(['question'])!.value,
    };
  }
}
