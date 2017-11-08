/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLAccidentDB;
import com.sinosoft.lis.db.LLAccidentSubDB;
import com.sinosoft.lis.db.LLCaseRelaDB;
import com.sinosoft.lis.db.LLReportDB;
import com.sinosoft.lis.db.LLReportReasonDB;
import com.sinosoft.lis.db.LLReportRelaDB;
import com.sinosoft.lis.db.LLSubReportDB;
import com.sinosoft.lis.db.LLReportLogDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLAccidentSchema;
import com.sinosoft.lis.schema.LLAccidentSubSchema;
import com.sinosoft.lis.schema.LLCaseRelaSchema;
import com.sinosoft.lis.schema.LLReportReasonSchema;
import com.sinosoft.lis.schema.LLReportRelaSchema;
import com.sinosoft.lis.schema.LLReportSchema;
import com.sinosoft.lis.schema.LLSubReportSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LLReportLogSchema;
import com.sinosoft.lis.vschema.LLReportReasonSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 报案登记业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zl
 * @修改记录：2005/6/9 增加BLF层，删除BL层的数据提交，数据处理统一为"DELETE&INSERT"
 * 
 * @version 1.0
 */
public class ClaimReportConfirmBL implements BusinessService{
private static Logger logger = Logger.getLogger(ClaimReportConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private String mOperater;
	private String flag;
	private String RptNo;
	private boolean mFlag = true;
	private MMap map = new MMap();


	private LLAccidentSchema mLLAccidentSchema = new LLAccidentSchema();
	private LLAccidentSubSchema mLLAccidentSubSchema = new LLAccidentSubSchema();
	private LLReportSchema mLLReportSchema = new LLReportSchema();
	private LLSubReportSchema mLLSubReportSchema = new LLSubReportSchema();
	private LLReportRelaSchema mLLReportRelaSchema = new LLReportRelaSchema();
	private LLCaseRelaSchema mLLCaseRelaSchema = new LLCaseRelaSchema();
	private LLReportReasonSchema mLLReportReasonSchema = new LLReportReasonSchema();
	private LLReportReasonSet mLLReportReasonSet = new LLReportReasonSet();
	private LLReportLogSchema mLLReportLogSchema = new LLReportLogSchema();

	private GlobalInput mG = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private boolean mIsReportExist;
	private boolean mIsAccExist;

	public ClaimReportConfirmBL() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------LLReportBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData())
			return false;
		logger.debug("----------LLReportBL after getInputData----------");

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------LLReportBL after checkInputData----------");
		
		// 进行业务处理
		if (!dealData(cOperate)) {
			return false;
		}
		logger.debug("----------LLReportBL after dealData----------");

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------LLReportBL after prepareOutputData----------");
		
		if (mOperate.equals("reportconfirm"))
		{
				PubSubmit tPubSubmit = new PubSubmit();
				if (!tPubSubmit.submitData(mInputData, "")) {
					// @@错误处理
					this.mErrors.copyAllErrors(tPubSubmit.mErrors);
					CError tError = new CError();
					tError.moduleName = "ClaimWorkFlowBL";
					tError.functionName = "submitData";
					tError.errorMessage = "数据提交失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
		}

		mInputData = null;
		return true;
	}

	public VData getResult() {
		mResult.clear();
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		
		logger.debug("--start getInputData()");
		// 获取页面报案信息
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);// 按类取值
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		RptNo = (String) mTransferData.getValueByName("RptNo");
		// 从输入数据中得到所有对象
		// 获得全局公共数据

		if (mG == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "AskUWRegisterAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mOperater = mG.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LLClaimReportAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(String cOperate) {
		
		boolean tReturn = false;
		// 立案时添加纪录或新增报案时
		if (cOperate.equals("reportconfirm")) {
			logger.debug("----------INSERT dealData----------");

				//新增个人报案轨迹表
			LLReportLogDB tLLReportLogDB = new LLReportLogDB();
			tLLReportLogDB.setRptNo(RptNo);
			tLLReportLogDB.getInfo();
			mLLReportLogSchema = tLLReportLogDB.getSchema();
			mLLReportLogSchema.setRptNo(tLLReportLogDB.getRptNo());
			mLLReportLogSchema.setMngCom(tLLReportLogDB.getMngCom());
			mLLReportLogSchema.setOperator(tLLReportLogDB.getOperator());
			mLLReportLogSchema.setMakeDate(tLLReportLogDB.getMakeDate());
			mLLReportLogSchema.setMakeTime(tLLReportLogDB.getMakeTime());
			mLLReportLogSchema.setModifyDate(CurrentDate);
			mLLReportLogSchema.setModifyTime(CurrentTime);
			mLLReportLogSchema.setRgtState(tLLReportLogDB.getRgtState()); //案件类型  11—普通案件 01—简易案件
			mLLReportLogSchema.setReportState("1"); //报案状态 0—报案  1—报案完成

			map.put(mLLReportLogSchema, "DELETE&INSERT");
				
				tReturn = true;
		}

		logger.debug("----------Service dealData BEGIN----------");
		
		
		/**
		 * -----------------------------------------------------------BEG
		 * 对于身故案件执行保单挂起 如果挂起失败也可以通过,统一在立案确认时卡住!
		 * -----------------------------------------------------------
		 */
		LLLpContHangUpBL tLLLpContHangUpBL = new LLLpContHangUpBL();
		if (!tLLLpContHangUpBL.submitData(mInputData, "")) {
			// @@错误处理
//			this.mErrors.copyAllErrors(tLLLpContHangUpBL.mErrors);
//			return false;
		} else {
			logger.debug("-----start Service getData from LLLpContHangUpBL");
			VData tempVData = new VData();
			MMap tMap = new MMap();
			tempVData = tLLLpContHangUpBL.getResult();
			tMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
			map.add(tMap);
			tReturn = true;
		}

	
		logger.debug(tReturn);
		return tReturn;
	}


	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(map);
	
			if (mOperate.equals("reportconfirm"))
			//只保存报案信息，不进行工作流处理		
    			{
					mInputData.clear();
					mInputData.add(map);
					return true;
    		   }	
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错");
			return false;
		}

		return true;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}
