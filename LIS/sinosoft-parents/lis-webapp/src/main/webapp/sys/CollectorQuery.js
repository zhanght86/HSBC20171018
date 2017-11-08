
//程序名称：CollectorQuery.js
//程序功能：收费员查询
//创建日期：2005-09-30 11:10:36
//创建人  ：wuhao2
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件
 
var showInfo;
var mDebug="1";
var tPolNO = "";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
function LLCollectorGrid()
{   
   var strSQL = "";

  /* strSQL = " select distinct a.agentcode,c.branchattr,a.managecom,a.name,(select codename from ldcode where codetype='sex' and code=sex),a.idno,a.agentstate,a.phone,a.mobile from laagent a,lccont d,LABranchGroup c "
   			+" where a.agentcode = (d.agentcode) and (a.AgentCode)='"+document.all('AgentCode').value+"' "
   			+" and a.AgentGroup = c.AgentGroup";*/
	mySql = new SqlClass();
	mySql.setResourceName("sys.CollectorQuerySql");
	mySql.setSqlId("CollectorQuerySql1");
	mySql.addSubPara(document.all('AgentCode').value); 
	turnPage.queryModal(mySql.getString(), CollectorGrid);


//判断是否查询成功
 turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1); 
  if (!turnPage.strQueryResult) {
  	
  	CollectorGrid.clearData();
  	alert("数据库中没有满足条件的数据！");
    return false;
  }
}