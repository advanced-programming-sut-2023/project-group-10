package org.example.view.enums.messages;

public enum CustomizeMapMessages {
    INVALID_TEXTURE_FOR_TREE("this texture can't be used in tiles with trees"),
    SET_TEXTURE_OF_BLOCK_SUCCESSFUL(""),
    SUCCESSFUL_CLEAR(""),
    NON_EMPTY_LAND("tile is already full"),
    DROP_ROCK_SUCCESSFUL(""),
    SUCCESSFUL_TREE_DROP(""),
    IS_KEEP("invalid choice, keep's tile should be walkable and empty"),
    INCOMPATIBLE_LAND("can't drop a tree on this texture");
    private final String message;

    CustomizeMapMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
