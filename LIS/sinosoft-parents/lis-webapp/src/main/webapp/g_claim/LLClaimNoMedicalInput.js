/***************************************************************
 * <p>ProName��LLClaimNoMedicalInput.js</p>
 * <p>Title����ҽ���˵�¼��</p>
 * <p>Description����ҽ���˵�¼��</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
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
 * �����˲��˵�
 */
function addMaimBill() {
	
		if (fm.DefoTypeCode.value == ""||fm.DefoTypeCode.value ==null||fm.DefoTypeName.value ==""||fm.DefoTypeName.value ==null){
    	
        alert("�������˲����ͣ�");
        return false;
    }
    if(fm.DefoTypeCode.value=="3"){
			if (fm.DefoClassCode.value == ""||fm.DefoClassCode.value ==null||fm.DefoClassName.value ==""||fm.DefoClassName.value ==null){
	    	
	        alert("�������˲з��࣡");
	        return false;
	    }
  	}
    if (fm.DefoGradeCode.value == ""||fm.DefoGradeCode.value==null||fm.DefoGradeName.value==""||fm.DefoGradeName.value==null){
    	
        alert("�������˲м���");
        return false;
    }
    
    if (fm.DefoCode.value == ""||fm.DefoCode.value ==null||fm.DefoName.value ==""||fm.DefoName.value ==null){
    	
        alert("�������˲д��룡");
        return false;
    }
    if(fm.JudgeDate.value==""||fm.JudgeDate.value==null){
    	
    		alert("������������ڣ�");
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
				alert("��ѯ�˲���Ϣʧ��");
				
				return false;
			}
			
			if(dateDiff(fm.JudgeDate.value,tCurrentDate,'D') < 0){
    	
        alert("�����������ڵ�ǰ���ڣ�");
        return;
    	}
    	if (dateDiff(fm.JudgeDate.value,tAccidentDate,'D')>0) {
    	
    		alert("�����������ڳ������ڣ�");
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
    			alert("һ���¼��¡���ͨ�˲�(7��34��)���롾���˲еȼ�(281��)������ͬʱ����");
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
    			alert("һ���¼��¡���ͨ�˲�(7��34��)���롾���˲еȼ�(281��)������ͬʱ����");
    			return false;
    		}
    	}
    	tOperator1="1";//�˲�
  		fm.Operate.value = "INSERT";
			fm.action = "../g_claim/LLClaimNoMedicalDefoSave.jsp";
			submitForm();
}
/**
 * �޸��˲��˵�
 */
function modifyMaimBill() {
	
	var tRow = MaimInfoGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ���˲���Ϣ���ٽ����޸ģ�");
		return false;
	}
	if (fm.DefoTypeCode.value == ""||fm.DefoTypeCode.value ==null||fm.DefoTypeName.value ==""||fm.DefoTypeName.value ==null){
    	
        alert("�������˲����ͣ�");
        return false;
    }
    if(fm.DefoTypeCode.value=="3"){
				if (fm.DefoClassCode.value == ""||fm.DefoClassCode.value ==null||fm.DefoClassName.value ==""||fm.DefoClassName.value ==null){
	    	
	        alert("�������˲з��࣡");
	        return false;
	    }
  	}
    if (fm.DefoGradeCode.value == ""||fm.DefoGradeCode.value==null||fm.DefoGradeName.value==""||fm.DefoGradeName.value==null){
    	
        alert("�������˲м���");
        return false;
    }
    
    if (fm.DefoCode.value == ""||fm.DefoCode.value ==null||fm.DefoName.value ==""||fm.DefoName.value ==null){
    	
        alert("�������˲д��룡");
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
				alert("��ѯ������Ϣʧ��");
				
				return false;
			}
			if(dateDiff(fm.JudgeDate.value,tCurrentDate,'D') < 0){
    	
        alert("�����������ڵ�ǰ���ڣ�");
        return;
    	}
    	if (dateDiff(fm.JudgeDate.value,tAccidentDate,'D')>0) {
    	
    		alert("�����������ڳ������ڣ�");
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
    			alert("һ���¼��¡���ͨ�˲�(7��34��)���롾���˲еȼ�(281��)������ͬʱ����");
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
    			alert("һ���¼��¡���ͨ�˲�(7��34��)���롾���˲еȼ�(281��)������ͬʱ����");
    			return false;
    		}
    	}
    	tOperator1="1";//�˲�
  		fm.Operate.value = "UPDATE";
			fm.action = "../g_claim/LLClaimNoMedicalDefoSave.jsp";
			submitForm();
}
/**
 * ɾ���˲��˵�
 */
function deleteMaimBill() {
	
	var tRow = MaimInfoGrid.getSelNo();

	if (tRow==0) {
		
		alert("����ѡ��һ���˲���Ϣ���ٽ���ɾ����");
		return false;
	}
	tOperator1="1";//�˲�
	if (confirm("��ȷʵ��ɾ���ü�¼��?")){
    
        fm.Operate.value = "DELETE";    			  
        fm.action = "../g_claim/LLClaimNoMedicalDefoSave.jsp";
        
        submitForm();
   	}
}
/**
	*�����˲��˵�
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
 * �����ؼ��˵�
 */
