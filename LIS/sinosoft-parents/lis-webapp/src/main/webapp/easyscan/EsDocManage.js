
//�������ƣ�EsDocManage.js
//�����ܣ�
//�������ڣ�2004-06-02
//������  ��LiuQiang
//���¼�¼��  ������    ��������     ����ԭ��/����


//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var arrDataSet 
var tArr;
var turnPage = new turnPageClass();
var mySql = new SqlClass();
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
//    divReason.style.display=""; 
    fm.DelReasonCode.value="";
    fm.DelReason.value="";
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
  	alert("��OLDCode.js-->resetForm�����з����쳣:��ʼ���������!");
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
	
	//05-12  ����ֻ���޸�ͬһӡˢ�ų��ȵ�У��
//	for(var index=0; index< count; index++)
//	{
//	   if(CodeGrid.getChkNo(index)==true)
//	   {
//	   	flag =flag+1;
//	   	str = CodeGrid.getRowColData(index,12);	
//	   	str2 = CodeGrid.getRowColData(index,2);
//	   	if(str1 != '' && str1 != str2)//У���Ƿ��в�һ�µĵ�֤�ڲ���
//	   		flaga = 1;//�в�һ�µ�֤�ڲ���
//	   	str1 = CodeGrid.getRowColData(index,2);	   	
//	   }
//	}
	
	if(!checkOnlyOne(2))
		return false;	
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
  if(fm.DOC_ID.value=="" && fm.DOC_CODE.value=="")
  {
      alert("������ӡˢ�Ż�֤�ڲ��ţ�");
      return;
  }
  
  easyQueryClick();
  
  //showInfo=window.open("./EsDocMainQuery.html");
}

