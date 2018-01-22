package com.vowme.app.models;

import com.facebook.internal.ServerProtocol;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.services.network.HttpRequest;

public class Enum {

    public enum ActivityCode {
        GETSTARTED(0),
        ADJUSTMENT(1),
        FILETERING(2),
        RESULTFROMBOOKMARK(3),
        RESULTFROMSEARCH(4),
        EXPRESSEDINTEREST(5),
        PROFILEEDIT(6),
        SETTINGSNOTIFICATION(7),
        OPPORTUNITY(8),
        LOGHOURS(9),
        PUSHSETTINGSNOTIFICATION(10),
        RC_SIGN_IN(9001);

        private int value;

        private ActivityCode(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public enum AutoCompleteCategorie {
        KEYWORD(0),
        RECENTS(1),
        CAUSES(2),
        INTERESTS(3),
        ORGANISATIONS(4);

        private int value;

        private AutoCompleteCategorie(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public enum AvaibilityDayType {
        MONDAY("Monday"),
        TUESDAY("Tuesday"),
        WEDNESDAY("Wednesday"),
        THURSDAY("Thursday"),
        FRIDAY("Friday"),
        SATURDAY("Saturday"),
        SUNDAY("Sunday"),
        PUBLIC("Public Holiday");

        private String value;

        private AvaibilityDayType(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public enum AvaibilityFilterId {
        DAY(1),
        EVENING(2),
        WEEKDAYS(3),
        WEEKEND(4);

        private Integer value;

        private AvaibilityFilterId(int value) {
            this.value = Integer.valueOf(value);
        }

        public Integer getValue() {
            return this.value;
        }
    }

    public enum AvaibilityType {
        MORNING("Morning"),
        AFTERNOON("Afternoon"),
        EVENING("Evening");

        private String value;

        private AvaibilityType(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public enum BOOLEAN {
        TRUE(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE),
        FALSE("false");

        private String value;

        private BOOLEAN(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public enum HttpRequestType {
        GET(HttpRequest.METHOD_GET),
        POST(HttpRequest.METHOD_POST),
        DELETE(HttpRequest.METHOD_DELETE),
        PUT(HttpRequest.METHOD_PUT);

        private String value;

        private HttpRequestType(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public enum ListViewType {
        LOADING(0),
        RECOMMENDED(1),
        SHORTLIST(2),
        EXPRESSED(3),
        SEARCHRESULT(4),
        LASTROW(5),
        TIMESHEET(6),
        FEEDBACK(7);

        private int value;

        private ListViewType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public enum LoginProvider {
        FACEBOOK("Facebook"),
        LINKEDIN("LinkedIn"),
        GOOGLE("Google"),
        TWITTER(TwitterCore.TAG);

        private String value;

        private LoginProvider(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public enum LookupType {
        LOCATIONS("locations"),
        CAUSES("causes"),
        INTERESTS("interests"),
        DURATIONS("durations"),
        PROGRAMS("programs/explanation"),
        REQUIREMENTS("requirements"),
        LANGUAGUES("languages"),
        TRANSPORTS("transports"),
        TYPEOFCARS("typesOfCar"),
        TSHIRTSIZE("tShirtSizes"),
        TITLES("titles"),
        PROSKILLS("classifications"),
        EXPERIENCES("experiences"),
        SUBPROSKILLS("subClassifications"),
        NOTIFICATIONFREQUENCIES("notificationFrequencies"),
        AVAIBILITYFILTER("avaibilitiyFilter"),
        AVAIBILITY("avaibilitiy");

        private String value;

        private LookupType(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public enum PostAccessTokenType {
        CREDENTIAL(1),
        PASSWORD(2),
        REFRESH(3),
        EXTERNAL(4);

        private int value;

        private PostAccessTokenType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public enum SharedPreferencesKeys {
        USER_OAUTH_SHARED_DATA("USER_OAUTH_SHARED_DATA"),
        USER_ADJUSTMENT_SHARED_DATA("USER_ADJUSTMENT_SHARED_DATA"),
        USER_SEARCH_FILTER_SHARED_DATA("USER_SEARCH_FILTER_SHARED_DATA"),
        USER_SEARCH_LOCATION_FIELD("USER_SEARCH_LOCATION_FIELD"),
        USER_SHORTLIST_SHARED_DATA("USER_SHORTLIST_SHARED_DATA"),
        USER_BOOKMARK_SHARED_DATA("USER_BOOKMARK_SHARED_DATA"),
        USER_NAVIGATION_SHARED_DATA("USER_NAVIGATION_SHARED_DATA");

        private String value;

        private SharedPreferencesKeys(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
}
