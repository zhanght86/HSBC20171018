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
function superaddClick()
{
	if((document.all('LevelConditionB').value == null)||(document.all('LevelConditionB').value == ''))
	{
		alert("�����Ϊ�գ���������д��");
		document.all('LevelConditionB').value = '';
		document.all('LevelConditionBName').value = '';
		return false;
	}
	if((document.all('LevelCondition').value == null)||(document.all('LevelCondition').value == ''))
	{
		document.all('LevelCondition').value = document.all('LevelConditionB').value;
		document.all('LevelConditionName').value = document.all('LevelConditionBName').value;
		document.all('LevelConditionB').value = '';
		document.all('LevelConditionBName').value = '';
	}
	else
	{
		document.all('LevelCondition').value = document.all('LevelCondition').value + "," +document.all('LevelConditionB').value;
		document.all('LevelConditionName').value = document.all('LevelConditionName').value + "," +document.all('LevelConditionBName').value;
		document.all('LevelConditionB').value = '';
		document.all('LevelConditionBName').value = '';
	}
}//add by fx

function clearClick()
{
		document.all('LevelConditionB').value = '';
		document.all('LevelConditionBName').value = '';
		document.all('LevelCondition').value = '';
		document.all('LevelConditionName').value = '';
}//add by fx

//����ҳ���Զ���ѯ

