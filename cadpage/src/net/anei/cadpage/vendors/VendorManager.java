package net.anei.cadpage.vendors;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.anei.cadpage.C2DMService;
import net.anei.cadpage.CadPageApplication;
import net.anei.cadpage.ManagePreferences;
import net.anei.cadpage.NoticeActivity;
import net.anei.cadpage.R;
import net.anei.cadpage.SmsPopupUtils;
import net.anei.cadpage.donation.UserAcctManager;
import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceScreen;

/**
 * This class manages all of the Vendor classes
 */
public class VendorManager {
  
  private Context context;
  
  // List of supported vendors
  private Vendor[] vendorList = new Vendor[]{
    new CadpageVendor(),
    new CodeMessagingVendor(),
    new Active911Vendor()
  };
  
  /**
   * Initialize vendor status at startup
   * @param context current context
   */
  public void setup(Context context) {
    this.context = context;
    for (Vendor vendor : vendorList) {
      vendor.setup(context);
    }
  }
  
  /**
   * Set up Vendor configuration preference screen
   * @param context current context
   * @param pref preference to be set up with vendor config menu
   */
  public void setupPreference(final Context context, PreferenceScreen pref) {
    
    Preference reconnectPref = pref.findPreference(context.getString(R.string.pref_reconnect_key));
    reconnectPref.setOnPreferenceClickListener(new OnPreferenceClickListener(){

      @Override
      public boolean onPreferenceClick(Preference preference) {
        if (SmsPopupUtils.haveNet(context)) reconnect(context);
        return true;
      }});
    
    boolean developer = UserAcctManager.instance().isDeveloper();
    int order = 10;
    for (Vendor vendor : vendorList) {
      if (developer || vendor.isAvailable()) {
        pref.addPreference(new VendorPreference(context, vendor, order++));
      }
    }
  }
  
  /**
   * @return true if phone is registered with any direct paging vendor
   */
  public boolean isRegistered() {
    for (Vendor vendor : vendorList) {
      if (vendor.isEnabled()) return true;
    }
    return false;
  }
  
  /**
   * @return true if phone is registered with specified vendor
   * @param vendorName registered vendor name
   */
  public boolean isRegistered(String vendorName) {
    Vendor vendor = findVendor(vendorName);
    if (vendor == null) return false;
    return vendor.isEnabled();
  }
  
  /**
   * @return true if user is registered with a vendor that requires
   * a pad subscription
   */
  public boolean isPaidSubRequired() {
    return isRegistered("Cadpage") && !isRegistered("CodeMessaging");
  }
  
  /**
   * @return true specified vendor is a legal vendor name
   * @param vendorName registered vendor name
   */
  public boolean isVendorDefined(String vendorName) {
    Vendor vendor = findVendor(vendorName);
    return vendor != null;
  }
  
  /**
   * @return Name of sponsoring agency if an active vendor is sponsoring Cadpage
   */
  public String getSponsor() {
    for (Vendor vendor : vendorList) {
      String sponsor = vendor.getSponsor();
      if (sponsor != null) return sponsor;
    }
    return null;
  }
  
  /**
   * @return Name of inactive sponsoring agency or null
   * if there is no inactive sponsoring agency, or if there is
   * another active sponsoring agency
   */
  public String getInactiveSponsor() {
    String inactiveSponsor = null;
    for (Vendor vendor : vendorList) {
      String sponsor = vendor.getSponsor();
      if (sponsor != null) return null;
      sponsor = vendor.getInactiveSponsor();
      if (sponsor != null) inactiveSponsor = sponsor;
    }
    return inactiveSponsor;
  }
  
  /**
   * Return main icon ID associated with vendor code 
   * @param vendorCode vendor code
   * @return icon resource ID
   */
  public int getVendorIconId(String vendorCode) {
    if (vendorCode == null) return 0;
    Vendor vendor = findVendor(vendorCode);
    if (vendor == null) return 0;
    return vendor.getIconId();
  }
  
  /**
   * Get dispatch email address associated with vendor code
   * @param vendorCode vendor code
   * @return vendor support email address
   */
  public String getEmailAddress(String vendorCode) {
    if (vendorCode == null) return null;
    Vendor vendor = findVendor(vendorCode);
    if (vendor == null) return null;
    return vendor.getEmailAddress();
  }
  
  /**
   * Get support email address associated with vendor code
   * @param vendorCode vendor code
   * @return vendor support email address
   */
  public String getSupportEmail(String vendorCode) {
    if (vendorCode == null) return null;
    Vendor vendor = findVendor(vendorCode);
    if (vendor == null) return null;
    return vendor.getSupportEmail();
  }

