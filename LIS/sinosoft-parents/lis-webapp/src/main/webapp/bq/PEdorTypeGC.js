//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPageNewBnfGrid = new turnPageClass();      //ȫ�ֱ���, �������˲�ѯ�����ҳ
var sqlresourcename = "bq.PEdorTypeGCInputSql";

function saveEdorTypeGC()
{

//У��������� jiaqiangli-check the rule of bnf-lot
        var sumLiveBnf = new Array();
        var sumDeadBnf = new Array();
        
        var nNewBnfCount = NewBnfGrid.mulLineCount;
        
	    if (nNewBnfCount <= 0) {
	       alert("�����˷�����Ϣ������Ϊ�գ�");
	       return false;
	    }

        for(var i=0;i<nNewBnfCount;i++)
        {
               if (typeof(sumLiveBnf[parseInt(NewBnfGrid.getRowColData(i,9))]) == "undefined")
               sumLiveBnf[parseInt(NewBnfGrid.getRowColData(i,9))] = 0;
               sumLiveBnf[parseInt(NewBnfGrid.getRowColData(i,9))] = sumLiveBnf[parseInt(NewBnfGrid.getRowColData(i,9))] + parseFloat(NewBnfGrid.getRowColData(i, 10))*100;
        }
        for (i=0; i<sumLiveBnf.length; i++)
      {
        if (typeof(sumLiveBnf[i])!="undefined"){sumLiveBnf[i]=parseFloat(sumLiveBnf[i])/100;}
        if (typeof(sumLiveBnf[i])!="undefined" && sumLiveBnf[i]>1)
        {
            alert("ע�⣺��������������˳�� " + i + " �����������Ϊ��" + sumLiveBnf[i]+ " ������100%�������ύ��");
            return false;
        }
        else if (typeof(sumLiveBnf[i])!="undefined" && sumLiveBnf[i]<1)
        {
            alert("ע�⣺��������������˳�� " + i + " �����������Ϊ��" + sumLiveBnf[i] + " ��С��100%�������ύ��");
            return false;
        }
      }
//У��������� jiaqiangli-check the rule of bnf-lot
//add by jiaqiangli ����ת����ȨҪ����������ص���Ϣ������Ϊ��
if(document.all("GetForm").value == "0") {
  for (var i=0;i<nNewBnfCount;i++) {
    if (NewBnfGrid.getRowColData(i, 17)==null || NewBnfGrid.getRowColData(i, 17)==""
       || NewBnfGrid.getRowColData(i, 18)==null || NewBnfGrid.getRowColData(i, 18)==""
       || NewBnfGrid.getRowColData(i, 19)==null || NewBnfGrid.getRowColData(i, 19)=="") {
       alert("�����˷����е�"+(i+1)+"�е�����ת����Ȩ��������Ϣ������Ϊ��");
       return false;
    }
    //add by jiaqiangli 2009-03-16 ���ӻ���������������һ�µ�У��
    if (NewBnfGrid.getRowColData(i, 4) != NewBnfGrid.getRowColData(i, 19) ) {
    	alert("��"+(i+1)+"�����������������������ʻ���һ��");
       return false;
    }
    //add by jiaqiangli 2009-03-16 ���ӻ���������������һ�µ�У��
  }
}
//add by jiaqiangli ����ת����ȨҪ����������ص���Ϣ������Ϊ��

    //<!-- XinYQ added on 2005-12-19 : END -->
    if (!verifyInput2()) return;
    //�Ѿ�����mulline��У����
    //if (!checkBankInfo()) return;
    //<!-- XinYQ added on 2005-12-19 : END -->
    
    //add by jiaqiangli 2009-04-08 mutline��verifyУ��
     if(!NewBnfGrid.checkValue2())
  	{
  		return;
  	 }
    //add by jiaqiangli 2009-04-08 mutline��verifyУ��

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
  document.all('fmtransact').value = "UPDATE||MAIN";
  fm.submit();

}

/**
 * ���±��鿴
 */
