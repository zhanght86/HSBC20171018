package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCCGrpSpecDB;
import com.sinosoft.lis.db.LCGCUWMasterDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCCGrpSpecSchema;
import com.sinosoft.lis.schema.LCGCUWMasterSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class GrpUWSpecBL {
private static Logger logger = Logger.getLogger(GrpUWSpecBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();
	/** 传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCGrpContDB mLCGrpContDB = new LCGrpContDB();
	// private VData mIputData = new VData();

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mOperate;

	/** 业务数据操作字符串 */
	private String mGrpContNo = "";
	private String mRemark = "";
	private String mSpecReason = "";
	private String mOperatetype;
	private String mSerialno = "00000000000000000000";
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	/** 核保主表 */
	private LCGCUWMasterSchema mLCGCUWMasterSchema = new LCGCUWMasterSchema();
	/** 特约表 */
	private LCCGrpSpecSchema mLCCGrpSpecSchema = new LCCGrpSpecSchema();

	public GrpUWSpecBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		// 校验是否有未打印的体检通知书
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

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAutoChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
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
		mOperator = mGlobalInput.Operator;
		mOperate = cOperate;
		mRemark = (String) mTransferData.getValueByName("Remark");
		mGrpContNo = (String) mTransferData.getValueByName("GrpContNo");
		mSpecReason = (String) mTransferData.getValueByName("SpecReason");

		return true;

	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		if (mOperate == null || mOperate.equals("")) {
			CError tError = new CError();
			tError.moduleName = "GrpUWSpecBL";
			tError.functionName = "prepareSpec";
			tError.errorMessage = "获取前台传输数据操作类型失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (!mOperate.equals("DELETE||GrpUWSpec")
				&& mOperate.equals("INSERT||GrpUWSpec")) {
			CError tError = new CError();
			tError.moduleName = "GrpUWSpecBL";
			tError.functionName = "prepareSpec";
			tError.errorMessage = "错误的操作类型!";
			this.mErrors.addOneError(tError);

		}
		if (mGrpContNo == null || mGrpContNo.equals("")) {
			CError tError = new CError();
			tError.moduleName = "GrpUWSpecBL";
			tError.functionName = "prepareSpec";
			tError.errorMessage = "获取前台传输数据集体保单号失败!";
			this.mErrors.addOneError(tError);
			return false;

		}
		if (mOperator == null || mOperator.equals("")) {
			CError tError = new CError();
			tError.moduleName = "GrpUWSpecBL";
			tError.functionName = "prepareSpec";
			tError.errorMessage = "获取前台传输数据操作员信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCGrpContDB.setGrpContNo(mGrpContNo);
		if (!mLCGrpContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "GrpUWSpecBL";
			tError.functionName = "prepareSpec";
			tError.errorMessage = "查询集体保单[" + mGrpContNo + "]信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mOperate.equals("DELETE||GrpUWSpec")) {
			// 删除操作
			LCCGrpSpecDB tLCCGrpSpecDB = new LCCGrpSpecDB();
			tLCCGrpSpecDB.setProposalGrpContNo(mLCGrpContDB
					.getProposalGrpContNo());
			tLCCGrpSpecDB.setSerialNo(mSerialno);
			if (!tLCCGrpSpecDB.getInfo()) {
				CError tError = new CError();
				tError.moduleName = "GrpUWSpecBL";
				tError.functionName = "prepareSpec";
				tError.errorMessage = "该保单还未录入特约信息，不能进行删除操作!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 核保特约信息
		if (prepareSpec() == false) {
			return false;
		}
		return true;
	}

	/**
	 * 准备特约资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareSpec() {
		// 准备续保核保主表信息
		LCGCUWMasterDB tLCGCUWMasterDB = new LCGCUWMasterDB();
		tLCGCUWMasterDB.setProposalGrpContNo(mLCGrpContDB
				.getProposalGrpContNo());
		if (tLCGCUWMasterDB.getInfo() == false) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpUWSpecBL";
			tError.functionName = "prepareSpec";
			tError.errorMessage = "无保单核保主表信息!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 集体保单核保结信息
		mLCGCUWMasterSchema.setSchema(tLCGCUWMasterDB.getSchema());

		mLCGCUWMasterSchema.setSpecReason(mSpecReason);
		mLCGCUWMasterSchema.setSpecFlag("1"); // 特约标识

		if (mOperate.equals("DELETE||GrpUWSpec")) {
			mLCGCUWMasterSchema.setSpecReason("");
			mLCGCUWMasterSchema.setSpecFlag("");
		}

		mLCGCUWMasterSchema.setOperator(mOperator);
		mLCGCUWMasterSchema.setManageCom(mManageCom);
		mLCGCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		mLCGCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

		// 团体特约信息
		mLCCGrpSpecSchema.setSerialNo(mSerialno);
		mLCCGrpSpecSchema.setGrpContNo(mLCGCUWMasterSchema.getGrpContNo());
		mLCCGrpSpecSchema.setPrtSeq(mSerialno); // 打印流水号不起用，00000000000000000000
		mLCCGrpSpecSchema.setProposalGrpContNo(mLCGCUWMasterSchema
				.getProposalGrpContNo());
		mLCCGrpSpecSchema.setPrtFlag("1");
		mLCCGrpSpecSchema.setBackupType("");
		mLCCGrpSpecSchema.setSpecContent(mRemark);
		mLCCGrpSpecSchema.setOperator(mOperator);
		mLCCGrpSpecSchema.setMakeDate(mCurrentDate);
		mLCCGrpSpecSchema.setMakeTime(mCurrentTime);
		mLCCGrpSpecSchema.setModifyDate(mCurrentDate);
		mLCCGrpSpecSchema.setModifyTime(mCurrentTime);
		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		// 添加本次续保特约数据
		if (mOperate.equals("INSERT||GrpUWSpec")) {
			map.put(mLCCGrpSpecSchema, "DELETE&INSERT");
		}
		if (mOperate.equals("DELETE||GrpUWSpec")) {
			map.put(mLCCGrpSpecSchema, "DELETE");
		}

		// 添加续保批单核保主表通知书打印管理表数据
		map.put(mLCGCUWMasterSchema, "UPDATE");
		mResult.add(map);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String arts[]) {
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "8621000";
		tG.ManageCom = "8621000";
		tG.Operator = "001";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("GrpContNo", "48795000002863");
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(tG);

		GrpUWSpecBL tGrpUWSpecBL = new GrpUWSpecBL();
		if (!tGrpUWSpecBL.submitData(tVData, "DELETE||GrpUWSpec")) {
			logger.debug(tGrpUWSpecBL.mErrors.getFirstError());
		}
	}
}
