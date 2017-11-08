package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;


import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.utility.CErrors;
import com.sinosoft.lis.certify.AuotCertifyImportDestroy;


public class AutoCertifyImportDestroyTask extends TaskThread
{
private static Logger logger = Logger.getLogger(AutoCertifyImportDestroyTask.class);


	public CErrors mErrors = new CErrors();

	  public AutoCertifyImportDestroyTask() {
	  }

	  public boolean dealMain() {
		/* 业务处理逻辑 */
		logger.debug("AutoCertifyImportDestroyTask.... "+PubFun.getCurrentDate() +" "+PubFun.getCurrentTime());
		try {
			AuotCertifyImportDestroy mAuotCertifyImportDestroy = new AuotCertifyImportDestroy();
			if(mAuotCertifyImportDestroy.submitData())
			{
				logger.debug("fail------"+mAuotCertifyImportDestroy.mErrors.getFirstError());
				return false;
			}

			
			
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		logger.debug("End AutoCertifyImportDestroyTask .... "+PubFun.getCurrentDate() +" "+PubFun.getCurrentTime());
		return true;
	}

	  public static void main(String[] args) {
		  AutoCertifyImportDestroyTask tAutoActuaryTask= new AutoCertifyImportDestroyTask();
		  tAutoActuaryTask.dealMain();
	  }

}
