/***************************************************************
 * <p>ProName��LLClaimMedicalInput.js</p>
 * <p>Title��ҽ���˵�</p>
 * <p>Description��ҽ���˵�</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var turnPage5 = new turnPageClass();
var turnPage6 = new turnPageClass();
var turnPage7 = new turnPageClass();
var turnPage8 = new turnPageClass();
var tSQLInfo = new SqlClass();
var tAccidentDate;
var tCheckRule = /^(-)?(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/;
var tPageNo = 0;
var tIndexNo = 0;

/**
 * ���밸��
 */
function enterReport() {
	window.location.href="./LLClaimMajorApproveInput.jsp";		
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
	if(fm.currentInput.value=="1"){
		
		resClinicBillInfo();
		if (fm.Operate.value=="UPDATE") {
			setSelRow(ClinicGrid,turnPage1);
			getClinicInfo();					
		}			
	}else if (fm.currentInput.value=="2"){
		
		resHospBillInfo();
		if (fm.Operate.value=="UPDATE") {
			setSelRow(HospBillGrid,turnPage2);
			getHospBillInfo();					
		}			
			
	}else if(fm.currentInput.value=="3"){
		resSpecialClinicBillInfo();
		if (fm.Operate.value=="UPDATE") {
			setSelRow(SpecialClinicBillGrid,turnPage3);
			getSpecialClinicBillInfo();					
		}			
	}else if(fm.currentInput.value=="4"){
		
		resSpecialHospBillInfo();
		if (fm.Operate.value=="UPDATE") {
			setSelRow(SpecialHospGrid,turnPage4);
			getSpecialHospInfo();					
		}			
	}
}

// ��ѯ���׵Ŀ۳�����
function queryEasyDeductItem(DeductGrid){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
	tSQLInfo.setSqlId("LLClaimMedical1Sql1");
	tSQLInfo.addSubPara("1");
	var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
	
	//��ʾ��������
  if (arr){
	
		displayMultiline(arr,DeductGrid);
	}
}
//��ѯҽԺ��Ϣ
function queryHospInfo(objcode,objname){
	
	var HospName = objname.value;	
	if(HospName==""){
		return false;
	}	
	if (window.event.keyCode == "13") {
	
		var condition =" 1 and hospitalname like #%%"+ HospName +"%%# ";		
		
		window.event.keyCode = 0;
		if (HospName==null || trim(HospName)=="") {
		
				alert("������ҽԺ����!");
				return false;
				
		} else {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
		tSQLInfo.setSqlId("LLClaimMedical1Sql2");
		tSQLInfo.addSubPara(HospName);
		
		var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
		if (arr == null) {
				
				alert("�����ڸ�ҽԺ��Ϣ��������¼�룡");
				objcode.value = "";
				objname.value = "";
				
				return false;
			} else {
				if (arr.length == 1) {

					objcode.value = arr[0][0];
					objname.value = arr[0][1];
				} else {
					
					showCodeList('hospital', [objcode,objname], [0,1], null, condition , '1', 1, '400');
					
				}
			}
		}
	}
}
//��ѯ��Ҫ�����Ϣ
function queryICDInfo(objcode,objname,objcode1,objname1){
	
	var ICDName = objname.value;	
	if(ICDName == ""){
		objcode.value="";
		return false;
	}	

	if (window.event.keyCode == "13") {
	
		var condition =" 1 and icdname like #%"+ ICDName +"%# and icdlevel=#1# ";		
		
		window.event.keyCode = 0;
		if (ICDName==null || trim(ICDName)=="") {
		
				alert("��������Ҫ�������!");
				return false;
				
		} else {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
		tSQLInfo.setSqlId("LLClaimMedical1Sql3");
		tSQLInfo.addSubPara(ICDName);
		
		var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
		if (arr == null) {
				
				alert("�����ڸ���Ҫ�����Ϣ��������¼�룡");
				objcode.value = "";
				objname.value = "";
				
				return false;
			} else {
				objcode1.value="";
				objname1.value="";
				if (arr.length == 1) {

					objcode.value = arr[0][0];
					objname.value = arr[0][1];
				} else {
					
					showCodeList('diseasecode', [objcode,objname], [0,1], null,condition, "1", 1, '400');
				}
			}
		}
	}
}
//��ѯ��ϸ�����Ϣ
function queryICDDetailInfo(objcode,objname){

	var ICDNameDetail = objname.value;	
	var ICDNameDetail1= objcode1.value;
	
	if(ICDNameDetail == ""){
		objcode.value="";
		return false;
		}	
		if (window.event.keyCode == "13") {

		var condition ="1 and  icdname like #%"+ ICDNameDetail +"%# and upicdcode=#"+ ICDNameDetail1 +"# and icdlevel=#2#";

		window.event.keyCode = 0;
		
		if(ICDNameDetail1==null||trim(ICDNameDetail1)==""){
			
			alert("��������Ҫ�������!");
			return false;
		}	
		if (ICDNameDetail==null || trim(ICDNameDetail)=="") {
		
			alert("�����������������!");
			return false;
				
		} else {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
		tSQLInfo.setSqlId("LLClaimMedical1Sql4");
		tSQLInfo.addSubPara(ICDNameDetail);
		tSQLInfo.addSubPara(ICDNameDetail1);
		
		var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
		if (arr == null) {
				
				alert("�����ڸ���Ҫ�����Ϣ��������¼�룡");
				objcode.value = "";
				objname.value = "";
				
				return false;
			} else {

				if (arr.length == 1) {

					objcode.value = arr[0][0];
					objname.value = arr[0][1];
				} else {
					
					showCodeList('diseasecode', [objcode,objname], [0,1], null,condition, "1", 1, '400');
				}
			}
		}
	}
}

