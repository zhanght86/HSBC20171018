//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass(); //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass();  //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var mySql = new SqlClass();
var sel="";

//�����򿪻�رա�DIV����
function showMyPage(spanID,flag)
{
  if(!flag)//�ر�
  { 
    spanID.style.display="none";
  }
  else    //��
  {
    spanID.style.display="";
  }
}

//ҳ���ʼ��,�������κ�<Ĭ��Ϊ10λ>,
function initApplyPrepayNo()
{
//	//����������κ�,������������������κżӡ�10����<��һ����Ϊ--1000000000>
//    var strSQL = "select max(prepayno) from llprepaydetail";// order by PrepayNo DESC";
//    var arr = easyExecSql(strSQL);
////    alert(arr);
//    if (arr == "" || arr == null) 
//    {
//    	  tMaxNo = 1000000000; 	  
//    	  tMaxNo = parseInt(tMaxNo)+10;
//    }
//    else
//    {
//    	  tMaxNo = parseInt(arr[0][0])+10;
//    }	
//	tMaxNo = parseInt(tMaxNo);
//	fm.PrepayNo.value = "";
	fm.PrepayNo.value ="0";
}

//[��ѯԤ���ڵ���Ϣ]��Ϊ���� ��˽ڵ� ׼���ṩ����
function initLLClaimPrepayNodeQuery()
{
//    ��ѯ��Ԥ���ڵ㡱���ݣ�Ϊ���ɴ���˽ڵ� ׼������
/*	strSQL = "select missionprop1,missionprop2,missionprop3,missionprop4,missionprop5,missionprop6,missionprop7,missionprop8,missionprop9,missionprop10,missionprop12,missionprop19,missionprop20,missionprop18,missionid,submissionid,activityid from lwmission where 1=1 "
           + getWherePart( 'missionid','MissionID' )
		   + getWherePart( 'submissionid','SubMissionID' )
           + getWherePart( 'activityid','ActivityID' ); */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPrepayInputSql");
	mySql.setSqlId("LLClaimPrepaySql1");
	mySql.addSubPara(fm.MissionID.value ); 
	mySql.addSubPara(fm.SubMissionID.value ); 
	mySql.addSubPara(fm.ActivityID.value ); 
//    alert(strSQL);     
    var arr = new Array();    
    arr=easyExecSql(mySql.getString());  
    if(arr==null)
    {
       alert("���ݲ�ѯ����");   
       return;  	
    }
	else
	{
		fm.ClmNo.value=arr[0][0];
		fm.tRptorState.value=arr[0][1];
		fm.CustomerNo.value=arr[0][2];
		fm.CustomerName.value=arr[0][3];
		fm.CustomerSex.value=arr[0][4];
		fm.tAccDate.value=arr[0][5];
		//fm.tRgtClass.value=arr[0][6];
		fm.tRgtObj.value=arr[0][7]; 
		fm.tRgtObjNo.value=arr[0][8]; 
		fm.AuditPopedom.value=arr[0][9]; 
		fm.tPrepayFlag.value="1";
		fm.tComeWhere.value=arr[0][10];		
		fm.tMngCom.value=arr[0][12];
		fm.tComFlag.value=arr[0][13];  // <!--//Ȩ�޿缶��־-->
		fm.MissionID.value=arr[0][14];
		fm.SubMissionID.value=arr[0][15];
		fm.ActivityID.value=arr[0][16];	
	}	
}


