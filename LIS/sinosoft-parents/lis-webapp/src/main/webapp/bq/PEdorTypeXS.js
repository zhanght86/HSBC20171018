//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var selno;
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var polflag = "0";
var PolNo = "";
var sqlresourcename = "bq.PEdorTypeXSInputSql";

function queryLRInfo()
{
//		var tSQL = "select c.losttimes from lccont c where c.contno ='"+fm.ContNo.value+"'";
		
	var tSQL = "";
	var sqlid1="PEdorTypeXSInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	tSQL=mySql1.getString();
		
		var ret = easyExecSql(tSQL);
		if(ret)
		{
			var tLostTimes = ret[0][0];
			if(tLostTimes > 0)
			{
				fm.TrueLostTimes.value = tLostTimes;
				divLRInfo.style.display="";
			}
			else
			{
				fm.TrueLostTimes.value = 0;
			}
		}
}

function isExemptRisk(tRiskCode) {
	//1ΪͶ���˻��� 2Ϊ�����˻���
//	var SQL = "select 1 from lmriskapp where riskcode = '"+tRiskCode+"' and risktype7 in ('1','2')";
	
	var SQL = "";
	var sqlid2="PEdorTypeXSInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(tRiskCode);//ָ������Ĳ���
	SQL=mySql2.getString();
	
	var strResult = easyExecSql(SQL);
        if(strResult != null) {
        	return true;
        }
        else {
        	return false;
        }
}

function reduceOneRisk()
{
    polflag = "1";
    selno = PolGrid.getSelNo()-1;
    if (selno <0)
    {
        alert("��ѡ��Ҫ���������֣�");
        return;
    }
    
    if (fm.SurrReasonCode.value == null || fm.SurrReasonCode.value == "")
    {
        alert("��¼���˱�ԭ��!");
        return;
    }
    else {
    	//add by jiaqiangli 2009-05-23
    	if (fm.SurrReasonCode.value == "05" && (fm.ReasonContent.value == null || fm.ReasonContent.value == "")) {
    		alert("���ڱ�ע��¼�������ϸ�������˱�ԭ��!");
        	return;
    	}
    	//add by jiaqiangli 2009-05-23
    }
    
    //add by jiaqiangli 2008-11-20 ���������Զ�����
    var sRiskCode = PolGrid.getRowColData(selno, 1);
    if (isExemptRisk(sRiskCode) == true) {
    	alert("���������Զ�������");
    	return;
    }

    var mtempt = PolGrid.getRowColData(selno, 4);
    PolNo = PolGrid.getRowColData(selno, 10);

    var strRN = "select * from ljapayperson where polno = '"+PolNo+"'";
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    document.all('fmtransact').value = "INSERT||MAIN";
    fm.action = "PEdorTypeXSSubmit.jsp";
    fm.submit();
}

