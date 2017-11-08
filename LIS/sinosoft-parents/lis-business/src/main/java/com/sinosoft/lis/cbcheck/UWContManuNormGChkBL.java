package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sinosoft.ibrms.bom.BOMAgent;
import com.sinosoft.ibrms.bom.BOMAppnt;
import com.sinosoft.ibrms.bom.BOMCont;
import com.sinosoft.ibrms.bom.BOMElement;
import com.sinosoft.ibrms.bom.BOMInsured;
import com.sinosoft.ibrms.bom.BOMPol;
import com.sinosoft.lis.db.LCCUWErrorDB;
import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.db.LMUWDB;
import com.sinosoft.lis.pubfun.CalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCCUWErrorSchema;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCCUWSubSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LMUWSchema;
import com.sinosoft.lis.vschema.LCCUWErrorSet;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.lis.vschema.LCCUWSubSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCUWErrorSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.lis.vschema.LMUWSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统承保个人人工核保部分
 * </p>
 * <p>
 * Description: 逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Zhangrong
 * @version 1.0
 */
public class UWContManuNormGChkBL {
private static Logger logger = Logger.getLogger(UWContManuNormGChkBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperator; // 操作员
	private String mManageCom;
	private String mpassflag; // 通过标记
	private String mContPassFlag = "";
	private String mBackFlag;
	private String mCalCode; // 计算编码
	private String mUser;
	private double mValue;
	private double mprem;
	private String theDate = "";
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/** 业务处理相关变量 */
	private LCContSet mLCContSet = new LCContSet();
	private LCContSet mAllLCContSet = new LCContSet();
	private LCContSchema mLCContSchema = new LCContSchema();

	private String mGrpContNo = "";
	private String mContNo = "";
	private String mPolNo = "";
	private String mUWFlag = ""; // 核保标志
	private Date mvalidate = null;
	private String mUWIdea = ""; // 核保结论
	private String mUPUWCode = "";
	private String mpostday; // 延长天数
	private String mUWPopedom = ""; // 操作员核保级别
	private String mAppGrade = ""; // 上报级别
	private String mReason = ""; // 加费原因
	private String mSpecReason = ""; // 特约原因
	private String mQuesFlag = ""; // 是否有问题件标记
	private String mQuesOrgFlag = ""; // 机构问题件标记
	private String mQuesOpeFlag = ""; // 操作员问题件标记
	private String mPolType = ""; // 保单类型
	private String mAgentPrintFlag = ""; // 是否有返回业务员要打印的问题件标记
	private String mAgentQuesFlag = ""; // 是否有返回业务员问题件标记
	private boolean mChgContMaster = false; // 是否更新LCCUWMaster

	/** 核保主表 */
	private LCCUWMasterSet mLCCUWMasterSet = new LCCUWMasterSet();
	private LCCUWMasterSet mAllLCCUWMasterSet = new LCCUWMasterSet();
	private LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();
	private LCUWMasterSet mAllLCUWMasterSet = new LCUWMasterSet();
	private LCUWMasterSchema mLCUWMasterSchema = new LCUWMasterSchema();

	/** 核保子表 */
	private LCCUWSubSet mLCCUWSubSet = new LCCUWSubSet();
	private LCCUWSubSet mAllLCCUWSubSet = new LCCUWSubSet();
	private LCUWSubSet mLCUWSubSet = new LCUWSubSet();
	private LCUWSubSet mAllLCUWSubSet = new LCUWSubSet();

	/** 核保错误信息表 */
	private LCUWErrorSet mLCUWErrorSet = new LCUWErrorSet();
	private LCUWErrorSet mAllLCErrSet = new LCUWErrorSet();

	/** 计算公式表* */
	private LMUWSchema mLMUWSchema = new LMUWSchema();
	private LMUWSet mLMUWSet = new LMUWSet();
	private CalBase mCalBase = new CalBase();
	private GlobalInput mGlobalInput = new GlobalInput();
	private String mGetNoticeNo = "";

	public UWContManuNormGChkBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		int flag = 0; // 判断是不是所有数据都不成功
		int j = 0; // 符合条件数据个数

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		logger.debug("---UWContManuNormGChkBL getInputData Begin ---");

		if (!getInputData(cInputData)) {
			return false;
		}

		logger.debug("---UWContManuNormGChkBL getInputData End ---");

		// 校验该投保单是否已复核过,是则可进行核保,否则不能进行
		if (!checkApprove()) {
			return false;
		}

		// 校验核保级别
		logger.debug("---UWContManuNormGChkBL checkUWGrade Begin---");

		if (!checkUWGrade()) {
			return false;
		}

		logger.debug("---UWContManuNormGChkBL checkUWGrade End---");

		// 生成给付通知书号
		String tLimit = PubFun.getNoLimit(mManageCom);
		mGetNoticeNo = PubFun1.CreateMaxNo("GETNOTICENO", tLimit); // 产生即付通知书号
		logger.debug("---tLimit---" + tLimit);

		if (mUWFlag.equals("1") || mUWFlag.equals("2") || mUWFlag.equals("4")
				|| mUWFlag.equals("9")) {
			// 次标准体校验核保员级别
			if (!checkStandGrade()) {
				return false;
			}

			if (!checkBackPol()) {
				return false;
			}
		}

		// 拒保或延期要校验核保员级别
		if (mUWFlag.equals("1") || mUWFlag.equals("2")) {
			if (!checkUserGrade()) {
				return false;
			}
		}

		// 数据操作业务处理
		logger.debug("数据操作业务处理" + mUWFlag);

		if (!dealData()) {
			return false;
		}

		logger.debug("---UWContManuNormGChkBL dealData---");

		// 准备给后台的数据
		prepareOutputData();
		logger.debug("---UWContManuNormGChkBL prepareOutputData---");

		// 数据提交
		PubSubmit tSubmit = new PubSubmit();

		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWContManuNormGChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("IN dealData()" + mUWFlag);

		if (dealOnePol() == false) {
			return false;
		}

		return true;
	}

