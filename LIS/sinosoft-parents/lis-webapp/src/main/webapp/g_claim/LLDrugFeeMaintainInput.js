/***************************************************************
 * <p>ProName��LLDrugFeeMaintainInput.js</p>
 * <p>Title��ҩƷ��Ϣά��</p>
 * <p>Description��ҩƷ��Ϣά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();
/**
	*��ѯҩƷ��Ϣ
	*/
function queryPermissionInfo(){  
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLDrugsFeeSql");
	tSQLInfo.setSqlId("LLDrugsFeeSql1");
	tSQLInfo.addSubPara(fm.areaCodeQ.value);
	tSQLInfo.addSubPara(fm.StartdateQ.value);
	tSQLInfo.addSubPara(fm.EndDateQ.value);
	tSQLInfo.addSubPara(fm.drugFeeMaintainNameQ.value);
	tSQLInfo.addSubPara(fm.bussinessNameQ.value);
	turnPage2.queryModal(tSQLInfo.getString(), DrugFeeMaintainGrid,"2");   
	if (!turnPage2.strQueryResult) {                       
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	}
}
/**
	*mullineչʾ����
	*/
function showPermissionInfo(){
	 
	 var i = DrugFeeMaintainGrid.getSelNo();
   if(i < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return;
     }
	  i = DrugFeeMaintainGrid.getSelNo()-1;
    fm.tDrugsSerialNo.value=DrugFeeMaintainGrid.getRowColData(i,1);
    fm.areaCode.value=DrugFeeMaintainGrid.getRowColData(i,2);
    fm.areaName.value=DrugFeeMaintainGrid.getRowColData(i,3);
    fm.UpdateDate.value=DrugFeeMaintainGrid.getRowColData(i,4);
		fm.drugFeeMaintainName.value=DrugFeeMaintainGrid.getRowColData(i,5);
		fm.bussinessName.value=DrugFeeMaintainGrid.getRowColData(i,6);
		fm.drugForm.value=DrugFeeMaintainGrid.getRowColData(i,7);
		fm.format.value=DrugFeeMaintainGrid.getRowColData(i,8);
		fm.selfRate.value=DrugFeeMaintainGrid.getRowColData(i,9);
		fm.medicalInsurance.value=DrugFeeMaintainGrid.getRowColData(i,10);
		fm.price.value=DrugFeeMaintainGrid.getRowColData(i,11);
		fm.restrictions.value=DrugFeeMaintainGrid.getRowColData(i,12);
		fm.medicalInsuranceCode.value=DrugFeeMaintainGrid.getRowColData(i,13);
}

/**
	*����ҩƷ��Ϣ
	*/
function addDrugFee(){
		
	if (!verifyInput2()) {
		
			return false;
	}
	if(fm.drugFeeMaintainName.value==""&&fm.bussinessName.value==""){
		
		alert("ҩƷ��������Ʒ����������һ������Ϊ��");
		return false;
	
	}

	fm.Operate.value="INSERT";
	fm.action="./LLDrugFeeMaintainSave.jsp";
	submitForm();
}
/**
	*�޸�ҩƷ��Ϣ
	*/
function modifyDrugFee(){
		
	if (!verifyInput2()) {
		
			return false;
	}
	if(fm.drugFeeMaintainName.value==""&&fm.bussinessName.value==""){
		
		alert("ҩƷ��������Ʒ����������һ������Ϊ��");
		return false;
		
	}
	
		fm.Operate.value="UPDATE";
		fm.action="./LLDrugFeeMaintainSave.jsp";
		submitForm();
}
/**
	*ɾ��ҩƷ��Ϣ
	*/
function deleteDrugFee(){
		
	if (confirm("��ȷʵ��ɾ���ü�¼��?")){	
		
		fm.Operate.value="DELETE";
		fm.action="./LLDrugFeeMaintainSave.jsp";
		submitForm();
		}else{
			
		fm.Operate.value="";
		
		return false;
    }
}
/**
	*���ý�����Ϣ
	*/
function restartDrugFee(){
	  
 	 	fm.tDrugsSerialNo.value="";
    fm.areaCode.value="";
    fm.areaName.value="";
    fm.UpdateDate.value="";
		fm.drugFeeMaintainName.value="";
		fm.bussinessName.value="";
		fm.drugForm.value="";
		fm.format.value="";
		fm.selfRate.value="";
		fm.medicalInsurance.value="";
		fm.price.value="";
		fm.restrictions.value="";
		fm.Operate.value="";
		fm.tDrugsSerialNo.value="";
		fm.medicalInsuranceCode.value="";
		//initDrugFeeMaintainGrid();
	
}
/**
 * ҩƷ��Ϣ����
 */
function medicalImport() {
	
	var filePath = uploadfm.UploadPath.value;
	if(filePath == null || filePath == ""){
		alert("��ѡ�����ļ�·����");
		return false;
	}
	
	var indexFirst = filePath.lastIndexOf("\\");
	var indexLast = filePath.lastIndexOf(".xlsx");
	if(indexFirst < 0 || indexLast < 0 || indexLast <= indexFirst) {
		alert("�ļ�·�����Ϸ���ѡ����ļ���ʽ����ȷ��������ѡ��");
		return false;
	}

	var fileName = filePath.substring(indexFirst+1,indexLast);		
	
	if (fileName.length>=100) {
		
		alert("�ļ������Ȳ��ܳ���100��");
		return false;
	}
	
	var i = 0;
	var showStr = "���ڵ��룬�����Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");      	
	
	uploadfm.ImpOperate.value = "EXCELIMP";	
	
	uploadfm.action = "./LLClaimDrugImpSave.jsp?Operate="+uploadfm.ImpOperate.value;
	uploadfm.submit();	
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
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	}
	initForm();
	restartDrugFee();
	queryPermissionInfo();
}
//���ݵ���
function exportData() {
	
	if (!confirm("ȷ��Ҫ�������ݣ�")) {
		return false;
	}
	
	//�������
	var tTitle = "ҩƷ��ˮ��^|��������^|����^|��������^|ҩƷ����^|��Ʒ����^|����^|"
							+"���^|�Ը�����^|ҽ������^|�۸�^|��������^|ҽ�����ͱ���";
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLDrugsFeeSql");
	tSQLInfo.setSqlId("LLDrugsFeeSql1");
	tSQLInfo.addSubPara(fm.areaCodeQ.value);
	tSQLInfo.addSubPara(fm.StartdateQ.value);
	tSQLInfo.addSubPara(fm.EndDateQ.value);
	tSQLInfo.addSubPara(fm.drugFeeMaintainNameQ.value);
	tSQLInfo.addSubPara(fm.bussinessNameQ.value);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}
