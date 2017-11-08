//
//程序名称：YBTDZQuery.js
//程序功能：邮保通对帐数据查询
//创建日期：2008-01-24 15:10
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容

//var arrDataSet;
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//简单查询

function easyPrint()
{
	easyQueryPrint(2,'CodeGrid','turnPage');
}

function easyQuery()
{	
	if (fm.StartDate.value == "" || fm.EndDate.value == "" )
	{
			alert("开始时间和结束时间不能为空2!");
			return false;
	}
	
	initCodeGrid();
	
	// 书写SQL语句
	/*var strSQL = "select  b.managecom,a.banknode,a.transdate,a.makedate"
             + " from lktransstatus a ,lkcodemapping b"
             + " where a.banknode=b.banknode and b.remark5='1' and funcflag ='17'"
             + getWherePart('transdate','StartDate','>=')
             + getWherePart('transdate','EndDate','<=')
             + getWherePart('b.ManageCom','ManageCom','like')
             + " and b.managecom  like '"+comCode+"%' "
             +  "order by b.managecom,a.transdate";*/
//prompt('',strSQL);
	mySql = new SqlClass();
	mySql.setResourceName("app.YBTDZQuerySql");
	mySql.setSqlId("YBTDZQuerySql1");
	mySql.addSubPara(fm.StartDate.value ); 
	mySql.addSubPara(fm.EndDate.value ); 
	mySql.addSubPara(fm.ManageCom.value ); 
	mySql.addSubPara(comCode ); 
	turnPage.queryModal(mySql.getString(), CodeGrid);    

}


