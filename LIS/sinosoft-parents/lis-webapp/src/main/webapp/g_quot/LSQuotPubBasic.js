/***************************************************************
 * <p>ProName��LSQuotPubBasic.js</p>
 * <p>Title��������Ϣ���÷���</p>
 * <p>Description��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
/**
 * ��̬����չʾ-���ϵȼ�����
 */
function showPlanDiv(cFlag) {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql15");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	var tElementCode;//���ӱ���
	var tElementName;//��������
	var tControlFlag;//�Ƿ���¼���
	var tIsSelected;//����quotno���жϸ�����ֵ�Ƿ���ѯ����Լ����
	var tOElementValue;//ԭʼֵ
	var tNElementValue;//ѯ��ֵ
	
	var tDisabledFlag = "";
	if (cFlag=="1") {
		tDisabledFlag = "disabled";
	}
	
	//var tInnerHTML0 = "<table class=common><tr class=common style='display:none'><td class=input></td><td class=input></td><td class=input></td><td class=input></td></tr>";
	var tInnerHTML0 = "<table class=common><tr class=common><td class=title></td><td class=input><td class=title></td><td class=input><td class=title></td><td class=input></td></tr>";
	var tInnerHTML1 = "<tr class=common><td class=title>���ϲ㼶���ֱ�׼<span style='color: red'>*</span></td><td class=input colspan=5>";
	
	for (var i=0; i<tArr.length; i++) {
	
		tElementCode = tArr[i][0];
		tElementName = tArr[i][1];
		tControlFlag = tArr[i][2];
		tIsSelected = tArr[i][3];//�����ж��Ƿ�ѡ��
		tOElementValue = tArr[i][4];//ԭʼֵ
		tNElementValue = tArr[i][5];//ѯ��ֵ
		
		tInnerHTML1 += "<input type=checkbox id="+ tElementCode + " name="+ tElementCode + " "+ tDisabledFlag;
		
		if (tControlFlag=='1') {//����¼���
			
			if (tIsSelected=='0') {//ѯ�۱���û�б��������
			
				tInnerHTML1 += " onclick=\"showHiddenInput('"+ tElementCode +"')\">"+ tElementName;
				tInnerHTML1 += "<input type=hidden name=Hidden"+ tElementCode +" value="+ tControlFlag +"><input style='width:100px' class=common style=\"display: 'none'\" id="+ tElementCode +"Value name="+ tElementCode +"Value value=\""+ tNElementValue +"\" "+ tDisabledFlag +">";	
			} else {//ѯ���б����˸�����
				
				tInnerHTML1 += " onclick=\"showHiddenInput('"+ tElementCode +"')\" checked>"+ tElementName;
				tInnerHTML1 += "<input type=hidden name=Hidden"+ tElementCode +" value="+ tControlFlag +"><input style='width:100px' class=common id="+ tElementCode +"Value name="+ tElementCode +"Value value=\""+ tNElementValue +"\" "+ tDisabledFlag +">";	
			}
		} else {
			
			if (tIsSelected=='0') {//ѯ�۱���û�б��������
    	
				tInnerHTML1 += ">"+ tElementName;
			} else {//ѯ���б����˸�����
    	
				tInnerHTML1 += " checked>"+ tElementName;
			}
			
			tInnerHTML1 += "<input type=hidden name=Hidden"+ tElementCode +" value=0>";	
		}
	}
	
	tInnerHTML1 += "</td></tr>";
	tInnerHTML0 = tInnerHTML0+tInnerHTML1;
	
	return tInnerHTML0;
}

function showHiddenInput(o) {
	
	if (document.getElementById(o).checked == true) {
		
	   document.getElementById(o+"Value").style.display = "";
	} else {
		document.getElementById(o+"Value").style.display = "none";
	}
}

/**
 * �ύǰ��У��-�ȼ�����
 */
