var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var mySql = new SqlClass();
//�ɿͻ���ѯ��LLLdPersonQuery.js�����ص�����¼ʱ����
function afterQueryLL(arr)
{
  
    fm.customerNo.value   = arr[0];//Ͷ���˿ͻ���
    fm.customerName.value = arr[1];//Ͷ��������
    fm.customerSex.value  = arr[2];//�Ա�
    fm.customerAge.value  = arr[3];//���
    fm.IDNo.value         = arr[6];//ID��
    fm.IDTypeName.value   = arr[5];//֤����������
    fm.IDType.value       = arr[7];//֤������
    showOneCodeName('sex','customerSex','SexName');//�Ա�
    fm.QueryCont2.disabled = false; //�������ݺ󣬽�QueryCont2����Ϊ����
    fm.QueryCont3.disabled = false; //�������ݺ󣬽�QueryCont3����Ϊ����
    //��ʼ��¼����
    //fm.AccidentDate2.value = "";
    for(var j=0;j<fm.claimType.length;j++)
    {
        fm.claimType[j].checked = false;
    }

}

function afterQuery(mRgtNo){
if(mRgtNo!=''){
document.all('RptNo').value = mRgtNo;
queryRegister();
}
}

//�򿪷������
function showBeginInq()
{
    //var JustStateSql="select COUNT(*) from lwmission where activityid in ('0000009125','0000009145','0000009165','0000009175') and missionprop1='"+fm.RptNo.value+"'";
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
mySql.setSqlId("LLGrpBatchAuditSql1");
mySql.addSubPara(fm.RptNo.value ); 
    var JustStateCount=easyExecSql(mySql.getString());
    if(parseInt(JustStateCount)>0)
    {      				
    		alert("�ð����Ѿ�������飬�벻Ҫ�ظ�����!");
    		return;
    }       
    var strUrl="LLInqApplyMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&custName="+fm.customerName.value+"&initPhase=02";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�鿴����
function showQueryInq()
{
  //---------------------------------------
  //�жϸ��ⰸ�Ƿ���ڵ���
  //---------------------------------------
    /*var strSQL = "select count(1) from LLInqConclusion where "
                + "ClmNo = '" + fm.RptNo.value+"'";*/
     mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
mySql.setSqlId("LLGrpBatchAuditSql2");
mySql.addSubPara(fm.RptNo.value ); 
    var tCount = easyExecSql(mySql.getString());
    if (tCount == "0")
    {
        alert("���ⰸ��û�е�����Ϣ��");
        return;
    }
    var strUrl="LLInqQueryMain.jsp?claimNo="+fm.RptNo.value;
      window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//������ѯ
function queryRegister()
{
    
    var rptNo = document.all('RptNo').value;
    var tFlag = fm.Flag.value;
    //alert("rptNo="+rptNo);
    getGrpstandpay();
    if(rptNo == "")
    {
      
     fm.updatebutton.disabled = true;
     fm.QueryCont2.disabled = true;
     fm.QueryCont3.disabled = true;
     //fm.QueryReport.disabled = true;
     
     fm.dutySet.disabled = true;
     fm.QuerydutySet.disabled = true;
     fm.addUpdate.disabled = true;
     fm.simpleClaim.disabled = true;
     divSimpleClaim2.style.display= "";
     divSimpleClaim3.style.display= "none";
    }else{
      
     fm.updatebutton.disabled = false;
     fm.deletebutton.disabled = false;
     fm.QueryCont2.disabled = false;
     fm.QueryCont3.disabled = false;
     //fm.QueryReport.disabled = false;
     if(tFlag =="FROMSIMALL")
        fm.dutySet.disabled = true;
     else
         fm.dutySet.disabled = false;
     fm.QuerydutySet.disabled = false;
     fm.addUpdate.disabled = false;
     fm.simpleClaim.disabled = false;
    }
      
    //�����¼��š������¹ʷ�������(һ��)
    /*var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";*/
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
mySql.setSqlId("LLGrpBatchAuditSql3");
mySql.addSubPara(rptNo); 
    var AccNo = easyExecSql(mySql.getString());
    //���������ż�����������Ϣ(һ��)
    /*var strSQL2 = "select AppntNo,GrpName,GrpContNo,RgtNo,Peoples2,AppPeoples,RgtantName,AccidentReason,RgtConclusion,RgtClass,clmState,RiskCode,Operator from llregister where "
                + "rgtno = '"+rptNo+"'";*/
    /*var strSQL2 = "select AppntNo,GrpName,GrpContNo,RptNo,Peoples2,'','',AccidentReason,rgtflag,RgtClass,AvaliReason,RiskCode,Operator from llreport where "
                + "rptno = '"+rptNo+"'";   //prompt("",strSQL2); */
    /*var strSQL2 = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,(select codename from ldcode where codetype='relation' and code=Relation),"
    			+" RptMode,AccidentSite,AccidentReason,(select codename from ldcode where codetype='lloccurreason' and code=AccidentReason),"
    			+" RptDate,MngCom,Operator,RgtClass,GRPCONTNO,APPNTNO,PEOPLES2,GRPNAME,RiskCode from LLReport where "
                + "RptNo = '"+rptNo+"'";   */
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
mySql.setSqlId("LLGrpBatchAuditSql4");
mySql.addSubPara(rptNo);       
    var RptContent = easyExecSql(mySql.getString());//prompt("",strSQL2);
    //����ҳ������
    
    //
   // var strSQL5 = "select accepteddate from llregister where " + "rgtno = '"+rptNo+"'";
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
mySql.setSqlId("LLGrpBatchAuditSql5");
mySql.addSubPara(rptNo);  
    var RptContent5 = easyExecSql(mySql.getString());
    if(RptContent5!=null)
    {
    	fm.AcceptedDate.value=RptContent5[0][0];
    }
    
    if(AccNo!=null)
    {
        fm.AccNo.value = AccNo[0][0];
        fm.AccidentDate.value = AccNo[0][1];
    }
    
   /*if(RptContent!=null)
    {
        fm.GrpCustomerNo.value = RptContent[0][0];
        fm.GrpName.value = RptContent[0][1];
        fm.GrpContNo.value = RptContent[0][2];
        fm.RptNo.value = RptContent[0][3];
        fm.Peoples.value = RptContent[0][4];
        fm.customerName.value = RptContent[0][6];
        fm.occurReason.value = RptContent[0][7];
        fm.clmState.value = RptContent[0][8];
        fm.RgtClass.value = RptContent[0][9];
        //fm.clmState.value = RptContent[0][10];
        fm.Polno.value = RptContent[0][11];
        
       var ttoperator = new Array;
       var ttsql="select operator from llregister where rgtno='"+fm.RptNo.value+"' ";    	         
       ttoperator = easyExecSql(ttsql);        	      	  
        	        	      	                
        if(ttoperator==null||ttoperator=="")
        {
           fm.tOperator.value = RptContent[0][12];  
        }else
        	{
             fm.tOperator.value = ttoperator; //�˴�ȡ��������Ա�����ڶ�Ȩ�޵�У��        		
        	}         
        

        showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
    }*/
    if(RptContent!=null){
    fm.RptNo.value = RptContent[0][0];
    fm.RptorName.value = RptContent[0][1];
    fm.RptorPhone.value = RptContent[0][2];
    fm.RptorAddress.value = RptContent[0][3];
    fm.Relation.value = RptContent[0][4];//���������¹��˹�ϵ 
    fm.RelationName.value = RptContent[0][5];
//    fm.RptMode.value = RptContent[0][6];
    fm.AccidentSite.value = RptContent[0][7];
    fm.occurReason.value = RptContent[0][8];//����ԭ��
    fm.ReasonName.value = RptContent[0][9];
    fm.RptDate.value = RptContent[0][10];
    fm.MngCom.value = RptContent[0][11];
    fm.OOperator.value = RptContent[0][12];//alert("RptContent[0][10]: "+RptContent[0][10]);

    fm.GrpContNo.value = RptContent[0][14];
    fm.GrpCustomerNo.value = RptContent[0][15];
    fm.Peoples.value = RptContent[0][16];
    fm.GrpName.value = RptContent[0][17];
    fm.Polno.value = RptContent[0][18];
}
  //alert(document.all('isNew').value);   
  if (document.all('isNew').value =='0')   //�ѱ�����û����
   {               
     fm.addbutton.disabled = false;
     fm.updatebutton.disabled = true;
     fm.deletebutton.disabled = true;
     //fm.addbutton2.disabled = true;
     //fm.updateFeebutton.disabled = true;
     //fm.deleteFeebutton.disabled = true;
     fm.DiskInput.disabled = true;
     //fm.QueryReport.disabled = true;
     fm.dutySet.disabled = true;
     fm.QuerydutySet.disabled = true;   
     fm.simpleClaim.disabled = true; 
     divSimpleClaim2.style.display= "";
     divSimpleClaim3.style.display= "none";      
   }else if (document.all('isNew').value =='1')      //��������û����
   {          
     fm.addbutton.disabled = true;
     fm.updatebutton.disabled = false;
     fm.deletebutton.disabled = false;
     //fm.addbutton2.disabled = false;
     //fm.updateFeebutton.disabled = false;
     //fm.deleteFeebutton.disabled = false;
     //fm.QueryReport.disabled = false;
     fm.dutySet.disabled = false;
     fm.QuerydutySet.disabled = false;   
     fm.simpleClaim.disabled = false; 
     divSimpleClaim2.style.display= "";
     divSimpleClaim3.style.display= "none";         
   } else if (document.all('isNew').value =='2')      //����
   {
   	//var strSQL3="select auditconclusion from LLClaimUWMain where clmno='"+rptNo+"'";
   	mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
mySql.setSqlId("LLGrpBatchAuditSql6");
mySql.addSubPara(rptNo); 
   	var SimpleConclusion2 = easyExecSql(mySql.getString());//prompt("",strSQL3);
   	if(SimpleConclusion2!=null&&SimpleConclusion2!="null"){
   		fm.SimpleConclusion2.value=SimpleConclusion2;
   		showOneCodeName('llexamconclusion2','SimpleConclusion2','SimpleConclusion2Name');//��˽���
   	}
   	//alert("SimpleConclusion2:  "+SimpleConclusion2);
     fm.QueryPerson.disabled = true;
     fm.addbutton.disabled = true;
     fm.updatebutton.disabled = true;
     fm.deletebutton.disabled = true;
     fm.DiskInput.disabled = true;
     fm.DiskOutput.disabled = true;
     fm.QueryCont2.disabled = true;
     fm.QueryCont3.disabled = true;
     //fm.addbutton2.disabled = true;
     //fm.updateFeebutton.disabled = true;
     //fm.deleteFeebutton.disabled = true;
     //fm.QueryReport.disabled = true;
     fm.dutySet.disabled = true;
     fm.QuerydutySet.disabled = false;   
     divSimpleClaim2.style.display= "none";
     divSimpleClaim3.style.display= "";
     divLLAudit.style.display = "";
//     fm.ConclusionUp.disabled = true;          
     //fm.ConclusionSave.disabled = true;
//     fm.ConclusionBack.disabled = true;
     
     if( fm.SimpleConclusion2.value == '0'||fm.SimpleConclusion2.value == '1'||fm.SimpleConclusion2.value == '4'){
      	fm.ConclusionSave.disabled = false;
     }else{
     	//fm.ConclusionSave.disabled = true;
     }
     
     if( fm.SimpleConclusion2.value == '5'){//��������      
//      fm.ConclusionBack.disabled = false;
     }else{
//      fm.ConclusionBack.disabled = true;
     }
     
   }else if (document.all('isNew').value =="" || document.all('isNew').value ==null)
   	{   	 
   	 fm.QueryPerson.disabled = false;
     fm.addbutton.disabled = false;
     fm.updatebutton.disabled = false;
     fm.deletebutton.disabled = false;
     fm.QueryCont2.disabled = false;
     fm.QueryCont3.disabled = false;
     //fm.addbutton2.disabled = false;
     //fm.updateFeebutton.disabled = false;
     //fm.deleteFeebutton.disabled = false;
     //fm.QueryReport.disabled = false;
     fm.dutySet.disabled = true;
     fm.QuerydutySet.disabled = false;   
     divSimpleClaim2.style.display= "";
     divSimpleClaim3.style.display= "none";  
//     fm.ConclusionUp.disabled = false;   
     fm.ConclusionSave.disabled = false;
//     fm.ConclusionBack.disabled = false; 
   	}           
     
   
    
  /*  if(RptContent!=null)
    {
        fm.GrpCustomerNo.value = RptContent[0][0];
        fm.GrpName.value = RptContent[0][1];
        fm.GrpContNo.value = RptContent[0][2];
        fm.RptNo.value = RptContent[0][3];
        fm.Peoples.value = RptContent[0][4];
        fm.customerName.value = RptContent[0][6];
        fm.occurReason.value = RptContent[0][7];
        fm.clmState.value = RptContent[0][8];
        fm.RgtClass.value = RptContent[0][9];
        fm.clmState.value = RptContent[0][10];
        fm.Polno.value = RptContent[0][11];
        fm.tOperator.value = RptContent[0][12];

        showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
    
        fm.clmState2.value = RptContent[0][10]
        //�᰸��������
        if( fm.clmState2.value == '60' )
        {
             fm.QueryPerson.disabled = true;
             fm.addbutton.disabled = true;
             fm.Inputbutton.disabled = true;
             fm.addbutton.disabled = true;
             fm.addbutton2.disabled = true;
             fm.updatebutton.disabled = true;
             fm.deletebutton.disabled = true;
             fm.QueryCont2.disabled = true;
             fm.QueryCont3.disabled = true;
             fm.QueryReport.disabled = true;
             fm.dutySet.disabled = true;
             fm.addUpdate.disabled = true;
             fm.simpleClaim.disabled = true;
             fm.SimpleConclusion.value = '0';
             showOneCodeName('llexamconclusion','SimpleConclusion','SimpleConclusionName');//����ԭ��
             divSimpleClaim2.style.display= "none";
             divSimpleClaim3.style.display= "";
         }else if( fm.clmState2.value == '40')//���˰�������
         {
             fm.QueryPerson.disabled = true;
             fm.addbutton.disabled = true;
             fm.addbutton.disabled = true;
             fm.addbutton2.disabled = true;
             fm.updatebutton.disabled = true;
             fm.deletebutton.disabled = true;
             fm.QueryCont2.disabled = true;
             fm.QueryCont3.disabled = true;
             fm.QueryReport.disabled = true;
             fm.dutySet.disabled = true;
             fm.addUpdate.disabled = true;
            if(fm.Flag.value == '2')
            {
                 divSimpleClaim2.style.display= "none";
                 divSimpleClaim3.style.display= "";
            }
            else
            {
                 divSimpleClaim2.style.display= "";
                 divSimpleClaim3.style.display= "none";
            }
        }
        else
        {
             divSimpleClaim2.style.display= "";
             divSimpleClaim3.style.display= "none";
        }

    } */
  
   // var strSQL4 = "select count(*) CustomerNo from llsubreport where subrptno = '"+rptNo+"'";
    	mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
mySql.setSqlId("LLGrpBatchAuditSql7");
mySql.addSubPara(rptNo); 
    var CustomerNoCount = easyExecSql(mySql.getString());
    if(CustomerNoCount!=null)
    {
        //fm.PeopleNo.value = CustomerNoCount[0][0];
    }
    
   /* var strSQL5 = "select reasoncode from Llappclaimreason where "
                + "RgtNo = '"+rptNo+"'";
    var ReasonCode = easyExecSql(strSQL5);
    if (ReasonCode!=null||ReasonCode!="")
    {
      fm.occurReason.value=	ReasonCode[0][0].substring(0,1);
      showCodeName('occurReason','occurReason','ReasonName');      
    } */
    
    //****************************************************
    //����ҳ��Ȩ��
    //****************************************************
    fm.AccidentDate.readonly = true;
    fm.claimType.disabled=true;
    
    //�����ְ�������������Ϣ(����)
   /* var strSQL1 = "select count(*) from LDPerson where "
                + "CustomerNo in("
                + "select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";*/
 //     alert("rptNo"+rptNo);          
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
mySql.setSqlId("LLGrpBatchAuditSql8");
mySql.addSubPara(rptNo); 
    var count = easyExecSql(mySql.getString());
   
    if(count > 0)
    {
       /* var strSQL3 = "select a.CustomerNo,a.Name,a.Sex,a.Birthday,"
                    + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                	+ " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end) as �Ƿ����籣��־, "
                    +"(select codename from ldcode where "
                    + " codetype = 'idtype' and code = a.IDType),a.IDNo,"
                    + " a.IDType, b.accdate,(select case count(*) when 0 then '��' else '��' end from es_doc_main where doccode='"+ rptNo +"' and printcode=a.customerno) " 
                    + " from LDPerson a,llsubreport b where "
                    + " a.CustomerNo=b.CustomerNo "                 
                    + " and b.subrptno = '"+ rptNo +"' order by to_number(b.condoleflag)"; */
       mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
		mySql.setSqlId("LLGrpBatchAuditSql9");
		mySql.addSubPara(rptNo);   
		mySql.addSubPara(rptNo);      
       /*var strSQL3 = "select a.CustomerNo,a.Name,a.Sex,a.Birthday,"
                    + " a.VIPValue,(select codename from ldcode where "
                    + " codetype = 'idtype' and code = a.IDType),a.IDNo,"
                    + " a.IDType, b.accdate from LDPerson a,LLCase b where "
                    + " a.CustomerNo=b.CustomerNo "                 
                    + " and b.CaseNo = '"+ rptNo +"' order by lpad(b.seconduwflag,4,'0')";                       
       */           // prompt("",strSQL3) ;
        turnPage3.queryModal(mySql.getString(),SubReportGrid);
        //prompt("��ѯ������",strSQL3);
        if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
        {
            SubReportGridClick(0);
        }
    }
   
  //��ѯ��˽���
    queryAudit();
}

//ѡ��SubReportGrid��Ӧ�¼�,tPhase=0Ϊ��ʼ��ʱ����
function SubReportGridClick(tPhase)
{
  //********************************************Beg
  //�ÿ���ر�
  //********************************************
  fm.customerName.value = "";
  fm.customerAge.value = "";
  fm.customerSex.value = "";
  fm.SexName.value = "";
  //fm.AccidentDate2.value = "";
  fm.claimType.value = "";
  fm.IDType.value = "";
  fm.IDTypeName.value = "";
  fm.IDNo.value = "";
  fm.QueryCont3.disabled = false;
  fm.QueryCont2.disabled = false;
  fm.dutySet.disabled = false;
  //���������ÿ�
    for (var j = 0;j < fm.claimType.length; j++)
    {
        fm.claimType[j].checked = false;
    }
    //********************************************End
	
    //ȡ������
    var i = "";
    if (tPhase == "0")
    {
        i = 1;
    }
    else
    {
        i = SubReportGrid.getSelNo();
    }
    if (i != '0')
    {
        i = i - 1;
        fm.customerNo.value = SubReportGrid.getRowColData(i,1);
        fm.customerName.value = SubReportGrid.getRowColData(i,2);
        fm.customerSex.value = SubReportGrid.getRowColData(i,3);
        fm.customerAge.value = calAge(SubReportGrid.getRowColData(i,4));
        fm.IDTypeName.value = SubReportGrid.getRowColData(i,7);
        fm.IDNo.value = SubReportGrid.getRowColData(i,8);
        fm.IDType.value = SubReportGrid.getRowColData(i,9);
        showOneCodeName('sex','customerSex','SexName');//�Ա�
        fm.AccidentDate.value = SubReportGrid.getRowColData(i,10);//��������
    }

    //��ѯ�����������
    var tClaimType = new Array;
  /*  var strSQL1 = "select ReasonCode from llreportreason where "
                + " rpNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";*/
   mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
		mySql.setSqlId("LLGrpBatchAuditSql10");
		mySql.addSubPara(fm.RptNo.value);   
		mySql.addSubPara(fm.customerNo.value);    
    tClaimType = easyExecSql(mySql.getString());
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
                if(fm.claimType[j].value == tClaim)
                {
                    fm.claimType[j].checked = true;
                }
            }
        }
    }
