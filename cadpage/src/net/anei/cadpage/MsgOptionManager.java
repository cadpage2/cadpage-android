package net.anei.cadpage;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.anei.cadpage.donation.DonationManager;
import net.anei.cadpage.parsers.MsgParser.MapPageStatus;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Class to handle all of the menus and popup buttons associated with a message
 */
public class MsgOptionManager {
  
  private Activity activity;
  private SmsMmsMessage message;
  
  // View group and list of button handlers associated with response menu buttons
  private ViewGroup respButtonGroup = null;
  private List<ButtonHandler> respButtonList = new ArrayList<ButtonHandler>();
  
  // View group and list of button handlers associated with main menu buttons
  private ViewGroup mainButtonGroup = null;
  private List<ButtonHandler> mainButtonList = new ArrayList<ButtonHandler>();
  
  // Broadcast receiver logging results of text send messages
  private SendSMSReceiver receiver = null;
  
  public MsgOptionManager(Activity activity, SmsMmsMessage message) {
    this.activity = activity;
    this.message = message;
  }

  /**
   * Create option or context menu for message
   * @param context current context
   * @param menu menu to be constructed
   * @param display true if called from popup menu display
   */
  public void createMenu(Menu menu, boolean display) {
    MenuInflater inflater = activity.getMenuInflater();
    inflater.inflate(R.menu.message_menu, menu);
    
    // map_item is a dummy placeholder that should never appear. map_addr_item
    // and map_gps_item do the real mapping
    menu.removeItem(R.id.map_item);
    
    if (display) {
      menu.removeItem(R.id.open_item);
    } else {
      menu.removeItem(R.id.resp_menu_item);
      menu.removeItem(R.id.close_item);
    }
    
    prepareMenu(menu);
  }
  
  /**
   * Make any final changes to menu before actually displaying it
   * @param context current context
   * @param menu Message menu needed to be adjusted
   */
  public void prepareMenu(Menu menu) {
    for (int ndx = 0; ndx < menu.size(); ndx++) {
      final MenuItem item = menu.getItem(ndx);
      prepareItem(new ItemObject() {
        
        @Override
        public int getId() {
          return item.getItemId();
        }

        @Override
        public void setEnabled(boolean enabled) {
          item.setEnabled(enabled);
        }

        @Override
        public void setTitle(int resId) {
          item.setTitle(resId);
        }

        @Override
        public void setVisible(boolean visible) {
          item.setVisible(visible);
        }
        
      }, false);
    }
  }

  // List of menu items associated with each button index.
  private static final int[] ITEM_ID_LIST = new int[]{
    0, 
    R.id.resp_menu_item, 
    R.id.map_item, 
    R.id.toggle_lock_item, 
    R.id.delete_item, 
    R.id.close_item, 
    R.id.email_item,
    R.id.publish_item, 
    R.id.close_app_item,
    R.id.more_info_item,
    R.id.start_radio_item,
    R.id.active911_item
  };
  
  // List of item title resources associated with each button index
  private static final int[] ITEM_TEXT_LIST = new int[]{
    0, 
    R.string.resp_menu_item_text, 
    R.string.map_item_text, 
    0, 
    R.string.delete_item_text, 
    R.string.close_item_text, 
    R.string.email_item_text,
    R.string.publish_item_text, 
    R.string.close_app_item_text,
    R.string.more_info_item_text,
    R.string.start_radio_item_text,
    R.string.active911_item_text
  };
  
  public void setupButtons(ViewGroup respButtonGroup, ViewGroup mainButtonGroup) {
    this.respButtonGroup = respButtonGroup;
    this.mainButtonGroup = mainButtonGroup;

    // Setup the regular button list
    setupMainButtons(mainButtonGroup);
    
    // set up the response menu
    setupResponseButtons();
    
    // And finalize the transient display status of all visible buttons
    prepareButtons();
  }

  private void setupMainButtons(ViewGroup mainButtonGroup) {
    boolean hasMoreInfo = false;
    mainButtonList.clear();
    mainButtonGroup.removeAllViews();
    for (int btn = 1; btn <= ManagePreferences.POPUP_BUTTON_CNT; btn++) {
      int itemNdx = ManagePreferences.popupButton(btn);
      if (itemNdx == 0) continue;
      if (itemNdx == 9) hasMoreInfo = true;
      addRegularButton(itemNdx, mainButtonList, mainButtonGroup);
    }
    
    // If user doesn't have a More info button configured, add it at the end
    // Unless the paging vendor specifically requests otherwise
    if (!hasMoreInfo && !(message != null && message.infoButtonOptional())) {
      addRegularButton(9, mainButtonList, mainButtonGroup);
    }
  }

