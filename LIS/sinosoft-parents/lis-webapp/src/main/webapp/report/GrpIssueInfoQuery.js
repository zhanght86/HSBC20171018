var SQL = "";
var turnPage = new turnPageClass();
//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow){
  if (cShow=="true") {
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
	var ManageCom = fm.ManageCom.value;	
	var flag=compareDate(StartDate,EndDate);
	if(flag==1){		
		alert("开始日期晚于截止日期");
		return false;
	}
		
	SQL=" select a.IsueManageCom 管理机构代码,"
        +" a.GrpContNo 团体合同号,"
        +" a.ProposalGrpContNo 投保单号,"
        +" a.IssueType 问题件类型,"
        +" a.IssueCont 问题件内容,"
        +" a.Operator 录入人,"
   		+" (select DefaultOperator from LBMission where ProcessID='0000000004' and "
		+" ActivityID='0000002001' and MissionProp1=a.ProposalGrpContNo and SubMissionID='1') 复核人员,"                
    	+" a.MakeDate 录入日期,"      
    	+" (select MakeDate from LBMission where ProcessID='0000000004' and ActivityID='0000002001' "
		+" and MissionProp1=a.ProposalGrpContNo and SubMissionID='1') 复核日期, "		
    	+" (select SignDate from LCGrpCont where GrpContNo=a.GrpContNo) 签单日期,"
        +" a.OperatePos 操作位置,"
        +" a.BackObj 返回对象"
		+" from LCGrpIssuePol a where 1=1 "
		+" and exists(select 'X' from LCGrpCont where GrpContNo=a.GrpContNo and AppFlag<>'0')";
		
    if(ManageCom!=null && ManageCom!=""){
		SQL+=" and a.ManageCom like '"+ManageCom+"%' ";
	}
	
	if(EndDate!=null && EndDate!=""){
		SQL+="and a.MakeDate <= date'"+EndDate+"' ";	
	} 
	if(StartDate!=null && StartDate!=""){
		SQL+="and a.MakeDate >= date'"+StartDate+"' ";	
	}  
	fm.sql.value=SQL;
	turnPage.queryModal(SQL, IssueInfoGrid);	
	document.all("query").disabled="";
	if(IssueInfoGrid.mulLineCount<1){
		initIssueInfoGrid();
  	  	alert("没有查询到符合条件的记录！");
  	  	return false; 
	}	
	
}
//导出到EXCEL
function ToExcel() {
	if(IssueInfoGrid.mulLineCount<1){		
  	  	alert("没有可导出的记录！");
  	  	return false;
	}
	document.all("toExcel").disabled="true";
	fm.action="./GrpIssueInfoToExcel.jsp";
	fm.target="fraSubmit";	
	document.getElementById("fm").submit(); //提交	
}