//�������ƣ�LLUWNoticeQuery.js
//�����ܣ��˱�֪ͨ���ѯ
//�������ڣ�2005-12-05 
//������  �������


var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var mySql = new SqlClass();
//�ӷ���Ϣ��ѯ
function LJSPayQuery()
{
	var tClmNo = fm.ClmNo.value ;
	var tContNo = fm.ContNo.value;
	
	var strSQL = "";
	/*strSQL = " select getnoticeno,appntno,sumduepaymoney,paydate,approvecode "
	       + " from ljspay where otherno = '"+tClmNo+"' and othernotype = '5' "
	       + " and serialno='"+tContNo+"' ";*/
	mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWLJSPayQueryInputSql");
	mySql.setSqlId("LLUWLJSPayQuerySql1");
	mySql.addSubPara(tClmNo ); 
	mySql.addSubPara(tContNo ); 
//	prompt("���˼ӷ���Ϣ��ѯ��ѯ",strSQL);
    turnPage.queryModal(mySql.getString(),LJSPayGrid);

}


//����Ӧ�ս��ѱ��¼��ѯ
function GetLJSPayPerson()
{
	var tSel = LJSPayGrid.getSelNo()-1;
	var tNoticeNo  = LJSPayGrid.getRowColData(tSel,1);
	/*var tSQL = "";
	tSQL = " select polno,dutycode,payplancode,paytype,sumduepaymoney,lastpaytodate,curpaytodate"
	       + " from ljspayperson where getnoticeno = '"+tNoticeNo+"'";*/
	mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWLJSPayQueryInputSql");
	mySql.setSqlId("LLUWLJSPayQuerySql2");
	mySql.addSubPara(tNoticeNo ); 

	//prompt("����Ӧ�ս��ѱ��¼��ѯ",tSQL);
    turnPage1.queryModal(mySql.getString(),LJSPayPersonGrid);
    
    //QueryLJSPayClaim();
}      

//��ѯ��Ϣ����
function QueryLJSPayClaim()
{
	var tNoticeNo  = LJSPayGrid.getRowColData(0,1);
	var tClmNo     = fm.ClmNo.value ;
	
    var tPaySQL   = "";
    fm.tPay.value = "";
    /*tPaySQL = " select Pay from ljspayclaim "
            + " where OtherNoType='5' and OtherNo='"+tClmNo+"' and getnoticeno='"+tNoticeNo+"' ";  */   
	mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWLJSPayQueryInputSql");
	mySql.setSqlId("LLUWLJSPayQuerySql3");
	mySql.addSubPara(tClmNo ); 
	mySql.addSubPara(tNoticeNo ); 
	//prompt("��ѯ��Ϣ������ѯ",tPaySQL);
    var tPayResult = new Array;
    tPayResult     = easyExecSql(mySql.getString());  
    fm.tPay.value  = tPayResult[0][0];  
      
}

/*****************************************************************************
���ܣ���ӡ����֪ͨ��
������niuzj,2006-01-24
******************************************************************************/
function LLUWPCLMAddFeePrint()
{
	  //alert("��ӡ����֪ͨ��");
    var i = 0;
    i = LJSPayGrid.mulLineCount ; 
    if(i==0)
    {
        alert("û�пɴ�ӡ������!");
        return ;
    }	  
    
//    var tNoticeNo = LJSPayGrid.getRowColData(0,1); //֪ͨ���
    var tSel = LJSPayGrid.getSelNo()-1;
    var tNoticeNo = LJSPayGrid.getRowColData(tSel,1); //֪ͨ���
    var tPolNo = LJSPayPersonGrid.getRowColData(0,1); //������
    var tDoutyCode = LJSPayPersonGrid.getRowColData(0,2);//���α���
    var tPayPlanCode = LJSPayPersonGrid.getRowColData(0,3);//���Ѽƻ�����
    var tClmNo = fm.ClmNo.value; //�ⰸ��
    fm.NoticeNo.value=tNoticeNo;
    fm.DoutyCode.value=tDoutyCode;
    fm.PayPlayCode.value=tPayPlanCode;
    fm.PolNo.value=tPolNo;
    //alert(tNoticeNo+":"+tPolNo)
    if (tNoticeNo=="" || tClmNo=="")
    {
        alert("û�пɴ�ӡ������!");
        return;
    }
    fm.action="LLUWPCLMAddFeeSave.jsp";
	fm.target = "../f1print";
	fm.submit();
}
