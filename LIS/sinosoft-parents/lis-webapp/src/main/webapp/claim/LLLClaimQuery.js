var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//��ʼ����ѯ
function initQuery()
{
	var strSQL = "";
	var tClmno = fm.ClmNo.value;
    if (tClmno=="")
    {
        tClmno = "000000";
    }
	/*strSQL = "select k1,k2,k3,k4,k5,k6,k7,k8 from"
		   + "(select a.rgtno k1 ,(case a.clmstate when '10' then '����' when '20' then '����' when '30' then '���' when '40' then '����' when '50' then '�᰸' when '60' then '���' when '70' then '�ر�' end) k2,"
		   + " b.customerno k3,(select c.name from ldperson c where c.customerno = b.customerno) k4,(case b.customersex when '0' then '��' when '1' then 'Ů' when '2' then '����' end) k5,b.medaccdate k6,b.AccDate k7,c.caserelano k8"
           + " from LLRegister a,llcase b,LLCaseRela c"
           + " where a.rgtno = b.caseno and b.caseno=c.caseno"
           + " and a.RgtNo in (select t.CaseNo from LLCase t where t.CustomerNo = '"+ document.all('AppntNo').value +"' and t.CaseNo != '" + tClmno + "')"
           + " union " 
           + " select a.rptno k1,'����'k2,b.customerno k3,c.name k4,(case c.sex when '0' then '��' when '1' then 'Ů' when '2' then '����' end) k5,b.medaccdate k6,b.AccDate k7,d.caserelano  k8"
           + " from (select * from llreport where rptno not in (select rgtno from llregister)) a,llsubreport b,ldperson c,LLCaseRela d "
           + " where a.rptno = b.subrptno and b.subrptno=d.caseno and b.customerno = c.customerno and a.rgtflag = '10' "
           + " and a.rptno in (select t.subrptno from llsubreport t where t.CustomerNo = '"+ document.all('AppntNo').value +"' and t.subrptno != '" + tClmno + "')"
           + " )";*/
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLLClaimQueryInputSql");
		mySql.setSqlId("LLLClaimQuerySql1");
		mySql.addSubPara(document.all('AppntNo').value );  
		mySql.addSubPara(tClmno );       
		mySql.addSubPara(document.all('AppntNo').value );       
		mySql.addSubPara(tClmno);                 
//	  turnPage.pageLineNum=5;    //ÿҳ5��
//    turnPage.queryModal(strSQL, LLLcContSuspendGrid);

	//prompt("�����ⰸ��ѯ",strSQL);
	arrResult = easyExecSql(mySql.getString());
    if (arrResult)
    {
    	displayMultiline(arrResult,LLLcContSuspendGrid);
    }
}

//��ʼ����ѯ�����ϲ���Ϣ
function initQuery2()
{
	var strSQL = "";
	var tClmno = fm.ClmNo.value;
    if (tClmno=="")
    {
        tClmno = "000000";
    }
	/*strSQL = "(select a.rgtno,(case a.clmstate when '10' then '����' when '20' then '����' when '30' then '���' when '40' then '����' when '50' then '�᰸' when '60' then '���' when '70' then '�ر�' end),"
		   + " b.customerno,b.customername,(case b.customersex when '0' then '��' when '1' then 'Ů' when '2' then '����' end),b.medaccdate,b.AccDate,c.caserelano"
           + " from LLRegister a,llcase b,LLCaseRela c"
           + " where a.rgtno = b.caseno and b.caseno=c.caseno"
           + " and b.CustomerNo = '"+ document.all('AppntNo').value +"' and b.CaseNo != '" + tClmno + "'"
           + " and clmstate in('50','60')"
           + ")"
           + " union " 
           + "(select a.rgtno,(case a.clmstate when '10' then '����' when '20' then '����' when '30' then '���' when '40' then '����' when '50' then '�᰸' when '60' then '���' when '70' then '�ر�' end),"
           + " b.customerno,b.customername,(case b.customersex when '0' then '��' when '1' then 'Ů' when '2' then '����' end),b.medaccdate,b.AccDate,c.caserelano "
           + " from LLRegister a,llcase b,LLCaseRela c"
           + " where a.rgtno = b.caseno and b.caseno=c.caseno "
           + " and b.CustomerNo = '"+ document.all('AppntNo').value +"' and b.CaseNo != '" + tClmno + "'"
           + " and exists(select 1 from ljagetclaim where otherno=a.rgtno and feeoperationtype='B')"
           + ")";*/
       		mySql = new SqlClass();
		mySql.setResourceName("claim.LLLClaimQueryInputSql");
		mySql.setSqlId("LLLClaimQuerySql2");
		mySql.addSubPara(document.all('AppntNo').value );  
		mySql.addSubPara(tClmno );       
		mySql.addSubPara(document.all('AppntNo').value );       
		mySql.addSubPara(tClmno);       
//	  turnPage.pageLineNum=5;    //ÿҳ5��
//    turnPage.queryModal(strSQL, LLLcContSuspendGrid);

	//prompt("�����ⰸ��ѯ-�����ϲ���Ϣ",strSQL);
	arrResult = easyExecSql(mySql.getString());
    if (arrResult)
    {
    	displayMultiline(arrResult,LLClaimMergeGrid);
    }
}


