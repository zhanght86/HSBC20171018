//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var arrDataSet;
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var turnPage0 = new turnPageClass();//grppolsingleinfo
var turnPage1 = new turnPageClass();//grppolinfo
var turnPage2 = new turnPageClass();//grppolsingleacc
var turnPage3 = new turnPageClass();//GrpLoanInfo
var turnPage5 = new turnPageClass();//GrpEdorInfo
var turnPage6 = new turnPageClass();//GrpEdorUserInfo
//�ύ�����水ť��Ӧ����
function PrintGrpSingleAcc()
{
			var tGrpContNo = fm.GSAGrpContNo.value;
			if(trim(tGrpContNo)==null ||trim(tGrpContNo)==""||trim(tGrpContNo)=="00000000000000000000")
			{
				alert("��¼�����屣����!");
				return;
			}
			var tContNo = fm.GSAContNo.value;
			if(trim(tContNo)==null ||trim(tContNo)=="")
			{
				alert("��¼����˱�����!");
				return;
			}
      fm.target = "f1jprint";
      fm.action="./BQXReportPrint.jsp";
      fm.submit();
}

function PrintGrpLoanInfo()
{

      fm.target = "f1jprint";
      fm.action="./BQXReportPrint.jsp";
      fm.submit();
}


function PrintGrpSingleInfo()
{
		var tGrpContNo = fm.GSGrpContNo.value;
	if(trim(tGrpContNo)==null ||trim(tGrpContNo)==""||trim(tGrpContNo)=="00000000000000000000")
	{
		alert("��¼�����屣����!");
		return;
	}
       fm.target = "f1jprint";
      fm.action="./BQXReportPrint.jsp";
      fm.submit();
}

function PrintGrpInfo()
{
	var tStartDate = fm.GIStartCValidate.value;
	var tEndDate = fm.GIEndCValidate.value;
	if(trim(tStartDate)==null || trim(tStartDate)==""||trim(tEndDate)==null || trim(tEndDate)=="")
	{
	 		alert("ͳ������û��¼���¼�벻����!");
	 	return;
	}
      fm.target = "f1jprint";
      fm.action="./BQXReportPrint.jsp";
      fm.submit();
}


function PrintGrpEdorInfo()
{
	var tStartDate = fm.GEdorConfStartDate.value;
	var tEndDate = fm.GEdorConfEndDate.value;
	if(trim(tStartDate)==null || trim(tStartDate)==""||trim(tEndDate)==null || trim(tEndDate)=="")
	{
	 		alert("ͳ������û��¼���¼�벻����!");
	 	return;
	}
      fm.target = "f1jprint";
      fm.action="./BQXReportPrint.jsp";
      fm.submit();
}

function PrintGrpEdorUserInfo()
{
      fm.target = "f1jprint";
      fm.action="./BQXReportPrint.jsp";
      fm.submit();
}

function ReportNameSelect()
{
	divGrpInfo.style.display = 'none';	
	divGrpSingleInfo.style.display = 'none';	
	divGrpSingleAcc.style.display = 'none';
	divGrpLoanInfo.style.display = 'none';
	divGrpEdorInfo.style.display = 'none';
	divGrpEdorUser.style.display = 'none';
	//alert(fm.ReportName.value);
	var ReportType = fm.ReportName.value;
	eval("div"+ReportType+ ".style.display = ''");
}

function QueryGrpSingleAcc()
{
	initGrpSingleAccGrid();
	var tGrpContNo = fm.GSAGrpContNo.value;
	if(trim(tGrpContNo)==null ||trim(tGrpContNo)==""||trim(tGrpContNo)=="00000000000000000000")
	{
		alert("��¼�����屣����!");
		return;
	}
	var tContNo = fm.GSAContNo.value;
	if(trim(tContNo)==null ||trim(tContNo)=="")
	{
		alert("��¼����˱�����!");
		return;
	}
	var tStartDate = fm.GSAStartPayDate.value;
	var tEndDate = fm.GSAEndPayDate.value;
	
	var sqlid1="DSHomeContSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("xreport.BQXReportListSql");//ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(fm.GSAGrpContNo.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.GSAContNo.value);//ָ������Ĳ���
	mySql1.addSubPara(tStartDate);
	mySql1.addSubPara(tEndDate);
	var tSql=mySql1.getString();
	
//	var tSql = " select b.contno,c.name,c.idno,c.sequenceno,a.money,a.moneytype,a.paydate, "
//           + " (select payplanname from lmriskaccpay "
//           + " where riskcode = b.riskcode and payplancode = a.PayPlanCode and insuaccno = a.insuaccno),"
//           + " a.operator from lcinsureacctrace a, lcpol b, lcinsured c "
//           + " where a.polno = b.polno and b.insuredno = c.insuredno and b.contno = c.contno "
//	     		 + getWherePart('b.GrpContNo', 'GSAGrpContNo')
//	     		 + getWherePart('b.ContNo', 'GSAContNo');
	turnPage2.queryModal(tSql, GrpSingleAccGrid); 
	var rowNum=GrpSingleAccGrid.mulLineCount;
	if(rowNum<1)
	{
		divGrpSingleTrace.style.display="none";
		alert("�޷��������ļ�¼!");
		return;
	}else{
		divGrpSingleTrace.style.display="";
	}
}

