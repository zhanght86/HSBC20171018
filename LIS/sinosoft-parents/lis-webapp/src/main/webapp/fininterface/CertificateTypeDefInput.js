//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var mOperate="";
var turnPage = new turnPageClass();
window.onfocus=myonfocus;

//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
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


//Click�¼������������ѯ��ͼƬʱ�����ú���
//����汾��Ϣ��ѯҳ��Ĳ�ѯ��ť
function queryClick1()
{
  //����������Ӧ�Ĵ���
  //mOperate="QUERY||MAIN";  
  showInfo=window.open("./FrameVersionRuleQueryForOther.jsp");
}



//����ƾ֤���Ͳ�ѯҳ��Ĳ�ѯ��ť
function queryClick2()
{
  //����������Ӧ�Ĵ���
  //mOperate="QUERY||MAIN";  
  var VersionNo = document.all('VersionNo').value;
  var VersionState = document.all('VersionState').value;
    
  if (VersionNo == null||VersionNo == '')
  {
  	alert("���Ƚ��а汾��Ϣ��ѯ��Ȼ���ٽ���ƾ֤���Ͷ���");
  	return;
  }    
	showInfo=window.open("./FrameCertificateTypeDefQuery.jsp?VersionNo=" + VersionNo + "&VersionState=" + VersionState );  
}


function addClick()
{
	  if (!beforeSubmit()) //beforeSubmit()����
  {
  	return false;
  }
	//Ϊ�˷�ֹ˫����������Ӻ�����"����"��ť
  mOperate="INSERT||MAIN";
  submitForm();
}


//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  //����������Ӧ��ɾ������
  if((fm.VersionNo.value=="")||(fm.VersionNo.value=="null"))
  {
    alert("����¼��汾��ţ�");
    return ;
  }
  if ((document.all("CertificateID").value==null)||(trim(document.all("CertificateID").value)==''))
  {
    alert("��ȷ��Ҫɾ����ƾ֤���ͱ�ţ�");
    return false;
  }
  
  else
  {

    if (confirm("��ȷʵ��ɾ���ü�¼��?"))
    {
      mOperate="DELETE||MAIN";
      submitForm();
    }
    else
    {
      mOperate="";
      alert("��ȡ����ɾ��������");
    }
  }
}


function updateClick()
{
    if (!beforeSubmit()) //beforeSubmit()����
  {
  	return false;
  }
  else
  {
    if (confirm("��ȷʵ���޸ĸü�¼��?"))
    {
      mOperate="UPDATE||MAIN";
      submitForm();
    }
    else
    {
      mOperate="";
      alert("��ȡ�����޸Ĳ�����");
    }
  }
}


//����ƾ֤�������Ͷ���ҳ��
function intoCostTypeDef()
{
  //����������Ӧ�Ĵ���
  //mOperate="QUERY||MAIN";
  var VersionNo = document.all('VersionNo').value;
  var VersionState = document.all('VersionState').value;
  var CertificateID = document.all('CertificateID').value;
    
  if (VersionNo == null||VersionNo == '')
  {
  	alert("���Ƚ��а汾��Ϣ��ѯ��Ȼ���ٽ���ƾ֤�������Ͷ���");
  	return;
  }
  
  if (CertificateID == null||CertificateID == '')  
  {
  	alert("���Ƚ��п�Ŀ��Ϣ��ѯ��Ȼ���ٽ���ƾ֤�������Ͷ���");
  	return;
  }          
	var countjudge = "";
  var strSQL = "";
  /**
  strSQL = "select count(*) from ficertificatetypedef where versionno = '"+document.all('VersionNo').value+"' and CertificateID = '"+document.all('CertificateID').value+"' ";
  */
  var mySql1=new SqlClass();
	  mySql1.setResourceName("fininterface.CertificateTypeDefInputSql"); //ָ��ʹ�õ�properties�ļ���
	  mySql1.setSqlId("CertificateTypeDefInputSql1");//ָ��ʹ�õ�Sql��id
	  mySql1.addSubPara(document.all('VersionNo').value);//ָ������Ĳ���
	  mySql1.addSubPara(document.all('CertificateID').value);//ָ������Ĳ���
	  strSQL= mySql1.getString();
  
  //alert(strSQL);
  countjudge = easyQueryVer3(strSQL, 1, 0, 1); 
 	countjudge = trim(countjudge.substr(4));
  //alert(countjudge);
  if(countjudge == "0")
  {
  	alert("����ƾ֤��Ϣ�����ڣ���ȷ�������Ӹ�����Ϣ���ٽ���ƾ֤�������Ͷ���");
  	return false;
  }
  //alert(VersionNo);
  showInfo=window.open("./FrameCostTypeDefInput.jsp?VersionNo=" + VersionNo + "&VersionState=" + VersionState + "&CertificateID="+CertificateID);
	//parent.fraInterface.window.location = "./ProposalGrpInput.jsp?LoadFlag=" + LoadFlag + "&type=" + type;  
	//showInfo=window.open("./LABranchGroupQuery.jsp?BranchType='"+document.all('BranchType').value + "'");  
}


