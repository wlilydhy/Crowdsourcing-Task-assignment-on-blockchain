package util.greedy;

import bean.Task;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

import java.util.Iterator;

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

    /**
     *
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
     * 计算经纬度距离
     *
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
