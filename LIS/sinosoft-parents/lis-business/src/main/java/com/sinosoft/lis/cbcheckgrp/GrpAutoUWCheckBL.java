package com.sinosoft.lis.cbcheckgrp;

import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.sinosoft.ibrms.GrpRuleUI;
import com.sinosoft.ibrms.SQLTaskResult;
import com.sinosoft.ibrms.bom.BOMAgent;
import com.sinosoft.ibrms.bom.BOMAppnt;
import com.sinosoft.ibrms.bom.BOMBnf;
import com.sinosoft.ibrms.bom.BOMCont;
import com.sinosoft.ibrms.bom.BOMInsured;
import com.sinosoft.ibrms.bom.BOMMainPol;
import com.sinosoft.ibrms.bom.BOMPol;
import com.sinosoft.ibrms.bom.BOMSubPol;
import com.sinosoft.ibrms.bom.BOMSubPol2;
import com.sinosoft.lis.cbcheck.AutoUWCheckBL;
import com.sinosoft.lis.cbcheck.UWFBCal;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LCCUWErrorDB;
import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCIndUWErrorDB;
import com.sinosoft.lis.db.LCIndUWMasterDB;
import com.sinosoft.lis.db.LCIndUWSubDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCUWErrorDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LDOccupationDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCCUWErrorSchema;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCCUWSubSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCIndUWErrorSchema;
import com.sinosoft.lis.schema.LCIndUWMasterSchema;
import com.sinosoft.lis.schema.LCIndUWSubSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCUWErrorSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.tb.GlobalCheckSpot;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCCUWErrorSet;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.lis.vschema.LCCUWSubSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCIndUWErrorSet;
import com.sinosoft.lis.vschema.LCIndUWMasterSet;
import com.sinosoft.lis.vschema.LCIndUWSubSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCUWErrorSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 自动核保校验业务类
 * </p>
 * 
 * <p>
 * Description: 自动核保校验业务类
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HL
 * @version 6.0
 */
public class GrpAutoUWCheckBL {
	private static Logger logger = Logger.getLogger(GrpAutoUWCheckBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private static GlobalCheckSpot mGlobalCheckSpot = GlobalCheckSpot
			.getInstance();
	/** 往界面传输数据的容器 */
	MMap mMap = new MMap();
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private ExeSQL mExeSQL = new ExeSQL();
	private String theDate = "";

	private List tPolList = new ArrayList();// 存放险种自核不通过信息的list
	private List tContList = new ArrayList();// 存放合同自核不通过提示信息的list
	private List tInsList = new ArrayList();// 存放被保人自核不通过提示信息的list

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mAllPolPassFlag = "9";
	/** 业务数据操作字符串 */
	// private String mMissionID;
	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();
	private String mPolPassFlag = "0"; // 险种通过标记 初始为未核保
	private String mContPassFlag = "0"; // 合同通过标记 初始为未核保
	private String mInsPassFlag = "0";
	private String mUWGrade = "";// 规则引擎接口返回的核保级别
	private String mContNo = "";
	private String mPContNo = "";
	private String mOldPolNo = "";// 获得的保单号码
	private boolean FirstUW = true;
	private String ProductSaleFlag = "0";// 产品上市停售规则标记，如果不符合规则则置为1
	private int LCCUWNO = 0;
	private int LCUWNO = 0;
	private int LCINDUWNO = 0;
	private int LCBatchNo = 0;
	private int LCCBatchNo = 0;
	private int LCIndBatchNo = 0;

	private LCContSet mAllLCContSet = new LCContSet();
	private LCPolSet mAllLCPolSet = new LCPolSet();
	private LCInsuredSet mAllInsuredSet = new LCInsuredSet();

	/** 2008-11-20，自动核保改调用保险业务规则管理功能来完成自动核保 */
	private GrpRuleUI mGrpRuleUI = new GrpRuleUI();//

	/** 初始化保险业务规则管理所需要的相应的数据 */

	/** 合同核保主表 */
	private LCCUWMasterSet mLCCUWMasterSet = new LCCUWMasterSet();
	private LCCUWMasterSet mAllLCCUWMasterSet = new LCCUWMasterSet();

	/** 合同核保子表 */
	private LCCUWSubSet mLCCUWSubSet = new LCCUWSubSet();
	private LCCUWSubSet mAllLCCUWSubSet = new LCCUWSubSet();

	/** 合同核保错误信息表 */
	private LCCUWErrorSet mLCCUWErrorSet = new LCCUWErrorSet();
	private LCCUWErrorSet mAllLCCUWErrorSet = new LCCUWErrorSet();

	/** 各险种核保主表 */
	private LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();
	private LCUWMasterSet mAllLCUWMasterSet = new LCUWMasterSet();

	/** 各险种核保子表 */
	private LCUWSubSet mLCUWSubSet = new LCUWSubSet();
	private LCUWSubSet mAllLCUWSubSet = new LCUWSubSet();

	/** 被保人核保主表 */
	private LCIndUWMasterSet mLCIndUWMasterSet = new LCIndUWMasterSet();

	/** 被保人核保子表 */
	private LCIndUWSubSet mLCIndUWSubSet = new LCIndUWSubSet();

	/** 被保人核保错误信息表 */
	private LCIndUWErrorSet mLCIndUWErrorSet = new LCIndUWErrorSet();

	/** 核保错误信息表 */
	private LCUWErrorSet mLCUWErrorSet = new LCUWErrorSet();

	/** 发送拒保通知书 */
	LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private String hierarhy = ""; // 核保级别 add by yaory
	private String reDistribute = ""; // 分保标志 add by yaory
	private double LSumDangerAmnt = 0; // 同一被保险人下寿险类的累计危险保额add by yaory
	private double DSumDangerAmnt = 0; // 同一被保险人下重大疾病类的累计危险保额add by yaory
	private double ASumDangerAmnt = 0; // 同一被保险人下人身意外伤害类的累计危险保额add by yaory
	private double MSumDangerAmnt = 0; // 同一被保险人下人身意外医疗类的累计危险保额add by yaory
	private double SSumDangerAmnt = 0; // 同一被保险人下住院医疗类的累计危险保额add by yaory
	private double SSumDieAmnt = 0; // 同一被保险人下累计身故风险保额
	private double AllSumAmnt = 0;
	private String mBankInsu = "0";
	private int insuredCount = 0;
	private boolean HasAddFreeFlag = false;

	// private int theCount = 0;
	public GrpAutoUWCheckBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            输入的数据
	 * @param cOperate
	 *            数据操作
	 * @return boolean
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

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData(mLCContSchema)) {
			return false;
		}

		logger.debug("dealData successful!");

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("Start  Submit...");

		// 提交数据
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCInsuredUWBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param tLCContSchema
	 *            输入数据
	 * @return boolean
	 */
	private boolean dealData(LCContSchema tLCContSchema) {
		/** 将该合同下的所有信息查询出来,并将相应的数据转储到相应的BOM中 */
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSchema tLCPolSchema = null;

		mContNo = tLCContSchema.getContNo(); // 获得保单号
		mPContNo = tLCContSchema.getProposalContNo();
		tLCPolDB.setContNo(mContNo);
		mAllLCPolSet = tLCPolDB.query();
		int polCount = mAllLCPolSet.size();

		// /**
		// * 自核前首先校验险种是否符合产品上市停售规则，
		// * 如不符合不调用规则引擎接口，ProductSaleFlag置为1
		// * 直接流转到销售限制节点
		// * */
		// VData PolVData = new VData();
		// PolVData.add(mAllLCPolSet);
		// ProductSaleControlBL tProductSaleControlBL = new
		// ProductSaleControlBL();
		// if(!tProductSaleControlBL.submitData(PolVData, "")){
		// int tCount = tProductSaleControlBL.mErrors.getErrorCount();
		// for(int i=1; i <= tCount;i++){
		// CError.buildErr(this,
		// tProductSaleControlBL.mErrors.getFirstError().toString());
		// }
		// }
		// VData Result = tProductSaleControlBL.getResult();
		// if(Result.size()>0){
		// for(int i=0;i<Result.size();i++){
		// String tRe = (String)Result.get(i);
		// logger.debug(tRe);
		// }
		// ProductSaleFlag="1";
		// }else{
		// LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		// LCAppntDB tLCAppntDB = new LCAppntDB();
		// tLCAppntDB.setContNo(mContNo);
		// if(!tLCAppntDB.getInfo()){
		// CError.buildErr(this, "查询投保人信息失败！");
		// return false;
		// }

		// LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(mContNo);
		mAllInsuredSet = tLCInsuredDB.query();

		// //tongmeng 2009-04-28 add
		// //对于投保人豁免险121301 需要复制投保人数据作为被保人
		// //借用grpcontno记录是否是复制的被保人
		// LCInsuredSet tFreeLCInsuredSet = new LCInsuredSet();
		// LCInsuredDB tFreeLCInsuredDB = new LCInsuredDB();
		// String tSQL_Appnt =
		// "select * from lcinsured a where  a.contno='"+mContNo+"' and "+mAllInsuredSet.size()+"<=1 "
		// +
		// " and exists (select '1' from lcpol where contno=a.contno and riskcode='121301' ) ";
		// tFreeLCInsuredSet = tFreeLCInsuredDB.executeQuery(tSQL_Appnt);
		// if(tFreeLCInsuredSet.size()>0)
		// {
		// LCInsuredSchema tempLCInsuredSchema = tFreeLCInsuredSet.get(1);
		// tempLCInsuredSchema.setGrpContNo("FREE");
		// tempLCInsuredSchema.setInsuredNo(tLCAppntDB.getAppntNo());
		// tempLCInsuredSchema.setRelationToAppnt("00");
		// tempLCInsuredSchema.setName(tLCAppntDB.getAppntName());
		// tempLCInsuredSchema.setSex(tLCAppntDB.getAppntSex());
		// tempLCInsuredSchema.setBirthday(tLCAppntDB.getAppntBirthday());
		// tempLCInsuredSchema.setIDType(tLCAppntDB.getIDType());
		// tempLCInsuredSchema.setIDNo(tLCAppntDB.getIDNo());
		// tempLCInsuredSchema.setNativePlace(tLCAppntDB.getNativePlace());
		// tempLCInsuredSchema.setNationality(tLCAppntDB.getNationality());
		// tempLCInsuredSchema.setRgtAddress(tLCAppntDB.getRgtAddress());
		// tempLCInsuredSchema.setMarriage(tLCAppntDB.getMarriage());
		// tempLCInsuredSchema.setMarriageDate(tLCAppntDB.getMarriageDate());
		// tempLCInsuredSchema.setHealth(tLCAppntDB.getHealth());
		// tempLCInsuredSchema.setStature(tLCAppntDB.getStature());
		// tempLCInsuredSchema.setAvoirdupois(tLCAppntDB.getAvoirdupois());
		// tempLCInsuredSchema.setDegree(tLCAppntDB.getDegree());
		// tempLCInsuredSchema.setCreditGrade(tLCAppntDB.getCreditGrade());
		// tempLCInsuredSchema.setBankCode(tLCAppntDB.getBankCode());
		// tempLCInsuredSchema.setBankAccNo(tLCAppntDB.getBankAccNo());
		// tempLCInsuredSchema.setAccName(tLCAppntDB.getAccName());
		// tempLCInsuredSchema.setJoinCompanyDate(tLCAppntDB.getJoinCompanyDate());
		// tempLCInsuredSchema.setStartWorkDate(tLCAppntDB.getStartWorkDate());
		// tempLCInsuredSchema.setPosition(tLCAppntDB.getPosition());
		// tempLCInsuredSchema.setSalary(tLCAppntDB.getSalary());
		// tempLCInsuredSchema.setOccupationCode(tLCAppntDB.getOccupationCode());
		// tempLCInsuredSchema.setOccupationType(tLCAppntDB.getOccupationType());
		// tempLCInsuredSchema.setWorkType(tLCAppntDB.getWorkType());
		// tempLCInsuredSchema.setPluralityType(tLCAppntDB.getPluralityType());
		// tempLCInsuredSchema.setSmokeFlag(tLCAppntDB.getSmokeFlag());
		// tempLCInsuredSchema.setLicense(tLCAppntDB.getLicense());
		// tempLCInsuredSchema.setLicenseType(tLCAppntDB.getLicenseType());
		// tempLCInsuredSchema.setSocialInsuFlag(tLCAppntDB.getSocialInsuFlag());
		// HasAddFreeFlag = true;
		// mAllInsuredSet.add(tempLCInsuredSchema);
		// }

		insuredCount = mAllInsuredSet.size();
		// LCPOL的UWFLAG需要特殊处理，如果是第一次自核，无论是否通过UWFLAG都会置成9
		// 06-26 由于外包保存会将主险的uwflag置为9 故将此处注掉，改查lbmission
		// for(int a=1;a<=mAllLCPolSet.size();a++){
		// if(mAllLCPolSet.get(a).getUWFlag()==null||"".equals(mAllLCPolSet.get(a).getUWFlag())
		// ||"0".equals(mAllLCPolSet.get(a).getUWFlag())){
		// FirstUW=true;
		// }else{
		// FirstUW=false;
		// }
		// }
		// String tFirstSql =
		// "select count(1) from lbmission where missionprop1='"+mContNo+"' and activityid='0000001003'";
		String tFirstSql = "select count(1) from lbmission where missionprop1='"
				+ mContNo
				+ "' and activityid  in (select activityid from lwactivity  where functionid ='10010005')";

		ExeSQL firstExeSQL = new ExeSQL();
		String tFirst = firstExeSQL.getOneValue(tFirstSql);
		if (tFirst != null && !tFirst.equals("")
				&& Integer.parseInt(tFirst) > 0) {
			FirstUW = false;
		} else {
			FirstUW = true;
		}

		LCBnfSet tLCBnfSet = new LCBnfSet();
		LCBnfDB tLCBnfDB = new LCBnfDB();
		tLCBnfDB.setContNo(mContNo);
		tLCBnfSet = tLCBnfDB.query();
		int bnfCount = tLCBnfSet.size();

		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(tLCContSchema.getAgentCode());
		boolean tAgent = true;
		if (!tLAAgentDB.getInfo()) {
			logger.debug("未查询到代理人信息！");
			tAgent = false;
		}

		String tMainPolSql = "select * from lcpol where mainpolno=polno and contno='"
				+ mContNo + "'";
		LCPolSet tmainLCPolSet = new LCPolSet();
		tmainLCPolSet = tLCPolDB.executeQuery(tMainPolSql);
		int mainCount = tmainLCPolSet.size();

		String tSubPolSql = "select * from lcpol where mainpolno!=polno and contno='"
				+ mContNo + "'";
		LCPolSet tSubLCPolSet = new LCPolSet();
		tSubLCPolSet = tLCPolDB.executeQuery(tSubPolSql);
		int subCount = tSubLCPolSet.size();

		// 准备BOMCont数据
		BOMCont cont = new BOMCont();
		cont = DealBOMCont(tLCContSchema);

		// 准备被保人BOMAppnt数据
		// BOMAppnt appnt = new BOMAppnt();
		// ???
		// appnt = DealBOMAppnt(tLCAppntDB.getSchema(),tLCContSchema);

		// 准备代理人BOMAgent数据
		BOMAgent agent = new BOMAgent();
		if (tAgent) {
			agent = DealBOMAgent(tLAAgentDB.getSchema());
		}

		// 准备被保人BOMInsured数据
		BOMInsured[] insureds = new BOMInsured[insuredCount];// 为适用多被保人
		insureds = DealBOMInsured(mAllInsuredSet, tLCContSchema);

		// 准备险种BOMPol数据
		BOMPol[] pols = new BOMPol[polCount];// 多个险种
		pols = DealBOMPol(mAllLCPolSet, polCount, insureds);

		// 准备受益人BOMBnf数据
		BOMBnf[] bnfs = new BOMBnf[bnfCount];// 多个受益人
		bnfs = DealBOMBnf(tLCBnfSet, bnfCount, insureds);

		// 准备主险BOMMainPol数据
		BOMMainPol[] mainpols = new BOMMainPol[mainCount];// 多主险
		mainpols = DealBOMMainPol(tmainLCPolSet, insureds);

		// 处理附加险，规则引擎需要传入两个附加险的BOM，以对应录入磁条的“附加险”、“另一附加险”，而传入的两个附加险BOM只需一样就可以
		BOMSubPol[] subpols = new BOMSubPol[subCount]; // 多个附加险
		subpols = DealBOMSubPol(tSubLCPolSet, mainpols);
		BOMSubPol2[] subpol2s = new BOMSubPol2[subCount];
		subpol2s = DealBOMSubPol2(tSubLCPolSet, mainpols);

		// 调用规则引擎的接口 如果返回的List的size为0则表示自核通过
		List tList = mGrpRuleUI.AutoUWIndUI(cont, agent, insureds, pols,
				mainpols, subpols, subpol2s, bnfs);
		if (this.HasAddFreeFlag) {
			// 如果包含投保人豁免险,在之前处理完毕之后,需要修改下数据.把投保人从被保人集合中拿出去
			LCInsuredSet tempLCInsuredSet = new LCInsuredSet();
			for (int i = 1; i <= mAllInsuredSet.size() - 1; i++) {
				LCInsuredSchema tempLCInsuredSchema = new LCInsuredSchema();
				tempLCInsuredSchema.setSchema(mAllInsuredSet.get(i));
				tempLCInsuredSet.add(tempLCInsuredSchema);

			}
			mAllInsuredSet.clear();
			mAllInsuredSet.add(tempLCInsuredSet);

			// mAllInsuredSet.remove(aSchema)
		}

		if (tList.size() == 0) {
			// 自核通过
			mPolPassFlag = "9";
			mContPassFlag = "9";
			mInsPassFlag = "9";
			for (int a = 1; a <= mAllLCPolSet.size(); a++) {
				mAllLCPolSet.get(a).setUWCode(mOperator);
				mAllLCPolSet.get(a).setUWDate(PubFun.getCurrentDate());
				mAllLCPolSet.get(a).setUWFlag(mPolPassFlag);
				mAllLCPolSet.get(a).setUWTime(PubFun.getCurrentTime());
				mAllLCPolSet.get(a).setModifyDate(PubFun.getCurrentDate());
				mAllLCPolSet.get(a).setModifyTime(PubFun.getCurrentTime());
			}

			// if(!HasAddFreeFlag)
			{
				for (int a = 1; a <= mAllInsuredSet.size(); a++) {
					mAllInsuredSet.get(a).setUWCode(mOperator);
					mAllInsuredSet.get(a).setUWDate(PubFun.getCurrentDate());
					mAllInsuredSet.get(a).setUWFlag(mInsPassFlag);
					mAllInsuredSet.get(a).setUWTime(PubFun.getCurrentTime());
					mAllInsuredSet.get(a)
							.setModifyDate(PubFun.getCurrentDate());
					mAllInsuredSet.get(a)
							.setModifyTime(PubFun.getCurrentTime());
				}
			}
			// else
			// {
			// for (int a = 1; a <= mAllInsuredSet.size()-1; a++) {
			// mAllInsuredSet.get(a).setUWCode(mOperator);
			// mAllInsuredSet.get(a)
			// .setUWDate(PubFun.getCurrentDate());
			// mAllInsuredSet.get(a).setUWFlag(mInsPassFlag);
			// mAllInsuredSet.get(a)
			// .setUWTime(PubFun.getCurrentTime());
			// mAllInsuredSet.get(a).setModifyDate(
			// PubFun.getCurrentDate());
			// mAllInsuredSet.get(a).setModifyTime(
			// PubFun.getCurrentTime());
			// }
			// }
			mLCContSchema.setUWFlag(mContPassFlag);
			mLCContSchema.setUWOperator(mOperator);
			mLCContSchema.setUWDate(PubFun.getCurrentDate());
			mLCContSchema.setUWTime(PubFun.getCurrentTime());
			mLCContSchema.setModifyDate(PubFun.getCurrentDate());
			mLCContSchema.setModifyTime(PubFun.getCurrentTime());

			// 自核通过，处理MASTER表
			preparePolUW(mAllLCPolSet);
			dealOneCont(mLCContSchema);
			prepareInsured(mLCContSchema, mAllInsuredSet);

		} else {
			// 自核不通过
			for (int i = 0; i < tList.size(); i++) {
				SQLTaskResult SQLResult = (SQLTaskResult) tList.get(i);
				String result = SQLResult.getResult();
				String templateId = SQLResult.getTemplateId();// 模板号
				String tRuleid = SQLResult.getRuleid();//
				String tUWGrade1 = SQLResult.getUWLevel();// 规则定制时的核保级别
				String tRuleName = SQLResult.getRulename();// 规则名
				// 暂定抽检规则都以cj开头 如果该保单符合抽检规则 则当前最大抽检数加一
				if (tRuleName.indexOf("cj") != -1) {
					mGlobalCheckSpot.setCurrentCheckNum();
				}
				if ("X".equals(tUWGrade1)) {
					tUWGrade1 = "2";
				}
				Map m = SQLResult.getMapUsedBoms();
				BOMPol polResult = (BOMPol) m.get("BOMPol");
				BOMMainPol mainpolResult = (BOMMainPol) m.get("BOMMainPol");
				BOMSubPol subPolResult = (BOMSubPol) m.get("BOMSubPol");
				BOMSubPol2 subPol2Result = (BOMSubPol2) m.get("BOMSubPol2");
				BOMInsured insuredResult = (BOMInsured) m.get("BOMInsured");
				BOMAppnt AppntResult = (BOMAppnt) m.get("BOMAppnt");
				BOMCont contResult = (BOMCont) m.get("BOMCont");
				BOMAgent agentResult = (BOMAgent) m.get("BOMAgent");
				BOMBnf bnfResult = (BOMBnf) m.get("BOMBnf");

				logger.debug("tSQLResult::::::::" + tRuleid);
				logger.debug("result::::::::" + result);
				logger.debug("templateId::::::::" + templateId);
				// int tInsAge =
				// PubFun.calInterval2(mAllInsuredSet.get(1).getBirthday(),
				// tLCContSchema.getPolApplyDate(), "Y");

				// 取最高的核保级别决定核保师
				if (mUWGrade.compareTo(tUWGrade1) < 0) {
					mUWGrade = tUWGrade1;
				}
				logger.debug("最终得到的由规则引擎返回的核保级别是：" + mUWGrade);
				mPolPassFlag = "5";
				mInsPassFlag = "5";
				mContPassFlag = "5";
				// 将除lcpol外所有的核保结论置为不通过
				// lcpol需特殊处理，lcpol第一次核保核保状态需置为9，不是第一次核保才置为5
				for (int a = 1; a <= mAllLCPolSet.size(); a++) {
					if (FirstUW) {
						mAllLCPolSet.get(a).setUWFlag("9");
					} else {
						// tongmeng 2008-12-18 modify
						// 非第一次自核,不修改险种核保结论.
						// mAllLCPolSet.get(a).setUWFlag("5");
					}
					mAllLCPolSet.get(a).setUWCode(mOperator);
					mAllLCPolSet.get(a).setUWDate(PubFun.getCurrentDate());
					mAllLCPolSet.get(a).setUWTime(PubFun.getCurrentTime());
					mAllLCPolSet.get(a).setModifyDate(PubFun.getCurrentDate());
					mAllLCPolSet.get(a).setModifyTime(PubFun.getCurrentTime());
				}
				for (int a = 1; a <= mAllInsuredSet.size(); a++) {
					mAllInsuredSet.get(a).setUWCode(mOperator);
					mAllInsuredSet.get(a).setUWDate(PubFun.getCurrentDate());
					mAllInsuredSet.get(a).setUWFlag("5");
					mAllInsuredSet.get(a).setUWTime(PubFun.getCurrentTime());
					mAllInsuredSet.get(a)
							.setModifyDate(PubFun.getCurrentDate());
					mAllInsuredSet.get(a)
							.setModifyTime(PubFun.getCurrentTime());
				}
				mLCContSchema.setUWFlag("5");
				mLCContSchema.setUWOperator(mOperator);
				mLCContSchema.setUWDate(PubFun.getCurrentDate());
				mLCContSchema.setUWTime(PubFun.getCurrentTime());
				mLCContSchema.setModifyDate(PubFun.getCurrentDate());
				mLCContSchema.setModifyTime(PubFun.getCurrentTime());

				// tongmeng 2009-04-03 modify
				// 险种规则显示.
				// else
				if (polResult != null || mainpolResult != null
						|| subPolResult != null || subPol2Result != null) {
					String tPolNo = "";
					String tRiskCode = "";

					if (mainpolResult != null) {
						if (!(mainpolResult.getPolNo() == null || ""
								.equals(mainpolResult.getPolNo()))) {
							tPolNo = mainpolResult.getPolNo();
							tRiskCode = mainpolResult.getRiskCode();
						}
					}
					if (subPolResult != null) {
						if (!(subPolResult.getPolNo() == null || ""
								.equals(subPolResult.getPolNo()))) {
							tPolNo = subPolResult.getPolNo();
							tRiskCode = subPolResult.getRiskCode();
						}
					}
					if (subPol2Result != null) {
						if (!(subPol2Result.getPolNo() == null || ""
								.equals(subPol2Result.getPolNo()))) {
							tPolNo = subPol2Result.getPolNo();
							tRiskCode = subPol2Result.getRiskCode();
						}
					}

					if (polResult != null) {
						if (!(polResult.getPolNo() == null || ""
								.equals(polResult.getPolNo()))) {
							tPolNo = polResult.getPolNo();
							tRiskCode = polResult.getRiskCode();
						}
					}

					// 险种级规则
					TransferData polTransferData = new TransferData();
					polTransferData.setNameAndValue("result", result);
					polTransferData.setNameAndValue("templateId", templateId);
					polTransferData.setNameAndValue("tRuleid", tRuleid);
					polTransferData.setNameAndValue("tUWGrade1", tUWGrade1);
					polTransferData.setNameAndValue("polno", tPolNo);
					if (this.HasAddFreeFlag && tRiskCode.equals("121301")) {
						// 如果是投保人豁免险,并且客户号是投保人号
						// tongmeng 2009-05-06 modify
						// 替换结果文字.
						String tResult = (String) polTransferData
								.getValueByName("result");
						tResult = tResult.replaceAll("被保人|被保险人", "投保人");
						polTransferData.removeByName("result");
						polTransferData.setNameAndValue("result", tResult);
						// tContList.add(polTransferData);
						tPolList.add(polTransferData);
					} else {
						tPolList.add(polTransferData);
					}
					// for(int a=1;a<=mAllLCPolSet.size();a++){
					// if(polResult.getPolNo().equals(mAllLCPolSet.get(a).getPolNo())){
					// // LCUWMasterSchema tLCUWMasterSchema = new
					// LCUWMasterSchema();
					// // LCUWErrorSchema tLCUWErrorSchema = new
					// LCUWErrorSchema();
					// // LCUWSubSchema tLCUWSubSchema = new LCUWSubSchema();
					// mOldPolNo=polResult.getPolNo();
					// //错误信息写入个人险种核保错误信息表
					// preparePolUW(polResult,mAllLCPolSet.get(a),result,i,templateId,tRuleid);
					// }
					// }
				}

				// tongmeng 2009-04-03 modify
				// 被保人规则显示
				else

				if (!(insuredResult == null)) {
					// 被保人级规则
					TransferData insTransferData = new TransferData();
					insTransferData.setNameAndValue("result", result);
					insTransferData.setNameAndValue("templateId", templateId);
					insTransferData.setNameAndValue("tRuleid", tRuleid);
					insTransferData.setNameAndValue("tUWGrade1", tUWGrade1);
					insTransferData.setNameAndValue("insuredno",
							insuredResult.getInsuredNo());
					// if(this.HasAddFreeFlag&&tLCAppntDB.getAppntNo().equals(insuredResult.getInsuredNo()))
					// {
					// //如果是投保人豁免险,并且客户号是投保人号
					// //tongmeng 2009-05-06 modify
					// //替换结果文字.
					// String tResult =
					// (String)insTransferData.getValueByName("result");
					// tResult=tResult.replaceAll("被保人|被保险人", "投保人");
					// insTransferData.removeByName("result");
					// insTransferData.setNameAndValue("result", tResult);
					// tContList.add(insTransferData);
					// }
					// else
					// {
					tInsList.add(insTransferData);
					// }
					// for(int a=1;a<=mAllInsuredSet.size();a++){
					// if(mAllInsuredSet.get(a).getInsuredNo().equals(insuredResult.getInsuredNo())){
					// // LCIndUWMasterSchema tLCIndUWMasterSchema = new
					// LCIndUWMasterSchema();
					// //
					// prepareInsured(tLCContSchema,insuredResult,mAllInsuredSet.get(a),result,i,templateId,tRuleid);
					// }
					// }
				} else
				// 合同级规则
				if (AppntResult != null || contResult != null
						|| agentResult != null || bnfResult != null) {
					TransferData contTransferData = new TransferData();
					contTransferData.setNameAndValue("result", result);
					contTransferData.setNameAndValue("templateId", templateId);
					contTransferData.setNameAndValue("tRuleid", tRuleid);
					contTransferData.setNameAndValue("tUWGrade1", tUWGrade1);
					tContList.add(contTransferData);
					// 合同级规则
					// LCCUWMasterSchema tLCCUWMasterSchema = new
					// LCCUWMasterSchema();
					// LCCUWErrorSchema tLCCUWErrorSchema = new
					// LCCUWErrorSchema();
					// LCCUWSubSchema tLCCUWSubSchema = new LCCUWSubSchema();
					// dealOneCont(m,tLCContSchema,result,i,templateId,tRuleid);
				}

				if (AppntResult == null && contResult == null
						&& agentResult == null && bnfResult == null
						&& insuredResult == null && polResult == null
						&& mainpolResult == null && subPolResult == null
						&& subPol2Result == null) {
					CError.buildErr(this, "执行规则引擎内部出错！");
					return false;
				}
			}
			// 算出核保级别
			String tUWGrade2 = "";// 由系统计算出的核保级别
			String tempUWGrade = "";// 临时计算用的核保级别
			for (int a = 1; a <= mAllInsuredSet.size(); a++) {
				tempUWGrade = getUWGrade(mAllInsuredSet.get(a).getInsuredNo());
				if (tUWGrade2.compareTo(tempUWGrade) < 0) {
					tUWGrade2 = tempUWGrade;
				}
			}
			logger.debug("执行getUWGrade函数由SQL算出的核保级别是：" + tUWGrade2);
			// 比较规则定制界面录的核保级别与由SQL算法算出的核保级别，取最高的核保级别
			if (mUWGrade.compareTo(tUWGrade2) < 0) {
				mUWGrade = tUWGrade2;
			}
			if ("2".equals(mUWGrade)) {
				mUWGrade = "X";
			}
			hierarhy = mUWGrade;// 将最高的核保级别返回给服务类
			logger.debug("最终的核保级别是：" + mUWGrade);
			// 准备master表、sub表、error表的数据
			dealOneCont(tLCContSchema);
			prepareInsured(tLCContSchema, mAllInsuredSet);
			preparePolUW(mAllLCPolSet);
		}
		// }
		return true;
	}

