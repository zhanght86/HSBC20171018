/***************************************************************
 * <p>ProName��LLClaimNoAcceptInput.js</p>
 * <p>Title��δ����ͻ���ѯ</p>
 * <p>Description��δ����ͻ���ѯ</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-04-23
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * ��ѯδ����ͻ���Ϣ
 */
function queryCustomerList() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimNoAcceptSql");
	tSQLInfo.setSqlId("LLClaimNoAcceptSql");
	tSQLInfo.addSubPara(mGrpRgtNo);
	
	turnPage1.queryModal(tSQLInfo.getString(),NoAcceptListGrid,2);	
}

/**
 * ��ѯѡ�еĳ�������Ϣ
 */
function showSelectCustomerInfo() {
	
	var i = NoAcceptListGrid.getSelNo()-1;		
	var tRgtNo = NoAcceptListGrid.getRowColData(i,1);
	var tCustomerNo = NoAcceptListGrid.getRowColData(i,2);
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimNoAcceptSql");
	tSQLInfo.setSqlId("LLClaimNoAcceptSql1");
	tSQLInfo.addSubPara(tCustomerNo);	
	tSQLInfo.addSubPara(tRgtNo);
	tSQLInfo.addSubPara(mGrpRgtNo);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr!=null && tArr.length==1) {
						
		fm.CustomerNo.value = tArr[0][0];
		fm.CustomerName.value = tArr[0][1];
		fm.Gender.value = tArr[0][2];
		fm.GenderName.value = tArr[0][3];		
		fm.Birthday.value = tArr[0][4];
		fm.IDType.value = tArr[0][5];
		fm.IDTypeName.value = tArr[0][6];
		fm.IDNo.value = tArr[0][7];
		
		fm.AppAmnt.value = tArr[0][8];
		fm.BillCount.value = tArr[0][9];
		fm.ScanCount.value = tArr[0][10];		

		fm.NoAcceptReasonName.value = tArr[0][11];
		fm.RgtNo.value = tArr[0][12];
			
	} else {
		alert("��ѯ����");
		return false;		
	}	
}

/**
 * ����
 */
function addClick() {
	
	//ϵͳ��У�鷽��
	if (!verifyInput2()) {
		return false;
	}
	var tBirthday = fm.Birthday.value;
	if (dateDiff(tBirthday,mCurrentDate,'D')<0) {	
		
		alert("�������ڲ������ڵ�ǰ���ڣ�");
		return false;
	}
	
	fm.Operate.value="INSERT";
	submitForm();		
}
/**
 * �޸�
 */
function modifyClick() {
	
	var tSelNo = NoAcceptListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ���ó�������Ϣ");
		return false;
	}	
	//ϵͳ��У�鷽��
	if (!verifyInput2()) {
		return false;
	}
	var tBirthday = fm.Birthday.value;
	if (dateDiff(tBirthday,mCurrentDate,'D')<0) {	
		
		alert("�������ڲ������ڵ�ǰ���ڣ�");
		return false;
	}
	
	fm.Operate.value="UPDATE";
	submitForm();	
}
/**
 * ɾ��
 */
function deleteClick() {
	
	var tSelNo = NoAcceptListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ���ó�������Ϣ");
		return false;
	}
	if (confirm("ȷ��ɾ����δ����ͻ���")) {
		fm.Operate.value="DELETE";
		submitForm();			
	}
}
/**
 * ɾ������δ����ͻ���Ϣ
 */
function deleteAll() {

	if (confirm("�Ƿ�ɾ������δ����ͻ���")) {
		fm.Operate.value="DELETEALL";
		submitForm();		
	}	
}
/**
 * ����
 */
