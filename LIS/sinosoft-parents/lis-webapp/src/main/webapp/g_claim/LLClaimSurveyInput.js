/***************************************************************
 * <p>ProName：LLClaimSurveyInput.jsp</p>
 * <p>Title：调查录入</p>
 * <p>Description：调查录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
var showInfo;
var moperator = "0";//用来判断是哪一个保存状态 ( 0  过程录入保存  1  费用录入保存 2调查附件保存 3 调查结论录入保存 4 调查复核)
var turnPage5 = new turnPageClass();//调查过程查询
turnPage5.pageLineNum=5; 
var turnPage = new turnPageClass();//调查过程查询
var turnPage1 = new turnPageClass();//单证查询
var turnPage2 = new turnPageClass();//费用查询
turnPage2.pageLineNum=5; 
var turnPage3 = new turnPageClass();//附件上传
var turnPage4 = new turnPageClass();//附件上传muline
turnPage4.pageLineNum=5; 
var arrDataSet;
var ImportPath;
var ImportState="no";

/**界面展示控制**/
function showYC(){
	
	document.getElementById("uploadfile").style.display="none";
	document.getElementById("repalecefile").style.display="none";
	document.getElementById("deletefile").style.display="none";
}

/**
 * 提交数据
 */
function submitForm() {
	
	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = showDialogWindow(urlStr, 0);
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	 var  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	if(moperator=="3") {
		fm3.submit(); //提交
	}else if (moperator=="1") {
		fm5.submit();
	}else{
		fm.submit(); //提交
	}
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
		
		if(moperator=="4") {
			turnbackforReviewcheck();
		}else if(moperator=="3"){
			turnback();//返回
		}
	}	
	//过程录入
		if(moperator=="0") {
			initInqCourseInfo();
			InitQuerySurveyCourse();
		}
		if(moperator=="2") {
			SurveyAttachmentInfo();
			querySurveyAttachment();
		}
				//费用录入
		if(moperator=="1") {
			initInqFeeInfo();
			initSurveyFeeQuery();
		}
		
}
	//返回调查复核
function turnbackforReviewcheck()
{
    var strUrl= "LLClaimSurveyCheckInput.jsp"; 
    						
    location.href=strUrl;
}
//返回队列页面
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
	*调查申请信息
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
	fm3.SurveyType.value = arr[0][4];   //调查类型
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
//保存调查过程信息
function AddInqCourseClick(){
	
		if(!checkSurveySource()) {
				return false;
			}
    fm.fmtransact.value = "INSERT";
		fm.action="LLSurveyProcessSave.jsp";
		moperator = "0";
		submitForm();
		
}

//修改调查过程信息
function UpdateInqCourseClick() {
	var tSelNo = LLSurveyCourseGrid.getSelNo();
	tSelNo = LLSurveyCourseGrid.getSelNo();
	if(tSelNo==0) {
		alert("请选中一条调查过程记录");
		return ;
	}
	
	if(!checkSurveySource()) {
		return false;
	}
	var CourseCouNo = fm.CourseCouNo.value ;
	if(CourseCouNo==null||CourseCouNo=="") {
		alert("过程序号为空，请核查输入信息！");
		return;
	}
	fm.fmtransact.value = "UPDATE";
	fm.action="LLSurveyProcessSave.jsp";
	moperator = "0";
	submitForm();
	}
	
	//删除调查过程信息
function DeleteInqCourseClick() {
	var tSelNo = LLSurveyCourseGrid.getSelNo();
	tSelNo = LLSurveyCourseGrid.getSelNo();
	if(tSelNo==0) {
		alert("请选中一条调查过程记录");
		return ;
	}
	var CourseCouNo = fm.CourseCouNo.value ;
	if(CourseCouNo==null||CourseCouNo=="") {
		alert("过程序号为空，请核查输入信息！");
		return;
	}
	if(!confirm('确定要删除记录吗?')){
		return false;
	}
	fm.fmtransact.value = "DELETE";
	fm.action="LLSurveyProcessSave.jsp";
	moperator = "0";
	submitForm();
}

//查询调查过程信息
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
//调查过程信息初始化
function initInqCourseInfo()
{
  try
  {
	    	fm.InqMode.value=""; //调查方式
	    	fm.InqModeName.value=""; //调查方式
        fm.InqSite.value="";  //调查地点
        fm.InqDate.value=mCurrentDate; //调查日期
        fm.InqPer1.value=""; //第一调查人
        fm.InqPer2.value="";//第二调查人
        fm.InqByPer.value="";   //被调查人
        fm.InqCourse.value="";   //调查过程录入  	
        fm.CourseCouNo.value="";//过程序号
      //  fm.Relation.value="";   //被调查人与出险人关系
       // fm.RelationName.value="";   //被调查人与出险人关系
  }
  catch(ex)
  {
    alert("初始化调查过程信息错误!");
  }
}



