
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
    "AcctIdentType",
    "AcctIdentValue"
})

public class AcctIdent {

    @JsonProperty("AcctIdentType")
    private String acctIdentType;
    @JsonProperty("AcctIdentValue")
    private Integer acctIdentValue;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("AcctIdentType")
    public String getAcctIdentType() {
        return acctIdentType;
    }

    @JsonProperty("AcctIdentType")
    public void setAcctIdentType(String acctIdentType) {
        this.acctIdentType = acctIdentType;
    }

    @JsonProperty("AcctIdentValue")
    public Integer getAcctIdentValue() {
        return acctIdentValue;
    }

    @JsonProperty("AcctIdentValue")
    public void setAcctIdentValue(Integer acctIdentValue) {
        this.acctIdentValue = acctIdentValue;
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
		return "AcctIdent [acctIdentType=" + acctIdentType + ", acctIdentValue=" + acctIdentValue
				+ ", additionalProperties=" + additionalProperties + "]";
	}

}
