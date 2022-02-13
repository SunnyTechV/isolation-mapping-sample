import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IQuestion, Question } from '../question.model';
import { QuestionService } from '../service/question.service';
import { QuestionType } from 'app/entities/enumerations/question-type.model';

@Component({
  selector: 'jhi-question-update',
  templateUrl: './question-update.component.html',
})
export class QuestionUpdateComponent implements OnInit {
  isSaving = false;
  questionTypeValues = Object.keys(QuestionType);

  editForm = this.fb.group({
    id: [],
    question: [],
    questionDesc: [],
    questionType: [],
    active: [],
    lastModified: [],
    lastModifiedBy: [],
  });

  constructor(protected questionService: QuestionService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ question }) => {
      if (question.id === undefined) {
        const today = dayjs().startOf('day');
        question.lastModified = today;
      }

      this.updateForm(question);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const question = this.createFromForm();
    if (question.id !== undefined) {
      this.subscribeToSaveResponse(this.questionService.update(question));
    } else {
      this.subscribeToSaveResponse(this.questionService.create(question));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestion>>): void {
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

  protected updateForm(question: IQuestion): void {
    this.editForm.patchValue({
      id: question.id,
      question: question.question,
      questionDesc: question.questionDesc,
      questionType: question.questionType,
      active: question.active,
      lastModified: question.lastModified ? question.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: question.lastModifiedBy,
    });
  }

  protected createFromForm(): IQuestion {
    return {
      ...new Question(),
      id: this.editForm.get(['id'])!.value,
      question: this.editForm.get(['question'])!.value,
      questionDesc: this.editForm.get(['questionDesc'])!.value,
      questionType: this.editForm.get(['questionType'])!.value,
      active: this.editForm.get(['active'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
    };
  }
}
