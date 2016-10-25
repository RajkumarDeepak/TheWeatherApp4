package Service;

import Data.Chanel;

/**
 * Created by Raushan on 10/25/2016.
 */

public interface weatherServiceCallBack {
    void serviceSuccess(Chanel chanel);
    void serviceFailure(Exception exception);
}
