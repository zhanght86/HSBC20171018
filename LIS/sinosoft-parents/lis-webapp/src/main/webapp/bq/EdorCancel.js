//�������ƣ�EdorCancel.js
//�����ܣ���ȫ����
//�������ڣ�2005-05-08 09:20:22
//������  ��sinosoft
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
var turnPage = new turnPageClass();
var reasonatrr=""  ;
var sqlresourcename = "bq.EdorCancelInputSql";

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
            if (document.all('ActionFlag').value == "CSUBMIT")
            {
                var DelFlag = fm.DelFlag.value;
                //�˹��˱�ȷ��
                if (DelFlag == "EdorApp")
                {
                    //���뼶 DoNothing
                    returnParent();
                }
                else if (DelFlag == "EdorMain")
                {
                    //������
                    divEdorMainInfo.style.display='none';
                    divEdorItemInfo.style.display='none';
                    showEdorMainList();

                }
                else if (DelFlag == "EdorItem")
                {
                    //��ȫ��Ŀ��
                    showEdorItemList();
                    if (EdorItemGrid.mulLineCount == 0)
                    {
                        showEdorMainList();
                    }
                }
            }
            top.opener.jquerySelfGrid();
        }
        catch (ex) {}
    }
}

/*********************************************************************
 *  ��ѯ��ȫ������Ϣ
 *  ����: ��ѯ��ȫ������Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function initQuery()
{

    var EdorAcceptNo    = document.all('EdorAcceptNo').value;
    var MissionID       = document.all('MissionID').value
    var SubMissionID    = document.all('SubMissionID').value

    if(EdorAcceptNo == null || EdorAcceptNo == "")
    {
        alert("��ȫ�����Ϊ�գ�");
        return;
    }
    if(MissionID == null || MissionID == "")
    {
        alert("�����Ϊ�գ�");
        return;
    }
    if(SubMissionID == null || SubMissionID == "")
    {
        alert("�������Ϊ�գ�");
        return;
    }

    var strSQL;

    //��ѯ��ȫ������Ϣ
/*    strSQL =  " select OtherNo, (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'edornotype' and code = OtherNoType), "
            + " GetMoney,EdorAppName, "
            + " (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'edorapptype' and code = Apptype), "
            + " (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'station' and code = ManageCom),edorstate,othernotype, "
            + " GetInterest "
            + " from LPEdorApp "
            + " where EdorAcceptNo = '" + EdorAcceptNo + "' ";
*/
	var sqlid1="EdorCancelInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(EdorAcceptNo);//ָ������Ĳ���
	strSQL=mySql1.getString();

    var brr = easyExecSql(strSQL);

    if ( brr )
    {
        brr[0][0]==null||brr[0][0]=='null'?'0':fm.OtherNo.value     = brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.OtherNoTypeName.value = brr[0][1];
        brr[0][2]==null||brr[0][2]=='null'?'0':fm.GetMoney.value    = brr[0][2];
        brr[0][3]==null||brr[0][3]=='null'?'0':fm.EdorAppName.value = brr[0][3];
        brr[0][4]==null||brr[0][4]=='null'?'0':fm.Apptype.value     = brr[0][4];
        brr[0][5]==null||brr[0][5]=='null'?'0':fm.ManageCom.value   = brr[0][5];
        brr[0][6]==null||brr[0][6]=='null'?'0':fm.EdorState.value   = brr[0][6];
        brr[0][7]==null||brr[0][7]=='null'?'0':fm.OtherNoType.value = brr[0][7];
        brr[0][8]==null||brr[0][8]=='null'?'0':fm.GetInterest.value = brr[0][8];
    }
    else
    {
        alert("��ȫ�����ѯʧ�ܣ�");
        return;
    }

    //showEdorMainList();    <!-- XinYQ commented on 2006-02-09 -->

}

