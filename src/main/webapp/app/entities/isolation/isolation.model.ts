import dayjs from 'dayjs/esm';
import { IIsolationDetails } from 'app/entities/isolation-details/isolation-details.model';
import { IsolationStatus } from 'app/entities/enumerations/isolation-status.model';
import { HealthCondition } from 'app/entities/enumerations/health-condition.model';

export interface IIsolation {
  id?: number;
  icmrId?: string | null;
  rtpcrId?: string | null;
  ratId?: string | null;
  firstName?: string | null;
  lastName?: string | null;
  latitude?: string | null;
  longitude?: string | null;
  email?: string | null;
  imageUrl?: string | null;
  activated?: boolean | null;
  mobileNo?: string;
  passwordHash?: string;
  secondaryContactNo?: string | null;
  aadharCardNo?: string;
  status?: IsolationStatus | null;
  age?: string | null;
  gender?: string | null;
  stateId?: number | null;
  districtId?: number | null;
  talukaId?: number | null;
  cityId?: number | null;
  address?: string | null;
  pincode?: string | null;
  collectionDate?: dayjs.Dayjs | null;
  hospitalized?: boolean | null;
  hospitalId?: number | null;
  addressLatitude?: string | null;
  addressLongitude?: string | null;
  currentLatitude?: string | null;
  currentLongitude?: string | null;
  hospitalizationDate?: dayjs.Dayjs | null;
  healthCondition?: HealthCondition | null;
  remarks?: string | null;
  symptomatic?: boolean | null;
  ccmsLogin?: string | null;
  selfRegistered?: boolean | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  isolationStartDate?: dayjs.Dayjs | null;
  isolationEndDate?: dayjs.Dayjs | null;
  tvgIsolationUserId?: number | null;
  isolationDetails?: IIsolationDetails | null;
}

export class Isolation implements IIsolation {
  constructor(
    public id?: number,
    public icmrId?: string | null,
    public rtpcrId?: string | null,
    public ratId?: string | null,
    public firstName?: string | null,
    public lastName?: string | null,
    public latitude?: string | null,
    public longitude?: string | null,
    public email?: string | null,
    public imageUrl?: string | null,
    public activated?: boolean | null,
    public mobileNo?: string,
    public passwordHash?: string,
    public secondaryContactNo?: string | null,
    public aadharCardNo?: string,
    public status?: IsolationStatus | null,
    public age?: string | null,
    public gender?: string | null,
    public stateId?: number | null,
    public districtId?: number | null,
    public talukaId?: number | null,
    public cityId?: number | null,
    public address?: string | null,
    public pincode?: string | null,
    public collectionDate?: dayjs.Dayjs | null,
    public hospitalized?: boolean | null,
    public hospitalId?: number | null,
    public addressLatitude?: string | null,
    public addressLongitude?: string | null,
    public currentLatitude?: string | null,
    public currentLongitude?: string | null,
    public hospitalizationDate?: dayjs.Dayjs | null,
    public healthCondition?: HealthCondition | null,
    public remarks?: string | null,
    public symptomatic?: boolean | null,
    public ccmsLogin?: string | null,
    public selfRegistered?: boolean | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public isolationStartDate?: dayjs.Dayjs | null,
    public isolationEndDate?: dayjs.Dayjs | null,
    public tvgIsolationUserId?: number | null,
    public isolationDetails?: IIsolationDetails | null
  ) {
    this.activated = this.activated ?? false;
    this.hospitalized = this.hospitalized ?? false;
    this.symptomatic = this.symptomatic ?? false;
    this.selfRegistered = this.selfRegistered ?? false;
  }
}

export function getIsolationIdentifier(isolation: IIsolation): number | undefined {
  return isolation.id;
}