//����ѡ�еĳ�������ʾ��ص�������
afterMatchDutyPayQuery();
}

//ѡ��PolDutyCodeGrid��Ӧ�¼�
function PolDutyCodeGridClick() {
	//alert(document.all('Flag').value);
    if(document.all('Flag').value != '2'){
	    //��ձ�
	    fm.GiveType.value = "";//�⸶����
	    fm.RealPay.value = "";
	    fm.AdjReason.value = "";//����ԭ��
	    fm.AdjReasonName.value = "";//
	    fm.AdjRemark.value = "";//������ע
	    fm.GiveTypeDesc.value = "";//�ܸ�ԭ�����
	    fm.GiveReason.value = "";//�ܸ�����
	    fm.SpecialRemark.value = "";//���ⱸע
	    var tRiskCode = '';
	    //���ð�ť
	    if(fm.clmState2.value == '60'){
	    fm.addUpdate.disabled = true;//����޸�
	    }else{
	    fm.addUpdate.disabled = false;//����޸�
	    }
	    //�õ�mulline��Ϣ
	    var i = PolDutyCodeGrid.getSelNo();
	    if (i != '0')
	    {
	        i = i - 1;
	        fm.tPolNo.value = PolDutyCodeGrid.getRowColData(i,1);//������
	        fm.GiveType.value = PolDutyCodeGrid.getRowColData(i,13);//�⸶����
	        fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,21);
	        fm.AdjReason.value = PolDutyCodeGrid.getRowColData(i,22);//����ԭ��
	        fm.AdjReasonName.value = PolDutyCodeGrid.getRowColData(i,23);//
	        fm.AdjRemark.value = PolDutyCodeGrid.getRowColData(i,24);//������ע
	        fm.GiveTypeDesc.value = PolDutyCodeGrid.getRowColData(i,15);//�ܸ�ԭ�����
	        fm.GiveReason.value = PolDutyCodeGrid.getRowColData(i,17);//�ܸ�����
	        fm.SpecialRemark.value = PolDutyCodeGrid.getRowColData(i,18);//���ⱸע
	        showOneCodeName('llpayconclusion','GiveType','GiveTypeName');
	        showOneCodeName('llprotestreason','GiveTypeDesc','GiveTypeDescName');
	        tRiskCode = PolDutyCodeGrid.getRowColData(i,3);//���ֱ���
	    }
    	//��	���������жϣ��ǵĲ������޸�������
	    var sql1 = " select insuaccflag From lmrisk where riskcode = '"+tRiskCode+"'";
	     mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
		mySql.setSqlId("LLGrpBatchAuditSql11");
		mySql.addSubPara(tRiskCode);   
	    var tInsuaccFlag = easyExecSql(mySql.getString());
	    //���������жϣ��ǵĲ������޸�������
	   // var sql2 = " select riskperiod from lmriskapp where riskcode = '"+tRiskCode+"'";
	     mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
		mySql.setSqlId("LLGrpBatchAuditSql12");
			mySql.addSubPara(tRiskCode);   
	    var tRiskPeriod = easyExecSql(mySql.getString());
	    //��ʾ���ز�divBaseUnit5.style.display= "";
//	    if(tInsuaccFlag != 'Y' && tRiskPeriod != 'L' && document.all('isNew').value =='1'){
		    divBaseUnit5.style.display= "";
		    choiseGiveTypeType();
//	    }else{
//	    	divBaseUnit5.style.display= "none";
//	    }
    }
}

//�Ա��������޸�
function AddUpdate()
{
    if(PolDutyCodeGrid.getSelNo() <= 0)
    {
        alert("����ѡ��Ҫ����ı�����Ϣ,��ִ�д˲�����");
        return;
    }
        
    checkAdjMoney();//��鱣�������
    
    var i = PolDutyCodeGrid.getSelNo() - 1;//�õ�������

    PolDutyCodeGrid.setRowColData(i,13,fm.GiveType.value);//�⸶����
    PolDutyCodeGrid.setRowColData(i,15,fm.GiveTypeDesc.value);//�ܸ�ԭ�����
    PolDutyCodeGrid.setRowColData(i,17,fm.GiveReason.value);//�ܸ�����
    PolDutyCodeGrid.setRowColData(i,18,fm.SpecialRemark.value);//���ⱸע
    PolDutyCodeGrid.setRowColData(i,21,fm.RealPay.value);
    PolDutyCodeGrid.setRowColData(i,22,fm.AdjReason.value);//����ԭ��
    PolDutyCodeGrid.setRowColData(i,23,fm.AdjReasonName.value);//
    PolDutyCodeGrid.setRowColData(i,24,fm.AdjRemark.value);//������ע
    if(fm.GiveType.value == 0){
    	PolDutyCodeGrid.setRowColData(i,14,"����");//�⸶��������
    }else if(fm.GiveType.value == 1){
    	PolDutyCodeGrid.setRowColData(i,14,"�ܸ�");//�⸶��������
    }else if(fm.GiveType.value == 2){
    	PolDutyCodeGrid.setRowColData(i,14,"ͨ���⸶");//�⸶��������
    }else if(fm.GiveType.value == 3){
    	PolDutyCodeGrid.setRowColData(i,14,"Э���⸶");//�⸶��������
    }
    
    fm.saveUpdate.disabled = false;//�����޸�
}

//�Ա����޸ı��浽��̨
function SaveUpdate() {
	/*
   if (fm.GiveType.value==2||fm.GiveType.value==3)
    {      
      var tsql="select distinct b.appendmax from llclaimuser a ,llclaimpopedom b where a.checklevel=b.popedomlevel and a.usercode='"+document.all('Operator').value+"' ";
      var tappndmax=new Array;
      tappndmax=easyExecSql(tsql);
      
      if (tappndmax<document.all('RealPay').value) 
      {
      	 alert("����ͨ�ڡ�Э���⸶Ȩ�޲�����");
      	 return false;
      }     	
    }else if (fm.GiveType.value==1)
    {
      var tsql="select checklevel from llclaimuser where usercode='"+document.all('Operator').value+"' ";	
      var tchecklevel=new Array;
      tchecklevel=easyExecSql(tsql);
      if (tchecklevel=="A"||tchecklevel=="B1"||tchecklevel=="B2"||tchecklevel=="B3")
      {
        alert("��û�оܸ�Ȩ�ޣ�");	
        return false;
      }    
    }    
    //��Ӿܸ�У��*/
    //�ڸ���ʱУ��
    
  var tClaimNo = fm.RptNo.value;
	  
	//У�鱣�����⸶���뱾���⸶��֮�Ͳ��ܴ����˻����
	//tSQL = "select distinct polno from llclaimpolicy where clmno='"+tClaimNo+"' and riskcode in (select riskcode from lmrisk where insuaccflag='Y')";
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql13");
	mySql.addSubPara(tClaimNo); 
	arr = easyExecSql(mySql.getString());
	
	//tSQL1 = "select count (distinct polno) from llclaimdetail where clmno='"+tClaimNo+"' and riskcode in (select riskcode from lmrisk where insuaccflag='Y')";
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql14");
	mySql.addSubPara(tClaimNo); 
	arr1 = easyExecSql(mySql.getString());


 if(arr1[0][0] > 0)
 {
	for (var t =0;t<arr1[0];t++)
	 {

	var j=0;

	for(var k =0;k<PolDutyCodeGrid.mulLineCount;k++)
	 {
    var tPolNo=PolDutyCodeGrid.getRowColData(k,1);

    var tPolNo1=arr[t][0];

	     if (tPolNo == tPolNo1)
	        {
	    	     	var tRealM  = parseFloat(PolDutyCodeGrid.getRowColData(k,21));//�������
	    	 			j=j+tRealM;
	        }
	       
	  }
	  


	// var strSQL2 = "select sum(money) from lcinsureacctrace where polno='"+arr[t]+"'";
	 mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql15");
	mySql.addSubPara(arr[t]); 
	 arr2 = easyExecSql(mySql.getString());


	// var strSQL3 = "select nvl(sum(b.RealPay),0) from LLClaim a,LLClaimDetail b where a.ClmNo = b.ClmNo and a.ClmState in ('50') and a.ClmNo <>'"+tClaimNo+"' and b.GiveType != '1' and b.PolNo ='"+arr[t]+"'";
	 mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql16");
	mySql.addSubPara(tClaimNo); 
	mySql.addSubPara(arr[t]); 
	 arr3 = easyExecSql(mySql.getString());

	 var p = pointTwo(j) + pointTwo(arr3[0][0]);
	 var u=arr2[0];

	 var intev = (pointTwo(p))-(pointTwo(u));

	if(intev>0)
	    {
		    alert("������������������֮�ͳ����ʻ��������!");
			  return false;
	    }

	 }
	
 }
    
    fm.action = './LLClaimAuditGiveTypeSave.jsp';
    fm.fmtransact.value = "UPDATE";
    submitForm();
    fm.saveUpdate.disabled = true;//�����޸�
}

//ѡ���⸶����
function choiseGiveTypeType()
{
    if (fm.GiveType.value == '0')
  {
        divGiveTypeUnit1.style.display= "";
        divGiveTypeUnit2.style.display= "none";
        divGiveTypeUnit3.style.display= "none";
    }
    else if (fm.GiveType.value == '1')
    {
        divGiveTypeUnit1.style.display= "none";
        divGiveTypeUnit2.style.display= "";
        divGiveTypeUnit3.style.display= "none";
    }
    else if (fm.GiveType.value == '2'||fm.GiveType.value == '3')
    {
        divGiveTypeUnit1.style.display= "";
        divGiveTypeUnit2.style.display= "none";
        divGiveTypeUnit3.style.display= "none";        
    }
}

//���б�������ƥ��
function showMatchDutyPay()
{    
/*var strSQL = "select count(*) from lcinsureaccclass where accascription = '0'"
                + " and grpcontno = '" + fm.GrpContNo.value+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql17");
	mySql.addSubPara(fm.GrpContNo.value); 
    var tCount = easyExecSql(mySql.getString());//alert(tCount); return false;
    if(tCount > 0){
       if(confirm("��ȷ���Ѿ����˹���?"))
      {

    mOperate="MATCH||INSERT";
    var showStr="���ڽ��б�������ƥ������������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    fm.hideOperate.value=mOperate;
    fm.action = "./LLClaimRegisterMatchCalSave.jsp";
    fm.target="fraSubmit";
    document.getElementById("fm").submit(); //�ύ

      }else{
        alert("�뵽��ȫ������!");
        return false;
      }
    }else{

    mOperate="MATCH||INSERT";
    var showStr="���ڽ��б�������ƥ������������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    fm.hideOperate.value=mOperate;
    fm.action = "./LLClaimRegisterMatchCalSave.jsp";
    fm.target="fraSubmit";
    document.getElementById("fm").submit(); //�ύ

    }
}

//ƥ���Ĳ�ѯ

function afterMatchDutyPayQuery()
{
    var tSql;
    var arr;

    var tClaimNo = fm.RptNo.value;         //�ⰸ��
    var tCaseNo = fm.RptNo.value;          //�ְ���
    var tCustNo = fm.customerNo.value;          //�ͻ���

    //��ѯ�����ⰸ�Ľ��
    /*tSql = " select a.StandPay ,a.BeforePay,a.BalancePay,a.RealPay,a.DeclinePay,"
       +" '','','' "
       +" from LLClaim a  where 1=1 "
       +" and a.caseno = '"+tClaimNo+"'"
       ;*/
    //prompt("",tSql);
   mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql18");
	mySql.addSubPara(tClaimNo); 
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,ClaimGrid);
    }
    else
    {
        initClaimGrid();
    }
    
        //��ѯLLClaimPolicy,��ѯ�����������Ͳ������Ϣ
   /* tSql = " select a.ContNo,a.PolNo,a.GetDutyKind,"
       +" a.CValiDate,b.PaytoDate,"
       +" a.RiskCode,(select RiskName from LMRisk d where d.RiskCode = a.RiskCode), "
       +" a.RealPay "
       +" from LLClaimPolicy a ,LCPol b where 1=1 "
       +" and a.PolNo = b.PolNo"       
       +" and a.ClmNo = '"+tClaimNo+"'"
       +" and a.insuredno= '"+tCustNo+"'";*/
       mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql19");
	mySql.addSubPara(tClaimNo); 
	mySql.addSubPara(tCustNo); 
	//prompt("����������Ϣ",tSql);
    arr = easyExecSql(  mySql.getString());
    if (arr)
    {
        displayMultiline(arr,PolDutyKindGrid);
    }
    else
    {
        initPolDutyKindGrid();
    }
    
    //��ѯLLClaimDutyKind�������ⰸ�������ͽ��в���
   /* tSql = " select a.GetDutyKind ,"
       +" (select c.codename from ldcode c where c.codetype = 'llclaimtype' and c.code=a.GetDutyKind),"
       +" a.TabFeeMoney,a.SelfAmnt,a.StandPay,a.SocPay,a.RealPay,RealPay "
       +" from LLClaimDutyKind a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"*/
    //prompt("��ѯLLClaimDutyKind�������ⰸ�������ͽ��в���",tSql);
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql20");
	mySql.addSubPara(tClaimNo); 
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,DutyKindGrid);
    }
    else
    {
        initDutyKindGrid();
    }
    
