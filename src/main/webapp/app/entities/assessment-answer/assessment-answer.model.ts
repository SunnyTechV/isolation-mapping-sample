import dayjs from 'dayjs/esm';
import { IAssessment } from 'app/entities/assessment/assessment.model';
import { IQuestion } from 'app/entities/question/question.model';

export interface IAssessmentAnswer {
  id?: number;
  answer?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  assessment?: IAssessment | null;
  question?: IQuestion | null;
}

export class AssessmentAnswer implements IAssessmentAnswer {
  constructor(
    public id?: number,
    public answer?: string | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public assessment?: IAssessment | null,
    public question?: IQuestion | null
  ) {}
}

export function getAssessmentAnswerIdentifier(assessmentAnswer: IAssessmentAnswer): number | undefined {
  return assessmentAnswer.id;
}
