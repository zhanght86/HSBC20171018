//            该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var arrDataSet;
var turnPage = new turnPageClass();


//返回按钮对应的操作
function returnParent()
{
  var arrReturn = new Array();
	var tSel = RulesVersionTraceGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		//top.close();
		alert( "请您先选择一条记录，再点击返回按钮。" );
	else
	{

			try
			{
				arrReturn = getQueryResult();
				top.opener.afterQuery1( arrReturn );
			}
			catch(ex)
			{
				//alert( "没有发现父窗口的afterQuery接口。" + ex );
				alert(ex.message);
			}
			top.close();

	}
}



function getQueryResult()
{
	var arrSelected = null;
	tRow = RulesVersionTraceGrid.getSelNo();
	if( tRow == 0 || tRow == null )
	{
	  return arrSelected;
	}
	arrSelected = new Array();
		// 书写SQL语句
	var strSQL = "";
	/**
	strSQL = "select * ";
	strSQL = strSQL + " from FIRulesVersionTrace a  where a.Maintenanceno ='"+RulesVersionTraceGrid.getRowColData(tRow-1,1)+"' ";
	*/
	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.RulesVersionTraceQuerySql"); //指定使用的properties文件名
		mySql1.setSqlId("RulesVersionTraceQuerySql1");//指定使用的Sql的id
		mySql1.addSubPara(RulesVersionTraceGrid.getRowColData(tRow-1,1));//指定传入的参数
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



// 查询按钮对应的操作
function easyQueryClick()
{
	// 初始化表格
	initRulesVersionTraceGrid();
	// 书写SQL语句
	var strSQL = "";
	/**
	strSQL = "select * from FIRulesVersionTrace where 1=1 "
     + getWherePart('Maintenanceno','Maintenanceno','like')
     + getWherePart('VersionNo','VersionNo','like')
     + getWherePart('MaintenanceState')
     + getWherePart('MaintenanceReMark','MaintenanceReMark','like')
     +" order by Maintenanceno";
     */
	var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.RulesVersionTraceQuerySql"); //指定使用的properties文件名
		mySql2.setSqlId("RulesVersionTraceQuerySql2");//指定使用的Sql的id
		mySql2.addSubPara(fm.Maintenanceno.value);//指定传入的参数
		mySql2.addSubPara(fm.VersionNo.value);//指定传入的参数
		mySql2.addSubPara(fm.MaintenanceState.value);//指定传入的参数
		mySql2.addSubPara(fm.MaintenanceReMark.value);//指定传入的参数
		strSQL= mySql2.getString();
	turnPage.queryModal(strSQL,RulesVersionTraceGrid);
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