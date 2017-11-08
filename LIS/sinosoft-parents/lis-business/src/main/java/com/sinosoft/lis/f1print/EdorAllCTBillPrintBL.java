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
 * @CreateDate：2009-04-11 清单名称：保全退保统计表
 */

public class EdorAllCTBillPrintBL implements BqBill {
private static Logger logger = Logger.getLogger(EdorAllCTBillPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();

	private VData mInputData = new VData();

	private String mOperate = "";

	private GlobalInput mGlobalInput = new GlobalInput();

	public static final String VTS_NAME = "EdorAllCTBill.vts";

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

	private String mDateSQL = "";

	private String mRePortFlag = "";

	private String mRePortFlagSQL = "";

	private String mRePortFlagSQLMG = "";

	private int tComLength = 0;

	private String mOrderSQL = "order by a.managecom,a.edortype";

	private ListTable tBonusListTable = new ListTable();

	private XmlExport tXmlExport = new XmlExport();

	/** 其他机构的编码，指此次统计指标中为零的机构 */
	private String tOtherManageCom = "";

	/**
	 * 1|退保概况 2|险种退保概况 3|协议退保原因 4|协议退保差值比较 5|协议退保险种差值比较 6|撤件率 7|协议退保差值明细
	 */
	private String tTypeControl = "displayCT";

	public EdorAllCTBillPrintBL() {

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
			tError.moduleName = "EdorAllCTBillPrintBL";
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
		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		if (mManageCom == null || mManageCom.trim().equals("")) {
			mErrors.addOneError(new CError("没有得到足够的信息:机构不能为空！"));
			return false;
		}
		if (mManageCom.length() == 2) {
			tComLength = 4;
		} else if (mManageCom.length() == 4) {
			tComLength = 6;
		} else {
			tComLength = 6;
		}

		mSaleChnl = (String) mTransferData.getValueByName("SaleChnl");

		mDateSQL = " and  exists(select 'X' from lpedorapp c where c.edoracceptno = a.edoracceptno and c.confdate >= '"
				+ "?mStartDate?" + "'" + " and c.confdate <= '" + "?mEndDate?" + "')";
		// 处理渠道

		// mUserCode = (String) mTransferData.getValueByName("UserCode");
		// if (mUserCode != null && !"".equals(mUserCode)) {
		// mUserCodeSQL = " and exists (select 'X'" + " from lpedoritem p "
		// + " where p.contno = a.otherno "
		// + " and p.edoracceptno=a.edoracceptno "
		// + " and p.operator = '" + mUserCode + "')";
		// }
		mEdorType = (String) mTransferData.getValueByName("EdorType");

		// 1|退保概况 2|险种退保概况 3|协议退保原因 4|协议退保原因 5|协议退保险种差值比较 6|撤件率 7|协议退保差值明细
		mRePortFlag = (String) mTransferData.getValueByName("NewRePortFlag");
		if (mRePortFlag != null && !"".equals(mRePortFlag)) {
			tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档
			SSRS tssrs = new SSRS();
			ExeSQL texesql = new ExeSQL();
			ListTable tContListTable = new ListTable();
			ListTable tPolListTable = new ListTable();
			String strLine[] = null;
			String sql = "";
			SSRS rssrs = new SSRS();
			int j;// 计数
			double tSum1 = 0, tSum2 = 0, tSum3 = 0, tSum4 = 0, tSum5 = 0, tSum6 = 0, tSum7 = 0, tSum8 = 0,tSum9 = 0,tSum10 = 0;
			switch (Integer.parseInt(mRePortFlag)) {
			case 1: //
				texttag.add("Title", "退保概况");
				tTypeControl = tTypeControl + mRePortFlag;

				if (mEdorType != null && !"".equals(mEdorType)) {
					mEdorTypeSQL = " and a.feeoperationtype ='" + "?mEdorType?"
							+ "'";
				} else {
					mEdorTypeSQL = " and a.feeoperationtype in ('CT', 'WT', 'XT', 'PT', 'XS')";
				}

				if (mSaleChnl != null && !"".equals(mSaleChnl)) {
					mSaleChlSQL = " and exists (select 'X'" + " from lccont p "
							+ "where p.contno = a.contno "
							+ " and p.salechnl = '" + "?mSaleChnl?" + "')";
				}
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				// 按机构进行统计
				sql = "select distinct comcode from ldcom "
						+ "where  char_length(comcode)=" + tComLength
						+ " and comcode like concat('" + "?mManageCom?" + "','%') and comcode not in('8699') order by comcode";
				sqlbv1.sql(sql);
				sqlbv1.put("mManageCom", mManageCom);
				rssrs = texesql.execSQL(sqlbv1);
				for (int k = 1; k <= rssrs.getMaxRow(); k++) {
					SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
					String tManageCom = rssrs.GetText(k, 1);
					mRePortFlagSQL = "  select com,edortype, count(edorno), sum(money) "
							+ " from (select substr(a.managecom, 1, "
							+ tComLength
							+ ") com, "
							+ "	endorsementno edorno,a.feeoperationtype edortype, "
							+ "	sum((case a.getflag when '1' then a.getmoney when '0' then -a.getmoney else '' end)) money  "
							+ "	from ljagetendorse a "
							+ " where a.managecom like '86%' "
							+ mEdorTypeSQL
							+ "	 "
							+ "	 and a.makedate >= '"+ "?mStartDate?" + "' "
							+ "   and a.makedate <= '"
							+ "?mEndDate?"
							+ "' "
							+ " and a.managecom like concat('"+ "?tManageCom?" + "','%')"
							+ mSaleChlSQL
							+ "   and a.grppolno = '00000000000000000000'"
							+ " group by substr(a.managecom, 1, "
							+ tComLength
							+ "), a.endorsementno,a.feeoperationtype)  "
							+ " group by com , edortype  " + " order by com   ";
					logger.debug("SQL:" + mRePortFlagSQL);
					sqlbv2.sql(mRePortFlagSQL);
					sqlbv2.put("mEdorType", mEdorType);
					sqlbv2.put("mStartDate", mStartDate);
					sqlbv2.put("mEndDate", mEndDate);
					sqlbv2.put("tManageCom", tManageCom);
					sqlbv2.put("mSaleChnl", mSaleChnl);
					tssrs = texesql.execSQL(sqlbv2);
					if (tssrs == null || tssrs.getMaxRow() <= 0) {
						strLine = new String[4];
						strLine[0] = BqNameFun.getComName(rssrs.GetText(k, 1));
						strLine[1] = mEdorType;
						for (j = 2; j < 4; j++) {

							strLine[j] = "0";
						}
						tContListTable.add(strLine);
					} else {
						for (int i = 1; i <= tssrs.getMaxRow(); i++) {
							strLine = new String[4];
							strLine[0] = BqNameFun.getComName(tssrs.GetText(i,
									1));
							for (j = 1; j < 4; j++) {

								strLine[j] = tssrs.GetText(i, j + 1);
							}
							tSum1 += Double.parseDouble(tssrs.GetText(i, 3));
							tSum2 += Double.parseDouble(tssrs.GetText(i, 4));
							tContListTable.add(strLine);
						}
					}
				}

				// "select substr(a.managecom,0,"+tComLength+") mg,"
				// + " a.edortype,"
				// + " count(distinct edorno) num,"
				// + " nvl(sum((select sum(sumgetmoney)"
				// + " from ljaget c"
				// + " where c.otherno ="
				// + " (select d.edorconfno"
				// + " from lpedorapp d"
				// + " where d.edoracceptno = a.edoracceptno)"
				// + " and c.othernotype = '10')),0) money"
				// + " from lpedoritem a"
				// + " where " +
				// " 1=1 "+mEdorTypeSQL
				// + " and a.edorstate = '0'"
				// + " and a.grpcontno='00000000000000000000'"
				// + mDateSQL
				// + " and a.managecom like '"
				// + mManageCom
				// + "%'"+mSaleChlSQL
				// + " group by substr(a.managecom,0,"+tComLength+"),
				// a.edortype"
				// + " order by mg, num desc";

				tXmlExport.addDisplayControl(tTypeControl);

				tContListTable.setName("BILL");

				strLine = new String[4];
				strLine[0] = "合   计";
				strLine[1] = "     ";
				strLine[2] = String.valueOf((int) tSum1);
				strLine[3] = String.valueOf(BqNameFun.getFormat(tSum2));
				tContListTable.add(strLine);
				tXmlExport.addListTable(tContListTable, strLine);

				break;
			case 2: //
				texttag.add("Title", "险种退保概况");
				tTypeControl = tTypeControl + mRePortFlag;
				if (mEdorType != null && !"".equals(mEdorType)) {
					mEdorTypeSQL = " 1=1 and a.feeoperationtype ='" + "?mEdorType?"
							+ "'";
				} else {
					mEdorTypeSQL = " 1=1 and a.feeoperationtype in ('CT', 'WT', 'XT')";
				}
				if (mSaleChnl != null && !"".equals(mSaleChnl)) {
					mSaleChlSQL = " and exists (select 'X'" + "from lccont p "
							+ "where p.contno = a.contno "
							+ " and p.salechnl = '" + "?mSaleChnl?" + "')";
				}
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				String mRePortFlagSumSQL = "select "
						+ " (case when sum((case a.getflag when '1' then a.getmoney when '0' then -a.getmoney else '' end)) is not null then sum((case a.getflag when '1' then a.getmoney when '0' then -a.getmoney else '' end))  else 0 end) money"
						+ " from ljagetendorse a" + " where " + mEdorTypeSQL
						+ " and a.grpcontno='00000000000000000000' "
						+ " and a.makedate >= '" + "?mStartDate?" + "'"
						+ " and a.makedate <= '" + "?mEndDate?" + "'"
						+ " and a.managecom like concat('" + "?mManageCom?" + "','%')"
						+ mSaleChlSQL + "";
				sqlbv3.sql(mRePortFlagSumSQL);
				sqlbv3.put("mEdorType", mEdorType);
				sqlbv3.put("mStartDate", mStartDate);
				sqlbv3.put("mEndDate", mEndDate);
				sqlbv3.put("mManageCom", mManageCom);
				sqlbv3.put("mSaleChnl", mSaleChnl);
				String tSumMonye = texesql.getOneValue(sqlbv3);
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				mRePortFlagSQL = "select risk,feeoperationtype,num,money,(round(money/"
						+ tSumMonye
						+ " * 100, 2)) rate from (select (select riskname from lmriskapp where riskcode=a.riskcode) risk ,"
						+ " a.feeoperationtype,"
						+ " count(distinct a.endorsementno) num,"
						+ " (case when sum((case a.getflag when '1' then a.getmoney when '0' then -a.getmoney else '' end)) is not null then sum((case a.getflag when '1' then a.getmoney when '0' then -a.getmoney else '' end))  else 0 end) money"
						+ " from ljagetendorse a"
						+ " where "
						+ mEdorTypeSQL
						+ " and a.grpcontno='00000000000000000000'"
						+ " and a.makedate >= '"
						+ "?mStartDate?"
						+ "'"
						+ " and a.makedate <= '"
						+ "?mEndDate?"
						+ "'"
						+ " and a.managecom like concat('"+ "?mManageCom?" + "','%')"
						+ mSaleChlSQL
						+ " group by riskcode,a.feeoperationtype) order by rate desc";
				tXmlExport.addDisplayControl(tTypeControl);
				sqlbv4.sql(mRePortFlagSQL);
				sqlbv4.put("mEdorType", mEdorType);
				sqlbv4.put("mStartDate", mStartDate);
				sqlbv4.put("mEndDate", mEndDate);
				sqlbv4.put("mManageCom", mManageCom);
				sqlbv4.put("mSaleChnl", mSaleChnl);
				logger.debug("SQL:" + mRePortFlagSQL);
				tssrs = texesql.execSQL(sqlbv4);
				if (tssrs == null || tssrs.getMaxRow() <= 0) {
					CError.buildErr(this, mStartDate + "至" + mEndDate
							+ "之间无待打印清单！");
					return false;
				}
				tContListTable.setName("BILL");

				for (int i = 1; i <= tssrs.getMaxRow(); i++) {
					tSum1 += Double.parseDouble(tssrs.GetText(i, 3));
					tSum2 += Double.parseDouble(tssrs.GetText(i, 4));
				}

				for (int i = 1; i <= tssrs.getMaxRow(); i++) {
					strLine = new String[5];
					for (j = 0; j < 5; j++) {
						strLine[j] = tssrs.GetText(i, j + 1);
					}
					strLine[4] = strLine[4] + "%";
					tContListTable.add(strLine);
				}
				strLine = new String[5];
				strLine[0] = "合   计";
				strLine[1] = "";

				strLine[2] = String.valueOf((int) tSum1);
				strLine[3] = String.valueOf(BqNameFun.getFormat(tSum2));
				strLine[4] = "";
				tContListTable.add(strLine);
				tXmlExport.addListTable(tContListTable, strLine);

				break;
			case 3: //
				texttag.add("Title", "协议退保原因");
				tTypeControl = tTypeControl + mRePortFlag;

				if (mSaleChnl != null && !"".equals(mSaleChnl)) {
					mSaleChlSQL = " and exists (select 'X'" + "from lccont p "
							+ "where p.contno = a.contno "
							+ " and p.salechnl = '" + "?mSaleChnl?" + "')";
				}
				mEdorTypeSQL = "";
				tXmlExport.addDisplayControl(tTypeControl);
				// 按机构进行统计
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				sql = "select distinct comcode from ldcom "
						+ "where  char_length(comcode)=" + tComLength
						+ " and comcode like concat('" + "?mManageCom?" + "','%')"
						+ " and comcode not in('8699') order by comcode";
				sqlbv5.sql(sql);
				sqlbv5.put("mManageCom", mManageCom);
				rssrs = texesql.execSQL(sqlbv5);
				for (int k = 1; k <= rssrs.getMaxRow(); k++) {

					String tManageCom = rssrs.GetText(k, 1);
					SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
					mRePortFlagSQL = " select substr(mg,1,"
							+ tComLength
							+ ") mg1,(case when count((case re when '01' then num when '1' then num else '' end)) is not null then count((case re when '01' then num when '1' then num else '' end))  else 0 end) r1,"
							//modify by jiaqiangli 2009-11-10 select * from ldcode where codetype = lower('XSurrORDeReason');
							//投诉公司转办 与投诉业务员错位
							//+ " nvl(count(decode(re,'02',num,'2',num,'')),0) r2,"
							+ " (case when count((case re when '03' then num when '3' then num else '' end)) is not null then count((case re when '03' then num when '3' then num else '' end))  else 0 end) r3,"
							+ " (case when count((case re when '02' then num when '2' then num else '' end)) is not null then count((case re when '02' then num when '2' then num else '' end))  else 0 end) r2,"
							+ " (case when count((case re when '04' then num when '4' then num else '' end)) is not null then count((case re when '04' then num when '4' then num else '' end))  else 0 end) r4,"
							+ " (case when count((case re when '05' then num when '5' then num else '' end)) is not null then count((case re when '05' then num when '5' then num else '' end))  else 0 end) r5,"
							+ " (case when sum((case re when '01' then money when '1' then money else '' end)) is not null then sum((case re when '01' then money when '1' then money else '' end))  else 0 end) mr1,"
							//+ " nvl(sum(decode(re,'02',money,'2',money,'')),0) mr2,"
							+ " (case when sum((case re when '03' then money when '3' then money else '' end)) is not null then sum((case re when '03' then money when '3' then money else '' end))  else 0 end) mr3,"
							+ " (case when sum((case re when '02' then money when '2' then money else '' end)) is not null then sum((case re when '02' then money when '2' then money else '' end))  else 0 end) mr2,"
							+ " (case when sum((case re when '04' then money when '4' then money else '' end)) is not null then sum((case re when '04' then money when '4' then money else '' end))  else 0 end) mr4,"
							+ " (case when sum((case re when '05' then money when '5' then money else '' end)) is not null then sum((case re when '05' then money when '5' then money else '' end))  else 0 end) mr5"
							+ " from (select a.managecom mg, "
							+ " a.endorsementno num, "
							+ " (select max(edorreasoncode) from lpedoritem where edorno=a.endorsementno ) re,"
							+ "  sum((case a.getflag when '1' then a.getmoney when '0' then -a.getmoney else '' end)) money "
							+ " from ljagetendorse a"
							+ " where a.feeoperationtype = 'XT'"
							+ " and a.grpcontno = '00000000000000000000'"
							+ " and a.makedate >= '" + "?mStartDate?" + "'"
							+ " and a.makedate <= '" + "?mEndDate?" + "'"
							+ " and a.managecom like concat('" + "?tManageCom?" + "','%')"
							+ mSaleChlSQL
							+ " group by a.managecom, a.endorsementno" + " )"
							+ " group by substr(mg,1," + tComLength + ")"
							+ " order by mg1";

					logger.debug("SQL:" + mRePortFlagSQL);
					sqlbv6.sql(mRePortFlagSQL);
					sqlbv6.put("mSaleChnl", mSaleChnl);
					sqlbv6.put("mStartDate", mStartDate);
					sqlbv6.put("mEndDate", mEndDate);
					sqlbv6.put("tManageCom", tManageCom);
					
					tssrs = texesql.execSQL(sqlbv6);
					if (tssrs == null || tssrs.getMaxRow() <= 0) {
						strLine = new String[11];
						strLine[0] = BqNameFun.getComName(rssrs.GetText(k, 1));
						for (j = 1; j < 11; j++) {
							strLine[j] = "0";
						}
						tContListTable.add(strLine);
					} else {
						for (int i = 1; i <= tssrs.getMaxRow(); i++) {
							strLine = new String[11];
							strLine[0] = BqNameFun.getComName(tssrs.GetText(i,
									1));
							for (j = 1; j < 11; j++) {
								strLine[j] = tssrs.GetText(i, j + 1);
							}
							tSum1 += Double.parseDouble(tssrs.GetText(i, 2));
							tSum2 += Double.parseDouble(tssrs.GetText(i, 3));
							tSum3 += Double.parseDouble(tssrs.GetText(i, 4));
							tSum4 += Double.parseDouble(tssrs.GetText(i, 5));
							tSum5 += Double.parseDouble(tssrs.GetText(i, 6));
							tSum6 += Double.parseDouble(tssrs.GetText(i, 7));
							tSum7 += Double.parseDouble(tssrs.GetText(i, 8));
							tSum8 += Double.parseDouble(tssrs.GetText(i, 9));
							tSum9 += Double.parseDouble(tssrs.GetText(i, 10));
							tSum10 += Double.parseDouble(tssrs.GetText(i, 11));
							tContListTable.add(strLine);
						}
					}
				}
				tContListTable.setName("BILL");

				strLine = new String[11];
				strLine[0] = "合   计";
				strLine[1] = String.valueOf((int) tSum1);
				strLine[2] = String.valueOf((int) tSum2);
				strLine[3] = String.valueOf((int) tSum3);
				strLine[4] = String.valueOf((int) tSum4);
				strLine[5] = String.valueOf((int) tSum5);
				strLine[6] = String.valueOf(BqNameFun.getFormat(tSum6));
				strLine[7] = String.valueOf(BqNameFun.getFormat(tSum7));
				strLine[8] = String.valueOf(BqNameFun.getFormat(tSum8));
				strLine[9] = String.valueOf(BqNameFun.getFormat(tSum9));
				strLine[10] = String.valueOf(BqNameFun.getFormat(tSum10));
				tContListTable.add(strLine);
				tXmlExport.addListTable(tContListTable, strLine);

				break;
			case 4:// 协议退保差值比较
				texttag.add("Title", "协议退保差值比较");
				tTypeControl = tTypeControl + mRePortFlag;

				if (mSaleChnl != null && !"".equals(mSaleChnl)) {
					mSaleChlSQL = " and exists (select 'X'" + "from lccont p "
							+ "where p.contno = a.contno "
							+ " and p.salechnl = '" + "?mSaleChnl?" + "')";
				}
				SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
				mRePortFlagSQL =
				// " --按险种"
				" select riskcode ,"
						// + " (select (select riskname from lmriskapp where
						// riskcode = d.riskcode)"
						// + " from lcpol d"
						// + " where d.polno = a.polno),"
						+ " (case when sum((case a.getflag when '1' then a.getmoney when '0' then -a.getmoney else '' end)) is not null then sum((case a.getflag when '1' then a.getmoney when '0' then -a.getmoney else '' end))  else 0 end),"
						+ " (case when sum(to_number((case a.getflag when '1' then a.serialno when '0' then -a.serialno else '' end))) is not null then sum(to_number((case a.getflag when '1' then a.serialno when '0' then -a.serialno else '' end)))  else 0 end),"
						+ " (case when (sum((case a.getflag when '1' then a.getmoney when '0' then -a.getmoney else '' end)) - sum(to_number((case a.getflag when '1' then a.serialno when '0' then -a.serialno else '' end)))) is not null then (sum((case a.getflag when '1' then a.getmoney when '0' then -a.getmoney else '' end)) - sum(to_number((case a.getflag when '1' then a.serialno when '0' then -a.serialno else '' end))))  else 0 end)"
						+ " from ljagetendorse a"
						+ " where a.feeoperationtype = 'XT'"
						+ " and a.grpcontno = '00000000000000000000'"
						+ " and a.makedate >= '" + "?mStartDate?" + "'"
						+ " and a.makedate <= '" + "?mEndDate?" + "'"
						+ " and a.managecom like concat('" + "?mManageCom?" + "','%')"
						+ mSaleChlSQL + " "
						+ " group by riskcode  order by riskcode";
				tXmlExport.addDisplayControl(tTypeControl);
				logger.debug("SQL:" + mRePortFlagSQL);
				sqlbv7.sql(mRePortFlagSQL);
				sqlbv7.put("mStartDate", mStartDate);
				sqlbv7.put("mEndDate", mEndDate);
				sqlbv7.put("mManageCom", mManageCom);
				sqlbv7.put("mSaleChnl", mSaleChnl);
				
				tssrs = texesql.execSQL(sqlbv7);
				// if (tssrs == null || tssrs.getMaxRow() <= 0) {
				// CError.buildErr(this, mStartDate + "至" + mEndDate +
				// "之间无待打印清单！");
				// return false;
				// }
				tContListTable.setName("BILL");

				for (int i = 1; i <= tssrs.getMaxRow(); i++) {
					strLine = new String[4];
					strLine[0] = BqNameFun
							.getRiskShortName(tssrs.GetText(i, 1));
					for (j = 1; j < 4; j++) {
						strLine[j] = tssrs.GetText(i, j + 1);
					}
					tSum1 += Double.parseDouble(tssrs.GetText(i, 2));
					tSum2 += Double.parseDouble(tssrs.GetText(i, 3));
					tSum3 += Double.parseDouble(tssrs.GetText(i, 4));
					tContListTable.add(strLine);
				}
				strLine = new String[4];
				strLine[0] = "合   计";
				strLine[1] = String.valueOf(BqNameFun.getFormat(tSum1));
				strLine[2] = String.valueOf(BqNameFun.getFormat(tSum2));
				strLine[3] = String.valueOf(BqNameFun.getFormat(tSum3));
				tContListTable.add(strLine);
				tXmlExport.addListTable(tContListTable, strLine);
				
				
				//add by jiaqiangli 2009-10-10
				//按险种统计完成之后下面按机构统计之前需要重新初始化计数器
				tSum1 = 0;
				tSum2 = 0;
				tSum3 = 0;
				SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
				// 按机构进行统计
				sql = "select distinct comcode from ldcom "
						+ "where  char_length(comcode)=" + tComLength
						+ " and comcode like concat('" + "?mManageCom?" + "','%')"
						+ " and comcode not in('8699') order by comcode";
				sqlbv8.sql(sql);
				sqlbv8.put("mManageCom", mManageCom);
				rssrs = texesql.execSQL(sqlbv8);
				for (int k = 1; k <= rssrs.getMaxRow(); k++) {
					String tManageCom = rssrs.GetText(k, 1);
					SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
					// " --按机构"
					mRePortFlagSQLMG = " select substr(a.managecom,1,"
							+ tComLength
							+ ") mg,"
							+ " (case when sum((case a.getflag when '1' then a.getmoney when '0' then -a.getmoney else '' end)) is not null then sum((case a.getflag when '1' then a.getmoney when '0' then -a.getmoney else '' end))  else 0 end),"
							+ " (case when sum(to_number((case a.getflag when '1' then a.serialno when '0' then -a.serialno else '' end))) is not null then sum(to_number((case a.getflag when '1' then a.serialno when '0' then -a.serialno else '' end)))  else 0 end),"
							+ " (case when (sum((case a.getflag when '1' then a.getmoney when '0' then -a.getmoney else '' end)) - sum(to_number((case a.getflag when '1' then a.serialno when '0' then -a.serialno else '' end)))) is not null then (sum((case a.getflag when '1' then a.getmoney when '0' then -a.getmoney else '' end)) - sum(to_number((case a.getflag when '1' then a.serialno when '0' then -a.serialno else '' end))))  else 0 end)"
							+ " from ljagetendorse a"
							+ " where a.feeoperationtype = 'XT'"
							+ " and a.grpcontno = '00000000000000000000'"
							+ " and a.makedate >= '" + "?mStartDate?" + "'"
							+ " and a.makedate <= '" +"?mEndDate?"+ "'"
							+ " and a.ManageCom like concat('" + "?tManageCom?" + "','%')"
							+ mSaleChlSQL + " group by substr(a.managecom,1,"
							+ tComLength + ") order by mg";
					logger.debug("SQLMG:" + mRePortFlagSQLMG);
					sqlbv9.sql(mRePortFlagSQLMG);
					sqlbv9.put("mSaleChnl", mSaleChnl);
					sqlbv9.put("mStartDate", mStartDate);
					sqlbv9.put("mEndDate", mEndDate);
					sqlbv9.put("tManageCom", tManageCom);
					tssrs = texesql.execSQL(sqlbv9);
					if (tssrs == null || tssrs.getMaxRow() <= 0) {
						strLine = new String[4];
						strLine[0] = BqNameFun.getComName(rssrs.GetText(k, 1));
						for (j = 1; j < 4; j++) {
							strLine[j] = "0";
						}
						tPolListTable.add(strLine);
					} else {
						//modify by jiaqiangli 2009-10-10
						//tSum1 = 0;
						//tSum2 = 0;
						//tSum3 = 0;
						for (int i = 1; i <= tssrs.getMaxRow(); i++) {
							strLine = new String[4];
							strLine[0] = BqNameFun.getComName(tssrs.GetText(i,
									1));
							for (j = 1; j < 4; j++) {
								strLine[j] = tssrs.GetText(i, j + 1);
							}
							tSum1 += Double.parseDouble(tssrs.GetText(i, 2));
							tSum2 += Double.parseDouble(tssrs.GetText(i, 3));
							tSum3 += Double.parseDouble(tssrs.GetText(i, 4));
							tPolListTable.add(strLine);
						}
					}
				}

				tPolListTable.setName("BILL1");

				strLine = new String[4];
				strLine[0] = "合   计";
				strLine[1] = String.valueOf(BqNameFun.getFormat(tSum1));
				strLine[2] = String.valueOf(BqNameFun.getFormat(tSum2));
				strLine[3] = String.valueOf(BqNameFun.getFormat(tSum3));
				tPolListTable.add(strLine);
				tXmlExport.addListTable(tPolListTable, strLine);
				break;
			case 5://
				texttag.add("Title", "协议退保险种差值比较");
				tTypeControl = tTypeControl + mRePortFlag;
				if (mSaleChnl != null && !"".equals(mSaleChnl)) {
					mSaleChlSQL = " and exists (select 'X'" + "from lccont p "
							+ "where p.contno = a.contno "
							+ " and p.salechnl = '" + "?mSaleChnl?" + "')";
				}
				tXmlExport.addDisplayControl(tTypeControl);
				SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
				// 按机构进行统计
				sql = "select distinct comcode from ldcom "
						+ "where  char_length(comcode)=" + tComLength
						+ " and comcode like concat('" + "?mManageCom?"+ "','%')  "
						+ " and comcode not in('8699') order by comcode";
				sqlbv10.sql(sql);
				sqlbv10.put("mManageCom", mManageCom);
				rssrs = texesql.execSQL(sqlbv10);
				for (int k = 1; k <= rssrs.getMaxRow(); k++) {
					SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
					String tManageCom = rssrs.GetText(k, 1);
					mRePortFlagSQL = " select substr(a.managecom,1,"
							+ tComLength
							+ ") mg ,"
							+ "  riskcode,"
							+ " (case when sum((case a.getflag when '1' then a.getmoney when '0' then -a.getmoney else '' end)) is not null then sum((case a.getflag when '1' then a.getmoney when '0' then -a.getmoney else '' end))  else 0 end),"
							+ " (case when sum(to_number((case a.getflag when '1' then a.serialno when '0' then -a.serialno else '' end))) is not null then sum(to_number((case a.getflag when '1' then a.serialno when '0' then -a.serialno else '' end)))  else 0 end),"
							+ " (case when (sum((case a.getflag when '1' then a.getmoney when '0' then -a.getmoney else '' end)) - sum(to_number((case a.getflag when '1' then a.serialno when '0' then -a.serialno else '' end)))) is not null then (sum((case a.getflag when '1' then a.getmoney when '0' then -a.getmoney else '' end)) - sum(to_number((case a.getflag when '1' then a.serialno when '0' then -a.serialno else '' end))))  else 0 end)"
							+ " from ljagetendorse a"
							+ " where a.feeoperationtype = 'XT'"
							+ " and a.grpcontno = '00000000000000000000'"
							+ " and a.makedate >= '" + "?mStartDate?" + "'"
							+ " and a.makedate <= '" + "?mEndDate?" + "'"
							+ " and a.managecom like concat('" + "?tManageCom?" + "','%')"
							+ mSaleChlSQL + " "
							+ " group by substr(a.managecom,1," + tComLength
							+ "),riskcode" + " order by  mg,riskcode";
					sqlbv11.sql(mRePortFlagSQL);
					sqlbv11.put("mStartDate", mStartDate);
					sqlbv11.put("mEndDate", mEndDate);
					sqlbv11.put("tManageCom", tManageCom);
					sqlbv11.put("mSaleChnl", mSaleChnl);
					
					logger.debug("SQLMG:" + mRePortFlagSQL);
					tssrs = texesql.execSQL(sqlbv11);
					if (tssrs == null || tssrs.getMaxRow() <= 0) {
						strLine = new String[5];
						strLine[0] = BqNameFun.getComName(rssrs.GetText(k, 1));
						strLine[1] = "";
						for (j = 2; j < 5; j++) {
							strLine[j] = "0";
						}
						tPolListTable.add(strLine);
					} else {
						for (int i = 1; i <= tssrs.getMaxRow(); i++) {
							strLine = new String[5];
							strLine[0] = BqNameFun.getComName(tssrs.GetText(i,
									1));
							for (j = 1; j < 5; j++) {
								strLine[j] = tssrs.GetText(i, j + 1);
							}
							strLine[1] = BqNameFun.getRiskShortName(tssrs
									.GetText(i, 2));
							tSum1 += Double.parseDouble(tssrs.GetText(i, 3));
							tSum2 += Double.parseDouble(tssrs.GetText(i, 4));
							tSum3 += Double.parseDouble(tssrs.GetText(i, 5));
							tPolListTable.add(strLine);
						}
					}
				}

				tPolListTable.setName("BILL");

				strLine = new String[5];
				strLine[0] = "合   计";
				strLine[1] = "";
				strLine[2] = String.valueOf(BqNameFun.getFormat(tSum1));
				strLine[3] = String.valueOf(BqNameFun.getFormat(tSum2));
				strLine[4] = String.valueOf(BqNameFun.getFormat(tSum3));
				tPolListTable.add(strLine);

				tXmlExport.addListTable(tPolListTable, strLine);

				break;
			case 6://
				texttag.add("Title", "撤件率(包含万能险)");
				tTypeControl = tTypeControl + mRePortFlag;

				// 本期保费的销售渠道
				String tSaleChlSQL = "";

				if (mSaleChnl != null && !"".equals(mSaleChnl)) {
					mSaleChlSQL = " and exists (select 'X'" + "from lccont p "
							+ "where p.contno = a.contno "
							+ " and p.salechnl = '" + "?mSaleChnl?" + "')";

					tSaleChlSQL = " and exists (select 'X'" + "from lccont p "
							+ "where p.contno = t.contno "
							+ " and p.salechnl = '" + "?mSaleChnl?" + "')";
				}
				// mScanFlagSQL="";
				// " --"
				tXmlExport.addDisplayControl(tTypeControl);
				tContListTable.setName("BILL");
				SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
				// 按机构进行统计
				sql = "select distinct comcode from ldcom "
						+ "where  cahr_length(comcode)=" + tComLength
						+ " and comcode like concat('" + "?mManageCom?" +"','%') "
						+ " and comcode not in('8699') order by comcode";
				sqlbv12.sql(sql);
				sqlbv12.put("mManageCom", mManageCom);
				rssrs = texesql.execSQL(sqlbv12);
				for (int k = 1; k <= rssrs.getMaxRow(); k++) {
					String tManageCom = rssrs.GetText(k, 1);
					SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
					mRePortFlagSQL = " select mg,wtnum,sumnum,"
							+ "(case when sumnum>0 then "
							+ " concat(trim(cast(round(wtnum / sumnum * 100, 4) as char(20))) , '%') "
							+ " else "
							+ " '0.00%' "
							+ " end "
							+ " ), "
							+ "wtmoney,summoney,"
							+ "(case when summoney>0 then "
							+ " concat(trim(cast(round(wtmoney / summoney * 100, 4) as char(20))) , '%' )"
							+ " else "
							+ " '0.00%' "
							+ " end "
							+ " ) "
							+ " from ( select mg,"
							+ " wtnum,"
							+ "(select  count(distinct p14) "
							+ "	from lacommision b"
							+ " where exists   (select 'X' "
							+ " from lacommision a "
							+ "where b.receiptno=a.receiptno "
							+ "	and a.tmakedate >= '"
							+ "?mStartDate?"
							+ "'"
							+ "	and tmakedate <='"
							+ "?mEndDate?"
							+ "'"
							+ "	and grppolno = '00000000000000000000'"
							+ "	and mainpolno = polno"
							+ "	and (flag = 0 or (flag = 1 and transmoney >= 0))"
							+ "	and paycount = 1"
							+ mSaleChlSQL
							+ ") "
							+ "and (flag = 0 or (flag = 1 and transmoney >= 0)) "
							+ "and paycount = 1 and b.managecom like  concat('',concat(mg,'%')) "
							+ "   ) sumnum,"
							+ " wtmoney,"
							+ "(select  (case when sum(transmoney) is not null then sum(transmoney)  else 0 end) "
							+ "	from lacommision b"
							+ " where exists   (select 'X' "
							+ " from lacommision a "
							+ "where b.receiptno=a.receiptno "
							+ "	and a.tmakedate >= '"
							+ "?mStartDate?"
							+ "'"
							+ "	and tmakedate <='"
							+ "?mEndDate?"
							+ "'"
							+ "	and grppolno = '00000000000000000000'"
							+ "	and mainpolno = polno"
							+ "	and (flag = 0 or (flag = 1 and transmoney >= 0))"
							+ "	and paycount = 1"
							+ mSaleChlSQL
							+ ") "
							+ "and (flag = 0 or (flag = 1 and transmoney >= 0)) "
							+ "and paycount = 1 and b.managecom like  concat('',concat(mg,'%')) ) summoney"
							+ " from (select substr(a.managecom,1,"
							+ tComLength
							+ ") mg,"
							+ " count(distinct a.contno) wtnum,"
							+ " (case when sum((case a.getflag when '1' then a.getmoney when '0' then -a.getmoney else '' end)) is not null then sum((case a.getflag when '1' then a.getmoney when '0' then -a.getmoney else '' end))  else 0 end) wtmoney"
							+ " from ljagetendorse a"
							+ " where a.feeoperationtype = 'WT'"
							+ " and a.grpcontno = '00000000000000000000'"
							// + " and a.feefinatype='TF'"
							+ " and a.makedate >= '" + "?mStartDate?" + "'"
							+ " and a.makedate <= '" + "?mEndDate?"+ "'"
							+ " and a.managecom like concat('" + "?tManageCom?" + "','%')"
							+ mSaleChlSQL + " "
							+ " group by substr(a.managecom,1," + tComLength
							+ ")))";
					logger.debug("SQL:" + mRePortFlagSQL);
					sqlbv13.sql(mRePortFlagSQL);
					sqlbv13.put("mStartDate", mStartDate);
					sqlbv13.put("mEndDate", mEndDate);
					sqlbv13.put("mSaleChnl", mSaleChnl);
					sqlbv13.put("tManageCom", tManageCom);
					tssrs = texesql.execSQL(sqlbv13);
					if (tssrs == null || tssrs.getMaxRow() <= 0) {
						strLine = new String[7];
						strLine[0] = BqNameFun.getComName(rssrs.GetText(k, 1));
						for (j = 1; j < 7; j++) {
							if (j == 2) {
								SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
								String tSQL = "select  (case when count(distinct p14) is not null then count(distinct p14)  else 0 end) "
									+ "	from lacommision b"
									+ " where exists   (select 'X' "
									+ " from lacommision a "
									+ "where b.receiptno=a.receiptno "
									+ "	and a.tmakedate >= '"
									+ "?mStartDate?"
									+ "'"
									+ "	and tmakedate <='"
									+ "?mEndDate?"
									+ "'"
									+ "	and grppolno = '00000000000000000000'"
									+ "	and mainpolno = polno"
									+ "	and (flag = 0 or (flag = 1 and transmoney >= 0))"
									+ "	and paycount = 1"
									+ mSaleChlSQL
									+ ") "
									+ "and (flag = 0 or (flag = 1 and transmoney >= 0)) "
									+ "and paycount = 1 and b.managecom like concat('"+ "?managecom?" + "','%')   ";
								sqlbv14.sql(tSQL);
								sqlbv14.put("mStartDate", mStartDate);
								sqlbv14.put("mEndDate", mEndDate);
								sqlbv14.put("mSaleChnl", mSaleChnl);
								sqlbv14.put("managecom", rssrs.GetText(k, 1));
								strLine[j] = texesql.getOneValue(sqlbv14);
								tSum2+=Double.parseDouble(strLine[j]);
							} else if (j == 5) {
								SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
								String tSQL = "select  (case when sum(transmoney) is not null then sum(transmoney)  else 0 end) "
									+ "	from lacommision b"
									+ " where exists   (select 'X' "
									+ " from lacommision a "
									+ "where b.receiptno=a.receiptno "
									+ "	and a.tmakedate >= '"
									+ "?mStartDate?"
									+ "'"
									+ "	and tmakedate <='"
									+ "?mEndDate?"
									+ "'"
									+ "	and grppolno = '00000000000000000000'"
									+ "	and mainpolno = polno"
									+ "	and (flag = 0 or (flag = 1 and transmoney >= 0))"
									+ "	and paycount = 1"
									+ mSaleChlSQL
									+ ") "
									+ "and (flag = 0 or (flag = 1 and transmoney >= 0)) "
									+ "and paycount = 1 and b.managecom like concat('"+ "?managecom?" + "','%')   ";
								sqlbv15.sql(tSQL);
								sqlbv15.put("mStartDate", mStartDate);
								sqlbv15.put("mEndDate", mEndDate);
								sqlbv15.put("mSaleChnl", mSaleChnl);
								sqlbv15.put("managecom", rssrs.GetText(k, 1));
								strLine[j] = texesql.getOneValue(sqlbv15);
								tSum5+=Double.parseDouble(strLine[j]);

							}else
							{
								strLine[j] = "0";
							}

						}
						tContListTable.add(strLine);
					} else {
						for (int i = 1; i <= tssrs.getMaxRow(); i++) {
							strLine = new String[7];
							strLine[0] = BqNameFun.getComName(tssrs.GetText(i,
									1));
							for (j = 1; j < 7; j++) {
								strLine[j] = tssrs.GetText(i, j + 1);
							}
							tSum1 += Double.parseDouble(tssrs.GetText(i, 2));
							tSum2 += Double.parseDouble(tssrs.GetText(i, 3));
							// tSum3 += Double.parseDouble(tssrs.GetText(i, 4));
							tSum4 += Double.parseDouble(tssrs.GetText(i, 5));
							tSum5 += Double.parseDouble(tssrs.GetText(i, 6));
							// tSum6 += Double.parseDouble(tssrs.GetText(i, 7));
							tContListTable.add(strLine);
						}
					}
				}

				strLine = new String[7];
				strLine[0] = "合   计";
				strLine[1] = String.valueOf((int) tSum1);
				strLine[2] = String.valueOf((int) tSum2);
				strLine[3] = "";
				strLine[4] = String.valueOf(BqNameFun.getFormat(tSum4));
				strLine[5] = String.valueOf(BqNameFun.getFormat(tSum5));
				strLine[6] = "";
				tContListTable.add(strLine);
				tXmlExport.addListTable(tContListTable, strLine);

				tSum1 = 0;
				tSum2 = 0;
				tSum3 = 0;
				tSum4 = 0;
				tSum5 = 0;
				tSum6 = 0;
				// " --"
				texttag.add("Title1", "撤件率(不包含万能险)");
				SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
				// 按机构进行统计
				sql = "select distinct comcode from ldcom "
						+ "where  char_length(comcode)=?tComLength? and comcode like concat('" + "?mManageCom?" + "','%') and comcode not in('8699') order by comcode";
				sqlbv16.sql(sql);
				sqlbv16.put("tComLength", tComLength);
				sqlbv16.put("mManageCom", mManageCom);
				rssrs = texesql.execSQL(sqlbv16);
				for (int k = 1; k <= rssrs.getMaxRow(); k++) {
					String tManageCom = rssrs.GetText(k, 1);
					SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
					mRePortFlagSQLMG = " select mg,wtnum,sumnum,"
							+ "(case when sumnum>0 then "
							+ " concat(trim(cast(round(wtnum / sumnum * 100, 4) as char(20))) , '%') "
							+ " else "
							+ " '0.00%' "
							+ " end "
							+ " ), "
							+ "wtmoney,summoney,"
							+ "(case when summoney>0 then "
							+ " concat(trim(cast(round(wtmoney / summoney * 100, 4) as char(20))) , '%') "
							+ " else "
							+ " '0.00%' "
							+ " end "
							+ " ) "
							+ " from ( select mg,"
							+ " wtnum,"
							+ "(select  count(distinct p14) "
							+ "	from lacommision b"
							+ " where exists   (select 'X' "
							+ " from lacommision a "
							+ "where b.receiptno=a.receiptno "
							+ "	and a.tmakedate >= '"
							+ "?mStartDate?"
							+ "'"
							+ "	and tmakedate <='"
							+ "?mEndDate?"
							+ "'"
							+ "	and grppolno = '00000000000000000000'"
							+ "	and mainpolno = polno"
							+ "	and (flag = 0 or (flag = 1 and transmoney >= 0))"
							+ "	and paycount = 1  and  riskcode not in ('314301', '314302')"
							+ mSaleChlSQL
							+ ") "
							+ "and (flag = 0 or (flag = 1 and transmoney >= 0)) "
							+ "and paycount = 1  and b.managecom like concat('',concat(mg,'%'))  ) sumnum,"
							// + " wtnum/sumnum*100,"
							+ " wtmoney,"

							+ "(select  (case when sum(transmoney) is not null then sum(transmoney)  else 0 end) "
							+ "	from lacommision b"
							+ " where exists   (select 'X' "
							+ " from lacommision a "
							+ "where  b.receiptno=a.receiptno "
							+ "	and a.tmakedate >= '"
							+ "?mStartDate?"
							+ "'"
							+ "	and tmakedate <='"
							+ "?mEndDate?"
							+ "'"
							+ "	and grppolno = '00000000000000000000'"
							+ "	and mainpolno = polno"
							+ "	and (flag = 0 or (flag = 1 and transmoney >= 0))"
							+ "	and paycount = 1 and  riskcode not in ('314301', '314302')"
							+ mSaleChlSQL
							+ ") "
							+ "and (flag = 0 or (flag = 1 and transmoney >= 0)) "
							+ "and paycount = 1  and b.managecom like concat('',concat(mg,'%')) ) summoney"
							// + " wtmoney/summoney*100"
							+ " from (select substr(a.managecom,1,"
							+ tComLength
							+ ") mg,"
							+ " count(distinct a.contno) wtnum,"
							+ " (case when sum((case a.getflag when '1' then a.getmoney when '0' then -a.getmoney else '' end)) is not null then sum((case a.getflag when '1' then a.getmoney when '0' then -a.getmoney else '' end))  else 0 end) wtmoney"
							+ " from ljagetendorse a"
							+ " where a.feeoperationtype = 'WT'"
							// + " and a.feefinatype='TF'"
							+ " and a.grpcontno = '00000000000000000000'"
							+ " and a.makedate >= '" + "?mStartDate?" + "'"
							+ " and a.makedate <= '" + "?mEndDate?" + "'"
							+ " and a.managecom like concat('" + "?tManageCom?" + "','%')"
							+ mSaleChlSQL
							+ " and a.riskcode not in ('314301', '314302')"
							+ " group by substr(a.managecom,1," + tComLength
							+ ")))" + " ";
					logger.debug("SQLMG:" + mRePortFlagSQLMG);
					sqlbv17.sql(mRePortFlagSQLMG);
					sqlbv17.put("mSaleChnl", mSaleChnl);
					sqlbv17.put("mStartDate", mStartDate);
					sqlbv17.put("mEndDate", mEndDate);
					sqlbv17.put("tManageCom", tManageCom);
					tssrs = texesql.execSQL(sqlbv17);
					if (tssrs == null || tssrs.getMaxRow() <= 0) {
						strLine = new String[7];
						strLine[0] = BqNameFun.getComName(rssrs.GetText(k, 1));
						for (j = 1; j < 7; j++) {
							if (j == 2) {
								SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
								String tSQL = "select (case when count(distinct p14) is not null then count(distinct p14)  else 0 end) "
									+ "	from lacommision b"
									+ " where exists   (select 'X' "
									+ " from lacommision a "
									+ "where b.receiptno=a.receiptno "
									+ "	and a.tmakedate >= '"
									+ "?mStartDate?"
									+ "'"
									+ "	and tmakedate <='"
									+ "?mEndDate?"
									+ "'"
									+ "	and grppolno = '00000000000000000000'"
									+ "	and mainpolno = polno"
									+ "	and (flag = 0 or (flag = 1 and transmoney >= 0))"
									+ "	and paycount = 1"
									+ mSaleChlSQL
									+ ") "
									+ "and (flag = 0 or (flag = 1 and transmoney >= 0)) "
									+ "and paycount = 1 and  riskcode not in ('314301', '314302') and b.managecom like concat( '"+ "?managecom?" + "','%')  ";
								sqlbv18.sql(tSQL);
								sqlbv18.put("mStartDate", mStartDate);
								sqlbv18.put("mEndDate", mEndDate);
								sqlbv18.put("managecom", rssrs.GetText(k, 1));
								sqlbv18.put("mSaleChnl", mSaleChnl);
								strLine[j] = texesql.getOneValue(sqlbv18);
								tSum2+=Double.parseDouble(strLine[j]);
							} else if (j == 5) {
								SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
								String tSQL = "select  (case when sum(transmoney) is not null then sum(transmoney)  else 0 end) "
									+ "	from lacommision b"
									+ " where exists   (select 'X' "
									+ " from lacommision a "
									+ "where b.receiptno=a.receiptno "
									+ "	and a.tmakedate >= '"
									+ "?mStartDate?"
									+ "'"
									+ "	and tmakedate <='"
									+ "?mEndDate?"
									+ "'"
									+ "	and grppolno = '00000000000000000000'"
									+ "	and mainpolno = polno"
									+ "	and (flag = 0 or (flag = 1 and transmoney >= 0))"
									+ "	and paycount = 1"
									+ mSaleChlSQL
									+ ") "
									+ "and (flag = 0 or (flag = 1 and transmoney >= 0)) "
									+ "and paycount = 1 and  riskcode not in ('314301', '314302') and b.managecom like concat('"+ "?managecom?" + "','%') ";
								sqlbv19.sql(tSQL);
								sqlbv19.put("mStartDate", mStartDate);
								sqlbv19.put("mEndDate", mEndDate);
								sqlbv19.put("mSaleChnl", mSaleChnl);
								sqlbv19.put("managecom", rssrs.GetText(k, 1));
							strLine[j] = texesql.getOneValue(sqlbv19);
							tSum5+=Double.parseDouble(strLine[j]);

							}else
							{
								strLine[j] = "0";
							}

						}
						tPolListTable.add(strLine);
					}

					for (int i = 1; i <= tssrs.getMaxRow(); i++) {
						strLine = new String[7];
						strLine[0] = BqNameFun.getComName(tssrs.GetText(i, 1));
						for (j = 1; j < 7; j++) {

							strLine[j] = tssrs.GetText(i, j + 1);
						}
						tSum1 += Double.parseDouble(tssrs.GetText(i, 2));
						tSum2 += Double.parseDouble(tssrs.GetText(i, 3));
						// tSum3 = Double.parseDouble(tssrs.GetText(i, 4));
						tSum4 += Double.parseDouble(tssrs.GetText(i, 5));
						tSum5 += Double.parseDouble(tssrs.GetText(i, 6));
						// tSum6 = Double.parseDouble(tssrs.GetText(i, 7));
						tPolListTable.add(strLine);
					}
				}
				tPolListTable.setName("BILL1");
				strLine = new String[7];
				strLine[0] = "合   计";
				strLine[1] = String.valueOf((int) tSum1);
				strLine[2] = String.valueOf((int) tSum2);
				strLine[3] = "";
				strLine[4] = String.valueOf(BqNameFun.getFormat(tSum4));
				strLine[5] = String.valueOf(BqNameFun.getFormat(tSum5));
				strLine[6] = "";
				tPolListTable.add(strLine);
				tXmlExport.addListTable(tPolListTable, strLine);
				break;
			case 7://
				texttag.add("Title", "协议退保差值明细");
				tTypeControl = tTypeControl + mRePortFlag;

				if (mSaleChnl != null && !"".equals(mSaleChnl)) {
					mSaleChlSQL = "and exists (select 'X'" + "from lccont p "
							+ "where p.contno = a.contno "
							+ " and p.salechnl = '" + "?mSaleChnl?" + "')";
				}
				SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
				// mScanFlagSQL="";
				mRePortFlagSQL = " select contno,"
						+ " edorconfno,ca,"
						+ " riskname,"
						+ " cv,"
						+ " (case when sum(gm) is not null then sum(gm)  else 0 end),"
						+ " (case when sum(sgm) is not null then sum(sgm)  else 0 end),"
						+ " (case when sum(gm) - sum(sgm) is not null then sum(gm) - sum(sgm)  else 0 end),"
						+ " an,"
						+ " acode,"
						+ " bm,"
						+ " managecom,"
						+ " operator,"
						+ " makedate"
						+ " from (select a.contno contno,"
						+ " (select c.edorconfno"
						+ " from lpedorapp c"
						+ " where c.edoracceptno ="
						+ " (select r.edoracceptno"
						+ " from lpedoritem r"
						+ " where r.edorno = a.endorsementno)) edorconfno,"
						+ " (select riskname"
						+ " from lmriskapp m"
						+ " where m.riskcode = a.riskcode) riskname,"
						+ " (select c.appntname from lcpol c where c.polno = a.polno) ca,"
						+ " (select c.cvalidate from lcpol c where c.polno = a.polno) cv,"
						+ " (case when (case a.getflag when '1' then a.getmoney when  '0' then -a.getmoney else '' end) is not null then (case a.getflag when '1' then a.getmoney when  '0' then -a.getmoney else '' end)  else 0 end) gm,"
						// modify by jiaqiangli 2009-10-28 根据getflag进行decode
						+ " to_number((case when (case a.getflag when '1' then a.serialno when  '0' then -a.serialno else '' end) is not null then (case a.getflag when '1' then a.serialno when  '0' then -a.serialno else '' end)  else 0 end)) sgm," + " (select f.name"
						+ " from laagent f, lccont b"
						+ " where f.agentcode = b.agentcode"
						+ " and b.contno = a.contno) an,"
						+ " (select f.agentcode" + " from laagent f, lccont b"
						+ " where f.agentcode = b.agentcode"
						+ " and b.contno = a.contno) acode,"
						+ " substr(a.managecom, 1, 4) bm," + " managecom,"
						+ " (select operator from lpedorapp where edoracceptno = (select  distinct edoracceptno from lpedoritem where edorno = a.endorsementno)) operator," + " makedate" + " From ljagetendorse a"
						+ " where 1 = 1"
						+ " and a.grpcontno = '00000000000000000000'"
						+ " and a.feeoperationtype = 'XT'"
						+ " and a.makedate >= '" + "?mStartDate?" + "'"
						+ " and a.makedate <= '" + "?mEndDate?" + "'"
						+ " and a.managecom like concat('" + "?mManageCom?" + "','%')"
						+ mSaleChlSQL + " )" + " group by contno,"
						+ " edorconfno," + "ca,riskname," + " acode,cv,"
						+ " bm," + " managecom," + " an," + " operator,"
						+ " makedate order by contno,edorconfno";
				tXmlExport.addDisplayControl(tTypeControl);
				logger.debug("SQL:" + mRePortFlagSQL);
				sqlbv20.sql(mRePortFlagSQL);
				sqlbv20.put("mStartDate", mStartDate);
				sqlbv20.put("mEndDate", mEndDate);
				sqlbv20.put("mManageCom", mManageCom);
				sqlbv20.put("mSaleChnl", mSaleChnl);
				tssrs = texesql.execSQL(sqlbv20);
				if (tssrs == null || tssrs.getMaxRow() <= 0) {
					CError.buildErr(this, mStartDate + "至" + mEndDate
							+ "之间无待打印清单！");
					return false;
				}
				tContListTable.setName("BILL");

				for (int i = 1; i <= tssrs.getMaxRow(); i++) {
					strLine = new String[14];
					for (j = 0; j < 14; j++) {
						strLine[j] = tssrs.GetText(i, j + 1);
					}
					tContListTable.add(strLine);
				}
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
