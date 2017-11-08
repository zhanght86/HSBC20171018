<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWBankSendFlagInit.jsp
//程序功能：个人人工核保
//创建日期：2002-09-24 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%> 
<script language="JavaScript">
function initInpBox()
{ 
	//alert("2.1");
  fm.MissionID.value = tMissionID ;
  //alert("2.2");
	fm.SubMissionID.value = tSubMissionID ;
	//alert("2.3");
	fm.ActivityID.value = tActivityID ;
	//alert("2.4");
	fm.PrtNo.value = tPrtNo ;
	//alert("2");
    //var tSql = "select (case when NewAutoSendBankFlag is not null then NewAutoSendBankFlag else '1' end) from LCCont where ContNo='"+fm.PrtNo.value+"'";
  
    var sqlid1="UWBankSendFlagInitSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("uw.UWBankSendFlagInitSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addPara(fm.PrtNo.value);//指定传入的参数
    strSql=mySql1.getString();		
  
	//alert("tSql: "+tSql);
	var tNewAutoSendBankFlagResult=easyExecSql(strSql);
  //alert("tNewAutoSendBankFlagResult: "+tNewAutoSendBankFlagResult);
  tOldSendBankFlag = tNewAutoSendBankFlagResult[0][0];
  try
  {                                  
    document.all('NewAutoSendBankFlag').value = tNewAutoSendBankFlagResult[0][0];
  }
  catch(ex)
  {
    alert("在UWBankSendFlagInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
  	//alert("1");
		initInpBox();
  }
  catch(re)
  {
    alert("在PManuUWInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  
  }
}
</script>
