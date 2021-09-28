package me.test.demarcationqueue.use;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class DemarcationFlag {
    private static final DemarcationFlag instance = new DemarcationFlag();
    private AtomicInteger highTaskRunningNum = new AtomicInteger(0);
    private AtomicInteger normalTaskRunningNum = new AtomicInteger(0);
    private AtomicBoolean ccaTaskRunning = new AtomicBoolean(false);



    public static DemarcationFlag getInstance() {
        return instance;
    }

    public void updateHighRunningNum(int num) {
        highTaskRunningNum.getAndUpdate(it -> it + num);
    }

    public int getHighRunningNum(){
        return highTaskRunningNum.get();
    }

    public void updateNormalRunningNum(int num) {
        normalTaskRunningNum.getAndUpdate(it -> it + num);
    }

    public void updateCcaRunning(boolean flag) {
        ccaTaskRunning.getAndSet(flag);
    }
}
