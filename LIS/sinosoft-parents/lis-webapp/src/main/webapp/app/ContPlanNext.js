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
	ImpartGrid.delBlankLine("ImpartGrid");
	if(ImpartGrid.mulLineCount==0){
		alert("û��������Ϣ��");
		return false;
	}

	if (!beforeSubmit()){
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
}

//������һ��
function returnparent(){
	parent.close();
}

//�����ύ��ɾ����
function DelContClick(){
	ImpartGrid.delBlankLine("ImpartGrid");
//	alert(ImpartGrid.mulLineCount);
	if(ImpartGrid.mulLineCount==0){
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
	// ��ʼ�����
	var str = "";
	var tGrpContNo = GrpContNo;

	var tImpContPlanCode = initImpContPlanCode(tGrpContNo);
	//��ʼ������
	initImpartGrid(tImpContPlanCode);

	divLCImpart1.style.display= "";

//	var strSQL = "select ContPlanCode,RiskCode,FactoryType,OtherNo,concat(FactoryCode,to_char(trim(cast (FactorySubCode as char(20))))),CalRemark,Params," +
//			"concat(trim(FactoryType),trim(RiskCode)),concat(trim(ContPlanCode),GrpContNo),ContPlanName,RiskVersion,FactoryName,MainRiskCode," +
//			"MainRiskVersion " 
//		+ "from LCContPlanFactory where 1=1 "
//		+ "and GrpContNo='" +tGrpContNo+ "' "
//		+ "order by ContPlanCode,FactoryType, OtherNo,FactoryCode,FactorySubCode ";
	
	var sqlid0="ContPlanNextSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("app.ContPlanNextSql"); //ָ��ʹ�õ�properties�ļ���
	mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
	mySql0.addSubPara(tGrpContNo);//ָ������Ĳ���
	var strSQL=mySql0.getString();
	
	turnPage = new turnPageClass();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
		//��ѯ�ɹ������ַ��������ض�ά����
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//���ó�ʼ������MULTILINE����
		turnPage.pageDisplayGrid = ImpartGrid;
		//����SQL���
		turnPage.strQuerySql     = strSQL;
		//���ò�ѯ��ʼλ��
		turnPage.pageIndex = 0;
		//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
		arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
		//����MULTILINE������ʾ��ѯ���
		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	}

//	strSQL = "select GrpContNo,ProposalGrpContNo,ManageCom,AppntNo,GrpName from LCGrpCont where GrpContNo = '" +GrpContNo+ "'";
	
	var sqlid1="ContPlanNextSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.ContPlanNextSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(GrpContNo);//ָ������Ĳ���
	strSQL=mySql1.getString();
	
	turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	document.all('GrpContNo').value = turnPage.arrDataCacheSet[0][0];
	document.all('ProposalGrpContNo').value = turnPage.arrDataCacheSet[0][1];
	document.all('ManageCom').value = turnPage.arrDataCacheSet[0][2];
	document.all('AppntNo').value = turnPage.arrDataCacheSet[0][3];
	document.all('GrpName').value = turnPage.arrDataCacheSet[0][4];
	return ;
}

function initImpContPlanCode(tGrpContNo){
	// ��дSQL���
	var k=0;
	var strSQL = "";

	/*strSQL = "select distinct a.FactoryType,b.FactoryTypeName,a.FactoryType||"+tGrpContNo+" from LMFactoryMode a ,LMFactoryType b  where 1=1 "
	+ " and a.FactoryType= b.FactoryType "
	+ " and (RiskCode =('"+tGrpContNo+"' ) or RiskCode ='000000' )";
	*/
	//alert(GrpContNo);
	strSQL = "select distinct a.ContPlanCode,a.ContPlanName, concat(trim(a.ContPlanCode),'"+GrpContNo+"') from LCContPlan a where a.GrpContNo='"+GrpContNo+"' and a.PlanType='0'";
	
	var sqlid2="ContPlanNextSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("app.ContPlanNextSql"); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(GrpContNo);//ָ������Ĳ���
	mySql2.addSubPara(GrpContNo);//ָ������Ĳ���
	strSQL=mySql2.getString();
	
	//document.all('ff').value=strSQL;
	var str  = easyQueryVer3(strSQL, 1, 0, 1);
	//alert(str);
    return str;
}

function afterSubmit(FlagStr,content){
	showInfo.close();
	if( FlagStr == "Fail" ){
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
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
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		initForm();
	}
}