<%
//程序名称：ForceUWInit.jsp
//程序功能：
//创建日期：2005-05-19 08:49:52
//创建人  ：HL
//更新记录：  更新人    更新日期     更新原因/内容
%>


<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String CurrentDate = PubFun.getCurrentDate();
  String CurrentTime = PubFun.getCurrentTime();
%>  
<script language="JavaScript">
//表单初始化
function initForm(){
	try{
		initSelBox();
		initInpBox();
	}
	catch(e){
	  alert();	
	}
	
}

//初始化输入框
function initInpBox() { 
  document.all("ContNo").value=ContNo;
}

// 下拉框的初始化
function initSelBox(){
	//alert("aa");
  //查询投保单的强制进入人工核保状态
 // var tSQL = "select ForceUWFlag,ForceUWReason from lccont where contno='"+ContNo+"'";
              
     var sqlid1="ForceUWInitSql1";
	 var mySql1=new SqlClass();
	 mySql1.setResourceName("uw.ForceUWInitSql");
	 mySql1.setSqlId(sqlid1);//指定使用SQL的id
	 mySql1.addSubPara(ContNo);//指定传入参数
	 var tSQL = mySql1.getString();
  
  //alert("ContNo="+ContNo);
  var arrResult = easyExecSql(tSQL,1,0);
	//alert("arrResult[0][0]"+arrResult[0][0]);
  if(arrResult[0][0]==null){
    return;
  }
  else
  {
  	//alert("arrResult[0][0]"+arrResult[0][0]);
  	document.all('ForceUWOpt').value=arrResult[0][0];
  	document.all('ForceUWRemark').value=arrResult[0][1];
    return;
  }
  

}

</script>
