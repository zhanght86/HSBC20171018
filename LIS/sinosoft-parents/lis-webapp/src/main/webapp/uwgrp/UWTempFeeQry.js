//�������ƣ�UWTempFeeQry.js
//�����ܣ���������Լɨ�������¼��
//�������ڣ�2005-5-22 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����

var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var showInfo;
var k = 0;
var sqlresourcename = "uwgrp.UWTempFeeQrySql";

/*********************************************************************
 *  ��ѯ���Ѽ�¼
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function easyQueryClick()
{/*
	
	var strSQL = "select tempfeeno,TempFeeType,RiskCode,PayMoney,EnterAccDate,ManageCom from LJTempFee where '"+k+"'='"+k+"' and operstate='0' "
	 							+getWherePart('ljtempfee.otherno','PrtNo')
	 							;
	 	*/		
	    var sqlid1="UWTempFeeQrySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(k);
		mySql1.addSubPara(k);
		mySql1.addSubPara(fm.PrtNo.value);
	 			
	 							
	turnPage.queryModal(mySql1.getString(), TempToGrid); 					
	
	/*
	var strSQL1 = "select tempfeeno,PayMode,PayMoney,EnterAccDate,ChequeNo,PayDate,BankCode,BankAccNo,AccName,InBankAccNo from LJTempFeeClass where '"+k+"'='"+k+"' "		
	 							+" and tempfeeno in (select tempfeeno from ljtempfee where otherno = '"+fm.PrtNo.value+"' and operstate='0' )"
	 							+" and operstate='0'";
	 */
	 
	 var sqlid2="UWTempFeeQrySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(k);
		mySql2.addSubPara(k);
		mySql2.addSubPara(fm.PrtNo.value);
	 							
	turnPage1.queryModal(mySql2.getString(), TempClassToGrid); 		 							
}

/*********************************************************************
 *  ��ѯ���Ѽ�¼
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function QryTempFee()
{
	  var strSQL = "select tempfeeno,AgentCode,sum(PayMoney) from LJTempFee where '"+k+"'='"+k+"' and operstate='0' "
	 							+getWherePart('ljtempfee.otherno','PrtNo')
	 							//+" and othernotype = '4'"
	 							+" group by tempfeeno,agentcode"
	 							;

	  var brr = easyExecSql(strSQL);					
	  var strSQL1="select sum(PayMoney) from LJTempFee Where '"+k+"'='"+k+"' and operstate='0' "
	              +getWherePart('ljtempfee.otherno','PrtNo')
	              +" group by otherno";
	  var brr1=easyExecSql(strSQL1);
	 

	  if(brr&&brr1)
	  {
	 		//fm.all('TempFeeNo').value = brr[0][0];
	 		fm.all('AgentCode').value = brr[0][1];
	 		fm.all('PayMoney').value = brr1[0][0];
	 	
	 		easyQueryClick();
	  }
		else
		{
			alert("�ñ���δ¼�뽻����Ϣ��");
		}
}

function QryCustomer()
{
	
	var strSQL = "select Name from ldperson where CustomerNo = '"+CustomerNo+"'";
	
	var crr = easyExecSql(strSQL); 
	
	if(crr)
	{
	 
	 fm.all('CustomerName').value = crr[0][0]; 
	}
	else
		{
			
			strSQL = "select grpname from ldgrp where CustomerNo = '"+CustomerNo+"'";
		  crr = easyExecSql(strSQL);
			fm.all('CustomerName').value = crr[0][0];
		}
	
}   