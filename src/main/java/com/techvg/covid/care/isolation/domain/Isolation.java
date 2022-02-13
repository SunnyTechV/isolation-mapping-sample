package com.techvg.covid.care.isolation.domain;

import com.techvg.covid.care.isolation.domain.enumeration.HealthCondition;
import com.techvg.covid.care.isolation.domain.enumeration.IsolationStatus;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Isolation.
 */
@Entity
@Table(name = "isolation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Isolation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "icmr_id")
    private String icmrId;

    @Column(name = "rtpcr_id")
    private String rtpcrId;

    @Column(name = "rat_id")
    private String ratId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "email")
    private String email;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "activated")
    private Boolean activated;

    @NotNull
    @Column(name = "mobile_no", nullable = false, unique = true)
    private String mobileNo;

    @NotNull
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "secondary_contact_no")
    private String secondaryContactNo;

    @NotNull
    @Column(name = "aadhar_card_no", nullable = false, unique = true)
    private String aadharCardNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private IsolationStatus status;

    @Column(name = "age")
    private String age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "state_id")
    private Long stateId;

    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "taluka_id")
    private Long talukaId;

    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "address")
    private String address;

    @Column(name = "pincode")
    private String pincode;

    @Column(name = "collection_date")
    private Instant collectionDate;

    @Column(name = "hospitalized")
    private Boolean hospitalized;

    @Column(name = "hospital_id")
    private Long hospitalId;

    @Column(name = "address_latitude")
    private String addressLatitude;

    @Column(name = "address_longitude")
    private String addressLongitude;

    @Column(name = "current_latitude")
    private String currentLatitude;

    @Column(name = "current_longitude")
    private String currentLongitude;

    @Column(name = "hospitalization_date")
    private Instant hospitalizationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "health_condition")
    private HealthCondition healthCondition;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "symptomatic")
    private Boolean symptomatic;

    @Column(name = "ccms_login")
    private String ccmsLogin;

    @Column(name = "self_registered")
    private Boolean selfRegistered;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "isolation_start_date")
    private Instant isolationStartDate;

    @Column(name = "isolation_end_date")
    private Instant isolationEndDate;

    @Column(name = "tvg_isolation_user_id")
    private Long tvgIsolationUserId;

    @OneToOne
    @JoinColumn(unique = true)
    private IsolationDetails isolationDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Isolation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcmrId() {
        return this.icmrId;
    }

    public Isolation icmrId(String icmrId) {
        this.setIcmrId(icmrId);
        return this;
    }

    public void setIcmrId(String icmrId) {
        this.icmrId = icmrId;
    }

    public String getRtpcrId() {
        return this.rtpcrId;
    }

    public Isolation rtpcrId(String rtpcrId) {
        this.setRtpcrId(rtpcrId);
        return this;
    }

    public void setRtpcrId(String rtpcrId) {
        this.rtpcrId = rtpcrId;
    }

    public String getRatId() {
        return this.ratId;
    }

    public Isolation ratId(String ratId) {
        this.setRatId(ratId);
        return this;
    }

    public void setRatId(String ratId) {
        this.ratId = ratId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Isolation firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Isolation lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public Isolation latitude(String latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public Isolation longitude(String longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getEmail() {
        return this.email;
    }

    public Isolation email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Isolation imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public Isolation activated(Boolean activated) {
        this.setActivated(activated);
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public Isolation mobileNo(String mobileNo) {
        this.setMobileNo(mobileNo);
        return this;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public Isolation passwordHash(String passwordHash) {
        this.setPasswordHash(passwordHash);
        return this;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSecondaryContactNo() {
        return this.secondaryContactNo;
    }

    public Isolation secondaryContactNo(String secondaryContactNo) {
        this.setSecondaryContactNo(secondaryContactNo);
        return this;
    }

    public void setSecondaryContactNo(String secondaryContactNo) {
        this.secondaryContactNo = secondaryContactNo;
    }

    public String getAadharCardNo() {
        return this.aadharCardNo;
    }

    public Isolation aadharCardNo(String aadharCardNo) {
        this.setAadharCardNo(aadharCardNo);
        return this;
    }

    public void setAadharCardNo(String aadharCardNo) {
        this.aadharCardNo = aadharCardNo;
    }

    public IsolationStatus getStatus() {
        return this.status;
    }

    public Isolation status(IsolationStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(IsolationStatus status) {
        this.status = status;
    }

    public String getAge() {
        return this.age;
    }

    public Isolation age(String age) {
        this.setAge(age);
        return this;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return this.gender;
    }

    public Isolation gender(String gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getStateId() {
        return this.stateId;
    }

    public Isolation stateId(Long stateId) {
        this.setStateId(stateId);
        return this;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getDistrictId() {
        return this.districtId;
    }

    public Isolation districtId(Long districtId) {
        this.setDistrictId(districtId);
        return this;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Long getTalukaId() {
        return this.talukaId;
    }

    public Isolation talukaId(Long talukaId) {
        this.setTalukaId(talukaId);
        return this;
    }

    public void setTalukaId(Long talukaId) {
        this.talukaId = talukaId;
    }

    public Long getCityId() {
        return this.cityId;
    }

    public Isolation cityId(Long cityId) {
        this.setCityId(cityId);
        return this;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getAddress() {
        return this.address;
    }

    public Isolation address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return this.pincode;
    }

    public Isolation pincode(String pincode) {
        this.setPincode(pincode);
        return this;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Instant getCollectionDate() {
        return this.collectionDate;
    }

    public Isolation collectionDate(Instant collectionDate) {
        this.setCollectionDate(collectionDate);
        return this;
    }

    public void setCollectionDate(Instant collectionDate) {
        this.collectionDate = collectionDate;
    }

    public Boolean getHospitalized() {
        return this.hospitalized;
    }

    public Isolation hospitalized(Boolean hospitalized) {
        this.setHospitalized(hospitalized);
        return this;
    }

    public void setHospitalized(Boolean hospitalized) {
        this.hospitalized = hospitalized;
    }

    public Long getHospitalId() {
        return this.hospitalId;
    }

    public Isolation hospitalId(Long hospitalId) {
        this.setHospitalId(hospitalId);
        return this;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getAddressLatitude() {
        return this.addressLatitude;
    }

    public Isolation addressLatitude(String addressLatitude) {
        this.setAddressLatitude(addressLatitude);
        return this;
    }

    public void setAddressLatitude(String addressLatitude) {
        this.addressLatitude = addressLatitude;
    }

    public String getAddressLongitude() {
        return this.addressLongitude;
    }

    public Isolation addressLongitude(String addressLongitude) {
        this.setAddressLongitude(addressLongitude);
        return this;
    }

    public void setAddressLongitude(String addressLongitude) {
        this.addressLongitude = addressLongitude;
    }

    public String getCurrentLatitude() {
        return this.currentLatitude;
    }

    public Isolation currentLatitude(String currentLatitude) {
        this.setCurrentLatitude(currentLatitude);
        return this;
    }

    public void setCurrentLatitude(String currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public String getCurrentLongitude() {
        return this.currentLongitude;
    }

    public Isolation currentLongitude(String currentLongitude) {
        this.setCurrentLongitude(currentLongitude);
        return this;
    }

    public void setCurrentLongitude(String currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public Instant getHospitalizationDate() {
        return this.hospitalizationDate;
    }

    public Isolation hospitalizationDate(Instant hospitalizationDate) {
        this.setHospitalizationDate(hospitalizationDate);
        return this;
    }

    public void setHospitalizationDate(Instant hospitalizationDate) {
        this.hospitalizationDate = hospitalizationDate;
    }

    public HealthCondition getHealthCondition() {
        return this.healthCondition;
    }

    public Isolation healthCondition(HealthCondition healthCondition) {
        this.setHealthCondition(healthCondition);
        return this;
    }

    public void setHealthCondition(HealthCondition healthCondition) {
        this.healthCondition = healthCondition;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public Isolation remarks(String remarks) {
        this.setRemarks(remarks);
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getSymptomatic() {
        return this.symptomatic;
    }

    public Isolation symptomatic(Boolean symptomatic) {
        this.setSymptomatic(symptomatic);
        return this;
    }

    public void setSymptomatic(Boolean symptomatic) {
        this.symptomatic = symptomatic;
    }

    public String getCcmsLogin() {
        return this.ccmsLogin;
    }

    public Isolation ccmsLogin(String ccmsLogin) {
        this.setCcmsLogin(ccmsLogin);
        return this;
    }

    public void setCcmsLogin(String ccmsLogin) {
        this.ccmsLogin = ccmsLogin;
    }

    public Boolean getSelfRegistered() {
        return this.selfRegistered;
    }

    public Isolation selfRegistered(Boolean selfRegistered) {
        this.setSelfRegistered(selfRegistered);
        return this;
    }

    public void setSelfRegistered(Boolean selfRegistered) {
        this.selfRegistered = selfRegistered;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public Isolation lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Isolation lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getIsolationStartDate() {
        return this.isolationStartDate;
    }

    public Isolation isolationStartDate(Instant isolationStartDate) {
        this.setIsolationStartDate(isolationStartDate);
        return this;
    }

    public void setIsolationStartDate(Instant isolationStartDate) {
        this.isolationStartDate = isolationStartDate;
    }

    public Instant getIsolationEndDate() {
        return this.isolationEndDate;
    }

    public Isolation isolationEndDate(Instant isolationEndDate) {
        this.setIsolationEndDate(isolationEndDate);
        return this;
    }

    public void setIsolationEndDate(Instant isolationEndDate) {
        this.isolationEndDate = isolationEndDate;
    }

    public Long getTvgIsolationUserId() {
        return this.tvgIsolationUserId;
    }

    public Isolation tvgIsolationUserId(Long tvgIsolationUserId) {
        this.setTvgIsolationUserId(tvgIsolationUserId);
        return this;
    }

    public void setTvgIsolationUserId(Long tvgIsolationUserId) {
        this.tvgIsolationUserId = tvgIsolationUserId;
    }

    public IsolationDetails getIsolationDetails() {
        return this.isolationDetails;
    }

    public void setIsolationDetails(IsolationDetails isolationDetails) {
        this.isolationDetails = isolationDetails;
    }

    public Isolation isolationDetails(IsolationDetails isolationDetails) {
        this.setIsolationDetails(isolationDetails);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Isolation)) {
            return false;
        }
        return id != null && id.equals(((Isolation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Isolation{" +
            "id=" + getId() +
            ", icmrId='" + getIcmrId() + "'" +
            ", rtpcrId='" + getRtpcrId() + "'" +
            ", ratId='" + getRatId() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", email='" + getEmail() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", activated='" + getActivated() + "'" +
            ", mobileNo='" + getMobileNo() + "'" +
            ", passwordHash='" + getPasswordHash() + "'" +
            ", secondaryContactNo='" + getSecondaryContactNo() + "'" +
            ", aadharCardNo='" + getAadharCardNo() + "'" +
            ", status='" + getStatus() + "'" +
            ", age='" + getAge() + "'" +
            ", gender='" + getGender() + "'" +
            ", stateId=" + getStateId() +
            ", districtId=" + getDistrictId() +
            ", talukaId=" + getTalukaId() +
            ", cityId=" + getCityId() +
            ", address='" + getAddress() + "'" +
            ", pincode='" + getPincode() + "'" +
            ", collectionDate='" + getCollectionDate() + "'" +
            ", hospitalized='" + getHospitalized() + "'" +
            ", hospitalId=" + getHospitalId() +
            ", addressLatitude='" + getAddressLatitude() + "'" +
            ", addressLongitude='" + getAddressLongitude() + "'" +
            ", currentLatitude='" + getCurrentLatitude() + "'" +
            ", currentLongitude='" + getCurrentLongitude() + "'" +
            ", hospitalizationDate='" + getHospitalizationDate() + "'" +
            ", healthCondition='" + getHealthCondition() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", symptomatic='" + getSymptomatic() + "'" +
            ", ccmsLogin='" + getCcmsLogin() + "'" +
            ", selfRegistered='" + getSelfRegistered() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", isolationStartDate='" + getIsolationStartDate() + "'" +
            ", isolationEndDate='" + getIsolationEndDate() + "'" +
            ", tvgIsolationUserId=" + getTvgIsolationUserId() +
            "}";
    }
}
