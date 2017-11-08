var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();

//LLClaimUserGrid的响应函数
function LLClaimUserGridClick()
{
	//var i = LLClaimUserGrid.getSelNo();
    //i = i - 1;
    //fm.ContNo.value=LLClaimUserGrid.getRowColData(i,2);//合同号
	return true;
}


//“查询”按钮
function queryClick()
{       
	var strSQL="select UserCode,UserName,ComCode,ReportFlag,RegisterFlag,PrepayFlag,CheckFlag,CheckLevel,UWFlag,UWLevel,SimpleFlag,1,StateFlag from llclaimuser where 1=1 "
				 + getWherePart( 'UserCode','UserCodeQ')
				 + getWherePart( 'UserName','UserNameQ')
				 + getWherePart( 'ComCode','ComCodeQ')				 
				 + " order by UserCode";
	turnPage.pageLineNum=10;    //每页10条
    turnPage.queryModal(strSQL, LLClaimUserGrid);
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
		top.opener.afterQuery( arrReturn );
		top.close();
	}
	catch(ex)
	{
		alert( "没有发现父窗口的afterQuery接口" + ex );
	}
		
}