//检验是否含有特殊字

function checkTSFH(s){
    if(s.indexOf("'")==-1&&s.indexOf("‘")==-1&&s.indexOf("’")==-1) 
    {
	    return true;
    }
    return false;
}
//调查过程保存校验
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
		alert("调查方式不能为空！");
		return false;
	}
	if (tInqSite==""||tInqSite==null )
	{
		alert("调查地点不能为空！");
		return false;
		}
	if (tInqCourse==""||tInqCourse==null )
	{
		alert("调查过程不能为空！");
		return false;
	}
	if (tInqDate == null || tInqDate == "") {
		alert("调查日期不能为空！");
		return false;
	}
	if (tInqPer1 == null || tInqPer1 == "") {
		alert("第一调查人不能为空！");
		return false;
	}
	if(tInqByPer==null || tInqByPer==""){
		
		alert("被调查人不能为空！");
		return false;
	}
	tInqDate = fm.InqDate.value;
	if(compareDate(mCurrentDate,tInqDate)==2){
		alert("调查日期不能晚于当前日期！");
		return false;
} 
	if(!checkTSFH(tInqSite)) {
		alert("调查地点录入框中不能包含特殊符号：'’‘");
		return false;
		}
	if(!checkTSFH(tInqPer1)) {
		alert("第一调查人录入框中不能包含特殊符号：'’‘");
		return false;
		}
	if(!checkTSFH(tInqPer2)) {
		alert("第二调查人录入框中不能包含特殊符号：'’‘");
		return false;
	}
	if(!checkTSFH(tInqByPer)) {
		alert("被调查人录入框中不能包含特殊符号：'’‘");
		return false;
	}
	fm.InqCourse.value = replaceAllSymbol(tInqCourse);
	
	/*if (fm.Relation.value==null || fm.Relation.value=="") {
		alert("[被调查人与出险人关系]不可为空！");
		fm.Relation.focus();
		fm.Relation.style.background="#ffb900";
		return false;
	}*/
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
//点击调查过程触发方法
function showLLSurveyCourseGridClick(){

		var i = LLSurveyCourseGrid.getSelNo()-1;
		var couno = LLSurveyCourseGrid.getRowColData(i,1);
		fm.CourseCouNo.value = couno;//过程序号
		fm.InqModeName.value=LLSurveyCourseGrid.getRowColData(i,2); //调查方式
		fm.InqMode.value=LLSurveyCourseGrid.getRowColData(i,8); //调查方式
		fm.InqSite.value=LLSurveyCourseGrid.getRowColData(i,3);  //调查地点
		fm.InqDate.value=LLSurveyCourseGrid.getRowColData(i,4); //调查日期
		fm.InqPer1.value=LLSurveyCourseGrid.getRowColData(i,5); //第一调查人
		fm.InqPer2.value=LLSurveyCourseGrid.getRowColData(i,6);//第二调查人
		fm.InqByPer.value=LLSurveyCourseGrid.getRowColData(i,7);   //被调查人
		fm.InqCourse.value=LLSurveyCourseGrid.getRowColData(i,9);
		//querySurveycourse(fm.ClmNo.value,fm.InqNo.value,fm.CourseCouNo.value);
		//fm.Relation.value=LLSurveyCourseGrid.getRowColData(i,9);
		//fm.RelationName.value=LLSurveyCourseGrid.getRowColData(i,10);
}

   //显示调查过程录入  	
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
			alert("数据返回出错："+e1.message);
		}
		if("*$"==strQueryResult) {
			alert("查询调查过程录入错误");
			strQueryResult = "";
			}**//*
	
	}
	function afterSubmitInfo( strQueryResult){

			fm.InqCourse.value= strQueryResult;
		
	}
	*///新增附件
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
	var tClmno = fm.ClmNo.value;
  var tAffixName = trim(fm2.AffixName.value);
	var tInqno = fm.InqNo.value;
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
	
	var tClmno = fm.ClmNo.value;
  var tAffixName = trim(fm2.AffixName.value);
	var tInqno = fm.InqNo.value;
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

