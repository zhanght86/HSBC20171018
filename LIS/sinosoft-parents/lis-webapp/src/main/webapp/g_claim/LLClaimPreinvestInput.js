/***************************************************************
 * <p>ProName��LLClaimPreinvestInput.js</p>
 * <p>Title���������</p>
 * <p>Description���������</p>
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

/**
 * ������ϸ��ѯ
 */
function showInvest() {
		var i = InvestListGrid.getSelNo();
		if(i < 1){
			
			alert("��ѡ��һ���ѷ���ĵ�����Ϣ��");
			return false;
     }
		i = InvestListGrid.getSelNo()-1;
    var tInqNo=InvestListGrid.getRowColData(i,1);
    var RregisterNo=InvestListGrid.getRowColData(i,2);
    
    var strUrl= "LLClaimQueryPreinvestInputMain.jsp?State=2&InqNo="+fm.InqNo.value+"&RgtNo="+fm.RgtNo.value;   
		window.open(strUrl,"������ϸ��ѯ",'width=1000,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * �ջص���
 */
function returnInvest() {
	
   var i = InvestListGrid.getSelNo();
   if(i < 1)
    {
        alert("��ѡ��һ���ѷ���ĵ�����Ϣ��");
        return false;
     }
    if(fm.InqFlowState.value!="01"){
			alert("�����ջ�ֻ���δ����ĵ�����Ϣ");
			return false;
	}
		fm.Operate.value ="RETURNINVEST";
		submitForm();
	
}
/**
 * ��������
 */
function saveSurvey() {
	
	if (!verifyInput2()){
			return false;
	}

	if(!checkInput()) {
	
			return false;
		}

	if(compareDate(tCurrentDate,fm.ApplyDate.value)>0){
		
		alert("�����������ڲ������ڵ�ǰ���ڣ�");
		return false;
	}
	
	fm.Operate.value ="INSERT";
	fm.LocFlag.value="0";// 0-���� 1-���
	fm.InqFlowState.value="00";//����״̬��������
	fm.InitiationType.value="0";//����ʽ��0�ֶ�����
	submitForm();
}
/**
 * �޸ĵ���
 */
function modifySurvey() {
	
	if (!verifyInput2()){
			return false;
	}
	var i = OnInvestGrid.getSelNo();
   if(i < 1)
    {
        alert("��ѡ��һ�д�����ĵ�����Ϣ��");
        return false;
     }
	if(!checkInput()) {
	
			return false;
		}

	if(compareDate(tCurrentDate,fm.ApplyDate.value)>0){
		
		alert("�����������ڲ������ڵ�ǰ���ڣ�");
		return false;
	}
	
	fm.Operate.value ="UPDATE";
	fm.LocFlag.value="0";// 0-���� 1-���
	fm.InqFlowState.value="00";//����״̬��������
	submitForm();
	
}
/**
 * ɾ������
 */
function deleteSurvey() {
	var i = OnInvestGrid.getSelNo();
   if(i < 1)
    {
        alert("��ѡ��һ�д�����ĵ�����Ϣ��");
        return false;
     }
		if (confirm("��ȷʵ��ɾ���ü�¼��?")){	
			
			fm.Operate.value ="DELETE";
			submitForm();
    }
    else{
			
			return false;
    }
}
/**
 * �رյ���
 */
function closeSurvey() {
	var i = OnInvestGrid.getSelNo();
   if(i < 1)
    {
        alert("��ѡ��һ�д�����ĵ�����Ϣ��");
        return false;
     }
     if(trim(fm.InqCloseReason.value)==""){
     	
     		alert("��¼�����ر�ԭ��");
     		return false;
    }
    
   if (confirm("��ȷʵ��رոü�¼��?")){	
			
			fm.Operate.value ="COLCONFIRM";
			submitForm();
    }
}
/**
 * �������
 */
function startSurvey() {
	var i = OnInvestGrid.getSelNo();
	if(i < 1){
		
		alert("��ѡ��һ�д�����ĵ�����Ϣ��");
		
		return false;
	}
	if (confirm("��ȷʵ��Ըü�¼���������?")){
			
			fm.Operate.value ="CONFIRM";
			submitForm();
    }
}
/**����**/
function returnBack(){
	
	initForm();
	
}

/**
 * ����
 */
function goBack() {
	top.close();
}

//��ʼ������
function initValue(){
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimPreinvestSql");
		tSQLInfo.setSqlId("LLClaimPreinvestSql1");
		tSQLInfo.addSubPara(tCustomerNo);
		var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
		if(arr == null){
				alert("��ѯ����������Ϣʧ��");
				top.close;
				return false;
		}else {	
			fm.CustomerName.value=arr[0][0];
		}
		fm.CustomerNo.value=tCustomerNo;
		fm.ManageCom.value=tManageCom;
		fm.InitPhase.value=tInitPhase;
		fm.RgtNo.value=tRgtNo;
		fm.GrpRgtNo.value=tGrpRgtNo;
		
		if(tSurveyType=="01"){
			fm.SurveyType.value="1";
			fm.SurveyTypeName.value="�ⰸ����";
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimPreinvestSql");
			tSQLInfo.setSqlId("LLClaimPreinvestSql5");
			tSQLInfo.addSubPara(tRgtNo);
			
		}else if(tSurveyType=="02"){
			fm.SurveyType.value="0";
			fm.SurveyTypeName.value="ǰ�õ���";
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimPreinvestSql");
			tSQLInfo.setSqlId("LLClaimPreinvestSql4");
			tSQLInfo.addSubPara(tRgtNo);
		}
		var arrAppnt = easyExecSql(tSQLInfo.getString(),1, 0, 1);
		
		
		fm.AppntNo.value=arrAppnt[0][0];
		
		fm.ApplyDate.value="";
		fm.InqReason.value="";
		fm.InqReasonName.value="";
		fm.InqPurpose.value="";
		fm.InqPlan.value="";
		fm.Remark.value="";		
		fm.InqCloseReason.value="";
		fm.InqCloseReasonName.value="";
	
		queryMulineInfo();
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
		//showDialogWindow(urlStr, 1);
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showDialogWindow(urlStr, 1);
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
	}	
	initForm();
}

/**
 * ������ѡ���Ժ����
 */
function afterCodeSelect(cCodeName, Field) {

	if(cCodeName == '') {
		
	}
	
}
//�Ƚ�ʱ��ķ���
function compareDate(strDate1,strDate2){
	
		var date1 = new Date(strDate1.replace(/\-/g, "\/"));  
		var date2 = new Date(strDate2.replace(/\-/g, "\/"));  

		return date1-date2; 
}

//�������У��
function checkInput() {
	
	var tInqItem = fm.InqPurpose.value;
	var tInqDesc = fm.InqPlan.value;
	fm.InqPurpose.value = 	replaceAllSymbol(tInqItem);
	fm.InqPlan.value = replaceAllSymbol(tInqDesc);
	
	return true;
	}

//���������滻Ϊ���ĵ�
function replaceAllSymbol(filed) {
	var s = filed;
		
	while(s.indexOf("'")!=-1) {
		s = s.replace("'","��");
	}

	return s;
	}
	
/**Mulline ��ѯ**/
function queryMulineInfo(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimPreinvestSql");
	tSQLInfo.setSqlId("LLClaimPreinvestSql2");
	tSQLInfo.addSubPara(tCustomerNo);
	tSQLInfo.addSubPara(tRgtNo);
	turnPage1.queryModal(tSQLInfo.getString(), InvestListGrid,"2");
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimPreinvestSql");
	tSQLInfo.setSqlId("LLClaimPreinvestSql3");
	tSQLInfo.addSubPara(tCustomerNo);
	tSQLInfo.addSubPara(tRgtNo);
	turnPage2.queryModal(tSQLInfo.getString(), OnInvestGrid,"2");
	
}
function getInvestListInfo(){
	var i = InvestListGrid.getSelNo();
   if(i < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return false;
     }
	  i = InvestListGrid.getSelNo()-1;
    fm.InqNo.value=InvestListGrid.getRowColData(i,1);
    fm.ApplyDate.value=InvestListGrid.getRowColData(i,6);
    fm.SurveyType.value=InvestListGrid.getRowColData(i,7);
    fm.SurveyTypeName.value=InvestListGrid.getRowColData(i,8);
    fm.InqReason.value=InvestListGrid.getRowColData(i,9);
    fm.InqReasonName.value=InvestListGrid.getRowColData(i,10);
    fm.InitiationType.value=InvestListGrid.getRowColData(i,13);
    fm.InqFlowState.value=InvestListGrid.getRowColData(i,15);
    fm.InqCloseReason.value=InvestListGrid.getRowColData(i,17);
    fm.InqCloseReasonName.value=InvestListGrid.getRowColData(i,18);
    fm.InqPurpose.value=InvestListGrid.getRowColData(i,19);
	fm.InqPlan.value=InvestListGrid.getRowColData(i,20);
	fm.Remark.value=InvestListGrid.getRowColData(i,21);

	fm.saveButton.disabled=true;
	fm.recoverButton.disabled=true;
	fm.deleteButton.disabled=true;
	fm.doCloseButton.disabled=true;
	fm.doButton.disabled=true;
	
	querySurveyAttachment();
}
function getOnInvestInfo(){
	var i = OnInvestGrid.getSelNo();
   if(i < 1){
       
       alert("��ѡ��һ�м�¼��");
       return false;
     }
		 i = OnInvestGrid.getSelNo()-1;

    fm.InqNo.value=OnInvestGrid.getRowColData(i,1);
    fm.ApplyDate.value=OnInvestGrid.getRowColData(i,6);
    fm.SurveyType.value=OnInvestGrid.getRowColData(i,7);
    fm.SurveyTypeName.value=OnInvestGrid.getRowColData(i,8);
    fm.InqReason.value=OnInvestGrid.getRowColData(i,9);
    fm.InqReasonName.value=OnInvestGrid.getRowColData(i,10);
		fm.InitiationType.value=OnInvestGrid.getRowColData(i,12);
    fm.InqFlowState.value=OnInvestGrid.getRowColData(i,14);
    fm.InqCloseReason.value=OnInvestGrid.getRowColData(i,16);
    fm.InqCloseReasonName.value=OnInvestGrid.getRowColData(i,17);
    fm.InqPurpose.value=OnInvestGrid.getRowColData(i,18);
 		fm.InqPlan.value=OnInvestGrid.getRowColData(i,19);
		fm.Remark.value=OnInvestGrid.getRowColData(i,20);

		if(fm.InitiationType.value=="1"){
			
			fm.saveButton.disabled=true;
			fm.recoverButton.disabled=false;
			fm.deleteButton.disabled=true;
			fm.doCloseButton.disabled=false;
			fm.doButton.disabled=false;
			
		}else{
			
			fm.saveButton.disabled=true;
			fm.recoverButton.disabled=false;
			fm.deleteButton.disabled=false;
			fm.doCloseButton.disabled=true;
			fm.doButton.disabled=false;
			
		}
		querySurveyAttachment();
}

//��������
function saveAttachmentClick() {

	if(!beforeSaveAttachment()) {
  		return ;
  	}
  	var tAffixName = trim(fm2.AffixName.value);
	var tInqNo = fm.InqNo.value;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSurveySql");
	tSQLInfo.setSqlId("LLClaimSurveySql3");
	tSQLInfo.addSubPara(tInqNo);
	tSQLInfo.addSubPara(tAffixName.toLowerCase());
	var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
	if(arr==null){
		
	}else if(arr[0][0]=="1"){
		
		alert('�������Ʋ�����ͬ��');
		return false;	
		
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSurveySql");
	tSQLInfo.setSqlId("LLClaimSurveySql4");
	tSQLInfo.addSubPara("UploadPath");
	var arr1 = easyExecSql(tSQLInfo.getString(),1, 0, 1);
	
	fm2.AffixPatch.value=arr1[0][0];
	fm2.fmtransact.value= "ATTACHINSERT";
	submitFormAttachment();
}
//�޸ĸ���
function updateLLinqupload(){
	
	var selno = SurveyAttachmentGrid.getSelNo()-1;
	var tClmno = fm.RgtNo.value;
	var tAffixName = trim(fm2.AffixName.value);
	var tInqNo = fm.InqNo.value;
	if (selno <0)
	{
		alert('����ѡ��һ����Ϣ�ٵ��"�޸�"');
		return false;
	}
	if(!beforeSaveAttachment()) {
		return ;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSurveySql");
	tSQLInfo.setSqlId("LLClaimSurveySql3");
	tSQLInfo.addSubPara(tInqNo);
	tSQLInfo.addSubPara(tAffixName.toLowerCase());
	var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
	if(arr!=null&&arr!="") {
		if(arr.length>1) {
			alert('�������Ʋ�����ͬ��');
			return ;	
		}else if(arr.length==1){
			var toldFileName = SurveyAttachmentGrid.getRowColData(selno,4);
			if(toldFileName!=tAffixName) {
				alert('�������Ʋ�����ͬ��');
				return ;	
			}
		}
	}
	fm2.fmtransact.value= "ATTACHUPDATE";
	submitFormAttachment();
}
//ɾ������
function deleteLLinqupload() {
	var selno = SurveyAttachmentGrid.getSelNo()-1;
	if (selno <0)
	{
	   alert('����ѡ��һ����Ϣ�ٵ��"ɾ��"');
	   return false;
	}
	if(!confirm('ȷ��Ҫɾ����¼��?')){
		return false;
	}
	fm2.AttachNo.value = SurveyAttachmentGrid.getRowColData(selno,2);
	fm2.fmtransact.value= "DELETE";
 	submitFormAttachment();
}
	//�ϴ��ļ�	
function uploadFileClick(decide) {
	var selno = SurveyAttachmentGrid.getSelNo()-1;
	if (selno <0)
	{
		alert('����ѡ��һ����Ϣ�ٵ��"�ϴ��ļ�"');
		return false;
	}
	fm2.AffixName.value=SurveyAttachmentGrid.getRowColData(selno,3);
	fm2.OriginalLogo.value=SurveyAttachmentGrid.getRowColData(selno,10);
	fm2.AffixNumber.value=SurveyAttachmentGrid.getRowColData(selno,6);
	var tFilename = SurveyAttachmentGrid.getRowColData(selno,4);
	if(decide=="0") {
		if(tFilename!=null&&tFilename!="") {
			alert("�ļ��Ѿ��ϴ���Ҫ�滻�ļ������滻�ļ�");
			return false;
			}
	}
	if(!uploadAttachmentClick()) {
 	 return false;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSurveySql");
	tSQLInfo.setSqlId("LLClaimSurveySql4");
	tSQLInfo.addSubPara("UploadPath");
	var arr1 = easyExecSql(tSQLInfo.getString(),1, 0, 1);
	
	fm2.AffixPatch.value=arr1[0][0];
	fm2.fmtransact.value= "UPLOADFILE";
 	submitFormAttachment();
}
//�滻�ļ�
function replaceFileClick() {
	var selno = SurveyAttachmentGrid.getSelNo()-1;
	if (selno <0)
	{
		alert('����ѡ��һ����Ϣ�ٵ��"�滻�ļ�"');
		return false;
	}
	var tFilename = SurveyAttachmentGrid.getRowColData(selno,4);
	if(tFilename==null||tFilename=="") {
		alert("�ļ�δ�ϴ��������ϴ��ļ���");
		return false;
	}
	if(!confirm('ȷ��Ҫ�滻�ļ���?')){
		return false;
	}
	if(!uploadFileClick("1")) {
		return false;
		}
	}
//�鿴�ļ�
function viewAttachmentClick()
{
	var selno = SurveyAttachmentGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert('����ѡ��һ����Ϣ�ٵ��"�鿴�ļ�"');
	      return;
	}
	var filepath = SurveyAttachmentGrid.getRowColData(selno,9);
	var fileName1 = SurveyAttachmentGrid.getRowColData(selno,4);
	
	if(fileName1==null||fileName1=="") {
		alert("δ�ϴ��ļ��������ϴ��ļ���");
		return false;
		}
  window.location = "../common/jsp/download.jsp?FilePath="+filepath+"&FileName="+fileName1
}
//ɾ���ļ�
function deleteAttachmentClick()
{
	var selno = SurveyAttachmentGrid.getSelNo()-1;
	if (selno <0)
	{
	   alert('����ѡ��һ����Ϣ�ٵ��"ɾ���ļ�"');
	   return false;
	}
	if(!confirm('ȷ��Ҫɾ����¼��?')){
		return false;
	}
	
	fm2.AttachNo.value = SurveyAttachmentGrid.getRowColData(selno,2);
	fm2.fmtransact.value = "DELETEFILE";
 	submitFormAttachment();
}
	//���渽��ǰ��У��
function beforeSaveAttachment() {
	
	var tClmno = fm.RgtNo.value;
	var tAffixName = trim(fm2.AffixName.value);
	var tInqNo = fm.InqNo.value;
	if (tInqNo=="" || tInqNo==null) {
		alert("����ѡ�������Ϣ");
		return false;
	}
	var tOriginalLogo = fm2.OriginalLogo.value;
	var tAffixNumber = trim(fm2.AffixNumber.value);
	if(tAffixName==null||tAffixName=="") {
		alert("�������Ʋ���Ϊ�գ�");
		fm2.AffixName.focus();
		return false;
		}
	if(tOriginalLogo==null||tOriginalLogo=="") {
		alert("ԭ����ʶ����Ϊ�գ�");
		fm2.OriginalLogo.focus();
		return false;
		}
	if (!verifyInput2()){
		return false;
	}
	if(tAffixNumber==null||tAffixNumber=="") {
		alert("������������Ϊ�գ�");
		fm2.AffixNumber.focus();
		return false;
	}
	var patrn1=/^([1-9]{1}[0-9]*)$/;    
	if(!patrn1.exec(tAffixNumber)) {
		alert("����������������������Ϸ������֣�");
		fm2.AffixNumber.focus();
		return false;
		}
	return true;
}
//��ѯ�����б�
function querySurveyAttachment(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSurveySql");
	tSQLInfo.setSqlId("LLClaimSurveySql5");
	tSQLInfo.addSubPara(fm.InqNo.value);
	turnPage4.queryModal(tSQLInfo.getString(),SurveyAttachmentGrid,"2");

	var rowNum = SurveyAttachmentGrid.mulLineCount;
	if(rowNum<=4) {
		divSurveyAttachmentPage.style.display="none";
	}else{
		divSurveyAttachmentPage.style.display="";
	}
}

//�ϴ�����
function uploadAttachmentClick() {
	var tClmno = fm.RgtNo.value;
	var tInqNo = fm.InqNo.value;
	var ImportFile=trim(fm2.all('FileName').value);
	var filename=ImportFile;
	if(tClmno==null||tClmno=="") {
		alert("����ҵ���������˲�");
		return false;
		}
	if(tInqNo==null||tInqNo=="") {
			alert("����������������˲�");
		return false;
		}	
	if(filename==null||filename=="") {
		alert("�������ظ�������Ϊ��");
		return false;
	}		
	isLLinqSurvey(tClmno,tInqNo);

	if(!chksize(filename)) {
		alert("������ϴ�1000000KB ���ļ�");
		return false;
		}
			getImportPath();
	//ȥ�ļ�������
	if ( filename.indexOf("\\")>0 ) filename=filename.substring(filename.lastIndexOf("\\")+1);
	if ( filename.indexOf("/")>0 ) filename=filename.substring(filename.lastIndexOf("/")+1);
	
	if ( filename.indexOf("_")>0) filename=filename.substring( 0,filename.indexOf("_"));
	if ( filename.indexOf(".")>0) filename=filename.substring( 0,filename.indexOf("."));
	fm2.all('ImportPath').value = ImportPath;
	return true;
}
//չʾ������Ϣ
function showSurveyAttachment() {
	var tSelNo = SurveyAttachmentGrid.getSelNo();
	tSelNo = SurveyAttachmentGrid.getSelNo();
	if(tSelNo==0) {
		alert("��ѡ��һ��������ü�¼");
		return ;
	}
	fm2.FileName.value=SurveyAttachmentGrid.getRowColData(tSelNo-1,4);
    fm2.ImportPath.value=SurveyAttachmentGrid.getRowColData(tSelNo-1,9);
    fm2.AttachNo.value=SurveyAttachmentGrid.getRowColData(tSelNo-1,2);;
    fm2.AffixName.value=SurveyAttachmentGrid.getRowColData(tSelNo-1,3);
    fm2.OriginalLogo.value=SurveyAttachmentGrid.getRowColData(tSelNo-1,10);
    showOneCodeName('originallogo','OriginalLogo','OriginalLogoName');
    fm2.AffixNumber.value=SurveyAttachmentGrid.getRowColData(tSelNo-1,6);
    fm2.ImportFile.value=SurveyAttachmentGrid.getRowColData(tSelNo-1,4);
    fm2.OldFileName.value = SurveyAttachmentGrid.getRowColData(tSelNo-1,4);
}

	//����ϴ�����������ŵ�·��
function getImportPath (){
	// ��дSQL���
	var strSQL="";
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSurveySql");
	tSQLInfo.setSqlId("LLClaimSurveySql4");
	tSQLInfo.addSubPara("UploadPath");
	turnPage3.strQueryResult =easyQueryVer3(tSQLInfo.getString(), 1, 0, 1);
	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage3.strQueryResult) {
		alert("δ�ҵ��ϴ�·��");
		return;
	}
	//�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
	turnPage3.arrDataCacheSet=clearArrayElements(turnPage3.arrDataCacheSet);
	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage3.arrDataCacheSet=decodeEasyQueryResult(turnPage3.strQueryResult);
	ImportPath=turnPage3.arrDataCacheSet[0][0]+fm2.InitDept2.value;
}

//��ѯ�������������������Ŀ¼
function isLLinqSurvey(tClmno,tInqNo)
{
	// ��дSQL���
		tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSurveySql");
	tSQLInfo.setSqlId("LLClaimSurveySql6");
	tSQLInfo.addSubPara(tRgtNo);
	tSQLInfo.addSubPara(tInqNo);
	var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
  fm2.InitDept2.value = arr[0][1];
}
//���ϴ��ļ��Ĵ�С��������
function chksize(strFileName){
	
	var image=new Image();   
	image.src=strFileName;   
	var size = image.fileSize || fileObj.files[0].fileSize;   
	if(size > 1000000){
		return false; 
	}   
	return true;

}
//��ʼ������
function SurveyAttachmentInfo()
{	 
    fm2.FileName.value="";
    fm2.ImportPath.value="";
    fm2.AttachNo.value="";
    fm2.AffixName.value="";
    fm2.OriginalLogo.value="";
    fm2.AffixNumber.value="1";
    fm2.OriginalLogoName.value="";
    fm2.ImportFile.value="";
    fm2.FileName.outerHTML =fm2.FileName.outerHTML ;
    
}
	//�ύ��������
function submitFormAttachment(){
	
	fm2.action="LLSurveyAttachmentSave.jsp?fmtransact="+fm2.fmtransact.value+"&ClmNo="+fm.RgtNo.value+"&InqNo="+fm.InqNo.value+"&AttachNo="+fm2.AttachNo.value+"&AffixName="+fm2.AffixName.value
							+"&OriginalLogo="+fm2.OriginalLogo.value+"&AffixNumber="+fm2.AffixNumber.value+"&AffixPatch="+fm2.AffixPatch.value;
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  fm2.submit(); //�ύ 
  moperator ="2";
}

