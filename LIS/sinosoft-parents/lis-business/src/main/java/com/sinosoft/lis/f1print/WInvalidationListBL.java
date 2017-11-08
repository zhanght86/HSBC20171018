package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lh
 * @version 1.0
 */

public class WInvalidationListBL {
private static Logger logger = Logger.getLogger(WInvalidationListBL.class);
	String mManageCom = "";
	String mAgentCode = "";
	// String mStartDate = "";//lh注
	// String mEndDate = "";
	String mCalDate = ""; // lh加
	String mStartDate = "";
	String mEndDate = "";
	int mDays = 0;
	String mBank = "";
	String mBankPoint = "";
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	private String mSelString = "";

	// 业务处理相关变量
	/** 全局数据 */

	private GlobalInput mGlobalInput = new GlobalInput();
	// private LCPolSchema mLCPolSchema = new LCPolSchema();
	// private LJSPaySchema mLJSPaySchema = new LJSPaySchema();
	// private LOPRTManagerSchema mLOPRTManagerSchema = new
	// LOPRTManagerSchema();
	// private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	// private VData mLOPRTManagerSchemas = new VData();
	// private int mSchemaNum = 0;

	private TransferData mTransferData = new TransferData();

	public WInvalidationListBL() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		mResult.clear();

		// 准备所有要打印的数据
		if (!getPrintData()) {
			logger.debug("w我真的没有查到任何的值，所以现在我打算把我得到的给掉我的传回去");
			return false;
		}

