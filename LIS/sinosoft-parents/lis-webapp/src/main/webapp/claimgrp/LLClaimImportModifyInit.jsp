<%
//Name:LLClaimImportModifyInit.jsp
//function��
//author: quyang
//Date: 2005-06-22
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
%>
<script language="JavaScript">
    
var mCurrentDate = "";
var mNowYear = "";
var mNowMonth = "";
var mNowDay = "";

//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam()
{
    mCurrentDate = nullToEmpty("<%= CurrentDate %>");  
    mNowYear = mCurrentDate.substring(0,4);
    
    document.all('ClmNo').value = nullToEmpty("<%= tRptNo %>");
    document.all('customerNo').value = nullToEmpty("<%= tCustomerNo %>");
    
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

function initForm()
{
  //try
  //{
    initParam();
    initInpBox();
    initQuery();
    initHisEditReasonGrid();
  //}
  //catch(re)
  //{
    //alert("��LLClaimImportModifyInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  //}
}

/**
	ҳ���ʼ��
*/
function initInpBox()
{
  try
  {
  
  
  }
  catch(ex)
  {
    alert("��LLClaimImportModifyInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

/**=========================================================================
    סԺ¼����Ϣ
   =========================================================================
*/
function initHisEditReasonGrid()
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
        iArray[1][0]="����Ա";
        iArray[1][1]="80px";
        iArray[1][2]=80;
        iArray[1][3]=0;

        
        iArray[2]=new Array();
        iArray[2][0]="�޸�ʱ��";
        iArray[2][1]="80px";
        iArray[2][2]=80;
        iArray[2][3]=0;        
        
        iArray[3]=new Array();
        iArray[3][0]="�޸�ԭ��";
        iArray[3][1]="300px";
        iArray[3][2]=300;
        iArray[3][3]=0;
        
        
                                   
        HisEditReasonGrid = new MulLineEnter("document","HisEditReasonGrid");
        HisEditReasonGrid.mulLineCount = 5;
        HisEditReasonGrid.displayTitle = 1;
        HisEditReasonGrid.locked = 0;
//      HisEditReasonGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
        HisEditReasonGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        HisEditReasonGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        HisEditReasonGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
//        HisEditReasonGrid.selBoxEventFuncName = "getMedFeeInHosInpGrid"; //��������
//        HisEditReasonGrid.selBoxEventFuncParm ="divLLInqCourse";          //����        
        HisEditReasonGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}


</script>
