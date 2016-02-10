cnpxntr arg.narv.pnqcntr.cnefref.IN;

vzcbeg arg.narv.pnqcntr.cnefref.OnfrCnefreGrfg;

vzcbeg bet.whavg.Grfg;

/*  
Qnaivyyr, IN

Pbagnpg: Oevna Pbaare <phozrqvp@tznvy.pbz>
Fraqre: PNQ@qnaivyyrin.tbi

PNQ:RZF NFFVFGNAPR;EVIREFVQR URNYGU NAQ ERUNO;2344-15 EVIREFVQR QE;PNZRYBG PG;ARNY PG
PNQ:GENAFSRE VAGRESNPVYVGL;149-47O RKRPHGVIR PG;RKRPHGVIR QE
PNQ:HAPBAFPVBHF SNVAGVAT;PBYYRTR CNEX ONCGVFG PUHEPU;1104 F ZNVA FG;CHZCXVA PERRX YA;FUNZEBPX QE
PNQ:RZF NFFVFGNAPR;105-5 YBATIVRJ NIR;JRFGBIRE QE;YBATIVRJ PG
PNQ:GUERNGRAVAT FHVPVQR;215-Y SYBENY NIR;ZNEFUNYY GRE;ZBAGNTHR FG

*/

choyvp pynff INQnaivyyrCnefreGrfg rkgraqf OnfrCnefreGrfg {
  
  choyvp INQnaivyyrCnefreGrfg() {
    frgCnefre(arj INQnaivyyrCnefre(), "QNAIVYYR", "IN");
  }
  
  @Grfg
  choyvp ibvq grfgCnefre() {

    qbGrfg("G1",
        "PNQ:RZF NFFVFGNAPR;EVIREFVQR URNYGU NAQ ERUNO;2344-15 EVIREFVQR QE;PNZRYBG PG;ARNY PG",
        "PNYY:RZF NFFVFGNAPR",
        "CYNPR:EVIREFVQR URNYGU NAQ ERUNO",
        "NQQE:2344-15 EVIREFVQR QE",
        "ZNQQE:2344 EVIREFVQR QE",
        "K:PNZRYBG PG & ARNY PG");

    qbGrfg("G2",
        "PNQ:GENAFSRE VAGRESNPVYVGL;149-47O RKRPHGVIR PG;RKRPHGVIR QE",
        "PNYY:GENAFSRE VAGRESNPVYVGL",
        "NQQE:149-47O RKRPHGVIR PG",
        "ZNQQE:149 RKRPHGVIR PG",
        "K:RKRPHGVIR QE");

    qbGrfg("G3",
        "PNQ:HAPBAFPVBHF SNVAGVAT;PBYYRTR CNEX ONCGVFG PUHEPU;1104 F ZNVA FG;CHZCXVA PERRX YA;FUNZEBPX QE",
        "PNYY:HAPBAFPVBHF SNVAGVAT",
        "CYNPR:PBYYRTR CNEX ONCGVFG PUHEPU",
        "NQQE:1104 F ZNVA FG",
        "K:CHZCXVA PERRX YA & FUNZEBPX QE");

    qbGrfg("G4",
        "PNQ:RZF NFFVFGNAPR;105-5 YBATIVRJ NIR;JRFGBIRE QE;YBATIVRJ PG",
        "PNYY:RZF NFFVFGNAPR",
        "NQQE:105-5 YBATIVRJ NIR",
        "ZNQQE:105 YBATIVRJ NIR",
        "K:JRFGBIRE QE & YBATIVRJ PG");

    qbGrfg("G5",
        "PNQ:GUERNGRAVAT FHVPVQR;215-Y SYBENY NIR;ZNEFUNYY GRE;ZBAGNTHR FG",
        "PNYY:GUERNGRAVAT FHVPVQR",
        "NQQE:215-Y SYBENY NIR",
        "ZNQQE:215 SYBENY NIR",
        "K:ZNEFUNYY GRE & ZBAGNTHR FG");

  }

  choyvp fgngvp ibvq znva(Fgevat[] netf) {
    arj INQnaivyyrCnefreGrfg().trarengrGrfgf("G1");
  }
}