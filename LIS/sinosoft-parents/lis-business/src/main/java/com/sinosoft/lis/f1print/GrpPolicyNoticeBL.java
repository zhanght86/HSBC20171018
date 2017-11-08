package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Hashtable;

import com.sinosoft.lis.db.LCPolOtherDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.vschema.LCPolOtherSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class GrpPolicyNoticeBL {
private static Logger logger = Logger.getLogger(GrpPolicyNoticeBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */

	private GlobalInput mGlobalInput = new GlobalInput();

	private String mContNo = "";

	private String mContType = "";

	public GrpPolicyNoticeBL() {
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

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "8621";
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();

		tTransferData.setNameAndValue("ContNo", "335003");
		tTransferData.setNameAndValue("ContType", "335");

		tVData.addElement(tG);
		tVData.addElement(tTransferData);
		// tVData.addElement(tTransferData);
		GrpPolicyNoticeBL tGrpPolicyNoticeBL = new GrpPolicyNoticeBL();
		//
		if (!tGrpPolicyNoticeBL.submitData(tVData, "PRINT")) {
			logger.debug("失败");

		} else {
			logger.debug("成功");
		}
		// ExeSQL t = new ExeSQL();
		// SSRS t1 = new SSRS();
		// String sql = "select password from laagent where agentcode =
		// '00000057'";
		// t1 = t.execSQL(sql);
		// logger.debug(t1.getMaxRow());
		// try{
		// logger.debug("======="+tGrpPolicyNoticeBL.getFormatSSRS(t1,1,1));
		// }
		// catch(Exception ex)
		// {ex.printStackTrace();}
		// Hashtable ts = new Hashtable();
		// ts.put("335p1", "arg1");
		//
		// logger.debug(tGrpPolicyNoticeBL.getFormatStr(t.get("eyt")));
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
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		// 得到打印需要的数据保单号 ,
		TransferData tTransferData = new TransferData();
		tTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mContNo = (String) tTransferData.getValueByName("ContNo");
		logger.debug("=======保单号======" + mContNo);
		mContType = (String) tTransferData.getValueByName("ContType");
		logger.debug("=======打印类型模版======" + mContType);
		if (mContNo == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		if (mContType == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
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

		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String tStrSql = "  select a.appntname , a.contno , a.insuredname , "
				+ "nvl((select name from lcbnf c  where c.contno = a.contno ),''), "
				+ "b.amnt,b.signdate ,a.managecom , a.appntno , a.polapplydate , b.prem ,b.agentcode "
				+ "from lccont a, lcpol b " + "where a.contno = '" + mContNo
				+ "' " + "and a.appflag = '1' " + "and a.contno = b.contno "
				+ "and a.managecom like '" + mGlobalInput.ManageCom + "%' "
				+ "and a.grpcontno = '00000000000000000000'";

		tSSRS = tExeSQL.execSQL(tStrSql);
		if (tSSRS.MaxRow == 0) {
			buildError("getPrintData", "查无此保单详细数据");
			return false;
		}

		// 投保人姓名
		String tAppntNo = "";
		tAppntNo = tSSRS.GetText(1, 1);
		// 保险单号
		String tGrpContNo = "";
		tGrpContNo = tSSRS.GetText(1, 2);
		// 被保人姓名
		String tInsureName = "";
		tInsureName = tSSRS.GetText(1, 3);
		// 受益人姓名
		String tDnfName = "";
		tDnfName = tSSRS.GetText(1, 4);
		// 保险金额
		String SumMoney = tSSRS.GetText(1, 5);
		SumMoney = getFormatDou(SumMoney);
		SumMoney = new DecimalFormat("0.00").format(Double
				.parseDouble(SumMoney));
		// 出单日期
		String SignDate = "";
		SignDate = tSSRS.GetText(1, 6);
		// 公司地址
		SSRS tpSSRS = new SSRS();
		ExeSQL tpExeSQL = new ExeSQL();
		String tpStr = "select address , phone from ldcom where comcode  = '"
				+ tSSRS.GetText(1, 7).trim() + "'";
		tpSSRS = tpExeSQL.execSQL(tpStr);
		boolean tEx = false;
		if (tpSSRS.getMaxRow() > 0)
			tEx = true;
		String ManageComAddress = "";
		if (tEx)
			ManageComAddress = tpSSRS.GetText(1, 1);
		// 服务电话

		String ServicePhone = "";
		if (tEx)
			ServicePhone = tpSSRS.GetText(1, 2);

		// 投保人身份证号码
		String tSQL = "select idno from lcappnt " + "where appntno = '"
				+ tSSRS.GetText(1, 8) + "' and contno = '" + tGrpContNo + "' ";
		tpSSRS = tpExeSQL.execSQL(tSQL);
		String AppntNoID = "";
		AppntNoID = getFormatSSRS(tpSSRS, 1, 1);

		// 被保人身份证号码
		tSQL = "select name,idno,case when sex ='0' then '男' when sex = '1' then '女' else "
				+ " '不明' end  from lcinsured where contno = '"
				+ tGrpContNo
				+ "'";
		tpSSRS = tpExeSQL.execSQL(tSQL);
		String GrpNameID = "";
		GrpNameID = getFormatSSRS(tpSSRS, 1, 2);
		// 被保人性别
		String AppntSex = "";
		AppntSex = getFormatSSRS(tpSSRS, 1, 3);

		// 受益人身份证号码
		tSQL = "select idno  from lcbnf c  where c.contno = '" + tGrpContNo
				+ "'";
		tpSSRS = tpExeSQL.execSQL(tSQL);
		String dnfNameID = "";
		dnfNameID = getFormatSSRS(tpSSRS, 1, 1);

		// ==========以下调用另外的方法，来获得其中详细的信息==================
		Hashtable tHashtable = new Hashtable();
		tHashtable = getContNoInfo(tGrpContNo);

		if (tHashtable == null) {
			return false;
		}
		// 麻醉日期
		String NarcosisDate = "";
		NarcosisDate = getFormatStr(String.valueOf(tHashtable.get("p3")));
		// 麻醉方式
		String NarcosisMode = "";
		NarcosisMode = getFormatStr(String.valueOf(tHashtable.get("p1")));
		// 就诊医院
		String PolicyHos = "";
		PolicyHos = getFormatStr(String.valueOf(tHashtable.get("p2")));
		logger.debug("++++============================================+++");

		// 投保日期
		String PolicyDate = tSSRS.GetText(1, 9);

		// 保险金额(中文)
		String CHISumMoney = "";
		CHISumMoney = PubFun.getChnMoney(Double.parseDouble(getFormatDou(tSSRS
				.GetText(1, 5))));
		// 保险费
		String premSumMoney = "";
		premSumMoney = getFormatDou(tSSRS.GetText(1, 10));
		premSumMoney = new DecimalFormat("0.00").format(Double
				.parseDouble(premSumMoney));
		// 保险费(中文)
		String premCHISumMoney = PubFun.getChnMoney(Double
				.parseDouble(getFormatDou(tSSRS.GetText(1, 10))));
		// 代理人
		tSQL = "select name from laagent where agentcode = '"
				+ tSSRS.GetText(1, 11) + "'";
		tpSSRS = tpExeSQL.execSQL(tSQL);
		String AgentName = "";
		AgentName = getFormatSSRS(tpSSRS, 1, 1);

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		// 根据不同的险种来设定每个不同调用的模版
		String tVts = "Grp" + mContType.trim() + "policy.vts";
		xmlexport.createDocument(tVts, "PRINT"); // 最好紧接着就初始化xml文档

		texttag.add("LCGrpCont.AppntNo", tAppntNo); // 投保人姓名
		texttag.add("LCGrpCont.GrpContNo", tGrpContNo); // 保险单号
		texttag.add("LCGrpCont.InsureName", tInsureName); // 被保人姓名
		texttag.add("LCGrpAppnt.dnfName", tDnfName);// 受益人姓名
		texttag.add("SumMoney", SumMoney); // 保险金额
		texttag.add("LCGrpCont_SignDate", SignDate); // 出单日期
		texttag.add("ManageComAddress", ManageComAddress); // 公司地址
		texttag.add("ServicePhone", ServicePhone); // 服务电话
		texttag.add("SystemDate", PubFun.getCurrentDate());

		texttag.add("AppntNoID", AppntNoID);// 投保人身份证号码
		texttag.add("GrpNameID", GrpNameID);// 被保人身份证号码
		texttag.add("dnfNameID", dnfNameID);// 受益人身份证号码
		texttag.add("NarcosisDate", NarcosisDate);// 麻醉日期
		texttag.add("NarcosisMode", NarcosisMode);// 麻醉方式
		texttag.add("PolicyDate", PolicyDate);// 投保日期
		texttag.add("PolicyHos", PolicyHos);// 就诊医院
		texttag.add("CHISumMoney", CHISumMoney);// 保险金额(中文)
		texttag.add("premSumMoney", premSumMoney);// 保险费
		texttag.add("premCHISumMoney", premCHISumMoney);// 保险费(中文)
		texttag.add("AgentName", AgentName);// 代理人
		texttag.add("AppntSex", AppntSex);// 被保人性别
		// texttag.add("ESumPrem", new DecimalFormat("0.00")
		// .format(tCountRisk));

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		// xmlexport.outputDocumentToFile("D:\\", "atest"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

	private String getFormatSSRS(SSRS str, int rows, int cols) {
		if (str.getMaxRow() > 0) {
			if (str == null)
				return " ";
			if (str.equals("null") || str.equals(""))
				return " ";

			return str.GetText(rows, cols);
		} else {
			return " ";
		}
	}

	private String getFormatDou(String str) {
		if (str == null)
			return "0";
		if (str.equals("null") || str.equals(""))
			return "0";
		return str;
	}

	private String getFormatStr(String str) {
		if (str == null)
			return "";
		if (str.equals("null") || str.equals(""))
			return "";
		return str;
	}

	// 获得每张保单中最详细的医院信息
	private Hashtable getContNoInfo(String strContNo) {
		LCPolOtherDB tLCPolOtherDB = new LCPolOtherDB();
		LCPolOtherSet tLCPolOtherSet = new LCPolOtherSet();
		tLCPolOtherDB.setContNo(strContNo);
		tLCPolOtherSet = tLCPolOtherDB.query();
		if (tLCPolOtherSet.size() == 0) {
			buildError("getPrintData", "查无此保单治疗详细记录");
			return null;
		}
		Hashtable tHashtable = new Hashtable(); // 装载数据，完成往其中的打印页中展现数据。

		if (mContType.trim().equals("335")) {
			// P1 麻醉方式
			tHashtable.put("p1", getExtInfo(strContNo, "anesthetic", "p1"));
			// P2 治疗医院
			tHashtable.put("p2", tLCPolOtherSet.get(1).getP2());
			// P3 治疗日期
			tHashtable.put("p3", tLCPolOtherSet.get(1).getP3());

		} else if (mContType.trim().equals("336")) {
			// P1 保险类别
			tHashtable.put("p1", getExtInfo(strContNo, "cardrisktype", "p1"));
			// P2 治疗方式
			tHashtable.put("p2", getExtInfo(strContNo, "curetype", "p2"));
			// P3 治疗医院
			tHashtable.put("p3", tLCPolOtherSet.get(1).getP3());
			// P4 入院日期
			tHashtable.put("p4", tLCPolOtherSet.get(1).getP4());

		} else if (mContType.trim().equals("337")) {
			// P1 治疗方式
			tHashtable.put("p1", getExtInfo(strContNo, "curetype", "p1"));
			// P2 治疗医院
			tHashtable.put("p2", tLCPolOtherSet.get(1).getP2());
			// P3 入院日期
			tHashtable.put("p3", tLCPolOtherSet.get(1).getP3());

		} else {
			buildError("getPrintData", "传入后台数据有误，请重新核对");
			return null;
		}
		return tHashtable;
	}

	private String getExtInfo(String strContNo, String tCodeType, String pX) {
		ExeSQL tExeSQL = new ExeSQL();// 用于在查询结果中查询详细描述信息，正确展现给用户。
		SSRS tSSRS = new SSRS();
		String tSQL = "";
		tSQL = "select a.codename  from ldcode a , lcpolother b where trim(a.code) = b."
				+ pX
				+ " "
				+ "and a.codetype = '"
				+ tCodeType
				+ "' and contno = '" + strContNo + "'";
		logger.debug("=====查询额外信息SQL=====" + tSQL);
		tSSRS = tExeSQL.execSQL(tSQL);
		if (tSSRS.getMaxRow() > 0)
			return getFormatStr(tSSRS.GetText(1, 1));
		else
			return " ";

	}

}
