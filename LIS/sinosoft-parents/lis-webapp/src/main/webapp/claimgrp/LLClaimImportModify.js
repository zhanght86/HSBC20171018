var showInfo;
var turnPage = new turnPageClass();
var mySql = new SqlClass();


//��ʼ������
function initQuery()
{

	//��ʾ��

	//********************************************Beg
	//�ÿ���ر�
	//********************************************
	fm.customerName.value = "";
	fm.customerAge.value = "";
	fm.customerSex.value = "";
	fm.SexName.value = "";
	fm.SocialInsuFlag.value = "";
	fm.hospital.value = "";
	fm.TreatAreaName.value = "";
	fm.AccidentDate2.value = "";
	fm.accidentDetail.value = "";
	fm.accidentDetailName.value = "";
//	fm.IsDead.value = "";
//	fm.IsDeadName.value = "";
	fm.claimType.value = "";
	fm.cureDesc.value = "";
	fm.cureDescName.value = "";
	fm.AccResult1.value = "";
	fm.AccResult1Name.value = "";
	fm.AccResult2.value = "";
	fm.AccResult2Name.value = "";
    
	//���������ÿ�
    for (var j = 0;j < fm.claimType.length; j++)
    {
    	  fm.claimType[j].checked = false;
    }
    //********************************************End
    
    var rptNo = fm.ClmNo.value;
    if(rptNo == "")
    {
        alert("�����ⰸΪ�գ�");
        return;
    }

    //ȡ������

    //�����¼��š��¹�����(һ��)
    /*var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";*/

mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql1");
mySql.addSubPara(rptNo );                 
//    alert("strSQL1= "+strSQL1);
    var AccNo = easyExecSql(mySql.getString());
    fm.AccNo.value = AccNo[0][0];
    fm.AccidentDate.value = AccNo[0][1];
    fm.BaccDate.value = AccNo[0][1];
    
//    alert("AccNo= "+AccNo);
    //���������ż�����������Ϣ(һ��)
   /* var strSQL2 = "select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate,operator,mngcom,assigneetype,assigneecode,assigneename,assigneesex,assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion from llregister where "
                + "rgtno = '"+rptNo+"'";*/
mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql2");
mySql.addSubPara(rptNo );     
//    alert("strSQL2= "+strSQL2);
    var RptContent = easyExecSql(mySql.getString());
//    alert("RptContent= "+RptContent);
    //����ҳ������
    fm.AccidentDate.value = AccNo[0][1];

    fm.occurReason.value = RptContent[0][16];
    showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��


    //�����ְ�������������Ϣ(����)
   /* var strSQL3 = "select CustomerNo,Name," 
    	+ " (case trim(Sex) when '0' then '��' when '1' then 'Ů' when '2' then '����' end) as SexNA,"
        + " Birthday,"
        + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end) as �Ƿ����籣��־ "
        + " from LDPerson where "
                + "CustomerNo ='"+fm.customerNo.value+"'";*/
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql3");
mySql.addSubPara(fm.customerNo.value );  
    //prompt("�����ְ�������������Ϣ(����)",strSQL3);
    
    var tSubReportGrid = new Array;
	tSubReportGrid = easyExecSql(mySql.getString());
 
        fm.customerNo.value = tSubReportGrid[0][0];
        fm.customerName.value = tSubReportGrid[0][1];
        fm.customerSex.value = tSubReportGrid[0][2];
        fm.customerAge.value = getAge(tSubReportGrid[0][3]);
        fm.customerBir.value = tSubReportGrid[0][3];
        fm.SocialInsuFlag.value = tSubReportGrid[0][4];
        showOneCodeName('sex','customerSex','SexName');//�Ա�
    

    //��ѯ�����������
    var tClaimType = new Array;
    /*var strSQL1 = "select ReasonCode from LLAppClaimReason where "
                + " CaseNo = '" + rptNo + "'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";*/
     mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql4");
mySql.addSubPara(rptNo);  
mySql.addSubPara(fm.customerNo.value );
    //prompt("��ѯ�����������",strSQL1);
    
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
//        	  	  alert(tClaim);
        	  	  if(fm.claimType[j].value == tClaim)
        	  	  {
                	  fm.claimType[j].checked = true;
            	  }
            }
        }
    }
    
    //��ѯ�ְ�����Ϣ
    var tSubReport = new Array;
   /* var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccdentDesc,AccResult1,AccResult2 from LLCase where 1=1 "
                + getWherePart( 'CaseNo','ClmNo' )
                + getWherePart( 'CustomerNo','customerNo' );*/
mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql5");
mySql.addSubPara(fm.ClmNo.value );
mySql.addSubPara(fm.customerNo.value );
//    alert(strSQL2);
    tSubReport = easyExecSql(mySql.getString());
//    alert(tSubReport);


    fm.hospital.value = tSubReport[0][0];
    fm.AccidentDate2.value = tSubReport[0][1];
    fm.accidentDetail.value = tSubReport[0][2];
//    fm.IsDead.value = tSubReport[0][3];
    fm.cureDesc.value = tSubReport[0][4];
    fm.Remark.value = tSubReport[0][5];
    fm.AccDesc.value = tSubReport[0][6];
    fm.AccResult1.value = tSubReport[0][7];
    fm.AccResult2.value = tSubReport[0][8];
    
    
    showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ
    showOneCodeName('accidentcode','accidentDetail','accidentDetailName');//����ϸ��
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//������ʶ
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//�������
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//���ս��1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//���ս��2
    //��ѯ���ս��2
    queryResult2("AccResult2","AccResult2Name");

    //�޸���

	//********************************************Beg
	//�ÿ���ر�
	//********************************************
	fm.newcustomerName.value = "";
	fm.newcustomerAge.value = "";
	fm.newcustomerSex.value = "";
	fm.newSexName.value = "";
	fm.newSocialInsuFlag.value = "";
	fm.newhospital.value = "";
	fm.newTreatAreaName.value = "";
	fm.newAccidentDate2.value = "";
	fm.newaccidentDetail.value = "";
	fm.newaccidentDetailName.value = "";
//	fm.newIsDead.value = "";
//	fm.newIsDeadName.value = "";
	fm.newclaimType.value = "";
	fm.newcureDesc.value = "";
	fm.newcureDescName.value = "";
	fm.newAccResult1.value = "";
	fm.newAccResult1Name.value = "";
	fm.newAccResult2.value = "";
	fm.newAccResult2Name.value = "";
	//���������ÿ�
    for (var j = 0;j < fm.newclaimType.length; j++)
    {
    	  fm.newclaimType[j].checked = false;
    }
    //********************************************End
    
    var rptNo = fm.ClmNo.value;
    if(rptNo == "")
    {
        alert("�����ⰸΪ�գ�");
        return;
    }

    //ȡ������

    //�����¼��š��¹�����(һ��)
    /*var newstrSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";*/
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql6");
mySql.addSubPara(rptNo);          
//    alert("strSQL1= "+strSQL1);
    var newAccNo = easyExecSql(mySql.getString());
//    alert("AccNo= "+AccNo);
    //���������ż�����������Ϣ(һ��)
   /* var newstrSQL2 = "select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate,operator,mngcom,assigneetype,assigneecode,assigneename,assigneesex,assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion from llregister where "
                + "rgtno = '"+rptNo+"'";*/
//    alert("strSQL2= "+strSQL2);
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql7");
mySql.addSubPara(rptNo);   
    var newRptContent = easyExecSql(mySql.getString());
