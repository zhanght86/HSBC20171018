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

function initNextJudgementNo(cObj)
{
	mNextJudgementNo = " 1 and VersionNo = #"+VersionNo+"# and FinItemID = #"+FinItemID+"#";
	showCodeList('NextJudgementNo',[cObj], null, null, mNextJudgementNo, "1");//add by fx
}

function actionKeyUp(cObj)
{	
	mNextJudgementNo = " 1 and VersionNo = #"+VersionNo+"# and FinItemID = #"+FinItemID+"#";
	showCodeListKey('NextJudgementNo',[cObj], null, null, mNextJudgementNo, "1");//add by fx
}


//����ҳ���Զ���ѯ
function initDetailCodeQuery() 
{
	try
	{
  	var strSQL = "";
  	/**
  	strSQL = "select a.VersionNo,a.FinItemID,a.JudgementNo,func_1803_01(b.LevelCondition),a.LevelConditionValue,a.LevelCode,a.NextJudgementNo,a.ReMark from FIDetailFinItemCode a,FIDetailFinItemDef b ";
		strSQL = strSQL + " where a.VersionNo=b.VersionNo and a.FinItemID=b.FinItemID and a.JudgementNo=b.JudgementNo and "  	
		strSQL = strSQL + " a.VersionNo ='"+VersionNo+"' and a.FinItemID ='"+FinItemID+"' and a.JudgementNo ='"+JudgementNo+"' order by a.VersionNo,a.FinItemID,a.JudgementNo,a.LevelCode,a.LevelConditionValue ";
  	*/
  	//alert(strSQL);
  	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.DetailFinItemCodeInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("DetailFinItemCodeInputSql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(VersionNo);//ָ������Ĳ���
		mySql1.addSubPara(FinItemID);//ָ������Ĳ���
		mySql1.addSubPara(JudgementNo);//ָ������Ĳ���
		strSQL= mySql1.getString();
  	
  	turnPage.queryModal(strSQL, DetailCodeGrid);
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
  if ((fm.all("LevelConditionValue").value==null)||(trim(fm.all("LevelConditionValue").value)==''))
    alert("��ȷ��Ҫɾ���Ĳ㼶���������ֵ��");
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
	
  //����������Ӧ�Ĵ���
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
  	alert("��DetailFinItemCodeInput.js-->resetForm�����з����쳣:��ʼ���������!");
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
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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
  fm.action="DetailFinItemCodeSave.jsp";
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
    if(mOperate="DELETE||MAIN")
    {
    	fm.all("LevelConditionValue").value = '';
    	fm.all("LevelCode").value = '';
    	fm.all("NextJudgementNo").value = '';
    	fm.all("ReMark").value = '';
    	fm.all('LevelConditionValue').readOnly = false;
    }
    mOperate="";
    initDetailCodeQuery();    
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
  
  if((fm.LevelConditionValue.value=="")||(fm.LevelConditionValue.value=="null"))
  {
    alert("����¼��㼶���������ֵ��");
    return ;
  }  
  
  if((fm.NextJudgementNo.value=="")||(fm.NextJudgementNo.value=="null"))
  {
    alert("����¼���¼���Ŀ�ж�������");
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
	var tSel = DetailCodeGrid.getSelNo();	
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
 	else 
 	{
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}


//ѡ��mulline��һ�У��Զ��������ֵ
 function ShowDetailCode()
{
 	var arrResult = new Array();

	var tSel = DetailCodeGrid.getSelNo();	
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
		strSQL = "select a.VersionNo,a.FinItemID,a.JudgementNo,a.LevelConditionValue,a.LevelCode,a.NextJudgementNo,a.ReMark from FIDetailFinItemCode a where a.VersionNo='"+
	          DetailCodeGrid.getRowColData(tSel-1,1)+"' and a.FinItemID='"+
	          DetailCodeGrid.getRowColData(tSel-1,2)+"' and a.JudgementNo='"+
	          DetailCodeGrid.getRowColData(tSel-1,3)+"' and a.LevelConditionValue='"+	          
	          DetailCodeGrid.getRowColData(tSel-1,5)+"'";
	          */
		//alert(strSQL);
		var mySql2=new SqlClass();
			mySql2.setResourceName("fininterface.DetailFinItemCodeInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql2.setSqlId("DetailFinItemCodeInputSql2");//ָ��ʹ�õ�Sql��id
			mySql2.addSubPara(DetailCodeGrid.getRowColData(tSel-1,1));//ָ������Ĳ���
			mySql2.addSubPara(DetailCodeGrid.getRowColData(tSel-1,2));//ָ������Ĳ���
			mySql2.addSubPara(DetailCodeGrid.getRowColData(tSel-1,3));//ָ������Ĳ���
			mySql2.addSubPara(DetailCodeGrid.getRowColData(tSel-1,5));//ָ������Ĳ���
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
  	fm.all('VersionNo').value = arrResult[0][0];
  	//alert(VersionNo);  
  	fm.all('FinItemID').value = arrResult[0][1];
  	//alert(FinItemID);    	
  	fm.all('JudgementNo').value = arrResult[0][2];
  	//alert(JudgementNo);    	
  	fm.all('LevelConditionValue').value = arrResult[0][3];
  	fm.all('LevelCode').value = arrResult[0][4];  	  	  	  	
  	fm.all('NextJudgementNo').value = arrResult[0][5];  
		fm.all('ReMark').value = arrResult[0][6];    		  	  	  	
	}
	//ѡ��mulline��ֵ֮��JudgementNo���ܽ����޸ģ�ֻ���޸ķ�������Ϣ
		fm.all('LevelConditionValue').readOnly = true;
}

function resetAgain()
{
			fm.all("LevelConditionValue").value = '';
    	fm.all("LevelCode").value = '';
    	fm.all("NextJudgementNo").value = '';
    	fm.all("ReMark").value = '';
    	fm.all('LevelConditionValue').readOnly = false;
}