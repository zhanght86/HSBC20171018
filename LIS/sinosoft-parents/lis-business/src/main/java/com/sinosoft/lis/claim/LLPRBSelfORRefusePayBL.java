package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

/**
 * <p>Title: LLPRBSelfORRefusePayBL </p>
 * <p>Description:清单导出处理类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author liuyin
 * @version 1.0
 */


import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class LLPRBSelfORRefusePayBL {
private static Logger logger = Logger.getLogger(LLPRBSelfORRefusePayBL.class);
	public CErrors mErrors = new CErrors();
	private VData mInputData = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();

	/* 全局变量 */
	private String mManageCom = "";
	private String mStartDate = "";
	private String mEndDate = "";
	private String mNCLtype = "";
	private String mResultType = "";
	private String mLlclaimtypeCode = "";
	private MMap map = new MMap();
	int bingo = 0;
	private String CurrentDate = PubFun.getCurrentDate();

	private SSRS mSSRS = new SSRS();
	private SSRS kSSRS = new SSRS();
	private SSRS fSSRS = new SSRS();
	private SSRS bSSRS = new SSRS();
	private SSRS phoneSSRS = new SSRS();
	private SSRS ICDSSRS = new SSRS();
	private SSRS typeSSRS = new SSRS();
	private SSRS LLbnfSSRS = new SSRS();

	private String[][] mResult = null; // 需要生成excel的数据容器
	private static final int MAXLENGTH = 65; // 需要导出数据的列数dusx

	public LLPRBSelfORRefusePayBL() {
	}

	public static void main(String[] args) {
		GlobalInput tGI = new GlobalInput();
		tGI.ManageCom = "86";
		tGI.Operator = "aa";

		TransferData cTransferData = new TransferData();
		cTransferData.setNameAndValue("ManageCom", "8621");
		cTransferData.setNameAndValue("StartDate", "2006-10-21");
		cTransferData.setNameAndValue("EndDate", "2006-10-31");
		cTransferData.setNameAndValue("ResultType", "01");
		cTransferData.setNameAndValue("NCLType", "1");
		cTransferData.setNameAndValue("llclaimtypeCode", "0000");
		VData tVData = new VData();
		tVData.add(tGI);
		tVData.add(cTransferData);

		logger.debug("---------------");
		try {
			LLPRBSelfORRefusePayBL LLPRBSelfORRefusePayBL1 = new LLPRBSelfORRefusePayBL();
			LLPRBSelfORRefusePayBL1.submitData(tVData);
			if (LLPRBSelfORRefusePayBL1.mErrors.needDealError()) {
				for (int i = 0; i < LLPRBSelfORRefusePayBL1.mErrors
						.getErrorCount(); i++) {
					logger.debug(LLPRBSelfORRefusePayBL1.mErrors
							.getError(i).moduleName);
					logger.debug(LLPRBSelfORRefusePayBL1.mErrors
							.getError(i).functionName);
					logger.debug(LLPRBSelfORRefusePayBL1.mErrors
							.getError(i).errorMessage);
				}
			}
			logger.debug("---------------");
			logger.debug("end");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "LLPRBSelfORRefusePayBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData) {

		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		PubSubmit tPs = new PubSubmit();
		mInputData.clear();
		mInputData.add(map);
		if (!tPs.submitData(mInputData, "")) {
			CError tError = new CError();
			tError.moduleName = "LLPRBSelfORRefusePayBL";
			tError.functionName = "getContData";
			tError.errorMessage = "导出数据失败，" + tPs.mErrors.getFirstError();
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备传往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("End LLPRBSelfORRefusePayBL...");
		return true;
	}

	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		TransferData tTransferData = (TransferData) cInputData
				.getObjectByObjectName("TransferData", 0);
		mManageCom = (String) tTransferData.getValueByName("ManageCom");
		mStartDate = (String) tTransferData.getValueByName("StartDate");
		mResultType = (String) tTransferData.getValueByName("ResultType");
		mEndDate = (String) tTransferData.getValueByName("EndDate");
		mNCLtype = (String) tTransferData.getValueByName("NCLType");
		mLlclaimtypeCode = (String) tTransferData
				.getValueByName("llclaimtypeCode");
		return true;
	}

	private boolean dealData() {
		logger.debug("搜集范围为：生效对应日" + mStartDate + "至" + mEndDate
				+ "，管理机构：" + mManageCom + ",保项结论:" + mResultType + "申请类型: "
				+ mNCLtype + "理赔类型: " + mLlclaimtypeCode);
		String sql = "";
		String giveType = "";
		String comGrade = "";

		ExeSQL searchOneSQL = new ExeSQL();
		SSRS searchOneSsrs = new SSRS();
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql("Select substr(Comgrade,2,1) From ldcom Where comcode='"
				+ "?comcode?" + "'");
		sqlbv.put("comcode", mManageCom);
		searchOneSsrs = searchOneSQL
				.execSQL(sqlbv);
		
		int errd = searchOneSsrs.getMaxRow();
		if (errd == 0) {
			buildError("getprintData", "机构不存在");
			logger.debug("机构不存在");
			return false;
		}
		comGrade = searchOneSsrs.GetText(1, 1);

		if (comGrade.equals("1")) {
			buildError("getprintData", "无法打印总公司的清单");
			logger.debug("无法打印总公司的清单");
			logger.debug(mManageCom.substring(0, 4));
			return false;
		}
		String nCLtype = mNCLtype.trim().equals("1") ? "  and rgtobj='1' "
				: "  and rgtobj='2' ";
		String typeCode = "And g.code='" + "?code?" + "'";
		if (mLlclaimtypeCode.equals("0000")) {
			typeCode = " ";
		}
		//公共语句 rownum
		String row_sql="Select icdname From LDDisease Where trim(icdcode)=trim(j.accresult1) and rownum=1";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			row_sql="Select icdname From LDDisease Where trim(icdcode)=trim(j.accresult1) limit 0,1";
		}
		if (mResultType.equals("01")) // 给付
		{
			giveType = 0 + "";// Modify by zhaorx
								// 2006-11-01：若统计b.contno，无法区分领款人，因为一个b.contno下可能有多个不同的领款人。经与于睿沟通，最后还是取b.polno
			sql = "  Select a.Rgtno 赔案号, b.Riskcode 险种号码,f.Riskshortname 险种简称, g.codename 理赔类型, b.Getdutycode 保项代码, i.Getdutyname 保项名称, "
					+ " b.polno 保单号, h.Name 出险人, h.Birthday 生日, h.Sex 性别, b.Cvalidate 保单生效日, "
					+ " a.Accidentdate 意外事故发生日期, j.Accdate 出险日期, j.Accdentdesc 事故描述, k.Rptdate 报案日期, "
					+ " a.Rgtdate 立案日期, a.Accidentreason 出险原因代码, a.Operator 立案人, d.Auditper 审核人, "
					+ " (Select Count(distinct batno) From Llinqapply Where Clmno = a.Rgtno) 调查次数, d.Examper 审批人, d.Examdate 审批通过日期, "
					+ " b.customerno 出险人联系电话, (case when a.Assigneename is null then '空值' else a.Assigneename end) 受托人, (case when a.Assigneephone is null then '0' else a.Assigneephone end) 受托人联系电话, "
					+ " b.Givetype 保项结论, a.Rgtstate 简易案件标识, "
					+ " (case when (Select Sum(fee) From LLCaseReceipt Where Clmno = a.Rgtno) is null then 0 else(Select Sum(fee) From LLCaseReceipt Where Clmno = a.Rgtno) end) 账单金额, "
					+ " (case when (Select Sum(Orisum - Adjsum) From Llclaimdutyfee Where Clmno = a.Rgtno) is null then 0 else (Select Sum(Orisum - Adjsum) From Llclaimdutyfee Where Clmno = a.Rgtno) end) 责任外金额, b.Agentcode, "
					+ " b.Agentgroup, '0' 拒付金额, (case when (select codename from ldcode where code=b.Givereason and codetype='llprotestreason') is null then '空缺' else (select codename from ldcode where code=b.Givereason and codetype='llprotestreason') end) 拒付原因, "
					+ " (case when b.realpay is null then 0 else b.realpay end) 理赔金给付金额, "
					+ " (case when (select z.Payeename from llbnf z where z.clmno=b.clmno and z.polno = b.polno and z.Bnfno = '1' and z.bnfkind='A') is null then '0' else (select z.Payeename from llbnf z where z.clmno=b.clmno and z.polno = b.polno and z.Bnfno = '1' and z.bnfkind='A') end) 领款人,"
					+ " (case when (Select sum(Pay) From Llbalance Where (Feefinatype = 'TF' or SubFeeOperationType = 'C0502')  And ClmNo=a.rgtno And polno=b.polno) is null then 0 else (Select sum(Pay) From Llbalance Where (Feefinatype = 'TF' or SubFeeOperationType = 'C0502')  And ClmNo=a.rgtno And polno=b.polno) end) 退费, "
					+ " (case when (Select sum(Pay) From Llbalance Where Clmno = a.Rgtno And polno=b.polno and substr(FeeOperationType,1,1) in ('C','D')and pay<'0') is null then 0 else (Select sum(Pay) From Llbalance Where Clmno = a.Rgtno And polno=b.polno and substr(FeeOperationType,1,1) in ('C','D')and pay<'0') end) 扣费 ,"
					+ " d.ExamDate 审批日期 ,'0' 给付方式,(case when j.CureDesc is null then '无' else j.CureDesc end) 治疗情况,(case when (Select Hospitalname From Llcommendhospital Where hospitalcode=j.Hospitalcode ) is null then '无' else (Select Hospitalname From Llcommendhospital Where hospitalcode=j.Hospitalcode ) end)	治疗医院 , "
					+ " b.salechnl 业务类别,(case when j.dieflag is null then '0' else j.dieflag end)案件标识,(case when j.Accidentdetail is null then '无' else j.Accidentdetail end),(case when (Select icdname From LDDisease Where trim(icdcode)=trim(j.Accidentdetail)) is null then '无' else (Select icdname From LDDisease Where trim(icdcode)=trim(j.Accidentdetail)) end) ICD10代码, (case when j.accresult1 is null then '无' else j.accresult1 end) 出险结果1代码, "
					+ " (case when ("+row_sql+") is null then '无' else ("+row_sql+") end) 出险结果1,(case when j.accresult2 is null then '无' else j.accresult2 end) 出险结果2代码,(case when (Select icdname From LDDisease Where trim(icdcode)=trim(j.accresult2)) is null then '无' else (Select icdname From LDDisease Where trim(icdcode)=trim(j.accresult2)) end) 出险结果2, "
					+ " (case when (select z.name from llbnf z where z.clmno=b.clmno and z.polno = b.polno and z.Bnfno = '1' and z.bnfkind='A') is null then '0' else (select z.name from llbnf z where z.clmno=b.clmno and z.polno = b.polno and z.Bnfno = '1' and z.bnfkind='A') end) 受益人 "
					// 12月27日新增4个字段 二级机构 三级机构 申请人姓名 申请人与出险人关系 modify by yurui
					+ ", (Select Name From Ldcom Where a.mngcom Like concat(Trim(Comcode) , '%') And Comgrade = '02') 二级机构,"
					+ " (Select Name From Ldcom Where a.mngcom Like concat(Trim(Comcode) , '%') And Comgrade = '03') 三级机构,"
					+ " a.Rgtantname 申请人姓名,a.Relation 申请人与出险人关系,a.rgtantphone 申请人电话 "
					+ " From (Select * From Llregister Where Clmstate In ('50', '60') And Mngcom Like concat('"
					+ "?mngcom?"
					+ "','%')"
					+ nCLtype
					+ ") a, Llclaimdetail b, Llclaim c, "
					+ " Llclaimuwmain d, Lmrisk f, Ldperson h, Lmdutygetrela i, Llcase j, Llreport k,ldcode g "
					+ "  Where a.Rgtno = b.Clmno And a.Rgtno = c.Clmno And a.Rgtno = d.Clmno  And b.Givetype = '"
					+ giveType
					+ "' And "
					+ " f.Riskcode = b.Riskcode "
					+ " And b.Getdutycode = i.Getdutycode And "
					+ " j.Customerno = h.Customerno And j.Rgtno = a.Rgtno And k.Rptno = a.Rgtno    And g.Codetype = 'llclaimtype' And Trim(g.Code) = Trim(b.Getdutykind)  "
					+ typeCode
					+ "  And d.Examdate >= '"
					+ "?startdate?"
					+ "' And "
					+ " d.Examdate <= '"
					+ "?enddate?"
					+ "' "
					+ " Order By d.Examdate, a.Rgtno, b.Getdutycode ";

		} else if (mResultType.equals("02")) // 拒付
		{
			giveType = 1 + ""; // 2also
			sql = "  Select a.Rgtno 赔案号, b.Riskcode 险种号码, f.Riskshortname 险种简称,  g.codename 理赔类型, b.Getdutycode 保项代码, i.Getdutyname 保项名称, "
					+ " b.polno 保单号, h.Name 出险人, h.Birthday 生日, h.Sex 性别, b.Cvalidate 保单生效日, "
					+ " a.Accidentdate 意外事故发生日期, j.Accdate 出险日期, j.Accdentdesc 事故描述, k.Rptdate 报案日期, "
					+ " a.Rgtdate 立案日期, a.Accidentreason 出险原因代码, a.Operator 立案人, d.Auditper 审核人, "
					+ " (Select Count(distinct batno) From Llinqapply Where Clmno = a.Rgtno) 调查次数, d.Examper 审批人, d.Examdate 审批通过日期, "
					+ " b.customerno 出险人联系电话, (case when a.Assigneename is null then '空值' else a.Assigneename end) 受托人, (case when a.Assigneephone is null then '0' else a.Assigneephone end) 受托人联系电话, "
					+ " b.Givetype 保项结论, a.Rgtstate 简易案件标识, "
					+ " (case when (Select Sum(fee) From LLCaseReceipt Where Clmno = a.Rgtno) is null then 0 else (Select Sum(fee) From LLCaseReceipt Where Clmno = a.Rgtno) end) 账单金额, "
					+ " (case when (Select Sum(Orisum - Adjsum) From Llclaimdutyfee Where Clmno = a.Rgtno) is null then 0 else (Select Sum(Orisum - Adjsum) From Llclaimdutyfee Where Clmno = a.Rgtno) end) 责任外金额, b.Agentcode, "
					+ " b.Agentgroup, b.standpay 拒付金额, (case when (select codename from ldcode where code=b.Givereason and codetype='llprotestreason') is null then '空缺' else (select codename from ldcode where code=b.Givereason and codetype='llprotestreason') end) 拒付原因, '0', "
					+ " '0' 领款人, (case when (Select sum(Pay) From Llbalance Where (Feefinatype = 'TF' or SubFeeOperationType = 'C0502')  And ClmNo=a.rgtno And polno=b.polno) is null then 0 else (Select sum(Pay) From Llbalance Where (Feefinatype = 'TF' or SubFeeOperationType = 'C0502')  And ClmNo=a.rgtno And polno=b.polno) end) 退费, "
					+ " (case when (Select sum(Pay) From Llbalance Where Clmno = a.Rgtno And polno=b.polno and substr(FeeOperationType,1,1) in ('C','D')and pay<'0') is null then 0 else (Select sum(Pay) From Llbalance Where Clmno = a.Rgtno And polno=b.polno and substr(FeeOperationType,1,1) in ('C','D')and pay<'0') end) 扣费  , "
					+ " d.ExamDate 审批日期,'0' 给付方式,(case when j.CureDesc is null then '无' else j.CureDesc end) 治疗情况 ,(case when (Select Hospitalname From Llcommendhospital Where hospitalcode=j.Hospitalcode ) is null then '无' else (Select Hospitalname From Llcommendhospital Where hospitalcode=j.Hospitalcode ) end) 治疗医院 , "
					+ " b.salechnl 业务类别  ,(case when j.dieflag is null then '0' else j.dieflag end)案件标识,(case when j.Accidentdetail is null then '无' else j.Accidentdetail end),(case when (Select icdname From LDDisease Where trim(icdcode)=trim(j.Accidentdetail)) is null then '无' else (Select icdname From LDDisease Where trim(icdcode)=trim(j.Accidentdetail)) end) ICD10代码, "
					+ " (case when j.accresult1 is null then '无' else j.accresult1 end) 出险结果1代码,(case when ("+row_sql+") is null then '无' else ("+row_sql+") end) 出险结果1,(case when j.accresult2 is null then '无' else j.accresult2 end) 出险结果2代码, "
					+ " (case when (Select icdname From LDDisease Where trim(icdcode)=trim(j.accresult2)) is null then '无' else (Select icdname From LDDisease Where trim(icdcode)=trim(j.accresult2)) end) 出险结果2,'0' 受益人  "
					// 12月27日新增4个字段 二级机构 三级机构 申请人姓名 申请人与出险人关系 modify by yurui
					+ ", (Select Name From Ldcom Where a.mngcom Like concat(Trim(Comcode) , '%') And Comgrade = '02') 二级机构,"
					+ " (Select Name From Ldcom Where a.mngcom Like concat(Trim(Comcode) , '%') And Comgrade = '03') 三级机构,"
					+ " a.Rgtantname 申请人姓名,a.Relation 申请人与出险人关系,a.rgtantphone 申请人电话 "
					+ " From (Select * From Llregister Where Clmstate In ('50', '60') And Mngcom Like concat('"
					+ "?mngcom?"
					+ "','%')"
					+ nCLtype
					+ ") a, Llclaimdetail b, Llclaim c, "
					+ " Llclaimuwmain d, Lmrisk f,  Ldperson h, Lmdutygetrela i, Llcase j, Llreport k,ldcode g "
					+ "  Where a.Rgtno = b.Clmno And a.Rgtno = c.Clmno And a.Rgtno = d.Clmno  And b.Givetype = '"
					+ giveType
					+ "' And "
					+ " f.Riskcode = b.Riskcode "
					+ " And b.Getdutycode = i.Getdutycode And "
					+ " j.Customerno = h.Customerno And j.Rgtno = a.Rgtno And k.Rptno = a.Rgtno And g.Codetype = 'llclaimtype' And Trim(g.Code) = Trim(b.Getdutykind)   "
					+ typeCode
					+ "  And d.Examdate >= '"
					+ "?startdate?"
					+ "' And "
					+ " d.Examdate <= '"
					+ "?enddate?"
					+ "' "
					+ " Order By d.Examdate, a.Rgtno, b.Getdutycode ";

		} else // 全部
		{
			// sql = " Select a.Rgtno 赔案号, b.Riskcode 险种号码,f.Riskshortname 险种简称,
			// g.codename 理赔类型, b.Getdutycode 保项代码, i.Getdutyname 保项名称,"
			// + " b.polno 保单号, h.Name 出险人, h.Birthday 生日, h.Sex 性别, b.Cvalidate
			// 保单生效日,"
			// + " a.Accidentdate 意外事故发生日期, j.Accdate 出险日期, j.Accdentdesc 事故描述,
			// k.Rptdate 报案日期,"
			// + " a.Rgtdate 立案日期, a.Accidentreason 出险原因代码, a.Operator 立案人,
			// d.Auditper 审核人,"
			// + " (Select Count(*) From Llinqapply Where Clmno = a.Rgtno) 调查次数,
			// d.Examper 审批人, d.Examdate 审批通过日期,"
			// + " b.customerno 出险人联系电话, Nvl(a.Assigneename, '空值') 受托人,
			// Nvl(a.Assigneephone, '0') 受托人联系电话,"
			// + " b.Givetype 保项结论, a.Rgtstate 简易案件标识,"
			// + " Nvl((Select Sum(fee) From LLCaseReceipt Where Clmno =
			// a.Rgtno), '0') 账单金额,"
			// + " Nvl((Select Sum(Orisum - Adjsum) From Llclaimdutyfee Where
			// Clmno = a.Rgtno), '0') 责任外金额, b.Agentcode,"
			// + " b.Agentgroup, (Case b.GiveType when '0' then '0' When '1'
			// Then trim(b.standpay) End) 拒付金额, Nvl((select codename from ldcode
			// where code=b.Givereason and codetype='llprotestreason'), '空缺')
			// 拒付原因, "
			// + " Case b.GiveType when '1' then '0' When '0' Then
			// Trim((z.GetMoney)) End 理赔金给付金额,"
			// + " z.Payeename 领款人, Nvl((Select sum(Pay) From Llbalance Where
			// Feefinatype = 'TF' And ClmNo=a.rgtno And polno=b.polno), '0') 退费,
			// "
			// + " Nvl((Select sum(Pay) From Llbalance Where Clmno = a.Rgtno And
			// polno=b.polno and substr(FeeOperationType,1,1) in ('C','D')and
			// pay<'0'), '0') 扣费,d.ExamDate 审批日期 ,'0',nvl(j.CureDesc,'无')
			// 治疗情况,nvl((Select Hospitalname From Llcommendhospital Where
			// hospitalcode=j.Hospitalcode ),'无') 治疗医院 ,b.salechnl 业务类别
			// ,nvl(j.dieflag,'0')案件标识,nvl(j.Accidentdetail,'无'),nvl((Select
			// icdname From LDDisease Where
			// trim(icdcode)=trim(j.Accidentdetail)),'无') ICD10代码,
			// nvl(j.accresult1,'无') 出险结果1代码,nvl((Select icdname From LDDisease
			// Where trim(icdcode)=trim(j.accresult1) and rownum='1'),'无')
			// 出险结果1,nvl(j.accresult2,'无') 出险结果2代码,nvl((Select icdname From
			// LDDisease Where trim(icdcode)=trim(j.accresult2)),'无') 出险结果2 "
			// + " From (Select * From Llregister Where Clmstate In ('50', '60')
			// And Mngcom Like '" + mManageCom + "%'"+nCLtype+") a,
			// Llclaimdetail b, Llclaim c,"
			// + " Llclaimuwmain d, Lmrisk f, Ldperson h, Lmdutygetrela i,
			// Llcase j, Llreport k,ldcode g, llbnf z "
			// + " Where z.Clmno = a.Rgtno And z.polno=b.polno and a.Rgtno =
			// b.Clmno And a.Rgtno = c.Clmno And a.Rgtno = d.Clmno And
			// b.GiveType not in ('2') And "
			// + " f.Riskcode = b.Riskcode And b.Getdutycode = i.Getdutycode And
			// "
			// + " j.Customerno = h.Customerno And j.Rgtno = a.Rgtno And k.Rptno
			// = a.Rgtno And g.Codetype = 'llclaimtype' And Trim(g.Code) =
			// Trim(b.Getdutykind) " +typeCode+" And d.Examdate >= '" +
			// mStartDate + "' And "
			// + " d.Examdate <= '" + mEndDate + "' "
			// + " Order By d.Examdate, a.Rgtno, b.Getdutycode ";
			sql = "  Select a.Rgtno 赔案号, b.Riskcode 险种号码,f.Riskshortname 险种简称, g.codename 理赔类型, b.Getdutycode 保项代码, i.Getdutyname 保项名称, "
					+ " b.polno 保单号, h.Name 出险人, h.Birthday 生日, h.Sex 性别, b.Cvalidate 保单生效日, "
					+ " a.Accidentdate 意外事故发生日期, j.Accdate 出险日期, j.Accdentdesc 事故描述, k.Rptdate 报案日期, "
					+ " a.Rgtdate 立案日期, a.Accidentreason 出险原因代码, a.Operator 立案人, d.Auditper 审核人, "
					+ " (Select Count(distinct batno) From Llinqapply Where Clmno = a.Rgtno) 调查次数, d.Examper 审批人, d.Examdate 审批通过日期, "
					+ " b.customerno 出险人联系电话, (case when a.Assigneename is null then '空值' else a.Assigneename end) 受托人, (case when a.Assigneephone is null then '0' else a.Assigneephone end) 受托人联系电话, "
					+ " b.Givetype 保项结论, a.Rgtstate 简易案件标识, "
					+ " (case when (Select Sum(fee) From LLCaseReceipt Where Clmno = a.Rgtno) is null then 0 else (Select Sum(fee) From LLCaseReceipt Where Clmno = a.Rgtno) end) 账单金额, "
					+ " (case when (Select Sum(Orisum - Adjsum) From Llclaimdutyfee Where Clmno = a.Rgtno) is null then 0 else (Select Sum(Orisum - Adjsum) From Llclaimdutyfee Where Clmno = a.Rgtno) end) 责任外金额, b.Agentcode, "
					+ " b.Agentgroup, 0 拒付金额, (case when (select codename from ldcode where code=b.Givereason and codetype='llprotestreason') is null then '空缺' else (select codename from ldcode where code=b.Givereason and codetype='llprotestreason') end) 拒付原因, "
					+ " (case when b.realpay is null then 0 else b.realpay end) 理赔金给付金额, "
					+ " (case when (select z.Payeename from llbnf z where z.clmno=b.clmno and z.polno = b.polno and z.Bnfno = '1' and z.bnfkind='A') is null then '0' else (select z.Payeename from llbnf z where z.clmno=b.clmno and z.polno = b.polno and z.Bnfno = '1' and z.bnfkind='A') end) 领款人, "
					+ " (case when (Select sum(Pay) From Llbalance Where (Feefinatype = 'TF' or SubFeeOperationType = 'C0502')  And ClmNo=a.rgtno And polno=b.polno) is null then 0 else (Select sum(Pay) From Llbalance Where (Feefinatype = 'TF' or SubFeeOperationType = 'C0502')  And ClmNo=a.rgtno And polno=b.polno) end) 退费, "
					+ " (case when (Select sum(Pay) From Llbalance Where Clmno = a.Rgtno And polno=b.polno and substr(FeeOperationType,1,1) in ('C','D')and pay<'0') is null then 0 else (Select sum(Pay) From Llbalance Where Clmno = a.Rgtno And polno=b.polno and substr(FeeOperationType,1,1) in ('C','D')and pay<'0') end) 扣费 , "
					+ " d.ExamDate 审批日期 ,'0' 给付方式,(case when j.CureDesc is null then '无' else j.CureDesc end) 治疗情况,(case when (Select Hospitalname From Llcommendhospital Where hospitalcode=j.Hospitalcode ) is null then '无' else (Select Hospitalname From Llcommendhospital Where hospitalcode=j.Hospitalcode ) end)	治疗医院 , "
					+ " b.salechnl 业务类别,(case when j.dieflag is null then '0' else j.dieflag end)案件标识,(case when j.Accidentdetail is null then '无' else j.Accidentdetail end),(case when (Select icdname From LDDisease Where trim(icdcode)=trim(j.Accidentdetail)) is null then '无' else (Select icdname From LDDisease Where trim(icdcode)=trim(j.Accidentdetail)) end) ICD10代码, (case when j.accresult1 is null then '无' else j.accresult1 end) 出险结果1代码, "
					+ " (case when ("+row_sql+") is null then '无' else ("+row_sql+") end) 出险结果1,(case when j.accresult2 is null then '无' else j.accresult2 end) 出险结果2代码,(case when (Select icdname From LDDisease Where trim(icdcode)=trim(j.accresult2)) is null then '无' else (Select icdname From LDDisease Where trim(icdcode)=trim(j.accresult2)) end) 出险结果2, "
					+ " (case when (select z.name from llbnf z where z.clmno=b.clmno and z.polno = b.polno and z.Bnfno = '1' and z.bnfkind='A') is null then '0' else (select z.name from llbnf z where z.clmno=b.clmno and z.polno = b.polno and z.Bnfno = '1' and z.bnfkind='A') end) 受益人 "
					// 12月27日新增4个字段 二级机构 三级机构 申请人姓名 申请人与出险人关系 modify by yurui
					+ ", (Select Name From Ldcom Where a.mngcom Like concat(Trim(Comcode) , '%') And Comgrade = '02') 二级机构,"
					+ " (Select Name From Ldcom Where a.mngcom Like concat(Trim(Comcode) , '%') And Comgrade = '03') 三级机构,"
					+ " a.Rgtantname 申请人姓名,a.Relation 申请人与出险人关系,a.rgtantphone 申请人电话 "
					+ " From (Select * From Llregister Where Clmstate In ('50', '60') And Mngcom Like concat('"
					+ "?mngcom?"
					+ "','%')"
					+ nCLtype
					+ ") a, Llclaimdetail b, Llclaim c, "
					+ " Llclaimuwmain d, Lmrisk f, Ldperson h, Lmdutygetrela i, Llcase j, Llreport k,ldcode g "
					+ "  Where a.Rgtno = b.Clmno And a.Rgtno = c.Clmno And a.Rgtno = d.Clmno  And b.Givetype = '0' And "
					+ " f.Riskcode = b.Riskcode "
					+ " And b.Getdutycode = i.Getdutycode And "
					+ " j.Customerno = h.Customerno And j.Rgtno = a.Rgtno And k.Rptno = a.Rgtno And g.Codetype = 'llclaimtype' And Trim(g.Code) = Trim(b.Getdutykind)  "
					+ typeCode
					+ "  And d.Examdate >= '"
					+ "?startdate?"
					+ "' And "
					+ " d.Examdate <= '"
					+ "?enddate?"
					+ "' "
					// + " Order By d.Examdate, a.Rgtno, b.Getdutycode "
					+ " union "
					+ "  Select a.Rgtno 赔案号, b.Riskcode 险种号码, f.Riskshortname 险种简称,  g.codename 理赔类型, b.Getdutycode 保项代码, i.Getdutyname 保项名称, "
					+ " b.polno 保单号, h.Name 出险人, h.Birthday 生日, h.Sex 性别, b.Cvalidate 保单生效日, "
					+ " a.Accidentdate 意外事故发生日期, j.Accdate 出险日期, j.Accdentdesc 事故描述, k.Rptdate 报案日期, "
					+ " a.Rgtdate 立案日期, a.Accidentreason 出险原因代码, a.Operator 立案人, d.Auditper 审核人, "
					+ " (Select Count(distinct batno) From Llinqapply Where Clmno = a.Rgtno) 调查次数, d.Examper 审批人, d.Examdate 审批通过日期, "
					+ " b.customerno 出险人联系电话, (case when a.Assigneename is null then '空值' else a.Assigneename end) 受托人, (case when a.Assigneephone is null then '0' else a.Assigneephone end) 受托人联系电话, "
					+ " b.Givetype 保项结论, a.Rgtstate 简易案件标识, "
					+ " (case when (Select Sum(fee) From LLCaseReceipt Where Clmno = a.Rgtno) is null then 0 else (Select Sum(fee) From LLCaseReceipt Where Clmno = a.Rgtno) end) 账单金额, "
					+ " (case when (Select Sum(Orisum - Adjsum) From Llclaimdutyfee Where Clmno = a.Rgtno) is null then 0 else (Select Sum(Orisum - Adjsum) From Llclaimdutyfee Where Clmno = a.Rgtno) end) 责任外金额, b.Agentcode, "
					+ " b.Agentgroup, b.standpay 拒付金额, (case when (select codename from ldcode where code=b.Givereason and codetype='llprotestreason') is null then '空缺' else (select codename from ldcode where code=b.Givereason and codetype='llprotestreason') end) 拒付原因, 0 理赔金给付金额, "
					+ " '0' 领款人, (case when (Select sum(Pay) From Llbalance Where (Feefinatype = 'TF' or SubFeeOperationType = 'C0502')  And ClmNo=a.rgtno And polno=b.polno) is null then 0 else (Select sum(Pay) From Llbalance Where (Feefinatype = 'TF' or SubFeeOperationType = 'C0502')  And ClmNo=a.rgtno And polno=b.polno) end) 退费, "
					+ " (case when (Select sum(Pay) From Llbalance Where Clmno = a.Rgtno And polno=b.polno and substr(FeeOperationType,1,1) in ('C','D')and pay<'0') is null then 0 else (Select sum(Pay) From Llbalance Where Clmno = a.Rgtno And polno=b.polno and substr(FeeOperationType,1,1) in ('C','D')and pay<'0') end) 扣费  , "
					+ " d.ExamDate 审批日期,'0' 给付方式,(case when j.CureDesc is null then '无' else j.CureDesc end) 治疗情况 ,(case when (Select Hospitalname From Llcommendhospital Where hospitalcode=j.Hospitalcode ) is null then '无' else (Select Hospitalname From Llcommendhospital Where hospitalcode=j.Hospitalcode ) end) 治疗医院 , "
					+ " b.salechnl 业务类别  ,(case when j.dieflag is null then '0' else j.dieflag end)案件标识,(case when j.Accidentdetail is null then '无' else j.Accidentdetail end),(case when (Select icdname From LDDisease Where trim(icdcode)=trim(j.Accidentdetail)) is null then '无' else (Select icdname From LDDisease Where trim(icdcode)=trim(j.Accidentdetail)) end) ICD10代码, "
					+ " (case when j.accresult1 is null then '无' else j.accresult1 end) 出险结果1代码,(case when ("+row_sql+") is null then '无' else ("+row_sql+") end) 出险结果1, "
					+ " (case when j.accresult2 is null then '无' else j.accresult2 end) 出险结果2代码,(case when (Select icdname From LDDisease Where trim(icdcode)=trim(j.accresult2)) is null then '无' else (Select icdname From LDDisease Where trim(icdcode)=trim(j.accresult2)) end) 出险结果2,'0' 受益人  "
					// 12月27日新增4个字段 二级机构 三级机构 申请人姓名 申请人与出险人关系 modify by yurui
					+ ", (Select Name From Ldcom Where a.mngcom Like concat(Trim(Comcode) , '%') And Comgrade = '02') 二级机构,"
					+ " (Select Name From Ldcom Where a.mngcom Like concat(Trim(Comcode) , '%') And Comgrade = '03') 三级机构,"
					+ " a.Rgtantname 申请人姓名,a.Relation 申请人与出险人关系,a.rgtantphone 申请人电话 "
					+ " From (Select * From Llregister Where Clmstate In ('50', '60') And Mngcom Like concat('"
					+ "?mngcom?"
					+ "','%')"
					+ nCLtype
					+ ") a, Llclaimdetail b, Llclaim c, "
					+ " Llclaimuwmain d, Lmrisk f,  Ldperson h, Lmdutygetrela i, Llcase j, Llreport k,ldcode g "
					+ "  Where a.Rgtno = b.Clmno And a.Rgtno = c.Clmno And a.Rgtno = d.Clmno  And b.Givetype = '1' And "
					+ " f.Riskcode = b.Riskcode "
					+ " And b.Getdutycode = i.Getdutycode And "
					+ " j.Customerno = h.Customerno And j.Rgtno = a.Rgtno And k.Rptno = a.Rgtno And g.Codetype = 'llclaimtype' And Trim(g.Code) = Trim(b.Getdutykind)   "
					+ typeCode
					+ "  And d.Examdate >= '"
					+ "?startdate?"
					+ "' And " + " d.Examdate <= '" + "?enddate?" + "' ";
			// + " Order By d.Examdate, a.Rgtno, b.Getdutycode ";
		}
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("code", mLlclaimtypeCode);
		sqlbv1.put("mngcom", mManageCom);
		sqlbv1.put("startdate", mStartDate);
		sqlbv1.put("enddate", mEndDate);
		ExeSQL tExeSQL = new ExeSQL();
		ExeSQL tkExeSQL = new ExeSQL();
		mSSRS = tExeSQL.execSQL(sqlbv1);

		if (mSSRS == null || mSSRS.getMaxRow() == 0) {
			CError tError = new CError();
			tError.moduleName = "LLPRBSelfORRefusePayBL";
			tError.functionName = "getContData";
			tError.errorMessage = "此日期段无满足条件的数据";
			this.mErrors.addOneError(tError);
			return false;
		}

		logger.debug("----------------------开始导出数据的处理-----------------------");
		mResult = new String[mSSRS.getMaxRow()][MAXLENGTH];

		for (int i = 1; i <= mSSRS.getMaxRow(); i++) {
			try {
				String colContent2 = mSSRS.GetText(i, 1); // 赔案号
				String colContent3 = mSSRS.GetText(i, 2); // 险种代码
				String colContent4 = mSSRS.GetText(i, 3); // 险种简称
				String colContent5 = mSSRS.GetText(i, 4); // 理赔类型
				String colContent6 = mSSRS.GetText(i, 5); // 保项（保项责任）代码
				String colContent7 = mSSRS.GetText(i, 6); // 保项名称
				String colContent8 = mSSRS.GetText(i, 7); // 保单号
				String colContent9 = mSSRS.GetText(i, 8); // 出险人

				// 计算年龄
				String colContent10 = PubFun.calInterval(mSSRS.GetText(i, 9),
						CurrentDate, "Y")
						+ "";
				String colContent11 = ""; // 性别
				// 判断性别 0 男 1女
				if (mSSRS.GetText(i, 10).equals("0")) {
					colContent11 = "男";
				} else {
					colContent11 = "女";
				}
				String colContent12 = mSSRS.GetText(i, 11); // 保单生效日期
				String colContent13 = mSSRS.GetText(i, 12); // 意外事故发生日期
				String colContent14 = mSSRS.GetText(i, 13); // 出险日期
				// int arrayLength= colContent14.length();
				// String [] aaa= new String[arrayLength];

				// 2007-1-8针对事故描述中的用户错误输入'造成无法insert的问题进行的修改。
				String colContent15 = (mSSRS.GetText(i, 14))
						.replaceAll("'", ""); // 事故描述
				if (mSSRS.GetText(i, 14).length() > 2000) {
					colContent15 = mSSRS.GetText(i, 14).substring(0, 1999);
				}
				String colContent16 = mSSRS.GetText(i, 15); // 报案日期
				String colContent17 = mSSRS.GetText(i, 16); // 立案日期
				String colContent18 = mSSRS.GetText(i, 17); // 出险原因代码
				if (colContent18.equals("1")) {
					colContent18 = "意外";
				} else {
					colContent18 = "疾病";
				}
				String colContent19 = mSSRS.GetText(i, 18); // 立案人
				String colContent20 = mSSRS.GetText(i, 19); // 审核人
				String colContent21 = mSSRS.GetText(i, 20); // 调查次数
				String colContent22 = mSSRS.GetText(i, 21); // 审批人
				String colContent23 = mSSRS.GetText(i, 22); // 审批通过日期
				String colContent24 = mSSRS.GetText(i, 23); // 出险人联系电话
				String phoneSql = "select (case when phone is null then '0' else phone end) from Lcaddress a where a.customerno='"
						+ "?customerno?" + "'";
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				sqlbv5.sql(phoneSql);
				sqlbv5.put("customerno", colContent24);
				phoneSSRS = tkExeSQL.execSQL(sqlbv5);
				if (phoneSSRS == null || phoneSSRS.getMaxRow() == 0) {
					colContent24 = "0";
				} else {
					colContent24 = phoneSSRS.GetText(1, 1);
				}

				String colContent25 = mSSRS.GetText(i, 24);// 受托人
				String colContent26 = mSSRS.GetText(i, 25);// 受托人联系电话
				String colContent27 = mSSRS.GetText(i, 26); // 保项结论
				if (colContent27.equals("0")) {
					colContent27 = "给付";
				} else if (colContent27.equals("1")) {
					colContent27 = "拒付";
				}

				String colContent28 = mSSRS.GetText(i, 27); // 简易案件标识
				if (colContent28.equals("11")) {
					colContent28 = "否";
				} else if (colContent28.equals("01")) {
					colContent28 = "是";
				}

				String colContent29 = mSSRS.GetText(i, 28); // 账单金额
				String colContent30 = mSSRS.GetText(i, 29); // 责任外金额
				String colContent31 = mSSRS.GetText(i, 30); // 服务人员代码
				String colContent32 = mSSRS.GetText(i, 31); // 服务人员区部组代码
				String colContent33 = mSSRS.GetText(i, 32); // 拒付金额
				String colContent34 = mSSRS.GetText(i, 33); // 拒付原因
				String colContent35 = mSSRS.GetText(i, 34); // 理赔金给付金额
				String colContent36 = mSSRS.GetText(i, 35); // 领款人
				String colContent37 = mSSRS.GetText(i, 36); // 退费
				String colContent38 = mSSRS.GetText(i, 37); // 扣费
				String colContent39 = "否"; // 调查标识（是否）
				String colContent40 = "无记录"; // 调查阳性标识
				String colContent41 = "无记录"; // 调查人姓名1
				String colContent42 = "无记录"; // 调查人姓名2
				String colContent43 = "无记录"; // 调查人姓名3
				String colContent44 = "无记录"; // 首次提起调查日期
				String colContent45 = "无记录"; // 调查结束日期
				String colContent46 = "无记录"; // 调查费用
				String colContent47 = mSSRS.GetText(i, 38); // 提交审批日期
				String colContent48 = mSSRS.GetText(i, 39); // 给付方式
				// 领款人,给付方式
				String LLbnfSql = "Select (case when payeename is null then '无' else payeename end),(case when Casepaymode is null then '给付方式未定' else Casepaymode end) From llbnf Where polno='"
						+ "?polno?"
						+ "'And bnfno='1' And clmno='"
						+ "?clmno?" + "'";
				SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
				sqlbv6.sql(LLbnfSql);
				sqlbv6.put("polno", colContent8);
				sqlbv6.put("clmno", colContent2);
				LLbnfSSRS = tkExeSQL.execSQL(sqlbv6);
				if (LLbnfSSRS == null || LLbnfSSRS.getMaxRow() == 0) {
					colContent36 = "无"; // /领款人
					colContent48 = "给付方式未定";// 给付方式
				} else {
					colContent36 = LLbnfSSRS.GetText(1, 1); // 调查费用
					colContent48 = LLbnfSSRS.GetText(1, 2); // 给付方式
				}

				String colContent49 = mSSRS.GetText(i, 40); // 治疗类别
				String colContent50 = mSSRS.GetText(i, 41); // 治疗医院
				String colContent51 = mSSRS.GetText(i, 42); // 业务类别
				String colContent52 = mSSRS.GetText(i, 44); // //ICD10代码
				String colContent53 = ""; // 实付日期
				String bSql = "select (case when Max(confdate) is null then to_date('1800-09-09','yyyy-mm-dd') else Max(confdate) end) from ljagetclaim a Where  a.otherno='"
						+ "?otherno?" + "' And a.polno='" + "?polno?" + "'";
				SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
				sqlbv7.sql(bSql);
				sqlbv7.put("otherno", colContent2);
				sqlbv7.put("polno", colContent8);
				bSSRS = tkExeSQL.execSQL(sqlbv7);
				if (bSSRS.GetText(1, 1).equals("1800-09-09")) {
					colContent53 = "9999-99-99"; // 实付日期
				} else {
					colContent53 = bSSRS.GetText(1, 1); // 实付日期
				}

				String colContent54 = mSSRS.GetText(i, 43); // 案件标识

				if (colContent54.equals("1")) {
					colContent54 = "死亡";
				} else {
					colContent54 = "";
				}
				if (colContent49.equals("01")) {
					colContent49 = "门诊";
				} else if (colContent49.equals("02")) {
					colContent49 = "住院";
				} else if (colContent49.equals("03")) {
					colContent49 = "综合";
				}

				String colContent55 = mSSRS.GetText(i, 45); // //ICD10代码
				String colContent56 = mSSRS.GetText(i, 46); // //出险结果1代码
				String colContent57 = mSSRS.GetText(i, 47); // //出险结果1
				String colContent58 = mSSRS.GetText(i, 48); // //出险结果2代码
				String colContent59 = mSSRS.GetText(i, 49); // //出险结果2
				String colContent60 = mSSRS.GetText(i, 50); // //受益人dusx
				// 12月27日新增4个字段 二级机构 三级机构 申请人姓名 申请人与出险人关系 modify by yurui
				String colContent61 = mSSRS.GetText(i, 51); // //二级机构
				String colContent62 = mSSRS.GetText(i, 52); // //三级机构
				String colContent63 = mSSRS.GetText(i, 53); // //申请人姓名
				String colContent64 = mSSRS.GetText(i, 54); // //申请人与出险人关系
				String colContent65 = mSSRS.GetText(i, 55); // 申请人电话
				String[] relationype = new String[] { "本人", "保单服务人员", "同事",
						"朋友", "亲戚", "妻子", "丈夫", "儿女", "父亲", "母亲", "其他" };

				if (colContent64.equals("GX01")) {
					colContent64 = relationype[0];
				} else if (colContent64.equals("GX02")) {
					colContent64 = relationype[1];
				} else if (colContent64.equals("GX03")) {
					colContent64 = relationype[2];
				} else if (colContent64.equals("GX04")) {
					colContent64 = relationype[3];
				} else if (colContent64.equals("GX05")) {
					colContent64 = relationype[4];
				} else if (colContent64.equals("GX06")) {
					colContent64 = relationype[5];
				} else if (colContent64.equals("GX07")) {
					colContent64 = relationype[6];
				} else if (colContent64.equals("GX08")) {
					colContent64 = relationype[7];
				} else if (colContent64.equals("GX09")) {
					colContent64 = relationype[8];
				} else if (colContent64.equals("GX10")) {
					colContent64 = relationype[9];
				} else {
					colContent64 = relationype[10];
				}
				if (Integer.parseInt(mSSRS.GetText(i, 42)) == 1) {
					colContent51 = "个人";
				} else if (Integer.parseInt(mSSRS.GetText(i, 42)) == 3) {
					colContent51 = "银代";
				}
				String[] paytype = new String[] { "现金", "现金支票", "转帐支票", "银行代付",
						"", "POS付款", "网上银行" };
				if (!colContent48.equals("给付方式未定")) {
					colContent48 = paytype[Integer.parseInt(colContent48) - 1];
				}

				if (Integer.parseInt(colContent21) != 0) {
					String kSql = " Select min(a.ApplyDate) 首次提起调查日期,max(a.Inqenddate) 调查结束日期,'',max((case when c.inqper1 is null then '无记录' else c.inqper1 end))调查人姓名1,max((case when c.inqper2 is null then '无记录' else c.inqper2 end)) 调查人姓名2 "
							+ " From Llinqapply a,  llinqcourse c "
							+ " Where a.Clmno = '"
							+ "?clmno?"
							+ "'   And c.clmno=a.clmno And c.inqno=a.inqno ";

					String typeSql = " select count(distinct masflag) from llinqconclusion where clmno= '"
							+ "?clmno?" + "' " + " and masflag='1' ";
					SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
					sqlbv8.sql(kSql);
					sqlbv8.put("clmno", colContent2);
					kSSRS = tkExeSQL.execSQL(sqlbv8);
					int tableRow = kSSRS.getMaxRow();
					SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
					sqlbv9.sql(typeSql);
					sqlbv9.put("clmno", colContent2);
					typeSSRS = tkExeSQL.execSQL(sqlbv9);
					int tableRowType = typeSSRS.getMaxRow();

					String fSql = " Select (case when sum(feesum) is null then 0 else sum(feesum) end) 调查费用 From Llinqapply a, Llinqfee b Where a.Clmno = '"
							+ "?Clmno?"
							+ "'  And a.Clmno = b.Clmno And a.inqno=b.inqno";
					SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
					sqlbv10.sql(fSql);
					sqlbv10.put("Clmno", colContent2);
					fSSRS = tkExeSQL.execSQL(sqlbv10);

					colContent39 = "是"; // 调查标识（是否）
					colContent40 = typeSSRS.GetText(tableRowType, 1); // 调查阳性标识
					if (colContent40.equals("0")) {
						colContent40 = "非阳性";
					} else if (colContent40.equals("1")) {
						colContent40 = "阳性";
					} else {
						colContent40 = "无";
					}
					colContent41 = kSSRS.GetText(1, 4); // 调查人姓名1
					colContent42 = kSSRS.GetText(1, 5); // 调查人姓名2
					if (tableRow >= 2) {
						colContent43 = kSSRS.GetText(2, 4); // 调查人姓名3
					} else {
						colContent43 = "无"; // 调查人姓名3
					}
					colContent44 = this.getCorrectDataFormat(kSSRS
							.GetText(1, 1)); // 首次提起调查日期
					colContent45 = this.getCorrectDataFormat(kSSRS.GetText(
							tableRow, 2)); // 调查结束日期
					if (fSSRS == null || fSSRS.getMaxRow() == 0) {
						colContent46 = "0"; // 调查费用
					} else {
						colContent46 = fSSRS.GetText(1, 1); // 调查费用
					}
				}

				/*-----------------------准备往生成excel的二维数组-----------------*/
				mResult[i - 1][0] = i + ""; // 序号
				mResult[i - 1][1] = colContent2;
				mResult[i - 1][2] = colContent3;
				mResult[i - 1][3] = colContent4;
				mResult[i - 1][4] = colContent5;
				mResult[i - 1][5] = colContent6;
				mResult[i - 1][6] = colContent7;
				mResult[i - 1][7] = colContent8;
				mResult[i - 1][8] = colContent9;
				mResult[i - 1][9] = colContent10;
				mResult[i - 1][10] = colContent11;
				mResult[i - 1][11] = colContent12;
				mResult[i - 1][12] = colContent13;
				mResult[i - 1][13] = colContent14;
				mResult[i - 1][14] = colContent15;
				mResult[i - 1][15] = colContent16;
				mResult[i - 1][16] = colContent17;
				mResult[i - 1][17] = colContent18;
				mResult[i - 1][18] = colContent19;
				mResult[i - 1][19] = colContent20;
				mResult[i - 1][20] = colContent21;
				mResult[i - 1][21] = colContent22;
				mResult[i - 1][22] = colContent23;
				mResult[i - 1][23] = colContent24;
				mResult[i - 1][24] = colContent25;
				mResult[i - 1][25] = colContent26;
				mResult[i - 1][26] = colContent27;
				mResult[i - 1][27] = colContent28;
				mResult[i - 1][28] = colContent29;
				mResult[i - 1][29] = colContent30;
				mResult[i - 1][30] = colContent31;
				mResult[i - 1][31] = colContent32;
				mResult[i - 1][32] = colContent33;
				mResult[i - 1][33] = colContent34;
				mResult[i - 1][34] = colContent35;
				mResult[i - 1][35] = colContent36;
				mResult[i - 1][36] = colContent37;
				mResult[i - 1][37] = colContent38;
				mResult[i - 1][38] = colContent39;
				mResult[i - 1][39] = colContent40;
				mResult[i - 1][40] = colContent41;
				mResult[i - 1][41] = colContent42;
				mResult[i - 1][42] = colContent43;
				mResult[i - 1][43] = colContent44;
				mResult[i - 1][44] = colContent45;
				mResult[i - 1][45] = colContent46;
				mResult[i - 1][46] = colContent47;
				mResult[i - 1][47] = colContent48;
				mResult[i - 1][48] = colContent49;
				mResult[i - 1][49] = colContent50;
				mResult[i - 1][50] = colContent51;
				mResult[i - 1][51] = colContent53;
				mResult[i - 1][52] = colContent54;// 案件标识
				mResult[i - 1][53] = colContent52;
				mResult[i - 1][54] = colContent55;
				mResult[i - 1][55] = colContent56;
				mResult[i - 1][56] = colContent57;
				mResult[i - 1][57] = colContent58;
				mResult[i - 1][58] = colContent59;
				mResult[i - 1][59] = colContent61;
				mResult[i - 1][60] = colContent62;
				mResult[i - 1][61] = colContent63;
				mResult[i - 1][62] = colContent64;
				mResult[i - 1][63] = colContent65;

				/*----------------------准备结束-------------------------------*/
				if (i == 1) {
					String truncateStr = "DELETE FROM LLRepColligateBJ";
					SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
					sqlbv11.sql(truncateStr);
					map.put(sqlbv11, "DELETE");
				}

				bingo++;
				String emptyStr = "'" + "cont1" + "'";
				String insStr = "insert into LLRepColligateBJ value(Repid, Mngcom, Startdate, Enddate, Colid, Makedate, Maketime, Modifydate, Modifytime, Operator,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,Remark,c16,c17,c18,c19,c20,c21,c22,c23,c24,c25,c26,c27,c28,c29,c30,c31,c32,c33,c34,c35,c36,c37,c38,c39,c40,c41,c42,c43,c44,c45,c46,c47,c48,c49,c50,c51,c52,C53,c54,c55,c56,c57,c58,c59,c61,c62,c63,c64,c65)values( "
						+ ""
						+ emptyStr
						+ ", "
						+ ""
						+ 0
						+ ", "
						+ ""
						+ 0
						+ ", "
						+ ""
						+ 0
						+ ", "
						+ ""
						+ 0
						+ ", "
						+ "'"
						+ 20050909
						+ "', "
						+ ""
						+ 0
						+ ", "
						+ "'"
						+ 20050909
						+ "', "
						+ ""
						+ 0
						+ ", "
						+ ""
						+ 0
						+ ", "
						+ "'"
						+ "?cont2?"
						+ "',  "
						+ "'"
						+ "?cont3?"
						+ "',  "
						+ "'"
						+ "?cont4?"
						+ "',  "
						+ "'"
						+ "?cont5?"
						+ "',  "
						+ "'"
						+ "?cont6?"
						+ "',  "
						+ "'"
						+ "?cont7?"
						+ "',  "
						+ "'"
						+ "?cont8?"
						+ "',  "
						+ "'"
						+ "?cont9?"
						+ "',  "
						+ "'"
						+ "?cont10?"
						+ "', "
						+ "'"
						+ "?cont11?"
						+ "', "
						+ "'"
						+ "?cont12?"
						+ "', "
						+ "'"
						+ "?cont13?"
						+ "', "
						+ "'"
						+ "?cont14?"
						+ "', "
						+ "'"
						+ "?cont15?"
						+ "', "
						+ "'"
						+ "?cont16?"
						+ "', "
						+ "'"
						+ "?cont17?"
						+ "', "
						+ "'"
						+ "?cont18?"
						+ "', "
						+ "'"
						+ "?cont19?"
						+ "', "
						+ "'"
						+ "?cont20?"
						+ "', "
						+ "'"
						+ "?cont21?"
						+ "', "
						+ "'"
						+ "?cont22?"
						+ "', "
						+ "'"
						+ "?cont23?"
						+ "', "
						+ "'"
						+ "?cont24?"
						+ "', "
						+ "'"
						+ "?cont25?"
						+ "', "
						+ "'"
						+ "?cont26?"
						+ "', "
						+ "'"
						+ "?cont27?"
						+ "', "
						+ "'"
						+ "?cont28?"
						+ "', "
						+ "'"
						+ "?cont29?"
						+ "', "
						+ "'"
						+ "?cont30?"
						+ "', "
						+ "'"
						+ "?cont31?"
						+ "', "
						+ "'"
						+ "?cont32?"
						+ "', "
						+ "'"
						+ "?cont33?"
						+ "', "
						+ "'"
						+ "?cont34?"
						+ "', "
						+ "'"
						+ "?cont35?"
						+ "', "
						+ "'"
						+ "?cont36?"
						+ "', "
						+ "'"
						+ "?cont37?"
						+ "', "
						+ "'"
						+ "?cont38?"
						+ "', "
						+ "'"
						+ "?cont39?"
						+ "', "
						+ "'"
						+ "?cont40?"
						+ "', "
						+ "'"
						+ "?cont41?"
						+ "', "
						+ "'"
						+ "?cont42?"
						+ "', "
						+ "'"
						+ "?cont43?"
						+ "', "
						+ "'"
						+ "?cont44?"
						+ "', "
						+ "'"
						+ "?cont45?"
						+ "', "
						+ "'"
						+ "?cont46?"
						+ "', "
						+ "'"
						+ "?cont47?"
						+ "', "
						+ "'"
						+ "?cont48?"
						+ "', "
						+ "'"
						+ "?cont49?"
						+ "', "
						+ "'"
						+ "?cont50?"
						+ "', "
						+ "'"
						+ "?cont51?"
						+ "', "
						+ "'"
						+ "?cont54?"
						+ "', "
						+ "'"
						+ "?cont53?"
						+ "', "
						+ "'"
						+ "?cont52?"
						+ "', "
						+ "'"
						+ "?cont55?"
						+ "', "
						+ "'"
						+ "?cont56?"
						+ "', "
						+ "'"
						+ "?cont57?"
						+ "', "
						+ "'"
						+ "?cont58?"
						+ "', "
						+ "'"
						+ "?cont59?"
						+ "', "
						+ "'"
						+ "?cont61?"
						+ "', "
						+ "'"
						+ "?cont62?"
						+ "', "
						+ "'"
						+ "?cont63?"
						+ "', "
						+ "'"
						+ "?cont64?"
						+ "', "
						+ "'"
						+ "?cont65?" + "' " + ")";
				SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
				sqlbv12.sql(insStr);
				sqlbv12.put("cont1", bingo);
				sqlbv12.put("cont2", colContent2);
				sqlbv12.put("cont3", colContent3);
				sqlbv12.put("cont4", colContent4);
				sqlbv12.put("cont5", colContent5);
				sqlbv12.put("cont6", colContent6);
				sqlbv12.put("cont7", colContent7);
				sqlbv12.put("cont8", colContent8);
				sqlbv12.put("cont9", colContent9);
				sqlbv12.put("cont10", colContent10);
				sqlbv12.put("cont11", colContent11);
				sqlbv12.put("cont12", this.getCorrectDataFormat(colContent12));
				sqlbv12.put("cont13", this.getCorrectDataFormat(colContent13));
				sqlbv12.put("cont14", this.getCorrectDataFormat(colContent14));
				sqlbv12.put("cont15", colContent15);
				sqlbv12.put("cont16", this.getCorrectDataFormat(colContent16));
				sqlbv12.put("cont17", this.getCorrectDataFormat(colContent17));
				sqlbv12.put("cont18", colContent18);
				sqlbv12.put("cont19", colContent19);
				sqlbv12.put("cont20", colContent20);
				sqlbv12.put("cont21", colContent21);
				sqlbv12.put("cont22", colContent22);
				sqlbv12.put("cont23", this.getCorrectDataFormat(colContent23));
				sqlbv12.put("cont24", colContent24);
				sqlbv12.put("cont25", colContent25);
				sqlbv12.put("cont26", colContent26);
				sqlbv12.put("cont27", colContent27);
				sqlbv12.put("cont28", colContent28);
				sqlbv12.put("cont29", colContent29);
				sqlbv12.put("cont30", colContent30);
				sqlbv12.put("cont31", colContent31);
				sqlbv12.put("cont32", colContent32);
				sqlbv12.put("cont33", colContent33);
				sqlbv12.put("cont34", colContent34);
				sqlbv12.put("cont35", colContent35);
				sqlbv12.put("cont36", colContent36);
				sqlbv12.put("cont37", colContent37);
				sqlbv12.put("cont38", colContent38);
				sqlbv12.put("cont39", colContent39);
				sqlbv12.put("cont40", colContent40);
				sqlbv12.put("cont41", colContent41);
				sqlbv12.put("cont42", colContent42);
				sqlbv12.put("cont43", colContent43);
				sqlbv12.put("cont44", colContent44);
				sqlbv12.put("cont45", colContent45);
				sqlbv12.put("cont46", colContent46);
				sqlbv12.put("cont47", this.getCorrectDataFormat(colContent47));
				sqlbv12.put("cont48", colContent48);
				sqlbv12.put("cont49", colContent49);
				sqlbv12.put("cont50", colContent50);
				sqlbv12.put("cont51", colContent51);
				sqlbv12.put("cont54", colContent54);
				sqlbv12.put("cont53", this.getCorrectDataFormat(colContent53));
				sqlbv12.put("cont52", colContent52);
				sqlbv12.put("cont55", colContent55);
				sqlbv12.put("cont56", colContent56);
				sqlbv12.put("cont57", colContent57);
				sqlbv12.put("cont58", colContent58);
				sqlbv12.put("cont59", colContent59);
				//sqlbv12.put("cont60", colContent60);
				sqlbv12.put("cont61", colContent61);
				sqlbv12.put("cont62", colContent62);
				sqlbv12.put("cont63", colContent63);
				sqlbv12.put("cont64", colContent64);
				sqlbv12.put("cont65", colContent65);
				
				map.put(sqlbv12, "INSERT");
			} catch (Exception ex) {
				buildError("getprintData", "取数失败！！");
				logger.debug("没有需要打印的信息");
				return false;
			}
		}
		return true;
	}

	private String getCorrectDataFormat(String str) {
		String inputStr = str.trim();
		int strLength = inputStr.length();
		String strOne = inputStr.substring(0, 4);
		String strTwo = inputStr.substring(5, 7);
		String strThree = inputStr.substring(8, 10);
		return strOne + strTwo + strThree;
	}

	private boolean prepareOutputData() {
		return true;
	}

	/**
	 * 返回结果数据
	 * 
	 * @return String[][]
	 */
	public String[][] getResult() {
		return mResult;
	}

}
