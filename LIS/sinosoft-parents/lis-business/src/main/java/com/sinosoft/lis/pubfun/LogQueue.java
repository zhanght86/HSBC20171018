package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import java.util.*;

public class LogQueue {
private static Logger logger = Logger.getLogger(LogQueue.class);
    private static LinkedList LogList = new LinkedList();
    public  synchronized static void putQueue(Object o) {
//        LogList.addLast(o);
        LogList.offer(o);
    }

    //使用removeFirst()方法，返回队列中第一个数据，然后将它从队列中删除
    public  synchronized static Object get() {
        return LogList.removeFirst();
    }

    public synchronized static int getCount()
    {
        return LogList.size();
    }
    
    public static boolean empty() {
        return LogList.isEmpty();
    }

    public static LinkedList getList(){
    	return LogList;
    }


    public static void main(String[] args) {
    }
}