  /**
   * Get predefined custom response menu sequence
   * @param vendorCode vendor code
   * @param index response menu index
   * @return the custom response menu for this vendor and index, or null if not defined
   */
  public String getResponseMenu(String vendorCode, int index) {
    Vendor vendor = findVendor(vendorCode);
    if (vendor == null) return null;
    return vendor.getResponseMenu(index);
  }
  
  /**
   * Get client version code for this vendor
   * @param vendorCode vendor code
   * @return client version code for this vendor
   */
  public String getClientVersion(String vendorCode) {
    Vendor vendor = findVendor(vendorCode);
    if (vendor != null) return vendor.getClientVersion();
    return "0-" +  CadPageApplication.getVersionCode();
  }
  
  /**
   * @return true if user is allowed to set up a button configuration with no More Info button
   */
  public boolean infoButtonOptional(String vendorCode) {
    Vendor vendor = findVendor(vendorCode);
    if (vendor != null) return vendor.infoButtonOptional();
    // TODO Auto-generated method stub
    return false;
  }

  /**
   * Return vendor specific text label to use for the More Info button
   * @param vendorCode vendor code
   * @return resource ID of button title or 0 if not specified
   */
  public int getMoreInfoResId(String vendorCode) {
    Vendor vendor = findVendor(vendorCode);
    if (vendor != null) return vendor.getMoreInfoResId();
    return 0;
  }

  /**
   * Request reset of dispatch email address
   * @param vendorCode vendor code
   * @param context current context
   */
  public void resetEmailReq(String vendorCode, Context context) {
    Vendor vendor = findVendor(vendorCode);
    if (vendor != null) vendor.resetEmailReq(context);
  }
  
  /**
   * Update Cadpage services status.
   * Called when either the activation status or expiration date has changed
   * and should be reported to servers
   * @param context current context
   */
  public void updateCadpageStatus(Context context) {
    Vendor vendor = findVendor("Cadpage");
    if (vendor != null) vendor.updateCadpageStatus(context);
  }

  /**
   * Reconnect all enabled vendors
   * @param context current context
   */
  void reconnect(Context context) {
    ManagePreferences.setDirectPageActive(true);
    ManagePreferences.setReconnect(true);
    C2DMService.register(context, true);
  }
  
  /**
   * Display the Cadpage service registration window
   * @param context current context
   */
  public void CadpageServicePopup(Context context) {
    Vendor vendor = findVendor("Cadpage");
    if (vendor != null) VendorActivity.launchActivity(context, vendor);
  }
  
  /**
   * Called by CD2MReceiver when a new registration ID is defined
   * @param context current context
   * @param change true if new registration ID differs from previous ID
   * @param registrationId new registration ID
   */
  public void registerC2DMId(Context context, boolean change, String registrationId) {
    
    // Skip everything if the ID has not changed and a reconnect was not forced
    boolean reconect = ManagePreferences.reconnect();
    if (!change && !reconect) return;
    
    boolean transfer = ManagePreferences.transferFlag();
    
    // Pass new reg ID to all vendors and see if any of the respond
    for (Vendor vendor : vendorList) {
      vendor.registerC2DMId(context, registrationId, reconect, transfer);
    }
    
    // Reset the connect flag
    if (reconect) ManagePreferences.setReconnect(false);
  }
  
  /**
   * Called by CD2MReceiver when phone is unregistered from C2DM services
   * @param context current context
   */
  public void unregisterC2DMId(Context context) {
    
    // New rules, we always have to have a valid registration ID
    // so we always request a new one
    C2DMService.register(context, true);
    
    // But if there are no more registered vendors, we will disable
    // registration error reporting
    if (!isRegistered()) ManagePreferences.setDirectPageActive(false);
  }
  
  /**
   * Call by C2DMReceiver when a registration failure is reported
   * @param context current context
   * @param error error message
   */
  public void failureC2DMId(Context context, String error) {
    
    // We don't report any of the internal issues with registration unless
    // user has done something to enable direct paging
    if (!ManagePreferences.directPageActive()) return;
    
    // Display appropriate error message
    int resId;
    if (error.equals("SERVICE_NOT_AVAILABLE")) resId = R.string.vendor_service_not_available_error;
    else if (error.equals("ACCOUNT_MISSING")) resId = R.string.vendor_account_missing_error;
    else if (error.equals("AUTHENTICATION_FAILED")) resId = R.string.vendor_authentication_failed_error;
    else if (error.equals("PHONE_REGISTRATION_ERROR")) resId = R.string.vendor_phone_registration_error_error;
    else if (error.equals("PHONE_REGISTRATION_ERROR_HARD")) resId = R.string.vendor_phone_registration_error_hard_error;
    else if (error.equals("TOO_MANY_REGISTRATIONS")) resId = R.string.vendor_too_many_registrations_error;
    else resId = R.string.vendor_registration_error;
    String errMsg = context.getString(resId, error);
    NoticeActivity.showVendorNotice(context, errMsg);
    
  }
  
