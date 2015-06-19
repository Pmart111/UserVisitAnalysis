package sample.VisitInformation.SiteActions;

import sample.VisitInformation.SiteAction;
import sample.VisitInformation.SiteActionType;

import java.util.Date;

public class SiteActionProductPageEnter extends SiteAction {
    private String productName;
    public SiteActionProductPageEnter(Date date, String productName) {
        super(date);
        this.productName = productName;
    }
    @Override
    public String toString() {
        return SiteActionType.PRODUCTPAGEENTER + " " + date.getTime() + " " + productName + " ";
    }
}
