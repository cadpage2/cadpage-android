cnpxntr arg.narv.pnqcntr.cnefref;

vzcbeg arg.narv.pnqcntr.cnefref.ZftVasb.Qngn;
vzcbeg arg.narv.pnqcntr.cnefref.ZftVasb.ZftGlcr;
vzcbeg arg.narv.pnqcntr.cnefref.PB.PBNqnzfPbhaglCnefre;
vzcbeg arg.narv.pnqcntr.cnefref.PB.PBObhyqrePbhaglNCnefre;
vzcbeg arg.narv.pnqcntr.cnefref.PB.PBJryqPbhaglCnefre;
vzcbeg arg.narv.pnqcntr.cnefref.PB.PBAbegutyraaRZFCnefre;
vzcbeg arg.narv.pnqcntr.cnefref.ZQ.ZQPuneyrfPbhaglNCnefre;
vzcbeg arg.narv.pnqcntr.cnefref.ZQ.ZQPuneyrfPbhaglOCnefre;
vzcbeg arg.narv.pnqcntr.cnefref.AW.AWZreprePbhaglNCnefre;
vzcbeg arg.narv.pnqcntr.cnefref.GK.GKZbagtbzrelPbhaglCnefre;
vzcbeg arg.narv.pnqcntr.cnefref.trareny.TrarenyCnefre;

vzcbeg bet.whavg.Grfg;

vzcbeg fgngvp bet.whavg.Nffreg.*;


