//���ļ��а����ͻ�����Ҫ����ĺ������¼�

//�������ƣ�EdorBillPrint.js
//�����ܣ��嵥��ӡ
//�������ڣ�2005-08-08 15:39:06
//������  ��liurx
//���¼�¼��  ������    ��������     ����ԭ��/����


var showInfo;
var turnPage = new turnPageClass();


//<!-- XinYQ added on 2006-04-03 : ��ȫ�����ͳ���嵥��ӡ : BGN -->
/*============================================================================*/

/**
 * ����ͨ�ñ�ȫ�û���ѯ
 */
function queryEdorUser()
{
    var sManageCom, sUserCode;
    try
    {
        sManageCom = document.getElementsByName("ManageCom")[0].value;
        sUserCode = document.getElementsByName("UserCode")[0].value;
    }
    catch (ex) {}
    //����ҳ����ʾ
    var sOpenWinURL = "../sys/UsrCommonQueryMain.jsp";
    var sParameters = "ManageCom=" + sManageCom + "&UserCode=" + sUserCode;
    OpenWindowNew(sOpenWinURL + "?" + sParameters, "��ȫ�û���ѯ", "left");
}

/*============================================================================*/

/**
 * ����ͨ�ñ�ȫ�û���ѯ��㷵�ظ�ֵ
 */
function afterQuery(arrResult)
{
    if(arrQueryResult != null)
    {
        try { document.getElementsByName("UserCode")[0].value = arrResult[0][0]; } catch(ex) {}
    }
}

/*============================================================================*/
//<!-- XinYQ added on 2006-04-03 : ��ȫ�����ͳ���嵥��ӡ : END -->


//�ύ�����水ť��Ӧ����
function printBill()
{
  var i = 0;
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
  var StartDate = fm.StartDate.value;
  var EndDate = fm.EndDate.value;
  var EdorDate = fm.EdorDate.value;
  var BillType = fm.BillType.value;
  var ManageCom = fm.ManageCom.value;
  var SaleChnl = fm.SaleChnl.value;
  if(SaleChnl == "")
  {
       showInfo.close();
       alert("��ѡ��������");
       fm.SaleChnl.focus();
       return;
  }
  if(BillType == "")
  {
       showInfo.close();
       alert("��ѡ���嵥���ͣ�");
       fm.BillType.focus();
       return;
  }
  if(StartDate == "" || EndDate == "")
  {
        showInfo.close();
        alert("��������ʼ���ںͽ�������!");
        return;
  }
  if(!checkDate(StartDate)||!checkDate(EndDate))
  {
    showInfo.close();
    alert("����Ч�����ڸ�ʽ��");
    return;
  }
  if(fm.CTFlag.value == "1")
  {
    if(fm.DateType.value == "")
    {
       showInfo.close();
       alert("��ѡ��ͳ����ֹ�ڵ����ͣ�");
       fm.DateType.focus();
       return ;
    }
    if(fm.DateType.value != "1"&&fm.DateType.value != "2")
    {
        showInfo.close();
        alert("ͳ����ֹ�ڵ���������");
        fm.DateType.focus();
        return ;
    }
  }
  //��ֵͳ���嵥
  if(SaleChnl == "0" && BillType == "006")
  {
     if(EdorDate == "")
     {
        showInfo.close();
        alert("������ͳ������!");
        return;
     }
     if(!checkDate(EdorDate))
     {
       showInfo.close();
       alert("����Ч�����ڸ�ʽ��");
       return;
     }
  }
  var startValue=StartDate.split("-");
  var dateStartDate = new Date(startValue[0],startValue[1]-1,startValue[2]);
  var endValue=EndDate.split("-");
  var dateEndDate = new Date(endValue[0],endValue[1]-1,endValue[2]);
  if(dateStartDate.getTime() > dateEndDate.getTime())
  {
        showInfo.close();
        alert("ͳ�����ڲ�������ͳ��ֹ�ڣ�");
        return;
  }
  else
  {
        //
        fm.fmtransact.value = "PRINT";
        fm.target = "f1print";
        fm.submit();
        showInfo.close();
  }
}



//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  //showInfo.close();
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
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();


  }
}