  /**
   * Construct a regular button handler and add it to the button handler list and view group
   * @param itemNdx index of requested button
   * @param buttonList button handler list
   * @param buttonGroup view group
   */
  private void addRegularButton(int itemNdx, List<ButtonHandler> buttonList, ViewGroup buttonGroup) {
    
    // The map button can expand to multiple buttons
    if (itemNdx == 2) {
      //  First step is to see which map buttons are active
      boolean addrActive = (message.getMapAddress(false) != null);
      boolean gpsActive = (message.getMapAddress(true) != null);
      
      // If none are, that add a dummy Map button that simply serves
      // as a disabled placeholder
      if (!addrActive && !gpsActive) {
        addMapButton(1, buttonList, buttonGroup);
      }
      
      // If only one map button is active, add that
      else if (!gpsActive) {
        addMapButton(2, buttonList, buttonGroup);
      }
      else if (!addrActive) {
        addMapButton(3, buttonList, buttonGroup);
      }

      // If both map buttons are active, things get complicated
      // We always add the preferred map button first
      // The non-preferred alternate button is only added if requested
      else {
        boolean prefGPS = message.isPreferGPSLoc();
        addMapButton((prefGPS ? 3 : 2), buttonList, buttonGroup);
        if (ManagePreferences.altMapButton()) {
          addMapButton((prefGPS ? 2 : 3), buttonList, buttonGroup);
        }
      }
      
      // Add map page button requested and available
      if (ManagePreferences.mapPageButton() && message.getMapPageStatus() != null) {
        addMapButton(4, buttonList, buttonGroup);
      }
    }
    
    // Otherwise create create the appropriate button
    else {
      buttonList.add(new ButtonHandler(ITEM_ID_LIST[itemNdx], ITEM_TEXT_LIST[itemNdx], buttonGroup));
    }
  }
  
  /**
   * Create a specialized map button handler and add it to button handler list and view group
   * @param type map button type 1 - Map, 2 - Map Addr, 3 - Map GPS, 4 - Map Page
   * @param buttonList
   * @param buttonGroup
   */
  private void addMapButton(int type, List<ButtonHandler> buttonList, ViewGroup buttonGroup) {
    buttonList.add(new ButtonHandler(MAP_ITEM_ID_LIST[type-1], MAP_ITEM_TEXT_LIST[type-1], buttonGroup));
  }
  static final int[] MAP_ITEM_ID_LIST = new int[]{
    R.id.map_item,
    R.id.map_addr_item,
    R.id.map_gps_item,
    R.id.map_page_item
  }; 
  static final int[] MAP_ITEM_TEXT_LIST = new int[]{
    R.string.map_item_text,
    R.string.map_addr_item_text,
    R.string.map_gps_item_text,
    R.string.map_page_item_text
  }; 
  
  /**
   * Setup up the response button menu.  This is called when we finally have
   * both a message and response button viewgroup
   */
  private void setupResponseButtons() {
    
    // Start by clearing any previous arrays
    respButtonList.clear();
    respButtonGroup.removeAllViews();
    
    char mergeOption = ManagePreferences.responseMerge().charAt(0);

  
    // If response options have been requested by a direct paging vendor, they
    // preempt everything
    boolean menu = false;
    if (mergeOption != 'I') menu = setupDirectPageButtons();
    
    // If there are is no direct paging menu, or the user has requested that
    // user response buttons be merged with direct paging menus, add in
    // the user response buttons
    if (!menu || mergeOption == 'A') {
      if (setupUserButtons()) menu = true;
    }
    
    // If we have set up anything, add any user extra buttons to end
    if (menu) {
      setupExtraButtons();
    }
    
    // Otherwise, if there are any pending notifications set up a single ack button
    else if (ManageNotification.isAckNeeded()) {
      respButtonList.add(new ButtonHandler(R.id.ack_item, R.string.ack_item_text, respButtonGroup));
    }
  }