//alert(651);
    initPolDutyCodeGrid();
    //��ѯLLClaimDetail,��ѯ�����������ͱ���������Ϣ
    /*tSql = " select a.PolNo,a.GetDutyKind,a.RiskCode,a.GetDutyCode, "
        +" (select c.GetDutyName from LMDutyGetClm c where c.GetDutyKind = a.GetDutyKind and c.GetDutyCode = a.GetDutyCode),"
        +" b.GetStartDate,b.GetEndDate,"
        +" nvl(a.GracePeriod,0),"
        +" a.Amnt,a.YearBonus,a.EndBonus,"
        +" a.StandPay,a.GiveType, "
        +" (select e.codename from ldcode e where e.codetype = 'llpayconclusion2' and e.code=a.GiveType),"
        +" a.GiveReason,"
        +" (select f.codename from ldcode f where f.codetype = 'llprotestreason' and f.code=a.GiveReason),"
        +" a.GiveReasonDesc,a.SpecialRemark,"
        +" a.PrepaySum ,"//Ԥ�����
        +" '',"
        +" a.RealPay,a.AdjReason,"
        +" (select g.codename from ldcode g where g.codetype = 'lldutyadjreason' and g.code=a.AdjReason),"  
        +" a.AdjRemark, "
        +" a.PrepayFlag,case a.PrepayFlag when '0' then '��Ԥ��' when '1' then '��Ԥ��' end,"
        +" case a.PolSort when 'A' then '�б�ǰ' when 'B' then '����' when 'C' then '����' end ,"
        +" a.dutycode,a.CustomerNo,a.GrpContNo,a.ContNo, "
        +" (select name from ldperson where customerno=a.CustomerNo), "
        +"  (select codename from ldcode where codetype='polstate' and code in(select polstate from lcpol t where t.polno=a.PolNo))"
        +" from LLClaimDetail a,LCGet b  where 1=1 "
        +" and a.PolNo = b.PolNo"       
        +" and a.DutyCode = b.DutyCode"       
        +" and a.GetDutyCode = b.GetDutyCode" 
        +" and a.ClmNo = '"+tClaimNo+"'"   
        //+" and a.GiveType not in ('1')"
        +" and a.CustomerNo = '"+tCustNo+"'"
        ;*/
      mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql21");
	mySql.addSubPara(tClaimNo);   
	mySql.addSubPara(tCustNo);   
//    tSql = " select a.PolNo,a.GetDutyKind,a.RiskCode,a.GetDutyCode, "
//       +" (select c.GetDutyName from LMDutyGetRela c where c.GetDutyCode = a.GetDutyCode),"
//       +" b.GetStartDate,b.GetEndDate,"
//       +" nvl(a.GracePeriod,0)," //�������� + ������--+ a.GracePeriod
//       +" a.Amnt,a.YearBonus,a.EndBonus,"
//       +" a.StandPay,a.GiveType, "
//       +" (select e.codename from ldcode e where e.codetype = 'llpayconclusion2' and e.code=a.GiveType),"
//       +" a.GiveReason,"
//       +" (select f.codename from ldcode f where f.codetype = 'llprotestreason' and f.code=a.GiveReason),"
//       +" a.GiveReasonDesc,a.SpecialRemark,"
//       +" a.PrepaySum ,"//Ԥ�����
//       +" '',"
//       +" a.RealPay,a.AdjReason,"
//       +" (select g.codename from ldcode g where g.codetype = 'lldutyadjreason' and g.code=a.AdjReason),"
//       +" a.AdjRemark, "
//       +" a.PrepayFlag,case a.PrepayFlag when '0' then '��Ԥ��' when '1' then '��Ԥ��' end,"
//       +" case a.PolSort when 'A' then '�б�ǰ' when 'B' then '����' when 'C' then '����' end ,"
//       +" a.DutyCode,a.CustomerNo,a.GrpContNo,a.ContNo,"
//       //+" (select name from ldperson where customerno=a.CustomerNo), "
//       +"  (select codename from ldcode where codetype='polstate' and code in(select polstate from lcpol t where t.polno=a.PolNo))"
//       +" from LLClaimDetail a,LCGet b  where 1=1 "
//       +" and a.PolNo = b.PolNo"
//       +" and a.DutyCode = b.DutyCode"
//       +" and a.GetDutyCode = b.GetDutyCode"
//       +" and a.ClmNo = '"+tClaimNo+"'"
//       //+" and a.GiveType not in ('1')"
//       +" and a.CustomerNo = '"+tCustNo+"'"
    arr = easyExecSql( mySql.getString() );//prompt("",tSql);
    if (arr)
    {
        displayMultiline(arr,PolDutyCodeGrid);
    }
	//prompt("",tSql);
    /*tSql = "select a.customerno, b.FeeItemType,(select hospitalname from LLCommendHospital where hospitalcode=a.hospitalcode), a.HospitalCode, "
        +" a.HospitalGrade,a.MainFeeNo, (select icdname from lddisease where icdcode=b.diseasecode),b.diseasecode,"
        +" b.startdate,b.EndDate,decode(a.FeeType,'A','',b.DayCount),(select codename from ldcode where 1 = 1"
        +" and code=b.feeitemcode),b.FeeItemCode,b.Fee,"
        +" b.SelfAmnt,(select codename from ldcode where codetype = 'deductreason' and Code = b.AdjReason),b.AdjReason,b.AdjSum,b.SecurityAmnt,b.HospLineAmnt,b.AdjRemark,b.FeeDetailNo "
        +" from llfeemain a, LLCaseReceipt b,LLCase c "
        +" where a.clmno = '"+tClaimNo+"' and a.clmno = b.clmno and a.clmno=c.caseno and b.customerno=c.customerno"
        +" and a.customerno = b.customerno and a.mainfeeno = b.mainfeeno "
        +" and a.customerno = '"+tCustNo+"'"
        +" order by lpad(c.seconduwflag,4,'0')";*/
        //+" and a.hospitalcode = c.hospitalcode  and trim(b.diseasecode) = trim(d.icdcode) ";   
//        alert(697);
//        prompt("����",tSql);
   mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql22");
	mySql.addSubPara(tClaimNo);   
	mySql.addSubPara(tCustNo);   
   turnPage2.queryModal(mySql.getString(),MedFeeInHosInpGrid);
//alert(699);
   //�˲���Ϣ��ѯ
   initMedFeeCaseInfoGrid();       //��������(ԭ�˲�)
   /*var strSql = " select defotype,defograde,DefoName,defocode,DefoCodeName,deformityrate,appdeformityrate,realrate,clmno,caseno,serialno,customerno,JudgeOrganName,JudgeDate,adjremark"
       + " from LLCaseInfo where "
       + " ClmNo='" + tClaimNo + "'"
       + " and CustomerNo='"+tCustNo+"'"
       + " order by serialno";*/
   //��ʾ��������
   mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql23");
	mySql.addSubPara(tClaimNo);   
	mySql.addSubPara(tCustNo);   
   var arr = easyExecSql(mySql.getString());
   if (arr) {
   	displayMultiline(arr,MedFeeCaseInfoGrid);
   }
   //���ַ���
   initMedFeeOtherGrid();          //���ַ��� 
  /* var strSql = " select factortype,factorcode,factorname,factorvalue,SelfAmnt,clmno,caseno,serialno,customerno,UnitName,StartDate,EndDate,"
		   + " (select codename from ldcode where codetype='deductreason' and code=adjreason),adjreason,adjremark"
       + " from LLOtherFactor where "
       + " ClmNo='" + tClaimNo + "'"
       + " and CustomerNo='"+tCustNo+"'"
       + " and FeeItemType = 'T'"
       + " order by serialno";*/
  mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql24");
	mySql.addSubPara(tClaimNo);   
	mySql.addSubPara(tCustNo);      
   //prompt("������Ϣ��ѯ",strSql);
   //��ʾ��������
   var arr = easyExecSql(mySql.getString());
   if (arr) {
   	displayMultiline(arr,MedFeeOtherGrid);
   }
   //�籣����������
   initMedFeeThreeGrid();          //�籣����������
   /*var strSql = " select factortype,factorcode,factorname,factorvalue,clmno,caseno,serialno,customerno,UnitName,AdjRemark"
       + " from LLOtherFactor where "
       + " ClmNo='" + tClaimNo + "'"
       + " and CustomerNo='"+tCustNo+"'"
       + " and FeeItemType = 'D'"
       + " order by serialno";*/
   //��ʾ��������
   mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql25");
	mySql.addSubPara(tClaimNo);   
	mySql.addSubPara(tCustNo);   
   var arr = easyExecSql(mySql.getString());
   if (arr) {
   	displayMultiline(arr,MedFeeThreeGrid);
   }
//alert(699);

//��ѯLLClaimPolicy,��ѯ��ȫ��Ŀ��Ϣ      
    /* tSql = " select a.PolNo,(select RiskCode from LCPol where PolNo=a.PolNo),(select c.codename from ldcode c where c.codetype = 'edortypecode' and c.code=a.EdorType),"
     +" a.EdorValiDate,a.GetMoney "
     +" from LPEdorItem a where 1=1 and a.PolNo in(select PolNo from LLClaimPolicy where ClmNo = '"+tClaimNo+"') ";
    */mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql26");
	mySql.addSubPara(tClaimNo);    
    arr = easyExecSql( mySql.getString() );
  
    if (arr)
    {
        displayMultiline(arr,LPEdorItemGrid);
    }
    else
    {
        initLPEdorItemGrid();
    }

}


//������ѯ
//���ա��ͻ��š���LCpol�н��в�ѯ����ʾ�ÿͻ��ı�����ϸ
function showInsuredLCPol()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return;
    }
  var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
    var strUrl="LLLcContQueryMain.jsp?AppntNo="+tCustomerNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�����ⰸ��ѯ
function showOldInsuredCase()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return;
    }
    var tRgtNo = fm.RptNo.value;
  var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
    var strUrl="LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo+"&ClmNo="+tRgtNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//����ҽ�Ƶ�֤��Ϣ
function showLLMedicalFeeCal()
{
    var tClaimNo = fm.RptNo.value;         //�ⰸ��
    var tCaseNo = fm.RptNo.value;          //�ְ���
    var tCustNo = fm.customerNo.value;     //�ͻ���

    var strUrl="LLClaimRegMedFeeCalMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


//�����ύ
function submitForm()
{
    //�ύ����
    //showInfo.close();           
    var i = 0;
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    document.getElementById("fm").submit(); //�ύ
    tSaveFlag ="0";
}

//ƥ���Ķ���
function afterMatchDutyPay(FlagStr, content)
{
    showInfo.close();
    if (FlagStr == "FAIL" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        mOperate = '';
    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

    }
    afterMatchDutyPayQuery();

}

//���ذ�ť
function goToBack()
{
    var tFlag = fm.Flag.value;
    if(tFlag == "FROMSIMALL")
    {
       location.href="LLGrpClaimSimpleAllInput.jsp";
    }
    else if(tFlag == '2' || document.all('isNew').value =='2')
    {
        location.href="LLGrpClaimConfirmInput.jsp";
    }
    else
    {
        location.href="LLGrpClaimSimpleInput.jsp";
    }
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
    
    showInfo.close();
    if (FlagStr == "Fail")
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        goToBack();
    }
    queryRegister();
    tSaveFlag ="0";
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit2( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail")
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        queryRegister();        
    }
    if(fm.SimpleConclusion2.value != '4' && fm.SimpleConclusion2.value != '5')
    {
//      goToBack();
    }
    if(fm.SimpleConclusion2.value == '4'){
    	fm.ConclusionSave.disabled=false;
    }
    fm.simpleClaim2.disabled = false;
    tSaveFlag ="0";
}

//�ύ�����,������
function afterSubmit3( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

    }
      queryRegister();
      afterMatchDutyPayQuery();
      tSaveFlag ="0";
      
      //var tsql="select missionprop2 from lwmission where missionprop1='"+fm.RptNo.value+"' ";
     	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql27");
	mySql.addSubPara(fm.RptNo.value);    
      var tclmstate=easyExecSql(mySql.getString());
      if (tclmstate==10)         
      {
         fm.addbutton.disabled = false;
         fm.updatebutton.disabled = false;
         fm.deletebutton.disabled = false;
         //fm.addbutton2.disabled = true;
         //fm.updateFeebutton.disabled = true;
         //fm.deleteFeebutton.disabled = true;
         //fm.QueryReport.disabled = true;
         fm.dutySet.disabled = true;
         fm.QuerydutySet.disabled = true;   
         fm.simpleClaim.disabled = true; 
         fm.DiskInput.disabled = true;
       }else 
       {          
         fm.addbutton.disabled = true;
         fm.updatebutton.disabled = false;
         fm.deletebutton.disabled = false;
         //fm.addbutton2.disabled = false;
         //fm.updateFeebutton.disabled = false;
         //fm.deleteFeebutton.disabled = false;
         //fm.QueryReport.disabled = false;
         fm.dutySet.disabled = false;
         fm.QuerydutySet.disabled = false;   
         fm.simpleClaim.disabled = false;
         fm.DiskInput.disabled = false;  
       }    	
      
      
}


