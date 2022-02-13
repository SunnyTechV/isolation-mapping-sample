package com.techvg.covid.care.isolation.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.covid.care.isolation.domain.AssessmentAnswer} entity.
 */
public class AssessmentAnswerDTO implements Serializable {

    private Long id;

    private String answer;

    private Instant lastModified;

    private String lastModifiedBy;

    private AssessmentDTO assessment;

    private QuestionDTO question;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    public AssessmentDTO getAssessment() {
        return assessment;
    }

    public void setAssessment(AssessmentDTO assessment) {
        this.assessment = assessment;
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
        if (!(o instanceof AssessmentAnswerDTO)) {
            return false;
        }

        AssessmentAnswerDTO assessmentAnswerDTO = (AssessmentAnswerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, assessmentAnswerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssessmentAnswerDTO{" +
            "id=" + getId() +
            ", answer='" + getAnswer() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", assessment=" + getAssessment() +
            ", question=" + getQuestion() +
            "}";
    }
}
