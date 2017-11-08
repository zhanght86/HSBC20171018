package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDComSchema;
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
 * Title: 理赔清单打印：归档核对清单---------LLPRBReturnHomeCheck.vts
 * </p>
 * <p>
 * Description: 归档核对清单 统计界面：统计机构（总公司、某分公司、某中支、某支公司，默认为操作用户所在的权限机构层面）、立案起止日期
 * 数据项：扫描批次号、业务类别、赔案号、立案日期、出险人、保单号、审核人代码和姓名 算法：统计指定机构在选定的起止日期内立案且已经结案的案件清单
 * 排序：立案时间、赔案号、保单号 输出到Excel
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Niuzj,2005-11-07
 * @version 1.0
 */

public class LLPRBReturnHomeCheckBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRBReturnHomeCheckBL.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private MMap mMMap = new MMap();

	private TransferData mTransferData = new TransferData();
	private GlobalInput mG = new GlobalInput();
	private String mStartDate = ""; // 立案起期
	private String mEndDate = ""; // 立案止期
	private String mStatiCode = ""; // 统计机构
	private String mNCLType = ""; // 申请类型

	public LLPRBReturnHomeCheckBL() {

	}

	/**
	 * 主函数------主要用于设置数据，调试程序入口
	 * 
	 * @param:
	 * @return: 数据处理后信息
	 */

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86";
		tG.ManageCom = "86";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StartDate", "2005-8-6");// 开始日期
		tTransferData.setNameAndValue("EndDate", "2005-11-07"); // 结束日期
		tTransferData.setNameAndValue("StatiCode", "8632");// 统计机构

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);

		LLPRBReturnHomeCheckBL tLLPRBReturnHomeCheckBL = new LLPRBReturnHomeCheckBL();
		if (tLLPRBReturnHomeCheckBL.submitData(tVData, "") == false) {
			logger.debug("----------理赔清单打印：归档核对清单出错---------------");
		}
	}

	/**
	 * 提交数据的公共方法
	 * 
	 * @param: cInputData--- 输入的数据 cOperate--- 数据操作符
	 * @return: 布尔值，成功返回“true”，失败返回“false”
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("-----理赔清单打印：归档核对清单开始---------LLPRBReturnHomeCheckBL---");
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
		logger.debug("-----理赔清单打印：归档核对清单结束----LLPRBReturnHomeCheckBL---");

		return true;
	}

	/**
	 * 取传入参数信息
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		mInputData = cInputData;// 得到外部传入的数据,将数据备份到本类中
		mG = ((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mStartDate = (String) mTransferData.getValueByName("StartDate"); // 立案起期
		mEndDate = (String) mTransferData.getValueByName("EndDate"); // 立案止期
		mStatiCode = (String) mTransferData.getValueByName("StatiCode"); // 统计机构
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

		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();

		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		tXmlExport.createDocument("LLPRBReturnHomeCheck.vts", "");

		// 新建一个ListTable的实例
		ListTable tListTable = new ListTable();
		tListTable.setName("RHC");
		// 定义列表标题
		String[] Title = new String[10];
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

		logger.debug("---以下 查询列表$*/RHC/ROW/COL内容，并为列表赋值--");
		String strSql = "";

		/**
		 * 重写查询SQL，原来这个SQL将不再使用，niuzj,2005-12-10 strSql=" select distinct
		 * c.scanno,a.rgtno,a.rgtdate,b.customerno,d.polno,e.auditper,a.mngcom " +"
		 * from llregister a,llcase b,es_doc_main c,llclaimpolicy
		 * d,llclaimuwmain e " +" where 1=1 " +" and a.rgtno = b.caseno " +" and
		 * trim(a.rgtno) = c.doccode " +" and a.rgtno = d.clmno " +" and a.rgtno =
		 * e.clmno " +" and a.mngcom like '"+mStatiCode+"%' " +" and
		 * c.busstype='LP' " +" and (a.rgtdate between '"+mStartDate+"' and
		 * '"+mEndDate+"' ) " //by niuzj,2005-12-09,要统计已经结案的案件 +" and
		 * a.clmstate='60' " +" order by a.rgtdate,a.rgtno,d.polno ";
		 */

		/**
		 * 原来SQL只能统计已经立案确认并已经结案，而且有扫描件的案件 现在要求不光要统计出有扫描件的结案案件，显示出它们的扫描批次号，
		 * 而且对于没有扫描件的结案案件也要统计出来，但不必显示出扫描批次号 Modifyed by niuzj,2005-12-10
		 */
		String applType = mNCLType.trim().equals("1") ? " and a.rgtobj='1' "
				: " and a.rgtobj='2' ";
		strSql = " select distinct a.rgtno,a.rgtdate,b.customerno,d.polno,e.auditper,a.mngcom, "
				+ " (select c.name from ldperson c where c.customerno=b.customerno), "
				+ " (select f.name from ldcom f where f.comcode=a.mngcom) "
				+ " from llregister a,llcase b,llclaimpolicy d,llclaimuwmain e "
				+ " where 1=1 "
				+ " and a.rgtno = b.caseno "
				+ " and a.rgtno = d.clmno "
				+ " and a.rgtno = e.clmno "
				+ applType
				+ " and a.mngcom like concat('"
				+ "?staticode?"
				+ "','%') "
				+ " and (a.rgtdate between '"
				+ "?startdate?"
				+ "' and  '"
				+ "?enddate?" + "') " + " and a.clmstate='60' " // 已经结案的案件
				+ " order by a.rgtdate,a.rgtno,d.polno ";
		// 执行最后的查询SQL,得到符合条件的赔案号
		logger.debug("strSql==" + strSql);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strSql);
		sqlbv.put("staticode", mStatiCode);
		sqlbv.put("startdate", mStartDate);
		sqlbv.put("enddate", mEndDate);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv);
		if (tSSRS.getMaxRow() != 0) {
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				String tClmNo = tSSRS.GetText(i, 1); // 赔案号
				String tRgtDate = tSSRS.GetText(i, 2); // 立案日期

				String tCustNo = tSSRS.GetText(i, 3); // 出险人客户号码
				String tCustName = tSSRS.GetText(i, 7); // 出险人姓名

				String tPolNo = tSSRS.GetText(i, 4); // 保单号

				String tAuditPerCode = tSSRS.GetText(i, 5); // 审核人代码
				LLClaimPubFunBL tLLClaimPubFunBL = new LLClaimPubFunBL();
				String tAuditPerName = tLLClaimPubFunBL
						.getUserName(tAuditPerCode); // 审核人姓名

				String tMngCode = tSSRS.GetText(i, 6); // 立案机构代码
				String tMngName = tSSRS.GetText(i, 8); // 立案机构名称

				// 扫描批次号
				String tSQL = " select distinct t1.scanno from es_doc_main t1,es_doc_relation t2 "
						+ " where 1=1 "
						+ " and t1.doccode=t2.bussno " // 确认该扫描件的确已经上载
						+ " and t1.busstype='LP' "
						+ " and t2.busstype='LP' "
						+ " and t2.bussnotype='23' " // 业务关联号码
						+ " and t1.doccode='" + "?clmno?" + "' ";
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(tSQL);
				sqlbv1.put("clmno", tClmNo);
				ExeSQL tExeSQL = new ExeSQL();
				String tScanNo = tExeSQL.getOneValue(sqlbv1);
				if (tScanNo == null || tScanNo == "" || tScanNo.equals(null)
						|| tScanNo.equals("")) {
					tScanNo = "该赔案没有单证扫描上载";
				} else {
					tScanNo = tScanNo;
				}

				// Added by niuzj,2005-12-13,业务类型
				String tSQL1 = " select (case b.rgtclass when '1' then '个人' when '2' then '团体' else '代理' end) "
						+ " from llregister b where 1=1 "
						+ " and b.rgtno='"
						+ "?clmno?" + "' ";
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(tSQL);
				sqlbv2.put("clmno", tClmNo);
				ExeSQL tExeSQL1 = new ExeSQL();
				String tRgtClass = tExeSQL1.getOneValue(sqlbv2);

				// 给模板中的每一列赋值
				String[] stra = new String[10];
				stra[0] = tMngCode; // 机构代码
				stra[1] = tMngName; // 机构名称
				stra[2] = tScanNo; // 扫描批次号
				stra[3] = tRgtClass;// 业务类型
				stra[4] = tClmNo; // 赔案号
				stra[5] = tRgtDate; // 立案日期
				stra[6] = tCustName; // 出险人
				stra[7] = tPolNo; // 保单号
				stra[8] = tAuditPerCode; // 审核人代码
				stra[9] = tAuditPerName; // 审核人姓名
				tListTable.add(stra);
			}
		} else {
			CError tError = new CError();
			tError.moduleName = "LLPRBReturnHomeCheckBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}

		logger.debug("----------以下 查询，并为 单个变量赋值-----------");
		// 统计日期:$=/StatDate$
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		// 立案起期:$=/RptStaTime$
		String tStartDate = mStartDate;
		// 立案止期:$=/RptEndTime$
		String tEndDate = mEndDate;
		// 统计机构:$=/StatMngcom$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mStatiCode);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();
		// 申请类型
		String applTypeName = mNCLType.trim().equals("1") ? "个人 " : "团体 ";

		tTextTag.add("StatMngcom", tMngCom);
		tTextTag.add("StatDate", SysDate);
		tTextTag.add("RptStaTime", tStartDate);
		tTextTag.add("RptEndTime", tEndDate);
		tTextTag.add("ApplType", applTypeName);

		logger.debug("********************************************");
		logger.debug("---以下 tXmlExport实例添加一个列表和动态文本标签--");
		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		/* ##################### 后台调试部分，生成临时文件############################ */
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
		// tXmlExport.outputDocumentToFile("c:\\", "testHZM"); //输出xml文档到文件
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
			return false;
		}
		return true;

	}

	// 错误处理函数
	public CErrors getErrors() {
		return mErrors;
	}

	// 打包数据---用于向前台返回
	public VData getResult() {
		return mResult;
	}

}
