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



//�����Ŀר����ѯҳ��Ĳ�ѯ��ť
function queryClick2()
{
  //����������Ӧ�Ĵ���
  //mOperate="QUERY||MAIN";  
  var VersionNo = document.all('VersionNo').value;
  var VersionState = document.all('VersionState').value;
    
  if (VersionNo == null||VersionNo == '')
  {
  	alert("���Ƚ��а汾��Ϣ��ѯ��Ȼ���ٽ��п�Ŀ�̶�ר���");
  	return;
  }    
	showInfo=window.open("./FrameAssociatedDirectItemDefQuery.jsp?VersionNo=" + VersionNo + "&VersionState=" + VersionState );  
}


function addClick()
{
	 //�ύǰ�ļ���
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
  if ((document.all("ColumnID").value==null)||(trim(document.all("ColumnID").value)==''))
  {
    alert("��ȷ��Ҫɾ����ר����ֶα�ʶ��");
    return false;
  }
  if(document.all("ColumnID").value != document.all("ColumnID1").value)
  {
  	alert("��ȷ��Ҫɾ����ר����ֶα�ʶ��");
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
  if (!beforeSubmit()) //beforeSubmit()����
  {
  	return false;
  }
  if(document.all("ColumnID").value != document.all("ColumnID1").value)
  {
  	alert("��ȷ��Ҫ�޸ĵ�ר����ֶα�ʶ��");
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
  	alert("AssociatedDirectItemDefInput.js-->resetForm�����з����쳣:��ʼ���������!");
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
  fm.action="AssociatedDirectItemDefSave.jsp";
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
	var iHeight=250;     //�������ڵĸ߶�; 
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
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

//    showDiv(operateButton,"true");
//    showDiv(inputButton,"false");
    //ִ����һ������
    //resetForm();
    if(mOperate =="DELETE||MAIN")
    {
    	document.all('ColumnID').value = '';
    	document.all('ColumnID1').value = '';
   		document.all('columnidName').value = '';
   		document.all('SourceTableID').value = 'FIAboriginalData';
   		document.all('SourceColumnID').value = '';    
   		document.all('sourcecolumnName').value = '';          
   		document.all('ReMark').value = '';
   		
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

  if((fm.ColumnID.value=="")||(fm.ColumnID.value=="null"))
  {
    alert("����¼��ר����ֶα�ʶ��");
    return ;
  }
  
  if((fm.SourceTableID.value=="")||(fm.SourceTableID.value=="null"))
  {
    alert("����¼������������Դ������");
    return ;
  }

  if((fm.SourceColumnID.value=="")||(fm.SourceColumnID.value=="null"))
  {
    alert("����¼������������Դ�ֶΣ�");
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
 	else 
 	{
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
			document.all('ColumnID').value = '';
			document.all('ColumnID1').value = '';
   		document.all('columnidName').value = '';
   		document.all('SourceTableID').value = 'FIAboriginalData';
   		document.all('SourceColumnID').value = '';    
   		document.all('sourcecolumnName').value = '';          
   		document.all('ReMark').value = '';
		
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


//��Կ�Ŀר����ѯ�Ӵ��ڷ��ص�2ά����
function afterQuery2( arrQueryResult )
{
	var arrResult = new Array();
		//alert(arrQueryResult);

	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		//alert(arrResult);
		//document.all('VersionNo').value = arrResult[0][0];
		document.all('ColumnID').value = arrResult[0][1];
		document.all('ColumnID1').value = arrResult[0][1];
		document.all('SourceTableID').value = arrResult[0][2];		
		document.all('SourceColumnID').value = arrResult[0][3];
		document.all('ReMark').value = arrResult[0][4];
								
		//document.all('AssociatedID').readOnly=true;
		//document.all('ColumnID').readOnly = true;				
		//�ڿ�Ŀר����ѯ֮����ܽ����޸ĺ�ɾ������
		document.all('updatebutton').disabled = false;		
		document.all('deletebutton').disabled = false;
		//document.all('ColumnID').disabled = true;	
		//document.all('columnidName').disabled = true;
		
    //���汾״̬��Ϊ02-ά����ʱ����ɾ�İ�ťΪ��ɫ		
		if (document.all('VersionState').value == "01"||document.all('VersionState').value == "03"||document.all('VersionState').value == ""||document.all('VersionState').value == null)
		{
			document.all('addbutton').disabled = true;				
			document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true;	
		}				

		//������Common\javascript\Common.js�����ݴ���ѡ��Ĵ�����Ҳ���ʾ����
		showCodeName(); 
		//a.VersionNo,a.AssociatedID,a.AssociatedName,a.ColumnID,a.TransFlag,a.TransSQL,a.TransClass,a.ReMark
	}
}

function resetAgain()
{
			document.all('ColumnID').value = '';
			document.all('ColumnID1').value = '';
   		document.all('columnidName').value = '';
   		document.all('SourceTableID').value = 'FIAboriginalData';
   		document.all('SourceColumnID').value = '';    
   		document.all('sourcecolumnName').value = '';          
   		document.all('ReMark').value = '';
   		
   		document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true; 
}