//��ʼ���� ����������Ϣ�б��������ⰸ�Ų�ѯLLClaimDetail��
function initLLClaimDetailGridQuery()
{
	try
	{
		/*var	strSQL = " select t.clmno,t.caseno,t.polno,t.customerno,t.dutycode, "
		           +" (select codename from ldcode where codetype='llclaimtype' and trim(t.getdutykind)=trim(code)), "
		           +" t.getdutycode,t.CaseRelaNo,t.RealPay,t.PrepayFlag,t.PrepaySum,t.getdutykind,"
		           +" (select name from ldperson where customerno=t.customerno) "
	             + " from llclaimdetail t  where 1=1 "
		           + " and t.clmno='"+ fm.ClmNo.value +"'";	*/
		  mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPrepayInputSql");
	mySql.setSqlId("LLClaimPrepaySql2");
	mySql.addSubPara(fm.ClmNo.value ); 
	
//		arr = easyExecSql( strSQL );
//	    if (arr==null ||arr=="")
//	    {
//			alert("�ڸ��ⰸ��δ�ҵ��κ�Ԥ����ϸ��Ϣ��");    
//			fm.Bnf.disabled=true;
//			fm.PrepayCofirm.disabled=true;
//			return;     	
//	    } 
        turnPage.queryModal(mySql.getString(),LLClaimDetailGrid);   
        var rowNum=LLClaimDetailGrid. mulLineCount ; //���� 
        if(rowNum==0)
        {
			alert("�ڸ��ⰸ��δ�ҵ��κ�Ԥ����ϸ��Ϣ��");    
			fm.Bnf.disabled=true;  //[�����˷���]��ť������
			fm.PrepayCofirm.disabled=true;//[Ԥ��ȷ��]��ť������
			return;          	
        }
	}
	catch(ex)
    {
      alert("LLClaimPrepay.js-->initLLClaimDetailGridQuery�����з����쳣:���ݲ�ѯ����!");
    }

}

//��Ӧ�� ����������Ϣ�б������ѡť�ĺ�������ѯԤ����ϸ��¼��LLPrepayDetail��
function LLClaimDetailGridClick()
{
//	  alert(strSQl);	     
	var selno= LLClaimDetailGrid.getSelNo()-1;
	sel=LLClaimDetailGrid.getSelNo()-1;
	fm.ClmNo.value= LLClaimDetailGrid.getRowColData(selno, 1);//"�ⰸ��" 
	fm.CaseNo.value= LLClaimDetailGrid.getRowColData(selno, 2);//"�ְ���"
	fm.PolNo.value= LLClaimDetailGrid.getRowColData(selno, 3);//"������"
	fm.DutyCode.value= LLClaimDetailGrid.getRowColData(selno, 5);//"���α���"
	fm.GetDutyKind.value= LLClaimDetailGrid.getRowColData(selno, 13);//"������������ 
	fm.GetDutyCode.value= LLClaimDetailGrid.getRowColData(selno, 7);//"�������α���"
	fm.CaseRelaNo.value= LLClaimDetailGrid.getRowColData(selno, 8);//"�����¹ʺ�"
	initLLPrepayDetailGridQuery();//��ѯ�ü�¼��Ԥ�����
	//fm.LLPrepayAdd.disabled=false;//����Ԥ����ť����
	//divLLPrepayDetail.style.display="none";

}
	

