//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

// ��ѯ��ť
function easyQueryClick()
{
	if(!beforeSubmit())
	 {
	  return false;
	 }
	// ��ʼ�����
	initIndiDueFeeListGrid();
/*	
	// ��дSQL���
	var strSQL = "";
	var strSQLXQ = "";
	var strSQLXB = "";
	//��ѯ������Ϣ
	if(_DBT==_DBO){
		strSQLXQ = "select ContNo,PolNo,RiskCode,(case PayIntv when -1 then '�����ڽ���' when 0 then '�� ��' when 1 then '�� ��' when 3 then '�� ��' when 6 then '���꽻' when 12 then '�� ��' else '' end) PayIv,'����' xqxb,(case (select 1 from LJSpayPerson where Polno=LCPol.Polno and rownum=1) when 1 then '�����Ѵ���' else '����δ����' end) LJSState,Paytodate,PayEnddate,(case (select distinct(contno) from LPEdormain where contno=LCPol.contno and abs(LCPol.paytodate - edorappdate)<=60) when NULL then '�ޱ�ȫ' else '�� ȫ' end) BQ,(case (SELECT polno FROM ldsystrace WHERE polstate='4001' AND valiflag='1' AND polno=LCPol.PolNo) when NULL then '����������' else '������������') LiPei,Agentcode from LCPol where 1=1 and AppFlag='1' "			 
			 + "and Paytodate < PayEnddate "
			 + getWherePart( 'ContNo','ContNo' )
			 + getWherePart( 'MainPolNo','MainPolNo' )
			 + getWherePart( 'RiskCode','RiskCode' )
			 ;
	}else if(_DBT==_DBM){
		strSQLXQ = "select ContNo,PolNo,RiskCode,(case PayIntv when -1 then '�����ڽ���' when 0 then '�� ��' when 1 then '�� ��' when 3 then '�� ��' when 6 then '���꽻' when 12 then '�� ��' else '' end) PayIv,'����' xqxb,(case (select 1 from LJSpayPerson where Polno=LCPol.Polno limit 0,1) when 1 then '�����Ѵ���' else '����δ����' end) LJSState,Paytodate,PayEnddate,(case (select distinct(contno) from LPEdormain where contno=LCPol.contno and abs(LCPol.paytodate - edorappdate)<=60) when NULL then '�ޱ�ȫ' else '�� ȫ' end) BQ,(case (SELECT polno FROM ldsystrace WHERE polstate='4001' AND valiflag='1' AND polno=LCPol.PolNo) when NULL then '����������' else '������������') LiPei,Agentcode from LCPol where 1=1 and AppFlag='1' "			 
			 + "and Paytodate < PayEnddate "
			 + getWherePart( 'ContNo','ContNo' )
			 + getWherePart( 'MainPolNo','MainPolNo' )
			 + getWherePart( 'RiskCode','RiskCode' )
			 ;
	}
	
				 
	strSQLXB = "select ContNo,PolNo,RiskCode,(case PayIntv when -1 then '�����ڽ���' when 0 then '�� ��' when 1 then '�� ��' when 3 then '�� ��' when 6 then '���꽻' when 12 then '�� ��' else '' end) PayIv,'����' xqxb,(case (select 1 from LCRnewStateHistory where contno=LCPol.contno and riskcode=LCPol.riskcode and State='4') when 1 then '�����Ѵ���' else '����δ����' end) LJSState,Paytodate,PayEnddate,(case (select distinct(contno) from LPEdormain where contno=LCPol.contno and abs(LCPol.paytodate - edorappdate)<=60) when NULL then '�ޱ�ȫ' else '�� ȫ' end) BQ,(case (SELECT polno FROM ldsystrace WHERE polstate='4001' AND valiflag='1' AND polno=LCPol.PolNo) when NULL then '����������' else '������������' end) LiPei,Agentcode from LCPol where 1=1 and AppFlag='1' "			 
				 + "and Paytodate = PayEnddate "
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'MainPolNo','MainPolNo' )
				 + getWherePart( 'RiskCode','RiskCode' )
                                 ;                                 
                                 
      strSQL = "select ContNo,PolNo,RiskCode,PayIv,xqxb,LJSState,PaytoDate,PayEndDate,BQ,LiPei,Agentcode from (  "+strSQLXQ + " union all "+ strSQLXB +") order by PolNo";	
      */
      var strSQL = "";
      var sqlId = "";
      if(_DBT==_DBO){
    	  sqlId = "OnePolStatesListSql1";
      }else if(_DBT==_DBO){
    	  sqlId = "OnePolStatesListSql2";
      }
		var mySql1 = new SqlClass();
		mySql1.setResourceName("operfee.OnePolStatesListSql");
		mySql1.setSqlId(sqlId);
		mySql1.addSubPara(window.document.getElementsByName(trim('ContNo'))[0].value);
		mySql1.addSubPara(window.document.getElementsByName(trim('MainPolNo'))[0].value);
		mySql1.addSubPara(window.document.getElementsByName(trim('RiskCode'))[0].value);
		mySql1.addSubPara(window.document.getElementsByName(trim('ContNo'))[0].value);
		mySql1.addSubPara(window.document.getElementsByName(trim('MainPolNo'))[0].value);
		mySql1.addSubPara(window.document.getElementsByName(trim('RiskCode'))[0].value);
		strSQL = mySql1.getString();
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("�Ҳ��������ѯ�����ı�����");
    return false;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = IndiDueQueryGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  arrGrid = arrDataSet;
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
    initForm();
  }
  catch(re)
  {
  	alert("��Proposal.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���
    if((fm.ContNo.value == null||fm.ContNo.value =='')&&(fm.MainPolNo.value == null||fm.MainPolNo.value ==''))
     {
       alert("�����Ż��������ֺ��벻��Ϊ��!");
       return false;
     }
    return true;
}           

function returnParent()
{
    top.close();
}