//    alert("RptContent= "+RptContent);
    //����ҳ������
    fm.newAccidentDate.value = newAccNo[0][1];

    fm.newoccurReason.value = newRptContent[0][16];
    showOneCodeName('lloccurreason','newoccurReason','newReasonName');//����ԭ��
    
    //�����ְ�������������Ϣ(����)
   /* var newstrSQL3 = "select CustomerNo,Name," 
    	+ " (case trim(Sex) when '0' then '��' when '1' then 'Ů' when '2' then '����' end) as SexNA,"
        + " Birthday,"
        + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end) as �Ƿ����籣��־ "
        + " from LDPerson where CustomerNo ='"+fm.customerNo.value+"'";*/
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql8");
mySql.addSubPara(fm.customerNo.value); 
    var newtSubReportGrid = new Array;
	newtSubReportGrid = easyExecSql(mySql.getString());
    
        fm.customerNo.value = newtSubReportGrid[0][0];
        fm.newcustomerName.value = newtSubReportGrid[0][1];
        fm.newcustomerSex.value = newtSubReportGrid[0][2];
        fm.newcustomerAge.value = getAge(newtSubReportGrid[0][3]);
        fm.newcustomerBir.value = newtSubReportGrid[0][3];
        fm.newSocialInsuFlag.value = newtSubReportGrid[0][4];
        showOneCodeName('sex','newcustomerSex','newSexName');//�Ա�
    

    //��ѯ�����������
    var newtClaimType = new Array;
   /* var newstrSQL1 = "select ReasonCode from LLAppClaimReason where "
                + " CaseNo = '" + rptNo + "'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";*/
 mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql9");
mySql.addSubPara(rptNo); 
mySql.addSubPara(fm.customerNo.value); 
//    alert(tClaimType);
    newtClaimType = easyExecSql(mySql.getString());
    if (newtClaimType == null)
    {
    	  alert("��������Ϊ�գ�����˼�¼����Ч�ԣ�");
    	  return;
    }
    else
    {
        for(var j=0;j<fm.newclaimType.length;j++)
        {
        	  for (var l=0;l<newtClaimType.length;l++)
        	  {
        	  	  var tClaim = newtClaimType[l].toString();
        	  	  tClaim = tClaim.substring(tClaim.length-2,tClaim.length);//ȡ�������ͺ���λ
//        	  	  alert(tClaim);
        	  	  if(fm.newclaimType[j].value == tClaim)
        	  	  {
                	  fm.newclaimType[j].checked = true;
            	  }
            }
        }
    }
    //��ѯ�ְ�����Ϣ
    var newtSubReport = new Array;
    /*var newstrSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccdentDesc,AccResult1,AccResult2 from LLCase where 1=1 "
                + getWherePart( 'CaseNo','ClmNo' )
                + getWherePart( 'CustomerNo','customerNo' );*/
   mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql10");
mySql.addSubPara(fm.ClmNo.value); 
mySql.addSubPara(fm.customerNo.value);              
//    alert(strSQL2);
    newtSubReport = easyExecSql(mySql.getString());
//    alert(tSubReport);
    fm.newhospital.value = newtSubReport[0][0];
    fm.newAccidentDate2.value = newtSubReport[0][1];
    fm.newaccidentDetail.value = newtSubReport[0][2];
//    fm.newIsDead.value = newtSubReport[0][3];
    fm.newcureDesc.value = newtSubReport[0][4];
    fm.newRemark.value = newtSubReport[0][5];
    fm.newAccDesc.value = newtSubReport[0][6];
    fm.newAccResult1.value = newtSubReport[0][7];
    fm.newAccResult2.value = newtSubReport[0][8];
    showOneCodeName('commendhospital','newhospital','newTreatAreaName');//����ҽԺ
    showOneCodeName('accidentcode','newaccidentDetail','newaccidentDetailName');//����ϸ��
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//������ʶ
    showOneCodeName('llCureDesc','newcureDesc','newcureDescName');//�������
    showOneCodeName('lldiseases1','newAccResult1','newAccResult1Name');//���ս��1
    showOneCodeName('lldiseases2','newAccResult2','newAccResult2Name');//���ս��2
    //���ս��2
    queryResult2("newAccResult2","newAccResult2Name");

}

//����Ϊ��������,��1980-5-9 
function getAge(birth)
{
    var now = new Date();
    var mNowYear = now.getFullYear();
    var oneYear = birth.substring(0,4);
    var age = mNowYear - oneYear;
    if (age <= 0)
    {
        age = 0
    }
    return age;
}