//上传附件
function uploadAttachmentClick() {
	var tClmno = fm.ClmNo.value;
	var tInqno = fm.InqNo.value;
	var ImportFile=trim(fm2.all('FileName').value);
	var filename=ImportFile;
	if(tClmno==null||tClmno=="") {
		alert("传入业务号有误，请核查");
		return false;
		}
	if(tInqno==null||tInqno=="") {
			alert("传入调查序号有误，请核查");
		return false;
		}	
	if(filename==null||filename=="") {
		alert("调查上载附件不能为空");
		return false;
	}		
	isLLinqSurvey(tClmno,tInqno);

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
function isLLinqSurvey(tClmno,tInqno)
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
	var size = image.fileSize || fm2.FileName.files[0].fileSize;   
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
	
	fm2.action="LLSurveyAttachmentSave.jsp?fmtransact="+fm2.fmtransact.value+"&ClmNo="+fm.ClmNo.value+"&InqNo="+fm.InqNo.value+"&AttachNo="+fm2.AttachNo.value+"&AffixName="+fm2.AffixName.value
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


//添加保存调查费用信息
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
	      alert("同一费用类型只能录入一次!");
	      return;
    }

		fm5.fmtransact.value = "INSERT";
		moperator = "1";
		fm5.action="LLSurveyFeeSave.jsp?ClmNo="+fm.ClmNo.value+"&InqNo="+fm.InqNo.value+"&InqDept="+fm.InqDept.value;
		submitForm();

}

//修改调查费用信息
function UpdateFeeClick() {
	var tSelNo = LLSurveyFeeGrid.getSelNo();
	tSelNo = LLSurveyFeeGrid.getSelNo();
	if(tSelNo==0) {
		alert("请选中一条调查费用记录");
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
			alert("同一费用类型只能录入一次!");
			return;
		}else {
			var oldFeetype = arr[0][0];
			if(fm5.FeeType.value!=oldFeetype) {
				alert("同一费用类型只能录入一次!");
				return;
			}
		}
	}
	

	
	fm5.fmtransact.value = "UPDATE";
	moperator = "1";
	fm5.action="LLSurveyFeeSave.jsp?ClmNo="+fm.ClmNo.value+"&InqNo="+fm.InqNo.value+"&InqDept="+fm.InqDept.value+"&FeeTypeOld="+fm5.FeeTypeOld.value;
	submitForm();
	}

//删除调查费用信息
function DeleteFeeClick() {
	var tSelNo = LLSurveyFeeGrid.getSelNo();
	tSelNo = LLSurveyFeeGrid.getSelNo();
	if(tSelNo==0) {
		alert("请选中一条调查费用记录");
		return ;
	}
	
	if (fm5.FeeType.value == ""|| fm5.FeeType.value==null)
	{
		alert("费用类型为空，请核实输入信息！");
		return;
	}
	if(!confirm('确定要删除记录吗?')){
		return false;
	}
	fm5.fmtransact.value = "DELETE";
	moperator = "1";
	fm5.action="LLSurveyFeeSave.jsp?ClmNo="+fm.ClmNo.value+"&InqNo="+fm.InqNo.value+"&InqDept="+fm.InqDept.value;
	submitForm();
	}
	//调查费用保存前校验
function beforeFeeSava() {
	if (!verifyInput2()){
		return false;
	}
	
	if (fm5.FeeDate.value == null || fm5.FeeDate.value == "") {
		alert("调查发生时间不能为空！");
		return false;
	}
	if(compareDate(mCurrentDate,fm5.FeeDate.value)==2){
			alert("调查日期不能晚于当前日期！");
			return false;
		}
	var tFeeDate = fm5.FeeDate.value;
	var tPayee = fm5.Payee.value;
	var tRemark = fm5.Remark.value;
	if(fm5.FeeType.value==null||fm5.FeeType.value == "") {
		alert("费用类型不能为空！");
		return false;
	}
	if(fm5.FeeSum.value==null||fm5.FeeSum.value == "") {
		alert("费用金额不能为空！");
		return false;
	}
	if(tRemark==null||tRemark== "") {
		alert("备注不能为空！");
		return false;
	}
	var patrn1=/^(([1-9]{1}[0-9]*)|((([1-9]{1}[0-9]*)|0)\.\d{1,2})|(0))$/;    
	if(!patrn1.exec(fm5.FeeSum.value))
	{
		alert("费用金额填写有误！");
		fm5.FeeSum.focus();
		return false;
	} 
	
	if(!checkTSFH(tPayee)) {
		alert("领款人录入框中不能包含特殊符号：'’‘");
		return false;
	}
	
	if(tRemark.length>500) {
		alert("备注信息不能超过500字！");
		return false;
		}
	fm5.Remark.value = replaceAllSymbol(tRemark);	
	return true;
	}	
	//调查费用信息初始化
