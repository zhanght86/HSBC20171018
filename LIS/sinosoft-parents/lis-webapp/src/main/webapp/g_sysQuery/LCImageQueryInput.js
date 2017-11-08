/***************************************************************
 * <p>ProName：LCImageQueryInput.js</p>
 * <p>Title：影像件查询</p>
 * <p>Description：影像件查询</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-08-04
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * 查询
 */
function queryClick() {

	if(!verifyInput2()){
		return false;
	}
	if(fm.ScanStartDate.value !="" && fm.ScanEndDate.value !="" ){
		 if(fm.ScanStartDate.value > fm.ScanEndDate.value){
		 	alert("扫描起期需小于扫描止期！" );
		 	return false;
		 }
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_sysQuery.LCImageQuerySql");
	tSQLInfo.setSqlId("LCImageQuerySql1");
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.BussType.value);
	tSQLInfo.addSubPara(fm.BussNo.value);
	tSQLInfo.addSubPara(fm.ScanStartDate.value);
	tSQLInfo.addSubPara(fm.ScanEndDate.value);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.BussNo.value);
	tSQLInfo.addSubPara(fm.Operator.value);
	
	
	if(_DBT==_DBO){
		tSQLInfo.addSubPara("and rownum=1");
   }else if(_DBT==_DBM)
   {
	   tSQLInfo.addSubPara("limit 1");
   }
	
	
	turnPage1.queryModal(tSQLInfo.getString(),QueryResultGrid, 1, 1);
	
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

/**
 * 影像扫描件查询
 */
function queryScanPage() {
	var tSelNo = QueryResultGrid.getSelNo();
	
	if(tSelNo<1){
		alert("请选择一条影像信息!");
		return false;
	}
	var tBussType = QueryResultGrid.getRowColData(tSelNo-1,2);
	var tBussNo = QueryResultGrid.getRowColData(tSelNo-1,4);
	
	window.open("../easyscan/ScanPagesQueryMainInput.jsp?BussType="+tBussType+"&BussNo="+tBussNo,null , 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//重置
function resetClick(){
	fm.GrpContNo.value='';
	fm.ManageCom.value='';
	fm.ManageComName.value='';
	fm.BussType.value='';
	fm.BussTypeName.value='';
	fm.BussNo.value='';
	fm.ScanStartDate.value='';
	fm.ScanEndDate.value='';
	fm.GrpContNo.value='';
	initQueryResultGrid();
}

/**
 * 根据前台页面进行下拉控制
 **/
function returnShowCodeList (value1, value2, value3) {
	
	var tSql ="";
		
	if (value1=='esbusstype') {
	
		return showCodeList('esbusstype',value2,value3,null,null,null,'1',180);
	
	} else if (value1=='esbusssubtype') {
		if (isEmpty(fm.BussType)) {
			tSql = "1";
		}else {
			tSql = "1 and a.busstype=#"+ fm.BussType.value +"# ";
		}
		
		return showCodeList('esbusssubtype',value2,value3,null,tSql,1,'1',180);
	}
}