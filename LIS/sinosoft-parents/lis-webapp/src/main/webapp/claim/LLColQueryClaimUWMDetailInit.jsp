<%
//�������ƣ�LLColQueryClaimUWMDetailInit.jsp
//�����ܣ���������������--��ѯ
//�������ڣ�2005-05-10
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


function initForm()
{
    try
    {
    	//alert(1);
        initParam();
        initLLClaimUWMDetailGrid();
        queryLLClaimUWMDetailGrid();
    }
    catch(re)
    {
        alert("LLColQueryClaimUWMDetailInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}
// ��������������--��ʼ��
function initLLClaimUWMDetailGrid()
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
        iArray[1][0]="������";             //����
        iArray[1][1]="160px";                //�п�
        iArray[1][2]=160;                  //�����ֵ
        iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[2]=new Array();
        iArray[2][0]="�������";             //����
        iArray[2][1]="80px";                //�п�
        iArray[2][2]=100;                  //�����ֵ
        iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[3]=new Array();
        iArray[3][0]="����Ա";             //����
        iArray[3][1]="60px";                //�п�
        iArray[3][2]=100;                  //�����ֵ
        iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[4]=new Array();
        iArray[4][0]="�������";             //����
        iArray[4][1]="100px";                //�п�
        iArray[4][2]=100;                  //�����ֵ
        iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[5]=new Array();
        iArray[5][0]="�������";             //����
        iArray[5][1]="100px";                //�п�
        iArray[5][2]=100;                  //�����ֵ
        iArray[5][3]=0; 

        iArray[6]=new Array();
        iArray[6][0]="dddd";             //����
        iArray[6][1]="80px";                //�п�
        iArray[6][2]=200;                  //�����ֵ
        iArray[6][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

                               
        LLClaimUWMDetailGrid = new MulLineEnter( "document" , "LLClaimUWMDetailGrid" ); 
        //��Щ���Ա�����loadMulLineǰ
        LLClaimUWMDetailGrid.mulLineCount = 5;   
        LLClaimUWMDetailGrid.displayTitle = 1;
        LLClaimUWMDetailGrid.locked = 1;
        LLClaimUWMDetailGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
        LLClaimUWMDetailGrid.selBoxEventFuncName = "LLClaimUWMDetailGridClick"; //���RadioBoxʱ��Ӧ�ĺ�����
        LLClaimUWMDetailGrid.hiddenPlus=1;
        LLClaimUWMDetailGrid.hiddenSubtraction=1;
        LLClaimUWMDetailGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