function saveEdorTypePT()
{
    if (!isReducedPol())      return;
    //if (!checkIsReissue())    return;
    
    if(fm.TrueLostTimes.value > 0 )
    	{
    		if(fm.LostTimes.value == null || fm.LostTimes.value == "")
    		{
    			alert("�ñ����в�����¼����¼�벹����¼��");
    			fm.LostTimes.focus();
    			return;
    		}
    		
    		if(fm.LostTimes.value != fm.TrueLostTimes.value)
    		{
    			alert("����Ĳ�����������ȷ���ʵ��");
    			return;
    		}
    	}
    
    //Э�����У�� add by jiaqiangli 2008-09-17
    var rowNum=CTFeePolGrid.mulLineCount;
    if(rowNum > 0)
    {
    	var i;
    	for(i = 0 ; i <rowNum ; i++)
    	{
    		var inputNum = CTFeePolGrid.getRowColData(i,5);
    		var sourceNum = CTFeePolGrid.getRowColData(i,4);
    		//alert(inputNum);
    		if(!isNumeric(inputNum))
    		{
    			alert("���������¼��Ϸ����֣�")
    			return;
    		}
    		//comment by jiaqiangli 2008-09-16 ��������п��ܴ���Ӧ�˽���Ȩ�޿���
//    		if (inputNum > sourceNum) {
//	    		if (!confirm("����������Ӧ�˽��Ƿ�����˱���"))
//	        	{
//	            		return;
//	        	}
//        	}
    	}
    }
    //add by jiaqiangli 2008-09-17
    
    
    //add by jiaqiangli 2009-05-16
    //����Э�����ʱ������ʾ
/*    var tZDFlagSQL = "select case when (select count(1) from lcpol where contno='"+fm.ContNo.value+"' and appflag='1')"
    			   + "=(select count(1) from lppol where contno='"+fm.ContNo.value+"' and edortype='XS' and edorno='"+document.all('EdorNo').value+"') then 0 else 1 end from dual";
*/	
	var tZDFlagSQL = "";
	var sqlid3="PEdorTypeXSInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	mySql3.addSubPara(fm.ContNo.value);
	mySql3.addSubPara(document.all('EdorNo').value);
	tZDFlagSQL=mySql3.getString();
	
	var tZDFlag = easyExecSql(tZDFlagSQL, 1, 0,1);
	if (tZDFlag != null && tZDFlag != "" && tZDFlag== "0") {
//			var tYJFlagSQL="select case when (select nvl(sum(leavingmoney),0) from lcpol where contno='"+fm.ContNo.value+"' and appflag='1')>0 then 0 else 1 end from dual";
			
	var tYJFlagSQL = "";
	var sqlid4="PEdorTypeXSInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	tYJFlagSQL=mySql4.getString();
			
			var tYJFlag=easyExecSql(tYJFlagSQL, 1, 0,1);
			if (tYJFlag != null && tYJFlag != "" && tYJFlag== "0") {
				alert("��ʾ���ñ����´����罻���ѣ�");
			}
//			var tHLFlagSQL="select case when (select nvl(sum(money),0) from lcinsureacctrace where contno='"+fm.ContNo.value+"' and insuaccno in ('000001','000007','000008'))>0 then 0 else 1 end from dual";
			
	var tHLFlagSQL = "";
	var sqlid5="PEdorTypeXSInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	tHLFlagSQL=mySql5.getString();
			
			var tHLFlag=easyExecSql(tHLFlagSQL, 1, 0,1);
			if (tHLFlag != null && tHLFlag != "" && tHLFlag== "0") {
				alert("��ʾ���ñ����´���Ӧ�������");
			}
//			var tYFFlagSQL="select case when (select nvl(sum(INSUACCBALA),0) from lcinsureacc where contno='"+fm.ContNo.value+"' and insuaccno in ('000005','000009'))>0 then 0 else 1 end from dual";
			
	var tYFFlagSQL = "";
	var sqlid6="PEdorTypeXSInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
	mySql6.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	tYFFlagSQL=mySql6.getString();
			
			var tYFFlag=easyExecSql(tYFFlagSQL, 1, 0,1);
			if (tYFFlag != null && tYFFlag != "" && tYFFlag== "0") {
				alert("��ʾ���ñ����´���Ӧ�������");
			}  
//			var tLoanFlagSQL="select case when (select nvl(sum(leavemoney),0) from loloan where contno='"+fm.ContNo.value+"' and loantype='0' and payoffflag='0')>0 then 0 else 1 end from dual";
			
	var tLoanFlagSQL = "";
	var sqlid7="PEdorTypeXSInputSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
	mySql7.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	tLoanFlagSQL=mySql7.getString();
			
			var tLoanFlag=easyExecSql(tLoanFlagSQL, 1, 0,1);
			if (tLoanFlag != null && tLoanFlag != "" && tLoanFlag== "0") {
				alert("��ʾ���ñ����´��ڱ�����");
			}
	}
    //add by jiaqiangli 2009-05-16
    
    polflag = "0";
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.all('fmtransact').value = "EdorSave";
    fm.action = "./PEdorTypeXSSubmit.jsp";
    fm.submit();
}

