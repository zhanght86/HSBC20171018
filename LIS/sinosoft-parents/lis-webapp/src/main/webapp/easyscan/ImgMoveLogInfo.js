/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : ����ǿ <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2006-12-26
 * @direction: Ӱ��Ǩ����־��ѯ���ű�
 * Modified By QianLy on 2007-01-26
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var turnPage = new turnPageClass();                 //ȫ�ֱ���, ��ѯ�����ҳ, ������
var turnPageTaskLogGrid = new turnPageClass();      //ȫ�ֱ���, Ǩ�����β�ѯ�����ҳ
var turnPageMoveErrorGrid = new turnPageClass();    //ȫ�ֱ���, ������־��ѯ�����ҳ

/*============================================================================*/

var showInfo;   //ȫ�ֱ���, ������ʾ����, ������

/*============================================================================*/

/**
 * �ύ�����, ���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(DealFlag, MsgContent)
{
    
    try { 
        showInfo.close();
        document.all("DocID").value = "";//���֮�����DocID,��ֹ�ظ�(����)���
        } catch (ex) {}
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
}

/**
 * ��ѯǨ��������Ϣ
 */
function queryTaskLogGrid()
{
//    var QuerySQL = "select MoveID, "
//                 +        "ManageCom, "
//                 +        "ToManageCom, "
//                 +        "StartDate, "
//                 +        "EndDate, "
//                 +        "TaskNumber, "
//                 +        "SuccNumber, "
//                 +        "case when tasktype = '0' then 'Ǩ�����'"
//                 +        "     when tasktype = '1' then '����Ǩ��'"
//                 +        "     when tasktype = '2' then '�ش����' end "
//                 +   "from ES_DOCMOVE_TASK "
//                 +  "where 1 = 1 "
//                 +     getWherePart("ManageCom", "OldManageCom", "like")
//                 +     getWherePart("ToManageCom", "NewManageCom", "like")
//                 +     getWherePart("StartDate", "StartDate")
//                 +     getWherePart("EndDate", "EndDate")
//                 +  "order by MoveID desc";
    
  	var  OldManageCom0 = window.document.getElementsByName(trim("OldManageCom"))[0].value;
  	var  NewManageCom0 = window.document.getElementsByName(trim("NewManageCom"))[0].value;
  	var  StartDate0 = window.document.getElementsByName(trim("StartDate"))[0].value;
  	var  EndDate0 = window.document.getElementsByName(trim("EndDate"))[0].value;
	var sqlid0="ImgMoveLogInfoSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("easyscan.ImgMoveLogInfoSql"); //ָ��ʹ�õ�properties�ļ���
	mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
	mySql0.addSubPara(OldManageCom0);//ָ������Ĳ���
	mySql0.addSubPara(NewManageCom0);//ָ������Ĳ���
	mySql0.addSubPara(StartDate0);//ָ������Ĳ���
	mySql0.addSubPara(EndDate0);//ָ������Ĳ���
	var QuerySQL=mySql0.getString();
    
    //alert(QuerySQL);
    try
    {
        turnPageTaskLogGrid.pageDivName = "divTurnPageTaskLogGrid";
        turnPageTaskLogGrid.queryModal(QuerySQL, TaskLogGrid);
    }
    catch (ex)
    {
        alert("���棺��ѯǨ��������Ϣ�����쳣�� ");
    }
}

/*============================================================================*/

/**
 * ��ѯ���δ�����Ϣ
 * ����Ӷ�OldManageCom,NewManageCom,StartDate,DocID��EndDate�ĳ�ʼ��,Ϊ��֧���ش�
 */
function queryMoveErrorGrid()
{
    var nSelNo;
    try
    {
        nSelNo = TaskLogGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo != null && nSelNo >= 0)
    {
        var sMoveID;
        try
        {
            sMoveID = TaskLogGrid.getRowColData(nSelNo, 1);
            document.all("OldManageCom").value = TaskLogGrid.getRowColData(nSelNo, 2);
            document.all("NewManageCom").value = TaskLogGrid.getRowColData(nSelNo, 3);
            document.all("StartDate").value = TaskLogGrid.getRowColData(nSelNo, 4);
            document.all("EndDate").value = TaskLogGrid.getRowColData(nSelNo, 5);
            document.all("DocID").value = "";
        }
        catch (ex) {}
        if (sMoveID != null && sMoveID.trim() != "")
        {
//            var QuerySQL = "select MoveID, "
//                         +        "DocID, "
//                         +        "FileDate, "
//                         +        "replace(Filepath, '\', '\\') "
//                         +   "from ES_DOCMOVE_LOG a "
//                         +  "where 1 = 1 "
//                         +    "and MoveID = '" + sMoveID + "' "
//                         +    "and Flag = '1' "//Flag 1:���� 0:�ش�����ȷ
//                         //�Ƿ����ش�֮��ɹ���,����гɹ���¼�Ͳ�����ʾ����,�����Ժ������δ���������ȷ��֧����,��Ҫ�޸�����
//                         +    "and not exists (select 'X' from ES_DOCMOVE_LOG where docid = a.docid and flag = '0')"
//                         +     getWherePart("ManageCom", "OldManageCom", "like")
//                         +     getWherePart("ToManageCom", "NewManageCom", "like")
//                         +  "order by MoveID asc";
            
          	var  OldManageCom1 = window.document.getElementsByName(trim("OldManageCom"))[0].value;
          	var  NewManageCom1 = window.document.getElementsByName(trim("NewManageCom"))[0].value;
        	var sqlid1="ImgMoveLogInfoSql1";
        	var mySql1=new SqlClass();
        	mySql1.setResourceName("easyscan.ImgMoveLogInfoSql"); //ָ��ʹ�õ�properties�ļ���
        	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
        	mySql1.addSubPara(sMoveID);//ָ������Ĳ���
        	mySql1.addSubPara(OldManageCom1);//ָ������Ĳ���
        	mySql1.addSubPara(NewManageCom1);//ָ������Ĳ���
        	var QuerySQL=mySql1.getString();
        	
            //alert(QuerySQL);
            try
            {
                turnPageMoveErrorGrid.pageDivName = "divTurnPageMoveErrorGrid";
                turnPageMoveErrorGrid.queryModal(QuerySQL, MoveErrorGrid);
                divTurnPageResend.style.display = "";
            }
            catch (ex)
            {
                alert("���棺��ѯ���δ�����Ϣ�����쳣�� ");
            }
        }
    } //nSelNo >= 0
}

/**
 * ��ʼ��Ҫ�ش���DocID��
 */
function initDocID()
{
    var nSelNo;
    try
    {
        nSelNo = MoveErrorGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo != null && nSelNo >= 0)
    {
        try
        {
            document.all("DocID").value = MoveErrorGrid.getRowColData(nSelNo, 2);
        }
        catch (ex) {}
    }
}

/**
 * �ύ�ش�����
 */
function resend()
{
    var docid = document.all("DocID").value;
    if(docid == null || docid ==""){
        return;
    }
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
    document.forms(0).action = "ImgTransferMoveSave.jsp";
    document.forms(0).submit();
}

/*============================================================================*/


//<!-- JavaScript Document END -->
