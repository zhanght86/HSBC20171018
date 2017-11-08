 //程序名称：CutBonus.js
//程序功能：分红处理
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var k = 0;
var turnPage = new turnPageClass();

//提交，保存按钮对应操作
function submitForm()
{
	if(vertifyForm("UPDATE") == false)
		return ;
	var fiscalyearEnd = document.all('FiscalYear').value + "-12-31";
	//if(getCurrentDate() <= fiscalyearEnd)
	//{
	//	alert("未到会计年度末，不允许进行计算！");
	//	return;
	//}
	
	var State = "";
	var tSel = PolGrid.getSelNo();
	if( tSel == 0 || tSel == null )
	{
		alert( "请先选择一条记录。" );
		return ;
	}
	else
    State = PolGrid.getRowColData(tSel - 1,7);

	if (State != "0")
	{
		alert("该状态下的参数设置不允许修改！");
		return;
	}
		
	document.all("update").disabled=true;
	document.all("insAll").disabled=true;
	document.all("updAll").disabled=true;
	document.all("fmtransact").value="UPDATE";
	document.all('GrpContNo').value = PolGrid.getRowColData(tSel - 1,1);
	//return;
  var showStr="正在提交数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
  document.getElementById("fm").submit(); //提交
}

//按条件保存团单设置
function saveAll()
{
	var state = document.all('State').value;
	if(state != null && state != "")
	{
		alert("分红状态不符，不能进行保存操作！");
		return;
	}
	
	if(vertifyForm("INSERT") == false)
		return ;
	
	document.all("update").disabled=true;
	document.all("insAll").disabled=true;
	document.all("updAll").disabled=true;
	document.all("fmtransact").value="INSERT||ALL";

  var showStr="正在提交数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
  document.getElementById("fm").submit(); //提交
}

//按条件保存团单设置
function updateAll()
{
	var state = document.all('State').value;
	if(state != "0")
	{
		alert("分红状态不符，不能进行保存操作！");
		return;
	}

	if(vertifyForm("UPDATE") == false)
		return ;
	
	document.all("update").disabled=true;
	document.all("insAll").disabled=true;
	document.all("updAll").disabled=true;
	document.all("fmtransact").value="UPDATE||ALL";

  var showStr="正在修改数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
  document.getElementById("fm").submit(); //提交
}

function deleteParm()
{
	if(document.all('State').value!='0')
	{
		alert("不处于待计算状态，不能删除！");	
		return ;
	}
	
  var i = 0;
  var showStr="正在传送数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
 
	document.all("update").disabled=true;
	document.all("insAll").disabled=true;
	document.all("updAll").disabled=true;
  document.all('fmtransact').value='DELETE'; //提交
  document.getElementById("fm").submit();
}

