/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.pubfun;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */

public class GetPayType {
private static Logger logger = Logger.getLogger(GetPayType.class);

	private String payMode = ""; // 交费方式
	private String BankCode = ""; // 银行编码
	private String BankAccNo = ""; // 银行账号
	private String AccName = ""; // 户名
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	public static void main(String[] args) {
		// GetPayType getPayType1 = new GetPayType();
	}

	/**
	 * 根据印刷号查找个人保单表中的交费方式等数据(按支票，银行，现金的优先级查找)
	 * 
	 * @param inPrtNo
	 * @return
	 */
	public boolean getPayTypeForLCPol(String inPrtNo) {
		if (inPrtNo == null) {
			return false;
		}
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPrtNo(inPrtNo);
		LCPolSet tLCPolSet = tLCPolDB.query();
		if (tLCPolSet == null || tLCPolSet.size() == 0) {
			mErrors.addOneError("没有找到对应的保单");
			return false;
		}

		String sql = "select * from ljtempfeeclass where tempfeeno in ";
		sql = sql
				+ "(select tempfeeno from ljtempfee where EnterAccDate is not null and otherno in ('"
				+ "?inPrtNo?" + "'";

		for (int n = 1; n <= tLCPolSet.size(); n++) {
			sql = sql + ", '" + tLCPolSet.get(n).getPolNo() + "' ";
		}
		sql = sql + ") )";
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(sql);
        sqlbv.put("inPrtNo", inPrtNo);
        
		LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
		LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB
				.executeQuery(sqlbv);
		if (tLJTempFeeClassSet == null || tLJTempFeeClassSet.size() == 0) {
			mErrors.addOneError("没有找到对应的暂交费分类纪录");
			return false;
		}
		for (int i = 1; i <= tLJTempFeeClassSet.size(); i++) {
			if (tLJTempFeeClassSet.get(i).getPayMode().equals("2")
					|| tLJTempFeeClassSet.get(i).getPayMode().equals("3")) {
				payMode = tLJTempFeeClassSet.get(i).getPayMode();
				BankCode = tLJTempFeeClassSet.get(i).getBankCode();
				BankAccNo = tLJTempFeeClassSet.get(i).getChequeNo();
				break;
			}
			if (tLJTempFeeClassSet.get(i).getPayMode().equals("4")) {
				payMode = tLJTempFeeClassSet.get(i).getPayMode();
				BankCode = tLJTempFeeClassSet.get(i).getBankCode();
				BankAccNo = tLJTempFeeClassSet.get(i).getBankAccNo();
				AccName = tLJTempFeeClassSet.get(i).getAccName();
			} else {
				payMode = "1";
			}
		}

		return true;
	}

	/**
	 * 根据印刷号查找个人保单表中的交费方式等数据
	 * 
	 * @param inPrtNo
	 * @param flag
	 *            (0: 按支票，银行，现金的优先级查找. 2 查找支票的交费数据 4 查找银行的交费数据 )
	 * @return
	 */
	public boolean getPayTypeForLCPol(String inPrtNo, int flag) {
		if (inPrtNo == null) {
			return false;
		}
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPrtNo(inPrtNo);
		LCPolSet tLCPolSet = tLCPolDB.query();
		if (tLCPolSet == null || tLCPolSet.size() == 0) {
			mErrors.addOneError("没有找到对应的保单");
			return false;
		}

		String sql = "select * from ljtempfeeclass where tempfeeno in ";
		sql = sql
				+ "(select tempfeeno from ljtempfee where EnterAccDate is not null and otherno in ('"
				+ "?inPrtNo1?" + "'";

		ArrayList<String> othernoArr=new ArrayList<String>();
		othernoArr.add(inPrtNo);
		for (int n = 1; n <= tLCPolSet.size(); n++) {
			othernoArr.add(tLCPolSet.get(n).getPolNo());
//			sql = sql + ", '" + tLCPolSet.get(n).getPolNo() + "' ";
		}
		sql = sql + ") )";
		 SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
	        sqlbv1.sql(sql);
	        sqlbv1.put("inPrtNo1", othernoArr);

		LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
		LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB
				.executeQuery(sqlbv1);
		if (tLJTempFeeClassSet == null || tLJTempFeeClassSet.size() == 0) {
			mErrors.addOneError("没有找到对应的暂交费分类纪录");
			return false;
		}
		for (int i = 1; i <= tLJTempFeeClassSet.size(); i++) {
			// 如果交费方式是2，3 （支票）并且传入标记为0或2
			if ((tLJTempFeeClassSet.get(i).getPayMode().equals("2") || tLJTempFeeClassSet
					.get(i).getPayMode().equals("3"))
					&& (flag == 0 || flag == 2)) {
				payMode = tLJTempFeeClassSet.get(i).getPayMode();
				BankCode = tLJTempFeeClassSet.get(i).getBankCode();
				BankAccNo = tLJTempFeeClassSet.get(i).getChequeNo();
				break;
			}
			if (tLJTempFeeClassSet.get(i).getPayMode().equals("4")
					&& (flag == 0 || flag == 4)) {
				payMode = tLJTempFeeClassSet.get(i).getPayMode();
				BankCode = tLJTempFeeClassSet.get(i).getBankCode();
				BankAccNo = tLJTempFeeClassSet.get(i).getBankAccNo();
				AccName = tLJTempFeeClassSet.get(i).getAccName();
			}
			if (tLJTempFeeClassSet.get(i).getPayMode().equals("1")
					&& (flag == 0)) {
				payMode = "1";
			}
		}

		return true;
	}

	public String getPayMode() {
		if (payMode == null) {
			payMode = "";
		}
		return payMode;
	}

	public String getBankCode() {
		if (BankCode == null) {
			BankCode = "";
		}
		return BankCode;
	}

	public String getBankAccNo() {
		if (BankAccNo == null) {
			BankAccNo = "";
		}
		return BankAccNo;
	}

	public String getAccName() {
		if (AccName == null) {
			AccName = "";
		}
		return AccName;
	}
}
