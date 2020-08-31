package iss.workshop.myapplication.Model;
//AUTHOR: NGUI KAI LIN
public class RequestDetails {
    private int requestId;
    private String description;
    private int qtyRequested;

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQtyRequested() {
        return qtyRequested;
    }

    public void setQtyRequested(int qtyRequested) {
        this.qtyRequested = qtyRequested;
    }

    public RequestDetails(int requestId, String description, int qtyRequested) {
        this.requestId = requestId;
        this.description = description;
        this.qtyRequested = qtyRequested;
    }

    @Override
    public String toString() {
        return "RequestDetails{" +
                "requestId=" + requestId +
                ", description='" + description + '\'' +
                ", qtyRequested=" + qtyRequested +
                '}';
    }
}

