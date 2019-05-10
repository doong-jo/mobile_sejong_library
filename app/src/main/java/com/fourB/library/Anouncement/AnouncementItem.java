package com.fourB.library.Anouncement;

public class AnouncementItem {

    int Anouncement_Num;
    String Anouncement_Title;
    String Anouncement_Update_Date;
    String Anouncement_Detail_Url;

    public AnouncementItem(int anouncement_Num, String anouncement_Title,
                           String anouncement_Update_Date, String anouncement_Detail_Url) {
        Anouncement_Num = anouncement_Num;
        Anouncement_Title = anouncement_Title;
        Anouncement_Update_Date = anouncement_Update_Date;
        Anouncement_Detail_Url = anouncement_Detail_Url;
    }

    public int getAnouncement_Num() {
        return Anouncement_Num;
    }

    public void setAnouncement_Num(int anouncement_Num) {
        Anouncement_Num = anouncement_Num;
    }

    public String getAnouncement_Title() {
        return Anouncement_Title;
    }

    public void setAnouncement_Title(String anouncement_Title) {
        Anouncement_Title = anouncement_Title;
    }

    public String getAnouncement_Update_Date() {
        return Anouncement_Update_Date;
    }

    public void setAnouncement_Update_Date(String anouncement_Update_Date) {
        Anouncement_Update_Date = anouncement_Update_Date;
    }

    public String getAnouncement_Detail_Url() {
        return Anouncement_Detail_Url;
    }

    public void setAnouncement_Detail_Url(String anouncement_Detail_Url) {
        Anouncement_Detail_Url = anouncement_Detail_Url;
    }
}