  /**
   * Set up response menu with buttons defined by C2DM direct paging vendors
   * @return true if we set up any buttons, false otherwise
   */
  public boolean setupDirectPageButtons() {
    boolean result = false;
    
    // First see if normal responding and non-responding buttons were requested
    // We don't generate a not responding button unless a responding button was
    // requested.  If a responding button is requested without a not responding
    // button, generate a dummy not responding button that doesn't do anything
    String ackReq = message.getAckReq();
    if (ackReq != null && ackReq.contains("R")) {
      if (ackReq.contains("N")) {
        respButtonList.add(new ButtonHandler(R.id.resp_http_item, R.string.not_responding_text, "NO", respButtonGroup));
      } else {
        respButtonList.add(new ButtonHandler(R.id.ack_item, R.string.not_responding_text, respButtonGroup));
      }
      respButtonList.add(new ButtonHandler(R.id.resp_http_item, R.string.responding_text, "RESP", respButtonGroup));
      result = true;
    }
    
    // Next see if they requested any custom menus, and if they did, set those up
    String vendor = message.getVendorCode();
    boolean active911 = vendor != null && vendor.equals("Active911");
    String respMenu = message.getResponseMenu();
    if (respMenu != null) {
      for (String btnDef : respMenu.split(";")) {
        String respCode, respDesc;
        int pt = btnDef.indexOf('=');
        if (pt >= 0) {
          respCode = btnDef.substring(0,pt).trim();
          respDesc = btnDef.substring(pt+1).trim();
        } else {
          respDesc = btnDef.trim();
          respCode = active911 && respDesc.length() > 0 ? respDesc.substring(0,1) : "";
        }
        if (respCode.length() > 0) {
          respButtonList.add(new ButtonHandler(R.id.resp_http_item, respDesc, respCode, respButtonGroup));
        } else {
          respButtonList.add(new ButtonHandler(R.id.ack_item, respDesc, null, respButtonGroup));
        }
        result = true;
      }
    }
    
    return result;
  }
  
  /**
   * Set up the custom user response button menu 
   * @return true if anything was set up
   */
  private boolean setupUserButtons() {
    
    // There may be buttons with title but no codes.  But if all of the buttons
    // have no codes, then there is no point in setting anything up.  But this
    // means we have to make two passes through the buttons.  The first
    // gets the codes and determines if any are non-empty
    String[] respCodes = new String[ManagePreferences.CALLBACK_BUTTON_CNT];
    String[] respDesc = new String[ManagePreferences.CALLBACK_BUTTON_CNT];
    boolean found = false;
    for (int btn = 1; btn <= ManagePreferences.CALLBACK_BUTTON_CNT; btn++) {
      String code = ManagePreferences.callbackButtonCode(btn).trim();
      String desc = ManagePreferences.callbackButtonTitle(btn).trim();
      respCodes[btn-1] = code;
      respDesc[btn-1] = desc;
      if (code.length() > 0 && desc.length() > 0) found = true;
    }
    if (!found) return false;
    
    // We have at least one, so make another pass to actually set up the user
    // defined buttons
    for (int btn = 1; btn <= ManagePreferences.CALLBACK_BUTTON_CNT; btn++) {
      String desc = respDesc[btn-1];
      if (desc.length() == 0) continue;
      String type = ManagePreferences.callbackButtonType(btn);
      String code = respCodes[btn-1];
      if (type.length() == 0 || code.length() == 0) {
        respButtonList.add(new ButtonHandler(R.id.ack_item, desc, null, respButtonGroup));
      } else {
        int buttonId = (type.equals("T") ? R.id.resp_text_item : R.id.resp_call_item);
        respButtonList.add(new ButtonHandler(buttonId, desc, code, respButtonGroup));
      }
    }
    return true;
  }
  
  /**
   * Set up and "extra" regular buttons user wants to appear in response menu
   */
  private void setupExtraButtons() {
    for (int btn = 1; btn <= ManagePreferences.EXTRA_BUTTON_CNT; btn++) {
      int itemNdx = ManagePreferences.extraButton(btn);
      if (itemNdx == 0) continue;
      addRegularButton(itemNdx, respButtonList, respButtonGroup);
    }
  }

