var turnPage = new turnPageClass();       //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var tResourceName="finfee.FinFeePayQueryLJAGetSql";
/**
 * ��̨���ݿ��ѯ
 */
function easyQueryClick() {
  // ƴSQL��䣬��ҳ��ɼ���Ϣ
  /*var strSql = "select ActuGetNo, OtherNo, OtherNoType,PayMode,Currency,SumGetMoney,EnterAccDate,Drawer,DrawerID,bankcode,bankaccno,accname from LJAGet where 1=1 and ConfDate is null and EnterAccDate is null and (bankonthewayflag='0' or bankonthewayflag is null) and managecom like '"+manageCom+"%%' "
	           + getWherePart( 'ActuGetNo' )
	           + getWherePart( 'OtherNo' )
	           + getWherePart( 'PayMode' );*/
  var strSql = wrapSql(tResourceName,"querysqldes1",[manageCom,document.all('ActuGetNo').value,document.all('OtherNo').value,document.all('PayMode').value]);
  //alert(strSql);	           
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {  
    //���MULTILINE��ʹ�÷�����MULTILINEʹ��˵�� 
    QueryLJAGetGrid.clearData('QueryLJAGetGrid');  
    alert("û�в�ѯ�����ݣ�");
    return false;
  }
  
  //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = QueryLJAGetGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSql; 
  
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
  
}

// ���ݷ��ظ�����
function returnParent()
{
	var arrReturn = new Array();
	var tSel = QueryLJAGetGrid.getSelNo();
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		try
		{
			//alert(1);
	// alert(QueryLJAGetGrid.getRowColData(tSel-1,1));
	 top.opener.QueryLJAGetGrid.addOne("QueryLJAGetGrid");
     top.opener.QueryLJAGetGrid.setRowColData(0,1,QueryLJAGetGrid.getRowColData(tSel-1,1));
     top.opener.QueryLJAGetGrid.setRowColData(0,2,QueryLJAGetGrid.getRowColData(tSel-1,2));
     top.opener.QueryLJAGetGrid.setRowColData(0,3,QueryLJAGetGrid.getRowColData(tSel-1,3));
     top.opener.QueryLJAGetGrid.setRowColData(0,4,QueryLJAGetGrid.getRowColData(tSel-1,4));
     top.opener.QueryLJAGetGrid.setRowColData(0,5,QueryLJAGetGrid.getRowColData(tSel-1,5));
     top.opener.QueryLJAGetGrid.setRowColData(0,6,QueryLJAGetGrid.getRowColData(tSel-1,6));
     top.opener.QueryLJAGetGrid.setRowColData(0,7,QueryLJAGetGrid.getRowColData(tSel-1,7));
     top.opener.QueryLJAGetGrid.setRowColData(0,8,QueryLJAGetGrid.getRowColData(tSel-1,8));
     top.opener.QueryLJAGetGrid.setRowColData(0,9,QueryLJAGetGrid.getRowColData(tSel-1,9));

     top.opener.fm.ActuGetNo.value=QueryLJAGetGrid.getRowColData(tSel-1,1);
		 top.opener.document.all('ActuGetNo').value=QueryLJAGetGrid.getRowColData(tSel-1,1);
		 top.opener.fmSave.PolNo.value=QueryLJAGetGrid.getRowColData(tSel-1,2);
		 top.opener.fmSave.PayMode.value=QueryLJAGetGrid.getRowColData(tSel-1,4);
top.opener.fmSave.Currency.value=QueryLJAGetGrid.getRowColData(tSel-1,5);
		 top.opener.fmSave.GetMoney.value=QueryLJAGetGrid.getRowColData(tSel-1,6);
		 top.opener.fmSave.EnterAccDate.value=QueryLJAGetGrid.getRowColData(tSel-1,7);
	//	top.opener.fmSave.ConfDate.value=QueryLJAGetGrid.getRowColData(tSel-1,8); 
	    var mPayMode = QueryLJAGetGrid.getRowColData(tSel-1,4);
     if(mPayMode=="4")
     {
	    top.opener.fmSave.BankCode.value=QueryLJAGetGrid.getRowColData(tSel-1,10);
	    top.opener.fmSave.BankAccNo.value=QueryLJAGetGrid.getRowColData(tSel-1,11);
	    top.opener.fmSave.BankAccName.value=QueryLJAGetGrid.getRowColData(tSel-1,12);
     }
    else if(mPayMode=="2"|| mPayMode=="3")
    {
    	top.opener.fmSave.BankCode2.value=QueryLJAGetGrid.getRowColData(tSel-1,10);
    	top.opener.fmSave.ChequeNo.value=QueryLJAGetGrid.getRowColData(tSel-1,11);
    }
    else
    {
    	top.opener.fmSave.BankCode.value="";
	    top.opener.fmSave.BankAccNo.value="";
	    top.opener.fmSave.BankAccName.value="";
    	top.opener.fmSave.BankCode2.value="";
    	top.opener.fmSave.ChequeNo.value="";
    }                 
     top.opener.showBankAccNo();

		}
		catch(ex)
		{
			alert( "û�з��ָ����ڵĽӿ�" + ex );
		}
		top.opener.queryLJAGet();
		top.close();
	}
}