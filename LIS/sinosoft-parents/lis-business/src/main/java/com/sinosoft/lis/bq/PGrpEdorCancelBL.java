package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:保全申请删除通用类
 * </p>
 * <p>
 * Description:删除保全项目通用类.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author Lanjun modify by zhangtao
 * @version 1.0
 */
public class PGrpEdorCancelBL {
private static Logger logger = Logger.getLogger(PGrpEdorCancelBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	/** 传输到后台处理的map */
	private MMap mMap = new MMap();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public PGrpEdorCancelBL() {
	}

	/**
	 * 处理实际的业务逻辑。
	 * 
	 * @param cInputData
	 *            VData 从前台接收的表单数据
	 * @param cOperate
	 *            String 操作字符串
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将数据取到本类变量中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 将VData数据还原成业务需要的类
		if (!getInputData()) {
			logger.debug("批改状态不可以");
			return false;

		}

		logger.debug("---getInputData successful---");

		if (!dealData()) {
			return false;
		}

		logger.debug("---dealdata successful---");

		// 装配处理好的数据，准备给后台进行保存
		prepareOutputData();

		logger.debug("---prepareOutputData---");

		if ("I&EDORAPP".equals(mOperate) || "G&EDORAPP".equals(mOperate)) {
			return true; // 不要提交
		}

		PubSubmit tPubSubmit = new PubSubmit();

		if (!tPubSubmit.submitData(mResult, cOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			return false;
		}

		return true;
	}

	/**
	 * 将UI层传输来得数据根据业务还原成具体的类
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		return true;
	}

	/**
	 * 对业务数据进行加工 对于新增的操作，这里需要有生成新合同号和新客户号的操作。
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		String flag = "";
		flag = mOperate.substring(0, 1);
		logger.debug("flag=" + flag);
		if ("G".equals(flag)) // 团单
		{
			if ("G&EDORITEM".equalsIgnoreCase(mOperate) || // 团体保全项目
					"G&I&EDORITEM".equalsIgnoreCase(mOperate) || // 团体保全项目下的个人保全项目
					"G&EDORRISK".equalsIgnoreCase(mOperate)) // 团体保全项目下的个人保全险种
																// : XinYQ added
																// on 2006-04-24
			{
				GEdorItemCancelBL tGEdorItemCancelBL = new GEdorItemCancelBL();
				if (!tGEdorItemCancelBL.submitData(mInputData, mOperate)) {
					this.mErrors.copyAllErrors(tGEdorItemCancelBL.mErrors);
					return false;
				} else {
					mMap.add(tGEdorItemCancelBL.getMap());
				}
			} else if ("G&EDORMAIN".equalsIgnoreCase(mOperate)) // 团体保全批单
			{
				GEdorMainCancelBL tGEdorMainCancelBL = new GEdorMainCancelBL();
				if (!tGEdorMainCancelBL.submitData(mInputData, "EDORMAIN")) {
					this.mErrors.copyAllErrors(tGEdorMainCancelBL.mErrors);
					return false;
				} else {
					mMap.add(tGEdorMainCancelBL.getMap());
				}
			} else if ("G&EDORAPP".equalsIgnoreCase(mOperate)) // 团体保全申请
			{
				TransferData tTransferData = (TransferData) mInputData
				.getObjectByObjectName("TransferData", 0);
				String sEdorAcceptNo = (String) tTransferData
				.getValueByName("EdorAcceptNo");
				if(sEdorAcceptNo==null||"".equals(sEdorAcceptNo))
				{

					LPEdorAppSchema tLPEdorAppSchema = (LPEdorAppSchema)mInputData.getObjectByObjectName("LPEdorAppSchema", 0);
					sEdorAcceptNo = tLPEdorAppSchema.getEdorAcceptNo();
				}
				if(sEdorAcceptNo==null||"".equals(sEdorAcceptNo))
				{
					CError.buildErr(this, "受理号信息获取失败!");
					return false;
				}
				// 校验是否已经有暂交费尚未核销或是银行在途
				if (!checkPayGet(sEdorAcceptNo)) {
					return false;
				}
				GEdorAppCancelBL tGEdorAppCancelBL = new GEdorAppCancelBL();
				if (!tGEdorAppCancelBL.submitData(mInputData, "EDORAPP")) {
					this.mErrors.copyAllErrors(tGEdorAppCancelBL.mErrors);
					return false;
				} else {
					mMap.add(tGEdorAppCancelBL.getMap());
				}
			}
		} else if ("I".equals(flag)) // 个单
		{
			TransferData tTransferData = (TransferData) mInputData
					.getObjectByObjectName("TransferData", 0);
			String sEdorAcceptNo = (String) tTransferData
					.getValueByName("EdorAcceptNo");
			if(sEdorAcceptNo==null||"".equals(sEdorAcceptNo))
			{
				
				LPEdorAppSchema tLPEdorAppSchema = (LPEdorAppSchema)mInputData.getObjectByObjectName("LPEdorAppSchema", 0);
				sEdorAcceptNo = tLPEdorAppSchema.getEdorAcceptNo();
			}
			if(sEdorAcceptNo==null||"".equals(sEdorAcceptNo))
			{
				CError.buildErr(this, "受理号信息获取失败!");
				return false;
			}
			

			// 判断如果该保全任务处于人工核保中，不允许撤销
			String sIsInUWing = isInUWing(sEdorAcceptNo);
			if (sIsInUWing == null) {
				return false;
			} else if (sIsInUWing.equals("Y")) {
				CError.buildErr(this, "该保全任务处于人工核保或审批中,不允许撤销!");
				return false;
			}
			// 校验是否已经有暂交费尚未核销或是银行在途
			if (!checkPayGet(sEdorAcceptNo)) {
				return false;
			}
			if ("I&EDORITEM".equalsIgnoreCase(mOperate)) // 个单保全项目
			{
				PEdorItemCancelBL tPEdorItemCancelBL = new PEdorItemCancelBL();
				if (!tPEdorItemCancelBL.submitData(mInputData, "EDORITEM")) {
					this.mErrors.copyAllErrors(tPEdorItemCancelBL.mErrors);
					return false;
				} else {
					mMap.add(tPEdorItemCancelBL.getMap());
				}
			} else if ("I&EDORMAIN".equalsIgnoreCase(mOperate)) // 个单保全批单
			{
				PEdorMainCancelBL tPEdorMainCancelBL = new PEdorMainCancelBL();
				if (!tPEdorMainCancelBL.submitData(mInputData, "EDORMAIN")) {
					this.mErrors.copyAllErrors(tPEdorMainCancelBL.mErrors);
					return false;
				} else {
					mMap.add(tPEdorMainCancelBL.getMap());
				}
			} else if ("I&EDORAPP".equalsIgnoreCase(mOperate)) // 个单保全申请
			{
				PEdorAppCancelBL tPEdorAppCancelBL = new PEdorAppCancelBL();
				if (!tPEdorAppCancelBL.submitData(mInputData, "EDORAPP")) {
					this.mErrors.copyAllErrors(tPEdorAppCancelBL.mErrors);
					return false;
				} else {
					mMap.add(tPEdorAppCancelBL.getMap());
				}

			}
		} else // 错误处理
		{
			CError.buildErr(this, "传入操作符错误!");
			return false;
		}
		return true;
	}

	/**
	 * 判断该保全是否正处于人工核保中
	 */
	private String isInUWing(String sEdorAcceptNo) {
		String sql = " select 1 from lwmission where defaultoperator is not null "
				+ " and activityid in ('0000000005','0000000007') and missionprop1 = '"
				+ sEdorAcceptNo + "'";
		ExeSQL tExeSQL = new ExeSQL();
		String sEdorValidate = tExeSQL.getOneValue(sql);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保全核保任务查询失败!");
			return null;
		}
		if (sEdorValidate != null && sEdorValidate.trim().equals("1")) {
			return "Y"; // 处于人工核保中
		} else {
			return "N";
		}
	}

