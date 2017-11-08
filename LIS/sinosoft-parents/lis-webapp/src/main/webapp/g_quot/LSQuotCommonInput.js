/***************************************************************
 * <p>ProName��LSQuotCommonInput.js</p>
 * <p>Title��ѯ�۽׶ι�����</p>
 * <p>Description��ѯ�۽׶ι�����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
var tETQuotType = "00";
var tProjQuotType = "01";
var tActivityType = "8001";
var tPlanShowRows = 10;
var tNewColor = "#FCE6C9";
var tDifColor = "#FF0000";

function setTab(m,n){
	var menu=document.getElementById("tab"+m).getElementsByTagName("li");

	for(i=0;i<2;i++)
	{
	   menu[i].className=i==n?"now":""; 
		if (i==n) {
			document.getElementById("tablistdiv"+i).style.display = "block";
		} else {
			document.getElementById("tablistdiv"+i).style.display = "none";
		}
	}
	
	initForm();
}

function setTabOver(m,n){
	var menu=document.getElementById("tab"+m).getElementsByTagName("li");
	menu[n].className="lobutton";
}
	
function setTabOut(m,n){
	var menu=document.getElementById("tab"+m).getElementsByTagName("li");
	menu[n].className="libutton";
}

/**
 * �Զ���codename��ֵ
 */
function auotQuotShowCodeName(cCodeType, cCode, cObj, cCodeName) {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql9");
	tSQLInfo.addSubPara(cCodeType);
	tSQLInfo.addSubPara(cCode);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null) {

	} else {
		
		document.all(cCodeName).value = tArr[0][0];
	}
}

/**
 * ����ҳ����
 */
function noDiv(objPage, objGrid, tSql) {
	
	//Ϊ����������ĳ����ݴ������
	objPage = new turnPageClass();
	objPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1, 0, 1);
	//�ж��Ƿ��ѯ�ɹ�
	if (!objPage.strQueryResult) {
		//���MULTILINE��ʹ�÷�����MULTILINEʹ��˵��
		objGrid.clearData();
		return false;
	}
	
	//�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
	objPage.arrDataCacheSet = clearArrayElements(objPage.arrDataCacheSet);
	//��ջ���
	objPage.allArrDataCacheSet = clearArrayElements(objPage.allArrDataCacheSet);
	objPage.allCacheSize = 0;
	//��ѯ�ɹ������ַ��������ض�ά����
	objPage.arrDataCacheSet = decodeEasyQueryResult(objPage.strQueryResult, 0, 0, objPage);
	objPage.pageLineNum = objPage.queryAllRecordCount;
	var tKey = 1;
	//cTurnPage.allCacheSize ++;
	objPage.allArrDataCacheSet[objPage.allCacheSize%objPage.allArrCacheSize] = {id:tKey,value:objPage.arrDataCacheSet};
	//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	objPage.pageDisplayGrid = objGrid;
	//����SQL���
	objPage.strQuerySql = tSql;
	//���ò�ѯ��ʼλ��
	objPage.pageIndex = 0;
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	var arrDataSet = objPage.getData(objPage.arrDataCacheSet, objPage.pageIndex, objPage.pageLineNum);
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(arrDataSet, objPage.pageDisplayGrid, objPage);
	if (objPage.showTurnPageDiv==1) {
		try {
			objGrid.setPageMark(objPage);
		} catch(ex){}
	}
	
	return true;
}

/**
 * ��ת��Ŀ�경��,oĿ�경��
 */
function goToStep(o) {
	
	var tPath = "?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	
	if (o=="1") {//ȥ����һ��ʱ,��������ת,���ж�ѯ�����ͼ���
		
		if (tQuotType==tETQuotType) {
			location.href = "./LSQuotETBasicInput.jsp"+ tPath;
		} else if (tQuotType==tProjQuotType) {
			location.href = "./LSQuotProjBasicInput.jsp"+ tPath;
		}
	} else if (o=="2" || o=="3") {//ֻҪ��һ��������¼��,������2,3��������ת,��ֻҪ��Ʒ����
		
		//��ѯ�Ƿ�Ϊ��һ����Ϣ��¼��
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql7");
		tSQLInfo.addSubPara(tMissionID);
		tSQLInfo.addSubPara(tSubMissionID);
		tSQLInfo.addSubPara(tActivityID);
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tQuotType);
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null) {//δ��ѯ����¼
			alert("��ȡ��ѯ����Ϣʧ�ܣ�");
			return false;
		}
		
		if (tArr[0][0]=="0") {
			alert("��ѯ�ۻ�����Ϣδ¼�룡");
			return false;
		}
		
		if (o=="2") {
			
			if (tQuotType==tETQuotType) {
				location.href = "./LSQuotETPlanInput.jsp"+ tPath;
			} else if (tQuotType==tProjQuotType) {
				location.href = "./LSQuotProjPlanInput.jsp"+ tPath; 
			}
		}
		if (o=="3") {
			
			if (tQuotType==tETQuotType) {
				location.href = "./LSQuotETSubmitInput.jsp"+ tPath;
			} else if (tQuotType==tProjQuotType) {
				location.href = "./LSQuotProjSubmitInput.jsp"+ tPath; 
			}
		}
	}
}

/**
 * ��Ʒ����ά����תҳ o-Ŀ�경��
 */
function goToPordParamStep(o, p) {
	
	var tPath = "?ObjType="+ tObjType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	if (o=="0") {//�����ά��

		location.href = "./LSProdParamInput.jsp" + tPath +"&Flag=0";
	} else if (o=="1") {//��ȫ�˷��㷨ά��
		
		location.href = "./LSEdorRefundCalInput.jsp" + tPath;
	}
}

/**
 * �����ղ�����תҳ
 */
function goToLongRiskStep(o) {
	
	var tPath = "?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	if (o=="0") {//���ʲ���
		location.href = "./LSQuotCountRateMain.jsp" + tPath;
	} else if (o=="1") {//�������
		location.href = "./LSQuotCountProfitMain.jsp" + tPath;
	}
}

/**
 * ��ȡ��Ʒ����
 */
function getProdType(cQuotNo, cQuotBatNo) {
	
	if (tQuotType==tETQuotType) {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql8");
		tSQLInfo.addSubPara(cQuotNo);
		tSQLInfo.addSubPara(cQuotBatNo);
	} else if (tQuotType==tProjQuotType) {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotProjPlanSql");
		tSQLInfo.setSqlId("LSQuotProjPlanSql1");
		tSQLInfo.addSubPara(cQuotNo);
		tSQLInfo.addSubPara(cQuotBatNo);
	}
	
	var tProdArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	if (tProdArr==null) {
	
	} else {
		return tProdArr[0][0];
	}
	
	return null;
}

/**
 * ��ȡ���ѷ�̯��ʽ
 */
function getPremMode(cQuotNo, cQuotBatNo) {
   
	if (tTranProdType=="01") {//�������ޱ��ѷ�̯��ʽ
		
	} else {//���б��ѷ�̯��ʽ��ȡ
		
		if (tQuotType==tETQuotType) {
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_quot.LSQuotSql");
			tSQLInfo.setSqlId("LSQuotSql10");
			tSQLInfo.addSubPara(tQuotNo);
			tSQLInfo.addSubPara(tQuotBatNo);
		
			var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tArr==null) {
				
			} else {
				return tArr[0][0];
			}
		}
	}
	
	return "";
}

/**
 * У����������λ��С��λ����
 */
