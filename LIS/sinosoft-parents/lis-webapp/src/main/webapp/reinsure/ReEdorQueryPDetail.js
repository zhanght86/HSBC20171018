//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
//var mSwitch = parent.VD.gVSwitch;
//var turnPage = new turnPageClass(); 

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
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
	
	parent.fraMain.rows = "0,0,0,0,*";
}

function test1()
{
	myAlert("111");
}


// 查询按钮
function easyQueryClick()
{
	
	myAlert("here");
	// 初始化表格
	initPolGrid();
	
	// 书写SQL语句
	var strSQL = "";
	
	//strSQL = "select PolNo,GrpPolNo,ContNo,PayNo,PayAimClass,SumActuPayMoney,PayIntv,PayDate,PayType,CurPayToDate from LJAPayPerson where PayNo='" + tPayNo + "'";			 
	//strSQL = "select PolNo,PayNo,PayAimClass,SumActuPayMoney,PayIntv,PayDate,CurPayToDate,GrpPolNo,ContNo,PayType from LJAPayPerson";			 
	var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.ReEdorQueryPDetailSql"); //指定使用的properties文件名
		mySql100.setSqlId("ReEdorQueryPDetailSql100");//指定使用的Sql的id
	    mySql100.addSubPara("1");
	    strSQL=mySql100.getString();
	
	myAlert(strSQL);
	
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
    myAlert(""+"查询失败！"+"");
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = PolGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
}
