//�������ƣ�UWManuHealth.js
//�����ܣ�����Լ�˹��˱��������¼��
//�������ڣ�2004-11-19 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����


var showInfo;
var mDebug="0";
var flag;
var turnPage = new turnPageClass();
var tResourceName="uw.LLUWManuHealthSql";


//�ύ�����水ť��Ӧ����
function submitForm()
{
    if (document.all('InsureNo').value == "")
        {
         alert("����������˺��룡");
         return;
        }
  else

     {
    if(document.all('PEReason').value=="")
    {
      alert("���������ԭ��");
      return;
    }
    var tCustomerNo = document.all('InsureNo').value;
    var tContno = document.all('ContNo').value;

  //arrResult = easyExecSql("select * from lwmission where missionprop1 = '" + tContno +"' and activityid in ('0000001111','0000001106') and missionprop3 in (select trim(prtseq) from LLUWPENotice where contno='" + tContno + "' and CustomerNo='" + tCustomerNo + "')", 1, 0);
  arrResult = easyExecSql(wrapSql(tResourceName,"querysqldes1",[tContno,tCustomerNo]), 1, 0);
  if(arrResult == null)
  {
    //arrResult = easyExecSql("select * from lwmission where missionprop2 = '" + tContno +"' and activityid in ('0000001111','0000001106') and missionprop3 in (select trim(prtseq) from LLUWPENotice where contno='" + tContno + "' and CustomerNo='" + tCustomerNo + "')", 1, 0);
    arrResult = easyExecSql(wrapSql(tResourceName,"querysqldes2",[tContno,tCustomerNo]), 1, 0);
  }


  if (arrResult != null)
     {
       alert("���֪ͨ�Ѿ�¼��,��δ��ӡ������¼�����������!");
       return;
     }

//tongmeng 2008-10-13 add
//
  getAllChecked();
  //return ;
  var i = 0;
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


  fm.action= "./LLUWManuHealthChk.jsp";
  document.getElementById("fm").submit(); //�ύ
      }
}

/**
 * �ύ�����, ���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(DealFlag, MsgContent)
{
    try { showInfo.close(); } catch (ex) {}
    DealFlag = DealFlag.toLowerCase();
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=";
    switch (DealFlag)
    {
        case "fail":
            MsgPageURL = MsgPageURL + "F&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=250px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();

            break;
        case "succ":
        case "success":
            MsgPageURL = MsgPageURL + "S&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=350;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        default:
            MsgPageURL = MsgPageURL + "C&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=300;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
    }
    //���ļ������⴦��
    if (DealFlag == "succ" || DealFlag == "success")
    {
        try
        {
            //returnParent();
            initForm(mContNo,'','',mPrtNo,mFlag,mBatchNo,mRgtNo);
        }
        catch (ex) {}
    }
}


//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
            parent.fraMain.rows = "0,0,50,82,*";
  }
    else {
        parent.fraMain.rows = "0,0,0,82,*";
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


function manuchkhealthmain()
{
    document.getElementById("fm").submit();
}

// ��ѯ����
function easyQueryClickSingle()
{

	// ��дSQL���
	var strsql = "";
	var tProposalno = "";
	
	tProposalContno = fm.ContNo.value;
	tInsuredNo = fm.InsureNo.value;
	//tInsuredNo = "86110020030990000863";
	//alert('1');
  if(tProposalno != "" && tInsuredNo != "")
  {
	/*strsql = "select proposalno,insuredname,pedate,peaddress,PEBeforeCond,remark,printflag from LCPENotice where 1=1"
				 + " and proposalno = '"+ tProposalContno + "'"
				 + " and insuredno = '"+ tInsuredNo + "'";*/
	strsql = wrapSql(tResourceName,"querysqldes3",[tProposalContno,tInsuredNo]);
		//alert('2');		 				 
	//alert(strsql);
	
	//execEasyQuery( strSQL );
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    //alert("��ѯʧ�ܣ�");
    easyQueryClickInit();
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.ProposalNo.value = turnPage.arrDataCacheSet[0][0];
  //fm.InsureNo.value = turnPage.arrDataCacheSet[0][1];
  //fm.EDate.value = turnPage.arrDataCacheSet[0][2];
  fm.Hospital.value = turnPage.arrDataCacheSet[0][3];
  fm.IfEmpty.value = turnPage.arrDataCacheSet[0][4];
  fm.Note.value = turnPage.arrDataCacheSet[0][5];
  fm.PrintFlag.value = turnPage.arrDataCacheSet[0][6];
  }
  return true;
}



