<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuGrpSpecInit.jsp
//�����ܣ��˹��˱������б�
//�������ڣ�2007-06-15 11:10:36
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
                       
<%
  String tGrpContNo = "";
  tGrpContNo = request.getParameter("GrpContNo");
%>      

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

var str = "";

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
try
  {           
        fm.GrpContNo.value="<%=tGrpContNo%>";                        
  }
  catch(ex)
  {
    alert("��UWManuGrpSpecInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }   
}
                                      


function initForm()
{
  try
  {
    initInpBox();
    initUWSpecGrid();
    QueryGrpSpecGrid();
    //getGrpSpec();
  }
  catch(re)
  {
    alert("UWManuGrpSpecInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initUWSpecGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="0px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�����ͬ��";         		//����
      iArray[1][1]="140px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[2]=new Array();
      iArray[2][0]="ӡˢ��";         		//����
      iArray[2][1]="140px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[3]=new Array();
      iArray[3][0]="Ͷ����λ";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      


      UWSpecGrid = new MulLineEnter("fm" ,"UWSpecGrid"); 
      //��Щ���Ա�����loadMulLineǰ
      UWSpecGrid.mulLineCount = 3;   
      UWSpecGrid.displayTitle = 1;
      //PolAddGrid.locked = 1;
      UWSpecGrid.canSel = 0;
      UWSpecGrid.hiddenPlus = 1;
      UWSpecGrid. hiddenSubtraction = 1;
      UWSpecGrid.loadMulLine(iArray);       
      UWSpecGrid.selBoxEventFuncName = "getGrpSpec";
     
      //��Щ����������loadMulLine����
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>


