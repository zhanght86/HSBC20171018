//
//�������ƣ�ProposalSignErrQuery.js
//�����ܣ���ѯ����ǩ��ʧ��ԭ��
//�������ڣ�2007-03-09 11:42
//������  ��Fuqx
//���¼�¼��  ������    ��������     ����ԭ��/����

//var arrDataSet;
var turnPage = new turnPageClass();
var sqlresourcename = "app.ProposalSignErrQuerySql";

//�򵥲�ѯ
function easyQuery()
{
	
	var ManageCom = document.all('ManageCom').value;
	var sDate = document.all('StartDate').value;
	var eDate = document.all('EndDate').value;
	
	if (ManageCom == "")
	{
			alert("��ѡ����������");
			return;
	}

/*	if(ManageCom.length!=6 && ManageCom.length!=8){
		alert("�������ӦΪ6λ��8λ��");
		return;
	}
*/  
	//if(document.all('PrtNo').value=="")
	//{
	// if (sDate == "")
	// {
	//		alert("������¼����ʼ���ڣ�");
	//		return;
	// }
	// if (eDate == "")
	// {
	//		alert("������¼���������ڣ�");
	//		return;
	// }
	//}
	
  //var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  //var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");  
var strSQL = "";
var addStr1 = " and 1=1 ";
var addStr2 = " and 2=2 ";
	// ��дSQL���
	//���¼��ɨ��ʱ��,ֻ��ʾɨ���.
	lockScreen('lkscreen');
	if((document.all('ScanStartDate').value!=null&&document.all('ScanStartDate').value!='')
	   ||document.all('ScanEndDate').value!=null&&document.all('ScanEndDate').value!='')
	{
/*		strSQL = " select  b.PrtNo , '',b.salechnl ,b.managecom ,"
       		 + " b.agentcode ,"
       		 + "  b.appntname , b.insuredname , "
       	   + " nvl((select sum(amnt) from lcpol where contno=b.contno and mainpolno=polno and uwflag in ('4','9') and appflag='0'),0), "
       		 + " nvl((select sum(prem) from lcpol where contno=b.contno and uwflag in ('4','9') and appflag='0'),0), "
       	   + " (select Name from LAAgent where agentcode = b.agentcode),"
      		 + " a.signno, a.MakeDate ,replace(a.reason,b.prtno,'') , b.managecom " //RANK() OVER(PARTITION BY a.prtno ORDER BY a.SignNo desc) AS RANK"
     		   + " from lcindappsignlog a,lccont b,es_doc_main c"
      		 + " where a.prtno = b.prtno and b.prtno = c.doccode"
      		 + " and b.appflag='0' and b.uwflag in ('4','9') "
      		 + " and a.errtype='01' "
      		 + " and c.busstype='TB' "
					 + " and c.subtype='UA001' "
     		   //+ " and a.makedate = (select max(makedate) from lcindappsignlog where prtno =a.prtno)"
             + getWherePart('c.Makedate','ScanStartDate','>=')
             + getWherePart('c.Makedate','ScanEndDate','<=')
             + getWherePart('a.Makedate','SignStartDate','>=')
             + getWherePart('a.Makedate','SignEndDate','<=')
             + getWherePart('b.Makedate','StartDate','>=')
       		 + getWherePart('b.Makedate','EndDate','<=')
             //+ getWherePart('b.PrtNo','PrtNo')
            // + getWherePart('d.RiskCode','RiskCode')
             + getWherePart('b.InsuredName','InsuredName')
             + getWherePart('b.salechnl','SaleChnl')
             + getWherePart('b.agentcode','AgentCode')
             + " and b.ManageCom like '" + document.all("ManageCom").value + "%%'"
             + " and b.ManageCom like '" + comcode + "%%'"
 */      
        if(document.all('RiskCode').value!=null&&document.all('RiskCode').value!='')
   {
//   	 strSQL = strSQL + " and exists (select '1' from lcpol where riskcode='"+document.all('RiskCode').value+"' "
//   	        + " and contno=b.contno )";
   	        addStr1 = " and exists (select '1' from lcpol where riskcode='"+document.all('RiskCode').value+"' "
   	        + " and contno=b.contno )";
   }   
//    strSQL = strSQL + " order by c.makedate,b.prtno ";

var sqlid1="ProposalSignErrQuerySql1";
var mySql1=new SqlClass();
mySql1.setResourceName(sqlresourcename);
mySql1.setSqlId(sqlid1);
mySql1.addSubPara(fm.ScanStartDate.value);
mySql1.addSubPara(fm.ScanEndDate.value);
mySql1.addSubPara(fm.SignStartDate.value);
mySql1.addSubPara(fm.SignEndDate.value);
mySql1.addSubPara(fm.StartDate.value);
mySql1.addSubPara(fm.EndDate.value);
mySql1.addSubPara(fm.InsuredName.value);
mySql1.addSubPara(fm.SaleChnl.value);
mySql1.addSubPara(fm.AgentCode.value);
mySql1.addSubPara(document.all("ManageCom").value);
mySql1.addSubPara(comcode);     
   mySql1.addSubPara(addStr1);  
strSQL = mySql1.getString() ;  

	}
	else
	{
/*		strSQL = 
	     " select b.PrtNo ,'', b.salechnl ,b.managecom , "
       + " b.agentcode ,"
       + " b.appntname ,b.insuredname , "
       + " nvl((select sum(amnt) from lcpol where contno=b.contno and mainpolno=polno and uwflag in ('4','9') and appflag='0'),0), "
       + " nvl((select sum(prem) from lcpol where contno=b.contno and uwflag in ('4','9') and appflag='0'),0), "

       + " (select Name from LAAgent where agentcode = b.agentcode), "
       + " a.signno, a.MakeDate MakeDate,replace(a.reason,b.prtno,'') errinfo,b.managecom " //,RANK() OVER(PARTITION BY a.prtno ORDER BY a.SignNo desc) AS RANK "
       + " from LCIndAppSignLog a, lccont b "
       + "  where a.prtno = b.prtno "
       + " and b.appflag = '0' "
       + " and b.uwflag in ('4','9') "
       + " and a.errtype='01' "
       //+ "  and a.makedate = (select max(makedate) from lcindappsignlog where prtno =a.prtno)"
     	 + getWherePart('b.Makedate','StartDate','>=')
       + getWherePart('b.Makedate','EndDate','<=')
       + getWherePart('a.Makedate','SignStartDate','>=')
       + getWherePart('a.Makedate','SignEndDate','<=')
       + getWherePart('b.InsuredName','InsuredName')
       + getWherePart('b.salechnl','SaleChnl')
       + getWherePart('b.agentcode','AgentCode')
       + " and b.ManageCom like '" + document.all("ManageCom").value + "%%'"
       + " and b.ManageCom like '" + comcode + "%%'";
             //+ " order by b.prtno,a.makedate"
*/
   if(document.all('RiskCode').value!=null&&document.all('RiskCode').value!='')
   {
   	 addStr2 = " and exists (select '1' from lcpol where riskcode='"+document.all('RiskCode').value+"' "
   	        + " and contno=b.contno )";
   }   
//   	 strSQL = strSQL + " order by b.makedate,b.prtno ";

var sqlid4="ProposalSignErrQuerySql4";
var mySql4=new SqlClass();
mySql4.setResourceName(sqlresourcename);
mySql4.setSqlId(sqlid4);
mySql4.addSubPara(fm.StartDate.value);
mySql4.addSubPara(fm.EndDate.value);
mySql4.addSubPara(fm.SignStartDate.value);
mySql4.addSubPara(fm.SignEndDate.value);
mySql4.addSubPara(fm.InsuredName.value);
mySql4.addSubPara(fm.SaleChnl.value);
mySql4.addSubPara(fm.AgentCode.value);
mySql4.addSubPara(document.all("ManageCom").value);
mySql4.addSubPara(comcode);   
   mySql4.addSubPara(addStr2);  
strSQL = mySql4.getString() ;  

	}
    
	//prompt("abc",strSQL);
	turnPage.queryModal(strSQL, CodeGrid);    
		unlockScreen('lkscreen');  
}

function easyPrint()
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
  var arrReturn = new Array();
		fm.target = "../f1print";
		//fm.action = "";
		document.getElementById("fm").submit();
		showInfo.close();
}

//��ѯ�����˵ķ�ʽ
function queryAgent(comcode)
{
	//alert("ok");
  if(document.all('AgentCode').value == "")	
  {  
		var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+comcode,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
  }
  
	if(document.all('AgentCode').value != "")	 
	{
	var cAgentCode = fm.AgentCode.value;  //�����˱���	
//	var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";

var sqlid6="ProposalSignErrQuerySql6";
var mySql6=new SqlClass();
mySql6.setResourceName(sqlresourcename);
mySql6.setSqlId(sqlid6);
mySql6.addSubPara(cAgentCode);

    var arrResult = easyExecSql(mySql6.getString());
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
 
var sqlid7="ProposalSignErrQuerySql7";
var mySql7=new SqlClass();
mySql7.setResourceName(sqlresourcename);
mySql7.setSqlId(sqlid7);
mySql7.addSubPara(cAgentCode);
 
    var arrResult = easyExecSql(mySql7.getString());
       //alert(arrResult);
    if (arrResult != null) {
    
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else{
 
     alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
     }
	}
}