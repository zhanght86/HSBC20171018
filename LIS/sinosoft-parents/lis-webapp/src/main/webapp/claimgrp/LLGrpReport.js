var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var mySql = new SqlClass();
//������
function zhoulei()
{
  alert("������!");
//Q:who's "zhoulei"?? do u know
//A:
}

//���ذ�ť
function goToBack()
{
    location.href="LLGrpReportMissInput.jsp";
    if(fm.isClaimState.value == '1')
    {
     location.href="LLStateQueryInput.jsp";	
    }
}

//�������ο�����Ϣ
function ctrlclaimduty()
{
    var strUrl="../appgrp/CtrlClaimDuty.jsp?ProposalGrpContNo="+fm.GrpContNo.value+"&GrpContNo="+fm.GrpContNo.value+"&LoadFlag=2&LPFlag=1";
    //  window.open(strUrl,"��������������ѯ");
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

    //showInfo = window.open();
    //parent.fraInterface.window.location = "../appgrp/CtrlClaimDuty.jsp?scantype="+scantype+"&MissionID="+MissionID+"&ManageCom="+ManageCom+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&GrpContNo="+fm.GrpContNo.value+"&ProposalGrpContNo="+tGrpContNo+"&LoadFlag="+LoadFlag;
}

//���ý����ϵ�����������Ϊreadonly
function readonlyFormElements()
{
    var elementsNum = 0;//FORM�е�Ԫ����
    //����FORM�е�����ELEMENT
    for (elementsNum=0; elementsNum<fm.elements.length; elementsNum++)
    {
        if (fm.elements[elementsNum].type == "text" || fm.elements[elementsNum].type == "textarea")
        {
            fm.elements[elementsNum].readonly = true;
        }
        if (fm.elements[elementsNum].type == "button")
        {
            fm.elements[elementsNum].disabled = true;
        }
    }
}

//��ʼ����ѯ
function initQuery()
{    
	
	
    //��ѯ��������Ĵ�����Ϣ

    //easyQueryClick();
//alert(49);
	
    var rptNo = fm.ClmNo.value;
    if(rptNo == "")
    {
        alert("�����ⰸΪ�գ�");
        return;
    }

    //�����¼���(һ��)
    var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";
    /*mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpReportInputSql");
	mySql.setSqlId("LLGrpReportSql1");
	mySql.addSubPara(rptNo); */
//    prompt("�����¼���sql:",strSQL1);
    
    var AccNo = easyExecSql(strSQL1);
    
    //���������ż�����������Ϣ(һ��)
    var strSQL2 = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom,Operator,RgtClass,GRPCONTNO,APPNTNO,PEOPLES2,GRPNAME,RiskCode,RgtObj from LLReport where "
                + "RptNo = '"+rptNo+"'";
     /*mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpReportInputSql");
	mySql.setSqlId("LLGrpReportSql2");
	mySql.addSubPara(rptNo); */
//    prompt("���������ż�����������Ϣ(һ��)sql:",strSQL2);
    
    var RptContent = easyExecSql(strSQL2);
    //2007-07-03 jinsh----------------//
    //alert(74)
    var strcondoleflag="select distinct condoleflag from llsubreport where subrptno='"+rptNo+"'";
    /*mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpReportInputSql");
	mySql.setSqlId("LLGrpReportSql3");
	mySql.addSubPara(rptNo); */
    resultstrcondoleflag=easyExecSql(strcondoleflag);
    if(resultstrcondoleflag!=null)
    {
    	fm.strcondoleflag.value=resultstrcondoleflag;
    	//20070704alert("strcondoleflag"+resultstrcondoleflag);
    }
    
	//alert(83);
    //����ҳ������
    if(AccNo!=null){
    	fm.AccNo.value = AccNo[0][0];
    	fm.AccidentDate.value = AccNo[0][1];
    }

    //alert(90);alert("RptContent   "+RptContent);
    if(RptContent!=null){
	    fm.RptNo.value = RptContent[0][0];
	    fm.RptorName.value = RptContent[0][1];
	    fm.RptorPhone.value = RptContent[0][2];
	    fm.RptorAddress.value = RptContent[0][3];
	    fm.Relation.value = RptContent[0][4];
	    fm.RptMode.value = RptContent[0][5];
	    fm.AccidentSite.value = RptContent[0][6];
	    fm.occurReason.value = RptContent[0][7];
	    fm.RptDate.value = RptContent[0][8];
	    fm.MngCom.value = RptContent[0][9];
	    fm.Operator.value = RptContent[0][10];
	
	    fm.GrpContNo.value = RptContent[0][12];
	    fm.GrpCustomerNo.value = RptContent[0][13];
	    fm.Peoples.value = RptContent[0][14];
	    fm.GrpName.value = RptContent[0][15];
	    fm.RiskCode.value = RptContent[0][16];
	    fm.AccFlag.value = RptContent[0][17];
	    showOneCodeName('llrgrelation','Relation','RelationName');//���������¹��˹�ϵ
	    showOneCodeName('llrptmode','RptMode','RptModeName');//������ʽ
	    showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
	    showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ
    }
  


    //---------------------------------------------------*
    //����ҳ��Ȩ��
    //---------------------------------------------------*
    fm.AccidentDate.readonly = true;
    fm.claimType.disabled=true;
    fm.addbutton.disabled=false; 
    fm.deletebutton.disabled=false;
    //fm.QueryPerson.disabled=false;
    fm.DoHangUp.disabled=false;
    fm.CreateNote.disabled=false;
    fm.BeginInq.disabled=false;
    fm.ViewInvest.disabled=false;
     fm.Condole.disabled=false;
    fm.SubmitReport.disabled=false;
    fm.ViewReport.disabled=false;
    fm.AccidentDesc.disabled=false;
//    fm.QueryCont2.disabled=false;
//    fm.QueryCont3.disabled=false;

    //�����ְ�������������Ϣ(����)
    var strSQL4 = "select count(*) from ldperson where "
                + "CustomerNo in("
                + "select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";
   /* mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpReportInputSql");
	mySql.setSqlId("LLGrpReportSql4");
	mySql.addSubPara(rptNo); */
//    prompt("�����ְ�������������Ϣ(����)sql:",strSQL4);
    
    var tCount = easyExecSql(strSQL4);
    
    if( tCount > 0 )
    {//alert(147);
    	var strSQL3 = "select CustomerNo,Name,"
    			+"Sex,"
    			+ " (case trim(Sex) when '0' then '��' when '1' then 'Ů' when '2' then '����' end) as SexNA,"
    			+ "Birthday,"
    			+ " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end) as �Ƿ����籣��־, "
                + " idtype,idno"
    			+ " from ldperson where "
                + "CustomerNo in("
                + "select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";
    	/*mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportInputSql");
		mySql.setSqlId("LLGrpReportSql5");
		mySql.addSubPara(rptNo); */
//    	prompt("�ӱ����ְ���Ϣ�в�ѯ��������Ϣ",strSQL3);
    	
    	easyQueryVer3Modal(strSQL3, SubReportGrid);
    	if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    	{
    		SubReportGridClick(0,rptNo);
    	}//alert(164);
    }
    else
    {
    	//2008-11-01 zhangzheng ��ɾ�������һ��������ʱ����ʼ�������������ϢΪ��
    	initSubReportGrid();
    	fm.customerNo.value = "";
    	fm.customerName.value = "";
        fm.customerSex.value = "";
    	fm.SexName.value = "";
    	fm.customerAge.value = "";
    	fm.hospital.value = ""; 
        fm.TreatAreaName.value = "";
        fm.accidentDetail.value = "";
        fm.accidentDetailName.value = "";
        fm.AccResult2.value = "";
        fm.AccResult2Name.value = "";    
        fm.cureDesc.value = "";
        fm.cureDescName.value = "";
        fm.AccidentDate2.value = "";

        
        for(var j=0;j<fm.claimType.length;j++)
        {
            fm.claimType[j].checked = false;
        }

       
    }
	//alert(193);
    var strSQL5 = "select reasoncode from llreportreason where "
                + "RpNo = '"+rptNo+"'";
    /*mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportInputSql");
		mySql.setSqlId("LLGrpReportSql6");
		mySql.addSubPara(rptNo); */
//    prompt("��ѯ�ⰸ��������",strSQL5);
    var ReasonCode = easyExecSql(strSQL5);
    if (ReasonCode!=null&&ReasonCode!="")
    {
      fm.occurReason.value=	ReasonCode[0][0].substring(0,1);
      showCodeName('occurReason','occurReason','ReasonName');
    }


    
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr,content,Result )
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

        initQuery();
          
        if (Result==null||Result==""||Result==undefined)
        {
        	document.all('RptNo').value= fm.ClmNo.value;
        }
        else
        {
        		document.all('RptNo').value="";
        } 
        
        
        fm.RptConfirm.disabled = false;
        fm.addbutton.disabled = true;

        //2007-06-14saveClick();
    }
    tSaveFlag ="0";
    spanText7.disabled = true;     
    fm.addbutton.disabled = false;   
}


//jinsh------�����ִ���������-------2007-07-02
function afterSubmitDiskInput( FlagStr,content,Result )
{    
	
    showInfo.close();        
   /* if (FlagStr == "Fail" )
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
    { */

        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        initQuery();

        document.all('RptNo').value= fm.ClmNo.value;  

        fm.RptConfirm.disabled = false;

        fm.addbutton.disabled = true;    

        //var wsql="select MissionID,SubMissionID from LWMission where activityid = '0000009005' and processid = '0000000009' and missionprop1 = '"+Result+"'";
		 mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportInputSql");
		mySql.setSqlId("LLGrpReportSql7");
		mySql.addSubPara(Result); 
//        //prompt("�����������غ��ѯ������",wsql);
        var wsub = new Array();
        wsub=easyExecSql(mySql.getString());
        if(!(wsub==null||wsub==''))
        {
        	 document.all('MissionID').value= wsub[0][0];
             document.all('SubMissionID').value= wsub[0][1];  
        }

                 
    //}
    tSaveFlag ="0";
    spanText7.disabled = true;    
}


//����ȷ�Ϸ��غ�ִ�еĲ���
function afterSubmit2( FlagStr, content )
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

        goToBack();
    }
    tSaveFlag ="0";
}

//����ο�ʷ��غ�ִ�еĲ���
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
    tSaveFlag ="0";
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
    try
    {
        initForm();

  	    fm.AccNo.value = "";
 	    fm.RptNo.value = "";
	    fm.RptorName.value = "";
	    fm.RptorPhone.value = "";
	    fm.RptorAddress.value = "";
	    fm.Relation.value = "";
	    fm.RelationName.value = "";
	    fm.RptMode.value = "";
	    fm.RptModeName.value = "";
	    fm.AccidentSite.value = "";
	    fm.occurReason.value = "";
	    fm.RptDate.value = "";
 	    fm.MngCom.value = "";
 	    fm.Operator.value = "";
	    fm.CaseState.value = "";

	    fm.customerNo.value = "";
	    fm.customerAge.value = "";
 	    fm.customerSex.value = "";
 	    fm.SexName.value = "";
	    //fm.IsVip.value = "";
	    //fm.VIPValueName.value = "";
	    fm.AccidentDate.value = "";
 	    fm.occurReason.value = "";
 	    fm.ReasonName.value = "";
	    fm.hospital.value = "";
	    fm.TreatAreaName.value = "";
	    //fm.OtherAccidentDate.value = "";
	    //fm.MedicalAccidentDate.value = "";
 	    fm.accidentDetail.value = "";
 	    fm.accidentDetailName.value = "";
//	 	    fm.IsDead.value = "";
//	 	    fm.IsDeadName.value = "";
 	    fm.claimType.value = "";
 	    fm.cureDesc.value = "";
 	    fm.cureDescName.value = "";
 	    fm.Remark.value = "";
 	    fm.AccDesc.value = "";//�¹�����
 	    fm.AccResult1.value = "";
 	    fm.AccResult1Name.value = "";
 	    fm.AccResult2.value = "";
 	    fm.AccResult2Name.value = "";

        //���������ÿ�
        for (var j = 0;j < fm.claimType.length; j++)
        {
            fm.claimType[j].checked = false;
        }
    }
    catch(re)
    {
        alert("��LLReport.js-->resetForm�����з����쳣:��ʼ���������!");
    }
}

//ȡ����ť��Ӧ����
function cancelForm()
{
}

