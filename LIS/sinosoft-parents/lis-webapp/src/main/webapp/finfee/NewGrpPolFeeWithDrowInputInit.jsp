 <%
//�������ƣ�NewGrpPolFeeWithDrowInputInit.jsp
//�����ܣ�
//�������ڣ�2006-10-18 9:49
//������  ��lujun 
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<script language="JavaScript">
function initInpBox()
{ 
  try
  {
  	fm.GrpContNo.value = '';
  	fm.PrtNo.value = '';
  	fm.GrpName.value = '';
  	fm.AgentCode.value = '';
 // 	fm.Dif.value = 0;
  }
  catch(ex)
  {
    alert("��NewGrpPolFeeWithDrowInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();   
    initFinFeeWithDrawGrid();   	
  }
  catch(re)
  {
    alert("NewGrpPolFeeWithDrowInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

 var FinFeeWithDrawGrid ;
function initFinFeeWithDrawGrid()
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
      iArray[1][0]="�����ͬ��";   		//����
      iArray[1][1]="150px";            		//�п�
      iArray[1][2]=100;            		//�����ֵ
      iArray[1][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="Ͷ����λ����";		//����
      iArray[2][1]="180px";            		//�п�
      iArray[2][2]=60;            		//�����ֵ
      iArray[2][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
      

      iArray[3]=new Array();
      iArray[3][0]="�˷ѽ��";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=200;            	        //�����ֵ
      iArray[3][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="ǩ������";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=200;            	        //�����ֵ
      iArray[4][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������
	
	  
      iArray[5]=new Array();
      iArray[5][0]="��λ��������";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=200;            	        //�����ֵ
      iArray[5][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="���Ѻ�";         		//����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=200;            	        //�����ֵ
      iArray[6][3]=3;                   	//�Ƿ���������,1��ʾ����0��ʾ������
      

      iArray[7]=new Array();
      iArray[7][0]="��λ�����ʺ�";         		//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=200;            	        //�����ֵ
      iArray[7][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������  
      
      iArray[8]=new Array();
      iArray[8][0]="�������";         		//����
      iArray[8][1]="100px";            		//�п�
      iArray[8][2]=200;            	        //�����ֵ
      iArray[8][3]=3;   
/*
      iArray[8]=new Array();
      iArray[8][0]="�����Ż������";         		//����
      iArray[8][1]="120px";            		//�п�
      iArray[8][2]=200;            	        //�����ֵ
      iArray[8][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������  
      
      iArray[9]=new Array();
      iArray[9][0]="������";         		//����
      iArray[9][1]="120px";            		//�п�
      iArray[9][2]=200;            	        //�����ֵ
      iArray[9][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[10]=new Array();
      iArray[10][0]="ʵ������";         		//����
      iArray[10][1]="120px";            		//�п�
      iArray[10][2]=200;            	        //�����ֵ
      iArray[10][3]=3;                   	//�Ƿ���������,1��ʾ����0��ʾ������ 
      */       
 
      FinFeeWithDrawGrid = new MulLineEnter( "fm" , "FinFeeWithDrawGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      FinFeeWithDrawGrid.mulLineCount = 5;
      FinFeeWithDrawGrid.displayTitle = 1;
      FinFeeWithDrawGrid.hiddenPlus = 1;
      FinFeeWithDrawGrid.hiddenSubtraction = 1;
      FinFeeWithDrawGrid.canSel = 1;
      FinFeeWithDrawGrid.canChk = 0;
      FinFeeWithDrawGrid.loadMulLine(iArray);
      FinFeeWithDrawGrid.chkBoxEventFuncName = "GetRecord";  

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