  /**
   * Make any last minute corrections to button statuses
   */
  private void prepareButtons() {
    
    // First step is to see which button menu should be visible
    // If we do not have a response button menu, then force main menu display mode
    boolean responseMenu = message.isResponseMenuVisible();
    if (responseMenu && respButtonList.size() == 0) {
      responseMenu = false;
      message.setResponseMenuVisible(false);
    }
    
    // Set the menu visibility and prepare all buttons in the visible menu
    if (responseMenu) {
      respButtonGroup.setVisibility(View.VISIBLE);
      mainButtonGroup.setVisibility(View.GONE);
      prepareButtons(respButtonList);
    } else {
      respButtonGroup.setVisibility(View.GONE);
      mainButtonGroup.setVisibility(View.VISIBLE);
      prepareButtons(mainButtonList);
    }
  }
  
  public boolean isResponseMenuVisible() {
    return respButtonGroup.getVisibility() == View.VISIBLE;
  }

  /**
   * Prepare all buttons in a button menu
   * @param buttonList list of buttons to be prepared
   */
  public void prepareButtons(List<ButtonHandler> buttonList) {
    boolean suppressMoreInfo = false;
    for (ButtonHandler btnHandler : buttonList) {
      if (btnHandler.prepareButton(suppressMoreInfo)) suppressMoreInfo = true;
    }
  }
  
  /*
   * Internal class to handle dynamic button functions on popup
   */
  private class ButtonHandler implements OnClickListener {
    final private int itemId;
    final private Button button;
    private String respCode = null;

    /**
     * Normal constructor for regular button items
     * @param itemId button item ID
     * @param resId button title resource ID
     * @param parent parent ViewGroup
     */
    public ButtonHandler(int itemId, int resId, ViewGroup parent) {
      this(itemId, resId, null, null, parent);
    }
    
    /**
     * Response button constructor
     * @param itemId button item ID
     * @param title button title
     * @param respCode button response code
     * @param parent parent ViewGroup
     */
    public ButtonHandler(int itemId, String title, String respCode, ViewGroup parent) {
      this(itemId, 0, title, respCode, parent);
    }
    
    /**
     * Response button constructor
     * @param itemId button item ID
     * @param resId button title resource ID
     * @param respCode button response code
     * @param parent parent ViewGroup
     */
    public ButtonHandler(int itemId, int resId, String respCode, ViewGroup parent) {
      this(itemId, resId, null, respCode, parent);
    }
    
    /**
     * Common constructor 
     * @param itemId button item ID
     * @param resId button title resource ID
     * @param title button title
     * @param respCode button response code
     * @param parent parent ViewGroup
     */
    private ButtonHandler(int itemId, int resId, String title, String respCode, ViewGroup parent) {
      this.itemId = itemId;
      button = (Button)LayoutInflater.from(activity).inflate(R.layout.popup_button, parent, false);
      button.setId(itemId);
      if (resId != 0) button.setText(resId);
      else button.setText(title);
      button.setOnClickListener(this);
      parent.addView(button);
      this.respCode = respCode;
    }
    
    public boolean prepareButton(boolean suppressMoreInfo) {
      return prepareItem(new ItemObject(){
        
        @Override
        public int getId() {
          return itemId;
        }

        @Override
        public void setEnabled(boolean enabled) {
          button.setEnabled(enabled);
        }

        @Override
        public void setTitle(int resId) {
          button.setText(resId);
        }

        @Override
        public void setVisible(boolean visible) {
          button.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
      }, suppressMoreInfo);
    }

    public void onClick(View v) {
      
      // Perform the requested action
      menuItemSelected(itemId, true, respCode);
      
      // Reset button status in case anything has changed
      prepareButtons();
    }
  }
  
  private interface ItemObject {
    public int getId();
    public void setTitle(int resId);
    public void setEnabled(boolean enabled);
    public void setVisible(boolean visible);
  }
  
