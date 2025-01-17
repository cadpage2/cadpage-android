package net.anei.cadpage.parsers.KY;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.anei.cadpage.parsers.CodeSet;
import net.anei.cadpage.parsers.MsgInfo.Data;
import net.anei.cadpage.parsers.dispatch.DispatchB2Parser;


public class KYDaviessCountyParser extends DispatchB2Parser {
  
  public KYDaviessCountyParser() {
    super(CITY_LIST, "DAVIESS COUNTY", "KY");
    setupCallList(CALL_LIST);
    setupMultiWordStreets(MWORD_STREET_LIST);
    setupSaintNames("ALPHONSUS", "ANTHONY", "BENEDICT", "JOSEPH", "LAWRENCE");
    removeWords("STREET");
  }
  
  @Override
  public String getFilter() {
    return "911-CENTER,911CENTRAL,2002";
  }
  
  private static final Pattern MARKER = Pattern.compile("911-CENTER:|911CENTRAL:");
  private static final Pattern FIXED_ATTMO_PTN = Pattern.compile("\\bFIXED ATTMO (\\d{10}) OPT\\d\\b");
  private static final Pattern US_PTN = Pattern.compile("\\bU +S\\b", Pattern.CASE_INSENSITIVE);
  
  private boolean marker;
  
  @Override
  protected boolean parseMsg(String body, Data data) {
    
    // Check for prefix
    Matcher match = MARKER.matcher(body);
    marker = match.lookingAt();
    if (marker) body = body.substring(match.end()).trim();
    
    // Clean up weird phone number
    match = FIXED_ATTMO_PTN.matcher(body);
    body = match.replaceFirst("$1");
    
    // These are the only folks I know who split up US highway prefixes
    body = US_PTN.matcher(body).replaceAll("US");
    body = body.replace('\n', ' ');
    return super.parseMsg(body, data);
  }
  
  @Override
  protected boolean isPageMsg(String body) {
    if (marker) return true;
    if (body.contains(" Cad:")) return true;
    return super.isPageMsg(body);
  }
  
