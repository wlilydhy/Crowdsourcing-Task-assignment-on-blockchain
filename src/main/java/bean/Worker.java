package bean;

import java.util.HashSet;

public class Worker {
    private String id;
    private String longitude;
    private String latitude;
    private String time;
    private HashSet<Integer> skills;
    private int skillNumber;
    private double payoff;
    private int cluster;

    public Worker() {
    }

    public Worker(String id, String longitude, String latitude, String time, HashSet<Integer> skills, int skillNumber, double payoff, int cluster) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.time = time;
        this.skills = skills;
        this.skillNumber = skillNumber;
        this.payoff = payoff;
        this.cluster = cluster;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public HashSet<Integer> getSkills() {
        return skills;
    }

    public void setSkills(HashSet<Integer> skills) {
        this.skills = skills;
    }

    public int getSkillNumber() {
        return skillNumber;
    }

    public void setSkillNumber(int skillNumber) {
        this.skillNumber = skillNumber;
    }

    public double getPayoff() {
        return payoff;
    }

    public void setPayoff(double payoff) {
        this.payoff = payoff;
    }

    public int getCluster() {
        return cluster;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id='" + id + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", time='" + time + '\'' +
                ", skills=" + skills +
                ", skillNumber=" + skillNumber +
                ", payoff=" + payoff +
                ", cluster=" + cluster +
                '}';
    }
}
