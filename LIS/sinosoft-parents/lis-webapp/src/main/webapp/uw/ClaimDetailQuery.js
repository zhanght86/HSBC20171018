var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();



//������ѯ
function initQuery()
{	  
    var rptNo1 = fm.ClmNo.value;    
    if(rptNo1 == "")
    {
        alert("�����ⰸΪ�գ�");
        return;
    }    
    
    //�����¼���(һ��)
		var sqlid1="ClaimDetailQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.ClaimDetailQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(rptNo1);//ָ������Ĳ���
	    var strSQL1=mySql1.getString();	
	
//    var strSQL1 = "select a.CaseRelaNo,b.AccDate from LLCaseRela a,LLAccident b where a.CaseRelaNo = b.AccNo and "
//                + "a.CaseNo = '"+rptNo1+"'";

    var AccNo = easyExecSql(strSQL1);
    //���������ż�����������Ϣ(һ��)
			var sqlid2="ClaimDetailQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.ClaimDetailQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(rptNo1);//ָ������Ĳ���
	    var strSQL2=mySql2.getString();	
	
//    var strSQL2 = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom,(select username from llclaimuser where usercode = LLReport.Operator),RgtClass from LLReport where "
//                + "RptNo = '"+rptNo1+"'";

    var RptContent = easyExecSql(strSQL2);
    //����ҳ������
    fm.AccNo1.value = AccNo[0][0];
    fm.AccidentDate1.value = AccNo[0][1];
    if(RptContent!=null&&RptContent!=""){
        fm.RptNo1.value = RptContent[0][0];
        fm.RptorName1.value = RptContent[0][1];
        fm.RptorPhone1.value = RptContent[0][2];
        fm.RptorAddress1.value = RptContent[0][3];
        fm.Relation1.value = RptContent[0][4];
        fm.RptMode.value = RptContent[0][5];
        fm.AccidentSite1.value = RptContent[0][6];
        fm.occurReason.value = RptContent[0][7];
        fm.RptDate1.value = RptContent[0][8];
        fm.MngCom1.value = RptContent[0][9];
        fm.Operator1.value = RptContent[0][10];
        fm.RgtClass1.value = RptContent[0][11];
    }
    //showOneCodeName('llrgtclass','RgtClass1','RgtClass1Name'); //�������
    showOneCodeName('llrgrelation','Relation1','Relation1Name');//���������¹��˹�ϵ
    
    //showOneCodeName('llrptmode','RptMode','RptModeName');//������ʽ
    
    //showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
    showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ    
      
}


