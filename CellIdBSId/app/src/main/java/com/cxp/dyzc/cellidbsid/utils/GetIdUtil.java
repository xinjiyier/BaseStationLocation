package com.cxp.dyzc.cellidbsid.utils;

import android.content.Context;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by ShoppingChen on 2017/8/4.
 */

public class GetIdUtil {
    private Context mContext;
    TelephonyManager mTelephonyManager;

    public GetIdUtil(Context context){
        this.mContext = context;
    }
    /**
     * 获取 CellId
     * @return
     */
    public String getCellId(){
        if(mTelephonyManager==null){
            mTelephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        }
        // 返回值MCC + MNC
        String operator = mTelephonyManager.getNetworkOperator();
        int mcc = -1;
        int mnc = -1;
        if(operator!=null&&operator.length()>3){
            mcc = Integer.parseInt(operator.substring(0, 3));
            mnc = Integer.parseInt(operator.substring(3));
        }

        // 获取邻区基站信息
        List<NeighboringCellInfo> infos = mTelephonyManager.getNeighboringCellInfo();
        StringBuffer sb = new StringBuffer("总数 : " + infos.size() + "\n");

        for (NeighboringCellInfo info1 : infos) { // 根据邻区总数进行循环
            sb.append(" LAC : " + info1.getLac()); // 取出当前邻区的LAC
            sb.append("\n CID : " + info1.getCid()); // 取出当前邻区的CID
            sb.append("\n BSSS : " + (-113 + 2 * info1.getRssi()) + "\n"); // 获取邻区基站信号强度
        }


        int type = mTelephonyManager.getNetworkType();

        Toast.makeText(mContext,"type:= "+type,Toast.LENGTH_LONG).show();
        if(type == TelephonyManager.NETWORK_TYPE_CDMA        // 电信cdma网
                || type == TelephonyManager.NETWORK_TYPE_1xRTT
                || type == TelephonyManager.NETWORK_TYPE_EVDO_0
                || type == TelephonyManager.NETWORK_TYPE_EVDO_A
                || type == TelephonyManager.NETWORK_TYPE_EVDO_B
                || type == TelephonyManager.NETWORK_TYPE_LTE){
            CdmaCellLocation cdma = (CdmaCellLocation) mTelephonyManager.getCellLocation();
            if(cdma!=null){
                sb.append(" MCC = " + mcc );
                sb.append("\n  cdma.getBaseStationLatitude()   "+cdma.getBaseStationLatitude()/14400 +"  \n "+"cdma.getBaseStationLongitude() "+cdma.getBaseStationLongitude()/14400 +"  \n "
                        +"cdma.getBaseStationId()(CELLID)  "+cdma.getBaseStationId()+"\n  cdma.getNetworkId()(LAC)   "+cdma.getNetworkId()+" \n  cdma.getSystemId()(MNC)   "+cdma.getSystemId());
            }else{
                sb.append("can not get the CdmaCellLocation");
            }

        }else if(type == TelephonyManager.NETWORK_TYPE_GPRS              // GSM网
                || type == TelephonyManager.NETWORK_TYPE_EDGE
                || type == TelephonyManager.NETWORK_TYPE_HSDPA
                || type == TelephonyManager.NETWORK_TYPE_UMTS
                || type == TelephonyManager.NETWORK_TYPE_LTE){
            GsmCellLocation gsm = (GsmCellLocation) mTelephonyManager.getCellLocation();
            if(gsm!=null){
                sb.append("  gsm.getCid()(CELLID)   "+gsm.getCid()+"  \n "+"gsm.getLac()(LAC) "+gsm.getLac()+"  \n "
                        +"gsm.getPsc()  "+gsm.getPsc());
            }else{
                sb.append("can not get the GsmCellLocation");
            }
        }else if(type == TelephonyManager.NETWORK_TYPE_UNKNOWN){
            Toast.makeText(mContext,"电话卡不可用！",Toast.LENGTH_LONG).show();
        }

        Log.d("spp","mTelephonyManager.getNetworkType(); "+mTelephonyManager.getNetworkType());
        Log.i(TAG, " 获取邻区基站信息:" + sb.toString());
        return sb.toString();
    }
    /**
     * 获取 基站 的id
     * @return
     */
    public int getBaseStationId(){
        return 0;
    }
}
