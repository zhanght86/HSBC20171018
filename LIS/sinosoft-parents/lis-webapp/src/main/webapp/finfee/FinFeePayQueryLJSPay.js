var turnPage = new turnPageClass();       //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var tResourceName="finfee.FinFeeGetQueryLJSPaySql";
/**
 * ��̨���ݿ��ѯ
 */
function easyQueryClick() {
  // ƴSQL��䣬��ҳ��ɼ���Ϣ
  /*var strSql = "select getnoticeno, OtherNo, OtherNoType,SumDuePayMoney,AgentCode,RiskCode,StartPayDate from LJSPay where 1=1 and othernotype='2' "
	           + getWherePart( 'GetNoticeNo' )
	           + getWherePart( 'OtherNo' )
	           + getWherePart( 'RiskCode' )
	           ;
 if (fm.AppntName.value!="")
 {
 	strSql=strSql+" and appntno in(select customerno from ldperson where name='"+fm.AppntName.value+"')";
 }	      
 if (fm.InsuredName.value!="")
 {
 	strSql=strSql+" and OtherNo in (select polno from lcpol where InsuredName='"+fm.InsuredName.value+"')";
 }	    */
   var strSql = wrapSql(tResourceName,"querysqldes1",[document.all('GetNoticeNo').value,document.all('OtherNo').value,document.all('RiskCode').value
   									,document.all('AppntName').value,document.all('InsuredName').value]);     
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  

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
		top.opener.document.all('InputNo4').value=QueryLJAGetGrid.getRowColData(tSel-1,1);
		top.opener.document.all('InputNo3').value=QueryLJAGetGrid.getRowColData(tSel-1,2);
		}
		catch(ex)
		{
			alert( "û�з��ָ����ڵĽӿ�" + ex );
		}
		top.close();
	}
}