/***************************************************************
 * <p>ProName��LLClaimNoticePrintInput.js</p>
 * <p>Title������֪ͨ���ӡ</p>
 * <p>Description������֪ͨ���ӡ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();

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
function afterSubmit(FlagStr, content,filepath,tfileName) {
	
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
	
		window.location = "../common/jsp/download.jsp?FilePath="+filepath+"&FileName="+tfileName;
	}	
}

/*
 *���β�ѯ
 */
function easyQueryClick(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimNoticePrintSql");
	tSQLInfo.setSqlId("LLClaimNoticePrintSql");
	tSQLInfo.addSubPara(mManageCom);
	tSQLInfo.addSubPara(fm.BatchNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.MngCom.value);
	tSQLInfo.addSubPara(fm.RgtdateStart.value);
	tSQLInfo.addSubPara(fm.RgtdateEnd.value);
	turnPage1.queryModal(tSQLInfo.getString(), ClaimGrid,"2");
	
}
function QueryRgtClick( grpRgtNo){
		
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimNoticePrintSql");
	tSQLInfo.setSqlId("LLClaimNoticePrintSql1");
	if(grpRgtNo==null){
		
	tSQLInfo.addSubPara("");
	}else {
		
	tSQLInfo.addSubPara(grpRgtNo);
	}
	tSQLInfo.addSubPara(mManageCom);
	tSQLInfo.addSubPara(fm.GrpName1.value);
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.MngCom1.value);
	tSQLInfo.addSubPara(fm.RgtNo1.value);
	tSQLInfo.addSubPara(fm.EndDateStart.value);
	tSQLInfo.addSubPara(fm.EndDateEnd.value);
	turnPage2.queryModal(tSQLInfo.getString(), CaseGrid,"2");
	
}
function queryCustomer(){
	
	var i = ClaimGrid.getSelNo();
   if(i < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return false;
     }
	i = ClaimGrid.getSelNo()-1;
	
	fm.GrpRgtNo.value = ClaimGrid.getRowColData(i,1);
	
	QueryRgtClick(fm.GrpRgtNo.value);
	
}
function onSelSelected(){
	
	var i = CaseGrid.getSelNo();
   if(i < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return false;
     }
	i = CaseGrid.getSelNo()-1;
	fm.RgtNo.value=CaseGrid.getRowColData(i,2);
	
}
/**
	*�������ν᰸֪ͨ���ӡ
	*/
function GrpGetPrint(){
	
	var i = ClaimGrid.getSelNo();
	if(i < 1) {
			
		alert("��ѡ��һ��������Ϣ��");
		return false;
     }
    i = ClaimGrid.getSelNo()-1;
    fm.GrpRgtNo.value=ClaimGrid.getRowColData(i,1);    
    fm.Operate.value="BATCHENDPRINT";
    submitForm();
}

/**
	*���˸���������ӡ
	*/
function PerGetPrint(){
		
	var rowNum = CaseGrid.mulLineCount;
	var flag = false;
	var tRowNum = 0;
	
	for(var i=0;i<rowNum;i++) {
		
		if(CaseGrid.getChkNo(i)) {
			flag = true;
			tRowNum++;
			
		}
	}
	if (!flag) {
		alert("������ѡ��һ�������ⰸ��Ϣ");
		return false;
	} else if (tRowNum>1) {
		alert("ֻ��ѡ��һ���ⰸ���С����˽᰸֪ͨ���ӡ��");
		return false;		
	}

	fm.Operate.value="PERENDPRINT";
	submitForm();
}
/**
	*���˸�������������ӡ
	*/
function PerGetPrint1(){
	
	var rowNum = CaseGrid.mulLineCount;
	var flag = false;
	
	for(var i=0;i<rowNum;i++) {
		
		if(CaseGrid.getChkNo(i)) {
			flag = true;			
		}
	}
	if (!flag) {
		
		alert("������ѡ��һ���¼�������Ϣ");
		return false;
	}

	fm.Operate.value="PERENDPRINT";
	submitForm();
}
/**
	*�����ܷ⺯��ӡ
	*/
function PerSealPrint(){
	
	var rowNum = CaseGrid.mulLineCount;
	var flag = false;
	var tRowNum = 0;
	
	for(var i=0;i<rowNum;i++) {
		
		if(CaseGrid.getChkNo(i)) {
			flag = true;
			tRowNum++;
			
		}
	}
	if (!flag) {
		alert("������ѡ��һ���¼�������Ϣ");
		return false;
	} else if (tRowNum>1) {
		alert("ֻ��ѡ��һ���ⰸ���С������ܷ⺯��ӡ��");
		return false;		
	}
	fm.Operate.value="PERSEALPRINT";
	submitForm();	
}
/**
	*�����ܷ⺯������ӡ
	*/
function AllSealPrint(){
	
	var rowNum = CaseGrid.mulLineCount;
	var flag = false;
	
	for(var i=0;i<rowNum;i++) {
		
		if(CaseGrid.getChkNo(i)) {
			flag = true;
		}
	}
	if (!flag) {
		alert("������ѡ��һ���¼�������Ϣ");
		return false;
	}
	fm.Operate.value="PERSEALPRINT";
	submitForm();
	
}