//�����˲�ѯ
function ClientQuery()
{
    window.open("LLLdPersonQueryInput.jsp","",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open("LLLdPersonQueryInput.jsp");

}

//�õ�0��icd10��
function saveIcdValue()
{
    fm.ICDCode.value = fm.AccResult1.value;
}


//������װ���
function confirmClick()
{
  //���ǿ� 
  if (fm.Flag.value != '2' && (fm.SimpleConclusion.value == "" || fm.SimpleConclusion.value == null))
  {
      alert("����д���װ������ۣ�");
      return;
  }
  //���ǿ�
  if (fm.Flag.value == '2'&&(fm.SimpleConclusion2.value == "" || fm.SimpleConclusion2.value == null))
  {
      alert("����д���װ������˽��ۣ�");
      return;
  }
  //ƥ�䲢�����ж�
  var i = PolDutyCodeGrid.mulLineCount;
  if(i == '0')
  {
      alert("����ƥ�䲢���㣡");
      return;
  }
  
    //����Ƿ���ɨ��� liuyu-20070827
    /*var ssql="select count(*) from es_doc_main where doccode='"+fm.RptNo.value+"' ";
    var scancount=easyExecSql(ssql);
    if (scancount==0)
    {
        alert("���ⰸ��û��ɨ�����Ϣ����������ȷ�ϣ�");
        return;    	
    }  */
  
    //У���⸶������������Ƿ����  liuyu-2008-2-29
    //var accSql="select nvl(sum(pay),0) from llbalance where clmno='"+fm.RptNo.value+"' ";
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql28");
	mySql.addSubPara(fm.RptNo.value);   
	var accMoney=easyExecSql(mySql.getString());
	
   // var polSql="select nvl(sum(realpay),0) from llclaimpolicy where clmno='"+fm.RptNo.value+"' ";
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql29");
	mySql.addSubPara(fm.RptNo.value);   
    
    var polMoney=easyExecSql(mySql.getString());

    if (parseFloat(accMoney)!=parseFloat(polMoney))
    {
        alert("���ⰸ�⸶�����������ȣ���������ȷ�ϣ���������������ԣ�");
        return;      	
    }  
  
    //var txsql="select distinct givetype from llclaimdetail where clmno='"+fm.RptNo.value+"' ";
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql30");
	mySql.addSubPara(fm.RptNo.value); 
    var tGiveType=easyExecSql(mySql.getString());
    
    if (tGiveType.length>1)
    {     
       alert('�⸶���۲�ͳһ�����޸ĺ�������ȷ�ϣ�');	
       return;
    }   

   //������ԱȨ���ж�
  if (fm.isNew.value == '2')
  {
    if(fm.SimpleConclusion2.value == '0' ){
    var tClaimType1 ;
    var tClaimType2 ;
    var tRealpay ;
//01.��ѯ���ⰸ���������ֵ

    // var csql="select customerno from llcase where caseno='"+fm.RptNo.value+"' ";
     mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql31");
	mySql.addSubPara(fm.RptNo.value); 
     var tcustomerno=new Array();
     tcustomerno=easyExecSql(mySql.getString());
     /*for (var i=0;i<tcustomerno.length;i++)
     {
          /*var strSql01 = " select realpay, insuredno from llclaimpolicy "
                       + " where clmno = '"+fm.RptNo.value+"' and realpay = (select max(realpay) from llclaimpolicy "
                       + " where clmno = '"+fm.RptNo.value+"' )"   */
         /*var strSql00 = " select sum(realpay) from llclaimpolicy "
                       + " where clmno = '"+fm.RptNo.value+"' and riskcode in (select riskcode  from LMRiskApp where RiskType7='10') and insuredno='"+tcustomerno[i]+"'";                  
                       
         var tSubReport = new Array;
         tSubReport = easyExecSql(strSql00);
         var  tRealpay1 = tSubReport[0][0];
         //alert ("�����⸶:"+tRealpay1);
        // var tInsuredno = tSubReport[0][1];
        
             if(tRealpay1 == '' || tRealpay1 == null)
             {
               //  alert("δ��ѯ�����ⰸ���⸶��");
               //  return;
                 tRealpay1=0;  
             }
           else
           	{
           	  tClaimType1='10';	
           	}
           	
         var strSql01 = " select sum(realpay) from llclaimpolicy "
                       + " where clmno = '"+fm.RptNo.value+"' and riskcode in (select riskcode  from LMRiskApp where RiskType7='30') and insuredno='"+tcustomerno[i]+"'";                  
        
         var tSubReport1 = new Array;
         tSubReport1 = easyExecSql(strSql01);
         var   tRealpay2 = tSubReport1[0][0];
        //alert ("�������⸶:"+tRealpay2);
        // var tInsuredno2 = tSubReport[0][1];
           if(tRealpay2==null || tRealpay2 == "")
             {
        //         alert("δ��ѯ�����ⰸ���⸶��");
        //         return;
                tRealpay2=0;           
             }
           else
           	{
           	   	tClaimType2='30';
           	}      	
           	
        /*//*02.��ѯ���������ֵ�Ŀͻ��ĳ�������
             var strSql02 = "select reasoncode from LLAppClaimReason where caseno = '"+fm.RptNo.value+"' and customerno = '"+tInsuredno+"'";
             var tSubReport2 = new Array;
             tSubReport2 = easyExecSql(strSql02);
               if(tSubReport2 == null ){
                    alert("δ��ѯ�����ⰸ�ĳ������ͣ�");
                    return;
                   }
             for (var i= 0;i < tSubReport2.length ; i++ )
             {
                 var tReasonCode = tSubReport2[i][0].substring(1,3);
                 if(tReasonCode == 01 || tReasonCode == 02 || tReasonCode == 04){
                      tClaimType = 10;//����
                      break;
                     }else{
                      tClaimType = 30;//������
                     }
             }*/
             
        //03.1��ѯ������Ա��������͵�����Ȩ��
        //0301.1��ѯ������Ա������Ȩ��
               /*var strSql0301 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('tOperator').value +"' and b.claimtype = '"+tClaimType1+"' and a.StateFlag = '1'"
                var tBasemax0301 = easyExecSql(strSql0301);
                if (tBasemax0301 == null || tBasemax0301 == "")
                {
        //            alert("δ��ѯ����������Ȩ�ޣ�");
        //            return;
                      tBasemax0301 = 0; //Ϊ��ѯ����Ĭ��Ϊ0
                }
        //0302.1��ѯ������Ա������Ȩ��
                var strSql0302 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('Operator').value +"' and b.claimtype = '"+tClaimType1+"' and a.StateFlag = '1'"
                var tBasemax0302 = easyExecSql(strSql0302);
                if (tBasemax0302 == null || tBasemax0302 == "")
                {
        //            alert("δ��ѯ����������Ȩ�ޣ�");
        //            return;
                      tBasemax0302 = 0; //Ϊ��ѯ����Ĭ��Ϊ0
                }
        //0303.1������Ա�������ԱȨ���ж�
                tBasemax0301 = tBasemax0301*1;
                tBasemax0302 = tBasemax0302*1;
                if(tBasemax0301 > tBasemax0302){
                  tBasemax1 = tBasemax0301;
                }else{
                  tBasemax1 = tBasemax0302;
                }
        
        //03.2��ѯ������Ա��������͵�����Ȩ�� 
        //0301.2��ѯ������Ա������Ȩ��        
                var strSql03011 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('tOperator').value +"' and b.claimtype = '"+tClaimType2+"' and a.StateFlag = '1'"
                var tBasemax03011 = easyExecSql(strSql03011);
                if (tBasemax03011 == null || tBasemax03011 == "")
                {
        //            alert("δ��ѯ����������Ȩ�ޣ�");
        //            return;
                      tBasemax03011 = 0; //Ϊ��ѯ����Ĭ��Ϊ0
                }
        //0302.2��ѯ������Ա������Ȩ��
                var strSql03022 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('Operator').value +"' and b.claimtype = '"+tClaimType2+"' and a.StateFlag = '1'"
                var tBasemax03022 = easyExecSql(strSql03022);
                if (tBasemax03022== null || tBasemax03022 == "")
                {
        //            alert("δ��ѯ����������Ȩ�ޣ�");
        //            return;
                      tBasemax03022 = 0; //Ϊ��ѯ����Ĭ��Ϊ0
                }
        //0303.2������Ա�������ԱȨ���ж�
                tBasemax03011 = tBasemax03011*1;
                tBasemax03022 = tBasemax03022*1;
                if(tBasemax03011 > tBasemax03022){
                  tBasemax2 = tBasemax03011;
                }else{
                  tBasemax2 = tBasemax03022;
                }
        
        //04.Ȩ���ж�
                tBasemax1 = tBasemax1*1;
                tBasemax2 = tBasemax2*1;
        
              		tRealpay1 = tRealpay1*1;
              		tRealpay2 = tRealpay2*1;
              		
              	//alert ("��������⸶:"+tBasemax1);
                          
                //alert ("����������⸶:"+tBasemax2);
              		
                if(tRealpay1 > tBasemax1 ||tRealpay2 > tBasemax2)
                {
                    alert("����Ȩ�޲��㣡���ϱ��ܹ�˾����");
                    return;
                }     	
             }*/
             
             
      }else if (fm.SimpleConclusion2.value == '1')
      {
    	  var mngcom = new Array;
    	  //var tsql="select mngcom from llregister where rgtno='"+fm.RptNo.value+"' ";    	  
    	  mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql32");
	mySql.addSubPara(fm.RptNo.value); 
    	  mngcom = easyExecSql(mySql.getString());   
    	  
    	  if (document.allManageCom.value!=mngcom)
    	  {
    	     alert ("���¼�������� "+mngcom+" ���л��˲�����");	
    	     return;
    	  }
      }
   }   
      
 

  fm.action="./LLGrpClaimSimpleSave.jsp"
  fm.fmtransact.value = "";
  submitForm();
}

//������װ���
function confirmClick2()
{

	if(!(confirm("ȷ�ϱ����������!")==true)){
		return false;
	}
	


  //��ѯ�Ƿ���й�ƥ����� 2009-08-04 9:08
  /*var Detailsql = "select count(1) from LLClaimDetail where"
           + " ClmNo = '" + fm.RptNo.value + "'";*/
  mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql33");
	mySql.addSubPara(fm.RptNo.value); 
  var tDetailDutyKind = easyExecSql(mySql.getString());
  if (tDetailDutyKind == 0)
  {
      alert("���Ƚ���ƥ�䲢����!");
      return;
  }
   
  //���ǿ�
  if (fm.SimpleConclusion2.value == "" || fm.SimpleConclusion2.value == null)
  {
      alert("����д��˽��ۣ�");
      return;
  }
  else 
  {        
	  //���ǿ�
	  if (fm.AuditIdea.value == "" || fm.AuditIdea.value == null)
	  {
	      alert("����д��������");
	      return;
	  }
	  
      if (fm.SimpleConclusion2.value == "1")
        {            
            var tGivetype = new Array;
           /* var strSql = "select givetype from LLClaimDetail where "
                       + "clmno = '" + fm.RptNo.value + "'";*/
            mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql34");
	mySql.addSubPara(fm.RptNo.value); 
            tGivetype = easyExecSql(mySql.getString());
            if (tGivetype != null)
            {
                for (var i=0; i<tGivetype.length; i++)
                {
                    if (tGivetype[i] != "1")
                    {                      
                        alert("���ȫ��Ϊ�ܸ�,������ȫ���ܸ�����!");
                        return;
                    }
                }
            }
            
            if (fm.ProtestReason.value == "" || fm.ProtestReason.value == null)
            { 
              alert("����ѡ��ܸ�ԭ��!");
              return;
            } 
            
            if (fm.ProtestReasonDesc.value == "" || fm.ProtestReasonDesc.value == null)
            { 
              alert("������д�ܸ�����!");
              return;
            } 
        }
  else if (fm.SimpleConclusion2.value == "0"||fm.SimpleConclusion2.value == "4")
	  
        {//---------------jinsh--20070524--����ð������ڵ�������в�׼�᰸---------------//
        	//var JustStateSql="select COUNT(*) from lwmission where activityid in ('0000009125','0000009145','0000009165','0000009175') and missionprop1='"+fm.RptNo.value+"'";
        	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql35");
	mySql.addSubPara(fm.RptNo.value); 
        	var JustStateCount=easyExecSql(mySql.getString());
        	if(parseInt(JustStateCount)>0)
        	{      				
        		alert("�ð������ڵ�����������Ժ������᰸!");
        		return;
        	}
        //---------------jinsh--20070524--����ð������ڵ�������в�׼�᰸---------------//
           
            var tGivetype = new Array;
            /*var strSql = "select givetype from LLClaimDetail where "
                       + "clmno = '" + fm.RptNo.value + "'";*/
            mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql36");
	mySql.addSubPara(fm.RptNo.value); 
            tGivetype = easyExecSql(mySql.getString());
            if (tGivetype != null)
            {
                var tYesNo = 0;
                for (var i=0; i<tGivetype.length; i++)
                {
                    if (tGivetype[i] == "0" || tGivetype[i] == "2" || tGivetype[i] == "3")
                    {
                        tYesNo = 1;
                    }
                }
                if (tYesNo == 0)
                {          
                    alert("����ȫ��Ϊ�ܸ�,�����¸�������!");
                    return;
                }
            }
        }
    }
    

    //����Ǹ�����ܸ�����,�ύǰ����
    //alert("fm.SimpleConclusion2.value:"+fm.SimpleConclusion2.value);
  	if (!(fm.SimpleConclusion2.value ==null||fm.SimpleConclusion2.value==""))
    {
  		if(fm.SimpleConclusion2.value == "0" || fm.SimpleConclusion2.value == "1" || fm.SimpleConclusion2.value == "4")
  		{
  	        //���顢�ʱ���������Ͷ���У��,�������㡢��ͬ����
  	        if (!checkPre())
  	        {
  	            return;
  	        } 
  	        
  		}

    }

  	
  //ƥ�䲢�����ж�
  var i = PolDutyCodeGrid.mulLineCount;
  if(i == '0')
  {
      alert("����ƥ�䲢���㣡");
      return;
  }

    //���꿹���ڵ�У��:���Ϊ����������Ч�ճ�����������2�꣨��2�꣩���������ʾ��Ϣ
    if (!checkAccDate(fm.RptNo.value))
    {	
        return;
    }  

   //������ԱȨ���ж�
  if (fm.isNew.value == '2')
  {
    	//var tclmSql="select clmstate from llclaim where clmno='"+fm.RptNo.value+"' ";
    	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql37");
	mySql.addSubPara(fm.RptNo.value); 
    	var ttclmstate=easyExecSql(mySql.getString());
    	if (ttclmstate=="60"||ttclmstate=="70"||ttclmstate=="80")
    	{
    		alert("���ⰸ�Ѿ��᰸��");
    	  return;
    	}    
    
    fm.simpleClaim2.disabled = true;
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

        
    if(fm.SimpleConclusion2.value == '0')
    {
    var tClaimType1 ;
    var tClaimType2 ;
    var tRealpay ;
    var tGiveType ;
    var tappndmax ;
    var tappndmax1 ;
    var tappndmax2 ;    

    //var txsql="select distinct givetype from llclaimdetail where clmno='"+fm.RptNo.value+"' ";
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql38");
	mySql.addSubPara(fm.RptNo.value); 
    tGiveType=easyExecSql(mySql.getString());
    
    if (tGiveType.length>1)
    {
       showInfo.close();
       fm.simpleClaim2.disabled = false;
       alert('�⸶���۲�ͳһ������˵�����������޸ģ�');	
       return;
    }
    
//  if(tGiveType == '0' && tGiveType.length==1 )     
//  {
//     //01.��ѯ���ⰸ���������ֵ     
//     var csql="select customerno from llcase where caseno='"+fm.RptNo.value+"' ";
//     var tcustomerno=new Array();
//     tcustomerno=easyExecSql(csql);
//     for (var i=0;i<tcustomerno.length;i++)
//     {         
//         var strSql00 = " select sum(realpay) from llclaimpolicy "
//                      + " where clmno = '"+fm.RptNo.value+"' and riskcode in (select riskcode  from LMRiskApp where RiskType7='10') and insuredno='"+tcustomerno[i]+"'";                  
//         
//         var tSubReport = new Array;
//         tSubReport = easyExecSql(strSql00);    
//         var   tRealpay1 = tSubReport[0][0];
//         //alert ("�����⸶:"+tRealpay1);
//         //var tInsuredno1 = tSubReport[0][1];
//         
//          if(tRealpay1==null || tRealpay1 == "")
//             {
//               //  alert("δ��ѯ�����ⰸ���⸶��");
//              //   return false;
//              tRealpay1=0;       
//             }
//          else
//           	{
//           		//alert("tClaimType1");
//           	   tClaimType1='10';
//           	}
//         
//         var strSql01 = " select sum(realpay) from llclaimpolicy "
//                       + " where clmno = '"+fm.RptNo.value+"' and riskcode in (select riskcode  from LMRiskApp where RiskType7='30') and insuredno='"+tcustomerno[i]+"'";                  
//         
//         var tSubReport1 = new Array;
//         tSubReport1 = easyExecSql(strSql01);
//         var   tRealpay2 = tSubReport1[0][0];
//             //alert ("�������⸶:"+tRealpay2);
//            // var tInsuredno2 = tSubReport[0][1];
//               if(tRealpay2==null || tRealpay2 == "")
//                 {
//            //         alert("δ��ѯ�����ⰸ���⸶��");
//            //         return;
//                    tRealpay2=0;           
//                 }
//               else
//               	{
//               	   	tClaimType2='30';
//               	}
//               
//         //02.��ѯ���������ֵ�Ŀͻ��ĳ�������
//              /*var strSql02 = "select distinct reasoncode from LLAppClaimReason where caseno = '"+fm.ClmNo.value+"' and customerno = '"+fm.customerNo.value+"'";
//              var tSubReport2 = new Array;
//              tSubReport2 = easyExecSql(strSql02);
//                if(tSubReport2 == null ){
//                     alert("δ��ѯ�����ⰸ�ĳ������ͣ�");
//                     return;
//                    }
//              for (var i= 0;i < tSubReport2.length ; i++ )
//              {
//                  var tReasonCode = tSubReport2[i][0].substring(1,3);
//                  if(tReasonCode == 01 || tReasonCode == 02 || tReasonCode == 04){
//                       tClaimType = 10;//����
//                       break;
//                      }else{
//                       tClaimType = 30;//������
//                      }
//              }*/
//         //03.1��ѯ������Ա��������͵�����Ȩ��
//         //0301.1��ѯ������Ա������Ȩ��
//                 var strSql0301 = "select b.basemax from llgrpclaimuser a,LLgrpClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('tOperator').value +"' and b.claimtype = '"+tClaimType1+"' and a.StateFlag = '1'"
//                 var tBasemax0301 = easyExecSql(strSql0301);
//                 if (tBasemax0301 == null || tBasemax0301 == "")
//                 {
//         //            alert("δ��ѯ����������Ȩ�ޣ�");
//         //            return;
//                       tBasemax0301 = 0; //Ϊ��ѯ����Ĭ��Ϊ0
//                 }
//         //0302.1��ѯ������Ա������Ȩ��
//                 var strSql0302 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('Operator').value +"' and b.claimtype = '"+tClaimType1+"' and a.StateFlag = '1'"
//                 var tBasemax0302 = easyExecSql(strSql0302);
//                 if (tBasemax0302 == null || tBasemax0302 == "")
//                 {
//         //            alert("δ��ѯ����������Ȩ�ޣ�");
//         //            return;
//                       tBasemax0302 = 0; //Ϊ��ѯ����Ĭ��Ϊ0
//                 }
//         //0303.1������Ա�������ԱȨ���ж�
//                 tBasemax0301 = tBasemax0301*1;
//                 tBasemax0302 = tBasemax0302*1;
//                 if(tBasemax0301 > tBasemax0302){
//                   tBasemax1 = tBasemax0301;
//                 }else{
//                   tBasemax1 = tBasemax0302;
//                 }
//         
//         //03.2��ѯ������Ա��������͵�����Ȩ�� 
//         //0301.2��ѯ������Ա������Ȩ��        
//                 var strSql03011 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('tOperator').value +"' and b.claimtype = '"+tClaimType2+"' and a.StateFlag = '1'"
//                 var tBasemax03011 = easyExecSql(strSql03011);
//                 if (tBasemax03011 == null || tBasemax03011 == "")
//                 {
//         //            alert("δ��ѯ����������Ȩ�ޣ�");
//         //            return;
//                       tBasemax03011 = 0; //Ϊ��ѯ����Ĭ��Ϊ0
//                 }
//         //0302.2��ѯ������Ա������Ȩ��
//                 var strSql03022 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('Operator').value +"' and b.claimtype = '"+tClaimType2+"' and a.StateFlag = '1'"
//                 var tBasemax03022 = easyExecSql(strSql03022);
//                 if (tBasemax03022== null || tBasemax03022 == "")
//                 {
//         //            alert("δ��ѯ����������Ȩ�ޣ�");
//         //            return;
//                       tBasemax03022 = 0; //Ϊ��ѯ����Ĭ��Ϊ0
//                 }
//         //0303.2������Ա�������ԱȨ���ж�
//                 tBasemax03011 = tBasemax03011*1;
//                 tBasemax03022 = tBasemax03022*1;
//                 if(tBasemax03011 > tBasemax03022){
//                   tBasemax2 = tBasemax03011;
//                 }else{
//                   tBasemax2 = tBasemax03022;
//                 }
//                 
//         //04.Ȩ���ж�
//                 tBasemax1 = tBasemax1*1;
//                 tBasemax2 = tBasemax2*1;
//         
//               		tRealpay1 = tRealpay1*1;
//               		tRealpay2 = tRealpay2*1;      	                    
//                        
//                 //alert ("��������⸶:"+tBasemax1);
//                           
//                 //alert ("����������⸶:"+tBasemax2);
//              if(tRealpay1 > tBasemax1 || tRealpay2 > tBasemax2)
//               {
//         //05.��Ȩ���ϱ������������ж�
//         //0501.��ѯ�û����������Ȩ��,ͬʱ���û��������userstate 0 ��Ч 1ʧЧ
//                 var strSql0501 = "select max(checklevel) from llclaimuser a , Lduser b  where a.comcode like '"+fm.ManageCom.value+"%%' and a.StateFlag = '1' and a.usercode = b.usercode and b.userstate = '0'";
//                 var tChecklevel  = easyExecSql(strSql0501);
//         //0502.��ѯ�û������Ȩ�޵��⸶���
//                 var strSql0502 = "select basemax from LLClaimPopedom where claimtype = '"+tClaimType1+"' and Popedomlevel = '"+tChecklevel+"'";
//                 var strSql05022 = "select basemax from LLClaimPopedom where claimtype = '"+tClaimType2+"' and Popedomlevel = '"+tChecklevel+"'";
//                 var tBasemax3  = easyExecSql(strSql0502);
//                 var tBasemax4  = easyExecSql(strSql05022);
//                 tBasemax3 = tBasemax3*1;
//                 tBasemax4 = tBasemax4*1;
//                 
//                 if(tRealpay1 > tBasemax3 || tRealpay2 > tBasemax4)
//                   {
//         //�ؼ�Ȩ���趨
//                    fm.ConclusionSave.disabled = false;
////                    fm.ConclusionUp.disabled = true;
////                    fm.ConclusionBack.disabled = true;
//                    showInfo.close();
//                    fm.simpleClaim2.disabled=false;                     
//                     alert("����Ȩ�޲��㣡�뽫�ð����ϱ�����������");
//                     return;
//                   }                  
//                   else{
//         //�ؼ�Ȩ���趨 
//                    //fm.ConclusionSave.disabled = true;
////                    fm.ConclusionUp.disabled = false;
////                    fm.ConclusionBack.disabled = true;
//                    showInfo.close();
//                    fm.simpleClaim2.disabled=false;                         
//                     alert("����Ȩ�޲��㣡�뽫�ð�����Ȩ���ϱ���");
//                     return;
//                   }
//                   
//               }
//     }  //for customerno
//   }else if ((tGiveType == '2'||tGiveType == '3') && tGiveType.length== 1 )
//    {            
//      var tsql1="select distinct b.appendmax from llclaimuser a ,llclaimpopedom b where a.checklevel=b.popedomlevel and a.usercode='"+document.all('tOperator').value+"' "; //������Ա
//      tappndmax1=easyExecSql(tsql1);
//      var tsql2="select distinct b.appendmax from llclaimuser a ,llclaimpopedom b where a.checklevel=b.popedomlevel and a.usercode='"+document.all('Operator').value+"' ";  //������Ա
//      tappndmax2=easyExecSql(tsql2);     
//      if (parseFloat(tappndmax2)>parseFloat(tappndmax1))
//        {      	   
//      	    tappndmax=tappndmax2;      	    
//      	}else
//      		{
//      			tappndmax=tappndmax1;  
//      		}          	
//       var csql2="select customerno from llcase where caseno='"+fm.RptNo.value+"' ";
//       var xcustomerno=new Array();
//       xcustomerno=easyExecSql(csql2);
//       for (var k=0;k<xcustomerno.length;k++)
//       {         		
//         var maxsql="select sum(realpay) from llclaimpolicy where clmno = '"+fm.RptNo.value+"' and insuredno='"+xcustomerno[k]+"' ";
//         tRealpay=easyExecSql(maxsql);      
//         if (parseFloat(tappndmax)<parseFloat(tRealpay)) 
//         { 
//            var strSql0501 = "select max(checklevel) from llclaimuser a , Lduser b  where a.comcode like '"+fm.ManageCom.value+"%%' and a.StateFlag = '1' and a.usercode = b.usercode and b.userstate = '0'";
//            var tChecklevel  = easyExecSql(strSql0501);
//            
//            var strSql0502 = "select distinct appendmax from LLClaimPopedom where Popedomlevel = '"+tChecklevel+"'";                 
//            var tBasemax3  = easyExecSql(strSql0502);                 
//            tBasemax3 = tBasemax3*1;                 
//            
//            if(tRealpay > tBasemax3)
//              {
//               fm.ConclusionSave.disabled = false;
////               fm.ConclusionUp.disabled = true;
////               fm.ConclusionBack.disabled = true;
//               showInfo.close();
//               fm.simpleClaim2.disabled=false;                     
//                alert("����ͨ�ڡ�Э���⸶Ȩ�޲������뽫�ð����ϱ�����������");
//                return;
//              }                  
//              else{                        
//               //fm.ConclusionSave.disabled = true;
////               fm.ConclusionUp.disabled = false;
////               fm.ConclusionBack.disabled = true;
//               showInfo.close();
//               fm.simpleClaim2.disabled=false;                       
//                alert("����ͨ�ڡ�Э���⸶Ȩ�޲��㣡�뽫�ð�����Ȩ���ϱ���");
//                return;
//              }         	        	 
//          } 
//       }    	
//    }else if(tGiveType == '1' && tGiveType.length==1 )         
//    {
//      var tsql="select checklevel from llclaimuser where usercode='"+document.all('Operator').value+"' ";	//������Ա
//      var tchecklevel=new Array;
//      tchecklevel=easyExecSql(tsql);
//      if (tchecklevel=="A"||tchecklevel=="B1"||tchecklevel=="B2"||tchecklevel=="B3")
//      {
//          var strSql0501 = "select max(checklevel) from llclaimuser a , Lduser b  where a.comcode like '"+fm.ManageCom.value+"%%' and a.StateFlag = '1' and a.usercode = b.usercode and b.userstate = '0'";
//          var tChecklevel  = easyExecSql(strSql0501);              
//          
//          if (tChecklevel=="A"||tChecklevel=="B1"||tChecklevel=="B2"||tChecklevel=="B3")
//            {
//             fm.ConclusionSave.disabled = false;
////             fm.ConclusionUp.disabled = true;
////             fm.ConclusionBack.disabled = true;
//             showInfo.close();
//             fm.simpleClaim2.disabled=false;                     
//              alert("���ľܸ�Ȩ�޲������뽫�ð����ϱ�����������");
//              return;
//            }                  
//            else{         
//             //fm.ConclusionSave.disabled = true;
////             fm.ConclusionUp.disabled = false;
////             fm.ConclusionBack.disabled = true;
//             showInfo.close();
//             fm.simpleClaim2.disabled=false;                         
//              alert("���ľܸ�Ȩ�޲��㣡�뽫�ð�����Ȩ���ϱ���");
//              return;
//            }          
//
//       }                
//     }     
   }   //fm.SimpleConclusion2.value == '0'
   
 }   //fm.isNew.value == '2'
 
  fm.action="./LLGrpSimpleSave.jsp"
  fm.fmtransact.value = "INSERT";
  showInfo.close();
  submitForm();
}

//���꿹���ڵ�У��:���Ϊ����������Ч�ճ�����������2�꣨��2�꣩���������ʾ��Ϣ
function checkAccDate(tRptNo)
{	
	var flag=false;//�Ƿ񳬹������ʾ
	var tContno="";//��������ı�����
	
	var tAccDate;//���յĳ�������:LLAccident.AccDate
    /*var strQSql = "select AccDate from LLAccident where AccNo in (select CaseRelaNo from LLCaseRela "
                + "where CaseNo = '"+tRptNo+"')";*-*/
     mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql39");
	mySql.addSubPara(tRptNo); 
    var tDate = easyExecSql(mySql.getString());
    if (tDate == null)
    {
        alert("δ��ѯ����������!");
        return true;
    }else{
    	tAccDate=tDate[0][0];
	}

   /* var strQSql = "select cvalidate,contno from lccont a where a.contno in "
    			+" (select distinct b.contno from LLClaimPolicy b where b.clmno = '"+tRptNo+"')";*/
   mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql40");
	mySql.addSubPara(tRptNo); 
    var tCvalidate = easyExecSql(mySql.getString());//�ŵ��и��˱�����Ӧ��Ч����
    if (tCvalidate == null)
    {
        alert("δ��ѯ���˰�����Ӧ��������Ч����!");
        return true;
    }
	for(var i=0;i<tCvalidate.length;i++){
		//alert("tCvalidate["+i+"]:"+tCvalidate[i][0]);
		//alert("tAccDate:"+tAccDate);
		if(dateDiff(tCvalidate[i][0]+"",tAccDate,'M') >= 24){	//������Ч�������������ȣ�������ڻ��������(24��)
			tContno+="["+tCvalidate[i][1]+"]";
			flag=true;
		}
	}

	if(flag){
		if(!confirm("��ע�⣺�ð����漰������������"+tContno+"����Ч�������������Ѵﵽ�򳬹����꣬���ò��ɿ��硣")){
			return false;
		}
	}
	
	return true;
}

function AuditConfirmClick()
{
   //-----------------------------liuyu-20070627----------------------------------------//
   var mngcom = new Array;
   //var tsql="select mngcom,operator from llregister where rgtno='"+fm.RptNo.value+"' ";    	  
   mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql41");
	mySql.addSubPara(fm.RptNo.value); 
   mngcom = easyExecSql(mySql.getString());
   var tmanagecom=mngcom[0][0];   	      	  
   var toperator=mngcom[0][1];    	        	      	  
   
    if(tmanagecom==null||tmanagecom=="")
    {
    		alert("������������ʧ��!");
    		return;
    }
    if(toperator==null||toperator=="")
    {
    		alert("������������Աʧ��!");
    		return;
    }     
    //-----------------------------liuyu-20070627----------------------------------------//
    fm.action="./LLGrpSimpleAuditSave.jsp?mngNo="+tmanagecom+"&operator="+toperator;
    submitForm();  	
}

function ConclusionSaveClick()
{
	  //��ѯ�Ƿ���й�ƥ����� 2009-08-04 9:08
 /* var Detailsql = "select count(1) from LLClaimDetail where"
           + " ClmNo = '" + fm.RptNo.value + "'";*/
  mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql42");
	mySql.addSubPara(fm.RptNo.value);          
  var tDetailDutyKind = easyExecSql(mySql.getString());
  if (tDetailDutyKind == 0)
  {
      alert("���Ƚ���ƥ�䲢����!");
      return;
  }

	//�ж���˽���
   /* var strSql = "select AuditConclusion from LLClaimUWMain where "
               + "clmno = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql43");
	mySql.addSubPara(fm.RptNo.value);              
    var tGivetype = easyExecSql(mySql.getString());
    if (tGivetype == null)
    {
        alert("���ȱ�����˽��ۣ�");
        return;
    }
    else
    {
        if (tGivetype == "1")
        {
            var tGtype = new Array;
           /* var strSql = "select givetype from LLClaimDetail where "
                       + " clmno = '" + fm.RptNo.value + "'"
                       + " and givetype != '2'";*/
           mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql44");
	mySql.addSubPara(fm.RptNo.value);  
            tGtype = easyExecSql(mySql.getString());
            if (tGtype != null)
            {
                //alert(tGtype.length);
                for (var i=0; i<tGtype.length; i++)
                {
//                    alert(tGtype[i])
                    if (tGtype[i] != "1")
                    {
                        alert("���ȫ��Ϊ�ܸ�,������ȫ���ܸ�����!");
                        return;
                    }
                }
            }
            
            if (fm.ProtestReason.value == "" || fm.ProtestReason.value == null)
            { 
              alert("����ѡ��ܸ�ԭ��!");
              return;
            } 
            
            if (fm.ProtestReasonDesc.value == "" || fm.ProtestReasonDesc.value == null)
            { 
              alert("������д�ܸ�����!");
              return;
            } 
        }
        else if (tGtype == "0")
        {
            var tGtype = new Array;
          /*  var strSql = "select givetype from LLClaimDetail where "
                       + " clmno = '" + fm.RptNo.value + "'"
                       + " and givetype != '2'";*/
            mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql45");
	mySql.addSubPara(fm.RptNo.value);  
            tGtype = easyExecSql(mySql.getString());
            if (tGtype != null)
            {
                var tYesNo = 0;
                for (var i=0; i<tGtype.length; i++)
                {
//                    alert(tGtype[i])
                    if (tGtype[i] == "0"||tGtype == "4")
                    {
                        tYesNo = 1;
                    }
                }
                if (tYesNo == 0)
                {
                    alert("����ȫ��Ϊ�ܸ�,�����¸�������!");
                    return;
                }
            }
        }
    }
    
    //����Ǹ�����ܸ�����,�ύǰ����
    if (tGivetype == "0" || tGivetype == "1"||tGivetype == "4")
    {
        //���顢�ʱ���������Ͷ���У��,�������㡢��ͬ����
        if (!checkPre())
        {
            return;
        } 
        
        //ֻ�л���ʱ����Ҫ�������˷���
       /* var tSQL = "select substr(getdutykind,2,2) from llclaimdutykind where 1=1 "
                 + getWherePart( 'ClmNo','RptNo' );*/
         mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
	mySql.setSqlId("LLGrpBatchAuditSql46");
	mySql.addSubPara(fm.RptNo.value);  
        var tDutyKind = easyExecSql(mySql.getString());
        if (tDutyKind != '09')
        {
//            //����¸�������,У�������˷���
//            if (tGivetype == "0")
//            {
              /*  var strSql4 = "select (case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and b.bnfkind = 'A') when 0 then 0 else 1 end) from LLBalance a where 1=1 "
                            + getWherePart( 'ClmNo','RptNo' );*/
                mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql47");
				mySql.addSubPara(fm.RptNo.value);  
                var tBNF = easyExecSql(mySql.getString());
                
                if (tBNF)
                {
                    for (var i = 0; i < tBNF.length; i++)
                    {
                        if (tBNF[i] == '0')
                        {
                            alert("�����˷��仹û�����!");
                            return;
                        }
                    }
                }
//            }
        }
    }

    fm.action="./LLGrpSimpleAuditSave.jsp"
    submitForm();  	
}

