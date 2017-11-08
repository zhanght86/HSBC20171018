package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;

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
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author Gaoht
 * @version 1.0
 */
public class LJFinBalanceSub {
private static Logger logger = Logger.getLogger(LJFinBalanceSub.class);
	public LJFinBalanceSub() {
	}

	/***************************************************************************
	 * 
	 * 新契约
	 */

	public String[][] NBBalance(String cStartDate, String cEndDate,
			String cManageCom) {
		/* 收费号|收费机构|业务名称|业务号|金额|到帐日期|核销日期|缴费方式 */
		String[][] Strr = null;
		String tSql = "select tempfeeno,policycom,'新契约',paymoney,enteraccdate,confdate,otherno from ljtempfee "
				+ "where 1=1 "
				+ "and policycom like concat('?cManageCom?','%') "
				+ "and enteraccdate between '?cStartDate?' and '?cEndDate?' "
				+ "and (confdate is null or (confdate>'?cEndDate?')) "
				+ "and tempfeetype in ('1','C','9') and othernotype not in ('1','2','3','8')";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("cManageCom", cManageCom);
		sqlbv.put("cStartDate", cStartDate);
		sqlbv.put("cEndDate", cEndDate);
		Strr = ExeSql(sqlbv);
		return Strr;
	}

	/***************************************************************************
	 * 
	 * 一般续期预收
	 **************************************************************************/

	public String[][] RNBalanceNormal(String cStartDate, String cEndDate,
			String cManageCom) {
		String[][] Strr = null;
		String tSql = "select tempfeeno,policycom,'一般续期预收',paymoney,enteraccdate,confdate,otherno from ljtempfee "
				+ "where tempfeetype in ('2', 'C') "
				+ "and othernotype in ('2', '3') "
				+ "and policycom like concat('?cManageCom?','%') "
				+ "and enteraccdate between '?cStartDate?' and '?cEndDate?' "
				+ "and (confdate is null or confdate > '?cEndDate?')";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("cManageCom", cManageCom);
		sqlbv1.put("cStartDate", cStartDate);
		sqlbv1.put("cEndDate", cEndDate);
		Strr = ExeSql(sqlbv1);
		return Strr;
	}

	/***************************************************************************
	 * 
	 * 续期预收未核销
	 **************************************************************************/

