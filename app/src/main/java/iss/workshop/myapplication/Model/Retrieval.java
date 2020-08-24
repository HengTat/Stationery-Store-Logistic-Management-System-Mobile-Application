package iss.workshop.myapplication.Model;

import java.util.Date;

public class Retrieval {
    private int Id;
    private Date DateRetrieved;
    private int EmployeeId;

    public Retrieval(int Id, Date DateRetrieved, int EmployeeId){
        this.Id=Id;
        this.DateRetrieved=DateRetrieved;
        this.EmployeeId=EmployeeId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Date getDateRetrieved() {
        return DateRetrieved;
    }

    public void setDateRetrieved(Date dateRetrieved) {
        DateRetrieved = dateRetrieved;
    }

    public int getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(int employeeId) {
        EmployeeId = employeeId;
    }
}