  private static final String[] MWORD_STREET_LIST = new String[]{
      "5TH STREET",
      "ADABURG TOWER",
      "ALVEY BRIDGE",
      "ARLINGTON PARK",
      "AUTUMN VALLEY",
      "BEN FORD",
      "BEN HEAD",
      "BETHEL CHURCH",
      "BOLD FORBES",
      "BOLLING HEIGHTS",
      "BOOTH FIELD",
      "BRATCHER HILL",
      "BROWNS VALLEY-RED HILL",
      "CHELSEY PARK",
      "CHESTNUT GROVE",
      "CHUCK GRAY",
      "CLUB GROUNDS",
      "COVINGTON RIDGE",
      "CRANE POND",
      "DARK STAR",
      "DEANFIELD CHURCH",
      "DEER TRAIL",
      "DINNER BELL",
      "DONALD RALPH",
      "ED FOSTER",
      "ELLIS SMEATHERS",
      "FERN HILL",
      "FREE SILVER",
      "FRENCH ISLAND",
      "GIBSON MILL",
      "GOBLER FORD",
      "GRIFFITH STATION",
      "GULF STREAM",
      "HALL SCHOOL",
      "HARBOR HILLS",
      "HARMONS FERRY",
      "HARVEST HILL",
      "HAYDEN BRIDGE",
      "HAYDEN PARK",
      "HAYNES STATION",
      "HEARTLAND CROSSING",
      "HICKORY LAKE",
      "HIDDEN VALLEY",
      "HIGHLAND POINTE",
      "HILL BRIDGE",
      "HURRICANE ISLAND",
      "INDIAN CREEK",
      "INDIAN HILL",
      "ISAAC SHELBY",
      "J R MILLER",
      "JACK HINTON",
      "JOE HAYNES",
      "KARNS GROVE",
      "KELLY CEMETERY",
      "KIDRON VALLEY",
      "KINGFISHER LAKE",
      "KINGS MILL",
      "KNOTSVILLE-MOUNT ZION",
      "KNOTTSVILLE-MOUNT ZION",
      "KNOTTSVILLE-MT ZION",
      "LAKE FOREST",
      "LEE RUDY",
      "LEGION PARK",
      "LITTLE FLOCK CEMETERY",
      "LITTLE HICKORY",
      "LOCUST GROVE",
      "LONESOME PINE",
      "LUTHER TAYLOR",
      "LYDDANE BRIDGE",
      "M L KING JR",
      "MAIN CROSS",
      "MAPLE HEIGHTS",
      "MARTIN LUTHER KING JR",
      "MARY JO",
      "MASONVILLE HABIT",
      "MEADOW DROVE",
      "MILLER MURPHY",
      "MILLERS MILL",
      "MT MORIAH",
      "MT VERNON",
      "MYLES SCHOOL",
      "MYRON HOWARD",
      "PANTHER CREEK PARK",
      "PARK PLAZA",
      "PARRISH PLAZA",
      "PLEASANT GROVE",
      "PLEASANT POINT",
      "PLEASANT RIDGE",
      "PLEASANT VALLEY",
      "POPLAR LOG BRIDGE",
      "PUP CREEK",
      "RED HILL-MAXWELL",
      "RED MILE",
      "ROBY LEASE",
      "ROCKPORT FERRY",
      "RONNIE LAKE",
      "ROSE HILL",
      "ROY WELLS",
      "SAINT LAWRENCE",
      "SAN ANITA",
      "SAN JUAN",
      "SHORT STATION",
      "SOUTH HAMPTON",
      "SPRING CREEK",
      "SPRING HAVEN",
      "ST ANN",
      "ST ANTHONY",
      "ST JOSEPH",
      "ST LAWRENCE",
      "STANLEY-BIRK CITY",
      "SUGAR GROVE CHURCH",
      "SUMMER POINT",
      "TEN OAKS",
      "THRUSTON DERMONT",
      "TIMBER RIDGE",
      "TODD BELL",
      "TODD BRIDGE",
      "TOLER BRIDGE",
      "TOWNE SQUARE",
      "TWENTY GRAND",
      "WATER WHEEL",
      "WAYNE BRIDGE",
      "WENDELL FORD",
      "WEST LOUISVILLE",
      "WHISPERING MEADOWS",
      "WILLIAM H NATCHER",
      "WINDY HILL",
      "WINDY HOLLOW",
      "WINKLER-MT ZION",
      "WINNING COLORS",
      "WOOD BROOK",
      "WRIGHTS LANDING",
      "ZION CHURCH"

  };
  