//
function submitForm()
{
    //�ύ����
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


   // showSubmitFrame(mDebug);
//    fm.fmtransact.value = "INSERT"
    fm.action = './LLClaimImportModifySave.jsp';
    document.getElementById("fm").submit(); //�ύ
    tSaveFlag ="0";    
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content,tAccNo )
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
    	document.all('AccNo').value=tAccNo;
    	//alert(tAccNo);
    	
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

	
		top.opener.queryRegister();
		
		if(fm.recalculationFlag.value=='1')
		{
	        top.opener.showMatchDutyPay2(tAccNo);//�Զ�ƥ�䲢����
		}

    }
    
    //��ʼ��
    tSaveFlag ="0";
    fm.recalculationFlag.value='0';
    fm.cancelMergeFlag.value='0';
}

//���ذ�ť
function goToBack()
{
    location.href="LLClaimImportModifyInput.jsp";
}


//���ذ�ť
function goToBack2()
{
    location.href="LLClaimAuditInput.jsp?claimNo="+fm.ClmNo.value;
}

//�޸Ĳ���
function saveClick()
{
	//����ԭ�򼤻�
    fm.occurReason.disabled=false;
	

	//�������ͼ���
    for (var j = 0;j < fm.claimType.length; j++)
    {
    	  fm.claimType[j].disabled = false;
    }

    //���Ƚ��зǿ��ֶμ���
    if (beforeSubmit())
    {
        //�ж����ֱ��汨�����Ǳ��������
        if (fm.ClmNo.value != "")
        {
        	fm.fmtransact.value = "insert||customer";
        }
        else
        {
            fm.fmtransact.value = "insert||first";
        }
        
        //�������־����������ϲ���Ϣ��־
        checkRecalculation();
        
        
        fm.action = './LLClaimImportModifySave.jsp';
        submitForm();
    }
}


/**=========================================================================
�޸�״̬���޸�
�޸�ԭ���ύǰ��У�顢����
�� �� �ˣ�����
�޸����ڣ�2005.07.14
=========================================================================
**/
function beforeSubmit()
{
	  //��ȡ������Ϣ
	//var RptNo = fm.RptNo.value;//�ⰸ��
	//var CustomerNo = fm.customerNo.value;//�����˱��
	
	var AccDate = fm.newAccidentDate.value;//�¹�����
	var AccReason = fm.newoccurReason.value;//����ԭ��
	
	var AccDesc = fm.newaccidentDetail.value;//����ϸ��
	
	var ClaimType = new Array;
	var EditReason = fm.EditReason.value;//�޸ı�ע
	
	//��������
	for(var j=0;j<fm.newclaimType.length;j++)
	{
		  if(fm.newclaimType[j].checked == true)
		  {
	        ClaimType[j] = fm.newclaimType[j].value;
	    }
	}
	
	var newclaimType=0;//ѡ��������������
	
	
	//��������
	for(var j=0;j<fm.newclaimType.length;j++)
	{   	     	  
		  if(fm.newclaimType[j].checked == true)
		  {
			  newclaimType++;
	      }
	}
	
	
	//5 �����������
	if (newclaimType==0)
	{
	    alert("�������Ͳ���Ϊ�գ�");
	    return false;
	}
	
	//������ҽ����������ʱ��ҪУ��ҽ�Ƴ�������
	if(fm.claimType[5].checked == true)
	{
		//У��ҽ�Ƴ������ں��¹�����
		if (checkDate(fm.newAccidentDate.value,fm.newAccidentDate2.value,"��������") == false)
		{
		     return false;
		}
	}
	
	
	
	//3 ������ԭ��
	if (AccReason == null || AccReason == '')
	{
	    alert("����ԭ����Ϊ�գ�");
	    return false;
	}
	else if (AccReason == "1")
	{
	    //4 �����ʱ�����¼����ϸ��
	    if (AccDesc == null || AccDesc == '')
	    {
	        alert("����ϸ�ڲ���Ϊ�գ�");
	        return false;
	    }
	}    
	
//	
//	//-----------------------------------------------------------**Beg*2005-7-12 16:27
//	//��ӳ���ԭ��Ϊ����ʱ������������Ϊҽ��ʱ���¹����ڵ���ҽ�Ƴ�������
//	//-----------------------------------------------------------**
//	if(fm.newoccurReason.value == "2" && (fm.newAccidentDate.value != fm.newMedicalAccidentDate.value)&& fm.newclaimType[5].checked == true)
//	{
//	    alert("����ԭ��Ϊ����ʱ���¹����ڱ������ҽ�Ƴ������ڣ�");
//	    return false;
//	}
	
	
	//6 ����޸ı�ע
	if (EditReason == null || EditReason == '')
	{
	    alert("�޸ı�ע����Ϊ�գ�");
	    return false;
	}
	//**********************************************Beg*2005-6-13 20:26
	//�������������ѡ��"ҽ��"����ѡ��ҽԺ���ж�
	//**********************************************
	if(fm.newclaimType[5].checked == true && (fm.newhospital.value == "" || fm.newhospital.value == null))
	{
	    alert("��ѡ��ҽԺ��");
	    return false;
	}
	//**********************************************End
	
	//---------------------------------------------------------Beg*2005-6-27 11:55
	//�������������ѡ�л���,����ѡ������,�߲л����ش󼲲�֮һ���ж�
	//---------------------------------------------------------
	
	// add by wanzh begin
//	if (fm.newclaimType[4].checked == true && fm.newclaimType[1].checked == false && fm.newclaimType[0].checked == false && fm.newclaimType[2].checked == false)
//	{
//	    alert("ѡ�л���,����ѡ���������߲л������ش󼲲�֮һ!");
//	    return false;
//	}
//	if (fm.newclaimType[4].checked == true && fm.newclaimType[1].checked == false && fm.newclaimType[0].checked == false && fm.newclaimType[2].checked == false)
//	{
//	    alert("ѡ�л���,����ѡ���������߲л������ش󼲲�֮һ!");
//	    return false;
//	}
	// add by wanzh end
	
	
	
	//---------------------------------------------------------End
	
	
	
	
return true;
}



