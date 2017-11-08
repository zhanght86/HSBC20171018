//程序名称：LLDealUWsecond.js
//程序功能：二核结论
//创建日期：2003-03-27 15:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容


var showInfo;
var mDebug="1";
var turnPage1 = new turnPageClass(); 
var turnPage = new turnPageClass();
var mySql = new SqlClass();
// 未完成的二次核保合同单列表 
function initQueryLLUnfinishedContGrid()
{
	/*var strSQL = "select t.caseno, t.insuredno, t.insuredname ,t.managecom, t.operator"
			 + " from llcuwbatch t where 1=1 and t.state = '0'" 
			 + " and t.uwoperator='" + fm.Operator.value + "'"
			 //+ " and exists (select 1 from lwmission where 1 = 1 and activityid = '0000005035' and processid = '0000000005'"
			 //+ " 	and (DefaultOperator = '" + fm.Operator.value + "' or MissionProp19 = '" + fm.Operator.value + "') and missionprop8 = '1')"
	         + " group by t.caseno, t.insuredno, t.insuredname ,t.managecom, t.operator"
	         + " order by t.caseno, t.insuredno";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWInputSql");
	mySql.setSqlId("LLDealUWSql1");
	mySql.addSubPara(fm.Operator.value ); 
	turnPage1.pageLineNum=5;
	turnPage1.queryModal(mySql.getString(), LLUnfinishedContGrid);
}

// 已经完成的二次核保合同单列表 
function initQueryLLContGrid()
{
    /*var strSQL = "select t.caseno, t.batno, t.managecom, t.operator,"
       + " decode((select distinct 1 from lwmission where missionprop1 = t.caseno "
       + " and missionprop3 = t.batno and activityid = '0000005510' and processid = '0000000005'),"
       + " '1', '未处理', '已处理') state"
  	   + " from llcuwbatch t where t.state = '1' and t.uwoperator = '" + fm.Operator.value + "'"
   	   + " and exists (select 1 from lwmission where missionprop1 = t.caseno and missionprop3 = t.batno and activityid = '0000005510' and processid = '0000000005')"
 	   + " group by t.caseno, t.batno, t.managecom, t.operator, state"
 	   + " order by t.caseno, t.batno";*/
    	mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWInputSql");
	mySql.setSqlId("LLDealUWSql2");
	mySql.addSubPara(fm.Operator.value );      
	turnPage.pageLineNum=10;
	turnPage.queryModal(mySql.getString(), LLContGrid);	
}

function queryGrid()
{
	if(fm.RptNo.value==null || fm.RptNo.value==""){
		alert("请输入立案号！");
		return false;
	}
	initQueryLLContGrid;
   /* var strSQL = "select t.caseno, t.batno, t.managecom, t.operator,"
       + " decode((select distinct 1 from lwmission where missionprop1 = t.caseno "
       + " and missionprop3 = t.batno and activityid = '0000005510' and processid = '0000000005'),"
       + " '1', '未处理', '已处理') state"
  	   + " from llcuwbatch t where t.state = '1' and t.uwoperator = '" + fm.Operator.value + "'"
  	   + " and t.caseno='" + fm.RptNo.value + "'"
   	   //+ " and exists (select 1 from lwmission where missionprop1 = t.caseno and missionprop3 = t.batno and activityid = '0000005510' and processid = '0000000005')"
 	   + " group by t.caseno, t.batno, t.managecom, t.operator, state"
 	   + " order by t.caseno, t.batno"; */
 		mySql = new SqlClass();
		mySql.setResourceName("claim.LLDealUWInputSql");
		mySql.setSqlId("LLDealUWSql3");
		mySql.addSubPara(fm.Operator.value );  
		mySql.addSubPara(fm.RptNo.value );               
	turnPage.pageLineNum=10;
	turnPage.queryModal(mySql.getString(), LLContGrid);
	if(LLContGrid.mulLineCount<=0){
		alert("未查询到此案件或者此案件二核发起人不是当前操作员，请检查！");
		return false;
	}
}

//进入二核结论页面
function showSecondUWInput()
{
    var tSel = LLContGrid.getSelNo()-1;
    var CaseNo = LLContGrid.getRowColData(tSel,1);		//赔案号
    var InsuredNo = LLContGrid.getRowColData(tSel,2);	//被保人号码

	//var strUrl="LLDealUWsecondMain.jsp?CaseNo="+CaseNo+"&Flag=Y";    
	//window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	
	location.href="LLDealUWsecondMain.jsp?CaseNo="+CaseNo+"&InsuredNo="+InsuredNo+"&Flag=Y";
}

