//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass(); 
var mySql = new SqlClass();

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

function returnParent()
{
	//top.opener.initSelfGrid();
	//top.opener.easyQueryClick();
	top.close();
}

function easyQueryClick()
{
	// 初始化表格
	initPolGrid();
	// 书写SQL语句
	var strSQL = "";
	if( tIsCancelPolFlag == "0"){
		
	/*strSQL = "Select GrpContNo, GrpPolNo, riskcode, "
         + "paycount,  DutyCode,"
         + "PayPlanCode, Sumduepaymoney,  sumactupaymoney,"
         + "payintv, paydate,  paytype,  "
         + "curpaytodate,BankOnTheWayFlag, banksuccflag ,"
         + " startdate,enddate,startdate"
         + "  From LJSPayPerson a,LCContState b"
         + " Where a.ContNo = b.ContNo "
         + "   and a.polno = b.polno "
         + "   and a.ContNo='" + tContNo + "'"
         + "   and a.PolNo = '" + tPolNo + "'"
         + "   and StateType in ('Available','Terminate')"
         + "   and State in ('0','1')";*/
         mySql = new SqlClass();
		mySql.setResourceName("sys.PayPremQuerySql");
		mySql.setSqlId("PayPremQuerySql1");
		mySql.addSubPara(tContNo); 
		mySql.addSubPara(tPolNo); 
	}
	else
	if(tIsCancelPolFlag=="1"){//销户保单查询
	 
	 /*strSQL = "Select GrpContNo,GrpPolNo,riskcode,"
          + "paycount,DutyCode,"      
          + "PayPlanCode, Sumduepaymoney, sumactupaymoney,"
          + "payintv, paydate,paytype,"
          + "curpaytodate,BankOnTheWayFlag, banksuccflag, "
          + " startdate,enddate,startdate"
          + "  From LJSPayPerson "
          + " Where a.ContNo = b.ContNo "
          + "   and a.polno = b.polno "
          + "   and a.ContNo='" + tContNo + "'"
          + "   and a.PolNo = '" + tPolNo + "'"
          + "   and StateType in ('Available','Terminate')"
          + "   and State in ('0','1')";	*/ 
          mySql = new SqlClass();
		mySql.setResourceName("sys.PayPremQuerySql");
		mySql.setSqlId("PayPremQuerySql2");
		mySql.addSubPara(tContNo); 
		mySql.addSubPara(tPolNo); 
	}
	else {
	  alert("保单类型传输错误!");
	  return;
	  }
	
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
  	alert("数据库中没有满足条件的数据！");
    //alert("查询失败！");
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