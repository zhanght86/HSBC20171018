<%
//�������ƣ�CodeQuery.js
//�����ܣ�
//�������ڣ�2002-08-16 17:44:48
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   
  //$initfield$
  }
  catch(ex)
  {
    alert("��CodeQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=��&1=Ů&2=����");      
//    setOption("sex","0=��&1=Ů&2=����");        
//    setOption("reduce_flag","0=����״̬&1=�����");
//    setOption("pad_flag","0=����&1=�潻");   
  }
  catch(ex)
  {
    alert("��CodeQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
	  initCodeGrid();
  }
  catch(re)
  {
    alert("CodeQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
/************************************************************
 *               
 *���룺          û��
 *�����          û��
 *���ܣ�          ��ʼ��CodeGrid
 ************************************************************
 */
  var CodeGrid;          //����Ϊȫ�ֱ������ṩ��displayMultilineʹ��
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
        iArray[1][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[2]=new Array();
        iArray[2][0]="����";         //����
        iArray[2][1]="120px";         //���
        iArray[2][2]=100;         //��󳤶�
        iArray[2][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[3]=new Array();
        iArray[3][0]="��������";         //����
        iArray[3][1]="150px";         //���
        iArray[3][2]=100;         //��󳤶�
        iArray[3][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
    
        iArray[4]=new Array();
        iArray[4][0]="�������";         //����
        iArray[4][1]="150px";         //���
        iArray[4][2]=100;         //��󳤶�
        iArray[4][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
        
        iArray[5]=new Array();
        iArray[5][0]="������־";         //����
        iArray[5][1]="100px";         //���
        iArray[5][2]=100;         //��󳤶�
        iArray[5][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����


  
        CodeGrid = new MulLineEnter( "document" , "CodeGrid" ); 

        //��Щ���Ա�����loadMulLineǰ
        CodeGrid.mulLineCount = 5;   
        CodeGrid.displayTitle = 1;
        CodeGrid.canSel=1;
        CodeGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert("��ʼ��CodeGridʱ����"+ ex);
      }
    }


</script>
