//
//程序名称：NotBackQuery.js
//程序功能：未录入数据查询
//创建日期：2008-09-03 10:10
//创建人  ：HULY
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
	
	initCodeGrid();
	
	// 书写SQL语句
	if(fm.BPOID.value == null||fm.BPOID.value == ""){
//	var strSQL = "select doccode,managecom,to_char(makedate,'yyyy-mm-dd'),maketime "
//             + " from es_doc_main "
//             + " where inputstate is null  and (substr(doccode, 3, 2) in ('32', '31') or doccode like '532002%') "
//             + " and busstype ='OF' "
//             + getWherePart('makedate','StartDate','>=')
//             + getWherePart('makedate','EndDate','<=')
//             + getWherePart('ManageCom','ManageCom','like')
//             + " and managecom like '"+comCode+"%%' "
//             +  "order by makedate,maketime";
		
		var  StartDate= window.document.getElementsByName(trim("StartDate"))[0].value;
	  	var  EndDate = window.document.getElementsByName(trim("EndDate"))[0].value;
	  	var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value;
		var sqlid1="NotBackQuerySql0";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.NotBackQuerySql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(StartDate);//指定传入的参数
		mySql1.addSubPara(EndDate);//指定传入的参数
		mySql1.addSubPara(ManageCom);//指定传入的参数
		mySql1.addSubPara(comCode);//指定传入的参数
		var strSQL=mySql1.getString();
       }	    
       else{
       var tBPOID = fm.BPOID.value;
//        var strSQL = "select doccode,managecom,to_char(makedate,'yyyy-mm-dd'),maketime "
//             + " from es_doc_main "
//             + " where inputstate is null  and (substr(doccode, 3, 2) in ('32', '31') or doccode like '532002%') "
//             + " and busstype ='OF' "
//             + getWherePart('makedate','StartDate','>=')
//             + getWherePart('makedate','EndDate','<=')
//             + getWherePart('ManageCom','ManageCom','like')
//             + " and managecom like '"+comCode+"%%' "
//             + " and exists (select 1 from bpoallotrate where trim(bpoallotrate.managecom)=substr(es_doc_main.managecom,1,char_length(trim(bpoallotrate.managecom))) and BPOID='"+tBPOID+"')"
//             +  "order by makedate,maketime";
        
        var  StartDate= window.document.getElementsByName(trim("StartDate"))[0].value;
	  	var  EndDate = window.document.getElementsByName(trim("EndDate"))[0].value;
	  	var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value;
		var sqlid2="NotBackQuerySql1";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.NotBackQuerySql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(StartDate);//指定传入的参数
		mySql2.addSubPara(EndDate);//指定传入的参数
		mySql2.addSubPara(ManageCom);//指定传入的参数
		mySql2.addSubPara(comCode);//指定传入的参数
		mySql2.addSubPara(tBPOID);//指定传入的参数
		var strSQL=mySql2.getString();
       }
//  prompt('',strSQL);
	turnPage.queryModal(strSQL, CodeGrid);

}


