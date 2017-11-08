package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

/**
 * <p>Title: lis</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: sinosoft</p>
 * @author lh
 * @version 1.0
 */
import com.sinosoft.utility.*;

public interface CertifyService {
  public boolean submitData(VData cInputData, String cOperate);
  public VData getResult();
  public CErrors getErrors();
}