function checkDecimalFormat(cValue, cLen1, cLen2) {
	
	if (cValue=='' || cValue==null) {//Ϊ��,
		return true;
	}
	
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
 * ҵ������
 */
function showRequest() {
	
	window.open("./LSQuotRequestMain.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&ActivityID="+tActivityID,"ҵ������",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * չʾҵ������
 */
function onlyShowRequest() {
	
	window.open("./LSQuotShowRequestMain.jsp?QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo,"ҵ������",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * ������Ϣ
 */
function showFeeInfo() {
	
	//¼�뷽����ϸ�������¼�������Ϣ
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql21");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null) {
		alert("����¼�뷽����ϸ��Ϣ��");
		return false;
	}
	
	window.open("./LSQuotFeeMain.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&ActivityID="+tActivityID,"������Ϣ",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * ������Ϣ
 */
function showPast() {
	
	window.open("./LSQuotPastMain.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&ActivityID="+tActivityID,"������Ϣ",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * �ʾ����
 */
function showQuesnaire() {
	
	window.open("./LSQuotQuesnaireMain.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&ActivityID="+tActivityID,"�ʾ����",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * ��������
 */
function showAttachment() {
	
	window.open("../g_busicommon/LDAttachmentMain.jsp?OtherNoType=QUOT&OtherNo="+tQuotNo+"&SubOtherNo="+tQuotBatNo+"&UploadNode="+tActivityID,"��������",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * ���������
 */
function goToQuestion() {
	
	window.open("../g_busicommon/LDQuestionMain.jsp?OtherNoType=QUOT&OtherNo="+tQuotNo+"&SubOtherNo="+tQuotBatNo+"&ActivityID="+tActivityID+"&ShowStyle=1","���������",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * �ر�Լ��
 */
function showGrpSpec() {
	
	window.open("./LSQuotGrpSpecMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID,"�ر�Լ��",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * �����������
 */
function showPlanCombi() {
	
	window.open("./LSQuotPlanCombiMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID,"�����������",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * ������չ
 */
function showExpand() {
	
	window.open("./LSQuotExpandMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID,"������չ",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * �˱�����
 */
function showUWRule() {
	
	window.open("./LSQuotUWRuleMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID,"�˱�����",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * ��ϸ�鿴
 */
function showPalnDetailView() {
	
	window.open("./LSQuotDetailViewMain.jsp?QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType,"��ϸ�鿴",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * �����ղ���
 */
function showRateCount() {
	
	window.open("./LSQuotCountRateMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID,"�����ղ���",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * ��������
 */
function showCoinsurance() {
	
	//������ʶΪ �� ʱ������ά��������Ϣ
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql35");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tCoinArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tCoinArr[0][0]=="0") {
		alert("�뽫[�Ƿ񹲱�]ѡΪ\"��\"�ҵ��\"������Ϣ����\"��ť���ٽ��й������ã�");
		return false;
	}
	
	window.open("./LSQuotCoinsuranceMain.jsp?QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID,"��������",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 *	��������Ҫ�ؿ���
 */
function showCMRule() {

	var tCMRuleFlag = "0";
	if(tActivityID=="0800100002" || tActivityID=="0800100003") {
		tCMRuleFlag = "1";
	}
	
	var strUrl="../g_claim/LLCLaimControlMain.jsp?BussType=QUOT&BussNo="+ tQuotNo + "&SubBussNo="+ tQuotBatNo + "&Flag="+ tCMRuleFlag;
	window.open(strUrl,"�������ο���",'width=1600,height=620,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * ��ʼ���Զ��巭ҳ��Ϣ
 * cMark=1ʱ��ֻ��ѯ����������Ϣ
 */
function initPubDetailPageInfo(cQuotNo, cQuotBatNo,cSysPlanCode,cPlanCode,cMark) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
	tSQLInfo.setSqlId("LSQuotAllDetailSql5");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	if (cMark=="1") {
		tSQLInfo.addSubPara(cSysPlanCode);
		tSQLInfo.addSubPara(cPlanCode);
	} else {
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");
	}
	
	strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult==null || strQueryResult=="") {
		RowNum = 0;
		PageNum = 0;
		divTurnPage.style.display = "none";
	} else {
		
		RowNum = strQueryResult.length;
		//������ܵ�ҳ��TotalPage
		if (RowNum%tPlanShowRows==0) {
			PageNum = parseInt(RowNum/tPlanShowRows);
		} else {
			PageNum = parseInt(RowNum/tPlanShowRows)+1;
		}
	}
}

/**
 * ��ʼ�����۷�����ϸ��ҳ��Ϣ
 */
function initQuotPlanDetailPageInfo(cOfferListNo) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
	tSQLInfo.setSqlId("LSQuotAllDetailSql9");
	tSQLInfo.addSubPara(cOfferListNo);
	
	strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	RowNum = strQueryResult.length;
	
	//������ܵ�ҳ��TotalPage
	if (RowNum%tPlanShowRows==0) {
		PageNum = parseInt(RowNum/tPlanShowRows);
	} else {
		PageNum = parseInt(RowNum/tPlanShowRows)+1;
	}
}

/**
 * ��ϸһ��չʾ����
 */
function pubShowAllPlanDetail(cObj, cArr, tStartNum, cQuotType, cTranProdType, cActivityID) {
	
	
	var tNeedCheckFlag = false;
	var tTraceNo = "";
	if (cActivityID=="0800100002" || cActivityID=="0800100003" || cActivityID=="0800100004") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
		tSQLInfo.setSqlId("LSQuotAllDetailSql12");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(cActivityID);
		
		var tArr =  easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr[0][0]==null || tArr[0][0]=="") {
			
			tNeedCheckFlag = false;
		} else {
			
			tNeedCheckFlag = true;
			tTraceNo =  tArr[0][0];
		}
	}
	
	var tInnerHTML1 = "<table class=common><tr class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></tr></table>"
	
	if (cArr==null) {
		return tInnerHTML1;
	}
	
	var tWidthArr = new Array();
	tWidthArr[0] = 30;
	tWidthArr[1] = '';
	tWidthArr[2] = 130;
	tWidthArr[3] = '';
	tWidthArr[4] = 130;
	tWidthArr[5] = 250;
	tWidthArr[6] = '';
	tWidthArr[7] = 80;
	tWidthArr[8] = 70;
	tWidthArr[9] = 70;
	tWidthArr[10] = 60;
	tWidthArr[11] = 80;
	tWidthArr[12] = 60;
	tWidthArr[13] = 80;
	tWidthArr[14] = 60;
	tWidthArr[15] = 80;
	tWidthArr[16] = 60;
	tWidthArr[17] = 80;
	tWidthArr[18] = 80;

	tInnerHTML1 = "";
	for (var i=0; i<tPlanShowRows; i++) {

		if ((tStartNum+i-1)>=RowNum) {
			//���ʱ,��ʾ�ܼ�¼��ȡ��
		} else {
			
			var tSysPlanCode = cArr[tStartNum+i-1][0];
			var tPlanCode = cArr[tStartNum+i-1][1];
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
			tSQLInfo.setSqlId("LSQuotAllDetailSql6");
			tSQLInfo.addSubPara(tQuotNo);
			tSQLInfo.addSubPara(tQuotBatNo);
			tSQLInfo.addSubPara(tSysPlanCode);
			tSQLInfo.addSubPara(tPlanCode);

			var tPlanArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			
			var tPlanNeedCheck = false;
			var tOldPlanArr;
			if (tNeedCheckFlag) {//��ҪУ��
			
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
				tSQLInfo.setSqlId("LSQuotAllDetailSql13");
				tSQLInfo.addSubPara(tQuotNo);
				tSQLInfo.addSubPara(tQuotBatNo);
				tSQLInfo.addSubPara(tSysPlanCode);
				tSQLInfo.addSubPara(tPlanCode);     
				tSQLInfo.addSubPara(tTraceNo);
      	
				tOldPlanArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
				if (tOldPlanArr==null) {
					tPlanNeedCheck = false;
				} else {
					tPlanNeedCheck = true;
				}
			}

			if (tPlanArr!=null) {
			
				var j = 0;
				var tSysPlanCode = tPlanArr[0][j++];
				var tPlanCode = tPlanArr[0][j++];
				var tPlanDesc = tPlanArr[0][j++];
				var tPlanType =  tPlanArr[0][j++];
				var tPlanTypeName =  tPlanArr[0][j++];
				var tPremCalType =  tPlanArr[0][j++];
				var tPremCalTypeName =  tPlanArr[0][j++];
				var tPlanFlag = tPlanArr[0][j++];
				var tPlanFlagName = tPlanArr[0][j++];
				var tInsuredPeriod = tPlanArr[0][j++];//�����ڼ�
				var tOccupTypeFlag =  tPlanArr[0][j++];
				var tOccupTypeFlagName =  tPlanArr[0][j++];
				var tMinOccupType =  tPlanArr[0][j++];
				var tMinOccupTypeName =  tPlanArr[0][j++];
				var tMaxOccupType =  tPlanArr[0][j++];
				var tMaxOccupTypeName =  tPlanArr[0][j++];
				var tOccupType =  tPlanArr[0][j++];
				var tOccupTypeName =  tPlanArr[0][j++];
				var tOccupMidType =  tPlanArr[0][j++];
				var tOccupMidTypeName =  tPlanArr[0][j++];
				var tOccupCode =  tPlanArr[0][j++];
				var tOccupCodeName =  tPlanArr[0][j++];
				var tNumPeople =  tPlanArr[0][j++];
				var tMaleRate =  tPlanArr[0][j++];
				var tFemaleRate =  tPlanArr[0][j++];
				var tMinAge =  tPlanArr[0][j++];
				var tMaxAge =  tPlanArr[0][j++];
				var tAvgAge =  tPlanArr[0][j++];
				var tMinSalary =  tPlanArr[0][j++];
				var tMaxSalary =  tPlanArr[0][j++];
				var tAvgSalary =  tPlanArr[0][j++];
				var tSocialInsuRate =  tPlanArr[0][j++];//�μ��籣ռ��
				var tRetireRate =  tPlanArr[0][j++];//����ռ��
				var tOtherDesc = tPlanArr[0][j++];//����˵��

				if (tNeedCheckFlag&&tPlanNeedCheck) {//�ٴε��ﵱǰ�ڵ�,�Ҹ���ʷ���ڸ÷���
					
					j = 0;
					var tSysPlanCode1 = tOldPlanArr[0][j++];
					var tPlanCode1 = tOldPlanArr[0][j++];
					var tPlanDesc1 = tOldPlanArr[0][j++];
					var tPlanType1 =  tOldPlanArr[0][j++];
					var tPlanTypeName1 =  tOldPlanArr[0][j++];
					var tPremCalType1 =  tOldPlanArr[0][j++];
					var tPremCalTypeName1 =  tOldPlanArr[0][j++];
					var tPlanFlag1 = tOldPlanArr[0][j++];
					var tPlanFlagName1 = tOldPlanArr[0][j++];
					var tInsuredPeriod1 = tOldPlanArr[0][j++];//�����ڼ�
					var tOccupTypeFlag1 =  tOldPlanArr[0][j++];
					var tOccupTypeFlagName1 =  tOldPlanArr[0][j++];
					var tMinOccupType1 =  tOldPlanArr[0][j++];
					var tMinOccupTypeName1 =  tOldPlanArr[0][j++];
					var tMaxOccupType1 =  tOldPlanArr[0][j++];
					var tMaxOccupTypeName1 =  tOldPlanArr[0][j++];
					var tOccupType1 =  tOldPlanArr[0][j++];
					var tOccupTypeName1 =  tOldPlanArr[0][j++];
					var tOccupMidType1 =  tOldPlanArr[0][j++];
					var tOccupMidTypeName1 =  tOldPlanArr[0][j++];
					var tOccupCode1 =  tOldPlanArr[0][j++];
					var tOccupCodeName1 =  tOldPlanArr[0][j++];
					var tNumPeople1 =  tOldPlanArr[0][j++];
					var tMaleRate1 =  tOldPlanArr[0][j++];
					var tFemaleRate1 =  tOldPlanArr[0][j++];
					var tMinAge1 =  tOldPlanArr[0][j++];
					var tMaxAge1 =  tOldPlanArr[0][j++];
					var tAvgAge1 =  tOldPlanArr[0][j++];
					var tMinSalary1 =  tOldPlanArr[0][j++];
					var tMaxSalary1 =  tOldPlanArr[0][j++];
					var tAvgSalary1 =  tOldPlanArr[0][j++];
					var tSocialInsuRate1 =  tOldPlanArr[0][j++];//�μ��籣ռ��
					var tRetireRate1 =  tOldPlanArr[0][j++];//����ռ��
					var tOtherDesc1 = tOldPlanArr[0][j++];//����˵��

					tInnerHTML1 += "<table class=common><tr class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></tr>"
												+"<tr class=common>";
					if (tPlanDesc!=tPlanDesc1) {
						tInnerHTML1 += "	<td class=title colSpan=6><span style=\"background-color:"+ tDifColor +"\"><b>������</b>"+tPlanCode+"("+tPlanDesc+")</span></td>";
					} else {
						tInnerHTML1 += "	<td class=title colSpan=6><b>������</b>"+tPlanCode+"("+tPlanDesc+")</td>";
					}
					tInnerHTML1 += "</tr>";
					if (cTranProdType=="00" || cTranProdType=="02" || cTranProdType=="03") {//��ͨ����,�˻��ͼ���������
						
						if (tPlanType=="00" || tPlanType=="02") {
							
							if (tOccupTypeFlag!=tOccupTypeFlag1) {
								
								//ְҵ����
								if (tOccupTypeFlag=="1") {//��ְҵ
									
									tInnerHTML1 +="<tr class=common>"
															+"	<td class=title colSpan=6><span style=\"background-color:"+ tDifColor +"\"><b>ְҵ���</b>"+tOccupTypeName+"</span>&nbsp;&nbsp;<b><span style=\"background-color:"+ tDifColor +"\">ְҵ�з��ࣺ</b>"+tOccupMidTypeName+"</span>&nbsp;&nbsp;<span style=\"background-color:"+ tDifColor +"\"><b>���֣�</b>"+tOccupCodeName+"</span>&nbsp;&nbsp;</td>";
															+"</tr>";
								} else if (tOccupTypeFlag=="2") {//��ְҵ
									
									tInnerHTML1 +="<tr class=common>"
															 +"	<td class=title colSpan=6><span style=\"background-color:"+ tDifColor +"\"><b>ְҵ���</b>"+ tMinOccupTypeName +"-"+ tMaxOccupTypeName +"</span>";
															 +"</tr>";
								}
							} else {
								
								//ְҵ����
								if (tOccupTypeFlag=="1") {//��ְҵ
									
									tInnerHTML1 +="<tr class=common>";
									
									if (tOccupTypeName!=tOccupTypeName1) {
										
										tInnerHTML1 +="	<td class=title colSpan=6><span style=\"background-color:"+ tDifColor +"\"><b>ְҵ���</b>"+tOccupTypeName+"</span>&nbsp;&nbsp;<span style=\"background-color:"+ tDifColor +"\"><b>ְҵ�з��ࣺ</b>"+tOccupMidTypeName+"</span>&nbsp;&nbsp;<span style=\"background-color:"+ tDifColor +"\"><b>���֣�</b>"+tOccupCodeName+"</span>&nbsp;&nbsp;</td>";
									} else {
										
										tInnerHTML1 +="	<td class=title colSpan=6><b>ְҵ���</b>"+tOccupTypeName+"&nbsp;&nbsp;";
										if (tOccupMidTypeName!=tOccupMidTypeName1) {
											tInnerHTML1 +="<span style=\"background-color:"+ tDifColor +"\"><b>ְҵ�з��ࣺ</b>"+tOccupMidTypeName+"</span>&nbsp;&nbsp;<span style=\"background-color:"+ tDifColor +"\"><b>���֣�</b>"+tOccupCodeName+"</span>&nbsp;&nbsp;</td>";
										} else {
										
											tInnerHTML1 +="<b>ְҵ�з��ࣺ</b>"+tOccupMidTypeName+"&nbsp;&nbsp;<b>";
											if (tOccupCodeName!=tOccupCodeName1) {
												tInnerHTML1 +="<span style=\"background-color:"+ tDifColor +"\"><b>���֣�</b>"+tOccupCodeName+"</span>&nbsp;&nbsp;</td>";
											} else {
												tInnerHTML1 +="<b>���֣�</b>"+tOccupCodeName+"&nbsp;&nbsp;</td>";
											}
										}
									}
									
									tInnerHTML1 +="</tr>";
								} else if (tOccupTypeFlag=="2") {//��ְҵ
									
									if (tMinOccupTypeName!=tMinOccupTypeName1 || tMaxOccupTypeName!=tMaxOccupTypeName1) {
									
										tInnerHTML1 +="<tr class=common>"
																 +"	<td class=title colSpan=6><span style=\"background-color:"+ tDifColor +"\"><b>ְҵ���</b>"+ tMinOccupTypeName +"-"+ tMaxOccupTypeName +"</span>";
																 +"</tr>";
									} else {
									
										tInnerHTML1 +="<tr class=common>"
																 +"	<td class=title colSpan=6><b>ְҵ���</b>"+ tMinOccupTypeName +"-"+ tMaxOccupTypeName;
																 +"</tr>";
									}
								}
							}
							
							tInnerHTML1 +="<tr class=common>"
													+"	<td class=title colSpan=6>";
							if (tNumPeople!=tNumPeople1) {//����
								tInnerHTML1 +="<span style=\"background-color:"+ tDifColor +"\"><b>������</b>"+ tNumPeople +"</span>&nbsp;&nbsp;";
							} else {
								tInnerHTML1 +="<b>������</b>"+ tNumPeople +"&nbsp;&nbsp;";
							}
							
							if (tMaleRate!=tMaleRate1 || tFemaleRate!=tFemaleRate1) {//��Ů����
								tInnerHTML1 +="<span style=\"background-color:"+ tDifColor +"\"><b>��Ů������</b>"+ tMaleRate +":"+ tFemaleRate +"</span>&nbsp;&nbsp;";
							} else {
								tInnerHTML1 +="<b>��Ů������</b>"+ tMaleRate +":"+ tFemaleRate +"&nbsp;&nbsp;";
							}
							
							if (tMinAge!=tMinAge1) {//�������
								tInnerHTML1 +="<span style=\"background-color:"+ tDifColor +"\"><b>������䣺</b>"+ tMinAge +"</span>&nbsp;&nbsp;";
							} else {
								tInnerHTML1 +="<b>������䣺</b>"+ tMinAge +"&nbsp;&nbsp;";
							}
							
							if (tMaxAge!=tMaxAge1) {//�������
								tInnerHTML1 +="<span style=\"background-color:"+ tDifColor +"\"><b>������䣺</b>"+ tMaxAge +"</span>&nbsp;&nbsp;";
							} else {
								tInnerHTML1 +="<b>������䣺</b>"+ tMaxAge +"&nbsp;&nbsp;";
							}
							
							if (tAvgAge!=tAvgAge1) {
								tInnerHTML1 +="<span style=\"background-color:"+ tDifColor +"\"><b>ƽ�����䣺</b>"+ tAvgAge +"</span>&nbsp;&nbsp;";
							} else {
								tInnerHTML1 +="<b>ƽ�����䣺</b>"+ tAvgAge +"&nbsp;&nbsp;";
							}
							
							tInnerHTML1 +="</td>"
													+" </tr>"
													+"<tr class=common>"
													+"	<td class=title colSpan=6>";
													
							if (tMinSalary!=tMinSalary1) {
								tInnerHTML1 +="<span style=\"background-color:"+ tDifColor +"\"><b>�����н��</b>"+ tMinSalary +"</span>&nbsp;&nbsp;";
							} else {
								tInnerHTML1 +="<b>�����н��</b>"+ tMinSalary +"&nbsp;&nbsp;";
							}
							
							if (tMaxSalary!=tMaxSalary1) {
								tInnerHTML1 +="<span style=\"background-color:"+ tDifColor +"\"><b>�����н��</b>"+ tMaxSalary +"</span>&nbsp;&nbsp;";
							} else {
								tInnerHTML1 +="<b>�����н��</b>"+ tMaxSalary +"&nbsp;&nbsp;";
							}
							
							if (tAvgSalary!=tAvgSalary1) {
								tInnerHTML1 +="<span style=\"background-color:"+ tDifColor +"\"><b>ƽ����н��</b>"+ tAvgSalary +"</span>&nbsp;&nbsp;";
							} else {
								tInnerHTML1 +="<b>ƽ����н��</b>"+ tAvgSalary +"&nbsp;&nbsp;";
							}
							
							if (tSocialInsuRate!=tSocialInsuRate1) {
								tInnerHTML1 +="<span style=\"background-color:"+ tDifColor +"\"><b>�μ��籣ռ�ȣ�</b>"+ tSocialInsuRate +"</span>&nbsp;&nbsp;";
							} else {
								tInnerHTML1 +="<b>�μ��籣ռ�ȣ�</b>"+ tSocialInsuRate +"&nbsp;&nbsp;";
							}
							
							if (tRetireRate!=tRetireRate1) {
								tInnerHTML1 +="<span style=\"background-color:"+ tDifColor +"\"><b>����ռ�ȣ�</b>"+ tRetireRate +"&nbsp;&nbsp;</span>";
							} else {
								tInnerHTML1 +="<b>����ռ�ȣ�</b>"+ tRetireRate +"&nbsp;&nbsp;";
							}
													
							tInnerHTML1 +="</td>"
													+"</tr>";
						}
					}
        	
        	if (tOtherDesc!=tOtherDesc1) {
        		tInnerHTML1 +="<tr class=common>"
											+"	<td class=title colSpan=6><span style=\"background-color:"+ tDifColor +"\"><b>����˵����</b>"+tOtherDesc+"</span></td>"
											+"</tr>"
											+"</table>";
        	} else {
        		tInnerHTML1 +="<tr class=common>"
											+"	<td class=title colSpan=6><b>����˵����</b>"+tOtherDesc+"</td>"
											+"</tr>"
											+"</table>";
        	}
				} else {
					
					var tStyle = "";
					if (tNeedCheckFlag&&!tPlanNeedCheck) {
						tStyle = "style=\"background-color: "+ tNewColor +"\"";
					}
					
					tInnerHTML1 += "<table class=common><tr class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></tr>"
												+"<tr class=common>"
												+"	<td class=title colSpan=6 "+ tStyle +"><b>������</b>"+tPlanCode+"("+tPlanDesc+")</td>"
												+"</tr>";
					if (cTranProdType=="00" || cTranProdType=="02" || cTranProdType=="03") {//��ͨ����,�˻��ͼ���������
						
						if (tPlanType=="00" || tPlanType=="02") {
							
							//ְҵ����
							if (tOccupTypeFlag=="1") {//��ְҵ
								
								tInnerHTML1 +="<tr class=common>"
														+"	<td class=title colSpan=6 "+ tStyle +"><b>ְҵ���</b>"+tOccupTypeName+"&nbsp;&nbsp;<b>ְҵ�з��ࣺ</b>"+tOccupMidTypeName+"&nbsp;&nbsp;<b>���֣�</b>"+tOccupCodeName+"&nbsp;&nbsp;</td>"
														+"</tr>";
							} else if (tOccupTypeFlag=="2") {//��ְҵ
								
								tInnerHTML1 +="<tr class=common>"
														 +"	<td class=title colSpan=6 "+ tStyle +"><b>ְҵ���</b>"+ tMinOccupTypeName +"-"+ tMaxOccupTypeName;
														 +"</tr>";
							}
							tInnerHTML1 +="<tr class=common>"
													+"	<td class=title colSpan=6 "+ tStyle +"><b>������</b>"+ tNumPeople +"&nbsp;&nbsp;<b>��Ů������</b>"+ tMaleRate +":"+ tFemaleRate +"&nbsp;&nbsp;<b>������䣺</b>"+ tMinAge +"&nbsp;&nbsp;<b>������䣺</b>"+ tMaxAge +"&nbsp;&nbsp;<b>ƽ�����䣺</b>"+ tAvgAge +"&nbsp;&nbsp;</td>"
													+"</tr>"
													+"<tr class=common>"
													+"	<td class=title colSpan=6 "+ tStyle +"><b>�����н��</b>"+ tMinSalary +"&nbsp;&nbsp;<b>�����н��</b>"+ tMaxSalary +"&nbsp;&nbsp;<b>ƽ����н��</b>"+ tAvgSalary +"&nbsp;&nbsp;<b>�μ��籣ռ�ȣ�</b>"+ tSocialInsuRate +"&nbsp;&nbsp;<b>����ռ�ȣ�</b>"+ tRetireRate +"&nbsp;&nbsp;</td>"
													+"</tr>";
						}
					}
        	
					tInnerHTML1 +="<tr class=common>"
											+"	<td class=title colSpan=6 "+ tStyle +"><b>����˵����</b>"+tOtherDesc+"</td>"
											+"</tr>"
											+"</table>";
				}
					
				//��ѯ��������ϸ��Ϣ
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
				tSQLInfo.setSqlId("LSQuotAllDetailSql7");
				tSQLInfo.addSubPara(tQuotNo);
				tSQLInfo.addSubPara(tQuotBatNo);
				tSQLInfo.addSubPara(tSysPlanCode);
				tSQLInfo.addSubPara(tPlanCode);
				
				var tPlanDetailArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
				
				if (tPlanDetailArr==null) {
				
				} else {
				
					tInnerHTML1 +="<div>"	
											+"	<table class=common>"
											+"	 <tr class=common>"
											+"	 	<td text-align: left colSpan=1>"
											+"	 		<table class=muline ALIGN=left border=0 CELLSPACING=0 CELLPADDING=0>"
											+"	 			<tr>"
											+"	 				<td class=mulinetitle><input class=mulinetitle value='���' style='width: "+ tWidthArr[0] +"' readonly></td> "
											+"	 				<td class=mulinetitle style='display: none'><input class=mulinetitle value='�������Ʊ���' style='width: "+ tWidthArr[1] +"' readonly></td>"
											+"	 				<td class=mulinetitle><input class=mulinetitle value='��������' style='width: "+ tWidthArr[2] +"' readonly></td>"
											+"	 				<td class=mulinetitle style='display: none'><input class=mulinetitle value='���α���' style='width: "+ tWidthArr[3] +"' readonly></td> "
											+"	 				<td class=mulinetitle><input class=mulinetitle value='��������' style='width: "+ tWidthArr[4] +"' readonly></td> "
											+"	 				<td class=mulinetitle><input class=mulinetitle value='������������' style='width: "+ tWidthArr[5] +"' readonly></td> ";
											
					if (cTranProdType=="00" || cTranProdType=="01" || cTranProdType=="03") {//��ͨ����,�����ռ���������
					
						tInnerHTML1 +="	<td class=mulinetitle style='display: none'><input class=mulinetitle value='�����������ͱ���' style='width: "+ tWidthArr[6] +"' readonly></td>"
												+"	<td class=mulinetitle><input class=mulinetitle value='������������' style='width: "+ tWidthArr[7] +"' readonly></td>";
					
						if (cActivityID=="0800100001") {//ѯ��¼��
						
							tInnerHTML1 +="		<td class=mulinetitle><input class=mulinetitle value='�ο�����' style='width: "+ tWidthArr[8] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='��������' style='width: "+ tWidthArr[9] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='�ۿ��ʢ�' style='width: "+ tWidthArr[10] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='�˱�����(��)' style='width: "+ tWidthArr[11] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='�ۿ��ʢ�' style='width: "+ tWidthArr[12] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='�˱�����(��)' style='width: "+ tWidthArr[13] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='�ۿ��ʢ�' style='width: "+ tWidthArr[14] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='���鱣��' style='width: "+ tWidthArr[15] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='�ۿ��ʢ�' style='width: "+ tWidthArr[16] +"' readonly></td>";
						} else if (cActivityID=="0800100002") {//�к�
							
							tInnerHTML1 +="		<td class=mulinetitle><input class=mulinetitle value='�ο�����' style='width: "+ tWidthArr[8] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='��������' style='width: "+ tWidthArr[9] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='�ۿ��ʢ�' style='width: "+ tWidthArr[10] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='�˱�����(��)' style='width: "+ tWidthArr[11] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='�ۿ��ʢ�' style='width: "+ tWidthArr[12] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='�˱�����(��)' style='width: "+ tWidthArr[13] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='�ۿ��ʢ�' style='width: "+ tWidthArr[14] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='���鱣��' style='width: "+ tWidthArr[15] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='�ۿ��ʢ�' style='width: "+ tWidthArr[16] +"' readonly></td>";
						} else if (cActivityID=="0800100003") {//�ֺ�
							
							tInnerHTML1 +="		<td class=mulinetitle><input class=mulinetitle value='�ο�����' style='width: "+ tWidthArr[8] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='��������' style='width: "+ tWidthArr[9] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='�ۿ��ʢ�' style='width: "+ tWidthArr[10] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='�˱�����(��)' style='width: "+ tWidthArr[11] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='�ۿ��ʢ�' style='width: "+ tWidthArr[12] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='�˱�����(��)' style='width: "+ tWidthArr[13] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='�ۿ��ʢ�' style='width: "+ tWidthArr[14] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='���鱣��' style='width: "+ tWidthArr[15] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='�ۿ��ʢ�' style='width: "+ tWidthArr[16] +"' readonly></td>";
						} else if (cActivityID=="0800100004") {//�ܺ�
							
							tInnerHTML1 +="		<td class=mulinetitle><input class=mulinetitle value='�ο�����' style='width: "+ tWidthArr[8] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='��������' style='width: "+ tWidthArr[9] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='�ۿ��ʢ�' style='width: "+ tWidthArr[10] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='�˱�����(��)' style='width: "+ tWidthArr[11] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='�ۿ��ʢ�' style='width: "+ tWidthArr[12] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='�˱�����(��)' style='width: "+ tWidthArr[13] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='�ۿ��ʢ�' style='width: "+ tWidthArr[14] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='���鱣��' style='width: "+ tWidthArr[15] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='�ۿ��ʢ�' style='width: "+ tWidthArr[16] +"' readonly></td>";
						} else {
							
							tInnerHTML1 +="		<td class=mulinetitle><input class=mulinetitle value='�ο�����' style='width: "+ tWidthArr[8] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='��������' style='width: "+ tWidthArr[9] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='�ۿ��ʢ�' style='width: "+ tWidthArr[10] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='�˱�����(��)' style='width: "+ tWidthArr[11] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='�ۿ��ʢ�' style='width: "+ tWidthArr[12] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='�˱�����(��)' style='width: "+ tWidthArr[13] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='�ۿ��ʢ�' style='width: "+ tWidthArr[14] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='���鱣��' style='width: "+ tWidthArr[15] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='�ۿ��ʢ�' style='width: "+ tWidthArr[16] +"' readonly></td>";
						}
						
						tInnerHTML1 +="		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='��ʼ����' style='width: "+ tWidthArr[17] +"' readonly></td>"
												+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='����������' style='width: "+ tWidthArr[18] +"' readonly></td>"
												+"	</tr>";
					} else if (cTranProdType=="02") {//�˻���
						
						tInnerHTML1 +="			<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='�ο�����' style='width: "+ tWidthArr[8] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='��������' style='width: "+ tWidthArr[9] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='�ۿ��ʢ�' style='width: "+ tWidthArr[10] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='�˱�����(��)' style='width: "+ tWidthArr[11] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='�ۿ��ʢ�' style='width: "+ tWidthArr[12] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='�˱�����(��)' style='width: "+ tWidthArr[13] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='�ۿ��ʢ�' style='width: "+ tWidthArr[14] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='���鱣��' style='width: "+ tWidthArr[15] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='�ۿ��ʢ�' style='width: "+ tWidthArr[16] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='��ʼ����' style='width: "+ tWidthArr[17] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='����������' style='width: "+ tWidthArr[18] +"' readonly></td>"
													+"	</tr>";
					}

					for (var k=0; k<tPlanDetailArr.length; k++) {
						
						var k1 = 0;
						var tRiskCode = tPlanDetailArr[k][k1++];
						var tRiskName = tPlanDetailArr[k][k1++];
						var tDutyCode = tPlanDetailArr[k][k1++];
						var tDutyName = tPlanDetailArr[k][k1++];
						var tAmntType = tPlanDetailArr[k][k1++];
						var tAmntTypeName = tPlanDetailArr[k][k1++];
						var tFixedAmnt = tPlanDetailArr[k][k1++];
						var tSalaryMult = tPlanDetailArr[k][k1++];
						var tMinAmnt = tPlanDetailArr[k][k1++];
						var tMaxAmnt = tPlanDetailArr[k][k1++];
						var tExceptPremType = tPlanDetailArr[k][k1++];
						var tExceptPremTypeName = tPlanDetailArr[k][k1++];
						var tStandPrem = tPlanDetailArr[k][k1++];
						var tExceptPrem = tPlanDetailArr[k][k1++];
						var tDecuRate1 = tPlanDetailArr[k][k1++];
						var tSubUWValue = tPlanDetailArr[k][k1++];
						var tDecuRate20 = tPlanDetailArr[k][k1++];
						var tBranchUWValue = tPlanDetailArr[k][k1++];
						var tDecuRate21 = tPlanDetailArr[k][k1++];
						var tUWValue = tPlanDetailArr[k][k1++];
						var tDecuRate3 = tPlanDetailArr[k][k1++];
						var tInitPrem = tPlanDetailArr[k][k1++];
						var tExceptYield = tPlanDetailArr[k][k1++];
						var tRelaShareFlag = tPlanDetailArr[k][k1++];
						
						var tDetailDesc = "";
						
						var tOldRiskCode = "";
						var tOldRiskName = "";
						var tOldDutyCode = "";
						var tOldDutyName = "";
						var tOldAmntType = "";
						var tOldAmntTypeName = "";
						var tOldFixedAmnt = "";
						var tOldSalaryMult = "";
						var tOldMinAmnt = "";
						var tOldMaxAmnt = "";
						var tOldExceptPremType = "";
						var tOldExceptPremTypeName = "";
						var tOldStandPrem = "";
						var tOldExceptPrem = "";
						var tOldDecuRate1 = "";
						var tOldSubUWValue = "";
						var tOldDecuRate20 = "";
						var tOldBranchUWValue = "";
						var tOldDecuRate21 = "";
						var tOldUWValue = "";
						var tOldDecuRate3 = "";
						var tOldInitPrem = "";
						var tOldExceptYield = "";
						var tOldRelaShareFlag = "";
						
						var tOldDetailDesc = "";

						if (cTranProdType=="02") {//�˻��ʹ���
							
							if (tExceptYield==null || tExceptYield=="") {
								tDetailDesc =  "Ԥ��������:��;";
							} else {
								tDetailDesc += "Ԥ��������:"+ tExceptYield +";";
							}
						} else {
							
							tDetailDesc += "��������:"+ tAmntTypeName +";";
							if (tAmntType=="01") {
								tDetailDesc += "�̶�����(Ԫ):"+ tFixedAmnt +";";
							} else if (tAmntType=="02") {
								tDetailDesc += "��н����:"+ tSalaryMult +";";
							} else if (tAmntType=="03") {
								tDetailDesc += "��н����:"+ tSalaryMult +";��ͱ���(Ԫ):"+ tMinAmnt +";";
							} else if (tAmntType=="04") {
								tDetailDesc += "��ͱ���(Ԫ):"+ tMinAmnt +";��߱���(Ԫ):"+ tMaxAmnt +";";
							}
							
						}
						
						var tPlanDetailNeedCheck = false;
						var tOldPlanDetailArr;
						if (tNeedCheckFlag&&tPlanNeedCheck) {//��ҪУ��
						
							//�ҵ�������������ʷ��¼
							tSQLInfo = new SqlClass();
							tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
							tSQLInfo.setSqlId("LSQuotAllDetailSql14");
							tSQLInfo.addSubPara(tQuotNo);
							tSQLInfo.addSubPara(tQuotBatNo);
							tSQLInfo.addSubPara(tSysPlanCode);
							tSQLInfo.addSubPara(tPlanCode);
							tSQLInfo.addSubPara(tRiskCode);
							tSQLInfo.addSubPara(tDutyCode);
							tSQLInfo.addSubPara(tTraceNo);
							
							tOldPlanDetailArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
							if (tOldPlanDetailArr==null) {
							
							} else {
								
								tPlanDetailNeedCheck = true;
								k1 = 0;
								tOldRiskCode = tOldPlanDetailArr[0][k1++];
								tOldRiskName = tOldPlanDetailArr[0][k1++];
								tOldDutyCode = tOldPlanDetailArr[0][k1++];
								tOldDutyName = tOldPlanDetailArr[0][k1++];
								tOldAmntType = tOldPlanDetailArr[0][k1++];
								tOldAmntTypeName = tOldPlanDetailArr[0][k1++];
								tOldFixedAmnt = tOldPlanDetailArr[0][k1++];
								tOldSalaryMult = tOldPlanDetailArr[0][k1++];
								tOldMinAmnt = tOldPlanDetailArr[0][k1++];
								tOldMaxAmnt = tOldPlanDetailArr[0][k1++];
								tOldExceptPremType = tOldPlanDetailArr[0][k1++];
								tOldExceptPremTypeName = tOldPlanDetailArr[0][k1++];
								tOldStandPrem = tOldPlanDetailArr[0][k1++];
								tOldExceptPrem = tOldPlanDetailArr[0][k1++];
								tOldDecuRate1 = tOldPlanDetailArr[0][k1++];
								tOldSubUWValue = tOldPlanDetailArr[0][k1++];
								tOldDecuRate20 = tOldPlanDetailArr[0][k1++];
								tOldBranchUWValue = tOldPlanDetailArr[0][k1++];
								tOldDecuRate21 = tOldPlanDetailArr[0][k1++];
								tOldUWValue = tOldPlanDetailArr[0][k1++];
								tOldDecuRate3 = tOldPlanDetailArr[0][k1++];
								tOldInitPrem = tOldPlanDetailArr[0][k1++];
								tOldExceptYield = tOldPlanDetailArr[0][k1++];
								tOldRelaShareFlag = tOldPlanDetailArr[0][k1++];
								
								if (cTranProdType=="02") {//�˻��ʹ���
									
									if (tOldExceptYield==null || tOldExceptYield=="") {
										tOldDetailDesc =  "Ԥ��������:��;";
									} else {
										tOldDetailDesc += "Ԥ��������:"+ tOldExceptYield +";";
									}
									
								} else {
									
									tOldDetailDesc += "��������:"+ tOldAmntTypeName +";";
									if (tAmntType=="01") {
										tOldDetailDesc += "�̶�����(Ԫ):"+ tOldFixedAmnt +";";
									} else if (tAmntType=="02") {
										tOldDetailDesc += "��н����:"+ tOldSalaryMult +";";
									} else if (tAmntType=="03") {
										tOldDetailDesc += "��н����:"+ tOldSalaryMult +";��ͱ���(Ԫ):"+ tOldMinAmnt +";";
									} else if (tAmntType=="04") {
										tOldDetailDesc += "��ͱ���(Ԫ):"+ tOldMinAmnt +";��߱���(Ԫ):"+ tOldMaxAmnt +";";
									}
								}
							}
						}
						
						//������������ ��� ��������������Ϣ
						var tRelaSub = "";
						if (tRelaShareFlag=="1") {
							
							tSQLInfo = new SqlClass();
							tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
							tSQLInfo.setSqlId("LSQuotAllDetailSql16");
							tSQLInfo.addSubPara(tQuotNo);
							tSQLInfo.addSubPara(tQuotBatNo);
							tSQLInfo.addSubPara(tSysPlanCode);
							tSQLInfo.addSubPara(tPlanCode);
							tSQLInfo.addSubPara(tRiskCode);
							tSQLInfo.addSubPara(tDutyCode);
							
							var tRelaSubArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
							
							tRelaSub = "���������˱���ռ��:"+ tRelaSubArr[0][0]+";���������˱���ռ��:"+tRelaSubArr[0][1]+";";
						}
						
						//������������ ��� ��������������Ϣ  Old
						var tOldRelaSub = "";
						if (tOldRelaShareFlag=="1") {
							
							tSQLInfo = new SqlClass();
							tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
							tSQLInfo.setSqlId("LSQuotAllDetailSql18");
							tSQLInfo.addSubPara(tQuotNo);
							tSQLInfo.addSubPara(tQuotBatNo);
							tSQLInfo.addSubPara(tSysPlanCode);
							tSQLInfo.addSubPara(tPlanCode);
							tSQLInfo.addSubPara(tRiskCode);
							tSQLInfo.addSubPara(tDutyCode);
							tSQLInfo.addSubPara(tTraceNo);
							
							var tOldRelaSubArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
							
							tOldRelaSub = "���������˱���ռ��:"+ tOldRelaSubArr[0][0]+";���������˱���ռ��:"+tOldRelaSubArr[0][1]+";";
						}
						
						//��ȡ�������ζ�̬������
						var tDutyArr = getDutyElementArr(tRiskCode, tDutyCode);
						if (tDutyArr==null) {//�����ζ�̬��
							
							tDetailDesc += tRelaSub;
							tOldDetailDesc += tOldRelaSub;
						} else {
							
							var tSQLElement = getDutySQLElement(tDutyArr); 
							
							tSQLInfo = new SqlClass();
							tSQLInfo.setResourceName("g_quot.LSQuotSql");
							tSQLInfo.setSqlId("LSQuotSql19");
							tSQLInfo.addSubPara(tSQLElement);//SQL��ѯ�ֶ�
							tSQLInfo.addSubPara(tQuotNo);
							tSQLInfo.addSubPara(tQuotBatNo);
							tSQLInfo.addSubPara(tSysPlanCode);
							tSQLInfo.addSubPara(tPlanCode);
							tSQLInfo.addSubPara(tRiskCode);
							tSQLInfo.addSubPara(tDutyCode);
							
							var tDutyDetailSub = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

							tDetailDesc += getDutyDetailSub(tDutyArr, tDutyDetailSub,0);
							tDetailDesc += tRelaSub;
							
							//�����������ö�̬����
							if (tRelaShareFlag=="1") {
								
								tSQLInfo = new SqlClass();
								tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
								tSQLInfo.setSqlId("LSQuotAllDetailSql17");
								tSQLInfo.addSubPara(tSQLElement);//SQL��ѯ�ֶ�
								tSQLInfo.addSubPara(tQuotNo);
								tSQLInfo.addSubPara(tQuotBatNo);
								tSQLInfo.addSubPara(tSysPlanCode);
								tSQLInfo.addSubPara(tPlanCode);
								tSQLInfo.addSubPara(tRiskCode);
								tSQLInfo.addSubPara(tDutyCode);
								
								var tRelaSubDetailSub = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
								tDetailDesc += getDutyDetailSub(tDutyArr, tRelaSubDetailSub,1);
							}
							
							tSQLInfo = new SqlClass();
							tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
							tSQLInfo.setSqlId("LSQuotAllDetailSql15");
							tSQLInfo.addSubPara(tSQLElement);//SQL��ѯ�ֶ�
							tSQLInfo.addSubPara(tQuotNo);
							tSQLInfo.addSubPara(tQuotBatNo);
							tSQLInfo.addSubPara(tSysPlanCode);
							tSQLInfo.addSubPara(tPlanCode);
							tSQLInfo.addSubPara(tRiskCode);
							tSQLInfo.addSubPara(tDutyCode);
							tSQLInfo.addSubPara(tTraceNo);
							
							var tOldDutyDetailSub = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

							tOldDetailDesc += getDutyDetailSub(tDutyArr, tDutyDetailSub,0);
							
							//�����������ö�̬���� Old
							if (tOldRelaShareFlag=="1") {
								
								tSQLInfo = new SqlClass();
								tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
								tSQLInfo.setSqlId("LSQuotAllDetailSql19");
								tSQLInfo.addSubPara(tSQLElement);//SQL��ѯ�ֶ�
								tSQLInfo.addSubPara(tQuotNo);
								tSQLInfo.addSubPara(tQuotBatNo);
								tSQLInfo.addSubPara(tSysPlanCode);
								tSQLInfo.addSubPara(tPlanCode);
								tSQLInfo.addSubPara(tRiskCode);
								tSQLInfo.addSubPara(tDutyCode);
								tSQLInfo.addSubPara(tTraceNo);
								
								var tOldRelaSubDetailSub = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
								tOldDetailDesc += getDutyDetailSub(tDutyArr, tOldRelaSubDetailSub,1);
							}
							
						}
						
						if (tNeedCheckFlag&&tPlanNeedCheck&&tPlanDetailNeedCheck) {
								
							tInnerHTML1		+=" <tr>"
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[0] +"' value='"+ (k+1) +"' readonly></td>"// value='���' 
														+"		<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[1] +"' name=RiskCode"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tRiskCode +"' readonly></td>"//value='�������Ʊ���'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[2] +"' name=RiskName"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tRiskName +"' readonly></td>"// value='��������'
														+"		<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[3] +"' name=DutyCode"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDutyCode +"' readonly></td> "// value='���α���'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[4] +"' name=DutyName"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDutyName +"' readonly></td> ";// value='��������'
														
							if (tDetailDesc!=tOldDetailDesc) {// value='������������'
								tInnerHTML1 +=" <td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[5] +"' style=\"background-color:"+ tDifColor +"\" name=DetailDesc"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDetailDesc +"' readonly></td> ";
							} else {
								tInnerHTML1 +=" <td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[5] +"' name=DetailDesc"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDetailDesc +"' readonly></td> ";
							}
						} else {
							
							var tStyle = "";
							if ((tNeedCheckFlag&&tPlanNeedCheck&&!tPlanDetailNeedCheck) || (tNeedCheckFlag&&!tPlanNeedCheck)) {
							
								tStyle = "style=\"background-color:  "+ tNewColor +"\"";
							}
							tInnerHTML1		+=" <tr>"
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[0] +"' "+ tStyle +" value='"+ (k+1) +"' readonly></td>"// value='���' 
														+"		<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[1] +"'  "+ tStyle +" name=RiskCode"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tRiskCode +"' readonly></td>"//value='�������Ʊ���'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[2] +"'  "+ tStyle +" name=RiskName"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tRiskName +"' readonly></td>"// value='��������'
														+"		<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[3] +"'  "+ tStyle +" name=DutyCode"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDutyCode +"' readonly></td> "// value='���α���'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[4] +"'  "+ tStyle +" name=DutyName"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDutyName +"' readonly></td> "// value='��������'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[5] +"'  "+ tStyle +" name=DetailDesc"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDetailDesc +"' readonly></td> ";// value='������������'							
						}

						if (cTranProdType=="00" || cTranProdType=="01" || cTranProdType=="03") {//��ͨ����,�����ռ���������
						
							if (cActivityID=="0800100001") {//ѯ��¼��
								
								tInnerHTML1 +="	<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[6] +"' name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='�����������ͱ���'
													+"	<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[7] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>";// value='������������'
													
								tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[8] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tStandPrem +"' readonly></td>"// value='�ο�����'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[9] +"' name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>"// value='��������'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[10] +"' name=DecuRate1"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate1 +"' readonly></td>"// value='�ۿ��ʢ�'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[11] +"' name=SubUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tSubUWValue +"' readonly></td>"// value='�˱�����(��)'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[12] +"' name=DecuRate20"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate20 +"' readonly></td>"// value='�ۿ��ʢ�'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[13] +"' name=BranchUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tBranchUWValue +"' readonly></td>"// value='�˱�����(��)'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[14] +"' name=DecuRate21"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate21 +"' readonly></td>"// value='�ۿ��ʢ�'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[15] +"' name=UWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tUWValue +"' readonly></td>"// value='���鱣��'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[16] +"' name=DecuRate3"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate3 +"' readonly></td>";// value='�ۿ��ʢ�'		
							} else if (cActivityID=="0800100002") {//�к�
								
								if (tNeedCheckFlag&&tPlanNeedCheck&&tPlanDetailNeedCheck) {
								
									if (tExceptPremType!=tOldExceptPremType) {
										
										tInnerHTML1 +="	<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[6] +"' name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='�����������ͱ���'
														+"	<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[7] +"' style=\"background-color:"+ tDifColor +"\" name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>";// value='������������'
									} else {
										
										tInnerHTML1 +="	<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[6] +"' name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='�����������ͱ���'
														+"	<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[7] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>";// value='������������'
									}
									
									if (tStandPrem!=tOldStandPrem) {// value='�ο�����'
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[8] +"' style=\"background-color:"+ tDifColor +"\" name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tStandPrem +"' readonly></td>";// value='�ο�����'
									} else {
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[8] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tStandPrem +"' readonly></td>";
									}
									
									if (tExceptPrem!=tOldExceptPrem) {// value='��������'
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[9] +"' style=\"background-color:"+ tDifColor +"\" name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>";// value='��������'
									} else {
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[9] +"' name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>";
									}
									
									if (tDecuRate1!=tOldDecuRate1) {// value='�ۿ��ʢ�'
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[10] +"' style=\"background-color:"+ tDifColor +"\" name=DecuRate1"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate1 +"' readonly></td>";// value='�ۿ��ʢ�'
									} else {
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[10] +"' name=DecuRate1"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate1 +"' readonly></td>";
									}
									
									if (tSubUWValue!=tSubUWValue) {// value='�˱�����(��)'
										tInnerHTML1 +="<td class=mulinetitle><input class=mulinput style='width: "+ tWidthArr[11] +"' style=\"background-color:"+ tDifColor +"\" name=SubUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tSubUWValue +"'></td>";
									} else {
										tInnerHTML1 +="<td class=mulinetitle><input class=mulinput style='width: "+ tWidthArr[11] +"' name=SubUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tSubUWValue +"'></td>";
									}
		
									if (tDecuRate20!=tOldDecuRate20) {// value='�ۿ��ʢ�'
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[12] +"' style=\"background-color:"+ tDifColor +"\" name=DecuRate20"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate20 +"' readonly></td>";
									} else {
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[12] +"' name=DecuRate20"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate20 +"' readonly></td>";
									}
	
									tInnerHTML1 +="		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[13] +"' name=BranchUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tBranchUWValue +"' readonly></td>"// value='�˱�����(��)'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[14] +"' name=DecuRate21"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate21 +"' readonly></td>"// value='�ۿ��ʢ�'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[15] +"' name=UWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tUWValue +"' readonly></td>"// value='���鱣��'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[16] +"' name=DecuRate3"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate3 +"' readonly></td>";// value='�ۿ��ʢ�'
									
								} else {
									
									var tStyle = "";
									if ((tNeedCheckFlag&&tPlanNeedCheck&&!tPlanDetailNeedCheck) || (tNeedCheckFlag&&!tPlanNeedCheck)) {
									
										tStyle = "style=\"background-color:  "+ tNewColor +"\"";
									}
									
									tInnerHTML1 +="	<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[6] +"'  "+ tStyle +" name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='�����������ͱ���'
													+"	<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[7] +"'  "+ tStyle +" name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>";// value='������������'
									
									tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[8] +"' "+ tStyle +" name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tStandPrem +"' readonly></td>"// value='�ο�����'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[9] +"'  "+ tStyle +" name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>"// value='��������'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[10] +"' "+ tStyle +" name=DecuRate1"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate1 +"' readonly></td>"// value='�ۿ��ʢ�'
														+"		<td class=mulinetitle><input class=mulinput style='width: "+ tWidthArr[11] +"' "+ tStyle +" name=SubUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tSubUWValue +"'></td>"// value='�˱�����(��)'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[12] +"' "+ tStyle +" name=DecuRate20"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate20 +"' readonly></td>"// value='�ۿ��ʢ�'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[13] +"' "+ tStyle +" name=BranchUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tBranchUWValue +"' readonly></td>"// value='�˱�����(��)'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[14] +"' "+ tStyle +" name=DecuRate21"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate21 +"' readonly></td>"// value='�ۿ��ʢ�'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[15] +"' "+ tStyle +" name=UWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tUWValue +"' readonly></td>"// value='���鱣��'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[16] +"' "+ tStyle +" name=DecuRate3"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate3 +"' readonly></td>";// value='�ۿ��ʢ�'
								}	
							} else if (cActivityID=="0800100003") {//�ֺ�
								
								if (tNeedCheckFlag&&tPlanNeedCheck&&tPlanDetailNeedCheck) {
								
									if (tExceptPremType!=tOldExceptPremType) {
										
										tInnerHTML1 +="	<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[6] +"' name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='�����������ͱ���'
														+"	<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[7] +"' style=\"background-color:"+ tDifColor +"\" name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>";// value='������������'
									} else {
										
										tInnerHTML1 +="	<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[6] +"' name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='�����������ͱ���'
														+"	<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[7] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>";// value='������������'
									}
									
									if (tStandPrem!=tOldStandPrem) {// value='�ο�����'
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[8] +"' style=\"background-color:"+ tDifColor +"\" name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tStandPrem +"' readonly></td>";// value='�ο�����'
									} else {
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[8] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tStandPrem +"' readonly></td>";
									}
									
									if (tExceptPrem!=tOldExceptPrem) {// value='��������'
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[9] +"' style=\"background-color:"+ tDifColor +"\" name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>";// value='��������'
									} else {
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[9] +"' name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>";
									}
									
									if (tDecuRate1!=tOldDecuRate1) {// value='�ۿ��ʢ�'
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[10] +"' style=\"background-color:"+ tDifColor +"\" name=DecuRate1"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate1 +"' readonly></td>";// value='�ۿ��ʢ�'
									} else {
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[10] +"' name=DecuRate1"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate1 +"' readonly></td>";
									}
									
									if (tSubUWValue!=tSubUWValue) {// value='�˱�����(��)'
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[11] +"' style=\"background-color:"+ tDifColor +"\" name=SubUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tSubUWValue +"' readonly></td>";
									} else {
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[11] +"' name=SubUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tSubUWValue +"' readonly></td>";
									}
		
									if (tDecuRate20!=tOldDecuRate20) {// value='�ۿ��ʢ�'
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[12] +"' style=\"background-color:"+ tDifColor +"\" name=DecuRate20"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate20 +"' readonly></td>";
									} else {
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[12] +"' name=DecuRate20"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate20 +"' readonly></td>";
									}
									
									if (tBranchUWValue!=tOldBranchUWValue) {// value='�˱�����(��)'
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulinput style='width: "+ tWidthArr[13] +"' style=\"background-color:"+ tDifColor +"\" name=BranchUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tBranchUWValue +"'></td>";
									} else {
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulinput style='width: "+ tWidthArr[13] +"' name=BranchUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tBranchUWValue +"' ></td>";
									}
									
									if (tDecuRate21!=tOldDecuRate21) {// value='�ۿ��ʢ�'
										tInnerHTML1 +=" <td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[14] +"' style=\"background-color:"+ tDifColor +"\" name=DecuRate21"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate21 +"' readonly></td>";
									} else {
										tInnerHTML1 +=" <td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[14] +"' name=DecuRate21"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate21 +"' readonly></td>";
									}

									tInnerHTML1 +="		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[15] +"' name=UWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tUWValue +"' readonly></td>"// value='���鱣��'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[16] +"' name=DecuRate3"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate3 +"' readonly></td>";// value='�ۿ��ʢ�'
								} else {
									
									var tStyle = "";
									
									if ((tNeedCheckFlag&&tPlanNeedCheck&&!tPlanDetailNeedCheck) || (tNeedCheckFlag&&!tPlanNeedCheck)) {
									
										tStyle = "style=\"background-color:  "+ tNewColor +"\"";
									}
									
									tInnerHTML1 +="	<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[6] +"'  "+ tStyle +" name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='�����������ͱ���'
													+"	<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[7] +"'  "+ tStyle +" name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>";// value='������������'
									
									tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[8] +"' "+ tStyle +" name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tStandPrem +"' readonly></td>"// value='�ο�����'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[9] +"'  "+ tStyle +" name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>"// value='��������'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[10] +"' "+ tStyle +" name=DecuRate1"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate1 +"' readonly></td>"// value='�ۿ��ʢ�'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[11] +"' "+ tStyle +" name=SubUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tSubUWValue +"' readonly></td>"// value='�˱�����(��)'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[12] +"' "+ tStyle +" name=DecuRate20"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate20 +"' readonly></td>"// value='�ۿ��ʢ�'
														+"		<td class=mulinetitle><input class=mulinput style='width: "+ tWidthArr[13] +"' "+ tStyle +" name=BranchUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tBranchUWValue +"' ></td>"// value='�˱�����(��)'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[14] +"' "+ tStyle +" name=DecuRate21"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate21 +"' readonly></td>"// value='�ۿ��ʢ�'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[15] +"' "+ tStyle +" name=UWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tUWValue +"' readonly></td>"// value='���鱣��'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[16] +"' "+ tStyle +" name=DecuRate3"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate3 +"' readonly></td>";// value='�ۿ��ʢ�'
								}
							} else if (cActivityID=="0800100004") {//�ܺ�
								
								if (tNeedCheckFlag&&tPlanNeedCheck&&tPlanDetailNeedCheck) {
								
									if (tExceptPremType!=tOldExceptPremType) {
										
										tInnerHTML1 +="	<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[6] +"' name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='�����������ͱ���'
														+"	<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[7] +"' style=\"background-color:"+ tDifColor +"\" name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>";// value='������������'
									} else {
										
										tInnerHTML1 +="	<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[6] +"' name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='�����������ͱ���'
														+"	<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[7] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>";// value='������������'
									}
									
									if (tStandPrem!=tOldStandPrem) {// value='�ο�����'
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[8] +"' style=\"background-color:"+ tDifColor +"\" name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tStandPrem +"' readonly></td>";// value='�ο�����'
									} else {
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[8] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tStandPrem +"' readonly></td>";
									}
									
									if (tExceptPrem!=tOldExceptPrem) {// value='��������'
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[9] +"' style=\"background-color:"+ tDifColor +"\" name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>";// value='��������'
									} else {
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[9] +"' name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>";
									}
									
									if (tDecuRate1!=tOldDecuRate1) {// value='�ۿ��ʢ�'
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[10] +"' style=\"background-color:"+ tDifColor +"\" name=DecuRate1"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate1 +"' readonly></td>";// value='�ۿ��ʢ�'
									} else {
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[10] +"' name=DecuRate1"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate1 +"' readonly></td>";
									}
									
									if (tSubUWValue!=tSubUWValue) {// value='�˱�����(��)'
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[11] +"' style=\"background-color:"+ tDifColor +"\" name=SubUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tSubUWValue +"' readonly></td>";
									} else {
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[11] +"' name=SubUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tSubUWValue +"' readonly></td>";
									}
		
									if (tDecuRate20!=tOldDecuRate20) {// value='�ۿ��ʢ�'
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[12] +"' style=\"background-color:"+ tDifColor +"\" name=DecuRate20"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate20 +"' readonly></td>";
									} else {
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[12] +"' name=DecuRate20"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate20 +"' readonly></td>";
									}
									
									if (tBranchUWValue!=tOldBranchUWValue) {// value='�˱�����(��)'
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[13] +"' style=\"background-color:"+ tDifColor +"\" name=BranchUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tBranchUWValue +"' readonly></td>";
									} else {
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[13] +"' name=BranchUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tBranchUWValue +"' readonly></td>";
									}
									
									if (tDecuRate21!=tOldDecuRate21) {// value='�ۿ��ʢ�'
										tInnerHTML1 +=" <td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[14] +"' style=\"background-color:"+ tDifColor +"\" name=DecuRate21"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate21 +"' readonly></td>";
									} else {
										tInnerHTML1 +=" <td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[14] +"' name=DecuRate21"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate21 +"' readonly></td>";
									}
									
									if (tUWValue!=tOldUWValue) {// value='���鱣��'
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulinput style='width: "+ tWidthArr[15] +"' style=\"background-color:"+ tDifColor +"\" name=UWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tUWValue +"'></td>";
									} else {
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulinput style='width: "+ tWidthArr[15] +"' name=UWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tUWValue +"'></td>";
									}
									
									if (tDecuRate3!=tOldDecuRate3) {// value='�ۿ��ʢ�'
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[16] +"' style=\"background-color:"+ tDifColor +"\" name=DecuRate3"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate3 +"' readonly></td>";
									} else {
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[16] +"' name=DecuRate3"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate3 +"' readonly></td>";
									}
								} else {
									
									var tStyle = "";
									if ((tNeedCheckFlag&&tPlanNeedCheck&&!tPlanDetailNeedCheck) || (tNeedCheckFlag&&!tPlanNeedCheck)) {
									
										tStyle = "style=\"background-color:  "+ tNewColor +"\"";
									}
									
									tInnerHTML1 +="	<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[6] +"'  "+ tStyle +" name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='�����������ͱ���'
													+"	<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[7] +"'  "+ tStyle +" name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>";// value='������������'
									
									tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[8] +"' "+ tStyle +" name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tStandPrem +"' readonly></td>"// value='�ο�����'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[9] +"'  "+ tStyle +" name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>"// value='��������'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[10] +"' "+ tStyle +" name=DecuRate1"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate1 +"' readonly></td>"// value='�ۿ��ʢ�'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[11] +"' "+ tStyle +" name=SubUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tSubUWValue +"'readonly></td>"// value='�˱�����(��)'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[12] +"' "+ tStyle +" name=DecuRate20"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate20 +"' readonly></td>"// value='�ۿ��ʢ�'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[13] +"' "+ tStyle +" name=BranchUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tBranchUWValue +"' readonly></td>"// value='�˱�����(��)'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[14] +"' "+ tStyle +" name=DecuRate21"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate21 +"' readonly></td>"// value='�ۿ��ʢ�'
														+"		<td class=mulinetitle><input class=mulinput style='width: "+ tWidthArr[15] +"' "+ tStyle +" name=UWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tUWValue +"'></td>"// value='���鱣��'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[16] +"' "+ tStyle +" name=DecuRate3"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate3 +"' readonly></td>";// value='�ۿ��ʢ�'
								}
							} else {
								
								tInnerHTML1 +="	<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[6] +"' name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='�����������ͱ���'
													+"	<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[7] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>";// value='������������'
								
								tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[8] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tStandPrem +"' readonly></td>"// value='�ο�����'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[9] +"' name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>"// value='��������'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[10] +"' name=DecuRate1"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate1 +"' readonly></td>"// value='�ۿ��ʢ�'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[11] +"' name=SubUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tSubUWValue +"' readonly></td>"// value='�˱�����(��)'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[12] +"' name=DecuRate20"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate20 +"' readonly></td>"// value='�ۿ��ʢ�'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[13] +"' name=BranchUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tBranchUWValue +"' readonly></td>"// value='�˱�����(��)'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[14] +"' name=DecuRate21"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate21 +"' readonly></td>"// value='�ۿ��ʢ�'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[15] +"' name=UWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tUWValue +"'></td>"// value='���鱣��'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[16] +"' name=DecuRate3"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate3 +"' readonly></td>";// value='�ۿ��ʢ�'	
							}
							tInnerHTML1 +="		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[17] +"' name=InitPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tInitPrem +"' readonly></td>"// value='��ʼ����'
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[18] +"' name=ExceptYield"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptYield +"' readonly></td>"// value='����������'
													+"	</tr>";
							
						} else if (cTranProdType=="02") {//�˻���
							
							tInnerHTML1 +="	<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[6] +"' name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='�����������ͱ���'
													+"	<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[7] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>"// value='������������'
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[8] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tStandPrem +"' readonly></td>"// value='�ο�����'
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[9] +"' name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>"// value='��������'
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[10] +"' name=DecuRate1"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate1 +"' readonly></td>"// value='�ۿ��ʢ�'
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[11] +"' name=SubUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tSubUWValue +"' readonly></td>"// value='�˱�����(��)'
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[12] +"' name=DecuRate20"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate20 +"' readonly></td>"// value='�ۿ��ʢ�'
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[13] +"' name=BranchUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tBranchUWValue +"' readonly></td>"// value='�˱�����(��)'
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[14] +"' name=DecuRate21"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate21 +"' readonly></td>"// value='�ۿ��ʢ�'
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[15] +"' name=UWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tUWValue +"' readonly></td>"// value='���鱣��'
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[16] +"' name=DecuRate3"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate3 +"' readonly></td>";// value='�ۿ��ʢ�'
							if (tNeedCheckFlag&&tPlanNeedCheck&&tPlanDetailNeedCheck) {
								
								if (tInitPrem!=tOldInitPrem) {// value='��ʼ����'
									tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[17] +"' style=\"background-color:"+ tDifColor +"\" name=InitPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tInitPrem +"' readonly></td>";
								} else {
									tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[17] +"' name=InitPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tInitPrem +"' readonly></td>";
								}
								
								if (tExceptYield!=tOldExceptYield) {// value='����������'
									tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[18] +"' name=ExceptYield"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptYield +"' readonly></td>";
								} else {
									tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[18] +"' name=ExceptYield"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptYield +"' readonly></td>";// value='����������'
								}
							} else {
								
								var tStyle = "";
								if ((tNeedCheckFlag&&tPlanNeedCheck&&!tPlanDetailNeedCheck) || (tNeedCheckFlag&&!tPlanNeedCheck)) {
								
									tStyle = "style=\"background-color:  "+ tNewColor +"\"";
								}
								
								tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[17] +"' "+ tStyle +" name=InitPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tInitPrem +"' readonly></td>"
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[18] +"' "+ tStyle +" name=ExceptYield"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptYield +"' readonly></td>";// value='����������'
							}
							
							tInnerHTML1 +="</tr>";
						}							
					}
					tInnerHTML1 += "				</table>"
												+"			</td>"
												+"		</tr>"
												+"	</table>";
					if (cTranProdType=="00" || cTranProdType=="01" || cTranProdType=="03") {
						
						//modify by ZhangC 20150324 ����������Ϊ�˾�����ʱ�����水ť��չʾ
						if (cActivityID=="0800100002" || cActivityID=="0800100003") {
							
							if (tPlanType == "01") {
								
								tSQLInfo = new SqlClass();
								tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
								tSQLInfo.setSqlId("LSQuotAllDetailSql22");
								tSQLInfo.addSubPara(tQuotNo);
								tSQLInfo.addSubPara(tQuotBatNo);
								tSQLInfo.addSubPara(tSysPlanCode);
								tSQLInfo.addSubPara(tPlanCode);
								
								var tPremCalWayArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
								var tPremCalWay = tPremCalWayArr[0][0];
								if (tPremCalWay=="0") {//�����������շ�
									tInnerHTML1 += "<div align=\"right\"><input class=cssButton type=button value=\"��  ��\" onclick=\"saveAllDetail('"+ tQuotNo +"','"+ tQuotBatNo +"','"+ tSysPlanCode +"','"+ tPlanCode +"', '"+ tPlanDetailArr.length +"')\"></div>";
								} 
							} else {
								tInnerHTML1 += "<div align=\"right\"><input class=cssButton type=button value=\"��  ��\" onclick=\"saveAllDetail('"+ tQuotNo +"','"+ tQuotBatNo +"','"+ tSysPlanCode +"','"+ tPlanCode +"', '"+ tPlanDetailArr.length +"')\"></div>";
							}
						} else if (cActivityID=="0800100004") {
						
							tInnerHTML1 += "<div align=\"right\"><input class=cssButton type=button value=\"��  ��\" onclick=\"saveAllDetail('"+ tQuotNo +"','"+ tQuotBatNo +"','"+ tSysPlanCode +"','"+ tPlanCode +"', '"+ tPlanDetailArr.length +"')\"></div>";
						}
						
					}
					tInnerHTML1 += "</div>";
				}
			}
		}
	}
	return tInnerHTML1;
}


/**
 * ���۷�����ϸ
 */
function pubQuotPlanDetail(cObj, cArr, tStartNum, cQuotType, cTranProdType, cShow,cPrint,cQuotQuery) {
	
	var tWidthArr = new Array();
	tWidthArr[0] = 30;
	tWidthArr[1] = '';
	tWidthArr[2] = 150;
	tWidthArr[3] = '';
	tWidthArr[4] = 130;
	tWidthArr[5] = 200;
	tWidthArr[6] = '';
	tWidthArr[7] = 80;
	tWidthArr[8] = 150;
	tWidthArr[9] = 80;
	tWidthArr[10] = 80;

	var tInnerHTML1 = "";
	for (var i=0; i<tPlanShowRows; i++) {
		if ((tStartNum+i-1)>=RowNum) {
			//���ʱ,��ʾ�ܼ�¼��ȡ��
		} else {
			
			var tOfferListNo = cArr[tStartNum+i-1][0];
			var tSysPlanCode = cArr[tStartNum+i-1][1];
			var tPlanCode = cArr[tStartNum+i-1][2];
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
			tSQLInfo.setSqlId("LSQuotAllDetailSql10");
			tSQLInfo.addSubPara(tOfferListNo);
			tSQLInfo.addSubPara(tSysPlanCode);
			tSQLInfo.addSubPara(tPlanCode);

			var tPlanArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tPlanArr!=null) {
				var j = 0;
				var tSysPlanCode = tPlanArr[0][j++];
				var tPlanCode = tPlanArr[0][j++];
				var tPlanDesc = tPlanArr[0][j++];
				var tPlanType =  tPlanArr[0][j++];
				var tPlanTypeName =  tPlanArr[0][j++];
				var tPremCalType =  tPlanArr[0][j++];
				var tPremCalTypeName =  tPlanArr[0][j++];
				var tPlanFlag = tPlanArr[0][j++];
				var tPlanFlagName = tPlanArr[0][j++];
				var tOccupTypeFlag =  tPlanArr[0][j++];
				var tOccupTypeFlagName =  tPlanArr[0][j++];
				var tMinOccupType =  tPlanArr[0][j++];
				var tMinOccupTypeName =  tPlanArr[0][j++];
				var tMaxOccupType =  tPlanArr[0][j++];
				var tMaxOccupTypeName =  tPlanArr[0][j++];
				var tOccupType =  tPlanArr[0][j++];
				var tOccupTypeName =  tPlanArr[0][j++];
				var tOccupMidType =  tPlanArr[0][j++];
				var tOccupMidTypeName =  tPlanArr[0][j++];
				var tOccupCode =  tPlanArr[0][j++];
				var tOccupCodeName =  tPlanArr[0][j++];
				var tNumPeople =  tPlanArr[0][j++];
				var tMaleRate =  tPlanArr[0][j++];
				var tFemaleRate =  tPlanArr[0][j++];
				var tMinAge =  tPlanArr[0][j++];
				var tMaxAge =  tPlanArr[0][j++];
				var tAvgAge =  tPlanArr[0][j++];
				var tMinSalary =  tPlanArr[0][j++];
				var tMaxSalary =  tPlanArr[0][j++];
				var tAvgSalary =  tPlanArr[0][j++];
				var tSocialInsuRate =  tPlanArr[0][j++];//�μ��籣ռ��
				var tRetireRate =  tPlanArr[0][j++];//����ռ��
				var tOtherDesc = tPlanArr[0][j++];//����˵��
				var tOfferListNo = tPlanArr[0][j++];//���۵���

				tInnerHTML1 += "<table class=common><tr class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></tr>"
					+"<tr class=common>"
					+"	<td class=title colSpan=6><b>������</b>"+tPlanCode+"("+tPlanDesc+")</td>"
					+"</tr>";
				if (cTranProdType=="00" || cTranProdType=="02" || cTranProdType=="03") {//��ͨ����,�˻��ͼ���������
				
					if (tPlanType=="00" || tPlanType=="02") {
					
					//ְҵ����
					if (tOccupTypeFlag=="1") {//��ְҵ
						
						tInnerHTML1 +="<tr class=common>"
												+"	<td class=title colSpan=6><b>ְҵ���</b>"+tOccupTypeName+"&nbsp;&nbsp;<b>ְҵ�з��ࣺ</b>"+tOccupMidTypeName+"&nbsp;&nbsp;<b>���֣�</b>"+tOccupCodeName+"&nbsp;&nbsp;</td>"
												+"</tr>";
					} else if (tOccupTypeFlag=="2") {//��ְҵ
						
						tInnerHTML1 +="<tr class=common>"
												 +"	<td class=title colSpan=6><b>ְҵ���</b>"+ tMinOccupTypeName +"-"+ tMaxOccupTypeName;
												 +"</tr>";
					}
					tInnerHTML1 +="<tr class=common>"
											+"	<td class=title colSpan=6><b>������</b>"+ tNumPeople +"&nbsp;&nbsp;<b>��Ů������</b>"+ tMaleRate +":"+ tFemaleRate +"&nbsp;&nbsp;<b>������䣺</b>"+ tMinAge +"&nbsp;&nbsp;<b>������䣺</b>"+ tMaxAge +"&nbsp;&nbsp;<b>ƽ�����䣺</b>"+ tAvgAge +"&nbsp;&nbsp;</td>"
											+"</tr>"
											+"<tr class=common>"
											+"	<td class=title colSpan=6><b>�����н��</b>"+ tMinSalary +"&nbsp;&nbsp;<b>�����н��</b>"+ tMaxSalary +"&nbsp;&nbsp;<b>ƽ����н��</b>"+ tAvgSalary +"&nbsp;&nbsp;<b>�μ��籣ռ�ȣ�</b>"+ tSocialInsuRate +"&nbsp;&nbsp;<b>����ռ�ȣ�</b>"+ tRetireRate +"&nbsp;&nbsp;</td>"
											+"</tr>";
					}
				}
				
				tInnerHTML1 +="<tr class=common>"
								+"	<td class=title colSpan=6><b>����˵����</b>"+tOtherDesc+"</td>"
								+"</tr>"
								+"</table>";

				
				//��ѯ��������ϸ��Ϣ
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
				tSQLInfo.setSqlId("LSQuotAllDetailSql11");
				tSQLInfo.addSubPara(tOfferListNo);
				tSQLInfo.addSubPara(tSysPlanCode);
				tSQLInfo.addSubPara(tPlanCode);
				
				var tPlanDetailArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
				
				if (tPlanDetailArr==null) {
				
				} else {
					
					tInnerHTML1 +="<div>"	
											+"	<table class=common>"
											+"	 <tr class=common>"
											+"	 	<td text-align: left colSpan=1>"
											+"	 		<table class=muline ALIGN=left border=0 CELLSPACING=0 CELLPADDING=0>"
											+"	 			<tr>"
											+"	 				<td class=mulinetitle><input class=mulinetitle value='���' style='width: "+ tWidthArr[0] +"' readonly></td> "
											+"	 				<td class=mulinetitle style='display: none'><input class=mulinetitle value='�������Ʊ���' style='width: "+ tWidthArr[1] +"' readonly></td>"
											+"	 				<td class=mulinetitle><input class=mulinetitle value='��������' style='width: "+ tWidthArr[2] +"' readonly></td>"
											+"	 				<td class=mulinetitle style='display: none'><input class=mulinetitle value='���α���' style='width: "+ tWidthArr[3] +"' readonly></td> "
											+"	 				<td class=mulinetitle><input class=mulinetitle value='��������' style='width: "+ tWidthArr[4] +"' readonly></td> "
											+"	 				<td class=mulinetitle><input class=mulinetitle value='������������' style='width: "+ tWidthArr[5] +"' readonly></td> ";
											
					if (cTranProdType=="00" || cTranProdType=="01" || cTranProdType=="03") {//��ͨ����,�����ռ���������
					
						tInnerHTML1 +="	<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='�����������ͱ���' style='width: "+ tWidthArr[6] +"' readonly></td>"
									+"	<td class=mulinetitle style=\"display: ''\"><input class=mulinetitle value='������������' style='width: "+ tWidthArr[7] +"' readonly></td>"
									+"	<td class=mulinetitle style=\"display: ''\"><input class=mulinetitle value='��������(Ԫ)/��������/�����ۿ�' style='width: "+ tWidthArr[8] +"' readonly></td>";
						
						tInnerHTML1 +="	<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='��ʼ����' style='width: "+ tWidthArr[9] +"' readonly></td>"
									+"	<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='����������' style='width: "+ tWidthArr[10] +"' readonly></td>"
									+"	</tr>";
					} else if (cTranProdType=="02") {//�˻���
						
						tInnerHTML1 +="	<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='�����������ͱ���' style='width: "+ tWidthArr[6] +"' readonly></td>"
									+"	<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='������������' style='width: "+ tWidthArr[7] +"' readonly></td>"
									+"	<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='��������(Ԫ)/��������/�����ۿ�' style='width: "+ tWidthArr[8] +"' readonly></td>";
			
						tInnerHTML1 +="	<td class=mulinetitle style=\"display: ''\"><input class=mulinetitle value='��ʼ����' style='width: "+ tWidthArr[9] +"' readonly></td>"
									+"	<td class=mulinetitle style=\"display: ''\"><input class=mulinetitle value='����������' style='width: "+ tWidthArr[10] +"' readonly></td>"
									+"	</tr>";					}
					
					for (var k=0; k<tPlanDetailArr.length; k++) {
						
						var k1 = 0;
						var tRiskCode = tPlanDetailArr[k][k1++];
						var tRiskName = tPlanDetailArr[k][k1++];
						var tDutyCode = tPlanDetailArr[k][k1++];
						var tDutyName = tPlanDetailArr[k][k1++];
						var tAmntType = tPlanDetailArr[k][k1++];
						var tAmntTypeName = tPlanDetailArr[k][k1++];
						var tFixedAmnt = tPlanDetailArr[k][k1++];
						var tSalaryMult = tPlanDetailArr[k][k1++];
						var tMinAmnt = tPlanDetailArr[k][k1++];
						var tMaxAmnt = tPlanDetailArr[k][k1++];
						var tExceptPremType = tPlanDetailArr[k][k1++];
						var tExceptPremTypeName = tPlanDetailArr[k][k1++];
						var tExceptPrem = tPlanDetailArr[k][k1++];
						var tInitPrem = tPlanDetailArr[k][k1++];
						var tExceptYield = tPlanDetailArr[k][k1++];
						var tRelaShareFlag = tPlanDetailArr[k][k1++];
						
						var tDetailDesc = "";
						
						if (cTranProdType=="02") {//�˻��ʹ���
							
							if (tExceptYield==null || tExceptYield=="") {
								tDetailDesc =  "Ԥ��������:��;";
							} else {
								tDetailDesc += "Ԥ��������:"+ tExceptYield +";";
							}
							
						} else {
							
							tDetailDesc += "��������:"+ tAmntTypeName +";";
							if (tAmntType=="01") {
								tDetailDesc += "�̶�����(Ԫ):"+ tFixedAmnt +";";
							} else if (tAmntType=="02") {
								tDetailDesc += "��н����:"+ tSalaryMult +";";
							} else if (tAmntType=="03") {
								tDetailDesc += "��н����:"+ tSalaryMult +";��ͱ���(Ԫ):"+ tMinAmnt +";";
							} else if (tAmntType=="04") {
								tDetailDesc += "��ͱ���(Ԫ):"+ tMinAmnt +";��߱���(Ԫ):"+ tMaxAmnt +";";
							}
						}
						
						var tRelaSub = "";
						if (tRelaShareFlag=="1") {
							
							tSQLInfo = new SqlClass();
							tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
							tSQLInfo.setSqlId("LSQuotAllDetailSql20");
							tSQLInfo.addSubPara(tOfferListNo);
							tSQLInfo.addSubPara(tPlanCode);
							tSQLInfo.addSubPara(tSysPlanCode);
							tSQLInfo.addSubPara(tRiskCode);
							tSQLInfo.addSubPara(tDutyCode);
							
							var tRelaSubArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
							
							tRelaSub = "���������˱���ռ��:"+ tRelaSubArr[0][0]+";���������˱���ռ��:"+tRelaSubArr[0][1]+";";
						}
						
						
						//��ȡ�������ζ�̬������
						var tDutyArr = getDutyElementArr(tRiskCode, tDutyCode);
						if (tDutyArr==null) {//�����ζ�̬��
							tDetailDesc += tRelaSub;
						} else {
							
							var tSQLElement = getDutySQLElement(tDutyArr); 
							
							tSQLInfo = new SqlClass();
							tSQLInfo.setResourceName("g_quot.LSQuotSql");
							tSQLInfo.setSqlId("LSQuotSql42");
							tSQLInfo.addSubPara(tSQLElement);//SQL��ѯ�ֶ�
							tSQLInfo.addSubPara(tOfferListNo);
							tSQLInfo.addSubPara(tSysPlanCode);
							tSQLInfo.addSubPara(tPlanCode);
							tSQLInfo.addSubPara(tRiskCode);
							tSQLInfo.addSubPara(tDutyCode);
							
							var tDutyDetailSub = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

							tDetailDesc += getDutyDetailSub(tDutyArr, tDutyDetailSub,0);
							tDetailDesc += tRelaSub;
							
							//�����������ö�̬����
							if (tRelaShareFlag=="1") {
								if (tPlanCode.substring(0,1) == "G" ) {//��ͨ�ķ���
									
									tSQLInfo = new SqlClass();
									tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
									tSQLInfo.setSqlId("LSQuotAllDetailSql17");
									tSQLInfo.addSubPara(tSQLElement);//SQL��ѯ�ֶ�
									tSQLInfo.addSubPara(tQuotNo);
									tSQLInfo.addSubPara(tQuotBatNo);
									tSQLInfo.addSubPara(tSysPlanCode);
									tSQLInfo.addSubPara(tPlanCode);
									tSQLInfo.addSubPara(tRiskCode);
									tSQLInfo.addSubPara(tDutyCode);
								} else {
									//modify by ZhangC 20150310
									//����T��ͷ�����󷽰������֡����β����ظ���Ϊ�����ѯѯ�۱��ж�̬������Ϣ��ȥ�����������ѯ����
									tSQLInfo = new SqlClass();
									tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
									tSQLInfo.setSqlId("LSQuotAllDetailSql21");
									tSQLInfo.addSubPara(tSQLElement);//SQL��ѯ�ֶ�
									tSQLInfo.addSubPara(tQuotNo);
									tSQLInfo.addSubPara(tQuotBatNo);
									tSQLInfo.addSubPara(tRiskCode);
									tSQLInfo.addSubPara(tDutyCode);
								}
								
								var tRelaSubDetailSub = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
								tDetailDesc += getDutyDetailSub(tDutyArr, tRelaSubDetailSub,1);
							}
						}
						
						tInnerHTML1		+=" <tr>"
													+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[0] +"' value='"+ (k+1) +"' readonly></td>"// value='���' 
													+"		<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[1] +"' name=RiskCode"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tRiskCode +"' readonly></td>"//value='�������Ʊ���'
													+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[2] +"' name=RiskName"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tRiskName +"' readonly></td>"// value='��������'
													+"		<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[3] +"' name=DutyCode"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDutyCode +"' readonly></td> "// value='���α���'
													+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[4] +"' name=DutyName"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDutyName +"' readonly></td> "// value='��������'
													+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[5] +"' name=DetailDesc"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDetailDesc +"' readonly></td> "// value='������������'
						
						if (cTranProdType=="00" || cTranProdType=="01" || cTranProdType=="03") {//��ͨ����,�����ռ���������
						
							tInnerHTML1 +="	<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[6] +"' name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='�����������ͱ���'
										+"	<td class=mulinetitle style=\"display: ''\"><input class=mulcommon style='width: "+ tWidthArr[7] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>"// value='������������'
										+"	<td class=mulinetitle style=\"display: ''\"><input class=mulcommon style='width: "+ tWidthArr[8] +"' name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>"// value='��������/����/�ۿ�'
							
							tInnerHTML1 +=" <td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[9] +"' name=InitPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tInitPrem +"' readonly></td>"// value='��ʼ����'
										+"	<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[10] +"' name=ExceptYield"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptYield +"' readonly></td>"// value='����������'
										+"	</tr>";
							
						} else if (cTranProdType=="02") {//�˻���
							
							tInnerHTML1 +="	<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[6] +"' name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='�����������ͱ���'
										+"	<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[7] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>"// value='������������'
										+"	<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[8] +"' name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>"// value='��������/����/�ۿ�'
							
							tInnerHTML1 +=" <td class=mulinetitle style=\"display: ''\"><input class=mulcommon style='width: "+ tWidthArr[9] +"' name=InitPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tInitPrem +"' readonly></td>"// value='��ʼ����'
										+"	<td class=mulinetitle style=\"display: ''\"><input class=mulcommon style='width: "+ tWidthArr[10] +"' name=ExceptYield"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptYield +"' readonly></td>"// value='����������'
										+"	</tr>";
						}							
					}
					tInnerHTML1 += "				</table>"
												+"			</td>"
												+"		</tr>"
												+"	</table>";
					if (cTranProdType=="00" || cTranProdType=="01" || cTranProdType=="03") {
						
						tInnerHTML1 += "<div align=\"right\"><input class=cssButton type=button value=\"�������\" onclick=\"changDetail('"+ tOfferListNo +"','"+ tQuotNo +"','"+ tQuotBatNo +"','"+ tSysPlanCode +"','"+ tPlanCode +"')\"></div>";
					}
					tInnerHTML1 += "</div>";
				}
			}
		}
	}
	return tInnerHTML1;
}

/**
 * ��ҳ
 */
function firstPage() {
	
	OnPage = 1;
	goToPage(1);
}

/**
 * ��һҳ
 */
function previousPage() {
	
	if (OnPage-1<1) {
		alert("�Ѿ��ﵽ��ҳ");
		return false;
	}
	
	OnPage = OnPage-1;
	goToPage(OnPage);
}

/**
 * ��һҳ
 */
function nextPage() {
	
	if (OnPage+1>PageNum) {
		alert("�Ѿ��ﵽβҳ");
		return false;
	}
	
	OnPage = OnPage+1;
	goToPage(OnPage);
}

/**
 * βҳ
 */
function lastPage() {
	
	OnPage = PageNum;
	goToPage(PageNum);
}

function getDutyElementArr(cRiskCode, cDutyCode) {

	var tCalFactor;//ԭ���ӱ���
	var tFactorCode;//�ӱ��ֶ�
	var tFactorName;//��������
	var tFieldType;//�ֶ�����
	var tValueType;//ֵ����
	var tDefaultValue;//Ĭ��ֵ
	var tFieldLength;//�ֶγ���
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql18");
	tSQLInfo.addSubPara(cRiskCode);
	tSQLInfo.addSubPara(cDutyCode);
		
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	return tArr;
}

/**
 * ��ȡ���ζ�̬չʾ��,modify by songsz 20140520 ���ӱ���ֶ������������������˵�չʾ,0-�Ǹ�������,1-��������
 */
function getDutyElement(tArr, cFlag) {
	
	var tRelaFactor = "";
	var tRelaFactorName = "";
	
	if (cFlag=="1") {
	
		tRelaFactor = "Relation";
		tRelaFactorName = "��������";
	}

	var tInnerHTML0 = "<table class=common><tr class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></tr>";
	if (tArr==null) {
		//δ��ѯ������,��ʾ�޶�̬����
	} else {
		
		tInnerHTML0 = "<table class=common><tr class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></tr>";
		var tCount = 3;
		for (var i=0; i<tArr.length; i++) {
		
			if (tCount==3) {
				tInnerHTML0 += "<tr class=common>";
			}
			
			tCalFactor = tArr[i][0];
			tFactorCode = tArr[i][1];
			tFactorName = tArr[i][2];
			tFieldType = tArr[i][3];
			tValueType = tArr[i][4];
			tDefaultValue = tArr[i][5];
			tFieldLength = tArr[i][6];
			tMandatoryFlag = tArr[i][8];
			tDefalutName = tArr[i][9];
			
			if (tFieldType=="0") {//�����ֶ����ͽ��д���,0-¼���,1-������,2-����
				
				if (tValueType=="NUM") {
					//modify by ZhangC 20150320 ���������������ֶ�
					if (tCalFactor =="PerPrem") {
						tInnerHTML0 += "<td class=title id=idPerPrem1 name=idPerPrem1 style=\"display: ''\">"+ tRelaFactorName + tFactorName +"</td><td class=input id=idPerPrem2 name=idPerPrem2 style=\"display: ''\"><input class=common name=PerPrem id=PerPrem verify=\""+ tRelaFactorName + tFactorName +"|num\"";
					} else if (tCalFactor =="StandPerPrem")  {
						tInnerHTML0 += "<td class=title id=idStandPerPrem1 name=idStandPerPrem1 style=\"display: ''\">"+ tRelaFactorName + tFactorName +"</td><td class=input id=idStandPerPrem2 name=idStandPerPrem2 style=\"display: ''\"><input class=common name=StandPerPrem ";
					} else {
						tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=common name="+ tRelaFactor + tFactorCode +" verify=\""+ tRelaFactorName + tFactorName +"|num\"";
					}
					
				} else if (tValueType=="INT") {
					tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=common name="+ tRelaFactor + tFactorCode +" verify=\""+ tRelaFactorName + tFactorName +"|int\"";
				} else {
					tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=common name="+ tRelaFactor + tFactorCode ;
				}
				
				if (tMandatoryFlag=="1") {
					tInnerHTML0 += " readonly ";
				}
				//modify by ZhangC 20150320 ���������������ֶ�
				if (tCalFactor =="PerPrem" ) {
					tInnerHTML0 += " value=\""+ tDefaultValue +"\"> <span style=\"color: red;display: 'none'\" id=idStarPerPrem name=idStarPerPrem >*</span></td><td class=title id =hidden1 name=hidden1 style=\"display: 'none'\"></td><td class=input id =hidden2 name=hidden2 style=\"display: 'none'\"></td>";
				} else if (tCalFactor =="StandPerPrem") {
					tInnerHTML0 += " value=\""+ tDefaultValue +"\"> </td><td class=title id =hidden3 name=hidden3 style=\"display: 'none'\"></td><td class=input id =hidden4 name=hidden4 style=\"display: 'none'\"></td>";
				} else {
					tInnerHTML0 += " value=\""+ tDefaultValue +"\"> <span style=\"color: red\">*</span></td>";
				}
				
			} else if (tFieldType=="1") {
				if (tMandatoryFlag=="1") {
					tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=codeno name="+ tRelaFactor + tFactorCode +" value=\""+ tDefaultValue +"\" readonly><input class=codename name="+ tRelaFactor + tFactorCode +"Name value=\""+ tDefalutName +"\" readonly> <span style=\"color: red\">*</span></td>";
				} else {
					if (tCalFactor =="PremCalWay") {
						tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=codeno name=PremCalWay id=PremCalWay ondblclick=\"return showCodeList('"+ tValueType +"',[this,PremCalWayName],[0,1],null,null,null,1);\" onkeyup=\"return showCodeListKey('"+ tValueType +"',[this,PremCalWayName],[0,1],null,null,null,1)\" readonly><input class=codename name=PremCalWayName readonly> <span style=\"color: red\">*</span></td>";
					} else{
						tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=codeno name="+ tRelaFactor + tFactorCode +" value=\""+ tDefaultValue +"\" ondblclick=\"return showCodeList('"+ tValueType +"',[this,"+ tRelaFactor + tFactorCode +"Name],[0,1],null,null,null,1);\" onkeyup=\"return showCodeListKey('"+ tValueType +"',[this,"+ tRelaFactor + tFactorCode +"Name],[0,1],null,null,null,1)\" readonly><input class=codename name="+ tRelaFactor + tFactorCode +"Name value=\""+ tDefalutName +"\"  readonly> <span style=\"color: red\">*</span></td>";
					} 
				}
			} else if (tFieldType=="2") {
				
				if (tMandatoryFlag=="1") {
					tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=common name="+ tRelaFactor + tFactorCode +" readonly verify=\"'"+ tRelaFactorName + tFactorName +"'|date\"";
				} else {
					tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=\"coolDatePicker\" dateFormat=\"short\" name="+ tRelaFactor + tFactorCode +" verify=\"'"+ tRelaFactorName + tFactorName +"'|date\"";
				}
				
				tInnerHTML0 += " value=\""+ tDefaultValue +"\"> <span style=\"color: red\">*</span></td>";
			}
			
			tCount--;
			if (tCount==0) {
				tInnerHTML0 += "</tr>";
				tCount = 3;
			}
		}
		
		if (tCount!=0 && tCount!=3) {//���Ͽհ׵��ֶ�
			
			for (var i=1; i<=tCount; i++) {
				tInnerHTML0 += "<td class=title></td><td class=input></td>";
			}
			
			tInnerHTML0 += "</tr>";
		}
		
		tInnerHTML0 += "</table>";
	}
	//window.clipboardData.setData("Text",tInnerHTML0) ;
	return tInnerHTML0;
	
}

/**
 * ��ȡ���ζ�̬���踳ֵ�ֶ�SQL
 */
function getDutySQLElement(tArr) {

	var tSQLElement = "";

	if (tArr==null) {
		//δ��ѯ������,��ʾ�޶�̬����
	} else {

		for (var i=0; i<tArr.length; i++) {

			var tFactorCode = tArr[i][1];
			if (i==(tArr.length-1)) {
				tSQLElement += tFactorCode;
			} else {
				tSQLElement += tFactorCode + ",";
			}
		}
	}
		
	return tSQLElement;
}

/**
 * ��ȡ�ӱ���ϸ����
 */
function getDutyDetailSub(tDutyArr, tDutyResultArr,tFlag) {

	var tDetailSubDesc = "";
	for (var i=0; i<tDutyArr.length; i++) {
		
		var tFactorCode = tDutyArr[i][1];
		var tFactorName = tDutyArr[i][2];
		var tFieldType = tDutyArr[i][3];
		var tValueType = tDutyArr[i][4];
		
		if (tFlag=="0") {//��ͨ��̬����
			
			//modify by ZhangC 20150323 �������������������й�������� ���Ѽ��㷽ʽ���˾����� ��չʾ
			if (tFactorCode == "P17" && tDutyResultArr[0][i-1] == "0" ) {//��������Ѽ��㷽ʽΪ������
			} else if (tFactorCode == "P18") {
			} else {
				tDetailSubDesc += tFactorName+":";
			}
			
		} else if (tFlag=="1") {//�����������ö�̬����
			tDetailSubDesc += "��������" + tFactorName+":";
		}
		if (tFieldType=="1") {
		
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_quot.LSQuotSql");
			tSQLInfo.setSqlId("LSQuotSql9");
			tSQLInfo.addSubPara(tValueType);
			tSQLInfo.addSubPara(tDutyResultArr[0][i]);
			
			var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			
			tDetailSubDesc += tArr[0][0];
		} else {
			
			if (tFactorCode == "P17" && tDutyResultArr[0][i-1] == "0" ) {//��������Ѽ��㷽ʽΪ������
			} else if (tFactorCode == "P18") {
			} else {
				tDetailSubDesc += tDutyResultArr[0][i];
			}
		}
		
		if (tFactorCode == "P17" && tDutyResultArr[0][i-1] == "0" ) {//��������Ѽ��㷽ʽΪ������
		} else if (tFactorCode == "P18") {
		} else {
			tDetailSubDesc += ";";
		}
	}
	
	return tDetailSubDesc;
}

function checkDutyElement(cObj, tArr, cFlag) {
	
	var tRelaFactor = "";
	var tRelaFactorName = "";
	
	if (cFlag=="1") {
	
		tRelaFactor = "Relation";
		tRelaFactorName = "��������";
	}
	
	if (tArr==null) {
		//δ��ѯ������,��ʾ�޶�̬����
	} else {
		
		for (var i=0; i<tArr.length; i++) {
				
			//tCalFactor = tArr[i][0];
			tFactorCode = tRelaFactor + tArr[i][1];
			tFactorName = tRelaFactorName + tArr[i][2];
			tFieldType = tArr[i][3];
			tValueType = tArr[i][4];
			//tDefaultValue = tArr[i][5];
			tFieldLength = tArr[i][6];
			tValueScope = tArr[i][7];
			
			if (tFieldType=="1") {//������,��У���Ƿ�Ϊ��
			
				var tValue = document.all(tFactorCode).value;
				if (tValue==null || tValue=="") {
					alert(tFactorName+"����Ϊ�գ�");
					return false;
				}
			} else if (tFieldType=="0") {//¼���,ȡ������valutype,�������ͼ��ֶγ��Ƚ����ж�
			
				var tValue = document.all(tFactorCode).value;
				if (tValue==null || tValue=="") {
					alert(tFactorName+"����Ϊ�գ�");
					return false;
				}
				
				if (tValueType=="INT" || tValueType=="NUM") {

					var tSplitArr = tValueScope.split(",");
					
					var tReg1 = tValueScope.substr(0, 1);
					var tReg2 = tSplitArr[0].substr(1);
					var tReg3 = tSplitArr[1].substr(0,tSplitArr[1].length-1);
					var tReg4 = tValueScope.substr(tValueScope.length-1);
					
					if (tReg1=="(") {//������
						
						if (Number(tValue)<=Number(tReg2)) {
							alert(tFactorName+"Ӧ����"+ tReg2 +"��");
							return false;
						}
					} else if (tReg1=="[") {
						
						if (Number(tValue)<Number(tReg2)) {
							alert(tFactorName+"Ӧ��С��"+ tReg2 +"��");
							return false;
						}
					} else {
						alert(tFactorName+"У���쳣��");
						return false;
					}
					
					if (tReg4==")") {//������
						
						if (Number(tValue)>=Number(tReg3)) {
							alert(tFactorName+"ӦС��"+ tReg3 +"��");
							return false;
						}
					} else if (tReg4=="]") {
						
						if (Number(tValue)>Number(tReg3)) {
							alert(tFactorName+"Ӧ������"+ tReg3 +"��");
							return false;
						}
					} else {
						alert(tFactorName+"У���쳣��");
						return false;
					}
				}
				
				if (tValueType=="INT") {
					
					if (!isInteger(tValue)) {
						alert(tFactorName+"ӦΪ������");
						return false;
					}
				} else if (tValueType=="NUM") {
					
					if (!isNumeric(tValue)) {
						alert(tFactorName+"ӦΪ��Ч���֣�");
						return false;
					}
					
					//��ֹ���
					var tRegArr = tFieldLength.split(",");
					if (!checkDecimalFormat(tValue, tRegArr[0], tRegArr[1])) {
						alert(tFactorName+"����λ��Ӧ����"+ tRegArr[0] +"λ,С��λ��Ӧ����"+ tRegArr[1] +"λ��");
						return false;
					}
				}
			}
		}
	}
	
	return true;
}

/**
 * �������ťչʾ�ж�
 */
function judgeShowQuest(cQuotNo, cQuotBatNo, cActivityID) {
	
	if (cActivityID=="0800100001") {
	
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql31");
		tSQLInfo.addSubPara(cQuotNo);
		tSQLInfo.addSubPara(cQuotBatNo);
		
		var tArr =  easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr[0][0]=="0") {
			fmOther.all("enterQuest").style.display = "none";
		} else {
			fmOther.all("enterQuest").style.display = "";
		}
	} else if (cActivityID=="0800100002") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql32");
		tSQLInfo.addSubPara(cQuotNo);
		tSQLInfo.addSubPara(cQuotBatNo);
		
		var tArr =  easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr[0][0]=="0") {
			fm3.all("SubUWQuestionButton").style.display = "none";
		} else {
			fm3.all("SubUWQuestionButton").style.display = "";
		}
	} else if (cActivityID=="0800100003") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql33");
		tSQLInfo.addSubPara(cQuotNo);
		tSQLInfo.addSubPara(cQuotBatNo);
		
		var tArr =  easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr[0][0]=="0") {
			fm3.all("BranchUWQuestionButton").style.display = "none";
		} else {
			fm3.all("BranchUWQuestionButton").style.display = "";
		}
	} else if (cActivityID=="0800100004") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql34");
		tSQLInfo.addSubPara(cQuotNo);
		tSQLInfo.addSubPara(cQuotBatNo);
		
		var tArr =  easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr[0][0]=="0") {
			fm3.all("UWQuestionButton").style.display = "none";
		} else {
			fm3.all("UWQuestionButton").style.display = "";
		}
	}
}

/**
 * ��Ŀ¼
 */
function ReturnList(cQuotType) {
	
	if (tActivityID=="0800100001") {
		
		if (tQuotType==tETQuotType) {
			location.href = "./LSQuotETQueryInput.jsp";
		} else if (tQuotType==tProjQuotType) {
			location.href = "./LSQuotProjQueryInput.jsp";
		}
	} else if (tActivityID=="0800100002") {
		
		location.href = "./LSQuotSubUWQueryInput.jsp";
	} else if (tActivityID=="0800100003") {
		
		location.href = "./LSQuotBranchUWQueryInput.jsp";
	} else if (tActivityID=="0800100004") {
		
		location.href = "./LSQuotUWQueryInput.jsp";
	} else if (tActivityID=="0800100005") {
		
		location.href = "./LSQuotUWManagerQueryInput.jsp";
	} else if (tActivityID=="0800100006") {
		
		location.href = "./LSQuotBranchFinalQueryInput.jsp";
	} else if (tActivityID=="0800100007") {
		
		location.href = "./LSQuotSubFinalQueryInput.jsp";
	}
}

/**
 * ���֤������
 */
function checkidtype(cObj) {
	
	if (cObj.IDType.value=="") {
		alert("����ѡ��֤�����ͣ�");
		cObj.IDNo.value="";
	}
}

/**
 * �������֤��ȡ�ó������ں��Ա�
 */
function getBirthdaySexByIDNo(iIdNo,cObj) {

	try {
		if (document.all('IDType').value=="11") {
			cObj.Gender.value = "";
			cObj.GenderName.value = "";
			cObj.Birthday.value = "";
			if (iIdNo!=null&&iIdNo!="") {
				if (isNumeric(iIdNo.substring(0,(iIdNo.length-1)))==false) {
					alert("���֤����ֻ�������֣�");
					return false;
				}
	    
				if (iIdNo.length!=18&&iIdNo.length!=15) {
					alert("���֤����ֻ��Ϊ15��18λ��");
					return false;
				}
				if (iIdNo.length==18) {
					if (iIdNo.substring(10,12)>12) {
						alert("���֤�д���������ڵ�������д����");
						return false;
					}
				}
				if (iIdNo.length==18) {
					if (iIdNo.substring(10,12)<=00) {
						alert("���֤�д���������ڵ�������д����");
						return false;
					}
				}
					
				if (iIdNo.length==18) {
					if (iIdNo.substring(10,12)=="12"||iIdNo.substring(10,12)=="01"||iIdNo.substring(10,12)=="03"||iIdNo.substring(10,12)=="05"||iIdNo.substring(10,12)=="07"||iIdNo.substring(10,12)=="08"||iIdNo.substring(10,12)=="10") {
	    				if (iIdNo.substring(12,14)>31) {
	    					alert("���֤�д���������ڵ�������д����");
	    					return false;
	    				}
	    			}
	    		
					if (iIdNo.substring(10,12)=="04"||iIdNo.substring(10,12)=="06"||iIdNo.substring(10,12)=="09"||iIdNo.substring(10,12)=="11")	{
						if (iIdNo.substring(12,14)>30) {
	    					alert("���֤�д���������ڵ�������д����");
	    					return false;
						}
					}
	
					if (iIdNo.substring(10,12)=="02") {
						if (iIdNo.substring(6,10)%4!=0) {
							if(iIdNo.substring(12,14)>28) {
	    						alert("���֤�д���������ڵ�������д����");
	    						return false;
							}
	     				}
						if (iIdNo.substring(6,10)%4==0)	{
							if(iIdNo.substring(12,14)>29) {
								alert("���֤�д���������ڵ�������д����");
								return false;
							}
						}
					}
	    
					//��������1900��ļ��
					if (parseInt(iIdNo.substring(6,10),10)<1900 || parseInt(iIdNo.substring(6,10),10)>2100 ) {
						alert("�������ڸ�ʽ���ԣ��뷵�ؼ��");
						return false;
					}
				}			
				// ���ڳ�������Ϊ1980-08-00���ƵĴ���û��У��
				if(iIdNo.substring(12,14)<1) {
					alert("���֤�д���������ڵ�������д����");
					cObj.IDNo.className = "warn";
					return false;
				}
	  		}
	  
			if (iIdNo.length==15) {
				if(iIdNo.substring(8,10)>12){
					alert("���֤�д������������д����");
					return false;
	    		}
			}
			
				if (iIdNo.length==15) {
				if(iIdNo.substring(8,10)<=00){
					alert("���֤�д������������д����");
					return false;
	    		}
			}
	  
			if (iIdNo.length==15) {
		  		//�ж�����
				if (iIdNo.substring(8,10)=="12"||iIdNo.substring(8,10)=="01"||iIdNo.substring(8,10)=="03"||iIdNo.substring(8,10)=="05"||iIdNo.substring(8,10)=="07"||iIdNo.substring(8,10)=="08"||iIdNo.substring(8,10)=="10") {
		  			if (iIdNo.substring(10,12)>31) {
		  				alert("���֤�д���������ڵ�������д����");
		  				return false;
		    		}
		    	}
				if (iIdNo.substring(8,10)=="04"||iIdNo.substring(8,10)=="06"||iIdNo.substring(8,10)=="09"||iIdNo.substring(8,10)=="11") {
		    		if(iIdNo.substring(10,12)>30) {
		    			alert("���֤�д���������ڵ�������д����");
		    			return false;
		    		}
		    	}
				if (iIdNo.substring(8,10)=="02") {
		    		if(19+(iIdNo.substring(6,8))%4!=0) {
		    			if(iIdNo.substring(10,12)>28) {
		    				alert("���֤�д���������ڵ�������д����");
		    				return false;
		    			}
		     		}
					if (19+(iIdNo.substring(6,8)+1900)%4==0) {
						if (iIdNo.substring(10,12)>29) {
							alert("���֤�д���������ڵ�������д����");
							return false;
		    			}
		     		}
		    	}
			    var tmpStr= "19" + iIdNo.substring(6,8); //��������1900��ļ��
				if (parseInt(tmpStr.substring(0,4),10)<1900 || parseInt(tmpStr.substring(0,4),10)>2100 ) {
					alert("�������ڸ�ʽ���ԣ��뷵�ؼ��");
					return false;
				}
				 //	15λʱ���ֵ�У��
				var NUM="0123456789";
				var i;
				for (i=0;i<iIdNo.length;i++) {
						if(NUM.indexOf(iIdNo.charAt(i))<0) {
							alert("���֤Ϊ15λʱֻ�������֣�");
							return false;
					}
				}
				// ���ڳ�������Ϊ1980-08-00���ƵĴ���û��У��
				if (iIdNo.substring(10,12)<1) {			
			    	alert("���֤�д���������ڵ�������д����");
			    	cObj.IDNo.className = "warn";
			    	return false;
			    }
			}

			if (trim(iIdNo).length==18) {
				var sex;
				var sexq;
				var birthday;
				birthday=trim(iIdNo).substring(6,10)+"-"+trim(iIdNo).substring(10,12)+"-"+trim(iIdNo).substring(12,14);
				cObj.Birthday.value=birthday;
				sex=trim(iIdNo).substring(16,17)
				if (sex%2==1){
					sexq='1';
				} else {
					sexq='2';
				}						
				if (sexq=='1') {
					cObj.Gender.value='0';
					cObj.GenderName.value = '��';
				} else if(sexq =='2') {
					cObj.Gender.value='1';
					cObj.GenderName.value = 'Ů';
				}
				var tAge = calAge(birthday);
				cObj.Age.value=tAge;
			}
			if (trim(iIdNo).length==15) {
				var sex;
				var sexq;
				var birthday;
				birthday="19"+trim(iIdNo).substring(6,8)+"-"+trim(iIdNo).substring(8,10)+"-"+trim(iIdNo).substring(10,12);	
				cObj.Birthday.value=birthday;	
				sex=trim(iIdNo).substring(14,15)
				if (sex%2==1) {
					sexq='1';
				} else {
					sexq='2';
				}			
				if (sexq=='1') {
					cObj.Gender.value='0';
					cObj.GenderName.value = '��';
				} else if(sexq =='2') {
					cObj.Gender.value='1';
					cObj.GenderName.value = 'Ů';
				}	
				var tAge = calAge(birthday);
				cObj.Age.value=tAge;
			}
		}
	}catch(ex) {
	}
}