  private boolean prepareItem(ItemObject item, boolean suppressMoreInfo) {
    
    switch (item.getId()) {
    
    // Response menu button is only visible if response menu has more than one button
    // A single button would be a Ack button which never has to be repeated
    case R.id.resp_menu_item:
      item.setVisible(respButtonList.size() > 1);
      break;
    
    // Map button is a dummy placeholder that should always be disabled
    case R.id.map_item:
      item.setEnabled(false);
      break;
      
    // Map Address button should be enabled if we have a map street address
    case R.id.map_addr_item:
      item.setEnabled(message.getMapAddress(false) != null);
      break;
      
    // Ditto for map GPS address
    case R.id.map_gps_item:
      item.setEnabled(message.getMapAddress(true) != null);
      break;
      
    // Map page address enabled if we have a map page URL
    case R.id.map_page_item:
      item.setEnabled(message.getMapPageURL() != null);
      break;
    
    // Change label on toggle lock item depending on current lock state
    case  R.id.toggle_lock_item:
      item.setTitle(message.isLocked() ? R.string.unlock_item_text : R.string.lock_item_text);
      break;
      
    // Delete is only enabled if message has been read and is not locked
    case R.id.delete_item:
      item.setEnabled(message.canDelete());
      break;
    
    // Email is disabled for the free version
    case R.id.email_item:
      item.setEnabled(! DonationManager.instance().isFreeVersion());
      break;
      
      // More info disappears if there is no info to display
      // Or if the Active911 info button has been previously enabled, since
      // both buttons accomplish pretty much the same thing
    case R.id.more_info_item:
      item.setVisible(message.getInfoURL() != null && !suppressMoreInfo);
      item.setTitle(message.getInfoTitle());
      break;

      // Start radio button only visible if there is a scanner channel to open
    case R.id.start_radio_item:
      item.setVisible(ManagePreferences.scannerChannel() != null);
      break;
      
    case R.id.active911_item:
      String vendor = message.getVendorCode();
      boolean enabled = vendor != null && vendor.equals("Active911") && launchActive911(activity, false, message);
      item.setEnabled(enabled);
      return enabled;
    }
    return false;
  }

  /**
   * Handle a menu selection concerning this message
   * @param itemId Selected Menu ID
   * @param display true if called from message display dialog
   * @return true if menu item processed, false otherwise
   */
  public boolean menuItemSelected(int itemId, boolean display) {
    return menuItemSelected(itemId, display, null);
  }
  
  private static final Pattern PHONE_TEXT_PTN = Pattern.compile("(\\d+)/ *(.*)");
  
  public boolean menuItemSelected(int itemId, boolean display, String respCode) {
    
    // If parent activity is no longer valid, disregard
    if (activity.isFinishing()) return false;
    
    // Any button clears the notice
    ClearAllReceiver.clearAll(activity);
    
    // Any button will trigger an auto response, except for an HTTP response button
    // which is going to send a response code of it's own, rendering the auto response
    // unnecessary
    if (itemId != R.id.resp_http_item) message.acknowledge(activity);

    switch (itemId) {
    
    case R.id.resp_menu_item:
      message.setResponseMenuVisible(true);
      return true;
      
    case R.id.open_item:
      if (Log.DEBUG) Log.v("MsgOptionManager User launch SmsPopup for " + message.getMsgId()); 
      SmsPopupActivity.launchActivity(activity, message);
      return true;
      
    case R.id.map_addr_item:
      mapMessage(activity, false);
      return true;
      
    case R.id.map_gps_item:
      mapMessage(activity, true);
      return true;
      
    case R.id.map_page_item:
      viewMapPage(activity);
      return true;
      
    case R.id.toggle_lock_item:
      message.toggleLocked();
      return true;
      
    case R.id.delete_item:
      SmsMessageQueue.getInstance().deleteMessage(message);
      if (display) activity.finish();
      return true;
      
    case R.id.close_item:
      activity.finish();
      return true;
      
    case R.id.close_app_item:
      activity.finish();
      CallHistoryActivity.shutdown(activity);
      return true;
      
    case R.id.email_item:
      EmailDeveloperActivity.sendMessageEmail(activity,  message.getMsgId());
      return true;
      
    case R.id.publish_item:
      message.broadcastIntent(activity, true);
      return true;
      
    case R.id.more_info_item:
      message.showMoreInfo(activity);
      return true;
      
    case R.id.start_radio_item:
      Intent scanIntent = ManagePreferences.scannerIntent();
      if (scanIntent != null) {
        Log.v("Launching Scanner");
        scanIntent.putExtra("caller", "cadpage");
        ContentQuery.dumpIntent(scanIntent);
        activity.sendBroadcast(scanIntent);
      }
      return true;
      
    case R.id.active911_item:
      launchActive911(activity, true, message);
      return true;
      
    case R.id.ack_item:
      message.setResponseMenuVisible(false);
      return true;
      
    case R.id.resp_call_item:
      message.setResponseMenuVisible(false);
      String phone = respCode;
      Matcher match = PHONE_TEXT_PTN.matcher(respCode);
      if (match.matches()) phone = match.group(1);
      try {
        String urlPhone = "tel:" + phone;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(urlPhone));
        activity.startActivity(intent);
      } catch (Exception e) {
        Log.v("SMSPopupActivity: Phone call failed" + e.getMessage());
      }
      return true;
      
    case R.id.resp_text_item:
      message.setResponseMenuVisible(false);
      match = PHONE_TEXT_PTN.matcher(respCode);
      if (match.matches()) {
        sendSMS(match.group(1), match.group(2));
      } else {
        sendSMS(message.getFromAddress(), respCode);
      }
      return true;
      
    case R.id.resp_http_item:
      message.setResponseMenuVisible(false);
      message.sendResponse(activity, respCode);
      return true;
    
    default:
      return false;
    }
  }

