//�������ƣ�PEdorFormPrint.js
//�����ܣ���ȫ����������ӡ����̨
//�������ڣ�2006-09-25 09:10:00
//������  ��wangyan
//���¼�¼��  ������    ��������      ����ԭ��/����
//���ļ��а����ͻ�����Ҫ����ĺ������¼�


var showInfo;
var turnPage = new turnPageClass();
var sqlresourcename = "acc.DayreportFormPrintInputSql";

//��������ѡ������
function initBillType(cObjCode,cObjName)
{
  var ChnlType = 'reporttype';
  //var ChnlType = fm.ChnlType.value;
  /*
    if(ChnlType == null||ChnlType == "")
    {
      alert("����ѡ����������ѡ�񱨱�");
      fm.SaleChnl.focus();
        return;
    }
    */
    return showCodeList(ChnlType,[cObjCode,cObjName],[0,1],null,null,null,null,'230');
}
function onKeyUpBillType(cObjCode,cObjName)
{

    var ChnlType = 'reporttype';
    /*
    if(ChnlType == null||ChnlType == "")
    {
      alert("����ѡ����������ѡ�񱨱�");
      fm.SaleChnl.focus();
      return;
    }
    */
    return showCodeListKey(ChnlType,[cObjCode,cObjName],[0,1],null,null,null,null,'230');
}

//����֪ͨ�����ͷ������ض���sql��ѯ���
function getSql(tNoticeType)
{
   var strSql = "";
   switch(tNoticeType)
   {
      case "tl_008":
      var mySql=new SqlClass();
      	mySql.setResourceName(sqlresourcename);
        mySql.setSqlId("DayreportFormPrintInputSql2");
        mySql.addSubPara(document.all('StartDate').value);
        mySql.addSubPara(document.all('EndDate').value);
      	strSql = mySql.getString();
      break;
      default:    
          var mySql=new SqlClass();
          	mySql.setResourceName(sqlresourcename);
        	mySql.setSqlId("DayreportFormPrintInputSql1");
        	mySql.addSubPara(document.all('StartDate').value);
        	mySql.addSubPara(document.all('EndDate').value);
              strSql = mySql.getString();
      break;
   }
   return strSql;
}

//��ѯ���Դ�ӡ�ı���
function queryNotice()
{
  var i = 0;
  //var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  //var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no; resizable=no;alwaysRaised:yes;");
  var StartDate = fm.StartDate.value;
  var EndDate = fm.EndDate.value;
  var BillType = fm.BillType.value;
 // var ManageCom = fm.ManageCom.value;
  //var SaleChnl = fm.SaleChnl.value;
 // var RiskFlag = fm.RiskFlag.value;
  //var PayIntv = fm.PayIntv.value;
 // var Statistic = fm.Statistic.value;
  //var RiskCode = fm.RiskCode.value;
 // var Period = fm.Period.value;

  if(BillType == "")
  {
       //showInfo.close();
       alert("��ѡ�񱨱����ͣ�");
       fm.BillType.focus();
       return;
  }
  if(StartDate == "" || EndDate == "")
  {
      //showInfo.close();
      alert("��������ʼ���ںͽ�������!");
      return;
  }
  if(!checkDate(StartDate)||!checkDate(EndDate))
  {
    //showInfo.close();
    alert("����Ч�����ڸ�ʽ��");
    return;
  }

  var startValue=StartDate.split("-");
  var dateStartDate = new Date(startValue[0],startValue[1]-1,startValue[2]);
  var endValue=EndDate.split("-");
  var dateEndDate = new Date(endValue[0],endValue[1]-1,endValue[2]);
  if(dateStartDate.getTime() > dateEndDate.getTime())
  {
      //showInfo.close();
      alert("ͳ�����ڲ�������ͳ��ֹ�ڣ�");
      return;
  }
  var tFlag = fm.Flag.value;
  if(tFlag=="1")
  {
        var DATEDIFF = 31;
        if(startValue[1] == 11 && endValue[1] == 12)
        {
          DATEDIFF = 36;
        }
        var day = dateDiff(dateStartDate,dateEndDate,"D");
        if(day>DATEDIFF)
        {
          //showInfo.close();
          alert("���ڵ��±���ͳ�����ں�ֹ��ֻ�����һ���£�");
          return ;
        }
  }
  if(fm.CTFlag.value == "1")
  {
    if(fm.DateType.value == "")
    {
       //showInfo.close();
       alert("��ѡ��ͳ����ֹ�ڵ����ͣ�");
       fm.DateType.focus();
       return ;
    }
    if(fm.DateType.value != "1"&&fm.DateType.value != "2")
    {
      //showInfo.close();
      alert("ͳ����ֹ�ڵ���������");
      fm.DateType.focus();
      return ;
    }
  }

    strSql = getSql(BillType);
    var brr = easyExecSql(strSql);
    //showInfo.close();
    if(brr)
    {
      initNoticeGrid(BillType);
      turnPage.queryModal(strSql,NoticeGrid);
    }
    else
    {
      alert("û��Ҫ��ӡ�ı��� ");
      initNoticeGrid(BillType);
      return ;
    }
}

