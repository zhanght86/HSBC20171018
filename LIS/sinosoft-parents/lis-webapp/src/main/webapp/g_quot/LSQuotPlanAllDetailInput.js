/***************************************************************
 * <p>ProName��LSQuotPlanAllDetailInput.js</p>
 * <p>Title��������ϸһ��</p>
 * <p>Description��������ϸһ��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-05-06
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * ���淽��������Ϣ
 */
function saveAllDetail(cQuotNo, cQuotBatNo, cSysPlanCode, cPlanCode, cDetailRows) {

	//�ύǰУ��
	if (!beforeSubmit(cQuotNo, cQuotBatNo, cSysPlanCode, cPlanCode, cDetailRows)) {
		return false;
	}
	fm.encoding = "application/x-www-form-urlencoded";
	fm.action = "./LSQuotPlanAllDetailSave.jsp?Operate=ADDPLANDETAIL&QuotNo="+ cQuotNo +"&QuotBatNo="+ cQuotBatNo +"&QuotType="+ tQuotType +"&SysPlanCode="+ cSysPlanCode +"&PlanCode="+ cPlanCode +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&TranProdType="+ tTranProdType +"&DetailRows="+ cDetailRows;
	submitForm(fm);
}

/**
 * �ύ����
 */
function submitForm(obj) {

	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	obj.submit();
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
		if (mOperate == "EXPTEMPLATE" || mOperate == "EXPDETAIL") {
			
			var tFileName = content.substring(content.lastIndexOf("/")+1);
			
			window.location = "../common/jsp/download.jsp?FilePath="+content+"&FileName="+tFileName;
			
		} else {
			
			if (mOperate=="UPLOAD") {
				
				document.all('UploadPath').outerHTML = document.all('UploadPath').outerHTML;
			}
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
		
		goToPage(OnPage);
	}	
}

function showAllPlanDetail(cObj, cArr, tStartNum, cQuotType, cTranProdType, cActivityID) {
	
	cObj.PageInfo.value = "��"+OnPage+"/"+PageNum+"ҳ";
	document.all("divShowAllPlan").innerHTML = pubShowAllPlanDetail(cObj, cArr, tStartNum, cQuotType, cTranProdType, cActivityID);
}

/**
 * չʾָ��ҳ
 */
function goToPage(OnPage) {
	
	tStartNum = (OnPage-1)*tPlanShowRows+1;
	showAllPlanDetail(fm, strQueryResult,tStartNum, tQuotType, tTranProdType, tActivityID);
}

/**
 *
 */