  /**
   * Request map location for message
   * @param context current context
   * @param useGPS use GPS location instead regular address
   */
  private void  mapMessage(Context context, boolean useGPS)  {
    if (Log.DEBUG) Log.v("Request Received to Map Call");
    
    String searchStr = message.getMapAddress(useGPS);
    if (searchStr == null) return;
    
    if (!SmsPopupUtils.haveNet(context)) return;
    
    String mapOption = ManagePreferences.appMapOption();
    boolean navigateMap = ManagePreferences.navigateMap();
    boolean gps = GPS_LOC_PTN.matcher(searchStr).matches();
    if (!gps) searchStr = searchStr.replaceAll(" *& *", " AT ");
    searchStr = Uri.encode(searchStr);
    
    // Waze has completely different request protocols
    if (mapOption.equals("Waze")) {
      StringBuilder sb = new StringBuilder();
      sb.append("waze://?");
      if (gps) {
        sb.append("ll=");
      } else {
        sb.append("q=");
      }
      sb.append(searchStr);
      if (navigateMap) sb.append("&navigate=yes");
      
      // Build and launch map request
      Uri uri = Uri.parse(sb.toString());
      Intent intent = new Intent(Intent.ACTION_VIEW, uri);
      
      Log.w("Map Request:");
      ContentQuery.dumpIntent(intent);
      
      try {
          context.startActivity(intent);
          return;
      } catch (ActivityNotFoundException ex) {
          // Waze not installed, drop back to Google mapping
        Log.w("Map request failed");
        mapOption = "Google";
      }
    }
    
    // Everything other than Waze
    
    // Should we jump straight to navigation
    StringBuilder sb = new StringBuilder();
    if (navigateMap) {
      sb.append("google.navigation:q=");
      sb.append(searchStr);
    }
    
    // Regular mapping
    else {
      
      // We do things differently for GPS coordinates
      if (gps) {
        sb.append("geo:0,0?q=");
        sb.append(searchStr);
        
        // Add real address as title
        if (!ManagePreferences.noMapGpsLabel()) {
          String addr = message.getAddress();
          if (addr.length() > 0) {
            sb.append('(');
            sb.append(Uri.encode(addr));
            sb.append(')');
          }
        }
      }
      
      // Regular address parsing
      else {
        sb.append("geo:0,0?q=");
        sb.append(searchStr);
      }
    }
    
    // Build and launch map request
    Uri uri = Uri.parse(sb.toString());
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    
    if (mapOption.equals("Google")) intent.setPackage(GOOGLE_MAP_PKG);
    
    Log.w("Map Request:");
    ContentQuery.dumpIntent(intent);
    
    try {
        context.startActivity(intent);
    } catch (ActivityNotFoundException ex) {
        Log.e("Could not find com.google.android.maps.Maps activity");
    }
  }
  private static final Pattern GPS_LOC_PTN = Pattern.compile("[+-]?\\d+\\..*");
  private static final String GOOGLE_MAP_PKG = "com.google.android.apps.maps";
  
