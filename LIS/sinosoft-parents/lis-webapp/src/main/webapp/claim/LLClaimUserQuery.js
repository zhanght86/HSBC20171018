var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();

//LLClaimUserGrid的响应函数
function LLClaimUserGridClick()
{
	//var i = LLClaimUserGrid.getSelNo();
    //i = i - 1;
    //fm.ContNo.value=LLClaimUserGrid.getRowColData(i,2);//合同号
	//fm.ComCode.value=LLClaimUserGrid.getRowColData(i,3);
	return true;
}

//“查询”按钮
function queryClick()
{       
	/*var strSQL="select UserCode,UserName,ComCode,ReportFlag,RegisterFlag,PrepayFlag,CheckFlag,CheckLevel,UWFlag,UWLevel,SimpleFlag,1,StateFlag from llclaimuser where 1=1 "
				 + getWherePart( 'UserCode','UserCodeQ')
				 + getWherePart( 'UserName','UserNameQ')
				 + getWherePart( 'ComCode','ComCodeQ')	
				 + " and ComCode like '" + document.all('ComCode').value + "%%'"			 
				 + " order by UserCode";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimUserQueryInputSql");
	mySql.setSqlId("LLClaimUserQuerySql1");
	mySql.addSubPara(fm.UserCodeQ.value ); 
	mySql.addSubPara(fm.UserNameQ.value ); 
	mySql.addSubPara(fm.ComCodeQ.value ); 
	mySql.addSubPara(document.all('ComCode').value ); 
	turnPage.pageLineNum=10;    //每页10条
    turnPage.queryModal(mySql.getString(), LLClaimUserGrid);
}

// 数据返回父窗口
function returnParent()
{
	var arrReturn = new Array();
	try
	{
//		arrReturn = getQueryResult();
		//获取正确的行号
		var tRow = LLClaimUserGrid.getSelNo();
		if( tRow < 1||tRow == null )
		{
			alert( "请先选择一条记录，再点击返回按钮!" );
			return;
		}
		arrReturn[0]= LLClaimUserGrid.getRowColData(tRow-1,1);
		arrReturn[1]= LLClaimUserGrid.getRowColData(tRow-1,3);
		top.opener.afterQuery( arrReturn );
		top.close();
	}
	catch(ex)
	{
		alert( "没有发现父窗口的afterQuery接口" + ex );
	}
		
}