	private BOMCont DealBOMCont(LCContSchema tLCContSchema) {
		BOMCont cont = new BOMCont();
		try {
			// tongmeng 2009-02-19 modify
			// 卡单取录入时间
			String tScanSql = "select MakeDate,maketime from ES_DOC_MAIN where doccode='"
					+ mContNo
					+ "' "
					+ " and busstype='TB' and subtype='UA001' ";
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = tExeSQL.execSQL(tScanSql);
			String tScanDate = null;
			String tScanTime = null;
			if (tSSRS.getMaxRow() > 0) {
				tScanDate = tSSRS.GetText(1, 1);// 扫描日期
				tScanTime = tSSRS.GetText(1, 2);
			} else {
				String tNoScanSql = "select makedate,maketime  from  lccont where contno='"
						+ mContNo + "' ";
				tSSRS = tExeSQL.execSQL(tNoScanSql);
				tScanDate = tSSRS.GetText(1, 1);
				tScanTime = tSSRS.GetText(1, 2);
			}
			logger.debug("扫描日期: " + tScanDate + " " + tScanTime);
			LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
			LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
			// tLCIssuePolDB.setContNo(mContNo);
			// tongmeng 2009-04-27 modify
			// 问题件只校验未发放的/
			String tSQL_Issue = "select * from lcissuepol where contno='"
					+ this.mContNo + "'  "
					+ " and state is null and needprint='Y'";

			tLCIssuePolSet = tLCIssuePolDB.executeQuery(tSQL_Issue);
			cont.setSellType(tLCContSchema.getSellType());// 出单方式
			// tongmeng 2009-02-19
			// 问题件信息先设置默认值
			cont.setComIssueFlag("0");// 机构问题件标记
			cont.setCustomerIssueFlag("0");// 客户问题件标记
			cont.setAppntIssueFlag("0");// 投保人问题件标记
			cont.setAgentIssueFlag("0");// 业务员问题件标记
			if (tLCIssuePolSet.size() > 0) {
				for (int i = 1; i <= tLCIssuePolSet.size(); i++) {
					if ("1".equals(tLCIssuePolSet.get(i).getBackObjType())) {
						cont.setComIssueFlag("1");
					} else if ("2".equals(tLCIssuePolSet.get(i)
							.getBackObjType())) {
						cont.setCustomerIssueFlag("1");
						if ("0".equals(tLCIssuePolSet.get(i).getQuestionObj())) {
							cont.setAppntIssueFlag("1");
						}
					} else if ("3".equals(tLCIssuePolSet.get(i)
							.getBackObjType())) {
						cont.setAgentIssueFlag("1");
					} else if ("4".equals(tLCIssuePolSet.get(i)
							.getBackObjType())) {
						cont.setComIssueFlag("1");
					}
				}
			}

			// tongmeng 2009-03-12 modify
			// 合同级别的保额,保费,份数取pol的汇总值
			String tSQL = "select nvl(sum(prem),0),nvl(sum(amnt),0),nvl(sum(mult),1) from lcpol where "
					+ " contno='"
					+ tLCContSchema.getContNo()
					+ "' "
					+ " and (uwflag is null or uwflag not in ('1','2','a'))";
			ExeSQL tAmntExeSQL = new ExeSQL();
			SSRS tAmntSSRS = new SSRS();
			tAmntSSRS = tAmntExeSQL.execSQL(tSQL);
			String tSumAmnt = "0";
			String tSumPrem = "0";
			String tSumMult = "0";
			if (tAmntSSRS.getMaxRow() > 0) {
				tSumPrem = tAmntSSRS.GetText(1, 1);
				tSumAmnt = tAmntSSRS.GetText(1, 2);
				tSumMult = tAmntSSRS.GetText(1, 3);
			}
			cont.setAmnt(new Double(tSumAmnt));
			cont.setAutoPayFlag(tLCContSchema.getAutoPayFlag());
			cont.setBankAccNo(tLCContSchema.getBankAccNo());
			cont.setBankCode(tLCContSchema.getBankCode());
			cont.setCardFlag(tLCContSchema.getCardFlag());
			cont.setContNo(tLCContSchema.getContNo());
			// 获得责任终止日期
			String tSQL_EndDate = " select to_char(max(enddate),'yyyy-mm-dd')||' 00:00:00',nvl(max(payintv/1),0) from lcpol a where contno='"
					+ mContNo
					+ "' "
					+ " and exists (select '1' from lmriskapp where subriskflag='M' "
					+ " and riskcode=a.riskcode) and mainpolno=polno ";
			ExeSQL tExeSQL_EndDate = new ExeSQL();
			SSRS tSSRS_EndDate = new SSRS();
			tSSRS_EndDate = tExeSQL_EndDate.execSQL(tSQL_EndDate);
			String tEndDate = "";
			String tPayIntv = "";
			if (tSSRS_EndDate.getMaxRow() > 0) {
				tEndDate = tSSRS_EndDate.GetText(1, 1);
				tPayIntv = tSSRS_EndDate.GetText(1, 2);
			}
			// String tEndDate = tExeSQL_EndDate.getOneValue(tSQL_EndDate);
			if (tEndDate != null && !tEndDate.equals("")) {
				cont.setEndDate(sdf.parse(tEndDate));// 责任终止日期
			}
			cont.setForceUWFlag(tLCContSchema.getForceUWFlag());
			cont.setInsuredPeoples(Double.valueOf(String.valueOf(insuredCount)));
			if (!((tLCContSchema.getMakeDate() == null) || ""
					.equals(tLCContSchema.getMakeDate()))) {
				theDate = tLCContSchema.getMakeDate() + " "
						+ tLCContSchema.getMakeTime();
				cont.setMakeDate(sdf.parse(theDate));
				// logger.debug("日期为："+theDate+"   转换后的："+sdf.parse(theDate));
			}
			cont.setManageCom(tLCContSchema.getManageCom());
			cont.setMult(new Double(tSumMult));
			cont.setOutPayFlag(tLCContSchema.getOutPayFlag());
			// tongmeng 2009-03-25 modify
			// 缴费间隔取保单下主险的最大缴费年期.
			// String tSQL_PayIntv =
			// "select nvl(max(payintv/1),0) from lcpol where contno='"+tLCContSchema.getContNo()+"' and mainpolno=polno";
			// ExeSQL tExeSQL_PayIntv = new ExeSQL();
			// String tValue = "";
			// tValue = tExeSQL_PayIntv.getOneValue(tSQL_PayIntv);
			if (tPayIntv == null || tPayIntv.equals("")) {
				cont.setPayIntv("0");
			} else {
				cont.setPayIntv(tPayIntv);
			}
			// tongmeng 2009-04-22 modify
			// 首期或者续期如果有一个为4(银行转账),就赋值为银行转账
			String tNewPayMode = tLCContSchema.getNewPayMode();
			String tPayMode = tLCContSchema.getPayLocation();
			String tAccName = "";
			if (tNewPayMode != null && tNewPayMode.equals("0")) {
				cont.setPayMode("0");
				tAccName = tLCContSchema.getNewAccName();
			} else if (tPayMode != null && tPayMode.equals("0")) {
				cont.setPayMode("0");
				tAccName = tLCContSchema.getAccName();
			} else {
				cont.setPayMode(tLCContSchema.getPayMode());
			}
			cont.setAccName(tAccName);
			if (!((tLCContSchema.getPolApplyDate() == null) || ""
					.equals(tLCContSchema.getPolApplyDate()))) {
				theDate = tLCContSchema.getPolApplyDate() + " 00:00:00";
				cont.setPolApplyDate(sdf.parse(theDate));
				logger.debug(sdf.parse(theDate));
			}
			cont.setPrem(new Double(tSumPrem));
			if (!(tLCContSchema.getRemark() == null || "".equals(tLCContSchema
					.getRemark()))) {
				cont.setRemarkFlag("1");
			} else {
				cont.setRemarkFlag("0");
			}
			cont.setRnewFlag(String.valueOf(tLCContSchema.getRnewFlag()));
			cont.setSaleChnl(tLCContSchema.getSaleChnl());
			// 如果时间为空则默认为00:00:00
			if (!((tScanDate == null) || "".equals(tScanDate))) {
				if (!((tScanTime == null) || "".equals(tScanTime))) {
					theDate = tScanDate + " " + tScanTime;
				} else {
					theDate = tScanDate + " 00:00:00";
				}
				cont.setScanDate(sdf.parse(theDate));// 扫描日期
			}
			String tOldPayPremSql = "select decode(sum(InputPrem), null, '0', sum(InputPrem))"
					+ "from lcpoloriginal where contno ='" + mContNo + "'";
			String OldPayPrem = tExeSQL.getOneValue(tOldPayPremSql);
			cont.setContPrem(Double.valueOf(OldPayPrem));// 填单保费
			cont.setRealPrem(new Double(tSumPrem));// 应交保费
			String tSQL_TempFee = "select nvl(sum(paymoney),0) from ljtempfee where tempfeetype='1' "
					+ " and otherno='" + mContNo + "'";
			String tTempFee = tExeSQL.getOneValue(tSQL_TempFee);
			cont.setTempfee(new Double(tTempFee));// 暂收保费

			// 2009-2-11 ln add 体检医院名称与系统定义不一致
			String tSqlNew = " select count(1) from lcpenotice m where 1=1 and contno='"
					+ mContNo
					+ "'"
					// + " and hospitcode is not null "
					+ " and not exists (select 1 from ldhospital where hospitcode=m.hospitcode and trim(hospitalname)=m.peaddress "
					+ " and (hosstate is null or hosstate='0'))";
			String tValue_Hos = "";
			tValue_Hos = tExeSQL.getOneValue(tSqlNew);
			if (tValue_Hos != null && !tValue_Hos.equals("")
					&& Integer.parseInt(tValue_Hos) > 0) {
				cont.setHospitalCodePro("1");// 体检医院名称与系统定义不一致
			} else {
				cont.setHospitalCodePro("0");
			}

			// 2009-2-11 ln add 个险渠道有同一业务员且同一险种且同一扫描日期的保单关联份数
			tSqlNew = "select nvl(max(count(distinct(contno))),0)"
					+ " from ("
					+ " select contno,riskcode from lcpol l where 1=1"
					+ " and salechnl='02' "
					+ " and agentcode = '"
					+ tLCContSchema.getAgentCode()
					+ "' "
					+ " and exists (select 1 from es_doc_main where doccode=l.prtno "
					+ " and busstype='TB' and subtype='UA001' "
					+ " and makedate=(select min(makedate) from es_doc_main where doccode='"
					+ tLCContSchema.getPrtNo()
					+ "' "
					+ " and busstype='TB' and subtype='UA001')) "
					+ " and riskcode in (select distinct riskcode from lcpol where contno='"
					+ mContNo + "')) group by riskcode ";
			String tResultNew = tExeSQL.getOneValue(tSqlNew);
			// cont.setGrpCont(Double.valueOf(tResultNew));

			// 2009-2-11 ln add 核保结论
			cont.setUWFlag(tLCContSchema.getUWFlag());

			// 2009-3-16 duanyh
			// select * from es_doc_def;
			// 新系统规则（根据扫描类代码校验）

			// tongmeng 2009-04-07 modify
			String tSQL_DocAll = "select decode(nvl(sum(case when A.x='UR201' then 1 else 0 end),0),0,0,1), "
					+ " decode(nvl(sum(case when A.x='UR202' then 1 else 0 end),0),0,0,1), "
					+ " decode(nvl(sum(case when A.x='UR203' then 1 else 0 end),0),0,0,1), "
					+ " decode(nvl(sum(case when A.x='UR204' then 1 else 0 end),0),0,0,1), "
					+ " decode(nvl(sum(case when A.x='UR205' then 1 else 0 end),0),0,0,1), "
					+ " decode(nvl(sum(case when A.x='UR206' then 1 else 0 end),0),0,0,1), "
					+ " decode(nvl(sum(case when A.x='UR207' then 1 else 0 end),0),0,0,1), "
					+ " decode(nvl(sum(case when A.x='UR208' then 1 else 0 end),0),0,0,1), "
					+ " decode(nvl(sum(case when A.x='UR209' then 1 else 0 end),0),0,0,1), "
					+ " decode(nvl(sum(case when A.x='UR210' then 1 else 0 end),0),0,0,1) "
					+ " from ( "
					+ " select  distinct subtype x from es_doc_main where subtype in "
					+ " ('UR201','UR202','UR203','UR204','UR205','UR206','UR207','UR208','UR209','UR210') "
					+ " and doccode='"
					+ tLCContSchema.getProposalContNo()
					+ "' " + " ) A";
			SSRS tSSRS_DocAll = new SSRS();
			ExeSQL tExeSQL_DocAll = new ExeSQL();
			tSSRS_DocAll = tExeSQL_DocAll.execSQL(tSQL_DocAll);
			// String t
			if (tSSRS_DocAll.getMaxRow() > 0) {
				cont.setOImportDisFlag(tSSRS_DocAll.GetText(1, 1));// 要约撤销标记
				cont.setImportUpdatePFlag(tSSRS_DocAll.GetText(1, 2));// 要约更正标记（打印）
				cont.setImportUpdateNFlag(tSSRS_DocAll.GetText(1, 3));// 要约更正标记（非打印）
				cont.setPolAppDFlag(tSSRS_DocAll.GetText(1, 4));// 生效日回溯标记
				cont.setHPENotice(tSSRS_DocAll.GetText(1, 5));// 有体检资料标记???
				cont.setHSpecFlag(tSSRS_DocAll.GetText(1, 6));// 有特别约定标记
				cont.setHAskFlag(tSSRS_DocAll.GetText(1, 7));// 问卷标记
				cont.setStatusFlag(tSSRS_DocAll.GetText(1, 8));// 身份证明标记
				cont.setIllFlag(tSSRS_DocAll.GetText(1, 9));// 病历资料标记
				cont.setOtherDataFlag(tSSRS_DocAll.GetText(1, 10));// 其他资料标记

			} else {
				cont.setOImportDisFlag("0");// 要约撤销标记
				cont.setImportUpdatePFlag("0");// 要约更正标记（打印）
				cont.setImportUpdateNFlag("0");// 要约更正标记（非打印）
				cont.setPolAppDFlag("0");// 生效日回溯标记
				cont.setHPENotice("0");// 有体检资料标记???
				cont.setHSpecFlag("0");// 有特别约定标记
				cont.setHAskFlag("0");// 问卷标记
				cont.setStatusFlag("0");// 身份证明标记
				cont.setIllFlag("0");// 病历资料标记
				cont.setOtherDataFlag("0");// 其他资料标记
			}
			// ?????????????
			String sqlFind = "select count(*) from lccustomerimpart where contno='"
					+ mContNo + "' and impartver='103' and impartcode='12'";
			String tFindonTent = tExeSQL.getOneValue(sqlFind);
			if (tFindonTent != null && !tFindonTent.equals("")
					&& Integer.parseInt(tFindonTent) > 0) {
				cont.sethasSeenInsured("1");// 业务员是否亲眼见过被保险人
			} else
				cont.sethasSeenInsured("0");

			// tongmeng 2009-04-13 add
			// 合同词条增加经过自核次数
			String tCount_SQL = "select count(*)+1 from lbmission "
					// +
					// " where processID = '0000000003' and ActivityID = '0000001003' "
					+ " ActivityID  in (select activityid from lwactivity  where functionid ='10010005')"
					+ " and missionprop1='" + this.mContNo + "'";
			ExeSQL tExeSQL_Auto = new ExeSQL();
			String tValue_Auto = tExeSQL_Auto.getOneValue(tCount_SQL);
			if (tValue_Auto == null || tValue_Auto.equals("")) {
				cont.setAutoUWTimes(new Double(0));
			} else {
				cont.setAutoUWTimes(new Double(tValue_Auto));
			}
			cont.setReinImpart(getReinImpart());// 补充告知问卷
			cont.setRemarkCount(getRemarkCount());// 备注栏字数
			cont.setAccoBodyCheck(getAccoBodyCheck());// 陪检记录
			cont.setIsAppointHos(getIsAppointHos());// 是否是定点医院
			cont.setSpotCheckFlag(getSpotCheckFlag());// 系统抽检标记
			cont.setMOpeIsNotDefined(getMOpeIsNotDefined());// 生调回复人员是否与系统定义不一致
			cont.setSecondaryManagecom(getManagetCom(
					tLCContSchema.getManageCom(), 4));// 二级机构
			cont.setThirdStageManagecom(getManagetCom(
					tLCContSchema.getManageCom(), 6));// 三级机构
			cont.setFourStageManagecom(getManagetCom(
					tLCContSchema.getManageCom(), 8));// 四级机构
			cont.setSamplingFactor(getSamplingFactor());// 抽检因子
			cont.setTotalCount(Double.valueOf(String.valueOf(mGlobalCheckSpot
					.getCurrentCheckNum())));// 当前已抽检数
			cont.setUWLevel(getContUWLevel(tLCContSchema.getManageCom()));
			// hide by gaobo for MMA 20100920. Reason: can't get values.
			// cont.setFirstTrialDate(sdf.parse(tLCContSchema.getFirstTrialDate()+
			// " "+ "00:00:00"));//合同的【初审日期】
			// cont.setUWDate(sdf.parse((getUWDate(tLCContSchema.getPrtNo()))));//进入【自核日期】的时间
			cont.setManaSpecFlag(getManaSpecFlag(tLCContSchema.getManageCom()));// 管理机构百团标记
		} catch (Exception e) {
			CError.buildErr(this, "准备BOMCont时出错！");
			e.printStackTrace();
		}
		return cont;
	}

