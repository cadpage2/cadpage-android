package net.anei.cadpage.parsers.NC;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.anei.cadpage.parsers.FieldProgramParser;
import net.anei.cadpage.parsers.MsgInfo.Data;

public class NCYadkinCountyParser extends FieldProgramParser {
  
  public NCYadkinCountyParser() {
    super(CITY_LIST, "YADKIN COUNTY", "NC", 
          "ADDR/SP! X? CODE? CALL! geo:GPS? INFO+");
  }
  
  @Override
  public String getProgram() {
    return super.getProgram() + " ID";
  }
  
  @Override
  public int getMapFlags() {
    return MAP_FLG_PREFER_GPS;
  }
  
  public static Pattern ID_EXTRACTOR = Pattern.compile("(.*?) *OCA: *(\\d{7})");
  @Override
  protected boolean parseMsg(String subject, String body, Data data) {
    
    //fix weird subjectless formatting that occurs ~25% of the time
    if (!subject.contains("Text Message") && 
        (subject.endsWith(";geo") || subject.endsWith(" OCA"))) {
      body = subject + ":" + stripFieldStart(body, "CAD:");
      subject = "";
    }
    
    // Rule out OSSI based pages
    if (!subject.equals("Text Message") && body.startsWith("CAD:")) return false;;
        
    //remove OCA: blah and pass blah to callID
    Matcher idMat = ID_EXTRACTOR.matcher(body);
    if (idMat.matches()) {
      body = idMat.group(1);
      data.strCallId = idMat.group(2);
    }

    if (!parseFields(body.split(";"), data)) return false;
    if (data.strCity.equalsIgnoreCase("BOONEVILLE")) data.strCity = "BOONVILLE";
    return true;
  }
  
  @Override public Field getField(String name) {
    if (name.equals("ADDR")) return new MyAddressField();
    if (name.equals("X")) return new MyCrossField();
    if (name.equals("CODE")) return new CodeField("[MF]DL +(.*)", true);
    return super.getField(name);
  }
  
  private static final Pattern TRAIL_PAREN_PTN = Pattern.compile("(.*?)\\((.*)\\)");
  private class MyAddressField extends AddressField {
    @Override
    public void parse(String field, Data data) {
      String trailer = null;
      int pt = field.indexOf("//");
      if (pt >= 0) {
        trailer = field.substring(pt+2).trim();
        field = field.substring(0,pt);
      }
      
      super.parse(field, data);
      
      if (trailer != null) {
        if (data.strCity.length() == 0) {
          parseAddress(StartType.START_OTHER, FLAG_ONLY_CITY | FLAG_ANCHOR_END, trailer, data);
          trailer = getStart();
        }
        data.strPlace = append(data.strPlace, " - ", trailer);
      }
      
      if (data.strCity.length() == 0) {
        Matcher match = TRAIL_PAREN_PTN.matcher(data.strAddress);
        if (match.matches()) {
          String city = match.group(2).trim();
          if (isCity(city)) {
            data.strCity = city;
            data.strAddress = match.group(1).trim();
          }
        }
      }
      
      if (data.strCity.toUpperCase().endsWith("CO")) {
        if (data.strCity.endsWith("O")) data.strCity += "UNTY";
        else data.strCity += "unty";
      }
    }
  }
  
  private static Pattern CROSS_PTN = Pattern.compile("(.*?)(?: +\\(Verify\\))? *\\bX\\b *(.*?)(?: +\\(Verify\\))?");
  private class MyCrossField extends CrossField {
    
    @Override
    public boolean canFail() {
      return true;
    }
    
    @Override
    public boolean checkParse(String field, Data data) {
      Matcher match = CROSS_PTN.matcher(field);
      if (!match.matches()) return false;
      field = append(match.group(1), " / ", match.group(2)).replace("*", "");
      super.parse(field, data);
      return true;
    }
    
    @Override
    public void parse(String field, Data data) {
      if (!checkParse(field, data)) abort();
    }
  }
  
  public static String[] CITY_LIST = new String[]{
    
    // cities and towns
    "BOONEVILLE",
    "BOONVILLE",
    "EAST BEND",
    "JONESVILLE",
    "SURRY",
    "YADKINVILLE",
    
    //towns of the past 
    "ARLINGTON",
    "HAMPTONVILLE",
    "HUNTSVILLE",
    "SHORE",
    "SMITHTOWN",
  
    // Unincorporated communities
    "BARNEY HILL",
    "BRANON",
    "BUCK SHOALS",
    "CENTER",
    "BROOKS' CROSSROADS",
    "COURTNEY",
    "ENON",
    "FLINT HILL",
    "FOOTVILLE",
    "FORBUSH",
    "HAMPTONVILLE",
    "HARMONY HEIGHTS",
    "HUNTSVILLE",
    "LONE HICKORY",
    "LONGTOWN",
    "MARLER",
    "RICHMOND HILL",
    "SWAN CREEK",
    "UNION HILL",
    "WINDSOR'S CROSSROADS",
    "WYO",
    
    // County names
    "DAVIE",
    "DAVIE CO",
    "DAVIE COUNTY",
    "FORSYTH",
    "FORSYTH CO",
    "FORSYTH COUNTY",
    "IREDELL",
    "IREDELL CO",
    "IREDELL COUNTY",
    "SURRY",
    "SURRY CO",
    "SURRY COUNTY",
    "WILKES",
    "WILKES CO",
    "WILKES COUNTY",
    "YADKIN",
    "YADKIN CO",
    "YADKIN COUNTY",
    
    // Iredell County
    "HARMONY",
    "UNION GROVE",
    
    // Guilford County
    "GREENSBORO",
    
    // Surry County
    "ELKIN"
    
  };

}