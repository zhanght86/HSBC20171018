<%
//�������ƣ�LLCaseBackMissInit.jsp
//�����ܣ�
//�������ڣ�2005-10-20 11:50
//������  ��wanzh
//���¼�¼��
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

//���ղ���
function initParam()
{
    document.all('Operator').value  = nullToEmpty("<%= tGlobalInput.Operator %>");
    document.all('ComCode').value   = nullToEmpty("<%= tGlobalInput.ComCode %>");
    document.all('ManageCom').value = nullToEmpty("<%= tGlobalInput.ManageCom %>");
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
         initLLCaseBackGrid();
         initSelfLLCaseBackGrid();
         querySelfGrid();
    }
    catch(re)
    {
         alert("LLCaseBackMissInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}

// ������Ϣ�б�ĳ�ʼ��
function initLLCaseBackGrid()
{                               
    var iArray = new Array();
    
    try
    {
    
    iArray[0]=new Array();
    iArray[0][0]="���";                //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";                //�п�
    iArray[0][2]=10;                    //�����ֵ
    iArray[0][3]=0;                     //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="�ⰸ��";              //����
    iArray[1][1]="150px";               //�п�
    iArray[1][2]=100;                   //�����ֵ
    iArray[1][3]=0;                     //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="������";              //����
    iArray[2][1]="100px";               //�п�
    iArray[2][2]=100;                   //�����ֵ
    iArray[2][3]=0;                     //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="��������";  //����
    iArray[3][1]="80px";               //�п�
    iArray[3][2]=80;                   //�����ֵ
    iArray[3][3]=0;                     //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="����������";          //����
    iArray[4][1]="100px";               //�п�
    iArray[4][2]=500;                   //�����ֵ
    iArray[4][3]=0;                     //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="����״̬";          //����
    iArray[5][1]="60px";               //�п�
    iArray[5][2]=60;                   //�����ֵ
    iArray[5][3]=0; 

    iArray[6]=new Array();
    iArray[6][0]="�¹�����";            //����
    iArray[6][1]="80px";               //�п�
    iArray[6][2]=200;                   //�����ֵ
    iArray[6][3]=0;                     //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="�᰸����";             //����
    iArray[7][1]="80px";                 //�п�
    iArray[7][2]=200;                    //�����ֵ
    iArray[7][3]=0;                      //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[8]=new Array();
    iArray[8][0]="��������";             //����
    iArray[8][1]="20px";                //�п�
    iArray[8][2]=20;                  //�����ֵ
    iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
    

    
    LLCaseBackGrid = new MulLineEnter( "document" , "LLCaseBackGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LLCaseBackGrid.mulLineCount      = 5;   
    LLCaseBackGrid.displayTitle      = 1;
    LLCaseBackGrid.locked            = 0;
    LLCaseBackGrid.canSel            = 1; 
    LLCaseBackGrid.hiddenPlus        = 1;
    LLCaseBackGrid.hiddenSubtraction = 1;
    LLCaseBackGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

// ������Ϣ�б�ĳ�ʼ��
function initSelfLLCaseBackGrid()
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
    iArray[1][0]="���˺�";             //����
    iArray[1][1]="100px";                //�п�
    iArray[1][2]=100;                  //�����ֵ
    iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="�ⰸ��";             //����
    iArray[2][1]="100px";                //�п�
    iArray[2][2]=100;                  //�����ֵ
    iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="��������";             //����
    iArray[3][1]="120px";                //�п�
    iArray[3][2]=200;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="��������";             //����
    iArray[4][1]="200px";                //�п�
    iArray[4][2]=500;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="��������";             //����
    iArray[5][1]="20px";                //�п�
    iArray[5][2]=20;                  //�����ֵ
    iArray[5][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

    
    SelfLLCaseBackGrid = new MulLineEnter( "document" , "SelfLLCaseBackGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    SelfLLCaseBackGrid.mulLineCount = 5;   
    SelfLLCaseBackGrid.displayTitle = 1;
    SelfLLCaseBackGrid.locked = 0;
    SelfLLCaseBackGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    SelfLLCaseBackGrid.selBoxEventFuncName ="SelfLLCaseBackGridClick"; //��Ӧ�ĺ���������������
    SelfLLCaseBackGrid.hiddenPlus=1;
    SelfLLCaseBackGrid.hiddenSubtraction=1;
    SelfLLCaseBackGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
