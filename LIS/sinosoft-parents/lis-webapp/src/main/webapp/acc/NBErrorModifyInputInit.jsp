<%
//�������ƣ�ClientConjoinQueryInit.jsp
//�����ܣ�
//�������ڣ�2002-08-19
//������  ��Kevin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
  	
  	document.all('QureyPrtNo').value = '';
    document.all('QureyContNo').value = '';      
    document.all('PolNo').value=''; 
    document.all('RiskCode').value='';                          
  }
  catch(ex)
  {
    alert("NBErrorModifyInput.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
    initPolGrid();
    initOldPlan();
    initNewPlan();
  }
  catch(re)
  {
    alert("NBErrorModifyInput.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


//������������Ϣ�б��ʼ��
function initPolGrid()
{
    var iArray = new Array();
    try 
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		        //�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���յ����ֺ���";         		//����
      iArray[1][1]="0px";            		        //�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="���ֱ���";         		//����
      iArray[2][1]="40px";            		        //�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      //iArray[2][4]="RiskCode";              			//�Ƿ���������,1��ʾ����0��ʾ������           
      
      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="80px";            		        //�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[4]=new Array();
      iArray[4][0]="�����ڼ�";         		//����
      iArray[4][1]="40px";            		        //�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[5]=new Array();
      iArray[5][0]="��������";         		//����
      iArray[5][1]="40px";            		        //�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[6]=new Array();
      iArray[6][0]="���ս��";         		//����
      iArray[6][1]="40px";            		        //�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="Ͷ������";         		//����
      iArray[7][1]="40px";            		        //�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="�ϼƱ���(Ԫ)";         		//����
      iArray[8][1]="60px";            		        //�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[9]=new Array();
      iArray[9][0]="ְҵ�ӷ�(Ԫ)";         		//����
      iArray[9][1]="0px";            		        //�п�
      iArray[9][2]=60;            			//�����ֵ
      iArray[9][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[10]=new Array();
      iArray[10][0]="����";         		//����
      iArray[10][1]="40px";            		        //�п�
      iArray[10][2]=60;            			//�����ֵ
      iArray[10][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      iArray[10][4]="Currency";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[10][9]="����|code:Currency";
      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      PolGrid.mulLineCount = 5;   
      PolGrid.displayTitle = 1;
      PolGrid.canSel =1;
      PolGrid. selBoxEventFuncName ="getPolDetail";
      PolGrid. hiddenPlus=1;
      PolGrid. hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);  
    }
    catch(ex) {
      alert(ex);
    }
}


//Ͷ�����ֵ�Ͷ�ʼƻ�
function initOldPlan()
{
    var iArray = new Array();

    try {
      iArray[0] = new Array();
      iArray[0][0] = "���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1] = "30px";            		//�п�
      iArray[0][2] = 10;            			//�����ֵ
      iArray[0][3] = 0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1] = new Array();
      iArray[1][0] = "Ͷ���ʻ���";    	        //����
      iArray[1][1] = "80px";            		//�п�
      iArray[1][2] = 100;            			//�����ֵ
      iArray[1][3] = 0;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
     // iArray[1][14]=document.all('PolNo').value;
  
      iArray[2] = new Array();
      iArray[2][0] = "Ͷ���ʻ�����";         		//����
      iArray[2][1] = "100px";            		//�п�
      iArray[2][2] = 60;            			//�����ֵ
      iArray[2][3] = 0;                   			//�Ƿ���������,1��ʾ����0��ʾ������
   //   iArray[2][4] ="findinsuaccno";
   //   iArray[2][15] ="PolNo";
   //   iArray[2][16] =document.all('PolNo').value;
   
   
      
      iArray[3] = new Array();
      iArray[3][0] = "Ͷ�ʱ�������";         		//����
      iArray[3][1] = "0px";            		//�п�
      iArray[3][2] = 60;            			//�����ֵ
      iArray[3][3] = 3;              			//�Ƿ���������,1��ʾ����0��ʾ������
     // iArray[3][4] = "fpayplancode";
   //    iArray[3][15] ="PolNo";
    //   iArray[3][16] =document.all('PolNo').value;

      iArray[4] = new Array();
      iArray[4][0] = "Ͷ�ʱ�������";         		//����
      iArray[4][1] = "0px";            		//�п�
      iArray[4][2] = 50;            			//�����ֵ
      iArray[4][3] = 3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5] = new Array();
      iArray[5][0] = "Ͷ�ʱ���";         		//����
      iArray[5][1] = "80px";            		//�п�
      iArray[5][2] = 50;            			//�����ֵ
      iArray[5][3] = 0;              			//�Ƿ���������,1��ʾ����0��ʾ������

 
      iArray[6] = new Array();
      iArray[6][0] = "�ɷѱ��";         		//����
      iArray[6][1] = "0px";            		//�п�
      iArray[6][2] = 100;            			//�����ֵ
      iArray[6][3] = 3;              			//�Ƿ���������,1��ʾ����0��ʾ������

        
      iArray[7] = new Array();
      iArray[7][0] = "����";         		//����
      iArray[7][1] = "80px";            		//�п�
      iArray[7][2] = 100;            			//�����ֵ
      iArray[7][3] = 0;


     
      /*iArray[8] = new Array();
      iArray[8][0] = "";         		//����
      iArray[8][1] = "80px";            		//�п�
      iArray[8][2] = 100;            			//�����ֵ
      iArray[8][3] = 1;*/
 
      OldPlanGrid = new MulLineEnter( "fm" , "OldPlanGrid" );
      //��Щ���Ա�����loadMulLineǰ
      OldPlanGrid.mulLineCount = 5;
     OldPlanGrid.displayTitle = 1;
     OldPlanGrid.hiddenPlus = 1;
      OldPlanGrid.hiddenSubtraction = 1;
      OldPlanGrid.loadMulLine(iArray);
    }
    catch(ex) {
      alert(ex);
    }
}


//Ͷ�����ֵ�Ͷ�ʼƻ�
function initNewPlan()
{
    var iArray = new Array();

    try {
      iArray[0] = new Array();
      iArray[0][0] = "���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1] = "30px";            		//�п�
      iArray[0][2] = 10;            			//�����ֵ
      iArray[0][3] = 0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1] = new Array();
      iArray[1][0] = "Ͷ���ʻ���";    	        //����
      iArray[1][1] = "80px";            		//�п�
      iArray[1][2] = 100;            			//�����ֵ
      iArray[1][3] = 0;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
     // iArray[1][14]=document.all('PolNo').value;
  
      iArray[2] = new Array();
      iArray[2][0] = "Ͷ���ʻ�����";         		//����
      iArray[2][1] = "100px";            		//�п�
      iArray[2][2] = 60;            			//�����ֵ
      iArray[2][3] = 0;                   			//�Ƿ���������,1��ʾ����0��ʾ������
   //   iArray[2][4] ="findinsuaccno";
   //   iArray[2][15] ="PolNo";
   //   iArray[2][16] =document.all('PolNo').value;
   
   
      
      iArray[3] = new Array();
      iArray[3][0] = "Ͷ�ʱ�������";         		//����
      iArray[3][1] = "0px";            		//�п�
      iArray[3][2] = 60;            			//�����ֵ
      iArray[3][3] = 3;              			//�Ƿ���������,1��ʾ����0��ʾ������
     // iArray[3][4] = "fpayplancode";
   //    iArray[3][15] ="PolNo";
    //   iArray[3][16] =document.all('PolNo').value;

      iArray[4] = new Array();
      iArray[4][0] = "Ͷ�ʱ�������";         		//����
      iArray[4][1] = "0px";            		//�п�
      iArray[4][2] = 50;            			//�����ֵ
      iArray[4][3] = 3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5] = new Array();
      iArray[5][0] = "Ͷ�ʱ���";         		//����
      iArray[5][1] = "80px";            		//�п�
      iArray[5][2] = 50;            			//�����ֵ
      iArray[5][3] = 1;              			//�Ƿ���������,1��ʾ����0��ʾ������

 
      iArray[6] = new Array();
      iArray[6][0] = "�ɷѱ��";         		//����
      iArray[6][1] = "0px";            		//�п�
      iArray[6][2] = 100;            			//�����ֵ
      iArray[6][3] = 3;              			//�Ƿ���������,1��ʾ����0��ʾ������

        
      iArray[7] = new Array();
      iArray[7][0] = "����";         		//����
      iArray[7][1] = "80px";            		//�п�
      iArray[7][2] = 100;            			//�����ֵ
      iArray[7][3] = 0;


     
      /*iArray[8] = new Array();
      iArray[8][0] = "";         		//����
      iArray[8][1] = "80px";            		//�п�
      iArray[8][2] = 100;            			//�����ֵ
      iArray[8][3] = 1;*/
 
      NewPlanGrid = new MulLineEnter( "fm" , "NewPlanGrid" );
      //��Щ���Ա�����loadMulLineǰ
      NewPlanGrid.mulLineCount = 5;
     NewPlanGrid.displayTitle = 1;
     NewPlanGrid.hiddenPlus = 1;
      NewPlanGrid.hiddenSubtraction = 1;
      NewPlanGrid.loadMulLine(iArray);
    }
    catch(ex) {
      alert(ex);
    }
}

</script>
