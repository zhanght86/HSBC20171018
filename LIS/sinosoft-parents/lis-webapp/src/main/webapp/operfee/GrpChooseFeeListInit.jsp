<%
//�������ƣ�GrpChooseFeeListInit.jsp
//�����ܣ�
//�������ڣ�
//������  ��
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
    document.all('GrpPolNo').value = '';
  
  }
  catch(ex)
  {
    alert("��GrpChooseFeeListInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��GrpChooseFeeListInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        
//��ʼ��ҳ��
function initForm()
{
  try
  {   
    initInpBox();  
    initIndiDueQueryGrid();
  }
  catch(re)
  {
    alert("��GrpChooseFeeListInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initIndiDueQueryGrid()

  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="40px";            		//�п�
      iArray[0][2]=10;            		//�����ֵ
      iArray[0][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      iArray[1]=new Array();
      iArray[1][0]="�ɷ��վݺ���";   		//����
      iArray[1][1]="160px";            		//�п�
      iArray[1][2]=100;            		//�����ֵ
      iArray[1][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[2]=new Array();
      iArray[2][0]="���ֱ���";          		//����
      iArray[2][1]="80px";      	      		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=2;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      //iArray[2][4]="RiskCode";              	        //�Ƿ����ô���:null||""Ϊ������
      //iArray[2][5]="1|2";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      //iArray[2][9]="���ֱ���|code:RiskCode&NOTNULL";
      //iArray[2][18]=300;
      
      
      iArray[3]=new Array();
      iArray[3][0]="���屣������";   		//����
      iArray[3][1]="160px";            		//�п�
      iArray[3][2]=100;            		//�����ֵ
      iArray[3][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�ɷѽ��";		     //����
      iArray[4][1]="80px";            	 //�п�
      iArray[4][2]=60;            		//�����ֵ
      iArray[4][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�ɷ�����";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=200;            		//�����ֵ
      iArray[5][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="��������";         		//����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=200;            	        //�����ֵ
      iArray[6][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="�������";         		//����
      iArray[7][1]="120px";            		//�п�
      iArray[7][2]=200;            	        //�����ֵ
      iArray[7][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="�����˱���";         		//����
      iArray[8][1]="110px";            		//�п�
      iArray[8][2]=200;            	        //�����ֵ
      iArray[8][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������

     
      
      IndiDueQueryGrid = new MulLineEnter("fm","IndiDueQueryGrid"); 
      //��Щ���Ա�����loadMulLineǰ
      IndiDueQueryGrid.mulLineCount = 10;   
      IndiDueQueryGrid.displayTitle = 1;
      IndiDueQueryGrid.hiddenPlus = 1;
      IndiDueQueryGrid.hiddenSubtraction = 1;
//      IndiDueQueryGrid.locked = 1;
//      IndiDueQueryGrid.canSel = 1;
      IndiDueQueryGrid.loadMulLine(iArray);  
//      IndiDueQueryGrid.selBoxEventFuncName = "easyQueryAddClick";
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
