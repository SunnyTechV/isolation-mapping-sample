package com.techvg.covid.care.isolation.service.criteria;

import com.techvg.covid.care.isolation.domain.enumeration.HealthCondition;
import com.techvg.covid.care.isolation.domain.enumeration.IsolationStatus;
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
 * Criteria class for the {@link com.techvg.covid.care.isolation.domain.Isolation} entity. This class is used
 * in {@link com.techvg.covid.care.isolation.web.rest.IsolationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /isolations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class IsolationCriteria implements Serializable, Criteria {

    /**
     * Class for filtering IsolationStatus
     */
    public static class IsolationStatusFilter extends Filter<IsolationStatus> {

        public IsolationStatusFilter() {}

        public IsolationStatusFilter(IsolationStatusFilter filter) {
            super(filter);
        }

        @Override
        public IsolationStatusFilter copy() {
            return new IsolationStatusFilter(this);
        }
    }

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

    private StringFilter icmrId;

    private StringFilter rtpcrId;

    private StringFilter ratId;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter latitude;

    private StringFilter longitude;

    private StringFilter email;

    private StringFilter imageUrl;

    private BooleanFilter activated;

    private StringFilter mobileNo;

    private StringFilter passwordHash;

    private StringFilter secondaryContactNo;

    private StringFilter aadharCardNo;

    private IsolationStatusFilter status;

    private StringFilter age;

    private StringFilter gender;

    private LongFilter stateId;

    private LongFilter districtId;

    private LongFilter talukaId;

    private LongFilter cityId;

    private StringFilter address;

    private StringFilter pincode;

    private InstantFilter collectionDate;

    private BooleanFilter hospitalized;

    private LongFilter hospitalId;

    private StringFilter addressLatitude;

    private StringFilter addressLongitude;

    private StringFilter currentLatitude;

    private StringFilter currentLongitude;

    private InstantFilter hospitalizationDate;

    private HealthConditionFilter healthCondition;

    private StringFilter remarks;

    private BooleanFilter symptomatic;

    private StringFilter ccmsLogin;

    private BooleanFilter selfRegistered;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private InstantFilter isolationStartDate;

    private InstantFilter isolationEndDate;

    private LongFilter tvgIsolationUserId;

    private LongFilter isolationDetailsId;

    private Boolean distinct;

    public IsolationCriteria() {}

    public IsolationCriteria(IsolationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.icmrId = other.icmrId == null ? null : other.icmrId.copy();
        this.rtpcrId = other.rtpcrId == null ? null : other.rtpcrId.copy();
        this.ratId = other.ratId == null ? null : other.ratId.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.latitude = other.latitude == null ? null : other.latitude.copy();
        this.longitude = other.longitude == null ? null : other.longitude.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.imageUrl = other.imageUrl == null ? null : other.imageUrl.copy();
        this.activated = other.activated == null ? null : other.activated.copy();
        this.mobileNo = other.mobileNo == null ? null : other.mobileNo.copy();
        this.passwordHash = other.passwordHash == null ? null : other.passwordHash.copy();
        this.secondaryContactNo = other.secondaryContactNo == null ? null : other.secondaryContactNo.copy();
        this.aadharCardNo = other.aadharCardNo == null ? null : other.aadharCardNo.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.age = other.age == null ? null : other.age.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.stateId = other.stateId == null ? null : other.stateId.copy();
        this.districtId = other.districtId == null ? null : other.districtId.copy();
        this.talukaId = other.talukaId == null ? null : other.talukaId.copy();
        this.cityId = other.cityId == null ? null : other.cityId.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.pincode = other.pincode == null ? null : other.pincode.copy();
        this.collectionDate = other.collectionDate == null ? null : other.collectionDate.copy();
        this.hospitalized = other.hospitalized == null ? null : other.hospitalized.copy();
        this.hospitalId = other.hospitalId == null ? null : other.hospitalId.copy();
        this.addressLatitude = other.addressLatitude == null ? null : other.addressLatitude.copy();
        this.addressLongitude = other.addressLongitude == null ? null : other.addressLongitude.copy();
        this.currentLatitude = other.currentLatitude == null ? null : other.currentLatitude.copy();
        this.currentLongitude = other.currentLongitude == null ? null : other.currentLongitude.copy();
        this.hospitalizationDate = other.hospitalizationDate == null ? null : other.hospitalizationDate.copy();
        this.healthCondition = other.healthCondition == null ? null : other.healthCondition.copy();
        this.remarks = other.remarks == null ? null : other.remarks.copy();
        this.symptomatic = other.symptomatic == null ? null : other.symptomatic.copy();
        this.ccmsLogin = other.ccmsLogin == null ? null : other.ccmsLogin.copy();
        this.selfRegistered = other.selfRegistered == null ? null : other.selfRegistered.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.isolationStartDate = other.isolationStartDate == null ? null : other.isolationStartDate.copy();
        this.isolationEndDate = other.isolationEndDate == null ? null : other.isolationEndDate.copy();
        this.tvgIsolationUserId = other.tvgIsolationUserId == null ? null : other.tvgIsolationUserId.copy();
        this.isolationDetailsId = other.isolationDetailsId == null ? null : other.isolationDetailsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public IsolationCriteria copy() {
        return new IsolationCriteria(this);
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

    public StringFilter getIcmrId() {
        return icmrId;
    }

    public StringFilter icmrId() {
        if (icmrId == null) {
            icmrId = new StringFilter();
        }
        return icmrId;
    }

    public void setIcmrId(StringFilter icmrId) {
        this.icmrId = icmrId;
    }

    public StringFilter getRtpcrId() {
        return rtpcrId;
    }

    public StringFilter rtpcrId() {
        if (rtpcrId == null) {
            rtpcrId = new StringFilter();
        }
        return rtpcrId;
    }

    public void setRtpcrId(StringFilter rtpcrId) {
        this.rtpcrId = rtpcrId;
    }

    public StringFilter getRatId() {
        return ratId;
    }

    public StringFilter ratId() {
        if (ratId == null) {
            ratId = new StringFilter();
        }
        return ratId;
    }

    public void setRatId(StringFilter ratId) {
        this.ratId = ratId;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public StringFilter firstName() {
        if (firstName == null) {
            firstName = new StringFilter();
        }
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public StringFilter lastName() {
        if (lastName == null) {
            lastName = new StringFilter();
        }
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getLatitude() {
        return latitude;
    }

    public StringFilter latitude() {
        if (latitude == null) {
            latitude = new StringFilter();
        }
        return latitude;
    }

    public void setLatitude(StringFilter latitude) {
        this.latitude = latitude;
    }

    public StringFilter getLongitude() {
        return longitude;
    }

    public StringFilter longitude() {
        if (longitude == null) {
            longitude = new StringFilter();
        }
        return longitude;
    }

    public void setLongitude(StringFilter longitude) {
        this.longitude = longitude;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getImageUrl() {
        return imageUrl;
    }

    public StringFilter imageUrl() {
        if (imageUrl == null) {
            imageUrl = new StringFilter();
        }
        return imageUrl;
    }

    public void setImageUrl(StringFilter imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BooleanFilter getActivated() {
        return activated;
    }

    public BooleanFilter activated() {
        if (activated == null) {
            activated = new BooleanFilter();
        }
        return activated;
    }

    public void setActivated(BooleanFilter activated) {
        this.activated = activated;
    }

    public StringFilter getMobileNo() {
        return mobileNo;
    }

    public StringFilter mobileNo() {
        if (mobileNo == null) {
            mobileNo = new StringFilter();
        }
        return mobileNo;
    }

    public void setMobileNo(StringFilter mobileNo) {
        this.mobileNo = mobileNo;
    }

    public StringFilter getPasswordHash() {
        return passwordHash;
    }

    public StringFilter passwordHash() {
        if (passwordHash == null) {
            passwordHash = new StringFilter();
        }
        return passwordHash;
    }

    public void setPasswordHash(StringFilter passwordHash) {
        this.passwordHash = passwordHash;
    }

    public StringFilter getSecondaryContactNo() {
        return secondaryContactNo;
    }

    public StringFilter secondaryContactNo() {
        if (secondaryContactNo == null) {
            secondaryContactNo = new StringFilter();
        }
        return secondaryContactNo;
    }

    public void setSecondaryContactNo(StringFilter secondaryContactNo) {
        this.secondaryContactNo = secondaryContactNo;
    }

    public StringFilter getAadharCardNo() {
        return aadharCardNo;
    }

    public StringFilter aadharCardNo() {
        if (aadharCardNo == null) {
            aadharCardNo = new StringFilter();
        }
        return aadharCardNo;
    }

    public void setAadharCardNo(StringFilter aadharCardNo) {
        this.aadharCardNo = aadharCardNo;
    }

    public IsolationStatusFilter getStatus() {
        return status;
    }

    public IsolationStatusFilter status() {
        if (status == null) {
            status = new IsolationStatusFilter();
        }
        return status;
    }

    public void setStatus(IsolationStatusFilter status) {
        this.status = status;
    }

    public StringFilter getAge() {
        return age;
    }

    public StringFilter age() {
        if (age == null) {
            age = new StringFilter();
        }
        return age;
    }

    public void setAge(StringFilter age) {
        this.age = age;
    }

    public StringFilter getGender() {
        return gender;
    }

    public StringFilter gender() {
        if (gender == null) {
            gender = new StringFilter();
        }
        return gender;
    }

    public void setGender(StringFilter gender) {
        this.gender = gender;
    }

    public LongFilter getStateId() {
        return stateId;
    }

    public LongFilter stateId() {
        if (stateId == null) {
            stateId = new LongFilter();
        }
        return stateId;
    }

    public void setStateId(LongFilter stateId) {
        this.stateId = stateId;
    }

    public LongFilter getDistrictId() {
        return districtId;
    }

    public LongFilter districtId() {
        if (districtId == null) {
            districtId = new LongFilter();
        }
        return districtId;
    }

    public void setDistrictId(LongFilter districtId) {
        this.districtId = districtId;
    }

    public LongFilter getTalukaId() {
        return talukaId;
    }

    public LongFilter talukaId() {
        if (talukaId == null) {
            talukaId = new LongFilter();
        }
        return talukaId;
    }

    public void setTalukaId(LongFilter talukaId) {
        this.talukaId = talukaId;
    }

    public LongFilter getCityId() {
        return cityId;
    }

    public LongFilter cityId() {
        if (cityId == null) {
            cityId = new LongFilter();
        }
        return cityId;
    }

    public void setCityId(LongFilter cityId) {
        this.cityId = cityId;
    }

    public StringFilter getAddress() {
        return address;
    }

    public StringFilter address() {
        if (address == null) {
            address = new StringFilter();
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getPincode() {
        return pincode;
    }

    public StringFilter pincode() {
        if (pincode == null) {
            pincode = new StringFilter();
        }
        return pincode;
    }

    public void setPincode(StringFilter pincode) {
        this.pincode = pincode;
    }

    public InstantFilter getCollectionDate() {
        return collectionDate;
    }

    public InstantFilter collectionDate() {
        if (collectionDate == null) {
            collectionDate = new InstantFilter();
        }
        return collectionDate;
    }

    public void setCollectionDate(InstantFilter collectionDate) {
        this.collectionDate = collectionDate;
    }

    public BooleanFilter getHospitalized() {
        return hospitalized;
    }

    public BooleanFilter hospitalized() {
        if (hospitalized == null) {
            hospitalized = new BooleanFilter();
        }
        return hospitalized;
    }

    public void setHospitalized(BooleanFilter hospitalized) {
        this.hospitalized = hospitalized;
    }

    public LongFilter getHospitalId() {
        return hospitalId;
    }

    public LongFilter hospitalId() {
        if (hospitalId == null) {
            hospitalId = new LongFilter();
        }
        return hospitalId;
    }

    public void setHospitalId(LongFilter hospitalId) {
        this.hospitalId = hospitalId;
    }

    public StringFilter getAddressLatitude() {
        return addressLatitude;
    }

    public StringFilter addressLatitude() {
        if (addressLatitude == null) {
            addressLatitude = new StringFilter();
        }
        return addressLatitude;
    }

    public void setAddressLatitude(StringFilter addressLatitude) {
        this.addressLatitude = addressLatitude;
    }

    public StringFilter getAddressLongitude() {
        return addressLongitude;
    }

    public StringFilter addressLongitude() {
        if (addressLongitude == null) {
            addressLongitude = new StringFilter();
        }
        return addressLongitude;
    }

    public void setAddressLongitude(StringFilter addressLongitude) {
        this.addressLongitude = addressLongitude;
    }

    public StringFilter getCurrentLatitude() {
        return currentLatitude;
    }

    public StringFilter currentLatitude() {
        if (currentLatitude == null) {
            currentLatitude = new StringFilter();
        }
        return currentLatitude;
    }

    public void setCurrentLatitude(StringFilter currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public StringFilter getCurrentLongitude() {
        return currentLongitude;
    }

    public StringFilter currentLongitude() {
        if (currentLongitude == null) {
            currentLongitude = new StringFilter();
        }
        return currentLongitude;
    }

    public void setCurrentLongitude(StringFilter currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public InstantFilter getHospitalizationDate() {
        return hospitalizationDate;
    }

    public InstantFilter hospitalizationDate() {
        if (hospitalizationDate == null) {
            hospitalizationDate = new InstantFilter();
        }
        return hospitalizationDate;
    }

    public void setHospitalizationDate(InstantFilter hospitalizationDate) {
        this.hospitalizationDate = hospitalizationDate;
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

    public BooleanFilter getSymptomatic() {
        return symptomatic;
    }

    public BooleanFilter symptomatic() {
        if (symptomatic == null) {
            symptomatic = new BooleanFilter();
        }
        return symptomatic;
    }

    public void setSymptomatic(BooleanFilter symptomatic) {
        this.symptomatic = symptomatic;
    }

    public StringFilter getCcmsLogin() {
        return ccmsLogin;
    }

    public StringFilter ccmsLogin() {
        if (ccmsLogin == null) {
            ccmsLogin = new StringFilter();
        }
        return ccmsLogin;
    }

    public void setCcmsLogin(StringFilter ccmsLogin) {
        this.ccmsLogin = ccmsLogin;
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

    public InstantFilter getIsolationStartDate() {
        return isolationStartDate;
    }

    public InstantFilter isolationStartDate() {
        if (isolationStartDate == null) {
            isolationStartDate = new InstantFilter();
        }
        return isolationStartDate;
    }

    public void setIsolationStartDate(InstantFilter isolationStartDate) {
        this.isolationStartDate = isolationStartDate;
    }

    public InstantFilter getIsolationEndDate() {
        return isolationEndDate;
    }

    public InstantFilter isolationEndDate() {
        if (isolationEndDate == null) {
            isolationEndDate = new InstantFilter();
        }
        return isolationEndDate;
    }

    public void setIsolationEndDate(InstantFilter isolationEndDate) {
        this.isolationEndDate = isolationEndDate;
    }

    public LongFilter getTvgIsolationUserId() {
        return tvgIsolationUserId;
    }

    public LongFilter tvgIsolationUserId() {
        if (tvgIsolationUserId == null) {
            tvgIsolationUserId = new LongFilter();
        }
        return tvgIsolationUserId;
    }

    public void setTvgIsolationUserId(LongFilter tvgIsolationUserId) {
        this.tvgIsolationUserId = tvgIsolationUserId;
    }

    public LongFilter getIsolationDetailsId() {
        return isolationDetailsId;
    }

    public LongFilter isolationDetailsId() {
        if (isolationDetailsId == null) {
            isolationDetailsId = new LongFilter();
        }
        return isolationDetailsId;
    }

    public void setIsolationDetailsId(LongFilter isolationDetailsId) {
        this.isolationDetailsId = isolationDetailsId;
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
        final IsolationCriteria that = (IsolationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(icmrId, that.icmrId) &&
            Objects.equals(rtpcrId, that.rtpcrId) &&
            Objects.equals(ratId, that.ratId) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(latitude, that.latitude) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(email, that.email) &&
            Objects.equals(imageUrl, that.imageUrl) &&
            Objects.equals(activated, that.activated) &&
            Objects.equals(mobileNo, that.mobileNo) &&
            Objects.equals(passwordHash, that.passwordHash) &&
            Objects.equals(secondaryContactNo, that.secondaryContactNo) &&
            Objects.equals(aadharCardNo, that.aadharCardNo) &&
            Objects.equals(status, that.status) &&
            Objects.equals(age, that.age) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(stateId, that.stateId) &&
            Objects.equals(districtId, that.districtId) &&
            Objects.equals(talukaId, that.talukaId) &&
            Objects.equals(cityId, that.cityId) &&
            Objects.equals(address, that.address) &&
            Objects.equals(pincode, that.pincode) &&
            Objects.equals(collectionDate, that.collectionDate) &&
            Objects.equals(hospitalized, that.hospitalized) &&
            Objects.equals(hospitalId, that.hospitalId) &&
            Objects.equals(addressLatitude, that.addressLatitude) &&
            Objects.equals(addressLongitude, that.addressLongitude) &&
            Objects.equals(currentLatitude, that.currentLatitude) &&
            Objects.equals(currentLongitude, that.currentLongitude) &&
            Objects.equals(hospitalizationDate, that.hospitalizationDate) &&
            Objects.equals(healthCondition, that.healthCondition) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(symptomatic, that.symptomatic) &&
            Objects.equals(ccmsLogin, that.ccmsLogin) &&
            Objects.equals(selfRegistered, that.selfRegistered) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(isolationStartDate, that.isolationStartDate) &&
            Objects.equals(isolationEndDate, that.isolationEndDate) &&
            Objects.equals(tvgIsolationUserId, that.tvgIsolationUserId) &&
            Objects.equals(isolationDetailsId, that.isolationDetailsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            icmrId,
            rtpcrId,
            ratId,
            firstName,
            lastName,
            latitude,
            longitude,
            email,
            imageUrl,
            activated,
            mobileNo,
            passwordHash,
            secondaryContactNo,
            aadharCardNo,
            status,
            age,
            gender,
            stateId,
            districtId,
            talukaId,
            cityId,
            address,
            pincode,
            collectionDate,
            hospitalized,
            hospitalId,
            addressLatitude,
            addressLongitude,
            currentLatitude,
            currentLongitude,
            hospitalizationDate,
            healthCondition,
            remarks,
            symptomatic,
            ccmsLogin,
            selfRegistered,
            lastModified,
            lastModifiedBy,
            isolationStartDate,
            isolationEndDate,
            tvgIsolationUserId,
            isolationDetailsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IsolationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (icmrId != null ? "icmrId=" + icmrId + ", " : "") +
            (rtpcrId != null ? "rtpcrId=" + rtpcrId + ", " : "") +
            (ratId != null ? "ratId=" + ratId + ", " : "") +
            (firstName != null ? "firstName=" + firstName + ", " : "") +
            (lastName != null ? "lastName=" + lastName + ", " : "") +
            (latitude != null ? "latitude=" + latitude + ", " : "") +
            (longitude != null ? "longitude=" + longitude + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "") +
            (activated != null ? "activated=" + activated + ", " : "") +
            (mobileNo != null ? "mobileNo=" + mobileNo + ", " : "") +
            (passwordHash != null ? "passwordHash=" + passwordHash + ", " : "") +
            (secondaryContactNo != null ? "secondaryContactNo=" + secondaryContactNo + ", " : "") +
            (aadharCardNo != null ? "aadharCardNo=" + aadharCardNo + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (age != null ? "age=" + age + ", " : "") +
            (gender != null ? "gender=" + gender + ", " : "") +
            (stateId != null ? "stateId=" + stateId + ", " : "") +
            (districtId != null ? "districtId=" + districtId + ", " : "") +
            (talukaId != null ? "talukaId=" + talukaId + ", " : "") +
            (cityId != null ? "cityId=" + cityId + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (pincode != null ? "pincode=" + pincode + ", " : "") +
            (collectionDate != null ? "collectionDate=" + collectionDate + ", " : "") +
            (hospitalized != null ? "hospitalized=" + hospitalized + ", " : "") +
            (hospitalId != null ? "hospitalId=" + hospitalId + ", " : "") +
            (addressLatitude != null ? "addressLatitude=" + addressLatitude + ", " : "") +
            (addressLongitude != null ? "addressLongitude=" + addressLongitude + ", " : "") +
            (currentLatitude != null ? "currentLatitude=" + currentLatitude + ", " : "") +
            (currentLongitude != null ? "currentLongitude=" + currentLongitude + ", " : "") +
            (hospitalizationDate != null ? "hospitalizationDate=" + hospitalizationDate + ", " : "") +
            (healthCondition != null ? "healthCondition=" + healthCondition + ", " : "") +
            (remarks != null ? "remarks=" + remarks + ", " : "") +
            (symptomatic != null ? "symptomatic=" + symptomatic + ", " : "") +
            (ccmsLogin != null ? "ccmsLogin=" + ccmsLogin + ", " : "") +
            (selfRegistered != null ? "selfRegistered=" + selfRegistered + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (isolationStartDate != null ? "isolationStartDate=" + isolationStartDate + ", " : "") +
            (isolationEndDate != null ? "isolationEndDate=" + isolationEndDate + ", " : "") +
            (tvgIsolationUserId != null ? "tvgIsolationUserId=" + tvgIsolationUserId + ", " : "") +
            (isolationDetailsId != null ? "isolationDetailsId=" + isolationDetailsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
