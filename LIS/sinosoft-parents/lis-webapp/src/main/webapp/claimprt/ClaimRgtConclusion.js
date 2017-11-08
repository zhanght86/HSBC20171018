var showInfo;
var turnPage = new turnPageClass(); 

// 保全查询按钮
function easyQueryClick()
{
	initQueryResultGrid();
	
    if(fm.RgtNo.value == null||fm.RgtNo.value == "")
     {
    	    if(fm.ManageCom.value == null||fm.ManageCom.value == "")
    	     {
    	         alert("请输入所要查询的管理机构");
    	         return false;
    	     }
    	    
    	    if(fm.StartDate.value == null||fm.StartDate.value == "")
   	     	{
   	         	alert("请输入所要查询的统计起期");
   	         	return false;
   	     	}
    	    
    	    if(fm.EndDate.value == null||fm.EndDate.value == "")
   	     	{
   	         	alert("请输入所要查询的统计止期");
   	         	return false;
   	     	}
     }
    
//	var strSQL = "select a.RgtNo, "
//		+" b.customerno, (select name from ldperson where customerno = b.customerno) customername,"
//		+" a.rgtconclusion, (case a.rgtconclusion when '02' then '不予立案' when '03' then '延迟立案'),"
//		+" a.EndCaseDate, a.grpname"
//		+" from LLregister a, LLCase b"
// 		+" where a.rgtno = b.caseno"
//	 	+" and a.rgtconclusion = '02'"	//02不予立案
//        + getWherePart('a.rgtno','RgtNo')
//        + getWherePart('a.EndCaseDate','StartDate','>=')
//        + getWherePart('a.EndCaseDate','EndDate','<=')
//        + getWherePart('a.mngcom','ManageCom','like')
//		+ " order by a.RgtNo, a.EndCaseDate";
	
	var  RgtNo0 = window.document.getElementsByName(trim("RgtNo"))[0].value;
  	var  StartDate0 = window.document.getElementsByName(trim("StartDate"))[0].value;
  	var  EndDate0 = window.document.getElementsByName(trim("EndDate"))[0].value;
  	var  ManageCom0 = window.document.getElementsByName(trim("ManageCom"))[0].value;
	var sqlid0="ClaimRgtConclusionSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("claimprt.ClaimRgtConclusionSql"); //指定使用的properties文件名
	mySql0.setSqlId(sqlid0);//指定使用的Sql的id
	mySql0.addSubPara(RgtNo0);//指定传入的参数
	mySql0.addSubPara(StartDate0);//指定传入的参数
	mySql0.addSubPara(EndDate0);//指定传入的参数
	mySql0.addSubPara(ManageCom0);//指定传入的参数
	var strSQL=mySql0.getString();

   	turnPage.queryModal(strSQL, QueryResultGrid);
   	//alert("查询到的记录行数："+QueryResultGrid.mulLineCount);
   	if(QueryResultGrid.mulLineCount==0)
   	{
   		alert("没有查询到任何单证信息！");
   		return false;
   	}
}

//理赔立案结论打印:02不予立案
function PrintClaimRgtPdf()
{
	var selno = QueryResultGrid.getSelNo()-1;
	if (selno <0)
	{
		alert("请选择要打印的赔案！");
		return;
	}
    document.all('RgtNo').value = QueryResultGrid. getRowColData(selno,1);   
    document.all('CustomerNo').value = QueryResultGrid. getRowColData(selno,2); 
    //alert(PolGrid. getRowColData(tSel-1,5));
    
	fm.action = './ClaimRgtConclusionSave.jsp';  
    fm.target="../f1print";
	document.getElementById("fm").submit();
}

