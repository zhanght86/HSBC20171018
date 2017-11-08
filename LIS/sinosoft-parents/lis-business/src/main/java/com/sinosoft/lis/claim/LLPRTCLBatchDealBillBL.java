/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 批处理打印清单------CLBatchDealBillC00080.vts
 * </p>
 * <P>
 * 需要传入的参数：1、当前用户及其机构；2、理赔单证打印清单参数的Set集合
 * </P>
 * <P>
 * 理赔单证打印清单参数：LOPRTManagerSchema:PrtSeq为打印流水号，OtherNo为赔案号
 * </P>
 * <P>
 * 其中ForMakeDate为开始日期，ActMakeDate为结束日期，StandbyFlag4为客户号
 * </P>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author:
 * @version 1.0
 */
public class LLPRTCLBatchDealBillBL {
private static Logger logger = Logger.getLogger(LLPRTCLBatchDealBillBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private MMap map = new MMap();
	/** 数据操作字符串 */
	private String mOperate;
	private GlobalInput mG = new GlobalInput();
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private String mPrtSeq;// 流水号

	public LLPRTCLBatchDealBillBL() {

	}

	public static void main(String[] args) {
		// GlobalInput tG = new GlobalInput();
		// tG.ComCode="86";
		// tG.ManageCom="86";
		// tG.Operator="001";
		//
		// LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		// tLOPRTManagerSchema.setPrtSeq("0000001351");
		// tLOPRTManagerSchema.setOtherNo("90000001904");
		// tLOPRTManagerSchema.setForMakeDate("2005-08-20");
		// tLOPRTManagerSchema.setActMakeDate("2005-08-22");
		// tLOPRTManagerSchema.setStandbyFlag4("0000566910 ");
		//
		// LOPRTManagerSchema ttLOPRTManagerSchema = new LOPRTManagerSchema();
		// ttLOPRTManagerSchema.setPrtSeq("0000001352 ");
		// ttLOPRTManagerSchema.setOtherNo("90000001225");
		// ttLOPRTManagerSchema.setForMakeDate("2005-08-20");
		// ttLOPRTManagerSchema.setActMakeDate("2005-08-22");;
		// ttLOPRTManagerSchema.setStandbyFlag4("0000567120");
		//
		// LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();
		// tLOPRTManagerSet.add(tLOPRTManagerSchema);
		// tLOPRTManagerSet.add(ttLOPRTManagerSchema);
		//
		// VData tVData=new VData();
		// tVData.add(tLOPRTManagerSet);
		// tVData.add(tG);
		//
		// LLPRTCLBatchDealBillBL tLLPRTCLBatchDealBillBL = new
		// LLPRTCLBatchDealBillBL();
		// if (!tLLPRTCLBatchDealBillBL.submitData(tVData, ""))
		// {
		// logger.debug("打印出错");
		// }

	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("--start getInputData()");
		// 获取页面信息
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);// 按类取值
		mLOPRTManagerSet = (LOPRTManagerSet) mInputData.getObjectByObjectName(
				"LOPRTManagerSet", 0);// 按类取值

		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		logger.debug("----------begin checkInputData----------");
		try {
			// 非空字段检验
			return true;
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLPRTCLBatchDealBillBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "在校验输入的数据时出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		tXmlExport.createDocument("CLBatchDealBillC00080.vts", "");

		// 查询“制表单位-----ManageComName”，“统计日期--StartDate、EndDate”，“打印人---Operator”
		String tManageCom = mG.ManageCom;
		String tManageComSql = "select name from ldcom where comcode='"
				+ "?mngcom?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tManageComSql);
		sqlbv1.put("mngcom", tManageCom);
		ExeSQL tManageComExeSQL = new ExeSQL();
		String tManageComName = tManageComExeSQL.getOneValue(sqlbv1);

		String tOperator = mG.Operator;
		String tOperatorSql = "select * from llclaimuser where usercode='"
				+ "?usercode?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tOperatorSql);
		sqlbv2.put("usercode", tOperator);
		ExeSQL tOperatorExeSQL = new ExeSQL();
		String tOperatorName = tOperatorExeSQL.getOneValue(sqlbv2);

		String tStartDate = mLOPRTManagerSet.get(1).getForMakeDate();
		String tEndDate = mLOPRTManagerSet.get(1).getActMakeDate();

		logger.debug("#########################################");
		logger.debug("-------------以下为单个的变量赋值------------");
		tTextTag.add("ManageComName", tManageComName);// 制表单位--ManageComName
		tTextTag.add("StartDate", tStartDate);// 统计日期--开始日期$=/StartDate$
		tTextTag.add("EndDate", tEndDate);// 统计日期--截至日期 $=/EndDate$
		tTextTag.add("Operator", tOperatorName);// 打印人---Operator

		logger.debug("#########################################");
		logger.debug("-------------以下为$*/BILL/ROW/COL变量赋值------------");
		ListTable tBillListTable = new ListTable();
		tBillListTable.setName("BILL");
		String[] tBillListTitle = new String[8];
		tBillListTitle[0] = "";
		tBillListTitle[1] = "";
		tBillListTitle[2] = "";
		tBillListTitle[3] = "";
		tBillListTitle[4] = "";
		tBillListTitle[5] = "";
		tBillListTitle[6] = "";
		tBillListTitle[7] = "";
		for (int i = 1; i <= mLOPRTManagerSet.size(); i++) {
			LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
			tLOPRTManagerSchema.setSchema(mLOPRTManagerSet.get(i).getSchema());

			LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();

			String[] strBill = new String[8];
			/** **赔案号*** */
			String tClmNo = tLOPRTManagerSchema.getOtherNo();//
			/** **客户号*** */
			String tCusNo = tLOPRTManagerSchema.getStandbyFlag4();//
			/** **赔款金额*** */
			String tRealPaySql = "select realpay from llclaim  where "
					+ " clmno= '" + "?clmno?" + "'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tRealPaySql);
			sqlbv3.put("clmno", tClmNo);
			ExeSQL tRealPayExeSQL = new ExeSQL();
			String tRealPay = tRealPayExeSQL.getOneValue(sqlbv3);
			/** **理赔类型*** */
			String tClaimType = tLLPRTPubFunBL.getSLLReportReason(tClmNo,
					tCusNo);//
			/** 出险人姓名** */
			String tCusNameSql = "select a.name from ldperson a where "
					+ "a.customerno = '" + "?cusno?" + "'";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(tCusNameSql);
			sqlbv4.put("cusno", tCusNo);
			ExeSQL tCusNameExeSQL = new ExeSQL();
			String tCusName = tCusNameExeSQL.getOneValue(sqlbv4);
			// /****申请人姓名---申请人地址---邮编---申请人联系电话----（从立案表查找）******/
			// LLRegisterSchema tLLRegisterSchema= new LLRegisterSchema();
			// tLLRegisterSchema= tLLPRTPubFunBL.getLLRegister(tClmNo);
			// String tRgtantName=tLLRegisterSchema.getRgtantName();
			// String tRgtantAddress=tLLRegisterSchema.getRgtantAddress();
			// String tPostCode=tLLRegisterSchema.getPostCode();
			// String tRgtantPhone=tLLRegisterSchema.getRgtantPhone();

			// 将申请人信息全部改为收件人信息,modify by niuzj,2005-10-30
			LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
			tLLRegisterSchema = tLLPRTPubFunBL.getLLRegister(tClmNo);
			String tReciName = tLLRegisterSchema.getReciName();
			String tReciAddress = tLLRegisterSchema.getReciAddress();
			String tReciZip = tLLRegisterSchema.getReciZip();
			String tReciPhone = tLLRegisterSchema.getReciPhone();

			strBill[0] = tClmNo;// 赔案号
			strBill[1] = tRealPay;// 赔款金额
			strBill[2] = tClaimType;// 理赔类型
			strBill[3] = tCusName;// 出险人姓名
			strBill[4] = tReciName;// 收件人姓名
			strBill[5] = tReciAddress; // 收件人地址
			strBill[6] = tReciZip; // 收件人邮编
			strBill[7] = tReciPhone; // 收件人联系电话

			tBillListTable.add(strBill);
		}

		logger.debug("#################################################");
		logger.debug("以下为tXmlExport实例添加添加动态文本标签的数组、及列表");
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}
		// 添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tBillListTable, tBillListTitle);//

		// /*##################### 后台调试部分，生成临时文件############################*/
		// String strTemplatePath="D:/ui/f1print/NCLtemplate/";
		// String strVFPathName=strTemplatePath+"DDDD.vts";
		// CombineVts tcombineVts = null;
		// tcombineVts = new CombineVts(tXmlExport.getInputStream(), strTemplatePath);
		// ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		// tcombineVts.output(dataStream);
		// AccessVtsFile.saveToFile(dataStream, strVFPathName);

		mResult.clear();
		mResult.addElement(tXmlExport);
		logger.debug("xmlexport=" + tXmlExport);

		logger.debug("#######-----------打印数据处理完毕------###########");
		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mInputData.clear();
		return true;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public VData getResult() {
		return mResult;
	}

}