function initLLPrepayDetailGridQuery()
{
	/*var	strSQL = " select a.clmno,a.caseno,a.polno,a.getdutykind,a.getdutycode,a.prepayno,"
        +"  a.serialno,a.prepaysum,a.modifydate from LLPrepayDetail a "
        + " where 1=1 and a.clmno='"+ fm.ClmNo.value +"'";	*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPrepayInputSql");
	mySql.setSqlId("LLClaimPrepaySql3");
	mySql.addSubPara(fm.ClmNo.value ); 
	//prompt("initLLPrepayDetailGridQuery:",strSQL);
    turnPage2.queryModal(mySql.getString(),LLPrepayDetailGrid);     	         
}

//"����"��ť
function LLPrepayDetailAdd()
{
	var selno= LLClaimDetailGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��һ��ҪԤ���ĸ������");
	      return;
	}	
	fm.PrepaySum.value="";
    divLLPrepayDetail.style.display="";
    fm.LLPrepayAdd.disabled=true;
    fm.LLPrepayCancel.disabled=false;
//	showMyPage(divLLPrepayDetail,true);
}
//"ȡ��"��ť
function LLPrepayDetailCancel()
{
//	showMyPage(divLLPrepayDetail,false);
    divLLPrepayDetail.style.display="none";
    fm.LLPrepayAdd.disabled=false;
    fm.LLPrepayCancel.disabled=true;
}
//[����]��ˢ��ҳ������
function RenewPage()
{

}

//"����"��ť
function LLPrepayDetailSave()
{

	  if(!KillTwoWindows(fm.ClmNo.value,'30'))
	  {
	  	  return false;
	  }	
	if(fm.CasePayMode.value=="" ||fm.PrepaySum.value=="" )
	{
		alert("Ԥ�����δ��д��֧����ʽδѡ��");
		return;		
	}
	 if (!isNumeric(fm.PrepaySum.value))
    {
        alert("���ý����д����");
        return;
    }
	fm.fmtransact.value="Prepay||Insert";
	fm.action="LLClaimPrepaySave.jsp";
	submitForm(); //�ύ	
}


//�����ء���ť
function Return()
{
    location.href="LLClaimPrepayMissInput.jsp?";//����	
}

//�����ύ����
function submitForm()
{
    //�ύ����
    var i = 0;
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.submit(); //�ύ
    tSaveFlag ="0";    
}

//Ԥ�������ύ�����,����<����[����]��ť�ķ���>
function afterSubmit( FlagStr, content )
{

    showInfo.close();
    tSaveFlag ="0";
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
        Return();//�����ء���ť������Ԥ������
    }
}

//Ԥ��ȷ���ύ�����,����
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
        initLLPrepayDetailGridQuery();
    	initLLClaimDetailGridQuery();//ˢ��ҳ��  
    }
    tSaveFlag ="0";
}

//Ӱ���ѯ---------------2005-08-14���
function colQueryImage()
{
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
	//fm.IsVip.value = "";
	//fm.VIPValueName.value = "";
	fm.hospital.value = "";
	fm.TreatAreaName.value = "";
	fm.OtherAccidentDate.value = "";
	fm.MedicalAccidentDate.value = "";
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
        fm.CustomerNo.value = SubReportGrid.getRowColData(i,1);
        fm.customerName.value = SubReportGrid.getRowColData(i,2);
        fm.customerSex.value = SubReportGrid.getRowColData(i,3);
        fm.customerAge.value = calAge(SubReportGrid.getRowColData(i,5));
        //fm.IsVip.value = SubReportGrid.getRowColData(i,6);
        //fm.VIPValueName.value = SubReportGrid.getRowColData(i,7);
        showOneCodeName('sex','customerSex','SexName');//�Ա�
    }

	
    //��ѯ�����������
    var tClaimType = new Array;
   /* var strSQL1 = "select ReasonCode from LLAppClaimReason where "
                + " CaseNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.CustomerNo.value+"'";
//    alert(tClaimType);
;*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPrepayInputSql");
	mySql.setSqlId("LLClaimPrepaySql4");
	mySql.addSubPara(fm.RptNo.value ); 
	mySql.addSubPara(fm.CustomerNo.value ); 
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
   //modifyed by niuzj,2005-11-05
   /* var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccdentDesc,AccResult1,AccResult2,EditFlag,AffixConclusion,(case EditFlag when '0' then '��' when '1' then '��' end),(case AffixConclusion when '0' then '��' when '1' then '��' end),medaccdate  from LLCase where 1=1 "
                + getWherePart( 'CaseNo','RptNo' )
                + getWherePart( 'CustomerNo','CustomerNo' );*/
     mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPrepayInputSql");
	mySql.setSqlId("LLClaimPrepaySql5");
	mySql.addSubPara(fm.RptNo.value ); 
	mySql.addSubPara(fm.CustomerNo.value );            
    //prompt("Ԥ������-��ѯ�ְ�����Ϣ",strSQL2);
    tSubReport = easyExecSql(mySql.getString());
//    alert(tSubReport);
    fm.hospital.value = tSubReport[0][0];
    fm.OtherAccidentDate.value = tSubReport[0][1];
    fm.accidentDetail.value = tSubReport[0][2];
//    fm.IsDead.value = tSubReport[0][3];
    fm.cureDesc.value = tSubReport[0][4];
    fm.Remark.value = tSubReport[0][5];
    fm.AccDesc.value = tSubReport[0][6];
    fm.AccResult1.value = tSubReport[0][7];
    fm.AccResult2.value = tSubReport[0][8];
    
    
    //*************Added by niuzj,2005-11-05*********************
       fm.IsModify.value = tSubReport[0][9];
       fm.IsAllReady.value = tSubReport[0][10];
       fm.IsModifyName.value = tSubReport[0][11];
       fm.IsAllReadyName.value = tSubReport[0][12];
       fm.MedicalAccidentDate.value = tSubReport[0][13];
    //************************************************************
       
    
    showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ
    showOneCodeName('llaccidentdetail','accidentDetail','accidentDetailName');//����ϸ��
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//������ʶ
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//�������
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//���ս��1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//���ս��2
    queryResult('AccResult1','AccResult1Name');
    queryResult('AccResult2','AccResult2Name');
    queryHospital('hospital','TreatAreaName');    
}


