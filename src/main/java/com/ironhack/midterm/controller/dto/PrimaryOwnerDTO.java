package com.ironhack.midterm.controller.dto;

public class PrimaryOwnerDTO {

    private String primaryOwner;

    public PrimaryOwnerDTO() {
    }

    public PrimaryOwnerDTO(String primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public String getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(String primaryOwner) {
        this.primaryOwner = primaryOwner;
    }
}
