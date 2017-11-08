//***********************************************
//程序名称：EdorErrorUWQuery.js
//程序功能：初次核保查询
//创建日期：2008-07-21 19:10:36
//创建人  ：pst
//更新记录：  更新人    更新日期     更新原因/内容
//***********************************************

//               该文件中包含客户端需要处理的函数和事件


//全局变量
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();

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
 *  查询批单核保信息 
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function queryEdorMain() 
{
  
//   var strSql="select distinct UWRuleCode,UWError,EdorAcceptNo,edorno,edortype,ContNo"
//            + " from LPEdorUWError where EdorAcceptNo='"+EdorAcceptNo+ "'";
   
    var sqlid1="EdorErrorUWQuerySql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.EdorErrorUWQuerySql");
	mySql1.setSqlId(sqlid1); //指定使用SQL的id
	mySql1.addSubPara(EdorAcceptNo);//指定传入参数
	var strSql = mySql1.getString();

    var crr = easyExecSql(strSql, 1, 0,"","",1);
    if (!crr)
    {
        alert("保全申请下没有初次核保信息！");
        return;
    }
    else
    {
        initEdorMainGrid();
        turnPage.queryModal(strSql, EdorMainGrid);
    }   
}


 function showApproveInfo()
 {

 	//书写SQL语句
// 	var infoSql="";
// 	infoSql="select edoracceptno,operator,approveoperator,approvedate,"
// 	       +" (select codename from ldcode where codetype='edorapprovereason' and code = modifyreason),"
// 	       +" approvecontent"
// 	       +" from LPApproveTrack a"
// 	       +" where EdorAcceptNo = '"+EdorAcceptNo+"' order by approvedate,approvetime";
 	
 	var sqlid2="EdorErrorUWQuerySql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("bq.EdorErrorUWQuerySql");
	mySql2.setSqlId(sqlid2); //指定使用SQL的id
	mySql2.addSubPara(EdorAcceptNo);//指定传入参数
	var infoSql = mySql2.getString();
 	
 	turnPage1.queryModal(infoSql, InfoGrid);
 	
 	return true;
}