function vertifyForm(Operate)
{
	var acturate = document.all('ActuRate').value;
	var ensurate = document.all('EnsuRateDefault').value;
	var assignrate = document.all('AssignRate').value;
	
	if(acturate == null || acturate == "")
	{
		alert("请录入实际投资收益率！");
		return false;
	}

	if(ensurate == null || ensurate == "")
	{
		alert("请录入默认保证年收益率！");
		return false;
	}

	if(Operate == "UPDATE" && (assignrate == null || assignrate == ""))
	{
		alert("请录入分红比例！");
		return false;
	}

	return true;
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  document.all("update").disabled=false;
  document.all("insAll").disabled=false;
	document.all("updAll").disabled=false;
  
  if (FlagStr == "Fail" )
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  else
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  

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
//  easyQueryClick();
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
	var fiscalYear = document.all('FiscalYear').value;
	if(fiscalYear == null || fiscalYear == "")
	{
		alert("请录入会计年度！");
		return;
	}

	initPolGrid();
	
	var addSql = "";
	var strSQL = "";
	var endyear = fiscalYear + "-12-31";
	if(document.all('State').value != null && document.all('State').value != ""){
		var sqlid831104344="DSHomeContSql831104344";
var mySql831104344=new SqlClass();
mySql831104344.setResourceName("uwgrp.GrpCalBonusPrePareSql");//指定使用的properties文件名
mySql831104344.setSqlId(sqlid831104344);//指定使用的Sql的id
mySql831104344.addSubPara(fiscalYear);//指定传入的参数
mySql831104344.addSubPara(fiscalYear);//指定传入的参数
mySql831104344.addSubPara(fiscalYear);//指定传入的参数
mySql831104344.addSubPara(manageCom);//指定传入的参数
mySql831104344.addSubPara(endyear);//指定传入的参数
mySql831104344.addSubPara(fiscalYear);//指定传入的参数
mySql831104344.addSubPara(document.all('State').value);//指定传入的参数
mySql831104344.addSubPara(fm.GrpContNo.value);//指定传入的参数
mySql831104344.addSubPara(fm.RiskCode.value);//指定传入的参数
mySql831104344.addSubPara(fm.BDate.value);//指定传入的参数
mySql831104344.addSubPara(fm.EDate.value);//指定传入的参数
strSQL=mySql831104344.getString();
	}
		
	else{
		var sqlid831105228="DSHomeContSql831105228";
var mySql831105228=new SqlClass();
mySql831105228.setResourceName("uwgrp.GrpCalBonusPrePareSql");//指定使用的properties文件名
mySql831105228.setSqlId(sqlid831105228);//指定使用的Sql的id
mySql831105228.addSubPara(fiscalYear);//指定传入的参数
mySql831105228.addSubPara(fiscalYear);//指定传入的参数
mySql831105228.addSubPara(fiscalYear);//指定传入的参数
mySql831105228.addSubPara(manageCom);//指定传入的参数
mySql831105228.addSubPara(endyear);//指定传入的参数
mySql831105228.addSubPara(fiscalYear);//指定传入的参数
mySql831105228.addSubPara(fm.GrpContNo.value);//指定传入的参数
mySql831105228.addSubPara(fm.RiskCode.value);//指定传入的参数
mySql831105228.addSubPara(fm.BDate.value);//指定传入的参数
mySql831105228.addSubPara(fm.EDate.value);//指定传入的参数
strSQL=mySql831105228.getString();
	}
	k++;
	
	
	
	
//	if(document.all('State').value != null && document.all('State').value != "")
//		addSql = " and exists(select 1 from lobonusgrppolparm where grppolno = a.grppolno and fiscalyear = "+fiscalYear+" and computestate = '"+document.all('State').value+"') ";
//	else
//		addSql = " and not exists(select 1 from lobonusgrppolparm where grppolno = a.grppolno and fiscalyear = "+fiscalYear+") ";
//		
//	k++;
//	// 书写SQL语句
//	var endyear = fiscalYear + "-12-31";
//	var strSQL = "select GrpContNo,CValiDate,GrpName,"
//						 + "nvl((select acturate from lobonusgrppolparm where grppolno = a.grppolno and FiscalYear = '" + fiscalYear + "'),0), "
//						 + "nvl((select ensuratedefault from lobonusgrppolparm where grppolno = a.grppolno and FiscalYear = '" + fiscalYear + "'),0), "
//						 + "bonusrate, "
//						 + "(select computestate from lobonusgrppolparm where grppolno = a.grppolno and FiscalYear = '" + fiscalYear + "') "
//						 + "from LCGrpPol a where "+k+" = "+k+" "
//						 + "and AppFlag = '1' and ManageCom like '" + manageCom + "%%' "
//						 + "and CValiDate <='" + endyear + "' " //and (PayEndDate >='" + endyear + "' or PayEndDate is null) "
//						 + " and exists (select 1 from lcinsureacc where grpcontno = a.grpcontno and state not in ('1','4'))"
//						 + getWherePart( 'grpcontno','GrpContNo')
//						 + getWherePart( 'riskcode','RiskCode')
//						 + getWherePart('cvalidate','BDate','>=','0')
//						 + getWherePart('cvalidate','EDate','<=','0')
//						 + addSql 
//						 + "order by GrpContNo,CValiDate";	

	//alert(strSQL);
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
    alert("没有找到相关数据！");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = PolGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;
}

function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "没有找到相关的数据!" );
	else
	{
		// 初始化表格
		initPolGrid();
		
		arrGrid = arrResult;
		// 显示查询结果
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				PolGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}