  private void viewMapPage(Context context) {
    MapPageStatus mapPageStatus = message.getMapPageStatus();
    if (mapPageStatus == null) return;
    String url = message.getMapPageURL();
    if (url == null) return;
    
    Uri uri = Uri.parse(url);
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    switch (mapPageStatus) {
    case ADOBE:
      intent.setClassName("com.adobe.reader", "com.adobe.reader.AdobeReader");
      break;

    case ANY:
      break;
    }
    
    Log.w("Launching Map Page Viewer");
    ContentQuery.dumpIntent(intent);
    try {
      context.startActivity(intent);
    } catch (ActivityNotFoundException ex) {
      switch (mapPageStatus) {
      case ADOBE:
        NoticeActivity.showMissingReaderNotice(context, R.string.missing_map_page_reader_adobe, "com.adobe.reader");
        break;
        
      case ANY:
        Log.e(ex);
        ContentQuery.dumpIntent(intent);
      }
    }
  }

  /**
   * Send SMS response message
   * @param target target phone number or address
   * @param message message to be sent
   */
  private void sendSMS(String target, String message){ 
    Log.v("Sending text reponse to " + target + " : " + message);
    
    if (receiver == null) {
      receiver = new SendSMSReceiver();
      activity.registerReceiver(receiver, new IntentFilter(SMS_SENT));
      activity.registerReceiver(receiver, new IntentFilter(SMS_DELIVERED));;
    }
    
    Intent sendIntent = new Intent(SMS_SENT);
    sendIntent.setFlags(Intent.FLAG_DEBUG_LOG_RESOLUTION);
    PendingIntent sentPI = PendingIntent.getBroadcast(activity, 0, sendIntent, 0);                
    Intent deliverIntent = new Intent(SMS_DELIVERED);
    deliverIntent.setFlags(Intent.FLAG_DEBUG_LOG_RESOLUTION);
    PendingIntent deliveredPI = PendingIntent.getBroadcast(activity, 0, deliverIntent, 0);   

    /**
     * The send logic apparently isn't as bulletproof as we like.  It sometimes
     * throws a null pointer exception on the other side of an RPC.  We can't
     * do much about it.
     */
    SmsManager sms = SmsManager.getDefault();
    try {
      sms.sendTextMessage(target, null, message, sentPI, deliveredPI);
    } catch (NullPointerException ex) {
      
      Log.e(ex);
    }
  }
  
  public static class SendSMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
      if (intent == null) return;
      String action = intent.getAction();
      int pt = action.lastIndexOf('.');
      if (pt >= 0) action = action.substring(pt+1);
      String status;
      switch (getResultCode()) {
      
      case Activity.RESULT_OK:
        status = "OK";
        break;
        
      case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
        status = "Generic failure"; 
        break;
        
      case SmsManager.RESULT_ERROR_NO_SERVICE:
        status = "No service"; 
        break;
        
      case SmsManager.RESULT_ERROR_NULL_PDU:
        status = "Null PDU";
        break;
        
      case SmsManager.RESULT_ERROR_RADIO_OFF:
        status = "Radio off";
        break;
        
      case Activity.RESULT_CANCELED:
        status = "Canceled";
        break;                        
      
      default:
        status = "" + getResultCode();
      }
      Log.v("SMS " + action + " status:" + status);
    }
  }
  private static final String SMS_SENT = "net.anei.cadpage.MsgOptionManager.SMS_SENT";
  private static final String SMS_DELIVERED = "net.anei.cadpage.MsgOptionManager.SMS_DELIVERED";
  
  /**
   * Launch Active911 app if it is installed
   * @param context current context
   * @param launch true to really launch the app. false to just test to see if it is installed 
   * @return true if Active911 app is installed, false otherwise
   */
  private static boolean launchActive911(Context context, boolean launch, SmsMmsMessage msg) {
    Intent intent = new Intent(Intent.ACTION_MAIN);
    intent.addCategory(Intent.CATEGORY_LAUNCHER);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.setClassName("com.active911.app", "com.active911.app.MainActivity");
    
    // See if we can extract the message ID from the ack URL
//    String code = msg.getActive911MsgCode();
//    if (code != null) intent.putExtra("q", code);
    
    PackageManager pm = context.getPackageManager();
    List<ResolveInfo> list = pm.queryIntentActivities(intent, 0);
    if (list == null || list.size() == 0) return false;
    
    if (!launch) return true;
    Log.w("Launching Active911");
    ContentQuery.dumpIntent(intent);
    try {
      context.startActivity(intent);
    } catch (Exception ex) {
      Log.e(ex);
    }
    return true;
  }
}
