package net.anei.cadpage.parsers.IN;

import java.util.regex.Pattern;

import net.anei.cadpage.parsers.CodeSet;
import net.anei.cadpage.parsers.MsgInfo.Data;
import net.anei.cadpage.parsers.dispatch.DispatchA48Parser;

public class INShelbyCountyBParser extends DispatchA48Parser {
  
  public INShelbyCountyBParser() {
    super(CITY_LIST, "SHELBY COUNTY", "IN", FieldType.PLACE,  A48_NO_CODE, 
          Pattern.compile("\\d{3}|[A-Z]+\\d+"));
    setupCallList(CALL_LIST);
  }
  
  @Override
  protected boolean parseMsg(String subject, String body, Data data) {
    body = stripFieldStart(body, "Dispatch: ; ");
    return super.parseMsg(subject, body, data);
  }
  
  private static final CodeSet CALL_LIST = new CodeSet(
      "ACC PI",
      "ACC UKN",
      "ATT SUICIDE",
      "DOM VIO",
      "FIRE",
      "FIRE ALARM",
      "MEDIC",
      "SUSPIC",
      "WELL BEING"
 );
  
  private static final String[] CITY_LIST = new String[]{
    
      //Cities and towns
      "EDINBURGH",
      "FAIRLAND",
      "MORRISTOWN",
      "ST PAUL",
      "SHELBYVILLE",

      //Unincorporated towns
      "BLUE RIDGE",
      "BROOKFIELD",
      "BOGGSTOWN",
      "FLAT ROCK",
      "FOUNTAINTOWN",
      "GENEVA",
      "GWYNNEVILLE",
      "LONDON",
      "MARIETTA",
      "MEIKS",
      "MOUNT AUBURN",
      "SUGAR CREEK",
      "WALDRON",

      //Townships
      "ADDISON",
      "BRANDYWINE",
      "HANOVER",
      "HENDRICKS",
      "JACKSON",
      "LIBERTY",
      "MARION",
      "MORAL",
      "NOBLE",
      "SHELBY",
      "SUGAR CREEK",
      "UNION",
      "VAN BUREN",
      "WASHINGTON"
  };
}