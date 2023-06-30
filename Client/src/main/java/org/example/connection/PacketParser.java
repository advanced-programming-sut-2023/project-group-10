package org.example.connection;

import com.google.gson.Gson;
import org.example.connection.Packet;

public class PacketParser {
    public Packet parsePacket(String gson){
        return new Gson().fromJson(gson,Packet.class);
    }

    public String parseGson(Packet packet){
        return null;
    }
}