// ��ѯ��ť
function easyQueryClick()
{
	// ��ʼ�����                                                                                                                                                           
	//initUWHealthGrid();
	// ��дSQL���
	var strsql = "";
	var tProposalno = "";
	tProposalContno = fm.ContNo.value;
	tInsuredNo = fm.InsureNo.value;
  if(tProposalContno != "" && tInsuredNo != "")
  {
	/*strsql = "select peitemcode,peitemname from LCPENotice where 1=1"
				 + " and proposalcontno = '"+ tProposalContno + "'"
				 + " and customerno = '"+ tInsuredNo + "'";*/
	strsql = wrapSql(tResourceName,"querysqldes4",[tProposalContno,tInsuredNo]);
				 				 
	//alert(strsql);
	
	//execEasyQuery( strSQL );
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    //alert("����¼����Ϣ��");
    //easyQueryClickInit();
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = HealthGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strsql; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  }
  return true;
}

function QueryReason()
{
    var strsql = "";
    var tContNo = "";
    var tEdorNo = "";
    tContNo = fm.ContNo.value;
    tMissionID = fm.MissionID.value;
    tInsuredNo = fm.InsureNo.value;
  if(tContNo != "" && tInsuredNo != "")
  {


    /*strSQL = "select a.contno,a.uwno,a.uwerror,a.modifydate from LCUWError a where 1=1 "
             + " and a.PolNo in (select distinct polno from lcpol where contno ='" +tContNo+ "')"
             + " and SugPassFlag = 'H'"
             + " and (a.uwno = (select max(b.uwno) from LCUWError b where b.ContNo = '" +tContNo + "' and b.PolNo = a.PolNo))"
             + " union "
             + "select c.contno,c.uwno,c.uwerror,c.modifydate from LCCUWError c where 1=1"
             + " and c.ContNo ='" + tContNo + "'"
             + " and SugPassFlag = 'H'"
             + " and (c.uwno = (select max(d.uwno) from LCCUWError d where d.ContNo = '" + tContNo + "'))"*/
    strSQL = wrapSql(tResourceName,"querysqldes5",[tContNo]);

  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 1, 1);

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    return "";
  }

  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = UWErrGrid;

  //����SQL���
  turnPage.strQuerySql     = strsql;

  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;

  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
 }
}



// ��������ϲ�ѯ��ť
function easyQueryClickInit()
{
    fm.action= "./UWManuHealthQuery.jsp";
    document.getElementById("fm").submit();
}

function displayEasyResult( arrResult )
{
    var i, j, m, n;
    if( arrResult == null )
        alert( "û���ҵ���ص�����!" );
    else
    {
        arrGrid = arrResult;
        // ��ʾ��ѯ���
        n = arrResult.length;
        for( i = 0; i < n; i++ )
        {
            m = arrResult[i].length;
            for( j = 0; j < m; j++ )
            {
                HealthGrid.setRowColData( i, j+1, arrResult[i][j] );

            } // end of for
        } // end of for
        //alert("result:"+arrResult);
    } // end of if
}

