//
//�������ƣ�YBTDZQuery.js
//�����ܣ��ʱ�ͨ�������ݲ�ѯ
//�������ڣ�2008-01-24 15:10
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����

//var arrDataSet;
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//�򵥲�ѯ

function easyPrint()
{
	easyQueryPrint(2,'CodeGrid','turnPage');
}

function easyQuery()
{	
	if (fm.StartDate.value == "" || fm.EndDate.value == "" )
	{
			alert("��ʼʱ��ͽ���ʱ�䲻��Ϊ��2!");
			return false;
	}
	
	initCodeGrid();
	
	// ��дSQL���
	/*var strSQL = "select  b.managecom,a.banknode,a.transdate,a.makedate"
             + " from lktransstatus a ,lkcodemapping b"
             + " where a.banknode=b.banknode and b.remark5='1' and funcflag ='17'"
             + getWherePart('transdate','StartDate','>=')
             + getWherePart('transdate','EndDate','<=')
             + getWherePart('b.ManageCom','ManageCom','like')
             + " and b.managecom  like '"+comCode+"%' "
             +  "order by b.managecom,a.transdate";*/
//prompt('',strSQL);
	mySql = new SqlClass();
	mySql.setResourceName("app.YBTDZQuerySql");
	mySql.setSqlId("YBTDZQuerySql1");
	mySql.addSubPara(fm.StartDate.value ); 
	mySql.addSubPara(fm.EndDate.value ); 
	mySql.addSubPara(fm.ManageCom.value ); 
	mySql.addSubPara(comCode ); 
	turnPage.queryModal(mySql.getString(), CodeGrid);    

}


