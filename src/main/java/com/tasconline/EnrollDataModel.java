package com.tasconline;

public class EnrollDataModel {
    private String id;
    private int version = 0;
    private String parentId;
    private String parentType="INDIVIDUAL";
    private String clientId;
    private String planId;
    private String planName;
    private String planDescription;
    private String planStartDate;
    private String planEndDate;
    private String hireDate;
    private String benefitEffectiveDate ="2018-10-01";
    private String eligibilityEndDate = "2018-12-31";
    private String payrollFirstDate ="2018-10-01";
    private String electionScheduleType;
    private int electionAmount =1000;
    private float perPayPeriodAmount = 83.33f;
    private String currentState ="Enrolled";
    private String soaAccountId ="";
    private boolean carryoverDeclined = false;
    private String created ="2018-10-01T21:22:40.133Z";
    private String createdBy;
    private String lastTransition;
    private String payrollScheduleId;
    private boolean excludeFromBilling = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    public String getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(String planStartDate) {
        this.planStartDate = planStartDate;
    }

    public String getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(String planEndDate) {
        this.planEndDate = planEndDate;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getBenefitEffectiveDate() {
        return benefitEffectiveDate;
    }

    public void setBenefitEffectiveDate(String benefitEffectiveDate) {
        this.benefitEffectiveDate = benefitEffectiveDate;
    }

    public String getEligibilityEndDate() {
        return eligibilityEndDate;
    }

    public void setEligibilityEndDate(String eligibilityEndDate) {
        this.eligibilityEndDate = eligibilityEndDate;
    }

    public String getPayrollFirstDate() {
        return payrollFirstDate;
    }

    public void setPayrollFirstDate(String payrollFirstDate) {
        this.payrollFirstDate = payrollFirstDate;
    }

    public String getElectionScheduleType() {
        return electionScheduleType;
    }

    public void setElectionScheduleType(String electionScheduleType) {
        this.electionScheduleType = electionScheduleType;
    }

    public int getElectionAmount() {
        return electionAmount;
    }

    public void setElectionAmount(int electionAmount) {
        this.electionAmount = electionAmount;
    }

    public float getPerPayPeriodAmount() {
        return perPayPeriodAmount;
    }

    public void setPerPayPeriodAmount(float perPayPeriodAmount) {
        this.perPayPeriodAmount = perPayPeriodAmount;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getSoaAccountId() {
        return soaAccountId;
    }

    public void setSoaAccountId(String soaAccountId) {
        this.soaAccountId = soaAccountId;
    }

    public boolean isCarryoverDeclined() {
        return carryoverDeclined;
    }

    public void setCarryoverDeclined(boolean carryoverDeclined) {
        this.carryoverDeclined = carryoverDeclined;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastTransition() {
        return lastTransition;
    }

    public void setLastTransition(String lastTransition) {
        this.lastTransition = lastTransition;
    }

    public boolean isExcludeFromBilling() {
        return excludeFromBilling;
    }

    public void setExcludeFromBilling(boolean excludeFromBilling) {
        this.excludeFromBilling = excludeFromBilling;
    }

    public String getPayrollScheduleId() {
        return payrollScheduleId;
    }

    public void setPayrollScheduleId(String payrollScheduleId) {
        this.payrollScheduleId = payrollScheduleId;
    }
}