//��Ȩ���ϱ��ύ����
function ConclusionUpClick()
{
    fm.SimpleConclusion2.value = 6 ;//��Ȩ���ϱ�
    confirmClick2();//ֱ�ӱ�����˽���
}


//��鱣�������,ֻ�ܵ�С
function checkAdjMoney()
{
	if(fm.AdjReason.value == "00"||fm.AdjReason.value == "14"){
    	if(fm.AdjRemark.value == null || fm.AdjRemark.value =="null" || fm.AdjRemark.value == ""){
    		alert("����ԭ��Ϊͨ�ڸ�����Э�����ʱ������¼�������ע!");
    		return false;
    	}
    }
	
    var i = PolDutyCodeGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tContNo   = parseFloat(PolDutyCodeGrid.getRowColData(i,31));//������ͬ��
        var tRiskCode = parseFloat(PolDutyCodeGrid.getRowColData(i,3));//���ֱ���
        var tDutyCode = parseFloat(PolDutyCodeGrid.getRowColData(i,28));//���α���
        var tRealM    = parseFloat(PolDutyCodeGrid.getRowColData(i,21));//�������
        var tAmnt     = parseFloat(PolDutyCodeGrid.getRowColData(i,9));//����
        var tGrpContNo= parseFloat(PolDutyCodeGrid.getRowColData(i,30));//�����ͬ��
        var tGetDutyKind = parseFloat(PolDutyCodeGrid.getRowColData(i,2));
        var tGetDutyCode = parseFloat(PolDutyCodeGrid.getRowColData(i,4));
        var tAdjM     = parseFloat(fm.RealPay.value);
        var tContPlanCode = "0";
        var tDeadTopvalFlag = "";
        
        var tPolno = PolDutyCodeGrid.getRowColData(i,1);//���ֱ�����PolNo
        //var strPSQL = "select riskamnt from lcpol where polno='"+tPolno+"'";
       mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql48");
				mySql.addSubPara(tPolno);  
        var arrp= easyExecSql(mySql.getString());
       

       //�жϽ����Ͳ�������
       /*var strSQL4 = " select deadtopvalueflag from lmdutygetclm where "
                   + " getdutycode='"+tGetDutyCode+"' and getdutykind='"+tGetDutyKind+"'"*/
        mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql49");
				mySql.addSubPara(tGetDutyCode);  
				mySql.addSubPara(tGetDutyKind);
       var arr4= easyExecSql(mySql.getString());
          if(arr4 != null){
          tDeadTopvalFlag = arr4[0][0];
             }

        if(tRiskCode == '140' || tDeadTopvalFlag == 'N')
        {
          /*
         var strSQL3 = " select contplancode from lcinsured  where contno = '"+tContNo+"'"
         var arr3= easyExecSql(strSQL3);
            if(arr3 != null){
            tContPlanCode = arr3[0][0];
               }
         var strSQL = "select a.TotalLimit from lldutyctrl a"
                    + " where trim(a.ContType) = '2' and trim(a.ContNo) = '"+tGrpContNo+"'"
                    + " and trim(a.DutyCode) = '"+tDutyCode+"' and trim(a.RiskCode) = '"+tRiskCode+"'"
                    + " and trim(a.ContPlanCode) = '"+tContPlanCode+"'";
         var arr= easyExecSql(strSQL);
            if(arr != null){
            tAmnt = arr[0][0];
               }else{
         var strSQL2 = "select a.TotalLimit from lldutyctrl a"
                    + " where trim(a.ContType) = '1' and trim(a.ContNo) = '"+tContNo+"'"
                    + " and trim(a.DutyCode) = '"+tDutyCode+"' and trim(a.RiskCode) = '"+tRiskCode+"'"
                    + " and trim(a.ContPlanCode) = '"+tContPlanCode+"'";
         var arr2= easyExecSql(strSQL2);
             if(arr2 != null)
             {
                tAmnt = arr2[0][0];
             }
             else{
               alert("δ��ѯ��140���ֵ��⸶�޶");
               fm.RealPay.value = 0;
               return;
                  }
               }
               */
               tAmnt=1000000;
            }
        
        if(arrp[0][0]== null){
        	alert("���ձ������");
        }
        
        //var strSQL1 = "select InsuAccFlag from lmrisk where riskcode='"+tRiskCode+"'";
	mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql50");
				mySql.addSubPara(tRiskCode);  
        var arr1= easyExecSql(mySql.getString());
        
        if (arr1[0][0] != 'Y')
        {
        if (arrp[0][0] < tAdjM)
        	{
            	alert("�������ܴ��ڷ��ձ���!");
            	fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,12);
            	return;
        	} 
        }
        
        if(tAdjM < 0){
            alert("��������С����!");
            fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,12);
            return;
        }
    }
    if (fm.GiveType.value=="")
    {
    	alert("�������⸶����!");
    	return;
    }else if (fm.GiveType.value=="0" || fm.GiveType.value=="2" || fm.GiveType.value=="3")
    {
       if (fm.RealPay.value=="")
       {
       	alert("������������!");
       	return;
       }    
       if (fm.AdjReason.value=="")
       {
       	alert("���������ԭ��!");
       	return;
       }      	
    }
    else if (fm.GiveType.value=="1")
    {
        
        if(fm.GiveReason.value ==null || fm.GiveReason.value =="")
        {
        	alert ("����ѡ��ܸ�ԭ��!");
        	return false;
        }
        
        if(fm.GiveTypeDesc.value ==null || fm.GiveTypeDesc.value =="")
        {
        	alert ("����¼��ܸ�����!");
        	return false;
        }
        
        
     }else {
     	alert("��������ȷ���⸶���۴���!");
        return;     		
     }       
    if(fm.AdjReason.value == "00"||fm.AdjReason.value == "14"){
    	if(fm.AdjRemark.value == null || fm.AdjRemark.value =="null" || fm.AdjRemark.value == ""){
    		alert("����ԭ��Ϊͨ�ڸ�����Э�����ʱ������¼�������ע!");
    		return false;
    	}
    }
}


