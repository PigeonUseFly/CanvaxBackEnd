# CanvaxBackEnd
Detta backend-program används för att förse frontend med information samt hantera informationen på de olika sätt som kan behövas för att leverera informationen på rätt sätt till frontend.
För tillfället skickas all information i events.json filen som en lång lista med EventJson-objekt.

För att köra programmet:
1. Öppna filerna i Intellij eller en annan IDE.
2. Kör main-metoden i Main.
3. Medans programmet fortfarande körs, öppna din webbläsare och skriv in: http://localhost:8080/events för att hämta event-objekten.
                                                                Skriv in: http://localhost:8080/events/{Id på event som finns i events.json} för att ta bort ett event