//�ύǰ��У�顢����
function beforeSubmit()
{
	
	
	//alert("��ʼ�����ύǰУ��!");
	
    //�ж����屣����
    if(fm.GrpContNo.value == ''){
     alert("���屣���Ų���Ϊ��!");
     return false;
    }  
	
    //�ж�����ͻ���
    if(fm.GrpCustomerNo.value == ''){
     alert("����ͻ��Ų���Ϊ��!");
     return false;
    }
    //�жϵ�λ����
    if(fm.GrpName.value == ''){
     alert("��λ���Ʋ���Ϊ��!");
     return false;
    }
     
    
    if (fm.rgtstate[0].checked == false && fm.rgtstate[1].checked == false && fm.rgtstate[2].checked == false )
    {
       fm.addbutton.disabled = false;
       alert("��ѡ�񱨰����ͣ�");
       return;	
    } 
    
    

  //��ȡ������Ϣ
    var RptNo = fm.RptNo.value;//�ⰸ��
    var CustomerNo = fm.customerNo.value;//�����˱��
    var AccReason = fm.occurReason.value;//����ԭ��
    var AccDesc = fm.accidentDetail.value;//����ϸ��
    
    var AccDate = fm.AccidentDate.value;//�¹�����

    var AccDate2 = fm.AccidentDate2.value;//��������

    
    var AccFlag = fm.AccFlag.value;//�Ƿ�ʹ�������ʻ����
    
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
//alert(fm.claimType[5].checked);
//	if(fm.claimType[5].checked == true)
//    {
		//У��������ں��¹�����
		if (checkDate(fm.AccidentDate.value,fm.AccidentDate2.value,"��������") == false)
		{
		     return false;
		}
//    }
    //----------------------------------------------------------BEG
    //���ӱ����������͹�ϵ�ķǿ�У��
    //----------------------------------------------------------
   if (fm.rgtstate[0].checked == true)  
   {    
	   if (fm.RptorName.value == "" || fm.RptorName.value == null)
	   {
		   alert("�����뱨����������");
		   return false;
	   }
	   if (fm.Relation.value == "" || fm.Relation.value == null)
	   {
		   alert("�����뱨����������˹�ϵ��");
		   return false;
	   }

    
    //----------------------------------------------------------end
    //1 �������ˡ���Ϣ
    if (checkInput1() == false)
    {
        return false;
    }
    
    //3 ������ԭ��
    if (AccReason == null || AccReason == '')
    {
        alert("����ԭ����Ϊ�գ�");
        return false;
    }
    
    
    
    
    
    
    //---------------------------------------------------------------------------------**Beg*2005-7-12 16:27
    //��ӳ���ԭ��Ϊ����ʱ���¹����ڵ��ڳ�������
    //---------------------------------------------------------------------------------**

    if(fm.occurReason.value == "2" && (fm.AccidentDate.value != fm.AccidentDate2.value))
    {
        alert("����ԭ��Ϊ����ʱ���¹����ڱ�����ڳ������ڣ�");
        return false;
    }

    //---------------------------------------------------------------------------------**End
    //4 ������ϸ��
    if ((fm.accidentDetail.value == null || fm.accidentDetail.value == '') && fm.occurReason.value == '1')
    {
        alert("����ԭ��Ϊ����,����ϸ�ڲ���Ϊ�գ�");
        return false;
    }
    
    //5 �����������
    if (ClaimType == null || ClaimType == '')
    {
        alert("�������Ͳ���Ϊ�գ�");
        return false;
    }
    //6 ���ҽԺ����
    if (fm.hospital.value == '' && fm.occurReason.value == '2')
    {
        alert("����ԭ��Ϊ����,����ҽԺ���벻��Ϊ�գ�");
        return false;
    }

    //�������������ѡ�л���,����ѡ��������߲�֮һ���ж�
    //---------------------------------------------------------
    //if (fm.claimType[4].checked == true && fm.claimType[1].checked == false && fm.claimType[0].checked == false)
    //{
        //alert("ѡ�л���,����ѡ��������߲�֮һ!");
        //return false;
    //}
    
  } 
  
  else if (fm.rgtstate[1].checked==true )
 {
 		
  if (fm.RptorName.value == "" || fm.RptorName.value == null)
    {
        alert("�����뱨����������");
        return false;
    }

    //1 �������ˡ���Ϣ
    if (checkInput1() == false)
    {
        return false;
    }
    
    
    //3 ������ԭ��
    if (AccReason == null || AccReason == '')
    {
        alert("����ԭ����Ϊ�գ�");
        return false;
    }
    
    
    //---------------------------------------------------------------------------------**Beg*2005-7-12 16:27
    //��ӳ���ԭ��Ϊ����ʱ���¹����ڵ��ڳ�������
    //---------------------------------------------------------------------------------**

    if(fm.occurReason.value == "2" && (fm.AccidentDate.value != fm.AccidentDate2.value))
    {
        alert("����ԭ��Ϊ����ʱ���¹����ڱ�����ڳ������ڣ�");
        return false;
    }

  
    //5 �����������
    if (ClaimType == null || ClaimType == '')
    {
        alert("�������Ͳ���Ϊ�գ�");
        return false;
    }
     
     	
 }  
 else if ( fm.rgtstate[2].checked==true )
 {
 		
    if (fm.RptorName.value == "" || fm.RptorName.value == null)
    {
        alert("�����뱨����������");
        return false;
    }

    //1 �������ˡ���Ϣ
    if (checkInput1() == false)
    {
        return false;
    }
    
    
    //3 ������ԭ��
    if (AccReason == null || AccReason == '')
    {
        alert("����ԭ����Ϊ�գ�");
        return false;
    }
    
    //---------------------------------------------------------------------------------**Beg*2005-7-12 16:27
    //��ӳ���ԭ��Ϊ����ʱ���¹����ڵ��ڳ�������
    //---------------------------------------------------------------------------------**

    if(fm.occurReason.value == "2" && (fm.AccidentDate.value != fm.AccidentDate2.value))
    {
        alert("����ԭ��Ϊ����ʱ���¹����ڱ�����ڳ������ڣ�");
        return false;
    }


  
    //5 �����������
    if (ClaimType == null || ClaimType == '')
    {
        alert("�������Ͳ���Ϊ�գ�");
        return false;
    }
    
    //5 ����Ƿ�ʹ�������ʻ�
    if (AccFlag == null || AccFlag == '')
    {
        alert("�Ƿ�ʹ�������ʻ�����Ϊ�գ�");
        return false;
    }    
    
    //5 ������ֱ���
    if (fm.RiskCode.value == null || fm.RiskCode.value == '')
    {
        alert("���ֱ��벻��Ϊ�գ�");
        return false;
    }        
     
     	
  }    
    //---------------------------------------------------------End
    //---------------------------------------------------------Beg*2005-6-27 13:59
    //���δ�ڳ��պ�ʮ���ڱ������ж�
    //---------------------------------------------------------
    if (dateDiff(fm.AccidentDate.value,mCurrentDate,'D') > 10)
    {
        alert("δ�ڳ��պ�ʮ���ڱ���!");
    }
    //---------------------------------------------------------End

    //alert("�ύǰУ�������!");
    return true;
}

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
    if(cDebug=="1")
    {
        parent.fraMain.rows = "0,0,0,0,*";
    }
     else
     {
        parent.fraMain.rows = "0,0,0,0,*";
     }
}

//��������
function addClick()
{
    resetForm();
}

//��������Ϣ�޸�
function updateClick()
{
	var selno = SubReportGrid.getSelNo() - 1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ�޸ĵĿͻ���");
	      return;
	}
	/////////////////////////////////////////////////////////////////////////
	// * ���ӳ����˵ĳ���ԭ�����һ�µ�У��  09-04-17                         //
	/////////////////////////////////////////////////////////////////////////
	var tClmNo = fm.RptNo.value;
	var tCustomerNo = fm.customerNo.value;
	//var tReasonSql = "select AccidentType from LLSubReport where subrptno='"+tClmNo+"' and customerno<>'"+tCustomerNo+"'";
	mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportInputSql");
		mySql.setSqlId("LLGrpReportSql8");
		mySql.addSubPara(tCustomerNo); 
	var occurReasonResult = easyExecSql(mySql.getString());//prompt("",tReasonSql);
	if(occurReasonResult){
		if(fm.occurReason.value!=occurReasonResult[0][0]){
			alert("���пͻ��ĳ���ԭ����뱣��һ�£�");
			return false;
		}
	}
    if (confirm("��ȷʵ���޸ĸü�¼��"))
    {
        if (beforeSubmit() == true)
        {
              //jixf add 20051213
                /*var strSQL = " select a.polno from LCPol a, LCGrpCont b where  a.CValidate<='"+fm.AccidentDate.value+"' and a.enddate>='"+fm.AccidentDate.value+"'"
                            +" and a.GrpContNo=b.GrpContNo and insuredno='"+fm.customerNo.value+"' and a.appflag in ('1','4')";*/
                mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql9");
				mySql.addSubPara(fm.AccidentDate.value); 
				mySql.addSubPara(fm.AccidentDate.value); 
				mySql.addSubPara(fm.customerNo.value); 
                if (fm.GrpCustomerNo.value!=null&&fm.GrpCustomerNo.value!='000000')
                {
                     //strSQL=strSQL+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
                     mySql = new SqlClass();
					mySql.setResourceName("claimgrp.LLGrpReportInputSql");
					mySql.setSqlId("LLGrpReportSql10");
					mySql.addSubPara(fm.AccidentDate.value); 
					mySql.addSubPara(fm.AccidentDate.value); 
					mySql.addSubPara(fm.customerNo.value); 
					mySql.addSubPara(fm.GrpCustomerNo.value); 
                }
                
                if (fm.GrpContNo.value!=null)
                {
                   // strSQL=strSQL+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
                    mySql = new SqlClass();
					mySql.setResourceName("claimgrp.LLGrpReportInputSql");
					mySql.setSqlId("LLGrpReportSql11");
					mySql.addSubPara(fm.AccidentDate.value); 
					mySql.addSubPara(fm.AccidentDate.value); 
					mySql.addSubPara(fm.customerNo.value); 
					mySql.addSubPara(fm.GrpCustomerNo.value); 
					mySql.addSubPara(fm.GrpContNo.value); 
                }
                
//                prompt("У��ÿͻ�����δ��Ч����γ������ڲ��ڸñ������˵ı����ڼ��ڸÿͻ�����δ��Ч����γ������ڲ��ڸñ������˵ı����ڼ���sql",strSQL);
                
                var arr= easyExecSql(mySql.getString());
                if ( arr == null )
                {
                     alert("�ÿͻ�����δ��Ч����γ������ڲ��ڸñ������˵ı����ڼ��ڣ�");             
                     return false;
                }

               var strSQLBQ = " select a.polno from LPEdorItem a, LCGrpCont b where  a.EdorValidate>='"+fm.AccidentDate.value+"'"
                              +" and a.GrpContNo=b.GrpContNo and a.insuredno='"+fm.customerNo.value+"'"
                              +" and a.EdorType in ('AA','PT','IC','LC')��and EdorState='0'";
                /*mySql = new SqlClass();
					mySql.setResourceName("claimgrp.LLGrpReportInputSql");
					mySql.setSqlId("LLGrpReportSql12");
					mySql.addSubPara(fm.AccidentDate.value);  
					mySql.addSubPara(fm.customerNo.value);  */           
                if (fm.GrpCustomerNo.value!=null)
                {
                     strSQLBQ=strSQLBQ+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
                     /*mySql = new SqlClass();
					mySql.setResourceName("claimgrp.LLGrpReportInputSql");
					mySql.setSqlId("LLGrpReportSql13");
					mySql.addSubPara(fm.AccidentDate.value);  
					mySql.addSubPara(fm.customerNo.value);     
					mySql.addSubPara(fm.GrpCustomerNo.value);    */ 
                }
                
                if (fm.GrpContNo.value!=null)
                {
                    strSQLBQ=strSQLBQ+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
                   /*  mySql = new SqlClass();
					mySql.setResourceName("claimgrp.LLGrpReportInputSql");
					mySql.setSqlId("LLGrpReportSql14");
					mySql.addSubPara(fm.AccidentDate.value);  
					mySql.addSubPara(fm.customerNo.value);     
					mySql.addSubPara(fm.GrpCustomerNo.value);   
					mySql.addSubPara(fm.GrpContNo.value);  */
                }
                
//                prompt("���ؾ��棺�ñ��������ڳ�������֮���������ܸ��ı���ı�ȫsql",strSQLBQ);
                var arrbq= easyExecSql(strSQLBQ);
                if ( arrbq != null )
                {
                     alert("���ؾ��棺�ñ��������ڳ�������֮���������ܸ��ı���ı�ȫ��");             
                }
               
                var trgtstate;
                for (var i = 0; i < fm.rgtstate.length; i++)
                {
                	if (fm.rgtstate[i].checked==true)
                	{
                		trgtstate=fm.rgtstate[i].value;
                		//alert("trgtstate:"+trgtstate);
                	} 
                }                               
            
	            fm.fmtransact.value = "update";
	            fm.action = "LLGrpReportSave.jsp?rgtstate="+trgtstate; 
	            submitForm();
        }
    }
    else
    {
        mOperate="";
    }
}

