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
 * Criteria class for the {@link com.techvg.covid.care.isolation.domain.Assessment} entity. This class is used
 * in {@link com.techvg.covid.care.isolation.web.rest.AssessmentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /assessments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AssessmentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter assessmentDate;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter isolationId;

    private Boolean distinct;

    public AssessmentCriteria() {}

    public AssessmentCriteria(AssessmentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.assessmentDate = other.assessmentDate == null ? null : other.assessmentDate.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.isolationId = other.isolationId == null ? null : other.isolationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AssessmentCriteria copy() {
        return new AssessmentCriteria(this);
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

    public InstantFilter getAssessmentDate() {
        return assessmentDate;
    }

    public InstantFilter assessmentDate() {
        if (assessmentDate == null) {
            assessmentDate = new InstantFilter();
        }
        return assessmentDate;
    }

    public void setAssessmentDate(InstantFilter assessmentDate) {
        this.assessmentDate = assessmentDate;
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

    public LongFilter getIsolationId() {
        return isolationId;
    }

    public LongFilter isolationId() {
        if (isolationId == null) {
            isolationId = new LongFilter();
        }
        return isolationId;
    }

    public void setIsolationId(LongFilter isolationId) {
        this.isolationId = isolationId;
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
        final AssessmentCriteria that = (AssessmentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(assessmentDate, that.assessmentDate) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(isolationId, that.isolationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, assessmentDate, lastModified, lastModifiedBy, isolationId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssessmentCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (assessmentDate != null ? "assessmentDate=" + assessmentDate + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (isolationId != null ? "isolationId=" + isolationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
