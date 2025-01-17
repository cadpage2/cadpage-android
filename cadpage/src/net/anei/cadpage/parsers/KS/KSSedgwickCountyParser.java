package net.anei.cadpage.parsers.KS;

import java.util.Properties;
import java.util.regex.Pattern;

import net.anei.cadpage.parsers.MsgInfo.Data;
import net.anei.cadpage.parsers.dispatch.DispatchA21Parser;


public class KSSedgwickCountyParser extends DispatchA21Parser {
  
  public KSSedgwickCountyParser() {
    super(CITY_CODES, CALL_CODES, "SEDGWICK COUNTY", "KS");
  }
  
  @Override
  public String getFilter() {
    return "cad_admin@sedgwick.gov";
  }
  
  @Override
  protected boolean parseMsg(String subject, String body, Data data) {
    String[] parts= subject.split("\\|");
    if (parts.length != 2 || !parts[0].equals("CommandPoint CAD Message")) return false;
    return super.parseMsg(parts[1], body, data);
  }
  
  @Override
  public String adjustMapAddress(String addr) {
    addr = KNN_PTN.matcher(addr).replaceAll("STATE $1");
    return addr;
  }
  private static final Pattern KNN_PTN = Pattern.compile("\\bK(\\d+)\\b");
  
  private static final Properties CALL_CODES = buildCodeTable(new String[]{
      
      "01",      "HOMICIDE",
      "106",     "BUSY",
      "47HZ",    "NON INJURY ACC W/HAZMAT",
      "48",      "INJURY ACCIDENT",
      "48A",     "INJURY ACCIDENT-LEV A",
      "48B",     "INJURY ACCIDENT-LEV B",
      "48C",     "INJURY ACCIDENT-LEV C",
      "48D",     "INJURY ACCIDENT-LEV D",
      "48HR",    "INJURY ACCIDENT HIT & RUN",
      "48HZ",    "INJURY ACCIDENT W/HAZMAT",
      "48T",     "INJURY ACCIDENT-PT PINNED",
      "ABDOA",   "ABDOMINAL PAINS-LEVEL A",
      "ABDOB",   "ABDOMINAL PAINS-LEVEL B",
      "ABDOC",   "ABDOMINAL PAINS-LEVEL C",
      "ABDOD",   "ABDOMINAL PAINS-LEVEL D",
      "ABDOM",   "ABDOMINAL PAINS",
      "ABDUCT",  "ABDUCTION",
      "ALL",     "FALL",
      "ALPHA",   "FIRE ALPHA LEVEL CALL",
      "ALRGA",   "ALLERGIC REACTION-LEVEL A",
      "ALRGB",   "ALLERGIC REACTION-LEVEL B",
      "ALRGC",   "ALLERGIC REACTION-LEVEL C",
      "ALRGD",   "ALLERGIC REACTION-LEVEL D",
      "ALRGE",   "ALLERGIC REACTION-LEVEL E",
      "ALRGY",   "ALLERGIC REACTION",
      "ANBIA",   "ANIMAL BITE-LEVEL A",
      "ANBIB",   "ANIMAL BITE-LEVEL B",
      "ANBIC",   "ANIMAL BITE-LEVEL C",
      "ANBID",   "ANIMAL BITE-LEVEL D",
      "ANBIT",   "ANIMAL BITE",
      "ANBITE",  "ANIMAL BITE",
      "AOR",     "AVAILABLE ON RADIO",
      "APT",     "APARTMENT FIRE",
      "ASLT",    "ASSAULT",
      "ASLTA",   "ASSAULT-LEVEL A",
      "ASLTB",   "ASSAULT-LEVEL B",
      "ASLTC",   "ASSAULT-LEVEL C",
      "ASLTD",   "ASSAULT-LEVEL D",
      "ASSTC",   "ASSIST A CITIZEN",
      "ASSTE",   "ASSIST EMS",
      "ASSTP",   "ASSIST LAW ENFORCEMENT",
      "AUD",     "AUDIBLE ALARM",
      "BACK",    "NON-TRAUMATIC BACK PAINS",
      "BACKA",   "BACK PAINS-LEVEL A",
      "BACKB",   "BACK PAINS-LEVEL B",
      "BACKC",   "BACK PAINS-LEVEL C",
      "BACKD",   "BACK PAINS-LEVEL D",
      "BARN",    "BARN FIRE",
      "BLDG",    "BUILDING FIRE",
      "BLDGH",   "BUILDING FIRE WITH HAZ MAT",
      "BLDGHZ",  "BUILDING FIRE W/HAZMAT",
      "BLDGP",   "BUILDING FIRE WITH PERSON TRAPPED",
      "BLDGPT",  "BUILDING FIRE WITH PERSON TRAPPED",
      "BLDGT",   "BUILDING FIRE WITH PERSON TRAPPED",
      "BLUE",    "POSS CODE BLUE PATIENT",
      "BLUEA",   "CODE BLUE-LEVEL A",
      "BLUEB",   "CODE BLUE-LEVEL B",
      "BLUEC",   "CODE BLUE-LEVEL C",
      "BLUED",   "POSS CODE BLUE-LEVEL D",
      "BLUEE",   "POSS CODE BLUE-LEVEL E",
      "BRUSH",   "BRUSH FIRE",
      "BURN",    "BURN VICTIM",
      "BURNA",   "BURNS-LEVEL A",
      "BURNB",   "BURNS-LEVEL B",
      "BURNC",   "BURNS-LEVEL C",
      "BURND",   "BURNS-LEVEL D",
      "BURNE",   "BURNS-LEVEL E",
      "BURNS",   "BURNS",
      "BUS",     "SCHOOL/CHARTER BUS FIRE",
      "CAVEI",   "CAVE IN",
      "CAVEIN",  "CAVE IN",
      "CHESA",   "CHEST PAINS 1039 RESPONSE",
      "CHESC",   "CHEST PAINS-LEVEL C",
      "CHESD",   "CHEST PAINS-LEVEL D",
      "CHEST",   "CHEST PAINS 1039 RESPONSE",
      "CHOKA",   "PERSON CHOKING-LEVEL A",
      "CHOKD",   "PERSON CHOKING-LEVEL D",
      "CHOKE",   "PERSON CHOKING",
      "CKAPP",   "CHECK APPLIANCE",
      "CKCLUB",  "CHECK A CLUB",
      "CLUB",    "CLUBBING",
      "CMF",     "FIRE MAINTENANCE",
      "COALM",   "CARBON MONOXIDE ALARM",
      "COD25",   "BARRICADED SUBJECT",
      "COD26",   "BOMB THREAT",
      "CODE25",  "BARRICADED SUBJECT",
      "CODE26",  "BOMB THREAT",
      "COLA1",   "BLDG COLLAPSE - LEVEL 1",
      "COLA2",   "BLDG COLLAPSE - LEVEL 2",
      "COLA3",   "BLDG COLLAPSE - LEVEL 3",
      "COLD",    "COLD EXPOSURE",
      "COLDA",   "COLD EXPOSURE-LEVEL A",
      "COLDB",   "COLD EXPOSURE-LEVEL B",
      "COLDC",   "COLD EXPOSURE-LEVEL C",
      "COLDD",   "COLD EXPOSURE-LEVEL D",
      "CUT",     "CUTTING/STABBING",
      "CUTA",    "CUTTING/STABBING-LEVEL A",
      "CUTB",    "CUTTING/STABBING-LEVEL B",
      "CUTD",    "CUTTING/STABBING-LEVEL D",
      "CUTTIN",  "CUTTING",
      "CUTTING", "CUTTING",
      "DEATB",   "SUDDEN DEATH-LEVEL B",
      "DEATH",   "SUDDEN DEATH",
      "DERAI",   "TRAIN FIRE/DERAILMENT",
      "DEVICE",  "FOUND EXPLOSIVE DEVICE",
      "DIABA",   "DIABETIC-LEVEL A",
      "DIABC",   "DIABETIC-LEVEL C",
      "DIABD",   "DIABETIC-LEVEL D",
      "DIABE",   "DIABETIC COMPLICATIONS",
      "DIABET",  "DIABETIC EMERGENCY",
      "DIFFB",   "DIFFICULTY BREATHING",
      "DIFFBR",  "DIFFICULTY BREATHING",
      "DIFFC",   "DIFF BREATHING-LEVEL C",
      "DIFFD",   "DIFF BREATHING-LEVEL D",
      "DIFFE",   "DIFF BREATHING-LEVEL E",
      "DOC",     "DEPT OPER CNTR ACTIVATION",
      "DROWA",   "DROWNING-LEVEL A",
      "DROWB",   "DROWNING-LEVEL B",
      "DROWC",   "DROWNING-LEVEL C",
      "DROWD",   "DROWNING-LEVEL D",
      "DROWN",   "DROWNING IN BATHTUB/POOL",
      "E48",     "INJURY ACCIDENT",
      "E48HR",   "INJURY ACCIDENT HIT & RUN",
      "E48HZ",   "INJURY ACCIDENT W/HAZMAT",
      "E48T",    "INJURY ACCIDENT-PT PINNED",
      "EABDOM",  "ABDOMINAL PAINS",
      "EALRGY",  "ALLERGIC REACTION",
      "EANBIT",  "ANIMAL BITE",
      "EAPTT",   "APT FIRE - SUB TRAPPED",
      "EASLT",   "ASSAULT",
      "EBACK",   "NON-TRAUMATIC BACK PAINS",
      "EBLDGT",  "BUILDING FIRE-SUB TRAPPED",
      "EBURNS",  "BURNS",
      "ECHEST",  "CHEST PAINS 1039 RESPONSE",
      "ECHOKE",  "PERSON CHOKING",
      "ECLUB",   "CLUBBING",
      "ECOD25",  "BARRICADED SUBJECT",
      "ECOD26",  "BOMB THREAT",
      "ECOLA2",  "BLDG COLLAPSE - LEVEL 2",
      "ECOLA3",  "BLDG COLLAPSE - LEVEL 3",
      "ECOLD",   "COLD EXPOSURE",
      "ECUT",    "CUTTING/STABBING",
      "EDEATH",  "SUDDEN DEATH",
      "EDEVIC",  "FOUND EXPLOSIVE DEVICE",
      "EDIABE",  "DIABETIC EMERGENCY",
      "EDIFFB",  "DIFFICULTY BREATHING",
      "EDROWN",  "DROWNING IN BATHTUB/POOL",
      "EEYE",    "EYE PROBLEMS",
      "EFALL",   "FALL",
      "EFALLB",  "FALL-LEVEL B",
      "EFIRE",   "APARTMENT FIRE",
      "EGREEN",  "RESPONDER TRIAGE GREEN",
      "EHEAD",   "HEAD INJURY",
      "EHEART",  "HEART ATTACK",
      "EHEAT",   "HEAT RELATED EMERGENCY",
      "EHEMOR",  "HEMORRHAGE",
      "EHIRIST", "HIGH RISE FIRE WITH PERSON TRAPPED",
      "EHOUSET", "HOUSE FIRE WITH PERSON TRAPPED",
      "EHZ2",    "MEDIUM CHEMICAL/RADIOACTIVE SPILL/LEAK",
      "EHZ3",    "HAZ MAT LEVEL 3",
      "EHZ6",    "HAZ MAT LEVEL 6",
      "EICTAI",  "AIRPORT EMERGENCY",
      "EINDACC", "INDUSTRIAL ACCIDENT",
      "ELACER",  "LACERATION",
      "ELEV",    "PERSON STUCK IN ELEVATOR",
      "ELOSTA",  "LOST ADULT",
      "ELOSTJ",  "LOST JUVENILE",
      "EMA",     "MEDICAL ALARM",
      "EMANDW",  "MAN DOWN",
      "ENOTBR",  "PATIENT NOT BREATHING",
      "EOB",     "OBSTETRICAL EMERGENCY",
      "EOD",     "OVERDOSE",
      "EPLANE",  "PLANE ACCIDENT",
      "EPOISO",  "POISON",
      "ESEIZU",  "SEIZURE",
      "ESHOCK",  "SHOCK VICTIM",
      "ESHOOT",  "SHOOTING",
      "ESICK",   "SICK PERSON",
      "ESTDBY",  "STAND BY",
      "ESTROKE", "STROKE",
      "ESUB2",   "SUBMERSION - LEVEL 2",
      "ESUB3",   "SUBMERSION - LEVEL 3",
      "ESUICI",  "SUICIDE",
      "ETRAUM",  "TRAUMA",
      "ETRAUMA", "TRAUMA",
      "EUNCOD",  "SUBJECT UNCON-LEVEL D",
      "EUNCON",  "UNCONSCIOUS",
      "EUNKE",   "UNKNOWN EMERGENCY",
      "EXPLO",   "EXPLOSION (CHECK FOR)",
      "EXPLOS",  "CHECK FOR AN EXPLOSION",
      "EYE",     "EYE PROBLEMS",
      "EYEA",    "EYE PROBLEMS-LEVEL A",
      "EYEB",    "EYE PROBLEMS-LEVEL B",
      "EYED",    "EYE PROBLEMS-LEVEL D",
      "F47HZ",   "NON INJURY ACC W/HAZMAT",
      "F48",     "INJURY ACCIDENT",
      "F48HR",   "INJURY ACCIDENT HIT & RUN",
      "F48HZ",   "INJURY ACCIDENT W/HAZMAT",
      "F48T",    "INJURY ACCIDENT-PT PINNED",
      "FABDOM",  "ABDOMINAL PAINS",
      "FALL",    "FALL",
      "FALLA",   "FALL-LEVEL A",
      "FALLB",   "FALL-LEVEL B",
      "FALLD",   "FALL-LEVEL D",
      "FALRGY",  "ALLERGIC REACTION",
      "FANBIT",  "ANIMAL BITE",
      "FAPTT",   "APT FIRE - SUB TRAPPED",
      "FASLT",   "ASSAULT",
      "FASSTC",  "ASSIST A CITIZEN",
      "FASSTE",  "ASSIST EMS",
      "FBACK",   "NON-TRAUMATIC BACK PAINS",
      "FBLDGT",  "BUILDING FIRE-SUB TRAPPED",
      "FBURNS",  "BURNS",
      "FCC",     "FIREWORKS COMPLAINT",
      "FCCNO",   "FIREWORKS-NO COMPLAINT",
      "FCHEST",  "CHEST PAINS 1039 RESPONSE",
      "FCHOKE",  "PERSON CHOKING",
      "FCLUB",   "CLUBBING",
      "FCOD25",  "BARRICADED SUBJECT",
      "FCOD26",  "BOMB THREAT",
      "FCOLA1",  "BLDG COLLAPSE - LEVEL 1",
      "FCOLA2",  "BLDG COLLAPSE - LEVEL 2",
      "FCOLA3",  "BLDG COLLAPSE - LEVEL 3",
      "FCOLD",   "COLD EXPOSURE",
      "FCUT",    "CUTTING/STABBING",
      "FDEATH",  "SUDDEN DEATH",
      "FDERAIL", "TRAIN FIRE/DERAILMENT",
      "FDEVIC",  "FOUND EXPLOSIVE DEVICE",
      "FDIABE",  "DIABETIC EMERGENCY",
      "FDIFFB",  "DIFFICULTY BREATHING",
      "FDROWN",  "DROWNING IN BATHTUB/POOL",
      "FEXPLO",  "CHECK FOR AN EXPLOSION",
      "FEYE",    "EYE PROBLEMS",
      "FFALL",   "FALL",
      "FFALLB",  "FALL-LEVEL B",
      "FGREEN",  "RESPONDER TRIAGE GREEN",
      "FHEAD",   "HEAD INJURY",
      "FHEART",  "HEART ATTACK",
      "FHEAT",   "HEAT RELATED EMERGENCY",
      "FHEMOR",  "HEMORRHAGE",
      "FHIRIST", "HIGH RISE FIRE WITH PERSON TRAPPED",
      "FHOUSET", "HOUSE FIRE WITH PERSON TRAPPED",
      "FHOUST",  "HOUSE FIRE-SUBJ TRAPPED",
      "FHZ2",    "MEDIUM CHEMICAL/RADIOACTIVE SPILL/LEAK",
      "FHZ3",    "HAZ MAT LEVEL 3",
      "FHZ6",    "HAZ MAT LEVEL 6",
      "FICTAI",  "AIRPORT EMERGENCY",
      "FINDACC", "INDUSTRIAL ACCIDENT",
      "FIROUT",  "CHECK A FIRE THAT IS OUT",
      "FLACER",  "LACERATION",
      "FLOCKE",  "PERSON/ANIMAL LOCK IN/OUT",
      "FLOSTA",  "LOST ADULT",
      "FLOSTJ",  "LOST JUVENILE",
      "FLU",     "PANDEMIC FLU",
      "FLUA",    "PANDEMIC FLU - LEVEL A",
      "FLUB",    "PANDEMIC FLU - LEVEL B",
      "FLUC",    "PANDEMIC FLU - LEVEL C",
      "FLUD",    "PANDEMIC FLU - LEVEL D",
      "FLUO",    "PANDEMIC FLU - LEVEL O",
      "FMA",     "MEDICAL ALARM",
      "FMANDW",  "MAN DOWN",
      "FMETH",   "METH LAB",
      "FNOTBR",  "PATIENT NOT BREATHING",
      "FOB",     "OBSTETRICAL EMERGENCY",
      "FOD",     "OVERDOSE",
      "FPLANE",  "PLANE ACCIDENT",
      "FPOISO",  "POISON",
      "FSA",     "SPECIAL ASSIGNMENT",
      "FSEIZU",  "SEIZURE",
      "FSHOCK",  "SHOCK VICTIM",
      "FSHOOT",  "SHOOTING",
      "FSICK",   "SICK PERSON",
      "FSTDBY",  "STAND BY",
      "FSTROKE", "STROKE",
      "FSUB1",   "SUBMERSION - LEVEL 1",
      "FSUB2",   "SUBMERSION - LEVEL 2",
      "FSUB3",   "SUBMERSION - LEVEL 3",
      "FSUICI",  "SUICIDE",
      "FTRAUM",  "TRAUMA",
      "FTRAUMA", "TRAUMA",
      "FUNCOD",  "SUBJECT UNCON-LEVEL D",
      "FUNCON",  "UNCONSCIOUS",
      "FUNKE",   "UNKNOWN EMERGENCY",
      "FUP",     "FOLLOW UP WORK",
      "FVEH",    "VEHICLE FIRE",
      "GARAG",   "GARAGE FIRE",
      "GARAGE",  "NON ATTACHED GARAGE",
      "GASIN",   "CK FOR GAS ODOR IN A BLDG",
      "GASOUT",  "CK FOR GAS LEAK OUTSIDE",
      "GRASS1",  "GRASS FIRE - LEVEL 1",
      "GRASS2",  "GRASS FIRE - LEVEL 2",
      "GRASS3",  "GRASS FIRE - LEVEL 3",
      "GREEN",   "RESPONDER TRIAGE GREEN",
      "HEAD",    "HEAD INJURY",
      "HEADA",   "HEADACHE-LEVEL A",
      "HEADB",   "HEADACHE-LEVEL B",
      "HEADC",   "HEADACHE-LEVEL C",
      "HEADD",   "HEADACHE-LEVEL C",
      "HEARA",   "HEART PROBLEMS-LEVEL A",
      "HEARB",   "HEART PROBLEMS-LEVEL B",
      "HEARC",   "HEART PROBLEMS-LEVEL C",
      "HEARD",   "HEART PROBLEMS-LEVEL D",
      "HEART",   "HEART ATTACK",
      "HEAT",    "HEAT RELATED EMERGENCY",
      "HEATA",   "HEAT EXPOSURE-LEVEL A",
      "HEATB",   "HEAT EXPOSURE-LEVEL B",
      "HEATC",   "HEAT EXPOSURE-LEVEL C",
      "HEATD",   "HEAT EXPOSURE-LEVEL D",
      "HEMOA",   "BLEEDING-LEVEL A",
      "HEMOB",   "BLEEDING-LEVEL B",
      "HEMOC",   "BLEEDING-LEVEL C",
      "HEMOD",   "BLEEDING-LEVEL D",
      "HEMOR",   "HEMORRHAGE",
      "HIRIS",   "HIGH RISE FIRE",
      "HIRISE",  "HIGH RISE BUILDING FIRE",
      "HIRIST",  "HI RISE FIRE SUBJ TRAPPED",
      "HIRIT",   "HI RISE FIRE SUBJ TRAPPED",
      "HOSP",    "HOSPITAL",
      "HOUSE",   "HOUSE FIRE",
      "HOUSET",  "HOUSE FIRE WITH PERSON TRAPPED",
      "HOUST",   "HOUSE FIRE-SUBJ TRAPPED",
      "HTCDA",   "HOT/COLD EXPOSURE-LEVEL A",
      "HTCDB",   "HOT/COLD EXPOSURE-LEVEL B",
      "HTCDC",   "HOT/COLD EXPOSURE-LEVEL C",
      "HTCDD",   "HOT/COLD EXPOSURE-LEVEL D",
      "HZ1",     "KNOWN SUBSTANCE SPILL",
      "HZ2",     "MEDIUM CHEMICAL/RADIOACTIVE SPILL/LEAK",
      "HZ3",     "HAZ MAT LEVEL 3",
      "HZ6",     "HAZ MAT LEVEL 6",
      "HZB",     "CO/INHALATION/HZ-LEVEL B",
      "HZC",     "CO/INHALATION/HZ-LEVEL C",
      "HZD",     "CO/INHALATION/HZ-LEVEL D",
      "HZSM",    "CHEMICAL ODOR CHECK",
      "ICTAI",   "AIRPORT EMERGENCY",
      "ICTAIR",  "AIRPORT EMERGENCY",
      "INDAA",   "INDUSTRIAL ACCIDENT-LEV A",
      "INDAB",   "INDUSTRIAL ACCIDENT-LEV B",
      "INDAC",   "INDUSTRIAL ACCIDENT",
      "INDACC",  "INDUSTRIAL ACCIDENT",
      "INDAD",   "INDUSTRIAL ACCIDENT-LEV D",
      "INSP",    "INSPECTION",
      "IROUT",   "CHECK A FIRE THAT IS OUT",
      "LACER",   "LACERATION",
      "LINES",   "LINES DOWN",
      "LOCKED",  "PERSON/ANIMAL LOCK IN/OUT",
      "LOSTA",   "LOST ADULT",
      "LOSTJ",   "LOST JUVENILE",
      "LUNCH",   "LUNCH",
      "MA",      "MEDICAL ALARM",
      "MANDN",   "MAN DOWN",
      "MANDW",   "MAN DOWN",
      "METH",    "METH LAB",
      "MISCF",   "MISC SERVICE",
      "MSGF",    "PHONE MESSAGE",
      "MSGP",    "MESSAGE FOR LAW ENFORCEMENT",
      "MUTUAL",  "AUTOMATIC/MUTUAL AID REQ",
      "NOTBR",   "PATIENT NOT BREATHING",
      "OB",      "OBSTETRICAL EMERGENCY",
      "OBA",     "OBSTETRICAL EMERGENCY-LEV A",
      "OBB",     "OBSTETRICAL EMERGENCY-LEV B",
      "OBC",     "OBSTETRICAL EMERGENCY-LEV C",
      "OBD",     "OBSTETRICAL EMERGENCY-LEV D",
      "OD",      "OVERDOSE",
      "ODA",     "OVERDOSE-LEVEL A",
      "ODB",     "OVERDOSE-LEVEL B",
      "ODC",     "OVERDOSE-LEVEL C",
      "ODD",     "OVERDOSE-LEVEL D",
      "OMEGA",   "CANCEL RESP, OMEGA CALL",
      "OT",      "OFF TRACK",
      "PAGER",   "OUT ON PAGER",
      "PE",      "PHYSICAL EDUCATION",
      "PERMIT",  "ACTIVE BURN PERMIT",
      "PLANE",   "PLANE ACCIDENT",
      "POISO",   "POISON",
      "POISON",  "POISON",
      "POLE",    "POLE ON FIRE",
      "RV",      "RECREATIONAL VEHICLE FIRE",
      "SA",      "SYSTEM ALARM",
      "SC",      "SUSPICIOUS CHARACTER",
      "SEIZB",   "SEIZURE-LEV B",
      "SEIZC",   "SEIZURE-LEV C",
      "SEIZD",   "SEIZURE-LEV D",
      "SEIZU",   "SEIZURE",
      "SEIZUR",  "SEIZURE",
      "SEMI",    "TRACTOR TRAILER RIG FIRE",
      "SHED",    "SMALL OUT BLDG FIRE",
      "SHOCC",   "ELECTROCUTION-LEVEL C",
      "SHOCD",   "ELECTROCUTION-LEVEL D",
      "SHOCE",   "ELECTROCUTION-LEVEL E",
      "SHOCK",   "SHOCK VICTIM",
      "SHOOA",   "SHOOTING-LEV A (RUN 1039)",
      "SHOOB",   "SHOOTING-LEV B",
      "SHOOC",   "SHOOTING-LEV C",
      "SHOOD",   "SHOOTING-LEV D",
      "SHOOT",   "SHOOTING",
      "SICK",    "SICK PERSON",
      "SICKA",   "SICK PERSON-LEVEL A",
      "SICKB",   "SICK PERSON-LEVEL B",
      "SICKC",   "SICK PERSON-LEVEL C",
      "SICKD",   "SICK PERSON-LEVEL D",
      "SICKO",   "SICK PERSON-LEVEL O",
      "SMKDET",  "CHECK A SMOKE DETECTOR",
      "SMKIN",   "CK SMOKE IN A BUILDING",
      "SMKOUT",  "CK SMOKE IN OUTSIDE AREA",
      "STDBY",   "STAND BY",
      "STORM",   "CKRES DUE TO WEATHER",
      "STROA",   "STROKE-LEVEL A (RUN 1039)",
      "STROB",   "STROKE-LEVEL B",
      "STROC",   "STROKE-LEVEL C",
      "STROK",   "STROKE",
      "STROKE",  "STROKE",
      "SUB",     "SUBMERSION",
      "SUB1",    "SUBMERSION - LEVEL 1",
      "SUB2",    "SUBMERSION - LEVEL 2",
      "SUB3",    "SUBMERSION - LEVEL 3",
      "SUICA",   "SUICIDE/SIG4-LEVEL A",
      "SUICB",   "SUICIDE/SIG4-LEVEL B",
      "SUICC",   "SUICIDE/SIG4-LEVEL C",
      "SUICD",   "SUICIDE/SIG4-LEVEL D",
      "SUICI",   "SUICIDE",
      "SUICID",  "SUICIDE",
      "SYSB",    "SYSTEMS ALARM ON BUSINESS",
      "SYSH",    "SYSTEMS ALARM ON HOSPITAL",
      "SYSR",    "SYSTEMS ALARM ON RESIDENC",
      "TEST",    "TEST CALL FOR FIRE",
      "TRAFIC",  "TRAFFIC INCIDENT",
      "TRAIN",   "TRAIN FIRE",
      "TRAP",    "PERSON TRAPPED",
      "TRASH",   "TRASH FIRE",
      "TRAUA",   "TRAUMATIC INJURY-LEVEL A",
      "TRAUB",   "TRAUMATIC INJURY-LEVEL B",
      "TRAUD",   "TRAUMATIC INJURY-LEVEL D",
      "TRAUM",   "TRAUMA",
      "TRAUMA",  "TRAUMA",
      "UNCOA",   "SUBJECT UNCON-LEVEL A",
      "UNCOC",   "SUBJECT UNCON-LEVEL C",
      "UNCOD",   "SUBJECT UNCON-LEVEL D",
      "UNCOE",   "SUBJECT UNCON-LEVEL E",
      "UNCON",   "UNCONSCIOUS",
      "UNKE",    "UNKNOWN EMERGENCY",
      "UNKEA",   "UNKNOWN EMS-LEVEL A",
      "UNKEB",   "UNKNOWN EMS-LEVEL B",
      "UNKEC",   "UNKNOWN EMS-LEVEL C",
      "UNKED",   "UNKNOWN EMS-LEVEL D",
      "UNKF",    "UNKNOWN FIRE",
      "VACAT",   "VAC LOG FOR INDIVIDUAL",
      "VEH",     "VEHICLE FIRE",
      "WATER",   "WATER RELATED ISSUES/PROB",
      "WHEAT",   "STANDING WHEATFIELD FIRE",
      "WIRING",  "CHECK ELECTRICAL WIRING",
      "WORKIN",  "WORKIN",
      "XFER",    "EMERGENCY MEDICAL TRANSFER - NON EMERGENCY"

  });
  
  private static final Properties CITY_CODES = buildCodeTable(new String[]{
      "AN", "ANDALE",
      "BA", "BEL AIRE",
      "BE", "BENTLEY",
      "CH", "CHENEY",
      "CL", "CLEARWATER",
      "CO", "COLWICH",
      "DE", "DERBY",
      "EB", "EASTBOROUGH",
      "FU", "FURLEY",
      "GO", "GODDARD",
      "GP", "GARDEN PLAIN",
      "GR", "GREENWICH",
      "HA", "HAYSVILLE",
      "KE", "KECHI",
      "MA", "MAIZE",
      "MC", "MCCONNELL",
      "MH", "MT HOPE",
      "MU", "MULVANE",
      "PC", "PARK CITY",
      "PE", "PECK",
      "SC", "",   // SEDGWICK COUNTY???
      "SH", "SCHULTE",
      "VC", "VALLEY CENTER",
      "VI", "VIOLA",
      "WI", "WICHITA",

  });
}
