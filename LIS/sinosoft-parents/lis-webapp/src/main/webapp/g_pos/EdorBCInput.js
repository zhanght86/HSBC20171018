/***************************************************************
 * <p>ProName��EdorBCInput.js</p>
 * <p>Title��������ά��</p>
 * <p>Description��������ά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-06-13
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();


/**
 * �ύ
 */
function submitForm() {
	
	var i = 0;
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.Operate.value = mOperate;
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
		
		if(mOperate == "DELETE" ||mOperate =="INSERT||UPDATE" ){			
			initOldInsuredInfoGrid();
			initUpdateInsuredInfoGrid();
			initBnfUpdateInfoGrid();
			queryOldClick();
			queryUpdateClick(2);	
		}
	}	
}

/**
 * ����
 */
 
 function addRecord(){
 	
 	if (!verifyInput2()) {
			return false;
	}
	
	var tSelNoOld = OldInsuredInfoGrid.getSelNo();
	var tSelNoUpdate = UpdateInsuredInfoGrid.getSelNo();
	if(tSelNoOld=='0' && tSelNoUpdate=='0'){		
		alert("��ѡ��һ��ԭ��������Ϣ���޸ĺ�ı�������Ϣ���б��棡");
		return false;		
	}
	
	// У��������Ϣ
	BnfUpdateInfoGrid.delBlankLine();
	if(!valBnfInfo()){
			return false;	
	}	
	
	mOperate = "INSERT||UPDATE";
	submitForm();
 }
 
  /**
 * ����
 */
 function deleteRecord(){
 	
 	var tSelNo = UpdateInsuredInfoGrid.getSelNo();  
 	if(tSelNo=='0'){
 		alert("��ѡ��һ���޸Ĺ��ı���������Ϣ���г�����");
 	  return false;
 	}

 	mOperate = "DELETE";
	submitForm();	
 }


/**
 *У����������Ϣ
 */
