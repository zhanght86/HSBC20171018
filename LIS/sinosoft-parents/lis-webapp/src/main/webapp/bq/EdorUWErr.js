//程序名称：EdorUWErr.js
//程序功能：人工核保未过原因查询
//创建日期：2005-06-23 15:10:36
//创建人  ：liurongxiao
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;

var turnPage = new turnPageClass();





// 人工核保未过原因查询
function easyQueryClick()
{
	// 初始化表格
	initUWErrGrid();
	
	// 书写SQL语句
	var strSQL = "";
	var ContNo = fm.ContNo.value;
	var EdorNo = fm.EdorNo.value;
//	strSQL = "select a.edorno,a.contno,a.uwno,a.uwerror,a.modifydate from LPUWError a where 1=1 "
//			 + " and a.PolNo in (select distinct polno from lppol where contno ='" +ContNo+ "' and edorno='"+EdorNo+"')"
//			 + " and (a.uwno = (select max(b.uwno) from LPUWError b where b.ContNo = '" +ContNo + "' and b.EdorNo = '"+EdorNo+"' and b.PolNo = a.PolNo))"
//			 + " and a.EdorNo='"+EdorNo+"'"
//			 + " union "
//			 + "select c.edorno,c.contno,c.uwno,c.uwerror,c.modifydate from LPCUWError c where 1=1"
//			 + " and c.ContNo ='" + ContNo + "'"+" and c.EdorNo='"+EdorNo+"'"
//			 + " and (c.uwno = (select max(d.uwno) from LPCUWError d where d.ContNo = '" + ContNo + "' and d.EdorNo = '"+EdorNo+"'))"
	var sqlid1="EdorUWErrSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.EdorUWErrSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(ContNo);//指定传入的参数
	mySql1.addSubPara(EdorNo);//指定传入的参数
	mySql1.addSubPara(ContNo);//指定传入的参数
	mySql1.addSubPara(EdorNo);//指定传入的参数
	mySql1.addSubPara(EdorNo);//指定传入的参数
	mySql1.addSubPara(ContNo);//指定传入的参数
	mySql1.addSubPara(EdorNo);//指定传入的参数
	mySql1.addSubPara(ContNo);//指定传入的参数
	mySql1.addSubPara(EdorNo);//指定传入的参数
	strSQL=mySql1.getString();			 
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    
    alert("无最终核保信息！");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = UWErrGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  return true;

}


