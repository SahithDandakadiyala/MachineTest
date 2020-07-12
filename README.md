SplashScreen appears for everytime user opens the app.
After the splashscreen appears,app asks for user permission to allow location while using app(for the first time).
Using Location Manager and LocationListener, app  gets access of LocationService and location of user.
And stores them in localdatabase(here I preferred using SharedPreferences rather than using SQLite because to store the small values of latitude and longitude, SharedPreferences will be enough)
As the latitude and longitude of Gateway of India are already pre-registered , the app shows marker on Gateway of India.
And it also shows marker on user's location(by fetching the values of  latitude and longitude from local database).
After updating markers on these two locations, the app redirects you to directions between them(after 4 secs).
I don't want to increase the size of my app by creating many Java files(like DirectionParsers and JSON objects), so I preferred redirecting to Google Maps(using fetched values).