//������ѯ
function queryRegister()
{  
    var rptNo2 = fm.ClmNo.value;
    
    if(rptNo2 == "")
    {
        alert("�����ⰸΪ�գ�");
        return;
    }
    //�����¼��š������¹ʷ�������(һ��)
	
		var sqlid3="ClaimDetailQuerySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("uw.ClaimDetailQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(rptNo2);//ָ������Ĳ���
	    var strSQL1=mySql3.getString();	
	
//    var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
//                + "LLCaseRela.CaseNo = '"+rptNo2+"'";

    var AccNo = easyExecSql(strSQL1);

    //���������ż�����������Ϣ(һ��)
    //----------------------1------2-----------3-----------4------------5--------6------------7-------8--------9-----------10---------11--------12-----------13--------------14---------15-----------16------------17------------18---------19
    
		var sqlid4="ClaimDetailQuerySql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("uw.ClaimDetailQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(rptNo2);//ָ������Ĳ���
	    var strSQL2=mySql4.getString();	
	
//	var strSQL2 = "select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate,(select username from llclaimuser where usercode = llregister.Operator),mngcom,(case assigneetype when '0' then 'ҵ��Ա' when '1' then '�ͻ�' end),assigneecode,assigneename,(case assigneesex when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end),assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion,RgtState,RgtClass,(case RgtClass when '1' then '����' when '2' then '����' when '3' then '��ͥ' end),clmstate from llregister where "
//                + "rgtno = '"+rptNo2+"'";

    var RptContent = easyExecSql(strSQL2);

    //����ҳ������
    fm.AccNo2.value = AccNo[0][0];
    fm.AccidentDate2.value = AccNo[0][1];
    if(RptContent!=null&&RptContent!=""){

    fm.RptNo2.value = RptContent[0][0];
    fm.RptorName2.value = RptContent[0][1];
    fm.RptorPhone2.value = RptContent[0][2];
    fm.RptorAddress2.value = RptContent[0][3];
    fm.Relation2.value = RptContent[0][4];
    fm.AccidentSite2.value = RptContent[0][5];
    fm.RptDate2.value = RptContent[0][6];
    fm.Operator2.value = RptContent[0][7];
    fm.MngCom2.value = RptContent[0][8];
    fm.AssigneeType.value = RptContent[0][9];
    fm.AssigneeCode.value = RptContent[0][10];
    fm.AssigneeName.value = RptContent[0][11];
    fm.AssigneeSex.value = RptContent[0][12];
    fm.AssigneePhone.value = RptContent[0][13];
    fm.AssigneeAddr.value = RptContent[0][14];
    fm.AssigneeZip.value = RptContent[0][15];
    fm.occurReason.value = RptContent[0][16];
    //fm.RgtConclusion.value = RptContent[0][17];
   // fm.rgtType.value = RptContent[0][18];
    //fm.RgtClafm.value = RptContent[0][19];
    fm.RgtClassName.value = RptContent[0][20];
    }
   // fm.ClmState.value = RptContent[0][21];
   // showOneCodeName('llrgttype','rgtType','rgtTypeName');
    //showOneCodeName('llclaimstate','ClmState','ClmStateName');
    showOneCodeName('llrgrelation','Relation2','Relation2Name');//���������¹��˹�ϵ
    //showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
    //showOneCodeName('llrgtconclusion','RgtConclusion','RgtConclusionName');//��������
} 

//��������Ϣ
function afterQueryLL(arr)
{    
	  var rptNo3 = fm.ClmNo.value;    
    var tSubReport = new Array;
    var RptContent = new Array;
	
		var sqlid5="ClaimDetailQuerySql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("uw.ClaimDetailQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(rptNo3);//ָ������Ĳ���
	    var strSQL2=mySql5.getString();	
		
		var sqlid6="ClaimDetailQuerySql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("uw.ClaimDetailQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(rptNo3);//ָ������Ĳ���
	    var strSQL3=mySql6.getString();	
	
//    var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccdentDesc,AccResult1,AccResult2,CustomerName,CustomerAge,CustomerSex,VIPFlag,EditFlag from LLCase where RgtNo= '"+rptNo3+"' ";
//    var strSQL3 = "select AccidentReason from LLReport where "
//               + "RptNo = '"+rptNo3+"'";

     RptContent = easyExecSql(strSQL3);         
     tSubReport = easyExecSql(strSQL2);
    
    fm.occurReason.value = RptContent[0][0];
    fm.hospital.value = tSubReport[0][0];
    fm.AccidentDate2.value = tSubReport[0][1];
    fm.accidentDetail.value = tSubReport[0][2];
    fm.cureDesc.value = tSubReport[0][4];
    fm.Remark.value = tSubReport[0][5];
    fm.AccDesc.value = tSubReport[0][6];
    fm.AccResult1.value = tSubReport[0][7];
    fm.AccResult2.value = tSubReport[0][8];
    fm.customerName.value = tSubReport[0][9];
    fm.customerAge.value = tSubReport[0][10];
    fm.customerSex.value = tSubReport[0][11];
    fm.IsVip.value = tSubReport[0][12];
    fm.IsModify.value = tSubReport[0][13];
   // showOneCodeName('IsModify','IsModify','IsModifyName');
    showOneCodeName('sex','customerSex','customerSexName');//�Ա�
    showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ
    showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
    showOneCodeName('llaccidentdetail','accidentDetail','accidentDetailName');//����ϸ��
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//�������
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//���ս��1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//���ս��2
    queryResult('AccResult1','AccResult1Name');
    queryResult('AccResult2','AccResult2Name');        
}

