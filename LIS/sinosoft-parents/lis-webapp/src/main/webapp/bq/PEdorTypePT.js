//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var selno;
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var polflag = "0";
var PolNo = "";
var sqlresourcename = "bq.PEdorTypePTInputSql";

function isExemptRisk(tRiskCode) {
	//1ΪͶ���˻��� 2Ϊ�����˻���
//	var SQL = "select 1 from lmriskapp where riskcode = '"+tRiskCode+"' and risktype7 in ('1','2')";
	
	var SQL = "";
	var sqlid1="PEdorTypePTInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tRiskCode);//ָ������Ĳ���
	SQL=mySql1.getString();
	
	var strResult = easyExecSql(SQL);
        if(strResult != null) {
        	return true;
        }
        else {
        	return false;
        }
}

function queryLRInfo()
{
//		var tSQL = "select c.losttimes from lccont c where c.contno ='"+fm.ContNo.value+"'";
		
	var tSQL = "";
	var sqlid2="PEdorTypePTInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	tSQL=mySql2.getString();
		
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

function reduceOneRisk()
{
	//lpedoritem.getmoney�Ļ����Ѿ����ڱ��水ť��
    //if (isSavePol() == true)      return;
    
    polflag = "1";
    selno = PolGrid.getSelNo()-1;
    if (selno <0)
    {
        alert("��ѡ��Ҫ���������֣�");
        return;
    }

    var mtempt = PolGrid.getRowColData(selno, 4);
    PolNo = PolGrid.getRowColData(selno, 10);

    //add by jiaqiangli 2008-11-20 ���������Զ�����
    var sRiskCode = PolGrid.getRowColData(selno, 1);
    if (isExemptRisk(sRiskCode) == true) {
    	alert("���������Զ�������");
    	return;
    }

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
    fm.action = "PEdorTypePTSubmit.jsp";
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
    document.all('fmtransact').value = "OnlyCheck";
    fm.action = "./PEdorTypePTSubmit1.jsp";
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
	var sqlid3="PEdorTypePTInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(tContNo);//ָ������Ĳ���
	mySql3.addSubPara(tContNo);
	strSQL=mySql3.getString();
    
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
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }

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
        queryBackFee();
        getPolInfo();
        top.opener.getEdorItemGrid();
        //divPol5.style.display = "";
        //divPol2.style.display = "";
        //divPol3.style.display = "";
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
    //modify by jiaqiangli 2008-12-08 ȡ�����������ڶ�����Ч����
    var tConfDate = document.all("EdorItemAppDate").value;
    var tEdorNo = document.all("EdorNo").value;//Add By QianLy on 2006-9-23
    if(tContNo!=null&&tContNo!="")
    {
        //var strSQL = "Select distinct m.riskcode,m.riskname,c.INSUREDNAME,case when u.amntflag = 1 then p.amnt else p.mult end, p.prem,'','','',c.Insuredno,p.polno,u.amntflag From lcpol p Left Join lmrisk m on m.riskcode = p.riskcode Left Join LCCONT c On c.contno= p.contno Left Join LCDuty d on d.polno = p.polno Left Join LMDuty u on trim(u.dutycode) = substr(trim(d.dutycode),1,6) where ((p.appflag = '1') or (p.appflag = '9' and p.cvalidate + 60 >= '" + tConfDate + "')) and p.cvalidate <= '" + tConfDate + "' and enddate > '" + tConfDate + "' and c.contno = '"+tContNo+"' and polno not in (select polno from lccontstate where statetype = 'Terminate' and state = '1' and enddate is null and contno = c.contno)";
        //Modified By QianLy on 2006-9-23------------------
/*        var strSQL = "Select distinct m.riskcode,"
                   + " m.riskname,"
                   + " c.INSUREDNAME,"
                   + " case when u.calmode='O' then a.mult else a.amnt end,"
                   + " a.prem,"
                   + " case"
                   + "   when u.calmode='O' then"
                   + "    nvl((select mult"
                   + "     from lppol"
                   + "    where 1 = 1"
                   + "      and polno = a.polno"
                   + "      and edorno = '"+tEdorNo+"'),a.mult)"
                   + "   else"
                   + "      nvl((select amnt"
                   + "        from lppol"
                   + "        where 1 = 1"
                   + "         and polno = a.polno"
                   + "         and edorno = '"+tEdorNo+"'),a.amnt)"
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
                   //modify by jiaqiangli 2008-12-08 appflag='9'�Ĵ��ռ�¼��ɾ��������ȫ
                   + " and a.appflag = '1' "
                   //+ " and ((a.appflag = '1') or"
                   //+ " (a.appflag = '9' and a.cvalidate + 60 >= '" + tConfDate + "'))"
                   + " and a.cvalidate <= '" + tConfDate + "'"
                   //comment by jiaqiangli 2009-07-06 �����մ��������Ŀ�������Ŀǰ��ѯ������
                   //+ " and enddate > '" + tConfDate + "'"
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
                   
                   //prompt("",strSQL);
         //----------------------
*/        
    var strSQL = "";
    var sqlid4="PEdorTypePTInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql4.addSubPara(tEdorNo);
	mySql4.addSubPara(tEdorNo);
	mySql4.addSubPara(tConfDate);
	mySql4.addSubPara(tContNo);
	mySql4.addSubPara(tContNo);
	mySql4.addSubPara(tContNo);
	strSQL=mySql4.getString();
        
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
/*        strSQL = "select distinct riskcode, (select riskname from lmrisk where lmrisk.riskcode=lcpol.riskcode),paytodate,mult,Amnt,sumprem,paymode from lcpol WHERE 1=1 AND appflag='1' and "
                            +"CONTNO='"+tContNo+"'";
*/    
    var sqlid5="PEdorTypePTInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(tContNo);//ָ������Ĳ���
	strSQL=mySql5.getString();
    
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

/**
 * ��ȫ����ʱ��ʾ����ԭ��ͼ�������
 */
function queryEdorInfo()
{
    try
    {
        calcMinusNumber();
    }
    catch (ex) {}
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
                //modify by jiaqiangli 2008-12-30 javascript parseFloat���������޸���toFixed����
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
    var sqlid6="PEdorTypePTInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
	mySql6.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
	mySql6.addSubPara(fm.EdorType.value);
	mySql6.addSubPara(fm.ContNo.value);
	QuerySQL=mySql6.getString();
    
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
 * ��"����"֮��Ͳ�����"����"
 */
function isSavePol()
{
    var QuerySQL, arrResult;
/*    QuerySQL = "select 'X' "
             +   "from lpedoritem "
             +  "where 1 = 1 and edorstate = '1'"
             +     getWherePart("EdorNo", "EdorNo")
             +     getWherePart("EdorType", "EdorType")
             +     getWherePart("ContNo", "ContNo");
*/    
    var sqlid7="PEdorTypePTInputSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
	mySql7.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
	mySql7.addSubPara(fm.EdorType.value);
	mySql7.addSubPara(fm.ContNo.value);
	QuerySQL=mySql7.getString();
    
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("���棺��鱣���Ƿ��Ѿ�������������쳣�� ");
        return;
    }
    if (arrResult != null)
    {
        alert("����֮�������ٵ������ť�� ")
        return true;
    }
    return false;
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
    var sqlid8="PEdorTypePTInputSql8";
	var mySql8=new SqlClass();
	mySql8.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
	mySql8.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	QuerySQL=mySql8.getString();
    
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
    var sqlid9="PEdorTypePTInputSql9";
	var mySql9=new SqlClass();
	mySql9.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
	mySql9.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	QuerySQL=mySql9.getString();
        
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