function goBack() {
	top.close();	
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
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
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

/**
 * ������ѡ���Ժ����
 */
function afterCodeSelect(cCodeName, Field) {

	if(cCodeName == 'idtype') {
		
		if (fm.IDType.value == "01") {
			if (fm.IDNo.value == null || fm.IDType.value == "") {
				return false;
			} else {
				getBirthdaySexByIDNo(fm.IDNo.value,"IDType","Gender","GenderName","Birthday");
			}
		}
	}
}

/**
 * ���ý������֤����
 */
function analysisIDNo(orgIDNo){
		
		getBirthdaySexByIDNo(orgIDNo.value,"IDType","Gender","GenderName","Birthday");
}

/**
 * �������֤���룬�������գ��Ա�
 */
function getBirthdaySexByIDNo(iIdNo,iIdType,gender,gendername,insuredbirthday){
	
	try{
		if(fm.all(iIdType).value=="11"){
			fm.all(gender).value = "";
			fm.all(gendername).value = "";
			fm.all(insuredbirthday).value = "";
			if(iIdNo!=null&&iIdNo!=""){
				if(isNumeric(iIdNo.substring(0,(iIdNo.length-1)))==false){
					alert("���֤����ֻ�������֣�");
					return;
				}
					
				if(iIdNo.length!=18&&iIdNo.length!=15){
					alert("���֤����ֻ��Ϊ15��18λ��");
					return;
				}
					
				if(iIdNo.length==18){
					if(iIdNo.substring(10,12)>12){
						alert("���֤�д���������ڵ�������д����");
						return;
					}
				}
					
				if(iIdNo.length==18){
					if(iIdNo.substring(10,12)=="12"||iIdNo.substring(10,12)=="01"||iIdNo.substring(10,12)=="03"||iIdNo.substring(10,12)=="05"||iIdNo.substring(10,12)=="07"||iIdNo.substring(10,12)=="08"||iIdNo.substring(10,12)=="10"){
						if(iIdNo.substring(12,14)>31){
							alert("���֤�д���������ڵ�������д����");
							return;
						}
					}
					if(iIdNo.substring(10,12)=="04"||iIdNo.substring(10,12)=="06"||iIdNo.substring(10,12)=="09"||iIdNo.substring(10,12)=="11"){
						if(iIdNo.substring(12,14)>30){
							alert("���֤�д���������ڵ�������д����");
							return;
						}
					}
			
					if(iIdNo.substring(10,12)=="02"){
						if(iIdNo.substring(6,10)%4!=0){
							if(iIdNo.substring(12,14)>28){
								alert("���֤�д���������ڵ�������д����");
								return;
							}
					 	}
						if(iIdNo.substring(6,10)%4==0){
							if(iIdNo.substring(12,14)>29){
								alert("���֤�д���������ڵ�������д����");
								return;
							}
						}
					}
					//��������1900��ļ��
					if (parseInt(iIdNo.substring(6,10),10)<1900 || parseInt(iIdNo.substring(6,10),10)>2100 ){
						alert("�������ڸ�ʽ���ԣ��뷵�ؼ��");
						return false;
					}
					
					//ǰ2λ�Ƿ����
					var countQuerysql="select count(1) from ldarea where areatype='0' and provincecode ='"+iIdNo.substring(0,2)+"' ";
					var isexist=easyExecSql(countQuerysql,1,0);
					if(isexist==null||isexist==""||isexist=='0'){
						alert("���֤�����д����������ص�ǰ2λ�����ϱ�׼���뷵�ؼ��");
						return false;
					}
					//18λʱ���һλ����ļ��
					var NUM="0123456789";
					if(iIdNo.charAt(17)!='x' && iIdNo.charAt(17)!='X' && NUM.indexOf(iIdNo.charAt(17))<0){
						alert("���֤Ϊ18λʱ���һλֻ�������ֻ���X!");
						return false;
					}
				}
			
				if(iIdNo.length==15){
					if(iIdNo.substring(8,10)>12){
						alert("���֤�д������������д����");
						return;
					}
				}
				if(iIdNo.length==15){
			
					//�ж�����
					if(iIdNo.substring(8,10)=="12"||iIdNo.substring(8,10)=="01"||iIdNo.substring(8,10)=="03"||iIdNo.substring(8,10)=="05"||iIdNo.substring(8,10)=="07"||iIdNo.substring(8,10)=="08"||iIdNo.substring(8,10)=="10"){
						if(iIdNo.substring(10,12)>31){
							alert("���֤�д���������ڵ�������д����");
							return;
						}
					}
					if(iIdNo.substring(8,10)=="04"||iIdNo.substring(8,10)=="06"||iIdNo.substring(8,10)=="09"||iIdNo.substring(8,10)=="11"){
						if(iIdNo.substring(10,12)>30){
							alert("���֤�д���������ڵ�������д����");
							return;
						}
					}
					if(iIdNo.substring(8,10)=="02"){
						 if(19+(iIdNo.substring(6,8))%4!=0){
								if(iIdNo.substring(10,12)>28){
									alert("���֤�д���������ڵ�������д����");
									return;
								}
						 }
						 if(19+(iIdNo.substring(6,8)+1900)%4==0){
							if(iIdNo.substring(10,12)>29){
								alert("���֤�д���������ڵ�������д����");
								return;
							}
						}
					}
					var tmpStr= "19" + iIdNo.substring(6,8); //��������1900��ļ��
					if (parseInt(tmpStr.substring(0,4),10)<1900 || parseInt(tmpStr.substring(0,4),10)>2100 ){
						alert("�������ڸ�ʽ���ԣ��뷵�ؼ��");
						return false;
					}
					
					//ǰ2λ�Ƿ����
					var countQuerysql="select count(1) from ldcode where codetype ='province'' and code ='"+iIdNo.substring(0,2)+"' ";
					var isexist=easyExecSql(countQuerysql,1,0);
					if(isexist==null||isexist==""||isexist=='0'){
						alert("���֤�����д������������ǰ2λ�����ϱ�׼���뷵�ؼ��");
						return false;
					}
			
					//	15λʱ���ֵ�У��
					var NUM="0123456789";
					var i;
					for(i=0;i<iIdNo.length;i++){
					
						if(NUM.indexOf(iIdNo.charAt(i))<0) {
							alert("���֤Ϊ15λʱֻ�������֣�");
							return false;
						}
					}
				}
				if(trim(iIdNo).length==18){
					var sex;
					var sexq;
					var birthday;
					birthday=trim(iIdNo).substring(6,10)+"-"+trim(iIdNo).substring(10,12)+"-"+trim(iIdNo).substring(12,14);
					
					fm.all(insuredbirthday).value=getBirthdatByIdNo(iIdNo);
					//fm.all(insuredbirthday).value=birthday;
				/*	sex=trim(iIdNo).substring(16,17)
					if(sex%2==1){
						sexq='1';
					}else{
						sexq='2';
					}*/
					sexq=getSexByIDNo(iIdNo);
					fm.all(gender).value = sexq;
				}
			
				if(trim(iIdNo).length==15){
					var sex;
					var sexq;
					var birthday;
					birthday="19"+trim(iIdNo).substring(6,8)+"-"+trim(iIdNo).substring(8,10)+"-"+trim(iIdNo).substring(10,12);
					 
					fm.all(insuredbirthday).value=birthday;
					sex=trim(iIdNo).substring(14,15)
					if(sex%2==1){
						sexq='0';
					}else{
						sexq='1';
					}
					fm.all(gender).value=sexq;
				}
				
				
				
				if(fm.all(gender).value=="0"){
					fm.all(gendername).value = "��";
				}else if(fm.all(gender).value=="1"){
					fm.all(gendername).value = "Ů";
				}else if(fm.all(gender).value=="2"){
					fm.all(gendername).value = "����";
				}
			}
		}
	}catch(ex)
	{}
}
function getInfo() {
	
	if (mMode!=null && mMode=="0") {
		if (top.opener.fm.CustomerNo.value==null || top.opener.fm.CustomerNo.value=="") {
			fm.CustomerName.value = top.opener.fm.CustomerName.value;
			fm.Gender.value = top.opener.fm.Gender.value;
			fm.Birthday.value = top.opener.fm.Birthday.value;
			fm.IDNo.value = top.opener.fm.IDNo.value;
			fm.IDType.value = top.opener.fm.IDType.value;
		}		
	}	
}