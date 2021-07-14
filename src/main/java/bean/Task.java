package bean;

import java.util.HashSet;

public class Task {
    private String id;
    private String longitude;
    private String latitude;
    private String time;
    private HashSet<Integer> skills;
    private int skillNumber;
    private double budget;

    public Task() {
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", time='" + time + '\'' +
                ", skills=" + skills +
                ", skillNumber=" + skillNumber +
                ", budget=" + budget +
                '}';
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

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public Task(String id, String longitude, String latitude, String time, HashSet<Integer> skills, int skillNumber, double budget) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.time = time;
        this.skills = skills;
        this.skillNumber = skillNumber;
        this.budget = budget;
    }
}
