<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
	String FlagStr = "";
	String Content = "";

	//在此设置导出Excel的列名，应与sql语句取出的域相对应
	GlobalInput tGlobalInput = new GlobalInput(); 
	tGlobalInput = (GlobalInput)session.getAttribute("GI"); 
	
	String tInsuredNo = request.getParameter("InsuredNo");
	String tInsuredName = request.getParameter("InsuredName");
	String tAccumulateDefNO = request.getParameter("AccumulateDefNO");
	String tEventType = request.getParameter("EventType");
	
	ExportExcel.Format format = new ExportExcel.Format();
	  VData tVData = new VData();
	
	ArrayList listCell = new ArrayList();
	ArrayList listLB = new ArrayList();
	ArrayList listColWidth = new ArrayList();
	format.mListCell=listCell;
	format.mListBL=listLB;
	format.mListColWidth=listColWidth;
	
	ExportExcel.Cell tCell=null;
	ExportExcel.ListBlock tLB=null;
	
	String tSQL = "" ;
	listColWidth.add(new String[]{"0","5000"});  
	//String sql = request.getParameter("ExportExcelSQL");
	System.out.println("Excel语句=");

	tLB = new ExportExcel.ListBlock("001");
	tLB.colName = new String[]{""+"保单号"+"",""+"事件类型"+"",""+"被保人编码"+"",""+"被保人姓名"+"",""+"再保合同编码"+"",""+"再保方案编码"+"",""+"累计方案编码"+"",""+"风险保额"+"",""+"累计风险保额"+"",""+"分保公司编码"+"",""+"险种编码"+"",""+"分保区域"+"",""+"分保比例"+"",""+"分保保额"+"",""+"分保保费"+"",""+"分保佣金"+"",""+"理赔摊回"+"",""+"业务日期"+"",""+"分保费率"+"",""+"分保佣金率"+""};
	tSQL = " select a.contno,decode(a.EventType,'01','"+"新单"+"','02','"+"续期"+"','03','"+"保全"+"','04','"+"理赔"+"',''),a.insuredno,a.insuredname,(case when a.RIContNo is null then '' else a.RIContNo end),a.RIPreceptNo,a.accumulatedefno,riskamnt,(case when a.accamnt is null then 0 else a.accamnt end), "
		+" b.incomecompanyno,b.AssociateCode,b.AreaID,b.CessionRate,b.CessionAmount,(case when b.PremChang is null then 0 else b.PremChang end ),(case when b.CommChang is null then 0 else b.CommChang end ),(case when b.ReturnPay is null then 0 else b.ReturnPay end),b.RIDate ,c.paramdouble1,c.paramdouble2 "
		+" from ripolrecordbake a ,RIRecordTrace b,ricalparam c  where a.eventno = b.eventno and b.serialno = c.serialno ";
	if(tInsuredNo!=null && !"".equals(tInsuredNo))
	{
		tSQL = tSQL + " and a.insuredno = '"+tInsuredNo+"' " ;
	}
	if(tInsuredName!=null && !"".equals(tInsuredName))
	{
		tSQL = tSQL + " and a.insuredname = '"+tInsuredName+"' " ;
	}
	if(tAccumulateDefNO!=null && !"".equals(tAccumulateDefNO))
	{
		tSQL = tSQL + " and a.accumulatedefno = '"+tAccumulateDefNO+"' " ;
	}
	if(tEventType != null && !"".equals(tEventType))
	{
		tSQL = tSQL + " and a.EventType = '"+tEventType+"' " ;
	}
	tLB.sql = tSQL ;
	tLB.row1 = 0;
	tLB.col1 = 0;
	tLB.InitData();
	listLB.add(tLB);

	try
	{
		response.reset();
		response.setContentType("application/octet-stream");
		
		//设置导出的xls文件名默认值
		//String HeaderParam = "\""+"attachment;filename="+"000001"+".xls"+"\"";
		String HeaderParam = "\""+"attachment;filename="+"RIDataDetail.xls"+"\"";
		response.setHeader("Content-Disposition",HeaderParam);
		
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
	}
	catch(Exception e)
	{
	  Content = ""+"导出Excel失败!"+"";
    FlagStr = "Fail"; 
	}
	if (!FlagStr.equals("Fail"))
	{
		Content = " "+"导出Excel成功!"+" ";
		FlagStr = "Succ";
	}
%>