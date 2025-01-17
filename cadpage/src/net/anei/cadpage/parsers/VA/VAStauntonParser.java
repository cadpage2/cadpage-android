package net.anei.cadpage.parsers.VA;

import java.util.Properties;

import net.anei.cadpage.parsers.MsgInfo.Data;
import net.anei.cadpage.parsers.dispatch.DispatchOSSIParser;

public class VAStauntonParser extends DispatchOSSIParser {
  
  public VAStauntonParser() {
    super("STAUNTON", "VA", 
          "ADDR CITY CALL! INFO+");
    setupGpsLookupTable(GPS_LOOKUP_TABLE);
  }
  
  @Override
  public String getFilter() {
    return "CAD@staunton.net";
  }
  
  @Override
  public boolean parseMsg(String subject, String body, Data data) {
    if (!subject.equals("Text Message")) return false;
    return super.parseMsg("CAD:" + body, data);
  }
  
  private static final Properties GPS_LOOKUP_TABLE = buildCodeTable(new String[]{
      "208 ACADEMY ST",                       "+38.152484,-79.070467",
      "312 ACADEMY ST",                       "+38.153971,-79.069225",
      "316 ACADEMY ST",                       "+38.154430,-79.068801",
      "102 BAYLOR ST",                        "+38.168070,-79.071028",
      "102 BAYLOR ST BLDG 10",                "+38.168348,-79.071364",
      "102 BAYLOR ST BLDG 20",                "+38.168593,-79.071135",
      "102 BAYLOR ST BLDG 30",                "+38.168861,-79.070923",
      "102 BAYLOR ST BLDG 40",                "+38.169134,-79.070717",
      "102 BAYLOR ST BLDG 50",                "+38.169347,-79.070311",
      "102 BAYLOR ST BLDG 60",                "+38.169206,-79.069875",
      "102 BAYLOR ST BLDG 70",                "+38.168784,-79.069861",
      "102 BAYLOR ST BLDG 80",                "+38.168473,-79.069671",
      "102 BAYLOR ST BLDG 90",                "+38.168154,-79.069421",
      "102 BAYLOR ST BLDG 100",               "+38.167797,-79.069476",
      "102 BAYLOR ST BLDG 110",               "+38.167621,-79.069921",
      "102 BAYLOR ST BLDG 120",               "+38.167626,-79.070386",
      "102 BAYLOR ST BLDG 130",               "+38.167626,-79.070820",
      "102 BAYLOR ST BLDG 140",               "+38.168005,-79.070161",
      "102 BAYLOR ST BLDG 150",               "+38.168007,-79.070572",
      "102 BAYLOR ST BLDG 160",               "+38.168264,-79.070832",
      "102 BAYLOR ST BLDG 170",               "+38.168528,-79.070605",
      "102 BAYLOR ST BLDG 180",               "+38.168809,-79.070388",
      "301 DEMING DR",                        "+38.155102,-79.068686",
      "315 DEMING DR",                        "+38.154895,-79.068319",
      "101 E FREDERICK ST",                   "+38.151074,-79.071442",
      "115 E FREDERICK ST",                   "+38.151292,-79.071048",
      "201 E FREDERICK ST",                   "+38.151236,-79.070298",
      "215 E FREDERICK ST",                   "+38.151204,-79.069624",
      "227 E FREDERICK ST",                   "+38.151427,-79.069021",
      "218 HUNT DR",                          "+38.152066,-79.070373",
      "234 HUNT DR",                          "+38.152089,-79.069463",
      "219 KABLE ST",                         "+38.153450,-79.067566",
      "227 KABLE ST",                         "+38.154229,-79.068178",
      "240 KABLE ST",                         "+38.153731,-79.068722",
      "108 KING DR",                          "+38.151971,-79.071337",
      "109 KING DR",                          "+38.152226,-79.071250",
      "11 LINCOLN LN",                        "+38.168229,-79.071377",
      "12 LINCOLN LN",                        "+38.168269,-79.071334",
      "13 LINCOLN LN",                        "+38.168292,-79.071311",
      "14 LINCOLN LN",                        "+38.168320,-79.071284",
      "15 LINCOLN LN",                        "+38.168339,-79.071256",
      "16 LINCOLN LN",                        "+38.168403,-79.071217",
      "21 LINCOLN LN",                        "+38.168498,-79.071120",
      "22 LINCOLN LN",                        "+38.168522,-79.071100",
      "23 LINCOLN LN",                        "+38.168568,-79.071055",
      "24 LINCOLN LN",                        "+38.168592,-79.071030",
      "25 LINCOLN LN",                        "+38.168638,-79.070993",
      "26 LINCOLN LN",                        "+38.168661,-79.070977",
      "31 LINCOLN LN",                        "+38.168773,-79.070901",
      "32 LINCOLN LN",                        "+38.168794,-79.070880",
      "33 LINCOLN LN",                        "+38.168843,-79.070838",
      "34 LINCOLN LN",                        "+38.168863,-79.070830",
      "35 LINCOLN LN",                        "+38.168917,-79.070788",
      "36 LINCOLN LN",                        "+38.168948,-79.070770",
      "41 LINCOLN LN",                        "+38.169040,-79.070689",
      "42 LINCOLN LN",                        "+38.169064,-79.070674",
      "43 LINCOLN LN",                        "+38.169111,-79.070638",
      "44 LINCOLN LN",                        "+38.169136,-79.070623",
      "45 LINCOLN LN",                        "+38.169185,-79.070578",
      "46 LINCOLN LN",                        "+38.169206,-79.070565",
      "51 LINCOLN LN",                        "+38.169289,-79.070434",
      "52 LINCOLN LN",                        "+38.169288,-79.070403",
      "53 LINCOLN LN",                        "+38.169277,-79.070303",
      "54 LINCOLN LN",                        "+38.169280,-79.070275",
      "55 LINCOLN LN",                        "+38.169283,-79.070194",
      "56 LINCOLN LN",                        "+38.169284,-79.070151",
      "61 LINCOLN LN",                        "+38.169369,-79.069949",
      "62 LINCOLN LN",                        "+38.169335,-79.069940",
      "63 LINCOLN LN",                        "+38.169263,-79.069955",
      "64 LINCOLN LN",                        "+38.169233,-79.069946",
      "65 LINCOLN LN",                        "+38.169175,-79.069965",
      "66 LINCOLN LN",                        "+38.169147,-79.069964",
      "67 LINCOLN LN",                        "+38.169098,-79.069950",
      "68 LINCOLN LN",                        "+38.169073,-79.069956",
      "71 LINCOLN LN",                        "+38.168929,-79.069955",
      "72 LINCOLN LN",                        "+38.168902,-79.069952",
      "73 LINCOLN LN",                        "+38.168835,-79.069951",
      "74 LINCOLN LN",                        "+38.168803,-79.069947",
      "75 LINCOLN LN",                        "+38.168741,-79.069941",
      "76 LINCOLN LN",                        "+38.168719,-79.069940",
      "81 LINCOLN LN",                        "+38.168482,-79.069850",
      "82 LINCOLN LN",                        "+38.168457,-79.069825",
      "83 LINCOLN LN",                        "+38.168411,-79.069778",
      "84 LINCOLN LN",                        "+38.168392,-79.069753",
      "85 LINCOLN LN",                        "+38.168337,-79.069687",
      "86 LINCOLN LN",                        "+38.168316,-79.069657",
      "91 LINCOLN LN",                        "+38.168215,-79.069568",
      "92 LINCOLN LN",                        "+38.168182,-79.069553",
      "93 LINCOLN LN",                        "+38.168122,-79.069525",
      "94 LINCOLN LN",                        "+38.168097,-79.069512",
      "95 LINCOLN LN",                        "+38.168035,-79.069495",
      "96 LINCOLN LN",                        "+38.168008,-79.069485",
      "101 LINCOLN LN",                       "+38.167917,-79.069505",
      "103 LINCOLN LN",                       "+38.167825,-79.069553",
      "104 LINCOLN LN",                       "+38.167806,-79.069567",
      "105 LINCOLN LN",                       "+38.167733,-79.069620",
      "106 LINCOLN LN",                       "+38.167706,-79.069631",
      "111 LINCOLN LN",                       "+38.167684,-79.069780",
      "112 LINCOLN LN",                       "+38.167684,-79.069798",
      "113 LINCOLN LN",                       "+38.167676,-79.069921",
      "114 LINCOLN LN",                       "+38.167677,-79.069948",
      "115 LINCOLN LN",                       "+38.167679,-79.070048",
      "116 LINCOLN LN",                       "+38.167676,-79.070072",
      "121 LINCOLN LN",                       "+38.167685,-79.070266",
      "122 LINCOLN LN",                       "+38.167689,-79.070297",
      "123 LINCOLN LN",                       "+38.167687,-79.070396",
      "124 LINCOLN LN",                       "+38.167689,-79.070428",
      "125 LINCOLN LN",                       "+38.167694,-79.070520",
      "126 LINCOLN LN",                       "+38.167698,-79.070551",
      "131 LINCOLN LN",                       "+38.167691,-79.070704",
      "132 LINCOLN LN",                       "+38.167689,-79.070735",
      "133 LINCOLN LN",                       "+38.167686,-79.070805",
      "134 LINCOLN LN",                       "+38.167692,-79.070837",
      "135 LINCOLN LN",                       "+38.167690,-79.070931",
      "136 LINCOLN LN",                       "+38.167694,-79.070963",
      "141 LINCOLN LN",                       "+38.167902,-79.070066",
      "142 LINCOLN LN",                       "+38.167903,-79.070091",
      "143 LINCOLN LN",                       "+38.167901,-79.070172",
      "144 LINCOLN LN",                       "+38.167904,-79.070199",
      "145 LINCOLN LN",                       "+38.167901,-79.070271",
      "146 LINCOLN LN",                       "+38.167910,-79.070335",
      "151 LINCOLN LN",                       "+38.167889,-79.070485",
      "152 LINCOLN LN",                       "+38.167887,-79.070521",
      "153 LINCOLN LN",                       "+38.167896,-79.070587",
      "154 LINCOLN LN",                       "+38.167897,-79.070620",
      "155 LINCOLN LN",                       "+38.167891,-79.070696",
      "156 LINCOLN LN",                       "+38.167891,-79.070754",
      "161 LINCOLN LN",                       "+38.168242,-79.070963",
      "162 LINCOLN LN",                       "+38.168264,-79.070936",
      "163 LINCOLN LN",                       "+38.168322,-79.070875",
      "164 LINCOLN LN",                       "+38.168340,-79.070859",
      "165 LINCOLN LN",                       "+38.168390,-79.070827",
      "166 LINCOLN LN",                       "+38.168403,-79.070804",
      "171 LINCOLN LN",                       "+38.168500,-79.070731",
      "172 LINCOLN LN",                       "+38.168522,-79.070715",
      "173 LINCOLN LN",                       "+38.168577,-79.070672",
      "174 LINCOLN LN",                       "+38.168606,-79.070646",
      "175 LINCOLN LN",                       "+38.168638,-79.070613",
      "176 LINCOLN LN",                       "+38.168657,-79.070599",
      "181 LINCOLN LN",                       "+38.168781,-79.070523",
      "182 LINCOLN LN",                       "+38.168800,-79.070496",
      "183 LINCOLN LN",                       "+38.168879,-79.070439",
      "184 LINCOLN LN",                       "+38.168901,-79.070424",
      "185 LINCOLN LN",                       "+38.168939,-79.070395",
      "186 LINCOLN LN",                       "+38.168965,-79.070376",
      "138 N COALTER ST",                     "+38.152171,-79.068052",
      "140 N COALTER ST",                     "+38.152108,-79.068013",
      "146 N COALTER ST",                     "+38.152233,-79.067809",
      "172 N COALTER ST",                     "+38.153133,-79.067550",
      "204 N COALTER ST",                     "+38.153411,-79.067281",
      "220 N COALTER ST",                     "+38.155625,-79.066268",
      "110 N MARKET ST",                      "+38.152106,-79.071079",
      "120 N MARKET ST",                      "+38.152399,-79.071042",
      "201 N MARKET ST",                      "+38.152914,-79.070685",
      "209 N MARKET ST",                      "+38.153136,-79.070660",
      "215 N MARKET ST",                      "+38.153281,-79.070670",
      "221 N MARKET ST",                      "+38.153529,-79.070681",
      "121 N NEW ST",                         "+38.151390,-79.071546",
      "209 N NEW ST",                         "+38.152003,-79.071636",
      "221 N NEW ST",                         "+38.152436,-79.071693",
      "303 N NEW ST",                         "+38.152820,-79.071758",
      "224 POINTS ST",                        "+38.155024,-79.069706",
      "321 POINTS ST",                        "+38.153141,-79.070233",
      "228 PROSPECT ST",                      "+38.154381,-79.069113",
      "314 PROSPECT ST",                      "+38.154978,-79.068014",
      "318 PROSPECT ST",                      "+38.155273,-79.068058",
      "361 PROSPECT ST",                      "+38.155828,-79.067978",
      "233 SYCAMORE ST",                      "+38.152497,-79.068345",
      "237 SYCAMORE ST",                      "+38.152434,-79.068683",
      "242 SYCAMORE ST",                      "+38.152141,-79.068575",
      "245 SYCAMORE ST",                      "+38.152449,-79.069582",
      "249 SYCAMORE ST",                      "+38.152471,-79.069948",
      "128 TAMS ST",                          "+38.156317,-79.068217",
      "100 TULLIDGE DR",                      "+38.155693,-79.069953",
      "61 WATERFORD LOOP",                    "+38.115780,-79.0079501",
      "73 WATERFORD LOOP",                    "+38.115685,-79.0085641",
      "87 WATERFORD LOOP",                    "+38.115326,-79.0091573",
      "95 WATERFORD LOOP",                    "+38.114836,-79.0091994",
      "107 WATERFORD LOOP",                   "+38.115000,-79.0083786",
      "125 WATERFORD LOOP",                   "+38.115224,-79.0076877",
      "1125 WESTMORELAND DR",                 "+38.156177,-79.0674900"
  });
}