function showQuery()
{
    var tBillType = fm.BillType.value;
    var tCodeType = fm.ChnlType.value;

    //��ʼ�����п�
    divDate.style.display = '';
    divBank.style.display = 'none';
    divDateType.style.display = 'none';
    divErrorReportLayer.style.display = 'none';
    divPayGet.style.display = 'none';
    divEdorTypeTitle.style.display = 'none';
    divEdorType.style.display = 'none';
    divMaxBill.style.display = 'none';
    fm.CTFlag.value = "0";

    if(tBillType!=null && tBillType!="")
    {
//        var strSql = "select trim(othersign) from ldcode "
//                      + " where codetype = '"+tCodeType+"' "
//                      + " and code = '"+tBillType+"'";
//        
        var sqlid1="EdorBillPrintSql1";
     	var mySql1=new SqlClass();
     	mySql1.setResourceName("bq.EdorBillPrintSql");
     	mySql1.setSqlId(sqlid1); //ָ��ʹ��SQL��id
     	mySql1.addSubPara(tCodeType);//ָ���������
     	mySql1.addSubPara(tBillType);
     	var strSql = mySql1.getString();
        
        var brr = easyExecSql(strSql);
        if(brr)
        {
            //ldcode����othersign:
            //Ϊ1����Ҫ��д����������Ϣ
            //Ϊ2����Ҫѡ����������
            //Ϊ3�Ķ���Ҫ
            if(brr[0][0]=="1")
            {
                divBank.style.display = '';
            }
            else if(brr[0][0]=="2")
            {
                divDateType.style.display = '';
                fm.CTFlag.value = "1";
                fm.DateType.value = "1";
                fm.DateTypeName.value = "ȷ�����ڣ��ս�";
            }else if(brr[0][0]=="3")
            {
                divBank.style.display = '';
                divDateType.style.display = '';
                fm.CTFlag.value = "1";
                fm.DateType.value = "1";
                fm.DateTypeName.value = "ȷ�����ڣ��ս�";
            }
        }
    }

    //<!-- XinYQ added on 2006-04-03 : ��ȫ�����ͳ���嵥��ӡ : BGN -->
    var sSaleChnl, sBillType;
    try
    {
        sSaleChnl = document.getElementsByName("SaleChnl")[0].value;
        sBillType = document.getElementsByName("BillType")[0].value;
    }
    catch (ex) {}

    if (sSaleChnl != null && trim(sSaleChnl) == "0" && sBillType != null && trim(sBillType) == "004")
    {
        try { document.all("divErrorReportLayer").style.display = ""; } catch (ex) {}
        divEdorTypeTitle.style.display = '';
        divEdorType.style.display = '';
    }
    //<!-- XinYQ added on 2006-04-05 : ��ȫ�����ͳ���嵥��ӡ : END -->
    //�ո���ҵ��ͳ��
    if (sSaleChnl != null && trim(sSaleChnl) == "0" && sBillType != null && trim(sBillType) == "005")
    {
        try { document.all("divPayGet").style.display = ""; } catch (ex) {}
        divEdorTypeTitle.style.display = '';
        divEdorType.style.display = '';
    }
    //��ֵͳ�ƣ�����
    if (sSaleChnl != null && trim(sSaleChnl) == "0" && sBillType != null && trim(sBillType) == "006")
    {
        divDate.style.display = 'none';
        divEdorTypeTitle.style.display = '';
        divEdorType.style.display = '';
        divMaxBill.style.display = '';
        fm.EdorDate.value = getCurrentDate('-');//Ĭ��Ϊ����
    }
    //��ֵͳ�ƣ�����
    if (sSaleChnl != null && trim(sSaleChnl) == "0" && sBillType != null && trim(sBillType) == "007")
    {
        divEdorTypeTitle.style.display = '';
        divEdorType.style.display = '';
    }
}

