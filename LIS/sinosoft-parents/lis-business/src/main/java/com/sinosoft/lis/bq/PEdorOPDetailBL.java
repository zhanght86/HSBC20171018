package com.sinosoft.lis.bq;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.Arith;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.InsuAccBala;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsureAccTraceSchema;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPInsureAccTraceSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全-万能险部分领取提交类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：ZengYG
 * @version：1.0
 * @CreateDate：2007-05-31
 */
public class PEdorOPDetailBL {
private static Logger logger = Logger.getLogger(PEdorOPDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private Reflections mReflections = new Reflections();
	/** 业务数据 */
	private double mInsuAccBala = 0.0; // 帐户余额
	private double mCanGetMoney = 0.0; // 可用余额
	private double mWorkNoteFee = 0.0; // 手续费
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LPPolSet mLPPolSet = new LPPolSet();
	private LPDutySet mLPDutySet = new LPDutySet();
	private LPPremSet mLPPremSet = new LPPremSet();
	private LPGetSet mLPGetSet = new LPGetSet();
	private List mBomList = new ArrayList();

	private PrepareBOMBQEdorBL mPrepareBOMBQEdorBL = new PrepareBOMBQEdorBL();

	private boolean mBomListFlag = false;
	private ExeSQL tExeSQL = new ExeSQL();
	/** 计算要素 */
	private BqCalBase mBqCalBase = new BqCalBase();

	private TransferData mTransferData = new TransferData();
	private double tGetMoney = 0.0;
	private String tRiskCode = null;
	// 获得系统日期和时间
	String strCurrentDate = PubFun.getCurrentDate();
	String strCurrentTime = PubFun.getCurrentTime();
	private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();
	private String remark;

	public PEdorOPDetailBL() {
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

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public TransferData getTransferResult() {
		return mTransferData;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			if (mLPEdorItemSchema == null || mGlobalInput == null) {
				mErrors.addOneError(new CError("数据传输不完全！"));
				return false;
			}

			if(mLPEdorItemSchema==null ||  "".equals(mLPEdorItemSchema.getEdorAcceptNo()) || mLPEdorItemSchema.getEdorAcceptNo()==null)
			{
				CError tError = new CError();
				tError.moduleName = "PEdorTRDetailBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "接收数据失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			remark = mLPEdorItemSchema.getStandbyFlag3();
			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
			tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
			tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
			LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
			if (tLPEdorItemSet.size() < 1) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorOPDetailBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "查询保全项目失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			mLPEdorItemSchema = tLPEdorItemSet.get(1);

			// 如果不是查询
			if (mOperate.equals("OP||MAIN")) {
				mTransferData = (TransferData) mInputData
						.getObjectByObjectName("TransferData", 0);
				if (mTransferData == null) {
					mErrors.addOneError(new CError("数据传输不完全！"));
					return false;
				}
			}
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorOPDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {

		// 查询保单信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPUDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "查询保单信息失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCContSchema.setSchema(tLCContDB.getSchema());

		if (mOperate.equals("OP||MAIN")) {
			String tStrGetMoney = (String) mTransferData
					.getValueByName("GetMoney");
			tGetMoney = Double.parseDouble(tStrGetMoney);
			String  tInsuAccBala = (String) mTransferData
			.getValueByName("InsuAccBala");
			mInsuAccBala = Double.parseDouble(tInsuAccBala);
			if(tStrGetMoney==null ||  "".equals(tStrGetMoney) || tInsuAccBala==null || "".equals(tInsuAccBala))
			{
				CError tError = new CError();
				tError.moduleName = "PEdorOPDetailBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "获取领取金额信息失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
//			try {
//				tGetMoney = Double.parseDouble(tStrGetMoney);
//				if (tGetMoney < 1000 || tGetMoney % 100 != 0) {
//					CError tError = new CError();
//					tError.moduleName = "PEdorOPDetailBL";
//					tError.functionName = "checkData";
//					tError.errorMessage = "领取金额最少1000元,且为100元的整数倍！";
//					this.mErrors.addOneError(tError);
//					return false;
//				}
//			} catch (Exception e) {
//				CError tError = new CError();
//				tError.moduleName = "PEdorOPDetailBL";
//				tError.functionName = "checkData";
//				tError.errorMessage = "可能输入领取金额错误！";
//				this.mErrors.addOneError(tError);
//				return false;
//			}
			String tYear = mLPEdorItemSchema.getEdorValiDate().substring(0, 4); // 避免跨年度问题
			int sYear = Integer.parseInt(tYear);
			String sCurCValiDate = BqNameFun.calDate(sYear, mLCContSchema
					.getCValiDate());
			String sNextCValiDate = PubFun.calDate(sCurCValiDate, 1, "Y", ""); // 获得下一个保单生效日

			String tSql = "select count(*) from lpedoritem where edortype='OP' and edorstate='0' and contno='?contno?' and edorappdate >= to_date('?sCurCValiDate?') and edorappdate <= to_date('?sNextCValiDate?')";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("contno", mLPEdorItemSchema.getContNo());
			sqlbv.put("sCurCValiDate", sCurCValiDate);
			sqlbv.put("sNextCValiDate", sNextCValiDate);
			String strGetTimes = tExeSQL.getOneValue(sqlbv);
			if (Double.parseDouble(strGetTimes) > 1) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorPUDetailBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "，领取次数超限，每个保单年度内至多只允许两次";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		
		String csql = " select * from lcpol where 1=1 "
			+ " and  " + "  contno = '?contno?' and polno=mainpolno";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(csql);
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
	LCPolDB nLCPolDB = new LCPolDB();
	LCPolSet tLCPolSet = nLCPolDB.executeQuery(sqlbv);
	if (nLCPolDB.mErrors.needDealError()) {
		CError.buildErr(this, "险种查询失败!");
		return false;
	}
	LCPolSchema tLCPolSchema = tLCPolSet.get(1);
		
	LPInsureAccTraceSet tLPInsureAccTraceSet = new LPInsureAccTraceSet();	

		String sql = " select * from lcinsureacc where 1=1 "
				+ " and  " + "  contno = '?contno?'";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(sql);
		sbv.put("contno", mLPEdorItemSchema.getContNo());
		LCInsureAccDB nLCInsuAccDB = new LCInsureAccDB();
		LCInsureAccSet tLCInsuAccSet = nLCInsuAccDB.executeQuery(sbv);
		if (nLCInsuAccDB.mErrors.needDealError()) {
			CError.buildErr(this, "险种查询失败!");
			return false;
		}
		LCInsureAccSchema tLCInsureAccSchema = tLCInsuAccSet.get(1);
		tRiskCode = tLCInsureAccSchema.getRiskCode();
		if (mOperate.equals("OP||QUERY")) {
			getInitDataForFormatPage(tLCInsureAccSchema);
		} else if (mOperate.equals("OP||MAIN")) {
			if (!getWorkNoteFee(tGetMoney, tLCInsureAccSchema.getRiskCode())) // 先算手续费
			{
				return false;
			}
//			if (!checkAccInfo(tLCInsureAccSchema)) // 校验帐户信息
//			{
//				return false;
//			}
//			if (tGetMoney > mCanGetMoney) {
//				// @@错误处理
//				CError tError = new CError();
//				tError.moduleName = "PEdorPRDetailBL";
//				tError.functionName = "Preparedata";
//				tError.errorMessage = "输入领取金额大于可用金额!";
//				this.mErrors.addOneError(tError);
//				return false;
//			}
				// 创建领取金额
				createLJSGetEndorse((tGetMoney - mWorkNoteFee), "TB",
						BqCode.Get_InvestAccValue, tLCPolSchema); //

				// 处理Class表
				LCInsureAccClassSchema tLCInsureAccClassSchema = new LCInsureAccClassSchema();
					LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
				LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
				tLCInsureAccClassDB.setContNo(mLPEdorItemSchema.getContNo());
				tLCInsureAccClassDB.setPolNo(tLCPolSchema.getPolNo());
				String tSql = "";
				tLCInsureAccClassSet = tLCInsureAccClassDB.query();
				if (tLCInsureAccClassDB.mErrors.needDealError()) {
					CError.buildErr(this, "保单帐户费用信息查询失败!");
					return false;
				}
				if (tLCInsureAccClassSet.size() < 1) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PEdorPRDetailBL";
					tError.functionName = "Preparedata";
					tError.errorMessage = "保全保险账户分类表!";
					this.mErrors.addOneError(tError);
					return false;
				}
				tLCInsureAccClassSchema = tLCInsureAccClassSet.get(1);


				// 处理Trace表
				tSql = "delete from LPInsureAccTrace where edortype ='OP' and edorno ='?edorno?'";
				SQLwithBindVariables sbv1=new SQLwithBindVariables();
				sbv1.sql(tSql);
				sbv1.put("edorno", mLPEdorItemSchema.getEdorNo());
				mMap.put(sbv1, "DELETE");
				LPInsureAccTraceSchema tLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
				String mSerialNo = PubFun1.CreateMaxNo("SERIALNO", PubFun
						.getNoLimit(tLCPolSchema.getManageCom()));
				
				mReflections.transFields(tLPInsureAccTraceSchema, tLCInsureAccClassSchema);
				tLPInsureAccTraceSchema.setSerialNo(mSerialNo);// 待定
				tLPInsureAccTraceSchema.setFeeCode("000000");
				tLPInsureAccTraceSchema.setEdorType(mLPEdorItemSchema
						.getEdorType());
				tLPInsureAccTraceSchema
						.setEdorNo(mLPEdorItemSchema.getEdorNo());

				tLPInsureAccTraceSchema.setMoney(-(tGetMoney - mWorkNoteFee));
				tLPInsureAccTraceSchema.setMoneyType("TB");// 领取
				tLPInsureAccTraceSchema.setPayDate(mLPEdorItemSchema
						.getEdorValiDate());
				tLPInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
				tLPInsureAccTraceSchema.setMakeDate(strCurrentDate);
				tLPInsureAccTraceSchema.setMakeTime(strCurrentTime);
				tLPInsureAccTraceSchema.setModifyDate(strCurrentDate);
				tLPInsureAccTraceSchema.setModifyTime(strCurrentTime);
				tLPInsureAccTraceSchema.setAccAlterType("3");
				tLPInsureAccTraceSchema.setAccAlterNo(mLPEdorItemSchema
						.getEdorNo());// 待定
				tLPInsureAccTraceSet.add(tLPInsureAccTraceSchema);

				if (mWorkNoteFee != 0) {
					// 创建领取费用
					// createLJSGetEndorse(mWorkNoteFee, "TBFY",
					// BqCode.Get_InvestAccFee, tCPolSchema); //领取费用为正值
					// 由于财务不体现，业务也添加
					LPInsureAccTraceSchema aLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
					String rSerialNo = PubFun1.CreateMaxNo("SERIALNO", PubFun
							.getNoLimit(tLCPolSchema.getManageCom()));
					mReflections.transFields(aLPInsureAccTraceSchema, tLCInsureAccClassSchema);
					aLPInsureAccTraceSchema.setSerialNo(rSerialNo);// 待定
					aLPInsureAccTraceSchema.setFeeCode("000000");
					aLPInsureAccTraceSchema.setEdorType(mLPEdorItemSchema
							.getEdorType());
					aLPInsureAccTraceSchema
							.setEdorNo(mLPEdorItemSchema.getEdorNo());

					aLPInsureAccTraceSchema.setMoney(-mWorkNoteFee);
					aLPInsureAccTraceSchema.setMoneyType("TBFY");// 领取费用
					aLPInsureAccTraceSchema.setPayDate(mLPEdorItemSchema
							.getEdorValiDate());
					aLPInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
					aLPInsureAccTraceSchema.setManageCom(tLCPolSchema
							.getManageCom());
					aLPInsureAccTraceSchema.setMakeDate(strCurrentDate);
					aLPInsureAccTraceSchema.setMakeTime(strCurrentTime);
					aLPInsureAccTraceSchema.setModifyDate(strCurrentDate);
					aLPInsureAccTraceSchema.setModifyTime(strCurrentTime);
					aLPInsureAccTraceSchema.setAccAlterType("3");
					aLPInsureAccTraceSchema.setAccAlterNo(mLPEdorItemSchema
							.getEdorNo());// 待定
					tLPInsureAccTraceSet.add(aLPInsureAccTraceSchema);
				}
				mMap.put(mLJSGetEndorseSet, "DELETE&INSERT");
				mMap.put(tLPInsureAccTraceSet, "DELETE&INSERT");
				mMap.put(mLPEdorItemSchema, "UPDATE");				
				String tUPDATESQL="update lpedoritem set GetMoney='?tGetMoney?',StandbyFlag1='?StandbyFlag1?',StandbyFlag2='?StandbyFlag2?', StandbyFlag3='?StandbyFlag3?' where contno='?contno?' and edorno='?edorno?' and edortype='?edortype?'";

				SQLwithBindVariables sbv2=new SQLwithBindVariables();
				sbv2.sql(tUPDATESQL);
				sbv2.put("tGetMoney", tGetMoney);
				sbv2.put("StandbyFlag1", PubFun.round(mWorkNoteFee,2));
				sbv2.put("StandbyFlag2", PubFun.round(mInsuAccBala-tGetMoney,2));
				sbv2.put("StandbyFlag3", remark);
				sbv2.put("contno", mLPEdorItemSchema.getContNo());
				sbv2.put("edorno", mLPEdorItemSchema.getEdorNo());
				sbv2.put("edortype", mLPEdorItemSchema.getEdorType());
				mMap.put(sbv2, "UPDATE");
				mResult.clear();
				mResult.add(mMap);
			}
			mTransferData.setNameAndValue("WorkNoteFee", +PubFun.round(mWorkNoteFee,2));
			mTransferData.setNameAndValue("mCanGetMoney", +PubFun.round(mCanGetMoney - tGetMoney,2));
			mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
			mResult.add(mTransferData);
		return true;
	}

	/**
	 * 页面信息初始化计算（帐户余额、可用余额、手续费）
	 * 
	 * @param pLCInsuAccSchema
	 * @param pLPEdorItemSchema
	 * @param iRiskNum
	 * @return boolean
	 */
	private boolean getInitDataForFormatPage(LCInsureAccSchema pLCInsuAccSchema) {
		if (!checkAccInfo(pLCInsuAccSchema)) {
			return false;
		}
//		if (!getWorkNoteFee(0, pLCInsuAccSchema.getRiskCode())) {
//			CError tError = new CError();
//			tError.moduleName = "PEdorOPDetailBL";
//			tError.functionName = "checkData";
//			tError.errorMessage = "获取手续费失败";
//			this.mErrors.addOneError(tError);
//			return false;
//		}

		// 组织数据
		mResult.clear();
		mTransferData.setNameAndValue("InsuAccBala", BqNameFun
				.getRound(mInsuAccBala));
		mTransferData.setNameAndValue("CanGetMoney", BqNameFun
				.getRound(mCanGetMoney));
		mTransferData.setNameAndValue("WorkNoteFee", BqNameFun
				.getRound(mWorkNoteFee));
		mResult.add(mTransferData);
		return true;
	}

	private boolean checkAccInfo(LCInsureAccSchema aLCInsuAccSchema) {
		InsuAccBala tInsuAccBala = new InsuAccBala();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("InsuAccNo", aLCInsuAccSchema.getInsuAccNo());
		tTransferData.setNameAndValue("PolNo", aLCInsuAccSchema.getPolNo());
		tTransferData.setNameAndValue("BalaDate", mLPEdorItemSchema
				.getEdorValiDate());
		VData tVData = new VData();
		tVData.add(mGlobalInput);
		tVData.add(tTransferData);
		// 非万能险的账户型结算
		if (!tInsuAccBala.submitData(tVData, "CashValue")) {
			CError.buildErr(this, "分红结算失败！");
			return false;
		}
		VData tResult = new VData();
		tResult = tInsuAccBala.getResult();
		LCInsureAccSchema tLCInsureAccSchema = (LCInsureAccSchema) tResult
				.getObjectByObjectName("LCInsureAccSchema", 0);

		BqPolBalBL tBqPolBalBL = new BqPolBalBL();
		if (!tBqPolBalBL.calLoanCorpusAddInterest(
				mLPEdorItemSchema.getContNo(), mLPEdorItemSchema
						.getEdorAppDate())) {
			CError tError = new CError();
			tError.moduleName = "PEdorOPDetailBL";
			tError.functionName = "checkData";
			tError.errorMessage = "查询保单贷款信息失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		double tLoanAndAutoPayCorpusAddInterest = tBqPolBalBL.getCalResult();

		mInsuAccBala=tLCInsureAccSchema.getInsuAccBala();
		mCanGetMoney = mInsuAccBala - tLoanAndAutoPayCorpusAddInterest - 2000
				- mWorkNoteFee;
		if (mCanGetMoney < 0) {
			mCanGetMoney = 0;
		}
		return true;
	}

	// 取得手续费
	private boolean getWorkNoteFee(double tGetMoney, String tRiskCode) {
		// 已过保单年度
		int tInterval = PubFun.calInterval(mLCContSchema.getCValiDate(),
				mLPEdorItemSchema.getEdorValiDate(), "Y")+1;

		Calculator tCalculator = new Calculator();

		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		tLMEdorCalDB.setCalType("GetFee");
		tLMEdorCalDB.setRiskCode(tRiskCode);
		tLMEdorCalDB.setEdorType("OP");
		LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query();
		if (tLMEdorCalDB.mErrors.needDealError()) {
			CError.buildErr(this, "账户险退保扣除手续费计算代码查询失败!");
			return false;
		}
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() < 1) {
			mWorkNoteFee = 0.0;
			return true; // 没有退保扣除手续费算法,返回原值
		} else {
			// 查询算法编码
			BqCalBase mBqCalBase=new BqCalBase();
			mBqCalBase.setInterval(tInterval);
			mBqCalBase.setGetMoney(tGetMoney);
			if (!prepareBOMList(mBqCalBase)) {
				CError.buildErr(this, "Prepare BOMLIST Failed...");
				return false;
			}
			tCalculator.setBOMList(this.mBomList);// 添加BOMList
			tCalculator.setCalCode(tLMEdorCalSet.get(1).getCalCode());
			// 本次结算日期对应的保单年度
			tCalculator.addBasicFactor("Interval", String.valueOf(tInterval));
			tCalculator.addBasicFactor("GetMoney", String.valueOf(tGetMoney));
			String tMoney = tCalculator.calculate();
			if (tCalculator.mErrors.needDealError()) {
				CError.buildErr(this, "领取费用计算失败!");
				return false;
			}
			mWorkNoteFee = Double.parseDouble(tMoney);
			return true;
		}

	}
	/**
	 * 为全局变量mBomList赋值，如果已经赋值过，则不再赋值
	 * 
	 * @return
	 */
	private boolean prepareBOMList(BqCalBase mBqCalBase) {
		try {
			if (!this.mBomListFlag) {
				this.mPrepareBOMBQEdorBL.setBqCalBase(mBqCalBase);
				this.mBomList = this.mPrepareBOMBQEdorBL.dealData(mLPEdorItemSchema);
				this.mBomListFlag = true;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			this.mBomListFlag = false;
			return false;
		}
	}

	/**
	 * 创建批改补退费记录
	 * 
	 * @param dBJJE
	 * @param sBQFeeType
	 * @param sSubFeeOperationType
	 * @return boolean
	 */
	private boolean createLJSGetEndorse(double dMoneyValue, String sBQFeeType,
			String sSubFeeOperationType, LCPolSchema aLCPolSchema) {
		if (Math.abs(dMoneyValue - 0) < 0.01) {
			return true;
		}

		String sFeeType = BqCalBL.getFinType(mLPEdorItemSchema.getEdorType(),
				sBQFeeType, aLCPolSchema.getPolNo());

		BqCalBL tBqCalBL = new BqCalBL();
		LJSGetEndorseSchema tLJSGetEndorseSchema;

		tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		tLJSGetEndorseSchema = tBqCalBL.initLJSGetEndorse(mLPEdorItemSchema,
				aLCPolSchema, null, sSubFeeOperationType, sFeeType, Arith
						.round(dMoneyValue, 2), mGlobalInput);
		// 用子业务类型区分同一保全项目下不同的费用
		if (tLJSGetEndorseSchema == null) {
			mErrors.copyAllErrors(tBqCalBL.mErrors);
			return false;
		}
        //营改增 add zhangyingfeng 2016-07-14
        //价税分离 计算器
        TaxCalculator.calBySchema(tLJSGetEndorseSchema);
        //end zhangyingfeng 2016-07-14
		mLJSGetEndorseSet.add(tLJSGetEndorseSchema);
		return true;
	}

	public CErrors getErrors() {
		return this.mErrors;
	}
}
