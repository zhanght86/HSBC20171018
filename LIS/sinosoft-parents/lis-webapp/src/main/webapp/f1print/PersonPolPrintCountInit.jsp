 <%
//�������ƣ�RenewBankConfirmInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��hzm����
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
  try
  {
  	fm.ManageCom.value=<%=Branch%>;
    fm.ComCode.value=<%=Branch%>;
  	fm.StartDate.value="";
  	fm.EndDate.value="";
	}
  catch(ex)
  {
    alert("��RenewBankConfirmInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
		initCodeGrid();
  }
  catch(re)
  {
    alert("RenewBankConfirmInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

var CodeGrid;          //����Ϊȫ�ֱ������ṩ��displayMultilineʹ��
function initCodeGrid()
{                               
  var iArray = new Array();
      
  try {
	  iArray[0]=new Array();
	  iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	  iArray[0][1]="30px";            	//�п�
	  iArray[0][2]=10;            			//�����ֵ
	  iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	

    iArray[1]=new Array();
	  iArray[1][0]="ӡˢ��";		       	//����
	  iArray[1][1]="100px";            	//�п�
	  iArray[1][2]=20;            			//�����ֵ
	  iArray[1][3]=0; 
      

    iArray[2]=new Array();
	  iArray[2][0]="��ͬ����";   		//����
	  iArray[2][1]="160px";            	//�п�
	  iArray[2][2]=20;            			//�����ֵ
	  iArray[2][3]=0; 
    

	  iArray[3]=new Array();
	  iArray[3][0]="�������";		       	//����
	  iArray[3][1]="80px";          		//�п�
	  iArray[3][2]=10;            			//�����ֵ
	  iArray[3][3]=0; 

   
    iArray[4]=new Array();
	  iArray[4][0]="ǩ������";		       	//����
	  iArray[4][1]="80px";          		//�п�
	  iArray[4][2]=10;            			//�����ֵ
	  iArray[4][3]=0; 

    iArray[5]=new Array();
	  iArray[5][0]="ǩ��ʱ��";		       	//����
	  iArray[5][1]="80px";          		//�п�
	  iArray[5][2]=10;            			//�����ֵ
	  iArray[5][3]=0; 
	  

      
	  CodeGrid = new MulLineEnter( "fm" , "CodeGrid" ); 
	  //��Щ���Ա�����loadMulLineǰ
	  CodeGrid.mulLineCount = 5;   
	  CodeGrid.displayTitle = 1;
	  CodeGrid.hiddenSubtraction=1;
	  CodeGrid.hiddenPlus=1;
	  //CodeGrid.canChk = 1;
    CodeGrid.locked = 1;
	  CodeGrid.loadMulLine(iArray);  
	
	} catch(ex) {
		alert(ex);
	}
}

</script>
