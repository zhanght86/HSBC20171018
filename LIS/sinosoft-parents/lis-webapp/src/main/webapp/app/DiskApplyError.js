var turnPage=new turnPageClass();
function easyQuery()
{	
	var tGrpContNo = fm.GrpContNo.value;
//	var tSQL = "select insuredId,insuredName,errorinfo from lcgrpimportlog"
//	         + " where grpcontno='" + tGrpContNo 
//	         + "' and batchno='" + fm.BatchNo.value 
//	         + "' and errorstate='1'"
//	         + " order by length(trim(insuredId)),trim(insuredId)";
	
	
	var sqlid1="DiskApplyErrorSql0";
	var mySql1=new SqlClass();	
	mySql1.setResourceName("app.DiskApplyErrorSql"); //指定使用的properties文件   mySql1.setSqlId(sqlid1);//指定使用的Sql的id	
	mySql1.addSubPara(tGrpContNo);//指定传入的参数	
	mySql1.addSubPara(fm.BatchNo.value);//指定传入的参数	
	var tSQL=mySql1.getString();
	turnPage.queryModal(tSQL, ImportErrorGrid);
	
	if (ImportErrorGrid.mulLineCount == 0){
	    divOtherError.style.display = "";
	}
}

//[打印]按钮函数
function errorPrint()
{    
	//alert("查询到的记录行数："+CardInfoGrid.mulLineCount);
   	if(ImportErrorGrid.mulLineCount==0)
   	{
   		alert("没有可以打印的数据！！！");
   		return false;
   	}
	easyQueryPrint(2,"ImportErrorGrid","turnPage");	
}