//У��ͬһ���ͻ���ͬ���¼��µ��˵����Ƿ��ظ�ʹ��
function isRepeatMainFeeNo(mainFeeNo){

		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
		tSQLInfo.setSqlId("LLClaimMedical1Sql8");
		tSQLInfo.addSubPara(tRgtNo);
		tSQLInfo.addSubPara(tCustomerNo);
		tSQLInfo.addSubPara(tCaseNo);
		tSQLInfo.addSubPara(mainFeeNo);
	
		var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
    if(arr != null &&arr[0][0] != ''){
    	return parseFloat(arr[0][0]);   	
    }else {
			return 0.00;    	
		}   	
}
/**��ѯ��������**/
function getCaseDateInfo(){
	

	if(tCaseSource=="01"){

		if(tRgtNo!=""&&tRgtNo!=null){
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
			tSQLInfo.setSqlId("LLClaimMedical1Sql15");
			tSQLInfo.addSubPara(tCaseNo);
			var arr1 = easyExecSql(tSQLInfo.getString(),1, 0, 1);
			tAccidentDate=arr1[0][0];
		}
	}
}
function queryClinicBillInfo(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
	tSQLInfo.setSqlId("LLClaimMedical1Sql9");
	tSQLInfo.addSubPara(tRgtNo);
	tSQLInfo.addSubPara(tCustomerNo);
	if(tCaseSource=="02"){//���˵���ԴΪ�Զ�����ʱ����ѯ�����Զ����ɵ��˵�
		tSQLInfo.addSubPara("");
	}else {
		tSQLInfo.addSubPara(tCaseNo);
	}
	tSQLInfo.addSubPara(tCaseSource);
	turnPage1.queryModal(tSQLInfo.getString(), ClinicGrid,"2");
}
function queryInitHospBillInfo(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
	tSQLInfo.setSqlId("LLClaimMedical1Sql11");
	tSQLInfo.addSubPara(tRgtNo);
	tSQLInfo.addSubPara(tCustomerNo);
		if(tCaseSource=="02"){//���˵���ԴΪ�Զ�����ʱ����ѯ�����Զ����ɵ��˵�
		tSQLInfo.addSubPara("");
	}else {
		tSQLInfo.addSubPara(tCaseNo);
	}
	tSQLInfo.addSubPara(tCaseSource);
	turnPage2.queryModal(tSQLInfo.getString(), HospBillGrid,"2");
}
function querySpecialClinicInfo(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
	tSQLInfo.setSqlId("LLClaimMedical1Sql12");
	tSQLInfo.addSubPara(tRgtNo);
	tSQLInfo.addSubPara(tCustomerNo);
		if(tCaseSource=="02"){//���˵���ԴΪ�Զ�����ʱ����ѯ�����Զ����ɵ��˵�
		tSQLInfo.addSubPara("");
	}else {
		tSQLInfo.addSubPara(tCaseNo);
	}
	tSQLInfo.addSubPara(tCaseSource);
	turnPage3.queryModal(tSQLInfo.getString(), SpecialClinicBillGrid,"2");

}
function querySpecialHospInfo(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
	tSQLInfo.setSqlId("LLClaimMedical1Sql14");
	tSQLInfo.addSubPara(tRgtNo);
	tSQLInfo.addSubPara(tCustomerNo);
		if(tCaseSource=="02"){//���˵���ԴΪ�Զ�����ʱ����ѯ�����Զ����ɵ��˵�
		tSQLInfo.addSubPara("");
	}else {
		tSQLInfo.addSubPara(tCaseNo);
	}
	tSQLInfo.addSubPara(tCaseSource);
	turnPage4.queryModal(tSQLInfo.getString(), SpecialHospGrid,"2");
	
}
function getClinicInfo(){
	 
	 var i = ClinicGrid.getSelNo();
   if(i < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return false;
     }
	  i = ClinicGrid.getSelNo()-1;
	  fm.tCaseNo1.value=ClinicGrid.getRowColData(i,1);
    fm.ClinicBillNo.value=ClinicGrid.getRowColData(i,2);
    fm.ClinicBillNo1.value=ClinicGrid.getRowColData(i,2);
    fm.ClinicHosID.value=ClinicGrid.getRowColData(i,3);
    fm.ClinicHosName.value=ClinicGrid.getRowColData(i,4);
    fm.ClinicStartdate.value=ClinicGrid.getRowColData(i,5);
    fm.ClinicBillMoney.value=ClinicGrid.getRowColData(i,6);
    fm.ClinicICDNo.value=ClinicGrid.getRowColData(i,9);
    fm.ClinicICDName.value=ClinicGrid.getRowColData(i,10);
    fm.ClinicICDDetail.value=ClinicGrid.getRowColData(i,11);
    fm.ClinicICDDetailName.value=ClinicGrid.getRowColData(i,12);
    fm.ClinicScanNum.value=ClinicGrid.getRowColData(i,14);
		fm.ClinicRemark.value=ClinicGrid.getRowColData(i,15);
		
		fm.tSerialNo1.value=ClinicGrid.getRowColData(i,16);
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
		tSQLInfo.setSqlId("LLClaimMedical1Sql10");
		tSQLInfo.addSubPara(tRgtNo);
		tSQLInfo.addSubPara(fm.tSerialNo1.value);
		
		var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
		 if(arr!=null&&arr!=''){
    	displayMultiline(arr,ClinicBillItemGrid);
    	
    }else{
    	initClinicBillItemGrid();
			queryEasyDeductItem(ClinicBillItemGrid);
    }
   	 initScanPages(fm.ClinicScanNum.value);
    
}
function getHospBillInfo(){
		 var i = HospBillGrid.getSelNo();
   if(i < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return false;
     }
      i = HospBillGrid.getSelNo()-1;
		fm.tCaseNo2.value =HospBillGrid.getRowColData(i,1);
		fm.HospBillNo.value=HospBillGrid.getRowColData(i,2);
    fm.HospBillNo1.value=HospBillGrid.getRowColData(i,2);
    fm.HospID.value=HospBillGrid.getRowColData(i,3);
    fm.HospIDName.value=HospBillGrid.getRowColData(i,4);
    fm.HospStartdate.value=HospBillGrid.getRowColData(i,5);
    fm.HospEnddate.value=HospBillGrid.getRowColData(i,6);
    fm.HospDays.value=HospBillGrid.getRowColData(i,7);
    fm.HospBillMoney.value=HospBillGrid.getRowColData(i,8);
    fm.HospICDNo.value=HospBillGrid.getRowColData(i,11);
    fm.HospICDNoName.value=HospBillGrid.getRowColData(i,12);
    fm.HospICDDetail.value=HospBillGrid.getRowColData(i,13);
    fm.HospICDDetailName.value=HospBillGrid.getRowColData(i,14);
    fm.HospScanNum.value=HospBillGrid.getRowColData(i,16);
		fm.HospRemark.value=HospBillGrid.getRowColData(i,17);
		
		fm.tSerialNo2.value=HospBillGrid.getRowColData(i,18);
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
		tSQLInfo.setSqlId("LLClaimMedical1Sql10");
		tSQLInfo.addSubPara(tRgtNo);
		tSQLInfo.addSubPara(fm.tSerialNo2.value);
		
		var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
		 if(arr!=null&&arr!=''){
    	displayMultiline(arr,HospBillItemGrid);
    	
    }else{
    	initClinicBillItemGrid();
			queryEasyDeductItem(HospBillItemGrid);
    }
     initScanPages(fm.HospScanNum.value);
}
function getSpecialClinicBillInfo(){
	
		 var i = SpecialClinicBillGrid.getSelNo();
   if(i < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return false;
     }
	  i = SpecialClinicBillGrid.getSelNo()-1;
		fm.tCaseNo3.value =SpecialClinicBillGrid.getRowColData(i,1);
    fm.SpecialClinicNo.value=SpecialClinicBillGrid.getRowColData(i,2);
    fm.SpecialClinicBill1.value=SpecialClinicBillGrid.getRowColData(i,2);
    fm.SpecialClinicHospID.value=SpecialClinicBillGrid.getRowColData(i,3);
    fm.SpecialClinicHospIDName.value=SpecialClinicBillGrid.getRowColData(i,4);
    fm.SpecialClinicStart.value=SpecialClinicBillGrid.getRowColData(i,5);
    fm.SpecialClinicMoney.value=SpecialClinicBillGrid.getRowColData(i,6);
    fm.SpecialClinicICD.value=SpecialClinicBillGrid.getRowColData(i,9);
    fm.SpecialClinicICDName.value=SpecialClinicBillGrid.getRowColData(i,10);
    fm.SpecialClinicICDDetail.value=SpecialClinicBillGrid.getRowColData(i,11);
    fm.SpecialClinicICDDetailName.value=SpecialClinicBillGrid.getRowColData(i,12);
    fm.SpecialClinicScanNum.value=SpecialClinicBillGrid.getRowColData(i,14);
		fm.SpecailClinicRemark.value=SpecialClinicBillGrid.getRowColData(i,15);

		fm.tSerialNo3.value =SpecialClinicBillGrid.getRowColData(i,16);
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
		tSQLInfo.setSqlId("LLClaimMedical1Sql13");
		tSQLInfo.addSubPara(tRgtNo);
		tSQLInfo.addSubPara(tCustomerNo);
		tSQLInfo.addSubPara(fm.tCaseNo3.value);
		tSQLInfo.addSubPara(fm.tSerialNo3.value);
		turnPage5.pageLineNum = 1000;
		turnPage5.queryModal(tSQLInfo.getString(), SpecialClinicItemGrid,2,1);
		initScanPages(fm.SpecialClinicScanNum.value);
}
function getSpecialHospInfo(){
		
		var i = SpecialHospGrid.getSelNo();
   	if(i < 1){
        alert("��ѡ��һ�м�¼��");
        return false;
     }
		i = SpecialHospGrid.getSelNo()-1;
		fm.tCaseNo4.value =SpecialHospGrid.getRowColData(i,1);
		fm.SpecialHospBillNo.value=SpecialHospGrid.getRowColData(i,2);
    fm.SpecialHospBillNo1.value=SpecialHospGrid.getRowColData(i,2);
    fm.SpecialHospID.value=SpecialHospGrid.getRowColData(i,3);
    fm.SpecialHospIDName.value=SpecialHospGrid.getRowColData(i,4);
    fm.SpecialHospStart.value=SpecialHospGrid.getRowColData(i,5);
    fm.SpecialHospEnd.value=SpecialHospGrid.getRowColData(i,6);
    fm.SpecialHospNum.value=SpecialHospGrid.getRowColData(i,7);
    fm.SpecialHospBillMoney.value=SpecialHospGrid.getRowColData(i,8);
    fm.SpecialHospICD.value=SpecialHospGrid.getRowColData(i,11);
    fm.SpecialHospICDName.value=SpecialHospGrid.getRowColData(i,12);
    fm.SpecialHospICDDetail.value=SpecialHospGrid.getRowColData(i,13);
    fm.SpecialHospICDDetailName.value=SpecialHospGrid.getRowColData(i,14);
    fm.SpecialHospScanNum.value=SpecialHospGrid.getRowColData(i,16);
		fm.SpecialHospRemark.value=SpecialHospGrid.getRowColData(i,17);

		fm.tSerialNo4.value =SpecialHospGrid.getRowColData(i,18);

		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
		tSQLInfo.setSqlId("LLClaimMedical1Sql13");
		tSQLInfo.addSubPara(tRgtNo);
		tSQLInfo.addSubPara(tCustomerNo);
		tSQLInfo.addSubPara(fm.tCaseNo4.value);
		tSQLInfo.addSubPara(fm.tSerialNo4.value);
		turnPage6.pageLineNum = 1000;
		turnPage6.queryModal(tSQLInfo.getString(), SpecialHospItemGrid,2,1);
		initScanPages(fm.SpecialHospScanNum.value);
}
function addClinicBill(){
	//�ǿռ���
	if (fm.ClinicBillNo.value == "" || fm.ClinicBillNo.value == null){
		
		alert("�˵��Ų���Ϊ�գ�");
		return false;
	}  
	//���ڼ���
	if (fm.ClinicStartdate.value == ''){
    	
		alert("�������ڲ���Ϊ��!");
		return false;
	}	
	if(fm.ClinicHosID.value == "" ||fm.ClinicHosName.value== ""){
		
		alert("����ҽԺ����Ϊ��!");
		return false;
	}

	//��ҽ���ڲ��ܴ���ϵͳ��ǰ����
	if (dateDiff(fm.ClinicStartdate.value,tCurrentDate,'D')< 0 ){
		
		alert("�������ڲ��ܴ��ڵ�ǰ����!");
		return false;
    }

    //��ҽ���ڲ������ڳ�������
    
  if(tCaseSource=="01"){
		if(dateDiff(tAccidentDate,fm.ClinicStartdate.value,'D')<0){
		
			alert("�����������ڳ������ڣ�������¼�룡");
			return false;
    	}
	}
    //�����˵����Ϊ��¼�� 
	if(fm.ClinicBillMoney.value==null || fm.ClinicBillMoney.value==''){
		alert("�˵�����Ϊ�գ�");
		return false;	
    }

	if(!(fm.ClinicBillMoney.value>0&&isNumeric(fm.ClinicBillMoney.value))){
		alert("�˵�������Ϊ����0������");
		return false;	
    }

	if (fm.ClinicICDNo.value==null || fm.ClinicICDNo.value==""){
		
		alert("��Ҫ��ϲ���Ϊ��!");
		return false;
    }
    var ClinicICDNo = fm.ClinicICDNo.value;
    var ClinicICDName =trim(fm.ClinicICDName.value);
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
		tSQLInfo.setSqlId("LLClaimMedical1Sql7");
		tSQLInfo.addSubPara(ClinicICDNo);
		tSQLInfo.addSubPara(ClinicICDName);

		var arr1 = easyExecSql(tSQLInfo.getString(),1, 0, 1);
		if(arr1[0][0] == 0){
			
			alert("����Ҫ�����Ϣ��ϵͳ�в����ڣ�������¼��");
			return false;
		}
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
		tSQLInfo.setSqlId("LLClaimMedical1Sql5");
		tSQLInfo.addSubPara(tCustomerNo);
		tSQLInfo.addSubPara(fm.ClinicBillNo.value);
		
		var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
		if(arr[0][0] > 0){
			
			alert("���ʵ����ڸó��������Ѿ����ڣ������������ʵ��ţ�");
			return false;
		}

		var repeatFlag = false;
    var tBillMoney = 0.00; 
		var rowNum=ClinicBillItemGrid.mulLineCount ;//����    
	  //var colNum=EasyClinicInpGrid.colCount ;//����
	  for(var i=0;i<rowNum;i++){
	  			 
	  	if(((!tCheckRule.test(ClinicBillItemGrid.getRowColData(i,3))) && (!ClinicBillItemGrid.getRowColData(i,3)==""))){
			
			alert("�۳����¼�벻��ȷ��") ;
			return false;
		}	
	  	if(!isNumeric(ClinicBillItemGrid.getRowColData(i,3))&&ClinicBillItemGrid.getRowColData(i,3)!=null&&ClinicBillItemGrid.getRowColData(i,3)!=''){
	  		
	  		alert(ClinicBillItemGrid.getRowColData(i,2)+"����������");
				return false;
		}
	    if (ClinicBillItemGrid.getRowColData(i,3)<0) {
	  		alert("�۳����������0");
			return false;
	    }
	}

	fm.Operate.value="INSERT";
    fm.currentInput.value = "1";
    fm.action = "./LLClinicBillSave.jsp";
    submitForm();
	}
