<%
//�������ƣ�SysCertTakeBackQueryInit.jsp
//�����ܣ�
//�������ڣ�2002-10-14 10:20:50
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

function initForm()
{
  try
  {
	  initSysCertifyGrid();
  }
  catch(re)
  {
    alert("SysCertTakeBackQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
/************************************************************
 *               
 *���룺          û��
 *�����          û��
 *���ܣ�          ��ʼ��SysCertifyGrid
 ************************************************************
 */
function initSysCertifyGrid()
{                               
  var iArray = new Array();
  try
  {
    iArray[0]=new Array();
    iArray[0][0]="���";				//����
    iArray[0][1]="30px";     		//���
    iArray[0][2]=30;	        	//��󳤶�
    iArray[0][3]=0; 	        	//�Ƿ�����¼�룬0--���ܣ�1--����

    iArray[1]=new Array();
    iArray[1][0]="��֤����";		//����
    iArray[1][1]="80px";        //���
    iArray[1][2]=100;         	//��󳤶�
    iArray[1][3]=0;         		//�Ƿ�����¼�룬0--���ܣ�1--����

    iArray[2]=new Array();
    iArray[2][0]="��֤����";      //����
    iArray[2][1]="180px";         //���
    iArray[2][2]=100;         		//��󳤶�
    iArray[2][3]=0;         			//�Ƿ�����¼�룬0--���ܣ�1--����

    iArray[3]=new Array();
    iArray[3][0]="���Ż���";	    //����
    iArray[3][1]="80px";          //���
    iArray[3][2]=100;         		//��󳤶�
    iArray[3][3]=0;         			//�Ƿ�����¼�룬0--���ܣ�1--����

    iArray[4]=new Array();
    iArray[4][0]="���ջ���";    	//����
    iArray[4][1]="80px";          //���
    iArray[4][2]=100;         		//��󳤶�
    iArray[4][3]=0;         			//�Ƿ�����¼�룬0--���ܣ�1--����

    iArray[5]=new Array();
    iArray[5][0]="����Ա";      	//����
    iArray[5][1]="80px";          //���
    iArray[5][2]=100;         		//��󳤶�
    iArray[5][3]=0;         			//�Ƿ�����¼�룬0--���ܣ�1--����
    
    iArray[6]=new Array();
    iArray[6][0]="��Ч����";      	//����
    iArray[6][1]="80px";          //���
    iArray[6][2]=100;         		//��󳤶�
    iArray[6][3]=0;         			//�Ƿ�����¼�룬0--���ܣ�1--����
    
    iArray[7]=new Array();
    iArray[7][0]="������";      	//����
    iArray[7][1]="80px";          //���
    iArray[7][2]=100;         		//��󳤶�
    iArray[7][3]=0;         			//�Ƿ�����¼�룬0--���ܣ�1--����
    
    iArray[8]=new Array();
    iArray[8][0]="��������";      	//����
    iArray[8][1]="80px";          //���
    iArray[8][2]=100;         		//��󳤶�
    iArray[8][3]=0;         			//�Ƿ�����¼�룬0--���ܣ�1--����
    
    iArray[9]=new Array();
    iArray[9][0]="���ź�";      	//����
    iArray[9][1]="80px";          //���
    iArray[9][2]=100;         		//��󳤶�
    iArray[9][3]=0;         			//�Ƿ�����¼�룬0--���ܣ�1--����
    
    iArray[10]=new Array();
    iArray[10][0]="���պ�";      	//����
    iArray[10][1]="80px";          //���
    iArray[10][2]=100;         		//��󳤶�
    iArray[10][3]=0;         			//�Ƿ�����¼�룬0--���ܣ�1--����
    
    iArray[11]=new Array();
    iArray[11][0]="��������";      	//����
    iArray[11][1]="80px";          //���
    iArray[11][2]=100;         		//��󳤶�
    iArray[11][3]=0;         			//�Ƿ�����¼�룬0--���ܣ�1--����
    
    iArray[12]=new Array();
    iArray[12][0]="��������";      	//����
    iArray[12][1]="80px";          //���
    iArray[12][2]=100;         		//��󳤶�
    iArray[12][3]=0;         			//�Ƿ�����¼�룬0--���ܣ�1--����
    

    SysCertifyGrid = new MulLineEnter( "document" , "SysCertifyGrid" ); 

    //��Щ���Ա�����loadMulLineǰ
    SysCertifyGrid.mulLineCount = 5;   
    SysCertifyGrid.displayTitle = 1;
    SysCertifyGrid.canSel=1;
    SysCertifyGrid.loadMulLine(iArray);  

  }
  catch(ex)
  {
    alert("��ʼ��SysCertifyGridʱ����"+ ex);
  }
}
</script>
