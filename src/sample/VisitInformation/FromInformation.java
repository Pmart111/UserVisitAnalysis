package sample.VisitInformation;


public class FromInformation {
    private String siteName;
    private FromType fromType;
    public FromInformation() {}
    public FromInformation(String string) {
        String[] data = string.split(" ");
        siteName = data[0];
        fromType = FromType.valueOf(data[1]);
    }
    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }


    public FromType getFromType() {
        return fromType;
    }

    public void setFromType(FromType fromType) {
        this.fromType = fromType;
    }

    @Override
    public String toString() {
        return siteName + " " + fromType;
    }
}
