<%
/***************************************************************
 * <p>ProName��LCContPlanInit.jsp</p>
 * <p>Title��Ͷ��������Ϣ¼��</p>
 * <p>Description��Ͷ��������Ϣ¼��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-03-26
 ****************************************************************/
%>
<script language="JavaScript">

/**
 * ��ʼ������
 */
function initForm() {
	
	try {
		initRelaFlag();
		showManageCom();
		initParam();
		initInpBox();
		initButton();
		//initPlanInfoGrid();
		showPageInfo();
	
	} catch (re) {
		alert("��ʼ���������");
	}
}

/**
 * ��ʼ������
 */
function initParam() {
	
	try {
		fmrela.GrpPropNo.value=tGrpPropNo;
		tContPlanType = getContPlanType(tGrpPropNo);
		var prttype = tGrpPropNo.substr(3,1);
		if("RELA"==fmPub.RelaFlag.value){
			if(tQueryFlag=="null" || tQueryFlag==""){
				tQueryFlag = "4";
			}
		}
		if("1"==prttype){
			if(tQueryFlag=="null" || tQueryFlag==""){
				tQueryFlag = "2";
			}
		}else if("2"==prttype){
			if(tQueryFlag=="null" || tQueryFlag==""){
				tQueryFlag = "3";
			}
		}
		
	} catch (re) {
		alert("��ʼ����������");
	}
}

/**
 * ��ʼ��¼��ؼ�
 */
function initInpBox() {
	
	try {
	
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCContPlanTradSql");
		tSQLInfo.setSqlId("LCContPlanTradSql25");
		
		if(tPrtNo=="null"||tPrtNo==''){
			tSQLInfo.addSubPara(tGrpPropNo);
		}else{
			tSQLInfo.addSubPara(tPrtNo);
		}
		
		var tArrre = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if(tArrre==null||tArrre==''){
			fmrela.RequestButton.disabled=true;
			fmrela.GrpSpecButton.disabled=true;
		}else{			
			fmrela.OfferListNo.value=tArrre[0][0];
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_app.LCContPlanTradSql");
			tSQLInfo.setSqlId("LCContPlanTradSql26");
			tSQLInfo.addSubPara(fmrela.OfferListNo.value);
			var tArr1 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if(tArr1==0){
				fmrela.RequestButton.disabled=true;
			}else{
				fmrela.RequestButton.disabled=false;
			}
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_app.LCContPlanTradSql");
			tSQLInfo.setSqlId("LCContPlanTradSql27");
			tSQLInfo.addSubPara(fmrela.OfferListNo.value);
			var tArr2 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if(tArr2==0){
				fmrela.GrpSpecButton.disabled=true;
			}else{
				fmrela.GrpSpecButton.disabled=false;
			}
		}
		
		
	} catch (ex) {
		alert("��ʼ��¼��ؼ�����");
	}
}

/**
 * ��ʼ����ť
 */
function initButton() {
	
	try {
		divPeriod.style.display = "none";//�������
		divRela.style.display = "none";//����
		divEnginSaveButton.style.display = "none";//������
		divAddPlanButton.style.display = "none";//����
		divInfo5.style.display = "none";//����ά��
		divInfo4.style.display = "none";//¼�����
		divInfoClose.style.display = "";//�ر�
		fmrela.OfferListNo.className="readonly";
		
		if(tQueryFlag=="2"){
			divPeriod.style.display = "none";//�������
			divRela.style.display = "none";//����
			
			divEnginSaveButton.style.display = "none";//������
			divAddPlanButton.style.display = "none";//����
			if("02"==tContPlanType){
				divInfo5.style.display = "";//����ά��
			}
			divInfo4.style.display = "";//¼�����
			divInfoClose.style.display = "none";//�ر�
		}else if(tQueryFlag=="3"){
			if(tActivityID=='1800201002'){
				fmrela.queryOffLis.style.display='';
			}
			divPeriod.style.display = "";//�������
			divRela.style.display = "";//����
			fmrela.OfferListNo.className="common";
			if("01"==tContPlanType){
				divEnginSaveButton.style.display = "";//������
			}
			divAddPlanButton.style.display = "";//����
			if("02"==tContPlanType){
				divInfo5.style.display = "";//����ά��
			}
			divInfo4.style.display = "";//¼�����
			divInfoClose.style.display = "none";//�ر�
		}else if(tQueryFlag=="4"){
			divPeriod.style.display = "";//�������
			divRela.style.display = "";//����
			fmrela.OfferListNo.className="common";
			if("01"==tContPlanType){
				divEnginSaveButton.style.display = "none";//������
			}
			divAddPlanButton.style.display = "none";//����
			if("02"==tContPlanType){
				divInfo5.style.display = "";//����ά��
			}
			divInfo4.style.display = "";//¼�����
			divInfoClose.style.display = "none";//�ر�
		}
		
	} catch (ex) {
		alert("��ʼ����ť����");
	}
}

/**
 * ��null���ַ���תΪ��
 */
function nullToEmpty(string) {
	
	if ((string=="null")||(string=="undefined")) {
		string = "";
	}
	
	return string;
}

/**
 * ������Ϣ
 */
function initPlanInfoGrid() {

	turnPage1 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ϵͳ��������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������ͱ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "������ʶ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "������ʶ";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���Ѽ��㷽ʽ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���Ѽ��㷽ʽ";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;

		iArray[i] = new Array();
		iArray[i][0] = "ְҵ�����";//OccupTypeFlag
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ְҵ�����";//OccupTypeFlagName
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ְҵ������";
		if (tContPlanType=='00' || tContPlanType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "40px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tContPlanType=='00' || tContPlanType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}

		iArray[i] = new Array();
		iArray[i][0] = "ְҵ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ְҵ�з������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ְҵ�з���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���ֱ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������(��)";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������(��)";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ƽ������(��)";
		if (tContPlanType=='00' || tContPlanType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "40px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tContPlanType=='00' || tContPlanType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "����";
		if (tContPlanType=='00' || tContPlanType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "40px";
		} else {//������Ҳ����չʾ
			iArray[i][1] = "20px";
		}
		iArray[i][2] = 300;
		if (tContPlanType=='00' || tContPlanType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 0;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "�μ��籣ռ��";
		if (tContPlanType=='00' || tContPlanType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "40px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tContPlanType=='00' || tContPlanType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "��Ů����";
		if (tContPlanType=='00' || tContPlanType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "40px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tContPlanType=='00' || tContPlanType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "����ռ��";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���ѷ�̯��ʽ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���ѷ�̯��ʽ";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ҵ����ռ��";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����н(Ԫ)";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����н(Ԫ)";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ƽ����н(Ԫ)";
		if (tContPlanType=='00' || tContPlanType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "40px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tContPlanType=='00' || tContPlanType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "����˵��";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ְҵ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		PlanInfoGrid = new MulLineEnter("fm2", "PlanInfoGrid");
		PlanInfoGrid.mulLineCount = 0;
		PlanInfoGrid.displayTitle = 1;
		PlanInfoGrid.locked = 1;
		PlanInfoGrid.canSel = 1;
		PlanInfoGrid.canChk = 0;
		PlanInfoGrid.hiddenSubtraction = 1;
		PlanInfoGrid.hiddenPlus = 1;
		PlanInfoGrid.selBoxEventFuncName = "showPlanInfo";
		PlanInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
</script>
