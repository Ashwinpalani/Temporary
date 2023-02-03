
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
    "Status",
    "SCBPartyKeys",
    "SCBLoanTrn",
    "SCBCustomerEnquiry"
})

public class GetLoanListInquiryRs {

    @JsonProperty("Status")
    private Status status;
    @JsonProperty("SCBPartyKeys")
    private SCBPartyKeys sCBPartyKeys;
    @JsonProperty("SCBLoanTrn")
    private SCBLoanTrn sCBLoanTrn;
    @JsonProperty("SCBCustomerEnquiry")
    private List<SCBCustomerEnquiry> sCBCustomerEnquiry = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Status")
    public Status getStatus() {
        return status;
    }

    @JsonProperty("Status")
    public void setStatus(Status status) {
        this.status = status;
    }

    @JsonProperty("SCBPartyKeys")
    public SCBPartyKeys getSCBPartyKeys() {
        return sCBPartyKeys;
    }

    @JsonProperty("SCBPartyKeys")
    public void setSCBPartyKeys(SCBPartyKeys sCBPartyKeys) {
        this.sCBPartyKeys = sCBPartyKeys;
    }

    @JsonProperty("SCBLoanTrn")
    public SCBLoanTrn getSCBLoanTrn() {
        return sCBLoanTrn;
    }

    @JsonProperty("SCBLoanTrn")
    public void setSCBLoanTrn(SCBLoanTrn sCBLoanTrn) {
        this.sCBLoanTrn = sCBLoanTrn;
    }

    @JsonProperty("SCBCustomerEnquiry")
    public List<SCBCustomerEnquiry> getSCBCustomerEnquiry() {
        return sCBCustomerEnquiry;
    }

    @JsonProperty("SCBCustomerEnquiry")
    public void setSCBCustomerEnquiry(List<SCBCustomerEnquiry> sCBCustomerEnquiry) {
        this.sCBCustomerEnquiry = sCBCustomerEnquiry;
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
		return "GetLoanListInquiryRs [status=" + status + ", sCBPartyKeys=" + sCBPartyKeys + ", sCBLoanTrn="
				+ sCBLoanTrn + ", sCBCustomerEnquiry=" + sCBCustomerEnquiry + ", additionalProperties="
				+ additionalProperties + "]";
	}

}
