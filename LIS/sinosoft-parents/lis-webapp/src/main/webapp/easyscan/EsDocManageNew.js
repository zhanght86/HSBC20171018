
//�������ƣ�EsDocManage.js
//�����ܣ�
//�������ڣ�2009-09-09
//������  yanglh
//���¼�¼��  ������    ��������     ����ԭ��/����


//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var arrDataSet 
var tArr;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var arrDataSet;
var mDebug="0";
var mOperate="";
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

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  //fm.hideOperate.value=mOperate;
  //if (fm.hideOperate.value=="")
  //{
  //  alert("�����������ݶ�ʧ��");
  //}
  //showSubmitFrame(mDebug);
  document.getElementById("fm").submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
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

    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
    //ִ����һ������
    divReason.style.display="none";   
    if(fm.DOC_CODE.value == null || fm.DOC_CODE.value == '')
    {}
    else 	
    	easyQueryClick();
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
  	alert("��EsDocManageNew.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//ȡ����ť��Ӧ����
function cancelForm()
{
//  window.location="../common/html/Blank.html";
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
}
 
//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���	
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


//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
  alert("���ӹ�����ʱ���ṩ����ʹ��EasyScan���ص�֤����!");
  //����������Ӧ�Ĵ���
  //mOperate="INSERT||MAIN";
//  showDiv(operateButton,"false"); 
//  showDiv(inputButton,"true"); 
//  fm.fmtransact.value = "INSERT||CODE" ;
}           

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
	if(fm.PrtNo.value == null || fm.PrtNo.value == '')
	{
		alert("���Ȳ�ѯ��");
		return false;
	}
	if(fm.DOC_CODE.value == null || fm.DOC_CODE.value == '')
	{
		alert("����¼���޸ĺ��ӡˢ���룡");
		return false;
	}
  //����������Ӧ�Ĵ���
  if (confirm("��ȷʵ���޸ĸ�ӡˢ������?"))
  {
  	var i = 0;
  	var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  
  	//showSubmitFrame(mDebug);
  	fm.fmtransact.value = "UPDATE||MAIN";

  	document.getElementById("fm").submit(); //�ύ
  }
}

function updateClick1()
{
	
	if(getCount()!=1){
		alert("��ѡ��һҳɨ���");
		return false;	
	}
  //����������Ӧ�Ĵ���
  if (confirm("��ȷʵ���޸ĸü�¼��?"))
  {
  	var i = 0;
  	var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  
  	//showSubmitFrame(mDebug);
  	fm.fmtransact.value = "UPDATE||DOCID";

  	document.getElementById("fm").submit(); //�ύ
  }
}           

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{

  //����������Ӧ�Ĵ���
  //mOperate="QUERY||MAIN";
  if(fm.DOC_CODE.value=="")
  {
      alert("�����뵥֤���룡");
      return;
  }
  
  easyQueryClick();

}

