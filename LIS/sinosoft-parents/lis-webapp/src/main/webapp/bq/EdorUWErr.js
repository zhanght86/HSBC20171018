//�������ƣ�EdorUWErr.js
//�����ܣ��˹��˱�δ��ԭ���ѯ
//�������ڣ�2005-06-23 15:10:36
//������  ��liurongxiao
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;

var turnPage = new turnPageClass();





// �˹��˱�δ��ԭ���ѯ
function easyQueryClick()
{
	// ��ʼ�����
	initUWErrGrid();
	
	// ��дSQL���
	var strSQL = "";
	var ContNo = fm.ContNo.value;
	var EdorNo = fm.EdorNo.value;
//	strSQL = "select a.edorno,a.contno,a.uwno,a.uwerror,a.modifydate from LPUWError a where 1=1 "
//			 + " and a.PolNo in (select distinct polno from lppol where contno ='" +ContNo+ "' and edorno='"+EdorNo+"')"
//			 + " and (a.uwno = (select max(b.uwno) from LPUWError b where b.ContNo = '" +ContNo + "' and b.EdorNo = '"+EdorNo+"' and b.PolNo = a.PolNo))"
//			 + " and a.EdorNo='"+EdorNo+"'"
//			 + " union "
//			 + "select c.edorno,c.contno,c.uwno,c.uwerror,c.modifydate from LPCUWError c where 1=1"
//			 + " and c.ContNo ='" + ContNo + "'"+" and c.EdorNo='"+EdorNo+"'"
//			 + " and (c.uwno = (select max(d.uwno) from LPCUWError d where d.ContNo = '" + ContNo + "' and d.EdorNo = '"+EdorNo+"'))"
	var sqlid1="EdorUWErrSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.EdorUWErrSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(ContNo);//ָ������Ĳ���
	mySql1.addSubPara(EdorNo);//ָ������Ĳ���
	mySql1.addSubPara(ContNo);//ָ������Ĳ���
	mySql1.addSubPara(EdorNo);//ָ������Ĳ���
	mySql1.addSubPara(EdorNo);//ָ������Ĳ���
	mySql1.addSubPara(ContNo);//ָ������Ĳ���
	mySql1.addSubPara(EdorNo);//ָ������Ĳ���
	mySql1.addSubPara(ContNo);//ָ������Ĳ���
	mySql1.addSubPara(EdorNo);//ָ������Ĳ���
	strSQL=mySql1.getString();			 
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    
    alert("�����պ˱���Ϣ��");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = UWErrGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  return true;

}


