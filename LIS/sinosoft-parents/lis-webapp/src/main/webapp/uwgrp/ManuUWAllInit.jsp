<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//���ҳ��ؼ��ĳ�ʼ����
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	String strOperator = globalInput.Operator;
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {            
  	if(operFlag == "1")
  	{
  		//fm.ApplyNo.value = "5";    
  		fm.ApplyType.value = "1";  				//��������
  		fm.ActivityID.value = "0000001100"; 
  	}
  	if(operFlag == "2")
  	{
  		//fm.ApplyNo.value = "2";  
  		fm.ApplyType.value = "2";    		  //�ŵ�����
  		fm.ActivityID.value = "0000002004"; 
  	}  	      
  	if(operFlag == "3")
  	{
 
  		//fm.ApplyNo.value = "2";  
  		fm.ApplyType.value = "3";    			//ѯ������
  		fm.ActivityID.value = "0000006004"; 
  	}            
          
  }
  catch(ex)
  {
    alert("��UWInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
   // alert('operFlag:'+operFlag);
    if(operFlag == "1")
    {
    	initSelfGrpGrid();  
    	initGrpGrid();    
    }
    if(operFlag == "2" || operFlag == "3")
    {
    	initGrpSelfGrpGrid();
    	initGrpGrpGrid(); 
    }  
            
    easyQueryClick();
  }
  catch(re)
  {
    alert("��UWInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initSelfGrpGrid()
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
      iArray[1][0]="ӡˢ��";         		//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="Ͷ������";         		//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="Ͷ����";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�˱�����״̬";         		//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������            

      iArray[5]=new Array();                                                       
      iArray[5][0]="�˱�����";         		//����                                     
      iArray[5][1]="0px";            		//�п�                                   
      iArray[5][2]=100;            			//�����ֵ                                 
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������   
            
      iArray[6]=new Array();
      iArray[6][0]="�����������";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[7]=new Array();
      iArray[7][0]="�������������";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[8]=new Array();
      iArray[8][0]="�������Id";         		//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      
      iArray[9]=new Array();
      iArray[9][0]="�ϱ�����";              //����
      iArray[9][1]="80px";                   //�п�
      iArray[9][2]=60;                      //�����ֵ
      iArray[9][3]=0;       
      
      iArray[10]=new Array();
      iArray[10][0]="�˱�����";               //����
      iArray[10][1]="60px";                 //�п�
      iArray[10][2]=60;                     //�����ֵ
      iArray[10][3]=0;                      

      iArray[11]=new Array();
      iArray[11][0]="ҵ��Ա";         		//����
      iArray[11][1]="80px";            		//�п�
      iArray[11][2]=60;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[12]=new Array();
      iArray[12][0]="Ͷ������";         		//����
      iArray[12][1]="80px";            		//�п�
      iArray[12][2]=60;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[13]=new Array();
      iArray[13][0]="��������";         		//����
      iArray[13][1]="80px";            		//�п�
      iArray[13][2]=60;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[14]=new Array();
      iArray[14][0]="�Ǽ�ҵ��Ա";         		//����
      iArray[14][1]="0px";            		//�п�
      iArray[14][2]=60;            			//�����ֵ
      iArray[14][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[15]=new Array();
      iArray[15][0]="VIP�ͻ�";         		//����
      iArray[15][1]="0px";            		//�п�
      iArray[15][2]=60;            			//�����ֵ
      iArray[15][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[16]=new Array();
      iArray[16][0]="�ϱ���־";         		//����
      iArray[16][1]="0px";            		//�п�
      iArray[16][2]=60;            			//�����ֵ
      iArray[16][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
 
      SelfGrpGrid = new MulLineEnter( "fm" , "SelfGrpGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      SelfGrpGrid.mulLineCount = 5;   
      SelfGrpGrid.displayTitle = 1;
      SelfGrpGrid.locked = 1;
      SelfGrpGrid.canSel = 1;
      SelfGrpGrid.hiddenPlus = 1;
      SelfGrpGrid.hiddenSubtraction = 1;
      SelfGrpGrid.loadMulLine(iArray);
      
      SelfGrpGrid.selBoxEventFuncName = "IniteasyQueryAddClick";
      
      //��Щ����������loadMulLine����
      //SelfGrpGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// ������Ϣ�б�ĳ�ʼ��
function initGrpSelfGrpGrid()
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
      iArray[1][0]="״̬����";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=30;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="����";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=30;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="ӡˢ��";         		//����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="����Ͷ������";         		//����
      iArray[4][1]="160px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="Ͷ����λ";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�ύ�˱�����";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=30;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="�˱�����״̬";         		//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������            

      iArray[8]=new Array();                                                       
      iArray[8][0]="�˱�����";         		//����                                     
      iArray[8][1]="80px";            		//�п�                                   
      iArray[8][2]=100;            			//�����ֵ                                 
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������   
            
      iArray[9]=new Array();
      iArray[9][0]="�����������";         		//����
      iArray[9][1]="0px";            		//�п�
      iArray[9][2]=200;            			//�����ֵ
      iArray[9][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[10]=new Array();
      iArray[10][0]="�������������";         		//����
      iArray[10][1]="0px";            		//�п�
      iArray[10][2]=200;            			//�����ֵ
      iArray[10][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[11]=new Array();
      iArray[11][0]="�������Id";         		//����
      iArray[11][1]="0px";            		//�п�
      iArray[11][2]=60;            			//�����ֵ
      iArray[11][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[12]=new Array();
      iArray[12][0]="�ϱ�����";              //����
      iArray[12][1]="80px";                   //�п�
      iArray[12][2]=60;                      //�����ֵ
      iArray[12][3]=3;       
      
      iArray[13]=new Array();
      iArray[13][0]="�˱�����";               //����
      iArray[13][1]="60px";                 //�п�
      iArray[13][2]=60;                     //�����ֵ
      iArray[13][3]=3;                      

      iArray[14]=new Array();
      iArray[14][0]="ҵ��Ա";         		//����
      iArray[14][1]="80px";            		//�п�
      iArray[14][2]=60;            			//�����ֵ
      iArray[14][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[15]=new Array();
      iArray[15][0]="Ͷ������";         		//����
      iArray[15][1]="80px";            		//�п�
      iArray[15][2]=60;            			//�����ֵ
      iArray[15][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[16]=new Array();
      iArray[16][0]="��������";         		//����
      iArray[16][1]="80px";            		//�п�
      iArray[16][2]=60;            			//�����ֵ
      iArray[16][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[17]=new Array();
      iArray[17][0]="�Ǽ�ҵ��Ա";         		//����
      iArray[17][1]="0px";            		//�п�
      iArray[17][2]=60;            			//�����ֵ
      iArray[17][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[18]=new Array();
      iArray[18][0]="VIP�ͻ�";         		//����
      iArray[18][1]="0px";            		//�п�
      iArray[18][2]=60;            			//�����ֵ
      iArray[18][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[19]=new Array();
      iArray[19][0]="�ϱ���־";         		//����
      iArray[19][1]="0px";            		//�п�
      iArray[19][2]=60;            			//�����ֵ
      iArray[19][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������    
    
      SelfGrpGrid = new MulLineEnter( "fm" , "SelfGrpGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      SelfGrpGrid.mulLineCount = 5;   
      SelfGrpGrid.displayTitle = 1;
      SelfGrpGrid.locked = 1;
      SelfGrpGrid.canSel = 1;
      SelfGrpGrid.hiddenPlus = 1;
      SelfGrpGrid.hiddenSubtraction = 1;     
      SelfGrpGrid.selBoxEventFuncName = "GrpIniteasyQueryAddClick";
      SelfGrpGrid.loadMulLine(iArray); 
     
      
      //��Щ����������loadMulLine����
      //SelfGrpGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// ������Ϣ�б�ĳ�ʼ��
function initGrpGrid()
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
      iArray[1][0]="ӡˢ��";         		//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="Ͷ������";         		//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="Ͷ����";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�˱�����״̬";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������            

      iArray[5]=new Array();                                                       
      iArray[5][0]="�˱�����";         		//����                                     
      iArray[5][1]="0px";            		//�п�                                   
      iArray[5][2]=100;            			//�����ֵ                                 
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
            
      iArray[6]=new Array();
      iArray[6][0]="�����������";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[7]=new Array();
      iArray[7][0]="�������������";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[8]=new Array();
      iArray[8][0]="�������Id";         		//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[9]=new Array();
      iArray[9][0]="�ϱ�����";         		//����
      iArray[9][1]="100px";            		//�п�
      iArray[9][2]=60;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[10]=new Array();
      iArray[10][0]="ҵ��Ա";         		//����
      iArray[10][1]="80px";            		//�п�
      iArray[10][2]=60;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[11]=new Array();
      iArray[11][0]="Ͷ������";         		//����
      iArray[11][1]="80px";            		//�п�
      iArray[11][2]=60;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[12]=new Array();
      iArray[12][0]="��������";         		//����
      iArray[12][1]="80px";            		//�п�
      iArray[12][2]=60;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[13]=new Array();
      iArray[13][0]="�Ǽ�ҵ��Ա";         		//����
      iArray[13][1]="0px";            		//�п�
      iArray[13][2]=60;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[14]=new Array();
      iArray[14][0]="VIP�ͻ�";         		//����
      iArray[14][1]="0px";            		//�п�
      iArray[14][2]=60;            			//�����ֵ
      iArray[14][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[15]=new Array();
      iArray[15][0]="�ϱ���־";         		//����
      iArray[15][1]="0px";            		//�п�
      iArray[15][2]=60;            			//�����ֵ
      iArray[15][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
 
      GrpGrid = new MulLineEnter( "fm" , "GrpGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      GrpGrid.mulLineCount = 5;   
      GrpGrid.displayTitle = 1;
      GrpGrid.locked = 1;
      GrpGrid.canSel = 1;
      GrpGrid.hiddenPlus = 1;
      GrpGrid.hiddenSubtraction = 1;
      GrpGrid.loadMulLine(iArray);
     // GrpGrid.selBoxEventFuncName = "GrpIniteasyQueryAddClick";   

      }
      catch(ex)
      {
        alert(ex);
      }
}

// ������Ϣ�б�ĳ�ʼ��
function initGrpGrpGrid()
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
      iArray[1][0]="ӡˢ��";         		//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="����Ͷ������";         		//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="Ͷ����λ";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="���ֱ�ʶ";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      
      
      iArray[5]=new Array();
      iArray[5][0]="�˱�����״̬";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������            

      iArray[6]=new Array();                                                       
      iArray[6][0]="�˱�����";         		//����                                     
      iArray[6][1]="80px";            		//�п�                                   
      iArray[6][2]=100;            			//�����ֵ                                 
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
            
      iArray[7]=new Array();
      iArray[7][0]="�����������";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[8]=new Array();
      iArray[8][0]="�������������";         		//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[9]=new Array();
      iArray[9][0]="�������Id";         		//����
      iArray[9][1]="0px";            		//�п�
      iArray[9][2]=60;            			//�����ֵ
      iArray[9][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
  
      iArray[10]=new Array();
      iArray[10][0]="�ϱ�����";         		//����
      iArray[10][1]="100px";            		//�п�
      iArray[10][2]=60;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[11]=new Array();
      iArray[11][0]="ҵ��Ա";         		//����
      iArray[11][1]="80px";            		//�п�
      iArray[11][2]=60;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[12]=new Array();
      iArray[12][0]="Ͷ������";         		//����
      iArray[12][1]="80px";            		//�п�
      iArray[12][2]=60;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[13]=new Array();
      iArray[13][0]="��������";         		//����
      iArray[13][1]="80px";            		//�п�
      iArray[13][2]=60;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[14]=new Array();
      iArray[14][0]="�Ǽ�ҵ��Ա";         		//����
      iArray[14][1]="0px";            		//�п�
      iArray[14][2]=60;            			//�����ֵ
      iArray[14][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[15]=new Array();
      iArray[15][0]="VIP�ͻ�";         		//����
      iArray[15][1]="0px";            		//�п�
      iArray[15][2]=60;            			//�����ֵ
      iArray[15][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[16]=new Array();
      iArray[16][0]="�ϱ���־";         		//����
      iArray[16][1]="0px";            		//�п�
      iArray[16][2]=60;            			//�����ֵ
      iArray[16][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
     
      GrpGrid = new MulLineEnter( "fm" , "GrpGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      GrpGrid.mulLineCount = 5;   
      GrpGrid.displayTitle = 1;
      GrpGrid.locked = 1;
      GrpGrid.canSel = 1;
      GrpGrid.hiddenPlus = 1;
      GrpGrid.hiddenSubtraction = 1;
      GrpGrid.loadMulLine(iArray); 

      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
