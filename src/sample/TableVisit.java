package sample;

import javafx.beans.property.SimpleStringProperty;

/**
 * Класс, представляющий информацию о посещении в специальном формате
 */
public class TableVisit {
    /**Идентификатор посещения*/
    private final SimpleStringProperty ID;
    /**Сайт-источник*/
    private final SimpleStringProperty sourceSite;
    /**Тип источника*/
    private final SimpleStringProperty sourceType;
    /**Время перехода*/
    private final SimpleStringProperty date;
    /**Время перехода после показа баннера*/
    private final SimpleStringProperty timeAfterBannerDisplay;
    /**Действия на сайте*/
    private final SimpleStringProperty siteActions;

    public TableVisit(String ID, String sourceSite, String sourceType, String date, String timeAfterBannerDisplay, String siteActions) {
        this.ID = new SimpleStringProperty(ID);
        this.sourceSite = new SimpleStringProperty(sourceSite);
        this.sourceType = new SimpleStringProperty(sourceType);
        this.date = new SimpleStringProperty(date);
        this.timeAfterBannerDisplay = new SimpleStringProperty(timeAfterBannerDisplay);
        this.siteActions = new SimpleStringProperty(siteActions);
    }

    public String getID() {
        return ID.get();
    }

    public SimpleStringProperty IDProperty() {
        return ID;
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public String getSourceSite() {
        return sourceSite.get();
    }

    public SimpleStringProperty sourceSiteProperty() {
        return sourceSite;
    }

    public void setSourceSite(String sourceSite) {
        this.sourceSite.set(sourceSite);
    }

    public String getSourceType() {
        return sourceType.get();
    }

    public SimpleStringProperty sourceTypeProperty() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType.set(sourceType);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getTimeAfterBannerDisplay() {
        return timeAfterBannerDisplay.get();
    }

    public SimpleStringProperty timeAfterBannerDisplayProperty() {
        return timeAfterBannerDisplay;
    }

    public void setTimeAfterBannerDisplay(String timeAfterBannerDisplay) {
        this.timeAfterBannerDisplay.set(timeAfterBannerDisplay);
    }

    public String getSiteActions() {
        return siteActions.get();
    }

    public SimpleStringProperty siteActionsProperty() {
        return siteActions;
    }

    public void setSiteActions(String siteActions) {
        this.siteActions.set(siteActions);
    }
}
