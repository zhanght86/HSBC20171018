//�������ƣ�UWManuHealth.js
//�����ܣ�����Լ�˹��˱��������¼��
//�������ڣ�2004-11-19 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����


var showInfo;
var mDebug="0";
var flag;
var turnPage = new turnPageClass();


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

    var sqlid1="UWManuHealthSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("uw.UWManuHealthSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tContno);//ָ������Ĳ���
	mySql1.addSubPara(tContno);//ָ������Ĳ���
	mySql1.addSubPara(tCustomerNo);//ָ������Ĳ���
	var strSql=mySql1.getString();
    
  arrResult = easyExecSql(strSql, 1, 0);

  if(arrResult == null)
  {
	    var sqlid2="UWManuHealthSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.UWManuHealthSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(tContno);//ָ������Ĳ���
		mySql2.addSubPara(tContno);//ָ������Ĳ���
		mySql2.addSubPara(tCustomerNo);//ָ������Ĳ���
		var strSql=mySql2.getString();
	  
    arrResult = easyExecSql(strSql, 1, 0);
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
lockScreen('lkscreen');  
  fm.action= "./UWManuHealthChk.jsp";
  document.getElementById("fm").submit(); //�ύ
      }
}

/**
 * �ύ�����, ���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(DealFlag, MsgContent)
{
	unlockScreen('lkscreen');  
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
            initForm(mContNo,'','',mPrtNo,mFlag);
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
//	strsql = "select proposalno,insuredname,pedate,peaddress,PEBeforeCond,remark,printflag from LCPENotice where 1=1"
//				 + " and proposalno = '"+ tProposalContno + "'"
//				 + " and insuredno = '"+ tInsuredNo + "'";
	
	 var sqlid3="UWManuHealthSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("uw.UWManuHealthSql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(tProposalContno);//ָ������Ĳ���
		mySql3.addSubPara(tInsuredNo);//ָ������Ĳ���
		var strsql=mySql3.getString();
	
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
//	strsql = "select peitemcode,peitemname from LCPENotice where 1=1"
//				 + " and proposalcontno = '"+ tProposalContno + "'"
//				 + " and customerno = '"+ tInsuredNo + "'";
	
	 var sqlid4="UWManuHealthSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("uw.UWManuHealthSql"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(tProposalContno);//ָ������Ĳ���
		mySql4.addSubPara(tInsuredNo);//ָ������Ĳ���
		var strsql=mySql4.getString();
	
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


//    strSQL = "select a.contno,a.uwno,a.uwerror,a.modifydate from LCUWError a where 1=1 "
//             + " and a.PolNo in (select distinct polno from lcpol where contno ='" +tContNo+ "')"
//             + " and SugPassFlag = 'H'"
//             + " and (a.uwno = (select max(b.uwno) from LCUWError b where b.ContNo = '" +tContNo + "' and b.PolNo = a.PolNo))"
//             + " union "
//             + "select c.contno,c.uwno,c.uwerror,c.modifydate from LCCUWError c where 1=1"
//             + " and c.ContNo ='" + tContNo + "'"
//             + " and SugPassFlag = 'H'"
//             + " and (c.uwno = (select max(d.uwno) from LCCUWError d where d.ContNo = '" + tContNo + "'))"
             
            var sqlid5="UWManuHealthSql5";
     		var mySql5=new SqlClass();
     		mySql5.setResourceName("uw.UWManuHealthSql"); //ָ��ʹ�õ�properties�ļ���
     		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
     		mySql5.addSubPara(tContNo);//ָ������Ĳ���
     		mySql5.addSubPara(tContNo);//ָ������Ĳ���
     		mySql5.addSubPara(tContNo);//ָ������Ĳ���
     		mySql5.addSubPara(tContNo);//ָ������Ĳ���
     		var strSQL=mySql5.getString();

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
	lockScreen('lkscreen');  
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

function initInsured(tPrtNo)
{
//	strsql = "select insuredno,insuredname from lccont "
//	       + "where   Prtno = '" +tPrtNo +"' ";
	
	 var sqlid6="UWManuHealthSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("uw.UWManuHealthSql"); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(tPrtNo);//ָ������Ĳ���
		var strsql=mySql6.getString();
		
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
      
    alert("�޴˿ͻ�");
    return ;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.InsureNo.value = turnPage.arrDataCacheSet[0][0];
 
  fm.CustomerType.value = 'I';
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
//
//    var strSql = "select b,a,c,d,e from (select InsuredNo as a,(concat(concat('��',sequenceno),'������')) as b,name as c,'I' d,sequenceno e from lcinsured where 1=1 "
//           + " and Prtno = '" +tPrtNo + "' and not exists(select 'x' from lcinsuredrelated where customerno = insuredno and polno in (select polno from lcpol where Prtno = '"+tPrtNo+"'))"
//           + " union select appntno as a,'Ͷ����' as b ,appntname as c,'A' d,'5' e from lccont where prtno = '" +tPrtNo + "'"
//           + " union select CustomerNo as a,'�ڶ�������' as b , Name as c,'I' d,'0' e from LCInsuredRelated where polno in (select polno from lcpol where Prtno = '" +tPrtNo+"')";
    
	var ContNoid5="";
	var ContNoid6="";
    if (sEdorNo != null && trim(sEdorNo) != "")
    {
    	var ContNoid5= window.document.getElementsByName(trim("ContNo"))[0].value;
    	var ContNoid6= window.document.getElementsByName(trim("ContNo"))[0].value;
        //XinYQ added on 2006-10-28
//        strSql += " union select u.AppntNo as a, '���Ͷ����' as b, u.AppntName as c,'A' d,'0' e from LPAppnt u where 1 = 1 and u.EdorNo = '" + sEdorNo + "' " + getWherePart("u.ContNo", "ContNo") + " and not exists (select 'X' from LCAppnt where ContNo = u.ContNo and AppntNo = u.AppntNo)"
//               +  " union select v.InsuredNo as a, '���������' as b, v.Name as c,'I' d,'0' e from LPInsured v where 1 = 1 and v.EdorNo = '" + sEdorNo + "' " + getWherePart("v.ContNo", "ContNo") + " and not exists (select 'X' from LCInsured where ContNo = v.ContNo and InsuredNo = v.InsuredNo)";
    }

//        strSql += ") temp"
//               +  " order by e";
        
		var mySql7=new SqlClass();
		mySql7.setSqlId("UWManuHealthSql7");// ָ��ʹ�õ�Sql��id
		mySql7.setResourceName("uw.UWManuHealthSql"); //ָ��ʹ�õ�properties�ļ���
		mySql7.addSubPara(tPrtNo);//ָ������Ĳ���
		mySql7.addSubPara(tPrtNo);//ָ������Ĳ���
		mySql7.addSubPara(tPrtNo);//ָ������Ĳ���
		mySql7.addSubPara(tPrtNo);//ָ������Ĳ���
		mySql7.addSubPara(sEdorNo);//ָ������Ĳ���
		mySql7.addSubPara(ContNoid5);//ָ������Ĳ���
		mySql7.addSubPara(sEdorNo);//ָ������Ĳ���
		mySql7.addSubPara(ContNoid6);//ָ������Ĳ���
		var strSql=mySql7.getString();
        
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

//    var strSql = "select hospitcode,hospitalname from ldhospital where 1=1 "
//           + "and mngcom like concat(concat('' , substr((select managecom from lccont where ContNo = '"+tContNo+"'),1,6)) , '%%') ";
    
	var mySql8=new SqlClass();
	mySql8.setSqlId("UWManuHealthSql8");// ָ��ʹ�õ�Sql��id
	mySql8.setResourceName("uw.UWManuHealthSql"); //ָ��ʹ�õ�properties�ļ���
	mySql8.addSubPara(tContNo);//ָ������Ĳ���
	var strSql=mySql8.getString();
    
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
      	try{
      	eval("fm."+strValue[n]+".checked = true");
      	}
      	catch(ex)
      	{
      		}
      }
    }
}
function checkMainHealthCode(ID)
{
	//alert(ID);
	try
	{
  	eval("fm."+ID+".checked = true");
  }
  catch(ex)
  {}
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
	   
	       if(cCodeName == 'InsureNo')//
	       {
	       		easyQueryClickSingle();
	       		easyQueryClickInit();
	       }
	       
	    //   getAllChecked();
}


function querySavePEInfo()
{
//	var tSQL = "select name,(case customertype when 'A' then 'Ͷ����' else '������' end),(select codename from ldcode where codetype='printstate' and code=(case when printflag is not null then printflag else 'x' end)),"
//	         + " operator,prtseq,(case when printflag is not null then printflag else 'x' end),customerno,customertype,'',makedate from lcpenotice "
//	         + " where contno='"+document.all('ContNo').value+"' "
//	         + " order by customerno,makedate,maketime ";
	
	var mySql9=new SqlClass();
	mySql9.setSqlId("UWManuHealthSql9");// ָ��ʹ�õ�Sql��id
	mySql9.setResourceName("uw.UWManuHealthSql"); //ָ��ʹ�õ�properties�ļ���
	mySql9.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
	var tSQL=mySql9.getString();
	
	 turnPage.strQueryResult  = easyQueryVer3(tSQL, 1, 0, 1);

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    return "";
  }

  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  var m = turnPage.arrDataCacheSet.length;
  
  //alert(m);
  //��ѯ�������
  for(var i=0; i<m; i++)
  {
  	var tContent ="";
 	var arrResult = "";
 	var tSQL;
//    tSQL = "select peitemname from lcpenoticeitem where 1=1 "
//	       + " and contno = '"+ document.all('ContNo').value +"' and prtseq = '"+turnPage.arrDataCacheSet[i][4]+"' order by peitemcode" ;
//	
	var mySql10=new SqlClass();
	mySql10.setSqlId("UWManuHealthSql10");// ָ��ʹ�õ�Sql��id
	mySql10.setResourceName("uw.UWManuHealthSql"); //ָ��ʹ�õ�properties�ļ���
	mySql10.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
	mySql10.addSubPara(turnPage.arrDataCacheSet[i][4]);//ָ������Ĳ���
	var tSQL=mySql10.getString();
	
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
		
//		var tSQL = "select customerno,customertype,pereason,pebeforecond,PEDate,(case when PrintFlag is not null then PrintFlag  else 'x' end),remark from lcpenotice "
//		            + " where contno='"+document.all('ContNo').value+"' "
//		            + " and prtseq='"+tPrtSeq+"' and customerno='"+tCustomerNo+"' "
//		            + " and customertype='"+tCustomerType+"' "
//		            ;
		
		var mySql11=new SqlClass();
		mySql11.setSqlId("UWManuHealthSql11");// ָ��ʹ�õ�Sql��id
		mySql11.setResourceName("uw.UWManuHealthSql"); //ָ��ʹ�õ�properties�ļ���
		mySql11.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
		mySql11.addSubPara(tPrtSeq);//ָ������Ĳ���
		mySql11.addSubPara(tCustomerNo);//ָ������Ĳ���
		mySql11.addSubPara(tCustomerType);//ָ������Ĳ���
		var tSQL=mySql11.getString();
		
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
//    var tSQL_Sub = "select peitemcode,peitemname from lcpenoticeitem "
//                 + " where contno='"+document.all('ContNo').value+"' "
//                 + " and prtseq = '"+tPrtSeq+"' ";
    
    var mySql12=new SqlClass();
	mySql12.setSqlId("UWManuHealthSql12");// ָ��ʹ�õ�Sql��id
	mySql12.setResourceName("uw.UWManuHealthSql"); //ָ��ʹ�õ�properties�ļ���
	mySql12.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
	mySql12.addSubPara(tPrtSeq);//ָ������Ĳ���
	var tSQL_Sub=mySql12.getString();
	
    arrResult=easyExecSql(tSQL_Sub);
   // alert(arrResult.length);
    var tAllCheckedItem = "";
    var tItemOther = "";
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
	if (verifyInput2() == false)
   return;
   
	 submitForm();
}

function unlockscreen()
{
	unlockScreen('lkscreen');  
}