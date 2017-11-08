//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var sqlresourcename = "sys.LDPersonQuerySql";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

// 查询按钮
function easyQueryClick()
{
	// 初始化表格
	initPersonGrid();
	// 书写SQL语句
	var strSQL = "";
	/*
	strSQL = "select CustomerNo, Name, Sex, Birthday, IDType, IDNo from LDPerson where 1=1 "
		+ getWherePart( 'CustomerNo' )
		+ getWherePart( 'Name' )
		+ getWherePart( 'Sex' )
		+ getWherePart( 'Birthday' )
		+ getWherePart( 'IDType' )
		+ getWherePart( 'IDNo' );
	*/	
		
		var sqlid1="LDPersonQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(fm.CustomerNo.value);
		mySql1.addSubPara(fm.Name.value);
		mySql1.addSubPara(fm.Sex.value);
		mySql1.addSubPara(fm.Birthday.value);
		mySql1.addSubPara(fm.IDType.value);
		mySql1.addSubPara(fm.IDNo.value);
	turnPage.strQueryResult  = easyQueryVer3(mySql1.getString(), 1, 1, 1);  
    
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("查询失败！");
    return false;
  }

	//查询成功则拆分字符串，返回二维数组
  arrDataSet = decodeEasyQueryResult(turnPage.strQueryResult);
  turnPage.arrDataCacheSet = arrDataSet;
  
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = PersonGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var tArr = new Array();
  tArr = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

  //调用MULTILINE对象显示查询结果
  displayMultiline(tArr, turnPage.pageDisplayGrid);
}

function returnParent()
{
  var arrReturn = new Array();
	var tSel = PersonGrid.getSelNo();	
		
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
	//设置需要返回的数组
	var arrSelected = null;
	
	//获取正确的行号
	var tRow = PersonGrid.getSelNo();	
	if( tRow == 0 || tRow == null ||arrDataSet == null )
	  return arrSelected;

	arrSelected = new Array();
	
	var CustomerNo = PersonGrid.getRowColData(tRow - 1,1);
	/*
	strSql = "select a.*, b.*,c.* from LDPerson a, LCAddress b, LCAccount c"
	       	 + " where a.CustomerNo = '" + trim(CustomerNo) + "'"
	       	 + " and a.CustomerNo = b.CustomerNo"
	       	 + " and a.CustomerNo = c.CustomerNo"
	       	 + " and b.AddressNo = '1'";
	*/
	//strSql = "select * from LDPerson where CustomerNo = '" + trim(CustomerNo) + "'";
	
	var sqlid2="LDPersonQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(trim(CustomerNo));

	
	var strQueryResult  = easyQueryVer3(mySql2.getString(), 1, 1, 1);  
	arrSelected = decodeEasyQueryResult(strQueryResult);

	return arrSelected;
}