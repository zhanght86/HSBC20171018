<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">
	function initForm()
	{
		  try
		  {
			initPersonAgeGrid();
		  }
		  catch(re)
		  {
		    alert("GroupPolInputInit.jsp-->InitForm1�����з����쳣:��ʼ���������!"+re);
		  }
	}
	function initPersonAgeGrid()
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
			iArray[1][0]="��ʼ����";         		//����
			iArray[1][1]="60px";            		//�п�
			iArray[1][2]=3;            			//�����ֵ
			iArray[1][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
			
			iArray[2]=new Array();
			iArray[2][0]="��ֹ����";         		//����
			iArray[2][1]="60px";            		//�п�
			iArray[2][2]=3;            			//�����ֵ
			iArray[2][3]=1;
	
			iArray[3]=new Array();
			iArray[3][0]="��ְ����";         		//����
			iArray[3][1]="60px";            		//�п�
			iArray[3][2]=9;            			//�����ֵ
			iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
			
			iArray[4]=new Array();
			iArray[4][0]="��ְŮ��";         		//����
			iArray[4][1]="60px";            		//�п�
			iArray[4][2]=9;            			//�����ֵ
			iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
			
			iArray[5]=new Array(); 
			iArray[5][0]="��������";   
			iArray[5][1]="60px";   
			iArray[5][2]=9;        
			iArray[5][3]=1;
			
			iArray[6]=new Array(); 
			iArray[6][0]="����Ů��";   
			iArray[6][1]="60px";   
			iArray[6][2]=9;        
			iArray[6][3]=1;
			
			iArray[7]=new Array(); 
			iArray[7][0]="��ż����";   
			iArray[7][1]="60px";   
			iArray[7][2]=9;        
			iArray[7][3]=1;
			
			iArray[8]=new Array(); 
			iArray[8][0]="��żŮ��";   
			iArray[8][1]="60px";   
			iArray[8][2]=9;        
			iArray[8][3]=1;
			
			iArray[9]=new Array(); 
			iArray[9][0]="��Ů����";   
			iArray[9][1]="60px";   
			iArray[9][2]=9;        
			iArray[9][3]=1;
			
			iArray[10]=new Array(); 
			iArray[10][0]="��ŮŮ��";   
			iArray[10][1]="60px";   
			iArray[10][2]=9;        
			iArray[10][3]=1;
			
			iArray[11]=new Array(); 
			iArray[11][0]="��������";   
			iArray[11][1]="60px";   
			iArray[11][2]=9;        
			iArray[11][3]=1;
			
			iArray[12]=new Array(); 
			iArray[12][0]="����Ů��";   
			iArray[12][1]="60px";   
			iArray[12][2]=9;        
			iArray[12][3]=1;
			
			PersonAgeGrid = new MulLineEnter( "fm" , "PersonAgeGrid" ); 
			//��Щ���Ա�����loadMulLineǰ
			PersonAgeGrid.mulLineCount = 1;   
			PersonAgeGrid.displayTitle = 1;
			//ImpartGrid.tableWidth   ="500px";
			PersonAgeGrid.loadMulLine(iArray);  
			
			//��Щ����������loadMulLine����
			//ImpartGrid.setRowColData(1,1,"asdf");
	    }
	    catch(ex)
	    {
	    	  alert(ex);
	    }
	}
						
</script>
