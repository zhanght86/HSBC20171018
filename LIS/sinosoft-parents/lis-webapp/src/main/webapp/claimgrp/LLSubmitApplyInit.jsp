<%
//�������ƣ�LLSubmitApplyInit.jsp
//�����ܣ�����ʱ�ҳ���ʼ��
//�������ڣ�2005-05-30
//������  ��zhoulei
//���¼�¼��
%> 
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
    String tCurrentDate = PubFun.getCurrentDate();
%>                            
<script language="JavaScript">

//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam()
{
	document.all('ManageCom').value = nullToEmpty("<%= tG.ManageCom %>");	  
    document.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
    document.all('CustomerNo').value	= nullToEmpty("<%= tCustomerNo %>");
    document.all('CustomerName').value = nullToEmpty("<%= tCustomerName %>");
    //document.all('VIPFlag').value = nullToEmpty("<%= tVIPFlag %>");
    
    document.all('MissionID').value = nullToEmpty("<%= tMissionID %>");
    document.all('SubMissionID').value = nullToEmpty("<%= tSubMissionID %>");
}

//��null���ַ���תΪ��
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}

function initInpBox()
{ 
    try
    {                      
        fm.SubPer.value = nullToEmpty("<%= tG.Operator %>");
        fm.SubDate.value = nullToEmpty("<%= tCurrentDate %>");   
        fm.SubDept.value = nullToEmpty("<%= tG.ManageCom %>");
        showOneCodeName('station','SubDept','SubDeptName');
              
        fm.SubType.value = "0"; //�ʱ�����
        showOneCodeName('llSubType','SubType','SubTypeName');
        fm.SubRCode.value = ""; //�ʱ�ԭ��
    }
    catch(ex)
    {
        alert("��LLSubmitApplyInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }      
}

function initSelBox()
{  
    try                 
    {
 
    }
    catch(ex)
    {
        alert("��LLSubmitApplyInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
    }
}                                        

function initForm()
{
    try
    {
    	initParam();
        initInpBox();
        initSelBox();
        initQuery(); //��ѯ����������--�����������  
    }
    catch(re)
    {
        alert("LLSubmitApplyInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}

</script>