//�����˲�ѯ
function showInsPerQuery()
{
    var strUrl = "LLGrpLdPersonQueryMain.jsp?GrpContNo="+fm.GrpContNo.value+"&GrpCustomerNo="+fm.GrpCustomerNo.value+"&GrpName="+fm.GrpName.value+"&ManageCom="+document.allManageCom.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//[����]��ť��Ӧ����
function saveClick()
{
    //���Ƚ��зǿ��ֶμ���
    if (beforeSubmit())
    {
        if (fm.RgtClass.value == '' || fm.RgtClass.value == null)
        {
            fm.RgtClass.value = "2";//����
        }
        
        
        /*update by wood�����ڱ��������������ƣ����ﲻ���������ˡ�
        ���ӶԳ����˵�������������жϣ������δ�᰸�İ������ڣ�����������
         *2006-03-06 P��D
         
        var StrSQL = "select count(*) from llcase a, llregister b where a.caseno = b.rgtno and a.customerno = '"+fm.customerNo.value+"' and  b.clmstate not in ('60','80','50','70')";
        var arr= easyExecSql(StrSQL);
        if(arr > 0){
           alert("�ó�������δ�᰸������᰸������������");
           return false;
        }*/
        /*================================================================
         * �޸�ԭ�����Ӷ�����������������죩�ı������˵��жϲ���������
         * �� �� �ˣ�P.D
         * �޸����ڣ�2006-8-16
         *=================================================================
         */
       /* var SqlPol = " select count(*) From lcpol where polstate = '6'"
                   + " and  insuredno = '"+fm.customerNo.value+"'"
                   + " and  grpcontno = '"+fm.GrpContNo.value+"'";*/
        mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql51");
				mySql.addSubPara(tRiskCode);  
        if(fm.Polno.value != ''){
          //  SqlPol += " and riskcode = '"+fm.Polno.value+"'";
            mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql52");
				mySql.addSubPara(tRiskCode);  
				mySql.addSubPara(fm.Polno.value);  
             }
        var tPolState = easyExecSql(mySql.getString());
        if(tPolState > 0){
           alert("�ó������Ѿ����������������������������");
           return false;
         }
        /*================================================================
         * �޸�ԭ�����ӶԳ��ձ�ȫ���жϣ���ȫδȷ�Ϻ��˱��Ĳ���������
         * �� �� �ˣ�P.D
         * �޸����ڣ�2006-8-14
         =================================================================
         */
       // var SqlBq = "select  count(*) from LPEdorItem where insuredno = '"+fm.customerNo.value+"' and edorstate != '0' and edortype = 'CT'";
        mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql53"); 
				mySql.addSubPara(fm.customerNo.value);  
        var tEdorState = easyExecSql(mySql.getString());
        /*var sqlC = "select count(*) from lmriskapp where "
                 + "riskcode in (select riskcode From lcpol where "
                 + "grpcontno = '"+fm.GrpContNo.value+"' and insuredno = '"+fm.customerNo.value+"') "
                 + "and RiskPeriod = 'L'";*/
          mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql54"); 
				mySql.addSubPara(fm.GrpContNo.value);      
				mySql.addSubPara(fm.customerNo.value);       
        var tcount = easyExecSql(mySql.getString());
        if(tEdorState > 0 && tcount > 0 ){
           alert("�ó�������δȷ�ϵı�ȫ���Ѿ��˱�����ȷ�Ϻ�����������");
           return false;
        }

/*
        //jixf add 20051213
        var strSQL = " select a.polno from LCPol a, LCGrpCont b where  a.CValidate<='"+fm.AccidentDate.value+"' and a.enddate>='"+fm.AccidentDate.value+"'";
        strSQL=strSQL+" and a.GrpContNo=b.GrpContNo and insuredno='"+fm.customerNo.value+"' and a.appflag in ('1','4')";
         if (fm.GrpCustomerNo.value!=null&&fm.GrpCustomerNo.value!='000000')
         {
         strSQL=strSQL+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
         }
        if (fm.GrpContNo.value!=null)
        {
        strSQL=strSQL+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
        }
        
         var arr= easyExecSql(strSQL);
         if ( arr == null )
         {
         alert("�ñ���δ��Ч����γ������ڲ��ڸñ������˵ı����ڼ��ڣ�");         
         return false;
         }
*/
         /*if(TempCustomer() == false && showDate() == false){
         alert("�ñ���δ��Ч����γ������ڲ��ڸñ������˵ı����ڼ��ڣ�");         
         return false;
          }*/
         
         /*var strSQLBQ = " select a.polno from LPEdorItem a, LCGrpCont b where  a.EdorValidate>='"+fm.AccidentDate.value+"'";
            strSQLBQ=strSQLBQ+" and a.GrpContNo=b.GrpContNo and a.insuredno='"+fm.customerNo.value+"'";
            strSQLBQ=strSQLBQ+" and a.EdorType in ('AA','PT','IC','LC')��and EdorState='0'";*/
              mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql55"); 
				mySql.addSubPara(fm.AccidentDate.value);      
				mySql.addSubPara(fm.customerNo.value);  
             if (fm.GrpCustomerNo.value!=null)
             {
               //strSQLBQ=strSQLBQ+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
               mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql56"); 
				mySql.addSubPara(fm.AccidentDate.value);      
				mySql.addSubPara(fm.customerNo.value);  
				mySql.addSubPara(fm.GrpCustomerNo.value);  
             }
            if (fm.GrpContNo.value!=null)
            {
              //strSQLBQ=strSQLBQ+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
              mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql57"); 
				mySql.addSubPara(fm.AccidentDate.value);      
				mySql.addSubPara(fm.customerNo.value);  
				mySql.addSubPara(fm.GrpCustomerNo.value); 
				mySql.addSubPara(fm.GrpContNo.value); 
            }
            
             var arrbq= easyExecSql(mySql.getString());
             if ( arrbq != null )
             {
               alert("���ؾ��棺�ñ��������ڳ�������֮���������ܸ��ı���ı�ȫ��");                      
             }

    /*var strSQL = "select count(*) from lcinsureaccclass where accascription = '0'"
                + " and grpcontno = '" + fm.GrpContNo.value+"'";
    var tCount = easyExecSql(strSQL);//����  */


        //�ж������������������������Ǳ��������
        if(fm.RptNo.value == ''){
         fm.isReportExist.value = false;
        }
        if (fm.isReportExist.value == "false")
        {
            fm.fmtransact.value = "insert||first";
        }
        else
        {
            fm.fmtransact.value = "insert||customer";
        }
        fm.action = './LLGrpRegisterSave.jsp';
        submitForm();
              
   }
}

//��������Ϣ�޸�
function updateClick()
{
    if (confirm("��ȷʵ���޸ĸü�¼��"))
    {
        if (beforeSubmit())
        {
            alert("�޸���Ϣ��������ƥ�����㣡");
            //jixf add 20051213
           /* var strSQL = " select a.polno from LCPol a, LCGrpCont b where  a.CValidate<='"+fm.AccidentDate.value+"' and a.enddate>='"+fm.AccidentDate.value+"'";
            strSQL=strSQL+" and a.GrpContNo=b.GrpContNo and insuredno='"+fm.customerNo.value+"' and a.appflag in ('1','4')";*/
             mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql58"); 
				mySql.addSubPara(fm.AccidentDate.value);  
				mySql.addSubPara(fm.AccidentDate.value);      
				mySql.addSubPara(fm.customerNo.value);
             if (fm.GrpCustomerNo.value!=null&&fm.GrpCustomerNo.value!='000000')
             {
           //  strSQL=strSQL+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
              mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql59"); 
				mySql.addSubPara(fm.AccidentDate.value);  
				mySql.addSubPara(fm.AccidentDate.value);      
				mySql.addSubPara(fm.customerNo.value);  
				mySql.addSubPara(fm.GrpCustomerNo.value); 
             }
            if (fm.GrpContNo.value!=null)
            {
          //  strSQL=strSQL+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
             mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql60"); 
				mySql.addSubPara(fm.AccidentDate.value);  
				mySql.addSubPara(fm.AccidentDate.value);      
				mySql.addSubPara(fm.customerNo.value);  
				mySql.addSubPara(fm.GrpCustomerNo.value); 
				mySql.addSubPara(fm.GrpContNo.value); 
            }
            
             var arr= easyExecSql(mySql.getString());
             if ( arr == null )
             {
             alert("�ñ���δ��Ч����γ������ڲ��ڸñ������˵ı����ڼ��ڣ�");         
             return false;
             }
             
             /*var strSQLBQ = " select a.polno from LPEdorItem a, LCGrpCont b where  a.EdorValidate>='"+fm.AccidentDate.value+"'";
            strSQLBQ=strSQLBQ+" and a.GrpContNo=b.GrpContNo and a.insuredno='"+fm.customerNo.value+"'";
            strSQLBQ=strSQLBQ+" and a.EdorType in ('AA','PT','IC','LC')��and EdorState='0'";*/
             mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql61");   
				mySql.addSubPara(fm.AccidentDate.value);      
				mySql.addSubPara(fm.customerNo.value);  
             if (fm.GrpCustomerNo.value!=null)
             {
              // strSQLBQ=strSQLBQ+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
               mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql62");  
				mySql.addSubPara(fm.AccidentDate.value);      
				mySql.addSubPara(fm.customerNo.value);  
				mySql.addSubPara(fm.GrpCustomerNo.value); 
             }
            if (fm.GrpContNo.value!=null)
            {
              //strSQLBQ=strSQLBQ+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
              mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql63");  
				mySql.addSubPara(fm.AccidentDate.value);      
				mySql.addSubPara(fm.customerNo.value);  
				mySql.addSubPara(fm.GrpCustomerNo.value); 
				mySql.addSubPara(fm.GrpContNo.value); 
            }
            
             var arrbq= easyExecSql(mySql.getString());
             if ( arrbq != null )
             {
               alert("���ؾ��棺�ñ��������ڳ�������֮���������ܸ��ı���ı�ȫ��");                      
             }
                       
            fm.fmtransact.value = "update";
            fm.action = './LLGrpRegisterSave.jsp';
            submitForm();
        }
    }
}

//������Ϣ��ѯ
function ClientQuery2()
{
   var keycode = event.keyCode;
   if(keycode=="13" || keycode=="9")
   {
   if(document.all('GrpCustomerNo').value!=null && document.all('GrpCustomerNo').value!='' ||
      (document.all('GrpContNo').value!=null && document.all('GrpContNo').value!='') ||
      (document.all('GrpName').value!=null && document.all('GrpName').value!='') )
   {
      var arrResult = new Array();
     /* var strSQL="select  a.customerno, a.name, g.grpcontno,g.Peoples2, g.bankcode, g.bankaccno, accname, a.AddressNo  " +
      " from lcgrpcont g, LCGrpAppnt a " +
      " where a.grpcontno = g.grpcontno and g.appflag in ('1','4') "
      + getWherePart("g.AppntNo", "GrpCustomerNo" )
      + getWherePart( "g.GrpContNo","GrpContNo" );*/
      mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql64"); 
				mySql.addSubPara(fm.GrpCustomerNo.value); 
				mySql.addSubPara(fm.GrpContNo.value); 
				mySql.addSubPara(document.all('AllManageCom').value); 
      if(document.all('GrpName').value!=''){
         //strSQL += " and a.Name like '%%"+document.all('GrpName').value+"%%'";
          mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql65"); 
				mySql.addSubPara(fm.GrpCustomerNo.value); 
				mySql.addSubPara(fm.GrpContNo.value); 
				mySql.addSubPara(document.all('GrpName').value); 
				mySql.addSubPara(document.all('AllManageCom').value); 
      }
      //���ӶԻ������ж� ��½����ֻ�ܴ��������ĵ��� �ܻ������Դ���ֻ����ĵ��� 2006-02-18 P.D
      //strSQL += " and g.managecom like '"+document.all('AllManageCom').value+"%%'";
      arrResult=easyExecSql(mySql.getString());
       if(arrResult != null)
       {
          if(arrResult.length==1)
          {
             try{document.all('GrpCustomerNo').value=arrResult[0][0]}catch(ex){alert(ex.message+"GrpCustomerNo")}
             try{document.all('GrpName').value=arrResult[0][1]}catch(ex){alert(ex.message+"GrpName")}
             try{document.all('GrpContNo').value=arrResult[0][2]}catch(ex){alert(ex.message+"GrpContNo")}
             try{document.all('Peoples').value=arrResult[0][3]}catch(ex){alert(ex.message+"Peoples")}
             if(document.all('Peoples').value == null ||
                document.all('Peoples').value == "0")
             {
                alert("Ͷ������Ϊ�գ�");
                return;
             }
          }
      else
      {
            var varSrc = "&CustomerNo=" + fm.GrpCustomerNo.value;
               varSrc += "&GrpContNo=" + fm.GrpContNo.value;
               varSrc += "&GrpName=" + fm.GrpName.value;
               showInfo = window.open("./FrameMainLCGrpQuery.jsp?Interface= LCGrpQuery.jsp"+varSrc,"",'width=800,height=550,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
      }
       }else{
      //ȡ������������� 2006-02-18 P.D
     // var SQLEx = "select ExecuteCom From lcgeneral where grpcontno='"+document.all('GrpContNo').value+"'";
     mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql66"); 
				mySql.addSubPara(document.all('GrpContNo').value); 
      var tExecuteCom = new Array();
      tExecuteCom = easyExecSql(mySql.getString());
      for(var i = 0;i<tExecuteCom.length;i++){
      //�жϵ�½�����Ƿ��Ǳ���ָ������
      if(tExecuteCom[i][0] == document.all('AllManageCom').value){
      var arrResult = new Array();
     /* var strSQL="select  a.customerno, a.name, g.grpcontno,g.Peoples2, g.bankcode, g.bankaccno, accname, a.AddressNo  " +
      " from lcgrpcont g, LCGrpAppnt a ,lcgeneral b " +
      " where a.grpcontno = g.grpcontno and g.grpcontno = b.grpcontno and g.appflag = '1' "
      + getWherePart("g.AppntNo", "GrpCustomerNo" )
      + getWherePart( "g.GrpContNo","GrpContNo" );*/
      mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql67"); 
				mySql.addSubPara(fm.GrpCustomerNo.value); 
				mySql.addSubPara(fm.GrpContNo.value); 
				mySql.addSubPara(tExecuteCom[i][0]); 
      if(document.all('GrpName').value!=''){
        // strSQL += " and a.Name like '%%"+document.all('GrpName').value+"%%'";
         mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql68"); 
				mySql.addSubPara(fm.GrpCustomerNo.value); 
				mySql.addSubPara(fm.GrpContNo.value); 
				mySql.addSubPara(document.all('GrpName').value); 
				mySql.addSubPara(tExecuteCom[i][0]); 
      }
         //strSQL += " and b.ExecuteCom = '"+tExecuteCom[i][0]+"'";
      arrResult=easyExecSql(mySql.getString());

   if(arrResult != null){
          if(arrResult.length==1)
          {
             try{document.all('GrpCustomerNo').value=arrResult[0][0]}catch(ex){alert(ex.message+"GrpCustomerNo")}
             try{document.all('GrpName').value=arrResult[0][1]}catch(ex){alert(ex.message+"GrpName")}
             try{document.all('GrpContNo').value=arrResult[0][2]}catch(ex){alert(ex.message+"GrpContNo")}
             try{document.all('Peoples').value=arrResult[0][3]}catch(ex){alert(ex.message+"Peoples")}
             if(document.all('Peoples').value == null ||
                document.all('Peoples').value == "0")
             {
                alert("Ͷ������Ϊ�գ�");
                return;
             }
          }
      else
      {
            var varSrc = "&CustomerNo=" + fm.GrpCustomerNo.value;
               varSrc += "&GrpContNo=" + fm.GrpContNo.value;
               varSrc += "&GrpName=" + fm.GrpName.value;
               showInfo = window.open("./FrameMainLCGrpQuery.jsp?Interface= LCGrpQuery.jsp"+varSrc,"",'width=800,height=550,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

      }
       } else {
         alert("û�з������������ݣ�");
         document.all('GrpCustomerNo').value = "";
         document.all('GrpName').value = "";
         document.all('GrpContNo').value = "";
         document.all('Peoples').value = "";
         return;
       }
         }
      }
     }
   }
   else
   {
      alert("�������ѯ����");
   }
   }
}
function afterLCGrpQuery(arrReturn)
{
     document.all('GrpCustomerNo').value=arrReturn[0][0];
     document.all('GrpName').value=arrReturn[0][1];
//     document.all('GrpContNo').value=arrReturn[0][2];
     document.all('Peoples').value=arrReturn[0][3];
}

//����Ϊ��������,��1980-5-9
function getAge(birth)
{
    var oneYear = birth.substring(0,4);
    var age = mNowYear - oneYear;
    if (age <= 0)
    {
        age = 0
    }
    return age;
}


//�ύǰ��У�顢����
function beforeSubmit()
{
	/////////////////////////////////////////////////////////////////////////
	// * ���ӳ����˵ĳ���ԭ�����һ�µ�У��  09-04-17                         //
	/////////////////////////////////////////////////////////////////////////
	var tClmNo = fm.RptNo.value;
	var tCustomerNo = fm.customerNo.value;
	//var tReasonSql = "select AccidentType from LLSubReport where subrptno='"+tClmNo+"' and customerno<>'"+tCustomerNo+"'";
	mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql69"); 
				mySql.addSubPara(tClmNo); 
				mySql.addSubPara(tCustomerNo); 
	var occurReasonResult = easyExecSql(mySql.getString());//prompt("",tReasonSql);
	if(occurReasonResult){
		if(fm.occurReason.value!=occurReasonResult[0][0]){
			alert("�����˵ĳ���ԭ����뱣��һ�£�");
			return false;
		}
	}
    //��ȡ������Ϣ
    var RptNo = fm.RptNo.value;//�ⰸ��
    var CustomerNo = fm.customerNo.value;//�����˱��
    var AccDate = fm.AccidentDate.value;//�¹�����
    var AccReason = fm.occurReason.value;//����ԭ��
    var AccDate2 = fm.AccidentDate.value;//��������
//    var AccDesc = fm.accidentDetail.value;//����ϸ��
    var ClaimType = new Array;
    //��������
    for(var j=0;j<fm.claimType.length;j++)
    {
        if(fm.claimType[j].checked == true)
        {
            ClaimType[j] = fm.claimType[j].value;
        }
    }
    //ȡ����������Ϣ
    var AccYear = AccDate.substring(0,4);
    var AccMonth = AccDate.substring(5,7);
    var AccDay = AccDate.substring(8,10);
    var AccYear2 = AccDate2.substring(0,4);
    var AccMonth2 = AccDate2.substring(5,7);
    var AccDay2 = AccDate2.substring(8,10);
    //----------------------------------------------------------BEG2005-7-12 16:45
    //���������������͹�ϵ�ķǿ�У��
    //----------------------------------------------------------
    if (fm.GrpCustomerNo.value == "" || fm.GrpCustomerNo.value == null)
    {
        alert("����������ͻ��ţ�");
        return false;
    }
    if (fm.GrpName.value == "" || fm.GrpName.value == null)
    {
        alert("�����뵥λ���ƣ�");
        return false;
    }
    if (fm.GrpContNo.value == "" || fm.GrpContNo.value == null)
    {
        alert("���������屣���ţ�");
        return false;
    }
/*
    var tAssigneeZip = fm.AssigneeZip.value;
    if (tAssigneeZip.length > 6)
    {
        alert("�ʱ����");
        return false;
    }
*/

    //2-1 ����������
    if (AccDate2 == null || AccDate2 == '')
    {
        alert("�������ڲ���Ϊ�գ�");
        return false;
    }
    else
    {
        if (AccYear2 > mNowYear)
        {
            alert("�������ڲ������ڵ�ǰ����");
            return false;
        }
        else if (AccYear2 == mNowYear)
        {
            if (AccMonth2 > mNowMonth)
            {

                alert("�¹����ڲ������ڵ�ǰ����");
                return false;
            }
            else if (AccMonth2 == mNowMonth && AccDay2 > mNowDay)
            {
                alert("�������ڲ������ڵ�ǰ����");
                return false;
            }
        }
    }
    //2-2 ����������
    if (AccYear > AccYear2 )
    {
        alert("�¹����ڲ������ڳ�������");
        return false;
    }
    else if (AccYear == AccYear2)
    {
        if (AccMonth > AccMonth2)
        {

            alert("�¹����ڲ������ڳ�������");
            return false;
        }
        else if (AccMonth == AccMonth2 && AccDay > AccDay2)
        {
            alert("�¹����ڲ������ڳ�������");
            return false;
        }
    }
    //3 ������ԭ��
    if (AccReason == null || AccReason == '')
    {
        alert("����ԭ����Ϊ�գ�");
        return false;
    }

    //5 �����������
    if (ClaimType == null || ClaimType == '')
    {
        alert("�������Ͳ���Ϊ�գ�");
        return false;
    }

    return true;
}

//Ԥ�������Ϣ¼��
function operStandPayInfo()
{
     var varSrc ="";
     var CaseNo = fm.RptNo.value;
     pathStr="./StandPayInfoMain.jsp?CaseNo="+CaseNo;
     showInfo = OpenWindowNew(pathStr,"","middle");
}

//������ͳ�Ʋ�ѯ
function QuerydutySetInfo()
{
     var varSrc ="";
     var CaseNo = fm.RptNo.value;
     pathStr="./QuerydutySetInfoMain.jsp?CaseNo="+CaseNo;
     showInfo = OpenWindowNew(pathStr,"","middle");
}

//�ʵ���Ϣ¼��
function StandPaySave()
{
    var row = MedFeeInHosInpGrid.mulLineCount-1;
    var i = 0;
    var arr = new Array();
    var tCustomerNo = '';
    var tClinicStartDate = '';
    var tClinicEndDate = '';
    var tClinicDayCount1 = '';
    var tFeeType = '';
    var numFlag = false;
  if(row < 0){
    alert("�������������ٱ���");
    return false;
    }
  for(var m=0;m<=row;m++ )
  {
   var i = MedFeeInHosInpGrid.getChkNo(m);//�õ�������
   //aler(i);
   //if(i==true)
   //{
    numFlag = true;
   fm.hideOperate.value = "INSERT";
   tCustomerNo = MedFeeInHosInpGrid.getRowColData(m ,1);//�����˺���
   tClinicStartDate = MedFeeInHosInpGrid.getRowColData(m ,9);//�ʵ���ʼ����
   tClinicEndDate = MedFeeInHosInpGrid.getRowColData(m ,10);//�ʵ���������
   tClinicDayCount1 = MedFeeInHosInpGrid.getRowColData(m ,11);//����
   tFeeType = MedFeeInHosInpGrid.getRowColData(m ,2);//�ʵ�����
   tMainFeeNo = MedFeeInHosInpGrid.getRowColData(m ,6);//�ʵ�����
   tClinicMedFeeTypeName = MedFeeInHosInpGrid.getRowColData(m ,12);//��������
   tClinicMedFeeSum = MedFeeInHosInpGrid.getRowColData(m ,14);//ԭʼ����

//�����˺������
  if(tCustomerNo=='')
  {
    alert("������������˺���");
    return false;
  }
//�ʵ�����
  if(tFeeType=='')
  {
    alert("���������ʵ�����");
    return false;
  }
//�ʵ��������
  if(tMainFeeNo=='')
  {
    alert("���������ʵ�����");
    return false;
  }
//�ʵ���ʼ���ڼ���
  if(tClinicStartDate=='')
  {
    alert("�������뿪ʼ����");
    return false;
  }

if(tClinicStartDate != ''){
 if(!isDate(trim(tClinicStartDate)))
  {
    alert('��'+m+'�е��ʵ���ʼ���ڸ�ʽӦΪXXXX-XX-XX');
    return false;
  }
}

if(tClinicEndDate != ''){
 if(!isDate(trim(tClinicEndDate)))
  {
    alert('��'+m+'�е��ʵ��������ڸ�ʽӦΪXXXX-XX-XX');
    return false;
  }
}
//�������ͼ���
//  if(tFeeType == 'A'&& tClinicEndDate != '' )
//  {
//    alert("�������Ͳ�Ӧ�н�������");
//    return false;
//  }
  if(tFeeType == 'B'&& tClinicEndDate == '' )
  {
    alert("סԺ���ͽ������ڲ���Ϊ��");
    return false;
  }
   //���ڼ���
    if (dateDiff(tClinicStartDate,mCurrentDate,'D') < 0 || dateDiff(tClinicEndDate,mCurrentDate,'D') < 0)
    {
        alert("���ڴ���");
        return;
    }
    /*var date4 = dateDiff(fm.AccidentDate.value,tClinicStartDate,'D');
    if(date4 < 0)
    {
       alert("��֤��ʼ�������ڳ�������");
       return false;
    }*/
//�������Ƽ���
  if(tClinicMedFeeTypeName=='')
  {
    alert("���������������");
    return false;
  }
//ԭʼ���ü���
  if(tClinicMedFeeSum=='')
  {
    alert("��������ԭʼ����");
    return false;
  }
// }else
//   {
//      alert ("û��ѡ��Ҫ���������!")
//      return false;  
//   }
   
   
  }

    fm.fmtransact.value="INSERT||MAIN";
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    fm.action ="./LLGrpMedicalFeeInpSave.jsp";
    document.getElementById("fm").submit();
}
//ɾ���ʵ�
function deleteFeeClick()
{
    var numFlag=false;
    var row = MedFeeInHosInpGrid.mulLineCount-1;
    for(var m=0;m<=row;m++)
    {
      var i = MedFeeInHosInpGrid.getChkNo(m);//�õ�������
      if(i==true)
      {
         numFlag = true;
         var feeNo = MedFeeInHosInpGrid.getRowColData(m,20);
         if(feeNo=="")
          {
             alert("����ϸδ���棬����ֱ��ɾ����");
          }
      }
    }
     if(numFlag==false)
    {
        alert("��ѡ��Ҫɾ�����ʵ�");
        return false;
    }
    else
    {
          fm.hideOperate.value = "DELETE";
          fm.action ='./LLGrpMedicalFeeInpSave.jsp';
          submitForm();  
    }     
    
}
function getMedFeeInHosInpGrid()
{
    var i = MedFeeInHosInpGrid.getSelNo() - 1;//�õ�������

    MedFeeInHosInpGrid.setRowColData(i,1,fm.customerNo.value);//�����˱���

}
//Ԥ�����ط���
function getGrpstandpay(){
	//var tSql = "select sum(standpay) from LLStandPayInfo where caseno='"+fm.RptNo.value+"'";//prompt("",tSql);
	mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql70"); 
				mySql.addSubPara(fm.RptNo.value); 
	var standpay = easyExecSql(mySql.getString());
	if(standpay!=null||standpay!=""||standpay!="null"){
		fm.Grpstandpay.value = standpay; 
	}
}

function getin()
{
var tFlag  = "TOSIM"; //��ʾ����[������������]
   var tRgtNo = fm.RptNo.value;
//   var strUrl = "../claim/GrpCustomerDiskInput.jsp?grpcontno="+tRptNo+"&Flag="+tFlag;
   var strUrl = "./GrpCustomerDiskInput.jsp?Flag="+tFlag+"&RgtNo="+tRgtNo; 
   //showInfo=window.open(strUrl,"�������嵥����","");
   showInfo=window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//Ӱ���ѯ
function colQueryImage()
{
  var strUrl="LLColQueryImageMain.jsp?claimNo="+document.all('ClmNo').value+"&subtype=LP1001";
  window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/*��������������������������������������������������������������
  * �޸�ԭ����ӳ�������Ϣɾ������
  * �� �� �ˣ������
  * �޸����ڣ�2006-02-17
  ��������������������������������������������������������������
  */
function deleteClick()
{
    if (confirm("��ȷʵ��ɾ���ü�¼��"))
    {
        alert("ɾ����Ϣ��������ƥ�����㣡");
        fm.fmtransact.value = "DELETE";
        fm.action = './LLGrpRegisterSave.jsp';
        submitForm();
    }
}
/*��������������������������������������������������������������
  * �޸�ԭ����ӳ�������Ϣɾ����ķ����ύ
  * �� �� �ˣ������
  * �޸����ڣ�2006-02-17
  ��������������������������������������������������������������
  */
function afterSubmit4( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail")
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        initForm();
    }
    queryRegister();
    tSaveFlag ="0";
}

//У�����ڸ�ʽ
function checkapplydate(){
  if(fm.AccidentDate.value.length==8){
  if(fm.AccidentDate.value.indexOf('-')==-1){ 
    var Year =     fm.AccidentDate.value.substring(0,4);
    var Month =    fm.AccidentDate.value.substring(4,6);
    var Day =      fm.AccidentDate.value.substring(6,8);
    fm.AccidentDate.value = Year+"-"+Month+"-"+Day;
    if(Year=="0000"||Month=="00"||Day=="00"){
        alert("���������������!");
        fm.AccidentDate.value = ""; 
        return;
      }
  }
} else {var Year =     fm.AccidentDate.value.substring(0,4);
      var Month =    fm.AccidentDate.value.substring(5,7);
      var Day =      fm.AccidentDate.value.substring(8,10);
      if(Year=="0000"||Month=="00"||Day=="00"){
        alert("���������������!");
        fm.AccidentDate.value = "";
        return;
         }
  }
}
//����ת���������ڼ��ж�
function TempCustomer(){
        //var StrSQL = "select grpcontno From lctempcustomer where startdate <= '"+fm.AccidentDate.value+"' and paytodate >= '"+fm.AccidentDate.value+"' and grpcontno = '"+fm.GrpContNo.value+"'";
        mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql71"); 
				mySql.addSubPara(fm.AccidentDate.value); 
				mySql.addSubPara(fm.AccidentDate.value); 
				mySql.addSubPara(fm.GrpContNo.value); 
        var arr= easyExecSql(mySql.getString());
        if(arr != null ){
        return true;
        }else{
        return false;
        }
}
//�жϱ����ڼ�
function showDate(){
        //jixf add 20051213
       /* var strSQL = " select a.polno from LCPol a, LCGrpCont b where  a.CValidate<='"+fm.AccidentDate.value+"' and a.enddate>='"+fm.AccidentDate.value+"'";
        strSQL=strSQL+" and a.GrpContNo=b.GrpContNo and insuredno='"+fm.customerNo.value+"' and a.appflag in ('1','4')";*/
        mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql72"); 
				mySql.addSubPara(fm.AccidentDate.value); 
				mySql.addSubPara(fm.AccidentDate.value); 
				mySql.addSubPara(fm.customerNo.value); 
         if (fm.GrpCustomerNo.value!=null&&fm.GrpCustomerNo.value!='000000')
         {
         //strSQL=strSQL+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
         mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql73"); 
				mySql.addSubPara(fm.AccidentDate.value); 
				mySql.addSubPara(fm.AccidentDate.value); 
				mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.GrpCustomerNo.value); 
         }
        if (fm.GrpContNo.value!=null)
        {
       // strSQL=strSQL+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
        mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql74"); 
				mySql.addSubPara(fm.AccidentDate.value); 
				mySql.addSubPara(fm.AccidentDate.value); 
				mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.GrpCustomerNo.value); 
				mySql.addSubPara(fm.GrpContNo.value); 
        }
        
         var arr= easyExecSql(mySql.getString());
        if(arr != null ){
        return true;
        }else{
        return false;
        }
}

function showScanInfo()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return;
    }
    var tClaimNo = fm.RptNo.value;         //�ⰸ��  
    var tCustNo = fm.customerNo.value;     //�ͻ���
    var tCustomerName = fm.customerName.value;     //�ͻ�����
    
  if (tCustNo == "")
  {
      alert("��ѡ������ˣ�");
      return;
  }
    var strUrl="SubScanClaimQuery.jsp?ClaimNo="+tClaimNo+"&CustomerNo="+tCustNo+"&CustomerName="+tCustomerName;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
}

