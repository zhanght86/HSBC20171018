
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
	var comcode = document.all('ManageCom').value;
	if(comcode == null || comcode == "")
	{
		alert("�������������Ϊ�գ�");
		return;
	}
	if(fm.BussNo.value == ""){
		if(fm.StartDate.value == "" && fm.EndDate.value== ""){
			alert("��¼��ӡˢ�Ż���ֹ����");
			return;
		}
	}

		
	// ��дSQL���
//	var strSQL = "select subtype,doccode,(select bussnotype from es_doc_relation where docid = a.docid and bussno = a.doccode and subtype = a.subtype)"
//							+",numpages,docid,managecom,inputstate,makedate,scanoperator "
//							+ "from es_doc_main a "
//							+ "where docflag = '1' "		//���Ѿ����صĵ�֤��ʹ�ñ�����
//							+ "and busstype='TB' and subtype like 'UA%' "
//							+ getWherePart('doccode','BussNo','=','0')
//							+ getWherePart('inputstate','InputState','=','0')
//							+ getWherePart('managecom','ManageCom','like','0')
//							+ getWherePart('makedate','StartDate','>=','0')
//							+ getWherePart('makedate','EndDate','<=','0')
//							+ " order by subtype,managecom,makedate,doccode ";
    var mySql=new SqlClass();
	mySql.setResourceName("easyscan.ApplyOtherDocInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql.setSqlId("ApplyOtherDocInputSql1");//ָ��ʹ�õ�Sql��id
    mySql.addSubPara(fm.BussNo.value);//ָ������Ĳ���
    mySql.addSubPara(fm.InputState.value);
    mySql.addSubPara(fm.ManageCom.value);
    mySql.addSubPara(fm.StartDate.value);
	mySql.addSubPara(fm.EndDate.value);
	//alert(strSQL);
	turnPage.queryModal(mySql.getString(), CodeGrid);    
	//var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	HideChangeResult();
	divReason.style.display= "";
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
	document.all('APPLY').disabled = true;
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
	document.all('APPLY').disabled = false;
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

    //ִ����һ������
  }
}
