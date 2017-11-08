/***************************************************************
 * <p>ProName：LLClaimNoMedicalInput.js</p>
 * <p>Title：非医疗账单录入</p>
 * <p>Description：非医疗账单录入</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-05-01
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();
var tCondition1="";
var tCondition2="";
var tCondition3="";
var tOperator1="";
	

/**
 * 新增伤残账单
 */
function addMaimBill() {
	
		if (fm.DefoTypeCode.value == ""||fm.DefoTypeCode.value ==null||fm.DefoTypeName.value ==""||fm.DefoTypeName.value ==null){
    	
        alert("请输入伤残类型！");
        return false;
    }
    if(fm.DefoTypeCode.value=="3"){
			if (fm.DefoClassCode.value == ""||fm.DefoClassCode.value ==null||fm.DefoClassName.value ==""||fm.DefoClassName.value ==null){
	    	
	        alert("请输入伤残分类！");
	        return false;
	    }
  	}
    if (fm.DefoGradeCode.value == ""||fm.DefoGradeCode.value==null||fm.DefoGradeName.value==""||fm.DefoGradeName.value==null){
    	
        alert("请输入伤残级别！");
        return false;
    }
    
    if (fm.DefoCode.value == ""||fm.DefoCode.value ==null||fm.DefoName.value ==""||fm.DefoName.value ==null){
    	
        alert("请输入伤残代码！");
        return false;
    }
    if(fm.JudgeDate.value==""||fm.JudgeDate.value==null){
    	
    		alert("请输入鉴定日期！");
        return false;
    }
    	tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimNoMedicalSql");
			tSQLInfo.setSqlId("LLClaimNoMedical1");
			tSQLInfo.addSubPara(fm.DefoTypeCode.value);
			tSQLInfo.addSubPara(fm.DefoClassCode.value);
			tSQLInfo.addSubPara(fm.DefoGradeCode.value);
			tSQLInfo.addSubPara(fm.DefoCode.value);
	
			var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
			if(arr == null){
				alert("查询伤残信息失败");
				
				return false;
			}
			
			if(dateDiff(fm.JudgeDate.value,tCurrentDate,'D') < 0){
    	
        alert("鉴定日期晚于当前日期！");
        return;
    	}
    	if (dateDiff(fm.JudgeDate.value,tAccidentDate,'D')>0) {
    	
    		alert("鉴定日期早于出险日期！");
    		return false;
    	}
    	if(fm.DefoType.value=='1'){
				
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_claim.LLClaimNoMedicalSql");
				tSQLInfo.setSqlId("LLClaimNoMedical6");
				tSQLInfo.addSubPara(tRgtNo);
				tSQLInfo.addSubPara(tCaseNo);
				tSQLInfo.addSubPara("3");
				tSQLInfo.addSubPara("");
				var arr1 = easyExecSql(tSQLInfo.getString(),1, 0, 1);
    		if(arr1!=null){
    			alert("一次事件下【普通伤残(7级34项)】与【新伤残等级(281项)】不能同时存在");
    			return false;
    		}
    	} else if (fm.DefoType.value=='3'){
    		tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_claim.LLClaimNoMedicalSql");
				tSQLInfo.setSqlId("LLClaimNoMedical6");
				tSQLInfo.addSubPara(tRgtNo);
				tSQLInfo.addSubPara(tCaseNo);
				tSQLInfo.addSubPara("1");
				tSQLInfo.addSubPara("");
				var arr1 = easyExecSql(tSQLInfo.getString(),1, 0, 1);
    		if(arr1!=null){
    			alert("一次事件下【普通伤残(7级34项)】与【新伤残等级(281项)】不能同时存在");
    			return false;
    		}
    	}
    	tOperator1="1";//伤残
  		fm.Operate.value = "INSERT";
			fm.action = "../g_claim/LLClaimNoMedicalDefoSave.jsp";
			submitForm();
}
/**
 * 修改伤残账单
 */
