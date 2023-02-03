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
    "messageTimestamp",
    "initiatedTimestamp",
    "trackingId",
    "customSearchID",
    "possibleDuplicate"
})

public class OriginationDetails {

    @JsonProperty("messageSender")
    private MessageSender messageSender;
    @JsonProperty("messageTimestamp")
    private String messageTimestamp;
    @JsonProperty("initiatedTimestamp")
    private String initiatedTimestamp;
    @JsonProperty("trackingId")
    private String trackingId;
    @JsonProperty("customSearchID")
    private String customSearchID;
    @JsonProperty("possibleDuplicate")
    private Boolean possibleDuplicate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("messageSender")
    public MessageSender getMessageSender() {
        return messageSender;
    }

    @JsonProperty("messageSender")
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @JsonProperty("messageTimestamp")
    public String getMessageTimestamp() {
        return messageTimestamp;
    }

    @JsonProperty("messageTimestamp")
    public void setMessageTimestamp(String messageTimestamp) {
        this.messageTimestamp = messageTimestamp;
    }

    @JsonProperty("initiatedTimestamp")
    public String getInitiatedTimestamp() {
        return initiatedTimestamp;
    }

    @JsonProperty("initiatedTimestamp")
    public void setInitiatedTimestamp(String initiatedTimestamp) {
        this.initiatedTimestamp = initiatedTimestamp;
    }

    @JsonProperty("trackingId")
    public String getTrackingId() {
        return trackingId;
    }

    @JsonProperty("trackingId")
    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    @JsonProperty("customSearchID")
    public String getCustomSearchID() {
        return customSearchID;
    }

    @JsonProperty("customSearchID")
    public void setCustomSearchID(String customSearchID) {
        this.customSearchID = customSearchID;
    }

    @JsonProperty("possibleDuplicate")
    public Boolean getPossibleDuplicate() {
        return possibleDuplicate;
    }

    @JsonProperty("possibleDuplicate")
    public void setPossibleDuplicate(Boolean possibleDuplicate) {
        this.possibleDuplicate = possibleDuplicate;
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
		return "OriginationDetails [messageSender=" + messageSender + ", messageTimestamp=" + messageTimestamp
				+ ", initiatedTimestamp=" + initiatedTimestamp + ", trackingId=" + trackingId + ", customSearchID="
				+ customSearchID + ", possibleDuplicate=" + possibleDuplicate + ", additionalProperties="
				+ additionalProperties + "]";
	}

}
