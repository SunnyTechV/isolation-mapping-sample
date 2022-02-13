package com.techvg.covid.care.isolation.service.dto;

import com.techvg.covid.care.isolation.domain.enumeration.HealthCondition;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.covid.care.isolation.domain.QuestionsOptions} entity.
 */
public class QuestionsOptionsDTO implements Serializable {

    private Long id;

    private String ansOption;

    private HealthCondition healthCondition;

    private Boolean active;

    private Instant lastModified;

    private String lastModifiedBy;

    private QuestionDTO question;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnsOption() {
        return ansOption;
    }

    public void setAnsOption(String ansOption) {
        this.ansOption = ansOption;
    }

    public HealthCondition getHealthCondition() {
        return healthCondition;
    }

    public void setHealthCondition(HealthCondition healthCondition) {
        this.healthCondition = healthCondition;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public QuestionDTO getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDTO question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionsOptionsDTO)) {
            return false;
        }

        QuestionsOptionsDTO questionsOptionsDTO = (QuestionsOptionsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, questionsOptionsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionsOptionsDTO{" +
            "id=" + getId() +
            ", ansOption='" + getAnsOption() + "'" +
            ", healthCondition='" + getHealthCondition() + "'" +
            ", active='" + getActive() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", question=" + getQuestion() +
            "}";
    }
}
