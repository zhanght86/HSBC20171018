/***************************************************************
 * <p>ProName��LLClaimMajorInput.js</p>
 * <p>Title���ش󰸼��ϱ�/¼��</p>
 * <p>Description���ش󰸼��ϱ�/¼��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * ��ʼ��ҳ��չʾ��Ϣ
 */
function initPageInfo() {
	
		var tArr;
		
		//��ѯ�Ƿ������¼����ϱ���Ϣ
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMajorSql");
		tSQLInfo.setSqlId("LLClaimMajorSql");
		tSQLInfo.addSubPara(mRptNo);
		tSQLInfo.addSubPara(mCustomerNo);
		tArr = null;
		tArr = easyExecSql(tSQLInfo.getString());

		//�ж��Ƿ��ѯ�ɹ�
		if (tArr==null || tArr.length==0) {
			fm.reportMajor.style.display = "";			
			/*document.getElementById("InputRemarks").setAttribute('readOnly',false);		*/
			document.getElementById("InputRemarks").readOnly=false;		
			return;
		} else {
			fm.InputOperator.value = tArr[0][0];			
			fm.InputDate.value = tArr[0][1];
			fm.InputCom.value = tArr[0][2];
			fm.InputComName.value = tArr[0][3];
			fm.InputRemarks.value = tArr[0][4];
		/*	document.getElementById("InputRemarks").setAttribute('readOnly',true);*/
			document.getElementById("InputRemarks").readOnly=true;		
			fm.reportMajor.style.display = "none";			
		}
		//��ѯ�Ƿ������¼��ķֹ�˾�ظ���Ϣ
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMajorSql");
		tSQLInfo.setSqlId("LLClaimMajorSql1");
		tSQLInfo.addSubPara(mRptNo);
		tSQLInfo.addSubPara(mCustomerNo);
		tArr = null;
		tArr = easyExecSql(tSQLInfo.getString());
		//�ж��Ƿ��ѯ�ɹ�
		if (tArr==null || tArr.length==0) {
			document.getElementById("divReturnButton").style.display="";
			document.getElementById("divInputRepInfo").style.display="none";			
			return;
		} else {
			document.getElementById("divReturnButton").style.display="";
			document.getElementById("divInputRepInfo").style.display="";			
			document.getElementById("divBranchInfo").style.display="";
			fm.InputRepOperator.value = tArr[0][0];
			fm.InputRepDate.value = tArr[0][1];
			fm.InputRepCom.value = tArr[0][2];
			fm.InputRepComName.value = tArr[0][3];
			fm.InputRepRemarks.value = tArr[0][4];			
		}
		//��ѯ�Ƿ������¼����ܹ�˾�ظ���Ϣ
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMajorSql");
		tSQLInfo.setSqlId("LLClaimMajorSql2");
		tSQLInfo.addSubPara(mRptNo);
		tSQLInfo.addSubPara(mCustomerNo);
		tArr = null;
		tArr = easyExecSql(tSQLInfo.getString());
		//�ж��Ƿ��ѯ�ɹ�
		if (tArr==null || tArr.length==0) {

			document.getElementById("divBranchInfo1").style.display="none";
			return;
		} else {

			document.getElementById("divBranchInfo1").style.display="";
			fm.InputRepOperator1.value = tArr[0][0];
			fm.InputRepDate1.value = tArr[0][1];
			fm.InputRepCom1.value = tArr[0][2];
			fm.InputRepComName1.value = tArr[0][3];
			fm.InputRepRemarks1.value = tArr[0][4];			
		}	
}

/**
 * �ش󰸼��ϱ�
 */
function majorReport() {
	
	var tRemark = fm.InputRemarks.value;
	if (tRemark==null || tRemark.length==0) {
		alert("�����������Ϊ�գ�");
		fm.InputRepRemarks.focus();
		return false;
	}
	fm.Operate.value = "INSERT";
	submitForm();
}

/**
 * ��ӡ�ش󰸼�
 */
function casePrint() {
			
	
	fm.Operate.value="PRINT";
	submitForm();	
}

/**
 * �ش󰸼�����ȷ��
 */
function majorApprove() {
	
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

	showInfo.focus();
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
	
		showInfo.focus();
	} else {
				
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
	
		if (fm.Operate.value == "PRINT") {
			window.location = "../common/jsp/download.jsp?FilePath="+filepath+"&FileName="+tfileName;
		}	else {
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
