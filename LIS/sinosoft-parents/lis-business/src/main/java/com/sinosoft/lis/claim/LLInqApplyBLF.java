package com.sinosoft.lis.claim;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.claim.ClaimWorkFlowBL;
import com.sinosoft.workflowengine.ActivityOperator;
import com.sinosoft.workflowengine.ActivityOperatorNode;

public class LLInqApplyBLF {
	private static Logger logger = Logger.getLogger(ClaimWorkFlowBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往前台提交数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mG = new GlobalInput();

	private TransferData mTransferData = new TransferData();
	/** 数据操作字符串 */
	private String mOperate;
	// 提交数据打包类
	private MMap map = new MMap();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private String ActivityID;
	private String ProcessID;
	private String Version;

	public LLInqApplyBLF() {
	}
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("----------BLF after getInputData----------");
		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------BLF after checkInputData----------");
		// 进行业务处理,得到业务处理返回值
		if (!dealData()) {
			return false;
		}
		logger.debug("----------BLF after dealData----------");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------BLF after prepareOutputData----------");

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLInqApplyBLF";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("--start BLF getInputData()");

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);// 按类取值
		String BusiType = (String) mTransferData
				.getValueByName("BusiType");
		ActivityID = (String)mTransferData.getValueByName("ActivityID");
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql("select processid from LWCorresponding where busitype='"
				+ "?busitype?" + "'");
		sqlbv.put("busitype", BusiType);
		ProcessID = tExeSQL
				.getOneValue(sqlbv);
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql("select version from LWCorresponding where busitype='"
				+ "?busitype?" + "'");
		sqlbv1.put("busitype", BusiType);
		Version = tExeSQL
				.getOneValue(sqlbv1);
		return true;
	}

	/**
	 * 校验传入的信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		return true;
	}

	/**
	 * 创建理赔调查报告任务节点,同时调用业务处理类保存业务数据 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("------Goto dealData()------");
		boolean tReturn = false;
		VData cInputData = new VData();
		VData tVData = new VData();

		// 调用业务逻辑处理类，返回处理完数据
		LLInqApplyBL tLLInqApplyBL = new LLInqApplyBL();
		if (tLLInqApplyBL.submitData(mInputData, "INSERT")) {
			VData tempVData1 = new VData();
			tempVData1 = tLLInqApplyBL.getResult();
			cInputData.add(tempVData1);

			// 首先生成节点，由于第一次创建，没有触发服务类，在下面直接调用业务处理类
			ActivityOperatorNode tstartActivityOperatorNode = new ActivityOperatorNode(
					ActivityID, ProcessID, Version);
			String tMissionID = PubFun1.CreateMaxNo("MissionID", 20);
			String tSubMissionID = "1";
			try {
				// 产生报案确认节点
				if (tstartActivityOperatorNode.Create(tMissionID, tSubMissionID,
						mInputData)) {
					VData tempVData2 = new VData();
					tempVData2 = tstartActivityOperatorNode.getResult();
					cInputData.add(tempVData2);
					tempVData2 = null;
					tReturn = true;
				} else { // @@错误处理
					this.mErrors.copyAllErrors(tstartActivityOperatorNode.mErrors);
					return false;
				}
			} catch (Exception ex) {
				// @@错误处理
				this.mErrors.copyAllErrors(tstartActivityOperatorNode.mErrors);
				CError tError = new CError();
				tError.moduleName = "LLInqApplyBLF";
				tError.functionName = "submitData";
				tError.errorMessage = "工作流引擎工作出现异常!";
				this.mErrors.addOneError(tError);
				tReturn = false;
			}

			tempVData1 = null;
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLInqApplyBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLInqApplyBLF";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			tReturn = false;
		}
		mInputData.clear();
		mInputData = cInputData;
		logger.debug("------End dealData()------");
		return tReturn;
	}

	public VData getmResult() {
		return mResult;
	}
	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			// 把所有需要提交的map融合到一个map，统一使用pubsubmit提交
			for (int i = 0; i < mInputData.size(); i++) {
				VData tData = new VData();
				tData = (VData) mInputData.get(i);
				MMap tmap = new MMap();
				tmap = (MMap) tData.getObjectByObjectName("MMap", 0);
				map.add(tmap);
			}
			mInputData.clear();
			mInputData.add(map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