function queryCustomerInfo()
{
    var tContNo=top.opener.document.all('ContNo').value;
    var strSQL="";
    if(tContNo!=null || tContNo !='')
    {
/*        strSQL = " Select a.appntno,'Ͷ����',a.appntname,a.appntsex||'-'||sex.codename,a.appntbirthday,a.idtype||'-'||x.codename,a.idno From lcappnt a "
                                        +" Left Join (Select code,codename From ldcode Where codetype='idtype') x On x.code = a.idtype "
                                        +" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = a.appntsex  Where contno='"+tContNo+"'"
                                        +" Union"
                                        +" Select i.insuredno,'������',i.name,i.Sex||'-'||sex.codename,i.Birthday,i.IDType||'-'||xm.codename,i.IDNo From lcinsured i "
                                        +" Left Join (Select code,codename From ldcode Where codetype='idtype') xm On xm.code = i.idtype "
                                        +" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = i.sex Where contno='"+tContNo+"'";
*/    
	var sqlid8="PEdorTypeXSInputSql8";
	var mySql8=new SqlClass();
	mySql8.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
	mySql8.addSubPara(tContNo);//ָ������Ĳ���
	mySql8.addSubPara(tContNo);
	strSQL=mySql8.getString();
    
    }
    else
    {
        alert('û����Ӧ��Ͷ���˻򱻱�����Ϣ��');
    }
    var arrSelected = new Array();
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult) {
        //alert("û����Ӧ��Ͷ���˻򱻱�����Ϣ��");
        return false;
    }
    //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
    turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
    //��ѯ�ɹ������ַ��������ض�ά����
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
    turnPage.pageDisplayGrid = CustomerGrid;
    //����SQL���
    turnPage.strQuerySql = strSQL;
    //���ò�ѯ��ʼλ��
    turnPage.pageIndex = 0;
    //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //����MULTILINE������ʾ��ѯ���
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content,Result)
{
    showInfo.close();
    //alert(document.all('Flag266').value);
    if(document.all('Flag266').value != "NO")
    {
        show266();
    }
    document.all('Flag266').value = "NO";
    document.all('Flag267').value = "NO";
    var urlStr;
    if (FlagStr == "Success")
    {
        urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    else if (FlagStr == "Fail")
    {
        urlStr="../common/jsp/MessagePage.jsp?picture=F&content=" + content;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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
        urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=300;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    //alert(selno);

        if (FlagStr == "Success")
    {
        var iArray;
        //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
        turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
        //�����ѯ����ַ���
        turnPage.strQueryResult  = Result;

        //��ѯ�ɹ������ַ��������ض�ά����
        var tArr   = decodeEasyQueryResult(turnPage.strQueryResult,0);
        //alert(Result);
        //polflag = "1";
        
        getPolInfo();
        queryBackFee();
        top.opener.getEdorItemGrid();
	
	    //add by jiaqiangli 2008-09-16
	    queryShouldMoney();
        queryAdjustMoney();
        getCTFeePolGrid();
        //alert("getCTFeeDetailGrid"+getCTFeeDetailGrid);
        getCTFeeDetailGrid();
    }
}

/**
 * ����������
 */
function returnParent()
{
    try
    {
        top.close();
        top.opener.focus();
    }
    catch (ex) {}
}


function showNotePad()
{
    var MissionID = top.opener.document.all("MissionID").value;
    var SubMissionID = top.opener.document.all("SubMissionID").value;;
    var ActivityID = "0000000003";
    var OtherNo = top.opener.document.all("OtherNo").value;;

    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionIDΪ�գ�");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");
}

function getPolInfo()
{
    var tContNo = document.all("ContNo").value;
    var tConfDate = document.all("EdorItemAppDate").value;
    var tEdorNo = document.all("EdorNo").value;//Add By QianLy on 2006-9-23
    if(tContNo!=null&&tContNo!="")
    {
        //var strSQL = "Select distinct m.riskcode,m.riskname,c.INSUREDNAME,case when u.amntflag = 1 then p.amnt else p.mult end, p.prem,'','','',c.Insuredno,p.polno,u.amntflag From lcpol p Left Join lmrisk m on m.riskcode = p.riskcode Left Join LCCONT c On c.contno= p.contno Left Join LCDuty d on d.polno = p.polno Left Join LMDuty u on trim(u.dutycode) = substr(trim(d.dutycode),1,6) where ((p.appflag = '1') or (p.appflag = '9' and p.cvalidate + 60 >= '" + tConfDate + "')) and p.cvalidate <= '" + tConfDate + "' and enddate > '" + tConfDate + "' and c.contno = '"+tContNo+"' and polno not in (select polno from lccontstate where statetype = 'Terminate' and state = '1' and enddate is null and contno = c.contno)";
        //Modified By QianLy on 2006-9-23------------------
/*        var strSQL = "Select distinct m.riskcode,"
                   + " m.riskname,"
                   + " c.INSUREDNAME,"
                   + " case when u.amntflag = 1 then a.amnt else a.mult end,"
                   + " a.prem,"
                   + " case"
                   + "   when u.amntflag = 1 then"
                   + "    nvl((select amnt"
                   + "     from lppol"
                   + "    where 1 = 1"
                   + "      and polno = a.polno"
                   + "      and edorno = '"+tEdorNo+"'),a.amnt)"
                   + "   else"
                   + "      nvl((select mult"
                   + "        from lppol"
                   + "        where 1 = 1"
                   + "         and polno = a.polno"
                   + "         and edorno = '"+tEdorNo+"'),a.mult)"
                   + " end,"
                   + " '',"
                   + " nvl((select prem"
                   + "        from lppol"
                   + "       where polno = a.polno"
                   + "         and edorno ='"+tEdorNo+"'),"
                   + " a.prem),"
                   + " c.Insuredno,"
                   + " a.polno,"
                   + " u.amntflag,"
                   //add by jiaqiangli 2008-11-20 PTflag ���ٱ�־ PTFlag 0���ٱ���1���ٷ���2���ٱ���
                   + " (case when u.calmode='P' then '0' when u.calmode='O' then '1' else '2' end)"
                   + " From lcpol a"
                   + " Left Join lmrisk m on m.riskcode = a.riskcode"
                   + " Left Join LCCONT c On c.contno = a.contno"
                   + " Left Join LCDuty d on d.polno = a.polno"
                   + " Left Join LMDuty u on trim(u.dutycode) = substr(trim(d.dutycode), 1, 6)"
                   + " where 1 = 1"
                   + " and a.appflag = '1' "
                   //+ " and ((a.appflag = '1') or"
                   //+ " (a.appflag = '9' and a.cvalidate + 60 >= '" + tConfDate + "'))"
                   + " and a.cvalidate <= '" + tConfDate + "'"
                   + " and enddate > '" + tConfDate + "'"
                   + " and a.contno = '"+tContNo+"'"
                   + " and d.contno = '"+tContNo+"'"
                   + " and c.contno = '"+tContNo+"'"
                   + " and polno not in (select polno"
                   + "                     from lccontstate"
                   + "                    where statetype = 'Terminate'"
                   + "                      and state = '1'"
                   + "                      and enddate is null"
                   + "                      and contno = c.contno)"
                   + " order by a.PolNo asc";
                   //prompt("strSQL",strSQL);
         //----------------------
*/        
    var strSQL = "";
    var sqlid9="PEdorTypeXSInputSql9";
	var mySql9=new SqlClass();
	mySql9.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
	mySql9.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql9.addSubPara(tEdorNo);
	mySql9.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql9.addSubPara(tConfDate);
	mySql9.addSubPara(tConfDate);//ָ������Ĳ���
	mySql9.addSubPara(tContNo);
	mySql9.addSubPara(tContNo);//ָ������Ĳ���
	mySql9.addSubPara(tContNo);
	strSQL=mySql9.getString();
        
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
        //�ж��Ƿ��ѯ�ɹ�
        if (!turnPage.strQueryResult)
        {
            alert("û��Ͷ��������Ϣ��");
            return false;
        }
        //��ѯ�ɹ������ַ��������ض�ά����
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //���ó�ʼ������MULTILINE����
        turnPage.pageDisplayGrid = PolGrid;
        //����SQL���
        turnPage.strQuerySql = strSQL;
        //���ò�ѯ��ʼλ��
        turnPage.pageIndex = 0;
        //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //����MULTILINE������ʾ��ѯ���
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }
}

function QueryRiskDetail()
{
    var tContNo=top.opener.document.all('ContNo').value;
    var strSQL=""
    if(tContNo!=null || tContNo !='')
    {
/*        strSQL = "select distinct riskcode, (select riskname from lmrisk where lmrisk.riskcode=lcpol.riskcode),paytodate,mult,Amnt,sumprem,paymode from lcpol WHERE 1=1 AND "
                            +"CONTNO='"+tContNo+"'";
*/    
    var sqlid10="PEdorTypeXSInputSql10";
	var mySql10=new SqlClass();
	mySql10.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
	mySql10.addSubPara(tContNo);//ָ������Ĳ���
	strSQL=mySql10.getString();
    
    }
    else
    {
        alert('û�пͻ���Ϣ��');
    }
    var arrSelected = new Array();
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    //arrResult = easyExecSql("select * from LDGrp where GrpNo = '" + arrQueryResult[0][0] + "'", 1, 0);
    if(!turnPage.strQueryResult){
        alert("��ѯʧ��");
    }
    else
    {
        arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
        try {document.all('riskcode').value = arrSelected[0][0];} catch(ex) { }; //Ͷ��������
        try {document.all('riskname').value = arrSelected[0][1];} catch(ex) { }; //Ͷ����֤������
        try {document.all('paytodate').value = arrSelected[0][2];} catch(ex) { }; //Ͷ����֤������
        try {document.all('mult').value = arrSelected[0][3];} catch(ex) { }; //����������
        try {document.all('Amnt').value = arrSelected[0][4];} catch(ex) { }; //������֤������
        try {document.all('sumprem').value = arrSelected[0][5];} catch(ex) { }; //������֤������
        try {document.all('paymode').value = arrSelected[0][6];} catch(ex) {};

        //showOneCodeName('idtype','AppntIDType','AppntIDTypeName');
        //showOneCodeName('idtype','InsuredIDType','InsuredIDTypeName');
    }
}

function Edorquery()
{
    try
    {
        var ButtonFlag = top.opener.document.all('ButtonFlag').value;

        if(ButtonFlag!=null && ButtonFlag=="1")
        {
           divEdorquery.style.display = "none";
           divPTbuttion.style.display = "none";
        }
        else
        {
           divPTReturn.style.display = "none";
           divEdorquery.style.display = "";
        }
    }
    catch(e){}
}

//<!-- XinYQ added on 2006-07-24 : BGN -->
/*============================================================================*/

/**
 * ��ȫ����ʱ��ʾ����ԭ��ͼ�������
 */
function queryEdorInfo()
{
    try
    {
        queryEdorReasonAndAppnt();
        calcMinusNumber();
    }
    catch (ex) {}
}

function queryEdorReasonAndAppnt(){
    //����ԭ��
    var QuerySQL, arrResult;
/*    QuerySQL = "select a.EdorReasonCode, "
             +        "(select CodeName "
             +           "from LDCode "
             +          "where 1 = 1 "
             +            "and CodeType = 'xsurrordereason' "
             +            "and Code = a.EdorReasonCode) "
             + ",a.standbyflag2,(select codename from ldcode where codetype='relationtoappnt' and code=a.standbyflag2),EdorReason "
             +   "from LPEdorItem a "
             +  "where 1 = 1 "
             + getWherePart("a.EdorAcceptNo", "EdorAcceptNo")
             + getWherePart("a.EdorNo", "EdorNo")
             + getWherePart("a.EdorType", "EdorType");
*/             
    var sqlid11="PEdorTypeXSInputSql11";
	var mySql11=new SqlClass();
	mySql11.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
	mySql11.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
	mySql11.addSubPara(fm.EdorNo.value);
	mySql11.addSubPara(fm.EdorType.value);
	QuerySQL=mySql11.getString();
             
    try{
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }catch (ex){
        alert("���棺��ѯ��ȫ����ԭ������쳣�� ");
        return;
    }
    if (arrResult != null){
        try{
            document.getElementsByName("SurrReasonCode")[0].value = arrResult[0][0];
            document.getElementsByName("SurrReasonName")[0].value = arrResult[0][1];
            
            document.getElementsByName("RelationToAppnt")[0].value = arrResult[0][2];
            document.getElementsByName("RelationToAppntName")[0].value = arrResult[0][3];
            
            document.getElementsByName("ReasonContent")[0].value = arrResult[0][4];
        }catch (ex) {}
    }
}

/**
 * ��ȫ����ʱ��ʾ��������
 */
function calcMinusNumber()
{
    var nSelNo;
    try
    {
        nSelNo = PolGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        return;
    }
    else
    {
        var sOldMinusNumber, sNewMinusNumber;
        
        var sPTFlag = PolGrid.getRowColData(nSelNo, 12);
        
        try
        {
            sOldMinusNumber = PolGrid.getRowColData(nSelNo, 4);
            sNewMinusNumber = PolGrid.getRowColData(nSelNo, 6);
            
            //������ �������
            if (sPTFlag != null && sPTFlag == '0') {
            	sOldMinusNumber = PolGrid.getRowColData(nSelNo, 5);
                sNewMinusNumber = PolGrid.getRowColData(nSelNo, 8);
            }
        }
        catch (ex) {}
        if (sOldMinusNumber == null || trim(sOldMinusNumber) == "" || sNewMinusNumber == null || trim(sNewMinusNumber) == "")
        {
            return;
        }
        else
        {
            var fOldMinusNumber, fNewMinusNumber, fFinalMinusNumber;
            try
            {
                fOldMinusNumber = parseFloat(sOldMinusNumber);
                fNewMinusNumber = parseFloat(sNewMinusNumber);
                fFinalMinusNumber = eval((fOldMinusNumber - fNewMinusNumber).toFixed(2));
                
                //���ַ�֧��� PTFlag 0���ٱ���1���ٷ���2���ٱ���
                if (sPTFlag != null && sPTFlag == '0') {
                	document.getElementsByName("MinusPTPrem")[0].value = fFinalMinusNumber;
                	PTPremDiv.style.display = "";
                	PTAmntDiv.style.display = "none";
                	PTMutDiv.style.display = "none";
                }
                else if (sPTFlag != null && sPTFlag == '1') {
                	document.getElementsByName("MinusPTMut")[0].value = fFinalMinusNumber;
                	PTPremDiv.style.display = "none";
                	PTAmntDiv.style.display = "none";
                	PTMutDiv.style.display = "";
                }
                else if (sPTFlag != null && sPTFlag == '2') {
                	document.getElementsByName("MinusPTAmnt")[0].value = fFinalMinusNumber;
                	PTPremDiv.style.display = "none";
                	PTAmntDiv.style.display = "";
                	PTMutDiv.style.display = "none";
                }
            }
            catch (ex) {}
        }
    } //nSelNo != null
}

/**
 * ��"����"֮�������"����"
 */
function isReducedPol()
{
    var QuerySQL, arrResult;
/*    QuerySQL = "select 'X' "
             +   "from LPPol "
             +  "where 1 = 1 "
             +     getWherePart("EdorNo", "EdorNo")
             +     getWherePart("EdorType", "EdorType")
             +     getWherePart("ContNo", "ContNo");
    //alert(QuerySQL);
*/    
    var sqlid12="PEdorTypeXSInputSql12";
	var mySql12=new SqlClass();
	mySql12.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
	mySql12.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
	mySql12.addSubPara(fm.EdorType.value);
	mySql12.addSubPara(fm.ContNo.value);
	QuerySQL=mySql12.getString();
    
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("���棺��鱣���Ƿ��Ѿ����������쳣�� ");
        return;
    }
    if (arrResult == null)
    {
        alert("�����ȶ�ĳ���ּ������������������ ")
        return false;
    }
    return true;
}

