//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var turnPage = new turnPageClass();
var tResourceName="certify.CertifySendOutReplyInputSql";
//��ѯ����������������
function queryClick()
{
  initCertifyList();
  /*var strSql = "select a.applyno, a.certifycode, (select certifyname from lmcertifydes b where b.certifycode = a.certifycode),"
			 +"(select nvl(sum(c.sumcount), 0) from lzcard c where c.certifycode = a.certifycode and c.receivecom = a.sendoutcom and c.stateflag in ('2', '3')),"
			 +"(select nvl(sum(d.sumcount), 0) from lzcard d where d.certifycode = a.certifycode and d.receivecom = a.receivecom and d.stateflag in ('2', '3')),"
			 +" a.startno, a.endno, a.sumcount, a.reason, a.receivecom, (select managecom from laagent where 'D'||agentcode = a.receivecom),"
          	 +" a.operator,a.makedate from lzcardapp a where a.OperateFlag='1' and a.stateflag='1' "
			 + getWherePart('a.sendoutcom', 'SendOutCom')
			 + getWherePart('a.receivecom', 'ReceiveCom')
			 + getWherePart('a.ApplyCom', 'ComCode', 'like');
  strSql+=" and (a.sendoutcom ='A" + fm.ComCode.value + "' or a.sendoutcom like 'B" + fm.ComCode.value +"%')";
  strSql+=" order by a.makedate,a.certifycode";*/
  var strSql = wrapSql(tResourceName,"querysqldes3",[fm.SendOutCom.value,fm.ReceiveCom.value,fm.ComCode.value,fm.ComCode.value,fm.ComCode.value]);
  
  turnPage.pageDivName = "divCertifyList";
  turnPage.queryModal(strSql, CertifyList);
  divCertifyList.style.display='';	 
  if(CertifyList.mulLineCount==0){
 	 alert("û�д������ļ�¼��");
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
    alert("������ѡ��һ��Ҫ�����ĵ�֤��");
    return false;
  }
  if( verifyInput()==false || CertifyList.checkValue()==false ) 
  {
  	 return false;
  }
  
  if (confirm("��ȷ��ͬ�����������?")){
	try {		
	  	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	  	fm.operateFlag.value='true';
	  	document.getElementById("fm").submit(); //�ύ	 
    } catch(ex) {
  	  showInfo.close( );
  	  alert(ex);
    }
  }else{
    alert("��ȡ����ͬ�����������");
  }	  
}

