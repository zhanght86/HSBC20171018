<%
//�������ƣ�CodeInit.jsp
//�����ܣ�
//�������ڣ�2002-08-16 17:44:43
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {                                   
    document.all('CodeType').value = '';
    document.all('Code').value = '';
    document.all('CodeName').value = '';
    document.all('CodeAlias').value = '';
    document.all('OtherSign').value = '';


  }
  catch(ex)
  {
    alert("��CodeInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}
                                       
function initForm()
{
  try
  {
    initInpBox();
    initCodeGrid();   
  }
  catch(re)
  {
    alert("CodeInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initCodeGrid()
{                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";         //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";         //�п�
        iArray[0][2]=100;            //�����ֵ
        iArray[0][3]=0;              //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="��������";         //����
        iArray[1][1]="120px";         //���
        iArray[1][2]=100;         //��󳤶�
        iArray[1][3]=1;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[2]=new Array();
        iArray[2][0]="����";         //����
        iArray[2][1]="120px";         //���
        iArray[2][2]=100;         //��󳤶�
        iArray[2][3]=1;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[3]=new Array();
        iArray[3][0]="��������";         //����
        iArray[3][1]="150px";         //���
        iArray[3][2]=100;         //��󳤶�
        iArray[3][3]=1;         //�Ƿ�����¼�룬0--���ܣ�1--����
    
        iArray[4]=new Array();
        iArray[4][0]="�������";         //����
        iArray[4][1]="150px";         //���
        iArray[4][2]=100;         //��󳤶�
        iArray[4][3]=1;         //�Ƿ�����¼�룬0--���ܣ�1--����

        
        iArray[5]=new Array();
        iArray[5][0]="������־";         //����
        iArray[5][1]="100px";         //���
        iArray[5][2]=100;         //��󳤶�
        iArray[5][3]=1;         //�Ƿ�����¼�룬0--���ܣ�1--����


  
        CodeGrid = new MulLineEnter( "document" , "CodeGrid" ); 

        //��Щ���Ա�����loadMulLineǰ
        CodeGrid.mulLineCount = 1;   
        CodeGrid.displayTitle = 1;
		
        CodeGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert("��ʼ��CodeGridʱ����"+ ex);
      }
}
</script>
