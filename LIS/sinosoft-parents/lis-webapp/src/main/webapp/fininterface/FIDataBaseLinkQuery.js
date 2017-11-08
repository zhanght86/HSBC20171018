//            该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var arrDataSet;
var turnPage = new turnPageClass();

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
	var tSel = FIDataBaseLinkGrid.getSelNo();	
		
	if( tSel == 0 || tSel == null )
		//top.close();
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
	tRow = FIDataBaseLinkGrid.getSelNo();
	//alert("111" + tRow);
	if( tRow == 0 || tRow == null || arrDataSet == null )
	  return arrSelected;
	
	arrSelected = new Array();
	
	var strSQL = "";
	/**
	strSQL = "select * from FIDataBaseLink where 1=1 "
	       + "and InterfaceCode='"+FIDataBaseLinkGrid.getRowColData(tRow-1,1)+"'"; 
	       */
	         //alert(FIDataBaseLinkGrid.getRowColData(tRow-1,1));
	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.FIDataBaseLinkQuerySql"); //指定使用的properties文件名
		mySql1.setSqlId("FIDataBaseLinkQuerySql1");//指定使用的Sql的id
		mySql1.addSubPara(FIDataBaseLinkGrid.getRowColData(tRow-1,1));//指定传入的参数
		strSQL= mySql1.getString();
	     
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

// 查询按钮
function submitForm()
{
	initFIDataBaseLinkGrid();  
	// 书写SQL语句
	var strSQL = ""; 

	strSQL = "select InterfaceCode,InterfaceName,DBType,IP,Port,DBName,ServerName,UserName,PassWord,ManageCom,Operator,MakeDate,MakeTime from FIDataBaseLink"
	         +" where 1=1 "
	         + getWherePart('InterfaceCode','InterfaceCode','like')
	         + getWherePart('InterfaceName','InterfaceName','like')
	         + getWherePart('DBType','DBType','like')
	         + getWherePart('IP','IP','like')
	         + getWherePart('Port','Port','like')
	         + getWherePart('DBName','DBName','like')
	         + getWherePart('ServerName','ServerName','like')
	         + getWherePart('UserName','UserName','like')
	         + getWherePart('PassWord','PassWord','like')
	         + getWherePart('Operator','Operator');
	      
	 //    alert(strSQL);
	
  
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
	turnPage.pageDisplayGrid = FIDataBaseLinkGrid;
	//保存SQL语句
	turnPage.strQuerySql = strSQL ;
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
	//调用MULTILINE对象显示查询结果
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}