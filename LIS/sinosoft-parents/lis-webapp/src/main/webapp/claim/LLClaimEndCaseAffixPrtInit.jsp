<%
//�ļ�����:LLClaimEndCaseAffixPrtInit.jsp
//�ļ����ܣ����᰸ʱ���е�֤��ӡ����ʼ�� 
//������:                      �������ڣ�
//�ļ�����:
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">

//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam()
{
    document.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
    document.all('CustNo').value = nullToEmpty("<%= tCustNo %>");
    
    document.all('ClaimNo').value =document.all('ClmNo').value;
    document.all('RptNo').value =document.all('ClmNo').value;
    
    document.all('customerNo').value =document.all('CustNo').value;
    //document.all('Payee').value = document.all('Payee').value;
//    document.all('customerNo').value =document.all('CustNo').value;
//    alert(document.all('customerNo').value);
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
		//
	}
	catch(re)
	{
		alert("��LLClaimEndCaseAffixPrtInit.jsp-->initInpBox�����з����쳣:��ʼ���������!");
	}
}

function initForm()
{
  try
  {
    initParam();
    initLLInqApplyGrid();
    initLLInqFeeGrid();
  }
  catch(re)
  {
    alert("��LLClaimEndCaseAffixPrtInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

//����������ʼ��
function initLLInqApplyGrid()
  {                               
      var iArray = new Array();    
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                //�п�
      iArray[0][2]=10;                  //�����ֵ
      iArray[0][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[1]=new Array();
      iArray[1][0]="�ⰸ��";             //����
      iArray[1][1]="100px";                //�п�
      iArray[1][2]=100;                  //�����ֵ
      iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�������";             //����
      iArray[2][1]="80px";                //�п�
      iArray[2][2]=100;                  //�����ֵ
      iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";             //����
      iArray[3][1]="0px";                //�п�
      iArray[3][2]=100;                  //�����ֵ
      iArray[3][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�����˿ͻ���";             //����
      iArray[4][1]="80px";                //�п�
      iArray[4][2]=100;                  //�����ֵ
      iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="����ԭ��";             //����
      iArray[5][1]="0px";                //�п�
      iArray[5][2]=200;                  //�����ֵ
      iArray[5][3]=3; 

      iArray[6]=new Array();
      iArray[6][0]="�������";             //����
      iArray[6][1]="80px";                //�п�
      iArray[6][2]=200;                  //�����ֵ
      iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="�������";             //����
      iArray[7][1]="80px";                //�п�
      iArray[7][2]=100;                  //�����ֵ
      iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="���������";             //����
      iArray[8][1]="100px";                //�п�
      iArray[8][2]=100;                  //�����ֵ
      iArray[8][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="�����������";             //����
      iArray[9][1]="80px";                //�п�
      iArray[9][2]=100;                  //�����ֵ
      iArray[9][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="�����������";             //����
      iArray[10][1]="80px";                //�п�
      iArray[10][2]=100;                  //�����ֵ
      iArray[10][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������ 

      LLInqApplyGrid = new MulLineEnter( "fm" , "LLInqApplyGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LLInqApplyGrid.mulLineCount = 0;   
      LLInqApplyGrid.displayTitle = 1;
      LLInqApplyGrid.locked = 1;
      LLInqApplyGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
      LLInqApplyGrid.hiddenPlus=1;
      LLInqApplyGrid.hiddenSubtraction=1;
      LLInqApplyGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}

//����������ʼ��
function initLLInqFeeGrid()
  {                               
      var iArray = new Array();    
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                //�п�
      iArray[0][2]=10;                  //�����ֵ
      iArray[0][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[1]=new Array();
      iArray[1][0]="�ⰸ��";             //����
      iArray[1][1]="200px";                //�п�
      iArray[1][2]=100;                  //�����ֵ
      iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�鿱��";             //����
      iArray[2][1]="200px";                //�п�
      iArray[2][2]=100;                  //�����ֵ
      iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�����ܶ�";             //����
      iArray[3][1]="200px";                //�п�
      iArray[3][2]=100;                  //�����ֵ
      iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="";             //����
      iArray[4][1]="80px";                //�п�
      iArray[4][2]=100;                  //�����ֵ
      iArray[4][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="ȡ֤�����";             //����
      iArray[5][1]="80px";                //�п�
      iArray[5][2]=200;                  //�����ֵ
      iArray[5][3]=3; 

      iArray[6]=new Array();
      iArray[6][0]="�����";             //����
      iArray[6][1]="80px";                //�п�
      iArray[6][2]=200;                  //�����ֵ
      iArray[6][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="�����˿ͻ���";             //����
      iArray[7][1]="80px";                //�п�
      iArray[7][2]=100;                  //�����ֵ
      iArray[7][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������


      LLInqFeeGrid = new MulLineEnter( "fm" , "LLInqFeeGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LLInqFeeGrid.selBoxEventFuncName = "LAAQSuggestShiftClick"; //���RadioBoxʱ��Ӧ�ĺ�����
      LLInqFeeGrid.mulLineCount = 0;   
      LLInqFeeGrid.displayTitle = 1;
      LLInqFeeGrid.locked = 1;
      LLInqFeeGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
      LLInqFeeGrid.hiddenPlus=1;
      LLInqFeeGrid.hiddenSubtraction=1;
      LLInqFeeGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
