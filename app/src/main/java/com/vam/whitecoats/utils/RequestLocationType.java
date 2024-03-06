package com.vam.whitecoats.utils;

public enum RequestLocationType {


        REQUEST_LOCATION_HOME("Home"),
    REQUEST_LOCATION_Knowledge("Knowledge"),;

        String requestLocation;
    RequestLocationType(String requestLocation){
            this.requestLocation=requestLocation;
        }

        public String getRequestLocation(){
            return requestLocation;
        }
}
