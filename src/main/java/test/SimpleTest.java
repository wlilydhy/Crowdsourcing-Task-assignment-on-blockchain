package test;

import bean.Worker;
import util.framework.WorkersP;

import java.io.IOException;
import java.util.ArrayList;

public class SimpleTest {
    public static void main(String[] args) throws IOException {
        //1. 检索第一个时间片内的工人给Wp
        WorkersP wl = new WorkersP();
        ArrayList<Worker> Wp = wl.getWorkersP(1615910400,600);
        System.out.println(Wp.size());
        System.out.println(Wp.get(0));
    }
}
