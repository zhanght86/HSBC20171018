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
	
	listColWidth.add(new String[]{"0","3800"}); //�����������
    listColWidth.add(new String[]{"1","5000"}); //�����������
    listColWidth.add(new String[]{"2","5000"}); //�ŵ���ͬ��
    listColWidth.add(new String[]{"3","3000"}); //���ִ���
    listColWidth.add(new String[]{"4","12000"}); //��������
    listColWidth.add(new String[]{"5","3000"}); //����������
    listColWidth.add(new String[]{"6","3200"}); //Ͷ����λ����
    listColWidth.add(new String[]{"7","5000"}); //Ͷ����λ����
    listColWidth.add(new String[]{"8","5000"}); //��ҵ�������
    listColWidth.add(new String[]{"9","2800"}); //�ۼƱ���
    listColWidth.add(new String[]{"10","2500"}); //�ۼ��⸶���
    listColWidth.add(new String[]{"11","3000"}); //��Ч��
    listColWidth.add(new String[]{"12","5000"}); //Ͷ��λ���������
	
	
	String sql = request.getParameter("sql");
	
	listColWidth.add(new String[] { "0", "5000" });		

    /***����ͨ��ǰ̨��������OTHERNOTYPE�����ж���ʲô����***/
	tLB = new ExportExcel.ListBlock("001");
	//�ڴ����õ���Excel��������Ӧ��sql���ȡ���������Ӧ
    tLB.colName = new String[] { "�����������", "�����������", "�ŵ���ͬ��", "���ִ���","��������","����������","Ͷ����λ����","Ͷ����λ����","��ҵ�������","�ۼƱ���",
    		"�ۼ��⸶���","��Ч����","Ͷ����λ���������"}; 

	tLB.sql = sql;
		
	tLB.row1 = 0;
	tLB.col1 = 0;
	tLB.InitData();
	listLB.add(tLB);
	try {
		response.reset();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition",
		"attachment; filename=GrpClaimInfoToExcel.xls");

		OutputStream outOS = response.getOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(outOS);

		ExportExcel excel = new ExportExcel();
		excel.write(format, bos);
		bos.flush();
		bos.close();
	} catch (Exception e) {
		loggerDebug("GrpClaimInfoToExcel","����Excelʧ�ܣ�");
	}
%>

