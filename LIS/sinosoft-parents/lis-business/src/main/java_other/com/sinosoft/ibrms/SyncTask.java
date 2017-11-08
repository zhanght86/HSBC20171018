package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

public interface SyncTask extends Task {
       public void workDone(TaskContext context);
}
