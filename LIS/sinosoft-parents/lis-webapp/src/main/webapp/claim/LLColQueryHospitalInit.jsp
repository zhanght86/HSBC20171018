<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">
//���ղ���
function initParam()
{
   document.all('tOperator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
   document.all('tComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
   document.all('HosCode').value = nullToEmpty("<%= tCode %>");
   document.all('HosName').value = nullToEmpty("<%= tName %>");
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
        InitHospitalMngcomQ();
        initLLColQueryHospitalGrid();
    }
    catch(re)
    {
        alter("LLColQueryHospitalInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}


function initInpBox()
{
  try
  {
  }
  catch(ex)
  {
    alter("LLColQueryHospitalInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}


function initLLColQueryHospitalGrid()
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


        LLColQueryHospitalGrid = new MulLineEnter("document","LLColQueryHospitalGrid");
        LLColQueryHospitalGrid.mulLineCount = 5;
        LLColQueryHospitalGrid.displayTitle = 1;
        LLColQueryHospitalGrid.locked = 0;
        LLColQueryHospitalGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        LLColQueryHospitalGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        LLColQueryHospitalGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
//        LLColQueryHospitalGrid.selBoxEventFuncName = "LLColQueryHospitalGridClick"; //��������
        LLColQueryHospitalGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>

