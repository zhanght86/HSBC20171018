//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
//var turnPage1 = new turnPageClass();
var sqlresourcename = "bq.PEdorTypeLRInputSql";

function returnParent()
{
  top.opener.getEdorItemGrid();
  top.close();
  top.opener.focus();
}

function edorTypeLRQuery()
{
    alert("Wait...");
}
function edorTypeLRSave()
{
    var tReason = fm.GoonGetMethod1.value;
    var tGetMoney = fm.GetMoney.value;
    
    if(tReason == null || tReason =="")
    {
        alert("����ѡ�񲹷�ԭ��");
        fm.GoonGetMethod1.focus();
        return;
    }
    
    if(trim(tGetMoney)!="" && !isNumeric(tGetMoney))
		{
				errorMessage("������Ϸ�������");
				fm.GetMoney.focus();
				fm.GetMoney.select();
				return;
		}
		
		if(tGetMoney < 0)
		{
			alert("���ѽ���С��0��");
			fm.GetMoney.focus();
			fm.GetMoney.select();
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

    document.all('fmtransact').value = "INSERT";
    fm.submit();

}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
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
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    showmoney();
    queryBackFee();
    //alert(FlagStr);
    //showDiv(operateButton,"true");
    //showDiv(inputButton,"false");
    //ִ����һ������
  }
}

function showmoney(){
		 
//     		 var Str3 = "select getmoney from lpedoritem where edorno = '" + document.all('EdorNo').value + "' and edortype = 'LR'";
    		 
    var Str3 = "";
	var sqlid1="PEdorTypeLRInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(document.all('EdorNo').value);//ָ������Ĳ���
	Str3=mySql1.getString();
    		 
    		 var arr = easyExecSql(Str3);
    	 	 if(arr != null)
    	 	 {
         	  document.all('GetMoney').value = arr[0][0];
    		 }
    		 else
    		 {
         	  document.all('GetMoney').value = "10.0";
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
function initLRQuery()
{
    //��ѯ��¼����
//    var strsql = "select edorstate from lpedoritem where edorno = '" + document.all('EdorNo').value + "' and edortype = 'LR' and contno = '"+document.all('ContNo').value+"'";
    
    var strsql = "";
	var sqlid2="PEdorTypeLRInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(document.all('EdorNo').value);//ָ������Ĳ���
	mySql2.addSubPara(document.all('ContNo').value);
	strsql=mySql2.getString();
    
    var ret = easyExecSql(strsql);
    var state = ret[0][0];
    if(state != "3")//�ȴ�¼�롣
    {
/*        strsql = "select nvl(sum(getmoney),0.0) from ljsgetendorse "
               + " where contno = '"+fm.ContNo.value+"' "
               + " and endorsementno = '"+fm.EdorNo.value+"' "
               + " and feeoperationtype = '"+fm.EdorType.value+"' "
               + " and feefinatype = 'GB' and subfeeoperationtype = 'P012' ";
*/        
	var sqlid3="PEdorTypeLRInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	mySql3.addSubPara(fm.EdorNo.value);
	mySql3.addSubPara(fm.EdorType.value);
	strsql=mySql3.getString();
        
        var brr = easyExecSql(strsql);
        if(brr)
        {
            fm.GetMoney.value = pointTwo(brr[0][0]);
        }
        //��ѯ��¼�벹��ԭ��
/*        strsql = "select trim(edorreasoncode),edorreason from lpedoritem "
           + " where contno = '"+fm.ContNo.value+"' "
           + " and edorno = '"+fm.EdorNo.value+"' "
           + " and edortype = '"+fm.EdorType.value+"' ";
*/        
    var sqlid4="PEdorTypeLRInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	mySql4.addSubPara(fm.EdorNo.value);
	mySql4.addSubPara(fm.EdorType.value);
	strsql=mySql4.getString();
        
        brr = easyExecSql(strsql);
        if(brr)
        {
            fm.GoonGetMethod1.value = brr[0][0];
            fm.GoonGetMethod1Name.value = brr[0][1];
        }
      }
      else
      {
     			document.all('GetMoney').value = "10.0";
     	}
}

function queryLostTimes()
{
	var tQuerySQL,arrResult;
/*	tQuerySQL = "select nvl(LostTimes,0)||'��' "
             +   "from lCCont "
             +  "where 1 = 1 "
             +     getWherePart("ContNo", "ContNo");
*/  
 	var sqlid5="PEdorTypeLRInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	tQuerySQL=mySql5.getString();
  
  try
    {
        arrResult = easyExecSql(tQuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("���棺��ѯ����������Ϣ�����쳣�� ");
        return;
    }
    if(arrResult != null)
    {
    	fm.LostTimes.value = arrResult[0][0];
    }
}

function initDivLayer()
{
    var sAppobj;
    try
    {
        sAppobj = document.getElementsByName("AppObj")[0].value;
    }
    catch (ex) {}
    if (sAppobj != null)
    {
        if (sAppobj.trim() == "I")
        {
            try
            {
                showOneCodeName('PEdorType', 'EdorType', 'EdorTypeName');
                document.all("divGetMoneyTitle").style.display = "";
                document.all("divGetMoneyInput").style.display = "";
            }
            catch (ex) {}
        }
        else if (sAppobj.trim() == "G")
        {
            try
            {
                showOneCodeName('GEdorType', 'EdorType', 'EdorTypeName');
                document.all("divGetMoneyTitle").style.display = "none";
                document.all("divGetMoneyInput").style.display = "none";
            }
            catch (ex) {}
        }
    }
}