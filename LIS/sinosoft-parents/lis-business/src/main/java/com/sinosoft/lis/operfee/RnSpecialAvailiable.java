package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LPRevalidateTrackDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPRevalidateTrackSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LPRevalidateTrackSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author Gaoht
 * @version 1.0
 */
public class RnSpecialAvailiable implements BusinessService{
private static Logger logger = Logger.getLogger(RnSpecialAvailiable.class);
	public CErrors mErrors = new CErrors();

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();

	/** 往后面传输数据的容器 */
	private VData mResult = new VData();

	/** 往界面传输数据的容器 */
	private String CurrentDate = PubFun.getCurrentDate();

	private String CurrentTime = PubFun.getCurrentTime();

	private GlobalInput mGlobalInput = new GlobalInput();

	/** 全局数据 */
	private TransferData mTransferData = new TransferData();

	private MMap mMMap = new MMap();

	/** 往后面传输的数据库操作 */
	private String mContNo = ""; // 合同号
	private String mPayToDate = ""; // 当期交至日期，仅仅复效这一期的险种。
	

	private LCContSchema mLCContSchema = new LCContSchema();

	private LJSPaySet mLJSPaySet = new LJSPaySet();

	public RnSpecialAvailiable() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("特殊复效开始RnSpecialAvailiable.submitData-----");

		if (!getInputData(cInputData, cOperate)) 
		{
			return false;
		}

		if (!checkData()) 
		{
			return false;
		}

		if (!dealData()) 
		{
			return false;
		}

		if (!prepareOutputData()) 
		{
			return false;
		}

		if (!pubSubmit()) 
		{
			return false;
		}


		logger.debug("特殊复效结束RnSpecialAvailiable.submitData---------");

