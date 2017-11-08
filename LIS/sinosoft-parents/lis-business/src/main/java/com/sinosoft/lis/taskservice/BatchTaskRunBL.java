

package com.sinosoft.lis.taskservice;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.db.LDTaskRunLogDB;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.TransferData;




public class BatchTaskRunBL {

	private static Map FlagMap = new HashMap();

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后台传输数据的容器 */
	private VData mOutputData = new VData();

	/** 往前台传输数据的容器 */
	private VData mResult = new VData();

	/** 公共参数 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperate;

	private String TaskCode = "";

	private String CurrentDate = "";

	/***/
	private MMap map = new MMap();

	private LDTaskRunLogDB tLDTaskRunLogDB = new LDTaskRunLogDB();

	public BatchTaskRunBL() {
	}

	public VData getResult() {
		return this.mResult;
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 得到外部传入的数据,将数据备份到本类中
		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData mInputData) {

		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
			mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData", 0);
			TaskCode = (String) mTransferData.getValueByName("TaskCode");
			CurrentDate = (String) mTransferData.getValueByName("ExeDate");

			if (mGlobalInput == null || mTransferData == null || TaskCode == null || CurrentDate == null) {
				CError.buildErr(this, "批处理未开始执行,原因是：获取传入数据失败!");
				return false;
			}

			if ("1".equals((String) FlagMap.get(TaskCode))) {
				CError.buildErr(this, "批处理未开始执行,原因是：上次业务逻辑尚未结束！");
				return false;
			}
		} catch (Exception ex) {
			CError.buildErr(this, "批处理未开始执行,原因是：获取传入数据失败!");
			return false;
		}

		return true;
	}

	// 校验通过返回true, 否则返回false
	private boolean checkData() {
		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData() {

		String SerialNo = PubFun1.CreateMaxNo("SERIALNO", 10);
		String ServerInfo = "";

		InetAddress ServerIPaddress = null;
		try {
			ServerIPaddress = InetAddress.getLocalHost();
			ServerInfo = ServerIPaddress.getHostAddress();
		} catch (Exception ex) {
		}

		TaskThread tTaskThread;
		try {
			String tTaskClass = new ExeSQL().getOneValue("select taskclass from ldtask where taskcode = '" + TaskCode + "'");
			tTaskThread = (TaskThread) Class.forName("com.sinosoft.lis.taskservice.taskinstance." + tTaskClass).newInstance();
		} catch (Exception e) {
			CError.buildErr(this, "批处理未开始执行,原因是：没有找到该任务号对应的批处理");
			return false;
		}

		// 开始执行批处理
		tLDTaskRunLogDB.setSerialNo(SerialNo);
		tLDTaskRunLogDB.setTaskCode(TaskCode);
		tLDTaskRunLogDB.setTaskPlanCode("000000");
		tLDTaskRunLogDB.setServerInfo(ServerInfo);
		tLDTaskRunLogDB.setExecuteDate(PubFun.getCurrentDate());
		tLDTaskRunLogDB.setExecuteTime(PubFun.getCurrentTime());
//		tLDTaskRunLogDB.setBussDate(CurrentDate);

		FlagMap.put(TaskCode, "1");
		try {
			if (tTaskThread.dealMain(CurrentDate)) {
				tLDTaskRunLogDB.setFinishDate(PubFun.getCurrentDate());
				tLDTaskRunLogDB.setFinishTime(PubFun.getCurrentTime());
				tLDTaskRunLogDB.setExecuteState("2");
				tLDTaskRunLogDB.setExecuteResult("业务逻辑处理成功结束");
				tLDTaskRunLogDB.insert();
			} else {
				tLDTaskRunLogDB.setFinishDate(PubFun.getCurrentDate());
				tLDTaskRunLogDB.setFinishTime(PubFun.getCurrentTime());
				tLDTaskRunLogDB.setExecuteState("3");
				tLDTaskRunLogDB.setExecuteResult("业务逻辑处理失败");
				tLDTaskRunLogDB.insert();
			}
		} catch (Exception e) {
			e.printStackTrace();
			tLDTaskRunLogDB.setFinishDate(PubFun.getCurrentDate());
			tLDTaskRunLogDB.setFinishTime(PubFun.getCurrentTime());
			tLDTaskRunLogDB.setExecuteState("3");
			tLDTaskRunLogDB.setExecuteResult("业务逻辑处理失败");
			tLDTaskRunLogDB.insert();
		}
		changeFlag();


		return true;
	}

	private boolean prepareOutputData() {
		return true;
	}

	private void changeFlag() {
		FlagMap.remove(TaskCode);
		FlagMap.put(TaskCode, "0");
		System.out.println("FlagTransferData.getValueByName(TaskCode):" + FlagMap.get(TaskCode));
	}


}

