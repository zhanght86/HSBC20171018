<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLUWSpecInit.jsp
//�����ܣ�������Լ�б�
//�������ڣ�2005-11-04 
//������  �������
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
 
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
                       
<%
  String tPrtNo        = "";
  String tContNo       = "";
  String tMissionID    = "";
  String tSubMissionID = "";
  String tClmNo        = "";
  String tBatNo        = "";
  String tProposalNo   = "";
  
  tPrtNo         = request.getParameter("PrtNo");
  tContNo        = request.getParameter("ContNo");
  tMissionID     = request.getParameter("MissionID");
  tSubMissionID  = request.getParameter("SubMissionID");
  tClmNo         = request.getParameter("ClmNo");
  tProposalNo    = request.getParameter("ProposalNo");
  tBatNo         = request.getParameter("BatNo");
  
%>      

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

//��ʼ��ҳ��
function initForm(tContNo,tMissionID,tSubMission,tPrtNo,tClmNo,tProposalNo,tBatNo)
{
  try
  {
	    initHide(tContNo,tMissionID,tSubMission,tPrtNo,tClmNo,tProposalNo,tBatNo);
	    initLLUWSpecGrid();
	    if(tQueryFlag=="1")
	    {
	        fm.button1.style.display="none";
	        fm.button3.style.display="none";
		}
	    QueryPolSpecGrid(tContNo);
	    initLLUWSpecContGrid();
	    getPolGridCho();
  }
  catch(re)
  {
    alert("LLUWSpecInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initLLUWSpecGrid()
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
	      iArray[1][0]="������";         		//����
	      iArray[1][1]="140px";            		//�п�
	      iArray[1][2]=100;            			//�����ֵ
	      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	
	      iArray[2]=new Array();
	      iArray[2][0]="ӡˢ��";         		//����
	      iArray[2][1]="0px";            		//�п�
	      iArray[2][2]=100;            			//�����ֵ
	      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	      iArray[3]=new Array();
	      iArray[3][0]="����Ͷ������";         		//����
	      iArray[3][1]="0px";            		//�п�
	      iArray[3][2]=100;            			//�����ֵ
	      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	      iArray[4]=new Array();
	      iArray[4][0]="���ֱ���";         		//����
	      iArray[4][1]="60px";            		//�п�
	      iArray[4][2]=80;            			//�����ֵ
	      iArray[4][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
	      iArray[4][4]="RiskCode";              	        //�Ƿ����ô���:null||""Ϊ������
	      iArray[4][5]="4";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
	      iArray[4][9]="���ֱ���|code:RiskCode&NOTNULL";
	      iArray[4][18]=250;
	      iArray[4][19]= 0 ;
	
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
	
	      LLUWSpecGrid = new MulLineEnter( "document" , "LLUWSpecGrid" ); 
	      //��Щ���Ա�����loadMulLineǰ
	      LLUWSpecGrid.mulLineCount      = 5;   
	      LLUWSpecGrid.displayTitle      = 1;
	      LLUWSpecGrid.canSel            = 0;
	      LLUWSpecGrid.hiddenPlus        = 1;
	      LLUWSpecGrid.hiddenSubtraction = 1;
	      LLUWSpecGrid.loadMulLine(iArray);       
	     // LLUWSpecGrid.selBoxEventFuncName = "getPolGridCho";
	     
      }
      catch(ex)
      {
        alert(ex);
      }
}


function initLLUWSpecContGrid(){                               
   
   var iArray = new Array();
   try
   {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="Ͷ������";         		//����
      iArray[1][1]="0";            		    //�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="���κ�";         		//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��Լ����";         		//����
      iArray[3][1]="330px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��ʶ";         		    //����
      iArray[4][1]="0";            		    //�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������


      LLUWSpecContGrid = new MulLineEnter( "document" , "LLUWSpecContGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LLUWSpecContGrid.displayTitle         = 5;
      LLUWSpecContGrid.canSel               = 1;
      LLUWSpecContGrid.hiddenPlus           = 1;
      LLUWSpecContGrid.hiddenSubtraction    = 1;
      LLUWSpecContGrid.loadMulLine(iArray);       
      LLUWSpecContGrid.selBoxEventFuncName  = "getSpecGridCho";
   }
   catch(ex)
   {
      alert(ex);
   }
}


function initHide(tContNo,tMissionID,tSubMission,tPrtNo,tClmNo,tProposalNo,tBatNo)
{
	document.all('ContNo').value       = tContNo;
	document.all('MissionID').value    = tMissionID;
	document.all('SubMissionID').value = tSubMission;
	document.all('PrtNo').value        = tPrtNo ;
	document.all('ClmNo').value        = tClmNo;
	document.all('ProposalNo').value   = tProposalNo;
	document.all('BatNo').value        = tBatNo;
}


</script>


