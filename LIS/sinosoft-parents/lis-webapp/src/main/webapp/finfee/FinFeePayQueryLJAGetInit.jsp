<%
//�������ƣ�TempFeeQueryInit.jsp
//�����ܣ�
//�������ڣ�
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
    document.all('ActuGetNo').value = '';
    document.all('PayMode').value = '';
    document.all('OtherNo').value = '';
  }
  catch(ex)
  {
    alert("��FinFeeQueryLJAGetInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();  
    initFinFeeQueryGrid();
  }
  catch(re)
  {
    alert("��FinFeeQueryLJAGetInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

 var QueryLJAGetGrid ;
 
// ������Ϣ�б�ĳ�ʼ��
function initFinFeeQueryGrid()
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
      iArray[1][0]="ʵ������";   		//����
      iArray[1][1]="160px";            		//�п�
      iArray[1][2]=100;            		//�����ֵ
      iArray[1][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";		//����
      iArray[2][1]="160px";            		//�п�
      iArray[2][2]=60;            		//�����ֵ
      iArray[2][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=200;            	        //�����ֵ
      iArray[3][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="���ѷ�ʽ";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=200;            	        //�����ֵ
      iArray[4][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="����";      	   		//����
      iArray[5][1]="80px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][4]="currency";

      iArray[6]=new Array();
      iArray[6][0]="�������";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=200;            	        //�����ֵ
      iArray[6][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="��������";         		//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=200;            	        //�����ֵ
      iArray[7][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="��ȡ��";         		//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=200;            	        //�����ֵ
      iArray[8][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������  

      iArray[9]=new Array();
      iArray[9][0]="��ȡ�����֤";         		//����
      iArray[9][1]="120px";            		//�п�
      iArray[9][2]=200;            	        //�����ֵ
      iArray[9][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������      
      
      iArray[10]=new Array();
      iArray[10][0]="���б���";         		//����
      iArray[10][1]="0px";            		//�п�
      iArray[10][2]=200;            	        //�����ֵ
      iArray[10][3]=3;                   	//�Ƿ���������,1��ʾ����0��ʾ������  
      
      iArray[11]=new Array();
      iArray[11][0]="�����˻�";         		//����
      iArray[11][1]="0px";            		//�п�
      iArray[11][2]=200;            	        //�����ֵ
      iArray[11][3]=3;                   	//�Ƿ���������,1��ʾ����0��ʾ������  
      
      iArray[12]=new Array();
      iArray[12][0]="���л���";         		//����
      iArray[12][1]="0px";            		//�п�
      iArray[12][2]=200;            	        //�����ֵ
      iArray[12][3]=3;                   	//�Ƿ���������,1��ʾ����0��ʾ������    
       
       
      QueryLJAGetGrid = new MulLineEnter( "fm" , "QueryLJAGetGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      QueryLJAGetGrid.mulLineCount = 2;   
      QueryLJAGetGrid.displayTitle = 1;
      QueryLJAGetGrid.hiddenPlus = 1;
      QueryLJAGetGrid.hiddenSubtraction = 1;
      QueryLJAGetGrid.canSel = 1;
      QueryLJAGetGrid.loadMulLine(iArray);
      //QueryLJAGetGrid.selBoxEventFuncName = "GetRecord";  

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