  /**
   * Handle vendor request received as a C2DM message
   * @param context current context
   * @param type request type
   * @param vendorCode vendor code
   * @param account vendor account
   * @param token vendor security token
   * @param dispatchEmail dispatch email address
   */
  public void vendorRequest(Context context, String type, String vendorCode,
                            String account, String token, String dispatchEmail) {

    // Identify the correct vendor and pass request to them
    Vendor vendor = findVendor(vendorCode);
    if (vendor == null) return;
    
    // If the overall sponsor status changes from unsponsored to sponsored
    // as a result of this operation, set the register date to today.  We
    // will use this date to detect and possibly compensate users are are
    // paying for a Cadpage subscription and have a sponsored vendor.
    String sponsor = getSponsor();
    vendor.vendorRequest(context, type, account, token, dispatchEmail);
    if (sponsor == null && getSponsor() != null) ManagePreferences.setRegisterDate();
  }
  
  /**
   * Check for a C2DM discovery text message
   * @param context current context
   * @param address message sender address
   * @param message received text message
   * @return possibly modified text message to be processed, of null if
   * this was a discovery query that should not be visible to anything else
   */
  public String discoverQuery(Context context, String address, String message) {
    
    // There are two kinds of discover messages.  A standalone query
    // always begins with a fixed string and contains an http URL
    if (message.startsWith("*CADPAGEQ*")) {
      Matcher match = HTTP_PATTERN.matcher(message);
      if (match.find()) {
        String urlStr = match.group();
        
        // Confirm that it is a valid URL
        try {
          new URL(urlStr);
        } catch (MalformedURLException ex) {
          return message;
        }
        
        // Find vendor associated with URL
        Vendor vendor = findVendorFromUrl(urlStr);
        if (vendor != null) {
          vendor.registerReq(context, Uri.parse(urlStr));
          return null;
        }
      }
    }
    
    // An inline query is simply a 4+ character prefix unique to each vendor
    // If we find it, all we do is flag the vendor as receiving text pages
    // and strip off the trigger prefix
    else {
      for (Vendor vendor : vendorList) {
        String tag = vendor.getTrigerCode();
        if (tag != null && tag.length()>=4 && message.startsWith(tag)) {
          vendor.setTextPage(true);
          message = message.substring(tag.length());
          break;
        }
        
        if (vendor.isVendorAddress(address)) {
          vendor.setTextPage(true);
          break;
        }
      }
    }
    
    return message;
  }
  private static final Pattern HTTP_PATTERN = Pattern.compile("\\bhttps?://[^ ]+");
  
  /**
   * Return vendor code associated with URL string
   * @param urlString URL string
   * @return vendor code if found or null if not
   */
  public String findVendorCodeFromUrl(String urlString) {
    Vendor vendor = findVendorFromUrl(urlString);
    if (vendor == null) return null;
    return vendor.getVendorCode();
  }

  /**
   * Find the vendor associated with a URL string
   * @param urlString URL string
   * @return associated vendor or null if not found
   */
  private Vendor findVendorFromUrl(String urlString) {
    // Build URI from URL and extract the top level host name
    Uri uri = Uri.parse(urlString);
    String host = extractHostName(uri);
    if (host == null) return null;
    
    // Loop through the vendors looking for one with a base URL pointing
    // to the same top level host name
    for (Vendor vendor : vendorList) {
      Uri vendorUri = vendor.getBaseURI();
      if (host.equals(extractHostName(vendorUri))) return vendor;
    }
    
    return null;
  }
  
  /**
   * Return top level host name associated with URL
   * @param urlStr URL string
   * @return top level host name extracted from URL
   */
  private static String extractHostName(Uri uri) {
    String host = uri.getHost();
    if (host == null) return null;
    int pt = host.lastIndexOf('.');
    if (pt > 0) pt = host.lastIndexOf('.', pt-1);
    if (pt >= 0) host = host.substring(pt+1);
    return host;
  }

