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


//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam()
{
	//var sysdatearr=easyExecSql("select to_date(sysdate) from dual"); //ȡ��������ʱ����Ϊϵͳ��ǰ����
	var tResourceName="claim.QueryDateSql";
	var sysdatearr = easyExecSql(wrapSql(tResourceName,"querysqldes1",["1"]));
	mCurrentDate=sysdatearr[0][0];
    
    document.all('ClmNo').value = nullToEmpty("<%= tRptNo %>");
    document.all('MissionID').value = nullToEmpty("<%= tMissionID %>");
    document.all('SubMissionID').value = nullToEmpty("<%= tSubMissionID %>");
    
    document.all('IsShow').value = nullToEmpty("<%= tIsShow %>");
}

//Added by niuzj,2005-11-07
function initIsShow()
{
	  //�ж�[�޸�ȷ��]��ť�ܷ���ʾ,0-��ʾ,1-����ʾ
		if(fm.IsShow.value == '0')
		{
			spanIsShow.style.display="";
		}
		if(fm.IsShow.value == '1')
		{
			spanIsShow.style.display="none";
		}
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
  try
  {
    initParam();
    initInpBox();
    initQuery();
    initIsShow()
    initHisEditReasonGrid();
  }
  catch(re)
  {
    alert("��LLClaimImportModifyInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
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
        iArray[1][0]="�����û�";
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
        iArray[3][1]="700px";
        iArray[3][2]=700;
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
