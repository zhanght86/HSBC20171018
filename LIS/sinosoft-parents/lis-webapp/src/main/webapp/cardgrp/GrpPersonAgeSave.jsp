<%--
    ���漯�屣����Ϣ 2004-11-16 wzw
--%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.cardgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
  String FlagStr = "Fail";
  String Content = "";
    String tImpartNum[] = request.getParameterValues("PersonAgeGridNo");
	String tStartAge[] = request.getParameterValues("PersonAgeGrid1");            //��֪���
	String tEndAge[] = request.getParameterValues("PersonAgeGrid2");           //��֪����
      
	String tOnWorkMCount[] = request.getParameterValues("PersonAgeGrid3");
	String tOnWorkFCount[] = request.getParameterValues("PersonAgeGrid4");
	String tOffWorkMCount[] = request.getParameterValues("PersonAgeGrid5");
	String tOffWorkFCount[] = request.getParameterValues("PersonAgeGrid6");
	String tMateMCount[] = request.getParameterValues("PersonAgeGrid7");
	String tMateFCount[] = request.getParameterValues("PersonAgeGrid8");
	String tYoungMCount[] = request.getParameterValues("PersonAgeGrid9");
	String tYoungFCount[] = request.getParameterValues("PersonAgeGrid10");
	String tOtherMCount[] = request.getParameterValues("PersonAgeGrid11");
	String tOtherFCount[] = request.getParameterValues("PersonAgeGrid12");

    

	String transact = "";
	
	GrpPersonAgeUI tGrpPersonAgeUI   = new GrpPersonAgeUI();
	VData tVData = new VData();
	LCPersonAgeDisItemSet tLCPersonAgeDisItemSet = new LCPersonAgeDisItemSet();	//�ͻ���֪
	LCPersonClassDisInfoSchema tLCPersonClassDisInfoSchema= new LCPersonClassDisInfoSchema();
	LCPersonAgeDisInfoSchema tLCPersonAgeDisInfoSchema= new LCPersonAgeDisInfoSchema();
	transact = request.getParameter("fmtransact");
	
	int ImpartCount = 0;
	if (tImpartNum != null) ImpartCount = tImpartNum.length;
	loggerDebug("GrpPersonAgeSave","Start ImpartCount : " + ImpartCount);


 	for (int i = 0; i < ImpartCount; i++)	
	{
		LCPersonAgeDisItemSchema tLCPersonAgeDisItemSchema = new LCPersonAgeDisItemSchema();
		tLCPersonAgeDisItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
		tLCPersonAgeDisItemSchema.setPrtNo(request.getParameter("PrtNo"));
		//tLCPersonAgeDisItemSchema.setCustomerNo(tLDGrpSchema.getCustomerNo());
		//tLCPersonAgeDisItemSchema.setCustomerNoType("0");
		tLCPersonAgeDisItemSchema.setStartAge(tStartAge[i]);
		tLCPersonAgeDisItemSchema.setEndAge(tEndAge[i]);
		tLCPersonAgeDisItemSchema.setOnWorkMCount(tOnWorkMCount[i]) ;
		
		tLCPersonAgeDisItemSchema.setOnWorkFCount(tOnWorkFCount[i]) ;

		tLCPersonAgeDisItemSchema.setOffWorkMCount(tOffWorkMCount[i]) ;

		tLCPersonAgeDisItemSchema.setOffWorkFCount(tOffWorkFCount[i]) ;

		tLCPersonAgeDisItemSchema.setMateMCount(tMateMCount[i]) ;
		
		tLCPersonAgeDisItemSchema.setMateFCount(tMateFCount[i]) ;
		
		tLCPersonAgeDisItemSchema.setYoungMCount(tYoungMCount[i]) ;
		
		tLCPersonAgeDisItemSchema.setYoungFCount(tYoungFCount[i]) ;
		
		tLCPersonAgeDisItemSchema.setOtherMCount(tYoungMCount[i]) ;
		
		tLCPersonAgeDisItemSchema.setOtherFCount(tOtherFCount[i]) ; 
		
		
		tLCPersonAgeDisItemSet.add(tLCPersonAgeDisItemSchema);


	}
	tLCPersonClassDisInfoSchema.setGrpContNo(request.getParameter("GrpContNo"));
	tLCPersonClassDisInfoSchema.setPrtNo(request.getParameter("PrtNo"));
	tLCPersonClassDisInfoSchema.setClassCode("0");
	tLCPersonAgeDisInfoSchema.setGrpContNo(request.getParameter("GrpContNo"));
	tLCPersonAgeDisInfoSchema.setPrtNo(request.getParameter("PrtNo"));
	tLCPersonAgeDisInfoSchema.setClassCode("0");
	
	
	loggerDebug("GrpPersonAgeSave","Start ImpartCount 4: ");
		  try
	  {
	   //׼���������� VData
		tVData.add( tLCPersonAgeDisItemSet );
		tVData.add( tLCPersonClassDisInfoSchema );
		tVData.add( tLCPersonAgeDisInfoSchema );
		if( tGrpPersonAgeUI.submitData( tVData, transact ) == false )
		{
			Content = " ����ʧ�ܣ�ԭ����: " + tGrpPersonAgeUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		else
			FlagStr = "Succ";	
	
	  }
	  catch(Exception ex)
	  {
	  //ex.printStackTrace();
	  //loggerDebug("GrpPersonAgeSave","ex.toString() : " + ex.toString());
	    Content = "����ʧ�ܣ�ԭ����:�������" ;
	    FlagStr = "Fail";
	  }
%>
<html>
<script language="javascript">

	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>

</html>
