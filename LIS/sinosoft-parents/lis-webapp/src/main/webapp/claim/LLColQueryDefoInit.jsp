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
        initLLColQueryDefoGrid();
    }
    catch(re)
    {
        alter("LLColQueryDefoInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}


function initInpBox()
{
  try
  {
  }
  catch(ex)
  {
    alter("LLColQueryDefoInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}


function initLLColQueryDefoGrid()
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
        iArray[1][0]="�˲д���";
        iArray[1][1]="80px";
        iArray[1][2]=10;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="�˲д�������";
        iArray[2][1]="350px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="�˲�����";
        iArray[3][1]="50px";
        iArray[3][2]=10;
        iArray[3][3]=3;

        iArray[4]=new Array();
        iArray[4][0]="�˲���������";
        iArray[4][1]="80px";
        iArray[4][2]=10;
        iArray[4][3]=3;

        iArray[5]=new Array();
        iArray[5][0]="�˲м���";
        iArray[5][1]="50px";
        iArray[5][2]=10;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="�˲м�������";
        iArray[6][1]="120px";
        iArray[6][2]=10;
        iArray[6][3]=0;


        iArray[7]=new Array();
        iArray[7][0]="�м���������";
        iArray[7][1]="80px";
        iArray[7][2]=10;
        iArray[7][3]=0;

//        iArray[8]=new Array();
//        iArray[8][0]="�������";
//        iArray[8][1]="60px";
//        iArray[8][2]=10;
//        iArray[8][3]=0;

        LLColQueryDefoGrid = new MulLineEnter("fm","LLColQueryDefoGrid");
        LLColQueryDefoGrid.mulLineCount = 0;
        LLColQueryDefoGrid.displayTitle = 1;
        LLColQueryDefoGrid.locked = 0;
        LLColQueryDefoGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        LLColQueryDefoGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        LLColQueryDefoGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
//        LLColQueryDefoGrid.selBoxEventFuncName = "LLColQueryDefoGridClick"; //��������
        LLColQueryDefoGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>

