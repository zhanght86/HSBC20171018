//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var theFirstValue="";
var theSecondValue="";
function edorTypeAGReturn()
{
	top.close();
}
function edorTypeWTQuery()
{
	alert("Wait...");
}
function edorTypeAGSave()
{
	//alert('��δ���ӵ���̨����......');
	if (PolGrid.mulLineCount==0)
	{
		alert('Ŀǰû����ȡ��Ŀ��');
		return;
	}
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
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

 	//showSubmitFrame(mDebug);
  fm.submit();

}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content,Result )
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
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();
    //ˢ��ҳ��
    window.location.reload();
  }
}



//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("��Proposal.js-->resetForm�����з����쳣:��ʼ���������!");
  }
}

//ȡ����ť��Ӧ����
function cancelForm()
{
    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
}

//�ύǰ��У�顢����
function beforeSubmit()
{
  //��Ӳ���
}

//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
  //����������Ӧ�Ĵ���
  showDiv(operateButton,"false");
  showDiv(inputButton,"true");
}

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
  //����������Ӧ�Ĵ���
  alert("update click");
}

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
  //����������Ӧ�Ĵ���
	alert("query click");
	  //��ѯ���������һ��ģ̬�Ի��򣬲��ύ�������������ǲ�ͬ��
  //��ˣ����еĻ����Ҳ���Բ��ø�ֵ��
}

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  //����������Ӧ�Ĵ���
  alert("delete click");
}
//---------------------------
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
function showBankDiv()
{
	if(document.all('GoonGetMethod').value=='0')
	{
		BankInfo.style.display = "";
		//document.all('Bank').value="";
		//document.all('Account').value="";
		//document.all('AccName').value="";
	}
	if(document.all('GoonGetMethod').value=='1' || document.all('GoonGetMethod').value=='2' )
	{
		BankInfo.style.display = "none";

	}
}

//��֤�ı��������������ֵ�Ƿ����
function confirmSecondInput1(aObject,aEvent){
	if(aEvent=="onkeyup"){
	  var theKey = window.event.keyCode;
	  if(theKey=="13"){
	    if(theFirstValue!=""){
      	theSecondValue = aObject.value;
	      if(theSecondValue==""){
	      	alert("���ٴ�¼�룡");
	        aObject.value="";
	        aObject.focus();
	        return;
	      }
	      if(theSecondValue==theFirstValue){
	        aObject.value = theSecondValue;
	        return;
	      }
	      else{
	        alert("����¼����������������¼�룡");
	        theFirstValue="";
	        theSecondValue="";
	        aObject.value="";
	        aObject.focus();
	        return;
	      }
	    }
      else{
      	theFirstValue = aObject.value;
      	if(theFirstValue==""){
      		theSecondValue="";
      	  alert("��¼�����ݣ�");
      	  return;
      	}
      	aObject.value="";
      	aObject.focus();
        return;
      }
    }
  }
  else if(aEvent=="onblur"){
	  //alert("theFirstValue="+theFirstValue);
	  if(theFirstValue!=""){
    	theSecondValue = aObject.value;
	    if(theSecondValue==""){
	    	alert("���ٴ�¼�룡");
	      aObject.value="";
	      aObject.focus();
	      return;
	    }
	    if(theSecondValue==theFirstValue){
	      aObject.value = theSecondValue;
	      theSecondValue="";
	      theFirstValue="";
	      return;
	    }
	    else{
	      alert("����¼����������������¼�룡");
	      theFirstValue="";
	      theSecondValue="";
	      aObject.value="";
	      aObject.focus();
	      return;
	    }
	  }
    else{
    	theFirstValue = aObject.value;
     	theSecondValue="";
    	if(theFirstValue==""){
      	//alert("aa");
    	  return;
    	}
    	aObject.value="";
    	aObject.focus();
      return;
    }
  }
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
function returnParent()
{
 //top.opener.initEdorItemGrid();
 //top.opener.getEdorItemGrid();
 top.close();
}

//<!-- XinYQ modified on 2005-11-08 : BGN -->
/*============================================================================*/

/*
 * ������ȡ��ʽ-����ת��-�����˻���Ϣ��ѯ
 */
function queryBankInfo()
{
    var QuerySQL, arrResult;
   /* QuerySQL = "select GetForm, GetBankCode, GetBankAccNo, GetAccName "
             +   "from LCPol "
             +  "where 1 = 1 "
             +  getWherePart("ContNo", "ContNo")
             +  getWherePart("PolNo", "PolNo");
    */
    
    var sqlid1 = "PEdorTypeAGSql1";
  	var mySql1 = new SqlClass();
  	mySql1.setResourceName("bqs.PEdorTypeAGSql"); // ָ��ʹ�õ�properties�ļ���
  	mySql1.setSqlId(sqlid1);// ָ��ʹ�õ�Sql��id
  	mySql1.addSubPara(window.document.getElementsByName(trim('ContNo'))[0].value);// ָ������Ĳ���
  	mySql1.addSubPara(window.document.getElementsByName(trim('PolNo'))[0].value);// ָ������Ĳ���
    QuerySQL = mySql1.getString();   
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("���棺��ѯ�ͻ������˻���Ϣ�����쳣�� ");
        return;
    }
    if (arrResult != null)
    {
        try
        {
            //��ȡ��ʽ
            document.getElementsByName("GoonGetMethod")[0].value = arrResult[0][0];
            showOneCodeName('GetLocation','GoonGetMethod','GoonGetMethodName');
            //��������
            document.getElementsByName("GetBankCode")[0].value = arrResult[0][1];
            showOneCodeName('Bank', 'GetBankCode', 'BankName');
            //�����ʻ�
            document.getElementsByName("GetBankAccNo")[0].value = arrResult[0][2];
            //�ʻ���
            if (trim(arrResult[0][3]) != "")
                document.getElementsByName("GetAccName")[0].value = arrResult[0][3];
            else
                document.getElementsByName("GetAccName")[0].value = document.getElementsByName("InsuredName")[0].value;
        }
        catch (ex)
        {
            alert("���󣺲�ѯ�ͻ������˻���Ϣ�ɹ�, ����ʾ��ҳ��ʧ�ܣ� ");
            return;
        }
    }
    //�����ʾ
    if (arrResult != null && trim(arrResult[0][0]) == "0")
        try { document.all("BankInfo").style.display = ""; } catch (ex) {}
    else
        try { document.all("BankInfo").style.display = "none"; } catch (ex) {}
}

