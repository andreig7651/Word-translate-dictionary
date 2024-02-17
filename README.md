# tema-2-andreig7651

Clasele Word si Definitions-construite conform enuntului;

Clasa dict_entry-reprezinta intrarea in dictionar,ce contine un cuvant si lista sa de defintii;

Clasa functions
    -locul unde am initializat map-ul si am construit functiile;
    -am declarat colectia de tip TreeMap pentru a fi sortata dupa hash-uri;
    
    -addWord:-iteram prin colectie si cautam hash-ul(limba) corespunzatoare;
             -cand il gasim,iteram prin lista de intrari si cautam cuvantul;
             -daca deja exista,intoarcem false;
             -daca nu exista,il adaugam si intoarcem true;

    -removeWord:-cautam hash-ul in dictionar;
                -cand il gasim calculam pozitia cuvantului in lista;
                -cautam cuvantul si il stergem folosind pozitia calculata;
    
    -addDefinitionForWord:-cautam hash-ul,apoi cautam cuvantul;
                          -verificam fiecare dictionar din lista de definitii si fiecare efinitie din el;
                          -daca defintia exista deja,adaugam doar defintitiile din el care nu exista(il actualizam mai bine zis);
                          -daca defintia nu exista il adaugam cu totul;
    
    -removeDefinition:-cautam hash-ul,apoi defintia in lista de definitii;
                      -ii calculam pozitia,apoi o stergem din lista;
    
    -translateWord:-cautam hash-ul fromLanguage in colectia de dictionare;
                   -cand il gasim,cautam cuvantul in dictionar,dar in listele de singular sau plural;
                   -salvam in care dintre aceste liste se afla,dar si pozitia in lista si forma sa in engleza;
                   -acum,cautam hash-ul toLanguage,cautam forma cuvantului in engleza,la fiecare intrare;
                   -apoi iteram prin lista de singular sau plural,dupa caz,si intoarcem pozitia salvata;
                   -daca nu exista o traducere,intoarcem cuvantul;
    
    -translateSentence:-spargem propozitia in cuvinte,traducem fiecare cuvant apoi ii facem append 
                        la propozitia tradusa,pe care o vom intoarce;
    
    -translateSentences:-intoarcem doar 3 variante de traducere;
                        -traducem propozitia cuvant cu cuvant;
                        -cautam hash-ul fromLanguage,iteram prin lista sa de intrari;
                        -cautam cuvantul din propozitie,ii salvam forma in engleza;
                        -cautam hash-ul toLanguage,apoi iteram prin intrarile liste si cautam cuvantul in engleza;
                        -construim frazele cu indicii corespunzatori de la 0 la 2,folosind sinonimele din dcitionarul
                        de sinonime al cuvantului;
                        -dupa ce construim toata fraza,o adaugam in lista pe care o vom intoarce la final;
    
    -getDefinitionsForWord:-cautam hash-ul in colectie,apoi cautam cuvantul cerut;
                           -salvam anii definitiilor,intr-un vector,pe care il ordonam;
                           -parcurgem vectorul ordonat,si cautam definitiile dupa anii ordonati,apoi le adaugam intr-o lista;
                           -intoarcem lista de definitii ordonata;
    
    -exportDictionary:-trebuie sa afisam o lista de intrari;
                      -cautam hash-ul dat;
                      -realizam ordonarea cuvintelor,apoi construim obiectul json pentru fiecare cuvant;
                      -adaugam obiectul construit in lista pe care o vom exporta in fisier;
                      -construim numele fisierului de output,afisam lista in fisier;

    -afisareMap:-este o metoda creeata in plus de mine,chiar daca nu se cerea,pentru a vedea mai bine evolutia colectiei,dupa folosirea functiilor;

Clasa tema2:-declaram un element de tip functions,pentru a apela functiile;
            -primi folderul de intrare,cautam prin toate fisierele sale doar pe cele ce contin json;
            -salvam hash-ul fisierului;
            -parcurgem lista de cuvinte din fisier,initializam un element de tip entry si completam campurile sale cu datele citite;
            -apoi iteram prin defintii pe care le initializam in intrare;
            -adaugam cuvantul intr-o lista pe care o punem la hash-ul corespunzator;
            -apoi am cateva teste pentru fiecare functie;