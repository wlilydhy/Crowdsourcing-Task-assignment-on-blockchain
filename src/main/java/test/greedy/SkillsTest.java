package test.greedy;

import bean.Worker;
import util.GreedyUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class SkillsTest {
    public static void main(String[] args) throws IOException {
        //设置两个工人List
        Worker w1 = new Worker();
        HashSet<Double> s1 = new HashSet<>();
        s1.add(1.0);
        //s1.add(3.0);
        w1.setSkills(s1);
        Worker w2 = new Worker();
        HashSet<Double> s2 = new HashSet<>();
        //s2.add(2.0);
        //s2.add(4.0);
        w2.setSkills(s2);
        //新worker3
        Worker w3 = new Worker();
        HashSet<Double> s3 = new HashSet<>();
        s3.add(2.0);
        s3.add(3.0);
        w3.setSkills(s3);
        //团队包含w1，w2
        ArrayList<Worker> tjTeam = new ArrayList<>();
        tjTeam.add(w1);
        tjTeam.add(w2);
        //设置任务技能集合
        HashSet<Double> ts = new HashSet<>();
        ts.add(1.0);
        ts.add(2.0);
        ts.add(3.0);

        GreedyUtil gUtil = new GreedyUtil();

        /*//测试 isSkillsSatisfy
        System.out.println(gUtil.isSkillsSatisfy(ts,tjTeam));*/

        /*//测试 maxItem1
        int num = 0;
        num = gUtil.maxItem1(tjTeam, ts);
        System.out.println(num);*/

        //测试 maxItem2
        int num = 0;
        num = gUtil.maxItem2(tjTeam,w3, ts);
        System.out.println(num);


    }
}
