DOCUMENTATION
	
COMMON: 
In this a library module you will find the base source code needed to create the other client apps, it includes the following API’s:
•	BaseItemListActivity: this activity will fetch a list of topics  from the web service and build a simple Recycler View or Grid View List
•	BaseItemDetailActivity: this activity that will present the details of a Topic.

To make the connection between the app & the net, the app uses Retrofit. Retrofit is also used to request data in JSON format strings and then convert the data in Java classes (POJCs)

The app module supports Phones & Tablets; in devices with a width < 900dp you can see the content displayed in a vertical Recycler View or in a GridView list in both orientations (port & land), depending on your choice in the toolbar top-right side.
But in devices with a width >= 900dp (Tablets of 7, 8 & 10 inches) in landscape mode a different layout is used to present the content in a two pane fashion. The left side is a list of topics, and the right shows the details of the selected item.

The items in the list are displayed on CardView and comply with Material Design.
Some material design libraries were added to give a better presentation to the lists & have a better control in the toolbar.

Picasso was used to download & cache the images in the device, resulting in better performance when navigating the list.

The app uses animation transitions between activities & between fragments (in landscape mode for Tablets).

SIMPSONS CHARACTER VIEWER: 

  This module app fetches the contents of the following Restful web service: http://api.duckduckgo.com with the query string “simpsons characters”

GOT CHARACTER VIEWER: 
  This is a module app that fetches the contents of the following Restful web service: http://api.duckduckgo.com with the query string “game of thrones characters”

The project is optimized in such a way that the app modules only extend from the library project and write the necessary code present the content they want.
