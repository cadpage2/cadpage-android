package net.anei.cadpage.donation;

import android.app.Activity;
import net.anei.cadpage.R;
import net.anei.cadpage.SmsPopupUtils;

/**
Recalculate Purchase Status
 */
public class DonateResetMarketEvent extends DonateEvent {
  
  public DonateResetMarketEvent() {
    super(AlertStatus.YELLOW, R.string.donate_reset_market_title);
  }

  @Override
  protected void doEvent(Activity activity) {
    
    // Don't do anything if we aren't hooked to network
    if (!SmsPopupUtils.haveNet(activity)) return;
    
    // Request complete status reload
    DonationManager.instance().refreshStatus(activity);
    
    // Close donation screens
    closeEvents(activity);
  }
  
  private static final DonateResetMarketEvent instance = new DonateResetMarketEvent();
  
  public static DonateResetMarketEvent instance() {
    return instance;
  }

}
