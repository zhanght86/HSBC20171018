//***********************************************
//程序名称：HealthImpartQuery.js
//程序功能：健康告知查询
//创建日期：2005-06-18 11:10:36
//创建人  ：ccvip
//更新记录：  更新人    更新日期     更新原因/内容
//***********************************************

//               该文件中包含客户端需要处理的函数和事件


//全局变量
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var sqlresourcename = "uwgrp.HealthImpartQuerySql";

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
 *  查询保单信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function queryCont(){
  /*
  var aSQL="select a.proposalcontno,a.contno,a.PolApplyDate,"
          +" (case when a.appflag='0'"
          +" then '未承保' else '已承保'"
				  +" end)"
				  +" from lccont a where a.contno in (select distinct contno from lccustomerimpart where customerno='"+customerNo+"')";	
 // alert(aSQL);
  */
  var sqlid1="HealthImpartQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(customerNo);
  
  
  turnPage.queryModal(mySql1.getString(), ContGrid);

}



function queryPersonInfo(){
 // var aSQL = "select a.customerno,a.name from ldperson a where a.customerno='"+customerNo+"'";	
  
    var sqlid2="HealthImpartQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(customerNo);
  
  var arrResult = easyExecSql(mySql2.getString());
  fm.all('CustomerNo').value = arrResult[0][0];
  fm.all('CustomerName').value = arrResult[0][1];
}

function getContDetail(){
	//alert();
	
}



function contInfoClick(){
  
  var tSel = ContGrid.getSelNo();
  var tProposalContNo = ContGrid.getRowColData(tSel - 1,1);        //合同投保单号
	
  //var aSQL="select impartver,impartcode,impartcontent,ImpartParamModle from lccustomerimpart where customerno='"+customerNo+"' and proposalcontno='"+tProposalContNo+"' and ImpartVer='02'";	
 
 var sqlid3="HealthImpartQuerySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(customerNo);
		mySql3.addSubPara(tProposalContNo);
 
  turnPage.queryModal(mySql3.getString(), ImpartGrid);
	
}


