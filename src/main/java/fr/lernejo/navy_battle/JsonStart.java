package fr.lernejo.navy_battle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonStart {
    public final String id;
    public final String url;
    public final String message;

    public JsonStart(@JsonProperty("id") JsonNode id, @JsonProperty("url") JsonNode url, @JsonProperty("message") JsonNode message) {
        this.id = String.valueOf(id);
        this.url = String.valueOf(url);
        this.message = String.valueOf(message);
    }
}
