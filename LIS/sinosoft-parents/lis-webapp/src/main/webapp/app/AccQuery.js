//
//�������ƣ�AccQuery.js
//�����ܣ��˻����ݲ�ѯ
//�������ڣ�2008-03-17 15:10
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����

//var arrDataSet;
var turnPage = new turnPageClass();

var tResourceName="app.AccQuerySql";
var tResourceSQL1="AccQuerySql1";

//�򵥲�ѯ

function easyPrint()
{
	easyQueryPrint(2,'CodeGrid','turnPage');
}

function easyQuery()
{	
    if (fm.StartDate.value == "" || fm.EndDate.value == "" )
	{
			alert("��ʼʱ��ͽ���ʱ�䲻��Ϊ��!");
			return false;
	}
	
	// ��дSQL���
	//var strSQL = "select  bankaccno,accname"
  //           + " from lccont"
  //           + " where bankaccno is not null"
  //           + getWherePart('signdate','StartDate','>=')
  //           + getWherePart('signdate','EndDate','<=')
  //           + getWherePart('ManageCom','ManageCom','like')
  //           + getWherePart('bankcode','BankCode');  
  //
  var strSQL  = wrapSql(tResourceName,tResourceSQL1,[fm.StartDate.value,fm.EndDate.value,fm.ManageCom.value,fm.BankCode.value]);              
      
	turnPage.queryModal(strSQL, CodeGrid);
}


