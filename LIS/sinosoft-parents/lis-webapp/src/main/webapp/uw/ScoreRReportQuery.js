//�������ƣ�ScoreRReportQuery.js
//�����ܣ��������ֻ���
//�������ڣ�2008-10-27
//������  ��ln
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//��ѯ�������ֱ�
function ScoreQuery()
{	
	var ContNo = fm.ContNoH.value;
		if (ContNo == "")
		    return;
		 // window.open("./UWscoreRReport.jsp?ContNo="+fm.ContNoH.value+"&Type=2", "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");
		  window.open("./UWscoreRReport.jsp?ContNo="+fm.ContNoH.value+"&Type=2", "","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//������ϸ��Ϣ
function showPolDetail()
{  
  var cContNo = fm.ContNoH.value;
  var cPrtNo = fm.PrtNoH.value;
//    strSql="select case lmriskapp.riskprop"
//        +" when 'I' then"
//        +" '1'"
//        +" when 'G' then"
//        +" '2'"
//        +" when 'Y' then"
//        +" '3'"
//        +" end"
//        +" from lmriskapp"
//        +" where riskcode in (select riskcode"
//        +" from lcpol"
//        +" where polno = mainpolno"
//        +" and prtno = '"+cPrtNo+"')"
  
//  var BankFlag = document.all('SaleChnl').value;
//  var arrResult = easyExecSql(strSql);
//  var BankFlag = arrResult[0][0];
  //alert("BankFlag="+BankFlag);
  
    //ȥ��ԭ�����ж�Ͷ�������͵��߼����޸�Ϊ��ӡˢ�����ж�Ͷ��������
	var BankFlag = "";
    var tSplitPrtNo = cPrtNo.substring(0,4);
    //alert("tSplitPrtNo=="+tSplitPrtNo); 
    //8635��8625��8615������Ͷ�������棬����Ķ��߸��ս���
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"){
    	BankFlag="3";
    }else{
    	BankFlag="1";
    }
  
  	//var strSql2="select missionprop5 from lbmission where activityid='0000001099' and missionprop1='"+cPrtNo+"'";
	mySql = new SqlClass();
	mySql.setResourceName("uw.ScoreRReportQuerySql");
	mySql.setSqlId("ScoreRReportQuerySql1");
	mySql.addSubPara(cPrtNo); 
	var crrResult = easyExecSql(mySql.getString());
	var SubType="";
	if(crrResult!=null)
	{
	SubType = crrResult[0][0];
	}

  //window.open("../app/ProposalEasyScan.jsp?LoadFlag=6&prtNo="+cPrtNo+"&ContNo="+cContNo+"&BankFlag="+BankFlag+"&SubType="+SubType, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");
  window.open("../app/ProposalEasyScan.jsp?LoadFlag=6&prtNo="+cPrtNo+"&ContNo="+cContNo+"&BankFlag="+BankFlag+"&SubType="+SubType, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");

}

//ɨ�����ѯ
function ScanQuery()
{

	  var ContNo = fm.ContNoH.value;
		if (ContNo == "")
		    return;
		  //window.open("./ImageQueryMain.jsp?ContNo="+fm.PrtNoH.value, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");
window.open("./ImageQueryMain.jsp?ContNo="+fm.PrtNoH.value, "","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//��ѯ���������Ͷ����
function easyQueryClick()
{	
    fm.Button1.disabled = true; 
    fm.Button2.disabled = true;  
    fm.Button3.disabled = true;
    
	/*var strsql = " select (select contno from lccont where prtno=a.contno), contno, (select insuredname from lccont where prtno=a.contno) "
	       + " , (select managecom from lccont where prtno=a.contno), name, customerno, assessoperator, score"
	       + " from lcscorerreport a where 1=1 "
	       + getWherePart( 'CustomerNo','CustomerNo' )
				 + getWherePart( 'Name','Name' )
				 + getWherePart( 'AssessOperator','AssessOperator')	
				 + getWherePart( 'contno','PrtNo')			   
				 + getWherePart('ModifyDate','StartDate','>=')
         + getWherePart('ModifyDate','EndDate','<=')
         + " and ManageCom like '" + document.all("ManageCom").value + "%%'"
		 + " and ManageCom like '" + manageCom + "%%'"
         + " order by contno, ModifyDate";*/
	mySql = new SqlClass();
	mySql.setResourceName("uw.ScoreRReportQuerySql");
	mySql.setSqlId("ScoreRReportQuerySql2");
	mySql.addSubPara(fm.CustomerNo.value ); 	
	mySql.addSubPara(fm.Name.value ); 
	mySql.addSubPara(fm.AssessOperator.value ); 
	mySql.addSubPara(fm.PrtNo.value ); 
	mySql.addSubPara(fm.StartDate.value ); 
	mySql.addSubPara(fm.EndDate.value ); 
	mySql.addSubPara(document.all("ManageCom").value ); 
	mySql.addSubPara(manageCom); 
  turnPage.queryModal(mySql.getString(),ScoreRReportGrid); 
}

//��ѡ����¼�󣬸������ı���ֵ
function initButtonQuery()
{	
	 fm.Button1.disabled = false; 
	 fm.Button2.disabled = false;  
	 fm.Button3.disabled = false;
	 var selno = ScoreRReportGrid.getSelNo()-1;
	 fm.ContNoH.value = ScoreRReportGrid.getRowColData(selno, 1);
	 fm.PrtNoH.value  = ScoreRReportGrid.getRowColData(selno, 2);
	
}
