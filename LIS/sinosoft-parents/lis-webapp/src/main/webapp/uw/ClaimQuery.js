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
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";


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
	
	   var strSQL = "";
	   //contFlag="1"  保单核保查询界面调用
//	   alert(contFlag);
    if(contFlag=="1"){
		
		var sqlid1="ClaimQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.ClaimQuerySql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(contNo);//指定传入的参数
	    strSQL=mySql1.getString();	
		
//    	strSQL = " select a.rptno, c.name, b.MedAccDate, b.AccDate, (case (select clmstate from llregister where rgtno=a.rptno)"
//			+ " when '10' then '报案' when '20' then '立案' when '30' then"
//			+ " '审核' when '40' then '审批' when '50' then '结案' when '60' then"
//			+ " '完成' when '70' then '关闭' else '报案' end)"
//			+ " from llreport a, llsubreport b, ldperson c"
//			+ " where a.rptno = b.subrptno"
//			+ " and b.customerno = c.customerno"
//			+ " and exists ("
//			+ " select 1 from llclaimpolicy where contno='"+contNo+"' and clmno=a.rptno"
//			+ " )";
    	turnPage.queryModal(strSQL, ClaimGrid); //prompt("",strSQL);
    }else{
    	//09-11-11将此处SQL逻辑重新整理
		
		var sqlid2="ClaimQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.ClaimQuerySql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(customerNo);//指定传入的参数
	    strSQL=mySql2.getString();	
		
//    	strSQL = " select a.rptno, c.name, b.MedAccDate, b.AccDate, (case (select clmstate from llregister where rgtno=a.rptno)"
//    					+ " when '10' then '报案' when '20' then '立案' when '30' then"
//    					+ " '审核' when '40' then '审批' when '50' then '结案' when '60' then"
//    					+ " '完成' when '70' then '关闭' else '报案' end)"
//    					+ " from llreport a, llsubreport b, ldperson c"
//    					+ " where a.rptno = b.subrptno"
//    					+ " and b.customerno = c.customerno"
//    					+ " and b.customerno = '"+customerNo+"'";
    //查询立案以后信息
//    strSQL = "select a.rgtno,(select c.name from ldperson c where c.customerno = b.customerno),b.AccDate,(case a.clmstate when '10' then '报案' when '20' then '立案' when '30' then '审核' when '40' then '审批' when '50' then '结案' when '60' then '完成' when '70' then '关闭' end)"
//           + " from llregister a,llcase b,LLClaimPolicy c "
//           + " where a.rgtno = b.caseno "
//           + " and c.clmno = a.rgtno"
//           + " and c.contno = '"+contNo+"'" //出险人编码
//    strSQL = "select a.rgtno,(select c.name from ldperson c where c.customerno = b.customerno),b.MedAccDate,b.AccDate,(case a.clmstate when '10' then '报案' when '20' then '立案' when '30' then '审核' when '40' then '审批' when '50' then '结案' when '60' then '完成' when '70' then '关闭' end)"
//               + " from llregister a,llcase b "
//               + " where a.rgtno = b.caseno "
//               + " and b.customerno = '"+customerNo+"'" //出险人编码
//
//          
//    //联合查询报案信息
//    strSQL = strSQL + " union "
//           + "(select a.rptno,c.name,b.MedAccDate,b.AccDate,'报案'"
//           + " from llreport a,llsubreport b,ldperson c "
//           + " where a.rptno = b.subrptno and b.customerno = c.customerno and a.rgtflag = '10' "
//           + " and b.CustomerNo = '"+customerNo+"')" //出险人编码
    
    //prompt("被保险人理赔信息查询",strSQL);
//  var aSQL="select caseno,customername,endcasedate from llcase where customerno='"+customerNo+"'";
// //alert(aSQL);
//  var arrResult = easyExecSql(aSQL);
//  //fm.CustomerNo.value=aSQL;
//  if(arrResult!=null)
//  {
//  	var clmno = arrResult[0][0];
//  }
//  
//  var sql="select rgtno,rgtantname,rgtdate,(case when clmstate='20' then '立案' when clmstate='30' then '审核' when clmstate='40' then '审批' when clmstate='50' then '结案' when clmstate='60' then '完成' when clmstate='70' then '关闭' end) from llregister where rgtno='"+clmno
//  	+"' union select rptno,rptorname,rptdate,'报案' from llreport where rptno='"+clmno+"'";
//  //alert(sql);
//  
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
  turnPage.queryModal(strSQL, ClaimGrid); //prompt("",strSQL);
    }
}

 

