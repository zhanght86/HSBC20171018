//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
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
//
//// 数据返回父窗口
//function getQueryDetail()
//{
//	var arrReturn = new Array();
//	var tSel = PolGrid.getSelNo();
//	
//	if( tSel == 0 || tSel == null )
//		alert( "请先选择一条记录。" );
//	else
//	{
//	    var cPayNo = PolGrid.getRowColData(tSel - 1,1);				
////		parent.VD.gVSwitch.deleteVar("PayNo");				
////		parent.VD.gVSwitch.addVar("PayNo","",cPayNo);
//		
//		if (cPayNo == "")
//		    return;
//		    
//		  var cIncomeType = PolGrid.getRowColData(tSel - 1,3);
//		  var cSumActuPayMoney = 	PolGrid.getRowColData(tSel - 1,4);
//		  //alert(cSumActuPayMoney);
//		  if (cIncomeType==0||cIncomeType==1||cIncomeType==2) {
//		  //var urlstr1="../sys/AllFeeQueryPDetail.jsp?PayNo=" + cPayNo+ "&SumActuPayMoney=" + cSumActuPayMoney;
//		  //alert(urlstr1); 
//				window.open("../sys/AllFeeQueryPDetail.jsp?PayNo=" + cPayNo + "&SumActuPayMoney=" + cSumActuPayMoney);	
//			}	
//			else {
//				//var urlstr1="../sys/AllFeeQueryEDetail.jsp?PayNo=" + cPayNo+ "&SumActuPayMoney=" + cSumActuPayMoney;
//		  	//alert(urlstr1); 
//				window.open("../sys/AllFeeQueryEDetail.jsp?PayNo=" + cPayNo + "&SumActuPayMoney=" + cSumActuPayMoney);	
//							
//			}
//	}
//}



function easyQueryClick()
{
	// 初始化表格
	initPolGrid();
	// 书写SQL语句
	var strSQL = "";
	if( tIsCancelPolFlag == "0"){
		
		var sqlid1="PremQuerySQLl";
		var mySql1=new SqlClass();
		mySql1.setResourceName("sys.PremQuerySQL"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(tContNo);//指定传入的参数
		mySql1.addSubPara(tPolNo);//指定传入的参数
	    strSQL=mySql1.getString();	
		
//	strSQL = " select GrpContNo,DutyCode,PayPlanCode,(select decode(PayPlanType,0,'正常保费',01,'健康加费',02,'职业加费',03,'复效健康加费',04,'复效职业加费') from dual),PayTimes,"
//	       + " Rate,PayStartDate,PayEndDate,PaytoDate, (Select codename from ldcode where codetype = 'payintv' and code = lcprem.PayIntv), StandPrem,Prem,SumPrem"
//	       + " from LCPrem  where ContNo='" + tContNo + "'"
//	       + " and PolNo = '" + tPolNo + "'";
	}
	else
	if(tIsCancelPolFlag=="1"){//销户保单查询
	
		var sqlid2="PremQuerySQL2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("sys.PremQuerySQL"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(tPolNo);//指定传入的参数
	    strSQL=mySql2.getString();	
	
//	  strSQL = "select LBPrem.PolNo,LBPrem.PayPlanCode,LBPrem.PayPlanType,LBPrem.PayTimes,LBPrem.Mult,LBPrem.Prem,LBPrem.SumPrem,LBPrem.Rate,LBPol.AppntName,LBPol.riskcode,LBPrem.PaytoDate,LBPrem.PayIntv ,,LCPrem.managefeerate from LBPol,LBPrem where LBPrem.PolNo = '" + tPolNo + "' and LBPol.PolNo = LBPrem.PolNo";			 
	}
	else {
	  alert("保单类型传输错误!");
	  return;
	  }
	
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
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