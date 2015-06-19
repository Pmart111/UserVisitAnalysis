package sample;

import sample.VisitInformation.FromInformation;
import sample.VisitInformation.SiteAction;
import sample.VisitInformation.SiteActions.SiteActionFactory;
import sample.VisitInformation.WhenInformation;

import java.util.ArrayList;
import java.util.List;


public class Visit {
    private static int globalID = 0;
    private int ID;
    private FromInformation fromInformation;
    private WhenInformation whenInformation;
    private ArrayList<SiteAction> siteActions;

    public Visit() {
        ID = globalID ++;
        siteActions = new ArrayList<SiteAction>();
    }


    public Visit(FromInformation fromInformation, WhenInformation whenInformation) {
        this.fromInformation = fromInformation;
        this.whenInformation = whenInformation;
        ID = globalID ++;
        siteActions = new ArrayList<SiteAction>();
    }


    public Visit(String string) {
        String[] data = string.split(" ");
        fromInformation = new FromInformation(data[0] + " " + data[1]);
        whenInformation = new WhenInformation(data[2] + " " + data[3]);
        ID = globalID ++;
        siteActions = new ArrayList<SiteAction>();
        for (int i = 4; i < data.length; i += 3) {
            siteActions.add(SiteActionFactory.getFromString(data[i] + " " + data[i + 1] + " " + data[i + 2]));
        }
    }

    public FromInformation getFromInformation() {
        return fromInformation;
    }
    public void setFromInformation(FromInformation fromInformation) {
        this.fromInformation = fromInformation;
    }
    public WhenInformation getWhenInformation() {
        return whenInformation;
    }
    public void setWhenInformation(WhenInformation whenInformation) {
        this.whenInformation = whenInformation;
    }
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public ArrayList<SiteAction> getSiteActions() {
        return siteActions;
    }


    @Override
    public String toString() {
        String string = fromInformation.toString() + " " + whenInformation.toString() + " ";
        for (SiteAction siteAction : siteActions) {
            string += siteAction.toString();
        }
        return string;
    }
    public List<String> toStringList() {
        ArrayList<String> list = new ArrayList<String>();
        list.add(Integer.toString(ID));
        list.add(fromInformation.getSiteName());
        list.add(fromInformation.getFromType().toString());
        list.add(whenInformation.getDate().toString());
        list.add(Long.toString(whenInformation.getTimeAfterBannerDisplay().getTime()) + " ms");
        list.add(siteActions.get(0).toString());
        return list;
    }


    public TableVisit getTableVisit() {
        String actions = "";
        for (SiteAction siteAction : siteActions) {
            actions += siteAction.toString();
        }
        return new TableVisit(Integer.toString(ID), fromInformation.getSiteName(), fromInformation.getFromType().toString(), whenInformation.getDate().toString(), Long.toString(whenInformation.getTimeAfterBannerDisplay().getTime()) + " ms", actions);
    }
}
