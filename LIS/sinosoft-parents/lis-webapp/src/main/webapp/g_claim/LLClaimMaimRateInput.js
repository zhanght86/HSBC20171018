/***************************************************************
 * <p>ProName��LLClaimMaimRateInput.js</p>
 * <p>Title���˲б���ά��</p>
 * <p>Description���˲б���ά��</p>
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
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

function QueryDefoInfo(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.QueryDefoInfoSql");
	tSQLInfo.setSqlId("DefoInfoSql1");
	tSQLInfo.addSubPara(fm.DefoTypeCodeQ.value);
	tSQLInfo.addSubPara(fm.DefoClassQ.value);
	tSQLInfo.addSubPara(fm.DefoClassNameQ.value);
	tSQLInfo.addSubPara(fm.DefoGradeQ.value);
	tSQLInfo.addSubPara(fm.DefoGradeNameQ.value);
	tSQLInfo.addSubPara(fm.DefoCodeQ.value);
	tSQLInfo.addSubPara(fm.DefoCodeNameQ.value);
	
	turnPage2.queryModal(tSQLInfo.getString(), DefoGrid,"2");
	if (!turnPage2.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}
/**�����������**/
function showDefoInfo(){
	
	 var i = DefoGrid.getSelNo();
   if(i < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return;
     }
	  i = DefoGrid.getSelNo()-1;
    fm.DefoTypeCode.value=DefoGrid.getRowColData(i,1);
    fm.DefoTypeName.value=DefoGrid.getRowColData(i,2);
    fm.DefoClass.value=DefoGrid.getRowColData(i,3);
    fm.DefoClassName.value=DefoGrid.getRowColData(i,4);
		fm.DefoGrade.value=DefoGrid.getRowColData(i,5);
		fm.DefoGradeName.value=DefoGrid.getRowColData(i,6);
		fm.DefoCode.value=DefoGrid.getRowColData(i,7);
		fm.DefoName.value=DefoGrid.getRowColData(i,8);
		fm.DefoRate.value=DefoGrid.getRowColData(i,9);
		fm.tDefoType.value=DefoGrid.getRowColData(i,1);
		fm.tDefoCode.value=DefoGrid.getRowColData(i,7);
		fm.DInsert.disabled=true;
		fm.DModify.disabled=false;
		fm.DDelete.disabled=false;
		
		if(fm.DefoTypeCode.value=="3"){
			
			document.getElementById("DefoClass1").style.display="";
			document.getElementById("DefoClass2").style.display="";
			document.getElementById("DefoClassName1").style.display="";
			document.getElementById("DefoClassName2").style.display="";						
			
		}else{
			
			document.getElementById("DefoClass1").style.display="none";
			document.getElementById("DefoClass2").style.display="none";
			document.getElementById("DefoClassName1").style.display="none";
			document.getElementById("DefoClassName2").style.display="none";				
		}
		
}
/**�� ��**/
function DefoTurnBack(){
	
	  fm.DefoTypeCode.value="";
    fm.DefoTypeName.value="";
    fm.DefoClass.value="";
    fm.DefoClassName.value="";
		fm.DefoGrade.value="";
		fm.DefoGradeName.value="";
		fm.DefoCode.value="";
		fm.DefoName.value="";
		fm.DefoRate.value="";
		fm.tDefoType.value="";
		fm.tDefoCode.value="";
		fm.Operate.value="";
		fm.DInsert.disabled=false;
		fm.DModify.disabled=true;
		fm.DDelete.disabled=true;
		
		
		document.getElementById("DefoClass1").style.display="none";
		document.getElementById("DefoClass2").style.display="none";
		document.getElementById("DefoClassName1").style.display="none";
		document.getElementById("DefoClassName2").style.display="none";			
}
/**�� ��**/
function DefoInsert(){
	if(trim(fm.DefoTypeCode.value)==""){
		
		alert("�˲����Ͳ���Ϊ��");
		return false;
	}
	
	if(fm.DefoType.value=="3"){
		if(trim(fm.DefoClass.value)==""){
			
			alert("���˲�����Ϊ���˲еȼ�(10��281��)��ʱ���˲з��಻��Ϊ��");
			return false;
		}
		
		if(trim(fm.DefoClassName.value)==""){
			
			alert("���˲�����Ϊ���˲еȼ�(10��281��)��ʱ���˲з������Ʋ���Ϊ��");	
			return false;
		}
	}
	
	if(trim(fm.DefoGrade.value=="")){
		
			alert("�˲м�����Ϊ��");
			return false;
		
	}
	
	if(trim(fm.DefoGradeName.value=="")){
			alert("�˲м������Ʋ���Ϊ��");
			return false;
			
	}
	
	if(trim(fm.DefoCode.value=="")){
		alert("�˲д��벻��Ϊ��");
		return false;
	}
	
	if(trim(fm.DefoName.value=="")){
		alert("�˲д������Ʋ���Ϊ��");
		return false;
	}
	
	if(trim(fm.DefoRate.value=="")){
		alert("�˲б�������Ϊ��");
		return false;
	}
	
	fm.Operate.value="INSERT";
	fm.action="./LLClaimMaimRateSave.jsp";
	submitForm();
}