		return true;

	}

	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ManageCom", "8651");
		// tTransferData.setNameAndValue("AgentCode", AgentCode);
		// tTransferData.setNameAndValue("StartDate",StartDate);//lh注
		// tTransferData.setNameAndValue("EndDate",EndDate);
		tTransferData.setNameAndValue("CalDate", "2005-12-08"); // lh加
		// tTransferData.setNameAndValue("Bank", Bank);
		// tTransferData.setNameAndValue("BankPoint", BankPoint);

		logger.debug("adfasdfasf");
		VData tVData = new VData();
		VData mResult = new VData();
		CErrors mErrors = new CErrors();
		tVData.addElement(tTransferData);
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86";
		tG.Operator = "001";
		tVData.addElement(tG);
		WInvalidationListUI tWInvalidationListUI = new WInvalidationListUI();
		logger.debug("-----------------------我在创建xml之前");
		XmlExport txmlExport = new XmlExport();
		logger.debug("------------------------------我在之后");
		if (!tWInvalidationListUI.submitData(tVData, "PRINT")) {
			logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		}

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
		// mDay = (String[]) cInputData.get(0);
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		mAgentCode = (String) mTransferData.getValueByName("AgentCode");
		mStartDate = (String) mTransferData.getValueByName("StartDate"); // lh注
		mEndDate = (String) mTransferData.getValueByName("EndDate");
		mCalDate = (String) mTransferData.getValueByName("CalDate"); // lh加
		mBank = (String) mTransferData.getValueByName("Bank");
		mBankPoint = (String) mTransferData.getValueByName("BankPoint");

		return true;
	}

	/**
	 * 获取返回信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return this.mResult;
	}

	/**
	 * 错误构建
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "LCPolF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 打印处理
	 * 
	 * @return boolean
	 */
	private boolean getPrintData() {
		double SumMoney = 0;
		String tStartDate = "";
		String tSubDate;
		int aheaddays = 53;
		String SubDay;
		PubFun date = new PubFun();
		FDate tTranferDate = new FDate();
		logger.debug("我是第一个确认的消息");
		String strSql = "";
		ExeSQL tExeSQL = new ExeSQL();
		// 计算出这段时间的间隔天数*/
		// 保存成功处理的纪录数Integer.parseInt(AheadDays)Integer.parseInt(AheadDays)
		// int operSuccCount = 0;
		// lh注---------改为统计日期的点统计
		// if (mStartDate == null && mStartDate.equals("")) {
		// tStartDate = date.getCurrentDate();
		// tStartDate = date.calDate(date.getCurrentDate(), aheaddays, "D",
		// mStartDate);
		// tSubDate = date.calDate(tStartDate, 7, "D", mStartDate);
		// }
		// else
		// {
		// tStartDate = PubFun.calDate(mStartDate, aheaddays, "D", mStartDate);
		// int tSEInterval = date.calInterval(mStartDate, mEndDate,
		// "D");
		// tSubDate = date.calDate(mStartDate,
		// aheaddays + tSEInterval, "D", mStartDate);
		// }
		// 批量的往后台传值

		// lh注改统计的sql语句
		// String strSql = "select
		// j.otherno,c.appntname,s.codename,c.paytodate,j.sumduepaymoney,c.bankaccno,c.bankcode,a.postaladdress,a.phone"
		// + " from ljspay j,lccont c, lcaddress a ,lcappnt b ,ldcode s"
		// + " where j.otherno = c.contno and c.appntno = a.customerno and
		// j.othernotype = '2' and c.contno = b.contno and b.addressno =
		// a.addressno and c.paymode = s.code and s.codetype = 'continuepaymode'
		// "
		// + " and to_char(paytodate,'yyyy-mm-dd') >='" + tStartDate + "' and
		// to_char(paytodate,'yyyy-mm-dd')<='" +
		// tSubDate + "'";

		// lh改后的sql语句
		if (!(mStartDate == null || mStartDate.equals(""))
				&& !(mEndDate == null || mEndDate.equals(""))) {
			strSql = "select datediff(to_date('" + "?mEndDate?" + "','yyyy-mm-dd') , to_date('" + "?mStartDate?" + "','yyyy-mm-dd')) from dual";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(strSql);
			sqlbv.put("mEndDate", mEndDate);
			sqlbv.put("mStartDate", mStartDate);
			mDays = Integer.parseInt(tExeSQL.getOneValue(sqlbv));
			mCalDate = mStartDate;
		} else if (mCalDate == null || mCalDate.equals("")) {
			mCalDate = date.getCurrentDate();
			mDays = 0;
		}
		ListTable tlistTable = new ListTable();
		String strArr[] = null;
		String strCondition = "";
		if (mManageCom != null && !mManageCom.equals("")) {
			strCondition = " and c.ManageCom like '" + mManageCom + "%'";
		}
		if (mAgentCode != null && !mAgentCode.equals("")) {
			strCondition = " and c.agentcode ='" + mAgentCode + "'";
			mSelString = mSelString + mAgentCode + " ";
		}
		if (mBank != null && !mBank.equals("")) {
			strCondition = " and c.bankcode = '" + mBank + "'";
			mSelString = mSelString + mBank + " ";
		}
		tlistTable.setName("BILL");
		int count = -1;
		for (int day = 0; day <= mDays; day++) {
			strSql = "select distinct j.otherno,c.appntname,s.codename,c.paytodate,j.sumduepaymoney,c.bankaccno,c.bankcode,concat((select placename from ldaddress where placetype = '03' and trim(placecode) = a.county),a.postaladdress), (select GetAppntPhone(j.otherno,'') from dual)"
					+ " from ljspay j,lccont c, lcaddress a ,lcappnt b ,ldcode s"
					+ " where j.otherno = c.contno and c.appntno = a.customerno and j.othernotype = '2' and c.contno = b.contno and b.addressno = a.addressno and  c.paymode = s.code and s.codetype = 'continuepaymode'  "
					+ " and paytodate <=to_date('?mCalDate?','yyyy-mm-dd')+"+ day+ "-53 and paytodate >= to_date('?mCalDate?','yyyy-mm-dd')+"+ day+ "-60 ";

			strSql = strSql + strCondition;
			logger.debug("TTTTT=========" + strSql);
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(strSql);
			sqlbv1.put("mCalDate", mCalDate);
			SSRS tSSRS = new SSRS();
			logger.debug("我要开始运行了  mDays=" + mDays + "    day=" + day);
			tSSRS = tExeSQL.execSQL(sqlbv1);
			if (tSSRS.getMaxRow() == 0) {
				count++;
				logger.debug("没有数据 day=" + day);
				if (count == mDays) {
					logger.debug("查询中没有任何的值");
					mErrors.copyAllErrors(tSSRS.mErrors);
					buildError("getPrintData", "没有查询到相关数据！");
					return false;
				} else {
					continue;
				}
			}

			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				strArr = new String[9];
				for (int j = 1; j <= tSSRS.MaxCol; j++) {
					strArr[j - 1] = tSSRS.GetText(i, j);

					if (j == 5) {
						SumMoney = SumMoney
								+ Double.parseDouble(tSSRS.GetText(i, j));
					}
				}
				tlistTable.add(strArr);
			}
		}
		// strArr = new String[3];
		// strArr[0] = "Risk";
		// strArr[1] = "Money";
		// strArr[2] = "Mult";
		String tSumMoney = String.valueOf(SumMoney);
		if (mManageCom != null && !mManageCom.equals("")) {
			String nsql = "select Name from LDCom where ComCode='" + "?mManageCom?"
					+ "'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(nsql);
			sqlbv2.put("mManageCom", mManageCom);
			mSelString = " 机构：" + tExeSQL.getOneValue(sqlbv2);
		}
		if (mAgentCode != null && !mAgentCode.equals("")) {
			String nsql = "select Name from Laagent where agentCode='"
					+ "?mAgentCode?" + "'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(nsql);
			sqlbv3.put("mAgentCode", mAgentCode);
			mSelString += "   业务员：" + tExeSQL.getOneValue(sqlbv3);
		}
		if (mBank != null && !mBank.equals("")) {
			String nsql = "select Name from lacom where Bankcode='" + "?mBank?"
					+ "'";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(nsql);
			sqlbv4.put("mBank", mBank);
			mSelString += "   银行：" + tExeSQL.getOneValue(sqlbv4);
		}
		StrTool tSrtTool = new StrTool();
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		texttag.add("SelCondition", mSelString);
		texttag.add("SysDate", SysDate);
		texttag.add("SumMoney", tSumMoney);

		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("BeingabateBill.vts", "PRINT"); // 最好紧接着就初始化xml文档
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		strArr = new String[9];
		xmlexport.addListTable(tlistTable, strArr);
		// xmlexport.outputDocumentToFile("e:\\", "atest"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}
}
