<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuAddInit.jsp
//�����ܣ��˹��˱������б�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
<%
  String tContNo = "";
  String tPolNo = "";
  String tMissionID = "";
  String tSubMissionID = "";
  String tProposalNo = "";
  String tInsuredNo="";
tContNo = request.getParameter("ContNo");
//tPolNo = request.getParameter("PolNo");
tMissionID = request.getParameter("MissionID");
tSubMissionID = request.getParameter("SubMissionID");
tInsuredNo = request.getParameter("InsuredNo");
loggerDebug("LLUWAddFeeInit",tInsuredNo);

%>                            

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

var str = "";
var tRow = "";

/**=========================================================================
    �޸�״̬��ҳ���ʼ��
    �޸�ԭ��
    �� �� �ˣ�����
    �޸����ڣ�2005.10.28
   =========================================================================
**/
function initForm(tPolNo,tContNo, tMissionID, tSubMissionID,tInsuredNo)
{
		var str = "";
		try
		{
				initInpBox();
				
				initPolAddGrid();
			    initLCPremGrid();
			    initLLUWPremSubGrid();
				initHide(tPolNo,tContNo, tMissionID, tSubMissionID,tInsuredNo);
				
				QueryPolAddGrid(tContNo,tInsuredNo);
				getPolGridCho();
				LLUWPremMaster("",tContNo);		
				if(tQueryFlag=="1")
				{
				    fm.button1.style.display="none";
				    fm.button3.style.display="none";
				}	
				
			}
			catch(re)
			{
			  alert("��LLUWAddInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
			}
}


// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
try
  {                                   
	document.all('AddReason').value = '';
   }
  catch(ex)
  {
    alert("��PEdorUWManuAddInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }   
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("��PEdorUWManuAddInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        



// ������Ϣ�б�ĳ�ʼ��
function initSpecGrid(str){ 
    var iArray = new Array();      
      try
      {
      
	      iArray[0]=new Array();
	      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	      iArray[0][1]="30px";         			//�п�
	      iArray[0][2]=10;          			//�����ֵ
	      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
	
	      iArray[1]=new Array();
	      iArray[1][0]="�ӷ�����";    	        //����
	      iArray[1][1]="60px";            		//�п�
	      iArray[1][2]=100;            			//�����ֵ
	      iArray[1][3]=2;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
	      //iArray[1][7]="RadioSelected";   
	      iArray[1][10] = "DutyCode";             			
	      iArray[1][11] = str;
	      iArray[1][12] = "1|3|4";
	      iArray[1][13] = "0|1|2";
	      iArray[1][19] = 1;	
	
	      iArray[2]=new Array();
	      iArray[2][0]="�ӷ�ԭ��";    	        //����
	      iArray[2][1]="60px";            		//�п�
	      iArray[2][2]=100;            			//�����ֵ
	      iArray[2][3]=2;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��   
	      //iArray[2][7]="RadioSelected";   
	      iArray[2][10] = "PlanPay";             			
	      iArray[2][11] = "0|^01|�����ӷ�^02|ְҵ�ӷ�";
	      iArray[2][12] = "2";
	      iArray[2][13] = "0";
	      iArray[2][19]= 1 ;
	             
	      
	      iArray[3]=new Array();
	      iArray[3][0]="��ʼ����";         		//����
	      iArray[3][1]="60px";            		//�п�
	      iArray[3][2]=100;            			//�����ֵ
	      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
	
	      iArray[4]=new Array();
	      iArray[4][0]="��ֹ����";         		//����
	      iArray[4][1]="60px";            		//�п�
	      iArray[4][2]=60;            			//�����ֵ
	      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
	
	      iArray[5]=new Array();
	      iArray[5][0]="�ӷ�����";         		//����
	      iArray[5][1]="60px";            		//�п�
	      iArray[5][2]=100;            			//�����ֵ
	      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ�� 
	      
	      iArray[6]=new Array();
	      iArray[6][0]="�ڶ��������˼ӷ�����";         		//����
	      iArray[6][1]="80px";            		//�п�
	      iArray[6][2]=100;            			//�����ֵ
	      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ�� 
	
	          
	      iArray[7]=new Array();
	      iArray[7][0]="�ӷѶ���";         		//����
	      iArray[7][1]="60px";            		//�п�
	      iArray[7][2]=100;            			//�����ֵ
	      iArray[7][3]=2;
	      //iArray[7][7]="RadioSelected";   
	      iArray[7][10] = "PayObject";             			
	      iArray[7][11] = "0|^01|Ͷ����^02|��������^03|�౻������^04|�ڶ���������";
	      iArray[7][12] = "7";
	      iArray[7][13] = "0";
	      iArray[7][19]= 1 ;     
	     
	      iArray[8]=new Array();
	      iArray[8][0]="�ӷѽ��";         		//����
	      iArray[8][1]="80px";            		//�п�
	      iArray[8][2]=100;            			//�����ֵ
	      iArray[8][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ�� 
	      iArray[8][7]="CalHealthAddFee";
	         
	
	      SpecGrid = new MulLineEnter( "document" , "SpecGrid" ); 
	      //��Щ���Ա�����loadMulLineǰ                            
	      SpecGrid.mulLineCount = 5;
	      SpecGrid.canSel       = 1;
	      SpecGrid.displayTitle = 1;
	      SpecGrid.loadMulLine(iArray); 
      }
      catch(ex)
      {
        alert(ex);
      }
}

// ������Ϣ�б�ĳ�ʼ��
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
	      iArray[1][0]="�������ֺ�";         		//����
	      iArray[1][1]="140px";            		//�п�
	      iArray[1][2]=100;            			//�����ֵ
	      iArray[1][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	
	      iArray[2]=new Array();
	      iArray[2][0]="��ͬ����";         		//����
	      iArray[2][1]="140px";            		//�п�
	      iArray[2][2]=100;            			//�����ֵ
	      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	      iArray[3]=new Array();
	      iArray[3][0]="ӡˢ��";         		//����
	      iArray[3][1]="0px";            		//�п�
	      iArray[3][2]=200;            			//�����ֵ
	      iArray[3][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	      iArray[4]=new Array();
	      iArray[4][0]="���ֱ���";         		//����
	      iArray[4][1]="60px";            		//�п�
	      iArray[4][2]=80;            			//�����ֵ
	      iArray[4][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
	      iArray[4][4]="RiskCode";              	        //�Ƿ����ô���:null||""Ϊ������
	      iArray[4][5]="4";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
	      iArray[4][9]="���ֱ���|code:RiskCode&NOTNULL";
	      iArray[4][18]=250;
	      iArray[4][19]=0 ;
	
	      iArray[5]=new Array();
	      iArray[5][0]="���ְ汾";         		//����
	      iArray[5][1]="60px";            		//�п�
	      iArray[5][2]=100;            			//�����ֵ
	      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	      iArray[6]=new Array();
	      iArray[6][0]="Ͷ����";         		//����
	      iArray[6][1]="80px";            		//�п�
	      iArray[6][2]=100;            			//�����ֵ
	      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	      iArray[7]=new Array();
	      iArray[7][0]="������";         		//����
	      iArray[7][1]="80px";            		//�п�
	      iArray[7][2]=100;            			//�����ֵ
	      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	      
	      iArray[8]=new Array();
	      iArray[8][0]="����";         		    //����
	      iArray[8][1]="0px";            		//�п�
	      iArray[8][2]=100;            			//�����ֵ
	      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	      PolAddGrid = new MulLineEnter( "document" , "PolAddGrid" ); 
	      //��Щ���Ա�����loadMulLineǰ
	      PolAddGrid.mulLineCount      = 5;   
	      PolAddGrid.displayTitle      = 1;
	      PolAddGrid.canSel            = 0;
	      PolAddGrid.hiddenPlus        = 1;
	      PolAddGrid.hiddenSubtraction = 1;
	      PolAddGrid.loadMulLine(iArray);       
	      PolAddGrid.selBoxEventFuncName = "getPolGridCho";
	     
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tPolNo,tContNo,tMissionID, tSubMissionID,tInsuredNo)
{        
  
	document.all('PolNo').value = tPolNo;
	document.all('ContNo').value = tContNo;
	document.all('MissionID').value = tMissionID;
	document.all('SubMissionID').value = tSubMissionID;
	document.all('InsuredNo').value = tInsuredNo;

}

/**=========================================================================
    �޸�״̬����ӡ��ɷ���Ϣ���б�
    �޸�ԭ��
    �� �� �ˣ������
    �޸����ڣ�2005.11.03
   =========================================================================
**/
// ������Ϣ�б�ĳ�ʼ��
function initLCPremGrid()
  {                            
    var iArray = new Array();      
      try
      {
      
	      iArray[0]=new Array();
	      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	      iArray[0][1]="30px";         			//�п�
	      iArray[0][2]=10;          			//�����ֵ
	      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
	
	      iArray[1]=new Array();
	      iArray[1][0]="�ӷ�����";    	        //����
	      iArray[1][1]="60px";            		//�п�
	      iArray[1][2]=100;            			//�����ֵ
	      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
	      
	      iArray[2]=new Array();
	      iArray[2][0]="�ӷ�ԭ��";    	        //����
	      iArray[2][1]="60px";            		//�п�
	      iArray[2][2]=100;            			//�����ֵ
	      iArray[2][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��   
	      
	      iArray[3]=new Array();
	      iArray[3][0]="��ʼ����";         		//����
	      iArray[3][1]="60px";            		//�п�
	      iArray[3][2]=100;            			//�����ֵ
	      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
	
	      iArray[4]=new Array();
	      iArray[4][0]="��ֹ����";         		//����
	      iArray[4][1]="60px";            		//�п�
	      iArray[4][2]=60;            			//�����ֵ
	      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
	
	      iArray[5]=new Array();
	      iArray[5][0]="�ӷ�����";         		//����
	      iArray[5][1]="60px";            		//�п�
	      iArray[5][2]=100;            			//�����ֵ
	      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ�� 
	      
	      iArray[6]=new Array();
	      iArray[6][0]="�ڶ��������˼ӷ�����";         		//����
	      iArray[6][1]="80px";            		//�п�
	      iArray[6][2]=100;            			//�����ֵ
	      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ�� 
	
	          
	      iArray[7]=new Array();
	      iArray[7][0]="�ӷѶ���";         		//����
	      iArray[7][1]="60px";            		//�п�
	      iArray[7][2]=100;            			//�����ֵ
	      iArray[7][3]=0;
	      
	      iArray[8]=new Array();
	      iArray[8][0]="�ӷѽ��";         		//����
	      iArray[8][1]="80px";            		//�п�
	      iArray[8][2]=100;            			//�����ֵ
	      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ�� 
	         
	
	      LCPremGrid = new MulLineEnter( "document" , "LCPremGrid" ); 
	      //��Щ���Ա�����loadMulLineǰ                            
	      LCPremGrid.mulLineCount      = 5;
	      LCPremGrid.canSel            = 0;
	      LCPremGrid.displayTitle      = 1;
	      LCPremGrid.hiddenPlus        = 1;
	      LCPremGrid.hiddenSubtraction = 1 ;
	      LCPremGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

//�ӷѹ켣��Ϣ�б�ĳ�ʼ�� 2006-02-09 Add by zhaorx
function initLLUWPremSubGrid()
{                            
    var iArray = new Array();      
      try
      {      
	      iArray[0]=new Array();
	      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	      iArray[0][1]="30px";         			//�п�
	      iArray[0][2]=10;          			//�����ֵ
	      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
	
	      iArray[1]=new Array();
	      iArray[1][0]="���κ�";    	        //����
	      iArray[1][1]="100px";            		//�п�
	      iArray[1][2]=100;            			//�����ֵ
	      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
	
	      iArray[2]=new Array();
	      iArray[2][0]="�ӷ�����";    	        //����
	      iArray[2][1]="60px";            		//�п�
	      iArray[2][2]=100;            			//�����ֵ
	      iArray[2][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
	      
	      iArray[3]=new Array();
	      iArray[3][0]="�ӷ�ԭ��";    	        //����
	      iArray[3][1]="100px";            		//�п�
	      iArray[3][2]=100;            			//�����ֵ
	      iArray[3][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��   
	      
	      iArray[4]=new Array();
	      iArray[4][0]="��ʼ����";         		//����
	      iArray[4][1]="80px";            		//�п�
	      iArray[4][2]=100;            			//�����ֵ
	      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
	
	      iArray[5]=new Array();
	      iArray[5][0]="��ֹ����";         		//����
	      iArray[5][1]="80px";            		//�п�
	      iArray[5][2]=60;            			//�����ֵ
	      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
	
	      iArray[6]=new Array();
	      iArray[6][0]="�ӷ�����";         		//����
	      iArray[6][1]="60px";            		//�п�
	      iArray[6][2]=100;            			//�����ֵ
	      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ�� 
	      
	      iArray[7]=new Array();
	      iArray[7][0]="�ڶ��������˼ӷ�����";         		//����
	      iArray[7][1]="80px";            		//�п�
	      iArray[7][2]=100;            			//�����ֵ
	      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ�� 
		          
	      iArray[8]=new Array();
	      iArray[8][0]="�ӷѶ���";         		//����
	      iArray[8][1]="80px";            		//�п�
	      iArray[8][2]=100;            			//�����ֵ
	      iArray[8][3]=0;
	      
	      iArray[9]=new Array();
	      iArray[9][0]="�ӷѽ��";         		//����
	      iArray[9][1]="80px";            		//�п�
	      iArray[9][2]=100;            			//�����ֵ
	      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ�� 
	         
	
	      LLUWPremSubGrid = new MulLineEnter( "document" , "LLUWPremSubGrid" ); 
	      //��Щ���Ա�����loadMulLineǰ
	      LLUWPremSubGrid.mulLineCount      = 5;   
	      LLUWPremSubGrid.displayTitle      = 1;
	      LLUWPremSubGrid.hiddenPlus        = 1;
	      LLUWPremSubGrid.hiddenSubtraction = 1;     
	      
	      LLUWPremSubGrid.loadMulLine(iArray);  
	                	      
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>