  private static final CodeSet CALL_LIST = new CodeSet(
      "911 ACCIDENTAL CALL",
      "911 CALL - NO UNITS DISPATCHED",
      "911 HANG UP CALL",
      "911 PREFIX CALL UNABLE TO CALL",
      "ABDOMINAL PAIN",
      "ABDUCTION OF ATTEMPTED ABDUCTI",
      "ACCIDENT WITH INJURIES",
      "ACCIDENT WITH NO INJURIES",
      "ADD NARRATIVE",
      "AIRCRAFT ALERT",
      "AL CAR ALARM",
      "AL ROBBERY ALARM",
      "ALARM ELEVATOR",
      "ALARM PANIC",
      "ALLERGIC REACTION",
      "ANIMAL COMPLAINT",
      "ARSON",
      "ASSIST ENROUTE (CLI)",
      "ASSIST OTHER AGENCY",
      "ASSUALT",
      "AUTO THEFT",
      "AUTOMOBILE ACCIDENT DEER",
      "BACK PAIN",
      "BE ON THE LOOKOUT",
      "BIO-HAZARD AND THREATS",
      "BLEEDING",
      "BOMB SQUAD CALL OUT",
      "BOMB THREAT",
      "BREATHING DIFFICULTY",
      "BRIDGE CHECK",
      "BUNCO REPORT",
      "BURGLARY ALARM",
      "BURGLARY ALL OTHER",
      "BURGLARY NOT IN PROGRESS",
      "BURGLARY RESIDENTIAL",
      "BURN SUBJECT",
      "CA JUVENILE CHILD ABUSE",
      "CALL SWITCHED TO COUNTY AGENCY",
      "CALL SWITCHED TO STATE AGENCY",
      "CAR COMPLAINT - UNOCCUPIED",
      "CARBON MONOXIDE ALARM",
      "CARDIAC ARREST",
      "CHEST PAIN\\DISCOMFORT\\TIGHTNES",
      "CHILD ABUSE",
      "CHILD CUSTODY COMPLAINT",
      "CHILD LOCKED IN CAR",
      "CHOKING",
      "CIVIL COMPLAINT",
      "CK PERIODIC CHECK",
      "CORONER CALL OUT",
      "CRIMINAL MISCHIEF",
      "CRUISER REPAIR",
      "CUTTING",
      "D OPEN DOOR",
      "DEATH INVESTIGATION",
      "DIABETIC PROBLEMS",
      "DISTURBANCE DOMESTIC",
      "DISTURBANCE OR FIGHT",
      "DROWNING",
      "DRUG COMPLAINT",
      "DRUNK COMPLAINT",
      "DUI COMPLAINT",
      "ELECTION COMPLAINT",
      "ELECTROCUTION",
      "EPO (SERVING EPO)",
      "EQUIPMENT TESTING FOR DISPATCH",
      "ERT CALL OUT",
      "ESCAPE/WALK OFF FROM JAIL",
      "ESCORT FUNERAL BRIDGE ETC",
      "EXECUTION OF COURT ORDER",
      "EXPLOSION",
      "EXPLOSIVE DISPOSAL",
      "FBI GUN NOTIFICATION/DVO/EPO",
      "FIRE ALARM TESTING",
      "FIRE ALARM",
      "FIRE ARM DISCHARGING",
      "FIRE CHIMNEY",
      "FIRE DUMPSTER",
      "FIRE POLE",
      "FIRE STRUCTURE",
      "FIRE TREE",
      "FIRE VEHICLE",
      "FIREWORKS COMPLAINT",
      "FOLLOW UP COMPLAINT",
      "FORGERY COMPLAINT",
      "FOUND PROPERTY",
      "FRAUD COMPLAINT",
      "GAS DRIVE OFF",
      "GAS LEAK - INSIDE",
      "GAS LEAK - OUTSIDE",
      "GENERATOR TEST",
      "HAASSMENT COMPLAINT",
      "HAZARD IN THE ROAD",
      "HAZARDOUS CHEMICAL COMPLAINT",
      "HEADACHE",
      "HEAT EMERGENCIES",
      "HOMICIDE",
      "HOSTAGE SITUATION",
      "HOUSE CHECK",
      "HUNTING COMPLAINT",
      "ILLEGAL BURNING",
      "ILLEGAL DUMPING",
      "IMPROPER PARKING IN ROAD",
      "INDECENT EXPOSURE",
      "INDUSTRIAL ACCIDENTS",
      "INFORMATION ONLY",
      "JUMPER",
      "JUVENILE COMPLAINT",
      "JUVENILE RUNAWAY",
      "K-9 REQUEST",
      "LIFT ASSIST",
      "LOG INFORMATION",
      "LOST CHILD (WALK AWAY)",
      "LOST PROPERTY",
      "LOT CLEARING",
      "MAN WITH WEAPON",
      "MEDICAL ALARM",
      "MEDICAL EMERGENCY",
      "MEDICAL UNKNOWN",
      "MENTAL COMPLAINT",
      "MESSAGE DELIVERY",
      "MISSING PERSON REPORT",
      "MOTORIST ASSIST",
      "N RAPE NOT IN PROGRESS",
      "NOISE COMPLAINT",
      "NONLISCENSED VEH/ATV/MOTORCYCL",
      "OUT OF COUNTY TRIP",
      "PARKING COMPLAINT",
      "PARTY COMPLAINT",
      "PERSON DOWN",
      "POISONING",
      "PREGNANCY/OB/GYN",
      "PRISONER TRANSPORT WESTERN ST",
      "PRISONER TRANSPORT",
      "PRISONER",
      "PROPERTY DAMAGE STORM RELATED",
      "PROPERTY DMG NON WEATHER",
      "PROWLER OR PEEPING TOM",
      "PSYCHIATRIC PROBLEM",
      "PT CHECK POINT",
      "PUBLIC SAFETY COMMUNICATION",
      "PURSUIT",
      "RADIO PROBLEMS",
      "RANGE ACTIVATION (TRAINING)",
      "RAPE IN PROGRESS",
      "RECKLESS DRIVING",
      "RECOVERED PROPERTY",
      "RESPOSSESSION",
      "ROBBERY",
      "SA JUVENILE SEXUAL ABUSE REPORT",
      "SCHOOL TRAFFIC",
      "SEIZURES",
      "SELF INITATED",
      "SERVICE CALL NON-EMERGENCY FIR",
      "SERVICE CALL SHERIFF",
      "SERVICE CALL",
      "SEXUAL ABUSE OF AN ADULT",
      "SHOOTING",
      "SHOPLIFTING COMPLAINT",
      "SMOKING ORDINANCE VIOLATION",
      "STABBING SHOOTING PENETRATING",
      "STANDBY",
      "STROKE",
      "SUBJECT PURSUIT",
      "SUICIDE THREAT OR ATTEMPT",
      "SUSP INDIVIDUAL ON SCENE (CLI)",
      "SUSP PERSON/VEH ON SCENE (CLI)",
      "SUSPICIOUS CIRCUMSTANCES",
      "TAPE CHANGE FOR IN CAR VIDEO",
      "TDD TEST",
      "TERRORISTIC THREATENING",
      "TEST",
      "THEFT COMPLAINT",
      "THEFT FROM AUTO",
      "THEFT FROM",
      "TOBACCO VIOLATION",
      "TOWING COMPLAINT",
      "TR JUVENILE TRANSPORT TO BG",
      "TRAFFIC COMPLAINT",
      "TRAFFIC PURSUIT (CLI)",
      "TRAFFIC STOP (CLI)",
      "TRAFFIC STOP",
      "TRAUMATIC INJURY",
      "TREE DOWN",
      "TRESSPASSING COMPLAINT",
      "UNCONCIOUS",
      "UNDER INVESTIGATION",
      "UNKNOWN/PERSON DOWN",
      "UNLAWFAL USE OF",
      "UNLOCK VEHICLE",
      "VEHICLE INSPECTION",
      "VIOLATION OF AN E.P.O.",
      "WARRANT EMERGENCY MEDICAL",
      "WARRANT SERVICE OR CHECK",
      "WATER EMERGENCY OHIO RIVER",
      "WATER EMERGENCY",
      "WEBCAD EVENT",
      "WELFARE CHECK",
      "WILDLAND FIRE",
      "WIRE DOWN",
      "WRIT OF POSSESSION"
  );
  
  private static final String[] CITY_LIST = new String[]{
      "OWENSBORO",

      "WEST DAVIESS",

      "CURDSVILLE",
      "DELAWARE",
      "MOSELEYVILLE",
      "PANTHER",
      "PETTIT",
      "ROME",
      "SAINT JOSEPH",
      "SORGHO",
      "STANLEY",
      "SUTHERLAND",
      "TUCK",
      "UTICA",
      "WEST LOUISVILLE",

      "EAST DAVIESS",

      "DERMONT",
      "ENSOR",
      "HABIT",
      "KNOTTSVILLE",
      "MACEO",
      "MASONVILLE",
      "PHILPOT",
      "THRUSTON",
      "WHITESVILLE",
      "YELVINGTON",
      
      // Hancock County
      "HANCOCK COUNTY",
      "HANCOCK CO",
      "HAWESVILLE",
      "LEWISPORT",
      
      // Mclean County
      "MCLEAN COUNTY",
      "MCLEAN CO",
      "CALHOUN",
      
      // Ohio County
      "OHIO COUNTY",
      "OHIO CO",
      "HARTFORD",
      "REYNOLDS STATION"
  };
}
