<%@include file="/i18n/language.jsp"%>
<%
	 /************************
	 /*�ٱ�ҵ�����ݵ�������*/
	/*ҳ��:LRBsnzDataExportSave.jsp*/
	/*create:zhangbin*/
	/*����ʱ��:2007 version 1.0*/
	/*******************************/
%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%
	//�ڴ����õ���Excel��������Ӧ��sql���ȡ���������Ӧ
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
	String sql = "";
	sql="select case when a.conttype='1' then a.contno else a.grpcontno end ,"
		+ " b.dutycode,a.insuredno,a.cvalidate,(select max(IDNo) from lcinsured where insuredno=a.insuredno),InsuredAppAge,"
		+ " insuredsex,a.occupationtype,0,a.InsuredBirthday,a.riskcode,b.amnt,b.prem ,"
		+ " (case when conttype='1' then (select last_year_reser01 from ReportActu_temp  where i_info_cntr_no=a.contno and pol_code=a.riskcode) "
		+ " Else (select last_year_reser01 from ReportActu_temp  where ipsn_no=a.contno and pol_code=a.riskcode) end),"
		+ " (case when appflag='1' then '"+"��Ч"+"' else '"+"��Ч"+"' end) from lcpol a,lcduty b "
		+ " where a.polno=b.polno and appflag in ('1','4') and a.signdate between '"+startDate+"' and '"+endDate+"' "
		+ " order by signdate "
		;
			
  /***����ͨ��ǰ̨��������OTHERNOTYPE�����ж���ʲô����***/
	tLB = new ExportExcel.ListBlock("001");
  tLB.colName = new String[] { ""+"������"+"",""+"��Լ���Ʊ��"+"",""+"�ͻ�����"+"",""+"��Ч����"+"",""+"���֤��"+"",""+"Ͷ������"+"",""+"�Ա�"+"",
  	""+"ְҵ���"+"",""+"�α�׼��ӷ���"+"",""+"��������"+"",""+"����"+"",""+"����"+"",""+"����"+"",""+"׼����"+"",""+"����״̬"+"" };
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
		System.out.println("����Excelʧ�ܣ�");
	}
%>
