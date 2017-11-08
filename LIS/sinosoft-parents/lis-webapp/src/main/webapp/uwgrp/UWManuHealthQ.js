//程序名称：PEdorUWManuHealth.js
//程序功能：保全人工核保体检资料录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var turnPage = new turnPageClass();
var arrResult;
var turnPage1=new turnPageClass();
var turnPage2=new turnPageClass();
var sqlresourcename = "uwgrp.UWManuHealthQSql";
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

  fm.action= "./UWManuHealthChk.jsp";
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


// 查询按钮
function easyQueryClickSingle()
{
	// 书写SQL语句
	var strsql = "";
	var tContNo = "";
	var tEdorNo = "";
	tContNo = fm.ContNo.value;
	tInsuredNo = fm.InsureNo.value;
  if(tContNo != "" && tInsuredNo != "")
  {/*
	strsql = "select LCPENotice.ContNo,LCPENotice.PrtSeq,LCPENotice.CustomerNo,LCPENotice.Name,LCPENotice.PEDate,LCPENotice.MakeDate,LOPRTManager.StateFlag from LCPENotice,LOPRTManager where 1=1"
				+ " and LCPENotice.PrtSeq = LOPRTManager.PrtSeq"
				 + " and ContNo = '"+ tContNo + "'"
				 + " and Customerno = '"+ tInsuredNo + "'";
		*/		 				 
		var sqlid1="UWManuHealthQSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(tContNo);
		mySql1.addSubPara(tInsuredNo);
  //查询SQL，返回结果字符串
  turnPage.strQueryResult = easyQueryVer3(mySql1.getString(), 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = MainHealthGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strsql; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  }
  return true;
 
}


// 查询按钮
function easyQueryClick(parm1,parm2)
{


	// 书写SQL语句
	var strsql = "";
	var tContNo = "";
	var tEdorNo = "";
	tContNo = fm.ContNo.value;
	var tSelNo = MainHealthGrid.getSelNo()-1;
  	var tPrtSeq = MainHealthGrid.getRowColData(tSelNo,2);
  if(tContNo != "" && tPrtSeq != "")
  {/*
      var strsql1="select PEAddress,PEDoctor,PEDate,REPEDate,operator,makedate,modifydate,masculineflag,remark from LCPENotice where 1=1"	 
				  + " and ContNo = '"+ tContNo + "'"
				  + " and PrtSeq = '"+ tPrtSeq + "'";
				  //alert(strsql1);
	*/			  
		var sqlid2="UWManuHealthQSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(tContNo);
		mySql2.addSubPara(tPrtSeq);
				  
       //arrResult=easyExecSql(strsql1,1,0);
       var arrReturn = new Array();
       arrReturn = easyExecSql(mySql2.getString());
       //alert("arrReturn="+arrReturn);
        if(arrReturn!=null)
        {
        	//alert(222);
        	fm.all('PEAddress').value = arrReturn[0][0];
        	fm.all('PEDoctor').value = arrReturn[0][1];
        	
        	fm.all('PEDate').value = arrReturn[0][2];
       	 	fm.all('REPEDate').value = arrReturn[0][3];
       	 	
       	 	fm.all('Operator').value = arrReturn[0][4];
        	fm.all('MakeDate').value = arrReturn[0][5];
        	//alert(333);
        	fm.all('ReplyDate').value = arrReturn[0][6];
        	fm.all('MasculineFlag').value = arrReturn[0][7];
        	fm.all('Note').value = arrReturn[0][8];
        }
  }
  if(tContNo != "" && tPrtSeq != "")
  {/*
	 var tstrsql = "select peitemcode,peitemname,PEItemResult from LCPENoticeItem where 1=1"
				
				  + " and ContNo = '"+ tContNo + "'"
				  + " and PrtSeq = '"+ MainHealthGrid.getRowColData(MainHealthGrid.getSelNo()-1,2) + "'";
	*/
	var sqlid3="UWManuHealthQSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(tContNo);
		mySql3.addSubPara(MainHealthGrid.getRowColData(MainHealthGrid.getSelNo()-1,2));
	
	//alert(tstrsql);
	//fm.PEAddress.value = tstrsql;
	//turnPage.queryModal(tstrsql, HealthGrid);
	//alert()
 
	
turnPage.strQueryResult  = easyQueryVer3(mySql3.getString(), 1, 0, 1);  

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	
    alert("未查询到满足条件的数据！");
     return false;
  }
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  turnPage.pageLineNum = 30 ;
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = HealthGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = tstrsql; 
  
  
  //arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  }
  
  //查询体检结论信息
  /*
  var Sql = "select DisDesb,DisResult,ICDCode from LCPENoticeResult where 1=1"
           + " and ContNo = '"+ tContNo + "'"
				 + " and PrtSeq = '"+ tPrtSeq + "'";
				  //查询SQL，返回结果字符串
				  //alert(Sql);
	*/			  
				  var sqlid4="UWManuHealthQSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename);
		mySql4.setSqlId(sqlid4);
		mySql4.addSubPara(tContNo);
		mySql4.addSubPara(tPrtSeq);
				  
  turnPage.strQueryResult = easyQueryVer3(mySql4.getString(), 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = DisDesbGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = Sql; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  	//查询其他体检信息	
  	/*		 
	var strSQL = "select * from LCPENotice where 1=1"	
				+" and ContNo = '"+tContNo+"'"
				+" and PrtSeq = '"+tPrtSeq+"'"; 	
		
	*/
		var sqlid5="UWManuHealthQSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(sqlresourcename);
		mySql5.setSqlId(sqlid5);
		mySql5.addSubPara(tContNo);
		mySql5.addSubPara(tPrtSeq);
	
	var arrReturn = new Array();
        arrReturn = easyExecSql(mySql5.getString());
      
   if (arrReturn == null) {
			  alert("未查体检信息");
			} else {
			   displayHealth(arrReturn[0]);
			}  
			
  return true;
 
}

function displayHealth(cArr)
{
	
  	try { fm.all('Note').value = cArr[21]; } catch(ex) { };
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

function initInsureNo()
{
	
	var i,j,m,n;
	var returnstr;
	
	var tContNo = fm.ContNo.value;
	
		//alert(tPrtSeq);
	//查询SQL，返回结果字符串
	/*
 var strSql = " select ContNo,PrtSeq,CustomerNo,Name,Pedate,MakeDate,PrintFlag from lcpenotice where 1=1 "
          + " and ContNo = '"+tContNo+"'";
          */
 var sqlid6="UWManuHealthQSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(sqlresourcename);
		mySql6.setSqlId(sqlid6);
		mySql6.addSubPara(tContNo);

  turnPage.strQueryResult  = easyQueryVer3(mySql6.getString(), 1, 1, 1);  
  
  //判断是否查询成功
  //alert(turnPage.strQueryResult);
  if (!turnPage.strQueryResult)
  {
    alert("查询失败！");
    return "";
  }
  
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  
 //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = MainHealthGrid;

 //保存SQL语句
  turnPage.strQuerySql   = strSql;
  
 //设置查询起始位置
  turnPage.pageIndex = 0; 
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
  
   //调用MULTILINE对象显示查询结果
   displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
//    
//    var prtSeq = fm.PrtSeq.value;
//		var CustomerNo = fm.CustomerNo.value;
//		var arrReturn;
//		var strSQL = "select Operator,MakeDate,ModifyTime from LCPENotice where PrtSeq = '"+prtSeq+"' and CustomerNo='"+CustomerNo+"'";
//		alert(strSQL);
//		try{
//			arrReturn =easyExecSql(strSQL);
//		}
//		catch(ex)
//		{
//			alert( "查询发送人失败！");		
//		}
//		
//		fm.all.('Operator').value = arrReturn[0][0];
//		fm.all.('MakeDate').value = arrReturn[0][1];
//		return;	
//} 


}

  function initCustomerNo()
{
	
	var i,j,m,n;
	var returnstr;
	
	var tContNo = fm.ContNo.value;
	var tCustomerNo = fm.CustomerNo.value;
	
		//alert(tPrtSeq);
	//查询SQL，返回结果字符串
	/*
 var strSql = " select ContNo,PrtSeq,CustomerNo,Name,Pedate,MakeDate,PrintFlag from lcpenotice where 1=1 "
          + " and ContNo = '"+tContNo+"'"
          + " and CustomerNo = '"+tCustomerNo+"'";
 */
 var sqlid7="UWManuHealthQSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName(sqlresourcename);
		mySql7.setSqlId(sqlid7);
		mySql7.addSubPara(tContNo);
 mySql7.addSubPara(tCustomerNo);
  turnPage.strQueryResult  = easyQueryVer3(mySql7.getString(), 1, 1, 1);  
  
  //判断是否查询成功
  //alert(turnPage.strQueryResult);
  if (!turnPage.strQueryResult)
  {
    alert("查询失败！");
    return "";
  }
  
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  
 //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = MainHealthGrid;

 //保存SQL语句
  turnPage.strQuerySql   = strSql;
  
 //设置查询起始位置
  turnPage.pageIndex = 0; 
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
  
   //调用MULTILINE对象显示查询结果
   displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
   
}


//初始化医院
function initHospital(tContNo)
{
	var i,j,m,n;
	var returnstr;
	/*

	var strSql = "select code,codename from ldcode where 1=1 "
	       + "and codetype = 'hospitalcodeuw'"
	       + "and comcode = (select managecom from lccont where ContNo = '"+tContNo+"')";
	//查询SQL，返回结果字符串
	*/
	var sqlid8="UWManuHealthQSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName(sqlresourcename);
		mySql8.setSqlId(sqlid8);
		mySql8.addSubPara(tContNo);

	
  turnPage.strQueryResult  = easyQueryVer3(mySql8.getString(), 1, 1, 1);  
  
  //判断是否查询成功
  //alert(turnPage.strQueryResult);
  if (turnPage.strQueryResult == "")
  {
    alert("医院初始化失败！");
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


/*********************************************************************
 *  体检疾病信息保存
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function saveDisDesb()
{
		if(DisDesbGrid.mulLineCount<1){
		alert("疾病结果栏不能为空！");
		return false;		
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
	
 
  fm.action= "./UWManuHealthQSave.jsp";
  fm.submit(); //提交
}

function init()
{
	
	var prtSeq = fm.PrtSeq.value;
	var contno=fm.ContNo.value;
	//alert("222"+contno);
	//alert("777"+prtSeq);
	//var strSQL = "select PEItemCode,PEItemName,PEItemResult from LCPENoticeItem where PrtSeq = '"+prtSeq+"'";
	
	var sqlid9="UWManuHealthQSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName(sqlresourcename);
		mySql9.setSqlId(sqlid9);
		mySql9.addSubPara(prtSeq);
	
	//alert(strSQL);
        //turnPage.queryModal(strSQL, HealthGrid);
        
        
        	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(mySql9.getString(), 1, 0, 1);  
  //alert(turnPage.strQueryResult);
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	
    //alert("未查询到满足条件的数据！");
     return false;
  }
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  turnPage.pageLineNum = 30 ;
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = HealthGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL 
  
  
  //arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

      
       
}

