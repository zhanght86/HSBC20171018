<%
//�������ƣ�LLReportQueryInit.jsp
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
        alert("��LLReportQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }      
}

function initSelBox()
{  
    try                 
    {
 
    }
    catch(ex)
    {
       alert("��LLReportQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
    }
}                                        

function initForm()
{
    try
    {
        initInpBox();
        initSelBox();  
        initLLReportGrid();  
    }
    catch(re)
    {
        alert("LLReportQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}
// ������Ϣ�б�ĳ�ʼ��
function initLLReportGrid()
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
      iArray[6][0]="������ʽ";             //����
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
      iArray[9][0]="��Ͻ����";             //����
      iArray[9][1]="100px";                //�п�
      iArray[9][2]=200;                  //�����ֵ
      iArray[9][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������            
      
      iArray[10]=new Array();
      iArray[10][0]="����ԭ��";             //����AccidentReason
      iArray[10][1]="0px";                //�п�
      iArray[10][2]=200;                  //�����ֵ
      iArray[10][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������         
      
      LLReportGrid = new MulLineEnter( "fm" , "LLReportGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LLReportGrid.mulLineCount = 10;   
      LLReportGrid.displayTitle = 1;
      LLReportGrid.locked = 0;
//      LLReportGrid.canChk = 1;
      LLReportGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
      LLReportGrid.hiddenPlus=1;
      LLReportGrid.hiddenSubtraction=1;
      LLReportGrid.loadMulLine(iArray);  

    }
    catch(ex)
    {
        alert(ex);
    }
}
</script>
