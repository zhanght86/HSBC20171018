<%
//�������ƣ�ChannelQueryInit.jsp
//�����ܣ�������ʷ��ѯ��ʾҳ��
//�������ڣ�2005-10-27
//������  �������
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
                           
<script language="JavaScript">


//������ʷ��Ϣ�б�ĳ�ʼ��
function initLAwageForm()
{
  try
  { 
    initLAwageGrid();
    LAwageClick();
    
  }
  catch(re)
  {
    alert("initLAwageForm�����з����쳣:��ʼ���������! "+re.message);
  }
}

// ҵ��Ա������Ϣ�б�ĳ�ʼ��
function initLAAgentForm()
{
  try
  {
    initLAAgentGrid();
    LAAgentClick();
  }
  catch(re)
  {
    alert("initLAAgentForm�����з����쳣:��ʼ���������!"+re.message);
  }
}

// ҵ��Ա��ʷ��Ϣ�б�ĳ�ʼ��
function initLAAgentBForm()
{
  try
  {
    initLAAgentBGrid();
    LAAgentBClick();
  }
  catch(re)
  {
    alert("initLAAgentBForm�����з����쳣:��ʼ���������!"+re.message);
  }
}

// ������ʷ��ѯ�б�ĳ�ʼ��
function initLAAssessAccessoryForm()
{
  try
  {
    initLAAssessAccessoryGrid();
    LAAssessAccessoryClick();
  }
  catch(re)
  {
    alert("initLAAssessAccessoryForm�����з����쳣:��ʼ���������!"+re.message);
  }
}

// ������ѪԵ��ϵ��ѯ�б�ĳ�ʼ��
function initLARearRelationForm()
{
  try
  {
    initLARearRelationGrid();
    LARearRelationClick();
  }
  catch(re)
  {
    alert("initLARearRelationForm�����з����쳣:��ʼ���������!"+re.message);
  }
}

// ������ʷ��ѯ�б�ĳ�ʼ��
function initLAwageWelareForm()
{
  try
  {
    initLAwageWelareGrid();
    LAwageWelareClick();
  }
  catch(re)
  {
    alert("initLAwageWelareForm�����з����쳣:��ʼ���������!"+re.message);
  }
}

// ��֯��ϵ�б�ĳ�ʼ��
function initAgentForm()
{
  try
  {
    initAgentGrid();
    AgentClick();
  }
  catch(re)
  {
    alert("initAgentForm�����з����쳣:��ʼ���������!"+re.message);
  }
}

var LAwageGrid; 
// ������ʷ��Ϣ�б�ĳ�ʼ��
function initLAwageGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[1]=new Array();
      iArray[1][0]="ָ��������";         		//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="�����˱���";         		//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
           
      iArray[3]=new Array();
      iArray[3][0]="����������";         		//����
      iArray[3][1]="120px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="���������";         		//����
      iArray[4][1]="120px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="ʵ��(˰��н��)";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�������";         		//����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      

      iArray[7]=new Array();
      iArray[7][0]="Ӧ��(˰ǰн��)";         		//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0; 
        
      
      LAwageGrid = new MulLineEnter( "fm" , "LAwageGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LAwageGrid.mulLineCount = 0;   
      LAwageGrid.displayTitle = 1;
      LAwageGrid.locked = 1;
      LAwageGrid.canSel = 0;
 
      LAwageGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      LAwageGrid.hiddenSubtraction=1;
      LAwageGrid.loadMulLine(iArray);
      //��Щ����������loadMulLine����
      }
      catch(ex)
      {
        alert(ex);
      }
}

//ҵ��Ա������Ϣ�б�ĳ�ʼ��
var LAAgentGrid;
function initLAAgentGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[1]=new Array();
      iArray[1][0]="�����˱���";         		//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="������չҵ��������";         		//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
           
      iArray[3]=new Array();
      iArray[3][0]="����";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�Ա�";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�������";         		//����
      iArray[5][1]="90px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�����˼���";         		//����
      iArray[6][1]="90px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      

      iArray[7]=new Array();
      iArray[7][0]="�ֻ�";         		//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0; 
        
      iArray[8]=new Array();
      iArray[8][0]="E_Mail";         		//����
      iArray[8][1]="100px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  
      
      iArray[9]=new Array();
      iArray[9][0]="���֤����";         		//����
      iArray[9][1]="100px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  
      
      LAAgentGrid = new MulLineEnter( "fm1" , "LAAgentGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LAAgentGrid.mulLineCount = 0;   
      LAAgentGrid.displayTitle = 1;
      LAAgentGrid.locked = 1;
      LAAgentGrid.canSel = 0;
 
      LAAgentGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      LAAgentGrid.hiddenSubtraction=1;
      LAAgentGrid.loadMulLine(iArray);
      //��Щ����������loadMulLine����
      }
      catch(ex)
      {
        alert(ex);
      }
}

