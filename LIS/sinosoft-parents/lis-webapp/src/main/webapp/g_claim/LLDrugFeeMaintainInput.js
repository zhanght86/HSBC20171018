/***************************************************************
 * <p>ProName：LLDrugFeeMaintainInput.js</p>
 * <p>Title：药品信息维护</p>
 * <p>Description：药品信息维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();
/**
	*查询药品信息
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
		alert("未查询到符合条件的查询结果！");
		return false;
	}
}
/**
	*mulline展示数据
	*/
function showPermissionInfo(){
	 
	 var i = DrugFeeMaintainGrid.getSelNo();
   if(i < 1)
    {
        alert("请选中一行记录！");
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
	*增加药品信息
	*/
function addDrugFee(){
		
	if (!verifyInput2()) {
		
			return false;
	}
	if(fm.drugFeeMaintainName.value==""&&fm.bussinessName.value==""){
		
		alert("药品名称与商品名称至少有一个不能为空");
		return false;
	
	}

	fm.Operate.value="INSERT";
	fm.action="./LLDrugFeeMaintainSave.jsp";
	submitForm();
}
/**
	*修改药品信息
	*/
function modifyDrugFee(){
		
	if (!verifyInput2()) {
		
			return false;
	}
	if(fm.drugFeeMaintainName.value==""&&fm.bussinessName.value==""){
		
		alert("药品名称与商品名称至少有一个不能为空");
		return false;
		
	}
	
		fm.Operate.value="UPDATE";
		fm.action="./LLDrugFeeMaintainSave.jsp";
		submitForm();
}
/**
	*删除药品信息
	*/
function deleteDrugFee(){
		
	if (confirm("您确实想删除该记录吗?")){	
		
		fm.Operate.value="DELETE";
		fm.action="./LLDrugFeeMaintainSave.jsp";
		submitForm();
		}else{
			
		fm.Operate.value="";
		
		return false;
    }
}
/**
	*重置界面信息
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
 * 药品信息导入
 */
function medicalImport() {
	
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
	showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");      	
	
	uploadfm.ImpOperate.value = "EXCELIMP";	
	
	uploadfm.action = "./LLClaimDrugImpSave.jsp?Operate="+uploadfm.ImpOperate.value;
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
	initForm();
	restartDrugFee();
	queryPermissionInfo();
}
//数据导出
function exportData() {
	
	if (!confirm("确认要导出数据？")) {
		return false;
	}
	
	//报表标题
	var tTitle = "药品流水号^|地区编码^|地区^|更新日期^|药品名称^|商品名称^|剂型^|"
							+"规格^|自付比例^|医保类型^|价格^|限制内容^|医保类型编码";
	 
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
