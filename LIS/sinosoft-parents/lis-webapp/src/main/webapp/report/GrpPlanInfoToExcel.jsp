<%
	/************************
	/*�б��嵥���ݵ���Excel����*/
	/*ҳ��:GrpPolInfoToExcel.jsp*/
	/*������  ��Ԭ�෽*/
	/*����ʱ��:20090118*/
	/*******************************/
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	//GlobalInput tGlobalInput = new GlobalInput();
	//tGlobalInput = (GlobalInput) session.getAttribute("GI");

	ExportExcel.Format format = new ExportExcel.Format();
	ArrayList listCell = new ArrayList();
	ArrayList listLB = new ArrayList();
	ArrayList listColWidth = new ArrayList();
	format.mListCell = listCell;
	format.mListBL = listLB;
	format.mListColWidth = listColWidth;

	//ExportExcel.Cell tCell = null;
	ExportExcel.ListBlock tLB = null;	
	
	listColWidth.add(new String[]{"0","5000"}); //�������
    listColWidth.add(new String[]{"1","3800"}); //�����������
    listColWidth.add(new String[]{"2","5000"}); //�ŵ���ͬ��
    listColWidth.add(new String[]{"3","5000"}); //Ͷ������
    listColWidth.add(new String[]{"4","3000"}); //����������
    listColWidth.add(new String[]{"5","2000"}); //�Ա�
    listColWidth.add(new String[]{"6","3000"}); //��������
    listColWidth.add(new String[]{"7","5000"}); //�ײͼƻ�����
    listColWidth.add(new String[]{"8","3000"}); //����
    listColWidth.add(new String[]{"9","3000"}); //����
    listColWidth.add(new String[]{"10","3000"}); //��Ч����
    listColWidth.add(new String[]{"11","3000"}); //ǩ������ 
	
	String sql = request.getParameter("sql");
	
	listColWidth.add(new String[] { "0", "5000" });		

    /***����ͨ��ǰ̨��������OTHERNOTYPE�����ж���ʲô����***/
	tLB = new ExportExcel.ListBlock("001");
	//�ڴ����õ���Excel��������Ӧ��sql���ȡ���������Ӧ
    tLB.colName = new String[] { "�������", "�����������", "�����ͬ��", "Ͷ������","����������","�Ա�","��������","�ײͼƻ�����","����","����",
    		"��Ч����","ǩ������"}; 

	tLB.sql = sql;
		
	tLB.row1 = 0;
	tLB.col1 = 0;
	tLB.InitData();
	listLB.add(tLB);
	try {
		response.reset();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition",
		"attachment; filename=GrpPlanInfoToExcel.xls");

		OutputStream outOS = response.getOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(outOS);

		ExportExcel excel = new ExportExcel();
		excel.write(format, bos);
		bos.flush();
		bos.close();
	} catch (Exception e) {
		loggerDebug("GrpPlanInfoToExcel","����Excelʧ�ܣ�");
	}
%>
