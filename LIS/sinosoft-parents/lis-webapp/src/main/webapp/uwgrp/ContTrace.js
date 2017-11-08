//***********************************************
//程序名称：ContTrace.js  
//程序功能：合同核保轨迹查询  
//创建日期：2005-06-27 11:10:36
//创建人  ：ccvip
//更新记录：  更新人    更新日期     更新原因/内容
//***********************************************

//               该文件中包含客户端需要处理的函数和事件


//全局变量
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var sqlresourcename = "uwgrp.ContTraceSql";
 
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
 *  查询险种核保轨迹
 *  参数  ：  无
 *  返回值：  无
 ********************************************************************* 
 */

function queryContTrace(){
  //alert("ContNo="+ContNo); 
  //var aSQL="select 1,a.operator,a.makedate,a.passflag,(select b.codename from ldcode b where b.codetype='contuwstate1' and trim(b.code)=trim(a.PassFlag) )from LCCUWSub a where a.contno='"+ContNo+"'Union select 1,c.operator,c.makedate,c.passflag,(select d.codename from ldcode d where d.codetype='contuwstate1' and trim(d.code)=trim(c.PassFlag) )from LCCUWMaster c where c.contno='"+ContNo+"'";	   
  //alert(aSQL);
  
  var sqlid1="ContTraceSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(ContNo);
		mySql1.addSubPara(ContNo);
		
  turnPage.queryModal(mySql1.getString(), ContTraceGrid);
 
}   
