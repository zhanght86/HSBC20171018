
package com.sinosoft.lis.easyscan;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import com.sinosoft.lis.easyscan.cloud.CloudObjectStorageFactory;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.LisImageShow;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.ES_DOC_ScanNoSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;
/**
 * <p>Title: Web业务系统</p>
 * <p>Description: EasyScan从中心请求获取扫描批次号</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author wellhi
 * @version 1.0
 * 
 * @version 1.1 2009-5-20 陶宇
 * 增加批次号码申请表
 * 用户申请完批次号后记录在批次号码申请表中,只有申请过的批次号使用之后,才可以继续申请
 */

public class LisImageShowBL {
private static Logger logger = Logger.getLogger(LisImageShowBL.class);
  //错误处理类，每个需要错误处理的类中都放置该类
  public CErrors mErrors = new CErrors();
  private VData mInputData;
  private VData mResult = new VData();
  private String img_url = "";
  public LisImageShowBL() {
  }

  //入参处理
  private boolean getInputData() {
    GlobalInput nGlobalInput = (GlobalInput) mInputData.get(0);
    img_url = (String) mInputData.get(1);
  
    return true;
  }

  //传输数据的公共方法
  public boolean submitData(VData cInputData, String cOperate) {
    boolean tReturn = true;
    //首先将数据在本类中做一个备份
    mInputData = (VData) cInputData.clone();
    //进行业务处理
    if (!getInputData()) {
      return false;
    }
    if (!dealData()) {
      tReturn = false;
    }
    return tReturn;
  }

  //根据前面的输入数据，进行逻辑处理
  //如果在处理过程中出错，则返回false,否则返回true
  private boolean dealData() {
	  InputStream fis = null;
	  com.sinosoft.lis.easyscan.cloud.CloudObjectStorageDownloader cosdownloader = null;
    try {
    	LisImageShow tLisImageShow = new LisImageShow();    	
        fis = tLisImageShow.getImage(img_url);
        if(fis != null){
    	    mResult.add(fis);
        }else{
        	logger.debug("从本地获取文件失败。即将尝试从云端获取。");
        	cosdownloader = CloudObjectStorageFactory.getCloudObjectStorageDownloader();
        	if(cosdownloader == null)
        	{
        		logger.debug("没有云端服务被启用. 放弃从云端获取.");
        	}else{
        	    fis = tLisImageShow.getImageFromCloudObjectStorage(img_url, cosdownloader);
        	}
        	if(fis != null){
        		mResult.add(fis);
        	}else{
        		throw new Exception("Failed to get the data from local and cloud.");
        	}
        	
        }
    }
    catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    
    return true;
  }

  public VData getResult() {
    return mResult;
  }

}
