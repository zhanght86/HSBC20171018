//程序名称：RReportReply.js
//程序功能：生存调查报告回复
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var k = 0;
var turnPage = new turnPageClass();
var turnPageDetail = new turnPageClass();
var cflag = "";  //问题件操作位置 1.核保

//提交，保存按钮对应操作
function submitForm()
{
  if(fm.Reporter.value==null || fm.Reporter.value=="")
  {
    alert("生调人不能为空！");
    return false;
  }
  if(fm.ReportFee.value==null || fm.ReportFee.value=="")
  {
    alert("生调费用不能为空！");
    return false;
  }
  if(fm.ReplyResult.value==null || fm.ReplyResult.value=="")
  {
    alert("生调回复内容不能为空！");
    return false;
  }
  if(fm.ReplyResult.value.length>2000)
  {
	  alert("生调回复内容不能超过2000字！");
	    return false;
  }
  document.all('ActivityID').value = ActivityID;
  //alert(ActivityID);
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  lockScreen('lkscreen');  
  fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    //showInfo.close();
    alert(content);
  }
  else
  { 
	var showStr="操作成功！";
  	alert(showStr);
  	top.opener.easyQueryClick();
   	top.close();
  	
  	
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
  else 
  {
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

// 查询按钮
function easyQueryClick()
{ 
 //  alert('%%%') ;
  var strsql = "";
  
  var sqlid917114209="DSHomeContSql917114209";
var mySql917114209=new SqlClass();
mySql917114209.setResourceName("uw.MSRReportReplyDetailSql");//指定使用的properties文件名
mySql917114209.setSqlId(sqlid917114209);//指定使用的Sql的id
mySql917114209.addSubPara(PolNo);//指定传入的参数
strsql = mySql917114209.getString();
// alert('%%%') ;
//  strsql = "select lcrreport.contno,lccont.prtno ,lcrreport.appntname,lcrreport.operator,lcrreport.makedate,"
//           + " lcrreport.replyoperator,lcrreport.replydate,decode(lcrreport.replyflag,'0','未回复','已回复'),lcrreport.prtseq "
//           + " from lcrreport,lccont where 1 = 1 "           
//           + "and lcrreport.contno = lccont.contno "
//           + "and lcrreport.contno = '" +PolNo + "'";
           //+ "and lcrreport.prtseq = '" + SerialNo +"'";
	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  alert("没有需要回复的生调报告！");
  //easyQueryClickInit();
  return "";
}
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = QuestGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strsql; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  return true;
}

// 查询按钮
function easyQueryChoClick()
{	
	
	k++;
	
	var strSQL = "";

	if (PolNo != null && PolNo != "")
	{
		var sqlid917114321="DSHomeContSql917114321";
var mySql917114321=new SqlClass();
mySql917114321.setResourceName("uw.MSRReportReplyDetailSql");//指定使用的properties文件名
mySql917114321.setSqlId(sqlid917114321);//指定使用的Sql的id
mySql917114321.addSubPara(PolNo);//指定传入的参数
mySql917114321.addSubPara(SerialNo);//指定传入的参数
mySql917114321.addSubPara(SerialNo);//指定传入的参数
strSQL=mySql917114321.getString();

		
//		strSQL = "select contente, remark, replycontente, missionid, submissionid from lcrreport,lwmission "
//		       + " where contno = '"+PolNo+"' and prtseq = '"+SerialNo+"' "
//		       + " and contno=missionprop2 and missionprop14='"+SerialNo+"' and activityid='0000001120'";
	}
	
	
	  //查询SQL，返回结果字符串
  turnPageDetail.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPageDetail.strQueryResult) {
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPageDetail.arrDataCacheSet = decodeEasyQueryResult(turnPageDetail.strQueryResult);

  fm.Content.value = turnPageDetail.arrDataCacheSet[0][0];
 // fm.Remark.value = turnPageDetail.arrDataCacheSet[0][1];
  fm.ReplyResult.value = turnPageDetail.arrDataCacheSet[0][2];
  fm.ProposalNoHide.value = PolNo;
  fm.PrtSeqHide.value = SerialNo;
  fm.MissionID.value = turnPageDetail.arrDataCacheSet[0][3];
  fm.SubMissionID.value = turnPageDetail.arrDataCacheSet[0][4];

  return true;
}

