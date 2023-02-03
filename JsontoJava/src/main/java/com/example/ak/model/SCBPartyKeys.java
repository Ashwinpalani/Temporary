
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
    "SCB_ClientIDType",
    "SCB_ClientIDNum",
    "SCB_PartyType"
})

public class SCBPartyKeys {

    @JsonProperty("SCB_ClientIDType")
    private Integer sCBClientIDType;
    @JsonProperty("SCB_ClientIDNum")
    private Long sCBClientIDNum;
    @JsonProperty("SCB_PartyType")
    private String sCBPartyType;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("SCB_ClientIDType")
    public Integer getSCBClientIDType() {
        return sCBClientIDType;
    }

    @JsonProperty("SCB_ClientIDType")
    public void setSCBClientIDType(Integer sCBClientIDType) {
        this.sCBClientIDType = sCBClientIDType;
    }

    @JsonProperty("SCB_ClientIDNum")
    public Long getSCBClientIDNum() {
        return sCBClientIDNum;
    }

    @JsonProperty("SCB_ClientIDNum")
    public void setSCBClientIDNum(Long sCBClientIDNum) {
        this.sCBClientIDNum = sCBClientIDNum;
    }

    @JsonProperty("SCB_PartyType")
    public String getSCBPartyType() {
        return sCBPartyType;
    }

    @JsonProperty("SCB_PartyType")
    public void setSCBPartyType(String sCBPartyType) {
        this.sCBPartyType = sCBPartyType;
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
		return "SCBPartyKeys [sCBClientIDType=" + sCBClientIDType + ", sCBClientIDNum=" + sCBClientIDNum
				+ ", sCBPartyType=" + sCBPartyType + ", additionalProperties=" + additionalProperties + "]";
	}

}
