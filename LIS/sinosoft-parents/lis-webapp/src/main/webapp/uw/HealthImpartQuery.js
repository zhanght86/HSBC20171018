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
  
//  var aSQL="select a.proposalcontno,a.contno,a.PolApplyDate,"
//          +" (case when a.appflag='0'"
//          +" then '未承保' else '已承保'"
//				  +" end)"
//				  +" from lccont a where a.contno in (select distinct contno from lccustomerimpart where customerno='"+customerNo+"')";	
// // alert(aSQL);
  
     var sqlid1="HealthImpartQuerySql1";
	 var mySql1=new SqlClass();
	 mySql1.setResourceName("uw.HealthImpartQuerySql");
	 mySql1.setSqlId(sqlid1);//指定使用SQL的id
	 mySql1.addSubPara(customerNo);//指定传入参数
	 var aSQL = mySql1.getString();
  
  turnPage.queryModal(aSQL, ContGrid);

}



function queryPersonInfo(){
//  var aSQL = "select a.customerno,a.name from ldperson a where a.customerno='"+customerNo+"'";
  
     var sqlid2="HealthImpartQuerySql2";
	 var mySql2=new SqlClass();
	 mySql2.setResourceName("uw.HealthImpartQuerySql");
	 mySql2.setSqlId(sqlid2);//指定使用SQL的id
	 mySql2.addSubPara(customerNo);//指定传入参数
	 var aSQL = mySql2.getString();
  
  var arrResult = easyExecSql(aSQL);
  document.all('CustomerNo').value = arrResult[0][0];
  document.all('CustomerName').value = arrResult[0][1];
}

function getContDetail(){
	//alert();
	
}



function contInfoClick(){
  
  var tSel = ContGrid.getSelNo();
  var tProposalContNo = ContGrid.getRowColData(tSel - 1,1);        //合同投保单号
	
//  var aSQL="select impartver,impartcode,impartcontent,ImpartParamModle from lccustomerimpart where customerno='"+customerNo+"' and proposalcontno='"+tProposalContNo+"' and ImpartVer in('101','A01','B01','C01','D01','A05','A06','B05')";	
 
     var sqlid3="HealthImpartQuerySql3";
	 var mySql3=new SqlClass();
	 mySql3.setResourceName("uw.HealthImpartQuerySql");
	 mySql3.setSqlId(sqlid3);//指定使用SQL的id
	 mySql3.addSubPara(customerNo);//指定传入参数
	 mySql3.addSubPara(tProposalContNo);//指定传入参数
	 var aSQL = mySql3.getString();
  
  turnPage.queryModal(aSQL, ImpartGrid);
	
}


