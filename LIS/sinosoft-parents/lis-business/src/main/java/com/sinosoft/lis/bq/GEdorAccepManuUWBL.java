package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全人工核保-保全申请层处理类
 * </p>
 */
public class GEdorAccepManuUWBL {
private static Logger logger = Logger.getLogger(GEdorAccepManuUWBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();
	/** 用户登陆信息 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();
	private LPGrpEdorMainSet mLPGrpEdorMainSet = new LPGrpEdorMainSet();

	private String mUWFlag = ""; // 核保通过标志
	private String mUWPopedom = ""; // 操作员核保级别
	private String mAppGrade = ""; // 上报级别
	private String mPostponeDate = ""; // 延迟时间－－暂不用，保留
	private String mUWIdea = ""; // 核保意见
	private String mUpReportContent = ""; // 上报内容

	// 保全申请核保记录
	private LPAppUWMasterMainSet mLPAppUWMasterMainSet = new LPAppUWMasterMainSet();
	private LPAppUWSubMainSet mLPAppUWSubMainSet = new LPAppUWSubMainSet();

	public static String mEDORAPP_UWPASS = "9"; // 保全申请人工核保通过
	public static String mEDORAPP_UWSTOP = "1"; // 保全申请人工核不保通过
	public static String mEDORAPP_UWUPDATE = "T"; // 修改
	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	// 只处理团体结论，提升效率及可以达到需求，个人下合同及险种结论可以不使用，拒保，延期虽然不能回些，从既往兄弟项目组来看
	// 团调个的处理方式并不好，在后续的报表中也没有体现，也不会提高业务风险。
	public GEdorAccepManuUWBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作符
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}

		// 校验
		if (!checkData()) {
			return false;
		}

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		// 准备提交后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		return true;
	}

	/**
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {

		mLPEdorAppSchema.setUWOperator(mGlobalInput.Operator);
		mLPEdorAppSchema.setUWState(mUWFlag);
		mLPEdorAppSchema.setUWDate(mCurrentDate);
		mLPEdorAppSchema.setUWTime(mCurrentTime);
		// mLPEdorAppSchema.setOperator(mGlobalInput.Operator);
		//如果是返回上级核保，需要更新核保级别，将核保界别置为B(当前核保级别只有A,B,B为最高级)
		if(mUWFlag.equals("S"))
		{
			mLPEdorAppSchema.setUWGrade("B");
		}
		mLPEdorAppSchema.setModifyDate(mCurrentDate);
		mLPEdorAppSchema.setModifyTime(mCurrentTime);

		// 生成保全申请核保主表信息
		LPAppUWMasterMainSchema tLPAppUWMasterMainSchema = prepareEdorAppUWMasterMain(mLPEdorAppSchema);
		if (tLPAppUWMasterMainSchema != null) {
			mLPAppUWMasterMainSet.add(tLPAppUWMasterMainSchema);
		}

		// 生成保全申请核保轨迹表信息
		LPAppUWSubMainSchema tLPAppUWSubMainSchema = prepareEdorAppUWSubMain(tLPAppUWMasterMainSchema);
		if (tLPAppUWSubMainSchema != null) {
			mLPAppUWSubMainSet.add(tLPAppUWSubMainSchema);
		}

		map.put(mLPEdorAppSchema, "UPDATE");
		map.put(mLPAppUWMasterMainSet, "DELETE&INSERT");
		map.put(mLPAppUWSubMainSet, "INSERT");

		String sOtherNoType = mLPEdorAppSchema.getOtherNoType();
		String sEdorAcceptNo = mLPEdorAppSchema.getEdorAcceptNo();

		// （团体）根据险种核保结论自动给保单置核保结论
		if (sOtherNoType == null || !sOtherNoType.equals("4")) {
			CError tError = new CError();
			tError.errorMessage = "传入的操作标志不属于团险!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (!getLPEdorMain()) {
			return false;
		}

		for (int i = 1; i <= mLPGrpEdorMainSet.size(); i++) {
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("UWFlag", mUWFlag);
			tTransferData.setNameAndValue("UWIdea", mUWIdea);
			tTransferData.setNameAndValue("PostponeDate", mPostponeDate);
			tTransferData.setNameAndValue("NoSubmit", "1");

			VData tVData = new VData();
			tVData.add(mGlobalInput);
			tVData.add(tTransferData);
			tVData.add(mLPGrpEdorMainSet.get(i));

			GEdorGrpContManuUWBL tGEdorGrpContManuUWBL = new GEdorGrpContManuUWBL();

			if (!tGEdorGrpContManuUWBL.submitData(tVData, "")) {
				mErrors.copyAllErrors(tGEdorGrpContManuUWBL.mErrors);
				return false;
			}

			map.add((MMap) tGEdorGrpContManuUWBL.getResult()
					.getObjectByObjectName("MMap", 0));
		}
		// 复核修改
		if (mUWFlag.equals(mEDORAPP_UWUPDATE)) {
			updEdorState(sEdorAcceptNo, "5");
		}
		// 则直接置保全受理为核保终止[8]
		if (mUWFlag.equals(mEDORAPP_UWSTOP)) {
			updEdorState(sEdorAcceptNo, "8");

			// 保单解挂
			BqContHangUpBL tContHangUpBL = new BqContHangUpBL(mGlobalInput);
			if (!tContHangUpBL.hangUpEdorAccept(sEdorAcceptNo, "0")) {
				CError.buildErr(this, "保全保单解挂失败! ");
				return false;
			} else {
				MMap tMap = tContHangUpBL.getMMap();
				map.add(tMap); // del
			}

			// ===add=======删除C表数据====BGN==============
			PEdorOverDueBL tPEdorOverDueBL = new PEdorOverDueBL(mGlobalInput);
			map.add(tPEdorOverDueBL.delLCPol(sEdorAcceptNo));
			// =======删除C表数据====END==============
		}
		return true;
	}

	/**
	 * 得到外部传入的数据
	 * 
	 * @return: boolean
	 */
	private boolean getInputData() {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);

		mLPEdorAppSchema = (LPEdorAppSchema) mInputData.getObjectByObjectName(
				"LPEdorAppSchema", 0);

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		mUWFlag = (String) mTransferData.getValueByName("UWFlag");
		mUWIdea = (String) mTransferData.getValueByName("UWIdea");
		mPostponeDate = (String) mTransferData.getValueByName("PostponeDate");

		if (mUWFlag == null || mUWFlag.trim().equals("")) {
			CError tError = new CError();
			tError.errorMessage = "前台传输数据UWFlag失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 准备提交后台的数据
	 * 
	 * @return: boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);

			mResult.clear();
			mResult.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "WorkFlowNotePadBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 查询保全申请信息
	 * 
	 * @return boolean
	 */
	private boolean getLPEdorApp() {
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mLPEdorAppSchema.getEdorAcceptNo());
		LPEdorAppSet tLPEdorAppSet = tLPEdorAppDB.query();
		if (tLPEdorAppDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPEdorAppDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保全申请信息查询失败";
			mErrors.addOneError(tError);
			return false;
		}
		if (tLPEdorAppSet == null || tLPEdorAppSet.size() != 1) {
			CError tError = new CError();
			tError.errorMessage = "保全申请信息查询失败";
			mErrors.addOneError(tError);
			return false;
		}
		mLPEdorAppSchema = tLPEdorAppSet.get(1);

		return true;
	}

