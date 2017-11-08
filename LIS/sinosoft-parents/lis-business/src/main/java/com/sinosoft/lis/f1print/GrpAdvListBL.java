package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author yinxiaoyi
 * function :print InputEfficiency or print CheckEfficiency Business Logic layer
 * @version 1.0
 * @date 2006-04-14
 */

import java.text.DecimalFormat;

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class GrpAdvListBL {
private static Logger logger = Logger.getLogger(GrpAdvListBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private String strMngCom;
	private String strStartDate;
	private String strEndDate;
	private String strPayType;

	public GrpAdvListBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();

		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	/***************************************************************************
	 * Name : getInputData() function :receive data from jsp and check date
	 * check :judge whether startdate begin enddate return :true or false
	 */
	private boolean getInputData(VData cInputData) {
		strMngCom = (String) cInputData.get(0);
		strStartDate = (String) cInputData.get(1);
		strEndDate = (String) cInputData.get(2);
		strPayType = (String) cInputData.get(3);

		logger.debug("strStartDate:" + strStartDate);
		logger.debug("strEndDate:" + strEndDate);
		logger.debug("strPaytype:" + strPayType);

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	/***************************************************************************
	 * name : buildError function : Prompt error message return :error message
	 */

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "GrpAdvListBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/***************************************************************************
	 * name : getPrintData() function :get print data parameter : null return
	 * :true or false
	 */

	private boolean getPrintData() {
		// 选择临时文件读取目录
		String tSQL = "";
		ExeSQL exeSql = new ExeSQL();
		// 选择临时文件读取目录
		ListTable alistTable = new ListTable(); // Create a ListTable instance
		alistTable.setName("RISK"); // Appoint to a sepcial flag

		SSRS FenSSRS = new SSRS();
		SSRS ZhongSSRS = new SSRS();
		SSRS YingSSRS = new SSRS();
		// 查询分公司SQL
		String tFenSQL = "";
		// 查询中支SQL
		String tZhongSQL = "";
		// 查询营销服务部SQL
		String tYingSQL = "";

		/**
		 * @todo 如果录入的机构为总公司，则循环取总公司下的所有分公司。 如果没有选择总公司则将分公司的数量设为1
		 */
		if (strMngCom.equals("86")) {
			tFenSQL = " select comcode,name from ldcom where upcomcode = '"
					+ strMngCom + "'";
		} else {
			if (strMngCom.length() == 4) {
				tFenSQL = " select comcode,name from ldcom where comcode = '"
						+ strMngCom + "'";
			} else {
				tFenSQL = " select comcode,name from ldcom where comcode = '"
						+ strMngCom.substring(0, 4) + "'";
			}
		}
		FenSSRS = exeSql.execSQL(tFenSQL);
		int j_count = 0;
		/**
		 * @todo 循环分公司
		 */
		for (int i = 0; i < FenSSRS.getMaxRow(); i++) {
			// 取得分公司名称
			String tFenName = FenSSRS.GetText(i + 1, 2);
			if (strMngCom.length() <= 4) {
				tZhongSQL = " select comcode,name from ldcom where upcomcode = '"
						+ FenSSRS.GetText(i + 1, 1) + "'";
			} else {
				tZhongSQL = " select comcode,name from ldcom where comcode = '"
						+ strMngCom.substring(0, 6) + "'";
			}
			ZhongSSRS = exeSql.execSQL(tZhongSQL);
			// 循环中支
			for (int j = 0; j < ZhongSSRS.getMaxRow(); j++) {
				// 中支名称
				String tZhongName = ZhongSSRS.GetText(j + 1, 2);
				if (strMngCom.length() <= 6) {
					tYingSQL = " select comcode,name from ldcom where upcomcode = '"
							+ ZhongSSRS.GetText(j + 1, 1) + "'";
				} else {
					tYingSQL = " select comcode,name from ldcom where comcode = '"
							+ strMngCom.substring(0, 8) + "'";
				}
				YingSSRS = exeSql.execSQL(tYingSQL);
				// 循环营销部
				for (int k = 0; k < YingSSRS.getMaxRow(); k++) {
					String tYingName = YingSSRS.GetText(k + 1, 2);
					String strTmp = "";
					if (strPayType.equals("4")) {
						strTmp = "b.BankAccNo";
					} else {
						strTmp = "b.ChequeNo";
					}
					// ///////////////////////////////////////////////////////////
					// /根据《数据结构清理模板_新契约财务.xls》中的变更说明，团险新契约在暂交费表LJTempFee中//
					// 判断条件为TempFeeType=4 and SaleChnl=2
					// ///////////////////////////////////////////////////////////

					// TempFeeType=1代表新单收费，othernotype=4代表团险-----modify by
					// haopan //
					tSQL = "select a.TempFeeNo,a.APPntName,a.PayMoney,"
							+ strTmp + ",a.AgentCode,a.AgentGroup,a.PayDate "
							+ " from LJTempFee a,LJTempFeeClass b "
							+ " where  a.TempFeeNo=b.TempFeeNo "
							+ " and a.TempFeeType='1' and a.othernotype = '4' "
							+ " and a.managecom like'"
							+ YingSSRS.GetText(k + 1, 1) + "%' and b.PayMode='"
							+ strPayType + "' " + " and a.modifydate >='"
							+ strStartDate + "' and  a.modifydate <='"
							+ strEndDate + "'";

					SSRS ssrs = exeSql.execSQL(tSQL);
					if (ssrs.getMaxRow() <= 0) {
						logger.debug("管理机构'" + YingSSRS.GetText(k + 1, 1)
								+ "'在'" + strStartDate + "'没有数据");
						continue;
					}
					int i_count = 0;
					i_count = ssrs.getMaxRow();
					// /////////////////////////////////////////////////////////////
					String[] colx = new String[9];
					colx[0] = "  统计日期：";
					colx[1] = strStartDate + " 至";
					colx[2] = strEndDate;
					colx[3] = "  打印日期：";
					colx[4] = PubFun.getCurrentDate();
					colx[5] = "    ";
					colx[6] = "    ";
					colx[7] = "单位：";
					colx[8] = "人民币/外币";
					alistTable.add(colx);

					String[] col1 = new String[9];
					col1[0] = "分公司：";
					col1[1] = tFenName;
					col1[2] = "    ";
					col1[3] = "中心支公司：";
					col1[4] = tZhongName;
					col1[5] = "    ";
					col1[6] = "营业区：";
					col1[7] = tYingName;
					col1[8] = "    ";
					alistTable.add(col1);

					String[] col2 = new String[9];
					col2[0] = "   ";
					col2[1] = "   ";
					col2[2] = "   ";
					col2[3] = "   ";
					col2[4] = "   ";
					col2[5] = "   ";
					col2[6] = "   ";
					col2[7] = "   ";
					col2[8] = "   ";
					alistTable.add(col2);

					String[] col3 = new String[9];
					col3[0] = "序号";
					col3[1] = "暂收收据号";
					col3[2] = "投保人";
					col3[3] = "保费收入";
					if (strPayType.equals("4")) {
						col3[4] = "开户行帐号";
					} else {
						col3[4] = "支票号";
					}
					col3[5] = "业务员编号";
					col3[6] = "业务员姓名";
					col3[7] = "部/分部";
					col3[8] = "收银日期";
					alistTable.add(col3);

					double Sum = 0;
					for (int l = 1; l <= i_count; l++) {
						String[] cols = new String[9];
						try {
							cols[0] = l + "";
							cols[1] = ssrs.GetText(l, 1);
							cols[2] = ssrs.GetText(l, 2);
							cols[3] = ssrs.GetText(l, 3);
							cols[4] = ssrs.GetText(l, 4);
							cols[5] = ssrs.GetText(l, 5);
							String sql = "select name from laagent where agentcode='"
									+ cols[5].trim() + "'";
							// logger.debug("您的sql的执行结果是"+sql);
							ExeSQL exeSQL2 = new ExeSQL();
							SSRS ssrs2 = exeSQL2.execSQL(sql);
							int count2 = ssrs2.getMaxRow();
							if (count2 == 0) {
								cols[6] = "";
							} else {
								cols[6] = ssrs2.GetText(1, 1);
							}
							cols[7] = ssrs.GetText(l, 6);
							cols[8] = ssrs.GetText(l, 7);
							Sum = Sum + Double.parseDouble(ssrs.GetText(l, 3));
						} catch (Exception ex) {
							buildError("getprintData", "抽取数据失败");
							logger.debug("抽取数据失败");
							return false;
						}
						// logger.debug("抽取信息成功！！");
						alistTable.add(cols);
					}
					String[] col4 = new String[9];
					col4[0] = "   ";
					col4[1] = "   ";
					col4[2] = "   ";
					col4[3] = "   ";
					col4[4] = "   ";
					col4[5] = "   ";
					col4[6] = "   ";
					col4[7] = "   ";
					col4[8] = "   ";
					alistTable.add(col4);
					j_count = j_count + i_count;
					String[] col5 = new String[9];
					col5[0] = "  小计:金额：";
					col5[1] = new DecimalFormat("0.00").format(Sum);
					col5[2] = "   ";
					col5[3] = " 件数：" + i_count;
					col5[4] = "   ";
					col5[5] = "   ";
					col5[6] = "   ";
					col5[7] = "   ";
					col5[8] = "   ";
					alistTable.add(col5);

					String[] col6 = new String[9];
					col6[0] = "复核： ";
					col6[1] = "   ";
					col6[2] = "   ";
					col6[3] = "经办： ";
					col6[4] = "   ";
					col6[5] = "   ";
					col6[6] = "   ";
					col6[7] = "   ";
					col6[8] = "   ";
					alistTable.add(col6);

					String[] col7 = new String[9];
					col7[0] = "   ";
					col7[1] = "   ";
					col7[2] = "   ";
					col7[3] = "   ";
					col7[4] = "   ";
					col7[5] = "   ";
					col7[6] = "   ";
					col7[7] = "   ";
					col7[8] = "   ";
					alistTable.add(col7);

					String[] col8 = new String[9];
					col8[0] = "   ";
					col8[1] = "   ";
					col8[2] = "   ";
					col8[3] = "   ";
					col8[4] = "   ";
					col8[5] = "   ";
					col8[6] = "   ";
					col8[7] = "   ";
					col8[8] = "   ";
					alistTable.add(col8);

					String[] col9 = new String[9];
					col9[0] = "   ";
					col9[1] = "   ";
					col9[2] = "   ";
					col9[3] = "   ";
					col9[4] = "   ";
					col9[5] = "   ";
					col9[6] = "   ";
					col9[7] = "   ";
					col9[8] = "   ";
					alistTable.add(col9);
				}
			}
		}
		String[] col = new String[1];
		TextTag texttag = new TextTag(); // Create a TextTag instance
		XmlExport xmlexport = new XmlExport(); // Create a XmlExport instance
		if (j_count == 0) {
			buildError("tSSRSError", "对不起，没有查询结果。所选管理机构在对应日期内没有数据！");
			return false;
		}
		xmlexport.createDocument("GrpAdvList.vts", "printer"); // appoint to a
																// special
																// output .vts
																// file

		xmlexport.addListTable(alistTable, col);
		String strPayTypeName = "";
		if (strPayType.equals("4")) {
			strPayTypeName = "(银行代收)";
		} else {
			strPayTypeName = "(现金、支票)";
		}
		texttag.add("PayType", strPayTypeName);
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}

		xmlexport.outputDocumentToFile("D:\\", "GrpAdvList"); // 输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

	public static void main(String[] args) {
		GrpAdvListUI tGrpAdvListUI = new GrpAdvListUI();

		VData tVData = new VData();
		tVData.addElement("863200");
		tVData.addElement("2002-02-13");
		tVData.addElement("2005-04-14");
		tVData.addElement("1");

		tGrpAdvListUI.submitData(tVData, "PRINT");

	}

}
