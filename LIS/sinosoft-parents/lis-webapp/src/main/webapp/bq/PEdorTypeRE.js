//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "bq.PEdorTypeREInputSql";

function saveEdorTypeRE()
{

  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
var iWidth=550;      //�������ڵĿ��; 
var iHeight=250;     //�������ڵĸ߶�; 
var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

showInfo.focus();
  fm.fmtransact.value="";
  document.getElementById("fm").submit();
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content, tMulArray )
{
    showInfo.close();
    //alert(tMulArray);

    var urlStr;
    if (FlagStr == "Success")
    {
        urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    else if (FlagStr == "Fail")
    {
        urlStr="../common/jsp/MessagePage.jsp?picture=F&content=" + content;
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
        urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=300;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }

    if (FlagStr == "Success")
    {
        //displayMultiline(tMulArray,PolInsuredGrid,turnPage);
        try
        {
            queryBackFee();
            top.opener.getEdorItemGrid();
        } catch (ex) {}
    }

    /*
    var tTransact=document.all('fmtransact').value;
//      alert(tTransact);
        if (tTransact=="QUERY||MAIN")
        {
            var iArray;
            //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
            turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
            //�����ѯ����ַ���
            turnPage.strQueryResult  = Result;

            //��ѯ�ɹ������ַ��������ض�ά����
            var tArr   = decodeEasyQueryResult(turnPage.strQueryResult,0);
//          alert(tArr[0]);
//          alert(tArr[0].length);
            document.all('Amnt').value = tArr[0][45];
            document.all('Prem').value = tArr[0][42];
            document.all('AppntNo').value = tArr[0][28];
            document.all('AppntName').value = tArr[0][29];
            document.all('Multi').value = tArr[0][40];
            document.all('CalMode').value = tArr[0][134];

            //calMode�Ǹ����ڱ������ַ����������ģ������������
            var calMode = tArr[0][134];
            //alert(tArr[0][tArr[0].length-2]);

            if (calMode == 'P') {
                var urlStr="../common/jsp/MessagePage.jsp?picture=S&content= �˱����޷����в����˱�����" ;
                showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
                initForm();
                return;
            } else if (calMode == 'O') {   //���÷����仯���м���

                //document.all('RemainMulti').value = tArr[0][132];
                RemainAmntDiv.style.display = "none";
                RemainMultiDiv.style.display = "";
            } else {                      //���ñ���仯���м���

                //document.all('RemainAmnt').value = tArr[0][132];
                RemainMultiDiv.style.display = 'none';
                RemainAmntDiv.style.display = '';
            }

            //���ձ�ȫ���ã��Զ���д��ֵ�����ύ
        if (typeof(top.opener.GrpBQ)=="boolean" && top.opener.GrpBQ==true) {
          fm.RemainAmnt.value = top.opener.GTArr.pop();

          edorTypePTSave();
        }
        //***********************************

        } else {

      //���ձ�ȫ���ã��ύ�ɹ����ٴε��ã���ѭ��
        if (typeof(top.opener.GrpBQ)=="boolean" && top.opener.GrpBQ==true) {
              top.opener.PEdorDetail();
              top.close();
            }
            else {
              var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
          showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
          initForm();
        }
    }
    */

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


function personQuery()
{
    //window.open("./LCPolQuery.html");
//    window.open("./LPTypeIAPersonQuery.html");
    window.open("./LPTypeIAPersonQuery.html","",sFeatures);
}

function afterPersonQuery(arrResult)
{
    if (arrResult == null ||arrResult[0] == null || arrResult[0][0] == "" )
        return;

    //ѡ����һ��Ͷ����,��ʾ��ϸϸ��
    document.all("QueryCustomerNo").value = arrResult[0][0];

//    var strSql = "select * from ldperson where customerNo = " + arrResult[0][0];

	var strSql = "";
	var sqlid1="PEdorTypeREInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(arrResult[0][0]);//ָ������Ĳ���
	strSql=mySql1.getString();

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
        alert("��PEdorTypePT.js-->fillPersonDetail�����з����쳣:��ʼ���������!");
    }
}
function getPolInfo(tContNo)
{
      var tContno=document.all('ContNo').value;
    //alert(tContNo);
    //var tContNo;
    // ��дSQL���
    //add by jiaqiangli 2009-03-10 �Ѿ���ֹ�ĸ����ղ��ܸ�Ч���˴�����ʾ����
//    var strSQL ="select InsuredNo,InsuredName,RiskCode,(select RiskName from LMRisk where LMRisk.RiskCode = LCPol.RiskCode),Prem,Amnt,CValiDate,contno,grpcontno from LCPol where ContNo='"+tContNo+"' and appflag = '1' ";

	var strSQL = "";
	var sqlid2="PEdorTypeREInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(tContno);//ָ������Ĳ���
	strSQL=mySql2.getString();

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

    turnPage.pageDisplayGrid = LCInsuredGrid;
    //����SQL���
    turnPage.strQuerySql = strSQL;
    //���ò�ѯ��ʼλ��
    turnPage.pageIndex = 0;
    //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //����MULTILINE������ʾ��ѯ���
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

function showCustomerImpart(){
    var tContNo = document.all('ContNo').value;
//    var strsql = "select 1 from dual where '00' in (select substr(d.casepoltype,0,2) from lmdutygetclm d where d.getdutycode in (select getdutycode from lcget where contno = '"+tContNo+"'))";
    
    var strsql = "";
	var sqlid3="PEdorTypeREInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(tContNo);//ָ������Ĳ���
	strsql=mySql3.getString();
    
    var aResult = easyExecSql(strsql,1,0);
    //alert(aResult[0][0]);
    if(aResult != null){
         if(aResult[0][0] == "1"){
              document.all('ReFlag').value = "App";
              divAppnt.style.display = '';
              divImpart.style.display = '';
         }
    }
}

function queryCalInterest() {
//	var strsql = "select nvl(standbyflag3,'none') from lpedoritem where edoracceptno = '"+document.all('EdorAcceptNo').value+"' and contno = '"+document.all('ContNo').value+"' and edortype = '"+document.all('EdorType').value+"'";
    
    var strsql = "";
	var sqlid4="PEdorTypeREInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(document.all('EdorAcceptNo').value);//ָ������Ĳ���
	mySql4.addSubPara(document.all('ContNo').value);
	mySql4.addSubPara(document.all('EdorType').value);
	strsql=mySql4.getString();
    
    var aResult = easyExecSql(strsql,1,0);
    if (aResult[0][0] == "off") {
    	document.all('isCalInterest').checked = false;
    }
}

function showRelaInsured(){
    var tContNo = document.all('ContNo').value;
//    var str5  = "select customerno from lcinsuredrelated where polno in (select polno from lcpol where contno = '" + tContNo +"' and appflag = '1')";
    
    var str5 = "";
	var sqlid5="PEdorTypeREInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(tContNo);//ָ������Ĳ���
	str5=mySql5.getString();
    
    var aResult = easyExecSql(str5,1,0);
    if(aResult != null && aResult != ""){
         divInsured.style.display = '';
         divImpart.style.display = '';
       document.all('ReFlag').value = "Yes";
     document.all('CustomerNo').value = aResult[0][0];
    }
}

function Edorquery()
{
    var ButtonFlag = top.opener.document.all('ButtonFlag').value;
    if(ButtonFlag!=null && ButtonFlag=="1")
    {
       try { divEdorquery.style.display = "none"; } catch (ex) {}
    }
    else
    {
        try { divEdorquery.style.display = ""; } catch (ex) {}
    }
}


function showimpart(){
     var tEdorNo = document.all('EdorNo').value;
     var tContNo = document.all('ContNo').value;

    var strre = "";
	var sqlid6="PEdorTypeREInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
	mySql6.addSubPara(tContNo);//ָ������Ĳ���
	strre=mySql6.getString();
     
//     var aResult = easyExecSql("select insuredno from lcpol where contno = '" + tContNo +"' and mainpolno = polno and appflag = '1'");
    var aResult = easyExecSql(strre);
     if(aResult == null){
           alert("��ѯ������Ϣʧ�ܣ�");
           return false;
     }
/*     var Strvar = "select impartver,impartcode,impartcontent,impartparammodle from lpcustomerimpart where edortype = 'RE' and edorno = '"
         + tEdorNo + "' and customerno = '" + aResult[0][0] + "'";
*/     
    var Strvar = "";
	var sqlid7="PEdorTypeREInputSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
	mySql7.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql7.addSubPara(aResult[0][0]);
	Strvar=mySql7.getString();
     
     var arrSelected = new Array();
     turnPage.strQueryResult  = easyQueryVer3(Strvar, 1, 0, 1);
     //�ж��Ƿ��ѯ�ɹ�
     if (!turnPage.strQueryResult) {
          return false;
     }
     //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
     turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
     //��ѯ�ɹ������ַ��������ض�ά����
     turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
     //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
     turnPage.pageDisplayGrid = ImpartGrid;
     //����SQL���
     turnPage.strQuerySql = Strvar;
     //���ò�ѯ��ʼλ��
     turnPage.pageIndex = 0;
     //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
     arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
     //����MULTILINE������ʾ��ѯ���
     displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

function showotherimpart()
{
     var ReFlag = document.all('ReFlag').value;
     var tEdorNo = document.all('EdorNo').value;
     //alert(ReFlag);
     var CustomerNo;
     if(ReFlag == "Yes"){
          CustomerNo = document.all('CustomerNo').value;
          //alert(CustomerNo);
     }
     else if(ReFlag== "App"){
          var tContNo = document.all('ContNo').value;

    var strSQLre = "";
	var sqlid8="PEdorTypeREInputSql8";
	var mySql8=new SqlClass();
	mySql8.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
	mySql8.addSubPara(tContNo);//ָ������Ĳ���
	strSQLre=mySql8.getString();
	
//          var aResult = easyExecSql("select appntno from lcpol where contno = '" + tContNo +"' and mainpolno = polno and appflag = '1'");
    var aResult = easyExecSql(strSQLre);

        if(aResult == null){
               alert("��ѯ������Ϣʧ�ܣ�");
               return false;
        }
          CustomerNo = aResult[0][0];
     }
     else{
          return false;
     }
/*     var Strvar = "select impartver,impartcode,impartcontent,impartparammodle from lpcustomerimpart where edortype = 'RE' and edorno = '"
         + tEdorNo + "' and customerno = '" + CustomerNo + "'";
*/     
    var Strvar = "";
	var sqlid9="PEdorTypeREInputSql9";
	var mySql9=new SqlClass();
	mySql9.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
	mySql9.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql9.addSubPara(CustomerNo);
	Strvar=mySql9.getString();
     
     var arrSelected = new Array();
     turnPage.strQueryResult  = easyQueryVer3(Strvar, 1, 0, 1);
     //�ж��Ƿ��ѯ�ɹ�
     //alert(turnPage.strQueryResult);
     if (!turnPage.strQueryResult) {
          return false;
     }
     //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
     turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
     //��ѯ�ɹ������ַ��������ض�ά����
     turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
     //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
     turnPage.pageDisplayGrid = AppntImpartGrid;
     //����SQL���
     turnPage.strQuerySql = Strvar;
     //���ò�ѯ��ʼλ��
     turnPage.pageIndex = 0;
     //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
     arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
     //����MULTILINE������ʾ��ѯ���
     displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

}

