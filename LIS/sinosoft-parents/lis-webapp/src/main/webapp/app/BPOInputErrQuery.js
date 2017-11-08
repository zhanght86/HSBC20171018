//
//程序名称：ProposalIndQuery.js
//程序功能：个单状态轨迹查询
//创建日期：2007-03-26 10:02
//创建人  ：Fuqx
//更新记录：  更新人    更新日期     更新原因/内容

//var arrDataSet;
var turnPage = new turnPageClass();

//简单查询

function easyPrint()
{
	easyQueryPrint(2,'CodeGrid','turnPage');
}

function easyQuery()
{	
	if (fm.StartDate.value == "" || fm.EndDate.value == "" )
	{
			alert("开始时间和结束时间不能为空!");
			return false;
	}
	//外包方不为空时
	var tBPOSQL = "";
//	if(fm.BPOID.value!=null && trim(fm.BPOID.value)!="")
//	{
//		tBPOSQL= " and exists(select 1 from BPOMissionState where bussno=bpoerrlog.bussno and BussNoType='TB' and bpoid='"+trim(fm.BPOID.value)+"') ";
//	}

	//管理机构不为空
	var tManageComSQL = "";
	var comCode1 = "";
	if(fm.ManageCom.value!=null && trim(fm.ManageCom.value)!="")
	{
		comCode1 = comCode;
//		tManageComSQL= " and exists(select 1 from ES_Doc_Main where DocCode=trim(bpoerrlog.bussno) and  Managecom like '"+trim(fm.ManageCom.value)+"%%' "
//		              +" and ManageCom like '"+comCode+"%%') ";
	}

	initCodeGrid();
	
//	// 书写SQL语句
//	var strSQL = " select bussno,errorcontent,"
//             + "(select concat(concat(to_char(makedate,'yyyy-mm-dd'),' '),maketime) from bpomissiondetailstate where bussno=bpoerrlog.bussno and bussnotype='TB' and dealcount='1') databackdate,"
//             + "concat(concat(to_char(makedate,'yyyy-mm-dd'),' '),maketime) inputdate "
//             + "from bpoerrlog "
//             + "where errver='3' and makedate>='"+fm.StartDate.value+"' "
//             + "and makedate<='"+fm.EndDate.value+"' "
//             + tBPOSQL
//             + tManageComSQL
//             + " order by makedate,maketime";
	
	
	var StartDate = fm.StartDate.value;
	var EndDate = fm.EndDate.value;
	var BPOID = trim(fm.BPOID.value);
	var ManageCom = trim(fm.ManageCom.value);
	var sqlid1="BPOInputErrQuerySql0";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.BPOInputErrQuerySql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(StartDate);//指定传入的参数
	mySql1.addSubPara(EndDate);//指定传入的参数
	mySql1.addSubPara(BPOID);//指定传入的参数
	mySql1.addSubPara(ManageCom);//指定传入的参数
	mySql1.addSubPara(comCode1);//指定传入的参数
	var strSQL=mySql1.getString();
	
//prompt('',strSQL);
	turnPage.queryModal(strSQL, CodeGrid);    

}