//������ɾ������Ҫ�жϣ�����������ɾ��
function deleteClick()
{
    //alert("���޷�����ɾ��������");
	var selno = SubReportGrid.getSelNo() - 1;
	if (selno <0)
	{
	      alert("��ѡ���ɾ���Ŀͻ���");
	      return;
	}

	
    if (confirm("��ȷʵ��ɾ���ü�¼��"))
    {
        if (beforeSubmit() == true)
        {
              //jixf add 20051213

                /*var strSQLBQ = " select a.polno from LPEdorItem a, LCGrpCont b where  a.EdorValidate>='"+fm.AccidentDate.value+"'"
                             +" and a.GrpContNo=b.GrpContNo and a.insuredno='"+fm.customerNo.value+"'"
                             +" and a.EdorType in ('AA','PT','IC','LC')��and EdorState='0'"*/
               mySql = new SqlClass();
					mySql.setResourceName("claimgrp.LLGrpReportInputSql");
					mySql.setSqlId("LLGrpReportSql15");
					mySql.addSubPara(fm.AccidentDate.value);  
					mySql.addSubPara(fm.customerNo.value);     
					
                if (fm.GrpCustomerNo.value!=null)
                {
                    //strSQLBQ=strSQLBQ+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'"
                    mySql = new SqlClass();
					mySql.setResourceName("claimgrp.LLGrpReportInputSql");
					mySql.setSqlId("LLGrpReportSql16");
					mySql.addSubPara(fm.AccidentDate.value);  
					mySql.addSubPara(fm.customerNo.value);   
					mySql.addSubPara(fm.GrpCustomerNo.value);   
                }
                
                if (fm.GrpContNo.value!=null)
                {
                    //strSQLBQ=strSQLBQ+" and b.GrpContNo='"+fm.GrpContNo.value+"'"
                    mySql = new SqlClass();
					mySql.setResourceName("claimgrp.LLGrpReportInputSql");
					mySql.setSqlId("LLGrpReportSql17");
					mySql.addSubPara(fm.AccidentDate.value);  
					mySql.addSubPara(fm.customerNo.value);   
					mySql.addSubPara(fm.GrpCustomerNo.value);  
					mySql.addSubPara(fm.GrpContNo.value);  
                }
                
                //prompt("�жϳ������Ƿ�������ȫ��У��:",strSQLBQ);

                var arrbq= easyExecSql(mySql.getString());

                if ( arrbq != null )
                {
                    alert("���ؾ��棺�ñ��������ڳ�������֮���������ܸ��ı���ı�ȫ��");             
                }
             
                fm.fmtransact.value = "delete";
                fm.action = './LLGrpReportSave.jsp';
                submitForm();
        }
    }
    else
    {
        mOperate="";
    }
    
}

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow)
{
    if (cShow=="true")
    {
        cDiv.style.display="";
    }
    else
    {
        cDiv.style.display="none";
    }
}

//������ѯ
//���ա��ͻ��š���LCpol�н��в�ѯ����ʾ�ÿͻ��ı�����ϸ
function showInsuredLCPol()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("��ѡ��һ�м�¼��");
//        return;
//    }
  var tCustomerNo = fm.customerNo.value;
  if (tCustomerNo == "")
  {
      alert("��ѡ��һ���ͻ���Ϣ��");
      return;
  }
    var strUrl="LLLcContQueryMain.jsp?AppntNo="+tCustomerNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�����ⰸ��ѯ
function showOldInsuredCase()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("��ѡ��һ�м�¼��");
//        return;
//    }
  var tCustomerNo = fm.customerNo.value;
  var tClmNo = fm.RptNo.value;
  if (tCustomerNo == "")
  {
      alert("��ѡ��һ���ͻ���Ϣ��");
      return;
  }
    var strUrl="LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo+"&ClmNo="+tClmNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//������Ϣ��ѯ
function showCard(){
    var strUrl="../card/CardContInput.jsp?flag=1";//flag = 1 ������ѯ���Button������ʾ
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
//���¼���ѯ����
function afterQueryLL2(tAccNo,tOccurReason,tAccDesc,tAccidentSite)
{
  //�õ�����ֵ
    fm.AccNo.value = tAccNo;
    fm.occurReason.value = tOccurReason;
    fm.AccDesc.value = tAccDesc;
    fm.AccDescDisabled.value = tAccDesc;
    fm.AccidentSite.value = tAccidentSite;
    showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
}

//�ɿͻ���ѯ��LLLdPersonQuery.js�����ص�����¼ʱ����
function afterQueryLL(arr)
{
    //�õ�����ֵ
    fm.customerNo.value = arr[0];

    fm.customerName.value = arr[1];

    fm.customerSex.value = arr[2];
    fm.SexName.value = arr[3];
    
    fm.customerAge.value = arr[4];
    
    //alert("fm.customerSex.value: "+fm.customerSex.value)
    //alert("fm.SexName.value: "+fm.SexName.value)

    
    //����Ͷ������Ϣ
    fm.GrpCustomerNo.value=arr[8];
    fm.GrpName.value=arr[9];
    fm.GrpContNo.value=arr[10];
    
    //fm.IsVip.value = '';
    showOneCodeName('sex','customerSex','SexName');//�Ա�
   //��ʼ��¼����
    fm.hospital.value = ""; 
    fm.TreatAreaName.value = "";
    fm.accidentDetail.value = "";
    fm.accidentDetailName.value = "";
    fm.AccResult2.value = "";
    fm.AccResult2Name.value = "";    
    fm.cureDesc.value = "";
    fm.cureDescName.value = "";
    for(var j=0;j<fm.claimType.length;j++)
    {
        fm.claimType[j].checked = false;
    }
    //���ÿɲ�����ť
    fm.addbutton.disabled = false;
    fm.deletebutton.disabled = false;
    fm.disabled = false;
//    fm.QueryCont3.disabled = false;

}

//�����˲�ѯ
function showInsPerQuery()
{
	
      var tGrpContNo  = fm.GrpContNo.value ;

      var tGrpCustomerNo = fm.GrpCustomerNo.value;

      var tGrpName = fm.GrpName.value;
      
      if (fm.rgtstate[0].checked == false && fm.rgtstate[1].checked == false && fm.rgtstate[2].checked == false )
      {
         fm.addbutton.disabled = false;
         alert("����ѡ�񱨰����ͣ�");
         return;	
      } 
      
      if(tGrpContNo == "")
      {
    	  	 alert("�����������屣����!");
             return;
      }
      else
      {
            var strUrl = "LLGrpLdPersonQueryMain.jsp?GrpContNo="+tGrpContNo+"&GrpCustomerNo="+tGrpCustomerNo+"&GrpName="+tGrpName+"&ManageCom="+document.allManageCom.value;
            //window.open(strUrl);
            window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
      }

}

//��������
function showLcContSuspend()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return;
    }
    var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
    var strUrl="LLLcContSuspendMain.jsp?InsuredNo="+tCustomerNo+"&ClmNo="+fm.ClmNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�����ɵ�֤֪ͨ��
function showAffix()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("��ѡ��һ���ͻ���Ϣ��");
        return;
    }
    var tClaimNo = fm.RptNo.value;         //�ⰸ��
    var tCaseNo = fm.RptNo.value;          //�ְ���
    var tCustNo = fm.customerNo.value;          //�ͻ���
    if (tCustNo == "")
    {
    	alert("��ѡ��ͻ���");
    	return;
    }
  
    var claimTypes=getClaimTypes();

    var strUrl="LLRptMAffixListMain.jsp?claimTypes="+claimTypes+"&CaseNo="+tCaseNo+"&whoNo="+tCustNo+"&Proc=Rpt";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�򿪷������
function showBeginInq()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("��ѡ���ⰸ��");
        return;
    }
    
   // var JustStateSql="select COUNT(*) from lwmission where activityid in ('0000009125','0000009145','0000009165','0000009175') and missionprop1='"+fm.RptNo.value+"'";
     mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpReportInputSql");
	mySql.setSqlId("LLGrpReportSql18");
	mySql.addSubPara(fm.RptNo.value);  
    var JustStateCount=easyExecSql(mySql.getString());
    if(parseInt(JustStateCount)>0)
    {      				
    		alert("�ð����Ѿ�������飬�벻Ҫ�ظ�����!");
    		return;
    }    
    
    var strUrl="../claimgrp/LLInqApplyMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&custName="+fm.customerName.value+/*"&custVip="+fm.IsVip.value+*/"&initPhase=01";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�鿴����
