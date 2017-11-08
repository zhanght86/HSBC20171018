package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.taskservice.TaskServiceRssUI;

/**调用TaskServiceRssUI类，定时生成当天的RSS文件
 * @author sinosoft
 *
 */
public class TaskServiceRSSGenerate extends TaskThread {
private static Logger logger = Logger.getLogger(TaskServiceRSSGenerate.class);

	/** 调用TaskServiceRssUI类，定时生成当天的RSS文件
	 * @see com.sinosoft.lis.taskservice.TaskThread#dealMain()
	 */
	@Override
	public boolean dealMain() {
		// TODO Auto-generated method stub
		TaskServiceRssUI tTaskServiceRssUI = new TaskServiceRssUI();
		if (!tTaskServiceRssUI.submitData(null, "GenerateRSS")) {
			return false;
		} else {
			return true;
		}
	}

}
