package net.anei.cadpage;


import android.os.Bundle;
import net.anei.cadpage.donation.DonationManager;
import net.anei.cadpage.donation.MainDonateEvent;
import net.anei.cadpage.parsers.MsgInfo;
import net.anei.cadpage.vendors.VendorManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.util.Linkify;
import android.view.ContextMenu;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class SmsPopupActivity extends Safe40Activity {
  
  private static final String EXTRAS_MSG_ID = "SmsPopupActivity.MSG_ID";
  
  private PermissionManager permMgr = new PermissionManager(this);
  
  private SmsMmsMessage message;
  private MsgOptionManager optManager;

  private ImageView fromImage;
  private TextView fromTV;
  private TextView messageReceivedTV;
  private TextView messageTV;

  private ProgressDialog mProgressDialog = null;

  private LinearLayout mainLL = null;
  
  private Button donateStatusBtn = null;

  private boolean wasVisible = false;

  private static final double WIDTH = 0.9;
  private static final int MAX_WIDTH = 640;
//  private static final int DIALOG_LOADING = Menu.FIRST;

//  // TextToSpeech variables
//  private boolean ttsInitialized = false;
//  private static boolean androidTextToSpeechAvailable = false;
//  private TTS eyesFreeTts = null;
//  private TextToSpeechWrapper androidTts = null;
	
	private MsgInfo info;
  

//  // Establish whether the Android TextToSpeech class is available to us
//  static {
//    try {
//      TextToSpeechWrapper.checkAvailable();
//      androidTextToSpeechAvailable = true;
//    } catch (Throwable t) {
//      androidTextToSpeechAvailable = false;
//    }
//  }

  @Override
  protected void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    
    ManagePreferences.setPermissionManager(permMgr);

    requestWindowFeature(Window.FEATURE_NO_TITLE);

    setContentView(R.layout.popup);
    
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

    resizeLayout();

    // Find the main textviews
    fromImage = (ImageView)findViewById(R.id.FromImageView);
    fromTV = (TextView) findViewById(R.id.FromTextView);
    messageTV = (TextView) findViewById(R.id.MessageTextView);
    messageTV.setAutoLinkMask(Linkify.WEB_URLS);
    messageReceivedTV = (TextView) findViewById(R.id.HeaderTextView);

    // Enable long-press context menu
    mainLL = (LinearLayout)findViewById(R.id.MainLinearLayout);
    registerForContextMenu(mainLL);
    
    // We can't hook the current donations status here because it may change
    // from msg to message.
    donateStatusBtn = (Button)findViewById(R.id.donate_status_button);
    
    // Populate display fields
    populateViews(getIntent());
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);

    // First things first, acquire wakelock, otherwise the phone may sleep
    //ManageWakeLock.acquirePartial(getApplicationContext());

    setIntent(intent);

    // Force a reload of the contact photo
    //contactPhoto = null;

    // Re-populate views with new intent data (ie. new sms data)
    populateViews(intent);
  }

  @Override
  protected void onStart() {
    super.onStart();
    if (Log.DEBUG) Log.v("SMSPopupActivity: onStart()");
    //ManageWakeLock.acquirePartial(getApplicationContext());
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (Log.DEBUG) Log.v("SMSPopupActivity: onResume()");
    wasVisible = false;
    
    // Supposed to workaround Android 4 problem
    // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (Log.DEBUG) Log.v("SMSPopupActivity: onPause()");

//    // Shutdown eyes-free TTS
//    if (eyesFreeTts != null) {
//      eyesFreeTts.shutdown();
//    }
//
//    // Shutdown Android TTS
//    if (androidTextToSpeechAvailable) {
//      if (androidTts != null) {
//        androidTts.shutdown();
//      }
//    }

    // Dismiss loading dialog
    if (mProgressDialog != null) {
      mProgressDialog.dismiss();
    }

    if (wasVisible) ManageKeyguard.reenableKeyguard();
  }

  @Override
  protected void onStop() {
    super.onStop();
    if (Log.DEBUG) Log.v("SMSPopupActivity: onStop()");
    if (wasVisible) ManageKeyguard.reenableKeyguard();
  }

  @Override
  protected void onDestroy() {
    MainDonateEvent.instance().setButton(null, null, null);
    ManagePreferences.releasePermissionManager(permMgr);
    super.onDestroy();
  }
  
  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] granted) {
    ManagePreferences.onRequestPermissionsResult(requestCode, permissions, granted);
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    // Log.v("SMSPopupActivity: onWindowFocusChanged(" + hasFocus + ")");
    if (hasFocus) {
      // This is really hacky, basically a flag that is set if the message
      // was at some point visible. I tried using onResume() or other methods
      // to prevent doing some things 2 times but this seemed to be the only
      // reliable way (?)
      wasVisible = true;
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {

    // Save values from most recent bundle (ie. most recent message)
    outState.putAll(getIntent().getExtras());
    
    super.onSaveInstanceState(outState);
  }


  // Populate views from intent
  private void populateViews(Intent intent) {
    
    // Log startup intent
    ContentQuery.dumpIntent(intent);
    
    // Check to see if Cadpage is operating in restricted mode, and if it is
    // launch the donation status menu.  We'll check the donation status again
    // when this menu is closed
    if (!DonationManager.instance().isEnabled()) {
      MainDonateEvent.instance().doEvent(this, null);
    }
    
    // Retrieve message from queue
    SmsMessageQueue msgQueue = SmsMessageQueue.getInstance();
    int msgId = intent.getIntExtra(EXTRAS_MSG_ID, 0);
    SmsMmsMessage msg = msgQueue.getMessage(msgId);
    
    // This shouldn't be possible, unless someone other than SmsReceiver is
    // sending rouge intents to us.  But we had better catch it, just in case
    if (msg == null) {
      finish();
      return;
    }
    
    // Flag message read
    msg.setRead(true);
    msgQueue.notifyDataChange();

    // Populate views from message
    populateViews(msg);
  }

  /*
   * Handle results of Dontation status menu
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    
    // If Cadpage is still restricted, close activity
    if (!DonationManager.instance().isEnabled()) finish();
  }

  /*
   * Populate all the main SMS/MMS views with content from the actual
   * SmsMmsMessage
   */
  private void populateViews(SmsMmsMessage newMessage) {


    // Store message
    message = newMessage;

    // Set up regular button list
    optManager = new MsgOptionManager(this, message);
    optManager.setupButtons((ViewGroup)findViewById(R.id.RespButtonLayout),
                            (ViewGroup)findViewById(R.id.RegButtonLayout));
    
    info = message.getInfo();

    // Hook the donate status button with the current donation status
    MainDonateEvent.instance().setButton(this, donateStatusBtn, newMessage);
    
    // Update Icon to indicate direct paging source
    int resIcon = VendorManager.instance().getVendorIconId(message.getVendorCode());
    if (resIcon <= 0) resIcon = R.drawable.ic_launcher; 
    fromImage.setImageResource(resIcon);
    
    // Update TextView that contains the timestamp for the incoming message
    String headerText;
    String timeStamp = message.getFormattedTimestamp(this).toString();
    if (ManagePreferences.showSource()) {
      String source = "";
      if (info != null) source = info.getSource();
      if (source.length() == 0) source = message.getLocation();
      headerText = getString(R.string.src_text_at, source, timeStamp);//
    } else { 
      headerText = getString(R.string.new_text_at, timeStamp);
    }
    
    String detailText;
    
    // Special case if we have no parsed information (which is just about impossible)
    if (info == null) {
      detailText = message.getTitle();
    } 
    
    // Otherwise do things normally
    else {
  
      // Set the from, message and header views
      StringBuilder sb = new StringBuilder(info.getTitle());
      fromTV.setText(sb.toString());
      if (info.noCall()) fromTV.setMaxLines(2);
      sb = new StringBuilder();
      if (info.getPlace().length() > 0) {
        sb.append(info.getPlace());
        sb.append('\n');
      }
      String addr = info.getAddress();
      String apt = info.getApt();
      if (apt.length() > 0) {
        if (addr.length() > 0) addr = addr + ' ';
        addr = addr + getString(R.string.apt_label) + apt;
      }
      if (addr.length() > 0) {
        sb.append(addr);
        sb.append('\n');
      }
      String city = info.getCity();
      String st = info.getState();
      if (st.length() > 0) {
        if (city.length() > 0) city += ", ";
        city += st;
      }
      if (city.length() > 0) {
        sb.append(city);
        sb.append('\n');
      }
      if (info.getCross().length() > 0) {
        sb.append(getString(R.string.cross_label));
        sb.append(info.getCross());
        sb.append('\n');
      }
      if (info.getMap().length() > 0) {
        sb.append(getString(R.string.map_label));
        sb.append(info.getMap());
        sb.append('\n');
      }
      if (info.getBox().length() > 0) {
        sb.append(getString(R.string.box_label));
        sb.append(info.getBox());
        sb.append('\n');
      }
      if (info.getUnit().length() > 0) {
        sb.append(getString(R.string.units_label));
        sb.append(info.getUnit());
        sb.append('\n');
      }
      if (ManagePreferences.showPersonal()) {
        if (info.getName().length() > 0) {
          sb.append(getString(R.string.name_label));
          sb.append(info.getName());
          sb.append('\n');
        }
        if (info.getPhone().length() > 0) {
          sb.append(getString(R.string.phone_label));
          sb.append(info.getPhone());
          sb.append('\n');
        }
      }
      if (info.getChannel().length() > 0) {
        sb.append(getString(R.string.channel_label));
        sb.append(info.getChannel());
        sb.append('\n');
      }
      if (info.getSupp().length() >0) {
        sb.append(info.getSupp());
        sb.append('\n');
      }
      if (info.getCallId().length() >0) {
        sb.append(getString(R.string.call_id_label));
        sb.append(info.getCallId());
        sb.append('\n');
      }
      
      // Remove trailing \n
      int len = sb.length();
      if (len > 0) sb.setLength(len-1);
      detailText = sb.toString();
    }
    messageReceivedTV.setText(headerText);
    messageTV.setText(detailText);
    
    // There used to be a call to myFinish() that was invoked if this method was
    // passed a message that was not a CAD page.  I am about as certain as I can
    // possibly be that this is no longer possible, which is why this call no
    // longer exists.  But the comment remains as a possible clue if someone
    // should discover I was wrong.
    
    //Will add a Database method in the future.
    //storeFileMessage();
    
  } //end of function

  /* (non-Javadoc)
   * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    
    if (optManager != null) optManager.createMenu(menu, true);
    return true;
  }

  /* (non-Javadoc)
   * @see android.app.Activity#onPrepareOptionsMenu(android.view.Menu)
   */
  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    if (optManager != null) optManager.prepareMenu(menu);
    return true;
  }

  /* (non-Javadoc)
   * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (optManager != null && optManager.menuItemSelected(item.getItemId(), true)) return true;
    return super.onOptionsItemSelected(item);
  }

  /*
   * Create Context Menu (Long-press menu)
   */
  @Override
  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
    optManager.createMenu(menu, true);
  }

  /*
   * Context Menu Item Selected
   */
  @Override
  public boolean onContextItemSelected(MenuItem item) {
    if (optManager.menuItemSelected(item.getItemId(), true)) return true;
    return super.onContextItemSelected(item);
  }

  /**
   * Back key pressed
   */
