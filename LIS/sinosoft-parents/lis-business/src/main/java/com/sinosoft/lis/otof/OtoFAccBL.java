package com.sinosoft.lis.otof;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashSet;

import com.sinosoft.lis.db.LITranInfoDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LITranInfoSchema;
import com.sinosoft.lis.schema.LIVertifySchema;
import com.sinosoft.lis.schema.OFInterfaceSchema;
import com.sinosoft.lis.vschema.LITranErrSet;
import com.sinosoft.lis.vschema.LITranInfoSet;
import com.sinosoft.lis.vschema.LIVertifySet;
import com.sinosoft.lis.vschema.OFInterfaceSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class OtoFAccBL {
private static Logger logger = Logger.getLogger(OtoFAccBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private String manageCom = "";
	private String riskCode = "";
	private String mRiskName = "";
	private String bDate = "";
	private String eDate = "";
	private GlobalInput mGlobalInput = new GlobalInput();
	private String BF = ""; // 保费收入
	private String LX = ""; // 保单利息
	private String GL = ""; // 保单管理费
	private String FXGL = ""; // 风险管理费
	private String TB = ""; // 退保金
	private String SW = ""; // 死亡给付
	private String MQ = ""; // 满期给付
	private String TBFY = ""; // 退保费用
	private String CSFY = ""; // 账户初始费用
	private double AccountNet = 0.00; // 账户净值
	private TextTag mTextTag = new TextTag();

	private double tmoney = 0.00; // >金额</money>
	private double money = 0.00;
	private String tMoney = ""; // >金额</money>
	private int mTime = 0;
	private LIVertifySet mLIVertifySet = new LIVertifySet(); // 科目约束性
	private String mManageCom = "";
	private String mToday = "";
	private String mBillID = ""; // 对应凭证科目，便于查找 ==
	// 凭证类别+凭证分类(一张凭证内部有多个分类的情况)+内部编码
	private String mReversedStatus = ""; // 冲消状态
	private String mOrigRowID = ""; // 被冲消的行
	private String mReversedRowID = ""; // 冲消生成的行
	private String mCurrencyCode = ""; // 币别
	private String mVoucherType = ""; // 凭证类别
	private String mSegment2 = ""; // 成本中心
	private String mAccountCode = ""; // 科目代码
	private String mAccountSubCode = ""; // 科目明细代码
	private String mRiskCode = "";
	private String mSaleChnl = "03"; // 销售渠道
	private String mTransDate = ""; // 事务日期
	private String mAccountingDate = ""; // 记帐日期
	private int mMatchID = 0; // 借贷关系key值
	private String mBatchNo = ""; // 批次号
	private String mEnteredDR = ""; // 事务借计金额
	private String mEnteredCR = ""; // 事务贷计金额
	private String mHeadDescription = ""; // 日记帐摘要
	private String mLineDescription = ""; // 行记帐摘要

	private String mPolNo = ""; // 保单号
	private String mInsuredName = ""; // 被保险人姓名
	private String mBussNo = ""; // 收付款单据号
	private String mAttribute5 = ""; // 行为明细类别
	private String mAttribute6 = ""; // 代理机构（）
	/** 往后面传输数据的容器 */
	private VData mInputData;

	// 对表的定义
	OFInterfaceSet mOFInterfaceSet = new OFInterfaceSet();
	LITranInfoSet mLITranInfoSet = new LITranInfoSet();
	LITranErrSet mLITranErrSet = new LITranErrSet();

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("Begin OtoFAccBL-----");
		this.mOperate = cOperate;
		mInputData = (VData) cInputData.clone();
		try {
			if (!getInputData(cInputData))
				return false;
			if (!dealData())
				return false;

			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr("submitData", "发生异常");
			return false;
		}
	}

	public boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		TransferData mTransferData = new TransferData();
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		manageCom = (String) mTransferData.getValueByName("ManageCom");
		bDate = (String) mTransferData.getValueByName("bDate");
		eDate = (String) mTransferData.getValueByName("eDate");
		if (mGlobalInput == null) {
			CError.buildErr("getInputData", "没有获取登录信息！");
			return false;
		}

		if (mTransferData == null) {
			CError.buildErr("getInputData", "获取前台信息出错！");
			return false;
		}

		if (manageCom == null || manageCom.equals("")) {
			CError.buildErr("getInputData", "没有获取管理机构信息！");
			return false;
		}

		if (bDate == null || bDate.equals("")) {
			CError.buildErr("getInputData", "没有获取起始日期信息！");
			return false;
		}
		if (eDate == null || eDate.equals("")) {
			CError.buildErr("getInputData", "没有获取终止日期信息！");
			return false;
		}

		if ("Print".equals(mOperate)) {
			riskCode = (String) mTransferData.getValueByName("RiskCode");
			if (riskCode == null || riskCode.equals("")) {
				CError.buildErr("getInputData", "没有获取险种信息！");
				return false;
			}
		}

		if ("Certifi".equals(mOperate)) {
			riskCode = (String) mTransferData.getValueByName("RiskCode");
			Integer itemp = (Integer) mTransferData.getValueByName("tVouchNo");
			mTime = itemp.intValue();
			mAccountingDate = (String) mTransferData
					.getValueByName("tAccountDate");

		}
		return true;
	}

	public boolean dealData() {
		if ("Print".equals(mOperate)) {
			if (!getQueryData()) {
				CError.buildErr("getQueryData", "查询账户信息出错！");
				return false;
			}
			return true;

		}

		if ("Certifi".equals(mOperate)) {
			FDate chgdate = new FDate();
			Date dbdate = chgdate.getDate(bDate);
			Date dedate = chgdate.getDate(eDate);
			mBatchNo = PubFun1.CreateMaxNo("OTOF", "20");
			while (dbdate.compareTo(dedate) <= 0) {
				mToday = chgdate.getString(dbdate);
				// 准备所有要打印的数据
				if (!getVoucherData()) {
					CError.buildErr("getQueryData", "查询账户信息出错！");
					return false;
				}
				dbdate = PubFun.calDate(dbdate, 1, "D", null);
			}

			prepareOutputData();
			OtoFBLS tOtoFBLS = new OtoFBLS();
			logger.debug("Start OtoFBLS Submit...");

			if (!tOtoFBLS.submitData(mInputData, mOperate)) {
				// @@错误处理
				this.mErrors.copyAllErrors(tOtoFBLS.mErrors);
				logger.debug("Errors : "
						+ tOtoFBLS.mErrors.getFirstError());

				CError tError = new CError();
				tError.moduleName = "OtoFAccBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return true;
	}

	public boolean getQueryData() {
		logger.debug("Begin Print-----");
		ExeSQL tExeSQL = new ExeSQL();
		String QSql = "";
		mTextTag.add("bDate", bDate);
		mTextTag.add("eDate", eDate);
		mTextTag.add("ManageCom", manageCom);
		QSql = "select riskname from lmriskapp where riskcode='?riskCode?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(QSql);
		sqlbv.put("riskCode",riskCode);
		mRiskName = tExeSQL.getOneValue(sqlbv);
		mTextTag.add("RiskName", mRiskName);
		// BF--保费收入
		// 2008-6-4 zy 增加犹豫期撤单产生的冲减保费
		QSql = " select sum(money) from lcinsureacctrace where moneytype in ( 'BF','WT') and riskcode = '?riskCode?' and managecom like concat('?manageCom?','%') and makedate>='?bDate?' and makedate<='?eDate?' ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(QSql);
		sqlbv1.put("riskCode",riskCode);
		sqlbv1.put("manageCom",manageCom);
		sqlbv1.put("bDate",bDate);
		sqlbv1.put("eDate", eDate);
		BF = tExeSQL.getOneValue(sqlbv1);
		if (BF == null || BF.equals("")) {
			BF = "0";
		}

		// mTextTag.add("Prem",new
		// DecimalFormat("0.00").format(Double.parseDouble(BF)));
		// AccountNet = AccountNet + Double.parseDouble(BF);
		// logger.debug("保费收入:"+BF);
		// logger.debug("账户净值:"+AccountNet);
		// AccInitCost--账户初始费用
		QSql = "select sum(fee) from lcinsureaccfeetrace where feecode like '%01' and riskcode = '?riskCode?' and managecom like concat('?manageCom?','%') and makedate>='?bDate?' and makedate<='?eDate?' ";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(QSql);
		sqlbv2.put("riskCode", riskCode);
		sqlbv2.put("manageCom", manageCom);
		sqlbv2.put("bDate", bDate);
		sqlbv2.put("eDate", eDate);
		CSFY = tExeSQL.getOneValue(sqlbv2);
		if (CSFY == null || CSFY.equals("")) {
			CSFY = "0";
		}
		// 由于账户表中的保费收入为已经减去账户初始费用的保费收入，所以需要将帐户表中保费收入加上初始费用为保费收入
		BF = String.valueOf(Double.parseDouble(BF) + Double.parseDouble(CSFY));
		mTextTag.add("Prem", new DecimalFormat("0.00").format(Double
				.parseDouble(BF)));
		AccountNet = AccountNet + Double.parseDouble(BF);
		logger.debug("保费收入:" + BF);
		logger.debug("账户净值:" + AccountNet);

		mTextTag.add("AccInitCost", new DecimalFormat("0.00").format(Double
				.parseDouble(CSFY)));
		AccountNet = AccountNet - Double.parseDouble(CSFY);
		logger.debug("初始管理费:" + CSFY);
		logger.debug("账户净值:" + AccountNet);

		// GL--保单管理费用
		QSql = "select sum(-money) from lcinsureacctrace where moneytype = 'GL' and riskcode = '?riskCode?' and managecom like concat('?manageCom?','%') and makedate>='?bDate?' and makedate<='?eDate?' ";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(QSql);
		sqlbv3.put("riskCode", riskCode);
		sqlbv3.put("manageCom", manageCom);
		sqlbv3.put("bDate", bDate);
		sqlbv3.put("eDate", eDate);
		GL = tExeSQL.getOneValue(sqlbv3);
		if (GL == null || GL.equals("")) {
			GL = "0.00";
		}
		mTextTag.add("PolAdmiCost", new DecimalFormat("0.00").format(Double
				.parseDouble(GL)));
		AccountNet = AccountNet - Double.parseDouble(GL);
		logger.debug("保单管理费:" + GL);
		logger.debug("账户净值:" + AccountNet);

		// FXGL--风险管理费用
		FXGL = "0";
		mTextTag.add("RiskAdmiCost", new DecimalFormat("0.00").format(Double
				.parseDouble(FXGL)));
		AccountNet = AccountNet - Double.parseDouble(FXGL);
		logger.debug("风险管理费用:" + FXGL);
		logger.debug("账户净值:" + AccountNet);
		// 2008-5-20 zy 取消利息收入
		// LX--保单利息
		// QSql = "select sum(money) from lcinsureacctrace where moneytype = 'LX' and
		// riskcode = '"+riskCode+"'and managecom like '"+manageCom+"%' and
		// makedate>='"+bDate+"' and makedate<='"+eDate+"' ";
		// LX = tExeSQL.getOneValue(QSql);
		// if(LX==null || LX.equals(""))
		// {
		// LX="0.00";
		// }
		// mTextTag.add("PolInterest", new
		// DecimalFormat("0.00").format(Double.parseDouble(LX)));
		// AccountNet = AccountNet + Double.parseDouble(LX);
		// logger.debug("保单利息:"+LX);
		// logger.debug("账户净值:"+AccountNet);

		// TB--退保金
		QSql = "select sum(-money)from lcinsureacctrace where moneytype = 'TB'and riskcode = '?riskCode?' and managecom like concat('?manageCom?','%')  and makedate>='?bDate?' and makedate<='?eDate?' ";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(QSql);
		sqlbv4.put("riskCode", riskCode);
		sqlbv4.put("manageCom", manageCom);
		sqlbv4.put("bDate", bDate);
		sqlbv4.put("eDate", eDate);
		TB = tExeSQL.getOneValue(sqlbv4);
		if (TB == null || TB.equals("")) {
			TB = "0.00";
		}
		mTextTag.add("TBMoney", new DecimalFormat("0.00").format(Double
				.parseDouble(TB)));
		AccountNet = AccountNet - Double.parseDouble(TB);
		logger.debug("退保金:" + TB);
		logger.debug("账户净值:" + AccountNet);
		// DeathDraw--死亡给付
//		QSql = "select sum(-money)from lcinsureacctrace where moneytype = 'LP'and riskcode = '"
//				+ riskCode
//				+ "'and managecom like '"
//				+ manageCom
//				+ "%'  and makedate>='"
//				+ bDate
//				+ "' and makedate<='"
//				+ eDate
//				+ "' ";
		//zy 2009-07-09 由于理赔不进入账户，所以调整理赔给付的查询逻辑
		QSql = " select sum(sumgetmoney) from ljaget a  where othernotype='5' and exists(select 1 from ljagetclaim where  "
			 + "actugetno = a.actugetno and riskcode = '?riskCode?' and managecom like concat('?manageCom?','%')  "
			 + "and makedate>='?bDate?' and makedate<='?eDate?' )";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(QSql);
		sqlbv5.put("riskCode", riskCode);
		sqlbv5.put("manageCom", manageCom);
		sqlbv5.put("bDate", bDate);
		sqlbv5.put("eDate", eDate);
		SW = tExeSQL.getOneValue(sqlbv5);
		if (SW == null || SW.equals("")) {
			SW = "0.00";
		}
		mTextTag.add("DeathDraw", new DecimalFormat("0.00").format(Double
				.parseDouble(SW)));
		AccountNet = AccountNet - Double.parseDouble(SW);
		logger.debug("死亡给付:" + SW);
		logger.debug("账户净值:" + AccountNet);
		// ExpireDraw--满期给付
		MQ = "0";
		mTextTag.add("ExpireDraw", new DecimalFormat("0.00").format(Double
				.parseDouble(MQ)));
		AccountNet = AccountNet - Double.parseDouble(MQ);
		logger.debug("满期给付:" + MQ);
		logger.debug("账户净值:" + AccountNet);

		// TBFY--退保费用及部分领取费用
		QSql = "select sum(-money) from lcinsureacctrace where moneytype = 'TBFY' and riskcode = '?riskCode?' and managecom like concat('?managecom?','%')  and makedate>='?bDate?' and makedate<='?eDate?' ";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(QSql);
		sqlbv6.put("riskCode", riskCode);
		sqlbv6.put("manageCom", manageCom);
		sqlbv6.put("bDate", bDate);
		sqlbv6.put("eDate", eDate);
		TBFY = tExeSQL.getOneValue(sqlbv6);
		if (TBFY == null || TBFY.equals("")) {
			TBFY = "0.00";
		}
		mTextTag.add("TBFY", new DecimalFormat("0.00").format(Double
				.parseDouble(TBFY)));
		AccountNet = AccountNet - Double.parseDouble(TBFY);
		logger.debug("退保费用及部分领取费用:" + TBFY);
		// AccountNet--账户净值
		logger.debug("账户净值:" + AccountNet);
		mTextTag
				.add("AccountNet", new DecimalFormat("0.00").format(AccountNet));

		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("UniAccount.vts", "PRINT"); // 最好紧接着就初始化xml文档
		if (mTextTag.size() > 0) {
			xmlexport.addTextTag(mTextTag);
		}
		mResult.addElement(xmlexport);
		logger.debug("添加完毕");
		return true;
	}

	public boolean getVoucherData() {
		String rSql = "";
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		if (riskCode == null || "".equals(riskCode)) {
			rSql = "select distinct riskcode from lcinsureacctrace where makedate='?mToday?'  order by riskcode";
			sqlbv7.sql(rSql);
			sqlbv7.put("mToday",mToday);
		} else {
			rSql = "select distinct riskcode from lcinsureacctrace where makedate='?mToday?' and riskcode='?riskCode?'  order by riskcode";
			sqlbv7.sql(rSql);
			sqlbv7.put("mToday",mToday);
			sqlbv7.put("riskCode",riskCode);
		}
		ExeSQL rExeSQL = new ExeSQL();
		SSRS rSSRS = new SSRS();
		rSSRS = rExeSQL.execSQL(sqlbv7);
		for (int i = 1; i <= rSSRS.getMaxRow(); i++) {
			String tRiskCode = rSSRS.GetText(i, 1);
			initVar();
			mMatchID++;
			mBussNo = tRiskCode + "||" + mToday.substring(0, 4).trim()
					+ mToday.substring(5, 7).trim() + mToday.substring(8, 10);
			;
			mPolNo = mBussNo;
			mAccountingDate = (mAccountingDate.equals("")) ? mToday
					: mAccountingDate;
			mTransDate = mToday;
			mSegment2 = "01020000"; // 总公司投资部，没有设置业务管理机构，采用财务管理机构
			mManageCom = "010200";
			mRiskCode = tRiskCode; // 该凭证需要带险种、渠道
			// mRiskCode="NA";
			money = calMoney(tRiskCode);
			if (money == 0)
				continue;
			tMoney = new DecimalFormat("0.00").format(money);

			for (int j = 1; j <= 2; j++) {
				if (j == 1) {
					if (money < 0) {
						mBillID = "1401";
						// mAccountCode = "系统内往来-万能险资金划拨-公司账户";
						mAccountCode = "内部往来-万能险资金划拨-公司账户";
						mEnteredDR = String.valueOf(tMoney);
						mEnteredCR = "";
					} else {
						mBillID = "1403";
						// mAccountCode = "系统内往来-万能险资金划拨-独立账户";
						mAccountCode = "内部往来-万能险资金划拨-独立账户";
						mEnteredDR = String.valueOf(tMoney);
						mEnteredCR = "";
					}

				} else {
					if (money < 0) {
						mBillID = "1402";
						// mAccountCode = "系统内往来-万能险资金划拨-独立账户";
						mAccountCode = "内部往来-万能险资金划拨-独立账户";
						mEnteredDR = "";
						mEnteredCR = String.valueOf(tMoney);
					} else {

						mBillID = "1404";
						// mAccountCode = "系统内往来-万能险资金划拨-公司账户";
						mAccountCode = "内部往来-万能险资金划拨-公司账户";
						mEnteredDR = "";
						mEnteredCR = String.valueOf(tMoney);
					}
				}

				mSaleChnl = "03"; // 目前只针对万能险
				// mSaleChnl="NA";
				mHeadDescription = "万能险账户||" + mBussNo;

				if (isExitInTab(mBussNo, mBussNo, mBillID))
					break;
				OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
				if (tOFInterfaceSchema != null)
					mOFInterfaceSet.add(tOFInterfaceSchema);
				dealLITranInfo();
			}
		}

		return true;
	}

	// 预留出对接口流转表的操作
	private void prepareOutputData() {
		mInputData.clear();
		mInputData.add(String.valueOf(mTime)); // 增加数据库插入的并发控制
		mInputData.add(manageCom);
		mInputData.add(mTransDate);
		mInputData.add(mLITranInfoSet);
		mInputData.add(mLITranErrSet);
		mInputData.add(mOFInterfaceSet);
	}

	public double calMoney(String riskcode) {
		ExeSQL tExeSQL = new ExeSQL();
		String tSql = "";
		tmoney = 0.00;
		// BF--保费收入
		tSql = " select sum(money) from lcinsureacctrace where moneytype in ('BF','WT') 	and riskcode = '?riskcode?'  and makedate>='?bDate?' and makedate<='?eDate?' ";
		SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
		sqlbv8.sql(tSql);
		sqlbv8.put("riskcode", riskcode);
		sqlbv8.put("bDate", bDate);
		sqlbv8.put("eDate", eDate);
		BF = tExeSQL.getOneValue(sqlbv8);
		if (BF == null || BF.equals("")) {
			BF = "0";
		}
		tmoney = tmoney + Double.parseDouble(BF);
		logger.debug("保费收入:" + tmoney);

		// AccInitCost--账户初始费用
		tSql = "select sum(fee) from lcinsureaccfeetrace where feecode like '%01' and riskcode = '?riskcode?'  and makedate>='?bDate?' and makedate<='?eDate?' ";
		SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
		sqlbv9.sql(tSql);
		sqlbv9.put("riskcode", riskcode);
		sqlbv9.put("bDate", bDate);
		sqlbv9.put("eDate", eDate);
		CSFY = tExeSQL.getOneValue(sqlbv9);
		if (CSFY == null || CSFY.equals("")) {
			CSFY = "0";
		}
		// 账户表中的保费收入为已扣除初始费用的保费收入，所以不需要再次扣除初始费用
		// tmoney = tmoney - Double.parseDouble(CSFY);
		// logger.debug("账户初始费用:"+tmoney);
		// GL--保单管理费用
		tSql = "select sum(-money) from lcinsureacctrace where moneytype = 'GL' and riskcode = '?riskcode?'  and makedate>='?bDate?' and makedate<='?eDate?' ";
		SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
		sqlbv10.sql(tSql);
		sqlbv10.put("riskcode", riskcode);
		sqlbv10.put("bDate", bDate);
		sqlbv10.put("eDate", eDate);
		GL = tExeSQL.getOneValue(sqlbv10);
		if (GL == null || GL.equals("")) {
			GL = "0.00";
		}
		tmoney = tmoney - Double.parseDouble(GL);
		logger.debug("保单管理费用:" + tmoney);
		// FXGL--风险管理费用
		FXGL = "0";
		tmoney = tmoney - Double.parseDouble(FXGL);
		logger.debug("风险管理费用:" + tmoney);
		// //LX--保单利息
		// tSql = "select sum(money) from lcinsureacctrace where moneytype = 'LX' and
		// riskcode = '"+riskcode+"' and makedate>='"+bDate+"' and makedate<='"+eDate+"'
		// ";
		// LX = tExeSQL.getOneValue(tSql);
		// if(LX==null || LX.equals(""))
		// {
		// LX="0.00";
		// }
		// tmoney = tmoney + Double.parseDouble(LX);
		// logger.debug("保单利息:"+tmoney);
		//		
		// TB--退保金
		tSql = "select sum(-money)from lcinsureacctrace where moneytype = 'TB'and riskcode = '?riskcode?'  and makedate>='?bDate?' and makedate<='?eDate?' ";
		SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
		sqlbv11.sql(tSql);
		sqlbv11.put("riskcode", riskcode);
		sqlbv11.put("bDate", bDate);
		sqlbv11.put("eDate", eDate);
		TB = tExeSQL.getOneValue(sqlbv11);
		if (TB == null || TB.equals("")) {
			TB = "0.00";
		}
		tmoney = tmoney - Double.parseDouble(TB);
		logger.debug("保费收入:" + tmoney);
		// DeathDraw--死亡给付
		tSql = "select sum(-money)from lcinsureacctrace where moneytype = 'LP'and riskcode = '?riskcode?'  and makedate>='?bDate?' and makedate<='?eDate?' ";
		SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
		sqlbv12.sql(tSql);
		sqlbv12.put("riskcode", riskcode);
		sqlbv12.put("bDate", bDate);
		sqlbv12.put("eDate", eDate);
		SW = tExeSQL.getOneValue(sqlbv12);
		if (SW == null || SW.equals("")) {
			SW = "0.00";
		}
		mTextTag.add("DeathDraw", new DecimalFormat("0.00").format(Double
				.parseDouble(SW)));
		tmoney = tmoney - Double.parseDouble(SW);
		logger.debug("死亡给付:" + tmoney);
		// ExpireDraw--满期给付
		MQ = "0";
		tmoney = tmoney - Double.parseDouble(MQ);
		logger.debug("满期给付:" + tmoney);
		// TBFY--退保费用及部分领取费用
		tSql = "select sum(-money) from lcinsureacctrace where moneytype = 'TBFY' and riskcode = '?riskcode?'  and makedate>='?bDate?' and makedate<='?eDate?' ";
		SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
		sqlbv13.sql(tSql);
		sqlbv13.put("riskcode", riskcode);
		sqlbv13.put("bDate", bDate);
		sqlbv13.put("eDate", eDate);
		TBFY = tExeSQL.getOneValue(sqlbv13);
		if (TBFY == null || TBFY.equals("")) {
			TBFY = "0.00";
		}
		tmoney = tmoney - Double.parseDouble(TBFY);
		logger.debug("退保费用及部分领取费用:" + tmoney);
		logger.debug("账户净值:" + tmoney);
		return tmoney;
	}

	public VData getResult() {
		return mResult;
	}

	private void initVar() {
		mReversedStatus = "0"; // 冲消状态
		mOrigRowID = ""; // 被冲消的行
		mReversedRowID = ""; // 冲消生成的行
		mCurrencyCode = "CNY"; // 币别
		mVoucherType = String.valueOf(mTime); // 凭证类别
		mManageCom = "NA"; // 核算单位代码
		mSegment2 = "NA"; // 成本中心
		mAccountCode = "NA"; // 科目代码
		mAccountSubCode = "NA"; // 科目明细代码
		mSaleChnl = "NA"; // 销售渠道
		mRiskCode = "NA"; // 保险产品代码
		mTransDate = ""; // 事务日期
		mAccountingDate = (mAccountingDate.equals("")) ? mToday
				: mAccountingDate; // 记帐日期
		// mMatchID = 0; //借贷关系key值
		// mBatchNo = ""; //批次号
		mEnteredDR = ""; // 事务借计金额
		mEnteredCR = ""; // 事务贷计金额
		mHeadDescription = "NA"; // 日记帐摘要
		mLineDescription = "NA"; // 行记帐摘要
		// mCurrencyConversionDate = ""; //汇率日期
		// mCurrencyConversionRate = "1"; //汇率
		// mAccountedDR = ""; //记帐借计金额
		// mAccountedCR = ""; //记帐贷计金额
		mPolNo = ""; // 保单号
		mInsuredName = ""; // 被保险人姓名
		mBussNo = ""; // 收付款单据号
		mAttribute5 = "核心业务系统"; // 行为明细类别－来源
		mAttribute6 = "NA";
	}

	 private HashSet mLITranInfoSetHashtable=new HashSet();
	// 预留出对日志表的操作
	private boolean isExistInSet(LITranInfoSchema tLITranInfoSchema) {
		if (!mLITranInfoSetHashtable
				.contains((tLITranInfoSchema.getBillId().trim() + ":"
						+ tLITranInfoSchema.getBussNo().trim() + ":" + tLITranInfoSchema
						.getPolNo().trim()))) {
			mLITranInfoSetHashtable
					.add((tLITranInfoSchema.getBillId().trim() + ":"
							+ tLITranInfoSchema.getBussNo().trim() + ":" + tLITranInfoSchema
							.getPolNo().trim()));
			return false;
		}
		return true;
//		for (int i = 1; i <= mLITranInfoSet.size(); i++) {
//			LITranInfoSchema ttLITranInfoSchema = new LITranInfoSchema();
//			ttLITranInfoSchema.setSchema(mLITranInfoSet.get(i));
//			if ((ttLITranInfoSchema.getBillId().trim().equals(tLITranInfoSchema
//					.getBillId().trim()))
//					&& (ttLITranInfoSchema.getBussNo().trim()
//							.equals(tLITranInfoSchema.getBussNo().trim()))
//					&& (ttLITranInfoSchema.getPolNo().trim()
//							.equals(tLITranInfoSchema.getPolNo().trim()))) {
//				return true;
//			}
//		}
//		return false;
	}

	private boolean isExitInTab(String tBussNo, String tPolNo, String tBillId) {
		if (tBillId == null || tBillId.equals("")) {
			return true;
		}
		LITranInfoDB tLITranInfoDB = new LITranInfoDB();
		tLITranInfoDB.setBussNo(tBussNo);
		tLITranInfoDB.setBillId(tBillId);
		tLITranInfoDB.setPolNo(tPolNo);
		if (tLITranInfoDB.getCount() > 0)
			return true;
		return false;
	}

	private void dealLITranInfo() {
		LITranInfoSchema tLITranInfoSchema = new LITranInfoSchema();
		tLITranInfoSchema.setBatchNo(mBatchNo);
		tLITranInfoSchema.setBillId(mBillID);
		tLITranInfoSchema.setBussNo(mBussNo);
		tLITranInfoSchema.setPolNo(mPolNo);
		tLITranInfoSchema.setMakeDate(PubFun.getCurrentDate());
		tLITranInfoSchema.setMakeTime(PubFun.getCurrentTime());
//		tLITranInfoSchema.setBussDate(this.mToday);
//		tLITranInfoSchema.setVoucherID(this.mTime);
		tLITranInfoSchema.setManageCom(mManageCom);
		tLITranInfoSchema.setMatchID(mMatchID);
		if (!isExistInSet(tLITranInfoSchema))
			mLITranInfoSet.add(tLITranInfoSchema);
	}

	private OFInterfaceSchema entry() {
		String tMakeDate = PubFun.getCurrentDate();
		String tMakeTime = PubFun.getCurrentTime();
		mLineDescription = mManageCom + "||" + mBussNo + "||" + mInsuredName
				+ "||" + mAttribute5 + "||" + mBillID; // 公司段说明||收付单据号||保户人姓名||类别明细||BillID
		String[] tVerSubject = { mAccountSubCode, mManageCom, mRiskCode,
				mSaleChnl };

		try {
			tVerSubject = vertifySubject(mAccountCode); // 校验科目代码
			String tRecordID = PubFun1.CreateMaxNo("OTOF", "RECORD");

			OFInterfaceSchema rOFInterfaceSchema = new OFInterfaceSchema();
			rOFInterfaceSchema.setRecordID(tRecordID); // 记录行ID
			rOFInterfaceSchema.setReversedStatus(mReversedStatus); // 冲消状态
			rOFInterfaceSchema.setOrigRowID(mOrigRowID); // 被冲消的行
			rOFInterfaceSchema.setReversedRowID(mReversedRowID); // 冲消生成的行
			rOFInterfaceSchema.setCurrencyCode(mCurrencyCode); // 币别
			rOFInterfaceSchema.setVoucherType(mVoucherType); // 凭证类别
			rOFInterfaceSchema.setManageCom(tVerSubject[1]); // 核算单位代码
			rOFInterfaceSchema.setSegment2(mSegment2); // 成本中心
			rOFInterfaceSchema.setAccountCode(mAccountCode); // 科目代码
			rOFInterfaceSchema.setAccountSubCode(tVerSubject[0]); // 科目明细代码
			rOFInterfaceSchema.setSaleChnl(tVerSubject[3]); // 销售渠道
			rOFInterfaceSchema.setRiskCode(tVerSubject[2]); // 保险产品代码
			rOFInterfaceSchema.setSegment7("NA"); // 备用段1
			rOFInterfaceSchema.setSegment8("NA"); // 备用段2
			rOFInterfaceSchema.setTransDate(mTransDate); // 事务日期
			rOFInterfaceSchema.setAccountingDate(mAccountingDate); // 记帐日期
			rOFInterfaceSchema.setMakeDate(tMakeDate); // 创建日期
			rOFInterfaceSchema.setMakeTime(tMakeTime); // 创建时间
			rOFInterfaceSchema.setModifyDate(tMakeDate); // 最后一次修改日期
			rOFInterfaceSchema.setModifyTime(tMakeTime); // 最后一次修改时间
			rOFInterfaceSchema.setMatchID(mMatchID); // 借贷关系key值
			rOFInterfaceSchema.setBatchNo(mBatchNo); // 批次号
			rOFInterfaceSchema.setEnteredDR(mEnteredDR); // 事务借计金额
			rOFInterfaceSchema.setEnteredCR(mEnteredCR); // 事务贷计金额
			rOFInterfaceSchema.setHeadDescription(mHeadDescription); // 日记帐摘要
			rOFInterfaceSchema.setLineDescription(mLineDescription); // 行记帐摘要
			rOFInterfaceSchema.setCurrencyConversionDate(""); // 汇率日期
			rOFInterfaceSchema.setCurrencyConversionRate(""); // 汇率
			rOFInterfaceSchema.setAccountedDR(""); // 记帐借计金额
			rOFInterfaceSchema.setAccountedCR(""); // 记帐贷计金额
			rOFInterfaceSchema.setAttribute1(""); // 空闲属性1
			rOFInterfaceSchema.setPolNo(mPolNo); // 保单号
			rOFInterfaceSchema.setInsuredName(mInsuredName); // 被保险人姓名
			rOFInterfaceSchema.setBussNo(mBussNo); // 收付款单据号
			rOFInterfaceSchema.setAttribute5(mAttribute5); // 行为明细类别
			rOFInterfaceSchema.setAttribute6(mAttribute6); // 代理机构
			rOFInterfaceSchema.setAttribute7("NA"); // 空闲属性7
			rOFInterfaceSchema.setAttribute8("NA"); // 空闲属性8
			rOFInterfaceSchema.setVoucherID("-1"); // 总帐凭证号回写
			rOFInterfaceSchema.setVoucherFlag("NA"); // 回写标志
			rOFInterfaceSchema.setVoucherDate(""); // 回写导入日期

			return rOFInterfaceSchema;
		} catch (Exception ex) {
			logger.debug("Exception in entry " + ex.toString());
			CError.buildErr("OtoFAccBL", "OFInterfaceSchema赋值出错");
			return null;
		}
	}

	/**
	 * 校验科目约束性
	 */
	private String[] vertifySubject(String cSubjectName) {
		String[] rSubject = { mAccountSubCode, mManageCom, mRiskCode, mSaleChnl };

		LIVertifySchema tLIVertifySchema = new LIVertifySchema();
		for (int i = 1; i <= mLIVertifySet.size(); i++) {
			tLIVertifySchema = (LIVertifySchema) mLIVertifySet.get(i);
			if (tLIVertifySchema.getSubjectName().equals(cSubjectName)) {
				if (tLIVertifySchema.getSegAccountSub().equals("N"))
					rSubject[0] = "NA";
				if (tLIVertifySchema.getSegComCode().equals("N"))
					rSubject[1] = "NA";
				if (tLIVertifySchema.getSegRiskCode().equals("N"))
					rSubject[2] = "NA";
				if (tLIVertifySchema.getSegSaleChnl().equals("N"))
					rSubject[3] = "NA";

				break;
			}
		}

		return rSubject;
	}

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		String riskcode = "";
		String managecom = "86";
		String bDate = "2007-12-01";
		String eDate = "2007-12-01";
		String tVouchNo = "14";
		Integer itemp = new Integer(tVouchNo);
		String tAccountDate = "";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ManageCom", managecom);
		tTransferData.setNameAndValue("bDate", bDate);
		tTransferData.setNameAndValue("eDate", eDate);
		tTransferData.setNameAndValue("RiskCode", riskcode);
		tTransferData.setNameAndValue("tVouchNo", itemp);
		tTransferData.setNameAndValue("tAccountDate", tAccountDate);
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(tG);
		OtoFAccUI tOtoFAccUI = new OtoFAccUI();
		tOtoFAccUI.submitData(tVData, "Certifi");

	}

}
