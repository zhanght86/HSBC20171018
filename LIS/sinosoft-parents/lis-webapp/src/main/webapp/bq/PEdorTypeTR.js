//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var sqlresourcename = "bq.PEdorTypeTRInputSql";

function verify(i)
{   
	var tFlag=true;

      if (parseFloat(LoanGrid.getRowColData(i, 4))!= LoanGrid.getRowColData(i, 2))
      {
         tFlag=false;
      }
      if (parseFloat(LoanGrid.getRowColData(i, 5))!= LoanGrid.getRowColData(i, 3))
      {
         tFlag=false;
      }

  return tFlag;
}

function edorTypeTRSave()
{
  if (!LoanGrid.checkValue()) 
  {
    return false; 
  }
  
      var row = LoanGrid.mulLineCount;
    //�����ж���ѡ�������˱�ʱ�����ѡ�����գ���Ҫ�󽫸�����һ��ѡ��
    var tFlag=false;
    var i = 0;
    var tSReLoan=0;
    var tAReLoan=0;
      for(var m = 0; m < row; m++ )
      {
        if(LoanGrid.getChkNo(m))
        {
          if(!verifyErr(m))
          {
           return;  	
           }
          if (!verify(m)) 
          {
  	       if(!confirm("�Ե滹������仯���Ƿ����?"))
         	 {
  		       return false; 
  	       }  	       	
           }  	
          i = i+1;
          tAReLoan+=(parseFloat(LoanGrid.getRowColData(m, 4))+parseFloat(LoanGrid.getRowColData(m, 5)));
        }
        tSReLoan+=(parseFloat(LoanGrid.getRowColData(m, 2))+parseFloat(LoanGrid.getRowColData(m, 3)));
      }
      if(i == 0)
      {
        alert("��ѡ����Ҫ����ļ�¼��");
        return false;
      }
  fm.SReLoan.value=tSReLoan;
  fm.AReLoan.value=tAReLoan;
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
  fm.action="./PEdorTypeTRSubmit.jsp";
  fm.submit();
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content, Result, loanResult)
{ 
  try { showInfo.close(); } catch (e) {}
  
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    queryBackFee();
}

function returnParent()
{
 top.opener.initEdorItemGrid();
 top.opener.getEdorItemGrid();
 top.close();
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

function ChangCalInterest() {
	//add by jiaqiangli 2009-04-03 ����DivLockScreen���ֲ�Ĵ��� ����
	lockScreen('DivLockScreen');
	//add by jiaqiangli 2009-04-03 ����DivLockScreen���ֲ�Ĵ��� ����
	
	fm.action="./PEdorTypeTRCount.jsp";
	fm.submit();
}

function QueryEdorInfo()
{
	var tEdortype = top.opener.document.all('EdorType').value;
	var strSQL;
	if(tEdortype!=null || tEdortype !='')
	{
//	    strSQL="select distinct edorcode, edorname from lmedoritem where edorcode = '" + tEdortype + "'";
	    
	var sqlid1="PEdorTypeTRInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tEdortype);//ָ������Ĳ���
	strSQL=mySql1.getString();
	    
    }
    else
	{
		alert('δ��ѯ����ȫ������Ŀ��Ϣ��');
	}
	var arrSelected = new Array();	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
    try {document.all('EdorType').value = arrSelected[0][0];} catch(ex) { }; 
    try {document.all('EdorTypeName').value = arrSelected[0][1];} catch(ex) { }; 
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

function verifyErr(i)
{   
	var tFlag=true;

  if (parseFloat(LoanGrid.getRowColData(i, 4))> parseFloat(LoanGrid.getRowColData(i, 2)))
   {
	      	alert("������ܴ���Ƿ����");
		      return false;
    }
    if (parseFloat(LoanGrid.getRowColData(i, 5))> parseFloat(LoanGrid.getRowColData(i, 3)))
    {
		    alert("������Ϣ���ܴ���Ƿ����Ϣ");
		      return false;
   }
  return tFlag;
}