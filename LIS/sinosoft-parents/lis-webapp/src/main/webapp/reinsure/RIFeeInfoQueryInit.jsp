<%@include file="/i18n/language.jsp"%>

<%
	//�������ƣ�ClientConjoinQueryInit.jsp
	//�����ܣ�
	//�������ڣ�2002-08-19
	//������  ��Kevin
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//���ҳ��ؼ��ĳ�ʼ����
%>

<script type="text/javascript">
	var sqlresourcename = "reinsure.RIFeeInfoQuerySql";
	function initInpBox() {
		try {
		} catch (ex) {
			myAlert("��FeeInfoQueryInit.jsp-->"+"InitInpBox�����з����쳣:��ʼ���������!");
		}
	}

	function initForm() {
		try {
			initInpBox();
		} catch (re) {
			myAlert("FeeInfoQueryInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
		}
	}
	function initFeeGrid() {
		try {
			var feeno = fm.QureyFeeCode.value;
			var iArray = new Array();
			var mySql = new SqlClass();
			mySql.setResourceName(sqlresourcename);
			mySql.setSqlId("RIFeeInfoQuerySql3");// ָ��ʹ�õ�Sql��id
			mySql.addSubPara(feeno);// ָ������Ĳ������������˳�����
			var strSQL = mySql.getString();
			var result = easyExecSql(strSQL, 1, 0, 1);

			var i = 0;
			iArray[0] = new Array();
			iArray[0][0] = "���"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[0][1] = "30px"; //�п�
			iArray[0][2] = 10; //�����ֵ
			iArray[0][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������
			var n = 0;
			for (i = 0; i < result.length; i++) {
				colname = result[i][0];
				n = i + 1;
				iArray[n] = new Array();
				iArray[n][0] = colname; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
				iArray[n][1] = "50px"; //�п�
				iArray[n][2] = 10; //�����ֵ
				iArray[n][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������		
			}
			n = n + 1;
			iArray[n] = new Array();
			iArray[n][0] = "������ֵ"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[n][1] = "50px"; //�п�
			iArray[n][2] = 10; //�����ֵ
			iArray[n][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������	

			FeeGrid = new MulLineEnter("fm", "FeeGrid"); //��Щ���Ա�����loadMulLineǰ
			FeeGrid.mulLineCount = 0;
			FeeGrid.displayTitle = 1;
			FeeGrid.hiddenPlus = 1;
			FeeGrid.hiddenSubtraction = 1;
			FeeGrid.canSel = 1;
			FeeGrid.loadMulLine(iArray);

		} catch (ex) {
			myAlert(ex);
		}
	}
</script>
