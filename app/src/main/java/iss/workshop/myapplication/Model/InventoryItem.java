package iss.workshop.myapplication.Model;

//AUTHOR: JAMES FOO
public class InventoryItem {

    private String id;
    private int itemCateggoryId;
    private String description;
    private String bin;
    private int requestQty;
    private int qtyInStock;
    private int reorderLevel;
    private int reorderQty;
    private String uom;
    private ItemCategory itemCategory;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getItemCateggoryId() {
        return itemCateggoryId;
    }

    public void setItemCateggoryId(int itemCateggoryId) {
        this.itemCateggoryId = itemCateggoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(ItemCategory itemCategory) {
        this.itemCategory = itemCategory;
    }
}