//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
//    showDiv(operateButton,"true");
//    showDiv(inputButton,"false");
	  initForm();
  }
  catch(re)
  {
  	alert("CertificateTypeDefInput.js-->resetForm�����з����쳣:��ʼ���������!");
  }
}


//ȡ����ť��Ӧ����
function cancelForm()
{
//  window.location="../common/html/Blank.html";
    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
}



function submitForm()
{

  //�ύǰ�ļ���


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

  fm.hideOperate.value=mOperate;
  if (fm.hideOperate.value=="")
  {
    alert("�����������ݶ�ʧ��");
  }
  fm.action="CertificateTypeDefSave.jsp";
  //lockButton(); 
  fm.submit(); //�ύ

}



//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  //�ͷš����ӡ���ť

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
    //resetForm();
		mOperate="";
  }
  else
  {
    //alert(content);
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

//    showDiv(operateButton,"true");
//    showDiv(inputButton,"false");
    //ִ����һ������
    //resetForm();
    if(mOperate=="DELETE||MAIN")
    {
    	 document.all('CertificateID').value = '';   	
    	 document.all('CertificateName').value = '';  	 
    	 document.all('DetailIndexID').value = '';
    	 document.all('DetailIndexName').value = '';
    	 document.all('AcquisitionType').value = '';
    	 document.all('AcquisitionTypeName').value = '';                                 
    	 document.all('Remark').value = ''; 
    	 
    	 document.all('CertificateID').readOnly = false;
    	 document.all('updatebutton').disabled = true;		
			 document.all('deletebutton').disabled = true; 
    }
    mOperate="";
  }

}



//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";
  }
}


//�ύǰ��У�顢����
function beforeSubmit()
{
  if((fm.VersionNo.value=="")||(fm.VersionNo.value=="null"))
  {
    alert("����¼��汾��ţ�");
    return ;
  }

  if((fm.CertificateID.value=="")||(fm.CertificateID.value=="null"))
  {
    alert("����¼��ƾ֤���ͱ�ţ�");
    return ;
  }
  
  if((fm.DetailIndexID.value=="")||(fm.DetailIndexID.value=="null"))
  {
    alert("����¼����ϸ�������룡");
    return ;
  }

  if((fm.AcquisitionType.value=="")||(fm.AcquisitionType.value=="null"))
  {
    alert("����¼��������Դ��");
    return ;
  }
  
  if (!verifyInput2())
  {
  	return false;
  }  
   
    return true;
}


//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}



/*********************************************************************
 *  ��ѯ������ϸ��Ϣʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
 *  ����  ��  ��ѯ���صĶ�ά����
 *  ����ֵ��  ��
 *********************************************************************
 */