function modifyMaimBill() {
	
	var tRow = MaimInfoGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条伤残信息，再进行修改！");
		return false;
	}
	if (fm.DefoTypeCode.value == ""||fm.DefoTypeCode.value ==null||fm.DefoTypeName.value ==""||fm.DefoTypeName.value ==null){
    	
        alert("请输入伤残类型！");
        return false;
    }
    if(fm.DefoTypeCode.value=="3"){
				if (fm.DefoClassCode.value == ""||fm.DefoClassCode.value ==null||fm.DefoClassName.value ==""||fm.DefoClassName.value ==null){
	    	
	        alert("请输入伤残分类！");
	        return false;
	    }
  	}
    if (fm.DefoGradeCode.value == ""||fm.DefoGradeCode.value==null||fm.DefoGradeName.value==""||fm.DefoGradeName.value==null){
    	
        alert("请输入伤残级别！");
        return false;
    }
    
    if (fm.DefoCode.value == ""||fm.DefoCode.value ==null||fm.DefoName.value ==""||fm.DefoName.value ==null){
    	
        alert("请输入伤残代码！");
        return false;
    }
    
    	tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimNoMedicalSql");
			tSQLInfo.setSqlId("LLClaimNoMedical1");
			tSQLInfo.addSubPara(fm.DefoTypeCode.value);
			tSQLInfo.addSubPara(fm.DefoClassCode.value);
			tSQLInfo.addSubPara(fm.DefoGradeCode.value);
			tSQLInfo.addSubPara(fm.DefoCode.value);
	
			var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
			if(arr == null){
				alert("查询调查信息失败");
				
				return false;
			}
			if(dateDiff(fm.JudgeDate.value,tCurrentDate,'D') < 0){
    	
        alert("鉴定日期晚于当前日期！");
        return;
    	}
    	if (dateDiff(fm.JudgeDate.value,tAccidentDate,'D')>0) {
    	
    		alert("鉴定日期早于出险日期！");
    		return false;
    	}

			if(fm.DefoType.value=='1'){
				
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_claim.LLClaimNoMedicalSql");
				tSQLInfo.setSqlId("LLClaimNoMedical6");
				tSQLInfo.addSubPara(tRgtNo);
				tSQLInfo.addSubPara(tCaseNo);
				tSQLInfo.addSubPara("3");
				tSQLInfo.addSubPara(fm.tDefoSerialNo.value);
				var arr1 = easyExecSql(tSQLInfo.getString(),1, 0, 1);
    		if(arr1!=null){
    			alert("一次事件下【普通伤残(7级34项)】与【新伤残等级(281项)】不能同时存在");
    			return false;
    		}
    	} else if (fm.DefoType.value=='3'){
    		tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("claim.LLClaimNoMedicalSql");
				tSQLInfo.setSqlId("LLClaimNoMedical6");
				tSQLInfo.addSubPara(tRgtNo);
				tSQLInfo.addSubPara(tCaseNo);
				tSQLInfo.addSubPara("1");
				tSQLInfo.addSubPara(fm.tDefoSerialNo.value);
				var arr1 = easyExecSql(tSQLInfo.getString(),1, 0, 1);
    		if(arr1!=null){
    			alert("一次事件下【普通伤残(7级34项)】与【新伤残等级(281项)】不能同时存在");
    			return false;
    		}
    	}
    	tOperator1="1";//伤残
  		fm.Operate.value = "UPDATE";
			fm.action = "../g_claim/LLClaimNoMedicalDefoSave.jsp";
			submitForm();
}
/**
 * 删除伤残账单
 */
function deleteMaimBill() {
	
	var tRow = MaimInfoGrid.getSelNo();

	if (tRow==0) {
		
		alert("请先选择一条伤残信息，再进行删除！");
		return false;
	}
	tOperator1="1";//伤残
	if (confirm("您确实想删除该记录吗?")){
    
        fm.Operate.value = "DELETE";    			  
        fm.action = "../g_claim/LLClaimNoMedicalDefoSave.jsp";
        
        submitForm();
   	}
}
/**
	*重置伤残账单
	*/
function resetDefoInfo(){
	  
	  fm.tDefoSerialNo.value="";
    fm.DefoTypeCode.value="";
    fm.DefoTypeName.value="";
    fm.DefoClassCode.value="";
    fm.DefoClassName.value="";
    fm.DefoGradeCode.value="";
    fm.DefoGradeName.value="";
    fm.DefoCode.value ="";
    fm.DefoName.value ="";
    fm.defoRate.value ="";
    fm.JudgeOrganName.value="";
    fm.JudgeDate.value="";
    initForm();
		initMaimInfoGrid();
		QueryDefoInfo();
		document.getElementById("DefoClass").style.display="none";
		document.getElementById("DefoClassCode").style.display="none";
}
/**
 * 新增重疾账单
 */
