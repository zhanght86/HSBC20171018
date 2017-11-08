package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

public class FinDayPub {
private static Logger logger = Logger.getLogger(FinDayPub.class);

	public FinDayPub() {
	}

	public static void main(String[] args) {
		FinDayPub finDayPub1 = new FinDayPub();
	}

	public static String getRiskTypeName(int tIndex) {
		String tsqladd = "";
		switch (tIndex) {
		case 1:
			tsqladd = "终身寿险";
			break;
		case 2:
			tsqladd = "两全险";
			break;
		case 3:
			tsqladd = "定期";
			break;
		case 4:
			tsqladd = "年金";
			break;
		case 5:
			tsqladd = "重大疾病";
			break;
		case 6:
			tsqladd = "意外";
			break;
		case 7:
			tsqladd = "健康";
			break;
		case 8:
			tsqladd = "其他";
			break;
		}
		return tsqladd;
	}

	// 销售渠道
	public static String getXsqdName(int tIndex) {
		String tsqladd = "";
		switch (tIndex) {
		case 1: // 团体险
			tsqladd = "团体险";
			break;
		case 2: // 个人营销
			tsqladd = "个人营销";
			break;
		case 3: // 银行代理
			tsqladd = "银行代理";
			break;
		case 4: // 兼业代理
			tsqladd = "兼业代理";
			break;
		}
		return tsqladd;
	}

	// 险种大类名称
	public static String getXzdlName(int tIndex) {
		String tsqladd = "";
		switch (tIndex) {
		case 1: // 传统险
			tsqladd = "传统险";
			break;
		case 2: // 分红险
			tsqladd = "分红险";
			break;
		case 3: // 投连险
			tsqladd = "投连险";
			break;
		case 4: // 万能险
			tsqladd = "万能险";
			break;
		}
		return tsqladd;
	}

	// 首续期名称
	public static String getSxqName(int tIndex) {
		String tsqladd = "";
		switch (tIndex) {
		case 1: // 首期
			tsqladd = "首期";
			break;
		case 2: // 续期
			tsqladd = "续期";
			break;
		}
		return tsqladd;
	}

	// 趸期交名称
	public static String getDqjName(int tIndex) {
		String tsqladd = "";
		switch (tIndex) {
		case 1: // 趸交
			tsqladd = "趸交";
			break;
		case 2: // 期交
			tsqladd = "期交";
			break;
		}
		return tsqladd;
	}

	// 缴费方式
	public static String getPayModeName(int tIndex) {
		String tsqladd = "";
		switch (tIndex) {
		case 1: // 现金
			tsqladd = "现金";
			break;
		case 2: // 现金支票
			tsqladd = "现金支票";
			break;
		case 3: // 转账支票
			tsqladd = "转账支票";
			break;
		case 4: // 银行转账
			tsqladd = "银行转账";
			break;
		case 5: // 内部转账
			tsqladd = "内部转账";
			break;
		case 6: // 银行托收
			tsqladd = "银行托收";
			break;
		case 7: // 信用卡
			tsqladd = "信用卡";
			break;
		case 8: // 其他
			tsqladd = "其他";
			break;
		}
		return tsqladd;
	}

	public static String getRiskType(String tRiskType4) {
		if (tRiskType4.equals("9")) {
			tRiskType4 = "8";
		}
		return tRiskType4;
	}

	public static String getXzdl(String tRiskType3) {
		if (!tRiskType3.equals("1") && !tRiskType3.equals("2")
				&& tRiskType3.equals("3")) {
			tRiskType3 = "1";
		}
		return tRiskType3;
	}

	public static String getSxq(int tpaycount) {
		String tRet = "";
		if (tpaycount == 1) {
			tRet = "1";
		} else {
			tRet = "2";
		}
		return tRet;
	}

	public static String getDqj(int tPayIntv) {
		String tRet = "";
		if (tPayIntv == 0) {
			tRet = "1"; // 趸交
		} else {
			tRet = "2"; // 期交
		}
		return tRet;
	}
}