// ��ѯ��ť
function easyQueryClick()
{						 
	//alert(strSQL);
	/*var strSQL = " select docid,(select bussno from es_doc_relation where docid=a.docid and bussnotype='11'),'',(to_char(makedate,'yyyy-mm-dd')||' '||maketime),'',scanoperator,"
					+ "managecom,inputstate,operator,inputstartdate,inputstarttime "
					+ ",docflag,docremark,'',inputenddate,inputendtime,busstype,scanno,subtype " 
					+ " from ES_DOC_MAIN a where 1=1 " 
					+ " "+ getWherePart( 'DOCID','DOC_ID' );*/
	
	mySql = new SqlClass();
	mySql.setResourceName("easyscan.EsDocManageSql");
	mySql.setSqlId("EsDocManageSql1");
	mySql.addSubPara(fm.DOC_ID.value ); 	
				
					if(fm.DOC_CODE.value!=null && fm.DOC_CODE.value!='')
					{
					//	strSQL = strSQL + " and docid in (select docid from es_doc_relation where bussno='"+fm.DOC_CODE.value+"' and bussnotype='11') ";
					mySql = new SqlClass();
					mySql.setResourceName("easyscan.EsDocManageSql");
					mySql.setSqlId("EsDocManageSql2");
					mySql.addSubPara(fm.DOC_ID.value ); 
					mySql.addSubPara(fm.DOC_CODE.value ); 
					}
					//+ " "+ getWherePart( 'DOCCODE','DOC_CODE' )
				//strSQL = strSQL + " order by makedate,maketime";		 
	
	turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 1, 1);  
  
	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult) {
		//alert("��ѯʧ�ܣ�");
		//return false;
    }
	//��ѯ�ɹ������ַ��������ض�ά����
	arrResult = decodeEasyQueryResult(turnPage.strQueryResult);
	
	if( arrResult != null )
	{
		//alert(1);
		document.all( 'DOC_ID' ).value = 			arrResult[0][0];
		document.all( 'PrtNo' ).value = 			arrResult[0][1];
		document.all( 'DOC_CODE' ).value = 		arrResult[0][1];
		document.all('NUM_PAGES').value = 		arrResult[0][2];
		document.all('INPUT_DATE').value = 		arrResult[0][3];
		//document.all('Input_Time').value = 		arrResult[0][4];
		document.all('ScanOperator').value = 		arrResult[0][5];  
		document.all('ManageCom').value = 		arrResult[0][6];
		document.all('InputState').value = 		arrResult[0][7];
		document.all('Operator').value = 			arrResult[0][8];
		document.all('InputStartDate').value = 	arrResult[0][9];
		document.all('InputStartTime').value = 	arrResult[0][10];
		document.all('DOC_FLAGE').value = 		arrResult[0][11];
		document.all('DOC_REMARK').value = 		arrResult[0][12];
		//document.all('Doc_Ex_Flag').value = 		arrResult[0][13];
		document.all('InputEndDate').value = 		arrResult[0][14];
		document.all('InputEndTime').value = 		arrResult[0][15];
		document.all('BussType').value = 		arrResult[0][16];
		document.all('ScanNo').value = 		arrResult[0][17];
		/*document.all('SubType').value = 		arrResult[0][18];
		if(fm.SubType.value!="")
			showOneCodeName('imagetype','SubType','SubTypeName');
		else
			fm.SubTypeName.value="";*/
		//alert(1);
		
	}
	else
	{
		//alert(2);
		initInpBox1();
	}
		
  	queryPages(fm.DOC_CODE.value);
}           

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
	//��ʾ��Ҫ¼��ɾ��ԭ��
	//var strSQL = " select nvl(count(*),0) from ES_DOC_MAIN where doccode='"+fm.PrtNo.value+"'"; 	
	mySql = new SqlClass();
	mySql.setResourceName("easyscan.EsDocManageSql");
	mySql.setSqlId("EsDocManageSql3");
	mySql.addSubPara(fm.PrtNo.value );  
	var brr = easyExecSql(mySql.getString());
    if ( brr )  
    {		//���ʱ�ͨ���Խ���ɾ��
    		/*strSQL = " select 1 from lccont where prtno='"+fm.PrtNo.value+"' and appflag='1' and (salechnl!='03'"
    					+ " and selltype!='08')"; */
			mySql = new SqlClass();
			mySql.setResourceName("easyscan.EsDocManageSql");
			mySql.setSqlId("EsDocManageSql4");
			mySql.addSubPara(fm.PrtNo.value );  
			var brr1 = easyExecSql(mySql.getString());
		    if ( brr1 )  
		    {
		    	alert("��ɨ�������Ͷ�����Ѿ�ǩ��������ɾ������ɨ�����");
				return false;
		    }
    	//alert(brr[0][0]);
    	if( brr[0][0]> 0 )
    	{
    		if(fm.DelReasonCode.value == null || fm.DelReasonCode.value == '')
			{
				alert("����ѡ��ɾ��ԭ�����ͣ�");
				divReason.style.display="";
				return false;
			}
			if(fm.DelReason.value == null || fm.DelReason.value == '')
			{
				alert("����¼��ɾ��ԭ��");
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
	if(!checkOnlyOne(1))
		return false;
	//У������õ�֤Ϊӡˢ�������һ�ŵ�֤����ʾ��Ҫ¼��ɾ��ԭ��
	//var strSQL = " select nvl(count(*),0) from ES_DOC_MAIN where doccode='"+fm.PrtNo.value+"'"; 	
	mySql = new SqlClass();
			mySql.setResourceName("easyscan.EsDocManageSql");
			mySql.setSqlId("EsDocManageSql5");
			mySql.addSubPara(fm.PrtNo.value );  
	var brr = easyExecSql(mySql.getString());
    if ( brr )  
    {
    	//alert(brr[0][0]);
    	//2010-04-09 ln add ɾ���κ�һ�ֵ�֤����Ҫ¼��ɾ��ԭ��
    	//if( brr[0][0]== 1 )
    	if( brr[0][0]>0 )
    	{
    		//���ʱ�ͨ���Խ��в�ɨ
    		/*strSQL = " select 1 from lccont where prtno='"+fm.PrtNo.value+"' and appflag='1' and (salechnl!='03'"
    					+ " and selltype!='08')"; 	*/
			mySql = new SqlClass();
			mySql.setResourceName("easyscan.EsDocManageSql");
			mySql.setSqlId("EsDocManageSql6");
			mySql.addSubPara(fm.PrtNo.value );  
			var brr1 = easyExecSql(mySql.getString());
		    if ( brr1 )  
		    {
		    	alert("��ɨ�������Ͷ�����Ѿ�ǩ��������ɾ������ɨ�����");
				return false;
		    }
		    if(fm.DelReasonCode.value == null || fm.DelReasonCode.value == '')
			{
				alert("����ѡ��֤ɾ��ԭ�����ͣ�");
				divReason.style.display="";
				return false;
			}
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




/*********************************************************************
 *  ��ѯ������ϸ��Ϣʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
 *  ����  ��  ��ѯ���صĶ�ά����
 *  ����ֵ��  ��
 *********************************************************************
 */

function afterQuery( arrQueryResult )
{
	var arrResult = new Array();

	//alert("afterQuery");
	// ��дSQL���
	/*var strSQL = " select docid,doccode,numpages,makedate,maketime,scanoperator,"
					+ "managecom,inputstate,operator,inputstartdate,inputstarttime "
					+ ",docflag,docremark,'',inputenddate,inputendtime,busstype,scanno,subtype " 
					+ " from ES_DOC_MAIN where 1=1 " 
					+ " "+ " and DOCID = " + arrQueryResult[0][0] + " ";	*/		 
	mySql = new SqlClass();
	mySql.setResourceName("easyscan.EsDocManageSql");
	mySql.setSqlId("EsDocManageSql7");
	mySql.addSubPara(arrQueryResult[0][0]);  
	turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 1, 1);  
  
	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult) {
		alert("��ѯʧ�ܣ�");
		return false;
    }
	//��ѯ�ɹ������ַ��������ض�ά����
	arrResult = decodeEasyQueryResult(turnPage.strQueryResult);
	
	if( arrResult != null )
	{
		document.all( 'DOC_ID' ).value = 			arrResult[0][0];
		document.all( 'DOC_CODE' ).value = 		arrResult[0][1];
		document.all('NUM_PAGES').value = 		arrResult[0][2];
		document.all('INPUT_DATE').value = 		arrResult[0][3];
		//document.all('Input_Time').value = 		arrResult[0][4];
		document.all('ScanOperator').value = 		arrResult[0][5];  
		document.all('ManageCom').value = 		arrResult[0][6];
		document.all('InputState').value = 		arrResult[0][7];
		document.all('Operator').value = 			arrResult[0][8];
		document.all('InputStartDate').value = 	arrResult[0][9];
		document.all('InputStartTime').value = 	arrResult[0][10];
		document.all('DOC_FLAGE').value = 		arrResult[0][11];
		document.all('DOC_REMARK').value = 		arrResult[0][12];
		//document.all('Doc_Ex_Flag').value = 		arrResult[0][13];
		document.all('InputEndDate').value = 		arrResult[0][14];
		document.all('InputEndTime').value = 		arrResult[0][15];
		document.all('BussType').value = 		arrResult[0][16];
		document.all('ScanNo').value = 		arrResult[0][17];
		document.all('SubType').value = 		arrResult[0][18];
		if(fm.SubType.value!="")
			showOneCodeName('imagetype','SubType','SubTypeName');
		else
			fm.SubTypeName.value="";
		
		queryPages(arrResult[0][0]);
	}       
	else
		alert("arrQueryResult is null!");
}               
        
        
// ��ѯ��ť
function queryPages(strDoc_Code)
{
	// ��дSQL���
	/*var strSQL = "select pageid,docid,pagecode,hostname,pagename,pageflag,"
					+ "picpathftp,picpath,operator,makedate,maketime "
					+ ",(select subtype from es_doc_main where docid=ES_DOC_PAGES.docid),scanno"
					+ " from ES_DOC_PAGES where 1=1 " 
					+ " "+ " and DOCID in ( select docid from es_doc_relation where bussno='" + strDoc_Code + "' and bussnotype='11') "
					+ " order by docid,pageid";		*/	 
	//prompt('',strSQL);
	mySql = new SqlClass();
	mySql.setResourceName("easyscan.EsDocManageSql");
	mySql.setSqlId("EsDocManageSql8");
	mySql.addSubPara(strDoc_Code); 
	turnPage.queryModal(mySql.getString(), CodeGrid,0,0,100);    
	
	/*strSQL = "select count(*) "
					+ " from ES_DOC_PAGES where 1=1 " 
					+ " "+ " and DOCID in ( select docid from es_doc_relation where bussno='" + strDoc_Code + "' and bussnotype='11') "
					; 	*/
	mySql = new SqlClass();
	mySql.setResourceName("easyscan.EsDocManageSql");
	mySql.setSqlId("EsDocManageSql9");
	mySql.addSubPara(strDoc_Code); 				
	var brr = easyExecSql(mySql.getString());
	//prompt('',strSQL);
	if ( brr )  
	{
		document.all('NUM_PAGES').value = brr[0][0];
	}  		
}



//Click�¼� ���'�����޸�'��ťʱ������ʵ�ֱ���MultiLine��ǰ�޸ĵ����� ��ͨ��
function saveUpdate()
{
	if(!checkOnlyOne(0))
		return false;
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
	if(!checkOnlyOne(3))
		return false;
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

function checkOnlyOne(flag1)
{
	//��ʾû��ѡ���¼
	//������ʾֻ��ѡ��һ����¼
	var count = CodeGrid.mulLineCount;
	var flag = 0;
	var str = '';
	var flaga = 0;
	var str1 = '';
	var str2 = '';
	for(var index=0; index< count; index++)
	{
	   if(CodeGrid.getChkNo(index)==true)
	   {
	   	flag =flag+1;
	   	str = CodeGrid.getRowColData(index,12);	
	   	str2 = CodeGrid.getRowColData(index,2);
	   	if(str1 != '' && str1 != str2)//У���Ƿ��в�һ�µĵ�֤�ڲ���
	   		flaga = 1;//�в�һ�µ�֤�ڲ���
	   	str1 = CodeGrid.getRowColData(index,2);	   	
	   }
	}
	//alert(flag);
  	if(flag ==0)
  	{
  		alert("������ѡ��һ����¼���иò�����");
  		return false;
  	}
  	if((flag1 ==1||flag1 ==2) && flag >1)
  	{
  		alert("ֻ��ѡ��һ����¼���иò�����");
  		return false;
  	}
  	if(flag1 ==2 && str =='UA001')
  	{
  		//У�������ӡˢ����Ͷ�����࣬���ܰѱ���޸�ΪͶ��������
		//var strSQL = " select 1 from ES_DOC_MAIN where subtype='UA001' and doccode='"+fm.PrtNo.value+"'"; 	
		mySql = new SqlClass();
	mySql.setResourceName("easyscan.EsDocManageSql");
	mySql.setSqlId("EsDocManageSql10");
	mySql.addSubPara(fm.PrtNo.value); 
		var brr = easyExecSql(mySql.getString());
		//prompt('',strSQL);
	    if ( brr )  
	    {
	    	alert("��ӡˢ��������Ͷ������ɨ�����");
	    	return false;
	    }
  	}
  	/*if(flag1 ==3 && flaga ==1)
  	{
  		alert("ֻ��ѡ��һ����֤����ͬ�ĵ�֤�ڲ��ţ����в�����");
	    return false;
  	}*/
  	if(flag1 ==3)
  	{
  		//У�������ӡˢ����Ͷ�����࣬���ܰѱ���޸�ΪͶ��������
		//var strSQL = " select nvl(count(*),0) from ES_DOC_PAGES where docid in (select docid from ES_DOC_MAIN where doccode='"+fm.PrtNo.value+"')"; 	
		mySql = new SqlClass();
	mySql.setResourceName("easyscan.EsDocManageSql");
	mySql.setSqlId("EsDocManageSql11");
	mySql.addSubPara(fm.PrtNo.value); 
		var brr = easyExecSql(mySql.getString());
		//prompt('',strSQL);
	    if ( brr && (flag - brr[0][0] == 0))  
	    {	    	
	    	alert("����ɾ����ӡˢ�������е�֤ҳ��Ϣ����ʹ�á�ɾ ������ť��");
	    	return false;
	    }
  	}
  	
  	return true;
}
        