/**
 * ����Ƿ��ǲ�����ӡ����
 */
function checkIsReissue()
{
    var QuerySQL, arrResult;
    //��ѯ�Ƿ�������������
/*    QuerySQL = "select 'X' "
             +   "from LPEdorItem "
             +  "where 1 = 1 "
             +  getWherePart("ContNo", "ContNo")
             +    "and EdorType = 'LR' "
             +    "and EdorState = '0'";
    //alert(QuerySQL);
*/    
    var sqlid13="PEdorTypeXSInputSql13";
	var mySql13=new SqlClass();
	mySql13.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql13.setSqlId(sqlid13);//ָ��ʹ�õ�Sql��id
	mySql13.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	QuerySQL=mySql13.getString();
    
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("���棺��ѯ�Ƿ������������������쳣�� ");
        return true;
    }
    if (arrResult != null)
    {
        var sConfirmMsg = "�ñ�������������";
        //��ѯ����������ӡ����
/*        QuerySQL = "select distinct max(MakeDate) "
                 +   "from LDContInvoiceMap "
                 +  "where 1 = 1 "
                 +  getWherePart("ContNo", "ContNo")
                 +    "and OperType = '4'";
        //alert(QuerySQL);
*/        
    var sqlid14="PEdorTypeXSInputSql14";
	var mySql14=new SqlClass();
	mySql14.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql14.setSqlId(sqlid14);//ָ��ʹ�õ�Sql��id
	mySql14.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	QuerySQL=mySql14.getString();
        
        try
        {
            arrResult = easyExecSql(QuerySQL, 1, 0);
        }
        catch (ex)
        {
            alert("���棺��ѯ���������³����Ĵ�ӡ���ڳ����쳣�� ");
            return true;
        }
        if (arrResult != null && trim(arrResult[0][0]) != "")
        {
            sConfirmMsg += "���һ�δ�ӡ����Ϊ " + trim(arrResult[0][0]) + "��";
        }
        sConfirmMsg += "�Ƿ���������� ";
        //ȷ����ʾ����
        if (!confirm(sConfirmMsg))
        {
            return false;
        }
    }
    return true;
}

