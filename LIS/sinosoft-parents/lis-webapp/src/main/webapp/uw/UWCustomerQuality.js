//�������ƣ�UWCustomerQuality.js
//�����ܣ��ͻ�Ʒ�ʹ���
//�������ڣ�2005-06-18 11:10:36
//������  ��ccvip
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var tResourceName="uw.UWCustomerQualitySql";

function queryClick()
{
	// ��ʼ�����
	//initQuestGrid();
	//initContent();
	
	// ��дSQL���
  /*var strSql = "select PrtNo, ProposalContNo, NotePadCont, Operator, MakeDate, MakeTime from LCNotePad where PrtNo='" + PrtNo 
             + "' and ContNo='" + ContNo + "' and OperatePos='" + OperatePos + "'"
             + getWherePart('Operator', 'Operator')
             + getWherePart('MakeDate', 'MakeDate');*/
  var strSql = wrapSql(tResourceName,"querysqldes1",[PrtNo,ContNo,OperatePos,document.all('Operator').value,document.all('MakeDate').value]);

  turnPage.queryModal(strSql, QuestGrid); 
  
  fm.Content.value = "";
}


function showOne(parm1, parm2) {	
  //�жϸ����Ƿ�ȷʵ��ѡ��
	if(document.all(parm1).all('InpQuestGridSel').value == '1' ) {
	  var index = document.all(parm1).all('QuestGridNo').value - 1;
	  fm.Content.value = turnPage.arrDataCacheSet[index][2];
  }
}

function UpdateClick() {
  if (trim(fm.Remark.value) == "") {
    alert("������д��ע���ݣ�");
    return;
  }
  
  fm.PrtNo.value = PrtNo;
  fm.ContNo.value = ContNo;
//  fm.OperatePos.value = OperatePos; 
  
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;    
  //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  document.getElementById("fm").submit(); //�ύ
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content) {   
  showInfo.close();         
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

function afterCodeSelect( cCodeName, Field )
{
 if(cCodeName=="customer"){
 	//alert(fm.customer.value);
 	//var strSQL="select * from LDPerson where CustomerNo='"+fm.customer.value+"'";
 	var strSQL = wrapSql(tResourceName,"querysqldes2",[fm.customer.value]);
 	//alert(strSQL);
   var arrResult=easyExecSql(strSQL);
   //alert(arrResult);                         
   try{document.all('CustomerNo').value= arrResult[0][0];}catch(ex){};         
   try{document.all('Name').value= arrResult[0][1];}catch(ex){};          
   try{document.all('Sex').value= arrResult[0][2];}catch(ex){};     
   try{document.all('Birthday').value= arrResult[0][3];}catch(ex){};            
   try{document.all('IDType').value= arrResult[0][4];}catch(ex){};              
   try{document.all('IDNumber').value= arrResult[0][5];}catch(ex){};                
  
  
   
   return;       
 }	
// if(cCodeName=="paymode"){
//   	if(document.all('PayMode').value=="4"){
//   	  //divLCAccount1.style.display="";
//    }
//    else
//    {
//   	  //divLCAccount1.style.display="none";
//      //alert("accountImg===");	
//    }
// }
 //alert("aaa");
// afterCodeSelect2( cCodeName, Field );
}


function returnParent(){
  top.close();
	
}