function showQueryInq()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("��ѡ���ⰸ��");
        return;
    }
  //---------------------------------------
  //�жϸ��ⰸ�Ƿ���ڵ���
  //---------------------------------------
   /* var strSQL = "select count(1) from LLInqConclusion where "
                + "ClmNo = '" + fm.RptNo.value+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpReportInputSql");
	mySql.setSqlId("LLGrpReportSql19");
	mySql.addSubPara(fm.RptNo.value);  
    var tCount = easyExecSql(mySql.getString());
    if (tCount == "0")
    {
        alert("���ⰸ��û�е�����Ϣ��");
        return;
    }
    var strUrl="LLInqQueryMain.jsp?claimNo="+fm.RptNo.value;
      window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//����ο��
function showCondole()
{
    if(SubReportGrid.getSelNo() <= 0)
    {
        alert("��ѡ������ˣ�");
        return;
    }
  //---------------------------------------
  //�жϸ÷ְ��Ƿ�������ο��
  //---------------------------------------
    var strSQL = "select CondoleFlag from LLSubReport where "
                + " SubRptNo = '" + fm.RptNo.value + "'";
                + " and CustomerNo = '" + fm.customerNo.value + "'";
    /*mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpReportInputSql");
	mySql.setSqlId("LLGrpReportSql20");
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(fm.customerNo.value);   */
    var tCondoleFlag = easyExecSql(strSQL);
    if (tCondoleFlag == "1")
    {
        alert("������ο�ʣ�");
        return;
    }
    fm.action = './LLReportCondoleSave.jsp';
    submitForm();
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
    //---------------------------------------
    //�жϸ��ⰸ�Ƿ���ڳʱ�
    //---------------------------------------
    /*var strSQL = "select count(1) from LLSubmitApply where "
                + "ClmNo = " + fm.RptNo.value;*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpReportInputSql");
	mySql.setSqlId("LLGrpReportSql21");
	mySql.addSubPara(fm.RptNo.value); 
	 
    var tCount = easyExecSql(mySql.getString());
    //alert(tCount);
    if (tCount == "0")
    {
        alert("���ⰸ��û�гʱ���Ϣ��");
        return;
    }
    
    var strUrl="LLSubmitQueryMain.jsp?claimNo="+fm.RptNo.value;
      window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//���¹�������Ϣ
function showClaimDesc()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("��ѡ���ⰸ��");
        return;
    }
    var strUrl="LLClaimDescMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&startPhase=01";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

////�ͻ���ϸ��Ϣ��ѯ
//function showCustomerInfo()
//{
//  window.open("../sys/FrameCQPersonQuery.jsp");
//}

//������ѯ
function IsReportExist()
{
    //�������ˡ���Ϣ
    if (checkInput1() == false)
    {
        return;
    }
    queryReport();

}

//�������ˡ���Ϣ
function checkInput1()
{
	//alert("1113-checkInput1");
	
    //��ȡ������Ϣ
    var CustomerNo = fm.customerNo.value;//�����˱��
    var AccDate = fm.AccidentDate.value;//�¹�����
    //ȡ����������Ϣ
    var AccYear = AccDate.substring(0,4);
    var AccMonth = AccDate.substring(5,7);
    var AccDay = AccDate.substring(8,10);
    //����������Ϣ
    if (CustomerNo == null || CustomerNo == '')
    {
        alert("����ѡ������ˣ�");
        return false;
    }
    //����¹�����
    if (AccDate == null || AccDate == '')
    {
        alert("�¹����ڲ���Ϊ�գ�");
        return false;
    }
    else
    {
        if (AccYear > mNowYear)
        {
            alert("�¹����ڲ������ڵ�ǰ����");
            return false;
        }
        else if (AccYear == mNowYear)
        {
            if (AccMonth > mNowMonth)
            {

                alert("�¹����ڲ������ڵ�ǰ����");
                return false;
            }
            else if (AccMonth == mNowMonth && AccDay > mNowDay)
            {
                alert("�¹����ڲ������ڵ�ǰ����!");
                return false;
            }
        }
    }
    
    //alert("1113-checkInput1-End!");
    return true;
}

//����ԭ���ж�
function checkOccurReason()
{
    if (fm.occurReason.value == '1')
    {
        fm.accidentDetail.disabled = false;
    }
    else if (fm.occurReason.value == '2')
    {
        fm.accidentDetail.disabled = true;
    }
}

//������ѯ
function queryReport()
{
  var AccNo;
  var RptContent = new Array();
  var queryResult = new Array();
    //�����¼���(һ��)
   /* var strSQL1 = "select AccNo from LLAccident where "
                + "AccDate = to_date('"+fm.AccidentDate.value
                + "','yyyy-mm-dd') "
                + getWherePart( 'AccType','occurReason' )
                + " and AccNo in (select AccNo from LLAccidentSub where 1=1 "
                + getWherePart( 'CustomerNo','customerNo' )
                + ")";*/
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpReportInputSql");
	mySql.setSqlId("LLGrpReportSql22");
	mySql.addSubPara(fm.AccidentDate.value); 
	mySql.addSubPara(fm.occurReason.value); 
	mySql.addSubPara(fm.customerNo.value); 
    AccNo = decodeEasyQueryResult(easyQueryVer3(mySql.getString()));
    if (AccNo == null )
    {
        fm.isReportExist.value = "false";
        alert("����������!");
        return
    }
    else
    {
        //���������ż�����������Ϣ(һ��)
       /* var strSQL2 = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom,Operator,RgtClass from LLReport where "
                    + "RptNo in (select CaseNo from LLCaseRela where "
                    + "CaseRelaNo = '"+ AccNo +"' and SubRptNo in (select SubRptNo from LLSubReport where 1=1 "
                    + getWherePart( 'CustomerNo','customerNo' )
                    + "))";*/
        mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpReportInputSql");
	mySql.setSqlId("LLGrpReportSql23");
	mySql.addSubPara(AccNo); 
	mySql.addSubPara(fm.customerNo.value); 
        RptContent = decodeEasyQueryResult(easyQueryVer3(mySql.getString()));
        if (RptContent == null)
        {
            fm.isReportExist.value = "false";
            alert("����������!");
            return;
        }
        else
        {
            //����ҳ������
            fm.AccNo.value = AccNo;
            fm.RptNo.value = RptContent[0][0];
            fm.RptorName.value = RptContent[0][1];
            fm.RptorPhone.value = RptContent[0][2];
            fm.RptorAddress.value = RptContent[0][3];
            fm.Relation.value = RptContent[0][4];
            fm.RptMode.value = RptContent[0][5];
            fm.AccidentSite.value = RptContent[0][6];
            fm.occurReason.value = RptContent[0][7];
            fm.RptDate.value = RptContent[0][8];
            fm.MngCom.value = RptContent[0][9];
            fm.Operator.value = RptContent[0][10];
            fm.RgtClass.value = RptContent[0][11];
            showOneCodeName('llrgtclass','RgtClass','RgtClassName'); //�������
            showOneCodeName('llrgrelation','Relation','RelationName');//���������¹��˹�ϵ
            showOneCodeName('RptMode','RptMode','RptModeName');//������ʽ
            showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
            showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ
            //����ҳ��Ȩ��
            fm.AccidentDate.readonly = true;
            fm.claimType.disabled=true;

            fm.addbutton.disabled=false;
            fm.deletebutton.disabled=false;
            //fm.QueryPerson.disabled=false;
            fm.DoHangUp.disabled=false;
            fm.CreateNote.disabled=false;
            fm.BeginInq.disabled=false;
            fm.ViewInvest.disabled=false;
            fm.Condole.disabled=false;
            fm.SubmitReport.disabled=false;
            fm.ViewReport.disabled=false;
            fm.AccidentDesc.disabled=false;
//            fm.QueryCont2.disabled=false;
//            fm.QueryCont3.disabled=false;
        //����ԭ��У��
        checkOccurReason();

            //�����ְ�������������Ϣ(����)
           /* var strSQL3 = " select CustomerNo,Name,"
                        + " Sex,"
                        + " (case trim(Sex) when '0' then '��' when '1' then 'Ů' when '2' then '����' end) as SexNA,"
                        + " Birthday,"
                        + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                        + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end) as �Ƿ����籣��־, "
                        + " idtype,idno"
                        + " from LDPerson where "
                        + " CustomerNo in("
                        + " select CustomerNo from LLSubReport where SubRptNo = '"+ RptContent[0][0] +"')";*/
//            personInfo = decodeEasyQueryResult(easyQueryVer3(strSQL3));
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpReportInputSql");
	mySql.setSqlId("LLGrpReportSql24");
	mySql.addSubPara(RptContent[0][0]); 
	
            easyQueryVer3Modal(mySql.getString(), SubReportGrid);
        }
    }
}

