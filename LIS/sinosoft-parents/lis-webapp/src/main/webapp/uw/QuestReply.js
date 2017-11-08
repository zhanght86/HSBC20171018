//程序名称：QuestReply.js
//程序功能：问题件回复
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var k = 0;
var turnPage = new turnPageClass();
var cflag = "1";  //问题件操作位置 1.核保

//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
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

function QuestQuery(tContNo, tFlag)
{
	// 初始化表格
	
	initQuestGrid();
	
	
	// 书写SQL语句
	k++;
	var strSQL = "";
	if (tFlag == "1")
	{
		
		var sqlid98="ContQuerySQL98";
		var mySql98=new SqlClass();
		mySql98.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql98.setSqlId(sqlid98);//指定使用的Sql的id
		mySql98.addSubPara(k);//指定传入的参数
		mySql98.addSubPara(k);//指定传入的参数
	    strsql=mySql98.getString();	
		
//		strSQL = "select code,codename,cont from ldcodemod where "+k+"="+k				 	
//				 + " and codetype = 'Question'";
	}
	
	//alert(strSQL);
	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有问题件描述");
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


function QueryCont(tContNo, tFlag, tQuest)
{	
	
	// 书写SQL语句
	//alert("begin");
	k++;

	var strSQL = "";

	if (tContNo == "")
	{
		alert("保单号不能为空！");
	}
	if (tQuest == "")
	{
		alert("问题件不能为空！");
	}
	if (tFlag == "")
	{
		alert("操作位置不能为空！");	
	}

		var sqlid99="ContQuerySQL99";
		var mySql99=new SqlClass();
		mySql99.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql99.setSqlId(sqlid99);//指定使用的Sql的id
		mySql99.addSubPara(k);//指定传入的参数
		mySql99.addSubPara(k);//指定传入的参数
		mySql99.addSubPara(tContNo);//指定传入的参数
		mySql99.addSubPara(tFlag);//指定传入的参数
		mySql99.addSubPara(tQuest);//指定传入的参数
	    strSQL=mySql99.getString();	

//	strSQL = "select issuecont,replyresult from lcissuepol where "+k+"="+k				 	
//			 + " and ContNo = '"+tContNo+"'"
//			 + " and operatepos = '"+tFlag+"'"
//			 + " and issuetype = '"+tQuest+"'";
	
	
	//alert(strSQL);
	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有录入过问题键！");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  var returnstr = "";
  var tcont = "";
  var treply = "";
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
  		}
  		else
  		{
  			alert("没有录入过问题键！");
  			return "";
  		}
  	
  }
  else
  {
  	alert("没有录入过问题键！");
	return "";
  }
 
  if (tcont == "")
  {
  	alert("没有录入过问题键！");
  	return "";
  }
  //alert(1);
  if (treply != "")
  {
 	alert("问题件已经回复，不能继续操作！");
  	
  	//window.close();
  	parent.close();
  	return "";
  }
  
  //alert("returnstr:"+returnstr);		
  document.all('Content').value = tcont;
  document.all('ReplyResult').value = treply;
  //alert("已经录入过问题键，请考虑清楚再重新录入！");
  return returnstr;
}

