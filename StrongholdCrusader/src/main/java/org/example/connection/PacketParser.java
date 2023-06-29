package org.example.connection;

import com.google.gson.Gson;

public class PacketParser {


    public Packet parsePacket(String gson){
        Packet packet =new Gson().fromJson(gson,Packet.class);
        return packet;
    }

    public String parseGson(Packet packet){
        return null;
    }
}
