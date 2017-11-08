package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCAppntBL;
import com.sinosoft.lis.bl.LCGetBL;
import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.db.ES_DOC_MAINDB;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LAComDB;
import com.sinosoft.lis.db.LACommisionDetailDB;
import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCPENoticeDB;
import com.sinosoft.lis.db.LCPENoticeResultDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPolOriginalDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.ES_DOC_RELATIONSchema;
import com.sinosoft.lis.schema.LACommisionDetailSchema;
import com.sinosoft.lis.schema.LCAccountSchema;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntIndSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.LACommisionDetailSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPENoticeResultSet;
import com.sinosoft.lis.vschema.LCPENoticeSet;
import com.sinosoft.lis.vschema.LCPolOriginalSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: BL层业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 * @date 2002-09-25
 */
public class ContBL {
private static Logger logger = Logger.getLogger(ContBL.class);
	private String GrpName = "";

	private String GrpNo = "";
	private MMap map = new MMap();
	private String mark = ""; // add by yaory
	/** 1为走个险逻辑，2为团险*/
	private String mDealFlag = "1"; // 程序处理逻辑标志，1为走个险逻辑，2为团险
	private String KDCheckFlag = "0";//自助卡单标记，1--自助卡单，0--正常保单
	
	/** 旧投保人客户号*/
	private String mOldAppntNo = "";

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	private LACommisionDetailSet mLACommisionDetailSet;

	private LCAccountSchema mLCAccountSchema = new LCAccountSchema();

	private LCAddressSchema mLCAddressSchema = new LCAddressSchema();
	private LCAppntBL mLCAppntBL = new LCAppntBL();

	/** 业务处理相关变量 */
	private LCContSchema mLCContSchema = new LCContSchema();

	private LCCustomerImpartParamsSet mLCCustomerImpartParamsSet;
	private LCCustomerImpartSet mLCCustomerImpartSet;
	private LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();

	private LDPersonSchema mLDPersonSchema = new LDPersonSchema();
	// private LDPersonSchema tLDPersonSchema = new LDPersonSchema();
	private LDPersonSet mLDPersonSet = new LDPersonSet();
	private LCContSchema mOldLCCont = new LCContSchema();
	/** 数据操作字符串 */
	private String mOperate;
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	private String mSavePolType = "";
	private LCContSchema nLCContSchema = new LCContSchema();
	/** 全局数据 */
	private Reflections ref = new Reflections();
	private VData rResult = new VData(); // add by yaory
	private boolean TakeBackCertifyFalg = false; // 是否回收单证标记
	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();
	private LDGrpSchema tLDGrpSchema;
	/** 需要传到关系的数据 * */

	private MMap tmap = new MMap();
	private boolean updateldperson = false;

	// @Constructor
	public ContBL() {
	}

