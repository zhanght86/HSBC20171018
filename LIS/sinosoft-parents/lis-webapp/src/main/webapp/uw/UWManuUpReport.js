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

//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
 //alert(fm.ReportRemark.value);
 if(fm.ReportReason.value.trim()=="")
 {
 	alert("录入的呈报原因为空，请重新录入！");
 	return false;
 	}
 if(fm.ReportRemark.value.trim()=="")
 {
 	alert("录入的呈报原因描述为空，请重新录入！");
 	return false;
 	}
  if(fm.SubMissionID.value == "")
  {
  	
  	parent.close();
  	return;
  	}
  if(!checkPolConfirm()){
	  return false;
  }
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
lockScreen('lkscreen');  
  fm.submit(); //提交
}

//add by lzf 2013-04-03
function checkPolConfirm()
{
  var mMissionID = fm.MissionID.value;
//  var sql = " select count(1) from lwmission where activityid not in (select activityid from lwactivity where functionid in "
//	         +"('10010012','10010021','10010022','10010023','10010024','10010028','10010037','10010039','10010049'))"
//             + " and missionid = '"+mMissionID+"'";
  
    var sqlid1="UWManuUpReportSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("uw.UWManuUpReportSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(('10010012','10010021','10010022','10010023','10010024','10010028','10010037','10010039','10010049'));//指定传入的参数
	mySql1.addSubPara(mMissionID);//指定传入的参数
	var sql=mySql1.getString();
  
  var rs = 0;
  rs = easyExecSql(sql); 
  
  if (rs>=1) {
	alert("还有通知书或者再保呈报处理没有回复，不允许提起再保呈报处理");	
	return false;
  }
  return true;
}
//end 

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
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
	var showStr="操作成功！";  	
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
	
//	var strSQL = "select PrtNo,ContNo,SaleChnl,AgentCode,ManageCom,BankCode,InsuredName from lccont where contno='"+tContNo+"'";
	
	 var sqlid2="UWManuUpReportSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.UWManuUpReportSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(tContNo);//指定传入的参数
		var strSQL=mySql2.getString();
		
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	document.all('PrtNo').value = arrSelected[0][0];
	document.all('ContNo').value = arrSelected[0][1];
	document.all('SaleChnl').value = arrSelected[0][2];
	document.all('AgentCode').value = arrSelected[0][3];
	document.all('ManageCom').value = arrSelected[0][4];
	//document.all('BankCode').value = arrSelected[0][5];
    document.all('CustomerName').value = arrSelected[0][6];
}


function initLWMission()
{
	var MissionID = fm.MissionID.value;
//	var strSQL = "select submissionid from lwmission where missionid ='"+MissionID+"'"
//							 + " and activityid = (select activityid from lwactivity where functionid ='10010039')"
//								;
//						
	 var sqlid3="UWManuUpReportSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("uw.UWManuUpReportSql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(MissionID);//指定传入的参数
		var strSQL=mySql3.getString();
		
  var brr = easyExecSql(strSQL);
 
	if ( brr )  //已经申请保存过
 	{
 		var tSubMissionID = brr[0][0];
 		fm.SubMissionID.value = tSubMissionID;
 		
	}	 

}