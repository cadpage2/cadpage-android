package net.anei.cadpage.parsers.AL;

import net.anei.cadpage.parsers.dispatch.DispatchSouthernPlusParser;

public class ALJeffersonCountyGParser extends DispatchSouthernPlusParser {
    
  public ALJeffersonCountyGParser() {
    super(CITY_LIST, "JEFFERSON COUNTY", "AL", DSFLAG_CROSS_NAME_PHONE | DSFLAG_LEAD_UNIT);
  }
 
  private static final String[] CITY_LIST = new String[]{
      //Cities

      "ADAMSVILLE",
      "BESSEMER",
      "BIRMINGHAM",
      "BRIGHTON",
      "CENTER POINT",
      "CLAY",
      "FAIRFIELD",
      "FULTONDALE",
      "GARDENDALE",
      "GRAYSVILLE",
      "HELENA",
      "HOMEWOOD",
      "HOOVER",
      "HUEYTOWN",
      "IRONDALE",
      "KIMBERLY",
      "LEEDS",
      "LIPSCOMB",
      "MIDFIELD",
      "MOUNTAIN BROOK",
      "PINSON",
      "PLEASANT GROVE",
      "SUMITON",
      "TARRANT",
      "TRUSSVILLE",
      "VESTAVIA HILLS",
      "WARRIOR",

      //Towns

      "ARGO",
      "BROOKSIDE",
      "CARDIFF",
      "COUNTY LINE",
      "MAYTOWN",
      "MORRIS",
      "MULGA",
      "NORTH JOHNS",
      "SYLVAN SPRINGS",
      "TRAFFORD",
      "WEST JEFFERSON",

      //Census-designated places

      "CHALKVILLE",
      "CONCORD",
      "EDGEWATER",
      "FORESTDALE",
      "GRAYSON VALLEY",
      "MCDONALD CHAPEL",
      "MINOR",
      "MOUNT OLIVE",
      "ROCK CREEK",

      //Unincorporated communities

      "ADGER",
      "ALTON",
      "COALBURG",
      "CORNER",
      "CRUMLEY CHAPEL",
      "DOCENA",
      "DOLOMITE",
      "FLAT TOP",
      "HOPEWELL",
      "KIMBRELL",
      "MCCALLA",
      "NEW CASTLE",
      "PALMERDALE",
      "SAYRE",
      "SHANNON",
      "WATSON"
  };

}