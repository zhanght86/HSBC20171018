<%
//�������ƣ�LLClaimRegisterQueryInit.jsp
//�����ܣ�
//�������ڣ� 
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
    try
    {                                   


    }
    catch(ex)
    {
        alert("��LLClaimRegisterQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }      
}

function initSelBox()
{  
    try                 
    {
 
    }
    catch(ex)
    {
       alert("��LLClaimRegisterQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
    }
}                                        

function initForm()
{
    try
    {
        initInpBox();
        initSelBox();  
        initLLClaimRegisterGrid();  
    }
    catch(re)
    {
        alert("LLClaimRegisterQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}
// ������Ϣ�б�ĳ�ʼ��
function initLLClaimRegisterGrid()
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
      iArray[1][1]="120px";                //�п�
      iArray[1][2]=100;                  //�����ֵ
      iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="����������";             //����
      iArray[2][1]="100px";                //�п�
      iArray[2][2]=100;                  //�����ֵ
      iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�����˵绰";             //����
      iArray[3][1]="80px";                //�п�
      iArray[3][2]=200;                  //�����ֵ
      iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="������ͨѶ��ַ";             //����
      iArray[4][1]="120px";                //�п�
      iArray[4][2]=500;                  //�����ֵ
      iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="���������¹��˹�ϵ";             //����
      iArray[5][1]="60px";                //�п�
      iArray[5][2]=200;                  //�����ֵ
      iArray[5][3]=0; 


      iArray[6]=new Array();
      iArray[6][0]="����������";             //����
      iArray[6][1]="100px";                //�п�
      iArray[6][2]=200;                  //�����ֵ
      iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="���յص�";             //����
      iArray[7][1]="80px";                //�п�
      iArray[7][2]=200;                  //�����ֵ
      iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="������������";             //����
      iArray[8][1]="100px";                //�п�
      iArray[8][2]=200;                  //�����ֵ
      iArray[8][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="����ԭ��";             //����
      iArray[9][1]="100px";                //�п�
      iArray[9][2]=200;                  //�����ֵ
      iArray[9][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������            
      
      iArray[10]=new Array();
      iArray[10][0]="�ⰸ����";             //����AccidentReason
      iArray[10][1]="0px";                //�п�
      iArray[10][2]=200;                  //�����ֵ
      iArray[10][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������        
      
      iArray[11]=new Array();
      iArray[11][0]="�ⰸ״̬";             //����
      iArray[11][1]="0px";                //�п�
      iArray[11][2]=200;                  //�����ֵ
      iArray[11][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[12]=new Array();
      iArray[12][0]="��Ͻ����";             //����AccidentReason
      iArray[12][1]="0px";                //�п�
      iArray[12][2]=200;                  //�����ֵ
      iArray[12][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������               
      
      LLClaimRegisterGrid = new MulLineEnter( "fm" , "LLClaimRegisterGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LLClaimRegisterGrid.mulLineCount = 10;   
      LLClaimRegisterGrid.displayTitle = 1;
      LLClaimRegisterGrid.locked = 0;
//      LLClaimRegisterGrid.canChk = 1;
      LLClaimRegisterGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
      LLClaimRegisterGrid.hiddenPlus=1;
      LLClaimRegisterGrid.hiddenSubtraction=1;
      LLClaimRegisterGrid.loadMulLine(iArray);  

    }
    catch(ex)
    {
        alert(ex);
    }
}
</script>