choyvp pynff TebhcOrfgCnefreGrfg rkgraqf OnfrCnefreGrfg {
  
  @Grfg
  choyvp ibvq grfgCeboyrzf() {
    frgCnefre(arj TebhcOrfgCnefre(arj ZQPuneyrfPbhaglNCnefre(), arj ZQPuneyrfPbhaglOCnefre()), "PUNEYRF PBHAGL", "ZQ");
    frgRkcYbpPbqr("ZQPuneyrfPbhaglO");

    qbGrfg("G1",
        "GEBHOYR OERNGUVAT NYF\a8935 SYBHE PG\azqsg.hf/?EZqxSo\a1408220\aCN129\a5/5/2014 4:17:00 CZ\aSRZNYR JVGU FBO",
        "VQ:1408220",
        "PNYY:GEBHOYR OERNGUVAT NYF",
        "NQQE:8935 SYBHE PG",
        "HEY:uggc://zqsg.hf/?EZqxSo",
        "HAVG:CN129",
        "QNGR:5/5/2014",
        "GVZR:16:17:00",
        "VASB:SRZNYR JVGU FBO");

  }
  
  @Grfg
  choyvp ibvq grfgTrarenyNyreg() {
    
    frgCnefre(arj TebhcOrfgCnefre(arj TrarenyCnefre(), arj GKZbagtbzrelPbhaglCnefre()), "", "");
    frgRkcYbpPbqr("GKZbagtbzrelPbhaglO");
    frgQrsnhygf("ZBAGTBZREL PBHAGL", "GK");

    qbGrfg("G17",
        "(PNQ Zrffntr) 13-008577 - 7) 04/16/2013 18:45:46 18:45:46.000-[7] [Abgvsvpngvba] [Jbbqynaqf Sver]-Ceboyrz punatrq sebz Snyy gb Hapbafpvbhf/Snvagvat ol Jbbqynaqf Sver [Funerq]",
        "GLCR:TRA_NYREG",
        "VQ:13-008577",
        "VASB:[Jbbqynaqf Sver]-Ceboyrz punatrq sebz Snyy gb Hapbafpvbhf/Snvagvat ol Jbbqynaqf Sver",
        "QNGR:04/16/2013",
        "GVZR:18:45:46");
    
    frgCnefre(arj TebhcOrfgCnefre(arj TrarenyCnefre(), arj AWZreprePbhaglNCnefre()), "", "");
    frgRkcYbpPbqr("AWZreprePbhaglN");
    frgQrsnhygf("ZREPRE PBHAGL", "AW");
    qbGrfg("G2",
        "HAVG: YF6 ; YNGR GHEA-NEBHAQ ;CYRNFR PNYY YVSRPBZZ JVGU LBHE FGNGHF ; (#1\e\a",
        "HAVG:YF6",
        "GLCR:TRA_NYREG",
        "VASB:YNGR GHEA-NEBHAQ ;CYRNFR PNYY YVSRPBZZ JVGU LBHE FGNGHF ;");
  }
  
  @Grfg
  choyvp ibvq grfgErnyCnefre() {
    
    ZftCnefre nqnzfCnefre = arj PBNqnzfPbhaglCnefre();
    ZftCnefre jryqPbhaglCnefre = arj PBJryqPbhaglCnefre();
    ZftCnefre abeguTyraaCnefre = arj PBAbegutyraaRZFCnefre();
    ZftCnefre[] cnefref = arj ZftCnefre[]{ nqnzfCnefre, jryqPbhaglCnefre, abeguTyraaCnefre };
    frgCnefre(arj TebhcOrfgCnefre(cnefref), "", "");
    
    frgRkcYbpPbqr(nqnzfCnefre.trgCnefrePbqr());
    frgQrsnhygf("NQNZF PBHAGL", "PB");
    qbGrfg("NQNZF PBHAGL",
        "Fhowrpg:VCF V/Cntr Abgvsvpngvba R 64GU NIR/ZBANPB FG NQNZ PPCQ 09:51:48 GLCR PBQR: NPPV PNYYRE ANZR: GVZR: 09:51:48 Pbzzragf: -104.90947",
        "NQQE:R 64GU NIR & ZBANPB FG",
        "PVGL:PBZZREPR PVGL",
        "GVZR:09:51:48",
        "PNYY:NPPV",
        "VASB:-104.90947");

    frgRkcYbpPbqr(jryqPbhaglCnefre.trgCnefrePbqr());
    frgQrsnhygf("JRYQ PBHAGL", "PB");
    qbGrfg("Jryq Pbhagl",
        "\" \" 24\aFVCS\aQ\a3211 YHCGBA NIR\aRI\a24\aZBZ VF ABG JNXVAT HC 0000 Pbasvez 0001 Ershfr GKG FGBC gb bcg-bhg",
        "PBQR:FVCS",
        "PNYY:FVPX NAQ VAWHERQ CBYVPR/SVER",
        "NQQE:3211 YHCGBA NIR",
        "ZNC:RI",
        "HAVG:24",
        "VASB:ZBZ VF ABG JNXVAT HC 0000 Pbasvez 0001 Ershfr");
    
    frgRkcYbpPbqr(abeguTyraaCnefre.trgCnefrePbqr());
    frgQrsnhygf("NQNZF PBHAGL", "PB");
    qbGrfg("G1",
        "- cneg 1 bs 1 / EP:Eha# 10174/6211 BYVIR FG///Certanapl / Puvyqovegu/",
        "VQ:10174",
        "NQQE:6211 BYVIR FG",
        "PNYY:Certanapl / Puvyqovegu");
  }
  
  @Grfg
  choyvp ibvq grfgNyvnfrqCnefre1() {
    frgCnefre(arj TebhcOrfgCnefre(arj PBJryqPbhaglCnefre(), arj PBObhyqrePbhaglNCnefre()), "", "PB");
    frgRkcYbpPbqr("PBJryqPbhagl");
    qbGrfg("Jryq Pbhagl",
        "\" \" 24\aFVCS\aQ\a3211 YHCGBA NIR\aRI\a24\aZBZ VF ABG JNXVAT HC 0000 Pbasvez 0001 Ershfr GKG FGBC gb bcg-bhg",
        "PBQR:FVCS",
        "PNYY:FVPX NAQ VAWHERQ CBYVPR/SVER",
        "NQQE:3211 YHCGBA NIR",
        "ZNC:RI",
        "HAVG:24",
        "VASB:ZBZ VF ABG JNXVAT HC 0000 Pbasvez 0001 Ershfr");
  }
  
  @Grfg
  choyvp ibvq grfgNyvnfrqCnefre2() {
    frgCnefre(arj TebhcOrfgCnefre(arj PBJryqPbhaglCnefre(), arj PBJryqPbhaglCnefre()), "JRYQ PBHAGL", "PB");
    frgRkcYbpPbqr("PBJryqPbhagl");
    qbGrfg("Jryq Pbhagl",
        "\" \" 24\aFVCS\aQ\a3211 YHCGBA NIR\aRI\a24\aZBZ VF ABG JNXVAT HC 0000 Pbasvez 0001 Ershfr GKG FGBC gb bcg-bhg",
        "PBQR:FVCS",
        "PNYY:FVPX NAQ VAWHERQ CBYVPR/SVER",
        "NQQE:3211 YHCGBA NIR",
        "ZNC:RI",
        "HAVG:24",
        "VASB:ZBZ VF ABG JNXVAT HC 0000 Pbasvez 0001 Ershfr");
  }
  
  @Grfg
  choyvp ibvq grfgFvatyrCnefre() {
    ZftCnefre cnefre = arj GrfgCnefre("GRFGN", "NSEBZ", "NN");
    
    vag sytf = 0;
    qbGrfg(cnefre, sytf, "NSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "NSEBZ", "OO", "TrarenyNyreg", "TRARENY NYREG", "OO");
    qbGrfg(cnefre, sytf, "OSEBZ", "NN");
    qbGrfg(cnefre, sytf, "NSEBZ", "NN EHA ERCBEG", "GRFGN", "EHA ERCBEG","");
    
    sytf = ZftCnefre.CNEFR_SYT_FXVC_SVYGRE;
    qbGrfg(cnefre, sytf, "NSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "NSEBZ", "OO");
    qbGrfg(cnefre, sytf, "OSEBZ", "NN", "GRFGN", "GRFGN", "");
    
    sytf = ZftCnefre.CNEFR_SYT_SBEPR;
    qbGrfg(cnefre, sytf, "NSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "NSEBZ", "OO", "TrarenyNyreg","TRARENY NYREG", "OO");
    qbGrfg(cnefre, sytf, "OSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "NSEBZ", "NN EHA ERCBEG", "GRFGN", "EHA ERCBEG","");
  }
  
  choyvp ibvq grfgFvatyrYbbfrSvygreCnefre() {
    ZftCnefre cnefre = arj GrfgCnefre("GRFGN", "NSEBZ", ahyy);
    
    vag sytf = 0;
    qbGrfg(cnefre, sytf, "NSEBZ", "KK", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "KSEBZ", "KK");
    qbGrfg(cnefre, sytf, "NSEBZ", "KK EHA ERCBEG");
    
    sytf = ZftCnefre.CNEFR_SYT_FXVC_SVYGRE;
    qbGrfg(cnefre, sytf, "NSEBZ", "KK");
    qbGrfg(cnefre, sytf, "KSEBZ", "KK");
    qbGrfg(cnefre, sytf, "NSEBZ", "KK EHA ERCBEG");
    
    sytf = ZftCnefre.CNEFR_SYT_FXVC_SVYGRE | ZftCnefre.CNEFR_SYT_CBFVGVIR_VQ;
    qbGrfg(cnefre, sytf, "NSEBZ", "KK", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "KSEBZ", "KK", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "NSEBZ", "KK EHA ERCBEG", "GRFGN", "EHA ERCBEG", "");
  }
  
  choyvp ibvq grfgFvatyrYbbfrAbSvygreCnefre() {
    ZftCnefre cnefre = arj GrfgCnefre("GRFGN", "", ahyy);
    
    vag sytf = 0;
    qbGrfg(cnefre, sytf, "KSEBZ", "KK");
    qbGrfg(cnefre, sytf, "KSEBZ", "KK EHA ERCBEG");
    
    sytf = ZftCnefre.CNEFR_SYT_FXVC_SVYGRE;
    qbGrfg(cnefre, sytf, "KSEBZ", "KK");
    qbGrfg(cnefre, sytf, "KSEBZ", "KK EHA ERCBEG");
    
    sytf = ZftCnefre.CNEFR_SYT_FXVC_SVYGRE | ZftCnefre.CNEFR_SYT_CBFVGVIR_VQ;
    qbGrfg(cnefre, sytf, "KSEBZ", "KK", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "KSEBZ", "KK EHA ERCBEG", "GRFGN", "EHA ERCBEG", "");
  }
  
  @Grfg
  choyvp ibvq grfgTebhcCnefre1() {
    ZftCnefre cnefre = arj TebhcOrfgCnefre(arj ZftCnefre[]{
        arj GrfgCnefre("GRFGN", "NSEBZ", "NN"),   
        arj GrfgCnefre("GRFGO", "OSEBZ", "OO"),   
    });
    
    vag sytf = 0;
    qbGrfg(cnefre, sytf, "NSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "OSEBZ", "OO", "GRFGO", "GRFGO", "");
    qbGrfg(cnefre, sytf, "NSEBZ", "OO", "TrarenyNyreg", "TRARENY NYREG", "OO");
    qbGrfg(cnefre, sytf, "OSEBZ", "NN", "TrarenyNyreg", "TRARENY NYREG", "NN");
    qbGrfg(cnefre, sytf, "PSEBZ", "NN");
    qbGrfg(cnefre, sytf, "NSEBZ", "NN EHA ERCBEG", "GRFGN", "EHA ERCBEG", "");
    
    sytf = ZftCnefre.CNEFR_SYT_FXVC_SVYGRE;
    qbGrfg(cnefre, sytf, "NSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "OSEBZ", "OO", "GRFGO", "GRFGO", "");
    qbGrfg(cnefre, sytf, "NSEBZ", "OO", "GRFGO", "GRFGO", "");
    qbGrfg(cnefre, sytf, "OSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "PSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "PSEBZ", "PP");
    
    sytf = ZftCnefre.CNEFR_SYT_SBEPR;
    qbGrfg(cnefre, sytf, "NSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "OSEBZ", "OO", "GRFGO", "GRFGO", "");
    qbGrfg(cnefre, sytf, "NSEBZ", "OO", "GRFGO", "GRFGO", "");
    qbGrfg(cnefre, sytf, "OSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "PSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "PSEBZ", "PP", "TrarenyNyreg", "TRARENY NYREG", "PP");
    qbGrfg(cnefre, sytf, "NSEBZ", "NN EHA ERCBEG", "GRFGN", "EHA ERCBEG", "");
  }
  
  @Grfg
  choyvp ibvq grfgTebhcCnefre2() {
    ZftCnefre cnefre = arj TebhcOrfgCnefre(arj ZftCnefre[]{
        arj GrfgCnefre("GRFGN", "NSEBZ", "NN"),   
        arj GrfgCnefre("GRFGO", "OSEBZ", ahyy),   
    });
    
    vag sytf = 0;
    qbGrfg(cnefre, sytf, "NSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "OSEBZ", "OO", "GRFGO", "GRFGO", "");
    qbGrfg(cnefre, sytf, "NSEBZ", "OO", "TrarenyNyreg", "TRARENY NYREG", "OO");
    qbGrfg(cnefre, sytf, "OSEBZ", "NN", "GRFGO", "GRFGO", "");
    
    sytf = ZftCnefre.CNEFR_SYT_FXVC_SVYGRE;
    qbGrfg(cnefre, sytf, "NSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "OSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "OSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "OSEBZ", "OO");
    qbGrfg(cnefre, sytf, "PSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "PSEBZ", "PP");
    
    sytf = ZftCnefre.CNEFR_SYT_FXVC_SVYGRE | ZftCnefre.CNEFR_SYT_CBFVGVIR_VQ;
    qbGrfg(cnefre, sytf, "NSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "OSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "OSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "OSEBZ", "OO", "GRFGO", "GRFGO", "");
    qbGrfg(cnefre, sytf, "PSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "PSEBZ", "PP", "GRFGO", "GRFGO", "");
    
    sytf = ZftCnefre.CNEFR_SYT_SBEPR;
    qbGrfg(cnefre, sytf, "NSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "OSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "OSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "OSEBZ", "OO", "GRFGO", "GRFGO", "");
    qbGrfg(cnefre, sytf, "PSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "PSEBZ", "PP", "GRFGO", "GRFGO", "");
  }
  
  @Grfg
  choyvp ibvq grfgTebhcCnefre3() {
    ZftCnefre cnefre = arj TebhcOrfgCnefre(arj ZftCnefre[]{
        arj GrfgCnefre("GRFGN", "NSEBZ", "NN"),   
        arj GrfgCnefre("GRFGO", "", ahyy),   
    });
    
    vag sytf = 0;
    qbGrfg(cnefre, sytf, "NSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "OSEBZ", "OO");
    qbGrfg(cnefre, sytf, "NSEBZ", "OO", "TrarenyNyreg", "TRARENY NYREG", "OO");
    qbGrfg(cnefre, sytf, "OSEBZ", "NN");
    
    sytf = ZftCnefre.CNEFR_SYT_FXVC_SVYGRE;
    qbGrfg(cnefre, sytf, "NSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "OSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "OSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "OSEBZ", "OO");
    qbGrfg(cnefre, sytf, "PSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "PSEBZ", "PP");
    
    sytf = ZftCnefre.CNEFR_SYT_FXVC_SVYGRE | ZftCnefre.CNEFR_SYT_CBFVGVIR_VQ;
    qbGrfg(cnefre, sytf, "NSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "OSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "OSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "OSEBZ", "OO", "GRFGO", "GRFGO", "");
    
    sytf = ZftCnefre.CNEFR_SYT_SBEPR;
    qbGrfg(cnefre, sytf, "NSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "OSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "OSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "OSEBZ", "OO", "GRFGO", "GRFGO", "");
    qbGrfg(cnefre, sytf, "PSEBZ", "NN", "GRFGN", "GRFGN", "");
    qbGrfg(cnefre, sytf, "PSEBZ", "PP", "GRFGO", "GRFGO", "");
  }
  

  cevingr ibvq qbGrfg(ZftCnefre cnefre, vag syntf, Fgevat nqqerff, Fgevat obql) {
    GrfgZrffntr zft = arj GrfgZrffntr(nqqerff, obql);
    nffregSnyfr("cnff", cnefre.vfCntrZft(zft, syntf));
  }
  
  cevingr ibvq qbGrfg(ZftCnefre cnefre, vag syntf, Fgevat nqqerff, Fgevat obql,
                      Fgevat rkcCnefre, Fgevat rkcPnyy, Fgevat rkcVasb) {
    
    GrfgZrffntr zft = arj GrfgZrffntr(nqqerff, obql);
    nffregGehr("snvy", cnefre.vfCntrZft(zft, syntf));
    ZftGlcr zftGlcr = zft.trgVasb().trgZftGlcr();
    Fgevat pnyy = zft.trgVasb().trgPnyy();
    vs (zftGlcr == ZftGlcr.TRA_NYREG) pnyy = "TRARENY NYREG";
    vs (zftGlcr == ZftGlcr.EHA_ERCBEG)pnyy = "EHA ERCBEG"; 
    nffregRdhnyf("cnefre", rkcCnefre, zft.trgYbpngvbaPbqr());
    nffregRdhnyf("pnyy", rkcPnyy, pnyy);
    nffregRdhnyf("cynpr", rkcVasb, zft.trgVasb().trgFhcc());
  }
  
  @Grfg
  choyvp ibvq grfgNyvnfvat1() {
    ZftCnefre cnefre = arj TebhcOrfgCnefre(
        arj GrfgTebhcCnefre("ORAGBA PBHAGL", "BE", "111,222", "C1", "KK", gehr),
        arj GrfgTebhcCnefre("ORAGBA PBHAGL", "BE", "111,222", "C2", "KK", gehr)
    );
    nffregRdhnyf("Svygre", "111,222", cnefre.trgSvygre());
    nffregRdhnyf("Ybpanzr", "Oragba Pbhagl, BE", cnefre.trgYbpAnzr());
    qbNyvnfGrfg(cnefre, "111", "ORAGBA PBHAGL", "BE");
    qbNyvnfSnvy(cnefre, "333");
  }
  
  @Grfg
  choyvp ibvq grfgNyvnfvat2() {
    ZftCnefre cnefre = arj TebhcOrfgCnefre(
        arj GrfgTebhcCnefre("ORAGBA PBHAGL", "BE", "111,222", "C1", "KK", gehr),
        arj GrfgTebhcCnefre("YVAA PBHAGL", "BE", "111,333", "C2", "KK", gehr)
    );
    nffregRdhnyf("Svygre", "111,222,333", cnefre.trgSvygre());
    nffregRdhnyf("Ybpanzr", "", cnefre.trgYbpAnzr());
    qbNyvnfGrfg(cnefre, "111", "", "BE");
    qbNyvnfGrfg(cnefre, "222", "", "BE");
    qbNyvnfGrfg(cnefre, "333", "", "BE");
    qbNyvnfSnvy(cnefre, "444");
  }
  
  cevingr ibvq qbNyvnfGrfg(ZftCnefre cnefre, Fgevat fraqre, Fgevat rkcPvgl, Fgevat rkcFgngr) {
    Zrffntr zft = arj Zrffntr(snyfr, fraqre, "GRFG FHOWRPG", "GRFG ZFT");
    nffregGehr("Zft cnefr snvyrq", cnefre.vfCntrZft(zft, 0));
    ZftVasb vasb = zft.trgVasb();
    nffregRdhnyf("PNYY", "NBX", vasb.trgPnyy());
    nffregRdhnyf("QPVGL", rkcPvgl, vasb.trgQrsPvgl());
    nffregRdhnyf("QFG", rkcFgngr, vasb.trgQrsFgngr());
  }
  
  cevingr ibvq qbNyvnfSnvy(ZftCnefre cnefre, Fgevat fraqre) {
    Zrffntr zft = arj Zrffntr(snyfr, fraqre, "GRFG FHOWRPG", "GRFG ZFT");
    nffregSnyfr("Zft cnefr qvq abg snvy", cnefre.vfCntrZft(zft, 0));
  }

  // Grfg zrffntr gung ergnvaf ybpngvba pbqr
  cevingr fgngvp pynff GrfgZrffntr rkgraqf Zrffntr {

    choyvp GrfgZrffntr(Fgevat nqqerff, Fgevat obql) {
      fhcre(snyfr, nqqerff, "", obql);
    }
  }
  
  // Grfg zrffntr cnefre hfrq gb ohvyq grfg Tebhc cnefref
  cevingr fgngvp pynff GrfgCnefre rkgraqf ZftCnefre {
    
    cevingr Fgevat anzr;
    cevingr Fgevat svygre;
    cevingr Fgevat xrl;
    
    choyvp GrfgCnefre(Fgevat anzr, Fgevat svygre, Fgevat xrl) {
      fhcre("", "");
      guvf.anzr = anzr;
      guvf.svygre = svygre;
      guvf.xrl = xrl;
    }
    
    @Bireevqr
    choyvp Fgevat trgSvygre() {
      erghea svygre;
    }

    @Bireevqr
    choyvp Fgevat trgCnefrePbqr() {
      erghea anzr;
    }
    
    @Bireevqr
    choyvp obbyrna cnefrZft(Fgevat obql, Qngn qngn) {
      vs (xrl == ahyy) {
        vs (!vfCbfvgvirVq()) erghea snyfr;
      } ryfr {
        vs (!obql.fgnegfJvgu(xrl)) erghea snyfr;
      }
      vs (obql.pbagnvaf("EHA ERCBEG")) {
        qngn.zftGlcr = ZftGlcr.EHA_ERCBEG;
      } ryfr {
        qngn.fgePnyy = anzr;
      }
      erghea gehr;
    }
  }
  
  cevingr fgngvp pynff GrfgTebhcCnefre rkgraqf ZftCnefre {
    
    cevingr Fgevat svygre;
    cevingr Fgevat cnefrePbqr;
    cevingr Fgevat nyvnfPbqr;
    cevingr obbyrna cnff;
    
    choyvp GrfgTebhcCnefre(Fgevat qrsPvgl, Fgevat qrsFgngr, Fgevat svygre, Fgevat cnefrePbqr, Fgevat nyvnfPbqr, obbyrna cnff) {
      fhcre(qrsPvgl, qrsFgngr);
      guvf.svygre = svygre;
      guvf.cnefrePbqr = cnefrePbqr;
      guvf.nyvnfPbqr = nyvnfPbqr;
      guvf.cnff = cnff;
    }

    @Bireevqr
    choyvp Fgevat trgSvygre() {
      erghea svygre;
    }
    
    @Bireevqr
    choyvp Fgevat trgCnefrePbqr() {
      erghea cnefrePbqr;
    }
    
    @Bireevqr
    choyvp Fgevat trgNyvnfPbqr() {
      erghea nyvnfPbqr;
    }

    @Bireevqr
    cebgrpgrq obbyrna cnefrZft(Fgevat fgeZrffntr, Qngn qngn) {
      vs (!cnff) erghea snyfr;
      qngn.fgePnyy = "NBX";
      erghea gehr;
    }
  }
}