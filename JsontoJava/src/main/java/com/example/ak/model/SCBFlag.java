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
    "SCB_RiskGrade"
})

public class SCBFlag {

    @JsonProperty("SCB_RiskGrade")
    private String sCBRiskGrade;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("SCB_RiskGrade")
    public String getSCBRiskGrade() {
        return sCBRiskGrade;
    }

    @JsonProperty("SCB_RiskGrade")
    public void setSCBRiskGrade(String sCBRiskGrade) {
        this.sCBRiskGrade = sCBRiskGrade;
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
		return "SCBFlag [sCBRiskGrade=" + sCBRiskGrade + ", additionalProperties=" + additionalProperties + "]";
	}

}