function initInqFeeInfo()
{
  try
  {
    fm5.FeeType.value="";//费用类型
    fm5.FeeTypeName.value="";//费用类型
    fm5.FeeSum.value="";//费用金额
    fm5.FeeDate.value="";//发生时间
    fm5.Payee.value="";//领款人
    fm5.PayeeType.value="";//  领款方式
    fm5.PayeeTypeName.value="";//  领款方式
    fm5.Remark.value="";    //备注
  }
  catch(ex)
  {
    alert("初始化调查费用信息错误!");
  }
}
//查询调查费用列表
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
		fm5.FeeTypeName.value =LLSurveyFeeGrid.getRowColData(i,1);//费用类型
    fm5.FeeType.value =LLSurveyFeeGrid.getRowColData(i,2);//费用类型
    fm5.FeeTypeOld.value = LLSurveyFeeGrid.getRowColData(i,2);//存放旧的费用类型
    fm5.FeeSum.value=LLSurveyFeeGrid.getRowColData(i,3);//费用金额
    fm5.FeeDate.value=LLSurveyFeeGrid.getRowColData(i,4);//发生时间
    fm5.Payee.value=LLSurveyFeeGrid.getRowColData(i,5);//领款人
    fm5.PayeeTypeName.value =LLSurveyFeeGrid.getRowColData(i,6);//  领款方式
    fm5.PayeeType.value=LLSurveyFeeGrid.getRowColData(i,7);//  领款方式
    fm5.Remark.value=LLSurveyFeeGrid.getRowColData(i,8);    //备注
	
	
}


//调查结论确认
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
    	alert("没有任何调查过程，调查不可以完成！");	
    	return;
    }            
	  if(fm3.InqConclusion.value =="" || fm3.InqConclusion.value ==null)
	  {
			alert("请填写调查结论!");
			return;  
	  }
	  
	  if(fm3.InqConclusion.value.length>1000) {
	  	alert("调查结论信息长度不能超过1000字!");
			return;  
	  	}
	  
		fm3.InqConclusion.value =  replaceAllSymbol(fm3.InqConclusion.value);
		fm3.fmtransact.value = "CONFIRMUPDATE";
		fm3.SurveyType.value = fm.InqType.value;
		moperator="3";
		fm3.action="LLSurveyConfirmSave.jsp";
		submitForm();    
}
//调查需求单打印
function InqPrint() {
	moperator="3";
	fm3.action="LLClaimSurvePrintSave.jsp?RgtNo="+fm.ClmNo.value+"&InqNo="+fm.InqNo.value+"&Operate=INQDEMANDPRINT";
	submitForm();    
	
}
/**
 * 提交数据后返回操作
 */
function afterSubmit1(FlagStr, content,filepath,tfileName) {
	
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
		var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
	} else {
			window.location = "../common/jsp/download.jsp?FilePath="+filepath+"&FileName="+tfileName
	}	
}
//复核确认
function ReviewCheckInput() {
	if (!verifyInput2()){
			return false;
	}
	var tInqState = fm4.CasetypeCode.value;
	if(tInqState==null||tInqState=="") {
		alert("复核结论不能为空！");
		return;
		}
	var tInqConclusion =  fm4.InqConclusion.value;
	if(tInqConclusion==null||tInqConclusion=="") {
		alert("复核意见不能为空！");
			return;
		}
		
  if(tInqConclusion.length>1000) {
		alert("复核意见长度不能超过1000字!");
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
		//复核确认提交
	function submitFormReviewCheck() {

    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
    moperator = "4";
		fm4.submit();
	}
	//查询复核结论
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
		*保单查询
		*/
	function policyQuery(){		
		
		
		var strUrl="./LLClaimQueryPolicyMain.jsp?CustomerNo="+fm.CustomerNo.value+"&RgtNo="+tRgtNo+"&Mode=1";
		var tWidth=1000;
		var tHeight=600;
		var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
		var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
		window.open(strUrl,"保单查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
	/**
	*保全查询
	*/
	function edorQuery(){
		
		var strUrl="./LLClaimQueryEdorMain.jsp?CustomerNo="+fm.CustomerNo.value+"&Mode=1";
		var tWidth=1000;
		var tHeight=600;
		var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
		var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
		window.open(strUrl,"保全查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

	}
	/**
		*既往赔案查询
		*/
		
	function clmPastCaseQuery (){
			
			var strUrl="./LLClaimLastCaseMain.jsp?CustomerNo="+fm.CustomerNo.value+"&Mode=1";
			var tWidth=1000;
			var tHeight=600;
			var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
			var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
			window.open(strUrl,"赔案查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
			
		}
		
	