function beforeSubmit(cQuotNo, cQuotBatNo, cSysPlanCode, cPlanCode, cDetailRows) {
	
	/**
	 * ��ȡ��¼�������ݿ⵱ǰ��¼�����бȶ�
	 * ��ȡ����ֵ����ݹ������ڵ㼰����ֵ���ͽ����ж�ֵ�Ƿ���ȷ
	 */
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
	tSQLInfo.setSqlId("LSQuotAllDetailSql8");
	tSQLInfo.addSubPara(cQuotNo);
	tSQLInfo.addSubPara(cQuotBatNo);
	tSQLInfo.addSubPara(cSysPlanCode);
	tSQLInfo.addSubPara(cPlanCode);
	
	var tPlanDetailArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPlanDetailArr==null) {
		alert("��ȡԭ������ϸʧ�ܣ�");
		return false;
	}
	
	if (tPlanDetailArr.length!=cDetailRows) {//�Ƚ������Ƿ�һ��
		alert("�÷�����ϸ��Ϣ�����ı䣬��ˢ��ҳ����ٽ��иò�����");
		return false;
	}
	
	for (var i=0; i<tPlanDetailArr.length; i++) {
		
		var tRiskCode = tPlanDetailArr[i][0];
		var tRiskName = tPlanDetailArr[i][1];
		var tDutyCode = tPlanDetailArr[i][2];
		var tDutyName = tPlanDetailArr[i][3];
		var tExceptPremType = tPlanDetailArr[i][4];
		var tExceptPremTypeName = tPlanDetailArr[i][5];
		var tStandValue = tPlanDetailArr[i][6];
		var tExceptValue = tPlanDetailArr[i][7];
		var tSubUWValue = tPlanDetailArr[i][8];
		var tBranchUWValue = tPlanDetailArr[i][9];
		var tUWValue = tPlanDetailArr[i][10];
		
		var tDutyCode1 = document.all("DutyCode"+ tRiskCode + tDutyCode + cSysPlanCode).value;
		if (tDutyCode1==null || tDutyCode1=="") {
			alert("�÷�����ϸ��Ϣ�����ı䣬��ˢ��ҳ����ٽ��иò�����");
			return false;
		}
		
		var tExceptPremType = document.all("ExceptPremType"+ tRiskCode + tDutyCode + cSysPlanCode).value;
		if (tExceptPremType==null || tExceptPremType=="") {
			alert("�÷�����ϸ��Ϣ�����ı䣬��ˢ��ҳ����ٽ��иò�����");
			return false;
		}
		var tValue = "";
		if (tActivityID=="0800100002") {//�к��޸�
			
			tValue = 	document.all("SubUWValue"+ tRiskCode + tDutyCode + cSysPlanCode).value;
			if (tValue==null || tValue=="") {
				
				alert("��"+ (i+1) +"�к˱�����(��)����Ϊ�գ�");
				return false;
			}
			
			if (!isNumeric(tValue) || Number(tValue)<0) {
				
				alert("��"+ (i+1) +"�к˱�����(��)ӦΪ��С��0����Ч���֣�");
				return false;
			}
			
			//�������Ͷ�ֵ����У��
			if (tExceptPremType=="01") {//����,12,2
			
				if (!checkDecimalFormat(tValue, 12, 2)) {
					
					alert("�����������ѽ���ѯ��ʱ���˱�����(��)����λ��Ӧ����12λ,С��λ��Ӧ����2λ,���޸ĵ�"+ (i+1) +"�к˱�����(��)��");
					return false;
				}
			} else if (tExceptPremType=="02") {//����
				
				if (!checkDecimalFormat(tValue, 4, 8)) {
					
					alert("�����ڷ��ʽ���ѯ��ʱ���˱�����(��)����λ��Ӧ����4λ,С��λ��Ӧ����8λ,���޸ĵ�"+ (i+1) +"�к˱�����(��)��");
					return false;
				}
			} else if (tExceptPremType=="03") {//�ۿ�
				
				if (!checkDecimalFormat(tValue, 2, 2)) {
					
					alert("���Է����ۿ۽���ѯ��ʱ���˱�����(��)����λ��Ӧ����2λ,С��λ��Ӧ����2λ,���޸ĵ�"+ (i+1) +"�к˱�����(��)��");
					return false;
				}
			}
		} else if (tActivityID=="0800100003") {//�ֺ��޸�
		
			tValue = 	document.all("BranchUWValue"+ tRiskCode + tDutyCode + cSysPlanCode).value;
			if (tValue==null || tValue=="") {
				
				alert("��"+ (i+1) +"�к˱�����(��)����Ϊ�գ�");
				return false;
			}
			
			if (!isNumeric(tValue) || Number(tValue)<0) {
				
				alert("��"+ (i+1) +"�к˱�����(��)ӦΪ��С��0����Ч���֣�");
				return false;
			}
			
			//�������Ͷ�ֵ����У��
			if (tExceptPremType=="01") {//����,12,2
			
				if (!checkDecimalFormat(tValue, 12, 2)) {
					
					alert("�����������ѽ���ѯ��ʱ���˱�����(��)����λ��Ӧ����12λ,С��λ��Ӧ����2λ,���޸ĵ�"+ (i+1) +"�к˱�����(��)��");
					return false;
				}
			} else if (tExceptPremType=="02") {//����
				
				if (!checkDecimalFormat(tValue, 4, 8)) {
					
					alert("�����������ʽ���ѯ��ʱ���˱�����(��)����λ��Ӧ����4λ,С��λ��Ӧ����8λ,���޸ĵ�"+ (i+1) +"�к˱�����(��)��");
					return false;
				}
			} else if (tExceptPremType=="03") {//�ۿ�
				
				if (!checkDecimalFormat(tValue, 2, 2)) {
					
					alert("���Է����ۿ۽���ѯ��ʱ���˱�����(��)����λ��Ӧ����2λ,С��λ��Ӧ����2λ,���޸ĵ�"+ (i+1) +"�к˱�����(��)��");
					return false;
				}
			}
		} else if (tActivityID=="0800100004") {//�ܺ��޸�
			
			tValue = 	document.all("UWValue"+ tRiskCode + tDutyCode + cSysPlanCode).value;
			if (tValue==null || tValue=="") {
				
				alert("��"+ (i+1) +"�н��鱣�Ѳ���Ϊ�գ�");
				return false;
			}
			
			if (!isNumeric(tValue) || Number(tValue)<0) {
				
				alert("��"+ (i+1) +"�н��鱣��ӦΪ��С��0����Ч���֣�");
				return false;
			}
			
			//�������Ͷ�ֵ����У��
			if (tExceptPremType=="01") {//����,12,2
			
				if (!checkDecimalFormat(tValue, 12, 2)) {
					
					alert("�����������ѽ���ѯ��ʱ�����鱣������λ��Ӧ����12λ,С��λ��Ӧ����2λ,���޸ĵ�"+ (i+1) +"�н��鱣�ѣ�");
					return false;
				}
			} else if (tExceptPremType=="02") {//����
				
				if (!checkDecimalFormat(tValue, 4, 8)) {
					
					alert("�����������ʽ���ѯ��ʱ�����鱣������λ��Ӧ����4λ,С��λ��Ӧ����8λ,���޸ĵ�"+ (i+1) +"�н��鱣�ѣ�");
					return false;
				}
			} else if (tExceptPremType=="03") {//�ۿ�
				
				if (!checkDecimalFormat(tValue, 2, 2)) {
					
					alert("���Է����ۿ۽���ѯ��ʱ�����鱣������λ��Ӧ����2λ,С��λ��Ӧ����2λ,���޸ĵ�"+ (i+1) +"�н��鱣�ѣ�");
					return false;
				}
			}
		}
	}
	
	return true;
}

