<%
//�������ƣ�CertifyPrintQueryInit.jsp
//�����ܣ�
//�������ڣ�2002-10-14 10:20:50
//������  ��Kevin
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
	  initCertifyPrintGrid();
  }
  catch(re)
  {
    alert("OLZCardPrintQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
/************************************************************
 *               
 *���룺          û��
 *�����          û��
 *���ܣ�          ��ʼ��CertifyPrintGrid
 ************************************************************
 */
function initCertifyPrintGrid()
{                               
  var iArray = new Array();
  try
  {
    iArray[0]=new Array();
    iArray[0][0]="���";			//����
    iArray[0][1]="30px";      //����
    iArray[0][2]=60;	        //����
    iArray[0][3]=0; 	        //����

    iArray[1]=new Array();
    iArray[1][0]="ӡˢ���κ�";		//����
    iArray[1][1]="180px";         //���
    iArray[1][2]=100;         		//��󳤶�
    iArray[1][3]=0;         			//�Ƿ�����¼�룬0--���ܣ�1--����

    iArray[2]=new Array();
    iArray[2][0]="��֤����";      //����
    iArray[2][1]="80px";          //���
    iArray[2][2]=100;         		//��󳤶�
    iArray[2][3]=2;                                     //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
    iArray[2][4]="CertifyCode";              	        //�Ƿ����ô���:null||""Ϊ������
    iArray[2][5]="2|3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
    iArray[2][9]="��֤����|code:CertifyCode&NOTNULL";
    iArray[2][18]=200;
   
   
    iArray[3]=new Array();
    iArray[3][0]="�������";	    //����
    iArray[3][1]="80px";          //���
    iArray[3][2]=100;         		//��󳤶�
    iArray[3][3]=0;         			//�Ƿ�����¼�룬0--���ܣ�1--����

    iArray[4]=new Array();
    iArray[4][0]="��������Ա";    //����
    iArray[4][1]="80px";          //���
    iArray[4][2]=100;         		//��󳤶�
    iArray[4][3]=0;         			//�Ƿ�����¼�룬0--���ܣ�1--����

    iArray[5]=new Array();
    iArray[5][0]="��������";      //����
    iArray[5][1]="80px";          //���
    iArray[5][2]=100;         		//��󳤶�
    iArray[5][3]=0;         			//�Ƿ�����¼�룬0--���ܣ�1--����

    iArray[6]=new Array();
    iArray[6][0]="��ʼ��";        //����
    iArray[6][1]="140px";          //���
    iArray[6][2]=100;         		//��󳤶�
    iArray[6][3]=0;         			//�Ƿ�����¼�룬0--���ܣ�1--����

    iArray[7]=new Array();
    iArray[7][0]="��ֹ��";        //����
    iArray[7][1]="140px";          //���
    iArray[7][2]=100;         		//��󳤶�
    iArray[7][3]=0;         			//�Ƿ�����¼�룬0--���ܣ�1--����

    CertifyPrintGrid = new MulLineEnter( "fm" , "CertifyPrintGrid" ); 

    //��Щ���Ա�����loadMulLineǰ
    CertifyPrintGrid.mulLineCount = 0;   
    CertifyPrintGrid.displayTitle = 1;
    CertifyPrintGrid.canSel=1;
    CertifyPrintGrid.hiddenPlus = 1;
    CertifyPrintGrid.hiddenSubtraction = 1;
    CertifyPrintGrid.loadMulLine(iArray);  

  }
  catch(ex)
  {
    alert("��ʼ��CertifyPrintGridʱ����"+ ex);
  }
}
</script>