function modifyClinicBill(){
	
	var tSelNo = ClinicGrid.getSelNo();
   if(tSelNo < 1){
		
		alert("��ѡ��һ�м�¼��");
		return false;
		}
		
	if (fm.ClinicBillNo.value == "" || fm.ClinicBillNo.value == null){
		
		alert("�˵��Ų���Ϊ�գ�");
		return false;
	}  
	//���ڼ���
	if (fm.ClinicStartdate.value == ''){
    	
		alert("�������ڲ���Ϊ��!");
		return false;
	}	
	if(fm.ClinicHosID.value == "" ||fm.ClinicHosName.value== ""){
		
		alert("����ҽԺ����Ϊ��!");
		return false;
	}

	//��ҽ���ڲ��ܴ���ϵͳ��ǰ����
	if (dateDiff(fm.ClinicStartdate.value,tCurrentDate,'D')< 0 ){
		
		alert("�������ڲ��ܴ��ڵ�ǰ����!");
		return false;
    }
    //��ҽ���ڲ������ڳ�������
   
 if(tCaseSource=="01" ){
    //��ҽ���ڲ������ڳ�������
		if(dateDiff(tAccidentDate,fm.ClinicStartdate.value,'D')<0 ){
			
			alert("�����������ڳ������ڣ�������¼�룡");
			return false;
	    }
    	//��ʾ�ͻ�Ŀǰ����������ڵ�����

	}
    //�����˵����Ϊ��¼�� 
	if(fm.ClinicBillMoney.value==null || fm.ClinicBillMoney.value==''){
		alert("�˵�����Ϊ�գ�");
		return false;	
    }

	if(!(fm.ClinicBillMoney.value>0&&isNumeric(fm.ClinicBillMoney.value))){
		alert("�˵�������Ϊ����0������");
		return false;	
    }

	if (fm.ClinicICDNo.value==null || fm.ClinicICDNo.value==""){
		
		alert("��Ҫ��ϲ���Ϊ��!");
		return false;
    }
    var ClinicICDNo = fm.ClinicICDNo.value;
    var ClinicICDName =trim(fm.ClinicICDName.value);
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
		tSQLInfo.setSqlId("LLClaimMedical1Sql7");
		tSQLInfo.addSubPara(ClinicICDNo);
		tSQLInfo.addSubPara(ClinicICDName);

		var arr1 = easyExecSql(tSQLInfo.getString(),1, 0, 1);
		if(arr1[0][0] == 0){
			
			alert("����Ҫ�����Ϣ��ϵͳ�в����ڣ�������¼��");
			return false;
		}
		
		if(fm.ClinicBillNo.value!=fm.ClinicBillNo1.value){
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
			tSQLInfo.setSqlId("LLClaimMedical1Sql5");
			tSQLInfo.addSubPara(tCustomerNo);
			tSQLInfo.addSubPara(fm.ClinicBillNo.value);
			
			var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
			if(arr[0][0] > 0){
				
				alert("���ʵ����Ѿ����ڣ������������ʵ��ţ�");
				return false;
			}
		}


		var repeatFlag = false;
    var tBillMoney = 0.00; 
		var rowNum=ClinicBillItemGrid.mulLineCount ;//����    
	  //var colNum=EasyClinicInpGrid.colCount ;//����
	  for(var i=0;i<rowNum;i++){
	  	
	  	if(((!tCheckRule.test(ClinicBillItemGrid.getRowColData(i,3))) && (!ClinicBillItemGrid.getRowColData(i,3)==""))){
			
				alert("�۳����¼�벻��ȷ��") ;
				return false;
		}	
	  	if(!isNumeric(ClinicBillItemGrid.getRowColData(i,3))&&ClinicBillItemGrid.getRowColData(i,3)!=null&&ClinicBillItemGrid.getRowColData(i,3)!=''){
	  		
	  		alert(ClinicBillItemGrid.getRowColData(i,2)+"����������");
				return false;
		}
	    if (ClinicBillItemGrid.getRowColData(i,3)<0) {
	  		alert("�۳����������0");
			return false;
	    }
	}

	fm.tempHosp.value=fm.ClinicHosID.value;
	fm.tempHospStart.value=fm.ClinicStartdate.value;
	fm.tempHospEnd.value=fm.ClinicStartdate.value;
	fm.tempResult1.value=fm.ClinicICDNo.value;	
	
	tPageNo = turnPage1.pageIndex;
	tIndexNo = tSelNo;
	fm.SelNo.value = tIndexNo;
	fm.PageIndex.value = tPageNo;
		
	fm.Operate.value="UPDATE";
    fm.currentInput.value = "1";
    fm.action = "./LLClinicBillSave.jsp";
    submitForm();	
}
//ɾ�����������˵�
function deleteClinicBill(){
	
	if(ClinicGrid.getSelNo() == 0){
		
	  	alert("����ѡ��һ����¼!");
	  	return false;
		}
	if(ClinicGrid.getSelNo() > 0) {
		
		if (confirm("��ȷʵ��ɾ���ü�¼��?")) {
			fm.Operate.value="DELETE";
			fm.currentInput.value = "1";
			fm.action = "./LLClinicBillSave.jsp";
      submitForm();
			}else{
			
			fm.Operate.value="";
       }
    }else{
        alert("��ѡ��һ����¼��");
        return false;
    }
  }
  //��������סԺ�˵�
  function addHospBill(){
    
    //�ǿռ���
    if (fm.HospBillNo.value == "" || fm.HospBillNo.value == null){
    	
        alert("�˵��Ų���Ϊ�գ�");
        return;
    }
    //���ڼ���
    if (fm.HospStartdate.value == '' ||fm.HospStartdate.value== null) {
    	
        alert("��Ժ���ڲ���Ϊ�գ�");
        return;
    }
   
    if (fm.HospID.value == '' ||fm.HospIDName.value== '') {
    	
        alert("����ҽԺ����Ϊ�գ�");
        return;
    }	
    	
    if(fm.HospEnddate.value == ''||fm.HospEnddate.value ==null) 
    {
    	  alert("��Ժ���ڲ���Ϊ�գ�");
    	  return false;
    }
		
    var tStartDate=fm.HospStartdate.value;
    var tEndDate=fm.HospEnddate.value;
    if(dateDiff(tStartDate,tCurrentDate,'D')<0){
    	
    		alert("��Ժ���ڲ������ڵ�ǰ���ڣ�");
    		return false;	
    }
    
    if(dateDiff(tEndDate,tCurrentDate,'D')<0){
    	
    		alert("��Ժ���ڲ������ڵ�ǰ���ڣ�");
    		return false;	
    }
    
    //סԺ���ڲ������ڳ�Ժ����
    if(dateDiff(tStartDate,tEndDate,'D')<0) {
    	
    		alert("��Ժ���ڲ������ڳ�Ժ���ڣ�");
    		return false;	
    }
 		if(tCaseSource=="01" ){
	    
	    //סԺ���ڲ������ڳ�������
	    if(dateDiff(tAccidentDate,tStartDate,'D')<0){
	    	
	    		alert("��Ժ�������ڳ������ڣ�");
	    		return false;	
	    }
	    
    }
    
    //�����˵����Ϊ��¼�� 
    if(fm.HospBillMoney.value==null || fm.HospBillMoney.value=='') {
    	alert("�˵���������Ϊ�գ�");
    	return false;	
    }
    
    if(!(fm.HospBillMoney.value>0&&isNumeric(fm.HospBillMoney.value))){
    	alert("�˵�������Ϊ����0������");
    	return false;	
    }

   var tDays=dateDiff(tStartDate,tEndDate,'D');  
  	fm.HospDays.value = tDays;

		if (fm.HospICDNo.value==null || fm.HospICDNo.value==""){
		
			alert("��Ҫ��ϲ���Ϊ��!");
			return false;
    }
    var ClinicICDNo = fm.HospICDNo.value;
    var ClinicICDName =trim(fm.HospICDNoName.value);
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
		tSQLInfo.setSqlId("LLClaimMedical1Sql7");
		tSQLInfo.addSubPara(ClinicICDNo);
		tSQLInfo.addSubPara(ClinicICDName);

		var arr1 = easyExecSql(tSQLInfo.getString(),1, 0, 1);
		if(arr1[0][0] == 0){
			
			alert("����Ҫ�����Ϣ��ϵͳ�в����ڣ�������¼��");
			return false;
		}

		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
		tSQLInfo.setSqlId("LLClaimMedical1Sql5");
		tSQLInfo.addSubPara(tCustomerNo);
		tSQLInfo.addSubPara(fm.HospBillNo.value);
		
		var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
		if(arr[0][0] > 0){
			
			alert("���ʵ����Ѿ����ڣ������������ʵ��ţ�");
			return false;
		}
    var rowNum=HospBillItemGrid.mulLineCount ;//����
	  //var colNum=EasyClinicInpGrid.colCount ;//����
	  for(var i=0;i<rowNum;i++){
	  	
	  		if(((!tCheckRule.test(HospBillItemGrid.getRowColData(i,3))) && (!HospBillItemGrid.getRowColData(i,3)==""))){
			
				alert("�۳����¼�벻��ȷ��") ;
				return false;
			}	
			if(!isNumeric(HospBillItemGrid.getRowColData(i,3))&&HospBillItemGrid.getRowColData(i,3)!=null&&HospBillItemGrid.getRowColData(i,3)!=''){
				
				alert(HospBillItemGrid.getRowColData(i,2)+"����������");
				return false;
			  }
		    if (HospBillItemGrid.getRowColData(i,3)<0) {
		  		alert("�۳����������0");
				return false;
		    }
			
		}
		
    fm.Operate.value="INSERT";
    fm.currentInput.value = "2";
    fm.action = "./LLHospBillSave.jsp";
    submitForm();
  	
  }