/*********************************************************************
 *  ��ѯ��ȫ��Ŀ�б�
 *  ����: ��ѯ��ȫ��Ŀ�б�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
//function showEdorItemList()
//{
//  var tSel = EdorMainGrid.getSelNo() - 1;
//
//  var EdorNo = EdorMainGrid.getRowColData(tSel, 1);
//  var ContNo = EdorMainGrid.getRowColData(tSel, 2);
//  var EdorMainState = EdorMainGrid.getRowColData(tSel, 10);
//  document.all('EdorNo').value = EdorNo;
//  document.all('ContNo').value = ContNo;
//  document.all('EdorMainState').value = EdorMainState;
//
//  var strSQL;
//  strSQL =  " select EdorNo,"
//          + " (select distinct edorcode||'-'||edorname from lmedoritem m where  trim(m.edorcode) = trim(edortype)), "
//          + " ContNo,  InsuredNo, "
//          + " PolNo, EdorAppDate, EdorValiDate, "
//          + " nvl(ChgPrem, 0), nvl(ChgAmnt, 0), nvl(GetMoney, 0), nvl(GetInterest, 0),  "
//          + " (select c.codename from ldcode c where c.codetype = 'edorstate' and trim(c.code)=trim(EdorState)), "
//          + " EdorState, "
//          + " (select d.codename from ldcode d where d.codetype = 'edoruwstate' and trim(d.code)=trim(UWFlag)), "
//          + " UWFlag, makedate, maketime, edortype "
//          + " from LPEdorItem "
//          + " where LPEdorItem.EdorNo = '" + EdorNo + "'" ;
//  var drr = easyExecSql(strSQL);
//  if ( !drr )
//  {
//      document.all('DelFlag').value = "EdorMain";
//      initEdorItemGrid();
//      divEdorItemInfo.style.display='none';
//      //alert("����������û�б�ȫ��Ŀ��");
//      return;
//  }
//  else
//  {
//      //document.all('DelFlag').value = "EdorItem";
//      initEdorItemGrid();
//      turnPage.queryModal(strSQL, EdorItemGrid);
//      divEdorItemInfo.style.display='';
//  }
//}

/*********************************************************************
 *  ��ѯ��ȫ�����б�
 *  ����: ��ѯ��ȫ�����б�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
//function showEdorMainList()
//{
//  var EdorAcceptNo    = document.all('EdorAcceptNo').value;
//
//  //��ѯ��ȫ������Ϣ
//  strSQL =  " select a.EdorNo, a.ContNo, "
//              + " (select paytodate from lcpol p where p.contno = a.contno and p.polno = p.mainpolno), "
//              + " a.EdorAppDate, a.EdorValiDate, "
//              + " nvl(a.ChgPrem, 0), nvl(a.ChgAmnt, 0), nvl(a.GetMoney, 0), nvl(a.GetInterest, 0), "
//              + " (select c.codename from ldcode c where c.codetype = 'edorstate' and trim(c.code)=trim(a.EdorState)), "
//              + " a.EdorState, "
//              + " (select d.codename from ldcode d where d.codetype = 'edoruwstate' and trim(d.code)=trim(a.uwstate)), "
//              + " a.UWState "
//              + " from LPEdorMain a , LCCont b "
//              + " where 1=1 and a.ContNo  = b.ContNo "
//              + " and a.EdorAcceptNo = '" + EdorAcceptNo + "' ";
//  var crr = easyExecSql(strSQL);
//  if ( !crr )
//  {
//      document.all('DelFlag').value = "EdorApp";
//      initEdorMainGrid();
//      divEdorMainInfo.style.display='none';
//      //alert("��ȫ������û������������ֱ�ӳ�����ȫ���룡");
//      return;
//  }
//  else
//  {
//      document.all('DelFlag').value = "EdorMain";
//      initEdorMainGrid();
//      turnPage.queryModal(strSQL, EdorMainGrid);
//      divEdorMainInfo.style.display='';
//  }
//}

/*********************************************************************
 *  ȡ����ȫ��Ŀ��Ϣ
 *  ����: ȡ����ȫ��Ŀ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
//function getEdorItemInfo()
//{
//  var tSel = EdorItemGrid.getSelNo() - 1;
//  document.all('DelFlag').value = "EdorItem";
//
//  fm.EdorNo.value         = EdorItemGrid.getRowColData(tSel, 1);
//  fm.EdorType.value       = EdorItemGrid.getRowColData(tSel, 18);
//  fm.ContNo.value         = EdorItemGrid.getRowColData(tSel, 3);
//  fm.InsuredNo.value      = EdorItemGrid.getRowColData(tSel, 4);
//  fm.PolNo.value          = EdorItemGrid.getRowColData(tSel, 5);
//  fm.EdorItemState.value  = EdorItemGrid.getRowColData(tSel, 13);
//  fm.MakeDate.value       = EdorItemGrid.getRowColData(tSel, 16);
//  fm.MakeTime.value       = EdorItemGrid.getRowColData(tSel, 17);
//
//  //alert(fm.EdorNo.value);
//  //alert(fm.ContNo.value);
//  //alert(fm.EdorType.value);
//  //alert(fm.InsuredNo.value);
//  //alert(fm.PolNo.value);
//}


//<!-- XinYQ added on 2006-02-09 : �������޸�Ϊ������Ŀ��Ϣһ����ʾ : BGN -->
/*============================================================================*/

