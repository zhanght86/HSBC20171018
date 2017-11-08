//�������ƣ�QueryCache.js
//�����ܣ���ѯ�������ҳ��
//�������ڣ�2002-09-28
//������  ������
//���¼�¼��  ������    ��������     ����ԭ��/����
   
var sqlCacheIndex = 0;                //��¼SQL����������е�λ��
var sqlCacheCount = 0;                //��¼�µ�SQL��������
var arrSql = new Array();             //��¼SQL��������
var arrQueryResult = new Array();     //��¼��ÿ��SQL����Ӧ�Ĳ�ѯ���

//�ж�SQL����Ƿ񻺴��У�����з����ڴ��еĲ�ѯ�����û����洢SQL��䣬����FALSE
function SqlCache(strSql) {
  var isDupliQuery = false;           //�жϵ�ǰSQL����Ƿ��������м�¼�ظ��ı�־
  //alert("sqlCacheCount:" + sqlCacheCount + "sqlCacheIndex:" + sqlCacheIndex );
  
  //�ж�SQL����Ƿ��Ѿ��������д��ڣ����
  for (sqlCacheIndex=0; sqlCacheIndex<sqlCacheCount; sqlCacheIndex++) {
    if (arrSql[sqlCacheIndex] == strSql) {
      isDupliQuery = true;          
      //alert("arrSql:" + arrSql + "\narrQueryResult:" + arrQueryResult);
      break;
    };
  };
  
  if (isDupliQuery) {                 //���SQL�����ڣ����ض�Ӧ�Ĳ�ѯ����ַ���  
    return arrQueryResult[sqlCacheIndex]; 
  } else {                            //��������ڣ��򽫸���SQL����¼��������
    arrSql[sqlCacheCount] = strSql;
    sqlCacheCount++;
    return isDupliQuery;
  }            
}

//�洢��ѯ���
function setQueryResult(strResult) {
  arrQueryResult[sqlCacheCount - 1] = strResult;
}

//ǿ�Ʋ�ѯ
function mustQuery(strSql) {
  for (sqlCacheIndex=0; sqlCacheIndex<sqlCacheCount; sqlCacheIndex++) {
    if (arrSql[sqlCacheIndex] == strSql) {
      arrSql[sqlCacheIndex] = "";          
      arrQueryResult[sqlCacheIndex] = "";
      break;
    };
  };	
}