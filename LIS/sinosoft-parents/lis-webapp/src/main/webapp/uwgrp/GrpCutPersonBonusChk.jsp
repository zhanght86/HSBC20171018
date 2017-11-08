<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：GrpCutPersonBonusChk.jsp
//程序功能：团单下个人分红处理
//创建日期：2005-08-16
//创建人  ：wentao
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.tbgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%
		//输出参数
		CErrors tError = null;
		String FlagStr = "Fail";
		String Content = "";

		try
		{
				GlobalInput tG = (GlobalInput)session.getValue("GI");
				if(tG == null) 
				{
					out.println("session has expired");
					return;
				}

				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("FiscalYear",request.getParameter("FiscalYear"));
				tTransferData.setNameAndValue("GrpContNo",request.getParameter("GrpContNo"));
				tTransferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));
				tTransferData.setNameAndValue("BDate","");
				tTransferData.setNameAndValue("EDate","");
				tTransferData.setNameAndValue("ContNo",request.getParameter("ContNo"));

				// 准备传输数据 VData
				VData tVData = new VData();
				tVData.add( tG );
				tVData.add( tTransferData );
				
				// 数据传输
				GrpAssignBonus tGrpAssignBonus = new GrpAssignBonus();
				if (!tGrpAssignBonus.submitData(tVData,"ASSIGN||PART"))
				{			
						Content = "红利分配完成，部分数据有误，请查看错误信息表！";
						FlagStr = "Fail";
				}
				else
				{
						Content = "红利分配完成! ";
						FlagStr = "Succ";
				}
		}
		catch(Exception e)
		{
				e.printStackTrace();
				Content = Content.trim() +" 提示:异常退出.";
		}
%>                      
<html>
<script language="javascript">
		parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
//		parent.fraInterface.queryGrpPol();
</script>
</html>
