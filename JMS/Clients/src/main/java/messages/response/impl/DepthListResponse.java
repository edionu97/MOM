package messages.response.impl;

import messages.response.impl.abstracts.AbstractResponse;

import java.util.List;

public class DepthListResponse extends AbstractResponse {

    public List<List<String>> response;


    public List<List<String>> getResponse() {
        return response;
    }

    public void setResponse(final List<List<String>> response) {
        this.response = response;
    }
}