function showNotePad()
{
    var sMissionID, sSubMissionID, sOtherNo;
    try
    {
        sMissionID = top.opener.document.getElementsByName("MissionID")[0].value;
        sSubMissionID = top.opener.document.getElementsByName("SubMissionID")[0].value;
        sOtherNo = top.opener.document.getElementsByName("OtherNo")[0].value;
    }
    catch (ex) {}
    if (sMissionID == null || trim(sMissionID) == "" || sSubMissionID == null || trim(sSubMissionID) == "" || sOtherNo == null || trim(sOtherNo) == "")
    {
        alert("���棺�޷���ȡ����������ڵ�����š��鿴���±�ʧ�ܣ� ");
        return;
    }
    var sOpenWinURL = "../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp";
    var sParameters = "MissionID="+ sMissionID + "&SubMissionID="+ sSubMissionID + "&ActivityID=0000000003&PrtNo="+ sOtherNo + "&NoType=1";
    OpenWindowNew(sOpenWinURL + "&" + sParameters, "���������±��鿴", "left");
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
            top.opener.getEdorItemGrid();
        }
        catch (ex) {}
    }
}

/**
 * showCodeList �Ļص�����, �����ʻ���Ϣ¼������ʾ������
 */
 /*
function afterCodeSelect(sCodeListType, oCodeListField)
{
    if (sCodeListType == "GetLocation")
    {
        try
        {
            if (oCodeListField.value == "0")
            {
                document.all("BankInfo").style.display = "";
            }
            else
            {
                document.all("BankInfo").style.display = "none";
            }
        }
        catch (ex)
        {
            alert("���棺�����ʻ���Ϣ¼����ʾ/���س����쳣�� ");
        }
    } //CodeListType == EdorGetPayForm
}
*/

//<!-- XinYQ modified on 2005-11-08 : BGN -->
/*============================================================================*/

/*
 * ��ȡ��ʽ-����ת��-�����˻���Ϣ��ѯ
 */
