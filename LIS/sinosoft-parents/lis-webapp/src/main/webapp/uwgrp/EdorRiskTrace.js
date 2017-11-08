//***********************************************
//程序名称：RiskTrace.js
//程序功能：险种核保轨迹查询 
//创建日期：2005-06-27 11:10:36
//创建人  ：ccvip
//更新记录：  更新人    更新日期     更新原因/内容
//***********************************************

//               该文件中包含客户端需要处理的函数和事件


//全局变量
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var sqlresourcename = "uwgrp.EdorRiskTraceSql";
 
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

function queryRiskTrace(){
 // alert(PolNo); 
 // var aSQL="select a.polno,a.operator,a.makedate,a.passflag,(select b.codename from ldcode b where b.codetype='uwstate' and trim(b.code)=trim(a.PassFlag) )from LPUWSub a where a.polno='"+PolNo+"'Union select c.polno,c.operator,c.makedate,c.passflag,(select d.codename from ldcode d where d.codetype='uwstate' and trim(d.code)=trim(c.PassFlag) )from LPUWMaster c where c.polno='"+PolNo+"'";	     


		var sqlid1="EdorRiskTraceSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(PolNo);
		mySql1.addSubPara(PolNo);
   
  turnPage.queryModal(mySql1.getString(), RiskTraceGrid);
 
}  
 