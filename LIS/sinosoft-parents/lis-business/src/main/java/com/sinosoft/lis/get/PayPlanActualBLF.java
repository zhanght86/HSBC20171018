package com.sinosoft.lis.get;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 生存领取核销批处理</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author Nicholas
 * @version 1.0
 */


import com.sinosoft.lis.db.LJSGetDrawDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSGetDrawSchema;
import com.sinosoft.lis.vschema.LJSGetDrawSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.RSWrapper;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PayPlanActualBLF {
private static Logger logger = Logger.getLogger(PayPlanActualBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	// private MMap mMap = new MMap();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	private GlobalInput mGlobalInput = new GlobalInput();
	private LCGetSchema mLCGetSchema = new LCGetSchema();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	/** 控制信息传输类 */
	private TransferData mTransferData = new TransferData();
	/** 参数描述 */
	private String mTimeEnd;
	// private boolean OnlineFlag = false;
	// 后面信息可不输入
	private String mContNo;
	private String mInsuredNo;
	private String mManageCom;

	public PayPlanActualBLF() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// ***处理一条，提交一条
		SQLwithBindVariables condSql = initCondGet();
		LJSGetDrawDB tLJSGetDrawDB = new LJSGetDrawDB();
		int tUnitDo = 1;
		LJSGetDrawSet tLJSGetDrawSet = null;
		int tCount = 0;
		// 提交的条数
		int tSuccessNum = 0;
		// 处理错误的条数
		int tDealErrorNum = 0;
		// 提交错误的条数
		int tSubmitErrorNum = 0;
		PayPlanActualBL tPayPlanActualBL = null;
		LJSGetDrawSchema tLJSGetDrawSchema = null;
		VData tempInputData = null;
		PubSubmit tSubmit = null;
		// /*************据说改成这种方式快，就改了*********************************\
		tLJSGetDrawSet = new LJSGetDrawSet();
		RSWrapper rsWrapper = new RSWrapper();
		// 传入结果集合,SQL
		if (!rsWrapper.prepareData(tLJSGetDrawSet, condSql)) {
			this.mErrors.copyAllErrors(rsWrapper.mErrors);
			logger.debug(rsWrapper.mErrors.getFirstError());
			return false;
		}
		// \********************************************************************/

		do {
			// tLJSGetDrawSet = new LJSGetDrawSet();
			// tLJSGetDrawSet = tLJSGetDrawDB.executeQuery(condSql,tUnitDo,100);
			rsWrapper.getData();
			if (tLJSGetDrawSet != null && tLJSGetDrawSet.size() > 0) {
				tCount += tLJSGetDrawSet.size();

				for (int j = 1; j <= tCount; j++) {
					tLJSGetDrawSchema = new LJSGetDrawSchema();
					tLJSGetDrawSchema.setSchema(tLJSGetDrawSet.get(j));
					tempInputData = new VData();
					tempInputData.add(mGlobalInput);
					tempInputData.add(tLJSGetDrawSchema);
					tPayPlanActualBL = new PayPlanActualBL();
					// if(OnlineFlag)
					// {
					// tPayPlanActualBL.setOnlineFlag();
					// }
					if (!tPayPlanActualBL.submitData(tempInputData, mOperate)) {
						this.mErrors.copyAllErrors(tPayPlanActualBL.mErrors);
						logger.debug(mErrors.getFirstError() + " 保单号："
								+ tLJSGetDrawSchema.getContNo() + " 险种号："
								+ tLJSGetDrawSchema.getPolNo() + " 责任编码："
								+ tLJSGetDrawSchema.getDutyCode() + " 给付责任编码："
								+ tLJSGetDrawSchema.getGetDutyCode());
						tDealErrorNum++;
						continue;
					}
					mResult.clear();
					mResult = tPayPlanActualBL.getResult();
					tSubmit = new PubSubmit();
					if (!tSubmit.submitData(mResult, "")) {
						// @@错误处理
						this.mErrors.copyAllErrors(tSubmit.mErrors);
						logger.debug(mErrors.getFirstError() + " 保单号："
								+ tLJSGetDrawSchema.getContNo() + " 险种号："
								+ tLJSGetDrawSchema.getPolNo() + " 责任编码："
								+ tLJSGetDrawSchema.getDutyCode() + " 给付责任编码："
								+ tLJSGetDrawSchema.getGetDutyCode());
						tSubmitErrorNum++;
						continue;
					}
					tSuccessNum++;
				}
				// mResult.clear();
			} else {
				if (tUnitDo == 1) {
					CError tError = new CError();
					tError.moduleName = "PayPlanActualBLF";
					tError.functionName = "submitData";
					tError.errorMessage = "没有相关条件的记录！";
					this.mErrors.addOneError(tError);
					// 添加结果信息返回到界面
					mTransferData = new TransferData();
					mTransferData.setNameAndValue("Count", String
							.valueOf(tCount));
					mTransferData.setNameAndValue("SuccessNum", String
							.valueOf(tSuccessNum));
					mTransferData.setNameAndValue("DealErrorNum", String
							.valueOf(tDealErrorNum));
					mTransferData.setNameAndValue("SubmitErrorNum", String
							.valueOf(tSubmitErrorNum));
					mResult.clear();
					mResult.add(mTransferData);
					return false;
				} else {
					logger.debug("产生记录条数：" + String.valueOf(tCount));
					logger.debug("提交成功条数：" + String.valueOf(tSuccessNum));
					logger.debug("处理失败条数："
							+ String.valueOf(tDealErrorNum));
					logger.debug("提交失败条数："
							+ String.valueOf(tSubmitErrorNum));
					// 添加结果信息返回到界面
					mTransferData = new TransferData();
					mTransferData.setNameAndValue("Count", String
							.valueOf(tCount));
					mTransferData.setNameAndValue("SuccessNum", String
							.valueOf(tSuccessNum));
					mTransferData.setNameAndValue("DealErrorNum", String
							.valueOf(tDealErrorNum));
					mTransferData.setNameAndValue("SubmitErrorNum", String
							.valueOf(tSubmitErrorNum));
					mResult.clear();
					mResult.add(mTransferData);
				}
			}
			tUnitDo = tUnitDo + SysConst.FETCHCOUNT;
		} while (tLJSGetDrawSet != null && tLJSGetDrawSet.size() > 0);

		rsWrapper.close();

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		try {
			mTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);
			mTimeEnd = (String) mTransferData.getValueByName("timeEnd");
			// String Online = (String)
			// mTransferData.getValueByName("OnLineFlag");
			// if(Online != null && Online.equals("Yes"))
			// {
			// OnlineFlag = true;
			// }

		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "PayPlanActualBLF";
			tError.functionName = "getInputData";
			tError.errorMessage = "未获得核销结束日期！";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 获得其它条件信息
		try {
			mContNo = (String) mTransferData.getValueByName("ContNo");
		} catch (Exception ex) {
		}
		try {
			mInsuredNo = (String) mTransferData.getValueByName("InsuredNo");
		} catch (Exception ex) {
		}
		try {
			mManageCom = (String) mTransferData.getValueByName("ManageCom");
			// String Online = (String)
			// mTransferData.getValueByName("OnLineFlag");
		} catch (Exception ex) {
		}
		return true;
	}

	/**
	 * 初始化查询条件
	 * 
	 * @param Schema
	 * @return
	 */
	private SQLwithBindVariables initCondGet() {
		String tSql = "SELECT *"
				+ " FROM LJSGetDraw a"
				+ " WHERE not exists(select 'X' from LJAGetDraw where PolNo=a.PolNo and DutyCode=a.DutyCode and GetDutyCode=a.GetDutyCode and GetDutyKind=a.GetDutyKind and GetNoticeNo=a.GetNoticeNo)"
				+ " and GetDate<=to_date('?mTimeEnd?','YYYY-MM-DD')"
				+ " and (case when ComeFlag is not null then ComeFlag else 'x' end)<>'1'" + " and RReportFlag='0'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.put("mTimeEnd", mTimeEnd);
		if (mManageCom != null && mManageCom.trim().length() > 0) {
			if (mGlobalInput.ManageCom.length() >= 6) {
				tSql = tSql + " and ManageCom like concat('?ManageCom?','%')";
				sqlbv.put("", mManageCom.trim().substring(0, 6));
			} else {
				tSql = tSql + " and ManageCom like concat('?ManageCom?','%')";
				sqlbv.put("", mManageCom.trim());
			}
		}
		if (mContNo != null && mContNo.trim().length() > 0) {
			tSql = tSql + " and ContNo='?ContNo?'";
			sqlbv.put("ContNo", mContNo.trim());
		}
		if (mInsuredNo != null && mInsuredNo.trim().length() > 0) {
			tSql = tSql + " and InsuredNo='?InsuredNo?'";
			sqlbv.put("InsuredNo", mInsuredNo.trim());
		}
		sqlbv.sql(tSql);
		return sqlbv;
	}

	public static void main(String[] args) {
		PayPlanActualBLF tPayPlanActualBLF = new PayPlanActualBLF();
		CErrors tError = new CErrors();
		String FlagStr = "";
		String Content = "";
		String Count = "";

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";

		// LCPolSchema tLCPolSchema=new LCPolSchema();

		// LCGetSchema tLCGetSchema=new LCGetSchema();

		// String pattern="yyyy-MM-dd";
		// SimpleDateFormat df = new SimpleDateFormat(pattern);
		// Date today = new Date();
		// Date finday;
		// finday=PubFun.calDate(today,1,"M",null); //1个月内
		// finday=PubFun.calDate(today,30,"D",null); //30天
		// String timeEnd = df.format(finday); //催付结束日期
		TransferData aTransferData = new TransferData();
		aTransferData.setNameAndValue("timeEnd", PubFun.getCurrentDate());

		VData tVData = new VData();
		tVData.addElement(tGlobalInput);
		tVData.addElement(aTransferData);
		// tVData.addElement(tLCPolSchema);
		// tVData.addElement(tLCGetSchema);

		logger.debug("催付核销批处理程序启动...");
		try {
			tPayPlanActualBLF.submitData(tVData, "PCL||MAIN");
		} catch (Exception ex) {
			Content = "保存失败，原因是:" + ex.toString();
			FlagStr = "Fail";
		}

		// 如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "") {
			tError = tPayPlanActualBLF.mErrors;
			if (!tError.needDealError()) {
				Content = "保存成功！";
				// else
				// {
				// Content = "催付失败：已经产生催付记录！";
				// FlagStr = "Fail";
				// }
			} else {
				Content = "保存产生错误，原因是:" + tError.getFirstError();
				FlagStr = "Fail";
			}
		}
		logger.debug(Content);
		// if (tPayPlanUI.getResult()!=null&&tPayPlanUI.getResult().size()>0)
		// {
		// logger.debug("------SerialNo:"+tPayPlanUI.getResult().get(0));
		// }
		logger.debug("催付核销批处理程序结束！");
	}
}
