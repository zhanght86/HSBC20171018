package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.bq.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author pst
 * @version 1.0
 * @CreateDate：2009-04-11 清单名称：保全工作量统计表
 */

public class EdorWorkloadCountBillPrintBL implements BqBill {
private static Logger logger = Logger.getLogger(EdorWorkloadCountBillPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();

	private VData mInputData = new VData();

	private String mOperate = "";

	private GlobalInput mGlobalInput = new GlobalInput();

	public static final String VTS_NAME = "EdorWorkloadCountBill.vts";

	private TextTag texttag = new TextTag();

	private String mManageCom = "";

	private String mSaleChnl = "";

	private String mStartDate = "";

	private String mEndDate = "";

	private String mUserCode = "";

	private String mEdorType = "";

	private String mSaleChlSQL = "";

	private String mUserCodeSQL = "";

	private String mEdorTypeSQL = "";

	private String mRePortFlag = "";

	private String mRePortFlagSQL = "";

	private String mRePortFlagSQLMG = "";

	private int tComLength = 0;

	private String ComGrade = "";
	
	private String mDateSQL="";

	private String mOrderSQL = "order by a.managecom,a.edortype";

	private ListTable tBonusListTable = new ListTable();

	private XmlExport tXmlExport = new XmlExport();

	/**
	 * 1|月度工作量统计 2|保全人员月度工作量明细 3|各机构不同项目对应工作量
	 */
	private String tTypeControl = "displayCT";

	public EdorWorkloadCountBillPrintBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		if (!mOperate.equals("PRINT")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorWorkloadCountBillPrintBL";
			tError.functionName = "submitData";
			tError.errorMessage = "不支持的操作字符串！";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 准备需要打印的数据
		if (!preparePrintData()) {
			return false;
		}

		return true;
	}

	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mGlobalInput == null || mTransferData == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		mStartDate = (String) mTransferData.getValueByName("StartDate");
		if (mStartDate == null || mStartDate.trim().equals("")) {
			mErrors.addOneError(new CError("没有得到足够的信息:统计起期不能为空！"));
			return false;
		}
		mEndDate = (String) mTransferData.getValueByName("EndDate");
		if (mEndDate == null || mEndDate.trim().equals("")) {
			mErrors.addOneError(new CError("没有得到足够的信息:统计止期不能为空！"));
			return false;
		}
		mDateSQL= " and  exists(select 'X' from lpedorapp c where c.edoracceptno = t.edoracceptno and c.confdate >= '"
			+ "?mStartDate?" + "'" 
			+ " and c.confdate <= '" 
			+ "?mEndDate?" + "')";
		
		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		if (mManageCom == null || mManageCom.trim().equals("")) {
			mErrors.addOneError(new CError("没有得到足够的信息:机构不能为空！"));
			return false;
		}
		mEdorType = (String) mTransferData.getValueByName("EdorType");
		if (mEdorType != null && !"".equals(mEdorType)) {
			mEdorTypeSQL = " and  t.edortype = '" + "?mEdorType?" + "'";
		}
		ComGrade = (String) mTransferData.getValueByName("ComGrade");
		if ("1".equals(ComGrade)) {
			tComLength = 4;
		} else if ("1".equals(ComGrade)) {
			tComLength = 6;
		} else {
			tComLength = 6;
		}

		mSaleChnl = (String) mTransferData.getValueByName("SaleChnl");
		// 处理渠道
		//mUserCodeSQL = " and   exists	(select 'X' from lpedorapp c where c.edoracceptno = t.edoracceptno and c.confoperator=t.operator)";
		mRePortFlag = (String) mTransferData.getValueByName("RePortFlag");
		if (mRePortFlag != null && !"".equals(mRePortFlag)) {
			tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档
			SSRS tssrs = new SSRS();
			ExeSQL texesql = new ExeSQL();
			ListTable tContListTable = new ListTable();
			ListTable tPolListTable = new ListTable();
			String strLine[] = null;
			int j;// 计数
			double tSum1 = 0, tSum2 = 0, tSum3 = 0, tSum4 = 0, tSum5 = 0, tSum6 = 0, tSum7 = 0, tSum8 = 0;
			switch (Integer.parseInt(mRePortFlag)) {
			case 1: //
				texttag.add("Title", "月度工作量统计");
				tTypeControl = tTypeControl + mRePortFlag;

				if (mSaleChnl != null && !"".equals(mSaleChnl)) {
					mSaleChlSQL = "and exists (select 'X'" + "from lccont p "
							+ "where p.contno = t.contno "
							+ " and p.salechnl = '" + "?mSaleChnl?" + "')";
				}
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				mRePortFlagSQL = " select mg, (select name from ldcom where comcode = mg) mg1,"
						+ " o1,"
						+ " s1,"
						+ " s2,"
						+ " s3,"
						+ " (s1 + s2 + s3),"
						+ " round((s1 + s2 + s3) / o1, 2),"
						+ " s4,"
						+ " round(s4 / o1, 2)"
						+ " from (select substr(mcom, 1, "
						+ tComLength
						+ ") mg,"
						+ " count(distinct r2) o1,"
						+ " count(sum1) s1,"
						+ " count(sum2) s2,"
						+ " count(sum3) s3,"
						+ " sum(ra) s4 "
						+ " from (select (select comcode from lduser where usercode = "
						+"(select c.confoperator from lpedorapp c where c.edoracceptno = t.edoracceptno)) mcom,"
						+ " (select c.confoperator from lpedorapp c where c.edoracceptno = t.edoracceptno) r2,"
						+ " (select 'X' "
						+ "	from LMEDORSTATRATE l "
						+ "	 where l.edortype = t.edortype "
						+ "	 and l.Stattype = '0') sum1," //变更类
						+ " (select 'X' "
						+ "	from LMEDORSTATRATE l "
						+ "	 where l.edortype = t.edortype "
						+ "	 and l.Stattype = '1') sum2," //给付类
						+ " (select 'X' "
						+ "	from LMEDORSTATRATE l "
						+ "	 where l.edortype = t.edortype "
						+ "	and l.Stattype = '2') sum3," //其他
						+ " (select (CONVERTRATE) "
						+ "	from LMEDORSTATRATE l "
						+ "	 where l.edortype = t.edortype ) ra "
						+ " from lpedoritem t "
						+ " where "
						+ "  t.edorstate in ( '0','b') "
						+ mDateSQL
						+ mSaleChlSQL
						+ " and t.grpcontno = '00000000000000000000' "
						+ " and exists (select 'X' from ldedoruser l where " 
						+" l.usercode=(select c.confoperator from lpedorapp c where c.edoracceptno = t.edoracceptno)" 
						+" and  l.edorpopedom>='B' and exists (select 'X' from lduser r where r.usercode=l.usercode and comcode like concat('"+"?mManageCom?"+"','%') )) "
						+ " ) h"
						+ " group by substr(mcom, 1, "
						+ tComLength
						+ ")) t " + " order by mg ";
				tXmlExport.addDisplayControl(tTypeControl);
				logger.debug("SQL:" + mRePortFlagSQL);
				sqlbv1.sql(mRePortFlagSQL);
				sqlbv1.put("mStartDate", mStartDate);
				sqlbv1.put("mEndDate", mEndDate);
				sqlbv1.put("mSaleChnl", mSaleChnl);
				sqlbv1.put("mManageCom", mManageCom);
				tssrs = texesql.execSQL(sqlbv1);
				if (tssrs == null || tssrs.getMaxRow() <= 0) {
					CError.buildErr(this, mStartDate + "至" + mEndDate
							+ "之间无待打印清单！");
					return false;
				}

				tContListTable.setName("BILL");

				for (int i = 1; i <= tssrs.getMaxRow(); i++) {
					strLine = new String[9];
					for (j = 0; j < 9; j++) {
						strLine[j] = tssrs.GetText(i, j + 2);
					}
					tSum1 += Double.parseDouble(tssrs.GetText(i, 3));
					tSum2 += Double.parseDouble(tssrs.GetText(i, 4));
					tSum3 += Double.parseDouble(tssrs.GetText(i, 5));
					tSum4 += Double.parseDouble(tssrs.GetText(i, 6));
					tSum5 += Double.parseDouble(tssrs.GetText(i, 7));
					tSum6 += Double.parseDouble(tssrs.GetText(i, 8));
					tSum7 += Double.parseDouble(tssrs.GetText(i, 9));
					tSum8 += Double.parseDouble(tssrs.GetText(i, 10));

					tContListTable.add(strLine);
				}
				
				strLine = new String[9];	
				strLine[0]="合   计";
				strLine[1]=String.valueOf((int)tSum1);
				strLine[2]=String.valueOf((int)tSum2);
				strLine[3]=String.valueOf((int)tSum3);
				strLine[4]=String.valueOf((int)tSum4);
				strLine[5]=String.valueOf((int)tSum5);
				strLine[6]=String.valueOf(BqNameFun.getRound(tSum5/tSum1));
				strLine[7]=String.valueOf(BqNameFun.getRound(tSum7));
				strLine[8]=String.valueOf(BqNameFun.getRound(tSum7/tSum1));
				tContListTable.add(strLine);
				tXmlExport.addListTable(tContListTable, strLine);

				break;
			case 2: //
				texttag.add("Title", "保全人员月度工作量明细");
				tTypeControl = tTypeControl + mRePortFlag;
				tComLength = 4;
				if (mSaleChnl != null && !"".equals(mSaleChnl)) {
					mSaleChlSQL = "and exists (select 'X'" + "from lccont p "
							+ "where p.contno = t.contno "
							+ " and p.salechnl = '" + "?mSaleChnl?" + "')";
				}
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				mRePortFlagSQL = "select mg, (select name from ldcom where comcode = mg) mg1,"
						+ " r2,"
						+ " (select username from lduser where usercode = r2),"
						+ " s1,"
						+ " s2,"
						+ " s3,"
						+ " (s1 + s2 + s3),"
						+ " (case when s4 is not null then s4  else 0 end),"
						+ " (select (select name from ldcom where comcode = d.comcode)"
						+ "		from lduser d "
						+ "	 where usercode = r2) "
						+ " from (select substr(mcom, 1, "
						+ tComLength
						+ ") mg,"
						+ " r2,"
						+ " count(sum1) s1,"
						+ " count(sum2) s2,"
						+ " count(sum3) s3,"
						+ " sum(ra) s4 "
						+ " from (select (select comcode from lduser where usercode = (select c.confoperator from lpedorapp c where c.edoracceptno = t.edoracceptno)) mcom,"
						+ "(select c.confoperator from lpedorapp c where c.edoracceptno = t.edoracceptno) r2,"
						+ "(select 'X' "
						+ "   from LMEDORSTATRATE l "
						+ "  where l.edortype = t.edortype "
						+ " and l.Stattype = '0') sum1,"
						+ " (select 'X' "
						+ "	from LMEDORSTATRATE l "
						+ " where l.edortype = t.edortype "
						+ "	 and l.Stattype = '1') sum2,"
						+ " (select 'X' "
						+ "	from LMEDORSTATRATE l "
						+ " where l.edortype = t.edortype   "
						+ "	 and l.Stattype = '2'  "
						+ "   ) sum3,"
						+ " (select (CONVERTRATE) "
						+ "	from LMEDORSTATRATE l "
						+ " where l.edortype = t.edortype    ) ra "
						+ " from lpedoritem t "
						+ "where "
						+ " t.edorstate in ( '0','b') "
						+ mDateSQL
						+ mSaleChlSQL
						+ "and t.grpcontno = '00000000000000000000' "
						+ "and exists (select 'X' from ldedoruser  l where l.usercode=(select c.confoperator from "
						+" lpedorapp c where c.edoracceptno = t.edoracceptno) and  l.edorpopedom>='B' and exists (select 'X' from lduser r where r.usercode=l.usercode and comcode like concat('"+"?mManageCom?"+"','%' )))"
						+ " group by substr(mcom, 1, "
						+ tComLength
						+ "), r2) t"
						+ "order by mg ";
				tXmlExport.addDisplayControl(tTypeControl);
				logger.debug("SQL:" + mRePortFlagSQL);
				sqlbv2.sql(mRePortFlagSQL);
				sqlbv2.put("mStartDate", mStartDate);
				sqlbv2.put("mEndDate", mEndDate);
				sqlbv2.put("mSaleChnl", mSaleChnl);
				sqlbv2.put("mManageCom", mManageCom);
				tssrs = texesql.execSQL(sqlbv2);
				if (tssrs == null || tssrs.getMaxRow() <= 0) {
					CError.buildErr(this, mStartDate + "至" + mEndDate
							+ "之间无待打印清单！");
					return false;
				}

				tContListTable.setName("BILL");

				for (int i = 1; i <= tssrs.getMaxRow(); i++) {
					strLine = new String[9];
					for (j = 0; j < 9; j++) {
						strLine[j] = tssrs.GetText(i, j + 2);
					}
					tContListTable.add(strLine);
				}
				tXmlExport.addListTable(tContListTable, strLine);

				break;
			case 3: //
				texttag.add("Title", "各机构不同项目对应工作量");
				tTypeControl = tTypeControl + mRePortFlag;

				if (mSaleChnl != null && !"".equals(mSaleChnl)) {
					mSaleChlSQL = "and exists (select 'X'" + "from lccont p "
							+ "where p.contno = t.contno "
							+ " and p.salechnl = '" + "?mSaleChnl?" + "')";
				}
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				mRePortFlagSQL = "select mg, (select name from ldcom where comcode = mg) mg1, "
						+ " (select codename "
						+ "		from ldcode"
						+ "	 where codetype = 'edortype'"
						+ "		 and code = edortype), "
						+ " (case when s0 is not null then s0  else 0 end),"
						+ " (case when s1 is not null then s1  else 0 end),"
						+ " (case when s4 is not null then s4  else 0 end) "
						+ " from ("
						+ " select substr(mcom, 1, 6) mg, edortype,count(sum0) s0, sum(sum1) s1, sum(ra) s4 "
						+ "	from (select (select comcode from lduser where usercode=(select c.confoperator from lpedorapp c where c.edoracceptno = t.edoracceptno) ) mcom, "
						+ "  edortype,  "
						+ " t.edoracceptno sum0,   "
						//+ " (select sum(getmoney) from ljagetendorse where endorsementno=t.edorno  ) sum1, "
						// modify by jiaqiangli 2009-09-30 通过总表来统计保全金额 
						// 这样既可统计到wt，ln的实际金额 又可统计到DB和LG这两个保全项的涉及金额
						+ " (case when exists(select 1 from ljaget,lpedorapp where ljaget.otherno=lpedorapp.edorconfno and lpedorapp.edoracceptno=t.edoracceptno) then (select (case when sum(sumgetmoney) is not null then sum(sumgetmoney)  else 0 end) from ljaget,lpedorapp where ljaget.otherno=lpedorapp.edorconfno and lpedorapp.edoracceptno=t.edoracceptno) when exists(select 1 from ljapay where incomeno=t.edoracceptno) then (select (case when sum(sumactupaymoney) is not null then sum(sumactupaymoney)  else 0 end) from ljapay where incomeno=t.edoracceptno) else 0 end) sum1, "
						+ " (select (case when (CONVERTRATE) is not null then (CONVERTRATE)  else 0 end) "
						+ "		from LMEDORSTATRATE l "
						+ "	 where l.edortype = t.edortype ) ra "
						+ " from lpedoritem t "
						+ " where "
						+ " t.edorstate in ( '0','b') "
						+  mDateSQL
						+ mEdorTypeSQL
						+ mSaleChlSQL
						+ "and t.grpcontno = '00000000000000000000' "
						//+ mUserCodeSQL
						+ "and exists (select 'X' from ldedoruser l where "
						+ "l.usercode=(select c.confoperator from lpedorapp c where c.edoracceptno = t.edoracceptno) and  l.edorpopedom>='B' and exists (select 'X' from lduser r where r.usercode=l.usercode and comcode like concat('"+"?mManageCom?"+"','%') ))) h"
						+ " group by substr(mcom, 1, 6), edortype) t order by mg";
				tXmlExport.addDisplayControl(tTypeControl);
				logger.debug("SQL:" + mRePortFlagSQL);
				sqlbv3.sql(mRePortFlagSQL);
				sqlbv3.put("mStartDate", mStartDate);
				sqlbv3.put("mEndDate", mEndDate);
				sqlbv3.put("mEdorType", mEdorType);
				sqlbv3.put("mSaleChnl", mSaleChnl);
				sqlbv3.put("mManageCom", mManageCom);
				tssrs = texesql.execSQL(sqlbv3);
				if (tssrs == null || tssrs.getMaxRow() <= 0) {
					CError.buildErr(this, mStartDate + "至" + mEndDate
							+ "之间无待打印清单！");
					return false;
				}
				tContListTable.setName("BILL");

				for (int i = 1; i <= tssrs.getMaxRow(); i++) {
					strLine = new String[5];
					for (j = 0; j < 5; j++) {
						strLine[j] = tssrs.GetText(i, j + 2);
					}
					tSum1 += Double.parseDouble(tssrs.GetText(i, 4));
					tSum2 += Double.parseDouble(tssrs.GetText(i, 5));
					tSum3 += Double.parseDouble(tssrs.GetText(i, 6));	
					tContListTable.add(strLine);
				}
				strLine = new String[9];	
				strLine[0]="合   计";
				strLine[1]="";
				strLine[2]=String.valueOf((int)tSum1);
				strLine[3]=String.valueOf(BqNameFun.getRound(tSum2));
				strLine[4]=String.valueOf(BqNameFun.getRound(tSum3));
				tContListTable.add(strLine);								
				tXmlExport.addListTable(tContListTable, strLine);

				break;
			}

		}
		return true;
	}

	private boolean preparePrintData() {

		mManageCom = BqNameFun.getCBranch(mManageCom);
		mStartDate = BqNameFun.getChDate(mStartDate);
		mEndDate = BqNameFun.getChDate(mEndDate);
		// 模版自上而下的元素
		texttag.add("ManageCom", mManageCom);
		texttag.add("StartDate", mStartDate);
		texttag.add("EndDate", mEndDate);

		texttag
				.add("PrintDate", BqNameFun
						.getChDate((PubFun.getCurrentDate())));

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}

		// tXmlExport.outputDocumentToFile("c:\\XML\\", "EdorAllCTBill.vts");
		mResult.clear();
		mResult.addElement(tXmlExport);

		return true;

	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
