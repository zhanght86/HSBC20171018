//               该文件中包含客户端需要处理的函数和事件

//Creator :范昕	
//Date :2008-08-18

var showInfo;
var mDebug="0";
var arrDataSet;
var turnPage = new turnPageClass();


function ToExcel()
{
	if(FIRuleDealErrLogGrid.mulLineCount=="0"){
		alert("没有查询到数据");
		return false;
	} 	 	
	fm.action="./FinRuleDealCheckExcel.jsp";
	fm.target=".";
	document.getElementById("fm").submit(); //提交
}

//初始化页面
function queryFIRuleDealErrLogGrid()
{
	try
	{
		initFIRuleDealErrLogGrid();
		var strSQL = ""; 
		
  	   
  	    var mySql1=new SqlClass();
			mySql1.setResourceName("fininterface.FIRuleDealErrLogInputSql"); //指定使用的properties文件名
			mySql1.setSqlId("FIRuleDealErrLogInputSql1");//指定使用的Sql的id
			mySql1.addSubPara(RuleDealBatchNo);//指定传入的参数
			mySql1.addSubPara(DataSourceBatchNo);//指定传入的参数
			mySql1.addSubPara(RulePlanID);//指定传入的参数
			strSQL= mySql1.getString();
  	    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  	    strSQL = "select RuleDealBatchNo,DataSourceBatchNo,CertificateID || '-' ||(select c.certificatename from ficertificatetypedef c where c.certificateid = firuledealerrlog.certificateid),ErrInfo,(select d.detailindexname  from ficertificatetypedef d where d.certificateid = firuledealerrlog.certificateid) || '-' || businessno,RulePlanID || '-' || (select a.rulesplanname from firuleplandef a where a.rulesplanid = firuledealerrlog.ruleplanid),RuleID || '-' || (select b.rulename from firuledef b where b.ruleid = firuledealerrlog.ruleid),(case DealState when '1' then '处理完毕' else '未处理' end), ErrSerialNo from FIRuleDealErrLog where ";
  	    strSQL = strSQL + " RuleDealBatchNo ='"+RuleDealBatchNo+"' and DataSourceBatchNo ='"+DataSourceBatchNo+"' and ruleplanid = '" + RulePlanID + "' ";
  	    fm.ExpSQL.value = strSQL;
		if (!turnPage.strQueryResult)
		{
			return false;
		}
		//设置查询起始位置
		turnPage.pageIndex = 0;
		//在查询结果数组中取出符合页面显示大小设置的数组
		turnPage.pageLineNum = 30 ;
		//查询成功则拆分字符串，返回二维数组
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//设置初始化过的MULTILINE对象
		turnPage.pageDisplayGrid = FIRuleDealErrLogGrid;
		//保存SQL语句
		turnPage.strQuerySql = strSQL ;
		arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
		//调用MULTILINE对象显示查询结果
		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

  }
  
  	catch(Ex)
	{
		alert(Ex.message);
	}
	 
  
}


/*function queryFIRuleDealErrLogGrid()
{
	// 初始化表格
	initFIRuleDealErrLogGrid();
  var strSQL = ""; 
		//strSQL = "select RuleDealBatchNo,DataSourceBatchNo,ClassType,ErrInfo,KeyUnionValue,RulePlanID,RuleID,DealState from FIRuleDealErrLog where ";
		//strSQL = strSQL + " RuleDealBatchNo ='"+RuleDealBatchNo+"' and DataSourceBatchNo ='"+DataSourceBatchNo+"' ";
		//alert(strSQL);
		strSQL = "select 1,1,1,1,1,1,1,1 from dual ";	
  	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有查询到数据！");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = FIRuleDealErrLogGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;
}*/

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
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}


function ReturnData()
{
	var tSel = FIRuleDealErrLogGrid.getSelNo();	
		
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
	    //alert("dddd" + parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel-1,3));
	    document.all('CertificateID').value =  parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel - 1,3); //arrResult[0][1];
		document.all('ErrInfo').value = parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel - 1,4);//arrResult[0][2];
		document.all('businessno').value = parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel - 1,5); //arrResult[0][3];
		document.all('RulePlanID').value = parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel - 1,6);//arrResult[0][4];
		document.all('RuleID').value = parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel - 1,7);//arrResult[0][5];
		document.all('DealState').value = parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel - 1,8);//arrResult[0][6];
		
	}
}

function DealErrdata1(){

   alert( "该数据错误忽略成功，可以进行下步凭证处理!" );

}