// ����ʱ��ѯ֪ͨ�������б�(֮���Ը�Ϊ�������������Ϊ�˴�ӡ�귵��ʱ���²�ѯ������У�֮������Ӻ��������ǵ���ԭ���ĺ���������Ϊ���غ�鲻����¼ʱ�����Ի������û���ɲ���)
function easyQueryClick()
{
    var BillType = fm.BillType.value;
    var strSql = "";
    if(BillType == null || BillType == "")
    {
      alert("��ѡ�񱨱����ͣ�");
      fm.BillType.focus();
      return;
    }
    if(fm.StartDate.value == "" || fm.EndDate.value == "")
    {
      alert("��������ʼ���ںͽ������ڣ�");
      return;
    }

    strSql = getSql(BillType);
    var brr = easyExecSql(strSql);
    if(brr)
    {
      initNoticeGrid(BillType);
      turnPage.queryModal(strSql,NoticeGrid);
    }
    else
    {
      initNoticeGrid(BillType);
      return ;
    }
}

//��ӡѡ��ı����¼
function printNotice()
{
	var BillType = fm.BillType.value;
  var i = 0;
  var showStr="���ڴ�ӡ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  var tSel = NoticeGrid.getSelNo();
  if( tSel == 0 || tSel == null )
  {
    showInfo.close();
    alert( "����ѡ��һ����¼!" );
  }
  else
  {
    var sRiskCode;
    var sInsuAccNo;
    var sStartDate;
    try
    {
      //sRiskCode = NoticeGrid.getRowColData(tSel-1, 1);
      sInsuAccNo = NoticeGrid.getRowColData(tSel-1, 1);
      sStartDate = NoticeGrid.getRowColData(tSel-1, 2);
    }
    catch (ex) {}
      if(BillType != "tl_008")
      {
      fm.action = "./DayreportFormPrintSave.jsp?InsuAccNo="+sInsuAccNo+"&StartDate="+sStartDate;
      fm.target="f1print";
      document.getElementById("fm").submit();
      showInfo.close();
      }
      else
      {
      fm.action = "./DayreportFormPrintSave.jsp?InsuAccNo="+sInsuAccNo+"&StartDate="+fm.StartDate.value+"&EndDate="+fm.EndDate.value;
      fm.target="f1print";
      document.getElementById("fm").submit();
      showInfo.close();		
      }
   }
  
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
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
}