function AddAffixClick()
{
   var tClmNo=document.all("RptNo").value;
   var urlStr;
   urlStr="./LLInqCourseAffixFrame.jsp?ClmNo="+tClmNo+""; 
   window.open(urlStr,"",'height=100, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');   			
}

function LoadAffixClick()
{
   var tClmNo=document.all("RptNo").value;
   var urlStr;
   urlStr="./LLInqCourseShowAffixFrame.jsp?ClmNo="+tClmNo+""; 
   window.open(urlStr,"",'height=100, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');   			
}

//������Ϣ����
function  PrintReportClass()
{	
	
  document.all('RgtNo').value = document.all('RptNo').value;
	
	fm.action = "./ClaimPrtPDF.jsp?operator=PrintReportClass";
  fm.target=".";
	document.getElementById("fm").submit();
	
}

//�������ⰸ�����г����˼����⸶��Ϣ
function getLastCaseInfo(){
var CaseNo = fm.RptNo.value;
var GrpContNo = fm.GrpContNo.value
if(CaseNo == "" || CaseNo == null){
	alert("���ȵ㱣�棡");
	return false;
}else{
	var row = SubReportGrid.getSelNo();
    if(row < 1) {
        alert("��ѡ��һ�м�¼��");
        return;
    }
    var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
//alert("bb");
//  var tSQL = "select a.clmno,a.grpcontno,a.insuredno,a.insuredname,a.getdutykind,"
// + " b.accdate,b.endcasedate,a.riskcode,a.standpay," 
// + "a.realpay from llclaimpolicy a,llcase b "
// +"where  a.clmno=b.caseno and a.insuredno=b.customerno  and b.rgtstate='60' and "
// +" a.insuredno in (select distinct customerno from llsubreport where subrptno='"
//+ CaseNo + "' ) and a.grpcontno = '" + GrpContNo + "'";
	var tSQL = "select a.clmno,a.grpcontno,a.insuredno,a.insuredname,a.getdutykind,"
		 + " b.accdate,b.endcasedate,a.riskcode,a.standpay," 
		 + "a.realpay from llclaimpolicy a,llcase b "
		 +"where  a.clmno=b.caseno and a.insuredno=b.customerno "
		 //+" and b.rgtstate='60'"
		 +" and "
		 +" a.insuredno = '"+ tCustomerNo +"'"
	     +" and a.clmno <>'"+CaseNo+"'";
		 //+" and a.grpcontno = '" + GrpContNo + "'";
		 /* mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql75"); 
				mySql.addSubPara(tCustomerNo); 
				mySql.addSubPara(CaseNo); 
		  var arr = easyExecSql(mySql.getString())*/
	      var arr = easyExecSql(tSQL);

		  if(!arr){
			  alert("δ��ѯ�������ⰸ��Ϣ���޷�����");
			  return false;
		  }
fm.tSQL.value = tSQL;
  var Title="�����ⰸ��Ϣ��ѯ";
	var SheetName="�����ⰸ��Ϣ��ѯ";       
	var ColName = "�ⰸ��@���屣����@�ͻ���@�ͻ�����@��������@��������@�᰸����@���ִ���@Ӧ�⸶���@ʵ���⸶���";
	fm.action = "./PubCreateExcelSave.jsp?tSQL="+tSQL+"&Title=�����ⰸ��Ϣ��ѯ"+"&SheetName="+Title+"&ColName="+ColName;
	fm.target="../claimgrp";		 	
	document.getElementById("fm").submit();
}   
}

