package util.exactUtil;

import bean.State;
import bean.Task;
import bean.Worker;
import util.greedy.GreedyUtil;

import java.util.HashSet;
import java.util.Iterator;


public class ExactUtil {

    /**
     * 合并两个state
     * Attention!!! 合并的两个state不相等，也不存在包含关系
     * @param wState 当前工人的一个state
     * @param state states集合中的一个state
     * @return 如果返回为空，则说明这两个state要么相等，要么存在包含关系，否则返回合并后的state
     */
    public State mergeState(State wState, State state) {
        if ( isSameState(wState,state) ) {
            return null;
        }
        if ( isCoveredState(wState,state) ) {
            return null;
        }
        //此处的两个state集合必不相同，也不存在包含关系
        State newState = new State();
        //设置skills技能集合
        HashSet<Double> skills = new HashSet<>();
        skills.addAll(wState.getSkills());
        skills.addAll(state.getSkills());
        newState.setSkills(skills);
        //设置workers集合，此集合只包含一个worker
        HashSet<Worker> workers = new HashSet<>();
        workers.addAll(wState.getWorkers());
        workers.addAll(state.getWorkers());
        newState.setWorkers(workers);
        //设置cost
        double cost = wState.getCost() + state.getCost();
        newState.setCost(cost);
        return newState;
    }

    /**
     * 判断一个state是否包含另外一个state
     * 例如：state1的技能集合为：7，8 state2的技能集合为：7。这就算是包含关系
     * Attention!!! 此处的"包含"首先技能集合不能相等，如果相等就不算包含
     * @param state1
     * @param state2
     * @return
     */
    public boolean isCoveredState(State state1, State state2) {
        if ( isSameState(state1,state2) ) {
            return false;
        }
        boolean flag = false;
        Iterator<Double> iterator1 = state1.getSkills().iterator();
        while ( iterator1.hasNext() ) {
            Iterator<Double> iterator2 = state2.getSkills().iterator();
            while ( iterator2.hasNext() ) {
                if ( iterator1.next() == iterator2.next() ) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 判断两个state是否相等
     * Attention!!! 是否相等取决于技能集合是否相等
     * @param state1
     * @param state2
     * @return 相等返回true，否则返回false
     */
    public boolean isSameState( State state1, State state2 ) {
        if ( state1.getSkills().size() != state2.getSkills().size() ) {
            return false;
        }
        HashSet<Double> skillsNew1 = new HashSet<>();
        Iterator<Double> iterator1 = state1.getSkills().iterator();
        while ( iterator1.hasNext() ) {
            skillsNew1.add(iterator1.next());
        }
        skillsNew1.removeAll(state2.getSkills());
        if ( skillsNew1.size() == 0 ) {
            return true;
        }
        return false;
    }

    /**
     * 此方法用来分解一个工人的cover state
     * 例如w1技能为{e1,e2},价格为2.0
     * 因此分解后的的cover state为<{e1}, {w1}, 2.0>，<{e2}, {w1}, 2.0>，<{e1，e2}, {w1}, 2.0>
     * attention!!! states.size()有可能为0
     * @param worker 一个工人
     * @param tj 一个任务
     * @return 工人的state集合
     */
    public HashSet<State> getWorkerStates(Worker worker, Task tj) {
        GreedyUtil gUtil = new GreedyUtil();
        HashSet<State> states = new HashSet<>();

        //设置workers集合，此集合只包含一个worker
        HashSet<Worker> workers = new HashSet<>();
        workers.add(worker);
        //设置cost,一个工人的cost
        double wCost = gUtil.distance(tj,worker)/10000.0;

        //工人的技能数量不是1就是2，没有capacity限制
        for ( Double skill : worker.getSkills() ) {
            if ( gUtil.isNeededByTask(skill,tj) ) {
                State state = new State();
                //设置skills技能集合,此集合只包含一个skill
                HashSet<Double> skills = new HashSet<>();
                skills.add(skill);
                state.setSkills(skills);
                //设置workers集合
                state.setWorkers(workers);
                //设置cost
                state.setCost(wCost);
                //把state加入states集合
                states.add(state);
            }
        }
        //如果此工人的两个技能都被任务所需，则此工人有3种state
        //第3个state包含两个技能，正好是工人的技能集合
        if (states.size() ==2) {
            State state = new State();
            //设置skills技能集合
            HashSet<Double> skills = worker.getSkills();
            state.setSkills(skills);
            //设置workers集合
            state.setWorkers(workers);
            //设置cost
            state.setCost(wCost);
            //把state加入states集合
            states.add(state);
        }
        return states;
    }

}