@Override
  public void onBackPressed() {
  
    // Suppress back activity if response button menu is visible
    if (ManageNotification.isActiveNotice()) return;
    
    // Otherwise carry on with back function
    super.onBackPressed();
    
    // Clear any active notification and wake locks
    ClearAllReceiver.clearAll(this);
    
    // Flag message acknowledgment
    message.acknowledge(this);
  }

//  // The eyes-free text-to-speech library InitListener
//  private final TTS.InitListener eyesFreeTtsListener = new InitListener() {
//    public void onInit(int version) {
//      if (mProgressDialog != null) {
//        mProgressDialog.dismiss();
//      }
//      ttsInitialized = true;
//      speakMessage();
//    }
//  };

//  // The Android text-to-speech library OnInitListener (via wrapper class)
//  private final TextToSpeechWrapper.OnInitListener androidTtsListener = new OnInitListener() {
//    public void onInit(int status) {
//      if (mProgressDialog != null) {
//        mProgressDialog.dismiss();
//      }
//      if (status == TextToSpeechWrapper.SUCCESS) {
//        ttsInitialized = true;
//        speakMessage();
//      } else {
//        Toast.makeText(SmsPopupActivity.this, R.string.error_message, Toast.LENGTH_SHORT).show();
//      }
//    }
//  };