	/**
	 * 操作一张保单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealOnePol() {
		// 保单
		if (prepareCont() == false) {
			return false;
		}

		// 核保信息
		if (prepareUW() == false) {
			return false;
		}

		LCContSet tLCContSet = new LCContSet();
		tLCContSet.set(mLCContSet);
		mAllLCContSet.add(tLCContSet);

		LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();
		tLCCUWMasterSet.set(mLCCUWMasterSet);
		mAllLCCUWMasterSet.add(tLCCUWMasterSet);

		LCCUWSubSet tLCCUWSubSet = new LCCUWSubSet();
		tLCCUWSubSet.set(mLCCUWSubSet);
		mAllLCCUWSubSet.add(tLCCUWSubSet);

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		String getflag = "true"; // 判断承保条件是否接收

		GlobalInput tGlobalInput = new GlobalInput();
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mOperator = mGlobalInput.Operator;
		mManageCom = mGlobalInput.ManageCom;

		// 取投保单
		mLCContSet.set((LCContSet) cInputData.getObjectByObjectName(
				"LCContSet", 0));

		int n = mLCContSet.size();

		if (n == 1) {
			LCContSchema tLCContSchema = mLCContSet.get(1);
			mUWFlag = tLCContSchema.getUWFlag();
			mContNo = tLCContSchema.getContNo();
			mUWIdea = tLCContSchema.getRemark();

			// 校验是不是以下核保结论
			if ((mUWFlag == null) || mUWFlag.equals("")) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "UWContManuNormGChkBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "没有选择核保结论";
				this.mErrors.addOneError(tError);

				return false;
			}

			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(mContNo);
			logger.debug("--保单表中的合同单号:  " + mContNo);

			if (tLCContDB.getInfo() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCContDB.mErrors);

				CError tError = new CError();
				tError.moduleName = "UWContManuNormGChkBL";
				tError.functionName = "getInputData";
				tError.errorMessage = mPolNo + "投保单查询失败!";
				this.mErrors.addOneError(tError);

				return false;
			} else {
				mLCContSchema.setSchema(tLCContDB);
				mGrpContNo = mLCContSchema.getGrpContNo();
			}

			// 判断是团单下个单还是个人单
			if (!mLCContSchema.getGrpContNo().equals("00000000000000000000")) {
				mPolType = "2";
			}
		} else {
			return false;
		}

		return true;
	}

	/**
	 * 校验投保单是否复核 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkApprove() {
		if ((mLCContSchema.getApproveCode() == null)
				|| mLCContSchema.getApproveDate().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWContManuNormGChkBL";
			tError.functionName = "checkApprove";
			tError.errorMessage = "投保单尚未进行复核操作，不能核保!（投保单号：" + mContNo.trim()
					+ "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		return true;
	}

	/**
	 * 校验核保员级别 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkUWGrade() {
		LDUserDB tLDUserDB = new LDUserDB();
		tLDUserDB.setUserCode(mOperator);
		logger.debug("mOperator" + mOperator);

		if (!tLDUserDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "UWContManuNormGChkBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "无此操作员信息，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		String tUWPopedom = tLDUserDB.getUWPopedom();
		mUWPopedom = tUWPopedom;
		mAppGrade = mUWPopedom;
		logger.debug("tUWPopedom" + tUWPopedom);

		if ((tUWPopedom == null) || tUWPopedom.equals("")) {
			CError tError = new CError();
			tError.moduleName = "UWContManuNormGChkBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "操作员无核保权限，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setContNo(mLCContSchema.getContNo());
		tLCCUWMasterDB.setProposalContNo(mLCContSchema.getContNo());
		logger.debug("tUWPopedom" + tUWPopedom);

		if (!tLCCUWMasterDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "UWContManuNormGChkBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "没有核保信息，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		} else {
			String tappgrade = tLCCUWMasterDB.getAppGrade();

			if ((tappgrade == null) || tappgrade.equals("")) {
				CError tError = new CError();
				tError.moduleName = "UWContManuNormGChkBL";
				tError.functionName = "checkUWGrade";
				tError.errorMessage = "该投保单无核保级别，不能核保!";
				this.mErrors.addOneError(tError);

				return false;
			}

			logger.debug("tappgrade+tUWPopedom" + tappgrade + tUWPopedom);

			if (tUWPopedom.compareTo(tappgrade) < 0) {
				CError tError = new CError();
				tError.moduleName = "UWContManuNormGChkBL";
				tError.functionName = "checkUWGrade";
				tError.errorMessage = "已经提交上级核保，不能核保!（操作员：" + mOperator + "）";
				this.mErrors.addOneError(tError);

				return false;
			}
		}

		LCCUWErrorDB tLCCUWErrorDB = new LCCUWErrorDB();
		tLCCUWErrorDB.setContNo(mLCContSchema.getContNo());

		String tcontno = mLCContSchema.getContNo();
		String tsql = "select * from lccuwerror where contno = '"
				+ "?contno?"
				+ "' and uwno = (select max(uwno) from lccuwerror where contno = '"
				+ "?contno?" + "')";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tsql);
		sqlbv.put("contno", tcontno.trim());
		LCCUWErrorSet tLCCUWErrorSet = tLCCUWErrorDB.executeQuery(sqlbv);

		int errno = tLCCUWErrorSet.size();

		if (errno > 0) {
			for (int i = 1; i <= errno; i++) {
				LCCUWErrorSchema tLCCUWErrorSchema = new LCCUWErrorSchema();
				tLCCUWErrorSchema = tLCCUWErrorSet.get(i);

				String terrgrade = tLCCUWErrorSchema.getUWGrade();
				logger.debug("terrgrade" + terrgrade);

				if ((terrgrade != null)
						&& (tUWPopedom.compareTo(terrgrade) < 0)
						&& !mUWFlag.equals("6") && !mUWFlag.equals("8")
						&& !mUWFlag.equals("3")) {
					CError tError = new CError();
					tError.moduleName = "UWContManuNormGChkBL";
					tError.functionName = "checkUWGrade";
					tError.errorMessage = "核保级别不够，请录入核保意见，申请待上级核保!（操作员："
							+ mOperator + "）";
					this.mErrors.addOneError(tError);

					return false;
				}

				logger.debug("tUWPopedom" + tUWPopedom);
			}
		}

		return true;
	}

	/**
	 * 校验是否有撤销投保申请
	 * 
	 * @return
	 */
	private boolean checkBackPol() {
		return true;
	}

