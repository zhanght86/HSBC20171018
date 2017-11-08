//程序名称：SpecialQuery.js
//程序功能：特约查询
//创建日期：2005-09-29 11:10:36
//创建人  ：wuhao2
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var tPolNO = "";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
function LLSpecGrid()
{   
   var strSQL = "";
  /* strSQL = "select (select riskname from lmriskapp where riskcode=a.RiskCode) ,a.InsuredName,a.Prem,a.Amnt,b.SpecContent from LCPol a,LCSpec b where 1=1"
           +" and a.ContNo=b.ContNo and a.ContNo='"+fm.all('ContNo').value+"'" 
           +" order by a.ContNo";*/
	     
	mySql = new SqlClass();
	mySql.setResourceName("sys.SpecialQuerySql");
	mySql.setSqlId("SpecialQuerySql1");
	mySql.addSubPara(fm.all('ContNo').value );       
//   var arr= easyExecSql(strSQL);
//    if(arr)
//    {
//        displayMultiline(arr,SpecGrid);
//    }

	turnPage.queryModal(mySql.getString(), SpecGrid);



//判断是否查询成功
 turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
  if (!turnPage.strQueryResult) {
  	
  	SpecGrid.clearData();
  	alert("数据库中没有满足条件的数据！");
    return false;
  }

}

















