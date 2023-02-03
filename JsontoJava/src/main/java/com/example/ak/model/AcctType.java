
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
    "AcctTypeValue"
})

public class AcctType {

    @JsonProperty("AcctTypeValue")
    private Integer acctTypeValue;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("AcctTypeValue")
    public Integer getAcctTypeValue() {
        return acctTypeValue;
    }

    @JsonProperty("AcctTypeValue")
    public void setAcctTypeValue(Integer acctTypeValue) {
        this.acctTypeValue = acctTypeValue;
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
		return "AcctType [acctTypeValue=" + acctTypeValue + ", additionalProperties=" + additionalProperties + "]";
	}

}