/*
 * ��ѯ��ȫ������������Ŀ��Ϣ
 */
function queryAppItemGrid()
{
/*    var QuerySQL = "select a.EdorNo, "
                 +        "a.ContNo, "
                 +        "a.InsuredNo, "
                 +        "a.PolNo, "
                 +        "a.EdorType || '-' || "
                 +        "(select distinct EdorName "
                 +           "from LMEdorItem "
                 +          "where 1 = 1 "
                 +            "and EdorCode = a.EdorType "
                 +            "and AppObj in ('I', 'B')), "
                 +        "a.EdorAppDate, "
                 +        "a.GetMoney, "
                 +        "(select CodeName "
                 +           "from LDCode "
                 +          "where CodeType = 'edorstate' and trim(code) = a.EdorState), "
                 +        "(select CodeName "
                 +           "from LDCode "
                 +          "where CodeType = 'edoruwstate' and trim(code) = a.UWFlag) "
                 +   "from LPEdorItem a "
                 +  "where 1 = 1 "
                 +  getWherePart("a.EdorAcceptNo", "EdorAcceptNo")
                 +  "order by a.EdorNo asc, a.EdorAppDate asc";
*/    
    var QuerySQL = "";
    var sqlid2="EdorCancelInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
	QuerySQL=mySql2.getString();
    
    try
    {
        turnPage.queryModal(QuerySQL, AppItemGrid);
    }
    catch (ex)
    {
        alert("���棺��ѯ��ȫ������������Ŀ��Ϣ�����쳣�� ");
        return;
    }
}

/*============================================================================*/
//<!-- XinYQ added on 2006-02-09 : �������޸�Ϊ������Ŀ��Ϣһ����ʾ : END -->

/*********************************************************************
 *  ��ȫ�����ύ
 *  ����: ��ȫ�����ύ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function cSubmit()
{
    reasonatrr="" ;
    var DelFlag = document.all('DelFlag').value;
    if (DelFlag == "EdorApp")
    {
        if (fm.EdorAcceptNo.value == "" )
        {
            alert("��ȫ�����Ϊ�գ� ");
            return;
        }
    }
    //else if (DelFlag == "EdorMain")
    //{
    //  if (fm.EdorNo.value == null || fm.EdorNo.value == "" ||
    //      fm.ContNo.value == null || fm.ContNo.value == "" )
    //  {
    //      alert("��ѡ��Ҫ����������������");
    //      return false;
    //  }
    //}
    //else if (DelFlag == "EdorItem")
    //{
    //  if (fm.EdorNo.value == null     || fm.EdorNo.value == "" ||
    //      fm.EdorType.value == null   || fm.EdorType.value == "" ||
    //      fm.ContNo.value == null     || fm.ContNo.value == "" ||
    //      fm.InsuredNo.value == null  || fm.InsuredNo.value == "" ||
    //      fm.PolNo.value == null      || fm.PolNo.value == "" )
    //  {
    //      if(window.confirm("ȷ��Ҫɾ�������������б�ȫ��Ŀ��?"))
    //      {
    //          document.all('DelFlag').value = "EdorMain";
    //      }
    //      else
    //      {
    //          return false;
    //      }
    //
    //  }
    //}

    //var tCancelReasonCode         = fm.CancelReasonCode.value;
    //var tCancelReasonContent  = fm.CancelReasonContent.value;
    //tCancelReasonCode = "t";
    //tCancelReasonContent = "t";
    //if(tCancelReasonCode == null || tCancelReasonCode == "")
    //{
    //  alert("��ѡ����ԭ�� ");
    //  return ;
    //}
/*
    if(tCancelReasonContent == null || tCancelReasonContent == "")
    {
        alert("��¼�볷����ע�� ");
        return ;
    }
*/
    if (!verifyInput2()) return;    //XinYQ added on 2006-02-09
    if(!verifyFormElements())return;
    //return;
    //G��	��������֧��¼�룩,��ע��Ϣ��¼��У��
    if(fm.MCanclReason.value==null || fm.MCanclReason.value =="")
    {
      alert("��ѡ��ĳ���ԭ��");
      return ;
    	
    	}
    
    if(fm.MCanclReason.value=="G" && fm.CancelReasonContent.value=="")
    {
    	alert("ѡ��ĳ���ԭ��Ϊ��������¼�볷����ע�� ");
      return ;
    }
    //alert(fm.ReasonAtrr.value) ;
    //return ;
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    document.all('ActionFlag').value = "CSUBMIT";
    fm.action = "./PEdorAppCancelSubmit.jsp";
    document.getElementById("fm").submit();
}

