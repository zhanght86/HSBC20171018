//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var mOperation = "";
window.onfocus=myonfocus;
var turnPage1 = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var tResourceName="certify.SysCertTakeBackInputSql";
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


function isCheckSignature(){
    var tFlag = fm.SignatureFlag.value;
    if(tFlag=="false"){
       alert("δ�˶�ǩ��������¼���ִ��");
       return false;
    }
    return true;
}

function checkScan(){
     var lastNo = fm.LastCertifyNo.value;
     var nowNo = fm.CertifyNo.value;
     if(lastNo==nowNo){
     }else{
        fm.SignatureFlag.value="false";
     }
     fm.LastCertifyNo.value = fm.CertifyNo.value;
}

function queryScanSignature(){
   fm.SignatureFlag.value="true";
   //window.open("./ContInputScanMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&SubType="+SubType+"&scantype=scan", "", sFeatures);        
   var sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1";
//   var sFeatures = "'height=10, width=50, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no'";
   //var tSql ="select prtno from lccont where contno = '"+fm.CertifyNo.value+"'";
   var tSql = wrapSql(tResourceName,"querysqldes1",[fm.CertifyNo.value]);
   //var tSql ="select prtno from lcpol where polno = '210130000000041'";
   tPrtNo = easyExecSql(tSql);
   //window.open("./DisplayScanSignature.jsp?ScanFlag=1&scantype=scan&prtNo="+tPrtNo, "", sFeatures);        
   //window.open("./DisplayScanSignature.jsp?ScanFlag=1&scantype=scan&prtNo=86160100596242&SubType=TB1001", "", sFeatures);        
   window.open("./DisplayScanSignature.jsp?ScanFlag=1&scantype=scan&prtNo="+tPrtNo+"&SubType=TB1001", "", sFeatures);        
}

//У��¼��Ļ������ڱ���¼�����Σ��������α���һ��
function checkDouble()
{

	if(document.all('TakeBackDate').value==null||document.all('TakeBackDate').value=="")
	{
		alert("��ִ���ڲ���Ϊ��");
		return false;
	}
	
	if(document.all('hideTakeBackDate').value==null||document.all('hideTakeBackDate').value=="")
	{
		document.all('hideTakeBackDate').value=document.all('TakeBackDate').value;
		document.all('TakeBackDate').value="";
	}
	else if(document.all('hideTakeBackDate').value!=document.all('TakeBackDate').value)
	{
		alert("�����������ڲ���,������ȷ��");
		document.all('hideTakeBackDate').value="";
		document.all('TakeBackDate').value="";
      	return false;
	}
}


//�ύ�����水ť��Ӧ����
function submitForm()
{
     //var sql1="select * from lmcertifydes where JugAgtFlag in ('1','3') " +getWherePart('CertifyCode', 'CertifyCode');
	var sql1 = wrapSql(tResourceName,"querysqldes2",[document.all('CertifyCode').value]);
//     var arrResult1 = easyExecSql(sql1);
     
        //alert(arrResult1);
      //tongmeng 2009-05-26 modify
      //������ʾ
      /*
     if(arrResult1!=null)
     {
        //alert(sql1);
        if(!confirm("�õ�֤���ղ���У�飬�Ƿ���գ���"))
        {
            return;
        }
     }*/
     
     //�������ڱ���¼��
     //
     if(document.all('TakeBackDate').value==null||document.all('TakeBackDate').value=="")
     {
     	alert("�������ڲ���Ϊ��!");
      	return false;
     }

	if(isCheckSignature()==false) return false;
    //var strSql = "select AgentState from laagent where agentcode = '" + document.all('SendOutCom').value.substring(1) + "' ";
    var strSql = wrapSql(tResourceName,"querysqldes3",[document.all('SendOutCom').value.substring(1)]);
    //prompt(strSql);
     var strResult = easyExecSql(strSql);
    
    if (strResult==null || strResult=="") {
      alert("��ҵ��Ա������");
      return false;
    }
    
    if(strResult!="01" && strResult!="02")
    {
        if(!confirm("��ҵ��Ա�Ѿ�ʧЧ���Ƿ�ȷ�ϻ��մ˵�֤��"))
        {
            return ;
        }
    }    
	if(verifyInput() == true) {
//	  if(fm.CertifyCode.value == "9995")		//���Ǹ�����ִ�Ļ�Ҳ��Ҫ¼��������ڣ�����
		if(fm.TakeBackDate.value == "")
		{
			alert("����¼���������!");
			return ;
		}
		
		//var strSql = "select * from LZSysCertify where CertifyCode='" + fm.CertifyCode.value + "' and CertifyNo='"+ fm.CertifyNo.value + "' and stateflag='1'";
		var strSql = wrapSql(tResourceName,"querysqldes4",[fm.CertifyCode.value,fm.CertifyNo.value]);
		var arrResult = easyExecSql(strSql);
		if (arrResult != null) 
		{
			alert("���棺�õ�֤�ѻ��գ���������ж��λ��գ�");
			return false;
		}
		
	  var i = 0;
	  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
	  // showSubmitFrame(mDebug);
	  fm.hideOperation.value = "INSERT||MAIN";
	  document.getElementById("fm").submit(); //�ύ
	}
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
    content="����ɹ���";
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px"); 
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    top.opener.easyQueryClick();
    top.close();
  }
}


