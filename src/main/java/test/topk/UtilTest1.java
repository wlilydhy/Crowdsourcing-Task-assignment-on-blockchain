package test.topk;

import bean.State;
import bean.Task;
import bean.Worker;
import util.ExactUtil;

import java.util.ArrayList;
import java.util.HashSet;

public class UtilTest1 {
    public static void main(String[] args) {

        ExactUtil eUtil = new ExactUtil();

        //state1
        State state1 = new State();
        //工人
        Worker w1 = new Worker();
        Worker w2 = new Worker();
        Worker w3 = new Worker();
        HashSet<Worker> ws1 = new HashSet<>();
        ws1.add(w1);
        ws1.add(w2);
        ws1.add(w3);
        state1.setWorkers(ws1);
        //技能
        HashSet<Double> skills1 = new HashSet<>();
        skills1.add(1.0);
        skills1.add(2.0);
        //skills1.add(3.0);
        state1.setSkills(skills1);

        //state2
        State state2 = new State();
        //工人
        Worker w4 = new Worker();
        Worker w5 = new Worker();
        Worker w6 = new Worker();
        HashSet<Worker> ws2 = new HashSet<>();
        ws2.add(w4);
        ws2.add(w5);
        ws2.add(w6);
        state2.setWorkers(ws2);
        //技能
        HashSet<Double> skills2 = new HashSet<>();
        skills2.add(1.0);
        skills2.add(2.0);
        skills2.add(3.0);
        //skills2.add(4.0);
        state2.setSkills(skills2);

        HashSet<State> states = new HashSet<>();
        states.add(state1);
        states.add(state2);


        Task tj = new Task();
        HashSet<Double> tjSkills = new HashSet<>();
        tjSkills.add(1.0);
        tjSkills.add(2.0);
        tjSkills.add(3.0);
        tj.setSkills(tjSkills);

        /*boolean sameState = eUtil.isSameState(state1, state2);
        System.out.println(sameState);*/

        /*boolean coveredState = eUtil.isCoveredState(state1, state2);
        System.out.println(coveredState);*/

        /*State state = eUtil.mergeState(state1, state2);
        System.out.println(state);*/

        /*boolean statesOk = eUtil.isStatesOk(states, tj);
        System.out.println(statesOk);*/

        ArrayList<Worker> workers = eUtil.formTeam(states, tj);
        for (Worker w : workers) {
            System.out.println(w);
        }


    }
}
