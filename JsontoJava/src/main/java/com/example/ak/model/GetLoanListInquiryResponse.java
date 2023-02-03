
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
    "getLoanListInquiryResponse"
})

public class GetLoanListInquiryResponse {

    @JsonProperty("getLoanListInquiryResponse")
    private GetLoanListInquiryResponse__1 getLoanListInquiryResponse;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("getLoanListInquiryResponse")
    public GetLoanListInquiryResponse__1 getGetLoanListInquiryResponse() {
        return getLoanListInquiryResponse;
    }

    @JsonProperty("getLoanListInquiryResponse")
    public void setGetLoanListInquiryResponse(GetLoanListInquiryResponse__1 getLoanListInquiryResponse) {
        this.getLoanListInquiryResponse = getLoanListInquiryResponse;
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
		return "GetLoanListInquiryResponse [getLoanListInquiryResponse=" + getLoanListInquiryResponse
				+ ", additionalProperties=" + additionalProperties + "]";
	}

}
