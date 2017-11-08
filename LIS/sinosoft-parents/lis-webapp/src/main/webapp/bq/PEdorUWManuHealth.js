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
var str_sql = "",sql_id = "", my_sql = "";
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

  fm.action= "./PEdorUWManuHealthChk.jsp";
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

// 查询单条
function easyQueryClickSingle()
{
	// 书写SQL语句
	var strsql = "";
	var tContNo = "";
	var tEdorNo = "";
	tContNo = fm.ContNo.value;
	tEdorNo = fm.EdorNo.value;
	tMissionID = fm.MissionID.value;
	tInsuredNo = fm.InsureNo.value;
	//tInsuredNo = "86110020030990000863";
  if(tContNo != "" && tInsuredNo != "")
  {
//  	strsql = "select LWMission.SubMissionID from LWMission where 1=1"
//				 + " and LWMission.MissionProp1 = '" + tEdorNo +"'"
//				 + " and LWMission.MissionProp2 = '"+ tContNo + "'"
//				 + " and LWMission.ActivityID = '0000000001'"
//				 + " and LWMission.ProcessID = '0000000000'"
//				 + " and LWMission.MissionID = '" +tMissionID +"'";
  	sql_id = "PEdorUWManuHealthSql1";
  	my_sql = new SqlClass();
  	my_sql.setResourceName("bq.PEdorUWManuHealthSql"); //指定使用的properties文件名
  	my_sql.setSqlId(sql_id);//指定使用的Sql的id
  	my_sql.addSubPara(tEdorNo);//指定传入的参数
  	my_sql.addSubPara(tContNo);//指定传入的参数
  	my_sql.addSubPara(tMissionID);//指定传入的参数
  	str_sql = my_sql.getString();
	turnPage.strQueryResult = easyQueryVer3(str_sql, 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("有体检通知书已录入,但还未打印,不容许录入新体检通知书！");
    return "";
  }
  
   turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
   fm.SubMissionID.value = turnPage.arrDataCacheSet[0][0];
   
   turnPage = new turnPageClass();			 
//	strsql = "select LPPENotice.polno,LPPENotice.insuredname,LPPENotice.pedate,LPPENotice.peaddress,LPPENotice.PEBeforeCond,LPPENotice.remark,LPPENotice.printflag from LPPENotice where 1=1"
//				 + " and LPPENotice.Contno = '"+ tContNo + "'"
//				 + " and LPPENotice.CustomerNo = '"+ tInsuredNo + "'"
//				 + " and LPPENotice.EdorNo = '" + tEdorNo +"'" ;
	sql_id = "PEdorUWManuHealthSql2";
  	my_sql = new SqlClass();
  	my_sql.setResourceName("bq.PEdorUWManuHealthSql"); //指定使用的properties文件名
  	my_sql.setSqlId(sql_id);//指定使用的Sql的id
  	my_sql.addSubPara(tContNo);//指定传入的参数
  	my_sql.addSubPara(tInsuredNo);//指定传入的参数
  	my_sql.addSubPara(tEdorNo);//指定传入的参数
  	str_sql = my_sql.getString();
  //查询SQL，返回结果字符串
  turnPage.strQueryResult = easyQueryVer3(str_sql, 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    //alert("查询失败！");
    easyQueryClickInit();
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.PolNo.value = turnPage.arrDataCacheSet[0][0];
  //fm.InsureNo.value = turnPage.arrDataCacheSet[0][1];
  //fm.EDate.value = turnPage.arrDataCacheSet[0][2];
  fm.Hospital.value = turnPage.arrDataCacheSet[0][3];
  fm.IfEmpty.value = turnPage.arrDataCacheSet[0][4];
  fm.Note.value = turnPage.arrDataCacheSet[0][5];
  fm.PrintFlag.value = turnPage.arrDataCacheSet[0][6];
  
  easyQueryClick();
 				
  }
  return true;
}


// 查询按钮
function easyQueryClick()
{
	// 书写SQL语句
	var strsql = "";
	var tPolNo = "";
	var tEdorNo = "";
	tPolNo = fm.PolNo.value;
	tEdorNo = fm.EdorNo.value;
	tInsuredNo = fm.InsureNo.value;
  if(tPolNo != "" && tInsuredNo != "")
  {
//	strsql = "select peitemcode,peitemname,freepe from LPPENoticeItem where 1=1"
//				 + " and Polno = '"+ tPolNo + "'"
//				 + " and insuredno = '"+ tInsuredNo + "'"
//				 + " and EdorNo = '" + tEdorNo+ "'";
	sql_id = "PEdorUWManuHealthSql3";
  	my_sql = new SqlClass();
  	my_sql.setResourceName("bq.PEdorUWManuHealthSql"); //指定使用的properties文件名
  	my_sql.setSqlId(sql_id);//指定使用的Sql的id
  	my_sql.addSubPara(tPolNo);//指定传入的参数
  	my_sql.addSubPara(tInsuredNo);//指定传入的参数
  	my_sql.addSubPara(tEdorNo);//指定传入的参数
  	strsql = my_sql.getString();			 
	//alert(strsql);
	
	//execEasyQuery( strSQL );
	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    //alert("尚无录入信息！");
    //easyQueryClickInit();
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
	fm.action= "./PEdorUWManuHealthQuery.jsp";
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

function initInsureNo(tContNo)
{
	var i,j,m,n;
	var returnstr;
	
//	var strSql = "select insuredNo,name from lcinsured where 1=1 "
//	       + "and contno = '" +tContNo+"'";
	sql_id = "PEdorUWManuHealthSql4";
  	my_sql = new SqlClass();
  	my_sql.setResourceName("bq.PEdorUWManuHealthSql"); //指定使用的properties文件名
  	my_sql.setSqlId(sql_id);//指定使用的Sql的id
  	my_sql.addSubPara(tContNo);//指定传入的参数
  	str_sql = my_sql.getString();
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(str_sql, 1, 1, 1);  
  
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
	
//	var strSql = "select code,codename from ldcode where 1=1 "
//	       + "and codetype = 'hospitalcodeuw'"
//	       + "and comcode = (select managecom from lccont where contno = '"+tContNo+"')";
	sql_id = "PEdorUWManuHealthSql5";
  	my_sql = new SqlClass();
  	my_sql.setResourceName("bq.PEdorUWManuHealthSql"); //指定使用的properties文件名
  	my_sql.setSqlId(sql_id);//指定使用的Sql的id
  	my_sql.addSubPara(tContNo);//指定传入的参数
  	str_sql = my_sql.getString();
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(str_sql, 1, 1, 1);  
  
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