	public String[][] RNBalanceFront(String cStartDate, String cEndDate,
			String cManageCom) {
		String[][] Strr = null;
		/* 收费号|收费机构|业务名称|业务号|金额|到帐日期|核销日期|缴费方式 */
		String tSql = "select tempfeeno,policycom,'续期预收未核销',(select dif from lccont where contno = ljtempfee.otherno),enteraccdate,confdate,otherno from ljtempfee  "
				+ "where tempfeetype = '3' "
				+ "and enteraccdate between '?cStartDate?' and '?cEndDate?' "
				+ "and policycom like concat('?cManageCom?','%') "
				+ "and exists (select 1 from ljspay where otherno = ljtempfee.otherno)";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tSql);
		sqlbv2.put("cManageCom", cManageCom);
		sqlbv2.put("cStartDate", cStartDate);
		sqlbv2.put("cEndDate", cEndDate);
		Strr = ExeSql(sqlbv2);
		return Strr;
	}

	/***************************************************************************
	 * 
	 * 续期预收已经核销
	 **************************************************************************/
	public String[][] RNBalanceFrontConf(String cStartDate, String cEndDate,
			String cManageCom) {
		String[][] Strr = null;
		/* 收费号|收费机构|业务名称|业务号|金额|到帐日期|核销日期|缴费方式 */
		String tSql = "select  distinct '',policycom,'续期预收已经核销后余额',(select dif from lccont where contno = ljtempfee.otherno),enteraccdate,confdate,otherno from ljtempfee"
				+ " where tempfeetype = '3'"
				+ " and enteraccdate between '?cStartDate?' and '?cEndDate?' "
				+ " and policycom like concat('?cManageCom?','%') "
				+ " and Exists (Select 1 From Lccont Where Contno = Ljtempfee.Otherno And Dif > 0)"
				+ " and not exists (select 1 from ljspay where otherno = ljtempfee.otherno)";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(tSql);
		sqlbv3.put("cManageCom", cManageCom);
		sqlbv3.put("cStartDate", cStartDate);
		sqlbv3.put("cEndDate", cEndDate);
		Strr = ExeSql(sqlbv3);
		return Strr;
	}

	/***************************************************************************
	 * 
	 * 续期预收正常核销，但核销日期在统计日期以后
	 * 
	 */
	public String[][] RNBalanceFrontConfB(String cStartDate, String cEndDate,
			String cManageCom) {
		String[][] Strr = null;
		/* 收费号|收费机构|业务名称|业务号|金额|到帐日期|核销日期|缴费方式 */
		String tSql = "select getnoticeno,managecom,'续期预收-核销日期在统计日期后',aa.sumactupaymoney,enteraccdate,confdate,contno from ljapayperson aa "
				+ "where aa.paytype = 'ZC' "
				+ "and paycount > 1 "
				+ "and makedate>'?cEndDate?' "
				+ "and managecom like concat('?cManageCom?','%') "
				+ "and exists (select 1 from ljtempfee where tempfeetype = '3' and managecom like concat('?cManageCom?','%') and enteraccdate between '?cStartDate?' and '?cEndDate?' and otherno = aa.contno)";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(tSql);
		sqlbv4.put("cManageCom", cManageCom);
		sqlbv4.put("cStartDate", cStartDate);
		sqlbv4.put("cEndDate", cEndDate);

		Strr = ExeSql(sqlbv4);
		return Strr;
	}

	/* 采用多种交费方式，部分到帐的情况，特别是续期回退内部转帐 */
	public String[][] RNBalanceMixPaymode(String cStartDate, String cEndDate,
			String cManageCom) {
		String[][] Strr = null;

		/* 收费号|收费机构|业务名称|业务号|金额|到帐日期|核销日期|缴费方式 */
		String tSql = "select tempfeeno,policycom,'续期催收-部分到帐',paymoney,enteraccdate,confdate,otherno from ljtempfeeclass a "
				+ "where policycom like concat('?cManageCom?','%') "
				+ "and enteraccdate between '?cStartDate?' and '?cEndDate?' "
				+ "and exists (select 1 from ljtempfee where tempfeetype in('2','3') and tempfeeno = a.tempfeeno and enteraccdate is null and confdate is null)";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(tSql);
		sqlbv5.put("cManageCom", cManageCom);
		sqlbv5.put("cStartDate", cStartDate);
		sqlbv5.put("cEndDate", cEndDate);

		Strr = ExeSql(sqlbv5);
		return Strr;
	}

	/* 数据转换提前交缴费未到交费对应日 */
	public String[][] RNBalanceDataTr(String cStartDate, String cEndDate,
			String cManageCom) {
		String[][] Strr = null;
		String tSql = "select getnoticeno,managecom,'续期预收-数据转换',sumactupaymoney,enteraccdate,confdate,contno from ljapayperson "
				+ "where paytype='ZC' and paycount>1 "
				+ "and managecom like concat('?cManageCom?','%') "
				+ "and makedate>'?cEndDate?'"
				+ "and not exists (select 1 from lduser where usercode=ljapayperson.operator) and operator <> 'Batch'";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(tSql);
		sqlbv6.put("cManageCom", cManageCom);
		sqlbv6.put("cStartDate", cStartDate);
		sqlbv6.put("cEndDate", cEndDate);

		Strr = ExeSql(sqlbv6);
		return Strr;
	}

	/* 续期退费仍未核销 */
	public String[][] RNBalanceNotConf(String cStartDate, String cEndDate,
			String cManageCom) {
		/* 收费号|收费机构|业务名称|业务号|金额|到帐日期|核销日期|缴费方式 */
		String[][] Strr = null;
		String tSql = "select actugetno,managecom,'续期暂收费退费',sumgetmoney,enteraccdate,makedate,otherno from ljaget "
				+ "where othernotype = '3' "
				+ "and managecom like concat('?cManageCom?','%') "
				+ "and (makedate between '?cStartDate?' and '?cEndDate?') "
				+ "and (enteraccdate > '?cEndDate?' or enteraccdate is null) "
				+ "and exists (select 1 from ljtempfee where tempfeetype in ('2', '3') and managecom like concat('?cManageCom?','%') and (enteraccdate between '?cStartDate?' and '?cEndDate?') and otherno=ljaget.otherno)";
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		sqlbv7.sql(tSql);
		sqlbv7.put("cManageCom", cManageCom);
		sqlbv7.put("cStartDate", cStartDate);
		sqlbv7.put("cEndDate", cEndDate);

		Strr = ExeSql(sqlbv7);
		return Strr;
	}

	/* 续期回退未付费或在以后付费 */
	public String[][] RNBalanceRollback(String cStartDate, String cEndDate,
			String cManageCom) {
		/* 收费号|收费机构|业务名称|业务号|金额|到帐日期|核销日期|缴费方式 */
		String[][] Strr = null;
		String tSql = "select actugetno,managecom,'续期回退退费',sumgetmoney,enteraccdate,makedate,otherno from ljaget "
				+ "where othernotype = '9' "
				+ "and managecom like concat('?cManageCom?','%') "
				+ "and makedate between '?cStartDate?' and '?cEndDate?' "
				+ "and (enteraccdate is null or enteraccdate > '?cEndDate?')";
		SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
		sqlbv8.sql(tSql);
		sqlbv8.put("cManageCom", cManageCom);
		sqlbv8.put("cStartDate", cStartDate);
		sqlbv8.put("cEndDate", cEndDate);
		Strr = ExeSql(sqlbv8);
		return Strr;
	}

	private String[][] ExeSql(SQLwithBindVariables cSql) {
		String[][] Strr = null;
		SSRS nSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		nSSRS = tExeSQL.execSQL(cSql);
		logger.debug(nSSRS.getMaxRow());
		Strr = new String[nSSRS.getMaxRow()][8];
		for (int i = 1; i <= nSSRS.getMaxRow(); i++) {
			String tTempFeeNo = nSSRS.GetText(i, 1);
			String tManageCom = nSSRS.GetText(i, 2);

			String tPayMode = "";
			if (tTempFeeNo != null && !tTempFeeNo.equals("")) {
				LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
				LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
				tLJTempFeeClassDB.setTempFeeNo(tTempFeeNo);
				tLJTempFeeClassSet = tLJTempFeeClassDB.query();
				if (tLJTempFeeClassSet.size() > 0) {
					tPayMode = GetCodeName(tLJTempFeeClassSet.get(1)
							.getPayMode(), "paymode");
				} else {
					tPayMode = "";
				}
			}
			String YWname = "";

			YWname = nSSRS.GetText(i, 3);

			DecimalFormat df = new DecimalFormat("0.00");
			String tPayMoney;
			if (nSSRS.GetText(i, 4) == null)
				tPayMoney = "0.00";
			else
				// tPayMoney = df.format(Double.parseDouble(nSSRS.GetText(i,
				// 4)));
				tPayMoney = nSSRS.GetText(i, 4);
			String tEnterAccDate = nSSRS.GetText(i, 5);
			String tConfDate = nSSRS.GetText(i, 6);
			String tOtherNo = nSSRS.GetText(i, 7);
			Strr[i - 1][0] = tTempFeeNo;
			Strr[i - 1][1] = tManageCom;
			Strr[i - 1][2] = YWname;
			Strr[i - 1][3] = tOtherNo;
			Strr[i - 1][4] = tPayMoney;
			Strr[i - 1][5] = tEnterAccDate;
			Strr[i - 1][6] = tConfDate;
			Strr[i - 1][7] = tPayMode;
		}
		return Strr;
	}

	public String[][] PosBalance(String cStartDate, String cEndDate,
			String cManageCom) {
		String[][] Strr = null;

		return Strr;
	}

	private String GetCodeName(String Code, String CodeType) {
		String CodeName = "";
		LDCodeDB tLDCodeDB = new LDCodeDB();
		tLDCodeDB.setCode(Code);
		tLDCodeDB.setCodeType(CodeType);
		if (!tLDCodeDB.getInfo()) {
			CodeName = Code;
		} else {
			CodeName = tLDCodeDB.getCodeName();
		}
		return CodeName;
	}

	public static void main(String[] args) {
		LJFinBalanceSub ljfinbalancesub = new LJFinBalanceSub();
	}
}
