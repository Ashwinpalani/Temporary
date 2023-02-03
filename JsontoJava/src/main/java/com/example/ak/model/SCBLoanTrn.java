
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
    "SCB_PageNum",
    "SCB_NextLoan"
})

public class SCBLoanTrn {

    @JsonProperty("SCB_PageNum")
    private Integer sCBPageNum;
    @JsonProperty("SCB_NextLoan")
    private Integer sCBNextLoan;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("SCB_PageNum")
    public Integer getSCBPageNum() {
        return sCBPageNum;
    }

    @JsonProperty("SCB_PageNum")
    public void setSCBPageNum(Integer sCBPageNum) {
        this.sCBPageNum = sCBPageNum;
    }

    @JsonProperty("SCB_NextLoan")
    public Integer getSCBNextLoan() {
        return sCBNextLoan;
    }

    @JsonProperty("SCB_NextLoan")
    public void setSCBNextLoan(Integer sCBNextLoan) {
        this.sCBNextLoan = sCBNextLoan;
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
		return "SCBLoanTrn [sCBPageNum=" + sCBPageNum + ", sCBNextLoan=" + sCBNextLoan + ", additionalProperties="
				+ additionalProperties + "]";
	}

}
