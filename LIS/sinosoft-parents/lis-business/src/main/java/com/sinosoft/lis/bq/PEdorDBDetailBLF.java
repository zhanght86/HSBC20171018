package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.InsuAccBala;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsureAccClassSchema;
import com.sinosoft.lis.schema.LPInsureAccSchema;
import com.sinosoft.lis.schema.LPInsureAccTraceSchema;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPInsureAccClassSet;
import com.sinosoft.lis.vschema.LPInsureAccSet;
import com.sinosoft.lis.vschema.LPInsureAccTraceSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 红利领取
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @version 1.0
 */

public class PEdorDBDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorDBDetailBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	private MMap mMap = new MMap();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	private double mCanGetMoney = 0.00; // 结算可领金额

	private double mActGetMoney = 0.00; // 保存实领金额

	/** 全局数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private GlobalInput mGlobalInput = new GlobalInput();

	private LCInsureAccSet updLCInsureAccSet = new LCInsureAccSet();

	private LPInsureAccSet updLPInsureAccSet = new LPInsureAccSet();
	
	private LPInsureAccClassSet updLPInsureAccClassSet = new LPInsureAccClassSet();

	private LPInsureAccTraceSet updLPInsureAccTraceSet = new LPInsureAccTraceSet();

	private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();

	private String mCurrentDate = PubFun.getCurrentDate();

	private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorDBDetailBLF() {
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据
	 * @param: cOperate 数据操作字符串
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 数据校验
		if (!checkData()) {
			return false;
		}

		// 数据准备操作
		if (!dealData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorPTDetailBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			updLCInsureAccSet = (LCInsureAccSet) mInputData
					.getObjectByObjectName("LCInsureAccSet", 0);
		} catch (Exception e) {
			mErrors.addOneError("接收数据失败");
			return false;
		}
		return true;
	}

	/**
	 * 数据校验
	 * 
	 * @return boolean
	 */
	private boolean checkData() {

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPEdorItemDB.mErrors);
			CError.buildErr(this, "查询批改项目信息失败！");
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
		return true;
	}

	/**
	 * 业务逻辑处理
	 */
	private boolean dealData() {

		if (mOperate.equals("EdorQuery")) {// 调用账户结算
			InsuAccBala tInsuAccBala = null;
			TransferData tTransferData = null;

			String tQuerySQL = new String("");
			tQuerySQL = " select riskcode,(select RiskName from LMRisk where RiskCode=a.RiskCode), (select insuaccname from lmriskinsuacc where insuaccno=a.insuaccno) ,"
					+ " (select amnt from lcpol where polno=a.polno), (select standprem from lcpol where polno=a.polno),polno,insuaccno,InsuAccBala,to_char(baladate,'yyyy-mm-dd') from lcinsureacc a "
					+ " where contno = '?contno?' "
					+ " and exists (select 1 from lmriskapp where bonusflag='1' and riskcode=a.riskcode) and insuaccno in ('000001','000007','000008') ";
			SSRS tSSRS = new SSRS();
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tQuerySQL);
			sqlbv.put("contno", mLPEdorItemSchema.getContNo());			
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow == 0) {
				CError tError = new CError();
				tError.moduleName = "PEdorDBDetailBLF";
				tError.functionName = "dealData";
				tError.errorMessage = "没有查询到账户信息！";
				this.mErrors.addOneError(tError);
				return false;
			}
			int tArrLen = tSSRS.MaxRow;
			String rStrArray[][] = new String[tArrLen][10];
			for (int i = 0; i < tArrLen; i++) {
				// 险种代码
				rStrArray[i][0] = tSSRS.GetText(i + 1, 1);
				// 险种名称
				rStrArray[i][1] = BqNameFun.getRiskShortName(tSSRS.GetText(
						i + 1, 1));
				// 账户类型
				rStrArray[i][2] = tSSRS.GetText(i + 1, 3);
				// 保额
				rStrArray[i][3] = tSSRS.GetText(i + 1, 4);
				// 保费
				rStrArray[i][4] = tSSRS.GetText(i + 1, 5);
				// 只有累计生息帐户才需要进行失算结息，其他的，如抵交保费，现金均不计息
				if ("000001".equals(tSSRS.GetText(i + 1, 7))) {
					//add by jiaqiangli 2009-11-16 加上日期的控制
					if (mLPEdorItemSchema.getEdorValiDate().compareTo(tSSRS.GetText(i + 1, 9)) < 0) {
						CError.buildErr(this, "保全申请日期小于账户计息日期！");
						return false;
					}
					tInsuAccBala = new InsuAccBala();
					tTransferData = new TransferData();
					tTransferData.setNameAndValue("InsuAccNo", tSSRS.GetText(
							i + 1, 7));
					tTransferData.setNameAndValue("PolNo", tSSRS.GetText(i + 1,
							6));
					//db与lg应该类似 comment by jiaqiangli 2009-11-16
					//comment by jiaqiangli 2009-11-16 此处这个日期也即edorappdate会小于lcinsureacc.baladate么?
					//此处保全的edorappdate对保全项要求时间上连续，但又可能还存在下面这种情况
					//只有一种情况:db.edorappdate='2009-10-01' 但保全员先操作了sgetdate='2009-10-05'的红利分配 后再操作DB保全项 
					tTransferData.setNameAndValue("BalaDate", mLPEdorItemSchema
							.getEdorValiDate());
					VData tVData = new VData();
					tVData.add(mGlobalInput);
					tVData.add(tTransferData);
					// 非万能险的账户型结算
					if (!tInsuAccBala.submitData(tVData, "NonUniversal")) {
						CError.buildErr(this, "分红结算失败！");
						return false;
					}
					VData tResult = new VData();
					tResult = tInsuAccBala.getResult();
					LCInsureAccSet tLCInsureAccSet = (LCInsureAccSet) tResult
							.getObjectByObjectName("LCInsureAccSet", 0);
					LCInsureAccTraceSchema rLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
					LCInsureAccTraceSet rLCInsureAccTraceSet = (LCInsureAccTraceSet) tResult
							.getObjectByObjectName("LCInsureAccTraceSet", 0);
					if(rLCInsureAccTraceSet!=null && rLCInsureAccTraceSet.size()>0)
					{
						rLCInsureAccTraceSchema=rLCInsureAccTraceSet.get(1);			
					}
					rStrArray[i][5] = String.valueOf(tLCInsureAccSet.get(1)
							.getInsuAccBala());
					rStrArray[i][6] ="";
					// 截止此时红利利息
					rStrArray[i][9] = String.valueOf(rLCInsureAccTraceSchema.getMoney());
				} else {
					rStrArray[i][5] = tSSRS.GetText(i + 1, 8);
					rStrArray[i][6] = "";
					rStrArray[i][9] = "0";
				}

				rStrArray[i][7] = tSSRS.GetText(i + 1, 6);
				rStrArray[i][8] = tSSRS.GetText(i + 1, 7);

			}
			mResult.clear();
			mResult.add(rStrArray);
		} else if (mOperate.equals("EdorSave")) { // 红利领取 保存调整后的金额

			String DeleteSQL1 = "delete from lpinsureacctrace "
					+ "where 1 = 1 " + "and EdorNo = '?EdorNo?' " + "and EdorType = '?EdorType?' ";
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql(DeleteSQL1);
			sbv1.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			sbv1.put("EdorType", mLPEdorItemSchema.getEdorType());
			mMap.put(sbv1, "DELETE");

			double tSumGetMoney = 0;
			if (updLCInsureAccSet == null || updLCInsureAccSet.size() < 1) {
				CError.buildErr(this, "传入账户信息失败！");
				return false;
			} else {
				for (int k = 1; k <= updLCInsureAccSet.size(); k++) {
					// 账户表
					LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
					LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
					tLCInsureAccClassDB.setInsuAccNo(updLCInsureAccSet.get(k)
							.getInsuAccNo());
					tLCInsureAccClassDB
							.setPolNo(updLCInsureAccSet.get(k).getPolNo());
					tLCInsureAccClassSet = tLCInsureAccClassDB.query();
					if (tLCInsureAccClassSet == null || tLCInsureAccClassSet.size() < 1) {
						CError.buildErr(this, "查询账户信息失败！");
						return false;
					}
					LCInsureAccClassSchema tLCInsureAccClassSchema = tLCInsureAccClassSet
							.get(1);

					
					LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
					LCInsureAccSet tLCInsureAccSet= new LCInsureAccSet();
					tLCInsureAccDB.setInsuAccNo(updLCInsureAccSet.get(k)
							.getInsuAccNo());
					tLCInsureAccDB
							.setPolNo(updLCInsureAccSet.get(k).getPolNo());
					tLCInsureAccSet = tLCInsureAccDB.query();
					if (tLCInsureAccSet == null || tLCInsureAccSet.size() < 1) {
						CError.buildErr(this, "查询账户信息失败！");
						return false;
					}
					LCInsureAccSchema tLCInsureAccSchema = tLCInsureAccSet
							.get(1);
					
					LPInsureAccTraceSchema tLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
					PubFun.copySchema(tLPInsureAccTraceSchema,
							tLCInsureAccClassSchema);
					String tLimit = PubFun.getNoLimit(tLPInsureAccTraceSchema
							.getManageCom());
					String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
					tLPInsureAccTraceSchema.setSerialNo(serNo);

					// GF 给付
					// tLPInsureAccTraceSchema.setMoneyType("HLLQ");
					String trace_finType = BqCalBL.getFinType_HL_SC("HL",
							tLPInsureAccTraceSchema.getInsuAccNo(), "LQ");
					if (trace_finType.equals("")) {
						CError.buildErr(this, "红利领取时的财务类型获取失败");
						return false;
					}
					tLPInsureAccTraceSchema.setMoneyType(trace_finType);
					// 置负金额
					tLPInsureAccTraceSchema.setMoney(-updLCInsureAccSet.get(k)
							.getInsuAccBala());
					// 日期
					tLPInsureAccTraceSchema.setPayDate(mLPEdorItemSchema
							.getEdorValiDate());
					// 红利编码存 '000000'
					tLPInsureAccTraceSchema.setFeeCode("000000");
					tLPInsureAccTraceSchema.setEdorNo(mLPEdorItemSchema
							.getEdorNo());
					tLPInsureAccTraceSchema.setEdorType(mLPEdorItemSchema
							.getEdorType());
					tLPInsureAccTraceSchema.setRiskCode(tLCInsureAccClassSchema
							.getRiskCode());
					tLPInsureAccTraceSchema.setOtherNo(tLCInsureAccClassSchema
							.getPolNo());
					tLPInsureAccTraceSchema.setOtherType("1");
					tLPInsureAccTraceSchema.setAccAscription("1");
					tLPInsureAccTraceSchema.setContNo(tLCInsureAccClassSchema
							.getContNo());
					tLPInsureAccTraceSchema.setGrpPolNo(tLCInsureAccClassSchema
							.getGrpPolNo());
					tLPInsureAccTraceSchema.setInsuAccNo(tLCInsureAccClassSchema
							.getInsuAccNo());
					tLPInsureAccTraceSchema.setGrpContNo(tLCInsureAccClassSchema
							.getGrpContNo());
					tLPInsureAccTraceSchema.setState(tLCInsureAccClassSchema
							.getState());

					// PC 互换时注意更新MakeDate
					tLPInsureAccTraceSchema.setMakeDate(mCurrentDate);
					tLPInsureAccTraceSchema.setMakeTime(mCurrentTime);
					tLPInsureAccTraceSchema.setModifyDate(mCurrentDate);
					tLPInsureAccTraceSchema.setModifyTime(mCurrentTime);
					tLPInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
					updLPInsureAccTraceSet.add(tLPInsureAccTraceSchema);
					// 封装数据

					// ljsgetendorse ljsget
					LCPolDB tLCPolDB = new LCPolDB();
					tLCPolDB.setPolNo(tLCInsureAccClassSchema.getPolNo());
					if (tLCPolDB.getInfo() == false) {
						mErrors.addOneError(new CError("查询险种保单表失败！"));
						return false;
					}
					LCPolSchema tLCPolSchema = tLCPolDB.getSchema();
					LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
					PubFun.copySchema(tLJSGetEndorseSchema, tLCPolSchema);
					tLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema
							.getEdorNo());
					tLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema
							.getEdorNo());
					tLJSGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema
							.getEdorType());
					tLJSGetEndorseSchema
							.setSubFeeOperationType(BqCode.Get_BonusCashValue);
					String finType = BqCalBL.getFinType(mLPEdorItemSchema
							.getEdorType(), updLCInsureAccSet.get(k)
							.getInsuAccNo(), mLPEdorItemSchema.getPolNo());
					if (finType.equals("")) {
						// @@错误处理
						CError.buildErr(this, "在LDCode1中缺少保全财务接口转换类型编码!");
						return false;
					}
					tLJSGetEndorseSchema.setFeeFinaType(finType);
					tLJSGetEndorseSchema.setDutyCode("000000");
					tLJSGetEndorseSchema.setPayPlanCode("000000");
					// 走保全交费财务流程
					tLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema
							.getEdorNo());
					tLJSGetEndorseSchema.setOtherNoType("3");
					tLJSGetEndorseSchema.setMakeDate(mCurrentDate);
					tLJSGetEndorseSchema.setMakeTime(mCurrentTime);
					tLJSGetEndorseSchema.setModifyDate(mCurrentDate);
					tLJSGetEndorseSchema.setModifyTime(mCurrentTime);
					tLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
					// 存放期结算日
					tLJSGetEndorseSchema.setGetDate(mCurrentDate);
					// 0为补费1为退费
					tLJSGetEndorseSchema.setGetFlag("1");
					// 保存金额
					tLJSGetEndorseSchema.setGetMoney(updLCInsureAccSet.get(k)
							.getInsuAccBala());
					tSumGetMoney += updLCInsureAccSet.get(k).getInsuAccBala();
					// 封装数据
			         //营改增 add zhangyingfeng 2016-07-14
			          //价税分离 计算器
			          TaxCalculator.calBySchema(tLJSGetEndorseSchema);
			          //end zhangyingfeng 2016-07-14
					mLJSGetEndorseSet.add(tLJSGetEndorseSchema);

					// 只有累计生息的帐户，而且来领取的时候此保单的上年度红利已经结息,则在本次领取进行结息
					if ("000001"
							.equals(updLCInsureAccSet.get(k).getInsuAccNo())
							//modify by jiaqiangli 2009-11-12 为何需要这个条件，只要是累计升息帐户都要结息
							//对于结息类 因为结息的公共程序是每次都将trace表从头开始结息(paydate < 此次要结息的aBalaDate)
							//对于晚结息的(应入账户日期小于当前acc表的baladate)和应入账户日期大于当前acc表的baladate的两种情况
							//都可以处理成只管入trace表而不作结息处理 只是此时acc.insuacc与sum(trace.money)金额会出现不一致 但领取的时候会统一做结息处理
							//lg对上面两种情况都做了处理，第一种对应分的做结息，第二种对账户的先结息 see also in personpaytoaccbl.java
//							&& isAgetBonus(updLCInsureAccSet.get(k),
//									mLPEdorItemSchema)
									) {

						LPInsureAccTraceSchema rLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
						PubFun.copySchema(rLPInsureAccTraceSchema,
								tLCInsureAccClassSchema);
						String tLXLimit = PubFun
								.getNoLimit(rLPInsureAccTraceSchema
										.getManageCom());
						String sLXerNo = PubFun1.CreateMaxNo("SERIALNO",
								tLXLimit);
						rLPInsureAccTraceSchema.setSerialNo(sLXerNo);

						// GF 给付
						// rLPInsureAccTraceSchema.setMoneyType("HLLQ");
						String trace_LXfinType = BqCalBL.getFinType_HL_SC("HL",
								rLPInsureAccTraceSchema.getInsuAccNo(), "LX");
						if (trace_finType.equals("")) {
							CError.buildErr(this, "红利领取时的财务类型获取失败");
							return false;
						}
						rLPInsureAccTraceSchema.setMoneyType(trace_LXfinType);
						// 存放本次的利息
						rLPInsureAccTraceSchema.setMoney(updLCInsureAccSet.get(
								k).getLastAccBala());
						// 日期
						rLPInsureAccTraceSchema.setPayDate(mLPEdorItemSchema
								.getEdorAppDate());
						// 红利编码存 '000000'
						rLPInsureAccTraceSchema.setFeeCode("000000");
						rLPInsureAccTraceSchema.setEdorNo(mLPEdorItemSchema
								.getEdorNo());
						rLPInsureAccTraceSchema.setEdorType(mLPEdorItemSchema
								.getEdorType());
						rLPInsureAccTraceSchema.setRiskCode(tLCInsureAccClassSchema
								.getRiskCode());
						rLPInsureAccTraceSchema.setOtherNo(tLCInsureAccClassSchema
								.getPolNo());
						rLPInsureAccTraceSchema.setOtherType("1");
						rLPInsureAccTraceSchema.setAccAscription("1");
						rLPInsureAccTraceSchema.setContNo(tLCInsureAccClassSchema
								.getContNo());
						rLPInsureAccTraceSchema.setGrpPolNo(tLCInsureAccClassSchema
								.getGrpPolNo());
						rLPInsureAccTraceSchema.setInsuAccNo(tLCInsureAccClassSchema
								.getInsuAccNo());
						rLPInsureAccTraceSchema.setGrpContNo(tLCInsureAccClassSchema
								.getGrpContNo());
						rLPInsureAccTraceSchema.setState(tLCInsureAccClassSchema
								.getState());

						// PC 互换时注意更新MakeDate
						rLPInsureAccTraceSchema.setMakeDate(mCurrentDate);
						rLPInsureAccTraceSchema.setMakeTime("00:00:00");
						rLPInsureAccTraceSchema.setModifyDate(mCurrentDate);
						rLPInsureAccTraceSchema.setModifyTime(mCurrentTime);
						rLPInsureAccTraceSchema
								.setOperator(mGlobalInput.Operator);
						updLPInsureAccTraceSet.add(rLPInsureAccTraceSchema);
						
						
						LPInsureAccSchema rLPInsureAccSchema = new LPInsureAccSchema();
						PubFun.copySchema(rLPInsureAccSchema,
								tLCInsureAccSchema);
						rLPInsureAccSchema.setBalaDate(mLPEdorItemSchema.getEdorAppDate());
						rLPInsureAccSchema.setBalaTime("00:00:00");
						rLPInsureAccSchema.setInsuAccBala(updLCInsureAccSet.get(k).getInsuAccGetMoney()-updLCInsureAccSet.get(
								k).getInsuAccBala());
						rLPInsureAccSchema.setEdorNo(mLPEdorItemSchema
								.getEdorNo());
						rLPInsureAccSchema.setEdorType(mLPEdorItemSchema
								.getEdorType());
						rLPInsureAccSchema.setModifyDate(mCurrentDate);
						rLPInsureAccSchema.setModifyTime(mCurrentTime);
						updLPInsureAccSet.add(rLPInsureAccSchema);
						
						
						LPInsureAccClassSchema rLPInsureAccClassSchema = new LPInsureAccClassSchema();
						PubFun.copySchema(rLPInsureAccClassSchema,
								tLCInsureAccClassSchema);
						rLPInsureAccClassSchema.setInsuAccBala(updLCInsureAccSet.get(k).getInsuAccGetMoney()-updLCInsureAccSet.get(
								k).getInsuAccBala());
						rLPInsureAccClassSchema.setBalaDate(mLPEdorItemSchema.getEdorAppDate());
						rLPInsureAccClassSchema.setBalaTime("00:00:00");
						rLPInsureAccClassSchema.setEdorNo(mLPEdorItemSchema
								.getEdorNo());
						rLPInsureAccClassSchema.setEdorType(mLPEdorItemSchema
								.getEdorType());
						rLPInsureAccClassSchema.setModifyDate(mCurrentDate);
						rLPInsureAccClassSchema.setModifyTime(mCurrentTime);
						updLPInsureAccClassSet.add(rLPInsureAccClassSchema);						
					}else if("000008"
							.equals(updLCInsureAccSet.get(k).getInsuAccNo()) || "000007"
							.equals(updLCInsureAccSet.get(k).getInsuAccNo()))//如果是现金或者递交保费,则跟新帐户表
					{
						LPInsureAccSchema rLPInsureAccSchema = new LPInsureAccSchema();
						PubFun.copySchema(rLPInsureAccSchema,
								tLCInsureAccSchema);
						rLPInsureAccSchema.setBalaDate(mLPEdorItemSchema.getEdorAppDate());
						rLPInsureAccSchema.setBalaTime("00:00:00");
						rLPInsureAccSchema.setInsuAccBala(updLCInsureAccSet.get(k).getInsuAccGetMoney()-updLCInsureAccSet.get(
								k).getInsuAccBala());
						rLPInsureAccSchema.setEdorNo(mLPEdorItemSchema
								.getEdorNo());
						rLPInsureAccSchema.setEdorType(mLPEdorItemSchema
								.getEdorType());
						rLPInsureAccSchema.setModifyDate(mCurrentDate);
						rLPInsureAccSchema.setModifyTime(mCurrentTime);
						updLPInsureAccSet.add(rLPInsureAccSchema);
						
						
						LPInsureAccClassSchema rLPInsureAccClassSchema = new LPInsureAccClassSchema();
						PubFun.copySchema(rLPInsureAccClassSchema,
								tLCInsureAccClassSchema);
						rLPInsureAccClassSchema.setInsuAccBala(updLCInsureAccSet.get(k).getInsuAccGetMoney()-updLCInsureAccSet.get(
								k).getInsuAccBala());
						rLPInsureAccClassSchema.setBalaDate(mLPEdorItemSchema.getEdorAppDate());
						rLPInsureAccClassSchema.setBalaTime("00:00:00");
						rLPInsureAccClassSchema.setEdorNo(mLPEdorItemSchema
								.getEdorNo());
						rLPInsureAccClassSchema.setEdorType(mLPEdorItemSchema
								.getEdorType());
						rLPInsureAccClassSchema.setModifyDate(mCurrentDate);
						rLPInsureAccClassSchema.setModifyTime(mCurrentTime);
						updLPInsureAccClassSet.add(rLPInsureAccClassSchema);	
					}

				}
			}
			mMap.put(updLPInsureAccClassSet, "DELETE&INSERT");		
			mMap.put(updLPInsureAccSet, "DELETE&INSERT");			
			mMap.put(updLPInsureAccTraceSet, "DELETE&INSERT");
			mMap.put(mLJSGetEndorseSet, "DELETE&INSERT");
			// 变更保全状态
			mLPEdorItemSchema.setEdorState("3");
			// 本次保全的收付费
			mLPEdorItemSchema.setGetMoney(tSumGetMoney);
			mLPEdorItemSchema.setModifyDate(mCurrentDate);
			mLPEdorItemSchema.setModifyTime(mCurrentTime);
			mMap.put(mLPEdorItemSchema, "UPDATE");

			// 最后添加处理
			mResult.clear();
			mResult.add(mMap);
		}
		return true;
	}

	/** 以LOBonusPol作为判断依据 */
	private boolean isAgetBonus(LCInsureAccSchema rLCInsureAccSchema,
			LPEdorItemSchema mLPEdorItemSchema) {
		//comment by jiaqiangli 2009-11-06 此处的判断逻辑是不存在应分未分红利才去计息
		//但是前面在计算可领金额的时候可是按照edorappdate进行计了息处理的(但trace.lx与acc,accclass都没处理)，此时若再红利分配时会重复计息
		//请考虑lis5.3系统对累计升息只入帐户不结息的切换系统后，新系统需要对这种数据特殊考虑(acc.baldadate=accclass.baladate=lcpol.cvalidate acc.insuaccbala=accclass.insuaccbala=0)
		ExeSQL  tExeSQL= new ExeSQL();
		String tSQL = " select case "                                                            
			+"	 when exists (select 1  "                                        
			+"	from lcpol a     "                                      
			+"	where contno = '?contno?'    "                
			+"	and Exists   "                                        
			+"	(select 1   "                                          
			+"	from lmriskapp b   "                             
			+"	where a.riskcode = b.riskcode  "                  
			+"	and bonusflag = '1')  "                          
			+"	and ((to_date('?date?','yyyy-mm-dd') >= paytodate and   "                      
			//+"	floor(months_between(date'"+mLPEdorItemSchema.getEdorAppDate()+"', cvalidate) / 12) >  "
//			+"	floor(months_between(paytodate, cvalidate) / 12) >  "
			//modify by jiaqiangli 2009-11-12 此处的逻辑是判断应领未领红利
			//see also in calcode='BQI016'
			+"	floor(months_between(paytodate, cvalidate) / 12) >  "
			+"	(select count(1)  "                                 
			+"	 from lobonuspol   "                            
			+"	where polno = a.polno  "                        
			+"		and bonusflag = '1')) or   "                  
			+"	(to_date('?date?','yyyy-mm-dd') < paytodate and   "                        
			+"	floor(months_between(to_date('?date?','yyyy-mm-dd'), cvalidate) / 12) >  "  
			+"	(select count(1)  "                                  
			+"	from lobonuspol   "                            
			+"	where polno = a.polno   "                       
			+"	and bonusflag = '1')))) then   "              
			+"		0       "                                                        
			+"	 else    "                                                        
			+"	1    "                                                          
			+"	 end    "                                                           
			+"	from dual   "  ;  
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(tSQL);
		sbv1.put("contno", rLCInsureAccSchema.getContNo());
		sbv1.put("date", mLPEdorItemSchema.getEdorAppDate());
		//comment by jiaqiangli 2009-11-12 此处最好加上满期终止的statereason='01'
		//不过终止中只有满期终止可以操作DB保全项
		String tMQSql="select '1'  from lccontstate where  statetype='Terminate' and enddate is null and state='1'  and contno='?contno?' and polno='?polno?'  ";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(tMQSql);
		sbv2.put("contno", rLCInsureAccSchema.getContNo());
		sbv2.put("polno", rLCInsureAccSchema.getPolNo());
		if("1".equals(tExeSQL.getOneValue(sbv1)) || "1".equals(tExeSQL.getOneValue(sbv2)))
		{
			return true;			
		}else
		{
		   return false;
		}
			                                                                        
	}

	/**根据帐户类型确定财务类型*/
	private String getFeeFinaType(String tInsuAccNo) {
		String tFeeFinaType = "";
		//累计生息帐户
		if ("000001".equals(tInsuAccNo)) {
			tFeeFinaType = "LJTF";
		}
		if ("000007".equals(tInsuAccNo)) {
			tFeeFinaType = "DJTF";
		}
		if ("000008".equals(tInsuAccNo)) {
			tFeeFinaType = "XJTF";
		}
		return tFeeFinaType;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
