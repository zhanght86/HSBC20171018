<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UnderwriteInit.jsp
//�����ܣ������˹��˱�
//�������ڣ�2002-09-24 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%> 
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--�û�У����-->
<%
//���ҳ��ؼ��ĳ�ʼ����
String tGrpContNo="";
tGrpContNo=request.getParameter("GrpContNo");

GlobalInput tGI=new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");

if(tGI == null) {
	out.println("session has expired");
	return;
}

String strOperator = tGI.Operator;
%>                            

<script language="JavaScript">





function initForm()
{
  try
  {
    
       
    initPolGrid();   
    initPolHaveGrid();
		easyQueryClick(cGrpContNo);
		easyQueryHaveClick(cGrpContNo);
  }
  catch(re)
  {
    alert("��ManuUWInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initPolGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�������ֺ�";         		//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="����Ͷ������";         		//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="���ֱ���";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="����";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=200;                 	//�����ֵ
      iArray[4][3]=0;                     	//�Ƿ���������,1��ʾ����0��ʾ������

  
      iArray[5]=new Array();
      iArray[5][0]="�����ѱ���(С��)";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=200;                 	//�����ֵ
      iArray[5][3]=1;                     	//�Ƿ���������,1��ʾ����0��ʾ������
      
      

      iArray[6]=new Array();
      iArray[6][0]="������";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="��ע";         		//����
      iArray[7][1]="260px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      //iArray[6][4]="Station";              	        //�Ƿ����ô���:null||""Ϊ������
      
      //iArray[7]=new Array();
      //iArray[7][0]="��ע";         		//����
      //iArray[7][1]="80px";            		//�п�
      //iArray[7][2]=100;            			//�����ֵ
      //iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
  
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 3;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 0;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);     
  
      }
      catch(ex)
      {
        alert(ex);
      }
}


// ���е�
function initPolHaveGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�������ֺ�";         		//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="����Ͷ������";         		//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="���ֱ���";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

  
      iArray[4]=new Array();
      iArray[4][0]="�����ѱ���";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=200;                 	//�����ֵ
      iArray[4][3]=1;                     	//�Ƿ���������,1��ʾ����0��ʾ������
      
      

      iArray[5]=new Array();
      iArray[5][0]="������";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="��ȡ��";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      //iArray[6][4]="Station";              	        //�Ƿ����ô���:null||""Ϊ������
      
      iArray[7]=new Array();
      iArray[7][0]="����";         		//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="����֪ͨ�����";         		//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="��ȡ��ID";         		//����
      iArray[9][1]="80px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="��ע";         		//����
      iArray[10][1]="80px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
  
      HavePolGrid = new MulLineEnter( "fm" , "HavePolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      HavePolGrid.mulLineCount = 3;   
      HavePolGrid.displayTitle = 1;
      HavePolGrid.locked = 1;
      HavePolGrid.canSel = 0;
      HavePolGrid.canChk = 1;
      HavePolGrid.hiddenPlus = 1;
      HavePolGrid.hiddenSubtraction=1;
      HavePolGrid.loadMulLine(iArray);     
  
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