  /**
   * Do new release reset processing
   */
  public void newReleaseReset(Context context) {
    
    // Reset all disable text page checks when a new release is installed
    for (Vendor vendor : vendorList) {
      vendor.setDisableTextPageCheck(false);
    }
  }
  
  /**
   * See if there is a direct paging vendor that has discovered it is still getting
   * old fashion text pages
   * @param status context of request
   *         0 - return raw status
   *         1 - should we show startup register screen
   *         2 - should we show register screen in donation menus
   * @return true if there is one, false otherwise
   */
  public boolean findTextPageVendor(int status) {
    lastTextPageVendor = null;
    for (Vendor vendor : vendorList) {
      if (vendor.isTextPage(status)) {
        lastTextPageVendor = vendor;
        return true;
      }
    }
    lastTextPageVendor = null;
    return false;
  }
  private Vendor lastTextPageVendor = null;
  
  /**
   * return a resource ID defining it's title of text paging vendor
   * discovered by findTextPageVendor()
   * @return valid text title if found, -1 otherwise
   */
  public String getTextPageVendorName() {
    return context.getString(lastTextPageVendor.getTitleId());
  }
  
  /**
   * Register user with direct page vendor found by findTextPageVendor()
   * @param context current context
   */
  public void  registerTextPageVendor(Context context) {
    if (lastTextPageVendor != null) {
      VendorActivity.launchActivity(context, lastTextPageVendor);
    }
  }
  
  /**
   * Process user request to ignore direct direct page vendor found by 
   * findTextPageVendor()
   * @param context current context
   */
  public void  ignoreTextPageVendor() {
    lastTextPageVendor.setDisableTextPageCheck(true);
  }
  
  /**
   * Append vendor status info to logging buffer 
   * @param sb String buffer accumulated log information
   */
  public void addStatusInfo(StringBuilder sb) {
    for (Vendor vendor : vendorList) vendor.addStatusInfo(sb);
  }

  /**
   * Confirm that the vendor who has just sent us a message is really enabled
   * @param context current context
   * @param vendorCode vendor code
   * @return true if everything is cool, false if vendor should not be
   * sending pages to this device
   */
  public boolean checkVendorStatus(Context context, String vendorCode) {
    Vendor vendor = findVendor(vendorCode);
    if (vendor == null) return true;
    return vendor.checkVendorStatus(context);
  }
  
  /**
   * Process vendor account information request
   * @param context current context
   * @param vendorCode vendor code
   */
  public void requestAccountInfo(Context context, String vendorCode) {
    Vendor vendor = findVendor(vendorCode);
    if (vendor == null) return;
    vendor.publishAccountInfo(context);
  }

  /**
   * Perform and vendor specific location code conversions
   * @param vendorCode vendor code
   * @param location received location code
   * @return two element string array.  First element contains the converted
   * location code.  The second lists any parser elements that could not be
   * converted
   */
  public String[] convertLocationCode(String vendorCode, String location) {
    Vendor vendor = findVendor(vendorCode);
    if (vendor != null) return vendor.convertLocationCode(location);
    return new String[]{location, null};
  }

  /**
   * Add account identification information to Uri builder 
   * @param vendorCode vendor code
   * @param bld URI builder
   * @return updated URI builder
   */
  public Builder addAccountInfo(String vendorCode, Builder bld) {
    Vendor vendor = findVendor(vendorCode);
    if (vendor != null) bld = vendor.addAccountInfo(bld);
    return bld;
  }

  
  public String addAccountInfo(String vendorCode, String location) {
    if  (location == null) return null;
    Uri.Builder bld = Uri.parse(location).buildUpon();;
    bld = addAccountInfo(vendorCode, bld);
    return bld.build().toString();
  }
  
  /**
   * Update last contact time for specified vendor
   * @param vendorCode vendor code
   */
  public void updateLastContactTime(String vendorCode, String msg) {
    Vendor vendor = findVendor(vendorCode);
    if (vendor != null) vendor.updateLastContactTime(msg);
  }
  
  /**
   * Find vendor with matching vendor code
   * @param vendorCode vendor code
   * @return matching vendor object, or null if none found.
   */
  Vendor findVendor(String vendorCode) {
    if (vendorCode == null) return null;
    for (Vendor vendor : vendorList) {
      if (vendorCode.equals(vendor.getVendorCode())) return vendor;
    }
    return null;
  }

  
  // Support singleton instance of VendorManager
  private static final VendorManager instance = new VendorManager();
  
  public static VendorManager instance() {
    return instance;
  }
}
