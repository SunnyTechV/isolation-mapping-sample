import dayjs from 'dayjs/esm';

export interface IIsolationDetails {
  id?: number;
  referredDrName?: string | null;
  referredDrMobile?: string | null;
  prescriptionUrl?: string | null;
  reportUrl?: string | null;
  remarks?: string | null;
  selfRegistered?: boolean | null;
  lastAssessment?: dayjs.Dayjs | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
}

export class IsolationDetails implements IIsolationDetails {
  constructor(
    public id?: number,
    public referredDrName?: string | null,
    public referredDrMobile?: string | null,
    public prescriptionUrl?: string | null,
    public reportUrl?: string | null,
    public remarks?: string | null,
    public selfRegistered?: boolean | null,
    public lastAssessment?: dayjs.Dayjs | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null
  ) {
    this.selfRegistered = this.selfRegistered ?? false;
  }
}

export function getIsolationDetailsIdentifier(isolationDetails: IIsolationDetails): number | undefined {
  return isolationDetails.id;
}
