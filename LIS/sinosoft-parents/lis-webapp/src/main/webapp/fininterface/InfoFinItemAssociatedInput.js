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
function initAssociatedQuery() 
{
	try
	{
  	var strSQL = "";
  	/**
  	strSQL = "select VersionNo,FinItemID,AssociatedID,AssociatedName,ReMark from FIInfoFinItemAssociated where ";
		strSQL = strSQL + " VersionNo ='"+VersionNo+"' and FinItemID ='"+FinItemID+"' ";
  	*/
  	//alert(strSQL);
  	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.InfoFinItemAssociatedInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("InfoFinItemAssociatedInputSql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(VersionNo);//ָ������Ĳ���
		mySql1.addSubPara(FinItemID);//ָ������Ĳ���
		strSQL= mySql1.getString();
  	turnPage.queryModal(strSQL, ItemAssociatedGrid);
	}
	
	catch(Ex)
	{
		alert(Ex.message);
	}
	
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
  //�ύǰ�ļ���
  if (!beforeSubmit2())
  {
  	return false;
  }	
	
  //����������Ӧ��ɾ������
  if ((document.all("AssociatedID").value==null)||(trim(document.all("AssociatedID").value)==''))
  {
    alert("��ȷ��Ҫɾ����ר���ţ�");
    return false;
  }
  
  if(document.all("AssociatedID").value != document.all('AssociatedID1').value)
	{
		alert("��ȷ��Ҫɾ����ר���ţ�");
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
  //�ύǰ�ļ���
  if (!beforeSubmit2())
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
  	alert("��InfoFinItemAssociatedInput.js-->resetForm�����з����쳣:��ʼ���������!");
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
  fm.action="InfoFinItemAssociatedSave.jsp";
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
    	document.all('AssociatedID').value='';
    	document.all('AssociatedID1').value='';
    	document.all('ReMark').value = '';
    	document.all('AssociatedName').value = '';
    }
    mOperate="";
    initAssociatedQuery();     
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
  if((fm.AssociatedID.value=="")||(fm.AssociatedID.value=="null"))
  {
    alert("����¼��ר���ţ�");
    return ;
  }
  
  if (!verifyInput2()) 
  {
  	return false;
  }
  
    return true;
}


//ɾ�����޸�ǰ����ѡ��һ����¼
function beforeSubmit2()
{		
	var tSel = ItemAssociatedGrid.getSelNo();	
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
 function ShowAssociated()
{
 	var arrResult = new Array();

	var tSel = ItemAssociatedGrid.getSelNo();	
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
		strSQL = "select a.VersionNo,a.FinItemID,a.AssociatedID,a.AssociatedName,a.ReMark from FIInfoFinItemAssociated a where a.VersionNo='"+
	          ItemAssociatedGrid.getRowColData(tSel-1,1)+"' and a.FinItemID='"+
	          ItemAssociatedGrid.getRowColData(tSel-1,2)+"' and a.AssociatedID='"+
	          ItemAssociatedGrid.getRowColData(tSel-1,3)+"'";
	          */
		//alert(strSQL);
		var mySql2=new SqlClass();
			mySql2.setResourceName("fininterface.InfoFinItemAssociatedInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql2.setSqlId("InfoFinItemAssociatedInputSql2");//ָ��ʹ�õ�Sql��id
			mySql2.addSubPara(ItemAssociatedGrid.getRowColData(tSel-1,1));//ָ������Ĳ���
			mySql2.addSubPara(ItemAssociatedGrid.getRowColData(tSel-1,2));//ָ������Ĳ���
			mySql2.addSubPara(ItemAssociatedGrid.getRowColData(tSel-1,3));//ָ������Ĳ���
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
  	document.all('FinItemID').value = arrResult[0][1];
  	//alert(FinItemID);    	
  	document.all('AssociatedID').value = arrResult[0][2];
  	document.all('AssociatedID1').value = arrResult[0][2];
  	document.all('AssociatedName').value = arrResult[0][3];
  	document.all('ReMark').value = arrResult[0][4];  	  
  		  	  	
	}
	//ѡ��mulline��ֵ֮��AssociatedID��Ŀר���Ų��ܽ����޸ģ�ֻ���޸ķ�������Ϣ
		//document.all('AssociatedID').disabled = true;
		//document.all('AssociatedName').disabled = true;
}

function resetAgain()
{
			document.all('AssociatedID').value='';
    	document.all('AssociatedID1').value='';
    	document.all('ReMark').value = '';
    	document.all('AssociatedName').value = '';
}