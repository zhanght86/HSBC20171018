
//�������ƣ�BankSerialNoQuery.js
//�����ܣ����з��̻������β�ѯ
//�������ڣ�2010-04-12
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
	if (fm.Sdate.value == "" ||fm.Edate.value == "")
	{
			alert("��ʼ���ںͽ������ڲ���Ϊ��!");
			return false;
	}
	
	initCodeGrid();
	
	var sdate = fm.Sdate.value;
	var edate = fm.Edate.value;
	var managecom = "";
	var bankcode = "";
	var ystatus = "";
	/*var str1 = "";
	var str2="";
	var str3="";*/
	
	
	if (fm.Managecom.value != ""  )
	{
		managecom = fm.Managecom.value;
		//str1= "  and comcode like '" + managecom + "'" ;
	}
	if (fm.Bankcode.value != ""  )
	{
		bankcode = fm.Bankcode.value;
		//str2= " and bankcode = '" + bankcode + "'" ;
	}
	if (fm.YStatus.value != ""  )
	{
		ystatus = fm.YStatus.value;
		//str3= " and logtype = '" + ystatus + "'" ;
	}
	
	
               
       /*var strSQL  = "select a.comcode,a.bankcode,(select bankname from ldbank where bankcode=a.bankcode)," 
                   + "a.serialno,a.dealstate,a.senddate,a.returndate,a.totalmoney,a.totalnum,a.banksuccmoney,a.banksuccnum,a.logtype"
                   + " from lybanklog a"
                   + " where senddate>='" + sdate +"'"
                   + " and senddate<='" + edate +"'"
                   + str1
                   + str2  
                   + str3                                                   
                   ;*/
        mySql = new SqlClass();
		mySql.setResourceName("finfee.BankSerialNoQuerySql");
		mySql.setSqlId("BankSerialNoQuerySql1");
		mySql.addSubPara(sdate);
		mySql.addSubPara(edate);  
		mySql.addSubPara(managecom);  
		mySql.addSubPara(bankcode);  
		mySql.addSubPara(ystatus);            

	//alert(code);
	// ��дSQL���
	
	 
        //alert(strSQL);    
//      prompt('',strSQL);
	turnPage.queryModal(mySql.getString(), CodeGrid);    

}