function checkElements() {
	
	var arrAll;
	arrAll = document.getElementById("divPlanDiv").getElementsByTagName("input");

	var tConfigCount = 0;
	for (var i=0; i<arrAll.length; i++) {
		
		var obj = arrAll[i];
		if (obj.type == "checkbox") {

			if(obj.checked) {
				//�����ѡ����,ȡѡ�е�ֵ���������Ƿ���Ҫ¼��

				var tNeedInput = eval("fm2.Hidden"+ obj.name +".value");
      
				if (tNeedInput=='1') {//��Ҫ��ֵ
					
					var tInputValue = eval("fm2."+ obj.name +"Value.value");
					
					if (tInputValue==null || tInputValue=='') {
						alert("\"���ϲ㼶���ֱ�׼\"�ѹ�ѡ\"����\"����¼�������Ϣ��");
						return false;
					} else {
						
						if (tInputValue.length>30) {
							alert("\"���ϲ㼶���ֱ�׼\"��\"����\"��������Ϣ��Ӧ����30�ֳ���");
							return false;
						}
					}
				}
				tConfigCount++;
			}
		}
	}
	if (tConfigCount=="0") {
		alert("��ѡ���ϲ㼶���ֱ�׼��");
		return false;
	}

	return true;
}

/**
 * ��̬����չʾ-��������
 */
function showSaleChnlDiv(cFlag) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotProjBasicSql");
	tSQLInfo.setSqlId("LSQuotProjBasicSql4");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tArrChnl = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	var tElementCode;//���ӱ���
	var tElementName;//��������
	var tIsSelected;//�Ƿ�ѡ��
	var tControlFlag;
	
	var tDisabledFlag = "";
	if (cFlag=="1") {
		tDisabledFlag = "disabled";
	}
	
	var tInnerHTML0 = "<table class=common><tr class=common><td class=title></td><td class=input><td class=title></td><td class=input><td class=title></td><td class=input></td></tr>";
	var tInnerHTML1 = "<tr class=common><td class=title>��������<span style='color: red'>*</span>";
	if (cFlag!="1") {
		tInnerHTML1 +="<input type=checkbox name=CheckAll id=\"CheckAll\" onclick=\"checkAll();\">ȫѡ";
	}
	tInnerHTML1 +="</td><td class=input colspan=5>";
	
	for (var i=0; i<tArrChnl.length; i++) {
	
		tElementCode = tArrChnl[i][0];
		tElementName = tArrChnl[i][1];
		tIsSelected = tArrChnl[i][2];
		tControlFlag = tArrChnl[i][3];
		
		tInnerHTML1 += "<input type=checkbox name="+ tElementCode+" "+ tDisabledFlag +" onclick=\"showIfAgencyName();\"";
		if (tIsSelected=='0') {//ѯ�۱���û�б��������
			tInnerHTML1 += ">"+ tElementName;
		} else {//ѯ���б����˸�����

			tInnerHTML1 += " checked>"+ tElementName;
		}
		var tFlag1 = tControlFlag.substring(0,1);
		tInnerHTML1 += "<input type=hidden name="+ tElementCode +"Flag value="+ tFlag1 +">";
		
	}
	tInnerHTML1 += "</td></tr>";
	tInnerHTML0 = tInnerHTML0+tInnerHTML1;
	
	return tInnerHTML0;
}

function checkAll() {
	
	var arrAll;
	arrAll = document.getElementById("divSaleChnlDiv").getElementsByTagName("input");
	var tConfigCount = 0;
	var tFlag = document.getElementById("CheckAll").checked;
	for (var i=0; i<arrAll.length; i++) {
		
		var obj = arrAll[i];
		
		if (obj.type == "checkbox") {

			if (obj.name!="CheckAll") {
				
				if (tFlag) {
					if(obj.checked) {
						
					} else {
						
						obj.checked = true;
					}
				} else {
					if(obj.checked) {
						obj.checked = false;
					} else {
						
					}
				}
			}
		}
	}
	showIfAgencyName();
}
/**
 * �Ƿ�չʾ�н�����
 */