function modifyHospBill(){
	
		var tSelNo = HospBillGrid.getSelNo();
   if(tSelNo < 1){
		
		alert("��ѡ��һ�м�¼��");
		return false;
		}
	 //�ǿռ���
    if (fm.HospBillNo.value == "" || fm.HospBillNo.value == null){
    	
        alert("�˵��Ų���Ϊ�գ�");
        return;
    }
    if (fm.HospID.value == '' ||fm.HospIDName.value== '') {
    	
        alert("����ҽԺ����Ϊ�գ�");
        return;
    }	
    //���ڼ���
    if (fm.HospStartdate.value == '' ||fm.HospStartdate.value== null) {
    	
        alert("��Ժ���ڲ���Ϊ�գ�");
        return;
    }
    if(fm.HospEnddate.value == ''||fm.HospEnddate.value ==null) 
    {
    	  alert("��Ժ���ڲ���Ϊ�գ�");
    	  return false;
    }
		
    var tStartDate=fm.HospStartdate.value;
    var tEndDate=fm.HospEnddate.value;
    if(dateDiff(tStartDate,tCurrentDate,'D')<0){
    	
    		alert("��Ժ���ڲ������ڵ�ǰ���ڣ�");
    		return false;	
    }
    
    if(dateDiff(tEndDate,tCurrentDate,'D')<0){
    	
    		alert("��Ժ���ڲ������ڵ�ǰ���ڣ�");
    		return false;	
    }
    
    //סԺ���ڲ������ڳ�Ժ����
    if(dateDiff(tStartDate,tEndDate,'D')<0) {
    	
    		alert("��Ժ���ڲ������ڳ�Ժ���ڣ�");
    		return false;	
    }
 		if(tCaseSource=="01" ){
    //סԺ���ڲ������ڳ�������
	    if(dateDiff(tAccidentDate,tStartDate,'D')<0){
	    	
	    		alert("��Ժ�������ڳ������ڣ�");
	    		return false;	
	    }
	  }
    //�����˵����Ϊ��¼�� 
    if(fm.HospBillMoney.value==null || fm.HospBillMoney.value=='') {
    	alert("�˵���������Ϊ�գ�");
    	return false;	
    }
    
    if(!(fm.HospBillMoney.value>0&&isNumeric(fm.HospBillMoney.value))){
    	alert("�˵�������Ϊ����0������");
    	return false;	
    }
    
   var tDays=dateDiff(tStartDate,tEndDate,'D');  
  	fm.HospDays.value = tDays;
		if (fm.HospICDNo.value==null || fm.HospICDNo.value==""){
		
			alert("��Ҫ��ϲ���Ϊ��!");
			return false;
    }
    var ClinicICDNo = fm.HospICDNo.value;
    var ClinicICDName =trim(fm.HospICDNoName.value);
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
		tSQLInfo.setSqlId("LLClaimMedical1Sql7");
		tSQLInfo.addSubPara(ClinicICDNo);
		tSQLInfo.addSubPara(ClinicICDName);

		var arr1 = easyExecSql(tSQLInfo.getString(),1, 0, 1);
		if(arr1[0][0] == 0){
			
			alert("����Ҫ�����Ϣ��ϵͳ�в����ڣ�������¼��");
			return false;
		}
	if(fm.HospBillNo.value !=fm.HospBillNo1.value){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
		tSQLInfo.setSqlId("LLClaimMedical1Sql5");
		tSQLInfo.addSubPara(tCustomerNo);
		tSQLInfo.addSubPara(fm.HospBillNo.value);
		
		var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
		if(arr[0][0] > 0){
			
			alert("���ʵ����Ѿ����ڣ������������ʵ��ţ�");
			return false;
		}
	}
    var rowNum=HospBillItemGrid.mulLineCount ;//����
	  //var colNum=EasyClinicInpGrid.colCount ;//����
	  for(var i=0;i<rowNum;i++){
	  	
		  	if(((!tCheckRule.test(HospBillItemGrid.getRowColData(i,3))) && (!HospBillItemGrid.getRowColData(i,3)==""))){
			
				alert("�۳����¼�벻��ȷ��") ;
				return false;
			}	
			if(!isNumeric(HospBillItemGrid.getRowColData(i,3))&&HospBillItemGrid.getRowColData(i,3)!=null&&HospBillItemGrid.getRowColData(i,3)!=''){
				
				alert(HospBillItemGrid.getRowColData(i,2)+"����������");
				return false;
			  }
		    if (HospBillItemGrid.getRowColData(i,3)<0) {
		  		alert("�۳����������0");
				return false;
		    }
		}
		fm.tempHosp.value=fm.HospID.value;
		fm.tempHospStart.value=fm.HospStartdate.value;
		fm.tempHospEnd.value=fm.HospEnddate.value;
		fm.tempResult1.value=fm.HospICDNo.value;
  
	tPageNo = turnPage2.pageIndex;
	tIndexNo = tSelNo;
	fm.SelNo.value = tIndexNo;
	fm.PageIndex.value = tPageNo;		
		
    fm.Operate.value="UPDATE";
    fm.currentInput.value = "2";
    fm.action = "./LLHospBillSave.jsp";
    submitForm();
  		
}
//ɾ������סԺ�˵�
function deleteHospBill(){
	
	if(HospBillGrid.getSelNo() == 0){
		
	  	alert("����ѡ��һ����¼!");
	  	return false;
		}
	if(HospBillGrid.getSelNo() > 0) {
		
		if (confirm("��ȷʵ��ɾ���ü�¼��?")) {
			fm.Operate.value="DELETE";
			fm.currentInput.value = "2";
			fm.action = "./LLHospBillSave.jsp";
      submitForm();
			}else{
			
			fm.Operate.value="";
       }
    }else{
        alert("��ѡ��һ����¼��");
        return false;
    }
  }
