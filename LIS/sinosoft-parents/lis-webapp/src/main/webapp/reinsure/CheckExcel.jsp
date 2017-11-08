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
  String ribillno = request.getParameter("jkbillno");
  String ricontno = request.getParameter("jkcontno");
  String ricomno = request.getParameter("jkcomno");
  String risdate = request.getParameter("jksdate");
  String riedate = request.getParameter("jkedate");
  System.out.println("========"+ribillno);
  System.out.println("========"+ricontno);
  System.out.println("========"+ricomno);
  System.out.println("========"+risdate);
  System.out.println("========"+riedate);
	//在此设置导出Excel的列名，应与sql语句取出的域相对应
	GlobalInput tGlobalInput = new GlobalInput(); 
	tGlobalInput = (GlobalInput)session.getAttribute("GI"); 
	
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
	
	String tManageCom = tGlobalInput.ComCode;
	listColWidth.add(new String[]{"0","5000"});  
	//String sql = request.getParameter("ExportExcelSQL");
	System.out.println("Excel语句=");
	String tSQL = "";
	tLB = new ExportExcel.ListBlock("001");
	tLB.colName = new String[]{""+"保单号"+"",""+"事件类型"+"",""+"被保人编码"+"",""+"被保人姓名"+"",""+"再保合同编码"+"",""+"再保方案编码"+"",""+"累计方案编码"+"",""+"风险保额"+"",""+"累计风险保额"+"",""+"分保公司编码"+"",""+"险种编码"+"",""+"分保区域"+"",""+"分保比例"+"",""+"分保保额"+"",""+"分保保费"+"",""+"分保佣金"+"",""+"理赔摊回"+"",""+"业务日期"+""};
	tSQL = "  select a.contno,decode(a.EventType,'01','"+"新单"+"','02','"+"续期"+"','03','"+"保全"+"','04','"+"理赔"+"',''),a.insuredno,a.insuredname,(case when a.RIContNo is null then '' else a.RIContNo end),a.RIPreceptNo,a.accumulatedefno,riskamnt,(case when a.accamnt is null then 0 else a.accamnt end),"
		+" b.incomecompanyno,AssociateCode,AreaID,CessionRate,CessionAmount,(case when PremChang is null then 0 else PremChang end ),(case when CommChang is null then 0 else CommChang end ),(case when ReturnPay is null then 0 else ReturnPay end),RIDate "
		+" from ripolrecordbake a, RIRecordTrace b  where a.eventno=b.eventno  and a.batchno = b.batchno and a.eventtype in('01','03') "
		+" and a.RIContNo = '"+ricontno+"' and b.incomecompanyno = '"+ricomno+"' and RIDate >= '"+risdate+"' and RIDate <= '"+riedate+"' ";
	

	tLB.sql = tSQL;
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
		String HeaderParam = "\""+"attachment;filename="+"BillDetail-"+ricontno+".xls"+"\"";
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