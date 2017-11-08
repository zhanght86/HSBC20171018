//程序名称：ShowTraceQuery.js
//程序功能：保单服务轨迹查询
//创建日期：2005-11-24 11:19
//创建人  ：关巍
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var tPolNO = "";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
function LLTraceGrid()
{   
   var strSQL = "";

  /* strSQL =" select startserdate,endserdate,b.agentcode,name "
           +"  from laagent a, lacommisiondetail b "
           +" where a.agentcode = b.agentcode "
           +"   and grpcontno = '"+fm.all('ContNo').value+"'"
           +" union select startserdate,endserdate,c.agentcode,name "
           +"  from laagent a, lacommisiondetailb c "
           +" where a.agentcode = c.agentcode "
           +"   and grpcontno = '"+fm.all('ContNo').value+"'"
           +" order by startserdate "*/
	mySql = new SqlClass();
	mySql.setResourceName("sys.ShowTraceQuerySql");
	mySql.setSqlId("ShowTraceQuerySql1");
	mySql.addSubPara(fm.all('ContNo').value ); 
	mySql.addSubPara(fm.all('ContNo').value ); 
	turnPage.queryModal(mySql.getString(), TraceGrid);


//判断是否查询成功
 turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1); 
  if (!turnPage.strQueryResult) {
  	
  	TraceGrid.clearData();
  	alert("数据库中没有满足条件的数据！");
    return false;
  }
}