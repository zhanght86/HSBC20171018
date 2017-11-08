package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LOLoanDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.InsuAccBala;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LOLoanSchema;
import com.sinosoft.lis.schema.LPBnfSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsureAccClassSchema;
import com.sinosoft.lis.schema.LPInsureAccSchema;
import com.sinosoft.lis.schema.LPInsureAccTraceSchema;
import com.sinosoft.lis.schema.LPReturnLoanSchema;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LOLoanSet;
import com.sinosoft.lis.vschema.LPBnfSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
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

public class PEdorLGDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorLGDetailBLF.class);
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
	private LPBnfSet mLPBnfSet = new LPBnfSet();
	private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();

	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorLGDetailBLF() {
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
			mLPBnfSet = (LPBnfSet)mInputData.getObjectByObjectName("LPBnfSet", 0);
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
		if((mLPEdorItemSchema.getEdorAcceptNo() == null || mLPEdorItemSchema
				.getEdorAcceptNo().equals(""))
				&& (mLPEdorItemSchema.getEdorNo() == null || mLPEdorItemSchema
						.getEdorNo().equals("")))
		{
			CError.buildErr(this, "缺少查询条件！");
			return false;
		}
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
					+ " (select amnt from lcpol where polno=a.polno), (select standprem from lcpol where polno=a.polno),polno,insuaccno,to_char(baladate,'yyyy-mm-dd') from lcinsureacc a "
					+ " where contno = '?contno?' "
					+ " and A.insuaccno IN ('000005','000009') ";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tQuerySQL);
			sqlbv.put("contno", mLPEdorItemSchema.getContNo());
			SSRS tSSRS = new SSRS();
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
				//add by jiaqiangli 2009-11-16 加上日期的控制
				if (mLPEdorItemSchema.getEdorValiDate().compareTo(tSSRS.GetText(i + 1, 8)) < 0) {
					CError.buildErr(this, "保全申请日期小于账户计息日期！");
					return false;
				}
				
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

				tInsuAccBala = new InsuAccBala();
				tTransferData = new TransferData();
				tTransferData.setNameAndValue("InsuAccNo", tSSRS.GetText(i + 1,
						7));
				tTransferData.setNameAndValue("PolNo", tSSRS.GetText(i + 1, 6));
				tTransferData.setNameAndValue("BalaDate", mLPEdorItemSchema
						.getEdorValiDate());
				VData tVData = new VData();
				tVData.add(mGlobalInput);
				tVData.add(tTransferData);
				// 非万能险的账户型结算
				if (!tInsuAccBala.submitData(tVData, "NonUniversal")) {
					CError.buildErr(this, "生存金结算失败！");
					return false;
				}
				VData tResult = new VData();
				tResult = tInsuAccBala.getResult();
				LCInsureAccSet tLCInsureAccSet = (LCInsureAccSet) tResult
						.getObjectByObjectName("LCInsureAccSet", 0);
				rStrArray[i][5] = String.valueOf(tLCInsureAccSet.get(1)
						.getInsuAccBala());
				String paydate_sql = "select to_char(max(paydate),'yyyy-mm-dd') from lcinsureacctrace where polno='?polno?' and InsuAccNo = '?InsuAccNo?' ";
				SQLwithBindVariables sbv=new SQLwithBindVariables();
				sbv.sql(paydate_sql);
				sbv.put("polno", tSSRS.GetText(i + 1, 6));
				sbv.put("InsuAccNo", tSSRS.GetText(i + 1, 7));
				String paydate = tExeSQL.getOneValue(sbv);
				rStrArray[i][6] = paydate; // 最大领至日期
				rStrArray[i][7] = tSSRS.GetText(i + 1, 6);
				rStrArray[i][8] = tSSRS.GetText(i + 1, 7);
				rStrArray[i][9] = rStrArray[i][5];
			}
			mResult.clear();
			mResult.add(rStrArray);
		} else if (mOperate.equals("EdorSave")) { // 红利领取 保存调整后的金额
			
			String DeleteSQL1 = "delete from lpinsureacctrace " + "where 1 = 1 " + "and EdorNo = '?EdorNo?' " + "and EdorType = '?EdorType?' " ;
	        SQLwithBindVariables sbv1=new SQLwithBindVariables();
	        sbv1.sql(DeleteSQL1);
	        sbv1.put("EdorNo", mLPEdorItemSchema.getEdorNo());
	        sbv1.put("EdorType", mLPEdorItemSchema.getEdorType());
			mMap.put(sbv1, "DELETE");
			
			String DeleteSQL2 = "delete from lpinsureacc " + "where 1 = 1 " + "and EdorNo = '?EdorNo?' " + "and EdorType = '?EdorType?' " ;
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql(DeleteSQL2);
			sbv2.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			sbv2.put("EdorType", mLPEdorItemSchema.getEdorType());
			mMap.put(sbv2, "DELETE");
			
			String DeleteSQL3 = "delete from lpinsureaccclass " + "where 1 = 1 " + "and EdorNo = '?EdorNo?' " + "and EdorType = '?EdorType?' " ;
			SQLwithBindVariables sbv3=new SQLwithBindVariables();
			sbv3.sql(DeleteSQL3);
			sbv3.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			sbv3.put("EdorType", mLPEdorItemSchema.getEdorType());
			
			mMap.put(sbv3, "DELETE");
			
			if (updLCInsureAccSet == null || updLCInsureAccSet.size() < 1) {
				CError.buildErr(this, "传入账户信息失败！");
				return false;
			} else {
				LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
				double getMoney=0;
				for (int k = 1; k <= updLCInsureAccSet.size(); k++) {
					// 账户表进行结息计算，添加利息记录
					InsuAccBala tInsuAccBala = new InsuAccBala();
					TransferData tTransferData = new TransferData();
					tTransferData.setNameAndValue("InsuAccNo", updLCInsureAccSet.get(k).getInsuAccNo());
					tTransferData.setNameAndValue("PolNo", updLCInsureAccSet.get(k).getPolNo());
					tTransferData.setNameAndValue("BalaDate", mLPEdorItemSchema
							.getEdorValiDate());
					VData tVData = new VData();
					tVData.add(mGlobalInput);
					tVData.add(tTransferData);
					// 非万能险的账户型结算
					if (!tInsuAccBala.submitData(tVData, "NonUniversal")) {
						CError.buildErr(this, "生存金结算失败！");
						return false;
					}
					VData tResult = new VData();
					tResult = tInsuAccBala.getResult();
					LCInsureAccTraceSet tLCInsureAccTraceSet =(LCInsureAccTraceSet)tResult.getObjectByObjectName("LCInsureAccTraceSet", 0);
					for (int m=1;m<=tLCInsureAccTraceSet.size();m++)
					{
						LPInsureAccTraceSchema tLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
						PubFun.copySchema(tLPInsureAccTraceSchema,tLCInsureAccTraceSet.get(1));
						tLPInsureAccTraceSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
						tLPInsureAccTraceSchema.setEdorType(mLPEdorItemSchema.getEdorType());
						String tLimit = PubFun.getNoLimit(tLPInsureAccTraceSchema
								.getManageCom());
						String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
						tLPInsureAccTraceSchema.setSerialNo(serNo);
						String trace_finType = BqCalBL.getFinType_HL_SC("SC", tLPInsureAccTraceSchema.getInsuAccNo(), "LX");
						if(trace_finType.equals(""))
						{
							CError.buildErr(this, "生存金领取时的财务类型获取失败");
							return false;
						}
						tLPInsureAccTraceSchema.setMoneyType(trace_finType);
						tLPInsureAccTraceSchema.setPayDate(mLPEdorItemSchema
								.getEdorValiDate());
						tLPInsureAccTraceSchema.setFeeCode("000000");

						tLPInsureAccTraceSchema.setMakeDate(mCurrentDate);
						tLPInsureAccTraceSchema.setMakeTime("00:00:00");
						tLPInsureAccTraceSchema.setModifyDate(mCurrentDate);
						tLPInsureAccTraceSchema.setModifyTime(mCurrentTime);
						tLPInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
						this.mMap.put(tLPInsureAccTraceSchema, "INSERT");
						
					}
					LCInsureAccClassSet tLCInsureAccClassSet = (LCInsureAccClassSet)tResult.getObjectByObjectName("LCInsureAccClassSet", 0);
					for(int m=1;m<=tLCInsureAccClassSet.size();m++)
					{
						LPInsureAccClassSchema tLPInsureAccClassSchema = new LPInsureAccClassSchema();
						PubFun.copySchema(tLPInsureAccClassSchema, tLCInsureAccClassSet.get(m));
						tLPInsureAccClassSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
						tLPInsureAccClassSchema.setEdorType(mLPEdorItemSchema.getEdorType());
						//updLCInsureAccSet.get(k).getInsuAccBala() 保存的是外界录入的领取金额
						
						tLPInsureAccClassSchema.setLastAccBala(tLCInsureAccClassSet.get(m).getInsuAccBala());
			            tLPInsureAccClassSchema.setInsuAccBala(tLPInsureAccClassSchema.getInsuAccBala()
								-updLCInsureAccSet.get(k).getInsuAccBala());
			            //tLPInsureAccClassSchema.setBalaDate(tBalaDate);
			            tLPInsureAccClassSchema.setBalaTime("00:00:00");
			            tLPInsureAccClassSchema.setModifyDate(mCurrentDate);
			            tLPInsureAccClassSchema.setModifyTime(mCurrentTime);
						this.mMap.put(tLPInsureAccClassSchema, "INSERT");
					}
					LCInsureAccSet tLCInsureAccSet =(LCInsureAccSet)tResult.getObjectByObjectName("LCInsureAccSet", 0);
					for(int m=1;m<=tLCInsureAccSet.size();m++)
					{
						LPInsureAccSchema tLPInsureAccSchema = new LPInsureAccSchema();
						PubFun.copySchema(tLPInsureAccSchema,tLCInsureAccSet.get(m));
						tLPInsureAccSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
						tLPInsureAccSchema.setEdorType(mLPEdorItemSchema.getEdorType());
						tLPInsureAccSchema.setLastAccBala(tLPInsureAccSchema.getInsuAccBala());
						tLPInsureAccSchema.setInsuAccBala(tLPInsureAccSchema.getInsuAccBala()
								-updLCInsureAccSet.get(k).getInsuAccBala());
//						tLPInsureAccSchema.setBalaDate(aBalaDate)
						tLPInsureAccSchema.setBalaTime("00:00:00");
						tLPInsureAccSchema.setModifyDate(mCurrentDate);
						tLPInsureAccSchema.setModifyTime(mCurrentTime);
						this.mMap.put(tLPInsureAccSchema, "INSERT");
					}
//					LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
//					tLCInsureAccClassDB.setInsuAccNo(updLCInsureAccSet.get(k).getInsuAccNo());
//					tLCInsureAccClassDB.setPolNo(updLCInsureAccSet.get(k).getPolNo());
//					LCInsureAccClassSet tLCInsureAccClassSet = tLCInsureAccClassDB.query();
//					if(tLCInsureAccClassSet.size()<=0)
//					{
//						CError.buildErr(this, "传入账户信息失败！");
//						return false;
//					}

					LPInsureAccTraceSchema tLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
					tLPInsureAccTraceSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPInsureAccTraceSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					PubFun.copySchema(tLPInsureAccTraceSchema,
							tLCInsureAccClassSet.get(1));
					String tLimit = PubFun.getNoLimit(tLPInsureAccTraceSchema
							.getManageCom());
					String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
					tLPInsureAccTraceSchema.setSerialNo(serNo);

					// GF 给付
					String trace_finType = BqCalBL.getFinType_HL_SC("SC", tLPInsureAccTraceSchema.getInsuAccNo(), "LQ");
					if(trace_finType.equals(""))
					{
						CError.buildErr(this, "生存金领取时的财务类型获取失败");
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

					tLPInsureAccTraceSchema.setMakeDate(mCurrentDate);
					tLPInsureAccTraceSchema.setMakeTime("00:00:00");
					tLPInsureAccTraceSchema.setModifyDate(mCurrentDate);
					tLPInsureAccTraceSchema.setModifyTime(mCurrentTime);
					tLPInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
					// 封装数据
					mMap.put(tLPInsureAccTraceSchema, "INSERT");

					// ljsgetendorse ljsget
					LCPolDB tLCPolDB = new LCPolDB();
					tLCPolDB.setPolNo(tLCInsureAccClassSet.get(1).getPolNo());
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
							.setSubFeeOperationType(BqCode.Get_GetDraw);
					String finType = BqCalBL.getFinType(mLPEdorItemSchema
							.getEdorType(), updLCInsureAccSet.get(k)
							.getInsuAccNo(), mLPEdorItemSchema.getPolNo());
//					String finType = "EY";
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
					tLJSGetEndorseSchema.setGetDate(mLPEdorItemSchema
							.getEdorValiDate());
					// 0为补费1为退费
					tLJSGetEndorseSchema.setGetFlag("1");
					// 保存金额
					tLJSGetEndorseSchema.setGetMoney(updLCInsureAccSet.get(k)
							.getInsuAccBala());
					getMoney =getMoney + updLCInsureAccSet.get(k).getInsuAccBala();
					// 封装数据
					tLJSGetEndorseSet.add(tLJSGetEndorseSchema);
				}
				
				/*
				 *  增加扣除保单借款超出部分规则
				 *  1、抵扣前提：产生生存金后的保单现金价值的80%低于现有保单借款金额；
				 *  2、抵扣规则：抵扣金额=借款差额+借款差额产生的利息
  				 *     借款差额=现有借款金额-产生生存金后保单现金价值的80%；
				 */
				String sum_loanmoney_sql = "select  (case when sum(leavemoney) is not null then sum(leavemoney) else 0 end) from loloan a where contno='?contno?' and loantype='0' and payoffflag='0'";
				ExeSQL tExeSQL = new ExeSQL();
				SQLwithBindVariables sbv4=new SQLwithBindVariables();
				sbv4.sql(sum_loanmoney_sql);
				sbv4.put("contno", mLPEdorItemSchema.getContNo());
				double sum_loanmoney = Double.parseDouble(tExeSQL.getOneValue(sbv4));
				//抵扣借款的顺序为：借款金额最大的先抵扣，若两笔金额相同，抵扣借款时间靠前的一笔；
				String loanmoney_sql="select * from loloan a where contno='?contno?' and loantype='0' and payoffflag='0' order by leavemoney desc, newloandate";
				LOLoanDB tLOLoanDB = new LOLoanDB();
				SQLwithBindVariables sbv5=new SQLwithBindVariables();
				sbv5.sql(loanmoney_sql);
				sbv5.put("contno", mLPEdorItemSchema.getContNo());
				LOLoanSet tLOLoanSet = tLOLoanDB.executeQuery(sbv5);
				
				if(tLOLoanSet.size()>0)
				{
					FDate tFDate = new FDate();
					if(tFDate.getDate(mLPEdorItemSchema.getEdorValiDate()).
							before(tFDate.getDate(tLOLoanSet.get(1).getLoanDate())))
					{
						logger.debug("生存领取申请日期在借款日期前，不用扣除！");
					}
					else
					{
						double CashValue = 0;
						if(sum_loanmoney>0)
						{
							EdorCalZT tEdorCalZT = new EdorCalZT();
							//现金价值净额
							tEdorCalZT.setEdorInfo(mLPEdorItemSchema);
							CashValue= tEdorCalZT.getContCashValue(mLPEdorItemSchema);
							if(PubFun.round(CashValue*0.8,2)-sum_loanmoney<0)
							{
								//借款差额
								double ce = PubFun.round(sum_loanmoney -PubFun.round(CashValue*0.8,2),2);
								//抵扣借款的顺序为：借款金额最大的先抵扣，若两笔金额相同，抵扣借款时间靠前的一笔；
								//如果借款差额大于借款记录中金额最大记录，不经行进行抵扣
								if(ce<=tLOLoanSet.get(1).getLeaveMoney())
								{
									//抵扣借款的顺序为：借款金额最大的先抵扣，若两笔金额相同，抵扣借款时间靠前的一笔；
									double tRate = AccountManage.calMultiRateMS(tLOLoanSet.get(1).getLoanDate(),  
											mLPEdorItemSchema.getEdorValiDate(), "000000","00","L","C","Y",tLOLoanSet.get(1).getCurrency());
									if((tRate+1)==0)
									{
										CError.buildErr(this, "利率乘积计算失败！");	
										return false;
									}
									double ceIntrest = PubFun.round(ce*tRate,2);
									double all = ce+ceIntrest ;
									if(getMoney-all<=0)
									{
										CError.buildErr(this, "实际领取金额（"+getMoney+"）小于或等于应抵扣保单借款的金额（"+all
												+"）不允许操作本次领取！");	
										return false;
									}
									getMoney = getMoney -all;
									
									//抵扣借款的顺序为：借款金额最大的先抵扣，若两笔金额相同，抵扣借款时间靠前的一笔；
									LOLoanSchema tLOLoanSchema = tLOLoanSet.get(1);
	//								tLOLoanSchema
									// 组织数据
									LPReturnLoanSchema tLPReturnLoanSchema = new LPReturnLoanSchema();
									tLPReturnLoanSchema.setContNo(mLPEdorItemSchema.getContNo());
									tLPReturnLoanSchema.setPolNo(tLOLoanSchema.getPolNo());
									tLPReturnLoanSchema.setEdorType(mLPEdorItemSchema.getEdorType());
									tLPReturnLoanSchema.setSerialNo(tLOLoanSchema.getSerialNo()); 
									tLPReturnLoanSchema.setActuGetNo(tLOLoanSchema.getActuGetNo()); 
									tLPReturnLoanSchema.setLoanType(tLOLoanSchema.getLoanType()); 
									tLPReturnLoanSchema.setOrderNo(tLOLoanSchema.getOrderNo()); 
									tLPReturnLoanSchema.setLoanDate(tLOLoanSchema.getLoanDate()); 
									//存放截止本次还款所欠金额
									tLPReturnLoanSchema.setSumMoney(tLOLoanSchema.getLeaveMoney()); 
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
									tLPReturnLoanSchema.setLeaveMoney(tLOLoanSchema.getLeaveMoney()); // 余额
	//								if (tLOLoanSchema.getLeaveMoney() == 0) // 还清,改变险种状态
	//								{
	//									tLPReturnLoanSchema.setPayOffFlag("1");
	//									//tLPReturnLoanSchema.setPayOffDate(strCurrentDate);
	//								} else {
									//在此处不可能还清
									tLPReturnLoanSchema.setPayOffFlag("0");
									tLPReturnLoanSchema.setPayOffDate("");
	//								}
									tLPReturnLoanSchema.setOperator(this.mGlobalInput.Operator);
									tLPReturnLoanSchema.setMakeDate(this.mCurrentDate);
									tLPReturnLoanSchema.setMakeTime(this.mCurrentTime);
									tLPReturnLoanSchema.setModifyDate(this.mCurrentDate);
									tLPReturnLoanSchema.setModifyTime(this.mCurrentTime);
									tLPReturnLoanSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
									tLPReturnLoanSchema.setLoanNo(tLOLoanSchema.getEdorNo()); // 原批单号，界面传入,关键字段，对应那次还款，由于可以多次还款，则可以出现
									tLPReturnLoanSchema.setReturnMoney(ce); // 本次还款本金就是差额部分
									tLPReturnLoanSchema.setReturnInterest(ceIntrest);//差额部分产生的利息
									
	//								mLPReturnLoanSet.add(tLPReturnLoanSchema);
									mMap.put(tLPReturnLoanSchema, "DELETE&INSERT");
									
									LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
									PubFun.copySchema(tLJSGetEndorseSchema, tLJSGetEndorseSet.get(1));
									tLJSGetEndorseSchema.setGetFlag("0");//扣费
									tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_LoanCorpus);
									
									tLJSGetEndorseSchema.setFeeFinaType(BqCalBL.getFinType(mLPEdorItemSchema
											.getEdorType(), "HK", mLPEdorItemSchema.getPolNo()));
									tLJSGetEndorseSchema.setGetMoney(ce);
							          //营改增 add zhangyingfeng 2016-07-13
							          //价税分离 计算器
							          TaxCalculator.calBySchema(tLJSGetEndorseSchema);
							          //end zhangyingfeng 2016-07-13
									mMap.put(tLJSGetEndorseSchema, "DELETE&INSERT");
									
									tLJSGetEndorseSchema = new LJSGetEndorseSchema();
									PubFun.copySchema(tLJSGetEndorseSchema, tLJSGetEndorseSet.get(1));
									tLJSGetEndorseSchema.setGetFlag("0");//扣费
									tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_LoanCorpusInterest);
									
									tLJSGetEndorseSchema.setFeeFinaType(BqCalBL.getFinType(mLPEdorItemSchema
											.getEdorType(), "LX", mLPEdorItemSchema.getPolNo()));
									tLJSGetEndorseSchema.setGetMoney(ceIntrest);
							          //营改增 add zhangyingfeng 2016-07-13
							          //价税分离 计算器
							          TaxCalculator.calBySchema(tLJSGetEndorseSchema);
							          //end zhangyingfeng 2016-07-13
									mMap.put(tLJSGetEndorseSchema, "DELETE&INSERT");
								}
							}
						}
					}
//				}
//				else if(tLOLoanSet.size()>1)
//				{
//					CError.buildErr(this, "借款信息错误：存在多条未还清借款记录！");
//					return false;
//				}
				}
		          //营改增 add zhangyingfeng 2016-07-13
		          //价税分离 计算器
		          TaxCalculator.calBySchemaSet(tLJSGetEndorseSet);
		          //end zhangyingfeng 2016-07-13
				mMap.put(tLJSGetEndorseSet, "DELETE&INSERT");
				mLPEdorItemSchema.setGetMoney(getMoney);
			}
			
			String DeleteSQL = "delete from LPBnf " + "where 1 = 1 " + "and EdorNo = '?EdorNo?' " + "and EdorType = '?EdorType?' " ;
	        SQLwithBindVariables sbv6=new SQLwithBindVariables();
	        sbv6.sql(DeleteSQL);
	        sbv6.put("EdorNo", mLPEdorItemSchema.getEdorNo());
	        sbv6.put("EdorType", mLPEdorItemSchema.getEdorType());
			mMap.put(sbv6, "DELETE");
			if(this.mLPBnfSet.size()<=0)
			{
				CError.buildErr(this, "传入实际领取人信息失败！");
				return false;
			}
			//借用保全受益人表存储实际领取人信息
			LPBnfSet tLPBnfSet = new LPBnfSet();
			for(int j=1;j<=this.mLPBnfSet.size();j++)
			{
				LPBnfSchema tLPBnfSchema = mLPBnfSet.get(j);
				tLPBnfSchema.setEdorNo(this.mLPEdorItemSchema.getEdorNo());
				tLPBnfSchema.setEdorType(this.mLPEdorItemSchema.getEdorType());
				tLPBnfSchema.setPolNo("000000");
				tLPBnfSchema.setContNo(this.mLPEdorItemSchema.getContNo());
				tLPBnfSchema.setInsuredNo(this.mLPEdorItemSchema.getInsuredNo());
				tLPBnfSchema.setBnfType("0");
				tLPBnfSchema.setBnfGrade("1");
				tLPBnfSchema.setBnfNo(j);
				tLPBnfSchema.setMakeDate(mCurrentDate);
				tLPBnfSchema.setMakeTime(mCurrentTime);
				tLPBnfSchema.setModifyDate(mCurrentDate);
				tLPBnfSchema.setModifyTime(mCurrentTime);
				tLPBnfSchema.setOperator(mGlobalInput.Operator);
				tLPBnfSet.add(tLPBnfSchema);
			}
			mMap.put(tLPBnfSet, "INSERT");
			
			// 变更保全状态
			mLPEdorItemSchema.setEdorState("3");
			mLPEdorItemSchema.setModifyDate(mCurrentDate);
			mLPEdorItemSchema.setModifyTime(mCurrentTime);
			mMap.put(mLPEdorItemSchema, "UPDATE");

			// 最后添加处理
			mResult.clear();
			mResult.add(mMap);
		}
		return true;
	}

	/** 根据帐户类型确定财务类型 */
	private String getFeeFinaType(String tInsuAccNo) {
		String tFeeFinaType = "";
		// 累计生息帐户
		if ("000005".equals(tInsuAccNo)) {
			tFeeFinaType = "YF";
		}
		if ("000009".equals(tInsuAccNo)) {
			tFeeFinaType = "EF";
		}
//		if ("000008".equals(tInsuAccNo)) {
//			tFeeFinaType = "XJTF";
//		}
		return tFeeFinaType;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
