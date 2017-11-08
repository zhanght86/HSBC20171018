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
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  document.getElementById("fm").submit(); //提交
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
  	initInpBox();
	initQuestGrid();
  	//showInfo.close();
  	
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
	
    	//initInpBox();
  initQuestGrid();
  
  
  var strsql = "";
  /*var wherePartStr = getWherePart( 'lccont.ContNo','QPrtNo' )
           +  getWherePart( 'Rnewrreport.name','QInsuredName' )
           +  getWherePart( 'lccont.ManageCom','QManageCom' );*/
  var sqlid916150045="DSHomeContSql916150045";
var mySql916150045=new SqlClass();
mySql916150045.setResourceName("uw.RnewRReportReplySql");//指定使用的properties文件名
mySql916150045.setSqlId(sqlid916150045);//指定使用的Sql的id
mySql916150045.addSubPara(fm.QPrtNo.value);//指定传入的参数
mySql916150045.addSubPara(fm.QInsuredName.value);
mySql916150045.addSubPara(fm.QManageCom.value);
strsql=mySql916150045.getString();
  
//  strsql = "select Rnewrreport.contno,lccont.prtno ,Rnewrreport.name,Rnewrreport.operator,Rnewrreport.makedate,"
//           + " Rnewrreport.replyoperator,Rnewrreport.replydate,Rnewrreport.replyflag,Rnewrreport.prtseq, lwmission.missionid, lwmission.submissionid "
//           + " from Rnewrreport,lccont,lwmission where 1 = 1 "           
//           + "and Rnewrreport.contno = lccont.contno and lccont.contno=lwmission.missionprop2 and ActivityID='0000007014'"
//           +  getWherePart( 'lccont.ContNo','QPrtNo' )
//           +  getWherePart( 'Rnewrreport.name','QInsuredName' )
//           +  getWherePart( 'lccont.ManageCom','QManageCom' )
//           + " and Rnewrreport.contente is not null"
//           + " and Rnewrreport.replycontente is null"
//           + " and prtseq is not null "
//           + " and Rnewrreport.replyflag = '0'"
//           + " order by Rnewrreport.makedate,Rnewrreport.maketime";
	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  alert("没有待回复的生调报告！");
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
function easyQueryChoClick(parm1,parm2)
{	
	// 书写SQL语句
	k++;
	var tProposalNo = "";
	var strSQL = "";

	if(document.all(parm1).all('InpQuestGridSel').value == '1' )
	{
	//当前行第1列的值设为：选中
   		tProposalNo = document.all(parm1).all('QuestGrid1').value;
   		tSerialNo = document.all(parm1).all('QuestGrid9').value;
   		//alert(tSerialNo);
  	}	

	if (tProposalNo != " ")
	{
         window.open( "RnewRReportReplyDetailMain.jsp?PolNo=" + tProposalNo + "&SerialNo=" + tSerialNo );
	}
	
}


function ClearContent()
{
	fm.Content.value = '';
    fm.ReplyResult.value = '';
    fm.ProposalNoHide.value = '';
}