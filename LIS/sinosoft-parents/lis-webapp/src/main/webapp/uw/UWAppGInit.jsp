<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWAppGInit.jsp
//�����ܣ�����Ͷ����Ϣ��ѯ
//�������ڣ�2002-06-19 11:10:36
//������  �� WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
<%
  String tProposalNo = "";
  String tInsureNo = "";
  String tContNo = "";
  tProposalNo = request.getParameter("ProposalNo2");
  tInsureNo = request.getParameter("InsureNo2");
  tContNo  = request.getParameter("ContNo");
  loggerDebug("UWAppGInit","ContNo:"+tContNo);
%>                            

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

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
    alert("��UWAppGInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm(tProposalNo,tInsureNo,tContNo)
{
  try
  {
  	
	initPolGrid();
	initHide(tProposalNo,tInsureNo,tContNo);
	easyQueryClick();
  }
  catch(re)
  {
    alert("UWAppGInit.jsp-->InitForm�����з����쳣:��ʼ���������!"+re);
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
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="������";    	//����
      iArray[1][1]="160px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      iArray[2]=new Array();
      iArray[2][0]="Ͷ������";         			//����
      iArray[2][1]="160px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="ӡˢ��";         			//����
      iArray[3][1]="120px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="������Ч����";         			//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��������";         		//����
      iArray[5][1]="260px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      //iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      //iArray[5][4]="RiskCode";              	        //�Ƿ����ô���:null||""Ϊ������
      //iArray[5][5]="4";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      //iArray[5][9]="���ֱ���|code:RiskCode&NOTNULL";
      //iArray[5][18]=250;
      //iArray[5][19]= 0 ;

      iArray[6]=new Array();
      iArray[6][0]="����";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="����";         		//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="����״̬";         		//����
      iArray[8][1]="60px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[8][4]="PolState";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[8][5]="11";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[8][9]="����״̬|code:PolState&NOTNULL";
      iArray[8][18]=150;
      iArray[8][19]= 0 ;  

      iArray[9]=new Array();
      iArray[9][0]="ְҵ���";         		//����
      iArray[9][1]="60px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[9][4]="OccupationType";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[9][5]="9";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[9][9]="ְҵ���|code:OccupationType&NOTNULL";
      iArray[9][18]=100;
      iArray[9][19]= 0 ;

      iArray[10]=new Array();
      iArray[10][0]="�Ƿ�����";         		//����
      iArray[10][1]="60px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[10][10] = "Health";
      iArray[10][11] = "0|^0|û������^1|������,δ��ӡ^2|������,�Ѵ�ӡ,��δ�ظ�^3|������,�Ѵ�ӡ,��δ�ظ�";
      iArray[10][12] = "10";
      iArray[10][19] = "0";

      iArray[11]=new Array();
      iArray[11][0]="�Ƿ������б�";         		//����
      iArray[11][1]="60px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[11][10] = "Condition";
      iArray[11][11] = "0|^0|û�������б�^1|�������б�,δ�ظ�^2|�������б�,�ѻظ�";
      iArray[11][12] = "11";
      iArray[11][19] = "0";
      

      iArray[12]=new Array();
      iArray[12][0]="�˱�����";         		//����
      iArray[12][1]="60px";            		//�п�
      iArray[12][2]=100;            			//�����ֵ
      iArray[12][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[12][10] = "End";
      iArray[12][11] = "0|^0|δ�˱�^1|�ܱ�^2|����^3|�����б�^4|ͨ�ڳб�^5|�Զ��˱�^6|���ϼ��˱�^8|���˱�֪ͨ��^9|�����б�^a|����Ͷ����^b|���ռƻ����^z|�˱�����";
      iArray[12][12] = "12";
      iArray[12][19] = "0";

      iArray[13]=new Array();
      iArray[13][0]="�������";         		//����
      iArray[13][1]="60px";            		//�п�
      iArray[13][2]=100;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 10;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.loadMulLine(iArray);
      
      PolGrid. selBoxEventFuncName = "ChoClick";
      
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tProposalNo,tInsureNo,tContNo)
{
	document.all('ContNo').value= tContNo;
	document.all('ProposalNoHide').value= tProposalNo;
	document.all('InsureNoHide').value= tInsureNo;
	//alert("pol:"+tProposalNo);
}

</script>
