<%@include file="/i18n/language.jsp"%>
<%
//程序名称：LRSearchSave.jsp
//程序功能：再保查询
//创建日期：2008-2-20
//创建人  ：zhangbin
%>

<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.report.f1report.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.reinsure.tools.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.reinsure.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
	System.out.println("start");
  CError cError = new CError();
  boolean operFlag=true;
	String tRela  = "";
	String FlagStr = "";
	String Content = "";
	String strResult = "";
	
	//在此设置导出Excel的列名，应与sql语句取出的域相对应
	GlobalInput tGlobalInput = new GlobalInput();
	tGlobalInput = (GlobalInput) session.getAttribute("GI");

	RIExportExcel.Format format = new RIExportExcel.Format();
	ArrayList listCell = new ArrayList();
	ArrayList listLB = new ArrayList();
	ArrayList listColWidth = new ArrayList();
	format.mListCell = listCell;
	format.mListBL = listLB;
	format.mListColWidth = listColWidth;

	RIExportExcel.Cell tCell = null;
	RIExportExcel.ListBlock tLB = null;

	String tManageCom = tGlobalInput.ComCode;
	String startDate = request.getParameter("StartDate");
	String endDate = request.getParameter("EndDate");
	listColWidth.add(new String[] { "0", "5000" });
	
	tLB = new RIExportExcel.ListBlock("001");
	
	String tEventType = request.getParameter("EventType");;
  
  String tStartDate 	= request.getParameter("StartDate");
  String tEndDate 		= request.getParameter("EndDate");
  String tContNo 			= request.getParameter("ContNo");
  String tGrpContNo 	= request.getParameter("GrpContNo");
  String tInsuredNo 	= request.getParameter("InsuredNo");
  String tInsuredName = request.getParameter("InsuredName");
  String tTempType 		= request.getParameter("TempType");
  String tRIcomCode 	= request.getParameter("RIcomCode");
  String tRIContNo 		= request.getParameter("RIContNo");
  String tOperateType = request.getParameter("OperateType");
  String tContType 		= request.getParameter("ContType");
  String tAccumulateDefNO	= request.getParameter("AccumulateDefNO");
  
  System.out.println("tStartDate: "		+ tStartDate );
  System.out.println("tEndDate: "			+ tEndDate);
  System.out.println("tEventType: "		+ tEventType);
  System.out.println("tGrpContNo: "		+ tGrpContNo);
  System.out.println("tContNo: "			+ tContNo);
  System.out.println("tInsuredNo: "		+ tInsuredNo);
  System.out.println("tInsuredName: "	+ tInsuredName);
  System.out.println("tTempType: "		+ tTempType);
  System.out.println("tRIcomCode: "		+ tRIcomCode);
  System.out.println("tRIContNo: "		+ tRIContNo);
  System.out.println("tContType: "		+ tContType);
  System.out.println("tAccumulateDefNO: "+ tAccumulateDefNO);
  
  TransferData exportDate = new TransferData();
  
  GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getAttribute("GI");
	
	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
  tVData.addElement(tG);
  
  tVData.addElement(exportDate);
	ExeSQL tExeSQL = new ExeSQL();
	StringBuffer sqlStrBuffer = new StringBuffer();
	if(tEventType.equals("01")){ //新单
		sqlStrBuffer.append(" select x.A1,x.A2,max(x.A3),max(x.A4),max(x.A5),max(x.A6),max(x.A7),max(x.A8),x.A9,max(x.A11),max(x.A12),max(x.A13),sum(x.A14),sum(x.A15),sum(x.A16),max(x.A18),max(x.A19),max(x.A20),max(x.A21),max(x.A22) getdate from (");
		sqlStrBuffer.append(" select (case when a.grpcontno='00000000000000000000' then a.contno else a.grpcontno end) A1, a.InsuredNo A2,a.InsuredName A3,(case when a.InsuredSex=0 then '"+"男"+"' else '"+"女"+"' end) A4,a.insuredage A5,a.standbydate1 A6,a.cvalidate A7, a.enddate A8,a.riskcode A9,a.dutycode A10,a.amnt A11,a.riskamnt A12,a.accamnt A13,b.CessionAmount A14,b.PremChang A15,b.Commchang A16,a.eventno A17,a.standbydouble2 A18,a.standbydouble3 A19,a.peakline A20, a.payyears A21, a.getdate A22 ");
		sqlStrBuffer.append(" from RIPolRecordBake a,RIRecordTrace b where b.EventNo=a.EventNo  and a.EventType in('01','02') and a.GetDate between to_date('"+tStartDate+"') and to_date('"+tEndDate+"')") ;
		if(tContType.equals("1")){
			if(tContNo!=null&&!tContNo.equals("")){
				sqlStrBuffer.append(" and a.contno='"+tContNo+"'");
			}
		}else{
			if(tGrpContNo!=null&&!tGrpContNo.equals("")){
				sqlStrBuffer.append(" and a.grpcontno='"+tGrpContNo+"'");
			}
		}
		if(tInsuredNo!=null&&!tInsuredNo.equals("")){
			sqlStrBuffer.append(" and a.InsuredNo='"+tInsuredNo+"'");
		}
		if(tInsuredName!=null&&!tInsuredName.equals("")){
			sqlStrBuffer.append(" and a.InsuredName like '%"+tInsuredName+"%'");
		}
		if(tTempType.equals("1")){
			sqlStrBuffer.append(" and (b.StandbyString1 <> '03' or b.StandbyString1 is null)");
		}else if(tTempType.equals("2")){
			sqlStrBuffer.append(" and b.StandbyString1='03' ");
		}
		if(tAccumulateDefNO!=null&&!tAccumulateDefNO.equals("")){
			sqlStrBuffer.append(" and exists (select * from RIAccumulateDef where AccumulateDefNO = a.AccumulateDefNO and AccumulateDefNO ='"+tAccumulateDefNO+"') ");
		}
		sqlStrBuffer.append(" and exists (select * from ririskdivide where ripreceptno=b.ripreceptno and areaid=b.areaid and incomecompanyno='"+tRIcomCode+"')");
		sqlStrBuffer.append(" and rownum<=500 ) x group by x.A1,x.A2,x.A9,x.A10,x.A17 order by getdate");
		System.out.println("sqlStrBuffer: "+sqlStrBuffer.toString());
		
	}else if(tEventType.equals("03")){ //保全
		sqlStrBuffer.append("  select x.A1,x.A2,max(x.A3),max(x.A4),max(x.A5),max(x.A6),max(x.A7),max(x.A8),x.A9,max(x.A11),max(x.A12), max(x.A13),max(x.A14),sum(x.A15),sum(x.A16),sum(x.A17),max(x.A19),max(x.A20), max(x.A21),max(x.A22),max(x.A23) getdate ");
		sqlStrBuffer.append(" from (");
		sqlStrBuffer.append(" select (case when a.grpcontno='00000000000000000000' then a.contno else a.grpcontno end) A1, a.InsuredNo A2,a.InsuredName A3,(case when a.InsuredSex=0 then '"+"男"+"' else '"+"女"+"' end) A4,a.insuredage A5,a.standbydate1 A6, a.cvalidate A7, a.enddate A8,a.riskcode A9,a.dutycode A10,a.amnt A11,a.riskamnt A12,b.amountchang A13,a.accamnt A14,b.CessionAmount A15, b.PremChang A16, b.Commchang A17,a.eventno A18,a.standbydouble2 A19,a.standbydouble3 A20,a.peakline A21,a.payyears A22,a.getdate A23");
		sqlStrBuffer.append(" from RIPolRecordBake a,RIRecordTrace b where b.EventNo=a.EventNo  and a.EventType = '03' and a.GetDate between to_date('"+tStartDate+"') and to_date('"+tEndDate+"')") ;
		if(tContType.equals("1")){
			if(tContNo!=null&&!tContNo.equals("")){
				sqlStrBuffer.append(" and a.contno='"+tContNo+"'");
			}
		}else{
			if(tGrpContNo!=null&&!tGrpContNo.equals("")){
				sqlStrBuffer.append(" and a.grpcontno='"+tGrpContNo+"'");
			}
		}
		if(tInsuredNo!=null&&!tInsuredNo.equals("")){
			sqlStrBuffer.append(" and a.grpcontno='"+tInsuredNo+"'");
		}
		if(tInsuredName!=null&&!tInsuredName.equals("")){
			sqlStrBuffer.append(" and a.InsuredName like '%"+tInsuredName+"%'");
		}
		if(tTempType.equals("1")){
			sqlStrBuffer.append(" and (b.StandbyString1 <> '03' or b.StandbyString1 is null)");
		}else if(tTempType.equals("2")){
			sqlStrBuffer.append(" and b.StandbyString1='03' ");
		}
		sqlStrBuffer.append(" and exists (select * from ririskdivide where ripreceptno=b.ripreceptno and areaid=b.areaid and incomecompanyno='"+tRIcomCode+"')");
		if(tAccumulateDefNO!=null&&!tAccumulateDefNO.equals("")){
			sqlStrBuffer.append(" and exists (select * from RIAccumulateDef where AccumulateDefNO = a.AccumulateDefNO and AccumulateDefNO ='"+tAccumulateDefNO+"') ");
		}
		sqlStrBuffer.append(" and rownum<=500 ) x group by x.A1,x.A2,x.A9,x.A10,x.A18 order by getdate");
		System.out.println("sqlStrBuffer: "+sqlStrBuffer.toString());
	}else if(tEventType.equals("04")){ //理赔
		sqlStrBuffer.append(" select x.A1,x.A2,max(x.A3),max(x.A4),max(x.A5),max(x.A6),max(x.A7),max(x.A8),x.A9,max(x.A11),max(x.A12), max(x.A13),max(x.A15),max(x.A16), max(x.A17),max(x.A18),max(x.A19) getdate");
		sqlStrBuffer.append(" from (") ;
		sqlStrBuffer.append(" select  decode(a.grpcontno,'00000000000000000000',a.contno,a.grpcontno ) A1, a.InsuredNo A2,a.InsuredName A3,decode( a.InsuredSex,0 ,'"+"男"+"', '"+"女"+"') A4,a.insuredage A5, a.standbydate1 A6,a.cvalidate A7, a.enddate A8,a.riskcode A9,a.dutycode A10,a.amnt A11,a.riskamnt A12,b.returnpay A13,a.eventno A14,a.standbydouble2 A15, a.standbydouble3 A16,a.peakline A17,a.payyears A18,a.getdate A19 ");
		sqlStrBuffer.append(" from RIPolRecordBake a,RIRecordTrace b where b.EventNo=a.EventNo  and a.EventType = '04' and a.GetDate between to_date('"+tStartDate+"') and to_date('"+tEndDate+"')");
		if(tContType.equals("1")){
			if(tContNo!=null&&!tContNo.equals("")){
				sqlStrBuffer.append(" and a.contno='"+tContNo+"'");
			}
		}else{
			if(tGrpContNo!=null&&!tGrpContNo.equals("")){
				sqlStrBuffer.append(" and a.grpcontno='"+tGrpContNo+"'");
			}
		}
		if(tInsuredNo!=null&&!tInsuredNo.equals("")){
			sqlStrBuffer.append(" and a.grpcontno='"+tInsuredNo+"'");
		}
		if(tInsuredName!=null&&!tInsuredName.equals("")){
			sqlStrBuffer.append(" and a.InsuredName like '%"+tInsuredName+"%'");
		}
		if(tTempType.equals("1")){
			sqlStrBuffer.append(" and (b.StandbyString1 <> '03' or b.StandbyString1 is null)");
		}else if(tTempType.equals("2")){
			sqlStrBuffer.append(" and b.StandbyString1='03' ");
		}
		sqlStrBuffer.append(" and exists (select 'X' from ririskdivide where ripreceptno=b.ripreceptno and areaid=b.areaid and incomecompanyno='"+tRIcomCode+"')");
		if(tAccumulateDefNO!=null&&!tAccumulateDefNO.equals("")){
			sqlStrBuffer.append(" and exists (select 'X' from RIAccumulateDef where AccumulateDefNO = a.AccumulateDefNO and AccumulateDefNO ='"+tAccumulateDefNO+"') ");
		}
		sqlStrBuffer.append(" and rownum<=500 order by a.getdate) x group by x.A1,x.A2,x.A9,x.A10,x.A14 order by getdate ");
		System.out.println("sqlStrBuffer: "+sqlStrBuffer.toString());	
	}
		
	if(tOperateType.equals("QUERY")){
		strResult = tExeSQL.getEncodedResult(sqlStrBuffer.toString());
		System.out.println(" strResult: "+strResult);
	}else{
		if(tEventType.equals("01")){ //新单
			tLB.colName = new String[] {""+"保单号"+"",""+"被保人客户号"+"",""+"被保人姓名"+"",""+"被保人性别"+"",""+"投保年龄"+"",""+"生日"+"",""+"保单生效日期"+"",""+"保单终止日期"+"",""+"险种编码"+"",""+"保额"+"",""+"风险保额"+"",""+"累计风险保额"+"",""+"分保保额"+"",""+"分保保费"+"",""+"分保佣金"+"",""+"年缴化保费"+"",""+"准备金"+"",""+"现金价值"+"",""+"缴费年期"+"",""+"业务日期"+"" };
		}
		if(tEventType.equals("03")){ //保全
			tLB.colName = new String[] {""+"保单号"+"",""+"被保人客户号"+"",""+"被保人姓名"+"",""+"被保人性别"+"",""+"投保年龄"+"",""+"生日"+"",""+"保单生效日期"+"",""+"保单终止日期"+"",""+"险种编码"+"",""+"保额"+"",""+"风险保额"+"",""+"分保保额变化量"+"",""+"累计风险保额"+"",""+"分保保额"+"",""+"分保保费"+"",""+"分保佣金"+" ",""+"年缴化保费"+"",""+"准备金"+"",""+"现金价值"+"",""+"缴费年期"+"",""+"保全确认日期"+"" };
		}
		if(tEventType.equals("04")){ //理赔
			tLB.colName = new String[] {""+"保单号"+"",""+"被保人客户号"+"",""+"被保人姓名"+"",""+"被保人性别"+"",""+"投保年龄"+"",""+"生日"+"",""+"保单生效日期"+"",""+"保单终止日期"+"",""+"险种编码"+"",""+"保额"+"",""+"风险保额"+"",""+"理赔摊回"+"",""+"年缴化保费"+"",""+"准备金"+"",""+"现金价值"+"",""+"缴费年期"+"",""+"赔付日期"+""};
		}
			String sql = sqlStrBuffer.toString();
	tLB.sql = sql;
	tLB.row1 = 0;
	tLB.col1 = 0;
	tLB.InitData();
	listLB.add(tLB);
  
	try {
		response.reset();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition",
		"attachment; filename=LRBsnzDataExport_List.xls");
  
		String busiName="ExportExcel";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		OutputStream outOS = response.getOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(outOS);
		tVData.add(format);
		tVData.add(bos);
		
    	if(tBusinessDelegate.submitData(tVData,"write",busiName))
		{  
			bos = (BufferedOutputStream)tBusinessDelegate.getResult().getObjectByObjectName("BufferedOutputStream", 0);
			out.clear();
	 		out  =  pageContext.pushBody(); 
			bos.flush();
			bos.close();
		}
		//ExportExcel excel = new ExportExcel();
		//excel.write(format, bos);
	} catch (Exception e) {
		System.out.println("导出Excel失败！");
	}
	}
	
%>
<html>

<script type="text/javascript">	
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=strResult%>");
</script>
</html>
