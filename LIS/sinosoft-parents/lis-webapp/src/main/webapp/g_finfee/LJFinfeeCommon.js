/**
  校验数据整数位及小数位长度
 */
function checkDecimalFormat(cValue, cLen1, cLen2) {
	
	if (cValue=='' || cValue==null) {//为空,
		return true;
	}
	
	cValue = cValue.replace(/['\-','\+']/g,"");//去除掉正负号
	
	var tLen =  (""+cValue+"").length;
	var tLen1 = (""+cValue+"").indexOf(".");
	var tLen2 = 0;
	if (tLen1==-1) {
		tLen1 = tLen;
	} else {
		tLen2 = tLen - tLen1 - 1;
	}
	
	if (Number(tLen1)>Number(cLen1)) {
		return false;
	}
	
	if (Number(tLen2)>Number(cLen2)) {
		return false;
	}
	
	return true;
}

/**
 * 移除特殊字符
 */
function removeSpecChar(cObj) {
	
	var tValue = cObj.value;
	tValue = tValue.replace(/[ ,',','，','\-','\_']/g,"");
	cObj.value = tValue;
}

function checkUpFile(cFileName) {
	
	if(cFileName=="" || cFileName==null) {
		alert("请选择要上传的文件！");
		return false;
	}
	//检查文件类型是否符合
	var tFilePath = cFileName;
	var tFileName = tFilePath.substring(tFilePath.lastIndexOf("\\")+1);
	var tFileSuffix = tFilePath.substring(tFilePath.lastIndexOf("."));
	if(tFileSuffix==".doc" || tFileSuffix==".DOC" || tFileSuffix==".docx" || tFileSuffix==".DOCX" ||
		 tFileSuffix==".txt" || tFileSuffix==".TXT" || tFileSuffix==".xls"  || tFileSuffix==".XLS" || 
		 tFileSuffix==".xlsx"|| tFileSuffix==".XLSX" || tFileSuffix==".pdf" || tFileSuffix==".PDF" || tFileSuffix==".zip" || tFileSuffix==".ZIP" || tFileSuffix==".rar" || tFileSuffix==".RAR") {
	}else {
		alert("不支持此文件类型上传！");
		return false;
	}
	
	return true;
}
