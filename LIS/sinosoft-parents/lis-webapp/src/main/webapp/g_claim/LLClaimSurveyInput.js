/***************************************************************
 * <p>ProName��LLClaimSurveyInput.jsp</p>
 * <p>Title������¼��</p>
 * <p>Description������¼��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
var showInfo;
var moperator = "0";//�����ж�����һ������״̬ ( 0  ����¼�뱣��  1  ����¼�뱣�� 2���鸽������ 3 �������¼�뱣�� 4 ���鸴��)
var turnPage5 = new turnPageClass();//������̲�ѯ
turnPage5.pageLineNum=5; 
var turnPage = new turnPageClass();//������̲�ѯ
var turnPage1 = new turnPageClass();//��֤��ѯ
var turnPage2 = new turnPageClass();//���ò�ѯ
turnPage2.pageLineNum=5; 
var turnPage3 = new turnPageClass();//�����ϴ�
var turnPage4 = new turnPageClass();//�����ϴ�muline
turnPage4.pageLineNum=5; 
var arrDataSet;
var ImportPath;
var ImportState="no";

/**����չʾ����**/
function showYC(){
	
	document.getElementById("uploadfile").style.display="none";
	document.getElementById("repalecefile").style.display="none";
	document.getElementById("deletefile").style.display="none";
}

/**
 * �ύ����
 */
function submitForm() {
	
	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = showDialogWindow(urlStr, 0);
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	 var  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	if(moperator=="3") {
		fm3.submit(); //�ύ
	}else if (moperator=="1") {
		fm5.submit();
	}else{
		fm.submit(); //�ύ
	}
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
		
		if(moperator=="4") {
			turnbackforReviewcheck();
		}else if(moperator=="3"){
			turnback();//����
		}
	}	
	//����¼��
		if(moperator=="0") {
			initInqCourseInfo();
			InitQuerySurveyCourse();
		}
		if(moperator=="2") {
			SurveyAttachmentInfo();
			querySurveyAttachment();
		}
				//����¼��
		if(moperator=="1") {
			initInqFeeInfo();
			initSurveyFeeQuery();
		}
		
}
	//���ص��鸴��
function turnbackforReviewcheck()
{
    var strUrl= "LLClaimSurveyCheckInput.jsp"; 
    						
    location.href=strUrl;
}
//���ض���ҳ��
function turnback()
{
	
    var strUrl= "LLClaimSurveyAppInput.jsp";    
    location.href=strUrl;
}
function turnback1()
{
	
    var strUrl= "LLClaimSurveyQueryInput.jsp";    
    location.href=strUrl;
}
/**
	*����������Ϣ
	*/
function QueryInqAppInfo(){

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSurveySql");
	tSQLInfo.setSqlId("LLClaimSurveySql1");
	tSQLInfo.addSubPara(tInqNo);
	tSQLInfo.addSubPara(tRgtNo);
	
	var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
	
	fm.grpName.value=	arr[0][1];
	
	fm.InsuredName.value=arr[0][2];
	fm.IdNo.value=arr[0][3];
	fm.InqType.value=arr[0][4];
	fm3.SurveyType.value = arr[0][4];   //��������
	fm.ApplyDate.value=arr[0][5];
	
	fm.ApplyPer.value=arr[0][6];
	fm.ApplyPerName.value=arr[0][7];
	fm.InitDept.value=arr[0][8];
	fm.InitDeptName.value=arr[0][9];
	fm.InqRCode.value=arr[0][10];
	fm.InqRCodeName.value=arr[0][11];
	fm.SurveyFlowState.value=arr[0][12];
	fm.SurveyFlowStateName.value=arr[0][13];
	
	fm.InqDept.value=arr[0][14];
	fm.InqDeptName.value=arr[0][15];
	fm.IsLoc.value=arr[0][16];
	fm.IsLocName.value=arr[0][17];
	fm.InqItem.value=arr[0][18];
	
	fm.InqPlan.value=arr[0][19];
	fm.Remark.value=arr[0][20];
	fm3.InqConclusion.value=arr[0][21];
	fm3.ConPer.value=arr[0][22];
	fm3.ConDate.value=arr[0][23];
	fm.CustomerNo.value=arr[0][24];
	fm.InqTypeCode.value=arr[0][25];
	fm.RgtNo.value =arr[0][0];
	if(fm.SurveyFlowState.value=="05"){
		
		divSurveyReviewEntry.style.display="";
		divSurveyShowReturnView.style.display="none";
	}
}
//������������Ϣ
function AddInqCourseClick(){
	
		if(!checkSurveySource()) {
				return false;
			}
    fm.fmtransact.value = "INSERT";
		fm.action="LLSurveyProcessSave.jsp";
		moperator = "0";
		submitForm();
		
}