//��ʼ��������������ȡ��λ
function initManageCom()
{
  if(comcode.substring(0,6) !=null && comcode.substring(0,6) != "")
  {
      var i,j,m,n;
      var returnstr;
      var tTurnPage = new turnPageClass();
//      var strSQL = "select comcode,name from ldcom where comcode like '"+comcode.substring(0,6)+"%%' order by comcode";
///////////////////////add by luzhe on 20070914///////////////////////
//          var mySql=new SqlClass();
//              mySql.setJspName("../../acc/DayreportFormPrintInputSql.jsp");
//              mySql.setSqlId("DayreportFormPrintInputSql_2");
//              mySql.addPara('comcode.substring(0,6)',comcode.substring(0,6));
//              turnPage.queryModal(mySql.getString(), XXXXGrid);
///////////////////////end                 add///////////////////////
      tTurnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);
        if (tTurnPage.strQueryResult == "")
        {
          return "";
        }
        tTurnPage.arrDataCacheSet = decodeEasyQueryResult(tTurnPage.strQueryResult);
        var returnstr = "";
        var n = tTurnPage.arrDataCacheSet.length;
        if (n > 0)
        {
          for( i = 0;i < n; i++)
          {
              m = tTurnPage.arrDataCacheSet[i].length;
            if (m > 0)
              {
                for( j = 0; j< m; j++)
                {
                  if (i == 0 && j == 0)
                  {
                    returnstr = "0|^"+tTurnPage.arrDataCacheSet[i][j];
                  }
                  if (i == 0 && j > 0)
                  {
                    returnstr = returnstr + "|" + tTurnPage.arrDataCacheSet[i][j];
                  }
                  if (i > 0 && j == 0)
                  {
                    returnstr = returnstr+"^"+tTurnPage.arrDataCacheSet[i][j];
                  }
                  if (i > 0 && j > 0)
                  {
                    returnstr = returnstr+"|"+tTurnPage.arrDataCacheSet[i][j];
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
         fm.ManageCom.CodeData = returnstr;
         return returnstr;
  }
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

/*
//��ʼ�������б�
function initRiskCode(SaleChnl)
{
  var i,j,m,n;
  var returnstr;
  var strSql = "";
  if(SaleChnl == "1")
  {
    strSql = "select riskcode,riskname from lmriskapp where riskperiod = 'L' and subriskflag = 'M' and riskprop in ('I','C','D','A') union select '000000','���и�������' from dual";
///////////////////////add by luzhe on 20070914///////////////////////
//          var mySql=new SqlClass();
//              mySql.setJspName("../../acc/DayreportFormPrintInputSql.jsp");
//              mySql.setSqlId("DayreportFormPrintInputSql_3");
//              turnPage.queryModal(mySql.getString(), XXXXGrid);
///////////////////////end                 add///////////////////////
  }
    else if(SaleChnl == "3")
    {
    strSql = "select riskcode,riskname from lmriskapp where riskperiod = 'L' and subriskflag = 'M' and riskprop in ('Y','B','C','D') union select '000000','���д�������' from dual";
///////////////////////add by luzhe on 20070914///////////////////////
//          var mySql=new SqlClass();
//              mySql.setJspName("../../acc/DayreportFormPrintInputSql.jsp");
//              mySql.setSqlId("DayreportFormPrintInputSql_4");
//              turnPage.queryModal(mySql.getString(), XXXXGrid);
///////////////////////end                 add///////////////////////
    }
    else
    {
    strSql = "select riskcode,riskname from lmriskapp where riskperiod = 'L' and subriskflag = 'M' union select '000000','��������' from dual";
///////////////////////add by luzhe on 20070914///////////////////////
//          var mySql=new SqlClass();
//              mySql.setJspName("../../acc/DayreportFormPrintInputSql.jsp");
//              mySql.setSqlId("DayreportFormPrintInputSql_5");
//              turnPage.queryModal(mySql.getString(), XXXXGrid);
///////////////////////end                 add///////////////////////
    }
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);
  //alert(strSql);
  //�ж��Ƿ��ѯ�ɹ�
  //alert(turnPage.strQueryResult);
  if (turnPage.strQueryResult == "")
  {
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
  fm.RiskCode.CodeData = returnstr;
  return returnstr;
}
*/

/*
//���ݱ���������ʾ����Ҫ�ļ�������
function afterCodeSelect( cCodeName, Field )
{
    try {
        if( cCodeName == "bqformbank" || cCodeName == "bqformperson")
        {

            var BillType = document.all("BillType").value;
            if(BillType == "103" || BillType == "303")
            {
               divform.style.display = "";
               divPayTitle.style.display = "";
               divPayIntv.style.display = "";
               document.all('RiskFlag').value = "Yes";
               document.all('Statistic').value = "0";
               document.all('Period').value = "0";
            }
            else if(BillType == "304" || BillType == "104")
            {
               divform.style.display = "";
               divPayTitle.style.display = "";
               divPayIntv.style.display = "";
               document.all('RiskFlag').value = "Yes";
               document.all('Statistic').value = "0";
               document.all('Period').value = "1";
            }
            else if(BillType == "306" || BillType == "106")
            {
               divform.style.display = "";
               divPayTitle.style.display = "none";
               divPayIntv.style.display = "none";
               document.all('RiskFlag').value = "NO";
               document.all('Statistic').value = "1";
               document.all('Period').value = "1";
            }
            else
              {
             divPayTitle.style.display = "none";
             divPayIntv.style.display = "none";
               divform.style.display = "none";
               document.all('RiskFlag').value = "NO";
             }
             if(fm.BillType.value == "101" || fm.BillType.value == "301")
             {
              fm.Flag.value = "1";//���±����ͳ��
             }
                 checkCT();//�˱�������ѡ����������
        }
    }
    catch( ex ) {
    }
}
*/

/*
function checkCT()
{
  var tBillType = fm.BillType.value;
  var tCodeType = fm.ChnlType.value;
  if(tBillType!=null && tBillType!="")
  {
    var strSql = "select trim(othersign) from ldcode "
                  + " where codetype = '"+tCodeType+"' "
                  + " and code = '"+tBillType+"'";
///////////////////////add by luzhe on 20070914///////////////////////
//          var mySql=new SqlClass();
//              mySql.setJspName("../../acc/DayreportFormPrintInputSql.jsp");
//              mySql.setSqlId("DayreportFormPrintInputSql_6");
//              mySql.addPara('tCodeType',tCodeType);
//              mySql.addPara('"' "',"' ");
//              mySql.addPara('tBillType',tBillType);
//              mySql.addPara('"'";',"'";);
//              turnPage.queryModal(mySql.getString(), XXXXGrid);
///////////////////////end                 add///////////////////////
    var brr = easyExecSql(strSql);
    if(brr)
    {
      //ldcode����othersign:
      //Ϊ2����Ҫѡ����������
      if(brr[0][0]=="2")
        {
        divDateType.style.display = '';
        fm.CTFlag.value = "1";
              fm.DateType.value = "2";
              fm.DateTypeName.value = "��Ч����";
        return;
        }
    }
  }
  divDateType.style.display = 'none';
  fm.CTFlag.value = "0";
}
*/

/*
//��������ѡ������
function showChnlType()
{
  var SaleChnl = fm.SaleChnl.value;
  var ChnlSel = fm.ChnlSel.value;
  if(SaleChnl!=ChnlSel)
  {
    fm.ChnlSel.value = SaleChnl;    //��¼ÿ����ѡ���������������������ձ�������
    document.all('BillType').value = '';
    document.all('BillTypeName').value = '';
  }
  switch(SaleChnl)
  {
    case "1":  fm.ChnlType.value = "bqformperson";
          break;
    case "2":  fm.ChnlType.value = "bqformgrp";
          break;
    case "3":  fm.ChnlType.value = "bqformbank";
          break;
    default:   fm.ChnlType.value = "";
          break;
  }
  //initRiskCode(SaleChnl);
}
*/
