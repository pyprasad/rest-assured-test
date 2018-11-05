package utils;

import io.restassured.RestAssured;

public class Utils {
    public static String urlPath;
    public static String jsonPath;

    public static void setBaseURI(String baseURI){
        RestAssured.baseURI = baseURI;
    }

    public static void setBasePath(String basePath){
        RestAssured.basePath = basePath;
    }

    public static void resetBaseURI(){
        RestAssured.baseURI = "";
    }

    public static void resetBasePath(){
        RestAssured.basePath = "";
    }
}
