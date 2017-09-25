package com.vowme.app.models;

import com.vowme.app.models.Enum.AvaibilityDayType;
import com.vowme.app.models.Enum.AvaibilityType;

import java.util.ArrayList;
import java.util.List;

public class DayAvaibilityItem {
    public List<AvaibilityItem> avaibilityItems;
    public int day;
    public boolean isSelected;
    private AvaibilityDayType type;

    public DayAvaibilityItem(int day) {
        this.day = day;
        initAvaibilities();
    }

    private void initAvaibilities() {
        this.avaibilityItems = new ArrayList();
        this.avaibilityItems.add(new AvaibilityItem(AvaibilityType.MORNING));
        this.avaibilityItems.add(new AvaibilityItem(AvaibilityType.AFTERNOON));
        this.avaibilityItems.add(new AvaibilityItem(AvaibilityType.EVENING));
    }

    public boolean isMorning() {
        return ((AvaibilityItem) this.avaibilityItems.get(0)).isChecked;
    }

    public void setMorning(boolean isChecked) {
        ((AvaibilityItem) this.avaibilityItems.get(0)).isChecked = isChecked;
    }

    public boolean isAfternoon() {
        return ((AvaibilityItem) this.avaibilityItems.get(1)).isChecked;
    }

    public void setAfternoon(boolean isChecked) {
        ((AvaibilityItem) this.avaibilityItems.get(1)).isChecked = isChecked;
    }

    public boolean isEvening() {
        return ((AvaibilityItem) this.avaibilityItems.get(2)).isChecked;
    }

    public void setEvening(boolean isChecked) {
        ((AvaibilityItem) this.avaibilityItems.get(2)).isChecked = isChecked;
    }

    public void setIsSelected() {
        boolean z;
        if (((AvaibilityItem) this.avaibilityItems.get(0)).isChecked || ((AvaibilityItem) this.avaibilityItems.get(1)).isChecked || ((AvaibilityItem) this.avaibilityItems.get(2)).isChecked) {
            z = true;
        } else {
            z = false;
        }
        this.isSelected = z;
    }

    public AvaibilityDayType getType() {
        switch (this.day) {
            case 1:
                return AvaibilityDayType.MONDAY;
            case 2:
                return AvaibilityDayType.TUESDAY;
            case 3:
                return AvaibilityDayType.WEDNESDAY;
            case 4:
                return AvaibilityDayType.THURSDAY;
            case 5:
                return AvaibilityDayType.FRIDAY;
            case 6:
                return AvaibilityDayType.SATURDAY;
            case 7:
                return AvaibilityDayType.SUNDAY;
            case 8:
                return AvaibilityDayType.PUBLIC;
            default:
                return AvaibilityDayType.MONDAY;
        }
    }

    public void unCheckedAll() {
        for (AvaibilityItem avaibility : this.avaibilityItems) {
            avaibility.isChecked = false;
        }
    }
}
