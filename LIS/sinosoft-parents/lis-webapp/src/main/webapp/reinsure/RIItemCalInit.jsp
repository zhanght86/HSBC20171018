<%@include file="/i18n/language.jsp"%>
<%
//�������ƣ�CostDataAcquisitionDefInputInit.jsp
//�����ܣ�ƾ֤�������ݲɼ�����
//�������ڣ�2008-08-18
//������  �����  
%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>


<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
  GlobalInput globalInput = (GlobalInput)session.getAttribute("GI");
	
	if(globalInput == null) 
	{
		out.println(""+"��ҳ��ʱ�������µ�¼"+"");
		return;
	}
	String strOperator = globalInput.Operator;
%>  
                       
<script type="text/javascript">
function initInpBox()
{ 
  try
  {     
    fm.reset();
    
  }
  catch(ex)
  {
    myAlert("��RIItemCalInit.jsp-->"+"��ʼ���������!");
  }      
}

function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    myAlert("��RIItemCalInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initResultGrid();
    initKResultGrid();
  }
  catch(re)
  {
    myAlert("��RIItemCalInit.jsp-->"+"��ʼ���������!");
  }
}

function initResultGrid()
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
      iArray[1][0]="�㷨�������";         		//����
      iArray[1][1]="70px";            		//�п�
      iArray[1][2]=170;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�㷨��������";      	   		//����
      iArray[2][1]="100px";            			//�п�
      iArray[2][2]=10;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
      iArray[3]=new Array();
      iArray[3][0]="�㷨����";      	   		//����
      iArray[3][1]="130px";            			//�п�
      iArray[3][2]=10;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�ۼƷ��ձ���";      	   		//����
      iArray[4][1]="60px";            			//�п�
      iArray[4][2]=10;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
          
      iArray[5]=new Array();
      iArray[5][0]="�����ڼ�";      	   		//����
      iArray[5][1]="60px";            			//�п�
      iArray[5][2]=10;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[6]=new Array();
      iArray[6][0]="���㷽ʽ";      	   		//����
      iArray[6][1]="60px";            			//�п�
      iArray[6][2]=10;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      ResultGrid = new MulLineEnter( "fm" , "ResultGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      //ResultGrid.mulLineCount = 1;   
      ResultGrid.displayTitle = 1;
      ResultGrid.locked = 1;
      ResultGrid.canSel = 1;
      ResultGrid.canChk = 0;
      ResultGrid.hiddenSubtraction = 1;
      ResultGrid.hiddenPlus = 1;
      ResultGrid.loadMulLine(iArray);  
      ResultGrid.selBoxEventFuncName ="queryDetial"; //��Ӧ�ĺ���������������   
      

      }
      catch(ex)
      {
        myAlert(ex);
      }
}

function initKResultGrid()
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
      iArray[1][0]="�㷨�������";         		//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��ϸ�㷨����";      	   		//����
      iArray[2][1]="60px";            			//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
      iArray[3]=new Array();
      iArray[3][0]="��ϸ�㷨����";      	   		//����
      iArray[3][1]="130px";            			//�п�
      iArray[3][2]=10;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
      iArray[4]=new Array();
      iArray[4][0]="��ϸ�㷨����";      	   		//����
      iArray[4][1]="50px";            			//�п�
      iArray[4][2]=10;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[5]=new Array();
      iArray[5][0]="��ϸ�㷨��������";      	   		//����
      iArray[5][1]="70px";            			//�п�
      iArray[5][2]=10;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
            
      iArray[6]=new Array();
      iArray[6][0]="���������";      	   		//����
      iArray[6][1]="70px";            			//�п�
      iArray[6][2]=10;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[7]=new Array();
      iArray[7][0]="����������";      	   		//����
      iArray[7][1]="70px";            			//�п�
      iArray[7][2]=10;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[8]=new Array();
      iArray[8][0]="�㷨ִ��˳��";      	   		//����
      iArray[8][1]="50px";            			//�п�
      iArray[8][2]=10;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[9]=new Array();
      iArray[9][0]="���������";      	   		//����
      iArray[9][1]="10px";            			//�п�
      iArray[9][2]=10;            			//�����ֵ
      iArray[9][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[10]=new Array();
      iArray[10][0]="�������������";      	   		//����
      iArray[10][1]="10px";            			//�п�
      iArray[10][2]=10;            			//�����ֵ
      iArray[10][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[11]=new Array();
      iArray[11][0]="�̶�����ֵ";      	   		//����
      iArray[11][1]="10px";            			//�п�
      iArray[11][2]=10;            			//�����ֵ
      iArray[11][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[12]=new Array();
      iArray[12][0]="���㴦����";      	   		//����
      iArray[12][1]="10px";            			//�п�
      iArray[12][2]=10;            			//�����ֵ
      iArray[12][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[13]=new Array();
      iArray[13][0]="����SQL�㷨";      	   		//����
      iArray[13][1]="10px";            			//�п�
      iArray[13][2]=10;            			//�����ֵ
      iArray[13][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[14]=new Array();
      iArray[14][0]="�������洢�ֶ�";      	   		//����
      iArray[14][1]="10px";            			//�п�
      iArray[14][2]=10;            			//�����ֵ
      iArray[14][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[15]=new Array();
      iArray[15][0]="����";      	   		//����
      iArray[15][1]="10px";            			//�п�
      iArray[15][2]=10;            			//�����ֵ
      iArray[15][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[16]=new Array();
      iArray[16][0]="�����ַ�������1";      	   		//����
      iArray[16][1]="10px";            			//�п�
      iArray[16][2]=10;            			//�����ֵ
      iArray[16][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[17]=new Array();
      iArray[17][0]="�����ַ�������2";      	   		//����
      iArray[17][1]="10px";            			//�п�
      iArray[17][2]=10;            			//�����ֵ
      iArray[17][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[18]=new Array();
      iArray[18][0]="�����ַ�������3";      	   		//����
      iArray[18][1]="10px";            			//�п�
      iArray[18][2]=10;            			//�����ֵ
      iArray[18][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      KResultGrid = new MulLineEnter( "fm" , "KResultGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      //ResultGrid.mulLineCount = 1;   
      KResultGrid.displayTitle = 1;
      KResultGrid.locked = 1;
      KResultGrid.canSel = 1;
      KResultGrid.canChk = 0;
      KResultGrid.hiddenSubtraction = 1;
      KResultGrid.hiddenPlus = 1;
      KResultGrid.loadMulLine(iArray);  
      KResultGrid.selBoxEventFuncName ="DKmis"; //��Ӧ�ĺ���������������   
      

      }
      catch(ex)
      {
        myAlert(ex);
      }
}
</script>