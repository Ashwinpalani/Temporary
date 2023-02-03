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
    "CreditRisk"
})

public class PersonPartyInfo {

    @JsonProperty("CreditRisk")
    private CreditRisk creditRisk;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("CreditRisk")
    public CreditRisk getCreditRisk() {
        return creditRisk;
    }

    @JsonProperty("CreditRisk")
    public void setCreditRisk(CreditRisk creditRisk) {
        this.creditRisk = creditRisk;
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
		return "PersonPartyInfo [creditRisk=" + creditRisk + ", additionalProperties=" + additionalProperties + "]";
	}

}