	/**
	 * 检查保单代理人数据是否正确 如果在处理过程中出错或者数据有错误，则返回false,否则返回true
	 */
	private boolean checkAgent() {
		// 2005-12-2添加 增加 业务员作业区域控制
		// 系统能够自动校验业务员所属机构与操作员登录机构是否同属一个分公司,对于校验不通过的,系统控制不能进行后续保存等相关操作
		// tongmeng 2007-09-06 modify
		// 对于MSRS，如果是以总公司登陆的，则不做代理人管理机构和登陆机构的校验

		String tOperator = mGlobalInput.Operator; // 操作员代码
		String tOperatorCom = mGlobalInput.ManageCom; // 操作员所在机构代码
		String tOperatorFiliale = ""; // 操作员所在机构所在分公司代码,取代码前四位

		// 查询代理人的信息
		String tAgentCode = mLCContSchema.getAgentCode().trim(); // 业务员代码
		String tAgentCom = ""; // 业务员所属机构
		String tAgentFiliale = ""; // 业务员所属机构所在分公司代码,取代码前四位
		String tAgentSQL = "select agentcode,name,agentgroup,managecom  from laagent where 1=1"
				+ " and agentcode='" + "?agentcode?" + "'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tAgentSQL);
		sqlbv.put("agentcode", tAgentCode);
		ExeSQL tAgentExeSQL = new ExeSQL();
		SSRS tAgentSSRS = new SSRS();
		tAgentSSRS = tAgentExeSQL.execSQL(sqlbv);
		if (tAgentSSRS == null || tAgentSSRS.getMaxRow() == 0) {
			CError.buildErr(this, "查询业务员[" + tAgentCode + "]信息失败！");
			return false;
		}

		if (tOperatorCom != null && tOperatorCom.trim().length() == 2) {
			logger.debug("登陆管理机构为总公司，不做登陆机构校验。");
		} else {
			if (tOperatorCom != null && tOperatorCom.trim().length() >= 4) {
				tOperatorFiliale = tOperatorCom.trim().substring(0, 4); // 取代码前四位
			}

			tAgentCom = tAgentSSRS.GetText(1, 4);
			if (tAgentCom != null && !tAgentCom.equals("null")
					&& tAgentCom.trim().length() >= 4) {
				tAgentFiliale = tAgentCom.trim().substring(0, 4);
			}
			// 校验业务员所属机构与操作员登录机构是否同属一个分公司,不是则不通过，返回错误
			if (tAgentFiliale.equals("") || tOperatorFiliale.equals("")
					|| !tAgentFiliale.equals(tOperatorFiliale)) {
				String ErrInfo = "校验业务员作业区域控制错误可能原因是：1、获取业务员所属机构与操作员登录机构所在分公司代码出错;";
				ErrInfo = ErrInfo + "2、业务员所属机构与操作员登录机构是不属一个分公司，不允许保存投保单信息";
				ErrInfo = ErrInfo + " ||业务员代码[" + tAgentCode + "],所属机构代码["
						+ tAgentCom + "],所在分公司代码[" + tAgentFiliale + "]";
				ErrInfo = ErrInfo + " ||操作员代码[" + tOperator + "],登录机构代码["
						+ tOperatorCom + "],所在分公司代码[" + tOperatorFiliale + "]";
				CError.buildErr(this, ErrInfo);
				return false;
			}
		}

		// 2005-12-31添加校验业务员是否有销售资格，传入业务员代码。
		String tCalCode = "AgSale"; // 根据字串从 lmcalmode 里获取 校验的SQL字串
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("AgentCode", tAgentCode); // 校验字串所需参数
//		if (PubFun.userDefinedCheck(tCalCode, tTransferData) == false) {
//			String ErrInfo = "校验业务员销售资格失败：业务员[" + tAgentCode + "]没有销售资格。";
//			logger.debug(ErrInfo);
//			CError.buildErr(this, ErrInfo);
//			return false;
//		}
		// modify 2007-04-27 团体不校验conttype=2 为团体业务
		if (!mLCContSchema.getContType().equals("2")) {
			// 2005-10-24添加 增加 代理人校验
			// 根据传入的 合同号 查询 ljtempfee ,将获得的“代理人编码”与 传入的“代理人编码”比较
			String AgentSymbol = "false"; // 默认不通过
			String strFeeAgent = ""; // 获取ljtempfee表的代理人编码
			String strAgentCode = mLCContSchema.getAgentCode();
			String sSQL = "select distinct agentcode from ljtempfee where 1=1 "
					+ " and confdate is null and confflag='0'"
					+ " and othernotype in ('6','7') and otherno='"
					+ "?otherno?" + "' ";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(sSQL);
			sqlbv1.put("otherno", mLCContSchema.getContNo());
			ExeSQL sExeSQL = new ExeSQL();
			SSRS strSSRS = new SSRS();
			strSSRS = sExeSQL.execSQL(sqlbv1);
			int m = strSSRS.getMaxRow();
			if (m == 0) { // 暂交费记录表为零则是没有暂交费，不用校验
				AgentSymbol = "true";
				logger.debug("没有暂交费记录信息，无需代理人校验");
			} else {
				// 有暂交费，则必须校验代理人，只要有一个相同，则校验通过
				for (int i = 1; i <= m; i++) {
					String stra = strSSRS.GetText(i, 1);
					if (strFeeAgent == null || strFeeAgent.equals("")) {
						strFeeAgent = stra;
					} else {
						strFeeAgent = strFeeAgent + ";" + stra;
					}
					if (stra.equals(strAgentCode)) {
						AgentSymbol = "true";
						// break;
					}
				}
			}
			// 判断是否校验成功，若果失败则返回失败信息
			if (AgentSymbol.equals("false")) {
				logger.debug("代理人校验失败，此处传入的代理人代码与暂交费记录的代理人代码不符!");
				CError.buildErr(this, "代理人校验失败!此处传入的代理人代码[" + strAgentCode
						+ "]与暂交费记录的代理人代码[" + strFeeAgent + "]不符");
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查修改的合同信息是否符合业务规则。 如果在处理过程中出错或者数据有错误，则返回false,否则返回true ---yeshu,20071220
	 */
	private boolean checkCont() {
		if (mOperate.equals("UPDATE||CONT")) {
			logger.debug("cont is===============>"
					+ mLCContSchema.getContNo());
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(mLCContSchema.getContNo());
			if (tLCContDB.getInfo() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCContDB.mErrors);
				return false;
			}
			mOldLCCont.setSchema(tLCContDB);
			// 若修改了合同投保日期：
			// 1.校验投保日期是否超出已存险种的在售期间；
			// 2.取界面传入的投保人出生日期（二者可能同时修改）及数据库中的被保人出生日期重算投被保人年龄，校验是否超过已存险种的允许范围。
			if (!mOldLCCont.getPolApplyDate().equals(
					mLCContSchema.getPolApplyDate())) {
				int newAppntAge = PubFun.calInterval(mLCAppntBL
						.getAppntBirthday(), mLCContSchema.getPolApplyDate(),
						"Y");
				logger.debug("newAppntAge=" + newAppntAge);
				if (newAppntAge < 18) {
					CError.buildErr(this, "合同投保日投保人未满18周岁！");
					return false;
				}
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql("select a.insuredbirthday,(case when b.startdate is null then to_date('1900-01-01','yyyy-MM-dd') else b.startdate end),(case when b.enddate is null then to_date('3000-01-01','yyyy-MM-dd') else b.enddate end),(case when b.minappntage is null then 0 else b.minappntage end),(case when b.maxappntage is null then 105 else b.maxappntage end),(case when b.mininsuredage is null then 0 else b.mininsuredage end),(case when b.maxinsuredage is null then 105 else b.maxinsuredage end),b.riskname from lcpol a,lmriskapp b where a.riskcode=b.riskcode and a.contno='"
						+ "?contno?" + "'");
				sqlbv2.put("contno", mLCContSchema.getContNo());
				SSRS tSSRS = new ExeSQL().execSQL(sqlbv2);
				if (tSSRS != null && tSSRS.MaxRow >= 1) {
					for (int i = 1; i <= tSSRS.MaxRow; i++) {
						// 将来可能出现多被保人的情况，所以在循环内计算被保人投保年龄。
						int newInsuredAge = PubFun.calInterval(tSSRS.GetText(i,
								1), mLCContSchema.getPolApplyDate(), "Y");
						logger.debug("newInsuredAge=" + newInsuredAge);
						if (tSSRS.GetText(i, 2).compareTo(
								mLCContSchema.getPolApplyDate()) > 0
								|| tSSRS.GetText(i, 3).compareTo(
										mLCContSchema.getPolApplyDate()) < 0) {
							CError.buildErr(this, "投保日期超出险种"
									+ tSSRS.GetText(i, 8) + "的在售期间！");
							return false;
						}
						logger.debug("tSSRS.GetText(i, 4)="
								+ tSSRS.GetText(i, 4) + ";tSSRS.GetText(i, 5)="
								+ tSSRS.GetText(i, 5));
						if (Integer.parseInt(tSSRS.GetText(i, 4)) > newAppntAge
								|| Integer.parseInt(tSSRS.GetText(i, 5)) < newAppntAge) {
							CError.buildErr(this, "投保人年龄超出险种"
									+ tSSRS.GetText(i, 8) + "允许的范围！");
							return false;
						}
						if (Integer.parseInt(tSSRS.GetText(i, 6)) > newInsuredAge
								|| Integer.parseInt(tSSRS.GetText(i, 7)) < newInsuredAge) {
							CError.buildErr(this, "被保人年龄超出险种"
									+ tSSRS.GetText(i, 8) + "允许的范围！");
							return false;
						}
					}
				}
			}
			// 若修改了合同的管理机构，执行相关校验。
			if (!mOldLCCont.getManageCom().equals(mLCContSchema.getManageCom())) {
				if (!this.checkInputManageCom()) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 校验传入的数据
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean checkData() {
		if (!mOperate.equals("DELETE||CONT")) {
			if (mLCContSchema.getContType() == null) { // 2-集体总单,1-个人总投保单)
				mLCContSchema.setContType("1"); // 缺省设为个人单
			}

			// 如果保单类型为集体总单且不是卡单则走团单处理逻辑
			// 否则为个单逻辑
			if ("2".equals(mLCContSchema.getContType()) && !"card".equals(mark)) {
				this.mDealFlag = "2";
			} else {
				this.mDealFlag = "1";
			}

			if (mLCContSchema.getPolType() == null) { // 保单类型标记0--个人单，1--无名单，2
				// ---公共帐户)
				mLCContSchema.setPolType("0"); // 缺省设为个人单
			}
			if (mLCContSchema.getAppFlag() == null) { // 签单标记为空时，补为0
				mLCContSchema.setAppFlag("0");
			}

			if (mLCContSchema.getCardFlag() == null) { // 卡单标记为空时，补为0
				mLCContSchema.setCardFlag("0");
			}
			// start.校验合同信息。---yeshu,20071220
			if (this.checkCont() == false) {
				return false;
			}
			// end.---yeshu,20071220
			// 代理人检验
			if (this.checkAgent() == false) {
				return false;
			}
			if (this.checkLCAppnt() == false) {
				return false;
			}
			if (this.checkLCAddress() == false) {
				return false;
			}
			// 录入的职业编码检验
			if (this.checkInputOccupationCode() == false) {
				return false;
			}
			// 校验非空值
			if (mLCContSchema.getPrtNo() == null
					|| mLCContSchema.getPrtNo().trim().equals("")) {
				// @@错误处理
				CError.buildErr(this, "请录入投保单号码!");
				return false;
			}
			// 校验代理人编码并置上代理人组别
			if (mLCContSchema.getAgentCode() == null
					|| mLCContSchema.getAgentCode().equals("")) {
				// @@错误处理
				CError.buildErr(this, "请您确认有：代理人编码!");
				return false;
			} else {
				LAAgentDB tLAAgentDB = new LAAgentDB();
				tLAAgentDB.setAgentCode(mLCContSchema.getAgentCode());
				if (tLAAgentDB.getInfo() == false) {
					// @@错误处理
					CError.buildErr(this, "请您确认：代理人编码没有输入错误!");
					return false;
				}
				if (tLAAgentDB.getManageCom() == null) {
					// @@错误处理
					CError.buildErr(this, "代理人编码对应数据库中的管理机构为空！");
					return false;
				}
				// 根据业务员的销售渠道决定保单的销售渠道
				if (tLAAgentDB.getBranchType() == null
						|| tLAAgentDB.getBranchType().equals("")) {
					// @@错误处理
					CError.buildErr(this, "业务员对应的销售渠道为空！");
					return false;
				}
				
				String tSaleChnl = mLCContSchema.getSaleChnl();
				if(tSaleChnl!=null&&!tSaleChnl.equals("03")&&!tSaleChnl.equals("05"))
				{
					if (!tLAAgentDB.getManageCom().equals(
							mLCContSchema.getManageCom())) {
						// @@错误处理
						CError.buildErr(this, "您录入的管理机构和数据库中代理人编码对应的管理机构不符合！");
						return false;
					}
				}
				/*if (!this.checkInputManageCom()) {
					return false;
				}*/
				mLCContSchema.setAgentGroup(tLAAgentDB.getAgentGroup());
				

			}

			// 校验多业务员的佣金比例
			for (int i = 0; mLACommisionDetailSet != null
					&& i < this.mLACommisionDetailSet.size(); i++) {
				if (mLACommisionDetailSet.size() == 1) {
					if (mLACommisionDetailSet.get(i + 1).getBusiRate() != 1) {
						// @@错误处理
						CError.buildErr(this, "业务员佣金比例分配错误!");
						return false;

					}
				}
				if (mLACommisionDetailSet.size() > 1) {
					if (this.mLACommisionDetailSet.get(i + 1).getBusiRate() <= 0
							|| this.mLACommisionDetailSet.get(i + 1)
									.getBusiRate() >= 1) {
						// @@错误处理
						CError.buildErr(this, "业务员佣金比例分配错误!");
						return false;

					}
				}
			}

		}

		if (mOperate.equals("INSERT||CONT")) {

			String strSQL = "select count(*) from lccont where prtno='"
					+ "?prtno?" + "'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(strSQL);
			sqlbv3.put("prtno", mLCContSchema.getPrtNo());
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = tExeSQL.execSQL(sqlbv3);
			String strCount = tSSRS.GetText(1, 1);
			int SumCount = Integer.parseInt(strCount);
			if (SumCount > 0) {
				// @@错误处理
				CError.buildErr(this, "同一投保单号只能录入一个合同!");
				return false;
			}

		}
		if (mOperate.equals("DELETE||CONT")) {

			String strSQL = "select count(*) from LCInsured where contno='"
					+ "?contno?" + "'";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(strSQL);
			sqlbv4.put("contno", mLCContSchema.getPrtNo());
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = tExeSQL.execSQL(sqlbv4);
			String strCount = tSSRS.GetText(1, 1);
			int SumCount = Integer.parseInt(strCount);
			if (SumCount > 0) {
				// @@错误处理
				CError.buildErr(this, "该合同下还有被保险人未删除，不能删除合同信息!");
				return false;
			}

		}
		/*
		 * 生成号码 if( mLCGrpPolSchema.getGrpNo() == null ||
		 * mLCGrpPolSchema.getGrpNo().trim().equals( "" )) { String tLimit =
		 * "SN"; String tNo = PubFun1.CreateMaxNo( "GrpNo", tLimit );
		 * mLCGrpPolSchema.setGrpNo( tNo ); }
		 */
		if (mLCContSchema.getManageCom() == null
				|| mLCContSchema.getManageCom().trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "请录入管理机构!");
			return false;
		}

		return true;
	}

	/**
	 * 检查操作员录入的管理机构是否正确 如果在处理过程中出错或者数据有错误，则返回false,否则返回true
	 * 放开此方法并增加校验内容。---yeshu,20071220
	 */
	private boolean checkInputManageCom() {
		// 1.校验录入机构是否存在。
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mLCContSchema.getManageCom().trim());
		if (!tLDComDB.getInfo()) {
			CError.buildErr(this, "录入的管理机构不存在！");
			return false;
		}
		// 2.校验录入机构与代理人信息是否匹配。
		String tSaleChnl = mLCContSchema.getSaleChnl();
		if(tSaleChnl!=null&&!tSaleChnl.equals("03")&&!tSaleChnl.equals("05"))
		{
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mLCContSchema.getAgentCode());
			tLAAgentDB.setManageCom(mLCContSchema.getManageCom());
			if (tLAAgentDB.query().size() <= 0) {
				CError.buildErr(this, "录入的管理机构与代理人所属机构不符！");
				return false;
			}
		}
		/*//修改为与异常件录入校验一致 2009-9-1 modified by ln 		
		String tSaleChnl = mLCContSchema.getSaleChnl();
		if(tSaleChnl!=null&&!tSaleChnl.equals("03")&&!tSaleChnl.equals("05"))
		{
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mLCContSchema.getAgentCode());
			if (tLAAgentDB.getInfo() == false) {
				// @@错误处理
				CError.buildErr(this, "请您确认：代理人编码没有输入错误!");
				return false;
			}
			if (tLAAgentDB.getManageCom() == null) {
				// @@错误处理
				CError.buildErr(this, "代理人编码对应数据库中的管理机构为空！");
				return false;
			}
			if (!tLAAgentDB.getManageCom().equals(
					mLCContSchema.getManageCom())) {
				// @@错误处理
				CError.buildErr(this, "您录入的管理机构和数据库中代理人编码对应的管理机构不符合！");
				return false;
			}
		}
		if (tSaleChnl != null
				&& ("03".equals(tSaleChnl)
						||"05".equals(tSaleChnl))) {
			if ((mLCContSchema.getAgentCom() == null || ""
					.equals(mLCContSchema.getAgentCom()))) {
				CError.buildErr(this, "销售渠道为银行代理和专业代理时必须输入代理机构！");
				return false;
			}
			else
			{
				LAComDB tLAComDB = new LAComDB();
				tLAComDB.setAgentCom(mLCContSchema.getAgentCom());
				if (!tLAComDB.getInfo()) {
					CError.buildErr(this, "代理机构编码错误！");
					return false;
				}
				//如果是联办代理的代理机构,保单的管理机构设置为扫描机构+2位0
				if(tLAComDB.getBranchType()!=null && tLAComDB.getBranchType().equals("6"))
				{
					String tSQL = "select Managecom from ES_Doc_Main where DocCode='"+mLCContSchema.getPrtNo()+"' and subtype='UA001' and rownum=1";
					ExeSQL tExeSQL = new ExeSQL();
					String tManageCom = tExeSQL.getOneValue(tSQL);//扫描机构
					if (tManageCom != null && !"".equals(tManageCom) && !mLCContSchema.getManageCom().equals(PubFun.RCh(tManageCom,"0",8))) {
						CError.buildErr(this, "录入的管理机构与扫描机构不符！");
						return false;
					}				        
				}
				//中介投保单的管理机构取代理机构的管理机构
				else if (!mLCContSchema.getManageCom().equals(tLAComDB.getManageCom())) {
						CError.buildErr(this, "录入的管理机构与代理机构所属机构不符！");
						return false;
					}
			}				
		}
		//end*/
		// 3.存在暂交费时校验是否与暂交费收取机构匹配。（待定）

		// 4.校验录入机构是否有权销售已存的险种。(目前MS可能没有这条规则)

		return true;
	}

	/**
	 * 检查操作员录入的职业编码是否正确 如果在处理过程中出错或者数据有错误，则返回false,否则返回true
	 */
	private boolean checkInputOccupationCode() {
		logger.debug("录入的职业编码是===" + mLCAppntBL.getOccupationCode());
		if ((mLCAppntBL.getOccupationCode() == null)
				|| (mLCAppntBL.getOccupationCode().equals(""))
				|| (mLCAppntBL.getOccupationCode() == "")
				|| ("".equals(mLCAppntBL.getOccupationCode()))) {
			logger.debug("^^^^^^^^^^^^^^^^职业编码不是必录项^^^^^^^^^^^^^^^^^^");
			return true;
		} else {
			String chkSQL = "select 'X' from LDOccupation where 1=1 and worktype = 'GR' and  "
					+ " OccupationCode ='"
					+ "?OccupationCode?" + "'";
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(chkSQL);
			sqlbv5.put("OccupationCode", mLCAppntBL.getOccupationCode().trim());
			
			logger.debug("校验录入的职业编码是否正确:::::" + chkSQL);
			ExeSQL chkocExeSQL = new ExeSQL();
			SSRS chkocSSRS = new SSRS();
			chkocSSRS = chkocExeSQL.execSQL(sqlbv5);
			if (chkocSSRS.getMaxRow() == 0 || chkocSSRS == null) {
				CError.buildErr(this, "录入的职业编码不正确！");
				return false;
			}
		}
		return true;
	}

	// ------------------------add end --------------//

	private boolean checkLCAddress() {

		if (mLCAddressSchema != null) {
			if (mLCAddressSchema.getPostalAddress() != null) { // 去空格
				mLCAddressSchema.setPostalAddress(mLCAddressSchema
						.getPostalAddress().trim());
				if (mLCAddressSchema.getZipCode() != null) {
					mLCAddressSchema.setZipCode(mLCAddressSchema.getZipCode()
							.trim());
				}
				if (mLCAddressSchema.getPhone() != null) {
					mLCAddressSchema.setPhone(mLCAddressSchema.getPhone()
							.trim());
				}

			}
			if (mLCAddressSchema.getAddressNo() != 0
					&& mLCAppntBL.getAppntNo() != null
					&& !mLCAppntBL.getAppntNo().equals("")) {
				// 如果有地址号
				if (mLCAddressSchema.getAddressNo() != 0) {
					LCAddressDB tLCAddressDB = new LCAddressDB();

					tLCAddressDB.setAddressNo(mLCAddressSchema.getAddressNo());
					tLCAddressDB
							.setCustomerNo(mLCAddressSchema.getCustomerNo());
					if (tLCAddressDB.getInfo() == false) {
						/*
						 * CError tError = new CError(); tError.moduleName =
						 * "ContBL"; tError.functionName = "checkAddress";
						 * tError.errorMessage = "投保人地址代码" +
						 * mLCAddressSchema.getAddressNo() + "数据库查询失败!";
						 * this.mErrors.addOneError(tError);
						 * 
						 * return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(
							mLCAddressSchema.getCustomerNo(), tLCAddressDB
									.getCustomerNo())) {
						/*
						 * String cuErrInfo = "您输入的投保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户号(" +
						 * tLCAddressDB.getCustomerNo() + ")与录入的客户号(" +
						 * mLCAddressSchema.getCustomerNo() + ")不匹配！"; cuErrInfo
						 * = cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);

					}
					if (!StrTool.compareString(""
							+ mLCAddressSchema.getAddressNo(), ""
							+ tLCAddressDB.getAddressNo())) {
						/*
						 * String cuErrInfo = "您输入的投保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户地址代码(" +
						 * tLCAddressDB.getAddressNo() + ")与录入的客户地址代码(" +
						 * mLCAddressSchema.getAddressNo() + ")不匹配！"; cuErrInfo
						 * = cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);

					}
					if (!StrTool.compareString(mLCAddressSchema
							.getPostalAddress(), tLCAddressDB
							.getPostalAddress())) {
						/*
						 * String cuErrInfo = "您输入的投保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户通讯地址(" +
						 * tLCAddressDB.getPostalAddress() + ")与录入的客户通讯地址(" +
						 * mLCAddressSchema.getPostalAddress() + ")不匹配！";
						 * cuErrInfo = cuErrInfo + "请去掉地址代码，重新操作！"; CError
						 * tError = new CError(); tError.moduleName =
						 * "ProposalBL"; tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);

					}
					if (!StrTool.compareString(mLCAddressSchema.getZipCode(),
							tLCAddressDB.getZipCode())) {
						/*
						 * String cuErrInfo = "您输入的投保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户邮政编码(" +
						 * tLCAddressDB.getZipCode() + ")与录入的客户邮政编码(" +
						 * mLCAddressSchema.getZipCode() + ")不匹配！"; cuErrInfo =
						 * cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(mLCAddressSchema.getPhone(),
							tLCAddressDB.getPhone())) {
						/*
						 * String cuErrInfo = "您输入的投保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户通讯电话(" +
						 * tLCAddressDB.getPhone() + ")与录入的客户通讯电话(" +
						 * mLCAddressSchema.getPhone() + ")不匹配！"; cuErrInfo =
						 * cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);

					}
					if (!StrTool.compareString(mLCAddressSchema.getFax(),
							tLCAddressDB.getFax())) {
						/*
						 * String cuErrInfo = "您输入的投保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户通讯传真(" + tLCAddressDB.getFax()
						 * + ")与录入的客户通讯传真(" + mLCAddressSchema.getFax() +
						 * ")不匹配！"; cuErrInfo = cuErrInfo + "请去掉地址代码，重新操作！";
						 * CError tError = new CError(); tError.moduleName =
						 * "ProposalBL"; tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(mLCAddressSchema
							.getHomeAddress(), tLCAddressDB.getHomeAddress())) {
						/*
						 * String cuErrInfo = "您输入的投保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户家庭地址(" +
						 * tLCAddressDB.getHomeAddress() + ")与录入的客户家庭地址(" +
						 * mLCAddressSchema.getHomeAddress() + ")不匹配！";
						 * cuErrInfo = cuErrInfo + "请去掉地址代码，重新操作！"; CError
						 * tError = new CError(); tError.moduleName =
						 * "ProposalBL"; tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(mLCAddressSchema
							.getHomeZipCode(), tLCAddressDB.getHomeZipCode())) {
						/*
						 * String cuErrInfo = "您输入的投保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户家庭邮编(" +
						 * tLCAddressDB.getHomeZipCode() + ")与录入的客户家庭邮编(" +
						 * mLCAddressSchema.getHomeZipCode() + ")不匹配！";
						 * cuErrInfo = cuErrInfo + "请去掉地址代码，重新操作！"; CError
						 * tError = new CError(); tError.moduleName =
						 * "ProposalBL"; tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);

					}
					if (!StrTool.compareString(mLCAddressSchema.getHomePhone(),
							tLCAddressDB.getHomePhone())) {
						/*
						 * String cuErrInfo = "您输入的投保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户家庭电话(" +
						 * tLCAddressDB.getHomePhone() + ")与录入的客户家庭电话(" +
						 * mLCAddressSchema.getHomePhone() + ")不匹配！"; cuErrInfo
						 * = cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);

					}
					if (!StrTool.compareString(mLCAddressSchema.getHomeFax(),
							tLCAddressDB.getHomeFax())) {
						/*
						 * String cuErrInfo = "您输入的投保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户家庭传真(" +
						 * tLCAddressDB.getHomeFax() + ")与录入的客户家庭传真(" +
						 * mLCAddressSchema.getHomeFax() + ")不匹配！"; cuErrInfo =
						 * cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);

					}
					if (!StrTool.compareString(mLCAddressSchema
							.getCompanyAddress(), tLCAddressDB
							.getCompanyAddress())) {
						/*
						 * String cuErrInfo = "您输入的投保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户单位地址(" +
						 * tLCAddressDB.getPostalAddress() + ")与录入的客户单位地址(" +
						 * mLCAddressSchema.getPostalAddress() + ")不匹配！";
						 * cuErrInfo = cuErrInfo + "请去掉地址代码，重新操作！"; CError
						 * tError = new CError(); tError.moduleName =
						 * "ProposalBL"; tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);

					}
					if (!StrTool.compareString(mLCAddressSchema
							.getCompanyZipCode(), tLCAddressDB
							.getCompanyZipCode())) {
						/*
						 * String cuErrInfo = "您输入的投保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户单位邮编(" +
						 * tLCAddressDB.getCompanyZipCode() + ")与录入的客户单位邮编(" +
						 * mLCAddressSchema.getCompanyZipCode() + ")不匹配！";
						 * cuErrInfo = cuErrInfo + "请去掉地址代码，重新操作！"; CError
						 * tError = new CError(); tError.moduleName =
						 * "ProposalBL"; tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(mLCAddressSchema
							.getCompanyPhone(), tLCAddressDB.getCompanyPhone())) {
						/*
						 * String cuErrInfo = "您输入的投保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户单位电话(" +
						 * tLCAddressDB.getCompanyPhone() + ")与录入的客户单位电话(" +
						 * mLCAddressSchema.getCompanyPhone() + ")不匹配！";
						 * cuErrInfo = cuErrInfo + "请去掉地址代码，重新操作！"; CError
						 * tError = new CError(); tError.moduleName =
						 * "ProposalBL"; tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(
							mLCAddressSchema.getCompanyFax(), tLCAddressDB
									.getCompanyFax())) {
						/*
						 * String cuErrInfo = "您输入的投保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户单位传真(" +
						 * tLCAddressDB.getCompanyFax() + ")与录入的客户单位传真(" +
						 * mLCAddressSchema.getCompanyFax() + ")不匹配！"; cuErrInfo
						 * = cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(mLCAddressSchema.getMobile(),
							tLCAddressDB.getMobile())) {
						/*
						 * String cuErrInfo = "您输入的投保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户手机(" +
						 * tLCAddressDB.getMobile() + ")与录入的客户手机(" +
						 * mLCAddressSchema.getMobile() + ")不匹配！"; cuErrInfo =
						 * cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(mLCAddressSchema.getMobileChs(),
							tLCAddressDB.getMobileChs())) {
						/*
						 * String cuErrInfo = "您输入的投保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户手机中文标示(" +
						 * tLCAddressDB.getMobileChs() + ")与录入的客户手机中文标示(" +
						 * mLCAddressSchema.getMobileChs() + ")不匹配！"; cuErrInfo
						 * = cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(mLCAddressSchema.getEMail(),
							tLCAddressDB.getEMail())) {
						/*
						 * String cuErrInfo = "您输入的投保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户e_mail(" +
						 * tLCAddressDB.getEMail() + ")与录入的客户e_mail(" +
						 * mLCAddressSchema.getEMail() + ")不匹配！"; cuErrInfo =
						 * cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(mLCAddressSchema.getBP(),
							tLCAddressDB.getBP())) {
						/*
						 * String cuErrInfo = "您输入的投保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户传呼(" + tLCAddressDB.getBP() +
						 * ")与录入的客户传呼(" + mLCAddressSchema.getBP() + ")不匹配！";
						 * cuErrInfo = cuErrInfo + "请去掉地址代码，重新操作！"; CError
						 * tError = new CError(); tError.moduleName =
						 * "ProposalBL"; tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(mLCAddressSchema.getMobile2(),
							tLCAddressDB.getMobile2())) {
						/*
						 * String cuErrInfo = "您输入的投保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户手机2(" +
						 * tLCAddressDB.getMobile2() + ")与录入的客户手机2(" +
						 * mLCAddressSchema.getMobile2() + ")不匹配！"; cuErrInfo =
						 * cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(
							mLCAddressSchema.getMobileChs2(), tLCAddressDB
									.getMobileChs2())) {
						/*
						 * String cuErrInfo = "您输入的投保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户手机中文标示2(" +
						 * tLCAddressDB.getMobileChs2() + ")与录入的客户手机中文标示2(" +
						 * mLCAddressSchema.getMobileChs2() + ")不匹配！"; cuErrInfo
						 * = cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(mLCAddressSchema.getEMail2(),
							tLCAddressDB.getEMail2())) {
						/*
						 * String cuErrInfo = "您输入的投保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户e_mail2(" +
						 * tLCAddressDB.getEMail2() + ")与录入的客户e_mail2(" +
						 * mLCAddressSchema.getEMail2() + ")不匹配！"; cuErrInfo =
						 * cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(mLCAddressSchema.getBP2(),
							tLCAddressDB.getBP2())) {
						/*
						 * String cuErrInfo = "您输入的投保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户传呼2(" + tLCAddressDB.getBP2()
						 * + ")与录入的客户传呼2(" + mLCAddressSchema.getBP2() +
						 * ")不匹配！"; cuErrInfo = cuErrInfo + "请去掉地址代码，重新操作！";
						 * CError tError = new CError(); tError.moduleName =
						 * "ProposalBL"; tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(mLCAddressSchema.getGrpName(),
							tLCAddressDB.getGrpName())) {
						/*
						 * String cuErrInfo = "您输入的投保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户单位名称(" +
						 * tLCAddressDB.getGrpName() + ")与录入的客户单位名称(" +
						 * mLCAddressSchema.getGrpName() + ")不匹配！"; cuErrInfo =
						 * cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}

				}
			} else {
				mLCAddressSchema.setAddressNo(0);
			}

		}

		return true;
	}

	/**
	 * 检查投保人数据是否正确 如果在处理过程中出错或者数据有错误，则返回false,否则返回true
	 */
	private boolean checkLCAppnt() {
		// 如果是无名单或者公共帐户的个人，不校验返回
		if (mLCContSchema.getPolType().equals("1")
				|| mLCContSchema.getPolType().equals("2")) {
			return true;
		}

		// 投保人-个人客户--如果是集体下个人，该投保人为空,所以个人时校验个人投保人
		if (mDealFlag.equals("1") && mLCAppntBL != null) {
			// 去空格
			if (mLCAppntBL.getAppntName() != null) {
				mLCAppntBL.setAppntName(mLCAppntBL.getAppntName().trim());
			}
			if (mLCAppntBL.getAppntSex() != null) {
				mLCAppntBL.setAppntSex(mLCAppntBL.getAppntSex().trim());
			}
			if (mLCAppntBL.getIDNo() != null) {
				mLCAppntBL.setIDNo(mLCAppntBL.getIDNo().trim());
			}
			if (mLCAppntBL.getIDType() != null) {
				mLCAppntBL.setIDType(mLCAppntBL.getIDType().trim());
			}
			if (mLCAppntBL.getAppntBirthday() != null) {
				mLCAppntBL.setAppntBirthday(mLCAppntBL.getAppntBirthday()
						.trim());
			}
			// 如果客户号存在，那么就利用客户号查询数据，将查询出的客户信息与录入客户信息比较
			// 如皋重要信息（姓名、证件类型、证件号码、出生日期、性别）有任意一项不同
			// 去掉客户号码以及地址号码，且客户号需要重新生成。
			 if (mLCAppntBL.getAppntNo() != null)
			 {
			 if (!mLCAppntBL.getAppntNo().equals(""))
			 {
			 LDPersonDB tLDPersonDB = new LDPersonDB();
			 tLDPersonDB.setCustomerNo(mLCAppntBL.getAppntNo());
			 if (tLDPersonDB.getInfo() == false)
			 {
			 CError tError = new CError();
			 tError.moduleName = "ProposalBL";
			 tError.functionName = "checkPerson";
			 tError.errorMessage = "数据库查询投保人客户号[" +
			 mLCAppntBL.getAppntNo() + "]失败!";
			 this.mErrors.addOneError(tError);
			 return false;
			 }
			 //以下准备比较 客户重要信息（姓名、证件类型、证件号码、出生日期、性别）
			 //String cuErrInfo="";//保存比较客户时的错误信息
			 //tongmeng 2007-12-05 modify
			 //去掉地址号码录入
			 if (mLCAppntBL.getAppntName() != null)
			 {
			 String Name = StrTool.GBKToUnicode(tLDPersonDB.getName().
			 trim());
			 String NewName = StrTool.GBKToUnicode(mLCAppntBL.
			 getAppntName().trim());
			 if (!Name.equals(NewName))
			 { //保存比较客户时的错误信息
			 String cuErrInfo = "您输入的投保人客户号[" +
			 mLCAppntBL.getAppntNo() + "]：";
			 cuErrInfo = cuErrInfo + "对应在数据库中的客户姓名(" + Name +
			 ")"
			 + "与您录入的客户姓名(" + NewName + ")不匹配！";
			 cuErrInfo = cuErrInfo + "请去掉客户号码，重新操作！";
			 CError tError = new CError();
			 tError.moduleName = "ProposalBL";
			 tError.functionName = "checkPerson";
			 tError.errorMessage = cuErrInfo;
			 this.mErrors.addOneError(tError);
			 return false;
			 }
			 }
			 //比较投保人客户性别
			 if (mLCAppntBL.getAppntSex() != null)
			 {
			 if (!tLDPersonDB.getSex().equals(mLCAppntBL.getAppntSex()))
			 {
			 String cuErrInfo = "您输入的投保人客户号[" +
			 mLCAppntBL.getAppntNo() + "]：";
			 cuErrInfo = cuErrInfo + "对应在数据库中的客户性别(" +
			 tLDPersonDB.getSex()
			 + ")与录入的客户性别(" + mLCAppntBL.getAppntSex() +
			 ")不匹配！";
			 cuErrInfo = cuErrInfo + "请去掉客户号码，重新操作！";
			 CError tError = new CError();
			 tError.moduleName = "ProposalBL";
			 tError.functionName = "checkPerson";
			 tError.errorMessage = cuErrInfo;
			 this.mErrors.addOneError(tError);
			 return false;
			 }
			 }
			 //比较投保人客户出生日期
			 if (mLCAppntBL.getAppntBirthday() != null)
			 {
			 if (!tLDPersonDB.getBirthday().equals(mLCAppntBL.
			 getAppntBirthday()))
			 {
			 String cuErrInfo = "您输入的投保人客户号[" +
			 mLCAppntBL.getAppntNo() + "]：";
			 cuErrInfo = cuErrInfo + "对应在数据库中的客户出生日期(" +
			 tLDPersonDB.getBirthday()
			 + ")与您录入的客户出生日期(" +
			 mLCAppntBL.getAppntBirthday() + ")不匹配！";
			 cuErrInfo = cuErrInfo + "请去掉客户号码，重新操作！";
			 CError tError = new CError();
			 tError.moduleName = "ProposalBL";
			 tError.functionName = "checkPerson";
			 tError.errorMessage = cuErrInfo;
			 this.mErrors.addOneError(tError);
			 return false;
			 }
			 }
			 //比较投保人客户证件类型
			 if (mLCAppntBL.getIDType() != null)
			 {
			 if (!tLDPersonDB.getIDType().equals(mLCAppntBL.
			 getIDType()))
			 {
			 String cuErrInfo = "您输入的投保人客户号[" +
			 mLCAppntBL.getAppntNo() + "]：";
			 cuErrInfo = cuErrInfo + "对应在数据库中的客户证件类型(" +
			 tLDPersonDB.getIDType()
			 + ")与录入的客户证件类型(" + mLCAppntBL.getIDType() +
			 ")不匹配！";
			 cuErrInfo = cuErrInfo + "请去掉客户号码，重新操作！";
			 CError tError = new CError();
			 tError.moduleName = "ProposalBL";
			 tError.functionName = "checkPerson";
			 tError.errorMessage = cuErrInfo;
			 this.mErrors.addOneError(tError);
			 return false;
			 }
			 }
			 //比较投保人客户证件号码
			 if (mLCAppntBL.getIDNo() != null)
			 {
			 ExeSQL tExeSQL = new ExeSQL();
			 SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			 sqlbv.sql("select ChgIDNo('" + "?idno?" +
					 "','" + "?idtype?" +
					 "') from dual");
			 sqlbv.put("idno", tLDPersonDB.getIDNo());
			 sqlbv.put("idtype", tLDPersonDB.getIDType());
			 String OriginIDNo = tExeSQL.getOneValue(sqlbv);
			 SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			 sqlbv1.sql("select ChgIDNo('" + "?idno?" +
					 "','" + "?idtype?" + "') from dual");
			 sqlbv1.put("idno", mLCAppntBL.getIDNo());
			 sqlbv1.put("idtype", mLCAppntBL.getIDType());
			 String InputIDNo = tExeSQL.getOneValue(sqlbv1);
			 //if (!tLDPersonDB.getIDNo().equals(mLCAppntBL.getIDNo()))
			 if (!OriginIDNo.equals(InputIDNo))
			 {
			 String cuErrInfo = "您输入的投保人客户号[" +
			 mLCAppntBL.getAppntNo() + "]：";
			 cuErrInfo = cuErrInfo + "对应在数据库中的客户证件号码(" +
			 tLDPersonDB.getIDNo()
			 + ")与录入的客户证件号码(" + mLCAppntBL.getIDNo() +
			 ")不匹配！";
			 cuErrInfo = cuErrInfo + "请去掉客户号码，重新操作！";
			 CError tError = new CError();
			 tError.moduleName = "ProposalBL";
			 tError.functionName = "checkPerson";
			 tError.errorMessage = cuErrInfo;
			 this.mErrors.addOneError(tError);
			 return false;
			 }
			 }
			 //
			 }
			 ////如果客户号不存在，则查找客户号
			 /*
			 else
			 {
			 if ((mLCAppntBL.getAppntName() != null) &&
			 (mLCAppntBL.getAppntSex() != null)
			 && (mLCAppntBL.getIDNo() != null))
			 {
			 LDPersonDB tLDPersonDB = new LDPersonDB();
			 tLDPersonDB.setName(mLCAppntBL.getAppntName());
			 tLDPersonDB.setSex(mLCAppntBL.getAppntSex());
			 tLDPersonDB.setBirthday(mLCAppntBL.getAppntBirthday());
			 tLDPersonDB.setIDType(mLCAppntBL.getIDType());
			 tLDPersonDB.setIDNo(mLCAppntBL.getIDNo());
			 LDPersonSet tLDPersonSet = tLDPersonDB.query();
			 if (tLDPersonSet != null && tLDPersonSet.size() > 0)
			 {
			 mLCAppntBL.setAppntNo(tLDPersonSet.get(1).
			 getCustomerNo());
			 }
			 }
			 }*/
			 //
			 }
		}
		return true;
	}

	/**
	 * 校验个人投保单的客户
	 * 
	 * @return
	 */
	private boolean checkLDPerson() {
		// 如果是无名单或者公共帐户的个人，不校验返回
		if (mLCContSchema.getPolType().equals("1")
				|| mLCContSchema.getPolType().equals("2")) {
			return true;
		}

		// 投保人-个人客户--如果是集体下个人，该投保人为空,所以个人时校验个人投保人
		if (mDealFlag.equals("1")) {
			if (mLDPersonSchema != null) {
				if (mLDPersonSchema.getName() != null) { // 去空格
					mLDPersonSchema.setName(mLDPersonSchema.getName().trim());
					if (mLDPersonSchema.getSex() != null) {
						mLDPersonSchema.setSex(mLDPersonSchema.getSex().trim());
					}
					if (mLDPersonSchema.getIDNo() != null) {
						mLDPersonSchema.setIDNo(mLDPersonSchema.getIDNo()
								.trim());
					}
					if (mLDPersonSchema.getIDType() != null) {
						mLDPersonSchema.setIDType(mLDPersonSchema.getIDType()
								.trim());
					}
					if (mLDPersonSchema.getBirthday() != null) {
						mLDPersonSchema.setBirthday(mLDPersonSchema
								.getBirthday().trim());
					}

				}
				if (mLDPersonSchema.getCustomerNo() != null) {
					// 如果有客户号
					if (!mLDPersonSchema.getCustomerNo().equals("")) {
						LDPersonDB tLDPersonDB = new LDPersonDB();

						tLDPersonDB.setCustomerNo(mLDPersonSchema
								.getCustomerNo());
						if (tLDPersonDB.getInfo() == false) {
							CError.buildErr(this, "数据库查询失败!");
							return false;
						}
						if (mLDPersonSchema.getName() != null) {
							String Name = StrTool.GBKToUnicode(tLDPersonDB
									.getName().trim());
							String NewName = StrTool
									.GBKToUnicode(mLDPersonSchema.getName()
											.trim());

							if (!Name.equals(NewName)) {
								CError.buildErr(this, "您输入的投保人客户号对应在数据库中的客户姓名("
										+ Name + ")与您录入的客户姓名(" + NewName
										+ ")不匹配！请去掉客户号码以及地址号码，重新操作！");
								return false;
							}
						}
						if (mLDPersonSchema.getSex() != null) {
							if (!tLDPersonDB.getSex().equals(
									mLDPersonSchema.getSex())) {
								CError.buildErr(this, "您输入的投保人客户号对应在数据库中的客户性别("
										+ tLDPersonDB.getSex() + ")与您录入的客户性别("
										+ mLDPersonSchema.getSex() + ")不匹配！");
								return false;
							}
						}
					} else { // 如果没有客户号,查找客户信息表是否有相同名字，性别，出生年月，身份证号的纪录，若有，取客户号
						if ((mLDPersonSchema.getName() != null)
								&& (mLDPersonSchema.getSex() != null)
								&& (mLDPersonSchema.getIDNo() != null)) {
							LDPersonDB tLDPersonDB = new LDPersonDB();
							tLDPersonDB.setName(mLDPersonSchema.getName());
							tLDPersonDB.setSex(mLDPersonSchema.getSex());
							tLDPersonDB.setBirthday(mLDPersonSchema
									.getBirthday());

							tLDPersonDB.setIDType("0"); // 证件类型为身份证
							tLDPersonDB.setIDNo(mLDPersonSchema.getIDNo());

							LDPersonSet tLDPersonSet = tLDPersonDB.query();
							if (tLDPersonSet != null) {
								if (tLDPersonSet.size() > 0) {
									mLDPersonSchema.setCustomerNo(tLDPersonSet
											.get(1).getCustomerNo());
								}
							}
						}
					}
				}

			}
		}
		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean dealData() {

		// 产生集体投保单号码
		// 设置LCCont相关值
		// TakeBackCertifyFalg = true;
		String ContRelationNO = "";
		String LDPersonRelationNO = "";
		String tLimit = PubFun.getNoLimit(mLCContSchema.getManageCom());
		String tNo = "";
		logger.debug("aaaa" + mLCContSchema.getContNo());
		if (mLCContSchema.getContNo() == null
				|| ("").equals(mLCContSchema.getContNo())) {
			tNo = PubFun1.CreateMaxNo("ProposalContNo", tLimit);
			mLCContSchema.setProposalContNo(tNo);
			ContRelationNO = tNo;
			// tNo = PubFun1.CreateMaxNo("ContNo", tLimit);
			// logger.debug("ContNo" + tNo);
			mLCContSchema.setContNo(tNo);
			mLCContSchema.setGrpContNo(SysConst.ZERONO); // 集体合同号
		} else {
			ContRelationNO = mLCContSchema.getContNo();
		}
		mLCContSchema.setSignCom(mLCContSchema.getManageCom());
		mLCContSchema.setExecuteCom(mLCContSchema.getManageCom());
		// 设置合同信息
		mLCContSchema.setAppFlag("0");

		mLCContSchema.setUWFlag("0");
		mLCContSchema.setApproveFlag("0");
		mLCContSchema.setMult(0);
		mLCContSchema.setPrem(0);
		mLCContSchema.setAmnt(0);

		mLCContSchema.setModifyDate(theCurrentDate);
		mLCContSchema.setModifyTime(theCurrentTime);
		mLCContSchema.setOperator(mGlobalInput.Operator);
		// 产生客户号

//		String tsql = " select customerno from ldperson where 1=1 "
//				+ " and name = '" + mLCAppntBL.getAppntName() + "'"
//				+ " and sex = '" + mLCAppntBL.getAppntSex() + "'"
//				+ " and trim(ComPareID(trim(IDNo),trim(IDType),'"
//				+ mLCAppntBL.getIDNo() + "','" + mLCAppntBL.getIDType()
//				+ "'))='1'" + " and IDType = '" + mLCAppntBL.getIDType() + "'"
//				+ " and Birthday = '" + mLCAppntBL.getAppntBirthday() + "'";
		String ss = "";
//		ExeSQL ttExeSQL = new ExeSQL();
		//KDCheckFlag=1 自助卡单不需要进行客户号合并，直接生成新的客户号
		if(KDCheckFlag==null||!KDCheckFlag.equals("1"))
		{
//			ss = ttExeSQL.getOneValue(tsql);
			if(ss==null||"".equals(ss)){
				if(!"DELETE||CONT".equals(mOperate)){
					ss=getCustomerNo(mLCAppntBL.getSchema(),mLCAddressSchema);
				}
			}
		}
		
		logger.debug("ss=" + ss);

		LDPersonRelationNO = mLCAppntBL.getAppntNo();

		if ((mLCAppntBL.getAppntNo() == null || ("").equals(mLCAppntBL
				.getAppntNo()))
				&& (ss == null || ss.equals(""))) {
			tNo = PubFun1.CreateMaxNo("CUSTOMERNO", "SN");
			LDPersonRelationNO = tNo;
			// ref.transFields(mLDPersonSchema, mLCAppntBL);
			mLCAppntBL.setAppntNo(tNo);
			mLDPersonSchema.setCustomerNo(tNo);
			mLDPersonSchema.setName(mLCAppntBL.getAppntName());
			mLDPersonSchema.setSex(mLCAppntBL.getAppntSex());
			mLDPersonSchema.setBirthday(mLCAppntBL.getAppntBirthday());
			mLDPersonSchema.setIDType(mLCAppntBL.getIDType());
			mLDPersonSchema.setIDNo(mLCAppntBL.getIDNo());
			mLDPersonSchema.setNativePlace(mLCAppntBL.getNativePlace());
			mLDPersonSchema.setNationality(mLCAppntBL.getNationality());
			mLDPersonSchema.setRgtAddress(mLCAppntBL.getRgtAddress());
			mLDPersonSchema.setMarriage(mLCAppntBL.getMarriage());
			mLDPersonSchema.setDegree(mLCAppntBL.getDegree());
			mLDPersonSchema.setOccupationType(mLCAppntBL.getOccupationType());
			mLDPersonSchema.setOccupationCode(mLCAppntBL.getOccupationCode());
			mLDPersonSchema.setWorkType(mLCAppntBL.getWorkType());
			mLDPersonSchema.setPluralityType(mLCAppntBL.getPluralityType());
			mLDPersonSchema.setSmokeFlag(mLCAppntBL.getSmokeFlag());
			mLDPersonSchema.setVIPValue("0");
			mLDPersonSchema.setMakeDate(theCurrentDate);
			mLDPersonSchema.setMakeTime(theCurrentTime);
			mLDPersonSchema.setModifyDate(theCurrentDate);
			mLDPersonSchema.setModifyTime(theCurrentTime);
			mLDPersonSchema.setOperator(mGlobalInput.Operator);
			logger.debug("单位编码是：" + GrpNo);
			mLDPersonSchema.setGrpNo(GrpNo);
			mLDPersonSchema.setGrpName(GrpName);
			mLDPersonSchema.setLicenseType(mLCAppntBL.getLicenseType());
			mLDPersonSchema.setIDExpDate(mLCAppntBL.getIDExpDate());
			mLDPersonSchema.setSocialInsuFlag(mLCAppntBL.getSocialInsuFlag());
			mLDPersonSchema.setLastName(mLCAppntBL.getLastName());
			mLDPersonSchema.setFirstName(mLCAppntBL.getFirstName());
			mLDPersonSchema.setLastNameEn(mLCAppntBL.getLastNameEn());
			mLDPersonSchema.setFirstNameEn(mLCAppntBL.getFirstNameEn());
			mLDPersonSchema.setNameEn(mLCAppntBL.getNameEn());
			mLDPersonSchema.setLastNamePY(mLCAppntBL.getLastNamePY());
			mLDPersonSchema.setFirstNamePY(mLCAppntBL.getFirstNamePY());
			mLDPersonSchema.setLanguage(mLCAppntBL.getLanguage());
			mLDPersonSet.add(mLDPersonSchema); // tLDPersonSet包含需要新建的客户
		} else {
			/**
			 * @todo 根据是ss和appntno的号码是否存在来进行ldperson表的查询
			 */
			LDPersonDB tLDPersonDB = new LDPersonDB();
			if (mLCAppntBL.getAppntNo() != null
					&& !mLCAppntBL.getAppntNo().equals("")) {
				tLDPersonDB.setCustomerNo(mLCAppntBL.getAppntNo());
			} else {
				tLDPersonDB.setCustomerNo(ss);
				LDPersonRelationNO = ss;
			}

			if (tLDPersonDB.getInfo() == false) {
				CError.buildErr(this, "没有查到客户信息!");
			}
			mLDPersonSchema = tLDPersonDB.getSchema();
			mLDPersonSchema.setGrpNo(GrpNo);
			mLDPersonSchema.setGrpName(GrpName);
			////////////////////////////////////////////////////////////////////
			// ////
			// hanlin 20050517
			// 对于已有客户信息的修改，仍然会修改客户层信息。
			//mLDPersonSchema.setCustomerNo(tNo);
			mLDPersonSchema.setName(mLCAppntBL.getAppntName());
			mLDPersonSchema.setSex(mLCAppntBL.getAppntSex());
			mLDPersonSchema.setBirthday(mLCAppntBL.getAppntBirthday());
			mLDPersonSchema.setIDType(mLCAppntBL.getIDType());
			mLDPersonSchema.setIDNo(mLCAppntBL.getIDNo());
			mLDPersonSchema.setNativePlace(mLCAppntBL.getNativePlace());
			mLDPersonSchema.setNationality(mLCAppntBL.getNationality());
			mLDPersonSchema.setRgtAddress(mLCAppntBL.getRgtAddress());
			mLDPersonSchema.setMarriage(mLCAppntBL.getMarriage());
			mLDPersonSchema.setDegree(mLCAppntBL.getDegree());
			mLDPersonSchema.setOccupationType(mLCAppntBL.getOccupationType());
			mLDPersonSchema.setOccupationCode(mLCAppntBL.getOccupationCode());
			mLDPersonSchema.setWorkType(mLCAppntBL.getWorkType());
			mLDPersonSchema.setPluralityType(mLCAppntBL.getPluralityType());
			mLDPersonSchema.setSmokeFlag(mLCAppntBL.getSmokeFlag());
			mLDPersonSchema.setVIPValue("0");
			//mLDPersonSchema.setMakeDate(theCurrentDate);
			//mLDPersonSchema.setMakeTime(theCurrentTime);
			mLDPersonSchema.setModifyDate(theCurrentDate);
			mLDPersonSchema.setModifyTime(theCurrentTime);
			mLDPersonSchema.setOperator(mGlobalInput.Operator);
			logger.debug("单位编码是：" + GrpNo);
			mLDPersonSchema.setGrpNo(GrpNo);
			mLDPersonSchema.setGrpName(GrpName);
			mLDPersonSchema.setLicenseType(mLCAppntBL.getLicenseType());
			mLDPersonSchema.setModifyDate(theCurrentDate);
			mLDPersonSchema.setModifyTime(theCurrentTime);
			mLDPersonSchema.setIDExpDate(mLCAppntBL.getIDExpDate());
			mLDPersonSchema.setSocialInsuFlag(mLCAppntBL.getSocialInsuFlag());
			mLDPersonSchema.setLanguage(mLCAppntBL.getLanguage());
			// /////////////////////////////////////////////////////////////
			mLDPersonSet.add(mLDPersonSchema);
			updateldperson = true;
		}

		if (mLDPersonSchema.getCustomerNo() != null
				&& !(mLDPersonSchema.getCustomerNo().equals(""))) {
			mLCAppntBL.setAppntNo(mLDPersonSchema.getCustomerNo());

		} else {
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());

			if (tLCAppntDB.getInfo()) {
				CError tError = new CError();
				tError.moduleName = "ContIsuredBL";
				tError.functionName = "DealDate";
				tError.errorMessage = "不能重复添加同一客户!";
				this.mErrors.addOneError(tError);

			}
			mLCAppntBL.setAppntNo(ss);
		}
		logger.debug("mLCInsuredSchema.getInsuredNo:"
				+ mLCInsuredSchema.getInsuredNo());

		// 产生客户地址,需要增加统一处理客户地址方法
		if (mLCAddressSchema != null) {
			if (mLCAddressSchema.getAddressNo() == 0) {
				try {
					SSRS tSSRS = new SSRS();
					String sql = "Select Case When max(AddressNo) Is Null Then 0 Else max(AddressNo) End from LCAddress where CustomerNo='"
							+ "?CustomerNo?" + "'";
					SQLwithBindVariables sqlbv = new SQLwithBindVariables();
					sqlbv.sql(sql);
					sqlbv.put("CustomerNo",mLCAppntBL.getAppntNo());
					ExeSQL tExeSQL = new ExeSQL();
					tSSRS = tExeSQL.execSQL(sqlbv);
					Integer firstinteger = Integer.valueOf(tSSRS.GetText(1, 1));
					int ttNo = firstinteger.intValue() + 1;
					Integer integer = new Integer(ttNo);
					tNo = integer.toString();
					logger.debug("得到的地址码是：" + tNo);
					mLCAddressSchema.setAddressNo(tNo);
				} catch (Exception e) {
					CError.buildErr(this,"地址码超长,生成号码失败,请先删除原来的超长地址码!");
					mLCAddressSchema.setAddressNo("");
				}

			}
			mLCAddressSchema.setCustomerNo(mLCAppntBL.getAppntNo());
			mLCAddressSchema.setModifyDate(theCurrentDate);
			mLCAddressSchema.setModifyTime(theCurrentTime);
			mLCAddressSchema.setOperator(mGlobalInput.Operator);
			mLCAddressSchema.setMakeDate(theCurrentDate);
			mLCAddressSchema.setMakeTime(theCurrentTime);
			map.put(mLCAddressSchema, "DELETE&INSERT");
		}

		// 产生客户账户,需要增加统一处理客户账户方法
		if (mLCAccountSchema != null) {
			if (mLCAccountSchema.getBankCode() == null
					|| mLCAccountSchema.getBankAccNo() == null
					|| mLCAccountSchema.getBankCode().equals("")
					|| mLCAccountSchema.getBankAccNo().equals("")) {
			} else {
				mLCAccountSchema.setCustomerNo(mLCAppntBL.getAppntNo());
				if (mLCAccountSchema.getAccName() == null
						&& ("").equals(mLCAccountSchema.getAccName())) {
					mLCAccountSchema.setAccName(mLCAppntBL.getAppntName());
				}
				mLCAccountSchema.setMakeDate(theCurrentDate);
				mLCAccountSchema.setMakeTime(theCurrentTime);
				mLCAccountSchema.setModifyDate(theCurrentDate);
				mLCAccountSchema.setModifyTime(theCurrentTime);
				mLCAccountSchema.setOperator(mGlobalInput.Operator);

				map.put(mLCAccountSchema, "DELETE&INSERT");
			}
		}

		// 个人投保人部分信息
		if (mLCAddressSchema != null) {
			mLCAppntBL.setAddressNo("" + mLCAddressSchema.getAddressNo());
		}
		mLCAppntBL.setContNo(mLCContSchema.getContNo());
		mLCAppntBL.setGrpContNo(mLCContSchema.getGrpContNo());
		mLCAppntBL.setPrtNo(mLCContSchema.getPrtNo());
		mLCAppntBL.setManageCom(mLCContSchema.getManageCom());
		mLCAppntBL.setModifyDate(theCurrentDate);
		mLCAppntBL.setModifyTime(theCurrentTime);
		mLCAppntBL.setOperator(mGlobalInput.Operator);
//		mLCAppntBL.setSocialInsuFlag("0");//初始化社保标记

		// 设置合同上投保人信息
		mLCContSchema.setAppntSex(mLCAppntBL.getAppntSex());
		mLCContSchema.setAppntBirthday(mLCAppntBL.getAppntBirthday());
		mLCContSchema.setAppntIDNo(mLCAppntBL.getIDNo());
		mLCContSchema.setAppntIDType(mLCAppntBL.getIDType());
		mLCContSchema.setAppntName(mLCAppntBL.getAppntName());
		mLCContSchema.setAppntNo(mLCAppntBL.getAppntNo());
		mLCContSchema.setInsuredNo("0"); // 暂填
		mLCContSchema.setAppFlag("0");
		mLCContSchema.setApproveFlag("0");
		mLCContSchema.setUWFlag("0");

		// 处理告知信息
		if (mLCCustomerImpartSet != null && mLCCustomerImpartSet.size() > 0) {
			// 设置所有告知信息得客户号码
			for (int i = 1; i <= mLCCustomerImpartSet.size(); i++) {
				mLCCustomerImpartSet.get(i)
						.setContNo(mLCContSchema.getContNo());
				mLCCustomerImpartSet.get(i).setGrpContNo(
						mLCContSchema.getGrpContNo());
				mLCCustomerImpartSet.get(i).setPrtNo(mLCContSchema.getPrtNo());
				mLCCustomerImpartSet.get(i).setProposalContNo(
						mLCContSchema.getProposalContNo());
				mLCCustomerImpartSet.get(i).setCustomerNo(
						mLCAppntBL.getAppntNo());
				//处理投保人社保标记  因新版投保单将社保由告知转到了直接录入是否，故此处先取input录入的值，如果为空
				//才去判断告知中是否录入有社保标记
				if(mLCAppntBL.getSocialInsuFlag()==null||"".equals(mLCAppntBL.getSocialInsuFlag())){
					mLCAppntBL.setSocialInsuFlag("0");//如果界面传入为空则初始化值为0
					if(mLCContSchema.getPrtNo()!=null && !mLCContSchema.getPrtNo().equals("") 
							&& mLCContSchema.getPrtNo().length()==14 && mLCContSchema.getPrtNo().substring(2,4).equals("51"))//家庭单
					{
						if (mLCCustomerImpartSet.get(i).getImpartVer()!=null && !mLCCustomerImpartSet.get(i).getImpartVer().equals("")
							&& mLCCustomerImpartSet.get(i).getImpartCode()!=null && !mLCCustomerImpartSet.get(i).getImpartCode().equals("")
							&& mLCCustomerImpartSet.get(i).getCustomerNoType()!=null && !mLCCustomerImpartSet.get(i).getCustomerNoType().equals("")
							&& ("D02".equals(mLCCustomerImpartSet.get(i).getImpartVer())
							&& "D0118".equals(mLCCustomerImpartSet.get(i)
									.getImpartCode()))
							&& "0".equals(mLCCustomerImpartSet.get(i)
									.getCustomerNoType())) {
							mLCAppntBL.setSocialInsuFlag("1");
						}else{
							mLCAppntBL.setSocialInsuFlag("0");
						}
					}
				}
			}
			CustomerImpartBL mCustomerImpartBL = new CustomerImpartBL();
			VData tempVData = new VData();
			tempVData.add(mLCCustomerImpartSet);
			tempVData.add(mGlobalInput);
			mCustomerImpartBL.submitData(tempVData, "IMPART||DEAL");
			if (mCustomerImpartBL.mErrors.needDealError()) {
				CError tError = new CError();
				tError.moduleName = "ContInsuredBL";
				tError.functionName = "dealData";
				tError.errorMessage = mCustomerImpartBL.mErrors.getFirstError()
						.toString();
				this.mErrors.addOneError(tError);
				return false;
			}
			tempVData.clear();
			tempVData = mCustomerImpartBL.getResult();
			if (null != (LCCustomerImpartSet) tempVData.getObjectByObjectName(
					"LCCustomerImpartSet", 0)) {
				mLCCustomerImpartSet = (LCCustomerImpartSet) tempVData
						.getObjectByObjectName("LCCustomerImpartSet", 0);
				logger.debug("告知条数" + mLCCustomerImpartSet.size());
			} else {
				logger.debug("告知条数为空");
			}

			if (null != (LCCustomerImpartParamsSet) tempVData
					.getObjectByObjectName("LCCustomerImpartParamsSet", 0)) {
				mLCCustomerImpartParamsSet = (LCCustomerImpartParamsSet) tempVData
						.getObjectByObjectName("LCCustomerImpartParamsSet", 0);
			}
		}
		
		//处理客户表社保标记
		for(int i=1; i<=mLDPersonSet.size();i++)
			mLDPersonSet.get(i).setSocialInsuFlag(mLCAppntBL.getSocialInsuFlag());

		if (mOperate.equals("UPDATE||CONT")) {
			mLCAppntBL.setGrpContNo(mOldLCCont.getGrpContNo());
			mLCAppntBL.setContNo(mLCContSchema.getContNo());
			mLCAppntBL.setPrtNo(mLCContSchema.getPrtNo());
			mLCAppntBL.setMakeDate(mOldLCCont.getMakeDate());
			mLCAppntBL.setMakeTime(mOldLCCont.getMakeTime());
			mLCContSchema.setGrpContNo(mOldLCCont.getGrpContNo());
			mLCContSchema.setProposalContNo(mOldLCCont.getProposalContNo());
			mLCContSchema.setContNo(mOldLCCont.getContNo());
			mLCContSchema.setAppFlag(mOldLCCont.getAppFlag());
			mLCContSchema.setUWFlag(mOldLCCont.getUWFlag());
			mLCContSchema.setApproveFlag(mOldLCCont.getApproveFlag());
			mLCContSchema.setApproveDate(mOldLCCont.getApproveDate());
			mLCContSchema.setApproveCode(mOldLCCont.getApproveCode());
			mLCContSchema.setMult(mOldLCCont.getMult());
			mLCContSchema.setInsuredNo(mOldLCCont.getInsuredNo());
			mLCContSchema.setInsuredBirthday(mOldLCCont.getInsuredBirthday());
			mLCContSchema.setInsuredIDNo(mOldLCCont.getInsuredIDNo());
			mLCContSchema.setInsuredIDType(mOldLCCont.getInsuredIDType());
			mLCContSchema.setInsuredName(mOldLCCont.getInsuredName());
			mLCContSchema.setInsuredSex(mOldLCCont.getInsuredSex());
			mLCContSchema.setPrem(mOldLCCont.getPrem());
			mLCContSchema.setAmnt(mOldLCCont.getAmnt());
			mLCContSchema.setUWOperator(mOldLCCont.getUWOperator());
			mLCContSchema.setOperator(mOldLCCont.getOperator());
			mLCContSchema.setMakeDate(mOldLCCont.getMakeDate());
			mLCContSchema.setMakeTime(mOldLCCont.getMakeTime());
			mLCContSchema.setModifyDate(theCurrentDate);
			mLCContSchema.setModifyTime(theCurrentTime);
		}

		if (mOperate.equals("INSERT||CONT")) {
			// 由于在投保书影像上传时已经关联到投保单号在次不需要进行再次关联，故注释
			// tmap = getRelation(ContRelationNO, LDPersonRelationNO);
			// if (tmap != null) {
			// map.add(tmap); //对于关联表的数据
			// }
			mLCContSchema.setMakeDate(theCurrentDate);
			mLCContSchema.setMakeTime(theCurrentTime);
			mLCAppntBL.setMakeDate(theCurrentDate);
			mLCAppntBL.setMakeTime(theCurrentTime);
			// modify by pz 2007-04-09 团体卡式无名单不处理客户信息表PolType=1为无名单
			if ((mLDPersonSet.size() > 0 && !updateldperson)
					&& !mLCContSchema.getPolType().equals("1")) {
				map.put(mLDPersonSet, "INSERT");
			}
			if ((mLDPersonSet.size() > 0 && updateldperson)
					&& !mLCContSchema.getPolType().equals("1")) {
				map.put(mLDPersonSet, "UPDATE");
			}
			/** @todo 对于卡单需要在最后提交合同信息 */
			if (mark != null) {
				if (!mark.equals("card")) {
					map.put(mLCContSchema, "INSERT");
				}
			} else {
				map.put(mLCContSchema, "INSERT");
			}

			map.put(mLCAppntBL.getSchema(), "INSERT");
			if (mLCCustomerImpartParamsSet != null
					&& mLCCustomerImpartParamsSet.size() > 0) {
				map.put(mLCCustomerImpartParamsSet, "INSERT");
			}
			if (mLCCustomerImpartSet != null && mLCCustomerImpartSet.size() > 0) {
				map.put(mLCCustomerImpartSet, "INSERT");
			}

		}
		/** @todo 卡单暂时没有修改功能，需要完善 */
		if (mOperate.equals("UPDATE||CONT")) {
			if (mLDPersonSet.size() > 0 && !updateldperson) {
				map.put(mLDPersonSet, "INSERT");
			}
			if (mLDPersonSet.size() > 0 && updateldperson) {
				map.put(mLDPersonSet, "UPDATE");
			}
			logger.debug("the appntIDNo in BL========================================>>>>>>>>>>"
							+ mLCContSchema.getAppntIDNo());
			logger.debug("the contno in BL========================================>>>>>>>>>>"
							+ mLCContSchema.getContNo());
//			map.put("update lccont set AppntNo='" + mLCContSchema.getAppntNo()
//					+ "',appntname='" + mLCContSchema.getAppntName()
//					+ "',appntsex='" + mLCContSchema.getAppntSex()
//					+ "',appntbirthday='" + mLCContSchema.getAppntBirthday()
//					+ "',appntidtype='" + mLCContSchema.getAppntIDType()
//					+ "',appntidno ='" + mLCContSchema.getAppntIDNo()
//					+ "',ModifyDate='" + theCurrentDate + "',ModifyTime='"
//					+ theCurrentTime + "' where contNo='"
//					+ mLCContSchema.getContNo() + "'", "UPDATE");
			map.put(mLCContSchema, "UPDATE");

			map.put(mLCAppntBL.getSchema(), "UPDATE");
			String sql = "delete from LCCustomerImpart where " + " contno = '"
					+ "?contno?" + "'"
					+ " and CustomerNoType in('0','2')";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("contno", mLCContSchema.getContNo());
			map.put(sqlbv, "DELETE");
			if(!"card".equals(mark))
				map.put(mLCCustomerImpartSet, "DELETE&INSERT");

			String Sql = "delete from LCCustomerImpartparams where "
					+ " contno = '" + "?contno?" + "'"
					+ " and CustomerNoType in('0','2')";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(Sql);
			sqlbv1.put("contno", mLCContSchema.getContNo());
			map.put(sqlbv1, "DELETE");
			map.put(mLCCustomerImpartParamsSet, "DELETE&INSERT");
		}
		if (mOperate.equals("UPDATE||CONT")) {

			String strSQL = "select count(*) from LCInsured where contno='"
					+ "?contno?" + "'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(strSQL);
			sqlbv2.put("contno", mLCContSchema.getContNo());
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = tExeSQL.execSQL(sqlbv2);
			String strCount = tSSRS.GetText(1, 1);
			int SumCount = Integer.parseInt(strCount);
			if (SumCount > 0) {
				SQLwithBindVariables sqlbv3= new SQLwithBindVariables();
				sqlbv3.sql("update LCInsured set AppntNo='"
						+ "?AppntNo?" + "'," + "ManageCom='"
						+ "?ManageCom?" + "'"
						// +"ExecuteCom='"+mLCContSchema.getExecuteCom()+"' "
						+ "where contno='" + "?contno?" + "'");
				sqlbv3.put("AppntNo", mLCAppntBL.getAppntNo());
				sqlbv3.put("ManageCom", mLCContSchema.getManageCom());
				sqlbv3.put("contno", mLCContSchema.getContNo());
				map.put(sqlbv3,"UPDATE");
				if(!"card".equals(this.mark)){
					SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
					sqlbv4.sql("update LCPol set AppntNo='" + "?AppntNo?"
							+ "'," + "AppntName='" + "?AppntName?"
							+ "'," + "ManageCom='" + "?ManageCom?"
							+ "'," + "AgentCom='" + "?AgentCom?"
							+ "'," + "SaleChnl='" + "?SaleChnl?"
							+ "'," + "AgentCode='" + "?AgentCode?"
							+ "'," + "AgentGroup='" + "?AgentGroup?"
							+ "' " + "where contno='" + "?contno?"
							+ "'");
					sqlbv4.put("AppntNo", mLCAppntBL.getAppntNo());
					sqlbv4.put("AppntName", mLCAppntBL.getAppntName());
					sqlbv4.put("ManageCom", mLCContSchema.getManageCom());
					sqlbv4.put("AgentCom", mLCContSchema.getAgentCom());
					sqlbv4.put("SaleChnl", mLCContSchema.getSaleChnl());
					sqlbv4.put("AgentCode", mLCContSchema.getAgentCode());
					sqlbv4.put("AgentGroup", mLCContSchema.getAgentGroup());
					sqlbv4.put("contno", mLCContSchema.getContNo());
					map.put(sqlbv4, "UPDATE");
					
					SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
					sqlbv5.sql("update LCPrem set AppntNo='" + "?AppntNo?"
							+ "' " + "where contno='" + "?contno?"
							+ "'");
					sqlbv5.put("AppntNo", mLCAppntBL.getAppntNo());
					sqlbv5.put("contno", mLCContSchema.getContNo());
					map.put(sqlbv5, "UPDATE");
				}

			}

		}

		if (mOperate.equals("DELETE||CONT")) {
			map.put(mLCContSchema, "DELETE");
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql("delete from LCAppnt where ContNo='"
					+ "?ContNo?" + "'");
			sqlbv6.put("ContNo", mLCContSchema.getContNo());
			map.put(sqlbv6, "DELETE");
			
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql("delete from LCCustomerImpart where ContNo='"
					+ "?ContNo?" + "'");
			sqlbv7.put("ContNo", mLCContSchema.getContNo());
			map.put(sqlbv7, "DELETE");
			
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql("delete from LCCustomerImpartparams where ContNo='"
					+ "?ContNo?" + "'");
			sqlbv8.put("ContNo", mLCContSchema.getContNo());
			map.put(sqlbv8, "DELETE");
			
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.sql("delete from LCAccount where customerno='"
					+ "?customerno?" + "'");
			sqlbv9.put("customerno", mLCContSchema.getAppntNo());
			map.put(sqlbv9, "DELETE");
			
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql("delete from lcaddress where customerno='"
					+ "?customerno?" + "'");
			sqlbv10.put("customerno", mLCContSchema.getAppntNo());
			map.put(sqlbv10, "DELETE");
			
		}

		// 处理多业务员的情况
		if (mOperate.equals("INSERT||CONT")) {
			for (int i = 0; mLACommisionDetailSet != null
					&& i < this.mLACommisionDetailSet.size(); i++) {
				this.mLACommisionDetailSet.get(i + 1).setMakeDate(
						theCurrentDate);
				this.mLACommisionDetailSet.get(i + 1).setMakeTime(
						theCurrentTime);
				this.mLACommisionDetailSet.get(i + 1).setModifyDate(
						theCurrentDate);
				this.mLACommisionDetailSet.get(i + 1).setModifyTime(
						theCurrentTime);
				this.mLACommisionDetailSet.get(i + 1).setOperator(
						mGlobalInput.Operator);
				this.mLACommisionDetailSet.get(i + 1).setStartSerDate(
						theCurrentDate);

			}
			logger.debug("this.mLCContSchema.getSaleChnl()=="
					+ this.mLCContSchema.getSaleChnl());
			// add by pz begin 2007-04-10 对于团体交叉销售，根据录入的个人业务员，取得对应的团体业务员信息
			// 判断该业务员是否是个险业务员
			// tongmeng 2007-09-06 add 注释
			// 5.3系统暂时不支持交叉销售且LAAgentToBranch表不存在，需要升级时考虑。
			if (this.mLCContSchema.getContType().equals("2")
					&& mark.equals("card")) // 团体卡单特殊处理
			{
				String tSQL = "select * from laagent where agentcode='"
						+ "?agentcode?"
						+ "' and branchtype ='1' ";
				SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
				sqlbv11.sql(tSQL);
				sqlbv11.put("agentcode", this.mLCContSchema.getAgentCode());
				ExeSQL exeSQL0 = new ExeSQL();
				SSRS ss1 = exeSQL0.execSQL(sqlbv11);
				if (ss1 == null) {
					CError.buildErr(this,"查询业务员"
							+ this.mLCContSchema.getAgentCode() + "信息出错。");
					return false;
				}
				mLCContSchema.setAgentType("01"); // 直销团队

				int count = ss1.getMaxRow();

				if (count > 0) {
					mLCContSchema.setAgentType("05"); // 交叉销售
					String strSQL = "select AgentCode,StandbyFlag1,BranchCode from LAAgentToBranch where type='1' and code1='"
							+ "?agentcode?" + "'";
					ExeSQL exeSQL = new ExeSQL();
					SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
					sqlbv12.sql(tSQL);
					sqlbv12.put("agentcode", this.mLCContSchema.getAgentCode());
					SSRS ssrs = exeSQL.execSQL(sqlbv12);
					int i_count = ssrs.getMaxRow();
					if (i_count > 0) {
						for (int i = 1; i <= i_count; i++) {
							LACommisionDetailSchema bLACommisionDetailSchema = new LACommisionDetailSchema();
							bLACommisionDetailSchema.setGrpContNo(mLCContSchema
									.getContNo());
							bLACommisionDetailSchema.setAgentCode(ssrs.GetText(
									i, 1));
							bLACommisionDetailSchema.setBusiRate(ssrs.GetText(
									i, 2));
							bLACommisionDetailSchema.setAgentGroup(ssrs
									.GetText(i, 3));
							bLACommisionDetailSchema.setPolType("0");
							bLACommisionDetailSchema
									.setOperator(mGlobalInput.Operator);
							bLACommisionDetailSchema
									.setMakeDate(theCurrentDate);
							bLACommisionDetailSchema
									.setMakeTime(theCurrentTime);
							bLACommisionDetailSchema
									.setModifyDate(theCurrentDate);
							bLACommisionDetailSchema
									.setModifyTime(theCurrentTime);
							mLACommisionDetailSet.add(bLACommisionDetailSchema);
							logger.debug("mLACommisionDetailSet1:"
									+ mLACommisionDetailSet.encode());
						}
					} else {
						strSQL = "select AgentCode,StandbyFlag1,BranchCode from LAAgentToBranch where type='2'"
								+ " and code1=(select AgentGroup from LATree where AgentCode='"
								+ "?agentcode?" + "')";
						SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
						sqlbv13.sql(tSQL);
						sqlbv13.put("agentcode", this.mLCContSchema.getAgentCode());
						SSRS ssrs1 = exeSQL.execSQL(sqlbv13);
						int j_count = ssrs1.getMaxRow();
						if (j_count > 0) {
							for (int l = 1; l <= j_count; l++) {
								LACommisionDetailSchema cLACommisionDetailSchema = new LACommisionDetailSchema();
								cLACommisionDetailSchema
										.setGrpContNo(mLCContSchema.getContNo());
								cLACommisionDetailSchema.setAgentCode(ssrs1
										.GetText(l, 1));
								cLACommisionDetailSchema.setBusiRate(ssrs1
										.GetText(l, 2));
								cLACommisionDetailSchema.setAgentGroup(ssrs1
										.GetText(l, 3));
								cLACommisionDetailSchema.setPolType("0");
								cLACommisionDetailSchema
										.setOperator(mGlobalInput.Operator);
								cLACommisionDetailSchema
										.setMakeDate(theCurrentDate);
								cLACommisionDetailSchema
										.setMakeTime(theCurrentTime);
								cLACommisionDetailSchema
										.setModifyDate(theCurrentDate);
								cLACommisionDetailSchema
										.setModifyTime(theCurrentTime);
								mLACommisionDetailSet
										.add(cLACommisionDetailSchema);
								logger.debug("mLACommisionDetailSet2:"
										+ mLACommisionDetailSet.encode());
							}
						}

						else {
							strSQL = "select AgentCode,StandbyFlag1,BranchCode from LAAgentToBranch where type='3'"
									+ " and code1=(select ManageCom from LATree where AgentCode='"
									+ "?agentcode?" + "')";
							SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
							sqlbv14.sql(tSQL);
							sqlbv14.put("agentcode", this.mLCContSchema.getAgentCode());
							SSRS ssrs2 = exeSQL.execSQL(sqlbv14);
							int k_count = ssrs2.getMaxRow();
							if (k_count > 0) {
								for (int k = 1; k <= k_count; k++) {
									LACommisionDetailSchema dLACommisionDetailSchema = new LACommisionDetailSchema();
									dLACommisionDetailSchema
											.setGrpContNo(mLCContSchema
													.getContNo());
									dLACommisionDetailSchema.setAgentCode(ssrs2
											.GetText(k, 1));
									dLACommisionDetailSchema.setBusiRate(ssrs2
											.GetText(k, 2));
									dLACommisionDetailSchema
											.setAgentGroup(ssrs2.GetText(k, 3));
									dLACommisionDetailSchema.setPolType("0");
									dLACommisionDetailSchema
											.setOperator(mGlobalInput.Operator);
									dLACommisionDetailSchema
											.setMakeDate(theCurrentDate);
									dLACommisionDetailSchema
											.setMakeTime(theCurrentTime);
									dLACommisionDetailSchema
											.setModifyDate(theCurrentDate);
									dLACommisionDetailSchema
											.setModifyTime(theCurrentTime);
									mLACommisionDetailSet
											.add(dLACommisionDetailSchema);
									logger.debug("mLACommisionDetailSet3:"
													+ mLACommisionDetailSet
															.encode());
								}
							} else {
								CError.buildErr(this,"指派团险业务员失败");
								return false;

							}
						}
					}
				}
			}
			// add by pz end 2007-04-10
			if (this.mLCContSchema.getSaleChnl() != null
					&& this.mLCContSchema.getSaleChnl().equals("3")) {

				logger.debug("银代机构代码为：" + mLCContSchema.getAgentCom());
				// 根据网点代码查询专管员
				// tongmeng 2007-12-26 modify
				// 去掉代理人和代理机构校验
				// ExeSQL aExeSQL = new ExeSQL();
				/*
				 * String sql = "select * from lacomtoagent where agentcom='" +
				 * mLCContSchema.getAgentCom() + "'" + " and exists (select 'X'
				 * from lacomtoagent where agentcom='" +
				 * mLCContSchema.getAgentCom() + "'" + " and agentcode='" +
				 * mLCContSchema.getAgentCode() + "' )"; SSRS aSSRS =
				 * aExeSQL.execSQL(sql); if (aSSRS == null || aSSRS.getMaxRow()
				 * == 0) { if (mLCContSchema.getAgentCom() != null &&
				 * !mLCContSchema.getAgentCom().equals("") &&
				 * !mLCContSchema.getAgentCom().toLowerCase().equals("null")) {
				 * //加入此判断是为了排除银代专管员销售个险录单没有录入银代机构的情况 CError tError = new
				 * CError(); tError.moduleName = "ContBL"; tError.functionName =
				 * "dealdata"; tError.errorMessage = "录入的银代专管员代码[" +
				 * mLCContSchema.getAgentCode() + "]与银代网点机构代码[" +
				 * mLCContSchema.getAgentCom() + "]不符，请确认。";
				 * mErrors.addOneError(tError); return false; } }
				 * 
				 * String[][] aResult = new String[aSSRS.getMaxRow()][aSSRS.
				 * getMaxCol()]; aResult = aSSRS.getAllData();
				 */
				// tongmeng 2007-09-10 modify
				// lis5.3 lacomtoagent没有rate字段，暂时修改此处
				/*
				 * for (int i = 0; i < aResult.length; i++) {
				 * LACommisionDetailSchema aLACommisionDetailSchema = new
				 * LACommisionDetailSchema();
				 * aLACommisionDetailSchema.setAgentCode(aResult[i][2]); if
				 * (aResult[i][11] != null && !(aResult[i][11].equals("")) &&
				 * !(aResult[i][11].equals("null"))) {
				 * aLACommisionDetailSchema.setBusiRate(aResult[i][11]); } else
				 * { aLACommisionDetailSchema.setBusiRate(0); }
				 * aLACommisionDetailSchema.setMakeDate(theCurrentDate);
				 * aLACommisionDetailSchema.setMakeTime(theCurrentTime);
				 * aLACommisionDetailSchema.setModifyDate(theCurrentDate);
				 * aLACommisionDetailSchema.setModifyTime(theCurrentTime);
				 * aLACommisionDetailSchema.setOperator(mGlobalInput.Operator);
				 * aLACommisionDetailSchema.setStartSerDate(theCurrentDate);
				 * aLACommisionDetailSchema.setPolType("0");
				 * aLACommisionDetailSchema.setAgentGroup(aResult[i][3]);
				 * aLACommisionDetailSchema.setGrpContNo(mLCContSchema.
				 * getContNo()); if (mLACommisionDetailSet == null) {
				 * mLACommisionDetailSet = new LACommisionDetailSet(); }
				 * mLACommisionDetailSet.add(aLACommisionDetailSchema); }
				 */
				for (int i = 0; i < 1; i++) {
					LACommisionDetailSchema aLACommisionDetailSchema = new LACommisionDetailSchema();
					aLACommisionDetailSchema.setAgentCode(mLCContSchema
							.getAgentCode());
					aLACommisionDetailSchema.setBusiRate(0);
					aLACommisionDetailSchema.setMakeDate(theCurrentDate);
					aLACommisionDetailSchema.setMakeTime(theCurrentTime);
					aLACommisionDetailSchema.setModifyDate(theCurrentDate);
					aLACommisionDetailSchema.setModifyTime(theCurrentTime);
					aLACommisionDetailSchema.setOperator(mGlobalInput.Operator);
					aLACommisionDetailSchema.setStartSerDate(theCurrentDate);
					aLACommisionDetailSchema.setPolType("0");
					aLACommisionDetailSchema.setAgentGroup("000000");
					aLACommisionDetailSchema.setGrpContNo(mLCContSchema
							.getContNo());
					if (mLACommisionDetailSet == null) {
						mLACommisionDetailSet = new LACommisionDetailSet();
					}
					mLACommisionDetailSet.add(aLACommisionDetailSchema);
				}
			}
			map.put(this.mLACommisionDetailSet, "INSERT");
		}
		if (mOperate.equals("UPDATE||CONT")) {
			for (int i = 0; i < this.mLACommisionDetailSet.size(); i++) {
				LACommisionDetailDB tLACommisionDetailDB = new LACommisionDetailDB();
				if("card".equals(this.mark)){
					tLACommisionDetailDB.setGrpContNo(mLCContSchema.getPrtNo());
				}else{
					tLACommisionDetailDB.setGrpContNo(mLCContSchema.getContNo());
				}
				tLACommisionDetailDB.setAgentCode(mLCContSchema.getAgentCode());
				if (!tLACommisionDetailDB.getInfo()) {
					CError.buildErr(this,"查询LACommisionDetail失败");
				}
				this.mLACommisionDetailSet.get(i + 1).setMakeDate(
						theCurrentDate);
				this.mLACommisionDetailSet.get(i + 1).setMakeTime(
						theCurrentTime);
				this.mLACommisionDetailSet.get(i + 1).setModifyDate(
						theCurrentDate);
				this.mLACommisionDetailSet.get(i + 1).setModifyTime(
						theCurrentTime);
				this.mLACommisionDetailSet.get(i + 1).setOperator(
						mGlobalInput.Operator);
				this.mLACommisionDetailSet.get(i + 1).setStartSerDate(
						theCurrentDate);
			}
			if (this.mLCContSchema.getSaleChnl() != null
					&& this.mLCContSchema.getSaleChnl().equals("3")) {
				/*
				 * logger.debug("银代机构代码为：" + mLCContSchema.getAgentCom());
				 * //根据网点代码查询专管员 ExeSQL aExeSQL = new ExeSQL(); String sql =
				 * "select * from lacomtoagent where agentcom='" +
				 * mLCContSchema.getAgentCom() + "'" + " and exists (select 'X'
				 * from lacomtoagent where agentcom='" +
				 * mLCContSchema.getAgentCom() + "'" + " and agentcode='" +
				 * mLCContSchema.getAgentCode() + "' )"; SSRS aSSRS =
				 * aExeSQL.execSQL(sql); if (aSSRS == null || aSSRS.getMaxRow()
				 * == 0) { if (mLCContSchema.getAgentCom() != null &&
				 * !mLCContSchema.getAgentCom().equals("") &&
				 * !mLCContSchema.getAgentCom().toLowerCase().equals("null")) {
				 * //加入此判断是为了排除银代专管员销售个险录单没有录入银代机构的情况 CError tError = new
				 * CError(); tError.moduleName = "ContBL"; tError.functionName =
				 * "dealdata"; tError.errorMessage = "录入的银代专管员代码[" +
				 * mLCContSchema.getAgentCode() + "]与银代网点机构代码[" +
				 * mLCContSchema.getAgentCom() + "]不符，请确认。";
				 * mErrors.addOneError(tError); return false; } } String[][]
				 * aResult = new String[aSSRS.getMaxRow()][aSSRS. getMaxCol()];
				 * aResult = aSSRS.getAllData();
				 */
				// tongmeng 2007-09-10 modify
				/*
				 * for (int i = 0; i < aResult.length; i++) {
				 * LACommisionDetailSchema aLACommisionDetailSchema = new
				 * LACommisionDetailSchema();
				 * aLACommisionDetailSchema.setAgentCode(aResult[i][2]); if
				 * (aResult[i][11] != null && !(aResult[i][11].equals("")) &&
				 * !(aResult[i][11].equals("null"))) {
				 * aLACommisionDetailSchema.setBusiRate(aResult[i][11]); } else
				 * { aLACommisionDetailSchema.setBusiRate(0); }
				 * aLACommisionDetailSchema.setMakeDate(theCurrentDate);
				 * aLACommisionDetailSchema.setMakeTime(theCurrentTime);
				 * aLACommisionDetailSchema.setModifyDate(theCurrentDate);
				 * aLACommisionDetailSchema.setModifyTime(theCurrentTime);
				 * aLACommisionDetailSchema.setOperator(mGlobalInput.Operator);
				 * aLACommisionDetailSchema.setPolType("0");
				 * aLACommisionDetailSchema.setAgentGroup(aResult[i][3]);
				 * aLACommisionDetailSchema.setGrpContNo(mLCContSchema.
				 * getContNo());
				 * mLACommisionDetailSet.add(aLACommisionDetailSchema); }
				 */
				for (int i = 0; i < 1; i++) {
					LACommisionDetailSchema aLACommisionDetailSchema = new LACommisionDetailSchema();
					aLACommisionDetailSchema.setAgentCode(mLCContSchema
							.getAgentCode());
					aLACommisionDetailSchema.setBusiRate(0);
					aLACommisionDetailSchema.setMakeDate(theCurrentDate);
					aLACommisionDetailSchema.setMakeTime(theCurrentTime);
					aLACommisionDetailSchema.setModifyDate(theCurrentDate);
					aLACommisionDetailSchema.setModifyTime(theCurrentTime);
					aLACommisionDetailSchema.setOperator(mGlobalInput.Operator);
					aLACommisionDetailSchema.setStartSerDate(theCurrentDate);
					aLACommisionDetailSchema.setPolType("0");
					aLACommisionDetailSchema.setAgentGroup("000000");
					aLACommisionDetailSchema.setGrpContNo(mLCContSchema
							.getContNo());
					if (mLACommisionDetailSet == null) {
						mLACommisionDetailSet = new LACommisionDetailSet();
					}
					mLACommisionDetailSet.add(aLACommisionDetailSchema);
				}
			}
			// 修改时先删除再插入
			if("card".equals(this.mark)){
				SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
				sqlbv14.sql("delete from LACommisionDetail where GrpContNo='"
						+ "?getPrtNo?" + "'");
				sqlbv14.put("getPrtNo", mLCContSchema.getPrtNo());
				map.put(sqlbv14, "DELETE");
			}else{
				SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
				sqlbv15.sql("delete from LACommisionDetail where GrpContNo='"
						+ "?GrpContNo?" + "'");
				sqlbv15.put("GrpContNo", mLCContSchema.getContNo());
				map.put(sqlbv15, "DELETE");
			}
			map.put(this.mLACommisionDetailSet, "INSERT");
		}
		if (mOperate.equals("DELETE||CONT")) {
			SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
			sqlbv16.sql("delete from LACommisionDetail where GrpContNo='"
					+ "?GrpContNo?" + "'");
			sqlbv16.put("GrpContNo", mLCContSchema.getContNo());
			map.put(sqlbv16, "DELETE");
		}
		
		//tongmeng 2009-04-27 modify
		//如果修改合同的投保申请日期,并且该合同下已经录入了险种,并且导致被保人的投保年龄发生变化,
		//需要重算保费保额等数据.
		
		if (mOperate.equals("UPDATE||CONT")&&mLCContSchema.getContType().equals("1")) {
			boolean tCValidateFlag = false;
			//boolean tHasPolFlag = false;
			SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
			sqlbv17.sql("select (case when to_char(cvalidate,'yyyy-mm-dd') is null then '' else to_char(cvalidate,'yyyy-mm-dd') end) from lccont where contno='"+"?contno?"+"'");
			sqlbv17.put("contno", this.mLCContSchema.getContNo());
			ExeSQL tExeSQL = new ExeSQL();
			String tOldCValidate = tExeSQL.getOneValue(sqlbv17);
			
			String tCurrentCValiDate = mLCContSchema.getCValiDate();
			if(tCurrentCValiDate==null)
			{
				tCurrentCValiDate = "";
			}
			if(!tCurrentCValiDate.equals(tOldCValidate)
				)
			{
				tCValidateFlag = true;
			}
			//tongmeng 2009-06-22 modify
			//如果职业类别发生变化,也需要重算
			LCAppntDB tOldLCAppntDB = new LCAppntDB();
			tOldLCAppntDB.setContNo(mLCContSchema.getContNo());
			if(!tOldLCAppntDB.getInfo())
			{
				CError.buildErr(this,"查询原投保人信息出错!");
				return false;
			}
			String tOldocc = tOldLCAppntDB.getOccupationType();
			if(tOldocc==null)
			{
				tOldocc = "";
			}
			String tNewocc = this.mLCAppntBL.getOccupationType();
			if(tNewocc==null)
			{
				tNewocc = "";
			}
			
			if(tCValidateFlag||!tOldocc.equals(tNewocc))
			{
				//生效日期发生变化,需要同步更新
				LCPolDB oldLCPolDB = new LCPolDB();
				LCDutyDB oldLCDutyDB = new LCDutyDB();
				LCPolSet oldLCPolSet = new LCPolSet();
				LCDutySet oldLCDutySet = new LCDutySet();
				// 放入map更新的内容。
				LCPolSet newLCPolSet = new LCPolSet();
				LCDutySet newLCDutySet = new LCDutySet();
				LCGetSet newLCGetSet = new LCGetSet();
				LCPremSet newLCPremSet = new LCPremSet();
				// 查询旧的lcpol
				oldLCPolDB.setContNo(this.mLCContSchema.getContNo());
				oldLCPolSet = oldLCPolDB.query();
				if (oldLCPolSet != null && oldLCPolSet.size() >= 1) {
					// 查询每个险种下的所有责任。
					for (int i = 1; i <= oldLCPolSet.size(); i++) {
						LCPolBL recalLCPol = new LCPolBL();
						LCDutyBLSet recalLCDuty = new LCDutyBLSet();
						oldLCDutyDB = new LCDutyDB();
						oldLCDutySet = new LCDutySet();
						oldLCDutyDB.setPolNo(oldLCPolSet.get(i).getPolNo());
						oldLCDutySet = oldLCDutyDB.query();

						if (oldLCDutySet != null && oldLCDutySet.size() >= 1) {// 为lcpol赋上新被保人的相关值
							// ，
							// 供重算使用
							recalLCPol.setSchema(oldLCPolSet.get(i));
							recalLCPol.setInsuredAppAge(PubFun.calInterval(
									recalLCPol.getInsuredBirthday(), mLCContSchema.getCValiDate(), "Y"));
							recalLCPol.setPayEndDate("");
							recalLCPol.setEndDate("");
							recalLCPol.setPayYears("");
							recalLCPol.setYears("");
							//tongmeng 2009-03-23 modify
							//
							if(mLCContSchema.getCValiDate()!=null&&!mLCContSchema.getCValiDate().equals(""))
							{
								recalLCPol
								.setCValiDate(mLCContSchema.getCValiDate());
							}
							else
							{
							recalLCPol
									.setCValiDate(mLCContSchema.getPolApplyDate());
							}
							recalLCPol.setPolApplyDate(mLCContSchema.getPolApplyDate());
							logger.debug("mLCContSchema.getPolApplyDate():"
									+ mLCContSchema.getPolApplyDate());
							for (int j = 1; j <= oldLCDutySet.size(); j++) {// 循环为险种下的每个责任赋新被保人的值
								// ，
								// 供重算使用
								// 。
								oldLCDutySet.get(j).setPayYears("");
								oldLCDutySet.get(j).setYears("");
								oldLCDutySet.get(j).setPayEndDate("");
								oldLCDutySet.get(j).setEndDate("");
								recalLCDuty.add(oldLCDutySet.get(j));
							}
						}
						//tongmeng 2009-06-18 modify
						//重算需要传入GetDutyKind
						String tSQL_Get = "  select max(getdutykind) from lcget "
							            + " where contno='"+"?contno?"+"' "
							            + " and polno = '"+"?polno?"+"' "
	                                    + " and getdutykind is not null and getdutykind<>'0' ";
						SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
						sqlbv18.sql(tSQL_Get);
						sqlbv18.put("contno", recalLCPol.getContNo());
						sqlbv18.put("polno", recalLCPol.getPolNo());
						ExeSQL tempExeSQL = new ExeSQL();
						String tValue = tempExeSQL.getOneValue(sqlbv18);
						LCGetBL tLCGetBL = new LCGetBL();
						CalBL tCalBL = null;
						if(tValue!=null&&!tValue.equals(""))
						{
							tLCGetBL.setGetDutyKind(tValue);
							LCGetBLSet tLCGetBLSet = new LCGetBLSet();
							tLCGetBLSet.add(tLCGetBL);
							tCalBL = new CalBL(recalLCPol, recalLCDuty, tLCGetBLSet,
									"");
						}
						else
						{
							tCalBL = new CalBL(recalLCPol, recalLCDuty, "");
						}
						
						// 重算该险种。
						//CalBL tCalBL = new CalBL(recalLCPol, recalLCDuty, "");
						tCalBL.setOperator(mGlobalInput.Operator);
						if (tCalBL.calPol()) {
							/*
							 * 此处应增加重算后调用投保规则的处理。 当前万能险投保规则与投被保人无关，时间关系暂不处理。
							 */

							// 将重算处理后的险种信息数据放入待更新集合中。
							newLCPolSet.add(tCalBL.getLCPol());
							newLCDutySet.add(tCalBL.getLCDuty());
							newLCGetSet.add(tCalBL.getLCGet());
							newLCPremSet.add(tCalBL.getLCPrem());
						} else {
							CError.buildErr(this,"险种重算失败！");
							return false;
						}
					}
					map.put(newLCPolSet, "DELETE&INSERT");
					map.put(newLCDutySet, "DELETE&INSERT");
					map.put(newLCGetSet, "DELETE&INSERT");
					map.put(newLCPremSet, "DELETE&INSERT");
				}
			}
			
			//2009-06-05  增加对体检表客户号的修改
			String tAppntNoSql = "select appntno from lcappnt where contno='"+"?contno?"+"'";
			SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
			sqlbv19.sql(tAppntNoSql);
			sqlbv19.put("contno", mLCContSchema.getContNo());
			String tAppntNo = tExeSQL.getOneValue(sqlbv19);
			
			LCPENoticeDB tLCPENoticeDB = new LCPENoticeDB();
			LCPENoticeSet tLCPENoticeSet = new LCPENoticeSet();
			tLCPENoticeDB.setContNo(mLCContSchema.getContNo());
			tLCPENoticeDB.setCustomerNo(tAppntNo);
			tLCPENoticeDB.setCustomerType("A");
			tLCPENoticeSet = tLCPENoticeDB.query();
			if(tLCPENoticeSet.size()>0){
				for(int i=1;i<tLCPENoticeSet.size();i++){
					tLCPENoticeSet.get(i).setCustomerNo(mLCAppntBL.getAppntNo());
					tLCPENoticeSet.get(i).setAppName(mLCAppntBL.getAppntName());
					tLCPENoticeSet.get(i).setName(mLCAppntBL.getAppntName());
					tLCPENoticeSet.get(i).setModifyDate(theCurrentDate);
					tLCPENoticeSet.get(i).setModifyTime(theCurrentTime);
				}
				map.put(tLCPENoticeSet, "UPDATE");
			}
			LCPolOriginalDB tLCPolOriginalDB = new LCPolOriginalDB();
			LCPolOriginalSet tLCPolOriginalSet = new LCPolOriginalSet();
			tLCPolOriginalDB.setContNo(mLCContSchema.getContNo());
			tLCPolOriginalSet = tLCPolOriginalDB.query();
			if(tLCPolOriginalSet.size()>0){
				for(int i=1;i<=tLCPolOriginalSet.size();i++){
					tLCPolOriginalSet.get(i).setAppntNo(mLCAppntBL.getAppntNo());
					tLCPolOriginalSet.get(i).setModifyDate(theCurrentDate);
					tLCPolOriginalSet.get(i).setModifyTime(theCurrentTime);
				}
				map.put(tLCPolOriginalSet, "UPDATE");
			}
			//体检结果
			LCPENoticeResultDB tLCPENoticeResultDB = new LCPENoticeResultDB();
			LCPENoticeResultSet tLCPENoticeResultSet = new LCPENoticeResultSet();
			tLCPENoticeResultDB.setContNo(mLCContSchema.getContNo());
			tLCPENoticeResultDB.setCustomerNo(tAppntNo);
			tLCPENoticeResultSet = tLCPENoticeResultDB.query();
			if(tLCPENoticeResultSet.size()>0){
				for(int i=1;i<=tLCPENoticeResultSet.size();i++){
					tLCPENoticeResultSet.get(i).setCustomerNo(mLCAppntBL.getAppntNo());
					tLCPENoticeResultSet.get(i).setName(mLCAppntBL.getAppntName());
					tLCPENoticeResultSet.get(i).setModifyDate(theCurrentDate);
					tLCPENoticeResultSet.get(i).setModifyTime(theCurrentTime);
				}
				map.put(tLCPENoticeResultSet, "UPDATE");
			}
		}
		

		return true;
	}

	public VData getCardResult() {
		return rResult;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			// 全局变量
			mGlobalInput.setSchema((GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 0));
			// 合同表
			mLCContSchema.setSchema((LCContSchema) mInputData
					.getObjectByObjectName("LCContSchema", 0));
			logger.debug("保单录入:LCContSchema.ContType="
					+ mLCContSchema.getContType());
			mLCAppntBL.setSchema((LCAppntSchema) mInputData
					.getObjectByObjectName("LCAppntSchema", 0));
			// mLDPersonSchema.setSchema( (LDPersonSchema) mInputData.
			// getObjectByObjectName("LDPersonSchema", 0));
			mLCAddressSchema.setSchema((LCAddressSchema) mInputData
					.getObjectByObjectName("LCAddressSchema", 0));
			mLCAccountSchema.setSchema((LCAccountSchema) mInputData
					.getObjectByObjectName("LCAccountSchema", 0));
			// mLCInsuredSchema.setSchema( (LCInsuredSchema) mInputData.
			// getObjectByObjectName("LCInsuredSchema", 0));
			// 告知信息
			this.mLCCustomerImpartSet = (LCCustomerImpartSet) mInputData
					.getObjectByObjectName("LCCustomerImpartSet", 0);
			// 多业务员佣金比例
			this.mLACommisionDetailSet = (LACommisionDetailSet) mInputData
					.getObjectByObjectName("LACommisionDetailSet", 0);

			TransferData tTransferData = (TransferData) mInputData
					.getObjectByObjectName("TransferData", 0);
			GrpNo = (String) tTransferData.getValueByName("GrpNo");
			GrpName = (String) tTransferData.getValueByName("GrpName");
			mark = (String) tTransferData.getValueByName("mark");
			KDCheckFlag = (String)tTransferData.getValueByName("KDCheckFlag");
			mOldAppntNo = (String)tTransferData.getValueByName("OldAppntNo");
			logger.debug("卡单标记===in contbl==" + mark);
			//start.注释。MS新契约问题件修改要求无需删除险种，一步即可修改到位。合同相关校验放到checkData中处理。---yeshu
			// ,20071220
			// if (mOperate.equals("UPDATE||CONT"))
			// {
			// LCContDB tLCContDB = new LCContDB();
			// tLCContDB.setContNo(mLCContSchema.getContNo());
			// if (tLCContDB.getInfo() == false)
			// {
			// // @@错误处理
			// this.mErrors.copyAllErrors(tLCContDB.mErrors);
			// return false;
			// }
			// mOldLCCont.setSchema(tLCContDB);
			// if (!mOldLCCont.getPolApplyDate().equals(mLCContSchema.
			// getPolApplyDate()))
			// {
			// String tSQL = "select count(*) from lcpol where contno='" +
			// mOldLCCont.getContNo() + "'";
			// ExeSQL tExeSQL = new ExeSQL();
			// String tPolCount = "0";
			// tPolCount = tExeSQL.getOneValue(tSQL);
			// if (!tPolCount.equals("0"))
			// {
			// CError tError = new CError();
			// tError.moduleName = "ContBL";
			// tError.functionName = "getInputData";
			// tError.errorMessage = "请先删除险种信息，再修改投保日期！";
			// this.mErrors.addOneError(tError);
			// return false;
			// }
			// }
			// }
			// end.---yeshu,20071220
			if (mLCContSchema.getRemark() != null
					&& mLCContSchema.getRemark().length() > 800) {
				// @@错误处理
				CError.buildErr(this,"输入其他声名内容过长!");
				return false;
			}
			return true;
		} catch (Exception ex) {
			CError.buildErr(this,ex.toString());
			return false;

		}

	}

	/**
	 * 检查地址数据是否正确 如果在处理过程中出错或者数据有错误，则返回false,否则返回true
	 */
	private MMap getRelation(String ContRelationNO, String LDPersonRelationNO) {
		MMap rMMap = new MMap();
		ES_DOC_MAINDB mES_DOC_MAINDB = new ES_DOC_MAINDB();
		mES_DOC_MAINDB.setDocCode(mLCContSchema.getPrtNo());
		ES_DOC_MAINSet mES_DOC_MAINSet = mES_DOC_MAINDB.query();
		if (mES_DOC_MAINSet.size() == 0) {
			return null;
		}
		for (int i = 0; i < mES_DOC_MAINSet.size(); i++) {
			ES_DOC_RELATIONSchema mES_DOC_RELATIONSchema = new ES_DOC_RELATIONSchema();
			mES_DOC_RELATIONSchema.setBussNoType("13");
			mES_DOC_RELATIONSchema.setBussNo(ContRelationNO);
			mES_DOC_RELATIONSchema.setBussType(mES_DOC_MAINSet.get(i + 1)
					.getBussType());
			mES_DOC_RELATIONSchema.setSubType(mES_DOC_MAINSet.get(i + 1)
					.getSubType());
			mES_DOC_RELATIONSchema.setRelaFlag("0");
			mES_DOC_RELATIONSchema.setDocID(mES_DOC_MAINSet.get(i + 1)
					.getDocID());
			rMMap.put(mES_DOC_RELATIONSchema, "DELETE&INSERT");
		}
		for (int i = 0; i < mES_DOC_MAINSet.size(); i++) {
			ES_DOC_RELATIONSchema mES_DOC_RELATIONSchema = new ES_DOC_RELATIONSchema();
			mES_DOC_RELATIONSchema.setBussNoType("42");
			mES_DOC_RELATIONSchema.setBussNo(LDPersonRelationNO);
			mES_DOC_RELATIONSchema.setBussType(mES_DOC_MAINSet.get(i + 1)
					.getBussType());
			mES_DOC_RELATIONSchema.setSubType(mES_DOC_MAINSet.get(i + 1)
					.getSubType());
			mES_DOC_RELATIONSchema.setRelaFlag("0");
			mES_DOC_RELATIONSchema.setDocID(mES_DOC_MAINSet.get(i + 1)
					.getDocID());
			rMMap.put(mES_DOC_RELATIONSchema, "DELETE&INSERT");
		}
		return rMMap;
	}

	// --------------add by haopan 2007-03-08----用verifyinput检验效率太低故放到后来类里----//

	/**
	 * 得到处理后的结果集
	 * 
	 * @return 结果集
	 */

	public VData getResult() {
		return mResult;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: void
	 */
	private void prepareOutputData() {
		mInputData.clear();
		mInputData.add(map);
		rResult.clear();
		rResult.add(map);
		mResult.clear();

		mResult.add(mLCInsuredSchema);
		mResult.add(mLCContSchema);
		mResult.add(mLCAppntBL.getSchema());
		mResult.add(mLCAddressSchema);
		mResult.add(mLDPersonSet);
		mResult.add(mLCCustomerImpartSet);
		mResult.add(mLCCustomerImpartParamsSet);
		mResult.add(mLACommisionDetailSet);

	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return: 
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将传入的数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		logger.debug("now in ContBL submit");
		// 将外部传入的数据分解到本类的属性中，准备处理
		// 合并完
		if (this.getInputData() == false) {
			return false;
		}
		logger.debug("---getInputData---");
		// 校验传入的数据
		// if (!mOperate.equals("DELETE||CONT"))
		// {
		// 合并完
		if (this.checkData() == false) {
			return false;
		}
		logger.debug("---checkData---");
		// }

		// 根据业务逻辑对数据进行处理
		logger.debug("---dealData start---");
		// 合并完
		if (this.dealData() == false) {
			return false;
		}
		logger.debug("---dealData  ended---");
		// 装配处理好的数据，准备给后台进行保存
		this.prepareOutputData();
		logger.debug("---prepareOutputData---");

		// 数据提交、保存
		if (mark != null) {
			if (mark.equals("card")) {
				logger.debug("in contbl 是卡单！");
				return true;
			}
		}
		logger.debug("卡单不会执行以下程序！");
		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start tPRnewManualDunBLS Submit...");

		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError.buildErr(this,"数据提交失败!");
			return false;
		}

		logger.debug("---commitData---");
		return true;
	}
	/**
	 * 客户合并
	 * 合并原则：
	   1．姓名、性别、出生日期、证件号码四项与系统中原有客户资料完全一致时，则进行自动合并。
	   2．姓名、性别、出生日期，三项与系统中原有客户资料完全一致，且证件号码因15位或18位与原有客户资料不匹配时，系统自动将15位的号码转为18位后（但只作校验用，系统仍记录原有的15位号码），进行校验，如四项与系统中原有客户资料一致，则进行自动合并。
	   3．姓名、性别、出生日期，三项与系统中原有客户资料完全一致，且机构代码、银行帐号、业务员代码、电话中有任意一项与系统中原有客户资料一致时，进行自动合并。
	   4、姓名、性别、出生日期，三项与系统中原有客户资料完全一致，且有银行账号信息，当录入的“帐号”数字内容完全一致时进行自动合并。
	 * @param tLCPolSchema LCPolSchema
	 * @param tLCAppntIndSchema LCAppntIndSchema
	 * @param tLCInsuredSchema LCInsuredSchema
	 * @param tFlag int 客户号码合并类型：0 投保人客户号合并，1 被保险人客户号合并
	 * @return String 返回已经存在的客户号，如果没有投保，返回空字符串
	 */
	public String getCustomerNo(LCAppntSchema tLCAppntSchema,LCAddressSchema tLCAddressSchema)
	{
	  String tCustomerNo = "";
	  try
	  {
	    String tName = "";  //客户姓名
	    String tSex = "";  //客户性别
	    String tBirthday = "";  //客户出生日期
	    String tIDNoType = "";  //证件类型
	    String tIDNo = "";      //证件号码
	//    String tManagecom = tLCPolSchema.getManageCom();  //管理机构
	//    String tBankAccNo = tLCPolSchema.getBankAccNo();  //银行帐号
	//    String tAgentCode = tLCPolSchema.getAgentCode();  //代理人编码
	    String tPhone = "";     //联系电话1
	    String tPhone2 = "";    //联系电话2
	    String tBankAccNo = ""; //银行账号
	    //0 投保人客户号合并
	    tName = tLCAppntSchema.getAppntName();
	    tSex = tLCAppntSchema.getAppntSex();
	    tBirthday = tLCAppntSchema.getAppntBirthday();
	    tIDNoType = tLCAppntSchema.getIDType();
	    tIDNo = tLCAppntSchema.getIDNo();
	    tPhone = tLCAddressSchema.getMobile();
	    tPhone2 = tLCAddressSchema.getPhone();
	    tBankAccNo = tLCAppntSchema.getBankAccNo();
	    if(tPhone==null||tPhone.equals(""))
	    {
	    	tPhone = " ";
	    }
	    if(tPhone2==null||tPhone2.equals(""))
	    {
	    	tPhone2 = " ";
	    }
	    
	//    else  //1 被保险人客户号合并
	//    {
	//      tName = tLCInsuredSchema.getName();
	//      tSex = tLCInsuredSchema.getSex();
	//      tBirthday = tLCInsuredSchema.getBirthday();
	//      tIDNoType = tLCInsuredSchema.getIDType();
	//      tIDNo = tLCInsuredSchema.getIDNo();
	//      tPhone = tLCInsuredSchema.getPhone();
	//      tPhone2 = tLCInsuredSchema.getPhone2();
	//    }
	    
	    ExeSQL tExeSQL = new ExeSQL();
	    SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
	    sqlbv20.sql(" select to_char(to_date('"+"?tBirthday?"+"','yyyy-mm-dd'),'yyyy-mm-dd') from dual ");
	    sqlbv20.put("tBirthday", tBirthday);
	    tBirthday = tExeSQL.getOneValue(sqlbv20);
		    
	    logger.debug("*********getCustomerNo***********");
	    logger.debug("tName: "+StrTool.GBKToUnicode(tName));
	    logger.debug("tSex: "+tSex);
	    logger.debug("tBirthday: "+tBirthday);
	    logger.debug("tIDNoType: "+tIDNoType);
	    logger.debug("tIDNo: "+tIDNo);
	    logger.debug("tPhone: "+tPhone);
	    logger.debug("tPhone2: "+tPhone2);
	
	//    //1．姓名、性别、出生日期、证件号码四项与系统中原有客户资料完全一致时，则进行自动合并。
	//    logger.debug("******Start Step 1***********");
	//    LDPersonDB tLDPersonDB = new LDPersonDB();
	//    tLDPersonDB.setName(tName);
	//    tLDPersonDB.setSex(tSex);
	//    tLDPersonDB.setBirthday(tBirthday);
	//    tLDPersonDB.setIDNo(tIDNo);
	
	//    LDPersonSet tLDPersonSet = tLDPersonDB.query();
	//    logger.debug("***tLDPersonSet.getName Step 1: " + tLDPersonSet.encode());
	//    if (tLDPersonSet != null && tLDPersonSet.size() > 0)
	//    {
	//      tCustomerNo = tLDPersonSet.get(1).getCustomerNo();
	//    }
	//    logger.debug("******End Step 1 tCustomerNo : "+tCustomerNo);
	    
	    LDPersonSet tLDPersonSet = new LDPersonSet();
	    LDPersonDB tLDPersonDB = new LDPersonDB();
	    if(StrTool.cTrim(tCustomerNo).equals(""))
	    {
	    	 tLDPersonDB = new LDPersonDB();	
//	    	 tLDPersonDB.setName(tName);
//	         tLDPersonDB.setSex(tSex);
//	         tLDPersonDB.setBirthday(tBirthday);
	         String tpersonSql="select * from ldperson a where name='"+"?name?"+"' "
	         					+" and sex='"+"?sex?"+"' "
	         					+" and birthday=to_date('"+"?birthday?"+"','yyyy-mm-dd')"
	         					+" and  exists (select 1 from lcappnt where grpcontno='00000000000000000000' and appntno=a.customerno "
	         		            +" union select 1 from lcinsured where grpcontno='00000000000000000000' and insuredno=a.customerno) ";
	         SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
	         sqlbv1.sql(tpersonSql);
	         sqlbv1.put("name",StrTool.GBKToUnicode(tName));
	         sqlbv1.put("sex",tSex);
	         sqlbv1.put("birthday",tBirthday);
	         
	         logger.debug("判断是否有姓名性别出生日期相同的客户sql: "+tpersonSql);
	         tLDPersonSet = tLDPersonDB.executeQuery(sqlbv1);
	         if ( tLDPersonSet.size() > 0)
	         {
	        	 //首先判断证件号码长度是否18位或者15位
	        	 if((StrTool.cTrim(tIDNo).length()==18||StrTool.cTrim(tIDNo).length()==15))
	        	 {
	        		  //如果是18位或者15位,将18和15位的号码按照身份证转换后校验
	        		  logger.debug("***Step 1 start 证件号码为15或者18位: " + tIDNo);
	        		  LDPersonSet tempLDPersonSet = new LDPersonSet();
	            	  if(StrTool.cTrim(tIDNo).length()==18)
	       	          {
	       		          String customer_sql="select * from ldperson a where name='"+"?name?"+"' and sex='"+"?sex?"+"'"
	       		          					+" and birthday='"+"?birthday?"+"' and (lower(idno)=lower('"+"?idno1?"+"') or lower(idno)=lower('"+"?idno2?"+"') )"
	       		         					+" and  exists (select 1 from lcappnt where grpcontno='00000000000000000000' and appntno=a.customerno "
	       		         		            +" union select 1 from lcinsured where grpcontno='00000000000000000000' and insuredno=a.customerno) ";
	       		          //+" and idtype='0'";
	       		       SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
	       		       sqlbv2.sql(customer_sql);
	       		       sqlbv2.put("name",StrTool.GBKToUnicode(tName));
	       		       sqlbv2.put("sex",tSex);
	       		       sqlbv2.put("birthday",tBirthday);
	       		       sqlbv2.put("idno1",get15IDNo(tIDNo));
	       		       sqlbv2.put("idno2",tIDNo);
	       		          logger.debug("***customer_sql Step 2 : " + customer_sql);
	       		          logger.debug("customer_sql1111111111111:"+customer_sql);
	       		          tempLDPersonSet = tLDPersonDB.executeQuery(sqlbv2);
	       	          }
	       	          else if(StrTool.cTrim(tIDNo).length()==15)
	       	          {
	       		          String customer_sql="select * from ldperson a where name='"+"?name?"+"' and sex='"+"?sex?"+"'"
	       		          +" and birthday='"+"?birthday?"+"' and (lower(idno)=lower('"+"?idno1?"+"') or lower(idno)=lower('"+"?idno2?"+"') )"
	       		          +" and  exists (select 1 from lcappnt where grpcontno='00000000000000000000' and appntno=a.customerno "
	       		          +" union select 1 from lcinsured where grpcontno='00000000000000000000' and insuredno=a.customerno) ";
	       		          //+" and idtype='0'";
	       		          logger.debug("customer_sql2222222222222:"+customer_sql);
	       		          SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
	       		         sqlbv3.sql(customer_sql);
		      	         sqlbv3.put("name",StrTool.GBKToUnicode(tName));
		      	         sqlbv3.put("sex",tSex);
		      	         sqlbv3.put("birthday",tBirthday);
		      	         sqlbv3.put("idno1",get18IDNo(tIDNo,tBirthday));
		      	         sqlbv3.put("idno2",tIDNo);
	       		          tempLDPersonSet = tLDPersonDB.executeQuery(sqlbv3);
	       	          }
	       	          
	       	          if (tempLDPersonSet != null && tempLDPersonSet.size() > 0)
	       	          {
	       	            tCustomerNo = tempLDPersonSet.get(1).getCustomerNo();
	       	          }
	       	          logger.debug("***Step 1 end 证件号码为15或者18位: " + tCustomerNo);
	        	 }
	        	 else
	        	 {		LDPersonSet tempLDPersonSet2 = new LDPersonSet();
	            		for(int i=1;i<=tLDPersonSet.size();i++)
	  		            {
	  			           	if(("2".equals(tIDNoType)&&tIDNoType.equals(StrTool.cTrim(tLDPersonSet.get(i).getIDType()))))
	  			           	{
	  			           	    logger.debug("***Step 2 statr 证件类型同为军官证: " + tIDNoType);
	  			           		if(tIDNo.replaceAll("[^0-9]", "").equals(tLDPersonSet.get(i).getIDNo().replaceAll("[^0-9]", "")))
	  			           		{
	  			           			tCustomerNo = tLDPersonSet.get(i).getCustomerNo();
	  			           		    logger.debug("***Step 2 end succ 证件类型同为军官证: " + tCustomerNo);
	  			           			break;
	  			           		}
	  			           	    logger.debug("***Step 2 end 证件类型同为军官证: " + tCustomerNo);
	  			           	}
	  			           	else if((!("2".equals(tIDNoType)||"8".equals(tIDNoType)||"9".equals(tIDNoType)))&&tIDNoType!=null&&tIDNoType.equals(StrTool.cTrim(tLDPersonSet.get(i).getIDType())))
	  			           	{
	  			           		logger.debug("***Step 3 statr 证件类型与证件号码是否都相同: " + tCustomerNo);
	  			           		String customer_sql="select * from ldperson a where name='"+"?name?"+"' and sex='"+"?sex?"+"'"
	  			           						   +" and birthday='"+"?birthday?"+"' and (lower(idno)=lower('"+"?idno?"+"') and lower(idtype)=lower('"+"?idnoType?"+"') )"
	  			           						   +" and  exists (select 1 from lcappnt where grpcontno='00000000000000000000' and appntno=a.customerno "
	  			           						   +" union select 1 from lcinsured where grpcontno='00000000000000000000' and insuredno=a.customerno) ";
	  			           		logger.debug("customer_sql2222222222222:"+customer_sql);
	  			           		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
	  			           		sqlbv4.sql(customer_sql);
	  			           		sqlbv4.put("name",StrTool.GBKToUnicode(tName));
	  			           		sqlbv4.put("sex",tSex);
	  			           		sqlbv4.put("birthday",tBirthday);
	  			           		sqlbv4.put("idno",StrTool.GBKToUnicode(tName));
	  			           		sqlbv4.put("idnoType",tIDNoType);
	  			           		tempLDPersonSet2 = tLDPersonDB.executeQuery(sqlbv4);
	  			           		
	  			           		if (tempLDPersonSet2 != null && tempLDPersonSet2.size() > 0)
	  			           		{
	  			           			tCustomerNo = tempLDPersonSet2.get(1).getCustomerNo();
	  			           			logger.debug("***Step 3 end succ 证件号码与证件类型都相同: " + tCustomerNo);
	  			           			break;
	  			           		}
	  			           		
//		  			           	if(tIDNo.toUpperCase().equals(tLDPersonSet.get(i).getIDNo().toUpperCase())){
//	  			           			tCustomerNo = tLDPersonSet.get(i).getCustomerNo();
//	  			           			break;
//	  			           		}
	  			           	}
//	  			           	else
//	  			           	{
//	  			           		logger.debug("***Step 4 statr 是否有电话一致: " + tCustomerNo);
	  			           		String customer_sql="select * from ldperson a where name='"+"?name?"
	  			           			+"' and sex='"+"?sex?"
	  			           			+"' and birthday='"+"?birthday?"
	  			           			+"' and '"+"?phone2?"
	  			           			+"' in (select replace(phone, '无', '') from lcaddress where customerno=a.customerno)"
	  			           			+ " and '"+"?phone2?"+"' is not null and '"+"?phone2?"+"' <>' ' "
		         					+" and  exists (select 1 from lcappnt where grpcontno='00000000000000000000' and appntno=a.customerno "
		         		            +" union select 1 from lcinsured where grpcontno='00000000000000000000' and insuredno=a.customerno) "
	  			           			+" union "
	  			           			+" select * from ldperson a where name='"+"?name?"
	  			           			+"' and sex='"+"?sex?"
	  			           			+"' and birthday='"+"?birthday?"
	  			           			+"' and '"+"?phone2?"
	  			           			+"' in (select replace(mobile, '无', '') from lcaddress where customerno=a.customerno)"
	  			           			+ " and '"+"?phone2?"+"' is not null and '"+"?phone2?"+"' <>' ' "
		         					+" and  exists (select 1 from lcappnt where grpcontno='00000000000000000000' and appntno=a.customerno "
		         		            +" union select 1 from lcinsured where grpcontno='00000000000000000000' and insuredno=a.customerno) "
	  			           			+" union "
	  			           			+" select * from ldperson a where name='"+"?name?"
				           			+"' and sex='"+"?sex?"
				           			+"' and birthday='"+"?birthday?"
				           			+"' and '"+"?phone?"
				           			+"' in (select replace(phone, '无', '') from lcaddress where customerno=a.customerno)"
				           			+ " and '"+"?phone?"+"' is not null and '"+"?phone?"+"' <>' ' "
		         					+" and  exists (select 1 from lcappnt where grpcontno='00000000000000000000' and appntno=a.customerno "
		         		            +" union select 1 from lcinsured where grpcontno='00000000000000000000' and insuredno=a.customerno) "
				           			+" union "
				           			+" select * from ldperson a where name='"+"?name?"
				           			+"' and sex='"+"?sex?"
				           			+"' and birthday='"+"?birthday?"
				           			+"' and '"+"?phone?"
				           			+"' in (select replace(mobile, '无', '') from lcaddress where customerno=a.customerno)"
				           			+ " and '"+"?phone?"+"' is not null and '"+"?phone?"+"' <>' ' "
		         					+" and  exists (select 1 from lcappnt where grpcontno='00000000000000000000' and appntno=a.customerno "
		         		            +" union select 1 from lcinsured where grpcontno='00000000000000000000' and insuredno=a.customerno) "
				           			;
	  			           			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
	  			           			sqlbv5.sql(customer_sql);
	  			           			sqlbv5.put("name", StrTool.GBKToUnicode(tName));
	  			           			sqlbv5.put("sex", tSex);
	  			           			sqlbv5.put("birthday", tBirthday);
	  			           			sqlbv5.put("phone", tPhone);
	  			           			sqlbv5.put("phone2", tPhone2);
			  			           	tempLDPersonSet2 = tLDPersonDB.executeQuery(sqlbv5);
		  			           		logger.debug("判断是否有电话一致sql："+customer_sql);
		  			           		if (tempLDPersonSet2 != null && tempLDPersonSet2.size() > 0)
		  			           		{
		  			           			tCustomerNo = tempLDPersonSet2.get(1).getCustomerNo();
		  			           			logger.debug("***Step 4 end succ 是否有电话一致: " + tCustomerNo);
		  			           			break;
		  			           		}
		  			           		logger.debug("***Step 4 end 是否有电话一致: " + tCustomerNo);
//		  			           	if(StrTool.cTrim(tPhone).equals(tLDPersonSet.get(i))||StrTool.cTrim(tPhone).equals(tLDPersonSet.get(i).getPhone2())||
//					    				  StrTool.cTrim(tPhone2).equals(tLDPersonSet.get(i).getPhone())||StrTool.cTrim(tPhone2).equals(tLDPersonSet.get(i).getPhone2()))
//					    		{
//						    		tCustomerNo = tLDPersonSet.get(i).getCustomerNo();
//						    		logger.debug("***Step 3 end succ  是否有电话一致: " + tCustomerNo);
//					          		break;
//					    		}
		  			           	//2010-4-13 增加自动合并规则
		  			           	//有银行账号信息并且银行账号一致
		  			           	String tBankSql = "select * from ldperson a  where name = '"+"?name?"+"'"
		  			           		+ " and sex = '"+"?sex?"+"' and birthday = '"+"?birthday?"+"'"
		  			           		+ " and ('"+"?tBankAccNo?"+"' in (select bankaccno from lcappnt "
		  			           		+ " where appntno = a.customerno )"
		  			           		+ " or '"+"?BankAccNo?"+"' in (select bankaccno from lcinsured "
		  			           		+ " where insuredno = a.customerno))"
		  			           		+ " and '"+"?BankAccNo?"+"' is not null"
		  			           		+ " and '"+"?BankAccNo?"+"' <> ' '"
		  			           		+ " and exists (select 1 from lcappnt"
		  			           		+ " where grpcontno = '00000000000000000000'"
		  			           		+ " and appntno = a.customerno union select 1"
		  			           		+ " from lcinsured where grpcontno = '00000000000000000000'"
		  			           		+ " and insuredno = a.customerno)";
		  			          SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			           			sqlbv6.sql(tBankSql);
			           			sqlbv6.put("name", StrTool.GBKToUnicode(tName));
			           			sqlbv6.put("sex", tSex);
			           			sqlbv6.put("birthday", tBirthday);
			           			sqlbv6.put("tBankAccNo", tBankAccNo);
		  			          tempLDPersonSet2 = tLDPersonDB.executeQuery(sqlbv6);
		  			          logger.debug("判断是否有银行账号一致sql："+customer_sql);
		  			          if(tempLDPersonSet2 != null && tempLDPersonSet2.size() > 0){
		  			        	tCustomerNo = tempLDPersonSet2.get(1).getCustomerNo();
  			           			logger.debug("***Step 5 end succ 是否有银行账号一致: " + tCustomerNo);
  			           			break;
		  			          }
	  			           	}
	  		            }
	        	 //}
	         }
	         
	    }
	
	  }
	  catch(Exception ex)
	  {
	    ex.printStackTrace();
	    logger.debug("客户合并时发生异常××××");
	  }
	  logger.debug("******End  tCustomerNo : "+tCustomerNo);
	  return tCustomerNo;
	
	}
	
	public String get15IDNo(String IDNo) {
		String str = "";
		str += IDNo.substring(0, 6);
		str += IDNo.substring(8, 17);
		return str;
	}
	
	public static String get18IDNo(String IDNo, String Birthday) {
		if (IDNo.length() == 18) {
			if (IDNo.endsWith("x"))
				IDNo = IDNo.substring(0, 17) + "X";
			return IDNo;
		}
		String str = "";
		str += IDNo.substring(0, 6);
		if (Birthday.length() == 10) {
			str += Birthday.substring(0, 2);
		} else
			str += "19";
		str += IDNo.substring(6, 15);
		int n = 0;
		int[] weight = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
				8, 4, 2 };
		for (int i = 0; i < 17; i++) {
			n += Integer.parseInt(str.substring(i, i + 1)) * weight[i];
		}
		n %= 11;
		if (n == 0)
			str += "1";
		else if (n == 1)
			str += "0";
		else if (n == 2)
			str += "X";
		else if (n == 3)
			str += "9";
		else if (n == 4)
			str += "8";
		else if (n == 5)
			str += "7";
		else if (n == 6)
			str += "6";
		else if (n == 7)
			str += "5";
		else if (n == 8)
			str += "4";
		else if (n == 9)
			str += "3";
		else if (n == 10)
			str += "2";
		return str;
	}
	

}

