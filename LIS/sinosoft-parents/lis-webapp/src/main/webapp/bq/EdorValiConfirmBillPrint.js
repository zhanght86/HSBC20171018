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
  var StartDate = fm.StartDate.value;
  var EndDate = fm.EndDate.value;
  var EdorType = fm.EdorType.value;
  var ManageCom = fm.ManageCom.value;
  var SaleChnl = fm.SaleChnl.value;

  if(StartDate == "" || EndDate == "")
  {
        alert("��������ʼ���ںͽ�������!");
        return;
  }

  if(ManageCom == "")
  {
       alert("��ѡ�����!");
       fm.EdorType.focus();
       return;
  }

//  if(!checkDate(StartDate)||!checkDate(EndDate))
//  {
//    showInfo.close();
//    alert("����Ч�����ڸ�ʽ��");
//    return;
//  }
//
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
       var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
       var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();  
        fm.fmtransact.value = "PRINT";
        fm.target = "f1print";
        document.getElementById("fm").submit();showInfo.close();
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
