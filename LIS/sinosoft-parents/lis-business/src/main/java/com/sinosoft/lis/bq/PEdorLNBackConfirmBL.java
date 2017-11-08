package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保单质押贷款回退确认BL
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Nicholas
 * @version 1.0
 */

public class PEdorLNBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorLNBackConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	public PEdorLNBackConfirmBL() {
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

		// 得到外部传入的数据,将数据备份到本类中
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

	public CErrors getErrors() {
		return this.mErrors;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			// 需要回退的保全项目
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
		} catch (Exception e) {
			e.printStackTrace();
			return this.makeError("getInputData", "接收前台数据失败！");
		}
		if (mGlobalInput == null || mLPEdorItemSchema == null) {
			return this.makeError("getInputData", "传入数据有误！");
		}

		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(mLPEdorItemSchema);
		if (!tLPEdorItemDB.getInfo()) {
			// @@错误处理
			return this.makeError("checkData", "无保全数据！");
		}

		mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());
		if (!"0".equals(tLPEdorItemDB.getEdorState())) {
			// @@错误处理
			return this.makeError("checkData", "此项目尚未确认生效！");
		}
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
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = null;
			String tSQL="select orderno,lastsumloanmoney from loloan where edorno='?edorno?' and contno='?contno?'"
			            +" and payoffflag='0' and loantype='0' order by orderno desc";
			
			String tOrderNo="",tLastMoney="";
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql(tSQL);
			sbv.put("edorno", mLPEdorItemSchema.getEdorNo());
			sbv.put("contno", mLPEdorItemSchema.getContNo());
			tSSRS=tExeSQL.execSQL(sbv);
			int iOrderNo=0;
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				return this.makeError("dealData", "查询贷款数据失败！");
			}else				
			{ 
				tOrderNo=tSSRS.GetText(1, 1);
				tLastMoney=tSSRS.GetText(1, 2);
				iOrderNo=Integer.parseInt(tOrderNo);
				iOrderNo=iOrderNo-1;
			}
			//以前只借了一次款，则直接删除相应的LOLOan,更改保单状态即可
			if(iOrderNo==0)
			{
				// 删除借款表数据
				String tSql = "DELETE FROM LOLoan WHERE" + " ContNo='?ContNo?'" + " and EdorNo='?EdorNo?'" + " and LoanType='0'";
				SQLwithBindVariables sbv1=new SQLwithBindVariables();
				sbv1.sql(tSql);
				sbv1.put("ContNo", mLPEdorItemSchema.getContNo());
				sbv1.put("EdorNo", mLPEdorItemSchema.getEdorNo());
				mMap.put(sbv1, "DELETE");
				// 删除贷款状态
				tSql = "DELETE FROM LCContState WHERE" + " ContNo='?ContNo?'"
						+ " and StateType='Loan'" + " and State='1'"
						+ " and EndDate is null";
				SQLwithBindVariables sbv2=new SQLwithBindVariables();
				sbv2.sql(tSql);
				sbv2.put("ContNo", mLPEdorItemSchema.getContNo());
				mMap.put(sbv2, "DELETE");
				// 恢复非贷款状态
				// 获得贷款状态起期
				tSql = "SELECT to_char(StartDate,'YYYY-MM-DD')"
						+ " FROM LCContState" + " WHERE ContNo='?ContNo?'"
						+ " and StateType='Loan'" + " and State='1'"
						+ " and EndDate is null";
				SQLwithBindVariables sbv3=new SQLwithBindVariables();
				sbv3.sql(tSql);
				sbv3.put("ContNo", mLPEdorItemSchema.getContNo());

				tSSRS = tExeSQL.execSQL(sbv3);
				if (tSSRS == null || tSSRS.MaxRow <= 0) {
					return this.makeError("dealData", "查询贷款状态起期失败！");
				}
				// 恢复上一个非贷款状态
				tSql = "UPDATE LCContState set EndDate=null,"
						+ " Operator='?Operator?',"
						+ " ModifyDate=to_date('?strCurrentDate?','YYYY-MM-DD'),"
						+ " ModifyTime='?strCurrentTime?'"
						+ " WHERE ContNo='?ContNo?'"
						+ " and StateType='Loan'"
						+ " and State='0'"
						+ " and to_date(to_char(adddate(EndDate,1),'YYYY-MM-DD'),'YYYY-MM-DD')=to_date('?EndDate?','YYYY-MM-DD')"
						+ " and EndDate is not null";
				SQLwithBindVariables sbv4=new SQLwithBindVariables();
				sbv4.sql(tSql);
				sbv4.put("Operator", this.mGlobalInput.Operator);
				sbv4.put("strCurrentDate", strCurrentDate);
				sbv4.put("strCurrentTime", strCurrentTime);
				sbv4.put("ContNo", mLPEdorItemSchema.getContNo());
				sbv4.put("EndDate", tSSRS.GetText(1, 1));
				mMap.put(sbv4, "UPDATE");
			}else  //如果是多次借款则删除本次借款记录，并跟新保单状态
			{
				
				// 删除借款表数据
				String tSql = "DELETE FROM LOLoan WHERE" + " ContNo='?ContNo?'" 
						+ " and LoanType='0' and payoffflag='0' and OrderNo='?tOrderNo?' and edorno='?edorno?'";
				SQLwithBindVariables sbv5=new SQLwithBindVariables();
				sbv5.sql(tSql);
				sbv5.put("ContNo", mLPEdorItemSchema.getContNo());
				sbv5.put("tOrderNo", tOrderNo);
				sbv5.put("edorno", mLPEdorItemSchema.getEdorNo());
				mMap.put(sbv5, "DELETE");

				String tSQLStr="select leavemoney from loloan where OrderNo=?iOrderNo? and contno='?contno?'"
	                           +" and  loantype='0' order by orderno desc";
	
	            String tLastLeaveMoney="";
	            SQLwithBindVariables sbv6=new SQLwithBindVariables();
	            sbv6.sql(tSQLStr);
	            sbv6.put("iOrderNo", iOrderNo);
	            sbv6.put("contno", mLPEdorItemSchema.getContNo());
	
	            tLastLeaveMoney=tExeSQL.getOneValue(sbv6);
				
				//如果说明上次已经还清，本次借款新增了已借款记录和还清记录，因此作为回退，应该删除新增的已借款记录和更新还清记录
				if("0".equals(tLastLeaveMoney))
				{
					// 删除贷款状态
					tSql = "DELETE FROM LCContState WHERE" + " ContNo='?ContNo?'"
							+ " and StateType='Loan'" + " and State='1'"
							+ " and EndDate is null";
					SQLwithBindVariables sbv7=new SQLwithBindVariables();
					sbv7.sql(tSql);
					sbv7.put("ContNo", mLPEdorItemSchema.getContNo());
					mMap.put(sbv7, "DELETE");
					// 恢复非贷款状态
					// 获得贷款状态起期
					tSql = "SELECT to_char(StartDate,'YYYY-MM-DD')"
							+ " FROM LCContState" + " WHERE ContNo='?ContNo?'"
							+ " and StateType='Loan'" + " and State='1'"
							+ " and EndDate is null";
					SQLwithBindVariables sbv8=new SQLwithBindVariables();
					sbv8.sql(tSql);
					sbv8.put("ContNo", mLPEdorItemSchema.getContNo());

					tSSRS = tExeSQL.execSQL(sbv8);
					if (tSSRS == null || tSSRS.MaxRow <= 0) {
						return this.makeError("dealData", "查询贷款状态起期失败！");
					}
					// 恢复上一个非贷款状态
					tSql = "UPDATE LCContState set EndDate=null,"
							+ " Operator='?Operator?',"
							+ " ModifyDate=to_date('?strCurrentDate?','YYYY-MM-DD'),"
							+ " ModifyTime='?strCurrentTime?'"
							+ " WHERE ContNo='?ContNo?'"
							+ " and StateType='Loan'"
							+ " and State='0'"
							+ " and to_date(to_char(adddate(EndDate,1),'YYYY-MM-DD'),'YYYY-MM-DD')=to_date('?EndDate?','YYYY-MM-DD')"
							+ " and EndDate is not null";
					SQLwithBindVariables sbv9=new SQLwithBindVariables();
					sbv9.sql(tSql);
					sbv9.put("Operator", this.mGlobalInput.Operator);
					sbv9.put("strCurrentDate", strCurrentDate);
					sbv9.put("strCurrentTime", strCurrentTime);
					sbv9.put("ContNo", mLPEdorItemSchema.getContNo());
					sbv9.put("EndDate", tSSRS.GetText(1, 1));
					mMap.put(sbv9, "UPDATE");
				}
			}

			mResult.clear();
			mResult.add(mMap);
		} catch (Exception e) {
			// @@错误处理
			return this.makeError("dealData", "数据处理错误！" + e.getMessage());
		}
		return true;
	}

	/**
	 * 创建一个错误
	 * 
	 * @param vFuncName
	 *            发生错误的函数名
	 * @param vErrMsg
	 *            错误信息
	 * @return 布尔值（false--永远返回此值）
	 */
	private boolean makeError(String vFuncName, String vErrMsg) {
		CError tError = new CError();
		tError.moduleName = "PEdorLNBackConfirmBL";
		tError.functionName = vFuncName;
		tError.errorMessage = vErrMsg;
		this.mErrors.addOneError(tError);
		return false;
	}

	public static void main(String[] args) {
		PEdorLNBackConfirmBL tPEdorLNBackConfirmBL = new PEdorLNBackConfirmBL();
	}
}
