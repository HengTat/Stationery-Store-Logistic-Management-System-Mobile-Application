package iss.workshop.myapplication.Model;

import java.util.Date;

public class DisbursementModel {
    public int Id ;

    public Date DateRequested ;

    public Date DisbursedDate;

    public String DepartmentName;

    public int CollectionPointId ;

    public int getId() {
        return Id;
    }

    public Date getDateRequested() {
        return DateRequested;
    }

    public Date getDisbursedDate() {
        return DisbursedDate;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public int getCollectionPointId() {
        return CollectionPointId;
    }
}
