//程序名称：QuestInput.js
//程序功能：问题件录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var k = 0;
var turnPage = new turnPageClass();
var sqlresourcename = "uwgrp.QuestInputSql";
//提交，保存按钮对应操作
function submitForm()
{
	if(LoadFlag=="1"&&fm.all('BackObj').value=="1"){
	  alert("在新契约录单时不允许选择“发送对象”为“问题件修改岗”，请重新选择“发送对象”！");
	  return;
	}
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
    
    showInfo.close();
   
    //parent.close();
  }
  else
  { 
	var content="操作成功";
  	var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  	 //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
  	showInfo.close();
  	
  	//parent.close();
  	
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
	var i,j,m,n;
	//initQuestGrid();
	
	
	// 书写SQL语句
	k++;
	var strSQL = "";
	//if (tFlag == "1")
	//{
	/*
		strSQL = "select code,cont from ldcodemod where "+k+"="+k				 	
				 + " and codetype = 'Question'"
				 + " and code <> '99'";
	//}
	*/
	//alert(strSQL);
	
  //查询SQL，返回结果字符串
            var mysql1= new SqlClass();
            var sqlid1 = "QuestInputSql1";
			mysql1.setResourceName(sqlresourcename);
			mySql1.setSqlId(sqlid1);
			mysql1.addSubPara(k);
			mysql1.addSubPara(k);
  
  turnPage.strQueryResult  = easyQueryVer3(mysql1.getString(), 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有问题件描述");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
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
  fm.Quest.CodeData = returnstr;
  return "";	
}


function QueryCont(tContNo, tFlag)
{	
	
	// 书写SQL语句

	k++;

	var strSQL = "";

	//if (tFlag == "1")
	//{
		//strSQL = "select issuecont from lcissuepol where "+k+"="+k				 	
		//		 + " and ContNo = '"+tContNo+"'";
	//}
	var mysql2= new SqlClass();
	var sqlid2 = "QuestInputSql2";
			mysql2.setResourceName(sqlresourcename);
			mySql2.setSqlId(sqlid2);
			mysql2.addSubPara(k);
			mysql2.addSubPara(k);
			mysql2.addSubPara(tContNo);
	//alert(strSQL);
	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(mysql2.getString(), 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有录入过问题键！");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;
  //alert("N:"+n);
  if (n > 0)
  {
  	m = turnPage.arrDataCacheSet[0].length;
  		//alert("M:"+m);
  		if (m > 0)
  		{
  			//alert("turnPage:"+turnPage.arrDataCacheSet[0][0]);
			returnstr = turnPage.arrDataCacheSet[0][0];
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

  if (returnstr == "")
  {
  	alert("没有录入过问题键！");
  }
  
  //alert("returnstr:"+returnstr);		
  fm.all('Content').value = returnstr;
  //alert("已经录入过问题键，请考虑清楚再重新录入！");
  return returnstr;
}

function afterCodeSelect( cCodeName, Field )
{

	var tObjFlag = fm.all('BackObj').value;
	
	var tCustomerNoFlag = fm.all('QuestionObj').value;
	
	
	if(cCodeName=="backobj")
	{
	if(tObjFlag == '2')
	{
		
	divCustomerqustion.style.display = '';
  }
else if(tObjFlag == '1')
	{
		divCustomerqustion.style.display = 'none';
		divquestobjname.style.display = 'none';
	  divquestobj.style.display = 'none';
	}
}

if(cCodeName == "question")
{
	
if(tCustomerNoFlag =='0')
{
	
  initAppnt();
}
if(tCustomerNoFlag =='1')
{
	
	initInsured();
}
if(tCustomerNoFlag == '2'||tCustomerNoFlag == '3')
{
	fm.all('CustomerNo').value = "";
	fm.all('CustomerName').value = "";
}
	
}
}
function initAppnt()
{
	var tContNo = fm.ContNo.value;
	//strsql = " select appntno,appntname from lccont where  ContNo = '"+tContNo+"' ";
	var mysql3= new SqlClass();
	var sqlid3 = "QuestInputSql3";
	mysql3.setResourceName(sqlresourcename);
	mySql3.setSqlId(sqlid3);
	mysql3.addSubPara(tContNo);

  //查询SQL，返回结果字符串
  turnPage.strQueryResult = easyQueryVer3(mysql3.getString(), 1, 1, 1);  

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
      
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.CustomerNo.value = turnPage.arrDataCacheSet[0][0];
 
  fm.CustomerName.value = turnPage.arrDataCacheSet[0][1];
}
function initInsured()
{
	var tContNo = fm.ContNo.value;
//	alert(tContNo+","+fm.all('QuestionObj').value);
    /*
	strsql = "select insuredno,Name from lcinsured "
	       + "where  ContNo = '"+tContNo+"' "
	       + "and SequenceNo = '"+fm.all('QuestionObj').value+"'";
	*/
	var mysql4= new SqlClass();
	var sqlid4 = "QuestInputSql4";
	mysql4.setResourceName(sqlresourcename);
	mySql4.setSqlId(sqlid4);
	mysql4.addSubPara(tContNo);
    mysql4.addSubPara(fm.all('QuestionObj').value);
  //查询SQL，返回结果字符串
  turnPage.strQueryResult = easyQueryVer3(mysql4.getString(), 1, 1, 1);  

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
      
    alert("无此客户");
    return ;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.CustomerNo.value = turnPage.arrDataCacheSet[0][0];
 
  fm.CustomerName.value = turnPage.arrDataCacheSet[0][1];
}