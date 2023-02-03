
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
    "Header",
    "Body"
})

public class Envelope {

    @JsonProperty("Header")
    private String header;
    @JsonProperty("Body")
    private Body body;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Header")
    public String getHeader() {
        return header;
    }

    @JsonProperty("Header")
    public void setHeader(String header) {
        this.header = header;
    }

    @JsonProperty("Body")
    public Body getBody() {
        return body;
    }

    @JsonProperty("Body")
    public void setBody(Body body) {
        this.body = body;
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
		//return "Envelope [header=" + header + ", body=" + body + ", additionalProperties=" + additionalProperties + "]";
		return "Envelope [header=" + header + ", body=" + body + "]";
	}

}