	private BOMAppnt DealBOMAppnt(LCAppntSchema tLCAppntSchema,
			LCContSchema tLCContSchema) {
		BOMAppnt appnt = new BOMAppnt();
		ExeSQL tempExeSQL = new ExeSQL();
		try {
			int tAppAge = PubFun.calInterval2(
					tLCAppntSchema.getAppntBirthday(),
					tLCContSchema.getPolApplyDate(), "Y");
			appnt.setAppntAge(Double.valueOf(String.valueOf(tAppAge)));
			if (!((tLCAppntSchema.getAppntBirthday() == null))
					|| "".equals(tLCAppntSchema.getAppntBirthday())) {
				theDate = tLCAppntSchema.getAppntBirthday() + " 00:00:00";
				appnt.setAppntBirthday(sdf.parse(theDate));
			}
			appnt.setAppntName(tLCAppntSchema.getAppntName());
			appnt.setAppntNo(tLCAppntSchema.getAppntNo());
			appnt.setAppntSex(tLCAppntSchema.getAppntSex());
			String tBlackList = "select nvl(BlacklistFlag,0) from ldperson where customerno='"
					+ tLCAppntSchema.getAppntNo() + "'";

			String tBlackFlag = tempExeSQL.getOneValue(tBlackList);

			appnt.setBlackListFlag(tBlackFlag);// 黑名单标记
			appnt.setCreditGrade(tLCAppntSchema.getCreditGrade());
			appnt.setDegree(tLCAppntSchema.getDegree());

			if (!((tLCAppntSchema.getJoinCompanyDate() == null) || ""
					.equals(tLCAppntSchema.getJoinCompanyDate()))) {
				theDate = tLCAppntSchema.getJoinCompanyDate() + " 00:00:00";
				appnt.setJoinCompanyDate(sdf.parse(theDate));
			}
			appnt.setMarriage(tLCAppntSchema.getMarriage());
			if (!((tLCAppntSchema.getMarriageDate() == null))
					|| "".equals(tLCAppntSchema.getMarriageDate())) {
				theDate = tLCAppntSchema.getMarriageDate() + " 00:00:00";
				appnt.setMarriageDate(sdf.parse(theDate));
			}
			appnt.setNationality(tLCAppntSchema.getNationality());
			appnt.setNativePlace(tLCAppntSchema.getNativePlace());
			appnt.setOccupationCode(tLCAppntSchema.getOccupationCode());
			appnt.setOccupationType(tLCAppntSchema.getOccupationType());
			appnt.setPosition(tLCAppntSchema.getPosition());
			appnt.setRelationToInsured(tLCAppntSchema.getRelationToInsured());
			appnt.setRgtAddress(tLCAppntSchema.getRgtAddress());
			appnt.setSalary(new Double(tLCAppntSchema.getSalary()));

			if (!((tLCAppntSchema.getStartWorkDate() == null))
					|| "".equals(tLCAppntSchema.getStartWorkDate())) {
				theDate = tLCAppntSchema.getStartWorkDate() + " 00:00:00";
				appnt.setStartWorkDate(sdf.parse(theDate));
			}
			// 个人单
			String tSmokeFlagSql = "select count(*)"
					+ " from lccustomerimpartparams"
					+ " where impartcode = 'A0102'" + " and impartver = 'A01' "
					+ " and impartparamno in ('3','4')" + " and contno='"
					+ mContNo + "'";
			if (mLCContSchema.getPrtNo() != null
					&& !mLCContSchema.getPrtNo().equals("")
					&& mLCContSchema.getPrtNo().length() == 14
					&& mLCContSchema.getPrtNo().substring(2, 4).equals("51"))// 家庭单
			{ // 家庭单
				tSmokeFlagSql = "select count(*)"
						+ " from lccustomerimpartparams"
						+ " where impartcode = 'D0102'"
						+ " and impartver = 'D01' "
						// +" and customernotype='1'"//被保人
						// +" and customerno='"
						// +tLCInsuredSchema.getInsuredNo()
						+ " and impartparamno in ('1','2')" + " and contno='"
						+ mContNo + "'";

			}
			String tSmokeFlag = tempExeSQL.getOneValue(tSmokeFlagSql);// 是否有吸烟习惯
			if (tSmokeFlag != null && !tSmokeFlag.equals("")
					&& Integer.parseInt(tSmokeFlag) > 0) {
				appnt.setSmokeFlag("1");
			} else {
				appnt.setSmokeFlag("0");// 吸烟标记
			}
			String tAppHosSql = "select nvl(blacklistflag,'0'),nvl(hospitalname,'') from ldhospital where hospitcode in "
					+ "(select hospitcode from lcpenotice where contno='"
					+ mContNo
					+ "' and customerno='"
					+ tLCAppntSchema.getAppntNo() + "'and customertype='A')";
			SSRS tSSRS_Hospital = new SSRS();
			tSSRS_Hospital = tempExeSQL.execSQL(tAppHosSql);
			if (tSSRS_Hospital.getMaxRow() > 0) {
				appnt.setAppHosBlack(tSSRS_Hospital.GetText(1, 1));
				appnt.setAppHosName(tSSRS_Hospital.GetText(1, 2));
			}

			// 健康告知不全为否 HealthTellConTent
			// tongmeng 2009-04-25 modify
			// 按照客户号查询
			String tHealthTellConTent_sql = "select count(*) from lccustomerimpart where impartcode in('A0105','A0110','A0111e','A0111j',"
					+ "'A0106','A0111a','A0111f','A0112','A0107','A0111b','A0111g','A0108','A0111c','A0111h','A0109','A0111d',"
					+ "'A0111i','D0105','D0110a','D0110e','D0110i','D0106','D0110b','D0110f','D0110j','D0107','D0110c','D0110g',"
					+ "'D0111','D0108','D0110d','D0110h','D0122','D0109','C0101','C0102','C0103','C0104','C0105','C0106','B0101',"
					+ "'B0102','B0103','B0104','A0504','A0505','A0506','A0507','A0509','A0510','A0511','A0512',"
					+ "'A0513','A0514','A0515','A0516','A0517','A0518','A0519','A0520')"
					+ " and contno='"
					+ mContNo
					+ "' and customerno='"
					+ tLCAppntSchema.getAppntNo() + "' ";// and
															// CustomerNoType='0'";
			String tHealthTellConTent = "";
			tHealthTellConTent = tempExeSQL.getOneValue(tHealthTellConTent_sql);
			if (tHealthTellConTent != null && !tHealthTellConTent.equals("")
					&& Integer.parseInt(tHealthTellConTent) > 0) {
				appnt.setHealthTellConTent("1");
			} else {
				appnt.setHealthTellConTent("0");
			}

			// 妇科告知不全为否 FemaleConTent
			String tFemaleConTent_sql = "select count(*) from lccustomerimpart where impartcode in('A0113a','A0113b','D0112a','D0112b')"
					+ " and contno='"
					+ mContNo
					+ "' and customerno='"
					+ tLCAppntSchema.getAppntNo() + "' ";// and
															// CustomerNoType='0'";
			String tFemaleConTent = "";
			tFemaleConTent = tempExeSQL.getOneValue(tFemaleConTent_sql);
			if (tFemaleConTent != null && !tFemaleConTent.equals("")
					&& Integer.parseInt(tFemaleConTent) > 0) {
				appnt.setFemaleConTent("1");
			} else {
				appnt.setFemaleConTent("0");
			}
			// 婴儿告知不为否 BabyConTent
			String tBabyConTent_sql = "select count(*) from lccustomerimpart where impartcode in('A0114b','D0121')"
					+ " and contno='"
					+ mContNo
					+ "' and customerno='"
					+ tLCAppntSchema.getAppntNo() + "' ";// and
															// CustomerNoType='0'";
			String tBabyConTent = "";
			tBabyConTent = tempExeSQL.getOneValue(tBabyConTent_sql);
			if (tBabyConTent != null && !tBabyConTent.equals("")
					&& Integer.parseInt(tBabyConTent) > 0) {
				appnt.setBabyConTent("1");
			} else {
				appnt.setBabyConTent("0");
			}
			// 家族史 FamilyConTent
			String tFamilyConTent_sql = "select count(*) from lccustomerimpart where impartcode in('A0115a','A0115b','D0113a','D0113b')"
					+ " and contno='"
					+ mContNo
					+ "' and customerno='"
					+ tLCAppntSchema.getAppntNo() + "' ";// and
															// CustomerNoType='0'";
			String tFamilyConTent = "";
			tFamilyConTent = tempExeSQL.getOneValue(tFamilyConTent_sql);
			if (tFamilyConTent != null && !tFamilyConTent.equals("")
					&& Integer.parseInt(tFamilyConTent) > 0) {
				appnt.setFamilyConTent("1");
			} else {
				appnt.setFamilyConTent("0");
			}
			// 既往异常投保/理赔史 OuncommonConTent
			String tOuncommonConTent_sql = "select count(*) from lccustomerimpart where impartcode in('A0117','A0118','D0115','D0116','D0117','C0108')"
					+ " and contno='"
					+ mContNo
					+ "' and customerno='"
					+ tLCAppntSchema.getAppntNo() + "' ";// and
															// CustomerNoType='0'";
			String tOuncommonConTent = "";
			tOuncommonConTent = tempExeSQL.getOneValue(tOuncommonConTent_sql);
			if (tOuncommonConTent != null && !tOuncommonConTent.equals("")
					&& Integer.parseInt(tOuncommonConTent) > 0) {
				appnt.setOuncommonConTent("1");
			} else {
				appnt.setOuncommonConTent("0");
			}

			// 累计年交保费：交费方式不为趸交的不累计,其他换算为年交 半年交费率=年交费率×0.52 季交费率=年交费率×0.27
			// 月交费率=年交费率×0.09
			String tAddYPrem_sql = "select  nvl(sum(case when payintv = '12' then prem when payintv = '6' then prem / 0.52 when payintv = '3' then prem / 0.27"
					+ " when payintv = '1' then prem / 0.09 else 0 end),0) from lcpol where appflag not in ('4','9') and appntno='"
					+ tLCAppntSchema.getAppntNo()
					+ "' "
					+ " and uwflag not in ('1','2','a')";
			String tAddYPrem = "";
			tAddYPrem = tempExeSQL.getOneValue(tAddYPrem_sql);
			appnt.setAddYPrem(Double.valueOf(tAddYPrem));// 累积年交保费

			String tYearGet_sql =
			// "select nvl(max(ImpartParam),0) from lccustomerimpartparams where impartcode='A0120' and impartver='A02' and impartparamno='3'"
			// +"and contno='"+mContNo+"' and customerno='"+tLCAppntSchema.getAppntNo()+"' ";//and
			// CustomerNoType='0'";
			"select nvl(max(A.x),0) from (select nvl(max(ImpartParam*1), 0) x"
					+ " from lccustomerimpartparams"
					+ " where impartcode in ('A0120', 'D0119')"
					+ " and impartver in ('A02', 'D02')"
					+ " and impartparamno = '3'" + " and contno = '" + mContNo
					+ "'" + " and customerno = '" + tLCAppntSchema.getAppntNo()
					+ "'" + " union"
					+ " select nvl(max(impartparam * 10000),0) x"
					+ " from lccustomerimpartparams"
					+ " where impartcode = 'A0534'"
					+ " and impartparamno = '3'" + " and contno = '" + mContNo
					+ "'" + " and customerno = '" + tLCAppntSchema.getAppntNo()
					+ "') A";
			String tYearGet = "";
			tYearGet = tempExeSQL.getOneValue(tYearGet_sql);
			if (tYearGet != null && !tYearGet.equals("")) {
				// 60-U-10-201tYearGet 会作为除数,如果为0,设置为1
				//
				if (tYearGet.equals("0")) {
					tYearGet_sql = "select nvl(max(A.x),0) from (select nvl(max(ImpartParam*1), 0) x"
							+ " from lccustomerimpartparams"
							+ " where impartcode in ('A0120', 'D0119')"
							+ " and impartver in ('A02', 'D02')"
							+ " and impartparamno = '3'"
							+ " and contno = '"
							+ mContNo
							+ "'"
							+ " and customerno = '"
							+ tLCAppntSchema.getAppntNo()
							+ "'"
							+ " union"
							+ " select nvl(max(impartparam * 10000),0) x"
							+ " from lccustomerimpartparams"
							+ " where impartcode = 'A0534'"
							+ " and impartparamno = '1'"
							+ " and contno = '"
							+ mContNo
							+ "'"
							+ " and customerno = '"
							+ tLCAppntSchema.getAppntNo() + "') A";
					tYearGet = tempExeSQL.getOneValue(tYearGet_sql);
					if (tYearGet.equals("0")) {
						tYearGet = "1";
					}
				}
				appnt.setYearGet(Double.valueOf(tYearGet));// 年收入
			}

			// appnt.setAppntISOperator(AppntISOperator);//投保人职业为MS公司业务员（待确认）
			// appnt.setInnerFlag(InnerFlag);//内部员工标记

			// String sqlFind = "";
			// String tFindonTent = "";
			// sqlFind =
			// "select count(*) from lccustomerimpart where contno='"+mContNo+"' and impartver='103' and impartcode='21' and impartcontent like '%同事朋友推荐%'";
			// tFindonTent = tempExeSQL.getOneValue(sqlFind);
			// if(tFindonTent!=null&&!tFindonTent.equals("")&&Integer.parseInt(tFindonTent)>0){
			// appnt.setisFriendsRecommend("1");//投保人投保经过是否为同事朋友推荐
			// }
			// else
			// appnt.setisFriendsRecommend("0");
			//
			// sqlFind =
			// "select count(*) from lccustomerimpart where contno='"+mContNo+"' and impartver='103' and impartcode='21' and impartcontent like '%投保人自己提出%'";
			// tFindonTent = tempExeSQL.getOneValue(sqlFind);
			// if(tFindonTent!=null&&!tFindonTent.equals("")&&Integer.parseInt(tFindonTent)>0){
			// appnt.setisPresenter("1");//投保人投保经过是否投保人自己提出
			// }
			// else
			// appnt.setisPresenter("0");
			//
			// sqlFind =
			// "select count(*) from lccustomerimpart where contno='"+mContNo+"' and impartver='103' and impartcode='21' and impartcontent like '%业务员推销%'";
			// tFindonTent = tempExeSQL.getOneValue(sqlFind);
			// if(tFindonTent!=null&&!tFindonTent.equals("")&&Integer.parseInt(tFindonTent)>0){
			// appnt.setisSalerPromotion("1");//投保人投保经过是否为业务员推销
			// }
			// else
			// appnt.setisSalerPromotion("0");
			//
			// sqlFind =
			// "select count(*) from lccustomerimpart where contno='"+mContNo+"' and impartver='103' and impartcode='22' and impartcontent like '%家庭经济保障%'";
			// tFindonTent = tempExeSQL.getOneValue(sqlFind);
			// if(tFindonTent!=null&&!tFindonTent.equals("")&&Integer.parseInt(tFindonTent)>0){
			// appnt.setisEnsureIntention("1");//投保人投保目的为家庭经济保障
			// }
			// else
			// appnt.setisEnsureIntention("0");
			//
			// sqlFind =
			// "select count(*) from lccustomerimpart where contno='"+mContNo+"' and impartver='103' and impartcode='22' and impartcontent like '%储蓄/投资%'";
			// tFindonTent = tempExeSQL.getOneValue(sqlFind);
			// if(tFindonTent!=null&&!tFindonTent.equals("")&&Integer.parseInt(tFindonTent)>0){
			// appnt.setisInvestItention("1");//投保人投保目的为贮蓄/投资
			// }
			// else
			// appnt.setisInvestItention("0");
			//
			// sqlFind =
			// "select count(*) from lccustomerimpart where contno='"+mContNo+"' and impartver='103' and impartcode='22' and impartcontent like '%贷款偿还%'";
			// tFindonTent = tempExeSQL.getOneValue(sqlFind);
			// if(tFindonTent!=null&&!tFindonTent.equals("")&&Integer.parseInt(tFindonTent)>0){
			// appnt.setisPayIntention("1");//投保人投保目的为贷款偿还
			// }
			// else
			// appnt.setisPayIntention("0");
			String sqlFind = "";
			sqlFind = "select nvl(impartparam,'0')"
					+ " from lccustomerimpartparams"
					+ " where impartcode = 'A0101'"
					+ " and impartver = 'A01' "
					// +" and impartparamno in('3','4')"
					+ " and customerno='" + tLCAppntSchema.getAppntNo() + "' "
					+ " and contno='" + mContNo + "'"
					+ " order by customernotype,customerno,impartparamno";
			if (mLCContSchema.getPrtNo() != null
					&& !mLCContSchema.getPrtNo().equals("")
					&& mLCContSchema.getPrtNo().length() == 14
					&& mLCContSchema.getPrtNo().substring(2, 4).equals("51"))// 家庭单
			{
				sqlFind = "select nvl(impartparam,'0')"
						+ " from lccustomerimpartparams"
						+ " where impartcode = 'D0101'"
						+ " and impartver = 'D01' "
						+ " and impartparamno in('1','2')" + " and contno='"
						+ mContNo + "'"
						+ " order by customernotype,customerno,impartparamno";

			}
			SSRS tSSRS = new SSRS();
			tSSRS = tempExeSQL.execSQL(sqlFind);
			try {
				if (tSSRS.MaxRow >= 4) {
					double tStature = Double.parseDouble(tSSRS.GetText(3, 1));// 身高
					double tAvoirdupois = Double.parseDouble(tSSRS
							.GetText(4, 1));// 体重
					appnt.setAvoirdupois(new Double(tAvoirdupois));
					appnt.setStature(new Double(tStature));
				} else if (tSSRS.MaxRow >= 2) {
					double tStature = Double.parseDouble(tSSRS.GetText(1, 1));// 身高
					double tAvoirdupois = Double.parseDouble(tSSRS
							.GetText(2, 1));// 体重
					appnt.setAvoirdupois(new Double(tAvoirdupois));
					appnt.setStature(new Double(tStature));
				}
			} catch (Exception e) {
				CError.buildErr(this, mContNo + "投保人身高体重异常！");
				e.printStackTrace();
			}
			appnt.setPregnancyWeeks(getPregnancyWeeks(tLCAppntSchema
					.getAppntNo()));// 怀孕周数
			appnt.setSmokeYears(getSmokeYears(tLCAppntSchema.getAppntNo(), "A",
					false));// 吸烟年数
			appnt.setDrinkType(getDrinkType(tLCAppntSchema.getAppntNo()));// 饮酒类型
			appnt.setDrinkYears(getDrinkYears(tLCAppntSchema.getAppntNo()));// 饮酒年数
			appnt.setISPregnancy(getISImpart(tLCAppntSchema.getAppntNo(), "01"));// 孕妇告知
			appnt.setDanSportInter(getISImpart(tLCAppntSchema.getAppntNo(),
					"02"));// 危险运动爱好
			appnt.setTrafAccImpart(getISImpart(tLCAppntSchema.getAppntNo(),
					"03"));// 交通事故告知
			appnt.setAbroadImpart(getISImpart(tLCAppntSchema.getAppntNo(), "04"));// 出国意向告知
			appnt.setDisabilityImpart(getDisabilityImpart(tLCAppntSchema
					.getAppntNo()));// 残疾事项告知
			appnt.setSamePhone(getSamePhone(tLCAppntSchema.getAppntNo(),
					tLCAppntSchema.getAddressNo()));// 联系电话与业务员的联系电话是否一致
		} catch (Exception e) {
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return appnt;
	}

	private BOMAgent DealBOMAgent(LAAgentSchema tLAAgentSchema) {
		BOMAgent agent = new BOMAgent();
		String tBlackList = "select blacklisflag from latree where agentcode='"
				+ tLAAgentSchema.getAgentCode() + "'";
		ExeSQL tempExeSQL = new ExeSQL();
		String tBlackFlag = tempExeSQL.getOneValue(tBlackList);
		agent.setAgentBlankFlag(tBlackFlag);// 黑名单标记
		agent.setAgentCode(tLAAgentSchema.getAgentCode());
		agent.setAgentKind(tLAAgentSchema.getAgentKind());
		agent.setAgentState(tLAAgentSchema.getAgentState());
		agent.setBranchType(tLAAgentSchema.getBranchType());
		agent.setInsideFlag(tLAAgentSchema.getInsideFlag());
		agent.setManageCom(tLAAgentSchema.getManageCom());
		agent.setQuafNo(tLAAgentSchema.getQuafNo());
		agent.setSaleQuaf(tLAAgentSchema.getSaleQuaf());
		// tongmeng 2009-04-11 add
		// 增加业务员告知异常和投保人投保告知异常词条
		agent.setSpecAImpart(this.getSpecAImpart(mContNo));
		agent.setProAImpart(this.getProAImpart(mContNo));
		agent.setUWLevel(getAgentUWLevel(tLAAgentSchema.getAgentCode()));
		String tRelToInsured = getRelToInsured();
		agent.setRelToAppnt(tRelToInsured);// 与被保人关系
		// agent.setLevelYN(LevelYN);//等级是否为差
		return agent;
	}

	private BOMInsured[] DealBOMInsured(LCInsuredSet tLCInsuredSet,
			LCContSchema tLCContSchema) {
		BOMInsured[] insureds = new BOMInsured[insuredCount];
		try {
			if (tLCInsuredSet.size() > 0) {
				for (int i = 1; i <= tLCInsuredSet.size(); i++) {
					BOMInsured insured = new BOMInsured();
					LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
					tLCInsuredSchema = tLCInsuredSet.get(i);
					insured.setInsuredNo(tLCInsuredSchema.getInsuredNo());
					/** 是否是投保人 ture-是 */
					boolean FreeFlag = false;
					if (tLCInsuredSchema.getGrpContNo() != null
							&& tLCInsuredSchema.getGrpContNo().equals("FREE")) {
						FreeFlag = true;
					}
					ExeSQL tExeSQL2 = new ExeSQL();
					GetAllSumAmnt(tLCInsuredSchema.getInsuredNo());// 得到以下各种累计风险保额
					insured.setAccidentSumAmnt(new Double(this.MSumDangerAmnt));// 累计意外伤害风险保额

					// String tApproveSql =
					// " select count(*) from lwmission where activityid='0000001001' "
					String tApproveSql = " select count(*) from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010003') "
							+ " and missionprop1 in (select contno from lcinsured where insuredno='"
							+ tLCInsuredSchema.getInsuredNo() + "') ";
					String tApprovePolCount = tExeSQL2.getOneValue(tApproveSql);
					if (!(tApprovePolCount == null || ""
							.equals(tApprovePolCount))) {
						insured.setApprovePolCount(Double
								.valueOf(tApprovePolCount));// 待复合投保单数
					}
					// insured.setAvoirdupois(new
					// Double(tLCInsuredSchema.getAvoirdupois()));//体重
					// tongmeng 2009-04-25 modify
					// 按照客户号查询
					String BirthSql1 = "select nvl(max(ImpartParam),'0') from lccustomerimpartparams where impartver in ('A01','A05')"
							+ " and impartcode in ('A0114a','A0523') and impartparamno='1'"
							// +" and customernotype='1'"//被保人
							+ " and customerno='"
							+ tLCInsuredSchema.getInsuredNo()
							+ "' and contno='" + mContNo + "'";
					try {
						String tBirth1 = "";
						// String tempBirth1 = "";
						tBirth1 = tExeSQL2.getOneValue(BirthSql1);
						// if(tempBirth1!=null&&!tempBirth1.equals("")){
						// tBirth1 = tempBirth1.replaceAll("[^0-9]", "");
						// }
						logger.debug("tBirth1 " + tBirth1);
						if (!(tBirth1 == null || "".equals(tBirth1.trim()))) {
							insured.setBirthStature(Double.valueOf(tBirth1));// 出生时身高
						}
					} catch (Exception e1) {
						CError.buildErr(this, mContNo + "被保人出生时身高数据有误！");
						e1.printStackTrace();
					}
					try {
						String BirthSql2 = "select nvl(max(ImpartParam),'0') from lccustomerimpartparams where impartver in ('A01','A05')"
								+ " and impartcode in ('A0114a','A0523') and impartparamno='2'"
								// +" and customernotype='1'"//被保人
								+ " and customerno='"
								+ tLCInsuredSchema.getInsuredNo()
								+ "' and contno='" + mContNo + "'";
						String tBirth2 = "";
						// String tempBirth2 = "";
						tBirth2 = tExeSQL2.getOneValue(BirthSql2);
						// if(tempBirth2!=null&&!tempBirth2.equals("")){
						// tBirth2 = tempBirth2.replaceAll("[^0-9]", "");
						// }
						if (!(tBirth2 == null || "".equals(tBirth2.trim()))) {
							insured.setBirthAvoirdupois(Double.valueOf(tBirth2));// 出生时体重
						}
					} catch (Exception e1) {
						CError.buildErr(this, mContNo + "被保人出生时体重数据有误！");
						e1.printStackTrace();
					}

					if (!((tLCInsuredSchema.getBirthday() == null))
							|| "".equals(tLCInsuredSchema.getBirthday())) {
						theDate = tLCInsuredSchema.getBirthday() + " 00:00:00";
						insured.setBirthday(sdf.parse(theDate));
					}
					String tBlackList = "select nvl(BlacklistFlag,0) from ldperson where customerno='"
							+ tLCInsuredSchema.getInsuredNo() + "'";
					ExeSQL tempExeSQL = new ExeSQL();
					String tBlackFlag = tempExeSQL.getOneValue(tBlackList);
					insured.setBlackListFlag(tBlackFlag);// 黑名单标记

					String tBMISql = "select nvl(impartparam,'0')"
							+ " from lccustomerimpartparams"
							+ " where impartcode in ('A0101','A0501')"
							+ " and impartver in ('A01','A05') "
							// +" and impartparamno in('1','2')"
							// +" and customernotype='1'"//被保人
							+ " and customerno='"
							+ tLCInsuredSchema.getInsuredNo()
							+ "' "
							+ " and contno='"
							+ mContNo
							+ "'"
							+ " order by customernotype,customerno,impartparamno";
					if (mLCContSchema.getPrtNo() != null
							&& !mLCContSchema.getPrtNo().equals("")
							&& mLCContSchema.getPrtNo().length() == 14
							&& mLCContSchema.getPrtNo().substring(2, 4)
									.equals("51")
							&& tLCInsuredSchema.getSequenceNo() != null
							&& !tLCInsuredSchema.getSequenceNo().equals(""))// 家庭单
					{
						// tBMISql = "select 1 from dual";
						if (tLCInsuredSchema.getSequenceNo().equals("1")
								|| tLCInsuredSchema.getSequenceNo()
										.equals("-1")) {
							tBMISql = "select nvl(impartparam,'0')"
									+ " from lccustomerimpartparams"
									+ " where impartcode = 'D0101'"
									+ " and impartver = 'D01' "
									+ " and impartparamno in('1','2')"
									// +" and customernotype='1'"//被保人
									// +" and customerno='"+tLCInsuredSchema.getInsuredNo()+"' "
									+ " and contno='"
									+ mContNo
									+ "'"
									+ " order by customernotype,customerno,impartparamno";
						} else if (tLCInsuredSchema.getSequenceNo().equals("2")) {
							tBMISql = "select nvl(impartparam,'0')"
									+ " from lccustomerimpartparams"
									+ " where impartcode = 'D0101'"
									+ " and impartver = 'D01' "
									+ " and impartparamno in('3','4')"
									// +" and customernotype='1'"//被保人
									// +" and customerno='"+tLCInsuredSchema.getInsuredNo()+"' "
									+ " and contno='"
									+ mContNo
									+ "'"
									+ " order by customernotype,customerno,impartparamno";
						} else if (tLCInsuredSchema.getSequenceNo().equals("3")) {
							tBMISql = "select nvl(impartparam,'0')"
									+ " from lccustomerimpartparams"
									+ " where impartcode = 'D0101'"
									+ " and impartver = 'D01' "
									+ " and impartparamno in('5','6')"
									// +" and customernotype='1'"//被保人
									// +" and customerno='"+tLCInsuredSchema.getInsuredNo()+"' "
									+ " and contno='"
									+ mContNo
									+ "'"
									+ " order by customernotype,customerno,impartparamno";
						}
					}
					SSRS tSSRS = new SSRS();
					tSSRS = tempExeSQL.execSQL(tBMISql);
					double tBMI = 0;
					try {
						if (FreeFlag) {
							// 如果是投保人
							if (tSSRS.MaxRow >= 4) {
								double tStature = Double.parseDouble(tSSRS
										.GetText(3, 1));// 身高
								double tAvoirdupois = Double.parseDouble(tSSRS
										.GetText(4, 1));// 体重
								insured.setAvoirdupois(new Double(tAvoirdupois));
								insured.setStature(new Double(tStature));
								if (tStature > 0) {
									tBMI = tAvoirdupois * 10000
											/ (tStature * tStature);
									DecimalFormat mDecimalFormat = new DecimalFormat(
											"0");
									// tBMI =
									// Double.parseDouble(mDecimalFormat.format(tBMI));
									tBMI = PubFun.round(tBMI, 0);
									;
								}
								insured.setBMI(new Double(tBMI));
							} else if (tSSRS.MaxRow >= 2) {
								double tStature = Double.parseDouble(tSSRS
										.GetText(1, 1));// 身高
								double tAvoirdupois = Double.parseDouble(tSSRS
										.GetText(2, 1));// 体重
								insured.setAvoirdupois(new Double(tAvoirdupois));
								insured.setStature(new Double(tStature));
								if (tStature > 0) {
									tBMI = tAvoirdupois * 10000
											/ (tStature * tStature);
									DecimalFormat mDecimalFormat = new DecimalFormat(
											"0");
									// tBMI =
									// Double.parseDouble(mDecimalFormat.format(tBMI));
									tBMI = PubFun.round(tBMI, 0);
									;
								}
								insured.setBMI(new Double(tBMI));
							} else {
								insured.setAvoirdupois(new Double("0"));
								insured.setStature(new Double("0"));
								insured.setBMI(new Double("0"));
							}
						} else {
							if (tSSRS.MaxRow >= 2) {
								double tStature = Double.parseDouble(tSSRS
										.GetText(1, 1));// 身高
								double tAvoirdupois = Double.parseDouble(tSSRS
										.GetText(2, 1));// 体重
								insured.setAvoirdupois(new Double(tAvoirdupois));
								insured.setStature(new Double(tStature));
								if (tStature > 0) {
									tBMI = tAvoirdupois * 10000
											/ (tStature * tStature);
									DecimalFormat mDecimalFormat = new DecimalFormat(
											"0");
									// tBMI =
									// Double.parseDouble(mDecimalFormat.format(tBMI));
									tBMI = PubFun.round(tBMI, 0);
								}
								insured.setBMI(new Double(tBMI));
							} else {
								insured.setAvoirdupois(new Double("0"));
								insured.setStature(new Double("0"));
								insured.setBMI(new Double("0"));
							}
						}
					} catch (Exception e) {
						CError.buildErr(this, mContNo + "身高体重数据异常!");
						e.printStackTrace();
					}
					/*
					 * if(FreeFlag){ insured.setBMI(new Double("0")); }
					 */
					// tongmeng 2009-03-26 add
					// 增加身高体重是否在标准范围内
					// insured.setStature(Double.valueOf(String.valueOf(tLCInsuredSchema.getStature())));
					String tBMIFlag = "";
					tBMIFlag = this.getBMIFlag(tLCContSchema.getContNo(),
							tLCInsuredSchema, insured, tLCContSchema, "0");
					insured.setStatureFlag(tBMIFlag);// 身高是否符合标准范围
					tBMIFlag = this.getBMIFlag(tLCContSchema.getContNo(),
							tLCInsuredSchema, insured, tLCContSchema, "1");
					insured.setAvoirdupoisFlag(tBMIFlag);// 体重是否符合标准范围
					// tongmeng 2009-03-24 add
					// 增加BMI是否在标准范围内
					tBMIFlag = this.getBMIFlag(tLCContSchema.getContNo(),
							tLCInsuredSchema, insured, tLCContSchema, "2");
					insured.setBMIFlag(tBMIFlag);
					// tongmeng 2009-02-20 modify
					// 生调不会记录到人,只查合同是否生调即可
					String LiveSql = "select count(*) from lcrreport where contno='"
							+ tLCInsuredSchema.getContNo() + "'";
					String tLiveValue = "";
					tLiveValue = tempExeSQL.getOneValue(LiveSql);
					if (tLiveValue != null && !tLiveValue.equals("")
							&& Integer.parseInt(tLiveValue) > 0) {
						insured.setContenteFlag("1");// 生调标记
					} else {
						insured.setContenteFlag("0");// 生调标记
					}
					String HeathSql = "select count(*) from LCPENotice where customerno='"
							+ tLCInsuredSchema.getInsuredNo() + "'";
					String tHealthValue = "";
					tHealthValue = tempExeSQL.getOneValue(HeathSql);
					if (tHealthValue != null && !tHealthValue.equals("")
							&& Integer.parseInt(tHealthValue) > 0) {
						insured.setHealthFlag("1");// 体检标记
					} else {
						insured.setHealthFlag("0");// 体检标记
					}
					// tongmeng 2009-04-02 modify
					// 是否需要体检
					String tNeedPEN_SQL = "";
					if (!FreeFlag) {
						tNeedPEN_SQL = " select decode(count(*),0,0,1) from lcinsured a "
								+ " where insuredno='"
								+ tLCInsuredSchema.getInsuredNo()
								+ "' "
								+ " and contno='"
								+ tLCInsuredSchema.getContNo()
								+ "' "
								+ " and contno= HEALTHYAMNT(a.insuredno,a.contno,'0') ";
					} else {
						tNeedPEN_SQL = " select decode(count(*),0,0,1) from lcappnt a "
								+ " where appntno='"
								+ tLCInsuredSchema.getInsuredNo()
								+ "' "
								+ " and contno='"
								+ tLCInsuredSchema.getContNo()
								+ "' "
								+ " and contno= HEALTHYAMNT(a.appntno,a.contno,'1') ";
					}
					// insured.setNeedPEN(NeedPEN);
					ExeSQL tExeSQL_NeedPEN = new ExeSQL();
					String tNeedPE = "";
					String tempNeedPE = "";
					tempNeedPE = tExeSQL_NeedPEN.getOneValue(tNeedPEN_SQL);
					if (tempNeedPE != null && !tempNeedPE.equals("")) {
						tNeedPE = tempNeedPE.replaceAll("[^0-9]", "");
					} else {
						insured.setNeedPEN("0");
					}
					if (tNeedPE != null && !tNeedPE.equals("")
							&& Integer.parseInt(tNeedPE) > 0) {
						insured.setNeedPEN("1");
					} else {
						insured.setNeedPEN("0");
					}

					insured.setCreditGrade(tLCInsuredSchema.getCreditGrade());
					insured.setDegree(tLCInsuredSchema.getDegree());
					insured.setDiseaseSumAmnt(new Double(DSumDangerAmnt));// 累积重疾风险保额
					String tDrinkFlagSql = "select count(*)"
							+ " from lccustomerimpartparams"
							+ " where impartcode in ('A0103','A0503')"
							+ " and impartver in ('A01','A05') "
							// +" and customernotype='1'"//被保人
							+ " and customerno='"
							+ tLCInsuredSchema.getInsuredNo()
							+ "' and contno='" + mContNo + "'";
					if (mLCContSchema.getPrtNo() != null
							&& !mLCContSchema.getPrtNo().equals("")
							&& mLCContSchema.getPrtNo().length() == 14
							&& mLCContSchema.getPrtNo().substring(2, 4)
									.equals("51")
							&& tLCInsuredSchema.getSequenceNo() != null
							&& !tLCInsuredSchema.getSequenceNo().equals(""))// 家庭单
					{
						tDrinkFlagSql = "select 0 from dual where 1=1 ";
						if (tLCInsuredSchema.getSequenceNo().equals("1")
								|| tLCInsuredSchema.getSequenceNo()
										.equals("-1")) {
							tDrinkFlagSql = "select count(*)"
									+ " from lccustomerimpartparams"
									+ " where impartcode = 'D0103'"
									+ " and impartver = 'D01' "
									// +" and customernotype='1'"//被保人
									+ " and customerno='"
									+ tLCInsuredSchema.getInsuredNo()
									+ "' and impartparamno in ('1','2','3')"
									+ " and contno='" + mContNo + "'";
						} else if (tLCInsuredSchema.getSequenceNo().equals("2")) {
							tDrinkFlagSql = "select count(*)"
									+ " from lccustomerimpartparams"
									+ " where impartcode = 'D0103'"
									+ " and impartver = 'D01' "
									// +" and customernotype='1'"//被保人
									+ " and customerno='"
									+ tLCInsuredSchema.getInsuredNo()
									+ "' and impartparamno in ('4','5','6')"
									+ " and contno='" + mContNo + "'";
						}

					}
					String tDrinkFlag = "";
					tDrinkFlag = tempExeSQL.getOneValue(tDrinkFlagSql);// 是否有饮酒习惯
					if (tDrinkFlag != null && !tDrinkFlag.equals("")
							&& Integer.parseInt(tDrinkFlag) > 0) {
						insured.setDrinkFlag("1");// 饮酒标记
					} else {
						insured.setDrinkFlag("0");
					}
					// tongmeng 2009-03-26 add
					// 增加生存受益人是被保人本人,但姓名不一致
					String tLiveSefSQL = " select decode(count(*),0,0,1) from lcbnf a "
							+ " where contno='"
							+ tLCInsuredSchema.getContNo()
							+ "' and bnftype='0' "
							+ " and insuredno='"
							+ tLCInsuredSchema.getInsuredNo()
							+ "' "
							+ " and relationtoinsured is not null "
							+ " and relationtoinsured ='00' "
							+ " and name not in "
							+ " (select name from lcinsured where contno=a.contno and insuredno=a.insuredno)";
					String tValue = "";
					String tempValue = "";
					tempValue = tempExeSQL.getOneValue(tLiveSefSQL);
					if (tempValue != null && !tempValue.equals("")) {
						tValue = tempValue.replaceAll("[^0-9]", "");
					} else {
						insured.setLiveBnfSelf("0");
					}
					if (tValue != null && !tValue.equals("")
							&& Integer.parseInt(tValue) > 0) {
						insured.setLiveBnfSelf("1");
					} else {
						insured.setLiveBnfSelf("0");
					}

					// SSumDieAmnt
					insured.setSumDieAmnt(new Double(SSumDieAmnt));
					// tongmeng 2009-04-02 add
					// 增加身故受益人是被保人本人
					// tongmeng 2009-04-23 modify
					// 校验身故受益人和被保人的姓名.
					String tDieSefSQL = " select decode(count(*),0,0,1) from lcbnf a "
							+ " where contno='"
							+ tLCInsuredSchema.getContNo()
							+ "' and bnftype='1' "
							+ " and insuredno='"
							+ tLCInsuredSchema.getInsuredNo()
							+ "' "
							// + " and relationtoinsured is not null "
							// + " and relationtoinsured ='00' ";
							+ " and name=(select name from lcinsured where "
							+ " contno=a.contno and insuredno=a.insuredno) ";
					tValue = "";
					tempValue = tempExeSQL.getOneValue(tDieSefSQL);
					if (tempValue != null && !tempValue.equals("")) {
						tValue = tempValue.replaceAll("[^0-9]", "");
					} else {
						insured.setDieBnfSelf("0");
						tValue = "";
					}
					if (tValue != null && !tValue.equals("")
							&& Integer.parseInt(tValue) > 0) {
						insured.setDieBnfSelf("1");
					} else {
						insured.setDieBnfSelf("0");
					}
					//
					/** 此处用SQL查询出该合同该被保人下所有生存受益人受益比例之和、身故受益人受益比例之和 */
					String tLiveBnfSql = "select sum(bnflot)"
							+ " from lcbnf where insuredno = '"
							+ tLCInsuredSchema.getInsuredNo() + "'"
							+ " and bnftype = '0' and contno='" + mContNo + "'"
							+ " group by bnfgrade" + " order by bnfgrade";// 生存受益人
					String tDeadBnfSql = "select sum(bnflot)"
							+ " from lcbnf where insuredno = '"
							+ tLCInsuredSchema.getInsuredNo() + "'"
							+ " and bnftype = '1' and contno='" + mContNo + "'"
							+ " group by bnfgrade" + " order by bnfgrade";// 身故受益人
					SSRS LiveBnfs = new SSRS();// 生存受益人
					SSRS DeadBnfs = new SSRS();// 身故受益人
					LiveBnfs = tempExeSQL.execSQL(tLiveBnfSql);
					DeadBnfs = tempExeSQL.execSQL(tDeadBnfSql);
					if (LiveBnfs.MaxRow > 0) {
						if (!(LiveBnfs.GetText(1, 1) == null || ""
								.equals(LiveBnfs.GetText(1, 1)))) {
							insured.setFirstSurviveBnfSum(Double
									.valueOf(LiveBnfs.GetText(1, 1)));// 第一顺序生存受益人受益比例之和
						}
					}
					if (LiveBnfs.MaxRow > 1) {
						if (!(LiveBnfs.GetText(2, 1) == null || ""
								.equals(LiveBnfs.GetText(2, 1)))) {
							insured.setSecondSurviveBnfSum(Double
									.valueOf(LiveBnfs.GetText(2, 1)));
						}
					}
					if (LiveBnfs.MaxRow > 2) {
						if (!(LiveBnfs.GetText(3, 1) == null || ""
								.equals(LiveBnfs.GetText(3, 1)))) {
							insured.setThirdSurviveBnfSum(Double
									.valueOf(LiveBnfs.GetText(3, 1)));
						}
					}
					if (LiveBnfs.MaxRow > 3) {
						if (!(LiveBnfs.GetText(4, 1) == null || ""
								.equals(LiveBnfs.GetText(4, 1)))) {
							insured.setFourthSurviveBnfSum(Double
									.valueOf(LiveBnfs.GetText(4, 1)));
						}
					}
					// tongmeng 2009-03-26 add
					// 增加是否录入身故受益人
					if (DeadBnfs.MaxRow > 0) {
						insured.setDieBnfFlag("1");
					} else {
						insured.setDieBnfFlag("0");
					}

					if (DeadBnfs.MaxRow > 0) {
						if (!(DeadBnfs.GetText(1, 1) == null || ""
								.equals(DeadBnfs.GetText(1, 1)))) {
							insured.setFirstDieBnfSum(Double.valueOf(DeadBnfs
									.GetText(1, 1)));
						}
					}
					if (DeadBnfs.MaxRow > 1) {
						if (!(DeadBnfs.GetText(2, 1) == null || ""
								.equals(DeadBnfs.GetText(2, 1)))) {
							insured.setSecondDieBnfSum(Double.valueOf(DeadBnfs
									.GetText(2, 1)));
						}
					}
					if (DeadBnfs.MaxRow > 2) {
						if (!(DeadBnfs.GetText(3, 1) == null || ""
								.equals(DeadBnfs.GetText(3, 1)))) {
							insured.setThirdDieBnfSum(Double.valueOf(DeadBnfs
									.GetText(3, 1)));
						}
					}
					if (DeadBnfs.MaxRow > 3) {
						if (!(DeadBnfs.GetText(4, 1) == null || ""
								.equals(DeadBnfs.GetText(4, 1)))) {
							insured.setFourthDieBnfSum(Double.valueOf(DeadBnfs
									.GetText(4, 1)));
						}
					}
					int tInsAge = PubFun.calInterval(
							tLCInsuredSchema.getBirthday(),
							tLCContSchema.getPolApplyDate(), "Y");
					insured.setInsuredAppAge(Double.valueOf(String
							.valueOf(tInsAge)));// 投保年龄
					insured.setInsuredStat(tLCInsuredSchema.getInsuredStat());
					if (!((tLCInsuredSchema.getJoinCompanyDate() == null))
							|| "".equals(tLCInsuredSchema.getJoinCompanyDate())) {
						theDate = tLCInsuredSchema.getJoinCompanyDate()
								+ " 00:00:00";
						insured.setJoinCompanyDate(sdf.parse(theDate));
					}
					insured.setLicense(tLCInsuredSchema.getLicense());
					insured.setLicenseType(tLCInsuredSchema.getLicenseType());
					insured.setLifeSumAmnt(Double.valueOf(String
							.valueOf(LSumDangerAmnt)));// 累计寿险风险保额
					insured.setMarriage(tLCInsuredSchema.getMarriage());
					if (!(tLCInsuredSchema.getMarriageDate() == null || ""
							.equals(tLCInsuredSchema.getMarriageDate()))) {
						theDate = tLCInsuredSchema.getMarriageDate()
								+ " 00:00:00";
						insured.setMarriageDate(sdf.parse(theDate));
					}
					insured.setMedicalSumAmnt(new Double(SSumDangerAmnt));// 累计意外医疗风险保额
					insured.setNationality(tLCInsuredSchema.getNationality());
					insured.setNativePlace(tLCInsuredSchema.getNativePlace());
					// 下面的SQL查询出既往加费次数
					String tOaddFeeSql = "select sum(A.y) from ( "
							+ " select contno x,decode(count(*),0,0,1) y from lcprem "
							+ " where polno in (select polno from lcpol where insuredno='"
							+ tLCInsuredSchema.getInsuredNo() + "' ) "
							+ " and payplancode like '000000%%' and contno<>'"
							+ this.mContNo + "' group by contno " + " ) A ";
					String OAddFeeTimes = tempExeSQL.getOneValue(tOaddFeeSql);
					if (!(OAddFeeTimes == null || "".equals(OAddFeeTimes))) {
						insured.setOAddFeeTimes(Double.valueOf(OAddFeeTimes));// 既往投保加费次数
					}
					insured.setOccupationCode(tLCInsuredSchema
							.getOccupationCode());
					insured.setOccupationType(tLCInsuredSchema
							.getOccupationType());
					String tSQL_ChangePol = "select count(*) from lcuwmaster where insuredno='"
							+ tLCInsuredSchema.getInsuredNo()
							+ "' "
							+ " and changepolflag<>'0' and contno<>'"
							+ this.mContNo + "' ";
					String tChangeValue = "";
					tChangeValue = tempExeSQL.getOneValue(tSQL_ChangePol);
					if (tChangeValue != null && !tChangeValue.equals("")) {
						insured.setOChangeFlag(new Double(tChangeValue));// 既往承保计划变更次数
					}
					// 下面的SQL查询是否有既往理赔记录
					String tOClaimSql = "select count(*) from llcase where customerno='"
							+ tLCInsuredSchema.getInsuredNo() + "'";
					String OClaimFlag = "";
					String tempOClaimFlag = "";
					tempOClaimFlag = tempExeSQL.getOneValue(tOClaimSql);
					if (tempOClaimFlag != null && !tempOClaimFlag.equals("")) {
						OClaimFlag = tempOClaimFlag.replaceAll("[^0-9]", "");
					} else {
						insured.setOClaimFlag("0");// 既往理赔记录标记
					}
					if (OClaimFlag != null && !OClaimFlag.equals("")
							&& Integer.parseInt(OClaimFlag) > 0) {
						insured.setOClaimFlag("1");// 既往理赔记录标记
					} else {
						insured.setOClaimFlag("0");// 既往理赔记录标记
					}
					String ODeferSql = "select count(*) from lccont where uwflag='2' and contno in "
							+ "(select contno from lcpol where insuredno='"
							+ tLCInsuredSchema.getInsuredNo()
							+ "' ) "
							+ " and contno<>'" + this.mContNo + "' ";
					String tODeferTimes = tempExeSQL.getOneValue(ODeferSql);
					if (!(tODeferTimes == null || "".equals(tODeferTimes))) {
						insured.setODeferTimes(Double.valueOf(tODeferTimes));// 既往延期承保次数
					}
					String oHealthSql = "select count(*) from LCPENotice where contno!='"
							+ mContNo
							+ "' and customerno='"
							+ tLCInsuredSchema.getInsuredNo() + "'";
					String oHealthFlag = "";
					oHealthFlag = tempExeSQL.getOneValue(oHealthSql);
					if (oHealthFlag != null && !oHealthFlag.equals("")
							&& Integer.parseInt(oHealthFlag) > 0) {
						insured.setOHealthFlag("1");// 既往保单体检标记
						insured.setOHealthTimes(Double.valueOf(oHealthFlag));// 既往投保体检次数
					} else {
						insured.setOHealthFlag("0");// 既往保单体检标记
						insured.setOHealthTimes(Double.valueOf("0"));
					}

					String OInvaliSql = "select count(*) from lccontstate where statetype = 'Available'"
							+ " and state = '1' and enddate <> null"
							+ " and contno in (select contno from lcinsured where insuredno = '"
							+ tLCInsuredSchema.getInsuredNo() + "')";
					String tOInvaliTimes = tempExeSQL.getOneValue(OInvaliSql);
					if (!(tOInvaliTimes == null || "".equals(tOInvaliTimes))) {
						insured.setOInvaliTimes(Double.valueOf(tOInvaliTimes));// 既往保单失效次数
					}
					String FirstOCPSql = "select count(*) from lcinsured where OccupationType='1' and insuredno='"
							+ insured.getInsuredNo()
							+ "' and contno<>'"
							+ this.mContNo + "' ";
					String tOOccupationType = "";
					tOOccupationType = tempExeSQL.getOneValue(FirstOCPSql);
					if (tOOccupationType != null
							&& !tOOccupationType.equals("")
							&& Integer.parseInt(tOOccupationType) > 0) {
						insured.setFirstOCPType("1");// 既往职业类别为一级
					} else {
						insured.setFirstOCPType("0");// 既往职业类别为一级
					}
					String SecondOCPSql = "select count(*) from lcinsured where OccupationType='2' and insuredno='"
							+ insured.getInsuredNo()
							+ "' and contno<>'"
							+ this.mContNo + "' ";
					String tSecondOCPType = "";
					tSecondOCPType = tempExeSQL.getOneValue(SecondOCPSql);
					if (tSecondOCPType != null && !tSecondOCPType.equals("")
							&& Integer.parseInt(tSecondOCPType) > 0) {
						insured.setSecondOCPType("1");// 既往职业类别为二级
					} else {
						insured.setSecondOCPType("0");// 既往职业类别为二级
					}
					String ThirdOCPSql = "select count(*) from lcinsured where OccupationType='3' and insuredno='"
							+ insured.getInsuredNo()
							+ "' and contno<>'"
							+ this.mContNo + "' ";
					String tThirdOCPType = "";
					tThirdOCPType = tempExeSQL.getOneValue(ThirdOCPSql);
					if (tThirdOCPType != null && !tThirdOCPType.equals("")
							&& Integer.parseInt(tThirdOCPType) > 0) {
						insured.setThirdOCPType("1");// 既往职业类别为三级
					} else {
						insured.setThirdOCPType("0");// 既往职业类别为三级
					}
					String FourthOCPSql = "select count(*) from lcinsured where OccupationType='4' and insuredno='"
							+ insured.getInsuredNo()
							+ "' and contno<>'"
							+ this.mContNo + "' ";
					String tFourthOCPType = "";
					tFourthOCPType = tempExeSQL.getOneValue(FourthOCPSql);
					if (tFourthOCPType != null && !tFourthOCPType.equals("")
							&& Integer.parseInt(tFourthOCPType) > 0) {
						insured.setFourthOCPType("1");// 既往职业类别为四级
					} else {
						insured.setFourthOCPType("0");// 既往职业类别为四级
					}
					String FifthOCPSql = "select  count(*) from lcinsured where OccupationType='5' and insuredno='"
							+ insured.getInsuredNo()
							+ "' and contno<>'"
							+ this.mContNo + "' ";
					String tFifthOCPType = "";
					tFifthOCPType = tempExeSQL.getOneValue(FifthOCPSql);
					if (tFifthOCPType != null && !tFifthOCPType.equals("")
							&& Integer.parseInt(tFifthOCPType) > 0) {
						insured.setFifthOCPType("1");// 既往职业类别为五级
					} else {
						insured.setFifthOCPType("0");// 既往职业类别为五级
					}
					String SixthOCPSql = "select count(*) from lcinsured where OccupationType='6' and insuredno='"
							+ insured.getInsuredNo()
							+ "' and contno<>'"
							+ this.mContNo + "' ";
					String tSixthOCPType = "";
					tSixthOCPType = tempExeSQL.getOneValue(SixthOCPSql);
					if (tSixthOCPType != null && !tSixthOCPType.equals("")
							&& Integer.parseInt(tSixthOCPType) > 0) {
						insured.setSixthOCPType("1");// 既往职业类别为六级
					} else {
						insured.setSixthOCPType("0");// 既往职业类别为六级
					}
					String NOOCPSql = "select count(*) from lcinsured where OccupationType='z' and insuredno='"
							+ insured.getInsuredNo()
							+ "' and contno<>'"
							+ this.mContNo + "' ";
					String tNOOCPType = "";
					tNOOCPType = tempExeSQL.getOneValue(NOOCPSql);
					if (tNOOCPType != null && !tNOOCPType.equals("")
							&& Integer.parseInt(tNOOCPType) > 0) {
						insured.setNOOCPType("1");// 既往职业类别为拒保类职业
					} else {
						insured.setNOOCPType("0");// 既往职业类别为拒保类职业
					}
					String NegativeOCPSql = "select count(*) from lcinsured where OccupationType='-1' and insuredno='"
							+ insured.getInsuredNo()
							+ "' and contno<>'"
							+ this.mContNo + "' ";
					String tNegativeOCPType = "";
					tNegativeOCPType = tempExeSQL.getOneValue(NegativeOCPSql);
					if (tNegativeOCPType != null
							&& !tNegativeOCPType.equals("")
							&& Integer.parseInt(tNegativeOCPType) > 0) {
						insured.setNegativeOCPType("1");// 既往职业类别为-1级
					} else {
						insured.setNegativeOCPType("0");// 既往职业类别为-1级
					}
					// tongmeng 2009-03-25 add
					// 既往职业类别高于本次
					String OTypeCPSql = "select count(*) from lcinsured where "
							+ " ((OccupationType='-1' and '"
							+ tLCInsuredSchema.getOccupationType()
							+ "'<>'-1' )"
							+ " or (OccupationType<>'-1' and  OccupationType>"
							+ tLCInsuredSchema.getOccupationType() + " ) )"
							+ " and insuredno='" + insured.getInsuredNo()
							+ "' and contno<>'" + this.mContNo + "' ";
					String tOOType = "";
					tOOType = tempExeSQL.getOneValue(OTypeCPSql);
					if (tOOType != null && !tOOType.equals("")
							&& Integer.parseInt(tOOType) > 0) {
						insured.setOOMaxType("1");// 既往职业类别高于本次
					} else {
						insured.setOOMaxType("0");// 既往职业类别高于本次
					}

					String LateOUWSql = "select count(*) from lccont where uwflag='2' "
							+ " and contno in (select contno from lcpol where insuredno='"
							+ insured.getInsuredNo()
							+ "')"
							+ " and contno!='"
							+ mContNo + "'";
					String tLateOUWFlag = "";
					tLateOUWFlag = tempExeSQL.getOneValue(LateOUWSql);
					if (tLateOUWFlag != null && !tLateOUWFlag.equals("")
							&& Integer.parseInt(tLateOUWFlag) > 0) {
						insured.setLateOUWFlag("1");// 既往核保结论为延期
					} else {
						insured.setLateOUWFlag("0");// 既往核保结论为延期
					}
					String StandarOUWSql = " select count(*) from lccont where uwflag='9' "
							+ " and contno in (select contno from lcpol where insuredno='"
							+ insured.getInsuredNo()
							+ "')"
							+ " and contno!='"
							+ mContNo + "'";
					String tStandarOUWFlag = "";
					tStandarOUWFlag = tempExeSQL.getOneValue(StandarOUWSql);
					if (tStandarOUWFlag != null && !tStandarOUWFlag.equals("")
							&& Integer.parseInt(tStandarOUWFlag) > 0) {
						insured.setStandarOUWFlag("1");// 既往核保结论为标准承保
					} else {
						insured.setStandarOUWFlag("0");// 既往核保结论为标准承保
					}
					String SStandarOUWSql = " select count(*) from lccont where uwflag='4' "
							+ " and contno in (select contno from lcpol where insuredno='"
							+ insured.getInsuredNo()
							+ "')"
							+ " and contno!='"
							+ mContNo + "'";
					String tSStandarOUWFlag = "";
					// tSStandarOUWFlag=tempExeSQL.getOneValue(SStandarOUWSql);
					// if(tSStandarOUWFlag!=null&&!tSStandarOUWFlag.equals("")&&Integer.parseInt(tSStandarOUWFlag)>0){
					// insured.setSecStandardUW("1");//既往核保结论为次标准承保
					// }else{
					// insured.setSecStandardUW("0");
					// }
					String RejectOUWSql = "select count(*) from lccont where uwflag='1'"
							+ " and contno in (select contno from lcpol where insuredno='"
							+ insured.getInsuredNo()
							+ "')"
							+ " and contno!='"
							+ mContNo + "'";
					String tRejectOUWFlag = "";
					tRejectOUWFlag = tempExeSQL.getOneValue(RejectOUWSql);
					if (tRejectOUWFlag != null && !tRejectOUWFlag.equals("")
							&& Integer.parseInt(tRejectOUWFlag) > 0) {
						insured.setRejectOUWFlag("1");// 既往核保结论为拒保
						insured.setORejeceTimes(Double.valueOf(tRejectOUWFlag));// 既往拒保次数
					} else {
						insured.setRejectOUWFlag("0");// 既往核保结论为拒保
						insured.setORejeceTimes(Double.valueOf("0"));// 既往拒保次数
					}
					String BackOUWSql = " select count(*) from lccont where uwflag='a' "
							+ " and contno in (select contno from lcpol where insuredno='"
							+ insured.getInsuredNo()
							+ "')"
							+ " and contno!='"
							+ mContNo + "'";
					String tBackOUWFlag = "";
					tBackOUWFlag = tempExeSQL.getOneValue(BackOUWSql);
					if (tBackOUWFlag != null && !tBackOUWFlag.equals("")
							&& Integer.parseInt(tBackOUWFlag) > 0) {
						insured.setBackOUWFlag("1");// 既往核保结论为撤单
					} else {
						insured.setBackOUWFlag("0");// 既往核保结论为撤单
					}
					String AddOUWSql = "select count(*) from lccont where uwflag='3' "
							+ " and contno in (select contno from lcpol where insuredno='"
							+ insured.getInsuredNo()
							+ "')"
							+ " and contno!='"
							+ mContNo + "'";
					String tAddOUWFlag = "";
					tAddOUWFlag = tempExeSQL.getOneValue(AddOUWSql);
					if (tAddOUWFlag != null && !tAddOUWFlag.equals("")
							&& Integer.parseInt(tAddOUWFlag) > 0) {
						insured.setAddOUWFlag("1");// 既往核保结论为加费承保
					} else {
						insured.setAddOUWFlag("0");// 既往核保结论为加费承保
					}
					String SpecialOUWSql = "select count(*) from lccont where uwflag='4' "
							+ " and contno in (select contno from lcpol where insuredno='"
							+ insured.getInsuredNo()
							+ "')"
							+ " and contno!='"
							+ mContNo + "'";

					String tSpecialOUWFlag = "";
					tSpecialOUWFlag = tempExeSQL.getOneValue(SpecialOUWSql);
					if (tSpecialOUWFlag != null
							&& !tSpecialOUWFlag.endsWith("")
							&& Integer.parseInt(tSpecialOUWFlag) > 0) {
						insured.setSpecialOUWFlag("1");// 既往核保结论为特约承保
					} else {
						insured.setSpecialOUWFlag("0");// 既往核保结论为特约承保
					}
					String OOReSql = " select count(*) from lpedoritem where edortype='RE' "
							+ " and contno in (select contno from lcinsured where insuredno = '"
							+ tLCInsuredSchema.getInsuredNo()
							+ "') and contno<>'" + this.mContNo + "' ";
					String tOReTimes = tempExeSQL.getOneValue(OOReSql);
					if (!(tOReTimes == null || "".equals(tOReTimes))) {
						insured.setOReTimes(Double.valueOf(tOReTimes));// 既往保单复效次数
					}
					String OReleveSql = "select count(*),1 from lpedoritem where edortype in ('CT','WT','XT') "
							+ "AND EDORSTATE='0' and contno in (select contno from lcinsured where "
							+ " insuredno='"
							+ tLCInsuredSchema.getInsuredNo()
							+ "')";
					String tOReleveTimes = tempExeSQL.getOneValue(OReleveSql);
					if (!(tOReleveTimes == null || "".equals(tOReleveTimes))) {
						insured.setOReleveTimes(Double.valueOf(tOReleveTimes));// 既往保单合同解除次数
						insured.setOWithdrawTimes(Double.valueOf(tOReleveTimes));// 既往撤件次数????
					}
					String OSurrenderSql = "select count(distinct edoracceptno) from lpedoritem where "
							+ "edortype in ('CT','WT','XT') and edorstate='0' and contno in "
							+ "(select contno from lcinsured where insuredno='"
							+ tLCInsuredSchema.getInsuredNo() + "') ";
					String tOSurrenderTimes = tempExeSQL
							.getOneValue(OSurrenderSql);
					insured.setOSurrenderTimes(Double.valueOf(tOSurrenderTimes));// 既往退保次数
					insured.setPosition(tLCInsuredSchema.getPosition());
					insured.setRelationToAppnt(tLCInsuredSchema
							.getRelationToAppnt());
					insured.setRgtAddress(tLCInsuredSchema.getRgtAddress());
					insured.setSalary(Double.valueOf(String
							.valueOf(tLCInsuredSchema.getSalary())));
					insured.setSex(tLCInsuredSchema.getSex());
					// 计算每天吸烟量
					String tSmokeSql = "select nvl(max(impartparam),'0')"
							+ " from lccustomerimpartparams"
							+ " where impartcode in ('A0102','A0502')"
							+ " and impartver in ('A01','A05') "
							+ " and impartparamno in ('1','3')"
							// +" and customernotype='1'"//被保人
							+ " and customerno='"
							+ tLCInsuredSchema.getInsuredNo()
							+ "' and contno='" + mContNo + "'";
					if (mLCContSchema.getPrtNo() != null
							&& !mLCContSchema.getPrtNo().equals("")
							&& mLCContSchema.getPrtNo().length() == 14
							&& mLCContSchema.getPrtNo().substring(2, 4)
									.equals("51")
							&& tLCInsuredSchema.getSequenceNo() != null
							&& !tLCInsuredSchema.getSequenceNo().equals(""))// 家庭单
					{
						tSmokeSql = "select 0 from dual where 1=1 ";
						if (tLCInsuredSchema.getSequenceNo().equals("1")
								|| tLCInsuredSchema.getSequenceNo()
										.equals("-1")) {
							tSmokeSql = "select nvl(max(impartparam),'0')"
									+ " from lccustomerimpartparams"
									+ " where impartcode = 'D0102'"
									+ " and impartver = 'D01' "
									+ " and impartparamno ='1'"
									// +" and customernotype='1'"//被保人
									+ " and customerno='"
									+ tLCInsuredSchema.getInsuredNo()
									+ "' and contno='" + mContNo + "'";
						} else if (tLCInsuredSchema.getSequenceNo().equals("2")) {
							tSmokeSql = "select nvl(max(impartparam),'0')"
									+ " from lccustomerimpartparams"
									+ " where impartcode = 'D0102'"
									+ " and impartver = 'D01' "
									+ " and impartparamno ='3'"
									// +" and customernotype='1'"//被保人
									+ " and customerno='"
									+ tLCInsuredSchema.getInsuredNo()
									+ "' and contno='" + mContNo + "'";
						}

					}
					try {
						String tSmoke = "";
						// String tempSmoke =
						tSmoke = tempExeSQL.getOneValue(tSmokeSql);// 吸烟量
						logger.debug("tSmoke = " + tSmoke);
						// if(tempSmoke!=null&&!tempSmoke.equals("")){
						// tSmoke = tempSmoke.replaceAll("[^0-9]", "");
						// }else{
						// insured.setSmokeAmount(new Double("0"));//每天吸烟量
						// }
						if (tSmoke == null || tSmoke.equals("")) {
							insured.setSmokeAmount(new Double("0"));// 每天吸烟量
						} else {
							insured.setSmokeAmount(new Double(tSmoke));// 每天吸烟量
						}
					} catch (Exception e) {
						CError.buildErr(this, mContNo + "吸烟量数据异常！");
						e.printStackTrace();
					}
					String tSmokeFlagSql = "select count(*)"
							+ " from lccustomerimpartparams"
							+ " where impartcode in ('A0102','A0502')"
							+ " and impartver in ('A01','A05') "
							// +" and customernotype='1'"//被保人
							+ " and impartparamno in ('1','2')"
							+ " and customerno='"
							+ tLCInsuredSchema.getInsuredNo()
							+ "' and contno='" + mContNo + "'";
					if (mLCContSchema.getPrtNo() != null
							&& !mLCContSchema.getPrtNo().equals("")
							&& mLCContSchema.getPrtNo().length() == 14
							&& mLCContSchema.getPrtNo().substring(2, 4)
									.equals("51")
							&& tLCInsuredSchema.getSequenceNo() != null
							&& !tLCInsuredSchema.getSequenceNo().equals(""))// 家庭单
					{
						tSmokeFlagSql = "select 0 from dual where 1=1 ";
						if (tLCInsuredSchema.getSequenceNo().equals("1")
								|| tLCInsuredSchema.getSequenceNo()
										.equals("-1")) {
							tSmokeFlagSql = "select count(*)"
									+ " from lccustomerimpartparams"
									+ " where impartcode = 'D0102'"
									+ " and impartver = 'D01' "
									// +" and customernotype='1'"//被保人
									+ " and customerno='"
									+ tLCInsuredSchema.getInsuredNo()
									+ "' and impartparamno in ('1','2')"
									+ " and contno='" + mContNo + "'";
						} else if (tLCInsuredSchema.getSequenceNo().equals("2")) {
							tSmokeFlagSql = "select count(*)"
									+ " from lccustomerimpartparams"
									+ " where impartcode = 'D0103'"
									+ " and impartver = 'D01' "
									// +" and customernotype='1'"//被保人
									+ " and customerno='"
									+ tLCInsuredSchema.getInsuredNo()
									+ "' and impartparamno in ('3','4')"
									+ " and contno='" + mContNo + "'";
						}

					}
					String tSmokeFlag = tempExeSQL.getOneValue(tSmokeFlagSql);// 是否有吸烟习惯
					if (tSmokeFlag != null && !tSmokeFlag.equals("")
							&& Integer.parseInt(tSmokeFlag) > 0) {
						insured.setSmokeFlag("1");
					} else {
						insured.setSmokeFlag("0");
					}
					insured.setSocialInsuNo(tLCInsuredSchema
							.getSocialInsuFlag());
					if (!(tLCInsuredSchema.getStartWorkDate() == null || ""
							.equals(tLCInsuredSchema.getStartWorkDate()))) {
						theDate = tLCInsuredSchema.getStartWorkDate()
								+ " 00:00:00";
						insured.setStartWorkDate(sdf.parse(theDate));
					}
					String sumMultSql = "select nvl(sum( case when mult=0 then 1 else mult end),0) from lcpol where insuredno='"
							+ tLCInsuredSchema.getInsuredNo() + "'";
					String tsumMult = tempExeSQL.getOneValue(sumMultSql);
					insured.setSumMult(tsumMult);// 累计投保份数
					// String
					// UWPolCountSql=" select count(*) from lwmission where activityid='0000001100' "
					String UWPolCountSql = " select count(*) from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010028') "
							+ " and missionprop1 in (select prtno from lcpol where insuredno='"
							+ tLCInsuredSchema.getInsuredNo() + "') ";

					String tUWPolCount = tempExeSQL.getOneValue(UWPolCountSql);
					insured.setUWPolCount(Double.valueOf(tUWPolCount));// 待核保投保单数
					String tOccupationCode = tLCInsuredSchema
							.getOccupationCode();// 被保人职业代码
					LDOccupationDB tLDOccupationDB = new LDOccupationDB();
					tLDOccupationDB.setOccupationCode(tOccupationCode);
					if (!tLDOccupationDB.getInfo()) {
						CError.buildErr(this, "查询LDOccupation表失败！");
					}
					double tMEDRate = tLDOccupationDB.getMedRate();// 职业代码与医疗险费率比例
					insured.setOMRate(Double.valueOf(String.valueOf(tMEDRate)));
					// String
					// ThirdImpartSql="select * from lccustomerimpart where"
					// +" impartver='101' and impartcode "
					// +"in('020','030','040','050','060','080','090') "
					// +"and impartparammodle <>'否' and contno='"
					// +mContNo+"' and customerno='"+tLCInsuredSchema.getInsuredNo()+"'";
					// SSRS tThirdImpartSSRS = new SSRS();
					// tThirdImpartSSRS=tempExeSQL.execSQL(ThirdImpartSql);
					// if(tThirdImpartSSRS.MaxRow>0){
					// insured.setThirdImpartOption("1");
					// }else{
					// insured.setThirdImpartOption("0");
					// }
					String OUWFlagSql = "select count(*) from lccont where insuredno='"
							+ tLCInsuredSchema.getInsuredNo()
							+ "' and uwflag='4'";
					String tOUWSSRS = "";
					tOUWSSRS = tempExeSQL.getOneValue(OUWFlagSql);
					if (tOUWSSRS != null && !tOUWSSRS.equals("")
							&& Integer.parseInt(tOUWSSRS) > 0) {
						insured.setOUWFlag4("1");// 既往承保记录有次标准体
					} else {
						insured.setOUWFlag4("0");// 既往承保记录有次标准体
					}
					String tEdorUWFlagSql = "select count(*) from lwmission where missionprop2 in (select contno from lcinsured where insuredno='"
							+ tLCInsuredSchema.getInsuredNo()
							+ "') and activityid='0000000005'";
					String tEdorUWSSRS = tempExeSQL.getOneValue(tEdorUWFlagSql);
					if (tEdorUWSSRS != null && !tEdorUWSSRS.equals("")
							&& Integer.parseInt(tEdorUWSSRS) > 0) {
						insured.setEdorUWFlag("1");// 正在申请保全核保的记录
					} else {
						insured.setEdorUWFlag("0");// 正在申请保全核保的记录
					}
					String tRNewUWFlagSql = "select count(*) from lcpol where appflag='9' and uwflag='5' and insuredno='"
							+ tLCInsuredSchema.getInsuredNo() + "' ";
					String tRNewUWSSRS = tempExeSQL.getOneValue(tRNewUWFlagSql);
					if (tRNewUWSSRS != null && !tRNewUWSSRS.equals("")
							&& Integer.parseInt(tRNewUWSSRS) > 0) {
						insured.setReNewUWFlag("1");// 正在申请续期核保记录
					} else {
						insured.setReNewUWFlag("0");// 正在申请续期核保记录
					}
					String tRegisterSql = "select count(*) from LLCase where customerno='"
							+ tLCInsuredSchema.getInsuredNo() + "'";
					String tRegisterSSRS = "";
					tRegisterSSRS = tempExeSQL.getOneValue(tRegisterSql);
					if (tRegisterSSRS != null && !tRegisterSSRS.equals("")
							&& Integer.parseInt(tRegisterSSRS) > 0) {
						insured.setORegister("1");// 既往立案标记
					} else {
						insured.setORegister("0");
					}
					String ttORPTnoRGTSql = "select count(*) from llsubreport where "
							+ "not exists(select 1 from llregister where rgtno=subrptno)"
							+ " and customerno='"
							+ tLCInsuredSchema.getInsuredNo()
							+ "'"
							+ "and llsubreport.makedate <= substr(sysdate,1) "
							+ "and llsubreport.makedate>=substr(add_months(sysdate,-24),1)";
					String tORPTnoSSRS = "";
					tORPTnoSSRS = tempExeSQL.getOneValue(ttORPTnoRGTSql);
					if (tORPTnoSSRS != null && !tORPTnoSSRS.equals("")
							&& Integer.parseInt(tORPTnoSSRS) > 0) {
						insured.settORPTnoRGT("1");// 既往两年内发生报案但未立案标记
					} else {
						insured.settORPTnoRGT("0");
					}
					String tOEdorUWSql = "select count(*) from LPUWMasterMain where State='4' and "
							+ " insuredno='"
							+ tLCInsuredSchema.getInsuredNo()
							+ "'";
					String tOEdorUWSSRS = "";
					tOEdorUWSSRS = tempExeSQL.getOneValue(tOEdorUWSql);
					if (tOEdorUWSSRS != null && !tOEdorUWSSRS.equals("")
							&& Integer.parseInt(tOEdorUWSSRS) > 0) {
						insured.setOEdorUWFlag("1");// 既往保全核保结论
					} else {
						insured.setOEdorUWFlag("0");
					}

					String tAppHosSql = "";
					if (!FreeFlag) {
						tAppHosSql = "select blacklistflag,hospitalname from ldhospital where hospitcode in "
								+ "(select hospitcode from lcpenotice where contno='"
								+ mContNo
								+ "' and customerno='"
								+ tLCInsuredSchema.getInsuredNo()
								+ "'and customertype='I')";
					} else {
						tAppHosSql = "select blacklistflag,hospitalname from ldhospital where hospitcode in "
								+ "(select hospitcode from lcpenotice where contno='"
								+ mContNo
								+ "' and customerno='"
								+ tLCInsuredSchema.getInsuredNo()
								+ "'and customertype='A')";
					}
					SSRS tInsHosBlack = tempExeSQL.execSQL(tAppHosSql);
					if (tInsHosBlack.getMaxRow() > 0) {
						insured.setInsHosBlack(tInsHosBlack.GetText(1, 1));
						insured.setInsHopName(tInsHosBlack.GetText(1, 2));
					}

					// 计算饮酒量
					String tDrinkSql = "select max(A.x) from ("
							+ " select nvl(max(impartparam),'0')  x"
							+ " from lccustomerimpartparams"
							+ " where impartcode = 'A0103'"
							+ " and impartver = 'A01'"
							+ " and impartparamno='2'" + " and customerno='"
							+ tLCInsuredSchema.getInsuredNo() + "' "
							+ " and contno='"
							+ mContNo
							+ "'"
							+ " union"
							+ " select nvl(max(impartparam),'0') x"
							+ " from lccustomerimpartparams"
							+ " where impartcode ='A0503'"
							+ " and impartver = 'A05'"
							// + " and impartparamno in ('2','3','4')"
							+ " and impartparamno in ('4')" // 09-09-27目前只取白酒
							+ " and customerno='"
							+ tLCInsuredSchema.getInsuredNo() + "'"
							+ " and contno='" + mContNo + "' )A";
					if (mLCContSchema.getPrtNo() != null
							&& !mLCContSchema.getPrtNo().equals("")
							&& mLCContSchema.getPrtNo().length() == 14
							&& mLCContSchema.getPrtNo().substring(2, 4)
									.equals("51")
							&& tLCInsuredSchema.getSequenceNo() != null
							&& !tLCInsuredSchema.getSequenceNo().equals(""))// 家庭单
					{

						tDrinkSql = "select 0 from dual where 1=1 ";
						if (tLCInsuredSchema.getSequenceNo().equals("1")
								|| tLCInsuredSchema.getSequenceNo()
										.equals("-1")) {
							tDrinkSql = "select nvl(max(impartparam),'0')"
									+ " from lccustomerimpartparams"
									+ " where impartcode = 'D0103'"
									+ " and impartver = 'D01' "
									+ " and impartparamno ='2'"
									// +" and customernotype='1'"//被保人
									+ " and customerno='"
									+ tLCInsuredSchema.getInsuredNo()
									+ "' and contno='" + mContNo + "'";
						} else if (tLCInsuredSchema.getSequenceNo().equals("2")) {
							tDrinkSql = "select nvl(max(impartparam),'0')"
									+ " from lccustomerimpartparams"
									+ " where impartcode = 'D0103'"
									+ " and impartver = 'D01' "
									+ " and impartparamno ='5'"
									// +" and customernotype='1'"//被保人
									+ " and customerno='"
									+ tLCInsuredSchema.getInsuredNo()
									+ "' and contno='" + mContNo + "'";
						}
					}
					try {
						String tDrink = "";
						// String tempDrink
						tDrink = tempExeSQL.getOneValue(tDrinkSql);// 饮酒量
						logger.debug("tDrink = " + tDrink);
						// if(tempDrink!=null&&!tempDrink.equals("")){
						// tDrink = tempDrink.replaceAll("[^0-9]", "");
						// }else{
						// insured.setDrink(new Double("0"));//饮酒量
						// }
						if (tDrink == null || tDrink.equals("")) {
							insured.setDrink(new Double("0"));// 饮酒量
						} else {
							insured.setDrink(new Double(tDrink));// 饮酒量
						}
					} catch (Exception e) {
						CError.buildErr(this, mContNo + "饮酒量数据有误！");
						e.printStackTrace();
					}

					insured.setAddRiskPrem(Double.valueOf(String
							.valueOf(AllSumAmnt)));// 累计风险保额
					String tOMedRateSql = "select nvl(max(medrate),0) from ldoccupation where occupationcode"
							+ " in(select occupationcode from lcinsured where insuredno='"
							+ tLCInsuredSchema.getInsuredNo()
							+ "' and contno<>'"
							+ tLCInsuredSchema.getContNo()
							+ "') ";
					String tOMedRate = tempExeSQL.getOneValue(tOMedRateSql);
					if (tOMedRate == null || tOMedRate.equals("")) {
						insured.setOMedRate(new Double("0"));
					} else {
						insured.setOMedRate(new Double(tOMedRate));// 既往投保的职业医疗险费率
					}
					String tBussFlagSql = "select count(*) from lccont where bussflag='Y' and insuredno='"
							+ tLCInsuredSchema.getInsuredNo() + "'";
					String tBussFlag = tempExeSQL.getOneValue(tBussFlagSql);
					if ("0".equals(tBussFlag)) {
						insured.setComrAppFlag("0");// 是否有商业因素标准体承保记录
					} else {
						insured.setComrAppFlag("1");
					}

					// 2009-2-11 ln add 既往有逾期未回复调取医疗资料撤单记录
					String tSqlNew = " select 1 from lccont l where  contno<>'"
							+ mContNo
							+ "' "
							+ " and exists (select 1 from lccuwmaster where contno=l.contno and commonreasoncode='12' and passflag='a')"
							+ " and exists (select 1 from lcinsured where contno=l.contno and insuredno='"
							+ tLCInsuredSchema.getInsuredNo() + "' )";
					String tSSRSNew = tempExeSQL.getOneValue(tSqlNew);
					if (tSSRSNew != null && tSSRSNew.equals("1")) {
						insured.setWithDHispital("1");
					} else {
						insured.setWithDHispital("0");
					}

					// 2009-2-11 ln add 有愈期未交费撤单记录，且该记录中有次标准体承保的轨迹
					tSqlNew = " select 1 from lccont l where  1=1 "
							+ " and exists (select 1 from lccuwmaster where contno=l.contno and commonreasoncode='99' and passflag='a')"
							+ " and exists (select 1 from lccuwsub where contno=l.contno and passflag='4')"
							+ " and exists (select 1 from lcinsured where contno=l.contno and insuredno='"
							+ tLCInsuredSchema.getInsuredNo() + "' )";
					tSSRSNew = tempExeSQL.getOneValue(tSqlNew);
					if (tSSRSNew != null && tSSRSNew.equals("1")) {
						insured.setHaveNoFeeWithD("1");
					} else {
						insured.setHaveNoFeeWithD("0");
					}

					// 2009-2-11 ln add 本次或既往体检录入的体检结果不为正常
					// tongmeng 2009-08-05 modify
					// 兼容老系统体检数据
					// tongmeng 2009-08-10 modify
					// 修改老系统校验逻辑.
					tSqlNew = " select distinct 1 from lccont l where 1=1 "
							+ " and exists (select 1 from lcpenotice where contno=l.contno "
							+ " and (peresult is null or peresult='' or peresult like '异常%') "
							+ " and customerno='"
							+ tLCInsuredSchema.getInsuredNo()
							+ "' and makedate>'2009-06-13' "
							+ " union "
							+ " select 1 from lcpenotice where contno=l.contno "
							// + " and peresult is not null "
							+ " and customerno='"
							+ tLCInsuredSchema.getInsuredNo() + "' "
							+ " and makedate<='2009-06-13' "
							+ ") and insuredno='"
							+ tLCInsuredSchema.getInsuredNo() + "' ";
					;
					tSSRSNew = tempExeSQL.getOneValue(tSqlNew);
					if (tSSRSNew != null && tSSRSNew.equals("1")) {
						insured.setHealthPro("1");
						insured.setPENoticeError("1");// 本次或既往体检结果异常记录(本次或既往体检录入的体检结果不为正常
														// 没回复算空白(也是不正常))
					} else {
						insured.setHealthPro("0");
						insured.setPENoticeError("0");
					}

					// 2009-2-11 ln add 额外死亡率--加费评点数
					tSqlNew = "select nvl(max(suppriskscore),0) from lcprem "
							+ " where contno in ( select contno from lcinsured where 1=1 and insuredno='"
							+ tLCInsuredSchema.getInsuredNo()
							+ "' and contno='" + mContNo + "') ";
					String tResultNew = tempExeSQL.getOneValue(tSqlNew);
					insured.setEM(Double.valueOf(tResultNew));

					String tZBFlag = "";

					tZBFlag = this.getZBFlag(this.LSumDangerAmnt,
							DSumDangerAmnt, MSumDangerAmnt, tResultNew);
					insured.setZBFlag(tZBFlag);
					// 2009-3-16 add by duanyh
					// 健康告知不全为否 HealthTellConTent
					String tHealthTellConTent_sql = "select count(*) from lccustomerimpart where impartcode in('A0105','A0110','A0111e','A0111j',"
							+ "'A0106','A0111a','A0111f','A0112','A0107','A0111b','A0111g','A0108','A0111c','A0111h','A0109','A0111d',"
							+ "'A0111i','D0105','D0110a','D0110e','D0110i','D0106','D0110b','D0110f','D0110j','D0107','D0110c','D0110g',"
							+ "'D0111','D0108','D0110d','D0110h','D0122','D0109','C0101','C0102','C0103','C0104','C0105','C0106','B0101',"
							+ "'B0102','B0103','B0104','A0504','A0505','A0506','A0507','A0509','A0510','A0511','A0512',"
							+ "'A0513','A0514','A0515','A0516','A0517','A0518','A0519','A0520')"
							+ " and contno='"
							+ mContNo
							+ "' and customerno='"
							+ tLCInsuredSchema.getInsuredNo() + "' ";// and
																		// CustomerNoType='1'";
					String tHealthTellConTent = "";
					tHealthTellConTent = tempExeSQL
							.getOneValue(tHealthTellConTent_sql);
					if (tHealthTellConTent != null
							&& !tHealthTellConTent.equals("")
							&& Integer.parseInt(tHealthTellConTent) > 0) {
						insured.setHealthTellConTent("1");
					} else {
						insured.setHealthTellConTent("0");
					}

					// 妇科告知不全为否 FemaleConTent
					String tFemaleConTent_sql = "select count(*) from lccustomerimpart where impartcode in('A0113a','A0113b','D0112a','D0112b','A0522')"
							+ " and contno='"
							+ mContNo
							+ "' and customerno='"
							+ tLCInsuredSchema.getInsuredNo() + "' ";// and
																		// CustomerNoType='1'";
					String tFemaleConTent = "";
					tFemaleConTent = tempExeSQL.getOneValue(tFemaleConTent_sql);
					if (tFemaleConTent != null && !tFemaleConTent.equals("")
							&& Integer.parseInt(tFemaleConTent) > 0) {
						insured.setFemaleConTent("1");
					} else {
						insured.setFemaleConTent("0");
					}
					// 婴儿告知不为否 BabyConTent
					String tBabyConTent_sql = "select count(*) from lccustomerimpart where impartcode in('A0114b','D0121','A0524')"
							+ " and contno='"
							+ mContNo
							+ "' and customerno='"
							+ tLCInsuredSchema.getInsuredNo() + "' ";// and
																		// CustomerNoType='1'";
					String tBabyConTent = "";
					tBabyConTent = tempExeSQL.getOneValue(tBabyConTent_sql);
					if (tBabyConTent != null && !tBabyConTent.equals("")
							&& Integer.parseInt(tBabyConTent) > 0) {
						insured.setBabyConTent("1");
					} else {
						insured.setBabyConTent("0");
					}
					// 家族史 FamilyConTent
					String tFamilyConTent_sql = "select count(*) from lccustomerimpart where impartcode in('A0115a','A0115b','D0113a','D0113b','A0525','A0526')"
							+ " and contno='"
							+ mContNo
							+ "' and customerno='"
							+ tLCInsuredSchema.getInsuredNo() + "' ";// and
																		// CustomerNoType='1'";
					String tFamilyConTent = "";
					tFamilyConTent = tempExeSQL.getOneValue(tFamilyConTent_sql);
					if (tFamilyConTent != null && !tFamilyConTent.equals("")
							&& Integer.parseInt(tFamilyConTent) > 0) {
						insured.setFamilyConTent("1");
					} else {
						insured.setFamilyConTent("0");
					}
					// 既往异常投保/理赔史 OuncommonConTent
					String tOuncommonConTent_sql = "select count(*) from lccustomerimpart where impartcode in('A0117','A0118','D0115','D0116','D0117','C0108','A0528','A0529')"
							+ " and contno='"
							+ mContNo
							+ "' and customerno='"
							+ tLCInsuredSchema.getInsuredNo() + "' ";// and
																		// CustomerNoType='1'";
					String tOuncommonConTent = "";
					tOuncommonConTent = tempExeSQL
							.getOneValue(tOuncommonConTent_sql);
					if (tOuncommonConTent != null
							&& !tOuncommonConTent.equals("")
							&& Integer.parseInt(tOuncommonConTent) > 0) {
						insured.setOuncommonConTent("1");
					} else {
						insured.setOuncommonConTent("0");
					}

					// add by duanyh 2009-3-18
					String tClaimUWFlag_sql = "select count(*) from lwmission where activityid='0000005505' and missionprop3='"
							+ tLCInsuredSchema.getInsuredNo() + "'";
					String tClaimUWFlag = "";
					tClaimUWFlag = tempExeSQL.getOneValue(tClaimUWFlag_sql);
					if (tClaimUWFlag != null && !tClaimUWFlag.equals("")
							&& Integer.parseInt(tClaimUWFlag) > 0) {
						insured.setClaimUWFlag("1");// 是否有正在申请的理赔核保的保单
					} else {
						insured.setClaimUWFlag("0");
					}
					String tORNewUWFlag_sql = "select count(*) from lcuwmaster where passflag in ('4','1') and insuredno='"
							+ tLCInsuredSchema.getInsuredNo()
							+ "' and uwtype='1'";
					String tORNewUWFlag = "";
					tORNewUWFlag = tempExeSQL.getOneValue(tORNewUWFlag_sql);
					if (tORNewUWFlag != null && !tORNewUWFlag.equals("")
							&& Integer.parseInt(tORNewUWFlag) > 0) {
						insured.setORNewUWFlag("1");// 既往续期核保结论为“次标准体"或"拒保"
													// ？？待确认
					} else {
						insured.setORNewUWFlag("0");
					}
					String tOClaimUWFlag_sql = "select count(*) from LLCUWMaster where passflag in ('1','4') and insuredno='"
							+ tLCInsuredSchema.getInsuredNo() + "'";
					String tOClaimUWFlag = "";
					tOClaimUWFlag = tempExeSQL.getOneValue(tOClaimUWFlag_sql);
					if (tOClaimUWFlag != null && !tOClaimUWFlag.equals("")
							&& Integer.parseInt(tOClaimUWFlag) > 0) {
						insured.setOClaimUWFlag("1");// 既往理赔核保结论 为“次标准体"或"拒保"
					} else {
						insured.setOClaimUWFlag("0");
					}
					String tOReportFlag_sql = " select count(*) from llreport ,llsubreport where rptno=subrptno and not exists(select 1 from llregister where rgtno=rptno)"
							+ " and RptDate>=add_months(sysdate,-24) and customerno='"
							+ tLCInsuredSchema.getInsuredNo() + "'";
					String tOReportFlag = "";
					tOReportFlag = tempExeSQL.getOneValue(tOReportFlag_sql);
					if (tOReportFlag != null && !tOReportFlag.equals("")
							&& Integer.parseInt(tOReportFlag) > 0) {
						insured.setOReportFlag("1");// 二年内既往报案记录(被保人险既往2年内曾经在我公司发生过报案记录,但是未立案；)
					} else {
						insured.setOReportFlag("0");
					}

					tSqlNew = " select 1 from lccont l where  contno<>'"
							+ mContNo
							+ "' "
							+ " and exists (select 1 from lccuwmaster where contno=l.contno and commonreasoncode='03' and passflag='a')"
							+ " and exists (select 1 from lcinsured where contno=l.contno and insuredno='"
							+ tLCInsuredSchema.getInsuredNo() + "' )";
					tSSRSNew = tempExeSQL.getOneValue(tSqlNew);
					if (tSSRSNew != null && tSSRSNew.equals("1")) {
						insured.setOAddFeeYN("1");// 既往未同意加费记录
					} else {
						insured.setOAddFeeYN("0");
					}

					tSqlNew = " select 1 from lccont l where  contno<>'"
							+ mContNo
							+ "' "
							+ " and exists (select 1 from lccuwmaster where contno=l.contno and commonreasoncode='05' and passflag='a')"
							+ " and exists (select 1 from lcinsured where contno=l.contno and insuredno='"
							+ tLCInsuredSchema.getInsuredNo() + "' )";
					tSSRSNew = tempExeSQL.getOneValue(tSqlNew);
					if (tSSRSNew != null && tSSRSNew.equals("1")) {
						insured.setOChangePlanYN("1");// 既往未同意保险计划变更记录(被保险人既往有未同意保险计划变更客户申请撤单撤单记录)
					} else {
						insured.setOChangePlanYN("0");
					}

					tSqlNew = " select 1 from lccont l where  contno<>'"
							+ mContNo
							+ "' "
							+ " and exists (select 1 from lccuwmaster where contno=l.contno and commonreasoncode='09' and passflag='a')"
							+ " and exists (select 1 from lcinsured where contno=l.contno and insuredno='"
							+ tLCInsuredSchema.getInsuredNo() + "' )";
					tSSRSNew = tempExeSQL.getOneValue(tSqlNew);
					if (tSSRSNew != null && tSSRSNew.equals("1")) {
						insured.setOLateAddFeeYN("1");// 既往逾期未加费记录
					} else {
						insured.setOLateAddFeeYN("0");
					}

					tSqlNew = " select 1 from lccont l where  contno<>'"
							+ mContNo
							+ "' "
							+ " and exists (select 1 from lccuwmaster where contno=l.contno and commonreasoncode='11' and passflag='a')"
							+ " and exists (select 1 from lcinsured where contno=l.contno and insuredno='"
							+ tLCInsuredSchema.getInsuredNo() + "' )";
					tSSRSNew = tempExeSQL.getOneValue(tSqlNew);
					if (tSSRSNew != null && tSSRSNew.equals("1")) {
						insured.setOLateChangePlanYN("1");// 逾期未回复保险计划变更记录
					} else {
						insured.setOLateChangePlanYN("0");
					}

					tSqlNew = " select 1 from lccont l where  contno<>'"
							+ mContNo
							+ "' "
							+ " and exists (select 1 from lccuwmaster where contno=l.contno and commonreasoncode='08' and passflag='a')"
							+ " and exists (select 1 from lcinsured where contno=l.contno and insuredno='"
							+ tLCInsuredSchema.getInsuredNo() + "' )";
					tSSRSNew = tempExeSQL.getOneValue(tSqlNew);
					if (tSSRSNew != null && tSSRSNew.equals("1")) {
						insured.setOLateMeetYN("1");// 既往逾期未生调
					} else {
						insured.setOLateMeetYN("0");
					}

					tSqlNew = " select 1 from lccont l where  contno<>'"
							+ mContNo
							+ "' "
							+ " and exists (select 1 from lccuwmaster where contno=l.contno and commonreasoncode='07' and passflag='a')"
							+ " and exists (select 1 from lcinsured where contno=l.contno and insuredno='"
							+ tLCInsuredSchema.getInsuredNo() + "' )";
					tSSRSNew = tempExeSQL.getOneValue(tSqlNew);
					if (tSSRSNew != null && tSSRSNew.equals("1")) {
						insured.setOLatePENoticeYN("1");// 既往逾期未体检记录
					} else {
						insured.setOLatePENoticeYN("0");
					}

					tSqlNew = " select 1 from lccont l where  contno<>'"
							+ mContNo
							+ "' "
							+ " and exists (select 1 from lccuwmaster where contno=l.contno and commonreasoncode='10' and passflag='a')"
							+ " and exists (select 1 from lcinsured where contno=l.contno and insuredno='"
							+ tLCInsuredSchema.getInsuredNo() + "' )";
					tSSRSNew = tempExeSQL.getOneValue(tSqlNew);
					if (tSSRSNew != null && tSSRSNew.equals("1")) {
						insured.setOLateSpecYN("1");// 既往逾期未回复特约记录
					} else {
						insured.setOLateSpecYN("0");
					}

					tSqlNew = " select 1 from lccont l where  contno<>'"
							+ mContNo
							+ "' "
							+ " and exists (select 1 from lccuwmaster where contno=l.contno and commonreasoncode='02' and passflag='a')"
							+ " and exists (select 1 from lcinsured where contno=l.contno and insuredno='"
							+ tLCInsuredSchema.getInsuredNo() + "' )";
					tSSRSNew = tempExeSQL.getOneValue(tSqlNew);
					if (tSSRSNew != null && tSSRSNew.equals("1")) {
						insured.setOMeetYN("1");// 既往未同意生调记录(被保险人既往有未同意生调客户申请撤单撤单记录)
					} else {
						insured.setOMeetYN("0");
					}

					tSqlNew = " select 1 from lccont l where  contno<>'"
							+ mContNo
							+ "' "
							+ " and exists (select 1 from lccuwmaster where contno=l.contno and commonreasoncode='14' and passflag='a')"
							+ " and exists (select 1 from lcinsured where contno=l.contno and insuredno='"
							+ tLCInsuredSchema.getInsuredNo() + "' )";
					tSSRSNew = tempExeSQL.getOneValue(tSqlNew);
					if (tSSRSNew != null && tSSRSNew.equals("1")) {
						insured.setOOtherOver("1");// 既往有其他撤单记录
					} else {
						insured.setOOtherOver("0");
					}

					tSqlNew = " select 1 from lccont l where  contno<>'"
							+ mContNo
							+ "' "
							+ " and exists (select 1 from lccuwmaster where contno=l.contno and commonreasoncode='01' and passflag='a')"
							+ " and exists (select 1 from lcinsured where contno=l.contno and insuredno='"
							+ tLCInsuredSchema.getInsuredNo() + "' )";
					tSSRSNew = tempExeSQL.getOneValue(tSqlNew);
					if (tSSRSNew != null && tSSRSNew.equals("1")) {
						insured.setOPENotice("1");// 既往未同意体检记录(被保险人既往有未同意体检客户申请撤单撤单记录)
					} else {
						insured.setOPENotice("0");
					}

					tSqlNew = " select 1 from lccont l where  contno<>'"
							+ mContNo
							+ "' "
							+ " and exists (select 1 from lccuwmaster where contno=l.contno and commonreasoncode='04' and passflag='a')"
							+ " and exists (select 1 from lcinsured where contno=l.contno and insuredno='"
							+ tLCInsuredSchema.getInsuredNo() + "' )";
					tSSRSNew = tempExeSQL.getOneValue(tSqlNew);
					if (tSSRSNew != null && tSSRSNew.equals("1")) {
						insured.setOSpecYN("1");// 既往未同意特约记录(被保险人既往有未同意特约客户申请撤单撤单记录)
					} else {
						insured.setOSpecYN("0");
					}

					// insured.setOMainPol();//既往主险退保次数
					// insured.setOOccupationType();//既往职业类别
					// insured.setInsuredISOperator(); //被保险人职业为MS公司业务员（待确认）

					// tongmeng 2009-05-04 add
					// 增加住院费用补偿险保额,康顺意外保额,康顺老年份数
					ExeSQL tExeSQL_SumAmnt = new ExeSQL();
					String tSQL_SumZYAmnt = "select nvl(sum(amnt),0) from lcpol where appflag not in ('4','9') "
							+ " and uwflag not in ('1','2','a') "
							+ " and insuredno='"
							+ tLCInsuredSchema.getInsuredNo()
							+ "' "
							+ " and riskcode in ('111801','111802') ";
					String tSumZYAmnt = tExeSQL_SumAmnt
							.getOneValue(tSQL_SumZYAmnt);
					if (tSumZYAmnt == null || tSumZYAmnt.equals("")) {
						tSumZYAmnt = "0";
					}
					insured.setSumZYAmnt(new Double(tSumZYAmnt));
					String tSQL_SumKSAmnt = "select nvl(sum(amnt),0) from lcpol where appflag not in ('4','9') "
							+ " and uwflag not in ('1','2','a') "
							+ " and insuredno='"
							+ tLCInsuredSchema.getInsuredNo()
							+ "' "
							+ " and riskcode in ('111601','121601') ";
					String tSumKSAmnt = tExeSQL_SumAmnt
							.getOneValue(tSQL_SumKSAmnt);

					if (tSumKSAmnt == null || tSumKSAmnt.equals("")) {
						tSumKSAmnt = "0";
					}
					insured.setSumKSAmnt(new Double(tSumKSAmnt));

					String tSQL_SumKSMult = "select nvl(sum( case when mult=0 then 1 else mult end),0) from lcpol where appflag not in ('4','9') "
							+ " and uwflag not in ('1','2','a') "
							+ " and insuredno='"
							+ tLCInsuredSchema.getInsuredNo()
							+ "' "
							+ " and riskcode in ('141803','111602') ";
					String tSumKSMult = tExeSQL_SumAmnt
							.getOneValue(tSQL_SumKSMult);
					if (tSumKSMult == null || tSumKSMult.equals("")) {
						tSumKSMult = "0";
					}
					insured.setSumKSMult(new Double(tSumKSMult));

					// tongmeng 2009-04-28 add
					// 累计年金险保额和累计医疗险保额
					String tSQL_Ann = "select nvl(sum(amnt),0) from lcpol where appflag not in ('4','9') "
							+ " and uwflag not in ('1','2','a') "
							+ " and insuredno='"
							+ tLCInsuredSchema.getInsuredNo()
							+ "' "
							+ " and riskcode in (select riskcode from lmriskapp where risktype8='05' ) ";

					String tSumAnnAmnt = tExeSQL_SumAmnt.getOneValue(tSQL_Ann);
					if (tSumAnnAmnt == null || tSumAnnAmnt.equals("")) {
						insured.setSumAnnAmnt(new Double("0"));
					} else {
						insured.setSumAnnAmnt(new Double(tSumAnnAmnt));
					}

					// 累计费用型医疗险保额
					String tSQL_Med = "select nvl(sum(amnt),0) from lcpol where appflag not in ('4','9') "
							+ " and uwflag not in ('1','2','a') "
							+ " and insuredno='"
							+ tLCInsuredSchema.getInsuredNo()
							+ "' "
							+ " and riskcode in (select riskcode from lmriskapp where risktype8='07' ) ";
					String tSumMedAmnt = tExeSQL_SumAmnt.getOneValue(tSQL_Med);

					if (tSumMedAmnt == null || tSumMedAmnt.equals("")) {
						tSumMedAmnt = "0";
					}

					String tInsuredNo = tLCInsuredSchema.getInsuredNo();
					insured.setSumJYMTCount(getSumJYMTCount(tInsuredNo));// 被保险人的金玉满堂系列产品份数
					insured.setSumFGYMPrem(getSumFGYMPrem(tInsuredNo));// 被保险人的富贵盈门系列产品保费
					insured.setSumMedAmnt(new Double(tSumMedAmnt));
					insured.setSumKSCont(getSumCont(tInsuredNo, "1"));// 康顺合同份数
					insured.setSumMRZYCont(getSumCont(tInsuredNo, "2"));// 每日住院合同份数
					insured.setOImpart(getOImpart(tInsuredNo));// 既往告知事项不全为否
					insured.setYearIncome(getYearIncome(tInsuredNo));// 年收入
					// insured.setFinSumAmnt(FinSumAmnt);//本单财务风险保额
					insured.setPregnancyWeeks(getPregnancyWeeks(tInsuredNo));// 怀孕周数
					insured.setSmokeYears(getSmokeYears(tInsuredNo, "I",
							FreeFlag));// 吸烟年数
					insured.setDrinkYears(getDrinkYears(tInsuredNo));// 饮酒年数
					insured.setDrinkType(getDrinkType(tInsuredNo));// 饮酒类型
					insured.setISPregnancy(getISImpart(tInsuredNo, "01"));// 孕妇告知
					insured.setDanSportInter(getISImpart(tInsuredNo, "02"));// 危险运动爱好
					insured.setTrafAccImpart(getISImpart(tInsuredNo, "03"));// 交通事故告知
					insured.setAbroadImpart(getISImpart(tInsuredNo, "04"));// 出国意向告知
					insured.setTrafficAccident(getTrafficAccident(tInsuredNo));// 交通事故记录
					insured.setDisabilityImpart(getDisabilityImpart(tInsuredNo));// 残疾事项告知
					// 联系电话与业务员的联系电话是否一致
					// insured.setSamePhone(getSamePhone(tInsuredNo,tLCInsuredSchema.getAddressNo()));
					insured.setSameOccuCode(getSameOccuCode(tInsuredNo));// 一年内职业代码是否一致
					insureds[i - 1] = insured;
				}
			}
		} catch (Exception e) {
			CError.buildErr(this, "准备BOMInsured出错！");
			e.printStackTrace();
		}
		return insureds;
	}

	private BOMPol[] DealBOMPol(LCPolSet tLCPolSet, int polCount,
			BOMInsured[] insureds) {
		BOMPol[] pols = new BOMPol[polCount];// 多个险种
		try {
			if (tLCPolSet.size() > 0) {
				for (int i = 1; i <= tLCPolSet.size(); i++) {
					ExeSQL tempExeSQL = new ExeSQL();
					LCPolSchema tLCPolSchema = new LCPolSchema();
					tLCPolSchema = tLCPolSet.get(i);
					BOMPol pol = new BOMPol();
					boolean FreeFlag = false;
					BOMInsured tCurrentInsured = new BOMInsured();
					for (int k = 0; k < insureds.length; k++) {
						BOMInsured insured = new BOMInsured();
						insured = insureds[k];
						if (tLCPolSchema.getRiskCode().equals("121301")) {
							if (insured.getInsuredNo().equals(
									tLCPolSchema.getAppntNo())) {
								pol.setFatherBOM(insured);
								tCurrentInsured = insured;
								FreeFlag = true;
								break;
							}
						} else if (insured.getInsuredNo().equals(
								tLCPolSchema.getInsuredNo())) {
							pol.setFatherBOM(insured);
							tCurrentInsured = insured;
							break;
						}
					}

					pol.setAmnt(Double.valueOf(String.valueOf(tLCPolSchema
							.getAmnt())));
					pol.setBonusGetMode(tLCPolSchema.getBonusGetMode());
					pol.setBonusManType(tLCPolSchema.getBonusMan());
					if (!(tLCPolSchema.getCValiDate() == null || ""
							.equals(tLCPolSchema.getCValiDate()))) {
						theDate = tLCPolSchema.getCValiDate() + " 00:00:00";
						pol.setCValiDate(sdf.parse(theDate));
					}
					String DerateOrFreeSql = "select count(*)  from LCDuty where polno='"
							+ tLCPolSchema.getPolNo() + "' and FreeFlag='1'";
					String tDerateOrFree = "";
					tDerateOrFree = tempExeSQL.getOneValue(DerateOrFreeSql);
					if (tDerateOrFree != null && !tDerateOrFree.equals("")
							&& Integer.parseInt(tDerateOrFree) > 0) {
						pol.setDerateOrFreeFlag("1");// 保费减费/免费标志
					} else {
						pol.setDerateOrFreeFlag("0");// 保费减费/免费标志
					}

					pol.setFloatRate(new Double(tLCPolSchema.getFloatRate()));
					String AddFeeSql = "select payplantype from lcprem where  payplancode like '000000%%' and polno='"
							+ tLCPolSchema.getPolNo() + "'";
					SSRS tAddFeeSSRS = new SSRS();
					tAddFeeSSRS = tempExeSQL.execSQL(AddFeeSql);
					for (int add = 1; add <= tAddFeeSSRS.getMaxRow(); add++) {
						String AddFeeFlag = tAddFeeSSRS.GetText(add, 1);
						if ("01".equals(AddFeeFlag)) {
							pol.setHAddFeeFlag("1");// 健康加费标记
						} else if ("02".equals(AddFeeFlag)) {
							pol.setOAddFeeFlag(AddFeeFlag);// 职业加费标记
						}
					}

					pol.setInsuredNo(tLCPolSchema.getInsuredNo());
					pol.setInsuYear(Double.valueOf(String.valueOf(tLCPolSchema
							.getInsuYear())));
					pol.setInsuYearFlag(tLCPolSchema.getInsuYearFlag());
					// tongmeng 2009-03-25 modify
					//
					String tSQL = "select risktype8 from lmriskapp where riskcode='"
							+ tLCPolSchema.getRiskCode() + "'";
					ExeSQL tExeSQL = new ExeSQL();
					String tValue = tExeSQL.getOneValue(tSQL);
					// 121801险种的规则大部分都不符合意外险的规则，所以在规则引擎中把121801排除意外险的行列
					// if("121801".equals(tLCPolSchema.getRiskCode().trim())){
					pol.setKindCode(tValue);// 险种类别
					// } else {
					// pol.setKindCode("50");
					// }
					pol.setLiveGetMode(tLCPolSchema.getLiveGetMode());
					pol.setMainPolNo(tLCPolSchema.getMainPolNo());
					pol.setMult(new Double(tLCPolSchema.getMult()));
					pol.setPayYears(new Double(tLCPolSchema.getPayYears()));
					pol.setPolNo(tLCPolSchema.getPolNo());
					pol.setPrem(new Double(tLCPolSchema.getPrem()));
					pol.setRiskCode(tLCPolSchema.getRiskCode());// 险种编码
					pol.setStopFlag(tLCPolSchema.getStopFlag());

					String tTotalAmntSql = "";
					if (!FreeFlag) {
						tTotalAmntSql = "select sum(Amnt) from lcpol where insuredno='"
								+ tLCPolSchema.getInsuredNo()
								+ "' and riskcode='"
								+ tLCPolSchema.getRiskCode()
								+ "'"
								+ " and appflag not in ('4','9') and uwflag not in ('1','2','a')";
					} else {
						tTotalAmntSql = "select sum(Amnt) from lcpol where insuredno='"
								+ tLCPolSchema.getAppntNo()
								+ "' and riskcode='"
								+ tLCPolSchema.getRiskCode()
								+ "'"
								+ " and appflag not in ('4','9') and uwflag not in ('1','2','a')";
					}
					String tTotalAmnt = tempExeSQL.getOneValue(tTotalAmntSql);
					if (!(tTotalAmnt == null || "".equals(tTotalAmnt))) {
						pol.setTotalAmnt(Double.valueOf(tTotalAmnt));// 累计该险种保额
					}
					String tTotalMultSql = "";
					if (!FreeFlag) {
						tTotalMultSql = "select sum(decode(mult,0,1,mult)) from lcpol where insuredno='"
								+ tLCPolSchema.getInsuredNo()
								+ "' and riskcode='"
								+ tLCPolSchema.getRiskCode()
								+ "'"
								+ " and appflag not in ('4','9') and uwflag not in ('1','2','a') ";
					} else {

						tTotalMultSql = "select sum(decode(mult,0,1,mult)) from lcpol where insuredno='"
								+ tLCPolSchema.getAppntNo()
								+ "' and riskcode='"
								+ tLCPolSchema.getRiskCode()
								+ "'"
								+ " and appflag not in ('4','9') and uwflag not in ('1','2','a') ";
					}

					String tTotalMult = tempExeSQL.getOneValue(tTotalMultSql);
					if (!(tTotalMult == null || "".equals(tTotalMult))) {
						pol.setTotalMult(Double.valueOf(tTotalMult));// 累计该险种份数
					}
					pol.setUWFlag(tLCPolSchema.getUWFlag());
					pol.setSumThisPrem(getSumThisPrem(
							tLCPolSchema.getRiskCode(),
							tLCPolSchema.getInsuredNo()));// 累计该险种保费
					pols[i - 1] = pol;
				}
			}
		} catch (Exception e) {
			CError.buildErr(this, "准备BOMPol出错！");
			e.printStackTrace();
		}
		return pols;
	}

	private BOMBnf[] DealBOMBnf(LCBnfSet tLCBnfSet, int bnfCount,
			BOMInsured[] insureds) {
		BOMBnf[] bnfs = new BOMBnf[bnfCount];// 多个受益人
		try {
			if (tLCBnfSet.size() > 0) {
				for (int i = 1; i <= tLCBnfSet.size(); i++) {
					LCBnfSchema tLCBnfSchema = new LCBnfSchema();
					tLCBnfSchema = tLCBnfSet.get(i);
					BOMBnf bnf = new BOMBnf();
					if (!(tLCBnfSchema.getBirthday() == null || ""
							.equals(tLCBnfSchema.getBirthday()))) {
						theDate = tLCBnfSchema.getBirthday() + " 00:00:00";
						bnf.setBirthday(sdf.parse(theDate));
					}
					bnf.setBnfGrade(tLCBnfSchema.getBnfGrade());
					bnf.setBnfLot(new Double(tLCBnfSchema.getBnfLot()));
					bnf.setBnfType(tLCBnfSchema.getBnfType());
					bnf.setBnfName(tLCBnfSchema.getName());
					bnf.setCustomerNo(tLCBnfSchema.getCustomerNo());
					bnf.setInsuredNo(tLCBnfSchema.getInsuredNo());
					for (int k = 0; k < insureds.length; k++) {
						BOMInsured insured = new BOMInsured();
						insured = insureds[k];
						if (insured.getInsuredNo().equals(bnf.getInsuredNo())) {
							bnf.setFatherBOM(insured);
						}
					}
					bnf.setRelationToInsured(tLCBnfSchema
							.getRelationToInsured());
					bnf.setSex(tLCBnfSchema.getSex());
					bnfs[i - 1] = bnf;
				}
			}
		} catch (Exception e) {
			CError.buildErr(this, "准备BOMBnf时出错！");
			e.printStackTrace();
		}
		return bnfs;
	}

	private BOMMainPol[] DealBOMMainPol(LCPolSet tmainLCPolSet,
			BOMInsured[] insureds) {
		int tMainCount = tmainLCPolSet.size();
		BOMMainPol[] mainpols = new BOMMainPol[tMainCount];// 多主险
		try {
			if (tmainLCPolSet.size() > 0) {
				for (int i = 1; i <= tmainLCPolSet.size(); i++) {
					ExeSQL tempExeSQL = new ExeSQL();
					LCPolSchema tmainLCPolSchema = new LCPolSchema();
					BOMMainPol mainpol = new BOMMainPol();
					tmainLCPolSchema = tmainLCPolSet.get(i);
					mainpol.setAmnt(Double.valueOf(String
							.valueOf(tmainLCPolSchema.getAmnt())));
					mainpol.setBonusGetMode(tmainLCPolSchema.getBonusGetMode());
					mainpol.setBonusManType(tmainLCPolSchema.getBonusMan());
					mainpol.setInsuredNo(tmainLCPolSchema.getInsuredNo());
					if (!(tmainLCPolSchema.getCValiDate() == null || ""
							.equals(tmainLCPolSchema.getCValiDate()))) {
						theDate = tmainLCPolSchema.getCValiDate() + " 00:00:00";
						mainpol.setCValiDate(sdf.parse(theDate));
					}
					String DerateOrFreeSql = "select count(*)  from LCDuty where polno='"
							+ tmainLCPolSchema.getPolNo()
							+ "' and FreeFlag='1'";
					String tDerateOrFree = "";
					tDerateOrFree = tempExeSQL.getOneValue(DerateOrFreeSql);
					if (tDerateOrFree != null && !tDerateOrFree.equals("")
							&& Integer.parseInt(tDerateOrFree) > 0) {
						mainpol.setDerateOrFreeFlag("1");// 保费减费/免费标志
					} else {
						mainpol.setDerateOrFreeFlag("0");
					}
					for (int k = 0; k < insureds.length; k++) {
						BOMInsured insured = new BOMInsured();
						insured = insureds[k];
						if (insured.getInsuredNo().equals(
								mainpol.getInsuredNo())) {
							mainpol.setFatherBOM(insured);
						}
					}
					mainpol.setFloatRate(Double.valueOf(String
							.valueOf(tmainLCPolSchema.getFloatRate())));
					SSRS tAddFeeSSRS = new SSRS();
					String AddFeeSql = "select payplantype from lcprem where  payplancode like '000000%%' and polno='"
							+ tmainLCPolSchema.getPolNo() + "'";
					tAddFeeSSRS = tempExeSQL.execSQL(AddFeeSql);
					for (int add = 1; add <= tAddFeeSSRS.getMaxRow(); add++) {
						String AddFeeFlag = tAddFeeSSRS.GetText(add, 1);
						if ("01".equals(AddFeeFlag)) {
							mainpol.setHAddFeeFlag("1");// 健康加费标记
						} else if ("02".equals(AddFeeFlag)) {
							mainpol.setOAddFeeFlag(AddFeeFlag);// 职业加费标记
						}
					}
					mainpol.setInsuYear(Double.valueOf(String
							.valueOf(tmainLCPolSchema.getInsuYear())));
					mainpol.setInsuYearFlag(tmainLCPolSchema.getInsuYearFlag());
					// tongmeng 2009-03-25 modify
					//
					String tSQL = "select risktype8 from lmriskapp where riskcode='"
							+ tmainLCPolSchema.getRiskCode() + "'";
					ExeSQL tExeSQL = new ExeSQL();
					String tValue = tExeSQL.getOneValue(tSQL);
					mainpol.setKindCode(tValue);
					// mainpol.setKindCode(tmainLCPolSchema.getKindCode());
					mainpol.setLiveGetMode(tmainLCPolSchema.getLiveGetMode());
					mainpol.setMainPolNo(tmainLCPolSchema.getMainPolNo());
					mainpol.setMult(Double.valueOf(String
							.valueOf(tmainLCPolSchema.getMult())));
					mainpol.setPayYears(Double.valueOf(tmainLCPolSchema
							.getPayYears()));
					mainpol.setPolNo(tmainLCPolSchema.getPolNo());
					mainpol.setPrem(Double.valueOf(String
							.valueOf(tmainLCPolSchema.getPrem())));
					mainpol.setRiskCode(tmainLCPolSchema.getRiskCode());
					String SpecSql = "select count(*) from lccspec where contno = '"
							+ tmainLCPolSchema.getContNo() + "'";
					String tSpec = "";
					tSpec = tempExeSQL.getOneValue(SpecSql);
					if (tSpec != null && !tSpec.equals("")
							&& Integer.parseInt(tSpec) > 0) {
						mainpol.setSpecFlag("1");// 特约标记
					} else {
						mainpol.setSpecFlag("0");// 特约标记
					}
					mainpol.setStopFlag(tmainLCPolSchema.getStopFlag());
					String totalAmntSql = "select sum(amnt) from lcpol where riskcode='"
							+ tmainLCPolSchema.getRiskCode()
							+ "' and insuredno='"
							+ tmainLCPolSchema.getInsuredNo()
							+ "' and uwflag not in ('1','2','a') and appflag not in ('4','9')";
					String tTotalAmnt = tempExeSQL.getOneValue(totalAmntSql);
					if (!(tTotalAmnt == null || "".equals(tTotalAmnt))) {
						mainpol.setTotalAmnt(Double.valueOf(tTotalAmnt));// 累计该险种保额
					}
					mainpol.setUWFlag(tmainLCPolSchema.getUWFlag());
					mainpols[i - 1] = mainpol;
				}
			}
		} catch (Exception e) {
			CError.buildErr(this, "准备BOMMainPol时出错！");
			e.printStackTrace();
		}

		return mainpols;
	}

	private BOMSubPol[] DealBOMSubPol(LCPolSet tSubLCPolSet,
			BOMMainPol[] mainpols) {
		int subCount = tSubLCPolSet.size();
		BOMSubPol[] subpols = new BOMSubPol[subCount]; // 多个附加险
		ExeSQL tempExeSQL = new ExeSQL();
		try {
			if (tSubLCPolSet.size() > 0) {
				for (int i = 1; i <= tSubLCPolSet.size(); i++) {
					BOMSubPol subpol = new BOMSubPol();
					LCPolSchema subLCPolSchema = new LCPolSchema();
					subLCPolSchema = tSubLCPolSet.get(i);
					subpol.setAmnt(Double.valueOf(String.valueOf(subLCPolSchema
							.getAmnt())));
					subpol.setBonusGetMode(subLCPolSchema.getBonusGetMode());
					subpol.setBonusManType(subLCPolSchema.getBonusMan());
					if (!(subLCPolSchema.getCValiDate() == null || ""
							.equals(subLCPolSchema.getCValiDate()))) {
						theDate = subLCPolSchema.getCValiDate() + " 00:00:00";
						subpol.setCValiDate(sdf.parse(theDate));
					}

					String DerateOrFreeSql = "select count(*)  from LCDuty where polno='"
							+ subLCPolSchema.getPolNo() + "' and FreeFlag='1'";
					String tDerateOrFree = "";
					tDerateOrFree = tempExeSQL.getOneValue(DerateOrFreeSql);
					subpol.setMainPolNo(subLCPolSchema.getMainPolNo());
					if (tDerateOrFree != null && !tDerateOrFree.equals("")
							&& Integer.parseInt(tDerateOrFree) > 0) {
						subpol.setDerateOrFreeFlag("1");// 保费减费/免费标志
					} else {
						subpol.setDerateOrFreeFlag("0");// 保费减费/免费标志
					}
					for (int k = 0; k < mainpols.length; k++) {
						BOMMainPol tBOMMainPol = mainpols[k];
						if (tBOMMainPol.getMainPolNo().equals(
								subpol.getMainPolNo())) {
							subpol.setFatherBOM(tBOMMainPol);
						}
					}
					subpol.setFloatRate(Double.valueOf(String
							.valueOf(subLCPolSchema.getFloatRate())));
					SSRS tAddFeeSSRS = new SSRS();
					String AddFeeSql = "select payplantype from lcprem where  payplancode like '000000%%' and polno='"
							+ subLCPolSchema.getPolNo() + "'";
					tAddFeeSSRS = tempExeSQL.execSQL(AddFeeSql);
					for (int add = 1; add <= tAddFeeSSRS.getMaxRow(); add++) {
						String AddFeeFlag = tAddFeeSSRS.GetText(add, 1);
						if ("01".equals(AddFeeFlag)) {
							subpol.setHAddFeeFlag("1");// 健康加费标记
						} else if ("02".equals(AddFeeFlag)) {
							subpol.setOAddFeeFlag(AddFeeFlag);// 职业加费标记
						}
					}
					subpol.setInsuredNo(subLCPolSchema.getInsuredNo());
					subpol.setInsuYear(Double.valueOf(String
							.valueOf(subLCPolSchema.getInsuYear())));
					subpol.setInsuYearFlag(subLCPolSchema.getInsuYearFlag());
					// tongmeng 2009-03-25 modify
					//
					String tSQL = "select risktype8 from lmriskapp where riskcode='"
							+ subpol.getRiskCode() + "'";
					ExeSQL tExeSQL = new ExeSQL();
					String tValue = tExeSQL.getOneValue(tSQL);
					subpol.setKindCode(tValue);
					// subpol.setKindCode(subLCPolSchema.getKindCode());
					subpol.setLiveGetMode(subLCPolSchema.getLiveGetMode());
					subpol.setMult(Double.valueOf(String.valueOf(subLCPolSchema
							.getMult())));
					subpol.setPayYears(Double.valueOf(subLCPolSchema
							.getPayYears()));
					subpol.setPolNo(subLCPolSchema.getPolNo());
					subpol.setPrem(Double.valueOf(String.valueOf(subLCPolSchema
							.getPrem())));
					subpol.setRiskCode(subLCPolSchema.getRiskCode());
					String SpecSql = "select count(*) from lccspec where contno = '"
							+ subLCPolSchema.getContNo() + "'";
					String tSpec = "";
					tSpec = tempExeSQL.getOneValue(SpecSql);
					if (tSpec != null && !tSpec.equals("")
							&& Integer.parseInt(tSpec) > 0) {
						subpol.setSpecFlag("1");// 特约标记
					} else {
						subpol.setSpecFlag("0");// 特约标记
					}
					subpol.setStopFlag(subLCPolSchema.getStopFlag());
					String totalAmntSql = "select sum(amnt) from lcpol where riskcode='"
							+ subLCPolSchema.getRiskCode()
							+ "' and insuredno='"
							+ subLCPolSchema.getInsuredNo()
							+ "' and uwflag not in ('1','2','a') and appflag not in ('4','9')";

					String tTotalAmnt = tempExeSQL.getOneValue(totalAmntSql);
					if (!(tTotalAmnt == null || "".equals(tTotalAmnt))) {
						subpol.setTotalAmnt(Double.valueOf(tTotalAmnt));
					}
					subpol.setUWFlag(subLCPolSchema.getUWFlag());
					subpols[i - 1] = subpol;
				}
			}
		} catch (Exception e) {
			CError.buildErr(this, "准备BOMSubPol时出错！");
			e.printStackTrace();
		}
		return subpols;
	}

	private BOMSubPol2[] DealBOMSubPol2(LCPolSet tSubLCPolSet,
			BOMMainPol[] mainpols) {
		int subCount = tSubLCPolSet.size();
		BOMSubPol2[] subpol2s = new BOMSubPol2[subCount]; // 多个附加险
		ExeSQL tempExeSQL = new ExeSQL();
		try {
			if (tSubLCPolSet.size() > 0) {
				for (int i = 1; i <= tSubLCPolSet.size(); i++) {
					BOMSubPol2 subpol = new BOMSubPol2();
					LCPolSchema subLCPolSchema = new LCPolSchema();
					subLCPolSchema = tSubLCPolSet.get(i);
					subpol.setAmnt(Double.valueOf(String.valueOf(subLCPolSchema
							.getAmnt())));
					subpol.setBonusGetMode(subLCPolSchema.getBonusGetMode());
					subpol.setBonusManType(subLCPolSchema.getBonusMan());
					subpol.setMainPolNo(subLCPolSchema.getMainPolNo());
					if (!(subLCPolSchema.getCValiDate() == null || ""
							.equals(subLCPolSchema.getCValiDate()))) {
						theDate = subLCPolSchema.getCValiDate() + " 00:00:00";
						subpol.setCValiDate(sdf.parse(theDate));
					}
					String DerateOrFreeSql = "select count(*) from LCDuty where polno='"
							+ subLCPolSchema.getPolNo() + "' and FreeFlag='1'";
					String tDerateOrFree = "";
					tDerateOrFree = tempExeSQL.getOneValue(DerateOrFreeSql);
					if (tDerateOrFree != null && !tDerateOrFree.equals("")
							&& Integer.parseInt(tDerateOrFree) > 0) {
						subpol.setDerateOrFreeFlag("1");// 保费减费/免费标志
					} else {
						subpol.setDerateOrFreeFlag("0");// 保费减费/免费标志
					}
					for (int k = 0; k < mainpols.length; k++) {
						BOMMainPol tBOMMainPol = mainpols[k];
						if (tBOMMainPol.getMainPolNo().equals(
								subpol.getMainPolNo())) {
							subpol.setFatherBOM(tBOMMainPol);
						}
					}
					subpol.setFloatRate(Double.valueOf(String
							.valueOf(subLCPolSchema.getFloatRate())));
					SSRS tAddFeeSSRS = new SSRS();
					String AddFeeSql = "select payplantype from lcprem where  payplancode like '000000%%' and polno='"
							+ subLCPolSchema.getPolNo() + "'";
					tAddFeeSSRS = tempExeSQL.execSQL(AddFeeSql);
					for (int add = 1; add <= tAddFeeSSRS.getMaxRow(); add++) {
						String AddFeeFlag = tAddFeeSSRS.GetText(add, 1);
						if ("01".equals(AddFeeFlag)) {
							subpol.setHAddFeeFlag("1");// 健康加费标记
						} else if ("02".equals(AddFeeFlag)) {
							subpol.setOAddFeeFlag(AddFeeFlag);// 职业加费标记
						}
					}
					subpol.setInsuredNo(subLCPolSchema.getInsuredNo());
					subpol.setInsuYear(Double.valueOf(String
							.valueOf(subLCPolSchema.getInsuYear())));
					subpol.setInsuYearFlag(subLCPolSchema.getInsuYearFlag());
					// tongmeng 2009-03-25 modify
					//
					String tSQL = "select risktype8 from lmriskapp where riskcode='"
							+ subpol.getRiskCode() + "'";
					ExeSQL tExeSQL = new ExeSQL();
					String tValue = tExeSQL.getOneValue(tSQL);
					subpol.setKindCode(tValue);
					// subpol.setKindCode(subLCPolSchema.getKindCode());
					subpol.setLiveGetMode(subLCPolSchema.getLiveGetMode());
					subpol.setMult(Double.valueOf(String.valueOf(subLCPolSchema
							.getMult())));
					subpol.setPayYears(Double.valueOf(subLCPolSchema
							.getPayYears()));
					subpol.setPolNo(subLCPolSchema.getPolNo());
					subpol.setPrem(Double.valueOf(String.valueOf(subLCPolSchema
							.getPrem())));
					subpol.setRiskCode(subLCPolSchema.getRiskCode());
					subpol.setStopFlag(subLCPolSchema.getStopFlag());
					String totalAmntSql = "select sum(amnt) from lcpol where riskcode='"
							+ subLCPolSchema.getRiskCode()
							+ "' and insuredno='"
							+ subLCPolSchema.getInsuredNo()
							+ "' and uwflag not in ('1','2','a') and appflag not in ('4','9')";
					String tTotalAmnt = tempExeSQL.getOneValue(totalAmntSql);
					if (!(tTotalAmnt == null || "".equals(tTotalAmnt))) {
						subpol.setTotalAmnt(Double.valueOf(tTotalAmnt));
					}
					subpol.setUWFlag(subLCPolSchema.getUWFlag());
					subpol2s[i - 1] = subpol;
				}
			}
		} catch (Exception e) {
			CError.buildErr(this, "准备BOMSubPol时出错！");
			e.printStackTrace();
		}
		return subpol2s;
	}

	/**
	 * 计算是否需要临分
	 * 
	 * @return boolean
	 */
	private boolean CheckFB() {
		VData tVData = new VData();
		tVData.add(mLCContSchema);
		UWFBCal tUWFBCal = new UWFBCal();
		if (!tUWFBCal.submitData(tVData, "")) {
			logger.debug("不需要临分");
			return false;
		} else {
			logger.debug("需要临分");
			return true;
		}
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		map.put(mLCContSchema, "UPDATE");
		// if(mLCCUWMasterSet.size() ==1)
		map.put(mLCCUWMasterSet.get(1), "DELETE&INSERT");
		map.put(mLCCUWSubSet, "INSERT");
		map.put(mLCCUWErrorSet, "INSERT");

		map.put(mAllLCPolSet, "UPDATE");
		map.put(mAllInsuredSet, "UPDATE");
		int n = mLCUWMasterSet.size();
		for (int i = 1; i <= n; i++) {
			LCUWMasterSchema tLCUWMasterSchema = mLCUWMasterSet.get(i);
			map.put(tLCUWMasterSchema, "DELETE&INSERT");
		}
		map.put(mLCUWSubSet, "INSERT");
		map.put(mLCUWErrorSet, "INSERT");

		for (int i = 1; i <= mLCIndUWMasterSet.size(); i++) {
			LCIndUWMasterSchema tLCIndUWMasterSchema = mLCIndUWMasterSet.get(i);
			map.put(tLCIndUWMasterSchema, "DELETE&INSERT");
		}
		// map.put(mLCIndUWMasterSet.get(1), "DELETE&INSERT");
		map.put(mLCIndUWSubSet, "INSERT");
		map.put(mLCIndUWErrorSet, "INSERT");
		/** 发送拒保通知书 */
		if (mContPassFlag.equals("1")) {
			map.put(mLOPRTManagerSchema, "INSERT");
		}

		mResult.add(map);
		logger.debug("this.mPolPassFlag==" + this.mPolPassFlag);
		logger.debug("this.mAllPolPassFlag==" + this.mAllPolPassFlag);
		logger.debug("this.mContPassFlag==" + this.mContPassFlag);
		TransferData aTransferData = new TransferData();
		aTransferData.setNameAndValue("PolPassFlag", this.mPolPassFlag);
		aTransferData.setNameAndValue("ContPassFlag", this.mContPassFlag);
		aTransferData.setNameAndValue("ProductSaleFlag", ProductSaleFlag);
		mResult.add(aTransferData);
		return true;
	}

	/**
	 * 操作一张保单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param tLCContSchema
	 *            LCContSchema
	 * @param tLMUWSetContUnpass
	 *            LMUWSetContUnpass
	 * @return boolean
	 */
	private boolean dealOneCont(LCContSchema tLCContSchema) {
		prepareContUW(tLCContSchema);

		LCContSchema tLCContSchemaDup = new LCContSchema();
		tLCContSchemaDup.setSchema(tLCContSchema);
		mAllLCContSet.add(tLCContSchemaDup);

		LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();
		tLCCUWMasterSet.set(mLCCUWMasterSet);
		mAllLCCUWMasterSet.add(tLCCUWMasterSet);

		LCCUWSubSet tLCCUWSubSet = new LCCUWSubSet();
		tLCCUWSubSet.set(mLCCUWSubSet);
		mAllLCCUWSubSet.add(tLCCUWSubSet);

		LCCUWErrorSet tLCCUWErrorSet = new LCCUWErrorSet();
		tLCCUWErrorSet.set(mLCCUWErrorSet);
		mAllLCCUWErrorSet.add(tLCCUWErrorSet);

		return true;
	}

	/**
	 * 为个单被保人核保错误信息表准备数据
	 * 
	 * @param tLCContSchema
	 *            LCContSchema
	 * @param tLMUWSetContUnpass
	 *            LMUWSetContUnpass
	 * @return boolean
	 */
	private boolean prepareInsured(LCContSchema tLCContSchema,
			LCInsuredSet tLCInsuredSet) {
		int tLoop = 0;
		// if(!HasAddFreeFlag)
		{
			tLoop = tLCInsuredSet.size();
		}
		// else
		// {
		// tLoop = tLCInsuredSet.size()-1;
		// }
		for (int i = 1; i <= tLoop; i++) {
			int batchNo = 0;// 核保批次号
			LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
			tLCInsuredSchema = tLCInsuredSet.get(i);
			int tuwno = 0;
			LCIndUWMasterSchema tLCIndUWMasterSchema = new LCIndUWMasterSchema();
			LCIndUWMasterDB tLCIndUWMasterDB = new LCIndUWMasterDB();
			tLCIndUWMasterDB.setInsuredNo(tLCInsuredSchema.getInsuredNo());
			tLCIndUWMasterDB.setContNo(mContNo);
			LCIndUWMasterSet tLCIndUWMasterSet = new LCIndUWMasterSet();
			tLCIndUWMasterSet = tLCIndUWMasterDB.query();
			if (tLCIndUWMasterDB.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCIndUWMasterDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWAtuoChkBL";
				tError.functionName = "prepareUW";
				tError.errorMessage = mOldPolNo + "个人核保总表取数失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			int n = tLCIndUWMasterSet.size();
			if (n == 0) {
				tLCIndUWMasterSchema.setContNo(mContNo);
				tLCIndUWMasterSchema.setGrpContNo(tLCInsuredSchema
						.getGrpContNo());
				tLCIndUWMasterSchema.setProposalContNo(mPContNo);
				tLCIndUWMasterSchema.setUWNo(1);
				tLCIndUWMasterSchema.setInsuredNo(tLCInsuredSchema
						.getInsuredNo());
				tLCIndUWMasterSchema.setInsuredName(tLCInsuredSchema.getName());
				tLCIndUWMasterSchema.setAppntNo(tLCInsuredSchema.getAppntNo());
				tLCIndUWMasterSchema.setAppntName("");
				tLCIndUWMasterSchema.setAgentCode(tLCContSchema.getAgentCode());
				tLCIndUWMasterSchema.setAgentGroup(tLCContSchema
						.getAgentGroup());
				tLCIndUWMasterSchema.setUWGrade(mUWGrade); // 核保级别
				tLCIndUWMasterSchema.setAppGrade(mUWGrade); // 申报级别
				tLCIndUWMasterSchema.setPostponeDay("");
				tLCIndUWMasterSchema.setPostponeDate("");
				tLCIndUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
				tLCIndUWMasterSchema.setState(mPolPassFlag);
				tLCIndUWMasterSchema.setPassFlag(mPolPassFlag);
				tLCIndUWMasterSchema.setHealthFlag("0");
				tLCIndUWMasterSchema.setSpecFlag("0");
				tLCIndUWMasterSchema.setQuesFlag("0");
				tLCIndUWMasterSchema.setReportFlag("0");
				tLCIndUWMasterSchema.setChangePolFlag("0");
				tLCIndUWMasterSchema.setPrintFlag("0");
				tLCIndUWMasterSchema.setManageCom(tLCContSchema.getManageCom());
				tLCIndUWMasterSchema.setUWIdea("");
				tLCIndUWMasterSchema.setUpReportContent("");
				tLCIndUWMasterSchema.setOperator(mOperator); // 操作员
				tLCIndUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
				tLCIndUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
				tLCIndUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
				tLCIndUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
			} else if (n == 1) {
				tLCIndUWMasterSchema = tLCIndUWMasterSet.get(1);

				tuwno = tLCIndUWMasterSchema.getUWNo();
				tuwno = tuwno + 1;

				tLCIndUWMasterSchema.setUWNo(tuwno);
				tLCIndUWMasterSchema.setProposalContNo(mPContNo);
				tLCIndUWMasterSchema.setState(mPolPassFlag);
				tLCIndUWMasterSchema.setPassFlag(mPolPassFlag);
				tLCIndUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
				tLCIndUWMasterSchema.setUWGrade(mUWGrade); // 核保级别
				tLCIndUWMasterSchema.setAppGrade(mUWGrade); // 申报级别
				tLCIndUWMasterSchema.setOperator(mOperator); // 操作员
				tLCIndUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
				tLCIndUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
			} else {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCIndUWMasterDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWAtuoChkBL";
				tError.functionName = "prepareUW";
				tError.errorMessage = mOldPolNo + "个人核保总表取数据不唯一!";
				this.mErrors.addOneError(tError);
				return false;
			}

			// 核保轨迹表 ln 2008-10-21 modify uwno计算方法修改
			LCIndUWSubSchema tLCIndUWSubSchema = new LCIndUWSubSchema();
			LCIndUWSubDB tLCIndUWSubDB = new LCIndUWSubDB();
			// tLCUWSubDB.setPolNo(mOldPolNo);
			String sqlUwno = "select * from LCIndUWSub where contno ='"
					+ mContNo + "' and insuredno='"
					+ tLCInsuredSchema.getInsuredNo() + "' order by uwno desc ";
			LCIndUWSubSet tLCIndUWSubSet = new LCIndUWSubSet();
			tLCIndUWSubSet = tLCIndUWSubDB.executeQuery(sqlUwno);
			if (tLCIndUWSubSet.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCIndUWSubSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWAtuoChkBL";
				tError.functionName = "prepareUW";
				tError.errorMessage = mOldPolNo + "个人核保轨迹表查失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			int m = tLCIndUWSubSet.size();
			tLCIndUWSubSchema.setUWNo(m + 1); // 第几次核保

			tLCIndUWSubSchema.setContNo(mContNo);
			tLCIndUWSubSchema.setGrpContNo(tLCIndUWMasterSchema.getGrpContNo());
			tLCIndUWSubSchema.setProposalContNo(tLCIndUWMasterSchema
					.getProposalContNo());
			tLCIndUWSubSchema.setInsuredNo(tLCIndUWMasterSchema.getInsuredNo());
			tLCIndUWSubSchema.setInsuredName(tLCIndUWMasterSchema
					.getInsuredName());
			tLCIndUWSubSchema.setAppntNo(tLCIndUWMasterSchema.getAppntNo());
			tLCIndUWSubSchema.setAppntName(tLCIndUWMasterSchema.getAppntName());
			tLCIndUWSubSchema.setAgentCode(tLCIndUWMasterSchema.getAgentCode());
			tLCIndUWSubSchema.setAgentGroup(tLCIndUWMasterSchema
					.getAgentGroup());
			tLCIndUWSubSchema.setUWGrade(tLCIndUWMasterSchema.getUWGrade()); // 核保级别
			tLCIndUWSubSchema.setAppGrade(tLCIndUWMasterSchema.getAppGrade()); // 申请级别
			tLCIndUWSubSchema.setAutoUWFlag(tLCIndUWMasterSchema
					.getAutoUWFlag());
			tLCIndUWSubSchema.setState(tLCIndUWMasterSchema.getState());
			tLCIndUWSubSchema.setPassFlag(tLCIndUWMasterSchema.getState());
			tLCIndUWSubSchema.setPostponeDay(tLCIndUWMasterSchema
					.getPostponeDay());
			tLCIndUWSubSchema.setPostponeDate(tLCIndUWMasterSchema
					.getPostponeDate());
			tLCIndUWSubSchema.setUpReportContent(tLCIndUWMasterSchema
					.getUpReportContent());
			tLCIndUWSubSchema.setHealthFlag(tLCIndUWMasterSchema
					.getHealthFlag());
			tLCIndUWSubSchema.setSpecFlag(tLCIndUWMasterSchema.getSpecFlag());
			tLCIndUWSubSchema.setSpecReason(tLCIndUWMasterSchema
					.getSpecReason());
			tLCIndUWSubSchema.setQuesFlag(tLCIndUWMasterSchema.getQuesFlag());
			tLCIndUWSubSchema.setReportFlag(tLCIndUWMasterSchema
					.getReportFlag());
			tLCIndUWSubSchema.setChangePolFlag(tLCIndUWMasterSchema
					.getChangePolFlag());
			tLCIndUWSubSchema.setChangePolReason(tLCIndUWMasterSchema
					.getChangePolReason());
			tLCIndUWSubSchema.setAddPremReason(tLCIndUWMasterSchema
					.getAddPremReason());
			tLCIndUWSubSchema.setPrintFlag(tLCIndUWMasterSchema.getPrintFlag());
			tLCIndUWSubSchema.setPrintFlag2(tLCIndUWMasterSchema
					.getPrintFlag2());
			tLCIndUWSubSchema.setUWIdea(tLCIndUWMasterSchema.getUWIdea());
			tLCIndUWSubSchema.setOperator(tLCIndUWMasterSchema.getOperator()); // 操作员
			tLCIndUWSubSchema.setManageCom(tLCIndUWMasterSchema.getManageCom());
			tLCIndUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			tLCIndUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			tLCIndUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tLCIndUWSubSchema.setModifyTime(PubFun.getCurrentTime());

			mLCIndUWSubSet.add(tLCIndUWSubSchema);
			if ("5".equals(mInsPassFlag)) {
				// 核保错误信息表
				LCIndUWErrorSchema tLCIndUWErrorSchema = new LCIndUWErrorSchema();
				LCIndUWErrorDB tLCIndUWErrorDB = new LCIndUWErrorDB();
				String UWNoSql = "select * from lcinduwerror where insuredno='"
						+ tLCIndUWMasterSchema.getInsuredNo()
						+ "' and contno='" + mContNo + "' order by uwno desc";// );
				LCIndUWErrorSet tLCIndUWErrorSet = new LCIndUWErrorSet();
				tLCIndUWErrorSet = tLCIndUWErrorDB.executeQuery(UWNoSql);
				if (tLCIndUWErrorDB.mErrors.needDealError()) {
					// @@错误处理
					this.mErrors.copyAllErrors(tLCIndUWErrorDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "UWAtuoChkBL";
					tError.functionName = "prepareUW";
					tError.errorMessage = mOldPolNo + "个人错误信息表查询失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				if (tLCIndUWErrorSet.size() == 0) {
					m = 0;
				} else {
					m = tLCIndUWErrorSet.get(1).getUWNo();
				}
				batchNo = m + 1;
				tLCIndUWErrorSchema.setUWNo(m + 1);
				tLCIndUWErrorSchema.setContNo(mContNo);
				tLCIndUWErrorSchema.setGrpContNo(tLCContSchema.getGrpContNo());
				tLCIndUWErrorSchema.setProposalContNo(mPContNo);
				tLCIndUWErrorSchema.setInsuredNo(tLCContSchema.getInsuredNo());
				tLCIndUWErrorSchema.setInsuredName(tLCContSchema
						.getInsuredName());
				tLCIndUWErrorSchema.setAppntNo(tLCContSchema.getAppntNo());
				tLCIndUWErrorSchema.setAppntName(tLCContSchema.getAppntName());
				tLCIndUWErrorSchema.setManageCom(tLCContSchema.getManageCom());
				tLCIndUWErrorSchema.setModifyDate(PubFun.getCurrentDate());
				tLCIndUWErrorSchema.setModifyTime(PubFun.getCurrentTime());
				tLCIndUWErrorSchema.setUWPassFlag(mPolPassFlag);
				// tongmeng 2009-03-24 modify
				// 去除重复信息
				Hashtable tInsuredHashtable = new Hashtable();
				for (int a = 0; a < tInsList.size(); a++) {
					TransferData InsTransferData = (TransferData) tInsList
							.get(a);
					String tInsuredno = (String) InsTransferData
							.getValueByName("insuredno");
					if (tInsuredno.equals(tLCInsuredSchema.getInsuredNo())) {

						String templateId = (String) InsTransferData
								.getValueByName("templateId");
						String result = (String) InsTransferData
								.getValueByName("result");
						String tRuleid = (String) InsTransferData
								.getValueByName("tRuleid");
						String tserialno = PubFun1.CreateMaxNo("LCIndUWError",
								20);
						if (tInsuredHashtable.containsKey(result)) {
							continue;
						} else {
							tInsuredHashtable.put(result, result);
						}

						tLCIndUWErrorSchema.setSerialNo(tserialno);
						tLCIndUWErrorSchema.setUWRuleCode(tRuleid); // 转储规则引擎接口返回的模板的Ruleid
						tLCIndUWErrorSchema.setUWError(result); // 核保出错信息，即核保规则的文字描述内容
						tLCIndUWErrorSchema.setUWGrade(mUWGrade);
						tLCIndUWErrorSchema.setCurrValue(templateId); // 转储规则引擎接口返回的模板号
						// tLCUWErrorSchema.setSugPassFlag(tLMUWSchema.getPassFlag());
						// // picch需求对自核规则分类（体检、契调）

						LCIndUWErrorSchema ttLCIndUWErrorSchema = new LCIndUWErrorSchema();
						ttLCIndUWErrorSchema.setSchema(tLCIndUWErrorSchema);
						mLCIndUWErrorSet.add(ttLCIndUWErrorSchema);
					}
				}
			}
			tLCIndUWMasterSchema.setBatchNo(batchNo);
			mLCIndUWMasterSet.add(tLCIndUWMasterSchema);
		}
		return true;
	}

	/**
	 * 校验核保员级别 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkUWGrade() {
		LDUserDB tLDUserDB = new LDUserDB();
		tLDUserDB.setUserCode(mOperator);

		if (!tLDUserDB.getInfo()) {
			CError.buildErr(this, "无此操作员信息，不能操作!（操作员：" + mOperator + "）");
			return false;
		}

		return true;
	}

	/**
	 * 准备合同核保信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareContUW(LCContSchema tLCContSchema) {
		int batchNo = 0;
		// 合同核保主表
		LCCUWMasterSchema tLCCUWMasterSchema = new LCCUWMasterSchema();
		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setContNo(mContNo);
		LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();
		tLCCUWMasterSet = tLCCUWMasterDB.query();
		if (tLCCUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAtuoChkAfterInitService";
			tError.functionName = "prepareContUW";
			tError.errorMessage = mContNo + "合同核保总表取数失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLCCUWMasterSet.size() == 0) {
			tLCCUWMasterSchema.setContNo(mContNo);
			tLCCUWMasterSchema.setGrpContNo(tLCContSchema.getGrpContNo());
			tLCCUWMasterSchema.setProposalContNo(tLCContSchema
					.getProposalContNo());
			tLCCUWMasterSchema.setUWNo(1);
			tLCCUWMasterSchema.setInsuredNo(tLCContSchema.getInsuredNo());
			tLCCUWMasterSchema.setInsuredName(tLCContSchema.getInsuredName());
			tLCCUWMasterSchema.setAppntNo(tLCContSchema.getAppntNo());
			tLCCUWMasterSchema.setAppntName(tLCContSchema.getAppntName());
			tLCCUWMasterSchema.setAgentCode(tLCContSchema.getAgentCode());
			tLCCUWMasterSchema.setAgentGroup(tLCContSchema.getAgentGroup());
			tLCCUWMasterSchema.setUWGrade(mUWGrade); // 核保级别
			tLCCUWMasterSchema.setAppGrade(mUWGrade); // 申报级别
			tLCCUWMasterSchema.setPostponeDay("");
			tLCCUWMasterSchema.setPostponeDate("");
			tLCCUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
			tLCCUWMasterSchema.setState(mContPassFlag);
			tLCCUWMasterSchema.setPassFlag(mContPassFlag);
			tLCCUWMasterSchema.setHealthFlag("0");
			tLCCUWMasterSchema.setSpecFlag("0");
			tLCCUWMasterSchema.setQuesFlag("0");
			tLCCUWMasterSchema.setReportFlag("0");
			tLCCUWMasterSchema.setChangePolFlag("0");
			tLCCUWMasterSchema.setPrintFlag("0");
			tLCCUWMasterSchema.setPrintFlag2("0");
			tLCCUWMasterSchema.setManageCom(tLCContSchema.getManageCom());
			tLCCUWMasterSchema.setUWIdea("");
			tLCCUWMasterSchema.setUpReportContent("");
			tLCCUWMasterSchema.setOperator(mOperator); // 操作员
			tLCCUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
			tLCCUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
			tLCCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			tLCCUWMasterSchema = tLCCUWMasterSet.get(1);
			tLCCUWMasterSchema.setUWNo(tLCCUWMasterSchema.getUWNo() + 1);
			tLCCUWMasterSchema.setState(mContPassFlag);
			tLCCUWMasterSchema.setPassFlag(mContPassFlag);
			tLCCUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
			tLCCUWMasterSchema.setUWGrade(mUWGrade); // 核保级别
			tLCCUWMasterSchema.setAppGrade(mUWGrade); // 申报级别
			tLCCUWMasterSchema.setOperator(mOperator); // 操作员
			tLCCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		}

		// 合同核保轨迹表
		LCCUWSubSchema tLCCUWSubSchema = new LCCUWSubSchema();
		LCCUWSubDB tLCCUWSubDB = new LCCUWSubDB();
		tLCCUWSubDB.setContNo(mContNo);
		LCCUWSubSet tLCCUWSubSet = new LCCUWSubSet();
		tLCCUWSubSet = tLCCUWSubDB.query();
		if (tLCCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWSubDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAtuoChkAfterInitService";
			tError.functionName = "prepareContUW";
			tError.errorMessage = mContNo + "合同核保轨迹表查失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		int nUWNo = tLCCUWSubSet.size();

		tLCCUWSubSchema.setUWNo(nUWNo + 1); // 第几次核保

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
		tLCCUWSubSchema.setPostponeDate(tLCCUWMasterSchema.getPostponeDate());
		tLCCUWSubSchema.setUpReportContent(tLCCUWMasterSchema
				.getUpReportContent());
		tLCCUWSubSchema.setHealthFlag(tLCCUWMasterSchema.getHealthFlag());
		tLCCUWSubSchema.setSpecFlag(tLCCUWMasterSchema.getSpecFlag());
		tLCCUWSubSchema.setSpecReason(tLCCUWMasterSchema.getSpecReason());
		tLCCUWSubSchema.setQuesFlag(tLCCUWMasterSchema.getQuesFlag());
		tLCCUWSubSchema.setReportFlag(tLCCUWMasterSchema.getReportFlag());
		tLCCUWSubSchema.setChangePolFlag(tLCCUWMasterSchema.getChangePolFlag());
		tLCCUWSubSchema.setChangePolReason(tLCCUWMasterSchema
				.getChangePolReason());
		tLCCUWSubSchema.setAddPremReason(tLCCUWMasterSchema.getAddPremReason());
		tLCCUWSubSchema.setPrintFlag(tLCCUWMasterSchema.getPrintFlag());
		tLCCUWSubSchema.setPrintFlag2(tLCCUWMasterSchema.getPrintFlag2());
		tLCCUWSubSchema.setUWIdea(tLCCUWMasterSchema.getUWIdea());
		tLCCUWSubSchema.setOperator(tLCCUWMasterSchema.getOperator()); // 操作员
		tLCCUWSubSchema.setManageCom(tLCCUWMasterSchema.getManageCom());
		tLCCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		tLCCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
		tLCCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		tLCCUWSubSchema.setModifyTime(PubFun.getCurrentTime());

		mLCCUWSubSet.add(tLCCUWSubSchema);

		if ("5".equals(mContPassFlag)) {
			// 核保错误信息表
			LCCUWErrorSchema tLCCUWErrorSchema = new LCCUWErrorSchema();
			LCCUWErrorDB tLCCUWErrorDB = new LCCUWErrorDB();
			String UWNoSql = "select * from lccuwerror where contno='"
					+ mContNo + "' order by uwno desc";
			LCCUWErrorSet tLCCUWErrorSet = new LCCUWErrorSet();
			tLCCUWErrorSet = tLCCUWErrorDB.executeQuery(UWNoSql);
			if (tLCCUWErrorDB.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCCUWErrorDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWAtuoChkAfterInitService";
				tError.functionName = "prepareContUW";
				tError.errorMessage = mContNo + "合同错误信息表查询失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (tLCCUWErrorSet.size() == 0) {
				nUWNo = 0;
			} else {
				nUWNo = tLCCUWErrorSet.get(1).getUWNo();
			}
			batchNo = nUWNo + 1;
			tLCCUWErrorSchema.setUWNo(nUWNo + 1);
			tLCCUWErrorSchema.setContNo(mContNo);
			tLCCUWErrorSchema.setGrpContNo(tLCCUWSubSchema.getGrpContNo());
			tLCCUWErrorSchema.setProposalContNo(tLCCUWSubSchema
					.getProposalContNo());
			tLCCUWErrorSchema.setInsuredNo(tLCCUWSubSchema.getInsuredNo());
			tLCCUWErrorSchema.setInsuredName(tLCCUWSubSchema.getInsuredName());
			tLCCUWErrorSchema.setAppntNo(tLCCUWSubSchema.getAppntNo());
			tLCCUWErrorSchema.setAppntName(tLCCUWSubSchema.getAppntName());
			tLCCUWErrorSchema.setManageCom(tLCCUWSubSchema.getManageCom());
			tLCCUWErrorSchema.setUWRuleCode(""); // 核保规则编码
			tLCCUWErrorSchema.setUWError(""); // 核保出错信息
			tLCCUWErrorSchema.setCurrValue(""); // 当前值
			tLCCUWErrorSchema.setModifyDate(PubFun.getCurrentDate());
			tLCCUWErrorSchema.setModifyTime(PubFun.getCurrentTime());
			tLCCUWErrorSchema.setUWPassFlag(mPolPassFlag);
			// tongmeng 2009-03-24 modify
			// 去除重复信息
			Hashtable tContHashtable = new Hashtable();
			for (int i = 0; i < tContList.size(); i++) {
				TransferData contTransferData = (TransferData) tContList.get(i);
				// 生成流水号
				String tserialno = PubFun1.CreateMaxNo("LCCUWERROR", 20);

				String tRuleid = (String) contTransferData
						.getValueByName("tRuleid");
				String templateId = (String) contTransferData
						.getValueByName("templateId");
				String result = (String) contTransferData
						.getValueByName("result");
				if (tContHashtable.containsKey(result)) {
					continue;
				} else {
					tContHashtable.put(result, result);
				}

				tLCCUWErrorSchema.setSerialNo(tserialno);
				tLCCUWErrorSchema.setUWRuleCode(tRuleid); // 转储规则引擎接口返回的模板的RuleId
				tLCCUWErrorSchema.setUWError(result); // 核保出错信息，即核保规则的文字描述内容
				tLCCUWErrorSchema.setUWGrade(mUWGrade);
				tLCCUWErrorSchema.setCurrValue(templateId); // 转储规则引擎接口返回的模板的模板号
				LCCUWErrorSchema ttLCCUWErrorSchema = new LCCUWErrorSchema();
				ttLCCUWErrorSchema.setSchema(tLCCUWErrorSchema);
				mLCCUWErrorSet.add(ttLCCUWErrorSchema);
			}
		}
		tLCCUWMasterSchema.setBatchNo(batchNo);
		mLCCUWMasterSet.add(tLCCUWMasterSchema);
		return true;
	}

	/**
	 * 准备险种核保信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean preparePolUW(LCPolSet tLCPolSet) {
		for (int i = 1; i <= tLCPolSet.size(); i++) {
			String tAddFeeFlag = "0";
			int batchNo = 0;
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = tLCPolSet.get(i);
			int tuwno = 0;
			LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
			LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
			tLCUWMasterDB.setPolNo(tLCPolSchema.getPolNo());
			LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
			tLCUWMasterSet = tLCUWMasterDB.query();
			if (tLCUWMasterDB.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWAtuoChkBL";
				tError.functionName = "prepareUW";
				tError.errorMessage = mOldPolNo + "个人核保总表取数失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			// 判断是否有加费信息
			/*
			 * String
			 * addFeeSql="select * from lcprem  where polno='"+tLCPolSchema
			 * .getPolNo()+"' and payplancode like '000000%%'"; SSRS tAddFee =
			 * new SSRS(); ExeSQL tExeSQL = new ExeSQL(); tAddFee =
			 * tExeSQL.execSQL(addFeeSql); if(tAddFee.MaxRow>0){
			 * tAddFeeFlag="1"; }
			 */
			int n = tLCUWMasterSet.size();
			if (n == 0) {
				tLCUWMasterSchema.setContNo(mContNo);
				tLCUWMasterSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
				tLCUWMasterSchema.setPolNo(tLCPolSchema.getPolNo());
				tLCUWMasterSchema.setProposalContNo(mPContNo);
				tLCUWMasterSchema.setProposalNo(tLCPolSchema.getProposalNo());
				tLCUWMasterSchema.setUWNo(1);
				tLCUWMasterSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
				tLCUWMasterSchema.setInsuredName(tLCPolSchema.getInsuredName());
				tLCUWMasterSchema.setAppntNo(tLCPolSchema.getAppntNo());
				tLCUWMasterSchema.setAppntName(tLCPolSchema.getAppntName());
				tLCUWMasterSchema.setAgentCode(tLCPolSchema.getAgentCode());
				tLCUWMasterSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
				tLCUWMasterSchema.setUWGrade(mUWGrade); // 核保级别
				tLCUWMasterSchema.setAppGrade(mUWGrade); // 申报级别
				tLCUWMasterSchema.setPostponeDay("");
				tLCUWMasterSchema.setPostponeDate("");
				tLCUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
				tLCUWMasterSchema.setState(mPolPassFlag);
				tLCUWMasterSchema.setPassFlag(mPolPassFlag);
				tLCUWMasterSchema.setHealthFlag("0");
				tLCUWMasterSchema.setSpecFlag("0");
				tLCUWMasterSchema.setQuesFlag("0");
				tLCUWMasterSchema.setReportFlag("0");
				tLCUWMasterSchema.setChangePolFlag("0");
				tLCUWMasterSchema.setPrintFlag("0");
				tLCUWMasterSchema.setManageCom(tLCPolSchema.getManageCom());
				tLCUWMasterSchema.setUWIdea("");
				tLCUWMasterSchema.setUpReportContent("");
				tLCUWMasterSchema.setOperator(mOperator); // 操作员
				tLCUWMasterSchema.setAddPremFlag("0");
				tLCUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
				tLCUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
				tLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
				tLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
			} else if (n == 1) {
				tLCUWMasterSchema = tLCUWMasterSet.get(1);

				tuwno = tLCUWMasterSchema.getUWNo();
				tuwno = tuwno + 1;

				tLCUWMasterSchema.setUWNo(tuwno);
				tLCUWMasterSchema.setProposalContNo(mPContNo);
				tLCUWMasterSchema.setState(mPolPassFlag);
				tLCUWMasterSchema.setPassFlag(mPolPassFlag);
				// tLCUWMasterSchema.setAddPremFlag(tAddFeeFlag);
				tLCUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
				tLCUWMasterSchema.setUWGrade(mUWGrade); // 核保级别
				tLCUWMasterSchema.setAppGrade(mUWGrade); // 申报级别
				tLCUWMasterSchema.setOperator(mOperator); // 操作员
				tLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
				tLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
			} else {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWAtuoChkBL";
				tError.functionName = "prepareUW";
				tError.errorMessage = mOldPolNo + "个人核保总表取数据不唯一!";
				this.mErrors.addOneError(tError);
				return false;
			}

			// 核保轨迹表 ln 2008-10-21 modify uwno计算方法修改
			LCUWSubSchema tLCUWSubSchema = new LCUWSubSchema();
			LCUWSubDB tLCUWSubDB = new LCUWSubDB();
			// tLCUWSubDB.setPolNo(mOldPolNo);
			String sqlUwno = "select * from lcuwsub where polno ='"
					+ tLCPolSchema.getPolNo() + "' order by uwno desc ";
			LCUWSubSet tLCUWSubSet = new LCUWSubSet();
			tLCUWSubSet = tLCUWSubDB.executeQuery(sqlUwno);
			if (tLCUWSubDB.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCUWSubDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWAtuoChkBL";
				tError.functionName = "prepareUW";
				tError.errorMessage = mOldPolNo + "个人核保轨迹表查失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			int m = tLCUWSubSet.size();
			int uwNo = 0;

			if (m > 0) {
				uwNo = tLCUWSubSet.get(1).getUWNo();
				tLCUWSubSchema.setUWNo(uwNo + 1);
			} else {
				tLCUWSubSchema.setUWNo(1); // 第1次核保
			}

			tLCUWSubSchema.setContNo(mContNo);
			tLCUWSubSchema.setPolNo(tLCPolSchema.getPolNo());
			tLCUWSubSchema.setGrpContNo(tLCUWMasterSchema.getGrpContNo());
			tLCUWSubSchema.setProposalContNo(tLCUWMasterSchema
					.getProposalContNo());
			tLCUWSubSchema.setProposalNo(tLCUWMasterSchema.getProposalNo());
			tLCUWSubSchema.setInsuredNo(tLCUWMasterSchema.getInsuredNo());
			tLCUWSubSchema.setInsuredName(tLCUWMasterSchema.getInsuredName());
			tLCUWSubSchema.setAppntNo(tLCUWMasterSchema.getAppntNo());
			tLCUWSubSchema.setAppntName(tLCUWMasterSchema.getAppntName());
			tLCUWSubSchema.setAgentCode(tLCUWMasterSchema.getAgentCode());
			tLCUWSubSchema.setAgentGroup(tLCUWMasterSchema.getAgentGroup());
			tLCUWSubSchema.setUWGrade(tLCUWMasterSchema.getUWGrade()); // 核保级别
			tLCUWSubSchema.setAppGrade(tLCUWMasterSchema.getAppGrade()); // 申请级别
			tLCUWSubSchema.setAutoUWFlag(tLCUWMasterSchema.getAutoUWFlag());
			tLCUWSubSchema.setState(tLCUWMasterSchema.getState());
			tLCUWSubSchema.setPassFlag(tLCUWMasterSchema.getState());
			tLCUWSubSchema.setPostponeDay(tLCUWMasterSchema.getPostponeDay());
			tLCUWSubSchema.setPostponeDate(tLCUWMasterSchema.getPostponeDate());
			tLCUWSubSchema.setUpReportContent(tLCUWMasterSchema
					.getUpReportContent());
			tLCUWSubSchema.setHealthFlag(tLCUWMasterSchema.getHealthFlag());
			tLCUWSubSchema.setSpecFlag(tLCUWMasterSchema.getSpecFlag());
			tLCUWSubSchema.setSpecReason(tLCUWMasterSchema.getSpecReason());
			tLCUWSubSchema.setQuesFlag(tLCUWMasterSchema.getQuesFlag());
			tLCUWSubSchema.setReportFlag(tLCUWMasterSchema.getReportFlag());
			tLCUWSubSchema.setChangePolFlag(tLCUWMasterSchema
					.getChangePolFlag());
			tLCUWSubSchema.setChangePolReason(tLCUWMasterSchema
					.getChangePolReason());
			tLCUWSubSchema.setAddPremReason(tLCUWMasterSchema
					.getAddPremReason());
			tLCUWSubSchema.setPrintFlag(tLCUWMasterSchema.getPrintFlag());
			tLCUWSubSchema.setPrintFlag2(tLCUWMasterSchema.getPrintFlag2());
			tLCUWSubSchema.setUWIdea(tLCUWMasterSchema.getUWIdea());
			tLCUWSubSchema.setOperator(tLCUWMasterSchema.getOperator()); // 操作员
			tLCUWSubSchema.setManageCom(tLCUWMasterSchema.getManageCom());
			tLCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			tLCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			tLCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tLCUWSubSchema.setModifyTime(PubFun.getCurrentTime());

			if ("5".equals(mPolPassFlag)) {
				// 核保错误信息表
				LCUWErrorSchema tLCUWErrorSchema = new LCUWErrorSchema();
				LCUWErrorDB tLCUWErrorDB = new LCUWErrorDB();
				String UWNoSql = "select * from lcuwerror where PolNo='"
						+ tLCPolSchema.getPolNo() + "' order by uwno desc";
				LCUWErrorSet tLCUWErrorSet = new LCUWErrorSet();
				tLCUWErrorSet = tLCUWErrorDB.executeQuery(UWNoSql);
				if (tLCUWErrorDB.mErrors.needDealError()) {
					// @@错误处理
					this.mErrors.copyAllErrors(tLCUWErrorDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "UWAtuoChkBL";
					tError.functionName = "prepareUW";
					tError.errorMessage = mOldPolNo + "个人错误信息表查询失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				if (tLCUWErrorSet.size() == 0) {
					m = 0;
				} else {
					m = tLCUWErrorSet.get(1).getUWNo();
				}
				batchNo = m + 1;
				tLCUWErrorSchema.setUWNo(m + 1);
				tLCUWErrorSchema.setContNo(mContNo);
				tLCUWErrorSchema.setGrpContNo(tLCUWMasterSchema.getGrpContNo());
				tLCUWErrorSchema.setProposalContNo(mPContNo);
				tLCUWErrorSchema.setPolNo(tLCPolSchema.getPolNo());
				tLCUWErrorSchema.setProposalNo(tLCPolSchema.getProposalNo());
				tLCUWErrorSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
				tLCUWErrorSchema.setInsuredName(tLCPolSchema.getInsuredName());
				tLCUWErrorSchema.setAppntNo(tLCPolSchema.getAppntNo());
				tLCUWErrorSchema.setAppntName(tLCPolSchema.getAppntName());
				tLCUWErrorSchema.setManageCom(tLCPolSchema.getManageCom());
				tLCUWErrorSchema.setModifyDate(PubFun.getCurrentDate());
				tLCUWErrorSchema.setModifyTime(PubFun.getCurrentTime());
				tLCUWErrorSchema.setUWPassFlag(mPolPassFlag);

				for (int a = 0; a < tPolList.size(); a++) {
					TransferData PolTransferData = (TransferData) tPolList
							.get(a);
					String tPolNo = (String) PolTransferData
							.getValueByName("polno");
					if (tPolNo.equals(tLCPolSchema.getPolNo())) {

						String templateId = (String) PolTransferData
								.getValueByName("templateId");
						String result = (String) PolTransferData
								.getValueByName("result");
						String tRuleid = (String) PolTransferData
								.getValueByName("tRuleid");
						String tserialno = PubFun1.CreateMaxNo("LCUWERROR", 20);

						tLCUWErrorSchema.setSerialNo(tserialno);
						tLCUWErrorSchema.setUWRuleCode(tRuleid); // 转储规则引擎接口返回的模板的Ruleid
						tLCUWErrorSchema.setUWError(result); // 核保出错信息，即核保规则的文字描述内容
						tLCUWErrorSchema.setUWGrade(mUWGrade);
						tLCUWErrorSchema.setCurrValue(templateId); // 转储规则引擎接口返回的模板号
						// tLCUWErrorSchema.setSugPassFlag(tLMUWSchema.getPassFlag());
						// // picch需求对自核规则分类（体检、契调）

						LCUWErrorSchema ttLCUWErrorSchema = new LCUWErrorSchema();
						ttLCUWErrorSchema.setSchema(tLCUWErrorSchema);
						mLCUWErrorSet.add(ttLCUWErrorSchema);
					}
				}
			}
			tLCUWMasterSchema.setBatchNo(batchNo);
			mLCUWSubSet.add(tLCUWSubSchema);
			mLCUWMasterSet.add(tLCUWMasterSchema);
		}
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		// 校验核保级别
		if (!checkUWGrade()) {
			return false;
		}

		// 校验是否复核
		// if (!checkApprove(mLCContSchema))
		// return false;

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

		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		mOperator = mGlobalInput.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据Operate失败!");
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据ManageCom失败!");
			return false;
		}

		// 获得当前工作任务的任务ID
		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中mContNo失败!");
			return false;
		}

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (tLCContDB.getInfo()) { // 验证LCCont表中是否存在该合同项记录
			mLCContSchema.setSchema(tLCContDB.getSchema());
		} else {
			CError.buildErr(this, "合同号为" + mLCContSchema.getContNo() + "未查询到!");
			return false;
		}

		// 银保通标志
		mBankInsu = (String) mTransferData.getValueByName("BankInsu");
		if (mBankInsu == null) {
			mBankInsu = "0";
		}

		// theCount = Integer.parseInt((String)
		// mTransferData.getValueByName("totalCount"));

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

	public float parseFloat(String s) {
		if (s.length() < 0 || s.equals("")) {
			return 0;
		}
		float f1 = 0;
		String tmp = "";
		String s1 = "";
		for (int i = 0; i < s.length(); i++) {
			s1 = s.substring(i, i + 1);
			if (s1.equals("0") || s1.equals("1") || s1.equals("2")
					|| s1.equals("3") || s1.equals("4") || s1.equals("5")
					|| s1.equals("6") || s1.equals("7") || s1.equals("8")
					|| s1.equals("9") || s1.equals(".")) {
				tmp = tmp + s1;
			} else if (tmp.length() > 0) {
				break;
			}
		}
		f1 = Float.parseFloat(tmp);
		return f1;
	}

	public String getHierarhy() {
		// String tsql = "select RiskSortValue from lmrisksort where
		// riskcode='"+tLCPolSchema.getRiskCode()+"' and risksorttype='5'";
		// ExeSQL riskSql = new ExeSQL();
		// SSRS sumAmntSSRS = new SSRS();
		// sumAmntSSRS = riskSql.execSQL(tsql);
		// String riskty=sumAmntSSRS.GetText(1,1);
		// tsql = "select uwpopedom from lduwgradeperson where uwtype=1 and
		// maxamnt>'"+riskamnt+"' and uwkind='"+riskty+"' order by maxamnt";
		// sumAmntSSRS = riskSql.execSQL(tsql);
		return hierarhy; // 返回几级核保师
	}

	public String getReDistribute() {
		logger.debug("返回分保标志前====" + reDistribute);
		return reDistribute; // 返回是否需要分保--1:需要0:不需要

	}

	public boolean SendRefuseNotice(String tContNo) {
		String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);
		String mPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); // 生成打印流水号
		mLOPRTManagerSchema.setPrtSeq(mPrtSeq);
		mLOPRTManagerSchema.setOtherNo(tContNo);
		mLOPRTManagerSchema.setOtherNoType("02");
		mLOPRTManagerSchema.setCode("JB00");
		mLOPRTManagerSchema.setManageCom(mLCContSchema.getManageCom());
		mLOPRTManagerSchema.setAgentCode(mLCContSchema.getAgentCode());
		mLOPRTManagerSchema.setReqCom(mLCContSchema.getManageCom());
		mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
		mLOPRTManagerSchema.setExeCom(mLCContSchema.getManageCom());
		mLOPRTManagerSchema.setExeOperator(mGlobalInput.Operator);
		mLOPRTManagerSchema.setPrtType("0");
		mLOPRTManagerSchema.setStateFlag("0");
		mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
		mLOPRTManagerSchema.setOldPrtSeq(mPrtSeq);
		mLOPRTManagerSchema.setPatchFlag("0");
		return true;
	}

	private void GetAllSumAmnt(String tInsuredNo) {
		// //为自核服务 write by yaory///////
		String risktype = "";
		try {
			logger.debug(mContNo);
			ExeSQL riskSql = new ExeSQL();
			// tongmeng 2009-03-12 modify
			// 使用新的自核风险保额计算规则
			String tsql = "";
			// 寿险风险保额
			/*
			 * -- tRiskType = 1 寿险风险保额 -- tRiskType = 2 重疾险风险保额 -- tRiskType = 3
			 * 医疗险风险保额 -- tRiskType = 4 意外险风险保额 -- tRiskType = 12 身故风险保额 --
			 * tRiskType = 13 寿险体检额度 -- tRiskType = 14 重疾体检额度 -- tRiskType = 15
			 * 医疗体检额度
			 */
//			tsql = "select healthyamnt2('" + tInsuredNo
//					+ "','1','1') from dual";
			 if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				 tsql = "select healthyamnt2('" + tInsuredNo + "','1','1') from dual";
			 }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				 tsql ="{ call HEALTHYAMNT2(?#@d#?,'" + tInsuredNo+ "','1','1') }";
			 }
			String tempAmnt = riskSql.getOneValue(tsql);
			LSumDangerAmnt = parseFloat(tempAmnt);// 寿险

//			tsql = "select healthyamnt2('" + tInsuredNo
//					+ "','2','1') from dual";
			 if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				 tsql = "select healthyamnt2('" + tInsuredNo +  "','2','1') from dual";
			 }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				 tsql ="{ call HEALTHYAMNT2(?#@d#?,'" + tInsuredNo+ "','2','1') }";
			 }
			tempAmnt = riskSql.getOneValue(tsql);
			DSumDangerAmnt = parseFloat(tempAmnt); // 重疾

