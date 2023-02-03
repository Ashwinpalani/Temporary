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
    "domainName",
    "subDomainName"
})

public class SenderDomain {

    @JsonProperty("domainName")
    private String domainName;
    @JsonProperty("subDomainName")
    private SubDomainName subDomainName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("domainName")
    public String getDomainName() {
        return domainName;
    }

    @JsonProperty("domainName")
    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    @JsonProperty("subDomainName")
    public SubDomainName getSubDomainName() {
        return subDomainName;
    }

    @JsonProperty("subDomainName")
    public void setSubDomainName(SubDomainName subDomainName) {
        this.subDomainName = subDomainName;
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
		return "SenderDomain [domainName=" + domainName + ", subDomainName=" + subDomainName + ", additionalProperties="
				+ additionalProperties + "]";
	}

}