function initInsureNo(tPrtNo)
{
    var sEdorNo;
    try
    {
        sEdorNo = document.getElementsByName("EdorNo")[0].value;
    }
    catch (ex) {}

    var i,j,m,n;
    var returnstr;

    /*var strSql = "select b,a,c,d,e from (select InsuredNo as a,('��'||sequenceno||'������') as b,name as c,'I' d,sequenceno e from lcinsured where 1=1 "
           + " and Prtno = '" +tPrtNo + "' and not exists(select 'x' from lcinsuredrelated where customerno = insuredno and polno in (select polno from lcpol where Prtno = '"+tPrtNo+"'))"
           + " union select appntno as a,'Ͷ����' as b ,appntname as c,'A' d,'-1' e from lccont where prtno = '" +tPrtNo + "'"
           + " union select CustomerNo as a,'�ڶ�������' as b , Name as c,'I' d,'0' e from LCInsuredRelated where polno in (select polno from lcpol where Prtno = '" +tPrtNo+"')";*/
	var sqltemp = ""; 
    if (sEdorNo != null && trim(sEdorNo) != "")
    {
        //XinYQ added on 2006-10-28
        /*strSql += " union select u.AppntNo as a, '���Ͷ����' as b, u.AppntName as c,'A' d,'0' e from LPAppnt u where 1 = 1 and u.EdorNo = '" + sEdorNo + "' " + getWherePart("u.ContNo", "ContNo") + " and not exists (select 'X' from LCAppnt where ContNo = u.ContNo and AppntNo = u.AppntNo)"
               +  " union select v.InsuredNo as a, '���������' as b, v.Name as c,'I' d,'0' e from LPInsured v where 1 = 1 and v.EdorNo = '" + sEdorNo + "' " + getWherePart("v.ContNo", "ContNo") + " and not exists (select 'X' from LCInsured where ContNo = v.ContNo and InsuredNo = v.InsuredNo)";*/
        sqltemp=" union select u.AppntNo as a, '���Ͷ����' as b, u.AppntName as c,'A' d,'0' e from LPAppnt u where 1 = 1 and u.EdorNo = '" + sEdorNo + "' " + getWherePart("u.ContNo", "ContNo") + " and not exists (select 'X' from LCAppnt where ContNo = u.ContNo and AppntNo = u.AppntNo)"
               +  " union select v.InsuredNo as a, '���������' as b, v.Name as c,'I' d,'0' e from LPInsured v where 1 = 1 and v.EdorNo = '" + sEdorNo + "' " + getWherePart("v.ContNo", "ContNo") + " and not exists (select 'X' from LCInsured where ContNo = v.ContNo and InsuredNo = v.InsuredNo)";
    }

       /* strSql += ") temp"
               +  " order by e";*/
    var strSql = wrapSql(tResourceName,"querysqldes6",[tPrtNo,sqltemp]);
    //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);

  //�ж��Ƿ��ѯ�ɹ�
  //alert(turnPage.strQueryResult);
  if (turnPage.strQueryResult == "")
  {
    alert("��ѯʧ�ܣ�");
    return "";
  }

  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;
  //alert("N:"+n);
  if (n > 0)
  {
    for( i = 0;i < n; i++)
    {
        m = turnPage.arrDataCacheSet[i].length;
        //alert("M:"+m);
        if (m > 0)
        {
            for( j = 0; j< m; j++)
            {
                if (i == 0 && j == 0)
                {
                    returnstr = "0|^"+turnPage.arrDataCacheSet[i][j];
                }
                if (i == 0 && j > 0)
                {
                    returnstr = returnstr + "|" + turnPage.arrDataCacheSet[i][j];
                }
                if (i > 0 && j == 0)
                {
                    returnstr = returnstr+"^"+turnPage.arrDataCacheSet[i][j];
                }
                if (i > 0 && j > 0)
                {
                    returnstr = returnstr+"|"+turnPage.arrDataCacheSet[i][j];
                }

            }
        }
        else
        {
            alert("��ѯʧ��!!");
            return "";
        }
    }
}
else
{
    alert("��ѯʧ��!");
    return "";
}
  //alert("returnstr:"+returnstr);
  fm.InsureNo.CodeData = returnstr;
  //prompt('',returnstr);
  return returnstr;
}

//��ʼ��ҽԺ
function initHospital(tContNo)
{
    var i,j,m,n;
    var returnstr;

    /*var strSql = "select hospitcode,hospitalname from ldhospital where 1=1 "
           //+ "and codetype = 'hospitalcodeuw'"
           + "and mngcom like '' || substr((select managecom from lccont where ContNo = '"+tContNo+"'),0,6) || '%%' ";*/
    var strSql = wrapSql(tResourceName,"querysqldes7",[tContNo]);
    //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);
  //alert(strSql);
  //�ж��Ƿ��ѯ�ɹ�
  //alert(turnPage.strQueryResult);
  if (turnPage.strQueryResult == "")
  {
    //alert("ҽԺ��ʼ��ʧ�ܣ�");
    return "";
  }

  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  //turnPage.pageDisplayGrid = VarGrid;

  //����SQL���
  //turnPage.strQuerySql     = strSql;

  //���ò�ѯ��ʼλ��
  //turnPage.pageIndex       = 0;

  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  //var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

  //����MULTILINE������ʾ��ѯ���
  //displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;
  //alert("N:"+n);
  if (n > 0)
  {
    for( i = 0;i < n; i++)
    {
        m = turnPage.arrDataCacheSet[i].length;
        //alert("M:"+m);
        if (m > 0)
        {
            for( j = 0; j< m; j++)
            {
                if (i == 0 && j == 0)
                {
                    returnstr = "0|^"+turnPage.arrDataCacheSet[i][j];
                }
                if (i == 0 && j > 0)
                {
                    returnstr = returnstr + "|" + turnPage.arrDataCacheSet[i][j];
                }
                if (i > 0 && j == 0)
                {
                    returnstr = returnstr+"^"+turnPage.arrDataCacheSet[i][j];
                }
                if (i > 0 && j > 0)
                {
                    returnstr = returnstr+"|"+turnPage.arrDataCacheSet[i][j];
                }

            }
        }
        else
        {
            alert("��ѯʧ��!!");
            return "";
        }
    }
}
else
{
    alert("��ѯʧ��!");
    return "";
}
  //alert("returnstr:"+returnstr);
  fm.Hospital.CodeData = returnstr;
  return returnstr;
}

