package ru.stairenx.wellsofttestapp.server;

public class ConstantsAPI {

    private static final String URL = "http://zapravkimanagement.staging.wellsoft.pro";
    static final String AUTH = "/api/auth";
    static final String TRUCK_ROUTE = "/api/v1/truck/route";
    static final String CONTENT_TYPE = "application/x-www-form-urlencoded";

    public static final String JSON_GRANT_TYPE = "grant_type";
    public static final String JSON_USERNAME = "username";
    public static final String JSON_PASSWORD = "password";
    public static final String JSON_ERROR = "error";

    public static final String JSON_ACCESS_TOKEN = "access_token";
    public static final String JSON_TOKEN_TYPE = "token_type";
    public static final String JSON_EXPIRES_IN = "expires_in";
    public static final String JSON_REFRESH_TOKEN = "refresh_token";
    public static final String JSON_MESSAGE = "Message";
    public static final String JSON_ARRAY_ROUTE_SEGMENTS = "RouteSegments";
    public static final String JSON_ARRAY_FROM_BASE_COORDINATES = "FromBaseCoordinates";
    public static final String JSON_LONGITUDE = "Lng";
    public static final String JSON_LATITUDE = "Lat";

    static String getLink(String type){
        String result = "";
        switch (type){
            case AUTH :
                result = URL + AUTH;
                break;
            case TRUCK_ROUTE :
                result = URL + TRUCK_ROUTE;
                break;
            }
            return result;
        }
    }