//��ѯ�����������
function ClaimType() 
{ 
	  var rptNo4 = fm.ClmNo.value; 
    var tClaimType = new Array;
	
		var sqlid7="ClaimDetailQuerySql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("uw.ClaimDetailQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(rptNo4);//ָ������Ĳ���
	    var strSQL4=mySql7.getString();	
	
//    var strSQL4 = "select ReasonCode from LLAppClaimReason where "
//                + " CaseNo = '"+rptNo4+"'";

    tClaimType = easyExecSql(strSQL4);
    if (tClaimType == null)
    {
    	  alert("��������Ϊ�գ�����˼�¼����Ч�ԣ�");
    	  return;
    }
    else
    {
        for(var j=0;j<fm.claimType.length;j++)
        {
        	  for (var l=0;l<tClaimType.length;l++)
        	  {
        	  	  var tClaim = tClaimType[l].toString();
        	  	  tClaim = tClaim.substring(tClaim.length-2,tClaim.length);//ȡ�������ͺ���λ
//        	  	  alert(tClaim);
        	  	  if(fm.claimType[j].value == tClaim)
        	  	  {
                	  fm.claimType[j].checked = true;
            	  }
            }
        }
    }   
}    
    
//����������Ϣ
function Register()
{
	  var rptNo5 = fm.ClmNo.value;
	  var RptContent = new Array;
	  
	  	var sqlid8="ClaimDetailQuerySql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("uw.ClaimDetailQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
		mySql8.addSubPara(rptNo5);//ָ������Ĳ���
	    var strSQL2=mySql8.getString();	
	  
//    var strSQL2 = "select RgtConclusion,RgtState,clmstate from llregister where "
//                + "rgtno = '"+rptNo5+"'";

    var RptContent = easyExecSql(strSQL2);
    fm.RgtConclusion.value = RptContent[0][0];   
    fm.rgtType.value = RptContent[0][1];
    fm.ClmState.value = RptContent[0][2];
    showOneCodeName('llclaimstate','ClmState','ClmStateName');
    showOneCodeName('llrgttype','rgtType','rgtTypeName');
    showOneCodeName('llrgtconclusion','RgtConclusion','RgtConclusionName');//�������� 

} 