function showNewUWSub()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();


  cContNo=fm.ContNo.value;

    //window.open("./PUWErrMain.jsp?ProposalNo1="+cProposalNo,"window1");
    window.open("./UWPEErrMain.jsp?ContNo="+cContNo,"window1");

    showInfo.close();

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

//tongmeng 2008-10-10 add
//�����������Զ����������Ŀ
function showBodyCheck(ID)
{
	clearAllCheck();
	//alert(ID);
	var strValue;
     strValue=ID.split("#");
    for(n=0;n<strValue.length;n++)
    {
      //alert(strValue[n]);
      if(strValue[n]!=null&&strValue[n]!='')
      {
      	eval("document.fm."+strValue[n]+".checked = true");
      }
    }
}
function checkMainHealthCode(ID)
{
	//alert(ID);
  eval("document.fm."+ID+".checked = true");
}

//�������check,����Ϊ��ѡ��
function clearAllCheck()
{
  for (elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++)
		{
			if (window.document.forms[0].elements[elementsNum].type != null && window.document.forms[0].elements[elementsNum].type != "")
			{
				if(window.document.forms[0].elements[elementsNum].type=='checkbox'
				//||window.document.forms[0].elements[elementsNum].type=='radio'
				)
				{
				  //alert(window.document.forms[0].elements[elementsNum].id);
				  window.document.forms[0].elements[elementsNum].checked= false;
				}
			}
		}
}

function getAllChecked()
{
   var tAllChecked = "";
   for (elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++)
		{
			if (window.document.forms[0].elements[elementsNum].type != null && window.document.forms[0].elements[elementsNum].type != "")
			{
				if(window.document.forms[0].elements[elementsNum].type=='checkbox'
				//||window.document.forms[0].elements[elementsNum].type=='radio'
				)
				{
				  //alert(window.document.forms[0].elements[elementsNum].id);
				  if(window.document.forms[0].elements[elementsNum].checked)
				  {
				     tAllChecked = tAllChecked + "#" + window.document.forms[0].elements[elementsNum].id;
				     
				  }
				}
			}
		}
	//	alert(tAllChecked);
	fm.CheckedItem.value = tAllChecked;
}
function afterCodeSelect( cCodeName, Field ) {
	   
//	       if(cCodeName == 'InsureNo')//
//	       {
//	       		easyQueryClickSingle();
//	       }
//	       easyQueryClickInit();
	    //   getAllChecked();
}


function querySavePEInfo()
{
	/*var tSQL = "select name,decode(customertype,'A','Ͷ����','������'),(select codename from ldcode where codetype='printstate' and code=nvl(printflag,'x')),"
	         + " operator,prtseq,nvl(printflag,'x'),customerno,customertype,'',makedate from lluwpenotice "
	         + " where contno='"+document.all('ContNo').value+"' "
	         + " and batno='"+fm.BatchNo.value+"' "
	         + " and clmno='"+fm.RgtNo.value+"' "
	         + " order by customerno,makedate,maketime ";*/
	var tSQL = wrapSql(tResourceName,"querysqldes8",[document.all('ContNo').value,fm.BatchNo.value,fm.RgtNo.value]);
	 turnPage.strQueryResult  = easyQueryVer3(tSQL, 1, 0, 1);//prompt("",tSQL);

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    return "";
  }

  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  var m = turnPage.arrDataCacheSet.length;
  var tContent ="";
  var arrResult = "";
  var tSQL;
  //alert(m);
  //��ѯ�������
  for(var i=0; i<m; i++)
  {
    /*tSQL = "select peitemname from lluwpenoticeitem where 1=1 "
	       + " and contno = '"+ document.all('ContNo').value +"' and prtseq = '"+turnPage.arrDataCacheSet[i][4]+"' "
	       + " and batno='"+fm.BatchNo.value+"' "
	       + " and clmno='"+fm.RgtNo.value+"' "
	       + " order by peitemcode" ;*/
	tSQL = wrapSql(tResourceName,"querysqldes9",[document.all('ContNo').value,turnPage.arrDataCacheSet[i][4],fm.BatchNo.value,fm.RgtNo.value]);
	arrResult=easyExecSql(tSQL);
	for(var j=0; j<arrResult.length; j++)
	   tContent = tContent + arrResult[j][0]+" ;";
	turnPage.arrDataCacheSet[i][8] = tContent;
  }    

  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PEGrid;

  //����SQL���
  turnPage.strQuerySql     = tSQL;

  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;

  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

