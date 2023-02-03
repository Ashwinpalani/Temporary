
package com.example.ak.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "CurCode",
    "AcctIdent",
    "Term",
    "MaturityDt",
    "TaxCountry",
    "AcctType",
    "AcctBal",
    "AcctPeriodData",
    "Ownership",
    "RemainingPmtCount",
    "Segmentation",
    "SCBLoanApplication"
})

public class AcctInfo {

    @JsonProperty("CurCode")
    private CurCode curCode;
    @JsonProperty("AcctIdent")
    private AcctIdent acctIdent;
    @JsonProperty("Term")
    private Term term;
    @JsonProperty("MaturityDt")
    private String maturityDt;
    @JsonProperty("TaxCountry")
    private TaxCountry taxCountry;
    @JsonProperty("AcctType")
    private AcctType acctType;
    @JsonProperty("AcctBal")
    private List<AcctBal> acctBal = null;
    @JsonProperty("AcctPeriodData")
    private List<AcctPeriodDatum> acctPeriodData = null;
    @JsonProperty("Ownership")
    private String ownership;
    @JsonProperty("RemainingPmtCount")
    private Integer remainingPmtCount;
    @JsonProperty("Segmentation")
    private Segmentation segmentation;
    @JsonProperty("SCBLoanApplication")
    private SCBLoanApplication sCBLoanApplication;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("CurCode")
    public CurCode getCurCode() {
        return curCode;
    }

    @JsonProperty("CurCode")
    public void setCurCode(CurCode curCode) {
        this.curCode = curCode;
    }

    @JsonProperty("AcctIdent")
    public AcctIdent getAcctIdent() {
        return acctIdent;
    }

    @JsonProperty("AcctIdent")
    public void setAcctIdent(AcctIdent acctIdent) {
        this.acctIdent = acctIdent;
    }

    @JsonProperty("Term")
    public Term getTerm() {
        return term;
    }

    @JsonProperty("Term")
    public void setTerm(Term term) {
        this.term = term;
    }

    @JsonProperty("MaturityDt")
    public String getMaturityDt() {
        return maturityDt;
    }

    @JsonProperty("MaturityDt")
    public void setMaturityDt(String maturityDt) {
        this.maturityDt = maturityDt;
    }

    @JsonProperty("TaxCountry")
    public TaxCountry getTaxCountry() {
        return taxCountry;
    }

    @JsonProperty("TaxCountry")
    public void setTaxCountry(TaxCountry taxCountry) {
        this.taxCountry = taxCountry;
    }

    @JsonProperty("AcctType")
    public AcctType getAcctType() {
        return acctType;
    }

    @JsonProperty("AcctType")
    public void setAcctType(AcctType acctType) {
        this.acctType = acctType;
    }

    @JsonProperty("AcctBal")
    public List<AcctBal> getAcctBal() {
        return acctBal;
    }

    @JsonProperty("AcctBal")
    public void setAcctBal(List<AcctBal> acctBal) {
        this.acctBal = acctBal;
    }

    @JsonProperty("AcctPeriodData")
    public List<AcctPeriodDatum> getAcctPeriodData() {
        return acctPeriodData;
    }

    @JsonProperty("AcctPeriodData")
    public void setAcctPeriodData(List<AcctPeriodDatum> acctPeriodData) {
        this.acctPeriodData = acctPeriodData;
    }

    @JsonProperty("Ownership")
    public String getOwnership() {
        return ownership;
    }

    @JsonProperty("Ownership")
    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    @JsonProperty("RemainingPmtCount")
    public Integer getRemainingPmtCount() {
        return remainingPmtCount;
    }

    @JsonProperty("RemainingPmtCount")
    public void setRemainingPmtCount(Integer remainingPmtCount) {
        this.remainingPmtCount = remainingPmtCount;
    }

    @JsonProperty("Segmentation")
    public Segmentation getSegmentation() {
        return segmentation;
    }

    @JsonProperty("Segmentation")
    public void setSegmentation(Segmentation segmentation) {
        this.segmentation = segmentation;
    }

    @JsonProperty("SCBLoanApplication")
    public SCBLoanApplication getSCBLoanApplication() {
        return sCBLoanApplication;
    }

    @JsonProperty("SCBLoanApplication")
    public void setSCBLoanApplication(SCBLoanApplication sCBLoanApplication) {
        this.sCBLoanApplication = sCBLoanApplication;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

	@Override
	public String toString() {
		return "AcctInfo [curCode=" + curCode + ", acctIdent=" + acctIdent + ", term=" + term + ", maturityDt="
				+ maturityDt + ", taxCountry=" + taxCountry + ", acctType=" + acctType + ", acctBal=" + acctBal
				+ ", acctPeriodData=" + acctPeriodData + ", ownership=" + ownership + ", remainingPmtCount="
				+ remainingPmtCount + ", segmentation=" + segmentation + ", sCBLoanApplication=" + sCBLoanApplication
				+ ", additionalProperties=" + additionalProperties + "]";
	}

}