function queryPersonInfo(){
	
			var sqlid3="ClaimQuerySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("uw.ClaimQuerySql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(customerNo);//指定传入的参数
	    var aSQL=mySql3.getString();	
	
//  var aSQL = "select a.customerno,a.name from ldperson a where a.customerno='"+customerNo+"'";	
  var arrResult = easyExecSql(aSQL);
  document.all('CustomerNo').value = arrResult[0][0];
  document.all('CustomerName').value = arrResult[0][1];
}
/*********************************************************************
 *  开始
 *  说明：将“既往赔案险种信息”列表中的“保单号”改为“合同号”
 *  修改人：  万泽辉
 *  时间： 2005-10-26
 *********************************************************************
 */

function getPolInfo(){
	
  var tSelNo = ClaimGrid.getSelNo()-1;
  var tClmNo= ClaimGrid.getRowColData(tSelNo,1);
  var tClmState= ClaimGrid.getRowColData(tSelNo,4);
  if(tClmState=="报案")
  {
  	var strUrl="../claim/LLClaimQueryReportInput.jsp?claimNo="+tClmNo+"&phase=0";
  	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
  }
  else
  {
  	
		var sqlid4="ClaimQuerySql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("uw.ClaimQuerySql"); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(tClmNo);//指定传入的参数
	    var aSQL=mySql4.getString();	
	
//	var aSQL = "select unique a.clmno,a.contno,(select b.riskname from lmrisk b where trim(a.riskcode)=trim(b.riskcode)),(select c.codename from ldcode c where c.codetype='llclaimconclusion' and trim(a.givetype)=trim(c.code)),a.realpay,a.operator from LLClaimPolicy a ,llregister b where a.clmno=b.rgtno and a.ClmNo='"+tClmNo+"'";
	turnPage2.queryModal(aSQL, PolGrid);
  }
  
	
}


//影像资料查询
function showImage()
{ 	
	var tsel=PolGrid.getSelNo()-1; 
	//alert(ContNo); 
	if(tsel<0)
	{
       alert("请选择保单!");
       return;	 
    }
    var ClmNo = PolGrid.getRowColData(tsel,1);//赔案号 
//  alert(ClmNo);
//	window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");	
//  Modify by zhaorx 2006-03-07							
    var strUrl="../claim/LLColQueryImageMain.jsp?claimNo="+ClmNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');    
} 

//既往理赔详细查询
function showDetail(){ 
	
	 var tsel=PolGrid.getSelNo()-1;
	if(tsel<0)
	{
    alert("请选择赔案!");
   	return;	 
  }
  var ClmNo=PolGrid.getRowColData(tsel,1); 
  if(ClmNo==null||ClmNo=="")
   {alert("没有赔案险种信息！")
   	return;
   	}
  //var ClmNo="90000013761";
else
	window.open("./ClaimDetailQueryMain.jsp?ClmNo="+ClmNo,"", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);								
    
} 

//理赔二核结论查询
function queryClaimUW(){
	 var tsel=PolGrid.getSelNo()-1;
	if(tsel<0)
	{
    alert("请选择赔案!");
   	return;	 
  }
  var ClmNo=PolGrid.getRowColData(tsel,1); 
  if(ClmNo==null||ClmNo=="")
   {alert("没有赔案险种信息！")
   	return;
   	}

  window.open("../claim/LLDealUWsecondMain.jsp?CaseNo="+ClmNo+"&InsuredNo="+fm.CustomerNo.value,"",sFeatures);	
	
}
