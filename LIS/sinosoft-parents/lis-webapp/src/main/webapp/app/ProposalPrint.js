//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var arrDataSet
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var PolNo;
var manageCom;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "app.ProposalPrintSql";


//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  // showSubmitFrame(mDebug);
  initContGrid();
  document.getElementById("fm").submit(); //�ύ
}

//�ύ�����水ť��Ӧ����
function printPol()
{
	var i = 0;
	var flag = 0;
	flag = 0;
	//�ж��Ƿ���ѡ���ӡ����
	for( i = 0; i < ContGrid.mulLineCount; i++ )
	{
		if( ContGrid.getChkNo(i) == true ) {
//			alert("ContGrid.getRowColData(i,9):"+ContGrid.getRowColData(i,9)+"@@@@@@@");
//			alert("ContGrid.getRowColData(i,8):"+ContGrid.getRowColData(i,8)+"@@@@@@@");
//			alert("ContGrid.getRowColData(i,10):"+ContGrid.getRowColData(i,10)+"@@@@@@@");
				if(ContGrid.getRowColData(i,10) == "1"){
			alert("������ʾ��������ӡVIP����������ֽ����ҳ�Ƿ�ƥ�䣡");
		     }
		        else {
			alert("������ʾ��������ӡ��ͨ����������ֽ����ҳ�Ƿ�ƥ�䣡");
		     }
			flag = 1;
			break;
		}
	}
	//���û�д�ӡ���ݣ���ʾ�û�ѡ��
	if( flag == 0 )
	{
		alert("����ѡ��һ����¼���ٴ�ӡ����");
		return false;
	}
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	//disable��ӡ��ť����ֹ�û��ظ��ύ
	document.all("printButton").disabled=true;
	document.all("printVIPButton").disabled=true;
	document.getElementById("fm").submit();
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = ContGrid.getSelNo();
	//alert("111" + tRow);
	if( tRow == 0 || tRow == null || arrDataSet == null )
	//{alert("111");
	return arrSelected;
	//}
	//alert("222");
	arrSelected = new Array();
	//������Ҫ���ص�����
	arrSelected[0] = arrDataSet[tRow-1];

	return arrSelected;
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	//���۴�ӡ�����Σ������¼����ӡ��ť
	showInfo.close();
	document.all("printButton").disabled=false;
	document.all("printVIPButton").disabled=false;
	if (FlagStr == "Fail" )
	{
		//�����ӡʧ�ܣ�չ�ִ�����Ϣ
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	else
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		easyQueryClick();		
	}
	 //document.all("divErrorMInfo").style.display = "";
	 //easyQueryErrClick();
	 
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
	try
	{
		initForm();
	}
	catch(re)
	{
		alert("��Proposal.js-->resetForm�����з����쳣:��ʼ���������!");
	}
}

//�ύǰ��У�顢����
function beforeSubmit()
{
  //��Ӳ���
}

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
	if(cDebug=="1")
	{
		parent.fraMain.rows = "0,0,50,82,*";
	}
	else
	{
		parent.fraMain.rows = "0,0,0,0,*";
	}
}

function returnParent()
{
    tPolNo = "00000120020110000050";
    top.location.href="./ProposalQueryDetail.jsp?PolNo="+tPolNo;
}



