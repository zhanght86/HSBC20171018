//ReadFromFileInput.js���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var filePath;
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���


var tResourceName="bank.CancelReadFromFileInputSql";
var tResourceSQL1="CancelReadFromFileInputSql1";

//�ύ�����水ť��Ӧ����
function submitForm() {
  if(document.all('BackReason').value.length=0){
  	alert("��¼��ع�ԭ��");
  	return;	
  } 
  if(document.all('BackReason').value.length>200){
  	alert("�ع�ԭ��������������¼��200������");
  	return;	
  } 
  if (BankGrid.getSelNo()) {    
    var showStr="�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
   //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;"); 
   var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.getElementById("fm").submit(); //�ύ
  }
  else {
    alert("����ѡ��һ�����κ���Ϣ��"); 
  }
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content ) {
  try { showInfo.close(); } catch(e) {}
  
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}   

// ��ѯ��ť
function easyQueryClick() {
  if(document.all('BankCode').value.length==0 && document.all('SerialNo').value.length==0){
  	alert("���б��������κ�������¼��һ���ٽ��в�ѯ");
  	return;	
  }
	// ��дSQL���
	var inFileSql = "";
	if(document.all('InFile').value.length>0){
		inFileSql = " and InFile like '%" + document.all('InFile').value + "%' ";
	}
	//var strSql = " select BankCode,(select bankname from ldbank where bankcode=a.bankcode),SerialNo,SUBSTR(InFile,INSTR(InFile,'/',-1)+1),ReturnDate,TotalNum, "
	//					 + "(select count(1) from LYReturnFromBank where SerialNo=a.serialno and banksuccflag='0000' ), "
	//					 + "SUBSTR(OutFile,INSTR(OutFile,'/',-1)+1),SendDate "
	//					 + " from LYBankLog a "
	//					 + "	where a.dealstate is null and ComCode like '" + comCode + "%%'  and LogType='" + dealType + "' "
	//			     + getWherePart('BankCode')
	//			     + getWherePart('SerialNo')
	//			     + getWherePart('ReturnDate')
	//			     + inFileSql
	//			     + tBankDataSQL
	//					 + " and exists (select 1 from LYReturnFromBank where SerialNo=a.serialno) "		
	//					 + " and not exists(select 1 from LYReturnFromBankB where SerialNo = a.serialno) "	     
	//					 + " order by SerialNo desc";	
	var strSql = wrapSql(tResourceName,tResourceSQL1,[comCode,dealType,document.all('BankCode').value,document.all('SerialNo').value,document.all('ReturnDate').value,inFileSql,tBankDataSQL]);			     
  //prompt('',strSql);
	turnPage.queryModal(strSql, BankGrid);
	if(BankGrid.mulLineCount == 0){
		alert("���޽��");	
	}
}
