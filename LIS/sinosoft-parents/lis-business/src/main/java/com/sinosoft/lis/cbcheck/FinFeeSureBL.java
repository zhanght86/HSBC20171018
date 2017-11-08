package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统到帐确认部分
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
 * @author WHN
 * @version 1.0
 */
public class FinFeeSureBL {
private static Logger logger = Logger.getLogger(FinFeeSureBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private String CurrentDate = PubFun.getCurrentDate();
//	private String mManageCom;


//	private LCContHangUpStateSet mLCContHangUpStateSet = new LCContHangUpStateSet();
	/** 暂交费表 */
	private LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();
	private LJTempFeeSet mAllLJTempFeeSet = new LJTempFeeSet();

	/** 暂交费关联表 */
	// private LJTempFeeClassSchema mLJTempFeeClassSchema = new
	// LJTempFeeClassSchema();
//	private LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();
	private LJTempFeeClassSet mmLJTempFeeClassSet = new LJTempFeeClassSet();
	private LJTempFeeClassSet mAllLJTempFeeClassSet = new LJTempFeeClassSet();

	public VData getResult() {
		return mInputData;
	}

	public FinFeeSureBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			CError.buildErr(this, "获取数据失败！");
			return false;
		}

		logger.debug("---FinFeeSureBL getInputData---");

		if (!dealData()) {
			CError.buildErr(this, "业务处理失败！");
			return false;
		}

		// LJTempFeeClassSchema tLJTempFeeClassSchema = new
		// LJTempFeeClassSchema();
		// int SetNo = mmLJTempFeeClassSet.size();
		// for (int i = 1; i <= SetNo; i++) {
		// tLJTempFeeClassSchema = mmLJTempFeeClassSet.get(i);
		// mLJTempFeeClassSchema = mmLJTempFeeClassSet.get(i);
		//
		// logger.debug("---FinFeeSureBL checkData---");
		// // 数据操作业务处理
		// if (!dealData(tLJTempFeeClassSchema)) {
		// continue;
		// }
		// // return false;
		// else {
		// flag = 1;
		// }
		// }
		//
		// if (flag == 0) {
		// CError tError = new CError();
		// tError.moduleName = "FinFeeSureBL";
		// tError.functionName = "submitData";
		// tError.errorMessage = "到帐确认失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		//
		// try {
		// if (!setTempfeeAccDate()) {
		// throw new Exception();
		// }
		// } catch (Exception e) {
		// CError tError = new CError();
		// tError.moduleName = "FinFeeSureBL";
		// tError.functionName = "submitData";
		// tError.errorMessage = "更新暂交费表失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }

		logger.debug("---FinFeeSureBL dealData---");
		// 准备给后台的数据
		prepareOutputData();

		PubSubmit ps = new PubSubmit();
		if(!ps.submitData(mInputData,""))
		{
			CError.buildErr(this, "数据提交失败！");
			return false;
		}