/*============================================================================*/

//�����ȡ��ʽ��������������ʻ���Ϣ����
//function formatGetModeAbout()
//{
//	var tSql="SELECT GetForm,GetBankCode,GetBankAccNo,GetAccName"
//				+ " FROM ((select '1' as flag,GetForm,GetBankCode,GetBankAccNo,GetAccName"
//		 					 + " from LPPol"
//		 					 + " where EdorNo='" + document.all('EdorNo').value + "' and EdorType='" + document.all('EdorType').value + "' and PolNo='" + document.all('PolNo').value + "')"
//		 					+ " union"
//							+ " (select '2' as flag,GetForm,GetBankCode,GetBankAccNo,GetAccName"
//		 					 + " from LCPol"
//		 					 + " where PolNo='" + document.all('PolNo').value + "'))"
//				+ " WHERE rownum=1"
//				+ " ORDER BY flag";
//	//alert(tSql);
//	var arrResult=easyExecSql(tSql,1,0);
//	try{document.all('GoonGetMethod').value= arrResult[0][0];}catch(ex){};
//	showOneCodeName('getlocation','GoonGetMethod','GoonGetMethodName');
//	try{document.all('GetBankCode').value= arrResult[0][1];}catch(ex){};
//	showOneCodeName('bank','GetBankCode','BankName');
//	try{document.all('GetBankAccNo').value= arrResult[0][2];}catch(ex){};
//	try{document.all('GetAccName').value= arrResult[0][3];}catch(ex){};
//	if (document.all('GoonGetMethod').value == '0')
//	{
//		BankInfo.style.display = "";
//	}
//	else
//	{
//		BankInfo.style.display = "none";
//	}
//}

function QueryEdorInfo()
{
	var tEdortype=top.opener.document.all('EdorType').value;
	var strSQL;
	if(tEdortype!=null || tEdortype !='')
	{
	   //strSQL="select distinct edorcode, edorname from lmedoritem where edorcode = '" + tEdortype + "'";
	    var sqlid2 = "PEdorTypeAGSql2";
	  	var mySql2 = new SqlClass();
	  	mySql2.setResourceName("bqs.PEdorTypeAGSql"); // ָ��ʹ�õ�properties�ļ���
	  	mySql2.setSqlId(sqlid2);// ָ��ʹ�õ�Sql��id
	  	mySql2.addSubPara(tEdortype);// ָ������Ĳ���
	  	strSQL = mySql2.getString();   
	   
    }
    else
	{
		alert('δ��ѯ����ȫ������Ŀ��Ϣ��');
	}
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	var arrSelected = new Array();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
    try {document.all('EdorType').value = arrSelected[0][0];} catch(ex) { }; //ְҵ���
    try {document.all('EdorTypeName').value = arrSelected[0][1];} catch(ex) { }; //ְҵ���
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