package org.example;

import com.google.gson.Gson;

public class PacketParser {
    public Packet parsePacket(String gson){
        return new Gson().fromJson(gson,Packet.class);
    }

    public String parseGson(Packet packet){
        return null;
    }
}
