<script language="JavaScript">

                     

function initForm()
{
  try
  {
    fm.SubManageCom.value = manageCom;
		showOneCodeName('station', 'SubManageCom', 'ManageComName');
    initAgentGrid();
  }
  catch(re)
  {
    alert("AgentQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
/************************************************************
 *               
 *���룺          û��
 *�����          û��
 *���ܣ�          ��ʼ��AgentGrid
 ************************************************************
 */
function initAgentGrid()
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
        iArray[1][0]="�ʱ����";         //����
        iArray[1][1]="100px";         //���
        iArray[1][2]=100;         //��󳤶�
        iArray[1][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[2]=new Array();
        iArray[2][0]="�ŵ�����";         //����
        iArray[2][1]="100px";         //���
        iArray[2][2]=100;         //��󳤶�
        iArray[2][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[3]=new Array();
        iArray[3][0]="��ȫ��Ŀ";         //����
        iArray[3][1]="60px";         //���
        iArray[3][2]=100;         //��󳤶�
        iArray[3][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[4]=new Array();
        iArray[4][0]="�ʱ���";         //����
        iArray[4][1]="60px";         //���
        iArray[4][2]=100;         //��󳤶�
        iArray[4][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[5]=new Array();
        iArray[5][0]="״̬";         //����
        iArray[5][1]="60px";         //���
        iArray[5][2]=100;         //��󳤶�
        iArray[5][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
        
        iArray[6]=new Array();
        iArray[6][0]="�ʱ�����";         //����
        iArray[6][1]="40px";         //���
        iArray[6][2]=100;         //��󳤶�
        iArray[6][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
        
        iArray[7]=new Array();
        iArray[7][0]="�ʱ�����";         //����
        iArray[7][1]="80px";         //���
        iArray[7][2]=100;         //��󳤶�
        iArray[7][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
        
  
        AgentGrid = new MulLineEnter( "document" , "AgentGrid" ); 

        //��Щ���Ա�����loadMulLineǰ
        AgentGrid.mulLineCount = 5;   
        AgentGrid.displayTitle = 1;
        AgentGrid.canSel=1;
//        AgentGrid.canChk=0;
        AgentGrid.locked=1;
	      AgentGrid.hiddenPlus=1;
	      AgentGrid.hiddenSubtraction=1;
	      AgentGrid.selBoxEventFuncName = "ReLLSubmitApplyGridClick"; //����RadioBoxʱ��Ӧ����
        AgentGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert("��ʼ��AgentGridʱ����"+ ex);
      }
    }


</script>
