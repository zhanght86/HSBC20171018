//�������ƣ�PEdorTypePL.js
//�����ܣ�������ʧ����
//���¼�¼��  ������    ��������     ����ԭ��/����
//             liurx    2005-06-28
//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var sqlresourcename = "bq.PEdorTypePLInputSql";


function getPolInfo()
{
    //alert(tContNo);
    var tContNo=document.all("ContNo").value;
    // ��дSQL���
//    var strSQL ="select InsuredNo,InsuredName,RiskCode,(select RiskName from LMRisk where LMRisk.RiskCode = LCPol.RiskCode),Prem,Amnt,CValiDate,contno,grpcontno from LCPol where ContNo='"+tContNo+"' and appflag <> '4'";

	var strSQL = "";
	var sqlid1="PEdorTypePLInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tContNo);//ָ������Ĳ���
	strSQL=mySql1.getString();

    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult)
    {
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

function chkPol()
{
    var tContno=document.all('ContNo').value;
    var tEdorNo=document.all('EdorNo').value;
    var tEdorType=document.all('EdorType').value;
//    var strSQL="select polno from lppol where edorno='"+tEdorNo+"' and edortype='"+tEdorType+"' and contno='"+tContno+"'";
	
	var strSQL = "";
	var sqlid2="PEdorTypePLInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	
	mySql2.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql2.addSubPara(tEdorType);
	//begin zbx 20110512
	//mySql2.addSubPara(tContNo);
	mySql2.addSubPara(tContno);
	//end zbx 20110512
	strSQL=mySql2.getString();
    var arrResult2=easyExecSql(strSQL);

    var m=0;
    var n=0;

    if(arrResult2!=null)
    {
        var q=arrResult2.length;

        for(m=0;m<PolGrid.mulLineCount;m++)
        {

            for(n=0;n<q;n++)
           {
                if(PolGrid.getRowColData(m,3)==arrResult2[n][0])
                {
                    PolGrid.checkBoxSel(m+1);
                }
            }
        }
    }
}
//��ʧ�����ύ
function edorTypePLSave()
{
  var vLostFlag = fm.LostFlag.value;

  if(vLostFlag=="1")
  {
     if(!fm.ReportByLoseForm.value)
     {
       alert("��ʧ���Ͳ���Ϊ�գ�");
       fm.ReportByLoseForm.focus();
       return;
     }
    var tReportByLoseDate = fm.ReportByLoseDate.value;
    if(tReportByLoseDate!="")
    {
      if (!isDate(tReportByLoseDate)&&!isDateN(tReportByLoseDate))
      {
            alert("��ʧʱ�䲻����Ч�����ڸ�ʽ(YYYY-MM-DD)����(YYYYMMDD)!");
            return false;
      }
    }

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
  fm.fmtransact.value="INSERT||MAIN";
  fm.submit();
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
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

    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  }
  initForm();
}


/*********************************************************************
 *  ��ʾfrmSubmit��ܣ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
    if( cDebug == "1" )
        parent.fraMain.rows = "0,0,50,82,*";
    else
        parent.fraMain.rows = "0,0,0,72,*";
}

/*********************************************************************
 *  ��ѯ�ͻ���Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function ShowCustomerInfo()
{

    var tContNo=document.all("ContNo").value;

    if(tContNo!=null && tContNo!="")
    {
/*        var strSQL = " Select a.appntno,'Ͷ����',a.appntname,a.appntsex||'-'||sex.codename,a.appntbirthday,a.idtype||'-'||x.codename,a.idno From lcappnt a "
                                        +" Left Join (Select code,codename From ldcode Where codetype='idtype') x On x.code = a.idtype "
                                        +" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = a.appntsex  Where contno='"+tContNo+"'"
                                        +" Union"
                                        +" Select i.insuredno,'������',i.name,i.Sex||'-'||sex.codename,i.Birthday,i.IDType||'-'||xm.codename,i.IDNo From lcinsured i "
                                        +" Left Join (Select code,codename From ldcode Where codetype='idtype') xm On xm.code = i.idtype "
                                        +" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = i.sex Where contno='"+tContNo+"'";
                        + " birthday from lcinsured b where contno = '"+tContNo+"' ";
*/        
    var strSQL = "";
	var sqlid3="PEdorTypePLInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(tContNo);//ָ������Ĳ���
	mySql3.addSubPara(tContNo);
	mySql3.addSubPara(tContNo);
	strSQL=mySql3.getString();
        
        var crr = easyExecSql(strSQL);
        if(crr)
        {
           initCustomerGrid();
           displayMultiline(crr,CustomerGrid);
        }
        else
        {
            return"";
        }
    }
}


