
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var showInfo;
var queryflag=0;////��ѯ��־�������ڵ�����ذ�ťǰ��������ѯ��ť���鿴һ�����صļ�¼:0��ʾδ�����1��ʾ����˼�¼�����Ҳ�ѯ���˼�¼

// ��ѯ��ť
function easyQueryClick()
{

	// ��дSQL���
	//alert(CertifyCode);
	var str="";
	//���ݿ��ŵõ����ֻ�����Ϣ
//	var strSQL=" select d.riskcode,(select riskname from lmrisk where lmrisk.riskcode = d.riskcode),"
//	           + " d.insuyear, (case trim(d.insuyearflag) when 'Y' then '��' when 'D' then '��' else '��' end),d.prem"
//	           + " from LMCardRisk a,ratecard d where a.riskcode=d.riskcode and a.prem = d.prem"
//               + " and a.certifyCode='"+CertifyCode+"'"
               
                var sqlid1="SelfRiskInformationQuerySql1";
            	var mySql1=new SqlClass();
            	mySql1.setResourceName("selflist.SelfRiskInformationQuerySql");
            	mySql1.setSqlId(sqlid1); //ָ��ʹ��SQL��id
            	mySql1.addSubPara(CertifyCode);//ָ���������
            	var strSQL = mySql1.getString();
               
  
   //prompt("",strSQL);
   turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1); 
  
   //�ж��Ƿ��ѯ�ɹ�
   if (!turnPage.strQueryResult) 
   {
  		queryflag=0;
    	alert("û�в�ѯ���Ķ�Ӧ��������Ϣ��");
    	return false;
  	}
    
  	queryflag=1;
  	//��ѯ�ɹ������ַ��������ض�ά����
  	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  	//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  	turnPage.pageDisplayGrid = RiskInfo;    
          
  	//����SQL���
  	turnPage.strQuerySql     = strSQL; 
  
  	//���ò�ѯ��ʼλ��
  	turnPage.pageIndex       = 0;  
  
  	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  	arrDataSet= turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
 	//tArr=chooseArray(arrDataSet,[0]) 
  	//����MULTILINE������ʾ��ѯ���
  
  	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  	//displayMultiline(tArr, turnPage.pageDisplayGrid);
 
}