function addMajorBill() {
	
		if (fm.OperationType.value == null ||fm.OperationType.value == ""){
		
			alert("请输入类型信息！");
			return false;
    }
/*    if (fm.OpFee.value == null ||fm.OpFee.value == ""){
    	
			alert("请输入金额信息！");
			return false;
    }*/
    if (fm.OperationCode.value == null ||fm.OperationCode.value == ""){
    	
			alert("请输入代码信息！");
			return false;
    }
    if (fm.UnitName.value == null || fm.UnitName.value == ""){
    	
			alert("请输入医疗机构名称！");
			return false;
    }
    if(fm.DiagnoseDate.value==""||fm.DiagnoseDate.value==null){
    	
    	alert("确诊日期不能为空!");
      return false;
    }
    if (dateDiff(fm.DiagnoseDate.value,tCurrentDate,'D') < 0){
    	
			alert("确诊日期不能大于当前日期!");
      return false;
    }
		if (dateDiff(fm.DiagnoseDate.value,tAccidentDate,'D')>0) {
    	
    		alert("重大疾病确诊日期早于出险日期，请重新录入！");
    		return false;
    	}
    if(hasMoreOperation()){
    	
			alert("该被保人的此事件已经增加了一种重大疾病，不能再增加！");
			return false;	
			
    }
		tOperator1="2";//重疾
		fm.Operate.value = "INSERT";
		fm.action = "../g_claim/LLClaimNoMedicalOperationSave.jsp";
		submitForm();
}
/**
 * 修改重疾账单
 */
function modifyMajorBill() {
	
	var tRow = MajorBillGrid.getSelNo();
	if (tRow==0) {	
		
		alert("请先选择一条重疾信息，再进行修改！");
		return false;
	}
	if (fm.OperationType.value == null ||fm.OperationType.value == ""){
		alert("请输入类型信息！");
		return false;
  }
/*  if (fm.OpFee.value == null ||fm.OpFee.value == ""){
    	
		alert("请输入金额信息！");
		return false;
  }*/
  if (fm.OperationCode.value == null ||fm.OperationCode.value == ""){
  	
		alert("请输入代码信息！");
		return false;
  }
  if (fm.UnitName.value == null || fm.UnitName.value == ""){
  	
		alert("请输入医疗机构名称！");
		return false;
  }
  if(fm.DiagnoseDate.value==""||fm.DiagnoseDate.value==null){
    	
    	alert("确诊日期不能为空!");
      return false;
    }
  if (dateDiff(fm.DiagnoseDate.value,tCurrentDate,'D') < 0){
  	
		alert("确诊日期不能大于当前日期!");
    return false;
  }
	
	if (dateDiff(fm.DiagnoseDate.value,tAccidentDate,'D')>0) {
    	
    		alert("重大疾病确诊日期早于出险日期，请重新录入！");
    		return false;
    	}
  /**  if(hasMoreOperation()){  此问题待处理
    	
			alert("该被保人的此事件已经增加了一种重大疾病，不能再增加！");
			return false;	
			
    }**/
    	tOperator1="2";//重疾
			fm.Operate.value = "UPDATE";
			fm.action = "../g_claim/LLClaimNoMedicalOperationSave.jsp";
			submitForm();
}
/**
 * 删除重疾账单
 */
function deleteMajorBill() {
	
	var tRow = MajorBillGrid.getSelNo();
	if (tRow==0) {	
		
		alert("请先选择一条重疾信息，再进行删除！");
		return false;
	}
	tOperator1="2";//重疾
	if (confirm("您确实想删除该记录吗?")){
    
        fm.Operate.value = "DELETE";    			  
        fm.action = "../g_claim/LLClaimNoMedicalOperationSave.jsp";
        
        submitForm();
   	}
}

/**
 * 提交数据
 */
function submitForm() {
	
	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit();
}

/**
 * 提交数据后返回操作
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		
	}	
	if(tOperator1=="1"){//伤残
		
		resetDefoInfo();	
	}else if(tOperator1=="2"){//重疾
		resetMajorInfo();
	}
}

/**
 * 下拉框选择以后调用
 */
