/***************************************************************
 * <p>ProName：LLClaimHospitalInput.js</p>
 * <p>Title：医院信息维护</p>
 * <p>Description：医院信息维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/***医院信息查询**/
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
		alert("未查询到符合条件的查询结果！");
	}

}
/**Mulline触发方法**/
function LLCommendHospitalGridClick(){
	
	 var i = LLCommendHospitalGrid.getSelNo();
   if(i < 1)
    {
        alert("请选中一行记录！");
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
		
		fm.HospitalCode.disabled= true; //医院代码禁止修改
		fm.saveHospitalButton.disabled=true;
		fm.editHospitalButton.disabled=false;
		fm.deleteHospitalButton.disabled=false;
}
/**新增医院信息**/
function HospitalAddClick(){
	
	if(trim(fm.HospitalCode.value)==""){
		
		alert("医院代码不能为空");
		return false;
	}
	if(trim(fm.HospitalName.value)==""){
		
		alert("医院名称不能为空");
		return false;
	}
	if(fm.HosState.value==""){
		
		alert("医院状态不能为空");
		return false;
	}
	
	if(fm.HosGrade.value==""){
		
		alert("医院等级不能为空");
		return false;
	}
	/**校验医院信息是否已经存在**/
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.QueryHospitalSql");
	tSQLInfo.setSqlId("HospitalSql2");
	tSQLInfo.addSubPara(fm.HospitalCode.value);
	
	var arr = easyExecSql(tSQLInfo.getString());
	if(arr==null){
		
	}else{
		
		alert("该医院代码在系统中已经存在,请核查录入的医院信息");
		return false;
	}
	
	fm.Operate.value="INSERT";
	fm.action="./LLClaimHospitalSave.jsp";
	
	submitForm();
}
/**修改**/
function HospitalEditClick(){
	
	if(trim(fm.HospitalCode.value)==""){
		
		alert("医院代码不能为空");
		return false;
	}
	if(trim(fm.HospitalName.value)==""){
		
		alert("医院名称不能为空");
		return false;
	}
	if(fm.HosState.value==""){
		
		alert("医院状态不能为空");
		return false;
	}
	
	if(fm.HosGrade.value==""){
		
		alert("医院等级不能为空");
		return false;
	}
	
	if (confirm("您确实想修改该记录吗?")){
		
			fm.HospitalCode.disabled= false; //[医院代码]---否则无法取到数据
			fm.Operate.value="UPDATE";
			fm.action="./LLClaimHospitalSave.jsp";
			submitForm();
			
    }
    else{
    	
			fm.Operate.value="";
			return false;        
    }
	
}
/**删除**/
function HospitalDeleteClick(){
	
		if (confirm("您确实想删除该记录吗?")){	
			fm.HospitalCode.disabled= false; //[医院代码]---否则无法取到数据
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
/**重置**/
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
		fm.HospitalCode.disabled= false; //医院?
		fm.saveHospitalButton.disabled=false;   
		fm.editHospitalButton.disabled=true;  
		fm.deleteHospitalButton.disabled=true;

} 

/**
 * 导入
 */
function submitImport() {
	
	var filePath = uploadfm.UploadPath.value;
	if(filePath == null || filePath == ""){
		alert("请选择导入文件路径！");
		return false;
	}
	
	var indexFirst = filePath.lastIndexOf("\\");
	var indexLast = filePath.lastIndexOf(".xlsx");
	if(indexFirst < 0 || indexLast < 0 || indexLast <= indexFirst) {
		alert("文件路径不合法或选择的文件格式不正确，请重新选择！");
		return false;
	}

	var fileName = filePath.substring(indexFirst+1,indexLast);		
	
	if (fileName.length>=100) {
		
		alert("文件名长度不能超过100！");
		return false;
	}
	
	var i = 0;
	var showStr = "正在导入，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");      	
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
	uploadfm.ImpOperate.value = "EXCELIMP";	
	
	uploadfm.action = "./LLClaimHospImpSave.jsp?Operate="+uploadfm.ImpOperate.value;
	uploadfm.submit();	
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
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
	}	
	initLLCommendHospitalGrid();
	QueryHospitalInfo();
}
/**
 * 查询省之前的校验
 */
function beforQueryCity(Filed) {
	
	var provinceCode = Filed.value;
	if(provinceCode==null||provinceCode=="") {
		alert("请先选择省！");
		Filed.focus();
		return false;
	}
	return true;
}

/**
 * 查询区/县之前的校验
 */
function beforQueryCounty(Filed) {
	
	var cityCode = Filed.value;
	if (cityCode==null||cityCode=="") {
		alert("请先选择市！");
		Filed.focus();
		return false;
	}
	
	return true;
}

//数据导出
function exportData() {
	
	if (!confirm("确认要导出数据？")) {
		return false;
	}
	
	//报表标题
	var tTitle = "医院代码^|医院名称^|医院等级编码^|医院等级^|医院状态编码^|医院状态^|"
							
							+"医院电话^|"
							+"所在地址";
	 
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
