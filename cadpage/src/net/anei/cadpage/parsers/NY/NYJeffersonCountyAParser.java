package net.anei.cadpage.parsers.NY;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.anei.cadpage.parsers.FieldProgramParser;
import net.anei.cadpage.parsers.MsgInfo.Data;



public class NYJeffersonCountyAParser extends FieldProgramParser {
  
  public NYJeffersonCountyAParser() {
    super("JEFFERSON COUNTY", "NY",
          "CALL ADDR! INFO");
  }

  @Override
  protected boolean parseMsg(String subject, String body, Data data) {

    // Fixed stuff messed up by IAR edits :(
    String[] flds = null;
    if (subject.equals("So. Jeff. Rescue")) {
      data.strSource = subject;
      do {
        String work = body;
        if (work.endsWith("/") || work.endsWith(":")) work += ' ';
        work = work.replace(" : ", ":");
        
        String[] tFlds = new String[3];
        
        // Generally split by " / ".  With some special checks to see if there
        // is a legitimate slash sequence in the address or info fields
        int pt1 = work.indexOf(" / ");
        if (pt1 < 0) break;
        tFlds[0] = work.substring(0,pt1).trim();
        
        int pt2 = work.indexOf(" / ", pt1+3);
        if (pt2 < 0) break;
        
        int pt3 = work.indexOf(" / ", pt2+3);
        if (pt3 >= 0) {
          if (!work.substring(pt1+3,pt2).contains(":") &
              work.substring(pt2+3,pt3).contains(":")) {
            pt2 = pt3;
          }
        }
        tFlds[1] = work.substring(pt1+3,pt2).trim();
        tFlds[2] = work.substring(pt2+3).trim();
        flds = tFlds;
      } while (false);
    }
    
    // Normal parsing
    if (flds == null) {
      
      if (subject.startsWith("[")) {
        int pt = subject.indexOf(']');
        if (pt < 0) return false;
        data.strSource = subject.substring(1,pt).trim();
        subject = subject.substring(pt+1).trim();
      }
      
      if (subject.startsWith("DISPATCH:")) {
        data.strUnit = subject.substring(9).trim();
      }
      else if (subject.startsWith("DISPATCH") && subject.contains("Incident #:")) {
        data.strCall = "RUN REPORT";
        data.strPlace = '(' + subject + ") " + body;
        return true;
      }
      
      flds = body.split("\\|", -1);
      if (flds.length != 3) return false;
    }
    return parseFields(flds, data);
  }
  
  @Override
  public String getProgram() {
    return "SRC UNIT " + super.getProgram();
  }
  
  @Override
  public Field getField(String name) {
    if (name.equals("ADDR")) return new MyAddressField();
    if (name.equals("INFO")) return new MyInfoField();
    return super.getField(name);
  }
  
  private static final Pattern ADDR_PTN = Pattern.compile("\\(.\\)$");
  private class MyAddressField extends AddressField {
    
    @Override
    public void parse(String field, Data data) {
      Matcher match = ADDR_PTN.matcher(field);
      if (match.find()) field = field.substring(0,match.start()).trim();
      Parser p = new Parser(field.trim());
      data.strCity = p.getLastOptional(':');
      data.strPlace = p.getLastOptional(';');
      parseAddress(p.get(), data);
    }
    
    @Override
    public String getFieldNames() {
      return "ADDR APT PLACE CITY";
    }
  }
  
  private static final Pattern MSPACE_PTN = Pattern.compile("  +");
  private static final Pattern CALLBACK_PTN = Pattern.compile("(?:CALLBACK=([^ ]*) +)?LAT[:=]([-+]?[\\.0-9]*) LON[:=]([-+]?[\\.0-9]*).*");
  private static final Pattern TURN_OFF_PTN = Pattern.compile("\\d{5}-\\d{3}-\\d{4} \\d\\d/\\d\\d \\d\\d:\\d\\d:\\d\\d .*");
  private static final Pattern TURN_ON_PTN = Pattern.compile("\\d\\d:\\d\\d:\\d\\d \\d\\d/\\d\\d/\\d{4} - .*");
  private class MyInfoField extends InfoField {
    @Override
    public void parse(String field, Data data) {
      boolean include = true;
      for (String part : field.split("\n")) {
        part = MSPACE_PTN.matcher(part.trim()).replaceAll(" ");
        Matcher match = CALLBACK_PTN.matcher(part);
        if (match.find()) {
          String phone = match.group(1);
          if (phone != null) data.strPhone = phone;
          setGPSLoc(append(match.group(2), ",", match.group(3)), data);
          include = false;
          continue;
        }
        if (include) {
          include = !TURN_OFF_PTN.matcher(part).matches();
        } else {
          include = TURN_ON_PTN.matcher(part).matches();
        }
        if (include) data.strSupp = append(data.strSupp, "\n", part);
      }
    }
    
    @Override
    public String getFieldNames() {
      return "INFO PHONE GPS";
    }
  }
}