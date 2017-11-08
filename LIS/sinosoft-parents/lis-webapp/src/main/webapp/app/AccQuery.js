//
//程序名称：AccQuery.js
//程序功能：账户数据查询
//创建日期：2008-03-17 15:10
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容

//var arrDataSet;
var turnPage = new turnPageClass();

var tResourceName="app.AccQuerySql";
var tResourceSQL1="AccQuerySql1";

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
	
	// 书写SQL语句
	//var strSQL = "select  bankaccno,accname"
  //           + " from lccont"
  //           + " where bankaccno is not null"
  //           + getWherePart('signdate','StartDate','>=')
  //           + getWherePart('signdate','EndDate','<=')
  //           + getWherePart('ManageCom','ManageCom','like')
  //           + getWherePart('bankcode','BankCode');  
  //
  var strSQL  = wrapSql(tResourceName,tResourceSQL1,[fm.StartDate.value,fm.EndDate.value,fm.ManageCom.value,fm.BankCode.value]);              
      
	turnPage.queryModal(strSQL, CodeGrid);
}


