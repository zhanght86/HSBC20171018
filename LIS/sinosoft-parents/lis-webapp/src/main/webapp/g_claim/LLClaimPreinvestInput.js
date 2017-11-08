/***************************************************************
 * <p>ProName：LLClaimPreinvestInput.js</p>
 * <p>Title：发起调查</p>
 * <p>Description：发起调查</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
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
 * 调查明细查询
 */
function showInvest() {
		var i = InvestListGrid.getSelNo();
		if(i < 1){
			
			alert("请选中一行已发起的调查信息！");
			return false;
     }
		i = InvestListGrid.getSelNo()-1;
    var tInqNo=InvestListGrid.getRowColData(i,1);
    var RregisterNo=InvestListGrid.getRowColData(i,2);
    
    var strUrl= "LLClaimQueryPreinvestInputMain.jsp?State=2&InqNo="+fm.InqNo.value+"&RgtNo="+fm.RgtNo.value;   
		window.open(strUrl,"调查明细查询",'width=1000,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * 收回调查
 */
function returnInvest() {
	
   var i = InvestListGrid.getSelNo();
   if(i < 1)
    {
        alert("请选中一行已发起的调查信息！");
        return false;
     }
    if(fm.InqFlowState.value!="01"){
			alert("调查收回只针对未分配的调查信息");
			return false;
	}
		fm.Operate.value ="RETURNINVEST";
		submitForm();
	
}
/**
 * 新增调查
 */
function saveSurvey() {
	
	if (!verifyInput2()){
			return false;
	}

	if(!checkInput()) {
	
			return false;
		}

	if(compareDate(tCurrentDate,fm.ApplyDate.value)>0){
		
		alert("调查申请日期不能早于当前日期！");
		return false;
	}
	
	fm.Operate.value ="INSERT";
	fm.LocFlag.value="0";// 0-本地 1-异地
	fm.InqFlowState.value="00";//流程状态：待发起
	fm.InitiationType.value="0";//发起方式：0手动发起
	submitForm();
}
/**
 * 修改调查
 */
function modifySurvey() {
	
	if (!verifyInput2()){
			return false;
	}
	var i = OnInvestGrid.getSelNo();
   if(i < 1)
    {
        alert("请选中一行待发起的调查信息！");
        return false;
     }
	if(!checkInput()) {
	
			return false;
		}

	if(compareDate(tCurrentDate,fm.ApplyDate.value)>0){
		
		alert("调查申请日期不能早于当前日期！");
		return false;
	}
	
	fm.Operate.value ="UPDATE";
	fm.LocFlag.value="0";// 0-本地 1-异地
	fm.InqFlowState.value="00";//流程状态：待发起
	submitForm();
	
}
/**
 * 删除调查
 */
function deleteSurvey() {
	var i = OnInvestGrid.getSelNo();
   if(i < 1)
    {
        alert("请选中一行待发起的调查信息！");
        return false;
     }
		if (confirm("您确实想删除该记录吗?")){	
			
			fm.Operate.value ="DELETE";
			submitForm();
    }
    else{
			
			return false;
    }
}
/**
 * 关闭调查
 */
function closeSurvey() {
	var i = OnInvestGrid.getSelNo();
   if(i < 1)
    {
        alert("请选中一行待发起的调查信息！");
        return false;
     }
     if(trim(fm.InqCloseReason.value)==""){
     	
     		alert("请录入调查关闭原因");
     		return false;
    }
    
   if (confirm("您确实想关闭该记录吗?")){	
			
			fm.Operate.value ="COLCONFIRM";
			submitForm();
    }
}
/**
 * 发起调查
 */
function startSurvey() {
	var i = OnInvestGrid.getSelNo();
	if(i < 1){
		
		alert("请选中一行待发起的调查信息！");
		
		return false;
	}
	if (confirm("您确实想对该记录发起调查吗?")){
			
			fm.Operate.value ="CONFIRM";
			submitForm();
    }
}
/**重置**/
function returnBack(){
	
	initForm();
	
}

/**
 * 返回
 */
function goBack() {
	top.close();
}

//初始化数据
function initValue(){
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimPreinvestSql");
		tSQLInfo.setSqlId("LLClaimPreinvestSql1");
		tSQLInfo.addSubPara(tCustomerNo);
		var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
		if(arr == null){
				alert("查询被调查人信息失败");
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
			fm.SurveyTypeName.value="赔案调查";
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimPreinvestSql");
			tSQLInfo.setSqlId("LLClaimPreinvestSql5");
			tSQLInfo.addSubPara(tRgtNo);
			
		}else if(tSurveyType=="02"){
			fm.SurveyType.value="0";
			fm.SurveyTypeName.value="前置调查";
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
		//showDialogWindow(urlStr, 1);
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showDialogWindow(urlStr, 1);
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
	}	
	initForm();
}

/**
 * 下拉框选择以后调用
 */
function afterCodeSelect(cCodeName, Field) {

	if(cCodeName == '') {
		
	}
	
}
//比较时间的方法
function compareDate(strDate1,strDate2){
	
		var date1 = new Date(strDate1.replace(/\-/g, "\/"));  
		var date2 = new Date(strDate2.replace(/\-/g, "\/"));  

		return date1-date2; 
}

//对输入的校验
function checkInput() {
	
	var tInqItem = fm.InqPurpose.value;
	var tInqDesc = fm.InqPlan.value;
	fm.InqPurpose.value = 	replaceAllSymbol(tInqItem);
	fm.InqPlan.value = replaceAllSymbol(tInqDesc);
	
	return true;
	}

//将“’”替换为中文的
function replaceAllSymbol(filed) {
	var s = filed;
		
	while(s.indexOf("'")!=-1) {
		s = s.replace("'","‘");
	}

	return s;
	}
	
/**Mulline 查询**/
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
        alert("请选中一行记录！");
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
       
       alert("请选中一行记录！");
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

//新增附件
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
		
		alert('附件名称不能相同！');
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
//修改附件
function updateLLinqupload(){
	
	var selno = SurveyAttachmentGrid.getSelNo()-1;
	var tClmno = fm.RgtNo.value;
	var tAffixName = trim(fm2.AffixName.value);
	var tInqNo = fm.InqNo.value;
	if (selno <0)
	{
		alert('请先选择一条信息再点击"修改"');
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
			alert('附件名称不能相同！');
			return ;	
		}else if(arr.length==1){
			var toldFileName = SurveyAttachmentGrid.getRowColData(selno,4);
			if(toldFileName!=tAffixName) {
				alert('附件名称不能相同！');
				return ;	
			}
		}
	}
	fm2.fmtransact.value= "ATTACHUPDATE";
	submitFormAttachment();
}
//删除附件
function deleteLLinqupload() {
	var selno = SurveyAttachmentGrid.getSelNo()-1;
	if (selno <0)
	{
	   alert('请先选择一条信息再点击"删除"');
	   return false;
	}
	if(!confirm('确定要删除记录吗?')){
		return false;
	}
	fm2.AttachNo.value = SurveyAttachmentGrid.getRowColData(selno,2);
	fm2.fmtransact.value= "DELETE";
 	submitFormAttachment();
}
	//上传文件	
function uploadFileClick(decide) {
	var selno = SurveyAttachmentGrid.getSelNo()-1;
	if (selno <0)
	{
		alert('请先选择一条信息再点击"上传文件"');
		return false;
	}
	fm2.AffixName.value=SurveyAttachmentGrid.getRowColData(selno,3);
	fm2.OriginalLogo.value=SurveyAttachmentGrid.getRowColData(selno,10);
	fm2.AffixNumber.value=SurveyAttachmentGrid.getRowColData(selno,6);
	var tFilename = SurveyAttachmentGrid.getRowColData(selno,4);
	if(decide=="0") {
		if(tFilename!=null&&tFilename!="") {
			alert("文件已经上传，要替换文件请点击替换文件");
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
//替换文件
function replaceFileClick() {
	var selno = SurveyAttachmentGrid.getSelNo()-1;
	if (selno <0)
	{
		alert('请先选择一条信息再点击"替换文件"');
		return false;
	}
	var tFilename = SurveyAttachmentGrid.getRowColData(selno,4);
	if(tFilename==null||tFilename=="") {
		alert("文件未上传，请先上传文件！");
		return false;
	}
	if(!confirm('确定要替换文件吗?')){
		return false;
	}
	if(!uploadFileClick("1")) {
		return false;
		}
	}
//查看文件
function viewAttachmentClick()
{
	var selno = SurveyAttachmentGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert('请先选择一条信息再点击"查看文件"');
	      return;
	}
	var filepath = SurveyAttachmentGrid.getRowColData(selno,9);
	var fileName1 = SurveyAttachmentGrid.getRowColData(selno,4);
	
	if(fileName1==null||fileName1=="") {
		alert("未上传文件，请先上传文件！");
		return false;
		}
  window.location = "../common/jsp/download.jsp?FilePath="+filepath+"&FileName="+fileName1
}
//删除文件
function deleteAttachmentClick()
{
	var selno = SurveyAttachmentGrid.getSelNo()-1;
	if (selno <0)
	{
	   alert('请先选择一条信息再点击"删除文件"');
	   return false;
	}
	if(!confirm('确定要删除记录吗?')){
		return false;
	}
	
	fm2.AttachNo.value = SurveyAttachmentGrid.getRowColData(selno,2);
	fm2.fmtransact.value = "DELETEFILE";
 	submitFormAttachment();
}
	//保存附件前的校验
function beforeSaveAttachment() {
	
	var tClmno = fm.RgtNo.value;
	var tAffixName = trim(fm2.AffixName.value);
	var tInqNo = fm.InqNo.value;
	if (tInqNo=="" || tInqNo==null) {
		alert("请先选择调查信息");
		return false;
	}
	var tOriginalLogo = fm2.OriginalLogo.value;
	var tAffixNumber = trim(fm2.AffixNumber.value);
	if(tAffixName==null||tAffixName=="") {
		alert("附件名称不能为空！");
		fm2.AffixName.focus();
		return false;
		}
	if(tOriginalLogo==null||tOriginalLogo=="") {
		alert("原件标识不能为空！");
		fm2.OriginalLogo.focus();
		return false;
		}
	if (!verifyInput2()){
		return false;
	}
	if(tAffixNumber==null||tAffixNumber=="") {
		alert("附件件数不能为空！");
		fm2.AffixNumber.focus();
		return false;
	}
	var patrn1=/^([1-9]{1}[0-9]*)$/;    
	if(!patrn1.exec(tAffixNumber)) {
		alert("附件件数输入有误，请输入合法的数字！");
		fm2.AffixNumber.focus();
		return false;
		}
	return true;
}
//查询附件列表
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

//上传附件
function uploadAttachmentClick() {
	var tClmno = fm.RgtNo.value;
	var tInqNo = fm.InqNo.value;
	var ImportFile=trim(fm2.all('FileName').value);
	var filename=ImportFile;
	if(tClmno==null||tClmno=="") {
		alert("传入业务号有误，请核查");
		return false;
		}
	if(tInqNo==null||tInqNo=="") {
			alert("传入调查序号有误，请核查");
		return false;
		}	
	if(filename==null||filename=="") {
		alert("调查上载附件不能为空");
		return false;
	}		
	isLLinqSurvey(tClmno,tInqNo);

	if(!chksize(filename)) {
		alert("最大能上传1000000KB 的文件");
		return false;
		}
			getImportPath();
	//去文件的名称
	if ( filename.indexOf("\\")>0 ) filename=filename.substring(filename.lastIndexOf("\\")+1);
	if ( filename.indexOf("/")>0 ) filename=filename.substring(filename.lastIndexOf("/")+1);
	
	if ( filename.indexOf("_")>0) filename=filename.substring( 0,filename.indexOf("_"));
	if ( filename.indexOf(".")>0) filename=filename.substring( 0,filename.indexOf("."));
	fm2.all('ImportPath').value = ImportPath;
	return true;
}
//展示附件信息
function showSurveyAttachment() {
	var tSelNo = SurveyAttachmentGrid.getSelNo();
	tSelNo = SurveyAttachmentGrid.getSelNo();
	if(tSelNo==0) {
		alert("请选中一条调查费用记录");
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

	//查出上传到服务器存放的路径
function getImportPath (){
	// 书写SQL语句
	var strSQL="";
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSurveySql");
	tSQLInfo.setSqlId("LLClaimSurveySql4");
	tSQLInfo.addSubPara("UploadPath");
	turnPage3.strQueryResult =easyQueryVer3(tSQLInfo.getString(), 1, 0, 1);
	//判断是否查询成功
	if (!turnPage3.strQueryResult) {
		alert("未找到上传路径");
		return;
	}
	//清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
	turnPage3.arrDataCacheSet=clearArrayElements(turnPage3.arrDataCacheSet);
	//查询成功则拆分字符串，返回二维数组
	turnPage3.arrDataCacheSet=decodeEasyQueryResult(turnPage3.strQueryResult);
	ImportPath=turnPage3.arrDataCacheSet[0][0]+fm2.InitDept2.value;
}

//查询出管理机构，用来生产目录
function isLLinqSurvey(tClmno,tInqNo)
{
	// 书写SQL语句
		tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSurveySql");
	tSQLInfo.setSqlId("LLClaimSurveySql6");
	tSQLInfo.addSubPara(tRgtNo);
	tSQLInfo.addSubPara(tInqNo);
	var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
  fm2.InitDept2.value = arr[0][1];
}
//对上传文件的大小进行限制
function chksize(strFileName){
	
	var image=new Image();   
	image.src=strFileName;   
	var size = image.fileSize || fileObj.files[0].fileSize;   
	if(size > 1000000){
		return false; 
	}   
	return true;

}
//初始化附件
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
	//提交附件数据
function submitFormAttachment(){
	
	fm2.action="LLSurveyAttachmentSave.jsp?fmtransact="+fm2.fmtransact.value+"&ClmNo="+fm.RgtNo.value+"&InqNo="+fm.InqNo.value+"&AttachNo="+fm2.AttachNo.value+"&AffixName="+fm2.AffixName.value
							+"&OriginalLogo="+fm2.OriginalLogo.value+"&AffixNumber="+fm2.AffixNumber.value+"&AffixPatch="+fm2.AffixPatch.value;
	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  fm2.submit(); //提交 
  moperator ="2";
}

