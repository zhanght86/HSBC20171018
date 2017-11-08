<%
//程序名称：PEdorTypeACSubmit.jsp
//程序功能：
//创建日期：2007-06-19 16:49:22
//创建人  ：Pst程序创建

%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
  <%@page import="com.sinosoft.service.*" %> 

<%
 //接收信息，并作校验处理。
  //输入参数
  //个人批改信息
  
  LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();
//  EdorDetailUI tEdorDetailUI = new EdorDetailUI();
	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  CErrors tError = null;
  //后面要执行的动作：添加，修改
 
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String transact = "";
  String Result="";
  String tConFirmName="";
  
  
  //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
  transact = request.getParameter("fmtransact");
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
  
  //个人保单批改信息
  
  tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
	tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
  tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
	tLPEdorItemSchema.setInsuredNo("000000");
  tLPEdorItemSchema.setPolNo("000000");
	tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorAppDate")); 
	tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate")); 
  tLPEdorItemSchema.setStandbyFlag1(request.getParameter("AppReason"));
  tLPEdorItemSchema.setStandbyFlag2(request.getParameter("UnValiReason")); 
  String  tCheckStr="";
  for(int t=1;t<=8;t++)
  {
    tCheckStr+="S"+t+"-"+request.getParameter("chkS"+t)+"\\";
  }
  tCheckStr=tCheckStr.substring(0,tCheckStr.lastIndexOf("\\"));
  tCheckStr+="$"; //分组符号
    for(int t=1;t<=3;t++)
  {
    tCheckStr+="O"+t+"-"+request.getParameter("chkO"+t)+"\\";
  }
  tCheckStr=tCheckStr.substring(0,tCheckStr.lastIndexOf("\\"));
  tCheckStr+="$"; //分组符号
  tLPEdorItemSchema.setStandbyFlag3(tCheckStr+request.getParameter("Remark"));

try
  {
  
     //准备传输数据 VData
  	 VData tVData = new VData();  
  	
		 //保存个人保单信息(保全)	
		 tVData.add(tG);
		 tVData.add(tLPEdorItemSchema);
//	   boolean tag = tEdorDetailUI.submitData(tVData,"");
	   boolean tag = tBusinessDelegate.submitData(tVData,"",busiName);
	   if (tag)
     {
          System.out.println("Successful");
     }
     else
     {
          System.out.println("Fail");
     }
	//System.out.println("6$$$$$$$$$$$$$\r\n$$$$$$$$$$$$$$\r\n$$$$$$$$$$$$$$\r\n$$$$$$$$$$$$$$\r\n$$$$$$$$$$$$$$");
	}
catch(Exception ex)
{
	Content = "保存失败，原因是:" + ex.toString();
  FlagStr = "Fail";
}			
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr.equals(""))
  {
//    tError = tEdorDetailUI.mErrors;
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {                          
      Content = " 保存成功！";
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
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

