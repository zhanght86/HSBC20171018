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
	if(verifyInput()==false) return false;		
	document.all("query").disabled="true";
	document.all("toExcel").disabled="";
	
	var StartDate = fm.StartDate.value;   	//开始时间
	var EndDate = fm.EndDate.value; 	  	//截止时间	
	var GrpName=fm.GrpName.value; 			//投保单位名称	
	var Blacklist=fm.Blacklist.value; 		//投保单位黑名单标记
	var flag=compareDate(StartDate,EndDate);
	var ManageCom = fm.ManageCom.value;
	
	if(flag==1){		
		alert("开始日期晚于截止日期");
		return false;
	}	
	SQL=" select " 
       +" a.ManageCom 管理机构代码, "
       +" (select Name from LDCom where ComCode=a.ManageCom) 管理机构名称, "
       +" a.GrpContNo 团单合同号, "
       +" a.RiskCode 险种代码, "
       +" (select RiskName from LMRiskApp where RiskCode = a.RiskCode) 险种名称, "
       +" a.Peoples2 被保人人数, "       
       +" a.CustomerNo 投保单位代码, "
       +" (select GrpName from LCGrpCont where GrpContNo=a.GrpContNo) 投保单位名称, "
       +" (select CodeName from LDCode where CodeType='businesstype' and Code=(select BusinessType from LCGrpCont where GrpContNo=a.GrpContNo)) 行业类别名称, "
       +" a.Prem 累计保费, "       
       +" (select sum(Pay) from LJAGetClaim where GrpPolNo = a.GrpPolNo and GrpContNo=a.GrpContNo and RiskCode=a.RiskCode) 累计赔付金额, "
       +" a.CValiDate 生效日期, "
       +" (select nvl(BlackListFlag,'0') from LDGrp where CustomerNo=(select AppntNo from LCGrpCont where GrpContNo=a.GrpContNo)) 投保单位黑名单标记 "
       +" from LCGrpPol a where 1=1 and a.AppFlag in('1','4') "
       +" and exists(select 'X' from LJAGetClaim where GrpPolNo=a.GrpPolNo) "
       + getWherePart('a.GrpContNo','GrpContNo')
       + getWherePart('a.RiskCode','RiskCode');
     
    if(ManageCom!=null && ManageCom!=""){
		SQL+=" and a.ManageCom like '"+ManageCom+"%' ";
	}
      
    if(EndDate!=null && EndDate!=""){
		SQL+=" and a.CValiDate <= date'"+EndDate+"' ";	
	}	
	if(StartDate!=null && StartDate!=""){
		SQL+=" and a.CValiDate >= date'"+StartDate+"' ";	
	}
	if(GrpName!=null && GrpName!=""){
		SQL+=" and (select GrpName from LCGrpCont where GrpContNo=a.GrpContNo) like '%"+GrpName+"%' ";	
	}
	if(Blacklist!=null && Blacklist!=""){
		SQL+=" and exists(select 'X' from LDGrp where CustomerNo=(select AppntNo from LCGrpCont where GrpContNo=a.GrpContNo) and nvl(BlackListFlag,'0')='"+Blacklist+"') ";
	}    
	
	SQL+=" order by a.ManageCom,a.CustomerNo,a.GrpContNo,a.RiskCode ";
	
	fm.sql.value=SQL;
	
	turnPage.queryModal(SQL, ClaimInfoGrid);
	
	document.all("query").disabled="";
	if(ClaimInfoGrid.mulLineCount<1){
		initClaimInfoGrid();
  	  	alert("没有查询到符合条件的记录！");
  	  	return false; 
	}	
}

//导出到EXCEL
function ToExcel() {
	if(ClaimInfoGrid.mulLineCount<1){		
  	  	alert("没有可导出的记录！");
  	  	return false;
	}
	document.all("toExcel").disabled="true";
	fm.action="./GrpClaimInfoToExcel.jsp";
	fm.target="fraSubmit";	
	document.getElementById("fm").submit(); //提交
}