//
//�������ƣ�ProposalIndQuery.js
//�����ܣ�����״̬�켣��ѯ
//�������ڣ�2007-03-26 10:02
//������  ��Fuqx
//���¼�¼��  ������    ��������     ����ԭ��/����

//var arrDataSet;
var turnPage = new turnPageClass();

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
	//�������Ϊ��ʱ
	var tBPOSQL = "";
//	if(fm.BPOID.value!=null && trim(fm.BPOID.value)!="")
//	{
//		tBPOSQL= " and exists(select 1 from BPOMissionState where bussno=bpoerrlog.bussno and BussNoType='TB' and bpoid='"+trim(fm.BPOID.value)+"') ";
//	}

	//���������Ϊ��
	var tManageComSQL = "";
	var comCode1 = "";
	if(fm.ManageCom.value!=null && trim(fm.ManageCom.value)!="")
	{
		comCode1 = comCode;
//		tManageComSQL= " and exists(select 1 from ES_Doc_Main where DocCode=trim(bpoerrlog.bussno) and  Managecom like '"+trim(fm.ManageCom.value)+"%%' "
//		              +" and ManageCom like '"+comCode+"%%') ";
	}

	initCodeGrid();
	
//	// ��дSQL���
//	var strSQL = " select bussno,errorcontent,"
//             + "(select concat(concat(to_char(makedate,'yyyy-mm-dd'),' '),maketime) from bpomissiondetailstate where bussno=bpoerrlog.bussno and bussnotype='TB' and dealcount='1') databackdate,"
//             + "concat(concat(to_char(makedate,'yyyy-mm-dd'),' '),maketime) inputdate "
//             + "from bpoerrlog "
//             + "where errver='3' and makedate>='"+fm.StartDate.value+"' "
//             + "and makedate<='"+fm.EndDate.value+"' "
//             + tBPOSQL
//             + tManageComSQL
//             + " order by makedate,maketime";
	
	
	var StartDate = fm.StartDate.value;
	var EndDate = fm.EndDate.value;
	var BPOID = trim(fm.BPOID.value);
	var ManageCom = trim(fm.ManageCom.value);
	var sqlid1="BPOInputErrQuerySql0";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.BPOInputErrQuerySql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(StartDate);//ָ������Ĳ���
	mySql1.addSubPara(EndDate);//ָ������Ĳ���
	mySql1.addSubPara(BPOID);//ָ������Ĳ���
	mySql1.addSubPara(ManageCom);//ָ������Ĳ���
	mySql1.addSubPara(comCode1);//ָ������Ĳ���
	var strSQL=mySql1.getString();
	
//prompt('',strSQL);
	turnPage.queryModal(strSQL, CodeGrid);    

}


