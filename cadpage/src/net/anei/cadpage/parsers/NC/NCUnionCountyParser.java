package net.anei.cadpage.parsers.NC;

/**
 * Union County, NC
 */
import java.util.Properties;
import java.util.regex.Pattern;

import net.anei.cadpage.parsers.MsgInfo.Data;
import net.anei.cadpage.parsers.dispatch.DispatchOSSIParser;


public class NCUnionCountyParser extends DispatchOSSIParser {
  
  public NCUnionCountyParser() {
    super(CITY_LIST, "UNION COUNTY", "NC",
           "( CANCEL ADDR CITY2 INFO+ | FYI? ID? ADDR ( CITY ID? | CITY/Z ID | ID? ) CALL! SRC? CUSTOM? INFO+? DATETIME ID? PLACE )");
    setupSaintNames("JOHNS", "SIMONS");
    setupProtectedNames("BURGESS AND HELMS", "SUGAR AND WINE");
  }
  
  @Override
  public String getFilter() {
    return "cad@uc.co.union.nc.us,cad@webmail.co.union.nc.us,cad@co.union.nc.us";
  }
  
  @Override
  protected Field getField(String name) {
    if (name.equals("CITY2")) return new MyCity2Field();
    if (name.equals("SRC")) return new SourceField("[A-Z0-9]{2,4}", true);
    if (name.equals("CUSTOM")) return new CustomField();
    if (name.equals("INFO")) return new MyInfoField();
    if (name.equals("DATETIME")) return new DateTimeField("\\d\\d/\\d\\d/\\d{4} \\d\\d:\\d\\d:\\d\\d");
    if (name.equals("ID")) return new IdField("\\d{5,}");
    return super.getField(name);
  }
  
  private class MyCity2Field extends CityField {
    @Override
    public void parse(String field, Data data) {
      data.strCity = convertCodes(field, CITY_CODES);
    }
  }
  
  // Custom field is an optional 6 character field that
  // different departments use for different purposes.  We will look at the
  // previous source field to figure out which department this is and what
  // they want us to do with it.
  
  private static final Pattern SRC_CHANNEL_PTN = Pattern.compile("S\\d\\d");
  private class CustomField extends Field {
    
    @Override
    public boolean canFail() {
      return true;
    }
    
    @Override
    public boolean checkParse(String field, Data data) {
      if (field.length() > 6 || field.contains(" ")) return false;
      parse(field, data);
      return true;
    }

    @Override
    public void parse(String field, Data data) {
      if (SRC_CHANNEL_PTN.matcher(data.strSource).matches()) {
        data.strChannel = field;
      } else {
        data.strUnit = field;
      }
    }
    
    @Override
    public String getFieldNames() {
      return "UNIT CH";
    }
  }
  
  private class MyInfoField extends InfoField {
    @Override
    public void parse(String field, Data data) {
      if (isValidAddress(field)) {
        data.strCross = append(data.strCross, " & ", field);
      } else if (data.strPlace.length() == 0) {
        data.strPlace = field;
      } else {
        data.strSupp = append(data.strSupp, " / ", field);
      }
    }
    
    @Override
    public String getFieldNames() {
      return "X PLACE INFO";
    }
  }
  
  private static final String[] CITY_LIST = new String[]{
    "FAIRVIEW",
    "HEMBY BRIDGE",
    "INDIAN TRAIL",
    "LAKE PARK",
    "MARSHVILLE",
    "MARVIN",
    "MINERAL SPRINGS",
    "MONROE",
    "STALLINGS",
    "UNIONVILLE",
    "WAXHAW",
    "WEDDINGTON",
    "WESLEY CHAPEL",
    "WINGATE",
    
    // Anson County
    "PEACHLAND",
    
    // Mecklenburg County
    "MATTHEWS"
  };
  
  // CIty codes are only used for cance/under control/confirmed fire updates
  private static final Properties CITY_CODES = buildCodeTable(new String[]{
      "FAI", "FAIRVILLE",
      "HEM", "HEMBY BRIDGE",
      "IND", "INDIAN TRAIL",
      "LAK", "LAKE PARK",    //???
      "MAR", "MARVIN",       //???
      "MAT", "MATTHEWS",
      "MIN", "MINERAL SPRINTS", //???
      "MON", "MONROE",
      "MSH", "MARSHVILLE",
      "STA", "STALLINGS",
      "UNI", "UNIONVILLE",
      "WAX", "WAXHAW",
      "WED", "WEDDINGTON",
      "WES", "WESLEY CHAPEL",
      "WIN", "WINGATE"
  });
}
