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
  var i = 0;
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

  initGrpPolGrid();

  document.getElementById("fm").submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  document.all("assignbtn").disabled = false;
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  

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
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    alert(content);
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

// 查询按钮
function queryGrpPol()
{
		// 初始化表格
		initGrpPolGrid();
		
		
		var sqlid831144718="DSHomeContSql831144718";
var mySql831144718=new SqlClass();
mySql831144718.setResourceName("uwgrp.GrpCutPersonBonusSql");//指定使用的properties文件名
mySql831144718.setSqlId(sqlid831144718);//指定使用的Sql的id
mySql831144718.addSubPara(document.all("FiscalYear").value);//指定传入的参数
mySql831144718.addSubPara(document.all("GrpContNo").value);//指定传入的参数
mySql831144718.addSubPara(fm.FiscalYear.value);//指定传入的参数
mySql831144718.addSubPara(fm.ContNo.value);//指定传入的参数
var strSQL=mySql831144718.getString();

		
		// 书写SQL语句
//		var strSQL = "select distinct(b.ContNo),b.GrpContNo,a.FiscalYear,a.InsuAccNo,(select sum(bonusmoney) from "
//		            +"lobonusgrppol where polno =a.polno and bonusflag='0' and fiscalyear='"+document.all("FiscalYear").value+"'),"
//		            +"a.SGetDate from LOBonusGrpPol a,lcpol b "
//  					+ "where a.polno = b.polno and exists (select 1 from lcgrppol where appflag = '1' and grppolno = a.grppolno and GrpContNo = '" + document.all("GrpContNo").value + "') and bonusflag = '0' "
//					+ getWherePart('a.FiscalYear','FiscalYear')
// 					+ getWherePart('b.ContNo','ContNo');
					
		
		//alert(strSQL);
		//查询SQL，返回结果字符串
		turnPage.queryModal(strSQL, GrpPolGrid); 
}

function assignPerson()
{
		var tSel = GrpPolGrid.getSelNo();
		if( tSel != null && tSel > 0)
		{
			document.all('ContNo').value = GrpPolGrid.getRowColData(tSel-1,1);
		}

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
		
		//showSubmitFrame(mDebug);
		document.all("assignbtn").disabled=true;
		document.getElementById("fm").submit(); //提交
}

/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}

function viewErrLog()
{
	window.open("./ViewErrLogGrpBonusAssign.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");		
}