function easyQueryAddClick()
{
	//��ñ�ѡ�����
		var tSelNo = PEGrid.getSelNo()-1;
		var tPrtSeq = PEGrid.getRowColData(tSelNo,5);
		var tCustomerNo = PEGrid.getRowColData(tSelNo,7);
		var tCustomerType = PEGrid.getRowColData(tSelNo,8);
		
		/*var tSQL = "select customerno,customertype,pereason,pebeforecond,PEDate,nvl(PrintFlag,'x'),remark from lluwpenotice "
		            + " where contno='"+document.all('ContNo').value+"' "
		            + " and prtseq='"+tPrtSeq+"' and customerno='"+tCustomerNo+"' "
		            + " and customertype='"+tCustomerType+"' "
		            ;*/
		var tSQL = wrapSql(tResourceName,"querysqldes10",[document.all('ContNo').value,tPrtSeq,tCustomerNo,tCustomerType]);
		            //prompt("",tSQL);
		var arrResult=easyExecSql(tSQL);
    if(arrResult!=null)
    {
    	 document.all('InsureNo').value=arrResult[0][0];
    	 document.all('CustomerType').value=arrResult[0][1];
    	 document.all('PEReason').value=arrResult[0][2];
    	 document.all('IfEmpty').value=arrResult[0][3];
    	 document.all('EDate').value=arrResult[0][4];
    	 document.all('PrintFlag').value=arrResult[0][5];
    	 document.all('Note').value=arrResult[0][6];
    }
    document.all('PrtSeq').value = tPrtSeq;
    //��ѯ�������Ŀ
    /*var tSQL_Sub = "select peitemcode,peitemname from lluwpenoticeitem "
                 + " where contno='"+document.all('ContNo').value+"' "
                 + " and prtseq = '"+tPrtSeq+"' ";*/
    var tSQL_Sub = wrapSql(tResourceName,"querysqldes11",[document.all('ContNo').value,tPrtSeq]);
    arrResult=easyExecSql(tSQL_Sub);
   // alert(arrResult.length);
    var tAllCheckedItem = "";
    var tItemOther = " ";
    if(arrResult!=null)       
    {
    	 for(i=0;i<arrResult.length;i++)
    	 {
    	 	 if(arrResult[i][0]!='other')
    	 	 {
    	 	 	 tAllCheckedItem = tAllCheckedItem + '#'+arrResult[i][0];
    	 	 }
    	 	  else
    	 	 {
    	 	 	 tItemOther = arrResult[i][1];
    	 	 }
    	 }
    }  
   // alert(tAllCheckedItem); 
   // alert(tItemOther);       
    showBodyCheck(tAllCheckedItem);
    document.all('otheritem2').value=tItemOther;
	//	fm.proposalContNo.value = UWSpecGrid.getRowColData(tSelNo,5);
}

function updateData()
{
	document.all('Action').value = "UPDATE";
	var tSelNo = PEGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		alert("��ѡ����Ҫ�޸ĵ��������!");
		return false;
	}
	
	if(document.all('PrintFlag').value!='x')
	{
		alert("����������Ѿ�����,�������޸�!");
		return false;
	}
	 submitForm();
}


function deleteData()
{
	document.all('Action').value = "DELETE";
	var tSelNo = PEGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		alert("��ѡ����Ҫɾ�����������!");
		return false;
	}
	
	if(document.all('PrintFlag').value!='x')
	{
		alert("����������Ѿ�����,������ɾ��!");
		return false;
	}
	 submitForm();
}


function addData()
{
	document.all('Action').value = "INSERT";
	 submitForm();
}