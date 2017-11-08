//***********************************************
//程序名称：EdorHealthImpartQuery.js
//程序功能：健康告知查询
//创建日期：2005-06-23 11:10:36
//创建人  ：liurongxiao
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
 *  查询保单信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function queryCont(){
  
//  var aSql=" select a.proposalcontno,a.contno,a.PolApplyDate,"
//          +" (case when a.appflag='0'"
//          +" then '未承保' else '已承保'"
//				  +" end)"
//				  +" from lccont a where a.contno in"
//				  +" (select distinct contno from lccustomerimpart where customerno='"+customerNo
//				  +"' and impartver = '02' union select distinct contno from lpcustomerimpart where customerno='"+customerNo
//				  +"' and impartver = '02')";	
  var aSql="";
  var sqlid1="RnewHealthImpartQuerySql3";
  var mySql1=new SqlClass();
	mySql1.setResourceName("xb.RnewHealthImpartQuerySql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(customerNo);//指定传入的参数
	mySql1.addSubPara(customerNo);//指定传入的参数
	aSql = mySql1.getString();
   
 
  var brr = easyExecSql(aSql);
 
  
  if(brr)
  {
    turnPage.queryModal(aSql, ContGrid);
  }
  else
  {
  	alert("该客户没有健康告知信息！");
  	divContInfo.style.display = "none";
  	divImpartInfo.style.display = "none";
  	return;
  	
  }
}


//查询客户信息
function queryPersonInfo()
{
//    var aSQL = "select CustomerNo, Name from LDPerson where CustomerNo = '" + customerNo + "'";
    var aSQL="";
    var sqlid1="RnewHealthImpartQuerySql2";
    var mySql1=new SqlClass();
	mySql1.setResourceName("xb.RnewHealthImpartQuerySql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(customerNo);//指定传入的参数
	aSQL = mySql1.getString();
    var arrResult = easyExecSql(aSQL);
    if (arrResult != null)
    {
        document.all('CustomerNo').value = arrResult[0][0];
        document.all('CustomerName').value = arrResult[0][1];
    }
}

//显示保单下健康告知信息
function contInfoClick(){
  
  var tSel = ContGrid.getSelNo();
  var tProposalContNo = ContGrid.getRowColData(tSel - 1,1);        //合同投保单号
	
//  var aSQL="select impartver,impartcode,impartcontent,ImpartParamModle"
//                +" from lccustomerimpart where customerno='"+customerNo+"' and proposalcontno='"+tProposalContNo+"' and impartver = '02'"
//                +" union select impartver,impartcode,impartcontent,impartParamModle"
//                +" from lpcustomerimpart where customerno='"+customerNo+"' and proposalcontno='"+tProposalContNo+"' and impartver = '02'";	
  var aSQL="";
  var sqlid1="RnewHealthImpartQuerySql1";
  var mySql1=new SqlClass();
	mySql1.setResourceName("xb.RnewHealthImpartQuerySql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(customerNo);//指定传入的参数
	mySql1.addSubPara(tProposalContNo);//指定传入的参数
	mySql1.addSubPara(customerNo);//指定传入的参数
	mySql1.addSubPara(tProposalContNo);//指定传入的参数
	aSQL = mySql1.getString();
  turnPage.queryModal(aSQL, ImpartGrid);
	
}


