
var showInfo; 
var turnPage = new turnPageClass();
var moveFlag=false;
var mySql = new SqlClass();
//��ȡ�����˻��ܽ��
function getTotalAcc()
{
	var Totalacc=0.00;
	//var TotalaccSql="select round(nvl(sum(money),0),2)	from lcinsureacctrace a where grpcontno = '"+document.all('GrpContNo').value+"'	 and exists (select 1 from lccont where grpcontno = '"+document.all('GrpContNo').value+"' and poltype = '2' and contno = a.contno)";
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimEdorTypeCAInputSql");
	mySql.setSqlId("LLGrpClaimEdorTypeCASql1");
	mySql.addSubPara(document.all('GrpContNo').value ); 
	mySql.addSubPara(document.all('GrpContNo').value ); 
	Totalacc=easyExecSql(mySql.getString());
   if(Totalacc=="" || Totalacc==null)
   Totalacc=0.00;
  document.all('AccTotal').value =	Totalacc;
}

//��ȡ�ŵ���ÿ�������ĸ����˻����
function getAcc()
{
	/*var accSql = "select customerno,(select name from ldperson where customerno = a.customerno),(select birthday from ldperson where customerno = a.customerno),
						 + "(select sex from ldperson where customerno = a.customerno),(select round(nvl(sum(realpay), 0), 2) from llclaimpolicy where  "
						 + "grpcontno='"+document.all('GrpContNo').value+"' and insuredno = a.customerno),'',(select round(nvl(sum(money), 0), 2) from lcinsureacctrace where polno in  "
             + "(select polno from lcpol b where grpcontno = '"+document.all('GrpContNo').value+"' and insuredno = a.customerno and exists(select 1 from lccont  "
						 + "where contno = b.contno and (poltype <> '2' or poltype is null)))),substr(sysdate,1,10)	 "
						 + "from llcase a where caseno = '"+document.all('GrpContNo').value+"'order by customerno";*/
						 
						 
	/*var accSql = " select a.insuredno,(select name from ldperson where customerno = a.insuredno),"
						 + "(select birthday from ldperson where customerno = a.insuredno),  (select sex from ldperson where customerno = a.insuredno), "
       			 + "(select round(nvl(sum(realpay), 0), 2) from llclaimpolicy where grpcontno = '"+document.all('GrpContNo').value+"' and insuredno = c.customerno), "
       			 + " '',(select round(nvl(sum(money), 0), 2) from lcinsureacctrace where contno =a.contno),substr(sysdate, 1, 10),a.ContNo , "
       			 + "(select payplanname from lmriskaccpay where riskcode=a.riskcode and payplancode=a.PayPlanCode and insuaccno=a.insuaccno),a.PayPlanCode,a.riskcode "
 					   + " from LCInsureAccClass a, lccont b,llcase c  "
 						 + " where a.GrpContNo = '"+document.all('GrpContNo').value+"' and a.contno = b.contno  and c.caseno = '"+document.all('RptNo').value+"'  and c.customerno=a.insuredno and (poltype <> '2' or poltype is null) "
 						 + " and exists (select 1 from LMRiskInsuAcc where acctype='003' and insuaccno=a.insuaccno) order by a.InsuredNo" ;	*/		
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimEdorTypeCAInputSql");
	mySql.setSqlId("LLGrpClaimEdorTypeCASql2");
	mySql.addSubPara(document.all('GrpContNo').value ); 
	mySql.addSubPara(document.all('GrpContNo').value );   
	mySql.addSubPara(document.all('RptNo').value );   
  turnPage.queryModal(mySql.getString(), LPAccMoveGrid); 
}

//�����ʽ�ת��
function MoveRecord()
{
	var MoveGridCount=LPAccMoveGrid.mulLineCount;
	var moveTotalMoney=0;

	var chkFlag=false;
	for(i=0;i<MoveGridCount;i++)
	{
		if(LPAccMoveGrid.getChkNo(i))
		{
			chkFlag=true;
			

    	//re=/^([1-9]\d*|0|)\.\d{2}$/
    	re=/^\d+(\.\d{1,2})?$/
			if(!re.test(LPAccMoveGrid.getRowColData(i,6)))
			{
    		alert("ת�ƽ����¼����λС����������");
    		return false;
			}
    	//moveTotalMoney=moveTotalMoney+ LPAccMoveGrid.getRowColData(i,6);
    	moveTotalMoney=Arithmetic(moveTotalMoney,'+',LPAccMoveGrid.getRowColData(i,6),2);
	  }
	}
	if (!chkFlag)
	{
	  alert("��ѡ��Ҫת����ʻ�");
	  return false;		
	}


	if(Arithmetic(fm.AccTotal.value,'-',moveTotalMoney,2)<0)
	{
	  alert("ת�ƽ��������˻����ý����ʵ��")
	  return false;
	}
	lockScreen('lkscreen'); 	         
	var showStr = "���ڱ�����Ϣ�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//	showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	document.all("fmtransact").value = "INSERT";
	moveFlag=true;//��־�������ʽ�ת�ƵĲ���
	fm.Move.disabled=true;
	fm.submit();	

}






function returnParent() {   
	top.close();
}

function afterSubmit(FlagStr, content)
{
	  showInfo.close();
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//	  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
	  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	  var iWidth=550;      //�������ڵĿ��; 
	  var iHeight=250;     //�������ڵĸ߶�; 
	  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	  showInfo.focus();

	  unlockScreen('lkscreen');
}
