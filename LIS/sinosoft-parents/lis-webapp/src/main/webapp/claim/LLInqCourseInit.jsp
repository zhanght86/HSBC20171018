<%
//Name:LLInqCourseInit.jsp
//function��

//Date:2005-6-22
//���¼�¼��  ������:yuejw    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
%>
<script language="JavaScript">
function initParam()
{	
//	var sysdatearr=easyExecSql("select to_date(now(),'yyyy-mm-dd') from dual"); //ȡ��������ʱ����Ϊϵͳ��ǰ����
	var sysdatearr=easyExecSql("select to_char(now(),'yyyy-mm-dd') from dual"); //ȡ��������ʱ����Ϊϵͳ��ǰ����    //wyc
	mCurrentDate=sysdatearr[0][0];
	
   	document.all('ManageCom').value = nullToEmpty("<%= tG.ManageCom %>");	  
    document.all('ClmNo').value =nullToEmpty("<%=tClmNo%>");
    document.all('InqNo').value =nullToEmpty("<%=tInqNo%>");
    document.all('Activityid').value =nullToEmpty("<%=tActivityid%>");
    document.all('MissionID').value =nullToEmpty("<%=tMissionID%>");
    document.all('SubMissionID').value =nullToEmpty("<%=tSubMissionID%>");
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
//����������Ϣ��ʼ��	
function initLLInqApplyInfo()
{
  try
  {
    	fm.ClmNo.value=""; //�ⰸ��
        fm.InqNo.value="";  //�������
        fm.BatNo.value=""; //���κ�
        fm.CustomerNo.value=""; //�����˿ͻ���
        fm.CustomerName.value=""; //�����˿ͻ�����
        fm.VIPFlag.value="";   //VIP�ͻ�
        fm.InqDept.value="";   //�������
        fm.InitPhase.value=""; //����׶�
        fm.InqRCode.value="";  //����ԭ��
        fm.InqItem.value="";  //������Ŀ
  }
  catch(ex)
  {
    alter("��LLInqCourseInit.jsp-->initLLInqApplyInfo�����з����쳣:��ʼ���������!");
  }
}
//���������Ϣ��ʼ��
function initInqCourseInfo()
{
  try
  {
    	fm.InqMode.value=""; //���鷽ʽ
    	fm.InqModeName.value=""; //���鷽ʽ
        fm.InqSite.value="";  //����ص�
        fm.InqDate.value=""; //��������
        fm.InqPer1.value=""; //��һ������
        fm.InqPer2.value=""; //�ڶ�������
        fm.InqByPer.value="";   //��������
        fm.InqCourse.value="";   //�������¼��  	
        fm.InqCourseRemark.value="";   //������̱�ע
  }
  catch(ex)
  {
    alert("��LLInqCourseInit.jsp-->InitInqCourseInfo�����з����쳣:��ʼ���������!");
  }
}
//���������Ϣ��ʼ��
function initInqFeeInfo()
{
  try
  {
    fm.FeeType.value=""; //��������
    fm.FeeTypeName.value=""; //��������
    fm.FeeSum.value=""; //���ý��
    fm.FeeDate.value=""; //����ʱ��
    fm.Payee.value=""; //�����
    fm.PayeeType.value=""; //  ��ʽ
    fm.PayeeTypeName.value=""; //  ��ʽ
    fm.Remark.value="";    //��ע
  }
  catch(ex)
  {
    alert("��LLInqCourseInit.jsp-->initInqFeeInfo�����з����쳣:��ʼ���������!");
  }
}
//��ʼ��ҳ��
function initForm()
{
  try
  {
    initLLInqApplyInfo();
    initInqCourseInfo();
    initInqFeeInfo();
    initLLInqCertificateInfo();
	initParam();
	LLInqApplyQueryClick();
  }
  catch(re)
  {
    alter("��LLInqCourseInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


//������̵�֤��Ϣ
function initLLInqCertificateGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";
        iArray[0][1]="30px";
        iArray[0][2]=10;
        iArray[0][3]=0;
   
        iArray[1]=new Array();
        iArray[1][0]="��֤����";
        iArray[1][1]="60px";
        iArray[1][2]=100;
        iArray[1][3]=0;
//     	iArray[1][4]="llmaffix";
////     	iArray[1][7]="llmaffixClick" //��д��JS����������������
//     	iArray[1][18]= "500"; 

		iArray[2]=new Array();
        iArray[2][0]="��֤����";
        iArray[2][1]="200px";
        iArray[2][2]=100;
        iArray[2][3]=0;
            
        iArray[3]=new Array()
        iArray[3][0]="ԭ����־";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=2;
		iArray[3][10]="OriFlag";
        iArray[3][11]="0|^0|ԭ��^1|��ӡ��";  
        iArray[3][14]="0";
        
        iArray[4]=new Array();
        iArray[4][0]="����";
        iArray[4][1]="50px";
        iArray[4][2]=100;
        iArray[4][3]=1;
		iArray[4][14]=1;

		iArray[5]=new Array();
        iArray[5][0]="��ע��Ϣ";
        iArray[5][1]="200px";
        iArray[5][2]=100;
        iArray[5][3]=1;
        
        LLInqCertificateGrid = new MulLineEnter("document","LLInqCertificateGrid");
        LLInqCertificateGrid.mulLineCount = 5;
        LLInqCertificateGrid.displayTitle = 1;
        LLInqCertificateGrid.locked = 0;
        LLInqCertificateGrid.canSel =0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
        LLInqCertificateGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        LLInqCertificateGrid.hiddenSubtraction=0; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        LLInqCertificateGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert(ex);
  }
}

function initLLInqCertificateInfo()
{
	initLLInqCertificateGrid();
	LLInqCertificateGrid.clearData();
	fm.AffixCode.value="";
	fm.AffixName.value="";
	fm.checkbox.checked=false;
	fm.OtherName.value="";
	fm.OtherName.disabled=true;
	DivInqCertificate.style.display="none";
}

 </script>
