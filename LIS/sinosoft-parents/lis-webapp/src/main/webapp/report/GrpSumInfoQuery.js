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
	document.all("query").disabled="true";
	document.all("toExcel").disabled="";
	var StartDate = fm.StartDate.value;   //开始时间
	var EndDate = fm.EndDate.value; 	  //截止时间	
	var flag=compareDate(StartDate,EndDate);  //比较日期
	var ManageCom = fm.ManageCom.value;	
	if(ManageCom==null||ManageCom==""){
	  alert("请录入管理机构！");
	  document.all("query").disabled="";
	  return false;
	}
	if(StartDate==null||StartDate==""||EndDate==null||EndDate==""){
	   alert("请录入开始日期和结束日期！");
	   document.all("query").disabled="";
	   return false;
	}
	if(flag==1){		
		alert("开始日期晚于截止日期");
		document.all("query").disabled="";
		return false;
	}	
	SQL="select a.RiskCode 险种代码,"
        +"a.ManageCom 管理机构代码,"
        +"(select Name from LDCom where ComCode=a.ManageCom) 管理机构名称,"
        +"count(distinct a.GrpContNo) 出单件数,"
        +"sum(InsuredPeoples) 承保人数,"
        +"sum(Amnt) 总保额,"
        +"sum(Prem) 总保费 "
		+"from LCPol a where 1=1 "
		+"and a.ContType='2' "
		+"and a.AppFlag in('1','4')";		
		
	if(ManageCom!=null && ManageCom!=""){
		SQL+=" and a.ManageCom like '"+ManageCom+"%' ";
	}
	if(EndDate!=null && EndDate!=""){
		SQL+="and a.SignDate <= date'"+EndDate+"' ";	
	}
	if(StartDate!=null && StartDate!=""){
		SQL+="and a.SignDate >= date'"+StartDate+"' ";	
	}	
	SQL+=" group by a.RiskCode,a.ManageCom ";	
	fm.sql.value=SQL;	
	turnPage.queryModal(SQL, SumInfoGrid);	
	document.all("query").disabled="";
	if(SumInfoGrid.mulLineCount<1){
		initSumInfoGrid();
  	  	alert("没有查询到符合条件的记录！");
  	  	document.all("query").disabled="";
  	  	return false; 
	}	
}
//导出到EXCEL
function ToExcel() {
	if(SumInfoGrid.mulLineCount<1){		
  	  	alert("没有可导出的记录！");
  	  	return false;
	}
	document.all("toExcel").disabled="true";
	fm.action="./GrpSumInfoToExcel.jsp";
	fm.target="fraSubmit";	
	document.getElementById("fm").submit(); //提交	
}