//*****************************************************
//���ܣ����������߼��ж�
//����1 �������߲С�ҽ������ֻ��ѡһ
//      2 ѡ��������߲�ʱ��Ĭ��ѡ�л���
//*****************************************************
function callClaimType(ttype)
{
    switch (ttype)
    {
        case "02" : //����
            if ((fm.newclaimType[1].checked == true || fm.newclaimType[5].checked == true) && fm.newclaimType[0].checked == true)
            {
                //alert("�������߲С�ҽ������ֻ��ѡһ!");
                //fm.newclaimType[0].checked = false;
                //return;
            }
            if (fm.newclaimType[0].checked == true)
            {
                fm.newclaimType[4].checked = true;
            }
        case "03" :
            if ((fm.newclaimType[0].checked == true || fm.newclaimType[5].checked == true)&& fm.newclaimType[1].checked == true)
            {
                //alert("�������߲С�ҽ������ֻ��ѡһ!");
                //fm.newclaimType[1].checked = false;
                //return;
            }        
            if (fm.newclaimType[1].checked == true)
            {
                fm.newclaimType[4].checked = true;
            }
        case "09" :
//            if ((fm.newclaimType[0].checked == true || fm.newclaimType[1].checked == true) && fm.newclaimType[4].checked == false)
//            {
//                alert("ѡȡ�������߲�ʱϵͳ���Զ�ѡ�����!");
//                fm.newclaimType[4].checked = true;
//                return;
//            }            
        case "00" :
            if ((fm.newclaimType[0].checked == true || fm.newclaimType[1].checked == true) && fm.newclaimType[5].checked == true)
            {
                //alert("�������߲С�ҽ������ֻ��ѡһ!");
                //fm.newclaimType[5].checked = false;
                //return;
            }
        default :
            return;
    }     
}

//�õ�0��icd10��
function saveIcdValue()
{
    fm.ICDCode.value = fm.newAccResult1.value;
}


/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ������Ϊ���ݳ���ԭ��У����ս�������Ϊ1�������¼�룬Ϊ2������¼
    �� �� �ˣ�����
    �޸����ڣ�2005.07.14
   =========================================================================
**/

function checkInsReason()
{

    var tInsReason = fm.newoccurReason.value;
    if ( tInsReason == "1")//���Ϊ����
    {
        fm.newaccidentDetail.disabled = false;
        fm.newaccidentDetailName.disabled = false;
    }
    else if ( tInsReason == "2")//���Ϊ����
    {
        fm.newaccidentDetail.value = "";
        fm.newaccidentDetailName.value = "";        
        fm.newaccidentDetail.disabled = true;
        fm.newaccidentDetailName.disabled = true;
    }        
}


