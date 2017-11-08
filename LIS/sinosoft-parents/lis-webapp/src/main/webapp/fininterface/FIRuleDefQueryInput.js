 //             该文件中包含客户端需要处理的函数和事件
//Creator :范昕	
//Date :2008-09-17

var showInfo;
var mDebug="0";
var arrDataSet;
var turnPage = new turnPageClass();

//初始化页面
function QueryForm()
{
	initFIRuleDefQueryGrid();
	var strSQL = ""; 
	/**
	strSQL = "select VersionNo,RuleID,RuleName,RuleDealMode,RuleReturnMean"
	         +" from FIRuleDef where 1=1 "
	         + getWherePart('VersionNo','VersionNo')
	         + getWherePart('RuleID','RuleID')
	         + getWherePart('RuleName','RuleName','like')
	         + getWherePart('RuleDealMode','RuleDealMode');
	         */
	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.FIRuleDefQueryInputSql"); //指定使用的properties文件名
		mySql1.setSqlId("FIRuleDefQueryInputSql1");//指定使用的Sql的id
		mySql1.addSubPara(fm.VersionNo.value);//指定传入的参数
		mySql1.addSubPara(fm.RuleID.value);//指定传入的参数
		mySql1.addSubPara(fm.RuleName.value);//指定传入的参数
		mySql1.addSubPara(fm.RuleDealMode.value);//指定传入的参数
		strSQL= mySql1.getString();
	 
  
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

	//判断是否查询成功
	if (!turnPage.strQueryResult)
	{
//		alert("未查询到满足条件的数据！");
		return false;
	}

	//设置查询起始位置
	turnPage.pageIndex = 0;
	//在查询结果数组中取出符合页面显示大小设置的数组
	turnPage.pageLineNum = 30 ;
	//查询成功则拆分字符串，返回二维数组
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//设置初始化过的MULTILINE对象
	turnPage.pageDisplayGrid = FIRuleDefQueryGrid;
	//保存SQL语句
	turnPage.strQuerySql = strSQL ;
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
	//调用MULTILINE对象显示查询结果
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
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
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}

function ReturnData()
{
  var arrReturn = new Array();
	var tSel = FIRuleDefQueryGrid.getSelNo();	
		
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		
			try
			{	
				arrReturn1 = getQueryResult();
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
	tRow = FIRuleDefQueryGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrDataSet == null )
	  return arrSelected;
	
	arrSelected = new Array();
	
	var strSQL = "";
	/**
	strSQL = "select VersionNo,RuleID,RuleName,RuleDealMode,RuleDealClass,RuleFileName,replace(RuleDealSQL,'|','@'),replace(RuleReturnMean,'|','@') from FIRuleDef where 1=1 "
	       + "and VersionNo='"+FIRuleDefQueryGrid.getRowColData(tRow-1,1)+"' and RuleID='"+FIRuleDefQueryGrid.getRowColData(tRow-1,2)+"'" ; 
	*/
	var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.FIRuleDefQueryInputSql"); //指定使用的properties文件名
		mySql2.setSqlId("FIRuleDefQueryInputSql2");//指定使用的Sql的id
		mySql2.addSubPara(FIRuleDefQueryGrid.getRowColData(tRow-1,1));//指定传入的参数
		mySql2.addSubPara(FIRuleDefQueryGrid.getRowColData(tRow-1,2));//指定传入的参数
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



