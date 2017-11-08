<%@include file="../i18n/language.jsp"%>



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
  
<%
 //接收信息，并作校验处理。
 //输入参数
 


 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 TransferData transferData = new TransferData();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 String tFromPath = getServletContext().getRealPath( "/")+ "productdef\\sugconfig\\";
 
 /*
 	fm.all("RiskCode").value = Mulline9Grid.getRowColData(selNo-1,1);
	fm.all("RequDate").value = Mulline9Grid.getRowColData(selNo-1,3);
	fm.all("RiskName").value = Mulline9Grid.getRowColData(selNo-1,2);
	fm.all("MissionID").value = Mulline9Grid.getRowColData(selNo-1,5);
	fm.all("SubMissionID").value = Mulline9Grid.getRowColData(selNo-1,6);
	fm.all("ActivityID").value = Mulline9Grid.getRowColData(selNo-1,7);
 */
  //向transferData插入发布原因
 

 String tChk[] = request.getParameterValues("InpMulline11GridChk"); 

 if(request.getParameter("PlatformType") != null && request.getParameter("PlatformType").trim().equals("1") && tChk != null){
 for(int index=0;index<tChk.length;index++)
 {
   if(tChk[index].equals("0")) { 
	   FlagStr = "Fail";
	   Content = ""+"有未测试的要素"+"";
%>
	<script type="text/javascript">
 		parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	</script>
<% 
   }
 }  
 }
 //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
 operator = request.getParameter("operator");
 String tChk1[] = request.getParameterValues("InpMulline9GridChk"); 
 String[] Mulline9Grid1=request.getParameterValues("Mulline9Grid1");
 String[] Mulline9Grid2=request.getParameterValues("Mulline9Grid2");
 String[] Mulline9Grid3=request.getParameterValues("Mulline9Grid3");
 String[] Mulline9Grid5=request.getParameterValues("Mulline9Grid5");
 String[] Mulline9Grid6=request.getParameterValues("Mulline9Grid6");
 String[] Mulline9Grid7=request.getParameterValues("Mulline9Grid7");
 String ReleasePlatform=request.getParameter("ReleasePlatform");
 String SysType=request.getParameter("SysType");
 String EnvType=request.getParameter("EnvType");
 String PDNOY=request.getParameter("pd_noy");
 String IsDeleteCoreDataBeforeInsert=request.getParameter("IsDeleteCoreDataBeforeInsert");
 String deployReason=request.getParameter("deployReason");
 System.out.println(request.getParameter("PlatformType") + " "+"发布原因为："+"" + request.getParameter("deployReason"));
//参数格式=” Inp+MulLine对象名+Chk”  
for(int index=0;index<tChk1.length;index++){
	
	transferData = new TransferData();
    if(tChk1[index].equals("1")){
             transferData.setNameAndValue("RiskCode",Mulline9Grid1[index]);  
             transferData.setNameAndValue("RequDate",Mulline9Grid3[index]);
             transferData.setNameAndValue("MissionID",Mulline9Grid5[index]);
             transferData.setNameAndValue("ActivityID",Mulline9Grid7[index]);
             transferData.setNameAndValue("SubMissionID",Mulline9Grid6[index]);
             transferData.setNameAndValue("RiskName",Mulline9Grid2[index]);
             transferData.setNameAndValue("DeplDate",PubFun.getCurrentDate());
             transferData.setNameAndValue("Operator",tG.Operator); 
             transferData.setNameAndValue("ReleasePlatform",ReleasePlatform);
             transferData.setNameAndValue("SysType",SysType);
             transferData.setNameAndValue("EnvType",EnvType);
             transferData.setNameAndValue("flag","N");// 工作流组件不提交数据
             transferData.setNameAndValue("IsDeleteCoreDataBeforeInsert",IsDeleteCoreDataBeforeInsert);
             transferData.setNameAndValue("deployReason",deployReason);
             transferData.setNameAndValue("PDNOY",PDNOY);
	try {
		  // 准备传输数据 VData
		  VData tVData = new VData();
		  tVData.add(tG);
		  transferData.setNameAndValue("FromPath",tFromPath);
		  tVData.add(transferData);
		  PDTestDeployBL pDTestDeployBL = new PDTestDeployBL();
		  pDTestDeployBL.submitData(tVData,operator);
	 }catch(Exception ex){
		  Content =Content+ ""+"保存失败，原因是:"+"" + ex.toString();
		  FlagStr = "Fail";
	 }                   
 	}             
}

 //如果在Catch中发现异常，则不从错误类中提取错误信息
 if (FlagStr=="")
 {
   Content = " "+"保存成功!"+" ";
   FlagStr = "Success";
  }
  else                                                                           
  {
   Content = " "+"保存失败!"+"";
   FlagStr = "Fail";
  }

 //添加各种预处理
%>                      
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

