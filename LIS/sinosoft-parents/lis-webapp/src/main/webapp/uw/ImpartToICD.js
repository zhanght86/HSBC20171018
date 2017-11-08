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

  fm.action= "./ImpartToICDSave.jsp";
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

/*********************************************************************
 *  执行待新契约体检通知书的EasyQuery
 *  描述:查询显示对象是体检通知书
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function easyQueryClickSingle()
{
	// 书写SQL语句
	var strsql = "";
	var tContNo = "";

	tContNo = fm.ContNo.value;
  tInsuredNo = fm.InsureNo.value;
  var mySql=new SqlClass();
  //turnPage = new turnPageClass();			
  if(tContNo != "" && tInsuredNo != "")
  { 
//		strSQL = "select ImpartVer,ImpartCode,ImpartDetailContent,DiseaseContent,StartDate,EndDate,Prover,CurrCondition,IsProved from LCCustomerImpartDetail where CustomerNo='"+tInsuredNo+"' and ContNo='"+tContNo+"' and CustomerNoType='I'";
		 
			 mySql.setResourceName("uw.ImpartToICDSql"); //指定使用的properties文件名
			 mySql.setSqlId("ImpartToICDSql4");//指定使用的Sql的id
			 mySql.addSubPara(tInsuredNo);
			 mySql.addSubPara(tContNo);
			 strSQL = mySql.getString();
  	//查询SQL，返回结果字符串
  	turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 1, 1);  
  	
  	//判断是否查询成功
  	if (!turnPage.strQueryResult) {
  	  return "";
  	}
  	
  	//查询成功则拆分字符串，返回二维数组
  	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  	//设置初始化过的MULTILINE对象
  	turnPage.pageDisplayGrid = ImpartDetailGrid;    
  	//保存SQL语句
  	turnPage.strQuerySql = strSQL; 
  	//设置查询起始位置
  	turnPage.pageIndex = 0;  
  	//在查询结果数组中取出符合页面显示大小设置的数组
  	
  	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  	
  	//调用MULTILINE对象显示查询结果
  	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	}
  return true;
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
	  var mySql=new SqlClass();
  if(tContNo != "" && tInsuredNo != "")
  {
//	strsql = "select peitemcode,peitemname,freepe from LCPENoticeItem where 1=1"
//				 + " and ContNo = '"+ tContNo + "'"
//				 + " and PrtSeq in (select distinct prtseq from lcpenotice where contno = '"+ tContNo + "'"
//				 + " and customerno = '"+ tInsuredNo + "')";
	 mySql.setResourceName("uw.ImpartToICDSql"); //指定使用的properties文件名
	 mySql.setSqlId("ImpartToICDSql5");//指定使用的Sql的id
	 mySql.addSubPara(tContNo);
	 mySql.addSubPara(tInsuredNo);
	 strsql = mySql.getString();		 				 
	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = HealthGrid;    
          
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
	
//	var strSql = "select InsuredNo,name from lcinsured where 1=1 "
//	       + " and Prtno = '" +tPrtNo + "'"
	       var mySql=new SqlClass();
			 mySql.setResourceName("uw.ImpartToICDSql"); //指定使用的properties文件名
			 mySql.setSqlId("ImpartToICDSql6");//指定使用的Sql的id
			 mySql.addSubPara(tPrtNo);
			 var strSql = mySql.getString();			;
	//查询SQL，返回结果字符串
  
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
  
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