	/**
	 * 校验核保
	 */
	private boolean checkData() {
		// 查询保全申请信息
		if (!getLPEdorApp()) {
			return false;
		}

		// //校验核保级别
//		if (!checkUserGrade()) {
//			return false;
//		}

		return true;
	}

	/**
	 * 统一更新保全受理、保全申请批单、保全项目的批改状态为确认未生效 更新保全确认信息
	 * 
	 * @param sEdorAcceptNo
	 * @param sEdorState
	 */
	private void updEdorState(String sEdorAcceptNo, String sEdorState) {
		String wherePart = "where EdorAcceptNo='?sEdorAcceptNo?'";

		StringBuffer sbSQL = new StringBuffer();

		// 保全项目
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorItem set EdorState = '").append("?sEdorState?")
				.append("', ModifyDate = '").append("?mCurrentDate?").append(
						"', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(sbSQL.toString());
		sbv1.put("sEdorState", sEdorState);
		sbv1.put("mCurrentDate", mCurrentDate);
		sbv1.put("mCurrentTime", mCurrentTime);
		sbv1.put("sEdorAcceptNo", sEdorAcceptNo);
		map.put(sbv1, "UPDATE");

		// 保全批单
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorMain set EdorState = '").append("?sEdorState?")
				.append("', ModifyDate = '").append("?mCurrentDate?").append(
						"', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(sbSQL.toString());
		sbv2.put("sEdorState", sEdorState);
		sbv2.put("mCurrentDate", mCurrentDate);
		sbv2.put("mCurrentTime", mCurrentTime);
		sbv2.put("sEdorAcceptNo", sEdorAcceptNo);
		map.put(sbv2, "UPDATE");

		// 团体保全项目
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPGrpEdorItem set EdorState = '").append(
				"?sEdorState?").append("', ModifyDate = '").append("?mCurrentDate?")
				.append("', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(sbSQL.toString());
		sbv3.put("sEdorState", sEdorState);
		sbv3.put("mCurrentDate", mCurrentDate);
		sbv3.put("mCurrentTime", mCurrentTime);
		sbv3.put("sEdorAcceptNo", sEdorAcceptNo);
		map.put(sbv3, "UPDATE");

		// 团体保全批单
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPGrpEdorMain set EdorState = '").append(
				"?sEdorState?").append("', ModifyDate = '").append("?mCurrentDate?")
				.append("', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql(sbSQL.toString());
		sbv4.put("sEdorState", sEdorState);
		sbv4.put("mCurrentDate", mCurrentDate);
		sbv4.put("mCurrentTime", mCurrentTime);
		sbv4.put("sEdorAcceptNo", sEdorAcceptNo);
		map.put(sbv4, "UPDATE");

		// 保全申请
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorApp set EdorState = '").append("?sEdorState?")
				.append("', ModifyDate = '").append("?mCurrentDate?").append(
						"', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv5=new SQLwithBindVariables();
		sbv5.sql(sbSQL.toString());
		sbv5.put("sEdorState", sEdorState);
		sbv5.put("mCurrentDate", mCurrentDate);
		sbv5.put("mCurrentTime", mCurrentTime);
		sbv5.put("sEdorAcceptNo", sEdorAcceptNo);
		map.put(sbv5, "UPDATE");

	}

	/**
	 * 校验核保级别
	 * 
	 */
	private boolean checkUserGrade() {

		LDEdorUserDB tLDEdorUserDB = new LDEdorUserDB();
		tLDEdorUserDB.setUserCode(mGlobalInput.Operator);
		tLDEdorUserDB.setUserType("2");
		if (!tLDEdorUserDB.getInfo()) {
			mErrors.copyAllErrors(tLDEdorUserDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "没有配置核保权限!" + "操作员：" + mGlobalInput.Operator;
			mErrors.addOneError(tError);
			return false;
		}
		String tUWPopedom = tLDEdorUserDB.getEdorPopedom();
		if (tUWPopedom == null || tUWPopedom.trim().equals("")) {
			CError tError = new CError();
			tError.errorMessage = "操作员无核保权限，不能核保!" + "操作员："
					+ mGlobalInput.Operator;
			mErrors.addOneError(tError);
			return false;
		}

		String sql = "select * from LPEdorApp where EdorAcceptNo = '"
				+ "?EdorAcceptNo?" + "' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("EdorAcceptNo", mLPEdorAppSchema.getEdorAcceptNo());
		logger.debug(sql);
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		LPEdorAppSet tLPEdorAppSet = tLPEdorAppDB.executeQuery(sqlbv);

		if (tLPEdorAppSet == null || tLPEdorAppSet.size() <= 0) {
			CError tError = new CError();
			tError.moduleName = "PEdorAppManuUWBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "没有保全申请记录，不能核保!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			String tAppGrade = tLPEdorAppSet.get(1).getAppGrade();

			if ((tAppGrade == null) || tAppGrade.equals("")) {
				CError tError = new CError();
				tError.moduleName = "PEdorAppManuUWBL";
				tError.functionName = "checkUWGrade";
				tError.errorMessage = "该保全申请无核保级别，不能核保!";
				this.mErrors.addOneError(tError);
				return false;
			}
			
			if (tUWPopedom.compareTo(tAppGrade) < 0 && !mUWFlag.equals("S")) {
				CError tError = new CError();
				tError.moduleName = "PEdorAppManuUWBL";
				tError.functionName = "checkUWGrade";
				tError.errorMessage = "您的权限为" + tUWPopedom + ",此次申请的权限需要"
						+ tAppGrade + "级以上(包括此级别)，请选择核保上报";
				this.mErrors.addOneError(tError);

				return false;
			}
			
		}

		return true;
	}

	/**
	 * 生成保全申请核保主表信息
	 * 
	 * @return boolean
	 */
	private LPAppUWMasterMainSchema prepareEdorAppUWMasterMain(
			LPEdorAppSchema tLPEdorAppSchema) {
		int tUWNo = 0;
		LPAppUWMasterMainSchema tLPAppUWMasterMainSchema = new LPAppUWMasterMainSchema();

		LPAppUWMasterMainDB tLPAppUWMasterMainDB = new LPAppUWMasterMainDB();
		tLPAppUWMasterMainDB
				.setEdorAcceptNo(tLPEdorAppSchema.getEdorAcceptNo());
		LPAppUWMasterMainSet tLPAppUWMasterMainSet = tLPAppUWMasterMainDB
				.query();
		if (tLPAppUWMasterMainSet.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPAppUWMasterMainDB.mErrors);
			mErrors.addOneError(new CError("获取保全申请核保主表信息失败！"));
			return null;
		}
		if (tLPAppUWMasterMainSet == null || tLPAppUWMasterMainSet.size() <= 0) {
			tUWNo = 1;
			tLPAppUWMasterMainSchema.setEdorAcceptNo(tLPEdorAppSchema
					.getEdorAcceptNo());
			tLPAppUWMasterMainSchema.setOtherNo(tLPEdorAppSchema.getOtherNo());
			tLPAppUWMasterMainSchema.setOtherNoType(tLPEdorAppSchema
					.getOtherNoType());
			tLPAppUWMasterMainSchema.setUpReportContent("");
			tLPAppUWMasterMainSchema.setHealthFlag("0");
			tLPAppUWMasterMainSchema.setQuesFlag("0");
			tLPAppUWMasterMainSchema.setSpecFlag("0");
			tLPAppUWMasterMainSchema.setAddPremFlag("0");
			tLPAppUWMasterMainSchema.setAddPremReason("");
			tLPAppUWMasterMainSchema.setReportFlag("0");
			tLPAppUWMasterMainSchema.setPrintFlag("0");
			tLPAppUWMasterMainSchema.setPrintFlag2("0");
			tLPAppUWMasterMainSchema.setSpecReason("");
			tLPAppUWMasterMainSchema.setManageCom(mLPEdorAppSchema
					.getManageCom());
			tLPAppUWMasterMainSchema.setMakeDate(PubFun.getCurrentDate());
			tLPAppUWMasterMainSchema.setMakeTime(PubFun.getCurrentTime());
		} else {
			tUWNo = tLPAppUWMasterMainSet.get(1).getUWNo() + 1;
			tLPAppUWMasterMainSchema.setSchema(tLPAppUWMasterMainSet.get(1));
		}
		tLPAppUWMasterMainSchema.setUWNo(tUWNo);

		tLPAppUWMasterMainSchema.setAutoUWFlag("2");
		tLPAppUWMasterMainSchema.setPassFlag(mLPEdorAppSchema.getUWState());
		tLPAppUWMasterMainSchema.setPostponeDate(mPostponeDate);
		tLPAppUWMasterMainSchema.setState(mLPEdorAppSchema.getUWState());
		tLPAppUWMasterMainSchema.setUWIdea(mUWIdea);
		tLPAppUWMasterMainSchema.setUWGrade(mLPEdorAppSchema.getUWGrade());
		tLPAppUWMasterMainSchema.setAppGrade(mLPEdorAppSchema.getAppGrade());
		tLPAppUWMasterMainSchema.setOperator(mGlobalInput.Operator);
		tLPAppUWMasterMainSchema.setModifyDate(PubFun.getCurrentDate());
		tLPAppUWMasterMainSchema.setModifyTime(PubFun.getCurrentTime());

		return tLPAppUWMasterMainSchema;
	}

	/**
	 * 生成保全申请核保轨迹表信息
	 * 
	 * @return boolean
	 */
	private LPAppUWSubMainSchema prepareEdorAppUWSubMain(
			LPAppUWMasterMainSchema tLPAppUWMasterMainSchema) {
		LPAppUWSubMainSchema tLPAppUWSubMainSchema = new LPAppUWSubMainSchema();
		Reflections tRef = new Reflections();
		tRef.transFields(tLPAppUWSubMainSchema, tLPAppUWMasterMainSchema);

		tLPAppUWSubMainSchema.setOperator(mGlobalInput.Operator);
		tLPAppUWSubMainSchema.setModifyDate(PubFun.getCurrentDate());
		tLPAppUWSubMainSchema.setModifyTime(PubFun.getCurrentTime());
		tLPAppUWSubMainSchema.setMakeDate(PubFun.getCurrentDate());
		tLPAppUWSubMainSchema.setMakeTime(PubFun.getCurrentTime());

		return tLPAppUWSubMainSchema;
	}

	/**
	 * 查询保全批单信息
	 * 
	 * @return boolean
	 */
	private boolean getLPEdorMain() {

		LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
		tLPGrpEdorMainDB.setEdorAcceptNo(mLPEdorAppSchema.getEdorAcceptNo());
		mLPGrpEdorMainSet = tLPGrpEdorMainDB.query();
		if (tLPGrpEdorMainDB.mErrors.needDealError()) {
			CError.buildErr(this, "保全批单信息查询失败");
			return false;
		}

		return true;
	}

	/**
	 * 返回处理结果
	 * 
	 * @return: VData
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.Operator = "zhangtao";
		tG.ManageCom = "86110000";
		LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
		tLPEdorAppSchema.setEdorAcceptNo("6120061213000001");

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("UWFlag", "9");
		tTransferData.setNameAndValue("UWIdea", " test by zhangtao");
		tTransferData.setNameAndValue("PostponeDate", "");
		tTransferData.setNameAndValue("AppUser", "");

		// tTransferData.setNameAndValue("MissionID", "00000000000000006379");
		// tTransferData.setNameAndValue("SubMissionID", "1");
		// tTransferData.setNameAndValue("OtherNo", "230110000000520");
		// tTransferData.setNameAndValue("OtherNoType", "3");
		// tTransferData.setNameAndValue("EdorAppName", "陈洋洋");
		// tTransferData.setNameAndValue("Apptype", "2");
		// tTransferData.setNameAndValue("ManageCom", "86110000");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		tVData.add(tLPEdorAppSchema);

		PEdorAccepManuUWBL tPEdorAccepManuUWBL = new PEdorAccepManuUWBL();

		if (!tPEdorAccepManuUWBL.submitData(tVData, "")) {
			logger.debug(tPEdorAccepManuUWBL.mErrors.getError(0).errorMessage);
		}
	}
}
