<%
//�������ƣ�OLDCodeQuery.js
//�����ܣ�
//�������ڣ�2002-08-16 17:44:48
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
  //$initfield$
  }
  catch(ex)
  {
    alert("��OLDCodeQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��OLDCodeQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
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
    alert("OLDCodeQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
/************************************************************
 *               
 *���룺          û��
 *�����          û��
 *���ܣ�          ��ʼ��CodeGrid
 ************************************************************
 */
function initCodeGrid()
  {                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";         //����
        iArray[0][1]="100px";         //����
        iArray[0][2]=100;         //����
        iArray[0][3]=0;         //����

            iArray[1]=new Array();
    iArray[1][0]="��������";         //����
    iArray[1][1]="393px";         //���
    iArray[1][2]=100;         //��󳤶�
    iArray[1][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

    iArray[2]=new Array();
    iArray[2][0]="����";         //����
    iArray[2][1]="278px";         //���
    iArray[2][2]=100;         //��󳤶�
    iArray[2][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

    iArray[3]=new Array();
    iArray[3][0]="��������";         //����
    iArray[3][1]="261px";         //���
    iArray[3][2]=100;         //��󳤶�
    iArray[3][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
    
    iArray[4]=new Array();
    iArray[4][0]="�������";         //����
    iArray[4][1]="0px";         //���
    iArray[4][2]=0;         //��󳤶�
    iArray[4][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
    
    iArray[5]=new Array();
    iArray[5][0]="��������";         //����
    iArray[5][1]="0px";         //���
    iArray[5][2]=0;         //��󳤶�
    iArray[5][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
    
    iArray[6]=new Array();
    iArray[6][0]="������־";         //����
    iArray[6][1]="0px";         //���
    iArray[6][2]=0;         //��󳤶�
    iArray[6][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����


   CodeGrid = new MulLineEnter( "fm" , "CodeGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      CodeGrid.mulLineCount = 10;   
      CodeGrid.displayTitle = 1;
      CodeGrid.locked = 1;
      CodeGrid.canSel = 1;
      CodeGrid.loadMulLine(iArray);  
      CodeGrid.selBoxEventFuncName = "selectItem";
      
      //��Щ����������loadMulLine����
      //CodeGrid.setRowColData(1,1,"asdf");
      
       // CodeGrid = new MulLineEnter( "fm" , "CodeGrid" ); 

        //��Щ���Ա�����loadMulLineǰ
       // CodeGrid.mulLineCount = 3;   
       // CodeGrid.displayTitle = 1;
       // CodeGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert("��ʼ��CodeGridʱ����"+ ex);
      }
    }


</script>