//�¼���ѯ
//2005-8-1 16:08 �޸�:ȥ������ԭ��Ĳ�ѯ��������λ�¼�
function queryLLAccident()
{
  //�ǿռ��
  if (checkInput1() == false)
    {
        return;
    }
//    if (fm.occurReason.value == "")
//    {
//        alert("����ԭ��Ϊ�գ�");
//        return;
//    }
    //�����¼���
    /*var strSQL = "select AccNo from LLAccident where "
                + "AccDate = to_date('"+fm.AccidentDate.value
                + "','yyyy-mm-dd') and AccNo in (select AccNo from LLAccidentSub where 1=1 "
                + getWherePart( 'CustomerNo','customerNo' )
//                + getWherePart( 'AccType','occurReason' )
                + ")";*/
  mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpReportInputSql");
	mySql.setSqlId("LLGrpReportSql25");
	mySql.addSubPara(fm.AccidentDate.value); 
	mySql.addSubPara(fm.customerNo.value); 
    var tAccNo = easyExecSql(mySql.getString());
    if (tAccNo == null)
    {
        alert("û������¼���");
        return;
    }
    //���¼���ѯ����
//  var strUrl="LLAccidentQueryMain.jsp?AccDate="+fm.AccidentDate.value+"&CustomerNo="+fm.customerNo.value+"&AccType="+fm.occurReason.value;
  var strUrl="LLAccidentQueryMain.jsp?AccDate="+fm.AccidentDate.value+"&CustomerNo="+fm.customerNo.value;
window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//[����]��ť��Ӧ����
function toSaveClick()
{
    //�ж��Ƿ����������޸�
    if(fm.isNew.value == '1')
    {
      updateClick();
    }
    else
    {
        saveClick();
    }
}

//�������
function saveClick()
{

	/////////////////////////////////////////////////////////////////////////
	// * ���ӳ����˵ĳ���ԭ�����һ�µ�У��  09-04-17                         //
	/////////////////////////////////////////////////////////////////////////
	var tClmNo = fm.RptNo.value;
	var tCustomerNo = fm.customerNo.value;
	var tReasonSql = "select AccidentType from LLSubReport where subrptno='"+tClmNo+"' and customerno<>'"+tCustomerNo+"'";
	/*mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpReportInputSql");
	mySql.setSqlId("LLGrpReportSql26");
	mySql.addSubPara(tClmNo); 
	mySql.addSubPara(tCustomerNo); */
	var occurReasonResult = easyExecSql(tReasonSql);//prompt("",tReasonSql);
	if(occurReasonResult){
		if(fm.occurReason.value!=occurReasonResult[0][0]){
			alert("���пͻ��ĳ���ԭ����뱣��һ�£�");
			return false;
		}
	}
    //���Ƚ��зǿ��ֶμ���
    if (beforeSubmit() == true)
    {
    	
        if (fm.rgtstate[0].checked == false && fm.rgtstate[1].checked == false && fm.rgtstate[2].checked == false )
        {
           fm.addbutton.disabled = false;
           alert("��ѡ�񱨰����ͣ�");
           return;	
        } 

      /*================================================================
         * �޸�ԭ�����ӶԱ����������ѱ������ݵ��ⰸ�жϲ�������
         * �� �� �ˣ�wood
         * �޸����ڣ�2007-6-15
         *=================================================================
         */
       //----------------jinsh--2007-06-12--�����ظ�����-------------------------//
      // var repsql="select a.subrptno from llsubreport a ,llreport b where a.subrptno=b.rptno and b.grpcontno='"+fm.GrpContNo.value+"' and a.customerno='"+fm.customerNo.value+"' and not exists ( select 'X' from llregister where rgtno = a.subrptno )  ";
       mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportInputSql");
		mySql.setSqlId("LLGrpReportSql27");
		mySql.addSubPara(fm.GrpContNo.value); 
		mySql.addSubPara(fm.customerNo.value); 
       var arr1= easyExecSql(mySql.getString());
       if(arr1 != null&&arr1!="")
       {  
             fm.addbutton.disabled = false;
             alert("�ó������� " + arr1 + "�ⰸ���б������ݣ��벻Ҫ�ظ�������");
             return false;
       }
       //----------------jinsh--2007-06-12--�����ظ�����-------------------------//         

       /*================================================================
         * �޸�ԭ�����ӶԱ���������δ�᰸���ⰸ�жϲ�������
         * �� �� �ˣ�L.Y
         * �޸����ڣ�2006-8-16
         *=================================================================
         */
     //add by wood ����b.clmstate<>'70'����Ҫ���ǲ������������llcase���������ݲ���rgtstate='20'
        //var StrSQL = "select a.caseno from llcase a, llregister b where a.rgtno=b.rgtno and b.grpcontno='"+fm.GrpContNo.value+"' and a.customerno = '"+fm.customerNo.value+"' and  b.clmstate not in ('60','80','50','70') and rownum<=1";
       mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportInputSql");
		mySql.setSqlId("LLGrpReportSql28");
		mySql.addSubPara(fm.GrpContNo.value); 
		mySql.addSubPara(fm.customerNo.value); 
        var arr= easyExecSql(mySql.getString());
        if(arr != null&&arr!=""){
         //  showInfo.close();
           fm.addbutton.disabled = false;
           alert("�ó������� " + arr + " �ⰸ����δ�᰸������᰸������������");
           return false;
        }
        
        /*var MstrSQL = " select a.polno from LCPol a, LCGrpCont b where  a.CValidate<='"+fm.AccidentDate2.value+"' and a.enddate>='"+fm.AccidentDate2.value+"'"
        +" and a.GrpContNo=b.GrpContNo and insuredno='"+fm.customerNo.value+"' and a.appflag in ('1','4')"*/
        //alert(strSQL);
        mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportInputSql");
		mySql.setSqlId("LLGrpReportSql29");
		mySql.addSubPara(fm.AccidentDate2.value); 
		mySql.addSubPara(fm.AccidentDate2.value); 
		mySql.addSubPara(fm.customerNo.value); 
        if (fm.GrpCustomerNo.value!=null&&fm.GrpCustomerNo.value!='000000')
        {
        	//MstrSQL=MstrSQL+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'"
        	mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportInputSql");
		mySql.setSqlId("LLGrpReportSql30");
		mySql.addSubPara(fm.AccidentDate2.value); 
		mySql.addSubPara(fm.AccidentDate2.value); 
		mySql.addSubPara(fm.customerNo.value); 
		mySql.addSubPara(fm.GrpCustomerNo.value); 
        }
        
        if (fm.GrpContNo.value!=null)
        {
        	//MstrSQL=MstrSQL+" and b.GrpContNo='"+fm.GrpContNo.value+"'"
        	mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportInputSql");
		mySql.setSqlId("LLGrpReportSql31");
		mySql.addSubPara(fm.AccidentDate2.value); 
		mySql.addSubPara(fm.AccidentDate2.value); 
		mySql.addSubPara(fm.customerNo.value); 
		mySql.addSubPara(fm.GrpCustomerNo.value); 
		mySql.addSubPara(fm.GrpContNo.value); 
        }

        var arr= easyExecSql(mySql.getString());
		if ( arr == null )
		{
			MstrSQL = " select a.polno from LCPol a, LCGrpCont b where  a.CValidate<='"+fm.AccidentDate2.value+"' and a.enddate>='"+fm.AccidentDate2.value+"'"
	        +" and a.GrpContNo=b.GrpContNo and a.insuredno='"+fm.customerNo.value+"' and a.appflag in ('1','4')"
	        +"union select a.polno from LCPol a, LCGrpCont b, lcinsuredrelated c where  a.CValidate<='"+fm.AccidentDate2.value+"' and a.enddate>='"+fm.AccidentDate2.value+"'"
	        +" and a.GrpContNo=b.GrpContNo  and a.polno=c.polno and c.customerno='"+fm.customerNo.value+"' and a.appflag in ('1','4')";
	        /*mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportInputSql");
		mySql.setSqlId("LLGrpReportSql32");
		mySql.addSubPara(fm.AccidentDate2.value); 
		mySql.addSubPara(fm.AccidentDate2.value); 
		mySql.addSubPara(fm.customerNo.value); 
		mySql.addSubPara(fm.AccidentDate2.value); 
		mySql.addSubPara(fm.AccidentDate2.value); 
		mySql.addSubPara(fm.customerNo.value); */
	        //alert(strSQL);
	        if (fm.GrpCustomerNo.value!=null&&fm.GrpCustomerNo.value!='000000')
	        {
	        	MstrSQL=MstrSQL+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'"
	        	/*mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql33");
				mySql.addSubPara(fm.AccidentDate2.value); 
				mySql.addSubPara(fm.AccidentDate2.value); 
				mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.AccidentDate2.value); 
				mySql.addSubPara(fm.AccidentDate2.value); 
				mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.GrpCustomerNo.value); */
	        }
	        
	        if (fm.GrpContNo.value!=null)
	        {
	        	MstrSQL=MstrSQL+" and b.GrpContNo='"+fm.GrpContNo.value+"'"
	        	/*mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql34");
				mySql.addSubPara(fm.AccidentDate2.value); 
				mySql.addSubPara(fm.AccidentDate2.value); 
				mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.AccidentDate2.value); 
				mySql.addSubPara(fm.AccidentDate2.value); 
				mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.GrpCustomerNo.value); 
				mySql.addSubPara(fm.GrpContNo.value); */
	        }
	        
	        //prompt("",MstrSQL);
	        arr= easyExecSql(MstrSQL);
	        
			if ( arr == null )
			{
				alert("�ÿͻ�����δ��Ч����γ������ڲ��ڸñ������˵ı����ڼ���,�������������ˣ�");             
				return false;
			}
		}
              
       
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
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql35");
				mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.GrpContNo.value); 
				
        if(fm.RiskCode.value != ''){
           // SqlPol += " and riskcode = '"+fm.RiskCode.value+"'";
             mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql36");
				mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.GrpContNo.value); 
				mySql.addSubPara(fm.RiskCode.value); 
             }
        var tPolState = easyExecSql(mySql.getString());
        if(tPolState > 0){
          // showInfo.close();
           fm.addbutton.disabled = false;
           alert("�ó������Ѿ����������������������������");
           return false;
         }       
         
         /*�Ա����źͿͻ��Ž���У��
         *2007-02-13 L��Y 
         */        
        var StrSQL = "select count(*) from lcgrpcont where appntno='"+fm.GrpCustomerNo.value+"' and grpcontno='"+fm.GrpContNo.value+"' ";
        /* mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql37");
				mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.GrpContNo.value); */
			
        var arr= easyExecSql(StrSQL);
        if(arr == 0){
        //   showInfo.close();
           fm.addbutton.disabled = false;
           alert("������ͻ��������屣���Ų�ƥ�䣬���޸ĺ��ٲ�����");
           return false;
        }
        /*===================================================================
         * �޸�ԭ�����ӶԳ������Ƿ���������������жϣ���������������������
         * �� �� �ˣ�JINSH
         * �޸����ڣ�2007-05-17
         *===================================================================
         */
        ///////////////////////////////////////////////////////////////////////////////
        //					��ϵͳ�л�ȥ�ж�polstate 6.5���ã���ע�͵�				 //
        //					ֱ��ȥ��ldperson�е����������Ƿ�Ϊ��						 //
        //        var Polsqlflag = " select count(*) From lcpol where polstate = '7'"//
        //                   + " and  insuredno = '"+fm.customerNo.value+"'"		 //
        //                   + " and  grpcontno = '"+fm.GrpContNo.value+"'";		 //
        //                   //alert(fm.customerNo.value);							 //
        ///////////////////////////////////////////////////////////////////////////////
//        var Polsqlflag = " select count(*) From ldperson where DeathDate is not null"
//        	+ " and  customerno = '"+fm.customerNo.value+"'";
//        	//+ " and  grpcontno = '"+fm.GrpContNo.value+"'";
//        //alert(fm.customerNo.value);
//        var Polflag = easyExecSql(Polsqlflag);
//        if(Polflag > 0)
//        {
//          // showInfo.close();
//           fm.addbutton.disabled = false;
//           alert("�ó�����"+fm.customerNo.value+"�Ѿ������������⣬��������������");
//           return false;
//        }

         /*================================================================
         * �޸�ԭ�����ڸ��պ���������ֱ���У�����ע�͵������У���߼�ʹ�����µ�У���߼�
         * �� �� �ˣ�WK
         * �޸����ڣ�2010-1-27
         *=================================================================
         */
		//var strSQL = "select deathdate from LDPerson where CustomerNo = '" + fm.customerNo.value + "'";
     mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql38");
				mySql.addSubPara(fm.customerNo.value); 
				
    var tDeathDate = easyExecSql(mySql.getString());
    var tAccDate = fm.AccidentDate2.value;
    if (tDeathDate != null && tDeathDate != "")
    {
        if (dateDiff(tAccDate,tDeathDate[0][0],'D') < 0)
        {
            alert("�ͻ�"+fm.customerName.value+"�ѱ�ȷ����"+tDeathDate+"��ʣ��������Ժ���ⰸ��������");
            return;
        }
        else
        {
            if (!confirm("�ͻ�"+fm.customerName.value+"�ѱ�ȷ����"+tDeathDate+"��ʣ��Ƿ�������У�"))
            {
                return;
            }
        }
    }
        
        /*================================================================
         * �޸�ԭ�����ӶԱ�ȫ���жϣ���ȫδȷ�Ϻ��˱��Ĳ���������
         * �� �� �ˣ�P.D
         * �޸����ڣ�2006-8-14
         *=================================================================
         */
        //var SqlBq = "select  count(*) from LPEdorItem where grpcontno = '"+fm.GrpContNo.value+"' and insuredno = '"+fm.customerNo.value+"' and edorstate != '0' and edortype = 'CT'";
        mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql39");
				mySql.addSubPara(fm.GrpContNo.value); 
				mySql.addSubPara(fm.customerNo.value); 
        var tEdorState = easyExecSql(mySql.getString());
        var sqlC = "select count(*) from lmriskapp where "
                 + "riskcode in (select riskcode From lcpol where "
                 + "grpcontno = '"+fm.GrpContNo.value+"' and insuredno = '"+fm.customerNo.value+"') "
                 + "and RiskPeriod = 'L'";
         /*mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql40");
				mySql.addSubPara(fm.GrpContNo.value); 
				mySql.addSubPara(fm.customerNo.value); */
        var tcount = easyExecSql(sqlC);
        if(tEdorState > 0 && tcount > 0 ){
          // showInfo.close();
           fm.addbutton.disabled = false;
           alert("�ó�������δȷ�ϵı�ȫ���Ѿ��˱�����ȷ�Ϻ�����������");
           return false;
        }

        //�ж����ֱ��汨�����Ǳ��������
        
        //jixf add 20051213
       /* var strSQL = " select a.polno from LCPol a, LCGrpCont b where  a.CValidate<='"+fm.AccidentDate.value+"' and a.enddate>='"+fm.AccidentDate.value+"'";
        strSQL=strSQL+" and a.GrpContNo=b.GrpContNo and insuredno='"+fm.customerNo.value+"' and a.appflag in ('1','4')";*/
       	mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql41");
				mySql.addSubPara(fm.AccidentDate.value); 
				mySql.addSubPara(fm.AccidentDate.value); 
				mySql.addSubPara(fm.customerNo.value); 
        if (fm.GrpCustomerNo.value!=null&&fm.GrpCustomerNo.value!='000000')
        {
         // strSQL=strSQL+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
          mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql42");
				mySql.addSubPara(fm.AccidentDate.value); 
				mySql.addSubPara(fm.AccidentDate.value); 
				mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.GrpCustomerNo.value); 
        }
        if (fm.GrpContNo.value!=null)
        {
          //strSQL=strSQL+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
           mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql43");
				mySql.addSubPara(fm.AccidentDate.value); 
				mySql.addSubPara(fm.AccidentDate.value); 
				mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.GrpCustomerNo.value); 
				mySql.addSubPara(fm.GrpContNo.value); 
        }        
        var arr= easyExecSql(mySql.getString());
        
    	  //var tsql="select distinct riskcode from lcgrppol where grpcontno='"+fm.GrpContNo.value+"' ";
       	mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql44");
				mySql.addSubPara(fm.GrpContNo.value); 
        if(fm.RiskCode.value != ''){
            //tsql += " and riskcode = '"+fm.RiskCode.value+"'";
            mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql45");
				mySql.addSubPara(fm.GrpContNo.value); 
				mySql.addSubPara(fm.RiskCode.value); 
             }        
        var tsub=new Array;
        tsub=easyExecSql(mySql.getString());
      
