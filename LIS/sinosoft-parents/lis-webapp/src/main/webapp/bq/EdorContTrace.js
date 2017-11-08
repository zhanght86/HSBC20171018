//***********************************************
//程序名称：EdorContTrace.js  
//程序功能：保全合同核保轨迹查询  
//创建日期：2005-07-13 10:10:36
//创建人  ：liurx
//更新记录：  更新人    更新日期     更新原因/内容
//***********************************************

//               该文件中包含客户端需要处理的函数和事件


//全局变量
var showInfo;
var turnPage = new turnPageClass();

 
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

function queryContTrace()
{
  
//  var strSql = "select 1,a.operator,a.makedate,a.passflag,"
//                 +"(select b.codename from ldcode b where b.codetype='edorcontuwstate' and trim(b.code)=trim(a.PassFlag) ) "
//                 +"from LPCUWSub a where a.contno='"+ContNo+"' "
//                 +"and a.EdorNo = '"+EdorNo+"' and a.autouwflag = '2' order by makedate ";
//  
    var sqlid1="EdorContTraceSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.EdorContTraceSql");
	mySql1.setSqlId(sqlid1); //指定使用SQL的id
	mySql1.addSubPara(ContNo);//指定传入参数
	mySql1.addSubPara(EdorNo);
	var strSql = mySql1.getString();

  var brr=easyExecSql(strSql);
  if(brr)
  {
    turnPage.queryModal(strSql, ContTraceGrid);
  }
  else
  {
  	alert("该保单尚未下过核保结论！");
  	return ;
  }
}   
