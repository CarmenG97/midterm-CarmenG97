package com.ironhack.midterm.controller.dto;

import com.ironhack.midterm.model.AccountHolder;

public class CheckingDTO {

    public long primaryAccountHolderId;

    public CheckingDTO() {
    }

    public CheckingDTO(long primaryAccountHolderId) {
        this.primaryAccountHolderId = primaryAccountHolderId;
    }

    public long getPrimaryAccountHolderId() {
        return primaryAccountHolderId;
    }

    public void setPrimaryAccountHolderId(long primaryAccountHolderId) {
        this.primaryAccountHolderId = primaryAccountHolderId;
    }
}