function valBnfInfo(){
	
	var tCount = BnfUpdateInfoGrid.mulLineCount;
	var sumRate = 0;
	
	if(parseInt(tCount)>0){
		if(!BnfUpdateInfoGrid.checkValue("BnfUpdateInfoGrid")){
			return false;
		}
		
		for (var i=0; i<tCount;i++){
			for(var j=i+1;j<tCount;j++){
				if(BnfUpdateInfoGrid.getRowColData(i,2)!=BnfUpdateInfoGrid.getRowColData(j,2)){
					alert("���������ֻ��¼��һ�֣�");
					return false;
				}
			}
			if(BnfUpdateInfoGrid.getRowColData(i,4)==''){
				if(BnfUpdateInfoGrid.getRowColData(i,2)!='00'||BnfUpdateInfoGrid.getRowColData(i,5)!=''||
					BnfUpdateInfoGrid.getRowColData(i,7)!=''||
					BnfUpdateInfoGrid.getRowColData(i,8)!=''||
					BnfUpdateInfoGrid.getRowColData(i,10)!=''||
					BnfUpdateInfoGrid.getRowColData(i,11)!=''||
					BnfUpdateInfoGrid.getRowColData(i,13)!=''||
					//BnfUpdateInfoGrid.getRowColData(i,14)!=''||
					BnfUpdateInfoGrid.getRowColData(i,16)!=''||
					BnfUpdateInfoGrid.getRowColData(i,17)!=''||
					BnfUpdateInfoGrid.getRowColData(i,18)!=''||
					BnfUpdateInfoGrid.getRowColData(i,20)!=''||
					BnfUpdateInfoGrid.getRowColData(i,22)!=''||
					BnfUpdateInfoGrid.getRowColData(i,23)!=''){
					alert("������˳����Ϊ��!");
					return false;
				}
			}else{
				if(BnfUpdateInfoGrid.getRowColData(i,5)==''){
					alert("��������������Ϊ��!");
					return false;
				}
				if(BnfUpdateInfoGrid.getRowColData(i,2)!='02'){
			
				if(BnfUpdateInfoGrid.getRowColData(i,10)==''){
					alert("֤�����Ͳ���Ϊ��!");
					return false;
				}
				if(BnfUpdateInfoGrid.getRowColData(i,11)==''){
					alert("֤�����벻��Ϊ��!");
					return false;
				}
				if(BnfUpdateInfoGrid.getRowColData(i,10) !=''){
					if(BnfUpdateInfoGrid.getRowColData(i,10)!='0' ){	
						if(BnfUpdateInfoGrid.getRowColData(i,7)==null || BnfUpdateInfoGrid.getRowColData(i,7) =='' && BnfUpdateInfoGrid.getRowColData(i,8)==null || BnfUpdateInfoGrid.getRowColData(i,8) =='' ){
							alert("�������������Ա�/�������ڣ�");
							return false;
						}
					}else if(BnfUpdateInfoGrid.getRowColData(i,10)=='0'){
						if(BnfUpdateInfoGrid.getRowColData(i,11).length!='15' && BnfUpdateInfoGrid.getRowColData(i,11).length!='18'){
							alert("���������֤�������Ϊ15����18λ��");
							return false;
						}
					}
				}
				if(BnfUpdateInfoGrid.getRowColData(i,13)==''){
					alert("�뱻�����˹�ϵ����Ϊ��!");
					return false;
				}
			}
			
			if(BnfUpdateInfoGrid.getRowColData(i,14)==''){
				alert("�����������Ϊ��!");
				return false;
			}
			var tHeadBankCode = BnfUpdateInfoGrid.getRowColData(i,16);
			var tAccname = BnfUpdateInfoGrid.getRowColData(i,17); 
			var tBankaccno = BnfUpdateInfoGrid.getRowColData(i,18); 
			var tBankprovince = BnfUpdateInfoGrid.getRowColData(i,20);
			var tBankcity = BnfUpdateInfoGrid.getRowColData(i,22);
			
			// У��������Ϣ
			var tflag1 = true;
			var bankFlag = 0;
			if(tHeadBankCode!=null || tHeadBankCode!=""){
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_app.LCContCommonSql");
				tSQLInfo.setSqlId("LCContCommonSql9");
				tSQLInfo.addSubPara(tHeadBankCode);
				bankFlag = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			}
			if(bankFlag =='1'){
				if(tHeadBankCode==''&& tBankprovince==''&& tBankcity=='' && tAccname=='' && trim(tBankaccno)==''){
					tflag1= true;
				}else if(tHeadBankCode !=''&& tBankprovince!=''&& tBankcity!='' && tAccname!='' && trim(tBankaccno)!=''){
					tflag1= true;
				}else{
					tflag1=false;
				}
			
			} else {
				if(tHeadBankCode=='' && tAccname=='' && trim(tBankaccno)==''){
					tflag1= true;
				}else if(tHeadBankCode !='' && tAccname!='' && trim(tBankaccno)!=''){
					tflag1= true;
				}else{
					tflag1=false;
				}
			}
			if(tflag1==false){
				alert("����д���������Ϣ��");
				return false;
			
			}
			if(tHeadBankCode != ''){
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_pos.EdorCommonSql");
				tSQLInfo.setSqlId("EdorCommonSql7");
				tSQLInfo.addSubPara(tHeadBankCode);
	
				var arrReslut = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
				if(arrReslut==null){
					alert("��ѡ����Ч�Ŀ������� ��");
					return false;
				}
			}
			if(tBankprovince != ''){
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_pos.EdorCommonSql");
				tSQLInfo.setSqlId("EdorCommonSql8");
				tSQLInfo.addSubPara(tBankprovince);
	
				var arrReslut = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
				if(arrReslut==null){
					alert("��ѡ����Ч�Ŀ���������ʡ��");
					return false;
				}
			}
			if(tBankcity != ''){
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_pos.EdorCommonSql");
				tSQLInfo.setSqlId("EdorCommonSql9");
				tSQLInfo.addSubPara(tBankprovince);
				tSQLInfo.addSubPara(tBankcity);
				var arrReslut = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
				if(arrReslut==null){
					alert("��ѡ����Ч�Ŀ����������У�");
					return false;
				}
			}
		}
	}
}
		
	return true;
}

/**
 * ���ñ��շ����ķ���
 */
function showContPlanCode(cObj,cObjName,cObjCode){
	return showCodeList('contplan',[cObj,cObjName,cObjCode],[0,1,2],null,fm.GrpPropNo.value,'grpcontno',1,null);
}

function showContPlanCodeName(cObj1,cObjName1,cObjCode1){
	return showCodeListKey('contplan',[cObj1,cObjName1,cObjCode1],[0,1,2],null,fm.GrpPropNo.value,'grpcontno',1,null);
}


/**
 * ��ѯԭ��������Ϣ
 */