function QueryGrpSingleInfo()
{
	initGrpSinglePolGrid();
	var tGrpContNo = fm.GSGrpContNo.value;
	if(trim(tGrpContNo)==null ||trim(tGrpContNo)==""||trim(tGrpContNo)=="00000000000000000000")
	{
		alert("��¼�����屣����!");
		return;
	}
	var tStartDate = fm.GSStartCValidate.value;
	var tEndDate = fm.GSEndCValidate.value;
	
	var sqlid2="DSHomeContSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("xreport.BQXReportListSql");//ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(fm.GSAppFlag.value);//ָ������Ĳ���
	mySql2.addSubPara(tGrpContNo);//ָ������Ĳ���
	if(tStartDate!=null && tStartDate != "") tStartDate = trim(tStartDate);
	if(tEndDate!=null && tEndDate != "") tEndDate = trim(tEndDate);
	mySql2.addSubPara(tStartDate);
	mySql2.addSubPara(tEndDate);
	var tSql=mySql2.getString();
	
//	var tSql = "select a.contno,b.name,decode(b.sex, '0', '��', '1', 'Ů', '����'),b.birthday,"
//           + "decode(b.idtype, '0','���֤','1','����','2','����֤','3','����','4','���ڱ�','5','ѧ��֤','6','����֤','8','����','9','��֤��','����'),"
//           + "b.idno,a.cvalidate,c.riskname,a.amnt,a.prem,decode(a.appflag,'1','��Ч','4','ʧЧ','δ��Ч') "
//           + " from lcpol a, lcinsured b, lmriskapp c"
//           + " where a.contno = b.contno and a.insuredno = b.insuredno and a.riskcode = c.riskcode and a.grpcontno = '"+tGrpContNo+"'"
//	     		 + getWherePart('a.AppFlag', 'GSAppFlag');
	turnPage0.queryModal(tSql, GrpSinglePolGrid); 
	var rowNum=GrpSinglePolGrid.mulLineCount;
	if(rowNum<1)
	{
		divGrpSinglePol.style.display="none";
		alert("�޷��������ļ�¼!");
		return;
	}else{
		divGrpSinglePol.style.display="";
	}
}

function QueryGrpInfo()
{
	initGrpPolGrid();
	var tGrpContNo = fm.GIGrpContNo.value;
	var tStartDate = fm.GIStartCValidate.value;
	var tEndDate = fm.GIEndCValidate.value;

	if(trim(tStartDate)==null || trim(tStartDate)==""||trim(tEndDate)==null || trim(tEndDate)=="")
	{
	 		alert("ͳ������û��¼���¼�벻����!");
	 		return;
	}
	
	var sqlid3="DSHomeContSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName("xreport.BQXReportListSql");//ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(fm.GIGrpContNo.value);//ָ������Ĳ���
	mySql3.addSubPara(fm.GIRiskCode.value);//ָ������Ĳ���
	mySql3.addSubPara(fm.GIManageCom.value);//ָ������Ĳ���
	mySql3.addSubPara(fm.GIAppFlag.value);//ָ������Ĳ���
	mySql3.addSubPara(tStartDate);//ָ������Ĳ���
	mySql3.addSubPara(tEndDate);//ָ������Ĳ���
	var tSql=mySql3.getString();
	
//	var tSql = "select a.grpcontno,a.prtno,b.riskname,a.managecom,"
//           + "a.grpname,nvl(a.Peoples2,'0'),nvl(a.prem,'0'),nvl(a.amnt,'0'),a.cvalidate,ceil(months_between(a.payenddate,a.cvalidate))||'��',decode(a.appflag,'1','��Ч','4','ʧЧ','δ��Ч') "
//           + " from lcgrppol a,lmriskapp b"
//           + " where a.riskcode = b.riskcode and a.appflag not in ('0','2')"
//           + getWherePart('a.GrpContNo', 'GIGrpContNo')
//           + getWherePart('a.RiskCode', 'GIRiskCode')
//           + getWherePart('a.managecom','GIManageCom','like' )
//	     		 + getWherePart('a.AppFlag', 'GIAppFlag');
	 if(trim(tStartDate)!=null && trim(tStartDate)!="")
	 {
	 		//tSql += " and a.cvalidate >= '"+tStartDate+"'";
	 
	 }
	 	if(trim(tEndDate)!=null && trim(tEndDate)!="")
	 {
	 		
	 		//tSql += " and a.cvalidate <= '"+tEndDate+"'";
	 }    
	 //tSql += " order by a.cvalidate desc,a.GrpContNo";	           
	turnPage1.queryModal(tSql, GrpPolGrid); 
	var rowNum=GrpPolGrid.mulLineCount;
	if(rowNum<1)
	{
		divGrpPol.style.display="none";
		alert("�޷��������ļ�¼!");
		return;
	}else{
		divGrpPol.style.display="";
	}
}

