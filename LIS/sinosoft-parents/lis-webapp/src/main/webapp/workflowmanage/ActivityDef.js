/**
 * Created by IntelliJ IDEA.
 * User: jinsh
 * Date: 2009-1-7
 * Time: 15:32:15
 * To change this template use File | Settings | File Templates.
 */

/*������ҳ��*/
var turnPage = new turnPageClass();
/*ϵͳ��ǰ������Ա*/
var mOperate = "";
/*����mysql��ѯ��*/
var mySql = new SqlClass();
/*������Ϣ����*/
var showInfo;
function goBack()
{
    if (document.all("ReturnFlag").value == "N")
    {
        window.returnValue = "true|1|1";
    }
    else
    {
        opener.fileQuery();
    }
    top.close();
}

function showData()
{
    var selno = TICollectGrid.getSelNo() - 1;
    if (selno < 0)
    {
        alert("û��ѡ������!");
        return false;
    }
    var tempActivityID = TICollectGrid.getRowColData(selno,1);
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ActivityDefSQL2");
    mySql.addSubPara(tempActivityID);  
 
    var arr = new Array();
    arr = easyExecSql(mySql.getString());
    try
    {
        fm.ActivityID.value           = arr[0][0];
        fm.hiddenActivityID.value     = arr[0][0];
        fm.ActivityName.value         = arr[0][1];
        fm.BusiType.value             = arr[0][2];
        fm.BusiTypeName.value         = arr[0][3];
        fm.ActivityFlag.value         = arr[0][4];
        fm.ActivityFlagName.value     = arr[0][5];
        fm.IsNeed.value               = arr[0][6];
        fm.IsNeedName.value           = arr[0][7];
        
        fm.BeforeInitType.value       = arr[0][8]; 
        fm.BeforeInitTypeName.value   = arr[0][9];
        fm.BeforeInit.value           = arr[0][10];
        fm.AfterInitType.value        = arr[0][11];
        fm.AfterInitTypeName.value    = arr[0][12];
        fm.AfterInit.value            = arr[0][13];
        fm.BeforeEndType.value        = arr[0][14];
        fm.BeforeEndTypeName.value    = arr[0][15];
        fm.BeforeEnd.value            = arr[0][16];
        fm.AfterEndType.value         = arr[0][17];
        fm.AfterEndTypeName.value     = arr[0][18];
        fm.AfterEnd.value             = arr[0][19];
        
        fm.ImpDegree.value            = arr[0][20];
        fm.ImpDegreeName.value        = arr[0][21];    
        fm.FunctionID.value            = arr[0][22];
        fm.FunctionIDName.value        = arr[0][23];
        //�����˴��������䡢ɾ��   jiyongtan begin
        fm.CreateActionType.value      = arr[0][24];
        fm.CreateActionTypeName.value  = arr[0][25];    
        fm.CreateAction.value          = arr[0][26];
        fm.ApplyActionType.value       = arr[0][27];
        fm.ApplyActionTypeName.value   = arr[0][28];    
        fm.ApplyAction.value           = arr[0][29];
        fm.DeleteActionType.value      = arr[0][30];
        fm.DeleteActionTypeName.value  = arr[0][31];    
        fm.DeleteAction.value          = arr[0][32];
        fm.Together.value              = arr[0][33];
        fm.TogetherName.value          = arr[0][34];    

        //end
    }
    catch(ex)
    {
    }
    return true;
}
/*�ύ����*/
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
        queryClick();
    }


}