/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ�򣺲�ѯ��ʷ���޸�ԭ��
    �� �� �ˣ�����
    �޸����ڣ�2005.07.14
   =========================================================================
**/
function queryHisEditReason()
{
    
  /*  var strSql = " select a.Operator,a.MakeDate,a.EditReason "
       +" from LLBCase a where 1=1 "
       +" and a.CaseNo = '"+fm.ClmNo.value+"'"
       +" and a.CustomerNo = '"+fm.customerNo.value+"'";     */         
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql11");
mySql.addSubPara(fm.ClmNo.value); 
mySql.addSubPara(fm.customerNo.value);  
    turnPage.pageLineNum=5;    //ÿҳ2��
    turnPage.queryModal(mySql.getString(),HisEditReasonGrid);
        
       
}

//��ѯ���ս��2
function queryResult2(tInput,tInputValue)
{
   /* var strSql = " select ICDName from LDDisease where "
               + " ICDCode = '" + document.all(tInput).value + "'"
               + " order by ICDCode";*/
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql12");
mySql.addSubPara(document.all(tInput).value); 
    var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        document.all(tInputValue).value = tICDName;
    }
}


/**
 * 2009-01-08 zhangzheng
 * �Ƚϱ����Ƿ��޸��˳�������(����ҽ�Ƴ������ں�������������),����ԭ��,�������ͣ��������κ�һ���޸��˶�Ҫ�Զ���������
 * �޸��˳������ںͳ���ԭ��Ҫ��������ϲ���Ϣ!
 */
function checkRecalculation(){
	
	//��ʼ��
	fm.recalculationFlag.value='0';
	fm.cancelMergeFlag.value='0';
	
	
	//�Ƚϳ���ԭ��
	if(fm.newoccurReason.value!=fm.occurReason.value)
	{
		  fm.recalculationFlag.value='1';
		  fm.cancelMergeFlag.value='1'
	}
	else if(fm.AccidentDate2.value!=fm.newAccidentDate2.value)
	{
		  fm.recalculationFlag.value='1';
		  fm.cancelMergeFlag.value='1'
	}
	else
	{
		 //ѭ���ж����������Ƿ�仯
		 var newClaimType = new Array;
         for(var j=0;j<fm.newclaimType.length;j++)
         {
	     	 //alert("fm.newclaimType["+j+"].checked:"+fm.newclaimType[j].checked);
	     	 //alert("fm.claimType["+j+"].checked:"+fm.claimType[j].checked);
	     	 
	  	     if(fm.newclaimType[j].checked != fm.claimType[j].checked)
	  	     {
			  			fm.recalculationFlag.value='1';
			  			break;
	  	     }
         }
	}
	
	//�������ԭ��ͳ��������б仯,���ж��Ƿ���ڰ����ϲ�,���������,����Ȼ��Ϊ0
	if(fm.cancelMergeFlag.value=='1')
	{
		//���ж������Ƿ����ִ�а����ϲ�
	  /*  var strSql = "  select nvl(count(1),0) from lmrisksort a where"
	    	       + "  exists(select 1 from llclaimpolicy b where b.riskcode=a.riskcode "
                   + "  and b.clmno = '" + document.all('ClmNo').value + "' )"
                   + "  and a.risksorttype = '6'";*/
	    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql13");
mySql.addSubPara(document.all('ClmNo').value); 
	    //prompt("�ж������Ƿ����ִ�а����ϲ�",strSql);
	    
		var tFlag = easyExecSql(mySql.getString());
		
		if (tFlag)
		{
			//����0���������ֲ�׼��ִ�а����ϲ�,�򲻱��ж��Ƿ�ִ�а����ϲ�
		    if (tFlag[0][0] ==0)
		    {
		    	fm.cancelMergeFlag.value='0';
		    }
		    else
		    {
		    	//�ж��Ƿ��Ѿ�ִ�й������ϲ�
			   /* strSql = " select nvl(count(1),0),a.caserelano from llcaserela a,llcaserela b  where a.caserelano=b.caserelano"
		                    + " and a.caseno = '" + document.all('ClmNo').value + "' group by a.caserelano";*/
			     mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql14");
mySql.addSubPara(document.all('ClmNo').value); 
			    //prompt("�ж��Ƿ��Ѿ�ִ�й������ϲ���sql",strSql);
			    
				tFlag = easyExecSql(mySql.getString());
				
				if (tFlag)
				{
					//С�ڵ���1��ʾû��ִ�й������ϲ�,�������������ϲ���־
				    if (tFlag[0][0] <=1)
				    {
				    	fm.cancelMergeFlag.value='0';
				    }
				}
		    }
		}
	  
	}
	
	//alert("fm.cancelMergeFlag.value:"+fm.cancelMergeFlag.value);
	//alert("fm.recalculationFlag.value:"+fm.recalculationFlag.value);

}




