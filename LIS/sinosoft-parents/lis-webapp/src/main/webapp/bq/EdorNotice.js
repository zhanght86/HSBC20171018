//�������ƣ�EdorApprove.js
//�����ܣ���ȫ����
//�������ڣ�2005-05-08 15:20:22
//������  ��sinosoft
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();


/**
*  ��ѯ��ȫ������Ϣ
*  ����: ��ѯ��ȫ������Ϣ
*/
function initQuery()
{
    var EdorAcceptNo    = document.all('EdorAcceptNo').value;

    if (EdorAcceptNo == null || EdorAcceptNo == "")
    {
        alert("��ȫ�����Ϊ�գ�");
        return;
    }
    
 
    mySql=new SqlClass();
    mySql.setResourceName("bq.EdorNotice");
    mySql.setSqlId("EdorNoticeSql1");
    mySql.addSubPara(EdorAcceptNo); 
    
    var brr = easyExecSql(mySql.getString(),1,0,1);
    if ( brr )
    {
        brr[0][0]==null||brr[0][0]=='null'?'0':fm.OtherNo.value     = brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.OtherNoTypeName.value = brr[0][1];
        brr[0][3]==null||brr[0][2]=='null'?'0':fm.EdorAppName.value = brr[0][2];
        brr[0][4]==null||brr[0][3]=='null'?'0':fm.ApptypeName.value     = brr[0][3];
        brr[0][5]==null||brr[0][4]=='null'?'0':fm.ManageComName.value   = brr[0][4];
        brr[0][6]==null||brr[0][5]=='null'?'0':fm.EdorState.value   = brr[0][5];
        brr[0][7]==null||brr[0][6]=='null'?'0':fm.OtherNoType.value = brr[0][6];
        brr[0][9]==null||brr[0][7]=='null'?'0':fm.Apptype.value     = brr[0][7];
        brr[0][10]==null||brr[0][8]=='null'?'0':fm.ManageCom.value   = brr[0][8];

    }
    else
    {
        alert("��ȫ�����ѯʧ�ܣ�");
        return;
    }

    showEdorItemList(EdorAcceptNo);
}


/**
*  ��ѯ��ȫ��Ŀ�б�
*  ����: ��ѯ��ȫ��Ŀ�б�
*/
function showEdorItemList(tEdorAcceptNo)
{
 
    mySql=new SqlClass();
    mySql.setResourceName("bq.EdorNotice");
    mySql.setSqlId("EdorNoticeSql3");
    mySql.addSubPara(tEdorAcceptNo);     
    
    var drr = easyExecSql(mySql.getString(), 1, 0,"","",1);
    if ( !drr )
    {
        alert("����������û�б�ȫ��Ŀ��");
        return;
    }
    else
    {
        turnPage2.pageDivName = "divTurnPageEdorItemGrid";
        turnPage2.queryModal(mySql.getString(), EdorItemGrid);
    }
}

/**
*  ȡ����ȫ��Ŀ��Ϣ
*  ����: ȡ����ȫ��Ŀ��Ϣ
*/
function getEdorItemInfo()
{
    var tSel = EdorItemGrid.getSelNo() - 1;

    fm.EdorNo.value         = EdorItemGrid.getRowColData(tSel, 1);
    fm.EdorType.value       = EdorItemGrid.getRowColData(tSel, 14);
    fm.ContNo.value         = EdorItemGrid.getRowColData(tSel, 3);
    fm.InsuredNo.value      = EdorItemGrid.getRowColData(tSel, 4);
    fm.PolNo.value          = EdorItemGrid.getRowColData(tSel, 5);
    fm.EdorItemAppDate.value       = EdorItemGrid.getRowColData(tSel, 6);
    fm.EdorAppDate.value           = EdorItemGrid.getRowColData(tSel, 6);
    fm.EdorValiDate.value          = EdorItemGrid.getRowColData(tSel, 7);
    fm.EdorItemState.value  = EdorItemGrid.getRowColData(tSel, 11);
    fm.MakeDate.value       = EdorItemGrid.getRowColData(tSel, 12);
    fm.MakeTime.value       = EdorItemGrid.getRowColData(tSel, 13);
 
}



/**
 * �ύ�����, ���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(DealFlag, MsgContent, OtherFlag)
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
            checkEdorPrint();
            top.opener.easyQueryClickSelf();
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
* showCodeList �Ļص�����
*/
function afterCodeSelect(sCodeListType, oCodeListField)
{
    sCodeListType = sCodeListType.toLowerCase();
    if (sCodeListType == "edornoticeidea")
    {
        if (oCodeListField.value == "2")
        {
            try
            {

                document.all("divPayNotice").style.display = "";
                    document.all('ApproveContent').value = "";
            }
            catch (ex) {}
        }
        else
        {
            try
            {
                document.all("divPayNotice").style.display = "none";
            }
            catch (ex) {}
        }
         if (oCodeListField.value == "1")
        {
            try
            {

                document.all("divPayPassNotice").style.display = "";
                    document.all('ApproveContent').value = "";
            }
            catch (ex) {}
        }
        else
        {
            try
            {
                document.all("divPayPassNotice").style.display = "none";
            }
            catch (ex) {}
        }
    } //EdorApproveReason
}

/*============================================================================*/

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



