
package com.example.ak.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "SCB_CustClass",
    "SCB_ApplScore",
    "SCB_MMCycleDue",
    "AcctRec",
    "PersonPartyInfo",
    "SCBFlag"
})

public class SCBCustomerEnquiry {

    @JsonProperty("SCB_CustClass")
    private String sCBCustClass;
    @JsonProperty("SCB_ApplScore")
    private Integer sCBApplScore;
    @JsonProperty("SCB_MMCycleDue")
    private Integer sCBMMCycleDue;
    @JsonProperty("AcctRec")
    private AcctRec acctRec;
    @JsonProperty("PersonPartyInfo")
    private PersonPartyInfo personPartyInfo;
    @JsonProperty("SCBFlag")
    private SCBFlag sCBFlag;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("SCB_CustClass")
    public String getSCBCustClass() {
        return sCBCustClass;
    }

    @JsonProperty("SCB_CustClass")
    public void setSCBCustClass(String sCBCustClass) {
        this.sCBCustClass = sCBCustClass;
    }

    @JsonProperty("SCB_ApplScore")
    public Integer getSCBApplScore() {
        return sCBApplScore;
    }

    @JsonProperty("SCB_ApplScore")
    public void setSCBApplScore(Integer sCBApplScore) {
        this.sCBApplScore = sCBApplScore;
    }

    @JsonProperty("SCB_MMCycleDue")
    public Integer getSCBMMCycleDue() {
        return sCBMMCycleDue;
    }

    @JsonProperty("SCB_MMCycleDue")
    public void setSCBMMCycleDue(Integer sCBMMCycleDue) {
        this.sCBMMCycleDue = sCBMMCycleDue;
    }

    @JsonProperty("AcctRec")
    public AcctRec getAcctRec() {
        return acctRec;
    }

    @JsonProperty("AcctRec")
    public void setAcctRec(AcctRec acctRec) {
        this.acctRec = acctRec;
    }

    @JsonProperty("PersonPartyInfo")
    public PersonPartyInfo getPersonPartyInfo() {
        return personPartyInfo;
    }

    @JsonProperty("PersonPartyInfo")
    public void setPersonPartyInfo(PersonPartyInfo personPartyInfo) {
        this.personPartyInfo = personPartyInfo;
    }

    @JsonProperty("SCBFlag")
    public SCBFlag getSCBFlag() {
        return sCBFlag;
    }

    @JsonProperty("SCBFlag")
    public void setSCBFlag(SCBFlag sCBFlag) {
        this.sCBFlag = sCBFlag;
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
		return "SCBCustomerEnquiry [sCBCustClass=" + sCBCustClass + ", sCBApplScore=" + sCBApplScore
				+ ", sCBMMCycleDue=" + sCBMMCycleDue + ", acctRec=" + acctRec + ", personPartyInfo=" + personPartyInfo
				+ ", sCBFlag=" + sCBFlag + ", additionalProperties=" + additionalProperties + "]";
	}

}