		return true;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		this.mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		this.mContNo = (String) mTransferData.getValueByName("ContNo"); // 合同号	
		this.mPayToDate = (String) mTransferData.getValueByName("PayToDate"); // 合同号

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "无此保单信息");
			return false;
		}
		mLCContSchema = tLCContDB.getSchema();
		return true;
	}

	private boolean checkData() {
		// 判断是否被挂起
		RNHangUp tRNHangUp = new RNHangUp(mGlobalInput); // XinYQ added on
		if (!tRNHangUp.checkHangUP(mContNo)) {
			this.mErrors.copyAllErrors(tRNHangUp.mErrors);
			return false;
		}
        /*
		// 应收数据目前银行在途中不能做特殊复效
		LJSPaySet tLJSPaySet = new LJSPaySet();
		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPayDB.setOtherNo(mContNo);
		tLJSPaySet = tLJSPayDB.query();
		if (tLJSPaySet.size() <= 0) 
		{
			CError.buildErr(this, "无应收记录，不能复效");
			return false;
		}
		if (tLJSPaySet.get(1).getBankOnTheWayFlag() != null) {
			if (tLJSPaySet.get(1).getBankOnTheWayFlag().equals("1")) {
				CError.buildErr(this, "应收数据目前银行在途中,不能撤销!");
				return false;
			}
		}
		*/
		return true;
	}

	private boolean dealData() {
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet = Entrance(mContNo);
		if (tLCPolSet.size() == 0) {
			CError.buildErr(this, "此保单下没有符合复效条件的险种信息");
			return false;
		}
		logger.debug("当前处理保单号："+mContNo);
		for (int i = 1; i <= tLCPolSet.size(); i++) 
		{
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = tLCPolSet.get(i);
			logger.debug("当前处理险种："+tLCPolSchema.getRiskCode());
			if(tLCPolSchema.getPolNo().equals(tLCPolSchema.getMainPolNo())
			    &&tLCPolSchema.getAppFlag().endsWith("4"))
			{
				if(tLCPolSchema.getPayIntv()!=0 ||tLCPolSchema.getRnewFlag()!=-1)
				{
					CError.buildErr(this, "主险状态终止，但是非续保件，此为问题数据！");
					return false;
				}
                //主险续保复效需要同时将lccont表的appflag从4置为1(主险续保会出现此种现象)
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql("update lccont set appflag='1' where contno='?mContNo?'");
				sqlbv.put("mContNo", mContNo);
				mMMap.put(sqlbv,"UPDATE");
			}
					
			// 处理长险
			if (tLCPolSchema.getAppFlag().endsWith("1")) 
			{
				if (!ReLongRisk(tLCPolSchema.getPolNo())) 
				{
					return false;
				}
			}
			// 处理续保件
			else 
			{
				if (!ReShortRisk(tLCPolSchema.getPolNo())) 
				{
					return false;
				}
			}
			
		}
		// 处理保单复效的轨迹
		if (!RevalidateTrack()) 
		{
			return false;
		}
        //修改应收记录
		if (!UpdateSpay()) 
		{
			return false;
		}
		// 删除保单失效通知书
		if (!DealPrtManager()) 
		{
			return false;
		}

		return true;
	}

	/*-----------------------------
	 * 修改应收记录
	 *-----------------------------
	 */
	private boolean UpdateSpay() {
		String tSql = "select * from ljtempfee where 1=1 and exists (select 1 from ljspay where getnoticeno=ljtempfee.tempfeeno and otherno='?otherno?' and confdate is null) and otherno='?otherno?'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("otherno", mLCContSchema.getContNo());
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv1);
		if (tLJTempFeeSet.size() > 0)
			return true;

		//调用续期催收撤销程序，但不在催收撤销中提交数据，返回需要处理的集合，在复效程序中一并提交		
		VData tVData = new VData();
		tVData.add(mLCContSchema);
		tVData.add(mGlobalInput);
		
		IndiDueFeeCancelBL tIndiDueFeeCancelBL = new IndiDueFeeCancelBL();
		tIndiDueFeeCancelBL.submitData(tVData, "RNSpecialA");

		mMMap.add(tIndiDueFeeCancelBL.getMap());
		return true;
	}

	/*------------------------------------
	 * 删除失效通知书数据
	 * -----------------------------------
	 */
	private boolean DealPrtManager() {
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql("delete from loprtmanager where otherno='?otherno?' and code='42'");
		sqlbv2.put("otherno", mLCContSchema.getContNo());
		mMMap.put(sqlbv2, "DELETE");
		return true;
	}

	/*---------------------------
	 * 记录保单复效的轨迹
	 *---------------------------
	 */
	private boolean RevalidateTrack() {
		// 准备记录轨迹
		LPRevalidateTrackSchema tLPRevalidateTrackSchema = new LPRevalidateTrackSchema();
		LPRevalidateTrackDB tLPRevalidateTrackDB = new LPRevalidateTrackDB();
		LPRevalidateTrackSet tLPRevalidateTrackSet = new LPRevalidateTrackSet();
		// 取出复效轨迹的最大值
		tLPRevalidateTrackSchema.setContNo(mContNo);
		tLPRevalidateTrackDB.setSchema(tLPRevalidateTrackSchema);
		tLPRevalidateTrackSet = tLPRevalidateTrackDB.query();
		tLPRevalidateTrackSchema.setRevalidateTimes(tLPRevalidateTrackSet
				.size() + 1);
		// 失效原因
		if (mTransferData.getValueByName("InvalidReason") == null
				|| mTransferData.getValueByName("InvalidReason").equals("")) {
			CError.buildErr(this, "无法获取保单失效的原因信息！");
			logger.debug("\t@> LRNSpecialAvailableBL.dealData() : TransferData.getValueByName(\"InvalidReason\") 失败！");
			return false;
		} else
			tLPRevalidateTrackSchema.setInvalidReason(((String) mTransferData
					.getValueByName("InvalidReason")).trim());
		// 管理机构
		tLPRevalidateTrackSchema.setManageCom(mLCContSchema.getManageCom());
		// 复效备注
		if (mTransferData.getValueByName("Remark") != null
				&& !mTransferData.getValueByName("Remark").equals(""))
			tLPRevalidateTrackSchema.setRemark(((String) mTransferData
					.getValueByName("Remark")).trim());
		// 常规字段的赋值
		tLPRevalidateTrackSchema.setOperator(mGlobalInput.Operator);
		tLPRevalidateTrackSchema.setMakeDate(CurrentDate);
		tLPRevalidateTrackSchema.setMakeTime(CurrentTime);
		tLPRevalidateTrackSchema.setModifyDate(CurrentDate);
		tLPRevalidateTrackSchema.setModifyTime(CurrentTime);

		LPRevalidateTrackSet nLPRevalidateTrackSet = new LPRevalidateTrackSet();
		nLPRevalidateTrackSet.add(tLPRevalidateTrackSchema);

		// 提交更新
		mMMap.put(nLPRevalidateTrackSet, "INSERT");
		return true;
	}

	/*------------------------------
	 * 对长险进行处理
	 * 长险为失效
	 *------------------------------
	 */
	private boolean ReLongRisk(String tPolNo) {
		logger.debug("###############################################");
		logger.debug("delete from lccontstate where state='1' and statetype = 'Available' and enddate is null and polno='"
						+ tPolNo + "'");
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql("delete from lccontstate where state='1' and statetype = 'Available' and enddate is null and polno='?tPolNo?'");
		sqlbv3.put("tPolNo", tPolNo);
		mMMap.put(sqlbv3, "DELETE");
		logger.debug("###############################################");

		logger.debug("update lccontstate set enddate = '' where state='0' and statetype = 'Available' and enddate is not null and polno='"
						+ tPolNo
						+ "' and enddate = (select max(enddate) from lccontstate where state='0' and statetype = 'Available' and enddate is not null and polno = '"
						+ tPolNo + "')");
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql("update lccontstate set enddate = '' where state='0' and statetype = 'Available' and enddate is not null and polno='?tPolNo?' and enddate =(select s from (select max(enddate) as s from lccontstate where state='0' and statetype = 'Available' and enddate is not null and polno = '?tPolNo?') g)");
		sqlbv4.put("tPolNo", tPolNo);
		mMMap.put(sqlbv4, "UPDATE");
		
		//mMMap.put("update lcpol set appflag='1' where polno='" + tPolNo + "'","UPDATE");
		return true;
	}

	/*-------------------------------
	 * 对短期续保险进行处理
	 * 终止
	 *------------------------------
	 */
	private boolean ReShortRisk(String tPolNo) {
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql("delete from lccontstate where state='1' and statetype ='Terminate' and enddate is null and polno='?tPolNo?'");
		sqlbv5.put("tPolNo", tPolNo);
		mMMap.put(sqlbv5, "DELETE");
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql("update lcpol set appflag='1',operator='RnSA' where polno='?tPolNo?'");
		sqlbv6.put("tPolNo", tPolNo);
		mMMap.put(sqlbv6,"UPDATE");
		return true;
	}

	/*-------------------------------------
	 * 获得业务操作入口数据
	 * @return LCPolSet
	 *------------------------------------
	 */
	private LCPolSet Entrance(String tContNo) {
		LCPolSet tLCPolSet = new LCPolSet();
		String tSql = "select * from lcpol a where a.contno='?contno?' and a.appflag in ('1','4') "
		+" and a.paytodate='?paytodate?' and exists "
        + " (select 'X' from LCContState b where "
           +" b.statetype ='Available' and b.polno=a.polno  and b.state='1' and b.statereason in ('01','02','03','04') and b.enddate is null  "
           +" union " 
           +" select 'X' from LCContState b where "
           +" b.statetype ='Terminate' and b.polno=a.polno  and b.state='1' and b.statereason in ('01','07') and b.enddate is null  ) "
        + " order by a.appflag asc ";
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		sqlbv7.sql(tSql);
		sqlbv7.put("contno", this.mContNo);
		sqlbv7.put("paytodate", this.mPayToDate);
		logger.debug("查询需要复效操作的险种信息:::::::::::::" + tSql);
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolSet = tLCPolDB.executeQuery(sqlbv7);
		return tLCPolSet;
	}

	/*---------------------------------
	 * 准备需要保存的数据
	 * @return boolean
	 *---------------------------------
	 */
	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(mMMap);
		return true;
	}

	/*-------------------------------------
	 * 提交数据
	 * @return
	 *-------------------------------------
	 */
	private boolean pubSubmit() {
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimCalMatchFilterBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/*
	 * 得到返回的结果集 @return
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 测试主方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "86";

		String tConNo = "BJ010426161004261";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ContNo", tConNo);
		tTransferData.setNameAndValue("InvalidReason", "01");

		VData tVData = new VData();
		tVData.addElement(tG);
		tVData.addElement(tTransferData);

		RnSpecialAvailiable tRnSpecialAvailiable = new RnSpecialAvailiable();
		tRnSpecialAvailiable.submitData(tVData, "");
		int n = tRnSpecialAvailiable.mErrors.getErrorCount();
		logger.debug("-------------------------------------------------------");
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tRnSpecialAvailiable.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 得到主险下的合同状态表的开始时间
		 * 对主险进行特殊复效处理 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		// String tSql = "select distinct(contno) from lccontstate where
		// startdate >= '2005-06-29' and (statetype = 'Available' and state =
		// '1' or statetype = 'Terminate' and state = '1' and statereason =
		// '07')";
		//
		// ExeSQL tExeSQL = new ExeSQL();
		// SSRS tSSRS = tExeSQL.execSQL(tSql);
		// if (tExeSQL.mErrors.needDealError())
		// {
		// logger.debug(
		// "------------------------------------------------------");
		// logger.debug("--语句执行错误" + tExeSQL.mErrors);
		// logger.debug(
		// "------------------------------------------------------");
		//
		// }
		//
		// for (int i = 1; i <= tSSRS.getMaxRow(); i++)
		// {
		// String tContNo = tSSRS.GetText(i, 1); //保单险种号
		//
		// TransferData tTransferData = new TransferData();
		// tTransferData.setNameAndValue("ContNo", tContNo);
		//
		// VData tVDataTemp = new VData();
		// tVDataTemp.addElement(tG);
		// tVDataTemp.addElement(tTransferData);
		//
		// LRNSpecialAvailableBL tLLClaimCalMatchBL = new
		// LRNSpecialAvailableBL();
		// tLLClaimCalMatchBL.submitData(tVDataTemp, "");
		// }
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
