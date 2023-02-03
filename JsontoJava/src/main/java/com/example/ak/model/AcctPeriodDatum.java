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
    "AcctAmtType",
    "Amt",
    "SCB_AgingCode",
    "EffDt"
})

public class AcctPeriodDatum {

    @JsonProperty("AcctAmtType")
    private String acctAmtType;
    @JsonProperty("Amt")
    private Integer amt;
    @JsonProperty("SCB_AgingCode")
    private String sCBAgingCode;
    @JsonProperty("EffDt")
    private String effDt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("AcctAmtType")
    public String getAcctAmtType() {
        return acctAmtType;
    }

    @JsonProperty("AcctAmtType")
    public void setAcctAmtType(String acctAmtType) {
        this.acctAmtType = acctAmtType;
    }

    @JsonProperty("Amt")
    public Integer getAmt() {
        return amt;
    }

    @JsonProperty("Amt")
    public void setAmt(Integer amt) {
        this.amt = amt;
    }

    @JsonProperty("SCB_AgingCode")
    public String getSCBAgingCode() {
        return sCBAgingCode;
    }

    @JsonProperty("SCB_AgingCode")
    public void setSCBAgingCode(String sCBAgingCode) {
        this.sCBAgingCode = sCBAgingCode;
    }

    @JsonProperty("EffDt")
    public String getEffDt() {
        return effDt;
    }

    @JsonProperty("EffDt")
    public void setEffDt(String effDt) {
        this.effDt = effDt;
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
		return "AcctPeriodDatum [acctAmtType=" + acctAmtType + ", amt=" + amt + ", sCBAgingCode=" + sCBAgingCode
				+ ", effDt=" + effDt + ", additionalProperties=" + additionalProperties + "]";
	}

}
