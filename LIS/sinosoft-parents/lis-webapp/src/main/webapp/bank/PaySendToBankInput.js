//PaySendToBankInput.js���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var tFees;

var tResourceName="bank.PaySendToBankInputSql";
var tResourceSQL1="PaySendToBankInputSql1";

//�ύ�����水ť��Ӧ����
function submitForm() {
  if(verifyInput() == false) return false;
  
  document.getElementById("fm").submit();}


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
}    

//��ʼ�����д���
function initBankCode() {
  //var strSql = "select BankCode, BankName from LDBank where ComCode = '" + comCode + "'"; 
  var strSql  = wrapSql(tResourceName,tResourceSQL1,[document.all('comCode').value]); 
  var strResult = easyQueryVer3(strSql);
  //alert(strResult);

  if (strResult) {
    document.all("bankCode").CodeData = strResult;
  }
}
