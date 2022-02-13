package com.techvg.covid.care.isolation.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.techvg.covid.care.isolation.domain.IsolationDetails} entity. This class is used
 * in {@link com.techvg.covid.care.isolation.web.rest.IsolationDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /isolation-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class IsolationDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter referredDrName;

    private StringFilter referredDrMobile;

    private StringFilter prescriptionUrl;

    private StringFilter reportUrl;

    private StringFilter remarks;

    private BooleanFilter selfRegistered;

    private InstantFilter lastAssessment;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public IsolationDetailsCriteria() {}

    public IsolationDetailsCriteria(IsolationDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.referredDrName = other.referredDrName == null ? null : other.referredDrName.copy();
        this.referredDrMobile = other.referredDrMobile == null ? null : other.referredDrMobile.copy();
        this.prescriptionUrl = other.prescriptionUrl == null ? null : other.prescriptionUrl.copy();
        this.reportUrl = other.reportUrl == null ? null : other.reportUrl.copy();
        this.remarks = other.remarks == null ? null : other.remarks.copy();
        this.selfRegistered = other.selfRegistered == null ? null : other.selfRegistered.copy();
        this.lastAssessment = other.lastAssessment == null ? null : other.lastAssessment.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public IsolationDetailsCriteria copy() {
        return new IsolationDetailsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getReferredDrName() {
        return referredDrName;
    }

    public StringFilter referredDrName() {
        if (referredDrName == null) {
            referredDrName = new StringFilter();
        }
        return referredDrName;
    }

    public void setReferredDrName(StringFilter referredDrName) {
        this.referredDrName = referredDrName;
    }

    public StringFilter getReferredDrMobile() {
        return referredDrMobile;
    }

    public StringFilter referredDrMobile() {
        if (referredDrMobile == null) {
            referredDrMobile = new StringFilter();
        }
        return referredDrMobile;
    }

    public void setReferredDrMobile(StringFilter referredDrMobile) {
        this.referredDrMobile = referredDrMobile;
    }

    public StringFilter getPrescriptionUrl() {
        return prescriptionUrl;
    }

    public StringFilter prescriptionUrl() {
        if (prescriptionUrl == null) {
            prescriptionUrl = new StringFilter();
        }
        return prescriptionUrl;
    }

    public void setPrescriptionUrl(StringFilter prescriptionUrl) {
        this.prescriptionUrl = prescriptionUrl;
    }

    public StringFilter getReportUrl() {
        return reportUrl;
    }

    public StringFilter reportUrl() {
        if (reportUrl == null) {
            reportUrl = new StringFilter();
        }
        return reportUrl;
    }

    public void setReportUrl(StringFilter reportUrl) {
        this.reportUrl = reportUrl;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public StringFilter remarks() {
        if (remarks == null) {
            remarks = new StringFilter();
        }
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
    }

    public BooleanFilter getSelfRegistered() {
        return selfRegistered;
    }

    public BooleanFilter selfRegistered() {
        if (selfRegistered == null) {
            selfRegistered = new BooleanFilter();
        }
        return selfRegistered;
    }

    public void setSelfRegistered(BooleanFilter selfRegistered) {
        this.selfRegistered = selfRegistered;
    }

    public InstantFilter getLastAssessment() {
        return lastAssessment;
    }

    public InstantFilter lastAssessment() {
        if (lastAssessment == null) {
            lastAssessment = new InstantFilter();
        }
        return lastAssessment;
    }

    public void setLastAssessment(InstantFilter lastAssessment) {
        this.lastAssessment = lastAssessment;
    }

    public InstantFilter getLastModified() {
        return lastModified;
    }

    public InstantFilter lastModified() {
        if (lastModified == null) {
            lastModified = new InstantFilter();
        }
        return lastModified;
    }

    public void setLastModified(InstantFilter lastModified) {
        this.lastModified = lastModified;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final IsolationDetailsCriteria that = (IsolationDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(referredDrName, that.referredDrName) &&
            Objects.equals(referredDrMobile, that.referredDrMobile) &&
            Objects.equals(prescriptionUrl, that.prescriptionUrl) &&
            Objects.equals(reportUrl, that.reportUrl) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(selfRegistered, that.selfRegistered) &&
            Objects.equals(lastAssessment, that.lastAssessment) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            referredDrName,
            referredDrMobile,
            prescriptionUrl,
            reportUrl,
            remarks,
            selfRegistered,
            lastAssessment,
            lastModified,
            lastModifiedBy,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IsolationDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (referredDrName != null ? "referredDrName=" + referredDrName + ", " : "") +
            (referredDrMobile != null ? "referredDrMobile=" + referredDrMobile + ", " : "") +
            (prescriptionUrl != null ? "prescriptionUrl=" + prescriptionUrl + ", " : "") +
            (reportUrl != null ? "reportUrl=" + reportUrl + ", " : "") +
            (remarks != null ? "remarks=" + remarks + ", " : "") +
            (selfRegistered != null ? "selfRegistered=" + selfRegistered + ", " : "") +
            (lastAssessment != null ? "lastAssessment=" + lastAssessment + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