//       if (tsub != null)
//       {
//         for (var i=0; i < tsub.length; i++)
//         {
//          //alert("tsub[i]:"+tsub[i]);   
//          	var accSql="select risktype6 from lmriskapp where riskcode='"+fm.RiskCode.value+"' ";
//          	prompt("accSql:",accSql);
//	          var tRiskType6 = easyExecSql(accSql);	
//	                       
//          if (tRiskType6 != '8') //Ŀǰ��307���ŵ���ֻ��307һ������,risktype6='8'����307������
//          {
//            if(arr == null||arr=="")
//            {
//         //    showInfo.close();
//               fm.addbutton.disabled = false;
//               alert("�ñ���δ��Ч����γ������ڲ��ڸñ������˵ı����ڼ��ڣ�");         
//               return false;
//            }
//           }
//          }
//        }
        
        var strSQLBQ = " select a.polno from LPEdorItem a, LCGrpCont b where  a.EdorValidate>='"+fm.AccidentDate2.value+"'";
        strSQLBQ=strSQLBQ+" and a.GrpContNo=b.GrpContNo and a.insuredno='"+fm.customerNo.value+"'";
        strSQLBQ=strSQLBQ+" and a.EdorType in ('AA','PT','IC','LC')��and EdorState='0'";
        /*mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql46");
				mySql.addSubPara(fm.AccidentDate2.value); 
				mySql.addSubPara(fm.customerNo.value); */
        if (fm.GrpCustomerNo.value!=null)
        {
           strSQLBQ=strSQLBQ+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
           /* mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql47");
				mySql.addSubPara(fm.AccidentDate2.value); 
				mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.GrpCustomerNo.value); */
        }
        if (fm.GrpContNo.value!=null)
        {
            strSQLBQ=strSQLBQ+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
            /*mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql48");
				mySql.addSubPara(fm.AccidentDate2.value); 
				mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.GrpCustomerNo.value); 
				mySql.addSubPara(fm.GrpContNo.value); */
        }
        
       var arrbq= easyExecSql(mySql.getString());
       if ( arrbq != null )
       {
         //  showInfo.close();
           fm.addbutton.disabled = false;
          alert("���ؾ��棺�ñ��������ڳ�������֮���������ܸ��ı���ı�ȫ��");             
       }
               
       
         //-----------------jinsh--20070517------------------������˻�--��ѯlcinsureaccclass��--//
        if (fm.rgtstate[2].checked == true )
        {
        		//alert("OK");
          	//var QueryStrSql="select count(*) from lcinsureaccclass where grpcontno='"+fm.GrpContNo.value+"' and insuredno='"+fm.customerNo.value+"'";
         		 mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql49");
				mySql.addSubPara(fm.GrpContNo.value); 
				mySql.addSubPara(fm.customerNo.value); 
         		var QueryNum=easyExecSql(mySql.getString());
         		if(QueryNum=='0'||QueryNum==null)
         		{
           //     showInfo.close();
           			fm.addbutton.disabled = false;
         				alert("�ó�����"+fm.customerNo.value+"û���˻���Ϣ!��������������");
         				return false;
         		}
			       //var StringSQL="select * from lcinsureaccclass where grpcontno='"+fm.GrpContNo.value+"' and riskcode='"+fm.RiskCode.value+"'";
			        mySql = new SqlClass();
					mySql.setResourceName("claimgrp.LLGrpReportInputSql");
					mySql.setSqlId("LLGrpReportSql50");
					mySql.addSubPara(fm.GrpContNo.value); 
					mySql.addSubPara(fm.RiskCode.value); 
			       var ForbidResult=easyExecSql(mySql.getString());
			       //alert(ForbidResult);
			       if(ForbidResult==null)
			       {			
            //      showInfo.close();
           			  fm.addbutton.disabled = false;
			       			alert("������"+fm.RiskCode.value+"���˻����в����ڣ��������ʻ����� ��");
			       			return false;
			       }
            // var StrAccSQL = "select rgtno from llregister  where grpcontno = '"+fm.GrpContNo.value+"' and clmstate not in ('60', '80', '50', '70') and rownum<=1 "; //�����ֻ����û���������ƣ�����ӵڶ�����ʱ�ͻ���Ӳ���
             mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql51");
			mySql.addSubPara(fm.GrpContNo.value); 
             var arr2= easyExecSql(mySql.getString());
             if(arr2 > 0)
             {
            //    	showInfo.close();
           				fm.addbutton.disabled = false;
                 alert("�ñ�����δ�᰸��" + arr2 + "����᰸������������");
                 return false;
             }
             if (document.all('AccFlag').value=='10')
              {
               // var asql="select polstate from lcpol where grpcontno='"+fm.GrpContNo.value+"' and riskcode='"+fm.RiskCode.value+"' and poltypeflag='2' ";
                mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql52");
			mySql.addSubPara(fm.GrpContNo.value); 
			mySql.addSubPara(fm.RiskCode.value); 
                var tpolstate=easyExecSql(mySql.getString());
                 if (tpolstate=='4')
                 {                   
            //    	showInfo.close();
           				fm.addbutton.disabled = false;                   
                   alert("�������˻����˱�����ʹ�ø����ʻ����⣡");	
                   return false;
                 }
              } //�жϹ����ʻ��Ƿ��˱���             			       
			                		
        }           
        //-----------------jinsh--20070517------------------������˻�--��ѯlcinsureaccclass��--//
         
        if (fm.RptNo.value != "")
        {
          fm.fmtransact.value = "insert||customer";
        }
        else
        {
            fm.fmtransact.value = "insert||first";
        }
        
        for (var i = 0; i < fm.rgtstate.length; i++)
       {
          if (fm.rgtstate[i].checked==true)
          {         	
          	var trgtstate=new Array();
            trgtstate=fm.rgtstate[i].value;
            //alert("trgtstate:"+trgtstate);
          } 
       }

        //�ύ
        fm.addbutton.disabled = true;
    	fm.action = "LLGrpReportSave.jsp?rgtstate="+trgtstate;
        submitForm();
    }
}

//����ȷ��
function reportConfirm()
{
    //---------------------------------------------------------------------beg 2005-8-4 16:32
    //���������స�������������ѹ���
    //---------------------------------------------------------------------
/*
    var tReasonCode = new Array;
    var strSQL = "select substr(ReasonCode,2,2) from LLReportReason where "
                + "RpNo = '"+fm.RptNo.value+"'";
    tReasonCode = easyExecSql(strSQL);
    var tYesOrNo = 0;
    for (var i = 0;i < tReasonCode.length; i++)
    {
      if (tReasonCode[i] == '02')
      {
            tYesOrNo = 1;
      }
    }
    if (tYesOrNo == 1)
    {
        tYesOrNo = 0;
        
        //����Ƿ��Ѿ��ֹ�����
        var strSQL = "";
        var arrResult = new Array;
      strSQL = "select nvl((select b.PosFlag from LCContHangUpState b where b.ContNo = a.ContNo),0),nvl((select b.RNFlag from LCContHangUpState b where b.ContNo = a.ContNo),0)"
           + " from LcCont a where 1=1 "
           + " and a.insuredno in (select c.customerno from llsubreport c where c.subrptno = '"+ fm.RptNo.value +"')"
           + " or a.AppntNo in (select c.customerno from llsubreport c where c.subrptno = '"+ fm.RptNo.value +"')";  //Ͷ����     
      arrResult = easyExecSql(strSQL);
      
      for (var j=0; j<arrResult.length; j++)
      {
          for (var k=0; k<arrResult[j].length; k++)
          {
              if (arrResult[j][k] == "0")
              {
                  tYesOrNo++;
              }
          }
      }
//      alert(tYesOrNo);
      if (tYesOrNo > 0)
      {
          if (confirm("��������ѡ������,�Ƿ���б����������?"))
            {
                return;
            }
        }
    }
*/
    //---------------------------------------------------------------------end
    //���ǿ�
    
    
    
    if (fm.RptNo.value == "")
    {
         alert("������Ϊ�գ�");
         return;
    }
    var sql2 = "select count(1) from LLStandPayInfo where"
                 + " CaseNo = '" + fm.RptNo.value + "'";
    /*mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql53");
			mySql.addSubPara(fm.RptNo.value);           */
    var tDutyKind = easyExecSql(sql2);
        if (tDutyKind == 0)
        {
            alert("���Ƚ���Ԥ�����¼��!");
            return;
        }
    for (var i = 0; i < fm.rgtstate.length; i++)
       {
          if (fm.rgtstate[i].checked==true)
          {
          	var trgtstate=new Array();
            trgtstate=fm.rgtstate[i].value;
            //alert("trgtstate:"+trgtstate);
          } 
       }
        
    fm.action = './LLReportConfirmSave.jsp?rgtstate='+trgtstate;
    submitForm();
}


//�����������Ϣ
function getin() {
   	//fm.action = "./GrpCustomerDiskReportSave.jsp?";
   	//document.getElementById("fm").submit();
	//alert(1665);
   	var rgtstate="";
	var i = 0;
	
    getImportPath();
    fmload.all('ImportPath').value = ImportPath;
    
    if (fmload.all('FileName').value==null||fmload.all('FileName').value=="")
    {
       alert ("�������ļ���ַ!");	
       return false;
    }
    

	if(document.all('RptorName').value==null||document.all('RptorName').value=="")
	{
		alert ("�����뱨����������");	
       	return false;
	}
	
	if(document.all('GrpCustomerNo').value==null||document.all('GrpCustomerNo').value=="")
	{
		alert ("����������ͻ��ţ�");	
       	return false;
	}
	
	if(document.all('GrpContNo').value==null||document.all('GrpContNo').value=="")
	{
		alert ("���������屣���ţ�");	
       	return false;
	}
	
	if(document.all('GrpName').value==null||document.all('GrpName').value=="")
	{
		alert ("�����뵥λ���ƣ�");	
       	return false;
	}
	
	if(document.all('occurReason').value==null||document.all('occurReason').value=="")
	{
		alert ("���������ԭ��");	
       	return false;
	}
	
	
    var AccDate = fm.AccidentDate.value;//�¹�����
    //ȡ����������Ϣ
    var AccYear = AccDate.substring(0,4);
    var AccMonth = AccDate.substring(5,7);
    var AccDay = AccDate.substring(8,10);

    //����¹�����
    if (AccDate == null || AccDate == '')
    {
        alert("�¹����ڲ���Ϊ�գ�");
        return false;
    }
    else
    {
        if (AccYear > mNowYear)
        {
            alert("�¹����ڲ������ڵ�ǰ����");
            return false;
        }
        else if (AccYear == mNowYear)
        {
            if (AccMonth > mNowMonth)
            {

                alert("�¹����ڲ������ڵ�ǰ����");
                return false;
            }
            else if (AccMonth == mNowMonth && AccDay > mNowDay)
            {
                alert("�¹����ڲ������ڵ�ǰ����!");
                return false;
            }
        }
    }
		
	
	if (fm.rgtstate[0].checked == false && fm.rgtstate[1].checked == false && fm.rgtstate[2].checked == false )
    {
          alert("��ѡ�񱨰����ͣ�");
          return false;	
    } 
	
    if(fm.rgtstate[1].checked == true)
    {
    	  rgtstate="03";
    }
    
    if (fm.rgtstate[2].checked == true )
    {
    	  rgtstate="02";
          if (fm.AccFlag.value == null || fm.AccFlag.value == '')
    	  {
        		alert("�Ƿ�ʹ�������ʻ�����Ϊ�գ�");
        		return false;
    	  }
          
          if(document.all('Polno').value=="")
		  {
				alert ("���������ֱ��룡");	
       		    return false;
		  }
          
    	//  var StringSQL="select * from lcinsureaccclass where grpcontno='"+fm.GrpContNo.value+"' and riskcode='"+fm.RiskCode.value+"'";
		  mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql54");
			mySql.addSubPara(fm.GrpContNo.value);  
			mySql.addSubPara(fm.RiskCode.value);  
		  var ForbidResult=easyExecSql(mySql.getString());
			    //alert(ForbidResult);
		  if(ForbidResult==null)
		  {			
			  alert("������"+fm.RiskCode.value+"���˻����в����ڣ��������ʻ����� ��");
	          return false;
		  }	     			    
    }

    
    var StrSQL = "select count(*) from lcgrpcont where appntno='"+fm.GrpCustomerNo.value+"' and grpcontno='"+fm.GrpContNo.value+"' ";
   /* mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql55");
			mySql.addSubPara(fm.GrpCustomerNo.value);  
			mySql.addSubPara(fm.GrpContNo.value);  */
    //prompt("У������ͻ��źͱ������Ƿ�ƥ��sql",StrSQL);
    var arr= easyExecSql(StrSQL);
    if(arr == 0)
    {
          alert("������ͻ��������屣���Ų�ƥ�䣬���޸ĺ��ٲ�����");
          return false;
    }

    
    tSaveFlag ="0";  
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

    fmload.action = "./GrpCustomerDiskReportSave.jsp?RptorName="+document.all('RptorName').value+"&GrpCustomerNo="+document.all('GrpCustomerNo').value+"&GrpContNo="+document.all('GrpContNo').value+"&GrpName="+document.all('GrpName').value+"&rgtstate="+rgtstate+"&AccFlag="+document.all('AccFlag').value+"&RiskCode="+document.all('RiskCode').value+"&RptorPhone="+document.all('RptorPhone').value+"&RptorAddress="+document.all('RptorAddress').value+"&RptNo="+document.all('RptNo').value+"&ImportPath="+fmload.all('ImportPath').value+"&RptMode="+document.all('RptMode').value+"&AccidentDate="+document.all('AccidentDate').value+"&AccidentReason="+document.all('occurReason').value+"&Relation="+document.all('Relation').value;
    //alert(fmload.action);
    fmload.submit(); //�ύ
 
   	//fm.action = "./GrpCustomerDiskReportSave.jsp?";
   	//document.getElementById("fm").submit();
}

function getImportPath ()
{
	  //var strSQL = "";
	 // strSQL = "select SysvarValue from ldsysvar where sysvar ='XmlPath'";
	  mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql56");
			mySql.addSubPara('');
	  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 1, 1);
	  //alert(mySql.getString()) modified by lilinsen 2011-10-8 11:44:38
	  if (!turnPage.strQueryResult) 
	  {
		    alert("δ�ҵ��ϴ�·��");
		    return;
	  }
	  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	  ImportPath = turnPage.arrDataCacheSet[0][0];
}


//�ύ����
function submitForm()
{
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

    //    showSubmitFrame(mDebug);
    fm.target="fraSubmit";
    //fm.addbutton.disabled = false;
    document.getElementById("fm").submit(); //�ύ
    tSaveFlag ="0";
}

