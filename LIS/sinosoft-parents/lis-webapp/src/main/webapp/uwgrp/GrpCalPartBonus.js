var showInfo;
var turnPage = new turnPageClass(); 

// 查询按钮
function easyQueryClick()
{
	// 初始化表格
	initBonusGrid();
	
	var fiscalYear = document.all('FiscalYear').value;
	var grppolNo = document.all('GrpContNo').value;
	var addSql = "";
	if(document.all('ComputeState').value != null && document.all('ComputeState').value != "")
		addSql = " and exists(select 1 from lobonusgrppolparm where grppolno = a.grppolno and fiscalyear = "+fiscalYear+" and computestate = '"+document.all('ComputeState').value+"') ";
	
	var sqlid831102106="DSHomeContSql831102106";
var mySql831102106=new SqlClass();
mySql831102106.setResourceName("uwgrp.GrpCalPartBonusSql");//指定使用的properties文件名
mySql831102106.setSqlId(sqlid831102106);//指定使用的Sql的id
mySql831102106.addSubPara(grppolNo);//指定传入的参数
mySql831102106.addSubPara(fiscalYear);//指定传入的参数
mySql831102106.addSubPara(grppolNo);//指定传入的参数
mySql831102106.addSubPara(fiscalYear);//指定传入的参数
mySql831102106.addSubPara(grppolNo);//指定传入的参数
//mySql831102106.addSubPara(fiscalYear);//指定传入的参数
//mySql831102106.addSubPara(document.all('ComputeState').value);//指定传入的参数
mySql831102106.addSubPara(addSql);
mySql831102106.addSubPara(fm.PolNo.value);//指定传入的参数
mySql831102106.addSubPara(fm.BDate.value);//指定传入的参数
mySql831102106.addSubPara(fm.EDate.value);//指定传入的参数
var strSQL=mySql831102106.getString();
	
	
	// 书写SQL语句
//	var strSQL = "select grppolno,polno,cvalidate,"
//						 + "(select assignrate from lobonusgrppolparm where grppolno = '"+ grppolNo +"' and fiscalyear = '"+fiscalYear+"'),"
//						 + "decode((select max(bonusflag) from lobonusgrppol where grppolno = '"+ grppolNo +"' and fiscalyear = '"+fiscalYear+"' and polno = a.polno),null,'0','0','3','1','4') "
//						 + "from lcpol a "
//						 + "where grppolno = '" + grppolNo + "' "
//						 + addSql
//						 + getWherePart('PolNo')
//						 + getWherePart('cvalidate','BDate','>=','0')
//						 + getWherePart('cvalidate','EDate','<=','0')
//						 + " order by polno "
//						 ;
	//alert(strSQL);
	
	//查询SQL，返回结果字符串
	turnPage.queryModal(strSQL, BonusGrid);    
}

//提交，保存按钮对应操作
function submitForm()
{
	var tSel = BonusGrid.getSelNo();
	if( tSel == 0 || tSel == null )
	{
		alert( "请先选择一条记录。" );
		return;
	}
	
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
	document.all('PolNo').value=BonusGrid.getRowColData(tSel-1,2);
  document.getElementById("fm").submit(); //提交
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  document.all("btnSubmit").disabled=false;

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
