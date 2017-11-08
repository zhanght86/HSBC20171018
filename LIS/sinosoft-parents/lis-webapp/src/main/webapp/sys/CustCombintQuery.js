//程序名称：QuestQuery.js
//程序功能：问题件查询
//创建日期：2002-09-24 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var k = 0;
var turnPage = new turnPageClass();
var cflag = "";  //问题件操作位置 1.核保
var canReplyFlag = false;
var mySql = new SqlClass();
//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
//  var name='提示';   //网页名称，可为空; 
//  var iWidth=550;      //弹出窗口的宽度; 
//  var iHeight=250;     //弹出窗口的高度; 
//  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
//  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
//  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
//
//  showInfo.focus();

  fm.submit(); //提交
}

//提交，保存按钮对应操作
function IfPrint()
{
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
//　var name='提示';   //网页名称，可为空; 
//	var iWidth=550;      //弹出窗口的宽度; 
//	var iHeight=250;     //弹出窗口的高度; 
//	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
//	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
//	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
//	
//	showInfo.focus();

  cContNo = fm.ContNo.value;  //保单号码	
　//var strSql = "select * from LCPol where PolNo='" + cContNo + "' and  approveflag = '2'";
   mySql = new SqlClass();
	mySql.setResourceName("sys.CustCombintQuerySql");
	mySql.setSqlId("CustCombintQuerySql1");
	mySql.addSubPara(cContNo);  
    var arrResult = easyExecSql(mySql.getString());
       //alert(arrResult);
    if (arrResult != null) {     
   　　alert("已发核保通知书，但未打印,不容许在此时修改问题件的打印标志！");
          return;
      }      
  fm.action = "./QuestQueryUpdatePrintFlag.jsp";
  fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
//    var name='提示';   //网页名称，可为空; 
//    var iWidth=550;      //弹出窗口的宽度; 
//    var iHeight=350;     //弹出窗口的高度; 
//    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
//    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
//    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
//
//    showInfo.focus();

    //showInfo.close();
    alert(content);
  }
  else if (FlagStr == "Succ") {
    content = "操作成功！";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px"); 
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=350;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

  }
  else
  { 
	var showStr="打印标记更新成功";
  	alert(showStr);
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


function manuchkspecmain()
{
	fm.submit();
}

function query()
{
	// 初始化表格
	initQuestGrid();
	initContent();

	// 书写SQL语句
	k++;
	var strSQL = "";
//	var ifreply = fm.IfReply.value;
//	var tOPos = fm.OperatePos.value;
//	var tOperatePos = fm.Flag.value;
	var tContNo = fm.ContNo.value;

//	if (tOperatePos == "")
//	{
//		alert("操作位置传输失败!");
//		return "";
//	}
//	//if(tOPos == "")
//	//{
//	//	alert("操作位置不能为空！");
//	//	return "";
//	//}
//	
//	if (tOperatePos == "1"||tOperatePos == "3"||tOperatePos == "5")
//	{
//		if(tContNo == "")
//		{
//			alert("保单号不能为空!");
//			return "";
//		}
//	}
	
	
		/*strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "				 	
				 + " and issuetype='99' and contno='"+tContNo+"'"
				 + " and IsueManageCom like '" + manageCom + "%%'"*/
//				 + " and ReplyMan is not null "
//				 + " and replyresult is null";
	 	  	//alert(strSQL);  	
mySql = new SqlClass();
	mySql.setResourceName("sys.CustCombintQuerySql");
	mySql.setSqlId("CustCombintQuerySql2");
	mySql.addSubPara(cContNo);  
	mySql.addSubPara(manageCom);  
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);  
  
  //判断是否查询成功
  if (turnPage.strQueryResult == false) {
    alert("没有客户合并通知书");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = QuestGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;	
}


function queryone(parm1,parm2)
{	
	
	// 书写SQL语句
	//alert("begin");
	k++;

	var strSQL = "";
	var tcho;
	var tOperatePos = fm.Flag.value;
	if(fm.all(parm1).all('InpQuestGridSel').value == '1' )
	{
	//当前行第1列的值设为：选中
   		tPos = fm.all(parm1).all('QuestGrid7').value;
   		tQuest = fm.all(parm1).all('QuestGrid2').value;
   		tSerialNo = fm.all(parm1).all('QuestGrid10').value;
  	}
  	
	fm.all('HideOperatePos').value=tPos;
	fm.all('HideQuest').value=tQuest;
    fm.all('HideSerialNo').value=tSerialNo;
    fm.all('SerialNo').value=tSerialNo;
	tContNo = fm.ContNo.value;
	//tQuest = fm.Quest.value;
	//tPos = fm.OperatePos.value;
	if (tPos == "")
	{
		alert("请选择问题件!")
		return "";
	}	
	
	if (tContNo == "")
	{
		alert("保单号不能为空！");
		return "";
	}
	if (tQuest == "")
	{
		alert("问题件不能为空！");
		return "";
	}
	if (tSerialNo == "")
	{
		alert("问题件编码不能为空！");
		return "";
	}
	
	if (tOperatePos == "16")
	{
	
		/*strSQL = "select issuecont,replyresult,issuetype,OperatePos from lcgrpissuepol where "+k+"="+k+" "
						+ " and grpcontno = (select grpcontno from lccont where contno = '"+tContNo+"')"*/
		//alert(strSQL);
	mySql = new SqlClass();
	mySql.setResourceName("sys.CustCombintQuerySql");
	mySql.setSqlId("CustCombintQuerySql3");
	mySql.addSubPara(tContNo);   
	}
	else
	{
		/*strSQL = "select issuecont,replyresult,issuetype,OperatePos,operator,makedate,modifydate from lcissuepol where "+k+"="+k+" "
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'issuetype','HideQuest' )
				 + getWherePart( 'OperatePos','HideOperatePos')
				 + getWherePart( 'SerialNo','HideSerialNo');*/
				 //+ getWherePart( 'BackObjType','BackObj')
				 //+ getWherePart( 'ManageCom','ManageCom')
				 //+ getWherePart( 'IssueManageCom','OManageCom')
		mySql = new SqlClass();
	mySql.setResourceName("sys.CustCombintQuerySql");
	mySql.setSqlId("CustCombintQuerySql4");
	mySql.addSubPara(fm.ContNo.value);   
	mySql.addSubPara(fm.HideQuest.value);  
	mySql.addSubPara(fm.HideOperatePos.value);  
	mySql.addSubPara(fm.HideSerialNo.value);  		 
	}
	
	//alert(strSQL);
	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有录入过问题件！");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  var returnstr = "";
  var tcont = "";
  var treply = "";
  var ttype = "";
  var tOperatePos = "";
  var tOperator="";
  var tMakeDate = "";
  var tModifyDate="";
  var n = turnPage.arrDataCacheSet.length;
  //alert("N:"+n);
  if (n > 0)
  {
  	m = turnPage.arrDataCacheSet[0].length;
  		//alert("M:"+m);
  		if (m > 1)
  		{
  			//alert("turnPage:"+turnPage.arrDataCacheSet[0][0]);
			tcont = turnPage.arrDataCacheSet[0][0];
			treply = turnPage.arrDataCacheSet[0][1];
			ttype = turnPage.arrDataCacheSet[0][2];
			tOperatePos = turnPage.arrDataCacheSet[0][3];
			tOperator= turnPage.arrDataCacheSet[0][4];
			tMakeDate = turnPage.arrDataCacheSet[0][5];
			tModifyDate= turnPage.arrDataCacheSet[0][6];
  		}
  		else
  		{
  			alert("没有录入过问题件！");
  			return "";
  		}
  	
  }
  else
  {
  	alert("没有录入过问题件！");
	return "";
  }
 
  if (tcont == "")
  {
  	alert("没有录入过问题件！");
  	return "";
  }
  
  fm.all('Content').value = tcont;
  
  if (treply == "") {
    fm.all('replyresult').readOnly = false;
    canReplyFlag = true;
  }
  else {
    fm.all('replyresult').readOnly = true;
    canReplyFlag = false;
  }
  fm.all('replyresult').value = treply;
  
  fm.all('Type').value = ttype;
  fm.all('OperatePos').value = tOperatePos;
   fm.all('Operator').value = tOperator;
  
  fm.all('MakeDate').value = tMakeDate;
  fm.all('ReplyDate').value = tModifyDate;
  return returnstr;
}

function input()
{
	cContNo = fm.ContNo.value;  //保单号码
	
	//showModalDialog("./QuestInputMain.jsp?ContNo1="+cContNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("./QuestInputMain.jsp?ContNo1="+cContNo+"&Flag="+cflag,"window1","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
	
}

function quickReply() {
  if (QuestGrid.getSelNo() == "0") {
    alert("请先选择一条问题件！");
    return;
  }
  
  fm.ReplyResult.value = "问题修改完成！";
  reply();
}
function replySave()
{
		fm.QuesFlag.value = "allover";   //置"回复完毕"标志
		fm.action = "./QuestReplyChk.jsp";
		fm.submit();	
}
function reply()
{ 
	fm.QuesFlag.value = "";
  if (fm.ReplyResult.value == "") {
    alert("没有回复内容，不能回复！");  
    return;
  }
  
  if (!canReplyFlag) {
    alert("该问题件已回复，不能再次回复！");  
    return;
  }
  
	cContNo = fm.ContNo.value;  //保单号码
	cQuest = fm.HideQuest.value;            //问题件类型
	cflag = fm.HideOperatePos.value;        //问题件操作位置 
	
	fm.ContNoHide.value = fm.ContNo.value;  //保单号码
	fm.Quest.value = fm.HideQuest.value;            //问题件类型
	fm.Flag.value = fm.HideOperatePos.value;        //问题件操作位置 
	fm.SerialNo.value = fm.HideSerialNo.value;
	if(cQuest == "")
	{
		alert("请选择要回复问题件!");
	}
	else
	{
		//showModalDialog("./QuestInputMain.jsp?ContNo1="+cContNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
		//window.open("./QuestReplyMain.jsp?ContNo1="+cContNo+"&Flag="+cflag+"&Quest="+cQuest,"window2");
		fm.action = "./QuestReplyChk.jsp";
		fm.submit();
	}
	
}

function QuestQuery(tContNo)
{
	// 初始化表格
	var i,j,m,n;
	//initQuestGrid();
	
	
	// 书写SQL语句
	k++;
	var strSQL = "";
	//if (tFlag == "1")
	//{
	/*	strSQL = "select code,cont from ldcodemod where "+k+"="+k				 	
				 + " and codetype = 'Question'";*/
	//}
	mySql = new SqlClass();
	mySql.setResourceName("sys.CustCombintQuerySql");
	mySql.setSqlId("CustCombintQuerySql5"); 
	//alert(strSQL);
	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有问题件描述");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  //alert(turnPage.arrDataCacheSet);
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  //turnPage.pageDisplayGrid = QuestGrid;    
          
  //保存SQL语句
  //turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  //turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  //var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  //displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;  
    if (n > 0)
  {
  	for( i = 0;i < n; i++)
  	{
  		m = turnPage.arrDataCacheSet[i].length;
  		//alert("M:"+m);
  		if (m > 0)
  		{
  			for( j = 0; j< m; j++)
  			{
  				if (i == 0 && j == 0)
  				{
  					returnstr = "0|^"+turnPage.arrDataCacheSet[i][j];
  				}
  				if (i == 0 && j > 0)
  				{
  					returnstr = returnstr + "|" + turnPage.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j == 0)
  				{
  					returnstr = returnstr+"^"+turnPage.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j > 0)
  				{
  					returnstr = returnstr+"|"+turnPage.arrDataCacheSet[i][j];
  				}
  				
  			}
  		}
  		else
  		{
  			alert("查询失败!!");
  			return "";
  		}
  	}
}
else
{
	alert("查询失败!");
	return "";
}
  //alert("returnstr:"+returnstr);		
 // fm.Quest.CodeData = returnstr;
  return "";	
}


function QuestPicQuery(){
	alert("建设中……");
	 var tsel=QuestGrid.getSelNo()-1; 
	 //alert(ContNo); 
	 if(tsel<0){
     alert("请选择保单!");
     retutn;	 
   }
   var ContNo=QuestGrid.getRowColData(tsel,1); 
	window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");								
    
	
 	
}
