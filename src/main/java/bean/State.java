package bean;

import java.util.HashSet;

public class State {
    private HashSet<Double> skills;
    private HashSet<Worker> workers;
    private double cost;

    public State() {
    }

    @Override
    public String toString() {
        return "State{" +
                "skills=" + skills +
                ", workers=" + workers +
                ", cost=" + cost +
                '}';
    }

    public HashSet<Double> getSkills() {
        return skills;
    }

    public void setSkills(HashSet<Double> skills) {
        this.skills = skills;
    }

    public HashSet<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(HashSet<Worker> workers) {
        this.workers = workers;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public State(HashSet<Double> skills, HashSet<Worker> workers, double cost) {
        this.skills = skills;
        this.workers = workers;
        this.cost = cost;
    }
}
