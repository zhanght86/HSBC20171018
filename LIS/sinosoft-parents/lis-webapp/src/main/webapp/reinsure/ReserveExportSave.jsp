<%@include file="/i18n/language.jsp"%>
<%
	 /************************
	 /*再保业务数据导出功能*/
	/*页面:ReserveExportSave.jsp*/
	/*create:zhangbin*/
	/*创建时间:2008 version 1.0*/
	/*******************************/
%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%
	//在此设置导出Excel的列名，应与sql语句取出的域相对应
	GlobalInput tGlobalInput = new GlobalInput();
	tGlobalInput = (GlobalInput) session.getAttribute("GI");

	ExportExcel.Format format = new ExportExcel.Format();
	ArrayList listCell = new ArrayList();
	ArrayList listLB = new ArrayList();
	ArrayList listColWidth = new ArrayList();
	format.mListCell = listCell;
	format.mListBL = listLB;
	format.mListColWidth = listColWidth;

	ExportExcel.Cell tCell = null;
	ExportExcel.ListBlock tLB = null;

	String tManageCom = tGlobalInput.ComCode;
	String startDate = request.getParameter("StartDate");
	String endDate = request.getParameter("EndDate");
	listColWidth.add(new String[] { "0", "5000" });
	
	tLB = new ExportExcel.ListBlock("001");
	/***************************/
	String tEventType = request.getParameter("EventType");;
  
  String tStartDate 	= request.getParameter("StartDate");
  String tEndDate 		= request.getParameter("EndDate");
  String tGrpContNo 	= request.getParameter("GrpContNo");
  String tInsuredNo 	= request.getParameter("InsuredNo");
  String tInsuredName = request.getParameter("InsuredName");
  String tTempType 		= request.getParameter("TempType");
  String tRIcomCode 	= request.getParameter("RIcomCode");
  String tRIContNo 		= request.getParameter("RIContNo");
  
  System.out.println("tStartDate: "	+ tStartDate );
  System.out.println("tEndDate: "		+ tEndDate);
  System.out.println("tEventType: "	+ tEventType);
  System.out.println("tGrpContNo: "	+ tGrpContNo);
  System.out.println("tInsuredNo: "	+ tInsuredNo);
  System.out.println("tInsuredName:"+ tInsuredName);
  System.out.println("tTempType: "	+ tTempType);
  System.out.println("tRIcomCode: "	+ tRIcomCode);
  System.out.println("tRIContNo: "	+ tRIContNo);
  
  TransferData exportDate = new TransferData();
  
  GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getAttribute("GI");
	
	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
  tVData.addElement(tG);
  
  tVData.addElement(exportDate);
	ExeSQL tExeSQL = new ExeSQL();
	StringBuffer sqlSB = new StringBuffer();
	if(tEventType.equals("01")){ //新单
		//sqlStr=" select x.A1,x.A2,max(x.A3),max(x.A4),max(x.A5),max(x.A6),max(x.A7),x.A8,x.A9,max(x.A10),max(x.A11),max(x.A12),sum(x.A13),sum(x.A14),sum(x.A15) ,A18 from ( select (case when b.grpcontno='00000000000000000000' then substr(b.contno,1,instr(b.contno,',')-1) else b.grpcontno end) A1, b.InsuredNo A2,b.InsuredName A3,b.CValiDate A4, (case when b.InsuredSex=0 then '男' else '女' end) A5,b.InsuredAge A6, OccupationType A7,b.riskcode A8,b.dutycode A9,b.amnt A10,a.CurentAmnt A11,(select sum(AccumulateChang) from AccumulateRecordTrace where AccumulateDefNO=a.AccumulateDefNO and OtherNo=a.OtherNo and eventno<=a.eventno) A12,round(a.CessionRate,2) A13, a.CessionAmount A14, a.PremChang A15,(select IncomeCompanyNo from RIRiskDivide where RIPreceptNo=a.RIPreceptNo and AreaID=a.AreaID) A16,(select RIContNo from riprecept where ripreceptno=a.ripreceptno ) A17,b.eventno A18 from ReinsureRecordTrace a,RIPolRecordBake b where a.EventNo=b.EventNo  and b.EventType in('01','02') and GetDate between to_date('"+tStartDate+"') and to_date('"+EndDate+"') "
		sqlSB.append(" select x.A1,x.A19,x.A2,max(x.A3),max(x.A4),max(x.A22),max(x.A23),max(x.A5),max(x.A6),max(x.A7),x.A8,x.A9,max(x.A10),max(x.A11),max(x.A12),sum(x.A13),sum(x.A14),sum(x.A15) ,max(A18),max(x.A20),max(x.A21),max(x.A24),max(x.A25),max(x.A26),max(x.A27) from ( select (case when b.grpcontno='00000000000000000000' then substr(b.contno,1,instr(b.contno,',')-1) else b.grpcontno end) A1, b.InsuredNo A2,b.InsuredName A3,b.CValiDate A4, (case when b.InsuredSex=0 then '"+"男"+"' else '"+"女"+"' end) A5,b.InsuredAge A6, OccupationType A7,b.riskcode A8,b.dutycode A9,b.amnt A10,a.CurentAmnt A11,(select sum(AccumulateChang) from AccumulateRecordTrace where AccumulateDefNO=a.AccumulateDefNO and OtherNo=a.OtherNo and eventno<=a.eventno) A12,round(a.CessionRate,2) A13, a.CessionAmount A14, a.PremChang A15, (select IncomeCompanyNo from RIRiskDivide where RIPreceptNo=a.RIPreceptNo and AreaID=a.AreaID) A16,(select distinct c.RIContNo from riprecept c where c.ripreceptno=a.ripreceptno) A17,b.eventno A18,SUBSTR(b.contno,1,decode(instr(b.contno,','),0,length(b.contno),instr(b.contno,',')-1)) A19,");
		sqlSB.append(" (select d.GetCValiDate from ripolrecordbake d where d.eventno =( select max(c.eventno) from ripolrecordbake c where c.eventtype='03' and c.insuredno=b.insuredno and c.riskcode=b.riskcode and c.dutycode=b.dutycode and c.contno=b.contno and c.eventno<b.eventno))A20,(select (select edorname from lmedoritem where edorcode=d.FeeOperationType and appobj='G') from ripolrecordbake d where d.eventno=(select max(c.eventno) from ripolrecordbake c where c.eventtype='03' and c.insuredno=b.insuredno and c.riskcode=b.riskcode and c.dutycode=b.dutycode and c.contno=b.contno and c.eventno<b.eventno )) A21, (select c.cvalidate from lcgrpcont c where trim(c.grpcontno)=trim(b.grpcontno)) A22, b.enddate A23, b.OccupationType A24, (select d.amnt from ripolrecordbake d where d.eventno=(select min(eventno) from ripolrecordbake c where b.insuredno=c.insuredno and trim(b.riskcode)=trim(c.riskcode) and trim(b.dutycode)=trim(c.dutycode) and trim(b.contno)=trim(c.contno) and trim(c.eventtype)='01')) A25 , b.prem A26, (select DivisionLineValue from DivisionLineDef where RIPreceptNo=(select distinct c.RIPreceptNo from reinsurerecordtrace c where c.eventno=a.eventno ) and DivisionLineType='01') A27 from ReinsureRecordTrace a,RIPolRecordBake b where a.EventNo=b.EventNo  and b.EventType in('01','02') and GetDate between to_date('"+tStartDate+"') and to_date('"+tEndDate+"')") ;
		if(tGrpContNo!=""&&!tGrpContNo.equals("")){
			sqlSB.append(" b.grpcontno='"+tGrpContNo+"'");
		}
		if(tInsuredNo!=""&&!tInsuredNo.equals("")){
			sqlSB.append(" b.grpcontno='"+tInsuredNo+"'");
		}
		if(tInsuredName!=""&&!tInsuredName.equals("")){
			sqlSB.append(" b.InsuredName like '%"+tInsuredName+"%'");
		}
		if(tTempType.equals("1")){
			sqlSB.append(" and (b.ReinsreFlag <> '03' or b.ReinsreFlag is null)");
		}else if(tTempType.equals("2")){
			sqlSB.append(" and b.ReinsreFlag='03' ");
		}
		if(tRIcomCode!=null&&tRIcomCode.equals("")){
			sqlSB.append(" and exists (select * from ririskdivide where ripreceptno=a.ripreceptno and areaid=a.areaid and incomecompanyno='"+tRIcomCode+"')");
		}
		if(tRIContNo!=null&&tRIContNo.equals("")){
			sqlSB.append(" and exists (select * from riprecept where ripreceptno=a.ripreceptno and ricontno='"+tRIContNo+"') ");
		}
		sqlSB.append(" ) x where rownum <=2000 group by A16,A17,A1,A2,A8,A9,x.A18,x.A19 order by x.A1,x.A2,x.A18 ");
		System.out.println("sqlSB: "+sqlSB.toString());	
		tLB.colName = new String[] { ""+"团体保单号"+"",""+"个人保单号"+"",""+"被保人客户号"+"",""+"被保人姓名"+"",""+"个人保单生效日"+"",""+"团体保单生效日"+"",""+"个人终止日"+"",""+"性别"+"",""+"投保年龄"+"",""+"职业类别"+"",""+"险种编码"+"",""+"责任代码"+"",""+"保额"+"",""+"风险保额"+"",""+"累计风险保额"+"",""+"分保比例"+"",""+"分保保额"+"",""+"分保保费"+"","eventno",""+"最近一次保全日期"+"",""+"最近一次保全类型"+"",""+"职业等级"+"",""+"原始保额"+"",""+"保费"+"",""+"自留额"+"" };
	}else if(tEventType.equals("03")){ //保全
		sqlSB.append(" select x.A1,x.A2,max(x.A3),max(x.A4),max(x.A5),max(x.A6),max(x.A7),x.A8,x.A9,max(x.A10),max(x.A11),max(x.A12),max(x.A13),sum(x.A14),sum(x.A15),sum(x.A16),x.A19,max(x.A20),max(x.A21),max(x.A22),max(x.A23),max(x.A24),sum(x.A25),sum(x.A26),sum(x.A27),sum(x.A28),sum(x.A29),sum(x.A30)");
		sqlSB.append(" from ( ");
		sqlSB.append(" select (case when b.grpcontno='00000000000000000000' then substr(b.contno,1,instr(b.contno,',')-1) else b.grpcontno end) A1, b.InsuredNo A2,b.InsuredName A3,b.CValiDate A4, (case when b.InsuredSex=0 then '"+"男"+"' else '"+"女"+"' end) A5,b.InsuredAge A6, b.OccupationType A7,b.riskcode A8,b.dutycode A9,(select edorname from lmedoritem where edorcode=b.FeeOperationType and appobj='G') A10,b.amnt A11,a.AmountChang A12,(select sum(AccumulateChang) from AccumulateRecordTrace where AccumulateDefNO=a.AccumulateDefNO and AccumulateCode=a.AccumulateCode and eventno<=a.eventno) A13,round(a.CessionRate,2) A14, a.CessionAmount A15, a.PremChang A16,(select IncomeCompanyNo from RIRiskDivide where RIPreceptNo=a.RIPreceptNo and AreaID=a.AreaID) A17,(select RIContNo from riprecept where ripreceptno=a.ripreceptno ) A18,b.eventno A19, ");
		sqlSB.append(" b.GetCValiDate A20,b.PreAmnt A21,b.Amnt A22,b.PrePrem A23,b.Prem A24,(select sum(c.AmountChang) from reinsurerecordtrace c where a.AccumulateDefNO=c.AccumulateDefNO and a.OtherNo=c.OtherNo and a.AccumulateCode=c.AccumulateCode and a.RIPreceptNo=c.RIPreceptNo and a.AreaID=c.AreaID and c.EventNo<a.EventNo ) A25,(select sum(c.AmountChang) from reinsurerecordtrace c where a.AccumulateDefNO=c.AccumulateDefNO and a.OtherNo=c.OtherNo and a.AccumulateCode=c.AccumulateCode and a.RIPreceptNo=c.RIPreceptNo and a.AreaID=c.AreaID and c.EventNo<=a.EventNo ) A26,(select sum(c.PremChang) from reinsurerecordtrace c where a.AccumulateDefNO=c.AccumulateDefNO and a.OtherNo=c.OtherNo and a.AccumulateCode=c.AccumulateCode and a.RIPreceptNo=c.RIPreceptNo and a.AreaID=c.AreaID and c.EventNo<a.EventNo ) A27,(select sum(c.PremChang) from reinsurerecordtrace c where a.AccumulateDefNO=c.AccumulateDefNO and a.OtherNo=c.OtherNo and a.AccumulateCode=c.AccumulateCode and a.RIPreceptNo=c.RIPreceptNo and a.AreaID=c.AreaID and c.EventNo<=a.EventNo ) A28,(select round(c.CessionRate,2) from reinsurerecordtrace c where a.AccumulateDefNO=c.AccumulateDefNO and a.OtherNo=c.OtherNo and a.AccumulateCode=c.AccumulateCode and a.RIPreceptNo=c.RIPreceptNo and a.AreaID=c.AreaID and c.EventNo=(select max(c.EventNo) from reinsurerecordtrace c where a.AccumulateDefNO=c.AccumulateDefNO and a.OtherNo=c.OtherNo and a.AccumulateCode=c.AccumulateCode and a.RIPreceptNo=c.RIPreceptNo and a.AreaID=c.AreaID and c.EventNo<a.EventNo )) A29,round(a.CessionRate,2) A30 ");
		sqlSB.append(" from ReinsureRecordTrace a,RIPolRecordBake b where a.EventNo=b.EventNo  and b.EventType='03' and GetDate between to_date('"+tStartDate+"') and to_date('"+tEndDate+"')") ;
		if(tGrpContNo!=""&&!tGrpContNo.equals("")){
			sqlSB.append(" b.grpcontno='"+tGrpContNo+"'");
		}
		if(tInsuredNo!=""&&!tInsuredNo.equals("")){
			sqlSB.append(" b.grpcontno='"+tInsuredNo+"'");
		}
		if(tInsuredName!=""&&!tInsuredName.equals("")){
			sqlSB.append(" b.InsuredName like '%"+tInsuredName+"%'");
		}
		if(tTempType.equals("1")){
			sqlSB.append(" and (b.ReinsreFlag <> '03' or b.ReinsreFlag is null)");
		}else if(tTempType.equals("2")){
			sqlSB.append(" and b.ReinsreFlag='03' ");
		}
		if(tRIcomCode!=null&&tRIcomCode.equals("")){
			sqlSB.append(" and exists (select * from ririskdivide where ripreceptno=a.ripreceptno and areaid=a.areaid and incomecompanyno='"+tRIcomCode+"')");
		}
		if(tRIContNo!=null&&tRIContNo.equals("")){
			sqlSB.append(" and exists (select * from riprecept where ripreceptno=a.ripreceptno and ricontno='"+tRIContNo+"') ");
		}
		sqlSB.append(" ) x where rownum <=2000 group by A16,A17,A1,A2,A8,A9,x.A18,x.A19 order by x.A1,x.A2,x.A18 ");
		System.out.println("sqlSB: "+sqlSB.toString());
		
		tLB.colName = new String[] {""+"保单号"+"",""+"被保人客户号"+"",""+"被保人姓名"+"",""+"个人保单生效日"+"",""+"性别"+"",""+"投保年龄"+"",""+"职业类别"+"",""+"险种编码"+"",""+"责任代码"+"",""+"保全业务类型"+"",""+"保额"+"",""+"风险保额变化量"+"",""+"累计风险保额"+"",""+"分保比例"+"",""+"分保保额"+"",""+"分保保费"+"","eventno",""+"变更保额日期"+"",""+"变更前保额"+"",""+"变更后保额"+"",""+"变更前保费"+"",""+"变更后保费"+"",""+"变更前再保保额"+"",""+"变更后再保保额"+"",""+"变更前再保保费"+"",""+"变更后再保保费"+"",""+"变更前分保比例"+"",""+"变更后分保比例"+"" };
		
	}else if(tEventType.equals("04")){ //理赔
	
		sqlSB.append(" select x.A1,x.A2,max(x.A3),max(x.A4),max(x.A5),max(x.A6),max(x.A7),x.A8,x.A9,max(x.A10),sum(x.A11),sum(x.A12) from (select (case when b.grpcontno='00000000000000000000' then substr(b.contno,1,instr(b.contno,',')-1) else b.grpcontno end) A1, b.InsuredNo A2,b.InsuredName A3,b.CValiDate A4, (case when b.InsuredSex=0 then '"+"男"+"' else '"+"女"+"' end) A5,b.InsuredAge A6, OccupationType A7,b.riskcode A8,b.dutycode A9,b.amnt A10,round(a.CessionRate,2) A11,a.ReturnPay A12, (select IncomeCompanyNo from RIRiskDivide where RIPreceptNo=a.RIPreceptNo and AreaID=a.AreaID) A16,(select RIContNo from riprecept where ripreceptno=a.ripreceptno ) A17,b.eventno A18 from ReinsureRecordTrace a,RIPolRecordBake b where a.EventNo=b.EventNo  and b.EventType = '04' and GetDate between to_date('"+tStartDate+"') and to_date('"+tEndDate+"')") ;
		if(tGrpContNo!=""&&!tGrpContNo.equals("")){
			sqlSB.append(" b.grpcontno='"+tGrpContNo+"'");
		}
		if(tInsuredNo!=""&&!tInsuredNo.equals("")){
			sqlSB.append(" b.grpcontno='"+tInsuredNo+"'");
		}
		if(tInsuredName!=""&&!tInsuredName.equals("")){
			sqlSB.append(" b.InsuredName like '%"+tInsuredName+"%'");
		}
		if(tTempType.equals("1")){
			sqlSB.append(" and (b.ReinsreFlag <> '03' or b.ReinsreFlag is null)");
		}else if(tTempType.equals("2")){
			sqlSB.append(" and b.ReinsreFlag='03' ");
		}
		if(tRIcomCode!=null&&tRIcomCode.equals("")){
			sqlSB.append(" and exists (select * from ririskdivide where ripreceptno=a.ripreceptno and areaid=a.areaid and incomecompanyno='"+tRIcomCode+"')");
		}
		if(tRIContNo!=null&&tRIContNo.equals("")){
			sqlSB.append(" and exists (select * from riprecept where ripreceptno=a.ripreceptno and ricontno='"+tRIContNo+"') ");
		}
		sqlSB.append(" ) x where rownum <=5000 group by A16,A17,A1,A2,A8,A9,A18  order by A1,A2,A18 ");
		System.out.println("sqlSB: "+sqlSB.toString());	
		tLB.colName = new String[] { ""+"保单号"+"",""+"被保人客户号"+"",""+"被保人姓名"+"",""+"个人保单生效日"+"",""+"性别"+"",""+"投保年龄"+"",""+"职业类别"+"",""+"险种编码"+"",""+"责任代码"+"",""+"保额"+"",""+"分保比例"+"",""+"理赔摊回"+""};
	}
	/***************************/
	String sql = sqlSB.toString();
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

		OutputStream outOS = response.getOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(outOS);

		ExportExcel excel = new ExportExcel();
		excel.write(format, bos);
		bos.flush();
		bos.close();
	} catch (Exception e) {
		System.out.println("导出Excel失败！");
	}
%>
