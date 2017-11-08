package com.sinosoft.lis.bq;
import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LOLoanDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.LDExch;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LOLoanSchema;
import com.sinosoft.lis.schema.LPContStateSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPReturnLoanSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LPContStateSet;
import com.sinosoft.lis.vschema.LPReturnLoanSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.VData;


/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 自垫清偿类BL
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Nicholas
 * @version 1.0
 */

public class PEdorTRDetailBL {
private static Logger logger = Logger.getLogger(PEdorTRDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 改变保单状态相关 */
	private Reflections mReflections = new Reflections();
	private LPContStateSet mLPContStateSet = new LPContStateSet();

	/** 计算要素 */
	private BqCalBase mBqCalBase = new BqCalBase();
	//tongmeng 2010-12-02 modify
	LDExch mLDExch = new LDExch();
//	/** 用户需要清偿的本金*/
//	private double mAllShouldPay = 0.0;
//	/**用户需要累计清还的利息和*/
//	private double mAllShouldPayIntrest=0.0;

	/** 业务数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPReturnLoanSet mLPReturnLoanSet = new LPReturnLoanSet();


	private String mAction = "";

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();	
	private String tCurMakeDate = PubFun.getCurrentDate();
	
	//默认计息
	private String tisCalInterest = "on";
	
	private double tSLoanMoney=0;
	
	private double tALoanMoney=0;
	
	private MMap map = new MMap();

	public PEdorTRDetailBL() {
		
		
		
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括""和""
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 数据查询，初始化界面时初始化MulLine用
		if (mOperate.equals("QUERY||MAIN")) {
			if (!getArrayForFormatPage()) {
				return false;
			} else {
				return true;
			}
		}

		// 数据校验操作
		if (!checkData()) {
			return false;
		}
		logger.debug("---End checkData---");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("---End dealData---");
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

/* * 数据查询业务处理
	 */

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {

			// 保全项目校验
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			
			if(!"".equals(mLPEdorItemSchema.getStandbyFlag1()) && mLPEdorItemSchema.getStandbyFlag1()!=null
				&&!"".equals(mLPEdorItemSchema.getStandbyFlag2()) && mLPEdorItemSchema.getStandbyFlag2()!=null)
			{
				//应还金额
				tSLoanMoney=Double.parseDouble(mLPEdorItemSchema.getStandbyFlag1());
				//实际要还金额
				tALoanMoney=Double.parseDouble(mLPEdorItemSchema.getStandbyFlag2());
			}
			
			if(mLPEdorItemSchema==null ||  "".equals(mLPEdorItemSchema.getEdorAcceptNo()) || mLPEdorItemSchema.getEdorAcceptNo()==null)
			{
				CError tError = new CError();
				tError.moduleName = "PEdorTRDetailBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "接收数据失败！";
				this.mErrors.addOneError(tError);
				return false;
			}else
			{

				LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
				tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
				tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());

				mLPEdorItemSchema = tLPEdorItemDB.query().get(1);	
			}
			
			tisCalInterest = (String) mInputData.getObjectByObjectName("String", 0);
			if (tisCalInterest == null || tisCalInterest.equals("")) {
				tisCalInterest = "off";
			}
			logger.debug("tisCalInterest"+tisCalInterest);
			
			// 如果是查询
			if (mOperate.equals("QUERY||MAIN")) {
				return true;
			}
			// 不是查询
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLPReturnLoanSet = (LPReturnLoanSet) mInputData
					.getObjectByObjectName("LPReturnLoanSet", 0);
			return true;
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorTRDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
//		if (mOperate.equals("INSERT||MAIN")) {
//			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
//			tLPEdorItemDB.setSchema(mLPEdorItemSchema);
//			mLPEdorItemSchema.setSchema(tLPEdorItemDB.query().get(1));
//		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		try {
			// 获得此时的日期和时间
			String strCurrentDate = PubFun.getCurrentDate();
			String strCurrentTime = PubFun.getCurrentTime();

			//由于界面多次保存的原因会出现误还的情况
			String tDelStr="delete from LPReturnLoan where contno='?contno?' and  edorno='?edorno?' and edortype='?edortype?'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tDelStr);
			sqlbv1.put("contno", mLPEdorItemSchema.getContNo());
			sqlbv1.put("edorno", mLPEdorItemSchema.getEdorNo());
			sqlbv1.put("edortype", mLPEdorItemSchema.getEdorType());
			mMap.put(sqlbv1, "DELETE");
			
			String tDelStrContState="delete from LPContState where contno='?contno?' and  edorno='?edorno?' and edortype='?edortype?'";
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(tDelStrContState);
			sqlbv2.put("contno", mLPEdorItemSchema.getContNo());
			sqlbv2.put("edorno", mLPEdorItemSchema.getEdorNo());
			sqlbv2.put("edortype", mLPEdorItemSchema.getEdorType());
			mMap.put(sqlbv2, "DELETE");
			
			// 主险相关信息
			String tSqlPol = "SELECT * FROM LCPol WHERE ContNo='?ContNo?' and MainPolNo=PolNo order by polno";
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(tSqlPol);
			sqlbv3.put("ContNo", mLPEdorItemSchema.getContNo());
			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSet tLCPolSet = new LCPolSet();
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSet = tLCPolDB.executeQuery(sqlbv3);
			if (tLCPolSet == null || tLCPolSet.size() <= 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorRFDetailBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询主险信息失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			// 取主险
			tLCPolSchema = tLCPolSet.get(1);
			
			// 记录总本金和总利息，更新LPEdorItem相应字段用
			double tSumPrincipal = 0.0;
			double tSumInterest = 0.0;
			
			double tActSumPrincipal = 0.0;
			double tActSumInterest = 0.0;

			LPReturnLoanSet tLPReturnLoanSet = new LPReturnLoanSet();
			LOLoanDB tLOLoanDB = new LOLoanDB();
			// 生成“批改补退费表（应收/应付）”数据用
			LJSGetEndorseSchema tLJSGetEndorseSchema = null;
			LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
			StringBuffer tSB = new StringBuffer();
			// 查询“单位名称”
			String tGrpName = "";
			String tSql = "SELECT a.GrpName"
					+ " FROM LCAddress a,LCInsured b,LCPol c"
					+ " WHERE a.CustomerNo=b.InsuredNo"
					+ " and a.AddressNo=b.AddressNo"
					+ " and b.InsuredNo=c.InsuredNo" + " and b.ContNo=c.ContNo"
					+ " and c.PolNo='?PolNo?'";
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(tSql);
			sqlbv4.put("PolNo", tLCPolSchema.getPolNo());
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv4);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				tGrpName = tSSRS.GetText(1, 1);
			}
			
