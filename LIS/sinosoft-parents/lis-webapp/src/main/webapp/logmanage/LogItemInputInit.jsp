<%
//�������ƣ�LogComponentInit.jsp
//�����ܣ�
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
     String tSubjectID = request.getParameter("SubjectID");
%>                            

<script language="JavaScript">

function initInpBox()
{ 
  try
  {          
    /*document.all('ItemID').value = '';
    document.all('ItemDes').value = '';
    document.all('KeyType').value = '';
    document.all('Grammar').value = '';
    document.all('ClassName').value = '';
    document.all('Func').value = '';                         
   // document.all('ItemSwitch').value = 'Y';
   // document.all('ItemSwitchName').value = '��';
    document.all('Remark').value = '';*/
    document.all('SubIDForItem').value = '<%= tSubjectID %>';
  }
  catch(ex)
  {
    alert("��LogComponentInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
    initInpBox();
    initLogItemGrid();
    ItemQuery();
}

function initLogItemGrid()
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
		iArray[1][0]="���Ƶ�ID";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[1][1]="80px";            		//�п�
		iArray[1][2]=10;            			//�����ֵ
		iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[2]=new Array();
		iArray[2][0]="��־��ص�ID";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[2][1]="100px";            		//�п�
		iArray[2][2]=10;            			//�����ֵ
		iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
		iArray[3]=new Array();
		iArray[3][0]="��־��ص�����";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[3][1]="100px";            		//�п�
		iArray[3][2]=10;            			//�����ֵ
		iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

		
		
		iArray[4]=new Array();
		iArray[4][0]="���Ƶ�ؼ���������";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[4][1]="80px";            		//�п�
		iArray[4][2]=10;            			//�����ֵ
		iArray[4][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
		iArray[4][4]="logkeytype";
		iArray[4][17]="1";
		
		iArray[5]=new Array();                                                    
		iArray[5][0]="��ؿ���";         			//����������Ϊ˳��ţ����������壬���Ҳ�
		iArray[5][1]="50px";            		//�п�                                
		iArray[5][2]=10;            			//�����ֵ                              
		iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������   
		iArray[5][10]="ItemSwitch";
		iArray[5][11]="0|^Y|��^N|��";
		
	

      
		LogItemGrid = new MulLineEnter( "fm" , "LogItemGrid" ); 
		//��Щ���Ա�����loadMulLineǰ
		
		LogItemGrid.mulLineCount = 0;
		LogItemGrid.displayTitle = 1;
		LogItemGrid.hiddenPlus = 1;
		 //  CollectivityGrid.locked = 0;
		LogItemGrid.hiddenSubtraction = 1;
		LogItemGrid.canSel=0;
		//LogItemGrid.selBoxEventFuncName = "ShowItem";
		LogItemGrid.loadMulLine(iArray);

	}
	catch(ex)
	{
		alert(ex);
	}
}

</script>