//�޸ĵ��������Ϣ
function UpdateInqCourseClick() {
	var tSelNo = LLSurveyCourseGrid.getSelNo();
	tSelNo = LLSurveyCourseGrid.getSelNo();
	if(tSelNo==0) {
		alert("��ѡ��һ��������̼�¼");
		return ;
	}
	
	if(!checkSurveySource()) {
		return false;
	}
	var CourseCouNo = fm.CourseCouNo.value ;
	if(CourseCouNo==null||CourseCouNo=="") {
		alert("�������Ϊ�գ���˲�������Ϣ��");
		return;
	}
	fm.fmtransact.value = "UPDATE";
	fm.action="LLSurveyProcessSave.jsp";
	moperator = "0";
	submitForm();
	}
	
	//ɾ�����������Ϣ
function DeleteInqCourseClick() {
	var tSelNo = LLSurveyCourseGrid.getSelNo();
	tSelNo = LLSurveyCourseGrid.getSelNo();
	if(tSelNo==0) {
		alert("��ѡ��һ��������̼�¼");
		return ;
	}
	var CourseCouNo = fm.CourseCouNo.value ;
	if(CourseCouNo==null||CourseCouNo=="") {
		alert("�������Ϊ�գ���˲�������Ϣ��");
		return;
	}
	if(!confirm('ȷ��Ҫɾ����¼��?')){
		return false;
	}
	fm.fmtransact.value = "DELETE";
	fm.action="LLSurveyProcessSave.jsp";
	moperator = "0";
	submitForm();
}

//��ѯ���������Ϣ
function InitQuerySurveyCourse() {
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSurveySql");
	tSQLInfo.setSqlId("LLClaimSurveySql2");
	tSQLInfo.addSubPara(tRgtNo);
	tSQLInfo.addSubPara(tInqNo);
	turnPage5.queryModal(tSQLInfo.getString(),LLSurveyCourseGrid,"2");
	
	var rowNum = LLSurveyCourseGrid.mulLineCount;
	if(rowNum<=4) {
		divLLSurveyCoursePage.style.display="none";
	}else{
		
		divLLSurveyCoursePage.style.display="";
	}
}
//���������Ϣ��ʼ��
function initInqCourseInfo()
{
  try
  {
	    	fm.InqMode.value=""; //���鷽ʽ
	    	fm.InqModeName.value=""; //���鷽ʽ
        fm.InqSite.value="";  //����ص�
        fm.InqDate.value=mCurrentDate; //��������
        fm.InqPer1.value=""; //��һ������
        fm.InqPer2.value="";//�ڶ�������
        fm.InqByPer.value="";   //��������
        fm.InqCourse.value="";   //�������¼��  	
        fm.CourseCouNo.value="";//�������
      //  fm.Relation.value="";   //��������������˹�ϵ
       // fm.RelationName.value="";   //��������������˹�ϵ
  }
  catch(ex)
  {
    alert("��ʼ�����������Ϣ����!");
  }
}



//�����Ƿ���������

