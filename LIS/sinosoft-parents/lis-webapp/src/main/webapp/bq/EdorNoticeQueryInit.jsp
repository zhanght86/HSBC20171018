<script language="JavaScript">

                     

function initForm()
{
  try
  {
  	fm.EdorAcceptNo.value=EdorAcceptNo;
		easyQueryClickAll();
  }
  catch(re)
  {
    alert("AllNoticeInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
/************************************************************
 *               
 *���룺          û��
 *�����          û��
 *���ܣ�          ��ʼ��AgentGrid
 ************************************************************
 */

    
function initAllNoticeGrid()
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
        iArray[1][0]="��ȫ�����";         //����
        iArray[1][1]="120px";         //���
        iArray[1][2]=100;         //��󳤶�
        iArray[1][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[2]=new Array();
        iArray[2][0]="���˱�����";         //����
        iArray[2][1]="120px";         //���
        iArray[2][2]=100;         //��󳤶�
        iArray[2][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[3]=new Array();
        iArray[3][0]="�ͻ���";         //����
        iArray[3][1]="80px";         //���
        iArray[3][2]=100;         //��󳤶�
        iArray[3][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
 
        
        iArray[4]=new Array();
        iArray[4][0]="���������Code";         //����
        iArray[4][1]="0px";         //���
        iArray[4][2]=100;         //��󳤶�
        iArray[4][3]=3;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[5]=new Array();
        iArray[5][0]="���������";         //����
        iArray[5][1]="80px";         //���
        iArray[5][2]=100;         //��󳤶�
        iArray[5][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[6]=new Array();
        iArray[6][0]="������";         //����
        iArray[6][1]="50px";         //���
        iArray[6][2]=100;         //��󳤶�
        iArray[6][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
        
        iArray[7]=new Array();
        iArray[7][0]="�������";         //����
        iArray[7][1]="60px";         //���
        iArray[7][2]=100;         //��󳤶�
        iArray[7][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
        
        iArray[8]=new Array();
        iArray[8][0]="�·�����";         //����
        iArray[8][1]="80px";         //���
        iArray[8][2]=100;         //��󳤶�
        iArray[8][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

     		iArray[9]=new Array();
      	iArray[9][0]="����";         		//����
      	iArray[9][1]="0px";            		//�п�
      	iArray[9][2]=200;            			//�����ֵ
      	iArray[9][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      	iArray[10]=new Array();
      	iArray[10][0]="�ظ�";         		//����
      	iArray[10][1]="0px";            		//�п�
      	iArray[10][2]=60;            			//�����ֵ
      	iArray[10][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
  
        AllNoticeGrid = new MulLineEnter( "fm" , "AllNoticeGrid" ); 
        AllNoticeGrid.displayTitle = 1;
        AllNoticeGrid.canSel=1;
        AllNoticeGrid.locked=1;
	      AllNoticeGrid.hiddenPlus=1;
	      AllNoticeGrid.hiddenSubtraction=1;
	      AllNoticeGrid.selBoxEventFuncName = "ShowInfo";      
        AllNoticeGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert("��ʼ��AllNoticeGridʱ����"+ ex);
      }
    }


</script>
