<%
//�������ƣ�TempFeeQueryByChqNoInit.jsp
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
<%@page import="com.sinosoft.lis.pubfun.*"%>                          
<%
   String ManageCom = tGI.ComCode;
%>
<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   
	// ��ѯ����
    document.all('TempFeeStatus1').value = '';
    document.all('StartDate').value = '';
    document.all('EndDate').value = '';
    document.all('Operator').value = '';
    document.all('Operator').value = '';
    //document.all('ManageCom').value = '<%=ManageCom%>';
    //document.all('PolicyCom').value = '<%=ManageCom%>';  
    document.all('ManageCom').value = '';
    document.all('PolicyCom').value = '';
  }
  catch(ex)
  {
    alert("��TempFeeQueryByChqNoInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��TempFeeQueryByChqNoInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();  
    initTempQueryByChqNoGrid();
  }
  catch(re)
  {
    alert("��TempFeeQueryByChqNoInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

 var TempQueryGrid ;
 
// ������Ϣ�б�ĳ�ʼ��
function initTempQueryByChqNoGrid()
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
      iArray[1][0]="���ѽ��";          		//����
      iArray[1][1]="80px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      
      
      
      iArray[2]=new Array();
      iArray[2][0]="�ݽ����վݺ�";   		//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            		//�����ֵ
      iArray[2][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��Ӧ����";         	//����
      iArray[3][1]="160px";              	//�п�
      iArray[3][2]=200;            	        //�����ֵ
      iArray[3][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="���ѻ���";		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=60;            		//�����ֵ
      iArray[4][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�������";		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=60;            		//�����ֵ
      iArray[5][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="Ͷ��������";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=200;            		//�����ֵ
      iArray[6][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="���ѷ�ʽ";         		//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=200;            	        //�����ֵ
      iArray[7][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="Ʊ�ݺ�";         		//����
      iArray[8][1]="120px";            		//�п�
      iArray[8][2]=200;            	        //�����ֵ
      iArray[8][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="֧Ʊ����";         		//����
      iArray[9][1]="100px";            		//�п�
      iArray[9][2]=200;            	        //�����ֵ
      iArray[9][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="��������";         		//����
      iArray[10][1]="100px";            		//�п�
      iArray[10][2]=200;            	        //�����ֵ
      iArray[10][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[11]=new Array();
      iArray[11][0]="��������";         		//����
      iArray[11][1]="100px";            		//�п�
      iArray[11][2]=200;            	        //�����ֵ
      iArray[11][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[12]=new Array();
      iArray[12][0]="���б���";         	//����
      iArray[12][1]="100px";            		//�п�
      iArray[12][2]=200;            	        //�����ֵ
      iArray[12][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[13]=new Array();
      iArray[13][0]="�����˺�";         	//����
      iArray[13][1]="80px";              	//�п�
      iArray[13][2]=200;            	        //�����ֵ
      iArray[13][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[14]=new Array();
      iArray[14][0]="�����˻���";         	//����
      iArray[14][1]="100px";              	//�п�
      iArray[14][2]=200;            	        //�����ֵ
      iArray[14][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[15]=new Array();
      iArray[15][0]="�տ�����";         	//����
      iArray[15][1]="100px";              	//�п�
      iArray[15][2]=200;            	        //�����ֵ
      iArray[15][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������

      
      TempQueryByChqNoGrid = new MulLineEnter( "fm" , "TempQueryByChqNoGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TempQueryByChqNoGrid.mulLineCount = 0;   
      TempQueryByChqNoGrid.displayTitle = 1;
      TempQueryByChqNoGrid.hiddenPlus = 1;
      TempQueryByChqNoGrid.hiddenSubtraction = 1;
//      TempQueryByChqNoGrid.locked = 1;
//      TempQueryByChqNoGrid.canSel = 1;
      TempQueryByChqNoGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
