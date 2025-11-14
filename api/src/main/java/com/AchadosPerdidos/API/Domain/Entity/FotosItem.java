package com.AchadosPerdidos.API.Domain.Entity;


public class FotosItem {
    private Integer itemId;
    private Integer fotoId;

    public FotosItem() {}

    public FotosItem(Integer itemId, Integer fotoId) {
        this.itemId = itemId;
        this.fotoId = fotoId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getFotoId() {
        return fotoId;
    }

    public void setFotoId(Integer fotoId) {
        this.fotoId = fotoId;
    }

}