			//tongmeng 2011-04-26 modify
			//增加对多币种的处理
			Hashtable tCurrencyMap = new Hashtable();
			Hashtable tCurrencyTypeMap = new Hashtable();
			for (int i = 1; i <= mLPReturnLoanSet.size(); i++) {
				// 获得一条数据
				LPReturnLoanSchema tLPReturnLoanSchema = new LPReturnLoanSchema();
				tLPReturnLoanSchema = mLPReturnLoanSet.get(i);
				// 查询“借款业务表”数据
				tLOLoanDB.setContNo(tLPReturnLoanSchema.getContNo());
				tLOLoanDB.setPolNo(tLPReturnLoanSchema.getPolNo());
				tLOLoanDB.setEdorNo(tLPReturnLoanSchema.getLoanNo());
				tLOLoanDB.setLoanType("1");
				tLOLoanDB.setPayOffFlag("0");
				tLOLoanDB.setCurrency(tLPReturnLoanSchema.getCurrency());
				if (!tLOLoanDB.getInfo()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PEdorTRDetailBL";
					tError.functionName = "dealData";
					tError.errorMessage = "查询借款信息错误！";
					this.mErrors.addOneError(tError);
					return false;
				}
				LOLoanSchema tLOLoanSchema = new LOLoanSchema();
				tLOLoanSchema = tLOLoanDB.getSchema();

				tLPReturnLoanSchema.setContNo(mLPEdorItemSchema.getContNo());
				tLPReturnLoanSchema.setPolNo(tLPReturnLoanSchema.getPolNo());
				tLPReturnLoanSchema
						.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPReturnLoanSchema.setSerialNo(tLOLoanSchema.getSerialNo()); 
				tLPReturnLoanSchema.setActuGetNo(tLOLoanSchema.getActuGetNo()); 
				tLPReturnLoanSchema.setLoanType(tLOLoanSchema.getLoanType()); 
				tLPReturnLoanSchema.setOrderNo(tLOLoanSchema.getOrderNo()); 
				tLPReturnLoanSchema.setLoanDate(tLOLoanSchema.getLoanDate()); 
	
				tLPReturnLoanSchema.setSumMoney(tLOLoanSchema.getSumMoney()); 
				tLPReturnLoanSchema.setInputFlag(tLOLoanSchema.getInputFlag()); 
				tLPReturnLoanSchema.setInterestType(tLOLoanSchema
						.getInterestType()); 
				tLPReturnLoanSchema.setInterestRate(tLOLoanSchema
						.getInterestRate()); 
				tLPReturnLoanSchema.setInterestMode(tLOLoanSchema
						.getInterestMode()); 
				tLPReturnLoanSchema.setRateCalType(tLOLoanSchema
						.getRateCalType()); 
				tLPReturnLoanSchema.setRateCalCode(tLOLoanSchema
						.getRateCalCode()); 
				tLPReturnLoanSchema.setSpecifyRate(tLOLoanSchema
						.getSpecifyRate()); 
				tLPReturnLoanSchema.setLeaveMoney(0); 
				tLPReturnLoanSchema.setOperator(this.mGlobalInput.Operator);
				tLPReturnLoanSchema.setMakeDate(strCurrentDate);
				tLPReturnLoanSchema.setMakeTime(strCurrentTime);
				tLPReturnLoanSchema.setModifyDate(strCurrentDate);
				tLPReturnLoanSchema.setModifyTime(strCurrentTime);
				tLPReturnLoanSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				 tLPReturnLoanSchema.setLoanNo(tLOLoanSchema.getEdorNo());
				// //原批单号，界面传入
				 tLPReturnLoanSchema.setReturnMoney(tLPReturnLoanSchema.getReturnMoney()); //还款金额，界面传入
				 tLPReturnLoanSchema.setReturnInterest(tLPReturnLoanSchema.getReturnInterest()); //还款利息，界面传入

				 tLPReturnLoanSchema.setPayOffFlag("1");
				 tLPReturnLoanSchema.setPayOffDate(mLPEdorItemSchema.getEdorAppDate());				 
				tLPReturnLoanSet.add(tLPReturnLoanSchema);

				String tCurrency = tLPReturnLoanSchema.getCurrency();
				
				//直接向tCurrencyTypeMap中赋值
				tCurrencyTypeMap.put(tCurrency, tCurrency);
				//处理本金 - 原
				double tempSumPrincipal = 0;
				if(tCurrencyMap.containsKey(tCurrency+":BJ1"))
				{
					tempSumPrincipal = Double.parseDouble((String)tCurrencyMap.get(tCurrency+":BJ1"));
				}

				tempSumPrincipal = tempSumPrincipal + tLPReturnLoanSchema.getReturnMoney();

				tCurrencyMap.put(tCurrency+":BJ1", String.valueOf(tempSumPrincipal));
				
				//处理利息 - 原
				double tempSumInterest = 0;
				if(tCurrencyMap.containsKey(tCurrency+":LX1"))
				{
					tempSumInterest = Double.parseDouble((String)tCurrencyMap.get(tCurrency+":LX1"));
				}

				tempSumInterest = tempSumInterest + tLPReturnLoanSchema.getReturnInterest();

				tCurrencyMap.put(tCurrency+":LX1", String.valueOf(tempSumInterest));
				
				
				
				//
				//处理本金 - 实际
				double tempActSumPrincipal = 0;
				if(tCurrencyMap.containsKey(tCurrency+":BJ2"))
				{
					tempActSumPrincipal = Double.parseDouble((String)tCurrencyMap.get(tCurrency+":BJ2"));
				}

				tempActSumPrincipal = tempActSumPrincipal + mLPReturnLoanSet.get(i).getReturnMoney();

				tCurrencyMap.put(tCurrency+":BJ2", String.valueOf(tempActSumPrincipal));
				
				//处理利息 - 实际
				double tempActSumInterest = 0;
				if(tCurrencyMap.containsKey(tCurrency+":LX2"))
				{
					tempActSumInterest = Double.parseDouble((String)tCurrencyMap.get(tCurrency+":LX2"));
				}

				tempActSumInterest = tempActSumInterest + mLPReturnLoanSet.get(i).getReturnInterest();

				tCurrencyMap.put(tCurrency+":LX2", String.valueOf(tempActSumInterest));
				
				//

				// 记录总本金和总利息，更新LPEdorItem相应字段用,由于自垫肯定清偿，则应还等于实际还款
				
				//直接折算到本币
				tSumPrincipal += this.mLDExch.toBaseCur(tCurrency, SysConst.BaseCur, strCurrentDate, tLPReturnLoanSchema.getReturnMoney());
				tSumInterest += this.mLDExch.toBaseCur(tCurrency, SysConst.BaseCur, strCurrentDate, tLPReturnLoanSchema.getReturnInterest());
				//累计实际换的的本金
				tActSumPrincipal+=this.mLDExch.toBaseCur(tCurrency, SysConst.BaseCur, strCurrentDate, mLPReturnLoanSet.get(i).getReturnMoney());
				tActSumInterest+=this.mLDExch.toBaseCur(tCurrency, SysConst.BaseCur, strCurrentDate, mLPReturnLoanSet.get(i).getReturnInterest());
			}
			