function addMajorBill() {
	
		if (fm.OperationType.value == null ||fm.OperationType.value == ""){
		
			alert("������������Ϣ��");
			return false;
    }
/*    if (fm.OpFee.value == null ||fm.OpFee.value == ""){
    	
			alert("����������Ϣ��");
			return false;
    }*/
    if (fm.OperationCode.value == null ||fm.OperationCode.value == ""){
    	
			alert("�����������Ϣ��");
			return false;
    }
    if (fm.UnitName.value == null || fm.UnitName.value == ""){
    	
			alert("������ҽ�ƻ������ƣ�");
			return false;
    }
    if(fm.DiagnoseDate.value==""||fm.DiagnoseDate.value==null){
    	
    	alert("ȷ�����ڲ���Ϊ��!");
      return false;
    }
    if (dateDiff(fm.DiagnoseDate.value,tCurrentDate,'D') < 0){
    	
			alert("ȷ�����ڲ��ܴ��ڵ�ǰ����!");
      return false;
    }
		if (dateDiff(fm.DiagnoseDate.value,tAccidentDate,'D')>0) {
    	
    		alert("�ش󼲲�ȷ���������ڳ������ڣ�������¼�룡");
    		return false;
    	}
    if(hasMoreOperation()){
    	
			alert("�ñ����˵Ĵ��¼��Ѿ�������һ���ش󼲲������������ӣ�");
			return false;	
			
    }
		tOperator1="2";//�ؼ�
		fm.Operate.value = "INSERT";
		fm.action = "../g_claim/LLClaimNoMedicalOperationSave.jsp";
		submitForm();
}
/**
 * �޸��ؼ��˵�
 */
function modifyMajorBill() {
	
	var tRow = MajorBillGrid.getSelNo();
	if (tRow==0) {	
		
		alert("����ѡ��һ���ؼ���Ϣ���ٽ����޸ģ�");
		return false;
	}
	if (fm.OperationType.value == null ||fm.OperationType.value == ""){
		alert("������������Ϣ��");
		return false;
  }
/*  if (fm.OpFee.value == null ||fm.OpFee.value == ""){
    	
		alert("����������Ϣ��");
		return false;
  }*/
  if (fm.OperationCode.value == null ||fm.OperationCode.value == ""){
  	
		alert("�����������Ϣ��");
		return false;
  }
  if (fm.UnitName.value == null || fm.UnitName.value == ""){
  	
		alert("������ҽ�ƻ������ƣ�");
		return false;
  }
  if(fm.DiagnoseDate.value==""||fm.DiagnoseDate.value==null){
    	
    	alert("ȷ�����ڲ���Ϊ��!");
      return false;
    }
  if (dateDiff(fm.DiagnoseDate.value,tCurrentDate,'D') < 0){
  	
		alert("ȷ�����ڲ��ܴ��ڵ�ǰ����!");
    return false;
  }
	
	if (dateDiff(fm.DiagnoseDate.value,tAccidentDate,'D')>0) {
    	
    		alert("�ش󼲲�ȷ���������ڳ������ڣ�������¼�룡");
    		return false;
    	}
  /**  if(hasMoreOperation()){  �����������
    	
			alert("�ñ����˵Ĵ��¼��Ѿ�������һ���ش󼲲������������ӣ�");
			return false;	
			
    }**/
    	tOperator1="2";//�ؼ�
			fm.Operate.value = "UPDATE";
			fm.action = "../g_claim/LLClaimNoMedicalOperationSave.jsp";
			submitForm();
}
/**
 * ɾ���ؼ��˵�
 */
function deleteMajorBill() {
	
	var tRow = MajorBillGrid.getSelNo();
	if (tRow==0) {	
		
		alert("����ѡ��һ���ؼ���Ϣ���ٽ���ɾ����");
		return false;
	}
	tOperator1="2";//�ؼ�
	if (confirm("��ȷʵ��ɾ���ü�¼��?")){
    
        fm.Operate.value = "DELETE";    			  
        fm.action = "../g_claim/LLClaimNoMedicalOperationSave.jsp";
        
        submitForm();
   	}
}

/**
 * �ύ����
 */
function submitForm() {
	
	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit();
}

/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		
	}	
	if(tOperator1=="1"){//�˲�
		
		resetDefoInfo();	
	}else if(tOperator1=="2"){//�ؼ�
		resetMajorInfo();
	}
}

/**
 * ������ѡ���Ժ����
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
				alert("��ѯ������Ϣʧ��");
				
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
        alert("��ѡ��һ�м�¼��");
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
		
		alert("��ѡ��һ�м�¼��");
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
/*�ؼ�����**/
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
		
			alert("�����������������!");
			return false;
				
		} else {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimNoMedicalSql");
		tSQLInfo.setSqlId("LLClaimNoMedical5");
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara(DefoName);
		
		var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
		if (arr == null) {
				
				alert("�����ڸ��˲���Ϣ��������¼�룡");
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