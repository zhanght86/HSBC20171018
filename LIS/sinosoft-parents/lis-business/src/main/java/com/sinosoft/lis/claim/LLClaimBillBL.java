package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * Title: 理赔清单打印
 * 
 * Description: 个险理赔案件统计清单
 * （一）统计条件：机构、起始日期、结束日期、统计类型。均为必选项，统计类型分为已结案件和未结案件。（不予立案和撤件案件不在该统计范围内）
 * （二）清单显示内容： 1.表头：标题、统计机构、统计区间、统计人、统计日期
 * 2.表格内容：序号、立案号、保单号、险种代码、险种名称、事故者姓名、立案日期、签批同意日期、赔付金额、拒付金额、保单生效日期、出险日期、
 * 出险类型、案件类型、案件结论类型、保项结论类型、案件状态、扫描件标志。
 * 3.统计精确至保项层。一个立案号对应一个序号，同一立案号下涉及多个保项的赔付时，每个保项对应一条显示记录，但归属于同一序号下。
 * 4.案件状态包括："签批同意"、"完成"。统计时点以相应的案件状态为准。 5.案件结论类型包括："给付"和"拒付"，精确至保项层。
 * 
 * Copyright: Copyright (c) 2005
 * 
 * Company:sinosoft
 * 
 * @author mw,2009-04-14
 */

public class LLClaimBillBL implements PrintService {
private static Logger logger = Logger.getLogger(LLClaimBillBL.class);

	public CErrors mErrors = new CErrors();

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();

	/** 往后面传输数据的容器 */
	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();

	private String mOperate = "";

	private GlobalInput mG = new GlobalInput();

	private String mMngCom = ""; // 统计机构

	private String mStatType = ""; // 清单类型

	private String mStartDate = ""; // 起始日期

	private String mEndDate = ""; // 结束日期

	private String mClaimType="";//统计类型

	public LLClaimBillBL() {
	}

	/**
	 * 提交数据的公共方法
	 * 
	 * @param: cInputData--- 输入的数据 cOperate--- 数据操作符
	 * @return: 布尔值，成功返回“true”，失败返回“false”
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("-----理赔清单打印开始---------LLPRBDealORFactPayBL---");
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
		logger.debug("-----理赔清单打印结束----LLPRBDealORFactPayBL---");

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
		mOperate = cOperate;
		mInputData = cInputData;// 得到外部传入的数据,将数据备份到本类中

		mG = ((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData", 0);
		if ("ClaimBill".equals(mOperate)) {
			mMngCom = (String) mTransferData.getValueByName("MngCom"); // 统计机构
			mStatType = (String) mTransferData.getValueByName("StatType"); // 统计类型
			mStartDate = (String) mTransferData.getValueByName("StartDate"); // 统计起期
			mEndDate = (String) mTransferData.getValueByName("EndDate"); // 统计止期
		} else if ("NoRgtBill".equals(mOperate)) {
			mMngCom = (String) mTransferData.getValueByName("MngCom"); // 统计机构
			mStartDate = (String) mTransferData.getValueByName("StartDate"); // 统计起期
			mEndDate = (String) mTransferData.getValueByName("EndDate"); // 统计止期
		} else if ("BackBill".equals(mOperate)) {
			mMngCom = (String) mTransferData.getValueByName("MngCom"); // 统计机构
			mStartDate = (String) mTransferData.getValueByName("StartDate"); // 统计起期
			mEndDate = (String) mTransferData.getValueByName("EndDate"); // 统计止期
		} else if ("InqBill".equals(mOperate)) {
			mMngCom = (String) mTransferData.getValueByName("MngCom"); // 统计机构
			mStartDate = (String) mTransferData.getValueByName("StartDate"); // 统计起期
			mEndDate = (String) mTransferData.getValueByName("EndDate"); // 统计止期
		} else if ("PrepayBill".equals(mOperate)) {
			mMngCom = (String) mTransferData.getValueByName("MngCom"); // 统计机构
			mStartDate = (String) mTransferData.getValueByName("StartDate"); // 统计起期
			mEndDate = (String) mTransferData.getValueByName("EndDate"); // 统计止期
		} else if ("Cancel".equals(mOperate)) {
			mMngCom = (String) mTransferData.getValueByName("MngCom"); // 统计机构
			mStartDate = (String) mTransferData.getValueByName("StartDate"); // 统计起期
			mEndDate = (String) mTransferData.getValueByName("EndDate"); // 统计止期
		} else if ("UWsecond".equals(mOperate)) {
			mMngCom = (String) mTransferData.getValueByName("MngCom"); // 统计机构
			mStartDate = (String) mTransferData.getValueByName("StartDate"); // 统计起期
			mEndDate = (String) mTransferData.getValueByName("EndDate"); // 统计止期
		}  else if ("UserBill".equals(mOperate)) {
			mMngCom = (String) mTransferData.getValueByName("MngCom"); // 统计机构
		}  else if ("EndCaseBill".equals(mOperate)) {
			mMngCom = (String) mTransferData.getValueByName("MngCom"); // 统计机构
			mStartDate = (String) mTransferData.getValueByName("StartDate"); // 统计起期
			mEndDate = (String) mTransferData.getValueByName("EndDate"); // 统计止期
		}
		else if ("NotEndCase".equals(mOperate)) {
			mMngCom = (String) mTransferData.getValueByName("MngCom"); // 统计机构
			mStartDate = (String) mTransferData.getValueByName("StartDate"); // 统计起期
			mEndDate = (String) mTransferData.getValueByName("EndDate"); // 统计止期
			mClaimType= (String) mTransferData.getValueByName("ClaimType"); // 统计类型
		}else if("DelayInterestBill".equals(mOperate)){
			mMngCom = (String) mTransferData.getValueByName("MngCom"); // 统计机构
			mStartDate = (String) mTransferData.getValueByName("StartDate"); // 统计起期
			mEndDate = (String) mTransferData.getValueByName("EndDate"); // 统计止期
		}
		
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
		if ("ClaimBill".equals(mOperate)) {// 理赔案件统计清单
			if (dealClaimBill() == false) {
				return false;
			}
		} else if ("NoRgtBill".equals(mOperate)) {// 理赔不予立案清单
			if (dealNoRgtBill() == false) {
				return false;
			}
		} else if ("BackBill".equals(mOperate)) {// 理赔回退案件清单
			if (dealBackBill() == false) {
				return false;
			}
		} else if ("InqBill".equals(mOperate)) {// 理赔未结调查案件清单
			if (dealInqBill() == false) {
				return false;
			}
		} else if ("PrepayBill".equals(mOperate)) {// 理赔预付案件清单
			if (dealPrepayBill() == false) {
				return false;
			}
		} else if ("Cancel".equals(mOperate)) {// 理赔预付案件清单
			if (dealCancelBill() == false) {
				return false;
			}
		} else if ("UWsecond".equals(mOperate)) {// 理赔预付案件清单
			if (dealUWsecondBill() == false) {
				return false;
			}
		} else if ("UserBill".equals(mOperate)) {// 理赔预付案件清单
			if (dealUserBill() == false) {
				return false;
			}
		} else if ("EndCaseBill".equals(mOperate)) {// 理赔预付案件清单
			if (dealEndCaseBill() == false) {
				return false;
			}
		}
		else if ("NotEndCase".equals(mOperate)) {// 理赔未完结案件清单
			if (dealNotEndCase() == false) {
				return false;
			}
		}else if("DelayInterestBill".equals(mOperate)){
			if(dealDelayInterest()== false){
				return false;
			}
		}

		return true;
	}

	private boolean dealDelayInterest() {

		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		tXmlExport.createDocument("DelayInterestBill.vts", "");

		// 新建一个ListTable的实例
		ListTable tListTable = new ListTable();
		tListTable.setName("DelayInterestBill");
		// 定义列表标题
		String[] Title = new String[5];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";
		

		logger.debug("---以下 查询列表$*/DOF/ROW/COL内容，并为列表赋值--");
	
