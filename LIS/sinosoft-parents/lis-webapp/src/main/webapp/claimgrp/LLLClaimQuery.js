var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//��ʼ����ѯ
function initQuery()
{
	var tClmNo = fm.ClmNo.value;
	var strSQL = "";
	/*strSQL = "select a.RgtNo, "
           + " (select c.codename from ldcode c where c.codetype = 'llrgttype' and trim(c.code)=trim(a.RgtState)), "
           + " (select c.codename from ldcode c where c.codetype = 'llrgtclass' and trim(c.code)=trim(a.RgtClass)), "
           + " (select c.codename from ldcode c where c.codetype = 'llrgtclass' and trim(c.code)=trim(a.RgtObj)), "
           + " a.RgtObjNo, "
           + " a.RgtDate, "
           + " (select c.codename from ldcode c where c.codetype = 'llclaimstate' and trim(c.code)=trim(a.ClmState)) "
           + " from LLRegister a "
           + " where a.RgtNo in (select t.CaseNo from LLCase t where t.CustomerNo = '"+ document.all('AppntNo').value +"')"
           //+ " and a.ClmState in ('60','70')" ; */
  /*strSQL="select a.clmno,a.grpcontno,a.insuredno,a.insuredname,(select codename from ldcode where codetype='getdutykind' and code=a.getdutykind),"
        +" case a.clmstate when '10' then '������' when '20' then '�����' when '30' then '��ǩ��'"
        +" when '35' then 'Ԥ��' when '40' then '����' when '50' then '�᰸' when '60' then '���'"
        +" when '70' then '�ر�' end ,"/*+"b.accdate,"*/ /*+"b.endcasedate,a.riskcode,a.standpay,"
        +"case when c.clmstate in ('10','20') then 0 else "
		+"nvl((select realpay from llclaim where clmno = a.clmno),0) end"
        +" from llclaimpolicy a,llcase b,llregister c where a.clmno=b.caseno and a.clmno = c.rgtno"
        +" and a.insuredno=b.customerno and a.insuredno = '"+ document.all('AppntNo').value +"'"
        +"and a.clmno !='"+tClmNo+"'";*/
        //+" and b.rgtstate='60' " ;                     
//               prompt("",strSQL);
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLLClaimQueryInputSql");
	mySql.setSqlId("LLLClaimQuerySql1");
	mySql.addSubPara(document.all('AppntNo').value );
	mySql.addSubPara(tClmNo);
	turnPage.pageLineNum=5;    //ÿҳ5��
    turnPage.queryModal(mySql.getString(), LLLcContSuspendGrid);
	arrResult = easyExecSql(mySql.getString());
    if (arrResult)
    {
    	displayMultiline(arrResult,LLLcContSuspendGrid);
    }
}
//LLLcContSuspendGrid����Ӧ����
function LLLcContSuspendGridClick()
{
	var i = LLLcContSuspendGrid.getSelNo();
    i = i - 1;
    fm.ContNo.value=LLLcContSuspendGrid.getRowColData(i,2);//��ͬ��
}

//�����桱��ť��Ӧ����
function saveClick()
{
    fm.isReportExist.value="false";
	fm.fmtransact.value="update";
	submitForm();
}
//����ѯ����ť
function queryClick()
{
//	//���ȼ����Ƿ�ѡ���¼  
	var row = LLLcContSuspendGrid.getSelNo();
	var tContNo="";
    if (row < 1)
    {
    	alert("��ѡ��һ����¼��");
        return;
    } 
        
    tContNo=fm.ContNo.value;//��ͬ��    
    location.href="../sys/PolDetailQueryMain.jsp?ContNo="+tContNo+"&IsCancelPolFlag=0";
}
//����
//function goToBack()
//{
//    location.href="LLReportInput.jsp";
//}

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

    fm.action = './LLLcContSuspendSave.jsp';
    document.getElementById("fm").submit(); //�ύ
    tSaveFlag ="0"; 
}
//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
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
    //tSaveFlag ="0";
    initQuery();//ˢ��
}

