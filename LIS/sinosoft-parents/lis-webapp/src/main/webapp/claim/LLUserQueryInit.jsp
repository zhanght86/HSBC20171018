<%
//Name��LLUserQueryInit.jsp
//Function���û���ѯҳ���ʼ��
//Date��2005.07.11
//Author��quyang
%>
<!--�û�У����-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">
//����ǰ��ҳ�洫�ݹ����Ĳ���
function initParam()
{
   
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
        initLLQueryUserGrid();
        initQuery();
    }
    catch(re)
    {
        alert("LLClaimUserQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}
//����������Ϣ
function initLLQueryUserGrid()
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
        iArray[1][0]="�û�����";      //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[1][1]="50px";                //�п�
        iArray[1][2]=100;                  //�����ֵ
        iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[2]=new Array();
        iArray[2][0]="�û�����";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[2][1]="50px";                //�п�
        iArray[2][2]=100;                  //�����ֵ
        iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
 
        iArray[3]=new Array();
        iArray[3][0]="��������";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[3][1]="50px";                //�п�
        iArray[3][2]=100;                  //�����ֵ
        iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[4]=new Array();
        iArray[4][0]="�û�����";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[4][1]="100px";                //�п�
        iArray[4][2]=100;                  //�����ֵ
        iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[5]=new Array();
        iArray[5][0]="�û�״̬";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[5][1]="60px";                //�п�
        iArray[5][2]=100;                  //�����ֵ
        iArray[5][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

       

        LLQueryUserGrid = new MulLineEnter( "document" , "LLQueryUserGrid" );
        //��Щ���Ա�����loadMulLineǰ
        LLQueryUserGrid.mulLineCount = 5;
        LLQueryUserGrid.displayTitle = 1;
        LLQueryUserGrid.canChk = 0;
        LLQueryUserGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
        LLQueryUserGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        LLQueryUserGrid.selBoxEventFuncName = "LLQueryUserGridClick"; //���RadioBoxʱ��Ӧ�ĺ�����
        LLQueryUserGrid.hiddenSubtraction=1; 
        LLQueryUserGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
          alert(ex);
      }
  }
</script>
