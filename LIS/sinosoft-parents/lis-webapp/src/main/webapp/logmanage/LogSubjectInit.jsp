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
    document.all('LogSubjectID').value = '';
    document.all('LogSubjectDes').value = '';
    document.all('LogDept').value = '';
    document.all('LogServiceModule').value = '';
    document.all('TaskCode').value = '';
    document.all('LogType').value = '';                         
    document.all('Switch').value = 'Y';
    document.all('SwitchName').value = '��';
  }
  catch(ex)
  {
    alert("��LogComponentInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}


function initForm()
{
    initInpBox();
    initLogSubjectGrid();
    LogQuery();
}

function initLogSubjectGrid()
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
		iArray[1][0]="��־����";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[1][1]="80px";            		//�п�
		iArray[1][2]=10;            			//�����ֵ
		iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[2]=new Array();
		iArray[2][0]="��������";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[2][1]="100px";            		//�п�
		iArray[2][2]=10;            			//�����ֵ
		iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[3]=new Array();
		iArray[3][0]="��ز���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[3][1]="80px";            		//�п�
		iArray[3][2]=10;            			//�����ֵ
		iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[4]=new Array();
		iArray[4][0]="����ҵ��ģ��";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[4][1]="80px";            		//�п�
		iArray[4][2]=10;            			//�����ֵ
		iArray[4][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
		iArray[4][4]="logservicemodule";   
		
		iArray[5]=new Array();
		iArray[5][0]="ҵ���ܱ���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[5][1]="80px";            		//�п�
		iArray[5][2]=10;            			//�����ֵ
		iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
		iArray[5][4]="taskcode";
		
		iArray[6]=new Array();
		iArray[6][0]="ҵ��������";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[6][1]="120px";            		//�п�
		iArray[6][2]=10;            			//�����ֵ
		iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[7]=new Array();
		iArray[7][0]="��־����";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[7][1]="80px";            		//�п�
		iArray[7][2]=10;            			//�����ֵ
		iArray[7][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������    
		iArray[7][4]="logtype";
		iArray[7][17]="1";
		
		iArray[8]=new Array();                                                    
		iArray[8][0]="��ؿ���";         			//����������Ϊ˳��ţ����������壬���Ҳ�
		iArray[8][1]="50px";            		//�п�                                
		iArray[8][2]=10;            			//�����ֵ                              
		iArray[8][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������   
		iArray[8][10]="Switch";
		iArray[8][11]="0|^1|��^0|��";

      
		LogSubjectGrid = new MulLineEnter( "fm" , "LogSubjectGrid" ); 
		//��Щ���Ա�����loadMulLineǰ
		
		LogSubjectGrid.mulLineCount = 0;
		LogSubjectGrid.displayTitle = 1;
		LogSubjectGrid.hiddenPlus = 1;
		 //  CollectivityGrid.locked = 0;
		LogSubjectGrid.hiddenSubtraction = 1;
		LogSubjectGrid.canSel=1;
		LogSubjectGrid.selBoxEventFuncName = "ShowLog";
		LogSubjectGrid.loadMulLine(iArray);

	}
	catch(ex)
	{
		alert(ex);
	}
}


</script>
