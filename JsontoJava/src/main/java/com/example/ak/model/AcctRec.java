
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
    "AcctId",
    "AcctInfo",
    "AcctStatus"
})

public class AcctRec {

    @JsonProperty("AcctId")
    private Long acctId;
    @JsonProperty("AcctInfo")
    private AcctInfo acctInfo;
    @JsonProperty("AcctStatus")
    private AcctStatus acctStatus;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("AcctId")
    public Long getAcctId() {
        return acctId;
    }

    @JsonProperty("AcctId")
    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    @JsonProperty("AcctInfo")
    public AcctInfo getAcctInfo() {
        return acctInfo;
    }

    @JsonProperty("AcctInfo")
    public void setAcctInfo(AcctInfo acctInfo) {
        this.acctInfo = acctInfo;
    }

    @JsonProperty("AcctStatus")
    public AcctStatus getAcctStatus() {
        return acctStatus;
    }

    @JsonProperty("AcctStatus")
    public void setAcctStatus(AcctStatus acctStatus) {
        this.acctStatus = acctStatus;
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
		return "AcctRec [acctId=" + acctId + ", acctInfo=" + acctInfo + ", acctStatus=" + acctStatus
				+ ", additionalProperties=" + additionalProperties + "]";
	}

}