//���������˵�����
function resClinicBillInfo(){
	
	fm.tCaseNo1.value=tCaseNo;
	
	  fm.ClinicBillNo.value="";
    fm.ClinicBillNo1.value="";
    fm.ClinicHosID.value="";
    fm.ClinicHosName.value="";
    fm.ClinicStartdate.value="";
    fm.ClinicBillMoney.value="";
    fm.ClinicICDNo.value="";
    fm.ClinicICDName.value="";
    fm.ClinicICDDetail.value="";
    fm.ClinicICDDetailName.value="";
    fm.ClinicScanNum.value="";
		fm.ClinicRemark.value="";
		fm.currentInput.value = "";
		//fm.tCaseNo1.value="";
		
		//�޸Ĳ�����ɺ�չʾѡ����˵�
		
		
		initClinicGrid();
		queryClinicBillInfo();
		initClinicBillItemGrid();
		queryEasyDeductItem(ClinicBillItemGrid);
}
//����סԺ�˵�����
function resHospBillInfo(){
	
	fm.tCaseNo2.value=tCaseNo;
		fm.HospBillNo.value="";
    fm.HospBillNo1.value="";
    fm.HospID.value="";
    fm.HospIDName.value="";
    fm.HospStartdate.value="";
    fm.HospEnddate.value="";
    fm.HospDays.value="";
    fm.HospBillMoney.value="";
    fm.HospICDNo.value="";
    fm.HospICDNoName.value="";
    fm.HospICDDetail.value="";
    fm.HospICDDetailName.value="";
    fm.HospScanNum.value="";
		fm.HospRemark.value="";
		//fm.tCaseNo2.value="";
		
		InitHospBillGrid();
		queryInitHospBillInfo();
		initHospBillItemGrid();
		queryEasyDeductItem(HospBillItemGrid);
}
//���������˵�����
function resSpecialClinicBillInfo(){
	
	fm.tCaseNo3.value=tCaseNo;
	  fm.SpecialClinicNo.value="";
    fm.SpecialClinicBill1.value="";
    fm.SpecialClinicHospID.value="";
    fm.SpecialClinicHospIDName.value="";
    fm.SpecialClinicStart.value="";
    fm.SpecialClinicMoney.value="";
    fm.SpecialClinicICD.value="";
    fm.SpecialClinicICDName.value="";
    fm.SpecialClinicICDDetail.value="";
    fm.SpecialClinicICDDetailName.value="";
    fm.SpecialClinicScanNum.value="";
		fm.SpecailClinicRemark.value="";
		//fm.tCaseNo3.value="";
		initSpecialClinicBillGrid();
		initSpecialClinicItemGrid();
		querySpecialClinicInfo();

}
//����סԺ�˵�����
function resSpecialHospBillInfo(){
	
	fm.tCaseNo4.value=tCaseNo;
		fm.SpecialHospBillNo.value="";
    fm.SpecialHospBillNo1.value="";
    fm.SpecialHospID.value="";
    fm.SpecialHospIDName.value="";
    fm.SpecialHospStart.value="";
    fm.SpecialHospEnd.value="";
    fm.SpecialHospNum.value="";
    fm.SpecialHospBillMoney.value="";
    fm.SpecialHospICD.value="";
    fm.SpecialHospICDName.value="";
    fm.SpecialHospICDDetail.value="";
    fm.SpecialHospICDDetailName.value="";
    fm.SpecialHospScanNum.value="";
		fm.SpecialHospRemark.value="";
		//fm.tCaseNo4.value="";
		
		initSpecialHospGrid();
		initSpecialHospItemGrid();
		querySpecialHospInfo();
	
}
//���ݿ�ʼ�������ڼ���סԺ����
function calculateDate(k) {
	if(k==1){
		if (fm.HospStartdate.value == "" || fm.HospEnddate.value == "") {
			
			return false;
		}else {
				
			fm.HospDays.value = dateDiff(fm.HospStartdate.value,fm.HospEnddate.value,'D');
			
			if (fm.HospDays.value<0) {
				
				fm.HospDays.value = 0;
			}
		}
	}else if (k=2){
		if (fm.SpecialHospStart.value == "" || fm.SpecialHospEnd.value == "") {
			
			return false;
		}else {
				
			fm.SpecialHospNum.value = dateDiff(fm.SpecialHospStart.value,fm.SpecialHospEnd.value,'D');
			
			if (fm.SpecialHospNum.value<0) {
				
				fm.SpecialHospNum.value = 0;
			}
		}	
	}
}
//����-���������˵�
function addSpeicalClinic(){
	
    //�ǿռ���
    if (fm.SpecialClinicNo.value == "" || fm.SpecialClinicNo.value == null)
    {
        alert("�˵��Ų���Ϊ�գ�");
        return;
    } 
   
    //���ڼ���
    if (fm.SpecialClinicStart.value == '')
    {
        alert("�������ڲ���Ϊ��!");
        return;
    }    
    
    if (fm.SpecialClinicHospID.value == "" || fm.SpecialClinicHospIDName.value == "")
    {
        alert("����ҽԺ����Ϊ�գ�");
        return;
    } 
    
    //��ҽ���ڲ��ܴ���ϵͳ��ǰ����
    if (dateDiff(fm.SpecialClinicStart.value,tCurrentDate,'D')< 0 )
    {
        alert("�������ڲ��ܴ��ڵ�ǰ����!");
        return;
    }
    if(fm.SpecialClinicMoney.value==""){
    		
    		alert("�˵���������Ϊ��!");
        return;
    }
    //��ҽ���ڲ������ڳ�������
		 if(tCaseSource=="01" ){
	    if(dateDiff(tAccidentDate,fm.SpecialClinicStart.value,'D')<0)
	    {
	        alert("�����������ڳ������ڣ�������¼�룡");
	        return false;
	    }
	  }

    var rowNum=SpecialClinicItemGrid.mulLineCount ;//����
    //����Ҫ����¼��һ����ý��
    var strbillMoney = fm.SpecialClinicMoney.value;
    var inFlag = false;	
    var sumFeeMoney = 0;							
    for(var i=0; i<rowNum;i++)
    {
    	var feeMoney = SpecialClinicItemGrid.getRowColData(i,3);
    	if( feeMoney != null &&feeMoney !='')	
    	{
    		inFlag = true;    			
    		sumFeeMoney = parseFloat(sumFeeMoney) + parseFloat(feeMoney);     		
    	}    	
    }
    if(!inFlag)
    {
    	alert("����Ҫ¼��һ����ý�");	
    	return false;
    }
    if(inFlag)
    {
    	if(sumFeeMoney < 0.001)
    	{
    		alert("¼��ķ��ý��֮��Ӧ����0");
    		 return false;	
    	}	
    }    
	  //var colNum=EasyClinicInpGrid.colCount ;//����
	  for(var i=0;i<rowNum;i++)
	  {
	  	if(SpecialClinicItemGrid.getRowColData(i,1)==""||SpecialClinicItemGrid.getRowColData(i,2)==""){
	  		alert("�������ʹ�����������Ͳ���Ϊ��");
	  		return false;
	  	}
    		for(var j=3;j<7;j++) {
	  			if(!isNumeric(SpecialClinicItemGrid.getRowColData(i,j))
	  			   &&SpecialClinicItemGrid.getRowColData(i,j)!=null
	  			   &&SpecialClinicItemGrid.getRowColData(i,j)!='')
		  		{
					  alert(SpecialClinicItemGrid.getRowColData(i,2)+"�ķ��ý�����������");
					  return false;
		  		}
	  			if (SpecialClinicItemGrid.getRowColData(i,j)<0) {
					  alert("�Էѽ��������0");
					  return false;
	  			}
		    }
		}
			//��¼���˵��������ʾ�˵�����������ý��֮���Ƿ����
	if(strbillMoney !=null && strbillMoney !=''&& inFlag)
    {
    	var billMoney = parseFloat(strbillMoney);
    	var m = Math.abs(billMoney-sumFeeMoney);
    	if(m > 0.001)
    	{
	 	/*	if(!confirm("������ý��֮�����˵��������Ƿ񱣴�?"))
    		{
    			return;
    		}*/
    		alert("������ý��֮�����˵�����ȣ��������");
    		return false;
		}
    }
		if (fm.SpecialClinicICD.value==null || fm.SpecialClinicICD.value==""){
		
		alert("��Ҫ��ϲ���Ϊ��!");
		return false;
    }
    var ClinicICDNo = fm.SpecialClinicICD.value;
    var ClinicICDName =trim(fm.SpecialClinicICDName.value);
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
		tSQLInfo.setSqlId("LLClaimMedical1Sql7");
		tSQLInfo.addSubPara(ClinicICDNo);
		tSQLInfo.addSubPara(ClinicICDName);
		var arr1 = easyExecSql(tSQLInfo.getString(),1, 0, 1);
		if(arr1[0][0] == 0){
			
			alert("����Ҫ�����Ϣ��ϵͳ�в����ڣ�������¼��");
			return false;
		}
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
		tSQLInfo.setSqlId("LLClaimMedical1Sql5");
		tSQLInfo.addSubPara(tCustomerNo);
		tSQLInfo.addSubPara(fm.SpecialClinicNo.value);
		
		var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
		if(arr[0][0] > 0){
			
			alert("���ʵ����Ѿ����ڣ������������ʵ��ţ�");
			return false;
		}
	
    fm.Operate.value="INSERT";
    fm.currentInput.value = "3";
    fm.action = "./LLSpeicalClinicSave.jsp";
    submitForm();

}
function modifySpecialClinic(){
		
	var tSelNo = SpecialClinicBillGrid.getSelNo();
	if(tSelNo < 1){			
		alert("��ѡ��һ�м�¼��");
		return false;
	}
	  
	tPageNo = turnPage3.pageIndex;
	tIndexNo = tSelNo;
	fm.SelNo.value = tIndexNo;
	fm.PageIndex.value = tPageNo;
	
	  //�ǿռ���
    if (fm.SpecialClinicNo.value == "" || fm.SpecialClinicNo.value == null)
    {
        alert("�˵��Ų���Ϊ�գ�");
        return;
    } 
     if (fm.SpecialClinicHospID.value == "" || fm.SpecialClinicHospIDName.value == "")
    {
        alert("����ҽԺ����Ϊ�գ�");
        return;
    } 
    if(fm.SpecialClinicMoney.value==""){
    		
    		alert("�˵���������Ϊ��!");
        return;
    }
    //���ڼ���
    if (fm.SpecialClinicStart.value == '')
    {
        alert("�������ڲ���Ϊ��!");
        return;
    }    
    //��ҽ���ڲ��ܴ���ϵͳ��ǰ����
    if (dateDiff(fm.SpecialClinicStart.value,tCurrentDate,'D')< 0 )
    {
        alert("�������ڲ��ܴ��ڵ�ǰ����!");
        return;
    }
		 if(tCaseSource=="01" ){
	    //��ҽ���ڲ������ڳ�������
	    if(dateDiff(tAccidentDate,fm.SpecialClinicStart.value,'D')<0)
	    {
	        alert("�����������ڳ������ڣ�������¼�룡");
	        return false;
	    }    
		}  
    var rowNum=SpecialClinicItemGrid.mulLineCount ;//����
    //����Ҫ����¼��һ����ý��
    var strbillMoney = fm.SpecialClinicMoney.value;
    var inFlag = false;	
    var sumFeeMoney = 0;							
    for(var i=0; i<rowNum;i++)
    {
    	var feeMoney = SpecialClinicItemGrid.getRowColData(i,3);
    	if( feeMoney != null &&feeMoney !='')	
    	{
    		inFlag = true;    			
    		sumFeeMoney = parseFloat(sumFeeMoney) + parseFloat(feeMoney);     		
    	}    	
    }
    if(!inFlag)
    {
    	alert("����Ҫ¼��һ����ý�");	
    	return false;
    }
    if(inFlag)
    {
    	if(sumFeeMoney < 0.001)
    	{
    		alert("¼��ķ��ý��֮��Ӧ����0");
    		 return false;	
    	}	
    }    
	  //var colNum=EasyClinicInpGrid.colCount ;//����
	  for(var i=0;i<rowNum;i++)
	  {
	  	if(SpecialClinicItemGrid.getRowColData(i,1)==""||SpecialClinicItemGrid.getRowColData(i,2)==""){
	  		alert("�������ʹ�����������Ͳ���Ϊ��");
	  		return false;
	  	}
    		for(var j=3;j<7;j++)
    		{
		  			if(!isNumeric(SpecialClinicItemGrid.getRowColData(i,j))
		  			   &&SpecialClinicItemGrid.getRowColData(i,j)!=null
		  			   &&SpecialClinicItemGrid.getRowColData(i,j)!='')
			  		{
						  alert(SpecialClinicItemGrid.getRowColData(i,2)+"�ķ��ý�����������");
						  return false;
			  		}
		  			if (SpecialClinicItemGrid.getRowColData(i,j)<0) {
						  alert("�Էѽ��������0");
						  return false;
		  			}
		    }
		}
			//��¼���˵��������ʾ�˵�����������ý��֮���Ƿ����
			
	if(strbillMoney !=null && strbillMoney !=''&& inFlag)
    {
    	var billMoney = parseFloat(strbillMoney);
    	var m = Math.abs(billMoney-sumFeeMoney);
    	if(m > 0.001)
    	{
	 	/*	if(!confirm("������ý��֮�����˵��������Ƿ񱣴�?"))
    		{
    			return;
    		}*/
				alert("������ý��֮�����˵�����ȣ��������");
    		return false;
		}
    }
		if (fm.SpecialClinicICD.value==null || fm.SpecialClinicICD.value==""){
		
		alert("��Ҫ��ϲ���Ϊ��!");
		return false;
    }
    var ClinicICDNo = fm.SpecialClinicICD.value;
    var ClinicICDName =trim(fm.SpecialClinicICDName.value);
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
		tSQLInfo.setSqlId("LLClaimMedical1Sql7");
		tSQLInfo.addSubPara(ClinicICDNo);
		tSQLInfo.addSubPara(ClinicICDName);
		var arr1 = easyExecSql(tSQLInfo.getString(),1, 0, 1);
		if(arr1[0][0] == 0){
			
			alert("����Ҫ�����Ϣ��ϵͳ�в����ڣ�������¼��");
			return false;
		}
		if(fm.SpecialClinicNo.value!=fm.SpecialClinicBill1.value){
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
			tSQLInfo.setSqlId("LLClaimMedical1Sql5");
			tSQLInfo.addSubPara(tCustomerNo);
			tSQLInfo.addSubPara(fm.SpecialClinicNo.value);
			
			var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
			if(arr[0][0] > 0){
				
				alert("���ʵ����Ѿ����ڣ������������ʵ��ţ�");
				return false;
			}
		}
		fm.tempHosp.value=fm.SpecialClinicHospID.value;
		fm.tempHospStart.value=fm.SpecialClinicStart.value;
		fm.tempHospEnd.value=fm.SpecialClinicStart.value;
		fm.tempResult1.value=fm.SpecialClinicICD.value;
		fm.Operate.value="UPDATE";
    fm.currentInput.value = "3";
    fm.action = "./LLSpeicalClinicSave.jsp";
    submitForm();
	
}
//ɾ�����������˵�
function deleteSpecialClinic(){
	
	if(SpecialClinicBillGrid.getSelNo() == 0){
		
	  	alert("����ѡ��һ����¼!");
	  	return false;
		}
	if(SpecialClinicBillGrid.getSelNo() > 0) {
		
		if (confirm("��ȷʵ��ɾ���ü�¼��?")) {
			fm.Operate.value="DELETE";
			fm.currentInput.value = "3";
			fm.action = "./LLSpeicalClinicSave.jsp";
      submitForm();
			}else{
			
			fm.Operate.value="";
       }
    }else{
        alert("��ѡ��һ����¼��");
        return false;
    }
  }
 //��������ҽԺ�˵�
