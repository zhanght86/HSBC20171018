<%
//�������ƣ�LLSubmitQueryInit.jsp
//�����ܣ��ʱ���Ϣ��ѯҳ���ʼ��
//�������ڣ� 
//������  �� 
//���¼�¼��
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            
<script language="JavaScript">

//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam()
{
    document.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
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

    }
    catch(ex)
    {
        alert("��LLSubmitQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }      
}

function initSelBox()
{  
    try                 
    {
 
    }
    catch(ex)
    {
        alert("��LLSubmitQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
    }
}                                        

function initForm()
{
    try
    {
        initParam();
        initLLSubmitApplyGrid();
        queryLLSubmitApplyGrid();
    }
    catch(re)
    {
        alert("LLSubmitQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}
// ������۱�ĳ�ʼ��
function initLLSubmitApplyGrid()
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
        iArray[1][1]="160px";                //�п�
        iArray[1][2]=100;                  //�����ֵ
        iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[2]=new Array();
        iArray[2][0]="�ʱ����";             //����
        iArray[2][1]="80px";                //�п�
        iArray[2][2]=100;                  //�����ֵ
        iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[3]=new Array();
        iArray[3][0]="�ʱ�����";             //����
        iArray[3][1]="60px";                //�п�
        iArray[3][2]=100;                  //�����ֵ
        iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[4]=new Array();
        iArray[4][0]="�����˿ͻ���";             //����
        iArray[4][1]="100px";                //�п�
        iArray[4][2]=100;                  //�����ֵ
        iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[5]=new Array();
        iArray[5][0]="����������";             //����
        iArray[5][1]="100px";                //�п�
        iArray[5][2]=100;                  //�����ֵ
        iArray[5][3]=0; 

        iArray[6]=new Array();
        iArray[6][0]="VIP";             //����
        iArray[6][1]="30px";                //�п�
        iArray[6][2]=200;                  //�����ֵ
        iArray[6][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[7]=new Array();
        iArray[7][0]="����׶�";             //����
        iArray[7][1]="50px";                //�п�
        iArray[7][2]=100;                  //�����ֵ
        iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[8]=new Array();
        iArray[8][0]="�ʱ�ԭ��";             //����
        iArray[8][1]="50px";                //�п�
        iArray[8][2]=100;                  //�����ֵ
        iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[9]=new Array();
        iArray[9][0]="�ʱ���";             //����
        iArray[9][1]="80px";                //�п�
        iArray[9][2]=100;                  //�����ֵ
        iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[10]=new Array();
        iArray[10][0]="�ʱ�����";             //����
        iArray[10][1]="80px";                //�п�
        iArray[10][2]=100;                  //�����ֵ
        iArray[10][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[11]=new Array();
        iArray[11][0]="�ʱ�����";             //����
        iArray[11][1]="60px";                //�п�
        iArray[11][2]=100;                  //�����ֵ
        iArray[11][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[12]=new Array();
        iArray[12][0]="�ʱ�״̬";             //����
        iArray[12][1]="60px";                //�п�
        iArray[12][2]=100;                  //�����ֵ
        iArray[12][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������           
                                 
        LLSubmitApplyGrid = new MulLineEnter( "fm" , "LLSubmitApplyGrid" ); 
        //��Щ���Ա�����loadMulLineǰ
        LLSubmitApplyGrid.mulLineCount = 0;   
        LLSubmitApplyGrid.displayTitle = 1;
        LLSubmitApplyGrid.locked = 1;
        LLSubmitApplyGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
        LLSubmitApplyGrid.selBoxEventFuncName = "LLSubmitApplyGridClick"; //���RadioBoxʱ��Ӧ�ĺ�����
        //LLSubmitApplyGrid.selBoxEventFuncParm ="divLLInqConclusion"; //��Ӧ�����ĵڶ�������
        LLSubmitApplyGrid.hiddenPlus=1;
        LLSubmitApplyGrid.hiddenSubtraction=1;
        LLSubmitApplyGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
