//
//�������ƣ�ProposalNoRespQuery.js
//�����ܣ���ѯ����δ�ظ��嵥
//�������ڣ�2007-03-20 18:02
//������  ��Fuqx
//���¼�¼��  ������    ��������     ����ԭ��/����

//var arrDataSet;
var turnPage = new turnPageClass();
var sqlresourcename = "app.UnSignPolQuerySql";
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
	if(ManageCom.length<4){
		alert("�������ӦΪ4λ��8λ��");
		return;
	}  
	*/
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
	/*
	var strSQL = "  select  a.prtno, a.managecom, "
             + " (select min(makedate) from es_doc_main  where doccode =a.prtno), "
             + " (select min(maketime) from es_doc_main where doccode =a.prtno), "
             + " a.prem, "
             + " (select min(makedate) from ljtempfee where otherno =a.prtno),"
             + " (select min(enteraccdate) from ljtempfee where otherno =a.prtno)"
             + " from lccont a where 1=1 "
             + " and a.Appflag = '0'	and conttype ='1' "
             + getWherePart('b.Makedate','StartDate','>=')
             + getWherePart('b.Makedate','EndDate','<=')
             + getWherePart('d.prtno','prtNo')
             //+ getWherePart('a.PrtNo','PrtNo')
             //+ getWherePart('a.RiskCode','RiskCode')
             //+ getWherePart('a.InsuredName','InsuredName')
             //+ getWherePart('d.salechnl','SaleChnl')
             //+ getWherePart('a.agentcode','AgentCode')
             + " and a.ManageCom like '" + document.all("ManageCom").value + "%%'"
             + " and a.ManageCom like '" + comCode + "%%'";
    */
var sqlid1="UnSignPolQuerySql1";
var mySql1=new SqlClass();
mySql1.setResourceName(sqlresourcename);
mySql1.setSqlId(sqlid1);
mySql1.addSubPara(fm.StartDate.value);
mySql1.addSubPara(fm.EndDate.value);
mySql1.addSubPara(fm.prtNo.value);
mySql1.addSubPara(document.all("ManageCom").value);
mySql1.addSubPara(comCode);
    
var strSQL =   mySql1.getString();
    
    
    
    
    
    //״̬
    var Activityid ="";
    var State =fm.state.value;
     if(State!=null&&State!=""){
        if(State=="1") {strSQL=strSQL+" and exists (select 1 from lwmission where missionprop1 =a.prtno and activityid in ('0000001099','0000001098'))";
        
        }
        else if(State=="2") {strSQL=strSQL+" and exists (select 1 from lwmission where missionprop1 =a.prtno and activityid in ('0000001090'))";
        
        }
        else if(State=="3") strSQL=strSQL+" and exists (select 1 from lwmission where missionprop1 =a.prtno and activityid in ('0000001091'))";
        else if(State=="4") strSQL=strSQL+" and exists (select 1 from lwmission where missionprop1 =a.prtno and activityid in ('0000001001'))";
        else if(State=="5") strSQL=strSQL+" and exists (select 1 from lwmission where missionprop1 =a.prtno and activityid in ('0000001002'))";
        else if(State=="6") strSQL=strSQL+" and exists (select 1 from lwmission where missionprop1 =a.prtno and activityid in ('0000001003'))";
        else if(State=="7") strSQL=strSQL+" and exists (select 1 from lwmission where missionprop1 =a.prtno and activityid in ('0000001100'))"
                                         +" and a.uwflag !='4'";
        else if(State=="8") strSQL=strSQL+" and exists (select 1 from lwmission where missionprop1 =a.prtno and activityid in ('0000001150'))";
        else if(State=="9") strSQL=strSQL+" and exists (select 1 from lwmission where missionprop1 =a.prtno and activityid in ('0000001003'))";
     }
	//prompt("abc",strSQL);
	turnPage.queryModal(strSQL, CodeGrid);    
}
 
function easyPrint()
{
	var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;"); 

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
    
var sqlid2="UnSignPolQuerySql2";
var mySql2=new SqlClass();
mySql2.setResourceName(sqlresourcename);
mySql2.setSqlId(sqlid2);
mySql2.addSubPara(cAgentCode);

    
    var arrResult = easyExecSql(mySql2.getString());
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
    
var sqlid3="UnSignPolQuerySql3";
var mySql3=new SqlClass();
mySql3.setResourceName(sqlresourcename);
mySql3.setSqlId(sqlid3);
mySql3.addSubPara(cAgentCode);
    
    var arrResult = easyExecSql(mySql3.getString());
       //alert(arrResult);
    if (arrResult != null) {
    
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else{
 
     alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
     }
	}
}