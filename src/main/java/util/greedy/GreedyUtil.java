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
     * 计算一个团队的熵
     * @param tjTeam 形成的团队
     * @return 熵值
     */
    public double entropy(ArrayList<Worker> tjTeam) {
        double entropy = 0.0; //熵
        //先判断，如果team中只有一个人，则熵最大为1.0986122886681096
        int size = tjTeam.size(); //当前团队总人数
        if (size==1) {
            entropy = 1.0986122886681096;
            return entropy;
        }
        int[] clusterNum = new int[4]; //用来保存当前团队中每个cluster的人数
        //计算每个cluster中的人数
        for (Worker w : tjTeam) {
            if (w.getCluster() == 1.0) {
                clusterNum[1]++;
            }else if (w.getCluster() == 2.0) {
                clusterNum[2]++;
            }else {
                clusterNum[3]++;
            }
        }
        //计算熵
        for (int i = 1; i < 4; i++) {
            if (clusterNum[i] != 0) {
                double pk = Double.valueOf(clusterNum[i])/Double.valueOf(size);
                entropy -= (pk)*Math.log(pk);
            }
        }
        return entropy;
    }

    /**
     * 得到任务tj的有效工人：约束1：工人技能是任务所需的；约束2：工人在任务范围内
     * @param tj 时间片p下的任务tj
     * @param Wp 时间片p下的可用工人
     * @return 任务tj的有效工人
     */
    public ArrayList<Worker> getTjWorkers(Task tj, ArrayList<Worker> Wp) {
        ArrayList<Worker> tjWorkers = new ArrayList<>();
        for (Worker wi : Wp) {
            Iterator<Double> iSkills = wi.getSkills().iterator();
            while (iSkills.hasNext()) {
                //约束1：工人技能是任务所需的
                if (isNeededByTask(iSkills.next(), tj)) {
                    //约束2：工人在任务范围内
                    //先计算工人和任务的距离
                    long distance = GreedyUtil.calculateTheDistance(wi.getLongitude(), wi.getLatitude(), tj.getLongitude(), tj.getLatitude());
                    //System.out.println(distance);
                    if (distance < 8200000) {
                        tjWorkers.add(wi);
                    }
                    //tjWorkers.add(wi);
                }
            }
        }
        return tjWorkers;
    }

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

    /**
     * 为一个任务组成团队，
     * Attention！此团队不一定可以满足任务的技能要求，有效工人全部遍历完就结束组队
     * @param tjWorkers 任务tj的有效工人
     * @param tj 当前任务
     * @return tj的团队
     */
    public ArrayList<Worker> formTeam(ArrayList<Worker> tjWorkers, Task tj) {
        //先形成团队，能招几个人算几个人，当工人们tjWorkers全部遍历完，或者工人能够完成任务时break
        // 最后在外部判断此团队是否可以满足任务要求
        ArrayList<Worker> tjTeam = new ArrayList<>();
        for (Worker tjW : tjWorkers) {
            if (isSkillsSatisfy(tj.getSkills(),tjTeam)) {
                break;
            }
            Worker maxWorker = new Worker();
            maxWorker = argMax(tjWorkers,tj,tjTeam);
            if (maxWorker.getId() != 0.0) {
                tjTeam.add(maxWorker);
            }
        }
        return tjTeam;
    }

    /**
     * 第一轮，为当前任务tj找出性价比最高的工人
     * @param tjWorkers 任务tj的有效工人
     * @param tj 任务tj
     * @param tjTeam 任务tj的团队
     * @return 性价比最高的工人
     */
    public Worker argMax(ArrayList<Worker> tjWorkers, Task tj, ArrayList<Worker> tjTeam) {
        double argMax = 0;
        Worker maxWorker = new Worker();
        for (Worker worker : tjWorkers) {
            int maxItem1 = maxItem1(tjTeam,tj.getSkills());
            int maxItem2 = maxItem2(tjTeam,worker,tj.getSkills());
            double value = (maxItem2 - maxItem1) / distance(tj,worker);
            if ( value>argMax ) {
                argMax = value;
                maxWorker = worker;
            }
        }
        /*System.out.println(argMax);
        System.out.println(maxWorker);
        System.out.println("---------------------");*/
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
