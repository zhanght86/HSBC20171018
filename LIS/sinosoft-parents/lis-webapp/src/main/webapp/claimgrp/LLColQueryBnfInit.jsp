<%
//�������ƣ�LLBnfInit.jsp
//�����ܣ�������Ϣ��ѯҳ���ʼ��
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
    document.all('BnfKind').value = nullToEmpty("<%= tBnfKind %>");
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
//        fm.addButton.disabled = true;
//        fm.updateButton.disabled = true;
//        fm.deleteButton.disabled = true;
//        fm.saveButton.disabled = true;

    }
    catch(ex)
    {
        alert("��LLBnfInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }
}

function initSelBox()
{
    try
    {

    }
    catch(ex)
    {
        alert("��LLBnfInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
    }
}

function initForm()
{
    try
    {
    	initParam();
        initInpBox();
        initSelBox();
        initLLBalanceGrid();
        queryLLBalanceGrid();
        initLLBnfGrid();
        
    }
    catch(re)
    {
        alert("LLBnfInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}
//�ⰸ������ϸ��ĳ�ʼ��
function initLLBalanceGrid()
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
        iArray[1][1]="100px";                //�п�
        iArray[1][2]=100;                  //�����ֵ
        iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[2]=new Array();
        iArray[2][0]="������";             //����
        iArray[2][1]="200px";                //�п�
        iArray[2][2]=100;                  //�����ֵ
        iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[3]=new Array();
        iArray[3][0]="�⸶���";           //����
        iArray[3][1]="200px";                //�п�
        iArray[3][2]=100;                  //�����ֵ
        iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[4]=new Array();
        iArray[4][0]="GrpContNo";        //����
        iArray[4][1]="100px";                //�п�
        iArray[4][2]=100;                  //�����ֵ
        iArray[4][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[5]=new Array();
        iArray[5][0]="GrpPolNo";             //����
        iArray[5][1]="100px";                //�п�
        iArray[5][2]=200;                  //�����ֵ
        iArray[5][3]=3;

        iArray[6]=new Array();
        iArray[6][0]="ContNo";          //����
        iArray[6][1]="100px";                //�п�
        iArray[6][2]=200;                  //�����ֵ
        iArray[6][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[7]=new Array();
        iArray[7][0]="���κ�";          //����
        iArray[7][1]="100px";                //�п�
        iArray[7][2]=200;                  //�����ֵ
        iArray[7][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[8]=new Array();
        iArray[8][0]="�Ƿ��ѷ���";         //����
        iArray[8][1]="100px";                //�п�
        iArray[8][2]=200;                  //�����ֵ
        iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[9]=new Array();
        iArray[9][0]="�������";         //����
        iArray[9][1]="100px";                //�п�
        iArray[9][2]=200;                  //�����ֵ
        iArray[9][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        LLBalanceGrid = new MulLineEnter( "document" , "LLBalanceGrid" );
        //��Щ���Ա�����loadMulLineǰ
        LLBalanceGrid.mulLineCount = 5;
        LLBalanceGrid.displayTitle = 1;
        LLBalanceGrid.locked = 1;
        LLBalanceGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
        LLBalanceGrid.selBoxEventFuncName = "LLBalanceGridClick"; //���RadioBoxʱ��Ӧ�ĺ�����
//        LLBalanceGrid.selBoxEventFuncParm =""; //��Ӧ�����ĵڶ�������
        LLBalanceGrid.hiddenPlus=1;
        LLBalanceGrid.hiddenSubtraction=1;
        LLBalanceGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

//�����������˻����ʼ��
function initLLBnfGrid()
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
        iArray[1][1]="100px";                //�п�
        iArray[1][2]=100;                  //�����ֵ
        iArray[1][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[2]=new Array();
        iArray[2][0]="��������";             //����
        iArray[2][1]="100px";                //�п�
        iArray[2][2]=100;                  //�����ֵ
        iArray[2][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[3]=new Array();
        iArray[3][0]="�����˺���";     //����
        iArray[3][1]="100px";                //�п�
        iArray[3][2]=100;                  //�����ֵ
        iArray[3][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[4]=new Array();
        iArray[4][0]="���������";        //����
        iArray[4][1]="100px";                //�п�
        iArray[4][2]=100;                  //�����ֵ
        iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[5]=new Array();
        iArray[5][0]="�����˺���";             //����
        iArray[5][1]="100px";                //�п�
        iArray[5][2]=200;                  //�����ֵ
        iArray[5][3]=3;

        iArray[6]=new Array();
        iArray[6][0]="����������";          //����
        iArray[6][1]="120px";                //�п�
        iArray[6][2]=200;                  //�����ֵ
        iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[7]=new Array();
        iArray[7][0]="����˺���";         //����
        iArray[7][1]="100px";                //�п�
        iArray[7][2]=200;                  //�����ֵ
        iArray[7][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[8]=new Array();
        iArray[8][0]="���������";         //����
        iArray[8][1]="120px";                //�п�
        iArray[8][2]=200;                  //�����ֵ
        iArray[8][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[9]=new Array();
        iArray[9][0]="bnftype";         //����
        iArray[9][1]="80px";                //�п�
        iArray[9][2]=200;                  //�����ֵ
        iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[10]=new Array();
        iArray[10][0]="bnfgrade";         //����
        iArray[10][1]="80px";                //�п�
        iArray[10][2]=200;                  //�����ֵ
        iArray[10][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[11]=new Array();
        iArray[11][0]="relationtoinsured";         //����
        iArray[11][1]="80px";                //�п�
        iArray[11][2]=200;                  //�����ֵ
        iArray[11][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[12]=new Array();
        iArray[12][0]="sex";                //����
        iArray[12][1]="80px";                //�п�
        iArray[12][2]=200;                  //�����ֵ
        iArray[12][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[13]=new Array();
        iArray[13][0]="birthday";         //����
        iArray[13][1]="80px";                //�п�
        iArray[13][2]=200;                  //�����ֵ
        iArray[13][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[14]=new Array();
        iArray[14][0]="idtype";         //����
        iArray[14][1]="80px";                //�п�
        iArray[14][2]=200;                  //�����ֵ
        iArray[14][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[15]=new Array();
        iArray[15][0]="idno";         //����
        iArray[15][1]="80px";                //�п�
        iArray[15][2]=200;                  //�����ֵ
        iArray[15][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[16]=new Array();
        iArray[16][0]="relationtopayee";         //����
        iArray[16][1]="80px";                //�п�
        iArray[16][2]=200;                  //�����ֵ
        iArray[16][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[17]=new Array();
        iArray[17][0]="payeesex";         //����
        iArray[17][1]="80px";                //�п�
        iArray[17][2]=200;                  //�����ֵ
        iArray[17][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[18]=new Array();
        iArray[18][0]="payeebirthday";       //����
        iArray[18][1]="80px";                //�п�
        iArray[18][2]=200;                  //�����ֵ
        iArray[18][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[19]=new Array();
        iArray[19][0]="payeeidtype";         //����
        iArray[19][1]="80px";                //�п�
        iArray[19][2]=200;                  //�����ֵ
        iArray[19][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[20]=new Array();
        iArray[20][0]="payeeidno";         //����
        iArray[20][1]="80px";                //�п�
        iArray[20][2]=200;                  //�����ֵ
        iArray[20][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[21]=new Array();
        iArray[21][0]="������";         //����
        iArray[21][1]="80px";                //�п�
        iArray[21][2]=200;                  //�����ֵ
        iArray[21][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[22]=new Array();
        iArray[22][0]="�������";          //����
        iArray[22][1]="80px";                //�п�
        iArray[22][2]=200;                  //�����ֵ
        iArray[22][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[23]=new Array();
        iArray[23][0]="֧����ʽ";          //����
        iArray[23][1]="100px";                //�п�
        iArray[23][2]=200;                  //�����ֵ
        iArray[23][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[24]=new Array();
        iArray[24][0]="���б���";          //����
        iArray[24][1]="100px";                //�п�
        iArray[24][2]=200;                  //�����ֵ
        iArray[24][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[25]=new Array();
        iArray[25][0]="�����ʺ�";          //����
        iArray[25][1]="100px";                //�п�
        iArray[25][2]=200;                  //�����ֵ
        iArray[25][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[26]=new Array();
        iArray[26][0]="�����ʻ���";          //����
        iArray[26][1]="100px";                //�п�
        iArray[26][2]=200;                  //�����ֵ
        iArray[26][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
                
        LLBnfGrid = new MulLineEnter( "document" , "LLBnfGrid" );
        //��Щ���Ա�����loadMulLineǰ
        LLBnfGrid.mulLineCount = 5;
        LLBnfGrid.displayTitle = 1;
        LLBnfGrid.locked = 0;
        LLBnfGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
        LLBnfGrid.selBoxEventFuncName = "LLBnfGridClick"; //���RadioBoxʱ��Ӧ�ĺ�����
//        LLBnfGrid.selBoxEventFuncParm =""; //��Ӧ�����ĵڶ�������
        LLBnfGrid.hiddenPlus=1;
        LLBnfGrid.hiddenSubtraction=1;
        LLBnfGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
