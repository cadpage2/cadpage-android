package net.anei.cadpage;

import java.util.HashMap;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Build;

/**
 * Class probing USB connections looking to establish communications with a
 * Tetra pager
 */

public class ManageUsb {
  
  private UsbProbeInterface realUsb;
  
  private ManageUsb() {
    
    // The classes we are trying to invoke only exist at SDK version 12 and up
    // But Cadpage support goes back to SDK 10.  Which means the critical calls
    // have to be made in an embedded class that is only loaded as SDK 12+
    if (Build.VERSION.SDK_INT < 12) {
      realUsb = null;
    }
    
    // OK, go for it
    else {
      try {
        realUsb = (UsbProbeInterface)Class.forName("net.anei.cadpage.ManageUsb$ManageUsbReal").newInstance();
      } catch (Exception ex) {
        Log.e(ex);;
      }
    }
    
  }

  public void probe(Context context) {
    if (realUsb != null) realUsb.probe(context);
    else Log.w("USB Probe rejected at API level:" + Build.VERSION.SDK_INT);
  }
  
  public void onReceive(Context context, Intent intent) {
    if (realUsb != null) realUsb.onReceive(context, intent);
  }
  
  private interface UsbProbeInterface {
    public void probe(Context contxt);
    public void onReceive(Context context, Intent intent);
  }
  
  @TargetApi(12)
  public static class ManageUsbReal implements UsbProbeInterface {
    
    private UsbManager mgr = null;
    
    public void probe(Context context) {
      Log.v("Starting USB Probe");
      if (mgr == null) mgr = (UsbManager) context.getSystemService(Context.USB_SERVICE);
      
      Log.v("\nUSB accessory list");
      UsbAccessory[] accList = mgr.getAccessoryList();
      if (accList != null) {
        for (UsbAccessory acc : accList) {
          Log.v(acc.toString());
        }
      }
      
      Log.v("\nUSB Host list");
      HashMap<String, UsbDevice> devMap = mgr.getDeviceList();
      if (devMap != null) {
        for (String devKey : devMap.keySet()) {
          UsbDevice usbDev = devMap.get(devKey);
          Log.v("Device Key:" + devKey);
          dumpUsbDevice(usbDev);
        }
      }
    }

    private void dumpUsbDevice(UsbDevice usbDev) {
      Log.v ("Device Name:" + usbDev.getDeviceName() +
            "\n   VendorId:" + Integer.toHexString(usbDev.getVendorId()) +
            "\n   ProductId:" + Integer.toHexString(usbDev.getProductId()) +
            "\n   DeviceId:" + Integer.toHexString(usbDev.getDeviceId()) +
            "\n   Class:" + Integer.toHexString(usbDev.getDeviceClass()) +
            "\n   Subclass:" + Integer.toHexString(usbDev.getDeviceSubclass()) +
            "\n   Protocol:" + Integer.toHexString(usbDev.getDeviceProtocol()) +
            "\n   Permitted:" + mgr.hasPermission(usbDev));
      for (int intrNdx = 0; intrNdx < usbDev.getInterfaceCount(); intrNdx++) {
        Log.v("   Interface " + intrNdx);
        UsbInterface usbIntr = usbDev.getInterface(intrNdx);
        Log.v("      Id:" + Integer.toHexString(usbIntr.getId()) +
              "\n      Class:" + Integer.toHexString(usbIntr.getInterfaceClass()) +
              "\n      Sublass:" + Integer.toHexString(usbIntr.getInterfaceSubclass()) +
              "\n      Protocol:" + Integer.toHexString(usbIntr.getInterfaceProtocol()));
        for (int endNdx = 0; endNdx < usbIntr.getEndpointCount(); endNdx++) {
          Log.v("      Endpoint " + endNdx);
          UsbEndpoint usbEnd = usbIntr.getEndpoint(endNdx);
          Log.v("         Address:" + Integer.toHexString(usbEnd.getAddress()) +
                "\n         Attributes:" + Integer.toHexString(usbEnd.getAttributes()) +
                "\n         Direction:" + usbEnd.getDirection() +
                "\n         Interval:" + usbEnd.getInterval() +
                "\n         PacketSize:" + usbEnd.getMaxPacketSize() +
                "\n         Type:" + usbEnd.getType());
          
        }
      }
    }
    
    public void onReceive(Context context, Intent intent) {
      if (mgr == null) return;
      String action = intent.getAction();
      if (action == null) return;
      if (action.equals(UsbManager.ACTION_USB_ACCESSORY_ATTACHED) || action.equals(UsbManager.ACTION_USB_ACCESSORY_DETACHED)) {
        Log.v(action);
        UsbAccessory acc = (UsbAccessory)intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
        Log.v(acc == null ? "null" : acc.toString());
      }
      else if (action.equals(UsbManager.ACTION_USB_DEVICE_ATTACHED) || action.equals(UsbManager.ACTION_USB_DEVICE_DETACHED)) {
        Log.v(action);
        UsbDevice dev = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
        Log.v(dev == null ? "null" : dev.toString());
      }
      
      
    }
  }
  
  private static final ManageUsb instance = new ManageUsb();
  
  public static ManageUsb instance() {
    return instance;
  }
}