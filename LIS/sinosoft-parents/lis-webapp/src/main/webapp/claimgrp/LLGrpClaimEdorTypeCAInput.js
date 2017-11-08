
var showInfo; 
var turnPage = new turnPageClass();
var moveFlag=false;
var mySql = new SqlClass();
//获取公共账户总金额
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

//获取团单下每个个单的个人账户金额
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

//进行资金转移
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
    		alert("转移金额请录入两位小数的正数！");
    		return false;
			}
    	//moveTotalMoney=moveTotalMoney+ LPAccMoveGrid.getRowColData(i,6);
    	moveTotalMoney=Arithmetic(moveTotalMoney,'+',LPAccMoveGrid.getRowColData(i,6),2);
	  }
	}
	if (!chkFlag)
	{
	  alert("请选择要转入的帐户");
	  return false;		
	}


	if(Arithmetic(fm.AccTotal.value,'-',moveTotalMoney,2)<0)
	{
	  alert("转移金额超出公共账户可用金额，请核实！")
	  return false;
	}
	lockScreen('lkscreen'); 	         
	var showStr = "正在保存信息，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//	showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	document.all("fmtransact").value = "INSERT";
	moveFlag=true;//标志进行了资金转移的操作
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
	  var name='提示';   //网页名称，可为空; 
	  var iWidth=550;      //弹出窗口的宽度; 
	  var iHeight=250;     //弹出窗口的高度; 
	  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	  showInfo.focus();

	  unlockScreen('lkscreen');
}
