package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Hezy Lys
 * @version 1.0
 * @date:2003-05-28
 */




import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.HashReport;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class FinDayListBL {
private static Logger logger = Logger.getLogger(FinDayListBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	private String mStartDate = ""; // 取得的时间
	private String mEndDate = ""; // 取得的时间
	private String mComCode = ""; // 报表统计机构
	private GlobalInput mGlobalInput = new GlobalInput();// 全局数据

	public FinDayListBL() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		int tCode = vertifyOperate(cOperate);
		if (tCode < 0) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		if (!getInputData(cInputData))
			return false;

		// 准备所有要打印的数据
		if (!VoucherReport(cOperate, tCode)) {
			logger.debug("Error occured : " + mErrors.getFirstError());
			return false;
		}

		return true;
	}

	/**
	 * 校验传入的操作类型
	 * 
	 * @param cOperate
	 * @return
	 */
	private int vertifyOperate(String cOperate) {
		String[] tOperate = { "Temp", "Advance", "Prem", "Claim", "EdorDuePay",
				"LiveGet", "OtherDuePay", "ActuPay",  "DuePayYE",
				"AdvanceGetYE","GLFY", "AirPol" };

		for (int i = 0; i < tOperate.length; i++)
			if (cOperate.equals(tOperate[i]))
				return i;

		return -1;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean VoucherReport(String cOperate, int cCode) {
		try {
			String sql = "";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			String[][] tableHead = new String[0][0];

			switch (cCode) {
			case 0: // 暂收保费
				tableHead = new String[1][4];
				tableHead[0][0] = "收据号";
				tableHead[0][1] = "金额";
				tableHead[0][2] = "合同号";
				tableHead[0][3] = "险种编码";
				sql = "select tempfeeno, (case when paymoney is not null then paymoney else 0 end), otherno, riskcode from ljtempfee a "
						+ "where confmakedate >=  '?mStartDate?' and confmakedate <= '?mEndDate?' "
						+ "and managecom like concat('?mComCode?','%')  and tempfeetype = '1' " + " order by tempfeeno, otherno, riskcode";
				sqlbv.sql(sql);
				sqlbv.put("mStartDate", mStartDate);
				sqlbv.put("mEndDate", mEndDate);
				sqlbv.put("mComCode", mComCode);
				break;
			case 1: // 预收保费
				tableHead = new String[1][4];
				tableHead[0][0] = "收据号";
				tableHead[0][1] = "金额";
				tableHead[0][2] = "保单号";
				tableHead[0][3] = "险种编码";
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				sql = "select tempfeeno, nvl(paymoney, 0), otherno, riskcode from ljtempfee "
						+ "where confmakedate >= '?mStartDate?' and confmakedate <= '?mEndDate?' "
						+ "and managecom like concat('?mComCode?','%') and tempfeetype <> '1' "
						+ "union all "
						+ "select  /*+index (IDX_LJAPAYPERSON_CONFDATE)+*/ payno, nvl(sumactupaymoney, 0), contno, riskcode from ljapayperson "
						+ "where confdate >= '?mStartDate?' and confdate <= '?mEndDate?' "
						+ "and managecom like concat('?mComCode?','%') and paytype = 'YET' and paycount = 1 and grpcontno = '00000000000000000000' "
						+ "union all "
						+ "select payno, nvl(sumactupaymoney, 0), grpcontno, riskcode from ljapaygrp "
						+ "where confdate >= '?mStartDate?' and confdate <= '?mEndDate?' "
						+ "and managecom like concat('?mComCode?','%') and paytype = 'YET' and paycount = 1 "
						+ "order by 1";
				}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
					sql = "select tempfeeno, (case when paymoney is not null then paymoney else 0 end), otherno, riskcode from ljtempfee "
							+ "where confmakedate >= '?mStartDate?' and confmakedate <= '?mEndDate?' "
							+ "and managecom like concat('?mComCode?','%') and tempfeetype <> '1' "
							+ "union all "
							+ "select  payno, (case when sumactupaymoney is not null then sumactupaymoney else 0 end), contno, riskcode from ljapayperson "
							+ "where confdate >= '?mStartDate?' and confdate <= '?mEndDate?' "
							+ "and managecom like concat('?mComCode?','%') and paytype = 'YET' and paycount = 1 and grpcontno = '00000000000000000000' "
							+ "union all "
							+ "select payno, (case when sumactupaymoney is not null then sumactupaymoney else 0 end), grpcontno, riskcode from ljapaygrp "
							+ "where confdate >= '?mStartDate?' and confdate <= '?mEndDate?' "
							+ "and managecom like concat('?mComCode?','%') and paytype = 'YET' and paycount = 1 "
							+ "order by 1";
				}
				sqlbv.sql(sql);
				sqlbv.put("mStartDate", mStartDate);
				sqlbv.put("mEndDate", mEndDate);
				sqlbv.put("mComCode", mComCode);
				break;
			case 2: // 保费收入
				tableHead = new String[1][6];
				tableHead[0][0] = "合同号";
				tableHead[0][1] = "科目名称";
				tableHead[0][2] = "险种编码";
				tableHead[0][3] = "险种名称";
				tableHead[0][4] = "渠道";
				tableHead[0][5] = "金额";
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
					sql = "select /*+index (IDX_LJAPAYPERSON_CONFDATE)+*/ a.contno,(decode(a.payintv,0,'趸缴',-1,'趸缴',decode(a.paycount,'1','首年首期',(case when (a.curpaytodate - add_months(b.cvalidate, 12)) > 1 then  '续年续期'  else  '首年续期'	end)))), "
							+ "a.riskcode,(select riskname from lmriskapp where riskcode = a.riskcode), "
							+ "(select codename from ldcode	 where codetype = 'salechnl' and code = b.salechnl),nvl(a.sumactupaymoney, 0) "
							+ "from ljapayperson a, lcpol b "
							+ "where a.polno = b.polno " + "and a.confdate >= '?mStartDate?'" 
							+ " and a.confdate <= '?mEndDate?' "
							+ "and a.paytype = 'ZC' and a.managecom like concat('?mComCode?','%') and a.grppolno = '00000000000000000000' "
							+ "and not exists (select 1 from ljaget	 where actugetno = a.payno and othernotype = '10' " 
							+ "union select 1 from ljapay where payno = a.payno	 and incometype = '10') "
							+ "union "
							+ "select  a.contno,(decode(a.payintv,0,'趸缴',-1,'趸缴',decode(a.paycount,'1','首年首期',(case when (a.curpaytodate - add_months(b.cvalidate, 12)) > 1 then  '续年续期'  else  '首年续期'	end)))), "
							+ "a.riskcode,(select riskname from lmriskapp where riskcode = a.riskcode), "
							+ "(select codename from ldcode	 where codetype = 'salechnl' and code = b.salechnl),nvl(a.sumactupaymoney, 0) "
							+ "from ljapayperson a, lbpol b "
							+ "where a.polno = b.polno " + "and a.confdate >= '?mStartDate?'" 
							+ " and a.confdate <= '?mEndDate?' "
							+ "and a.paytype = 'ZC' and a.managecom like concat('?mComCode?','%') and a.grppolno = '00000000000000000000' "
							+ "and not exists (select 1 from ljaget	 where actugetno = a.payno and othernotype = '10' " 
							+ "union select 1 from ljapay where payno = a.payno	 and incometype = '10') "

							+ "union all "
							+ "select a.grpcontno,(decode(a.payintv, 0, '趸缴', -1, '趸缴',decode(a.paycount,'1','首年首期',(case 	when (a.curpaytodate - add_months(b.cvalidate, 12)) > 1 then '续年续期' else '首年续期'	end)))), "
							+ "a.riskcode,(select riskname from lmriskapp where riskcode = a.riskcode), "
							+ "(select codename from ldcode where codetype = 'salechnl'  and code = b.salechnl), nvl(a.sumactupaymoney, 0) "
							+ "from ljapaygrp a, lcgrppol b "
							+ "where a.grppolno = b.grppolno "
							+ "and a.confdate >= '?mStartDate?' and a.confdate <= '?mEndDate?' "
							+ "and a.paytype = 'ZC' and a.managecom like concat('?mComCode?','%') "
							+ "  and not exists (select 1 from ljaget where actugetno = a.payno and othernotype = '10' "
							+ " union select 1 from ljapay where payno = a.payno and incometype = '10')" 
							+ "order by 1";
					}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
						sql = "select a.contno,((case a.payintv when 0 then '趸缴' when -1 then '趸缴' else (case a.paycount when 1 then '首年首期' else (case when datediff(a.curpaytodate , add_months(b.cvalidate, 12)) > 1 then  '续年续期'  else  '首年续期'	end) end) end)), "
								+ "a.riskcode,(select riskname from lmriskapp where riskcode = a.riskcode), "
								+ "(select codename from ldcode	 where codetype = 'salechnl' and code = b.salechnl),(case when a.sumactupaymoney is not null then a.sumactupaymoney else 0 end) "
								+ "from ljapayperson a, lcpol b "
								+ "where a.polno = b.polno " + "and a.confdate >= '?mStartDate?'" 
								+ " and a.confdate <= '?mEndDate?' "
								+ "and a.paytype = 'ZC' and a.managecom like concat('?mComCode?','%') and a.grppolno = '00000000000000000000' "
								+ "and not exists (select 1	from ljaget	 where actugetno = a.payno and othernotype = '10' " 
								+ "union select 1 from ljapay where payno = a.payno	 and incometype = '10') "
								+ "union "
								+ "select a.contno,((case a.payintv when 0 then '趸缴' when -1 then '趸缴' else (case a.paycount when 1 then '首年首期' else (case when datediff(a.curpaytodate , add_months(b.cvalidate, 12)) > 1 then  '续年续期'  else  '首年续期'	end) end) end)), "
								+ "a.riskcode,(select riskname from lmriskapp where riskcode = a.riskcode), "
								+ "(select codename from ldcode	 where codetype = 'salechnl' and code = b.salechnl),(case when a.sumactupaymoney is not null then a.sumactupaymoney else 0 end) "
								+ "from ljapayperson a, lbpol b "
								+ "where a.polno = b.polno " + "and a.confdate >= '?mStartDate?'" 
								+ " and a.confdate <= '?mEndDate?' "
								+ "and a.paytype = 'ZC' and a.managecom like concat('?mComCode?','%') and a.grppolno = '00000000000000000000' "
								+ "and not exists (select 1	from ljaget	 where actugetno = a.payno and othernotype = '10' " 
								+ "union select 1 from ljapay where payno = a.payno	 and incometype = '10') "

								+ "union all "
								+ "select a.grpcontno,((case a.payintv when 0 then '趸缴' when -1 then '趸缴' else (case a.paycount when 1 then '首年首期' else (case when datediff(a.curpaytodate , add_months(b.cvalidate, 12)) > 1 then  '续年续期'  else  '首年续期'	end) end) end)), "
								+ "a.riskcode,(select riskname from lmriskapp where riskcode = a.riskcode), "
								+ "(select codename from ldcode where codetype = 'salechnl' and code = b.salechnl), (case when a.sumactupaymoney is not null then a.sumactupaymoney else 0 end) "
								+ "from ljapaygrp a, lcgrppol b "
								+ "where a.grppolno = b.grppolno "
								+ "and a.confdate >= '?mStartDate?' and a.confdate <= '?mEndDate?' "
								+ "and a.paytype = 'ZC' and a.managecom like concat('?mComCode?','%') "
								+ " and not exists (select 1 from ljaget where actugetno = a.payno	 and othernotype = '10' "
								+ " union select 1 from ljapay where payno = a.payno and incometype = '10')" 
								+ "order by 1";
					}
				
				sqlbv.sql(sql);
				sqlbv.put("mStartDate", mStartDate);
				sqlbv.put("mEndDate", mEndDate);
				sqlbv.put("mComCode", mComCode);
				break;
			case 3: // 赔款支出
				tableHead = new String[1][7];
				tableHead[0][0] = "实付号";
				tableHead[0][1] = "科目名称";
				tableHead[0][2] = "险种编码";
				tableHead[0][3] = "险种名称";
				tableHead[0][4] = "渠道";
				tableHead[0][5] = "金额";
				tableHead[0][6] = "合同号";
				sql = "select actugetno,(select codename from ldcode where codetype = 'feefinatype' and code = a.feefinatype),riskcode, "
						+ "(select riskname from lmriskapp where riskcode = a.riskcode), "
						+ "(select codename from ldcode where codetype = 'salechnl' and code = a.salechnl),(case when pay is not null then pay else 0 end),contno "
						+ "from ljagetclaim a " + "where makedate >= '?mStartDate?' and makedate <= '?mEndDate?' "
						+ "and managecom like concat('?mComCode?','%') "
						+ "	 and feefinatype not in ('HDLX', 'HKLX', 'BF', 'HK', 'HD', 'HLRB', 'YFRB','HLTF', 'DJTF', 'XJTF', 'LJTF') "
						+ " order by actugetno, contno, riskcode ";
				sqlbv.sql(sql);
				sqlbv.put("mStartDate", mStartDate);
				sqlbv.put("mEndDate", mEndDate);
				sqlbv.put("mComCode", mComCode);
				break;
			case 4: // 保全应付
				tableHead = new String[1][7];
				tableHead[0][0] = "实付号";
				tableHead[0][1] = "科目名称";
				tableHead[0][2] = "险种编码";
				tableHead[0][3] = "险种名称";
				tableHead[0][4] = "渠道";
				tableHead[0][5] = "金额";
				tableHead[0][6] = "保单号";
				sql = "select actugetno,(select codename from ldcode where codetype = 'feefinatype' and code = a.feefinatype),riskcode, "
						+ "(select riskname from lmriskapp where riskcode = a.riskcode), "
						+ "(select codename from ldcode where codetype = 'salechnl' and code = (select salechnl from lcpol where polno = a.polno)), "
						+ "(case when getmoney is not null then getmoney else 0 end),contno "
						+ "from ljagetendorse a "
						+ "where makedate >= '?mStartDate?' and makedate <= '?mEndDate?' "
						+ "and managecom like concat('?mComCode?','%') "
//						+ "and feefinatype not in ('LX', 'BF', 'GB', 'HK', 'HD',  'BD') "
						+ "and feefinatype  in ('TF','TB','YE','DK','DJ','LJTF','RV') "
						+ "order by actugetno, contno, riskcode ";
				sqlbv.sql(sql);
				sqlbv.put("mStartDate", mStartDate);
				sqlbv.put("mEndDate", mEndDate);
				sqlbv.put("mComCode", mComCode);
				break;
			case 5: // 领取类给付
				tableHead = new String[1][7];
				tableHead[0][0] = "实付号";
				tableHead[0][1] = "科目名称";
				tableHead[0][2] = "险种编码";
				tableHead[0][3] = "险种名称";
				tableHead[0][4] = "渠道";
				tableHead[0][5] = "金额";
				tableHead[0][6] = "保单号";
				sql = "select serialno,(select codename from ldcode where codetype = 'feefinatype' and code = a.moneytype),riskcode,"
						+ "(select riskname from lmriskapp where riskcode = a.riskcode),"
						+ "(select codename from ldcode where codetype = 'salechnl' and code = (select salechnl from lcpol where polno = a.polno)),"
						+ "(case when money is not null then money else 0 end),contno "
						+ "from lcinsureacctrace a "
						+ "where makedate >= '?mStartDate?' and makedate <= '?mEndDate?' and moneytype in ('YF', 'EF', 'YFLX', 'EFLX') "
						+ "and managecom like concat('?mComCode?','%') "
						+ "order by serialno ";
				sqlbv.sql(sql);
				sqlbv.put("mStartDate", mStartDate);
				sqlbv.put("mEndDate", mEndDate);
				sqlbv.put("mComCode", mComCode);
				break;
			case 6: // 其他应付
				tableHead = new String[1][5];
				tableHead[0][0] = "实付号";
				tableHead[0][1] = "科目名称";
				tableHead[0][2] = "金额";
				tableHead[0][3] = "保单号";
				tableHead[0][4] = "险种编码";
				sql = "select actugetno,'暂收退费',(case when getmoney is not null then getmoney else 0 end), "
					+ "(select otherno from ljtempfee where tempfeeno = a.tempfeeno and riskcode = a.riskcode and tempfeetype = a.tempfeetype and currency=a.currency),riskcode  "
					+ "from LJAGetTempFee a  "
					+ "where makedate >= '?mStartDate?' and makedate <= '?mEndDate?'  "
					+ "and managecom like concat('?mComCode?','%') "
					+ "union all  "
					+ "select actugetno, '红利分配支出', (case when getmoney is not null then getmoney else 0 end), contno, riskcode  "
					+ "from LJABonusGet  "
					+ "where makedate >= '?mStartDate?' and makedate <= '?mEndDate?'  "
					+ "and feefinatype in ('LJTF','HLTF') and managecom like concat('?mComCode?','%') "
					+ "union all  "
					+ "select '',(select codename from ldcode where codetype='feefinatype' and code=a.moneytype), "
					+ " (case when money is not null then money else 0 end), contno, riskcode  "
					+ "from LCInsureAccTrace a  "
					+ "where grpcontno = '00000000000000000000' and makedate >= '?mStartDate?' and makedate <= '?mEndDate?'  "
					+ "and managecom like concat('?mComCode?','%') and moneytype in ('HL','HLLX','DJLX','YCLX') "
					+ "union all  "
					+ "select '',(case moneytype when 'HLLX' then '红利利息' else '红利分配支出' end), (case when sum(money) is not null then sum(money) else 0 end), grpcontno, ''  "
					+ "from LCInsureAccTrace  "
					+ "where grpcontno <> '00000000000000000000' and makedate >= '?mStartDate?' and makedate <= '?mEndDate?'  "
					+ "and managecom like concat('?mComCode?','%') and moneytype in ('HL', 'HLLX')  "
					+ "group by grpcontno, moneytype "
					+ "union all  "
					+ "select actugetno, '预收退费', (case when getmoney is not null then getmoney else 0 end), otherno,''  "
					+ "from LJAGetOther a  "
					+ "where makedate >= '?mStartDate?' and makedate <= '?mEndDate?'  "
					+ "and managecom like concat('?mComCode?','%') "
					+ "order by 1 ";
				sqlbv.sql(sql);
				sqlbv.put("mStartDate", mStartDate);
				sqlbv.put("mEndDate", mEndDate);
				sqlbv.put("mComCode", mComCode);
				break;
			case 7: // 实付
				tableHead = new String[1][4];
				tableHead[0][0] = "实付号";
				tableHead[0][1] = "科目名称";
				tableHead[0][2] = "金额";
				tableHead[0][3] = "保单号";
				sql = "select * from (" 
					+"select actugetno ano,'付费' ptype,(case when (select codename from ldcode where codetype='feefinatype' and code=a.feefinatype) is not null then (select codename from ldcode where codetype='feefinatype' and code=a.feefinatype) else '保全项目' end) ftype,(select codename from ldcode where codetype = 'paymode' and code = (select paymode from ljaget where actugetno = a.actugetno)) pmode, "
					+ "getmoney,polno  "
					+ "from LJAGetEndorse a  "
					+ "where getconfirmdate >='"+"?mStartDate?"+"' and getconfirmdate <= '?mEndDate?'  "
					+ "and managecom like concat('?mComCode?','%') and feefinatype not in('CM')  "
					+ "and exists(select 1 from ljaget where actugetno = a.actugetno and othernotype = '10')  "
					+ "union all  "
					+ "select actugetno ano,'付费' ptype,(case when (select codename from ldcode where codetype='feefinatype' and code=a.feefinatype) is not null then (select codename from ldcode where codetype='feefinatype' and code=a.feefinatype) else '生存领取' end) ftype,(select codename from ldcode where codetype = 'paymode' and code = (select paymode from ljaget where actugetno = a.actugetno)) pmode, "
					+ "getmoney,polno  "
					+ "from LJAGetDraw a  "
					+ "where confdate >='?mStartDate?' and confdate <= '?mEndDate?' and managecom like concat('?mComCode?','%') "
					+ "union all  "
					+ "select actugetno ano,'付费' ptype,'暂收退费' ftype,(select codename from ldcode where codetype = 'paymode' and code = (select paymode from ljaget where actugetno = a.actugetno)) pmode "
					+ ",getmoney,(select otherno from ljtempfee where tempfeeno = a.tempfeeno and tempfeetype = a.tempfeetype and riskcode = a.riskcode and currency=a.currency)  "
					+ "from LJAGetTempFee a  "
					+ "where confdate >='?mStartDate?' and confdate <= '?mEndDate?'  "
					+ "and managecom like concat('?mComCode?','%')  "
					+ "union all  "
					+ "select actugetno ano,'付费' ptype,(case when (select codename from ldcode where codetype='feefinatype' and code=a.feefinatype) is not null then (select codename from ldcode where codetype='feefinatype' and code=a.feefinatype) else '理赔项目' end) ftype,(select codename from ldcode where codetype = 'paymode' and code = (select paymode from ljaget where actugetno = a.actugetno)) pmode, "
					+ "pay,polno  "
					+ "from LJAGetClaim a  "
					+ "where confdate >='?mStartDate?' and confdate <= '?mEndDate?'  "
					+ "and managecom like concat('?mComCode?','%')                          "
					+ "and exists(select 1 from ljaget where actugetno = a.actugetno and othernotype = '5')  "
					+ "union all  "
					//xuyunpeng change “=”变为 “in”
					+ "select actugetno ano,'付费' ptype,(case when (select codename from ldcode where codetype='feefinatype' and code=a.feefinatype) is not null then (select codename from ldcode where codetype='feefinatype' and code=a.feefinatype) else '红利实付' end) ftype,(select codename from ldcode where codetype = 'paymode' and code in (select paymode from ljaget where actugetno = a.actugetno)) pmode, "
					+ "getmoney,otherno  "
					+ "from LJABonusGet a  "
					+ "where confdate >='?mStartDate?' and confdate <= '?mEndDate?'  "
					+ "and managecom like concat('?mComCode?','%')"
					+ "union all  "
					+ "select actugetno ano,'付费' ptype,'溢交退费' ftype,(select codename from ldcode where codetype = 'paymode' and code = (select paymode from ljaget where actugetno = a.actugetno)) pmode, "
					+ "getmoney,otherno  "
					+ "from LJAGetOther a  "
					+ "where confdate >='?mStartDate?' and confdate <= '?mEndDate?'  "
					+ "and managecom like concat('?mComCode?','%') " 
					+ "union all "
					+"select actugetno ano,'收费' ptype,(case when (select codename from ldcode where codetype='feefinatype' and code=a.feefinatype) is not null then (select codename from ldcode where codetype='feefinatype' and code=a.feefinatype) else '保全项目' end) ftype,'' pmode, "
					+ "getmoney,polno  "
					+ "from LJAGetEndorse a  "
					+ "where getconfirmdate >='?mStartDate?' and getconfirmdate <= '?mEndDate?'  "
					+ "and managecom like concat('?mComCode?','%') and feefinatype in ('BF', 'LX', 'HD', 'GB','HK')  "
					+ "and exists(select 1 from ljapay where payno=a.actugetno and incometype='10')  "
					+ "union all  "
					+ "select actugetno ano,'收费' ptype,(case when (select codename from ldcode where codetype='feefinatype' and code=a.feefinatype) is not null then (select codename from ldcode where codetype='feefinatype' and code=a.feefinatype) else '理赔项目' end) ftype,'' pmode, "
					+ "pay,polno  "
					+ "from LJAGetClaim a  "
					+ "where confdate >='?mStartDate?' and confdate <= '?mEndDate?'  "
					+ "and managecom like concat('?mComCode?','%')                         "
					+ "and exists(select 1 from ljapay where payno=a.actugetno and incometype='5')  "
					+ "union all "
					+ "select payno ano,'收费' ptype,'保费收入-趸缴' ftype,'' pmode,sumactupaymoney, polno " 
					+ "from ljapayperson a "
					+ "where confdate >= '?mStartDate?' and confdate <= '?mEndDate?' "
					+ "and paytype = 'ZC' and managecom like concat('?mComCode?','%') and grppolno = '00000000000000000000' "
					+ "and exists((select 1 from ljaget where actugetno=a.payno and othernotype='10' ) "
					+ "union all (select 1 from ljapay where payno=a.payno and  incometype='10')) "
					+ "union all "
					+ "select payno ano,'收费' ptype,'保费收入-趸缴' ftype,'' pmode,sumactupaymoney,grppolno "
					+ "from ljapaygrp a "
					+ "where confdate >= '?mStartDate?' and confdate <= '?mEndDate?' "
					+ "and paytype = 'ZC' and managecom like concat('?mComCode?','%') "
					+ "and exists((select 1 from ljaget where actugetno=a.payno and othernotype='10' ) "
					+ "union all (select 1 from ljapay where payno=a.payno and  incometype='10')) "
					+ ") g order by ptype,pmode,ftype,ano ";
				sqlbv.sql(sql);
				sqlbv.put("mStartDate", mStartDate);
				sqlbv.put("mEndDate", mEndDate);
				sqlbv.put("mComCode", mComCode);
				break;
/*			case 8: // 保全收费
				tableHead = new String[1][6];
				tableHead[0][0] = "实付号";
				tableHead[0][1] = "科目名称";
				tableHead[0][2] = "险种名称";
				tableHead[0][3] = "渠道";
				tableHead[0][4] = "金额";
				tableHead[0][5] = "保单号";
				sql = "select actugetno,(select distinct codealias from ldcode1 where codetype = a.feeoperationtype and code = a.feefinatype and code1='000000'),"
						+ "(select riskname from lmriskapp where riskcode = a.riskcode ),"
						+ "decode((select trim(salechnl) from lcpol where polno = a.polno) ,'1','个人营销','2','团险直销','3','银行代理','5','电话直销','其他'),"
						+ "getmoney, contno    "
						+ "from ljagetendorse a "
						+ "where (feefinatype in ('BF','GB','HK','LX','RV') ) "
						+ "and makedate >= '" + mStartDate	+ "' and makedate <= '"	+ mEndDate + "' "
						+ "and managecom like '" + mComCode	+ "%' "
						+ "union all "
						+ "select payno,'保费收入-趸缴',"
						+ "(select riskname from lmriskapp where riskcode = a.riskcode),"
						+ "(select decode(trim(salechnl),'1','个人营销','2','团险直销','3','银行代理','5','电话直销','其他') from lcpol where polno = a.polno),sumactupaymoney," 
						+ " (select contno from lcpol where polno=a.polno)   "
						+ "from ljapayperson a "
						+ "where confdate >= '"	+ mStartDate + "' and confdate <= '" + mEndDate + "' "
						+ "and paytype = 'ZC' and managecom like '"	+ mComCode	+ "%' and grppolno = '00000000000000000000' "
						+ "and (payno in(select actugetno from ljaget where othernotype='10') "
						+ "or payno in(select payno from ljapay where othernotype='10')) "
						+ "union all "
						+ "select payno,'保费收入-趸缴',"
						+ "(select riskname from lmriskapp where riskcode = a.riskcode),"
						+ "(select decode(trim(salechnl),'1','个人营销','2','团险直销','3','银行代理','5','电话直销','其他') from lcgrppol where grppolno = a.grppolno),sumactupaymoney,"
						+ "  (select grpcontno from lcgrppol where grppolno=a.grppolno)   "
						+ "from ljapaygrp a "
						+ "where confdate >= '"	+ mStartDate + "' and confdate <= '" + mEndDate	+ "' "
						+ "and paytype = 'ZC' and managecom like '"	+ mComCode + "%' "
						+ "and (payno in(select actugetno from ljaget where othernotype='10') "
						+ "or payno in(select payno from ljapay where othernotype='10')) "
						+ "order by 1 ";
				break;*/
//			case 9://保费收入
//			tableHead = new String [1][4];
//			tableHead[0][0] = "保单号";
//			tableHead[0][1] = "缴费方式";
//			tableHead[0][2] = "险种名称";
//			tableHead[0][3] = "渠道";
//			tableHead[0][4] = "金额";
//			sql = "select (select contno from lcpol where polno=a.polno),(decode(a.payintv,0,'趸缴',-1,'趸缴',decode(a.paycount,'1','首年首期',(case when (a.curpaytodate - add_months(b.cvalidate,12)) >1 then '续年续期' else '首年续期' end)))), "
//			+ "(select riskname from lmriskapp where riskcode = a.riskcode), "
//			+ "decode(trim(b.salechnl),'1','个人营销','2','团险直销','3','银行代理','5','电话直销','其他'),a.sumactupaymoney  "
//			+ "from ljapayperson a ,lcpol b "
//			+ "where a.polno = b.polno   and a.confdate >= '" + mStartDate + "' and a.confdate <= '" + mEndDate + "'  "
//			+ "and a.paytype = 'ZC' and a.managecom like concat('?mComCode?','%')
//			+ "and a.grppolno = '00000000000000000000' "
//			+ "and a.payno not in(select actugetno from ljaget where othernotype='10') "
//			+ "and a.payno not in(select payno from ljapay where othernotype='10')"
//			+ "union all "
//			+ "select(select contno from lcpol where polno=a.grppolno),(decode(a.payintv,0,'趸缴',-1,'趸缴',decode(a.paycount,'1','首年首期',(case when (a.curpaytodate - add_months(b.cvalidate,12)) >1 then '续年续期' else '首年续期' end)))), "
//			+ "(select riskname from lmriskapp where riskcode = a.riskcode), "
//			+ "decode(trim(b.salechnl),'1','个人营销','2','团险直销','3','银行代理','5','电话直销','其他'),a.sumactupaymoney "
//			+ "from ljapaygrp a,lcgrppol b "
//			+ "where a.grppolno = b.grppolno "
//			+ "and a.confdate >= '" + mStartDate + "' and a.confdate <= '" + mEndDate + "' "
//			+ "and a.paytype = 'ZC' and a.managecom like concat('?mComCode?','%')
//			+ "and not exists (select 'X' from  ljaget d where a.payno = d.actugetno and othernotype = '10') "
//			+ "and not exists( select 'X' from  ljapay e where a.payno = e.payno and othernotype = '10') "
//			+ "order by 1";
			
//				break;
			case 8: //应付余额 
			tableHead = new String [1][6];
			tableHead[0][0] = "实付号";
			tableHead[0][1] = "科目名称";
			tableHead[0][2] = "金额";
			tableHead[0][3] = "保单号";
			tableHead[0][4] = "险种编码";
			tableHead[0][5] = "险种名称";
			sql = "select actugetno,(select codename from ldcode where codetype='feefinatype' and code=a.feefinatype), "
				+ "getmoney,contno,riskcode,(select riskname from lmrisk where riskcode=a.riskcode)	 "
				+ "from ljagetendorse a   "
				+ "where makedate >= '?mStartDate?' and makedate <= '?mEndDate?' "
				+ "and managecom like concat('?mComCode?','%') and getconfirmdate is not null "
				+ "union all  "
				+ "select actugetno, (select codename from ldcode where codetype='feefinatype' and code=a.feefinatype), "
				+ "getmoney, contno,riskcode,(select riskname from lmrisk where riskcode=a.riskcode)  "
				+ "from ljagetdraw a   "
				+ "where makedate >= '?mStartDate?' and makedate <= '?mEndDate?' "
				+ "and managecom like concat('?mComCode?','%') and confdate is not null "
				+ "union all  "
				+ "select actugetno,'暂收退费',getmoney, "
				+ "(select otherno from ljtempfee where tempfeeno = a.tempfeeno and riskcode = a.riskcode and tempfeetype = a.tempfeetype and currency=a.currency), "
				+ "riskcode,(select riskname from lmrisk where riskcode=a.riskcode) "
				+ "from ljagettempfee a  "
				+ "where makedate >= '?mStartDate?'	 and makedate <= '?mEndDate?' "
				+ "and managecom like concat('?mComCode?','%') and confdate is not null "
				+ "union all  "
				+ "select actugetno,(select codename from ldcode where codetype='feefinatype' and code=a.feefinatype), "
				+ "pay,contno,riskcode,(select riskname from lmrisk where riskcode=a.riskcode)	 "
				+ "from ljagetclaim a   "
				+ "where makedate >= '?mStartDate?' and makedate <= '?mEndDate?' "
				+ "and managecom like concat('?mComCode?','%') and confdate is not null "
				+ "union all  "
				+ "select actugetno,(select codename from ldcode where codetype='feefinatype' and code=a.feefinatype),  "
				+ "getmoney, contno,riskcode,(select riskname from lmrisk where riskcode=a.riskcode)	 "
				+ "from ljabonusget a  "
				+ "where makedate >= '?mStartDate?'	 and makedate <= '?mEndDate?' "
				+ "and managecom like concat('?mComCode?','%') and confdate is not null "
				+ "union all  "
				+ "select actugetno, '其他退费', getmoney, otherno,'','' "
				+ "from ljagetother  "
				+ "where makedate >= '?mStartDate?'	 and makedate <= '?mEndDate?' "
				+ "and managecom like concat('?mComCode?','%') and confdate is not null order by actugetno ";
			sqlbv.sql(sql);
			sqlbv.put("mStartDate", mStartDate);
			sqlbv.put("mEndDate", mEndDate);
			sqlbv.put("mComCode", mComCode);
			break;
			case 9: //预收余额
			tableHead = new String [1][3];
			tableHead[0][0] = "收据号";
			tableHead[0][1] = "金额";
			tableHead[0][2] = "保单号";
			sql = "select tempfeeno,paymoney,otherno from ljtempfee "
			+ "where makedate >= '?mStartDate?' and makedate <= '?mEndDate?' "
			+ "and managecom like concat('?mComCode?','%') and confdate is null "
			+ "order by tempfeeno";
			sqlbv.sql(sql);
			sqlbv.put("mStartDate", mStartDate);
			sqlbv.put("mEndDate", mEndDate);
			sqlbv.put("mComCode", mComCode);
			break;
			case 10: //健管理费收入
			tableHead = new String [1][4];
			tableHead[0][0] = "保单号";
			tableHead[0][1] = "科目名称";
			tableHead[0][2] = "险种名称";
			tableHead[0][3] = "渠道";
			tableHead[0][3] = "金额";
			sql ="select a.grpcontno,((case a.payintv when 0 then '趸缴' when -1 then '趸缴' else (case a.paycount when 1 then '首年首期' else (case when (datediff(a.curpaytodate , add_months(b.cvalidate, 12))) > 1 then '续年续期' else '首年续期' end) end) end)), "
				 + "(select riskname from lmriskapp where riskcode = a.riskcode), "
				 + "(select codename from ldcode where codetype = 'salechnl' and code = b.salechnl), "
//				 + "round((a.SumActuPayMoney * b.ManageFeeRate), 2) "
				 + "(select (case when sum(fee) is not null then sum(fee) else 0 end) from lcinsureaccfeetrace where grppolno=a.grppolno and makedate=a.makedate and feecode like '633%') "
				 + "from ljapaygrp a, lcgrppol b "
				 + "where a.grppolno = b.grppolno "
				 + " and a.confdate >= '?mStartDate?' and a.confdate <= '?mEndDate?' "
				 + " and a.paytype = 'TM' and a.managecom like concat('?mComCode?','%')"
				 + " and not exists (select 1 from ljapay where payno = a.payno and incometype = '10') "
				 + " and not exists (select 1 from dual where a.paycount = 1 and b.StandbyFlag4 = '1' and b.appflag = '1') "
				 + "union all "
				 + "select a.grpcontno,((case a.payintv when 0 then '趸缴' when -1 then '趸缴' else (case a.paycount when 1 then '首年首期' else (case when (datediff(a.curpaytodate , add_months(b.cvalidate, 12))) > 1 then '续年续期' else '首年续期' end) end) end)), "
				 + "(select riskname from lmriskapp where riskcode = a.riskcode), "
				 + "(select codename from ldcode where codetype = 'salechnl' and code = b.salechnl), "
//				 + "round((a.SumActuPayMoney * b.ManageFeeRate), 2) "
				 + "(select (case when sum(fee) is not null then sum(fee) else 0 end) from lcinsureaccfeetrace where grppolno=a.grppolno and makedate=a.makedate and feecode like '633%') "
				 + "from ljapaygrp a, lcgrppol b "
				 + "where a.grppolno = b.grppolno "
				 + " and a.confdate >= '?mStartDate?' and a.confdate <= '?mEndDate?' "
				 + " and a.paytype = 'TM' and a.managecom like concat('?mComCode?','%')"
				 + " and exists (select 1 from ljapay where payno = a.payno and incometype = '10') "
				 + "order by 1 ";
			sqlbv.sql(sql);
			sqlbv.put("mStartDate", mStartDate);
			sqlbv.put("mEndDate", mEndDate);
			sqlbv.put("mComCode", mComCode);	

			break;
			   case 11:                    //航意险日结清单
                   tableHead = new String [1][5];
                   tableHead[0][0] = "险种编码";
                   tableHead[0][1] = "保单号";
                   tableHead[0][2] = "金额";
                   tableHead[0][3] = "状态";
                   tableHead[0][4]="代理网点";
                   //根据新规则航意险结算时从lcairpol转到lbairpol表
                   sql = "select riskcode, polno, prem, (case polstate when '2' then '无效' else '有效' end), agentcom from lcairpol  "
                   		+"where makedate >= '?mStartDate?'  and makedate <= '?mEndDate?'  "
                   		+"and managecom like concat('?mComCode?','%')  and state in ('A', 'B', 'C') " 
                   		+"union all "
                   		+"select riskcode, polno, prem * -1, '无效', agentcom  from lcairpol "
                   		+"where ModifyDate >= '?mStartDate?' and ModifyDate <= '?mEndDate?'  "
						+"and managecom like concat('?mComCode?','%')  and state in ('A', 'B', 'C') and polstate = '2' "
						+"union all "
						+"select riskcode, polno, prem, (case polstate when '2' then '无效' else '有效' end), agentcom from lbairpol  "
                   		+"where makedate >= '?mStartDate?'  and makedate <= '?mEndDate?'  "
                   		+"and managecom like concat('?mComCode?','%')  and state in ('A', 'B', 'C') " 
                   		+"union all "
                   		+"select riskcode, polno, prem * -1, '无效', agentcom  from lbairpol "
                   		+"where ModifyDate >= '?mStartDate?' and ModifyDate <= '?mEndDate?'  "
						+"and managecom like concat('?mComCode?','%')  and state in ('A', 'B', 'C') and polstate = '2' ";
       			sqlbv.sql(sql);
    			sqlbv.put("mStartDate", mStartDate);
    			sqlbv.put("mEndDate", mEndDate);
    			sqlbv.put("mComCode", mComCode);
                   break;
//			case 13: //冲消航意险
//			tableHead = new String [1][5];
//			tableHead[0][0] = "保单号";
//			tableHead[0][1] = "科目名称";
//			tableHead[0][2] = "险种名称";
//			tableHead[0][3] = "渠道";
//			tableHead[0][4] = "金额";
//			sql = "select a.polno,'趸缴',"
//			+ "(select riskname from lmriskapp where riskcode = a.riskcode),"
//			+ "'团体险',a.prem "
//			+ "from lcairpol a "
//			+ "where a.polstate='2' and a.modifydate >= '" + mStartDate + "'
//			and a.modifydate <= '" + mEndDate + "' "
//			+ "and a.managecom like concat('?mComCode?','%')
//			 + "order by 1";
//			 break; //一期不做开发 ---hb
			}
			logger.debug(sql);
			SSRS tssrs = new ExeSQL().execSQL(sqlbv);
			if (tssrs == null || tssrs.getMaxRow() <= 0) {
				buildError("VoucherReport", "清单中无数据 ： "
						+ tssrs.mErrors.getFirstError());
				return false;
			}
			// 获取生成文件的路径
			LDSysVarDB tLDSysVarDB = new LDSysVarDB();
			tLDSysVarDB.setSysVar("CreatListPath");
			if (!tLDSysVarDB.getInfo()) {
				buildError("VoucherReport", "无法获得生成文件的路径！");
				return false;
			}
			StringBuilder sb = new StringBuilder();
			
			// Update by YaoYi in 2011-9-27 for code capability.
			sb.append(tLDSysVarDB.getSysVarValue()).append("\\")
				.append(cOperate).append("List_").append(mComCode)
				.append("_").append(mGlobalInput.Operator).append(".xls.z");
			String tFileName = sb.toString();
