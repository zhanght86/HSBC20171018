<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�QuestInputInit.jsp
//�����ܣ��˱��ȴ���ʼ��
//�������ڣ�2008-09-27 11:10:36
//������  ��ln
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
<script language="JavaScript">


// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
try
  {                                   
    //document.all('ContNo').value = tContNo;
    document.all('WaitReason').value = '';
    document.all('Reason').value = '';
    document.all('Content').value = '';  
    document.all('UniteNo').value = '';  
 
  }
  catch(ex)
  {
    alert("��WaitReasonInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }   
} 

// ���������ĳ�ʼ��
function initHide()
{ 
try
  {                                   
    document.all('ContNoH').value = tContNo;
    document.all('MissionIDH').value = '';
    document.all('SubMissionIDH').value = '';
    document.all('InsuredNoH').value = '';    
  }
  catch(ex)
  {
    alert("��WaitReasonInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }   
}                                     

function initForm()
{
  try
  {
    
	initInpBox();
	initHide();	
	initPolAddGrid();
	//alert(2);
	initWaitContGrid();	
	iniReason();
	//alert(1);
	queryInsuredNo();
	if(tType == '1')
	{
		//document.all('UniteNo').value = tContNo;
		//initUniteNo();
	}
	if(tType == '2')
	{	    
	    showReasonInfo();
	}
		
  }
  catch(re)
  {
    alert("WaitReasonInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

//��ѯͬһ��������δ�б�Ͷ����
function initWaitContGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��ͬ��";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="0px";         			//�п�
      iArray[1][2]=10;          			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="ӡˢ��";    	//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=130;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="Ͷ���˿ͻ���";         			//����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="Ͷ��������";         			//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="Ͷ������";         			//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�˱�����״̬";         			//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=50;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="�˱�����״̬����";         			//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="����˱��˹���";         			//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="Ŀǰλ��";         			//����
      iArray[9][1]="80px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������     
      
      iArray[10]=new Array();
      iArray[10][0]="�����";         			//����
      iArray[10][1]="0px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������  
      
      iArray[11]=new Array();
      iArray[11][0]="�������";         			//����
      iArray[11][1]="0px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������    
          
      
      WaitContGrid = new MulLineEnter( "fm" , "WaitContGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      WaitContGrid.mulLineCount = 0;   
      WaitContGrid.displayTitle = 1;
      WaitContGrid.locked = 1;
      WaitContGrid.canSel = 1;
      WaitContGrid.hiddenPlus = 1;
      WaitContGrid.hiddenSubtraction = 1;
      WaitContGrid.loadMulLine(iArray); 
      WaitContGrid.selBoxEventFuncName = "initClick"; 

      }
      catch(ex)
      {
        alert(ex);
      }
}

//�������б�
function initPolAddGrid()
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
      iArray[1][0]="�ͻ�����";         		//����
      iArray[1][1]="140px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[2]=new Array();
      iArray[2][0]="����";         		//����
      iArray[2][1]="140px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="֤������";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="֤������";         		//����
      iArray[4][1]="120px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      PolAddGrid = new MulLineEnter( "fm" , "PolAddGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolAddGrid.mulLineCount = 0;   
      PolAddGrid.displayTitle = 1;
      PolAddGrid.locked = 1;
      PolAddGrid.canSel = 1;
      PolAddGrid.hiddenPlus = 1;
      PolAddGrid.hiddenSubtraction = 1;
      PolAddGrid.loadMulLine(iArray);       
      PolAddGrid.selBoxEventFuncName = "showInsuredInfo";
      //��Щ����������loadMulLine����
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>


