package util.greedy;

import bean.Task;
import bean.Worker;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class GreedyUtil {


    /**
     * 此方法用来判断某个技能是否是当前任务所需的
     * @param skill 某个技能
     * @param task 当前任务
     * @return 如果此技能是任务所需则返回true，否则返回false
     */
    public boolean isNeededByTask(double skill, Task task) {
        Iterator<Double> iteratorSkills = task.getSkills().iterator();
        while ( iteratorSkills.hasNext() ) {
            if (iteratorSkills.next() == skill){
                return true;
            }
        }
        return false;
    }

    //TODO:此方法还没有测试
    /**
     * first round，找出当前工人集合中效用最大一个的工人
     * @param workers
     * @param task
     * @param tjTeam
     * @return
     */
    public Worker argMax(ArrayList<Worker> workers, Task task, ArrayList<Worker> tjTeam) {
        double argMax = 0;
        Worker maxWorker = new Worker();
        for (Worker worker : workers) {
            int maxItem1 = maxItem1(tjTeam,task.getSkills());
            int maxItem2 = maxItem2(tjTeam,worker,task.getSkills());
            double value = (maxItem2 - maxItem1) / distance(task,worker);
            if ( value>argMax ) {
                argMax = value;
                maxWorker = worker;
            }
        }
        //System.out.println(argMax);
        //System.out.println(maxWorker);
        //System.out.println("---------------------");
        return maxWorker;
    }

    /**
     * 计算工人和任务之间的距离（m）
     * @param t 任务
     * @param w 工人
     * @return 距离（m）
     */
    public double distance(Task t, Worker w) {
        return calculateTheDistance(t.getLongitude(),t.getLatitude(),w.getLongitude(), w.getLatitude());
    }

    /**
     * 贪心算法的MAXITEM(g)
     * 用来计算当前团队中工人技能集合与任务技能集合的交集
     * @param tjTeam 当前组建的团队
     * @param taskSkills 任务技能集合
     * @return
     */
    public int maxItem1(ArrayList<Worker> tjTeam, HashSet<Double> taskSkills) {
        //此集合用来存放，team中工人的技能集合与任务技能集合的交集
        HashSet<Double> intersection = new HashSet<>();
        if (tjTeam.size()==0){
            return 0;
        }
        //遍历team中的每一个工人
        for (Worker w : tjTeam) {
            ArrayList<Double> wSkills = new ArrayList<>();
            Iterator<Double> iterator = w.getSkills().iterator();
            while (iterator.hasNext()) {
                wSkills.add(iterator.next().doubleValue());
            }
            //比较当前工人的技能和任务所需技能，如果工人技能是任务所需的，则将此技能加入交集intersection
            for (Double wSkill : wSkills) {
                for (Double tSkill : taskSkills) {
                    if (wSkill.doubleValue() == tSkill.doubleValue()) {
                        intersection.add(wSkill.doubleValue());
                    }
                }
            }
        }
        return intersection.size();
    }


    /**
     * 贪心算法 MAXITEM(g∪{w})
     * 用来计算当前团队中加入当前正在遍历的工人后的工人技能集合与任务技能集合的交集
     * @param tjTeam 当前组建的团队
     * @param worker 当前正在遍历的工人
     * @param taskSkills 任务技能集合
     * @return
     */
    public int maxItem2(ArrayList<Worker> tjTeam, Worker worker, HashSet<Double> taskSkills) {
        List<Worker> tjTeamNew = new ArrayList<>();
        for (Worker w : tjTeam) {
            tjTeamNew.add(w);
        }
        tjTeamNew.add(worker);
        //此集合用来存放，team中工人的技能集合与任务技能集合的交集
        HashSet<Double> intersection = new HashSet<>();
        //遍历team中的每一个工人
        for (Worker w : tjTeamNew) {
            ArrayList<Double> wSkills = new ArrayList<>();
            Iterator<Double> iterator = w.getSkills().iterator();
            while (iterator.hasNext()) {
                wSkills.add(iterator.next());
            }
            //比较当前工人的技能和任务所需技能，如果工人技能是任务所需的，则将此技能加入交集intersection
            for (Double ws : wSkills) {
                for (Double tSkill : taskSkills) {
                    if (ws.doubleValue() == tSkill.doubleValue()) {
                        intersection.add(ws.doubleValue());
                    }
                }
            }
        }
        return intersection.size();
    }

    /**
     * 用来判断当前team中的成员是否可以cover任务的所有技能
     * @param taskSkills 任务技能集合
     * @param tjTeam 当前组建的团队
     * @return true表示可以cover，false表示不行
     */
    public boolean isSkillsSatisfy(HashSet<Double> taskSkills, ArrayList<Worker> tjTeam) {
        if (tjTeam.size()==0) {
            return false;
        }
        //把taskSkills放进一个新的集合中，以便于做删除操作
        HashSet<Double> taskSkillsNew = new HashSet<>();
        Iterator<Double> iteratorOfTaskSkills = taskSkills.iterator();
        while ( iteratorOfTaskSkills.hasNext() ) {
            taskSkillsNew.add(iteratorOfTaskSkills.next());
        }
        //遍历team中的每一个工人，判断技能是否满足任务需求
        for (Worker w : tjTeam) {
            //把当前遍历的工人的技能放进数组wSkills中
            ArrayList<Double> wSkills = new ArrayList<>();
            Iterator<Double> iteratorOfWorkerSkills = w.getSkills().iterator();
            while (iteratorOfWorkerSkills.hasNext()) {
                wSkills.add(iteratorOfWorkerSkills.next());
            }
            for (Double wSkill : wSkills) {
                Iterator<Double> iterator = taskSkillsNew.iterator();
                while (iterator.hasNext()) {
                    Double temp=iterator.next();
                    if (wSkill.equals( temp)) {
                        iterator.remove();
                    }
                }
            }
        }
        //如果新的任务技能集合为空，则说明当前团队team中的工人满足任务需求
        if (taskSkillsNew.isEmpty()) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 计算两个经纬度点的距离（m）
     * @param longitudeFrom 起点经度
     * @param latitudeFrom  起点纬度
     * @param longitudeTo   终点经度
     * @param latitudeTo   终点纬度
     * @return
     */
    public static long calculateTheDistance(double longitudeFrom, double latitudeFrom, double longitudeTo, double latitudeTo) {
        GlobalCoordinates source = new GlobalCoordinates(latitudeFrom, longitudeFrom);
        GlobalCoordinates target = new GlobalCoordinates(latitudeTo, longitudeTo);
        Long distanceMeter = Math.round(getDistanceMeter(target, source, Ellipsoid.Sphere));
        return distanceMeter;
    }

    /**
     * @param gpsFrom
     * @param gpsTo
     * @param ellipsoid
     * @return
     */
    public static double getDistanceMeter(GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid) {
        // 创建GeodeticCalculator，调用计算方法，传入坐标系、经纬度用于计算距离
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);
        return geoCurve.getEllipsoidalDistance();
    }



}
