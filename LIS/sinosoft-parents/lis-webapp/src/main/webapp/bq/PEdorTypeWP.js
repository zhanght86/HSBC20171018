//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var selno; 
var mySql1=new SqlClass();

function edorTypePTReturn()
{
		initForm();
}
function getCustomerGrid()
{
    var tContNo=document.all("ContNo").value;
    if(tContNo!=null&&tContNo!="")
    {
		var strSQL = "";
		var sqlid1="PEdorTypeIPInputInputSql8";
	
		var sqlresourcename = "bq.PEdorTypeIPInputInputSql";  
		mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(tContNo);
		mySql1.addSubPara(tContNo);
		strSQL=mySql1.getString();
          
        arrResult = easyExecSql(strSQL);
        if (arrResult)
        {
            displayMultiline(arrResult,CustomerGrid);
        }
    }
}
/***********************************************************************
 *   ��һ���������ֽ��мӱ�
 ***********************************************************************/
function addpolamnt(){
	  selno = LCInsuredGrid.getSelNo()-1;    
	  if (selno <0)
	  {
	      alert("��ѡ��Ҫ�ӱ������֣�");
	      return;
	  }
	  document.all("fmtransact").value = "INSERT||EDORAPPCONFIRM";
    if (window.confirm("�Ƿ����㱾�α�ȫ����?"))
    {

        var showStr="���ڼ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
        //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

        fm.action= "./PEdorTypeAASubmit.jsp";
        fm.submit();
    }
}

function edorTypeMRSave()
{
    var nPayMoney = document.getElementsByName('AddPrem')[0].value;
    if(nPayMoney == null || nPayMoney == '')
    {
        alert("������׷�ӽ�");
        return false;
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
    fm.submit();
}

function customerQuery()
{	
	window.open("./LCAppntIndQuery.html");
}

//�ύ�����水ť��Ӧ����
function submitForm()
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
  initLCAppntIndGrid();
  fm.submit(); //�ύ
}
function updatepol(){
	 mySql.setSqlId("PEdorTypeWPInputSql_1");
   mySql.addPara('tContNo',document.all("EdorNo").value);
	 var arrResult = easyExecSql(mySql.getSQL());
	 if(arrResult == null){
	 	  //alert("��������ʧ��!");
	 	  return false;
	 }
	 LCInsuredGrid.setRowColData(selno, 6, arrResult[0][0]);
	 LCInsuredGrid.setRowColData(selno, 7, arrResult[0][1]);
	 LCInsuredGrid.setRowColData(selno, 8, arrResult[0][2]);
}


//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���	
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

/*********************************************************************
 *  ��ѯ������ϸ��Ϣʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
 *  ����  ��  ��ѯ���صĶ�ά����
 *  ����ֵ��  ��
 *********************************************************************
 */

function afterQuery( arrQueryResult )
{

}

function returnParent()
{
	try
    {
        top.close();
        top.opener.focus();
    }
    catch (ex) {}
}

function personQuery()
{
    //window.open("./LCPolQuery.html");
    window.open("./LPTypeIAPersonQuery.html");
}

function afterPersonQuery(arrResult)
{
    if (arrResult == null ||arrResult[0] == null || arrResult[0][0] == "" )
        return;

    //ѡ����һ��Ͷ����,��ʾ��ϸϸ��
    document.all("QueryCustomerNo").value = arrResult[0][0];
    
    var strSql = "select * from ldperson where customerNo = " + arrResult[0][0];
    
	//��ѯSQL�����ؽ���ַ���
    turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  

 //   alert(turnPage.strQueryResult);
    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult) {  
    	//���MULTILINE��ʹ�÷�����MULTILINEʹ��˵�� 
      	VarGrid.clearData('VarGrid');  
      	alert("��ѯʧ�ܣ�");
      	return false;
  	}

  
 	 //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
  	turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
    //��ѯ�ɹ������ַ��������ض�ά����
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
    fillPersonDetail();
    
  divLPAppntIndDetail.style.display = "";
    
    	
	
}