//��԰汾��Ϣ��ѯ�Ӵ��ڷ��ص�2ά����
function afterQuery( arrQueryResult )
{
	var arrResult = new Array();
		//alert(arrQueryResult);

	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		//alert(arrResult);
		document.all('VersionNo').value = arrResult[0][0];		
		//VersionState��ʾΪ01��02��03����VersionState2��ʾΪ������ά����ɾ��
		document.all('VersionState').value = arrResult[0][1];		
		document.all('VersionState2').value = arrResult[0][2];								
		//document.all('VersionNo').readOnly=true;
		//document.all('VersionState').readOnly=true;
			 document.all('CertificateID').value = '';   	
    	 document.all('CertificateName').value = '';  	 
    	 document.all('DetailIndexID').value = '';
    	 document.all('DetailIndexName').value = '';
    	 document.all('AcquisitionType').value = '';
    	 document.all('AcquisitionTypeName').value = '';                                 
    	 document.all('Remark').value = ''; 
		document.all('CertificateID').readOnly = false;
    //���汾״̬��Ϊ02-ά����ʱ����ɾ�İ�ťΪ��ɫ		
		if (arrResult[0][1] == "01"||arrResult[0][1] == "03"||arrResult[0][1] == ""||arrResult[0][1] == null)
		{
			document.all('addbutton').disabled = true;				
			document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true;	
		}
		if (arrResult[0][1] == "02")
		{
			document.all('addbutton').disabled = false;				
			document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true;	
		}
		
		//������Common\javascript\Common.js�����ݴ���ѡ��Ĵ�����Ҳ���ʾ����
		showCodeName(); 
		//a.VersionNo,a.VersionState
	}
}

function test()
{
	document.all('AcquisitionType').value = '01';
	showCodeName(); 
}

//���ƾ֤���Ͳ�ѯ�Ӵ��ڷ��ص�2ά����
function afterQuery2( arrQueryResult )
{
	var arrResult = new Array();
		//alert(arrQueryResult);

	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		//alert(arrResult);
		//document.all('VersionNo').value = arrResult[0][0];
		document.all('CertificateID').value = arrResult[0][1];
		document.all('CertificateName').value = arrResult[0][2];		
		document.all('DetailIndexID').value = arrResult[0][3];
		document.all('DetailIndexName').value = arrResult[0][4];		
		document.all('AcquisitionType').value = arrResult[0][5];
		document.all('Remark').value = arrResult[0][6];
								
		document.all('CertificateID').readOnly = true;						
		
		//��ƾ֤���Ͳ�ѯ֮����ܽ����޸ĺ�ɾ������
		document.all('updatebutton').disabled = false;		
		document.all('deletebutton').disabled = false;  	
		
    //���汾״̬��Ϊ02-ά����ʱ����ɾ�İ�ťΪ��ɫ		
		if (document.all('VersionState').value == "01"||document.all('VersionState').value == "03"||document.all('VersionState').value == ""||document.all('VersionState').value == null)
		{
			document.all('addbutton').disabled = true;				
			document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true;	
		}				

		//������Common\javascript\Common.js�����ݴ���ѡ��Ĵ�����Ҳ���ʾ����
		showCodeName(); 
		//a.VersionNo,a.CertificateID,a.CertificateName,a.DetailIndexID,a.DetailIndexName,a.AcquisitionType,a.Remark
	}
}

function resetAgain()
{
			 document.all('CertificateID').value = '';   	
    	 document.all('CertificateName').value = '';  	 
    	 document.all('DetailIndexID').value = '';
    	 document.all('DetailIndexName').value = '';
    	 document.all('AcquisitionType').value = '';
    	 document.all('AcquisitionTypeName').value = '';                                 
    	 document.all('Remark').value = ''; 
    	 
    	 document.all('CertificateID').readOnly = false;
    	 document.all('updatebutton').disabled = true;		
			 document.all('deletebutton').disabled = true; 
}