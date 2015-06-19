package sample.VisitInformation.SiteActions;

import sample.VisitInformation.SiteAction;
import sample.VisitInformation.SiteActionType;

import java.util.Date;


public class SiteActionFactory {
    public static SiteAction getFromString(String string) {
        String[] data = string.split(" ");
        SiteActionType siteActionType = SiteActionType.valueOf(data[0]);
        Date date = new Date(Long.parseLong(data[1]));
        return get(siteActionType,date, data[2]);
    }

    public static SiteAction get(SiteActionType siteActionType, Date date, String productName) {
        switch (siteActionType) {
            case CALL:
                return new SiteActionCall(date);
            case PRODUCTPAGEENTER:
                return new SiteActionProductPageEnter(date, productName);
            case PURCHASE:
                return new SiteActionPurchase(date, productName);
            default:
                return null;
        }
    }
}
