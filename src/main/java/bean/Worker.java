package bean;

import java.util.HashSet;

public class Worker {
    private double id;
    private double longitude;
    private double latitude;
    private double time;
    private HashSet<Double> skills;
    private double skillNumber;
    private double payoff;
    private double cluster;

    public Worker() {
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id='" + id + '\'' +
                /*", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", time='" + time + '\'' +*/
                ", skills=" + skills +
                /*", skillNumber=" + skillNumber +
                ", payoff=" + payoff +
                ", cluster=" + cluster +*/
                '}';
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public HashSet<Double> getSkills() {
        return skills;
    }

    public void setSkills(HashSet<Double> skills) {
        this.skills = skills;
    }

    public double getSkillNumber() {
        return skillNumber;
    }

    public void setSkillNumber(double skillNumber) {
        this.skillNumber = skillNumber;
    }

    public double getPayoff() {
        return payoff;
    }

    public void setPayoff(double payoff) {
        this.payoff = payoff;
    }

    public double getCluster() {
        return cluster;
    }

    public void setCluster(double cluster) {
        this.cluster = cluster;
    }

    public Worker(double id, double longitude, double latitude, double time, HashSet<Double> skills, double skillNumber, double payoff, double cluster) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.time = time;
        this.skills = skills;
        this.skillNumber = skillNumber;
        this.payoff = payoff;
        this.cluster = cluster;
    }
}