/*
 * ����У��,У���������ڶ��������ڵ�ǰ���ڣ��ҵ�2�����ڲ������ڵ�1������
 * Date1,����ĵ�һ������,�˴�Ϊ�¹�����
 * Date2 ����ĵڶ�������,�˴��������Ϊҽ�Ƴ������ڻ�������������
 * tDateName ������������ƣ�������ɵ�������ʾ����
 */
function checkDate(tDate1,tDate2,tDateName)
{
    
    var AccDate  =  tDate1;//�¹�����
    var AccDate2 =  tDate2;//������������
   
    //alert("AccDate:"+AccDate);
    //alert("AccDate2:"+AccDate2);
    //alert("tDateName:"+tDateName);
       
    //����¹�����
    if (AccDate == null || AccDate == '')
    {
        alert("�������¹����ڣ�");
        return false;
    }
    else
    {       
      	if (dateDiff(mCurrentDate,AccDate,'D') > 0)
        {
      		alert("�¹����ڲ������ڵ�ǰ����!");
            return false; 
        }
    }
    
    //��֤�¹��������������ϵĴ������ⰸ--------BEG
    if (fm.BaccDate.value == null || fm.BaccDate.value == '')//Modify by zhaorx 2006-07-31
    {
    	fm.BaccDate.value = AccDate; //��������ʱ����У���¹�����
    }    
    
    var OAccDate = fm.BaccDate.value;//ԭ�¹�����Modify by zhaorx 2006-07-04
 
   
    if (OAccDate != AccDate)
    {    
       /* var strSQL = " select count(*) from llreport a left join llregister b on a.rptno = b.rgtno "
                    + " where nvl(clmstate,0) != '70' "
                    + " and rptno in (select caseno from llcaserela where caserelano = '" +	fm.AccNo.value + "')"*/
       mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql15");
mySql.addSubPara(fm.AccNo.value); 
        //prompt("��֤�¹��������������ϵĴ������ⰸ",strSQL);
        
		var tCount = easyExecSql(mySql.getString());
		
        if (tCount != null && tCount != "1" && tCount != "0")
        {
            alert("�¹��������������ϵĴ������ⰸ���������޸��¹����ڣ�");
            fm.newAccidentDate.value = OAccDate;
            return false;
        }
    }
    

    //У���������
    if (AccDate2 == null || AccDate2 == '')
    {
        alert(tDateName+"����Ϊ�գ�");
        return false;
    }
    else
    {
    	
       	//�Ƚϳ������ں͵�ǰ����
    	if (dateDiff(mCurrentDate,AccDate2,'D') > 0)
        {
        	alert(tDateName+"�������ڵ�ǰ����!");
            return false; 
        }

        //�Ƚϳ������ں��¹�����       
    	if (dateDiff(AccDate2,AccDate,'D') > 0)
        {
        	alert(tDateName+"���������¹�����!");
            return false; 
        }

    }
    
    return true;
}


//����ϸ�ڲ�ѯ
function showAccDetail(tCode,tName)
{
	var strUrl="../claim/LLColQueryAccDetailInput.jsp?codeno="+tCode+"&codename="+tName;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//ҽԺģ����ѯ
function showHospital(tCode,tName)
{
	var strUrl="../claim/LLColQueryHospitalInput.jsp?codeno="+tCode+"&codename="+tName;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}