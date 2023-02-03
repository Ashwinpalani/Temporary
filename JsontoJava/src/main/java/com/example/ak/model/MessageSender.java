
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
    "messageSender",
    "senderDomain",
    "countryCode"
})

public class MessageSender {

    @JsonProperty("messageSender")
    private String messageSender;
    @JsonProperty("senderDomain")
    private SenderDomain senderDomain;
    @JsonProperty("countryCode")
    private String countryCode;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("messageSender")
    public String getMessageSender() {
        return messageSender;
    }

    @JsonProperty("messageSender")
    public void setMessageSender(String messageSender) {
        this.messageSender = messageSender;
    }

    @JsonProperty("senderDomain")
    public SenderDomain getSenderDomain() {
        return senderDomain;
    }

    @JsonProperty("senderDomain")
    public void setSenderDomain(SenderDomain senderDomain) {
        this.senderDomain = senderDomain;
    }

    @JsonProperty("countryCode")
    public String getCountryCode() {
        return countryCode;
    }

    @JsonProperty("countryCode")
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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
		return "MessageSender [messageSender=" + messageSender + ", senderDomain=" + senderDomain + ", countryCode="
				+ countryCode + ", additionalProperties=" + additionalProperties + "]";
	}

}
