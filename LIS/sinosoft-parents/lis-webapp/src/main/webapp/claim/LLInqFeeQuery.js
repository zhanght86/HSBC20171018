var turnPage = new turnPageClass();
var mySql = new SqlClass();
//��ʼ�����ñ��LLInqFeeGrid��ѯ
function  LLInqFeeQuery()

{//Modify by zhaorx 2006-03-13
   /*  var strSQL = "select ClmNo, InqNo, InqDept,FeeType, FeeDate,FeeSum,Payee,PayeeType,Remark,(select lduser.username from lduser where lduser.usercode = Llinqfee.operator) from Llinqfee where 1=1 "
                 + getWherePart('ClmNo','ClmNo')
                 + getWherePart('InqNo','InqNo')
                 + " order by ClmNo";  */
     mySql = new SqlClass();
	mySql.setResourceName("claim.LLInqFeeQueryInputSql");
	mySql.setSqlId("LLInqFeeQuerySql1");
	mySql.addSubPara(fm.ClmNo.value );  
	mySql.addSubPara(fm.InqNo.value );               
     turnPage.queryModal(mySql.getString(), LLInqFeeGrid);
}
//ѡ��LLInqFeeGrid��Ӧ�¼�
function LLInqFeeGridClick()
{
	initInpBox(); 
    var i = LLInqFeeGrid.getSelNo();
    if (i != '0')
    {  
    	i = i - 1;
	   fm.ClmNo1.value = LLInqFeeGrid.getRowColData(i,1);            
	   fm.InqNo1.value = LLInqFeeGrid.getRowColData(i,2);   
	   fm.InqDept.value = LLInqFeeGrid.getRowColData(i,3);   
	   fm.FeeType.value = LLInqFeeGrid.getRowColData(i,4);   
	   fm.FeeDate.value = LLInqFeeGrid.getRowColData(i,5);   
	   fm.FeeSum.value = LLInqFeeGrid.getRowColData(i,6);   
	   fm.Payee.value = LLInqFeeGrid.getRowColData(i,7);   
	   fm.PayeeType.value = LLInqFeeGrid.getRowColData(i,8);   
	   fm.Remark.value = LLInqFeeGrid.getRowColData(i,9);   
	   showOneCodeName('stati','InqDept','InqDeptName');   
       showOneCodeName('llinqfeetype','FeeType','FeeTypeName');   
       showOneCodeName('llpaymode','PayeeType','PayeeTypeName');              
    }        
}