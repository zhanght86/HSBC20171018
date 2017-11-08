//***********************************************
//程序名称：ClaimQueryInit.js
//程序功能：既网理赔查询
//创建日期：2005-06-01 11:10:36
//创建人  ：HL
//更新记录：  更新人    更新日期     更新原因/内容
//***********************************************

//               该文件中包含客户端需要处理的函数和事件 


//全局变量
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();

var sqlresourcename = "uwgrp.ClaimQueryInitSql";

/*********************************************************************
 *  投保单复核的提交后的操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
//function afterSubmit( FlagStr, content )
//{
//	if (FlagStr == "Fail" )
//	{             
//  	showInfo.close();  
//		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
//
//	}
//	if (FlagStr == "Succ")
//	{
//  	showInfo.close();  
//		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
//
//	}
//	
//}
//


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
 *  查询已承保保单
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function queryClaim(){
  //alert(111);
 // var aSQL="select caseno,customername,endcasedate from llcase where customerno='"+customerNo+"'";

var sqlid1="ClaimQueryInitSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(customerNo);

 //alert(aSQL);
  var arrResult = easyExecSql(mySql1.getString());
  //fm.CustomerNo.value=aSQL;
  if(arrResult!=null)
  {
  	var clmno = arrResult[0][0];
  }
  /*
  var sql="select rgtno,rgtantname,rgtdate,(case when clmstate='20' then '立案' when clmstate='30' then '审核' when clmstate='40' then '审批' when clmstate='50' then '结案' when clmstate='60' then '完成' when clmstate='70' then '关闭' end) from llregister where rgtno='"+clmno
  	+"' union select rptno,rptorname,rptdate,'报案' from llreport where rptno='"+clmno+"'";
  */
  var sqlid2="ClaimQueryInitSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(clmno);
		mySql2.addSubPara(clmno);
  
  //alert(sql);
  
//            "select a.contno,a.appntname,a.CValiDate,a.state,"
//         +" (case when exists(select 'X' from LCIssuePol where contno=a.contno)"
//         +" then '有'	else '无'"
//			  +" end),"
//			  +" (case when exists(select 'X' from LCPENotice where contno=a.contno)"
//			  +" then '有' else '无'"
//			  +" end),"
//			  +" (case when exists(select 'X' from LCCustomerImpart where contno=a.contno) "
//			  +" then '有' else '无'"
//			  +" end)"
//			  +" from lccont a,lcinsured b where b.insuredno='"+customerNo+"' and a.contno=b.contno and a.appflag='1'";	
// 
  turnPage.queryModal(mySql2.getString(), ClaimGrid);

}

 

function queryPersonInfo(){
  //var aSQL = "select a.customerno,a.name from ldperson a where a.customerno='"+customerNo+"'";	
  
  ar sqlid3="ClaimQueryInitSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(customerNo);
  
  var arrResult = easyExecSql(mySql3.getString());
  fm.all('CustomerNo').value = arrResult[0][0];
  fm.all('CustomerName').value = arrResult[0][1];
}


function getPolInfo(){
	
  var tSelNo = ClaimGrid.getSelNo()-1;
  var tClmNo= ClaimGrid.getRowColData(tSelNo,1);
  var tClmState= ClaimGrid.getRowColData(tSelNo,4);
  //alert("tSelNo="+tSelNo);
  //alert("tClmNo="+tClmNo);
  //alert("tClmState="+tClmState);
  if(tClmState=="报案")
  {
  	alert("该赔案无理赔信息！");
  	
  }
  else
  {
	//var aSQL = "select unique a.clmno,a.polno,(select b.riskname from lmrisk b where trim(a.riskcode)=trim(b.riskcode)),(select c.codename from ldcode c where c.codetype='llclaimconclusion' and trim(a.givetype)=trim(c.code)),a.realpay,a.operator from LLClaimPolicy a ,llregister b where a.clmno=b.rgtno and a.ClmNo='"+tClmNo+"'";
	
	var sqlid4="ClaimQueryInitSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename);
		mySql4.setSqlId(sqlid4);
		mySql4.addSubPara(tClmNo);
	var aSQL =mySql4.getString();
	//alert(aSQL);
  }
  turnPage2.queryModal(aSQL, PolGrid);
	
}

//影像资料查询
function showImage(){ 
	
	 var tsel=PolGrid.getSelNo()-1;
	 
	 //alert(ContNo); 
	 if(tsel<0){
     alert("请选择保单!");
     retutn;	 
   }
   var ContNo=PolGrid.getRowColData(tsel,1); 
	window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");								
    
} 