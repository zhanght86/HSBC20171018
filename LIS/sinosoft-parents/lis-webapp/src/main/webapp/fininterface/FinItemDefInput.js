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



//�����Ŀ���Ͷ����ѯҳ��Ĳ�ѯ��ť
function queryClick2()
{
  //����������Ӧ�Ĵ���
  //mOperate="QUERY||MAIN";  
  var VersionNo = document.all('VersionNo').value;
  var VersionState = document.all('VersionState').value;
    
  if (VersionNo == null||VersionNo == '')
  {
  	alert("���Ƚ��а汾��Ϣ��ѯ��Ȼ���ٽ��п�Ŀ���Ͷ���");
  	return;
  }    
	showInfo=window.open("./FrameFinItemDefQuery.jsp?VersionNo=" + VersionNo + "&VersionState=" + VersionState );  
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
  if ((document.all("FinItemID").value==null)||(trim(document.all("FinItemID").value)==''))
    alert("��ȷ��Ҫɾ���Ŀ�Ŀ��ţ�");
  
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


//�����Ŀ����ר���ҳ��
function intoAssociatedDef()
{
  //����������Ӧ�Ĵ���
  //mOperate="QUERY||MAIN";
  var VersionNo = document.all('VersionNo').value;
  var VersionState = document.all('VersionState').value;
  var FinItemID = document.all('FinItemID').value;
    
  if (VersionNo == null||VersionNo == '')
  {
  	alert("���Ƚ��а汾��Ϣ��ѯ��Ȼ���ٽ��п�Ŀ����ר���");
  	return;
  }
  
  if (FinItemID == null||FinItemID == '')  
  {
  	alert("���Ƚ��п�Ŀ���Ͷ����ѯ��Ȼ���ٽ��п�Ŀ����ר���");
  	return;
  }          
	var countjudge = "";
  var strSQL = "";
  /**
  strSQL = "select count(*) from FIFinItemDef where versionno = '"+document.all('VersionNo').value+"' and FinItemID = '"+document.all('FinItemID').value+"' ";
  */
  //alert(strSQL);
  	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.FinItemDefInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("FinItemDefInputSql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(document.all('VersionNo').value);//ָ������Ĳ���
		mySql1.addSubPara(document.all('FinItemID').value);//ָ������Ĳ���
		strSQL= mySql1.getString();
  
  countjudge = easyQueryVer3(strSQL, 1, 0, 1); 
 	countjudge = trim(countjudge.substr(4));
  //alert(countjudge);
  if(countjudge == "0")
  {
  	alert("�����Ŀ��Ϣ�����ڣ���ȷ�������Ӹ�����Ϣ���ٽ��п�Ŀ����ר���");
  	return false;
  }
  //alert(VersionNo);
  showInfo=window.open("./FrameInfoFinItemAssociatedInput.jsp?VersionNo=" + VersionNo + "&VersionState=" + VersionState + "&FinItemID="+FinItemID);
	//parent.fraInterface.window.location = "./ProposalGrpInput.jsp?LoadFlag=" + LoadFlag + "&type=" + type;  
	//showInfo=window.open("./LABranchGroupQuery.jsp?BranchType='"+document.all('BranchType').value + "'");  
}



//������ϸ��Ŀ�ж���������ҳ��
function intoDetailDef()
{
  //����������Ӧ�Ĵ���
  //mOperate="QUERY||MAIN";   
  var VersionNo = document.all('VersionNo').value;
  var VersionState = document.all('VersionState').value;
  var FinItemID = document.all('FinItemID').value;
    
  if (VersionNo == null||VersionNo == '')
  {
  	alert("���Ƚ��а汾��Ϣ��ѯ��Ȼ���ٽ�����ϸ��Ŀ�ж���������");
  	return;
  }
  
  if (FinItemID == null||FinItemID == '')  
  {
  	alert("���Ƚ��п�Ŀ���Ͷ����ѯ��Ȼ���ٽ�����ϸ��Ŀ�ж���������");
  	return;
  }          

	var countjudge = "";
  var strSQL = "";
  /**
  strSQL = "select count(*) from FIFinItemDef where versionno = '"+document.all('VersionNo').value+"' and FinItemID = '"+document.all('FinItemID').value+"' ";
  */
  //alert(strSQL);
  	var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.FinItemDefInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId("FinItemDefInputSql2");//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(document.all('VersionNo').value);//ָ������Ĳ���
		mySql2.addSubPara(document.all('FinItemID').value);//ָ������Ĳ���
		strSQL= mySql2.getString();
  countjudge = easyQueryVer3(strSQL, 1, 0, 1); 
 	countjudge = trim(countjudge.substr(4));
  //alert(countjudge);
  if(countjudge == "0")
  {
  	alert("�����Ŀ��Ϣ�����ڣ���ȷ�������Ӹ�����Ϣ���ٽ��п�Ŀ��ϸ�ж���������");
  	return false;
  }
  showInfo=window.open("./FrameDetailFinItemDefInput.jsp?VersionNo=" + VersionNo + "&VersionState=" + VersionState + "&FinItemID="+FinItemID);
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
  	alert("FinItemDefInput.js-->resetForm�����з����쳣:��ʼ���������!");
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
  fm.action="FinItemDefSave.jsp";
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
  	/*
  	if(mOperate="INSERT||MAIN")
  	{
  		content='������������ظ��������������룡';
  	}
		*/
  		
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
    	document.all('FinItemID').value = '';   	
    	document.all('FinItemName').value = '';  	 
    	document.all('FinItemType').value = '';
    	document.all('FinItemTypeMame').value = '';
    	document.all('ItemMainCode').value = '';
    	document.all('DealMode').value = '1'; 
    	document.all('ReMark').value = '';
    	
    	document.all('FinItemID').readOnly = false;
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
    alert("���ѯ��ȡ�汾��ţ�");
    return ;
  }

  if((fm.FinItemID.value=="")||(fm.FinItemID.value=="null"))
  {
    alert("����¼���Ŀ��ţ�");
    return ;
  }
  
  if((fm.FinItemName.value=="")||(fm.FinItemName.value=="null"))
  {
    alert("����¼���Ŀ���ƣ�");
    return ;
  }

  if((fm.FinItemType.value=="")||(fm.FinItemType.value=="null"))
  {
    alert("����¼���Ŀ���ͣ�");
    return ;
  }
  
  if((fm.ItemMainCode.value=="")||(fm.ItemMainCode.value=="null"))
  {
    alert("����¼���Ŀ���루һ������");
    return ;
  }
  
  if((fm.DealMode.value=="")||(fm.DealMode.value=="null"))
  {
    alert("����¼���Ŀ����ʽ��");
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
			document.all('FinItemID').value = '';   	
    	document.all('FinItemName').value = '';  	 
    	document.all('FinItemType').value = '';
    	document.all('FinItemTypeMame').value = '';
    	document.all('ItemMainCode').value = '';
    	document.all('DealMode').value = '1'; 
    	document.all('ReMark').value = '';
			document.all('FinItemID').readOnly = false;
			
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


//��Կ�Ŀ���Ͷ����ѯ�Ӵ��ڷ��ص�2ά����
function afterQuery2( arrQueryResult )
{
	var arrResult = new Array();
		//alert(arrQueryResult);

	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		//alert(arrResult);
		//document.all('VersionNo').value = arrResult[0][0];
		document.all('FinItemID').value = arrResult[0][1];
		document.all('FinItemName').value = arrResult[0][2];		
		document.all('FinItemType').value = arrResult[0][3];
		document.all('ItemMainCode').value = arrResult[0][4];		
		document.all('DealMode').value = arrResult[0][5];
		document.all('ReMark').value = arrResult[0][6];
								
		//document.all('FinItemID').readOnly=true;
		document.all('FinItemID').readOnly = true;						
		
		//�ڿ�Ŀ���Ͷ����ѯ֮����ܽ����޸ĺ�ɾ������
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
		//a.VersionNo,a.FinItemID,a.FinItemName,a.FinItemType,a.ItemMainCode,a.DealMode,a.ReMark
	}
}

function resetAgain()
{
			document.all('FinItemID').value = '';   	
    	document.all('FinItemName').value = '';  	 
    	document.all('FinItemType').value = '';
    	document.all('FinItemTypeMame').value = '';
    	document.all('ItemMainCode').value = '';
    	document.all('DealMode').value = '1'; 
    	document.all('ReMark').value = '';
    	
    	document.all('FinItemID').readOnly = false;
    	document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true;
}