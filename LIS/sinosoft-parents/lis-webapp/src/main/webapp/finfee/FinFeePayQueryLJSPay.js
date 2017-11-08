var turnPage = new turnPageClass();       //使用翻页功能，必须建立为全局变量
var tResourceName="finfee.FinFeeGetQueryLJSPaySql";
/**
 * 后台数据库查询
 */
function easyQueryClick() {
  // 拼SQL语句，从页面采集信息
  /*var strSql = "select getnoticeno, OtherNo, OtherNoType,SumDuePayMoney,AgentCode,RiskCode,StartPayDate from LJSPay where 1=1 and othernotype='2' "
	           + getWherePart( 'GetNoticeNo' )
	           + getWherePart( 'OtherNo' )
	           + getWherePart( 'RiskCode' )
	           ;
 if (fm.AppntName.value!="")
 {
 	strSql=strSql+" and appntno in(select customerno from ldperson where name='"+fm.AppntName.value+"')";
 }	      
 if (fm.InsuredName.value!="")
 {
 	strSql=strSql+" and OtherNo in (select polno from lcpol where InsuredName='"+fm.InsuredName.value+"')";
 }	    */
   var strSql = wrapSql(tResourceName,"querysqldes1",[document.all('GetNoticeNo').value,document.all('OtherNo').value,document.all('RiskCode').value
   									,document.all('AppntName').value,document.all('InsuredName').value]);     
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  

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
		top.opener.document.all('InputNo4').value=QueryLJAGetGrid.getRowColData(tSel-1,1);
		top.opener.document.all('InputNo3').value=QueryLJAGetGrid.getRowColData(tSel-1,2);
		}
		catch(ex)
		{
			alert( "没有发现父窗口的接口" + ex );
		}
		top.close();
	}
}