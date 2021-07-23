package bean;

import java.util.HashSet;

public class Task {
    private double id;
    private double longitude;
    private double latitude;
    private double time;
    private HashSet<Double> skills;
    private double skillNumber;
    private double budget;
    private double cost;

    public Task() {
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", time=" + time +
                ", skills=" + skills +
                ", skillNumber=" + skillNumber +
                ", budget=" + budget +
                ", cost=" + cost +
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

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Task(double id, double longitude, double latitude, double time, HashSet<Double> skills, double skillNumber, double budget, double cost) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.time = time;
        this.skills = skills;
        this.skillNumber = skillNumber;
        this.budget = budget;
        this.cost = cost;
    }
}
