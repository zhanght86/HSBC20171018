
var arrDataSet 
var tArr;
var turnPage = new turnPageClass();

var mDebug="0";
var mOperate="";
var showInfo;
window.onfocus=myonfocus;
//使得从该窗口弹出的窗口能够聚焦
function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();  
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}

//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
var iWidth=550;      //弹出窗口的宽度; 
var iHeight=250;     //弹出窗口的高度; 
var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

showInfo.focus();
  //fm.hideOperate.value=mOperate;
  //if (fm.hideOperate.value=="")
  //{
  //  alert("操作控制数据丢失！");
  //}
  //showSubmitFrame(mDebug);

  fm.submit(); //提交
  showDiv(operateButton,"true"); 
  showDiv(inputButton,"false"); 
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
	//alert("In afterSubmit");
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
    //执行下一步操作
    queryPages(document.all('ManageCom').value);
    queryPages(document.all('Host_Name').value);
  }

}



//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
//    showDiv(operateButton,"true"); 
//    showDiv(inputButton,"false"); 
	  initForm();
  }
  catch(re)
  {
  	alert("在OLDCode.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 

//取消按钮对应操作
function cancelForm()
{
//  window.location="../common/html/Blank.html";
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
}
 
//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作	
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


//Click事件，当点击增加图片时触发该函数
function addClick()
{
//  	var i = 0;
//  	var showStr="正在添加数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
//  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  showDiv(operateButton,"false"); 
  showDiv(inputButton,"true"); 
 	fm.fmtransact.value = "INSERT";
//	codeGrid.setRowColData(5,1,request.getParameter("ManageCom"));
//	codeGrid.setRowColData(5,2,request.getParameter("Host_Name"));
//  	fm.submit(); //提交
}           

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
  	alert("暂时不提供修改功能");
  //下面增加相应的代码
/*  if (confirm("您确实想修改该记录吗?"))
  {
  	var i = 0;
  	var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  	//showSubmitFrame(mDebug);
  	fm.fmtransact.value = "UPDATE";
  	fm.submit(); //提交 
  }
  else
  {
    //mOperate="";
    alert("您取消了修改操作！");
  } */
}           

//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{
  //下面增加相应的代码
  //mOperate="QUERY||MAIN";
  showInfo=window.open("./ServerMainQuery.html");
}           

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  //下面增加相应的删除代码
  if (confirm("您确实想删除该记录吗?"))
  {
  	
	var i = 0;
  	var showStr="正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  	//showSubmitFrame(mDebug);
  	fm.fmtransact.value = "DELETE";

  	fm.submit(); //提交
  	initForm();
  }
  else
  {
    //mOperate="";
    alert("您取消了删除操作！");
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




/*********************************************************************
 *  查询返回明细信息时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
 *  参数  ：  查询返回的二维数组
 *  返回值：  无
 *********************************************************************
 */

function afterQuery( arrQueryResult )
{
	var arrResult = new Array();

	//alert("afterQuery");
	// 书写SQL语句
//	var strSQL = " select ManageCom,HostName from ES_COM_SERVER where 1=1 " 
//				+ " "+ " and ManageCom = " + arrQueryResult[0][0] + " ";
	
	
	var sqlid0="ServerRelationSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("easyscan.ServerRelationSql"); //指定使用的properties文件名
	mySql0.setSqlId(sqlid0);//指定使用的Sql的id
	mySql0.addSubPara(arrQueryResult[0][0]);//指定传入的参数
	var strSQL=mySql0.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
  
	//判断是否查询成功
	if (!turnPage.strQueryResult) {
		alert("查询失败！");
		return false;
    }
	//查询成功则拆分字符串，返回二维数组
	arrResult = decodeEasyQueryResult(turnPage.strQueryResult);
	
	if( arrResult != null )
	{
		document.all( 'ManageCom' ).value =	arrResult[0][0];
		document.all( 'Host_Name' ).value = 	arrResult[0][1];
		
		queryPages(arrResult[0][0]);
	}       
	else
		alert("arrQueryResult is null!");
}               
        
        
// 查询按钮
function queryPages(strDoc_id)
{
	// 书写SQL语句
//	var strSQL = "select ManageCOM,HostName from ES_COM_SERVER where 1=1 " ;
	
	var sqlid1="ServerRelationSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("easyscan.ServerRelationSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	var strSQL=mySql1.getString();
	
	//alert(strSQL);
	turnPage.queryModal(strSQL, CodeGrid);    
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
}



//Click事件 点击'保存修改'按钮时触发，实现保存MultiLine当前修改的数据 非通用
function saveUpdate()
{
  if (confirm("您确实想保存修改吗?"))
  {
  	fm.fmtransact.value = "UPDATE";
  	
  	//选中所有行
  	//CodeGrid.checkBoxAll ()

  	fm.submit(); //提交
  	//initForm();
  }
}        
//Click事件 点击'删除选中'按钮时触发，实现删除选中的MultiLine行数据 非通用
function deleteChecked()
{
  if (confirm("您确实想删除选中的单证页数据吗?"))
  {
	
  	fm.fmtransact.value = "DELETE";

  	fm.submit(); //提交
  	//重新查询
  	//queryPages(document.all( 'DOC_ID' ).value);
  	//initForm();
  }
}
        
//***************************************************************


function addNewComp()
{
  	
  	fm.fmtransact.value = "INSERT";
//	codeGrid.setRowColData(5,1,request.getParameter("ManageCom"));
//	codeGrid.setRowColData(5,2,request.getParameter("Host_Name"));
  	fm.submit(); //提交
}

function easyQuery()
{
	// 书写SQL语句
//	var strSQL = "select ManageCom,HostName from ES_COM_SERVER where 1=1 " 
//					+ " "+ getWherePart( 'ManageCom' );	
	
  	var  ManageCom2 = window.document.getElementsByName(trim("ManageCom"))[0].value;
	var sqlid2="ServerRelationSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("easyscan.ServerRelationSql"); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(ManageCom2);//指定传入的参数
	var strSQL=mySql2.getString();
	
	//alert(strSQL);
	turnPage.queryModal(strSQL, CodeGrid);    
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	
/*	// 初始化表格
	initCodeGrid();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
  
	//判断是否查询成功
	if (!turnPage.strQueryResult) {
		alert("查询失败！");
		return false;
    }
	//查询成功则拆分字符串，返回二维数组
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
	//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
	turnPage.pageDisplayGrid = CodeGrid;    
          
	//保存SQL语句
	turnPage.strQuerySql     = strSQL; 
	//设置查询起始位置
	turnPage.pageIndex       = 0;  
  
	//在查询结果数组中取出符合页面显示大小设置的数组
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
 	tArr=chooseArray(arrDataSet,[0,1,2,3,4,5,6,7,8,9]) 
 	
	//调用MULTILINE对象显示查询结果
	displayMultiline(tArr, turnPage.pageDisplayGrid);
*/ 
}	
