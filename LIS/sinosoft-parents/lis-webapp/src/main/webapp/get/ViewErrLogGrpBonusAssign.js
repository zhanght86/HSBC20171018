var showInfo;
var mDebug="0";
var turnPage = new turnPageClass(); 
var arrDataSet;
// 查询按钮
function easyQueryClick()
{
	
	// 初始化表格
	initErrLogBonusGrid();
	
	// 书写SQL语句
	var strSQL = "";
	strSQL = "select FiscalYear,GrpPolNo,Type,errMsg,makedate from LOBonusAssignGrpErrLog where 1=1  "
				 + getWherePart( 'GrpPolNo' )
				 + getWherePart( 'Type' )
				 + getWherePart( 'FiscalYear' )
				 + getWherePart( 'MakeDate' );
				 
//	alert(strSQL);
	
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有查询到记录！");
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = ErrLogBonusGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}




// 数据返回父窗口
function returnParent()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	//alert(tSel);
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		try
		{
			arrReturn = getQueryResult();
			//alert("返回"+arrReturn);
			top.opener.afterQuery( arrReturn );
			//alert("333");
			top.close();
		}
		catch(ex)
		{
			alert( "请先选择一条非空记录，再点击返回按钮。");
			//alert( "没有发现父窗口的afterQuery接口。" + ex );
		}
		
	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = PolGrid.getSelNo();
	//alert(arrGrid);
	if( tRow == 0 || tRow == null || arrDataSet == null )
		return arrSelected;
	arrSelected = new Array();
	arrSelected[0] = new Array();
	arrSelected[0] = PolGrid.getRowData(tRow-1);
	
	//tRow = 10 * turnPage.pageIndex + tRow; //10代表multiline的行数
	//设置需要返回的数组
	//arrSelected[0] = turnPage.arrDataCacheSet[tRow-1];
	//设置需要返回的数组
	//arrSelected[0] = arrDataSet[tRow-1];
	//alert(arrDataSet[tRow-1]);
	return arrSelected;
}