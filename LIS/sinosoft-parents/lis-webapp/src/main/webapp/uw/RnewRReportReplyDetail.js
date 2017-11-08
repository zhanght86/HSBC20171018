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
var str_sql = "",sql_id = "", my_sql = "";   //绑定变量访问数据库

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
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
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
  
  var strsql = "";
//  strsql = "select Rnewrreport.contno,lccont.prtno ,Rnewrreport.appntname,Rnewrreport.operator,Rnewrreport.makedate,"
//           + " Rnewrreport.replyoperator,Rnewrreport.replydate,(case Rnewrreport.replyflag when '0' then '未回复' else '已回复' end),Rnewrreport.prtseq "
//           + " from Rnewrreport,lccont where 1 = 1 "           
//           + "and Rnewrreport.contno = lccont.contno "
//           + "and Rnewrreport.contno = '" +PolNo + "'";
  sql_id = "RnewRReportReplyDetailSql0";
 	my_sql = new SqlClass();  
 	my_sql.setResourceName("uw.RnewRReportReplyDetailSql"); //指定使用的properties文件名
 	my_sql.setSqlId(sql_id);//指定使用的Sql的id
 	my_sql.addSubPara(PolNo);//指定传入的参数
 	str_sql = my_sql.getString();
  //查询SQL，返回结果字符串
  turnPage.strQueryResult = easyQueryVer3(str_sql, 1, 0, 1);  
  
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
//		strSQL = "select contente, remark, replycontente, missionid, submissionid from Rnewrreport,lwmission "
//		       + " where contno = '"+PolNo+"' and prtseq = '"+SerialNo+"' "
//		       + " and contno=missionprop2 and missionprop14='"+SerialNo+"' and activityid='0000007014'";
	    sql_id = "RnewRReportReplyDetailSql1";
	 	my_sql = new SqlClass();  // document.getElementsByName(trim('CodeS'))[0].value
	 	my_sql.setResourceName("uw.RnewRReportReplyDetailSql"); //指定使用的properties文件名
	 	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	 	my_sql.addSubPara(PolNo);//指定传入的参数
	 	my_sql.addSubPara(SerialNo);//指定传入的参数
	 	my_sql.addSubPara(SerialNo);//指定传入的参数
	 	str_sql = my_sql.getString();
	}
	
	
	  //查询SQL，返回结果字符串
  turnPageDetail.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1);  
  
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

