package services.message.msg;

import com.fasterxml.jackson.annotation.JsonProperty;
import utils.enums.OperationType;

import java.io.Serializable;

public class Message implements Serializable {

    @JsonProperty
    private String payload;

    @JsonProperty("type")
    private OperationType operationType;

    public String getPayload() {
        return payload;
    }

    public void setPayload(final String payload) {
        this.payload = payload;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(final OperationType operationType) {
        this.operationType = operationType;
    }
}
