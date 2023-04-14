public enum BuildingTypes {

    APPLE_ORCHARD("apple orchard", "farm building"),
    DIARY_FARMER("diary farmer", "farm building"),
    HOPS_FARMER("hops farmer", "farm building");
    //define all buildings

    private String type;
    private String category;

    BuildingTypes(String type, String category){
        this.type = type;
        this.category = category;
    }

    public static String getCategory(String type){
        for(BuildingTypes buildingTypes : BuildingTypes.values()){
            if(type.equalsIgnoreCase(buildingTypes.type))
                return buildingTypes.category;
        }
        return null;
    }
}
