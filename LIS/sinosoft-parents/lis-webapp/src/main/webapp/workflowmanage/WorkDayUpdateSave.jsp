<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
    /**
     * Created by IntelliJ IDEA.
     * User: jinsh
     * Date: 2009-1-7
     * Time: 15:32:15
     * To change this template use File | Settings | File Templates.
     */
%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.service.*" %> 
<%@page import="java.text.*"%>

<%

  String FlagStr="";      //操作结果
  String Content = "";    //控制台信息
  BusinessDelegate tBusinessDelegate;
  VData tVData = new VData();
  LDWorkCalendarSet tLDWorkCalendarSet = new LDWorkCalendarSet();
  String tWorkDayNum[] = request.getParameterValues("SaveWorkDayGridNo");
  String tWorkDayCalType[] = request.getParameterValues("SaveWorkDayGrid1");
  String tWorkDayCalSn[] = request.getParameterValues("SaveWorkDayGrid2");            //
  String tWorkDayYear[] = request.getParameterValues("SaveWorkDayGrid3");           //
  String tWorkDayCalDate[] = request.getParameterValues("SaveWorkDayGrid4");        //
  String tWorkDayAmBegin[] = request.getParameterValues("SaveWorkDayGrid6");
  String tWorkDayAmEnd[] = request.getParameterValues("SaveWorkDayGrid7");
  String tWorkDayPmBegin[] = request.getParameterValues("SaveWorkDayGrid8");            //
  String tWorkDayPmEnd[] = request.getParameterValues("SaveWorkDayGrid9");           //
  String tWorkDayWorkTime[] = request.getParameterValues("SaveWorkDayGrid10");        //
  String tWorkDayDateType[] = request.getParameterValues("SaveWorkDayGrid11");
  String tWorkDayOtherProp[] = request.getParameterValues("SaveWorkDayGrid12");

  String tChk[] = request.getParameterValues("WorkDayGridChk");
  for(int i=0;i<tWorkDayNum.length;i++){
    //if(tChk[i].equals("1")){
      LDWorkCalendarSchema tLDWorkCalendarSchema = new LDWorkCalendarSchema();
      tLDWorkCalendarSchema.setCalSN(tWorkDayCalSn[i]);
      tLDWorkCalendarSchema.setCalType(tWorkDayCalType[i]);
      tLDWorkCalendarSchema.setYear(tWorkDayYear[i]);
      tLDWorkCalendarSchema.setCalDate(tWorkDayCalDate[i]);
      tLDWorkCalendarSchema.setAmBegin(tWorkDayAmBegin[i]);
      tLDWorkCalendarSchema.setAmEnd(tWorkDayAmEnd[i]);
      tLDWorkCalendarSchema.setPmBegin(tWorkDayPmBegin[i]);
      tLDWorkCalendarSchema.setPmEnd(tWorkDayPmEnd[i]);
      tLDWorkCalendarSchema.setWorkTime(tWorkDayWorkTime[i]);
      tLDWorkCalendarSchema.setDateType(tWorkDayDateType[i]);
      tLDWorkCalendarSchema.setOtherProp(tWorkDayOtherProp[i]);
      tLDWorkCalendarSet.add(tLDWorkCalendarSchema);
    //}
  }


  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");

  tVData.add(tLDWorkCalendarSet);
  tVData.add(tG);

    tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  if(!tBusinessDelegate.submitData(tVData, "UPDATE", "WorkDayUpdateUI"))
  	{
		  Content = " 保存失败，原因是: " + tBusinessDelegate.getCErrors().getFirstError();
	   	FlagStr = "Fail";
	  }
	  else
	  {
	  	Content = " 保存成功! ";
		  FlagStr = "Succ";
    }
%>
<html>
<script language="javascript" >
//alert("xx"+"<%=Content%>"+"<%=FlagStr%>");
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