		String strSQL = "select distinct(a.comcode)  from ldcom a where char_length(a.comcode)=6 and a.comcode like "
			+ "concat('?comcode?','%')" + " order by a.comcode";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strSQL);
		sqlbv.put("comcode", mMngCom);
		ExeSQL kExeSQL = new ExeSQL();
		SSRS kSSRS = new SSRS();
		kSSRS = kExeSQL.execSQL(sqlbv);
		if (kSSRS == null || kSSRS.MaxRow == 0) {
			buildError("dealdata", "没有要统计的数据!");
			return false;
		}   
		String strSQL1 = "select clmno,"+
			 " examdate,"+
			 " (select sum(realpay) from llclaimdetail where clmno = a.clmno),"+
			 " (select (case when sum(pay) is null then 0 else sum(pay) end) "+
					" from ljagetclaim "+
				 " where otherno = a.clmno "+
					" and feefinatype = 'YCLX') "+
					" from llclaimuwmain a "+
					"where examdate >= '"+"?startdate?"+"'"+
					"and examdate <= '"+"?enddate?"+"'"+"and a.examcom like concat('?comcode?','%')";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strSQL);
		sqlbv1.put("comcode", mMngCom);
		sqlbv1.put("startdate", mStartDate);
		sqlbv1.put("enddate", mEndDate);
		   
		logger.debug("查询语句:strSQL==" + strSQL1);

		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv1);

		if (tSSRS.getMaxRow() != 0) {
			//循环管理机构
			for (int i = 1; i <= kSSRS.MaxRow; i++) {
				
				
				
				for(int j=1; j<=tSSRS.MaxRow; j++){
					String[] stra = new String[5];
					stra[0] = j + "";
					stra[1] = "";
					stra[2] = "";
					stra[3] = "0.00";
					stra[4] = "0.00";
					logger.debug("简易案件前6位"+(tSSRS.GetText(j, 1).substring(0, 6)));
					logger.debug("简易案件号"+(tSSRS.GetText(j, 1)));
					logger.debug("kSSRS.GetText(i, 1)"+kSSRS.GetText(i, 1));
					if(kSSRS.GetText(i, 1).equals(tSSRS.GetText(j, 1).substring(0, 6))){
						
						stra[1] = tSSRS.GetText(j, 1); //立案号
						stra[2] = tSSRS.GetText(j, 2); //审批同意日期
						stra[3] = String.valueOf(tSSRS.GetText(j, 3));//赔付金额
						logger.debug("tSSRS.GetText(j, 4)"+tSSRS.GetText(j, 4));//赔付金额		
						if(tSSRS.GetText(j, 4) == null ){
							stra[4]="0.00";
							 
						}else{
							stra[4] = String.valueOf(tSSRS.GetText(j, 4));
						}
						//延滞利息金额
						
						
					}
					tListTable.add(stra);
				}
				
			}
		} else {
			CError tError = new CError();
			tError.moduleName = "LLClaimBillBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}

		logger.debug("----------以下查询，是为单个变量赋值-----------");

		// 统计机构$=/DOFStatMngcom$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mMngCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();

		// 统计人
		String Operator = mG.Operator;
		// 统计日期$=/DOFDate$
		String SysDate = PubFun.getCurrentDate();

		// 给模板中的变量赋值
		tTextTag.add("ManageCom", tMngCom); // 统计机构
		
		tTextTag.add("StartDate", mStartDate); // 统计起期
		tTextTag.add("EndDate", mEndDate); // 统计止期
		
		tTextTag.add("Operator", Operator); // 统计人
		tTextTag.add("Date", SysDate); // 统计日期

		logger.debug("********************************************");

		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);

		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		logger.debug("********************************************");
		logger.debug("---以下 将tXmlExport打包，返回前台--");
		// tXmlExport.outputDocumentToFile("c:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);
		logger.debug("xmlexport=" + tXmlExport);

		return true;
	
	}
	
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RiskClaimBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealClaimBill() {
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		tXmlExport.createDocument("LLClaimBill.vts", "");

		// 新建一个ListTable的实例
		ListTable tListTable = new ListTable();
		tListTable.setName("DOF");
		// 定义列表标题
		String[] Title = new String[19];
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
		Title[14] = "";
		Title[15] = "";
		Title[16] = "";
		Title[17] = "";
		Title[18] = "";

		logger.debug("---以下 查询列表$*/DOF/ROW/COL内容，并为列表赋值--");
		String strSQL = "select '',a.clmno,"// 立案号
				+ " a.contno,"// 保单号
				+ " a.riskcode,"// 险种代码
				+ " (select riskname from lmrisk where riskcode = a.riskcode),"// 险种名称
				+ " (select name from ldperson where customerno=a.customerno),"// 事故者姓名
				+ " b.makedate,"// 立案日期
				+ " b.EndCaseDate,"// 签批同意日期
				+ " (case a.givetype when '0' then a.realpay else 0 end), "// 给付金额
				+ " (case a.givetype when '1' then a.realpay else 0 end), "// 拒付金额
				+ " a.CValiDate,"// 保单生效日期
				+ " (select medaccdate from llcase where caseno = a.caseno and customerno = a.customerno), "// 医疗出险日期
				+ " (select accdate from llcase where caseno = a.caseno and customerno = a.customerno), "// 其他出险日期
				+ " (case b.AccidentReason when '1' then '意外' when '2' then '疾病' else '' end), "// 出险类型
				+ " (case b.RgtState when '01' then '简易案件' when '02' then '帐户案件' when '03' then '批量案件' when '11' then '普通案件' else '' end), "// 案件类型
				+ " (select (case AuditConclusion when '0' then '给付' when '1' then '拒付' else '' end) from LLClaimUWMain where clmno = a.clmno) AuditConclusion,"// 案件结论
				+ " (case a.givetype when '0' then '给付' when '1' then '拒付' else '' end), "// 保项结论
				+ " (case b.clmstate when '10' then '报案' when '20' then '立案' when '30' then '审核' when '35' then '预付' when '40' then '审批' when '50' then '结案' when '60' then '完成' when '70' then '关闭' else '' end), "// 案件状态
				+ " (case (select 1 from es_doc_main where busstype = 'LP' and doccode = a.clmno) when 1 then '有' else '无' end)"// 扫描件标志
				+ " from llclaimdetail a , llregister b"
				+ " where a.clmno = b.rgtno "
				+ " and a.grpcontno='00000000000000000000'"
				+ " and b.RgtConclusion!='02' "// 不予立案
				+ " and not exists (select * from LLClaimUWMain where clmno = a.clmno and AuditConclusion in ('2','3'))" // 撤件案件
				+ " and b.mngcom like concat('" + "?mngcom?" + "','%') and b.makedate>=to_date('"
				+ "?startdate?"
				+ "','yyyy-mm-dd')  and b.makedate<=to_date('"
				+ "?enddate?"
				+ "','yyyy-mm-dd')  and b.clmstate in ('20','30','35','40','50','60','70')    ";
		// 拼入清单类型条件
		if (mStatType.equals("0")) // 已结案件
		{
			strSQL += " and b.clmstate in ('40','50','60','70')";
		} else {
			strSQL += " and b.clmstate in ('20','30','35')";
		}
		strSQL += " order by a.clmno";
		logger.debug("查询语句:strSQL==" + strSQL);
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(strSQL);
		sqlbv2.put("mngcom", mMngCom);
		sqlbv2.put("startdate", mStartDate);
		sqlbv2.put("enddate", mEndDate);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv2);
		int k = 1;
		if (tSSRS.getMaxRow() != 0) {
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				if (i > 1 && !tSSRS.GetText(i, 2).equals(tSSRS.GetText(i - 1, 2))) {
					k++;
				}

				String[] stra = new String[19];
				stra[0] = k + "";
				stra[1] = tSSRS.GetText(i, 2); // 立案号
				stra[2] = tSSRS.GetText(i, 3); // 保单号
				stra[3] = tSSRS.GetText(i, 4); // 险种代码
				stra[4] = tSSRS.GetText(i, 5); // 银行
				stra[5] = tSSRS.GetText(i, 6); // 帐号
				stra[6] = tSSRS.GetText(i, 7); // 金额
				stra[7] = tSSRS.GetText(i, 8); // 支付方式
				stra[8] = tSSRS.GetText(i, 9); // 审批通过日期
				stra[9] = tSSRS.GetText(i, 10); // 实付日期
				stra[10] = tSSRS.GetText(i, 11);// 审核人代码
				stra[11] = tSSRS.GetText(i, 12);// 审核人姓名
				stra[12] = tSSRS.GetText(i, 13);// 审核结论
				stra[13] = tSSRS.GetText(i, 14);// 案件标识
				stra[14] = tSSRS.GetText(i, 15);// 受托人
				stra[15] = tSSRS.GetText(i, 16);// 受托人联系电话
				stra[16] = tSSRS.GetText(i, 17);// 受托人区部组
				stra[17] = tSSRS.GetText(i, 18);// 受托人联系电话
				stra[18] = tSSRS.GetText(i, 19);// 受托人区部组

				tListTable.add(stra);
			}
		} else {
			CError tError = new CError();
			tError.moduleName = "LLClaimBillBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}

		logger.debug("----------以下查询，是为单个变量赋值-----------");

		// 统计机构$=/DOFStatMngcom$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mMngCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();

		// 统计人
		String Operator = mG.Operator;
		// 统计日期$=/DOFDate$
		String SysDate = PubFun.getCurrentDate();

		// 给模板中的变量赋值
		tTextTag.add("DOFMngCom", tMngCom); // 统计机构

		tTextTag.add("DOFStartDate", mStartDate); // 统计起期
		tTextTag.add("DOFEndDate", mEndDate); // 统计止期

		tTextTag.add("DOFOperator", Operator); // 统计人
		tTextTag.add("DOFDate", SysDate); // 统计日期

		logger.debug("********************************************");

		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);

		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

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
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealNoRgtBill() {
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		tXmlExport.createDocument("LLClaimNoRgtBill.vts", "");

		// 新建一个ListTable的实例
		ListTable tListTable = new ListTable();
		tListTable.setName("DOF");
		// 定义列表标题
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

		logger.debug("---以下 查询列表$*/DOF/ROW/COL内容，并为列表赋值--");
		String strSQL = "select '', "// 序号
				+ " a.rgtno, "// 立案号
				+ " '', "// 保单号
				+ " a.riskcode, "// 险种代码
				+ " (select riskname from lmrisk where riskcode = a.riskcode), "// 险种名称
				+ " (select name from ldperson where customerno = a.customerno), "// 事故者姓名
				+ " a.rgtdate, "// 立案日期
				+ " '', "// 保单生效日期
				+ " (select medaccdate from llcase where rgtno = a.rgtno and customerno = a.customerno), "// 医疗出险日期
				+ " (select accdate from llcase where rgtno = a.rgtno and customerno = a.customerno), "// 其他出险日期
				+ " (case a.AccidentReason when '1' then '意外' when '2' then '疾病' else '' end), "// 出险类型
				+ " a.modifydate, "// 不予立案日期
				+ " a.NoRgtReason, "// 不予立案原因
				+ " a.remark "// 备注
				+ " from llregister a "
				+ " where a.RgtConclusion = '02'"// 不予立案
				+ " and a.rgtclass = '1' "// 个险"
				+ " and a.mngcom like concat('" + "?mngcom?" + "','%') and a.makedate>=to_date('"
				+ "?startdate?"
				+ "','yyyy-mm-dd') and a.makedate<=to_date('" + "?enddate?" + "','yyyy-mm-dd') order by a.rgtno";
		logger.debug("查询语句:strSQL==" + strSQL);
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(strSQL);
		sqlbv3.put("mngcom", mMngCom);
		sqlbv3.put("startdate", mStartDate);
		sqlbv3.put("enddate", mEndDate);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv3);

		if (tSSRS.getMaxRow() != 0) {
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				String[] stra = new String[14];
				stra[0] = i + "";
				stra[1] = tSSRS.GetText(i, 2); // 立案号
				stra[2] = tSSRS.GetText(i, 3); // 保单号
				stra[3] = tSSRS.GetText(i, 4); // 险种代码
				stra[4] = tSSRS.GetText(i, 5); // 险种名称
				stra[5] = tSSRS.GetText(i, 6); // 事故者姓名
				stra[6] = tSSRS.GetText(i, 7); // 立案日期
				stra[7] = tSSRS.GetText(i, 8); // 保单生效日期
				stra[8] = tSSRS.GetText(i, 9); // 医疗出险日期
				stra[9] = tSSRS.GetText(i, 10); // 其他出险日期
				stra[10] = tSSRS.GetText(i, 11);// 出险类型
				stra[11] = tSSRS.GetText(i, 12);// 不予立案日期
				stra[12] = tSSRS.GetText(i, 13);// 不予立案原因
				stra[13] = tSSRS.GetText(i, 14);// 备注

				tListTable.add(stra);
			}
		} else {
			CError tError = new CError();
			tError.moduleName = "LLClaimBillBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}

		logger.debug("----------以下查询，是为单个变量赋值-----------");

		// 统计机构$=/DOFStatMngcom$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mMngCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();

		// 统计人
		String Operator = mG.Operator;
		// 统计日期$=/DOFDate$
		String SysDate = PubFun.getCurrentDate();

		// 给模板中的变量赋值
		tTextTag.add("DOFMngCom", tMngCom); // 统计机构

		tTextTag.add("DOFStartDate", mStartDate); // 统计起期
		tTextTag.add("DOFEndDate", mEndDate); // 统计止期

		tTextTag.add("DOFOperator", Operator); // 统计人
		tTextTag.add("DOFDate", SysDate); // 统计日期

		logger.debug("********************************************");

		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);

		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

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
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealBackBill() {
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		tXmlExport.createDocument("LLClaimBackBill.vts", "");

		// 新建一个ListTable的实例
		ListTable tListTable = new ListTable();
		tListTable.setName("DOF");
		// 定义列表标题
		String[] Title = new String[19];
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
		Title[14] = "";
		Title[15] = "";
		Title[16] = "";
		Title[17] = "";
		Title[18] = "";

		logger.debug("---以下 查询列表$*/DOF/ROW/COL内容，并为列表赋值--");
		String strSQL = "select backno, clmno, contno, riskcode, riskname, name, RgtDate,"
				+ " ExamDate1, ExamDate2, sum(realpay), cvalidate, medaccdate, accdate,AccidentReason,"
				+ " RgtState, clmstate, BackState, backreason, remark "
				+ " from ("
				+ " select b.backno," // 序号
				+ " a.clmno," // 立案号
				+ " a.contno," // 保单号,
				+ " a.riskcode," // 险种代码
				+ " (select riskname from lmrisk where riskcode = a.riskcode) riskname," // 险种名称
				+ " (select name from ldperson where customerno = a.insuredno) name," // 事故者姓名
				+ " (select RgtDate from llregister where rgtno = a.clmno) RgtDate," // 立案日期
				+ " (select ExamDate from LLClaimUWMDetail where ExamConclusion = 0 and clmno = a.clmno and clmuwno = (select min(clmuwno) from LLClaimUWMDetail where ExamConclusion = 0 and clmno = a.clmno)) ExamDate1," // 首次签批同意日期
				+ " (select ExamDate from LLClaimUWMain where clmno = a.clmno) ExamDate2," // 二次签批日期
				+ " a.realpay," // 回退金额
				+ " (select cvalidate from lccont where contno = a.contno) cvalidate," // 保单生效日期
				+ " (select medaccdate from llcase where caseno = a.caseno and customerno = a.insuredno) medaccdate," // 医疗出险日期
				+ " (select accdate from llcase where caseno = a.caseno and customerno = a.insuredno) accdate," // 其他出险日期
				+ " (select (case AccidentReason when '1' then '意外' when '2' then '疾病' else '' end) from llregister aa where rgtno=a.clmno) AccidentReason,"// 出险类型
				+ " (select (case RgtState when '01' then '简易案件' when '02' then '帐户案件' when '03' then '批量案件' when '11' then '普通案件' else '' end) from llregister aa where rgtno=a.clmno) RgtState,"// 案件类型
				+ " (select (case clmstate when '10' then '报案' when '20' then '立案' when '30' then '审核' when '35' then '预付' when '40' then '审批' when '50' then '结案' when '60' then '完成' when '70' then '关闭' else '' end) from llregister where rgtno=a.clmno) clmstate,"// 案件状态
				+ " (case b.BackState when '0' then '申请' when '1' then '完成' else '' end) BackState," // 回退状态
				+ " (select codename from ldcode where codetype='llbackreason' and code=b.backreason) backreason," // 回退原因
				+ " b.remark "// 备注
				+ " from llclaimpolicy a , llcaseback b" 
				+ " where a.clmno = b.clmno"
				+ " and a.grpcontno = '00000000000000000000' and a.mngcom like concat('" + "?mngcom?"
				+ "','%') and a.makedate>=to_date('" + "?startdate?" + "','yyyy-mm-dd') and a.makedate<=to_date('" + "?enddate?" + "','yyyy-mm-dd')) g "
				+ " group by backno, clmno, contno, riskcode, riskname, name, RgtDate,"
				+ " ExamDate1, ExamDate2, cvalidate, medaccdate,accdate,AccidentReason,"
				+ " RgtState, clmstate, BackState, backreason, remark" + " order by backno";
		logger.debug("查询语句:strSQL==" + strSQL);
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(strSQL);
		sqlbv4.put("mngcom", mMngCom);
		sqlbv4.put("startdate", mStartDate);
		sqlbv4.put("enddate", mEndDate);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv4);

		if (tSSRS.getMaxRow() != 0) {
			int k = 1;
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				if (i > 1 && !tSSRS.GetText(i, 1).equals(tSSRS.GetText(i - 1, 1))) {
					k = k + 1;
				}
				String[] stra = new String[19];
				stra[0] = k + "";
				stra[1] = tSSRS.GetText(i, 2); // 立案号
				stra[2] = tSSRS.GetText(i, 3); // 保单号
				stra[3] = tSSRS.GetText(i, 4); // 险种代码
				stra[4] = tSSRS.GetText(i, 5); // 险种名称
				stra[5] = tSSRS.GetText(i, 6); // 事故者姓名
				stra[6] = tSSRS.GetText(i, 7); // 立案日期
				stra[7] = tSSRS.GetText(i, 8); // 首次签批同意日期
				stra[8] = tSSRS.GetText(i, 9); // 二次签批日期
				stra[9] = tSSRS.GetText(i, 10); // 回退金额
				stra[10] = tSSRS.GetText(i, 11);// 保单生效日期
				stra[11] = tSSRS.GetText(i, 12);// 医疗出险日期
				stra[12] = tSSRS.GetText(i, 13);// 其他出险日期
				stra[13] = tSSRS.GetText(i, 14);// 出险类型
				stra[14] = tSSRS.GetText(i, 15); // 案件类型
				stra[15] = tSSRS.GetText(i, 16);// 案件状态
				stra[16] = tSSRS.GetText(i, 17);// 回退状态
				stra[17] = tSSRS.GetText(i, 18);// 回退原因
				stra[18] = tSSRS.GetText(i, 19);// 备注
				tListTable.add(stra);
			}
		} else {
			CError tError = new CError();
			tError.moduleName = "LLClaimBillBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}

		logger.debug("----------以下查询，是为单个变量赋值-----------");

		// 统计机构$=/DOFStatMngcom$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mMngCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();

		// 统计人
		String Operator = mG.Operator;
		// 统计日期$=/DOFDate$
		String SysDate = PubFun.getCurrentDate();

		// 给模板中的变量赋值
		tTextTag.add("DOFMngCom", tMngCom); // 统计机构

		tTextTag.add("DOFStartDate", mStartDate); // 统计起期
		tTextTag.add("DOFEndDate", mEndDate); // 统计止期

		tTextTag.add("DOFOperator", Operator); // 统计人
		tTextTag.add("DOFDate", SysDate); // 统计日期

		logger.debug("********************************************");

		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);

		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

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
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealInqBill() {
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		tXmlExport.createDocument("LLClaimInqBill.vts", "");

		// 新建一个ListTable的实例
		ListTable tListTable = new ListTable();
		tListTable.setName("DOF");
		// 定义列表标题
		String[] Title = new String[16];
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
		Title[14] = "";
		Title[15] = "";

		logger.debug("---以下 查询列表$*/DOF/ROW/COL内容，并为列表赋值--");
		String strSQL = "select clmno,contno,riskcode,riskname,name,RgtDate,realpay,InitPhase,schemass,"
				+ "cvalidate,medaccdate,accdate,AccidentReason,RgtState,clmstate from ("
				+ " select a.clmno, "// 立案号
				+ " '' contno, "// 保单号
				+ " '' riskcode, "// 险种代码
				+ " '' riskname, "// 险种名称
				+ " (select name from ldperson where customerno = b.customerno) name,"// 事故者姓名
				+ " to_date('','yyyy-mm-dd') RgtDate, "// 立案日期
				+ " 0 realpay, "// 涉及赔付金额
				+ " (case a.InitPhase when '01' then '报案' when '02' then '审核' when '03' then '呈报' else '' end) InitPhase, "// 调查提起阶段
				+ " (case (select activityid from lwmission where missionprop1=a.clmno and activityid in('0000005125','0000005145','0000005165')) when '0000005125' then '待任务分配' when '0000005145' then '调查中' when '0000005165' then '待机构结论录入' else '' end) schemass, "// 调查进度
				+ " to_date('','yyyy-mm-dd') cvalidate, "// 保单生效日期
				+ " b.medaccdate, "// 医疗出险日期
				+ " b.accdate, "// 其他出险日期
				+ " (select (case AccidentReason when '1' then '意外' when '2' then '疾病' else '' end) from llreport where rptno=b.subrptno) AccidentReason, "// 出险类型
				+ " (select (case RgtState when '1' then '意外' when '2' then '疾病' else '' end) from llreport where rptno=b.subrptno) RgtState, "// 案件类型
				+ " '报案' clmstate"// 案件状态
				+ " from LLInqApply a , llsubreport b"
				+ " where a.clmno = b.subrptno "
				+ " and a.initphase in('01','03')"// 01报案02审核03呈报
				+ " and exists (select 1 from llreport where rptno= a.clmno and rgtclass='1')"// 1个险
				+ " and not exists (select 1 from llregister where rgtno=a.clmno)"// 未立案
				+ " and not exists (select 1 from LLInqConclusion where clmno=a.clmno and Conno=a.inqno and FiniFlag='1')"// 1已完成
				+ " and b.mngcom like concat('"
				+ "?mngcom?"
				+ "','%') and a.makedate>=to_date('"
				+ "?startdate?"
				+ "','yyyy-mm-dd') and a.makedate<=to_date('"
				+ "?enddate?"
				+ "','yyyy-mm-dd')"
				+ " union"
				+ " select a.clmno , "// 立案号
				+ " b.contno, "// 保单号
				+ " b.riskcode, "// 险种代码
				+ " (select riskname from lmrisk where riskcode = b.riskcode) riskname, "// 险种名称
				+ " (select name from ldperson where customerno = b.insuredno) name, "// 事故者姓名
				+ " (select RgtDate from llregister where rgtno = a.clmno) RgtDate, "// 立案日期
				+ " (select realpay from LLClaim where clmno = a.clmno) realpay, "// 涉及赔付金额
				+ " (case a.InitPhase when '01' then '报案' when '02' then '审核' when '03' then '呈报' else '' end) InitPhase, "// 调查提起阶段
				+ " (case (select activityid from lwmission where missionprop1=a.clmno and activityid in('0000005125','0000005145','0000005165')) when '0000005125' then '待任务分配' when '0000005145' then '调查中' when '0000005165' then '待机构结论录入' else '' end) schemass, "// 调查进度
				+ " b.cvalidate, "// 保单生效日期
				+ " (select medaccdate from llcase where rgtno = b.clmno and customerno = b.insuredno) medaccdate, "// 医疗出险日期
				+ " (select accdate from llcase where rgtno = b.clmno and customerno = b.insuredno) accdate, "// 其他出险日期
				+ " (select (case AccidentReason when '1' then '意外' when '2' then '疾病' else '' end) from llregister where rgtno=a.clmno) AccidentReason,"// 出险类型
				+ " (select (case RgtState when '01' then '简易案件' when '02' then '帐户案件' when '03' then '批量案件' when '11' then '普通案件' else '' end) from llregister where rgtno=a.clmno) RgtState,"// 案件类型
				+ " '审核' clmstate"// 案件状态
				+ " from LLInqApply a , llclaimpolicy b"
				+ " where a.clmno = b.clmno "
				+ " and a.initphase in('02','03')"// 01报案02审核03呈报
				+ " and b.grpcontno='00000000000000000000'"
				+ " and not exists (select 1 from LLInqConclusion where clmno=a.clmno and Conno=a.inqno and FiniFlag='1')"// 1已完成
				+ " and b.mngcom like concat('"
				+ "?mngcom?"
				+ "','%') and a.makedate>=to_date('"
				+ "?startdate?"
				+ "','yyyy-mm-dd') and a.makedate<=to_date('"
				+ "?enddate?"
				+ "','yyyy-mm-dd')) g order by clmno";
		logger.debug("查询语句:strSQL==" + strSQL);
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(strSQL);
		sqlbv5.put("mngcom", mMngCom);
		sqlbv5.put("startdate", mStartDate);
		sqlbv5.put("enddate", mEndDate);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv5);

		if (tSSRS.getMaxRow() != 0) {
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				String[] stra = new String[16];
				stra[0] = i + "";
				stra[1] = tSSRS.GetText(i, 1); // 立案号
				stra[2] = tSSRS.GetText(i, 2); // 保单号
				stra[3] = tSSRS.GetText(i, 3); // 险种代码
				stra[4] = tSSRS.GetText(i, 4); // 险种名称
				stra[5] = tSSRS.GetText(i, 5); // 事故者姓名
				stra[6] = tSSRS.GetText(i, 6); // 立案日期
				stra[7] = tSSRS.GetText(i, 7); // 首次签批同意日期
				stra[8] = tSSRS.GetText(i, 8); // 二次签批日期
				stra[9] = tSSRS.GetText(i, 9); // 回退金额
				stra[10] = tSSRS.GetText(i, 10);// 保单生效日期
				stra[11] = tSSRS.GetText(i, 11);// 医疗出险日期
				stra[12] = tSSRS.GetText(i, 12);// 其他出险日期
				stra[13] = tSSRS.GetText(i, 13);// 出险类型
				stra[14] = tSSRS.GetText(i, 14); // 案件类型
				stra[15] = tSSRS.GetText(i, 15);// 案件状态
				tListTable.add(stra);
			}
		} else {
			CError tError = new CError();
			tError.moduleName = "LLClaimBillBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}

		logger.debug("----------以下查询，是为单个变量赋值-----------");

		// 统计机构$=/DOFStatMngcom$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mMngCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();

		// 统计人
		String Operator = mG.Operator;
		// 统计日期$=/DOFDate$
		String SysDate = PubFun.getCurrentDate();

		// 给模板中的变量赋值
		tTextTag.add("DOFMngCom", tMngCom); // 统计机构

		tTextTag.add("DOFStartDate", mStartDate); // 统计起期
		tTextTag.add("DOFEndDate", mEndDate); // 统计止期

		tTextTag.add("DOFOperator", Operator); // 统计人
		tTextTag.add("DOFDate", SysDate); // 统计日期

		logger.debug("********************************************");

		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);

		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

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
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealPrepayBill() {
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		tXmlExport.createDocument("LLClaimPrepayBill.vts", "");

		// 新建一个ListTable的实例
		ListTable tListTable = new ListTable();
		tListTable.setName("DOF");
		// 定义列表标题
		String[] Title = new String[18];
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
		Title[14] = "";
		Title[15] = "";
		Title[16] = "";
		Title[17] = "";

		logger.debug("---以下 查询列表$*/DOF/ROW/COL内容，并为列表赋值--");
		String strSQL = "select clmno,contno,riskcode,riskname,name,rgtdate,ExamDate1,sum(prepaysum),ExamDate,"
				+ " realpay,schedule,cvalidate,medaccdate,accdate,AccidentReason, RgtState,clmstate"
				+ " from ("
				+ " select a.clmno, "// 立案号
				+ " a.contno, "// 保单号
				+ " a.riskcode, "// 险种代码
				+ " (select riskname from lmrisk where riskcode = a.riskcode) riskname, "// 险种名称
				+ " (select name from ldperson where customerno = b.customerno) name, "// 事故者姓名
				+ " b.rgtdate, "// 立案日期
				+ " (select ExamDate from llclaimuwmain where clmno=a.clmno and CheckType=1) ExamDate1, "// 预付签批日期
				+ " a.prepaysum, "// 预付金额
				+ " (select ExamDate from LLClaimUWMain where clmno = a.clmno and CheckType=0) ExamDate, "// 签批同意日期
				+ " (select realpay from LLClaim where clmno = a.clmno) realpay, "// 给付金额
				+ " (case (select min(feeoperationtype) from ljagetclaim where othernotype='5' and otherno=a.clmno) when 'A' then '完成' when 'B' then '已预付' else '待预付签批' end) schedule, "// 预付进度
				+ " (select cvalidate from lccont where contno = a.contno) cvalidate, "// 保单生效日期
				+ " (select medaccdate from llcase where caseno = a.caseno and customerno = b.customerno) medaccdate, "// 医疗出险日期
				+ " (select accdate from llcase where caseno = a.caseno and customerno = b.customerno) accdate, "// 其他出险日期
				+ " (case b.AccidentReason when '1' then '意外' when '2' then '疾病' else '' end) AccidentReason, "// 出险类型
				+ " (case b.RgtState when '01' then '简易案件' when '02' then '帐户案件' when '03' then '批量案件' when '11' then '普通案件' else '' end) RgtState, "// 案件类型
				+ " (case b.clmstate when '10' then '报案' when '20' then '立案' when '30' then '审核' when '35' then '预付' when '40' then '审批' when '50' then '结案' when '60' then '完成' when '70' then '关闭' else '' end) clmstate "// 案件状态
				+ " from LLPrepayDetail a , llregister b"
				+ " where a.clmno = b.rgtno "
				+ "and a.grpcontno='00000000000000000000' "
				+ "and a.mngcom like concat('"
				+ "?mngcom?"
				+ "','%') and a.makedate>=to_date('"
				+ "?startdate?"
				+ "','yyyy-mm-dd') and a.makedate<=to_date('"
				+ "?enddate?"
				+ "','yyyy-mm-dd')) g"
				+ " group by clmno,contno,riskcode,riskname,name,rgtdate,ExamDate1,ExamDate,"
				+ " realpay,schedule,cvalidate,medaccdate,accdate,AccidentReason, RgtState,clmstate"
				+ " order by clmno";
		logger.debug("查询语句:strSQL==" + strSQL);
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(strSQL);
		sqlbv6.put("mngcom", mMngCom);
		sqlbv6.put("startdate", mStartDate);
		sqlbv6.put("enddate", mEndDate);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv6);

		if (tSSRS.getMaxRow() != 0) {
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				String[] stra = new String[18];
				stra[0] = i + "";
				stra[1] = tSSRS.GetText(i, 1); // 立案号
				stra[2] = tSSRS.GetText(i, 2); // 保单号
				stra[3] = tSSRS.GetText(i, 3); // 险种代码
				stra[4] = tSSRS.GetText(i, 4); // 险种名称
				stra[5] = tSSRS.GetText(i, 5); // 事故者姓名
				stra[6] = tSSRS.GetText(i, 6); // 立案日期
				stra[7] = tSSRS.GetText(i, 7); // 首次签批同意日期
				stra[8] = tSSRS.GetText(i, 8); // 二次签批日期
				stra[9] = tSSRS.GetText(i, 9); // 回退金额
				stra[10] = tSSRS.GetText(i, 10);// 保单生效日期
				stra[11] = tSSRS.GetText(i, 11);// 医疗出险日期
				stra[12] = tSSRS.GetText(i, 12);// 其他出险日期
				stra[13] = tSSRS.GetText(i, 13);// 出险类型
				stra[14] = tSSRS.GetText(i, 14); // 案件类型
				stra[15] = tSSRS.GetText(i, 15);// 案件状态
				stra[16] = tSSRS.GetText(i, 16);// 回退状态
				stra[17] = tSSRS.GetText(i, 17);// 回退原因
				tListTable.add(stra);
			}
		} else {
			CError tError = new CError();
			tError.moduleName = "LLClaimBillBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}

		logger.debug("----------以下查询，是为单个变量赋值-----------");

		// 统计机构$=/DOFStatMngcom$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mMngCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();

		// 统计人
		String Operator = mG.Operator;
		// 统计日期$=/DOFDate$
		String SysDate = PubFun.getCurrentDate();

		// 给模板中的变量赋值
		tTextTag.add("DOFMngCom", tMngCom); // 统计机构

		tTextTag.add("DOFStartDate", mStartDate); // 统计起期
		tTextTag.add("DOFEndDate", mEndDate); // 统计止期

		tTextTag.add("DOFOperator", Operator); // 统计人
		tTextTag.add("DOFDate", SysDate); // 统计日期

		logger.debug("********************************************");

		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);

		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

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
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealCancelBill() {
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		tXmlExport.createDocument("LLClaimCancelBill.vts", "");

		// 新建一个ListTable的实例
		ListTable tListTable = new ListTable();
		tListTable.setName("DOF");
		// 定义列表标题
		String[] Title = new String[16];
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
		Title[14] = "";
		Title[15] = "";

		logger.debug("---以下 查询列表$*/DOF/ROW/COL内容，并为列表赋值--");
		String strSQL = "select clmno,contno,riskcode,riskname,name,rgtdate,sum(realpay1),sum(realpay2),"
				+ " cvalidate,medaccdate,accdate,AccidentReason,modifydate,AuditConclusion,Specialremark from ("
				+ " select '',"
				+ " a.clmno, "// 立案号
				+ " a.contno,"// 保单号
				+ " a.riskcode,"// 险种代码
				+ " (select riskname from lmrisk where riskcode = a.riskcode) riskname,"// 险种名称
				+ " (select name from ldperson where customerno = a.insuredno) name,"// 事故者姓名
				+ " b.rgtdate,"// 立案日期
				+ " (select (case when sum(realpay) is null then 0 else sum(realpay) end) from llclaimdetail where ClmNo=a.clmno and riskcode=a.riskcode and llclaimdetail.givetype='0') realpay1,"// 赔付金额
				+ " (select (case when sum(realpay) is null then 0 else sum(realpay) end) from llclaimdetail where ClmNo=a.clmno and riskcode=a.riskcode and llclaimdetail.givetype='1') realpay2,"// 拒付金额
				+ " (select cvalidate from lccont where contno=a.contno) cvalidate,"// 保单生效日期
				+ " (select medaccdate from llcase where caseno = a.caseno and customerno = a.insuredno) medaccdate,"// 医疗出险日期
				+ " (select accdate from llcase where caseno = a.caseno and customerno = a.insuredno) accdate, "// 其他出险日期
				+ " (case b.AccidentReason when '1' then '意外' when '2' then '疾病' else '' end) AccidentReason, "// 出险类型
				+ " a.modifydate,"// 撤件日期
				+ " (select (case AuditConclusion when '2' then '客户撤案' when '3' then '公司撤案' else '' end) from LLClaimUWMain where clmno = a.clmno ) AuditConclusion,"// 撤件类型
				+ " (select Specialremark from LLClaimUWMain where clmno = a.clmno) Specialremark"// 备注
				+ " from LLClaimPolicy a , llregister b"
				+ " where a.clmno = b.rgtno"
				+ " and a.grpcontno = '00000000000000000000'"
				+ "and a.mngcom like concat('"
				+ "?mngcom?"
				+ "','%') and a.makedate>=to_date('"
				+ "?startdate?"
				+ "','yyyy-mm-dd') and a.makedate<=to_date('"
				+ "?enddate?"
				+ "','yyyy-mm-dd') and exists (select 1 from LLClaimUWMain where clmno = a.clmno and AuditConclusion in ('2', '3')) "// 2客户撤案3公司撤案
				+ " ) g group by clmno,contno,riskcode,riskname,name,rgtdate,cvalidate,"
				+ " medaccdate,accdate,AccidentReason,modifydate,AuditConclusion,Specialremark"
				+ " order by clmno";
		logger.debug("查询语句:strSQL==" + strSQL);
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(strSQL);
		sqlbv7.put("mngcom", mMngCom);
		sqlbv7.put("startdate", mStartDate);
		sqlbv7.put("enddate", mEndDate);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv7);

		if (tSSRS.getMaxRow() != 0) {
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				String[] stra = new String[16];
				stra[0] = i + "";
				stra[1] = tSSRS.GetText(i, 1); // 立案号
				stra[2] = tSSRS.GetText(i, 2); // 保单号
				stra[3] = tSSRS.GetText(i, 3); // 险种代码
				stra[4] = tSSRS.GetText(i, 4); // 险种名称
				stra[5] = tSSRS.GetText(i, 5); // 事故者姓名
				stra[6] = tSSRS.GetText(i, 6); // 立案日期
				stra[7] = tSSRS.GetText(i, 7); // 首次签批同意日期
				stra[8] = tSSRS.GetText(i, 8); // 二次签批日期
				stra[9] = tSSRS.GetText(i, 9); // 回退金额
				stra[10] = tSSRS.GetText(i, 10);// 保单生效日期
				stra[11] = tSSRS.GetText(i, 11);// 医疗出险日期
				stra[12] = tSSRS.GetText(i, 12);// 其他出险日期
				stra[13] = tSSRS.GetText(i, 13);// 出险类型
				stra[14] = tSSRS.GetText(i, 14); // 案件类型
				stra[15] = tSSRS.GetText(i, 15);// 案件状态
				tListTable.add(stra);
			}
		} else {
			CError tError = new CError();
			tError.moduleName = "LLClaimBillBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}

		logger.debug("----------以下查询，是为单个变量赋值-----------");

		// 统计机构$=/DOFStatMngcom$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mMngCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();

		// 统计人
		String Operator = mG.Operator;
		// 统计日期$=/DOFDate$
		String SysDate = PubFun.getCurrentDate();

		// 给模板中的变量赋值
		tTextTag.add("DOFMngCom", tMngCom); // 统计机构

		tTextTag.add("DOFStartDate", mStartDate); // 统计起期
		tTextTag.add("DOFEndDate", mEndDate); // 统计止期

		tTextTag.add("DOFOperator", Operator); // 统计人
		tTextTag.add("DOFDate", SysDate); // 统计日期

		logger.debug("********************************************");

		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);

		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

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
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealUWsecondBill() {
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		tXmlExport.createDocument("LLClaimUWsecondBill.vts", "");

		// 新建一个ListTable的实例
		ListTable tListTable = new ListTable();
		tListTable.setName("DOF");
		// 定义列表标题
		String[] Title = new String[16];
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
		Title[14] = "";
		Title[15] = "";

		logger.debug("---以下 查询列表$*/DOF/ROW/COL内容，并为列表赋值--");
		String strSQL = "select missionprop1,"// 立案号
				+ " b.contno,"// 保单号
				+ " (select riskcode from lcpol where polno=b.polno) riskcode,"// 险种代码
				+ " (select riskname from lmrisk where riskcode = (select riskcode from lcpol where polno=b.polno)) riskname,"// 险种名称
				+ " a.MissionProp4 name,"// 事故者姓名
				+ " (select RgtDate from llregister where rgtno = a.missionprop1) RgtDate, "// 立案日期
				+ " a.makedate ,"// 发起日期
				+ " (case (select riskperiod from lmriskapp where riskcode=(select riskcode from lcpol where polno=b.polno) ) when 'S' then '短期险' when 'M' then '短期险' when 'L' then '长期险' else '' end),"// 二核类型
				+ " (select codename from ldcode where codetype = 'llriskuwflag' and code=b.passflag),"// 二核结论
				+ " (case a.activitystatus when '3' then '待核销' else (case a.defaultoperator when '' then '待核保' else '待确认' end) end),"// 二核进度
				+ " (select cvalidate from lccont where contno = b.contno) cvalidate,"// 保单生效日期
				+ " (select medaccdate from llcase where caseno = a.missionprop1 and customerno = a.MissionProp3) medaccdate, "// 医疗出险日期
				+ " (select accdate from llcase where caseno = a.missionprop1 and customerno = a.MissionProp3) accdate, "// 其他出险日期
				+ " (select (case AccidentReason when '1' then '意外' when '2' then '疾病' else '' end) from llregister where rgtno=b.caseno) AccidentReason,"// 出险类型
				+ " (select (case RgtState when '01' then '简易案件' when '02' then '帐户案件' when '03' then '批量案件' when '11' then '普通案件' else '' end) from llregister where rgtno=b.caseno) RgtState"// 案件类型
				+ " from lwmission a , lluwmaster b"
				+ " where a.missionprop1=b.caseno"
				+ " and a.activityid='0000005505' and a.MissionProp20 like concat('"
				+ "?mngcom?"
				+ "','%') and a.makedate>=to_date('" + "?startdate?" + "','yyyy-mm-dd') and a.makedate<=to_date('"
				+ "?enddate?"
				+ "','yyyy-mm-dd') order by a.missionprop1";

		logger.debug("查询语句:strSQL==" + strSQL);
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(strSQL);
		sqlbv8.put("mngcom", mMngCom);
		sqlbv8.put("startdate", mStartDate);
		sqlbv8.put("enddate", mEndDate);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv8);

		if (tSSRS.getMaxRow() != 0) {
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				String[] stra = new String[16];
				stra[0] = i + "";
				stra[1] = tSSRS.GetText(i, 1); // 立案号
				stra[2] = tSSRS.GetText(i, 2); // 保单号
				stra[3] = tSSRS.GetText(i, 3); // 险种代码
				stra[4] = tSSRS.GetText(i, 4); // 险种名称
				stra[5] = tSSRS.GetText(i, 5); // 事故者姓名
				stra[6] = tSSRS.GetText(i, 6); // 立案日期
				stra[7] = tSSRS.GetText(i, 7); // 首次签批同意日期
				stra[8] = tSSRS.GetText(i, 8); // 二次签批日期
				stra[9] = tSSRS.GetText(i, 9); // 回退金额
				stra[10] = tSSRS.GetText(i, 10);// 保单生效日期
				stra[11] = tSSRS.GetText(i, 11);// 医疗出险日期
				stra[12] = tSSRS.GetText(i, 12);// 其他出险日期
				stra[13] = tSSRS.GetText(i, 13);// 出险类型
				stra[14] = tSSRS.GetText(i, 14); // 案件类型
				stra[15] = tSSRS.GetText(i, 15);// 案件状态
				tListTable.add(stra);
			}
		} else {
			CError tError = new CError();
			tError.moduleName = "LLClaimBillBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}

		logger.debug("----------以下查询，是为单个变量赋值-----------");

		// 统计机构$=/DOFStatMngcom$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mMngCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();

		// 统计人
		String Operator = mG.Operator;
		// 统计日期$=/DOFDate$
		String SysDate = PubFun.getCurrentDate();

		// 给模板中的变量赋值
		tTextTag.add("DOFMngCom", tMngCom); // 统计机构

		tTextTag.add("DOFStartDate", mStartDate); // 统计起期
		tTextTag.add("DOFEndDate", mEndDate); // 统计止期

		tTextTag.add("DOFOperator", Operator); // 统计人
		tTextTag.add("DOFDate", SysDate); // 统计日期

		logger.debug("********************************************");

		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);

		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

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
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealUserBill() {
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		tXmlExport.createDocument("LLClaimUserBill.vts", "");

		// 新建一个ListTable的实例
		ListTable tListTable = new ListTable();
		tListTable.setName("DOF");
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

		logger.debug("---以下 查询列表$*/DOF/ROW/COL内容，并为列表赋值--");
		String strSQL = "select a.comcode,"// 机构代码
				+ " (select name from ldcom where comcode = a.comcode) name,"// 机构名称
				+ " a.usercode,"// 系统用户号
				+ " a.username,"// 姓名
				+ " ''," // 性别
				+ " ''," // 出生日期
				+ " ''," // 身份证号
				+ " (select codename from ldcode where codetype='jobcategory' and code=a.JobCategory) JobCategory," // 权限级别(取岗位分类)
				+ " a.ModifyDate "// 授权日期
				+ " from llclaimuser a where a.comcode like concat('" + "?mngcom?" + "','%') order by a.comcode,a.usercode";
		logger.debug("查询语句:strSQL==" + strSQL);
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(strSQL);
		sqlbv9.put("mngcom", mMngCom);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv9);

		if (tSSRS.getMaxRow() != 0) {
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				String[] stra = new String[16];
				stra[0] = i + "";
				stra[1] = tSSRS.GetText(i, 1); // 立案号
				stra[2] = tSSRS.GetText(i, 2); // 保单号
				stra[3] = tSSRS.GetText(i, 3); // 险种代码
				stra[4] = tSSRS.GetText(i, 4); // 险种名称
				stra[5] = tSSRS.GetText(i, 5); // 事故者姓名
				stra[6] = tSSRS.GetText(i, 6); // 立案日期
				stra[7] = tSSRS.GetText(i, 7); // 首次签批同意日期
				stra[8] = tSSRS.GetText(i, 8); // 二次签批日期
				stra[9] = tSSRS.GetText(i, 9); // 回退金额
				tListTable.add(stra);
			}
		} else {
			CError tError = new CError();
			tError.moduleName = "LLClaimBillBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}

		logger.debug("----------以下查询，是为单个变量赋值-----------");

		// 统计机构$=/DOFStatMngcom$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mMngCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();

		// 统计人
		String Operator = mG.Operator;
		// 统计日期$=/DOFDate$
		String SysDate = PubFun.getCurrentDate();

		// 给模板中的变量赋值
		tTextTag.add("DOFMngCom", tMngCom); // 统计机构

		tTextTag.add("DOFOperator", Operator); // 统计人
		tTextTag.add("DOFDate", SysDate); // 统计日期

		logger.debug("********************************************");

		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);

		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		logger.debug("********************************************");
		logger.debug("---以下 将tXmlExport打包，返回前台--");
		// tXmlExport.outputDocumentToFile("c:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);
		logger.debug("xmlexport=" + tXmlExport);

		return true;
	}

	//理赔未完结统计
	private boolean dealNotEndCase()
	{
	XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		tXmlExport.createDocument("LLClaimNotEndCase.vts", "");

		// 新建一个ListTable的实例
		ListTable tListTable = new ListTable();
		tListTable.setName("DOF");
		// 定义列表标题
		String[] Title = new String[16];
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
		Title[14] = "";
		Title[15] = "";

		String claimTypeSql = "";
		if("全部".equals(mClaimType)){
			claimTypeSql = "";
		}else{
			if(SysConst.DBTYPE_ORACLE.equals(SysConst.DBTYPE)){
				claimTypeSql = "and (to_date('"+PubFun.getCurrentDate()+"','yyyy-mm-dd')-a.rgtdate)>"+Integer.valueOf(mClaimType);
			}else if(SysConst.DBTYPE_MYSQL.equals(SysConst.DBTYPE)){
				claimTypeSql = "and datediff(to_date('"+PubFun.getCurrentDate()+"','yyyy-mm-dd')-a.rgtdate)>"+Integer.valueOf(mClaimType); 
			}
		}
		logger.debug("---以下 查询列表$*/DOF/ROW/COL内容，并为列表赋值--");
		String rownumstr = "";
		if(SysConst.DBTYPE_ORACLE.equals(SysConst.DBTYPE)){
			rownumstr  = " (select riskname from lmriskapp where riskcode in (select riskcode from lcpol where polno=e.polno) and rownum=1) riskname,";
		}else if(SysConst.DBTYPE_MYSQL.equals(SysConst.DBTYPE)){
			rownumstr  = " (select riskname from lmriskapp where riskcode in (select riskcode from lcpol where polno=e.polno) limit 1) riskname,"; 
		} 
		String strSQL = "select a.rgtno,(select contno from lcpol where polno=e.polno) contno, "
					+ " (select riskcode from lcpol where polno=e.polno) riskcode, "
					+ rownumstr
					+ " b.customername,a.RgtDate,e.standpay, "
					+ " (select cvalidate from lcpol where polno=e.polno) cvalidate, "
					+ " c.AccidentDate,(select codename from ldcode where codetype='ngetdutykind' and code=e.getdutykind), "
					+ " a.clmstate,(select case count(*) when 0 then '无' else '有' end from llinqapply where clmno = a.rgtno), "
					+ " (select case count(*) when 0 then '无' else '有' end from es_doc_main where doccode = a.rgtno) "
					+ " from llregister a, llcase b ,llclaimpolicy e,llreport c "
					+ " where a.rgtno = b.rgtno and a.rgtno = e.clmno and a.rgtno = c.rptno and a.RgtClass='1' "
					+ " and a.clmstate not in ('50','60','70') "
					+ " and a.RgtDate >=to_date('" + "?startdate?" + "','yyyy-mm-dd') and a.RgtDate<=to_date('" + "?enddate?" + "','yyyy-mm-dd') "
					+ " and a.MngCom like concat('" + "?mngcom?" + "','%') " + claimTypeSql;
		logger.debug("查询语句:strSQL==" + strSQL);
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql(strSQL);
		sqlbv10.put("mngcom", mMngCom);
		sqlbv10.put("startdate", mStartDate);
		sqlbv10.put("enddate", mEndDate);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv10);

		if (tSSRS.getMaxRow() != 0) {
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				String[] stra = new String[16];
				stra[0] = i + "";
				stra[1] = tSSRS.GetText(i, 1); // 立案号
				stra[2] = tSSRS.GetText(i, 2); // 保单号
				stra[3] = tSSRS.GetText(i, 3); // 险种代码
				stra[4] = tSSRS.GetText(i, 4); // 险种名称
				stra[5] = tSSRS.GetText(i, 5); // 事故者姓名
				stra[6] = tSSRS.GetText(i, 6); // 立案日期
				stra[7] = tSSRS.GetText(i, 7); // 涉及赔付金额
				stra[8] = tSSRS.GetText(i, 8); // 保单生效日期
				stra[9] = tSSRS.GetText(i, 9); // 出险日期
				stra[10] = tSSRS.GetText(i, 10); // 出险类型
				stra[11] = tSSRS.GetText(i, 11); // 案件状态
				stra[12] = tSSRS.GetText(i, 12); // 调查件标志
				stra[13] = tSSRS.GetText(i, 13); // 扫描件标志
				stra[14] = tSSRS.GetText(i, 14); // 业务员
				stra[15] = tSSRS.GetText(i, 15); // 业务员号
			
				tListTable.add(stra);
			}
		} else {
			CError tError = new CError();
			tError.moduleName = "LLClaimBillBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}

		logger.debug("----------以下查询，是为单个变量赋值-----------");

		// 统计机构$=/DOFStatMngcom$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mMngCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();
        String tClaimTypeName ="";
        if("5".equals(mClaimType))
        	tClaimTypeName ="5日未结案件";
        else if("10".equals(mClaimType))
        	tClaimTypeName ="10日未结案件";
        else if("30".equals(mClaimType))
        	tClaimTypeName ="30日未结案件";
        else if("全部".equals(mClaimType))
        	tClaimTypeName ="全部";
        else
        	tClaimTypeName ="";
		// 统计人
		String Operator = mG.Operator;
		// 统计日期$=/DOFDate$
		String SysDate = PubFun.getCurrentDate();

		// 给模板中的变量赋值
		tTextTag.add("DOFMngCom", tMngCom); // 统计机构

		tTextTag.add("DOFOperator", Operator); // 统计人
		tTextTag.add("DOFDate", SysDate); // 统计日期
		tTextTag.add("DOFType", tClaimTypeName); // 统计类型
		tTextTag.add("DOFStartDate", mStartDate); // 统计起始起期
		tTextTag.add("DOFEndDate", mEndDate); // 统计结束止期

		logger.debug("********************************************");

		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);

		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

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
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealEndCaseBill() {
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		tXmlExport.createDocument("LLClaimEndCase.vts", "");

		// 新建一个ListTable的实例
		ListTable tListTable = new ListTable();
		tListTable.setName("DOF");
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

		logger.debug("---以下 查询列表$*/DOF/ROW/COL内容，并为列表赋值--");
		String rownumstr = "";
		String rownumstr2 = "";
		if(SysConst.DBTYPE_ORACLE.equals(SysConst.DBTYPE)){
			rownumstr =	" (select name from laagent where agentcode in (select agentcode from lcpol where polno=e.polno) and rownum=1),"; 
			rownumstr2 = " (select riskname from lmriskapp where riskcode in (select riskcode from lcpol where polno=e.polno) and rownum=1) riskname,";
		}else if(SysConst.DBTYPE_MYSQL.equals(SysConst.DBTYPE)){
			rownumstr =	" (select name from laagent where agentcode in (select agentcode from lcpol where polno=e.polno) limit 1),"; 
			rownumstr2 = " (select riskname from lmriskapp where riskcode in (select riskcode from lcpol where polno=e.polno) limit 1) riskname,"; 
		} 
		String strSQL = "select a.rgtno,"
			  + " (select contno from lcpol where polno=e.polno) contno,"
			  + " (select riskcode from lcpol where polno=e.polno) riskcode,"
			  + rownumstr2
			  + " b.customername,"
			  + " a.RgtDate,"
			  + " (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=a.rgtno),"
			  + " e.realpay,"
			  + " (e.standpay-e.realpay),"
			  + " (select cvalidate from lcpol where polno=e.polno) cvalidate,"
			  + " b.MedAccDate,b.AccDate,"
			  + " (select codename from ldcode where codetype='ngetdutykind' and code=e.getdutykind),"
			  + " (case a.CasePayType when '1' then '短期出险件' else '一般案件' end),"
			  + " (case (select count(*) from ljagetclaim where FeeOperationType='B' and otherno=a.rgtno) when 0 then (case a.CasePayType when '0' then '一般案件' when '5' then '简易案件' when '3' then '预付案件' else '' end) else '二次给付案件' end ),"
			  + " (case d.AuditConclusion when '0' then '给付或部分给付' when '1' then '全部拒付' end),"
			  + " (case e.GiveType when '0' then '给付' when '1' then '拒付' end),"
			  + " (case a.clmstate when '50' then '审批确认' when '60' then '完成' end),"
			  + " (select case count(*) when 0 then '无' else '有' end from es_doc_main where doccode = a.rgtno),"
			  + rownumstr
			  + " (select agentcode from lcpol where polno=e.polno)"
			  + " from llregister a, llcase b, LLClaimUWMain d ,llclaimdetail e"
			  + " where a.rgtno = b.rgtno"
			  + " and a.rgtno = e.clmno"
			  + " and a.rgtno = d.clmno"
			  + " and a.RgtClass='1'"
			  + " and a.clmstate='50'"
			  + " and a.endcasedate >=to_date('" + "?startdate?" + "','yyyy-mm-dd') and a.endcasedate<=to_date('" + "?enddate?" +"','yyyy-mm-dd')"
			  + " and a.mngcom like concat('" + "?mngcom?" + "','%')"
			  + " union select a.rgtno,"
			  + " (select contno from lcpol where polno=e.polno) contno,"
			  + " (select riskcode from lcpol where polno=e.polno) riskcode,"
			  + rownumstr2
			  + " b.customername,"
			  + " a.RgtDate,"
			  + " (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=a.rgtno),"
			  + " e.realpay,"
			  + " (e.standpay-e.realpay),"
			  + " (select cvalidate from lcpol where polno=e.polno) cvalidate,"
			  + " b.MedAccDate,b.AccDate,"
			  + " (select codename from ldcode where codetype='ngetdutykind' and code=e.getdutykind),"
			  + " (case a.CasePayType when '1' then '短期出险件' else '一般案件' end),"
			  + " (case (select count(*) from ljagetclaim where FeeOperationType='B' and otherno=a.rgtno) when 0 then (case a.CasePayType when '0' then '一般案件' when '5' then '简易案件' when '3' then '预付案件' else '' end) else '二次给付案件' end ),"
			  + " (case d.AuditConclusion when '0' then '给付或部分给付' when '1' then '全部拒付' end),"
			  + " (case e.GiveType when '0' then '给付' when '1' then '拒付' end),"
			  + " (case a.clmstate when '50' then '审批确认' when '60' then '完成' end),"
			  + " (select case count(*) when 0 then '无' else '有' end from es_doc_main where doccode = a.rgtno),"
			  + rownumstr
			  + " (select agentcode from lcpol where polno=e.polno)"
			  + " from llregister a, llcase b, LLClaimUWMain d ,llclaimdetail e"
			  + " where a.rgtno = b.rgtno"
			  + " and a.rgtno = e.clmno"
			  + " and a.rgtno = d.clmno"
			  + " and a.RgtClass='1'"
			  + " and a.clmstate='60'"
			  + " and a.modifydate >=to_date('" + "?startdate?" + "','yyyy-mm-dd') and a.modifydate<=to_date('" + "?startdate?" +"','yyyy-mm-dd')"
			  + " and a.mngcom like concat('" + "?mngcom?" + "','%')";
		   
		logger.debug("查询语句:strSQL==" + strSQL);
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql(strSQL);
		sqlbv11.put("mngcom", mMngCom);
		sqlbv11.put("startdate", mStartDate);
		sqlbv11.put("enddate", mEndDate);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv11);

		if (tSSRS.getMaxRow() != 0) {
			int k = 1;
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				if (i > 1 && !tSSRS.GetText(i, 1).equals(tSSRS.GetText(i - 1, 1))) {
					k = k + 1;
				}
				String[] stra = new String[25];
				stra[0] = k + "";
				stra[1] = tSSRS.GetText(i, 1); //立案号
				stra[2] = tSSRS.GetText(i, 2); //保单号
				stra[3] = tSSRS.GetText(i, 3); //险种代码
				stra[4] = tSSRS.GetText(i, 4); //险种名称
				stra[5] = tSSRS.GetText(i, 5); //事故者姓名
				stra[6] = tSSRS.GetText(i, 6); //立案日期
				stra[7] = tSSRS.GetText(i, 7); //签批同意日期
				stra[8] = tSSRS.GetText(i, 8); //赔付金额
				stra[9] = tSSRS.GetText(i, 9); //拒付金额
				stra[10] = tSSRS.GetText(i, 10); //保单生效日期
				stra[11] = tSSRS.GetText(i, 11); //医疗出险日期
				stra[12] = tSSRS.GetText(i, 12); //其他出险日期
				stra[13] = tSSRS.GetText(i, 13); //出险类型
				stra[14] = tSSRS.GetText(i, 14); //案件类型
				stra[15] = tSSRS.GetText(i, 15); //给付类型
				stra[16] = tSSRS.GetText(i, 16); //案件结论类型
				stra[17] = tSSRS.GetText(i, 17); //保项结论类型
				stra[18] = tSSRS.GetText(i, 18); //案件状态
				stra[19] = tSSRS.GetText(i, 19); //扫描件标志
				stra[20] = tSSRS.GetText(i, 20); //业务员
				stra[21] = tSSRS.GetText(i, 21); //业务员号
				tListTable.add(stra);
			}
		} else {
			CError tError = new CError();
			tError.moduleName = "LLClaimBillBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}

		logger.debug("----------以下查询，是为单个变量赋值-----------");

		// 统计机构$=/DOFStatMngcom$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mMngCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();

		// 统计人
		String Operator = mG.Operator;
		// 统计日期$=/DOFDate$
		String SysDate = PubFun.getCurrentDate();

		// 给模板中的变量赋值
		tTextTag.add("DOFMngCom", tMngCom); // 统计机构
		
		tTextTag.add("DOFStartDate", mStartDate); // 统计起期
		tTextTag.add("DOFEndDate", mEndDate); // 统计止期
		
		tTextTag.add("DOFOperator", Operator); // 统计人
		tTextTag.add("DOFDate", SysDate); // 统计日期

		logger.debug("********************************************");

		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);

		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		logger.debug("********************************************");
		logger.debug("---以下 将tXmlExport打包，返回前台--");
		// tXmlExport.outputDocumentToFile("c:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);
		logger.debug("xmlexport=" + tXmlExport);

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

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86";
		tG.ManageCom = "86";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StatiCode", "8632");// 统计机构
		tTransferData.setNameAndValue("DStartDate", "2005-01-06");// 应付起期
		tTransferData.setNameAndValue("DEndDate", "2005-11-09"); // 应付止期
		tTransferData.setNameAndValue("FStartDate", "2005-01-06");// 实付起期
		tTransferData.setNameAndValue("FEndDate", "2005-11-15"); // 实付止期
		tTransferData.setNameAndValue("PRBType", "1");// 清单类型
		tTransferData.setNameAndValue("PayMode", "");// 支付方式

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);

		LLPRBDealORFactPayBL tLLPRBDealORFactPayBL = new LLPRBDealORFactPayBL();
		if (tLLPRBDealORFactPayBL.submitData(tVData, "") == false) {
			logger.debug("----------理赔清单打印：应付和实付清单出错---------------");
		}
	}

}
