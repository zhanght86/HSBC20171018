/**
  У����������λ��С��λ����
 */
function checkDecimalFormat(cValue, cLen1, cLen2) {
	
	if (cValue=='' || cValue==null) {//Ϊ��,
		return true;
	}
	
	cValue = cValue.replace(/['\-','\+']/g,"");//ȥ����������
	
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
 * �Ƴ������ַ�
 */
function removeSpecChar(cObj) {
	
	var tValue = cObj.value;
	tValue = tValue.replace(/[ ,',','��','\-','\_']/g,"");
	cObj.value = tValue;
}

function checkUpFile(cFileName) {
	
	if(cFileName=="" || cFileName==null) {
		alert("��ѡ��Ҫ�ϴ����ļ���");
		return false;
	}
	//����ļ������Ƿ����
	var tFilePath = cFileName;
	var tFileName = tFilePath.substring(tFilePath.lastIndexOf("\\")+1);
	var tFileSuffix = tFilePath.substring(tFilePath.lastIndexOf("."));
	if(tFileSuffix==".doc" || tFileSuffix==".DOC" || tFileSuffix==".docx" || tFileSuffix==".DOCX" ||
		 tFileSuffix==".txt" || tFileSuffix==".TXT" || tFileSuffix==".xls"  || tFileSuffix==".XLS" || 
		 tFileSuffix==".xlsx"|| tFileSuffix==".XLSX" || tFileSuffix==".pdf" || tFileSuffix==".PDF" || tFileSuffix==".zip" || tFileSuffix==".ZIP" || tFileSuffix==".rar" || tFileSuffix==".RAR") {
	}else {
		alert("��֧�ִ��ļ������ϴ���");
		return false;
	}
	
	return true;
}
