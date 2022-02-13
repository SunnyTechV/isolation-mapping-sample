package com.techvg.covid.care.isolation.service.dto;

import com.techvg.covid.care.isolation.domain.enumeration.HealthCondition;
import com.techvg.covid.care.isolation.domain.enumeration.IsolationStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.techvg.covid.care.isolation.domain.Isolation} entity.
 */
public class IsolationDTO implements Serializable {

    private Long id;

    private String icmrId;

    private String rtpcrId;

    private String ratId;

    private String firstName;

    private String lastName;

    private String latitude;

    private String longitude;

    private String email;

    private String imageUrl;

    private Boolean activated;

    @NotNull
    private String mobileNo;

    @NotNull
    private String passwordHash;

    private String secondaryContactNo;

    @NotNull
    private String aadharCardNo;

    private IsolationStatus status;

    private String age;

    private String gender;

    private Long stateId;

    private Long districtId;

    private Long talukaId;

    private Long cityId;

    private String address;

    private String pincode;

    private Instant collectionDate;

    private Boolean hospitalized;

    private Long hospitalId;

    private String addressLatitude;

    private String addressLongitude;

    private String currentLatitude;

    private String currentLongitude;

    private Instant hospitalizationDate;

    private HealthCondition healthCondition;

    private String remarks;

    private Boolean symptomatic;

    private String ccmsLogin;

    private Boolean selfRegistered;

    private Instant lastModified;

    private String lastModifiedBy;

    private Instant isolationStartDate;

    private Instant isolationEndDate;

    private Long tvgIsolationUserId;

    private IsolationDetailsDTO isolationDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcmrId() {
        return icmrId;
    }

    public void setIcmrId(String icmrId) {
        this.icmrId = icmrId;
    }

    public String getRtpcrId() {
        return rtpcrId;
    }

    public void setRtpcrId(String rtpcrId) {
        this.rtpcrId = rtpcrId;
    }

    public String getRatId() {
        return ratId;
    }

    public void setRatId(String ratId) {
        this.ratId = ratId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSecondaryContactNo() {
        return secondaryContactNo;
    }

    public void setSecondaryContactNo(String secondaryContactNo) {
        this.secondaryContactNo = secondaryContactNo;
    }

    public String getAadharCardNo() {
        return aadharCardNo;
    }

    public void setAadharCardNo(String aadharCardNo) {
        this.aadharCardNo = aadharCardNo;
    }

    public IsolationStatus getStatus() {
        return status;
    }

    public void setStatus(IsolationStatus status) {
        this.status = status;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Long getTalukaId() {
        return talukaId;
    }

    public void setTalukaId(Long talukaId) {
        this.talukaId = talukaId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Instant getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(Instant collectionDate) {
        this.collectionDate = collectionDate;
    }

    public Boolean getHospitalized() {
        return hospitalized;
    }

    public void setHospitalized(Boolean hospitalized) {
        this.hospitalized = hospitalized;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getAddressLatitude() {
        return addressLatitude;
    }

    public void setAddressLatitude(String addressLatitude) {
        this.addressLatitude = addressLatitude;
    }

    public String getAddressLongitude() {
        return addressLongitude;
    }

    public void setAddressLongitude(String addressLongitude) {
        this.addressLongitude = addressLongitude;
    }

    public String getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(String currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public String getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(String currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public Instant getHospitalizationDate() {
        return hospitalizationDate;
    }

    public void setHospitalizationDate(Instant hospitalizationDate) {
        this.hospitalizationDate = hospitalizationDate;
    }

    public HealthCondition getHealthCondition() {
        return healthCondition;
    }

    public void setHealthCondition(HealthCondition healthCondition) {
        this.healthCondition = healthCondition;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getSymptomatic() {
        return symptomatic;
    }

    public void setSymptomatic(Boolean symptomatic) {
        this.symptomatic = symptomatic;
    }

    public String getCcmsLogin() {
        return ccmsLogin;
    }

    public void setCcmsLogin(String ccmsLogin) {
        this.ccmsLogin = ccmsLogin;
    }

    public Boolean getSelfRegistered() {
        return selfRegistered;
    }

    public void setSelfRegistered(Boolean selfRegistered) {
        this.selfRegistered = selfRegistered;
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

    public Instant getIsolationStartDate() {
        return isolationStartDate;
    }

    public void setIsolationStartDate(Instant isolationStartDate) {
        this.isolationStartDate = isolationStartDate;
    }

    public Instant getIsolationEndDate() {
        return isolationEndDate;
    }

    public void setIsolationEndDate(Instant isolationEndDate) {
        this.isolationEndDate = isolationEndDate;
    }

    public Long getTvgIsolationUserId() {
        return tvgIsolationUserId;
    }

    public void setTvgIsolationUserId(Long tvgIsolationUserId) {
        this.tvgIsolationUserId = tvgIsolationUserId;
    }

    public IsolationDetailsDTO getIsolationDetails() {
        return isolationDetails;
    }

    public void setIsolationDetails(IsolationDetailsDTO isolationDetails) {
        this.isolationDetails = isolationDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IsolationDTO)) {
            return false;
        }

        IsolationDTO isolationDTO = (IsolationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, isolationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IsolationDTO{" +
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
            ", isolationDetails=" + getIsolationDetails() +
            "}";
    }
}
