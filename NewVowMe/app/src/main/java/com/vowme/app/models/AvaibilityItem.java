package com.vowme.app.models;

import com.vowme.app.models.Enum.AvaibilityType;

public class AvaibilityItem {
    public boolean isChecked;
    public AvaibilityType type;

    public AvaibilityItem(AvaibilityType type) {
        this.type = type;
    }
}
