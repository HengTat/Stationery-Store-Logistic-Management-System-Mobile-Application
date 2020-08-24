package iss.workshop.myapplication.Model;

public class DisbursementDetailModel {
    public int Id;
    public int QtyNeeded;
    public int QtyReceived;
    public int DisbursementId;
    public String InventoryItemId;
    public String ItemDescription;

    public int getQtyReceived() {
        return QtyReceived;
    }

    public void setQtyReceived(int qtyReceived) {
        QtyReceived = qtyReceived;
    }

    public DisbursementDetailModel() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getQtyNeeded() {
        return QtyNeeded;
    }

    public void setQtyNeeded(int qtyNeeded) {
        QtyNeeded = qtyNeeded;
    }

    public int getDisbursementId() {
        return DisbursementId;
    }

    public void setDisbursementId(int disbursementId) {
        DisbursementId = disbursementId;
    }

    public String getInventoryItemId() {
        return InventoryItemId;
    }

    public void setInventoryItemId(String inventoryItemId) {
        InventoryItemId = inventoryItemId;
    }

    public String getItemDescription() {
        return ItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        ItemDescription = itemDescription;
    }

}