function DealErrdata(){

	
	var tSel = FIRuleDealErrLogGrid.getSelNo();	

	if( tSel == 0 || tSel == null ){
		alert( "请先选择一条记录，再点击返回按钮。" );
	}
	else
	{
	
	   var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
       var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
       var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	
   	  document.all('DelErrSerialNo').value = parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel - 1,9);
   	  fm.action="./FIRuleDealErrLogSave.jsp";
   	  fm.target="fraSubmit";
   	  document.getElementById("fm").submit();
    }

}

function getQueryResult()
{
	var arrSelected = null;
	tRow = FIRuleDealErrLogGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrDataSet == null )
	  return arrSelected;
	
	arrSelected = new Array();
	
	var strSQL = "";
	/**
	strSQL = "select CertificateID,ErrInfo,businessno,RulePlanID,RuleID,DealState,ErrSerialNo from FICostDataBaseDef where 1=1 "
	       + "and RuleDealBatchNo='"+FIRuleDealErrLogGrid.getRowColData(tRow-1,1)+"' and DataSourceBatchNo='"+FIRuleDealErrLogGrid.getRowColData(tRow-1,2)+"' " ; 
	 */
	var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.FIRuleDealErrLogInputSql"); //指定使用的properties文件名
		mySql2.setSqlId("FIRuleDealErrLogInputSql2");//指定使用的Sql的id
		mySql2.addSubPara(FIRuleDealErrLogGrid.getRowColData(tRow-1,1));//指定传入的参数
		mySql2.addSubPara(FIRuleDealErrLogGrid.getRowColData(tRow-1,2));//指定传入的参数
		strSQL= mySql2.getString();    
turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("查询失败！");
    return false;
    }
//查询成功则拆分字符串，返回二维数组
  arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	
	return arrSelected;
}

function afterQuery( arrQueryResult )
{
	var arrResult = new Array();

	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		document.all('CertificateID').value =  parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel - 1,1); //arrResult[0][1];
		document.all('ErrInfo').value = parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel - 1,2);//arrResult[0][2];
		document.all('businessno').value = parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel - 1,3); //arrResult[0][3];
		document.all('RulePlanID').value = parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel - 1,4);//arrResult[0][4];
		document.all('RuleID').value = parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel - 1,5);//arrResult[0][5];
		document.all('DealState').value = parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel - 1,6);//arrResult[0][6];		
	}
}
function initFIRuleDealErrLogQuery()
{
	try
	{
  	var strSQL = "";
  	/**
  	strSQL = "select RuleDealBatchNo,DataSourceBatchNo, CertificateID || '-' ||(select c.certificatename from ficertificatetypedef c where c.certificateid = firuledealerrlog.certificateid),ErrInfo,(select d.detailindexname  from ficertificatetypedef d where d.certificateid = firuledealerrlog.certificateid) || '-' || businessno,RulePlanID || '-' || (select a.rulesplanname from firuleplandef a where a.rulesplanid = firuledealerrlog.ruleplanid),RuleID || '-' || (select b.rulename from firuledef b where b.ruleid = firuledealerrlog.ruleid),(case DealState when '1' then '处理完毕' else '未处理' end),ErrSerialNo from FIRuleDealErrLog where ";
		strSQL = strSQL + " RuleDealBatchNo ='"+RuleDealBatchNo+"' and DataSourceBatchNo ='"+DataSourceBatchNo+"' ";
  	*/
  	//alert(strSQL);
  	var mySql3=new SqlClass();
		mySql3.setResourceName("fininterface.FIRuleDealErrLogInputSql"); //指定使用的properties文件名
		mySql3.setSqlId("FIRuleDealErrLogInputSql3");//指定使用的Sql的id
		mySql3.addSubPara(RuleDealBatchNo);//指定传入的参数
		mySql3.addSubPara(DataSourceBatchNo);//指定传入的参数
		strSQL= mySql3.getString(); 
  	turnPage.queryModal(strSQL, FIRuleDealErrLogGrid);
  	 fm.ExpSQL.value = strSQL;
  	    document.all('CertificateID').value = '';
		document.all('ErrInfo').value = '';
		document.all('businessno').value = '';
		document.all('RulePlanID').value = '';
		document.all('RuleID').value = '';
		document.all('DealState').value = '';	
	}
	
	catch(Ex)
	{
		alert(Ex.message);
	}
}
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  //释放“增加”按钮

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
	mOperate="";
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


    mOperate="";
    initFIRuleDealErrLogQuery();
  }
}

