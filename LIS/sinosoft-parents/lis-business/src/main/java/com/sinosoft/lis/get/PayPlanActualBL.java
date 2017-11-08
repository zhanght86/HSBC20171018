package com.sinosoft.lis.get;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 生存领取核销程序</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author Nicholas
 * @version 1.0
 */


import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJSGetDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJAGetDrawSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJSGetDrawSchema;
import com.sinosoft.lis.schema.LJSGetSchema;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

public class PayPlanActualBL {
private static Logger logger = Logger.getLogger(PayPlanActualBL.class);
	// 2005-07-21日添加本类，目的是：
	// 将催付时产生的应该进实付的数据从应付导入实付

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();;
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	private MMap mMap = new MMap();

	/** 业务处理相关变量 */
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	// private boolean OnlineFlag = false;

	// 还可能直接生成实付数据，由产品定义描述判断。新增变量。Add by Nicholas
	private LJAGetSet mLJAGetSet = new LJAGetSet();

	// 是否领完标志修改。
	// 比较GetToDate和GetEndDate来判断。
	// 实付直接修改LCGet的“止领标志”，应付交给AG项目去判断修改。
	// 发通知书
	// LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();

	// private LCPolSchema mLCPolSchema = new LCPolSchema();
	// private LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();
	// private LCPolSchema aLCPolSchema = new LCPolSchema();
	// private LMDutyGetAliveSchema mLMDutyGetAliveSchema = new
	// LMDutyGetAliveSchema();
	// private LMDutyGetSchema mLMDutyGetSchema = new LMDutyGetSchema();

	private LJSGetDrawSchema mLJSGetDrawSchema = new LJSGetDrawSchema();
	private LJSGetSchema mLJSGetSchema = new LJSGetSchema();