function getCTFeePolGrid()
{
	    var tEdorAcceptNo=fm.EdorAcceptNo.value;
/*      var QuerySQL = "  select a.polno , "
                 +                "(select riskcode from lcpol where polno = a.polno) , "
                 +                "(select RiskName "
                 +                   "from LMRisk "
                 +                  "where RiskCode = "
                 +                        "(select riskcode from lcpol where polno = a.polno)), "
                 //ljsgetendorse.getmoneyΪ������ʵ�˽��
                 +                "a.GetMoney,"
                 //lpedoritem.getmoneyΪ������ʵ�˽��
                 +                "a.GetMoney "
                 //+                "(select nvl(GetMoney,0) from LPEdorItem where EdorAcceptNo='"+tEdorAcceptNo+"' and EdorType='XS')"
                 +           " from ljsgetendorse a  "
                 +          "  where 1 = 1  and subfeeoperationtype in ('G006','G001') "
                 +             getWherePart("a.EndorsementNo", "EdorNo")
                 +  getWherePart("a.ContNo", "ContNo")
                 //add by jiaqiangli ������������
                 +           "order by a.polno, a.feefinatype, a.subfeeoperationtype ";
*/     
    var QuerySQL = "";
    var sqlid15="PEdorTypeXSInputSql15";
	var mySql15=new SqlClass();
	mySql15.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql15.setSqlId(sqlid15);//ָ��ʹ�õ�Sql��id
	mySql15.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
	mySql15.addSubPara(fm.ContNo.value);
	QuerySQL=mySql15.getString();
     
     var arrResult1 = easyExecSql(QuerySQL,1,0,1);
     if (arrResult1)
    {
        displayMultiline(arrResult1, CTFeePolGrid);
        var rowNum=CTFeePolGrid.mulLineCount; 
        if(rowNum > 0)
        {
        	//fm.HasCal.value = 'Y';
        	divCTFeePolDetailButton.style.display="";
        	divCTFeePolDetail.style.display="";
        }
    }
}