//			tsql = "select healthyamnt2('" + tInsuredNo
//					+ "','3','1') from dual";
			 if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				 tsql = "select healthyamnt2('" + tInsuredNo + "','3','1') from dual";
			 }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				 tsql ="{ call HEALTHYAMNT2(?#@d#?,'" + tInsuredNo+ "','3','1') }";
			 }
			tempAmnt = riskSql.getOneValue(tsql);
			SSumDangerAmnt = parseFloat(tempAmnt); // 医疗

//			tsql = "select healthyamnt2('" + tInsuredNo
//					+ "','4','1') from dual";
			 if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				 tsql = "select healthyamnt2('" + tInsuredNo + "','4','1') from dual";
			 }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				 tsql ="{ call HEALTHYAMNT2(?#@d#?,'" + tInsuredNo+ "','4','1') }";
			 }
			tempAmnt = riskSql.getOneValue(tsql);
			MSumDangerAmnt = parseFloat(tempAmnt); // 意外
//			tsql = "select healthyamnt2('" + tInsuredNo
//					+ "','12','1') from dual";
			 if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				 tsql = "select healthyamnt2('" + tInsuredNo + "','12','1') from dual";
			 }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				 tsql ="{ call HEALTHYAMNT2(?#@d#?,'" + tInsuredNo+ "','12','1') }";
			 }
			tempAmnt = riskSql.getOneValue(tsql);
			SSumDieAmnt = parseFloat(tempAmnt); // 身故
			AllSumAmnt = LSumDangerAmnt + DSumDangerAmnt + MSumDangerAmnt;// 2009-3-5
																			// modify累计风险保额=累计寿险风险保额+累计重疾风险保额+累计意外伤害风险保额

		} catch (Exception ex) {
		}
	}

	/**
	 * @param 根据数据库中的描述算出核保级别
	 *            执行getUWGrade函数
	 * */
	private String getUWGrade(String tInsuredNo) {
		String tUWGrade = "";
		String tempUWGrade = "";
		try {
			logger.debug(mContNo);
			ExeSQL riskSql = new ExeSQL();
			double RiskAmnt1 = 0;// 累计寿险风险保额
			double RiskAmnt2 = 0;// 累积重疾风险保额
			double RiskAmnt4 = 0;// 累积意外风险保额
			double RiskAmnt6 = 0;
			// duanyh 2009-03-14 modify
			// 使用新的自核风险保额计算规则
			String tsql = "";
			// 寿险风险保额
			/*
			 * -- tRiskType = 1 寿险风险保额 -- tRiskType = 2 重疾险风险保额 -- tRiskType = 3
			 * 医疗险风险保额 -- tRiskType = 4 意外险风险保额 -- tRiskType = 12 身故风险保额 --
			 * tRiskType = 13 寿险体检额度 -- tRiskType = 14 重疾体检额度 -- tRiskType = 15
			 * 医疗体检额度
			 */
//			tsql = "select healthyamnt2('" + tInsuredNo
//					+ "','1','1') from dual";
			 if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				 tsql = "select healthyamnt2('" + tInsuredNo + "','1','1') from dual";
			 }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				 tsql ="{ call HEALTHYAMNT2(?#@d#?,'" + tInsuredNo+ "','1','1') }";
			 }
			String tempAmnt = riskSql.getOneValue(tsql);
			RiskAmnt1 = parseFloat(tempAmnt);// 寿险