	/**
	 * 个人单核保数据准备 输出：如果发生错误则返回false,否则返回true
	 */
	private void CheckPolInit(LCContSchema tLCContSchema) {
		mCalBase = new CalBase();
		mCalBase.setPrem(tLCContSchema.getPrem());
		mCalBase.setGet(tLCContSchema.getAmnt());
		mCalBase.setMult(tLCContSchema.getMult());
		mCalBase.setContNo(tLCContSchema.getContNo());
	}

	/**
	 * 个人单核保 输出：如果发生错误则返回false,否则返回true
	 */
	private String CheckPol() {
		// 准备数据
		CheckPolInit(mLCContSchema);
		//准备BOMCont数据
		BOMCont cont = new BOMCont();
		cont = DealBOMCont(mLCContSchema);
		
		/**备用**/
//		//准备被保人BOMAppnt数据
//		BOMAppnt appnt = new BOMAppnt();		
//		appnt = DealBOMAppnt(tLCAppntDB.getSchema());
//		
//		//准备代理人BOMAgent数据
//		BOMAgent agent = new BOMAgent();
//		agent = DealBOMAgent(tLAAgentDB.getSchema());
//		
//		//准备被保人BOMInsured数据
//		BOMInsured insured = new BOMInsured();
//		insured = DealBOMInsured(tLCInsuredDB.getSchema());
//		
//		//准备险种BOMPol数据
//		BOMPol pol = new BOMPol();//一个险种
//		pol = DealBOMPol(tLCPolSchema);
		
		//准备受益人BOMBnf数据
		
		BOMElement element = new BOMElement();
		List list = new ArrayList();
		list.add(cont);
//		list.add(appnt);
//		list.add(agent);
//		list.add(insured);
//		list.add(pol);
		
		list.add(element);
		
		String tUWGrade = "";

		// 计算
		Calculator mCalculator = new Calculator();
		mCalculator.setCalCode(mCalCode);
		mCalculator.setBOMList(list);
		// 增加基本要素
		mCalculator.addBasicFactor("Get", mCalBase.getGet());
		mCalculator.addBasicFactor("Mult", mCalBase.getMult());
		mCalculator.addBasicFactor("Prem", mCalBase.getPrem());
		mCalculator.addBasicFactor("AppAge", mCalBase.getAppAge());
		mCalculator.addBasicFactor("Sex", mCalBase.getSex());
		mCalculator.addBasicFactor("Job", mCalBase.getJob());
		mCalculator.addBasicFactor("PayEndYear", mCalBase.getPayEndYear());
		mCalculator.addBasicFactor("GetStartDate", "");
		mCalculator.addBasicFactor("Years", mCalBase.getYears());
		mCalculator.addBasicFactor("Grp", "");
		mCalculator.addBasicFactor("GetFlag", "");
		mCalculator.addBasicFactor("ValiDate", "");
		mCalculator.addBasicFactor("Count", mCalBase.getCount());
		mCalculator.addBasicFactor("FirstPayDate", "");
		mCalculator.addBasicFactor("PolNo", mCalBase.getPolNo());

		String tStr = "";
		tStr = mCalculator.calculate();

		if (tStr.trim().equals("")) {
			tUWGrade = "";
		} else {
			tUWGrade = tStr.trim();
		}

		logger.debug("AmntGrade:" + tUWGrade);

		return tUWGrade;
	}
	private BOMCont DealBOMCont(LCContSchema tLCContSchema){
		BOMCont cont = new BOMCont();
		try{			
			cont.setSellType(tLCContSchema.getSellType());//出单方式
			//tongmeng 2009-02-19
			//问题件信息先设置默认值
			cont.setComIssueFlag("0");//机构问题件标记
			cont.setCustomerIssueFlag("0");//客户问题件标记
			cont.setAppntIssueFlag("0");//投保人问题件标记
			cont.setAgentIssueFlag("0");//业务员问题件标记			
			cont.setAutoPayFlag(tLCContSchema.getAutoPayFlag());
			cont.setBankAccNo(tLCContSchema.getBankAccNo());
			cont.setBankCode(tLCContSchema.getBankCode());
			cont.setCardFlag(tLCContSchema.getCardFlag());
			cont.setContNo(tLCContSchema.getContNo());			
			cont.setManageCom(tLCContSchema.getManageCom());			
			cont.setOutPayFlag(tLCContSchema.getOutPayFlag());	
			cont.setPrem(tLCContSchema.getPrem());
			cont.setAmnt(tLCContSchema.getAmnt());
			cont.setMult(tLCContSchema.getMult());
			cont.setContNo(tLCContSchema.getContNo());
		}catch(Exception e){
			CError.buildErr(this, "准备BOMCont时出错！");
			e.printStackTrace();
		}
		return cont;
	}
	
