//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var mOperate="";
var turnPage = new turnPageClass();
window.onfocus=myonfocus;

//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus()
{
	if(showInfo!=null) //shwoInfo��ʲô��
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


//����ҳ���Զ���ѯ
function initCostTypeDefQuery() 
{		
  	var strSQL = "";
  	/**
  	strSQL = "select VersionNo,CertificateID,CostID,CostName,FinItemType,decode(FinItemType,'C','����','D','�跽','����'),FinItemID,FinItemName,Remark,State from FICostTypeDef where ";
		strSQL = strSQL + " VersionNo ='"+VersionNo+"' and CertificateID ='"+CertificateID+"' ";
  	*/
  	//alert(strSQL);
  	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.CostTypeDefInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("CostTypeDefInputSql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(VersionNo);//ָ������Ĳ���
		mySql1.addSubPara(CertificateID);//ָ������Ĳ���
		strSQL= mySql1.getString();
  	
  	turnPage.queryModal(strSQL, CostTypeDefGrid);
} 



function addClick()
{
	if (!beforeSubmit()) //beforeSubmit()����
  {
  	return false;
  }
  mOperate="INSERT||MAIN";
  submitForm();
}


//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  //�ύǰ�ļ���
  if (!beforeSubmit2())
  {
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
  //�ύǰ�ļ���
  if (!beforeSubmit2())
  {
  	return false;
  }		
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
  	alert("��CostTypeDefInput.js-->resetForm�����з����쳣:��ʼ���������!");
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
  fm.action="CostTypeDefSave.jsp";
  //lockButton(); 
  document.getElementById("fm").submit(); //�ύ

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
    		document.all('State').value = '02'; 
    		document.all('CostID').value = '';
    		document.all('CostName').value = '';
    		document.all('FinItemType').value = '';
    		document.all('FinItemTypeName').value = '';
    		document.all('FinItemID').value = '';
    		document.all('FinItemName').value = '';
    		document.all('Remark').value = '';
    		
    		document.all('CostID').readOnly = false;	
    }
    mOperate="";
    initCostTypeDefQuery();    
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


//���ӡ�ɾ�����޸��ύǰ��У�顢����
function beforeSubmit()
{			
  if((fm.VersionNo.value=="")||(fm.VersionNo.value==null))
  {
    alert("����¼��汾��ţ�");
    return ;
  }
  
  if((fm.CertificateID.value=="")||(fm.CertificateID.value==null))
  {
    alert("����¼��ƾ֤���ͱ�ţ�");
    return ;
  }

  if((fm.CostID.value=="")||(fm.CostID.value==null))
  {
    alert("����¼��������ͱ��룡");
    return ;
  }
  
  if((fm.CostName.value=="")||(fm.CostName.value==null))
  {
    alert("����¼������������ƣ�");
    return ;
  }
  
  if((fm.FinItemType.value=="")||(fm.FinItemType.value==null))
  {
    alert("����¼������־��");
    return ;
  }
  
  if((fm.FinItemID.value=="")||(fm.FinItemID.value==null))
  {
    alert("����¼���Ŀ���ͱ��룡");
    return ;
  }
  
  if((fm.FinItemName.value=="")||(fm.FinItemName.value==null))
  {
    alert("����¼���Ŀ�������ƣ�");
    return ;
  }  
  
/*  
  if((fm.State.value=="")||(fm.State.value==null))
  {
    alert("����¼��״̬��");
    return ;
  }
*/
  
  if (!verifyInput2()) 
  {
  	return false;
  }
    
    return true;
}


//ɾ�����޸�ǰ����ѡ��һ����¼
function beforeSubmit2()
{		
	var tSel = CostTypeDefGrid.getSelNo();	
	var strSQL="";
	if( tSel == 0 || tSel == null )
	{
		alert( "������ѡ��һ����¼���ٽ����޸ĺ�ɾ���Ĳ���!" );
		return;
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


//ѡ��mulline��һ�У��Զ��������ֵ
 function ShowCostTypeDef()
{
 	var arrResult = new Array();

	var tSel = CostTypeDefGrid.getSelNo();	
	var strSQL="";
	if( tSel == 0 || tSel == null )
	{
		alert( "����ѡ��һ����¼!" );
		return;
	}			
	else
	{
	//������Ҫ���ص�����
	// ��дSQL���		
  	var strSQL = "";
  	/**
		strSQL = "select a.VersionNo,a.CertificateID,a.CostID,a.CostName,a.FinItemType,a.FinItemID,a.FinItemName,a.Remark,a.State from FICostTypeDef a where a.VersionNo='"+
	          CostTypeDefGrid.getRowColData(tSel-1,1)+"' and a.CertificateID='"+
	          CostTypeDefGrid.getRowColData(tSel-1,2)+"' and a.CostID='"+
	          CostTypeDefGrid.getRowColData(tSel-1,3)+"'";
	          */
		//alert(strSQL);
		var mySql2=new SqlClass();
			mySql2.setResourceName("fininterface.CostTypeDefInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql2.setSqlId("CostTypeDefInputSql2");//ָ��ʹ�õ�Sql��id
			mySql2.addSubPara(CostTypeDefGrid.getRowColData(tSel-1,1));//ָ������Ĳ���
			mySql2.addSubPara(CostTypeDefGrid.getRowColData(tSel-1,2));//ָ������Ĳ���
			mySql2.addSubPara(CostTypeDefGrid.getRowColData(tSel-1,3));//ָ������Ĳ���
			strSQL= mySql2.getString();
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) 
  {
    alert("��ѯʧ�ܣ�");
    return false;
  }
	//��ѯ�ɹ������ַ��������ض�ά����
  	arrResult = decodeEasyQueryResult(turnPage.strQueryResult); 
  	document.all('VersionNo').value = arrResult[0][0];
  	//alert(VersionNo);  
  	document.all('CertificateID').value = arrResult[0][1];
  	//alert(CertificateID);    	
  	document.all('CostID').value = arrResult[0][2];
  	document.all('CostName').value = arrResult[0][3];
  	document.all('FinItemType').value = arrResult[0][4];  	  	  	  	  	
  	document.all('FinItemID').value = arrResult[0][5];  	  	  	  	
  	document.all('FinItemName').value = arrResult[0][6];  	  	  	  	
  	document.all('Remark').value = arrResult[0][7];  	  	
		document.all('State').value = arrResult[0][8]; 
		showCodeName(); 	  	  	
	}	 
	//ѡ��mulline��ֵ֮��JudgementNo���ܽ����޸ģ�ֻ���޸ķ�������Ϣ
		document.all('CostID').readOnly = true;	
		//document.all('CostID').disabled = true;	
}

function resetAgain()
{
			 	document.all('State').value = '02'; 
    		document.all('CostID').value = '';
    		document.all('CostName').value = '';
    		document.all('FinItemType').value = '';
    		document.all('FinItemTypeName').value = '';
    		document.all('FinItemID').value = '';
    		document.all('FinItemName').value = '';
    		document.all('Remark').value = '';
    	 
				document.all('CostID').readOnly = false;	 
}