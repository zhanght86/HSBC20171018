/***************************************************************
 * <p>ProName：LLClaimDataInput.js</p>
 * <p>Title：索赔金额</p>
 * <p>Description：索赔金额</p>
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
var tSQLInfo = new SqlClass();
//turnPage.pageLineNum = 20;


//初始化单证信息
function initAffix() {
	
	//查询报案出险类型
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimDataSql");
	tSQLInfo.setSqlId("LLClaimDataSql");
	tSQLInfo.addSubPara(mRptNo)
	tSQLInfo.addSubPara(mCustomerNo);
	
	var tClaimType = "";
	var tClaimReason = "";
	var tClaimAllType = "";
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr!=null && tArr.length!=0) {
		
		for(var j=0;j<tArr.length;j++) {

			if (j==tArr.length-1) {
				tClaimAllType = tClaimAllType + tArr[j][0];
			} else {
				tClaimAllType = tClaimAllType + tArr[j][0] + ",";				
			}
		}
		var tClaimAllArr = tClaimAllType.split(",");
		if (tClaimAllArr==null || tClaimAllArr.length==0) {
			tClaimType = tClaimAllType;
		} else {
			
			for (var i=0;i<tClaimAllArr.length;i++) {
				if (i==tClaimAllArr.length-1) {
					tClaimType = tClaimType+"'"+tClaimAllArr[i]+"'";					
				} else {
					tClaimType = tClaimType+"'"+tClaimAllArr[i]+"',";
				}
			}		
		}
	} else {
		if(confirm("该出险人还未录入事件信息，索赔资料只展示公用部分，是否继续?")){
			tClaimType = "''";
		} else {
			top.close();
		}
	}
	tClaimType = tClaimType;	
	
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimDataSql");
	tSQLInfo.setSqlId("LLClaimDataSql1");
	tSQLInfo.addSubPara(mRptNo);
	tSQLInfo.addSubPara(mRptNo);
	tSQLInfo.addSubPara(mCustomerNo);
	tSQLInfo.addSubPara(tClaimType);
	turnPage1.queryModal(tSQLInfo.getString(),DocumentListGrid,2,1);
	
	var rowNum = DocumentListGrid.mulLineCount;
	var flag = false;
	
	for(var i=0;i<rowNum;i++) {
		
		var tAffixNo = DocumentListGrid.getRowColData(i,8);
		if (tAffixNo!=null && tAffixNo!="") {
			//选中该行
		}
	}
	
}

/**
 * 保存单证
 */
function saveDocument() {
	
	
	var rowNum = DocumentListGrid.mulLineCount;
	var flag = false;
	
	for(var i=0;i<rowNum;i++) {
		if(DocumentListGrid.getChkNo(i)) {
			flag = true;
		}
	}
	if (!flag) {
		alert("请至少选择一条索赔资料");
		return false;
	}
	fm.Operate.value="INSERT";
	submitForm();		
}
/**
 * 删除单证
 */
function deleteDocument() {
	var rowNum = DocumentListGrid.mulLineCount;
	var flag = false;
	
	for(var i=0;i<rowNum;i++) {
		if(DocumentListGrid.getChkNo(i)) {
			flag = true;
		}
	}
	if (!flag) {
		alert("请至少选择一条索赔资料");
		return false;
	}
	fm.Operate.value="DELETE";
	submitForm();	
}
/**
 * 生成单证
 */
function createDocument() {
	
		if (fm.AffixName.value==null || fm.AffixName.value=="") {
			alert("单证名称不可为空！");
			return false;
		}
		DocumentListGrid.addOne();
		var rows=DocumentListGrid.mulLineCount;
		DocumentListGrid.setRowColData(rows-1,1,"0000000000");
		DocumentListGrid.setRowColData(rows-1,2,fm.AffixName.value);
		DocumentListGrid.setRowColData(rows-1,3,"000001");
		DocumentListGrid.setRowColData(rows-1,4,"其他");
		DocumentListGrid.setRowColData(rows-1,5,"原件");
		DocumentListGrid.setRowColData(rows-1,6,"01");
		DocumentListGrid.setRowColData(rows-1,7,"未保存");
		DocumentListGrid.setRowColData(rows-1,8,"");
	
}
/**
 * 打印索赔资料
 */
function printClaimData() {
	
    fm.Operate.value="PRINT";
    submitForm();	
}
/**
 * 返回
 */
function goBack() {
	top.close();
}

/**
 * 提交数据
 */
function submitForm() {
	
	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	fm.submit();
}

/**
 * 提交数据后返回操作
 */
function afterSubmit(FlagStr, content, filepath, tfileName) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		if (fm.Operate.value=="PRINT") {
			window.location = "../common/jsp/download.jsp?FilePath="+filepath+"&FileName="+tfileName;
		} else {
			initForm();
		}
	}	
}

/**
 * 下拉框选择以后调用
 */
function afterCodeSelect(cCodeName, Field) {

	if(cCodeName == '') {
		
	}
	
}