		Enumeration eKey=tCurrencyTypeMap.keys(); 
		while(eKey.hasMoreElements()) 
		{ 
			String key=(String)eKey.nextElement();
				
			logger.debug("Current Currency:"+key);
			double tempSumPrincipal = Double.parseDouble((String)tCurrencyMap.get(key+":BJ1"));
			double tempSumInterest = Double.parseDouble((String)tCurrencyMap.get(key+":LX1"));
			
			double tempActSumPrincipal = Double.parseDouble((String)tCurrencyMap.get(key+":BJ2"));
			double tempActSumInterest = Double.parseDouble((String)tCurrencyMap.get(key+":LX2"));
			
			// 生成“批改补退费表（应收/应付）”数据（本金）
			tLJSGetEndorseSchema = new LJSGetEndorseSchema();
			tLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema
					.getEdorNo());
			tLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema
					.getEdorNo());
			tLJSGetEndorseSchema.setFeeOperationType("TR");
			tLJSGetEndorseSchema.setCurrency(key);
			// 查询“补/退费财务类型”
			// 清空缓冲区
			tSB.replace(0, tSB.length(), "");
			// 组织SQL语句
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			tSB.append("SELECT CodeName"
							+ " FROM ((select '1' as flag,CodeName"
							+ " from LDCode1"
							+ " where CodeType='TR' and Code='HD' and Code1='?Code1?')"
							+ " union"
							+ " (select '2' as flag,CodeName"
							+ " from LDCode1"
							+ " where CodeType='TR' and Code='HD' and Code1='000000'))"
							+ " WHERE rownum=1" + " ORDER BY flag");
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL))	{
				tSB.append("SELECT CodeName"
						+ " FROM ((select '1' as flag,CodeName"
						+ " from LDCode1"
						+ " where CodeType='TR' and Code='HD' and Code1='?Code1?')"
						+ " union"
						+ " (select '2' as flag,CodeName"
						+ " from LDCode1"
						+ " where CodeType='TR' and Code='HD' and Code1='000000')) g"
						+ " ORDER BY flag" + " limit 0,1" );
			}
			// 查询
			tSSRS = new SSRS();
			SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
			sqlbv5.sql(tSB.toString());
			sqlbv5.put("Code1", tLCPolSchema.getRiskCode());
			tSSRS = tExeSQL.execSQL(sqlbv5);
			if (tSSRS == null && tSSRS.MaxRow <= 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorTRDetailBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询还款的“补/退费财务类型”时产生错误！";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLJSGetEndorseSchema.setFeeFinaType(tSSRS.GetText(1, 1));

			tLJSGetEndorseSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
			tLJSGetEndorseSchema.setContNo(tLCPolSchema.getContNo());
			tLJSGetEndorseSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
			tLJSGetEndorseSchema.setPolNo(tLCPolSchema.getPolNo());
			tLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo());
			tLJSGetEndorseSchema.setOtherNoType("3");
			tLJSGetEndorseSchema.setDutyCode("000000");
			tLJSGetEndorseSchema.setPayPlanCode("000000");
			tLJSGetEndorseSchema.setAppntNo(tLCPolSchema.getAppntNo());
			tLJSGetEndorseSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
			tLJSGetEndorseSchema.setGetDate(strCurrentDate);
			//由于用户可以对还垫金额可以修改，则存入从界面出入的值，如果发生变化则需要进行审批
			tLJSGetEndorseSchema.setGetMoney(tempActSumPrincipal);
			tLJSGetEndorseSchema.setKindCode(tLCPolSchema.getKindCode());
			tLJSGetEndorseSchema.setRiskCode(tLCPolSchema.getRiskCode());
			tLJSGetEndorseSchema.setRiskVersion(tLCPolSchema
					.getRiskVersion());
			tLJSGetEndorseSchema.setManageCom(tLCPolSchema.getManageCom());
			tLJSGetEndorseSchema.setAgentCom(tLCPolSchema.getAgentCom());
			tLJSGetEndorseSchema.setAgentType(tLCPolSchema.getAgentType());
			tLJSGetEndorseSchema.setAgentCode(tLCPolSchema.getAgentCode());
			tLJSGetEndorseSchema
					.setAgentGroup(tLCPolSchema.getAgentGroup());
			tLJSGetEndorseSchema.setGrpName(tGrpName);
			tLJSGetEndorseSchema.setHandler(""); // 暂时不知道如何处理
			tLJSGetEndorseSchema.setPolType("1");
			tLJSGetEndorseSchema.setGetFlag("0");
			tLJSGetEndorseSchema.setSerialNo(""); // 暂时不知道如何处理
			tLJSGetEndorseSchema.setOperator(this.mGlobalInput.Operator);
			tLJSGetEndorseSchema.setMakeDate(strCurrentDate);
			tLJSGetEndorseSchema.setMakeTime(strCurrentTime);
			tLJSGetEndorseSchema.setModifyDate(strCurrentDate);
			tLJSGetEndorseSchema.setModifyTime(strCurrentTime);
			tLJSGetEndorseSchema
					.setSubFeeOperationType(BqCode.Pay_AutoPayPrem
							);
	          //营改增 add zhangyingfeng 2016-07-14
	          //价税分离 计算器
	          TaxCalculator.calBySchema(tLJSGetEndorseSchema);
	          //end zhangyingfeng 2016-07-14
			tLJSGetEndorseSet.add(tLJSGetEndorseSchema);

			// 生成“批改补退费表（应收/应付）”数据（利息）
			tLJSGetEndorseSchema = new LJSGetEndorseSchema();
			tLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema
					.getEdorNo());
			tLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema
					.getEdorNo());
			tLJSGetEndorseSchema.setFeeOperationType("TR");

			// 查询“补/退费财务类型”
			// 清空缓冲区
			tSB.replace(0, tSB.length(), "");
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			// 组织SQL语句
			tSB.append("SELECT CodeName"
							+ " FROM ((select '1' as flag,CodeName"
							+ " from LDCode1"
							+ " where CodeType='TR' and Code='LX' and Code1='?Code1?')"
							+ " union"
							+ " (select '2' as flag,CodeName"
							+ " from LDCode1"
							+ " where CodeType='TR' and Code='LX' and Code1='000000'))"
							+ " WHERE rownum=1" + " ORDER BY flag");
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tSB
				.append("SELECT CodeName"
						+ " FROM ((select '1' as flag,CodeName"
						+ " from LDCode1"
						+ " where CodeType='TR' and Code='LX' and Code1='?Code1?')"
						+ " union"
						+ " (select '2' as flag,CodeName"
						+ " from LDCode1"
						+ " where CodeType='TR' and Code='LX' and Code1='000000')) g"
						+ " ORDER BY flag" + " limit 0,1" );	
			}
			// 查询
			SQLwithBindVariables sbv6=new SQLwithBindVariables();
			sbv6.sql(tSB.toString());
			sbv6.put("Code1", tLCPolSchema.getRiskCode());
			tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sbv6);
			if (tSSRS == null && tSSRS.MaxRow <= 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorTRDetailBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询利息的“补/退费财务类型”时产生错误！";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLJSGetEndorseSchema.setFeeFinaType(tSSRS.GetText(1, 1));
			tLJSGetEndorseSchema.setCurrency(key);
			tLJSGetEndorseSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
			tLJSGetEndorseSchema.setContNo(tLCPolSchema.getContNo());
			tLJSGetEndorseSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
			tLJSGetEndorseSchema.setPolNo(tLCPolSchema.getPolNo());
			tLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo());
			tLJSGetEndorseSchema.setOtherNoType("3");
			tLJSGetEndorseSchema.setDutyCode("000000");
			tLJSGetEndorseSchema.setPayPlanCode("000000");
			tLJSGetEndorseSchema.setAppntNo(tLCPolSchema.getAppntNo());
			tLJSGetEndorseSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
			tLJSGetEndorseSchema.setGetDate(strCurrentDate);
			//由于用户可以对还垫金额可以修改，则存入从界面出入的值，如果发生变化则需要进行审批
			tLJSGetEndorseSchema.setGetMoney(tempActSumInterest);
			tLJSGetEndorseSchema.setKindCode(tLCPolSchema.getKindCode());
			tLJSGetEndorseSchema.setRiskCode(tLCPolSchema.getRiskCode());
			tLJSGetEndorseSchema.setRiskVersion(tLCPolSchema
					.getRiskVersion());
			tLJSGetEndorseSchema.setManageCom(tLCPolSchema.getManageCom());
			tLJSGetEndorseSchema.setAgentCom(tLCPolSchema.getAgentCom());
			tLJSGetEndorseSchema.setAgentType(tLCPolSchema.getAgentType());
			tLJSGetEndorseSchema.setAgentCode(tLCPolSchema.getAgentCode());
			tLJSGetEndorseSchema
					.setAgentGroup(tLCPolSchema.getAgentGroup());
			tLJSGetEndorseSchema.setGrpName(tGrpName);
			tLJSGetEndorseSchema.setHandler(""); // 暂时不知道如何处理
			tLJSGetEndorseSchema.setPolType("1");
			tLJSGetEndorseSchema.setGetFlag("0");
			tLJSGetEndorseSchema.setSerialNo(""); // 暂时不知道如何处理
			tLJSGetEndorseSchema.setOperator(this.mGlobalInput.Operator);
			tLJSGetEndorseSchema.setMakeDate(strCurrentDate);
			tLJSGetEndorseSchema.setMakeTime(strCurrentTime);
			tLJSGetEndorseSchema.setModifyDate(strCurrentDate);
			tLJSGetEndorseSchema.setModifyTime(strCurrentTime);
			tLJSGetEndorseSchema
					.setSubFeeOperationType(BqCode.Pay_AutoPayPremInterest
							);
	          //营改增 add zhangyingfeng 2016-07-14
	          //价税分离 计算器
	          TaxCalculator.calBySchema(tLJSGetEndorseSchema);
	          //end zhangyingfeng 2016-07-14
			tLJSGetEndorseSet.add(tLJSGetEndorseSchema);
		}
			// 只有所有自垫都还清才修改保单状态为未贷款状态
            //不再从前台传入，前台可能有问题
			if ((Math.abs(tSLoanMoney-tALoanMoney)<0.01)) 
			{
				if (!changePolState("000000", "PayPrem", "0",
						mLPEdorItemSchema.getEdorAppDate(),mLPEdorItemSchema.getContNo())) {
					return false;
				}
			}

			mMap.put(tLPReturnLoanSet, "DELETE&INSERT");
			mMap.put(tLJSGetEndorseSet, "DELETE&INSERT");
            			
			mMap.put(mLPContStateSet, "DELETE&INSERT");
            double tActSumMoeny=tActSumPrincipal+tActSumInterest;
			// 修改“个险保全项目表”相应信息
			mLPEdorItemSchema.setEdorState("3");
			mLPEdorItemSchema.setOperator(this.mGlobalInput.Operator);
			mLPEdorItemSchema.setModifyDate(strCurrentDate);
			mLPEdorItemSchema.setModifyTime(strCurrentTime);
			mLPEdorItemSchema.setGetMoney(tActSumMoeny);
			mLPEdorItemSchema.setGetInterest(tActSumInterest);
			//原本要还的本金
			mLPEdorItemSchema.setStandbyFlag1(String.valueOf(tSumPrincipal));	
			//原本要还的利息
			mLPEdorItemSchema.setStandbyFlag3(String.valueOf(tSumInterest));	

			mMap.put(mLPEdorItemSchema, "UPDATE");

			mResult.clear();
			mResult.add(mMap);
			mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
			mBqCalBase.setPolNo(mLPEdorItemSchema.getPolNo());
			mResult.add(mBqCalBase);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorTRDetailBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误！" + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

		/**
	 * 获得一个二维数组，初始化MulLine用 行内容：自垫日期、垫交保费、垫交利率、垫交利息、垫交本息、原批单号（隐藏列）
	 * 
	 * @return true--Success,false--Fail
	 */
	private boolean getArrayForFormatPage() {

		String tSql = "SELECT LoanDate,SumMoney,EdorNo,Polno,Currency FROM LOLoan WHERE LoanType='1' and PayOffFlag='0' and ContNo='?ContNo?' ORDER BY LoanDate";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("ContNo", this.mLPEdorItemSchema.getContNo());
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv);
		if (tSSRS == null || tSSRS.MaxRow == 0) {
			// 没有自垫信息
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorTRDetailBL";
			tError.functionName = "getArrayForFormatPage";
			tError.errorMessage = "没有查询到自垫信息！";
			this.mErrors.addOneError(tError);
			return false;
		}
		int tArrLen = tSSRS.MaxRow;
		String rStrArray[][] = new String[tArrLen][8];
		// 利息相关计算类
		for (int i = 0; i < tArrLen; i++) {
			// 自垫日期
			rStrArray[i][0] = tSSRS.GetText(i + 1, 1);
			// 垫交保费
			rStrArray[i][1] = tSSRS.GetText(i + 1, 2);
			// 分段计息
			String tLoanDate=PubFun.calDate(mLPEdorItemSchema.getEdorAppDate(), 1, "D", "");
			double tRate=0.00;
			
			if (this.tisCalInterest != null && this.tisCalInterest.equals("on"))
				tRate = AccountManage.calMultiRateMS(rStrArray[i][0],tLoanDate,"000000","00","L","C","Y",tSSRS.GetText(i + 1, 5));
			
			if (tRate+1==0) {
				CError tError = new CError();
				tError.moduleName = "PEdorTRDetailBL";
				tError.functionName = "getArrayForFormatPage";
				tError.errorMessage = "没有查询到利率信息！";
				this.mErrors.addOneError(tError);
				return false;
			}
			// 利息
			rStrArray[i][2] =BqNameFun.getRound((Double.parseDouble(rStrArray[i][1] )*tRate));
			//本息和
			
			// 垫交保费
			rStrArray[i][3] = tSSRS.GetText(i + 1, 2);
			
			rStrArray[i][4] =BqNameFun.getRound((Double.parseDouble(rStrArray[i][1] )*tRate));
			
			rStrArray[i][5] = tSSRS.GetText(i + 1, 3);
			
			rStrArray[i][6] = tSSRS.GetText(i + 1, 4);
			//tongmeng 2011-04-26 modify
			//增加币种的显示
			rStrArray[i][7] = tSSRS.GetText(i + 1, 5);
			// 本金累计
			//mAllShouldPay += Double.parseDouble(rStrArray[i][1]);
			//利息累计
			//mAllShouldPayIntrest+=Double.parseDouble(rStrArray[i][2]);
		}
		mResult.clear();
		mResult.add(rStrArray);
		return true;
	}

	/**
	 * 改变险种的状态（注：是险种级状态）
	 * 
	 * @param tPolNo
	 * @param tStateType
	 * @param tState
	 * @param tNewStateDate
	 * @return boolean true--成功，false--失败。结果放在mLPContStateSet变量中（累计）
	 */
	private boolean changePolState(String tPolNo, String tStateType,
			String tState, String tNewStateDate,String tContNo) {
		try {
			// 当前日期时间
			String tCurrentDate = PubFun.getCurrentDate();
			String tCurrentTime = PubFun.getCurrentTime();
			LCContStateSchema tLCContStateSchema = null;
			LPContStateSchema tLPContStateSchema = null;
			// 先查询当前状态是否是要改变的状态，如果是，则保持
			String tSql = "SELECT *" + " FROM LCContState" + " WHERE contno='?tContNo?' and PolNo='?tPolNo?'" + " and StateType='?tStateType?'"
					+ " and State='?tState?'" + " and EndDate is null";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("tContNo", tContNo);
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("tStateType", tStateType);
			sqlbv.put("tState", tState);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				// 现在的状态就是要改变后的状态，所以，保持
				return true;
			}

			String tStardDate="";
			
			// 按张容所说，如果新旧状态主键重复（指在C表中重复），则直接用新状态把旧状态替掉。2005-09-07日修改。
			LCContStateDB tLCContStateDB = new LCContStateDB();
			tLCContStateDB.setContNo(mLPEdorItemSchema.getContNo());
			tLCContStateDB.setInsuredNo("000000");
			tLCContStateDB.setPolNo(tPolNo);
			tLCContStateDB.setStateType(tStateType);
			tLCContStateDB.setStartDate(tNewStateDate);
			if (!tLCContStateDB.getInfo()) {
				// 查询现在状态，将此状态结束
				tSql = "SELECT *" + " FROM LCContState" + " WHERE contno='?tContNo?' and  PolNo='?tPolNo?'" + " and StateType='?tStateType?'"
						+ " and EndDate is null";
				sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("tContNo", tContNo);
				sqlbv.put("tPolNo", tPolNo);
				sqlbv.put("tStateType", tStateType);
				tLCContStateDB = new LCContStateDB();
				LCContStateSet tLCContStateSet = new LCContStateSet();
				tLCContStateSet = tLCContStateDB.executeQuery(sqlbv);
				if (tLCContStateSet != null && tLCContStateSet.size() > 0) {
					tLCContStateSchema = new LCContStateSchema();
					tLCContStateSchema = tLCContStateSet.get(1);
					tStardDate=tLCContStateSchema.getEndDate();
					//状态转换在同一天，未防主键冲突.往后移一天
					if(tStardDate!=null && tStardDate.equals(mLPEdorItemSchema.getEdorAppDate()))
					{
						tNewStateDate=PubFun.calDate(tNewStateDate,
								1, "D", null);
					}
					tLCContStateSchema.setEndDate(PubFun.calDate(tNewStateDate,
							-1, "D", null)); // 状态在前一天结束
					tLCContStateSchema.setOperator(mGlobalInput.Operator);
					tLCContStateSchema.setModifyDate(tCurrentDate);
					tLCContStateSchema.setModifyTime(tCurrentTime);
					tLPContStateSchema = new LPContStateSchema();
					this.mReflections.transFields(tLPContStateSchema,
							tLCContStateSchema);
					tLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPContStateSchema.setEdorType(mLPEdorItemSchema
							.getEdorType());
					mLPContStateSet.add(tLPContStateSchema);
				}
			}
			// 新状态信息
			tLPContStateSchema = new LPContStateSchema();
			tLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPContStateSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPContStateSchema.setContNo(mLPEdorItemSchema.getContNo());
			tLPContStateSchema.setInsuredNo("000000");
			tLPContStateSchema.setPolNo(tPolNo);
			tLPContStateSchema.setStateType(tStateType);
			tLPContStateSchema.setState(tState);
			if (tStateType != null && tStateType.equals("Terminate")) {
				tLPContStateSchema.setStateReason("05");
			}
			tLPContStateSchema.setStartDate(tNewStateDate);
			tLPContStateSchema.setOperator(mGlobalInput.Operator);
			tLPContStateSchema.setMakeDate(tCurrentDate);
			tLPContStateSchema.setMakeTime(tCurrentTime);
			tLPContStateSchema.setModifyDate(tCurrentDate);
			tLPContStateSchema.setModifyTime(tCurrentTime);
			mLPContStateSet.add(tLPContStateSchema);
			return true;
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "PEdorTRDetailBL";
			tError.functionName = "changePolState";
			tError.errorMessage = "修改险种状态时产生错误！险种号：" + tPolNo;
			this.mErrors.addOneError(tError);
			return false;
		}
	}

}
