<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：Submit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：Minim
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tbgrp.*"%>
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
    loggerDebug("ProposalAutoMoveCustomSubmit","用户选择的操作为autoMove:" + autoMove);
	 		 
		Hashtable tHashtable = new Hashtable();
		tHashtable.put("type", riskType);
		tHashtable.put("autoMove", autoMove);
	
		// 准备传输数据 VData
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
