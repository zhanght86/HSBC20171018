<%
//�������ƣ�UWReportInit.jsp
//�����ܣ�
//�������ڣ�2008-10-15 
//������  ��ln
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">                                      

function initForm()
{
  try
  {    
    if(sQueryFlag == null || sQueryFlag == '')
    {}
    else if(sQueryFlag == '1') //��ѯ
    	fm.Add.disabled = true;
    	
	initParam();
	initReportGrid();
	initQuery();
  }
  catch(re)
  {
    alert("UWReportInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initReportGrid() {                              
  var iArray = new Array();
    
  try  {
    iArray[0]=new Array();
    iArray[0][0]="���";         			 //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          			    //�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="Ͷ������";    	     //����
    iArray[1][1]="90px";            		//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="�˱�������������";         			//����
    iArray[2][1]="280px";            		//�п�
    iArray[2][2]=800;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="¼��Ա";         			//����
    iArray[3][1]="50px";            		//�п�
    iArray[3][2]=40;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
                             
    iArray[4]=new Array();
    iArray[4][0]="¼������";         			//����
    iArray[4][1]="80px";            		//�п�
    iArray[4][2]=40;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[5]=new Array();
    iArray[5][0]="¼��ʱ��";         			//����
    iArray[5][1]="80px";            		//�п�
    iArray[5][2]=50;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    ReportGrid = new MulLineEnter( "fm" , "ReportGrid" ); 
    //��Щ���Ա�����loadMulLineǰ                         
    ReportGrid.mulLineCount = 0;
    ReportGrid.displayTitle = 1;
    ReportGrid.canSel = 1;
    ReportGrid.hiddenPlus = 1;
    ReportGrid.hiddenSubtraction = 1;
    ReportGrid.loadMulLine(iArray); 
    ReportGrid.selBoxEventFuncName = "showContent";
 
  }
  catch(ex) {
    alert(ex);
  }
}
</script>
