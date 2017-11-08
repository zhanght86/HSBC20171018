<%
//程序名称：PEdorTypePTSubmit.jsp
//程序功能：
//创建日期：2005-07-3 16:49:22
//创建人  ：lizhuo
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
 <%@page import="com.sinosoft.utility.*"%>
 <%@page import="com.sinosoft.lis.schema.*"%>
 <%@page import="com.sinosoft.lis.vschema.*"%>
 <%@page import="com.sinosoft.lis.bq.*"%>
 <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
 <%@page contentType="text/html;charset=GBK" %>
 <%@page import="com.sinosoft.service.*" %> 

<%
    //接收信息，并作校验处理。
    //输入参数
    //个人批改信息
    System.out.println("-----PT submit---");
    GlobalInput tG = new GlobalInput();
    LPPolSchema tLPPolSchema   = new LPPolSchema();
    LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();
//    EdorDetailUI tEdorDetailUI   = new EdorDetailUI();
//    GEdorDetailUI tGEdorDetailUI   = new GEdorDetailUI();
     String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	 String GbusiName="GEdorDetailUI";
	 BusinessDelegate GBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
    CErrors tError = new CErrors();
    //后面要执行的动作：添加，修改
 
    String tRela  = "";                
    String FlagStr = "";
    String Content = "";
    String transact = "";
    String Result = "Check";
    String AppObj = "";
    
  
    //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
    transact = request.getParameter("fmtransact");	  
	tG = (GlobalInput)session.getValue("GI");
	AppObj = request.getParameter("AppObj");	
  
    if(!FlagStr.equals("Fail"))
    {
    	//个人批改信息
	  	tLPEdorItemSchema.setPolNo("000000");
	  	tLPEdorItemSchema.setInsuredNo(request.getParameter("InsuredNo"));
    	tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	  	tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
	  	tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
	  	tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
	  	tLPEdorItemSchema.setStandbyFlag2(request.getParameter("RelationToAppnt"));	
    	try
    	{
        	// 准备传输数据 VData  
  	    	VData tVData = new VData(); 	
	      	//保存个人保单信息(保全)	
	 	    tVData.addElement(tG);
	 	    tVData.addElement(tLPEdorItemSchema);	 	    
	 	    System.out.println(" tLPEdorItemSchema ========================>"+tLPEdorItemSchema.encode()); 	    
	 	    if("G".equals(AppObj))
      		{
      		  	LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
      		  	tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
      		  	tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
      		  	tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
      		  	tVData.addElement(tLPGrpEdorItemSchema);
      		  	System.out.println("Group submitData");
//      		tGEdorDetailUI.submitData(tVData, "OnlyCheck");
      		  	GBusinessDelegate.submitData(tVData, "OnlyCheck" ,GbusiName);   
      		}
      		else
      		{
    	  		System.out.println("Person submitData");
//    	  		tEdorDetailUI.submitData(tVData, "OnlyCheck");
    	  		tBusinessDelegate.submitData(tVData, "OnlyCheck" ,busiName);	
    	  	}
	   	}
	   	catch(Exception ex)
	   	{
		    Content = "保存失败，原因是:" + ex.toString();
        	FlagStr = "Fail";
	   	}
	}			
    
    //如果在Catch中发现异常，则不从错误类中提取错误信息
    if (FlagStr=="")
    {
        if("G".equals(AppObj))
//            tError = tGEdorDetailUI.mErrors;
            tError = GBusinessDelegate.getCErrors(); 
        else
//        	tError = tEdorDetailUI.mErrors;
        	tError = tBusinessDelegate.getCErrors(); 
        if (!tError.needDealError())
        {     
           	Content = " 保存成功";
    	    FlagStr = "Success";
        }
        else                                                                           
        {
    	    Content = " 保存失败，原因是:" + tError.getFirstError();
    	    FlagStr = "Fail";
        }
    }
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=Result%>");
</script>
</html>