function addSpecialHospBill(){
     var inFlag = false;
    //�ǿռ���
    if (fm.SpecialHospBillNo.value == "" || fm.SpecialHospBillNo.value == null){
        alert("�˵��Ų���Ϊ�գ�");
        return false;
    } 	
      if (fm.SpecialHospID.value == "" || fm.SpecialHospIDName.value == null)
    {
        alert("����ҽԺ����Ϊ�գ�");
        return false;
    } 
    if (fm.SpecialHospBillMoney.value == ''){
    	
        alert("�˵���������Ϊ�գ�");
        return false;
    }
    //���ڼ���
    if (fm.SpecialHospStart.value == '' ||fm.SpecialHospStart.value== null){
    	
        alert("��Ժ���ڲ���Ϊ�գ�");
        return false;
    }
    if(fm.SpecialHospEnd.value == ''||fm.SpecialHospEnd.value ==null) {
    	
    	  alert("��Ժ���ڲ���Ϊ�գ�");
    	  return false;
    }

    var tStartDate=fm.SpecialHospStart.value;
    var tEndDate=fm.SpecialHospEnd.value;
    if(dateDiff(tStartDate,tCurrentDate,'D')<0) {
    	
    		alert("��Ժ���ڲ������ڵ�ǰ���ڣ�");
    		return false;	
    }
    
    if(dateDiff(tEndDate,tCurrentDate,'D')<0){
    	
    		alert("��Ժ���ڲ������ڵ�ǰ���ڣ�");
    		return false;	
    }
    //סԺ���ڲ������ڳ�Ժ����
    if(dateDiff(tStartDate,tEndDate,'D')<0){
    	
    		alert("��Ժ���ڲ������ڳ�Ժ���ڣ�");
    		return false;	
    }
		 if(tCaseSource=="01" ){
    //סԺ���ڲ������ڳ�������
	    if(dateDiff(tAccidentDate,tStartDate,'D')<0){
	    	
	    		alert("��Ժ�������ڳ������ڣ�");
	    		return false;	
	    }
    }
   //������޸ģ������޸��˵��ţ���������������˵��Ų����ظ�
    var repeatFlag = false;
    var tBillMoney = 0.00; 
  	var tDays=dateDiff(tStartDate,tEndDate,'D');  
  	fm.SpecialHospNum.value=tDays;
    var rowNum=SpecialHospItemGrid.mulLineCount ;//����
    
    var strbillMoney = fm.SpecialHospBillMoney.value;
    var sumFeeMoney = 0.00;
    
    var CountCode = 0;//��55-�㴲�ѡ����ñ����ۼ���
    var CountCode3 = 0;//��22-��λ�ѡ����ñ����ۼ���
    //����Ҫ����¼��һ����ý��
    for(var i=0; i<rowNum;i++)
    {
    	var feeMoney = SpecialHospItemGrid.getRowColData(i,3);
    	if( feeMoney != null &&feeMoney !='')	
    	{   	
    		inFlag = true;    			
    		sumFeeMoney = parseFloat(sumFeeMoney) + parseFloat(feeMoney);     	  
    	}
    	
    	var FeeItemCode = SpecialHospItemGrid.getRowColData(i,1);
    }
    if(!inFlag)
    {
    	alert("����Ҫ¼��һ����ý�");	
    	return false;
    }
    if(inFlag)
    {
    	if(sumFeeMoney < 0.001)
    	{
    		alert("¼��ķ��ý��֮��Ӧ����0");
    		 return false;	
    	}	
    }
	  //var colNum=EasyClinicInpGrid.colCount ;//����
	  for(var i=0;i<rowNum;i++)
	  {
	  	if(SpecialHospItemGrid.getRowColData(i,1)==""||SpecialHospItemGrid.getRowColData(i,2)==""){
	  		alert("�������ʹ�����������Ͳ���Ϊ��");
	  		return false;
	  	}
	  	
    		for(var j=3;j<7;j++)
    		{
		  			if(!isNumeric(SpecialHospItemGrid.getRowColData(i,j))
		  			&&SpecialHospItemGrid.getRowColData(i,j)!=null
		  			&&SpecialHospItemGrid.getRowColData(i,j)!='')
			  		{
						  alert(SpecialHospItemGrid.getRowColData(i,2)+"�ķ��ý�����������");
						  return false;
			  		}
		  			if (SpecialHospItemGrid.getRowColData(i,j)<0) {
						  alert("�Էѽ��������0");
						  return false;
		  			}
		    }
		}
	//��¼���˵��������ʾ�˵�����������ý��֮���Ƿ����
	if(strbillMoney !=null && strbillMoney !=''&& inFlag)
    {
    	var billMoney = parseFloat(strbillMoney);
    	var m = Math.abs(billMoney-sumFeeMoney);
    	if(m > 0.001)
    	{
	 	/*	if(!confirm("������ý��֮�����˵��������Ƿ񱣴�?"))
    		{
    			return false;
    		}*/
				alert("������ý��֮�����˵�����ȣ��������");
    		return false;
		}    	
    }
		if (fm.SpecialHospICD.value==null || fm.SpecialHospICD.value==""){
		
		alert("��Ҫ��ϲ���Ϊ��!");
		return false;
    }
    var ClinicICDNo = fm.SpecialHospICD.value;
    var ClinicICDName =trim(fm.SpecialHospICDName.value);
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
		tSQLInfo.setSqlId("LLClaimMedical1Sql7");
		tSQLInfo.addSubPara(ClinicICDNo);
		tSQLInfo.addSubPara(ClinicICDName);
		var arr1 = easyExecSql(tSQLInfo.getString(),1, 0, 1);
		if(arr1[0][0] == 0){
			
			alert("����Ҫ�����Ϣ��ϵͳ�в����ڣ�������¼��");
			return false;
		}
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
		tSQLInfo.setSqlId("LLClaimMedical1Sql5");
		tSQLInfo.addSubPara(tCustomerNo);
		tSQLInfo.addSubPara(fm.SpecialHospBillNo.value);
		
		var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
		if(arr[0][0] > 0){
			
			alert("���ʵ����Ѿ����ڣ������������ʵ��ţ�");
			return false;
		}
		
    fm.Operate.value="INSERT";
    fm.currentInput.value = "4";
    fm.action = "./LLSpeicalHospSave.jsp";
    submitForm();
  }