function QueryGrpLoanInfo()
{
	initGrpLoanGrid();
	//var tGrpContNo = fm.GIGrpContNo.value;
	var tStartDate = fm.GLStartLoanDate.value;
	var tEndDate = fm.GLEndLoanDate.value;

	var sqlid4="DSHomeContSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName("xreport.BQXReportListSql");//ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(fm.GLPayOffFlag.value);//ָ������Ĳ���
	mySql4.addSubPara(fm.GLManageCom.value);//ָ������Ĳ���
	mySql4.addSubPara(tStartDate);//ָ������Ĳ���
	mySql4.addSubPara(tEndDate);//ָ������Ĳ���
	var tSql=mySql4.getString();
	
//	var tSql = " select b.grpcontno,(select name from ldcom where comcode = b.managecom),b.grpname,"
//           + " nvl((select sum(insuaccbala) from lcinsureacc where grpcontno = b.grpcontno and state not in (1, 4)),0),"
//           + " a.summoney,nvl((select sum(getmoney) from ljagetendorse where EndorsementNo = a.edorno and FeeFinaType = 'RV'),0),"
//           + " a.loandate,nvl(c.returnmoney, 0),nvl(c.returninterest, 0),nvl(c.payoffdate, ''),''"
//           + " from lcgrpcont b,loloan a left outer join loreturnloan c on a.contno = c.contno and a.edorno = c.loanno "
//           + " where 1 = 1 and a.contno = b.grpcontno " 
//           + getWherePart('a.PayOffFlag', 'GLPayOffFlag')
//           + getWherePart('b.managecom','GLManageCom','like' );

	turnPage3.queryModal(tSql, GrpLoanGrid); 
	var rowNum=GrpLoanGrid.mulLineCount;
	if(rowNum<1)
	{
		divGrpLoan.style.display="none";
		alert("�޷��������ļ�¼!");
		return;
	}else{
		divGrpLoan.style.display="";
	}
}

function QueryGrpEdorInfo()
{
	initGrpEdorGrid();
	//var tGrpContNo = fm.GIGrpContNo.value;
	var tStartDate = fm.GEdorConfStartDate.value;
	var tEndDate = fm.GEdorConfEndDate.value;

	if(trim(tStartDate)==null || trim(tStartDate)==""||trim(tEndDate)==null || trim(tEndDate)=="")
	{
	 		alert("ͳ������û��¼���¼�벻����!");
	 		return;
	}
	var sqlid5="DSHomeContSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName("xreport.BQXReportListSql");//ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(fm.GEGrpContNo.value);//ָ������Ĳ���
	mySql5.addSubPara(fm.GEEdorType.value);//ָ������Ĳ���
	mySql5.addSubPara(fm.GEManageCom.value);//ָ������Ĳ���
	mySql5.addSubPara(fm.GEdorOperator.value);//ָ������Ĳ���
	mySql5.addSubPara(tStartDate);//ָ������Ĳ���
	mySql5.addSubPara(tEndDate);//ָ������Ĳ���
	var tSql=mySql5.getString();
	
//	var tSql = "select b.edortype,b.grpcontno,nvl(a.edorconfno, b.edorno),nvl((select m.riskcode from lcgrppol m,lmriskapp n where m.grpcontno = b.grpcontno and m.riskcode = n.riskcode and n.SubRiskFlag <> 'S' and rownum = 1),"
//					 + "(select riskcode from lcgrppol where grpcontno = b.grpcontno and rownum = 1)),a.managecom,"
//           + "(select grpname from lcgrpcont where grpcontno = b.grpcontno), "
//           + " a.getmoney,(select nvl(count(distinct contno), '0') from lpedoritem where edorno = b.edorno and edortype = b.edortype),"
//           + " a.makedate,a.operator,a.confdate,'',''from lpedorapp a, lpgrpedoritem b "
//           + "  where 1=1 and a.edoracceptno = b.edoracceptno and b.edorstate = '0' "
//           + getWherePart('b.GrpContNo', 'GEGrpContNo')
//           + getWherePart('b.EdorType', 'GEEdorType')
//           + getWherePart('a.managecom','GEManageCom','like' )
//	     		 + getWherePart('a.Operator', 'GEdorOperator');
	turnPage5.queryModal(tSql, GrpEdorGrid); 
	var rowNum=GrpEdorGrid.mulLineCount;
	if(rowNum<1)
	{
		divGrpEdor.style.display="none";
		alert("�޷��������ļ�¼!");
		return;
	}else{
		divGrpEdor.style.display="";
	}
}