function submitForm2()
{
  CertifyList.delBlankLine("CertifyList");
  var nSelectedCount = CertifyList.getChkCount();
  if (nSelectedCount == null || nSelectedCount <= 0)
  {
    alert("������ѡ��һ��Ҫ�����ĵ�֤��");
    return false;
  }
  if( verifyInput()==false || CertifyList.checkValue()==false ) 
  {
  	 return false;
  }
    
  if (confirm("��ȷ����������������?")){
	try {
	  	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	  	fm.operateFlag.value='false';
	  	document.getElementById("fm").submit(); //�ύ	 
    } catch(ex) {
  	  showInfo.close( );
  	  alert(ex);
    }
  }else{
    alert("��ȡ������������������");
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
  fm.note.value = '';
}

function afterCodeSelect( cName, Filed)
{
  if(cName=='DepartmentNo')//ѡ���ű����
  {
    if(fm.Department.value==null || fm.Department.value==""){
      alert("��ѡ�񡾲�����Դ��");
    }else if(fm.Department.value=="1"){
      fm.SendOutCom.value ="B" + fm.ComCode.value + fm.DepartmentNo.value
      fm.SendOutCom.readOnly = true;
      fm.ReceiveCom.value = "";
      fm.ReceiveCom.readOnly = false;
    }else if(fm.Department.value=="2"){
      fm.ReceiveCom.value ="B" + fm.ComCode.value + fm.DepartmentNo.value
      fm.ReceiveCom.readOnly = true;
      fm.SendOutCom.value = "";
      fm.SendOutCom.readOnly = false;        
    }else{      
      alert("ѡ�񡾲��ű��롿����");
      fm.DepartmentNo.value="";
    }
  }
  if(cName=='Department')//ѡ���ź�
  {
    if(fm.DepartmentNo.value!=null && fm.DepartmentNo.value!=""){
 	  if(fm.Department.value=="1"){
        fm.SendOutCom.value ="B" + fm.ComCode.value + fm.DepartmentNo.value
        fm.SendOutCom.readOnly = true;
        fm.ReceiveCom.value = "";
        fm.ReceiveCom.readOnly = false;         
      }else if(fm.Department.value=="2"){
        fm.ReceiveCom.value ="B" + fm.ComCode.value + fm.DepartmentNo.value
        fm.ReceiveCom.readOnly = true;   
        fm.SendOutCom.value = "";
        fm.SendOutCom.readOnly = false;       
      }else if(fm.Department.value=="3"){
        fm.DepartmentNo.value = "";
        fm.DepartmentNoName.value = "";
        fm.SendOutCom.value = "";
        fm.SendOutCom.readOnly = false; 
        fm.ReceiveCom.value = "";
        fm.ReceiveCom.readOnly = false;          
      }
    }
  }
  if(cName=='CertifyCode')//ѡ��֤�����,�Զ���ѯ�����ߺͽ����ߵĿ�沢��ʾ
  {
    var SendOutCom = fm.SendOutCom.value;
    var ReceiveCom = fm.ReceiveCom.value;
    if(SendOutCom=="" || ReceiveCom==""){
      alert("����¼�롾�����ߡ��͡������ߡ�����ѡ��֤���룡");
      return false;
    }
    for(var i=0;i<CertifyList.mulLineCount;i++){//ѭ����ѯmulLine����������δ��ѯ���Ľ����ѯ������ʾ
      var CertifyCode=CertifyList.getRowColData(i,1);
      var sum1=CertifyList.getRowColData(i,3);
      var sum2=CertifyList.getRowColData(i,3);
      if(CertifyCode!="" && (sum1=="" || sum2==""))
        {
         //alert("��"+(i+1)+"�е�֤����="+CertifyCode);         
         //var sqlSum1="select nvl(sum(a.sumcount),0),min(a.startno) from lzcard a where a.certifycode='"+CertifyCode+"' and a.receivecom='"+ SendOutCom +"' and a.stateflag in ('2','3')";
         var sqlSum1 = wrapSql(tResourceName,"querysqldes1",[CertifyCode,SendOutCom]);
         //var sqlSum2="select nvl(sum(a.sumcount),0) from lzcard a where a.certifycode='"+CertifyCode+"' and a.receivecom='"+ ReceiveCom +"' and a.stateflag in ('2','3')";
         var sqlSum2 = wrapSql(tResourceName,"querysqldes2",[CertifyCode,ReceiveCom]);
         var arrResult1 = easyExecSql(sqlSum1);
         var arrResult2 = easyExecSql(sqlSum2);
         //alert("�������="+arrResult1[0][0]);
         //alert("�¼����="+arrResult2[0][0]);
         CertifyList.setRowColData(i,3,arrResult1[0][0]);//�����߿��
         CertifyList.setRowColData(i,4,arrResult2[0][0]);//�����߿��
         
         //var sqlHavnumber="select a.havenumber from lmcertifydes a where a.certifycode='"+CertifyCode+"'";
         var sqlHavnumber = wrapSql(tResourceName,"querysqldes4",[CertifyCode]);
         var Havnumber = easyExecSql(sqlHavnumber);
         if(Havnumber=="Y"){
           CertifyList.setRowColData(i,5,arrResult1[0][1]);//���Ϊ�кŵ�֤���Զ�������С����ʼ����
         }
        }
    }
  }  
}

