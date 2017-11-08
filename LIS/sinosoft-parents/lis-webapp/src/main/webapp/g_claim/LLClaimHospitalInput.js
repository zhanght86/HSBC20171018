/***************************************************************
 * <p>ProName��LLClaimHospitalInput.js</p>
 * <p>Title��ҽԺ��Ϣά��</p>
 * <p>Description��ҽԺ��Ϣά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/***ҽԺ��Ϣ��ѯ**/
function QueryHospitalInfo(){
		
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.QueryHospitalSql");
	tSQLInfo.setSqlId("HospitalSql1");
	tSQLInfo.addSubPara(fm.HospitalCodeQ.value);
	tSQLInfo.addSubPara(fm.HospitalNameQ.value);
	tSQLInfo.addSubPara(fm.HosStateQ.value);
	tSQLInfo.addSubPara(fm.HosGradeQ.value);
	tSQLInfo.addSubPara(tManageCom);
	
	turnPage1.queryModal(tSQLInfo.getString(), LLCommendHospitalGrid,"6");
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}

}
/**Mulline��������**/
function LLCommendHospitalGridClick(){
	
	 var i = LLCommendHospitalGrid.getSelNo();
   if(i < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return;
     }
	  i = LLCommendHospitalGrid.getSelNo()-1;
    fm.HospitalCode.value=LLCommendHospitalGrid.getRowColData(i,1);
    fm.HospitalName.value=LLCommendHospitalGrid.getRowColData(i,2);
    fm.HosGrade.value=LLCommendHospitalGrid.getRowColData(i,3);
    fm.HosGradeName.value=LLCommendHospitalGrid.getRowColData(i,4);
		fm.HosState.value=LLCommendHospitalGrid.getRowColData(i,5);
		fm.HosStateName.value=LLCommendHospitalGrid.getRowColData(i,6);
		
		fm.HosPhone.value=LLCommendHospitalGrid.getRowColData(i,7);
		fm.HosAddress.value=LLCommendHospitalGrid.getRowColData(i,8);
		
		fm.HospitalCode.disabled= true; //ҽԺ�����ֹ�޸�
		fm.saveHospitalButton.disabled=true;
		fm.editHospitalButton.disabled=false;
		fm.deleteHospitalButton.disabled=false;
}
/**����ҽԺ��Ϣ**/
function HospitalAddClick(){
	
	if(trim(fm.HospitalCode.value)==""){
		
		alert("ҽԺ���벻��Ϊ��");
		return false;
	}
	if(trim(fm.HospitalName.value)==""){
		
		alert("ҽԺ���Ʋ���Ϊ��");
		return false;
	}
	if(fm.HosState.value==""){
		
		alert("ҽԺ״̬����Ϊ��");
		return false;
	}
	
	if(fm.HosGrade.value==""){
		
		alert("ҽԺ�ȼ�����Ϊ��");
		return false;
	}
	/**У��ҽԺ��Ϣ�Ƿ��Ѿ�����**/
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.QueryHospitalSql");
	tSQLInfo.setSqlId("HospitalSql2");
	tSQLInfo.addSubPara(fm.HospitalCode.value);
	
	var arr = easyExecSql(tSQLInfo.getString());
	if(arr==null){
		
	}else{
		
		alert("��ҽԺ������ϵͳ���Ѿ�����,��˲�¼���ҽԺ��Ϣ");
		return false;
	}
	
	fm.Operate.value="INSERT";
	fm.action="./LLClaimHospitalSave.jsp";
	
	submitForm();
}
/**�޸�**/
function HospitalEditClick(){
	
	if(trim(fm.HospitalCode.value)==""){
		
		alert("ҽԺ���벻��Ϊ��");
		return false;
	}
	if(trim(fm.HospitalName.value)==""){
		
		alert("ҽԺ���Ʋ���Ϊ��");
		return false;
	}
	if(fm.HosState.value==""){
		
		alert("ҽԺ״̬����Ϊ��");
		return false;
	}
	
	if(fm.HosGrade.value==""){
		
		alert("ҽԺ�ȼ�����Ϊ��");
		return false;
	}
	
	if (confirm("��ȷʵ���޸ĸü�¼��?")){
		
			fm.HospitalCode.disabled= false; //[ҽԺ����]---�����޷�ȡ������
			fm.Operate.value="UPDATE";
			fm.action="./LLClaimHospitalSave.jsp";
			submitForm();
			
    }
    else{
    	
			fm.Operate.value="";
			return false;        
    }
	
}
/**ɾ��**/
function HospitalDeleteClick(){
	
		if (confirm("��ȷʵ��ɾ���ü�¼��?")){	
			fm.HospitalCode.disabled= false; //[ҽԺ����]---�����޷�ȡ������
			fm.Operate.value="DELETE";
			fm.action="./LLClaimHospitalSave.jsp";
			submitForm();
    }
    else
    {
			fm.Operate.value="";
			return false;
    }
		
	
}
/**����**/
function HospitalTurnBack(){
	//initLLCommendHospitalGrid();
		fm.HospitalCode.value="";
		fm.HospitalName.value="";
		fm.HosGrade.value="";
		fm.HosGradeName.value="";
		fm.HosState.value="";
		fm.HosStateName.value="";
		
		fm.HosPhone.value="";
		
		fm.HosAddress.value="";
		fm.Operate.value="";
		fm.HospitalCode.disabled= false; //ҽԺ?
		fm.saveHospitalButton.disabled=false;   
		fm.editHospitalButton.disabled=true;  
		fm.deleteHospitalButton.disabled=true;

} 

/**
 * ����
 */
function submitImport() {
	
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
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");      	
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
	uploadfm.ImpOperate.value = "EXCELIMP";	
	
	uploadfm.action = "./LLClaimHospImpSave.jsp?Operate="+uploadfm.ImpOperate.value;
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
	initLLCommendHospitalGrid();
	QueryHospitalInfo();
}
/**
 * ��ѯʡ֮ǰ��У��
 */
function beforQueryCity(Filed) {
	
	var provinceCode = Filed.value;
	if(provinceCode==null||provinceCode=="") {
		alert("����ѡ��ʡ��");
		Filed.focus();
		return false;
	}
	return true;
}

/**
 * ��ѯ��/��֮ǰ��У��
 */
function beforQueryCounty(Filed) {
	
	var cityCode = Filed.value;
	if (cityCode==null||cityCode=="") {
		alert("����ѡ���У�");
		Filed.focus();
		return false;
	}
	
	return true;
}

//���ݵ���
function exportData() {
	
	if (!confirm("ȷ��Ҫ�������ݣ�")) {
		return false;
	}
	
	//�������
	var tTitle = "ҽԺ����^|ҽԺ����^|ҽԺ�ȼ�����^|ҽԺ�ȼ�^|ҽԺ״̬����^|ҽԺ״̬^|"
							
							+"ҽԺ�绰^|"
							+"���ڵ�ַ";
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.QueryHospitalSql");
	tSQLInfo.setSqlId("HospitalSql1");
	tSQLInfo.addSubPara(fm.HospitalCodeQ.value);
	tSQLInfo.addSubPara(fm.HospitalNameQ.value);
	tSQLInfo.addSubPara(fm.HosStateQ.value);
	tSQLInfo.addSubPara(fm.HosGradeQ.value);
	tSQLInfo.addSubPara(tManageCom);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}
