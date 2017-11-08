package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.PubFun;

public class VerifyIdCard {   
private static Logger logger = Logger.getLogger(VerifyIdCard.class);
    // wi =2(n-1)(mod 11);加权因子   
    final int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };   
    // 校验码   
    final int[] vi = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 };   
    private int[] ai = new int[18];   
  
    public VerifyIdCard() {   
    }   
  
    // 校验身份证的校验码   
    public boolean verify(String idNo) throws Exception{   
        if (idNo.length() == 15) {   
        	idNo = uptoeighteen(idNo);   
        }   
        if (idNo.length() != 18) {   
            return false;   
        }   
        String verify = idNo.substring(17, 18);   
        if (verify.equals(getVerify(idNo))) {   
            return true;   
        }   
        return false;   
    }   
  
    // 15位转18位   
    public String uptoeighteen(String fifteen) {   
          
        return PubFun.TransID(fifteen);   
    }   
  
    // 计算最后一位校验值   
    public String getVerify(String eighteen) throws Exception{   
        int remain = 0;   
        if (eighteen.length() == 18) {   
            eighteen = eighteen.substring(0, 17);   
        }   
        if (eighteen.length() == 17) {   
            int sum = 0;   
            for (int i = 0; i < 17; i++) {   
                String k = eighteen.substring(i, i + 1);   
                ai[i] = Integer.parseInt(k);   
            }   
            for (int i = 0; i < 17; i++) {   
                sum += wi[i] * ai[i];   
            }   
            remain = sum % 11;   
        }   
        return remain == 2 ? "X" : String.valueOf(vi[remain]);   
  
    }
    
    public boolean chkIdNo(String iIdNo, String iBirthday ,String iSex) throws Exception{

    	String tBirthDay = PubFun.getBirthdayFromId(iIdNo);
    	String tSex = PubFun.getSexFromId(iIdNo);
    	if(!iBirthday.equals(tBirthDay)){
    		throw new Exception("身份证与出生日期校验不一致");
    	}
    	if(!iSex.equals(tSex)){
    		throw new Exception("身份证与性别校验不一致");
    	}
    	return true;
    }
    
    public static void main(String[] args){
    	
    	VerifyIdCard vc = new VerifyIdCard();
    	String id = "110108197312279712";
    	boolean tret = false;
    	try {
			tret = vc.verify(id);
		} catch (Exception e) {
			
		}
		if(tret){
			logger.debug("合法身份证!");
		}
    }
}  
