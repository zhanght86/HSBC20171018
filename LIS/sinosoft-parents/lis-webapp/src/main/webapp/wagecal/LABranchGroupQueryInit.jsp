<%
//�������ƣ�LABranchGroupInit.jsp
//�����ܣ�
//�������ڣ�
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   
  //$initfield$document.all('BranchType').value=top.opener.document.all('BranchType').value;
  }
  catch(ex)
  {
    alert("��LABranchGroupQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��LABranchGroupQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
	  initBranchGroupGrid();
  }
  catch(re)
  {
    alert("LABranchGroupQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
/************************************************************
 *               
 *���룺          û��
 *�����          û��
 *���ܣ�          ��ʼ��BranchGroupGrid
 ************************************************************
 */
function initBranchGroupGrid()
  {                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";         //����
        iArray[0][1]="30px";         //����
        iArray[0][2]=100;         //����
        iArray[0][3]=0;         //����

        iArray[1]=new Array();
        iArray[1][0]="չҵ��������";         //����
        iArray[1][1]="80px";         //���
        iArray[1][2]=100;         //��󳤶�
        iArray[1][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[2]=new Array();
        iArray[2][0]="չҵ��������";         //����
        iArray[2][1]="130px";         //���
        iArray[2][2]=100;         //��󳤶�
        iArray[2][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[3]=new Array();
        iArray[3][0]="�������";         //����
        iArray[3][1]="80px";         //���
        iArray[3][2]=100;         //��󳤶�
        iArray[3][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[4]=new Array();
        iArray[4][0]="������־����";         //����
        iArray[4][1]="70px";         //���
        iArray[4][2]=100;         //��󳤶�
        iArray[4][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
        
        iArray[5]=new Array();
        iArray[5][0]="ͣҵ";         //����
        iArray[5][1]="40px";         //���
        iArray[5][2]=100;         //��󳤶�
        iArray[5][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
        
         iArray[6]=new Array();
        iArray[6][0]="չҵ�����ڲ�����";         //����
        iArray[6][1]="0px";         //���
        iArray[6][2]=100;         //��󳤶�
        iArray[6][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����


  
        BranchGroupGrid = new MulLineEnter( "fm" , "BranchGroupGrid" ); 

        //��Щ���Ա�����loadMulLineǰ
        BranchGroupGrid.mulLineCount = 0;   
        BranchGroupGrid.displayTitle = 1;
        BranchGroupGrid.locked=1;
        BranchGroupGrid.canSel=1;
        BranchGroupGrid.canChk=0;
        BranchGroupGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert("��ʼ��BranchGroupGridʱ����"+ ex);
      }
    }


</script>