function modifySpecialHospBill(){
	
	var tSelNo = SpecialHospGrid.getSelNo();
	if(tSelNo < 1){			
		alert("��ѡ��һ�м�¼��");
		return false;
	}
	tPageNo = turnPage4.pageIndex;
	tIndexNo = tSelNo;
	fm.SelNo.value = tIndexNo;
	fm.PageIndex.value = tPageNo;
	
	var inFlag = false;
    //�ǿռ���
    if (fm.SpecialHospBillNo.value == "" || fm.SpecialHospBillNo.value == null){
        alert("�˵��Ų���Ϊ�գ�");
        return false;
    } 	
    if (fm.SpecialHospBillMoney.value == ''){
    	
        alert("�˵���������Ϊ�գ�");
        return false;
    }
    if (fm.SpecialHospID.value == "" || fm.SpecialHospIDName.value == null)
    {
        alert("����ҽԺ����Ϊ�գ�");
        return false;
    } 
    //���ڼ���
    if (fm.SpecialHospStart.value == '' ||fm.SpecialHospStart.value== null){
    	
        alert("��Ժ���ڲ���Ϊ�գ�");
        return;
    }
    if(fm.SpecialHospEnd.value == ''||fm.SpecialHospEnd.value ==null) {
    	
    	  alert("��Ժ���ڲ���Ϊ�գ�");
    	  return false;
    }

    var tStartDate=fm.SpecialHospStart.value;
    var tEndDate=fm.SpecialHospEnd.value;
    if(dateDiff(tStartDate,tCurrentDate,'D')<0) {
    	
    		alert("��Ժ���ڲ������ڵ�ǰ���ڣ�");
    		return false;	
    }
    
    if(dateDiff(tEndDate,tCurrentDate,'D')<0){
    	
    		alert("��Ժ���ڲ������ڵ�ǰ���ڣ�");
    		return false;	
    }
    //סԺ���ڲ������ڳ�Ժ����
    if(dateDiff(tStartDate,tEndDate,'D')<0){
    	
    		alert("סԺ���ڲ������ڳ�Ժ���ڣ�");
    		return false;	
    }
		 if(tCaseSource=="01" ){
	    //סԺ���ڲ������ڳ�������
	    if(dateDiff(tAccidentDate,tStartDate,'D')<0){
	    	
	    		alert("סԺ�������ڳ������ڣ�");
	    		return false;	
	    }
    }
   //������޸ģ������޸��˵��ţ���������������˵��Ų����ظ�
    var repeatFlag = false;
    var tBillMoney = 0.00; 
   var tDays=dateDiff(tStartDate,tEndDate,'D');  
  	fm.SpecialHospNum.value=tDays;
    var rowNum=SpecialHospItemGrid.mulLineCount ;//����
    
    var strbillMoney = fm.SpecialHospBillMoney.value;
    var sumFeeMoney = 0.00;
    
    var CountCode = 0;//��55-�㴲�ѡ����ñ����ۼ���
    var CountCode3 = 0;//��22-��λ�ѡ����ñ����ۼ���
    //����Ҫ����¼��һ����ý��
    for(var i=0; i<rowNum;i++)
    {
    	var feeMoney = SpecialHospItemGrid.getRowColData(i,3);
    	if( feeMoney != null &&feeMoney !='')	
    	{   	
    		inFlag = true;    			
    		sumFeeMoney = parseFloat(sumFeeMoney) + parseFloat(feeMoney);     	  
    	}
    	
    	var FeeItemCode = SpecialHospItemGrid.getRowColData(i,1);
    }
    if(!inFlag)
    {
    	alert("����Ҫ¼��һ����ý�");	
    	return false;
    }
    if(inFlag)
    {
    	if(sumFeeMoney < 0.001)
    	{
    		alert("¼��ķ��ý��֮��Ӧ����0");
    		 return false;	
    	}	
    }
	  //var colNum=EasyClinicInpGrid.colCount ;//����
	  for(var i=0;i<rowNum;i++)
	  {
	  	if(SpecialHospItemGrid.getRowColData(i,1)==""||SpecialHospItemGrid.getRowColData(i,2)==""){
	  		alert("�������ʹ�����������Ͳ���Ϊ��");
	  		return false;
	  	}
    		for(var j=3;j<7;j++)
    		{
		  			if(!isNumeric(SpecialHospItemGrid.getRowColData(i,j))
		  			&&SpecialHospItemGrid.getRowColData(i,j)!=null
		  			&&SpecialHospItemGrid.getRowColData(i,j)!='')
			  		{
						  alert(SpecialHospItemGrid.getRowColData(i,2)+"�ķ��ý�����������");
						  return false;
			  		}
		  			if (SpecialHospItemGrid.getRowColData(i,j)<0) {
						  alert("�Էѽ��������0");
						  return false;
		  			}
		    }
		}
		
	//��¼���˵��������ʾ�˵�����������ý��֮���Ƿ����
	if(strbillMoney !=null && strbillMoney !=''&& inFlag)
    {
    	var billMoney = parseFloat(strbillMoney);
    	var m = Math.abs(billMoney-sumFeeMoney);
    	if(m > 0.001)
    	{

				alert("������ý��֮�����˵�����ȣ��������");
    		return false;
		}    	
    }
		if (fm.SpecialHospICD.value==null || fm.SpecialHospICD.value==""){
		
		alert("��Ҫ��ϲ���Ϊ��!");
		return false;
    }
    var ClinicICDNo = fm.SpecialHospICD.value;
    var ClinicICDName =trim(fm.SpecialHospICDName.value);
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
		tSQLInfo.setSqlId("LLClaimMedical1Sql7");
		tSQLInfo.addSubPara(ClinicICDNo);
		tSQLInfo.addSubPara(ClinicICDName);
		var arr1 = easyExecSql(tSQLInfo.getString(),1, 0, 1);
		if(arr1[0][0] == 0){
			
			alert("����Ҫ�����Ϣ��ϵͳ�в����ڣ�������¼��");
			return false;
		}
		if(fm.SpecialHospBillNo.value!=fm.SpecialHospBillNo1.value){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("claim.LLClaimMedicalSql");
		tSQLInfo.setSqlId("LLClaimMedical1Sql5");
		tSQLInfo.addSubPara(tCustomerNo);
		tSQLInfo.addSubPara(fm.SpecialHospBillNo.value);
		
		var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
		if(arr[0][0] > 0){
			
			alert("���ʵ����Ѿ����ڣ������������ʵ��ţ�");
			return false;
		}
	}		
		fm.tempHosp.value=fm.SpecialHospID.value;
		fm.tempHospStart.value=fm.SpecialHospStart.value;
		fm.tempHospEnd.value=fm.SpecialHospEnd.value;
		fm.tempResult1.value=fm.SpecialHospICD.value;
		
    fm.Operate.value="UPDATE";
    fm.currentInput.value = "4";
    fm.action = "./LLSpeicalHospSave.jsp";
    submitForm();
}
function deleteSpecialHospBill(){
		if(SpecialHospGrid.getSelNo() == 0){
		
	  	alert("����ѡ��һ����¼!");
	  	return false;
		}
	if(SpecialHospGrid.getSelNo() > 0) {
		
		if (confirm("��ȷʵ��ɾ���ü�¼��?")) {
			fm.Operate.value="DELETE";
			fm.currentInput.value = "4";
			fm.action = "./LLSpeicalHospSave.jsp";
      submitForm();
			}else{
			
			fm.Operate.value="";
       }
    }else{
        alert("��ѡ��һ����¼��");
        return false;
    }
}
//���ҩƷ����
function clinicDeductConfirm(flag){
	
	if(flag=="1"){
		
		var tBillMonty=0.0;
		var tDeductionDesc="";
		var a="";
		var i = ClinicBillItemGrid.getSelNo();
		if(i < 1){
			
			alert("��ѡ��һ�з��ÿۼ���Ϣ��");
			return false;
			}
		//��ȡ�ۼ���Ϣ
		i = ClinicBillItemGrid.getSelNo()-1;
		tBillMonty=ClinicBillItemGrid.getRowColData(i,3);
		tDeductionDesc=ClinicBillItemGrid.getRowColData(i,4);
		if(tDeductionDesc==""){
			a="";
		}else{
			a=";";
		}
		var j = DrugsGrid.getSelNo();
		if( j< 1){
			
			alert("��ѡ��һ��ҩƷ��Ϣ��");
			return false;
		}
		j = DrugsGrid.getSelNo()-1;
		var DrugsName =DrugsGrid.getRowColData(j,5);
		var DrugsMoney =DrugsGrid.getRowColData(j,14);
		if(DrugsMoney==""){
			
			alert("��¼����ѡҩƷ��Ϣ�Ŀۼ���") ;
			return false;
		}
		if(!tCheckRule.test(DrugsMoney)){
			
			alert("�۳����¼�벻��ȷ��") ;
			return false;
		}
	    if (DrugsMoney<0) {
	  		alert("�۳����������0");
			return false;
	    }
		
		
		tBillMonty=Number(tBillMonty)+Number(DrugsMoney);
		var trueBillMonty = tBillMonty.toString();
		tDeductionDesc =tDeductionDesc+a+DrugsName+":"+DrugsMoney+"Ԫ";
		
		ClinicBillItemGrid.setRowColData(i,3,trueBillMonty);
		ClinicBillItemGrid.setRowColData(i,4,tDeductionDesc);
		DeductQuery();
		
	}else if (flag=="2"){
		
		var tBillMonty=0.0;
		var tDeductionDesc="";
		var a="";
		var i = HospBillItemGrid.getSelNo();
		if(i < 1){
			
			alert("��ѡ��һ�з��ÿۼ���Ϣ��");
			return false;
			}
		//��ȡ�ۼ���Ϣ
		i = HospBillItemGrid.getSelNo()-1;
		tBillMonty=HospBillItemGrid.getRowColData(i,3);
		tDeductionDesc=HospBillItemGrid.getRowColData(i,4);
		if(tDeductionDesc==""){
			a="";
		}else{
			a=";";
		}
		var j = Drugs1Grid.getSelNo();
		if( j< 1){
			
			alert("��ѡ��һ��ҩƷ��Ϣ��");
			return false;
		}
		j = Drugs1Grid.getSelNo()-1;
		var DrugsName =Drugs1Grid.getRowColData(j,5);
		var DrugsMoney =Drugs1Grid.getRowColData(j,14);
		
		if(DrugsMoney==""){
			
			alert("��¼����ѡҩƷ��Ϣ�Ŀۼ���") ;
			return false;
		}
		if(!tCheckRule.test(DrugsMoney)){
			
			alert("�۳����¼�벻��ȷ��") ;
			return false;
		}
	    if (DrugsMoney<0) {
	  		alert("�۳����������0");
			return false;
	    }
		tBillMonty=Number(tBillMonty)+Number(DrugsMoney);
		var trueBillMonty = tBillMonty.toString();
		tDeductionDesc =tDeductionDesc+a+DrugsName+":"+DrugsMoney+"Ԫ";
		HospBillItemGrid.setRowColData(i,3,trueBillMonty);
		HospBillItemGrid.setRowColData(i,4,tDeductionDesc);
		DeductQuery1();
	}
}
//�ر��˵�����
function closePage(){
	if(tMode=="0"){
		top.opener.showSelectCustomer();
	}
	top.close();
}
//Ӱ����涯
function initScanPages(EcmNo){
		
	top.fraPic.location.href = "../g_easyscan/ScanServo.jsp?BussType="+tBussType+"&SubType="+tSubType+"&BussNo="+tBussNo+"&PageCode="+EcmNo+"";
	
}
/**
 * ����У��¼����
 */
