//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var arrDataSet 
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var PolNo;
var manageCom;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "app.ProposalComPrint_IDGFSql";
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
  initPolGrid();
  document.getElementById("fm").submit(); //�ύ
}

//�ύ�����水ť��Ӧ����
function printPol()
{
  var i = 0;
  var flag = 0;
  flag = 0;
  
  for( i = 0; i < PolGrid.mulLineCount; i++ ) 
  {
    if( PolGrid.getChkNo(i) == true ) 
    {
       flag = 1;
       break;
    }
  }
	
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
  // showSubmitFrame(mDebug);
  document.all("printButton").disabled=true;
  document.getElementById("fm").submit();
}


//��ѯ�����˵ķ�ʽ
function queryAgent(comcode)
{
  if(document.all('AgentCode').value == "")	
  {  
		var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+comcode,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
  }
  
	if(document.all('AgentCode').value != "")	 
	{
	var cAgentCode = fm.AgentCode.value;  //�����˱���	
	//var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
    
var sqlid1="ProposalComPrint_IDGFSql1";
var mySql1=new SqlClass();
mySql1.setResourceName(sqlresourcename);
mySql1.setSqlId(sqlid1);
mySql1.addSubPara(cAgentCode);

    
    var arrResult = easyExecSql(mySql1.getString());
    //alert(arrResult);
    if (arrResult != null) 
    {
			alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else
    {
			
			alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
    }
	}	
}

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
//  	fm.QAgentGroup.value = arrResult[0][1];
  }
}
function queryAgent2()
{
	if(document.all('AgentCode').value != "" && document.all('AgentCode').value.length==10 )	 {
	var cAgentCode = fm.AgentCode.value; 
	//var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";
    
var sqlid2="ProposalComPrint_IDGFSql2";
var mySql2=new SqlClass();
mySql2.setResourceName(sqlresourcename);
mySql2.setSqlId(sqlid2);
mySql2.addSubPara(cAgentCode);

    
    var arrResult = easyExecSql(mySql2.getString());
       //alert(arrResult);
    if (arrResult != null) {
    
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else{
 
     alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
     }
	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = PolGrid.getSelNo();
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
  showInfo.close();
   document.all("printButton").disabled=false;
  if (FlagStr == "Fail" ) {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  } else {
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
   easyQueryClick();
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
 	else {
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
	// ��ʼ�����
	initPolGrid();
	
	 if(document.all('ManageCom').value=="" || document.all('ManageCom').value==null )
	{
		alert("�����������Ϊ��");
		return;
		}	
		
	   if(fm.ManageGrade.value==null || fm.ManageGrade.value=="" )
	  {
	  		 alert("����������Ϊ��");
	  		 return false;
	  }  
	  if(fm.ManageCom.value.length>fm.ManageGrade.value)
	  {
	  		 alert("��ѡ�Ĺ�������ͼ���ƥ�䣬������ѡ��");
	  		 return false;
	  }	
   //Ϊ�������ܣ�ֻ������ʮ��ǩ���ı����������ʮ�첹��ı�����ʮ����Ե���
   var  strContNoSQL=" and (a.signdate <= now() and a.signdate >= substr((subdate(now() , 10)), 1, 10)) ";// or exists (select 'X' from lccontprinttrace r where r.contno = a.contno and r.makedate <= sysdate and r.makedate >= substr((sysdate - 30), 0, 10)))";

	//��һ��SQL����ʱ�������в�ѯ
   var tTempSQL= "";
/**   if(_DBT==_DBO){
	    tTempSQL=" with familycont as ( select prtno,(select contno from lccont "
           +" where prtno = familyprt.prtno and rownum <= 1) contno from "   
+  " (SELECT PrtNo FROM LCCont a where grpcontno = '00000000000000000000'"
	+ " AND AppFlag = '1' and ( familycontno is  null or  familycontno='00000000000000000000')  "
	+ " AND ( PrintCount <= 0 OR PrintCount = 10 )"
	+ getWherePart('ManageCom', 'ManageCom', 'like')
	+ " and ManageCom like '" + comcode + "%%' "
	
	+ strContNoSQL
	
	+" union  "
+  " (SELECT PrtNo FROM LCCont A where grpcontno = '00000000000000000000'"
	+ " AND AppFlag = '1' and FamilyType in ('1','2')  and (familycontno is not null and  familycontno!='00000000000000000000')   "
	+ " AND ( PrintCount <= 0 OR PrintCount = 10 )"
	+ getWherePart('ManageCom', 'ManageCom', 'like')
	
	+ strContNoSQL
	
	+" ) "
	+ "  union  (SELECT (SELECT distinct c.PrtNo from lccont  c where  c.contno=a.contno )"
  +" FROM lpedoritem  a where 1=1  and edortype='LR' and edorstate='0' "
  + " and ManageCom like '" + comcode + "%%' "
  + getWherePart('ManageCom', 'ManageCom', 'like')
  +" and  exists (select 'X' from lccont d where  d.contno=a.contno and d.AppFlag='1' and (d.printcount=10 or  d.printcount<=0))"
  +" and (a.edorappdate <= now() and a.edorappdate >= substr((subdate(now() , 10)), 1, 10))) " 
	+") familyprt )"	;
	   }else if(_DBT==_DBM){
		    tTempSQL=" with familycont as ( select prtno,(select contno from lccont "
               +" where prtno = familyprt.prtno limit 1) contno from "   
  +  " (SELECT PrtNo FROM LCCont a where grpcontno = '00000000000000000000'"
		+ " AND AppFlag = '1' and ( familycontno is  null or  familycontno='00000000000000000000')  "
		+ " AND ( PrintCount <= 0 OR PrintCount = 10 )"
		+ getWherePart('ManageCom', 'ManageCom', 'like')
		+ " and ManageCom like '" + comcode + "%%' "
		
		+ strContNoSQL
		
		+" union  "
  +  " (SELECT PrtNo FROM LCCont A where grpcontno = '00000000000000000000'"
		+ " AND AppFlag = '1' and FamilyType in ('1','2')  and (familycontno is not null and  familycontno!='00000000000000000000')   "
		+ " AND ( PrintCount <= 0 OR PrintCount = 10 )"
		+ getWherePart('ManageCom', 'ManageCom', 'like')
		
		+ strContNoSQL
		
		+" ) "
		+ "  union  (SELECT (SELECT distinct c.PrtNo from lccont  c where  c.contno=a.contno )"
	  +" FROM lpedoritem  a where 1=1  and edortype='LR' and edorstate='0' "
	  + " and ManageCom like '" + comcode + "%%' "
	  + getWherePart('ManageCom', 'ManageCom', 'like')
	  +" and  exists (select 'X' from lccont d where  d.contno=a.contno and d.AppFlag='1' and (d.printcount=10 or  d.printcount<=0))"
	  +" and (a.edorappdate <= now() and a.edorappdate >= substr((subdate(now() , 10)), 1, 10))) " 
		+") familyprt )"	;
	   }
 */ 
/*
var sqlid3="ProposalComPrint_IDGFSql3";
var mySql3=new SqlClass();
mySql3.setResourceName(sqlresourcename);
mySql3.setSqlId(sqlid3);
mySql3.addSubPara(fm.ManageCom.value);
mySql3.addSubPara(comcode);


var sqlid6="ProposalComPrint_IDGFSql6";
var mySql6=new SqlClass();
mySql6.setResourceName(sqlresourcename);
mySql6.setSqlId(sqlid6);
mySql6.addSubPara(fm.ManageCom.value);

var sqlid7="ProposalComPrint_IDGFSql7";
var mySql7=new SqlClass();
mySql7.setResourceName(sqlresourcename);
mySql7.setSqlId(sqlid7);
mySql7.addSubPara(comcode);
mySql7.addSubPara(fm.ManageCom.value);

var tTempSQL= mySql3.getString()+ strContNoSQL + mySql6.getString() + strContNoSQL +mySql7.getString();	
*/
	//	+ " and ManageCom like '" + comcode + "%%')"
	//	+ " union "
  //  +" (SELECT f.PrtNo "
	//  +" FROM lccontprinttrace f where 1=1 and RePrintFlag=0 "
	//  +" and ManageCom like '" + comcode + "%%' "
	//  + getWherePart('ManageCom', 'ManageCom', 'like')
	//  +" and (f.makedate <= sysdate and f.makedate >= substr((sysdate - 100), 0, 10)) "
  // 	+" and  exists (select 'X' from lccont d where  d.prtno=f.prtno and d.printcount='-1'))" 					
	//	+" ) familyprt )"	;		
			
	// ��дSQL���
	
	var strSQL = "";
/**
	strSQL = tTempSQL+ "select m,count(p) cp from (select a.contno p, substr(managecom,1,"+document.all('ManageGrade').value+") m from LCCont a,familycont b "
               + "where a.contno=b.contno and a.prtno=b.prtno and AppFlag = '1'  and EXISTS (SELECT 'X' from lcpol c where  c.contno=a.contno "
               +" and c.mainpolno=c.polno and c.riskcode not in ('311603') and riskcode in (SELECT riskcode FROM LMRiskApp where NotPrintPol = '0') and isvipcont(prtno,payintv,payyears,prem)= '0' )" //ֻ����Ҫ��ӡ�ı���" and riskcode<>'112401'
//               + "and not exists ( select PolNo from LCPol where a.PrtNo = PrtNo and AppFlag <> '1' ) "
	               + " ) " //ȥ�� and mainpolno=polno
//               + " and ISVIPPOL(prtno,payintv,payyears,prem)='0' "//�ж�VIP���߼���6.0�п��ܲ���ȷ -2009-02-09
//               + " and VERIFYOPERATEPOPEDOM(Riskcode,Managecom,'"+comcode+"','Pj')=1) "//6.0�в������У��
               +"group by m order by cp desc";
*/

		var sqlid8="";
		if(_DBT==_DBO){
			sqlid8="ProposalComPrint_IDGFSql8";
		}else if(_DBT==_DBM){
			sqlid8="ProposalComPrint_IDGFSql8_MYSQL";
		}
		var mySql8=new SqlClass();
		mySql8.setResourceName(sqlresourcename);
		mySql8.setSqlId(sqlid8);
		mySql8.addSubPara(window.document.getElementsByName(trim(controlName))[0].value);
		mySql8.addSubPara(comcode);
		mySql8.addSubPara(window.document.getElementsByName(trim(controlName))[0].value);
		mySql8.addSubPara(comcode);
		mySql8.addSubPara(window.document.getElementsByName(trim(controlName))[0].value);
		mySql8.addSubPara(document.all('ManageGrade').value);
//	  turnPage.strQueryResult  = easyQueryVer3(mySql8.getString(), 1, 0, 1);  
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("δ��ѯ���������������ݣ�");
     return false;
  }
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  turnPage.pageLineNum = 100 ;
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = PolGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL ; 
  
  
  //arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	
}

function queryBranch()
{
  showInfo = window.open("../certify/AgentTrussQuery.html","",sFeatures);
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
	
/*
  var strSQL="select contno,Errmsg,makedate,maketime,ManageCom from LDSysErrLog where 1=1 "
  + getWherePart( 'ContNo','ErrorContNo' )
  + getWherePart( 'MakeDate' );
  + getWherePart( 'ManageCom' );
*/	
var sqlid5="ProposalComPrint_IDGFSql5";
var mySql5=new SqlClass();
mySql5.setResourceName(sqlresourcename);
mySql5.setSqlId(sqlid5);
mySql5.addSubPara(fm.ErrorContNo.value);
mySql5.addSubPara(fm.MakeDate.value);
mySql5.addSubPara(fm.ManageCom.value);
	
	turnPage.strQueryResult  = easyQueryVer3(mySql5.getString(), 1, 0, 1);

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