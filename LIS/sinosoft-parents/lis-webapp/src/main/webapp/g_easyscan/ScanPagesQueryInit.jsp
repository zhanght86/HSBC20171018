<%
/***************************************************************
 * <p>ProName��ScanPagesQueryInit.jsp</p>
 * <p>Title��Ӱ���ѯ</p>
 * <p>Description��Ӱ���ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
%>
<script language="JavaScript">
	
function initForm() {

	try {
		initScanPagesGrid();
		divPages.style.display = 'none';
		divPages1.style.display = 'none';
		scanQuery();
	} catch(ex) {
		alert("��ʼ���������!");
	}
}

function initScanPagesGrid() {
	try {
		var iArray = new Array();
		var i=0;
		iArray[i]=new Array();
		iArray[i][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[i][1]="40px";         			//�п�
		iArray[i][2]=10;          			    //�����ֵ

		iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

		
		iArray[i]=new Array();
		iArray[i][0]="ҵ�����";         			//����
		iArray[i][1]="120px";            		//�п�
		iArray[i][2]=80;            			//�����ֵ

		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="ҵ�����ͱ���";         		//����
		iArray[i][1]="80px";            		//�п�
		iArray[i][2]=90;            			//�����ֵ

		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="ҵ������";         		//����
		iArray[i][1]="100px";            		//�п�
		iArray[i][2]=90;            			//�����ֵ

		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��֤���ͱ���";    	        //����
		iArray[i][1]="80px";            		//�п�
		iArray[i][2]=90;            			//�����ֵ

		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��֤����";    	        //����
		iArray[i][1]="150px";            		//�п�
		iArray[i][2]=90;            			//�����ֵ

		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��֤��";    	        //����
		iArray[i][1]="0px";            		//�п�
		iArray[i][2]=90;            			//�����ֵ

		iArray[i++][3]=3;
		
		ScanPagesGrid = new MulLineEnter("fm","ScanPagesGrid");
		ScanPagesGrid.mulLineCount = 0;
		ScanPagesGrid.displayTitle = 1;
		ScanPagesGrid.locked = 0;
		ScanPagesGrid.canSel = 1;                   // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
		ScanPagesGrid.selBoxEventFuncName = "showPages"; //��Ӧ�ĺ���������������

		ScanPagesGrid.hiddenPlus=1;        //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		ScanPagesGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		ScanPagesGrid.loadMulLine(iArray);
	
	} catch(ex) {
		alert(ex);
	}  
}

</script>
