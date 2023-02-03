
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
    "payloadFormat",
    "payloadVersion",
    "getLoanListInquiryRes"
})

public class GetLoanListInquiryResPayload {

    @JsonProperty("payloadFormat")
    private String payloadFormat;
    @JsonProperty("payloadVersion")
    private Integer payloadVersion;
    @JsonProperty("getLoanListInquiryRes")
    private GetLoanListInquiryRes getLoanListInquiryRes;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("payloadFormat")
    public String getPayloadFormat() {
        return payloadFormat;
    }

    @JsonProperty("payloadFormat")
    public void setPayloadFormat(String payloadFormat) {
        this.payloadFormat = payloadFormat;
    }

    @JsonProperty("payloadVersion")
    public Integer getPayloadVersion() {
        return payloadVersion;
    }

    @JsonProperty("payloadVersion")
    public void setPayloadVersion(Integer payloadVersion) {
        this.payloadVersion = payloadVersion;
    }

    @JsonProperty("getLoanListInquiryRes")
    public GetLoanListInquiryRes getGetLoanListInquiryRes() {
        return getLoanListInquiryRes;
    }

    @JsonProperty("getLoanListInquiryRes")
    public void setGetLoanListInquiryRes(GetLoanListInquiryRes getLoanListInquiryRes) {
        this.getLoanListInquiryRes = getLoanListInquiryRes;
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
		return "GetLoanListInquiryResPayload [payloadFormat=" + payloadFormat + ", payloadVersion=" + payloadVersion
				+ ", getLoanListInquiryRes=" + getLoanListInquiryRes + ", additionalProperties=" + additionalProperties
				+ "]";
	}

}
