<%
//�ļ�����: LLUserDefinedBillPrtInit.jsp
//�ļ����ܣ��û��Զ����ӡ
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">

//����ҳ�洫�ݹ����Ĳ���
function initParam()
{
	document.all('MngCom').value = nullToEmpty("<%= tG.ManageCom %>");	 
	document.all('ComCode').value = nullToEmpty("<%= tG.ComCode %>");	  
//	document.all('Operator').value = nullToEmpty("<%= tG.Operator %>");	 
    document.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
    document.all('CustNo').value	= nullToEmpty("<%= tCustNo %>");
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
		initAffixGrid();
    }
    catch(re)
    {
        alert("LLUserDefinedBillPrtInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}

//�ļ����ݱ��
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
        iArray[1][0]="��֤����";
        iArray[1][1]="50px";
        iArray[1][2]=10;
        iArray[1][3]=0;
     
        iArray[2]=new Array();
        iArray[2][0]="��֤����";
        iArray[2][1]="200px";
        iArray[2][2]=10;
        iArray[2][3]=0;        
        
        iArray[3]=new Array();
        iArray[3][0]="�Ƿ��ύ";
        iArray[3][1]="40px";
        iArray[3][2]=10;
        iArray[3][3]=2;   
        iArray[3][10]="SubFlag";
        iArray[3][11]="0|^0|��^1|��";      
        iArray[3][14]="0";
                
        iArray[4]=new Array();
        iArray[4][0]="ҳ��";
        iArray[4][1]="40px";
        iArray[4][2]=10;
        iArray[4][3]=1;  
        iArray[4][14]="1";  
                        
        iArray[5]=new Array();
        iArray[5][0]="�ύ��ʽ";
        iArray[5][1]="40px";
        iArray[5][2]=10;
        iArray[5][3]=2;   
        iArray[5][10]="Property";
        iArray[5][11]="0|^0|ԭ��^1|��ӡ��";      
        iArray[5][14]="0";
        
        iArray[6]=new Array();
        iArray[6][0]="�Ƿ��˻�ԭ��";
        iArray[6][1]="60px";
        iArray[6][2]=10;
        iArray[6][3]=2;   
        iArray[6][10]="ReturnFlag";
        iArray[6][11]="0|^0|��^1|��";      
        iArray[6][14]="0";
        
        AffixGrid = new MulLineEnter("fm","AffixGrid");
        AffixGrid.mulLineCount = 0;
        AffixGrid.displayTitle = 1;
        AffixGrid.locked = 0;
        AffixGrid.canChk =0;              //��ѡ��ť��1��ʾ��0����
        AffixGrid.canSel =0;              //��ѡ��ť��1��ʾ��0����
        AffixGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        AffixGrid.hiddenSubtraction=0;    //���ţ�1���أ�0��ʾ
        AffixGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alter(ex);
    }
}

</script>
