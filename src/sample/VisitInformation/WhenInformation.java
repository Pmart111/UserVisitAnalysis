package sample.VisitInformation;

import java.sql.Time;
import java.util.Date;


public class WhenInformation {
    private Date timeAfterBannerDisplay;
    private Date date;
    public WhenInformation() {}
    public WhenInformation (String string) {
        String[] data = string.split(" ");
        date = new Date(Long.parseLong(data[0]));
        timeAfterBannerDisplay = new Date(Long.parseLong(data[1]));
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public Date getTimeAfterBannerDisplay() {
        return timeAfterBannerDisplay;
    }

    public void setTimeAfterBannerDisplay(Date timeAfterBannerDisplay) {
        this.timeAfterBannerDisplay = timeAfterBannerDisplay;
    }
    @Override
    public String toString() {
        return date.getTime() + " " + timeAfterBannerDisplay.getTime();
    }

}
