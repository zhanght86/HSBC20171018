<%
//�������ƣ�LLSubReportDescInputInit.jsp
//�����ܣ��¹�������Ϣҳ���ʼ��
//�������ڣ�2005-05-10
//������  ��zhoulei
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
    document.all('WhoNo').value	= nullToEmpty("<%= tCustomerNo %>");
    document.all('StartPhase').value	= nullToEmpty("<%= tStartPhase %>");
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
//        fm.DescType.value = "";
        fm.Describe.value = "";
    }
    catch(ex)
    {
        alert("��LLSubReportDescInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }
}

function initSelBox()
{
    try
    {

    }
    catch(ex)
    {
        alert("��LLSubReportDescInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
    }
}

function initForm()
{
    try
    {
        initParam();
        initInpBox();
        initSelBox();
        initLLSubReportDescGrid();
        addMulline();
    }
    catch(re)
    {
        alert("LLSubReportDescInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}
// ������۱�ĳ�ʼ��
function initLLSubReportDescGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";              //�п�
        iArray[0][2]=10;                  //�����ֵ
        iArray[0][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="������";             //����
        iArray[1][1]="100px";                //�п�
        iArray[1][2]=100;                  //�����ֵ
        iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[2]=new Array();
        iArray[2][0]="�ͻ�����";             //����
        iArray[2][1]="100px";                //�п�
        iArray[2][2]=100;                  //�����ֵ
        iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[3]=new Array();
        iArray[3][0]="�������";             //����
        iArray[3][1]="100px";                //�п�
        iArray[3][2]=100;                  //�����ֵ
        iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

//      iArray[3]=new Array();
//      iArray[3][0]="�Ա�";             //����
//      iArray[3][1]="60px";                //�п�
//      iArray[3][2]=200;                  //�����ֵ
//      iArray[3][3]=2;                    //�Ƿ���������,1��ʾ����0��ʾ������
//      iArray[3][10] = "Sex";
//      iArray[3][11] = "0|^0|��^1|Ů^2|����";
//      iArray[3][12] = "3";
//      iArray[3][19] = "0";

        iArray[4]=new Array();
        iArray[4][0]="��������";             //����
        iArray[4][1]="100px";                //�п�
        iArray[4][2]=100;                  //�����ֵ
        iArray[4][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[5]=new Array();
        iArray[5][0]="��������";             //����
        iArray[5][1]="150px";                //�п�
        iArray[5][2]=200;                  //�����ֵ
        iArray[5][3]=0;


        iArray[6]=new Array();
        iArray[6][0]="����Ա";             //����
        iArray[6][1]="80px";                //�п�
        iArray[6][2]=200;                  //�����ֵ
        iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[7]=new Array();
        iArray[7][0]="�������";             //����
        iArray[7][1]="100px";                //�п�
        iArray[7][2]=100;                  //�����ֵ
        iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[8]=new Array();
        iArray[8][0]="���ʱ��";             //����
        iArray[8][1]="100px";                //�п�
        iArray[8][2]=100;                  //�����ֵ
        iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

//      iArray[9]=new Array();
//      iArray[9][0]="";             //����
//      iArray[9][1]="0px";                //�п�
//      iArray[9][2]=100;                  //�����ֵ
//      iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        LLSubReportDescGrid = new MulLineEnter( "document" , "LLSubReportDescGrid" );
        //��Щ���Ա�����loadMulLineǰ
        LLSubReportDescGrid.mulLineCount = 5;
        LLSubReportDescGrid.displayTitle = 1;
        LLSubReportDescGrid.locked = 1;
        LLSubReportDescGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
        LLSubReportDescGrid.selBoxEventFuncName ="LLSubReportDescGridClick"; //��Ӧ�ĺ���������������
//        LLSubReportDescGrid.selBoxEventFuncParm =""; //����Ĳ���,����ʡ�Ը���        
        LLSubReportDescGrid.hiddenPlus=1;
        LLSubReportDescGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        LLSubReportDescGrid.loadMulLine(iArray);
        }
        catch(ex)
        {
            alert(ex);
        }
}

</script>
