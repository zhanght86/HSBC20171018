//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var tResourceName="certify.CertifySendOutInputSql";
//���ŵ�֤����
function submitForm()
{ //����˫��
  fm.btnOp.disabled = true;
  //ɾ���հ���
  CertifyList.delBlankLine("CertifyList");
  //У���б�������һ��
  var nmulLineCount = CertifyList.mulLineCount;
  if (nmulLineCount == null || nmulLineCount <= 0)
  {
    alert("����������һ��Ҫ���ŵĵ�֤�� ");
    fm.btnOp.disabled = false;
    return false;
  }
  //У��ҳ����Ϣ
  if( verifyInput() == false || !CertifyList.checkValue("CertifyList"))
  {
    fm.btnOp.disabled = false;
    return false;
  }
  
  //add by duanyh 2009-8-18 У����Ҫ��֤���ʱ������������Ϊ30000
  if(fm.ReceiveCom.value.charAt(0)=='B'||fm.ReceiveCom.value.charAt(0)=='D'||(fm.ReceiveCom.value.charAt(0)=='A'&&fm.ReceiveCom.value.length==9))
  {
  	for(var i=1; i<=CertifyList.mulLineCount; i++)
	{
		varStartNo=CertifyList.getRowColData(i-1,5);
		varEndNo=CertifyList.getRowColData(i-1,6);
		//var strSql = "select '"+varEndNo+"'-'"+varStartNo+"'+1 from dual";
		var strSql = wrapSql(tResourceName,"querysqldes5",[varEndNo,varStartNo]);
          var arrResult = easyExecSql(strSql);
          //alert(arrResult);
		if(arrResult>30000)
		{
			alert("��"+i+"�м�¼��Ҫ��ֵĵ�֤������30000���������һ���Է��ŵĵ�֤������");
			fm.btnOp.disabled = false;
               return false;
		}
	}
  }
  

  //��Ҫ����������ȷ����Ϣ
  if (confirm("��ȷ�Ϸ��Ų�����?")){
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
		document.getElementById("fm").submit(); //�ύ
    } catch(ex) {
  	  showInfo.close();
  	  alert(ex);
    }
  }else{
    fm.btnOp.disabled = false;
    alert("��ȡ���˷��Ų�����");
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
	content="����ɹ���";
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
}

//ѡ�����󴥷��Ķ���
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
      alert("ѡ�񡾲�����Դ������");
      fm.DepartmentNo.value="";
    }
  }
  if(cName=='Department')//ѡ������Դ��
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
      if(CertifyCode!="")
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
           //CertifyList.setRowColData(i,6,'');
        }
    }
  }  
}

//��ȡ�Ǹ��˴��������ڻ������롢����
function getAgentCom()
{
	var receivecom = document.all('ReceiveCom').value;
	if(receivecom!=null && receivecom != "" && receivecom.substring(0,1) == 'D')	 
	{
		/*var strSQL="select a.agentcom,(select name from lacom where agentcom=a.agentcom) "
		+" from lacomtoagent a where a.relatype='1' "//���� 3
		+" and a.agentcode='" + receivecom.substring(1, 12) + "' "
		+" union "
		+" select b.agentcom,b.name "
		+" from lacom b where b.comtoagentflag='1' "//���� 6
		+" and b.agentcode='" + receivecom.substring(1, 12) + "' "
		+" union "
		+" select c.agentcom,(select name from lacom where agentcom=c.agentcom)  "
		+" from lacomtoagentnew c where c.relatype='2' "//�н� 7
		+" and c.agentcode='" + receivecom.substring(1, 12) + "'";*/
		var strSQL = wrapSql(tResourceName,"querysqldes3",[receivecom.substring(1, 12),receivecom.substring(1, 12),receivecom.substring(1, 12)]);
		
		if(easyQueryVer3(strSQL)!=false){
			fm.agentCom.value="";
			fm.agentComName.value="";
			fm.agentCom.CodeData = easyQueryVer3(strSQL); 	
		}else{
			fm.agentCom.value="";
			fm.agentComName.value="";
			fm.agentCom.CodeData = "";
		}
	}
}

