package net.anei.cadpage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ExternalEventReceiver extends BroadcastReceiver {
  public static final String ACTION_SMSPOPUP_ENABLE = "net.everythingandroid.smspopup.ENABLE";
  public static final String ACTION_SMSPOPUP_DISABLE = "net.everythingandroid.smspopup.DISABLE";
  public static final String ACTION_DOCK_EVENT = "android.intent.action.DOCK_EVENT";
  public static final String EXTRA_DOCK_STATE = "android.intent.extra.DOCK_STATE";
  public static final int EXTRA_DOCK_STATE_UNDOCKED = 0;
  public static final int EXTRA_DOCK_STATE_DESK = 1;
  public static final int EXTRA_DOCK_STATE_CAR = 2;

  @Override
  public void onReceive(Context context, Intent intent) {
    if (Log.DEBUG) Log.v("ExternalEventReceiver: onReceive()");

    String action = intent.getAction();

    if (ACTION_SMSPOPUP_ENABLE.equals(action)) {
      SmsPopupUtils.enableSMSPopup(context, ManagePreferences.enableMsgType());
    } else if (ACTION_SMSPOPUP_DISABLE.equals(action)) {
      SmsPopupUtils.enableSMSPopup(context, "");
    } else if (ACTION_DOCK_EVENT.equals(action)) {

      SharedPreferences.Editor settings =
        PreferenceManager.getDefaultSharedPreferences(context).edit();
      settings.commit();

      //      switch (event) {
      //        case EXTRA_DOCK_STATE_UNDOCKED:
      //          if (Log.DEBUG) Log.v("Phone was undocked!");
      //          settings.putBoolean("docked", false);
      //
      //          break;
      //        case EXTRA_DOCK_STATE_DESK:
      //          if (Log.DEBUG) Log.v("Phone was docked to desk!");
      //          break;
      //        case EXTRA_DOCK_STATE_CAR:
      //          if (Log.DEBUG) Log.v("Phone was docked to car!");
      //          break;
      //      }


    }
  }
}
