package dev.scratch.nfttracker.model.mongo;

import org.springframework.data.mongodb.core.mapping.Field;

public class Destination {
    @Field(value = "role_id")
    public String roleID;
    @Field(value = "server_id")
    private String serverID;
    @Field(value = "channel_id")
    private String channelID;
    @Field
    private String nftName;

    public Destination() {
    }

    public Destination(String roleID, String serverID, String channelID) {
        this.roleID = roleID;
        this.serverID = serverID;
        this.channelID = channelID;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public String getNftName() {
        return nftName;
    }

    public Destination setNftName(String nftName) {
        this.nftName = nftName;
        return this;
    }
}
