
var arrDataSet 
var tArr;
var turnPage = new turnPageClass();

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
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
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

  fm.submit(); //�ύ
  showDiv(operateButton,"true"); 
  showDiv(inputButton,"false"); 
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
	//alert("In afterSubmit");
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
    queryPages(document.all('ManageCom').value);
    queryPages(document.all('Host_Name').value);
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
//  	var i = 0;
//  	var showStr="����������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
//  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  showDiv(operateButton,"false"); 
  showDiv(inputButton,"true"); 
 	fm.fmtransact.value = "INSERT";
//	codeGrid.setRowColData(5,1,request.getParameter("ManageCom"));
//	codeGrid.setRowColData(5,2,request.getParameter("Host_Name"));
//  	fm.submit(); //�ύ
}           

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
  	alert("��ʱ���ṩ�޸Ĺ���");
  //����������Ӧ�Ĵ���
/*  if (confirm("��ȷʵ���޸ĸü�¼��?"))
  {
  	var i = 0;
  	var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  	//showSubmitFrame(mDebug);
  	fm.fmtransact.value = "UPDATE";
  	fm.submit(); //�ύ 
  }
  else
  {
    //mOperate="";
    alert("��ȡ�����޸Ĳ�����");
  } */
}           

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
  //����������Ӧ�Ĵ���
  //mOperate="QUERY||MAIN";
  showInfo=window.open("./ServerMainQuery.html");
}           

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  //����������Ӧ��ɾ������
  if (confirm("��ȷʵ��ɾ���ü�¼��?"))
  {
  	
	var i = 0;
  	var showStr="����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  	//showSubmitFrame(mDebug);
  	fm.fmtransact.value = "DELETE";

  	fm.submit(); //�ύ
  	initForm();
  }
  else
  {
    //mOperate="";
    alert("��ȡ����ɾ��������");
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
//	var strSQL = " select ManageCom,HostName from ES_COM_SERVER where 1=1 " 
//				+ " "+ " and ManageCom = " + arrQueryResult[0][0] + " ";
	
	
	var sqlid0="ServerRelationSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("easyscan.ServerRelationSql"); //ָ��ʹ�õ�properties�ļ���
	mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
	mySql0.addSubPara(arrQueryResult[0][0]);//ָ������Ĳ���
	var strSQL=mySql0.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
  
	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult) {
		alert("��ѯʧ�ܣ�");
		return false;
    }
	//��ѯ�ɹ������ַ��������ض�ά����
	arrResult = decodeEasyQueryResult(turnPage.strQueryResult);
	
	if( arrResult != null )
	{
		document.all( 'ManageCom' ).value =	arrResult[0][0];
		document.all( 'Host_Name' ).value = 	arrResult[0][1];
		
		queryPages(arrResult[0][0]);
	}       
	else
		alert("arrQueryResult is null!");
}               
        
        
// ��ѯ��ť
function queryPages(strDoc_id)
{
	// ��дSQL���
//	var strSQL = "select ManageCOM,HostName from ES_COM_SERVER where 1=1 " ;
	
	var sqlid1="ServerRelationSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("easyscan.ServerRelationSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	var strSQL=mySql1.getString();
	
	//alert(strSQL);
	turnPage.queryModal(strSQL, CodeGrid);    
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
}



//Click�¼� ���'�����޸�'��ťʱ������ʵ�ֱ���MultiLine��ǰ�޸ĵ����� ��ͨ��
function saveUpdate()
{
  if (confirm("��ȷʵ�뱣���޸���?"))
  {
  	fm.fmtransact.value = "UPDATE";
  	
  	//ѡ��������
  	//CodeGrid.checkBoxAll ()

  	fm.submit(); //�ύ
  	//initForm();
  }
}        
//Click�¼� ���'ɾ��ѡ��'��ťʱ������ʵ��ɾ��ѡ�е�MultiLine������ ��ͨ��
function deleteChecked()
{
  if (confirm("��ȷʵ��ɾ��ѡ�еĵ�֤ҳ������?"))
  {
	
  	fm.fmtransact.value = "DELETE";

  	fm.submit(); //�ύ
  	//���²�ѯ
  	//queryPages(document.all( 'DOC_ID' ).value);
  	//initForm();
  }
}
        
//***************************************************************


function addNewComp()
{
  	
  	fm.fmtransact.value = "INSERT";
//	codeGrid.setRowColData(5,1,request.getParameter("ManageCom"));
//	codeGrid.setRowColData(5,2,request.getParameter("Host_Name"));
  	fm.submit(); //�ύ
}

function easyQuery()
{
	// ��дSQL���
//	var strSQL = "select ManageCom,HostName from ES_COM_SERVER where 1=1 " 
//					+ " "+ getWherePart( 'ManageCom' );	
	
  	var  ManageCom2 = window.document.getElementsByName(trim("ManageCom"))[0].value;
	var sqlid2="ServerRelationSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("easyscan.ServerRelationSql"); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(ManageCom2);//ָ������Ĳ���
	var strSQL=mySql2.getString();
	
	//alert(strSQL);
	turnPage.queryModal(strSQL, CodeGrid);    
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	
/*	// ��ʼ�����
	initCodeGrid();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
  
	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult) {
		alert("��ѯʧ�ܣ�");
		return false;
    }
	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
	//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	turnPage.pageDisplayGrid = CodeGrid;    
          
	//����SQL���
	turnPage.strQuerySql     = strSQL; 
	//���ò�ѯ��ʼλ��
	turnPage.pageIndex       = 0;  
  
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
 	tArr=chooseArray(arrDataSet,[0,1,2,3,4,5,6,7,8,9]) 
 	
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(tArr, turnPage.pageDisplayGrid);
*/ 
}	
