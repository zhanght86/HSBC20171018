<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�EdorUWErrInit.jsp
//�����ܣ��˹��˱�δ��ԭ���ѯ
//�������ڣ�2005-06-23 15:10:36
//������  ��liurongxiao
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
  String tEdorNo = "";
  tContNo = request.getParameter("ContNo");
  tEdorNo = request.getParameter("EdorNo");
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
    alert("��EdorUWErrInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm(tContNo, tEdorNo)
{
  try
  {
	
	initUWErrGrid();
	
	initHide(tContNo,tEdorNo);
	
	easyQueryClick();
	
  }
  catch(re)
  {
    alert("EdorUWErrInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initUWErrGrid()
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
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="������";    	//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�˱�˳���";         			//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�˱����";         		//����
      iArray[4][1]="340px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�˱�����";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      UWErrGrid = new MulLineEnter( "fm" , "UWErrGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      UWErrGrid.mulLineCount = 10;   
      UWErrGrid.displayTitle = 1;
      UWErrGrid.locked = 1;
      UWErrGrid.loadMulLine(iArray);  
      }
      
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tContNo, tEdorNo)
{   
    
	document.all('ContNo').value=tContNo;
	document.all('EdorNo').value=tEdorNo;
}

</script>


