//程序名称：PEdorUWManuRReport.js
//程序功能：保全人工核保生存调查报告录入
//创建日期：2002-06-19 11:10:36
//创建人  ：zhangxing
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var str = "";
var turnPage = new turnPageClass();
var k = 0;
var cflag = "1";  //保单类型操作位置 1.个人
var sqlresourcename = "uwgrp.UWManuUpReportReplySql";
//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
 
  if(fm.SubMissionID.value == "")
  {
  	
  	parent.close();
  	return;
  	}
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    showInfo.close();
    alert(content);
    parent.close();
  }
  else
  { 
	var showStr="操作成功";  	
  	showInfo.close();
  	alert(showStr);
  	parent.close();
  	
    //执行下一步操作
  }
}


//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}
         

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}

//初始化部分信息
function easyQueryClick()
{
	
	var tContNo = fm.ProposalNoHide.value;
	
	//var strSQL = "select ContNo,SaleChnl,AgentCode,ManageCom,BankCode from lccont where contno='"+tContNo+"'";
	
	var sqlid1="UWManuUpReportReplySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(tContNo);
	
	turnPage.strQueryResult  = easyQueryVer3(mySql1.getString(), 1, 0, 1);  
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  if(arrSelected == null)
  {
  	alert("查询保单基本信息失败")
  	return "";
  }
else
	{
	fm.all('ContNo').value = arrSelected[0][0];
	fm.all('SellType').value = arrSelected[0][1];
	fm.all('AgentCode').value = arrSelected[0][2];
	fm.all('ManageCom').value = arrSelected[0][3];
	fm.all('BankCode').value = arrSelected[0][4];
  }
	
	//var strSQl = "select ReportRemark,ReinsuDesc,ReinsuRemark,ReinsuredResult,usercode,makedate,modifydate from LCReinsureReport where contno = '"+tContNo+"'";
	
	var sqlid2="UWManuUpReportReplySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(tContNo);
	
	turnPage.strQueryResult  = easyQueryVer3(mySql2.getString(), 1, 0, 1);  
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  if(arrSelected == null)
  {
  	alert("没有再保回复信息!")
  	return "";
  }
else
	{
	fm.all('ReportRemark').value = arrSelected[0][0];
	fm.all('ReinsuDesc').value = arrSelected[0][1];
	fm.all('ReinsuRemark').value = arrSelected[0][2];
	fm.all('ReinsuredResult').value = arrSelected[0][3];
	fm.all('Operator').value = arrSelected[0][4];
	fm.all('MakeDate').value = arrSelected[0][5];
	fm.all('ReplyDate').value = arrSelected[0][6];
	}
	

}


