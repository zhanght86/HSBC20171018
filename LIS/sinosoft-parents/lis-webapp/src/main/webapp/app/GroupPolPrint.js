var showInfo;
var mDebug="0";
var manageCom;
var turnPage = new turnPageClass();
var arrDataSet;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "app.GroupPolPrintSql";

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

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug){
	if(cDebug=="1"){
		parent.fraMain.rows = "0,0,0,0,*";
	}
	else{
		parent.fraMain.rows = "0,0,0,0,*";
	}
	parent.fraMain.rows = "0,0,0,0,*";
}

// 数据返回父窗口
function returnParent()
{
	var arrReturn = new Array();
	var tSel = GrpContGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		try
		{
			arrReturn = getQueryResult();
			top.opener.afterQuery( arrReturn );
		}
		catch(ex)
		{
			alert( "没有发现父窗口的afterQuery接口。" + ex );
		}
		top.close();
	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = GrpContGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrGrid == null )
		return arrSelected;
	arrSelected = new Array();
	//设置需要返回的数组
	arrSelected[0] = arrGrid[tRow-1];
	return arrSelected;
}

// 查询按钮
function easyQueryClick(){
	// 初始化表格
	initGrpContGrid();
	
	if(document.all('ManageCom').value=="" || document.all('ManageCom').value==null )
	{
		alert("管理机构不能为空");
		return;
	}	
	//加入管理机构查询限制
	var strManageComWhere = " AND ManageCom LIKE '" + manageCom + "%%' ";
	if( fm.ManageCom.value != '' ) {
		strManageComWhere += " AND ManageCom LIKE '" + fm.ManageCom.value + "%%' ";
	}
	if( fm.BranchGroup.value != '' ) {
		strManageComWhere += " AND AgentGroup IN ( SELECT AgentGroup FROM LABranchGroup WHERE BranchAttr LIKE '" + fm.BranchGroup.value + "%%') ";
	}
	
	var strRiskCodeWhere = " and 1=1 ";
	//alert(document.all('RiskCode').value );
	if(document.all('RiskCode').value != null && document.all('RiskCode').value != "" )
	{
		strRiskCodeWhere +=  " and EXISTS (select 1 from lcgrppol where grpcontno = A.grpcontno  and riskcode= '"+document.all('RiskCode').value +"') "
	}
	
		var strContNoSQL="";
	//	if((document.all('GrpContNo').value == null || document.all('GrpContNo').value == "") 团险暂时不加这样的控制
  //  && (document.all('PrtNo').value == null || document.all('PrtNo').value == "")
  //  )
  // {
  // 	
  // 	   strContNoSQL=" and a.signdate<=sysdate and a.signdate>=(sysdate-30)";
  // 	}
   	//	alert(document.all('GrpContNo').value);
	//alert(strContNoSQL);
	// 书写SQL语句
	var strSQL = "";
/*
	strSQL = "SELECT GrpContNo,PrtNo,Prem,GrpName,(select count(1) from lcinsured where grpcontno = A.grpcontno),signdate FROM LCGrpCont A"
		+ " WHERE AppFlag in ('1','4') and ( PrintCount < 1 OR PrintCount =10 )"
		 + " and  EXISTS (select 1 from lcgrppol where grpcontno = A.grpcontno  and riskcode in (select riskcode from LMRiskApp where NotPrintPol = '0'))"
//				 + " and ContNo='" + contNo + "' "
		+ getWherePart( 'PrtNo' )
		+ getWherePart( 'GrpContNo' )
		+ getWherePart( 'AgentCode' )
//		+ getWherePart( 'CValiDate' )
		+ getWherePart( 'SaleChnl' )
//				 + getWherePart( 'RiskCode' )
		+ strManageComWhere
		+ strRiskCodeWhere
//		+ strContNoSQL
 	  + " AND NOT EXISTS ( SELECT GrpPolNo FROM LCGrpPol WHERE A.PrtNo = PrtNo AND AppFlag = '0' ) "
		+ " order by CValiDate asc,signdate asc";
		//document.all('BranchGroup').value = strSQL;
*/
var sqlid5="GroupPolPrintSql5";
var mySql5=new SqlClass();
mySql5.setResourceName(sqlresourcename);
mySql5.setSqlId(sqlid5);
mySql5.addSubPara(fm.PrtNo.value);
mySql5.addSubPara(fm.GrpContNo.value);	
mySql5.addSubPara(fm.AgentCode.value);	
mySql5.addSubPara(fm.SaleChnl.value);		
mySql5.addSubPara(strManageComWhere);	
mySql5.addSubPara(strRiskCodeWhere);		
	
var strSQL = mySql5.getString();


	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//判断是否查询成功
	if (!turnPage.strQueryResult) {
		alert("未查询到满足条件的数据！");
		return false;
	}
	//设置查询起始位置
	turnPage.pageIndex = 0;
	//在查询结果数组中取出符合页面显示大小设置的数组
	turnPage.pageLineNum = 10 ;
	//查询成功则拆分字符串，返回二维数组
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//设置初始化过的MULTILINE对象
	turnPage.pageDisplayGrid = GrpContGrid;
	//保存SQL语句
	turnPage.strQuerySql     = strSQL ;
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
	//调用MULTILINE对象显示查询结果
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}