/*********************************************************************
 *  ��ȫ����ȡ��
 *  ����: ��ȫ����ȡ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function cCancel()
{
    document.all('CancelReasonCode').value = "";
    document.all('CancelReasonContent').value = "";
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


/**
 * ֱ�ӽ��뱣ȫ��Ŀ��ϸ
 */
function EdorDetailQuery()
{
    var nSelNo, sEdorType, sEdorState,sEdorNo;
    try
    {
        nSelNo = AppItemGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        alert("����ѡ����Ҫ�鿴��������Ŀ�� ");
        return;
    }
    else
    {
    	  sEdorNo = AppItemGrid.getRowColData(nSelNo, 1);
//    	  var strSql = "select edorstate,edortype,contno,EdorAppDate,EdorValiDate from lpedoritem where edorno='"+sEdorNo+"'  and EdorAcceptNo='"+fm.EdorAcceptNo.value+"'";
        
    var strSql = "";
    var sqlid3="EdorCancelInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(sEdorNo);//ָ������Ĳ���
	mySql3.addSubPara(fm.EdorAcceptNo.value);
	strSql=mySql3.getString();
        
        var arrResult = easyExecSql(strSql);
        if(arrResult==null || arrResult.length<=0)
        {
        	 alert("�˴�����û�б������Ϣ���� ");
           return;
        }
        try
        {
            sEdorType = arrResult[0][1];
            sEdorState = arrResult[0][0];
            if(sEdorState=="3")
            {
            	alert("�˴�����û�б������Ϣ���� ");
              return;
            }
            
            document.all('InsuredNo').value = AppItemGrid.getRowColData(nSelNo, 3);
            document.all('EdorNo').value = sEdorNo;
            document.all('ContNo').value = arrResult[0][2];
            document.all('EdorMainState').value = sEdorState;
            document.all('EdorType').value = sEdorType;
            document.all('EdorState').value = sEdorState;
            document.all('EdorAppDate').value = arrResult[0][3];
            document.all('EdorItemAppDate').value = arrResult[0][3];
            document.all('EdorValiDate').value = arrResult[0][4];
    
        }
        catch (ex) {}
        if (sEdorType == null || sEdorType.trim() == "")
        {
            alert("���棺�޷���ȡ��ȫ������Ŀ������Ϣ����ѯ��ȫ��ϸʧ�ܣ� ");
            return;
        }
        //alert(sEdorType);
        if (sEdorState != null && trim(sEdorState) == "0")
        {
            OpenWindowNew("../bqs/PEdorType" + sEdorType.trim() + ".jsp", "��ȫ��Ŀ��ϸ��ѯ", "left");    //bqs
        }
        else
        {
            OpenWindowNew("../bq/PEdorType" + sEdorType.trim() + ".jsp", "��ȫ��Ŀ��ϸ��ѯ", "left");    //bq
        }
    }
}

function afterCodeSelect(sCodeListType, oCodeListField)
{
    sCodeListType = sCodeListType.toLowerCase();
    if (sCodeListType == "edorcancelmreason")
    {
    	
    	
    	      try
            {
            	   var str = new Array('A','B','C','D','E','F','G');
                //str=('A','B','C','D','E','F','G');
                //alert(str);
                for(var t=0;t<str.length;t++)
                {
                	//alert("divAppCancel"+str[t]);
                	if(str[t]!=oCodeListField.value)
                	{
                		 document.all("divAppCancel"+str[t]).style.display = "none";
                	}
               }
                document.all("divAppCancel"+oCodeListField.value).style.display = "";
                
            }
            catch (ex) {}

    } //EdorApproveReason
}


function verifyFormElements()
{
	
	  var selectedIndex = -1;
    var i = 0;
    
    for (i=0; i<fm.SCanclReason.length; i++)
    {
        if (fm.SCanclReason[i].checked)
        {
            selectedIndex = i;
            //alert("��ѡ����� value �ǣ�" + fm.SCanclReason[i].value);
            fm.CancelReasonCode.value=fm.SCanclReason[i].value;
            return true;
        }
    }    
    if (selectedIndex < 0)
    {
        alert("��û��ѡ���κγ���ԭ��");
        return false ;
    }

}      
 