//ѡ��SubReportGrid��Ӧ�¼�,tPhase=0Ϊ��ʼ��ʱ����
function SubReportGridClick(tPhase,rptNo)
{
  //------------------------------------------**Beg
  //�ÿ���ر�
  //------------------------------------------**
 
  	rptNo=fm.ClmNo.value;
  	fm.customerName.value = "";
	fm.customerAge.value = "";
	fm.customerSex.value = "";
	fm.SexName.value = "";
	//fm.IsVip.value = "";
	//fm.VIPValueName.value = "";
	fm.hospital.value = "";
	fm.TreatAreaName.value = "";
	//fm.OtherAccidentDate.value = "";
	//fm.MedicalAccidentDate.value = "";
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
    //------------------------------------------**End
    
  //--------------------------------------------Beg
  //��ʾ��������
  //--------------------------------------------
  spanText1.style.display = "";
  spanText2.style.display = "none";
  spanText3.style.display = "";
  //spanText4.style.display = "";
  //--------------------------------------------End
  
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
        fm.customerAge.value = calAge(SubReportGrid.getRowColData(i,5));
        fm.IDNo.value = SubReportGrid.getRowColData(i,9);
        fm.IDType.value = SubReportGrid.getRowColData(i,8);
        //fm.IsVip.value = SubReportGrid.getRowColData(i,5);
        showOneCodeName('sex','customerSex','SexName');//�Ա�
//��ʾ������Ϣ
       if(fm.customerNo.value!=''){
         var strSQL = "";
       }
    }

    //��ѯ�����������
    var tClaimType = new Array;
    /*var strSQL1 = "select ReasonCode from LLReportReason where "
                + "RpNo = '"+rptNo+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";//prompt("",strSQL1);*/
    mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql57");
			mySql.addSubPara(rptNo);  
			mySql.addSubPara(fm.customerNo.value); 
    tClaimType = easyExecSql(mySql.getString());
    if (tClaimType == null)
    {
        alert("��������Ϊ�գ�����˼�¼����Ч�ԣ�");
        return;
    }
    else
    {
      fm.occurReason.value=	tClaimType[0][0].substring(0,1);
      showCodeName('occurReason','occurReason','ReasonName');

        for(var j=0;j<fm.claimType.length;j++)
        {
            for (var l=0;l<tClaimType.length;l++)
            {
                var tClaim = tClaimType[l].toString();
                tClaim = tClaim.substring(tClaim.length-2,tClaim.length);//ȡ�������ͺ���λ
                
                if(fm.claimType[j].value == tClaim)
                {
                    fm.claimType[j].checked = true;
                    
                    //���Ϊ�˲У���ʾ�˲м���֪ͨ2005-8-13 11:04
                    if (tClaim == '01')
                    {
                    		
                        fm.MedCertForPrt.disabled = false;
                    }
                }
            }
        }
    }
    //��ѯ�ְ�����Ϣ
    var tSubReport = new Array;
   /* var strSQL2 = "select c.HospitalCode,c.AccDate,c.AccidentDetail,c.DieFlag,c.CureDesc,c.Remark,c.AccDesc,c.AccResult1,c.AccResult2,c.HospitalName "
    	         +" ,(select codename from ldcode a where a.codetype='accidentcode' and a.code=c.AccidentDetail),f.AccidentDate"
    	         +" from LLSubReport c,LLReport f where 1=1 "
    	         +" and c.subrptno=f.rptno"
                + getWherePart( 'SubRptNo','RptNo' )
                + getWherePart( 'CustomerNo','customerNo' );*/
     mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql58");
			mySql.addSubPara(fm.RptNo.value);  
			mySql.addSubPara(fm.customerNo.value); 
//    prompt("��ѯ�ְ���sql",strSQL2);
    tSubReport = easyExecSql(mySql.getString());
    fm.hospital.value = tSubReport[0][0];
    fm.AccidentDate2.value = tSubReport[0][1];
    fm.AccidentDate.value = tSubReport[0][11];
    fm.accidentDetail.value = tSubReport[0][2];
//    fm.IsDead.value = tSubReport[0][3];
    fm.cureDesc.value = tSubReport[0][4];
    fm.Remark.value = tSubReport[0][5];
    fm.RemarkDisabled.value = tSubReport[0][5];
    fm.AccDesc.value = tSubReport[0][6];
    fm.AccDescDisabled.value = tSubReport[0][6];
    fm.AccResult1.value = tSubReport[0][7];
    fm.AccResult2.value = tSubReport[0][8];
    fm.TreatAreaName.value = tSubReport[0][9];
    fm.accidentDetailName.value = tSubReport[0][10];

    
    showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ
    showOneCodeName('llaccidentdetail','accidentDetail','accidentDetailName');//����ϸ��
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//������ʶ
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//�������
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//���ս��1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//���ս��2
    queryResult2();
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

function operStandPayInfo()
{
   var tSel = SubReportGrid.getSelNo();
/*
   if( tSel == 0 || tSel == null ){
        alert( "���ڳ�������Ϣ��ѡ��һ����¼" );
        return false;
     }
else {
     customerNo = SubReportGrid.getRowColData(tSel-1 ,1);
    }
*/
     var varSrc ="";
     var CaseNo = fm.ClmNo.value;//alert(CaseNo);
     var customerName = fm.customerName.value;
     var customerNo=fm.customerNo.value;
     var GrpContNo=fm.GrpContNo.value;
     var RiskCode=fm.RiskCode.value;
     pathStr="./StandPayInfoMain.jsp?CaseNo="+CaseNo+"&customerName="+customerName+"&customerNo="+customerNo
     		+"&GrpContNo="+GrpContNo+"&RiskCode="+RiskCode;
     showInfo = OpenWindowNew(pathStr,"","middle");
}

function callrgtstate(ttype)
{
  	switch (ttype)
    {
       case "03" : 
          //alert("ttype:"+ttype);
          
           	divreport1.style.display="none";  
           	divreport2.style.display="none"; 
           	divreport3.style.display="none";   
           	divreport4.style.display="";
           	divreport5.style.display="none";  
           	initDiskErrQueryGrid();    
           	divDiskErr.style.display="";       
           	operateButton2.style.display="none";
           	/*document.all('Relation').value=" ";
           	document.all('hospital').value=" "; 
           	document.all('TreatAreaName').value=" ";
           	document.all('accidentDetail').value=" ";
           	document.all('accidentDetailName').value=" ";*/
           break;
       case "02" :	
           //alert("ttype:"+ttype);

           	divreport1.style.display="none";
           	divreport2.style.display="none";
           	divreport3.style.display="none";
           	divreport4.style.display="";
           	divreport5.style.display="";
           	/*document.all('Relation').value=" ";
           	document.all('hospital').value=" ";
           	document.all('TreatAreaName').value=" ";
            document.all('accidentDetail').value=" ";
            document.all('accidentDetailName').value=" ";*/
           break;
       case "11":   
           //alert("ttype:"+ttype); 

           	divreport1.style.display="";
           	divreport2.style.display="";
          	divreport3.style.display="";
          	operateButton2.style.display="";
          	divreport4.style.display="none";
          	divreport5.style.display="none";
           break;
       default ://divreport.style.display="";
            return;
    }    

}

//---------------------------------------------------**
//���ܣ����������߼��ж�
//����1 �������߲С�ҽ������ֻ��ѡһ
//      2 ѡ��������߲�ʱ��Ĭ��ѡ�л���
//      3 ѡ�л���,����ѡ��������߲�֮һ(���ڱ���ʱ�ж�)
//---------------------------------------------------**
function callClaimType(ttype)
{
    switch (ttype)
    {
        case "02" : //����
            if (fm.claimType[0].checked == true)
            {
                //fm.claimType[4].checked = true;
            }
//            if ((fm.claimType[1].checked == true || fm.claimType[5].checked == true) && fm.claimType[0].checked == true)
//            {
//                alert("�������߲С�ҽ������ֻ��ѡһ!");
//                fm.claimType[0].checked = false;
//                return;
//            }
//            else
//            {
//                if (fm.claimType[0].checked == true)
//                {
//                    fm.claimType[4].checked = true;
//                }
//            }
        case "03" :
            if (fm.claimType[1].checked == true)
            {
                //fm.claimType[4].checked = true;
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
            return;
    }
}

/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ������Ϊȡ�������ͺ���������
    �� �� �ˣ���־��
    �޸����ڣ�2005.05.24
   =========================================================================
**/

function getClaimTypes(){
    var f=fm.elements;
    var types="";
    for (var i=0;i<f.length;i++){
      if ((f[i].type=="checkbox")&&(f[i].checked)){
          if (types=="")
            types=types+f[i].value;
          else
            types=types+","+f[i].value;
        }
    }
    return types;
}

//�õ�0��icd10��
function saveIcdValue()
{
    fm.ICDCode.value = fm.AccResult1.value;
}

//��ѯ���ս��2
function queryResult2()
{
   /* var strSql = " select ICDName from LDDisease where "
               + " ICDCode = '" + fm.AccResult2.value + "'"
               + " order by ICDCode";*/
   mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql59");
			mySql.addSubPara(fm.AccResult2.value);  
    var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        fm.AccResult2Name.value = tICDName;
    }
}

/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ�򣺴�ӡ��֤
    �� �� �ˣ�����
    �޸����ڣ�2005.07.28
    �޸ģ�2005.08.22����ӡ��֤ʱ��ǰ̨�жϡ��Ƿ���ڻ�����Ҫ���򡷣���ѯ��ˮ�ţ������̨��
   =========================================================================
**/
function showPrtAffix()
{
	var row = SubReportGrid.getSelNo();	
    if(row < 1) {
        alert("��ѡ��һ���ͻ���");
        return;
    }
    var CustomerNo = SubReportGrid.getRowColData(row-1 ,1);
    fm.customerNo.value = CustomerNo;
    fm.action = '../claim/LLPRTCertificatePutOutSave.jsp';  
    var tAffixSql = "select * from LLReportAffix where rptno='"+fm.RptNo.value
    			+"' and customerno='"+CustomerNo+"'";
    var arrAffix = easyExecSql(tAffixSql);
    if(!arrAffix){
    	alert("û����Ҫ��ӡ���������ϣ�");
    	return false;
    }
    
    if(beforePrtCheck(fm.ClmNo.value, CustomerNo, "PCT002")==false)
    {
      return;
    } 
  //  fm.target = "../f1print";
  //  document.getElementById("fm").submit();
}


function showPrtAffixNew()
{   fm.action = './LLPRTCertificatePutOutSaveNew.jsp';   //
    fm.target = "../f1print";
    document.getElementById("fm").submit();
  }

//��ӡ�˲м���֪ͨ��
function PrintMedCert()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return;
    }
    var CustomerNo = SubReportGrid.getRowColData(row-1 ,1);
    fm.customerNo.value = CustomerNo;
    fm.action = './LLPRTAppraisalSave.jsp';   
    if(beforePrtCheck(fm.ClmNo.value, CustomerNo, "PCT001")==false)
    {
      return;
    } 
//    fm.target = "../f1print";
//    document.getElementById("fm").submit();

}

