
var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//��ѯ��ʼ��ҳ���ϲ��ı����˻������
function LCInsureAccClassGridQuery()
{
    //���ȸ����ⰸ�Ŵӡ��⸶��ϸ����llclaimdetail���в�ѯ�����ⰸ�����е� �����ţ�PolNo��
    //Ȼ���ѯ�������˻��������LCInsureAccClass���������������ֺ��롱��PolNo�����в����ʻ���Ϣ
    //###################################################LCInsureAccClass����ֶ�
    //GrpContNo,GrpPolNo,ContNo,ManageCom
    // PolNo ,InsuAccNo ,PayPlanCode ,OtherNo ,OtherType ,AccAscription 
	//,RiskCode ,InsuredNo ,AppntNo ,AccType ,AccComputeFlag ,AccFoundDate 
	//,AccFoundTime ,BalaDate ,BalaTime ,SumPay ,SumPaym ,LastAccBala ,LastUnitCount 
	//,LastUnitPrice ,InsuAccBala ,UnitCount ,InsuAccGetMoney ,FrozenMoney ,State 
	//#######################################################
	/*var strSql = " select PolNo ,InsuAccNo ,PayPlanCode ,OtherNo ,OtherType ,AccAscription,InsuredNo,AccType ,AccComputeFlag ,AccFoundDate from LCInsureAccClass where 1=1"
            + " and polno in (select polno from llclaimdetail where clmno='" +document.all('ClmNo').value + "')";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LCInsureAccontInputSql");
	mySql.setSqlId("LCInsureAccontSql1");
	mySql.addSubPara(document.all('ClmNo').value ); 
  //    alert(strSql);        
    arr = easyExecSql( mySql.getString() );
    if (arr==null ||arr=="")
    {
		alert("�ڸ��ⰸ��δ�ҵ��κ��ʻ���Ϣ��");    
		return;    	
    }
    else
	{
        displayMultiline(arr,LCInsureAccClassGrid);		
	}
}
//LCInsureAccClassGrid��Ӧ�ĺ�����
function LCInsureAccClassGridClick()  //
{
    var i = LCInsureAccClassGrid.getSelNo() - 1;	
	var tPolNo=LCInsureAccClassGrid.getRowColData(i,1)
	var tInsuAccNo=LCInsureAccClassGrid.getRowColData(i,2)
	/*var strSQL="select polno,insuaccno,serialno,moneytype,money,unitcount,paydate,state from lcinsureacctrace  where 1=1 "
         + "and polno='"+ tPolNo +"'"
         +" and insuaccno='"+ tInsuAccNo +"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LCInsureAccontInputSql");
	mySql.setSqlId("LCInsureAccontSql2");
	mySql.addSubPara(tPolNo); 
	mySql.addSubPara(tInsuAccNo); 
    //alert(strSQL);        
    arr = easyExecSql( mySql.getString() );
    if (arr==null ||arr=="")
    {
		alert("δ�ҵ��κθ��ʻ��Ǽ�������Ϣ��");    
		return;    	
    }
    else
	{
        displayMultiline(arr,LCInsureAccTraceGrid);		
	}         
}
//��ѯ��ʼ��ҳ���в��ı����ʻ���Ǽ�������
function LCInsureAccTraceGridQuery()
{

}
//��ʾ��������span��
function showDiv(spanID,divID)
{
    if (spanID != null)
    {
        document.all(divID).style.display="";
    }
    else
    {
        document.all(divID).style.display="none";
    }
}

