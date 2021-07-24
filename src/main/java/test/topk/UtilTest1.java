package test.topk;

import bean.State;
import util.exactUtil.ExactUtil;

import java.util.HashSet;
import java.util.Set;

public class UtilTest1 {
    public static void main(String[] args) {

        ExactUtil eUtil = new ExactUtil();

        State state1 = new State();
        HashSet<Double> skills1 = new HashSet<>();
        skills1.add(1.0);
        skills1.add(2.0);
        //skills1.add(3.0);
        state1.setSkills(skills1);

        State state2 = new State();
        HashSet<Double> skills2 = new HashSet<>();
        //skills2.add(1.0);
       // skills2.add(2.0);
        skills2.add(3.0);
        //skills2.add(4.0);
        state2.setSkills(skills2);

        /*boolean sameState = eUtil.isSameState(state1, state2);
        System.out.println(sameState);*/

        /*boolean coveredState = eUtil.isCoveredState(state1, state2);
        System.out.println(coveredState);*/

        State state = eUtil.mergeState(state1, state2);
        System.out.println(state);


    }
}
