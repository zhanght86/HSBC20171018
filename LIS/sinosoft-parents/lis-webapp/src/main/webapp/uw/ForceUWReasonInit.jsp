<%
//程序名称：ForceUWReasonInit.jsp
//程序功能：强制人工核保原因查询页面初始化
//创建日期：2005-10-11 11:10:36
//创建人  ：guanw
//更新记录：  更新人    更新日期     更新原因/内容
%>


<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String CurrentDate = PubFun.getCurrentDate();
  String CurrentTime = PubFun.getCurrentTime();
  String ContNo = request.getParameter("ContNo");
  loggerDebug("ForceUWReasonInit",ContNo);
%>
 <script language="JavaScript"> 
 	var ContNo = '<%=ContNo%>';
 	</script> 
<%
  loggerDebug("ForceUWReasonInit","11111111111=========="+ContNo);
%>  
<script language="JavaScript">
//表单初始化
function initForm(){
	//try{
		initSelBox();
		initInpBox();
	//}
	//catch(e){
	  //alert();
	//}	
}
//初始化输入框
function initInpBox() { 
  //document.all("ContNo").value=ContNo;
}

// 下拉框的初始化
function initSelBox(){                                                                       
 //查询投保单的强制进入人工核保状态                                                            
// var tSQL = "select ForceUWReason from lccont where contno='"+ContNo+"'";     
 
 var sqlid1="ForceUWReasonInitSql1";
 var mySql1=new SqlClass();
 mySql1.setResourceName("uw.ForceUWReasonInitSql");
 mySql1.setSqlId(sqlid1);//指定使用SQL的id
 mySql1.addSubPara(ContNo);//指定传入参数
 var tSQL = mySql1.getString();
 
 var arrResult = easyExecSql(tSQL); 
 if(arrResult==null){                                                                 
 return;                                                                                     
 }                                                                                           
 else                                                                                        
 {                                                                                 
 document.all('ForceUWReason').value=arrResult;                                       
     return;
  }
}
</script>
