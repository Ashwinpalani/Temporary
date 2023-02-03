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
    "SCB_Product",
    "SCB_SubProductCode",
    "SCB_AppAmt",
    "SCB_AppDt",
    "SCB_ApprovedDt",
    "SCB_DueCycleDay",
    "SCB_ProduceStmt",
    "SCB_RepaymentSchedule"
})

public class SCBLoanApplication {

    @JsonProperty("SCB_Product")
    private Integer sCBProduct;
    @JsonProperty("SCB_SubProductCode")
    private String sCBSubProductCode;
    @JsonProperty("SCB_AppAmt")
    private Integer sCBAppAmt;
    @JsonProperty("SCB_AppDt")
    private String sCBAppDt;
    @JsonProperty("SCB_ApprovedDt")
    private String sCBApprovedDt;
    @JsonProperty("SCB_DueCycleDay")
    private Integer sCBDueCycleDay;
    @JsonProperty("SCB_ProduceStmt")
    private Integer sCBProduceStmt;
    @JsonProperty("SCB_RepaymentSchedule")
    private Integer sCBRepaymentSchedule;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("SCB_Product")
    public Integer getSCBProduct() {
        return sCBProduct;
    }

    @JsonProperty("SCB_Product")
    public void setSCBProduct(Integer sCBProduct) {
        this.sCBProduct = sCBProduct;
    }

    @JsonProperty("SCB_SubProductCode")
    public String getSCBSubProductCode() {
        return sCBSubProductCode;
    }

    @JsonProperty("SCB_SubProductCode")
    public void setSCBSubProductCode(String sCBSubProductCode) {
        this.sCBSubProductCode = sCBSubProductCode;
    }

    @JsonProperty("SCB_AppAmt")
    public Integer getSCBAppAmt() {
        return sCBAppAmt;
    }

    @JsonProperty("SCB_AppAmt")
    public void setSCBAppAmt(Integer sCBAppAmt) {
        this.sCBAppAmt = sCBAppAmt;
    }

    @JsonProperty("SCB_AppDt")
    public String getSCBAppDt() {
        return sCBAppDt;
    }

    @JsonProperty("SCB_AppDt")
    public void setSCBAppDt(String sCBAppDt) {
        this.sCBAppDt = sCBAppDt;
    }

    @JsonProperty("SCB_ApprovedDt")
    public String getSCBApprovedDt() {
        return sCBApprovedDt;
    }

    @JsonProperty("SCB_ApprovedDt")
    public void setSCBApprovedDt(String sCBApprovedDt) {
        this.sCBApprovedDt = sCBApprovedDt;
    }

    @JsonProperty("SCB_DueCycleDay")
    public Integer getSCBDueCycleDay() {
        return sCBDueCycleDay;
    }

    @JsonProperty("SCB_DueCycleDay")
    public void setSCBDueCycleDay(Integer sCBDueCycleDay) {
        this.sCBDueCycleDay = sCBDueCycleDay;
    }

    @JsonProperty("SCB_ProduceStmt")
    public Integer getSCBProduceStmt() {
        return sCBProduceStmt;
    }

    @JsonProperty("SCB_ProduceStmt")
    public void setSCBProduceStmt(Integer sCBProduceStmt) {
        this.sCBProduceStmt = sCBProduceStmt;
    }

    @JsonProperty("SCB_RepaymentSchedule")
    public Integer getSCBRepaymentSchedule() {
        return sCBRepaymentSchedule;
    }

    @JsonProperty("SCB_RepaymentSchedule")
    public void setSCBRepaymentSchedule(Integer sCBRepaymentSchedule) {
        this.sCBRepaymentSchedule = sCBRepaymentSchedule;
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
		return "SCBLoanApplication [sCBProduct=" + sCBProduct + ", sCBSubProductCode=" + sCBSubProductCode
				+ ", sCBAppAmt=" + sCBAppAmt + ", sCBAppDt=" + sCBAppDt + ", sCBApprovedDt=" + sCBApprovedDt
				+ ", sCBDueCycleDay=" + sCBDueCycleDay + ", sCBProduceStmt=" + sCBProduceStmt
				+ ", sCBRepaymentSchedule=" + sCBRepaymentSchedule + ", additionalProperties=" + additionalProperties
				+ "]";
	}

}
