<%
//�������ƣ�LLCommendHospitalInit.jsp
//�����ܣ�ҽԺ��Ϣά��
//�������ڣ�2005-7-13
//������  ��yuejw
//���¼�¼��  ������ yuejw    ��������     ����ԭ��/����
%>
<html>

</html>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">
//���ղ���
function initParam()
{
   fm.all('tOperator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
   fm.all('tComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
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
        initInpBox();        
        initParam();       
        initLLCommendHospitalGrid();                  
    }
    catch(re)
    {
        alter("LLCommendHospitalInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}


function initInpBox()
{
  try
  {
    fm.HospitalCodeQ.value ="";
    fm.HospitalNameQ.value ="";
    fm.HospitalCode.value ="";
    fm.HospitalName.value ="";
    fm.HosAtti.value = "";
    fm.ConFlag.value ="";
    fm.AppFlag.value ="";
    fm.HosState.value = "";  
  }
  catch(ex)
  {
    alter("LLCommendHospitalInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}


function initLLCommendHospitalGrid()
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
        iArray[1][0]="��ѯ֪ͨ����";
        iArray[1][1]="0px";
        iArray[1][2]=10;
        iArray[1][3]=3;

        iArray[2]=new Array();
        iArray[2][0]="ҽԺ����";
        iArray[2][1]="60px";
        iArray[2][2]=10;
        iArray[2][3]=0;
        
        iArray[3]=new Array();
        iArray[3][0]="ҽԺ����";
        iArray[3][1]="120px";
        iArray[3][2]=10;
        iArray[3][3]=0;        
        
        iArray[4]=new Array();
        iArray[4][0]="ҽԺ�ȼ�";
        iArray[4][1]="60px";
        iArray[4][2]=10;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="�����־";
        iArray[5][1]="60px";
        iArray[5][2]=10;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="�м��������ʱ�־";
        iArray[6][1]="60px";
        iArray[6][2]=10;
        iArray[6][3]=0;
                

        iArray[7]=new Array();
        iArray[7][0]="ҽԺ״̬";
        iArray[7][1]="60px";
        iArray[7][2]=10;
        iArray[7][3]=0;                

        iArray[8]=new Array();
        iArray[8][0]="�������";
        iArray[8][1]="60px";
        iArray[8][2]=10;
        iArray[8][3]=0;  
        

        LLCommendHospitalGrid = new MulLineEnter("fm","LLCommendHospitalGrid");
        LLCommendHospitalGrid.mulLineCount = 0;
        LLCommendHospitalGrid.displayTitle = 1;
        LLCommendHospitalGrid.locked = 0;
        LLCommendHospitalGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        LLCommendHospitalGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        LLCommendHospitalGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        LLCommendHospitalGrid.selBoxEventFuncName = "LLCommendHospitalGridClick"; //��������
        LLCommendHospitalGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alter(ex);
    }
}

</script>

