
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
    "AcctStatusCode",
    "StatusDesc"
})

public class AcctStatus {

    @JsonProperty("AcctStatusCode")
    private Integer acctStatusCode;
    @JsonProperty("StatusDesc")
    private String statusDesc;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("AcctStatusCode")
    public Integer getAcctStatusCode() {
        return acctStatusCode;
    }

    @JsonProperty("AcctStatusCode")
    public void setAcctStatusCode(Integer acctStatusCode) {
        this.acctStatusCode = acctStatusCode;
    }

    @JsonProperty("StatusDesc")
    public String getStatusDesc() {
        return statusDesc;
    }

    @JsonProperty("StatusDesc")
    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
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
		return "AcctStatus [acctStatusCode=" + acctStatusCode + ", statusDesc=" + statusDesc + ", additionalProperties="
				+ additionalProperties + "]";
	}

}