		logger.debug("---FinFeeSureBL commitData---");
		return true;
	}

	/**
	 * 设置暂交费表到帐日期
	 * 
	 * @return
	 */
	private boolean setTempfeeAccDate() {
		logger.debug("begin to setTempfeeAccDate");

		HashMap tHashMap = new HashMap(); // 记录每个暂交费号出现的次数，一次表示一种交费
		int num = 0;
		HashMap accDateHashMap = new HashMap(); // 记录每个暂交费号对应的最大到帐日期
		String tDate = "";

		// 统计该批次中的暂交费子表数据（暂交费号－－出现次数）
		for (int i = 0; i < mAllLJTempFeeClassSet.size(); i++) 
		{
			logger.debug("统计该批次中的暂交费子表数据,共有"+ mAllLJTempFeeClassSet.size() + "条记录");
			LJTempFeeClassSchema tLJTempFeeClassSchema = mAllLJTempFeeClassSet.get(i + 1);


			// 出现过，增加一次
			if (tHashMap.containsKey(tLJTempFeeClassSchema.getTempFeeNo())) 
			{
				num = ((Integer) tHashMap.get(tLJTempFeeClassSchema.getTempFeeNo())).intValue() + 1;
				tHashMap.put(tLJTempFeeClassSchema.getTempFeeNo(), new Integer(num));

				// 取最大的到帐日期
				if (PubFun.calInterval(tLJTempFeeClassSchema.getEnterAccDate(),(String) accDateHashMap.get(tLJTempFeeClassSchema.getTempFeeNo()), "D") < 0) 
				{
					accDateHashMap.put(tLJTempFeeClassSchema.getTempFeeNo(),tLJTempFeeClassSchema.getEnterAccDate());
				}
			} 
			else 
			{
				tHashMap.put(tLJTempFeeClassSchema.getTempFeeNo(), new Integer(1));
				accDateHashMap.put(tLJTempFeeClassSchema.getTempFeeNo(),tLJTempFeeClassSchema.getEnterAccDate());
			}
		}

		// 遍历所有的暂交费号
		for (Iterator tIterator = tHashMap.keySet().iterator(); tIterator.hasNext();) 
		{
			logger.debug("遍历所有的暂交费号");
			String tempfeeNo = (String) tIterator.next();

			// 选择出该暂交费号未到帐的数据
			LJTempFeeClassDB tLJTempFeeClassDB2 = new LJTempFeeClassDB();
			String strSql = "select * from LJTempFeeClass where TempFeeNo='"+ "?tempfeeNo?" + "' and EnterAccDate is null";
			logger.debug("查询该暂交费号未到账的数据：" + strSql);
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(strSql);
			sqlbv.put("tempfeeNo", tempfeeNo);
			LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB2.executeQuery(sqlbv);

			// 如果数量相同，表示这次全部到帐，否则表示还有未到帐的数据
			if (tLJTempFeeClassSet.size() == ((Integer) tHashMap.get(tempfeeNo)).intValue()) 
			{
				LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
				LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();

				tLJTempFeeDB.setTempFeeNo(tempfeeNo);
				tLJTempFeeSet = tLJTempFeeDB.query();
				if(tLJTempFeeSet.size()<=0)
				{
					CError.buildErr(this, "暂收据号"+tempfeeNo+"为不存在，请核实！");
					return false;
				}

				// 取暂交费分类中已经到帐的数据的最大到帐日期
				LJTempFeeClassDB tLJTempFeeClassDB3 = new LJTempFeeClassDB();
				strSql = "select * from LJTempFeeClass where TempFeeNo='"+ "?tempfeeNo?" + "' and EnterAccDate = "
					   + "(select max(EnterAccDate) from LJTempFeeClass where TempFeeNo='" + "?tempfeeNo?" + "')";
				logger.debug("取分类表中已到账数据的最大到账日期：" + strSql);
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(strSql);
				sqlbv1.put("tempfeeNo", tempfeeNo);
				LJTempFeeClassSet tLJTempFeeClassSet3 = tLJTempFeeClassDB3.executeQuery(sqlbv1);

				if (tLJTempFeeClassSet3.size() > 0&& (PubFun.calInterval(tLJTempFeeClassSet3.get(1).getEnterAccDate(), (String) accDateHashMap.get(tempfeeNo), "D") < 0)) 
				{
					tDate = tLJTempFeeClassSet3.get(1).getEnterAccDate();
				} else 
				{
					tDate = (String) accDateHashMap.get(tempfeeNo);
				}

				for (int i = 1; i <= tLJTempFeeSet.size(); i++) 
				{
					LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
					tLJTempFeeSchema = tLJTempFeeSet.get(i);

					tLJTempFeeSchema.setEnterAccDate(tDate);
					tLJTempFeeSchema.setConfMakeDate(CurrentDate);
					tLJTempFeeSchema.setConfMakeTime(PubFun.getCurrentTime());
					tLJTempFeeSchema.setModifyDate(CurrentDate);
					tLJTempFeeSchema.setModifyTime(PubFun.getCurrentTime());

					mLJTempFeeSet.add(tLJTempFeeSchema);
				}
			}

			mAllLJTempFeeSet = mLJTempFeeSet;
		}

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		// if (dealOnePol() == false) {
		// logger.debug("fail to dealOnePol");
		// return false;
		// }

		boolean flag = false; // 判断是不是所有数据都不成功
		// 暂收费分类表处理
		for (int i = 1; i <= mmLJTempFeeClassSet.size(); i++) {

			LJTempFeeClassSchema mLJTempFeeClassSchema = new LJTempFeeClassSchema();
			mLJTempFeeClassSchema = mmLJTempFeeClassSet.get(i);

			// // 数据操作业务处理
			if (!prepareFee(mLJTempFeeClassSchema)) {
				continue;
			} else {
				flag = true;
			}
		}

		if (!flag) {
			CError.buildErr(this, "到帐确认失败!");
			return false;
		}
//		mAllLJTempFeeClassSet = OutLJTempFeeClassSet;

		// 更新暂交费表
		if (!setTempfeeAccDate()) {
			CError.buildErr(this, "更新暂交费表失败!");
			return false;
		}

		return true;
	}

	/**
	 * 操作一张保单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	// private boolean dealOnePol() {
	// logger.debug("begin to dealOnePol");
	// // 健康信息
	// if (prepareFee() == false) {
	// logger.debug("fail to prepareFee");
	// return false;
	// }
	// // zy 2007-12-29
	// mAllLJTempFeeClassSet = OutLJTempFeeClassSet;
	//
	// return true;
	// }
	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {

		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		mmLJTempFeeClassSet = (LJTempFeeClassSet) cInputData
				.getObjectByObjectName("LJTempFeeClassSet", 0);

		if (mGlobalInput == null) {
			CError.buildErr(this, "获取登陆信息失败，请重新登陆！");
			return false;
		}

		if (mmLJTempFeeClassSet == null) {
			CError.buildErr(this, "获取暂收费信息失败！");
			return false;
		}
		return true;
	}

	/**
	 * 准备体检资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareFee(LJTempFeeClassSchema mLJTempFeeClassSchema) {
		logger.debug("begin to prepareFee");
		/**
		 * 暂交费关联表
		 */

		// 到帐日期
		String tDate = "";
		tDate = mLJTempFeeClassSchema.getEnterAccDate();
		LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
		
	//xuyunpeng add	
		String tSql = "select currency,paymode from LJTempFeeClass where 1 =1 "
			+ " and TempFeeNo='"
			+ "?TempFeeNo?' and chequeno ='?cheque?'";

	logger.debug("--按保单、理赔类型进行的分组信息，分别进行计算:"+tSql);
	SQLwithBindVariables sqlbv5 =new SQLwithBindVariables();
	sqlbv5.sql(tSql);
	sqlbv5.put("TempFeeNo", mLJTempFeeClassSchema.getTempFeeNo());
	sqlbv5.put("cheque", mLJTempFeeClassSchema.getChequeNo());
	ExeSQL tExeSQL = new ExeSQL();
	SSRS tSSRS = tExeSQL.execSQL(sqlbv5);
		
		if(tSSRS!=null){
			String[][] aCurrency =tSSRS.getAllData();
			mLJTempFeeClassSchema.setCurrency(aCurrency[0][0]);
			mLJTempFeeClassSchema.setPayMode(aCurrency[0][1]);
			
		}
		tLJTempFeeClassDB.setSchema(mLJTempFeeClassSchema);
		String tTempFeeNo = mLJTempFeeClassSchema.getTempFeeNo();
		if (tLJTempFeeClassDB.getInfo() == false) {
			// @@错误处理
			CError.buildErr(this, "没有" + tTempFeeNo + "收据!");
			return false;
		}

		tLJTempFeeClassSchema = tLJTempFeeClassDB.getSchema();

		if (tDate.compareTo(tLJTempFeeClassSchema.getMakeDate()) < 0) {
			CError.buildErr(this, tTempFeeNo + "收据,到帐日期小于入机日期");
			return false;
		}
		if (tDate.compareTo(PubFun.getCurrentDate()) > 0) {
			CError.buildErr(this, tTempFeeNo + "收据,到帐日期大于系统日期");
			return false;
		}
		tLJTempFeeClassSchema.setEnterAccDate(tDate);
		tLJTempFeeClassSchema.setConfMakeDate(PubFun.getCurrentDate());
		tLJTempFeeClassSchema.setConfMakeTime(PubFun.getCurrentTime());
		tLJTempFeeClassSchema.setModifyDate(PubFun.getCurrentDate());
		tLJTempFeeClassSchema.setModifyTime(PubFun.getCurrentTime());

		mAllLJTempFeeClassSet.add(tLJTempFeeClassSchema);
		/** @todo --------续期支票支付，解除挂起状态-------- */
		// if (tLJTempFeeClassSchema.getOtherNoType().equals("2") ||
		// tLJTempFeeClassSchema.getOtherNoType().equals("3"))
		// {
		// RNHangUp tRNHangUp = new RNHangUp(mGlobalInput);
		// LCContHangUpStateSchema tLCContHangUpStateSchema = new
		// LCContHangUpStateSchema();
		// tLCContHangUpStateSchema = tRNHangUp.undoHangUp(
		// tLJTempFeeClassSchema.getOtherNo());
		// if (tLCContHangUpStateSchema != null)
		// mLCContHangUpStateSet.add(tLCContHangUpStateSchema);
		// }
		/**
		 * @todo -------- 预交续期保费 更新LCCont-------UPDATE LCCont.Dif 客户预存
		 *       插入LJAGet-------INSERT LJAGet& &LJAGetTempFee-------INSERT
		 *       LJAGetTempFee
		 */
		// LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		// LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		// String Sql = "select * from LJTempFee where TempFeeNo = '" +
		// tLJTempFeeClassSchema.getTempFeeNo() + "'";
		// logger.debug("查询需要的到账后业务操作:::" + Sql);
		// tLJTempFeeSet = tLJTempFeeDB.executeQuery(Sql);
		// if (tLJTempFeeSet.size() == 0)
		// //continue;
		// {
		// CError tError = new CError();
		// tError.moduleName = "FinFeeSureBL";
		// tError.functionName = "prepareFee";
		// tError.errorMessage = "没有需要到账确认的数据，请确认输入条件。";
		// logger.debug("没有需要到账确认的数据，请确认输入条件。");
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// for (int i = 1; i <= tLJTempFeeSet.size(); i++)
		// {
		// LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
		// tLJTempFeeSchema = tLJTempFeeSet.get(i);
		// logger.debug("tLJTempFeeSchema:" +
		// tLJTempFeeSchema.encode());
		// tLJTempFeeSchema.setEnterAccDate(CurrentDate);
		// /**@todo ----处理保单表LCCont----UPDATE LCCont.Dif*/
		// if (tLJTempFeeSchema.getTempFeeType().equals("3"))
		// {
		// logger.debug("预交续期保费，开始处理LCCont中的Dif字段。");
		// Flag = "3";
		// mLCContDB.setContNo(tLJTempFeeSchema.getOtherNo());
		// if (!mLCContDB.getInfo())
		// {
		// CError tError = new CError();
		// tError.moduleName = "FinFeeSureBL";
		// tError.functionName = "prepareFee";
		// tError.errorMessage = "预交续期保费时未找到保单相关信息!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// mLCContSchema = mLCContDB.getSchema();
		// logger.debug("LCCont中的Dif原有金额：" +
		// mLCContSchema.getDif());
		// logger.debug("客户预存的金额为：" +
		// tLJTempFeeSchema.getPayMoney());
		// double Dif = mLCContSchema.getDif() +
		// tLJTempFeeSchema.getPayMoney();
		// logger.debug("处理完后的帐余额为：" + Dif);
		// mLCContSchema.setDif(Dif);
		// mLCContSet.add(mLCContSchema);
		// logger.debug("更新了LCCont中的" + mLCContSet.size() +
		// "条记录。");
		// logger.debug("更新了" + mLCContSet.get(1).encode());
		//
		// tLJTempFeeSchema.setConfDate(CurrentDate);
		// tLJTempFeeSchema.setConfFlag("1");
		// tLJTempFeeSchema.setModifyDate(CurrentDate);
		// tLJTempFeeSchema.setModifyTime(CurrentTime);
		// tLJTempFeeClassSchema.setConfDate(CurrentDate);
		// tLJTempFeeClassSchema.setConfFlag("1");
		// tLJTempFeeClassSchema.setEnterAccDate(CurrentDate);
		// tLJTempFeeClassSchema.setModifyDate(CurrentDate);
		// tLJTempFeeClassSchema.setModifyTime(CurrentTime);
		// logger.debug("客户预存，到账即核销。");
		//
		// }
		//
		// /**@todo ----处理LJAGet&LJAGetTempFee----INSERT*/
		// if (tLJTempFeeSchema.getTempFeeType().equals("5"))
		// {
		// logger.debug("客户预存，开始处理LJAGet&LJAGetTempFee");
		// Flag = "5";
		// tLimit = PubFun.getNoLimit(tLJTempFeeSchema.
		// getManageCom()); //产生通知书号即暂交费号
		// logger.debug("本次备注信息:::::::::" +
		// tLJTempFeeSchema.getRemark());
		// String tName = tLJTempFeeSchema.getAPPntName();
		// String tIDNo = "";
		// String nNo = PubFun1.CreateMaxNo("GETNO", tLimit);
		//
		// mLJAGetSchema.setActuGetNo(nNo);
		// mLJAGetSchema.setAgentCode(tLJTempFeeSchema.
		// getAgentCode());
		// mLJAGetSchema.setAgentCom(tLJTempFeeSchema.getAgentCom());
		// mLJAGetSchema.setAgentGroup(tLJTempFeeSchema.
		// getAgentGroup());
		// mLJAGetSchema.setDrawer(tName);
		// mLJAGetSchema.setDrawerID(tIDNo);
		// mLJAGetSchema.setOtherNo(tLJTempFeeSchema.getOtherNo());
		// mLJAGetSchema.setGetNoticeNo(tLJTempFeeSchema.
		// getTempFeeNo());
		// mLJAGetSchema.setOtherNoType("YC");
		// mLJAGetSchema.setPayMode("1");
		// mLJAGetSchema.setShouldDate(CurrentDate);
		// mLJAGetSchema.setStartGetDate(CurrentDate);
		// mLJAGetSchema.setSumGetMoney(tLJTempFeeSchema.
		// getPayMoney());
		// String tSo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
		// mLJAGetSchema.setSerialNo(tSo);
		// mLJAGetSchema.setMakeDate(CurrentDate);
		// mLJAGetSchema.setMakeTime(CurrentTime);
		// mLJAGetSchema.setManageCom(tLJTempFeeSchema.
		// getManageCom());
		// mLJAGetSchema.setModifyDate(CurrentDate);
		// mLJAGetSchema.setModifyTime(CurrentTime);
		// mLJAGetSchema.setOperator(mGlobalInput.Operator);
		//
		// mLJAGetTempFeeSchema.setMakeDate(CurrentDate);
		// mLJAGetTempFeeSchema.setMakeTime(CurrentTime);
		// mLJAGetTempFeeSchema.setManageCom(tLJTempFeeSchema.
		// getManageCom());
		// mLJAGetTempFeeSchema.setModifyDate(CurrentDate);
		// mLJAGetTempFeeSchema.setModifyTime(CurrentTime);
		// mLJAGetTempFeeSchema.setOperator(mGlobalInput.Operator);
		// mLJAGetTempFeeSchema.setActuGetNo(nNo);
		// mLJAGetTempFeeSchema.setTempFeeNo(tLJTempFeeSchema.
		// getTempFeeNo());
		// mLJAGetTempFeeSchema.setRiskCode("000000");
		// mLJAGetTempFeeSchema.setTempFeeType("5");
		// mLJAGetTempFeeSchema.setFeeOperationType("YC");
		// mLJAGetTempFeeSchema.setFeeFinaType("YC");
		// mLJAGetTempFeeSchema.setPayMode("Y");
		// mLJAGetTempFeeSchema.setGetMoney(tLJTempFeeSchema.
		// getPayMoney());
		// mLJAGetTempFeeSchema.setGetDate(CurrentDate);
		// mLJAGetTempFeeSchema.setAgentCom(tLJTempFeeSchema.
		// getAgentCom());
		// mLJAGetTempFeeSchema.setAgentCode(tLJTempFeeSchema.
		// getAgentCode());
		//
		// mLJAGetTempFeeSchema.setAgentGroup(tLJTempFeeSchema.
		// getAgentGroup());
		// mLJAGetTempFeeSchema.setAPPntName(tName);
		// mLJAGetTempFeeSchema.setSerialNo(tSo);
		// mLJAGetTempFeeSchema.setGetReasonCode("YC");
		// mLJAGetSet.add(mLJAGetSchema);
		// mLJAGetTempFeeSet.add(mLJAGetTempFeeSchema);
		// }
		// mAllLJTempFeeSet.add(tLJTempFeeSchema);
		// }
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		mInputData.clear();
		MMap map = new MMap();
//		if (Flag == "3") {
//			map.put(mLCContSet, "UPDATE");
//			logger.debug("LCCont中的Dif处理完毕。");
//		}
//		if (Flag == "5") {
//			map.put(mLJAGetSet, "INSERT");
//			map.put(mLJAGetTempFeeSet, "INSERT");
//		}
		map.put(mAllLJTempFeeSet, "UPDATE");
		map.put(mAllLJTempFeeClassSet, "UPDATE");
//		if (mLCContHangUpStateSet.size() > 0)
//			map.put(mLCContHangUpStateSet, "DELETE");
//		mInputData.add(mLCContHangUpStateSet);
		mInputData.add(map);
	}
}
