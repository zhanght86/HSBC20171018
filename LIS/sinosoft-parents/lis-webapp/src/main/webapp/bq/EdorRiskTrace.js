//***********************************************
//程序名称：EdorRiskTrace.js
//程序功能：险种核保轨迹查询 
//创建日期：2005-07-13 11:10:36
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

function queryRiskTrace()
{
  
  var strSql = "   
//  select a.polno,a.operator,a.makedate,a.passflag,"
//                 +"(select b.codename from ldcode b where b.codetype='edoruwstate' and trim(b.code)=trim(a.PassFlag) ) "
//                 +"from LPUWSub a where a.polno='"+PolNo+"' "
//                 +"and a.EdorNo = '"+EdorNo+"' and a.autouwflag = '2' order by makedate ";	  
  var strSql = "";
  var sqlid1="EdorRiskTraceSql1";
  var mySql1=new SqlClass();
  mySql1.setResourceName("bq.EdorRiskTraceSql"); //指定使用的properties文件名
  mySql1.setSqlId(sqlid1);//指定使用的Sql的id
  mySql1.addSubPara(PolNo);//指定传入的参数
  mySql1.addSubPara(EdorNo);//指定传入的参数
  strSql=mySql1.getString();
  var brr=easyExecSql(strSql);
  if(brr)
  {
    turnPage.queryModal(strSql, RiskTraceGrid);
  }
  else
  {
  	alert("该险种尚未下过核保结论！");
  	return ;
  }
 
}  
 