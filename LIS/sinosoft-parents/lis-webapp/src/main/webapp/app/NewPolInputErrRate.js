//
//�������ƣ�ProposalNoRespQuery.js
//�����ܣ���ѯ����δ�ظ��嵥
//�������ڣ�2007-03-20 18:02
//������  ��Fuqx
//���¼�¼��  ������    ��������     ����ԭ��/����

//var arrDataSet;
var turnPage = new turnPageClass();

//�򵥲�ѯ
function easyQuery()
{
	var ManageCom = document.all('ManageCom').value;
	var sDate = document.all('StartDate').value;
	var eDate = document.all('EndDate').value;
	/*
	if (ManageCom == "")
	{
			alert("��ѡ����������");
			return;
	}
	*/
	if(ManageCom!=null&&ManageCom!=""){
	if(ManageCom.length<4){
		alert("�������ӦΪ4λ��8λ��");
		return;
	}  
	}
	//if(document.all('PrtNo').value=="")
	//{
	// if (sDate == "")
	//  {
	//		alert("������¼����ʼ���ڣ�");
	//		return;
	//  }
	 // if (eDate == "")
	 // {
	//		alert("������¼���������ڣ�");
	//		return;
  	//}
  //}
	initCodeGrid();
	// ��дSQL���
	//var strSQL = " select a.PrtNo,(select RiskName from LMRiskApp where RiskCode=a.RiskCode) riskname,(select nvl(codename,a.salechnl) from ldcode where codetype='salechnl' and code=a.salechnl) salechnl ,a.managecom,(select Name from LAAgent where agentcode=a.agentcode) agentname,a.insuredname insuredname,a.appntname appntname,(select decode(min(c.makedate),null,'��ɨ���',to_char(min(c.makedate),'yyyy-mm-dd')) from es_doc_main c where c.doccode=trim(a.prtno)) ScanDate,(select decode(max(d.makedate),null,'δ��',to_char(max(d.makedate),'yyyy-mm-dd')) from lcuwsub d where d.ProposalNo=a.ProposalNo and uwflag='8') UWNoticeDate,(select decode(max(e.makedate),null,'δ��',to_char(max(e.makedate),'yyyy-mm-dd')) from LCPENotice e where e.ProposalNo = a.ProposalNo) HealthDate,(select decode(max(f.makedate),null,'δ��',to_char(max(f.makedate),'yyyy-mm-dd')) from LCRReport f where f.polno = a.ProposalNo) SurveyDate,getQuestSendDate(a.PolNo,'2') AgentQuestSendDate,getQuestSendDate(a.PolNo,'4') ManageQuestSendDate"
//	var strSQL = "  select a.prtno,(select riskname from lmriskapp where riskcode =a.riskcode),"
//             + " a.appntname,a.insuredname,a.amnt, "
//             + "  (select prem from lccont where contno =a.contno),"
//             + " a.agentcode, a.managecom "
//             + " from lcpol a ,es_doc_main b,lccont c where mainpolno =polno and a.prtno =b.doccode and b.doccode=c.prtno  and b.subtype ='UA001' and a.appflag ='0' and c.conttype ='1'"
//             + getWherePart('b.Makedate','StartDate','>=')
//             + getWherePart('b.Makedate','EndDate','<=')
//             + getWherePart('d.prtno','prtNo')
//             //+ getWherePart('a.PrtNo','PrtNo')
//             //+ getWherePart('a.RiskCode','RiskCode')
//             //+ getWherePart('a.InsuredName','InsuredName')
//             //+ getWherePart('d.salechnl','SaleChnl')
//             //+ getWherePart('a.agentcode','AgentCode')
//             + " and a.ManageCom like '" + document.all("ManageCom").value + "%%'"
//             + " and a.ManageCom like '" + comCode + "%%'";
	
	  var  StartDate = window.document.getElementsByName(trim("StartDate"))[0].value;
	  var  EndDate = window.document.getElementsByName(trim("EndDate"))[0].value;
	  var  prtNo = window.document.getElementsByName(trim("prtNo"))[0].value;
	  var sqlid1="NewPolInputErrRateSql0";
	  var mySql1=new SqlClass();
	  mySql1.setResourceName("app.NewPolInputErrRateSql"); //ָ��ʹ�õ�properties�ļ���
	  mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	  mySql1.addSubPara(StartDate);//ָ������Ĳ���
	  mySql1.addSubPara(EndDate);//ָ������Ĳ���
	  mySql1.addSubPara(prtNo);//ָ������Ĳ���
	  mySql1.addSubPara(document.all("ManageCom").value);//ָ������Ĳ���
	  mySql1.addSubPara(comCode);//ָ������Ĳ���
	  var strSQL=mySql1.getString();
	  
    //�ж�����
    var tScanType = document.all('ScanType').value;
       if(tScanType =="����"){
          strSQL = strSQL+" and substr(b.doccode,1,4) = '8611' order by a.prtno ";
       }else if(tScanType =="�н�"){
          strSQL = strSQL+" and substr(b.doccode,1,4) = '8621' order by a.prtno ";
       }else if(tScanType =="����"){
          strSQL = strSQL+" and substr(b.doccode,1,4) = '8616' order by a.prtno ";
       }else if(tScanType =="����"){
          strSQL = strSQL+" and substr(b.doccode,1,4) in ('8615','8625','8635') order by a.prtno";
       }else {
          strSQL = strSQL+" order by a.prtno ";
       }
	//prompt("abc",strSQL);
	turnPage.queryModal(strSQL, CodeGrid);    
}
 
function easyPrint()
{

 if(fm.ManageCom.value==""||fm.ManageCom.value==null){
   alert("��¼����������");
   return false;
 }
 if(fm.StartDate.value==null||fm.StartDate.value==""
     ||fm.EndDate.value==null||fm.EndDate.value==""){
      alert("��¼��ɨ�迪ʼʱ���ɨ�����ʱ��");
      return false;
     }
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
  if(document.all('AgentCode').value == "")	
  {  
		var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+comcode,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
  }
  
	if(document.all('AgentCode').value != "")	 
	{
	var cAgentCode = fm.AgentCode.value;  //�����˱���	
	//var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
	var sqlid2="NewPolInputErrRateSql1";
	var mySql2=new SqlClass();
	mySql2.setResourceName("app.NewPolInputErrRateSql"); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(cAgentCode);//ָ������Ĳ���
	var strSql=mySql2.getString();
    var arrResult = easyExecSql(strSql);
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
	var sqlid3="NewPolInputErrRateSql2";
	var mySql3=new SqlClass();
	mySql3.setResourceName("app.NewPolInputErrRateSql"); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(cAgentCode);//ָ������Ĳ���
	var strSql=mySql3.getString();
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {
    
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else{
 
     alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
     }
	}
}