// ��ѯ��ť
function easyQueryClick()
{
	if(document.all('ManageCom').value=="86")
	{
		alert("�����������Ϊ�ܹ�˾��");
		return;
		}
		if(document.all('ManageCom').value=="" || document.all('ManageCom').value==null )
	{
		alert("�����������Ϊ��");
		return;
		}	
	// ��ʼ�����
	initContGrid();
	//�ж����Ƿ��Ǽ�ͥ�����߶�����
	
	var strManageComWhere = " and 1=1 ";
  var strPrtNoSQL = " ";
  var strContNoSQL = " ";
	if( fm.BranchGroup.value != '' )
	{
		//strManageComWhere += " AND EXISTS" + " ( SELECT AgentGroup FROM LABranchGroup WHERE AgentGroup=A.AgentGroup and BranchAttr LIKE '" + fm.BranchGroup.value + "%%') ";
		strManageComWhere = " AND EXISTS" + " ( SELECT AgentGroup FROM LABranchGroup WHERE AgentGroup=A.AgentGroup and BranchAttr LIKE '" + fm.BranchGroup.value + "%%') ";
	}
	//ֻҪ��ͬ����һ�����շ���VIP����������,�򱾺�ͬ����VIP��ͬ hb-2009-02-11
	var strRiskCodeWhere = " and EXISTS (select 1 from lcpol where contno = A.contno and polno =mainpolno and riskcode not in ('311603')  and isvipcont(prtno,payintv,payyears,prem)= '0' and riskcode in (SELECT riskcode FROM LMRiskApp where NotPrintPol = '0') )"; //
	//alert(document.all('RiskCode').value );
	if(document.all('RiskCode').value != null && document.all('RiskCode').value != "" )
	{
		strRiskCodeWhere +=  " and EXISTS (select 1 from lcpol where contno = A.contno and polno =mainpolno and riskcode= '"+document.all('RiskCode').value 
		+"' and isvipcont(prtno,payintv,payyears,prem)= '0' and riskcode in (SELECT riskcode FROM LMRiskApp where NotPrintPol = '0') )"	
	}
  
   //alert(document.all('ContNo').value);
   //alert(document.all('PrtNo').value);
   //alert(document.all('Proposalcontno').value);
   var tContTraceSQL="";
   
   //�ų���ͬ�´���δǩ�������ĺ�ͬ
   var tPrtSQL=" and not exists (select PolNo from LCPol where a.PrtNo = PrtNo and AppFlag <> '1')";
   
   var tEdorContSQL=" and 2=2 ";
   
   if((document.all('ContNo').value == null || document.all('ContNo').value == "") 
    && (document.all('PrtNo').value == null || document.all('PrtNo').value == "")
    )
   {
   	   //or exists (select 'X' from lccontprinttrace r where a.contno = a.contno and a.makedate <= sysdate and a.makedate >= substr((sysdate - 30), 0, 10)))
   	   //Ϊ�������ܣ�ֻ������ʮ��ǩ���ı����������ʮ�첹��ı�����ʮ����Ե���
   	   strContNoSQL=" and (a.signdate <= now() and a.signdate >= substr((subdate(now() , 10)), 1, 10)) ";
   
   	   tEdorContSQL= "  union  SELECT a.ContNo ContNo ,(SELECT distinct c.PrtNo from lccont  c where  c.contno=a.contno ), (SELECT distinct Proposalcontno from lccont  c where   c.contno=a.contno ),(SELECT distinct AppntName from lccont  c where   c.contno=a.contno ),(SELECT distinct signdate from lccont  c where   c.contno=a.contno ) CValiDate,(SELECT distinct signtime from lccont  c where   c.contno=a.contno ) CValiTime ,(SELECT distinct PrintCount from lccont  c where   c.contno=a.contno ),(SELECT distinct familytype from lccont  c where   c.contno=a.contno ),(SELECT distinct familycontno from lccont  c where   c.contno=a.contno ),'0' "
	                   +" FROM lpedoritem  a where 1=1  and edortype='LR' and edorstate='0' "
	                   + " and ManageCom like '" + comcode + "%%' "
	                   + getWherePart('ManageCom', 'ManageCom', 'like')
	                   + strRiskCodeWhere
	                   +" and  exists (select 'X' from lccont d where  d.contno=a.contno and d.AppFlag=1 and (d.printcount=10 or  d.printcount<=0))"
	                   +" and (a.edorappdate <= now() and a.edorappdate >= substr((subdate(now() , 10)), 1, 10)) ";            
   	}

//��һ��SQL����ʱ�������в�ѯ
   var tTempSQL= "";
//   alert("_DBO:"+_DBO +" -----------"+"_DBM:"+_DBM);
   if(_DBT==_DBO){
	    tTempSQL=" with familycont as ( select prtno,(select contno from lccont "
           +" where prtno = familyprt.prtno and rownum <= 1) contno from "   
+  " (SELECT PrtNo FROM LCCont a where grpcontno = '00000000000000000000'"
	+ " AND AppFlag = '1'  and (familycontno is  null or  familycontno='00000000000000000000') "
	+ " AND ( PrintCount <=0 OR PrintCount='10' )"
	+ getWherePart('a.contno', 'ContNo' )
	+ getWherePart( 'PrtNo' )
	+ getWherePart( 'SaleChnl' )
	+ getWherePart('ManageCom', 'ManageCom', 'like')
	+ strContNoSQL
	+ " and ManageCom like '" + comcode + "%%' "
	+" union  "
+  " (SELECT PrtNo FROM LCCont A where grpcontno = '00000000000000000000'"
	+ " AND AppFlag = '1' and FamilyType in ('1','2') and (familycontno is not null and  familycontno!='00000000000000000000')  "
	+ " AND ( PrintCount <= 0 OR PrintCount='10')"
	+ getWherePart('a.familycontno', 'ContNo' )  //�Ƿ��书����ǿ��������֧�����Ᵽ���ı����Ž��в�ѯ 
	+ getWherePart( 'PrtNo' )
	+ getWherePart( 'SaleChnl' )
//	+ getWherePart('ManageCom', 'ManageCom', 'like')
+ strContNoSQL
	+ " and ManageCom like '" + comcode + "%%')) familyprt )"	;		
//	    alert(123);
   }else if(_DBT==_DBM){
	   tTempSQL="  ( select prtno,(select contno from lccont "
           +" where prtno = familyprt.prtno limit 1) contno from "   
+  " (SELECT PrtNo FROM LCCont a where grpcontno = '00000000000000000000'"
	+ " AND AppFlag = '1'  and (familycontno is  null or  familycontno='00000000000000000000') "
	+ " AND ( PrintCount <=0 OR PrintCount='10' )"
	+ getWherePart('a.contno', 'ContNo' )
	+ getWherePart( 'PrtNo' )
	+ getWherePart( 'SaleChnl' )
//	+ getWherePart('ManageCom', 'ManageCom', 'like')
	+ strContNoSQL
	+ " and ManageCom like '" + comcode + "%%' "
	+" union  "
+  " (SELECT PrtNo FROM LCCont A where grpcontno = '00000000000000000000'"
	+ " AND AppFlag = '1' and FamilyType in ('1','2') and (familycontno is not null and  familycontno!='00000000000000000000')  "
	+ " AND ( PrintCount <= 0 OR PrintCount='10')"
	+ getWherePart('a.familycontno', 'ContNo' )  //�Ƿ��书����ǿ��������֧�����Ᵽ���ı����Ž��в�ѯ 
	+ getWherePart( 'PrtNo' )
	+ getWherePart( 'SaleChnl' )
	+ getWherePart('ManageCom', 'ManageCom', 'like')
+ strContNoSQL
	+ " and ManageCom like '" + comcode + "%%')) familyprt )"	;		
   }
  

	var strSQL = "";	
/*	strSQL =tTempSQL+ " SELECT a.ContNo ContNo,a.PrtNo, a.Proposalcontno,AppntName,signdate,signtime,PrintCount,familytype,familycontno,'0' "
	  +" FROM LCCont A,familycont b where a.contno = b.contno "
    +" and a.prtno = b.prtno " //ֻ����Ҫ��ӡ�ı���
		+ strManageComWhere
		+ strRiskCodeWhere
		//+ tContTraceSQL ȥ��
	  + tEdorContSQL
		+ " order by ContNo asc,signdate asc ";
*/
///////////////ƴ��sql
var sqlid1="ProposalPrintSql1";
var mySql1=new SqlClass();
mySql1.setResourceName(sqlresourcename);
mySql1.setSqlId(sqlid1);
mySql1.addSubPara(tTempSQL);
mySql1.addSubPara(strManageComWhere);
mySql1.addSubPara(strRiskCodeWhere);
mySql1.addSubPara(tEdorContSQL);
strSQL = mySql1.getString() ;
//console.log(tEdorContSQL);
/////////////////////
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult)
	{
		alert("δ��ѯ���������������ݣ�");
		return false;
	}

	//���ò�ѯ��ʼλ��
	turnPage.pageIndex = 0;
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	turnPage.pageLineNum = 30 ;
	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//���ó�ʼ������MULTILINE����
	turnPage.pageDisplayGrid = ContGrid;
	//����SQL���
	turnPage.strQuerySql = strSQL ;
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}
//��ѯVIP��ͬ
function QueryVIPClick()
{

	if(document.all('ManageCom').value=="86")
	{
		alert("�����������Ϊ�ܹ�˾��");
		return;
		}
	// ��ʼ�����
	initContGrid();
	var strManageComWhere = " and 3=3 ";
	var tContTraceSQL="";
	var strContNoSQL = " and 4=4 ";
	var tEdorContSQL="";
	if( fm.BranchGroup.value != '' )
	{
		strManageComWhere += " AND EXISTS" + " ( SELECT AgentGroup FROM LABranchGroup WHERE AgentGroup=A.AgentGroup and BranchAttr LIKE '" + fm.BranchGroup.value + "%%') ";
	}
	//ֻҪ��ͬ����һ�����շ���VIP����������,�򱾺�ͬ����VIP��ͬ hb-2009-02-11
	//�ų��������� 311603 �����ŵ���ӡ�����ղ���ӡ��add by pengst on 2009-06-11
	var strRiskCodeWhere = " and EXISTS (select 1 from lcpol where contno = A.contno and polno =mainpolno and riskcode not in ('311603')  and isvipcont(prtno,payintv,payyears,prem)= '1' and riskcode in (SELECT riskcode FROM LMRiskApp where NotPrintPol = '0'))"; //ֻ����Ҫ��ӡ�ı���"   
	//alert(document.all('RiskCode').value );
	if(document.all('RiskCode').value != null && document.all('RiskCode').value != "" )
	{
		strRiskCodeWhere +=  " and EXISTS (select 1 from lcpol where contno = A.contno and polno =mainpolno and riskcode= '"+document.all('RiskCode').value 
		+"' and isvipcont(prtno,payintv,payyears,prem)= '1' and riskcode in (SELECT riskcode FROM LMRiskApp where NotPrintPol = '0') )"
	}
	
	   if((document.all('ContNo').value == null || document.all('ContNo').value == "") 
    && (document.all('PrtNo').value == null || document.all('PrtNo').value == ""))
   {
   	   // or exists (select 'X' from lccontprinttrace r where a.contno = a.contno and a.makedate <= sysdate and a.makedate >= substr((sysdate - 30), 0, 10)))";
   	   //Ϊ�������ܣ�ֻ������ʮ��ǩ���ı����������ʮ�첹��ı�����ʮ����Ե���
   	   strContNoSQL=" and (a.signdate <= now() and a.signdate >= substr((subdate(now() , 10)), 1, 10))";
       tEdorContSQL= "  union  SELECT a.ContNo ContNo ,(SELECT distinct c.PrtNo from lccont  c where  c.contno=a.contno ), (SELECT distinct Proposalcontno from lccont  c where   c.contno=a.contno ),(SELECT distinct AppntName from lccont  c where   c.contno=a.contno ),(SELECT distinct CValiDate from lccont  c where   c.contno=a.contno ) CValiDate,(SELECT distinct signtime from lccont  c where   c.contno=a.contno ) CValiTime ,(SELECT distinct PrintCount from lccont  c where   c.contno=a.contno ),(SELECT distinct familytype from lccont  c where   c.contno=a.contno ),(SELECT distinct familycontno from lccont  c where   c.contno=a.contno ),'1' "
	                   +" FROM lpedoritem  a where 1=1 and edortype='LR' and edorstate='0' "
	                   + " and ManageCom like '" + comcode + "%%' "
	                   + getWherePart('ManageCom', 'ManageCom', 'like')
	                   + strRiskCodeWhere
	                   +" and  exists (select 'X' from lccont d where  d.contno=a.contno and d.AppFlag='1' and (d.printcount=10 or  d.printcount<=0))"
	                   +" and (a.edorappdate <= now() and a.edorappdate >= substr((subdate(now() , 10)), 1, 10)) ";   
   
   	}

//��һ��SQL����ʱ�������в�ѯ
	   var tTempSQL="";
	   if(_DBT==_DBO){
		   tTempSQL=" with familycont as ( select prtno,(select contno from lccont "
               +" where prtno = familyprt.prtno and rownum <= 1) contno from "   
  +  " (SELECT PrtNo FROM LCCont a where grpcontno = '00000000000000000000'"
		+ " AND AppFlag = '1'  and (familycontno is  null or  familycontno='00000000000000000000') "
		+ " AND ( PrintCount <=0 OR PrintCount='10' )"
		+ getWherePart('a.contno', 'ContNo' )
		+ getWherePart( 'PrtNo' )
		+ getWherePart( 'SaleChnl' )
//		+ getWherePart('ManageCom', 'ManageCom', 'like')
		+ strContNoSQL
		+ " and ManageCom like '" + comcode + "%%' "
		+" union  "
  +  " (SELECT PrtNo FROM LCCont A where grpcontno = '00000000000000000000'"
		+ " AND AppFlag = '1' and FamilyType in ('1','2') and (familycontno is not null and  familycontno!='00000000000000000000')  "
		+ " AND ( PrintCount <= 0 OR PrintCount='10')"
		+ getWherePart('a.familycontno', 'ContNo' )  //�Ƿ��书����ǿ��������֧�����Ᵽ���ı����Ž��в�ѯ 
		+ getWherePart( 'PrtNo' )
		+ getWherePart( 'SaleChnl' )
		//+ getWherePart('ManageCom', 'ManageCom', 'like')
    + strContNoSQL
		+ " and ManageCom like '" + comcode + "%%')) familyprt )"	;
	   }else if(_DBT==_DBM){
		   tTempSQL="( select prtno,(select contno from lccont "
               +" where prtno = familyprt.prtno limit 0,1) contno from "   
  +  " (SELECT PrtNo FROM LCCont a where grpcontno = '00000000000000000000'"
		+ " AND AppFlag = '1'  and (familycontno is  null or  familycontno='00000000000000000000') "
		+ " AND ( PrintCount <=0 OR PrintCount='10' )"
		+ getWherePart('a.contno', 'ContNo' )
		+ getWherePart( 'PrtNo' )
		+ getWherePart( 'SaleChnl' )
		//+ getWherePart('ManageCom', 'ManageCom', 'like')
		+ strContNoSQL
		+ " and ManageCom like '" + comcode + "%%' "
		+" union  "
  +  " (SELECT PrtNo FROM LCCont A where grpcontno = '00000000000000000000'"
		+ " AND AppFlag = '1' and FamilyType in ('1','2') and (familycontno is not null and  familycontno!='00000000000000000000')  "
		+ " AND ( PrintCount <= 0 OR PrintCount='10')"
		+ getWherePart('a.familycontno', 'ContNo' )  //�Ƿ��书����ǿ��������֧�����Ᵽ���ı����Ž��в�ѯ 
		+ getWherePart( 'PrtNo' )
		+ getWherePart( 'SaleChnl' )
		+ getWherePart('ManageCom', 'ManageCom', 'like')
    + strContNoSQL
		+ " and ManageCom like '" + comcode + "%%')) familyprt )"	;
	   }
  		

	var strSQL = "";	
/*	strSQL =tTempSQL+ " SELECT a.ContNo ContNo,a.PrtNo, a.Proposalcontno,AppntName,signdate,signtime,PrintCount,familytype,familycontno,'1' "
	  +" FROM LCCont A,familycont b where a.contno = b.contno " 
    +" and a.prtno = b.prtno "
    + strManageComWhere
		+ strRiskCodeWhere
		//+ tContTraceSQL ȥ��
		+tEdorContSQL //��ӱ�ȫ�������������
		+ " order by ContNo asc,signdate asc";
*/
///////////////ƴ��sql
var sqlid2="ProposalPrintSql2";
var mySql2=new SqlClass();
mySql2.setResourceName(sqlresourcename);
mySql2.setSqlId(sqlid2);
mySql2.addSubPara(tTempSQL);
mySql2.addSubPara(strManageComWhere);
mySql2.addSubPara(strRiskCodeWhere);
mySql2.addSubPara(tEdorContSQL);
strSQL = mySql2.getString() ;
/////////////////////
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult)
	{
		alert("δ��ѯ���������������ݣ�");
		return false;
	}

	//���ò�ѯ��ʼλ��
	turnPage.pageIndex = 0;
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	turnPage.pageLineNum = 30 ;
	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//���ó�ʼ������MULTILINE����
	turnPage.pageDisplayGrid = ContGrid;
	//����SQL���
	turnPage.strQuerySql = strSQL ;
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

