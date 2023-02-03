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
    "messageDetails",
    "originationDetails",
    "captureSystem",
    "process"
})

public class Header {

    @JsonProperty("messageDetails")
    private MessageDetails messageDetails;
    @JsonProperty("originationDetails")
    private OriginationDetails originationDetails;
    @JsonProperty("captureSystem")
    private String captureSystem;
    @JsonProperty("process")
    private Process process;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("messageDetails")
    public MessageDetails getMessageDetails() {
        return messageDetails;
    }

    @JsonProperty("messageDetails")
    public void setMessageDetails(MessageDetails messageDetails) {
        this.messageDetails = messageDetails;
    }

    @JsonProperty("originationDetails")
    public OriginationDetails getOriginationDetails() {
        return originationDetails;
    }

    @JsonProperty("originationDetails")
    public void setOriginationDetails(OriginationDetails originationDetails) {
        this.originationDetails = originationDetails;
    }

    @JsonProperty("captureSystem")
    public String getCaptureSystem() {
        return captureSystem;
    }

    @JsonProperty("captureSystem")
    public void setCaptureSystem(String captureSystem) {
        this.captureSystem = captureSystem;
    }

    @JsonProperty("process")
    public Process getProcess() {
        return process;
    }

    @JsonProperty("process")
    public void setProcess(Process process) {
        this.process = process;
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
		return "Header [messageDetails=" + messageDetails + ", originationDetails=" + originationDetails
				+ ", captureSystem=" + captureSystem + ", process=" + process + ", additionalProperties="
				+ additionalProperties + "]";
	}

}
