package net.anei.cadpage.parsers.OH;

import java.util.Properties;

import net.anei.cadpage.parsers.FieldProgramParser;
import net.anei.cadpage.parsers.MsgInfo.Data;

public class OHButlerCountyBParser extends FieldProgramParser {

  /**
   * Butler County, OH (B)
   */

  public OHButlerCountyBParser () {
    super(CITY_CODES, "BUTLER COUNTY", "OH",
          "Loc:ADDR/S! TYPE:CALL! TIME:TIME! Comm:INFO");
  }
  
  @Override
  public String getFilter() {
    return "IPAGE@IPSCAD";
  }

  @Override
  public Field getField(String name) {
    if (name.equals("ADDR")) return new MyAddressField();
    if (name.equals("CALL")) return new MyCallField();
    if (name.equals("TIME")) return new TimeField("\\d\\d:\\d\\d:\\d\\d", true);
    return super.getField(name);
  }

  private class MyAddressField extends AddressField {
    
    @Override
    public void parse(String field, Data data) {
      Parser p = new Parser(field);
      data.strPlace = p.getLastOptional(": @");
      super.parse(p.get(), data);
    }
    
    @Override
    public String getFieldNames() {
      return super.getFieldNames() + " PLACE";
    }
  }

  private class MyCallField extends CallField {
    @Override 
    public void parse(String field, Data data) {
      data.strCall = append(convertCodes(field, CALL_CODES), " ", data.strCall);      
    }
    
    @Override
    public String getFieldNames() {
      return "PRI CALL";
    }
  }
  
  private static final Properties CITY_CODES = buildCodeTable(new String[]{
      "BC COLC", "COLLEGE CORNER",
      "BC FFCY", "FAIRFIELD",
      "BC FFTW", "FAIRFIELD TWP",
      "BC HAMI", "HAMILTON",
      "BC HANT", "HANOVER TWP",
      "BC JACB", "JACKSONBURG",
      "BC LEMT", "LEMON TWP",
      "BC LIBT", "LIBERTY TWP",
      "BC MADT", "MADISON TWP",
      "BC MIDT", "MIDDLETOWN",
      "BC MILT", "MILFORD TWP",
      "BC MILV", "MILLVILLE",
      "BC MONR", "MONROE",
      "BC MORT", "MORGAN TWP",
      "BC NMIA", "NEW MIAMI",
      "BC OXFC", "OXFORD",
      "BC OXFT", "OXFORD TWP",
      "BC REIT", "REILY TWP",
      "BC ROST", "ROSS TWP",
      "BC SCLT", "ST CLAIR TWP",
      "BC SEVM", "SEVEN MILE",
      "BC SOMV", "SOMERVILLE",
      "BC TREN", "TRENTON",
      "BC WAYT", "WAYNE TWP",
      "BC WCHT", "WEST CHESTER TWP",
      "HC AMBV", "AMBERLY VILLAGE",
      "HC ANDT", "ANDERSON TWP",
      "HC ARLH", "ARLINGTON HEIGHTS",
      "HC BLUA", "BLUE ASH  HAMILTON",
      "BC BRN",  "BROWN COUNTY",
      "WC CARL", "CARLISLE",
      "HC CHEV", "CHEVIOT",
      "HC CINN", "CINCINNATI",
      "HC CLEV", "CLEVES",
      "HC CLOV", "CLOVEDALE",
      "CC CLRT", "CLERMONT COUNTY",
      "HC COLT", "COLRAIN TWP",
      "WC CRBT", "CROSBY TWP",
      "HC DEEP", "DEER PARK",
      "HC DELT", "DELHI TWP",
      "HC DENT", "DENT",
      "WC DERT", "DEERFIELD TWP",
      "HC DILV", "DILLENVALE",
      "HC ELMP", "ELMWOOD PLACE",
      "HC EVED", "EVENDALE",
      "HC FAIX", "FAIRFAX",
      "HC FINT", "FINNYTOWN",
      "HC FORP", "FOREST PARK",
      "WC FRKC", "FRANKLIN",
      "WC FRKT", "FRANKLIN TWP",
      "HC GLED", "GLENDALE",
      "HC GOM",  "GOLF MANOR",
      "HC GREH", "GREEN HILLS",
      "HC GROB", "GROSBECK",
      "HC HARR", "HARRISON",
      "HC HART", "HARRISON TWP",
      "WC HARV", "HARVYESBURG",
      "HC INDH", "INDIAN HILL",
      "HC KENW", "KENWOOD",
      "HC LAND", "LANDEN",
      "WC LEBN", "LEBANON",
      "HC LINH", "LINCOLN HEIGHTS",
      "HC LOVH", "LOVELAND",
      "HC MADE", "MADEIRA",
      "HC MAIV", "MAINVILLE",
      "HC MARI", "MARIEMONT",
      "WC MASO", "MASON",
      "HC MHEA", "MOUNT HEALTHY",
      "HC MIAT", "MIAMI TWP",
      "HC MILF", "MILFORD",
      "HC MOHT", "MONFORD HEIGHTS",
      "HC MONT", "MONTGOMERY",
      "WC MORR", "MORROW",
      "HC NBEN", "NORTH BEND",
      "HC NCH",  "NORTH COLLEGE HILL",
      "HC NEWT", "NEWTOWN",
      "HC NORB", "NORTHBROOK",
      "HC NORW", "NORWOOD",
      "HC READ", "READING",
      "WC SALT", "SALEM TWP",
      "HC SBER", "ST. BERNARD",
      "HC SHAV", "SHARONVILLE",
      "HC SILV", "SILVERTON",
      "HC SPFT", "SPRINGFIELD TWP",
      "WC SPRB", "SPRINGBORO",
      "HC SPRD", "SPRINGDALE",
      "HC SYCA", "SYCAMORE",
      "HC SYMM", "SYMMS TWP",
      "HC TERP", "TERRACE PARK",
      "WC TURT", "TURTLE CREEK TWP",
      "UNIT",    "UNION TWP",
      "WAST",    "WASHINGTON TWP",
      "WC WAYV", "WAYNESVILLE",
      "HC WHTO", "WHITE OAK",
      "HC WHTW", "WHITEWATER TWP",
      "HC WODL", "WOODLAWN",
      "HC WYOM", "WYOMING",
  });

