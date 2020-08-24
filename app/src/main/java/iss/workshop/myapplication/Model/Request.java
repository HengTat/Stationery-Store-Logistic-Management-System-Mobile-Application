package iss.workshop.myapplication.Model;

import androidx.annotation.NonNull;

public class Request {
    private int Id;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Request(int Id) {
        this.Id = Id;
    }

    @NonNull
    @Override
    public String toString() {
        return "Request ID : " +  String.valueOf(Id);
    }
}