// ��ѯ��ť
function easyQueryClick()
{						 
//	var strSQL = " select docid,(select bussno from es_doc_relation where docid=a.docid ),'',(concat(concat(to_char(makedate,'yyyy-mm-dd'),' '),maketime)),'',scanoperator,"
//					+ "managecom,inputstate,operator,inputstartdate,inputstarttime "
//					+ ",docflag,docremark,'',inputenddate,inputendtime,busstype,scanno,subtype " 
//					+ " from ES_DOC_MAIN a where 1=1 " 
//					+ " and doccode = '" + fm.DOC_CODE.value + "' and busstype = '" + fm.busstype.value + "' "
//					+ " and managecom like '" + fm.MngCom.value + "%' "
//					+ " order by makedate,maketime";		 
	
	var sqlid0="EsDocManageNewSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("easyscan.EsDocManageNewSql"); //ָ��ʹ�õ�properties�ļ���
	mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
	mySql0.addSubPara(fm.DOC_CODE.value);//ָ������Ĳ���
	mySql0.addSubPara(fm.busstype.value);//ָ������Ĳ���
	mySql0.addSubPara(fm.MngCom.value);//ָ������Ĳ���
	var strSQL=mySql0.getString();
	
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
  
	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult) {
		CodeGrid.clearData();
		alert("��ѯʧ�ܣ�");
		return false;
  }
	//��ѯ�ɹ������ַ��������ض�ά����
	arrResult = decodeEasyQueryResult(turnPage.strQueryResult);
	
	if( arrResult != null )
	{
		document.all( 'PrtNo' ).value = 			arrResult[0][1];
		document.all( 'DOC_ID' ).value = 			arrResult[0][0];
		document.all( 'DOC_CODE' ).value = 		arrResult[0][1];
		document.all('NUM_PAGES').value = 		arrResult[0][2];
		fm.deletebutton.disabled=false;
		fm.DelReason.value='';
	}
	else
	{
		initInpBox1();
	}
		
  	queryPages(fm.DOC_CODE.value);
}           

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
	//��ʾ��Ҫ¼��ɾ��ԭ��
//	var strSQL = " select count(*) from ES_DOC_MAIN where doccode='"+fm.DOC_CODE.value+"'"; 
	
	var sqlid1="EsDocManageNewSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("easyscan.EsDocManageNewSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(fm.DOC_CODE.value);//ָ������Ĳ���
	var strSQL=mySql1.getString();
	
	var brr = easyExecSql(strSQL);
    if ( brr )  
    {
//    		strSQL = " select 1 from lccont where prtno='"+fm.DOC_CODE.value+"' and appflag='1'";
    		
    		var sqlid2="EsDocManageNewSql2";
    		var mySql2=new SqlClass();
    		mySql2.setResourceName("easyscan.EsDocManageNewSql"); //ָ��ʹ�õ�properties�ļ���
    		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
    		mySql2.addSubPara(fm.DOC_CODE.value);//ָ������Ĳ���
    		strSQL=mySql2.getString();
    		
			var brr1 = easyExecSql(strSQL);
		    if ( brr1 )  
		    {
		    	alert("��ɨ�������Ͷ�����Ѿ�ǩ��������ɾ������ɨ�����");
				return false;
		    }
    	//alert(brr[0][0]);
    	if( brr[0][0]> 0 )
    		if(fm.DelReason.value == null || fm.DelReason.value == '')
			{
				alert("����¼��ɾ��ԭ��");
				divReason.style.display="";
				return false;
			}
    }
    else
    {
    	alert("��ӡˢ����û��ɨ�������ɾ����");
    	return false;
    }
    
	//����������Ӧ��ɾ������
  if (confirm("��ȷʵ��ɾ����ӡˢ�������е�֤��?"))
  {	 
	var i = 0;
  	var showStr="����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  	
  	//showSubmitFrame(mDebug);
  	fm.fmtransact.value = "DELETE||MAIN";

  	document.getElementById("fm").submit(); //�ύ
  	initForm();
  }
  else
  {
    //mOperate="";
    alert("��ȡ����ɾ��������");
  }
}   

