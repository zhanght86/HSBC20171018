<%
//ҳ������: NBUserQueryInput.jsp
//ҳ�湦�ܣ��û���ѯҳ��
//�����ˣ�chenrong
//�������ڣ�2006.02.22
//�޸�ԭ�� ���ݣ�
%>
<!--�û�У����-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">

//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam()
{
   document.all('Operator').value = nullToEmpty("<%= tGI.Operator %>");
   document.all('ComCode').value = nullToEmpty("<%= tGI.ComCode %>");
   document.all('MngCom').value = nullToEmpty("<%= tGI.ManageCom %>");
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
        initNBUserGrid();
    }
    catch(re)
    {
        alert("NBUserQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}

//�����û��б�
function initNBUserGrid()
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
        iArray[2][1]="60px";
        iArray[2][2]=10;
        iArray[2][3]=0;
        
        iArray[3]=new Array();
        iArray[3][0]="��������";
        iArray[3][1]="60px";
        iArray[3][2]=10;
        iArray[3][3]=0;        
        
        NBUserGrid = new MulLineEnter("fm","NBUserGrid");
        NBUserGrid.mulLineCount = 0;
        NBUserGrid.displayTitle = 1;
        NBUserGrid.locked = 0;
        NBUserGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        NBUserGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        NBUserGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        NBUserGrid.selBoxEventFuncName = "NBUserGridClick"; //��������
        NBUserGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alter(ex);
    }
}
</script>
