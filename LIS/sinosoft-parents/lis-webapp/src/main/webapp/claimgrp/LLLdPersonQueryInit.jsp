<%
//�������ƣ�LLLDPersonQueryInit.jsp
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
        alert("��LLLDPersonQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }      
}

function initSelBox()
{  
    try                 
    {
 
    }
    catch(ex)
    {
       alert("��LLLDPersonQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
    }
}                                        

function initForm()
{
    try
    {
        initInpBox();
        initSelBox();  
        initPersonGrid();  
    }
    catch(re)
    {
        alert("LLLDPersonQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}
// ������Ϣ�б�ĳ�ʼ��
function initPersonGrid()
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
      iArray[1][0]="�ͻ�����";             //����
      iArray[1][1]="160px";                //�п�
      iArray[1][2]=100;                  //�����ֵ
      iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="����";             //����
      iArray[2][1]="100px";                //�п�
      iArray[2][2]=100;                  //�����ֵ
      iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�Ա�";             //����
      iArray[3][1]="60px";                //�п�
      iArray[3][2]=200;                  //�����ֵ
      iArray[3][3]=2;                    //�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][10] = "Sex";
      iArray[3][11] = "0|^0|��^1|Ů^2|����";
      iArray[3][12] = "3";
      iArray[3][19] = "0";    

      iArray[4]=new Array();
      iArray[4][0]="��������";             //����
      iArray[4][1]="100px";                //�п�
      iArray[4][2]=200;                  //�����ֵ
      iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="֤������";             //����
      iArray[5][1]="60px";                //�п�
      iArray[5][2]=200;                  //�����ֵ
      iArray[5][3]=2; 
      iArray[5][4]="IDType";                        //�Ƿ����ô���:null||""Ϊ������
      iArray[5][5]="3";                                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[5][9]="֤������|code:IDType&NOTNULL";
      iArray[5][18]=250;
      iArray[5][19]= 0 ;

      iArray[6]=new Array();
      iArray[6][0]="֤������";             //����
      iArray[6][1]="140px";                //�п�
      iArray[6][2]=200;                  //�����ֵ
      iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="VIPֵ";             //����
      iArray[7][1]="80px";                //�п�
      iArray[7][2]=200;                  //�����ֵ
      iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      PersonGrid = new MulLineEnter( "fm" , "PersonGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PersonGrid.mulLineCount = 0;   
      PersonGrid.displayTitle = 1;
      PersonGrid.locked = 1;
//      PersonGrid.canChk = 1;
      PersonGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
      PersonGrid.hiddenPlus=1;
      PersonGrid.hiddenSubtraction=1;
      PersonGrid.loadMulLine(iArray);  
    }
    catch(ex)
    {
        alert(ex);
    }
}
</script>
