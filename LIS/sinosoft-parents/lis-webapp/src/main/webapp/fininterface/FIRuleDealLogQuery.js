
//Creator :范昕	
//Date :2008-09-09

var showInfo;
var mDebug="0";
var arrDataSet;
var turnPage = new turnPageClass();
window.onfocus=myonfocus;

//使得从该窗口弹出的窗口能够聚焦
function myonfocus()
{
	if(showInfo!=null) //shwoInfo是什么？
	{
	  try
	  {
	    showInfo.focus();
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
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

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else 
 	{
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}

function queryFIRuleDealLog()
{
		try
		{
			initFIRuleDealLogGrid();
			var sStartDay = document.all('StartDay').value;
			var sEndDay = document.all('EndDay').value;
			var strSQL = ""; 
			/**
			strSQL = "select VersionNo,RuleDealBatchNo,DataSourceBatchNo,CallPointID || '-' || (select distinct b.codename from ficodetrans b where b.code = FIRuleDealLog.Callpointid and b.codetype = 'CallPointID'),RuleDealResult,DealOperator,RulePlanID || '-' || (select a.rulesplanname from firuleplandef a where a.rulesplanid = FIRuleDealLog.ruleplanid),RuleDealDate from FIRuleDealLog where ruledealresult = 'Fail' and VersionNo = '"+VersionNo+"' ";
			if(document.all('StartDay').value != '')
			{
				strSQL = strSQL + " and RuleDealDate >= '"+document.all('StartDay').value+"' ";
			}
			if(document.all('EndDay').value != '')
			{
				strSQL = strSQL + " and RuleDealDate <= '"+document.all('EndDay').value+"' ";
			}
			*/
			var mySql1=new SqlClass();
				mySql1.setResourceName("fininterface.FIRuleDealLogQuerySql"); //指定使用的properties文件名
				mySql1.setSqlId("FIRuleDealLogQuerySql1");//指定使用的Sql的id
				mySql1.addSubPara(VersionNo);//指定传入的参数
				mySql1.addSubPara(document.all('StartDay').value);//指定传入的参数
				mySql1.addSubPara(document.all('EndDay').value);//指定传入的参数
				strSQL= mySql1.getString();
			
  		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
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
			turnPage.pageDisplayGrid = FIRuleDealLogGrid;
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

function ReturnData()
{
  var arrReturn = new Array();
	var tSel = FIRuleDealLogGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		
			try
			{	
				arrReturn1 = getQueryResult();
				//alert(arrReturn1);
				top.opener.afterQuery1( arrReturn1 );
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
	tRow = FIRuleDealLogGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrDataSet == null )
	  return arrSelected;
	
	arrSelected = new Array();
	
	var strSQL = "";
	/**
	strSQL = "select RuleDealBatchNo,DataSourceBatchNo,CallPointID,RuleDealResult,DealOperator,RulePlanID,RuleDealDate,LogFilePath,LogFileName from FIRuleDealLog where 1=1 "
	       + "and VersionNo='"+FIRuleDealLogGrid.getRowColData(tRow-1,1)+"' and RuleDealBatchNo='"+FIRuleDealLogGrid.getRowColData(tRow-1,2)+"' and DataSourceBatchNo='"+FIRuleDealLogGrid.getRowColData(tRow-1,3)+"' and CallPointID = substr('"+FIRuleDealLogGrid.getRowColData(tRow-1,4)+"',1,2) and RulePlanID = substr('"+FIRuleDealLogGrid.getRowColData(tRow-1,7)+"',1,8) " ; 
	 */    
	//alert(strSQL);
	var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.FIRuleDealLogQuerySql"); //指定使用的properties文件名
		mySql2.setSqlId("FIRuleDealLogQuerySql4");//指定使用的Sql的id
		mySql2.addSubPara(FIRuleDealLogGrid.getRowColData(tRow-1,1));//指定传入的参数
		mySql2.addSubPara(FIRuleDealLogGrid.getRowColData(tRow-1,2));//指定传入的参数
		mySql2.addSubPara(FIRuleDealLogGrid.getRowColData(tRow-1,3));//指定传入的参数
		mySql2.addSubPara(FIRuleDealLogGrid.getRowColData(tRow-1,4));//指定传入的参数
		mySql2.addSubPara(FIRuleDealLogGrid.getRowColData(tRow-1,7));//指定传入的参数
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