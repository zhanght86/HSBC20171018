//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var tTempLoanRate=0;
var sqlresourcename = "bq.PEdorTypeLNInputSql";

function verify()
{
  for (i=0; i<LoanGrid.mulLineCount; i++)
  {
    if (LoanGrid.getRowColData(i, 5) == "")
    {
      continue;
    }
    else
    {
      if (parseFloat(LoanGrid.getRowColData(i, 5)) > LoanGrid.getRowColData(i, 2))
      {
        alert("��" + (i+1) + "�л�����ڵ���ȷ�Ͻ�");
        return false;
      }
    }
  }

  return true;
}

function saveEdorTypeLN()
{
        //����У��
    if (!verifyInput2())
    {
        return false;
    }

    //У���������ܴ�������޶�
    if ((document.all('MaxLoan').value-0) > (document.all('SaveMaxLoan').value-0))
    {
        alert("���������Ϊ"+document.all('SaveMaxLoan').value+"��������Ĵ�������ߣ�");
        return;
    }
    //У���������ܴ�������޶�
    if ((document.all('MaxLoan').value-0) < 1000)
    {
        alert("������ܵ���1000");
        return;
    }
    if(tTempLoanRate-document.all('LoanRate').value!=0)
    {
    	if(!confirm("���ʷ�����������Ҫͨ���������Ƿ����"))
    	{
    		        return;
    		}
    }
    document.all('fmtransact').value ="INSERT||MAIN";
    var MsgContent = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
    //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=300;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action="./PEdorTypeLNSubmit.jsp"
    fm.submit();
}

/**
 * �ύ�����, ���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(DealFlag, MsgContent, OtherParam)
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
            document.getElementsByName("Interest")[0].value = OtherParam;
            fm.MaxLoan.value= "";
			fm.LoanRate.value = "";
			fm.Currency.value = "";
			fm.SaveMaxLoan.value = "";
            initLoanGrid();
            queryBackFee();
        }
        catch (ex) {}
    }
}

//��ѯ���ʼ��MulLine
function afterCount(tFlagStr,tContent,tMulArray){
   //��ʼ��MulLine
        if (tFlagStr == "Success"){
        	displayMultiline(tMulArray,LoanGrid,turnPage);
 		} else {
         	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + tContent ;
         	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=300;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
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

function queryCustomerGrid()
 {
  try
    {

    var tContNo=document.all("ContNo").value;
    //alert(tContNo);
    if(tContNo!=null&&tContNo!="")
    {
/*        var strSQL = " Select a.appntno,'Ͷ����',a.appntname,a.idtype||'-'||x.codename,a.idno,a.appntsex||'-'||sex.codename,a.appntbirthday From lcappnt a "
                   + " Left Join (Select code,codename From ldcode Where codetype='idtype') x On x.code = a.idtype "
                   + " Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = a.appntsex  Where contno='"+tContNo+"'"
                   + " Union"
                   + " Select i.insuredno,'������',i.name,i.IDType||'-'||xm.codename,i.IDNo,i.Sex||'-'||sex.codename,i.Birthday From lcinsured i "
                   + " Left Join (Select code,codename From ldcode Where codetype='idtype') xm On xm.code = i.idtype "
                   + " Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = i.sex Where contno='"+tContNo+"'";
//alert(strSQL);
*/        
    var strSQL = "";
    var sqlid2="PEdorTypeLNInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(tContNo);//ָ������Ĳ���
	mySql2.addSubPara(tContNo);
	strSQL=mySql2.getString();
        
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

        //�ж��Ƿ��ѯ�ɹ�
        if (!turnPage.strQueryResult)
        {
            return false;
        }

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
    }
    catch(ex)
    {
        alert("��PEdorTypeEA.js-->queryCustomer()�����з����쳣:"+ex);
    }
   }

   function getPolInfo(tContNo)
{
      var tContno=document.all('ContNo').value;
    //alert(tContNo);
    //var tContNo;
    // ��дSQL���
 //   var strSQL ="select RiskCode,(select RiskName from LMRisk where LMRisk.RiskCode = LCPol.RiskCode),Amnt,Prem,'','','','' from LCPol where ContNo='"+tContNo+"' and AppFlag = '1'";

	var strSQL = "";
    var sqlid3="PEdorTypeLNInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(tContNo);//ָ������Ĳ���
	strSQL=mySql3.getString();

    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult)
    {
        return false;
    }
    //alert(turnPage.strQueryResult);
    //��ѯ�ɹ������ַ��������ض�ά����

    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //���ó�ʼ������MULTILINE����

    turnPage.pageDisplayGrid = LoanGrid;
    //����SQL���
    turnPage.strQuerySql = strSQL;
    //���ò�ѯ��ʼλ��
    turnPage.pageIndex = 0;
    //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //����MULTILINE������ʾ��ѯ���
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
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

function ShowLoanInfo(){
	var rate = LoanGrid.getRowColData(LoanGrid.getSelNo()-1, 7);
	var currency = LoanGrid.getRowColData(LoanGrid.getSelNo()-1, 9);
	var thisLoan = LoanGrid.getRowColData(LoanGrid.getSelNo()-1, 10);
	var maxHaveLoan = LoanGrid.getRowColData(LoanGrid.getSelNo()-1, 6);
	fm.MaxLoan.value= thisLoan;
	fm.LoanRate.value = rate;
	fm.Currency.value = currency;
	fm.SaveMaxLoan.value = maxHaveLoan;
}









