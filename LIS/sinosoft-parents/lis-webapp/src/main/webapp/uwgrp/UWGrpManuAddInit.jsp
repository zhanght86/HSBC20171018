<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWGrpManuAddInit.jsp
//�����ܣ��˹��˱������б�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
<%
  String tContNo = "";
  String tPolNo = "";
tContNo = request.getParameter("ContNo");
tPolNo = request.getParameter("PolNo");
%>                            

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

var str = "";

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
try
  {                                   
	fm.all('AddReason').value = '';
   }
  catch(ex)
  {
    alert("��PEdorUWGrpManuAddInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }   
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=��&1=Ů&2=����");      
//    setOption("sex","0=��&1=Ů&2=����");        
//    setOption("reduce_flag","0=����״̬&1=�����");
//    setOption("pad_flag","0=����&1=�潻");   
  }
  catch(ex)
  {
    alert("��PEdorUWGrpManuAddInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm(tPolNo,tContNo)
{
  var str = "";
  try
  {
	initInpBox();
	initPolAddGrid();
	initHide(tPolNo,tContNo);
	QueryPolAddGrid(tPolNo, tContNo);	
 }
  catch(re)
  {
   alert("��PEdorUWGrpManuAddInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
// ������Ϣ�б�ĳ�ʼ��
function initUWSpecGrid(str)
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
      iArray[1][0]="������Ŀ";    	        //����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=2;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
      iArray[1][10] = "DutyCode";             			
      iArray[1][11] = str;
      iArray[1][12] = "1|3|4";
      iArray[1][13] = "0|1|2";
      iArray[1][19] = 1;	

      iArray[2]=new Array();
      iArray[2][0]="������Ŀ";    	        //����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=2;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
      iArray[2][10] = "PlanPay";             			
      iArray[2][11] = "0|^01|�����ӷ�^02|ְҵ�ӷ�";
      iArray[2][12] = "2";
      iArray[2][13] = "0";
      iArray[2][19]= 1 ;
             
      iArray[3]=new Array();
      iArray[3][0]="��ʼ����";         		//����
      iArray[3][1]="120px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

      iArray[4]=new Array();
      iArray[4][0]="��ֹ����";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

      iArray[5]=new Array();
      iArray[5][0]="�ӷѽ��";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
      
      iArray[6]=new Array();
      iArray[6][0]="�����������";         		//����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��     
         


      SpecGrid = new MulLineEnter( "fm" , "SpecGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      SpecGrid.mulLineCount = 1;
      SpecGrid.displayTitle = 1;
      SpecGrid.loadMulLine(iArray);  
       //��Щ����������loadMulLine����
      //SpecGrid.setRowColData(1,1,"asdf");
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
      iArray[1][0]="Ͷ������";         		//����
      iArray[1][1]="140px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[2]=new Array();
      iArray[2][0]="���ձ�����";         		//����
      iArray[2][1]="140px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="ӡˢ��";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
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

      PolAddGrid = new MulLineEnter( "fm" , "PolAddGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolAddGrid.mulLineCount = 3;   
      PolAddGrid.displayTitle = 1;
      //PolAddGrid.locked = 1;
      PolAddGrid.canSel = 1;
      PolAddGrid.hiddenPlus = 1;
      PolAddGrid.loadMulLine(iArray);       
      PolAddGrid.selBoxEventFuncName = "getPolGridCho";
     
      //��Щ����������loadMulLine����
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tPolNo,tContNo,tMissionID, tSubMissionID)
{        
	fm.all('PolNo').value = tPolNo;
	fm.all('ContNo').value = tContNo;
	fm.all('MissionID').value = tMissionID;
	fm.all('SubMissionID').value = tSubMissionID;
}

</script>