	private BOMAppnt DealBOMAppnt(LCAppntSchema tLCAppntSchema){
		BOMAppnt appnt = new BOMAppnt();
		ExeSQL tempExeSQL = new ExeSQL();
		try{			
			appnt.setAppntName(tLCAppntSchema.getAppntName());
			appnt.setAppntNo(tLCAppntSchema.getAppntNo());
			appnt.setAppntSex(tLCAppntSchema.getAppntSex());		
			
			appnt.setNationality(tLCAppntSchema.getNationality());
			appnt.setNativePlace(tLCAppntSchema.getNativePlace());
			appnt.setOccupationCode(tLCAppntSchema.getOccupationCode());
			appnt.setOccupationType(tLCAppntSchema.getOccupationType());
			appnt.setPosition(tLCAppntSchema.getPosition());
			appnt.setRelationToInsured(tLCAppntSchema.getRelationToInsured());
			appnt.setRgtAddress(tLCAppntSchema.getRgtAddress());
			appnt.setSalary(new Double(tLCAppntSchema.getSalary()));
			
		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return appnt;
	}
	
	private BOMAgent DealBOMAgent(LAAgentSchema tLAAgentSchema){
		BOMAgent agent = new BOMAgent();
		String tBlackList="select blacklisflag from latree where agentcode='"+"?agentcode?"+"'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tBlackList);
		sqlbv1.put("agentcode", tLAAgentSchema.getAgentCode());
		ExeSQL tempExeSQL = new ExeSQL();
		String tBlackFlag = tempExeSQL.getOneValue(sqlbv1);
		agent.setAgentBlankFlag(tBlackFlag);//黑名单标记
		agent.setAgentCode(tLAAgentSchema.getAgentCode());
		agent.setAgentKind(tLAAgentSchema.getAgentKind());
		agent.setAgentState(tLAAgentSchema.getAgentState());
		agent.setBranchType(tLAAgentSchema.getBranchType());
		agent.setInsideFlag(tLAAgentSchema.getInsideFlag());
		agent.setManageCom(tLAAgentSchema.getManageCom());
		agent.setQuafNo(tLAAgentSchema.getQuafNo());
		agent.setSaleQuaf(tLAAgentSchema.getSaleQuaf());		
		return agent;
	}
	
	private BOMInsured DealBOMInsured(LCInsuredSchema tLCInsuredSchema){
		BOMInsured Insured = new BOMInsured();
		ExeSQL tempExeSQL = new ExeSQL();
		try{
			//参考AutoUWCheckBL.DealBOMInsured
			Insured.setInsuredNo(tLCInsuredSchema.getInsuredNo());
			
			String polApplyDateSql = "select PolApplyDate from lccont where contno='"+"?contno?"+"'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(polApplyDateSql);
			sqlbv2.put("contno", tLCInsuredSchema.getContNo());
			String tpolApplyDate = tempExeSQL.getOneValue(sqlbv2);
			int tInsAge = PubFun.calInterval(tLCInsuredSchema.getBirthday(), tpolApplyDate, "Y");
			Insured.setInsuredAppAge(Double.valueOf(String.valueOf(tInsAge)));//投保年龄
			
			Insured.setInsuredStat(tLCInsuredSchema.getInsuredStat());
			Insured.setMarriage(tLCInsuredSchema.getMarriage());
			Insured.setRelationToAppnt(tLCInsuredSchema.getRelationToAppnt());
			Insured.setSalary(Double.valueOf(String.valueOf(tLCInsuredSchema.getSalary())));
			Insured.setSex(tLCInsuredSchema.getSex());

			String sumMultSql = "select (case when sum( case when mult=0 then 1 else mult end) is null then 0 else sum( case when mult=0 then 1 else mult end) end) from lcpol where insuredno='"+"?insuredno?"+"'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(sumMultSql);
			sqlbv3.put("insuredno", tLCInsuredSchema.getInsuredNo());
			String tsumMult = tempExeSQL.getOneValue(sqlbv3);
			Insured.setSumMult(tsumMult);//累计投保份数

			//既往异常投保/理赔史 OuncommonConTent
			String tOuncommonConTent_sql = "select count(*) from lccustomerimpart where impartcode in('A0117','A0118','D0115','D0116','D0117','C0108','A0528','A0529')"
				+" and contno='"+"?contno?"+"' and customerno='"+"?customerno?"+"' ";//and CustomerNoType='1'";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(tOuncommonConTent_sql);
			sqlbv4.put("contno", tLCInsuredSchema.getContNo());
			sqlbv4.put("customerno", tLCInsuredSchema.getInsuredNo());
			String tOuncommonConTent = "";
			tOuncommonConTent = tempExeSQL.getOneValue(sqlbv4);
			if(tOuncommonConTent!=null&&!tOuncommonConTent.equals("")&&Integer.parseInt(tOuncommonConTent)>0){
				Insured.setOuncommonConTent("1");
			}else{
				Insured.setOuncommonConTent("0");
			}

		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return Insured;
	}
	
	private BOMPol DealBOMPol(LCPolSchema tLCPolSchema){
		BOMPol Pol = new BOMPol();
		ExeSQL tempExeSQL = new ExeSQL();
		try{			
			Pol.setAmnt(Double.valueOf(String.valueOf(tLCPolSchema.getAmnt())));
			Pol.setUWFlag(tLCPolSchema.getUWFlag());
			Pol.setPrem(new Double(tLCPolSchema.getPrem()));
			Pol.setInsuredNo(tLCPolSchema.getInsuredNo());
			Pol.setMainPolNo(tLCPolSchema.getMainPolNo());
			Pol.setMult(new Double(tLCPolSchema.getMult()));
			Pol.setPayYears(new Double(tLCPolSchema.getPayYears()));
			Pol.setPolNo(tLCPolSchema.getPolNo());
			Pol.setInsuYear(Double.valueOf(String.valueOf(tLCPolSchema.getInsuYear())));
			Pol.setInsuYearFlag(tLCPolSchema.getInsuYearFlag());
			Pol.setCurrency(tLCPolSchema.getCurrency());
			Pol.setLiveGetMode(tLCPolSchema.getLiveGetMode());
			Pol.setBonusGetMode(tLCPolSchema.getBonusGetMode());
			Pol.setRiskCode(tLCPolSchema.getRiskCode());
			if(!(tLCPolSchema.getCValiDate()==null||"".equals(tLCPolSchema.getCValiDate()))){
				theDate=tLCPolSchema.getCValiDate()+" 00:00:00";
				Pol.setCValiDate(sdf.parse(theDate));
			}
			Pol.setFloatRate(new Double(tLCPolSchema.getFloatRate()));
			
		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return Pol;
	}

	/**
	 * 核保险种信息校验,准备核保算法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean CheckKinds(String tFlag) {
		mLMUWSet.clear();

		LMUWSchema tLMUWSchema = new LMUWSchema();

		// 查询算法编码
		if (tFlag.equals("1")) {
			tLMUWSchema.setUWType("13"); // 非标准体
		}

		if (tFlag.equals("2")) {
			tLMUWSchema.setUWType("14"); // 拒保延期
		}

		tLMUWSchema.setRiskCode("000000");
		tLMUWSchema.setRelaPolType("I");

		LMUWDB tLMUWDB = new LMUWDB();
		tLMUWDB.setSchema(tLMUWSchema);

		mLMUWSet = tLMUWDB.query();

		if (tLMUWDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMUWDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWContManuNormGChkBL";
			tError.functionName = "CheckKinds";
			tError.errorMessage = "核保级别校验算法读取失败!";
			this.mErrors.addOneError(tError);

			// mLMUWDBSet.clear();
			return false;
		}

		return true;
	}

	/**
	 * 次标准体校验核保员级别 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkStandGrade() {
		CheckKinds("1");

		LCCUWMasterSchema tLCCUWMasterSchema = new LCCUWMasterSchema();
		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();

		tLCCUWMasterDB.setContNo(mContNo);

		if (tLCCUWMasterDB.getInfo() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWContManuNormGChkBL";
			tError.functionName = "checkStandGrade";
			tError.errorMessage = "LCCUWMaster表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		} else {
			tLCCUWMasterSchema = tLCCUWMasterDB.getSchema();
		}

		// 有特约，加费，保险计划变更为次标准体
		if (!tLCCUWMasterSchema.getSpecFlag().equals("0")
				|| !tLCCUWMasterSchema.getChangePolFlag().equals("0")) {
			if (mLMUWSet.size() > 0) {
				for (int i = 1; i <= mLMUWSet.size(); i++) {
					LMUWSchema tLMUWSchema = new LMUWSchema();
					tLMUWSchema = mLMUWSet.get(i);

					mCalCode = tLMUWSchema.getCalCode(); // 次标准体计算公式代码

					String tempuwgrade = CheckPol();

					if (tempuwgrade != null) {
						if (mUWPopedom.compareTo(tempuwgrade) < 0) {
							CError tError = new CError();
							tError.moduleName = "UWContManuNormGChkBL";
							tError.functionName = "prepareAllPol";
							tError.errorMessage = "无此次标准体投保件核保权限，需要上报上级核保师!";
							this.mErrors.addOneError(tError);

							return false;
						}
					}
				}
			}
		}

		return true;
	}

	/**
	 * 拒保，撤单校验核保员级别 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkUserGrade() {
		CheckKinds("2");

		if (mLMUWSet.size() > 0) {
			for (int i = 1; i <= mLMUWSet.size(); i++) {
				LMUWSchema tLMUWSchema = new LMUWSchema();
				tLMUWSchema = mLMUWSet.get(i);

				mCalCode = tLMUWSchema.getCalCode(); // 延期拒保计算公式代码

				String tempuwgrade = CheckPol();

				if (tempuwgrade != null) {
					if (mUWPopedom.compareTo(tempuwgrade) < 0) {
						CError tError = new CError();
						tError.moduleName = "UWContManuNormGChkBL";
						tError.functionName = "prepareAllPol";
						tError.errorMessage = "无此单拒保，延期权限，需上报上级核保师!";
						this.mErrors.addOneError(tError);

						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * 校验是否有返回业务员问题件
	 * 
	 * @return
	 */
	private boolean checkAgentQuest(LCPolSchema tLCPolSchema) {
		String tsql = "";
		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();

		tsql = "select * from lcissuepol where proposalno = '"
				+ "?proposalno?"
				+ "' and backobjtype = '2' and replyresult is null and needprint = 'Y'";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tsql);
		sqlbv5.put("proposalno", tLCPolSchema.getProposalNo());
		tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv5);

		if (tLCIssuePolSet.size() > 0) {
			mAgentPrintFlag = "1";
			mAgentQuesFlag = "1";
		}

		return true;
	}

	/**
	 * 准备保单信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareCont() {
		mLCContSchema.setUWFlag(mUWFlag);
		mLCContSchema.setUWOperator(mOperator);
		mLCContSchema.setUWDate(PubFun.getCurrentDate());
		mLCContSchema.setUWTime(PubFun.getCurrentTime());
		mLCContSchema.setOperator(mOperator);
		mLCContSchema.setModifyDate(PubFun.getCurrentDate());
		mLCContSchema.setModifyTime(PubFun.getCurrentTime());

		mLCContSet.clear();
		mLCContSet.add(mLCContSchema);

		// 拒保退费
		if (mUWFlag.equals("1")) {
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setContNo(mContNo);

			LCPolSet tLCPolSet = tLCPolDB.query();

			if (tLCPolDB.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tLCPolDB.mErrors);

				CError tError = new CError();
				tError.moduleName = "UWContManuNormGChkBL";
				tError.functionName = "prepareCont";
				tError.errorMessage = "LCPol表取数失败!";
				this.mErrors.addOneError(tError);

				return false;
			}

			int n = tLCPolSet.size();
			LCPolSchema tLCPolSchema = new LCPolSchema();

			for (int i = 1; i <= n; i++) {
				GlobalInput tG = new GlobalInput();
				tG.Operator = "001";
				tG.ManageCom = "86000000";

				// 投保单列表
				LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();

				boolean flag = false;

				tLCPolSchema = tLCPolSet.get(1);

				LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();

				tLCPolSchema.setUWFlag(mUWFlag);
				tLCPolSchema.setRemark(mUWIdea);
				tLCUWMasterSchema.setUWIdea(mUWIdea);

				LCPolSet tTransLCPolSet = new LCPolSet();
				tTransLCPolSet.add(tLCPolSchema);
				tLCUWMasterSet.add(tLCUWMasterSchema);

				// 准备传输数据 VData
				VData tVData = new VData();
				tVData.add(tTransLCPolSet);
				tVData.add(tLCUWMasterSet);
				tVData.add(tG);

				// 数据传输
				UWManuNormGChkUI tUWManuNormGChkUI = new UWManuNormGChkUI();

				if (tUWManuNormGChkUI.submitData(tVData, "INSERT") == false) {
					this.mErrors.copyAllErrors(tUWManuNormGChkUI.mErrors);
					this.mErrors.addOneError(new CError("险种保单拒保失败！"));

					return false;
				}
			}
		}

		return true;
	}

	/**
	 * 准备核保信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareUW() {
		LCCUWMasterSchema tLCCUWMasterSchema = new LCCUWMasterSchema();
		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setContNo(mContNo);
		tLCCUWMasterDB.setProposalContNo(mContNo);

		LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();
		tLCCUWMasterSet = tLCCUWMasterDB.query();

		if (tLCCUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWContManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCUWMaster表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		int n = tLCCUWMasterSet.size();
		logger.debug("该投保单的核保主表记录条数:   " + n);

		if (n == 1) {
			tLCCUWMasterSchema = tLCCUWMasterSet.get(1);

			int uwno = tLCCUWMasterSet.get(1).getUWNo();
			uwno++;
			tLCCUWMasterSchema.setUWNo(uwno);
			tLCCUWMasterSchema.setPassFlag(mUWFlag); // 通过标志
			tLCCUWMasterSchema.setState(mUWFlag);
			tLCCUWMasterSchema.setUWIdea(mUWIdea);
			tLCCUWMasterSchema.setAutoUWFlag("2"); // 1 自动核保 2 人工核保

			tLCCUWMasterSchema.setAppGrade(mAppGrade);

			if (mUWFlag.equals("6")) {
				tLCCUWMasterSchema.setOperator(mUPUWCode); // 上报核保指定核保师功能的实现借助将当前核保师改为待核保师方式实现
			} else {
				tLCCUWMasterSchema.setOperator(mOperator);
			}
			// 操作员

			tLCCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

			// 延期
			if (mUWFlag.equals("2")) // 此代码冗余
			{
				tLCCUWMasterSchema.setPostponeDay(mpostday);
				tLCCUWMasterSchema.setPostponeDate(mvalidate);
			}

			// 条件承保
			if (mUWFlag.equals("3")) {
				tLCCUWMasterSchema.setSpecReason(mSpecReason); // 特约原因
				tLCCUWMasterSchema.setAddPremReason(mReason);
			}

			if (mUWFlag.equals("3")) {
				if (tLCCUWMasterSchema.getPrintFlag().equals("1")) {
					CError tError = new CError();
					tError.moduleName = "UWContManuNormGChkBL";
					tError.functionName = "prepareUW";
					tError.errorMessage = "已经发核保通知不可录入!";
					this.mErrors.addOneError(tError);

					return false;
				}

				tLCCUWMasterSchema.setSpecFlag("1");
			}
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWContManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCUWMaster表取数据不唯一!";
			this.mErrors.addOneError(tError);

			return false;
		}

		mLCCUWMasterSet.clear();
		mLCCUWMasterSet.add(tLCCUWMasterSchema);

		// 核保轨迹表
		LCCUWSubSchema tLCCUWSubSchema = new LCCUWSubSchema();
		LCCUWSubDB tLCCUWSubDB = new LCCUWSubDB();
		tLCCUWSubDB.setContNo(mContNo);

		LCCUWSubSet tLCCUWSubSet = new LCCUWSubSet();
		tLCCUWSubSet = tLCCUWSubDB.query();

		if (tLCCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWSubDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWContManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCUWSub表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		int m = tLCCUWSubSet.size();
		logger.debug("subcount=" + m);

		if (m > 0) {
			m++; // 核保次数
			tLCCUWSubSchema = new LCCUWSubSchema();
			tLCCUWSubSchema.setUWNo(m); // 第几次核保
			tLCCUWSubSchema.setContNo(tLCCUWMasterSchema.getContNo());
			tLCCUWSubSchema.setGrpContNo(tLCCUWMasterSchema.getGrpContNo());
			tLCCUWSubSchema.setProposalContNo(tLCCUWMasterSchema
					.getProposalContNo());
			tLCCUWSubSchema.setInsuredNo(tLCCUWMasterSchema.getInsuredNo());
			tLCCUWSubSchema.setInsuredName(tLCCUWMasterSchema.getInsuredName());
			tLCCUWSubSchema.setAppntNo(tLCCUWMasterSchema.getAppntNo());
			tLCCUWSubSchema.setAppntName(tLCCUWMasterSchema.getAppntName());
			tLCCUWSubSchema.setAgentCode(tLCCUWMasterSchema.getAgentCode());
			tLCCUWSubSchema.setAgentGroup(tLCCUWMasterSchema.getAgentGroup());
			tLCCUWSubSchema.setUWGrade(tLCCUWMasterSchema.getUWGrade()); // 核保级别
			tLCCUWSubSchema.setAppGrade(tLCCUWMasterSchema.getAppGrade()); // 申请级别
			tLCCUWSubSchema.setAutoUWFlag(tLCCUWMasterSchema.getAutoUWFlag());
			tLCCUWSubSchema.setState(tLCCUWMasterSchema.getState());
			tLCCUWSubSchema.setPassFlag(tLCCUWMasterSchema.getState());
			tLCCUWSubSchema.setPostponeDay(tLCCUWMasterSchema.getPostponeDay());
			tLCCUWSubSchema.setPostponeDate(tLCCUWMasterSchema
					.getPostponeDate());
			tLCCUWSubSchema.setUpReportContent(tLCCUWMasterSchema
					.getUpReportContent());
			tLCCUWSubSchema.setHealthFlag(tLCCUWMasterSchema.getHealthFlag());
			tLCCUWSubSchema.setSpecFlag(tLCCUWMasterSchema.getSpecFlag());
			tLCCUWSubSchema.setSpecReason(tLCCUWMasterSchema.getSpecReason());
			tLCCUWSubSchema.setQuesFlag(tLCCUWMasterSchema.getQuesFlag());
			tLCCUWSubSchema.setReportFlag(tLCCUWMasterSchema.getReportFlag());
			tLCCUWSubSchema.setChangePolFlag(tLCCUWMasterSchema
					.getChangePolFlag());
			tLCCUWSubSchema.setChangePolReason(tLCCUWMasterSchema
					.getChangePolReason());
			tLCCUWSubSchema.setAddPremReason(tLCCUWMasterSchema
					.getAddPremReason());
			tLCCUWSubSchema.setPrintFlag(tLCCUWMasterSchema.getPrintFlag());
			tLCCUWSubSchema.setPrintFlag2(tLCCUWMasterSchema.getPrintFlag2());
			tLCCUWSubSchema.setUWIdea(tLCCUWMasterSchema.getUWIdea());
			tLCCUWSubSchema.setOperator(tLCCUWMasterSchema.getOperator()); // 操作员
			tLCCUWSubSchema.setManageCom(tLCCUWMasterSchema.getManageCom());
			tLCCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			tLCCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			tLCCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tLCCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWSubDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWContManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCUWSub表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		mLCCUWSubSet.clear();
		mLCCUWSubSet.add(tLCCUWSubSchema);

		return true;
	}

	/**
	 * 检查是不是需要送核保通知书到打印队列
	 * 
	 * @return
	 */

	/*
	 * private String checkBackOperator(String tPrintFlag) { LCPolSet tLCPolSet =
	 * new LCPolSet();
	 * 
	 * for(int i = 1;i<= mLCPolSet.size();i++) { LCPolSchema tLCPolSchema = new
	 * LCPolSchema();
	 * 
	 * tLCPolSchema = mLCPolSet.get(i);
	 * 
	 * //有返回保户需要打印 //String tsql = "select * from lcpol where ProposalNo in (
	 * select ProposalNo from LCIssuePol where ((makedate >= (select
	 * max(makedate) from lcissuepol where backobjtype in ('1','4') and
	 * ProposalNo = '"+tLCPolSchema.getPolNo()+"' and makedate is not null)) or
	 * ((select max(makedate) from lcissuepol where backobjtype in ('1','4') and
	 * ProposalNo = '"+tLCPolSchema.getPolNo()+"') is null))" String tsql =
	 * "select * from lcpol where ProposalNo in ( select ProposalNo from
	 * LCIssuePol where 1 = 1 " + " and backobjtype = '3'" + " and ProposalNo =
	 * '"+tLCPolSchema.getPolNo()+"'" + " and makedate is not null" + " and
	 * replyresult is null" + " and needprint = 'Y')";
	 * 
	 * logger.debug("printchecksql:"+tsql); LCPolDB tLCPolDB = new
	 * LCPolDB(); LCPolSet t2LCPolSet = new LCPolSet(); t2LCPolSet =
	 * tLCPolDB.executeQuery(tsql); if(t2LCPolSet.size()>0) { tPrintFlag = "2"; }
	 * 
	 * 
	 * 
	 * //只返回给操作员,机构不打印 //tsql = "select * from lcpol where ProposalNo in (
	 * select ProposalNo from LCIssuePol where ((makedate >= (select
	 * max(makedate) from lcissuepol where backobjtype in ('2','3') and
	 * ProposalNo = '"+tLCPolSchema.getPolNo()+"' and makedate is not null)) or
	 * ((select max(makedate) from lcissuepol where backobjtype in ('3','2') and
	 * ProposalNo = '"+tLCPolSchema.getPolNo()+"') is null))" tsql = "select *
	 * from lcpol where ProposalNo in ( select ProposalNo from LCIssuePol where
	 * 1 = 1 " + " and backobjtype = '1'" + " and ProposalNo =
	 * '"+tLCPolSchema.getPolNo()+"'" + " and makedate is not null" + " and
	 * replyresult is null)" + " and ProposalNo not in ( select ProposalNo from
	 * LCIssuePol where 1 = 1 " + " and backobjtype in ('2','3')" + " and
	 * ProposalNo = '"+tLCPolSchema.getPolNo()+"'" + " and makedate is not null" + "
	 * and replyresult is null" + " and needprint = 'Y')" + " and ProposalNo not
	 * in ( select ProposalNo from LCIssuePol where 1 = 1 " + " and backobjtype =
	 * '4'" + " and ProposalNo = '"+tLCPolSchema.getPolNo()+"'" + " and makedate
	 * is not null" + " and replyresult is null)";
	 * 
	 * logger.debug("printchecksql2:"+tsql); tLCPolDB = new LCPolDB();
	 * t2LCPolSet = new LCPolSet();
	 * 
	 * t2LCPolSet = tLCPolDB.executeQuery(tsql); if(t2LCPolSet.size()>0) {
	 * //复核标记 tLCPolSchema.setApproveFlag("1"); }
	 * 
	 * tLCPolSet.add(tLCPolSchema);
	 *  } mLCPolSet.clear(); mLCPolSet.add(tLCPolSet);
	 * 
	 * if(tPrintFlag.equals("2")) { tPrintFlag = "0"; } else { tPrintFlag = "1"; }
	 * 
	 * return tPrintFlag; }
	 */

	/**
	 * 打印信息表
	 * 
	 * @return
	 */

	/*
	 * private boolean print() { String tIfPrintFlag = "0";
	 * 
	 * LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	 * 
	 * tLOPRTManagerSchema.setOtherNo(mPolNo);
	 * logger.debug("polno:"+mPolNo);
	 * tLOPRTManagerSchema.setManageCom(mLCContSchema.getManageCom());
	 * tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT);
	 * tLOPRTManagerSchema.setAgentCode(mLCContSchema.getAgentCode());
	 * tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_INDPOL);
	 * if(mUWFlag.equals("1")) //拒保通知书 {
	 * tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_DECLINE);
	 * tLOPRTManagerSchema.setOtherNo(mGetNoticeNo);
	 * tLOPRTManagerSchema.setStandbyFlag1(mPolNo); } if(mUWFlag.equals("2"))
	 * //延期通知书 { tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_DEFER);
	 * tLOPRTManagerSchema.setOtherNo(mGetNoticeNo);
	 * tLOPRTManagerSchema.setStandbyFlag1(mPolNo); } if(mUWFlag.equals("8"))
	 * //核保通知书 { tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_UW);
	 * //只有问题件的时候校验 tIfPrintFlag = "0"; if(mBackFlag.equals("1")) { //
	 * tIfPrintFlag = checkBackOperator(tIfPrintFlag); } }
	 * if(mUWFlag.equals("a")) //撤单 {
	 * tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_WITHDRAW);
	 * tLOPRTManagerSchema.setOtherNo(mGetNoticeNo);
	 * tLOPRTManagerSchema.setStandbyFlag1(mPolNo); }
	 * 
	 * 
	 * VData tVData = new VData(); PrintManagerBL tPrintManagerBL = new
	 * PrintManagerBL();
	 * 
	 * tVData.add(tLOPRTManagerSchema); tVData.add( mGlobalInput);
	 * 
	 * logger.debug("Start PrintManagerBL Submit...");
	 * if(tIfPrintFlag.equals("0")) //只返回给操作员问题件无需发核保通知书 {
	 * if(!tPrintManagerBL.submitData(tVData,"REQUEST")) { // @@错误处理
	 * this.mErrors.copyAllErrors(tPrintManagerBL.mErrors); CError tError = new
	 * CError(); tError.moduleName = "UWContManuNormGChkBL"; tError.functionName =
	 * "print"; tError.errorMessage = "数据提交失败!"; this.mErrors
	 * .addOneError(tError) ; return false; } }
	 * 
	 * mLOPRTManagerSet.add(tLOPRTManagerSchema); return true; }
	 */

	/**
	 * 返回业务员问题件件送打印队列
	 * 
	 * @return
	 */

	/*
	 * private boolean printAgent() { LOPRTManagerSchema tLOPRTManagerSchema =
	 * new LOPRTManagerSchema();
	 * 
	 * tLOPRTManagerSchema.setOtherNo(mPolNo);
	 * tLOPRTManagerSchema.setManageCom(mLCContSchema.getManageCom());
	 * tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT);
	 * tLOPRTManagerSchema.setAgentCode(mLCContSchema.getAgentCode());
	 * tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_INDPOL);
	 * tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_AGEN_QUEST);
	 * 
	 * VData tVData = new VData(); PrintManagerBL tPrintManagerBL = new
	 * PrintManagerBL();
	 * 
	 * tVData.add(tLOPRTManagerSchema); tVData.add( mGlobalInput);
	 * 
	 * logger.debug("Start PrintManagerBL Submit...");
	 * if(!tPrintManagerBL.submitData(tVData,"REQUEST")) { // @@错误处理
	 * this.mErrors.copyAllErrors(tPrintManagerBL.mErrors); CError tError = new
	 * CError(); tError.moduleName = "UWContManuNormGChkBL"; tError.functionName =
	 * "print"; tError.errorMessage = "数据提交失败!"; this.mErrors
	 * .addOneError(tError) ; return false; }
	 * 
	 * return true; }
	 */

	/**
	 * 延期承保 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean TimeAccept() {
		return true;
	}

	/**
	 * 待上级核保 输出：如果发生错误则返回false,否则返回true
	 */

	/*
	 * private boolean uplevel() { LCUWErrorDB tLCUWErrorDB = new LCUWErrorDB();
	 * tLCUWErrorDB.setPolNo(mLCContSchema.getPolNo()); String tpolno =
	 * mLCPolSchema.getPolNo(); String tsql = "select * from lcuwerror where
	 * polno = '"+tpolno.trim()+"' and uwno = (select max(uwno) from lcuwerror
	 * where polno = '"+tpolno.trim()+"')"; LCUWErrorSet tLCUWErrorSet =
	 * tLCUWErrorDB.executeQuery(tsql); String tcurrgrade = "A"; String
	 * terrgrade = ""; logger.debug(" in uplevel()"); int errno =
	 * tLCUWErrorSet.size(); int j = 0; if (errno > 0) { for( int i = 1; i <=
	 * errno; i++) {
	 * 
	 * LCUWErrorSchema tLCUWErrorSchema = new LCUWErrorSchema();
	 * tLCUWErrorSchema = tLCUWErrorSet.get(i); terrgrade =
	 * tLCUWErrorSchema.getUWGrade();
	 * logger.debug("上报级别:terrgrade"+terrgrade); if (j == 0 &&
	 * mUWPopedom.compareTo(terrgrade) < 0) { j++; tcurrgrade = terrgrade; }
	 * else { if(mUWPopedom.compareTo(terrgrade) < 0 &&
	 * terrgrade.compareTo(tcurrgrade) > 0) { tcurrgrade = terrgrade; } }
	 * logger.debug("上报级别:tcurrgrade"+tcurrgrade); } mAppGrade =
	 * tcurrgrade; } logger.debug("上报级别:mAppGrade"+mAppGrade); if(errno ==
	 * 0||(mUWPopedom.compareTo(mAppGrade) >= 0&&mUWPopedom.compareTo("L") < 0)) {
	 * char temp[]; char tempgrade; temp = mUWPopedom.toCharArray(); tempgrade =
	 * (char)((int)temp[0]+1); logger.debug("上报级别:"+tempgrade); mAppGrade =
	 * String.valueOf(tempgrade); } //指定上报级别
	 * logger.debug("上报级别:mUPUWCode+mOperator"+mUPUWCode+mOperator);
	 * if(mUPUWCode!=null&&!mUPUWCode.equals(mOperator) ) { LDUserDB tLDUserDB =
	 * new LDUserDB(); tsql = "select * from lduser where usercode =
	 * '"+mUPUWCode+"'"; LDUserSet tLDUserSet = tLDUserDB.executeQuery(tsql);
	 * if(tLDUserSet.size() != 1) { CError tError = new CError();
	 * tError.moduleName = "UWContManuNormGChkBL"; tError.functionName =
	 * "uplever"; tError.errorMessage = "指定核保师信息有误!"; this.mErrors
	 * .addOneError(tError) ; return false; } else {
	 * logger.debug("上报级别:mAppGrade+tLDUserSet.get(1).getUWPopedom()"+mAppGrade+tLDUserSet.get(1).getUWPopedom());
	 * if(mAppGrade.compareTo(tLDUserSet.get(1).getUWPopedom())>0 ) { CError
	 * tError = new CError(); tError.moduleName = "UWContManuNormGChkBL";
	 * tError.functionName = "uplever"; tError.errorMessage = "指定核保师级别太底!";
	 * this.mErrors .addOneError(tError) ; return false; } else mAppGrade =
	 * tLDUserSet.get(1).getUWPopedom(); }
	 *  } logger.debug("上报级别:mAppGrade"+mAppGrade);
	 * 
	 * //撤销核保申请锁 mLDSysTraceSchema.setPolNo(mPolNo);
	 * mLDSysTraceSchema.setOperator(mOperator);
	 * mLDSysTraceSchema.setPolState(1001);
	 * 
	 * mLDSysTraceSet.add(mLDSysTraceSchema); return true; }
	 */

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		mMap.put(mAllLCContSet, "UPDATE");
		mMap.put(mAllLCCUWMasterSet, "DELETE&INSERT");
		mMap.put(mAllLCCUWSubSet, "INSERT");
		mResult.clear();
		mResult.add(mMap);
	}
}
