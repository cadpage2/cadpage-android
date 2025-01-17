package net.anei.cadpage.parsers.FL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.anei.cadpage.parsers.MsgInfo.Data;
import net.anei.cadpage.parsers.dispatch.DispatchPrintrakParser;



public class FLLeeCountyParser extends DispatchPrintrakParser {
  
  public FLLeeCountyParser() {
    super("LEE COUNTY", "FL", "XST:X");
  }
  
  @Override
  public String getFilter() {
    return "leecontrol@leegov";
  }
  
  private static final Pattern ID_PTN = Pattern.compile("Msg ID: *([\\S]*?)\n");
  private static final Pattern SRC_PTN = Pattern.compile("([A-Z]+) +");
  
  @Override
  public boolean parseMsg(String body, Data data) {
    
    Matcher match = ID_PTN.matcher(body);
    if (match.lookingAt()) {
      body =  body.substring(match.end()).trim();
      match = SRC_PTN.matcher(body);
      if (match.lookingAt()) {
        data.strSource = match.group(1);
        body = body.substring(match.end());
      }
    }
    
    else {
      int ipt = body.indexOf(" TYP:");
      if (ipt >= 0) {
        Parser p = new Parser(body.substring(0,ipt).trim());
        data.strCallId = p.getLast(' ');
        body = body.substring(ipt+1).trim();
      }
      
      else if (!body.startsWith("TYP:")) {
        body = "TYP:" + body;
      }
    }
    
    return super.parseMsg(body, data);
  }
  
  @Override
  public String getProgram() {
    return "ID " + super.getProgram();
  }
}
