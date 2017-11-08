package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title:承保单删除清单 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author ChenRong
 * @version 6.0
 */

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class GrpContDelListBL {
private static Logger logger = Logger.getLogger(GrpContDelListBL.class);
	/**
	 * 错误处理类，每个需要错误处理的类中都放置该类
	 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();

	// 全局数据
	private String mManageCom = "";
	private String mDelReason = "";
	private String mStartDate = "";
	private String mEndDate = "";

	private GlobalInput mGlobalInput = new GlobalInput();

	public GrpContDelListBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}
		logger.debug("cOperate=" + cOperate);
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		mResult.clear();

		// 准备所有要打印的数据
		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mGlobalInput == null) {
			buildError("mGlobalInput", "没有得到足够的信息！");
			return false;
		}
		mTransferData = ((TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0));
		if (mTransferData == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		mStartDate = (String) mTransferData.getValueByName("StartDate");
		mEndDate = (String) mTransferData.getValueByName("EndDate");
		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		mDelReason = (String) mTransferData.getValueByName("GrpDelReason");
		return true;
	}

	/**
	 * 打印处理
	 * 
	 * @return boolean
	 */
	private boolean getPrintData() {
		int tResultCount = 0;
		int tTotalPolCount = 0;
		ListTable ListTable = new ListTable();
		ListTable.setName("List");
		String[] Title = new String[14];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";
		Title[5] = "";
		Title[6] = "";
		Title[7] = "";
		Title[8] = "";
		Title[9] = "";
		Title[10] = "";
		Title[11] = "";
		Title[12] = "";
		Title[13] = "";
		String tSql = "";
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";

		ExeSQL exeSql = new ExeSQL();
		SSRS ManageTypeSSRS = new SSRS();
		String tManageTypeSQL = "";
		String tManageCom = mManageCom;

		if (mManageCom.trim().equals("86")) {
			tManageTypeSQL = "select comcode,name from ldcom where comgrade='02' "
					+ " order by comcode asc";
		} else {
			String tComCode = mManageCom.trim().substring(0, 4);
			tManageTypeSQL = "select comcode,name from ldcom where  comgrade='02' "
					+ " and comcode='" + tComCode + "' order by comcode asc";
		}
		ManageTypeSSRS = exeSql.execSQL(tManageTypeSQL);

		if (ManageTypeSSRS.getMaxRow() == 0) {
			CError tError = new CError();
			tError.moduleName = "GrpContDelListBL";
			tError.functionName = "getPrintData";
			tError.errorMessage = "错误的管理机构！！！";
			this.mErrors.addOneError(tError);
			return false;
		}

		/**
		 * @todo 循环下属公司
		 */
		for (int i = 0; i < ManageTypeSSRS.getMaxRow(); i++) {
			// 取得公司名称
			String tManageName = ManageTypeSSRS.GetText(i + 1, 2);
			String[] str1 = new String[13];
			if (tManageCom.equals("86")) {
				mManageCom = ManageTypeSSRS.GetText(i + 1, 1);
			}

			str1[0] = "分公司：";
			str1[3] = "";
			str1[1] = tManageName;
			str1[2] = "";
			str1[4] = "";
			str1[5] = "";
			str1[6] = "合计件数：";
			str1[7] = "";
			str1[8] = "";
			str1[9] = "";
			str1[10] = "";
			str1[11] = "";
			str1[12] = "";
			// str1[13] = "";

			String[] str8 = new String[13]; // 空行
			str8[0] = "——————————";
			str8[1] = "——————————";
			str8[2] = "——————————————";
			str8[3] = "——————————";
			str8[4] = "——————————";
			str8[5] = "——————————";
			str8[6] = "——————————";
			str8[7] = "——————————";
			str8[8] = "——————————";
			str8[9] = "——————————";
			str8[10] = "——————————";
			str8[11] = "——————————";
			str8[12] = "——————————";
			// str8[13] = "——————————";

			String[] str3 = new String[13];
			str3[0] = "投保单号";
			str3[1] = "保单号";
			str3[2] = "投保人";
			str3[3] = "险种代码";
			str3[4] = "保险费";
			// str3[5] = "交费期限";
			str3[5] = "投保日期";
			str3[6] = "收费日期";
			str3[7] = "生效日期";
			str3[8] = "签单日期";
			str3[9] = "业务员代码";
			str3[10] = "业务员姓名";
			str3[11] = "删除原因";
			str3[12] = "备注信息";
			// str3[13] = "";
			// tSql = " select bftd.proposalgrpcontno,bftd.grpcontno,"
			// +"bftd.grpname,"
			// + " bftx.riskcode,bftx.prem,'', bftd.polapplydate,"
			// + " (select makedate from ljtempfee where "
			// +
			// " otherno = bftx.grpcontno and riskcode = bftx.riskcode and "
			// +
			// " rownum = 1),bftx.cvalidate,bftd.signdate,bftd.agentcode, "
			// +
			// " (select name from laagent where agentcode =
			// trim(bftd.agentcode)),"
			// +
			// " (select codename from (select rj.delreason, rj.otherno, "
			// + " rj.makedate, rj.maketime from lcdelpollog rj order by "
			// + " rj.makedate, rj.maketime desc),ldcode where otherno = "
			// + " bftd.grpcontno and rownum = 1 and codetype='grpdelreason' and
			// trim(code)=delreason),"
			// + " (select remark from (select rj.remark, rj.otherno, "
			// + " rj.makedate, rj.maketime from lcdelpollog rj order by "
			// + " rj.makedate, rj.maketime desc) where otherno = "
			// + " bftd.grpcontno and rownum = 1 ) "
			// + " from lobgrpcont bftd,lobgrppol bftx "
			// + " where bftd.grpcontno=bftx.grpcontno "
			// + " and exists ( select 'X' from lcdelpollog bfrj where "
			// + " bfrj.otherno=bftd.grpcontno "
			// + " and bfrj.makedate between date '" + mStartDate +
			// "' and date '"
			// + mEndDate + "' and bfrj.delreason=(select delreason from "
			// +"(select delreason ,otherno ffe from lcdelpollog order by "
			// +" makedate,maketime desc) where ffe=bfrj.otherno and rownum=1)
			// ";
			//
			// if (mDelReason != null && !mDelReason.equals(""))
			// {
			// tSql += " and bfrj.delreason='" + mDelReason + "') and "
			// + " bftd.managecom like '" + mManageCom + "%'";
			// }
			// else
			// {
			// tSql += ") and bftd.managecom like '" + mManageCom + "%'";
			// }

			tSql = "select bftd.proposalgrpcontno,'',bftd.grpname,"
					+ " bftx.riskcode,bftx.prem,bftd.polapplydate, (select makedate "
					+ " from ljtempfee where otherno = bftx.grpcontno and riskcode "
					+ " = bftx.riskcode and rownum = 1),bftx.cvalidate, bftd.signdate,"
					+ " bftd.agentcode,(select name from laagent where agentcode = trim(bftd.agentcode)),"
					+ " (select codename from ldcode where codetype='grpdelreason' "
					+ " and trim(code)=bfrj.delreason), bfrj.remark "
					+ "from lcdelpollog bfrj,lobgrppol bftx,lobgrpcont bftd where "
					+ " bfrj.otherno=bftd.grpcontno and bftd.grpcontno = bftx.grpcontno "
					+ " and (bfrj.otherno,bfrj.othernotype,bfrj.makedate,bfrj.maketime) "
					+ "  = (select otherno,othernotype,makedate,maketime from ( "
					+ " select rt.otherno as mm, rt.othernotype, rt.makedate, "
					+ " rt.maketime from lcdelpollog rt order by makedate desc,"
					+ " maketime desc )where mm = bftd.grpcontno and rownum=1 ) "
					+ " and bftd.appflag='0' and bfrj.makedate between date '"
					+ mStartDate + "' and date '" + mEndDate
					+ "' and bftd.managecom like '" + mManageCom + "%'";
			if (mDelReason != null && !mDelReason.equals("")) {
				tSql += " and bfrj.delreason='" + mDelReason + "'";
			}
			logger.debug("strSql=" + tSql.toString());

			SSRS temp2SSRS = new SSRS();
			temp2SSRS = exeSql.execSQL(tSql.toString());

			if (temp2SSRS != null && temp2SSRS.getMaxRow() > 0
					&& temp2SSRS.getMaxCol() > 0) {
				int tPolCount = 0;
				for (int k = 1; k <= temp2SSRS.getMaxRow(); k++) {
					if (k == 1) {
						tPolCount = 1;
					} else {
						if (!temp2SSRS.GetText(k, 1).equals(
								temp2SSRS.GetText(k - 1, 1))) {
							tPolCount = tPolCount + 1;
						}
					}
				}
				tTotalPolCount = tTotalPolCount + tPolCount;
				str1[7] = new Integer(tPolCount).toString();

				ListTable.add(str1);
				ListTable.add(str8);
				ListTable.add(str3);
				logger.debug("temp2SSRS.getMaxRow()"
						+ temp2SSRS.getMaxRow());
				tResultCount = tResultCount + temp2SSRS.getMaxRow();
				for (int l = 1; l <= temp2SSRS.getMaxRow(); l++) {
					String[] stra = new String[19];
					String tBranchSeries = temp2SSRS.GetText(l, 17);
					stra[0] = temp2SSRS.GetText(l, 1);
					stra[1] = temp2SSRS.GetText(l, 2);
					stra[2] = temp2SSRS.GetText(l, 3);
					stra[3] = temp2SSRS.GetText(l, 4);
					stra[4] = temp2SSRS.GetText(l, 5);
					stra[5] = temp2SSRS.GetText(l, 6);
					stra[6] = temp2SSRS.GetText(l, 7);
					stra[7] = temp2SSRS.GetText(l, 8);
					stra[8] = temp2SSRS.GetText(l, 9);
					stra[9] = temp2SSRS.GetText(l, 10);
					stra[10] = temp2SSRS.GetText(l, 11);
					stra[11] = temp2SSRS.GetText(l, 12);
					stra[12] = temp2SSRS.GetText(l, 13);
					// stra[13] = temp2SSRS.GetText(l, 14);
					ListTable.add(stra);
				}
			}

			String[] str5 = new String[14]; // 空行
			str5[0] = "  ";
			str5[1] = "  ";
			str5[2] = "  ";
			str5[3] = "  ";
			str5[4] = "  ";
			str5[5] = "  ";
			str5[6] = "  ";
			str5[7] = "  ";
			str5[8] = "  ";
			str5[9] = "  ";
			str5[10] = "  ";
			str5[11] = "  ";
			str5[12] = "  ";
			// str5[13] = " ";

			String[] str7 = new String[14]; // 空行
			str7[0] = "——————————";
			str7[1] = "——————————";
			str7[2] = "——————————————";
			str7[3] = "——————————";
			str7[4] = "——————————";
			str7[5] = "——————————";
			str7[6] = "——————————";
			str7[7] = "——————————";
			str7[8] = "——————————";
			str7[9] = "——————————";
			str7[10] = "——————————";
			str7[11] = "——————————";
			str7[12] = "——————————";
			// str7[13] = "——————————";

			String[] str6 = new String[14]; // 空行
			str6[0] = "  ";
			str6[1] = "  ";
			str6[2] = "  ";
			str6[3] = "  ";
			str6[4] = "  ";
			str6[5] = "  ";
			str6[6] = "  ";
			str6[7] = "  ";
			str6[8] = "  ";
			str6[9] = "  ";
			str6[10] = "  ";
			str6[11] = "  ";
			str6[12] = "  ";
			// str6[13] = " ";

			if (temp2SSRS.getMaxRow() > 0) {
				ListTable.add(str5);
				ListTable.add(str7);
				ListTable.add(str6);
			}
		}

		if (tResultCount == 0) {
			CError tError = new CError();
			tError.moduleName = "GrpContDelListBL";
			tError.functionName = "getPrintData";
			tError.errorMessage = "在该查询条件内没有打印信息，请确认查询条件是否正确！！！";
			this.mErrors.addOneError(tError);
			return false;
		}

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("GrpContDelList.vts", "printer");
		texttag.add("PrintDate", SysDate);
		texttag.add("Date", mStartDate + " 至 " + mEndDate);
		texttag.add("TotalCount", tTotalPolCount);

		xmlexport.addTextTag(texttag);
		xmlexport.addListTable(ListTable, Title);

		mResult.clear();

		mResult.addElement(xmlexport);
		return true;
	}

	/**
	 * 返回处理信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误信息
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 错误构建
	 * 
	 * @param mFunctionName
	 *            String
	 * @param mErrorMsg
	 *            String
	 */
	private void buildError(String mFunctionName, String mErrorMsg) {
		CError cError = new CError();
		cError.moduleName = "GrpContDelListBL";
		cError.functionName = mFunctionName;
		cError.errorMessage = mErrorMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		GrpContDelListBL GrpContDelListBL = new GrpContDelListBL();
	}
}
