<%
//Creator :ln
//Date :2008-06-30
%>
<!--�û�У����-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.db.*"%>
<%@page import = "com.sinosoft.lis.vdb.*"%>
<%@page import = "com.sinosoft.lis.bl.*"%>
<%@page import = "com.sinosoft.lis.vbl.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
%>
<script language="JavaScript">
function init()
{
  try
  {
    
    document.all('checkRate').value = '';
    document.all('checkMax').value = '';
    document.all('BussNo').value = '';
    document.all('ManageCom').value = '';

  }
  catch(ex)
  {
    alert("ProposalSpotCheckInit.jsp����()��ʼ��ʧ��");
  }
}
;

//function RegisterDetailClick(cObj)
//{
  	//var ex,ey;
  	//ex = window.event.clientX+document.body.scrollLeft;  //�õ��¼�������x
  	//ey = window.event.clientY+document.body.scrollTop;   //�õ��¼�������y
  	//divDetailInfo.style.left=ex;
  	//divDetailInfo.style.top =ey;
    //divDetailInfo.style.display ='';
//}

var CheckGrid;
// ������Ϣ�б�ĳ�ʼ��
function initCheckGrid() {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�����˾";         		//����
      iArray[1][1]="80px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][4]="queryBPOID";              	//�Ƿ����ô���:null||""Ϊ������

      iArray[2]=new Array();
      iArray[2][0]="�������";         		//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][4]="comcode";              	//�Ƿ����ô���:null||""Ϊ������

      iArray[3]=new Array();
      iArray[3][0]="������";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�������";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��ע";         		//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      CheckGrid = new MulLineEnter( "fm" , "CheckGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      CheckGrid.mulLineCount = 0;   
      CheckGrid.displayTitle = 1;
      CheckGrid.locked = 0;
      CheckGrid.canSel = 1;
      CheckGrid.hiddenPlus = 0;
      CheckGrid.hiddenSubtraction = 1;
      CheckGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initForm()
{
  try
  {
    init();
  //  initCheckGrid();
  }
  catch(re)
  {
    alert("ProposalSpotCheckInit.jsp����InitForm()��ʼ��ʧ��");
  }
}

</script>


