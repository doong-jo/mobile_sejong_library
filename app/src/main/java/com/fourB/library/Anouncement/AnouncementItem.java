package com.fourB.library.Anouncement;

public class AnouncementItem {

    String Anouncement_Title;
    String Anouncement_Update_Date;
    String Anouncement_Detail_Url;

    public AnouncementItem(String anouncement_Title,
                           String anouncement_Update_Date, String anouncement_Detail_Url) {
        Anouncement_Title = anouncement_Title;
        Anouncement_Update_Date = anouncement_Update_Date;
        Anouncement_Detail_Url = anouncement_Detail_Url;
    }

    public String getAnouncement_Title() {
        return Anouncement_Title;
    }

    public String getAnouncement_Update_Date() {
        return Anouncement_Update_Date;
    }


    public String getAnouncement_Detail_Url() {
        return Anouncement_Detail_Url;
    }

}
