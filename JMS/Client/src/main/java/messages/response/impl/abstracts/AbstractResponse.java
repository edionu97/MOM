package messages.response.impl.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import messages.request.impl.MiddlewareRequest;
import messages.response.IResponse;
import messages.response.impl.DepthListResponse;
import messages.response.impl.SimpleListResponse;

public abstract class AbstractResponse implements IResponse {

    @JsonProperty
    private MiddlewareRequest onRequest;

    public MiddlewareRequest getOnRequest() {
        return onRequest;
    }

    public void setOnRequest(final MiddlewareRequest onRequest) {
        this.onRequest = onRequest;
    }

}