//---------------------------------------------------------------------------------------*
//���ܣ����������߼��ж�
//����1 �������߲С�ҽ������ֻ��ѡһ
//      2 ѡ��������߲�ʱ��Ĭ��ѡ�л���
//---------------------------------------------------------------------------------------*
function callClaimType(ttype)
{
    switch (ttype)
    {
        case "02" : //����
            if (fm.claimType[0].checked == true)
            {
                fm.claimType[4].checked = true;
            }
//            if ((fm.claimType[1].checked == true || fm.claimType[5].checked == true) && fm.claimType[0].checked == true)
//            {
//                alert("�������߲С�ҽ������ֻ��ѡһ!");
//                fm.claimType[0].checked = false;
//                return;
//            }
//            fm.claimType[4].checked = true;
        case "03" :
            if (fm.claimType[1].checked == true)
            {
                fm.claimType[4].checked = true;
            }
//            if ((fm.claimType[0].checked == true || fm.claimType[5].checked == true)&& fm.claimType[1].checked == true)
//            {
//                alert("�������߲С�ҽ������ֻ��ѡһ!");
//                fm.claimType[1].checked = false;
//                return;
//            }
//            fm.claimType[4].checked = true;
        case "09" :
//            if ((fm.claimType[0].checked == true || fm.claimType[1].checked == true) && fm.claimType[4].checked == false)
//            {
//                alert("ѡȡ�������߲б���ѡ�����!");
//                fm.claimType[4].checked = true;
//                return;
//            }
        case "00" :
//            if ((fm.claimType[0].checked == true || fm.claimType[1].checked == true) && fm.claimType[5].checked == true)
//            {
//                alert("�������߲С�ҽ������ֻ��ѡһ!");
//                fm.claimType[5].checked = false;
//                return;
//            }
        default :
        	
        	getChangeDate();
        
            return;
    }
}

/**
 * 2008-11-18
 * zhangzheng
 * ����ѡ����������;���ҽ�Ƴ������ں��������������Ƿ����¼��
 * 
**/
function getChangeDate()
{
	var flag=false;//�����Ƿ�׼��¼�������������ڱ�־,Ĭ�ϲ�׼��¼��
	
	//�������Ͱ���ҽ��ʱ,׼��¼��ҽ�Ƴ�������
	if(fm.claimType[5].checked == true)
	{
	    //document.all('MedicalAccidentDate').disabled=false;
	}
	else
	{
	    //document.all('MedicalAccidentDate').value="";
	    //document.all('MedicalAccidentDate').disabled=true;
	}
	
	//��ֻ����ҽ������ʱ,�����������ڲ�׼��¼��
	var ClaimType = new Array;
    for(var j=0;j<fm.claimType.length;j++)
    {
    	  if((fm.claimType[j].checked == true)&&(j!=5))
    	  {
    		  flag=true;
    	  }
    }
    
    if(flag==true)
    {
    	//document.all('OtherAccidentDate').disabled=false;
    }
    else
    {
	    //document.all('OtherAccidentDate').value="";
    	//document.all('OtherAccidentDate').disabled=true;
    }
}


//¼��ҽ�Ƶ�֤��Ϣ
function showLLMedicalFeeInp()
{

    //var tSel = SubReportGrid.getSelNo();
    //var tClaimNo = SubReportGrid.getRowColData(tSel - 1,8);         //�ⰸ��
    //var tCaseNo = SubReportGrid.getRowColData(tSel - 1,2);          //�ְ���
    //var tCustNo = SubReportGrid.getRowColData(tSel - 1,1);          //�ͻ���
    //if( tSel == 0 || tSel == null ){
//        alert( "����ѡ��һ���ͻ���¼����ִ�д˲���!" );
//        return false;
//    }
//else{
//        window.open("LLMedicalFeeInpInput.jsp?clamNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo+"&custName="+tCustName+"&custSex="+tCustSex,"ҽ�Ƶ�֤��Ϣ");
//    }

    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return;
    }
    var tClaimNo = fm.RptNo.value;         //�ⰸ��
    var tCaseNo = fm.RptNo.value;          //�ְ���
    var tCustNo = fm.customerNo.value;          //�ͻ���
  if (tCustNo == "")
  {
      alert("��ѡ������ˣ�");
      return;
  }
    var strUrl="../claim/LLMedicalFeeInpMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo+"&accDate="+fm.AccidentDate.value+"&medAccDate="+fm.AccidentDate.value+"&otherAccDate="+fm.AccidentDate.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

//����ԭ���ж�
function checkOccurReason()
{
    if (fm.occurReason.value == '1')
    {
        //fm.accidentDetail.disabled = false;
    }
    else if (fm.occurReason.value == '2')
    {
        //fm.accidentDetail.disabled = true;
    }
}


//��˽��۲�ѯ
function queryAudit()
{
    /*var strSql = " select AuditConclusion,AuditIdea,SpecialRemark,AuditNoPassReason,AuditNoPassDesc from LLClaimUWMain where "
               + " ClmNo = '" + fm.RptNo.value + "'";*/
   mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql76"); 
				mySql.addSubPara(fm.RptNo.value);             
//    alert("strSql= "+strSql);
    //prompt("",strSql);
    var tAudit = easyExecSql(mySql.getString());
//    alert(tAudit);
    if (tAudit != null)
    {
        fm.SimpleConclusion2.value = tAudit[0][0];
        fm.AuditIdea.value = tAudit[0][1];
        fm.SpecialRemark1.value = tAudit[0][2];
        showOneCodeName('llexamconclusion2','SimpleConclusion2','SimpleConclusion2Name');//��˽���
        //fm.ProtestReason.value = tAudit[0][3];
        //fm.ProtestReasonDesc.value = tAudit[0][4];
        //showOneCodeName('llprotestreason','ProtestReason','ProtestReasonName');
        //showOneCodeName('llclaimconclusion','AuditConclusion','AuditConclusionName');
        //��ʾ���ز�
        //choiseConclusionType();
        //fm.printAuditRpt.disabled = false;
    }
  //  alert("query audit!");
}


//�����˷���
function showBnf()
{
    var tSel = SubReportGrid.getSelNo();

    if( tSel == 0 || tSel == null ){
        alert( "����ѡ��һ����¼����ִ�д˲���!!!" );
    }
    else{
    	 /**=========================================================================BEG
        �޸�״̬��
        �޸�ԭ���������˷���ǰ����ҽ����������ͱ��������ܽ���У��
        �� �� �ˣ�����
        �޸����ڣ�2005.08.11
       =========================================================================**/
    var tSql ;
    var tClaimNo = fm.RptNo.value;
    
    //��ѯҽ�����������͵Ľ��
   /* tSql = " select nvl(sum(a.RealPay),0) from LLClaimDutyKind a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"
       +" and a.GetDutyKind in ('100','200') "
       ;*/
    mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql77"); 
				mySql.addSubPara(tClaimNo);       
    var arr = easyExecSql( mySql.getString() );    
    var tSumDutyKind = arr[0][0];


    //��ѯ�������ҽ�����������͵Ľ��
  /*  tSql = " select nvl(sum(a.RealPay),0) from LLClaimDetail a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"
       +" and a.GetDutyKind in ('100','200') "
       +" and a.GiveType not in ('1') "       
       ;*/
    mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql78"); 
				mySql.addSubPara(tClaimNo);     
    var brr = easyExecSql( mySql.getString() );    
    
    var tSumDutyCode = brr[0][0];
            
    if (tSumDutyKind != tSumDutyCode)
    {
        alert("��ʾ:ҽ�����⸶�ܽ��Ϊ:["+tSumDutyKind+"],�������⸶�ܽ��Ϊ:["+tSumDutyCode+"],���Ƚ��б�����������,�Ա���б�����!");
        return;
    }
    /**=========================================================================END**/
    
    //���顢�ʱ���������Ͷ���У��,�������㡢��ͬ����
    if (!checkPre())
    {
        return;
    } 
    
    var rptNo = fm.RptNo.value;//�ⰸ��
    var strUrl="../claim/LLBnfMain.jsp?RptNo="+rptNo+"&BnfKind=A&InsuredNo="+fm.customerNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"�����˷���");
    }
   
}

//�򿪷���ʱ�
function showBeginSubmit()
{

  var strUrl="LLSubmitApplyMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&custName="+fm.customerName.value//+"&custVip="+fm.IsVip.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

//�򿪳ʱ���ѯ
function showQuerySubmit()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("��ѡ���ⰸ��");
        return;
    }
    
  //�жϸ��ⰸ�Ƿ���ڳʱ�
    /*var strSQL = "select count(1) from LLSubmitApply where "
                + "ClmNo = '" + fm.RptNo.value+"'";*/
   mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql79"); 
				mySql.addSubPara( fm.RptNo.value);    
    var tCount = easyExecSql(mySql.getString());
    if (tCount == "0")
    {
        alert("���ⰸ��û�гʱ���Ϣ��");
        return;
    }
    var strUrl="LLSubmitQueryMain.jsp?claimNo="+fm.RptNo.value;
//    window.open(strUrl,"�ʱ���ѯ",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    //window.open(strUrl,"�ʱ���ѯ");
    window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}


//�����˷���ǰȷ�������\����\�ʱ�\�����Ƿ����,��������\��ͬ����
function checkPre()
{
//    alert("��ʼ");
    //�����
   /* var strSql0 = "select AffixConclusion from LLAffix where "
               + "RgtNo = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql80"); 
				mySql.addSubPara( fm.RptNo.value);  
    var tAffixConclusion = easyExecSql(mySql.getString());
    
    if (tAffixConclusion)
    {
        for (var i = 0; i < tAffixConclusion.length; i++)
        {
//            alert(tAffixConclusion[i]);
            if (tAffixConclusion[i] == null || tAffixConclusion[i] == "")
            {
//                alert("���������û�����!");
//                return false;
            }
        }
    }
    //����
   /* var strSql1 = "select FiniFlag from LLInqConclusion where "
               + "clmno = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql81"); 
				mySql.addSubPara( fm.RptNo.value);  
    var tFiniFlag = easyExecSql(mySql.getString());
    
    if (tFiniFlag)
    {
        for (var i = 0; i < tFiniFlag.length; i++)
        {
            if (tFiniFlag[i] != '1')
            {
                alert("����ĵ���û�����!");
                return false;
            }
        }
    }
    //�ʱ�

   /* var strSql2 = "select SubState from LLSubmitApply where "
               + "clmno = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql82"); 
				mySql.addSubPara( fm.RptNo.value);  
    var tSubState = easyExecSql(mySql.getString());
    
    if (tSubState)
    {
        for (var i = 0; i < tSubState.length; i++)
        {
            if (tSubState[i] != '1')
            {
                alert("����ĳʱ�û�����!");
                return false;
            }
        }
    }

    //����=============================================================Ŀǰ�д�����
/*
    //��������\��ͬ�����Ƿ����
    var strSql3 = "select conbalflag,condealflag from llclaim where "
                + "clmno = '" + fm.RptNo.value + "'";
    var tFlag = easyExecSql(strSql3);
//    alert(tFlag);
    if (tFlag == null)
    {
        alert("δ���б�������ͺ�ͬ�������!");
        return;
    }
    if (tFlag[0][0] != '1')
    {
        alert("δ���б����������!");
        return;
    }
    if (tFlag[0][1] != '1')
    {
        alert("δ���к�ͬ�������!");
        return;
    }
*/
    return true;
}



//2009-04-22 zhangzheng ˫������������Ӧ����
function afterCodeSelect( cCodeName, Field ) {

    //alert(cCodeName);  
    
	//��˽���
    if(cCodeName=="llexamconclusion2"){
    	
    	//������ԭ��Ϊ�ܸ�ʱ,��վܸ�ԭ��;ܸ�����
    	if(fm.SimpleConclusion2.value !='1'){
    		
    		divLLAudit2.style.display= "none";
       		fm.ProtestReason.value='';
    		fm.ProtestReasonName.value='';
    		fm.ProtestReasonDesc.value='';	
    		fm.SpecialRemark1.value='';
    	}
    	else
    	{
    		//������ⱸע
    		divLLAudit2.style.display= "";
    		fm.SpecialRemark1.value='';
    	}
        return true;
    }

}

//zy 2009-07-28 17:43 ��ȡ������Ʒ������������Ϣ
function getLLEdorTypeCA()
{
//�Ҳ����ŵ���
	 //var tAccRiskCodeSql="select distinct riskcode from lcgrppol where grpcontno='"+fm.GrpContNo.value+"' and riskcode='211901' and grpname like 'MS���ٱ��չɷ����޹�˾%'";
	 mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpBatchAuditInputSql");
				mySql.setSqlId("LLGrpBatchAuditSql83"); 
				mySql.addSubPara( fm.GrpContNo.value); 
	 //prompt("",tAccRiskCodeSql);
   var tAccRiskCode=easyExecSql(mySql.getString());
   if(tAccRiskCode=="211901")
   {
   	fm.AccRiskCode.value=tAccRiskCode;
   }
   else
   {
   	fm.AccRiskCode.value="";   
   }

}

//zy 2009-07-28 14:58 �˻��ʽ����
function ctrllcinsureacc()
{
	
    var strUrl="./LLGrpClaimEdorTypeCAMain.jsp?GrpContNo="+fm.GrpContNo.value+"&RptNo="+fm.RptNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}
