//***********************************************
//程序名称：QueryNotice.js
//程序功能：已发放通知书查询 
//创建日期：2006-11-17 17:05
//创建人  ：haopan
//更新记录：  更新人    更新日期     更新原因/内容
//***********************************************

//               该文件中包含客户端需要处理的函数和事件


//全局变量

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
 *  查询通知书
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 function queryNotice()
 {
 	//alert(GrpContNo);
 	var strSQl="";
// 	strSQl="select prtseq, case code "
// 							+" when 'G03' then '体检通知书'  "
// 							+" when 'G04' then '生调通知书'   "	
// 							+" when '50'  then '团体核保通知书'	"
// 							+" when '53'  then '团体延期承保通知书'	"
// 							+" when '57'  then '团体首期交费'	"
// 							+" when '54'  then '团单新契约问题件通知书'	"
// 							+" when '56'  then '团单新契约客户合并通知书'	"
// 							+" when '75'  then '团体核保要求通知书'	"
// 							+" when '76'  then '团单核保结论通知书'	"
// 							+" when '78'  then '团单溢交退费通知书'	"
// 							+" when 'BQ94' then '保全团体核保结论通知书'"
// 							+" when 'BQ95' then '保全团体核保要求通知书'"
//							+" 	End,case stateflag when '0' then '已发放待打印' when '1' then '已打印待回收' when '2' then '已回收' end "
//			+" from loprtmanager  where otherno='"+GrpContNo+"' or  standbyflag1='"+GrpContNo+"' or standbyflag2='"+GrpContNo+"' or standbyflag3='"+GrpContNo+"'";
 	    var  sqlid1="QueryNoticeSql0";
	 	var  mySql1=new SqlClass();
	 	mySql1.setResourceName("uw.QueryNoticeSql"); //指定使用的properties文件名
	 	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	 	mySql1.addSubPara(GrpContNo);//指定传入的参数
	 	mySql1.addSubPara(GrpContNo);//指定传入的参数
	 	mySql1.addSubPara(GrpContNo);//指定传入的参数
	 	mySql1.addSubPara(GrpContNo);//指定传入的参数
	 	strSQl=mySql1.getString();
	 turnPage.queryModal(strSQl, NoticeGrid);
	 
	   

			
}
