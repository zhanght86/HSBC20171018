<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�Submit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��Minim
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.cardgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
<%@page import="java.util.*"%>

<%

  try	{
		ProposalUI tProposalUI  = new ProposalUI();
		
		GlobalInput tG = new GlobalInput();	
		tG = ( GlobalInput )session.getValue( "GI" );
	
		String riskType = request.getParameter("autoMoveFlag");
		String autoMove = request.getParameter("autoMoveValue");
    loggerDebug("ProposalAutoMoveCustomSubmit","�û�ѡ��Ĳ���ΪautoMove:" + autoMove);
	 		 
		Hashtable tHashtable = new Hashtable();
		tHashtable.put("type", riskType);
		tHashtable.put("autoMove", autoMove);
	
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add(tHashtable);
		tVData.add(tG);
		
		AutoMoveCustomUI tAutoMoveCustomUI = new AutoMoveCustomUI();
		tAutoMoveCustomUI.submitData(tVData, "INSERT||MAIN");
  }
  catch(Exception e) {
    e.printStackTrace();
  }

		
%>
