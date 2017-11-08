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
  
  var sqlid826141102="DSHomeContSql826141102";
var mySql826141102=new SqlClass();
mySql826141102.setResourceName("uw.GrpContTraceSql");//指定使用的properties文件名
mySql826141102.setSqlId(sqlid826141102);//指定使用的Sql的id
mySql826141102.addSubPara(ContNo);//指定传入的参数
var aSQL=mySql826141102.getString();
  
//  var aSQL="select 1,a.operator,a.makedate,a.passflag,case passflag when '5' then '自核不通过' when 'z' then '核保订正' else (select b.codename from ldcode b where b.codetype='uwstate' and b.code=a.PassFlag) end,uwidea from LCCUWSub a where a.grpcontno='"+ContNo+"'  and a.passflag is not null "

  //alert(aSQL);
  turnPage.queryModal(aSQL, ContTraceGrid);

}
