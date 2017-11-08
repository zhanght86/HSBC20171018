//        该文件中包含客户端需要处理的函数和事件
var arrDataSet 
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var PolNo;
var manageCom;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "f1print.CountPrintSql";
//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  // showSubmitFrame(mDebug);
  initPolGrid();
  document.getElementById("fm").submit(); //提交
}

//提交，保存按钮对应操作
function printPol()
{
  var i = 0;
  var flag = 0;
	flag = 0;
        
	//for( i = 0; i < PolGrid.mulLineCount; i++ ) {
		if( PolGrid.getSelNo() ) {			
			flag = 1;
			//break;
		}
	//}
	
	if( flag == 0 ) {
		alert("请先选择一条记录，再打印保单");
		return false;
	}
	var tRow = PolGrid.getSelNo() - 1;
	var tPrtNo=PolGrid.getRowColData(tRow,1);
	if(tPrtNo==""||tPrtNo==null){
		alert("请先查询！");
		return false;
	}
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  // showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  // showSubmitFrame(mDebug);     
	var vOrgTarget = fm.target;
	fm.target="f1print";
	document.getElementById("fm").submit();
	fm.target = vOrgTarget;
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = PolGrid.getSelNo();
	//alert("111" + tRow);
	if( tRow == 0 || tRow == null || arrDataSet == null )
	//{alert("111");
	return arrSelected;
	//}
	//alert("222");	
	arrSelected = new Array();
	//设置需要返回的数组
	arrSelected[0] = arrDataSet[tRow-1];
	
	return arrSelected;
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  //showInfo.close();
  if (FlagStr == "Fail" ) {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  } else {
  	easyQueryClick();
  }
}

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("在CountPrint.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 

//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作	
}           

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,0,*";
 	}
}

function returnParent()
{
    tPolNo = "00000120020110000050";
    top.location.href="./ProposalQueryDetail.jsp?PolNo="+tPolNo;
}


// 查询按钮
function easyQueryClick()
{
	if(!verifyInput()){
		return;
		}
	// 初始化表格
	initPolGrid();


	//var strManageComWhere = " AND ManageCom LIKE '" + manageCom + "%%' ";
	
	//if( fm.ManageCom.value != '' ) {
	//	strManageComWhere += " AND ManageCom LIKE '" + fm.ManageCom.value + "%%' ";
	//}
	if(fm.ManageCom.value=="")
	{
		alert("请选择管理机构！");
		return false;
	}

	
	// 书写SQL语句
	var strSQL = "";

	//strSQL =	"select 1 from(select count(distinct polno) r from lcpol where contno='"+document.all('ContNo').value+"' and appflag='1' ) where r>1";
	
		var sqlid1="CountPrintSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(document.all('ContNo').value);
	
	turnPage.strQueryResult  = easyQueryVer3(mySql1.getString());  
	//判断是否查询成功
  if (turnPage.strQueryResult) {
    return alert("保单中可能含有多条险种，请检查保单信息");
	}
	var strContNoSQL="";
	     if((document.all('ContNo').value == null || document.all('ContNo').value == "") 
    && (document.all('PrtNo').value == null || document.all('PrtNo').value == "")
    )
   {
   	   //or exists (select 'X' from lccontprinttrace r where r.contno = a.contno and r.makedate <= sysdate and r.makedate >= substr((sysdate - 30), 0, 10)))
   	   //为提升性能，只查出最近十天签单的保单或者最近十天补打的保单，十天可以调整
   	   strContNoSQL=" and (a.signdate <= sysdate and a.signdate >= substr((sysdate - 15), 0, 10)) ";
 	}
	/*
	strSQL = "SELECT PolNo,ContNo, PrtNo, RiskCode, AppntName, InsuredName,ManageCom,AgentCode FROM LCPol a where GrpPolNo like '%00000000000000000000'"
				 + " AND AppFlag = '1' "
				 //+ "and NVL ((select printcount from lccont where contno=a.contno), 0) = 0 "
				 //+ " and riskcode in (select riskcode from LMRiskApp where NotPrintPol = '1')"
				// + " AND NOT EXISTS ( SELECT PolNo FROM LCPol WHERE A.PrtNo = PrtNo AND AppFlag <> '1' ) "
				//应保全要求修改为printcount in ('0','10')
				 + "and exists(select 1 from lccont where contno=a.contno and printcount in ('0','10')) "
				 + "and exists(select 1 from lmriskapp where riskcode=a.riskcode and notprintpol='1') "
				 + "and not exists(select 1 from lcpol where prtno=a.prtno and appflag='0') "
				 + " and ManageCom like '" + fm.ManageCom.value + "%'"
				 //+ strManageComWhere
			//	 + " and VERIFYOPERATEPOPEDOM(Riskcode,Managecom,'"+ComCode+"','Pl')=1 "
				 + getWherePart( 'ContNo' )
				 + getWherePart( 'PrtNo' )
				 + getWherePart( 'AgentCode' )
				 + getWherePart( 'RiskCode' )
				 + getWherePart( 'SaleChnl' )
				 +strContNoSQL;
*/
		var sqlid2="CountPrintSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(fm.ManageCom.value);
		mySql2.addSubPara(fm.ContNo.value);
		mySql2.addSubPara(fm.PrtNo.value);
		mySql2.addSubPara(fm.AgentCode.value);
		mySql2.addSubPara(fm.RiskCode.value);
		mySql2.addSubPara(fm.SaleChnl.value);
		mySql2.addSubPara(strContNoSQL);


	turnPage.strQueryResult  = easyQueryVer3(mySql2.getString());  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    return alert("未查询到满足条件的数据信息");
	}
	
  //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);

	//查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = PolGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

  //调用MULTILINE对象显示查询结果
  
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
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

//查询代理人的方式
function queryAgent(comcode)
{
  if(document.all('AgentCode').value == "")	
  {  
		var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+comcode,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
  }
  
	if(document.all('AgentCode').value != "")	 
	{
		var cAgentCode = fm.AgentCode.value;  //保单号码	
		//var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
    
    var sqlid3="CountPrintSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(cAgentCode);
	
    
    
    var arrResult = easyExecSql(mySql3.getString());
       //alert(arrResult);
    if (arrResult != null) 
    {
			fm.AgentCode.value = arrResult[0][0];
			alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else
    {
			fm.AgentCode.value="";
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