function initDetailDefQuery() 
{		
  	var strSQL = "";
  	/**
		strSQL = strSQL + " VersionNo ='"+VersionNo+"' and FinItemID ='"+FinItemID+"' ";
  	*/
  	//alert(strSQL);  	
  	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.DetailFinItemDefInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("DetailFinItemDefInputSql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(VersionNo);//ָ������Ĳ���
		mySql1.addSubPara(FinItemID);//ָ������Ĳ���
		strSQL= mySql1.getString();
  	
  	turnPage.queryModal(strSQL, DetailDefGrid);
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
  if ((document.all("JudgementNo").value==null)||(trim(document.all("JudgementNo").value)==''))
    alert("��ȷ��Ҫɾ�����ж��������ţ�");
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


//������ϸ��Ŀ��֧Ӱ�䶨��ҳ��
function intoDetailCode()
{
  //����������Ӧ�Ĵ���
  //mOperate="QUERY||MAIN";   
  var VersionNo = document.all('VersionNo').value;
  //VersionState��fmû�У���Ҫ��DetailFinItemCodeInput.jsp��fm�м���VersionState
  //var VersionState = document.all('VersionState').value;
  //alert(VersionState);
  //alert(fm.VersionState.value);
  var FinItemID = document.all('FinItemID').value;
  var JudgementNo = document.all('JudgementNo').value;
    
  if (VersionNo == null||VersionNo == '')
  {
  	alert("���Ƚ��а汾��Ϣ��ѯ��Ȼ���ٽ�����ϸ��Ŀ��֧Ӱ�䶨��");
  	return;
  }
  
  if (FinItemID == null||FinItemID == '')  
  {
  	alert("���Ƚ��п�Ŀ��Ϣ��ѯ��Ȼ���ٽ�����ϸ��Ŀ��֧Ӱ�䶨��");
  	return;
  }
  
  if (JudgementNo == null||JudgementNo == '')  
  {
  	alert("���Ƚ�����ϸ��Ŀ�ж��������壬Ȼ���ٽ�����ϸ��Ŀ��֧Ӱ�䶨��");
  	return;
  }
           
  var countjudge = "";
  var strSQL = "";
  /**
  strSQL = "select count(*) from FIDetailFinItemDef where versionno = '"+document.all('VersionNo').value+"' and FinItemID = '"+document.all('FinItemID').value+"' and JudgementNo = '"+document.all('JudgementNo').value+"' ";
  */
  	var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.DetailFinItemDefInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId("DetailFinItemDefInputSql2");//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(document.all('VersionNo').value);//ָ������Ĳ���
		mySql2.addSubPara(document.all('FinItemID').value);//ָ������Ĳ���
		mySql2.addSubPara(document.all('JudgementNo').value);//ָ������Ĳ���
		strSQL= mySql2.getString();
  countjudge = easyQueryVer3(strSQL, 1, 0, 1); 
 	countjudge = trim(countjudge.substr(4));
  if(countjudge == "0")
  {
  	alert("������ϸ��Ŀ�ж�������Ϣ�����ڣ���ȷ�������Ӹ�����Ϣ���ٽ�����ϸ��Ŀ��֧Ӱ�䶨��");
  	return false;
  }
   
  showInfo=window.open("./FrameDetailFinItemCodeInput.jsp?VersionNo=" + VersionNo + "&VersionState=" + VersionState + "&FinItemID="+FinItemID + "&JudgementNo="+JudgementNo );
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
  	alert("��DetailFinItemDefInput.js-->resetForm�����з����쳣:��ʼ���������!");
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
  fm.action="DetailFinItemDefSave.jsp";
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
    	 document.all('JudgementNo').value = '';
  		 document.all('LevelCondition').value = '';
  		 document.all('LevelConditionName').value = '';
  		 document.all('FirstMark').value = ''; 
    	 document.all('FirstMarkName').value = '';  
    	 document.all('ReMark').value = '';  
  		 document.all('LevelConditionB').value = '';
  		 document.all('LevelConditionBName').value = '';
  		 
  		 document.all('JudgementNo').readOnly = false;	
    }
    mOperate="";
    initDetailDefQuery();    
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
  if((fm.JudgementNo.value=="")||(fm.JudgementNo.value=="null"))
  {
    alert("����¼���ж��������ţ�");
    return ;
  }
  if(trim(document.all('LevelCondition').value).length > 500)
  {
  	alert("�㼶����������������ݹ��������������룡");
  	document.all('JudgementNo').value = '';
  	document.all('LevelCondition').value = '';
  	document.all('LevelConditionName').value = '';
  	document.all('LevelConditionB').value = '';
  	document.all('LevelConditionBName').value = '';
    document.all('FirstMark').value = ''; 
    document.all('FirstMarkName').value = '';  
    document.all('ReMark').value = '';  
  }//add by fx
  
  if (!verifyInput2())
  {
  	return false;
  }
    
    return true;
}


//ɾ�����޸�ǰ����ѡ��һ����¼
function beforeSubmit2()
{		
	var tSel = DetailDefGrid.getSelNo();	
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
 function ShowDetailDef()
{
 	var arrResult = new Array();

	var tSel = DetailDefGrid.getSelNo();	
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
		strSQL = "select a.VersionNo,a.FinItemID,a.JudgementNo,func_1803_01(a.LevelCondition),a.FirstMark,a.ReMark from FIDetailFinItemDef a where a.VersionNo='"+
	          DetailDefGrid.getRowColData(tSel-1,1)+"' and a.FinItemID='"+
	          DetailDefGrid.getRowColData(tSel-1,2)+"' and a.JudgementNo='"+
	          DetailDefGrid.getRowColData(tSel-1,3)+"'";
	          */
		//alert(strSQL);
		var mySql3=new SqlClass();
			mySql3.setResourceName("fininterface.DetailFinItemDefInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql3.setSqlId("DetailFinItemDefInputSql3");//ָ��ʹ�õ�Sql��id
			mySql3.addSubPara(DetailDefGrid.getRowColData(tSel-1,1));//ָ������Ĳ���
			mySql3.addSubPara(DetailDefGrid.getRowColData(tSel-1,2));//ָ������Ĳ���
			mySql3.addSubPara(DetailDefGrid.getRowColData(tSel-1,3));//ָ������Ĳ���
			strSQL= mySql3.getString();
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
  	document.all('JudgementNo').value = arrResult[0][2];
  	document.all('LevelCondition').value = arrResult[0][3];
  	document.all('FirstMark').value = arrResult[0][4];  	  	  	  	
  	document.all('ReMark').value = arrResult[0][5];  	  	  	  	
	}	 
	//ѡ��mulline��ֵ֮��JudgementNo���ܽ����޸ģ�ֻ���޸ķ�������Ϣ
		document.all('JudgementNo').readOnly = true;	
		//document.all('JudgementNo').disabled = true;	
		showCodeName(); 
}

function resetAgain()
{
			 document.all('JudgementNo').value = '';
  		 document.all('LevelCondition').value = '';
  		 document.all('LevelConditionName').value = '';
  		 document.all('FirstMark').value = ''; 
    	 document.all('FirstMarkName').value = '';  
    	 document.all('ReMark').value = '';  
  		 document.all('LevelConditionB').value = '';
  		 document.all('LevelConditionBName').value = '';
  		 
  		 document.all('JudgementNo').readOnly = false;
}