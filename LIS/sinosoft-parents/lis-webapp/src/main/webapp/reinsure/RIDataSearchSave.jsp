<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%
	String FlagStr = "";
	String Content = "";

	//�ڴ����õ���Excel��������Ӧ��sql���ȡ���������Ӧ
	GlobalInput tGlobalInput = new GlobalInput();
	tGlobalInput = (GlobalInput) session.getAttribute("GI");

	String tContNo = request.getParameter("ContNo");
	String tInsuredNo = request.getParameter("InsuredNo");
	String tInsuredName = request.getParameter("InsuredName");
	String tAccumulateDefNO = request.getParameter("AccumulateDefNO");
	String tEventType = request.getParameter("EventType");
	String tBFFlag = request.getParameter("BFFlag");
	String tRIContType = request.getParameter("RIContType");
	String tStartDate = request.getParameter("StartDate");
	String tEndDate = request.getParameter("EndDate");

	ExportExcel.Format format = new ExportExcel.Format();
	ArrayList listCell = new ArrayList();
	ArrayList listLB = new ArrayList();
	ArrayList listColWidth = new ArrayList();
	format.mListCell = listCell;
	format.mListBL = listLB;
	format.mListColWidth = listColWidth;

	ExportExcel.Cell tCell = null;
	ExportExcel.ListBlock tLB = null;

	String tSQL = "";
	listColWidth.add(new String[] { "0", "5000" });
	//String sql = request.getParameter("ExportExcelSQL");

	tLB = new ExportExcel.ListBlock("001");
	tLB.colName = new String[] { ""+"������"+"", ""+"ҵ������"+"",""+"ҵ����ϸ"+"", ""+"�����˱���"+"", ""+"����������"+"",
			""+"�������Ա�"+"", ""+"��ǰ����"+"", ""+"���̱�־"+"", ""+"ְҵ���"+"", ""+"������Ч��"+"", ""+"�������"+"", ""+"�˱����"+"", ""+"���ϼƻ�"+"", ""+"�ٱ�/����"+"", ""+"�ٱ���ͬ����"+"", ""+"�ٱ���������"+"", ""+"�ֳ����α���"+"",
			""+"��׼����"+"",""+"EM�ӷѽ��"+"",""+"EM�ӷ�����"+"",""+"$/M�ӷѽ��"+"",""+"$/M�ӷ�����"+"",""+"%�ӷѽ��"+"",""+"%�ӷ�����"+"",""+"��ǰ����"+"", ""+"���ձ���"+"", ""+"�ۼƱ���"+"", ""+"�ۼƷ��ձ���"+"", ""+"�ֱ���˾����"+"", ""+"���ֱ���"+"",
			""+"�ֱ�����"+"", ""+"�ֱ�����"+"", ""+"�ֱ�����"+"", ""+"�ֱ�����"+"", ""+"�ֱ�����"+"",""+"4�ڱ��ѱ��"+"", ""+"�ֱ�Ӷ����"+"", ""+"�ֱ�Ӷ��1"+"",
			""+"�ֱ�Ӷ��2"+"", ""+"�ֱ�Ӷ��3"+"", ""+"�ֱ�Ӷ��4"+"", ""+"�ֱ�Ӷ�����"+"", ""+"����̯��"+"", ""+"ҵ������"+"", ""+"��������"+"",""+"����״̬"+""};
	//tSQL = "select a.contno,(select codename from ldcode where code=a.eventtype and codetype='rieventtype'),(case when a.feeoperationtype='SX' then 'ʧЧ' when a.accumulatedefno='L01010060010' then (SELECT distinct duty.getdutyname FROM riaccumulategetduty duty WHERE a.dutycode=duty.getdutycode) when a.eventtype in ('01','02') then '����' when a.eventtype in ('04') then '�����' when a.eventtype in ('03') then (select edorname from lmedoritem where edorcode=a.feeoperationtype and appobj='I') end),a.insuredno,a.insuredname,"
			//+"(select codename from ldcode where code=a.Insuredsex and codetype='sex'),a.currentage,(SELECT codename FROM ldcode where code=a.smokeflag and codetype='smoke'),a.cvalidate,a.insuredyear,(select codename from ldcode where code=a.GetStartType and codetype='uwtype3'),a.plantype,(select codename from ldcode where code=(select getdutytype from riaccumulatedef riacc where riacc.accumulatedefno=a.accumulatedefno) and codetype='riacctype')��a.RIContNo,a.RIPreceptNo,a.accumulatedefno,"
			//+"a.standprem,a.ExtPrem1,a.ExtRate1||'%',a.ExtPrem2,a.ExtRate2,a.ExtPrem3,a.ExtRate3*100||'%',a.currency,riskamnt,a.acccurrency,a.accamnt,b.incomecompanyno,b.riskcode,"
			//+"b.AreaID,b.CessionRate,b.CessionAmount,c.paramdouble1,b.PremChang,c.paramdouble2,b.CommChang,"
			//+"decode(a.accumulatedefno,'L01010060010',nvl(b.Standbydouble2,0),nvl((select sum(pb.FeeMoney) from Ripolrecordbake pb where pb.Insuredyear = (select max(pb.Insuredyear) from Ripolrecordbake pc where pb.Polno = pc.Polno) and a.Eventno=pb.Eventno and a.ContNo=pb.ContNo and a.RiskCode=pb.Riskcode), 0)),nvl(b.Standbydouble3,0),nvl(b.Standbydouble4,0),b.CommChang+decode(a.accumulatedefno,'L01010060010',nvl(b.Standbydouble2,0),nvl((select sum(pb.FeeMoney)from Ripolrecordbake pb where pb.Insuredyear = (select max(pb.Insuredyear) from Ripolrecordbake pc where pb.Polno = pc.Polno) and a.Eventno=pb.Eventno and a.ContNo=pb.ContNo and a.RiskCode=pb.Riskcode), 0))+nvl(b.Standbydouble3,0)+nvl(b.Standbydouble4,0),b.ReturnPay,b.RIDate,a.makedate,(case when(a.getdate>=(select lockdate from RIBsnsBillLock l where l.accumulatedefno=a.accumulatedefno and l.serial=(select max(lb.serial) from RIBsnsBillLock lb where lb.accumulatedefno=l.accumulatedefno))) then '������' else 'δ����' end) "
			//+"from ripolrecordbake a, RIRecordTrace b, ricalparam c where a.eventno = b.eventno and b.serialno = c.serialno";
	tSQL = "select a.contno,(select codename from ldcode where code=a.eventtype and codetype='rieventtype'),(case when a.feeoperationtype='SX' then '"+"ʧЧ"+"' when a.feeoperationtype='JYFX' then '"+"���׸�Ч"+"' when a.accumulatedefno='L01010060010' then (SELECT distinct duty.getdutyname FROM riaccumulategetduty duty WHERE a.dutycode=duty.getdutycode) when a.eventtype in ('01','02') then '"+"����"+"' when a.eventtype in ('04') then '"+"�����"+"' when a.eventtype in ('03') then (select edorname from lmedoritem where edorcode=a.feeoperationtype and appobj='I') end),a.insuredno,(nvl((select x.Nameen from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'NONAME')),"
			+"(select codename from ldcode where code = decode((select x.sex from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'0','0','1') and codetype = 'sex'),(SELECT x1.insuredappage FROM lcpol x1 where x1.polno=a.polno)+ceil(months_between(date'"+PubFun.getCurrentDate()+"', a.cvalidate - 1) / 12)-1,(select codename from ldcode where code = nvl((select x.Smokeflag from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'N') and codetype = 'smoke'),(SELECT x1.Occupationlevel FROM lcpol x1 where x1.polno=a.polno),a.cvalidate,ceil(months_between(date'"+PubFun.getCurrentDate()+"', a.cvalidate - 1) / 12),(select codename from ldcode where code=a.GetStartType and codetype='uwtype3'),a.plantype,(select codename from ldcode where code=(select getdutytype from riaccumulatedef riacc where riacc.accumulatedefno=a.accumulatedefno) and codetype='riacctype'),a.RIContNo,a.RIPreceptNo,a.accumulatedefno,"
			+"(SELECT dd.standprem FROM lcduty dd where dd.polno=a.polno and dd.dutycode=a.dutycode),a.ExtPrem1,a.ExtRate1||'%',a.ExtPrem2,a.ExtRate2,a.ExtPrem3,a.ExtRate3*100||'%',a.currency,a.riskamnt,a.acccurrency,a.accamnt,b.incomecompanyno,b.riskcode,"
			+"b.AreaID,b.CessionRate,b.CessionAmount,c.paramdouble1,b.PremChang,(case when a.ripreceptno <> 'C01010040020' then '"+"������"+"' else decode(a.standbydouble1, 1,'"+"�ѳ���"+"' ,'"+"δ����"+"') end),c.paramdouble2,b.CommChang,"
			+"decode(a.accumulatedefno,'L01010060010',nvl(b.Standbydouble2,0),nvl((select sum(pb.FeeMoney) from Ripolrecordbake pb where pb.Insuredyear = (select max(pb.Insuredyear) from Ripolrecordbake pc where pb.Polno = pc.Polno) and a.Eventno=pb.Eventno and a.ContNo=pb.ContNo and a.RiskCode=pb.Riskcode), 0)),nvl(b.Standbydouble3,0),nvl(b.Standbydouble4,0),b.CommChang+decode(a.accumulatedefno,'L01010060010',nvl(b.Standbydouble2,0),nvl((select sum(pb.FeeMoney)from Ripolrecordbake pb where pb.Insuredyear = (select max(pb.Insuredyear) from Ripolrecordbake pc where pb.Polno = pc.Polno) and a.Eventno=pb.Eventno and a.ContNo=pb.ContNo and a.RiskCode=pb.Riskcode), 0))+nvl(b.Standbydouble3,0)+nvl(b.Standbydouble4,0),b.ReturnPay,b.RIDate,a.makedate,(case when(a.getdate>=(select lockdate from RIBsnsBillLock l where l.accumulatedefno=a.accumulatedefno and l.serial=(select max(lb.serial) from RIBsnsBillLock lb where lb.accumulatedefno=l.accumulatedefno))) then '"+"������"+"' else '"+"δ����"+"' end) "
			+"from ripolrecordbake a, RIRecordTrace b, ricalparam c where a.eventno = b.eventno and b.serialno = c.serialno";
	if (tInsuredNo != null && !"".equals(tInsuredNo)) {
		tSQL = tSQL + " and a.insuredno = '" + tInsuredNo + "' ";
	}
	if (tInsuredName != null && !"".equals(tInsuredName)) {
		tSQL = tSQL + " and a.insuredname like '%" + tInsuredName
				+ "%' ";
	}
	if (tAccumulateDefNO != null && !"".equals(tAccumulateDefNO)) {
		tSQL = tSQL + " and a.accumulatedefno = '" + tAccumulateDefNO
				+ "' ";
	}
	if (tContNo != null && !"".equals(tContNo)) {
		tSQL = tSQL + " and a.RIContNo='" + tContNo + "' ";
	}
	if (tEventType != null && !"".equals(tEventType)) {
		tSQL = tSQL + " and a.EventType = '" + tEventType + "' ";
	}
	if (tBFFlag != null && !"".equals(tBFFlag)) {
		tSQL = tSQL + " and (a.BFFlag='" + tBFFlag
				+ "' or a.BFFlag is null)";
	}
	if (tRIContType != null && !"".equals(tRIContType)) {
		tSQL = tSQL
				+ " and exists (select 1 from ribargaininfo b where a.ricontno=b.ricontno and b.conttype='"
				+ tRIContType + "')";
	}
	if (tStartDate != null && !"".equals(tStartDate)) {
		tSQL = tSQL + " and  ((a.getdate>='" + tStartDate + "' and a.bfflag is  null) or ((SELECT t.startdate FROM riwflog t WHERE t.batchno=a.batchno and a.accumulatedefno=t.taskcode ) >='" + tStartDate + "' and a.bfflag is not null))  ";
	}
	if (tEndDate != null && !"".equals(tEndDate)) {
		tSQL = tSQL + " and ((a.getdate<='" + tEndDate + "' and a.bfflag is  null) or ((SELECT m.enddate FROM riwflog m WHERE m.batchno=a.batchno and a.accumulatedefno=m.taskcode ) <='" + tEndDate + "' and a.bfflag is not null)) ";
	}

	tSQL = tSQL + " and rownum<=30000 order by accumulatedefno,insuredno,GetDate,MakeDate";
	tLB.sql = tSQL;
	tLB.row1 = 0;
	tLB.col1 = 0;
	tLB.InitData();
	listLB.add(tLB);

	try {
		response.reset();
		response.setContentType("application/octet-stream");

		//���õ�����xls�ļ���Ĭ��ֵ
		//String HeaderParam = "\""+"attachment;filename="+"000001"+".xls"+"\"";
		response.setHeader("Content-Disposition", "attachment; filename=RIDataDetail.xls");

		OutputStream outOS = response.getOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(outOS);
		ExportExcel excel = new ExportExcel();
		excel.write(format, bos);
		out.clear();
		out = pageContext.pushBody();
		bos.flush();
		bos.close();
	} catch (Exception e) {
		Content = ""+"����Excelʧ��!"+"";
		FlagStr = "Fail";
	}
	if (!FlagStr.equals("Fail")) {
		Content = " "+"����Excel�ɹ�!"+" ";
		FlagStr = "Succ";
	}
%>