//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("��Proposal.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//ȡ����ť��Ӧ����
function cancelForm()
{
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
}
 
//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���	
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

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
  //����������Ӧ�Ĵ���
  mOperation = "QUERY||MAIN";
  fm.sql_where.value = " and StateFlag = '1' ";
  fm.QUERY_FLAG.value='1';
  showInfo = window.open("./SysCertTakeBackQuery.html","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

function query()
{
	mOperation = "QUERY||MAIN";
	fm.sql_where.value = " and StateFlag = '0' ";
	fm.QUERY_FLAG.value='0';
	showInfo = window.open("SysCertTakeBackQuery.html","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery(arrResult)
{   
	try {
	  if(arrResult!=null)
	  {
	  	fm.CertifyCode.value = "";
			fm.CertifyNo.value = "";
			fm.ValidDate.value = "";
			fm.SendOutCom.value = "";
			fm.ReceiveCom.value = "";
			fm.Handler.value = "";
			fm.HandleDate.value = "";
			fm.SendNo.value = "";
			fm.TakeBackNo.value = "";
			fm.Operator.value = "";
			fm.MakeDate.value = "";
			fm.TakeBackDate.value = "";
			//tongmeng 2007-12-28 modify
			//�����Ѿ����չ��ĵ�֤���������
	  	if(arrResult[0][12]!=null&&arrResult[0][12]=='1')
	  	{
	  		alert("�õ�֤�Ѿ�����!");
	  		return false;
	  	}
	  	//tongmeng 2007-12-29 add
	  	//���ӶԱ����Ƿ��ӡ�����ж�
	  	//var tSQL = " select '1' from lccont where contno='"+arrResult[0][1]+"' and printcount<=0 ";
	  	var tSQL = wrapSql(tResourceName,"querysqldes5",[arrResult[0][1]]);
	  	tempResult = easyExecSql(tSQL);
	  	//alert(tempResult);
				if(tempResult!=null)
				{
					//if(tempResult[0][0]=='1')
					{
						alert('�ñ���δ��ӡ,���������');
						return false;
					}
				}
				
			fm.CertifyCode.value = arrResult[0][0];
			fm.CertifyNo.value = arrResult[0][1];
			fm.ValidDate.value = arrResult[0][5];
			fm.SendOutCom.value = arrResult[0][3];
			fm.ReceiveCom.value = arrResult[0][2];
			fm.Handler.value = arrResult[0][6];
			fm.HandleDate.value = arrResult[0][7];
			fm.SendNo.value = arrResult[0][8];
			fm.TakeBackNo.value = arrResult[0][9];
			fm.Operator.value = arrResult[0][4];
			fm.MakeDate.value = arrResult[0][10];
			//fm.TakeBackDate.value = arrResult[0][11];
		}
	} catch (ex) {
		alert("��afterQuery�з�������");
	}
}
function confirmSecondInput1(aObject,aEvent){
	if(aEvent=="onkeyup"){
	  var theKey = window.event.keyCode;
	  if(theKey=="13"){
	    if(theFirstValue!=""){
      	theSecondValue = aObject.value;
	      if(theSecondValue==""){
	      	alert("���ٴ�¼�룡");
	        aObject.value="";
	        aObject.focus();
	        return;
	      }
	      if(theSecondValue==theFirstValue){
	        aObject.value = theSecondValue;
	        return;
	      }
	      else{
	        alert("����¼����������������¼�룡");
	        theFirstValue="";
	        theSecondValue="";
	        aObject.value="";
	        aObject.focus();
	        return;
	      }
	    }
      else{
      	theFirstValue = aObject.value;
      	if(theFirstValue==""){
      		theSecondValue="";
      	  alert("��¼�����ݣ�");
      	  return;
      	}
      	aObject.value="";
      	aObject.focus();
        return;
      }
    }
  }
  else if(aEvent=="onblur"){
	  //alert("theFirstValue="+theFirstValue);
	  if(theFirstValue!=""){
    	theSecondValue = aObject.value;
	    if(theSecondValue==""){
	    	alert("���ٴ�¼�룡");
	      aObject.value="";
	      aObject.focus();
	      return;
	    }
	    if(theSecondValue==theFirstValue){
	      aObject.value = theSecondValue;
	      theSecondValue="";
	      theFirstValue="";
	      return;
	    }
	    else{
	      alert("����¼����������������¼�룡");
	      theFirstValue="";
	      theSecondValue="";
	      aObject.value="";
	      aObject.focus();
	      return;
	    }
	  }
    else{
    	theFirstValue = aObject.value;
     	theSecondValue="";
    	if(theFirstValue==""){
      	//alert("aa");
    	  return;
    	}
    	aObject.value="";
    	aObject.focus();
      return;
    }
  }
}

function queryagent()
{//alert(358);
		if(trim(fm.CertifyCode.value)=='')
		{
			 alert("�������뵥֤����");
			 fm.CertifyNo.value ='';
			 return false;
		}
		if(fm.CertifyNo.value!=""&&fm.CertifyNo.value!=null)
		{
			var arrResult;
			if(trim(fm.CertifyCode.value)=="9995")
			{
				//var sqlStr="select certifycode,certifyno,sendoutcom,receivecom,operator,validdate,handler,handledate,sendno,takebackno,makedate,takebackdate,StateFlag from LZSysCertify  where CertifyNo='"+fm.CertifyNo.value+"' and CertifyCode='9995' ";
				var sqlStr = wrapSql(tResourceName,"querysqldes6",[fm.CertifyNo.value]);
				arrResult = easyExecSql(sqlStr);//alert(arrResult);
				if(arrResult!=null) { afterQuery(arrResult);}
				else
				{	//alert(374);
						//var sqlStr = "select agentcode,managecom from lccont where contno = '"+fm.CertifyNo.value+"'";
						var sqlStr = wrapSql(tResourceName,"querysqldes7",[fm.CertifyNo.value]);
						arrResult = easyExecSql(sqlStr);
						if(arrResult!=null)
						{
							document.all('ReceiveCom').value = "A"+arrResult[0][1];
							document.all('SendOutCom').value = "D"+arrResult[0][0];
						}
						else
						{alert("δ�鵽��Ӧ��Ϣ�����鱣�����Ƿ���������!");}
						
							  	//tongmeng 2007-12-29 add
	  							//���ӶԱ����Ƿ��ӡ�����ж�
	  							//var tSQL = " select '1' from lccont where contno='"+fm.CertifyNo.value+"' and printcount<=0 ";
	  							var tSQL = wrapSql(tResourceName,"querysqldes5",[fm.CertifyNo.value]);
	  							tempResult = easyExecSql(tSQL);
	  							//alert(tempResult);
									if(tempResult!=null)
									{
										//if(tempResult[0][0]=='1')
										{
												alert('�ñ���δ��ӡ,���������');
												fm.CertifyNo.value ='';
												document.all('ReceiveCom').value = '';
												document.all('SendOutCom').value = '';
												return false;
										}
									}
				}
			}
			else
			{
				//var sqlStr="select certifycode,certifyno,sendoutcom,receivecom,operator,validdate,handler,handledate,sendno,takebackno,makedate,takebackdate from LZSysCertify  where CertifyNo='"+fm.CertifyNo.value+"'";
				var sqlStr = wrapSql(tResourceName,"querysqldes8",[fm.CertifyNo.value]);
				arrResult = easyExecSql(sqlStr);
				if(arrResult!=null)
				  { afterQuery(arrResult);}
				else
				  { alert("δ�鵽��Ӧ��Ϣ�����鵥֤���Ƿ���������!");}
 	 
			}
		}
}

/**
 * 2009-11-24
 * ���ڻ��������޸�Ϊ��ɨ�����գ����Դ�����ɨ���ǩ��������������˴����ӹ��ܣ����Զ��Ѿ�ɨ��Ļ�ִ��ɾ������
 * */
function DELPic(){
	lockScreen('lkscreen');
	if(!confirm("ȷ��Ҫɾ����ɨ��Ļ�ִ��")){
		unlockScreen('lkscreen');
		return false;
	}
	var showStr="����ɾ��ɨ����������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();	
	fm.action="DelPicture.jsp";
	document.getElementById("fm").submit();
}

//ɾ��ɨ����������afterSubmit
function afterSubmit2( FlagStr, content ) {
	showInfo.close();
	unlockScreen('lkscreen');
	if (FlagStr == "Fail" ) {             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else { 
		content="ɾ���ɹ���";
		var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		top.opener.easyQueryClick();
		top.close();
	}
}

function setTakeBackDateFocus(){
	goToPic(0); top.fraPic.scrollTo(350, 370); showPosition(870+hx, 400+hy, 300, 60);
}
