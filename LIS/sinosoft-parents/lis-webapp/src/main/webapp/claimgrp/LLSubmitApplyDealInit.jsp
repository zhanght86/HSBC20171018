<%
//�������ƣ�LLSubmitApplyDealInit.jsp
//�����ܣ��ʱ���Ϣ����ҳ��ؼ��ĳ�ʼ��
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">

//���ձ���ҳ�洫�ݹ����Ĳ���(//�˴��д���д)


function initForm()
{
	try
    {   
        initInpBox();
        initDivLLsubmit();
        initParam();
        easyQueryClick();
        if(fm.SubCount.value =="1") //�ʱ�����)
        {
       	 	DivHeadIdea.style.display="";    
        }
     }
    catch(re)
    {
    	alert("LLSubmitApplyDealInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}
function initParam()
{	
	fm.all('ManageCom').value = nullToEmpty("<%= tG.ManageCom %>");	      
    fm.all('ClmNo').value =nullToEmpty("<%=tClmNo%>");
    fm.all('SubNO').value =nullToEmpty("<%=tSubNo%>");
    fm.all('SubCount').value =nullToEmpty("<%=tSubCount%>");
    fm.all('MissionID').value =nullToEmpty("<%=tMissionID%>");
    fm.all('SubMissionID').value =nullToEmpty("<%=tSubMissionID%>");
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
function initDivLLsubmit()  //��ʼ��DivLLSubmit���
{
	try
	{
    	fm.ClmNo.value=""; //�ⰸ��
        fm.SubNO.value="";  //�ʱ����
        fm.SubCount.value=""; //�ʱ�����
        fm.CustomerNo.value=""; //�����˿ͻ���
        fm.CustomerName.value=""; //�����˿ͻ�����
        fm.VIPFlag.value="";   //VIP�ͻ�
        fm.InitPhase.value=""; //����׶�
        fm.SubType.value="";    //�ʱ�����
        fm.SubRCode.value="";  //�ʱ�ԭ��
        fm.SubDesc.value="";  //�ʱ�����
        fm.SubPer.value="";   //�ʱ���
        fm.SubDate.value="";  //�ʱ�����
        fm.SubDept.value="";  //�ʱ�����
        fm.SubState.value=""; //�ʱ�״̬
        fm.DispDept.value=""; //�нӻ�������
        fm.DispPer.value="";  //�н���Ա���       
        
     }
     catch(ex)
     {
    	alert("��LLSubmitApplyDealInit.jsp-->initDivLLsubmit()()�����з����쳣:��ʼ���������!");
     }
}

function initInpBox()  //
{
	try
	{
 		DivDispType.style.display="none";     
 		DivReportheadSubDesc.style.display="none";   
 		DivHeadIdea.style.display="none";        	     	
    }
     catch(ex)
     {
    	alert("��LLSubmitApplyDealInit.jsp-->initInpBox()�����з����쳣:��ʼ���������!");
     }
}

</script>
