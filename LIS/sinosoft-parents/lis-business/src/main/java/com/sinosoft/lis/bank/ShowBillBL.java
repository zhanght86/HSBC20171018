package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LYBankLogDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.vschema.LYBankLogSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 查询批次号程序
 * </p>
 * <p>
 * Description: 根据条件查询出要打印的批次号
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003-04-02
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author 刘岩松
 * @version 1.0
 * @function show BillNo that was selected from LYBankLog Business Logic Layer
 */
public class ShowBillBL {
private static Logger logger = Logger.getLogger(ShowBillBL.class);
	public CErrors mErrors = new CErrors();
	private VData mInputData;
	private VData mResult = new VData();
	private String mOperate;
	private LYBankLogSet mLYBankLogSet = new LYBankLogSet();
	private LYBankLogSet yLYBankLogSet = new LYBankLogSet();
	private String strStartDate;
	private String strEndDate;
	private String strBankCode;
	private String strFlag;
	private String strTFFlag; // 正确清单还是错误清单
	private GlobalInput mG = new GlobalInput();
	private String t_ComCode;

	public ShowBillBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return: true or false
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		if (!getInputData(cInputData))
			return false;
		if (!queryData())
			return false;
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/***************************************************************************
	 * @function :receive parameter and checkdate
	 * @receive :receive parameter from jsp
	 * @checkdate:judge whether startDate before EndDate
	 * @return true or false
	 **************************************************************************/
	private boolean getInputData(VData cInputData) {
		if (mOperate.equals("QUERY||MAIN")) {
			strStartDate = (String) cInputData.get(0);
			strEndDate = (String) cInputData.get(1);
			strBankCode = (String) cInputData.get(2);
			strFlag = (String) cInputData.get(3);
			strTFFlag = (String) cInputData.get(4);
			mG.setSchema((GlobalInput) cInputData.getObjectByObjectName(
					"GlobalInput", 0));
			t_ComCode = mG.ManageCom;
		}

		// int date_flag = strStartDate.compareTo(strEndDate);
		// logger.debug("2003-06-10*********日期比较的值是"+date_flag);
		//
		//
		//
		// if(date_flag>=1)
		// {
		// CError tError = new CError();
		// tError.moduleName = "ShowBillBL";
		// tError.functionName = "getDutyGetClmInfo";
		// tError.errorMessage = "开始日期不能比结束日期晚，请您重新输入起止日期！！！" ;
		// this.mErrors.addOneError(tError) ;
		// return false;
		// }
		return true;
	}

	/**
	 * @function select BillNo from LYBankLog
	 * @execute different SQL bansd on different OperateFlag return true or
	 *          false
	 */
	private boolean queryData() {
		// 注意事项：管理机构要以登陆的管理机构为准。与界面上录入的管理机构是无关的。
		// 查询时注意登陆的管理机构
		LYBankLogDB tLYBankLogDB = new LYBankLogDB();
		LYBankLogSet tLYBankLogSet = new LYBankLogSet();
		String t_Sql = "";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		logger.debug("根据操作标志进行选择====" + strFlag);
		String addsql = "";
		if (strTFFlag.equals("T")) // 正确清单
		{
			addsql = " and banksuccnum > 0 ";
		} else {
			addsql = " and banksuccnum < totalnum ";
		}

		if (strFlag.equals("YS")) {
			// logger.debug("2003-05-12开始执行到ys中的程序");
			if (strBankCode.equals("") || strBankCode == null) {
				t_Sql = "select * from LYBankLog where StartDate >='"
						+ "?StartDate?" + "' and StartDate <='"
						+ "?StartDate1?"
						+ "' and LogType = 'S' and ComCode like concat('?t_ComCode?','%') and ReturnDate is not null " + addsql;
				
				sqlbv1.sql(t_Sql);
				sqlbv1.put("StartDate", strStartDate.trim());
				sqlbv1.put("StartDate1", strEndDate.trim());
				sqlbv1.put("t_ComCode", t_ComCode);
	

			} else {
				t_Sql = "select * from LYBankLog where StartDate >='"
						+ "?StartDate1?" + "' and StartDate <='"
						+ "?StartDate2?" + "' and BankCode ='"
						+ "?BankCode?"
						+ "' and LogType = 'S' and ComCode like concat('?t_ComCode?','%') and ReturnDate is not null " + addsql;
				sqlbv1.sql(t_Sql);
				sqlbv1.put("StartDate1", strStartDate.trim());
				sqlbv1.put("StartDate2", strEndDate.trim());
				sqlbv1.put("BankCode", strBankCode.trim());
				sqlbv1.put("t_ComCode", t_ComCode);
			}
		}
		if (strFlag.equals("YF")) {
			sqlbv1=new SQLwithBindVariables();
			if (strBankCode.equals("") || strBankCode == null) {
				t_Sql = "select * from LYBankLog where StartDate >='"
						+ "?StartDate5?" + "' and StartDate <='"
						+ "?StartDate6?"
						+ "' and LogType = 'F' and ComCode like concat('?t_ComCode?','%') and ReturnDate is not null " + addsql;
				
				sqlbv1.sql(t_Sql);
				sqlbv1.put("StartDate5", strStartDate.trim());
				sqlbv1.put("StartDate6", strEndDate.trim());
				sqlbv1.put("t_ComCode", t_ComCode);
				
			} else {
				t_Sql = "select * from LYBankLog where StartDate >='"
						+ "?StartDate7?" + "' and StartDate <='"
						+ "?StartDate8?" + "' and BankCode ='"
						+ "?BankCode1?"
						+ "' and LogType = 'F' and ComCode like concat('?t_ComCode?','%') and ReturnDate is not null " + addsql;
	
						sqlbv1.sql(t_Sql);
						sqlbv1.put("StartDate7", strStartDate.trim());
						sqlbv1.put("StartDate8", strEndDate.trim());
						sqlbv1.put("BankCode1", strBankCode.trim());
						sqlbv1.put("t_ComCode", t_ComCode);
					
				
			}
		}
		logger.debug("SQL:" + t_Sql);
		tLYBankLogSet = tLYBankLogDB.executeQuery(sqlbv1);
		int i_count = tLYBankLogSet.size();
		if (i_count == 0) {
			this.mErrors.copyAllErrors(tLYBankLogDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ShowBillBL";
			tError.functionName = "queryData()";
			tError.errorMessage = "在该起始日期和结束日期之间没有交易批次，请确认起止日期是否正确！！！";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tLYBankLogDB.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLYBankLogDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ShowBillBL";
			tError.functionName = "queryData()";
			tError.errorMessage = "银行日志表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		yLYBankLogSet.set(tLYBankLogSet);
		mResult.add(yLYBankLogSet);
		return true;
	}

	public static void main(String[] args) {
		ShowBillBL tShowBillBL = new ShowBillBL();

		tShowBillBL.strStartDate = "2003-05-09";
		tShowBillBL.strEndDate = "2003-05-09";
		tShowBillBL.strBankCode = "0701";
		tShowBillBL.strFlag = "YS";
		tShowBillBL.t_ComCode = "8611";

		tShowBillBL.queryData();
	}
}
