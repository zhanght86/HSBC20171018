
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

		
	// ��дSQL���
//	var strSQL = "select doccode,MissionProp2,numpages,managecom,m.makedate,m.createoperator "
//			+ "from lwmission m,es_doc_main d where ActivityID = '0000003001' "
//			+ "and m.MissionProp1=d.DocCode"
//			+ " and subtype like 'UA%' and busstype='TB' " 
//			+ getWherePart('d.doccode','BussNo','=','0')
//			+ getWherePart('d.managecom','ManageCom','like','0')
//			+ getWherePart('m.makedate','StartDate','>=','0')
//			+ getWherePart('m.makedate','EndDate','<=','0')
//			+ " order by subtype,managecom,makedate,doccode ";
	
  	var  BussNo0 = window.document.getElementsByName(trim("BussNo"))[0].value;
  	var  ManageCom0 = window.document.getElementsByName(trim("ManageCom"))[0].value;
  	var  StartDate0 = window.document.getElementsByName(trim("StartDate"))[0].value;
  	var  EndDate0 = window.document.getElementsByName(trim("EndDate"))[0].value;
	var sqlid0="AcceptOtherDocSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("easyscan.AcceptOtherDocSql"); //ָ��ʹ�õ�properties�ļ���
	mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
	mySql0.addSubPara(BussNo0);//ָ������Ĳ���
	mySql0.addSubPara(ManageCom0);//ָ������Ĳ���
	mySql0.addSubPara(StartDate0);//ָ������Ĳ���
	mySql0.addSubPara(EndDate0);//ָ������Ĳ���
	var strSQL=mySql0.getString();
	
	//prompt("",strSQL);
	turnPage.queryModal(strSQL, CodeGrid);    
	document.all('Accept').disabled = false;
}           

//�ύ�����水ť��Ӧ����
function submitForm()
{
	if(!verifyInput())
		return ;
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	document.all('Accept').disabled = true;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
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
	try{
		showInfo.close();
	}catch(ex){}
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
  easyQueryClick();
}
