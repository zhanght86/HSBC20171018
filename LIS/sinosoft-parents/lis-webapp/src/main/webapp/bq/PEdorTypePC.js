// ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var theFirstValue="";
var theSecondValue="";
var sqlresourcename = "bq.PEdorTypePCInputSql";

function verify() {

  //if (trim(fm.BankAccNo.value)!=trim(fm.BankAccNoAgain.value)) {
   // alert("�����˺Ų�һ�£���ȷ�ϣ�");
    //return false;
  //}
  if (trim(fm.BankCode.value)=="0101") {
    if (trim(fm.BankAccNo.value).length!=19) {
      alert("�������е��˺ű�����19λ�����֣����һ���Ǻţ�*����Ҫ��");
      return false;
    }
  }
  if(trim(fm.BankAccNo.value)==null||trim(fm.BankAccNo.value)=="")
  {
      alert("�����ʺŲ���Ϊ��!");
      return false;
  }
  if(trim(document.all('AppntName').value)!=trim(document.all('AccName').value))
   {
          alert("�� ����Ͷ������������ͬ!");
          return false;
   }

 if(trim(document.all('AppntName').value)!=trim(document.all('AccName').value)){
    if (!confirm("�˻����ƺ�Ͷ�������Ʋ�һ������ȷ�������ύ����ȡ���򷵻ظ��ģ�"))
      return false;
  }
 if(fm.PayLocation.value=="0" )
  {
            //���л��������֧��
     if(fm.BankCode.value == null || fm.BankCode.value == ""
             || fm.BankAccNo.value == null || fm.BankAccNo.value == ""
             || fm.AccName.value == null || fm.AccName.value == "")
     {
                alert("�����ո�����Ϣ������!");
                return false ;
     }
  } 

  return true;
}

function edorTypePCSave()
{
  if(document.all('PayLocation').value == "0"){
    if (verify() == false) return false;
  }else{
  	//���ɷ�����Ϊ���꽻���������½�ʱϵͳֻĬ�Ͻɷѷ�ʽΪ�����Զ�ת��
//  	var strsql = "select payintv  from lcpol where contno='"+fm.ContNo.value+"' and polno=mainpolno";
    
    var strsql = "";
	var sqlid1="PEdorTypePCInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	strsql=mySql1.getString();
    
    var aResult = easyExecSql(strsql,1,0);
    if(aResult!=null && aResult.length>0)
    {
    	if((aResult[0][0]=='1'|| aResult[0][0]=='3' || aResult[0][0]=='6') && document.all('PayLocation').value != "0")
    	{
    		alert("�ɷ�����Ϊ���꽻���������½�ʱϵͳֻĬ�Ͻɷѷ�ʽΪ�����Զ�ת��");
    		return false;
    	}
    }
  }
  
  //var accountname = document.all('AccName').value;
  //if (accountname!= document.all('AppntName').value)
  //{
  //     alert("�ʻ�������Ͷ����������һ�£�");
  //}
  searchOtherContTheAppntAssociated();

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
            initBankInfo();
            top.opener.getEdorItemGrid();
        }
        catch (ex) {}
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

function afterQuery( arrQueryResult )
{
}

function afterCodeSelect( cCodeName, Field )
{
    //alert(cCodeName);
    try {
        if( cCodeName == "paylocation" )
        {
            if (document.all("PayLocation").value=="0") {
                divBank.style.display = "";
            } else {
                divBank.style.display = "none";
            }
        }
    }
    catch( ex ) {
    }
}

