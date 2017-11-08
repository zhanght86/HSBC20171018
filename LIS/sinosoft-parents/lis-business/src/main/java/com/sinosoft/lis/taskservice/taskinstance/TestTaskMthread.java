package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.lis.db.LDTaskServiceTestDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDTaskServiceTestSchema;
import com.sinosoft.service.CovBase;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:多线程测试
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author tongmeng
 * @version 6.5
 */

public class TestTaskMthread extends CovBase {
private static Logger logger = Logger.getLogger(TestTaskMthread.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	private VData mInputDataNew;

	/** 数据操作字符串 */
	private String mOperate;

	private HttpServletRequest httprequest;

	public TestTaskMthread() {
	}

	public static void main(String[] args) {

	}

	public void setObject(Object tObject) {
		// 多线程的外部参数条件
		mInputDataNew = (VData) tObject;
	}

	public void run() {
		TransferData mTransferData = new TransferData();
		mTransferData = mInputDataNew.getObjectByObjectName("TransferData", 0) == null ? null
				: (TransferData) mInputDataNew.getObjectByObjectName(
						"TransferData", 0);
		String tListNo = (String) mTransferData.getValueByName("TaskListNo");// 日志监控数据：任务编号
		GlobalInput tG = (GlobalInput) mTransferData
				.getValueByName("GlobalInput");// 日志监控数据：全局变量
		// logger.debug(tG.Operator);
		// 日志监控,过程监控
		// PubFun.logTrack (tG,"KeyNo"+tListNo,"开始处理KeyNo"+tListNo);//在执行任务前写入

		/*
		 * 测试功能：获得服务器IP，端口（这个重要），当前日期，时间，流水号，写入指定的临时表中（先删除，后写入），
		 * 调用任务监控（在pubfun中），获得从计划中传来的“其他参数”parameter.get
		 * 功能点：任务正常启动，正常停止，在指定的服务器节点运行（通过端口得知），任务监控功能，其他参数
		 * 
		 */
		// for(int i=0;i<100000;i++);
		// 获取业务数据
		String aServerIP = (String) mTransferData.getValueByName("ServerIP");// 业务数据逻辑：服务器IP，写入表
		String aServerPort = (String) mTransferData
				.getValueByName("ServerPort");// 业务数据逻辑：服务器端口Port，写入表
		String aServerType = (String) mTransferData
				.getValueByName("ServerType");// 业务数据逻辑：服务器类型，暂不写入表
		// logger.debug(tServerIP+tServerPort+tServerType);
		if (mTransferData != null) {
			String tActivityid = (String) mTransferData
					.getValueByName("ActivityID");
			LDTaskServiceTestSchema aLDTaskServiceTestSchema = new LDTaskServiceTestSchema();
			LDTaskServiceTestDB aLDTaskServiceTestDB = new LDTaskServiceTestDB();
			aLDTaskServiceTestSchema.setServerIP(aServerIP);
			aLDTaskServiceTestSchema.setServerPort(aServerPort);
			aLDTaskServiceTestSchema.setIDX(tActivityid);
			aLDTaskServiceTestSchema.setOperator(tG.Operator);
			aLDTaskServiceTestSchema.setMakeDate(PubFun.getCurrentDate());
			aLDTaskServiceTestSchema.setMakeTime(PubFun.getCurrentTime());
			aLDTaskServiceTestSchema.setModifyDate(PubFun.getCurrentDate());
			aLDTaskServiceTestSchema.setModifyTime(PubFun.getCurrentTime());
			aLDTaskServiceTestDB.setSchema(aLDTaskServiceTestSchema);
			aLDTaskServiceTestDB.delete();
			aLDTaskServiceTestDB.insert();
			// tLDTaskServiceTestSchema.s
			// int k = 0;// 为了使运行时间延长到30毫秒以上
			// for (int i = 0; i <= 100; i++) {
			// for (int j = 0; j <= 100; j++)
			// for (int h = 0; h <= 100; h++)
			// k++;
			// }
			logger.debug("##################Current Run:" + tActivityid
					+ "####");
			try {
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// this.close();
		// 日志监控,结果监控
		// PubFun.logResult(tG,"KeyNo"+tListNo,"处理结果是KeyNo"+tListNo);//在执行任务后写入
	}

	/**
	 * 传输数据的公共方法
	 */
	public VData getResult() {
		return mResult;
	}

}