	/**
	 * 财务数据校验
	 * 
	 * @return boolean
	 */
	public boolean checkPayGet(String sEdorAcceptNo) {
		// 查询应收数据
		LJSPayDB tLJSPayDB = new LJSPayDB();
		
		//add by jiaqiangli 2009-05-06
		if (sEdorAcceptNo == null || "".equals(sEdorAcceptNo)) {
			CError.buildErr(this, "受理号"+sEdorAcceptNo+"数据为空！");
			return false;
		}
		
		
		String tSQL = "select *" + "  from ljspay a " + " where otherno = '"
				+ sEdorAcceptNo + "'" + "   and othernotype = '10'"
				+ "   and not exists   " + " (select 'X'   "
				+ " from ljtempfee b   " + " where b.otherno = a.otherno   "
				+ "  and b.tempfeeno=a.getnoticeno  "
				+ "  and b.enteraccdate is not null  "
				+ "  and b.confdate is not null)";
		
		LJSPaySet tLJSPaySet = tLJSPayDB.executeQuery(tSQL);
		if (tLJSPayDB.mErrors.needDealError()) {
			CError.buildErr(this, "保全应收信息查询失败!");
			return false;
		}
		if (tLJSPaySet != null && tLJSPaySet.size() > 0) {

			// 判断是否银行在途
			String sBankOnTheWayFlag = tLJSPaySet.get(1).getBankOnTheWayFlag();
			if (sBankOnTheWayFlag != null
					&& sBankOnTheWayFlag.trim().equals("1")) {
				CError.buildErr(this, "保全收费银行在途,不允许撤销");
				return false;
			}
			// 判断是否有暂交费
			String sql = " select * from ljtempfee where 1=1 "
					+ " and (enteraccdate <> '3000-01-01' or enteraccdate is null) "
					+ " and tempfeetype = '4' " + " and  tempfeeno = '"
					//add by jiaqiangli 2009-07-20
					+ tLJSPaySet.get(1).getGetNoticeNo() + "' and ConfDate is null ";
			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.executeQuery(sql);
			if (tLJTempFeeSet == null || tLJTempFeeSet.size() < 1) {
				return true; // 没有任何暂交费记录或暂缴费已经被退(3000-01-01)
			}
			String sEnterAccDate;
			String sTempFeeNo;
			for (int i = 1; i <= tLJTempFeeSet.size(); i++) {
				sEnterAccDate = tLJTempFeeSet.get(i).getEnterAccDate();
				sTempFeeNo = tLJTempFeeSet.get(i).getTempFeeNo();
				if (sEnterAccDate != null && !sEnterAccDate.equals("")) {
					// 暂交费已经到帐
					CError.buildErr(this, "保全已经录入暂收费,不允许撤销!");
					return false;
				} 
			}
			


		} 
		return true;
	}

	
	/**
	 * 准备数据，重新填充数据容器中的内容
	 */
	private void prepareOutputData() {
		// 记录当前操作员
		mResult.clear();
		mResult.add(mMap);
	}

	/**
	 * 操作结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回本类运行时产生的错误信息
	 * 
	 * @param null
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	} // function getErrors end

}
