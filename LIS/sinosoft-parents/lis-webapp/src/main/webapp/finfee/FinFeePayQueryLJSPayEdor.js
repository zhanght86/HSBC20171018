var turnPage = new turnPageClass();        //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var tResourceName="finfee.FinFeePayQueryLJSPayEdorSql";
/**
 * ��̨���ݿ��ѯ
 */
function easyQueryClick() {
  // ƴSQL��䣬��ҳ��ɼ���Ϣ
  /*strSql = "select getnoticeno, OtherNo, OtherNoType,SumDuePayMoney,AgentCode,RiskCode,StartPayDate,managecom from LJSPay where 1=1 and othernotype='10' and getnoticeno not in (select tempfeeno from ljtempfee where tempfeetype='4')"
         + getWherePart( 'OtherNo' ) ;
 
 if (fm.ContNo.value!="")
 {
  strSql=strSql+" and Otherno in (select EdorAcceptNo from lpedorapp where otherno ='"+fm.ContNo.value+"')";	
 }
 if (fm.AppntName.value!="")
 {
 	strSql=strSql+" and appntno in(select customerno from ldperson where name='"+fm.AppntName.value+"')";
 }	      
 if (fm.InsuredName.value!="")
 {
 	strSql=strSql+" and OtherNo in (select polno from lcpol where InsuredName='"+fm.InsuredName.value+"')";
 }	     */
 var strSql = wrapSql(tResourceName,"querysqldes1",[document.all('OtherNo').value,document.all('ContNo').value
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
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
  
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
		if (top.opener.document.all('TempFeeType').value == "4")
		     {
		      top.opener.document.all('InputNo8').value=QueryLJAGetGrid.getRowColData(tSel-1,1);
		      top.opener.document.all('InputNo7').value=QueryLJAGetGrid.getRowColData(tSel-1,2);		
	        top.opener.document.all('AgentCode').value=QueryLJAGetGrid.getRowColData(tSel-1,6);
	        top.opener.document.all('PolicyCom').value=QueryLJAGetGrid.getRowColData(tSel-1,9);
	       }
	  else if (top.opener.document.all('TempFeeType').value == "2")
	  	  { 
		     top.opener.document.all('InputNo4').value=QueryLJAGetGrid.getRowColData(tSel-1,1);
		     top.opener.document.all('InputNo3').value=QueryLJAGetGrid.getRowColData(tSel-1,2);
		     top.opener.document.all('AgentCode').value=QueryLJAGetGrid.getRowColData(tSel-1,6);
		     top.opener.document.all('PolicyCom').value=QueryLJAGetGrid.getRowColData(tSel-1,9);
		   }
		}
		catch(ex)
		{
			alert( "û�з��ָ����ڵĽӿ�" + ex );
		}
		top.close();
	}
}