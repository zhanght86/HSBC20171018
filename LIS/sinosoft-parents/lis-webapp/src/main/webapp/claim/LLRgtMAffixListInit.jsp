<%
//Name:LLRgtMAffixListInit.jsp
//Function����֤���������ʼ������
//Date��2005-07-1 17:44:28
//Author ��yuejw
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">
var turnPage = new turnPageClass();
//������ҳ��������� 
function initParam()
{
	document.all('ClmNo').value =nullToEmpty("<%=tClmNo%>");
    document.all('CustomerNo').value =nullToEmpty("<%=tCustomerNo%>");
    document.all('Proc').value =nullToEmpty("<%=tProc%>");
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
    	initAffixGrid();
    	initRgtAffixGrid();
	    initAffixGridQuery();   
	    initRgtAffixGridQuery();	
    }
    catch(re)
    {
        alert("LLRgtMAffixListInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}


function initInpBox()
{
    try
    {
    }
    catch(re)
    {
        alert("LLRgtMAffixListInit.jsp-->initInpBox�����з����쳣:��ʼ���������!");
    }
}

//[������֤]���赥֤��Ϣ
function initAffixGrid()
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
        iArray[1][0]="��������";
        iArray[1][1]="0px";
        iArray[1][2]=10;
        iArray[1][3]=3;
        
        iArray[2]=new Array();
        iArray[2][0]="��֤����";
        iArray[2][1]="40px";
        iArray[2][2]=10;
        iArray[2][3]=0;
      
        iArray[3]=new Array();
        iArray[3][0]="��֤����";
        iArray[3][1]="100px";
        iArray[3][2]=10;
        iArray[3][3]=0;        
        
        iArray[4]=new Array();
        iArray[4][0]="�Ƿ����ύ";
        iArray[4][1]="0px";
        iArray[4][2]=10;
        iArray[4][3]=3;  

        iArray[5]=new Array();
        iArray[5][0]="�Ƿ����";
        iArray[5][1]="40px";
        iArray[5][2]=10;
        iArray[5][3]=0;    
                
        iArray[6]=new Array();
        iArray[6][0]="��֤����";
        iArray[6][1]="40px";
        iArray[6][2]=10;
        iArray[6][3]=1;  

        iArray[7]=new Array();
        iArray[7][0]="ȱ�ټ���";
        iArray[7][1]="40px";
        iArray[7][2]=10;
        iArray[7][3]=1;  
                
        iArray[8]=new Array();
        iArray[8][0]="�ύ��ʽ";
        iArray[8][1]="40px";
        iArray[8][2]=10;
        iArray[8][3]=2; 
        iArray[8][10]="Property";
        iArray[8][11]="0|^0|ԭ��^1|��ӡ��";      

        iArray[9]=new Array();
        iArray[9][0]="�Ƿ��˻�ԭ��";
        iArray[9][1]="50px";
        iArray[9][2]=10;
        iArray[9][3]=2; 
        iArray[9][10]="returnflag";
        iArray[9][11]="0|^0|��^1|��";     
        iArray[9][14]="0";     
       
        iArray[10]=new Array();
        iArray[10][0]="��֤������";
        iArray[10][1]="50px";
        iArray[10][2]=10;
        iArray[10][3]=2;         
        iArray[10][10]="affixconclusion";        
        iArray[10][11]="0|^0|��ȫ^1|����ȫ";  
        iArray[10][14]="0"; 
        
        iArray[11]=new Array();
        iArray[11][0]="����ȫԭ��";
        iArray[11][1]="100px";
        iArray[11][2]=10;
        iArray[11][3]=1;      

        iArray[12]=new Array();
        iArray[12][0]="����׶�";
        iArray[12][1]="0px";
        iArray[12][2]=10;
        iArray[12][3]=3;                           
                                  
        AffixGrid = new MulLineEnter("document","AffixGrid");
        AffixGrid.mulLineCount = 5;
        AffixGrid.displayTitle = 1;
        AffixGrid.locked = 0;
        AffixGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
        AffixGrid.canSel =0;              //��ѡ��ť��1��ʾ��0����
        AffixGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        AffixGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        //AffixGrid.chkBoxEventFuncName ="ChkBoxClick"; //��ѡ��ť��Ӧ��JS����������������
        //AffixGrid.chkBoxAllEventFuncName="ChkBoxAllClick"; //��Ա���������ȫѡ���ܵ�checkBox��JS������,��������
        AffixGrid.loadMulLine(iArray);
        
    }
    catch(ex)
    {
        alter(ex);
    }
}


//[�Ѿ�������֤]���赥֤��Ϣ
function initRgtAffixGrid()
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
        iArray[1][0]="��������";
        iArray[1][1]="0px";
        iArray[1][2]=10;
        iArray[1][3]=3;
        
        iArray[2]=new Array();
        iArray[2][0]="��֤����";
        iArray[2][1]="40px";
        iArray[2][2]=10;
        iArray[2][3]=0;
      
        iArray[3]=new Array();
        iArray[3][0]="��֤����";
        iArray[3][1]="100px";
        iArray[3][2]=10;
        iArray[3][3]=0;        
        
        iArray[4]=new Array();
        iArray[4][0]="�Ƿ����ύ";
        iArray[4][1]="0px";
        iArray[4][2]=10;
        iArray[4][3]=3;  

        iArray[5]=new Array();
        iArray[5][0]="�Ƿ����";
        iArray[5][1]="40px";
        iArray[5][2]=10;
        iArray[5][3]=0;    
                
        iArray[6]=new Array();
        iArray[6][0]="��֤����";
        iArray[6][1]="40px";
        iArray[6][2]=10;
        iArray[6][3]=0;  

        iArray[7]=new Array();
        iArray[7][0]="ȱ�ټ���";
        iArray[7][1]="40px";
        iArray[7][2]=10;
        iArray[7][3]=0;  
                
        iArray[8]=new Array();
        iArray[8][0]="�ύ��ʽ";
        iArray[8][1]="40px";
        iArray[8][2]=10;
        iArray[8][3]=0;   

        iArray[9]=new Array();
        iArray[9][0]="�Ƿ��˻�ԭ��";
        iArray[9][1]="50px";
        iArray[9][2]=10;
        iArray[9][3]=0; 
       
        iArray[10]=new Array();
        iArray[10][0]="��֤������";
        iArray[10][1]="50px";
        iArray[10][2]=10;
        iArray[10][3]=0;         
        
        iArray[11]=new Array();
        iArray[11][0]="����ȫԭ��";
        iArray[11][1]="100px";
        iArray[11][2]=10;
        iArray[11][3]=0;                             
                                  
        RgtAffixGrid = new MulLineEnter("document","RgtAffixGrid");
        RgtAffixGrid.mulLineCount = 5;
        RgtAffixGrid.displayTitle = 1;
        RgtAffixGrid.locked = 0;
        RgtAffixGrid.canChk =0;              //��ѡ��ť��1��ʾ��0����
        RgtAffixGrid.canSel =0;              //��ѡ��ť��1��ʾ��0����
        RgtAffixGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        RgtAffixGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        RgtAffixGrid.loadMulLine(iArray);
        
    }
    catch(ex)
    {
        alter(ex);
    }
}
</script>