//LLLcContSuspendGrid����Ӧ����
function LLLcContSuspendGridClick()
{
	var i = LLLcContSuspendGrid.getSelNo();
    i = i - 1;
    fm.ContNo.value=LLLcContSuspendGrid.getRowColData(i,2);//��ͬ��
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

//���ذ�ť
function goToBack()
{
	//�رյ�ǰҳ��
    top.close();
}

//�ύ����
function submitForm()
{
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
    document.getElementById("fm").submit(); //�ύ
    tSaveFlag ="0";
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr,content,tAccNo)
{
    showInfo.close();

    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    else
    {
        document.all('AccNo').value=tAccNo;
        
        //alert("tAccNo");
        
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        
        if(fm.fmtransact.value=='CancelMerge')
        {
	        operateButton1.style.display="";
	        operateButton2.style.display="none"; 
        }
        else
        {
	        operateButton1.style.display="none";
	        operateButton2.style.display=""; 
        }
        
        initQuery();//ˢ��
        initQuery2();//ˢ��
        

        top.opener.showMatchDutyPay2(tAccNo);//�Զ�ƥ�䲢����

    }


}



//ˢ��
function Refresh()
{
	top.opener.showMatchDutyPay2();//�Զ�ƥ�䲢����
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
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"�ʱ���ѯ");
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

    if (i < 1)
    {
    	alert("��ѡ��һ����¼��");
        return;
    }

    var strUrl = "";
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = LLLcContSuspendGrid.getRowColData(i,1);
        var tPhase = LLLcContSuspendGrid.getRowColData(i,2);
        var tAppntNo = LLLcContSuspendGrid.getRowColData(i,3);

        
        //2008-11-21 ���ڿͻ�����ȡ���������׶ε����㹦��,���Զ����������ֵ���ʾҲ�ͱ�����ʾ��Ԫ��һ��
        if (tPhase == '����'||tPhase == '����')
        {
//          location.href = "LLClaimQueryReportInput.jsp?claimNo="+tClmNo+"&phase=" + fm.AppntNo.value;
            strUrl = "LLClaimQueryReportInputMain.jsp?claimNo="+tClmNo+"&AppntNo="+tAppntNo+"&phase=0";

        }
        else
        {
//          location.href = "LLClaimQueryInput.jsp?claimNo="+tClmNo+"&phase=" + fm.AppntNo.value;
            strUrl = "LLClaimQueryInputMain.jsp?claimNo="+tClmNo+"&AppntNo="+tAppntNo+"&phase=0";

        }
        
        window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
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


/**
 * ���ݴ���ı�־���ƽ�����ʾ����
 *
 */
function viewParm()
{

	//ֻ������˹�������Ҫ���а����ϲ�����Ҫ��ʾ�����ϲ����ǲ�����Ϣ
	if(document.all('ViewFlag').value==null||document.all('ViewFlag').value=="")
	{
		divLLCaseMerge.style.display= "none";
	}
	else
	{
		//��ʾ�����ϲ���Ϣ
		divLLCaseMerge.style.display= "";
		
		//���ж������Ƿ����ִ�а����ϲ�
	    /*var strSql = "  select nvl(count(1),0) from lmrisksort a where"
	    	       + "  exists(select 1 from llclaimpolicy b where b.riskcode=a.riskcode "
                   + "  and b.clmno = '" + document.all('ClmNo').value + "' )"
                   + "  and a.risksorttype = '6'";*/
	    mySql = new SqlClass();
		mySql.setResourceName("claim.LLLClaimQueryInputSql");
		mySql.setSqlId("LLLClaimQuerySql3");
		mySql.addSubPara(document.all('ClmNo').value );  

	    //prompt("�ж������Ƿ����ִ�а����ϲ�",strSql);
	    
		var tFlag = easyExecSql(mySql.getString());
		
		if (tFlag)
		{
			//����0���������ֲ�׼��ִ�а����ϲ�,�򲻱���ʾ�����ϲ����ǲ��ֽ���
		    if (tFlag[0][0] ==0)
		    {
		    	divLLCaseMerge.style.display= "none";
		    }
		    else
		    {
		    	//�ж��Ƿ��Ѿ�ִ�й������ϲ�
			    /*strSql = " select nvl(count(1),0),a.caserelano from llcaserela a,llcaserela b  where a.caserelano=b.caserelano"
		                    + " and a.caseno = '" + document.all('ClmNo').value + "' group by a.caserelano"; */
			mySql = new SqlClass();
			mySql.setResourceName("claim.LLLClaimQueryInputSql");
			mySql.setSqlId("LLLClaimQuerySql4");
			mySql.addSubPara(document.all('ClmNo').value );  
			    //prompt("�ж��Ƿ��Ѿ�ִ�й������ϲ���sql",strSql);
			    
				tFlag = easyExecSql(mySql.getString());
				
				//alert("tFlag[0][0]:"+tFlag[0][0]);
				//alert("tFlag[0][1]:"+tFlag[0][1]);
				
				if (tFlag)
				{
					//С�ڵ���1��ʾû��ִ�й������ϲ�,���ʼ����ʾ�����ϲ���ť
				    if (tFlag[0][0] <=1)
				    {
				        operateButton1.style.display="";
				        operateButton2.style.display="none"; 
				    	fm.AccNo.value=tFlag[0][1];	
				    }
				    else
				    {
				        operateButton1.style.display="none";
				        operateButton2.style.display="";  	
				    }
				}
		    }
		}

	}
	
	MergeClick();
}


/**
 * ���û���������ϲ���Ϣ��multiline��ÿһ�еĵ�ѡ��ťʱ��Ӧ�ú���
 *
 */
function MergeClick()
{
     var i = LLClaimMergeGrid.getSelNo();
	 
	/* var strSql2 = " select a.caserelano from llcaserela a "
            + " where  a.caseno = '" + document.all('ClmNo').value + "'";*/
			mySql = new SqlClass();
			mySql.setResourceName("claim.LLLClaimQueryInputSql");
			mySql.setSqlId("LLLClaimQuerySql4");
			mySql.addSubPara(document.all('ClmNo').value ); 
	 var result=easyExecSql(mySql.getString());
	 
	 //alert("result:"+result);

 	 fm.AccNo.value=result[0][0];	

}

/**
 * 2009-01-06
 * zhangzheng
 * �����ϲ�����
 * @return
 */
function LLClaimMerge()
{
    var i = LLClaimMergeGrid.getSelNo();

    if (i < 1)
    {
    	alert("��ѡ��һ����¼��");
        return;
    }

	fm.fmtransact.value="Merge";
    fm.action = './LLClaimCaseMergeSave.jsp';
	submitForm();
}

/**
 * 2009-01-06
 * zhangzheng
 * ȡ�������ϲ�����
 * @return
 */
function LLClaimCancelMerge()
{
    var i = LLClaimMergeGrid.getSelNo();

    if (i < 1)
    {
    	alert("��ѡ��һ����¼��");
        return;
    }

	fm.fmtransact.value="CancelMerge";   
    fm.action = './LLClaimCaseMergeSave.jsp';
	submitForm();
}