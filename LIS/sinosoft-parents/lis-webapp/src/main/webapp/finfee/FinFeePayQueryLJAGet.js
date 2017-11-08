var turnPage = new turnPageClass();       //使用翻页功能，必须建立为全局变量
var tResourceName="finfee.FinFeePayQueryLJAGetSql";
/**
 * 后台数据库查询
 */
function easyQueryClick() {
  // 拼SQL语句，从页面采集信息
  /*var strSql = "select ActuGetNo, OtherNo, OtherNoType,PayMode,Currency,SumGetMoney,EnterAccDate,Drawer,DrawerID,bankcode,bankaccno,accname from LJAGet where 1=1 and ConfDate is null and EnterAccDate is null and (bankonthewayflag='0' or bankonthewayflag is null) and managecom like '"+manageCom+"%%' "
	           + getWherePart( 'ActuGetNo' )
	           + getWherePart( 'OtherNo' )
	           + getWherePart( 'PayMode' );*/
  var strSql = wrapSql(tResourceName,"querysqldes1",[manageCom,document.all('ActuGetNo').value,document.all('OtherNo').value,document.all('PayMode').value]);
  //alert(strSql);	           
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {  
    //清空MULTILINE，使用方法见MULTILINE使用说明 
    QueryLJAGetGrid.clearData('QueryLJAGetGrid');  
    alert("没有查询到数据！");
    return false;
  }
  
  //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = QueryLJAGetGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSql; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  //控制是否显示翻页按钮
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
  
}

// 数据返回父窗口
function returnParent()
{
	var arrReturn = new Array();
	var tSel = QueryLJAGetGrid.getSelNo();
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		try
		{
			//alert(1);
	// alert(QueryLJAGetGrid.getRowColData(tSel-1,1));
	 top.opener.QueryLJAGetGrid.addOne("QueryLJAGetGrid");
     top.opener.QueryLJAGetGrid.setRowColData(0,1,QueryLJAGetGrid.getRowColData(tSel-1,1));
     top.opener.QueryLJAGetGrid.setRowColData(0,2,QueryLJAGetGrid.getRowColData(tSel-1,2));
     top.opener.QueryLJAGetGrid.setRowColData(0,3,QueryLJAGetGrid.getRowColData(tSel-1,3));
     top.opener.QueryLJAGetGrid.setRowColData(0,4,QueryLJAGetGrid.getRowColData(tSel-1,4));
     top.opener.QueryLJAGetGrid.setRowColData(0,5,QueryLJAGetGrid.getRowColData(tSel-1,5));
     top.opener.QueryLJAGetGrid.setRowColData(0,6,QueryLJAGetGrid.getRowColData(tSel-1,6));
     top.opener.QueryLJAGetGrid.setRowColData(0,7,QueryLJAGetGrid.getRowColData(tSel-1,7));
     top.opener.QueryLJAGetGrid.setRowColData(0,8,QueryLJAGetGrid.getRowColData(tSel-1,8));
     top.opener.QueryLJAGetGrid.setRowColData(0,9,QueryLJAGetGrid.getRowColData(tSel-1,9));

     top.opener.fm.ActuGetNo.value=QueryLJAGetGrid.getRowColData(tSel-1,1);
		 top.opener.document.all('ActuGetNo').value=QueryLJAGetGrid.getRowColData(tSel-1,1);
		 top.opener.fmSave.PolNo.value=QueryLJAGetGrid.getRowColData(tSel-1,2);
		 top.opener.fmSave.PayMode.value=QueryLJAGetGrid.getRowColData(tSel-1,4);
top.opener.fmSave.Currency.value=QueryLJAGetGrid.getRowColData(tSel-1,5);
		 top.opener.fmSave.GetMoney.value=QueryLJAGetGrid.getRowColData(tSel-1,6);
		 top.opener.fmSave.EnterAccDate.value=QueryLJAGetGrid.getRowColData(tSel-1,7);
	//	top.opener.fmSave.ConfDate.value=QueryLJAGetGrid.getRowColData(tSel-1,8); 
	    var mPayMode = QueryLJAGetGrid.getRowColData(tSel-1,4);
     if(mPayMode=="4")
     {
	    top.opener.fmSave.BankCode.value=QueryLJAGetGrid.getRowColData(tSel-1,10);
	    top.opener.fmSave.BankAccNo.value=QueryLJAGetGrid.getRowColData(tSel-1,11);
	    top.opener.fmSave.BankAccName.value=QueryLJAGetGrid.getRowColData(tSel-1,12);
     }
    else if(mPayMode=="2"|| mPayMode=="3")
    {
    	top.opener.fmSave.BankCode2.value=QueryLJAGetGrid.getRowColData(tSel-1,10);
    	top.opener.fmSave.ChequeNo.value=QueryLJAGetGrid.getRowColData(tSel-1,11);
    }
    else
    {
    	top.opener.fmSave.BankCode.value="";
	    top.opener.fmSave.BankAccNo.value="";
	    top.opener.fmSave.BankAccName.value="";
    	top.opener.fmSave.BankCode2.value="";
    	top.opener.fmSave.ChequeNo.value="";
    }                 
     top.opener.showBankAccNo();

		}
		catch(ex)
		{
			alert( "没有发现父窗口的接口" + ex );
		}
		top.opener.queryLJAGet();
		top.close();
	}
}