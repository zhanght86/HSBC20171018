//程序名称：UWManuSpec.js
//程序功能：人工核保条件承保
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
var cflag = "1";  //问题件操作位置 1.核保
var sqlresourcename = "uwgrp.UWManuSpecSql";
//提交，保存按钮对应操作
function submitForm()
{
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

function QueryPolSpecGrid(tContNo)
{
	// 初始化表格
	// 书写SQL语句
	var strSQL = "";
	var i, j, m, n;	
	
       //获取原保单信息
       /*
        strSQL = "select LCPol.ContNo,LCPol.PrtNo,LCPol.PolNo,LCPol.RiskCode,LCPol.RiskVersion,LCPol.AppntName,LCPol.InsuredName from LCPol where "				 			
			 + "  ContNo ='"+tContNo+"'"
			 + "  order by polno ";	
			 */		
	var sqlid1="UWManuSpecSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(tContNo);
		
	turnPage.strQueryResult  = easyQueryVer3(mySql1.getString(), 1, 0, 1); 

  //判断是否查询成功
  if (turnPage.strQueryResult) {  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = UWSpecGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}
 
  return true;	
}


function QueryPolSpecCont()
{
	
		var strSQL = "";
	
       //获取原保单信息
       // strSQL = "select cont from ldcodemod where 1 =1 and codetype='spectype'";		
       /*
       strSQL = "select cont from ldcodemod where "+k+"="+k				 	
				 + " and codetype = 'spectype'"
				 ;	
			*/	 
				 var sqlid2="UWManuSpecSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(k);
		mySql2.addSubPara(k);
				 
	turnPage.strQueryResult  = easyQueryVer3(mySql2.getString(), 1, 0, 1); 

  //判断是否查询成功
  if (turnPage.strQueryResult) 
  {  
  	
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = UWSpecContGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}	
  

}
function getPolGridCho()
{
  var tSelNo = UWSpecGrid.getSelNo()-1;
  var cPolNo = UWSpecGrid.getRowColData(tSelNo,3);
  var tcontno = UWSpecGrid.getRowColData(tSelNo,1);
  //alert(tcontno);
 if(cPolNo != null && cPolNo != "" )
  {
    fm.all("PolNo").value = cPolNo;
   
    QuerySpecReason(cPolNo);   
    QuerySpec(tcontno);
  }	
}



//查询已经录入特约内容
function QuerySpec(tcontno)
{
	
	// 书写SQL语句
	var strSQL = "";
	var i, j, m, n;	
	/*
	strSQL = "select speccontent from LCSpec where contno='"+tcontno+
			   "' and SerialNo in (select max(SerialNo) from lcspec where contno = '"+tcontno+"')";
	*/
	var sqlid3="UWManuSpecSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(tcontno);
		mySql3.addSubPara(tcontno);
			
	var arrResult=easyExecSql(mySql3.getString());  
	try{fm.all('Remark').value= arrResult[0][0];}catch(ex){};                                                                                          
//	tur   try{fm.all('AppntNo').value= arrResult[0][0];}catch(ex){};                                                                     nPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
//
//  //判断是否查询成功
//  if (!turnPage.strQueryResult) {
//    return "";
//  }
//  
//  //查询成功则拆分字符串，返回二维数组
//  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
//  
//  fm.Remark.value = turnPage.arrDataCacheSet[0][0];	
  
  return true;	
}


//查询已经录入加费特约原因
function QuerySpecReason(cPolNo)
{
	
	// 书写SQL语句
	var strSQL = "";
	var i, j, m, n;	
	/*
	strSQL = "select specreason from LCUWMaster where 1=1 "
			 + " and proposalno = '"+cPolNo+"'";
	*/
	var sqlid4="UWManuSpecSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename);
		mySql4.setSqlId(sqlid4);
		mySql4.addSubPara(cPolNo);
	
	turnPage.strQueryResult  = easyQueryVer3(mySql4.getString(), 1, 0, 1); 

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.SpecReason.value = turnPage.arrDataCacheSet[0][0];
  //fm.Reason.value = turnPage.arrDataCacheSet[0][1];
  
  return true;	
}


function getSpecGridCho()
{
	var tContent = fm.Remark.value;
	
	var tSelNo = UWSpecContGrid.getSelNo()-1;
	var tRemark = UWSpecContGrid.getRowColData(tSelNo,1);	
	
  fm.Remark.value = tRemark + tContent;
  

		
}