package messages.response.impl;

import messages.response.impl.abstracts.AbstractResponse;

import java.util.List;

public class SimpleListResponse  extends AbstractResponse {

    private List<String> response;


    public List<String> getResponse() {
        return response;
    }

    public void setResponse(final List<String> response) {
        this.response = response;
    }
}