function afterCodeSelect(cCodeName, Field) {
	
	if(cCodeName=="defotype") {
		if(fm.DefoTypeCode.value=="3") {
			
			document.getElementById("DefoClass").style.display="";
			document.getElementById("DefoClassCode").style.display="";
		}else {
			document.getElementById("DefoClass").style.display="none"
			document.getElementById("DefoClassCode").style.display="none";
		}
	}
	if(cCodeName=="llparadeformity3") {
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimNoMedicalSql");
			tSQLInfo.setSqlId("LLClaimNoMedical1");
			tSQLInfo.addSubPara(fm.DefoTypeCode.value);
			tSQLInfo.addSubPara(fm.DefoClassCode.value);
			tSQLInfo.addSubPara(fm.DefoGradeCode.value);
			tSQLInfo.addSubPara(fm.DefoCode.value);
	
			var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
			if(arr == null){
				alert("查询调查信息失败");
				
				return false;
			}else {
				fm.defoRate.value=arr[0][0];
			}
		}
		if(cCodeName=="llparadeformity4") {
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimNoMedicalSql");
			tSQLInfo.setSqlId("LLClaimNoMedical5");
			tSQLInfo.addSubPara(fm.DefoCode.value);
			tSQLInfo.addSubPara(fm.DefoName.value);
			var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
			fm.DefoTypeCode.value=arr[0][0];
			fm.DefoTypeName.value=arr[0][1];
			fm.DefoClassCode.value=arr[0][2];
			fm.DefoClassName.value=arr[0][3];
			fm.DefoGradeCode.value=arr[0][4];
			fm.DefoGradeName.value=arr[0][5];
			fm.DefoCode.value=arr[0][6];
			fm.DefoName.value=arr[0][7];
			fm.defoRate.value=arr[0][8];
			if(fm.DefoTypeCode.value=="3") {
			document.getElementById("DefoClass").style.display="";
			document.getElementById("DefoClassCode").style.display="";
		}else {
			document.getElementById("DefoClass").style.display="none"
			document.getElementById("DefoClassCode").style.display="none";
		}
	}
}
	
/*function queryDefoTypeInfo(){
	alert(fm.DefoTypeCode.value);
	tCondition1="";
	tCondition2="";
	
	tCondition1="1 and defotype=#"+fm.DefoTypeCode.value+"#";
	
	if(fm.DefoTypeCode.value==3){
		
	} else {
		tCondition2="1 and defotype=#"+fm.DefoTypeCode.value+"#";
	}
	
	fm.DefoClassCode.value="";
	fm.DefoClassName.value="";
	fm.DefoGradeCode.value="";
	fm.DefoGradeName.value="";
	fm.DefoCode.value="";	
	fm.DefoName.value="";
	fm.defoRate.value="";
}*/
function queryDefoGradeInfo(tObject){
	var tObjectName = tObject.name;
	var tObjectValue = tObject.value;
	//alert(fm.DefoType.value);
	
	if(fm.DefoType.value==3){

		tCondition2 ="1 and defotype=#"+fm.DefoType.value+"# and defoclass=#"+fm.DefoClassCode.value+"#";
		tCondition3 ="1 and defotype=#"+fm.DefoType.value+"# and defoclass=#"+fm.DefoClassCode.value+"# and DefoGrade=#"+fm.DefoGradeCode.value+"#";
	} else {
		tCondition2="1 and defotype=#"+fm.DefoType.value+"#";
		tCondition3="1 and defotype=#"+fm.DefoType.value+"# and DefoGrade=#"+fm.DefoGradeCode.value+"#";
	}	
	if(tObjectName=="DefoGradeName"){
		showCodeList('lldefograde', [fm.DefoGradeCode,fm.DefoGradeName], [0,1], null,tCondition2, 1, 1, '400');	
	}else if(tObjectName=="DefoName"){
		showCodeList('llparadeformity3', [fm.DefoCode,fm.DefoName], [0,1], null,tCondition3, 1, 1, '400');	
	}
	
	//showCodeList('lldefocode', [objcode,objname], [0,1], null,tCondition3, 1, 1, '400');	
}
function queryDeformityDefoClassInfo(){
		
		fm.DefoGradeCode.value="";
		fm.DefoGradeName.value="";
		fm.DefoCode.value="";
		fm.DefoName.value="";
		fm.defoRate.value="";
	}
	