function showChnlType()
{
    var SaleChnl = fm.SaleChnl.value;
    var ChnlSel = fm.ChnlSel.value;
    if(SaleChnl!=ChnlSel)
    {
        fm.ChnlSel.value = SaleChnl;    //��¼ÿ����ѡ�����������������������嵥����
        document.all('BillType').value = '';
        document.all('BillTypeName').value = '';
    }

    switch(SaleChnl)
    {
        case "1":  fm.ChnlType.value = "bqbillperson";
                    break;
        case "2":  fm.ChnlType.value = "bqbillgrp";
                    break;
        case "3":  fm.ChnlType.value = "bqbillbank";
                    break;
        case "5":  fm.ChnlType.value = "bqbillphone";
                    break;
        case "0":  fm.ChnlType.value = "bqbillall";
                    break;
        default:    fm.ChnlType.value = "";
        break;
    }
}
function initBillType(cObjCode,cObjName)
{
    var ChnlType = fm.ChnlType.value;
    if(ChnlType == null||ChnlType == "")
    {
        alert("����ѡ����������ѡ���嵥��");
        fm.SaleChnl.focus();
        return;
    }
    showCodeList(ChnlType,[cObjCode,cObjName],[0,1]);
}
function onKeyUpBillType(cObjCode,cObjName)
{
    var ChnlType = fm.ChnlType.value;
    if(ChnlType == null||ChnlType == "")
    {
        alert("����ѡ����������ѡ���嵥��");
        fm.SaleChnl.focus();
        return;
    }
    showCodeListKey(ChnlType,[cObjCode,cObjName],[0,1]);
}
//��Ϊ��̨ȡĬ����ֹ�ڣ��˺�����������
function initDate()
{
        var today = new Date();
        var thisDay = 25;
        var preDay = 26;
        var tYear = today.getYear();
        var preYear = tYear;
        var thisMonth = today.getMonth()+1;
        var preMonth = thisMonth-1;
        if(thisMonth == 1)
        {
           preMonth = thisMonth;
           preDay = 1;
        }
        if(thisMonth == 12)
        {
           thisDay = 31;
        }
        document.all('StartDate').value = preYear+"-"+preMonth+"-"+preDay;
        document.all('EndDate').value = tYear+"-"+thisMonth+"-"+thisDay;

}

function isLeap(tYear)
{
    return (tYear%4)==0 ? ((tYear%100)==0?((tYear%400)==0?true:false):true):false;
}
function getMonthLength(tYear,tMonth)
{
    if(tMonth > 12 || tMonth < 1)
        return 0;
    var MONTH_LENGTH = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
    var LEAP_MONTH_LENGTH = new Array(31,29,31,30,31,30,31,31,30,31,30,31);

    return isLeap(tYear) ? LEAP_MONTH_LENGTH[tMonth-1] : MONTH_LENGTH[tMonth-1];
}
/**
 * ����У�麯��
 �� ������Common.js�ж��������ڸ�ʽ��У�飬��������������˻�ֱ�Ӱ�"2006-05-31"��Ϊ"2006-04-31"����ѯ,���϶��������ܳ����·ݳ��ȵ�У�顣
 * �����������ַ���
 * added by liurx 2006-05-25
 */
function checkDate(tDate)
{
    var dateValue;
    var tYear;
    var tMonth;
    var tDay;
    if(isDate(tDate))
    {
        dateValue = tDate.split("-");
        tYear = eval(dateValue[0]);
        tMonth = eval(dateValue[1]);
        tDay = eval(dateValue[2]);
        if(tDay > getMonthLength(tYear,tMonth))
        {
            return false;
        }
        return true;
    }
    else if(isDateN(tDate))
    {
        dateValue = new Array();
        dateValue[0]=tDate.substring(0, 4);
        dateValue[1]=tDate.substring(4, 6);
        dateValue[2]=tDate.substring(6, 8);

        tYear = eval(dateValue[0]);
        tMonth = eval(dateValue[1]);
        tDay = eval(dateValue[2]);
        if(tDay > getMonthLength(tYear,tMonth))
        {
            return false;
        }
        return true;
    }
    else if(isDateI(tDate))
    {
        dateValue = tDate.split("/");
        tYear = eval(dateValue[0]);
        tMonth = eval(dateValue[1]);
        tDay = eval(dateValue[2]);
        if(tDay > getMonthLength(tYear,tMonth))
        {
            return false;
        }
        return true;
    }
    else
    {
        return false;
    }
}