
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
    "messageVersion",
    "messageType"
})

public class MessageDetails {

    @JsonProperty("messageVersion")
    private Integer messageVersion;
    @JsonProperty("messageType")
    private MessageType messageType;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("messageVersion")
    public Integer getMessageVersion() {
        return messageVersion;
    }

    @JsonProperty("messageVersion")
    public void setMessageVersion(Integer messageVersion) {
        this.messageVersion = messageVersion;
    }

    @JsonProperty("messageType")
    public MessageType getMessageType() {
        return messageType;
    }

    @JsonProperty("messageType")
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
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
		return "MessageDetails [messageVersion=" + messageVersion + ", messageType=" + messageType
				+ ", additionalProperties=" + additionalProperties + "]";
	}

}