function QueryDefoInfo(){
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimNoMedicalSql");
		tSQLInfo.setSqlId("LLClaimNoMedical2");
		tSQLInfo.addSubPara(tRgtNo);
		tSQLInfo.addSubPara(tCustomerNo);
		tSQLInfo.addSubPara(tCaseNo);
		turnPage1.queryModal(tSQLInfo.getString(), MaimInfoGrid,"2");
		
}
function getDefoInfo(){
	var i = MaimInfoGrid.getSelNo();
   if(i < 1)
    {
        alert("请选中一行记录！");
        return false;
     }
	  i = MaimInfoGrid.getSelNo()-1;
    fm.tDefoSerialNo.value=MaimInfoGrid.getRowColData(i,1);
    fm.DefoTypeCode.value=MaimInfoGrid.getRowColData(i,2);
    fm.DefoTypeName.value=MaimInfoGrid.getRowColData(i,3);
    fm.DefoClassCode.value=MaimInfoGrid.getRowColData(i,4);
    fm.DefoClassName.value=MaimInfoGrid.getRowColData(i,5);
    fm.DefoGradeCode.value=MaimInfoGrid.getRowColData(i,6);
    fm.DefoGradeName.value=MaimInfoGrid.getRowColData(i,7);
    fm.DefoCode.value =MaimInfoGrid.getRowColData(i,8);
    fm.DefoName.value =MaimInfoGrid.getRowColData(i,9);
    fm.defoRate.value =MaimInfoGrid.getRowColData(i,10);
    fm.JudgeOrganName.value=MaimInfoGrid.getRowColData(i,11);
    fm.JudgeDate.value=MaimInfoGrid.getRowColData(i,12);

    if(fm.DefoTypeCode.value=="3"){
    	
			document.getElementById("DefoClass").style.display="";
			document.getElementById("DefoClassCode").style.display="";
		}else {
			document.getElementById("DefoClass").style.display="none";
			document.getElementById("DefoClassCode").style.display="none";
		}
}
 function hasMoreOperation(){
 	
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimNoMedicalSql");
		tSQLInfo.setSqlId("LLClaimNoMedical3");
		tSQLInfo.addSubPara(tRgtNo);
		tSQLInfo.addSubPara(tCustomerNo);
		tSQLInfo.addSubPara(tCaseNo);
		var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
		
		if(arr!=null&&arr[0][0]>0){
			
			return true;	
		}else{
			
			return false;
		}
}
function QueryMajorInfo (){
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimNoMedicalSql");
		tSQLInfo.setSqlId("LLClaimNoMedical4");
		tSQLInfo.addSubPara(tRgtNo);
		tSQLInfo.addSubPara(tCustomerNo);
		tSQLInfo.addSubPara(tCaseNo);
		turnPage2.queryModal(tSQLInfo.getString(), MajorBillGrid,"2");
		
}
function getMajorInfo(){
		
	var i = MajorBillGrid.getSelNo();
	if(i < 1){
		
		alert("请选中一行记录！");
		return false;	
	}
		i = MajorBillGrid.getSelNo()-1;
    fm.tMajorSerialNo.value=MajorBillGrid.getRowColData(i,1);
		fm.OperationType.value=MajorBillGrid.getRowColData(i,2);
    fm.OperationTypeName.value=MajorBillGrid.getRowColData(i,3);
    fm.OperationCode.value=MajorBillGrid.getRowColData(i,4);
    fm.OperationName.value=MajorBillGrid.getRowColData(i,5);
    fm.UnitName.value=MajorBillGrid.getRowColData(i,7);
    fm.DiagnoseDate.value=MajorBillGrid.getRowColData(i,8);
}
/*重疾重置**/
function resetMajorInfo(){
	
	  fm.tMajorSerialNo.value="";
		fm.OperationType.value="";
    fm.OperationTypeName.value="";
    fm.OperationCode.value="";
    fm.OperationName.value="";
    fm.UnitName.value="";
    fm.DiagnoseDate.value="";
		initMajorBillGrid();
		QueryMajorInfo();
	
}
/*function queryDefoNameInfo(objcode,objname){

		var DefoCode = objcode.value;	
		var DefoName= objname.value;
	
	if(DefoName == ""){
		objname.value="";
		return false;
		}	

		if (window.event.keyCode == "13") {

		var condition ="1 and  defoname like #%"+ DefoName +"%# ";

		window.event.keyCode = 0;
		
		
		if (DefoName==null || trim(DefoName)=="") {
		
			alert("请输入诊断详情名称!");
			return false;
				
		} else {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimNoMedicalSql");
		tSQLInfo.setSqlId("LLClaimNoMedical5");
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara(DefoName);
		
		var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
		if (arr == null) {
				
				alert("不存在该伤残信息，请重新录入！");
				objcode.value = "";
				objname.value = "";
				
				return false;
			} else {
				if (arr.length == 1) {
					
					fm.DefoTypeCode.value=arr[0][0];
					fm.DefoTypeName.value=arr[0][1];
					fm.DefoClassCode.value=arr[0][2];
					fm.DefoClassName.value=arr[0][3];
					fm.DefoGradeCode.value=arr[0][4];
					fm.DefoGradeName.value=arr[0][5];
					fm.DefoCode.value=arr[0][6];
					fm.DefoName.value=arr[0][7];
					fm.defoRate.value=arr[0][8];
					if(fm.DefoTypeCode.value=="3") {
						document.getElementById("DefoClass").style.display="";
						document.getElementById("DefoClassCode").style.display="";
					}else {
						document.getElementById("DefoClass").style.display="none"
						document.getElementById("DefoClassCode").style.display="none";
					}
				} else {
					
					showCodeList('llparadeformity4', [objcode,objname], [0,1], null,condition, "1", 1, '400');
				}
			}
		}
	}
}*/