<%
//�������ƣ�LLGrpClaimUserInit.jsp
//�����ܣ������û�����
//�������ڣ�2005-7-11 
//������  ������
//���¼�¼��  ������ yuejw    ��������     ����ԭ��/����
//            zhouming        2006/04/30   ����ڸ�״̬
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
        initLLGrpClaimUserGrid();                  
    }
    catch(re)
    {
        alter("LLGrpClaimUserInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}


function initInpBox()
{
  try
  {

  }
  catch(ex)
  {
    alter("LLClaimUserInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}


function initLLGrpClaimUserGrid()
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
        iArray[1][0]="�û�����";
        iArray[1][1]="60px";
        iArray[1][2]=10;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="�û�����";
        iArray[2][1]="80px";
        iArray[2][2]=10;
        iArray[2][3]=0;
        
        iArray[3]=new Array();
        iArray[3][0]="��������";
        iArray[3][1]="60px";
        iArray[3][2]=10;
        iArray[3][3]=0;        

        iArray[4]=new Array();
        iArray[4][0]="Ȩ�޼������";
        iArray[4][1]="60px";
        iArray[4][2]=10;
        iArray[4][3]=3;        
        
        iArray[5]=new Array();
        iArray[5][0]="Ȩ�޼���";
        iArray[5][1]="120px";
        iArray[5][2]=10;
        iArray[5][3]=0;        

        iArray[6]=new Array();
        iArray[6][0]="�ϼ��û�����";
        iArray[6][1]="80px";
        iArray[6][2]=10;
        iArray[6][3]=3;        

        iArray[7]=new Array();
        iArray[7][0]="�ϼ��û�";
        iArray[7][1]="80px";
        iArray[7][2]=10;
        iArray[7][3]=0;    
        
        iArray[8]=new Array();
        iArray[8][0]="����Ȩ��";
        iArray[8][1]="60px";
        iArray[8][2]=10;
        iArray[8][3]=0;
        
        iArray[9]=new Array();
        iArray[9][0]="����Ȩ��";
        iArray[9][1]="60px";
        iArray[9][2]=10;
        iArray[9][3]=0;
        
        iArray[10]=new Array();
        iArray[10][0]="�ʱ�Ȩ��";
        iArray[10][1]="60px";
        iArray[10][2]=10;
        iArray[10][3]=0;         

        iArray[11]=new Array();
        iArray[11][0]="����Ȩ��";
        iArray[11][1]="60px";
        iArray[11][2]=10;
        iArray[11][3]=0;                

        iArray[12]=new Array();
        iArray[12][0]="Ԥ��Ȩ��";
        iArray[12][1]="60px";
        iArray[12][2]=10;
        iArray[12][3]=0;  
               
        iArray[13]=new Array();
        iArray[13][0]="���װ���Ȩ��";
        iArray[13][1]="80px";
        iArray[13][2]=10;
        iArray[13][3]=0;
               
        iArray[14]=new Array();
        iArray[14][0]="��֤ɨ��Ȩ��";
        iArray[14][1]="80px";
        iArray[14][2]=10;
        iArray[14][3]=0;  
               
        iArray[15]=new Array();
        iArray[15][0]="�������Ȩ��";
        iArray[15][1]="80px";
        iArray[15][2]=10;
        iArray[15][3]=0;
               
		iArray[16]=new Array();
        iArray[16][0]="���Ȩ��";
        iArray[16][1]="60px";
        iArray[16][2]=10;
        iArray[16][3]=0;               
               
        iArray[17]=new Array();
        iArray[17][0]="��˼���";
        iArray[17][1]="60px";
        iArray[17][2]=10;
        iArray[17][3]=3;  
               
        iArray[18]=new Array();
        iArray[18][0]="����Ȩ��";
        iArray[18][1]="60px";
        iArray[18][2]=10;
        iArray[18][3]=0;
                
        iArray[19]=new Array();
        iArray[19][0]="��������";
        iArray[19][1]="70px";
        iArray[19][2]=10;
        iArray[19][3]=3;  
               
        iArray[20]=new Array();
        iArray[20][0]="����Ȩ��";
        iArray[20][1]="70px";
        iArray[20][2]=10;
        iArray[20][3]=0;     
        
        iArray[21]=new Array();
        iArray[21][0]="�ڸ�״̬";
        iArray[21][1]="60px";
        iArray[21][2]=10;
        iArray[21][3]=0;  
               
        iArray[22]=new Array();
        iArray[22][0]="�����绰";
        iArray[22][1]="70px";
        iArray[22][2]=10;
        iArray[22][3]=0;     


//------------------------------------------------------------------------------End            
                                
        LLGrpClaimUserGrid = new MulLineEnter("document","LLGrpClaimUserGrid");
        LLGrpClaimUserGrid.mulLineCount = 5;
        LLGrpClaimUserGrid.displayTitle = 1;
        LLGrpClaimUserGrid.locked = 0;
//      LLGrpClaimUserGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
        LLGrpClaimUserGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        LLGrpClaimUserGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        LLGrpClaimUserGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        LLGrpClaimUserGrid.selBoxEventFuncName = "LLGrpClaimUserGridClick"; //��������
        LLGrpClaimUserGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alter(ex);
    }
}

</script>

