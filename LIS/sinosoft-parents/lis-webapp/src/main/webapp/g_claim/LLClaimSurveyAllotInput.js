/***************************************************************
 * <p>PreName��LLClaimSurveyAllotInput.js</p>
 * <p>Title�������������</p>
 * <p>Description�������������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-02-21
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var str ="";
var str1="";
var str2=" 1 and comcode like #"+tManageCom+"%# ";

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
		//showDialogWindow(urlStr, 1);
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showDialogWindow(urlStr, 1);
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
	}	
	initForm();
}


function SurveyTaskGridSelClick1(){
	
	var i = SurveyTask1Grid.getSelNo();
   if(i < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return false;
     }
	  i = SurveyTask1Grid.getSelNo()-1;
    fm.InqNo.value=SurveyTask1Grid.getRowColData(i,1);
    fm.RgtNo.value=SurveyTask1Grid.getRowColData(i,2);
    fm.AcceptMngCom.value=SurveyTask1Grid.getRowColData(i,10);
    fm.InqCom.value= SurveyTask1Grid.getRowColData(i,11);
		str = "1 and comcode != #"+fm.InqCom.value.substring(0,4)+"#" +" and char_length(comcode)=#4# " ;
		divInqPer2.style.display = '';
	
}
function SurveyTaskGridSelClick(){
	
	var i = SurveyTaskGrid.getSelNo();
   if(i < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return false;
     }
	  i = SurveyTaskGrid.getSelNo()-1;
    fm.InqNo.value=SurveyTaskGrid.getRowColData(i,1);
    fm.RgtNo.value=SurveyTaskGrid.getRowColData(i,2);
    fm.AcceptMngCom.value=SurveyTaskGrid.getRowColData(i,10);
    fm.InqCom.value= SurveyTaskGrid.getRowColData(i,12);
		str1="1 and a.SurveyFlag=#1# and exists (select 1 from ldusertocom c where c.managecom like #"+fm.InqCom.value.substring(0,4)+"%# and c.usercode=b.usercode) ";
		
		divInqPer.style.display = '';
	
}
/**�鿴����Ȩ��**/
function limitSurveyDept(){
	
	if(tManageCom.length>4) {
		alert("��������û�е�������Ȩ�ޣ�") ;
		fm.SurveyDeptQuery.disabled=true;
	}
	
}
function SurveyTaskQueryClick(){
	
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSurveyAllotSql");
	tSQLInfo.setSqlId("LLClaimSurveyAllotSql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.acceptNo.value);
	tSQLInfo.addSubPara(fm.appntName.value);
	tSQLInfo.addSubPara(fm.customerName.value);
	tSQLInfo.addSubPara(fm.idNo.value);
	tSQLInfo.addSubPara(fm.inqType.value);
	tSQLInfo.addSubPara(fm.applyStartdate.value);
	tSQLInfo.addSubPara(fm.applyEndDate.value);
	tSQLInfo.addSubPara(fm.initdept.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), SurveyTask1Grid);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
	
	
}
function querytoTaskInfo(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSurveyAllotSql");
	tSQLInfo.setSqlId("LLClaimSurveyAllotSql2");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.acceptNo1.value);
	tSQLInfo.addSubPara(fm.appntName1.value);
	tSQLInfo.addSubPara(fm.customerName1.value);
	tSQLInfo.addSubPara(fm.idNo1.value);
	tSQLInfo.addSubPara(fm.inqType1.value);
	tSQLInfo.addSubPara(fm.applyStartdate1.value);
	tSQLInfo.addSubPara(fm.applyEndDate1.value);
	tSQLInfo.addSubPara(fm.IsLoc.value);
	tSQLInfo.addSubPara(fm.Initdept1.value);
	tSQLInfo.addSubPara(fm.Initdept2.value);
	
	turnPage2.queryModal(tSQLInfo.getString(), SurveyTaskGrid);
	if (!turnPage2.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
	
}
//�������
function Designate(){
	
	var i = SurveyTask1Grid.getSelNo();
   if(i < 1) {
        alert("��ѡ��һ�м�¼��");
        return false;
     }
	if(fm.InqDept.value==""){
		alert("ָ�������������Ϊ��");
		return false;
	}
	fm.Operate.value="CHMNGINQ";
	submitForm();
	
}
//��������
function Designate1(){
	var i = SurveyTaskGrid.getSelNo();
   if(i < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return false;
     }
		if(fm.InqPer.value==""){
			alert("ָ�������˲���Ϊ��");
			return false;
		}
		fm.Operate.value="CHTASKINQ";
		submitForm();
}