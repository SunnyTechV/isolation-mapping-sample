package com.techvg.covid.care.isolation.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A IsolationDetails.
 */
@Entity
@Table(name = "isolation_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IsolationDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "referred_dr_name")
    private String referredDrName;

    @Column(name = "referred_dr_mobile")
    private String referredDrMobile;

    @Column(name = "prescription_url")
    private String prescriptionUrl;

    @Column(name = "report_url")
    private String reportUrl;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "self_registered")
    private Boolean selfRegistered;

    @Column(name = "last_assessment")
    private Instant lastAssessment;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IsolationDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferredDrName() {
        return this.referredDrName;
    }

    public IsolationDetails referredDrName(String referredDrName) {
        this.setReferredDrName(referredDrName);
        return this;
    }

    public void setReferredDrName(String referredDrName) {
        this.referredDrName = referredDrName;
    }

    public String getReferredDrMobile() {
        return this.referredDrMobile;
    }

    public IsolationDetails referredDrMobile(String referredDrMobile) {
        this.setReferredDrMobile(referredDrMobile);
        return this;
    }

    public void setReferredDrMobile(String referredDrMobile) {
        this.referredDrMobile = referredDrMobile;
    }

    public String getPrescriptionUrl() {
        return this.prescriptionUrl;
    }

    public IsolationDetails prescriptionUrl(String prescriptionUrl) {
        this.setPrescriptionUrl(prescriptionUrl);
        return this;
    }

    public void setPrescriptionUrl(String prescriptionUrl) {
        this.prescriptionUrl = prescriptionUrl;
    }

    public String getReportUrl() {
        return this.reportUrl;
    }

    public IsolationDetails reportUrl(String reportUrl) {
        this.setReportUrl(reportUrl);
        return this;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public IsolationDetails remarks(String remarks) {
        this.setRemarks(remarks);
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getSelfRegistered() {
        return this.selfRegistered;
    }

    public IsolationDetails selfRegistered(Boolean selfRegistered) {
        this.setSelfRegistered(selfRegistered);
        return this;
    }

    public void setSelfRegistered(Boolean selfRegistered) {
        this.selfRegistered = selfRegistered;
    }

    public Instant getLastAssessment() {
        return this.lastAssessment;
    }

    public IsolationDetails lastAssessment(Instant lastAssessment) {
        this.setLastAssessment(lastAssessment);
        return this;
    }

    public void setLastAssessment(Instant lastAssessment) {
        this.lastAssessment = lastAssessment;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public IsolationDetails lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public IsolationDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IsolationDetails)) {
            return false;
        }
        return id != null && id.equals(((IsolationDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IsolationDetails{" +
            "id=" + getId() +
            ", referredDrName='" + getReferredDrName() + "'" +
            ", referredDrMobile='" + getReferredDrMobile() + "'" +
            ", prescriptionUrl='" + getPrescriptionUrl() + "'" +
            ", reportUrl='" + getReportUrl() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", selfRegistered='" + getSelfRegistered() + "'" +
            ", lastAssessment='" + getLastAssessment() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
