var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//������
function zhoulei()
{
	alert("������!");
}

//���ذ�ť
function goToBack()
{
    if (fm.Phase.value == '0')
    {
        top.close();  //�᰸����
    }
    else
    {
        location.href = "LLLClaimQueryMain.jsp?AppntNo=" + fm.Phase.value + "&ClmNo=" + fm.RptNo.value; //�����ⰸ��ѯ����
    }
//    top.close();
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
    var rptNo = fm.ClmNo.value;
    if(rptNo == "")
    {
        alert("�����ⰸΪ�գ�");
        return;
    }
    //�����¼���(һ��)
    /*var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimQueryReportInputSql");
	mySql.setSqlId("LLClaimQueryReportSql1");
	mySql.addSubPara(rptNo ); 
    var AccNo = easyExecSql(mySql.getString());

    //���������ż�����������Ϣ(һ��)
   /* var strSQL2 =" select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom, "
                +" (select username from llclaimuser where usercode = LLReport.Operator), "
                +" RgtClass,PostCode from LLReport where 1=1 "
                +" and RptNo = '"+rptNo+"'";*/
		mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimQueryReportInputSql");
	mySql.setSqlId("LLClaimQueryReportSql2");
	mySql.addSubPara(rptNo ); 
    var RptContent = easyExecSql(mySql.getString());

    //����ҳ������
    fm.AccNo.value = AccNo[0][0];
    fm.AccidentDate.value = AccNo[0][1];

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
    fm.PostCode.value = RptContent[0][12];
    showOneCodeName('llrgtclass','RgtClass','RgtClassName'); //�������
    showOneCodeName('llrgrelation','Relation','RelationName');//���������¹��˹�ϵ
    showOneCodeName('llrptmode','RptMode','RptModeName');//������ʽ
    showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
    showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ

    //�����ְ�������������Ϣ(����)
    /*var strSQL3 = " select CustomerNo,Name,"
                         + " Sex,"
                         + " (case trim(Sex) when '0' then '��' when '1' then 'Ů' else '����' end) as SexNA,"
                         + " Birthday,"
                         + " nvl(SocialInsuFlag,0) as SocialInsuFlag, "
                         + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end) as �Ƿ����籣��־ "
                         + " from LDPerson where "
                         + " CustomerNo in("
                         + " select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";*/
    		mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimQueryReportInputSql");
	mySql.setSqlId("LLClaimQueryReportSql3");
	mySql.addSubPara(rptNo ); 
    easyQueryVer3Modal(mySql.getString(), SubReportGrid);
    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        SubReportGridClick(0);
    }
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        initQuery();
        fm.RptConfirm.disabled = false;
    }
    tSaveFlag ="0";
}

//����ȷ�Ϸ��غ�ִ�еĲ���
function afterSubmit2( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");\
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
	    alert("��ѡ������ˣ�");
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
	if (tCustomerNo == "")
	{
	    alert("��ѡ������ˣ�");
	    return;
	}
    var strUrl="LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo+"&ClmNo="+fm.RptNo.value;
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
    /*var strSQL = "select count(1) from LLInqConclusion where "
                + "ClmNo = '" + fm.RptNo.value+"'";*/
        		mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimQueryReportInputSql");
	mySql.setSqlId("LLClaimQueryReportSql4");
	mySql.addSubPara(fm.RptNo.value); 
    var tCount = easyExecSql(mySql.getString());
//    alert(tCount);
    if (tCount == "0")
    {
    	  alert("���ⰸ��û�е�����Ϣ��");
    	  return;
    }
	  var strUrl="LLInqQueryMain.jsp?claimNo="+fm.RptNo.value;
      window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"�鿴����");
}

//�򿪳ʱ���ѯ
function showQuerySubmit()
{
	  //---------------------------------------
	  //�жϸ��ⰸ�Ƿ���ڳʱ�
	  //---------------------------------------
//    var strSQL = "select count(1) from LLSubmitApply where "
//                + "ClmNo = " + fm.RptNo.value;
//    var tCount = easyExecSql(strSQL);
////    alert(tCount);
//    if (tCount == "0")
//    {
//    	  alert("���ⰸ��û�гʱ���Ϣ��");
//    	  return;
//    }
	  var strUrl="LLSubmitQueryMain.jsp?claimNo="+fm.RptNo.value;
      window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"�ʱ���ѯ");
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
//    window.open(strUrl,"�¹�������Ϣ");
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
	mySql.setResourceName("claim.LLClaimQueryReportInputSql");
	mySql.setSqlId("LLClaimQueryReportSql5");
	mySql.addSubPara(fm.AccidentDate.value); 
	mySql.addSubPara(fm.occurReason.value); 
	mySql.addSubPara(fm.customerNo.value); 
//    alert("strSQL1= "+strSQL1);
    AccNo = decodeEasyQueryResult(easyQueryVer3(mySql.getString()));
