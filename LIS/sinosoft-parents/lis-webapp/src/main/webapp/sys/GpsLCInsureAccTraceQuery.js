var turnPage = new turnPageClass();

function GoBack(){
	parent.window.focus();
	
  window.close();
}

function LCInsureAccTraceQuery(){
	

	initGrpPolGrid();
	
	var sqlid826092258="DSHomeContSql826092258";
var mySql826092258=new SqlClass();
mySql826092258.setResourceName("sys.GpsLCInsureAccTraceQuerySql");//指定使用的properties文件名
mySql826092258.setSqlId(sqlid826092258);//指定使用的Sql的id
mySql826092258.addSubPara(tPolNo);//指定传入的参数
mySql826092258.addSubPara(tInsuAccNo);//指定传入的参数
mySql826092258.addSubPara(tPayPlanCode);//指定传入的参数
var strSQL=mySql826092258.getString();

//	var strSQL = "select InsuAccNo,PayPlanCode,PayDate,OtherNo,OtherType,Money,MoneyType,makedate,maketime from LCInsureAccTrace where PolNo='"
//    				 +  tPolNo +"' and InsuAccNo='" + tInsuAccNo + "' and PayPlanCode='" + tPayPlanCode + "' order by makedate,maketime";
  				 

 
  
 turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
 
  //判断是否查询成功
 if (!turnPage.strQueryResult) {
    
  
    alert("查询失败！");
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = GrpPolGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	
	
	
	
}