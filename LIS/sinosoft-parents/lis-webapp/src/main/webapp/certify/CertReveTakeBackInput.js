//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var turnPage = new turnPageClass();
var tResourceName="certify.CertReveTakeBackInputSql";
//��ѯ����������������
function queryClick()
{
  initCertifyList();
  /*var strSql = "select a.applyno, a.certifycode, (select certifyname from lmcertifydes b where b.certifycode = a.certifycode),"
			 +" a.sendoutcom, a.receivecom, a.startno, a.endno, a.sumcount, a.reason "
			 +" from lzcardapp a where a.OperateFlag='2' and a.stateflag='1' "
			 + getWherePart('a.handler', 'handler')
			 + getWherePart('a.handledate', 'handledate')
			 + getWherePart('a.ApplyCom', 'ComCode', 'like')
			 +" order by a.certifycode, a.startno";*/
  var strSql = wrapSql(tResourceName,"querysqldes1",[document.all('Handler').value,document.all('HandleDate').value,document.all('ComCode').value]);
  turnPage.pageDivName = "divCertifyList";
  turnPage.queryModal(strSql, CertifyList);
  divCertifyList.style.display='';	  		 
  if (CertifyList.mulLineCount==0) {
 	 alert("û�в�ѯ�������޶������¼��");
     return false;
    }
}

//�ύ�����水ť��Ӧ����
function submitForm()
{
  CertifyList.delBlankLine("CertifyList");
  var nSelectedCount = CertifyList.getChkCount();
  if (nSelectedCount == null || nSelectedCount <= 0)
  {
    alert("������ѡ��һ��Ҫ�޶�ȷ�ϵĵ�֤�� ");
    return false;
  }
  
  if (confirm("��ȷ���޶�ȷ�ϲ�����?")){
	try {
		if( verifyInput() == true && CertifyList.checkValue("CertifyList") ) {
	  	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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
    } catch(ex) {
  	  showInfo.close( );
  	  alert(ex);
    }
  }else{
    alert("��ȡ�����޶�ȷ�ϲ�����");
  }	  
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  fm.btnOp.disabled = false;  	
  if(FlagStr == "Fail" ) {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");    
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }else { 
	var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content;	    
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  queryClick();
}

//��������ҵ��Աʱ������ҵ��Ա�������͹���
var arrResult = new Array();
function queryAgent()
{
	var receivecom = document.all('ReceiveCom').value;
	if(receivecom != "" && receivecom.substring(0,1) == 'D')	 
	{
		if(trim(receivecom).length >= 11)
		{
			var cAgentCode = receivecom.substring(1,11);
			//var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";
			var strSql = wrapSql(tResourceName,"querysqldes2",[cAgentCode]);
	    	var arrResult = easyExecSql(strSql);
	    	if (arrResult != null) 
	      		alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
	    	else
				alert("����Ϊ:["+ cAgentCode +"]�Ĵ����˲����ڣ���ȷ��!");
		}
	}
}
