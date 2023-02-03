
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
    "Count",
    "TermUnits",
    "Desc"
})

public class Term {

    @JsonProperty("Count")
    private Integer count;
    @JsonProperty("TermUnits")
    private String termUnits;
    @JsonProperty("Desc")
    private String desc;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Count")
    public Integer getCount() {
        return count;
    }

    @JsonProperty("Count")
    public void setCount(Integer count) {
        this.count = count;
    }

    @JsonProperty("TermUnits")
    public String getTermUnits() {
        return termUnits;
    }

    @JsonProperty("TermUnits")
    public void setTermUnits(String termUnits) {
        this.termUnits = termUnits;
    }

    @JsonProperty("Desc")
    public String getDesc() {
        return desc;
    }

    @JsonProperty("Desc")
    public void setDesc(String desc) {
        this.desc = desc;
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
		return "Term [count=" + count + ", termUnits=" + termUnits + ", desc=" + desc + ", additionalProperties="
				+ additionalProperties + "]";
	}

}
