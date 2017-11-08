/**
 * 
 */
package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;


public class GrpZTChargeRate {
private static Logger logger = Logger.getLogger(GrpZTChargeRate.class);
	
	
	public static double getZTChargeRate(String riskCode, int intv) {
		
		double tManageRate = -10;
		
		if("212401".equals(riskCode)){
			/*MS众悦团体年金保险帐户退保收费比率*/
			switch(intv){
			case 0 ://0-1年
				return 0.95;
			case 1 ://1-2年
				return 0.96;
			case 2 ://2-3年
				return 0.97;
			case 3 ://3-4年
				return 0.99;
			case 4 ://4年及4年以上
				return 1;
			default:
				return 1;
			}
		}else if("212402".equals(riskCode)){
			/*MS众鑫团体年金保险帐户退保收费比率*/
			switch(intv){
			case 0 ://0-1年
				return 0.96;
			case 1 ://1-2年
				return 0.97;
			case 2 ://2-3年
				return 0.98;
			case 3 ://3-4年
				return 0.99;
			case 4 ://4年及4年以上
				return 1;
			default:
				return 1;
			}
		}else if("000000".equals(riskCode)){
			/*一般短期险退保手续费用比率*/
			switch(intv){
			case 0 ://0个月
				return 0;
			case 1 ://0-1个月
				return 0.3;
			case 2 ://1-2个月
				return 0.4;
			case 3 ://2-3个月
				return 0.5;
			case 4 ://3-4个月
				return 0.6;
			case 5 ://4-5个月
				return 0.65;
			case 6 ://5-6个月
				return 0.7;
			case 7 ://6-7个月
				return 0.75;
			case 8 ://7-8个月
				return 0.8;
			case 9 ://8-9个月
				return 0.85;
			case 10 ://9-10个月
				return 0.90;
			case 11 ://10-11个月
				return 0.95;
			case 12 ://11-12个月
				return 1;
			default:
				return 1;
			}
		}else if("311603".equals(riskCode)){
			/*一般短期险退保手续费用比率*/
			switch(intv){
			case 0 ://0个月
				return 0.4;
			case 1 ://0-1个月
				return 0.4;
			case 2 ://1-2个月
				return 0.4;
			case 3 ://2-3个月
				return 0.5;
			case 4 ://3-4个月
				return 0.6;
			case 5 ://4-5个月
				return 0.7;
			case 6 ://5-6个月
				return 0.75;
			case 7 ://6-7个月
				return 1;
			case 8 ://7-8个月
				return 1;
			case 9 ://8-9个月
				return 1;
			case 10 ://9-10个月
				return 1;
			case 11 ://10-11个月
				return 1;
			case 12 ://11-12个月
				return 1;
			default:
				return 1;
			}
		} else if("211901".equals(riskCode)){
			/*MS康福健康保障委托管理产品*/
			return 1;
		}
		return tManageRate;
	}
	
	public static void main(String[] args){
		
		for(int i = 0 ;i<=12;i++){
		
		logger.debug(i+"个月的手费费收取比例"+GrpZTChargeRate.getZTChargeRate("000000", i));
		}
	}
	

}