function initBankInfo()
{
     //var i = 0;

   var strSql = "";
/*   strSql = "select AppntNo,AppntName,Prem,Amnt,PayMode,BankCode,BankAccNo,AccName,PayLocation from LPCont where 1 =1" + " "
              + getWherePart('ContNo')
              + getWherePart('EdorNo')
              + getWherePart('EdorType')
 //alert(strSql);
*/   
	var sqlid2="PEdorTypePCInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	mySql2.addSubPara(fm.EdorNo.value);
	mySql2.addSubPara(fm.EdorType.value);
	strSql=mySql2.getString();
   
   var arrResult = easyExecSql(strSql, 1, 0);
   if (arrResult == null)
   {
       //alert("û����Ӧ�ı�����Ϣ");
           document.all('BankCode').value = "";
           document.all('BankAccNo').value = "";
           document.all('AccName').value = "";
           document.all('PayLocation').value = "";
   }
   else
   {
       displayBank(arrResult);
   }

   //turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);
     //document.all('fmtransact').value = "QUERY||PayLocation";
     //fm.submit();

}

function displayBank(arrResult)
{
   document.all('BankCode').value = arrResult[0][5];
   document.all('BankAccNo').value = arrResult[0][6];
   //document.all('BankAccNoAgain').value = arrResult[0][6];
   document.all('AccName').value = arrResult[0][7];
   document.all('PayLocation').value = arrResult[0][8];
}

function queryCustomerInfo()
{
  var tContNo=top.opener.document.all('ContNo').value;

    var strSQL="";
    //alert("------"+tContNo+"---------");
    if(tContNo!=null || tContNo !='')
      {
/*      var strSQL =" Select a.appntno,'Ͷ����',a.appntname,a.idtype||'-'||x.codename,a.idno,a.appntsex||'-'||sex.codename,a.appntbirthday From lcappnt a "
                                        +" Left Join (Select code,codename From ldcode Where codetype='idtype') x On x.code = a.idtype "
                                        +" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = a.appntsex  Where contno='"+tContNo+"'"
                                        +" Union"
                                        +" Select i.insuredno,'������',i.name,i.IDType||'-'||xm.codename,i.IDNo,i.Sex||'-'||sex.codename,i.Birthday From lcinsured i "
                                        +" Left Join (Select code,codename From ldcode Where codetype='idtype') xm On xm.code = i.idtype "
                                        +" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = i.sex Where contno='"+tContNo+"'";
//alert("-----------"+strSQL+"------------");
*/    
    var strSQL ="";
    var sqlid3="PEdorTypePCInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	mySql3.addSubPara(fm.EdorNo.value);
	strSQL=mySql3.getString();
    
    }
    else
    {
        alert('û����Ӧ��Ͷ���˻򱻱�����Ϣ��');
    }
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

function searchOtherContTheAppntAssociated()
{
   try
   {
       var i;
       var tFlag1 = 0;
       var tFlag2 = 0;
       var content1 = "�Ե�ǰͶ����ΪͶ���˵������������У�";
       //var content2 = "�Ե�ǰͶ����Ϊ�����˵������������У�";

       var sContNo = document.getElementsByName("ContNo")[0].value;
       var QuerySQL;
/*       QuerySQL = "select distinct ContNo "
                +   "from LCCont "
                +  "where 1 = 1 "
                +    "and ContNo <> '" + sContNo + "' "
                +     getWherePart("AppntNo", "AppntNo")
                +    "and AppFlag = '1'";
       //alert(QuerySQL);
*/       
    var sqlid4="PEdorTypePCInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(sContNo);//ָ������Ĳ���
	mySql4.addSubPara(fm.AppntNo.value);
	QuerySQL=mySql4.getString();
       
       var arrResult1 = easyExecSql(QuerySQL,1,0);
       if ((arrResult1!=null) && (arrResult1.length!=0))
       {
          tFlag1 = 1;
          for (i=0;i<arrResult1.length;i++)
          {
               content1 = content1 + arrResult1[i][0] + " ";
          }
       }
       var tFlag = 0;
       var content = "";
       if (tFlag1==1)
       {
          tFlag = 1;
          content = content1;
       }
          //alert(tFlag);
       if (tFlag==1)
       {
            alert(content);
         }
         return;
  }
  catch(ex)
  {
     alert("PEdorTypePCInit.jsp-->��ѯ��ǰͶ���������������Ĺ���ʱ��������!");
     return;
  }
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