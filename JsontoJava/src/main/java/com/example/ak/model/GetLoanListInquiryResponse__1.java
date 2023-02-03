
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
    "header",
    "getLoanListInquiryResPayload"
})

public class GetLoanListInquiryResponse__1 {

    @JsonProperty("header")
    private Header header;
    @JsonProperty("getLoanListInquiryResPayload")
    private GetLoanListInquiryResPayload getLoanListInquiryResPayload;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("header")
    public Header getHeader() {
        return header;
    }

    @JsonProperty("header")
    public void setHeader(Header header) {
        this.header = header;
    }

    @JsonProperty("getLoanListInquiryResPayload")
    public GetLoanListInquiryResPayload getGetLoanListInquiryResPayload() {
        return getLoanListInquiryResPayload;
    }

    @JsonProperty("getLoanListInquiryResPayload")
    public void setGetLoanListInquiryResPayload(GetLoanListInquiryResPayload getLoanListInquiryResPayload) {
        this.getLoanListInquiryResPayload = getLoanListInquiryResPayload;
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
		return "GetLoanListInquiryResponse__1 [header=" + header + ", getLoanListInquiryResPayload="
				+ getLoanListInquiryResPayload + ", additionalProperties=" + additionalProperties + "]";
	}

}
