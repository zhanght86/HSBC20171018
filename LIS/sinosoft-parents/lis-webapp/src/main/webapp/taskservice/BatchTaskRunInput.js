
var showInfo;                          //ȫ�ֱ���, ������ʾ����, ������
var turnPage = new turnPageClass();    //ȫ�ֱ���, ��ѯ�����ҳ, ������
var isThisSaved = false;               //ȫ�ֱ���, ����Ƿ��ѳɹ������

/**
 * ִ��������-����
 */
function exeTask()
{
    var tExeDate = document.getElementsByName("tExeDate")[0].value;
 	if (tExeDate == null || trim(tExeDate) == "") {
 		alert("��������Ҫִ�е�����������ڣ�"); 
 		return;
 	} 
 	
 	var tTaskClass = document.getElementsByName("tTaskCode")[0].value;
 	if (tTaskClass == null || trim(tTaskClass) == "") {
 		alert("��������Ҫִ�е�������Ķ�Ӧ������룡");
 		return;
 	}  
    
 	//add by fengxueqian 2014-03-10 ����DivLockScreen���ֲ�Ĵ��� ����-start
	lockScreen('DivLockScreen');
	//add by fengxueqian 2014-03-10 ����DivLockScreen���ֲ�Ĵ��� ����-end
 	
    var MsgContent = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(MsgContent));
    showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
    document.forms(0).action = "BatchTaskRunSave.jsp";
    document.forms(0).submit();   
}

/**
 * �ύ�����, ���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(DealFlag, MsgContent)
{ 
    try { showInfo.close(); } catch (ex) {}
    
    //add by fengxueqian 2014-03-10 ����DivLockScreen���ֲ�Ĵ��� ����-start
	unlockScreen('DivLockScreen');
	//add by fengxueqian 2014-03-10 ����DivLockScreen���ֲ�Ĵ��� ����-end
    
    DealFlag = DealFlag.toLowerCase();
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=";    switch (DealFlag)
    {
        case "fail":
            MsgPageURL = MsgPageURL + "F&content=" + encodeURI(encodeURI(MsgContent));
            showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=250px");
            break;
        case "succ":
        case "success":
            MsgPageURL = MsgPageURL + "S&content=" + encodeURI(encodeURI(MsgContent));
            showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
            break;
        default:
            MsgPageURL = MsgPageURL + "C&content=" + encodeURI(encodeURI(MsgContent));
            showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
            break;
    }
    //���ļ������⴦��
    if (DealFlag == "succ" || DealFlag == "success")
    {
        isThisSaved = true;
    }
}


//<!-- JavaScript Document END -->
/**
	mysql���������ݴ����������Sql�ַ���
	
	sqlId:ҳ����ĳ��sql��Ψһ��ʶ
	param:��������,sql��where��������Ĳ���
**/
function wrapSql(sqlId,param){
	
	var mysql=new SqlClass();
	
	mysql.setResourceName("operfee.TaskTestInputSql");
	mysql.setSqlId(sqlId);
	
	for(i=0;i<param.length;i++){
		 mysql.addSubPara(param[i]);
	}
	
	return mysql.getString();
	
}