//������ѯ
function queryRegister()
{
    var rptNo = fm.RptNo.value;
    if(rptNo == "")
    {
        alert("�����ⰸΪ�գ�");
        return;
    }
    //�����¼��š������¹ʷ�������(һ��)
    /*var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";*/
     mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPrepayInputSql");
	mySql.setSqlId("LLClaimPrepaySql6");
	mySql.addSubPara(rptNo); 
	
    //prompt("Ԥ������-�����¼��š������¹ʷ�������(һ��):",strSQL1);
    var AccNo = easyExecSql(mySql.getString());

    //���������ż�����������Ϣ(һ��)
    //----------------------1------2-----------3-----------4------------5--------6------------7-------8--------9-----------10---------11--------12-----------13--------------14---------15-----------16------------17------------18---------19
   /* var strSQL2 =" select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate, "
                +" (select username from llclaimuser where usercode = llregister.Operator),mngcom, "
                +" (case assigneetype when '0' then 'ҵ��Ա' when '1' then '�ͻ�' end),assigneecode,assigneename, "
                +" (case assigneesex when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end), "
                +" assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion,RgtState,RgtClass, "
                +" (case RgtClass when '1' then '����' when '2' then '����' when '3' then '��ͥ' end), "
                +" (case getmode when '1' then 'һ��ͳһ����' when '2' then '�����ʽ��ȡ' when '3' then '����֧��' end),"
                +" accepteddate"
                +" from llregister where 1=1 "
                +" and rgtno = '"+rptNo+"'";*/
     mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPrepayInputSql");
	mySql.setSqlId("LLClaimPrepaySql7");
	mySql.addSubPara(rptNo); 
    //prompt("Ԥ������-���������ż�����������Ϣ(һ��):",strSQL2);
    var RptContent = easyExecSql(mySql.getString());
    

    //����ҳ������
    fm.AccNo.value = AccNo[0][0];
    fm.AccidentDate.value = AccNo[0][1];

    fm.RptNo.value = RptContent[0][0];
    fm.RptorName.value = RptContent[0][1];

    fm.RptorPhone.value = RptContent[0][2];
    fm.RptorAddress.value = RptContent[0][3];
    fm.Relation.value = RptContent[0][4];
    fm.AccidentSite.value = RptContent[0][5];
    fm.RptDate.value = RptContent[0][6];
    fm.Operator.value = RptContent[0][7];
    fm.MngCom.value = RptContent[0][8];

    fm.AssigneeType.value = RptContent[0][9];
    fm.AssigneeCode.value = RptContent[0][10];
    fm.AssigneeName.value = RptContent[0][11];
    fm.AssigneeSex.value = RptContent[0][12];
    fm.AssigneePhone.value = RptContent[0][13];
    fm.AssigneeAddr.value = RptContent[0][14];
    fm.AssigneeZip.value = RptContent[0][15];
    fm.occurReason.value = RptContent[0][16];

    fm.RgtConclusion.value = RptContent[0][17];

    fm.tRgtClass.value = RptContent[0][19];
    fm.RgtClassName.value = RptContent[0][20];

    fm.GetMode.value = RptContent[0][21];
    fm.AcceptedDate.value = RptContent[0][22]; 
    
    showOneCodeName('llrgrelation','Relation','RelationName');//���������¹��˹�ϵ

    showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
    showOneCodeName('llrgtconclusion','RgtConclusion','RgtConclusionName');//��������

    //****************************************************
    //����ҳ��Ȩ��
    //****************************************************
    fm.AccidentDate.readonly = true;

    fm.claimType.disabled=true;
    fm.Remark.disabled=true;

    //���ð�ť++++++++++++++++++++++++++++++++++++++++�����

    //�����ְ�������������Ϣ(����)
    /*var strSQL3 = " select CustomerNo,Name,"
                         + " Sex,"
                         + " (case trim(Sex) when '0' then '��' when '1' then 'Ů' when '2' then '����' end) as SexNA,"
                         + " Birthday,"
                         + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                         + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end) as �Ƿ����籣��־ "
                         + " from LDPerson where "
                         + " CustomerNo in("
                         + " select CustomerNo from LLCase where CaseNo = '"+ rptNo +"')";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPrepayInputSql");
	mySql.setSqlId("LLClaimPrepaySql8");
	mySql.addSubPara(rptNo); 
    //prompt("Ԥ������-�����ְ�������������Ϣ(����):",strSQL3);
    easyQueryVer3Modal(mySql.getString(), SubReportGrid);
    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        SubReportGridClick(0);
    }
    
    //��ѯ�ⰸ��־
    QueryClaimFlag();
    
    //��ѯ���,��������
    queryAudit();
}


//��˽��۲�ѯ
function queryAudit()
{
    /*var strSql = " select AuditConclusion,(select codename from ldcode where codetype = 'llclaimpreconclusion' and code = auditconclusion),AuditIdea,"
    	       + " ExamConclusion,(select codename from ldcode where codetype = 'llpreExamconclusion' and COMCODE = ExamConclusion and OTHERSIGN='4'),ExamIdea "
    	       + " from LLClaimUWMain where  ClmNo = '" + fm.ClmNo.value + "'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPrepayInputSql");
	mySql.setSqlId("LLClaimPrepaySql9");
	mySql.addSubPara(fm.ClmNo.value ); 
    //prompt("strSql",strSql);
    var tAudit = easyExecSql(mySql.getString());
//    alert(tAudit);
    if (tAudit != null)
    {
    	//��˽���
        fm.AuditConclusion.value = tAudit[0][0];
        fm.AuditConclusionName.value = tAudit[0][1];
        fm.AuditIdea.value = tAudit[0][2];
        
        //�ֹ�˾��������
        //fm.DecisionSP.value = tAudit[0][3];
        //fm.DecisionSPName.value = tAudit[0][4];
        fm.RemarkSP.value = tAudit[0][5];
    }
}

//��ѯ���ս��
function queryResult(tCode,tName)
{
   /* var strSql = " select ICDName from LDDisease where "
               + " trim(ICDCode) = '" + document.all(tCode).value + "'";*/
     mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPrepayInputSql");
	mySql.setSqlId("LLClaimPrepaySql10");
	mySql.addSubPara(document.all(tCode).value ); 
    var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        document.all(tName).value = tICDName;
    }
}