/**�� ��**/
function DefoModify(){
	
	if(trim(fm.DefoTypeCode.value)==""){
		
		alert("�˲����Ͳ���Ϊ��");
		return false;
	}
	
	if(fm.DefoType.value=="3"){
		if(trim(fm.DefoClass.value)==""){
			
			alert("���˲�����Ϊ���˲еȼ�(10��281��)��ʱ���˲з��಻��Ϊ��");
			return false;
		}
		
		if(trim(fm.DefoClassName.value)==""){
			
			alert("���˲�����Ϊ���˲еȼ�(10��281��)��ʱ���˲з������Ʋ���Ϊ��");	
			return false;
		}
	}
	
	if(trim(fm.DefoGrade.value=="")){
		
			alert("�˲м�����Ϊ��");
			return false;
		
	}
	
	if(trim(fm.DefoGradeName.value=="")){
			alert("�˲м������Ʋ���Ϊ��");
			return false;
			
	}
	
	if(trim(fm.DefoCode.value=="")){
		alert("�˲д��벻��Ϊ��");
		return false;
	}
	
	if(trim(fm.DefoName.value=="")){
		alert("�˲д������Ʋ���Ϊ��");
		return false;
	}
	
	if(trim(fm.DefoRate.value=="")){
		alert("�˲б�������Ϊ��");
		return false;
	}
	
	fm.Operate.value="UPDATE";
	fm.action="./LLClaimMaimRateSave.jsp";
	submitForm();
}
function DefoDelete(){
	
	if (confirm("��ȷʵ��ɾ���ü�¼��?")){	
		
		fm.Operate.value="DELETE";
		fm.action="./LLClaimMaimRateSave.jsp";
		submitForm();
    }else{
			fm.Operate.value="";
			return false;
    }
	
}
/**
 * �ύ����
 */
function submitForm() {
	
	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	}	
	DefoTurnBack();
	initDefoGrid();
}
/**��������**/
function afterCodeSelect( cCodeName, Field ) {
	if(cCodeName=="defotype") {
		if(fm.DefoTypeCode.value=="3") {
			
			document.getElementById("DefoClass1").style.display="";
			document.getElementById("DefoClass2").style.display="";
			document.getElementById("DefoClassName1").style.display="";
			document.getElementById("DefoClassName2").style.display="";			

		}else {
			document.getElementById("DefoClass1").style.display="none";
			document.getElementById("DefoClass2").style.display="none";
			document.getElementById("DefoClassName1").style.display="none";
			document.getElementById("DefoClassName2").style.display="none";			
		}
	}
}
//���ݵ���
function exportData() {
	
	if (!confirm("ȷ��Ҫ�������ݣ�")) {
		return false;
	}
	
	//�������
	var tTitle = "�˲����ͱ���^|�˲�����^|�˲з���^|�˲з�������^|�˲м���^|�˲м�������^|"
							+"�˲д���^|�˲д�������^|�˲и�������";
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.QueryDefoInfoSql");
	tSQLInfo.setSqlId("DefoInfoSql1");
	tSQLInfo.addSubPara(fm.DefoTypeCodeQ.value);
	tSQLInfo.addSubPara(fm.DefoClassQ.value);
	tSQLInfo.addSubPara(fm.DefoClassNameQ.value);
	tSQLInfo.addSubPara(fm.DefoGradeQ.value);
	tSQLInfo.addSubPara(fm.DefoGradeNameQ.value);
	tSQLInfo.addSubPara(fm.DefoCodeQ.value);
	tSQLInfo.addSubPara(fm.DefoCodeNameQ.value);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}