function QueryGrpEdorUserInfo()
{
	initGrpEdorUserGrid();
	//var tGrpContNo = fm.GIGrpContNo.value;
	var tStartDate = fm.GEdorUserStartDate.value;
	var tEndDate = fm.GEdorUserEndDate.value;
	
	var sqlid6="DSHomeContSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName("xreport.BQXReportListSql");//ָ��ʹ�õ�properties�ļ���
	mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
	mySql6.addSubPara(fm.GEdorPopedom.value);//ָ������Ĳ���
	mySql6.addSubPara(fm.GEdorUserCode.value);//ָ������Ĳ���
	mySql6.addSubPara(fm.GEUserState.value);//ָ������Ĳ���
	mySql6.addSubPara(fm.GEdorUserName.value);//ָ������Ĳ���
	mySql6.addSubPara(fm.GEUManageCom.value);//ָ������Ĳ���
	mySql6.addSubPara(tStartDate);//ָ������Ĳ���
	mySql6.addSubPara(tEndDate);//ָ������Ĳ���
	var tSql=mySql6.getString();
//	
//	var tSql = "select a.usercode, a.username,"
//					 + " (select shortname from ldcom where comcode = a.comcode),"
//           + " b.edorpopedom,decode(a.userstate, '0', '��Ч', '1', 'ʧЧ', 'δ֪'),"
//           + " '',nvl(b.validstartdate,''),nvl(a.validenddate,'')"
//           + " from lduser a, ldedoruser b"
//           + "  where a.usercode = b.usercode and b.usertype = '2' "
//           + getWherePart('b.edorpopedom', 'GEdorPopedom')
//           + getWherePart('a.usercode', 'GEdorUserCode','like')
//           + getWherePart('a.userstate', 'GEUserState')
//           + getWherePart('a.username', 'GEdorUserName','like')
//           + getWherePart('a.comcode','GEUManageCom','like' );
	turnPage6.queryModal(tSql, GrpEdorUserGrid); 
	var rowNum=GrpEdorUserGrid.mulLineCount;
	if(rowNum<1)
	{
		divGrpEdorUserInfo.style.display="none";
		alert("�޷��������ļ�¼!");
		return;
	}else{
		divGrpEdorUserInfo.style.display="";
	}
}

function getcodedata2()
{
	var sqlid7="DSHomeContSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName("xreport.BQXReportListSql");//ָ��ʹ�õ�properties�ļ���
	mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
	mySql7.addSubPara();//ָ������Ĳ���
	var sql=mySql7.getString();
	
//	var sql="select RiskCode, RiskName from LMRiskApp where riskprop in ('G','D') order by riskcode";
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
	//prompt("��ѯ���ִ���:",sql);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    //alert(tCodeData);
    fm.GIRiskCode.CodeData=tCodeData;
		//fm.GERiskCode.CodeData=tCodeData;
}

function getEdorInfo()
{
	var sqlid8="DSHomeContSql8";
	var mySql8=new SqlClass();
	mySql8.setResourceName("xreport.BQXReportListSql");//ָ��ʹ�õ�properties�ļ���
	mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
	
	var sql=mySql8.getString();
	
//	var sql = "select  EdorCode, EdorName "
// 												+ " from lmedoritem"
// 												+ "	where appobj = 'G'"
  var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
	if (turnPage.strQueryResult != "")
  {
  	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  	m = turnPage.arrDataCacheSet.length;
  	for (i = 0; i < m; i++)
  	{
  		j = i + 1;
  		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
  	}
  }  
  fm.GEEdorType.CodeData = tCodeData;	
}