	public PayPlanActualBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		this.mInputData = (VData) cInputData.clone();
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PayPlanActualBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		// 全局变量
		try {
			mGlobalInput.setSchema((GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 0));
			mLJSGetDrawSchema = (LJSGetDrawSchema) mInputData
					.getObjectByObjectName("LJSGetDrawSchema", 0);

			// 获得总表数据
			LJSGetDB tLJSGetDB = new LJSGetDB();
			tLJSGetDB.setGetNoticeNo(mLJSGetDrawSchema.getGetNoticeNo());
			if (!tLJSGetDB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PayPlanActualBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "查询应付总表数据失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			mLJSGetSchema.setSchema(tLJSGetDB.getSchema());
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PayPlanActualBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取数据失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	public boolean dealData() {
		try {
			// 实付-------------------------------------------->
			String tCurrentDate = PubFun.getCurrentDate();
			String tCurrentTime = PubFun.getCurrentTime();

			LJAGetDrawSchema tLJAGetDrawSchema = new LJAGetDrawSchema();
			// 核销时重新计算领取金额(考虑万能险催付和分红险在红利公布前后有差异的保单催付)
			double tGetMoney = 0.0;
			PayPlanBL tPayPlanBL;
			LCGetSchema tLCGetSchema = new LCGetSchema();
			LCGetDB tempLCGetDB = new LCGetDB();
			tempLCGetDB.setPolNo(mLJSGetDrawSchema.getPolNo());
			tempLCGetDB.setDutyCode(mLJSGetDrawSchema.getDutyCode());
			tempLCGetDB.setGetDutyCode(mLJSGetDrawSchema.getGetDutyCode());
			if (!tempLCGetDB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PayPlanActualBL";
				tError.functionName = "dealdata";
				tError.errorMessage = "查询领取项表数据失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLCGetSchema = tempLCGetDB.getSchema();
			LCPolSchema tLCPolSchema = new LCPolSchema();
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(mLJSGetDrawSchema.getPolNo());
			if (!tLCPolDB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PayPlanActualBL";
				tError.functionName = "dealdata";
				tError.errorMessage = "查询保单数据失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLCPolSchema = tLCPolDB.getSchema();
			try {
//				tPayPlanBL = new PayPlanBL(tLCPolSchema, mLJSGetDrawSchema
//						.getGetDate());
//				tGetMoney = tPayPlanBL.calGetMoney(tLCGetSchema);
				if (mLJSGetDrawSchema.getGetMoney() != tGetMoney) {
					tLJAGetDrawSchema.setGetMoney(tGetMoney);
				} else {
					tLJAGetDrawSchema.setGetMoney(mLJSGetDrawSchema
							.getGetMoney());
				}
//				if (tPayPlanBL.mErrors.needDealError()) {
//					CError.buildErr(this, "核销时生存领取金计算失败!");
//					return false;
//				}
			} catch (Exception e) {
				e.printStackTrace();
				CError.buildErr(this, "核销时计算生存领取金失败!");
				return false;
			}

			tLJAGetDrawSchema
					.setDestrayFlag(mLJSGetDrawSchema.getDestrayFlag());
			// 生成通知
			// String tGetNoticeNo =
			// PubFun1.CreateMaxNo("GetNoticeNo",PubFun.getNoLimit(mLJSGetDrawSchema.getManageCom()));
			// 实付号
			String tActuGetNo = PubFun1.CreateMaxNo("GetNo", PubFun
					.getNoLimit(mLJSGetDrawSchema.getManageCom()));
			// /--------------------比应付总表多的字段---------------------\
			tLJAGetDrawSchema.setActuGetNo(tActuGetNo);
			// \--------------------------------------------------------/
			tLJAGetDrawSchema
					.setGetNoticeNo(mLJSGetDrawSchema.getGetNoticeNo());
			tLJAGetDrawSchema.setSerialNo(mLJSGetDrawSchema.getSerialNo()); // 不再重新生成
			tLJAGetDrawSchema.setGetDate(mLJSGetDrawSchema.getGetDate());
			tLJAGetDrawSchema.setDutyCode(mLJSGetDrawSchema.getDutyCode());
			tLJAGetDrawSchema
					.setGetDutyCode(mLJSGetDrawSchema.getGetDutyCode());
			tLJAGetDrawSchema
					.setGetDutyKind(mLJSGetDrawSchema.getGetDutyKind());
			tLJAGetDrawSchema.setLastGettoDate(mLJSGetDrawSchema
					.getLastGettoDate());
			tLJAGetDrawSchema
					.setFeeFinaType(mLJSGetDrawSchema.getFeeFinaType());
			tLJAGetDrawSchema.setFeeOperationType(mLJSGetDrawSchema
					.getFeeOperationType());
			tLJAGetDrawSchema.setCurGetToDate(mLJSGetDrawSchema
					.getCurGetToDate());
			// 是否首期给付
			tLJAGetDrawSchema.setGetmFirstFlag(mLJSGetDrawSchema
					.getGetFirstFlag());
			// 给付渠道
			tLJAGetDrawSchema.setGetChannelFlag(mLJSGetDrawSchema
					.getGetChannelFlag());

			tLJAGetDrawSchema.setAppntNo(mLJSGetDrawSchema.getAppntNo());
			tLJAGetDrawSchema.setPolNo(mLJSGetDrawSchema.getPolNo());
			tLJAGetDrawSchema.setGrpName(mLJSGetDrawSchema.getGrpName());
			tLJAGetDrawSchema.setGrpPolNo(mLJSGetDrawSchema.getGrpPolNo());
			tLJAGetDrawSchema.setGrpContNo(mLJSGetDrawSchema.getGrpContNo());
			tLJAGetDrawSchema.setContNo(mLJSGetDrawSchema.getContNo());
			tLJAGetDrawSchema.setPolNo(mLJSGetDrawSchema.getPolNo());
			tLJAGetDrawSchema.setPolType(mLJSGetDrawSchema.getPolType());
			tLJAGetDrawSchema.setInsuredNo(mLJSGetDrawSchema.getInsuredNo());
			tLJAGetDrawSchema.setAgentCode(mLJSGetDrawSchema.getAgentCode());
			tLJAGetDrawSchema.setAgentCom(mLJSGetDrawSchema.getAgentCom());
			tLJAGetDrawSchema.setAgentGroup(mLJSGetDrawSchema.getAgentGroup());
			tLJAGetDrawSchema.setAgentType(mLJSGetDrawSchema.getAgentType());
			tLJAGetDrawSchema.setRiskCode(mLJSGetDrawSchema.getRiskCode());
			tLJAGetDrawSchema.setKindCode(mLJSGetDrawSchema.getKindCode());
			tLJAGetDrawSchema
					.setRiskVersion(mLJSGetDrawSchema.getRiskVersion());

			tLJAGetDrawSchema.setMakeDate(tCurrentDate);
			tLJAGetDrawSchema.setMakeTime(tCurrentTime);
			tLJAGetDrawSchema.setModifyDate(tCurrentDate);
			tLJAGetDrawSchema.setModifyTime(tCurrentTime);
			tLJAGetDrawSchema.setOperator(mGlobalInput.Operator);
			tLJAGetDrawSchema.setManageCom(mLJSGetDrawSchema.getManageCom());
			// 是否生调(上柜)
			tLJAGetDrawSchema
					.setRReportFlag(mLJSGetDrawSchema.getRReportFlag()); // 实际上这里都是不生调的
			// 是否现金领取
			tLJAGetDrawSchema.setComeFlag(mLJSGetDrawSchema.getComeFlag()); // 实际上这里都是不上柜的

			// 总表与子表信息量一致
			LJAGetSchema tLJAGetSchema = new LJAGetSchema();
			// /--------------------比应付总表多的字段---------------------\
			tLJAGetSchema.setActuGetNo(tActuGetNo);
			// tLJAGetSchema.setEnterAccDate();//财务到帐日期，财务领取时才置，下3个同
			// tLJAGetSchema.setConfDate();//财务确认日期
			// tLJAGetSchema.setDrawer();//领取人
			// tLJAGetSchema.setDrawerID();//领取人身份证
			// \--------------------------------------------------------/
			tLJAGetSchema.setGetNoticeNo(mLJSGetSchema.getGetNoticeNo());
			tLJAGetSchema.setSerialNo(mLJSGetSchema.getSerialNo());
			tLJAGetSchema.setAppntNo(mLJSGetSchema.getAppntNo());
			tLJAGetSchema.setOtherNo(mLJSGetSchema.getOtherNo());
			tLJAGetSchema.setOtherNoType(mLJSGetSchema.getOtherNoType());
			tLJAGetSchema.setAgentCode(mLJSGetSchema.getAgentCode());
			tLJAGetSchema.setAgentCom(mLJSGetSchema.getAgentCom());
			tLJAGetSchema.setAgentGroup(mLJSGetSchema.getAgentGroup());
			tLJAGetSchema.setAgentType(mLJSGetSchema.getAgentType());

			// tLJAGetSchema.setSumGetMoney(mLJSGetSchema.getSumGetMoney());
			tLJAGetSchema.setSumGetMoney(tGetMoney);
			// 待处理
			tLJAGetSchema.setShouldDate(mLJSGetSchema.getGetDate());

			tLJAGetSchema.setMakeDate(tCurrentDate);
			tLJAGetSchema.setMakeTime(tCurrentTime);
			tLJAGetSchema.setModifyDate(tCurrentDate);
			tLJAGetSchema.setModifyTime(tCurrentTime);
			tLJAGetSchema.setOperator(mGlobalInput.Operator);
			tLJAGetSchema.setManageCom(mLJSGetSchema.getManageCom());
			// /***************如果领取形式是银行划款，则增加银行划款的相应信息*******Add by
			// Nicholas*****\
			tLJAGetSchema.setBankOnTheWayFlag(mLJSGetSchema
					.getBankOnTheWayFlag());
			tLJAGetSchema.setBankSuccFlag(mLJSGetSchema.getBankSuccFlag());
			tLJAGetSchema.setSendBankCount(mLJSGetSchema.getSendBankCount());
			tLJAGetSchema.setBankCode(mLJSGetSchema.getBankCode());
			tLJAGetSchema.setBankAccNo(mLJSGetSchema.getBankAccNo());
			tLJAGetSchema.setAccName(mLJSGetSchema.getAccName());
			// \***********************************************************************************/
			// 把lcpol表的领取形式信息置到ljsget总表中,核销时置到ljaget中
			tLJAGetSchema.setPayMode(mLJSGetSchema.getPayMode());
			mLJAGetSet.add(tLJAGetSchema);

			// ****************2005-07-19日讨论**********************************************************
			// 比较新的领至日期是否到达了止领日期，如果到了，无论是年金还是满期金，都要修改“止领标志”为“1 领取终止”
			// 这里只处理实付的情况，应付由AG处理
			LCGetSchema tempLCGetSchema = new LCGetSchema();
			LCGetDB tLCGetDB = new LCGetDB();
			tLCGetDB.setPolNo(mLJSGetDrawSchema.getPolNo());
			tLCGetDB.setDutyCode(mLJSGetDrawSchema.getDutyCode());
			tLCGetDB.setGetDutyCode(mLJSGetDrawSchema.getGetDutyCode());
			if (!tLCGetDB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PayPlanActualBL";
				tError.functionName = "dealdata";
				tError.errorMessage = "查询领取项表数据失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			tempLCGetSchema.setSchema(tLCGetDB.getSchema());
			tempLCGetSchema.setGetMode("1");
			tempLCGetSchema.setSumMoney(tempLCGetSchema.getSumMoney()
					+ tGetMoney);
			tempLCGetSchema.setGettoDate(mLJSGetDrawSchema.getCurGetToDate()); // 改变“领至日期”
			FDate tempFData = new FDate();
			// --2005-07-28日彭谦说如果是趸领，则领取时将止领日期改成起领日期，因为原起止日期不等，做如下修改-->
			if (tempLCGetSchema.getGetIntv() == 0) {
				// 趸领
				tempLCGetSchema
						.setGetEndDate(tempLCGetSchema.getGetStartDate());
				tempLCGetSchema.setGettoDate(tempLCGetSchema.getGetStartDate());
				tempLCGetSchema.setGetEndState("1");
			} else {
				// if
				// (tempFData.getDate(mLJSGetDrawSchema.getCurGetToDate()).compareTo(tempFData.getDate(tempLCGetSchema.getGetEndDate()))
				// >= 0)
				// {
				// tempLCGetSchema.setGetEndState("1");
				// }

				String sGetToDate = tempLCGetSchema.getGettoDate(); // 本次领至日期
				String sGetEndDate = tempLCGetSchema.getGetEndDate(); // 止领日期
				// 止领日期往后推11个月，为了兼容月领描述
				String sYearLaterGetEndDate = PubFun.calDate(sGetEndDate, 11,
						"M", null);

				int iIntv = PubFun.calInterval(sYearLaterGetEndDate,
						sGetToDate, "D");
				if (iIntv > 0) {
					tempLCGetSchema.setGetEndState("1");
				}
			}
			tempLCGetSchema.setOperator(this.mGlobalInput.Operator);
			tempLCGetSchema.setModifyDate(tCurrentDate);
			tempLCGetSchema.setModifyTime(tCurrentTime);
			// ***************************115在GM改催付标记,催付核销就不要了*********************************
			// if(OnlineFlag)
			// {
			// if(tempLCGetSchema.getUrgeGetFlag() != null &&
			// tempLCGetSchema.getUrgeGetFlag().trim().equals("N")
			// && tempLCGetSchema.getLiveGetType() != null &&
			// tempLCGetSchema.getLiveGetType().equals("0"))
			// {
			// tempLCGetSchema.setUrgeGetFlag("Y");
			// }
			// }
			// *****************************************************************************************
			// 发通知书。注意：这里类型是"BQ18"是自己定的，还要到PrintManagerBL类添加对应信息
			// LOPRTManagerSchema tLOPRTManagerSchema =
			// this.setLOPRTManager(mLCGetSet.get(j), tSerialNo, "BQ18",
			// tGetNoticeNo,tActuGetNo);
			// mLOPRTManagerSet.add(tLOPRTManagerSchema);

			// /***2005-08-14日发现的重要问题，如果多个批处理对象同时启动，会在应付表里生成多条只有GetNoticeNo不同的数据***\
			// --说明：在这里逐条判断库中是否已有重复数据，如果有就不提交。这个判断实际应该放在循环外面单独做，但那样打印管理表
			// -- 不好处理，而且目前实际此BL类一次只处理一条数据，所以就放在这里了。以后一次处理多条时要改！
			// 查询库中是否已有这笔领取（可能是另一个线程产生的），GetDate相同就认为是同一笔领取-->
			String tSql = "SELECT 'X' FROM LJAGetDraw WHERE PolNo='?PolNo?' and DutyCode='?DutyCode?' and GetDutyKind='?GetDutyKind?' and GetDutyCode='?GetDutyCode?' and GetNoticeNo='?GetNoticeNo?'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("PolNo", mLJSGetDrawSchema.getPolNo());
			sqlbv.put("DutyCode", mLJSGetDrawSchema.getDutyCode());
			sqlbv.put("GetDutyKind", mLJSGetDrawSchema.getGetDutyKind());
			sqlbv.put("GetDutyCode", mLJSGetDrawSchema.getGetDutyCode());
			sqlbv.put("GetNoticeNo", mLJSGetDrawSchema.getGetNoticeNo());
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				// 库中已经存在数据，不再插入
			} else {
				// 库中还没有当前领取
				// /***2005-07-26日讨论***经彭谦同意***做如下处理***************\
				// 原因：LCGet表中起领日期是基于被保人生日计算出的，没有跟保单生效日期
				// （LCPol.CValiDate）比较。所以，如108某险的某领取，领取年龄
				// 为12到17岁，但12岁后投保只能领14到17岁的，算出的起领日期是被
				// 保人12岁对应领取日期，而如果超过12岁投保，保单生效日期就会大
				// 于起领日期，这样计算出的领取金额就是0。
				// 新结果：如果是零都不生调，在催付核销时此条不进实付，只改领至日期
				if (tLJAGetDrawSchema.getGetMoney() != 0) {
					mMap.put(tLJAGetDrawSchema, "DELETE&INSERT");
					mMap.put(tLJAGetSchema, "DELETE&INSERT");
					// 对进实付的数据入帐户
					// try
					// {
					// FinInsuAcc tFinInsuAcc = new FinInsuAcc();
					// String[] tOperationType = new String[1]; //业务类型
					// String[] tMoneyType = new String[1]; //财务类型
					// double[] tMoney = new double[1]; //本次记入实付金额
					// tOperationType[0] =
					// mLJSGetDrawSchema.getFeeOperationType();
					// tMoneyType[0] = mLJSGetDrawSchema.getFeeFinaType();
					// tMoney[0] = mLJSGetDrawSchema.getGetMoney();
					// mMap.put(tFinInsuAcc.createInsuAcc(this.mGlobalInput,
					// mLJSGetDrawSchema.getInsuredNo(), "1",
					// tOperationType, mLJSGetDrawSchema.getContNo(), "2",
					// tMoneyType, tMoney), "DELETE&INSERT");
					// if (tFinInsuAcc.mErrors.needDealError())
					// {
					// this.mErrors.copyAllErrors(tFinInsuAcc.mErrors);
					// return false;
					// }
					// }
					// catch(Exception e)
					// {
					// CError tError = new CError();
					// tError.moduleName = "PayPlanActualBL";
					// tError.functionName = "dealdata";
					// tError.errorMessage = "生成客户帐户信息时产生错误！";
					// this.mErrors.addOneError(tError);
					// return false;
					// }
				}
				// \********************************************************/
				mMap.put(mLJSGetDrawSchema, "DELETE");
				mMap.put(mLJSGetSchema, "DELETE");
				mMap.put(tempLCGetSchema, "UPDATE");
			}
			// \***********************************************************************************************/

			mResult.clear();
			mResult.add(mMap);
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "PayPlanActualBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "数据处理产生错误，数据未提交！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// public void setOnlineFlag()
	// {
	// OnlineFlag = true;
	// }

	public VData getResult() {
		return mResult;
	}
}
