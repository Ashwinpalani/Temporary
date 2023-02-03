
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
    "BalType",
    "CurAmt"
})

public class AcctBal {

	@JsonProperty("BalType")
    private BalType balType;
    @JsonProperty("CurAmt")
    private CurAmt curAmt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("BalType")
    public BalType getBalType() {
        return balType;
    }

    @JsonProperty("BalType")
    public void setBalType(BalType balType) {
        this.balType = balType;
    }

    @JsonProperty("CurAmt")
    public CurAmt getCurAmt() {
        return curAmt;
    }

    @JsonProperty("CurAmt")
    public void setCurAmt(CurAmt curAmt) {
        this.curAmt = curAmt;
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
		return "AcctBal [balType=" + balType + ", curAmt=" + curAmt + ", additionalProperties=" + additionalProperties
				+ "]";
	}


}
