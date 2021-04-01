package JCovReader;

public class Case {
    private String region, state, date;
    private int regionId;
    private long totalInfected, newlyInfected;

    public Case(){}

    public String getRegion() {
        return region;
    }

    public String getDate() {
        return date;
    }

    public long getTotalInfected() {
        return totalInfected;
    }

    public long getNewlyInfected() {
        return newlyInfected;
    }

    public int getRegionId() {
        return regionId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTotalInfected(long totalInfected) {
        this.totalInfected = totalInfected;
    }

    public void setNewlyInfected(long newlyInfected) {
        this.newlyInfected = newlyInfected;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    @Override
    public String toString() {
        String s="";
        s+="Region: " + region + "(" + regionId + ") State: " + state + "\n";
        s+="Date: " + date + "\n";
        s+="Currently infected: " + totalInfected + "\n";
        s+="New infected as of now: " + newlyInfected + "\n";
        return s;
    }
}
