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



// 数据返回父窗口
function getQueryDetail()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPayNo = PolGrid.getRowColData(tSel - 1,1);				
		if (cPayNo == "")
		    return;
		  var cIncomeType = PolGrid.getRowColData(tSel - 1,3);
		  var cSumActuPayMoney = 	PolGrid.getRowColData(tSel - 1,4);
		  if (cIncomeType==1) {
				window.open("../sys/AllFeeQueryGDetail.jsp?PayNo=" + cPayNo + "&SumActuPayMoney=" + cSumActuPayMoney,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");	
			}
			else
			if (cIncomeType==2) {
				window.open("../sys/AllFeeQueryPDetail.jsp?PayNo=" + cPayNo + "&SumActuPayMoney=" + cSumActuPayMoney,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");	
			}	
			else {
				window.open("../sys/AllFeeQueryEDetail.jsp?PayNo=" + cPayNo + "&SumActuPayMoney=" + cSumActuPayMoney,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");	
							
			}
	}
}




function easyQueryClick()
{
	
	// 初始化表格
	initPolGrid();
	
	// 书写SQL语句
	var strSQL = "";
	
	/*strSQL = "select a.PayNo,a.IncomeNo,a.IncomeType,a.SumActuPayMoney,b.CurPayToDate,a.ConfDate,a.Operator,a.ManageCom,a.AgentCode,b.paycount from LJAPay a,LJAPayGrp b  where a.IncomeNo='" + tGrpContNo + "' and a.IncomeType='1' "
	       +"and a.PayNo=b.PayNo and b.RiskCode='"+ tRiskCode +"'"
	       +"order by b.paycount ";	*/		 
	//alert(strSQL);
	 mySql = new SqlClass();
	mySql.setResourceName("sys.GrpRelFeeQuerySql");
	mySql.setSqlId("GrpRelFeeQuerySql1");
	mySql.addSubPara(tGrpContNo);  	
	mySql.addSubPara(tRiskCode);  	
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
function goback()
{
  top.close();	
}  
