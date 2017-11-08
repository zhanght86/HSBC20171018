//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
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


function easyQueryClick()
{
	// 初始化表格
	initPolGrid();
	
	// 书写SQL语句
	var strSQL = "";
	if(tIsCancelPolFlag == "0")
	{
	     /* strSQL = " select polno,contno,FiscalYear,BONUSMONEY,decode(BONUSFLAG,'1','已分配','0','已计算'),BONUSCOEF,SGETDATE,"
             +" decode(BONUSGETMODE,'1','累计生息','2','现金','3','抵交续期保费','5','增额交清'),aGETDATE, "
             +" (case when a.bonusgetmode = '3' and a.BONUSFLAG='1' then a.bonusmoney ELSE 0 END ),"
             +" (case when a.bonusgetmode = '1' and a.BONUSFLAG='1' then (select sum(money) from lcinsureacctrace d where d.insuaccno = '000001'and d.polno = a.polno and paydate<=a.aGETDATE)  ELSE 0 END),"
             +" nvl((select sum(amnt) from lcduty r  where r.polno = a.polno and length(dutycode) = 10 and substr(firstpaydate, 0, 4) = (a.FiscalYear+1)),0),"
             +" (case when a.BONUSFLAG='1' then (select sum(amnt) from lcduty r where r.polno = a.polno) else 0 end) from LOBonusPol a "
	     		 + " Where a.ContNo='" + tContNo + "' and a.PolNo='" + tPolNo + "'"
	     		 + " order by MakeDate asc,MakeTime asc";   */  
	   mySql = new SqlClass();
	mySql.setResourceName("sys.BonusQuerySql");
	mySql.setSqlId("BonusQuerySql1");
	mySql.addSubPara(tContNo);   	
	mySql.addSubPara(tPolNo); 	  				 
	}
	else
	 if(tIsCancelPolFlag == "1")
	 {//销户保单 
	    /*  strSQL =" select polno,contno,FiscalYear,BONUSMONEY,decode(BONUSFLAG,'1','已分配','0','已计算'),BONUSCOEF,SGETDATE,"
             +" decode(BONUSGETMODE,'1','累计生息','2','现金','3','抵交续期保费','5','增额交清'),aGETDATE, "
             +" (case when a.bonusgetmode = '3' and a.BONUSFLAG='1' then a.bonusmoney ELSE 0 END ),"
             +" (case when a.bonusgetmode = '1' and a.BONUSFLAG='1' then (select sum(money) from lcinsureacctrace d where d.insuaccno = '000001'and d.polno = a.polno and paydate<=a.aGETDATE)  ELSE 0 END),"
             +" nvl((select sum(amnt) from lcduty r  where r.polno = a.polno and length(dutycode) = 10 and substr(firstpaydate, 0, 4) = (a.FiscalYear+1)),0),"
             +" ((select sum(amnt) from lcduty r where r.polno = a.polno)) from LPBonusPol a "
	     		   + " Where a.ContNo='" + tContNo + "' and a.PolNo='" + tPolNo + "'"
	     		   + " order by MakeDate asc,MakeTime asc";  */   	
	 mySql = new SqlClass();
	mySql.setResourceName("sys.BonusQuerySql");
	mySql.setSqlId("BonusQuerySql2");
	mySql.addSubPara(tContNo);   	
	mySql.addSubPara(tPolNo); 	
	 
	 }
	else
    {
	    alert("保单类型传输错误!");
	    return;
	}
	//alert(strSQL);
	
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult)
  {
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

// 查询按钮
function QueryClick()
{
	var strSQL="";

	     /* strSQL = " select polno,contno,FiscalYear,BONUSMONEY,decode(BONUSFLAG,'1','已分配','0','已计算'),BONUSCOEF,SGETDATE,"
             +" decode(BONUSGETMODE,'1','累计生息','2','现金','3','抵交续期保费','5','增额交清'),aGETDATE, "
             +" (case when a.bonusgetmode = '3' and a.BONUSFLAG='1' then a.bonusmoney ELSE 0 END )  from LOBonusPol a "
	     		   + " Where 1=1"
	      		+ getWherePart('a.ContNo','ContNo')
	      		+ getWherePart('a.PolNo','PolNo')
	       		+ " order by MakeDate,MakeTime asc";*/
	 mySql = new SqlClass();
	mySql.setResourceName("sys.BonusQuerySql");
	mySql.setSqlId("BonusQuerySql3");
	mySql.addSubPara(fm.ContNo.value);   	
	mySql.addSubPara(fm.PolNo.value); 
	   //查询SQL，返回结果字符串
        turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);  
  
  		//判断是否查询成功
       if (!turnPage.strQueryResult)
  	   {
  		   PolGrid.clearData();
       	   alert("未查到该保单险种给付项信息！");
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
  		arrGrid = arrDataSet;
  		//调用MULTILINE对象显示查询结果
  		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
}

function returnParent()
{
	//top.opener.initSelfGrid();
	//top.opener.easyQueryClickSelf();
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