function queryOldClick(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorBCSql");
	tSQLInfo.setSqlId("EdorBCSql1");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.OldInsuredName.value);
	tSQLInfo.addSubPara(fm.OldInsuredIDNo.value);
	tSQLInfo.addSubPara(fm.OldInsuredNo.value);
	tSQLInfo.addSubPara(fm.ContPlanCodeOld.value);
	tSQLInfo.addSubPara(tEdorAppNo);
		
	initOldInsuredInfoGrid();
	initUpdateInsuredInfoGrid();
	initBnfUpdateInfoGrid();
	fm.edorValDate.value ="";
	fm.SerialNo.value ="";
	turnPage1.queryModal(tSQLInfo.getString(), OldInsuredInfoGrid, 1, 1,10);
	
	if (!turnPage1.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}
}

 /**
 * ��ѯ�޸ĺ󱻱�����Ϣ
 */
function queryUpdateClick(o){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorBCSql");
	tSQLInfo.setSqlId("EdorBCSql3");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(fm.EdorType.value);
	tSQLInfo.addSubPara(NullToEmpty(fm.EdorNo.value));
	tSQLInfo.addSubPara(fm.InsuredName.value);
	tSQLInfo.addSubPara(fm.InsuredIDNo.value);
	tSQLInfo.addSubPara(fm.BatchNo.value);
	
	initOldInsuredInfoGrid();
	initUpdateInsuredInfoGrid();
	initBnfUpdateInfoGrid();
	fm.edorValDate.value ="";
	turnPage2.queryModal(tSQLInfo.getString(), UpdateInsuredInfoGrid, 1, 1,10);
	
	if(o=='1'){
		if (!turnPage2.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}
	}	
}

 /**
 * չʾԭ��������������Ϣ
 */
function showOldBnfList(){
	
	var tSelNo = OldInsuredInfoGrid.getSelNo();
	var tContNo = OldInsuredInfoGrid.getRowColData(tSelNo-1,1);
	var tInsuredNo =  OldInsuredInfoGrid.getRowColData(tSelNo-1,12);
	var tOldInsuredName = OldInsuredInfoGrid.getRowColData(tSelNo-1,2);	
	var tOldIdno = OldInsuredInfoGrid.getRowColData(tSelNo-1,8);
	
	fm.InsuredOldName.value = tOldInsuredName;
	fm.IdOldNo.value = tOldIdno;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorBCSql");
	tSQLInfo.setSqlId("EdorBCSql2");
	tSQLInfo.addSubPara(tContNo);
	tSQLInfo.addSubPara(tInsuredNo);
	
	initBnfUpdateInfoGrid();
	turnPage4.queryModal(tSQLInfo.getString(), BnfUpdateInfoGrid, 1, 1,10);	
}

 /**
 * չʾ�޸ĺ󱻱�����������Ϣ
 */
function showUpdateInsuredList(){
	
	var tSelNo = UpdateInsuredInfoGrid.getSelNo();
	var tSerialNo = UpdateInsuredInfoGrid.getRowColData(tSelNo-1,1);
	var tOldInsuredName = UpdateInsuredInfoGrid.getRowColData(tSelNo-1,2);	
	var tOldIdno = UpdateInsuredInfoGrid.getRowColData(tSelNo-1,3);	
	var tEdorValdate =  UpdateInsuredInfoGrid.getRowColData(tSelNo-1,4);	
	
	fm.edorValDate.value = tEdorValdate;
	fm.InsuredOldName.value = tOldInsuredName;
	fm.IdOldNo.value = tOldIdno;
	fm.SerialNo.value = tSerialNo;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorBCSql");
	tSQLInfo.setSqlId("EdorBCSql4");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(fm.EdorType.value);
	tSQLInfo.addSubPara(tSerialNo); 
	tSQLInfo.addSubPara(fm.BatchNo.value);

	initBnfUpdateInfoGrid();
	turnPage4.queryModal(tSQLInfo.getString(), BnfUpdateInfoGrid, 1, 1,10);
}
/**
 * ������ѡ�����
 */
function afterCodeSelect(tVal, tObj) {

	if(tVal=='province'){
		if(tObj.name == "BnfUpdateInfoGrid19"){
			var rowNumber = BnfUpdateInfoGrid.lastFocusRowNo;
			BnfUpdateInfoGrid.setRowColData(rowNumber, 21, "");
			BnfUpdateInfoGrid.setRowColData(rowNumber, 22, "");
		}
	}
	if(tVal=='bnftype'){
		if(tObj.value == "����������"){
			var rowNumber = BnfUpdateInfoGrid.lastFocusRowNo;
			BnfUpdateInfoGrid.setRowColData(rowNumber, 14, "1");
		}
	}
}


