<%
//Name��LLLcContSuspendInit.jsp
//Function����������ҳ���ʼ��
//Date��
//Author��yuejinwei
//Modify : zhoulei
//Modify date : 2005-11-19 14:18
%>
<!--�û�У����-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">

//����ǰ��ҳ�洫�ݹ����Ĳ���
function initParam()
{
    document.all('InsuredNo').value = nullToEmpty("<%= tInsuredNo %>");
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
    	initParam();
        initLLLcContSuspendGrid();
        initLLLcContInGrid();
        initQuery();
    }
    catch(re)
    {
        alert("LLLcContSuspendInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}

//�ⰸ�޹صı�����Ϣ
function initLLLcContSuspendGrid()
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
        iArray[1][0]="�����ͬ����";      //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[1][1]="150px";                //�п�
        iArray[1][2]=120;                  //�����ֵ
        iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[2]=new Array();
        iArray[2][0]="��ͬ����";           //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[2][1]="130px";                //�п�
        iArray[2][2]=100;                  //�����ֵ
        iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
 
        iArray[3]=new Array();
        iArray[3][0]="�ܵ�Ͷ��������";      //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[3][1]="100px";               //�п�
        iArray[3][2]=100;                  //�����ֵ
        iArray[3][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[4]=new Array();
        iArray[4][0]="ӡˢ����";            //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[4][1]="130px";                //�п�
        iArray[4][2]=100;                  //�����ֵ
        iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[5]=new Array();
        iArray[5][0]="�ܵ�����";           //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[5][1]="60px";                //�п�
        iArray[5][2]=100;                  //�����ֵ
        iArray[5][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[6]=new Array();
        iArray[6][0]="��ͥ������";          //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[6][1]="60px";                //�п�
        iArray[6][2]=100;                  //�����ֵ
        iArray[6][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[7]=new Array();
        iArray[7][0]="��ͥ���Ϻ�";          //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[7][1]="100px";                //�п�
        iArray[7][2]=100;                  //�����ֵ
        iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[8]=new Array();
        iArray[8][0]="ԭ����״̬��";       //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[8][1]="50px";                //�п�
        iArray[8][2]=100;                  //�����ֵ
        iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[9]=new Array();
        iArray[9][0]="��ȫ����";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[9][1]="80px";                //�п�
        iArray[9][2]=100;                  //�����ֵ
        iArray[9][3]=2;                    //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[9][10]="YesOrNo1";          //���ô��룺"CodeName"Ϊ�������ݵ�����
        iArray[9][11]="0|^1|��|^0|��";        //"CodeContent" �Ǵ���Ҫ������ʾ�Ĵ��� 

        iArray[10]=new Array();
        iArray[10][0]="���ڹ���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[10][1]="80px";                //�п�
        iArray[10][2]=150;                  //�����ֵ
        iArray[10][3]=2;                    //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[10][10]="YesOrNo2";          //���ô��룺"CodeName"Ϊ�������ݵ�����
        iArray[10][11]="0|^1|��|^0|��";        //"CodeContent" �Ǵ���Ҫ������ʾ�Ĵ��� 

        LLLcContSuspendGrid = new MulLineEnter( "document" , "LLLcContSuspendGrid" );
        //��Щ���Ա�����loadMulLineǰ
        LLLcContSuspendGrid.mulLineCount = 5;
        LLLcContSuspendGrid.displayTitle = 1;
        LLLcContSuspendGrid.canChk = 0;
        LLLcContSuspendGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
        LLLcContSuspendGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        LLLcContSuspendGrid.selBoxEventFuncName = "LLLcContSuspendGridClick"; //���RadioBoxʱ��Ӧ�ĺ�����
        LLLcContSuspendGrid.hiddenSubtraction=1; 
        LLLcContSuspendGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
          alert(ex);
      }
}

//�ⰸ��صı�����Ϣ
function initLLLcContInGrid()
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
        iArray[1][0]="�����ͬ����";      //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[1][1]="150px";                //�п�
        iArray[1][2]=120;                  //�����ֵ
        iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[2]=new Array();
        iArray[2][0]="��ͬ����";           //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[2][1]="130px";                //�п�
        iArray[2][2]=100;                  //�����ֵ
        iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
 
        iArray[3]=new Array();
        iArray[3][0]="�ܵ�Ͷ��������";      //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[3][1]="100px";               //�п�
        iArray[3][2]=100;                  //�����ֵ
        iArray[3][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[4]=new Array();
        iArray[4][0]="ӡˢ����";            //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[4][1]="130px";                //�п�
        iArray[4][2]=100;                  //�����ֵ
        iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[5]=new Array();
        iArray[5][0]="�ܵ�����";           //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[5][1]="60px";                //�п�
        iArray[5][2]=100;                  //�����ֵ
        iArray[5][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[6]=new Array();
        iArray[6][0]="��ͥ������";          //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[6][1]="60px";                //�п�
        iArray[6][2]=100;                  //�����ֵ
        iArray[6][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[7]=new Array();
        iArray[7][0]="��ͥ���Ϻ�";          //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[7][1]="100px";                //�п�
        iArray[7][2]=100;                  //�����ֵ
        iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[8]=new Array();
        iArray[8][0]="ԭ����״̬��";       //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[8][1]="50px";                //�п�
        iArray[8][2]=100;                  //�����ֵ
        iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[9]=new Array();
        iArray[9][0]="��ȫ����";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[9][1]="80px";                //�п�
        iArray[9][2]=100;                  //�����ֵ
        iArray[9][3]=2;                    //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[9][10]="YesOrNo1";          //���ô��룺"CodeName"Ϊ�������ݵ�����
        iArray[9][11]="0|^1|��|^0|��";        //"CodeContent" �Ǵ���Ҫ������ʾ�Ĵ��� 

        iArray[10]=new Array();
        iArray[10][0]="���ڹ���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[10][1]="80px";                //�п�
        iArray[10][2]=150;                  //�����ֵ
        iArray[10][3]=2;                    //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[10][10]="YesOrNo2";          //���ô��룺"CodeName"Ϊ�������ݵ�����
        iArray[10][11]="0|^1|��|^0|��";        //"CodeContent" �Ǵ���Ҫ������ʾ�Ĵ��� 

        LLLcContInGrid = new MulLineEnter( "document" , "LLLcContInGrid" );
        //��Щ���Ա�����loadMulLineǰ
        LLLcContInGrid.mulLineCount = 5;
        LLLcContInGrid.displayTitle = 1;
        LLLcContInGrid.canChk = 0;
        LLLcContInGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
        LLLcContInGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        LLLcContInGrid.selBoxEventFuncName = "LLLcContInGridClick"; //���RadioBoxʱ��Ӧ�ĺ�����
        LLLcContInGrid.hiddenSubtraction=1; 
        LLLcContInGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
          alert(ex);
      }
      
}
</script>