//ҵ��Ա������Ϣ�б�ĳ�ʼ��
var LAAgentBGrid;
function initLAAgentBGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[1]=new Array();
      iArray[1][0]="ת������";         		//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="չҵ����";         		//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
           
      iArray[3]=new Array();
      iArray[3][0]="�����˱���";         		//����
      iArray[3][1]="120px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�´����˱���";         		//����
      iArray[4][1]="120px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="����";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�Ա�";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      

      iArray[7]=new Array();
      iArray[7][0]="������չҵ��������";         		//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0; 
        
      iArray[8]=new Array();
      iArray[8][0]="�ֻ�";         		//����
      iArray[8][1]="100px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  
      
      iArray[9]=new Array();
      iArray[9][0]="���֤����";         		//����
      iArray[9][1]="100px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  
      
      LAAgentBGrid = new MulLineEnter( "fm" , "LAAgentBGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LAAgentBGrid.mulLineCount = 0;   
      LAAgentBGrid.displayTitle = 1;
      LAAgentBGrid.locked = 1;
      LAAgentBGrid.canSel = 0;
 
      LAAgentBGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      LAAgentBGrid.hiddenSubtraction=1;
      LAAgentBGrid.loadMulLine(iArray);
      //��Щ����������loadMulLine����
      }
      catch(ex)
      {
        alert(ex);
      }
}

//ҵ��Ա������Ϣ�б�ĳ�ʼ��
var LAAssessAccessoryGrid;
function initLAAssessAccessoryGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[1]=new Array();
      iArray[1][0]="ָ��������";         		//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="���˶������";         		//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
           
      iArray[3]=new Array();
      iArray[3][0]="����";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�Ա�";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�������";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="ԭ�����˼���";         		//����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      

      iArray[7]=new Array();
      iArray[7][0]="�´����˼���";         		//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0; 
        
      
      LAAssessAccessoryGrid = new MulLineEnter( "fm" , "LAAssessAccessoryGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LAAssessAccessoryGrid.mulLineCount = 0;   
      LAAssessAccessoryGrid.displayTitle = 1;
      LAAssessAccessoryGrid.locked = 1;
      LAAssessAccessoryGrid.canSel = 0;
 
      LAAssessAccessoryGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      LAAssessAccessoryGrid.hiddenSubtraction=1;
      LAAssessAccessoryGrid.loadMulLine(iArray);
      //��Щ����������loadMulLine����
      }
      catch(ex)
      {
        alert(ex);
      }
}


//ҵ��Ա������Ϣ�б�ĳ�ʼ��
var LARearRelationGrid;
function initLARearRelationGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[1]=new Array();
      iArray[1][0]="���ɼ���";         		//����
      iArray[1][1]="80px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="���ɴ���Ŀ";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
           
      iArray[3]=new Array();
      iArray[3][0]="�������˴���";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="������������";         		//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�����˴���";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="����������";         		//����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      

      iArray[7]=new Array();
      iArray[7][0]="�����ɻ���";         		//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0; 
        
      iArray[8]=new Array();
      iArray[8][0]="״̬";         		//����
      iArray[8][1]="100px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=0; 
       
      LARearRelationGrid = new MulLineEnter( "fm" , "LARearRelationGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LARearRelationGrid.mulLineCount = 0;   
      LARearRelationGrid.displayTitle = 1;
      LARearRelationGrid.locked = 1;
      LARearRelationGrid.canSel = 0;
 
      LARearRelationGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      LARearRelationGrid.hiddenSubtraction=1;
      LARearRelationGrid.loadMulLine(iArray);
      //��Щ����������loadMulLine����
      }
      catch(ex)
      {
        alert(ex);
      }
}


// ������ʷ��Ϣ�б�ĳ�ʼ��
var LAwageWelareGrid; 

function initLAwageWelareGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[1]=new Array();
      iArray[1][0]="ָ��������";         		//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="�����˱���";         		//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
           
      iArray[3]=new Array();
      iArray[3][0]="���������";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="����";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="���ϱ���";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="ʧҵ����";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      

      iArray[7]=new Array();
      iArray[7][0]="ҽ�Ʊ���";         		//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0; 
        
      iArray[8]=new Array();
      iArray[8][0]="ס��������";         		//����
      iArray[8][1]="60px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  
      
      iArray[9]=new Array();
      iArray[9][0]="��������";         		//����
      iArray[9][1]="60px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  
      
      iArray[10]=new Array();
      iArray[10][0]="���ݽ�";         		//����
      iArray[10][1]="60px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  
      
      
      LAwageWelareGrid = new MulLineEnter( "fm" , "LAwageWelareGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LAwageWelareGrid.mulLineCount = 0;   
      LAwageWelareGrid.displayTitle = 1;
      LAwageWelareGrid.locked = 1;
      LAwageWelareGrid.canSel = 0;
 
      LAwageWelareGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      LAwageWelareGrid.hiddenSubtraction=1;
      LAwageWelareGrid.loadMulLine(iArray);
      //��Щ����������loadMulLine����
      }
      catch(ex)
      {
        alert(ex);
      }
}

var AgentGrid; 
// ��֯��ϵ�б�ĳ�ʼ��
function initAgentGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[1]=new Array();
      iArray[1][0]="���ۻ�������";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="90px";            		//�п�
      iArray[1][2]=10;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="Ӫҵ��";         		//����
      iArray[2][1]="200px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����������";         		//����
      iArray[3][1]="70px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�����˱���";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="ְ��";         		//����
      iArray[5][1]="50px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="��˾ʱ��";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      AgentGrid = new MulLineEnter( "fm" , "AgentGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      AgentGrid.mulLineCount = 0;   
      AgentGrid.displayTitle = 1;
      AgentGrid.locked = 1;
      AgentGrid.canSel = 0;
      AgentGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      AgentGrid.hiddenSubtraction=1;
      AgentGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>     
      
      
      
      
      
      
      
      
      


      