/**
 * չʾ����������Ϣ
 */
function showImp() {
	
	if(divImpDetail.style.display=='none') {
		divImpDetail.style.display=''
	} else {
		divImpDetail.style.display='none'
	}
}

/**
 * ����������ϸ
 */
function expPlanDetail() {
	
	mOperate = "EXPDETAIL";
	fm.encoding = "application/x-www-form-urlencoded";
	fm.action = "./LSQuotExpDetailSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&ProdType="+ tTranProdType+"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm);
}

/**
 * ����ģ��
 */
function expImpTemplate() {
	
	mOperate = "EXPTEMPLATE";
	fm.encoding = "application/x-www-form-urlencoded";
	fm.action = "./LSQuotExpPremSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm);
}

/**
 * ���ѵ���
 */
function impPrem() {
	
	if (tQuotNo == null || tQuotNo == "") {
		alert("��ȡѯ�ۺ�ʧ�ܣ�");
		return false;
	}
	if (tQuotBatNo == null || tQuotBatNo == "") {
		alert("��ȡ���κ�ʧ�ܣ�");
		return false;
	}
	if (tQuotType == null || tQuotType == "") {
		alert("��ȡѯ������ʧ�ܣ�");
		return false;
	}
	if (tActivityID == null || tActivityID == "") {
		alert("��ȡ�������ڵ�ʧ�ܣ�");
		return false;
	}
	var tFilePath = fm.UploadPath.value;
	if(tFilePath == null || tFilePath == ""){
		alert("��ѡ�����ļ�·����");
		return false;
	}
	
	if (tFilePath!=null && tFilePath !="") {
		
		var tFileName = tFilePath.substring(tFilePath.lastIndexOf("\\")+1);
		var pattern2 = /^[^\s\+\&]+$/;
		if (!pattern2.test(tFileName)) {
			alert("�ϴ����ļ����ܺ��пո񡢡�+������&����");
			return false;
		}
		var tFileSuffix = tFilePath.substring(tFilePath.lastIndexOf("."));
		if (tFileSuffix==".xls" || tFileSuffix==".XLS" ) {
			
		} else {
			alert("��֧�ִ��ļ������ϴ���");
			return false;
		}
		
	}
	mOperate = "UPLOAD";
	fm.encoding = "multipart/form-data";
	fm.action="./LQuotImpPremSave.jsp?Operate=UPLOAD&QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&TranProdType="+tTranProdType+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitForm(fm);
}

/**
 * չʾ����������Ϣ
 */
function showImpLog() {
	
	window.open("./LSQuotShowImpPremMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo+ "&ActivityID="+ tActivityID,"ѯ�۱��ѵ�����Ϣ",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
