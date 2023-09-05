# Breweries
Android application that shows information about breweries collected from an online API

Specifications:
  - Homepage that contains a search button and an editable field that allows users to type the name of a city
  - When the search button is pressed, the input is validated.If the input is valid,the list of breweries returned by the API for the selected city is shown
  - If the user clicks a brewery, it is marked as favorite by adding a star in the layout
  - If the user clicks a brewery that was already marked as favorite, the star is removed
  - Options menu:
    + Favorite breweries - only the breweries that
were marked as favorite should be displayed
    + Not favorite breweries - only the breweries
that were not marked as favorite should be displayed
    + All
- Contextual menu: Displays breweries with the same type
- If the user clicks on  *See breweries with the same type*  then he is redirected to a new page that shows all the breweries that have the same type
as the selected brewery
- Delete brewery type option menu - If the user clicks on  *Delete brewery type* , the app returns to the activity
presenting the list of breweries and showw only the ones that have the type different
than the one presented on Brewery type page.