//��˹���
function queryAudit()
{
	  var rptNo6 = fm.ClmNo.value;
	  var tAudit = new Array;
	  
	  	 var sqlid9="ClaimDetailQuerySql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("uw.ClaimDetailQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
		mySql9.addSubPara(rptNo6);//ָ������Ĳ���
	    var strSql=mySql9.getString();	
	  
//    var strSql = " select AuditConclusion,AuditIdea,SpecialRemark,AuditNoPassReason,AuditNoPassDesc,(select b.username from llclaimuser b where b.usercode = LLClaimUWMain.AuditPer),AuditDate from LLClaimUWMain where "
//               + " ClmNo = '" +rptNo6 + "'";

     tAudit = easyExecSql(strSql);
    if (tAudit != null)
    {
        fm.AuditConclusion.value = tAudit[0][0];
        fm.AuditIdea.value = tAudit[0][1];
        fm.SpecialRemark1.value = tAudit[0][2];
        //fm.ProtestReason.value = tAudit[0][3];
        //fm.ProtestReasonDesc.value = tAudit[0][4];
        fm.AuditPer.value = tAudit[0][5];
        fm.AuditDate.value = tAudit[0][6];
        showOneCodeName('llclaimconclusion','AuditConclusion','AuditConclusionName');
        //showOneCodeName('llprotestreason','ProtestReason','ProtestReasonName');
        
        
    }
}

    
//��������
function queryConfirm()
{
	  var rptNo7 = fm.ClmNo.value;
	  
	  	 var sqlid10="ClaimDetailQuerySql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("uw.ClaimDetailQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
		mySql10.addSubPara(rptNo7);//ָ������Ĳ���
	    var strSql=mySql10.getString();	
	  
//    var strSql = " select ExamConclusion,ExamIdea,ExamNoPassReason,ExamNoPassDesc,(select b.username from llclaimuser b where b.usercode = LLClaimUWMain.ExamPer),ExamDate from LLClaimUWMain where "
//               + " ClmNo = '" + rptNo7 + "'";

    var tConfirm = easyExecSql(strSql);

    if (tConfirm != null)
    {
        fm.DecisionSP.value = tConfirm[0][0];
        fm.RemarkSP.value = tConfirm[0][1];
        //fm.ExamNoPassReason.value = tConfirm[0][2];
        //fm.ExamNoPassDesc.value = tConfirm[0][3];
        fm.ExamPer.value = tConfirm[0][4];
        fm.ExamDate.value = tConfirm[0][5];
        showOneCodeName('llexamconclusion','DecisionSP','DecisionSPName');
       // showOneCodeName('llexamnopassreason','ExamNoPassReason','ExamNoPassReasonName');
        
    }
}    

//�᰸��Ϣ    
function queryEndCase()
{
	  var rptNo8 = fm.ClmNo.value;
	  
	  	  	 var sqlid11="ClaimDetailQuerySql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName("uw.ClaimDetailQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
		mySql11.addSubPara(rptNo8);//ָ������Ĳ���
	    var strSql=mySql11.getString();	
	  
//    var strSql = " select a.realpay,(select b.username from llclaimuser b where b.usercode = a.operator),a.endcasedate from llclaim a where "
//               + " ClmNo = '" + rptNo8 + "'";

    var tEndCase = easyExecSql(strSql);
    if (tEndCase != null)
    {
        fm.ClmEndRealPay.value = "��" + tEndCase[0][0];
        fm.ClmEndPer.value = tEndCase[0][1];
        fm.ClmEndDate.value = tEndCase[0][2];       
    }
}    

//����ʵ����ѯ
function queryConfDate()
{
	  var rptNo9 = fm.ClmNo.value;
	  
	    var sqlid12="ClaimDetailQuerySql12";
		var mySql12=new SqlClass();
		mySql12.setResourceName("uw.ClaimDetailQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
		mySql12.addSubPara(rptNo9);//ָ������Ĳ���
	    var strSql=mySql12.getString();	
	  
//    var strSql = " select count(1) from ljaget a where a.enteraccdate is null "
//               + " and a.otherno = '" + rptNo9 + "'";

    var tConfDate = easyExecSql(strSql);
    if (tConfDate == "0")
    {
        fm.confGetMoney.value = "��";
    }
    else
    {
        fm.confGetMoney.value = "��";
    }
}
    
 //�õ�0��icd10��
function saveIcdValue()
{
    fm.ICDCode.value = fm.AccResult1.value;
}   
    
//��ѯ���ս��
function queryResult(tCode,tName)
{
		 var sqlid13="ClaimDetailQuerySql13";
		var mySql13=new SqlClass();
		mySql13.setResourceName("uw.ClaimDetailQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql13.setSqlId(sqlid13);//ָ��ʹ�õ�Sql��id
		mySql13.addSubPara(document.all(tCode).value);//ָ������Ĳ���
	    var strSql=mySql13.getString();	
	
//    var strSql = " select ICDName from LDDisease where "
//               + " ICDCode = '" + document.all(tCode).value + "'";
    var tICDName = easyExecSql(strSql);
    if (tICDName)
    {
        document.all(tName).value = tICDName;
    }
}    
    
    
    
    
    
    
    
    
    
    