/*============================================================================*/

/**
 * XinYQ rewrote on 2007-06-05
 */
function getCTFeeDetailGrid()
{
/*    var QuerySQL = "              select polno,  "
                 +                "(select riskcode from lcpol where polno = a.polno) , "
                 +                "(select RiskName "
                 +                   "from LMRisk "
                 +                  "where RiskCode = "
                 +                        "(select riskcode from lcpol where polno = a.polno)) , "
                 +                "(select codename "
                 +                   "from ldcode "
                 +                  "where 1 = 1 "
                 +                    "and codetype = 'finfeetype' "
                 +                    "and code = a.feefinatype) , "
                 +                "(select codename "
                 +                   "from ldcode "
                 +                  "where 1 = 1 "
                 +                    "and codetype = 'BQSubFeeType' "
                 +                    "and code = a.subfeeoperationtype) , "
                 //ljsgetendorse.getmoneyΪ������ʵ�˽��
                 +                "a.GetMoney , "                
                 +                "a.feefinatype , "
                 +                "a.subfeeoperationtype "
                 +           " from ljsgetendorse a "
                 +          " where 1 = 1 "
                 +             getWherePart("a.EndorsementNo", "EdorNo")
                 +  getWherePart("a.ContNo", "ContNo")
                 +          " order by a.polno, a.feefinatype, a.subfeeoperationtype ";
*/    
    var QuerySQL = "";
    var sqlid16="PEdorTypeXSInputSql16";
	var mySql16=new SqlClass();
	mySql16.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql16.setSqlId(sqlid16);//ָ��ʹ�õ�Sql��id
	mySql16.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
	mySql16.addSubPara(fm.ContNo.value);
	QuerySQL=mySql16.getString();
    
    var arrResult = easyExecSql(QuerySQL);
    if (arrResult)
    {
        displayMultiline(arrResult, CTFeeDetailGrid);
        var rowNum=CTFeeDetailGrid.mulLineCount;
        if(rowNum > 0)
        {
        	//fm.HasCal.value = 'Y';
        	divCTFeeDetailButton.style.display="";
        	divCTFeeDetail.style.display="";
        }
    }
}