//��ʼ����ѯ
function initQuery2(tClmNo)
{
   // var rptNo = fm.ClmNo.value;
   var rptNo = tClmNo;
    if(rptNo == "")
    {
        alert("�����ⰸΪ�գ�");
        return;
    }
    //�����¼���(һ��)
   /* var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLLClaimQueryInputSql");
	mySql.setSqlId("LLLClaimQuerySql2");
	mySql.addSubPara(rptNo);            
//    alert("strSQL1= "+strSQL1);
    var AccNo = easyExecSql(mySql.getString());
//    alert("AccNo= "+AccNo);
    //���������ż�����������Ϣ(һ��)
   /* var strSQL2 = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom,Operator from LLReport where "
                + "RptNo = '"+rptNo+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLLClaimQueryInputSql");
	mySql.setSqlId("LLLClaimQuerySql3");
	mySql.addSubPara(rptNo);                   
//    alert("strSQL2= "+strSQL2);
    var RptContent = easyExecSql(mySql.getString());
//    alert("RptContent= "+RptContent);
    //����ҳ������
    fm.AccNo.value = AccNo[0][0];
//    fm.AccidentDate.value = AccNo[0][1];
    
    fm.RptNo.value = RptContent[0][0];
    fm.RptorName.value = RptContent[0][1];
    fm.RptorPhone.value = RptContent[0][2];
    fm.RptorAddress.value = RptContent[0][3];
    fm.Relation.value = RptContent[0][4];
    fm.RptMode.value = RptContent[0][5];
    fm.AccidentSite.value = RptContent[0][6];
//    fm.occurReason.value = RptContent[0][7];
    fm.RptDate.value = RptContent[0][8];
    fm.MngCom.value = RptContent[0][9];
    fm.Operator.value = RptContent[0][10];
    showOneCodeName('Relation','Relation','RelationName');//���������¹��˹�ϵ
    showOneCodeName('RptMode','RptMode','RptModeName');//������ʽ
//    showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
//    showOneCodeName('hospitalcode','hospital','TreatAreaName');//����ҽԺ
//    showOneCodeName('lloccurreason','IsDead','IsDeadName');//������־
    
    //****************************************************
    //����ҳ��Ȩ��
    //****************************************************    
//    fm.AccidentDate.readonly = true;

//    fm.QueryPerson.disabled=true;
//    fm.QueryReport.disabled=true;

//    fm.occurReason.disabled=true;
//    fm.accidentDetail.disabled=true;
//    fm.claimType.disabled=true;
//    fm.Remark.disabled=true;

//    fm.AddReport.disabled=false;
//    fm.addbutton.disabled=false;
//    fm.updatebutton.disabled=false;
//    fm.DoHangUp.disabled=false;
/*    fm.CreateNote.disabled=false;
    fm.BeginInq.disabled=false;
    fm.ViewInvest.disabled=false;
   	fm.Condole.disabled=false;
    fm.SubmitReport.disabled=false;
    fm.ViewReport.disabled=false;
    fm.AccidentDesc.disabled=false;
   	fm.QueryCont1.disabled=false;
    fm.QueryCont2.disabled=false;
    fm.QueryCont3.disabled=false;*/

    //�����ְ�������������Ϣ(����)
    /*var strSQL3 = "select CustomerNo,Name,Sex,Birthday,VIPValue from LDPerson where "
                + "CustomerNo in("
                + "select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";*/
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLLClaimQueryInputSql");
	mySql.setSqlId("LLLClaimQuerySql4");
	mySql.addSubPara(rptNo);   
//    alert("strSQL3= "+strSQL3);
    easyQueryVer3Modal(mySql.getString(), SubReportGrid);
/*    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        SubReportGridClick(0);
    }*/
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
    try
    {
        //initForm();
        
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
	    fm.RptDate.value = "";
 	    fm.MngCom.value = "";
 	    fm.Operator.value = "";
	    fm.CaseState.value = "";

/*	    fm.customerNo.value = "";
	    fm.customerAge.value = "";
 	    fm.customerSex.value = "";
 	    fm.SexName.value = "";
	    fm.IsVip.value = "";
	    fm.AccidentDate.value = "";
 	    fm.occurReason.value = "";
 	    fm.ReasonName.value = "";
	    fm.hospital.value = "";
	    fm.TreatAreaName.value = "";
	    fm.AccidentDate2.value = "";
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
        }*/
    }
    catch(re)
    {
        alert("��LLReport.js-->resetForm�����з����쳣:��ʼ���������!");
    }
}


//������ѯ
//���ա��ͻ��š���LCpol�н��в�ѯ����ʾ�ÿͻ��ı�����ϸ
function showInsuredLCPol()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        
        return;
    }
	var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
    var strUrl="LLLcContQueryMain.jsp?AppntNo="+tCustomerNo;
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
	  //***************************************
	  //�жϸ��ⰸ�Ƿ���ڵ���
	  //***************************************
//    var strSQL = "select count(1) from LLInqConclusion where "
//                + "ClmNo = " + fm.RptNo.value;
//    var tCount = easyExecSql(strSQL);
////    alert(tCount);
//    if (tCount == "0")
//    {
//    	  alert("���ⰸ��û�е�����Ϣ��");
//    	  return;
//    }
	  var strUrl="LLInqQueryMain.jsp?claimNo="+fm.RptNo.value;
      window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"�鿴����");
}

//�鿴�ʱ�
function showQuerySubmit()
{
	  //***************************************
	  //�жϸ��ⰸ�Ƿ���ڳʱ�
	  //***************************************
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
//    window.open(strUrl,"�ʱ���ѯ",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    //window.open(strUrl,"�ʱ���ѯ");
    window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

/**
* ��span����ʾ������
* <p><b>Example: </b><p>
* <p>showPage(HTML.ImageObject, HTML.SpanObject.ID)<p>
* @param img ��ʾͼƬ��HTML����
* @param spanID HTML��SPAN�����ID
* @return ���ҳ��SPAN�ɼ����������أ�����ʾ��ʾ�رյ�ͼƬ����֮
*/
function showPage2(img,spanID)
{
  if(spanID.style.display=="")
  {
    //�ر�
    //spanID.style.display="none";
    //img.src="../common/images/butCollapse.gif";

	//��
    spanID.style.display="";
    img.src="../common/images/butExpand.gif";
  }
  else
  {
    //��
    spanID.style.display="";
    img.src="../common/images/butExpand.gif";
  }
}

//SelfLLReportGrid����¼�
function LLLClaimQueryGridClick()
{
    var i = LLLcContSuspendGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        tClmNo = LLLcContSuspendGrid.getRowColData(i,1);
        location.href = "LLClaimQueryInput.jsp?claimNo=" + tClmNo + "&phase=" + fm.AppntNo.value;//�洢�����˺����������
    }

//-------------------------ԭҳ����ʾ��Ϣ----------------------------------------------------------
//    showPage2(this,divLLReport1);
//    showPage2(this,divLLReport2);
//
//	resetForm();
//
//    try
//    {
//    	//initParam();
//		var appntNo = tClmNo;
//        initSubReportGrid();
//        initQuery2(appntNo);
//    }
//    catch(re)
//    {
//        alert("LLLClaimQueryInit.jsp-->LLLClaimQueryGridClick�����з����쳣:��ʼ���������!");
//    }
//-------------------------ԭҳ����ʾ��Ϣ----------------------------------------------------------

}