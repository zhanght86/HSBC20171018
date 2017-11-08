//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage=new turnPageClass();
var mySql = new SqlClass();

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
    initForm();
  }
  catch(re)
  {
    alert("在Proposal.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 

//取消按钮对应操作
function cancelForm()
{
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
}
 
//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作  
}           



function getQueryResult()
{
  var arrSelected = null;
  tRow = GrpContGrid.getSelNo();

  if( tRow == 0 || tRow == null )
      return arrSelected;
  
  arrSelected = new Array();
  
  //设置需要返回的数组
  //edit by guo xiang at 2004-9-13 17:54
  arrSelected[0] = new Array();
  arrSelected[0] = GrpContGrid.getRowData(tRow-1);
  //arrSelected[0] = arrDataSet[tRow-1];
  
  return arrSelected;
}

function initQuery()
{

  /*var strSQL=" select  a.customerno, a.name, g.grpcontno,g.Peoples2 " +
        " from lcgrpcont g, LCGrpAppnt a " +
        " where a.grpcontno = g.grpcontno and g.appflag = '1' "
        + getWherePart("g.AppntNo", "CustomerNo" )
//        + getWherePart("a.Name", "GrpName" )
        + getWherePart( "g.GrpContNo","GrpContNo" );*/
       mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LCGrpQuerySql");
		mySql.setSqlId("LCGrpQuerySql1");
		mySql.addSubPara(fm.CustomerNo.value );  
		mySql.addSubPara(fm.GrpContNo.value );  
      if(fm.all('GrpName').value!=''){
      //   strSQL += " and a.Name like '%%"+fm.all('GrpName').value+"%%'";
         mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LCGrpQuerySql");
		mySql.setSqlId("LCGrpQuerySql2");
		mySql.addSubPara(fm.CustomerNo.value );  
		mySql.addSubPara(fm.GrpContNo.value );
		mySql.addSubPara(fm.all('GrpName').value);  
         
      }
        
  turnPage.queryModal(mySql.getString(), GrpContGrid);
}

// 数据返回父窗口
function returnParent()
{
  var arrReturn = new Array();
  var tSel = GrpContGrid.getSelNo();
  
  if( tSel == 0 || tSel == null )
    alert( "请先选择一条记录，再点击返回按钮。" );
  else
  {
    try
    {
      arrReturn = getQueryResult();
      top.opener.afterLCGrpQuery( arrReturn );
    }
    catch(ex)
    {
      alert( "没有发现父窗口的afterLCGrpQuery接口。" + ex.message );
    }
    top.close();
  }
}