package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import java.util.Iterator;

import com.sinosoft.lis.db.LYBankLogDB;
import com.sinosoft.lis.f1print.PremBankPubFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LYBankLogSchema;
import com.sinosoft.lis.vschema.LYBankLogSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
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
public class NewShowBillBL {
private static Logger logger = Logger.getLogger(NewShowBillBL.class);
	public CErrors mErrors = new CErrors();
	private VData mInputData;
	private VData mResult = new VData();
	private String mOperate;
	private String strStartDate;
	private String strEndDate;
	private String strBankCode;
	private String strFlag;
	private GlobalInput mG = new GlobalInput();
	private String t_ComCode;
	private String strTFFlag = "";
	private String strXQFlag = "";
	private PremBankPubFun mPremBankPubFun = new PremBankPubFun();

	public NewShowBillBL() {
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
		if (mOperate.equals("SHOW")) {
			strStartDate = (String) cInputData.get(0);// 开始日期
			strEndDate = (String) cInputData.get(1);// 结束日期
			strBankCode = (String) cInputData.get(2);// 银行代码（可以是空）
			strFlag = (String) cInputData.get(3);// 查询的标准“YF”or“YS”
			mG.setSchema((GlobalInput) cInputData.getObjectByObjectName(
					"GlobalInput", 0));
			t_ComCode = mG.ManageCom;
			strTFFlag = (String) cInputData.get(4);// 正确还是错误清单
			strXQFlag = (String) cInputData.get(5);// 首期还是续期的标志
			strStartDate = strStartDate.trim();
			strEndDate = strEndDate.trim();
			strFlag = strFlag.trim();
			t_ComCode = t_ComCode.trim();
			strTFFlag = strTFFlag.trim();
			strXQFlag = strXQFlag.trim();
		}
		return true;
	}

	/**
	 * @function select BillNo from LYBankLog
	 * @execute different SQL bansd on different OperateFlag return true or
	 *          false
	 */
	private boolean queryData() {
		// 注意事项：管理机构要以登陆的管理机构为准。与界面上录入的管理机构是无关的。
		// 在银行返回盘及银行返回判备份表中直接查询数据

		String SF_flag = "";
		String addsql = "";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		if (strFlag.equals("YS")) {
			SF_flag = "S";
		}
		if (strFlag.equals("YF")) {
			SF_flag = "F";
		}
		addsql = " and LogType = '" + "?LogType?" + "' and ReturnDate is not null ";
		sqlbv1.put("LogType", SF_flag);
		if (strTFFlag.equals("T")) // 正确清单
		{
			addsql += "and banksuccnum > 0 "; // 批次内有成功记录的时候在正确清单中才显示
		} else // 错误清单
		{
			addsql += "and banksuccnum < totalnum "; // 不是全部成功时才在错误清单中显示
		}
		
		if (strBankCode != null && !strBankCode.equals("")) {
			addsql = addsql + " and BankCode = '" + "?BankCode?" + "' ";
			sqlbv1.put("BankCode", strBankCode);
		}
		
		
		
		
		String notype=null;
		if (strXQFlag.equals("S")) {
			notype = "notype not in('2','10')";
		} else if (strXQFlag.equals("X")) {
			notype = "notype = '2'";
		} else if (strXQFlag.equals("B")) {
			notype = "notype = '10'";
		} else if (strXQFlag.equals("L")) {
			notype = "notype = '5'";
		}
		if(notype!=null){
			String sa=" (dealstate<>'1' and exists(select 1 from lyreturnfrombank where serialno=lybanklog.serialno and "+notype+"))";
			String sb=" (dealstate='1' and exists(select 1 from lyreturnfrombankb where serialno=lybanklog.serialno and "+notype+"))";
			addsql = addsql+" and ("+sa+" or"+sb+")";
		}
		String sql = " select * from lybanklog where ComCode like concat('?t_ComCode?','%') and StartDate >='?strStartDate?'and StartDate <= '?strEndDate?' " + addsql
				+ " order by serialno ";
		sqlbv1.sql(sql);
		sqlbv1.put("t_ComCode", t_ComCode);
		sqlbv1.put("StartDate", strStartDate);
		sqlbv1.put("StartDate1", strEndDate);
		LYBankLogDB tLYBankLogDB=new LYBankLogDB();
		logger.debug("SQL:"+sql);
		LYBankLogSet tLYBankLogSet=tLYBankLogDB.executeQuery(sqlbv1);
		if (tLYBankLogSet.size() <= 0) {
			CError.buildErr(this, "该时间段内没有发生银行返回盘的记录信息，请您从新录入查询条件！");
			return false;
		}
		mResult.add(tLYBankLogSet);
		return true;
	}

	public static void main(String[] args) {
		String aStartDate = "2004-7-1"; // 开始日期
		String aEndDate = "2004-7-9"; // 结束日期
		String aBankCode = "";
		String aFlag = "YS";
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "8611";
		String aTFFlag = "F";// 1是正确2是错误
		String aXQFlag = "S";

		VData tVData = new VData();
		tVData.addElement(aStartDate);
		tVData.addElement(aEndDate);
		tVData.addElement(aBankCode);
		tVData.addElement(aFlag);
		tVData.addElement(aTFFlag);
		tVData.addElement(aXQFlag);
		tVData.addElement(tG);

		NewShowBillBL tNewShowBillBL = new NewShowBillBL();
		tNewShowBillBL.submitData(tVData, "SHOW");
	}
}
