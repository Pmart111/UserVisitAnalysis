package sample.VisitInformation.SiteActions;

import sample.VisitInformation.SiteAction;
import sample.VisitInformation.SiteActionType;

import java.util.Date;

public class SiteActionPurchase extends SiteAction {
    private String productName;

    public SiteActionPurchase(Date date, String productName) {
        super(date);
        this.productName = productName;
    }

    @Override
    public String toString() {
        return SiteActionType.PURCHASE + " " + date.getTime() + " " + productName + " ";
    }
}