  private static final Properties CALL_CODES = buildCodeTable(new String[]{
  "SQUAD",    "SQUAD RUN",
  "SUICIDE",  "SUICIDE ATTEMPT",
  "FIRE",     "FIRE",
  "FLUSH",    "FLUSH",
  "ELEVATOR", "ELEVATOR PROBLEMS",
  "ICE",      "ICE RESCUE",
  "HAZMAT",   "HAZMAT LEAK OR SPILL",
  "SUSPMAT",  "SUSPICIOUS MATERIAL",
  "PUBASST",  "PUBLIC ASSIST",
  "ACCIDENT", "AUTO ACCIDENT",
  "BOMB",     "BOMB THREAT",
  "TECHRES",  "TECHNICAL RESCUE CALLOUT",
  "DEATH",    "DEAD PERSON FOUND",
  "MEDICAL",  "MEDICAL TEST EVENT TYPE",
  "MISSING",  "MISSING PERSON REPORT",
  "ALARM",    "ALARM"
  });
}

/* City Codes
  COLC  COLLEGE CORNER  BUTLER COUNTY
  FFCY  FAIRFIELD CITY  BUTLER COUNTY
  FFTW  FAIRFIELD TOWNSHIP  BUTLER COUNTY
  HAMI  HAMILTON CITY BUTLER COUNTY
  HANT  HANOVER TOWNSHIP  BUTLER COUNTY
  JACB  JACKSONBURG BUTLER COUNTY
  LEMT  LEMON TOWNSHIP  BUTLER COUNTY
  LIBT  LIBERTY TOWNSHIP  BUTLER COUNTY
  MADT  MADISON TOWNSHIP  BUTLER COUNTY
  MIDT  MIDDLETOWN  BUTLER COUNTY
  MILT  MILFORD TOWNSHIP  BUTLER COUNTY
  MILV  MILLVILLE BUTLER COUNTY
  MONR  MONROE  BUTLER COUNTY
  MORT  MORGAN TOWNSHIP BUTLER COUNTY
  NMIA  NEW MIAMI BUTLER COUNTY
  OXFC  OXFORD CITY BUTLER COUNTY
  OXFT  OXFORD TOWNSHIP BUTLER COUNTY
  REIT  REILY TOWNSHIP  BUTLER COUNTY
  ROST  ROSS TOWNSHIP BUTLER COUNTY
  SCLT  ST. CLAIR TOWNSHIP  BUTLER COUNTY
  SEVM  SEVEN MILE  BUTLER COUNTY
  SOMV  SOMERVILLE  BUTLER COUNTY
  TREN  TRENTON BUTLER COUNTY
  WAYT  WAYNE TOWNSHIP  BUTLER COUNTY
  WCHT  WEST CHESTER TOWNSHIP BUTLER COUNTY
  AMBV  AMBERLY VILLAGE HAMILTON COUNTY
  ANDT  ANDERSON TOWNSHIP HAMILTON COUNTY
  ARLH  ARLINGTON HEIGHTS HAMILTON COUNTY
  BLUA  BLUE ASH  HAMILTON COUNTY
  BRN BROWN COUNTY  
  CARL  CARLISLE  WARREN COUNTY
  CHEV  CHEVIOT HAMILTON COUNTY
  CINN  CINCINNATI  HAMILTON COUNTY
  CLEV  CLEVES  HAMILTON COUNTY
  CLOV  CLOVEDALE HAMILTON COUNTY
  CLRT  CLERMONT  
  COLT  COLRAIN TOWNSHIP  HAMILTON COUNTY
  CRBT  CROSBY TOWNSHIP WARREN COUNTY
  DEEP  DEER PARK HAMILTON COUNTY
  DELT  DELHI TOWNSHIP  HAMILTON COUNTY
  DENT  DENT  HAMILTON COUNTY
  DERT  DEERFIELD TOWNSHIP  WARREN COUNTY
  DILV  DILLENVALE  HAMILTON COUNTY
  ELMP  ELMWOOD PLACE HAMILTON COUNTY
  EVED  EVENDALE  HAMILTON COUNTY
  FAIX  FAIRFAX HAMILTON COUNTY
  FINT  FINNYTOWN HAMILTON COUNTY
  FORP  FOREST PARK HAMILTON COUNTY
  FRKC  FRANKLIN CITY WARREN COUNTY
  FRKT  FRANKLIN TOWNSHIP WARREN COUNTY
  GLED  GLENDALE  HAMILTON COUNTY
  GOM GOLF MANOR  HAMILTON COUNTY
  GREH  GREEN HILLS HAMILTON COUNTY
  GROB  GROSBECK  HAMILTON COUNTY
  HARR  HARRISON  HAMILTON COUNTY
  HART  HARRISON TOWNSHIP HAMILTON COUNTY
  HARV  HARVYESBURG 
  HAST    
  INDH  INDIAN HILL HAMILTON COUNTY
  KENW  KENWOOD HAMILTON COUNTY
  KINI    
  LAND  LANDEN  HAMILTON COUNTY
  LEBN  LEBANON WARREN COUNTY
  LINH  LINCOLN HEIGHTS HAMILTON COUNTY
  LOCL  LOCKLAND  HAMILTON COUNTY
  LOVH  LOVELAND  HAMILTON COUNTY
  LOVP    
  LOVW    
  MACN    
  MACS    
  MADE  MADEIRA HAMILTON COUNTY
  MAIV  MAINVILLE HAMILTON COUNTY
  MARI  MARIEMONT HAMILTON COUNTY
  MASO  MASON WARREN COUNTY
  MAST    
  MHEA  MOUNT HEALTHY HAMILTON COUNTY
  MIAT  MIAMI TOWNSHIP  HAMILTON COUNTY
  MILF  MILFORD HAMILTON COUNTY
  MOHT  MONFORD HEIGHTS HAMILTON COUNTY
  MONT  MONTGOMERY  HAMILTON COUNTY
  MONW    
  MORR  MORROW  WARREN COUNTY
  NBEN  NORTH BEND  HAMILTON COUNTY
  NCH NORTH COLLEGE HILL  HAMILTON COUNTY
  NEWT  NEWTOWN HAMILTON COUNTY
  NORB  NORTHBROOK  HAMILTON COUNTY
  NORW  NORWOOD HAMILTON COUNTY
  PLEF    
  PLER    
  READ  READING HAMILTON COUNTY
  SALT  SALEM TOWNSHIP  WARREN COUNTY
  SBER  ST. BERNARD HAMILTON COUNTY
  SHAV  SHARONVILLE HAMILTON COUNTY
  SILV  SILVERTON HAMILTON COUNTY
  SLEB    
  SPFT  SPRINGFIELD TOWNSHIP  HAMILTON COUNTY
  SPRB  SPRINGBORO  WARREN COUNTY
  SPRD  SPRINGDALE  HAMILTON COUNTY
  SYCA  SYCAMORE  HAMILTON COUNTY
  SYMM  SYMMS TOWNSHIP  HAMILTON COUNTY
  TERP  TERRACE PARK  HAMILTON COUNTY
  TURT  TURTLE CREEK TOWNSHIP WARREN COUNTY
  US    
  UNIT  UNION TOWNSHIP  
  WAST  WASHINGTON TOWNSHIP 
  WAYV  WAYNESVILLE WARREN COUNTY
  WAYW    
  WHTE    
  WHTO  WHITE OAK HAMILTON COUNTY
  WHTT    
  WHTW  WHITEWATER TOWNSHIP HAMILTON COUNTY
  WODL  WOODLAWN  HAMILTON COUNTY
  WYOM  WYOMING HAMILTON COUNTY
  WCPS 

*/

