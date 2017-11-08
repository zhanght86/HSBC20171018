<%@include file="../i18n/language.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%><%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：PDTestDeploySave.jsp
//程序功能：产品测试与发布
//创建日期：2009-3-18
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>  
<%
 //接收信息，并作校验处理。
 //输入参数
 

 //PDDeployPubRuleBL pPDDeployPubRuleBL = new PDDeployPubRuleBL();
 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 TransferData transferData = new TransferData();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
 //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
 System.out.println(1);
 operator = request.getParameter("operator");
 try{
  System.out.println(2);
 transferData.setNameAndValue("DeplDate",PubFun.getCurrentDate());
 transferData.setNameAndValue("ReleasePlatform",request.getParameter("ReleasePlatform"));
 transferData.setNameAndValue("RuleType",request.getParameter("RuleType"));
 transferData.setNameAndValue("SysType",request.getParameter("SysType"));
 transferData.setNameAndValue("EnvType",request.getParameter("EnvType"));
 transferData.setNameAndValue("flag","N");// 工作流组件不提交数据
 }catch(Exception e){e.printStackTrace();}
 if("UWDEPLOY".equals(operator)){
  	transferData.setNameAndValue("Operator",operator);
 	List<String> list=new ArrayList<String>();
    String tChk[] = request.getParameterValues("InpMulline8GridChk");     
    String[] Mulline8Grid1=request.getParameterValues("Mulline8Grid1");  
    for(int i=0;i<tChk.length;i++){
        if(tChk[i].equals("1")){
        list.add(Mulline8Grid1[i]);
        }
    }
    transferData.setNameAndValue("Mulline8Grid",list);
  }else if("UWDEPLOYSELECT".equals(operator)){
     transferData.setNameAndValue("Operator",operator);
     transferData.setNameAndValue("SQL",request.getParameter("SQL"));         
  }else{
   transferData.setNameAndValue("Operator",tG.Operator);
  }
  
  
 //String tChk[] = request.getParameterValues("InpMulline11GridChk"); 
 // if(request.getParameter("PlatformType") != null && request.getParameter("PlatformType").trim().equals("1") && tChk != null){
 //for(int index=0;index<tChk.length;index++)
 //{
  // if(tChk[index].equals("0")) { 
	   //FlagStr = "Fail";
	  // Content = "有未测试的要素";
%>
	<script type="text/javascript">
 		//parent.fraInterface.afterSubmit("</%=FlagStr%>","</%=Content%>");
	</script>
<% 
	//return;  
  // }
 //}  
 //}

 try
 {
  // 准备传输数据 VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  String busiName="PDDeployPubRuleBL";
  tBusinessDelegate.submitData(tVData, operator,busiName);
 }
 catch(Exception ex)
 {
  Content = ""+"保存失败，原因是:"+"" + ex.toString();
  FlagStr = "Fail";
 }

 //如果在Catch中发现异常，则不从错误类中提取错误信息
 if (FlagStr=="")
 {
  tError = tBusinessDelegate.getCErrors();
  if (!tError.needDealError())
  {                          
   Content = " "+"保存成功!"+" ";
   FlagStr = "Success";
  }
  else                                                                           
  {
   Content = " "+"保存失败，原因是:"+"" + tError.getFirstError();
   FlagStr = "Fail";
  }
 }

 //添加各种预处理
%>                      
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

