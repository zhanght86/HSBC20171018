//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var mDebug="0";
var mOperate="";
var showInfo;
var arrDataSet;
var turnPage = new turnPageClass();
var QueryResult="";
var QueryCount = 0;
var mulLineCount = 0;
var QueryWhere="";
window.onfocus=myonfocus;

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow){
	if (cShow=="true"){
		cDiv.style.display="";
	}
	else{
		cDiv.style.display="none";
	}
}

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug){
	if(cDebug=="1"){
		parent.fraMain.rows = "0,0,0,0,*";
	}
	else {
		parent.fraMain.rows = "0,0,0,82,*";
	}
	parent.fraMain.rows = "0,0,0,0,*";
}

//�����ύ�����棩
function submitForm(){
	//alert("submitForm start");
	AscriptionRuleNewGrid.delBlankLine("AscriptionRuleGrid");
	//alert(AscriptionRuleNewGrid.mulLineCount);
	if(AscriptionRuleNewGrid.mulLineCount==0){
		alert("û��������Ϣ��");
		return false;
	}

	//����У��
	if(fm.AscriptionRuleCode.value=="")
	{
		alert("����������������ƣ�");
		return false;
	}
	if(fm.AscriptionRuleName.value=="")
	{
		alert("������˵����");
		return false;
	}
	document.all('mOperate').value = "INSERT||MAIN";
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit(); //�ύ
	//initAscriptionRuleGrid();
}

//������һ��
function returnparent(){
	window.location.href = "GrpFeeInput.jsp?ProposalGrpContNo="+GrpContNo+"&LoadFlag="+LoadFlag;
}

//�����ύ��ɾ����
function DelContClick(){
	AscriptionRuleNewGrid.delBlankLine("AscriptionRuleGrid");
//	alert(AscriptionRuleNewGrid.mulLineCount);
	if(AscriptionRuleNewGrid.mulLineCount==0){
		alert("û��������Ϣ��");
		return false;
	}

	document.all('mOperate').value = "DELETE||MAIN";
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit(); //�ύ
}

//����У��
function beforeSubmit(){
	return true;
}

function GrpPerPolDefine(){
	
		//alert("ok");

	var tGrpContNo = fm.GrpContNo.value;
	//if(tGrpContNo.substring(0,2)=="28")
	//{
	//	alert("�Ѿ��б��ĵ����µĹ�������ֻ������ȫ�����");
	//	return;
	//}
	var strSQL = "select a.RiskCode,b.RiskName,a.GrpPolNo from LCGrpPol a,LMRisk b where a.GrpContNo='"+tGrpContNo+"' and a.RiskCode = b.RiskCode";
	var tImpAscriptionRuleCode  = easyQueryVer3(strSQL, 1, 0, 1);
	
    
	
	initAscriptionRuleNewGrid(tImpAscriptionRuleCode);
  queryrelate();    
	divAscriptionRule.style.display= "";
	

}


function GrpPerPolDefineOld(){
	// ��ʼ�����
	var str = "";
	var tGrpContNo = GrpContNo;
	var tImpAscriptionRuleOldCode = initAscriptionRuleOld(tGrpContNo);
	//alert("is me");
	initAscriptionRuleOldGrid(tImpAscriptionRuleOldCode);
	//alert("not here");

	divAscriptionRuleOld.style.display= "";
	var strSQL = "";


	strSQL = "select distinct Ascriptionrulecode,Ascriptionrulename from lcAscriptionrulefactory a where a.GrpContNo='"+tGrpContNo+"'" 
	turnPage = new turnPageClass();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	if (turnPage.strQueryResult) {
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		turnPage.pageDisplayGrid = AscriptionRuleOldGrid;
		turnPage.strQuerySql     = strSQL;
		turnPage.pageIndex = 0;
		arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	}
        return ;
}