function pubShowAgencyInfoCheck(cObj) {
	
	var tArr = document.all("divSaleChnlDiv").getElementsByTagName("input");
	
	var tFlag = false;//Ĭ�ϲ�չʾ�н�����
	for (var i=0; i<tArr.length; i++) {
		
		if (tFlag==false) {
			var tField = tArr[i];
			
			if (tField.type=="checkbox") {//ֻ�и��ֶ�Ϊcheckboxʱ,�Ž��д���
				
				if (tField.name!="CheckAll") {
					
					if (tField.checked) {//ѡ��ʱ,����ȡ��־ֵ,ȡ����־ֵΪ1ʱ,չʾ״��˵��
						
						var tCheckValue = eval(cObj.name+"."+ tField.name +"Flag.value");
						if (tCheckValue=="1") {
							tFlag = true;
						}	
					}
				}
			}
		}
	}
	
	if (tFlag==true) {
		document.all("divAgencyInfo").style.display = "";
		showAgencyInfo();
	} else {
		document.all("divAgencyInfo").style.display = "none";
	}
}

/**
 * У�����������Ƿ�ѡ��
 */
function checkSaleChnl() {
	
	var arrAll;
	arrAll = document.getElementById("divSaleChnlDiv").getElementsByTagName("input");

	var tConfigCount = 0;
	for (var i=0; i<arrAll.length; i++) {
		
		var obj = arrAll[i];
		
		if (obj.type == "checkbox") {

			if (obj.name!="CheckAll") {
				
				if(obj.checked) {
				      
					tConfigCount++;
				}
			}
		}
	}
	if (tConfigCount=="0") {
		alert("��ѡ������������");
		return false;
	}

	return true;
}

/**
 * ��̬����չʾ-�ɷѷ�ʽ
 */
function showPayIntvDiv(cFlag) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotProjBasicSql");
	tSQLInfo.setSqlId("LSQuotProjBasicSql2");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tArrPayIntv = easyExecSql(tSQLInfo.getString());
	
	var tElementCode;//���ӱ���
	var tElementName;//��������
	var tIsSelected;//�Ƿ�ѡ��
	var tPayIntv;//�ɷѷ�ʽ
	
	var tDisabledFlag = "";
	if (cFlag=="1") {
		tDisabledFlag = "disabled";
	}
	
	var tInnerHTML0 = "<table class=common><tr class=common><td class=title></td><td class=input><td class=title></td><td class=input><td class=title></td><td class=input></td></tr>";
	var tInnerHTML1 = "<tr class=common><td class=title>�ɷѷ�ʽ<span style='color: red'>*</span></td><td class=input colspan=5>";
	
	for (var i=0; i<tArrPayIntv.length; i++) {
	
		tElementCode = tArrPayIntv[i][0];
		tElementName = tArrPayIntv[i][1];
		tIsSelected = tArrPayIntv[i][2];
		tPayIntv = tArrPayIntv[i][3];
		
		tInnerHTML1 += "<input type=checkbox name="+ tElementCode +" "+ tDisabledFlag;
		if (tIsSelected=='0') {//ѯ�۱���û�б��������
			tInnerHTML1 += ">"+ tElementName;
		} else {//ѯ���б����˸�����

			tInnerHTML1 += " checked>"+ tElementName;
		}
	}

	tInnerHTML1 += "</td></tr>";
	tInnerHTML0 = tInnerHTML0+tInnerHTML1;
	
	return tInnerHTML0;
}

/**
 * У��ɷѷ�ʽ�Ƿ�ѡ��
 */
function checkPayIntv() {
	
	var arrAll;
	arrAll = document.getElementById("divPayIntvDiv").getElementsByTagName("input");

	var tConfigCount = 0;
	for (var i=0; i<arrAll.length; i++) {
		
		var obj = arrAll[i];
		if (obj.type == "checkbox") {

			if(obj.checked) {
      
				tConfigCount++;
			}
		}
	}
	if (tConfigCount=="0") {
		alert("��ѡ��ɷѷ�ʽ��");
		return false;
	}

	return true;
}

/**
 * ��ȡѯ�۷�¼�뻷�ڹ���������
 */
function getNotInputCount(cQuotNo, cQuotBatNo) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql29");
	tSQLInfo.addSubPara(cQuotNo);
	tSQLInfo.addSubPara(cQuotBatNo);
	
	var tCountArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tCountArr==null || tCountArr[0][0]=="") {
		return "0";
	}
	
	return tCountArr[0][0];
}
