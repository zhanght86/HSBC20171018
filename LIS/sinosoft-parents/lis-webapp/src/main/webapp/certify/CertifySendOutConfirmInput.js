//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var turnPage = new turnPageClass();
window.onfocus=myonfocus;
var tResourceName="certify.CertifySendOutConfirmInputSql";
function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();  
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}

//��ѯ����������������
function queryClick()
{
  initCertifyList();
  /*var strSql = "select a.applyno, a.certifycode, (select certifyname from lmcertifydes b where b.certifycode = a.certifycode),"
			 +"a.sendoutcom,(select nvl(sum(c.sumcount), 0) from lzcard c where c.certifycode = a.certifycode and c.receivecom = a.sendoutcom and c.stateflag in ('2', '3')),"
			 +"a.receivecom,(select nvl(sum(d.sumcount), 0) from lzcard d where d.certifycode = a.certifycode and d.receivecom = a.receivecom and d.stateflag in ('2', '3')),"
			 +"a.startno, a.endno, a.sumcount, a.reason from lzcardapp a where a.OperateFlag='1' and a.stateflag='2' "
			 + getWherePart('a.sendoutcom', 'SendOutCom')
			 + getWherePart('a.receivecom', 'ReceiveCom')
			 + getWherePart('a.ApplyCom', 'ComCode', 'like');
	 strSql+=" and (a.sendoutcom='A" + fm.ComCode.value + "' or a.sendoutcom like 'B" + fm.ComCode.value + "%')";
	 strSql+=" order by a.certifycode,a.sendoutcom,a.receivecom"*/
		 var strSql = wrapSql(tResourceName,"querysqldes3",[fm.SendOutCom.value,fm.ReceiveCom.value,fm.ComCode.value,fm.ComCode.value,fm.ComCode.value]);
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1); 
  if (!turnPage.strQueryResult) {
 	 alert("û���������ļ�¼��");
     return false;
    }else{
  	  turnPage.pageDivName = "divCertifyList";
  	  turnPage.queryModal(strSql, CertifyList);
  	  divCertifyList.style.display='';
	}
}

//�ύ�����水ť��Ӧ����
function submitForm()
{
  CertifyList.delBlankLine("CertifyList");
  var nSelectedCount = CertifyList.getChkCount();
  if (nSelectedCount == null || nSelectedCount <= 0)
  {
    alert("������ѡ��һ��Ҫ���췢�ŵĵ�֤�� ");
    return false;
  }
  
  if (confirm("��ȷ�����췢�Ų�����?")){
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
    alert("��ȡ�������췢�Ų�����");
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
	queryClick();
  }  
}

//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���	
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

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1") {
	parent.fraMain.rows = "0,0,50,82,*";
  }	else {
  	parent.fraMain.rows = "0,0,0,82,*";
  }
}

function showDiv(cDiv,cShow)
{
	if( cShow == "true" )
		cDiv.style.display = "";
	else
		cDiv.style.display = "none";  
}

// ��ѯӡˢ�ŵĹ���
function queryPrtNo()
{
	fm.sql_where.value = " State = '1' ";
  showInfo = window.open("./CertifyPrintQuery.html");
}

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery(arrResult)
{
  if(arrResult!=null)
  {
  	if( fm.chkModeBatch.checked == false ) {
	    fm.PrtNoEx.value = arrResult[0][0];
	    fm.ReceiveCom.value = 'A' + arrResult[0][11];
	    
	    CertifyList.clearData();
	    CertifyList.addOne();
	    
			var rowCount = 0;

	    CertifyList.setRowColData(rowCount, 1, arrResult[0][1]);
	    CertifyList.setRowColData(rowCount, 3, arrResult[0][18]);
	    CertifyList.setRowColData(rowCount, 4, arrResult[0][19]);
	  } else {
	    fm.ReceiveCom.value = arrResult[0][3];
	    fm.ReceiveCom.title = arrResult[0][1];
	    
	    CertifyList.clearData();
	    CertifyList.addOne();
		}
  }
}

//��������ģʽ���л�����
function changeMode(objCheck)
{
	CertifyList.clearData();
	if(objCheck.checked == true) {
		fm.btnOp.value = "��������";
		
		fm.btnQueryCom.style.display = "";
		
		fm.chkPrtNo.disabled = true;
		
	} else {
		fm.btnOp.value = "���ŵ�֤";
		
		fm.btnQueryCom.style.display = "none";
		
		fm.chkPrtNo.disabled = false;
	}
}

//�������š���ѯ������������ĺ�����
function queryCom()
{
	fm.sql_where.value = "";
  showInfo = window.open("./AgentTrussQuery.html");
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
			/*var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";*/
			    var sqlid1="querysqldes5";
				var mySql1=new SqlClass();
				mySql1.setResourceName("certify.CertifySendOutConfirmInputSql");//ָ��ʹ�õ�properties�ļ���
				mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
				mySql1.addSubPara(cAgentCode);//ָ������Ĳ���
				var strSQL1=mySql1.getString();
	    	var arrResult = easyExecSql(strSQL1);
	    	if (arrResult != null) 
	      		alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
	    	else
				alert("����Ϊ:["+ cAgentCode +"]�Ĵ����˲����ڣ���ȷ��!");
		}
	}
}
