//
//�������ƣ�CBCDZQuery1.js
//�����ܣ�δ¼�����ݲ�ѯ
//�������ڣ�2008-07-02 15:10
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
			alert("��ʼʱ��ͽ���ʱ�䲻��Ϊ��!");
			return false;
	}
	
	initCodeGrid();
	
	var comcode=fm.ManageCom.value;
	var code=comcode.substr(2,2);
	//alert(code);
	// ��дSQL���
	/*var strSQL = "select g.cardno,g.trandate,g.tranamnt,g.makedate,g.brno "
             + " from lis.lkbalancedetail g  "
             + " where g.bankcode='03'  and g.confirmflag='1'  "
             + getWherePart('trandate','StartDate','>=')
             + getWherePart('trandate','EndDate','<=')
             //+ getWherePart('bankzonecode','ManageCom','like')
             + " and bankzonecode like '"+code+"%' "
             +  "order by trandate";*/
        //alert(strSQL);    
//      prompt('',strSQL);
	mySql = new SqlClass();
	mySql.setResourceName("app.CBCDZQuerySql");
	mySql.setSqlId("CBCDZQuerySql1");
	mySql.addSubPara(fm.StartDate.value ); 
	mySql.addSubPara(fm.EndDate.value ); 
	mySql.addSubPara(code); 
	turnPage.queryModal(mySql.getString(), CodeGrid);    

}


