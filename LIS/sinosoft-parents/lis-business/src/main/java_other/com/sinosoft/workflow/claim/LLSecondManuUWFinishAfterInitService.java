/**
 * <p>Title: 理赔人工核保 “核保完成”服务类 </p>
 * <p>Description: </p>
 * <p>Company: SinoSoft</p>
 * @author yuejw
 */

package com.sinosoft.workflow.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLCUWBatchDB;
import com.sinosoft.lis.db.LLCUWMasterDB;
import com.sinosoft.lis.db.LLClaimDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LLCUWMasterSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LLCUWBatchSet;
import com.sinosoft.lis.vschema.LLCUWMasterSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

public class LLSecondManuUWFinishAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(LLSecondManuUWFinishAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 提交数据容器 */
	private MMap mmap = new MMap();
	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();
	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;
	private String mOperate;
	private String mTransact;
	private String mMissionID;
	private String mSubMissionID;
	private String mCaseno;
	private String mBatNo;
	private String mUWFlag;
	private String mUWPrtSeq;
	private String mUWIdea2;

	private LLCUWBatchSet mLLCUWBatchSet = new LLCUWBatchSet();
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();

	public LLSecondManuUWFinishAfterInitService() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		// 校验传入数据
		if (!checkData()) {
			return false;
		}
		logger.debug("Start  dealData...");
		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("dealData successful!");
		// 为工作流下一节点属性字段准备数据
		if (!prepareTransferData()) {
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("Start  Submit...");

		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		// 检查该任务下的所有赔案是否都下了核保结论
		// 检验险种结论是否完成，根据（分案号--批次号---赔案相关标志），从个人理赔合同批次表---LLCUWBatch 取出该任务下所有 合同号
		// 然后以“分案号 and 批次号”查询 个人合同理陪核保最近结果表--- LLCUWMaster 是否存在记录
		// 如果理赔结论是除“0-返回理赔”之外的结论，则判断是否所有的合同都已经下了核保结论
		// 如果理赔结论是“0-返回理赔”，则判断是否已经有合同下过核保结论了，如果有，则返回false
		LLCUWBatchDB tLLCUWBatchDB = new LLCUWBatchDB();
		tLLCUWBatchDB.setCaseNo(mCaseno);
		tLLCUWBatchDB.setBatNo(mBatNo);
		// tLLCUWBatchDB.setClaimRelFlag(mClaimRelFlag);
		mLLCUWBatchSet.set(tLLCUWBatchDB.query());
		if(!mUWFlag.equals("0")){
			logger.debug("-----------该任务下合同数目---" + mLLCUWBatchSet.size());
	
			String tstate = "complete"; // 险种结论全部下完标志----自定义
			String struncomplete = "该任务下未下核保结论的合同号码:";
			for (int i = 1; i <= mLLCUWBatchSet.size(); i++) {
				LLCUWMasterDB tLLCUWMasterDB = new LLCUWMasterDB();
				LLCUWMasterSet tLLCUWMasterSet = new LLCUWMasterSet();
				LLCUWMasterSchema tLLCUWMasterSchema = new LLCUWMasterSchema();
				String tCheckSql="select * from llcuwmaster where caseno='"+"?mCaseno?"
									+"' and batno='"+"?mBatNo?"
									+"' and contno='"+"?contno?"
									+"' and passflag='5'";
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(tCheckSql);
				sqlbv.put("mCaseno", mCaseno);
				sqlbv.put("mBatNo", mBatNo);
				sqlbv.put("contno", mLLCUWBatchSet.get(i).getContNo());
//				tLLCUWMasterDB.setCaseNo(mCaseno);
//				tLLCUWMasterDB.setBatNo(mBatNo);
//				tLLCUWMasterDB.setContNo(mLLCUWBatchSet.get(i).getContNo());
	
				tLLCUWMasterSet.set(tLLCUWMasterDB.executeQuery(sqlbv));
				String strcont = "";
				// 判断是否存在记录，存在说明该合同已经下结论，不存在该合同已未下结论
	
				if (tLLCUWMasterSet.size() > 0) {
					strcont = strcont + " / " + mLLCUWBatchSet.get(i).getContNo();
					tstate = "uncomplete";
				}
				struncomplete = struncomplete + strcont;
			}
	
			if (tstate.equals("uncomplete")) {
				// 如果未完成则返回信息
				logger.debug("************该任务下还有合同未下核保结论************");
				logger.debug(struncomplete);
				CError tError = new CError();
				tError.moduleName = "";
				tError.functionName = "dealtData";
				tError.errorMessage = struncomplete;
				this.mErrors.addOneError(tError);
				return false;
			}
			logger.debug("************该任务下所有合同已下核保结论************");
		}else{
//			LLCUWBatchDB tLLCUWBatchDB = new LLCUWBatchDB();
//			tLLCUWBatchDB.setCaseNo(mCaseno);
//			tLLCUWBatchDB.setBatNo(mBatNo);
//			// tLLCUWBatchDB.setClaimRelFlag(mClaimRelFlag);
//			mLLCUWBatchSet.set(tLLCUWBatchDB.query());
//			logger.debug("-----------该任务下合同数目---" + mLLCUWBatchSet.size());
//	
//			String tstate = "complete"; // 险种结论全部下完标志----自定义
//			String struncomplete = "该任务下未下核保结论的合同号码:";
//			for (int i = 1; i <= mLLCUWBatchSet.size(); i++) {
//				LLCUWMasterDB tLLCUWMasterDB = new LLCUWMasterDB();
//				LLCUWMasterSet tLLCUWMasterSet = new LLCUWMasterSet();
//				LLCUWMasterSchema tLLCUWMasterSchema = new LLCUWMasterSchema();
////				tLLCUWMasterDB.setCaseNo(mCaseno);
////				tLLCUWMasterDB.setBatNo(mBatNo);
////				tLLCUWMasterDB.setContNo(mLLCUWBatchSet.get(i).getContNo());
//				String tCheckSql="select * from llcuwmaster where caseno='"+mCaseno
//									+"' and batno='"+mBatNo
//									+"' and contno='"+mLLCUWBatchSet.get(i).getContNo()
//									+"' and passflag<>'5'";
//
//				tLLCUWMasterSet.set(tLLCUWMasterDB.executeQuery(tCheckSql));
//	
//				tLLCUWMasterSet.set(tLLCUWMasterDB.query());
//				String strcont = "";
//				// 判断是否存在记录，存在说明该合同已经下结论，不存在该合同已未下结论
//	
//				if (tLLCUWMasterSet.size() == 0) {
//					strcont = strcont + " / " + mLLCUWBatchSet.get(i).getContNo();
//					tstate = "uncomplete";
//				}
//				struncomplete = struncomplete + strcont;
//			}
//	
//			if (tstate.equals("uncomplete")) {
//				// 如果未完成则返回信息
//				logger.debug("************该任务下已经有合同下过核保结论************");
//				logger.debug(struncomplete);
//				CError tError = new CError();
//				tError.moduleName = "";
//				tError.functionName = "dealtData";
//				tError.errorMessage = struncomplete;
//				this.mErrors.addOneError(tError);
//				return false;
//			}
//			logger.debug("************该任务下所有合同都未下核保结论************");
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		// 获取页面信息<个人合同理陪核保最近结果表>

		mTransact = (String) cInputData.getObjectByObjectName("String", 0);
		mInputData = cInputData;

		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LLSecondManuUWFinishAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LLSecondManuUWFinishAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operater失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		mUWIdea2 = (String) mTransferData.getValueByName("UWIdea2");
		if (mMissionID == null || mSubMissionID == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLSecondManuUWFinishAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mCaseno = (String) mTransferData.getValueByName("CaseNo");
		if (mCaseno == null || "".equals(mCaseno)) {
			CError.buildErr(this, "前台传输业务数据mCaseno失败！");
			return false;
		}
		mBatNo = (String) mTransferData.getValueByName("BatNo");
		if (mBatNo == null || "".equals(mBatNo)) {
			CError.buildErr(this, "前台传输业务数据mBatNo失败！");
			return false;
		}
		mUWFlag = (String) mTransferData.getValueByName("UWFlag");
		if (mUWFlag == null || "".equals(mUWFlag)) {
			CError.buildErr(this, "前台传输业务数据UWFlag失败！");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，调用BL进行逻辑处理，返回处理完数据 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("----------Service dealData BEGIN----------");

		logger.debug("-----###############-----------" + mTransact);
		boolean tReturn = false;

		// 完成时，根据“LLUWMaster---个人理陪险种核保最近结果表”修改“lcpol”表的相应记录的状态

		logger.debug("－－－－－－－－－－－－－核保开始－－－－－－－－－－－－");

		// 如果完成，则修改 个人理赔合同批次表(LLCUWBatch) 中的相关记录状态为“完成”
		for (int k = 1; k <= mLLCUWBatchSet.size(); k++) {

			mLLCUWBatchSet.get(k).setState("1"); // 1---完成
			mLLCUWBatchSet.get(k).setPassFlag(mUWFlag);
			mLLCUWBatchSet.get(k).setOperator(mOperater);
			mLLCUWBatchSet.get(k).setRemark2(mUWIdea2);
			logger.debug("--mLLCUWBatchSet-[" + k + "]----"
					+ mLLCUWBatchSet.get(k).getBatNo());
			logger.debug("--mLLCUWBatchSet-[" + k + "]----"
					+ mLLCUWBatchSet.get(k).getContNo());
			logger.debug("--mLLCUWBatchSet-[" + k + "]----"
					+ mLLCUWBatchSet.get(k).getState());
			logger.debug("--mLLCUWBatchSet-[" + k + "]----"
					+ mLLCUWBatchSet.get(k).getOperator());
		}
		mmap.put(mLLCUWBatchSet, "DELETE&INSERT");
		logger.debug("－－－－－－－－－－－－－核保完成－－－－－－－－－－－－");
		//更新理赔二核状态
		LLClaimDB tLLClaimDB=new LLClaimDB();
		tLLClaimDB.setClmNo(this.mCaseno);
		if(!tLLClaimDB.getInfo()){
				
		}
		tLLClaimDB.setUWState("2");
		mmap.put(tLLClaimDB.getSchema(), "UPDATE");
		
		//如果核保结论为“4-次标准体” 则打印核保通知书
//		if(mUWFlag.equals("4")){
//			if(preparePrint()){
//				CError.buildErr(this, "准备核保通知书数据时失败！");
//				return false;
//			}
//		}
		// 与赔案有关，核保完成，转向理赔岗
		if (mTransact.equals("Finish||ToClaim")) {
			VData tVData = new VData();
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("ClmNo", mCaseno);
			tTransferData.setNameAndValue("BatNo", mBatNo);
			tVData.add(this.mGlobalInput);
			tVData.add(tTransferData);

//			LLUWFinishBL tLLUWFinishBL = new LLUWFinishBL();
//			if (tLLUWFinishBL.submitData(tVData, mTransact) == false) {
//				this.mErrors.copyAllErrors(tLLUWFinishBL.mErrors);
//				return false;
//			} else {
//				VData tempVData = tLLUWFinishBL.getResult();
//				MMap tMMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
//				mmap.add(tMMap);
//			}

			mResult.add(mTransferData);
			mResult.add(mmap);

		}
		
		
		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return
	 */
	private boolean prepareTransferData() {
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 准备需打印的通知书的信息
	 * */
	private boolean preparePrint(){
		String tLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
		String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
		LOPRTManagerSchema tLOPRTManagerSchema;
		tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
		mUWPrtSeq=tPrtSeq;
		tLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
		tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
		tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
//		tLOPRTManagerSchema.setManageCom(tLCContSchema.getManageCom());
		tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT);
//		tLOPRTManagerSchema.setAgentCode(tLCContSchema.getAgentCode());
		tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT);
		tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_PEdorUW);//25-保全核保通知书
//		tLOPRTManagerSchema.setOtherNo(mContNo);
//		tLOPRTManagerSchema.setStandbyFlag1(cLPEdorItemSchema.getEdorNo());
//		tLOPRTManagerSchema.setStandbyFlag7(cLPEdorItemSchema.getEdorType());
		tLOPRTManagerSchema.setOldPrtSeq(tPrtSeq);
		tLOPRTManagerSchema.setStateFlag("0");
		mLOPRTManagerSet.add(tLOPRTManagerSchema);
		mTransferData.setNameAndValue("PrtSeq", mUWPrtSeq);
		mTransferData.setNameAndValue("Code", PrintManagerBL.CODE_PEdorUW);
		
		return true;
	}
}