function InvaliNotice()
{
    var tContNo    = document.all('OtherNo').value;
    var EdorAcceptNo    = document.all('EdorAcceptNo').value;
     mySql=new SqlClass();
    mySql.setResourceName("bq.EdorNotice");
    mySql.setSqlId("EdorNoticeSql5");
    mySql.addSubPara(tContNo); 
    mySql.addSubPara(EdorAcceptNo);     
    var sResult;
    sResult = easyExecSql(mySql.getString(), 1, 0,"","",1);
    if(sResult == null){
        alert("��ѯ��ȫ�ܾ�֪ͨ����Ϣʧ��,����ѡ�񺯼������Լ�������������");
        return;
    }
    document.all('PrtSeq').value = sResult[0][0];
    fm.action = "./EdorNoticePrintSave.jsp?";
    fm.target = "f1print";
    fm.submit();
}

function InvaliPassNotice()
{
    var tContNo    = document.all('OtherNo').value;
    var EdorAcceptNo    = document.all('EdorAcceptNo').value;
     mySql=new SqlClass();
    mySql.setResourceName("bq.EdorNotice");
    mySql.setSqlId("EdorNoticeSql4");
    mySql.addSubPara(tContNo); 
    mySql.addSubPara(EdorAcceptNo);       
   
    var sResult;
    sResult = easyExecSql(mySql.getString(), 1, 0,"","",1);
    if(sResult == null){
        alert("��ѯ��ȫ���֪ͨ����Ϣʧ��,����ѡ�񺯼������Լ�������������");
        return;
    }
    document.all('PrtSeq').value = sResult[0][0];
    fm.action = "./EdorNoticePrintSave.jsp?";
    fm.target = "f1print";
    fm.submit();
}

function ApproveCancel()
{
    document.all('ApproveFlag').value = "";
    document.all('ApproveContent').value = "";
}

/**
*  ��ȫ�����ύ
*  ����: ��ȫ�����ύ
*/
function ApproveSubmit()
{
    if (fm.EdorAcceptNo.value == "" )
    {
        alert("�޷���ȡ��ȫ���������Ϣ����ȫ��������ʧ�ܣ�");
        return;
    }
    var tApproveFlag        = fm.ApproveFlag.value;
    var tApproveContent     = fm.ApproveContent.value;


     if ((tApproveFlag == "" || tApproveFlag == null))
    {
        alert("��ѡ�񺯼�����!");
        return;
    }
          
     if ((tApproveFlag == "2" || tApproveFlag == "1") && (tApproveContent == null || tApproveContent.trim() == ""))
    {
        alert("��¼�뺯�����ݣ� ");
        return;
    }
    //�߼�
     mySql=new SqlClass();
    mySql.setResourceName("bq.EdorNotice");
    mySql.setSqlId("EdorNoticeSql6");
    mySql.addSubPara(fm.OtherNo.value);    
    mySql.addSubPara(fm.EdorAcceptNo.value);  
    var sResult;
    sResult = easyExecSql(mySql.getString(), 1, 0,"","",1);
    if(sResult&&document.all('ApproveFlag').value=='1' ){
        alert("����Ŀ�Ѿ���ȫ�·���˽��ۣ��������ظ��·����뷵��");
        return;
    }else if(sResult&&document.all('ApproveFlag').value=='2')
    	{
        alert("����Ŀ����δ���յĺ����������Ա�ȫ�ܾ����뷵��");
        return;   		    		
    	}
    	
    //BQ33 ��ȫ��ֹ֪ͨ�飬BQ37 ��ȫ���֪ͨ��
    mySql=new SqlClass();
    mySql.setResourceName("bq.EdorNotice");
    mySql.setSqlId("EdorNoticeSql7");
    mySql.addSubPara(fm.OtherNo.value);    
    mySql.addSubPara(fm.EdorAcceptNo.value);     
    sResult = easyExecSql(mySql.getString(), 1, 0,"","",1);
    if(sResult&&document.all('ApproveFlag').value=='1' ){
        alert("����Ŀ�Ѿ���ȫ��ֹ���������ڽ�����ˣ��뷵��");
        return;
    }else if(sResult&&document.all('ApproveFlag').value=='2')
    {
    	  alert("����Ŀ�Ѿ���ȫ�·���˽��ۣ��������ظ��·����뷵��");
        return;   	
    }
    if(document.all('ApproveFlag').value=='2')
    {
    	if(!confirm("�·���ȫ�ܾ�֪ͨ�齫��ʹ������Ŀ��ȫ��ֹ���Ƿ����"))
    	{
    		  return;   	
    		}
    }
               

    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
   // showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "./EdorNoticeSave.jsp";
    fm.target="fraSubmit";  //�ύ���ƶ��Ŀ��
    fm.submit();
}

function InitApproveContent()
{
	  var tContNo    = document.all('OtherNo').value;
    var EdorAcceptNo    = document.all('EdorAcceptNo').value;
     mySql=new SqlClass();
    mySql.setResourceName("bq.EdorNotice");
    mySql.setSqlId("EdorNoticeSql2");
    mySql.addSubPara(tContNo);
    mySql.addSubPara(EdorAcceptNo);    
    var sResult;
    sResult = easyExecSql(mySql.getString(), 1, 0,"","",1);
    if(sResult){
       fm.ApproveContent.value=sResult[0][0];
    }	
	
	}