import dayjs from 'dayjs/esm';
import { IIsolation } from 'app/entities/isolation/isolation.model';

export interface IAssessment {
  id?: number;
  assessmentDate?: dayjs.Dayjs;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  isolation?: IIsolation | null;
}

export class Assessment implements IAssessment {
  constructor(
    public id?: number,
    public assessmentDate?: dayjs.Dayjs,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public isolation?: IIsolation | null
  ) {}
}

export function getAssessmentIdentifier(assessment: IAssessment): number | undefined {
  return assessment.id;
}
