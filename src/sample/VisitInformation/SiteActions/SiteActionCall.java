package sample.VisitInformation.SiteActions;

import sample.VisitInformation.SiteAction;
import sample.VisitInformation.SiteActionType;

import java.util.Date;


public class SiteActionCall extends SiteAction {
    public SiteActionCall(Date date) {
        super(date);
    }
    @Override
    public String toString() {
        return SiteActionType.CALL + " " + date.getTime() + " 1 ";
    }
}
