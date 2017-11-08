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
	if(tContType!="1"){
		
	var sqlid826115051="DSHomeContSql826115051";
var mySql826115051=new SqlClass();
mySql826115051.setResourceName("uw.RiskTraceSql");//指定使用的properties文件名
mySql826115051.setSqlId(sqlid826115051);//指定使用的Sql的id
mySql826115051.addSubPara(PolNo);//指定传入的参数
var aSQL=mySql826115051.getString();	
	
//  var aSQL=" select a.polno,a.operator,a.makedate,a.passflag,case a.passflag when '5' then '自核不通过' when 'z' then '核保订正' else (select b.codename from ldcode b where b.codetype='uwstate' and b.code=a.PassFlag) end,a.changepolreason from LCUWSub a where a.polno='"+PolNo+"'"
//           
//           + " and a.autouwflag = '2' order by uwno/1" ;    
	}else{
		var sqlid826140634="DSHomeContSql826140634";
var mySql826140634=new SqlClass();
mySql826140634.setResourceName("uw.RiskTraceSql");//指定使用的properties文件名
mySql826140634.setSqlId(sqlid826140634);//指定使用的Sql的id
mySql826140634.addSubPara(tProposalNo);//指定传入的参数
var aSQL=mySql826140634.getString();
		
//		var aSQL=" select a.polno,a.operator,a.makedate,a.passflag,case a.passflag when '5' then '自核不通过' when 'z' then '核保订正' else (select b.codename from ldcode b where b.codetype='uwstate' and b.code=a.PassFlag) end,a.changepolreason from LCUWSub a where a.proposalno='"+tProposalNo+"'"
//        
////        + " and a.autouwflag = '2'"
//        + " order by uwno/1" ;    
	}
// prompt('',aSQL);
  turnPage.queryModal(aSQL, RiskTraceGrid);
 
}  
 