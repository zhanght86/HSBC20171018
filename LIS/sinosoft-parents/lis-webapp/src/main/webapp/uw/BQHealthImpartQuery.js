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
  
//  var aSQL="select edorno,proposalcontno,contno,PolApplyDate,state from ("
//  		  +"select '' edorno,a.proposalcontno,a.contno,a.PolApplyDate,"
//          +" (case trim(appflag) when '0' else '未承保' when '1' then '有效' when '4' then '终止' end) state"
//				  +" from lccont a where a.contno in (select distinct contno from lccustomerimpart where customerno='"+customerNo+"' and ImpartVer in('101','A01','B01','C01','D01','A05','A06'))"
//				  +" and not exists (select contno from lpcont where contno=a.contno ) "
//		  +" union all "
//		  +"select a.edorno,a.proposalcontno,a.contno,a.PolApplyDate,"
//          +" (case trim(appflag) when '1' then '有效' when '4' then '终止' end) state"
//				  +" from lpcont a where a.contno in (select distinct contno from("
//				  + " select contno from lccustomerimpart where customerno='"+customerNo+"' and ImpartVer in('101','A01','B01','C01','D01','A05','A06')"
//				  + " union all select contno from lpcustomerimpart where customerno='"+customerNo+"' and ImpartVer in('101','A01','B01','C01','D01','A05','A06')))"
//		  +")";	
//  //alert(aSQL);
  
    var sqlid1="BQHealthImpartQuerySql1";                              
	var mySql1=new SqlClass();                                            
	mySql1.setResourceName('uw.BQHealthImpartQuerySql'); //指定使用的properties文件名         
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id                                
	mySql1.addSubPara(customerNo);//传入参数                             
	mySql1.addSubPara(customerNo);//传入参数                                      
	mySql1.addSubPara(customerNo);//传入参数                                                   
	var aSQL = mySql1.getString();   
  
  turnPage.queryModal(aSQL, ContGrid);

}

function queryPersonInfo(){
//  var aSQL = "select a.customerno,a.name from ldperson a where a.customerno='"+customerNo+"'";	
  
    var sqlid2="BQHealthImpartQuerySql2";                              
	var mySql2=new SqlClass();                                            
	mySql2.setResourceName('uw.BQHealthImpartQuerySql'); //指定使用的properties文件名         
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id                                
	mySql2.addSubPara(customerNo);//传入参数                                                                                
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
  var tEdorNo = ContGrid.getRowColData(tSel - 1,1); 
  var tProposalContNo = ContGrid.getRowColData(tSel - 1,1);        //合同投保单号
  var tContNo = ContGrid.getRowColData(tSel - 1,3); 
	
//  var aSQL="select impartver,impartcode,impartcontent,ImpartParamModle from lccustomerimpart where customerno='"+customerNo+"' and contno='"+tContNo+"' and ImpartVer in('101','A01','B01','C01','D01','A05','A06')"
//           + " union all "
//           + "select impartver,impartcode,impartcontent,ImpartParamModle from lpcustomerimpart where customerno='"+customerNo+"' and edorno='"+tEdorNo+"' and contno='"+tContNo+"' and ImpartVer in('101','A01','B01','C01','D01','A05','A06')";	
 
    var sqlid3="BQHealthImpartQuerySql3";                              
	var mySql3=new SqlClass();                                            
	mySql3.setResourceName('uw.BQHealthImpartQuerySql'); //指定使用的properties文件名         
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id                                
	mySql3.addSubPara(customerNo);//传入参数     
	mySql3.addSubPara(tContNo);//传入参数     
	mySql3.addSubPara(customerNo);//传入参数     
	mySql3.addSubPara(tEdorNo);//传入参数     
	mySql3.addSubPara(tContNo);//传入参数     
	var aSQL = mySql3.getString();   

  
  turnPage.queryModal(aSQL, ImpartGrid);
	
}


