
//�������ƣ�ManageIssueDoc.js
//�����ܣ�
//�������ڣ�2006-04-02
//������  ��wentao
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var showInfo;
window.onfocus=myonfocus;
//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�

function $(element) {
  if (typeof element == 'string')
    element = document.getElementById(element);
  return element;
}

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
function easyQueryClick()
{
	if(!verifyInput())return;
	var comcode = document.all('ManageCom').value;
	if(comcode == null || comcode == "")
	{
		alert("�������������Ϊ�գ�");
		return;
	}

	if($('ScanState').value=='1' && $('AcceptState').value=='0'){
		alert("��������ɨ��δǩ����");
		return;
	}
	// ��дSQL���
	var sqlstate="";
//	var strSQL="select missionprop1,missionprop5,(select min(makedate) from es_doc_main where doccode=missionprop1),missionprop3,makedate,maketime,"
//	+"missionprop4,missionprop2,(case activityid when '0000003001' then 'N' when '0000003002' then 'Y' end) from ";
	if($('ScanState').value==1)
	{
//		strSQL=strSQL+"lbmission ";
		sqlstate=sqlstate+"1";
	}
	else
	{
//		strSQL=strSQL+"lwmission ";
		sqlstate=sqlstate+"0";
	}
//	strSQL+="a where processid='0000000010'";
	if($('AcceptState').value=='0')
	{
//		strSQL=strSQL+" and activityid='0000003001' ";
		sqlstate=sqlstate+"0";
	}
	else if($('AcceptState').value=='1')
	{
//		strSQL=strSQL+" and activityid='0000003002' ";
		sqlstate=sqlstate+"1";
	}
	else
	{
//		strSQL=strSQL+" and activityid in ('0000003001','0000003002') ";
		sqlstate=sqlstate+"2";
	}
//	strSQL=strSQL + getWherePart('missionprop3','ApplyOperator')
//	+ getWherePart('missionprop1','BussNo')
//	+ getWherePart('missionProp2','Reason')
//	+ getWherePart('makedate','ApplyDate')
//	+"and exists(select * from es_doc_main where doccode=a.missionprop1 and subtype like 'UA%' and busstype='TB'"
//	+ getWherePart('makedate','StartDate','>=','0')
//	+ getWherePart('makedate','EndDate','<=','0')+")"
//	+ getWherePart('missionprop5','ManageCom', 'like');
	
	var sqlid="QueryOtherDocSql"+sqlstate;
	var mySql=new SqlClass();
	mySql.setResourceName("easyscan.ApplyOtherDocInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
    mySql.addSubPara(fm.BussNo.value);//ָ������Ĳ���
    mySql.addSubPara(fm.Reason.value);
    mySql.addSubPara(fm.ApplyDate.value);
    mySql.addSubPara(fm.StartDate.value);
	mySql.addSubPara(fm.EndDate.value);
	mySql.addSubPara(fm.ManageCom.value);
	strSQL=mySql.getString();
	
	turnPage.queryModal(strSQL, CodeGrid);    
	//var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	if($('ScanState').value==1){
		$('divReason').style.display= "none";
	}else{
		$('divReason').style.display= "";
	}
//	divReason.style.display= "";
}           

//ɨ���ά������
function saveApply()
{
	fm.fmtransact.value = "APPLY";
	submitForm(); //�ύ
}

//ɨ���ά������
function undoApply()
{
	fm.fmtransact.value = "UNDO";
	submitForm(); //�ύ
}        

//���ر��ռƻ��������
function HideChangeResult()
{
	divReason.style.display= "none";
	fm.Reason.value = "";
}

//�ύ�����水ť��Ӧ����
function submitForm()
{
	if(!verifyInput())
		return ;
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	$('undoApplyBtn').disabled = true;
  document.getElementById("fm").submit(); //�ύ
//  document.all('Content').value = "";
}

//У�麯��
/*function vertifyInput()
{
	var content = trim(document.all('Content').value);
	if(content == null || content == "")
	{
		alert("��¼������ԭ��");
		return false;
	}
	return true;
}*/

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
	$('undoApplyBtn').disabled = false;
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
  }
  else
  { 
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
    easyQueryClick();
    //ִ����һ������
  }
}
