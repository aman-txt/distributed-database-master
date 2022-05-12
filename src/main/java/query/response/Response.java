package query.response;

public class Response {

    private  ResponseType responseType;             //"success" or "failed"
    private  String description;        //description

    public Response(ResponseType responseType, String description) {
        this.responseType = responseType;
        this.description = description;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
