<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ApplyRecallChk.jsp
//程序功能：
//创建日期：
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.reinsure.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	  //输出参数
	  CErrors tError = null;
	  String FlagStr = "Fail";
		String Content="";
		GlobalInput tG = new GlobalInput(); 
		tG=(GlobalInput)session.getAttribute("GI"); 
		
		CasualRIContAssociateSet mCasualRIContAssociateSet=new CasualRIContAssociateSet(); 
		String tDutyCode= request.getParameter("DutyCode"); 
		String tPolNo 	= request.getParameter("PolNo"); 
		
	  String[] StrNum=request.getParameterValues("PreceptGridNo");
	  String[] rIContNo=request.getParameterValues("PreceptGrid1");
	  String[] rIPreceptNo=request.getParameterValues("PreceptGrid2");
	  String tChk[] = request.getParameterValues("InpPreceptGridChk");
	  CasualRIContAssociateSchema tCasualRIContAssociateSchema ;
		for(int index=0;index<tChk.length;index++){
			if(tChk[index].equals("0")) continue;
			tCasualRIContAssociateSchema = new CasualRIContAssociateSchema();
			tCasualRIContAssociateSchema.setRIContNo("");
			tCasualRIContAssociateSchema.setRIPreceptNo("");
			tCasualRIContAssociateSchema.setDataFlag("02");
			tCasualRIContAssociateSchema.setContNo("");
			tCasualRIContAssociateSchema.setRiskCode("");
			tCasualRIContAssociateSchema.setDutyCode(tDutyCode);
			tCasualRIContAssociateSchema.setNaturalRIContNo(rIContNo[index]);
			tCasualRIContAssociateSchema.setNaturalRIPreceptNo(rIPreceptNo[index]);
			mCasualRIContAssociateSet.add(tCasualRIContAssociateSchema);
		}
	  
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("DutyCode",tDutyCode);
		tTransferData.setNameAndValue("PolNo",tPolNo);
		
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(mCasualRIContAssociateSet);
		tVData.add(tG);
		
		// 数据传输 
		LRTempConclusionUI tLRTempConclusionUI = new LRTempConclusionUI(); 
		if (tLRTempConclusionUI.submitData(tVData,"") == false){ 
			Content = " "+"保存失败，原因是:"+" " + tLRTempConclusionUI.mErrors.getError(0).errorMessage; 
			FlagStr = "Fail";
		}
    else{
		  Content = " "+"保存成功!"+" ";
		  FlagStr = "Succ";
		}

%>                      
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");

</script>
</html>