function deleteClick1()
{
	if(getCount()!=1){
		alert("��ѡ��һҳɨ���");
		return false;
	}
	//У������õ�֤Ϊӡˢ�������һ�ŵ�֤����ʾ��Ҫ¼��ɾ��ԭ��
//	var strSQL = " select count(*) from ES_DOC_MAIN where doccode='"+fm.DOC_CODE.value+"'";
	
	var sqlid3="EsDocManageNewSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName("easyscan.EsDocManageNewSql"); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(fm.DOC_CODE.value);//ָ������Ĳ���
	var strSQL=mySql3.getString();
	
	var brr = easyExecSql(strSQL);
    if ( brr )  
    {
    	//alert(brr[0][0]);
    	if( brr[0][0]== 1 )
    	{
    		if(fm.DelReason.value == null || fm.DelReason.value == '')
				{
				alert("����¼�뵥֤ɾ��ԭ��");
				divReason.style.display="";
				return false;
				}
			}
    }
    else
    {
    	alert("��ӡˢ����û��ɨ�������ɾ����");
    	return false;
    } 
	
  //����������Ӧ��ɾ������
  if (confirm("��ȷʵ��ɾ���ü�¼��?"))
  {
		var i = 0;
  	var showStr="����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  	
  	//showSubmitFrame(mDebug);
  	fm.fmtransact.value = "DELETE||DOCID";

  	document.getElementById("fm").submit(); //�ύ

  	//initForm();
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
          
       
// ��ѯ��ť
function queryPages(strDoc_Code)
{
	// ��дSQL���
//	var strSQL = "select pageid,docid,pagecode,hostname,pagename,pageflag,"
//					+ "picpathftp,picpath,operator,makedate,maketime "
//					+ ",(select subtype from es_doc_main where docid=ES_DOC_PAGES.docid),scanno"
//					+ " from ES_DOC_PAGES where 1=1 " 
//					+ " "+ " and DOCID in ( select docid from es_doc_relation where bussno='" + strDoc_Code + "' ) "
//					+ " order by docid,pagecode";
	
	var sqlid4="EsDocManageNewSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName("easyscan.EsDocManageNewSql"); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(strDoc_Code);//ָ������Ĳ���
	var strSQL=mySql4.getString();
	
	//prompt('',strSQL);
	turnPage1.queryModal(strSQL, CodeGrid, 0, 0, 10 );
	
}



//Click�¼� ���'�����޸�'��ťʱ������ʵ�ֱ���MultiLine��ǰ�޸ĵ����� ��ͨ��
function saveUpdate()
{
	if(getCount()<=0){
		alert("������ѡ��һҳɨ���");
		return false;
	}
  if (confirm("��ȷʵ�뱣���޸���?"))
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
  	
  	fm.fmtransact.value = "UPDATE||PAGES";
  	
  	//ѡ��������
  	//CodeGrid.checkBoxAll();

  	document.getElementById("fm").submit(); //�ύ
  	//initForm();
  }
}        
//Click�¼� ���'ɾ��ѡ��'��ťʱ������ʵ��ɾ��ѡ�е�MultiLine������ ��ͨ��
function deleteChecked()
{
	if(getCount()<=0){
		alert("������ѡ��һҳɨ���");
		return false;
	}
//	var strSQL = " select (case when count(*) is not null then count(*) else 0 end) from ES_DOC_PAGES where docid in (select docid from ES_DOC_MAIN where doccode='"+fm.DOC_CODE.value+"')"; 	
	
	var sqlid5="EsDocManageNewSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName("easyscan.EsDocManageNewSql"); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(fm.DOC_CODE.value);//ָ������Ĳ���
	var strSQL=mySql5.getString();
	
	var brr = easyExecSql(strSQL);
	if ( brr && (getCount() - brr[0][0] == 0))  
	{	    	
	 	alert("����ɾ����ӡˢ�������е�֤ҳ��Ϣ����ʹ�á�ɾ����֤����ť��");
	 	return false;
	}
  if (confirm("��ȷʵ��ɾ��ѡ�еĵ�֤ҳ������?"))
  {
	var i = 0;
  	var showStr="����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  	
  	fm.fmtransact.value = "DELETE||PAGES";

  	document.getElementById("fm").submit(); //�ύ
  	//���²�ѯ
  	//queryPages(document.all( 'DOC_ID' ).value);
  	//initForm();
  }
}

function getCount()
{
	//�õ���ѡ����ѡ���˶�����
	var count = CodeGrid.mulLineCount;
	var flag = 0;
	for(var index=0; index< count; index++)
	{
	   if(CodeGrid.getChkNo(index)==true)
	   {
	   	flag =flag+1;
	   }
	}

  return flag;
}    