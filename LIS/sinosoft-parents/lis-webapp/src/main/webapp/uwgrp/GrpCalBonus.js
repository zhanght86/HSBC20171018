//程序名称：CutBonus.js
//程序功能：分红处理
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件

var showInfo;
var k = 0;
var turnPage = new turnPageClass();

//提交，保存按钮对应操作
function submitForm()
{
	if(!vertifyForm())
		return;
	
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
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

	document.all("btnSubmit").disabled=true;
  document.all("btnForward").disabled=true;
	document.all("opertype").value = 'CALC||ALL';

  document.getElementById("fm").submit(); //提交
}

function vertifyForm()
{
	var tRiskCode = document.all('RiskCode').value;
	var tFiscalYear = document.all('FiscalYear').value;
	var tGrpContNo = document.all('GrpContNo').value;
	if(tRiskCode == null || tRiskCode == "" || tFiscalYear == null || tFiscalYear == "" || tGrpContNo == null ||tGrpContNo=="")
	{
		alert("会计年度,险种代码和团体保单号不允许为空！");
		return false;
	}

//	var tState = document.all('ComputeState').value;
//	if(tState == null || tState == "")
//	{
//		alert("分红状态信息不允许为空！");
//		return false;
//	}
	return true;
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  document.all("btnSubmit").disabled=false;
  document.all("btnForward").disabled=false;

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

function getGrppolInfo(){
   var tSelNo = PolGrid.getSelNo();
   var GrpContNo =PolGrid.getRowColData(tSelNo-1,1);
   fm.GrpContNo1.value = GrpContNo;
}

// 查询按钮
function easyQueryClick()
{
	if(!vertifyForm())
		return;
	
	// 初始化表格
	initPolGrid();
	k++;
	// 书写SQL语句
	
	var sqlid831100116="DSHomeContSql831100116";
var mySql831100116=new SqlClass();
mySql831100116.setResourceName("uwgrp.GrpCalBonusSql");//指定使用的properties文件名
mySql831100116.setSqlId(sqlid831100116);//指定使用的Sql的id
mySql831100116.addSubPara(manageCom+"%%");//指定传入的参数
mySql831100116.addSubPara(fm.FiscalYear.value);//指定传入的参数
mySql831100116.addSubPara(fm.ComputeState.value);//指定传入的参数
mySql831100116.addSubPara(fm.GrpContNo.value);//指定传入的参数
mySql831100116.addSubPara(fm.RiskCode.value);//指定传入的参数
mySql831100116.addSubPara(fm.BDate.value);//指定传入的参数
mySql831100116.addSubPara(fm.EDate.value);//指定传入的参数
var strSQL=mySql831100116.getString();

	
//	var strSQL = "select a.GrpContNo,a.GrpName,a.CValiDate,b.ActuRate,b.EnsuRateDefault,b.AssignRate "
//						 + "from LCGrpPol a,LOBonusGrpPolParm b where "+k+" = "+k+" "
//						 + "and a.GrpPolNo = b.GrpPolNo and a.AppFlag = '1' and b.computestate in ('0','3','4') "
//						 + "and a.ManageCom like '" + manageCom + "%%' "
//						 + getWherePart( 'b.FiscalYear','FiscalYear')
//						 + getWherePart( 'b.ComputeState','ComputeState')
//						 + getWherePart( 'a.GrpContNo','GrpContNo')
//						 + getWherePart( 'a.RiskCode','RiskCode')
//						 + getWherePart('a.cvalidate','BDate','>=','0')
//						 + getWherePart('a.cvalidate','EDate','<=','0')
//						 + " order by a.CValiDate";	
	
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

function viewErrLog()
{
	window.open("./ViewErrLogGrpBonusAssign.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

function calGrpPart()
{
	var tSel = PolGrid.getSelNo();
	if( tSel == 0 || tSel == null )
	{
		alert( "请先选择一条记录。" );
		return;
	}
	else
	{
		var tGrpContNo = PolGrid.getRowColData(tSel-1,1);
		var tFiscalYear = document.all('FiscalYear').value;
		var tRiskCode = document.all('RiskCode').value;
		window.open("./GrpCalPartBonusMain.jsp?GrpContNo=" + tGrpContNo + "&FiscalYear=" + tFiscalYear + "&RiskCode=" + tRiskCode,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
	}	
}