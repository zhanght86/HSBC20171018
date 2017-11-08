//
//程序名称：CBCDZQuery1.js
//程序功能：未录入数据查询
//创建日期：2008-07-02 15:10
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
			alert("开始时间和结束时间不能为空!");
			return false;
	}
	
	initCodeGrid();
	
	var comcode=fm.ManageCom.value;
	var code=comcode.substr(2,2);
	//alert(code);
	// 书写SQL语句
	/*var strSQL = "select g.cardno,g.trandate,g.tranamnt,g.makedate,g.brno "
             + " from lis.lkbalancedetail g  "
             + " where g.bankcode='03'  and g.confirmflag='1'  "
             + getWherePart('trandate','StartDate','>=')
             + getWherePart('trandate','EndDate','<=')
             //+ getWherePart('bankzonecode','ManageCom','like')
             + " and bankzonecode like '"+code+"%' "
             +  "order by trandate";*/
        //alert(strSQL);    
//      prompt('',strSQL);
	mySql = new SqlClass();
	mySql.setResourceName("app.CBCDZQuerySql");
	mySql.setSqlId("CBCDZQuerySql1");
	mySql.addSubPara(fm.StartDate.value ); 
	mySql.addSubPara(fm.EndDate.value ); 
	mySql.addSubPara(code); 
	turnPage.queryModal(mySql.getString(), CodeGrid);    

}


