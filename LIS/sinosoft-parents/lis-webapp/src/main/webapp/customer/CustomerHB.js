//�������ƣ�FinFeeSure.js
//�����ܣ�����ȷ��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var k = 0;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();


var tResourceName = "customer.CustomerHBSql";
var tResourceSQL1="CustomerHBSql1";
var tResourceSQL2="CustomerHBSql2";

//�ύ�����水ť��Ӧ����
function submitForm()
{
	var i = 0;
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ; 
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	initCustomerGrid();
	document.getElementById("fm").submit();//�ύ
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	//document.all("signbutton").disabled=false;
	if (FlagStr == "Fail" )
	{             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ; 
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
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	easyQueryClick();
	easyQueryClick1();
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
function easyQueryClick()
{
		// ��ʼ�����
	initCustomerGrid();
	k++;


	// ��дSQL���
	var strSQL = "";
	//strSQL = "select a.insuaccno,a.customerno,b.name from ficustomeracc a,ldperson b where b.customerno=a.customerno ";
	
	var strSQL2 = "";
	//alert(strSQL);
  if (fm.InsuAccNo.value!="")
  {
	  strSQL2 = strSQL2 + " and a.insuaccno='"+ fm.InsuAccNo.value+"'";
  }
  if (fm.CustomerNo.value!="")
  {
	  strSQL2 = strSQL2 + " and a.CustomerNo='"+ fm.CustomerNo.value+"'";
  }
  if (fm.CustomerName.value!="")
  {
	  strSQL2 = strSQL2 + " and b.name='"+ fm.CustomerName.value+"'";
  }
  
  strSQL = wrapSql(tResourceName,tResourceSQL1,[strSQL2]); 
  turnPage.queryModal(strSQL,CustomerGrid);
  
  //��ѯSQL�����ؽ���ַ���
  /*turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û�����ݣ�");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);	 
				 
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = CustomerGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);

  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  //�����Ƿ���ʾ��ҳ��ť
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
  */
  return true;
}
// ��ѯ��ť
function easyQueryClick1()
{
	// ��ʼ�����
	initCustomer1Grid();
	k++;


	// ��дSQL���
	var strSQL = "";
	//strSQL = "select a.insuaccno,a.customerno,b.name from ficustomeracc a,ldperson b where b.customerno=a.customerno ";
	var strSQL2 = "";
	
  if (fm.InsuAccNo1.value!="")
  {
	  strSQL2 = strSQL2 + " and a.insuaccno='"+ fm.InsuAccNo1.value+"'";
  }
  if (fm.CustomerNo1.value!="")
  {
	  strSQL2 = strSQL2 + " and a.CustomerNo='"+ fm.CustomerNo1.value+"'";
  }
  if (fm.CustomerName1.value!="")
  {
	  strSQL2 = strSQL2 + " and b.name='"+ fm.CustomerName1.value+"'";
  }
  strSQL = wrapSql(tResourceName,tResourceSQL2,[strSQL2]); 
  turnPage2.queryModal(strSQL,Customer1Grid);
  //��ѯSQL�����ؽ���ַ���
  /*turnPage2.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage2.strQueryResult) {
    alert("û�����ݣ�");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage2.arrDataCacheSet = decodeEasyQueryResult(turnPage2.strQueryResult);	 
				 
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage2.pageDisplayGrid = Customer1Grid;    
          
  //����SQL���
  turnPage2.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage2.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage2.getData(turnPage2.arrDataCacheSet, turnPage2.pageIndex, turnPage2.pageLineNum);

  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage2.pageDisplayGrid);
  
  //�����Ƿ���ʾ��ҳ��ť
  if (turnPage2.queryAllRecordCount > turnPage2.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
  */
  return true;
}


//����ȷ��
function autochk()
{

	var haveCheck = false;
	for (i=0; i<CustomerGrid.mulLineCount; i++) 
	{
		if (CustomerGrid.getSelNo(i)) 
		{
			haveCheck = true;
			break;
		}
	}
	//alert(haveCheck);
var haveCheck1 = false;
	for (i=0; i<Customer1Grid.mulLineCount; i++) 
	{
		if (Customer1Grid.getSelNo(i)) 
		{
			haveCheck1 = true;
			break;
		}
	}
	//alert(haveCheck1);
	
	if (haveCheck&&haveCheck1) {
		var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		//showSubmitFrame(mDebug);
		fm.action="./CustomerHBChk.jsp";
		//document.all("signbutton").disabled = true;
		document.getElementById("fm").submit();//�ύ
	}
	else 
	{
		alert("û�пͻ��˻��ϲ�ǰ����˻���Ϣ����ѡ����д򹳣�");
	}
}


