//�������ƣ�UWTempFeeQry.js
//�����ܣ���������Լɨ�������¼��
//�������ڣ�2005-5-22 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����

var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var showInfo;
var k = 0;

/*********************************************************************
 *  ��ѯ���Ѽ�¼
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function easyQueryClick(contno)
{
	
		var sqlid1="UWTempFeeQrySQL1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.UWTempFeeQrySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara('1');//ָ������Ĳ���
		//mySql1.addSubPara(k);//ָ������Ĳ���
		mySql1.addSubPara(contno);//ָ������Ĳ���
	    var strSQL =mySql1.getString();	
	
//	var strSQL = "select tempfeeno,TempFeeType,RiskCode,PayMoney,EnterAccDate,ManageCom from LJTempFee where '"+k+"'='"+k+"'"
//	 							+" and ljtempfee.otherno='"+contno+"'";
	 							
	turnPage.queryModal(strSQL, TempToGrid); 					
	
	
		var sqlid2="UWTempFeeQrySQL2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.UWTempFeeQrySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara('1');//ָ������Ĳ���
		//mySql2.addSubPara(k);//ָ������Ĳ���
		mySql2.addSubPara(contno);//ָ������Ĳ���
	    var strSQL1 =mySql2.getString();	
	
//	var strSQL1 = "select tempfeeno,PayMode,PayMoney,EnterAccDate,ChequeNo,PayDate,BankCode,BankAccNo,AccName,InBankAccNo from LJTempFeeClass where '"+k+"'='"+k+"'"		
//	 							+" and tempfeeno in (select tempfeeno from ljtempfee where otherno =  '"+contno+"')"
//	 							;
	 							
	turnPage1.queryModal(strSQL1, TempClassToGrid); 		 							
}

/*********************************************************************
 *  ��ѯ���Ѽ�¼
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function QryTempFee()
{
    var tContType = fm.ContType.value;
    var strSQL = "";
    var contno = "";
    if (tContType == "2")
    {
        contno = document.all('PrtNo').value;
		
		var sqlid3="UWTempFeeQrySQL3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("uw.UWTempFeeQrySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(document.all('PrtNo').value);//ָ������Ĳ���
	    strSQL=mySql3.getString();	
		
//        strSQL = "select grpcontno from lcgrpcont where prtno='"+document.all('PrtNo').value+"'";
        var arrResult = easyExecSql(strSQL);
        //alert(arrResult);	
        if (arrResult == null || arrResult == "")
            return;
        var contno = arrResult[0][0];
    }
    else
    {
		
		var sqlid4="UWTempFeeQrySQL4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("uw.UWTempFeeQrySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(trim(document.all('PrtNo').value));//ָ������Ĳ���
	    strSQL=mySql4.getString();	
		
//        strSQL = "select contno from lccont where prtno='"+trim(document.all('PrtNo').value)+"'";
        var arrResult = easyExecSql(strSQL);//prompt("",strSQL);	
        if (arrResult == null || arrResult == "")
            return;
        contno = arrResult[0][0];
    }
    //alert(contno);
	
		var sqlid5="UWTempFeeQrySQL5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("uw.UWTempFeeQrySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara('1');//ָ������Ĳ���
		//mySql5.addSubPara(k);//ָ������Ĳ���
		mySql5.addSubPara(contno);//ָ������Ĳ���
	    strSQL=mySql5.getString();	
	
//    strSQL = " select tempfeeno,AgentCode,sum(PayMoney) from LJTempFee where '"+k+"'='"+k+"'"
//    			 + " and ljtempfee.otherno='" + contno + "'"
//     //+ " and othernotype = '4'"
//    			 +" group by tempfeeno,agentcode";//prompt("",strSQL);
    
    var brr = easyExecSql(strSQL);					
	
		var sqlid6="UWTempFeeQrySQL6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("uw.UWTempFeeQrySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara('1');//ָ������Ĳ���
		//mySql6.addSubPara(k);//ָ������Ĳ���
		mySql6.addSubPara(contno);//ָ������Ĳ���
	    var strSQL1=mySql6.getString();	
	
//    var strSQL1= " select sum(PayMoney) from LJTempFee Where '"+k+"'='"+k+"'"
//     				 + " and ljtempfee.otherno='" + contno + "'"
//             + " group by otherno";//prompt("",strSQL1);
    var brr1=easyExecSql(strSQL1);
    
    if(brr&&brr1)
    {
    	//document.all('TempFeeNo').value = brr[0][0];
    	document.all('AgentCode').value = brr[0][1];
    	document.all('PayMoney').value = brr1[0][0];
    
    	easyQueryClick(contno);
    }
    else
    {
    	alert("�ñ���δ¼�뽻����Ϣ��");
    }
}

function QryCustomer()
{
	
	var strSQL = "";
	var tContType = fm.ContType.value;
    var strSQL = "";
    if (tContType == "2")
    {
		
		var sqlid7="UWTempFeeQrySQL7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("uw.UWTempFeeQrySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(CustomerNo);//ָ������Ĳ���
	    strSQL=mySql7.getString();	
		
//        strSQL = "select grpname from ldg/rp where CustomerNo = '"+CustomerNo+"'";
    }
    else
    {
		
		var sqlid8="UWTempFeeQrySQL8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("uw.UWTempFeeQrySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
		mySql8.addSubPara(CustomerNo);//ָ������Ĳ���
	    strSQL=mySql8.getString();	
		
//        strSQL = "select Name from ldperson where CustomerNo = '"+CustomerNo+"'";
    }
	var crr = easyExecSql(strSQL); 
	
	if(crr != null)
	{
        document.all('CustomerName').value = crr[0][0]; 
	}
	
}   