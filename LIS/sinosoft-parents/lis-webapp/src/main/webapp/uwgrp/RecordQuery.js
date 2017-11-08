//***********************************************
//程序名称：RecordQuery.js
//程序功能：操作履历查询
//创建日期：2005-06-23 11:10:36
//创建人  ：ccvip
//更新记录：  更新人    更新日期     更新原因/内容
//***********************************************

//               该文件中包含客户端需要处理的函数和事件


//全局变量
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();

var sqlresourcename = "uwgrp.RecordQuerySql";
/*********************************************************************
 *  返回上一页
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function returnParent(){
  top.close();	
	
}


/*********************************************************************
 *  查询操作履历信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function queryRecord(){
  //alert(ContNo); 
  //var aSQL="select a.contno,a.operator,a.makedate, 1 ,(select c.codename from ldcode c where c.codetype='busitype' and trim(c.code)=trim(a.businesstype) )from ldconttime a where a.contno='"+ContNo+"' order by serialno";	
  //alert(aSQL);
  var sqlid1="RecordQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(ContNo);
		
  turnPage.queryModal(mySql1.getString(), RecordGrid);

}



//function queryPersonInfo(){
//  var aSQL = "select a.customerno,a.name from ldperson a where a.customerno='"+customerNo+"'";	
//  var arrResult = easyExecSql(aSQL);
//  fm.all('CustomerNo').value = arrResult[0][0];
//  fm.all('CustomerName').value = arrResult[0][1];
//}

//function getContDetail(){
//	//alert();
//	
//}



//function contInfoClick(){
//  
//  var tSel = ContGrid.getSelNo();
//  var tProposalContNo = ContGrid.getRowColData(tSel - 1,1);        //合同投保单号
//	
//  var aSQL="select impartver,impartcode,impartcontent,ImpartParamModle from lccustomerimpart where customerno='"+customerNo+"' and proposalcontno='"+tProposalContNo+"'";	
// 
//  turnPage.queryModal(aSQL, ImpartGrid);
//	
//}


