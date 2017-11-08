<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LDUWUserSave.jsp
//程序功能：
//创建日期：2005-01-24 18:15:01
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.config.*"%>
<%
  //接收信息，并作校验处理。
  //输入参数
  LDUWUserSchema tLDUWUserSchema   = new LDUWUserSchema();
  LDUWUserSchema tLDUWUserSchema1   = new LDUWUserSchema();
  LDIndUWUserUI tLDIndUWUserUI   = new LDIndUWUserUI();
  //输出参数
  CErrors tError = null;
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String transact = "";
 // String tImpartNum[] = request.getParameterValues("LCUWReponsComGridNo");            //
 // String tManageCom[] = request.getParameterValues("LCUWReponsComGrid1");      // 负责机构
 // String tValidStartDate[] = request.getParameterValues("LCUWReponsComGrid2"); //有效开始日期 
 // String tValidEndDate[] = request.getParameterValues("LCUWReponsComGrid3");   //有效结束日期?
  
  GlobalInput tG = new GlobalInput(); 
  tG=(GlobalInput)session.getValue("GI");
	
  //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
  transact = request.getParameter("fmtransact");
   tLDUWUserSchema.setUserCode(request.getParameter("UserCode"));
    tLDUWUserSchema.setUWType(request.getParameter("UWType"));
    tLDUWUserSchema.setUWPopedom(request.getParameter("UWPopedom"));
    tLDUWUserSchema.setUpUWPopedom(request.getParameter("UpUwPopedom"));
    tLDUWUserSchema.setEdorPopedom(request.getParameter("edpopedom"));
    tLDUWUserSchema.setClaimPopedom(request.getParameter("ClaimPopedom"));
    tLDUWUserSchema.setAddPoint(request.getParameter("AddPoint"));
    tLDUWUserSchema.setRemark(request.getParameter("Remark"));
    tLDUWUserSchema.setOperator(request.getParameter("Operator"));
    tLDUWUserSchema.setManageCom(request.getParameter("ManageCom"));
    tLDUWUserSchema.setUWRate(request.getParameter("Rate"));
    tLDUWUserSchema.setSpecJob(request.getParameter("SpecJob"));
    tLDUWUserSchema.setLowestAmnt(request.getParameter("LowestAmnt"));
    tLDUWUserSchema.setOverAge(request.getParameter("OverAge"));
    tLDUWUserSchema.setUWProcessFlag(request.getParameter("UWProcessFlag"));
    tLDUWUserSchema.setSurpassGradeFlag(request.getParameter("SurpassGradeFlag"));
    tLDUWUserSchema.setUWOperatorFlag(request.getParameter("UWOperatorFlag"));
      if(transact.equals("UPDATE||MAIN"))
    {
    	tLDUWUserSchema1.setUserCode(request.getParameter("UserCode1"));
    	tLDUWUserSchema1.setUWType(request.getParameter("UWType1"));
    	tLDUWUserSchema1.setUWPopedom(request.getParameter("UWPopedom1"));   
    }    
   // tLDUWGradeSchema.setUserCode(request.getParameter("UserCode"));
   // tLDUWGradeSchema.setUWType(request.getParameter("UWType"));
   // tLDUWGradeSchema.setUWPopedom(request.getParameter("UWPopedom"));

   // tLDUWAmntGradeSchema.setUserCode(request.getParameter("UserCode"));
   // tLDUWAmntGradeSchema.setUWType(request.getParameter("UWType"));
   // tLDUWAmntGradeSchema.setUWPopedom(request.getParameter("UWPopedom"));
    
    //核保结论    UserCode UserName UWGrade UWType UWRate

      LDUWGradeSet tLDUWGradeSet = new LDUWGradeSet();  
   
	String tUWState[] = request.getParameterValues("UWResultGrid1");    
	String tUWType[] = request.getParameterValues("UWResultGrid2");	
      String tResultCount[] = request.getParameterValues("UWResultGridNo");	
    
	int ResultCount = 0;
	if (tResultCount != null) ResultCount = tResultCount.length;
	for (int i = 0; i < ResultCount; i++)	
	{
                LDUWGradeSchema tLDUWGradeSchema   = new LDUWGradeSchema();
                tLDUWGradeSchema.setUserCode(request.getParameter("UserCode"));
                tLDUWGradeSchema.setUWType(request.getParameter("UWType"));
                tLDUWGradeSchema.setUWPopedom(request.getParameter("UWPopedom"));
                tLDUWGradeSchema.setUWPopedomName(request.getParameter("UWPopedomName"));
		tLDUWGradeSchema.setUWState(tUWState[i]);
		tLDUWGradeSchema.setUWStateName(tUWType[i]);
		tLDUWGradeSet.add(tLDUWGradeSchema);
		
	}
	loggerDebug("LDIndUWUserSave","ResultCount:"+ResultCount);
    	loggerDebug("LDIndUWUserSave","UWPopedomName: " + request.getParameter("UWPopedomName"));
    //核保保额上限
      LDUWAmntGradeSet tLDUWAmntGradeSet = new LDUWAmntGradeSet();
        String MaxAmntCount[] = request.getParameterValues("UWMaxAmountGridNo");
        String UWKind[]=request.getParameterValues("UWMaxAmountGrid1");
        String MaxAmnt[]=request.getParameterValues("UWMaxAmountGrid3");
	int Count = 0;
	if (MaxAmntCount != null) Count = MaxAmntCount.length;
	loggerDebug("LDIndUWUserSave","UserCode"+request.getParameter("UserCode"));
	for (int i = 0; i < Count; i++)	
	{
		
		 LDUWAmntGradeSchema tLDUWAmntGradeSchema = new LDUWAmntGradeSchema();
               tLDUWAmntGradeSchema.setUserCode(request.getParameter("UserCode"));
               tLDUWAmntGradeSchema.setUWType(request.getParameter("UWType"));
               tLDUWAmntGradeSchema.setUWPopedom(request.getParameter("UWPopedom"));
               tLDUWAmntGradeSchema.setUWPopedomName(request.getParameter("UWPopedomName"));
		tLDUWAmntGradeSchema.setUWKind(UWKind[i]);
		tLDUWAmntGradeSchema.setMaxAmnt(MaxAmnt[i]);
                tLDUWAmntGradeSet.add(tLDUWAmntGradeSchema);
	}
	loggerDebug("LDIndUWUserSave","Count:"+Count);
    
  try
  {
  // 准备传输数据 VData
  	VData tVData = new VData();
	tVData.add(tLDUWUserSchema);
	tVData.add(tLDUWGradeSet);
	tVData.add(tLDUWAmntGradeSet);
	if(transact.equals("UPDATE||MAIN"))
	{
	  tVData.add(tLDUWUserSchema1);
	  loggerDebug("LDIndUWUserSave","UPDATE MAIN " + tLDUWUserSchema1.getUserCode());
	}
  	tVData.add(tG);
  	  loggerDebug("LDIndUWUserSave","操作是:"+transact);
    tLDIndUWUserUI.submitData(tVData,transact);
  }
  catch(Exception ex)
  {
    Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }
//如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    tError = tLDIndUWUserUI.mErrors;
    if (!tError.needDealError())
    {                          
    	Content = " 保存成功! ";
    	FlagStr = "Success";
    }
    else                                                                           
    {
    	Content = " 保存失败，原因是:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
  //添加各种预处理
%>                      
<%=Content%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