//			String tFileName = tLDSysVarDB.getSysVarValue() + "\\" + cOperate + "\\"
//					+ "List_" + mComCode + "_" + mGlobalInput.Operator
//					+ ".xls.z";
			HashReport tHashReport = new HashReport();
			if (!tHashReport.outputArrayToFile(tFileName, tableHead, tssrs
					.getAllData())) {
				buildError("VoucherReport", tHashReport.mErrors.getFirstError());
				return false;
			}
			logger.debug("File : " + tFileName + " Created !");

			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "FinDayListBL";
			tError.functionName = "VoucherReport";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			logger.debug(ex.toString());
			return false;
		}
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) // 打印付费
	{
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		TransferData tTransferData = (TransferData) cInputData
				.getObjectByObjectName("TransferData", 0);
		if (mGlobalInput == null || tTransferData == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		mStartDate = (String) tTransferData.getValueByName("StartDate");
		mEndDate = (String) tTransferData.getValueByName("EndDate");
		mComCode = (String) tTransferData.getValueByName("ComCode");

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "FinDayListBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "Test";
		tGlobalInput.ManageCom = "8611";
		tGlobalInput.ComCode = "86";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StartDate", "2004-12-07");
		tTransferData.setNameAndValue("EndDate", "2004-12-07");
		tTransferData.setNameAndValue("ComCode", "8611");

		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tTransferData);

		FinDayListBL tFinDayListBL = new FinDayListBL();
		tFinDayListBL.submitData(tVData, "OtherDuePay");
	}
}