function checkMoney(tObject) {

	var tValue = tObject.value;
	if(((!/^(-)?(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/.test(tValue)) && (!tValue==""))){
		
		alert("��������ȷ�Ľ�") ;
		tObject.className = "warn" ;
		tObject.focus();
		tObject.value="";		
	}
}
/**
	*��ѯҩƷ��Ϣ
	*/
function DeductQuery(){

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
	tSQLInfo.setSqlId("LLClaimMedical1Sql16");
	tSQLInfo.addSubPara(fm.ClinicArea.value);
	tSQLInfo.addSubPara(fm.Clinicdate.value);
	tSQLInfo.addSubPara(fm.ClinicMediName.value);
	turnPage7.queryModal(tSQLInfo.getString(), DrugsGrid,"2");
	if (!turnPage7.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	}
}
function DeductQuery1(){

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimMedicalSql");
	tSQLInfo.setSqlId("LLClaimMedical1Sql16");
	tSQLInfo.addSubPara(fm.ClinicArea1.value);
	tSQLInfo.addSubPara(fm.Clinicdate1.value);
	tSQLInfo.addSubPara(fm.ClinicMediName1.value);
	turnPage8.queryModal(tSQLInfo.getString(), Drugs1Grid,"2");
	if (!turnPage8.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	}
}
/**
 * Ĭ��ѡ�иղ����ļ�¼
 */
function setSelRow(ObjGrid,tTurnPage){			
	
 	var tPageIndex = fm.PageIndex.value;
 	
	if (tPageIndex!=null && tPageIndex!="") {
		for (var i=0; i<tPageIndex; i++) {
			tTurnPage.nextPage();
		}
	}
	var tSelNo =fm.SelNo.value;
	if(tSelNo==null || tSelNo ==""){
		tSelNo = 1;
	}	
	ObjGrid.radioSel(tSelNo);
	
}