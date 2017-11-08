var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();

//LLClaimUserGrid的响应函数
function NBUserGridClick()
{
	return true;
}

//“查询”按钮
function queryClick()
{       
	/*if (trim(fm.UserCode.value) == "" || fm.UserCode.value == null)
	{
	    alert("请先录入用户代码，再点击查询按钮!");
	    fm.UserCode.focus();
	    return ;
	}*/
//	var strSQL="select usercode,username,comcode from lduser lu "
//                 + "where ComCode like '" + document.all('ComCode').value + "%%'"
//          //       + " and uwpopedom is null"
//                 + getWherePart( 'UserCode','UserCode')
//				 + getWherePart( 'UserName','UserName')
//				 + getWherePart( 'ComCode','ManageCom')
//				 + " and usercode in(select code from ldcode where codetype='appalluser')";
//				 + " order by UserCode";
				 
	var  UserCode = window.document.getElementsByName(trim("UserCode"))[0].value;
	var  UserName = window.document.getElementsByName(trim("UserName"))[0].value;
	var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value;
    var sqlid1="NBUserQuerySql0";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.NBUserQuerySql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(document.all('ComCode').value);//指定传入的参数
	mySql1.addSubPara(UserCode);//指定传入的参数
	mySql1.addSubPara(UserName);//指定传入的参数
	mySql1.addSubPara(ManageCom);//指定传入的参数
	var strSQL=mySql1.getString();
	
	turnPage.pageLineNum=10;    //每页10条
    turnPage.queryModal(strSQL, NBUserGrid);
}

// 数据返回父窗口
function returnParent()
{
	var arrReturn = new Array();
	try
	{
		//获取正确的行号
		var tSelRow = NBUserGrid.getSelNo() - 1;
		if( tSelRow < 0 || tSelRow == null )
		{
			alert( "请先选择一条记录，再点击返回按钮!" );
			return;
		}
		arrReturn[0] = NBUserGrid.getRowColData(tSelRow,1);
		arrReturn[1] = NBUserGrid.getRowColData(tSelRow,2);
		top.opener.afterQuery( arrReturn );
		top.close();
	}
	catch(ex)
	{
		alert( "没有发现父窗口的afterQuery接口" + ex );
	}
		
}