function queryBankInfo()
{
    var QuerySQL, arrResult;
/*    QuerySQL = "select GetForm, GetBankCode, GetBankAccNo, GetAccName "
             +   "from LPPol "
             +  "where 1 = 1 "
             +     getWherePart("EdorType", "EdorType")
             +     getWherePart("EdorNo", "EdorNo")
             +     getWherePart("ContNo", "ContNo")
             +     getWherePart("PolNo", "PolNo");
    //alert(QuerySQL);
*/    
	var sqlid1="PEdorTypeGCInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(fm.EdorType.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.EdorNo.value);
	mySql1.addSubPara(fm.ContNo.value);
	mySql1.addSubPara(fm.PolNo.value);
	QuerySQL=mySql1.getString();
    
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("���棺��ѯ�ͻ������˻���Ϣ�����쳣�� ");
        return;
    }
    if (arrResult != null && trim(arrResult[0][1]) != "")
    {
        try
        {
            //��ȡ��ʽ
            document.getElementsByName("GetForm")[0].value = arrResult[0][0];
            showOneCodeName('GetLocation','GetForm','GetFormName');
            //��������
            document.getElementsByName("GetBankCode")[0].value = arrResult[0][1];
            showOneCodeName('Bank', 'GetBankCode', 'BankName');
            //�����ʻ�
            document.getElementsByName("GetBankAccNo")[0].value = arrResult[0][2];
            //�ʻ���
            if (trim(arrResult[0][3]) != "")
            {
                document.getElementsByName("GetAccName")[0].value = arrResult[0][3];
            }
            else
            {
            		if (fm.AppObj.value == 'G')
            		{
            			document.getElementsByName("GetAccName")[0].value = PolGrid.getRowColData(0, 3);
            		}    
            		else
            		{
            			document.getElementsByName("GetAccName")[0].value = PolGrid.getRowColData(1, 3);
        				}
        		}
        }
        catch (ex)
        {
            alert("���󣺲�ѯ�ͻ������˻���Ϣ�ɹ�, ����ʾ��ҳ��ʧ�ܣ� ");
            return;
        }
    } //arrResult[0][1]) != ""
    //��ѯ LCPol
    else
    {
/*        QuerySQL = "select GetForm, GetBankCode, GetBankAccNo, GetAccName "
                 +   "from LCPol "
                 +  "where 1 = 1 "
                 +     getWherePart("ContNo", "ContNo")
                 +     getWherePart("PolNo", "PolNo");
*/       
    var sqlid2="PEdorTypeGCInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	mySql2.addSubPara(fm.PolNo.value);
	QuerySQL=mySql2.getString();
       
        try
        {
            arrResult = easyExecSql(QuerySQL, 1, 0);
        }
        catch (ex)
        {
            alert("���棺��ѯ�ͻ������˻���Ϣ�����쳣�� ");
            return;
        }
        try
        {
            //��ȡ��ʽ
            document.getElementsByName("GetForm")[0].value = arrResult[0][0];
            showOneCodeName('GetLocation','GetForm','GetFormName');
            //��������
            document.getElementsByName("GetBankCode")[0].value = arrResult[0][1];
            showOneCodeName('Bank', 'GetBankCode', 'BankName');
            //�����ʻ�
            document.getElementsByName("GetBankAccNo")[0].value = arrResult[0][2];
            //�ʻ���
            if (trim(arrResult[0][3]) != "")
            {
                document.getElementsByName("GetAccName")[0].value = arrResult[0][3];
            }
            else
            {
                //�ŵ�û��Ͷ���ˣ����Ե�һ�м�Ϊ�����ˣ�û�еڶ������� zhangtao 2007-02-05
                if (fm.AppObj.value == 'G')
                {
                	document.getElementsByName("GetAccName")[0].value = PolGrid.getRowColData(0, 3);
                }
                else
                {
                	document.getElementsByName("GetAccName")[0].value = PolGrid.getRowColData(1, 3);
								}	
            }
        }
        catch (ex)
        {
            alert("���󣺲�ѯ�ͻ������˻���Ϣ�ɹ�, ����ʾ��ҳ��ʧ�ܣ� ");
            return;
        }
    }
    //�����ʾ
    if (arrResult != null && trim(arrResult[0][0]) == "0")
    {
        try
        {
            document.all("BankInfo").style.display = "";
        }
        catch (ex) {}
    }
    else
    {
        try
        {
            document.all("BankInfo").style.display = "none";
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/*
 * ��ȡ��ʽ-����ת��-�����˻���Ϣ��¼У��
 */
function checkBankInfo()
{
    var sGetForm = document.getElementsByName("GetForm")[0].value;
    var sGetBankCode = document.getElementsByName("GetBankCode")[0].value;
    var sGetBankAccNo = document.getElementsByName("GetBankAccNo")[0].value;
    var sGetAccName = document.getElementsByName("GetAccName")[0].value;
    if (sGetForm == null)
    {
        alert("���棺�޷���ȡ��ȡ��ʽ��Ϣ�� ");
        return false;
    }
    else if (sGetForm == "0")
    {
        if (sGetBankCode == null || trim(sGetBankCode) == "" || sGetBankAccNo == null || trim(sGetBankAccNo) == "" || sGetAccName == null || trim(sGetAccName) == "")
        {
            alert("��ȡ��ʽΪ����ת��, ����¼�뿪�����С������ʻ����ʻ����� ");
            return false;
        }
    }
    return true;
}

/*============================================================================*/
//<!-- XinYQ modified on 2005-11-08 : END -->


//��ȡ����ȡ��ʽ��Ϣ
//function initBankInfo()
//{
//  alert(0);
//   var tEdorNo = document.all('EdorNo').value;
//   var tPolNo = document.all('PolNo').value;
//   var showStr = "���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
//   var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//   showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
//   var strSql = "Select getform,getbankcode,getbankaccno,getaccname From lppol where 1 =1 and PolNo='"+tPolNo+"' and EdorNo='"+tEdorNo+"'";
//   alert(strSql);
//
//   var arrResult = easyExecSql(strSql, 1, 0);
//   if (arrResult == null)
//   {
//       alert("û����Ӧ�ı�����Ϣ");
//       showInfo.close();
//         document.all('GetBankCode').value = "";
//         document.all('GetBankAccNo').value = "";
//         document.all('GetAccName').value = "";
//         document.all('GetForm').value = "";
//   }
//   else
//   {
//              BankInfo.style.display = "";
//          document.all('GetBankCode').value = arrResult[0][1];
//              document.all('GetBankAccNo').value = arrResult[0][2];
//              document.all('GetAccName').value = arrResult[0][3];
//              document.all('GetForm').value = arrResult[0][0];
//        showInfo.close();
//   }
//   //turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);
//   //document.all('fmtransact').value = "QUERY||PayLocation";
//   //fm.submit();
//      //document.all('GetAccName').value=strSql;
//}

//У�鲿��
function verify() {
  //if (trim(fm.BankAccNo.value)!=trim(fm.BankAccNoAgain.value)) {
   // alert("�����˺Ų�һ�£���ȷ�ϣ�");
    //return false;
  //}
  if (trim(fm.GetBankCode.value)=="0101") {
    if (trim(fm.GetBankCode.value).length!=19) {
      alert("�������е��˺ű�����19λ�����֣����һ���Ǻţ�*����Ҫ��");
      return false;
    }
  }
  //if(trim(document.all('AppntName').value)!=trim(document.all('AccName').value))
  //{
  //        alert("�ʻ�����Ͷ������������ͬ!");
  //return false;
  //    }

 if(trim(document.all('AppntName').value)!=trim(document.all('GetAccName').value)){
    if (!confirm("�˻����ƺ�Ͷ�������Ʋ�һ������ȷ�������ύ����ȡ���򷵻ظ��ģ�"))
      return false;
  }

  return true;
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

function Edorquery()
{
    var ButtonFlag = top.opener.document.all('ButtonFlag').value;
    if(ButtonFlag!=null && ButtonFlag=="1")
    {
       divEdorquery.style.display = "none";
    }
    else
    {
        divEdorquery.style.display = "";
    }
}

function queryCustomerInfo()
{
  var tContNo=top.opener.document.all('ContNo').value;
    var strSQL="";
    if(tContNo!=null || tContNo !='')
      {
/*      strSQL = " Select a.appntno,'Ͷ����',a.appntname,a.appntsex||'-'||sex.codename,a.appntbirthday,a.idtype||'-'||x.codename,a.idno From lcappnt a "
             + " Left Join (Select code,codename From ldcode Where codetype='idtype') x On x.code = a.idtype "
             + " Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = a.appntsex  Where contno='"+tContNo+"'"
             + " Union"
             + " Select i.insuredno,'������',i.name,i.Sex||'-'||sex.codename,i.Birthday,i.IDType||'-'||xm.codename,i.IDNo From lcinsured i "
             + " Left Join (Select code,codename From ldcode Where codetype='idtype') xm On xm.code = i.idtype "
             + " Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = i.sex Where contno='"+tContNo+"'";
*/    
    var sqlid3="PEdorTypeGCInputSql3";
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
    //prompt("strSQL",strSQL);
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

function initSelQuery()
{
     var tEdorNo=top.opener.document.all('EdorNo').value;
     var tEdorType=top.opener.document.all('EdorType').value;
     var tPolNo=top.opener.document.all('PolNo').value;
//     var strSql = "Select AppntNo,AppntName,Prem,Amnt,PayMode,GetBankCode,GetBankAccNo,GetAccName,PayLocation,(select codename from ldcode where codetype = 'getlocation' and trim(code) = trim(getForm)),insuredname From lcpol where 1 =1 and PolNo='"+tPolNo+"'";
   
    var strSql = "";
    var sqlid4="PEdorTypeGCInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(tPolNo);//ָ������Ĳ���
	strSql=mySql4.getString();
   
   var arrResult = easyExecSql(strSql, 1, 0);
   
   //prompt("strSql",strSql);

   if (arrResult == null)
   {
       alert("û����Ӧ�ı�����Ϣ");
   }
   else
   {
       displayInfo(arrResult);
   }
}

function displayInfo(arrResult)
{
 try{
   document.all('AppntNo').value = arrResult[0][0];
   document.all('AppntName').value = arrResult[0][1];
   document.all('Prem').value = arrResult[0][2];
   document.all('Amnt').value = arrResult[0][3];
   document.all('PayMode').value = arrResult[0][4];
   document.all('BankCode_S').value = arrResult[0][5];
   document.all('BankAccNo_S').value = arrResult[0][6];
   document.all('AccName_S').value = arrResult[0][7];
   //document.all('GetAccName').value = arrResult[0][10];
   document.all('PayLocation_S').value = arrResult[0][8];
   document.all('GerForm_S').value = arrResult[0][9];
   }catch(ex){
        alert("error!");
   }
}

function newGetType()
{
//   var strsql = "select GetForm,GetBankCode,GetBankAccNo,GetAccName From lppol where 1 =1 and PolNo='"+top.opener.document.all('PolNo').value+"' and EdorNo = '"+top.opener.document.all('EdorNo').value+"' and EdorType = '"+top.opener.document.all('EdorType').value+"'";
   
    var strSql = "";
    var sqlid5="PEdorTypeGCInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	
	mySql5.addSubPara(top.opener.document.all('PolNo').value);//ָ������Ĳ���
	mySql5.addSubPara(top.opener.document.all('EdorNo').value);
	mySql5.addSubPara(top.opener.document.all('EdorType').value);
	strSql=mySql5.getString();
	//begin zbx 20110513
   var aResult = easyExecSql(strSql,1,0);
   //end zbx 20110513
   if(aResult != null){
      document.all('GetForm').value = aResult[0][0];
      //if(aResult[0][0] == "0"){
         //BankInfo.style.display = "";
         //document.all('GetBankCode').value = aResult[0][1];
         //document.all('GetBankAccNo').value = aResult[0][2];
         //document.all('GetAccName').value = aResult[0][3];
      //}
   }
}

function getInsuredCodeList()
{
    var sInsuredCodeList = "";
/*    var QuerySQL = "select * "
                 //������
                 +   "from (select a.InsuredNo as CustomerNo, "
                 +                "a.Name as CustomerName "
                 +           "from LCInsured a "
                 +          "where 1 = 1 "
                 +             getWherePart("a.ContNo", "ContNo")
                 +            "and a.InsuredNo = "
                 +                "(select InsuredNo "
                 +                   "from LCPol "
                 +                  "where 1 = 1 "
                 +                     getWherePart("ContNo", "ContNo")
                 +                     getWherePart("PolNo", "PolNo")
                 +                ") "
                 //�ڶ�������
                 +         "union "
                 +         "select b.InsuredNo as CustomerNo, "
                 +                "b.Name as CustomerName "
                 +           "from LCInsured b "
                 +          "where 1 = 1 "
                 +             getWherePart("b.ContNo", "ContNo")
                 +            "and b.InsuredNo in "
                 +                "(select c.CustomerNo "
                 +                   "from LCInsuredRelated c "
                 +                  "where 1 = 1 "
                 +                     getWherePart("c.PolNo", "PolNo")
                 +                   "and c.MainCustomerNo = "
                 +                       "(select InsuredNo "
                 +                          "from LCPol "
                 +                         "where 1 = 1 "
                 +                            getWherePart("ContNo", "ContNo")
                 +                            getWherePart("PolNo", "PolNo")
                 +                       "))) "
                 //������������ѯ�ڶ�������
                 //+         "union "
                 //+         "select b.CustomerNo as CustomerNo, "
                 //+                "b.Name as CustomerName "
                 //+           "from LCInsuredRelated b "
                 //+          "where 1 = 1 "
                 //+             getWherePart("b.PolNo", "PolNo")
                 //+            "and b.MainCustomerNo = "
                 //+                "(select InsuredNo "
                 //+                   "from LCPol "
                 //+                  "where 1 = 1 "
                 //+                     getWherePart("ContNo", "ContNo")
                 //+                     getWherePart("PolNo", "PolNo")
                 //+                ")) "
                 +  "order by CustomerNo asc";
    //alert(QuerySQL);
*/    
    var QuerySQL = "";
    var sqlid6="PEdorTypeGCInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
	mySql6.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	mySql6.addSubPara(fm.ContNo.value);
	mySql6.addSubPara(fm.PolNo.value);
	mySql6.addSubPara(fm.ContNo.value);
	mySql6.addSubPara(fm.PolNo.value);
	mySql6.addSubPara(fm.ContNo.value);
	mySql6.addSubPara(fm.PolNo.value);
	QuerySQL=mySql6.getString();
    
    try
    {
        sInsuredCodeList = easyQueryVer3(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("���棺��ѯ������������Ϣ�����쳣�� ");
        return;
    }
    return sInsuredCodeList;
}

//add by jiaqiangli 2008-08-21
/**
 * ��ѯ������������Ϣ
 */
function queryNewBnfGrid()
{
/*    var QuerySQL = "select a.InsuredNo, "
                 +        "(select Name "
                 +           "from LCInsured "
                 +          "where 1 = 1 "
                 +            "and ContNo = a.ContNo "
                 +            "and InsuredNo = a.InsuredNo), "
                 +        "a.BnfType, "
                 +        "a.Name, "
                 +        "'', "
                 +        "a.IDType, "
                 +        "a.IDNo, "
                 +        "a.RelationToInsured, "
                 +        "a.BnfGrade, "
                 +        "a.BnfLot, "
                 +        "'', "    //����ʹ��һ��
                 +        "a.sex, "
                 +        "a.birthday, "
                 +        "a.postaladdress, "
                 +        "a.zipcode, "
                 +        "a.remark,"
                 //add by jiaqiangli 2008-08-25 ��������
                 +        "a.bankcode,"
                 +        "a.bankaccno,"
                 +        "a.accname "
                 +   "from LPBnf a "
                 +  "where 1 = 1 and bnftype = '0' "
                 +     getWherePart("a.EdorNo", "EdorNo")
                 +     getWherePart("a.EdorType", "EdorType")
                 +     getWherePart("a.ContNo", "ContNo")
                 +     getWherePart("a.PolNo", "PolNo");
    //prompt("QuerySQL",QuerySQL);
*/    
    var QuerySQL = "";
    var sqlid7="PEdorTypeGCInputSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
	mySql7.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
	mySql7.addSubPara(fm.EdorType.value);
	mySql7.addSubPara(fm.ContNo.value);
	mySql7.addSubPara(fm.PolNo.value);
	QuerySQL=mySql7.getString();
    
    try
    {
        turnPageNewBnfGrid.pageDivName = "divTurnPageNewBnfGrid";
        turnPageNewBnfGrid.queryModal(QuerySQL, NewBnfGrid);
    }
    catch (ex)
    {
        alert("���棺��ѯ��������Ϣ�����쳣�� ");
        return;
    }
    //�Ȳ�lpbnf����Ϣ���ѯlcbnf����Ϣ
    if (NewBnfGrid.mulLineCount <= 0) {
/*    	QuerySQL = "select a.InsuredNo, "
                 +        "(select Name "
                 +           "from LCInsured "
                 +          "where 1 = 1 "
                 +            "and ContNo = a.ContNo "
                 +            "and InsuredNo = a.InsuredNo), "
                 +        "a.BnfType, "
                 +        "a.Name, "
                 +        "'', "
                 +        "a.IDType, "
                 +        "a.IDNo, "
                 +        "a.RelationToInsured, "
                 +        "a.BnfGrade, "
                 +        "a.BnfLot, "
                 +        "'', "    //����ʹ��һ��
                 +        "a.sex, "
                 +        "a.birthday, "
                 +        "a.postaladdress, "
                 +        "a.zipcode, "
                 +        "a.remark,"
                 //add by jiaqiangli 2008-08-25 ��������
                 +        "a.bankcode,"
                 +        "a.bankaccno,"
                 +        "a.accname "
                 +   "from LcBnf a "
                 +  "where 1 = 1 and bnftype = '0' "
                 +     getWherePart("a.ContNo", "ContNo")
                 +     getWherePart("a.PolNo", "PolNo");
	    //prompt("QuerySQL",QuerySQL);
*/	    
    var sqlid8="PEdorTypeGCInputSql8";
	var mySql8=new SqlClass();
	mySql8.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
	mySql8.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	mySql8.addSubPara(fm.PolNo.value);
	QuerySQL=mySql8.getString();
	    
	    try
	    {
	        turnPageNewBnfGrid.pageDivName = "divTurnPageNewBnfGrid";
	        turnPageNewBnfGrid.queryModal(QuerySQL, NewBnfGrid);
	    }
	    catch (ex)
	    {
	        alert("���棺��ѯ��������Ϣ�����쳣�� ");
	        return;
	    }       
    }
}
//add by jiaqiangli 2008-08-21



function confirmSecondInput1(aObject,aEvent)
{
 {
  if(theFirstValue!="")
  {
   theSecondValue = aObject.value;
   if(theSecondValue=="")
   {
    alert("���ٴ�¼�룡");
    aObject.value="";
    aObject.focus();
    return;
   }
   if(theSecondValue==theFirstValue)
   {
    aObject.value = theSecondValue;
    theSecondValue="";
    theFirstValue="";
    return;
   }
   else
   {
    alert("����¼����������������¼�룡");
    theFirstValue="";
    theSecondValue="";
    aObject.value="";
    aObject.focus();
    return;
   }
  }
  else
  {
   theFirstValue = aObject.value;
   theSecondValue="";
   if(theFirstValue=="")
   {
    return;
   }
   aObject.value="";
   aObject.focus();
   return;
  }
 }
}