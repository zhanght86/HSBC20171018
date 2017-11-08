package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.ReportPubFun;
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
 * @author 刘岩松
 * function :print InputEfficiency or print CheckEfficiency Business Logic layer
 * @version 1.0
 * @date 2003-04-04
 */

public class ScanLisBL {
private static Logger logger = Logger.getLogger(ScanLisBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	/**
	 * 管理机构
	 */
	private String strMngCom;
	/**
	 * 分公司名称
	 */
	private String mFenName;
	/**
	 * 中支名称
	 */
	private String mZhongName;

	/**
	 * 起始日期
	 */
	private String strScanStartDate;
	/**
	 * 终止日期
	 */
	private String strScanEndDate;
	/**
	 * 业务类型 TB－新契约 LP－理赔 BQ－保全
	 */
	private String strBusiType;
	/**
	 * 单证类型 新契约：00-全部；01-投保书类；02-通知书类；03-其他类
	 */
	private String strBusiPaperType;
	/**
	 * 扫描批次号
	 */
	private String strScanBatchNo;
	private String mScanType; // 扫描批次号
	private String mSubType; // 扫描批次号
	private String mOperaterNo; // 扫描批次号
	private String strSQL;
	// private String strNoticeType;
	private TransferData mTransferData = new TransferData();

	public ScanLisBL() {
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

		if (!dealData()) {
			return false;
		}

		return true;
	}

	/***************************************************************************
	 * Name : getInputData() function :receive data from jsp and check date
	 * check :judge whether startdate begin enddate return :true or false
	 */
	private boolean getInputData(VData cInputData) {
		// strMngCom = (String) cInputData.get(0);
		// strScanDate = (String) cInputData.get(1);
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		strMngCom = (String) mTransferData.getValueByName("ManageCom");
		strScanStartDate = (String) mTransferData
				.getValueByName("ScanStartDate");
		strScanEndDate = (String) mTransferData.getValueByName("ScanEndDate");
		strBusiType = (String) mTransferData.getValueByName("BusiType");
		strBusiPaperType = (String) mTransferData
				.getValueByName("BusiPaperType");
		strScanBatchNo = (String) mTransferData.getValueByName("ScanBatchNo");
		mScanType = (String) mTransferData.getValueByName("ScanType"); // 扫描批次号
		mSubType = (String) mTransferData.getValueByName("SubType"); // 扫描批次号
		mOperaterNo = (String) mTransferData.getValueByName("OperaterNo"); // 扫描批次号
		

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
		cError.moduleName = "ScanLisBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/***************************************************************************
	 * name : getPrintData() function :get print data parameter : null return
	 * :true or false
	 */

	private boolean getPrintData() {
		XmlExport xmlexport = new XmlExport();
		xmlexport.createDocument("ScanLis.vts", "printer");
		TextTag aTextTag = new TextTag();
		ListTable aListTable = new ListTable();
		aListTable.setName("LIST");

		// 根据传入的管理机构查询中心支公司。
		String strComSQL = "";
		int tComLength = strMngCom.length();
		if (tComLength <= 6) {
			strComSQL = "select a.comcode,a.name,a.upcomcode,"
					+ "(select name from ldcom b where b.comcode=a.upcomcode)"
					+ " from ldcom a where a.comgrade='03'"
					+ " and a.comcode like concat('" + "?strMngCom?" + "','%')"
					+ " order by a.comcode";
		} else { // 当机构长度大于6位时，该机构为营销服务部，应取其前六位以确定所属中支及分公司。
			strComSQL = "select a.comcode,a.name,a.upcomcode,"
					+ "(select name from ldcom b where b.comcode=a.upcomcode)"
					+ " from ldcom a where a.comgrade='03'"
					+ " and a.comcode like concat('" + "?strMngCom1?"
					+ "','%')" + " order by a.comcode";
		}
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strComSQL);
		sqlbv1.put("strMngCom", strMngCom);
		sqlbv1.put("strMngCom1", strMngCom);
		int dataCount = 0;
		int SumCount =0;
		int PageCount =0;
		ExeSQL ComExeSQL = new ExeSQL();
		SSRS ComSSRS = new SSRS();
		ComSSRS = ComExeSQL.execSQL(sqlbv1);
		int Count_ComSSRS = ComSSRS.getMaxRow();
		String managecom = strMngCom;
		if (Count_ComSSRS <= 0) {
			return false;
		}
		for (int i = 1; i <= Count_ComSSRS; i++) {
			String BranchComCode = ComSSRS.GetText(i, 1); // 中心支公司代码
			String BranchComName = ComSSRS.GetText(i, 2); // 中心支公司名称
			String FilialeComCode = ComSSRS.GetText(i, 3);
			; // 分公司代码
			String FilialeComName = ComSSRS.GetText(i, 4);
			; // 分公司名称
			if (tComLength <= 6) {
				managecom = BranchComCode;
			}
			String tSql = "select a.doccode,a.docid, a.managecom,a.subtype, "
				+ "a.numpages,a.makedate, a.scanoperator,a.docremark"
				+ " from es_doc_main a where 1=1"
				+ReportPubFun.getWherePart("a.scanoperator", ReportPubFun.getParameterStr(mOperaterNo,"?mOperaterNo?"))
	            +ReportPubFun.getWherePart("a.Makedate", ReportPubFun.getParameterStr(strScanStartDate,"?strScanStartDate?"), ReportPubFun.getParameterStr(strScanEndDate,"?strScanEndDate?"), 1)
	            +ReportPubFun.getWherePartLike("a.ManageCom", ReportPubFun.getParameterStr(managecom,"?managecom?"))
	            + "  and a.busstype like 'TB%'"
	            +ReportPubFun.getWherePart("a.subtype", ReportPubFun.getParameterStr(mSubType,"?mSubType?"))
	            ; 
			logger.debug("统计类型："+mScanType);
			if(mScanType !=null){
				if(mScanType.equals("个险")){
					tSql = tSql+" and substr(a.doccode,1,4) ='8611' order by a.makedate";
				}else if(mScanType.equals("中介")){
					tSql = tSql+" and substr(a.doccode,1,4) ='8621' order by a.makedate";
				}else if(mScanType.equals("简易")){
					tSql = tSql+" and substr(a.doccode,1,4) ='8616' order by a.makedate";
				}else if(mScanType.equals("银代")){
					tSql = tSql+" and substr(a.doccode,1,4) in ('8615','8625','8635') order by a.makedate";
				}else{
					tSql = tSql+" order by a.makedate ";
				}
			}
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSql);
			sqlbv2.put("mOperaterNo", mOperaterNo);
			sqlbv2.put("strScanStartDate", strScanStartDate);
			sqlbv2.put("strScanEndDate", strScanEndDate);
			sqlbv2.put("managecom", managecom);
			sqlbv2.put("mSubType", mSubType);
			ExeSQL ListExeSQL = new ExeSQL();
			SSRS ListSSRS = new SSRS();
			ListSSRS = ListExeSQL.execSQL(sqlbv2);
			int ListCount = ListSSRS.getMaxRow();
			SumCount=SumCount+ListCount;
			if (ListCount <= 0) {
				logger.debug("中支公司：" + BranchComCode + "("
						+ BranchComName + ")下无数据。");
				continue;
			} else {
				dataCount++;
				String[] col1 = new String[9];
				col1[0] = "序号";
				col1[1] = "印刷号";
				col1[2] = "流水号";
				col1[3] = "管理机构";
				col1[4] = "单证类型";
				col1[5] = "单证页数";
				col1[6] = "扫描日期";
				col1[7] = "操作员工号";
				col1[8] = "备注";
				String[] col2 = new String[9];
				col2[0] = "分公司:";
				col2[1] = "" + FilialeComName;
				col2[2] = "中心支公司: ";
				col2[3] = BranchComName;
				col2[4] = "";
				col2[5] = "";
				col2[6] = "";
				col2[7] = "";
				col2[8] = "";
				aListTable.add(col2);
				aListTable.add(col1);

			}
			for (int j = 1; j <= ListSSRS.MaxRow; j++) {
				String[] cols = new String[9];
				cols[0] = j + "";
				cols[1] = ListSSRS.GetText(j, 1);
				cols[2] = ListSSRS.GetText(j, 2);
				cols[3] = ListSSRS.GetText(j, 3);
				cols[4] = ListSSRS.GetText(j, 4);
				cols[5] = ListSSRS.GetText(j, 5);
				PageCount =PageCount+Integer.parseInt(ListSSRS.GetText(j, 5));
				cols[6] = ListSSRS.GetText(j, 6);
				cols[7] = ListSSRS.GetText(j, 7);
				cols[8] = ListSSRS.GetText(j, 8);
				aListTable.add(cols);
			}
			String[] col5 = new String[9];
			col5[0] = "";
			col5[1] = "";
			col5[2] = "";
			col5[3] = "";
			col5[4] = "";
			col5[5] = "";
			col5[6] = "";
			col5[7] = "";
			col5[8] = "";
			aListTable.add(col5);
		}
		String[] col = new String[10];
		xmlexport.addListTable(aListTable, col);
		aTextTag.add("StatiStartDate", StrTool.getChnDate(strScanStartDate));
		aTextTag.add("StatiEndDate", StrTool.getChnDate(strScanEndDate));
		aTextTag.add("PrintDate", PubFun.getCurrentDate());
		aTextTag.add("Sum", SumCount);
		aTextTag.add("PageSum", PageCount);

		xmlexport.addTextTag(aTextTag);

		mResult.addElement(xmlexport);
		if (dataCount == 0) {
			buildError("getInputData", "没有可以打印的数据");
			return false;
		}
		return true;
		// 选择临时文件读取目录
//		ListTable alistTable = new ListTable(); // Create a ListTable instance
//		alistTable.setName("RISK"); // Appoint to a sepcial flag
//
//		ExeSQL exeSql = new ExeSQL();
//		SSRS testSSRS = new SSRS();
//		testSSRS = exeSql
//				.execSQL("select sysvarvalue From ldsysvar where sysvar='DataPrintCombine'");
//		String strTemplatePath = testSSRS.GetText(1, 1); // 数据库中的路径-模板路径D:/lis/ui/f1print/NCLtemplate/
//		// String strTemplatePath = "D:/vsssino/ui/f1print/NCLtemplate/";
//
//		/**
//		 * @todo 查询管理机构，扫描只统计到中支以上的机构 如果录入总公司则查询出所有分公司的数据
//		 *       如果录入分公司则查询出该分公司下所有中支的数据
//		 */
//		ExeSQL comdSQL = new ExeSQL();
//		String tSQL = "";
//		String tSQL2 = "";
//		boolean haveBatchNo = false;
//
//		if (strMngCom != null) {
//			if (strMngCom.length() == 2) {
//				mFenName = "总公司";
//				mZhongName = "";
//			} else if (strMngCom.length() == 4) {
//				tSQL = "Select Name From ldcom Where comcode='" + strMngCom
//						+ "'";
//				SSRS comSsrs = comdSQL.execSQL(tSQL);
//				mFenName = comSsrs.GetText(1, 1);
//				mZhongName = "";
//			} else if (strMngCom.length() == 6) {
//				tSQL = "Select Name From ldcom Where comcode='"
//						+ strMngCom.substring(0, 4) + "'";
//				tSQL2 = "Select Name From ldcom Where comcode='" + strMngCom
//						+ "'";
//				SSRS comSsrs = comdSQL.execSQL(tSQL);
//				mFenName = comSsrs.GetText(1, 1);
//				comSsrs = comdSQL.execSQL(tSQL2);
//				mZhongName = comSsrs.GetText(1, 1);
//			} else {
//				/**
//				 * 如果不录入扫描批次号作为统计条件，则必须录入管理机构，如果录入的管理机构 不符合要求则在此进行错误处理
//				 */
//				if (strScanBatchNo == null && strScanBatchNo.equals("")) {
//					buildError("getPrintData", "管理机构只能选择中支及中支以上机构！");
//					return false;
//				}
//			}
//		}
//		/**
//		 * @todo 根据选择的单证类型拼入相应的SQL语句
//		 */
//		String tSubType = "";
//		if (strBusiPaperType != null && !strBusiPaperType.equals("")) {
//			if (strBusiPaperType.equals("01")) {
//				/**
//				 * 投保书类 UA001 个人寿险投保书 UA015 个人寿险投保书(支持多主险) UA060 团险投保书 UA061
//				 * 团险投保书 UA006 银代投保书及授权划款声明 UA002 多保通吉祥卡投保书（保险单） UF001 暂收首期保险费收据
//				 * UA003 途中关爱商务卡保险单暨保险费专用发票 UA004 五福一心（成人款）投保书 UA005
//				 * 五福一心（少儿款）投保书五福一心（少儿款）投保书 UA081 银代万能险投保书 UF002 委托银行代扣保险费协议书
//				 * UO060 委托银行代扣保险费协议书 TB000 新契约不需要打印单证 UA009 多保通如意卡保险单 UR001
//				 * 业务员告知书
//				 */
//				tSubType = "('TB000','UA001','UA015','UA060','UA061','UF001','UF002','UO060','UA006','UA002','UA003',"
//						+ "'UA004','UA005','UA081','UA009','UR001')";
//			} else if (strBusiPaperType.equals("02")) {
//				/**
//				 * 通知书 UN003 体检通知书(手写） UN012 生调通知书 UN002 新契约变更通知书 UN063
//				 * 契约内容变更通知书_新契约修改事项 UN064 契约内容变更通知书_客户合并 UN066 契约内容变更通知书_核保要求
//				 * UN067 契约内容变更通知书_核保问卷 UN068 契约内容变更通知书_核保结论 UN013
//				 * 新契约变更通知书(客户合并) UN011 核保通知书 UN007 个单保费余额退费通知书 UN008 个单撤保退费通知书
//				 * UN005 个单逾期划款通知书 UN006 个单逾期通知书 UN070 团单保费余额退费通知书 UN071
//				 * 团单撤保退费通知书 UN001 催缴通知书 UN016 补费通知书
//				 */
//				tSubType = " ('UN003','UN012','UN002','UN063','UN064',"
//						+ "'UN066','UN067','UN068','UN013','UN011','UN012','UN015','UN017','UN018','UN019','UN020','UN021','UN022','UN023','UN024','UN025','UN026','UN027','UN007','UN008','UN005',"
//						+ "'UN006','UN070','UN071','UN001','UN016')";
//
//			} else if (strBusiPaperType.equals("03")) {
//				/**
//				 * UA007 申请补充告知声明 UA008 出单前撤保申请 UN060 团单保险费专用发票 UN004 回单样本 UN061
//				 * 短期险保单 UN062 短期险人名清单
//				 */
//				tSubType = "('UA007',"
//						+ "'UA008','UN060','UN004','UN061','UN062')";
//
//			}
//		}
//
//		/**
//		 * @todo 需要通过扫描批次号进行循环查询，如果录入扫描批次号则直接利用录入的扫描批次号查询
//		 */
//
//		String tBatchNoSQL = "select distinct scanno from es_doc_main where 1=1 and managecom like '"
//				+ strMngCom
//				+ "%'"
//				+ " and makedate between '"
//				+ strScanStartDate
//				+ "' and '"
//				+ strScanEndDate
//				+ "'"
//				+ " and busstype='" + strBusiType + "'";
//		if (strScanBatchNo != null && !strScanBatchNo.equals("")) {
//			haveBatchNo = true;
//			tBatchNoSQL = "select distinct scanno from es_doc_main where 1=1 ";
//			if (strScanStartDate != null && !strScanStartDate.equals("")
//					&& strScanEndDate != null && !strScanEndDate.equals("")) {
//				tBatchNoSQL = tBatchNoSQL + " and makedate between '"
//						+ strScanStartDate + "' and '" + strScanEndDate + "'";
//			}
//			if (strMngCom != null && !strMngCom.equals("")) {
//				tBatchNoSQL = tBatchNoSQL + " and managecom like '" + strMngCom
//						+ "%'";
//			}
//			if (strBusiType != null && !strBusiType.equals("")) {
//				tBatchNoSQL = tBatchNoSQL + " and busstype='" + strBusiType
//						+ "'";
//			}
//			tBatchNoSQL = tBatchNoSQL + " and scanno='" + strScanBatchNo + "'";
//
//			/**
//			 * @todo 如果用扫描批次号查询，根据扫描批次号查询出机构名称
//			 */
//			String aSQL = "select managecom from es_doc_main where scanno='"
//					+ strScanBatchNo + "' and rownum=1";
//			ExeSQL aExeSQL = new ExeSQL();
//			SSRS aSSRS = aExeSQL.execSQL(aSQL);
//			if (aSSRS != null && aSSRS.getMaxRow() > 0) {
//				if (aSSRS.GetText(1, 1).equals("86")) {
//					mFenName = "总公司";
//					mZhongName = "";
//
//				} else if (aSSRS.GetText(1, 1).trim().length() == 4) {
//					tSQL = "Select Name From ldcom Where comcode='"
//							+ aSSRS.GetText(1, 1).trim() + "'";
//					SSRS comSsrs = comdSQL.execSQL(tSQL);
//					mFenName = comSsrs.GetText(1, 1);
//					mZhongName = "";
//
//				} else if (aSSRS.GetText(1, 1).trim().length() == 6) {
//					tSQL = "Select Name From ldcom Where comcode='"
//							+ aSSRS.GetText(1, 1).trim().substring(0, 4) + "'";
//					tSQL2 = "Select Name From ldcom Where comcode='"
//							+ aSSRS.GetText(1, 1).trim() + "'";
//					SSRS comSsrs = comdSQL.execSQL(tSQL);
//					mFenName = comSsrs.GetText(1, 1);
//					comSsrs = comdSQL.execSQL(tSQL2);
//					mZhongName = comSsrs.GetText(1, 1);
//
//				} else if (aSSRS.GetText(1, 1).trim().length() == 8) {
//					tSQL = "Select Name From ldcom Where comcode='"
//							+ aSSRS.GetText(1, 1).substring(0, 4) + "'";
//					tSQL2 = "Select Name From ldcom Where comcode='"
//							+ aSSRS.GetText(1, 1).substring(0, 6) + "'";
//					SSRS comSsrs = comdSQL.execSQL(tSQL);
//					mFenName = comSsrs.GetText(1, 1);
//					comSsrs = comdSQL.execSQL(tSQL2);
//					mZhongName = comSsrs.GetText(1, 1);
//
//				}
//			}
//
//		}
//		if (tSubType != null && !tSubType.equals("")) {
//			tBatchNoSQL = tBatchNoSQL + " and subtype in " + tSubType;
//		}
//
//		ExeSQL tExeSQL = new ExeSQL();
//		SSRS tScanSSRS = tExeSQL.execSQL(tBatchNoSQL);
//
//		if (tScanSSRS.getMaxRow() == 0) {
//			buildError("getPrintData", "未查到相关数据！");
//			return false;
//		}
//
//		int tTotal = 0;
//		for (int i = 0; i < tScanSSRS.getMaxRow(); i++) {
//
//			String[] cols0 = new String[4];
//			cols0[0] = "";
//			cols0[1] = "";
//			cols0[2] = "";
//			cols0[3] = "";
//			alistTable.add(cols0);
//
//			String[] cols1 = new String[4];
//			cols1[0] = "扫描批次号：";
//			cols1[1] = tScanSSRS.GetText(i + 1, 1);
//			cols1[2] = "";
//			cols1[3] = "";
//			alistTable.add(cols1);
//
//			String[] cols2 = new String[4];
//			cols2[0] = "序号";
//			cols2[1] = "单证类型";
//			cols2[2] = "单证号码";
//			cols2[3] = "扫描日期";
//			alistTable.add(cols2);
//
//			/**
//			 * @todo 通过循环每一次的扫描批次号查询具体的清单信息
//			 */
//			strSQL = " select (select a.subtypename" + " from es_doc_def a"
//					+ " where (a.subtype) = (c.subtype))," + " c.doccode,"
//					+ " c.makedate," + " c.subtype," + " c.scanno"
//					+ " from es_doc_main c" + "  where c.managecom like '"
//					+ strMngCom + "%'" + " and c.makedate between '"
//					+ strScanStartDate + "' and '" + strScanEndDate + "'"
//					+ "  and c.busstype = '" + strBusiType + "'"
//					+ " and c.scanno='" + tScanSSRS.GetText(i + 1, 1) + "'";
//			if (haveBatchNo) {
//				strSQL = " select (select a.subtypename" + " from es_doc_def a"
//						+ " where (a.subtype) = (c.subtype))," + " c.doccode,"
//						+ " c.makedate," + " c.subtype," + " c.scanno"
//						+ " from es_doc_main c" + " where 1=1";
//				if (strScanStartDate != null && !strScanStartDate.equals("")
//						&& strScanEndDate != null && !strScanEndDate.equals("")) {
//					strSQL = strSQL + " and c.makedate between '"
//							+ strScanStartDate + "' and '" + strScanEndDate
//							+ "'";
//				}
//				if (strMngCom != null && !strMngCom.equals("")) {
//					strSQL = strSQL + " and c.managecom like '" + strMngCom
//							+ "%'";
//				}
//				if (strBusiType != null && !strBusiType.equals("")) {
//					strSQL = strSQL + " and c.busstype='" + strBusiType + "'";
//				}
//				strSQL = strSQL + " and c.scanno='"
//						+ tScanSSRS.GetText(i + 1, 1) + "'";
//
//			}
//
//			/**
//			 * @todo 根据选择的单证类型拼入相应的where条件
//			 */
//			if (tSubType != null && !tSubType.equals("")) {
//				strSQL = strSQL + " and c.subtype in " + tSubType;
//			}
//			strSQL = strSQL + " order by c.subtype";
//
//			ExeSQL exeSQL = new ExeSQL();
//			SSRS ssrs = exeSQL.execSQL(strSQL);
//			// 将数据装入表单
//			for (int j = 1; j <= ssrs.getMaxRow(); j++) {
//				// 定义模版对应的数组
//				String[] cols = new String[4];
//				cols[0] = j + "";
//				cols[1] = ssrs.GetText(j, 1);
//				cols[2] = ssrs.GetText(j, 2);
//				cols[3] = ssrs.GetText(j, 3);
//				alistTable.add(cols);
//			}
//			tTotal = tTotal + ssrs.getMaxRow();
//
//		}
//
//		TextTag texttag = new TextTag(); // Create a TextTag instance
//		XmlExport xmlexport = new XmlExport(); // Create a XmlExport instance
//		xmlexport.createDocument("ScanLis.vts", "printer"); // appoint to a
//															// special output
//															// .vts file
//		String[] col = new String[1];
//		xmlexport.addListTable(alistTable, col);
//
//		texttag.add("PrintDate", PubFun.getCurrentDate());
//		texttag.add("FenName", mFenName);
//		texttag.add("ZhongName", mZhongName);
//		texttag.add("Total", tTotal);
//
//		if (texttag.size() > 0) {
//			xmlexport.addTextTag(texttag);
//		}
//
//		// xmlexport.outputDocumentToFile("D:\\", "testHZM"); //输出xml文档到文件
//		mResult.clear();
//		mResult.addElement(xmlexport);
//		return true;

	}

	private boolean dealData() {

		if (!getPrintData()) {
			return false;
		}
		return true;

	}

	/*
	 * @function :in order to debug
	 */
	public static void main(String[] args) {
		String tStrMngCom = "86110000";
		String tIssueDate = "2005-08-05";
		String tAgentGroup = "0003003003";
		String tNoticeType = "UA011";

		VData tVData = new VData();
		tVData.addElement(tStrMngCom);
		tVData.addElement(tIssueDate);
		tVData.addElement(tAgentGroup);
		tVData.addElement(tNoticeType);

		ScanLisUI tScanLisUI = new ScanLisUI();
		tScanLisUI.submitData(tVData, "PRINT");

	}
}
