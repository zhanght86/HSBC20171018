//ReadFromFileInput.js���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var filePath;
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���


var tResourceName="bank.ReadFromFileInputSql";
var tResourceSQL1="ReadFromFileInputSql1";
var tResourceSQL2="ReadFromFileInputSql2";
var tResourceSQL3="ReadFromFileInputSql3";
var tResourceSQL4="ReadFromFileInputSql4"; 

//�ύ�����水ť��Ӧ����
function submitForm() {
  //if(verifyInput() == false) return false;  
  
  if (BankGrid.getSelNo()) {     
    var showStr="�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    var tempaction = fm.action;					//ÿ��Ҫ������ǰ��·����./ReadFromFileSave.jsp + "?filePath=" + filePath;��
    fm.action = fm.action + "&serialno=" + BankGrid.getRowColData(BankGrid.getSelNo()-1, 2)
                          + "&bankCode=" + BankGrid.getRowColData(BankGrid.getSelNo()-1, 1)
                          + "&bussType=" + dealType;
//		alert(fm.action);
    document.getElementById("fm").submit(); //�ύ
		fm.action = tempaction;             //�ύ��ԭԭ�е�·��
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
	var iHeight=300;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  easyQueryClick();
}   

//��ȡ�ļ��ϴ�����·��
function getUploadPath() {
 // var strSql = "select SysVarValue from LDSysVar where SysVar = 'ReturnFromBankPath'";
 // var strSql = wrapSql(tResourceName,tResourceSQL1,[]); 
 
  
    var sqlid1=tResourceSQL1;
	var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara("");
  var strSql = mySql1.getString(); 
  
   filePath = easyExecSql(strSql);
   //alert(filePath);
  fm.action = "./ReadFromFileSave.jsp" + "?filePath=" + filePath;
}

// ��ѯ��ť
function easyQueryClick() {
	// ��дSQL���
	//var strSql = "select BankCode, Serialno, Outfile, StartDate from LYBankLog where InFile is null and ComCode like '" + comCode + "%%'"
	//           + tBankDataSQL
	//           + " and LogType='" + dealType + "'"
	//			     + getWherePart('BankCode')
	//			     + getWherePart('SendDate')
	//			     +" order by SerialNo desc";

 var tComCode = comCode;
   var tDealType = dealType;
   var tBankCode = document.all("BankCode").value;
   var tSendDate = document.all("SendDate").value;
   //var tBankDataSQL = tBankDataSQL
  
  // alert('2');
    var sqlid1="ReadFromFileInputSql2";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
    mySql1.addSubPara(tComCode);//ָ������Ĳ���
   // alert('3');
    mySql1.addSubPara(tBankDataSQL);//ָ������Ĳ���
   // alert('4');
    mySql1.addSubPara(tDealType);//ָ������Ĳ���
    mySql1.addSubPara(tBankCode);//ָ������Ĳ���
    mySql1.addSubPara(tSendDate);//ָ������Ĳ���


	//var strSql = wrapSql(tResourceName,tResourceSQL2,[document.all("comCode").value,tBankDataSQL,document.all("dealType").value,document.all("BankCode").value,document.all("StartDate").value]); 			     
  var strSql =  mySql1.getString(); 
  //alert(strSql);
	turnPage.queryModal(strSql, BankGrid);
}
