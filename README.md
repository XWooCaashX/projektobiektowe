# Kalendarz Wydarzeń

**Kurs:** Programowanie Obiektowe  
**Autor:** Łukasz Kopański

## Opis zadania

Aplikacja "Kalendarz Wydarzeń" to program desktopowy napisany w języku Java z wykorzystaniem biblioteki Swing. Umożliwia użytkownikom zarządzanie osobistymi wydarzeniami, takimi jak spotkania i przypomnienia.

### Główne założenia i funkcjonalności:
- **Graficzny interfejs użytkownika:** Aplikacja posiada intuicyjny interfejs z widokiem kalendarza miesięcznego oraz listą wydarzeń dla wybranego dnia.
- **Zarządzanie wydarzeniami:** Użytkownik może dodawać dwa rodzaje wydarzeń:
    - **Spotkania:** Charakteryzujące się datą, opisem i miejscem.
    - **Przypomnienia:** Z datą, opisem i opcjonalnym wysokim priorytetem.
- **Nawigacja:** Możliwość łatwego przełączania się pomiędzy miesiącami w kalendarzu.
- **Trwałość danych:** Wszystkie wydarzenia są automatycznie zapisywane do pliku `events.txt` przy zamykaniu aplikacji i wczytywane przy jej ponownym uruchomieniu.
- **Polimorfizm:** Wykorzystanie polimorfizmu do obsługi różnych typów wydarzeń (Spotkanie, Przypomnienie) poprzez wspólną klasę bazową `Event`.

### Ograniczenia:
- Aplikacja działa lokalnie, nie posiada funkcji sieciowych ani synchronizacji.
- Formatowanie i walidacja danych wejściowych są podstawowe.
- Brak możliwości edycji istniejących wydarzeń (należy je usunąć i dodać ponownie).

## Diagram klas UML

![Diagram klas UML](DiagramUML.jpg)

## Instrukcja dla użytkownika

1.  **Uruchomienie aplikacji:** Aplikacja jest gotowa do uruchomienia. Po starcie wyświetli się główne okno.
2.  **Nawigacja po kalendarzu:**
    - Użyj przycisków `<` i `>` w górnej części kalendarza, aby przechodzić do poprzedniego lub następnego miesiąca.
    - Kliknij na dowolny dzień w kalendarzu, aby go zaznaczyć. Lista wydarzeń po prawej stronie zostanie automatycznie zaktualizowana, pokazując wydarzenia dla wybranej daty.
3.  **Dodawanie spotkania:**
    - Wybierz dzień w kalendarzu.
    - Naciśnij przycisk `Dodaj Spotkanie`.
    - W nowym oknie dialogowym wprowadź opis i miejsce spotkania, a następnie kliknij `OK`.
    - Nowe spotkanie pojawi się na liście wydarzeń dla wybranego dnia.
4.  **Dodawanie przypomnienia:**
    - Wybierz dzień w kalendarzu.
    - Naciśnij przycisk `Dodaj Przypomnienie`.
    - Wprowadź opis i zaznacz pole `Wysoki priorytet`, jeśli to konieczne. Kliknij `OK`.
    - Nowe przypomnienie pojawi się na liście.
5.  **Usuwanie wydarzenia:**
    - Kliknij na wydarzenie na liście po prawej stronie, aby je zaznaczyć.
    - Naciśnij przycisk `Usuń Zaznaczone`.
    - Wydarzenie zostanie usunięte z listy i z kalendarza.

## Ilustracja możliwości programu

-   **Widok główny:** Po lewej stronie znajduje się interaktywny kalendarz, a po prawej lista wydarzeń dla zaznaczonego dnia. Dni z wydarzeniami są podświetlone na niebiesko, dzisiejszy dzień na pomarańczowo, a wybrany na zielono.

-   **Dodawanie wydarzenia:** W celu dodania nowego spotkania, wybieramy interesujący nas dzień w kalendarzu, a następnie klikamy przycisk `Dodaj Spotkanie`. Pojawi się okno, w którym należy uzupełnić dane.

-   **Usuwanie wydarzenia:** Aby usunąć wydarzenie, należy je najpierw zaznaczyć na liście po prawej stronie, a następnie kliknąć przycisk `Usuń Zaznaczone`.