//			tsql = "select healthyamnt2('" + tInsuredNo
//					+ "','2','1') from dual";
			 if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				 tsql = "select healthyamnt2('" + tInsuredNo + "','2','1') from dual";
			 }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				 tsql ="{ call HEALTHYAMNT2(?#@d#?,'" + tInsuredNo+ "','2','1') }";
			 }
			tempAmnt = riskSql.getOneValue(tsql);
			RiskAmnt2 = parseFloat(tempAmnt); // 重疾

//			tsql = "select healthyamnt2('" + tInsuredNo
//					+ "','4','1') from dual";
			 if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				 tsql = "select healthyamnt2('" + tInsuredNo + "','4','1') from dual";
			 }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				 tsql ="{ call HEALTHYAMNT2(?#@d#?,'" + tInsuredNo+ "','4','1') }";
			 }
			tempAmnt = riskSql.getOneValue(tsql);
			RiskAmnt4 = parseFloat(tempAmnt); // 意外

			RiskAmnt6 = RiskAmnt1 + RiskAmnt2 + RiskAmnt4;
			logger.debug("RiskAmnt1:" + RiskAmnt1 + "  RiskAmnt2:" + RiskAmnt2
					+ " " + " RiskAmnt4:" + RiskAmnt4 + "  RiskAmnt6:"
					+ RiskAmnt6);
			// 执行getUWGrade函数，返回核保级别
			String UWGradeSql = "select trim(nvl(getUWGrade('1','" + RiskAmnt1
					+ "','2','" + RiskAmnt2 + "'," + "'4','" + RiskAmnt4
					+ "','6','" + RiskAmnt6 + "'),1)) from dual";
			tempUWGrade = riskSql.getOneValue(UWGradeSql);
			if (tUWGrade.compareTo(tempUWGrade) < 0) {
				tUWGrade = tempUWGrade;
			}
		} catch (Exception ex) {
		}
		return tUWGrade;
	}

	/**
	 * 判断被保人的BMI是否在标准范围内
	 * 
	 * @param tCountNo
	 * @param insured
	 * @param tLCContSchema
	 * @param tFlag
	 *            0-身高标准 1 体重标准 2 BMI标准
	 * @return
	 */
	private String getBMIFlag(String tCountNo,
			LCInsuredSchema tLCInsuredSchema, BOMInsured insured,
			LCContSchema tLCContSchema, String tFlag) {

		/*
		 * select decode(count(*),0,0,1) from BMIStandard where 1=1 and Sex = ''
		 * and StartAge>='' and StartAgeFlag='' and EndAge<'' and EndAgeFlag=''
		 * and StartStature>='' and EndStature<='' and StartAvoirdupois>='' and
		 * EndAvoirdupois<='' and StartBMI>='' and EndBMI<=''
		 */
		try {
			String tRes = "";
			String tAgeFlag = "";
			int tAge = 0;
			String tSex = tLCInsuredSchema.getSex();
			double tStature = (insured.getStature()).doubleValue();
			double tAvoirdupois = (insured.getAvoirdupois()).doubleValue();
			double tBMI = (insured.getBMI()).doubleValue();
			String tBirthDay = tLCInsuredSchema.getBirthday();
			String tPolApplyDate = tLCContSchema.getPolApplyDate();
			tAge = PubFun.calInterval(tBirthDay, tPolApplyDate, "Y");
			if (tAge == 0) {
				// 如果为0的话,计算几个月
				tAge = PubFun.calInterval(tBirthDay, tPolApplyDate, "M");
				tAgeFlag = "M";
				// 如果0个月则需判断多少天
				if (tAge == 0) {
					tAge = PubFun.calInterval(tBirthDay, tPolApplyDate, "D");
					// 如果年龄小于28天则按0个月计算
					if (tAge < 28) {
						tAge = 0;
						tAgeFlag = "M";
					} else {
						tAgeFlag = "D";
					}
				}
			} else {
				tAgeFlag = "A";
			}
			String tSQL = "select decode(count(*),0,0,1) from BMIStandard where 1=1 "
					+ " and Sex = '"
					+ tSex
					+ "' "
					+ " and StartAge<='"
					+ tAge
					+ "' and StartAgeFlag='"
					+ tAgeFlag
					+ "' "
					+ " and EndAge>'"
					+ tAge
					+ "' and EndAgeFlag='"
					+ tAgeFlag
					+ "' ";
			if (tFlag.equals("0")) {
				tSQL = tSQL + " and StartStature<='" + tStature + "' "
						+ " and EndStature>='" + tStature + "' ";
			} else if (tFlag.equals("1")) {
				tSQL = tSQL + " and StartAvoirdupois<='" + tAvoirdupois + "' "
						+ " and EndAvoirdupois>='" + tAvoirdupois + "' ";
			} else if (tFlag.equals("2")) {
				tSQL = tSQL + " and StartBMI<='" + tBMI + "' "
						+ " and EndBMI>='" + tBMI + "' ";
			}
			logger.debug("BMISQL:" + tSQL);
			ExeSQL tExeSQL = new ExeSQL();
			String tValue = tExeSQL.getOneValue(tSQL);
			if (tValue != null && !tValue.equals("")
					&& Integer.parseInt(tValue) > 0) {
				tRes = "1";
			} else {
				tRes = "0";
			}

			return tRes;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "1";
		}
	}

	private String getZBFlag(double SX, double ZJ, double YW, String EM) {
		/*
		 * 累计寿险风险保额大于230万的投保单 累计重疾风险保额大于120万的投保单 累计意外险风险保额大于250万的投保单
		 * 累计寿险风险保额大于30万元且EM评点大于150点的投保单 累计重疾风险保额大于20万元且EM评点大于125点的投保单
		 */
		String tRes = "0";
		try {
			String tSQL = "select decode(nvl(sum(A.x),0),0,0,1) from  "
					+ "( select 1 x from dual where " + SX + " >2300000 or "
					+ ZJ + " >1200000 or " + YW + " >2500000 or " + "(" + SX
					+ " >300000  and " + EM + " >150) " + " or " + "(" + ZJ
					+ " >200000  and " + EM + " >125 " + ")" + ") A";
			ExeSQL tExeSQL = new ExeSQL();
			tRes = tExeSQL.getOneValue(tSQL);
			logger.debug("ZB tSQL:" + tSQL + ":Value:" + tRes);
			if (tRes == null || tRes.equals("")) {
				tRes = "0";
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return "1";
		}
		return tRes;
	}

	/**
	 * 业务员告知异常
	 * 
	 * @param tContNo
	 * @return
	 */
	private String getSpecAImpart(String tContNo) {
		String tRes = "0";
		try {
			String tSQL = "select nvl(sum(A.x),0) from ( "
					+ " select (case when count(*)>0 then 0 else 1 end ) x "
					+ " from lccustomerimpart where customernotype='2' "
					+ " and contno='" + tContNo + "' "
					+ " and impartcode in ('A0152','D0153') " + " union "
					+ " select ( case when count(*)>0 then 1 else 0 end ) x "
					+ " from lccustomerimpart where customernotype='2' "
					+ " and contno='" + tContNo + "' "
					+ " and impartcode in ('A0153','A0154','D0154','D0155') "
					/*
					 * + " union " +
					 * " select ( case when count(*)>0 then 0 else 1 end ) x " +
					 * " from lccustomerimpart where customernotype='2' " +
					 * " and contno='"+tContNo+"' " +
					 * " and impartcode in ('A0156','A0157','D0151','D0157','D0158') "
					 * +
					 * " and regexp_replace(impartparammodle,'/','') is not null "
					 */
					/*
					 * + " union " +
					 * " select (case when count(*)>0 then 0 else 1 end ) x " +
					 * " from lccustomerimpart where customernotype='2' " +
					 * " and contno='"+tContNo+"' " +
					 * " and impartcode in ('A0155','D0156') and " +
					 * " (regexp_replace(impartparammodle,'/','')='业务员推销' " +
					 * " or regexp_replace(impartparammodle,'/','')='同事朋友推荐' ) "
					 */
					+ ") A ";
			ExeSQL tExeSQL = new ExeSQL();
			tRes = tExeSQL.getOneValue(tSQL);
			logger.debug(":tSQL:" + tSQL);
			if (tRes == null || tRes.equals("") || Integer.parseInt(tRes) == 0) {
				return "0";
			} else {
				return "1";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "1";
		}

	}

	/**
	 * 代理人的_告知投保人投保经过异常
	 * 
	 * @param tContNo
	 * @return
	 */
	private String getProAImpart(String tContNo) {
		String tRes = "0";
		try {
			String tSQL = "select nvl(sum(A.x),0) from ( "
					+ " select (case when count(*)>0 then 0 else 1 end ) x "
					+ " from lccustomerimpart where customernotype='2' "
					+ " and contno='"
					+ tContNo
					+ "' "
					+ " and impartcode in ('A0155','D0156') and "
					+ " (regexp_replace(impartparammodle,'/','')='业务员推销' "
					+ " or regexp_replace(impartparammodle,'/','')='同事朋友推荐' )) A ";
			ExeSQL tExeSQL = new ExeSQL();
			tRes = tExeSQL.getOneValue(tSQL);
			logger.debug(":tSQL1:" + tSQL);
			if (tRes == null || tRes.equals("") || Integer.parseInt(tRes) == 0) {
				return "0";
			} else {
				return "1";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "1";
		}
	}

	/**
	 * 怀孕周数 默认返回0
	 * 
	 * @param customerno
	 * @return Double
	 * */
	private Double getPregnancyWeeks(String tCustomerno) {
		Double tPregnancyWeeks = null;
		try {
			String ttPregnancyWeeksSql = "select impartparammodle from lccustomerimpart where 1=1"
					+ " and customerno='"
					+ tCustomerno
					+ "' and prtno='"
					+ mContNo
					+ "'"
					+ " and impartver in('A05','A01','D01')"
					+ " and impartcode in('A0521','A0113a','D0112a')";
			ExeSQL mExeSQl = new ExeSQL();
			String tPreWeeks = mExeSQl.getOneValue(ttPregnancyWeeksSql);
			if (!(tPreWeeks == null || "".equals(tPreWeeks.trim()))) {
				tPregnancyWeeks = Double.valueOf(tPreWeeks);
			} else {
				tPregnancyWeeks = Double.valueOf("0");
			}
		} catch (Exception e) {
			CError.buildErr(this, "印刷号：" + mContNo + "客户：" + tCustomerno
					+ "怀孕周数数据有误！");
			e.printStackTrace();
			tPregnancyWeeks = Double.valueOf("0");
		}
		return tPregnancyWeeks;
	}

	/**
	 * 吸烟年数
	 * 
	 * @param tCustomerType
	 *            A-投保人 I-被保人 FreeFlag 豁免标记
	 * @return 默认返回0
	 * */
	private Double getSmokeYears(String tCustomerno, String tCustomerType,
			boolean FreeFlag) {
		Double tSmokeYears = null;
		String impartparamno = "";
		if (tCustomerType.equals("A") || FreeFlag == true) {
			impartparamno = "4";
		} else {
			impartparamno = "2";
		}
		try {
			String tSmokeYearsSql = "select impartparam from lccustomerimpartparams where"
					+ " impartver in('A05','A01','D01') and impartcode in('A0502','A0102','D0102')"
					+ " and impartparamno='"
					+ impartparamno
					+ "'"
					+ "and prtno='"
					+ mContNo
					+ "' and customerno='"
					+ tCustomerno + "'";
			ExeSQL mExeSQl = new ExeSQL();
			String tSmoYears = mExeSQl.getOneValue(tSmokeYearsSql);
			if (!(tSmoYears == null || "".equals(tSmoYears.trim()))) {
				tSmokeYears = Double.valueOf(tSmoYears);
			} else {
				tSmokeYears = Double.valueOf("0");
			}
		} catch (NumberFormatException e) {
			CError.buildErr(this, "印刷号：" + mContNo + "客户：" + tCustomerno
					+ "吸烟年数数据有误！");
			e.printStackTrace();
			tSmokeYears = Double.valueOf("0");
		}
		return tSmokeYears;
	}

	/**
	 * 饮酒类型 1-啤酒 2-红酒 3-白酒 4-其他 默认返回0
	 * 
	 * 09-09-27 目前规则引擎只支持一种饮酒类型 故注掉原逻辑 与业管陈军朝沟通后确认目前只取白酒
	 * */
	private String getDrinkType(String tCustomerNo) {
		String tDrinkType = "";
		// String tDrinkTypeSql =
		// "select case  when impartparamno='2' then 1 when impartparamno='3' then 2"
		// +
		// " when impartparamno='4' then 3 when impartparamno='5' then 4 else 0 end"
		// +
		// " from lccustomerimpartparams where impartver = 'A05' and impartcode='A0503'"
		// + " and impartparam !='0'"
		// + " and impartparamno in ('2','3','4','5')"
		// + " and customerno='"+tCustomerNo+"' and prtno='"+mContNo+"' ";
		// tDrinkType = mExeSQL.getOneValue(tDrinkTypeSql); 09-09-27
		// 目前规则引擎只支持一种饮酒类型
		// 故注掉原逻辑 与业管陈军朝沟通后确认目前只取白酒
		String tDrinkTypeSql = "select count(1) from lccustomerimpartparams where impartparamno='4' and impartcode='A0503'"
				+ " and impartparam !='0' and customerno='"
				+ tCustomerNo
				+ "'"
				+ " and prtno='" + mContNo + "'";
		tDrinkType = mExeSQL.getOneValue(tDrinkTypeSql);
		if (tDrinkType == null || tDrinkType.equals("")
				|| Integer.parseInt(tDrinkType) == 0) {
			tDrinkType = "0";
		} else {
			tDrinkType = "3";
		}
		return tDrinkType;
	}

	/**
	 * 计算饮酒年数 默认返回0
	 * */
	private Double getDrinkYears(String tCustomerNo) {
		Double tDrinkYears = null;
		try {
			String tDrinkYearsSql = "select impartparam from lccustomerimpartparams where impartver='A05'"
					+ " and impartcode='A0503' and impartparamno='1'"
					+ " and customerno='"
					+ tCustomerNo
					+ "' and prtno='"
					+ mContNo
					+ "'"
					+ " union"
					+ " select impartparam from lccustomerimpartparams where impartver in ('A01','D01')"
					+ " and impartcode in ('A0103','D0103') and impartparamno='3'"
					+ " and customerno='"
					+ tCustomerNo
					+ "' and prtno='"
					+ mContNo + "'";
			String tDriYears = mExeSQL.getOneValue(tDrinkYearsSql);
			tDriYears = mExeSQL.getOneValue(tDrinkYearsSql);
			if (!(tDriYears == null || "".equals(tDriYears.trim()))) {
				tDrinkYears = Double.valueOf(tDriYears);
			} else {
				tDrinkYears = Double.valueOf("0");
			}
		} catch (NumberFormatException e) {
			CError.buildErr(this, "印刷号：" + mContNo + "客户：" + tCustomerNo
					+ "饮酒年数数据有误！");
			e.printStackTrace();
			tDrinkYears = Double.valueOf("0");
		}
		return tDrinkYears;
	}

	/**
	 * 获得各种告知
	 * 
	 * @param impartType
	 *            01-孕妇告知 02-危险运动爱好 03-交通事故告知 04-出国意向告知
	 * 
	 * @return 0-否 1-是
	 * */
	private String getISImpart(String tCustomerNo, String impartType) {
		String tReturn = "";
		StringBuffer tReturnSql = new StringBuffer();
		tReturnSql
				.append("select decode(count(1),0,0,1) from lccustomerimpart where");
		if (impartType.equals("01")) {
			// 孕妇告知
			tReturnSql
					.append(" impartver in ('A01','A05','D01') and impartcode in ('A0521','A0113a','D0112a')");
		} else if (impartType.equals("02")) {
			// 危险运动爱好
			tReturnSql
					.append(" impartver in ('A01','A06','D01','C01') and impartcode in ('A0105','A0530','D0105','C0105')");
		} else if (impartType.equals("03")) {
			// 交通事故告知
			tReturnSql
					.append(" impartver in ('A06') and impartcode in ('A0532')");
		} else if (impartType.equals("04")) {
			// 出国意向告知
			tReturnSql
					.append(" impartver in ('A06','D02') and impartcode in ('A0533','D0117')");
		}
		tReturnSql.append("and contno='" + mContNo + "' and customerno='"
				+ tCustomerNo + "'");
		tReturn = mExeSQL.getOneValue(tReturnSql.toString());
		return tReturn;
	}

	/**
	 * 康顺、每日给付住院合同份数
	 * 
	 * @param tRiskType
	 *            : 1-康顺 2-每日住院
	 * */
	private Double getSumCont(String tCustomerNo, String tCalType) {
		Double SumKSCont = null;
		String tRiskType = "";
		if ("1".equals(tCalType)) {
			tRiskType = "'141803','111602'";
		} else {
			tRiskType = "'121701','121704'";
		}
		try {
			String tSumKSContSql = "select count(prtno) from lccont a where exists ("
					+ " select 1 from lcpol b where b.appflag not in ('4','9')"
					+ " and b.uwflag not in ('1','2','a')"
					+ " and b.insuredno='"
					+ tCustomerNo
					+ "'"
					+ " and b.riskcode in ("
					+ tRiskType
					+ ")"
					+ " and a.contno=b.contno)";
			String tSumKSCont = mExeSQL.getOneValue(tSumKSContSql);
			if (!(tSumKSCont == null || "".equals(tSumKSCont.trim()))) {
				SumKSCont = Double.valueOf(tSumKSCont);
			} else {
				SumKSCont = Double.valueOf("0");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			SumKSCont = Double.valueOf("0");
		}
		return SumKSCont;
	}

	/**
	 * 被保险人的金玉满堂系列产品份数
	 * 
	 * @param tCustomerNo
	 *            : 被保人客户号
	 * */
	private Double getSumJYMTCount(String tCustomerNo) {
		Double SumJYMTCount = null;
		try {
			String tSumJYMTCountSql = "select sum(mult) from lcpol a where appflag not in ('4','9') "
					+ " and uwflag not in ('1','2','a') "
					+ " and riskcode in ('312201','312202','312203','312204','312206') "
					+ " and insuredno='" + tCustomerNo + "' ";
			String tSumJYMTCount = mExeSQL.getOneValue(tSumJYMTCountSql);
			if (!(tSumJYMTCount == null || "".equals(tSumJYMTCount.trim()))) {
				SumJYMTCount = Double.valueOf(tSumJYMTCount);
			} else {
				SumJYMTCount = Double.valueOf("0");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			SumJYMTCount = Double.valueOf("0");
		}
		return SumJYMTCount;
	}

	/**
	 * 被保险人的富贵盈门系列产品保费
	 * 
	 * @param tCustomerNo
	 *            : 被保人客户号
	 * */
	private Double getSumFGYMPrem(String tCustomerNo) {
		Double SumFGYMPrem = null;
		try {
			String tSumFGYMPremSql = "select nvl(sum(Prem),0) from lcpol a where  appflag not in ('4','9') "
					+ " and uwflag not in ('1','2','a') "
					+ " and riskcode in ('314301','314302') "
					+ " and insuredno='" + tCustomerNo + "' ";
			String tSumFGYMPrem = mExeSQL.getOneValue(tSumFGYMPremSql);
			if (!(tSumFGYMPremSql == null || "".equals(tSumFGYMPremSql.trim()))) {
				SumFGYMPrem = Double.valueOf(tSumFGYMPrem);
			} else {
				SumFGYMPrem = Double.valueOf("0");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			SumFGYMPrem = Double.valueOf("0");
		}
		return SumFGYMPrem;
	}

	/**
	 * 既往告知事项不全为否
	 * */
	private String getOImpart(String tCustomerNo) {
		String OImpart = "";
		String tOImpartSql = "select count(*) from lccustomerimpart where impartcode in"
				+ "('A0105','A0110','A0111e','A0111j','A0106','A0111a','A0111f',"
				+ "'A0112','A0107','A0111b','A0111g','A0108','A0111c','A0111h','A0109','A0111d',"
				+ "'A0111i','D0105','D0110a','D0110e','D0110i','D0106','D0110b','D0110f','D0110j',"
				+ "'D0107','D0110c','D0110g','D0111','D0108','D0110d','D0110h','D0122','D0109',"
				+ "'C0101','C0102','C0103','C0104','C0105','C0106','B0101','B0102','B0103','B0104',"
				+ "'A0113a','A0113b','D0112a','D0112b','A0114b','D0121','A0115a','A0115b','D0113a',"
				+ "'D0113b','A0117','A0118','D0115','D0116','D0117','C0108','A0512','A0513','A0510',"
				+ "'A0511','A0516','A0517','A0514','A0515','A0507','A0506','A0509','A0508','A0505',"
				+ "'A0504','A0524','A0526','A0525','A0522','A0519','A0518','A0520','A0532','A0529',"
				+ "'A0530','A0528','B0504','B0505','B0503','B0501','B0502','B0506','B0508','B0507') "
				+ "and customerno='"
				+ tCustomerNo
				+ "' and contno <> '"
				+ mContNo + "'";
		String tOImpart = mExeSQL.getOneValue(tOImpartSql);
		if (tOImpart != null && !tOImpart.equals("")
				&& Integer.parseInt(tOImpart) > 0) {
			OImpart = "1";
		} else {
			OImpart = "0";
		}
		return OImpart;
	}

	/**
	 * 查询被保人年收入
	 * */
	private Double getYearIncome(String tCustomerNo) {
		Double YearIncome = null;
		try {
			// 09-10-10 与陈军朝确定后被保人年收入逻辑改为如果投被关系为本人则取年收入数值最大的
			// 同时为了对豁免险的支持，查询的sql改为查0534的impartparamno in ('1','3')并且取最大值
			String tYearIncomeSql = "select nvl(max(A.x),0) from (select nvl(max(ImpartParam*1), 0) x"
					+ " from lccustomerimpartparams"
					+ " where impartcode in ('A0120', 'D0119')"
					+ " and impartver in ('A02', 'D02')"
					+ " and impartparamno in ('1','3')"
					+ " and contno = '"
					+ mContNo
					+ "'"
					+ " and customerno = '"
					+ tCustomerNo
					+ "'"
					+ " union"
					+ " select nvl(max(impartparam * 10000),0) x"
					+ " from lccustomerimpartparams"
					+ " where impartcode = 'A0534'"
					+ " and impartparamno in ('1','3')"
					+ " and contno = '"
					+ mContNo
					+ "'"
					+ " and customerno = '"
					+ tCustomerNo
					+ "') A";
			String tYearIncome = mExeSQL.getOneValue(tYearIncomeSql);
			if (!(tYearIncome == null || "".equals(tYearIncome.trim()))) {
				YearIncome = Double.valueOf(tYearIncome);
			} else {
				YearIncome = Double.valueOf("0");
			}
		} catch (NumberFormatException e) {
			CError.buildErr(this, "印刷号：" + mContNo + "客户：" + tCustomerNo
					+ "年收入数据有误！");
			e.printStackTrace();
			YearIncome = Double.valueOf("0");
		}
		return YearIncome;
	}

	/**
	 * 交通事故记录
	 * */
	private String getTrafficAccident(String tCustomerNo) {
		String TrafficAccident = "";
		String tTrafficAccidentSql = "select decode(count(1),0,0,1) from lccustomerimpart"
				+ " where impartcode in ('A0104','D0104','A0532')"
				+ " and contno='"
				+ mContNo
				+ "' and customerno='"
				+ tCustomerNo + "'";
		TrafficAccident = mExeSQL.getOneValue(tTrafficAccidentSql);
		// if(TrafficAccident==null||"".equals(TrafficAccident)){
		// TrafficAccident = "0";
		// }
		return TrafficAccident;
	}

	// /**
	// * 本单财务风险保额
	// * */
	// private Double getFinSumAmnt(tCustomerNo){
	//
	// }

	/**
	 * 补充告知问卷标记
	 * */
	private String getReinImpart() {
		String ReinImpart = "";
		String tReinImpartSql =
		// 09-09-27 与陈军朝确认后此处不取核保问卷 只取扫描件
		// "select decode(count(1),0,0,1) from LCQuestionnaire"
		// + " where AskContentNo='026' and proposalcontno =("
		// + " select proposalcontno from lccont where contno='"+mContNo+"'"
		// + " )";
		"select decode(count(1),0,0,1) from es_doc_main"
				+ " where subtype='UR212' and doccode ='" + mContNo + "' ";
		ReinImpart = mExeSQL.getOneValue(tReinImpartSql);
		return ReinImpart;
	}

	/**
	 * 备注栏的字数
	 * */
	private Double getRemarkCount() {
		Double RemarkCount = null;
		try {
			String tRemarkCountSql = "select nvl(length(remark),0) from lccont"
					+ " where contno='" + mContNo + "'";
			String tRemarkCount = mExeSQL.getOneValue(tRemarkCountSql);
			if (!(tRemarkCount == null || "".equals(tRemarkCount.trim()))) {
				RemarkCount = Double.valueOf(tRemarkCount);
			} else {
				RemarkCount = Double.valueOf("0");
			}
		} catch (NumberFormatException e) {
			CError.buildErr(this, "印刷号：" + mContNo + "备注栏字数取数异常！");
			e.printStackTrace();
			RemarkCount = Double.valueOf("0");
		}
		return RemarkCount;
	}

	/**
	 * 是否有陪检记录
	 * */
	private String getAccoBodyCheck() {
		String AccoBodyCheck = "";
		String tAccoBodyCheckSql = "select decode(count(1),0,0,1) from lcpenotice"
				+ " where agentname is not null and  rownum=1 and contno='"
				+ mContNo + "'" + " order by makedate";
		AccoBodyCheck = mExeSQL.getOneValue(tAccoBodyCheckSql);
		return AccoBodyCheck;
	}

	/**
	 * 体检医院是否是定点医院
	 * */
	private String getIsAppointHos() {
		String IsAppointHos = "";
		String tIsAppointHosSql = "select decode(count(1),0,0,1) from ldhospital where hosstate='0'"
				+ " and hospitcode = ( select A.x from"
				+ " ("
				+ " select hospitcode x from lcpenotice a where a.contno='"
				+ mContNo
				+ "'"
				+ " order by a.makedate desc"
				+ " )A where rownum=1" + " )";
		IsAppointHos = mExeSQL.getOneValue(tIsAppointHosSql);
		return IsAppointHos;
	}

	/**
	 * 系统抽检标记
	 * */
	private String getSpotCheckFlag() {
		String SpotCheckFlag = "";
		String tSpotCheckFlagSql = "select decode(count(1),0,0,1) from bpomissionstate"
				+ " where bussno='" + mContNo + "' and dealtype='01'";
		SpotCheckFlag = mExeSQL.getOneValue(tSpotCheckFlagSql);
		return SpotCheckFlag;
	}

	/**
	 * 生调回复人员是否与系统定义不一致
	 * */
	private String getMOpeIsNotDefined() {
		String MOpeIsNotDefined = "";
		String tMOpeIsNotDefinedSql = " select 1 from dual where"
				+ " nvl((select username from lduser where usercode =("
				+ " select A.x from ("
				+ " select replyoperator x from lcrreport where contno='"
				+ mContNo + "'" + "  order by makedate desc) A"
				+ " where rownum=1" + " )),'xx')=nvl((" + " select A.x from ("
				+ " select remark x from lcrreport where contno='" + mContNo
				+ "'" + " order by makedate desc) A where rownum=1),'xx')";
		String tMOpeIsNotDefined = mExeSQL.getOneValue(tMOpeIsNotDefinedSql);
		if (!"1".equals(tMOpeIsNotDefined)) {
			MOpeIsNotDefined = "1";
		} else {
			MOpeIsNotDefined = "0";
		}
		return MOpeIsNotDefined;
	}

	/**
	 * 累计该险种保费
	 * 
	 * @param riskcode
	 *            insurdno
	 * */
	private Double getSumThisPrem(String tRiskCode, String tInsuredNo) {
		Double SumThisPrem = null;
		try {
			String tSumThisPremSql = "select sum(prem) from lcpol where"
					+ " uwflag not in ('1','2','a') and appflag not in ('4','9')"
					+ " and insuredno='" + tInsuredNo + "'" + " and riskcode='"
					+ tRiskCode + "'";
			String tSumThisPrem = mExeSQL.getOneValue(tSumThisPremSql);
			if (!(tSumThisPrem == null || "".equals(tSumThisPrem.trim()))) {
				SumThisPrem = Double.valueOf(tSumThisPrem);
			} else {
				SumThisPrem = Double.valueOf("0");
			}
		} catch (NumberFormatException e) {
			CError.buildErr(this, "印刷号：" + mContNo + "客户：" + tInsuredNo
					+ "累计该险种保费数据有误！");
			e.printStackTrace();
			SumThisPrem = Double.valueOf("0");
		}
		return SumThisPrem;
	}

	/**
	 * 残疾事项告知
	 * */
	private String getDisabilityImpart(String tCustomerNo) {
		String DisabilityImpart = "";
		String tDisabilityImpartSql = "select decode(count(1),0,0,1) from lccustomerimpart"
				+ " where impartcode='A0508' and contno='"
				+ mContNo
				+ "' and customerno='" + tCustomerNo + "'";
		DisabilityImpart = mExeSQL.getOneValue(tDisabilityImpartSql);
		return DisabilityImpart;
	}

	/**
	 * 取保单管理机构的前*位
	 * 
	 * 
	 * 如果个人保单的管理机构为6位时默认在后面补“4”(反正系统里面不会有以两个4结尾的机构， 所以就用4补齐了-_-||)
	 * 
	 * */
	private String getManagetCom(String tManageCom, int tLength) {
		int strLen = tManageCom.length();

		StringBuffer strReturn = new StringBuffer();
		if (strLen > tLength) {
			strReturn.append(tManageCom.substring(0, tLength));
		} else {
			if (strLen == 0) {
				strReturn.append("");
			} else {
				strReturn.append(tManageCom);
			}

			for (int i = strLen; i < tLength; i++) {
				strReturn.append("4");
			}
		}
		return strReturn.toString();
	}

	/**
	 * 获得一个0~100的随机数
	 * */
	private Double getSamplingFactor() {
		Double SamplingFactor;
		Random rand = new Random();
		int tSelect = rand.nextInt(100) + 1;// 修改抽检因子为1至100的数字
		SamplingFactor = Double.valueOf(String.valueOf(tSelect));
		return SamplingFactor;
	}

	/**
	 * 保单的机构差异化等级
	 * */
	private String getContUWLevel(String tComCode) {
		String tUWLevel = "";
		String getSql = "select case othersign when 'A' then 1 when 'B' then 2 when 'C' then 3 when 'D' then 4 end"
				+ " from ldcode where codetype='station' and code='"
				+ tComCode
				+ "'";
		ExeSQL tExeSQL = new ExeSQL();
		tUWLevel = tExeSQL.getOneValue(getSql);
		return tUWLevel;
	}

	/**
	 * 获得个单进入自核的日期
	 */
	private String getUWDate(String tPrtNo) {
		String tUWDate = "";
		// String getSql =
		// "select makedate||' '||maketime  from lwmission where processid = '0000000003' and activityid = '0000001003' and missionprop1 = '"+tPrtNo+"'";
		String getSql = "select makedate||' '||maketime  from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010005') and missionprop1 = '"
				+ tPrtNo + "'";

		ExeSQL tExeSQL = new ExeSQL();
		tUWDate = tExeSQL.getOneValue(getSql);
		return tUWDate;
	}

	/**
	 * 业务员差异化等级
	 * */
	private String getAgentUWLevel(String tAgentCode) {
		String tUWLevel = "";
		String getSql = "select uwlevel from latree where agentcode='"
				+ tAgentCode + "'";
		ExeSQL tExeSQL = new ExeSQL();
		tUWLevel = tExeSQL.getOneValue(getSql);
		return tUWLevel;
	}

	/**
	 * 代理人与被保人关系
	 * */
	private String getRelToInsured() {
		String tRelToInsured = "";
		String tRelToInsuredSql = "select decode((select code from ldcode where codetype = 'relagenttoins' and codename = impartparammodle),"
				+ " '','5',(select code from ldcode where codetype = 'relagenttoins' and codename = impartparammodle))"
				+ " from lccustomerimpart where impartver = 'A03' and impartcode='A0151'"
				+ " and prtno='" + mContNo + "'";
		ExeSQL tExeSQL = new ExeSQL();
		tRelToInsured = tExeSQL.getOneValue(tRelToInsuredSql);
		if ("".equals(StrTool.cTrim(tRelToInsured))) {
			// 如果查询结果为空则认为是 6-否
			tRelToInsured = "6";
		}
		return tRelToInsured;
	}

	/**
	 * 投(被)保人联系电话与业务员联系电话是否一致 2010-3-26与陈军朝电话沟通 业务员电话取值为最新增加的告知项“业务员电话”
	 * 
	 * @return 0-否 1-是
	 * */
	private String getSamePhone(String tCustomerno, String tAddressno) {
		String tSamePhone = "0";
		String tPhone = "";
		String tMobile = "";
		String tAgentPhoneSql = "select impartparammodle from lccustomerimpart"
				+ " where impartver='A03' and impartcode='A0158' "
				+ " and prtno='" + mContNo + "'";
		ExeSQL tExeSQL = new ExeSQL();
		tPhone = tExeSQL.getOneValue(tAgentPhoneSql);
		String tSamePhoneSql = "";
		if (tPhone != null && !"".equals(tPhone)) {
			if (!"".equals(tPhone.replaceAll("[^0-9]", ""))) {
				tSamePhoneSql = " select 1 from lcaddress a where customerno='"
						+ tCustomerno + "'" + " and addressno='" + tAddressno
						+ "' and (" + " a.phone='"
						+ tPhone.replaceAll("[^0-9]", "") + "' or a.mobile='"
						+ tPhone.replaceAll("[^0-9]", "") + "')";
				tSamePhone = tExeSQL.getOneValue(tSamePhoneSql);
				if ("1".equals(tSamePhone)) {
					return "1";
				}
			}
		} else {
			tSamePhone = "0";
		}
		return tSamePhone;
	}

	/**
	 * 一年内职业代码是否一致
	 * 
	 * @return 0-否 1-是
	 * */
	private String getSameOccuCode(String tInsuredNo) {
		String tSameOccuCode = "0";
		ExeSQL tExeSQL = new ExeSQL();
		String tCurrentDate = PubFun.getCurrentDate();
		String tOneYearBefore = PubFun.calDate(tCurrentDate, -1, "Y", "");
		String tSql = "select distinct occupationcode from lcinsured where"
				+ " insuredno='" + tInsuredNo + "' "
				+ " and occupationcode is not null" + " and (makedate>='"
				+ tOneYearBefore + "' " + " and makedate<='" + tCurrentDate
				+ "')";
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(tSql);
		if (tSSRS.getMaxRow() > 1) {
			tSameOccuCode = "0";
		} else {
			tSameOccuCode = "1";
		}
		return tSameOccuCode;
	}

	/**
	 * 管理机构百团标记
	 * 
	 * @return 0-否 1-是
	 * */
	private String getManaSpecFlag(String tManageCom) {
		String tSql = "select nvl(comareatype1,0) from ldcom where "
				+ " comcode = '" + tManageCom + "'";
		ExeSQL tExeSQL = new ExeSQL();
		return tExeSQL.getOneValue(tSql);
	}

	public static void main(String[] agrs) {
		String tResult = "被保险人";
		logger.debug(tResult.replaceAll("被保人|被保险人", "投保人"));

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "86";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("MissionID", "00000000000000354833");
		tTransferData.setNameAndValue("SubMissionID", "1");
		tTransferData.setNameAndValue("ActivityID", "0000001003");
		tTransferData.setNameAndValue("ContNo", "32110000000035");

		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(tG);

		AutoUWCheckBL tAutoUWCheckBL = new AutoUWCheckBL();

		String Content = "";
		if (tAutoUWCheckBL.submitData(tVData, "") == false) {
			int n = tAutoUWCheckBL.mErrors.getErrorCount();
			logger.debug("n==" + n);
			for (int j = 0; j < n; j++) {
				logger.debug("Error: "
						+ tAutoUWCheckBL.mErrors.getError(j).errorMessage);
			}
			Content = " 自动核保失败，原因是: "
					+ tAutoUWCheckBL.mErrors.getError(0).errorMessage;
		}
	}

}
