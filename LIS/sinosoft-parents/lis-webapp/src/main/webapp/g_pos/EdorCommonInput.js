/***************************************************************
 * <p>ProName��EdorCommonInput.js</p>
 * <p>Title����ȫ��������</p>
 * <p>Description����ȫ��������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-06-12
 ****************************************************************/
 
var tPlanShowRows = 10;
/**
 * Ӱ�����ѯ
 */
function queryScanPage() {
	window.open("../g_easyscan/ScanPagesQueryMainInput.jsp?BussType=G_POS&BussNo="+tEdorAppNo,null , 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * ���������
 */
function goToQuestion() {
	
	window.open("../g_busicommon/LDQuestionMain.jsp?OtherNoType=POS&OtherNo="+tEdorAppNo+"&ActivityID="+tActivityID+"&ShowStyle=2","���������",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * ��������
 */
function showAttachment() {
	
	window.open("../g_busicommon/LDAttachmentMain.jsp?OtherNoType=POS&OtherNo="+tEdorAppNo+"&UploadNode="+tActivityID,"��������",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * ��Ա�嵥����
 */
function edorInsuredList() {
	
	window.open("./EdorInsuredDealMain.jsp?MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&EdorAppNo="+tEdorAppNo,"��Ա�嵥����",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * ��Լ��Ϣ
 */
function queryGrpSpec() {
	
	var tPolicyNo = fm.PolicyNo.value;
	window.open("./GrpSpecMain.jsp?MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&GrpContNo="+tPolicyNo,"��Լ��Ϣ",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * ���շ�����ѯ
 */
function gradeQuery() {
	window.open("../g_app/LCContPlanQueryMain.jsp?PolicyNo="+tPolicyNo ,"������ѯ",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
}
/**
 * ����
 */
function returnClick() {
	
	if (tActivityID=="1800401001") {
		location.href = "./EdorAcceptQueryInput.jsp";
	} else if (tActivityID=="1800401002") {
		location.href = "./EdorInputQueryInput.jsp";
	} else if (tActivityID=="1800401003") {
		location.href = "./EdorCheckQueryInput.jsp";
	} else if (tActivityID=="1800401004") {
		
		location.href = "./EdorUWQueryInput.jsp";
	} else if (tActivityID=="1800401005") {
		
		location.href = "./EdorAuditQueryInput.jsp";
	}
}

/**
 * �Զ���codename��ֵ
 */
function auotContShowCodeName(cCodeType, cCode, cObj, cCodeName) {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorCommonSql");
	tSQLInfo.setSqlId("EdorCommonSql3");
	tSQLInfo.addSubPara(cCodeType);
	tSQLInfo.addSubPara(cCode);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null) {

	} else {
		document.all(cCodeName).value = tArr[0][0];
	}
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
չʾ��ѯ���,��ֵ��Muline
**/
function showMulLineInfo(tResultStr, objGrid ,objPage){
	objPage.strQueryResult = tResultStr;
	if(objPage.strQueryResult==null||objPage.strQueryResult=="") {
		
		//initExeTrendsGrid();
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	}
	
	objPage.decodeEasyQueryResult(objPage.strQueryResult,'0');
	objPage.useSimulation = 1;
	objPage.arrDataCacheSet = decodeEasyQueryResult(turnPage1.strQueryResult, 0, 0, objPage);
	//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	
	objPage.pageDisplayGrid = objGrid;
	//objGrid.SortPage = objPage;��ַҳ��ʶ
	//���ò�ѯ��ʼλ��
	objPage.pageIndex = 0;
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	var arrDataSet = turnPage1.getData(objPage.arrDataCacheSet, objPage.pageIndex, 10);
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(arrDataSet, objPage.pageDisplayGrid, objPage);
	objGrid.setPageMark(objPage);
	 
	return true;
	
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
	tSQLInfo.setResourceName("g_app.LCContPlanTradSql");
	tSQLInfo.setSqlId("LCContPlanTradSql13");
	tSQLInfo.addSubPara(cRiskCode);
	tSQLInfo.addSubPara(cDutyCode);
		
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	return tArr;
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
					tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=common name="+ tRelaFactor + tFactorCode +" verify=\""+ tRelaFactorName + tFactorName +"|num\"";
				} else if (tValueType=="INT") {
					tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=common name="+ tRelaFactor + tFactorCode +" verify=\""+ tRelaFactorName + tFactorName +"|int\"";
				} else {
					tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=common name="+ tRelaFactor + tFactorCode ;
				}
				
				if (tMandatoryFlag=="1") {
					tInnerHTML0 += " readonly ";
				}
				
				tInnerHTML0 += " value=\""+ tDefaultValue +"\"> <span style=\"color: red\">*</span></td>";
			} else if (tFieldType=="1") {
				
				if (tMandatoryFlag=="1") {
					tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=codeno name="+ tRelaFactor + tFactorCode +" value=\""+ tDefaultValue +"\" readonly><input class=codename name="+ tRelaFactor + tFactorCode +"Name value=\""+ tDefalutName +"\" readonly> <span style=\"color: red\">*</span></td>";
				} else {
					tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=codeno name="+ tRelaFactor + tFactorCode +" value=\""+ tDefaultValue +"\" ondblclick=\"return showCodeList('"+ tValueType +"',[this,"+ tRelaFactor + tFactorCode +"Name],[0,1],null,null,null,1);\" onkeyup=\"return showCodeListKey('"+ tValueType +"',[this,"+ tRelaFactor + tFactorCode +"Name],[0,1],null,null,null,1)\" readonly><input class=codename name="+ tRelaFactor + tFactorCode +"Name value=\""+ tDefalutName +"\"  readonly> <span style=\"color: red\">*</span></td>";
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
 * ����ҳ����
 */
function noDiv(objPage, objGrid, tSql) {
	
	//Ϊ����������ĳ����ݴ������
	objPage = new turnPageClass();
	objPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1, 1, 1);
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
 * ȥ���ո�
 */
function trimBlank(cObjFm, Cobj, CobjType) {
	if(CobjType==null || CobjType ==""){
		CobjType ="common";
	}
	if(Cobj!=null){
		if(CobjType=="common"){alert(CobjType);
			Cobj.value = trim(Cobj.value);
		}
		return true;
	}
	//����FORM�е�����ELEMENT
	for (elementsNum=0;elementsNum<cObjFm.elements.length;elementsNum++) {
		//Ԫ��У������verify��ΪNULL
		if (cObjFm.elements[elementsNum].className!=null && cObjFm.elements[elementsNum].className!="") {
			//����У��verifyElement
			if (cObjFm.elements[elementsNum].className == "common") {
				 cObjFm.elements[elementsNum].value = trim(cObjFm.elements[elementsNum].value);
			}
		}
	}
	return true;
}

/**
 * ��ȡ��Ʒ����
 */
function getContPlanType(cGrpPropNo) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorCommonSql");
	tSQLInfo.setSqlId("EdorCommonSql2");
	tSQLInfo.addSubPara(cGrpPropNo);
	var tProdArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	if (tProdArr==null) {
		return "";
	} else {
		return tProdArr[0][0];
	}
}
/**
 * ��ʼ������ϸ��ҳ��Ϣ
 */
function initContPlanDetailPageInfo(cPolicyNo,cEdorNo,cEdorType) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorCommonSql");
	tSQLInfo.setSqlId("EdorCommonSql4");
	tSQLInfo.addSubPara(cPolicyNo);
	tSQLInfo.addSubPara(cEdorNo);
	tSQLInfo.addSubPara(cEdorType);
	
	strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	if(strQueryResult==null){
		return false;
	}
	RowNum = strQueryResult.length;
	
	//������ܵ�ҳ��TotalPage
	if (RowNum%tPlanShowRows==0) {
		PageNum = parseInt(RowNum/tPlanShowRows);
	} else {
		PageNum = parseInt(RowNum/tPlanShowRows)+1;
	}
	return true;
}
/**
 * ������ϸ
 */
function pubContPlanDetail(cObj, cArr, tStartNum, cContPlanType) {
	
	var tWidthArr = new Array();
	tWidthArr[0] = 30;
	tWidthArr[1] = 0;
	tWidthArr[2] = 150;
	tWidthArr[3] = 0;
	tWidthArr[4] = 130;
	tWidthArr[5] = 300;
	tWidthArr[6] = 0;
	tWidthArr[7] = 80;
	tWidthArr[8] = 150;
	tWidthArr[9] = 80;
	tWidthArr[10] = 80;

	var tInnerHTML1 = "";
	for (var i=0; i<tPlanShowRows; i++) {
		if ((tStartNum+i-1)>=RowNum) {
			//���ʱ,��ʾ�ܼ�¼��ȡ��
		} else {
			
			var tPolicyNo = cArr[tStartNum+i-1][0];
			var tSysPlanCode = cArr[tStartNum+i-1][1];
			var tPlanCode = cArr[tStartNum+i-1][2];
			var tEdorNo = cArr[tStartNum+i-1][3];
			var tEdorType = cArr[tStartNum+i-1][4];
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_pos.EdorCommonSql");
			tSQLInfo.setSqlId("EdorCommonSql5");
			tSQLInfo.addSubPara(tPolicyNo);
			tSQLInfo.addSubPara(tSysPlanCode);
			tSQLInfo.addSubPara(tPlanCode);
			tSQLInfo.addSubPara(tEdorNo);
			tSQLInfo.addSubPara(tEdorType);

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
				var tPolciyNo = tPlanArr[0][j++];//������

				tInnerHTML1 += "<table class=common><tr class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></tr>"
					+"<tr class=common>"
					+"	<td class=title colSpan=6><b>������</b>"+tPlanCode+"("+tPlanDesc+")</td>"
					+"</tr>";
				if (cContPlanType=="00" || cContPlanType=="02" || cContPlanType=="03") {//��ͨ����,�˻��ͼ���������
				
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
				tSQLInfo.setResourceName("g_pos.EdorCommonSql");
				tSQLInfo.setSqlId("EdorCommonSql6");
				tSQLInfo.addSubPara(tPolicyNo);
				tSQLInfo.addSubPara(tSysPlanCode);
				tSQLInfo.addSubPara(tPlanCode);
				tSQLInfo.addSubPara(tEdorNo);
				tSQLInfo.addSubPara(tEdorType);
				
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
											
					if (cContPlanType=="00" || cContPlanType=="01" || cContPlanType=="03") {//��ͨ����,�����ռ���������
					
						tInnerHTML1 +="	<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='�����������ͱ���' style='width: "+ tWidthArr[6] +"' readonly></td>"
									+"	<td class=mulinetitle style=\"display: ''\"><input class=mulinetitle value='������������' style='width: "+ tWidthArr[7] +"' readonly></td>"
									+"	<td class=mulinetitle style=\"display: ''\"><input class=mulinetitle value='��������(Ԫ)/��������/�����ۿ�' style='width: "+ tWidthArr[8] +"' readonly></td>";
						
						tInnerHTML1 +="	<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='��ʼ����' style='width: "+ tWidthArr[9] +"' readonly></td>"
									+"	<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='����������' style='width: "+ tWidthArr[10] +"' readonly></td>"
									+"	</tr>";
					} else if (cContPlanType=="02") {//�˻���
						
						tInnerHTML1 +="	<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='�����������ͱ���' style='width: "+ tWidthArr[6] +"' readonly></td>"
									+"	<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='������������' style='width: "+ tWidthArr[7] +"' readonly></td>"
									+"	<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='��������(Ԫ)/��������/�����ۿ�' style='width: "+ tWidthArr[8] +"' readonly></td>";
			
						tInnerHTML1 +="	<td class=mulinetitle style=\"display: ''\"><input class=mulinetitle value='��ʼ����' style='width: "+ tWidthArr[9] +"' readonly></td>"
									+"	<td class=mulinetitle style=\"display: ''\"><input class=mulinetitle value='����������' style='width: "+ tWidthArr[10] +"' readonly></td>"
									+"	</tr>";}
					
					for (var k=0; k<tPlanDetailArr.length; k++) {
						
						var k1 = 0;
						var tRiskCode = tPlanDetailArr[k][k1++];
						var tRiskName = tPlanDetailArr[k][k1++];
						var tDutyCode = tPlanDetailArr[k][k1++];
						var tDutyName = tPlanDetailArr[k][k1++];
						var tDetailDesc = tPlanDetailArr[k][k1++];
						var tExceptPremType = tPlanDetailArr[k][k1++];
						var tExceptPremTypeName = tPlanDetailArr[k][k1++];
						var tExceptPrem = tPlanDetailArr[k][k1++];
						var tInitPrem = tPlanDetailArr[k][k1++];
						var tExceptYield = tPlanDetailArr[k][k1++];
						
						tInnerHTML1		+=" <tr>"
													+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[0] +"' value='"+ (k+1) +"' readonly></td>"// value='���' 
													+"		<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[1] +"' name=RiskCode"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tRiskCode +"' readonly></td>"//value='�������Ʊ���'
													+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[2] +"' name=RiskName"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tRiskName +"' readonly></td>"// value='��������'
													+"		<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[3] +"' name=DutyCode"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDutyCode +"' readonly></td> "// value='���α���'
													+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[4] +"' name=DutyName"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDutyName +"' readonly></td> "// value='��������'
													+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[5] +"' name=DetailDesc"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDetailDesc +"' readonly></td> "// value='������������'
						
						if (cContPlanType=="00" || cContPlanType=="01" || cContPlanType=="03") {//��ͨ����,�����ռ���������
						
							tInnerHTML1 +="	<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[6] +"' name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='�����������ͱ���'
										+"	<td class=mulinetitle style=\"display: ''\"><input class=mulcommon style='width: "+ tWidthArr[7] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>"// value='������������'
										+"	<td class=mulinetitle style=\"display: ''\"><input class=mulcommon style='width: "+ tWidthArr[8] +"' name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>"// value='��������/����/�ۿ�'
							
							tInnerHTML1 +=" <td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[9] +"' name=InitPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tInitPrem +"' readonly></td>"// value='��ʼ����'
										+"	<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[10] +"' name=ExceptYield"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptYield +"' readonly></td>"// value='����������'
										+"	</tr>";
							
						} else if (cContPlanType=="02") {//�˻���
							
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
					 
					tInnerHTML1 += "</div>";
				}
			}
		}
	}
	return tInnerHTML1;
}
/**
** �ļ�����
**/
function downFile(patch,fileName1){
	window.location = "../common/jsp/download.jsp?FilePath="+patch+"&FileName="+fileName1;
}

/**
 * ��ӡ
 */
function print() {
	
	if (tEdorAppNo==null || tEdorAppNo=="") {
		alert("δ���������");
		return false;
	}
	mOperate = "PRINT";
	fm.action="../g_print/EdorPrintSave.jsp?Operate="+mOperate+"&EdorAppNo="+tEdorAppNo+"&EdorNo="+tEdorNo;
	submitForm(fm);
}
