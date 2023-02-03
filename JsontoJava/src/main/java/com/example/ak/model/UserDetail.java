
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
    "accountId",
    "availabilityState",
    "derivedAvailableFlag",
    "channelTypeId",
    "activeWorkCount",
    "workLimit"
})

public class UserDetail {

    @JsonProperty("accountId")
    private String accountId;
    @JsonProperty("availabilityState")
    private String availabilityState;
    @JsonProperty("derivedAvailableFlag")
    private String derivedAvailableFlag;
    @JsonProperty("channelTypeId")
    private String channelTypeId;
    @JsonProperty("activeWorkCount")
    private String activeWorkCount;
    @JsonProperty("workLimit")
    private String workLimit;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("accountId")
    public String getAccountId() {
        return accountId;
    }

    @JsonProperty("accountId")
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @JsonProperty("availabilityState")
    public String getAvailabilityState() {
        return availabilityState;
    }

    @JsonProperty("availabilityState")
    public void setAvailabilityState(String availabilityState) {
        this.availabilityState = availabilityState;
    }

    @JsonProperty("derivedAvailableFlag")
    public String getDerivedAvailableFlag() {
        return derivedAvailableFlag;
    }

    @JsonProperty("derivedAvailableFlag")
    public void setDerivedAvailableFlag(String derivedAvailableFlag) {
        this.derivedAvailableFlag = derivedAvailableFlag;
    }

    @JsonProperty("channelTypeId")
    public String getChannelTypeId() {
        return channelTypeId;
    }

    @JsonProperty("channelTypeId")
    public void setChannelTypeId(String channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    @JsonProperty("activeWorkCount")
    public String getActiveWorkCount() {
        return activeWorkCount;
    }

    @JsonProperty("activeWorkCount")
    public void setActiveWorkCount(String activeWorkCount) {
        this.activeWorkCount = activeWorkCount;
    }

    @JsonProperty("workLimit")
    public String getWorkLimit() {
        return workLimit;
    }

    @JsonProperty("workLimit")
    public void setWorkLimit(String workLimit) {
        this.workLimit = workLimit;
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
		return "UserDetail [accountId=" + accountId + ", availabilityState=" + availabilityState
				+ ", derivedAvailableFlag=" + derivedAvailableFlag + ", channelTypeId=" + channelTypeId
				+ ", activeWorkCount=" + activeWorkCount + ", workLimit=" + workLimit + ", additionalProperties="
				+ additionalProperties + "]";
	}

}
