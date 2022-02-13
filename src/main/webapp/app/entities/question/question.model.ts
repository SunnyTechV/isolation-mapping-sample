import dayjs from 'dayjs/esm';
import { IAssessmentAnswer } from 'app/entities/assessment-answer/assessment-answer.model';
import { QuestionType } from 'app/entities/enumerations/question-type.model';

export interface IQuestion {
  id?: number;
  question?: string | null;
  questionDesc?: string | null;
  questionType?: QuestionType | null;
  active?: boolean | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  assessmentAnswers?: IAssessmentAnswer[] | null;
}

export class Question implements IQuestion {
  constructor(
    public id?: number,
    public question?: string | null,
    public questionDesc?: string | null,
    public questionType?: QuestionType | null,
    public active?: boolean | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public assessmentAnswers?: IAssessmentAnswer[] | null
  ) {
    this.active = this.active ?? false;
  }
}

export function getQuestionIdentifier(question: IQuestion): number | undefined {
  return question.id;
}