function initAscriptionRuleNew(tGrpContNo){
	// ��дSQL���
	
}
function initAscriptionRuleOld(tGrpContNo){
	// ��дSQL���
	var k=0;
	var strSQL = "";

	strSQL = "select Ascriptionrulecode,Ascriptionrulename from lcAscriptionrulefactory where lcAscriptionrulefactory.GrpContNo='tGrpContNo'";
	var str  = easyQueryVer3(strSQL, 1, 0, 1);
	//alert(str);
    return str;
}
//��ѡ���������¼�
function ShowAscriptionRule(parm1,parm2){
		//��ǰ�е�1�е�ֵ��Ϊ��ѡ��
		//alert("not me");
		var tGrpContNo= fm.GrpContNo.value;
		//alert(GrpContNo);
		var cAscriptionRuleCode = document.all(parm1).all('AscriptionRuleOldGrid1').value;	//�ƻ�����
		document.all('AscriptionRuleCode').value = cAscriptionRuleCode;
		document.all('AscriptionRuleName').value = document.all(parm1).all('AscriptionRuleOldGrid2').value;
		//alert("cAscriptionRuleCode");
               var strSQL="";
        /*       
               strSQL="select AscriptionRulecode,AscriptionRulename,Calremark,params from lcAscriptionrulefactory where Ascriptionrulecode='"+cAscriptionRuleCode+"' and lcAscriptionrulefactory.GrpContNo='"+tGrpContNo+"'";
      
            //   document.write(strSQL);
               	turnPage = new turnPageClass();
	        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//alert("not me");

	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
	         // alert("result is right");
		//��ѯ�ɹ������ַ��������ض�ά����
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//���ó�ʼ������MULTILINE����
		turnPage.pageDisplayGrid = AscriptionRuleGrid;
		//����SQL���
		turnPage.strQuerySql     = strSQL;
		//���ò�ѯ��ʼλ��
		turnPage.pageIndex = 0;
		//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
		arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
		//����MULTILINE������ʾ��ѯ���
		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
		QueryCount = 1;
		tSearch = 1;
	*/
	strSQL="select riskcode,ascripttype,factorytype,otherno,factorycode||to_char(factorysubcode),calremark,params,factoryname,trim(FactoryType)||trim(RiskCode),grppolno from lcascriptionrulefactory "
		+ "where grpcontno='"+document.all('GrpContNo').value+"' "
		//+ "and riskcode='"+cRiskCode+"' "
		+ "and ascriptionrulecode='"+cAscriptionRuleCode+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	if (turnPage.strQueryResult) {
	         // alert("result is right");
		//��ѯ�ɹ������ַ��������ض�ά����
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//���ó�ʼ������MULTILINE����
		turnPage.pageDisplayGrid = AscriptionRuleNewGrid;
		//����SQL���
		turnPage.strQuerySql     = strSQL;
		//���ò�ѯ��ʼλ��
		turnPage.pageIndex = 0;
		//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
		arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
		//����MULTILINE������ʾ��ѯ���
		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
		QueryCount = 1;
		tSearch = 1;
	}
}
function afterSubmit(FlagStr,content){
	showInfo.close();
	if( FlagStr == "Fail" ){
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	else{
		content = "�����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		initForm();
		document.all('AscriptionRuleCode').value="";
    document.all('AscriptionRuleName').value="";
	}
}

function updateClick(){
	AscriptionRuleNewGrid.delBlankLine("AscriptionRuleGrid");
	if(AscriptionRuleNewGrid.mulLineCount==0){
		alert("û��������Ϣ��");
		return false;
	}

	if (!beforeSubmit()){
		return false;
	}
	document.all('mOperate').value = "UPDATE||MAIN";
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit(); //�ύ
	initAscriptionRuleGrid();
}


function queryrelate()
{
	var strSQL = "select distinct Ascriptionrulecode,Ascriptionrulename from lcAscriptionrulefactory a where a.GrpContNo='"+fm.GrpContNo.value+"'" 
	turnPage.queryModal(strSQL, AscriptionRuleOldGrid);
}


function ChangePlan(){
	initAscriptionRuleNewGrid();
}