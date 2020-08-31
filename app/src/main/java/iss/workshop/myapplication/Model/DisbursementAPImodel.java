package iss.workshop.myapplication.Model;

import java.util.Date;

//AUTHOR: CHONG HENG TAT
public class DisbursementAPImodel {

    public int Id ;

    public Date DateRequested ;

    public Date DisbursedDate;

    public String DepartmentName;

    public int CollectionPointId ;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Date getDateRequested() {
        return DateRequested;
    }

    public void setDateRequested(Date dateRequested) {
        DateRequested = dateRequested;
    }

    public Date getDisbursedDate() {
        return DisbursedDate;
    }

    public void setDisbursedDate(Date disbursedDate) {
        DisbursedDate = disbursedDate;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public int getCollectionPointId() {
        return CollectionPointId;
    }

    public void setCollectionPointId(int collectionPointId) {
        CollectionPointId = collectionPointId;
    }







}
