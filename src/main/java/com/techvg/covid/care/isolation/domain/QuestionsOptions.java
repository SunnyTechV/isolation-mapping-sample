package com.techvg.covid.care.isolation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.covid.care.isolation.domain.enumeration.HealthCondition;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A QuestionsOptions.
 */
@Entity
@Table(name = "questions_options")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QuestionsOptions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ans_option")
    private String ansOption;

    @Enumerated(EnumType.STRING)
    @Column(name = "health_condition")
    private HealthCondition healthCondition;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = { "assessmentAnswers" }, allowSetters = true)
    private Question question;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public QuestionsOptions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnsOption() {
        return this.ansOption;
    }

    public QuestionsOptions ansOption(String ansOption) {
        this.setAnsOption(ansOption);
        return this;
    }

    public void setAnsOption(String ansOption) {
        this.ansOption = ansOption;
    }

    public HealthCondition getHealthCondition() {
        return this.healthCondition;
    }

    public QuestionsOptions healthCondition(HealthCondition healthCondition) {
        this.setHealthCondition(healthCondition);
        return this;
    }

    public void setHealthCondition(HealthCondition healthCondition) {
        this.healthCondition = healthCondition;
    }

    public Boolean getActive() {
        return this.active;
    }

    public QuestionsOptions active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public QuestionsOptions lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public QuestionsOptions lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Question getQuestion() {
        return this.question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public QuestionsOptions question(Question question) {
        this.setQuestion(question);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionsOptions)) {
            return false;
        }
        return id != null && id.equals(((QuestionsOptions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionsOptions{" +
            "id=" + getId() +
            ", ansOption='" + getAnsOption() + "'" +
            ", healthCondition='" + getHealthCondition() + "'" +
            ", active='" + getActive() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
