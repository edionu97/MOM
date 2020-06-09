package messages.request.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import messages.request.IRequest;
import utils.enums.OperationType;

public class MiddlewareRequest implements IRequest {

    @JsonProperty
    private String payload;

    @JsonProperty
    private OperationType type;

    //region Getters and Setters
    public String getPayload() {
        return payload;
    }

    public void setPayload(final String payload) {
        this.payload = payload;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(final OperationType type) {
        this.type = type;
    }
    //endregion
}