/**
 * ��ѯ�Ѿ��������Ӧ�˽��
 */
function queryShouldMoney()
{
//	alert("come to queryShouldMoney");
//	      //Ӧ�˽��
//	      var tFLagSQL = "  select a.getFlag,a.GetMoney "
//                 +           " from ljsgetendorse a  "
//                 +          "  where 1 = 1 and OtherNo ='000000'"
//                 +             getWherePart("a.EndorsementNo", "EdorNo");                 
//        var arrResult = easyExecSql(tFLagSQL,1,0,1);
//        var tPreMoney=0; 
//        prompt("tFLagSQL",tFLagSQL);      
//        if(arrResult)
//        {
//        for(var i=0;i<arrResult.length;i++)
//        {
//        	    var tTempMoney=0;
//        	    if(arrResult[i][0]=='0'){  	 //Pay ����         
//        	     tTempMoney=-parseFloat(arrResult[i][1]);
//        	    }
//        	    if(arrResult[i][0]=='1')
//        	    {
//        	    	tTempMoney=parseFloat(arrResult[i][1]);
//        	    }
//            tPreMoney+=tTempMoney;
//            alert("tPreMoney"+tPreMoney);
//        }
//        fm.GetMoney.value=mathRound(tPreMoney);  
//        }else
        	{
      var tEdorAcceptNo=fm.EdorAcceptNo.value; 
      //comment by jiaqiangli 2008-09-16 �����ֵ=ʵ��-Ӧ�� ���� Ӧ�� = ʵ�� - ��ֵ
//      var tSQL="select nvl(StandbyFlag3,0) from LPEdorItem where EdorAcceptNo='"+tEdorAcceptNo+"' and EdorType='XS'";
      
    var tSQL = "";
    var sqlid17="PEdorTypeXSInputSql17";
	var mySql17=new SqlClass();
	mySql17.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql17.setSqlId(sqlid17);//ָ��ʹ�õ�Sql��id
	mySql17.addSubPara(tEdorAcceptNo);//ָ������Ĳ���
	tSQL=mySql17.getString();
      
      var arrResult3 = easyExecSql(tSQL,1,0,1);
      fm.GetMoney.value=mathRound(arrResult3);      
      }
}

/*============================================================================*/

/**
 * ��ѯ�Ѿ���������˷ѽ��
 */
function queryAdjustMoney()
{
	  var tEdorAcceptNo=fm.EdorAcceptNo.value;
	  //ʵ�˺ϼ�ֵ 
 //     var tSql="select nvl(GetMoney,0) from LPEdorItem where EdorAcceptNo='"+tEdorAcceptNo+"' and EdorType='XS'";
      
    var tSql = "";
    var sqlid18="PEdorTypeXSInputSql18";
	var mySql18=new SqlClass();
	mySql18.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql18.setSqlId(sqlid18);//ָ��ʹ�õ�Sql��id
	mySql18.addSubPara(tEdorAcceptNo);//ָ������Ĳ���
	tSql=mySql18.getString();
      
      var arrResult4 = easyExecSql(tSql,1,0,1);
      if(arrResult4)
      {
      fm.AdjustMoney.value=arrResult4; 
    }
}