function checkTSFH(s){
    if(s.indexOf("'")==-1&&s.indexOf("��")==-1&&s.indexOf("��")==-1) 
    {
	    return true;
    }
    return false;
}
//������̱���У��
function checkSurveySource() {
	if (!verifyInput2()){
		return false;
}
	var tInqSite = fm.InqSite.value;
	var tInqDate = fm.InqDate.value;
	var tInqPer1 = fm.InqPer1.value;
	var tInqPer2 = fm.InqPer2.value;
	var tInqByPer = fm.InqByPer.value;
	var tInqCourse = fm.InqCourse.value;
	var tInqMode = fm.InqMode.value;
	if (tInqMode==""||tInqMode==null )
	{
		alert("���鷽ʽ����Ϊ�գ�");
		return false;
	}
	if (tInqSite==""||tInqSite==null )
	{
		alert("����ص㲻��Ϊ�գ�");
		return false;
		}
	if (tInqCourse==""||tInqCourse==null )
	{
		alert("������̲���Ϊ�գ�");
		return false;
	}
	if (tInqDate == null || tInqDate == "") {
		alert("�������ڲ���Ϊ�գ�");
		return false;
	}
	if (tInqPer1 == null || tInqPer1 == "") {
		alert("��һ�����˲���Ϊ�գ�");
		return false;
	}
	if(tInqByPer==null || tInqByPer==""){
		
		alert("�������˲���Ϊ�գ�");
		return false;
	}
	tInqDate = fm.InqDate.value;
	if(compareDate(mCurrentDate,tInqDate)==2){
		alert("�������ڲ������ڵ�ǰ���ڣ�");
		return false;
} 
	if(!checkTSFH(tInqSite)) {
		alert("����ص�¼����в��ܰ���������ţ�'����");
		return false;
		}
	if(!checkTSFH(tInqPer1)) {
		alert("��һ������¼����в��ܰ���������ţ�'����");
		return false;
		}
	if(!checkTSFH(tInqPer2)) {
		alert("�ڶ�������¼����в��ܰ���������ţ�'����");
		return false;
	}
	if(!checkTSFH(tInqByPer)) {
		alert("��������¼����в��ܰ���������ţ�'����");
		return false;
	}
	fm.InqCourse.value = replaceAllSymbol(tInqCourse);
	
	/*if (fm.Relation.value==null || fm.Relation.value=="") {
		alert("[��������������˹�ϵ]����Ϊ�գ�");
		fm.Relation.focus();
		fm.Relation.style.background="#ffb900";
		return false;
	}*/
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
//���������̴�������
function showLLSurveyCourseGridClick(){

		var i = LLSurveyCourseGrid.getSelNo()-1;
		var couno = LLSurveyCourseGrid.getRowColData(i,1);
		fm.CourseCouNo.value = couno;//�������
		fm.InqModeName.value=LLSurveyCourseGrid.getRowColData(i,2); //���鷽ʽ
		fm.InqMode.value=LLSurveyCourseGrid.getRowColData(i,8); //���鷽ʽ
		fm.InqSite.value=LLSurveyCourseGrid.getRowColData(i,3);  //����ص�
		fm.InqDate.value=LLSurveyCourseGrid.getRowColData(i,4); //��������
		fm.InqPer1.value=LLSurveyCourseGrid.getRowColData(i,5); //��һ������
		fm.InqPer2.value=LLSurveyCourseGrid.getRowColData(i,6);//�ڶ�������
		fm.InqByPer.value=LLSurveyCourseGrid.getRowColData(i,7);   //��������
		fm.InqCourse.value=LLSurveyCourseGrid.getRowColData(i,9);
		//querySurveycourse(fm.ClmNo.value,fm.InqNo.value,fm.CourseCouNo.value);
		//fm.Relation.value=LLSurveyCourseGrid.getRowColData(i,9);
		//fm.RelationName.value=LLSurveyCourseGrid.getRowColData(i,10);
}

   //��ʾ�������¼��  	
/*function querySurveycourse(tClmNo,tInqNO,tCourseCouNo) {
	
		var strQueryResult = "";

		fm.action = "LLSurveyCourseQuery.jsp?ClmNo=" + tClmNo + "&InqNO=" + tInqNO + "&CourseCouNo=" + tCourseCouNo;
		
		fm.submit();
*//**		Request = new ActiveXObject("Microsoft.XMLHTTP");
		Request.open("GET",strURL, false);
		Request.send(null);
		try
		{
			strQueryResult = Request.responseText.trim();
			alert("16:"+strQueryResult);
		}
		catch(e1)
		{
			alert("���ݷ��س���"+e1.message);
		}
		if("*$"==strQueryResult) {
			alert("��ѯ�������¼�����");
			strQueryResult = "";
			}**//*
	
	}
	function afterSubmitInfo( strQueryResult){

			fm.InqCourse.value= strQueryResult;
		
	}
	*///��������
function saveAttachmentClick() {
  if(!beforeSaveAttachment()) {
  	return ;
  	}
  var tAffixName = trim(fm2.AffixName.value);
	var tInqno = fm.InqNo.value;
	
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
	var tClmno = fm.ClmNo.value;
  var tAffixName = trim(fm2.AffixName.value);
	var tInqno = fm.InqNo.value;
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
	
	var tClmno = fm.ClmNo.value;
  var tAffixName = trim(fm2.AffixName.value);
	var tInqno = fm.InqNo.value;
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
function querySurveyAttachment(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSurveySql");
	tSQLInfo.setSqlId("LLClaimSurveySql5");
	tSQLInfo.addSubPara(tInqNo);
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
	var tClmno = fm.ClmNo.value;
	var tInqno = fm.InqNo.value;
	var ImportFile=trim(fm2.all('FileName').value);
	var filename=ImportFile;
	if(tClmno==null||tClmno=="") {
		alert("����ҵ���������˲�");
		return false;
		}
	if(tInqno==null||tInqno=="") {
			alert("����������������˲�");
		return false;
		}	
	if(filename==null||filename=="") {
		alert("�������ظ�������Ϊ��");
		return false;
	}		
	isLLinqSurvey(tClmno,tInqno);

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
function isLLinqSurvey(tClmno,tInqno)
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
	var size = image.fileSize || fm2.FileName.files[0].fileSize;   
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
	
	fm2.action="LLSurveyAttachmentSave.jsp?fmtransact="+fm2.fmtransact.value+"&ClmNo="+fm.ClmNo.value+"&InqNo="+fm.InqNo.value+"&AttachNo="+fm2.AttachNo.value+"&AffixName="+fm2.AffixName.value
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


//��ӱ�����������Ϣ
function AddInqFeeClick()
{
    if(!beforeFeeSava()) {
    	return false;
    	}
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimSurveySql");
		tSQLInfo.setSqlId("LLClaimSurveySql7");
		tSQLInfo.addSubPara(fm.ClmNo.value);
		tSQLInfo.addSubPara(fm.InqNo.value);
		tSQLInfo.addSubPara(fm.InqDept.value);
		tSQLInfo.addSubPara(fm5.FeeType.value);
		var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
    if (arr != null)
    {
	      alert("ͬһ��������ֻ��¼��һ��!");
	      return;
    }

		fm5.fmtransact.value = "INSERT";
		moperator = "1";
		fm5.action="LLSurveyFeeSave.jsp?ClmNo="+fm.ClmNo.value+"&InqNo="+fm.InqNo.value+"&InqDept="+fm.InqDept.value;
		submitForm();

}

//�޸ĵ��������Ϣ
function UpdateFeeClick() {
	var tSelNo = LLSurveyFeeGrid.getSelNo();
	tSelNo = LLSurveyFeeGrid.getSelNo();
	if(tSelNo==0) {
		alert("��ѡ��һ��������ü�¼");
		return ;
	}
	if(!beforeFeeSava()) {
		return false;
	}
	tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimSurveySql");
		tSQLInfo.setSqlId("LLClaimSurveySql7");
		tSQLInfo.addSubPara(fm.ClmNo.value);
		tSQLInfo.addSubPara(fm.InqNo.value);
		tSQLInfo.addSubPara(fm.InqDept.value);
		tSQLInfo.addSubPara(fm5.FeeType.value);
		var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
	if (arr != null)
	{
		if(arr.length>1) {
			alert("ͬһ��������ֻ��¼��һ��!");
			return;
		}else {
			var oldFeetype = arr[0][0];
			if(fm5.FeeType.value!=oldFeetype) {
				alert("ͬһ��������ֻ��¼��һ��!");
				return;
			}
		}
	}
	

	
	fm5.fmtransact.value = "UPDATE";
	moperator = "1";
	fm5.action="LLSurveyFeeSave.jsp?ClmNo="+fm.ClmNo.value+"&InqNo="+fm.InqNo.value+"&InqDept="+fm.InqDept.value+"&FeeTypeOld="+fm5.FeeTypeOld.value;
	submitForm();
	}

//ɾ�����������Ϣ
function DeleteFeeClick() {
	var tSelNo = LLSurveyFeeGrid.getSelNo();
	tSelNo = LLSurveyFeeGrid.getSelNo();
	if(tSelNo==0) {
		alert("��ѡ��һ��������ü�¼");
		return ;
	}
	
	if (fm5.FeeType.value == ""|| fm5.FeeType.value==null)
	{
		alert("��������Ϊ�գ����ʵ������Ϣ��");
		return;
	}
	if(!confirm('ȷ��Ҫɾ����¼��?')){
		return false;
	}
	fm5.fmtransact.value = "DELETE";
	moperator = "1";
	fm5.action="LLSurveyFeeSave.jsp?ClmNo="+fm.ClmNo.value+"&InqNo="+fm.InqNo.value+"&InqDept="+fm.InqDept.value;
	submitForm();
	}
	//������ñ���ǰУ��
function beforeFeeSava() {
	if (!verifyInput2()){
		return false;
	}
	
	if (fm5.FeeDate.value == null || fm5.FeeDate.value == "") {
		alert("���鷢��ʱ�䲻��Ϊ�գ�");
		return false;
	}
	if(compareDate(mCurrentDate,fm5.FeeDate.value)==2){
			alert("�������ڲ������ڵ�ǰ���ڣ�");
			return false;
		}
	var tFeeDate = fm5.FeeDate.value;
	var tPayee = fm5.Payee.value;
	var tRemark = fm5.Remark.value;
	if(fm5.FeeType.value==null||fm5.FeeType.value == "") {
		alert("�������Ͳ���Ϊ�գ�");
		return false;
	}
	if(fm5.FeeSum.value==null||fm5.FeeSum.value == "") {
		alert("���ý���Ϊ�գ�");
		return false;
	}
	if(tRemark==null||tRemark== "") {
		alert("��ע����Ϊ�գ�");
		return false;
	}
	var patrn1=/^(([1-9]{1}[0-9]*)|((([1-9]{1}[0-9]*)|0)\.\d{1,2})|(0))$/;    
	if(!patrn1.exec(fm5.FeeSum.value))
	{
		alert("���ý����д����");
		fm5.FeeSum.focus();
		return false;
	} 
	
	if(!checkTSFH(tPayee)) {
		alert("�����¼����в��ܰ���������ţ�'����");
		return false;
	}
	
	if(tRemark.length>500) {
		alert("��ע��Ϣ���ܳ���500�֣�");
		return false;
		}
	fm5.Remark.value = replaceAllSymbol(tRemark);	
	return true;
	}	
	//���������Ϣ��ʼ��
function initInqFeeInfo()
{
  try
  {
    fm5.FeeType.value="";//��������
    fm5.FeeTypeName.value="";//��������
    fm5.FeeSum.value="";//���ý��
    fm5.FeeDate.value="";//����ʱ��
    fm5.Payee.value="";//�����
    fm5.PayeeType.value="";//  ��ʽ
    fm5.PayeeTypeName.value="";//  ��ʽ
    fm5.Remark.value="";    //��ע
  }
  catch(ex)
  {
    alert("��ʼ�����������Ϣ����!");
  }
}
//��ѯ��������б�
function  initSurveyFeeQuery()
{
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimSurveySql");
		tSQLInfo.setSqlId("LLClaimSurveySql8");
		tSQLInfo.addSubPara(fm.ClmNo.value);
		tSQLInfo.addSubPara(fm.InqNo.value);
		tSQLInfo.addSubPara(fm.InqDept.value);
		turnPage2.queryModal(tSQLInfo.getString(),LLSurveyFeeGrid,"2");
		var rowNum = LLSurveyFeeGrid.mulLineCount;   
	if(rowNum<=4) {
		divLLSurveyFeePage.style.display="none";
	}else{
		divLLSurveyFeePage.style.display="";
	}
}
function showLLSurveyFeeClick(){
	
	var i = LLSurveyFeeGrid.getSelNo()-1;
		fm5.FeeTypeName.value =LLSurveyFeeGrid.getRowColData(i,1);//��������
    fm5.FeeType.value =LLSurveyFeeGrid.getRowColData(i,2);//��������
    fm5.FeeTypeOld.value = LLSurveyFeeGrid.getRowColData(i,2);//��žɵķ�������
    fm5.FeeSum.value=LLSurveyFeeGrid.getRowColData(i,3);//���ý��
    fm5.FeeDate.value=LLSurveyFeeGrid.getRowColData(i,4);//����ʱ��
    fm5.Payee.value=LLSurveyFeeGrid.getRowColData(i,5);//�����
    fm5.PayeeTypeName.value =LLSurveyFeeGrid.getRowColData(i,6);//  ��ʽ
    fm5.PayeeType.value=LLSurveyFeeGrid.getRowColData(i,7);//  ��ʽ
    fm5.Remark.value=LLSurveyFeeGrid.getRowColData(i,8);    //��ע
	
	
}


//�������ȷ��
function InqConfirmClick() {
		if (!verifyInput2()){
			
				return false;
		}
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimSurveySql");
		tSQLInfo.setSqlId("LLClaimSurveySql9");
		tSQLInfo.addSubPara(fm.ClmNo.value);
		tSQLInfo.addSubPara(fm.InqNo.value);

		var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
    if(arr==null)
    {
    	alert("û���κε�����̣����鲻������ɣ�");	
    	return;
    }            
	  if(fm3.InqConclusion.value =="" || fm3.InqConclusion.value ==null)
	  {
			alert("����д�������!");
			return;  
	  }
	  
	  if(fm3.InqConclusion.value.length>1000) {
	  	alert("���������Ϣ���Ȳ��ܳ���1000��!");
			return;  
	  	}
	  
		fm3.InqConclusion.value =  replaceAllSymbol(fm3.InqConclusion.value);
		fm3.fmtransact.value = "CONFIRMUPDATE";
		fm3.SurveyType.value = fm.InqType.value;
		moperator="3";
		fm3.action="LLSurveyConfirmSave.jsp";
		submitForm();    
}
//�������󵥴�ӡ
function InqPrint() {
	moperator="3";
	fm3.action="LLClaimSurvePrintSave.jsp?RgtNo="+fm.ClmNo.value+"&InqNo="+fm.InqNo.value+"&Operate=INQDEMANDPRINT";
	submitForm();    
	
}
/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit1(FlagStr, content,filepath,tfileName) {
	
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
		var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
	} else {
			window.location = "../common/jsp/download.jsp?FilePath="+filepath+"&FileName="+tfileName
	}	
}
//����ȷ��
function ReviewCheckInput() {
	if (!verifyInput2()){
			return false;
	}
	var tInqState = fm4.CasetypeCode.value;
	if(tInqState==null||tInqState=="") {
		alert("���˽��۲���Ϊ�գ�");
		return;
		}
	var tInqConclusion =  fm4.InqConclusion.value;
	if(tInqConclusion==null||tInqConclusion=="") {
		alert("�����������Ϊ�գ�");
			return;
		}
		
  if(tInqConclusion.length>1000) {
		alert("����������Ȳ��ܳ���1000��!");
		return;  
	}	
		
	fm4.InqConclusion.value = replaceAllSymbol(tInqConclusion);				
	var tClmNo = fm.ClmNo.value;
	var tInqNo = fm.InqNo.value;
	var tSurveyType = fm.InqType.value;
	var tConNo =fm4.ConNo.value;
	fm4.fmtransact.value = "REVIEWCONFIRM";
  fm4.action="LLSurveyReviewConfirmSave.jsp?ClmNo="+tClmNo+"&InqNo="+tInqNo+"&ConNo="+tConNo+"&SurveyType="+tSurveyType; 
	
	submitFormReviewCheck();
	}
		//����ȷ���ύ
	function submitFormReviewCheck() {

    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
    moperator = "4";
		fm4.submit();
	}
	//��ѯ���˽���
	function queryUwConclusionInfo(){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimSurveySql");
		tSQLInfo.setSqlId("LLClaimSurveySql10");
		tSQLInfo.addSubPara(fm.ClmNo.value);
		tSQLInfo.addSubPara(fm.InqNo.value);

		var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
		
		if (arr !=null){
			
			fm4.CasetypeCode.value=	arr[0][0];
			fm4.CasetypeName.value=arr[0][1];
			fm4.uwConPer.value=arr[0][2];
			fm4.uwConDate.value=arr[0][3];
			fm4.InqConclusion.value=arr[0][4];
	}
	}
	/**
		*������ѯ
		*/
	function policyQuery(){		
		
		
		var strUrl="./LLClaimQueryPolicyMain.jsp?CustomerNo="+fm.CustomerNo.value+"&RgtNo="+tRgtNo+"&Mode=1";
		var tWidth=1000;
		var tHeight=600;
		var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
		var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
		window.open(strUrl,"������ѯ",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
	/**
	*��ȫ��ѯ
	*/
	function edorQuery(){
		
		var strUrl="./LLClaimQueryEdorMain.jsp?CustomerNo="+fm.CustomerNo.value+"&Mode=1";
		var tWidth=1000;
		var tHeight=600;
		var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
		var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
		window.open(strUrl,"��ȫ��ѯ",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

	}
	/**
		*�����ⰸ��ѯ
		*/
		
	function clmPastCaseQuery (){
			
			var strUrl="./LLClaimLastCaseMain.jsp?CustomerNo="+fm.CustomerNo.value+"&Mode=1";
			var tWidth=1000;
			var tHeight=600;
			var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
			var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
			window.open(strUrl,"�ⰸ��ѯ",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
			
		}
		
	