# BIBLIOTEKSTEST
Detta är ett embryo till backend och API för att skapa och hantera ett bibliotek. 

## Inför uppgiften
Uppgiften kräver att du har basförståelse av Spring Boot och Spring Data JPA. Om du inte känner till dessa, googla lite på nätet, där förklaras dessa bra.

## UPPGIFT
Vi vill att du implementerar någon/några av de användningsfall som finns listade nedan.

Det vi vill uppnå med uppgiften är att se kodstil, kodkvalitet och struktur, samt att ha kod att prata om på den tekniska intervjun.
Du får gärna refaktorera koden och filstrukturen så att den är mer skalbar om du hittar förbättringar.

Du bör inte lägga mer än 3-5 timmar på uppgiften. Om du inte fått någon annan information av oss ber vi att du epostar oss din lösning innan intervjun.

Du får gärna förbättra och lägga till fler tester om du känner att du har tid.

### Användarfall:
* Som kund vill jag kunna söka efter böcker med titel eller författare.
* Som kund vill jag sortera mina sökresultat efter titel eller författarnamn.
* Som kund vill jag få ett informativt felmeddelande då jag skickat fel data till tjänsten.
* Som admin vill jag kunna göra alla CRUD-operationer på "book".
* Introducera "book genre" och möjliggör att söka och sortera med hjälp av det.
* Som admin vill jag kunna göra alla CRUD på "kund". Det betyder att du ska skapa en "customer" klass.
* Som kund vill jag kunna låna och återlämna böcker.
    * En kund kan inte boka fler än 2 böcker.
* Som kund vill jag kunna få lista på alla böcker som man har lånat.
* Som admin vill man kunna köra alla CRUD operationer på "author". "Author" ska ha "name", "nationality", "List of books".

### Att tänka på
* Avsikten är INTE att du ska implementera ALLA dessa användningsfall, utan välj NÅGRA av dem.
* Du bestämmer själv hur du vill verifiera och demoa funktionaliteten.
* Använd tredjeparts-libs om du vill.


## Lycka Till!