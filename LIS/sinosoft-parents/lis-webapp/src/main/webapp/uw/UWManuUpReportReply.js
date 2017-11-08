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
 
  if(fm.SubMissionID.value == "")
  {
  	
  	parent.close();
  	return;
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

  document.getElementById("fm").submit(); //提交
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
	
	var tContNo = fm.ContNo.value;
	var tPrtNo = fm.PrtNo.value;
	
	//var strSQL = "select ContNo,SaleChnl,AgentCode,ManageCom,BankCode from lccont where contno='"+tContNo+"'";
//	var strSQL = " select ContNo,"
//             + " (select codename"
//             + " from ldcode"
//             + " where codetype = 'salechnl'"
//             + " and code = SaleChnl),"
//             + " AgentCode,"
//             + " ManageCom,"
//             + " BankCode"
//             + " from lccont"
//             + " where contno = '" + tContNo + "'";
//	
	var sqlid1="UWManuUpReportReplySql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("uw.UWManuUpReportReplySql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(tContNo);//指定传入的参数
	var strSQL=mySql1.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  if(arrSelected == null)
  {
  	alert("查询保单基本信息失败！")
  	return "";
  }
else
	{
	document.all('ContNo').value = arrSelected[0][0];
	document.all('SaleChnl').value = arrSelected[0][1];
	document.all('AgentCode').value = arrSelected[0][2];
	document.all('ManageCom').value = arrSelected[0][3];
	//document.all('BankCode').value = arrSelected[0][4];
  }
	
	//var strSQl = "select ReportRemark,ReinsuDesc,ReinsuRemark,ReinsuredResult,usercode,makedate,modifydate,dealusercode from LCReinsureReport where contno = '"+tContNo+"'";
//	var strSQl = " select ReportRemark,"
//             + " ReinsuDesc,"
//             + " ReinsuRemark,"
//             + " (select codename"
//             + " from ldcode"
//             + " where codetype = 'uqreportstate'"
//             + " and code = ReinsuredResult),"
//             + " usercode,"
//             + " makedate,"
//             + " modifydate,"
//             + " dealusercode"
//             + " from LCReinsureReport"
//             + " where contno = '" + tContNo + "'";
	
	var sqlid2="UWManuUpReportReplySql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("uw.UWManuUpReportReplySql"); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(tContNo);//指定传入的参数
	var strSQl=mySql2.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQl, 1, 0, 1);  
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  if(arrSelected == null)
  {
  	alert("没有再保回复信息!")
  	return "";
  }
else
	{
	document.all('ReportRemark').value = arrSelected[0][0];
	document.all('ReinsuDesc').value = arrSelected[0][1];
	document.all('ReinsuRemark').value = arrSelected[0][2];
	document.all('ReinsuredResult').value = arrSelected[0][3];
	document.all('Operator').value = arrSelected[0][4];
	document.all('MakeDate').value = arrSelected[0][5];
	document.all('ReplyDate').value = arrSelected[0][6];
	document.all('ReplyPerson').value = arrSelected[0][7];
	}
	

}

function querytrace(){
//alert(document.all('PrtNo').value);
   var tContNo = fm.ContNo.value;
//    var SQL = "select t.reportremark,(select d.codename from ldcode d where d.codetype = 'uqreportstate' and d.code = t.reinsuredresult),t.reinsudesc,t.reinsuremark,t.makedate,t.usercode,t.modifydate,t.dealusercode from lcreinsurereporttrace t where t.contno = '"+tContNo+"' order by t.makedate,t.maketime";
    
    var sqlid3="UWManuUpReportReplySql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName("uw.UWManuUpReportReplySql"); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(tContNo);//指定传入的参数
	var  SQL=mySql3.getString();
	
    turnPage.queryModal(SQL, TraceGrid);
   

}