//  /*
//   * Speak the message out loud using text-to-speech (either via Android text-to-speech or
//   * via the free eyes-free text-to-speech library)
//   */
//  private void speakMessage() {
//    // TODO: we should really require the keyguard be unlocked here if we are in privacy mode
//
//    // If not previously initialized...
//    if (!ttsInitialized) {
//
//      // Show a loading dialog
//      showDialog(DIALOG_LOADING);
//
//      // User interacted so remove all locks and cancel reminders
//      ClearAllReceiver.removeCancel(getApplicationContext());
//      ClearAllReceiver.clearAll(false);
//
//      // We'll use update notification to stop the sound playing
//      // This doesn't work anymore.  Will have to be reimplemented somehow
//      // ManageNotification.update(getApplicationContext(), message);
//
//      if (androidTextToSpeechAvailable) {
//        // Android text-to-speech available (normally found on Android 1.6+, aka Donut)
//        androidTts = new TextToSpeechWrapper(SmsPopupActivity.this, androidTtsListener);
//      } else { // Else use eyes-free text-to-speech library
//        /*
//         * This is an aweful fix for the loading dialog not disappearing
//         * when the user decides to not install the TTS package but there didn't
//         * seem like another way to hook into the current TTS library.
//         * 
//         * This will all go away once we can purely use the system TTS engine and do away
//         * with the eyes-free version from Market.
//         */
//        // Extend TTS alert dialog so we can dismiss the loading dialog correctly
//        class mTtsVersionAlert extends TTSVersionAlert {
//          // Leaving this as hardcoded just as from the TTS source
//          private final static String QUIT = "Do not install the TTS";
//          mTtsVersionAlert(Context context) {
//            super(context, null, null, null);
//            setNegativeButton(QUIT, new DialogInterface.OnClickListener() {
//              public void onClick(DialogInterface dialog, int which) {
//                if (mProgressDialog != null) {
//                  mProgressDialog.dismiss();
//                }
//              }
//            });
//            setOnCancelListener(new OnCancelListener() {
//              public void onCancel(DialogInterface dialog) {
//                if (mProgressDialog != null) {
//                  mProgressDialog.dismiss();
//                }
//              }
//            });
//          }
//        }
//
//        // Init the eyes-free text-to-speech library
//        eyesFreeTts = new TTS(this, eyesFreeTtsListener, new mTtsVersionAlert(this));
//      }
//
//    } else {
//
//      // Speak the message!
//      if (androidTextToSpeechAvailable) {
//        androidTts.speak(message.getMessageBody(), TextToSpeechWrapper.QUEUE_FLUSH, null);
//      } else {
//        eyesFreeTts.speak(message.getMessageBody(), 0 /* no queue mode */, null);
//      }
//    }
//  }

  private void resizeLayout() {
    // This sets the minimum width of the activity to a minimum of 80% of the screen
    // size only needed because the theme of this activity is "dialog" so it looks
    // like it's floating and doesn't seem to fill_parent like a regular activity
    if (mainLL == null) {
      mainLL = (LinearLayout) findViewById(R.id.MainLinearLayout);
    }
    Display d = getWindowManager().getDefaultDisplay();

    int width = d.getWidth() > MAX_WIDTH ? MAX_WIDTH : (int) (d.getWidth() * WIDTH);

    mainLL.setMinimumWidth(width);
    mainLL.invalidate();
  }
  
  /**
   * Launch call display popup activity
   * @param context context
   * @param message message to be displayed
   * @return
   */
  public static void launchActivity(Context context, SmsMmsMessage message) {
    launchActivity(context, message.getMsgId());
  }
  
  /**
   * Launch call display popup activity
   * @param context context
   * @param msgId message ID of message to be displayed
   * @return
   */
  public static void launchActivity(Context context, int msgId) {
    Intent popup = new Intent(context, SmsPopupActivity.class);
    popup.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    popup.putExtra(EXTRAS_MSG_ID, msgId);
    context.startActivity(popup);
  }

}