//Ӱ���ѯ---------------2005-08-14���
function colQueryImage()
{
    //var row = SubReportGrid.getSelNo();
    //if(row < 1)
    //{
        //alert("��ѡ�пͻ���");
        //return;
    //}
    //var tClaimNo = fm.RptNo.value;         //�ⰸ��  
    //var tCustNo = fm.customerNo.value;     //�ͻ���
    
  //if (tCustNo == "")
  //{
      //alert("��ѡ��ͻ���");
      //return;
  //}
  
  var strUrl="LLColQueryImageMain.jsp?claimNo="+document.all('ClmNo').value+"&subtype=LP1001";
  window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//��ӡ�ⰸ��������
function colBarCodePrint()
{
	if(fm.RptNo.value==""||fm.RptNo.value==null){
		alert("���ȱ��汨����Ϣ��");
		return false;
	}
  fm.action="LLClaimBarCodePrintSave.jsp";
    fm.target = "../f1print";
    document.getElementById("fm").submit();
}



/**********************************
//��ӡǰ���飨������Ҫ�����������֤���롢�ⰸ�ţ�--------2005-08-22���
***********************************/
function beforePrtCheck(clmno,CustomerNo,code)
{
  //��ѯ��֤��ˮ�ţ���Ӧ�������루�ⰸ�ţ����������͡���ӡ��ʽ����ӡ״̬�������־
     var tclmno=trim(clmno);
     var tCustomerNo =trim(CustomerNo);
     var tcode =trim(code);
     
     if(tclmno=="" ||tcode=="")
     {
       alert("������ⰸ�Ż�֤���ͣ����룩Ϊ��");
       return false;
     }
    var strSql="select t.prtseq,t.otherno,t.code,t.prttype,t.stateflag,t.patchflag from loprtmanager t where 1=1 "
      +" and t.otherno='" + tclmno + "'"
      +" and trim(t.code)='" + tcode + "'"
      +" and t.standbyflag4='" + tCustomerNo + "'";
  /* mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql60");
			mySql.addSubPara(tclmno); 
			mySql.addSubPara(tcode); 
			mySql.addSubPara(tCustomerNo); */
  var arrLoprt = easyExecSql(strSql);
  if(arrLoprt==null)
  {
    alert("û���ҵ��õ�֤����ˮ��");
    return false;
  }  

  var tprtseq=arrLoprt[0][0];//��֤��ˮ��
  var totherno=arrLoprt[0][1];//��Ӧ�������루�ⰸ�ţ�
  var tcode=arrLoprt[0][2];//��������
  var tprttype=arrLoprt[0][3];//��ӡ��ʽ
  var tstateflag=arrLoprt[0][4];//��ӡ״̬
  var tpatchflag=arrLoprt[0][5];//�����־
  fm.PrtSeq.value=arrLoprt[0][0];
  //���ڵ�δ��ӡ����ֱ�ӽ����ӡҳ��
   if(tstateflag=="0")
   {
//      fm.action = './LLPRTCertificatePutOutSave.jsp';   //
    fm.target = "../f1print";
    document.getElementById("fm").submit();
   }
  else
  {
    //���ڵ��Ѿ���ӡ����tstateflag[��ӡ״̬��1 -- ��ɡ�2 -- ��ӡ�ĵ����Ѿ��ظ���3 -- ��ʾ�Ѿ����߰�֪ͨ��]
    if(confirm("�õ�֤�Ѿ���ӡ��ɣ���ȷʵҪ������"))
     {
       //���벹��ԭ��¼��ҳ��
       var strUrl="../claim/LLPrtagainMain.jsp?PrtSeq="+fm.PrtSeq.value+"&CustomerNo="+fm.customerNo.value+"&RgtClass=2";
       window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
     }
  }
  
}

function easyQueryClick()
{
	  //alert(2327);
	  
	  //alert("fm.ClmNo.value:"+fm.ClmNo.value);
	  //alert("fmload.tRgtNo.value:"+fmload.tRgtNo.value);
	  
	  if(fm.RptNo.value==null||fm.RptNo.value=="")
	  {
		  fmload.tRgtNo.value="00000000000000000000";
	  }
	  else
	  {
		  fmload.tRgtNo.value=fm.ClmNo.value;
	  }
	  
	  //alert("fmload.tRgtNo.value2:"+fmload.tRgtNo.value);

	  if(fmload.tBatchNo.value==null||fmload.tBatchNo.value=="")
	  {
		  	alert("��¼�뵼���ļ�����");
		  	return;
	  }
	  
	  if (fmload.tRgtNo.value==""&&fmload.tCustomerNo.value==""&&fmload.tCustomerName.value==""&&fmload.tBatchNo.value=="")
	  {
	  	alert("����������һ����ѯ������");
	  	return;
	  }
	  
	  initDiskErrQueryGrid();
	  // ��дSQL���
	  //var strSql = "select rgtno,customerno,customername,ErrorInfo,BatchNo,ID,operator,makedate,maketime from lcGrpCustomerImportLog where 1=1 ";
	   // + "and ErrorState='0' "; �ݲ����������
	   mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql61");
	  if(fmload.all('tRgtNo').value!=null&&fmload.all('tRgtNo').value!="")
	  {
	   // strSql=strSql+ "and RgtNo='"+fmload.all('tRgtNo').value+"'";
	     mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql62");
			mySql.addSubPara(fmload.all('tRgtNo').value); 
			
	  }
	  if(fmload.all('tCustomerNo').value!=null&&fmload.all('tCustomerNo').value!="")
	  {
	    //strSql=strSql+ "and CustomerNo='"+fmload.all('tCustomerNo').value+"'";
	    mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql63");
			mySql.addSubPara(fmload.all('tRgtNo').value); 
			mySql.addSubPara(fmload.all('tCustomerNo').value); 
	  }
	  if(fmload.all('tCustomerName').value!=null&&fmload.all('tCustomerName').value!="")
	  {
	    //strSql=strSql+ "and customername='"+fmload.all('tCustomerName').value+"'";
	    mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql64");
			mySql.addSubPara(fmload.all('tRgtNo').value); 
			mySql.addSubPara(fmload.all('tCustomerNo').value); 
			mySql.addSubPara(fmload.all('tCustomerName').value); 
	  }
	  if(fmload.all('tBatchNo').value!=null&&fmload.all('tBatchNo').value!="")
	  {
	    //strSql=strSql+ "and BatchNo like '"+fmload.all('tBatchNo').value+"%%'";
	    mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql65");
			mySql.addSubPara(fmload.all('tRgtNo').value); 
			mySql.addSubPara(fmload.all('tCustomerNo').value); 
			mySql.addSubPara(fmload.all('tCustomerName').value); 
			mySql.addSubPara(fmload.all('tBatchNo').value); 
	  }  
	    //strSql=strSql+ "and operator='"+document.all('Operator').value+"'";
	 // strSql=strSql+ " Order by makedate desc";	
	  
	//  prompt("��ѯ�������������Ϣsql",strSql);
	  turnPage2.queryModal(mySql.getString(),DiskErrQueryGrid,1,1);
	  
	  if (!turnPage.strQueryResult) {
	    //alert("δ��ѯ���������������ݣ�");
		fmload.tRgtNo.value="";
	    return false;
	  }  
	  
}



function afterLCGrpQuery(arrReturn)
{
     document.all('GrpCustomerNo').value=arrReturn[0][0];
     document.all('GrpName').value=arrReturn[0][1];
     document.all('GrpContNo').value=arrReturn[0][2];
     document.all('Peoples').value=arrReturn[0][3];
}

//�������ⰸ�����г����˼����⸶��Ϣ
function getLastCaseInfo(){
var CaseNo = fm.RptNo.value;
var GrpContNo = fm.GrpContNo.value
if(CaseNo == "" || CaseNo == null){
	alert("���ȵ㱣�棡");
	return false;
}else{
//alert("bb");
	var row = SubReportGrid.getSelNo() - 1;
	if (row <0)
	{
	      alert("����ѡ��һ���ͻ���Ϣ��");
	      return;
	}
  var tCustomerNo = SubReportGrid.getRowColData(row,1);
 //+" and a.grpcontno = '" + GrpContNo + "'";
  var tSQL = "select a.clmno,a.grpcontno,a.insuredno,a.insuredname,a.getdutykind,"
		 + " b.accdate,b.endcasedate,a.riskcode,a.standpay," 
		 + "a.realpay from llclaimpolicy a,llcase b "
		 +"where  a.clmno=b.caseno and a.insuredno=b.customerno "
		 //+" and b.rgtstate='60'"
		 +" and "
		 +" a.insuredno = '"+ tCustomerNo +"'"
	     +" and a.clmno <>'"+CaseNo+"'";
	mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql66");
			mySql.addSubPara(tCustomerNo); 
			mySql.addSubPara(CaseNo);       
  var arr = easyExecSql(mySql.getString());
  if(!arr){
	  alert("δ��ѯ�������ⰸ��Ϣ���޷�����");
	  return false;
  }
fm.tSQL.value = tSQL;
//prompt("",tSQL);
  var Title="�����ⰸ��Ϣ��ѯ";
	var SheetName="�����ⰸ��Ϣ��ѯ";       
	var ColName = "�ⰸ��@���屣����@�ͻ���@�ͻ�����@��������@��������@�᰸����@���ִ���@Ӧ�⸶���@ʵ���⸶���";
	fm.action = "./PubCreateExcelSave.jsp?tSQl="+tSQL+"&Title=�����ⰸ��Ϣ��ѯ"+"&SheetName="+Title+"&ColName="+ColName;
	fm.target="../claimgrp";		 	
	document.getElementById("fm").submit();
}   
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



/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ����������˺ͱ�������ͬһ���˵Ļ�����ô�����˵���Ϣ�ӳ����˵�
              ��Ϣ�еõ�
    �� �� �ˣ������
    �޸����ڣ�2005.11.15
   =========================================================================
**/
function getRptorInfo()
{
   var tCustomerNo = fm.customerNo.value ;
   var tRelation = fm.Relation.value;

   var tRelationName = fm.RelationName.value;
   if(tRelation == "00"|| tRelationName == "����")
   {
      if( tCustomerNo != null  && tCustomerNo != "")
      {
          //var strSQL = "select postaladdress,phone from lcaddress where customerno ='"+tCustomerNo+"'";
          mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql67");
			mySql.addSubPara(tCustomerNo); 
          var strQueryResult = easyExecSql(mySql.getString());
          if(strQueryResult==null || strQueryResult=="")
          {
          	return;
          }
          else
          {
             fm.RptorAddress.value = strQueryResult[0][0];
             fm.RptorPhone.value = strQueryResult[0][1];
             fm.RptorName.value = fm.customerName.value;
          }
      }
      else
      {
          return;
      }
   }

   

   //�޸�ԭ������������˺ͳ����˹�ϵ��Ϊ��GX02-����������Ա��ʱ����ô�����˵���Ϣȡ����������Ա����Ϣ
   //�� �� �ˣ�zhangzheng 
   //�޸�ʱ�䣺2008-12-16
   //MSû�б���������Ա,ȥ������߼�
   
   //else if(tRelation == "GX02" || tRelationName == "����������Ա")
   //{
   	 //if( tCustomerNo != null  && tCustomerNo != "")
   	 //{
   	 	   //var strSQL =" select b.name, b.homeaddress, b.phone from laagent b " 
           //         +" where 1=1 "
           //         +" and b.agentcode in (select distinct trim(a.agentcode) from lccont a where a.insuredno = '"+tCustomerNo+"') ";
         //var arr = easyExecSql(strSQL);
         // if(arr==null || arr=="")
         // {
         // 	return;
         // }
         // else
         // {
         //    fm.RptorName.value = arr[0][0];
         //    fm.RptorAddress.value = arr[0][1];
         //    fm.RptorPhone.value = arr[0][2];
         // }
         
   	// }
   	// else
   	 //{
   	 	// return;
   	 //}
   //}
   else
   {
       return;
   }
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
    var AccDate2 =  tDate2;//��������
   
    //alert("AccDate:"+AccDate);
//    alert("AccDate2:"+AccDate2);
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
        /*var strSQL = " select count(*) from llreport a left join llregister b on a.rptno = b.rgtno "
                    + " where nvl(clmstate,0) != '70' "
                    + " and rptno in (select caseno from llcaserela where caserelano = '" +	fm.AccNo.value + "')"*/
        //prompt("��֤�¹��������������ϵĴ������ⰸ",strSQL);
         mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql68");
			mySql.addSubPara(fm.AccNo.value); 
		var tCount = easyExecSql(mySql.getString());
		
        if (tCount != null && tCount != "1" && tCount != "0")
        {
            alert("�¹��������������ϵĴ������ⰸ���������޸��¹����ڣ�");
            fm.AccidentDate.value = OAccDate;
            return false;
        }
    }
//    alert(2497);

    //У���������
    if (AccDate2 == null || AccDate2 == '')
    {
        alert("��������"+"����Ϊ�գ�");
        return false;
    }
    else
    {

       	//�Ƚϳ������ں͵�ǰ����
    	if (dateDiff(mCurrentDate,AccDate2,'D') > 0)
        {
        	alert("��������"+"�������ڵ�ǰ����!");
            return false; 
        }

        //�Ƚϳ������ں��¹�����       
    	if (dateDiff(AccDate2,AccDate,'D') > 0)
        {
        	alert("��������"+"���������¹�����!");
            return false; 
        }

    }
    
    return true;
}


/*
 * �����ŵ��Ų�ѯͶ���˿ͻ��ź�����
 */
function queryCustomerIn()
{
	 var strSQL = "  select customerno,grpname from lcgrppol where grpcontno= '"+fm.GrpContNo.value + "'"
     //prompt("���������ͬ�Ų�ѯͶ���˿ͻ��ź�����",strSQL);
	/*mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql69");
			mySql.addSubPara(fm.GrpContNo.value); */
	 var tAppnt = easyExecSql(strSQL);

	 if (tAppnt != null)
	 {
		 fm.GrpCustomerNo.value=tAppnt[0][0];
		 fm.GrpName.value=tAppnt[0][1];
	 }
}