//提交，保存按钮对应操作
function printGroupPol()
{
	
		if(document.all('ManageCom').value==86)
     {
    	 alert("总公司不可以进行团单打印，请返回");
    	 return false;
     }
	var i = 0;
	var flag = 0;
	flag = 0;
	//判定是否有选择打印数据
	for( i = 0; i < GrpContGrid.mulLineCount; i++ )
	{
		if( GrpContGrid.getChkNo(i) == true )
		{
			flag = 1;
			break;
		}
	}
	//如果没有打印数据，提示用户选择
	if( flag == 0 )
	{
		alert("请先选择一条记录，再打印保单");
		return false;
	}
	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	//disable打印按钮，防止用户重复提交
	document.all("printButton").disabled=true;
	document.getElementById("fm").submit();
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	//无论打印结果如何，都重新激活打印按钮
	document.all("printButton").disabled=false;
	if (FlagStr == "Fail" )
	{
		//如果失败，则返回错误信息
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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
		//如果提交成功，则执行查询操作
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		easyQueryClick();
	}
	//document.all("divErrorMInfo").style.display = "";
}

function queryBranch()
{
	showInfo = window.open("../certify/AgentTrussQuery.html","",sFeatures);
}

//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery(arrResult)
{
	if(arrResult!=null)
	{
		fm.BranchGroup.value = arrResult[0][3];
	}
}

// 查询按钮
function easyQueryErrClick()
{

	// 初始化表格
	initErrorGrid();
	//判断其是否是家庭单或者多主险
	
 /*
  var strSQL="select contno,Errmsg,makedate,maketime from LDSysErrLog where 1=1 "
  + getWherePart( 'GrpContNo','ErrorContNo' )
  + getWherePart( 'MakeDate' );
	*/
var sqlid7="GroupPolPrintSql7";
var mySql7=new SqlClass();
mySql7.setResourceName(sqlresourcename);
mySql7.setSqlId(sqlid7);
mySql7.addSubPara(fm.ErrorContNo.value)	
mySql7.addSubPara(fm.MakeDate.value)
	
	turnPage.strQueryResult  = easyQueryVer3(mySql7.getString(), 1, 0, 1);

	//判断是否查询成功
	if (!turnPage.strQueryResult)
	{
		alert("未查询到满足条件的数据！");
		return ;
	}

	//设置查询起始位置
	turnPage.pageIndex = 0;
	//在查询结果数组中取出符合页面显示大小设置的数组
	turnPage.pageLineNum = 30 ;
	//查询成功则拆分字符串，返回二维数组
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//设置初始化过的MULTILINE对象
	turnPage.pageDisplayGrid = ErrorGrid;
	//保存SQL语句
	turnPage.strQuerySql = strSQL ;
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
	//调用MULTILINE对象显示查询结果
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}