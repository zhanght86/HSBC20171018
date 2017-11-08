//               该文件中包含客户端需要处理的函数和事件
var arrDataSet;
var showInfo;
var mDebug="0";
var manageCom;
var turnPage = new turnPageClass();       
var sqlresourcename = "app.ReGroupPolPrintSql";
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
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,0,*";
 	}
	
	parent.fraMain.rows = "0,0,0,0,*";
}

// 数据返回父窗口
function returnParent()
{
	var arrReturn = new Array();
	var tSel = GrpPolGrid.getSelNo();
	
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

//查询代理人的方式
function queryAgent(comcode)
{
  if(document.all('AgentCode').value == "")	
  {  
		var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+comcode,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
  }
  
	if(document.all('AgentCode').value != "")	 
	{
	var cAgentCode = fm.AgentCode.value;  //代理人编码	
	//var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
    
var sqlid1="ReGroupPolPrintSql1";
var mySql1=new SqlClass();
mySql1.setResourceName(sqlresourcename);
mySql1.setSqlId(sqlid1);
mySql1.addSubPara(cAgentCode);
    
    var arrResult = easyExecSql(mySql1.getString());

  //  alert(arrResult);
    if (arrResult != null) 
    {
			alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else
    {
			
			alert("编码为:["+document.all('AgentCode').value+"]的代理人不存在，请确认!");
    }
	}	
}

//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
//  	fm.QAgentGroup.value = arrResult[0][1];
  }
}
function queryAgent2()
{
	if(document.all('AgentCode').value != "" && document.all('AgentCode').value.length==10 )	 {
	var cAgentCode = fm.AgentCode.value; 
	//var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";
 
var sqlid2="ReGroupPolPrintSql2";
var mySql2=new SqlClass();
mySql2.setResourceName(sqlresourcename);
mySql2.setSqlId(sqlid2);
mySql2.addSubPara(cAgentCode);
    
 
    var arrResult = easyExecSql(mySql2.getString());
       //alert(arrResult);
    if (arrResult != null) {
    
      alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else{
 
     alert("编码为:["+document.all('AgentCode').value+"]的代理人不存在，请确认!");
     }
	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = GrpPolGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrGrid == null )
		return arrSelected;
	arrSelected = new Array();
	//设置需要返回的数组
	arrSelected[0] = arrGrid[tRow-1];
	return arrSelected;
}

// 查询按钮
function easyQueryClick()
{
	// 初始化表格
	initGrpPolGrid();
	
	var strManageComWhere = " AND ManageCom LIKE '" + manageCom + "%' ";
	
	if( fm.ManageCom.value != '' ) {
		strManageComWhere += " AND ManageCom LIKE '" + fm.ManageCom.value + "%' ";
	}
	var strRiskCodeWhere=" and 1=1 ";
	if( fm.RiskCode.value != '' ) {
		strRiskCodeWhere += " AND  exists (select RiskCode from lcgrppol a where   a.GrpContNo=b.GrpContNo  and  riskcode='"+fm.RiskCode.value+"'  and  a.RiskCode in  ( select RiskCode from LMRiskApp where SubRiskFlag = 'M' and NotPrintPol = '0'))";                                                   
	}
	var strRiskVersionWhere=" and 2=2 ";
	//alert( fm.RiskVersion.value);
	if( fm.RiskVersion.value != '' ) {
		strRiskVersionWhere += " AND  exists  (select 'X' from lcgrppol a   where a.GrpContNo=b.GrpContNo and  a.riskversion='" + fm.RiskVersion.value + "' ) ";
	}
	var strContNoSQL="";
	//	if((document.all('GrpContNo').value == null || document.all('GrpContNo').value == "") 
  //  && (document.all('PrtNo').value == null || document.all('PrtNo').value == "")
  //  )
  // {
  // 	
  // 	   strContNoSQL=" and b.signdate<=sysdate and b.signdate>=(sysdate-30)";
  // 	}
  
	
	// 书写SQL语句
	var strSQL = "";
	var addStr3 = " and 3=3 ";
	strSQL = "select GrpContNo,PrtNo,(select min(RiskCode) from  lcgrppol a   where a.GrpContNo=b.GrpContNo and a.riskcode in ( select riskcode from LMRiskApp where SubRiskFlag = 'M' )),GrpName,CValiDate from LCGrpCont  b where 1=1 "
				 + "and AppFlag in ('1','4') and PrintCount = 1 "
				 + " and  EXISTS (select 1 from lcgrppol where grpcontno = b.grpcontno  and riskcode in (select riskcode from LMRiskApp where NotPrintPol = '0'))"
				 + getWherePart( 'GrpContNo' )
				 + getWherePart( 'PrtNo' )
				 + getWherePart( 'AgentCode' )
				 + getWherePart( 'AgentGroup' )
				 + strRiskCodeWhere
				 + strRiskVersionWhere
				 + getWherePart( 'GrpName' )
				 + strContNoSQL
				 + strManageComWhere;
if( fm.GrpName.value != '' ) {
//	strSQL +=" AND GrpName LIKE '%" + fm.GrpName.value + "%' ";
	addStr3 =" AND GrpName LIKE '%" + fm.GrpName.value + "%' ";
}
//strSQL +="order by ManageCom,AgentGroup,AgentCode";

var sqlid7="ReGroupPolPrintSql7";
var mySql7=new SqlClass();
mySql7.setResourceName(sqlresourcename);
mySql7.setSqlId(sqlid7);
mySql7.addSubPara(fm.GrpContNo.value);
mySql7.addSubPara(fm.PrtNo.value);
mySql7.addSubPara(fm.AgentCode.value);
mySql7.addSubPara(fm.AgentGroup.value);
mySql7.addSubPara(strRiskCodeWhere);
mySql7.addSubPara(strRiskVersionWhere);
mySql7.addSubPara(fm.GrpName.value);
mySql7.addSubPara(strContNoSQL);
mySql7.addSubPara(strManageComWhere);
mySql7.addSubPara(addStr3);
	strSQL = mySql7.getString();		 
			 
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

	//判断是否查询成功
	if (!turnPage.strQueryResult)
	{
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
	turnPage.pageDisplayGrid = GrpPolGrid;
	//保存SQL语句
	turnPage.strQuerySql = strSQL ;
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
	//调用MULTILINE对象显示查询结果
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "没有找到相关的数据!" );
	else
	{
		// 初始化表格
		initGrpPolGrid();
		//HZM 到此修改
		GrpPolGrid.recordNo = (currBlockIndex - 1) * MAXMEMORYPAGES * MAXSCREENLINES + (currPageIndex - 1) * MAXSCREENLINES;
		GrpPolGrid.loadMulLine(GrpPolGrid.arraySave);		
		//HZM 到此修改		
		arrGrid = arrResult;
		// 显示查询结果
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				GrpPolGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
		
		GrpPolGrid.delBlankLine();
	} // end of if
}


//提交，保存按钮对应操作
function printGroupPol()
{
  var i = 0;
  var flag = 0;
	flag = 0;

	for( i = 0; i < GrpPolGrid.mulLineCount; i++ ) {
		if( GrpPolGrid.getChkNo(i) == true ) {
			flag = 1;
			break;
		}
	}
	
	if( flag == 0 ) {
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
  showSubmitFrame(mDebug);


	document.getElementById("fm").submit();
	
	if(fmSave.fmtransact.value == "")
	{fmSave.fmtransact.value = "PRINT";
	document.getElementById("fmSave").submit();}
else{
	document.getElementById("fmSave").submit();
	
	}
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=350;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
  }
  else
  { 

    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=350;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
	
  }
  easyQueryClick();
}
function dataConfirm(rr)
{
	if(rr.checked == true)
     {if( confirm("是否生成新数据？生成新数据点击“确定”，否则点“取消”。"))
	         {
	         	rr.checked = true;
	         	fmSave.fmtransact.value = "CONFIRM";	         
	         }
	    else {
		       rr.checked = false;
		       fmSave.fmtransact.value = "PRINT";		       
	    }
	  }
	else{
	    rr.checked = false;
	    fmSave.fmtransact.value = "PRINT";		  
	    }  
}

function initRiskVersion()
{	
	//var  tSQL="select distinct Riskver from LMRiskApp  where   RiskProp in ('G','A','B','D') order by riskver";

var sqlid9="ReGroupPolPrintSql9";
var mySql9=new SqlClass();
mySql9.setResourceName(sqlresourcename);
mySql9.setSqlId(sqlid9);
mySql9.addSubPara("1");

	  var strResult = easyQueryVer3(mySql9.getString());
  //alert(strResult);
  if (strResult) {
    document.all("RiskVersion").CodeData = strResult;    
  }
 }