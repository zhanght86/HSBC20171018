<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
//�������ƣ�GrpFeeInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%
	//���ҳ��ؼ��ĳ�ʼ����
%>
<script language="JavaScript">

function initInpBox()
{ 
  try
  {                                   
    document.all('ProposalGrpContNo').value = GrpContNo;
    document.all('GrpContNo').value = GrpContNo;
  }
  catch(ex)
  {
    alert("��GroupPolInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}


function initSelBox()
{
  try
  {
   
  }
  catch(ex)
  {
    alert("��LLClaimConfListInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}
function initForm()
{
  try
  {
    
    initInpBox();
    initSelBox();
    initCaseGrid();
    //selectLMChargeRate();

  }
  catch(re)
  {
    alter("��LLClaimConfListInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
function initCaseGrid()
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
    iArray[1][0]="�����ͬ��";
    iArray[1][1]="70px";
    iArray[1][2]=100;
    iArray[1][3]=0;

    iArray[2]=new Array();
    iArray[2][0]="���ֱ���";
    iArray[2][1]="70px";
    iArray[2][2]=100;
    iArray[2][3]=0;

    
      iArray[3]=new Array();
      iArray[3][0]="�����ѱ���";         		//����
      iArray[3][1]="70px";            		//�п�
      iArray[3][2]= 100;            			//�����ֵ
      iArray[3][3]= 0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
    

    CaseGrid = new MulLineEnter("fm","CaseGrid");
    CaseGrid.mulLineCount =0;
    CaseGrid.displayTitle = 1;        //����
    CaseGrid.locked = 1;        //��д
    CaseGrid.canSel =1;        //��ѡ��ѡ
    CaseGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    CaseGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    CaseGrid.selBoxEventFuncName = "flagLMChargeRate";
    //CaseGrid.chkBoxEventFuncName = "flagLMChargeRate";
    CaseGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alter(ex);
  }
}

</script>
