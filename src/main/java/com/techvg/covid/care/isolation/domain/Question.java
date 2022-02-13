package com.techvg.covid.care.isolation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.covid.care.isolation.domain.enumeration.QuestionType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Question.
 */
@Entity
@Table(name = "question")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "question")
    private String question;

    @Column(name = "question_desc")
    private String questionDesc;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type")
    private QuestionType questionType;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @OneToMany(mappedBy = "question")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "assessment", "question" }, allowSetters = true)
    private Set<AssessmentAnswer> assessmentAnswers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Question id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return this.question;
    }

    public Question question(String question) {
        this.setQuestion(question);
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionDesc() {
        return this.questionDesc;
    }

    public Question questionDesc(String questionDesc) {
        this.setQuestionDesc(questionDesc);
        return this;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    public QuestionType getQuestionType() {
        return this.questionType;
    }

    public Question questionType(QuestionType questionType) {
        this.setQuestionType(questionType);
        return this;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public Boolean getActive() {
        return this.active;
    }

    public Question active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public Question lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Question lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<AssessmentAnswer> getAssessmentAnswers() {
        return this.assessmentAnswers;
    }

    public void setAssessmentAnswers(Set<AssessmentAnswer> assessmentAnswers) {
        if (this.assessmentAnswers != null) {
            this.assessmentAnswers.forEach(i -> i.setQuestion(null));
        }
        if (assessmentAnswers != null) {
            assessmentAnswers.forEach(i -> i.setQuestion(this));
        }
        this.assessmentAnswers = assessmentAnswers;
    }

    public Question assessmentAnswers(Set<AssessmentAnswer> assessmentAnswers) {
        this.setAssessmentAnswers(assessmentAnswers);
        return this;
    }

    public Question addAssessmentAnswer(AssessmentAnswer assessmentAnswer) {
        this.assessmentAnswers.add(assessmentAnswer);
        assessmentAnswer.setQuestion(this);
        return this;
    }

    public Question removeAssessmentAnswer(AssessmentAnswer assessmentAnswer) {
        this.assessmentAnswers.remove(assessmentAnswer);
        assessmentAnswer.setQuestion(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Question)) {
            return false;
        }
        return id != null && id.equals(((Question) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Question{" +
            "id=" + getId() +
            ", question='" + getQuestion() + "'" +
            ", questionDesc='" + getQuestionDesc() + "'" +
            ", questionType='" + getQuestionType() + "'" +
            ", active='" + getActive() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
