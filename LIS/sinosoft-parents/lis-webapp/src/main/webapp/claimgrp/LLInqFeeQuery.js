var turnPage = new turnPageClass();
var mySql = new SqlClass();
//初始化费用表格LLInqFeeGrid查询
function  LLInqFeeQuery()
{
    /* var strSQL = "select ClmNo, InqNo, InqDept,FeeType, FeeDate,FeeSum,Payee,PayeeType,Remark from Llinqfee where 1=1 "
                 + getWherePart('ClmNo','ClmNo')
                 + getWherePart('InqNo','InqNo')
                 + " order by ClmNo";    */   
     mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLInqFeeQueryInputSql");
mySql.setSqlId("LLInqFeeQuerySql1");
mySql.addSubPara(fm.ClmNo.value );    
mySql.addSubPara(fm.InqNo.value );          
     turnPage.queryModal(mySql.getString(), LLInqFeeGrid);
}
//选中LLInqFeeGrid响应事件
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