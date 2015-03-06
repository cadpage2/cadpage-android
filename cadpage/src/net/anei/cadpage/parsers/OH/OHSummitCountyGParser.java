package net.anei.cadpage.parsers.OH;

import net.anei.cadpage.parsers.dispatch.DispatchA39Parser;

public class OHSummitCountyGParser  extends DispatchA39Parser {
  
  public OHSummitCountyGParser() {
    super(OHSummitCountyParser.CITY_LIST, "TWINSBURG", "OH");
  }

  @Override
  public String getFilter() {
    return "dispatch@twinsburg.local";
  }
}