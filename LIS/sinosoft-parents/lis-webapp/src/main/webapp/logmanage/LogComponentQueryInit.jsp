<%
//�������ƣ�LogComponentInit.jsp
//�����ܣ�
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
    document.all('SubjectID').value = '';
    document.all('ItemID').value = '';
    document.all('KeyNO').value = '';
  }
  catch(ex)
  {
    alert("��LogComponentInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
    initInpBox();
    initLogStateGrid();
    initLogTrackGrid();
}

function initLogStateGrid()
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
		iArray[1][0]="��־���к�";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[1][1]="120px";            		//�п�
		iArray[1][2]=10;            			//�����ֵ
		iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[2]=new Array();
		iArray[2][0]="��־����";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[2][1]="80px";            		//�п�
		iArray[2][2]=10;            			//�����ֵ
		iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
		iArray[3]=new Array();
		iArray[3][0]="���Ƶ�ID";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[3][1]="80px";            		//�п�
		iArray[3][2]=10;            			//�����ֵ
		iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[4]=new Array();
		iArray[4][0]="�ؼ���������";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[4][1]="80px";            		//�п�
		iArray[4][2]=10;            			//�����ֵ
		iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[5]=new Array();
		iArray[5][0]="�ؼ�����";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[5][1]="80px";            		//�п�
		iArray[5][2]=10;            			//�����ֵ
		iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[6]=new Array();
		iArray[6][0]="ҵ���ܱ���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[6][1]="80px";            		//�п�
		iArray[6][2]=10;            			//�����ֵ
		iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[7]=new Array();
		iArray[7][0]="ҵ��������";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[7][1]="100px";            		//�п�
		iArray[7][2]=10;            			//�����ֵ
		iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������    
		
		iArray[8]=new Array();                                                    
		iArray[8][0]="״̬";         			//����������Ϊ˳��ţ����������壬���Ҳ�
		iArray[8][1]="50px";            		//�п�                                
		iArray[8][2]=10;            			//�����ֵ                              
		iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
		
		iArray[9]=new Array();                                                    
		iArray[9][0]="��ע";         			//����������Ϊ˳��ţ����������壬���Ҳ�
		iArray[9][1]="100px";            		//�п�                                
		iArray[9][2]=10;            			//�����ֵ                              
		iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
		
		iArray[10]=new Array();                                                    
		iArray[10][0]="�޸�����";         			//����������Ϊ˳��ţ����������壬���Ҳ�
		iArray[10][1]="70px";            		//�п�                                
		iArray[10][2]=10;            			//�����ֵ                              
		iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
		
		iArray[11]=new Array();                                                    
		iArray[11][0]="�޸�ʱ��";         			//����������Ϊ˳��ţ����������壬���Ҳ�
		iArray[11][1]="70px";            		//�п�                                
		iArray[11][2]=10;            			//�����ֵ                              
		iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
		LogStateGrid = new MulLineEnter( "fm" , "LogStateGrid" ); 
		//��Щ���Ա�����loadMulLineǰ
		
		LogStateGrid.mulLineCount = 5;
		LogStateGrid.displayTitle = 1;
		LogStateGrid.hiddenPlus = 1;
		 //  CollectivityGrid.locked = 0;
		LogStateGrid.hiddenSubtraction = 1;
		//LogStateGrid.canSel=1;
		//LogSubjectGrid.selBoxEventFuncName = "ShowLog";
		LogStateGrid.loadMulLine(iArray);

	}
	catch(ex)
	{
		alert(ex);
	}
}

function initLogTrackGrid()
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
		iArray[1][0]="��־���к�";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[1][1]="120px";            		//�п�
		iArray[1][2]=10;            			//�����ֵ
		iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[2]=new Array();
		iArray[2][0]="��־����";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[2][1]="80px";            		//�п�
		iArray[2][2]=10;            			//�����ֵ
		iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
		iArray[3]=new Array();
		iArray[3][0]="���Ƶ�ID";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[3][1]="80px";            		//�п�
		iArray[3][2]=10;            			//�����ֵ
		iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[4]=new Array();
		iArray[4][0]="��������";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[4][1]="80px";            		//�п�
		iArray[4][2]=10;            			//�����ֵ
		iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[5]=new Array();
		iArray[5][0]="�ؼ�����";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[5][1]="80px";            		//�п�
		iArray[5][2]=10;            			//�����ֵ
		iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[6]=new Array();
		iArray[6][0]="ҵ���ܱ���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[6][1]="80px";            		//�п�
		iArray[6][2]=10;            			//�����ֵ
		iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[7]=new Array();
		iArray[7][0]="ҵ��������";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[7][1]="100px";            		//�п�
		iArray[7][2]=10;            			//�����ֵ
		iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������    
		
		iArray[8]=new Array();                                                    
		iArray[8][0]="������Ϣ";         			//����������Ϊ˳��ţ����������壬���Ҳ�
		iArray[8][1]="50px";            		//�п�                                
		iArray[8][2]=10;            			//�����ֵ                              
		iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
		
		iArray[9]=new Array();                                                    
		iArray[9][0]="��ע";         			//����������Ϊ˳��ţ����������壬���Ҳ�
		iArray[9][1]="100px";            		//�п�                                
		iArray[9][2]=10;            			//�����ֵ                              
		iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
		
		iArray[10]=new Array();                                                    
		iArray[10][0]="��������";         			//����������Ϊ˳��ţ����������壬���Ҳ�
		iArray[10][1]="70px";            		//�п�                                
		iArray[10][2]=10;            			//�����ֵ                              
		iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
		
		iArray[11]=new Array();                                                    
		iArray[11][0]="����ʱ��";         			//����������Ϊ˳��ţ����������壬���Ҳ�
		iArray[11][1]="70px";            		//�п�                                
		iArray[11][2]=10;            			//�����ֵ                              
		iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      
		LogTrackGrid = new MulLineEnter( "fm" , "LogTrackGrid" ); 
		//��Щ���Ա�����loadMulLineǰ
		
		LogTrackGrid.mulLineCount = 5;
		LogTrackGrid.displayTitle = 1;
		LogTrackGrid.hiddenPlus = 1;
		 //  CollectivityGrid.locked = 0;
		LogTrackGrid.hiddenSubtraction = 1;
		//LogTrackGrid.canSel=1;
		//LogItemGrid.selBoxEventFuncName = "ShowItem";
		LogTrackGrid.loadMulLine(iArray);

	}
	catch(ex)
	{
		alert(ex);
	}
}

</script>
