package com.techvg.covid.care.isolation.service.criteria;

import com.techvg.covid.care.isolation.domain.enumeration.HealthCondition;
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
 * Criteria class for the {@link com.techvg.covid.care.isolation.domain.QuestionsOptions} entity. This class is used
 * in {@link com.techvg.covid.care.isolation.web.rest.QuestionsOptionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /questions-options?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class QuestionsOptionsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering HealthCondition
     */
    public static class HealthConditionFilter extends Filter<HealthCondition> {

        public HealthConditionFilter() {}

        public HealthConditionFilter(HealthConditionFilter filter) {
            super(filter);
        }

        @Override
        public HealthConditionFilter copy() {
            return new HealthConditionFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter ansOption;

    private HealthConditionFilter healthCondition;

    private BooleanFilter active;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter questionId;

    private Boolean distinct;

    public QuestionsOptionsCriteria() {}

    public QuestionsOptionsCriteria(QuestionsOptionsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.ansOption = other.ansOption == null ? null : other.ansOption.copy();
        this.healthCondition = other.healthCondition == null ? null : other.healthCondition.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.questionId = other.questionId == null ? null : other.questionId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public QuestionsOptionsCriteria copy() {
        return new QuestionsOptionsCriteria(this);
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

    public StringFilter getAnsOption() {
        return ansOption;
    }

    public StringFilter ansOption() {
        if (ansOption == null) {
            ansOption = new StringFilter();
        }
        return ansOption;
    }

    public void setAnsOption(StringFilter ansOption) {
        this.ansOption = ansOption;
    }

    public HealthConditionFilter getHealthCondition() {
        return healthCondition;
    }

    public HealthConditionFilter healthCondition() {
        if (healthCondition == null) {
            healthCondition = new HealthConditionFilter();
        }
        return healthCondition;
    }

    public void setHealthCondition(HealthConditionFilter healthCondition) {
        this.healthCondition = healthCondition;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public BooleanFilter active() {
        if (active == null) {
            active = new BooleanFilter();
        }
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
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

    public LongFilter getQuestionId() {
        return questionId;
    }

    public LongFilter questionId() {
        if (questionId == null) {
            questionId = new LongFilter();
        }
        return questionId;
    }

    public void setQuestionId(LongFilter questionId) {
        this.questionId = questionId;
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
        final QuestionsOptionsCriteria that = (QuestionsOptionsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(ansOption, that.ansOption) &&
            Objects.equals(healthCondition, that.healthCondition) &&
            Objects.equals(active, that.active) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(questionId, that.questionId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ansOption, healthCondition, active, lastModified, lastModifiedBy, questionId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionsOptionsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (ansOption != null ? "ansOption=" + ansOption + ", " : "") +
            (healthCondition != null ? "healthCondition=" + healthCondition + ", " : "") +
            (active != null ? "active=" + active + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (questionId != null ? "questionId=" + questionId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
