<%
//�������ƣ�LFGetNoticeInit.jsp
//�����ܣ�
//�������ڣ�2002-08-16 15:39:06
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>


<script language="JavaScript">
function initInpBox()
{ 
  try
  {     


  }
  catch(ex)
  {
    alert("��LFGetNoticeInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
    initPolGrid();   
  }
  catch(re)
  {
    alert("LFGetNoticeInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
 var PolGrid;          //����Ϊȫ�ֱ������ṩ��displayMultilineʹ��
// Ͷ������Ϣ�б�ĳ�ʼ��
function initPolGrid()
{                               
  var iArray = new Array();
  try
  {
	  iArray[0]=new Array();
	  iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	  iArray[0][1]="30px";            	//�п�
	  iArray[0][2]=10;            			//�����ֵ
	  iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[1]=new Array();
	  iArray[1][0]="��ȡ�嵥��";   		//����
	  iArray[1][1]="80px";            	//�п�
	  iArray[1][2]=100;            			//�����ֵ
	  iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[2]=new Array();
	  iArray[2][0]="�ŵ���";        		//����
	  iArray[2][1]="100px";            	//�п�
	  iArray[2][2]=100;            			//�����ֵ
	  iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[3]=new Array();
	  iArray[3][0]="Ͷ����λ����";       	//����
	  iArray[3][1]="100px";            	//�п�
	  iArray[3][2]=100;            			//�����ֵ
	  iArray[3][3]=0; 
	
	  iArray[4]=new Array();
	  iArray[4][0]="��ˮ��";      //����
	  iArray[4][1]="0px";            	//�п�
	  iArray[4][2]=100;            			//�����ֵ
	  iArray[4][3]=3; 									//�Ƿ���������,1��ʾ����0��ʾ������
      
    iArray[5]=new Array();
	  iArray[5][0]="��ȡ������";       	//����
	  iArray[5][1]="100px";            	//�п�
	  iArray[5][2]=100;            			//�����ֵ
	  iArray[5][3]=0;  
	  	        
    iArray[6]=new Array();
	  iArray[6][0]="����Ա";      //����
	  iArray[6][1]="60px";            	//�п�
	  iArray[6][2]=60;            			//�����ֵ
	  iArray[6][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
	  
    PolGrid = new MulLineEnter( "document" , "PolGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    //PolGrid.mulLineCount = 10;   
    PolGrid.displayTitle = 1;
    PolGrid.canSel = 1;
    PolGrid.hiddenPlus=1;
    PolGrid.hiddenSubtraction=1;
    PolGrid.selBoxEventFuncName ="reportDetailClick";
    PolGrid.loadMulLine(iArray);  
    }
    catch(ex)
    {
      alert("LFGetNoticeInit.jsp-->initPolGrid�����з����쳣:"+ex);
    }
}


function initLFGetGrid()
{                               
  var iArray = new Array();
  try
  {
	  iArray[0]=new Array();
	  iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	  iArray[0][1]="30px";            	//�п�
	  iArray[0][2]=10;            			//�����ֵ
	  iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[1]=new Array();
	  iArray[1][0]="���˱�����";   		//����
	  iArray[1][1]="120px";            	//�п�
	  iArray[1][2]=100;            			//�����ֵ
	  iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[2]=new Array();
	  iArray[2][0]="��������";        		//����
	  iArray[2][1]="80px";            	//�п�
	  iArray[2][2]=100;            			//�����ֵ
	  iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[3]=new Array();
	  iArray[3][0]="��ȡ��ʽ";       	//����
	  iArray[3][1]="60px";            	//�п�
	  iArray[3][2]=100;            			//�����ֵ
	  iArray[3][3]=0; 
	
	  iArray[4]=new Array();
	  iArray[4][0]="��ȡ����";      //����
	  iArray[4][1]="80px";            	//�п�
	  iArray[4][2]=100;            			//�����ֵ
	  iArray[4][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
      
    iArray[5]=new Array();
	  iArray[5][0]="Ӧ������";       	//����
	  iArray[5][1]="80px";            	//�п�
	  iArray[5][2]=100;            			//�����ֵ
	  iArray[5][3]=0;  
	  	        
    iArray[6]=new Array();
	  iArray[6][0]="����Ӧ�����";      //����
	  iArray[6][1]="60px";            	//�п�
	  iArray[6][2]=60;            			//�����ֵ
	  iArray[6][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[7]=new Array();
	  iArray[7][0]="֧����ʽ";      //����
	  iArray[7][1]="60px";            	//�п�
	  iArray[7][2]=60;            			//�����ֵ
	  iArray[7][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[8]=new Array();                                                 
  	iArray[8][0]="��������";      //����                                  
  	iArray[8][1]="120px";            	//�п�                               
  	iArray[8][2]=60;            			//�����ֵ                          
  	iArray[8][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������ 
  	
  	iArray[9]=new Array();                                                 
  	iArray[9][0]="����";      //����                                
  	iArray[9][1]="60px";            	//�п�                              
  	iArray[9][2]=60;            			//�����ֵ                              
  	iArray[9][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������  
     
    iArray[10]=new Array();                                                                                                                                                                           
    iArray[10][0]="�˺�";      //���� 
    iArray[10][1]="60px";      //�п�     
    iArray[10][2]=60;         //�����ֵ 
    iArray[10][3]=0; 				//�Ƿ���������,1��ʾ����0��ʾ������  
     
     
    LFGetGrid = new MulLineEnter( "document" , "LFGetGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    //PolGrid.mulLineCount = 10;   
    LFGetGrid.displayTitle = 1;
    //LFGetGrid.canSel = 1;
    LFGetGrid.hiddenPlus=1;
    LFGetGrid.hiddenSubtraction=1;
    //LFGetGrid.selBoxEventFuncName ="reportDetailClick";
    LFGetGrid.loadMulLine(iArray);                                                                                                                             }
    catch(ex)
    {
      alert("LFGetNoticeInit.jsp-->initPolGrid�����з����쳣:"+ex);
    }
}
</script>
