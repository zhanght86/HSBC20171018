/**
 * Created by IntelliJ IDEA��
 * User: jiyt
 * Date: 2012-6-25
 * Time: 9:42:15
 */

/*������ҳ��*/
var turnPage = new turnPageClass();
/*ϵͳ��ǰ������Ա*/
var mOperate = "";
/*����mysql��ѯ��*/
var mySql = new SqlClass();
/*������Ϣ����*/
var showInfo;
//var oldFunctionID="";
//var oldBusiNessTable="";
//var oldFieldCode="";
//var oldColWidth="";
//var oldColSerialNo="";
//var oldIsShow="";

function checkFunctionID()
{
    if(fm.FunctionID.value==null||fm.FunctionID.value=="")
    {
     alert("��ѡ��������");
     return false;
    }	
}
function queryClick()
{  
	initToBusiTableGrid();
/*	var strSQL="select ldwf.functionid, (select codename from ldcode where codetype = 'functionid' and code = ldwf.functionid), ldwf.businesstable,(select businesstablename from ldwftobusiness where businesstable = ldwf.businesstable and functionid = ldwf.functionid), ldwf.fieldcode, (select fieldcodename from ldwfbusifield where fieldcode=ldwf.fieldcode and businesstable=ldwf.businesstable ), ldwf.colwidth, ldwf.colserialno, ldwf.isshow, (case ldwf.isshow when '1' then '��' else '��' end) from ldwfmulline ldwf where 1=1 ";
	if (document.all('FunctionID').value != null && document.all('FunctionID').value !="")
	{
		strSQL=strSQL+" and ldwf.functionid ='"+document.all('FunctionID').value+"'";
	}
	if (document.all('BusiNessTable').value != null && document.all('BusiNessTable').value !="")
	{
		strSQL=strSQL+" and ldwf.BusiNessTable ='"+document.all('BusiNessTable').value+"'";
	}
	if (document.all('FieldCode').value != null && document.all('FieldCode').value !="")
	{
		strSQL=strSQL+" and ldwf.FieldCode ='"+document.all('FieldCode').value+"'";
	}
	if (document.all('ColWidth').value != null && document.all('ColWidth').value !="")
	{
		strSQL=strSQL+" and ldwf.ColWidth ='"+document.all('ColWidth').value+"'";
	}
	if (document.all('ColSerialNo').value != null && document.all('ColSerialNo').value !="")
	{
		strSQL=strSQL+" and ldwf.ColSerialNo ='"+document.all('ColSerialNo').value+"'";
	}
	if (document.all('IsShow').value != null && document.all('IsShow').value !="")
	{
		strSQL=strSQL+" and ldwf.IsShow ='"+document.all('IsShow').value+"'";
	}
	strSQL=strSQL+" order by ldwf.functionid,ldwf.colserialno,ldwf.fieldcode ";*/
	var strSQL = "";
	mySql = new SqlClass();
	  mySql.setResourceName("workflowmanage.LDWFMulLineSql");
	  mySql.setSqlId("LDWFMulLineSql1");
	  mySql.addSubPara(document.all('FunctionID').value);   	
	  mySql.addSubPara(document.all('BusiNessTable').value);      
	  mySql.addSubPara(document.all('FieldCode').value);
	  mySql.addSubPara(document.all('ColWidth').value);
	  mySql.addSubPara(document.all('ColSerialNo').value);
	  mySql.addSubPara(document.all('IsShow').value);
	  strSQL = mySql.getString();
    turnPage.queryModal(strSQL, ToBusiTableGrid);	
}
function saveClick()
{  
    if (verifyInput2() == false) return false; 
    var tfunctionid=document.all('FunctionID').value;
    var tbusinesstable=document.all('BusiNessTable').value;
    var tfieldcode=document.all('FieldCode').value;
//    var tSQL="select 1 from ldwfmulline  where functionid = '"+tfunctionid+"' and BusiNessTable = '"+tbusinesstable+"' and fieldcode= '"+tfieldcode+"'";     
    var tSQL = "";
    mySql = new SqlClass();
	  mySql.setResourceName("workflowmanage.LDWFMulLineSql");
	  mySql.setSqlId("LDWFMulLineSql2");
	  mySql.addSubPara(tfunctionid);   	
	  mySql.addSubPara(tbusinesstable);      
	  mySql.addSubPara(tfieldcode);
	  tSQL = mySql.getString();
    var arr = easyExecSql(tSQL);    
    if (arr>0)
    {
        alert("���ݿ��Ѿ����ڸü�¼,��ѡ���޸Ļ�ɾ������!");
        return false;
    }
    else
    {
        var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
        //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        fm.action = "LDWFMulLineSave.jsp?OperFlag=Insert";
        document.getElementById("fm").submit();	
    }
}
function updateClick()
{
    if (verifyInput2() == false) return false;
    var tfunctionid=document.all('FunctionID').value;
    var tbusinesstable=document.all('BusiNessTable').value;
    var tfieldcode=document.all('FieldCode').value;
//    var trSQL="select count(1) from ldwfmulline  where functionid = '"+tfunctionid+"' and BusiNessTable = '"+tbusinesstable+"' and fieldcode= '"+tfieldcode+"'";   
    var trSQL = "";
    mySql = new SqlClass();
	  mySql.setResourceName("workflowmanage.LDWFMulLineSql");
	  mySql.setSqlId("LDWFMulLineSql3");
	  mySql.addSubPara(tfunctionid);   	
	  mySql.addSubPara(tbusinesstable);      
	  mySql.addSubPara(tfieldcode);
	  trSQL = mySql.getString();
    var arr = easyExecSql(trSQL); 
    if (arr == 0)
    {
        alert("���ݿⲻ���ڸü�¼,��ѡ�񱣴����!");
        return false;
    }
    else
    {   
        var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
        //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        fm.action = "LDWFMulLineSave.jsp?OperFlag=Update";
        document.getElementById("fm").submit();	
    }
}
function deleteClick()
{ 
    if (verifyInput2() == false) return false;
    var tfunctionid=document.all('FunctionID').value;
    var tbusinesstable=document.all('BusiNessTable').value;
    var tfieldcode=document.all('FieldCode').value;
//    var strSQL="select count(1) from ldwfmulline  where functionid = '"+tfunctionid+"' and BusiNessTable = '"+tbusinesstable+"' and fieldcode= '"+tfieldcode+"'";     
    var strSQL = "";
    mySql = new SqlClass();
	  mySql.setResourceName("workflowmanage.LDWFMulLineSql");
	  mySql.setSqlId("LDWFMulLineSql4");
	  mySql.addSubPara(tfunctionid);   	
	  mySql.addSubPara(tbusinesstable);      
	  mySql.addSubPara(tfieldcode);
	  strSQL = mySql.getString();
    var arr = easyExecSql(strSQL); 
    if (arr == 0)
    {
        alert("���ݿⲻ���ڸü�¼,�޷�����ɾ������!");
        return false;
    }
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "LDWFMulLineSave.jsp?OperFlag=Delete";
    document.getElementById("fm").submit();
}
function showData()
{  
   var selno = ToBusiTableGrid.getSelNo() - 1;
   if (selno < 0)
   {
	  alert("û��ѡ������!");
	  return false;
   }
   try 
   { 
	   fm.FunctionID.value        = ToBusiTableGrid.getRowColData(selno, 1);
	   fm.FunctionIDName.value    = ToBusiTableGrid.getRowColData(selno, 2);
	   fm.BusiNessTable.value     = ToBusiTableGrid.getRowColData(selno, 3);
	   fm.BusiNessTableName.value = ToBusiTableGrid.getRowColData(selno, 4);
	   fm.FieldCode.value         = ToBusiTableGrid.getRowColData(selno, 5);
	   fm.FieldCodeName.value     = ToBusiTableGrid.getRowColData(selno, 6);
	   fm.ColWidth.value          = ToBusiTableGrid.getRowColData(selno, 7);
	   fm.ColSerialNo.value       = ToBusiTableGrid.getRowColData(selno, 8);
	   fm.IsShow.value            = ToBusiTableGrid.getRowColData(selno, 9);
	   fm.IsShowName.value        = ToBusiTableGrid.getRowColData(selno, 10);
	   
//	   oldFunctionID=fm.FunctionID.value;
//	   oldBusiNessTable=fm.BusiNessTable.value;
//	   oldFieldCode=fm.FieldCode.value;
//	   oldColWidth=fm.ColWidth.value;
//	   oldColSerialNo=fm.ColSerialNo.value;
//	   oldIsShow=fm.IsShow.value;
   }
   catch(e){
	   alert("�쳣"+e.message);
   }
}
function submitForm()
{
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.hideOperate.value = mOperate;
    document.getElementById("fm").submit();
}
function afterSubmit(FlagStr, content)
{
    if (typeof(showInfo) == "object")
    {
        showInfo.close();
        if (typeof(showInfo.parent) == "object" && typeof(showInfo.parent) != "unknown")
        {
            showInfo.parent.focus();
            if (typeof(showInfo.parent.parent) == "object" && typeof(showInfo.parent.parent) != "unknown")
            {
                showInfo.parent.parent.blur();
            }
        }
    }
    if (FlagStr == "Fail")
    {
        content = "����ʧ�ܣ�ԭ���ǣ�" + content;
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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
        content = "�����ɹ���";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        initbox();
        queryClick();
    }
}
function checkBusiNessTable()
{
	  if(fm.BusiNessTable.value==null||fm.BusiNessTable.value=="")
	    {
	     alert("��ҵ���");
	     return false;
	    }	
}