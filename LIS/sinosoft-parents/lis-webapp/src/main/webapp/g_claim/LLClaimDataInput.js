/***************************************************************
 * <p>ProName��LLClaimDataInput.js</p>
 * <p>Title��������</p>
 * <p>Description��������</p>
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
var tSQLInfo = new SqlClass();
//turnPage.pageLineNum = 20;


//��ʼ����֤��Ϣ
function initAffix() {
	
	//��ѯ������������
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
		if(confirm("�ó����˻�δ¼���¼���Ϣ����������ֻչʾ���ò��֣��Ƿ����?")){
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
			//ѡ�и���
		}
	}
	
}

/**
 * ���浥֤
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
		alert("������ѡ��һ����������");
		return false;
	}
	fm.Operate.value="INSERT";
	submitForm();		
}
/**
 * ɾ����֤
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
		alert("������ѡ��һ����������");
		return false;
	}
	fm.Operate.value="DELETE";
	submitForm();	
}
/**
 * ���ɵ�֤
 */
function createDocument() {
	
		if (fm.AffixName.value==null || fm.AffixName.value=="") {
			alert("��֤���Ʋ���Ϊ�գ�");
			return false;
		}
		DocumentListGrid.addOne();
		var rows=DocumentListGrid.mulLineCount;
		DocumentListGrid.setRowColData(rows-1,1,"0000000000");
		DocumentListGrid.setRowColData(rows-1,2,fm.AffixName.value);
		DocumentListGrid.setRowColData(rows-1,3,"000001");
		DocumentListGrid.setRowColData(rows-1,4,"����");
		DocumentListGrid.setRowColData(rows-1,5,"ԭ��");
		DocumentListGrid.setRowColData(rows-1,6,"01");
		DocumentListGrid.setRowColData(rows-1,7,"δ����");
		DocumentListGrid.setRowColData(rows-1,8,"");
	
}
/**
 * ��ӡ��������
 */
function printClaimData() {
	
    fm.Operate.value="PRINT";
    submitForm();	
}
/**
 * ����
 */
function goBack() {
	top.close();
}

/**
 * �ύ����
 */
function submitForm() {
	
	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	fm.submit();
}

/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content, filepath, tfileName) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		if (fm.Operate.value=="PRINT") {
			window.location = "../common/jsp/download.jsp?FilePath="+filepath+"&FileName="+tfileName;
		} else {
			initForm();
		}
	}	
}

/**
 * ������ѡ���Ժ����
 */
function afterCodeSelect(cCodeName, Field) {

	if(cCodeName == '') {
		
	}
	
}
