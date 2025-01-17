package net.anei.cadpage;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

public class CadPageWidget extends AppWidgetProvider {
  
  private static final String ACTION_UPDATE = "net.anei.cadpage.UpdateWidget";
  private static final String ACTION_CADPAGE_ENABLED = "net.anei.cadpage.CadPageEnabled";
  private static final String ACTION_NOTIFICATION = "net.anei.cadpage.CadPageNotification";
  private static final String ACTION_POPUP = "net.anei.cadpage.CadPageAlerts";
  private static final String ACTION_REINIT = "net.anei.cadpage.InitializeWidget"; 

  @Override
  public void onUpdate(Context context,AppWidgetManager appWidgetManager, int[] appWidgetIds){
    
    Log.v("CadpageWidget.onUpdate(" + appWidgetIds.length + ")");
    
    updateEnabled(context,appWidgetManager,appWidgetIds);
   
  }
  
    
  private void updateEnabled(Context context,AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    
    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
    
    //First Button (Enable/Disable Cadpage)
    Intent aEnabledIntent = new Intent(context, CadPageWidget.class);
    aEnabledIntent.setAction(ACTION_CADPAGE_ENABLED);
    PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, aEnabledIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    views.setOnClickPendingIntent(R.id.widget_button_cadpage, actionPendingIntent);

    
    //Second Button (Enable/Disable Alerts)
    Intent bEnabledIntent = new Intent(context, CadPageWidget.class);
    bEnabledIntent.setAction(ACTION_NOTIFICATION);
    PendingIntent notificationPendingIntent = PendingIntent.getBroadcast(context, 0, bEnabledIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    views.setOnClickPendingIntent(R.id.widget_button_notification, notificationPendingIntent);
    
    //Third Button (Enable/Disable PopUps)
    Intent cEnabledIntent = new Intent(context, CadPageWidget.class);
    cEnabledIntent.setAction(ACTION_POPUP);
    PendingIntent popupPendingIntent = PendingIntent.getBroadcast(context, 0, cEnabledIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    views.setOnClickPendingIntent(R.id.widget_button_popup, popupPendingIntent);
    
   //Fourth Button (Show Unread Calls. Click to go into History)
    Intent dEnabledIntent = CallHistoryActivity.getLaunchIntent(context, true);
    PendingIntent callsPendingIntent = PendingIntent.getActivity(context, 0, dEnabledIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    views.setOnClickPendingIntent(R.id.widget_text_newcalls, callsPendingIntent);

    if (ManagePreferences.enabled()){
      views.setImageViewResource(R.id.widget_button_cadpage, R.drawable.cadpage_widget_logo);
      views.setViewVisibility(R.id.widget_button_notification,View.VISIBLE);
      views.setViewVisibility(R.id.widget_button_popup,View.VISIBLE );
      views.setViewVisibility(R.id.widget_text_newcalls, View.VISIBLE);
    } else {
      views.setImageViewResource(R.id.widget_button_cadpage, R.drawable.cadpage_widget_logo_disable);  
      views.setViewVisibility(R.id.widget_button_notification,View.INVISIBLE);
      views.setViewVisibility(R.id.widget_button_popup,View.INVISIBLE );
      views.setViewVisibility(R.id.widget_text_newcalls, View.INVISIBLE);
    }
    if (ManagePreferences.notifyEnabled()){
      views.setImageViewResource(R.id.widget_button_notification, R.drawable.cadpage_widget_bell);
    } else {
      views.setImageViewResource(R.id.widget_button_notification, R.drawable.cadpage_widget_bell_disable);
    }
    if (ManagePreferences.popupEnabled()){
      views.setImageViewResource(R.id.widget_button_popup, R.drawable.cadpage_widget_talk);
    } else {
      views.setImageViewResource(R.id.widget_button_popup, R.drawable.cadpage_widget_talk_disable);
    }
    
    SmsMessageQueue instance = SmsMessageQueue.getInstance();
    int newCallCount = (instance == null ? 0 : instance.getNewCallCount());
    views.setTextViewText(R.id.widget_text_newcalls, Integer.toString(newCallCount));
    
    appWidgetManager.updateAppWidget(appWidgetIds, views);
  }
  
  
  private void updateEnabled(Context context) {
    ComponentName thisWidget = new ComponentName(context,CadPageWidget.class);
    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
    updateEnabled(context,appWidgetManager,appWidgetIds);
  }

  
  private void initialize(Context context) {
    ComponentName thisWidget = new ComponentName(context,CadPageWidget.class);
    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
    onUpdate(context, appWidgetManager, appWidgetIds);
  }
  
  
  @Override 
  public void onReceive(Context context, Intent intent){
    Log.v("Widget Activity");
    ContentQuery.dumpIntent(intent);

    super.onReceive(context, intent);
    if (ACTION_UPDATE.equals(intent.getAction())) {
      updateEnabled(context);
    }
    else if(ACTION_CADPAGE_ENABLED.equals(intent.getAction())){
      boolean aEnabled = ManagePreferences.enabled();
      ManagePreferences.setEnabled(!aEnabled);
    }
    else if (ACTION_NOTIFICATION.equals(intent.getAction())){
      boolean bEnabled = ManagePreferences.notifyEnabled();
      ManagePreferences.setNotifyEnabled(!bEnabled);
    }
    else if (ACTION_POPUP.equals(intent.getAction())){
      boolean cEnabled = ManagePreferences.popupEnabled();
      ManagePreferences.setPopupEnabled(!cEnabled);
    }
    else if (ACTION_REINIT.equals(intent.getAction())) {
      initialize(context);
    }
  }
  
  /**
   * Force update of all widget displays
   * @param context current context
   */
  public static void update(Context context) {
    Intent intent = new Intent(ACTION_UPDATE);
    intent.setClass(context, CadPageWidget.class);
    context.sendBroadcast(intent);
  }
  
  /**
   * Force complete reinitialization of all widgets
   * @param context current context
   */
  public static void reinit(Context context) {
    Intent intent = new Intent(ACTION_REINIT);
    intent.setClass(context, CadPageWidget.class);
    context.sendBroadcast(intent);
  }
}

