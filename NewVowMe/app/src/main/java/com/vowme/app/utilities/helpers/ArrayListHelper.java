package com.vowme.app.utilities.helpers;

import android.text.TextUtils;

import com.vowme.app.models.lookUp.Lookup;
import com.vowme.app.models.lookUp.LookupDesc;

import java.util.ArrayList;
import java.util.List;

public class ArrayListHelper {

    public static ArrayList<String> convertToListString(List<Integer> list) {
        ArrayList<String> result = new ArrayList();
        for (Integer myInt : list) {
            result.add(String.valueOf(myInt));
        }
        return result;
    }

    public static ArrayList<Integer> convertToListInteger(List<String> list) {
        ArrayList<Integer> result = new ArrayList();
        for (String myString : list) {
            result.add(Integer.valueOf(Integer.parseInt(myString)));
        }
        return result;
    }

    public static ArrayList<Integer> convertToListHashCode(List<String> list) {
        ArrayList<Integer> result = new ArrayList();
        for (String myString : list) {
            result.add(Integer.valueOf(myString.hashCode()));
        }
        return result;
    }

    public static String getNameFromId(int id, List<Lookup> list) {
        String result = "";
        for (Lookup myLookup : list) {
            if (myLookup.getId() == id) {
                result = myLookup.getName();
            }
        }
        return result;
    }

    public static String getNameFromIdDesc(int id, List<LookupDesc> list) {
        String result = "";
        for (LookupDesc myLookup : list) {
            if (myLookup.getId() == id) {
                result = myLookup.getName();
            }
        }
        return result;
    }

    public static int getIdFromName(String name, List<Lookup> list) {
        int result = 0;
        for (Lookup myLookup : list) {
            if (myLookup.getName().equals(name)) {
                result = myLookup.getId();
            }
        }
        return result;
    }

    public static List<List<String>> buildSentence(List<Integer> Morning, List<Integer> Afternoon, List<Integer> Evening) {
        List<Integer> morning = new ArrayList(Morning);
        List<Integer> afternoon = new ArrayList(Afternoon);
        List<Integer> evening = new ArrayList(Evening);
        List<List<String>> result = new ArrayList();
        List<Integer> weekDays = new C09141();
        List<Integer> weekEnd = new C09152();
        List<String> list = new ArrayList();
        if (morning.size() == 8 && afternoon.size() == 8 && evening.size() == 8) {
            list.add("Any day");
            list.add("at");
            list.add("anytime.");
            result.add(list);
        } else {
            String sentence = "";
            if (morning.containsAll(weekDays) && afternoon.containsAll(weekDays) && evening.containsAll(weekDays)) {
                sentence = "Week days";
                morning.removeAll(weekDays);
                afternoon.removeAll(weekDays);
                evening.removeAll(weekDays);
            }
            if (morning.containsAll(weekEnd) && afternoon.containsAll(weekEnd) && evening.containsAll(weekEnd)) {
                sentence = sentence + (TextUtils.isEmpty(sentence) ? "" : ", ") + "Weekend";
                morning.removeAll(weekEnd);
                afternoon.removeAll(weekEnd);
                evening.removeAll(weekEnd);
            }
            if (morning.contains(Integer.valueOf(8)) && afternoon.contains(Integer.valueOf(8)) && evening.contains(Integer.valueOf(8))) {
                sentence = sentence + (TextUtils.isEmpty(sentence) ? "" : ", ") + "Public holydays";
                morning.remove(8);
                afternoon.remove(8);
                evening.remove(8);
            }
            List<Integer> daysAnytime = new ArrayList(morning);
            daysAnytime.retainAll(afternoon);
            daysAnytime.retainAll(evening);
            if (daysAnytime.size() > 0) {
                for (Integer day : daysAnytime) {
                    sentence = sentence + (TextUtils.isEmpty(sentence) ? "" : ", ") + getDayName(day.intValue());
                    morning.remove(day);
                    afternoon.remove(day);
                    evening.remove(day);
                }
            }
            if (!TextUtils.isEmpty(sentence)) {
                list = new ArrayList();
                list.add(sentence);
                list.add("at");
                list.add("anytime.");
                result.add(list);
            }
            List<Integer> daysOnlyMorningAfternoon = new ArrayList(morning);
            daysOnlyMorningAfternoon.retainAll(afternoon);
            daysOnlyMorningAfternoon.removeAll(daysAnytime);
            if (daysOnlyMorningAfternoon.size() > 0) {
                result.add(getSentence(daysOnlyMorningAfternoon, "day"));
            }
            List<Integer> daysOnlyEvening = new ArrayList(evening);
            daysOnlyEvening.removeAll(daysAnytime);
            daysOnlyEvening.removeAll(daysOnlyMorningAfternoon);
            if (daysOnlyEvening.size() > 0) {
                result.add(getSentence(daysOnlyEvening, "evening"));
            }
            List<Integer> daysOnlyMorning = new ArrayList(morning);
            daysOnlyMorning.removeAll(daysAnytime);
            daysOnlyMorning.removeAll(daysOnlyMorningAfternoon);
            if (daysOnlyMorning.size() > 0) {
                result.add(getSentence(daysOnlyMorning, "morning"));
            }
            List<Integer> daysOnlyAfternoon = new ArrayList(afternoon);
            daysOnlyAfternoon.removeAll(daysAnytime);
            daysOnlyAfternoon.removeAll(daysOnlyMorningAfternoon);
            if (daysOnlyAfternoon.size() > 0) {
                result.add(getSentence(daysOnlyAfternoon, "afternoon"));
            }
        }
        return result;
    }

    private static List<String> getSentence(List<Integer> avaibilities, String time) {
        List<String> list = new ArrayList();
        String sentence = "";
        for (Integer intValue : avaibilities) {
            sentence = sentence + (TextUtils.isEmpty(sentence) ? "" : ", ") + getDayName(intValue.intValue());
        }
        list.add(sentence);
        list.add("during the");
        list.add(time + ".");
        return list;
    }

    public static String getDayName(int day) {
        String result = "";
        switch (day) {
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";
            case 7:
                return "Sunday";
            case 8:
                return "Public holidays";
            default:
                return "";
        }
    }

    static class C09141 extends ArrayList<Integer> {
        C09141() {
            add(Integer.valueOf(1));
            add(Integer.valueOf(2));
            add(Integer.valueOf(3));
            add(Integer.valueOf(4));
            add(Integer.valueOf(5));
        }
    }

    static class C09152 extends ArrayList<Integer> {
        C09152() {
            add(Integer.valueOf(6));
            add(Integer.valueOf(7));
        }
    }
}
