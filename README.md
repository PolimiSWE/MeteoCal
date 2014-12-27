MeteoCal
========
chola poslednje promene:
- dodata admin stranica za privacy type
    + treba da se doradi izgleda ( trenutno ocajan )
    + znam da je najprostiji objekat ali svi ostali mogu da se odrade na isti nacin
    + unutar stranice ima i tabela koja vuce sve record iz table tako da moze da se vidi sta je u bazi ( mora da se osvezi stranica, nisam cacakao ajax )
- odradjena facada i bean za privacy type 

!!!! VAZNO !!!
-imamo problem sa JPA svaki put kad se pokerene aplikacija obrise celu DB i kreira nove prazne tabele tako da znas kad budes testirao novu stranicu to sto upises u bazu je samo validno za vreme sesije, moram to da pogledam sutra kako da sredim
-i generator IDjeva ne radi nista, nije lepo podesen, nasao sam kako se podesava pa cu posle da sredim, za sada kad se ubaca objekat u bazu mi mu zadajemo ID tako da pazi kad upisujes da je ID unique

- ako primetis da jos nesto treba da se sredjuje javi 

chola promene 1:
- dodao sam anotaciju za imena tabela da ne radi default ( ume da zaglupi sa default podesavanjima, kreiralo mi neke duple tabele )
- dodao sam anotaciju za mapiranja takodje da ne bude default ( targetEntity property )
- unacio sam sve relacije ( uz napomenu da 2-3 su mozda suvisne i veceras ih mozda izbacim kada odradim testing )
- nedostaju samo simple atributi u par entiteta ( dodacu kasnije uz testove )
