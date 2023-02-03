
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
    "SegmentType",
    "SegmentValue"
})

public class Segmentation {

    @JsonProperty("SegmentType")
    private String segmentType;
    @JsonProperty("SegmentValue")
    private String segmentValue;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("SegmentType")
    public String getSegmentType() {
        return segmentType;
    }

    @JsonProperty("SegmentType")
    public void setSegmentType(String segmentType) {
        this.segmentType = segmentType;
    }

    @JsonProperty("SegmentValue")
    public String getSegmentValue() {
        return segmentValue;
    }

    @JsonProperty("SegmentValue")
    public void setSegmentValue(String segmentValue) {
        this.segmentValue = segmentValue;
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
		return "Segmentation [segmentType=" + segmentType + ", segmentValue=" + segmentValue + ", additionalProperties="
				+ additionalProperties + "]";
	}

}
