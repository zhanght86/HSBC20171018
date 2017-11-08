//程序名称：UWManuHealth.js
//程序功能：新契约人工核保体检资料录入
//创建日期：2004-11-19 11:10:36
//创建人  ：HYQ
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var turnPage = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "uwgrp.UWManuHealthSql";
//提交，保存按钮对应操作
function submitForm()
{ 
	if (fm.all('InsureNo').value == "")   
	    {
	   	 alert("请输入体检人号码！");
	   	 return;
    	}
  else
    		 
     { 	
	var tCustomerNo = fm.all('InsureNo').value;
	var tContno = fm.all('ContNo').value; 
	
  //arrResult = easyExecSql("select * from LCPENotice where contno='" + tContno + "' and CustomerNo='" + tCustomerNo + "'", 1, 0);
  
  var sqlid1="UWManuHealthSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(tContno);
		mySql1.addSubPara(tCustomerNo);
		arrResult = easyExecSql(mySql1.getString(), 1, 0);
  
		
  if (arrResult != null)
     {
       alert("体检通知已经录入,但未打印，不能录入新体检资料!");
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

  fm.action= "./UWManuHealthChk.jsp";
  fm.submit(); //提交
      }
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
    //parent.close();
  }
  else
  { 
	var showStr="操作成功";
  	//showInfo.close();
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


function manuchkhealthmain()
{
	fm.submit();
}

/*********************************************************************
 *  执行待新契约体检通知书的EasyQuery
 *  描述:查询显示对象是体检通知书
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function easyQueryClickSingle()
{
	var strsql = "";
	var tContNo = "";
	var tEdorNo = "";
	tContNo = fm.ContNo.value;
	tMissionID = fm.MissionID.value;
	tInsuredNo = fm.InsureNo.value;
	QueryReason();
	/*
  strsql = "select LWMission.SubMissionID from LWMission where 1=1"
				 //+ " and LWMission.MissionProp2 = '"+ tContNo + "'"
				 + " and LWMission.ActivityID = '0000001101'"
				 + " and LWMission.ProcessID in ('0000000003', '0000000000')"
				 + " and LWMission.MissionID = '" +tMissionID +"'";
	 */
	  var sqlid2="UWManuHealthSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(tMissionID);	
			 
	turnPage.strQueryResult = easyQueryVer3(mySql2.getString(), 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("不容许录入新体检通知书！");
    return "";
  }
   
   turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
   fm.SubMissionID.value = turnPage.arrDataCacheSet[0][0];
   turnPage = new turnPageClass();			 
	/*
	 strsql = "select LCPENotice.ContNo,LCPENotice.name,LCPENotice.pedate,LCPENotice.peaddress,LCPENotice.PEBeforeCond,LCPENotice.remark,LCPENotice.printflag from LCPENotice where 1=1"
				 + " and LCPENotice.ContNo = '"+ tContNo + "'"
				 + " and LCPENotice.customerno = '"+ tInsuredNo + "'";
	*/			 
	 var sqlid3="UWManuHealthSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(tContNo);	
		mySql3.addSubPara(tInsuredNo);	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult = easyQueryVer3(mySql3.getString(), 1, 1, 1);  

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    //alert("查询失败！");
    easyQueryClickInit();
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.ContNo.value = turnPage.arrDataCacheSet[0][0];
  //fm.InsureNo.value = turnPage.arrDataCacheSet[0][1];
  //fm.EDate.value = turnPage.arrDataCacheSet[0][2];
  fm.Hospital.value = turnPage.arrDataCacheSet[0][3];
  fm.IfEmpty.value = turnPage.arrDataCacheSet[0][4];
  fm.Note.value = turnPage.arrDataCacheSet[0][5];
  fm.PrintFlag.value = turnPage.arrDataCacheSet[0][6];
  easyQueryClick();
 
}

function QueryReason()
{
		// 书写SQL语句

  var strsql = "";
	var tContNo = "";
	var tEdorNo = "";
	tContNo = fm.ContNo.value;
	tMissionID = fm.MissionID.value;
	tInsuredNo = fm.InsureNo.value;
  if(tContNo != "" && tInsuredNo != "")
  {
	
	/*
	strSQL = "select a.contno,a.uwno,a.uwerror,a.modifydate from LCUWError a where 1=1 "
			 + " and a.PolNo in (select distinct polno from lcpol where contno ='" +tContNo+ "')"
			 + " and SugPassFlag = 'H'"
			 + " and (a.uwno = (select max(b.uwno) from LCUWError b where b.ContNo = '" +tContNo + "' and b.PolNo = a.PolNo))"
			 + " union "
			 + "select c.contno,c.uwno,c.uwerror,c.modifydate from LCCUWError c where 1=1"
			 + " and c.ContNo ='" + tContNo + "'"
			 + " and SugPassFlag = 'H'"
			 + " and (c.uwno = (select max(d.uwno) from LCCUWError d where d.ContNo = '" + tContNo + "'))"
		*/
		 var sqlid4="UWManuHealthSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename);
		mySql4.setSqlId(sqlid4);
		mySql4.addSubPara(tContNo);	
		mySql4.addSubPara(tContNo);	
		mySql4.addSubPara(tContNo);	
		mySql4.addSubPara(tContNo);	
		
				 				 
  //查询SQL，返回结果字符串
  turnPage.strQueryResult = easyQueryVer3(mySql4.getString(), 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = UWErrGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strsql; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
 }
}
// 查询按钮
function easyQueryClick()
{
	// 书写SQL语句
	var strsql = "";
	var tContNo = "";
	var tEdorNo = "";
	tContNo = fm.ContNo.value;
	tInsuredNo = fm.InsureNo.value; 
  
  if(tContNo != "" && tInsuredNo != "")
  {/*
	strsql = "select peitemcode,peitemname from LCPENoticeItem where 1=1"
				 + " and ContNo = '"+ tContNo + "'"
				 + " and PrtSeq in (select distinct prtseq from lcpenotice where contno = '"+ tContNo + "'"
				 + " and customerno = '"+ tInsuredNo + "')";
	*/			 				 
	 var sqlid5="UWManuHealthSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(sqlresourcename);
		mySql5.setSqlId(sqlid5);
		mySql5.addSubPara(tContNo);	
		mySql5.addSubPara(tContNo);	
		mySql5.addSubPara(tInsuredNo);	

  //查询SQL，返回结果字符串
  turnPage.strQueryResult = easyQueryVer3(mySql5.getString(), 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  //turnPage.pageDisplayGrid = HealthGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strsql; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  //displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  }
  
  
 
  return true;
}


// 无体检资料查询按钮
function easyQueryClickInit()
{
	fm.action= "./UWManuHealthQuery.jsp";
	fm.submit();
}

function displayEasyResult( arrResult )
{
	var i, j, m, n;
	if( arrResult == null )
		alert( "没有找到相关的数据!" );
	else
	{		
		arrGrid = arrResult;
		// 显示查询结果
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				HealthGrid.setRowColData( i, j+1, arrResult[i][j] );
				
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}


function initInsureNo(tPrtNo)
{
	var i,j,m,n;
	var returnstr;
	/*
	var strSql = "select InsuredNo,name from lcinsured where 1=1 "
	       + " and Prtno = '" +tPrtNo + "'"
	       + " union select CustomerNo , Name from LCInsuredRelated where polno in (select polno from lcpol where Prtno = '" +tPrtNo+"')";
	//查询SQL，返回结果字符串
	*/
	 var sqlid6="UWManuHealthSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(sqlresourcename);
		mySql6.setSqlId(sqlid6);
		mySql6.addSubPara(tPrtNo);	
		mySql6.addSubPara(tPrtNo);	
	
	
  turnPage.strQueryResult  = easyQueryVer3(mySql6.getString(), 1, 1, 1);  
  
  //判断是否查询成功
  //alert(turnPage.strQueryResult);
  if (turnPage.strQueryResult == "")
  {
    alert("查询失败！");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;
  //alert("N:"+n);
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
  fm.InsureNo.CodeData = returnstr;
  return returnstr;
}

//初始化医院
function initHospital(tContNo)
{
	var i,j,m,n;
	var returnstr;
	/*
	var strSql = "select hospitcode,hospitname from ldhospital where 1=1 "
	       //+ "and codetype = 'hospitalcodeuw'"
	       + "and areacode = trim((select managecom from lccont where ContNo = '"+tContNo+"'))";
	*/
	 var sqlid7="UWManuHealthSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName(sqlresourcename);
		mySql7.setSqlId(sqlid7);
		mySql7.addSubPara(tContNo);	

	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(mySql7.getString(), 1, 1, 1);  
  //alert(strSql);
  //判断是否查询成功
  //alert(turnPage.strQueryResult);
  if (turnPage.strQueryResult == "")
  {
    //alert("医院初始化失败！");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  //turnPage.pageDisplayGrid = VarGrid;    
          
  //保存SQL语句
  //turnPage.strQuerySql     = strSql; 
  
  //设置查询起始位置
  //turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  //var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  //displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;
  //alert("N:"+n);
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
  fm.Hospital.CodeData = returnstr;
  return returnstr;
}

function showNewUWSub()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
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
  
  cContNo=fm.ContNo.value;

  	//window.open("./PUWErrMain.jsp?ProposalNo1="+cProposalNo,"window1");
  	window.open("./UWPEErrMain.jsp?ContNo="+cContNo,"window1",sFeatures);
  	           
  	showInfo.close();
  	
}                      
