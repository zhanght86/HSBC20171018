<%
//�������ƣ�TempFeeQueryInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
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
	// ��ѯ����
    document.all('ContNo').value = '';
    document.all('MainPolNo').value = '';
    document.all('RiskCode').value = '';
  }
  catch(ex)
  {
    alert("��IndiDueFeeListInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("��IndiDueFeeListInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();  
    initIndiDueFeeListGrid();
  }
  catch(re)
  {
    alert("��IndiDueFeeListInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
 
// ������Ϣ�б�ĳ�ʼ��
function initIndiDueFeeListGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            		//�����ֵ
      iArray[0][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="������";   		        //����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            		//�����ֵ
      iArray[1][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���ֺ�";   		        //����
      iArray[2][1]="160px";            		//�п�
      iArray[2][2]=100;            		//�����ֵ
      iArray[2][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      
      iArray[3]=new Array();
      iArray[3][0]="���ֱ���";          		//����
      iArray[3][1]="60px";      	      		//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=2;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      iArray[3][4]="RiskCode";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[3][5]="1|2";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[3][9]="���ֱ���|code:RiskCode&NOTNULL";
      iArray[3][18]=300;
      
      
      iArray[4]=new Array();
      iArray[4][0]="�ɷѼ��";		        //����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=60;            		//�����ֵ
      iArray[4][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="����/����";		        //����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=60;            		//�����ֵ
      iArray[5][3]=0;  
      
      iArray[6]=new Array();
      iArray[6][0]="���ղ���״̬";         		//����
      iArray[6][1]="150px";            		//�п�
      iArray[6][2]=200;            	        //�����ֵ
      iArray[6][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[7]=new Array();
      iArray[7][0]="��������";         		//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=200;            	        //�����ֵ
      iArray[7][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="�ս�����";         		//����
      iArray[8][1]="100px";            		//�п�
      iArray[8][2]=200;            	        //�����ֵ
      iArray[8][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[9]=new Array();
      iArray[9][0]="�Ƿ�������60��������ȫ";         		//����
      iArray[9][1]="180px";            		//�п�
      iArray[9][2]=200;            	        //�����ֵ
      iArray[9][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="�Ƿ���������";         		//����
      iArray[10][1]="100px";            		//�п�
      iArray[10][2]=200;            	        //�����ֵ
      iArray[10][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[11]=new Array();
      iArray[11][0]="�����˱���";         		//����
      iArray[11][1]="100px";            		//�п�
      iArray[11][2]=200;            	        //�����ֵ
      iArray[11][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������
      
      IndiDueQueryGrid = new MulLineEnter( "fm" , "IndiDueQueryGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      IndiDueQueryGrid.mulLineCount = 6;   
      IndiDueQueryGrid.displayTitle = 1;
      IndiDueQueryGrid.hiddenPlus = 1;
      IndiDueQueryGrid.hiddenSubtraction = 1;
      IndiDueQueryGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>