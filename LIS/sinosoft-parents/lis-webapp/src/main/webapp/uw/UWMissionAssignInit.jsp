<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWMissionAssignInit.jsp
//�����ܣ��˱��������
//�������ڣ�2005-5-14 16:25
//������  ��HWM
//���¼�¼��  ������    ��������     ����ԭ��/����
//            ������    2006-10-20    ����˱������������
%> 
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

function initForm()
{
  try
  {
   	initAllPolGrid();    
   	initBqPolGrid();
   	initLpPolGrid();
  }
  catch(re)
  {
    alert("��UWMissionAssignInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initAllPolGrid()
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
      iArray[1][1]="160px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="Ͷ������";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="Ͷ����";         		//����
      iArray[3][1]="200px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�˱�״̬";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������            

      iArray[5]=new Array();                                                       
      iArray[5][0]="��������";         		//����                                     
      iArray[5][1]="80px";            		//�п�                                   
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
      iArray[9][0]="�˱�ʦ���";         		//����                                     
      iArray[9][1]="80px";            		//�п�                                   
      iArray[9][2]=100;            			//�����ֵ                                 
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
      
      iArray[10]=new Array();                                                       
      iArray[10][0]="�˱�����";         		//����                                     
      iArray[10][1]="80px";            		//�п�                                   
      iArray[10][2]=100;            			//�����ֵ                                 
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
	    iArray[11]=new Array();                                                       
      iArray[11][0]="�������";         		//����                                     
      iArray[11][1]="80px";            		//�п�                                   
      iArray[11][2]=100;            			//�����ֵ                                 

      
      
	  AllPolGrid = new MulLineEnter( "document" , "AllPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      AllPolGrid.mulLineCount = 5;   
      AllPolGrid.displayTitle = 1;
      AllPolGrid.locked = 1;
      AllPolGrid.canSel = 0;
      AllPolGrid.canChk = 1;
      AllPolGrid.hiddenPlus = 1;
      AllPolGrid.hiddenSubtraction = 1;
  //    AllPolGrid.selBoxEventFuncName = "setManageCom";
      AllPolGrid.loadMulLine(iArray);     

      }
      catch(ex)
      {
        alert(ex);
      }
}
function initBqPolGrid()
  {                               
    var iArray = new Array();
    try 
     {
      iArray[0] = new Array();
      iArray[0][0] = "���";                          //����(˳���, ������)
      iArray[0][1] = "30px";                          //�п�
      iArray[0][2] = 30;                              //�����ֵ
      iArray[0][3] = 0;                               //�Ƿ���������: 0��ʾ������; 1��ʾ����

      iArray[1] = new Array();
      iArray[1][0] = "�����������";
      iArray[1][1] = "0px";
      iArray[1][2] = 0;
      iArray[1][3] = 3;

      iArray[2] = new Array();
      iArray[2][0] = "�������������";
      iArray[2][1] = "0px";
      iArray[2][2] = 0;
      iArray[2][3] = 3;

      iArray[3] = new Array();
      iArray[3][0] = "��ȫ�����";
      iArray[3][1] = "110px";
      iArray[3][2] = 150;
      iArray[3][3] = 0;

      iArray[4] = new Array();
      iArray[4][0] = "�ͻ�/������";
      iArray[4][1] = "110px";
      iArray[4][2] = 150;
      iArray[4][3] = 0;

      iArray[5] = new Array();
      iArray[5][0] = "��������";
      iArray[5][1] = "70px";
      iArray[5][2] = 100;
      iArray[5][3] = 0;

      iArray[6] = new Array();
      iArray[6][0] = "������";
      iArray[6][1] = "70px";
      iArray[6][2] = 100;
      iArray[6][3] = 0;
      
      iArray[7] = new Array();
      iArray[7][0] = "�˱�״̬";
      iArray[7][1] = "60px";
      iArray[7][2] = 100;
      iArray[7][3] = 0;
                
      iArray[8] = new Array();
      iArray[8][0] = "��������";
      iArray[8][1] = "60px";
      iArray[8][2] = 100;
      iArray[8][3] = 0;
                
      iArray[9] = new Array();
      iArray[9][0] = "�˱�ʦ����";
      iArray[9][1] = "60px";
      iArray[9][2] = 100;
      iArray[9][3] = 0;
                   
      iArray[10] = new Array();
      iArray[10][0] = "�������";
      iArray[10][1] = "85px";
      iArray[10][2] = 100;
      iArray[10][3] = 0;
      
      iArray[11] = new Array();
      iArray[11][0] = "Activityid";
      iArray[11][1] = "0px";
      iArray[11][2] = 100;
      iArray[11][3] = 0;
      
     }
     catch (ex)
      {
        alert("�� UWMissionAssignInit.jsp --> initSelfGrid �����з����쳣: ��ʼ���������");
      }
      try
      {
        BqPolGrid = new MulLineEnter("document", "BqPolGrid");
        BqPolGrid.mulLineCount = 5;
        BqPolGrid.displayTitle = 1;
        BqPolGrid.locked = 1;
        BqPolGrid.hiddenPlus = 1;
        BqPolGrid.hiddenSubtraction = 1;
        BqPolGrid.canSel = 0;
        BqPolGrid.canChk = 1;
        BqPolGrid.chkBoxEventFuncName = "";
        BqPolGrid.selBoxEventFuncName = "";
         //�������Ա����� MulLineEnter loadMulLine ֮ǰ
        BqPolGrid.loadMulLine(iArray);
       }
        catch (ex)
        {
                alert("�� WFGrpEdorManuUWApplyInit.jsp --> initSelfGrid �����з����쳣: ��ʼ���������");
        }
 }
 
function initLpPolGrid()
{                               
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";         			//���� ������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";            		//�п�
		iArray[0][2]=30;            			//�����ֵ
		iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������       			 
		
		iArray[1]=new Array();
		iArray[1][0]="�ⰸ��";         		 
		iArray[1][1]="100px";            		 
		iArray[1][2]=100;            			 
		iArray[1][3]=0;              			 
	
		iArray[2]=new Array();
		iArray[2][0]="���κ�";         		 
		iArray[2][1]="120px";            		 
		iArray[2][2]=100;            			 
		iArray[2][3]=0;  
		            			 
		iArray[3]=new Array();
		iArray[3][0]="�˱�����״̬";         		 
		iArray[3][1]="100px";            		 
		iArray[3][2]=100;            			 
		iArray[3][3]=0;              			 
		
		iArray[4]=new Array();
		iArray[4][0]="������������";         		 
		iArray[4][1]="100px";            		 
		iArray[4][2]=100;            			 
		iArray[4][3]=0;              			 
	
		iArray[5]=new Array();
		iArray[5][0]="�ⰸ��ر�־";         		 
		iArray[5][1]="80px";            		 
		iArray[5][2]=100;            			 
		iArray[5][3]=0;              			 
		
		iArray[6]=new Array();
		iArray[6][0]="�˱�ʦ����";         		 
		iArray[6][1]="100px";            		 
		iArray[6][2]=100;            			 
		iArray[6][3]=0;              			    
	
		iArray[7]=new Array();                                                       
		iArray[7][0]="�������";         		                                      
		iArray[7][1]="120px";            		                                    
		iArray[7][2]=100;            			                                  
		iArray[7][3]=0;              			    
	
	  iArray[8]=new Array();
	  iArray[8][0]="Missionid";              
    iArray[8][1]="0px";                 
    iArray[8][2]=200;                   
	  iArray[8][3]=3;                           
	
	  iArray[9]=new Array();
	  iArray[9][0]="Submissionid";              
	  iArray[9][1]="0px";                 
	  iArray[9][2]=200;                   
	  iArray[9][3]=3;                           
	    
    iArray[10]=new Array();
	  iArray[10][0]="Activityid";              
	  iArray[10][1]="0px";                 
	  iArray[10][2]=200;                   
	  iArray[10][3]=3;                      
	   
	  iArray[11]=new Array();
	  iArray[11][0]="IsReFalg";              // ���� ���ⰸ��ر�־��
	  iArray[11][1]="0px";                
	  iArray[11][2]=200;                  
	  iArray[11][3]=3;                       
	
		LpPolGrid = new MulLineEnter( "document" , "LpPolGrid" ); 
		//��Щ���Ա�����loadMulLineǰ
		LpPolGrid.mulLineCount =5;   
		LpPolGrid.displayTitle = 1;
		LpPolGrid.locked = 1;
		LpPolGrid.canSel = 0;
    LpPolGrid.canChk = 1;
		LpPolGrid.hiddenPlus = 1;
		LpPolGrid.hiddenSubtraction = 1;
		LpPolGrid.loadMulLine(iArray);     
	//	LpPolGrid.selBoxEventFuncName = "SelfLLCUWBatchGridClick";
		//��Щ����������loadMulLine����
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>