function queryClick()
{
//    if (document.all('ActivityIDQ').value == "")
//    {
//        alert("����������!");
//        return false;
//    }
//    if (document.all('BusiTypeQ').value == "")
//    {
//        alert("������ҵ������!");
//        return false;
//    }
	try
    {
        fm.ActivityID.value           = "";
        fm.ActivityName.value         = "";
        fm.BusiType.value             = "";
        fm.BusiTypeName.value         = "";
        fm.ActivityFlag.value         = "";
        fm.ActivityFlagName.value     = "";
        fm.IsNeed.value               = "";
        fm.IsNeedName.value           = "";
        
        fm.BeforeInitType.value       = ""; 
        fm.BeforeInitTypeName.value   = "";
        fm.BeforeInit.value           = "";
        fm.AfterInitType.value        = "";
        fm.AfterInitTypeName.value    = "";
        fm.AfterInit.value            = "";
        fm.BeforeEndType.value        = "";
        fm.BeforeEndTypeName.value    = "";
        fm.BeforeEnd.value            = "";
        fm.AfterEndType.value         = "";
        fm.AfterEndTypeName.value     = "";
        fm.AfterEnd.value             = "";
        
        fm.ImpDegree.value            = "";
        fm.ImpDegreeName.value        = "";        
        fm.FunctionID.value           = "";
        fm.FunctionIDName.value       = ""; 
        
        fm.CreateActionType.value      = "";
        fm.CreateActionTypeName.value  = ""; 
        fm.CreateAction.value          = "";
        fm.ApplyActionType.value       = ""; 
        fm.ApplyActionTypeName.value   = "";
        fm.ApplyAction.value           = ""; 
        fm.DeleteActionType.value      = ""; 
        fm.DeleteActionTypeName.value  = "";
        fm.DeleteAction.value          = ""; 
        fm.Together.value              = ""; 
        fm.TogetherName.value          = ""; 
    }
    catch(ex)
    {
    }
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ActivityDefSql1");   
    mySql.addSubPara(document.all('ActivityIDQ').value);    
    mySql.addSubPara(document.all('BusiTypeQ').value);
 
    turnPage.queryModal(mySql.getString(), TICollectGrid);
}
function SaveClick()
{
    if (verifyInput2() == false) return false;
    
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ActivityDefSQL3");
    mySql.addSubPara(document.all('ActivityID').value);      
    var arr = easyExecSql(mySql.getString());
    if (arr>0)
    {
        alert("���ݿ��Ѿ����ڸü�¼,��ѡ���޸Ļ�ɾ������!");
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
    fm.action = "ActivityDefSave.jsp?OperFlag=INSERT||Activity";
    document.getElementById("fm").submit();
}
function ModifyClick()
{
    if(document.all('hiddenActivityID').value!=document.all('ActivityID').value)
    {
        alert("�������޸Ļ����!");
        return false;
    }
    if (verifyInput2() == false) return false;
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ActivityDefSQL3");
    mySql.addSubPara(document.all('ActivityID').value);  
    var arr = easyExecSql(mySql.getString());
    if (arr == 0)
    {
        alert("���ݿⲻ���ڸü�¼,��ѡ�񱣴����!");
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
    fm.action = "ActivityDefSave.jsp?OperFlag=MODIFY||Activity";
    document.getElementById("fm").submit();
}
function DeleteClick()
{
    if(document.all('hiddenActivityID').value!=document.all('ActivityID').value)
    {
        alert("�������޸Ļ����!");
        return false;
    }
    if (verifyInput2() == false) return false;
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ActivityDefSQL3");
    mySql.addSubPara(document.all('ActivityID').value);  
    var arr = easyExecSql(mySql.getString());
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
    fm.action = "ActivityDefSave.jsp?OperFlag=DELETE||Activity";
    document.getElementById("fm").submit();
}
function DefParamClick()
{
    if(document.all('ActivityID').value=="")
    {
        alert("���ȱ������ݻ�ѡ��һ����������");
        return false;
    }
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ActivityDefSQL3");
    mySql.addSubPara(document.all('ActivityID').value);  
    var arr = easyExecSql(mySql.getString());
    if (arr == 0)
    {
        alert("���ݿⲻ���ڸü�¼,���ȱ����ڵ���Ϣ!");
        return false;
    }
    var dialogURL="CommonMain.jsp?PageNo=ParamDef.jsp&Comment=��������&ActivityID="+document.all('ActivityID').value;
   // showModalDialog(dialogURL,window,"status:no;help:0;close:0;dialogWidth:1000px;dialogHeight:700px");
   window.open (dialogURL,"newwindow","height=700;width=1000;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;  resizable=no;alwaysRaised:yes;");

}
function DefConditionClick()
{
    if(document.all('ActivityID').value=="")
    {
        alert("���ȱ������ݻ�ѡ��һ����������");
        return false;
    }
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ActivityDefSQL3");
    mySql.addSubPara(document.all('ActivityID').value);  
    var arr = easyExecSql(mySql.getString());
    if (arr == 0)
    {
        alert("���ݿⲻ���ڸü�¼,���ȱ����ڵ���Ϣ!");
        return false;
    }
    window.open("CommonMain.jsp?PageNo=ConditionDef.jsp&Comment=ת����������");
}

function checkBusiType()
{
	if (document.all('BusiTypeQ').value==""||document.all('BusiTypeQ').value==null)
	{
		alert("����ѡ��һ��ҵ������")
		return false;
	}
}

function afterCodeSelect( cCodeName, Field )
{
    if (cCodeName == "functionid")
    {
        mySql = new SqlClass();
        mySql.setResourceName("workflow.ActivityDefSql");
        mySql.setSqlId("ActivityDefSQL4");
        mySql.addSubPara(Field.value);      	
        
        var  arr = easyExecSql(mySql.getString());
        fm.BusiType.value           = arr[0][0];
        fm.BusiTypeName.value       = arr[0][1];
    }  
}
