//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass(); 

var tResourceName="get.PayPlanCancelQuerySql";
var tResourceSQL1="PayPlanCancelQuerySql1";

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
    parent.fraMain.rows = "0,0,0,0,*";
  }
  else 
  {
    parent.fraMain.rows = "0,0,0,82,*";
  }
  parent.fraMain.rows = "0,0,0,0,*";
}


// 查询按钮
function easyQueryClick()
{
  // 初始化表格
 if(trim(fm.GrpContNo.value)==""&&trim(fm.ContNo.value)==""){
 		alert("请录入团单号或个人保单号!");
 		return;
 }
  initLFGetCancelLogGrid();
  divCancelContent.style.display = 'none';   //显示确认按扭  
  
  divCancelLog.style.display ='';
	
  // 书写SQL语句
  var strSQL = "";	
  //strSQL = "select b.grpcontno,a.contno,b.name,a.oldgetdate,a.oldgetmoney,a.oldoperator,a.oldmakedate,a.operator,a.makedate,a.remark,a.maketime "
  //       + "from lclfgetcancellog a,lcinsured b "
  //       + "where a.contno=b.contno and a.managecom like '"+manageCom+"%%'"
  //       + getWherePart( 'b.GrpContNo','GrpContNo' )
  //       + getWherePart( 'a.ContNo','ContNo' )
  //       + getWherePart( 'a.oldgetdate','GetDate' )
  //       + " order by makedate";
  //
	strSQL = wrapSql(tResourceName,tResourceSQL1,[fm.ManageCom.value,fm.GrpContNo.value,fm.ContNo.value,fm.GetDate.value]); 
	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("查询失败,没有符合条件的数据!");
    divCancelLog.style.display ='none';
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = LFGetCancelLogGrid;
          
  //保存SQL语句
  turnPage.strQuerySql = strSQL;
  
  //设置查询起始位置
  turnPage.pageIndex = 0;

  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  arrGrid = arrDataSet;
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}