<%
//�������ƣ�LLParaPupilAmntInit.jsp
//�����ܣ�δ�����˱����׼��Ϣά��
//�������ڣ�2005-9-15
//������  ��zhangyang
//���¼�¼��  ������ zhangyang    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">
//���ղ���
function initParam()
{
   document.all('tOperator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
   document.all('tComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
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
        initLLParaPupilAmntGrid();                  
    }
    catch(re)
    {
        alter("LLParaPupilAmntInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}


function initInpBox()
{
  try
  {
  	fm.ComCodeQ.value ="";
    fm.ComCodeNameQ.value ="";
<!--    fm.UpComCode.value ="";-->
    fm.BaseValue.value ="";
    fm.StartDate.value = "";
    fm.EndDate.value ="";  
  }
  catch(ex)
  {
    alter("LLParaPupilAmntInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}


function initLLParaPupilAmntGrid()
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
        iArray[1][0]="�����������";
        iArray[1][1]="60px";
        iArray[1][2]=10;
        iArray[1][3]=0;
        
        iArray[2]=new Array();
        iArray[2][0]="�����������";
        iArray[2][1]="120px";
        iArray[2][2]=10;
        iArray[2][3]=0;
        
        iArray[3]=new Array();
        iArray[3][0]="�ϼ�����";
        iArray[3][1]="60px";
        iArray[3][2]=10;
        iArray[3][3]=3;        
        
        iArray[4]=new Array();
        iArray[4][0]="��׼����";
        iArray[4][1]="60px";
        iArray[4][2]=10;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="��������";
        iArray[5][1]="60px";
        iArray[5][2]=10;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="����ʱ��";
        iArray[6][1]="60px";
        iArray[6][2]=10;
        iArray[6][3]=0;
                        

          
        

        LLParaPupilAmntGrid = new MulLineEnter("document","LLParaPupilAmntGrid");
        LLParaPupilAmntGrid.mulLineCount = 5;
        LLParaPupilAmntGrid.displayTitle = 1;
        LLParaPupilAmntGrid.locked = 0;
        LLParaPupilAmntGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        LLParaPupilAmntGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        LLParaPupilAmntGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        LLParaPupilAmntGrid.selBoxEventFuncName = "LLParaPupilAmntGridClick"; //��������
        LLParaPupilAmntGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alter(ex);
    }
}

</script>

