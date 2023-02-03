
package com.example.ak.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "userDn",
    "userState",
    "workState",
    "workCode",
    "userDetailList",
    "stateEffectiveDT"
})
public class Employee {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("userDn")
    private String userDn;
    @JsonProperty("userState")
    private String userState;
    @JsonProperty("workState")
    private String workState;
    @JsonProperty("workCode")
    private String workCode;
    @JsonProperty("userDetailList")
    private List<UserDetail> userDetailList = null;
    @JsonProperty("stateEffectiveDT")
    private String stateEffectiveDT;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("userDn")
    public String getUserDn() {
        return userDn;
    }

    @JsonProperty("userDn")
    public void setUserDn(String userDn) {
        this.userDn = userDn;
    }

    @JsonProperty("userState")
    public String getUserState() {
        return userState;
    }

    @JsonProperty("userState")
    public void setUserState(String userState) {
        this.userState = userState;
    }

    @JsonProperty("workState")
    public String getWorkState() {
        return workState;
    }

    @JsonProperty("workState")
    public void setWorkState(String workState) {
        this.workState = workState;
    }

    @JsonProperty("workCode")
    public String getWorkCode() {
        return workCode;
    }

    @JsonProperty("workCode")
    public void setWorkCode(String workCode) {
        this.workCode = workCode;
    }

    @JsonProperty("userDetailList")
    public List<UserDetail> getUserDetailList() {
        return userDetailList;
    }

    @JsonProperty("userDetailList")
    public void setUserDetailList(List<UserDetail> userDetailList) {
        this.userDetailList = userDetailList;
    }

    @JsonProperty("stateEffectiveDT")
    public String getStateEffectiveDT() {
        return stateEffectiveDT;
    }

    @JsonProperty("stateEffectiveDT")
    public void setStateEffectiveDT(String stateEffectiveDT) {
        this.stateEffectiveDT = stateEffectiveDT;
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
		return "Employee [id=" + id + ", userDn=" + userDn + ", userState=" + userState + ", workState=" + workState
				+ ", workCode=" + workCode + ", userDetailList=" + userDetailList + ", stateEffectiveDT="
				+ stateEffectiveDT + ", additionalProperties=" + additionalProperties + "]";
	}

}
