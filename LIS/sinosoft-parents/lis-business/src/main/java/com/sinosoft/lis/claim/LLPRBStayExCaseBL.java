package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LLClaimDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.lis.schema.LLAccidentSchema;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.schema.LLClaimUserSchema;
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
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 待审批案件清单打印，模板为LLPRBStayExCase.vts
 * </p>
 * 由于二期不能实现领导待审批任务的提醒功能，需要增加一个待审批案件清单 统计界面：审批权限级别（起、始） 表头：报表名称、统计条件、统计日期
 * 数据项：赔案号、出险人、事故日期、出险日期、理算金额、审批级别、审核人、审核时间、审核机构 算法：在指定的审批权限级别内的待审批案件
 * 排序：审批级别、审核时间、赔案号 希望也能输出到Excel
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author niuzj 20050908
 * @version 1.0
 */

public class LLPRBStayExCaseBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRBStayExCaseBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	// 向后台提交,后面传输数据的容器
	private VData mInputData = new VData();
	// 返回前台的数据包,往界面传输数据的容器
	private VData mResult = new VData();
	private MMap mMMap = new MMap();

	private TransferData mTransferData = new TransferData();

	private String mManageCom = ""; // 管理机构
	private String mSUWLevel = ""; // 初始审批权限级别
	private String mEUWLevel = ""; // 终止审批权限级别
	private String mNCLType = ""; // 申请类型
	private String mCurrentDate = PubFun.getCurrentDate(); // 系统日期
	private String mCurrentTime = PubFun.getCurrentTime(); // 系统时间

	public LLPRBStayExCaseBL() {
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
		logger.debug("----------待审批案件清单LLPRBStayExCaseBL打印测试开始----------");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		// 校验检查外部传入的数据
		if (!checkInputData()) {
			return false;
		}
		// 处理业务数据
		if (!dealData()) {
			return false;
		}
		// 准备输出数据
		if (!prepareOutputData()) {
			return false;
		}
		// 提交数据
		if (!pubSubmit()) {
			return false;
		}

		logger.debug("-----------待审批案件清单LLPRBStayExCaseBL打印测试结束----------");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		mInputData = cInputData; // 得到外部传入的数据,将数据备份到本类中

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		mSUWLevel = (String) mTransferData.getValueByName("SUWLevel"); // 初始审批权限级别
		mEUWLevel = (String) mTransferData.getValueByName("EUWLevel"); // 终止审批权限级别
		mNCLType = (String) mTransferData.getValueByName("NCLType"); // 申请类型
		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		tXmlExport.createDocument("LLPRBStayExCase.vts", ""); // 所用模板名称
		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();

		// 定义列表标题，共9列
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

		logger.debug("********************************************");
		logger.debug("---以下 查询列表$*/STAYCASE/ROW/COL内容，并为列表赋值--");

		// 申请类型判断
		String tApplType = mNCLType.equals("1") ? " and exists (select 'X' from llregister where b.clmno=llregister.rgtno and llregister.rgtobj='1') "
				: " and exists (select 'X' from llregister where b.clmno=llregister.rgtno and llregister.rgtobj='2') ";
		// 算法：在指定的审批权限级别内的待审批案件
		// 排序：审批级别、审核时间、赔案号
		String mClmNoSQL = " select a.missionprop1,b.auditdate,a.missionprop10,a.missionprop4, "
				+ " a.missionprop6,a.missionprop13,a.missionprop20,b.AuditConclusion"
				+ " from lwmission a,llclaimuwmain b where 1=1"
				+ " and a.activityid='0000005055'"
				+ " and a.missionprop2='40'" // 所有的待审批案件
				+ " and (a.missionprop10 between '"
				+ "?suwlevel?"
				+ "' and '"
				+ "?euwlevel?"
				+ "')" // 在两个审批权限级别之间
				+ " and a.missionprop20 like concat('"
				+ "?mngcom?"
				+ "','%') "
				+ " and a.missionprop1 =trim(b.clmno)" + tApplType
				// + " and trim(a.missionprop1)=trim(c.clmno)" //获取理算金额
				+ " order by a.missionprop10,b.auditdate,a.missionprop1";

		logger.debug("----------查询赔案号语句为-----------" + mClmNoSQL);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(mClmNoSQL);
		sqlbv.put("suwlevel", mSUWLevel);
		sqlbv.put("euwlevel", mEUWLevel);
		sqlbv.put("mngcom", mManageCom);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv);
		// 新建一个ListTable的实例,给模板的每一列赋值
		ListTable tListTable = new ListTable();
		tListTable.setName("STAYCASE");

		int tStatCount = 0; // 用来统计符合条件的记录的数量

		// 循环处理每一条打印记录
		for (int i = 1; i <= tSSRS.MaxRow; i++) {
			logger.debug("-------------第 " + i + " 次循环开始----------------");
			String tClmNo = tSSRS.GetText(i, 1); // 赔案号a.missionprop1
			String tAuditDate = tSSRS.GetText(i, 2); // 审核时间b.auditdate
			String tPopedom = tSSRS.GetText(i, 3); // 审批权限a.missionprop10
			String tCustomerName = tSSRS.GetText(i, 4); // 出险人a.missionprop4
			String tAccidentDate = tSSRS.GetText(i, 5); // 出险日期a.missionprop6
			String ttAuditer = tSSRS.GetText(i, 6); // 审核人a.missionprop13
			String ttMngCom = tSSRS.GetText(i, 7); // 管理机构a.missionprop20
			String tAuditCon = tSSRS.GetText(i, 8); // 审核结论代码
			int a = Integer.parseInt(tAuditCon);// 转化为int型
			String tAuditConName = "";// 审核结论
			switch (a) {
			case 0:
				tAuditConName = "给付";
				break;
			case 1:
				tAuditConName = "拒付";
				break;
			case 2:
				tAuditConName = "客户撤案";
				break;
			case 3:
				tAuditConName = "公司撤案";
				break;
			}
			String strSQLq = " select standpay,realpay,declinepay from llclaim "
					+ " where clmno='" + "?clmno?" + "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(strSQLq);
			sqlbv1.put("clmno", tClmNo);
			SSRS tSSRSq = new SSRS();
			tSSRSq = cusExeSQL.execSQL(sqlbv1);
			String standpay = tSSRSq.GetText(1, 1);// 给付保险金
			String realpay = tSSRSq.GetText(1, 2);// 应付金额
			String declinepay = tSSRSq.GetText(1, 3);// 拒付保险金

			// 理算金额
			LLClaimSchema tllclaimSchema = new LLClaimSchema();
			LLClaimDB tLLClaimDB = new LLClaimDB();
			tLLClaimDB.setClmNo(tClmNo);
			tLLClaimDB.getInfo();
			tllclaimSchema = tLLClaimDB.getSchema();
			// double tBalancePay = tllclaimSchema.getBalancePay();
			// modifyde by niuzj,2005-11-04
			// 如果理算金额为0,则该案件应该是拒付案件
			double tBalancePay = tllclaimSchema.getStandPay();

			// 意外事故发生日期
			LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema();
			tLLAccidentSchema = tLLPRTPubFunBL.getLLAccident(tClmNo);
			String tAccDate = tLLAccidentSchema.getAccDate();

			// 审核人姓名
			LLClaimUserSchema tLLClaimUserSchema = new LLClaimUserSchema();
			tLLClaimUserSchema = tLLPRTPubFunBL.getLLClaimUser(ttAuditer);
			String tAuditer = tLLClaimUserSchema.getUserName();

			// 审核机构名称
			LDComSchema tLDComSchema = new LDComSchema();
			LDComDB tLDComDB = new LDComDB();
			tLDComDB.setComCode(ttMngCom);
			tLDComDB.getInfo();
			tLDComSchema = tLDComDB.getSchema();
			String tMngCom = tLDComSchema.getName();

			// 为模板的每一列赋值
			String[] stra = new String[14];
			stra[0] = tClmNo; // 赔案号
			stra[1] = tCustomerName; // 出险人
			stra[2] = tAccDate; // 意外发生日期
			stra[3] = tAccidentDate; // 出险日期
			stra[4] = String.valueOf(tBalancePay); // 理算金额
			stra[5] = tPopedom; // 审批级别
			stra[6] = tAuditer; // 审核人
			stra[7] = tAuditDate; // 审核时间
			stra[8] = tMngCom; // 审核机构
			stra[9] = tAuditConName; // 审核结论
			stra[10] = standpay; // 给付保险金
			stra[11] = declinepay; // 拒付保险金
			stra[12] = realpay; // 应付金额
			stra[13] = ttMngCom; // 机构代码

			tListTable.add(stra);
			tStatCount = tStatCount + 1; // 记录数量加1

			logger.debug("-------------第 " + i + " 次循环结束----------------");
		}
		logger.debug("----------以下查询是为单个变量赋值-----------");
		// 统计时间,默认为系统时间
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		// 申请类型: $=/CSApplType$
		String mNCLTypeName = mNCLType.trim().equals("1") ? "个人" : "团体";
		// tTextTag.add("RICDApplType",mNCLTypeName);
		String tStatCondition = "审批权限级别:" + mSUWLevel + "至" + mEUWLevel
				+ "                " + " 申请类型：" + mNCLTypeName; // 统计条件

		// 管理机构名称 $=/StatMng$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mManageCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();

		tTextTag.add("StatCondition", tStatCondition); // 统计条件：$/=StatCondition$
		tTextTag.add("StatMng", tMngCom);
		tTextTag.add("StatDate", SysDate); // 统计日期：$=/StatDate$
		tTextTag.add("StatCount", tStatCount); // 统计数量：$=/StatCount$

		logger.debug("---以下 tXmlExport实例添加一个列表和动态文本标签--");
		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		// /*##################### 后台调试部分，生成临时文件############################*/
		// String strTemplatePath="E:/lis/ui/f1print/NCLtemplate/";
		// String strVFPathName=strTemplatePath+"test.vts"; //生成临时文件名
		// CombineVts tcombineVts = null;
		// tcombineVts = new CombineVts(tXmlExport.getInputStream(),
		// strTemplatePath);
		// ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		// tcombineVts.output(dataStream);
		// AccessVtsFile.saveToFile(dataStream, strVFPathName);

		logger.debug("********************************************");
		logger.debug("---以下 将tXmlExport打包，返回前台--");

		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);
		logger.debug("xmlexport=" + tXmlExport);

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(mMMap);
		return true;
	}

	/**
	 * 提交数据
	 * 
	 * @return
	 */
	private boolean pubSubmit() {
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPRBStayExCaseBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			//
			this.mErrors.addOneError(tError);
			return false;
		}
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

	// test
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86";
		tG.ManageCom = "86";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ManageCom", "86");
		tTransferData.setNameAndValue("SUWLevel", "B01");// 初始审批权限级别
		tTransferData.setNameAndValue("EUWLevel", "B09");// 终止审批权限级别

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);

		LLPRBStayExCaseBL tLLPRBStayExCaseBL = new LLPRBStayExCaseBL();
		if (tLLPRBStayExCaseBL.submitData(tVData, "") == false) {
			logger.debug("----------理赔清单打印：待审批案件清单出错---------------");
		}
	}
}