/********************************************************
 * �ж��ǽ�һ��ǹ�ʧ
 * ��������ȥ����״̬����lccontstate�в�ѯ�����Ƿ��ѹ�ʧ
 *       �������ѹ�ʧ���ҹ�ʧ״̬δ�սᣬ����ʾ"���"��ť��������ʾ"��ʧ"��ť��
 * ��������
 * ����ֵ����
 *********************************************************
*/
function ReportByLoseFormV(tCurrentDate)
{
    var vState = "";
    var vStateType = "";
    var lostFlag = false;//��ʧ��־�������ѹ�ʧʱ��Ϊ�棬δ��ʧ��Ϊ��
    
//    var strsql="select standbyflag1,decode(standbyflag1,'1','��ʧ','0','���'),edorreasoncode,edorreason,standbyflag2 from lpedoritem where edortype='PL' and contno='"+fm.ContNo.value+"' and edoracceptno='"+fm.EdorAcceptNo.value+"'";
    
    var strsql = "";
	var sqlid4="PEdorTypePLInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	mySql4.addSubPara(fm.EdorAcceptNo.value);
	strsql=mySql4.getString();
    
    var brr = easyExecSql(strsql);
    
    //�������ϸ����
    if (brr) {
    	//��ʧ
    	if (brr[0][0] != null && brr[0][0] != '' && brr[0][0] == '1') {
    		document.all('LostFlag').value=brr[0][0];
    		document.all('LostFlagName').value=brr[0][1];
    		divLostDiv.style.display='';
    		document.all('ReportByLoseReason').value=brr[0][2];
    		document.all('ReportByLoseReasonName').value=brr[0][3];
    		document.all('ReportByLoseDate').value=brr[0][4];
    	}
    	//���
    	else {
    		document.all('LostFlag').value=brr[0][0];
    		document.all('LostFlagName').value=brr[0][1];
    		divLostDiv.style.display='none';
    	}
    }

//    var strSql = "select contno from lccontstate where contno='"+fm.ContNo.value
//              +"' and statetype='Lost' and state='1' and startdate <= '"
//              +tCurrentDate+"' and (enddate is null or enddate >='"+tCurrentDate+"')";
//    var brr = easyExecSql(strSql);
//
//    if(brr)
//    {
//       lostFlag = true; //�ѹ�ʧ
//    }
//    if(!lostFlag)
//    {
//        strSql = "select distinct trim(statereason),(select codename from ldcode where trim(code) = trim(statereason) and codetype = 'reportlosttype'),trim(remark) "
//                   + " from lpcontstate where edorno = '"+fm.EdorNo.value
//                   + " ' and contno = '"+fm.ContNo.value+"' and edortype = '"+fm.EdorType.value+"'";
//        brr = easyExecSql(strSql);
//        if(brr)
//        {
//            document.all('ReportByLoseForm').value=brr[0][0];
//            document.all('ReportByLoseFormName').value=brr[0][1];
//            document.all('ReportByLoseDate').value=brr[0][2].substring(5);
//        }
//        strSql = "select edorreasoncode,(select codename from ldcode where codetype = 'lostreason' and trim(code) = trim(edorreasoncode)) from lpedoritem "
//                   + " where edorno = '"+fm.EdorNo.value+"' "
//                   + " and contno = '"+fm.ContNo.value+"' "
//                   + " and edortype = '"+fm.EdorType.value+"' ";
//        brr = easyExecSql(strSql);
//        if(brr)
//        {
//            document.all('ReportByLoseReason').value=brr[0][0];
//            document.all('ReportByLoseReasonName').value=brr[0][1];
//        }
//        //fm.ReportByLose.value="��  ʧ";
//        document.all('LostFlag').value="1";
//        //document.all('EdorTypeName').value = "������ʧ";
//    }
//    else
//    {
//        divReportLostInfo.style.display='none';
//        //fm.ReportByLose.value="ȡ����ʧ";
//        document.all('LostFlag').value="0";
//        //document.all('EdorTypeName').value = "�����ʧ";
//    }

}
//���±��鿴
function showNotePad()
{
    var MissionID = top.opener.document.all("MissionID").value;
    var SubMissionID = top.opener.document.all("SubMissionID").value;
    var ActivityID = "0000000003";
    var OtherNo = top.opener.document.all("OtherNo").value;

    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionIDΪ�գ�");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");
}

//����
function returnParent()
{
 top.opener.getEdorItemGrid();
 top.close();
 top.opener.focus();
}

function querySignDate()
{
    var tContNo=document.all("ContNo").value;
//    var strSql = "select signdate from lccont where contno = '"+tContNo+"'";
    var strsql = "";
	var sqlid5="PEdorTypePLInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(tContNo);//ָ������Ĳ���
	strsql=mySql5.getString();
	//begin zbx 20110512
    var brr = easyExecSql(strsql);
	//end zbx 20110512
    if(brr)
    {
        document.all('SignDate').value = brr[0][0];
        if(fm.SignDate.value == null || fm.SignDate.value =="")
        {
            alert("�������ݲ���ȫ��ǩ������Ϊ�գ�");
            return;
        }
    }
    else
    {
        alert("������Ϣ����û�м�¼��");
        return;
    }

}

function Edorquery()
{
    var ButtonFlag = top.opener.document.all('ButtonFlag').value;
    if(ButtonFlag!=null && ButtonFlag=="1")
    {
       divEdorquery.style.display = "none";
       fm.Flag.value = "1";
    }
    else
    {
        divEdorquery.style.display = "";
        fm.Flag.value = "0";
    }
}

//add by jiaqiangli 2008-08-05
//���ƹ�ʧ���ҵĲ�ͬ����
function afterCodeSelect( cCodeName, Field ) {
	try	{
		if (cCodeName == "lostflag") {
			var fieldvalue = Field.value;
			//���ʧ
			if (fieldvalue == "1") {
				divLostDiv.style.display = "";
			}
			//����
		        else {
		        	divLostDiv.style.display = "none";
		        }
		}
	}
	catch( ex ) {
	}
}