function fillPersonDetail()
{
	try {
		document.all("AppntCustomerNo").value = turnPage.arrDataCacheSet[0][0];
		document.all("AppntName").value = turnPage.arrDataCacheSet[0][2];
		document.all("AppntSex").value = turnPage.arrDataCacheSet[0][3];
		document.all("AppntBirthday").value = turnPage.arrDataCacheSet[0][4];
		
		document.all("AppntIDType").value = turnPage.arrDataCacheSet[0][16];
		document.all("AppntIDNo").value = turnPage.arrDataCacheSet[0][18];
		document.all("AppntNativePlace").value = turnPage.arrDataCacheSet[0][5];
		
		document.all("AppntPostalAddress").value = turnPage.arrDataCacheSet[0][24];
		document.all("AppntZipCode").value = turnPage.arrDataCacheSet[0][25];
		document.all("AppntHomeAddress").value = turnPage.arrDataCacheSet[0][23];
		document.all("AppntHomeZipCode").value = turnPage.arrDataCacheSet[0][22];
		
		document.all("AppntPhone").value = turnPage.arrDataCacheSet[0][26];
		document.all("AppntPhone2").value = turnPage.arrDataCacheSet[0][56];
		document.all("AppntMobile").value = turnPage.arrDataCacheSet[0][28];
		document.all("AppntEMail").value = turnPage.arrDataCacheSet[0][29];
		document.all("AppntGrpName").value = turnPage.arrDataCacheSet[0][38];
		
		document.all("AppntWorkType").value = turnPage.arrDataCacheSet[0][48];
		document.all("AppntPluralityType").value = turnPage.arrDataCacheSet[0][49];
		document.all("AppntOccupationCode").value = turnPage.arrDataCacheSet[0][50];
		document.all("AppntOccupationType").value = turnPage.arrDataCacheSet[0][9];
    } catch(ex) {
    	alert("��PEdorTypeWP.js-->fillPersonDetail�����з����쳣:��ʼ���������!");
  	}      
}
function showNotePad()
{
	var MissionID = top.opener.document.all("MissionID").value;
	var SubMissionID = top.opener.document.all("SubMissionID").value;
	var ActivityID = '0000000003';
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

function QueryEdorInfo()
{
	var tEdortype=top.opener.document.all('EdorType').value;
	if(tEdortype!=null || tEdortype !='')
	{
		mySql.setSqlId("PEdorTypeIPInputSql_2");
    mySql.addPara('tEdorType',tEdortype);
    }
    else
	{
		alert('δ��ѯ����ȫ������Ŀ��Ϣ��');
	}
	turnPage.strQueryResult  = easyQueryVer3(mySql.getSQL(), 1, 0, 1);	
	var arrSelected = new Array();	
	turnPage.strQueryResult  = easyQueryVer3(mySql.getSQL(), 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
    try {document.all('EdorType').value = arrSelected[0][0];} catch(ex) { }; //ְҵ���
    try {document.all('EdorTypeName').value = arrSelected[0][1];} catch(ex) { }; //ְҵ���
}

function Edorquery()
{
  var ButtonFlag = top.opener.document.all('ButtonFlag').value;
  if(ButtonFlag!=null && ButtonFlag=="1")
  {
     DivEdorSaveButton.style.display = "none";
     DivEdorCancelButton.style.display = "none";
  }
  else
  {
 	 DivEdorSaveButton.style.display = "";
 	 DivEdorCancelButton.style.display = "";
  }
}

function getAddFee()
{
mySql.setSqlId("PEdorTypeWPInputSql_3");
mySql.addPara('tEdorAcceptNo',top.opener.document.all('EdorAcceptNo').value);
    
var arrResult = easyExecSql(mySql.getSQL());
if (arrResult != null)
{
   fm.AddPrem.value = arrResult[0][0];

}
else
{
   fm.OccupationType.value = '';
}
}

/**
 *��ѯ׷�Ӽ�¼
 */
function queryNoteGrid()
{
    var EdorAppDate = document.getElementsByName('EdorItemAppDate')[0].value;
    var sContNo=document.getElementsByName('ContNo')[0].value;
    
	var sqlid1="PEdorTypeIPInputInputSql8";
	
	var sqlresourcename = "bq.PEdorTypeIPInputInputSql";  
	mySql.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
    mySql.setSqlId("PEdorTypeWPInputSql_4");
    mySql.addPara('tContNo',sContNo);
    mySql.addPara('tEdorAcceptNo',document.all('EdorAcceptNo').value);
    try
    {
        var arrResult = easyExecSql(mySql.getSQL());
        if (arrResult)
        {
            displayMultiline(arrResult,NoteGrid);
            //document.all("DivAddMoneyInfo").style.display = "none";
        }
    }catch( ex )
    {}
}

//ɾ��׷�Ӽ�¼
function deleteRecord()
{
    var sSelNo = NoteGrid.getSelNo() - 1;
    if (sSelNo < 0)
    {
       alert("��ѡ����Ҫɾ���ļ�¼!");
       return;
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
    fm.fmtransact.value = "EDORITEM|DELETE";
    fm.submit();
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
        var sfmtransact = document.getElementsByName("fmtransact")[0].value;
        
        if (sfmtransact == "EDORITEM|DELETE" )
        {
            try
            {
               //document.all("DivAddMoneyInfo").style.display = "";
               document.getElementsByName("AddPrem")[0].value = '';
               NoteGrid.clearData();
               queryNoteGrid();
            }catch( ex ){}
        }else{
        	queryNoteGrid();
        }
        try
        {
            queryBackFee();
            top.opener.getEdorItemGrid();
            document.getElementsByName("fmtransact")[0].value = "";

        }
        catch (ex) {}

    }
}