//��ѯ����ҽԺ��Added by niuzj,2005-11-26
function queryHospital(tCode,tName)
{
	 /* var strSql =" select hospitalname from llcommendhospital  "
	             +" where trim(hospitalcode)='"+document.all(tCode).value+"' ";*/
	   mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPrepayInputSql");
	mySql.setSqlId("LLClaimPrepaySql11");
	mySql.addSubPara(document.all(tCode).value ); 
	  var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        document.all(tName).value = tICDName;
    }
}


//������ѯ
//���ա��ͻ��š���LCpol�н��в�ѯ����ʾ�ÿͻ��ı�����ϸ
function showInsuredLCPol()
{
//  var row = SubReportGrid.getSelNo();
//  if(row < 1)
//  {
//      alert("��ѡ��һ�м�¼��");
//      return;
//  }
//	var CustomerNo = SubReportGrid.getRowColData(row-1,1);
//Modifyed by niuzj,2005-12-24
var CustomerNo = fm.customerNo.value;
	if (CustomerNo == "")
	{
	    alert("��ѡ������ˣ�");
	    return;
	}
  var strUrl="LLLcContQueryMain.jsp?AppntNo="+CustomerNo;
  window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�����ⰸ��ѯ
function showOldInsuredCase()
{
//  var row = SubReportGrid.getSelNo();
//  if(row < 1)
//  {
//      alert("��ѡ��һ�м�¼��");
//      return;
//  }
//	var CustomerNo = SubReportGrid.getRowColData(row-1,1);
//Modifyed by niuzj,2005-12-24
var CustomerNo = fm.customerNo.value;
	if (CustomerNo == "")
	{
	    alert("��ѡ������ˣ�");
	    return;
	}
  var strUrl="LLLClaimQueryMain.jsp?AppntNo="+CustomerNo+"&ClmNo="+fm.RptNo.value;
  window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


//Ӱ���ѯ---------------2005-08-14���
function colQueryImage()
{
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


//У�������˷��������ɲ��ܵ����˽��۱���
function checkBnf()
{
	/*var strSql = "select bnfkind from llbnf where 1=1 and  bnfkind='B' "
        + getWherePart( 'ClmNo','ClmNo' );*/
	  mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPrepayInputSql");
	mySql.setSqlId("LLClaimPrepaySql12");
	mySql.addSubPara(fm.ClmNo.value ); 
	var tBNF = easyExecSql(mySql.getString());
	
	prompt("������������ʱУ���Ƿ�ִ�������˷���",strSql);
	alert("tBNF:"+tBNF);
	
	if (tBNF==null)
	{
		alert("�����˷���û�����,���ܵ��������˽���!");
		return false;
	}
	
	return true;
}



//У���Ƿ����Ԥ��ȷ��
function checkConfirmPay()
{
	
	//�жϱ������Ƿ�Ϊ��
	if (fm.DecisionSP.value == "" || fm.DecisionSP.value == null)
	{
		alert("����ѡ����������!");
      return;
	}
	
	if (fm.RemarkSP.value == "" || fm.RemarkSP.value == null){
			alert("������д�������!");
	      return;
	}
	  
	// ����������700�ַ�ʱ����ʾ��2006-01-01 С��
	if(fm.RemarkSP.value.length >=700)
	{
	  	alert("����������ܳ���700���ַ�������������д��");
	  	return;
	}
	
	
	return true;
}


//"Ԥ��ȷ��"��ť��Ԥ���ڵ����������ɴ���˽ڵ㣬����ص�����˹��������У�������Ԥ����־��
function LLClaimPrepayCofirm()
{
	
	if(!KillTwoWindows(fm.ClmNo.value,'35'))
	{
	  	  return false;
	}	
	
    //У����������Ƿ����¼��
    if (!checkConfirmPay())
    {
        return;
    } 
    
    //���ӷֹ�˾�ϱ����̵Ĵ����ϱ���û���й�������ת��ֻ������ȷ�������ˣ����ܹ�˾��������
   /* if(fm.DecisionSP.value == '2')
    {
       //alert("������ת���ϱ�����");
       fm.fmtransact.value = "UPREPORT";
       fm.action = './LLClaimConfirmReportBackSave.jsp';
    }
    else{   	
    	
    	fm.fmtransact.value="Prepay||Confirm";	
    	fm.action="./LLClaimPrepayCofirmSave.jsp";
    }
    */
    fm.action="./LLClaimPrepayCofirmSave.jsp";//lzf
	submitForm(); //�ύ		
}


//��ʼ���� ���������������ⰸ�Ų�ѯLLClaimUWMain��
function initLLClaimUWMainQuery()
{
	try
	{
		/*var	strSQL = "select a.auditconclusion,(select codename from ldcode where codetype = 'llclaimpreconclusion' and code = a.auditconclusion),"
	    	         +" a.auditidea from LLClaimUWMain a where clmno = '"+fm.ClmNo.value+"'";*/
	  mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPrepayInputSql");
	mySql.setSqlId("LLClaimPrepaySql13");
	mySql.addSubPara(fm.ClmNo.value ); 
		//prompt("initLLClaimUWMainQuery-��ʼ���� ���������������ⰸ�Ų�ѯLLClaimUWMain��",strSQL);

		var tAuditConclusion = easyExecSql(mySql.getString());
		
		if (tAuditConclusion)
		{
			for (var i = 0; i < tAuditConclusion.length; i++)
			{
			    fm.AuditConclusion.value=tAuditConclusion[0][0];
			    fm.AuditConclusionName.value=tAuditConclusion[0][1];
			    fm.AuditIdea.value=tAuditConclusion[0][2];
			}
		}
	}
	catch(ex)
    {
      alert("LLClaimPrepayAudit.js-->initLLClaimUWMainQuery�����з����쳣:���ݲ�ѯ����!");
    }

}


//��������������--��ѯ 2005-08-16���
function LLQueryUWMDetailClick()
{
//	alert("��������������ѯ");
    var strUrl="LLColQueryClaimUWMDetailMain.jsp?ClmNo="+fm.ClmNo.value;
//    window.open(strUrl,"");
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}