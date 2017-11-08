var SQL = "";
var turnPage = new turnPageClass();
//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow){
  if (cShow=="true"){
    cDiv.style.display="";
  }
  else{
    cDiv.style.display="none";
  }
}

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug){
	if(cDebug=="1"){
		parent.fraMain.rows = "0,0,0,0,*";
	}
	else{
		parent.fraMain.rows = "0,0,0,0,*";
	}
	parent.fraMain.rows = "0,0,0,0,*";
}
// 查询按钮
function easyQuery(){	
	if(fm.StartDate.value == "" ||fm.StartDate.value == null){
		alert("请选择起始日期！");
		return false;
	}
	if(fm.EndDate.value == "" ||fm.EndDate.value == null){
		alert("请选择截止日期！");
		return false;
	}	
	document.all("query").disabled="true";
	document.all("toExcel").disabled="";	
	var StartDate = fm.StartDate.value;   //开始时间
	var EndDate = fm.EndDate.value; 	  //截止时间	
	var ManageCom = fm.ManageCom.value;	  //机构代码	
	var ContPlanCode = fm.ContPlanCode.value; //套餐计划编码
	var ManageCom = fm.ManageCom.value;	
	var flag=compareDate(StartDate,EndDate);
	if(flag==1){		
		alert("开始日期晚于截止日期");
		return false
	}
	
	SQL=" select (select Name from LDCom where ComCode=a.ManageCom)管理机构名称, "
       	+" a.ManageCom 管理机构代码, "
       	+" a.GrpContNo 团体合同号, "
       	+" a.PrtNo 投保单号, "
       	+" a.InsuredName 被保人姓名, "
       	+" a.InsuredSex 性别, "
       	+" a.InsuredBirthday 出生日期, "
       	+" (select ContPlanCode from LCInsured where ContNo=a.ContNo and InsuredNo=a.InsuredNo) 套餐计划编码, "
       	+" a.Prem 保费, "
       	+" a.mult 份数, "
       	+" a.CValiDate 生效日期, "
       	+" a.SignDate 签单日期 "       	
		+" from LCCont a,lcinsured b where a.contno =b.contno "
		+" and a.ContType='2' "
		+" and a.AppFlag in('1','4')"
		+" and a.SignDate between date'"+StartDate+"' and date'"+EndDate+"' "
		+" and exists(select 'X' from LCContPlan where GrpContNo=a.GrpContNo and PlanType='1' "+getWherePart('ContPlanCode','ContPlanCode')+") "
		+" and contplancode=b.contplancode"
		;
		
	if(ManageCom!=null && ManageCom!=""){
		SQL+=" and a.ManageCom like '"+ManageCom+"%' ";
	}
	fm.sql.value=SQL;	
	turnPage.queryModal(SQL, PlanInfoGrid);	
	document.all("query").disabled="";
	if(PlanInfoGrid.mulLineCount<1){
		initPlanInfoGrid();
  	  	alert("没有查询到符合条件的记录！");
  	  	return false; 
	}
}

function queryContPlan(){
   var tManageCom =fm.ManageCom.value;
   var tSQL =" select ContPlanCode, ContPlanName from LDPlan where 1=1 and ContPlanCode <> '00' "
   			+" and managecom like '"+manageCom+"%%'"
   			+getWherePart('managecom','ManageCom','like')
   			+" order by ContPlanCode";
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(tSQL, 1, 0, 1);
	//prompt("查询险种代码:",sql);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    //alert(tCodeData);
    document.all("ContPlanCode").CodeData=tCodeData;
	
}

//导出到EXCEL
function ToExcel() {
	if(PlanInfoGrid.mulLineCount<1){		
  	  	alert("没有可导出的记录！");
  	  	return false;
	}
	document.all("toExcel").disabled="true";
	fm.action="./GrpPlanInfoToExcel.jsp";
	fm.target="fraSubmit";	
	document.getElementById("fm").submit(); //提交	
}