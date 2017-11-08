//程序名称：PEdorUWManuRReport.js
//程序功能：保全人工核保生存调查报告录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
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
  	alert("已录入生调通知书,但未打印,不容许录入新的生调通知书!");
  	//easyQueryClick();
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


function manuchkspecmain()
{
	fm.submit();
}


// 查询按钮
function easyQueryClick()
{	
	// 书写SQL语句
	k++;
	var strSQL = "";
	var tPolNo = fm.PolNo.value;	
	var tMissionID = fm.MissionID.value;
	var tEdorNo= fm.EdorNo.value;		
	//alert(tProposalNo);
	if (tPolNo != " ")
	{
//		strSQL = "select polno,operator,contente from lprreport where polno = '"+tPolNo+"' and replycontente is null";
		var sqlid1="PEdorUWManuRReportSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("bq.PEdorUWManuRReportSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(tPolNo);//指定传入的参数
		strSQL=mySql1.getString();
	}
	
   turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (turnPage.strQueryResult)
   {    
	  //查询成功则拆分字符串，返回二维数组
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	  fm.PolNo.value = turnPage.arrDataCacheSet[0][0];
	  fm.Operator.value = turnPage.arrDataCacheSet[0][1];
	  fm.Content.value = turnPage.arrDataCacheSet[0][2];
 }
//  strsql = "select LWMission.SubMissionID from LWMission where 1=1"
//				 + " and LWMission.MissionProp1 = '" + tEdorNo +"'"
//				 + " and LWMission.MissionProp2 = '"+ tPolNo + "'"
//				 + " and LWMission.ActivityID = '0000000004'"
//				 + " and LWMission.ProcessID = '0000000000'"
//				 + " and LWMission.MissionID = '" +tMissionID +"'";	
  var strsql = "";
  var sqlid="PEdorUWManuRReportSql2";
	var mySql=new SqlClass();
	mySql.setResourceName("bq.PEdorUWManuRReportSql"); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(tEdorNo);//指定传入的参数
	mySql.addSubPara(tPolNo);//指定传入的参数
	mySql.addSubPara(tMissionID);//指定传入的参数
	strsql=mySql.getString();
	turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);    
	  //判断是否查询成功
	  if (!turnPage.strQueryResult) {
	    fm.SubMissionID.value = "";
	    return "";
      }
  
   turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
   fm.SubMissionID.value = turnPage.arrDataCacheSet[0][0];
   
  
  return true;
}