function queryBranch()
{
	showInfo = window.open("../certify/AgentTrussQuery.html");
}

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery(arrResult)
{
	if(arrResult!=null)
	{
		fm.BranchGroup.value = arrResult[0][3];
	}
}


// ��ѯ��ť
function easyQueryErrClick()
{

	// ��ʼ�����
	initErrorGrid();
	//�ж����Ƿ��Ǽ�ͥ�����߶�����
	
  var addStr3 = " and 3=3 ";
  var addStr4 = " and 4=4 ";
  var addStr5 = " and 5=5 ";
if(fm.ErrorContNo.value != null) 
  { addStr3 = " and ContNo = '"+fm.ErrorContNo.value+"'"; }
if(fm.MakeDate.value != null && fm.MakeDate.value !="" ) 
  { addStr4 = " and MakeDate = '"+fm.MakeDate.value+ "'"; }
if(fm.ManageCom.value != null) 
  { addStr5 = " and ManageCom like '"+fm.ManageCom.value+"%'"; }
/*    
  var strSQL="select contno,Errmsg,makedate,maketime,ManageCom from LDSysErrLog where 1=1 "
  + getWherePart( 'ContNo','ErrorContNo' )
  + getWherePart( 'MakeDate' );
  + getWherePart( 'ManageCom' );
*/
var sqlid22="ProposalPrintSql22";
var mySql22=new SqlClass();
mySql22.setResourceName(sqlresourcename);
mySql22.setSqlId(sqlid22);
mySql22.addSubPara(addStr3);
mySql22.addSubPara(addStr4);
mySql22.addSubPara(addStr5);
	strSQL = mySql22.getString() ;
	turnPage.strQueryResult  = easyQueryVer3(mySql22.getString(), 1, 0, 1);

	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult)
	{
		alert("δ��ѯ���������������ݣ�");
		return ;
	}

	//���ò�ѯ��ʼλ��
	turnPage.pageIndex = 0;
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	turnPage.pageLineNum = 30 ;
	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//���ó�ʼ������MULTILINE����
	turnPage.pageDisplayGrid = ErrorGrid;
	//����SQL���
	turnPage.strQuerySql = strSQL ;
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}