

//DeleteSendToBankInput.js���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

var showInfo;

//�ύ�����水ť��Ӧ����
function submitForm() {
  if (BankGrid.getSelNo() == 0) {
    alert("����ѡ��һ��Ҫȡ�����������ݣ�������;���ݲ���ȡ����");
  }
  else {     
    fm.TempFeeNo.value = BankGrid.getRowColData(BankGrid.getSelNo()-1, 1);
    fm.Action.value = "DELETE";   
    fm.butSave.disabled = false;
    var showStr="�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	document.getElementById("fm").submit(); //�ύ
  }
}

//�ύ�����水ť��Ӧ����
function submitForm2() {
  if (BankGrid.getSelNo() == 0) {
    alert("����ѡ��һ��Ҫȡ�����������ݣ�");
  }
  else {     
    fm.TempFeeNo.value = BankGrid.getRowColData(BankGrid.getSelNo()-1, 1);   
    fm.Action.value = "UPDATE";  
    fm.butSave2.disabled = false;
    var showStr="�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();	
	document.getElementById("fm").submit(); //�ύ
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
}   

// ��ѯ��ť
function easyQueryClick() {
  if (fm.PrtNo.value == "") {
    alert("��������ӡˢ��");
    return; 
  }
  
  fm.butQuery.disabled = true;
	// ��дSQL���		
	//modify by ml 2006-03-17	for improving efficiency     
	//zy 2008-11-01 11:50 �޸Ĳ�ѯ�߼�
//	var strSql = "select /*+rule+*/ a.tempfeeno, a.paymoney, a.bankcode, a.accname, a.bankaccno, b.appntno, b.bankonthewayflag, b.riskcode, b.sendbankcount"
//             + " from ljtempfeeclass a, ljspay b where a.tempfeeno=b.getnoticeno and exists "
//          	 + " (select 1 from ljtempfee where tempfeeno=a.tempfeeno and otherno in "
//          	 + " (select contno from lccont where prtno='" + fm.PrtNo.value + "'" 
//          	 + " union "
//          	 + " select familycontno from lccont where prtno='" + fm.PrtNo.value + "'" 
//          	 + " union "
//          	 + " select '" + fm.PrtNo.value + "' from dual " 
//          	 +"union "
//          	 + " select prtno from lccont where prtno='" + fm.PrtNo.value + "' "
//          	 + " ))" ;
	
	  var sqlid1="DeleteSendToBankInputSql1";
	  var mySql1=new SqlClass();
	  mySql1.setResourceName("bank.DeleteSendToBankInputSql");
	  mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
	  mySql1.addSubPara(fm.PrtNo.value);//ָ���������
	  mySql1.addSubPara(fm.PrtNo.value);//ָ���������
	  mySql1.addSubPara(fm.PrtNo.value);//ָ���������
	  mySql1.addSubPara(fm.PrtNo.value);//ָ���������
	  var strSql = mySql1.getString();
	
	
// zy 2009-06-05 11:23 add by hezy �Ż���ѯ�߼�
//   var strSql = "select  a.tempfeeno, a.paymoney, a.bankcode, a.accname, a.bankaccno, b.appntno, b.bankonthewayflag, b.riskcode, b.sendbankcount "
//							+ " from ljtempfeeclass a, ljspay b,ljtempfee c "
//							+ " where '1244162938000' = '1244162938000' "
//							+ "and a.tempfeeno = b.getnoticeno  and a.tempfeeno = c.tempfeeno   and c.otherno in "
//							+ "(select contno from lccont where prtno = '" + fm.PrtNo.value + "' "
//							+ "  union select familycontno from lccont where prtno = '" + fm.PrtNo.value + "' and familycontno is not null "
//							+ "union select '" + fm.PrtNo.value + "'  from dual "
//							+ "union select prtno from lccont where prtno = '" + fm.PrtNo.value + "') "
//							;
       	
				     
  //alert(strSql);

	turnPage.queryModal(strSql, BankGrid);
	  if (!turnPage.strQueryResult) {
    alert("�Բ���,δ�鵽��Ӧ���ݡ�");
    return ;
  }
}