//    alert("AccNo= "+AccNo);
    if (AccNo == null )
    {
    	  fm.isReportExist.value = "false";
    	  alert("����������!");
    	  return
    }
    else
    {
        //���������ż�����������Ϣ(һ��)
        /*var strSQL2 = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom,(select username from llclaimuser where usercode = LLReport.Operator),RgtClass from LLReport where "
                    + "RptNo in (select CaseNo from LLCaseRela where "
                    + "CaseRelaNo = '"+ AccNo +"' and SubRptNo in (select SubRptNo from LLSubReport where 1=1 "
                    + getWherePart( 'CustomerNo','customerNo' )
                    + "))";*/
                		mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimQueryReportInputSql");
	mySql.setSqlId("LLClaimQueryReportSql6");
	mySql.addSubPara(AccNo); 
	mySql.addSubPara(fm.customerNo.value); 
//        alert("strSQL2= "+strSQL2);
        RptContent = decodeEasyQueryResult(easyQueryVer3(mySql.getString()));
//        alert("RptContent= "+RptContent);
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

            //�����ְ�������������Ϣ(����)
          /*  var strSQL3 = " select CustomerNo,Name,"
                                 + " Sex,"
                                 + " (case trim(Sex) when '0' then '��' when '1' then 'Ů' else '����' end) as SexNA,"
                                 + " Birthday,"
                                 + " nvl(SocialInsuFlag,0) as SocialInsuFlag, "
                                 + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end) as �Ƿ����籣��־ "
                                 + " from LDPerson where "
                                 + " CustomerNo in("
                                 + " select CustomerNo from LLSubReport where SubRptNo = '"+ RptContent[0][0] +"')";*/
          mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimQueryReportInputSql");
		mySql.setSqlId("LLClaimQueryReportSql7");
		mySql.addSubPara(RptContent[0][0]);  
            easyQueryVer3Modal(mySql.getString(), SubReportGrid);
        }
    }
}

//ѡ��SubReportGrid��Ӧ�¼�,tPhase=0Ϊ��ʼ��ʱ����
function SubReportGridClick(tPhase)
{
	//------------------------------------------**Beg
	//�ÿ���ر�
	//------------------------------------------**
	fm.customerName.value = "";
	fm.customerAge.value = "";
	fm.customerSex.value = "";
	fm.SexName.value = "";
	//fm.IsVip.value = "";
	//fm.VIPValueName.value = "";
	fm.hospital.value = "";
	fm.TreatAreaName.value = "";
	fm.MedicalAccidentDate.value = "";
	fm.OtherAccidentDate.value = "";
	fm.accidentDetail.value = "";
	fm.accidentDetailName.value = "";
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
        //fm.IsVip.value = SubReportGrid.getRowColData(i,6);
        //fm.VIPValueName.value = SubReportGrid.getRowColData(i,7);
        showOneCodeName('sex','customerSex','SexName');//�Ա�
    }

    //��ѯ�����������
    var tClaimType = new Array;
 /*   var strSQL1 = "select ReasonCode from LLReportReason where "
                + "RpNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";*/
             mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimQueryReportInputSql");
		mySql.setSqlId("LLClaimQueryReportSql8");
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
    /*var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccDesc,AccResult1,AccResult2,Medaccdate from LLSubReport where 1=1 "
                + getWherePart( 'SubRptNo','RptNo' )
                + getWherePart( 'CustomerNo','customerNo' );*/
   mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimQueryReportInputSql");
		mySql.setSqlId("LLClaimQueryReportSql9");
		mySql.addSubPara(fm.RptNo.value);  
		mySql.addSubPara(fm.customerNo.value);  
//    alert(strSQL2);
    tSubReport = easyExecSql(mySql.getString());

    fm.hospital.value = tSubReport[0][0];
    fm.OtherAccidentDate.value = tSubReport[0][1];
    fm.accidentDetail.value = tSubReport[0][2];
//    fm.IsDead.value = tSubReport[0][3];
    fm.cureDesc.value = tSubReport[0][4];
    fm.RemarkDisabled.value = tSubReport[0][5];
    fm.AccDescDisabled.value = tSubReport[0][6];
    fm.AccResult1.value = tSubReport[0][7];
    fm.AccResult2.value = tSubReport[0][8];
    fm.MedicalAccidentDate.value = tSubReport[0][9];
    showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ
    showOneCodeName('llaccidentdetail','accidentDetail','accidentDetailName');//����ϸ��
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//�������
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//���ս��1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//���ս��2
    queryResult('AccResult1','AccResult1Name');
    queryResult('AccResult2','AccResult2Name');
    
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

//�õ�0��icd10��
function saveIcdValue()
{
    fm.ICDCode.value = fm.AccResult1.value;
}

//��ѯ���ս��
function queryResult(tCode,tName)
{
   /* var strSql = " select ICDName from LDDisease where "
               + " trim(ICDCode) = '" + document.all(tCode).value + "'";*/
      mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimQueryReportInputSql");
		mySql.setSqlId("LLClaimQueryReportSql10");
		mySql.addSubPara(document.all(tCode).value);  
		
    var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        document.all(tName).value = tICDName;
    }
}

//Ӱ���ѯ---------------2005-08-14���
function colQueryImage()
{
	//alert("Ӱ���ѯ");
	//return;	
	var strUrl="LLColQueryImageMain.jsp?claimNo="+document.all('ClmNo').value+"&subtype=LP1001";
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
//��ӡ�ⰸ��������
function colBarCodePrint()
{
    fm.action="LLClaimBarCodePrintSave.jsp";
    fm.target = "../f1print";
    fm.submit();
}

//��֤������Ϣ��ѯ,Added by niuzj,2005-11-01
function PrtAgainInfo()
{ 
	//alert("��֤������Ϣ��ѯ");
	var tClmNo = fm.ClmNo.value;   //�ⰸ��
	/*var strSQL =" Select count(*) from loprtmanager t "
	           +" where t.otherno='" + tClmNo + "' "
	           +" and t.patchflag='1' ";*/
	      mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimQueryReportInputSql");
		mySql.setSqlId("LLClaimQueryReportSql11");
		mySql.addSubPara(tClmNo); 
	var arr = easyExecSql( mySql.getString() );
  if(arr==0 || arr==null || arr=="")  //���ֵΪ1,��������
  {
    alert("���ⰸ��û�в�����ĵ�֤!");
  } 
  else
  {
	  var strUrl = "LLClaimPrtAgainInfoMain.jsp?ClmNo="+tClmNo;
	  window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0'); 
  }
  
}